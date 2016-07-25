package com.syml.service.rule;

import com.syml.domain.Opportunity;

public abstract class BaseChecking extends Thread {

	protected boolean checkingDone;
	protected RuleResult ruleResult; 
	protected Opportunity opp;

	
	
	public BaseChecking(RuleResult ruleResult, Opportunity opp) {
		super();
		this.ruleResult = ruleResult;
		this.opp = opp;
	}


	public boolean isCheckingDone() {
		// TODO Auto-generated method stub
		return checkingDone;
	}

}
