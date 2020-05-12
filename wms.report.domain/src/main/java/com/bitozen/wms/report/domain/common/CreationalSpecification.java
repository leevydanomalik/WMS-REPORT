package com.bitozen.wms.report.domain.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Embeddable
public class CreationalSpecification implements Serializable{
    private String createdBy;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    private String modifiedBy;
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedDate;

    public CreationalSpecification() {
    }

    public CreationalSpecification(String createdBy, Date createdDate, String modifiedBy, Date modifiedDate) {
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.modifiedBy = modifiedBy;
        this.modifiedDate = modifiedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @JsonIgnore
    public CreationalSpecification getInstance(){
        return new CreationalSpecification("SYSTEM", new Date(), null, null);
    }
}
