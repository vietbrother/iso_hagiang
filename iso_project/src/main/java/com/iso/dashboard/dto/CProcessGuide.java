package com.iso.dashboard.dto;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the c_process_guide database table.
 *
 */
@Entity
@Table(name = "c_process_guide")
@NamedQuery(name = "CProcessGuide.findAll", query = "SELECT c FROM CProcessGuide c")
public class CProcessGuide implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "process_guide_id")
    private Integer processGuideId;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "process_guide_name")
    private String processGuideName;

    @Column(name = "position")
    private String position;

    public CProcessGuide() {
    }

    public Integer getProcessGuideId() {
        return processGuideId;
    }

    public void setProcessGuideId(Integer processGuideId) {
        this.processGuideId = processGuideId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getProcessGuideName() {
        return processGuideName;
    }

    public void setProcessGuideName(String processGuideName) {
        this.processGuideName = processGuideName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

}
