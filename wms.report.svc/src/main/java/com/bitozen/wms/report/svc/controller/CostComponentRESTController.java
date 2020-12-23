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
 * @author vulture
 */
@RestController
@RequestMapping("report")
public class CostComponentRESTController {
    
    private final String COST_COMPONENT = "cost_component.jasper";
    private final String COST_COMPONENT_NEW = "cost_component_new.jasper";

    @Autowired
    ReportService reportService;

    @ResponseBody
    @RequestMapping(value = "data.cost.component.csv/", method = RequestMethod.GET,
            produces = "text/csv")
    public byte[] generateCostComponentReportCSV(@RequestParam(value = "reportFormat", required = false) FileExtention reportFormat) throws GenericException {
        Map<String, Object> params = new HashMap<>();
        ByteArrayOutputStream os;
        reportService.setRptResourcePrefix("/report/");
        os = (ByteArrayOutputStream) reportService.showReportJdbcDataSourceExportToPdfTxtCsvXls(FileExtention.CSV, COST_COMPONENT, params);

        /*validate object*/
        Validate.notNull(os);

        /*get the byte*/
        byte[] datastream = os.toByteArray();

        /*validate object*/
        Validate.notNull(datastream);

        return datastream;
    }
    
    @ResponseBody
    @RequestMapping(value = "/data.cost.component/{cgID}", method = RequestMethod.GET,
            produces = "text/csv")
    public byte[] generateCostComponentByCgIDReportCSV(@PathVariable("cgID") String cgID) {
        Map<String, Object> params = new HashMap<>();
        params.put("cgid", cgID);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        reportService.setRptResourcePrefix("/report/");
        try {
            os = (ByteArrayOutputStream) reportService.showReportJdbcDataSourceExportToPdfTxtCsvXls(FileExtention.CSV, COST_COMPONENT_NEW, params);
        } catch (GenericException ex) {
            Logger.getLogger(CostComponentRESTController.class.getName()).log(Level.SEVERE, null, ex);
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
