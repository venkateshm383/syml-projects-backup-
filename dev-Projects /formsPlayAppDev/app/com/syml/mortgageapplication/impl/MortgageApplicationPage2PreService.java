package com.syml.mortgageapplication.impl;

import java.text.DecimalFormat;

import com.syml.couchbase.dao.CouchbaseServiceException;
import com.syml.couchbase.dao.service.CouchBaseService;
import com.syml.couchbasehelper.CouchbaseServiceHelper;
import com.syml.hibernate.dao.PostGressDaoServiceException;
import com.syml.hibernate.service.PostGresDaoService;
import com.syml.mortgageapplication.MortgageApplicationConstants;

import controllers.LendingTerm;
import controllers.Opportunity;
import play.Logger;
import play.data.DynamicForm;
import play.mvc.Http.Session;

public class MortgageApplicationPage2PreService {
	MortgageApplicationPageService mortAppService = new MortgageApplicationPageService();
	PostGresDaoService postGresDaoService = null;
	CouchBaseService couchBaseService= null;
	CouchbaseServiceHelper couchbaseServiceHelper = new CouchbaseServiceHelper();
	Opportunity opportunity = null;
	 DecimalFormat decimalFormatter = new DecimalFormat("###");
	LendingTerm lendingTerm=null;
	/**
	 * To Read form data form Data & call to PostgresDB for the mortgagePage2Pre Controller
	 * @param dynamicForm
	 * @param session
	 * @param paymtSrcList
	 * @return
	 * @throws MortgageApplicationPageServiceException
	 * @throws CouchbaseServiceException
	 * @throws PostGressDaoServiceException
	 */
	public Opportunity updatePage2PreDetails(DynamicForm dynamicForm,Session session,String[] paymtSrcList) throws MortgageApplicationPageServiceException{
		Logger.info("Inside (.) updatePage2PreDetails of MortgageApplicationPage2PreService ");
		couchBaseService= new CouchBaseService();
		try{
		int crm_LeadId = mortAppService.parseLeadId(session);
		opportunity = couchbaseServiceHelper.getCouhbaseDataByKey(crm_LeadId);
		setFormDataInOpportunity(dynamicForm,session,opportunity);
		mortAppService.setPaymentSourceInOpportunity(opportunity,paymtSrcList);
		Logger.debug("Bank_account>>>>>>>> "+opportunity.getBank_account());
		opportunity.setPogressStatus(20);
		
		
		
		postGresDaoService = new PostGresDaoService();
		postGresDaoService.insertOpportunityPage2Details(opportunity);
		couchBaseService.storeDataToCouchbase(MortgageApplicationConstants.MORTGAGE_APPLICATION_COUCHBASE_KEY+ crm_LeadId, opportunity);
		Logger.debug("Page2 data successfully updated in PostgresDB and CouchbaseDB ");
		return opportunity;
		}catch(MortgageApplicationPageServiceException mortAppExcpetion){
			throw new MortgageApplicationPageServiceException("Error when reading crm_LeadId Data from Couchbase / Invalid crm_LeadId "+opportunity.getId(), mortAppExcpetion);
		}catch(PostGressDaoServiceException pgException){
			throw new MortgageApplicationPageServiceException("Error when inserting page2 Pre-Approval Details into Postgress DB with given crm_LeadId"+opportunity.getId(), pgException);
		}catch(CouchbaseServiceException cbException){
			throw new MortgageApplicationPageServiceException("Error when inserting page2 Pre-Approval Details into Couchbase DB with given crm_Leadid "+opportunity.getId(), cbException);
		}
	}
	
	/**
	 * Set form data into Opportunity POJO
	 * @param dynamicForm
	 * @param session
	 * @param opportunity
	 */
	private void setFormDataInOpportunity(DynamicForm dynamicForm,Session session,Opportunity opportunity) {
		Logger.info("Inside (.) MortgageApplicationPage2PreService ");
		String leadingGoal = (String) session.get("leadingGoal");
		String province = dynamicForm.get("provience");
		String purchaseprice = dynamicForm.get("purchaseprice");
		String downpayment = dynamicForm.get("downpayment");
		String living4Financing = dynamicForm.get("living4Financing");
		String additionalApplicants = dynamicForm.get("additionalApplicants");
		Logger.debug("provience " + province + "\n purchaseprice :"
				+ purchaseprice + "\n downpayment :" + downpayment
				+ "\n living4Financing  " + living4Financing
				+ "\n leadin goal " + leadingGoal + "\n additionalApplicants  "
				+ additionalApplicants);
		opportunity.setProvince(province);
		opportunity.setDown_payment_amount(mortAppService.convertStringToDouble(downpayment));
		opportunity.setPurchase_price(mortAppService.convertStringToDouble(purchaseprice));
		opportunity.setLiveDetails(living4Financing);
		opportunity.setLiving_in_property(mortAppService.getLivingInPropertyOrMonthlyRentalIncome(living4Financing,dynamicForm,opportunity));
	}
	
	/** The Below code for the back button functionality */
	
	/**
	 * This method is for To set the Form value from the COUCHBASE returned value for
	 * back button functionality
	 * @param opportunity
	 * @return
	 * @throws MortgageApplicationPageServiceException 
	 */
	
	public LendingTerm getPreApprovalDetails(Opportunity opportunity) throws MortgageApplicationPageServiceException{
		Logger.info("Inside (.) getPreApprovalDetails of MortgageApplicationPage2PurService ");
		lendingTerm = new LendingTerm();
		postGresDaoService = new PostGresDaoService();
		couchBaseService= new CouchBaseService();
		try {
			
			
		lendingTerm = new LendingTerm();
		
		
		lendingTerm.setProvince(opportunity.getProvince());
		lendingTerm.setDownpayment(decimalFormatter.format(opportunity.getDown_payment_amount())+"");
		lendingTerm.setPurchasePrice(decimalFormatter.format(opportunity.getPurchase_price())+"");
		lendingTerm.setWhoWillLiving(opportunity.getLiveDetails());
		try{
		lendingTerm.setRentalAmount(decimalFormatter.format(opportunity.getMonthly_rental_income())+"");
		}catch(IllegalArgumentException e){}
		mortAppService.setPaymentSrcTrueFalse(lendingTerm, opportunity);
		
		mortAppService.setPaymentSrcNull(opportunity);
		
		opportunity.setProvince(null);
		opportunity.setDown_payment_amount(null);
		opportunity.setPurchase_price(null);
		opportunity.setLiveDetails(null);
		opportunity.setPropertyAddress(null);
		opportunity.setLiving_in_property(null);
		opportunity.setMonthly_rental_income(null);
		opportunity.setPogressStatus(10);
		
			postGresDaoService.insertOpportunityPage2Details(opportunity);
			couchBaseService.storeDataToCouchbase(MortgageApplicationConstants.MORTGAGE_APPLICATION_COUCHBASE_KEY+opportunity.getId(), opportunity);
			
			return lendingTerm;
		} catch (PostGressDaoServiceException e) {
			throw new MortgageApplicationPageServiceException();
		}catch (CouchbaseServiceException e1) {
			throw new MortgageApplicationPageServiceException();
		}
		
	}
	
}


