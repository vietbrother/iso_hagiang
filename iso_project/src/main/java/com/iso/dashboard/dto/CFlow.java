package com.iso.dashboard.dto;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * The persistent class for the c_flow database table.
 *
 */
@Entity
@Table(name = "c_flow")
@NamedQuery(name = "CFlow.findAll", query = "SELECT c FROM CFlow c")
public class CFlow implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FLOW_ID")
    private Integer flowId;

    private String code;

    @Column(name = "CREATE_BY")
    private String createBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    private String description;

    private String name;

    @Column(name = "PROCESS_DAYS", insertable = false, updatable = false)
    private Integer processDays;

    @Column(name = "UPDATE_BY")
    private String updateBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Date updated;

    @Column(name = "STATUS")
    private Integer status;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public CFlow() {
    }

    public Integer getFlowId() {
        return this.flowId;
    }

    public void setFlowId(Integer flowId) {
        this.flowId = flowId;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCreateBy() {
        return this.createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreated() {
        return this.created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProcessDays() {
        return this.processDays;
    }

    public void setProcessDays(Integer processDays) {
        this.processDays = processDays;
    }

    public String getUpdateBy() {
        return this.updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdated() {
        return this.updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

}
