package com.syml.mortgageapplication.impl;

import java.util.ArrayList;
import java.util.List;

import play.Logger;

import com.syml.couchbase.dao.CouchbaseServiceException;
import com.syml.couchbase.dao.service.CouchBaseService;
import com.syml.hibernate.dao.PostGressDaoServiceException;
import com.syml.hibernate.service.PostGresDaoService;
import com.syml.mortgageapplication.MortgageApplicationConstants;

import controllers.Assetes;
import controllers.Opportunity;

public class MortgageApplicationPageTenService {
	String formType = "Mortgage Application";
	String subForm = "Mortgage Application 10 assets";
	PostGresDaoService posService=new PostGresDaoService();
	CouchBaseService couchBaseService=new CouchBaseService();
	
	
	public Opportunity listAssets(Opportunity opportunity,String[] asset, String[] description,String[] values,String[] ownerShip,String type,String assetType ){
		
		String additionalApplicantYesNo=opportunity.getIsAdditionalApplicantExist();
	
		
	
		for (int i = 0; i <= asset.length - 1; i++) {
			Assetes	assets = new Assetes();
			assets.setType(type);
			assets.setAssetType(assetType);
			String discription1 = (String) description[i];
			String propertyValue = (String) values[i];
			String ownership = (String) ownerShip[i];
			Double value = null;
			try {
				value = Double.parseDouble(propertyValue);
				} catch (NumberFormatException e) {
					Logger.error("Error when parsing property value into Double", e);
				}
			assets.setDescription(discription1);
			assets.setValue(value);
			assets.setOwnerShip(ownership);
			if(additionalApplicantYesNo != null && additionalApplicantYesNo.equalsIgnoreCase("yes") && 
					opportunity.getApplicants().get(1).getApplicant_name().equalsIgnoreCase(ownership)){
			assets.setOpportunity_id(opportunity.getApplicants().get(1).getId());
			}else{
			assets.setOpportunity_id(opportunity.getApplicants().get(0).getId());
			}
				opportunity.getApplicants().get(0).getAssetList().add(assets);
		}
		return opportunity;
	}
	
	public Opportunity createAssests(Opportunity opportunity) throws MortgageApplicationPageServiceException{
		try {
			posService.updateApplicantAssetsPage10(opportunity);
			couchBaseService.storeDataToCouchbase(MortgageApplicationConstants.MORTGAGE_APPLICATION_COUCHBASE_KEY+opportunity.getId(), opportunity);

		} catch (PostGressDaoServiceException e) {
			throw new MortgageApplicationPageServiceException("Error In inserting Applicant assets details Into Db ",e);
		} catch (CouchbaseServiceException e) {
			throw new MortgageApplicationPageServiceException("Error In updating Opportunity Details Into Couchbase ",e);

		}
		
		return opportunity;
	}
	
	
	
	public List<String> getListOfAssets(String[] assetTypes){
		List<String> asstypeArray = new ArrayList<String>();
		for (String assetType : assetTypes) {
			String[] splits = assetType.replaceAll("^\\s*\\[|\\]|\\s*$\"",
					"").split("\\s*,\\s*");
			Logger.info("Inside for loop " + splits);
			for (String s : splits) {
				String assType = s.replace("\"", "");
					asstypeArray.add(assType);
			}
		}
		return asstypeArray;
	}
	
	public void deleteAssest(Opportunity opportunity) throws PostGressDaoServiceException{
		List<Assetes>	listofAsset=opportunity.getApplicants().get(0).getAssetList();
		if(listofAsset.size()>0){
			/**remove assets  from POSTGRESSS and COUCHBASE*/ 
			posService.deleteApplicantAssets(opportunity);

			opportunity.getApplicants().get(0).setAssetList(new ArrayList<Assetes>());
		
		}
	}
}
