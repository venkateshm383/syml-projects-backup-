package com.syml.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.syml.domain.BaseBean;
import com.syml.generator.domain.annotation.ERP;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.syml.generator.domain.annotation.ERP.OpenERPType;
/**
* mapping open erp object : applicant.mortgage
* @author .x.m.
*
*/
@XmlTransient
@XmlRootElement
@Entity(name="applicant_mortgage")
public class Mortgage extends BaseBean {
	//property_id
	@ERP( name = "property_id" )
	public String propertyId;

	//balance
	public String balance;

	//selling
	public Boolean selling;

	//monthly_payment
	@ERP( name = "monthly_payment" )
	public String monthlyPayment;

	//monthly_rent
	@ERP( name = "monthly_rent" )
	public Integer monthlyRent;

	//name
	public String name;

	//renewal
	public String renewal;

	//pay_off
	@ERP( name = "pay_off" )
	public Integer payOff;

	//interest_rate
	@ERP( name = "interest_rate" )
	public String interestRate;

	/*********************************selection type******************************/
	//type
	@ERP( type = OpenERPType.selection, name = "type" )
	public String type;

	/*********************************many2one type******************************/

	//many to one : mapping table : applicant_record(com.syml.domain.Applicant)
	@ERP(name="applicant_id", type = OpenERPType.many2one )
	@JsonIgnore
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

	public void setBalance(String balance){
		this.balance = balance;
	}

	public String getBalance(){
		return this.balance;
	}

	public void setSelling(Boolean selling){
		this.selling = selling;
	}

	public Boolean getSelling(){
		return this.selling;
	}

	public void setMonthlyPayment(String monthlyPayment){
		this.monthlyPayment = monthlyPayment;
	}

	public String getMonthlyPayment(){
		return this.monthlyPayment;
	}

	public void setMonthlyRent(Integer monthlyRent){
		this.monthlyRent = monthlyRent;
	}

	public Integer getMonthlyRent(){
		return this.monthlyRent;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return this.type;
	}

	public void setRenewal(String renewal){
		this.renewal = renewal;
	}

	public String getRenewal(){
		return this.renewal;
	}

	public void setPayOff(Integer payOff){
		this.payOff = payOff;
	}

	public Integer getPayOff(){
		return this.payOff;
	}

	public void setInterestRate(String interestRate){
		this.interestRate = interestRate;
	}

	public String getInterestRate(){
		return this.interestRate;
	}

}