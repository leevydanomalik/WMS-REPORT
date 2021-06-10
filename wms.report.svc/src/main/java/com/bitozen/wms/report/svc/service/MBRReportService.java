package com.bitozen.wms.report.svc.service;

import com.bitozen.wms.common.dto.GenericResponseDTO;
import com.bitozen.wms.common.type.ProjectType;
import com.bitozen.wms.common.util.LogOpsUtil;
import com.bitozen.wms.report.domain.repository.MBRRepository;
import com.bitozen.wms.report.dto.GRVSPRDTO;
import com.bitozen.wms.report.dto.POVSPRDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import com.netflix.hystrix.exception.HystrixTimeoutException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 *
 * @author ASUS
 */
@Service
@Slf4j
public class MBRReportService {
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private MBRRepository repository;
    
    @HystrixCommand(fallbackMethod = "defaultGetPOVSPRFallback")
    public GenericResponseDTO<List<POVSPRDTO>> getPOVSPR() throws ParseException, JsonProcessingException {
        try {
            List<Object[]> povspr = repository.findPOvsPR();

            List<POVSPRDTO> povsprDTOs = new ArrayList<>();
            povspr.stream().map((Object[] temp) -> {
                POVSPRDTO dto = new POVSPRDTO();
                dto.setPlantname(temp[0] != null ? String.valueOf(temp[0]) : "");
                dto.setPlantid(temp[1] != null ?  String.valueOf(temp[1]) : "");
                dto.setPrdrum(temp[2] != null ?  Double.valueOf(String.valueOf(temp[2])) : 0d);
                dto.setPrbox(temp[3] != null ?  Double.valueOf(String.valueOf(temp[3])) : 0d);
                dto.setPrpail(temp[4] != null ?  Double.valueOf(String.valueOf(temp[4])) : 0d);
                dto.setPodrum(temp[5] != null ?  Double.valueOf(String.valueOf(temp[5])) : 0d);
                dto.setPobox(temp[6] != null ?  Double.valueOf(String.valueOf(temp[6])) : 0d);
                dto.setPopail(temp[7] != null ?  Double.valueOf(String.valueOf(temp[7])) : 0d);
                return dto;
            }).forEachOrdered((dto) -> {
                povsprDTOs.add(dto);
            });
            if (povsprDTOs.isEmpty()) {
                log.info(objectMapper.writeValueAsString(
                        LogOpsUtil.getErrorResponse(ProjectType.CQRS, "PO VS PR", new java.util.Date(), "Query", "204", "No Data Found")));
                return new GenericResponseDTO().noDataFoundResponse();
            }
            log.info(objectMapper.writeValueAsString(LogOpsUtil.getLogResponse(
                    ProjectType.CQRS, "PO VS PR", new java.util.Date(), "Query", new GenericResponseDTO().successResponse().getCode(),
                    new GenericResponseDTO().successResponse().getMessage())));
            return new GenericResponseDTO().successResponse(povsprDTOs);
        } catch (Exception e) {
            log.info(e.getMessage());
            try {
                log.info(objectMapper.writeValueAsString(
                        LogOpsUtil.getErrorResponse(ProjectType.CQRS, "PO VS PR", new java.util.Date(), "Query", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), e.getStackTrace())));
            } catch (JsonProcessingException ex) {
                log.info(ex.getMessage());
            }
            return new GenericResponseDTO().errorResponse(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), e.getLocalizedMessage());
        }

    }

    private GenericResponseDTO<List<POVSPRDTO>> defaultGetPOVSPRFallback(Throwable e) throws IOException {
        return new GenericResponseDTO<List<POVSPRDTO>>().errorResponse(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                e instanceof HystrixTimeoutException ? "Connection Timeout. Please Try Again Later"
                        : e instanceof HystrixBadRequestException ? "Bad Request. Please recheck submitted data" : e.getLocalizedMessage());
    }
    
    @HystrixCommand(fallbackMethod = "defaultGetGRVSPRFallback")
    public GenericResponseDTO<List<GRVSPRDTO>> getGRVSPR() throws ParseException, JsonProcessingException {
        try {
            List<Object[]> grvspr = repository.findGRvsPR();

            List<GRVSPRDTO> grvsprDTOs = new ArrayList<>();
            grvspr.stream().map((Object[] temp) -> {
                GRVSPRDTO dto = new GRVSPRDTO();
                dto.setPlantname(temp[0] != null ? String.valueOf(temp[0]) : "");
                dto.setPlantid(temp[1] != null ?  String.valueOf(temp[1]) : "");
                dto.setPrdrum(temp[2] != null ?  Double.valueOf(String.valueOf(temp[2])) : 0d);
                dto.setPrbox(temp[3] != null ?  Double.valueOf(String.valueOf(temp[3])) : 0d);
                dto.setPrpail(temp[4] != null ?  Double.valueOf(String.valueOf(temp[4])) : 0d);
                dto.setGrdrum(temp[5] != null ?  Double.valueOf(String.valueOf(temp[5])) : 0d);
                dto.setGrbox(temp[6] != null ?  Double.valueOf(String.valueOf(temp[6])) : 0d);
                dto.setGrpail(temp[7] != null ?  Double.valueOf(String.valueOf(temp[7])) : 0d);
                return dto;
            }).forEachOrdered((dto) -> {
                grvsprDTOs.add(dto);
            });
            if (grvsprDTOs.isEmpty()) {
                log.info(objectMapper.writeValueAsString(
                        LogOpsUtil.getErrorResponse(ProjectType.CQRS, "GR VS PR", new java.util.Date(), "Query", "204", "No Data Found")));
                return new GenericResponseDTO().noDataFoundResponse();
            }
            log.info(objectMapper.writeValueAsString(LogOpsUtil.getLogResponse(
                    ProjectType.CQRS, "GR VS PR", new java.util.Date(), "Query", new GenericResponseDTO().successResponse().getCode(),
                    new GenericResponseDTO().successResponse().getMessage())));
            return new GenericResponseDTO().successResponse(grvsprDTOs);
        } catch (Exception e) {
            log.info(e.getMessage());
            try {
                log.info(objectMapper.writeValueAsString(
                        LogOpsUtil.getErrorResponse(ProjectType.CQRS, "GR VS PR", new java.util.Date(), "Query", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), e.getStackTrace())));
            } catch (JsonProcessingException ex) {
                log.info(ex.getMessage());
            }
            return new GenericResponseDTO().errorResponse(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), e.getLocalizedMessage());
        }

    }
    
    private GenericResponseDTO<List<GRVSPRDTO>> defaultGetGRVSPRFallback(Throwable e) throws IOException {
        return new GenericResponseDTO<List<GRVSPRDTO>>().errorResponse(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()),
                e instanceof HystrixTimeoutException ? "Connection Timeout. Please Try Again Later"
                        : e instanceof HystrixBadRequestException ? "Bad Request. Please recheck submitted data" : e.getLocalizedMessage());
    }
    
}
