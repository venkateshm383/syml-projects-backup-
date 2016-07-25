package com.syml.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.syml.domain.BaseBean;
import com.syml.generator.domain.annotation.ERP;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.Transient;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.persistence.TemporalType;

import com.syml.generator.domain.annotation.ERP.OpenERPType;
/**
* mapping open erp object : applicant.liabilities
* @author .x.m.
*
*/
@XmlTransient
@XmlRootElement
@Entity(name="applicant_liabilities")
public class Liability extends BaseBean {
	
	// Not in OpenERP
	public double paymentRatio;
	// Not in OpenERP	
	public String person;
	
	//credit_balance
	@ERP( name = "credit_balance" )
	public Double creditBalance;

	//reported
	@Temporal(TemporalType.TIMESTAMP)
	public Date reported;

	//dla
	@Temporal(TemporalType.TIMESTAMP)
	public Date dla;

	//monthly_payment
	@ERP( name = "monthly_payment" )
	public String monthlyPayment;

	//pay_off
	@ERP( name = "pay_off" )
	public Double payOff;

	//business
	public String business;

	//credit_limit
	@ERP( name = "credit_limit" )
	public Double creditLimit;

	//rating
	public String rating;

	//opened
	@Temporal(TemporalType.TIMESTAMP)
	public Date opened;

	/*********************************selection type******************************/
	//status
	@ERP( type = OpenERPType.selection, name = "status" )
	public String status;

	//type
	@ERP( type = OpenERPType.selection, name = "type" )
	public String type;

	//name
	@ERP( type = OpenERPType.selection, name = "name" )
	public String name;

	/*********************************many2one type******************************/

	//many to one : mapping table : applicant_record(com.syml.domain.Applicant)
	@ERP(name="applicant_id", type = OpenERPType.many2one )
	@JsonIgnore
	public Integer applicantId;

	@Transient
	public String client;


	public void setCreditBalance(Double creditBalance){
		this.creditBalance = creditBalance;
	}

	public Double getCreditBalance(){
		return this.creditBalance;
	}

	public void setApplicantId(Integer applicantId){
		this.applicantId = applicantId;
	}

	public Integer getApplicantId(){
		return this.applicantId;
	}

	public void setReported(Date reported){
		this.reported = reported;
	}

	public Date getReported(){
		return this.reported;
	}

	public void setDla(Date dla){
		this.dla = dla;
	}

	public Date getDla(){
		return this.dla;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return this.status;
	}

	public void setMonthlyPayment(String monthlyPayment){
		this.monthlyPayment = monthlyPayment;
	}

	public String getMonthlyPayment(){
		return this.monthlyPayment;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return this.type;
	}

	public void setPayOff(Double payOff){
		this.payOff = payOff;
	}

	public Double getPayOff(){
		return this.payOff;
	}

	public void setBusiness(String business){
		this.business = business;
	}

	public String getBusiness(){
		return this.business;
	}

	public void setCreditLimit(Double creditLimit){
		this.creditLimit = creditLimit;
	}

	public Double getCreditLimit(){
		return this.creditLimit;
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

	public void setOpened(Date opened){
		this.opened = opened;
	}

	public Date getOpened(){
		return this.opened;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public double getPaymentRatio() {
		return paymentRatio;
	}

	public void setPaymentRatio(double paymentRatio) {
		this.paymentRatio = paymentRatio;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	@Override
	public String toString() {
		StringBuilder liabilityToString = new StringBuilder();
		liabilityToString.append(client + ", ");
		liabilityToString.append(name + ", ");
		liabilityToString.append(business + ", ");
		liabilityToString.append(creditLimit + ", ");
		liabilityToString.append(creditBalance + ", ");
		liabilityToString.append(monthlyPayment + ", ");
	    return liabilityToString.toString();
	}
	
}