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
public class SOSalesRESTController {
    
    private final String SOSALES_CSV = "soSalesCsv.jasper";
    private final String SOSALES_REPORT = "soSalesReport.jasper";
    
     @Autowired 
    ReportService reportService;
    
    @ResponseBody
    @RequestMapping(value = "data.so.sales.csv/{period}/{startDate}/{endDate}", method = RequestMethod.GET,
            produces = "text/csv")
    public byte[] generateSoSalesReportCSV(@RequestParam(value = "reportFormat", required = false) FileExtention reportFormat,@PathVariable("period") String period,@PathVariable("startDate") String startDate,@PathVariable("endDate") String endDate ) throws GenericException {
        Map<String, Object> params = new HashMap<>();
        params.put("period", period);
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        ByteArrayOutputStream os;
        reportService.setRptResourcePrefix("/report/");
        os = (ByteArrayOutputStream) reportService.showReportJdbcDataSourceExportToPdfTxtCsvXls(FileExtention.CSV, SOSALES_CSV, params);

        /*validate object*/
        Validate.notNull(os);

        /*get the byte*/
        byte[] datastream = os.toByteArray();

        /*validate object*/
        Validate.notNull(datastream);

        return datastream;
    }
    
     @RequestMapping(value = "/soSalesReport/{period}/{startDate}/{endDate}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<?> generateSOSalesReport(@PathVariable("period") String period,@PathVariable("startDate") String startDate,@PathVariable("endDate") String endDate) {

        Map<String, Object> params = new HashMap<>();
        params.put("period", period);
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        reportService.setRptResourcePrefix("/report/");
        try {
            os = (ByteArrayOutputStream) reportService.showReportJdbcDataSourceExportToPdfTxtCsvXls(null, SOSALES_REPORT, params);
        } catch (GenericException ex) {
            Logger.getLogger(SOSalesRESTController.class.getName()).log(Level.SEVERE, null, ex);
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
