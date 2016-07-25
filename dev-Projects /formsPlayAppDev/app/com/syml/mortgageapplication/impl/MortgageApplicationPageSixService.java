package com.syml.mortgageapplication.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.syml.couchbase.dao.CouchbaseServiceException;
import com.syml.couchbase.dao.service.CouchBaseService;
import com.syml.hibernate.dao.PostGressDaoServiceException;
import com.syml.hibernate.service.PostGresDaoService;
import com.syml.mortgageapplication.MortgageApplicationConstants;

import play.Logger;
import play.data.DynamicForm;
import controllers.Opportunity;

public class MortgageApplicationPageSixService {
	
	String formType = "Mortgage Application";
	String subForm = "Mortgage Application 6";
	
	PostGresDaoService posService=new PostGresDaoService();
	CouchBaseService couchBaseService=new CouchBaseService();
	
	/**
	 * To Map Page 6 values(applicant and co-applicant details) to Opportunity Pojo
	 * @param opportunity
	 * @param dynamicForm
	 * @return Opportunity
	 */
	public Opportunity loadFormData(Opportunity opportunity,DynamicForm dynamicForm){
		
		
		
		String inputBirthDay = dynamicForm.get("applBirthday");
		String insurance = dynamicForm.get("applInsurNum");
		String workPhone = dynamicForm.get("applWorkPhone");
		String dependant = dynamicForm.get("applDependants");
		String newToCananda =dynamicForm.get("movedCanadas");
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		Date birthday = null;
		try {
			birthday = df.parse(inputBirthDay);
		} catch (ParseException e) {
			Logger.error("Error in parsing string to date",e);
		}
		opportunity.getApplicants().get(0).setDob(birthday);
		opportunity.getApplicants().get(0).setSin(insurance);
		opportunity.getApplicants().get(0).setWork(workPhone);
		opportunity.getApplicants().get(0).setDependant(dependant);
		opportunity.getApplicants().get(0).setBirthDate(inputBirthDay);
		opportunity.getApplicants().get(0).setNewToCannada(newToCananda);
		
		if(opportunity.getIsAdditionalApplicantExist() !=null && opportunity.getIsAdditionalApplicantExist().equalsIgnoreCase("Yes")){
			inputBirthDay = dynamicForm.get("coApplBirthday");
			String CoAppinsurance = dynamicForm.get("coApplInsurNum");
			String coAppworkPhone = dynamicForm.get("coApplWorkPhone");
			String coAppDependant = dynamicForm.get("coAppDependants");
			String conewToCananda =dynamicForm.get("coAppMovedCanadae");
			
			birthday = null;
			try {
				birthday = df.parse(inputBirthDay);
			} catch (ParseException e) {
				Logger.error("Error in parsing string to date",e);
			}
			
			Logger.debug("birthday "+birthday+"\n inputBirthDay  "+inputBirthDay);
			opportunity.getApplicants().get(1).setDob(birthday);
			opportunity.getApplicants().get(1).setSin(CoAppinsurance);
			opportunity.getApplicants().get(1).setWork(coAppworkPhone);
			opportunity.getApplicants().get(1).setDependant(coAppDependant);
			opportunity.getApplicants().get(1).setBirthDate(inputBirthDay);
			opportunity.getApplicants().get(1).setNewToCannada(conewToCananda);
		}

		opportunity.setPogressStatus(55);
		
		return opportunity;
		
	}

	/**
	 * To update opportunity in Db  and update opportunity pojo to couchbase  
	 * @param opportunity
	 * @return Opportunity
	 * @throws MortgageApplicationPageServiceException
	 */
	public Opportunity updateOpportunity(Opportunity opportunity) throws MortgageApplicationPageServiceException{
		try {
			posService.updateApplicantPage6(opportunity);
			couchBaseService.storeDataToCouchbase(MortgageApplicationConstants.MORTGAGE_APPLICATION_COUCHBASE_KEY+opportunity.getId(), opportunity);

		} catch (PostGressDaoServiceException e) {
			throw new MortgageApplicationPageServiceException("Error In updating Applicant Details Into Db ",e);
		} catch (CouchbaseServiceException e) {
			throw new MortgageApplicationPageServiceException("Error In updating Applicant Details Into Couchbase ",e);

		}
		return opportunity;
		
	}
}
