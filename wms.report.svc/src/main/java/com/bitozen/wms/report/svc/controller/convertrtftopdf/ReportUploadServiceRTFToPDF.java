package com.bitozen.wms.report.svc.controller.convertrtftopdf;

import com.bitozen.wms.common.dto.GenericResponseDTO;
import com.bitozen.wms.common.type.ProjectType;
import com.bitozen.wms.common.util.LogOpsUtil;
import com.bitozen.wms.report.domain.ReportTemplate;
import com.bitozen.wms.report.domain.common.CreationalSpecification;
import com.bitozen.wms.report.domain.repository.ReportTemplateRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author bitozen
 */
@Service
@Slf4j
public class ReportUploadServiceRTFToPDF {

    @Autowired
    private ReportTemplateRepository reportTemplateRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${wms.upload.path}")
    private String BASE_UPLOAD_FOLDER;

    public GenericResponseDTO<String> saveUploadedFile(MultipartFile upload, String user) {
        GenericResponseDTO<String> response = new GenericResponseDTO().successResponse();
        long lastindex = reportTemplateRepository.count() + 1;
        String reportTemplateID = "template-" + lastindex;
        try {
            byte[] bytes;
            bytes = upload.getBytes();
            Path path = Paths.get(BASE_UPLOAD_FOLDER + reportTemplateID);
            Files.write(path, bytes);
            reportTemplateRepository.save(new ReportTemplate(reportTemplateID, path.toString(),
                    new CreationalSpecification(user, new Date(), null, null))
            );
            log.info(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(LogOpsUtil.getLogResponse(
                    ProjectType.CRUD, "Report Template", new Date(), "UPLOAD", new GenericResponseDTO().successResponse().getCode(),
                    new GenericResponseDTO().successResponse().getMessage())));
            response.setData(user);
        } catch (IOException ex) {
            try {
                log.info(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(
                        LogOpsUtil.getErrorResponse(ProjectType.CRUD, "Report Template", new Date(), "UPLOAD", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), ex.getStackTrace())));
            } catch (JsonProcessingException jpex) {
                log.info(jpex.getMessage());
            }
        }
        return response;
    }

}
