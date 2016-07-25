package com.syml.mortgageapplication.impl;
import java.text.DecimalFormat;
/**
 * 
 */
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import play.Logger;
import play.data.DynamicForm;
import play.mvc.Http.Session;

import com.syml.SplitAddress;
import com.syml.couchbase.dao.CouchbaseServiceException;
import com.syml.couchbase.dao.service.CouchBaseService;
import com.syml.couchbasehelper.CouchbaseServiceHelper;
import com.syml.hibernate.dao.PostGressDaoServiceException;
import com.syml.hibernate.service.PostGresDaoService;
import com.syml.mortgageapplication.MortgageApplicationConstants;

import controllers.LendingTerm;
import controllers.Opportunity;

public class MortgageApplicationPage2RefService {
	MortgageApplicationPageService mortAppService = new MortgageApplicationPageService();
	PostGresDaoService postGresDaoService = null;
	CouchBaseService couchBaseService= null;
	CouchbaseServiceHelper couchbaseServiceHelper = new CouchbaseServiceHelper();
	Opportunity opportunity = null;
	 DecimalFormat decimalFormatter = new DecimalFormat("###");
	LendingTerm lendingTerm=null;
	/**
	 * To Read form data  & call to PostgresDB for the mortgagePage2Ref Controller
	 * @param dynamicForm
	 * @param session
	 * @param paymtSrcList
	 * @return
	 * @throws MortgageApplicationPageServiceException
	 * @throws CouchbaseServiceException
	 * @throws PostGressDaoServiceException
	 */
	public Opportunity updatePage2RefDetails(DynamicForm dynamicForm,Session session,String[] paymtSrcList) throws MortgageApplicationPageServiceException {
		Logger.info("Inside (.) updatePage2RefDetails of MortgageAppliationPage2RefService " );
		couchBaseService= new CouchBaseService();
		try{
		int crm_LeadId = mortAppService.parseLeadId(session);
		opportunity = couchbaseServiceHelper.getCouhbaseDataByKey(crm_LeadId);
		setFormDataInOpportunity(dynamicForm,session,opportunity);
		setAdditionalFundsInOpportunity(opportunity,paymtSrcList);
		opportunity.setPogressStatus(20);
		postGresDaoService = new PostGresDaoService();
		postGresDaoService.insertOpportunityPage2Details(opportunity);
		couchBaseService.storeDataToCouchbase(MortgageApplicationConstants.MORTGAGE_APPLICATION_COUCHBASE_KEY+crm_LeadId, opportunity);
		return opportunity;
		}catch(MortgageApplicationPageServiceException mortAppExcpetion){
			throw new MortgageApplicationPageServiceException("Error when reading crm_LeadId Data from Couchbase / Invalid crm_LeadId "+opportunity.getId(), mortAppExcpetion);
		}catch(PostGressDaoServiceException pgException){
			throw new MortgageApplicationPageServiceException("Error when inserting page2 Refinance Details into Postgress DB with given crm_LeadId"+opportunity.getId(), pgException);
		}catch(CouchbaseServiceException cbException){
			throw new MortgageApplicationPageServiceException("Error when inserting page2 Refinance Details into Couchbase DB with given crm_Leadid "+opportunity.getId(), cbException);
		}
	}
	
	/**
	 * Set form data into Opportunity POJO
	 * @param dynamicForm
	 * @param session
	 * @param opportunity
	 */
	private void setFormDataInOpportunity(DynamicForm dynamicForm,Session session,Opportunity opportunity) {
		String propertyAddress = dynamicForm.get("formatted_address");
		String refinancing = dynamicForm.get("refivalue");
		String refiAdditionalAmount = dynamicForm.get("additionalFunds");
		String living4Financing = dynamicForm.get("living4Financing");
		Logger.debug("\n formattedAddress  " + propertyAddress
				+ "\n refinancing1  :" + refinancing
				+ "\n bankAccount: " + "\n living4Financing  "
				+ living4Financing);
		
		Logger.debug(refinancing+"<------------->"+refiAdditionalAmount);
		try{
	Float LtvValue = 	(Float.valueOf(refiAdditionalAmount)/Float.valueOf(refinancing))*100;
	Logger.debug("LtvValue : "+LtvValue);
	
	opportunity.setLtv(LtvValue);
	
		}catch(ArithmeticException e){}	
		
		opportunity.setLiveDetails(living4Financing);
		opportunity.setLiving_in_property(mortAppService.getLivingInPropertyOrMonthlyRentalIncome(living4Financing,dynamicForm,opportunity));
		opportunity.setProperty_value(mortAppService.convertStringToDouble(refinancing));
		opportunity.setAdditional_amount(mortAppService.convertStringToDouble(refiAdditionalAmount));
		opportunity.setPropertyAddress(propertyAddress);
		
		Map<String, String> addressMap = new SplitAddress().getProperAddressTwo(propertyAddress);
		opportunity.setAddress(addressMap.get("address1"));
		opportunity.setCity(addressMap.get("city"));
		opportunity.setProvince(addressMap.get("Province"));
		opportunity.setPostal_code(addressMap.get("postalcode"));
		
	}

	/**
	 * Set paymentSource into Opportunity POJO
	 * @param opportunity
	 */
	private void setAdditionalFundsInOpportunity(Opportunity opportunity, String[] paymtSrcList) {
		Set<String> additionalFunds=new HashSet<String>();
		for (String paymtSrc : paymtSrcList) {
			String[] splits = paymtSrc.replaceAll("^\\s*\\[|\\]|\\s*$\"",
					"").split("\\s*,\\s*");
			Logger.info("Inside for loop " + splits);
		for (String mixedString : splits) {
			String paymentSource = mixedString.replace("\"", "");
			if (paymentSource.equalsIgnoreCase("BuyProperty"))
				additionalFunds.add("Buy Property");
			else if (paymentSource.equalsIgnoreCase("PayOffDebt"))
				additionalFunds.add("Pay Off Debt");
			else if (paymentSource.equalsIgnoreCase("BuyInvestment"))
				additionalFunds.add("Buy Investment");
			else if (paymentSource.equalsIgnoreCase("BuyVehicle"))
				additionalFunds.add("Buy Vehicle");
			else if (paymentSource.equalsIgnoreCase("Renovate"))
				additionalFunds.add("Renovate");
			else if (paymentSource.equalsIgnoreCase("Refurnish"))
				additionalFunds.add("Refurnish");
			else if (paymentSource.equalsIgnoreCase("Vacation"))
				additionalFunds.add("Vacation");
			else if (paymentSource.equalsIgnoreCase("RecVehicle"))
				additionalFunds.add("Rec Vehicle");
			else if (paymentSource.equalsIgnoreCase("Other"))
				additionalFunds.add("Other");
			}
		}
		
		opportunity.setAdditionalFunds(additionalFunds);
	}
	
	/** The Below code for the back button functionality */
	/**
	 * This method is for To set the Form value from the COUCHBASE returned value for
	 * back button functionality
	 * @param opportunity
	 * @return
	 * @throws MortgageApplicationPageServiceException 
	 */
	public LendingTerm getRefinanceDetails(Opportunity opportunity) throws MortgageApplicationPageServiceException{
		
		Logger.info("Inside (.) getRefinanceDetails of MortgageApplicationPage2RefService ");
		lendingTerm = new LendingTerm();
		postGresDaoService = new PostGresDaoService();
		couchBaseService= new CouchBaseService();
		try {
		lendingTerm.setAddress(opportunity.getPropertyAddress());
		try{
		lendingTerm.setMarketValue(decimalFormatter.format(opportunity.getProperty_value())+"");
		}catch(IllegalArgumentException e){}
		try{
		lendingTerm.setAdditionalAmount(decimalFormatter.format(opportunity.getAdditional_amount())+"");
		}catch(IllegalArgumentException e){}
		lendingTerm.setWhoWillLiving(opportunity.getLiveDetails());
		try{
		lendingTerm.setRentalAmount(decimalFormatter.format(opportunity.getMonthly_rental_income())+"");
		}catch(IllegalArgumentException e){}
		setAdditionalFunds(lendingTerm, opportunity.getAdditionalFunds());
		
		mortAppService.setAddressNull(opportunity);
		opportunity.setProperty_value(null);
		opportunity.setAdditional_amount(null);
		opportunity.setLiveDetails(null);
		opportunity.setLiving_in_property(null);
		opportunity.setAdditionalFunds(null);
		opportunity.setPropertyAddress(null);
		opportunity.setMonthly_rental_income(null);
		opportunity.setPogressStatus(10);
		
		postGresDaoService.insertOpportunityPage2Details(opportunity);
		couchBaseService.storeDataToCouchbase(MortgageApplicationConstants.MORTGAGE_APPLICATION_COUCHBASE_KEY+opportunity.getId(), opportunity);
		
		return lendingTerm;
		} catch (PostGressDaoServiceException e) {
			throw new MortgageApplicationPageServiceException("Error when reading and inserting null");
		} catch (CouchbaseServiceException e) {
			throw new MortgageApplicationPageServiceException();
		}
		
	}
	/**
	 * set additional funds true false for form displaying
	 * @param lendingTerm
	 * @param additionalFunds
	 * @return
	 */
	private LendingTerm setAdditionalFunds(LendingTerm lendingTerm,Set<String> additionalFunds){
		for(String additionalFund:additionalFunds){
			if(additionalFund.equalsIgnoreCase("Buy Property"))
				lendingTerm.setBuyProperty(true);
			if(additionalFund.equalsIgnoreCase("Pay Off Debt"))
				lendingTerm.setPayOffDebt(true);
			if(additionalFund.equalsIgnoreCase("Buy Investment"))
				lendingTerm.setBuyInvestments(true);
			if(additionalFund.equalsIgnoreCase("Buy Vehicle"))
				lendingTerm.setBuyVehicle(true);
			if(additionalFund.equalsIgnoreCase("Renovate"))
				lendingTerm.setRenovate(true);
			if(additionalFund.equalsIgnoreCase("Refurnish"))
				lendingTerm.setRefurnish(true);
			if(additionalFund.equalsIgnoreCase("Vacation"))
				lendingTerm.setVacation(true);
			if(additionalFund.equalsIgnoreCase("Rec Vehicle"))
				lendingTerm.setRecVehicle(true);
			if(additionalFund.equalsIgnoreCase("Other"))
				lendingTerm.setOther(true);
		}
		
		return lendingTerm;
	}
	
}
