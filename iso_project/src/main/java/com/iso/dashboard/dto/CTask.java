package com.iso.dashboard.dto;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the c_task database table.
 *
 */
@Entity
@Table(name = "c_task")
@NamedQuery(name = "CTask.findAll", query = "SELECT c FROM CTask c")
public class CTask implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Integer taskId;

    @Column(name = "assigned_by")
    private Integer assignedBy;

    @Column(name = "budget")
    private Float budget;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "currency")
    private String currency;

    @Column(name = "department_id")
    private Integer departmentId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_time")
    private Date endTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "insert_time")
    private Date insertTime;

    @Column(name = "reviewed_by")
    private Integer reviewedBy;

    @Column(name = "reviewed_content")
    private String reviewedContent;

    @Column(name = "reviewed_result")
    private Integer reviewedResult;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "reviewed_time")
    private Date reviewedTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "status")
    private Integer status;

    @Column(name = "task_content")
    private String taskContent;

    @Column(name = "task_group_id")
    private Integer taskGroupId;

    @Column(name = "task_name")
    private String taskName;

    @Column(name = "task_parent_id")
    private Integer taskParentId;

    @Column(name = "task_piority_id")
    private Integer taskPiorityId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @Column(name = "rate")
    private Integer rate;

    public CTask() {
    }

    public Integer getTaskId() {
        return this.taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getAssignedBy() {
        return this.assignedBy;
    }

    public void setAssignedBy(Integer assignedBy) {
        this.assignedBy = assignedBy;
    }

    public Float getBudget() {
        return this.budget;
    }

    public void setBudget(Float budget) {
        this.budget = budget;
    }

    public Integer getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getDepartmentId() {
        return this.departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Date getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getInsertTime() {
        return this.insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public Integer getReviewedBy() {
        return this.reviewedBy;
    }

    public void setReviewedBy(Integer reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

    public String getReviewedContent() {
        return this.reviewedContent;
    }

    public void setReviewedContent(String reviewedContent) {
        this.reviewedContent = reviewedContent;
    }

    public Integer getReviewedResult() {
        return this.reviewedResult;
    }

    public void setReviewedResult(Integer reviewedResult) {
        this.reviewedResult = reviewedResult;
    }

    public Date getReviewedTime() {
        return this.reviewedTime;
    }

    public void setReviewedTime(Date reviewedTime) {
        this.reviewedTime = reviewedTime;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Integer getStatus() {
        return this.status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTaskContent() {
        return this.taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    public Integer getTaskGroupId() {
        return this.taskGroupId;
    }

    public void setTaskGroupId(Integer taskGroupId) {
        this.taskGroupId = taskGroupId;
    }

    public String getTaskName() {
        return this.taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Integer getTaskParentId() {
        return this.taskParentId;
    }

    public void setTaskParentId(Integer taskParentId) {
        this.taskParentId = taskParentId;
    }

    public Integer getTaskPiorityId() {
        return this.taskPiorityId;
    }

    public void setTaskPiorityId(Integer taskPiorityId) {
        this.taskPiorityId = taskPiorityId;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

}
