package com.bitozen.wms.report.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Reza Agika Putra
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExampleDTO implements Serializable {
    
    private String cnbComponent;
    private String cnbType;
    private String cnbValueType;
    private Double cnbValue;
    
}
