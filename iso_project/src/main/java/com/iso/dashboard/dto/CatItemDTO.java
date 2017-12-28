
/*
 * Copyright (C) 2011 Viettel Telecom. All rights reserved.
 * VIETTEL PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.iso.dashboard.dto;

import java.util.Objects;

/**
 * @author anhmv6
 * @version 1.0
 * @since 8/25/2015 2:42 PM
 */
public class CatItemDTO {

    //Fields
    private String itemId;
    private String itemCode;
    private String itemName;
    private String itemValue;
    private String categoryId;
    private String categoryIdName;
    private String position;
    private String description;
    private String editable;
    private String parentItemId;
    private String status;
    //tamdx_start
    private String categoryCode;
    private String parentItemName;
    private String updateTime;
    private String categoryName;

    private String defaultSortField;
    

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDefaultSortField() {
        return defaultSortField;
    }

    public void setDefaultSortField(String defaultSortField) {
        this.defaultSortField = defaultSortField;
    }

    public String getParentItemName() {
        return parentItemName;
    }

    public void setParentItemName(String parentItemName) {
        this.parentItemName = parentItemName;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }
    //tamdx_end

    //Constructor
    public CatItemDTO() {
        //Ham khoi tao
    }

    public CatItemDTO(String itemId, String itemName) {
        this.itemId = itemId;
        this.itemName = itemName;
    }
    public CatItemDTO(String itemId, String itemName, String itemValue) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemValue = itemValue;
    }

    public CatItemDTO(String itemId, String itemCode, String itemName, String itemValue, String categoryId, String position, String description, String editable, String parentItemId, String status) {
        this.itemId = itemId;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.itemValue = itemValue;
        this.categoryId = categoryId;
        this.position = position;
        this.description = description;
        this.editable = editable;
        this.parentItemId = parentItemId;
        this.status = status;
    }
    //Getters and setters

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryIdName(String categoryIdName) {
        this.categoryIdName = categoryIdName;
    }

    public String getCategoryIdName() {
        return categoryIdName;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setEditable(String editable) {
        this.editable = editable;
    }

    public String getEditable() {
        return editable;
    }

    public void setParentItemId(String parentItemId) {
        this.parentItemId = parentItemId;
    }

    public String getParentItemId() {
        return parentItemId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final CatItemDTO other = (CatItemDTO) obj;
        if (!Objects.equals(this.itemId, other.itemId)) {
            return false;
        }
        return true;
    }

}
