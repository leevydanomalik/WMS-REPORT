package com.bitozen.wms.report.svc.controller;

import com.bitozen.wms.report.common.exception.GenericException;
import com.bitozen.wms.report.common.type.FileExtention;
import com.bitozen.wms.report.svc.ReportService;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author IZZY KHALAM
 */
@RestController
@RequestMapping("report")
public class PRAllocRESTController {
    
    private final String PRALLOC_CSV = "prAllocCsv.jasper";
    private final String PRALLOC_REPORT = "prAllocReport.jasper";
    
    @Autowired 
    ReportService reportService;
    
    @ResponseBody
    @RequestMapping(value = "data.pr.alloc.csv/{period}/{startDate}/{endDate}", method = RequestMethod.GET,
            produces = "text/csv")
    public byte[] generatePrAllocReportCSV(@RequestParam(value = "reportFormat", required = false) FileExtention reportFormat,@PathVariable("period") String period,@PathVariable("startDate") String startDate,@PathVariable("endDate") String endDate ) throws GenericException {
        Map<String, Object> params = new HashMap<>();
        params.put("period", period);
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        ByteArrayOutputStream os;
        reportService.setRptResourcePrefix("/report/");
        os = (ByteArrayOutputStream) reportService.showReportJdbcDataSourceExportToPdfTxtCsvXls(FileExtention.CSV, PRALLOC_CSV, params);

        /*validate object*/
        Validate.notNull(os);

        /*get the byte*/
        byte[] datastream = os.toByteArray();

        /*validate object*/
        Validate.notNull(datastream);

        return datastream;
    }
    
     @RequestMapping(value = "/prAllocReport/{period}/{startDate}/{endDate}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<?> generatePrAllocReport(@PathVariable("period") String period,@PathVariable("startDate") String startDate,@PathVariable("endDate") String endDate) {

        Map<String, Object> params = new HashMap<>();
        params.put("period", period);
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        reportService.setRptResourcePrefix("/report/");
        try {
            os = (ByteArrayOutputStream) reportService.showReportJdbcDataSourceExportToPdfTxtCsvXls(null, PRALLOC_REPORT, params);
        } catch (GenericException ex) {
            Logger.getLogger(PRAllocRESTController.class.getName()).log(Level.SEVERE, null, ex);
        }

        /*validate object*/
        Validate.notNull(os);

        /*get the byte*/
        byte[] datastream = os.toByteArray();

        /*validate object*/
        Validate.notNull(datastream);

        return ResponseEntity.status(HttpStatus.OK).body(datastream);
    }
    
}
