package com.iso.dashboard.dto;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the m_flow_procedure database table.
 * 
 */
@Entity
@Table(name="m_flow_procedure")
@NamedQuery(name="MFlowProcedure.findAll", query="SELECT m FROM MFlowProcedure m")
public class MFlowProcedure implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name="FLOW_ID")
	private Integer flowId;

	@Column(name="PROCEDURE_ID")
	private Integer procedureId;

	public MFlowProcedure() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFlowId() {
		return this.flowId;
	}

	public void setFlowId(Integer flowId) {
		this.flowId = flowId;
	}

	public Integer getProcedureId() {
		return this.procedureId;
	}

	public void setProcedureId(Integer procedureId) {
		this.procedureId = procedureId;
	}

}