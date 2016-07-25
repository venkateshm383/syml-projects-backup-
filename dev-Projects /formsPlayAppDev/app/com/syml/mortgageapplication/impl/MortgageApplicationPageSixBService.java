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

public class MortgageApplicationPageSixBService {
	
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
		
																	 
		String coAppworkPhone = dynamicForm.get("coApplWorkPhone");
		String inputBirthDay = dynamicForm.get("coApplBirthday");
		String insurance = dynamicForm.get("coApplInsurNum");
		String coAppDependant = dynamicForm.get("coAppDependants");
		String conewToCannada = dynamicForm.get("movedCanadas");
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		Date birthday = null;
		try {
			birthday = df.parse(inputBirthDay);
		} catch (ParseException e) {
			Logger.error("Error in parsing string to date",e);
		}
		opportunity.getApplicants().get(1).setDob(birthday);
		opportunity.getApplicants().get(1).setSin(insurance);
		opportunity.getApplicants().get(1).setWork(coAppworkPhone);
		opportunity.getApplicants().get(1).setDependant(coAppDependant);
		opportunity.getApplicants().get(1).setBirthDate(inputBirthDay);
		opportunity.getApplicants().get(1).setNewToCannada(conewToCannada);
		
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
	 * To set the data BioInfo for Page6b back button display
	 * @param opportunity
	 * @return
	 */
	public PersonalInfo getPersonalInfo(Opportunity opportunity){
		
		Logger.info("Inside (.)  getPersonalInfo of MortgageApplicationPageSixAService ");
		PersonalInfo personalInfo = new PersonalInfo();
		Applicant applicant = opportunity.getApplicants().get(1);
		personalInfo.setCoApplicantName(applicant.getApplicant_name());
		personalInfo.setCoWorkPhone(applicant.getWork());
		personalInfo.setCoBirthDay(applicant.getBirthDate());
		personalInfo.setCoSocialInsurance(applicant.getSin());
		personalInfo.setCoDependents(applicant.getDependant());
		personalInfo.setCoMovedCanada(applicant.getNewToCannada());
	return personalInfo;
	}
}