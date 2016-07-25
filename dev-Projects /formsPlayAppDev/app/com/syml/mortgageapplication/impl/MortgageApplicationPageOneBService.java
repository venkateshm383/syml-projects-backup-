package com.syml.mortgageapplication.impl;

import com.syml.couchbase.dao.CouchbaseServiceException;
import com.syml.couchbase.dao.service.CouchBaseService;
import com.syml.hibernate.dao.PostGressDaoServiceException;
import com.syml.hibernate.service.PostGresDaoService;
import com.syml.mortgageapplication.MortgageApplicationConstants;

import play.data.DynamicForm;
import controllers.Applicant;
import controllers.Opportunity;

public class MortgageApplicationPageOneBService {
	
	PostGresDaoService posService=new PostGresDaoService();
	CouchBaseService couchBaseService=new CouchBaseService();
	
	
	/**
	 * To map Co_applicant form values to Opportunity Pojo
	 * @param opportunity
	 * @param dynamicForm
	 * @return opportunity
	 */
	public Opportunity loadFormData(Opportunity opportunity,DynamicForm dynamicForm){
		Applicant coApplicant=new Applicant();
		String CoApprelationshipStatus = dynamicForm.get("coAppRelStatus");
		
		
		if(opportunity.getApplicants().size()>1){
			coApplicant=	opportunity.getApplicants().get(1);
		}
		coApplicant.setApplicant_name( dynamicForm.get("adFirstName"));
		coApplicant.setApplicant_last_name( dynamicForm.get("adLastName"));
		coApplicant.setEmail_personal(dynamicForm.get("adEmail"));
		coApplicant.setCell(dynamicForm.get("coApplMobPhone"));
		if (CoApprelationshipStatus != null 
				&& CoApprelationshipStatus.equalsIgnoreCase("Common-Law")) {
			CoApprelationshipStatus = "Common_Law";
		}
		coApplicant.setRelationship_status(CoApprelationshipStatus);
		opportunity.setPogressStatus(10);

		if(opportunity.getApplicants().size()>1){
				opportunity.getApplicants().set(1, coApplicant);
			}else{
				
				opportunity.getApplicants().add(coApplicant);

			}
		return opportunity;

	}

	
	/**
	 * To Create Co_Applicant, update Opportunity in Db and update Opportunity in COUCHBASE  
	 * @param opportunity
	 * @return opportunity
	 * @throws MortgageApplicationPageServiceException
	 */
	public Opportunity createCoApplicant(Opportunity opportunity) throws MortgageApplicationPageServiceException{
		try {
			posService.createApplicant(opportunity);
			couchBaseService.storeDataToCouchbase(MortgageApplicationConstants.MORTGAGE_APPLICATION_COUCHBASE_KEY+opportunity.getId(), opportunity);

		} catch (PostGressDaoServiceException e) {
			throw new MortgageApplicationPageServiceException("Error In inserting Applicant Details Into Db ",e);
		} catch (CouchbaseServiceException e) {
			throw new MortgageApplicationPageServiceException("Error In updating Opportunity Details Into Couchbase ",e);

		}
		
		return opportunity;
	}
}
