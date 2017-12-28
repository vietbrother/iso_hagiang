package com.iso.dashboard.dto;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the c_task_history database table.
 *
 */
@Entity
@Table(name = "c_task_history")
@NamedQuery(name = "CTaskHistory.findAll", query = "SELECT c FROM CTaskHistory c")
public class CTaskHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "report_content")
    private String reportContent;

    @Column(name = "inserted_by")
    private Integer insertedBy;

    @Column(name = "report_rate")
    private Integer reportRate;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_time", insertable = false)
    private Date updatedTime;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hist_id")
    private Integer seq;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "inserted_time", insertable = false, updatable = false)
    private Date insertedTime;

    @Column(name = "task_id")
    private Integer taskId;

    @Column(name = "report_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date reportTime;

    @Column(name = "check_result")
    private String checkResult;

    @Column(name = "check_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date checkTime;

    @Column(name = "check_rate")
    private Integer checkRate;

    public CTaskHistory() {
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public Date getInsertedTime() {
        return insertedTime;
    }

    public void setInsertedTime(Date insertedTime) {
        this.insertedTime = insertedTime;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public Integer getInsertedBy() {
        return this.insertedBy;
    }

    public void setInsertedBy(Integer insertedBy) {
        this.insertedBy = insertedBy;
    }

    public Date getUpdatedTime() {
        return this.updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getReportContent() {
        return reportContent;
    }

    public void setReportContent(String reportContent) {
        this.reportContent = reportContent;
    }

    public Integer getReportRate() {
        return reportRate;
    }

    public void setReportRate(Integer reportRate) {
        this.reportRate = reportRate;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public Integer getCheckRate() {
        return checkRate;
    }

    public void setCheckRate(Integer checkRate) {
        this.checkRate = checkRate;
    }

}
