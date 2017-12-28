/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;

/**
 *
 * @author Thuclt-VNPTTech
 */
@Entity
@Table(name = "C_PROCEDURE")
public class CProcedure implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "C_PROCEDURE_ID", nullable = false)
    private int id;

    @Column(name = "CODE", nullable = false)
    private String code;
    @Column(name = "NAME", nullable = false)
    private String name;
    @Column(name = "LEVEL", nullable = false)
    private String level;
    @Column(name = "TYPE", nullable = false)
    private String type;
    @Column(name = "CONTENT", nullable = false)
    private String content;

    @Column(name = "COST", nullable = true)
    private String cost;
    @Column(name = "PROCESS_TIME", nullable = true)
    private String processTime;
    @Column(name = "ORGANIZATION_ID", nullable = true)
    private int orgId;
    @Transient
    private String orgName;

    @ManyToOne
    @JoinColumn(name = "C_FIELD_ID", nullable = false)
    private CField field;

    @Column(name = "DESCRIPTION", nullable = true)
    private String description;
    @Column(name = "IS_ACTIVE", nullable = true)
    private String isActive;
    @Column(name = "CREATED_BY", nullable = true)
    private String createdBy;
    @Column(name = "UPDATED_BY", nullable = true)
    private String updatedBy;
    @Column(name = "UPDATED", nullable = true)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date updated;
    @Column(name = "CREATED", nullable = true)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date created;

    @Transient
    private PProcess processDto;

    public CProcedure() {
    }

    public CProcedure(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public CField getField() {
        return field;
    }

    public void setField(CField field) {
        this.field = field;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getProcessTime() {
        return processTime;
    }

    public void setProcessTime(String processTime) {
        this.processTime = processTime;
    }

    public int getOrgId() {
        return orgId;
    }

    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public PProcess getProcessDto() {
        return processDto;
    }

    public void setProcessDto(PProcess processDto) {
        this.processDto = processDto;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + this.id;
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
        final CProcedure other = (CProcedure) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

}
