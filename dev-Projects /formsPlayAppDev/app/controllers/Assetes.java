package controllers;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name = "crm_asset")
public class Assetes {
	
	@Id
	@GenericGenerator(name = "kaugen", strategy = "increment")
	@GeneratedValue(generator = "kaugen")
	@Column(name = "id",unique = true)
	private Integer id;
	
	@Column(name = "asset_type")
	private String type;
	
	
	
	@Column(name = "name")
	private String description;
	
	@Column(name = "value")
	private Double value;
	
	@Column(name = "opportunity_id")
	private Integer opportunity_id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "opportunity_id", nullable = false,insertable = false, updatable = false)
	private Applicant applicant;
	
	@Transient
	private String assetType;

	public String getAssetType() {
		return assetType;
	}
	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}
	@Transient
	private String ownerShip;
	
	public String getOwnerShip() {
		
		return ownerShip;
	}
	public void setOwnerShip(String ownerShip) {
		this.ownerShip = ownerShip;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "Assets [type=" + type + ", description=" + description
				+ ", value=" + value + "]";
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getOpportunity_id() {
		return opportunity_id;
	}
	public void setOpportunity_id(Integer opportunity_id) {
		this.opportunity_id = opportunity_id;
	}
	public Applicant getApplicant() {
		return applicant;
	}
	public void setApplicant(Applicant applicant) {
		this.applicant = applicant;
	}

}
