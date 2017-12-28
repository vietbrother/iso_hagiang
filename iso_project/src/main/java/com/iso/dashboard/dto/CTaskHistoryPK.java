/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iso.dashboard.dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author TUYENNV1
 */
@Embeddable
public class CTaskHistoryPK implements Serializable {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "inserted_time")
    private Date insertedTime;
    @Column(name = "hist_type")
    private Integer histType;
    @Column(name = "task_id")
    private Integer taskId;

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Date getInsertedTime() {
        return insertedTime;
    }

    public void setInsertedTime(Date insertedTime) {
        this.insertedTime = insertedTime;
    }

    public Integer getHistType() {
        return histType;
    }

    public void setHistType(Integer histType) {
        this.histType = histType;
    }

}
