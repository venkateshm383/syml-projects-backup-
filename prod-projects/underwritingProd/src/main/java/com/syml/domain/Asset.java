package com.syml.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.syml.domain.BaseBean;

import javax.persistence.Column;

import com.syml.generator.domain.annotation.ERP;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.syml.generator.domain.annotation.ERP.OpenERPType;
/**
* mapping open erp object : crm.asset
* @author .x.m.
*
*/
@XmlTransient
@XmlRootElement
@Entity(name="crm_asset")
public class Asset extends BaseBean {
	//name
	@Column(length=9999)
	public String name;

	//value
	public Double value;

	/*********************************selection type******************************/
	//asset_type
	@ERP( type = OpenERPType.selection, name = "asset_type" )
	public String assetType;

	/*********************************many2one type******************************/

	//many to one : mapping table : applicant_record(com.syml.domain.Applicant)
	@ERP(name="opportunity_id", type = OpenERPType.many2one )
	@JsonIgnore
	public Integer opportunityId;


	public void setOpportunityId(Integer opportunityId){
		this.opportunityId = opportunityId;
	}

	public Integer getOpportunityId(){
		return this.opportunityId;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setAssetType(String assetType){
		this.assetType = assetType;
	}

	public String getAssetType(){
		return this.assetType;
	}

	public void setValue(Double value){
		this.value = value;
	}

	public Double getValue(){
		return this.value;
	}

}