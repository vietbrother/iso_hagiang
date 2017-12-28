package com.iso.dashboard.dto;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the c_task_review database table.
 *
 */
@Entity
@Table(name = "c_task_review")
@NamedQuery(name = "CTaskReview.findAll", query = "SELECT c FROM CTaskReview c")
public class CTaskReview implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "criteria_name")
    private String criteriaName;

    @Column(name = "mark")
    private Integer mark;

    @Column(name = "result")
    private Integer result;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "reviewed_time")
    private Date reviewedTime;

    @Column(name = "unsuitable")
    private Integer unsuitable;

    @Id
    private CTaskReviewPK id;

    public CTaskReviewPK getId() {
        return id;
    }

    public void setId(CTaskReviewPK id) {
        this.id = id;
    }

    public CTaskReview() {
    }

    public String getCriteriaName() {
        return this.criteriaName;
    }

    public void setCriteriaName(String criteriaName) {
        this.criteriaName = criteriaName;
    }

    public Integer getMark() {
        return this.mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public Integer getResult() {
        return this.result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public Date getReviewedTime() {
        return this.reviewedTime;
    }

    public void setReviewedTime(Date reviewedTime) {
        this.reviewedTime = reviewedTime;
    }

    public Integer getUnsuitable() {
        return this.unsuitable;
    }

    public void setUnsuitable(Integer unsuitable) {
        this.unsuitable = unsuitable;
    }

}
