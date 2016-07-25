package com.syml.test.bk;

import static org.junit.Assert.*;

import org.junit.Test;

import com.syml.bean.algo.AlgoOpportunity;
import com.syml.bean.algo.AlgoOpportunity.FrequencyDesired;
import com.syml.bean.algo.CalculatePayments;

public class TestPaymentCalc {

	@Test
	public void testCalculateMortgagePayment() {
		
		double TotalMortgageAmount = 500000;
		double interestRate = 3.5;
		int amortization = 25;
		AlgoOpportunity.FrequencyDesired Frequency = FrequencyDesired.Monthly;
		double monthlyPayment = CalculatePayments.calculateMortgagePayment(interestRate, TotalMortgageAmount, amortization, Frequency);
		double RemainingBalance = TotalMortgageAmount;
		for (int i = 0; i < 30; i++) {
			double InterestOnlyPayment = CalculatePayments.calculateMonthlyLOCPayment(interestRate, RemainingBalance);
			double PrinciplePaid = monthlyPayment - InterestOnlyPayment;
			RemainingBalance = RemainingBalance - PrinciplePaid;
			System.out.println("InterestOnlyPayment = " + InterestOnlyPayment + ", PrinciplePaid = " + PrinciplePaid + ", RemainingBalance = " + RemainingBalance);
		}
		
		fail("Not yet implemented");
	}

//	@Test
//	public void testCalculateMonthlyLOCPayment() {
//		fail("Not yet implemented");
//	}
//
//	@Test
//	public void testCalculateMaximumMortgageAmount() {
//		fail("Not yet implemented");
//	}

}
