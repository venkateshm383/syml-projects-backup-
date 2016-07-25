package com.syml.bean.algo;

import com.syml.util.SelectionHelper;

public class CombinedRecommendation {

	@Override
	public String toString() {
		return "CombinedRecommendation [underwriteCombined="
				+ underwriteCombined + ", baseLender=" + baseLender
				+ ", baseProduct=" + baseProduct + ", baseMortgageType="
				+ baseMortgageType + ", baseTerm=" + baseTerm
				+ ", baseAmortization=" + baseAmortization
				+ ", baseInterestRate=" + baseInterestRate
				+ ", baseMortgageAmount=" + baseMortgageAmount
				+ ", basePayment=" + basePayment + ", baseCashbackPercent="
				+ baseCashbackPercent + ", basePosition=" + basePosition
				+ ", baseProductID=" + baseProductID + ", additionalLender="
				+ additionalLender + ", additionalProduct=" + additionalProduct
				+ ", additionalMortgageType=" + additionalMortgageType
				+ ", additionalTerm=" + additionalTerm
				+ ", additionalAmortization=" + additionalAmortization
				+ ", additionalInterestRate=" + additionalInterestRate
				+ ", additionalMortgageAmount=" + additionalMortgageAmount
				+ ", additionalPayment=" + additionalPayment
				+ ", additionalCashbackPercent=" + additionalCashbackPercent
				+ ", additionalPosition=" + additionalPosition
				+ ", additionalProductID=" + additionalProductID
				+ ", totalMortgageAmount=" + totalMortgageAmount
				+ ", totalPayment=" + totalPayment + "]";
	}

	public UnderwriteCombined underwriteCombined;
	private  String  baseLender;
	private  String  baseProduct;
	private  String  baseMortgageType;
	private  String  baseTerm;
	private  String  baseAmortization;
	private  String  baseInterestRate;
	private  String  baseMortgageAmount;
	private  String  basePayment;
	private  String  baseCashbackPercent;
	private  String  basePosition;
	private  String  baseProductID;
	
	private  String  additionalLender;
	private  String  additionalProduct;
	private  String  additionalMortgageType;
	private  String  additionalTerm;
	private  String  additionalAmortization;
	private  String  additionalInterestRate;
	private  String  additionalMortgageAmount;
	private  String  additionalPayment;
	private  String  additionalCashbackPercent;
	private  String  additionalPosition;
	private  String  additionalProductID;
		
	private  String  totalMortgageAmount;
	private  String  totalPayment;
	
	public CombinedRecommendation (){
		
	}
	
	public CombinedRecommendation (UnderwriteCombined underwriteCombined){
		// Guy to Set Values using underwriteCombined instance  Will do on Monday
		// Line just for creating checkin ...
		this.underwriteCombined = underwriteCombined;
		baseLender = underwriteCombined.potentialProduct.lendername;
		baseProduct = underwriteCombined.potentialProduct.name; 
		baseMortgageType = mortgageTypeString(underwriteCombined.potentialProduct.mortgageType);
		baseTerm = mortgageTermString(underwriteCombined.potentialProduct.term);
		baseAmortization = String.valueOf(underwriteCombined.amortization);
		baseInterestRate = String.valueOf(underwriteCombined.potentialProduct.interestRate);
		baseMortgageAmount = String.valueOf(underwriteCombined.baseProductMortgageAmount);
		basePayment = String.valueOf(underwriteCombined.baseProductPayment);
		baseCashbackPercent = String.valueOf(underwriteCombined.potentialProduct.cashback);
		basePosition = "1st";
		baseProductID = String.valueOf(underwriteCombined.potentialProduct.getId());
		
		additionalLender = underwriteCombined.additionalProduct.lendername;
		additionalProduct = underwriteCombined.additionalProduct.name; 
		additionalMortgageType = mortgageTypeString(underwriteCombined.additionalProduct.mortgageType);
		additionalTerm = mortgageTermString(underwriteCombined.additionalProduct.term);
		additionalAmortization = String.valueOf(underwriteCombined.amortization);
		additionalInterestRate = String.valueOf(underwriteCombined.additionalProduct.interestRate);
		additionalMortgageAmount = String.valueOf(underwriteCombined.additionalProductMortgageAmount);
		additionalPayment = String.valueOf(underwriteCombined.additionalProductPayment);
		additionalCashbackPercent = String.valueOf(underwriteCombined.additionalProduct.cashback);
		additionalPosition = "1st";
		additionalProductID = String.valueOf(underwriteCombined.additionalProduct.getId());
			
		totalMortgageAmount = String.valueOf(underwriteCombined.expectedMortgageAmount);
		totalPayment = String.valueOf(underwriteCombined.expectedFitnessPayment);
	}
	
	public String mortgageTypeString(String MortgageType){
		String enumValue = MortgageType;
		String typeOfMorgage = null;
    	if (SelectionHelper.compareSelection(AlgoProduct.MortgageType.Variable, enumValue)){
    		typeOfMorgage = "Variable";
		}
		else if (SelectionHelper.compareSelection(AlgoProduct.MortgageType.Fixed, enumValue)){
			typeOfMorgage = "Fixed";
		}
		else if (SelectionHelper.compareSelection(AlgoProduct.MortgageType.LOC, enumValue)){
			typeOfMorgage = "LOC";
		}
		else if (SelectionHelper.compareSelection(AlgoProduct.MortgageType.Cashback, enumValue)){
			typeOfMorgage = "Cashback";
		}
		else if (SelectionHelper.compareSelection(AlgoProduct.MortgageType.Combined, enumValue)){
			typeOfMorgage = "Combined";
		}	
    	return typeOfMorgage;
	}

	public String mortgageTermString(String MortgageTerm){
		String enumValue = MortgageTerm;
		String termOfMorgage = null;
    	if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Month6, enumValue)){
    		termOfMorgage = "6 Month";
		}
    	else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year1, enumValue)){
    		termOfMorgage = "1 Year";
		}
    	else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year2, enumValue)){
    		termOfMorgage = "2 Year";
		}
    	else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year3, enumValue)){
    		termOfMorgage = "3 Year";
		}
    	else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year4, enumValue)){
    		termOfMorgage = "4 Year";
		}
    	else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year5, enumValue)){
    		termOfMorgage = "5 Year";
		}
    	else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year6, enumValue)){
    		termOfMorgage = "6 Year";
		}
    	else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year7, enumValue)){
    		termOfMorgage = "7 Year";
		}
    	else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year8, enumValue)){
    		termOfMorgage = "8 Year";
		}
    	else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year9, enumValue)){
    		termOfMorgage = "9 Year";
		}
    	else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year10, enumValue)){
    		termOfMorgage = "10 Year";
		}		
    	return termOfMorgage;
	}

	public String getBaseLender() {
		return baseLender;
	}

	public void setBaseLender(String baseLender) {
		this.baseLender = baseLender;
	}

	public String getBaseProduct() {
		return baseProduct;
	}

	public void setBaseProduct(String baseProduct) {
		this.baseProduct = baseProduct;
	}

	public String getBaseMortgageType() {
		return baseMortgageType;
	}

	public void setBaseMortgageType(String baseMortgageType) {
		this.baseMortgageType = baseMortgageType;
	}

	public String getBaseTerm() {
		return baseTerm;
	}

	public void setBaseTerm(String baseTerm) {
		this.baseTerm = baseTerm;
	}

	public String getBaseAmortization() {
		return baseAmortization;
	}

	public void setBaseAmortization(String baseAmortization) {
		this.baseAmortization = baseAmortization;
	}

	public String getBaseInterestRate() {
		return baseInterestRate;
	}

	public void setBaseInterestRate(String baseInterestRate) {
		this.baseInterestRate = baseInterestRate;
	}

	public String getBaseMortgageAmount() {
		return baseMortgageAmount;
	}

	public void setBaseMortgageAmount(String baseMortgageAmount) {
		this.baseMortgageAmount = baseMortgageAmount;
	}

	public String getBasePayment() {
		return basePayment;
	}

	public void setBasePayment(String basePayment) {
		this.basePayment = basePayment;
	}

	public String getBaseCashbackPercent() {
		return baseCashbackPercent;
	}

	public void setBaseCashbackPercent(String baseCashbackPercent) {
		this.baseCashbackPercent = baseCashbackPercent;
	}

	public String getBasePosition() {
		return basePosition;
	}

	public void setBasePosition(String basePosition) {
		this.basePosition = basePosition;
	}

	public String getBaseProductID() {
		return baseProductID;
	}

	public void setBaseProductID(String baseProductID) {
		this.baseProductID = baseProductID;
	}

	public String getAdditionalLender() {
		return additionalLender;
	}

	public void setAdditionalLender(String additionalLender) {
		this.additionalLender = additionalLender;
	}

	public String getAdditionalProduct() {
		return additionalProduct;
	}

	public void setAdditionalProduct(String additionalProduct) {
		this.additionalProduct = additionalProduct;
	}

	public String getAdditionalMortgageType() {
		return additionalMortgageType;
	}

	public void setAdditionalMortgageType(String additionalMortgageType) {
		this.additionalMortgageType = additionalMortgageType;
	}

	public String getAdditionalTerm() {
		return additionalTerm;
	}

	public void setAdditionalTerm(String additionalTerm) {
		this.additionalTerm = additionalTerm;
	}

	public String getAdditionalAmortization() {
		return additionalAmortization;
	}

	public void setAdditionalAmortization(String additionalAmortization) {
		this.additionalAmortization = additionalAmortization;
	}

	public String getAdditionalInterestRate() {
		return additionalInterestRate;
	}

	public void setAdditionalInterestRate(String additionalInterestRate) {
		this.additionalInterestRate = additionalInterestRate;
	}

	public String getAdditionalMortgageAmount() {
		return additionalMortgageAmount;
	}

	public void setAdditionalMortgageAmount(String additionalMortgageAmount) {
		this.additionalMortgageAmount = additionalMortgageAmount;
	}

	public String getAdditionalPayment() {
		return additionalPayment;
	}

	public void setAdditionalPayment(String additionalPayment) {
		this.additionalPayment = additionalPayment;
	}

	public String getAdditionalCashbackPercent() {
		return additionalCashbackPercent;
	}

	public void setAdditionalCashbackPercent(String additionalCashbackPercent) {
		this.additionalCashbackPercent = additionalCashbackPercent;
	}

	public String getAdditionalPosition() {
		return additionalPosition;
	}

	public void setAdditionalPosition(String additionalPosition) {
		this.additionalPosition = additionalPosition;
	}

	public String getAdditionalProductID() {
		return additionalProductID;
	}

	public void setAdditionalProductID(String additionalProductID) {
		this.additionalProductID = additionalProductID;
	}

	public String getTotalMortgageAmount() {
		return totalMortgageAmount;
	}

	public void setTotalMortgageAmount(String totalMortgageAmount) {
		this.totalMortgageAmount = totalMortgageAmount;
	}

	public String getTotalPayment() {
		return totalPayment;
	}

	public void setTotalPayment(String totalPayment) {
		this.totalPayment = totalPayment;
	}
	
}
