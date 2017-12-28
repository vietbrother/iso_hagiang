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
@Table(name = "C_ASSET")
public class Asset implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "C_ASSET_ID", nullable = false)
    private int id;

    @Column(name = "CODE", nullable = true)
    private String code;
    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "NUMBER", nullable = false)
    private int number;

    @ManyToOne
    @JoinColumn(name = "C_UNIT_ID", nullable = false)
    private CUnit cUnit;

    @ManyToOne
    @JoinColumn(name = "C_ASSET_TYPE_ID", nullable = false)
    private AssetType assetType;

    @ManyToOne
    @JoinColumn(name = "C_ASSET_GROUP_ID", nullable = false)
    private AssetGroup assetGroup;

    @ManyToOne
    @JoinColumn(name = "C_ASSET_CLASS_ID", nullable = false)
    private AssetClass assetClass;

    @ManyToOne
    @JoinColumn(name = "C_ORGANIZATION_ID", nullable = false)
    private Organization organization;

    @ManyToOne
    @JoinColumn(name = "C_EMPLOYEE_ID", nullable = false)
    private Employee employee;

    @Column(name = "DESIGN", nullable = true)
    private String design;
    @Column(name = "SEATS", nullable = true)
    private int seats;
    @Column(name = "DEPRECIATION_DATE", nullable = true)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date depreciationDate;

    @ManyToOne
    @JoinColumn(name = "C_PROVIDER_ID", nullable = false)
    private Provider provider;

    @Column(name = "CONTENT", nullable = true)
    private String content;
    @Column(name = "URL_IMAGE", nullable = true)
    private String urlImage;
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

    public Asset() {
    }

    public Asset(int id, String name) {
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public CUnit getcUnit() {
        return cUnit;
    }

    public void setcUnit(CUnit cUnit) {
        this.cUnit = cUnit;
    }

    public AssetType getAssetType() {
        return assetType;
    }

    public void setAssetType(AssetType assetType) {
        this.assetType = assetType;
    }

    public AssetGroup getAssetGroup() {
        return assetGroup;
    }

    public void setAssetGroup(AssetGroup assetGroup) {
        this.assetGroup = assetGroup;
    }

    public AssetClass getAssetClass() {
        return assetClass;
    }

    public void setAssetClass(AssetClass assetClass) {
        this.assetClass = assetClass;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getDesign() {
        return design;
    }

    public void setDesign(String design) {
        this.design = design;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public Date getDepreciationDate() {
        return depreciationDate;
    }

    public void setDepreciationDate(Date depreciationDate) {
        this.depreciationDate = depreciationDate;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
