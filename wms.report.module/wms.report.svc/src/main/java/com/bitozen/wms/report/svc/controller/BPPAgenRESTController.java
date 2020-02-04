package com.bitozen.wms.report.svc.controller;

import com.bitozen.wms.report.common.exception.GenericException;
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
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author IZZY KHALAM
 */
@RestController
@RequestMapping("report")
public class BPPAgenRESTController {
    
     private final String BPPAgen_JASPER = "BPPAgen.jasper";
    
    @Autowired
    ReportService reportService;
    
    @RequestMapping(value = "/bppAgen/{soNumber}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<?> generateBPPAgen(@PathVariable("soNumber") String soNumber) {

        Map<String, Object> params = new HashMap<>();
        params.put("sonumber", soNumber);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        reportService.setRptResourcePrefix("/report/");
        try {
            os = (ByteArrayOutputStream) reportService.showReportJdbcDataSourceExportToPdfTxtCsvXls(null, BPPAgen_JASPER, params);
        } catch (GenericException ex) {
            Logger.getLogger(BPPAgenRESTController.class.getName()).log(Level.SEVERE, null, ex);
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
