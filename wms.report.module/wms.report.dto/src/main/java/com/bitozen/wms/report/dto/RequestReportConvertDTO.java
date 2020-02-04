package com.bitozen.wms.report.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestReportConvertDTO implements Serializable {

    private String templateID;
    private Map<String, Object> datas;

    @JsonIgnore
    public RequestReportConvertDTO getInstance(){
        Map<String, Object> datas = new HashMap<>();
        return new RequestReportConvertDTO("Test-001", datas);
    }
}
