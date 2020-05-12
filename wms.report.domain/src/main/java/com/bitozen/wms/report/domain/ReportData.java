package com.bitozen.wms.report.domain;

import com.bitozen.wms.report.domain.common.CreationalSpecification;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author amyBachtiar
 */
@Entity
@Table(name = "RPT_ReportData")
public class ReportData implements Serializable {

    @Id
    @GenericGenerator(name = "sequence_dep_id", strategy = "com.bitozen.wms.report.domain.common.HibernateIDGenerator")
    @GeneratedValue(generator = "sequence_dep_id")
    private Long id;

    private String reportDataID;
    private String reportTemplateID;

    @Embedded
    private CreationalSpecification reportDataCreational;

    public ReportData() {
    }

    public ReportData(String reportDataID, String reportTemplateID, CreationalSpecification reportDataCreational) {
        this.reportDataID = reportDataID;
        this.reportTemplateID = reportTemplateID;
        this.reportDataCreational = reportDataCreational;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReportDataID() {
        return reportDataID;
    }

    public void setReportDataID(String reportDataID) {
        this.reportDataID = reportDataID;
    }

    public String getReportTemplateID() {
        return reportTemplateID;
    }

    public void setReportTemplateID(String reportTemplateID) {
        this.reportTemplateID = reportTemplateID;
    }

    public CreationalSpecification getReportDataCreational() {
        return reportDataCreational;
    }

    public void setReportDataCreational(CreationalSpecification reportDataCreational) {
        this.reportDataCreational = reportDataCreational;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.reportDataID);
        hash = 31 * hash + Objects.hashCode(this.reportTemplateID);
        hash = 31 * hash + Objects.hashCode(this.reportDataCreational);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ReportData other = (ReportData) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
