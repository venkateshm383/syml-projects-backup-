package com.syml.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.syml.domain.BaseBean;
import com.syml.generator.domain.annotation.ERP;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;
import javax.persistence.TemporalType;

import com.syml.generator.domain.annotation.ERP.OpenERPType;
/**
* mapping open erp object : applicant.collection
* @author .x.m.
*
*/
@XmlRootElement
@Entity(name="applicant_collection")
public class Collection extends BaseBean {
	//amount
	public Integer amount;

	//balance
	public Integer balance;

	//name
	public String name;

	//date
	@Temporal(TemporalType.TIMESTAMP)
	public Date date;

	//pay_off
	@ERP( name = "pay_off" )
	public Integer payOff;

	/*********************************selection type******************************/
	//status
	@ERP( type = OpenERPType.selection, name = "status" )
	public String status;

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

	public void setAmount(Integer amount){
		this.amount = amount;
	}

	public Integer getAmount(){
		return this.amount;
	}

	public void setBalance(Integer balance){
		this.balance = balance;
	}

	public Integer getBalance(){
		return this.balance;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return this.status;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setDate(Date date){
		this.date = date;
	}

	public Date getDate(){
		return this.date;
	}

	public void setPayOff(Integer payOff){
		this.payOff = payOff;
	}

	public Integer getPayOff(){
		return this.payOff;
	}

}