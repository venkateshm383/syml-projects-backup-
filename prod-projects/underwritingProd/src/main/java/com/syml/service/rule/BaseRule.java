package com.syml.service.rule;

import java.util.ArrayList;
import java.util.List;

import com.syml.domain.Opportunity;

public class BaseRule implements IRule{
	
	private RuleResult ruleResult;
	private Opportunity opp;
	
	public BaseRule(RuleResult ruleResult, Opportunity opp)
	{
		this.ruleResult = ruleResult;
		this.opp = opp;
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		
		List<BaseChecking> checkings = new ArrayList<BaseChecking>();
		
//		checkings.add(new DoubleLiabilitiesChecking(ruleResult, opp));
//		checkings.add(new PrePaymentChecking(ruleResult, opp));
//		checkings.add(new CurrentRentingChecking(ruleResult, opp));
//		checkings.add(new OpenLOCChecking(ruleResult, opp));
//		checkings.add(new CombineToMortgageChecking(ruleResult, opp));
//		checkings.add(new AvailableCashChecking(ruleResult, opp));
//		checkings.add(new CompanyDebtChecking(ruleResult, opp));
//		checkings.add(new LoanCompletionChecking(ruleResult, opp));
		
		
		for(BaseChecking check : checkings)
		{
			CheckingQueue.getInstance().execute(check);
		}
		
		while(true)
		{
			//wait for all the checking done
			boolean allCheckingsDone = true;
			for(BaseChecking check : checkings)
			{
				if(!check.isCheckingDone())
				{
					allCheckingsDone = false;
					break;
				}
			}
			
			if(!allCheckingsDone)
			{
				//wait for 1 s
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else
			{
				//end this waiting
				break;
			}
		}
		
		//ruleResult.setRuleDone(true);
		
	}
	

}
