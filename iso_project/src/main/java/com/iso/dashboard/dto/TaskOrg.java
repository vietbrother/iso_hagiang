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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;

/**
 *
 * @author VIET_BROTHER
 */
@javax.persistence.Entity(name = "CATA_USER")
public class TaskOrg implements Serializable {

    @Id
//    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int taskId;

    @Column(name = "TASK_NAME", nullable = true)
    private String taskName;

    @Column(name = "TASK_GROUP_ID", nullable = true)
    private int task_group_id;

    @Column(name = "department_id", nullable = true)
    private int department_id;

    @Column(name = "start_time", nullable = true)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date start_time;

    @Column(name = "end_time", nullable = true)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date end_time;

    @Column(name = "budget", nullable = true)
    private float budget;

    @Column(name = "currency", nullable = true)
    private String currency;

    @Column(name = "task_piority_id", nullable = true)
    private int task_piority_id;

    @Column(name = "task_content", nullable = true)
    private String task_content;

    @Column(name = "attachment", nullable = true)
    private String attachment;

    @Column(name = "process_user_id", nullable = true)
    private int process_user_id;

    @Column(name = "progress", nullable = true)
    private int progress;

    @Column(name = "status", nullable = true)
    private int status;

    @Column(name = "task_parent_id", nullable = true)
    private int task_parent_id;

    @Column(name = "insert_time", nullable = true)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date insert_time;

    @Column(name = "update_time", nullable = true)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date update_time;

    @Column(name = "inserted_user_id", nullable = true)
    private int inserted_user_id;

    @Column(name = "updated_user_id", nullable = true)
    private int updated_user_id;

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getTask_group_id() {
        return task_group_id;
    }

    public void setTask_group_id(int task_group_id) {
        this.task_group_id = task_group_id;
    }

    public int getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(int department_id) {
        this.department_id = department_id;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public float getBudget() {
        return budget;
    }

    public void setBudget(float budget) {
        this.budget = budget;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getTask_piority_id() {
        return task_piority_id;
    }

    public void setTask_piority_id(int task_piority_id) {
        this.task_piority_id = task_piority_id;
    }

    public String getTask_content() {
        return task_content;
    }

    public void setTask_content(String task_content) {
        this.task_content = task_content;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public int getProcess_user_id() {
        return process_user_id;
    }

    public void setProcess_user_id(int process_user_id) {
        this.process_user_id = process_user_id;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTask_parent_id() {
        return task_parent_id;
    }

    public void setTask_parent_id(int task_parent_id) {
        this.task_parent_id = task_parent_id;
    }

    public Date getInsert_time() {
        return insert_time;
    }

    public void setInsert_time(Date insert_time) {
        this.insert_time = insert_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }

    public int getInserted_user_id() {
        return inserted_user_id;
    }

    public void setInserted_user_id(int inserted_user_id) {
        this.inserted_user_id = inserted_user_id;
    }

    public int getUpdated_user_id() {
        return updated_user_id;
    }

    public void setUpdated_user_id(int updated_user_id) {
        this.updated_user_id = updated_user_id;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + this.taskId;
        hash = 47 * hash + Objects.hashCode(this.taskName);
        hash = 47 * hash + this.task_group_id;
        hash = 47 * hash + this.department_id;
        hash = 47 * hash + Objects.hashCode(this.start_time);
        hash = 47 * hash + Objects.hashCode(this.end_time);
        hash = 47 * hash + Float.floatToIntBits(this.budget);
        hash = 47 * hash + Objects.hashCode(this.currency);
        hash = 47 * hash + this.task_piority_id;
        hash = 47 * hash + Objects.hashCode(this.task_content);
        hash = 47 * hash + Objects.hashCode(this.attachment);
        hash = 47 * hash + this.process_user_id;
        hash = 47 * hash + this.progress;
        hash = 47 * hash + this.status;
        hash = 47 * hash + this.task_parent_id;
        hash = 47 * hash + Objects.hashCode(this.insert_time);
        hash = 47 * hash + Objects.hashCode(this.update_time);
        hash = 47 * hash + this.inserted_user_id;
        hash = 47 * hash + this.updated_user_id;
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
        final TaskOrg other = (TaskOrg) obj;
        if (this.taskId != other.taskId) {
            return false;
        }
        if (!Objects.equals(this.taskName, other.taskName)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TaskOrg{" + "taskId=" + taskId + ", taskName=" + taskName + ", task_group_id=" + task_group_id + ", department_id=" + department_id + ", start_time=" + start_time + ", end_time=" + end_time + ", budget=" + budget + ", currency=" + currency + ", task_piority_id=" + task_piority_id + ", task_content=" + task_content + ", attachment=" + attachment + ", process_user_id=" + process_user_id + ", progress=" + progress + ", status=" + status + ", task_parent_id=" + task_parent_id + ", insert_time=" + insert_time + ", update_time=" + update_time + ", inserted_user_id=" + inserted_user_id + ", updated_user_id=" + updated_user_id + '}';
    }

    
}
