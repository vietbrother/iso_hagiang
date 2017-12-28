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
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author Thuclt-VNPTTech
 */
@Entity
@Table(name = "C_JOB")
//@javax.persistence.Entity(name = "C_JOB_TEST");
public class Job implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "C_JOB_ID", nullable = false)
    private int id;

    @Column(name = "JOB_TITLE", nullable = false)
    private String jobTitle;
    @Column(name = "SALARY_GLONE", nullable = true)
    private String salaryGlone;
    @Column(name = "SALARY_WAGE", nullable = true)
    private String salaryWage;
    @Column(name = "MIN_SALARY", nullable = true)
    private String minSalary;
    @Column(name = "MAX_SALARY", nullable = true)
    private String maxSalary;
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

    public Job(int id, String jobTitle, String salaryGlone, String salaryWage) {
        this.id = id;
        this.jobTitle = jobTitle;
        this.salaryGlone = salaryGlone;
        this.salaryWage = salaryWage;
    }

    public Job() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getSalaryGlone() {
        return salaryGlone;
    }

    public void setSalaryGlone(String salaryGlone) {
        this.salaryGlone = salaryGlone;
    }

    public String getSalaryWage() {
        return salaryWage;
    }

    public void setSalaryWage(String salaryWage) {
        this.salaryWage = salaryWage;
    }

    public String getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(String minSalary) {
        this.minSalary = minSalary;
    }

    public String getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(String maxSalary) {
        this.maxSalary = maxSalary;
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

}
