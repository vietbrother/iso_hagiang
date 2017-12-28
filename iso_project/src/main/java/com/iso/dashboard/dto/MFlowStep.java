package com.iso.dashboard.dto;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the m_flow_step database table.
 *
 */
@Entity
@Table(name = "m_flow_step")
@NamedQuery(name = "MFlowStep.findAll", query = "SELECT m FROM MFlowStep m")
public class MFlowStep implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ALLOW_ACTION")
    private String allowAction;

    @Column(name = "FLOW_ID")
    private Integer flowId;

    @Column(name = "C_ACTION_ID")
    private Integer actionId;

    @Column(name = "STEP_BRANCH")
    private Integer stepBranch;

    @Column(name = "STEP_ID")
    private Integer stepId;

    @Column(name = "STEP_INDEX")
    private Integer stepIndex;

    @Column(name = "DEF_EMPLOYEE")
    private Integer defEmp;

    @Column(name = "BACKUP_EMPLOYEE")
    private Integer backEmp;

    @Column(name = "SUPPORT_EMPLOYEE")
    private Integer subEmp;

    public MFlowStep() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAllowAction() {
        return allowAction;
    }

    public void setAllowAction(String allowAction) {
        this.allowAction = allowAction;
    }

    public Integer getFlowId() {
        return flowId;
    }

    public void setFlowId(Integer flowId) {
        this.flowId = flowId;
    }

    public Integer getStepBranch() {
        return stepBranch;
    }

    public void setStepBranch(Integer stepBranch) {
        this.stepBranch = stepBranch;
    }

    public Integer getStepId() {
        return stepId;
    }

    public void setStepId(Integer stepId) {
        this.stepId = stepId;
    }

    public Integer getStepIndex() {
        return stepIndex;
    }

    public void setStepIndex(Integer stepIndex) {
        this.stepIndex = stepIndex;
    }

    public Integer getDefEmp() {
        return defEmp;
    }

    public void setDefEmp(Integer defEmp) {
        this.defEmp = defEmp;
    }

    public Integer getBackEmp() {
        return backEmp;
    }

    public void setBackEmp(Integer backEmp) {
        this.backEmp = backEmp;
    }

    public Integer getSubEmp() {
        return subEmp;
    }

    public void setSubEmp(Integer subEmp) {
        this.subEmp = subEmp;
    }

    public Integer getActionId() {
        return actionId;
    }

    public void setActionId(Integer actionId) {
        this.actionId = actionId;
    }

    
}
