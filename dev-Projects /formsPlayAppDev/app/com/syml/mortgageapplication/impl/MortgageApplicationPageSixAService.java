package com.syml.mortgageapplication.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import play.Logger;
import play.data.DynamicForm;

import com.syml.couchbase.dao.CouchbaseServiceException;
import com.syml.couchbase.dao.service.CouchBaseService;
import com.syml.hibernate.dao.PostGressDaoServiceException;
import com.syml.hibernate.service.PostGresDaoService;
import com.syml.mortgageapplication.MortgageApplicationConstants;

import controllers.Applicant;
import controllers.Opportunity;
import controllers.PersonalInfo;

public class MortgageApplicationPageSixAService {
	
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
		
																	 
		String workPhone= dynamicForm.get("applWorkPhone");
		String inputBirthDay = dynamicForm.get("applBirthday");
		String insurance = dynamicForm.get("applInsurNum");
		String dependant = dynamicForm.get("applDependants");
		String newToCannada = dynamicForm.get("movedCanadas");
		
		
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
		opportunity.getApplicants().get(0).setNewToCannada(newToCannada);
		
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
	
	/**
	 * To set the data BioInfo for Page6a back button display
	 * @param opportunity
	 * @return
	 */
	public PersonalInfo getPersonalInfo(Opportunity opportunity){
		
		Logger.info("Inside (.)  getPersonalInfo of MortgageApplicationPageSixAService ");
		PersonalInfo personalInfo = new PersonalInfo();
		Applicant applicant = opportunity.getApplicants().get(0);
		personalInfo.setWorkPhone(applicant.getWork());
		personalInfo.setBirthDay(applicant.getBirthDate());
		personalInfo.setSocialInsurance(applicant.getSin());
		personalInfo.setDependents(applicant.getDependant());
		personalInfo.setApplicantName(applicant.getApplicant_name());
		personalInfo.setMovedCanada(applicant.getNewToCannada());
		Logger.debug("::::::::::::::::::::::::"+opportunity+"opid "+opportunity.getId());
		Logger.debug("sin value-->"+applicant.getSin());
	return personalInfo;
	}
}