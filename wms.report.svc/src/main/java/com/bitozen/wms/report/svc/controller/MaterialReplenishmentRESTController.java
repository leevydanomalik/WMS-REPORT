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
public class MaterialReplenishmentRESTController {
    
    private final String MATERIAL_REPLENISHMENT = "material_replenishment.jasper";
    private final String MATERIAL_REPLENISHMENT_NEW = "material_replenishment_new.jasper";

    @Autowired
    ReportService reportService;

    @ResponseBody
    @RequestMapping(value = "data.material.replenishment.csv/", method = RequestMethod.GET,
            produces = "text/csv")
    public byte[] generateMaterialReplenishmentReportCSV(@RequestParam(value = "reportFormat", required = false) FileExtention reportFormat) throws GenericException {
        Map<String, Object> params = new HashMap<>();
        ByteArrayOutputStream os;
        reportService.setRptResourcePrefix("/report/");
        os = (ByteArrayOutputStream) reportService.showReportJdbcDataSourceExportToPdfTxtCsvXls(FileExtention.CSV, MATERIAL_REPLENISHMENT, params);

        /*validate object*/
        Validate.notNull(os);

        /*get the byte*/
        byte[] datastream = os.toByteArray();

        /*validate object*/
        Validate.notNull(datastream);

        return datastream;
    }
    
    @RequestMapping(value = "/materialreplenishment/{clientID}/{companyID}/{plantID}",
            method = RequestMethod.GET,
            produces = "text/csv")
    public byte[] generateMaterialReplenishmentReportByES(@PathVariable("clientID") String clientID, @PathVariable("companyID") String companyID,@PathVariable("plantID") String plantID) {

        Map<String, Object> params = new HashMap<>();
        params.put("clientid", clientID);
        params.put("companyid", companyID);
        params.put("plantid", plantID);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        reportService.setRptResourcePrefix("/report/");
        try {
            os = (ByteArrayOutputStream) reportService.showReportJdbcDataSourceExportToPdfTxtCsvXls(FileExtention.CSV, MATERIAL_REPLENISHMENT_NEW, params);
        } catch (GenericException ex) {
            Logger.getLogger(MaterialReplenishmentRESTController.class.getName()).log(Level.SEVERE, null, ex);
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
