package com.bitozen.wms.report.svc.controller;

import com.bitozen.wms.report.common.exception.GenericException;
import com.bitozen.wms.report.common.type.FileExtention;
import com.bitozen.wms.report.svc.ReportService;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
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
public class MaterialOutboundLabellingRESTController {
    
    
    private final String MATERIAL_OUTBOUND_LABELLING = "material_outbound_labelling.jasper";
     
    @Autowired 
    ReportService reportService;
    
    @ResponseBody
    @RequestMapping(value = "data.material.outbound.labelling.csv/", method = RequestMethod.GET,
            produces = "text/csv")
    public byte[] generateMaterialOutboundLabellingReportCSV(@RequestParam(value = "reportFormat", required = false) FileExtention reportFormat ) throws GenericException {
        Map<String, Object> params = new HashMap<>();
        ByteArrayOutputStream os;
        reportService.setRptResourcePrefix("/report/");
        os = (ByteArrayOutputStream) reportService.showReportJdbcDataSourceExportToPdfTxtCsvXls(FileExtention.CSV, MATERIAL_OUTBOUND_LABELLING, params);

        /*validate object*/
        Validate.notNull(os);

        /*get the byte*/
        byte[] datastream = os.toByteArray();

        /*validate object*/
        Validate.notNull(datastream);

        return datastream;
    }
    
}
