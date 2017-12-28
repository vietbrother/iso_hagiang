package com.iso.dashboard.dto;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "c_document")
public class DocumentDTO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "document_code")
    private String fileCode;
    @Column(name = "document_name")
    private String fileName;
    @Column(name = "document_type")
    private String fileType;
    @Column(name = "security_level")
    private String securityLevel;
    @Column(name = "created_by")
    private String createdBy;
    @Column(name = "status")
    private String status;
    @Column(name = "part_storage_time")
    private int partStorageTime;
    @Column(name = "department_storage_time")
    private int departmentStorageTime;                
    @Column(name = "document_url")
    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getSecurityLevel() {
        return securityLevel;
    }

    public void setSecurityLevel(String securityLevel) {
        this.securityLevel = securityLevel;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPartStorageTime() {
        return partStorageTime;
    }

    public void setPartStorageTime(int partStorageTime) {
        this.partStorageTime = partStorageTime;
    }

    public int getDepartmentStorageTime() {
        return departmentStorageTime;
    }

    public void setDepartmentStorageTime(int departmentStorageTime) {
        this.departmentStorageTime = departmentStorageTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

        
}
