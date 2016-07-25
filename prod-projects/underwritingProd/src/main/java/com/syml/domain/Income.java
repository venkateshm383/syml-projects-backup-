package com.syml.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.syml.domain.BaseBean;

import javax.persistence.Column;
import javax.persistence.Transient;

import com.syml.generator.domain.annotation.ERP;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.syml.generator.domain.annotation.ERP.OpenERPType;
/**
* mapping open erp object : income.employer
* @author .x.m.
*
*/
@XmlTransient
@XmlRootElement
@Entity(name="income_employer")
public class Income extends BaseBean {
	//position
	public String position;

	//month
	public Integer month;

	//annual_income
	@ERP( name = "annual_income" )
	public String annualIncome;

	//property_id
	@ERP( name = "property_id" )
	public String propertyID;
	
	//allow_duplex
	@ERP( name = "historical" )
	public Boolean historical;
		
	//industry
	@Column(length=9999)
	public String industry;

	//business
	public String business;

	/*********************************selection type******************************/
	//name
	@ERP( type = OpenERPType.selection, name = "name" )
	public String name;

	//income_type
	@ERP( type = OpenERPType.selection, name = "income_type" )
	public String typeOfIncome;
		
	/*********************************many2one type******************************/

	//many to one : mapping table : applicant_record(com.syml.domain.Applicant)
	@ERP(name="applicant_id", type = OpenERPType.many2one )
	@JsonIgnore
	public Integer applicantId;

//	@Transient
//	public String typeOfIncome;   This was the way typeOfIncome was being mapped


	public void setApplicantId(Integer applicantId){
		this.applicantId = applicantId;
	}

	public String getTypeOfIncome() {
		return typeOfIncome;
	}

	public void setTypeOfIncome(String typeOfIncome) {
		this.typeOfIncome = typeOfIncome;
	}

	public Boolean getHistorical() {
		return historical;
	}

	public void setHistorical(Boolean historical) {
		this.historical = historical;
	}

	public Integer getApplicantId(){
		return this.applicantId;
	}

	public void setPosition(String position){
		this.position = position;
	}

	public String getPosition(){
		return this.position;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setMonth(Integer month){
		this.month = month;
	}

	public Integer getMonth(){
		return this.month;
	}

	public void setAnnualIncome(String annualIncome){
		this.annualIncome = annualIncome;
	}

	public String getAnnualIncome(){
		return this.annualIncome;
	}

	public void setIndustry(String industry){
		this.industry = industry;
	}

	public String getIndustry(){
		return this.industry;
	}

	public void setBusiness(String business){
		this.business = business;
	}

	public String getBusiness(){
		return this.business;
	}

	public String getPropertyID() {
		return propertyID;
	}

	public void setPropertyID(String propertyID) {
		this.propertyID = propertyID;
	}
}