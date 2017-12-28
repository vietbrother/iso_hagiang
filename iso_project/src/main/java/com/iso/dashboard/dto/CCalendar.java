package com.iso.dashboard.dto;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the c_calendar database table.
 *
 */
@Entity
@Table(name = "c_calendar")
@NamedQuery(name = "CCalendar.findAll", query = "SELECT c FROM CCalendar c")
public class CCalendar implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "calendar_id")
    private Integer calendarId;

    @Column(name = "color")
    private String color;

    @Column(name = "calendar_name")
    private String calendarName;

    @Column(name = "description")
    private String description;

    @Column(name = "inserted_by")
    private Integer insertedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "inserted_time")
    private Date insertedTime;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "updated_by")
    private Integer updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_time")
    private Date updatedTime;

    @Column(name = "working_date")
    private Integer workingDate;

    public CCalendar() {
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getCalendarId() {
        return this.calendarId;
    }

    public void setCalendarId(Integer calendarId) {
        this.calendarId = calendarId;
    }

    public String getCalendarName() {
        return this.calendarName;
    }

    public void setCalendarName(String calendarName) {
        this.calendarName = calendarName;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getInsertedBy() {
        return this.insertedBy;
    }

    public void setInsertedBy(Integer insertedBy) {
        this.insertedBy = insertedBy;
    }

    public Date getInsertedTime() {
        return this.insertedTime;
    }

    public void setInsertedTime(Date insertedTime) {
        this.insertedTime = insertedTime;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Integer getUpdatedBy() {
        return this.updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedTime() {
        return this.updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Integer getWorkingDate() {
        return this.workingDate;
    }

    public void setWorkingDate(Integer workingDate) {
        this.workingDate = workingDate;
    }

}
