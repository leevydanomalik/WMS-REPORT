package com.bitozen.wms.report.svc.controller;

import com.bitozen.wms.common.dto.GenericResponseDTO;
import com.bitozen.wms.common.type.ProjectType;
import com.bitozen.wms.common.util.LogOpsUtil;
import com.bitozen.wms.report.common.exception.GenericException;
import com.bitozen.wms.report.common.type.FileExtention;
import com.bitozen.wms.report.dto.GRVSPRDTO;
import com.bitozen.wms.report.dto.PDTDTO;
import com.bitozen.wms.report.dto.POVSPRDTO;
import com.bitozen.wms.report.svc.ReportService;
import com.bitozen.wms.report.svc.service.MBRReportService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ASUS
 */
@RestController
@RequestMapping("report")
@Slf4j
public class MBRRESTController {
    
    private final String POvsPR = "PO VS PR.jasper";
    private final String GRvsPR = "GR VS PR.jasper";
    private final String PDT = "PDT.jasper";
     
    @Autowired 
    ReportService reportService;
    
    @Autowired 
    MBRReportService mbr;
    
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HttpServletRequest httpRequest;
    
    @ResponseBody
    @RequestMapping(value = "data.po.pr.csv/", method = RequestMethod.GET,
            produces = "text/csv")
    public byte[] generatePOPRCSV(@RequestParam(value = "reportFormat", required = false) FileExtention reportFormat ) throws GenericException {
        Map<String, Object> params = new HashMap<>();
        ByteArrayOutputStream os;
        reportService.setRptResourcePrefix("/report/");
        os = (ByteArrayOutputStream) reportService.showReportJdbcDataSourceExportToPdfTxtCsvXls(FileExtention.CSV, POvsPR,params);

        /*validate object*/
        Validate.notNull(os);

        /*get the byte*/
        byte[] datastream = os.toByteArray();

        /*validate object*/
        Validate.notNull(datastream);

        return datastream;
    }
    
    @ResponseBody
    @RequestMapping(value = "data.gr.pr.csv/", method = RequestMethod.GET,
            produces = "text/csv")
    public byte[] generateGRPRCSV(@RequestParam(value = "reportFormat", required = false) FileExtention reportFormat ) throws GenericException {
        Map<String, Object> params = new HashMap<>();
        ByteArrayOutputStream os;
        reportService.setRptResourcePrefix("/report/");
        os = (ByteArrayOutputStream) reportService.showReportJdbcDataSourceExportToPdfTxtCsvXls(FileExtention.CSV, GRvsPR,params);

        /*validate object*/
        Validate.notNull(os);

        /*get the byte*/
        byte[] datastream = os.toByteArray();

        /*validate object*/
        Validate.notNull(datastream);

        return datastream;
    }
    
    @ResponseBody
    @RequestMapping(value = "data.pdt.csv/", method = RequestMethod.GET,
            produces = "text/csv")
    public byte[] generatePDTCSV(@RequestParam(value = "reportFormat", required = false) FileExtention reportFormat ) throws GenericException {
        Map<String, Object> params = new HashMap<>();
        ByteArrayOutputStream os;
        reportService.setRptResourcePrefix("/report/");
        os = (ByteArrayOutputStream) reportService.showReportJdbcDataSourceExportToPdfTxtCsvXls(FileExtention.CSV, PDT,params);

        /*validate object*/
        Validate.notNull(os);

        /*get the byte*/
        byte[] datastream = os.toByteArray();

        /*validate object*/
        Validate.notNull(datastream);

        return datastream;
    }
    
    @GetMapping(value = "/get.po.vs.pr")
    public ResponseEntity<GenericResponseDTO<List<POVSPRDTO>>> getPOVSPR() throws ParseException, JsonProcessingException {
        try {
            log.info(objectMapper.writeValueAsString(
                    LogOpsUtil.getLogOps(ProjectType.CQRS, "MBR", MBRRESTController.class.getName(),
                            httpRequest.getRequestURL().toString(),
                            new Date(), "Query", "getPOVSPR",
                            "SYSTEM",
                            "")));
        } catch (JsonProcessingException ex) {
            log.info(ex.getMessage());
        }
        GenericResponseDTO<List<POVSPRDTO>> response = mbr.getPOVSPR();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    @GetMapping(value = "/get.gr.vs.pr")
    public ResponseEntity<GenericResponseDTO<List<GRVSPRDTO>>> getGRVSPR() throws ParseException, JsonProcessingException {
        try {
            log.info(objectMapper.writeValueAsString(
                    LogOpsUtil.getLogOps(ProjectType.CQRS, "MBR", MBRRESTController.class.getName(),
                            httpRequest.getRequestURL().toString(),
                            new Date(), "Query", "getGRVSPR",
                            "SYSTEM",
                            "")));
        } catch (JsonProcessingException ex) {
            log.info(ex.getMessage());
        }
        GenericResponseDTO<List<GRVSPRDTO>> response = mbr.getGRVSPR();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    @GetMapping(value = "/get.pdt")
    public ResponseEntity<GenericResponseDTO<List<PDTDTO>>> getPDT() throws ParseException, JsonProcessingException {
        try {
            log.info(objectMapper.writeValueAsString(
                    LogOpsUtil.getLogOps(ProjectType.CQRS, "MBR", MBRRESTController.class.getName(),
                            httpRequest.getRequestURL().toString(),
                            new Date(), "Query", "getPDT",
                            "SYSTEM",
                            "")));
        } catch (JsonProcessingException ex) {
            log.info(ex.getMessage());
        }
        GenericResponseDTO<List<PDTDTO>> response = mbr.getPDT();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
}
