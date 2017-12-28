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
@Table(name = "C_EMPLOYEES")
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "C_EMPLOYEE_ID", nullable = false)
    private int id;

    @Column(name = "EMPLOYEE_CODE")
    private String employeeCode;

    @ManyToOne
    @JoinColumn(name = "EMPLOYEE_TYPE_ID", nullable = false)
    private EmployeeType employeeType;

    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "BIRTHDAY")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date birthday;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "MOBILE")
    private String mobile;
    @Column(name = "ADDRESS")
    private String address;
    @Column(name = "IMAGE_URL")
    private String imageUrl;
    @Column(name = "HIRE_FROM_DATE")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fromDate;
    @Column(name = "HIRE_TO_DATE")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date toDate;
    @Column(name = "SALARY")
    private String salary;
    @Column(name = "COMMISSION_PCT")
    private String commistionPct;

    @ManyToOne
    @JoinColumn(name = "DEPARTMENT_ID", nullable = false)
    private Organization department;

    @ManyToOne
    @JoinColumn(name = "JOB_ID", nullable = false)
    private Job job;

    @Column(name = "IS_ACTIVE")
    private int isActive;

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

    @Column(name = "ROLE", nullable = true)
    private String role;
    @Column(name = "USERNAME", nullable = true)
    private String userName;
    @Column(name = "PASSWORD", nullable = true)
    private String password;
    @Column(name = "SEX", nullable = true)
    private boolean male;

    public Employee() {
    }

    public Employee(int id, String employeeCode, String firstName, String lastName, Date birthday) {
        this.id = id;
        this.employeeCode = employeeCode;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public EmployeeType getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(EmployeeType employeeType) {
        this.employeeType = employeeType;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getCommistionPct() {
        return commistionPct;
    }

    public void setCommistionPct(String commistionPct) {
        this.commistionPct = commistionPct;
    }

    public Organization getDepartment() {
        return department;
    }

    public void setDepartment(Organization department) {
        this.department = department;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + this.id;
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
        final Employee other = (Employee) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

}
