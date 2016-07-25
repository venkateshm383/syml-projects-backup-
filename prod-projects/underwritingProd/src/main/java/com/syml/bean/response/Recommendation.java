package com.syml.bean.response;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.syml.bean.algo.UnderwriteAll;
import com.syml.domain.Lender;
import com.syml.domain.Product;
import com.syml.service.CrudServices;
import com.syml.util.HibernateUtil;

public class Recommendation {
	
	public String lenderName;
	public String productName;
	public String mortgageType;
	public double mortaggeAmout;
	public double amortization;
	public double rate;
	public String term;
	public double payment;
	public double cashback;
	public String position;
	public double insuranceAmount;
	public double fitness;
	public int productID;
	
	public Recommendation(){}
	
	public Recommendation(UnderwriteAll underwrite){
		// TODO There are presently null Lenders in DB ... So, below code is interm to strip the Lender Name from the Product Name
//		int lenderID = (int)underwrite.potentialProduct.lender;
//		Session session = HibernateUtil.getSession();
//		Transaction tx = session.beginTransaction();
//		CrudServices<Lender> lenderCrud = new CrudServices<Lender>(Lender.class,true);
//		lenderCrud.setHsession(session);
//		Lender lender = lenderCrud.get(lenderID);	
		
    	productName = underwrite.potentialProduct.name;
    	
		String[] productNameArray = productName.split("\\s*-\\s*");
		String LenderNamefromDB = productNameArray[0];

//		if (LenderNamefromDB == null || LenderNamefromDB.contains("")){
//    		System.out.println("Challenge with Mortgage Amount");
    	
		
		lenderName = LenderNamefromDB;
    	mortgageType = underwrite.potentialProduct.mortgageType;
    	term = underwrite.potentialProduct.term;
    	rate = underwrite.potentialProduct.interestRate;
    	payment = roundedNumber(underwrite.expectedFitnessPayment);
    	cashback = roundedNumber(underwrite.algoPotentialProduct.cashbackAmount);
    	mortaggeAmout = underwrite.expectedMortgageAmount; // was underwrite.expectedMortgageAmount
    	amortization = underwrite.amortization;   
    	insuranceAmount = roundedNumber(underwrite.insuranceAmount);
    	fitness = underwrite.fitness;
    	productID = underwrite.potentialProduct.getId();
    	
    	if (mortaggeAmout < 20000){
    		System.out.println("Challenge with Mortgage Amount");
    	}
    	
	}
	
	public double roundedNumber(double inputDouble){
		double value = inputDouble;
		value = value*100;
		value = (double)((int) value);
		value = value /100;
		return value;
	}
	
	
	public String getLenderName() {
		return lenderName;
	}
	public void setLenderName(String lenderName) {
		this.lenderName = lenderName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getMortgageType() {
		return mortgageType;
	}
	public void setMortgageType(String mortgageType) {
		this.mortgageType = mortgageType;
	}
	public double getMortaggeAmout() {
		return mortaggeAmout;
	}
	public void setMortaggeAmout(double mortaggeAmout) {
		this.mortaggeAmout = mortaggeAmout;
	}
	public double getAmortization() {
		return amortization;
	}
	public void setAmortization(double amortization) {
		this.amortization = amortization;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public double getPayment() {
		return payment;
	}
	public void setPayment(double payment) {
		this.payment = payment;
	}
	public double getCashback() {
		return cashback;
	}
	public void setCashback(double cashback) {
		this.cashback = cashback;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}

	public double getInsuranceAmount() {
		return insuranceAmount;
	}

	public void setInsuranceAmount(double insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}

	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	@Override
	public String toString() {
		return "Recommendation [lenderName=" + lenderName + ", productName="
				+ productName + ", mortgageType=" + mortgageType
				+ ", mortaggeAmout=" + mortaggeAmout + ", amortization="
				+ amortization + ", rate=" + rate + ", term=" + term
				+ ", payment=" + payment + ", cashback=" + cashback
				+ ", position=" + position + "]";
	}
	
	

}
