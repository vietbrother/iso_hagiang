/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author VIET_BROTHER
 */
@Entity
@Table(name = "ORGANIZATION")
public class Organization implements Serializable {

    @Id
//    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "NAME", nullable = true)
    private String name;
    @Column(name = "CODE", nullable = true)
    private String code;
    @Column(name = "VALUE", nullable = true)
    private String value;
    @Column(name = "POSITION", nullable = true)
    private String position;
    @Column(name = "PARENT_ID", nullable = false)
    private int parentId;
    @Column(name = "DESCRIPTION", nullable = true)
    private String description;
    @Column(name = "STATUS", nullable = true)
    private String status;
    @Column(name = "CREATED_BY", nullable = true)
    private String createdBy;
    @Column(name = "UPDATE_BY", nullable = true)
    private String updatedBy;
    @Column(name = "TIME_UPDATE", nullable = true)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date timeUpdate;
    @Column(name = "CREATE_TIME", nullable = true)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date createTime;
    @Column(name = "LEVEL", nullable = true)
    private String level;

    public Organization() {
    }

    public Organization(String name, String code, String value, String position, int parentId) {
        this.name = name;
        this.code = code;
        this.value = value;
        this.position = position;
        this.parentId = parentId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Date getTimeUpdate() {
        return timeUpdate;
    }

    public void setTimeUpdate(Date timeUpdate) {
        this.timeUpdate = timeUpdate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Organization{" + "id=" + id + ", name=" + name + ", code=" + code + ", value=" + value + ", position=" + position + ", parentId=" + parentId + ", description=" + description + ", status=" + status + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy + ", timeUpdate=" + timeUpdate + ", createTime=" + createTime + ", level=" + level + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.id;
        hash = 59 * hash + Objects.hashCode(this.name);
        hash = 59 * hash + Objects.hashCode(this.code);
        hash = 59 * hash + Objects.hashCode(this.value);
        hash = 59 * hash + Objects.hashCode(this.position);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Organization other = (Organization) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.code, other.code)) {
            return false;
        }
        return true;
    }

    
}
