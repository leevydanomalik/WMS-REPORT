package com.bitozen.wms.report.svc.controller.convertrtftopdf;

import com.bitozen.wms.common.dto.GenericResponseDTO;
import com.bitozen.wms.common.type.ProjectType;
import com.bitozen.wms.common.util.LogOpsUtil;
import com.bitozen.wms.report.dto.RequestReportConvertDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author amy
 */
@RestController
@RequestMapping("/convertpdf")
@Slf4j
public class DocumentConvertRTFToPDFRESTController {

    @Autowired
    private DocumentConvertToPDFService documentConvertToPDFService;
    
    @Autowired
    private ReportUploadServiceRTFToPDF reportUploadService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HttpServletRequest httpRequest;
    
    @PostMapping("/upload.template")
    public ResponseEntity<GenericResponseDTO<String>> uploadTemplateRTF(@RequestParam("file") MultipartFile upload, @RequestParam("userID") String user) {
        try {
            log.info(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(LogOpsUtil.getLogOps(ProjectType.CRUD, "Upload", DocumentConvertRTFToPDFRESTController.class.getName(),
                            httpRequest.getRequestURL().toString(),
                            new Date(), "Rest", "Upload Template",
                            user == null? "SYSTEM": user,
                            "")));
        } catch (JsonProcessingException ex) {
            log.info(ex.getMessage());
        }
        GenericResponseDTO<String> response = reportUploadService.saveUploadedFile(upload, user);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    
    @RequestMapping(value = "/generate.rtf.template.to.pdf",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<?> convertToPDF(@RequestBody RequestReportConvertDTO dto) {
        byte[] document = null;
        try {
            log.info(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(LogOpsUtil.getLogOps(ProjectType.CRUD, "Generate RTF Template To PDF", DocumentConvertRTFToPDFRESTController.class.getName(),
                    httpRequest.getRequestURL().toString(),
                    new Date(), "Rest", "convertToPDF",
                    "SYSTEM",
                    dto)));
            document = documentConvertToPDFService.convertDocument(dto).getData();
        } catch (JsonProcessingException ex) {
            log.info(ex.getMessage());
        }
        
        return ResponseEntity.status(HttpStatus.OK).body(document);
    }

}
