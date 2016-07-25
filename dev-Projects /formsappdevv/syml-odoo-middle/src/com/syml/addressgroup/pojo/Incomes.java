package com.syml.addressgroup.pojo;

import com.syml.address.splitAddress.PropertySplitter;

public class Incomes {
	public String Type;
	public String Business;
	public  String JobTitle;
	public  Boolean Supplementary;
	public  Boolean Historical;
	public String Months;
	public String Annual;
	public String PropertyId;
	PropertySplitter splitter=new PropertySplitter();

	
	public void showIncomes1(){
		Incomes income1=new Incomes();
		
		income1.Historical=true;
		income1.Business=splitter.bussiness + " (Current NOA) ";
		income1.JobTitle=splitter.JobTitle;
		income1.Months=splitter.sumOfMonths;
		income1.Type=splitter.Type;
		income1.Supplementary=true;
		
	}
	public void showIncomes2(){
		Incomes income2=new Incomes();
		
		income2.Historical=true;
		income2.Business=splitter.bussiness + " (Current NOA) ";
		income2.JobTitle=splitter.JobTitle;
		income2.Months=splitter.sumOfMonths;
		income2.Type=splitter.Type;
		income2.Supplementary=true;
		
	}
	public void showIncomes3(){
		Incomes income3=new Incomes();
		
		income3.Historical=true;
		income3.Business=splitter.bussiness + " (Current NOA) ";
		income3.JobTitle=splitter.JobTitle;
		income3.Months=splitter.sumOfMonths;
		income3.Type=splitter.Type;
		income3.Supplementary=true;
		
	}
	
	
	
	
	public String getPropertyId() {
		return PropertyId;
	}
	public void setPropertyId(String propertyId) {
		PropertyId = propertyId;
	}
	public String getAnnual() {
		return Annual;
	}
	public void setAnnual(String annual) {
		Annual = annual;
	}
	public String getMonths() {
		return Months;
	}
	public void setMonths(String months) {
		Months = months;
	}
	public String getType() {
		return Type;
	}
	public void setType(String type) {
		Type = type;
	}
	public String getBusiness() {
		return Business;
	}
	public void setBusiness(String business) {
		Business = business;
	}
	public String getJobTitle() {
		return JobTitle;
	}
	public void setJobTitle(String jobTitle) {
		JobTitle = jobTitle;
	}
	
	public Boolean getSupplementary() {
		return Supplementary;
	}
	public void setSupplementary(Boolean supplementary) {
		Supplementary = supplementary;
	}
	public Boolean getHistorical() {
		return Historical;
	}
	public void setHistorical(Boolean historical) {
		Historical = historical;
	}
	
	
	
}