package com.bitozen.wms.report.svc.integration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import com.bitozen.wms.report.common.util.RequestUtil;
import com.bitozen.wms.report.common.util.RestClientUtil;
import com.fasterxml.jackson.databind.ObjectMapper;


import lombok.extern.slf4j.Slf4j;


/**
 * @author EKSAD - Kahfi
 *
 */
@Component
@Slf4j
public class ReportIntegrationService {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private RequestUtil requestUtil;

    @Autowired
    private RestClientUtil restClientUtil;

}
