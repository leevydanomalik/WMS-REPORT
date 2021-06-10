package com.bitozen.wms.report.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author ASUS
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GRVSPRDTO implements Serializable {
    
    private String plantname;
    private String plantid;
    private Double prdrum;
    private Double prbox;
    private Double prpail;
    private Double grdrum;
    private Double grbox;
    private Double grpail;
    
}
