package com.bitozen.wms.report.svc.controller;

import com.bitozen.wms.common.dto.GenericResponseDTO;
import com.bitozen.wms.common.status.ResponseStatus;
import com.bitozen.wms.elasticsearch.dto.trx.dsh.KPIRangePeriodGetDTO;
import com.bitozen.wms.elasticsearch.dto.trx.dsh.KPIRangePeriodRequestDTO;
import com.bitozen.wms.report.common.exception.GenericException;
import com.bitozen.wms.report.common.type.FileExtention;
import com.bitozen.wms.report.common.util.RequestUtil;
import com.bitozen.wms.report.common.util.RestClientUtil;
import com.bitozen.wms.report.svc.ReportService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
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
public class KeyPerformanceIndicatorRESTController {
    
    @Autowired
    private RequestUtil requestUtil;

    @Autowired
    private RestClientUtil restClientUtil;
    
    @Autowired
    private ObjectMapper objectMapper; 

    @Value("${wms.elastic.url}")    
    private String ELASTIC_URL;   
    
    private final String KPI = "kpi.jasper";
     
    @Autowired 
    ReportService reportService;
    
    @ResponseBody
    @RequestMapping(value = "/key.performance.indicator", method = RequestMethod.POST, produces = "text/csv")
    public byte[] generateKPICSV(@RequestParam(value = "reportFormat", required = false) FileExtention reportFormat, @RequestBody KPIRangePeriodRequestDTO dto) throws GenericException {
        List<KPIRangePeriodGetDTO> data = findKpi(dto);
        Map<String, Object> params = new HashMap<>();
        ByteArrayOutputStream os;
        reportService.setRptResourcePrefix("/report/");
        os = (ByteArrayOutputStream) reportService.showReportBeanDataSourceExportToPdfTxtCsvXls(FileExtention.CSV, KPI, params, data);

        /*validate object*/
        Validate.notNull(os);

        /*get the byte*/
        byte[] datastream = os.toByteArray();

        /*validate object*/
        Validate.notNull(datastream);

        return datastream;
    }   
    
    public List<KPIRangePeriodGetDTO> findKpi(KPIRangePeriodRequestDTO dto) {
        ResponseEntity<GenericResponseDTO<List<KPIRangePeriodGetDTO>>> data = restClientUtil.restServiceExchange(
                ELASTIC_URL,
                "/getAllOptimizedOperationKPIValuesInRangeOfTime",
                HttpMethod.POST,
                new HttpEntity<>(dto, requestUtil.getPreFormattedRequestWithToken().getHeaders()),
                GenericResponseDTO.class);
        if (data.getBody().getStatus().equals(ResponseStatus.S)) {
            return data.getBody().getData();
        }
        return new ArrayList<>();
    }
    
}
