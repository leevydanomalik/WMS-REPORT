package com.bitozen.wms.report.svc.controller.convertrtftopdf;

import com.bitozen.wms.common.dto.GenericResponseDTO;
import com.bitozen.wms.common.type.ProjectType;
import com.bitozen.wms.common.util.LogOpsUtil; 
import com.bitozen.wms.report.domain.ReportData;
import com.bitozen.wms.report.domain.ReportTemplate;
import com.bitozen.wms.report.domain.common.CreationalSpecification;
import com.bitozen.wms.report.domain.repository.ReportDataRepository;
import com.bitozen.wms.report.domain.repository.ReportTemplateRepository;
import com.bitozen.wms.report.dto.RequestReportConvertDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lowagie.text.DocumentException;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import com.netflix.hystrix.exception.HystrixTimeoutException;
import com.tutego.jrtf.Rtf; 
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 *
 * @author amy
 */
@Service
@Slf4j
public class DocumentConvertToPDFService {

    @Autowired
    private ReportDataRepository reportDataRepository;

    @Autowired
    private ReportTemplateRepository reportTemplateRepository;
    
    @Autowired
    private PdfConvertService pdfConvertService;
    
    @Autowired
    private ObjectMapper objectMapper;

    @Value("${wms.upload.path}")
    private String BASE_UPLOAD_FOLDER;
    
    @Value("${unoconv_home}") 
    public String unoconvHome;
    
    @Value("${wms.upload.path}")
    public String outputDirFile; 

    private static final String FILE_RESULT_SUFFIX = "_result";

    @HystrixCommand(fallbackMethod = "defaultConvertDocumentFallback")
    public GenericResponseDTO<byte[]>  convertDocument(RequestReportConvertDTO dto) {
        try {
            Optional<ReportTemplate> oReportTemplate = reportTemplateRepository.findOneByReportTemplateID(dto.getTemplateID());

            ReportTemplate reportTemplate = oReportTemplate != null ? oReportTemplate.get() : new ReportTemplate();
            Rtf.template(new FileInputStream(reportTemplate.getReportTemplateURL())).inject(dto.getDatas())
                    .out(new FileOutputStream(BASE_UPLOAD_FOLDER + reportTemplate.getReportTemplateID() + FILE_RESULT_SUFFIX + ".rtf"));

            convertToPdf(reportTemplate);

            reportDataRepository.save(
                    new ReportData(objectMapper.writeValueAsString(dto.getDatas()),
                            dto.getTemplateID(),
                            new CreationalSpecification("SYSTEM", new Date(), null, null))
            );

            log.info(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(LogOpsUtil.getLogResponse(
                    ProjectType.CRUD, " Report", new Date(), "Rest", new GenericResponseDTO().successResponse().getCode(),
                    new GenericResponseDTO().successResponse().getMessage())));
            return new GenericResponseDTO().successResponse(Files.readAllBytes(new File(BASE_UPLOAD_FOLDER + reportTemplate.getReportTemplateID() + FILE_RESULT_SUFFIX + ".pdf").toPath()));
        } catch (IOException | DocumentException e) {
            log.info(e.getMessage());
            try {
                log.info(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(
                        LogOpsUtil.getErrorResponse(ProjectType.CRUD, " Report", new Date(), "Rest", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), e.getStackTrace())));
            } catch (JsonProcessingException ex) {
                log.info(ex.getMessage());
            } 
            return new GenericResponseDTO().errorResponse(String.valueOf(HttpStatus.NOT_FOUND.value()), e.getLocalizedMessage());
        }
    }

    private void convertToPdf(ReportTemplate reportTemplate) throws DocumentException, FileNotFoundException, IOException {
        
        File inputFile = new File(BASE_UPLOAD_FOLDER + reportTemplate.getReportTemplateID() + FILE_RESULT_SUFFIX + ".rtf");
 
        pdfConvertService.examine(inputFile,unoconvHome,outputDirFile);
        inputFile.delete();
    }

    private GenericResponseDTO<byte[]> defaultConvertDocumentFallback(RequestReportConvertDTO dTO, Throwable e) throws IOException {
        return new GenericResponseDTO<byte[]>().errorResponse(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                e instanceof HystrixTimeoutException ? "Connection Timeout. Please Try Again Later"
                        : e instanceof HystrixBadRequestException ? "Bad Request. Please recheck submitted data" : e.getLocalizedMessage());
    }
}
