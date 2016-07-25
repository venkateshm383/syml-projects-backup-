package com.syml.bean.algo;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.syml.domain.Applicant;
import com.syml.service.AllProductsAlgorithm;

public class UwAppAllProduct {
	DecimalFormat df = new DecimalFormat("##");
	private String 	algo;

	private String opportunityName;

	private String opportunityID;
	private  Set<ApplicantsID> applicantsID;
	private  Set<ApplicantBeacon> applicantBeacon;


	private String cofDate;
	private String closingDate;
	private String lenderName;
	private String productName;
	private String productID;
	private String interestRate;
	private String qualifyingRate;
	private String mortgageType;
	private String term;
	private String amortization;
	private String ltv;
	private String gdsRatio;
	private String tdsRatio;
	private String mortgageAmount;
	private String mortgagePayment;
	private String insuranceAmount;

	private String totalIncome;
	private String totalLiabilities;
	private String totalHeat;
	private String totalPropTaxes;
	private String totalCondoFees;
	private String gDSAmount;
	private String tDSAmount;
	private String fitness;
	private String productQualifies;
	private Set<FailureReasons> FailureReasons;
	private static final Logger logger = LoggerFactory.getLogger(UwAppAllProduct.class);
	
	public UwAppAllProduct(UnderwriteAll qualifying){
		this.setAlgo("AllProduct");
		
    	try{
    		this.setAmortization(Integer.toString(qualifying.amortization));
    		//failProductJSON.getApplicantsID().add(applicantID);
    	}catch(Exception e){
    		logger.error("setApplicnatsis  is  Not  Required"+e);				
    	}
    	
    	for (Applicant applicant : qualifying.clientOpportunity.applicants) {
			ApplicantsNames applicantName = new ApplicantsNames();
			applicantName.setFirstName(applicant.applicantName);
			applicantName.setLastName(applicant.applicantLastName);
			
			ApplicantsID applicantID = new ApplicantsID();
			applicantID.setApplicantID(applicant.applicantId);
			applicantID.setApplicantName(applicantName);
			
			try{
			Set<ApplicantsID>  setapplicantsID=new HashSet<ApplicantsID>();
			setapplicantsID.add(applicantID);
			this.setApplicantsID(setapplicantsID);
			//this.getApplicantsID().add(applicantID);
			}catch (Exception e) {
				// TODO: handle exception
			}
			ApplicantBeacon applicantBeacon = new ApplicantBeacon();
			applicantBeacon.setApplicantName(applicantName);
			applicantBeacon.setApplicantBeacon(Integer.toString(applicant.beacon5Score));
			
			try{
			Set<ApplicantBeacon>  setAppBeacons=new HashSet<ApplicantBeacon>();
			
			setAppBeacons.add(applicantBeacon);
			this.setApplicantBeacon(setAppBeacons);
			}catch(Exception e){
				
			}
			//this.getApplicantBeacon().add(applicantBeacon);
		}
    	try{
        	this.setClosingDate(qualifying.clientOpportunity.expectedClosingDate.toString());

    	this.setCofDate(qualifying.clientOpportunity.conditionOfFinancingDate.toString());
    	}catch(Exception  e){
    		
    	}
    	for (String failedReason : qualifying.failReasons) {
			FailureReasons failReason = new FailureReasons();
			failReason.setReason(failedReason);
			try{
			Set<FailureReasons> failReasons=new HashSet<FailureReasons>();
			
			failReasons.add(failReason);
			
			this.setFailureReasons(failReasons);
			}catch(Exception e){
				
			}
			//this.getFailureReasons().add(failReason);						
		}
    	this.setFitness(String.valueOf(df.format(qualifying.fitness)));
    	this.setgDSAmount(String.valueOf(df.format(qualifying.totalDealGDSAmount)));
    	this.setGdsRatio((String.valueOf(qualifying.GDSRatio)));
    	this.setInsuranceAmount(String.valueOf(df.format(qualifying.insuranceAmount)));
    	this.setInterestRate(String.valueOf(df.format(qualifying.interestRate)));
    	String productName = qualifying.potentialProduct.name;			    	
		String[] productNameArray = productName.split("\\s*-\\s*");
		String LenderNamefromDB = productNameArray[0];
    	this.setLenderName(LenderNamefromDB);
    	this.setLtv(String.valueOf(df.format(qualifying.LTV)));
    	this.setMortgageAmount(String.valueOf(df.format(qualifying.expectedMortgageAmount)));
    	this.setMortgagePayment(String.valueOf(df.format(qualifying.expectedFitnessPayment)));
    	this.setOpportunityID(Integer.toString(qualifying.clientOpportunity.getId()));    	
    	this.setOpportunityName(qualifying.clientOpportunity.name);
    	this.setProductID(Integer.toString(qualifying.potentialProduct.getId()));
    	this.setProductName(qualifying.potentialProduct.name);
    	this.setProductQualifies(Boolean.toString(qualifying.opportunityQualifies));
    	this.setQualifyingRate(Double.toString(qualifying.potentialProduct.qualifyingRate));
    	this.settDSAmount(String.valueOf(df.format(qualifying.opportunityTDSAmount)));
    	this.setTdsRatio(String.valueOf(qualifying.TDSRatio));
    	this.setTotalCondoFees(String.valueOf(df.format(qualifying.condoFeesToUse)));
    	this.setTotalHeat(String.valueOf(df.format(qualifying.heatCostsToUse)));
    	this.setTotalIncome(String.valueOf(df.format(qualifying.totalDealIncome)));
    	this.setTotalLiabilities(String.valueOf(df.format(qualifying.totalDealLiabilities)));
    	this.setTotalPropTaxes(String.valueOf(df.format(qualifying.propertyTaxesToUse))); 
    	
    	
    	String typeOfMorgage = null;
    	if (qualifying.potentialProduct.mortgageType.contains("2")){
    		typeOfMorgage = "Variable";
		}
		else if (qualifying.potentialProduct.mortgageType.contains("3")){
			typeOfMorgage = "Fixed";
		}
		else if (qualifying.potentialProduct.mortgageType.contains("1")){
			typeOfMorgage = "LOC";
		}
		else if (qualifying.potentialProduct.mortgageType.contains("4")){
			typeOfMorgage = "Cashback";
		}
		else if (qualifying.potentialProduct.mortgageType.contains("5")){
			typeOfMorgage = "Combined";
		}			    				    	
    	
    	String termOfMorgage = null;
    	if (qualifying.potentialProduct.term.contains("1")){
    		termOfMorgage = "6 Month";
		}
    	else if (qualifying.potentialProduct.term.contains("2")){
    		termOfMorgage = "1 Year";
		}
    	else if (qualifying.potentialProduct.term.contains("3")){
    		termOfMorgage = "2 Year";
		}
    	else if (qualifying.potentialProduct.term.contains("4")){
    		termOfMorgage = "3 Year";
		}
    	else if (qualifying.potentialProduct.term.contains("5")){
    		termOfMorgage = "4 Year";
		}
    	else if (qualifying.potentialProduct.term.contains("6")){
    		termOfMorgage = "5 Year";
		}
    	else if (qualifying.potentialProduct.term.contains("7")){
    		termOfMorgage = "6 Year";
		}
    	else if (qualifying.potentialProduct.term.contains("8")){
    		termOfMorgage = "7 Year";
		}
    	else if (qualifying.potentialProduct.term.contains("9")){
    		termOfMorgage = "8 Year";
		}
    	else if (qualifying.potentialProduct.term.contains("10")){
    		termOfMorgage = "9 Year";
		}
    	else if (qualifying.potentialProduct.term.contains("11")){
    		termOfMorgage = "10 Year";
		}		

    	this.setMortgageType(typeOfMorgage); 
    	this.setTerm(termOfMorgage);
		
	}
	
	public UwAppAllProduct(UnderwriteCombined qualifying){
		this.setAlgo("AllProduct");
		
    	try{
    		this.setAmortization(Integer.toString(qualifying.amortization));
    		//failProductJSON.getApplicantsID().add(applicantID);
    	}catch(Exception e){
    		logger.error("setApplicnatsis  is  Not  Required"+e);				
    	}
    	
    	for (Applicant applicant : qualifying.clientOpportunity.applicants) {
			ApplicantsNames applicantName = new ApplicantsNames();
			applicantName.setFirstName(applicant.applicantName);
			applicantName.setLastName(applicant.applicantLastName);
			
			ApplicantsID applicantID = new ApplicantsID();
			applicantID.setApplicantID(applicant.applicantId);
			applicantID.setApplicantName(applicantName);
			
			try{
			Set<ApplicantsID>  setapplicantsID=new HashSet<ApplicantsID>();
			setapplicantsID.add(applicantID);
			this.setApplicantsID(setapplicantsID);
			//this.getApplicantsID().add(applicantID);
			}catch (Exception e) {
				// TODO: handle exception
			}
			ApplicantBeacon applicantBeacon = new ApplicantBeacon();
			applicantBeacon.setApplicantName(applicantName);
			applicantBeacon.setApplicantBeacon(Integer.toString(applicant.beacon5Score));
			
			try{
			Set<ApplicantBeacon>  setAppBeacons=new HashSet<ApplicantBeacon>();
			
			setAppBeacons.add(applicantBeacon);
			this.setApplicantBeacon(setAppBeacons);
			}catch(Exception e){
				
			}
			//this.getApplicantBeacon().add(applicantBeacon);
		}
    	try{
        	this.setClosingDate(qualifying.clientOpportunity.expectedClosingDate.toString());

    	this.setCofDate(qualifying.clientOpportunity.conditionOfFinancingDate.toString());
    	}catch(Exception  e){
    		
    	}
    	for (String failedReason : qualifying.failReasons) {
			FailureReasons failReason = new FailureReasons();
			failReason.setReason(failedReason);
			try{
			Set<FailureReasons> failReasons=new HashSet<FailureReasons>();
			
			failReasons.add(failReason);
			
			this.setFailureReasons(failReasons);
			}catch(Exception e){
				
			}
			//this.getFailureReasons().add(failReason);						
		}
    	this.setFitness(String.valueOf(df.format(qualifying.fitness)));
    	this.setgDSAmount(String.valueOf(df.format(qualifying.totalDealGDSAmount)));;
    	this.setGdsRatio(String.valueOf(df.format(qualifying.GDSRatio)));
    	this.setInsuranceAmount(String.valueOf(df.format(qualifying.insuranceAmount)));
    	this.setInterestRate(String.valueOf(df.format(qualifying.interestRate)));
    	String productName = qualifying.potentialProduct.name;			    	
		String[] productNameArray = productName.split("\\s*-\\s*");
		String LenderNamefromDB = productNameArray[0];
    	this.setLenderName(LenderNamefromDB);
    	this.setLtv(String.valueOf(df.format(qualifying.LTV)));
    	this.setMortgageAmount(String.valueOf(df.format(qualifying.expectedMortgageAmount)));
    	this.setMortgagePayment(Double.toString(qualifying.expectedFitnessPayment));
    	this.setOpportunityID(Integer.toString(qualifying.clientOpportunity.getId()));
    	
    	this.setOpportunityName(qualifying.clientOpportunity.name);
    	this.setProductID(Integer.toString(qualifying.potentialProduct.getId()));
    	this.setProductName(qualifying.potentialProduct.name);
    	this.setProductQualifies(Boolean.toString(qualifying.opportunityQualifies));
    	this.setQualifyingRate(String.valueOf(df.format(qualifying.potentialProduct.qualifyingRate)));
    	this.settDSAmount(String.valueOf(df.format(qualifying.opportunityTDSAmount)));
    	this.setTdsRatio(String.valueOf(df.format(qualifying.TDSRatio)));
    	this.setTotalCondoFees(String.valueOf(df.format(qualifying.condoFeesToUse)));
    	this.setTotalHeat(String.valueOf(df.format(qualifying.heatCostsToUse)));
    	this.setTotalIncome(String.valueOf(df.format(qualifying.totalDealIncome)));
    	this.setTotalLiabilities(String.valueOf(df.format(qualifying.totalDealLiabilities)));
    	this.setTotalPropTaxes(String.valueOf(df.format(qualifying.propertyTaxesToUse))); 
    	
    	
    	String typeOfMorgage = null;
    	if (qualifying.potentialProduct.mortgageType.contains("2")){
    		typeOfMorgage = "Variable";
		}
		else if (qualifying.potentialProduct.mortgageType.contains("3")){
			typeOfMorgage = "Fixed";
		}
		else if (qualifying.potentialProduct.mortgageType.contains("1")){
			typeOfMorgage = "LOC";
		}
		else if (qualifying.potentialProduct.mortgageType.contains("4")){
			typeOfMorgage = "Cashback";
		}
		else if (qualifying.potentialProduct.mortgageType.contains("5")){
			typeOfMorgage = "Combined";
		}			    				    	
    	
    	String termOfMorgage = null;
    	if (qualifying.potentialProduct.term.contains("1")){
    		termOfMorgage = "6 Month";
		}
    	else if (qualifying.potentialProduct.term.contains("2")){
    		termOfMorgage = "1 Year";
		}
    	else if (qualifying.potentialProduct.term.contains("3")){
    		termOfMorgage = "2 Year";
		}
    	else if (qualifying.potentialProduct.term.contains("4")){
    		termOfMorgage = "3 Year";
		}
    	else if (qualifying.potentialProduct.term.contains("5")){
    		termOfMorgage = "4 Year";
		}
    	else if (qualifying.potentialProduct.term.contains("6")){
    		termOfMorgage = "5 Year";
		}
    	else if (qualifying.potentialProduct.term.contains("7")){
    		termOfMorgage = "6 Year";
		}
    	else if (qualifying.potentialProduct.term.contains("8")){
    		termOfMorgage = "7 Year";
		}
    	else if (qualifying.potentialProduct.term.contains("9")){
    		termOfMorgage = "8 Year";
		}
    	else if (qualifying.potentialProduct.term.contains("10")){
    		termOfMorgage = "9 Year";
		}
    	else if (qualifying.potentialProduct.term.contains("11")){
    		termOfMorgage = "10 Year";
		}		

    	this.setMortgageType(typeOfMorgage); 
    	this.setTerm(termOfMorgage);
		
	}
	
	public String getAlgo() {
		return algo;
	}
	public void setAlgo(String algo) {
		this.algo = algo;
	}
	public String getOpportunityName() {
		return opportunityName;
	}
	public void setOpportunityName(String opportunityName) {
		this.opportunityName = opportunityName;
	}
	public String getOpportunityID() {
		return opportunityID;
	}
	public void setOpportunityID(String opportunityID) {
		this.opportunityID = opportunityID;
	}
	public Set<ApplicantsID> getApplicantsID() {
		return applicantsID;
	}
	public void setApplicantsID(Set<ApplicantsID> applicantsID) {
		this.applicantsID = applicantsID;
	}
	public Set<ApplicantBeacon> getApplicantBeacon() {
		return applicantBeacon;
	}
	public void setApplicantBeacon(Set<ApplicantBeacon> applicantBeacon) {
		this.applicantBeacon = applicantBeacon;
	}
	public String getCofDate() {
		return cofDate;
	}
	public void setCofDate(String cofDate) {
		this.cofDate = cofDate;
	}
	public String getClosingDate() {
		return closingDate;
	}
	public void setClosingDate(String closingDate) {
		this.closingDate = closingDate;
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
	public String getProductID() {
		return productID;
	}
	public void setProductID(String productID) {
		this.productID = productID;
	}
	public String getInterestRate() {
		return interestRate;
	}
	public void setInterestRate(String interestRate) {
		this.interestRate = interestRate;
	}
	public String getQualifyingRate() {
		return qualifyingRate;
	}
	public void setQualifyingRate(String qualifyingRate) {
		this.qualifyingRate = qualifyingRate;
	}
	public String getMortgageType() {
		return mortgageType;
	}
	public void setMortgageType(String mortgageType) {
		this.mortgageType = mortgageType;
	}
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public String getAmortization() {
		return amortization;
	}
	public void setAmortization(String amortization) {
		this.amortization = amortization;
	}
	public String getLtv() {
		return ltv;
	}
	public void setLtv(String ltv) {
		this.ltv = ltv;
	}
	public String getGdsRatio() {
		return gdsRatio;
	}
	public void setGdsRatio(String gdsRatio) {
		this.gdsRatio = gdsRatio;
	}
	public String getTdsRatio() {
		return tdsRatio;
	}
	public void setTdsRatio(String tdsRatio) {
		this.tdsRatio = tdsRatio;
	}
	public String getMortgageAmount() {
		return mortgageAmount;
	}
	public void setMortgageAmount(String mortgageAmount) {
		this.mortgageAmount = mortgageAmount;
	}
	public String getMortgagePayment() {
		return mortgagePayment;
	}
	public void setMortgagePayment(String mortgagePayment) {
		this.mortgagePayment = mortgagePayment;
	}
	public String getInsuranceAmount() {
		return insuranceAmount;
	}
	public void setInsuranceAmount(String insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}
	public String getTotalIncome() {
		return totalIncome;
	}
	public void setTotalIncome(String totalIncome) {
		this.totalIncome = totalIncome;
	}
	public String getTotalLiabilities() {
		return totalLiabilities;
	}
	public void setTotalLiabilities(String totalLiabilities) {
		this.totalLiabilities = totalLiabilities;
	}
	public String getTotalHeat() {
		return totalHeat;
	}
	public void setTotalHeat(String totalHeat) {
		this.totalHeat = totalHeat;
	}
	public String getTotalPropTaxes() {
		return totalPropTaxes;
	}
	public void setTotalPropTaxes(String totalPropTaxes) {
		this.totalPropTaxes = totalPropTaxes;
	}
	public String getTotalCondoFees() {
		return totalCondoFees;
	}
	public void setTotalCondoFees(String totalCondoFees) {
		this.totalCondoFees = totalCondoFees;
	}
	public String getgDSAmount() {
		return gDSAmount;
	}
	public void setgDSAmount(String gDSAmount) {
		this.gDSAmount = gDSAmount;
	}
	public String gettDSAmount() {
		return tDSAmount;
	}
	public void settDSAmount(String tDSAmount) {
		this.tDSAmount = tDSAmount;
	}
	public String getFitness() {
		return fitness;
	}
	public void setFitness(String fitness) {
		this.fitness = fitness;
	}
	public String getProductQualifies() {
		return productQualifies;
	}
	public void setProductQualifies(String productQualifies) {
		this.productQualifies = productQualifies;
	}
	public Set<FailureReasons> getFailureReasons() {
		return FailureReasons;
	}
	public void setFailureReasons(Set<FailureReasons> failureReasons) {
		FailureReasons = failureReasons;
	}

	
	
}
