package com.syml.mortgageapplication.backbuttonimpl;

import com.syml.couchbasehelper.CouchbaseServiceHelper;
import com.syml.mortgageapplication.impl.MortgageApplicationPageServiceException;

import controllers.Applicant;
import controllers.Opportunity;

public class MortgageApplicationPageOneBBackButtonService {
	CouchbaseServiceHelper couchbaseServiceHelper=new CouchbaseServiceHelper();

	public Opportunity getOpporunity(int opportunityID) throws MortgageApplicationPageServiceException{
		Opportunity opportunity=null;

		 opportunity=couchbaseServiceHelper.getCouhbaseDataByKey(opportunityID);
	
		return opportunity;
		
	}
	
	public Applicant getCoApplicant(Opportunity opportunity){
		Applicant coApplicant=opportunity.getApplicants().get(0);
		return coApplicant;
	}
	
}
