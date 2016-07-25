package com.syml.mortgageapplication.backbuttonimpl;

import com.syml.couchbasehelper.CouchbaseServiceHelper;
import com.syml.mortgageapplication.impl.MortgageApplicationPageServiceException;

import controllers.Applicant;
import controllers.Opportunity;

public class MortgageApplicationPageOneBackButtonService {
	CouchbaseServiceHelper couchbaseServiceHelper=new CouchbaseServiceHelper();

	public Opportunity getOpporunity(int opportunityID) throws MortgageApplicationPageServiceException{
		Opportunity opportunity=null;
		 opportunity=couchbaseServiceHelper.getCouhbaseDataByKey(opportunityID);
		return opportunity;
		
	}
	
	public Applicant getApplicant(Opportunity opportunity){
		Applicant applicant=opportunity.getApplicants().get(0);
		return applicant;
	}
	
	public Applicant getCoApplicant(Opportunity opportunity){
		if(opportunity.getIsAdditionalApplicantExist().equalsIgnoreCase("Yes")){
		Applicant coApplicant=opportunity.getApplicants().get(1);
		return coApplicant;
		}
		return null;
	}
	
	
}
