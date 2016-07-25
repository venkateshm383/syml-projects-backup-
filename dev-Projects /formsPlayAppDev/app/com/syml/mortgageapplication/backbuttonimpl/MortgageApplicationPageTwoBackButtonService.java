package com.syml.mortgageapplication.backbuttonimpl;

import com.syml.couchbasehelper.CouchbaseServiceHelper;
import com.syml.mortgageapplication.impl.MortgageApplicationPageServiceException;

import controllers.LendingTerm;
import controllers.Opportunity;

public class MortgageApplicationPageTwoBackButtonService {

	
	CouchbaseServiceHelper couchbaseServiceHelper=new CouchbaseServiceHelper();

	public Opportunity getOpporunity(int opportunityID) throws MortgageApplicationPageServiceException{
		Opportunity opportunity=null;
		 opportunity=couchbaseServiceHelper.getCouhbaseDataByKey(opportunityID);
		return opportunity;
		
	}
	
	
	public LendingTerm getRefinanceData(Opportunity opportunity){
		LendingTerm leTerm=new LendingTerm();
		leTerm.setAddress(opportunity.getAddress());
		leTerm.setMarketValue(opportunity.getProperty_value()+"");
		leTerm.setAdditionalAmount(opportunity.getAdditional_amount()+"");
		
		
		return null;
	}
	
	public LendingTerm getDownPaymentSource(Opportunity opportunity){

		return null;
	}
}
