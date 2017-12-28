package com.iso.dashboard.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The persistent class for the c_task_group database table.
 *
 */

public class TaskGroupResult implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer taskGroupId;

    private String description;

    private Boolean status;

    private String taskGroupName;

    private String color;
    
    private Integer index;

    public TaskGroupResult() {
    }
    public TaskGroupResult(CTaskGroup cTaskGroup) {
        this.color = cTaskGroup.getColor();
        this.description = cTaskGroup.getDescription();
        this.status = cTaskGroup.getStatus();
        this.taskGroupId = cTaskGroup.getTaskGroupId();
        this.taskGroupName = cTaskGroup.getTaskGroupName();
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

    public static List<TaskGroupResult> getListTaskGroupResult(List<CTaskGroup> lstCTaskGroup){
        List<TaskGroupResult> result = new ArrayList<>();
        int i = 1;
        for(CTaskGroup ctg : lstCTaskGroup){
            TaskGroupResult tgr = new TaskGroupResult(ctg);
            tgr.setIndex(i);
            i++;
        }
        return result;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
    
    
}
