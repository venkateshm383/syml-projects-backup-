package com.syml.service.calculate;

import com.syml.domain.Lender;
import com.syml.domain.Opportunity;

public class FitnessCalculationTask extends Thread{
	private Lender lender;
	private LenderFitnessScore returnLender;
	private Opportunity opp;
	private static final double ANNUAL_COST_MIN = 1000;
	private static final double ANNUAL_COST_MAX = 1500;
	private static final double CASH_BACK_MIN =  2050;
	private static final double CASH_BACK_MAX =  0;
	private static final double INTEREST_RATE_MIN =  3.1;
	private static final double INTEREST_RATE_MAX =  3.5;
	private static final double RISK_MIN =  0;
	private static final double RISK_MAX =  5;
	private static final double EASE_MIN =  0;
	private static final double EASE_MAX =  10;
	private static final double SPEED_MIN =  10;
	private static final double SPEED_MAX =  50;
	private static final double PAYOUTPENATLY_MIN =  2;
	private static final double PAYOUTPENATLY_MAX =  5;
	private static final double SUITABILITY_MIN =  1;
	private static final double SUITABILITY_MAX =  4;
	private static final double REGULAR_COMPENSATION_MIN =  1;
	private static final double REGULAR_COMPENSATION_MAX =  4;
	private static final double VOLUME_BONUS_MIN =  1;
	private static final double VOLUME_BONUS_MAX =  4;
	private static final double ROTATION_MIN =  1;
	private static final double ROTATION_MAX =  4;
	private static final double PREPAYMENT_OPTIONS_MIN =  1;
	private static final double PREPAYMENT_OPTIONS_MAX =  4;
	private static final double SKIP_PAYMENT_MIN =  1;
	private static final double SKIP_PAYMENT_MAX =  4;
	private static final double PAYMENT_OPTION_FREQUENCY_MIN =  1;
	private static final double PAYMENT_OPTION_FREQUENCY_MAX =  4;
	private static final double AIRMILES_MIN =  1;
	private static final double AIRMILES_MAX =  4;
	private static final double EXTRA_INSURANCE_OPTIONS_MIN =  1;
	private static final double EXTRA_INSURANCE_OPTIONS_MAX =  4;
	private static final double BRANCH_MIN =  1;
	private static final double BRANCH_MAX =  4;
	private static final double INTEREST_CALCULATION_MIN =  1;
	private static final double INTEREST_CALCULATION_MAX =  4;
	private static final double TRAILER_COMPENSATION_MIN =  1;
	private static final double TRAILER_COMPENSATION_MAX =  4;
	private static final double APPROVAL_RATE_MIN =  1;
	private static final double APPROVAL_RATE_MAX =  4;
	private static final double CLIENT_NOT_ATTEND_BRANCH_MIN =  1;
	private static final double CLIENT_NOT_ATTEND_BRANCH_MAX =  4;
	private static final double ADDITIONAL_MARKETING_COMP_MIN =  1;
	private static final double ADDITIONAL_MARKETING_COMP_MAX =  4;
	private static final double BRANCH_LEVEL_LOYALTY_MIN =  1;
	private static final double BRANCH_LEVEL_LOYALTY_MAX =  4;
	
	public FitnessCalculationTask(Lender lender,LenderFitnessScore returnlender, Opportunity opp){
		this.lender = lender;
		this.returnLender = returnlender;
		this.opp = opp;
	}

	@Override
	public void run() {
		double point = 0;
		
		point += getPoint(getAnnualCost(), ANNUAL_COST_MAX, ANNUAL_COST_MIN,10);
		point += getPoint(getCashBack(), CASH_BACK_MAX, CASH_BACK_MIN,10);
		point += getPoint(getInterestRate(), INTEREST_RATE_MAX, INTEREST_RATE_MIN,10);		
		point += getPoint(getRisk(), RISK_MAX, RISK_MIN,10);
		point += getPoint(getEase(), EASE_MAX, EASE_MIN,10);
		point += getPoint(getSpeed(), SPEED_MAX, SPEED_MIN,10);
		point += getPoint(getPayoutPenatly(), PAYOUTPENATLY_MAX, PAYOUTPENATLY_MIN,10);
		point += getPoint(getSuitability(), SUITABILITY_MAX, SUITABILITY_MIN,10);
		point += getPoint(getRegularCompensation(), REGULAR_COMPENSATION_MAX, REGULAR_COMPENSATION_MIN,10);
		point += getPoint(getVolumeBonus(), VOLUME_BONUS_MAX, VOLUME_BONUS_MIN,10);
		point += getPoint(getRotation(), ROTATION_MAX, ROTATION_MIN,10);
		point += getPoint(getSkipPayment(), SKIP_PAYMENT_MAX, SKIP_PAYMENT_MIN,10);
		point += getPoint(getPrepaymentOptions(), PREPAYMENT_OPTIONS_MAX, PREPAYMENT_OPTIONS_MIN,10);
		point += getPoint(getPaymentOptionFrequency(), PAYMENT_OPTION_FREQUENCY_MAX, PAYMENT_OPTION_FREQUENCY_MIN,10);
		point += getPoint(getAirmiles(), AIRMILES_MAX, AIRMILES_MIN,10);
		point += getPoint(getExtraInsuranceOptions(), EXTRA_INSURANCE_OPTIONS_MAX, EXTRA_INSURANCE_OPTIONS_MIN,10);
		point += getPoint(getBranch(), BRANCH_MAX, BRANCH_MIN,10);
		point += getPoint(getInterestCalculation(), INTEREST_CALCULATION_MAX, INTEREST_CALCULATION_MIN,10);
		point += getPoint(getTrailerCompensation(), TRAILER_COMPENSATION_MAX, TRAILER_COMPENSATION_MIN,10);
		point += getPoint(getApprovalRate(), APPROVAL_RATE_MAX, APPROVAL_RATE_MIN,10);
		point += getPoint(getClientNotAttendBranch(), CLIENT_NOT_ATTEND_BRANCH_MAX, CLIENT_NOT_ATTEND_BRANCH_MIN,10);
		point += getPoint(getAdditionalMarketingComp(), ADDITIONAL_MARKETING_COMP_MAX, ADDITIONAL_MARKETING_COMP_MIN,10);
		point += getPoint(getBranchLevelLoyalty(), BRANCH_LEVEL_LOYALTY_MAX, BRANCH_LEVEL_LOYALTY_MIN,10);
		
		this.returnLender.setPoint(point);
		this.returnLender.setLenderId(lender.getId());
		this.returnLender.setDone(true);
		
		
		
	}
	
	private double getAnnualCost()
	{
		return Math.random()*10;
	}
	
	private double getCashBack()
	{
		return Math.random()*10;
	}
	
	private double getInterestRate()
	{
		return Math.random()*10;
	}
	
	private double getRisk()
	{
		return Math.random()*10;
	}
	
	private double getEase()
	{
		return Math.random()*10;
	}
	
	private double getSpeed()
	{
		return Math.random()*10;
	}
	
	private double getPayoutPenatly()
	{
		return Math.random()*10;
	}
	
	private double getSuitability()
	{
		return Math.random()*10;
	}
	
	private double getRegularCompensation()
	{
		return Math.random()*10;
	}
	
	private double getVolumeBonus()
	{
		return Math.random()*10;
	}
	
	private double getRotation()
	{
		return Math.random()*10;
	}
	
	private double getPrepaymentOptions()
	{
		return Math.random()*10;
	}
	
	private double getSkipPayment()
	{
		return Math.random()*10;
	}
	
	private double getPaymentOptionFrequency()
	{
		return Math.random()*10;
	}
	
	private double getAirmiles()
	{
		return Math.random()*10;
	}
	
	private double getExtraInsuranceOptions()
	{
		return Math.random()*10;
	}
	
	private double getBranch()
	{
		return Math.random()*10;
	}
	
	private double getInterestCalculation()
	{
		return Math.random()*10;
	}
	
	private double getTrailerCompensation()
	{
		return Math.random()*10;
	}
	
	private double getApprovalRate()
	{
		return Math.random()*10;
	}
	
	private double getClientNotAttendBranch()
	{
		return Math.random()*10;
	}
	
	private double getAdditionalMarketingComp()
	{
		return Math.random()*10;
	}
	
	private double getBranchLevelLoyalty()
	{
		return Math.random()*10;
	}
	
	private double getPoint(double value, double maxValue, double minValue, double weight)
	{
		double point = 0;
		if(value <= minValue)
			return 0;
		else
		if(value >= maxValue)
			return weight;
		else
		{
			point = (value - minValue)/(maxValue - minValue)*weight;
		}
		return point;
	}
}
