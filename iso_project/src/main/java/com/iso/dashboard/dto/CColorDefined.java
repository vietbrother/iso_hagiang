package com.iso.dashboard.dto;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the c_color_defined database table.
 * 
 */
@Entity
@Table(name="c_color_defined")
@NamedQuery(name="CColorDefined.findAll", query="SELECT c FROM CColorDefined c")
public class CColorDefined implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="color_name")
	private String colorName;

	@Column(name="color_code")
	private String colorCode;

	public CColorDefined() {
	}

	public String getColorName() {
		return this.colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	public String getColorCode() {
		return this.colorCode;
	}

	public void setColorCode(String colorCode) {
		this.colorCode = colorCode;
	}

}