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

/**
 *
 * @author Thuclt-VNPTTech
 */
@Entity
@Table(name = "C_EMPLOYEE_PROCESS")
public class EmployeeProcess implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "C_EMPLOYEE_PROCESS_ID", nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name = "C_EMPLOYEE_ID", nullable = false)
    private Employee employee;

    @Column(name = "FROM_DATE", nullable = true)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fromDate;
    @Column(name = "TO_DATE", nullable = true)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date toDate;

    @ManyToOne
    @JoinColumn(name = "C_JOB_ID", nullable = false)
    private Job job;

    @ManyToOne
    @JoinColumn(name = "C_EMPLOYEE_TYPE_ID", nullable = false)
    private EmployeeType employeeType;

    @ManyToOne
    @JoinColumn(name = "C_DEPARTMENT_ID", nullable = false)
    private Organization department;

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

    public EmployeeProcess() {
    }

    public EmployeeProcess(int id, Employee employee) {
        this.id = id;
        this.employee = employee;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public EmployeeType getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(EmployeeType employeeType) {
        this.employeeType = employeeType;
    }

    public Organization getDepartment() {
        return department;
    }

    public void setDepartment(Organization department) {
        this.department = department;
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
