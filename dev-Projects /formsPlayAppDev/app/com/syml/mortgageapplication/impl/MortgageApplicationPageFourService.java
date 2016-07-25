package com.syml.mortgageapplication.impl;

import com.syml.couchbase.dao.CouchbaseServiceException;
import com.syml.couchbase.dao.service.CouchBaseService;
import com.syml.hibernate.dao.PostGressDaoServiceException;
import com.syml.hibernate.service.PostGresDaoService;
import com.syml.mortgageapplication.MortgageApplicationConstants;

import play.Logger;
import play.data.DynamicForm;
import controllers.Opportunity;

public class MortgageApplicationPageFourService {
	String formType = "Mortgage Application";
	String subForm = "Mortgage Application 4";
	PostGresDaoService posService=new PostGresDaoService();
	CouchBaseService couchBaseService=new CouchBaseService();
	
	String mortgageInMind = null;
	String mortgageTerm = null;
	String lookingForAmortize = null;
	String typedAmortizedYear = null;
	/**
	 * To map page4 value to opportunity POJO 
	 * @param opportunity
	 * @param dynamicForm
	 * @return Opportunity
	 */
	public Opportunity loadFormData(Opportunity opportunity,DynamicForm dynamicForm){
		mortgageInMind = dynamicForm.get("mortgageInMind");
		mortgageTerm = dynamicForm.get("mortgageTerm");
		lookingForAmortize = dynamicForm.get("lookingForAmortize");
		
		opportunity.setDesired_mortgage_type(getDesiredType(mortgageInMind));
		opportunity.setDesired_term(getMortgagemind(mortgageTerm));
		opportunity.setDesired_amortization(getDesiredMortgitionValue(lookingForAmortize, dynamicForm));
		opportunity.setPogressStatus(35);
		
		opportunity.setDesiredMortgageTypeCB(mortgageInMind);
		opportunity.setDesiredTermCB(mortgageTerm);
		opportunity.setDesiredAmortizationCB(lookingForAmortize);
		if(lookingForAmortize != null && lookingForAmortize.equalsIgnoreCase("Other")){
			opportunity.setAmortizeYear(opportunity.getDesired_amortization());
		}
		return opportunity;
	}
	
	/**
	 * To updateOpporunity with new value of page4 into Database and update same details into CouchBase 
	 * @param opportunity
	 * @return Opportunity
	 * @throws MortgageApplicationPageServiceException
	 */
	public Opportunity updateOpportunity(Opportunity opportunity) throws MortgageApplicationPageServiceException{
		try {
			posService.updateOpportunityPage4(opportunity);
			couchBaseService.storeDataToCouchbase(MortgageApplicationConstants.MORTGAGE_APPLICATION_COUCHBASE_KEY+opportunity.getId(), opportunity);

		} catch (PostGressDaoServiceException e) {
			throw new MortgageApplicationPageServiceException("Error In updating opporunity Details Into Db ",e);
		} catch (CouchbaseServiceException e) {
			throw new MortgageApplicationPageServiceException("Error In updating Opportunity Details Into Couchbase ",e);

		}
		return opportunity;
		
	}
	
	/**
	 * To get DesiredType  value of OpenErp based on Given Form Input Type 
	 * @param mortgageinmind
	 * @return
	 */
	public String getDesiredType(String mortgageinmind){
		if (mortgageinmind.equalsIgnoreCase("Variable")) {
			return "2";
		} else if (mortgageinmind.equalsIgnoreCase("Fixed")) {
			return "1";
		} else if (mortgageinmind.equalsIgnoreCase("Line of Credit")) {
			return "0";
		} else if (mortgageinmind.equalsIgnoreCase("Cashback")) {
			return "3";
		} else if (mortgageinmind.equalsIgnoreCase("Combination")) {
			return "5";
		}
		/* if (mortgageinmind.equalsIgnoreCase("Best Option")) {*/
		else 
			return "4";
	}
	
	/**
	 * To get MortgageInMind OpenErp values Based on given Form Input value
	 * @param mortgagemind
	 * @return String
	 */
	public String getMortgagemind(String mortgagemind){
		if (mortgagemind.equalsIgnoreCase("6 Month")) {
			return "2";
		} else if (mortgagemind.equalsIgnoreCase("1 Year")) {
			return "3";
		} else if (mortgagemind.equalsIgnoreCase("2 Year")) {
			return "4";
		} else if (mortgagemind.equalsIgnoreCase("3 Year")) {
			return "5";
		} else if (mortgagemind.equalsIgnoreCase("4 Year")) {
			return "6";
		} else if (mortgagemind.equalsIgnoreCase("5 Year")) {
			return "7";
		} else if (mortgagemind.equalsIgnoreCase("7 Year")) {
			return "8";
		} else if (mortgagemind.equalsIgnoreCase("10 Year")) {
			return "9";
		}
/*		if (mortgagemind.equalsIgnoreCase("None")) {*/
		else 
			return "1";
	}
	
	/**
	 * To getDesiredMortgageValue of OpenErp based on given Form Input value 
	 * @param lookingfor
	 * @param dynamicForm
	 * @return Integer
	 */
	public Integer getDesiredMortgitionValue(String lookingfor,DynamicForm dynamicForm){
		if (lookingfor.equalsIgnoreCase("10 Year"))
			return 10;
		else if (lookingfor.equalsIgnoreCase("15 Year"))
			return 15;
		else if (lookingfor.equalsIgnoreCase("20 Year"))
			return 20;
		else if (lookingfor.equalsIgnoreCase("25 Year"))
			return 25;
		else if (lookingfor.equalsIgnoreCase("30 Year"))
			return 30;
		else if (lookingfor.equalsIgnoreCase("Other")) {
			typedAmortizedYear = dynamicForm.get("amortizeYear");
			Logger.debug("typedAmtYear >>>>>>>>+++++>>>>> "+typedAmortizedYear);
				return Integer.parseInt(typedAmortizedYear.trim());
		}else
			return null;
	}
	
	/**
	 * To get getDesiredMortgageValue value of Form, Based on given Form Input value 
	 * @param lookingfor
	 * @param dynamicForm
	 * @return Integer
	 */
	public String getDesiredMortgitionValueForm(int lookingfor){
		if (lookingfor == 10)
			return "10 Year";
		else if (lookingfor == 15)
			return "15 Year";
		else if (lookingfor == 20)
			return "20 Year";
		else if (lookingfor == 25)
			return "25 Year";
		else if (lookingfor == 30)
			return "30 Year";
		/*else if (lookingfor != 10 .equalsIgnoreCase("Other")) {*/
		else 
			return "Other";
		
	}
}
