package com.iso.dashboard.dto;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the c_task database table.
 *
 */
@Entity
@Table(name = "p_process_his")
@NamedQuery(name = "PProcessHis.findAll", query = "SELECT c FROM PProcessHis c")
public class PProcessHis implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "P_PROCESS_ID")
    private Integer pProcessId;

    @Column(name = "FLOW_ID")
    private Integer flowId;

    @Column(name = "STEP_ID")
    private Integer stepId;

    @Column(name = "C_ACTION_ID")
    private Integer actionId;

    @Column(name = "ACTION_NAME")
    private String actionName;

    @Column(name = "PROCESS_STATE_ID")
    private Integer processStateId;

    @Column(name = "PROCESS_STATE_NAME")
    private String processStateName;

    @Column(name = "P_RESULT")
    private String result;

    @Column(name = "C_PROCEDURE_ID")
    private Integer proId;

    @Column(name = "EXECUTE_EMPLOYEE")
    private Integer executeEmployeeId;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CREATED_BY")
    private String createBy;

    @Column(name = "UPDATED_BY")
    private String updateBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATED")
    private Date createdTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATED")
    private Date updatedTime;


    public PProcessHis() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getpProcessId() {
        return pProcessId;
    }

    public void setpProcessId(Integer pProcessId) {
        this.pProcessId = pProcessId;
    }

    public Integer getFlowId() {
        return flowId;
    }

    public void setFlowId(Integer flowId) {
        this.flowId = flowId;
    }

    public Integer getStepId() {
        return stepId;
    }

    public void setStepId(Integer stepId) {
        this.stepId = stepId;
    }

    public Integer getActionId() {
        return actionId;
    }

    public void setActionId(Integer actionId) {
        this.actionId = actionId;
    }

    public Integer getProId() {
        return proId;
    }

    public void setProId(Integer proId) {
        this.proId = proId;
    }

    public Integer getExecuteEmployeeId() {
        return executeEmployeeId;
    }

    public void setExecuteEmployeeId(Integer executeEmployeeId) {
        this.executeEmployeeId = executeEmployeeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public Integer getProcessStateId() {
        return processStateId;
    }

    public void setProcessStateId(Integer processStateId) {
        this.processStateId = processStateId;
    }

    public String getProcessStateName() {
        return processStateName;
    }

    public void setProcessStateName(String processStateName) {
        this.processStateName = processStateName;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    
}
