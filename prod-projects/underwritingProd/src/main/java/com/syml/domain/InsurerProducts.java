package com.syml.domain;

import com.syml.bean.algo.AlgoOpportunity;
import com.syml.bean.algo.UnderwriteAll;
import com.syml.bean.algo.UnderwritingBase;
import com.syml.service.AllProductsAlgorithm;
import com.syml.util.SelectionHelper;

public class InsurerProducts {

	public boolean reFinanceTopUp;
	public double loanToValue; 
	public boolean standard; 
	public boolean selfEmployed; 
	public double mortgageAmount; 
	public double reFiBalance; 
	public boolean nonTradDownPay; 
	public boolean rental; 
	public boolean vacation2ndHomeTypeA; 
	public boolean vacation2ndHomeTypeB; 
	public boolean firstAndSecond; 
	public boolean secondOnly; 
	public int amortization;
	
	public InsurerProducts(AllProductsAlgorithm underwrite, double mortgageAmount, int amortization) {

		if (underwrite.clientOpportunity.ltv != null){
			loanToValue = underwrite.clientOpportunity.ltv;
		}
		else{
			loanToValue = 90;
		}
		
		if (underwrite.clientOpportunity.hasSelfEmployedIncome != null){
			if (underwrite.clientOpportunity.hasSelfEmployedIncome == true)
				selfEmployed = true;
			else 
				selfEmployed = false;
			
			if (selfEmployed == false)
				standard = true;	
		}
		
		
		this.mortgageAmount = mortgageAmount;
		
		if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Refinance, underwrite.clientOpportunity.whatIsYourLendingGoal)){
			if (underwrite.clientOpportunity.ltv > 80){
				reFiBalance = mortgageAmount - underwrite.clientOpportunity.currentMortgageAmount;
				reFinanceTopUp = true;
			}
			else{
				reFiBalance = 0;
				reFinanceTopUp = false;
			}	
		}
		else{
			reFiBalance = 0;
			reFinanceTopUp = false;
		}
		
		if (underwrite.clientOpportunity.isRentalProperty == true){
			rental = true;
		}
		else 
			rental = false;
		
		if (underwrite.clientOpportunity.isHighRatio2ndHome == true){
			vacation2ndHomeTypeA = true;
		}
		else 
			vacation2ndHomeTypeA = false;
		
		if (underwrite.clientOpportunity.hasChargesBehind == true){
			if (loanToValue > 80){
				firstAndSecond = true;
			}
			else
				firstAndSecond = false;
		}
		else 
			firstAndSecond = false;
		
		this.amortization = amortization;
	}
	
	public InsurerProducts(UnderwritingBase underwrite){
		
		
		loanToValue = underwrite.clientOpportunity.ltv;
		
		if (underwrite.clientOpportunity.hasSelfEmployedIncome != null){
			if (underwrite.clientOpportunity.hasSelfEmployedIncome == true)
				selfEmployed = true;
			else 
				selfEmployed = false;	
		}
		else
			selfEmployed = false;
		
		
		if (selfEmployed == false)
			standard = true;
		
		mortgageAmount = underwrite.mortgageAmount;
		
		if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Refinance, underwrite.clientOpportunity.whatIsYourLendingGoal)){
			if (underwrite.LTV > 80){
				reFiBalance = mortgageAmount - underwrite.clientOpportunity.currentMortgageAmount;
				reFinanceTopUp = true;
			}
			else{
				reFiBalance = 0;
				reFinanceTopUp = false;
			}	
		}
		else{
			reFiBalance = 0;
			reFinanceTopUp = false;
		}
		
		if (underwrite.clientOpportunity.isRentalProperty == true){
			rental = true;
		}
		else 
			rental = false;
		
		if (underwrite.clientOpportunity.isHighRatio2ndHome == true){
			vacation2ndHomeTypeA = true;
		}
		else 
			vacation2ndHomeTypeA = false;
		
		if (underwrite.clientOpportunity.hasChargesBehind == true){
			if (loanToValue > 80){
				firstAndSecond = true;
			}
			else
				firstAndSecond = false;
		}
		else 
			firstAndSecond = false;
		
		amortization = underwrite.amortization;
		
	}
	
}
