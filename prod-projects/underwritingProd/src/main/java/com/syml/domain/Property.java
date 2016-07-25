package com.syml.domain;

import com.syml.domain.BaseBean;
import com.syml.generator.domain.annotation.ERP;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.syml.generator.domain.annotation.ERP.OpenERPType;
/**
* mapping open erp object : applicant.property
* @author .x.m.
*
*/
@XmlTransient
@XmlRootElement
@Entity(name="applicant_property")
public class Property extends BaseBean {
	//property_id
	@ERP( name = "property_id" )
	public String propertyId;

	//selling
	public Boolean selling;

	//annual_tax
	@ERP( name = "annual_tax" )
	public Integer annualTax;

	//mo_condo_fees
	@ERP( name = "mo_condo_fees" )
	public Integer moCondoFees;

	//name
	public String name;

	//value
	public Double value;

	//owed
	public Integer owed;

	/*********************************many2one type******************************/

	//many to one : mapping table : applicant_record(com.syml.domain.Applicant)
	@ERP(name="applicant_id", type = OpenERPType.many2one )
	public Integer applicantId;


	public void setApplicantId(Integer applicantId){
		this.applicantId = applicantId;
	}

	public Integer getApplicantId(){
		return this.applicantId;
	}

	public void setPropertyId(String propertyId){
		this.propertyId = propertyId;
	}

	public String getPropertyId(){
		return this.propertyId;
	}

	public void setSelling(Boolean selling){
		this.selling = selling;
	}

	public Boolean getSelling(){
		return this.selling;
	}

	public void setAnnualTax(Integer annualTax){
		this.annualTax = annualTax;
	}

	public Integer getAnnualTax(){
		return this.annualTax;
	}

	public void setMoCondoFees(Integer moCondoFees){
		this.moCondoFees = moCondoFees;
	}

	public Integer getMoCondoFees(){
		return this.moCondoFees;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setValue(Double value){
		this.value = value;
	}

	public Double getValue(){
		return this.value;
	}

	public void setOwed(Integer owed){
		this.owed = owed;
	}

	public Integer getOwed(){
		return this.owed;
	}

}