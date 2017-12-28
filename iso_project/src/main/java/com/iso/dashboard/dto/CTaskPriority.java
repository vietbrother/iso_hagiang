package com.iso.dashboard.dto;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the c_task_piority database table.
 *
 */
@Entity
@Table(name = "c_task_priority")
@NamedQuery(name = "CTaskPriority.findAll", query = "SELECT c FROM CTaskPriority c")
public class CTaskPriority implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_priority_id")
    private Integer taskPiorityId;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "task_priority_name")
    private String taskPiorityName;

    @Column(name = "color")
    private String color;

    public CTaskPriority() {
    }

    public Integer getTaskPiorityId() {
        return this.taskPiorityId;
    }

    public void setTaskPiorityId(Integer taskPiorityId) {
        this.taskPiorityId = taskPiorityId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getTaskPiorityName() {
        return this.taskPiorityName;
    }

    public void setTaskPiorityName(String taskPiorityName) {
        this.taskPiorityName = taskPiorityName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    
}
