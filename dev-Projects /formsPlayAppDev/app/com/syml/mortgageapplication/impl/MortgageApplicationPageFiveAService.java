package com.syml.mortgageapplication.impl;

import play.data.DynamicForm;

import com.syml.couchbase.dao.CouchbaseServiceException;
import com.syml.couchbase.dao.service.CouchBaseService;
import com.syml.hibernate.dao.PostGressDaoServiceException;
import com.syml.hibernate.service.PostGresDaoService;
import com.syml.mortgageapplication.MortgageApplicationConstants;

import controllers.Opportunity;

public class MortgageApplicationPageFiveAService {
	String subForm = "Mortgage Application 5a";
	
	PostGresDaoService posService=new PostGresDaoService();
	CouchBaseService couchBaseService=new CouchBaseService();
	
	/**
	 * To Map Page  Five  values to Opportunity POJO
	 * @param opportunity
	 * @param dynamicForm
	 * @return Opportunity
	 */
	public Opportunity loadFormData(Opportunity opportunity,DynamicForm dynamicForm){
		String incomeDecreasedWorried = dynamicForm.get("mybills");
		String largerFamily = dynamicForm.get("largerFamily");
		String buyNewVehicle = dynamicForm.get("buyNewVehicle");
		String recreatStoreHome = dynamicForm.get("recreatStoreHome");
		String riskTaker = dynamicForm.get("riskTaker");
		
		opportunity.setIncome_decreased_worried(getIncomeDcreased(incomeDecreasedWorried));
		opportunity.setFuture_family(getFutureFamily(largerFamily));
		opportunity.setBuy_new_vehicle(getBuyNewVehicle(buyNewVehicle));
		opportunity.setLifestyle_change(getLifeStyle(recreatStoreHome));
		opportunity.setFinancial_risk_taker(getFinancialRisktaker(riskTaker));
		opportunity.setPogressStatus(40);
		
		/** For CouchBase storing purpose */
		opportunity.setIncomeDecreasedWorried(incomeDecreasedWorried);
		opportunity.setFutureFamily(largerFamily);
		opportunity.setBuyNewVehicle(buyNewVehicle);
		opportunity.setLifestyleChange(recreatStoreHome);
		opportunity.setFinancialRiskTaker(riskTaker);
		return opportunity;
	}
	/**
	 * To update Opportunity with new Values in DataBase and update CouchBase 
	 * @param opportunity
	 * @return Opportunity
	 * @throws MortgageApplicationPageServiceException
	 */
	public Opportunity updateOpportunity(Opportunity opportunity) throws MortgageApplicationPageServiceException{
		try {
			posService.updateOpportunityPage5a(opportunity);
			couchBaseService.storeDataToCouchbase(MortgageApplicationConstants.MORTGAGE_APPLICATION_COUCHBASE_KEY+opportunity.getId(), opportunity);

		} catch (PostGressDaoServiceException e) {
			throw new MortgageApplicationPageServiceException("Error In updating opportunity Details Into Db ",e);
		} catch (CouchbaseServiceException e) {
			throw new MortgageApplicationPageServiceException("Error In updating Opportunity Details Into Couchbase ",e);

		}
		return opportunity;
		
	}
	

	/**
	 * To  OpenErp Values for IncomeDcreased  based on given  Form values 
	 * @param incomeDcreased
	 * @return String
	 */
	public String getIncomeDcreased(String incomeDcreased){
		if (incomeDcreased != null  && incomeDcreased.equalsIgnoreCase("Yes")) {
			return "5";
		} else if (incomeDcreased != null && incomeDcreased.equalsIgnoreCase("No")) {
			return "1";
		} else {
			return "3";
		}
	}
	
	/** 
	 * To get FutureFamily OpenErp Value based on Given Form input values 
	 * @param futureFamily
	 * @return String
	 */
	public String getFutureFamily(String futureFamily){
		if (futureFamily != null && futureFamily.equalsIgnoreCase("Yes")) {
			return "5";
		} else if (futureFamily != null && futureFamily.equalsIgnoreCase("No")) {
			return "1";
		} else{ 
			return "3";
		}
	}
	
	/**
	 * To get buyingNewVechile OpenErp Values based on given Form Input value
	 * @param buyingNewVechile
	 * @return String
	 */
	public String getBuyNewVehicle(String buyingNewVechile){
		if (buyingNewVechile != null && buyingNewVechile.equalsIgnoreCase("Yes")) {
			return "5";
		} else if (buyingNewVechile != null
				&& buyingNewVechile.equalsIgnoreCase("No")) {
			return "1";
		} else 
			return "3";
	}
	
	/**
	 * To get LifeStyle Value of OpenErp Based on Given Form input values 
	 * @param lifestyle
	 * @return String
	 */
	public String getLifeStyle(String lifestyle){
		if (lifestyle != null && lifestyle.equalsIgnoreCase("Yes")) {
			return "5";
		} else if (lifestyle != null && lifestyle.equalsIgnoreCase("No")) {
			return "1";
		} else
			return "3";
	}
	
	/**
	 * To get FinancialRiskTaker Value of OpenErp Based on Given Form Input value 
	 * @param financialRisk
	 * @return String
	 */
	public String getFinancialRisktaker(String financialRisk){
		if (financialRisk != null && financialRisk.equalsIgnoreCase("Yes")) {
			return "5";
		} else if (financialRisk != null && financialRisk.equalsIgnoreCase("No")) {
			return "1";
		} else
			return "3";
		}
}
