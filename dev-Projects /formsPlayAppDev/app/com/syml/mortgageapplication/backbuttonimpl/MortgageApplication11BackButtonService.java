package com.syml.mortgageapplication.backbuttonimpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.syml.couchbasehelper.CouchbaseServiceHelper;
import com.syml.mortgageapplication.impl.MortgageApplicationPageServiceException;

import controllers.Applicant;
import controllers.Opportunity;
import controllers.Property;

public class MortgageApplication11BackButtonService {

	CouchbaseServiceHelper couchbaseServiceHelper = new CouchbaseServiceHelper();

	public Opportunity getOpportunity(int opportunityID)
			throws MortgageApplicationPageServiceException {
		Opportunity opportunity = null;
		opportunity = couchbaseServiceHelper
				.getCouhbaseDataByKey(opportunityID);
		return opportunity;
	}
	
	public List<Property> getListOfProperties(Opportunity opportunity){
		
		List<Property> listProperty=new ArrayList<Property>();
		
		Applicant applicant=opportunity.getApplicants().get(0);
		
		for (@SuppressWarnings("rawtypes")
		Iterator iterator = applicant.getProperties().iterator(); iterator.hasNext();) {
			Property property = (Property) iterator.next();
			listProperty.add(property);
		}
		/**
		 * for this reason only the double is coming on backbutton
		 */
	/*	if(opportunity.getIsAdditionalApplicantExist().equalsIgnoreCase("yes")){
			applicant=opportunity.getApplicants().get(0);
			for (@SuppressWarnings("rawtypes")
			Iterator iterator = applicant.getProperties().iterator(); iterator.hasNext();) {
				Property property = (Property) iterator.next();
				listProperty.add(property);
			}
		}*/
		
		return listProperty;
		
	}
}
