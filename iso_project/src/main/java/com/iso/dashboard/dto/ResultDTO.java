/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.dto;

/**
 *
 * @author VIET_BROTHER
 */
public class ResultDTO {
    private String id;
    private String key;
    private String message;

    public ResultDTO() {
    }

    public ResultDTO(String id, String key, String message) {
        this.id = id;
        this.key = key;
        this.message = message;
    }

    public ResultDTO(String key, String message) {
        this.key = key;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    
}
