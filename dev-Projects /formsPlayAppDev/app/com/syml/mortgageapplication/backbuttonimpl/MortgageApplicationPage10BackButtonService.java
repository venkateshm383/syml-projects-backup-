package com.syml.mortgageapplication.backbuttonimpl;

import play.Logger;

import com.syml.couchbasehelper.CouchbaseServiceHelper;
import com.syml.mortgageapplication.impl.MortgageApplicationPageServiceException;

import controllers.Applicant;
import controllers.Assetes;
import controllers.Opportunity;
import controllers.TotalAssets;

public class MortgageApplicationPage10BackButtonService {

	CouchbaseServiceHelper couchbaseServiceHelper = new CouchbaseServiceHelper();

	public Opportunity getOpportunity(int opportunityID)
			throws MortgageApplicationPageServiceException {
		Opportunity opportunity = null;
		opportunity = couchbaseServiceHelper
				.getCouhbaseDataByKey(opportunityID);
		return opportunity;
	}

	public TotalAssets getTotalAssets(Opportunity opportunity) {
		TotalAssets totalAssets = new TotalAssets();
		Applicant coApplicant = null;
		Applicant applicant = opportunity.getApplicants().get(0);
		if (opportunity.getIsAdditionalApplicantExist() !=null && opportunity.getIsAdditionalApplicantExist().equalsIgnoreCase("Yes")) {
			coApplicant = opportunity.getApplicants().get(1);
		}
		if (applicant.getAssetList().size() > 0) {
			for (int i = 0; i < applicant.getAssetList().size(); i++) {
				String assetype = applicant.getAssetList().get(i).getAssetType();
				getApplicantAssets(totalAssets, assetype, applicant.getAssetList().get(i));
				
			}
		}
		if(coApplicant!=null&&coApplicant.getAssetList().size()>0){
			for (int i = 0; i < coApplicant.getAssetList().size(); i++) {
				String assetype = coApplicant.getAssetList().get(i).getAssetType();
				getApplicantAssets(totalAssets, assetype, coApplicant.getAssetList().get(i));
				
			}
		}
		return totalAssets;
	}

	private TotalAssets getApplicantAssets(TotalAssets totalAssets,String assetype,Assetes assetes) {
		
		Logger.debug("Inside (.) getApplicantAssets "+assetype);
		if (assetype.equalsIgnoreCase("Vehicle")) {
			
			totalAssets.getVehicle().add(assetes);
	} else if (assetype.equalsIgnoreCase("BankAccount")) {
		totalAssets.getBankAccount().add(assetes);

	} else if (assetype.equalsIgnoreCase("RRSPTSFA")) {
		totalAssets.getRrsp().add(assetes);

	} else if (assetype.equalsIgnoreCase("Investments")) {
		totalAssets.getInvestments().add(assetes);

	} else {
		totalAssets.getOthers().add(assetes);

	}		return totalAssets;
	}

	

}
