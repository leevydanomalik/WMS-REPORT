package com.bitozen.wms.report.domain;

import com.bitozen.wms.report.domain.common.CreationalSpecification;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.Objects;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

/**
 *
 * @author kori
 */
@Entity
@Table(name = "RPT_ReportTemplate")
public class ReportTemplate implements Serializable {

    @Id
    @GenericGenerator(name = "sequence_dep_id", strategy = "com.bitozen.wms.report.domain.common.HibernateIDGenerator")
    @GeneratedValue(generator = "sequence_dep_id")
    private Long id;

    private String reportTemplateID;
    private String reportTemplateURL;

    @Embedded
    private CreationalSpecification reportTemplateCreational;

    public ReportTemplate() {
    }

    public ReportTemplate(String reportTemplateID, String reportTemplateURL, CreationalSpecification reportTemplateCreational) {
        this.reportTemplateID = reportTemplateID;
        this.reportTemplateURL = reportTemplateURL;
        this.reportTemplateCreational = reportTemplateCreational;
    }

    public ReportTemplate(String reportTemplateID, Path path, CreationalSpecification toDomain) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getReportTemplateID() {
        return reportTemplateID;
    }

    public void setReportTemplateID(String reportTemplateID) {
        this.reportTemplateID = reportTemplateID;
    }

    public String getReportTemplateURL() {
        return reportTemplateURL;
    }

    public void setReportTemplateURL(String reportTemplateURL) {
        this.reportTemplateURL = reportTemplateURL;
    }

    public CreationalSpecification getReportTemplateCreational() {
        return reportTemplateCreational;
    }

    public void setReportTemplateCreational(CreationalSpecification reportTemplateCreational) {
        this.reportTemplateCreational = reportTemplateCreational;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + Objects.hashCode(this.reportTemplateID);
        hash = 47 * hash + Objects.hashCode(this.reportTemplateURL);
        hash = 47 * hash + Objects.hashCode(this.reportTemplateCreational);
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
        final ReportTemplate other = (ReportTemplate) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
