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
public class COAMovementRESTController {
    
     private final String COA_MOVEMENT_CSV = "Coa_movement.jasper";
     private final String COA_MOVEMENT_NEW_CSV = "Coa_movement_new.jasper";
     
    @Autowired 
    ReportService reportService;
    
    @ResponseBody
    @RequestMapping(value = "data.coa.movement.csv/", method = RequestMethod.GET,
            produces = "text/csv")
    public byte[] generateCOAMovementReportCSV(@RequestParam(value = "reportFormat", required = false) FileExtention reportFormat ) throws GenericException {
        Map<String, Object> params = new HashMap<>();
        ByteArrayOutputStream os;
        reportService.setRptResourcePrefix("/report/");
        os = (ByteArrayOutputStream) reportService.showReportJdbcDataSourceExportToPdfTxtCsvXls(FileExtention.CSV, COA_MOVEMENT_CSV,params);

        /*validate object*/
        Validate.notNull(os);

        /*get the byte*/
        byte[] datastream = os.toByteArray();

        /*validate object*/
        Validate.notNull(datastream);

        return datastream;
    }
    
    @ResponseBody
    @RequestMapping(value = "/data.coa.movement/{cgID}", method = RequestMethod.GET,
            produces = "text/csv")
    public byte[] generateCOAMovementByCgIDReportCSV(@PathVariable("cgID") String cgID) {
        Map<String, Object> params = new HashMap<>();
        params.put("cgid", cgID);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        reportService.setRptResourcePrefix("/report/");
        try {
            os = (ByteArrayOutputStream) reportService.showReportJdbcDataSourceExportToPdfTxtCsvXls(FileExtention.CSV, COA_MOVEMENT_NEW_CSV, params);
        } catch (GenericException ex) {
            Logger.getLogger(COAMovementRESTController.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*validate object*/
        Validate.notNull(os);

        /*get the byte*/
        byte[] datastream = os.toByteArray();

        /*validate object*/
        Validate.notNull(datastream);

        return datastream;
    }
    
}
