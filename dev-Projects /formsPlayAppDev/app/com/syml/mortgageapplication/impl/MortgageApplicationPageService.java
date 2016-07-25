package com.syml.mortgageapplication.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import play.Logger;
import play.data.DynamicForm;
import play.mvc.Http.Session;

import com.syml.couchbasehelper.CouchbaseServiceHelper;
import com.syml.mortgageapplication.MortgageApplicationConstants;

import controllers.LendingTerm;
import controllers.Opportunity;


public class MortgageApplicationPageService {
	
	CouchbaseServiceHelper couchbaseServiceHelper=new CouchbaseServiceHelper();
	
	
	/**
	 * To check expiryDate of Mortgage Application URL link
	 * @param opportunity
	 * @return boolean 
	 * @throws MortgageApplicationPageServiceException
	 */
	public boolean checkExpireDate(Opportunity opportunity) throws MortgageApplicationPageServiceException{
		
		Logger.debug("inside (.) checkExpireDate  method  ");
		boolean isExpired=false;
		if(opportunity.getExpireDate()==null||opportunity.getExpireDate().isEmpty())
			throw new MortgageApplicationPageServiceException("Error Expirdate not given in Link for the opportubity Id= "+opportunity.getId());
	
		
			String pattern = "dd/MM/yyyy";
			String today = new SimpleDateFormat(pattern).format(new Date());
			Date expDate;
			try {
				expDate = new SimpleDateFormat("dd/MM/yyyy",
						Locale.ENGLISH).parse(opportunity.getExpireDate());
			
			Date end = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
					.parse(today);
			isExpired = expDate.compareTo(end) >= 0;

			} catch (ParseException e) {
				throw new MortgageApplicationPageServiceException("Error in parsing ExpiryDate For given opporunityid="+opportunity.getId() +" and given ExpiryDate ="+opportunity.getExpireDate());
			}
		
		return isExpired;
	}
	
	/**
	 * To check device Type used to  fill the Mortgage Application form 
	 * @param deviceType
	 * @param opportunity
	 * @return opportunity 
	 */
	public Opportunity checkDeviceType(String deviceType,Opportunity opportunity){
		if (deviceType!=null&&deviceType.contains("mobile")
				|| deviceType.contains("Mobile")) {
			
			opportunity.getForms().setMobile(true);
			opportunity.getForms().setDeviceType("Mobile");
		}else{
			
			/*opportunity.getForms().setMobile(true);
			opportunity.getForms().setDeviceType("Mobile");*/
			
			opportunity.getForms().setDeviceType("Desktop");


		}
		return opportunity;
	}
	
	/**
	 * 
	 * @param session
	 * @return
	 * CHECK FOR DEVICE TYPE IN SESSION.
	 */ 
	public boolean checkDeviceBySetInSession(Session session){
		boolean isMobileDevice=false;
		try{
		String mobilePhone = session.get("isMobile");
		if (mobilePhone != null && !mobilePhone.isEmpty()) {
			if (mobilePhone.equalsIgnoreCase("isMobile")) {
				isMobileDevice = true;
			}
		}
		Logger.info("Mobile devicetype Found in session Object ="+isMobileDevice);
		}catch(Exception e){
		Logger.error("Error parsing session object and to get DeviceType From Session ",e);	
		}
		return isMobileDevice;
	}

	
	/**
	 * To check Opportunity Exist in COUCHBASE or not  
	 * @param opportunity
	 * @return Opportunity
	 * @throws MortgageApplicationPageServiceException
	 */
	public Opportunity checkOpporunityExist(Opportunity opportunity) {
		
		
	Opportunity oldOpporunity = null;
	try {
		oldOpporunity = couchbaseServiceHelper.getCouhbaseDataByKey(opportunity.getId());
		oldOpporunity.getForms().setDeviceType(opportunity.getForms().getDeviceType());

	} catch (MortgageApplicationPageServiceException e) {
		Logger.error("Opporunity is Filling the form First Time so error To get OldOpporunity Deatails from Couchbase, Given OpporunityDetails= "+opportunity.getOpportunity_id(),e);
	}
	
	
		return oldOpporunity;
	}
	
	/**
	 * To Parse the value   crmLeadId in Session to Integer 
	 * @param session
	 * @return Integer 
	 * @throws MortgageApplicationPageServiceException
	 */
	public int parseLeadId(Session session) throws MortgageApplicationPageServiceException{
		Logger.debug("session Object "+session);

		int crm_LeadId = 0;
		try {
			crm_LeadId = Integer.parseInt(session.get("crmLeadId")
					.toString());
			if(crm_LeadId==0)
				throw new MortgageApplicationPageServiceException("Error CRM Lead should not be Zero    "+crm_LeadId);

		} catch (NumberFormatException e) {
		throw new MortgageApplicationPageServiceException("Error in parsing LeadID from String to integer given Lead ID= "+crm_LeadId,e);
		}
		return crm_LeadId;
		
	}
	
	/**
	 * To convert form's String value into selection value of openERP for field what_is_your_lending_goal.
	 * @param inputLendingGoal
	 * @return
	 */
	public String getLendingGoalForPG(String inputLendingGoal){
		String lendingGoalPG="";
		if(inputLendingGoal != null && inputLendingGoal.equalsIgnoreCase(MortgageApplicationConstants.PRE_APPROVAL_LENDING_GOAL))
			lendingGoalPG = MortgageApplicationConstants.PRE_APPROVAL_LENDING_GOAL_ID;
		else if(inputLendingGoal != null && inputLendingGoal.equalsIgnoreCase(MortgageApplicationConstants.PURCHASE_LENDING_GOAL))
				lendingGoalPG=MortgageApplicationConstants.PURCHASE_LENDING_GOAL_ID;
		else if(inputLendingGoal != null && inputLendingGoal.equalsIgnoreCase(MortgageApplicationConstants.REFINANCE_LENDING_GOAL))
			lendingGoalPG=MortgageApplicationConstants.REFINANCE_LENDING_GOAL_ID;
		return lendingGoalPG;
	}
	
	/**
	 *To Convert form String value into required Double value 
	 * @param inputString
	 * @return
	 */
	public Double convertStringToDouble(String inputString) {
		Double result = null;
		if(inputString != null && !inputString.equalsIgnoreCase("")){
			try{
			result = Double.parseDouble(inputString);
			}catch(Exception pe){
				Logger.error("Error when parsing string to double ",pe);
			}
		}
		return result;
	}
	/**
	 * To Convert Form String Value into required Integer value
	 * @param inputString
	 * @return
	 */
	public Integer convertStringToInteger(String inputString) {
		Integer result = null;
		if(inputString != null && !inputString.equalsIgnoreCase("")){
			try{
			result = Integer.parseInt(inputString);
			}catch(Exception pe){
				Logger.error("Error when parsing string to Integer ",pe);
			}
		}
		return result;
	}
	
	/**
	 * To convert form's String value into selection value of openERP for field Living_in_property.
	 * @param living4Financing
	 * @param dynamicForm
	 * @param opportunity
	 * @return
	 */
	public  Integer getLivingInPropertyOrMonthlyRentalIncome(String living4Financing,DynamicForm dynamicForm,Opportunity opportunity) {
		Integer livingInProperty=null;
		Logger.debug("living4Financing   "+living4Financing);
		if (living4Financing != null && living4Financing.equalsIgnoreCase(MortgageApplicationConstants.LIVING_IN_PROPERTY_RENTER)) {
			Logger.debug("Renter");
			String rentalAmount = dynamicForm.get("rentalAmount");
			opportunity.setMonthly_rental_income(convertStringToDouble(rentalAmount));
			livingInProperty = 2;	
		} else if (living4Financing != null && living4Financing.trim().equalsIgnoreCase(MortgageApplicationConstants.LIVING_IN_PROPERTY_OWNER_AND_RENTER)) {
			Logger.debug("Owner and Rente");
			String rentalAmount = dynamicForm.get("rentalAmount");			
			opportunity.setMonthly_rental_income(convertStringToDouble(rentalAmount));
			livingInProperty = 3;			
		} else if (living4Financing != null && living4Financing.equalsIgnoreCase(MortgageApplicationConstants.LIVING_IN_PROPERTY_OWNER_MYSELF)) {
			Logger.debug("Owner Myself");
			livingInProperty = 1;
		}
		return livingInProperty;
	}
	
	
	/**
	 * Set paymentSource into Opportunity POJO
	 * @param opportunity
	 */
	public void setPaymentSourceInOpportunity(Opportunity opportunity, String[] paymtSrcList) {
		
		for (String paymtSrc : paymtSrcList) {
			String[] splits = paymtSrc.replaceAll("^\\s*\\[|\\]|\\s*$\"", "")
					.split("\\s*,\\s*");
			Logger.info("Inside for loop " + splits);
			for (String s : splits) {
				String paymentSource = s.replace("\"", "");
				if (paymentSource.equalsIgnoreCase("BankAccount")) {
					Logger.info("set BankAccount " + paymentSource);
					opportunity.setBank_account(1.0);
				} else if (paymentSource.equalsIgnoreCase("RRSPS")) {
					Logger.info("set RRSPS " + paymentSource);					
					opportunity.setRrsp_amount(1.0);
				} else if (paymentSource.equalsIgnoreCase("Investments")) {
					Logger.info("Investment " + paymentSource);					
					opportunity.setInvestment(1.0);
				} else if (paymentSource.equalsIgnoreCase("Borrowed")) {
					Logger.info("Borrowed " + paymentSource);					
					opportunity.setBorrowed_amount(1.0);
				} else if (paymentSource.equalsIgnoreCase("SaleofProperty")) {
					Logger.info("SaleofProperty " + paymentSource);					
					opportunity.setSale_of_existing_amount(1.0);
				} else if (paymentSource.equalsIgnoreCase("Gift")) {
					Logger.info("set Gift " + paymentSource);					
					opportunity.setGifted_amount(1.0);
				} else if (paymentSource.equalsIgnoreCase("PersonalCash")) {
					Logger.info("set PersonalCash " + paymentSource);					
					opportunity.setPersonal_cash_amount(1.0);
				} else if (paymentSource.equalsIgnoreCase("ExistingEquity")) {
					Logger.info("set Existing Equity" + paymentSource);					
					opportunity.setExisting_equity_amount(1.0);
				} else if (paymentSource.equalsIgnoreCase("SweatEnquity")) {
					Logger.info("set SweatEnquity");					
					opportunity.setSweat_equity_amount(1.0);
				}
			}
		}
		
	}
	
	/**
	 * Set paymentSource into LendingTerm POJO as true if it is not null for form displaying
	 * @param lendingTerm
	 * @param opportunity
	 * @return
	 */
	public LendingTerm setPaymentSrcTrueFalse(LendingTerm lendingTerm,Opportunity opportunity){
		if(opportunity.getBank_account() != null)
			lendingTerm.setBankAccount(true);
		if(opportunity.getRrsp_amount() != null)
			lendingTerm.setRrsps(true);
		if(opportunity.getInvestment() != null)
			lendingTerm.setInvestments(true);
		if(opportunity.getBorrowed_amount() != null)
			lendingTerm.setBorrowed(true);
		if(opportunity.getSale_of_existing_amount() != null)
			lendingTerm.setSaleOfProperty(true);
		if(opportunity.getGifted_amount() != null)
			lendingTerm.setGift(true);
		if(opportunity.getPersonal_cash_amount() != null)
			lendingTerm.setPersonalCash(true);
		if(opportunity.getExisting_equity_amount() != null)
			lendingTerm.setExistingEquity(true);
		if(opportunity.getSweat_equity_amount() != null)
			lendingTerm.setSweetEquity(true);
		return lendingTerm;
	}
	
	/**
	 * To delete payment source list from the POSTGRESS as well as COUCHBASE need to be set null
	 * @param opportunity
	 */
	
	public void setPaymentSrcNull(Opportunity opportunity){
		opportunity.setBank_account(null);
		opportunity.setRrsp_amount(null);
		opportunity.setInvestment(null); 
		opportunity.setBorrowed_amount(null);
		opportunity.setSale_of_existing_amount(null);
		opportunity.setGifted_amount(null);
		opportunity.setPersonal_cash_amount(null);
		opportunity.setExisting_equity_amount(null);
		opportunity.setSweat_equity_amount(null);
	}
	 
	/**
	 * To delete address  from the POSTGRESS as well as COUCHBASE need to be set null 
	 * @param opportunity
	 */
	public void setAddressNull(Opportunity opportunity){
		opportunity.setAddress(null);
		opportunity.setCity(null);
		opportunity.setProvince(null);
		opportunity.setPostal_code(null);
	}
	
	/**
	 * GET THE GARAGE TYPE FROM COUCHBASE ON CONDITION .
	 * @param garageType
	 * @return
	 */
	public String getGarageType(String garageType) {
		if (garageType != null && garageType.equalsIgnoreCase("1")) {
			return "Attached";
		} else if (garageType != null
				&& garageType.equalsIgnoreCase("2")) {
			return "Detached";
		} else
			return "None";
	}
	
}
