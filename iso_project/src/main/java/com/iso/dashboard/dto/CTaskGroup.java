package com.iso.dashboard.dto;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the c_task_group database table.
 *
 */
@Entity
@Table(name = "c_task_group")
@NamedQuery(name = "CTaskGroup.findAll", query = "SELECT c FROM CTaskGroup c")
public class CTaskGroup implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_group_id")
    private Integer taskGroupId;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "task_group_name")
    private String taskGroupName;

    @Column(name = "color")
    private String color;

    public CTaskGroup() {
    }

    public Integer getTaskGroupId() {
        return this.taskGroupId;
    }

    public void setTaskGroupId(Integer taskGroupId) {
        this.taskGroupId = taskGroupId;
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

    public String getTaskGroupName() {
        return this.taskGroupName;
    }

    public void setTaskGroupName(String taskGroupName) {
        this.taskGroupName = taskGroupName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    
}
