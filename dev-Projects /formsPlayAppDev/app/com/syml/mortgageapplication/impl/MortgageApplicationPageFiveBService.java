package com.syml.mortgageapplication.impl;

import com.syml.couchbase.dao.CouchbaseServiceException;
import com.syml.couchbase.dao.service.CouchBaseService;
import com.syml.hibernate.dao.PostGressDaoServiceException;
import com.syml.hibernate.service.PostGresDaoService;
import com.syml.mortgageapplication.MortgageApplicationConstants;

import play.data.DynamicForm;
import controllers.Opportunity;
import controllers.PersonalInfo;

public class MortgageApplicationPageFiveBService {
	
	
	PostGresDaoService posService=new PostGresDaoService();
	CouchBaseService couchBaseService=new CouchBaseService();
	
	/**
	 * To map page5b  value To Opportunity Pojo 
	 * @param opportunity
	 * @param dynamicForm
	 * @return op
	 */
	public Opportunity loadFormData(Opportunity opportunity,DynamicForm dynamicForm){
		String incomeraise1 = dynamicForm.get("incomeGoing");
		String rentalproperty1 = dynamicForm.get("OneMoreProp");
		String propertyLessThen5Years = dynamicForm.get("likelyProperty");
		String job5Years = dynamicForm.get("sameJob");
		opportunity.setProperty_less_then_5_years(getPropertyLessThen5Years(propertyLessThen5Years));
		opportunity.setJob_5_years(getJob5Years(job5Years));
	
		//#TODO need to add to  Couchbase
		opportunity.setPropertyLessThen5Years(propertyLessThen5Years);
		opportunity.setJob5Years(job5Years);
		opportunity.setIncomeRaise(incomeraise1);
		opportunity.setRentalProperty(rentalproperty1);
		
		opportunity.setPogressStatus(45);
		
		return opportunity;
	}
	
	/**
	 * To update Opportunity to DB and Update couchbase with new values 
	 * @param opportunity
	 * @return Opportunity
	 * @throws MortgageApplicationPageServiceException
	 */
	public Opportunity updateOpportunity(Opportunity opportunity) throws MortgageApplicationPageServiceException{
		try {
			posService.updateOpportunityPage5b(opportunity);
			couchBaseService.storeDataToCouchbase(MortgageApplicationConstants.MORTGAGE_APPLICATION_COUCHBASE_KEY+opportunity.getId(), opportunity);

		} catch (PostGressDaoServiceException e) {
			throw new MortgageApplicationPageServiceException("Error In updating opportunity Details Into Db ",e);
		} catch (CouchbaseServiceException e) {
			throw new MortgageApplicationPageServiceException("Error In updating Opportunity Details Into Couchbase ",e);

		}
		return opportunity;
		
	}
	
	
	/**
	 * To map applicantnames to Personalonfo Pojo From Opportunity 
	 * @param opportunity
	 * @return PersonalInfo
	 */
	public PersonalInfo mapApplicantDetails(Opportunity opportunity){
		PersonalInfo psInfo=new PersonalInfo();
		psInfo.setAdditionalApplicant(opportunity.getIsAdditionalApplicantExist());

		if(opportunity.getApplicants()!=null && opportunity.getApplicants().size()>0){
			psInfo.setApplicantName(opportunity.getApplicants().get(0).getApplicant_name());
			if(opportunity.getIsAdditionalApplicantExist() != null && opportunity.getIsAdditionalApplicantExist().equalsIgnoreCase("Yes")){
				psInfo.setCoApplicantName(opportunity.getApplicants().get(1).getApplicant_name());
			}
		}
	
		return psInfo;
	}
	
	/**
	 * to get PropertyLessThen5Years OpenErp Value based on given Form Input value  
	 * @param propertyLessThen5Years
	 * @return String
	 */
	public String getPropertyLessThen5Years(String propertyLessThen5Years) {
		if (propertyLessThen5Years != null
				&& propertyLessThen5Years.equalsIgnoreCase("Yes")) {
			return "5";
		} else if (propertyLessThen5Years != null
				&& propertyLessThen5Years.equalsIgnoreCase("No")) {
			return "1";
		} else
			return "3";
	}

	/**
	 * to get Job5Year OpenErpValue based on given Form Input Value 
	 * @param job5Years
	 * @return String
	 */
	public String getJob5Years(String job5Years) {
		if (job5Years != null && job5Years.equalsIgnoreCase("Yes")) {
			return "5";
		} else if (job5Years != null && job5Years.equalsIgnoreCase("No")) {
			return "1";
		} else
			return "3";
	}
}
