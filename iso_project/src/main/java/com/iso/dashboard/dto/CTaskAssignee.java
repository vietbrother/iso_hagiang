package com.iso.dashboard.dto;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the c_task_assignee database table.
 *
 */
@Entity
@Table(name = "c_task_assignee")
@NamedQuery(name = "CTaskAssignee.findAll", query = "SELECT c FROM CTaskAssignee c")
public class CTaskAssignee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "assigned_id")
    private Integer assignedId;

    @Column(name = "is_main")
    private Integer isMain;

    @Id
    private CTaskAssigneePK id;

    public CTaskAssigneePK getId() {
        return id;
    }

    public void setId(CTaskAssigneePK id) {
        this.id = id;
    }

    public CTaskAssignee() {
    }

    public Integer getAssignedId() {
        return this.assignedId;
    }

    public void setAssignedId(Integer assignedId) {
        this.assignedId = assignedId;
    }

    public Integer getIsMain() {
        return this.isMain;
    }

    public void setIsMain(Integer isMain) {
        this.isMain = isMain;
    }

}
