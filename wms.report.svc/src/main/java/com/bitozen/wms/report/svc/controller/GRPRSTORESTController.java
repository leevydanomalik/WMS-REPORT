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
 * @author HP
 */

@RestController
@RequestMapping("report")
public class GRPRSTORESTController {
   private final String GRPRSTO_CSV = "asset.jasper";
    
    @Autowired
    ReportService reportService;
    
    @ResponseBody
    @RequestMapping(value = "gr.pr.sto.csv/", method = RequestMethod.GET,
            produces = "text/csv")
    
   public byte[] generateGRPRSTOReportCSV(@RequestParam(value = "reportFormat", required = false) FileExtention reportFormat) throws GenericException{
       Map <String, Object> params = new HashMap<>();
       ByteArrayOutputStream grPrSto;
       reportService.setRptResourcePrefix("/report/");
       grPrSto = (ByteArrayOutputStream) reportService.showReportJdbcDataSourceExportToPdfTxtCsvXls(FileExtention.CSV, GRPRSTO_CSV, params);
       
    /*validate object*/
    Validate.notNull(grPrSto);
    
    /*get the byte*/
    byte[] dataStream = grPrSto.toByteArray();
    
    /*validate object*/
    Validate.notNull(dataStream);
    
    return dataStream;   
   }
}
