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
@Table(name = "C_STEP")
public class CStep implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STEP_ID", nullable = false)
    private int id;

    @Column(name = "CODE", nullable = true)
    private String code;
    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "time_execute", nullable = true)
    private String timeExecute;

    @Column(name = "position", nullable = true)
    private String position;

    @Column(name = "DESCRIPTION", nullable = true)
    private String description;

    @Column(name = "CREATED_BY", nullable = true)
    private String createdBy;
    @Column(name = "UPDATED_BY", nullable = true)
    private String updatedBy;

    @Column(name = "UPDATED_TIME", nullable = true)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date updatedTime;

    @Column(name = "created_time", nullable = true)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date createdTime;

    @Transient
    private String branchName;

    @Transient
    private Integer banchId;

    public CStep() {
    }

    public CStep(int id, String name) {
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

    public String getTimeExecute() {
        return timeExecute;
    }

    public void setTimeExecute(String timeExecute) {
        this.timeExecute = timeExecute;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
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
        final CStep other = (CStep) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public Integer getBanchId() {
        return banchId;
    }

    public void setBanchId(Integer banchId) {
        this.banchId = banchId;
    }

}
