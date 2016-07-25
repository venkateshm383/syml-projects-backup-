package com.syml.mortgageapplication.impl;

import java.text.DecimalFormat;
import java.util.Map;

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

public class MortgageApplicationPage2PurService {
	MortgageApplicationPageService mortAppService = new MortgageApplicationPageService();
	PostGresDaoService postGresDaoService = null;
	CouchBaseService couchBaseService= null;
	CouchbaseServiceHelper couchbaseServiceHelper = new CouchbaseServiceHelper();
	Opportunity opportunity = null;
	 DecimalFormat decimalFormatter = new DecimalFormat("###");
	/** For Back Button */
	LendingTerm  lendingTerm =null;
	/**
	 * To Read form data form Data & call to PostgresDB for the mortgagePage2Pur Controller
	 * @param dynamicForm
	 * @param session
	 * @param paymtSrcList
	 * @return
	 * @throws MortgageApplicationPageServiceException
	 * @throws CouchbaseServiceException
	 * @throws PostGressDaoServiceException
	 */
	public Opportunity updatePage2PurDetails(DynamicForm dynamicForm,Session session,String[] paymtSrcList) throws MortgageApplicationPageServiceException {
		couchBaseService= new CouchBaseService();
		try{
		int crm_LeadId = mortAppService.parseLeadId(session);
		opportunity = couchbaseServiceHelper.getCouhbaseDataByKey(crm_LeadId);
		setFormDataInOpportunity(dynamicForm,session,opportunity);
		mortAppService.setPaymentSourceInOpportunity(opportunity,paymtSrcList);
		opportunity.setPogressStatus(20);
		
		postGresDaoService = new PostGresDaoService();
		postGresDaoService.insertOpportunityPage2Details(opportunity);
		couchBaseService.storeDataToCouchbase(MortgageApplicationConstants.MORTGAGE_APPLICATION_COUCHBASE_KEY+crm_LeadId, opportunity);
		return opportunity;
		}catch(MortgageApplicationPageServiceException mortAppExcpetion){
			throw new MortgageApplicationPageServiceException("Error when reading crm_LeadId Data from Couchbase / Invalid crm_LeadId ",mortAppExcpetion);
		}catch(PostGressDaoServiceException pgException){
			throw new MortgageApplicationPageServiceException("Error when inserting page2 Purchase Details into Postgress DB with given crm_LeadId"+opportunity.getId(), pgException);
		}catch(CouchbaseServiceException cbException){
			throw new MortgageApplicationPageServiceException("Error when inserting page2 Purchase Details into Couchbase DB with given crm_Leadid "+opportunity.getId(), cbException);
		}
	}
	
	/**
	 * Set form data into Opportunity POJO
	 * @param dynamicForm
	 * @param session
	 * @param opportunity
	 */
	private void setFormDataInOpportunity(DynamicForm dynamicForm,Session session,Opportunity opportunity) {
		String formattedAddress = dynamicForm.get("formatted_address");
		String downpayment30 = dynamicForm.get("downpayment30");
		String mlsList = dynamicForm.get("mlsList");
		String living4Financing = dynamicForm.get("living4Financing");
		Logger.debug("\n formattedAddress  " + formattedAddress
				+ "\n downpayment30  :" + downpayment30
				+ "\n bankAccount: " + "\n living4Financing  "
				+ living4Financing + "\n mlsList" + mlsList);
		
		opportunity.setDown_payment_amount(mortAppService.convertStringToDouble(downpayment30));
		opportunity.setLiveDetails(living4Financing);
		opportunity.setLiving_in_property(mortAppService.getLivingInPropertyOrMonthlyRentalIncome(living4Financing,dynamicForm,opportunity));
		opportunity.setPropertyAddress(formattedAddress);
		if (mlsList == null) {
			mlsList = "";
		}
		opportunity.setMls(mlsList);
		opportunity.setMlsListed(mlsList);
		
		@SuppressWarnings("unchecked")
		Map<String, String> addressMap = new SplitAddress().getProperAddressTwo(formattedAddress);
		opportunity.setAddress(addressMap.get("address1"));
		opportunity.setCity(addressMap.get("city"));
		opportunity.setProvince(addressMap.get("Province"));
		opportunity.setPostal_code(addressMap.get("postalcode"));
	}
	
	/** The Below code for the back button functionality */
	/**
	 * This method is for To set the Form value from the couchbase returned value for
	 * back button functionality
	 * @param opportunity
	 * @return
	 */
	
	public LendingTerm getPurchaseDetails(Opportunity opportunity) throws MortgageApplicationPageServiceException{
		Logger.info("Inside (.) getPurchaseDetails of MortgageApplicationPage2PurService ");
		lendingTerm = new LendingTerm();
		postGresDaoService = new PostGresDaoService();
		couchBaseService= new CouchBaseService();
		
		try {
		lendingTerm.setAddress(opportunity.getPropertyAddress());
		lendingTerm.setDownpayment(decimalFormatter.format(opportunity.getDown_payment_amount())+"");
		lendingTerm.setWhoWillLiving(opportunity.getLiveDetails());
		try{
		lendingTerm.setRentalAmount(decimalFormatter.format(opportunity.getMonthly_rental_income())+"");
		}catch(IllegalArgumentException e){}
		lendingTerm.setMlsListed(opportunity.getMls());
		mortAppService.setPaymentSrcTrueFalse(lendingTerm, opportunity);
		
		mortAppService.setPaymentSrcNull(opportunity);
		mortAppService.setAddressNull(opportunity);
		
		opportunity.setDown_payment_amount(null);
		opportunity.setLiveDetails(null);
		opportunity.setLiving_in_property(null);
		opportunity.setMonthly_rental_income(null);
		opportunity.setMlsListed(null);
		opportunity.setMls(null);
		
		opportunity.setPogressStatus(10);
		
		postGresDaoService.insertOpportunityPage2Details(opportunity);
		couchBaseService.storeDataToCouchbase(MortgageApplicationConstants.MORTGAGE_APPLICATION_COUCHBASE_KEY+opportunity.getId(), opportunity);

		return lendingTerm;
		} catch (PostGressDaoServiceException e) {
			throw new MortgageApplicationPageServiceException();
		} catch (CouchbaseServiceException e) {
			throw new MortgageApplicationPageServiceException();
		}
		
	}

}
