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
public class MaterialBasePriceRESTController {
    
    private final String MATERIAL_BASE_PRICE_CSV = "material_base_price.jasper";
    private final String MATERIAL_BASEPRICE_ITEM_CSV = "material_baseprice_item.jasper";
     
    @Autowired 
    ReportService reportService;
    
    @ResponseBody
    @RequestMapping(value = "data.material.base.price.csv/", method = RequestMethod.GET,
            produces = "text/csv")
    public byte[] generateMaterialBasePriceReportCSV(@RequestParam(value = "reportFormat", required = false) FileExtention reportFormat ) throws GenericException {
        Map<String, Object> params = new HashMap<>();
        ByteArrayOutputStream os;
        reportService.setRptResourcePrefix("/report/");
        os = (ByteArrayOutputStream) reportService.showReportJdbcDataSourceExportToPdfTxtCsvXls(FileExtention.CSV, MATERIAL_BASE_PRICE_CSV,params);

        /*validate object*/
        Validate.notNull(os);

        /*get the byte*/
        byte[] datastream = os.toByteArray();

        /*validate object*/
        Validate.notNull(datastream);

        return datastream;
    }
    
    @ResponseBody
    @RequestMapping(value = "/material.base.price.item/{basepriceID}", method = RequestMethod.GET,
            produces = "text/csv")
    public byte[] generateMaterialBasePriceItemReportCSV(@PathVariable("basepriceID") String basepriceID) {
        Map<String, Object> params = new HashMap<>();
        params.put("basepriceid", basepriceID);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        reportService.setRptResourcePrefix("/report/");
        try {
            os = (ByteArrayOutputStream) reportService.showReportJdbcDataSourceExportToPdfTxtCsvXls(FileExtention.CSV, MATERIAL_BASEPRICE_ITEM_CSV, params);
        } catch (GenericException ex) {
            Logger.getLogger(MaterialBasePriceRESTController.class.getName()).log(Level.SEVERE, null, ex);
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
