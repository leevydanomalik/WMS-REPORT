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
 * @author ASUS
 */
@RestController
@RequestMapping("report")
public class MaterialStockOpnameRESTController {

    private final String MATERIAL_STOCK_OPNAME_CSV = "material_stock_opname.jasper";
    private final String MATERIAL_STOCK_OPNAME_NEW_CSV = "material_stock_opname_new.jasper";

    @Autowired
    ReportService reportService;

    @ResponseBody
    @RequestMapping(value = "data.material.stock.opname.csv/", method = RequestMethod.GET,
            produces = "text/csv")
    public byte[] generateSAPActivityPostingReportCSV(@RequestParam(value = "reportFormat", required = false) FileExtention reportFormat) throws GenericException {
        Map<String, Object> params = new HashMap<>();
        ByteArrayOutputStream os;
        reportService.setRptResourcePrefix("/report/");
        os = (ByteArrayOutputStream) reportService.showReportJdbcDataSourceExportToPdfTxtCsvXls(FileExtention.CSV, MATERIAL_STOCK_OPNAME_CSV, params);

        /*validate object*/
        Validate.notNull(os);

        /*get the byte*/
        byte[] datastream = os.toByteArray();

        /*validate object*/
        Validate.notNull(datastream);

        return datastream;
    }
    
    @RequestMapping(value = "/materialstockopname/{clientID}/{companyID}/{plantID}",
            method = RequestMethod.GET,
            produces = "text/csv")
    public byte[] generateMaterialStockOpnameReportByES(@PathVariable("clientID") String clientID, @PathVariable("companyID") String companyID,@PathVariable("plantID") String plantID) {

        Map<String, Object> params = new HashMap<>();
        params.put("clientid", clientID);
        params.put("companyid", companyID);
        params.put("plantid", plantID);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        reportService.setRptResourcePrefix("/report/");
        try {
            os = (ByteArrayOutputStream) reportService.showReportJdbcDataSourceExportToPdfTxtCsvXls(FileExtention.CSV, MATERIAL_STOCK_OPNAME_NEW_CSV, params);
        } catch (GenericException ex) {
            Logger.getLogger(MaterialStockOpnameRESTController.class.getName()).log(Level.SEVERE, null, ex);
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
