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
* mapping open erp object : applicant.payment
* @author .x.m.
*
*/
@XmlRootElement
@Entity(name="applicant_payment")
public class LatePayment extends BaseBean {
	//days
	public String days;

	//reason
	public String reason;

	//name
	public String name;

	//rating
	public String rating;

	//date
	@Temporal(TemporalType.TIMESTAMP)
	public Date date;

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

	public void setDays(String days){
		this.days = days;
	}

	public String getDays(){
		return this.days;
	}

	public void setReason(String reason){
		this.reason = reason;
	}

	public String getReason(){
		return this.reason;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setRating(String rating){
		this.rating = rating;
	}

	public String getRating(){
		return this.rating;
	}

	public void setDate(Date date){
		this.date = date;
	}

	public Date getDate(){
		return this.date;
	}

}