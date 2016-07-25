package com.syml.bean.algo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventLocator;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.util.ValidationEventCollector;
import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.syml.constant.Constant;
import com.syml.domain.Applicant;
import com.syml.domain.Income;
import com.syml.domain.Lender;
import com.syml.domain.LenderNote;
import com.syml.domain.Liability;
import com.syml.domain.Mortgage;
import com.syml.domain.Product;
import com.syml.domain.Property;
import com.syml.domain.Task;
import com.syml.filogix.ReferralApplicationType;
import com.syml.morweb.BDIRequest;
import com.syml.morweb.CommonData;
import com.syml.morweb.CustomerData;
import com.syml.morweb.MortgageApplication;
import com.syml.service.CrudServices;
import com.syml.service.FilogixCall;
import com.syml.service.MorwebService;
import com.syml.service.PostSelectionAlgorithm;
import com.syml.util.SelectionHelper;
import com.syml.util.UnderwriteAppConfig;


public class UnderwritePostSel extends UnderwritingBase
{

	private static final long serialVersionUID = -8590481894155374883L;
	private static final Logger logger = LoggerFactory.getLogger(UnderwritePostSel.class);
	
	

    // Type of Underwriting for Determing the Set of Notes to Generate.
    public enum TypeOfUnderwriting { DesiredProduct, AllProducts, FinalVerify, PostSelection }
    public TypeOfUnderwriting underwritingType;
 
//    // Details
//    public Product potentialProduct;
//    public Opportunity clientOpportunity;
//    
//    public AlgoProduct algoPotentialProduct;
//    public AlgoOpportunity algoClientOpportunity;
////    public AlgoProduct potentialProduct;
////    public AlgoOpportunity clientOpportunity;
//
//    public int countOfAcceptableCritera;
//    public int countOfUnacceptableCriteria;
//    public int countOfQuestionableCriteria;
//
//    public boolean hasSelfEmployedIncome;
//    public double fitness;
//
//    public int lenderFeesPercent; // Some Lenders will charge an additional fee on the deal. Usually this is with either private Mortgages or 2nd Mortgages. 
//    public int lenderFeesFlat; // Some Lenders will charge an additional fee on the deal. Usually this is with either private Mortgages or 2nd Mortgages. 
//    public int brokerFeesPercent; // Sometimes we as a Broker may charge an additional fee on the deal. Usually this is with either private Mortgages or 2nd Mortgages. (Only applies to certain Products / Lenders ... Ussually B-D Lenders)
//    public int brokerFeesFlat; // Sometimes we as a Broker may charge an additional fee on the deal. Usually this is with either private Mortgages or 2nd Mortgages. (Only applies to certain Products / Lenders ... Ussually B-D Lenders) 
//    public double lendableValue; // The Values of the Property, in some instances Lenders may not use entire value of Property
//
//    public AlgoApplicant.CreditCategory OpportunityCreditCategory;
//
//    public boolean opportunityQualifies; // There are no UnacceptableCriteria
//    public int allowableGreyFlagGoodCredit; // Number of Grey Flags on the deal they will allow if the Credit is Good
//    public int allowableGreyFlagMediumCredit;
//    public int allowableGreyFlagPoorCredit;
//
//    public int allowableBlackFlagGoodCredit;
//    public int allowableBlackFlagMediumCredit;
//    public int allowableBlackFlagPoorCredit;
//    public int failedNotesCounter;
//    public int assistantNotesCounter;
//    
//    public double fixedCost;
//    public double variableCost;
//    public double LOCCost;
//    public double costToUse;
//    public double expectedFitnessPayment;
//    
//    public double interestRate;
      public double plusMinusPrime;
      public double percentVariable;
//    public int amortization;
//    public double cashbackAmount;
//    public double payoutAmount;
//    public double paymentOptions;
//    public double baseCompAmount;
//    public double volumeBonusCompAmount;
//    public double bonusCompAmount;
//    public double marketingCompAmount;
//    public double trailerCompAmount;
//    public double lenderFee;
//    public double brokerFee;
      public double referralFee;
//    
//    public double branchAttend;
//    public double applicationEase;
//    public double avgProcessingSpeed;
//    public double businessEase;
//    public double underwriterPref;
//
//    public double GDSRatio;
//    public double TDSRatio;
//    public double LTV;
//    public double allApplicantsTotalIncomes;
//    public double insuranceAmount;
//    public double maximumMortgageNoCondo;
//    public double maximumMortgageCondo;
//    public double maximumMortgageNoCondoDebtRepay;
//    public double maximumMortgageCondoDebtRepay;
//    public double totalLiabilityPayments;
//    public double expectedMortgageAmount;
//    public double allApplicantsTotalAssets;
//        
//    // A few extra Compensation Related fields
//    public double totalCompAmount;
//    public double oneTimeFees;
//    public List<String> allowedProvinces;
    public List<Task> assistantTasks;
    public List<Task> brokerTasks;
//    public List<AlgoNote> lenderNotes;
//    public List<AlgoNote> dealNotes;
//    public List<AlgoNote> nonOptimalRateNotes;
    public List<LenderNote> lenderNotesForAssembly;
//
//	private Session hsession;
//
//	private boolean hasEmployedIncome;
//	private int totalDealPropertiesCount;
//	private double beaconScoreToUse;
//	private double latePaymentsToUse;
//	private boolean statedIncomeDeal;
//	public String mortgageDescription;
//	private double insuredThresholdForProperty;
//	private double requiredNonInsureDownpayment;
//
//    public List<Liability> totalOpportunityLiabilities;
//    public List<String> mobileProviders;
//	public ArrayList<Liability> masterLiabilitiesList;
//
//	public double totalDebtRepaid;
//	public double insureAmountMaxMortgage;
//	public double insureAmountMaxMortgagePlusDebtReduce;
//	public double maximumMortgageNoCondoPlusDebtReduce;
//	public double insureAmountMaxMortgageWithCondo;
//	public double insureAmountMaxMortgageWithCondoPlusDebtReduce;
//	public double maxMortgageWithCondoPlusDebtReduce;
//	public double downPaymentWithDebtReduce;
//	
//	public double totalDealLiabilityPayments;
//	public double totalDealLiabilities;
    
    private Session hsession;

    public UnderwritePostSel(AlgoOpportunity clientOpportunity1, AlgoProduct potentialProduct1, Session hsession)
    throws XMLValidationException
    {
    	logger.info("----------------------inside  UnderWritePostSelection  Class---------------------------------------");
    	this.hsession = hsession;
        this.clientOpportunity = clientOpportunity1.opp;
        this.potentialProduct = potentialProduct1.product;
        this.algoClientOpportunity = clientOpportunity1;
        this.algoPotentialProduct = potentialProduct1;
        
        interestRate = potentialProduct.interestRate;
        avgProcessingSpeed = potentialProduct.avgProcessingSpeed;
        businessEase = potentialProduct.businessEase;
        underwriterPref = potentialProduct.underwriterPref;

        allowedProvinces = Arrays.asList(potentialProduct.allowProvinces.split(","));
        assistantTasks = new ArrayList<Task>();
        brokerTasks = new ArrayList<Task>();
        lenderNotes = new ArrayList<AlgoNote>();
        dealNotes = new ArrayList<AlgoNote>();
        nonOptimalRateNotes = new ArrayList<AlgoNote>();
        totalOpportunityLiabilities = new ArrayList<Liability>();
        lenderNotesForAssembly = new ArrayList<LenderNote>();        
        
        assistantNotes = new ArrayList<AlgoNote>();
        brokerNotes = new ArrayList<AlgoNote>();
        
        hasEmployedIncome = false;
        countOfAcceptableCritera = 0;
        countOfQuestionableCriteria = 0;
        countOfUnacceptableCriteria = 0;
        failedNotesCounter = 0;
        assistantNotesCounter = 0;
		masterLiabilitiesList = new ArrayList<Liability>();
		addedLiabilities = new ArrayList<Liability>();
		mobileProviders = new ArrayList<String>();
		listOfLenderNotes = new StringBuilder();
        
        percentVariable = 0;
    }

    public UnderwritePostSel(AlgoOpportunity clientAlgoOpportunity,
			Product algoProduct, Session session) throws XMLValidationException {
    	this.hsession = session;
        this.clientOpportunity = clientAlgoOpportunity.opp;
        this.potentialProduct = algoProduct;
        this.algoClientOpportunity = clientAlgoOpportunity;
        
        // Create Provinces in List of Allowed Provinces
        allowedProvinces = Arrays.asList(potentialProduct.allowProvinces.split(","));
        
        assistantTasks = new ArrayList<Task>();
        brokerTasks = new ArrayList<Task>();
        lenderNotes = new ArrayList<AlgoNote>();
        dealNotes = new ArrayList<AlgoNote>();
        nonOptimalRateNotes = new ArrayList<AlgoNote>();
        totalOpportunityLiabilities = new ArrayList<Liability>();
        hasEmployedIncome = false;
        countOfAcceptableCritera = 0;
        countOfQuestionableCriteria = 0;
        countOfUnacceptableCriteria = 0;
        failedNotesCounter = 0;
        assistantNotesCounter = 0;
		masterLiabilitiesList = new ArrayList<Liability>();
		addedLiabilities = new ArrayList<Liability>();
		mobileProviders = new ArrayList<String>();
		addCellCompanies();
		listOfLenderNotes = new StringBuilder();
        percentVariable = 0;
        if (interestRate < 1)
			logger.info("Rate Issue");
        
        // Set up initial text for lenderNotesString.
        StringBuilder namesToUse = new StringBuilder();
        if (clientOpportunity.applicants != null && clientOpportunity.applicants.size() > 0)
		{
        	int applicantCounter = 0;
			for (Applicant applicant : clientOpportunity.applicants) {
				if (applicant.includeInOpportunity == true) // Need to ability in Opportunity to remove an applicant from the deal if credit is too damaged.
				{
					if (applicantCounter > 0){
						namesToUse.append(" and " + applicant.applicantName);
					}
					else{
						namesToUse.append(applicant.applicantName);
					}
				}
			}
		}        
        
        String dealoverview = "To speed and ease the underwriting of deal for " + namesToUse.toString() + " we have included the following highlights for your attention:/n";
        listOfLenderNotes.append(dealoverview);
	}

    
    @Override
	public void checkUnderwritingRules() {
		/*
  	  Fields 
			BeaconTDS // Not being used Presently. I don't think this is required .... 

			Future Concept ... Idea is to include a variable in the income object which is the "verification method" where the assist
			selects which method was used, then use that to assign tasks & explain in Lender notes how we arrived at income.
			This future enhancement is to be considered in the event we are getting higher rejection rates due to mis-matches
			between how we calculated the income v.s. the method of the selected lender / product.
		 */
		
		if (interestRate < 1)
			logger.info("Rate Issue");
			
				
			double totalDealIncome = 0;
			double totalDealMortgagesBalance = 0;
			double totalDealMortgagesPayments = 0;
			double totalDealPropertyTaxes = 0;
			double totalDealCondoFees = 0;
			double totalDealPropertyValues = 0;
			double totalDealPropertyHeating = 0; 
			double totalDealCollections = 0;
			//boolean refinancePropertyNotInTotals = false;
			hasSelfEmployedIncome = false;
			double valueOfProperty = 0;
	
			boolean applicantIsImmigrant = false;
			Date applicantImmigrationDate = new Date();
			double MaxEmployedMonths = 0;
	
			double beaconScoreToUse = 0;
			double latePaymentsToUse = 0;
			
			String typeOfDeal = "";
			if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Refinance, clientOpportunity.whatIsYourLendingGoal))
					typeOfDeal = "Refinance";
			else if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Approval, clientOpportunity.whatIsYourLendingGoal))
					typeOfDeal = "Purchase";
			else if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Prequalify, clientOpportunity.whatIsYourLendingGoal))
					typeOfDeal = "Pre-Approval";
			
			StringBuilder downpayInfo = new StringBuilder();
			if (clientOpportunity.bankAccount > 0){
				downpayInfo.append(" Bank Account:" + roundedNumber(clientOpportunity.bankAccount) + ",");
			}
			if (clientOpportunity.personalCashAmount > 0){
				downpayInfo.append(" Personal Cash:" + roundedNumber(clientOpportunity.personalCashAmount) + ",");
			}
			if (clientOpportunity.rrspAmount > 0){
				downpayInfo.append(" RRSP:" + roundedNumber(clientOpportunity.rrspAmount) + ",");
			}
			if (clientOpportunity.giftedAmount > 0){
				downpayInfo.append(" Gifted:" + roundedNumber(clientOpportunity.giftedAmount) + ",");
			}
			if (clientOpportunity.borrowedAmount > 0){
				downpayInfo.append(" Borrowed:" + roundedNumber(clientOpportunity.borrowedAmount) + ",");
			}
			if (clientOpportunity.existingEquityAmount > 0){
				downpayInfo.append(" Existing Equity:" + roundedNumber(clientOpportunity.existingEquityAmount) + ",");
			}
			if (clientOpportunity.saleOfExistingAmount > 0){
				downpayInfo.append(" Sale of Existing:" + roundedNumber(clientOpportunity.saleOfExistingAmount) + ",");
			}
			if (clientOpportunity.sweatEquityAmount > 0){
				downpayInfo.append(" Sweat Equity:" + roundedNumber(clientOpportunity.sweatEquityAmount) + ",");
			}
			if (clientOpportunity.secondaryFinancingAmount > 0){
				downpayInfo.append(" Secondary Financing:" + roundedNumber(clientOpportunity.secondaryFinancingAmount) + ",");
			}
			if (clientOpportunity.otherAmount > 0){
				downpayInfo.append(" Other:" + roundedNumber(clientOpportunity.otherAmount) + ",");
			}
									
			String dealoverview = "This deal is a " + typeOfDeal + " with a downpayment of " + clientOpportunity.downpaymentAmount + " from the following sources: /n";
	        listOfLenderNotes.append(dealoverview);
	        listOfLenderNotes.append(downpayInfo.toString() + "/n");	        
	
			totalDealCollections = 0;
			boolean  refinancePropertyMatched = false;
			String qualifyingNote = "";
			String ltvNote = "";
			String gdsNote = "";
			String tdsNote = "";
			// This area determines if the Product passed in is a non-income Qualifier ... If so, run one set of rules.
			// If not, run the other set of rules where TDS and GDS are used as Primary Qualification criteria 
			if (clientOpportunity.nonIncomeQualifer){
				checkNonIncomeQualifier();
			}
			else{
				
				if (clientOpportunity.applicants != null && clientOpportunity.applicants.size() > 0)
				{
					for (Applicant applicant : clientOpportunity.applicants) {
						if (applicant.includeInOpportunity == true) // Need to ability in Opportunity to remove an applicant from the deal if credit is too damaged.
						{
							//Clean Fields ... 
							if (applicant.money != null){
								String tempApplicantMoney = applicant.money;
								tempApplicantMoney = tempApplicantMoney.replaceAll("[^0-9.]", "");
								applicant.money = tempApplicantMoney;
							}
							
							double totalApplicantIncome = 0;
							double currentNOA = 0;
							double priorNOA = 0;
							if (applicant.incomes != null && applicant.incomes.size() > 0){	        		        		
								for (Income currentIncome : applicant.incomes) {
									if (currentIncome.historical != true)
										MaxEmployedMonths = setMaxEmployedMonths(MaxEmployedMonths,	currentIncome);
	
									if (currentIncome.historical != true){  
										// hasSelfEmployedIncome is set in calcApplicantIncome() for later use.
										totalApplicantIncome = totalApplicantIncome + calcApplicantIncome(applicant, currentIncome);	
									}	
	
									if (currentIncome.business != null){
										if (currentIncome.business.contains("Current NOA")){
											currentNOA = currentNOA + Double.parseDouble(currentIncome.annualIncome);	        			        		
										}
										else if (currentIncome.business.contains("Prior NOA")){
											priorNOA = Double.parseDouble(currentIncome.annualIncome);
										}
										else{
											if (currentIncome.historical != true){
												currentNOA = currentNOA + Double.parseDouble(currentIncome.annualIncome);	
												priorNOA = Double.parseDouble(currentIncome.annualIncome);	
											}
										}
									}	        			        		
								}
							}
							if (hasEmployedIncome != true)
								hasEmployedIncome = false;
							
							if (hasSelfEmployedIncome == false){
								String employedIncomeDetails = applicant.applicantName.toString() + " has employed income of " + roundedNumber(totalApplicantIncome) + "/n";	
								listOfLenderNotes.append(employedIncomeDetails);
							}
							
	
							// Monthly Child / Spousal Support Received .... 
							if (applicant.monthlychildsupport != null && applicant.monthlychildsupport > 0){
								double annualChildSupport = applicant.monthlychildsupport * 12;
								totalApplicantIncome = totalApplicantIncome + annualChildSupport;
								String supportIncome = applicant.applicantName.toString() + " has support income of " + roundedNumber(annualChildSupport) + " per year./n";
								listOfLenderNotes.append(supportIncome);
							}	        		        	
	
							// Include this Applicant's Total Income into the Total Deal Income							 
							totalDealIncome = totalDealIncome + totalApplicantIncome;
							if (hasSelfEmployedIncome == true){
	
								double selfLastYearNOA = 0;
								double self2YrAvg = 0;
								double self2YrAvgPlus15 = 0;
							
								if (potentialProduct.lastYrNoa == true)
									selfLastYearNOA = currentNOA;
	
								if (potentialProduct.twoYrAvgNoa == true || potentialProduct.twoYrAverageNoa == true)
									self2YrAvg = (currentNOA + priorNOA) / 2;
	
								if (potentialProduct.twoYrAvgNoaPercent == true)
									self2YrAvgPlus15 = self2YrAvg + (self2YrAvg * potentialProduct.seGrossupPercent / 100);
	
								double largestSelfEmployedIncome1 = Math.max(selfLastYearNOA,self2YrAvg);
								double largestSelfEmployedIncome2 = Math.max(largestSelfEmployedIncome1,self2YrAvgPlus15);
	
								// Set the largest of the above income methods into the totalApplicant income after removing the CurrentNOA total
								totalApplicantIncome =  totalApplicantIncome - currentNOA + largestSelfEmployedIncome2;
								String incomeDetails = incomeDetails = applicant.applicantName.toString() + " has self-employed income of " + roundedNumber(largestSelfEmployedIncome2) + "/n";
								listOfLenderNotes.append(incomeDetails);
							}
	
							calcDealLiabilities(applicant);
							//For infoging ... 
//							for (Liability liability : addedLiabilities) {
//								logger.info(liability.toString());	
//							}							
							if (potentialProduct.childSupportTreatment != null){
								if (SelectionHelper.compareSelection(AlgoProduct.SupportTreatment.SubtractFromIncome, potentialProduct.childSupportTreatment)){
									if (applicant.monthlySupportPayment != null){
										double annualChildSupportPayment = applicant.monthlySupportPayment * 12;
										totalApplicantIncome = totalApplicantIncome - annualChildSupportPayment;
										String subtractSupportIncome = "As per guidelines " + applicant.applicantName.toString() + "'s support payment of " + roundedNumber(annualChildSupportPayment) + " has been subtracted from income./n";
										listOfLenderNotes.append(subtractSupportIncome);
									}
								}
							}
							
							applicant.totalIncome = totalApplicantIncome;
							applicant.totalApplicantIncome = totalApplicantIncome;
	
							double applicantCollectionsBalance = sumCollections(applicant);
							applicant.totalApplicantCollections = applicantCollectionsBalance;
							totalDealCollections = totalDealCollections + applicantCollectionsBalance;
	
							// Check CCRA Collections
							checkTaxCollections(applicant);
							
							// Create Asset / Properties oriented tasks related to the Applicant
							double mortgagePayments = 0;
							double mortgagesBalance = 0;
							double propertyTaxTotal = 0;
							double propertyValueTotal = 0;
							double condoFeesTotal = 0;
							List<String> lenderNames = new ArrayList<String>();
	
							int propertyCounter = 0;  
							if (applicant.properties != null && applicant.properties.size() > 0){
								for (Property currentProperty : applicant.properties) {
									if (currentProperty.selling != true){
										boolean includePropertyInCalculatons = checkIncludeProperty(currentProperty);
	
										int PropertyID = getPropertyID(currentProperty);
										if (applicant.mortgages != null && applicant.mortgages.size() > 0){ 
											for (Mortgage currenMortgage : applicant.mortgages) {
	
												int MortgageID = getMortgageID(currenMortgage);
	
												if (MortgageID == PropertyID && includePropertyInCalculatons == true){
	
													// Check that there are not too many mortgages with this lender
													lenderNames.add(currenMortgage.name.toLowerCase().trim());
													countLenderMortgages(lenderNames,currenMortgage);
	
													if (SelectionHelper.compareSelection(AlgoProduct.TreatmentOfRental.AddToIncome, potentialProduct.rentalIncomeTreatment))
													{
														if (currenMortgage.monthlyPayment != null)
															mortgagePayments = mortgagePayments + Double.parseDouble(currenMortgage.monthlyPayment);
														String additionalMortgageNote = "Mortgage on additional property for " + roundedNumber(mortgagePayments) + " has been included./n";
														listOfLenderNotes.append(additionalMortgageNote);
													}
													else
													{
														int IncomePropertyID = 0;
														double netMortgagesPayments = calcNetMortgagePayment(
																applicant,PropertyID,currenMortgage,IncomePropertyID);
														mortgagePayments = mortgagePayments + netMortgagesPayments;
														String additionalMortgageNote = "Annual Rental Net Income of " + roundedNumber(mortgagePayments) + " has been included./n";
														listOfLenderNotes.append(additionalMortgageNote);
													}
													if (currenMortgage.balance != null)
														mortgagesBalance = mortgagesBalance + Double.parseDouble(currenMortgage.balance);
												}
											}
										}
	
										if (includePropertyInCalculatons == true && currentProperty.moCondoFees > 0){
											condoFeesTotal = condoFeesTotal + currentProperty.moCondoFees;
										}
	
										if (currentProperty.value > 0 && includePropertyInCalculatons == true){ // Screen out - Property IDs must match ... 
											propertyTaxTotal = propertyTaxTotal + (double)currentProperty.annualTax;
											propertyValueTotal = propertyValueTotal + currentProperty.value;
											propertyCounter = propertyCounter + 1;    	
											
											// Check to see if this is the refinance Property ... 
											if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Refinance, clientOpportunity.whatIsYourLendingGoal)){
												if (Math.round((double)currentProperty.annualTax) == Math.round(clientOpportunity.propertyTaxes)
													|| Math.round(currentProperty.value) == Math.round(clientOpportunity.propertyValue) ){
													refinancePropertyMatched = true;
												}
											}
											
											
										}
									}
								}
							}							
							
							
							
							totalDealPropertiesCount = totalDealPropertiesCount + propertyCounter;
							// Need to Add the Mortgages, Property Values, etc for all Applicants
							totalDealPropertyHeating = totalDealPropertyHeating + ((double)propertyCounter * potentialProduct.minHeatCost);
							totalDealCondoFees = totalDealCondoFees + condoFeesTotal;
							totalDealPropertyTaxes = totalDealPropertyTaxes + propertyTaxTotal;
							totalDealPropertyValues = totalDealPropertyValues + propertyValueTotal;
							totalDealMortgagesBalance = totalDealMortgagesBalance + mortgagesBalance;
							totalDealMortgagesPayments = totalDealMortgagesPayments + mortgagePayments;
							applicant.totalApplicantMortgages = mortgagesBalance;
							applicant.totalApplicantMortgagePayments = mortgagePayments;
							applicant.totalApplicantPropertyTaxes = propertyTaxTotal;
							applicant.totalApplicantCondoFees = condoFeesTotal;
	
							checkBankruptcy(applicant);	        		            
							checkOrderlyDebtPayment(applicant);	        		            
							checkNonResident(applicant);
	
							if (applicant.immigrationDate != null){
								// Set values for later comparision once  calculated.
								applicantImmigrationDate = applicant.immigrationDate;
								applicantIsImmigrant = true;	            	
							}	
	
							// Calculate Applicant Assets for later usage in determining positive NetWorth
							double ApplicantAssetTotal = calcApplicantTotalAssets(applicant);
							ApplicantAssetTotal = ApplicantAssetTotal + propertyValueTotal;
							applicant.totalApplicantAssets = ApplicantAssetTotal;
							allApplicantsTotalAssets = allApplicantsTotalAssets + ApplicantAssetTotal;
						}
					}
				}
				else {
	
				}
	
				// Check to see if downpayment source is intended to be borrowed.  If so, create a new liability to add to GDS
				if (SelectionHelper.compareSelection(AlgoOpportunity.DownpaymentSource.Borrowed, clientOpportunity.downPaymentComingFrom)
						|| clientOpportunity.borrowedAmount > 0)
				{
					if (clientOpportunity.sourceOfBorrowing == null){
						double downpaymentDebtService = clientOpportunity.borrowedAmount * 0.03;
						totalDealLiabilityPayments = totalDealLiabilityPayments + downpaymentDebtService;
						totalDealLiabilities  = totalDealLiabilities + clientOpportunity.borrowedAmount;
					}
					else{
						if (SelectionHelper.compareSelection(AlgoOpportunity.BorrowedSource.Other, clientOpportunity.sourceOfBorrowing))
						{ 
							double downpaymentDebtService = clientOpportunity.borrowedAmount * 0.03;
							totalDealLiabilityPayments = totalDealLiabilityPayments + downpaymentDebtService;
							totalDealLiabilities  = totalDealLiabilities + clientOpportunity.borrowedAmount;
						}
						else
						{
							// Need to calculate secured LOC 
							double downpaymentDebtService = CalculatePayments.calculateMonthlyLOCPayment(potentialProduct.qualifyingRate, clientOpportunity.borrowedAmount);
							totalDealLiabilityPayments = totalDealLiabilityPayments + downpaymentDebtService;
							totalDealLiabilities  = totalDealLiabilities + clientOpportunity.borrowedAmount;
						}	
					}
				}
	
				valueOfProperty = calcValueOfProperty();
				String propertyValueNote = "Property Value is " + roundedNumber(valueOfProperty) + "/n";
				listOfLenderNotes.append(propertyValueNote);
				
				// a bit of logic around refinance in case they did not add property to list of properties in assets
				if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Refinance, clientOpportunity.whatIsYourLendingGoal)
						&& refinancePropertyMatched == false){					
					totalDealPropertyHeating = totalDealPropertyHeating + (1 * potentialProduct.minHeatCost);
					totalDealCondoFees = totalDealCondoFees + clientOpportunity.condoFees;
					totalDealPropertyTaxes = totalDealPropertyTaxes + clientOpportunity.propertyTaxes;
					totalDealPropertyValues = totalDealPropertyValues + valueOfProperty;
					totalDealMortgagesBalance = totalDealMortgagesBalance + clientOpportunity.currentMortgageAmount;					
				}
	
				// Calculate Mortgage Amount Including Insurance Confirm with Kendall the most reliable with this should be done ...
				calcExpectedMortgageAmount(valueOfProperty);
				
				double preInsuranceMortgageAmount = expectedMortgageAmount;
				
				double loanToValue = (preInsuranceMortgageAmount / valueOfProperty) * 100;
				clientOpportunity.ltv = loanToValue;   
				LTV = loanToValue;
	
//				if (valueOfProperty < 40000)
//					logger.info("Value: " + valueOfProperty);
				
				//#region Determine if HighRatio
				algoClientOpportunity.highRatio = checkIfHighRatio(valueOfProperty, expectedMortgageAmount);
	
//				if (valueOfProperty < 40000)
//					logger.info("Value: " + valueOfProperty);
				
				// TODO LTV INSURER ... If high ratio, can we run through an insurer algorithm? TODO Talk with Audrey whether Data Structure will work for insurer
				// Steps to Take:
				// Create a New Class which:
				// Queries Database for a List of Insurer Products
				// Creates an Underwriting instance for each Insurer Product in the list.
				// Returns a pass or fail for each insurer underwriting instance Based on Number of Flags)
				// Also Returns a list of Fail Reasons.
				// Create a Single note with the results for each insurer.
				// If an Insurer Fails, create a single note why it failed.
				// Pass these notes into Deal Notes.
				
				
				double productInterestRate = potentialProduct.interestRate; // Used on 5 Yr or Longer Terms or Actual Deals
				double interestRateVar = potentialProduct.qualifyingRate; // Used on Prequalification for < 5 Year or for LOC or for Variable
				
				if (clientOpportunity.desiredAmortization != null){
					if (LTV > 80){
						amortization = Math.min(25, clientOpportunity.desiredAmortization);
					}
					else 
						amortization = clientOpportunity.desiredAmortization;
				}
				else{
					amortization = 25; // Maximum25 Years except on Conventional, can be 30 Years.
				}
				
				//int amortization = 25; Was just used in temp when there was an issue with OpenERP Desired Amortization.
				if (amortization < 25){
					AlgoNote brokerNote1 = new AlgoNote(AlgoNote.TypeOfNote.BrokerAction, AlgoNote.Priority.Med, "Please Note that " + clientOpportunity.applicants.get(0).applicantName  + " has selected an amortization of " + clientOpportunity.amortization.toString() + ".  This will create a higher monthly payment amount. It is wise to provide the client with some education around this fact and to manage their expectations.");
					brokerNotes.add(brokerNote1);
				}
	
				mortgageAmount = expectedMortgageAmount;
	
				double insuranceCost = calcInsuranceCost();			
				expectedMortgageAmount = expectedMortgageAmount + insuranceCost;
	
	
				// In the event the Opportunity is a rental property, The expected rental income needs to be added to the total Income
				// Below method only runs on Product.AddToIncome
				totalDealIncome = addPropertyRentalIncome(totalDealIncome);
	
				// Below method only runs on Product.SubtractFromLiabilities
				double NetCashFlow = calcPropertyNetCashFlow(productInterestRate, interestRateVar);	                
				if (NetCashFlow > 0)
					totalDealIncome = totalDealIncome + NetCashFlow;  
				else
					totalDealLiabilities = totalDealLiabilities + NetCashFlow;
	
				// Determine the Maximum Allowable GDS and TDS Ratios for this lender.
				double allowableRatioForTDS = 0;
				double allowableRatioForGDS = 0;                
				// In this area, set Credit Categorization ....             
				beaconScoreToUse = beaconScore2Use();   
				latePaymentsToUse = latePayments2Use();                
	
				//Set the OpportunityCreditCategory
				setOpportunityCreditCategory(beaconScoreToUse);
	
				// Set the TGS and GDS Ratios we will be using for later calculations.
				setMaxAllowedTDS(beaconScoreToUse);
				allowableRatioForGDS = setMaxAllowedGDS(beaconScoreToUse);
				allowableRatioForTDS = setMaxAllowedTDS(beaconScoreToUse);
	
				// Determine the Maximum Allowable Mortgage Amount based upon LTV
				double maximumMortgageLTV = setMaxMortgageWithLTV(valueOfProperty);
				checkDownpayment(maximumMortgageLTV, valueOfProperty, clientOpportunity.downpaymentAmount);
				// TODO Confirm that the above method eliminates Lines of Credit.
	
				// Set the Values needed to feed the Maximum Mortgage Methods
				double rateToUse = rateToUse(productInterestRate, interestRateVar);
				double mortgagePaymentsToUse = totalDealMortgagesPayments;
				double liabilitiesToUse = totalDealLiabilityPayments * 12; // Problem with Dual Count (Is it really 6000??? or mortgages 2x ?
				double annualHeatCost =  potentialProduct.minHeatCost * 12;
				double heatToUse = 0;
				double condoFeesToUse = 0;
				double taxesToUse = totalDealPropertyTaxes;
				if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Refinance, clientOpportunity.whatIsYourLendingGoal)){
					heatToUse = totalDealPropertyHeating * 12;
					condoFeesToUse = totalDealCondoFees * 12; 
					taxesToUse = totalDealPropertyTaxes; 
				}
				else if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Prequalify, clientOpportunity.whatIsYourLendingGoal)){
					heatToUse = (totalDealPropertyHeating * 12) + annualHeatCost; 
					if (clientOpportunity.isCondo == true)
						condoFeesToUse = totalDealCondoFees + ((clientOpportunity.desiredMortgageAmount + clientOpportunity.downpaymentAmount) * 0.008);
					else
						condoFeesToUse = 0;
					
					taxesToUse = totalDealPropertyTaxes + ((clientOpportunity.desiredMortgageAmount + clientOpportunity.downpaymentAmount) * 0.01);
				}
				else{
					heatToUse = (totalDealPropertyHeating * 12) + annualHeatCost; 
					condoFeesToUse = (totalDealCondoFees + clientOpportunity.condoFees) * 12; 
					taxesToUse = totalDealPropertyTaxes + clientOpportunity.propertyTaxes; 
				}
	
				// For infoging
//				if (insuranceAmount > 0)
//					logger.info("Insurance amount: " + insuranceAmount);
				
				double mortgageAmountWithNoIns = expectedMortgageAmount - insuranceAmount;
		    	double equityOrDownpayment = clientOpportunity.downpaymentAmount;
		    	double projectedPropertyValue = mortgageAmountWithNoIns + clientOpportunity.downpaymentAmount;	
		    	
				// Determine the Maximum Allowable Mortgage Amount for no Condo based upon GDS (This mortgage only)
				double maxMortgageGdsNoCondo = maxMortgageNoCondoBasedOnGDS(totalDealIncome, totalDealMortgagesPayments, totalDealLiabilityPayments, 
						taxesToUse, heatToUse, allowableRatioForGDS, rateToUse); 
	
				// Determine the Maximum Allowable Mortgage Amount for with a Condo based upon GDS (This mortgage only)
				double maxMortgageGdsWithCondo = maxMortgageWithCondoBasedOnGDS(totalDealIncome, totalDealMortgagesPayments, totalDealLiabilityPayments, 
						taxesToUse, heatToUse, allowableRatioForGDS, rateToUse);               
	
				// Determine the Maximum Allowable Mortgage Amount for no Condo based upon TDS (Other Mortgages & Other Debts)
				double maxMortgageTdsNoCondo = maxMortgageNoCondoBasedOnTDS(totalDealIncome, totalDealMortgagesPayments, totalDealLiabilityPayments, 
						taxesToUse, heatToUse, allowableRatioForTDS, rateToUse);             	 
	
				// Determine the Maximum Allowable Mortgage Amount with a Condo based upon TDS (Other Mortgages & Other Debts)
				double maxMortgageTdsWithCondo = maxMortgageWithCondoBasedOnTDS(totalDealIncome, totalDealMortgagesPayments, totalDealLiabilityPayments, 
						taxesToUse, heatToUse, allowableRatioForTDS, rateToUse); 
	
				this.maximumMortgageNoCondo = Math.min(maxMortgageGdsNoCondo, maxMortgageTdsNoCondo);   
				this.maximumMortgageCondo = Math.min(maxMortgageGdsWithCondo, maxMortgageTdsWithCondo);
				// Need to Calculate Insurance on this in the event high ratio ... 
				
				if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Prequalify, clientOpportunity.whatIsYourLendingGoal)){
					double maxNoCondoPropertyValue = this.maximumMortgageNoCondo + clientOpportunity.downpaymentAmount;
					this.insureAmountMaxMortgage = calcInsuranceCostOnMax(this.maximumMortgageNoCondo, maxNoCondoPropertyValue);
					double maxCondoPropertyValue = this.maximumMortgageCondo + clientOpportunity.downpaymentAmount;
					this.insureAmountMaxMortgageWithCondo = calcInsuranceCostOnMax(this.maximumMortgageCondo, maxCondoPropertyValue);
				}
				
				
				// Determine what the loan amount is going to be (Either Desired or Max).  Max is used in Both Pre-Qualify and Refiance
				// Max is the smallest of the above Maximum amount Calculations, GDS, TDS, LTV
	
				if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Refinance, clientOpportunity.whatIsYourLendingGoal)){
					//clientOpportunity.maximumAmount = false; // TODO For testing
					if (clientOpportunity.maximumAmount == true){ 
						if (clientOpportunity.isCondo == true || clientOpportunity.condoFees > 0){
							double maxMortgageAllowed = Math.min(maxMortgageGdsWithCondo,maxMortgageTdsWithCondo);
							maxMortgageAllowed = Math.min(maxMortgageAllowed, maximumMortgageLTV);     
							totalDealMortgagesBalance = totalDealMortgagesBalance + maxMortgageAllowed - clientOpportunity.currentMortgageAmount;
							// expectedMortgageAmount = maxMortgageAllowed;  Was this ... Changed for Kevin because bad calc on Line 504 - 522
							expectedMortgageAmount = Math.max(maxMortgageAllowed, clientOpportunity.currentMortgageAmount);
						}
						else{
							double maxMortgageAllowed = Math.min(maxMortgageTdsNoCondo,maxMortgageGdsNoCondo);
							maxMortgageAllowed = Math.min(maxMortgageAllowed, maximumMortgageLTV);     
							totalDealMortgagesBalance = totalDealMortgagesBalance + maxMortgageAllowed - clientOpportunity.currentMortgageAmount;
							// expectedMortgageAmount = maxMortgageAllowed;  Was this ... Changed for Kevin because bad calc on Line 504 - 522
							expectedMortgageAmount = Math.max(maxMortgageAllowed, clientOpportunity.currentMortgageAmount);
						}  
						// Need to reset these values considering the Maximum Amount Desire.
						costToUse = calculateExpectedPayment(productInterestRate, interestRateVar, amortization);                  	
						//totalDealMortgagesPayments = totalDealMortgagesPayments + costToUse;
						clientOpportunity.totalMortgageAmount = expectedMortgageAmount;
						AlgoNote assistNote2 = new AlgoNote(AlgoNote.TypeOfNote.Info, AlgoNote.Priority.High, "The Maximum Mortgage amount allowable is: " + expectedMortgageAmount);
						assistantNotes.add(assistNote2);
					}
					else if (clientOpportunity.currentMortgageAmount < clientOpportunity.desiredMortgageAmount){
						expectedMortgageAmount = clientOpportunity.desiredMortgageAmount;
						costToUse = calculateExpectedPayment(productInterestRate, interestRateVar, amortization);  
						totalDealMortgagesBalance = totalDealMortgagesBalance + expectedMortgageAmount - clientOpportunity.currentMortgageAmount;
					}
					else{
						costToUse = calculateExpectedPayment(productInterestRate, interestRateVar, amortization);  
						totalDealMortgagesBalance = totalDealMortgagesBalance + expectedMortgageAmount - clientOpportunity.currentMortgageAmount;
						//totalDealMortgagesPayments = totalDealMortgagesPayments + expectedPaymentAmount;
					}
				}
				else{
					if (clientOpportunity.maximumAmount == true){ 
						if (clientOpportunity.isCondo == true || clientOpportunity.condoFees > 0){
							double maxMortgageAllowed = Math.min(maxMortgageGdsWithCondo,maxMortgageTdsWithCondo);
							maxMortgageAllowed = Math.min(maxMortgageAllowed, maximumMortgageLTV);     
							totalDealMortgagesBalance = totalDealMortgagesBalance + maxMortgageAllowed;
							totalDealPropertyValues = totalDealPropertyValues + valueOfProperty;
							expectedMortgageAmount = maxMortgageAllowed;
						}
						else{
							double maxMortgageAllowed = Math.min(maxMortgageTdsNoCondo,maxMortgageGdsNoCondo);
							maxMortgageAllowed = Math.min(maxMortgageAllowed, maximumMortgageLTV);     
							totalDealMortgagesBalance = totalDealMortgagesBalance + maxMortgageAllowed;
							totalDealPropertyValues = totalDealPropertyValues + valueOfProperty;
							expectedMortgageAmount = maxMortgageAllowed;      
						}  
						// Need to reset these values considering the Maximum Amount Desire.
						costToUse = calculateExpectedPayment(productInterestRate, interestRateVar, amortization);                  	
						//totalDealMortgagesPayments = totalDealMortgagesPayments + costToUse;
						clientOpportunity.totalMortgageAmount = expectedMortgageAmount;
						AlgoNote assistNote2 = new AlgoNote(AlgoNote.TypeOfNote.Info, AlgoNote.Priority.High, "The Maximum Mortgage amount allowable is: " + expectedMortgageAmount);
						assistantNotes.add(assistNote2);
					}
					else{
						costToUse = calculateExpectedPayment(productInterestRate, interestRateVar, amortization);  
						totalDealMortgagesBalance = totalDealMortgagesBalance + expectedMortgageAmount;
						totalDealPropertyValues = totalDealPropertyValues + valueOfProperty;
						//totalDealMortgagesPayments = totalDealMortgagesPayments + expectedPaymentAmount;
					}
				}
	
				double product1FitnessPayment = 0;
				if (SelectionHelper.compareSelection(AlgoProduct.MortgageType.LOC, potentialProduct.mortgageType))
					product1FitnessPayment = CalculatePayments.calculateMonthlyLOCPayment(productInterestRate, expectedMortgageAmount);
				else
					product1FitnessPayment = CalculatePayments.calculateMortgagePayment(productInterestRate, expectedMortgageAmount, amortization, AlgoOpportunity.FrequencyDesired.Monthly);
				
				expectedFitnessPayment = product1FitnessPayment;
				//expectedFitnessPayment = CalculatePayments.calculateMortgagePayment(productInterestRate, expectedMortgageAmount, amortization, AlgoOpportunity.FrequencyDesired.Monthly);
	
//				if (insuranceAmount > 0)
//					logger.info("Insurance amount: " + insuranceAmount);
				
				double mortgageAmountWithNoIns2 = expectedMortgageAmount - insuranceAmount;
		    	double equityOrDownpayment2 = clientOpportunity.downpaymentAmount;
		    	double projectedPropertyValue2 = mortgageAmountWithNoIns + clientOpportunity.downpaymentAmount;	
		    	
		    	if (Math.round(mortgageAmountWithNoIns2) != Math.round(mortgageAmountWithNoIns)){
		    		logger.info("mortgageAmountWithNoIns: {}, {}", mortgageAmountWithNoIns, mortgageAmountWithNoIns2);
		    		logger.info("equityOrDownpayment: {}, {}", equityOrDownpayment, equityOrDownpayment2);
			    	logger.info("projectedPropertyValue: {}, {}", projectedPropertyValue, projectedPropertyValue2);			    		
		    	}
		    		
				// Calculate GDS and TDS 
		    	double qualifyMortgagePayment = 0;
				if (SelectionHelper.compareSelection(AlgoProduct.MortgageType.LOC, potentialProduct.mortgageType))
					qualifyMortgagePayment = CalculatePayments.calculateMonthlyLOCPayment(interestRateVar, expectedMortgageAmount);
				else
					qualifyMortgagePayment = calculateExpectedPayment(productInterestRate, interestRateVar, this.amortization);
				//double qualifyMortgagePayment = calculateExpectedPayment(productInterestRate, interestRateVar, amortization);
				
				double totalDealGDSAmount = (qualifyMortgagePayment * 12) + heatToUse + taxesToUse + condoFeesToUse;
				//double totalDealGDSAmount = (anticipatedMortgagePayment * 12) + annualHeatCost + clientOpportunity.propertyTaxes + (clientOpportunity.condoFees * 12);
				double OpportunityGDSRatio = totalDealGDSAmount / totalDealIncome * 100;         
				
				double OpportunityTDSAmount = 0;
				if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Refinance, clientOpportunity.whatIsYourLendingGoal)){ 					
					// if refinance included in properties list this formula is true, If not, it is creating negative mortgage payment
					if (refinancePropertyMatched == false){
						OpportunityTDSAmount = (qualifyMortgagePayment * 12) + heatToUse + taxesToUse + condoFeesToUse + (mortgagePaymentsToUse * 12) + liabilitiesToUse;
					}
					else{
						OpportunityTDSAmount = (qualifyMortgagePayment * 12) + heatToUse + taxesToUse + condoFeesToUse + ((mortgagePaymentsToUse - clientOpportunity.currentMonthlyPayment) * 12) + liabilitiesToUse;	
					}
					
				}
				else{
					OpportunityTDSAmount = (qualifyMortgagePayment * 12) + (mortgagePaymentsToUse * 12) + heatToUse + taxesToUse + condoFeesToUse + liabilitiesToUse;
				}                	
	
				double opportunityTDSRatio =	OpportunityTDSAmount / totalDealIncome * 100;
	
				// Set Key Values into class variables for later usage ...                
				// TODO Need to test these TDS / MortgapotentialProduct.poorCredit12MoLatesge Amount Calculations ... 
				LTV =  preInsuranceMortgageAmount / valueOfProperty * 100;
				ltvNote = "LTV is " + roundedNumber(LTV) + "/n";			
				
				GDSRatio = OpportunityGDSRatio;
				
				TDSRatio = opportunityTDSRatio;
				
				allApplicantsTotalIncomes = totalDealIncome;
				totalLiabilityPayments = totalDealGDSAmount;
	
				boolean failedGDS = passFailGDS(allowableRatioForGDS);//                
	
				boolean failedTDS = passFailTDS(allowableRatioForTDS);
	
	
				// TODO Add a check on Max LTV depending on type of Property an Product (LOC Max = 65%)
				
				// Check if TDS or GDS is Borderline ... If so, amortize over 30 years if LTV <= 80% 
				if (LTV <= 80){
					boolean closeGDS = false;
					if (GDSRatio >= allowableRatioForGDS - 3){
						closeGDS = true;                	
					}
	
					boolean closeTds = false;
					if (TDSRatio >= allowableRatioForTDS - 3){
						closeTds = true;                	
					}
	
					if (closeGDS == true || closeTds == true){
						this.amortization = potentialProduct.maximumAmortization;
						
						double qualifyMortgagePayment2 = 0;
						if (SelectionHelper.compareSelection(AlgoProduct.MortgageType.LOC, potentialProduct.mortgageType))
							qualifyMortgagePayment2 = CalculatePayments.calculateMonthlyLOCPayment(interestRateVar, expectedMortgageAmount);
						else
							qualifyMortgagePayment2 = calculateExpectedPayment(productInterestRate, interestRateVar, this.amortization);
						//double anticipatedMortgagePayment2 = calculateExpectedPayment(productInterestRate, interestRateVar, this.amortization); 
						double totalDealGDSAmount2 = (qualifyMortgagePayment2 * 12) + annualHeatCost + clientOpportunity.propertyTaxes + (clientOpportunity.condoFees * 12);
						double OpportunityGDSRatio2 = totalDealGDSAmount2 / totalDealIncome * 100;              
	
						double OpportunityTDSAmount2 = 0;
						if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Refinance, clientOpportunity.whatIsYourLendingGoal)){
							if (refinancePropertyMatched == false){
								OpportunityTDSAmount2 = (qualifyMortgagePayment2 * 12) + heatToUse + taxesToUse + condoFeesToUse + (mortgagePaymentsToUse  * 12) + liabilitiesToUse;
							}
							else{
								OpportunityTDSAmount2 = (qualifyMortgagePayment2 * 12) + heatToUse + taxesToUse + condoFeesToUse + ((mortgagePaymentsToUse - clientOpportunity.currentMonthlyPayment) * 12) + liabilitiesToUse;	
							}
							
						}
						else{
							OpportunityTDSAmount2 = (qualifyMortgagePayment2 * 12) + heatToUse + taxesToUse + condoFeesToUse + (mortgagePaymentsToUse * 12) + liabilitiesToUse;
						}                	
	
						// reset totalLiabilityPayments to reflect new mortgage payment
						totalLiabilityPayments = totalDealGDSAmount2;
						double opportunityTDSRatio2 =	OpportunityTDSAmount2 / totalDealIncome * 100;
						
						GDSRatio = OpportunityGDSRatio2;
						
						TDSRatio = opportunityTDSRatio2;
	
						// Check TDS and GDS again to see if pass / fail with 30 year.
						failedGDS = passFailGDS(allowableRatioForGDS);
						failedTDS = passFailTDS(allowableRatioForTDS);
						
						double product1FitnessPayment2 = 0;
						if (SelectionHelper.compareSelection(AlgoProduct.MortgageType.LOC, potentialProduct.mortgageType))
							product1FitnessPayment2 = CalculatePayments.calculateMonthlyLOCPayment(productInterestRate, expectedMortgageAmount);
						else
							product1FitnessPayment2 = CalculatePayments.calculateMortgagePayment(productInterestRate, expectedMortgageAmount, amortization, AlgoOpportunity.FrequencyDesired.Monthly);
						
						expectedFitnessPayment = product1FitnessPayment2;
						//expectedFitnessPayment = CalculatePayments.calculateMortgagePayment(productInterestRate, expectedMortgageAmount, amortization, AlgoOpportunity.FrequencyDesired.Monthly);
								
						if (failedGDS == false && failedTDS == false){
							double rateForQualifying = calculateQualifyingInterestRate(potentialProduct.interestRate, potentialProduct.qualifyingRate);
							
							qualifyingNote = "Clients are qualified on "+ rateForQualifying +" for " + amortization + " years and are "
									+ "looking for " + potentialProduct.name  + " at " + potentialProduct.interestRate + "% /n";
												
							gdsNote = "GDS is " + roundedNumber(GDSRatio) + "/n";	
							tdsNote = "TDS is " + roundedNumber(TDSRatio) + "/n";
							
						}
						else{
							gdsNote = "GDS or TDS failed with GDS: " + roundedNumber(GDSRatio) + ", TDS: " + roundedNumber(TDSRatio) + "/n";													
						}
					}
					else{
						// TDS and GDS should not be close ... This is the else Statement for if (closeGDS == true || closeTds == true)
						if (failedGDS == false && failedTDS == false){
							double rateForQualifying = calculateQualifyingInterestRate(potentialProduct.interestRate, potentialProduct.qualifyingRate);
							
							qualifyingNote = "Clients are qualified on "+ rateForQualifying +" for " + amortization + " years and are "
									+ "looking for " + potentialProduct.name  + " at " + potentialProduct.interestRate + "% /n";
													
							gdsNote = "GDS is " + roundedNumber(GDSRatio) + "/n";	
							tdsNote = "TDS is " + roundedNumber(TDSRatio) + "/n";
						}
						else{
							gdsNote = "GDS or TDS failed with GDS: " + roundedNumber(GDSRatio) + ", TDS: " + roundedNumber(TDSRatio) + "/n";						
						}
					}
					
					
				}
				else{
					// LTV over 80%
					if (failedGDS == false && failedTDS == false){
						double rateForQualifying = calculateQualifyingInterestRate(potentialProduct.interestRate, potentialProduct.qualifyingRate);
						
						qualifyingNote = "Clients are qualified on "+ rateForQualifying +" for " + amortization + " years and are "
								+ "looking for " + potentialProduct.name  + " at " + potentialProduct.interestRate + "% /n";
												
						gdsNote = "GDS is " + roundedNumber(GDSRatio) + "/n";	
						tdsNote = "TDS is " + roundedNumber(TDSRatio) + "/n";
					}
					else{
						gdsNote = "GDS or TDS failed with GDS: " + roundedNumber(GDSRatio) + ", TDS: " + roundedNumber(TDSRatio) + "/n";					
					}
				}
	
				if(expectedMortgageAmount < 20000)
					logger.info("Challenge with Mortgage Amount");
	
				
				
				if (failedGDS == true || failedTDS == true){
					//checkNonIncomeQualifier();                   	
	
					if(expectedMortgageAmount < 20000)
						logger.info("Challenge with Mortgage Amount");
	
	
					if (statedIncomeDeal == true){
						AlgoNote assistNote2 = new AlgoNote(AlgoNote.TypeOfNote.AssistantAction, AlgoNote.Priority.High, "This opportunity can qualify based upon stated income.");
						assistantNotes.add(assistNote2);
					}
					else{
						AlgoNote assistNote2 = new AlgoNote(AlgoNote.TypeOfNote.AssistantAction, AlgoNote.Priority.High, "This opportunity cannot qualify based on Stated Income");
						assistantNotes.add(assistNote2);
						if (failedGDS == true){
							countOfUnacceptableCriteria++;
							countOfUnacceptableCriteria++;
							countOfUnacceptableCriteria++;
							countOfUnacceptableCriteria++;  // Multiple criteria are added - this is a deal breaker
							AlgoNote Note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "General Debt Service (GDS) Ratio High.  Client needs to reduce debt", "FailedApplication" + failedNotesCounter);
							dealNotes.add(Note1);
							failReasons.add("General Debt Service (GDS) Ratio High.  Client needs to reduce debt");
							failedNotesCounter++;
						}
						if (failedTDS == true){
							countOfUnacceptableCriteria++;
							countOfUnacceptableCriteria++;
							countOfUnacceptableCriteria++;
							countOfUnacceptableCriteria++;  // Multiple criteria are added - this is a deal breaker
							AlgoNote Note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Total Debt Service Ratio High.  Client needs to reduce debt or Mortgage Amount", "FailedApplication" + failedNotesCounter);
							dealNotes.add(Note1);
							failReasons.add("Total Debt Service Ratio High.  Client needs to reduce debt or Mortgage Amount");
							failedNotesCounter++;
	
							if (this.underwritingType.equals(TypeOfUnderwriting.PostSelection))
							{
								AlgoNote assistNote3 = new AlgoNote(AlgoNote.TypeOfNote.AssistantAction, AlgoNote.Priority.High, "Send email to the Broker to advise clients that Total Debt Service Ratio is" + TDSRatio + "% and General Debt Service Ratio is " + GDSRatio);
								assistantNotes.add(assistNote3);
							}
						}
					}
					
					logger.info(this.potentialProduct.name 
							+ ", opportunityQualifies: " + opportunityQualifies
							+ ", GDS: " + GDSRatio + ", GdsAmount: " + totalDealGDSAmount 
							+ ", TDS: " + TDSRatio + ", TdsAmount: " + OpportunityTDSAmount
							+ ", FailGDS: " + failedGDS + ", FailTDS: " + failedTDS 
							+ ", Income: " + totalDealIncome + ", Payment: " + anticipatedMortgagePayment + ", annualHeatCost: " + annualHeatCost 
							+ ", propertyTaxes: " + clientOpportunity.propertyTaxes + ", condoFees: " + clientOpportunity.condoFees
							+ ", Insurance: " + insuranceAmount);
				}
	
	
				// Need to Determine whether there are any debts which might be able to be paid off from Downpayment ... 
				// TODO Confirm this section with Kendall before including ... Why are we doing it? 
				//            	double downpaymentRatio = clientOpportunity.downpaymentAmount / valueOfProperty;
				//                double maxAvailableRatio = downpaymentRatio - (1-maximumAllowedLTV);
				//                double maxAvailableDebtRepay = maxAvailableRatio * valueOfProperty;                
				//                GenericComparator orderby = Util.newGenericComparator("paymentRatio","desc");                     
				//                Collections.sort(totalOpportunityLiabilities, orderby);
				//                double remainingDebtRepayAvailable = maxAvailableDebtRepay;
				//                totalDebtRepaid = 0;
				//                double totalPaymentReduction = 0;
				//                StringBuilder debtPayDown = new  StringBuilder();
				//                for (Liability liability : totalOpportunityLiabilities) {
				//                	if (remainingDebtRepayAvailable > 0){
				//                		if (liability.creditBalance < remainingDebtRepayAvailable){
				//                			totalDebtRepaid = totalDebtRepaid + liability.creditBalance;
				//                			totalPaymentReduction = totalPaymentReduction + Double.parseDouble(liability.monthlyPayment);
				//                			remainingDebtRepayAvailable = remainingDebtRepayAvailable - liability.creditBalance;
				//                			debtPayDown.append(liability.person + "'s liability at " + liability.business + " for " + liability.creditBalance + "with a payment of " + liability.monthlyPayment + "\n");
				//                		}
				//                	}
				//                	else 
				//                		break;
				//					
				//				}
				//                String debtPayDownString = debtPayDown.toString();
				//                AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Your Application with the lender would be substantially strengthened if you eliminated the following debts using a bit of the downpayment: \n" + debtPayDownString, "DebtElimNote");
				//                marketingTemplateNotes.add(note1);        
			}
	
			// These criteria are not for pre-qualification as they all involve the property All these additional criteria may not be needed ... Separate into those which are and those which are not ...
			checkPropertyCriteria(totalDealLiabilities, totalDealMortgagesBalance,totalDealCollections, valueOfProperty, beaconScoreToUse);
	
			// These are criteria that apply to all Deals, both Prequalification and non-prequalification
			checkMiscDealCriteria(totalDealCollections, applicantIsImmigrant,applicantImmigrationDate, MaxEmployedMonths, beaconScoreToUse);	
	
			// TODO Build out "Strategy Calculator / Recommendations" 
	
			// ALgorthim needs to Determine and Set 1st Mortgage AMount and Second Mrtgage Amount (Talk to kendall re: How to do so)
			// Check on "UnInsurable" - If true, then see if 2nd needed.
			// Talk to Kendall about how to generate List of Products (LOC vs. 2nd)
			// Check Allowed Charge ... If Opportunity.SecondMortgageAmount > 0, Check if "Allowed on 2nd == true"  
			// Also need to check that the Selected 1st Product "AllowChargesBehind == true"
			
			// Calculate expected Referral Fee
			referralFee = calculateReferralFee(expectedMortgageAmount);
			plusMinusPrime = Constant.currentPrimeRate - potentialProduct.interestRate;
			
			double filogixQualifyingRate = calculateQualifyingInterestRate(potentialProduct.interestRate, potentialProduct.qualifyingRate);
	
			checkOpportunityQualifies();      
			
			
			String QualifyingInfo = this.potentialProduct.name 
					+ ", opportunityQualifies: " + opportunityQualifies
					+ ", GDS: " + GDSRatio + ", GdsAmount: " + totalDealGDSAmount 
					+ ", TDS: " + TDSRatio + ", TdsAmount: " + opportunityTDSAmount
					+ ", FailGDS: " + failedGDS + ", FailTDS: " + failedTDS 
					+ ", Income: " + totalDealIncome + ", Payment: " + anticipatedMortgagePayment + ", annualHeatCost: " + annualHeatCost 
					+ ", propertyTaxes: " + clientOpportunity.propertyTaxes + ", condoFees: " + clientOpportunity.condoFees
					+ ", Insurance: " + insuranceAmount; 
			System.out.println(QualifyingInfo);
			
			StringBuilder failurestring = new StringBuilder();
			for (String reason : failReasons) {
				failurestring.append(reason + ", ");
			}
			
			if (opportunityQualifies == false){
				AlgoNote Note11 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Failure Reasons:" + failurestring);
				dealNotes.add(Note11);
			}
			
			AlgoNote Note11 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, QualifyingInfo);
			dealNotes.add(Note11);
			
			Calendar currentDate = new GregorianCalendar();
		    Calendar deadline = new GregorianCalendar();	  
		    
		    Task lenderNoteTask = new Task("Lender Notes", qualifyingNote + ltvNote + gdsNote + tdsNote + listOfLenderNotes.toString()
		    		+ " We have all the supporting documents and will send them in shortly.  Thank you for your time and assistance.  Have a great day!/n", "Assistant", "Opportunity.ID", "Opportunity.Team", 
	            			deadline,"", (double)0, 0.1, currentDate, deadline); 		    
	        assistantTasks.add(lenderNoteTask);
	        
			if(expectedMortgageAmount < 20000)
				logger.info("Challenge with Mortgage Amount");
			
			String sendingApp = "";
			if (potentialProduct.applicationMethod.contains("1")) {
				logger.info("---------------------Sending  To  MoreWeb------------------------------------------------------");

				sendingApp = "Morweb";
            } else{
				logger.info("---------------------------Sending  To  Filogix--------------------------------------------------");

            	sendingApp = "Filogix";
            }
			sendToLender(sendingApp);

			if (opportunityQualifies == true){
				logger.info(potentialProduct.name.toString() + " qualitifed with " + countOfUnacceptableCriteria);
			} else {
				logger.info("opportunityQualifies is false. Send to either filogix or morweb with opportunityQualifies=false .");
			}
	}

    public double calculateQualifyingInterestRate(double productInterestRate, double interestRateVar) {
		double qualifyingRateToUse= 0;
		if (SelectionHelper.compareSelection(AlgoProduct.MortgageType.LOC, potentialProduct.mortgageType)){
			qualifyingRateToUse = interestRateVar;
		}
		else if (SelectionHelper.compareSelection(AlgoProduct.MortgageType.Fixed, potentialProduct.mortgageType)){
			if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year5, potentialProduct.term)){
				qualifyingRateToUse = productInterestRate;
		    }
			else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year6, potentialProduct.term)){
				qualifyingRateToUse = productInterestRate;
		    }
			else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year7, potentialProduct.term)){
				qualifyingRateToUse = productInterestRate;
		    }
			else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year8, potentialProduct.term)){
				qualifyingRateToUse = productInterestRate;
		    }
			else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year9, potentialProduct.term)){
				qualifyingRateToUse = productInterestRate;
		    }
			else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year10, potentialProduct.term)){
				qualifyingRateToUse = productInterestRate;
		    }
			else {
				qualifyingRateToUse = interestRateVar;		
			}
		}
		else {
			qualifyingRateToUse = interestRateVar;		
		}
		return qualifyingRateToUse ;
	}

    private void sendToLender(String sendingApp) {
    	logger.info(">>> Will send to: {}", sendingApp);
    	if (sendingApp.contains("Fil")) {
        	logger.info(">>> -------------Will sending   to filogix-----------");

    		sendToFilogix();
    	} else {
        	logger.info(">>>---------------- Will sending   to Moreweb---------------------");

    		sendToMorweb();
    	}
    	
    	/*
    	Calendar currentDate = new GregorianCalendar();
    	Calendar deadline = new GregorianCalendar();
    	deadline.add(Calendar.MINUTE, 20);
    	
    	if (sendingApp.contains("fil")){
	 	   try {
		    	// Send Deal to Filogix
		    	   if (opportunityQualifies == true){
		    		   FilogixCall callFilogix = new FilogixCall(this);  
		    	   }
		    	   else{
		    		   Task task5 = new Task(
					        		"This Opportunity fails the approval with the lender.  Please talk with your manager to determine next steps.", 
					        		listOfLenderNotes.toString(), 
					        		"Assistant", "OpportunityID", "BrokerTeamID", deadline, "Assistant", (double)0, 0.05, currentDate, deadline, 1);
					                assistantTasks.add(task5);
		    	   }
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.info("Filogix Call failed  Underwrite2 1003 - See Stacktrace");
					e.printStackTrace();
				}   
	    }
	    else{
	 	   try {
	 		   if (opportunityQualifies == true){
		    	   }
		    	   else{
		    		   Task task5 = new Task(
					        		"This Opportunity fails the approval with the lender.  Please talk with your manager to determine next steps.", 
					        		listOfLenderNotes.toString(), 
					        		"Assistant", "OpportunityID", "BrokerTeamID", deadline, "Assistant", (double)0, 0.05, currentDate, deadline, 1);
					                assistantTasks.add(task5);
		    	   }
	
	 	   } catch (Exception e) {
	 		   // TODO Auto-generated catch block
	 		   logger.info("Morweb Call failed  Underwrite2 1022 - See Stacktrace");
	 		   e.printStackTrace();
	 	   }
	    }
	    */
    }

    private void sendToMorweb() {
    	final MorwebService morwebService = new MorwebService(this);
    	final BDIRequest bdiRequest = morwebService.getBdiRequest();

    	// createdDateTime, createdUnitId and createdUserId is set in request-template.xml in morweb-ws-gateway
    	final CommonData commonData = bdiRequest.getCommonData();
    	final CustomerData customerData = bdiRequest.getCustomerData();
    	final MortgageApplication mortgageApplication = bdiRequest.getMortgageApplication();
    	final StringWriter stringWriter = new StringWriter();

		JAXBContext jaxbContext;
		Marshaller marshaller;
		try {
			jaxbContext = JAXBContext.newInstance("com.syml.morweb");
			marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);

			final StringWriter commonDataStringWriter = new StringWriter();
			final StringWriter customerDataStringWriter = new StringWriter();
			final StringWriter mortgageAppStringWriter = new StringWriter();

	    	marshaller.marshal(createElementFromObject(CommonData.class, commonData), commonDataStringWriter);
	    	final String commonDataValue = commonDataStringWriter.toString();
	    	logger.info(">>> Done marshaling commonData: {}, value in string: {}", commonData, commonDataValue);

	    	marshaller.marshal(createElementFromObject(CustomerData.class, customerData), customerDataStringWriter);
	    	final String customerDataValue = customerDataStringWriter.toString();
	    	logger.info(">>> Done marshaling customerData: {}, value in string: {}", customerData, customerDataValue);

	    	marshaller.marshal(createElementFromObject(MortgageApplication.class, mortgageApplication), mortgageAppStringWriter);
	    	final String mortgageAppValue = mortgageAppStringWriter.toString();
	    	logger.info(">>> Done marshaling mortgageApplication: {}, value in string: {}", mortgageApplication, mortgageAppValue);

	    	stringWriter.append(commonDataValue).append(customerDataValue).append(mortgageAppValue);
	    	logger.info(">>> append all value to master stringwriter.");

		} catch (JAXBException e) {
			logger.error(">>> Error when marhaling: {}", e);
		}

    	String result = null;
    	try {
			result = stringWriter.toString();
			stringWriter.close();

			logger.info("commonData, customerData, mortgageApplication to send: {}", result);
		} catch (IOException e) {
			logger.error(">>> Error: {}", e.toString());
			e.printStackTrace();
		}

    	if (result == null) {
    		throw new RuntimeException("Cannot process to morweb. commonData, customerData, or mortgageApplication object cannot be deserialized.");
    	}

    	final String uri = createMorwebGatewayURL();
    	try {
			Response response = Request.Post(uri)
					.bodyString(result, ContentType.TEXT_XML)
					.execute();
			final String responseString = readResponse(response);
			logger.info("processRequest() result is : {}", responseString);
		} catch (IOException e) {
			logger.error("Error: {}", e.toString());
			e.printStackTrace();
		}
    }

    public <M> JAXBElement<M> createElementFromObject(Class<M> modelClass, M model) {
    	final String elementName = modelClass.getAnnotation(XmlRootElement.class).name();
    	final String namespace = modelClass.getAnnotation(XmlRootElement.class).namespace();
    	final QName modelQName = (namespace.equals("##default")) ? new QName(elementName) : new QName(namespace, elementName);
    	return new JAXBElement<M>(modelQName, modelClass, model);
    }


    private void sendToFilogix() {
    	logger.info("-----------------------------------------------Inside  Send  to  FiloGix  Apps-----------------------------");
    	final String dealRandom = Long.toString(System.currentTimeMillis());
    	final String dealId = this.clientOpportunity.applicationNo;
    	/*final String dealId = StringUtil.isNullOrEmpty(this.clientOpportunity.applicationNo) ? 
    			dealRandom : this.clientOpportunity.applicationNo + "-" + dealRandom;*/
    	final String messageId = dealRandom;
    	final String uri = createFilogixGatewayURL(dealId, messageId);
    	logger.info("---------------------------URI >>> {}-------------------------------------------------------------", uri);

    	FilogixCall filogixCall = new FilogixCall(this);
    	final ReferralApplicationType referralApplication = filogixCall.getReferralApplication();
    	
    	String data = new String();

    	ValidationEventCollector validationEventCollector = null;
		try {
			final SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			schemaFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, false);

			// http://tech.lalitbhatt.net/2014/08/jaxb-validation-using-schema.html
	    	final Source referralAppXSD = new StreamSource(loadFileFromClasspath("/_docs/fcx_api/referralApplication_1_0_1.xsd"));
			final Schema filogixSchema = schemaFactory.newSchema(new Source[] {referralAppXSD});

			final com.syml.filogix.ObjectFactory filogixObjectFactory = new com.syml.filogix.ObjectFactory();
	    	final JAXBContext context = JAXBContext.newInstance("com.syml.filogix");
	    	final JAXBElement<ReferralApplicationType> element = 
	    			filogixObjectFactory.createReferralApplication(referralApplication);

	    	validationEventCollector = new ValidationEventCollector();
	    	final StringWriter writer = new StringWriter();

	    	final Marshaller marshaller = context.createMarshaller();
	    	marshaller.setSchema(filogixSchema);
	    	marshaller.setEventHandler(validationEventCollector);
	    	marshaller.marshal(element, writer);
			data = writer.toString(); //xmlMapper.writeValueAsString(referralApplication);
			writer.close();

			if (validationEventCollector.getEvents().length != 0) {
				throw new XMLValidationException("Validation error.", "Filogix", validationEventCollector);
			}
		} catch (JAXBException | IOException | SAXException e) {
			logger.error(">>> {}", e.toString());
			if (validationEventCollector != null && validationEventCollector.getEvents() != null) {
				for (ValidationEvent event : validationEventCollector.getEvents()) {
					final ValidationEventLocator locator = event.getLocator();
					final Node node = locator.getNode();
					logger.error(">>> {}. Location: {}. Node:{}. Type: {}. Parent-Node:{}", 
							new Object[] {event.getMessage(), 
									
							locator.getObject().getClass().getName(),
							node == null ? "Null" : node.getNodeName(),
							node == null ? "Null node type" : node.getNodeType(),
							(node == null || node.getParentNode() == null) ? "Null parent node" : node.getParentNode().getNodeName()
							});
				}
			} else {
				logger.error(">>> validationEventCollector doesn't work!");
			}
			e.printStackTrace();
		}
		logger.info(">>> XMLData send to gateway: {}", data);
    	try {
			Response response = Request.Post(uri)
					.bodyByteArray(data.getBytes())
					.execute();
			final String result = readResponse(response);
			logger.info("setMessageTest() result is : {}", result);
			AlgoNote Note11 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Reponse from Filogix:" + result);
			dealNotes.add(Note11);
		} catch (IOException e) {
			logger.error(">>> Exception: {}", e.getMessage());
			
		}
    }

    private static String createMorwebGatewayURL() {
    	final String base = UnderwriteAppConfig.getInstance().getMorwebGatewayURL();
    	return base + "/morweb/process-request";
    }

    private static String createFilogixGatewayURL(String dealId, String messageId) {
    	final String baseURL = UnderwriteAppConfig.getInstance().getFilogixGatewayURL();
    	final String url = baseURL + "/filogix/set-message?deal-id=" + dealId + "&message-id=" + messageId;
    	return url;
    }

    private static final File loadFileFromClasspath(final String fileLocation) {
		try {
			final URI uri = UnderwritePostSel.class.getResource(fileLocation).toURI();
			return new File(uri);
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
    
    private static String readResponse(Response response) {
		try {
			final Content content = response.returnContent();
			final boolean noContent = content.equals(Content.NO_CONTENT);
			String result = null;
			if (noContent) {
				final HttpEntity httpEntity = response.returnResponse().getEntity();
				final InputStream inputStream = httpEntity.getContent();
				StringWriter writer = new StringWriter();
				IOUtils.copy(inputStream, writer, httpEntity.getContentEncoding().getValue());
				result = writer.toString();
			} else {
				result = content.asString();
			}
			logger.info(">>> Content is {}", (noContent ? "available" : "no content"));
			return result;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

    

	public void CalculateOneTimeFees()
    {
        lenderFee = (expectedMortgageAmount * potentialProduct.lenderFeesPercent / 100) + potentialProduct.lenderFeesFlat; 
        brokerFee = (expectedMortgageAmount * potentialProduct.brokerFeesPercent / 100) + potentialProduct.brokerFeesFlat;
        oneTimeFees = lenderFee + brokerFee;
    }
	
	
	public void checkNonIncomeQualifier(){
		Calendar currentDate = new GregorianCalendar();
        Calendar deadline = new GregorianCalendar();
        deadline.add(Calendar.MINUTE, 20);
        
		try {
			double valueOfProperty = 0;
			statedIncomeDeal = false;
	    	
			// Determine the beacon score to use
			beaconScoreToUse = beaconScore2Use();	    	
	        latePaymentsToUse = latePayments2Use();
	        //logger.info("beaconScoreToUse:" + beaconScoreToUse);
	        
	        boolean beaconAcceptable = false;
	        if (beaconScoreToUse >= potentialProduct.statedIncomeMinBeacon){
	        	countOfAcceptableCritera++;
	        	beaconAcceptable = true;
	        }
	        else{
	        	countOfUnacceptableCriteria++;
	        	AlgoNote Note1 = new AlgoNote(AlgoNote.TypeOfNote.Info, AlgoNote.Priority.Med, "The client Credit Rating does not meet the requirements for this non-income qualification product.", "FailedApplication" + failedNotesCounter);
	            dealNotes.add(Note1);
	            failedNotesCounter++;
	        }
	        
	        // Calculate LTV .... 
	        valueOfProperty = clientOpportunity.propertyValue;
	        if (clientOpportunity.outbuildingsValue > 0)
	        {
	            double lenderAcceptedOutBuildingVal = clientOpportunity.outbuildingsValue * ((double)potentialProduct.outbuildingIncluded / 100);
	            double valueToSubtract = clientOpportunity.outbuildingsValue - lenderAcceptedOutBuildingVal;
	            lendableValue = clientOpportunity.propertyValue - valueToSubtract;                
	            valueOfProperty = lendableValue;
	        }
	        else
	        {
	            lendableValue = clientOpportunity.propertyValue;
	            valueOfProperty = lendableValue;
	        }
	    	
	        
	        if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Prequalify, clientOpportunity.whatIsYourLendingGoal)){        
	        	valueOfProperty = clientOpportunity.desiredMortgageAmount + clientOpportunity.downpaymentAmount;  
	        }
	        else {
	        	valueOfProperty = valueOfProperty + clientOpportunity.renovationValue;
	        }  
	                
	        // TODO This needs adjustment to QUADRUPLE CHECK
	        expectedMortgageAmount = 0;
	        if (clientOpportunity.desiredMortgageAmount == 0){
	        	expectedMortgageAmount = clientOpportunity.propertyValue - clientOpportunity.downpaymentAmount;        			
	        }        
	        if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Refinance, clientOpportunity.whatIsYourLendingGoal)  ){
	        	
	        	if (clientOpportunity.maximumAmount == true){
	        		expectedMortgageAmount = clientOpportunity.propertyValue * (potentialProduct.statedIncomeMaxLtv / 100);
	        	}
	        	else{
	        		// was expectedMortgageAmount = clientOpportunity.currentMortgageAmount + clientOpportunity.additionalAmount;
	        		expectedMortgageAmount = clientOpportunity.currentMortgageAmount + clientOpportunity.currentBalance + clientOpportunity.renovationValue;
	        	}
	        	// TODO This needs to be verified with infoging once OpenERP field values are ser
	            // On Next Algo trip, when confirmed, if there is available equity,build a broekr note. 
	        }             
	        else if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Approval, clientOpportunity.whatIsYourLendingGoal)){
	        	expectedMortgageAmount = clientOpportunity.propertyValue + clientOpportunity.renovationValue - clientOpportunity.downpaymentAmount;
	        	// On next iteration of algorithm, add in the note that assistant needs to generate the "Property Checklist Report" in the event it is a private sale.
	        }             
	        else if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Prequalify, clientOpportunity.whatIsYourLendingGoal)){
	        	expectedMortgageAmount = clientOpportunity.desiredMortgageAmount;
	        	// Next iteration of Algo ... Determine if Broker notes are required  Maximum will be determined after paperwork is verified and put into proposal        	
	        }
	        
	        
	     // Check to see that loan amount is < MaximumLoanAmount
	        if (expectedMortgageAmount <= potentialProduct.maxMortgageAllowed)
	            countOfAcceptableCritera++;
	        else
	        {
	            countOfUnacceptableCriteria++;
	            AlgoNote Note1 = new AlgoNote(AlgoNote.TypeOfNote.Info, AlgoNote.Priority.Med, "Application requires a Larger Mortgage than 'Top of List' provide.", "FailedApplication" + failedNotesCounter);
	            dealNotes.add(Note1);
	            failedNotesCounter++;
	        }

	        // Check to see that loan amount is > MaximumLoanAmount
	        if (expectedMortgageAmount >= potentialProduct.minMortgageAllowed)
	            countOfAcceptableCritera++;
	        else
	        {
	            countOfUnacceptableCriteria++;
	            AlgoNote Note1 = new AlgoNote(AlgoNote.TypeOfNote.Info, AlgoNote.Priority.Med, "Application requires a Smaller Mortgage than 'Top of List' provide.", "FailedApplication" + failedNotesCounter);
	            dealNotes.add(Note1);
	            failedNotesCounter++;
	        }
	        
	        double preInsuranceMortgageAmount = expectedMortgageAmount;
	        
	        boolean acceptableLTV = false;
	        double loanToValue = (preInsuranceMortgageAmount / valueOfProperty) * 100;
	        clientOpportunity.ltv = loanToValue;   
	        
	        if (loanToValue <= potentialProduct.statedIncomeMaxLtv){
	        	countOfAcceptableCritera++;
	        	acceptableLTV = true;
	        }
	        else{
	        	countOfUnacceptableCriteria++;
	            AlgoNote Note1 = new AlgoNote(AlgoNote.TypeOfNote.Info, AlgoNote.Priority.Med, "The Loan to Value of this Opportunity is too large for this Product.", "FailedApplication" + failedNotesCounter);
	            dealNotes.add(Note1);
	            failedNotesCounter++;
	        }
	        
	        if (beaconAcceptable == true && acceptableLTV == true){
	        	statedIncomeDeal = true;
	        }
	        else 
	        	statedIncomeDeal = false;
	        
	        // Calculate Payments 
	     // Check on Downpayment Totals ... 
	        if (statedIncomeDeal == true){
	        	double SeparateSourceDownpayment = clientOpportunity.personalCashAmount + clientOpportunity.rrspAmount + clientOpportunity.giftedAmount + clientOpportunity.borrowedAmount + clientOpportunity.existingEquityAmount + clientOpportunity.saleOfExistingAmount + clientOpportunity.sweatEquityAmount + clientOpportunity.secondaryFinancingAmount + clientOpportunity.otherAmount;
	            if (clientOpportunity.downpaymentAmount != SeparateSourceDownpayment){
	            	
	                Task task1 = new Task(
            		"The Multiple sources of Downpayment amounts do not equal the Opportunity Down Payment Amount.  Confirm the downpayment amount and adjust fields", 
            		"", 
            		"Assistant", "OpportunityID", "BrokerTeamID", deadline, "Assistant", (double)0, 0.05, currentDate, deadline, 1);
	                assistantTasks.add(task1);
	            }
	            
	            double productInterestRate = potentialProduct.interestRate; // Used on 5 Yr or Longer Terms or Actual Deals
	            double interestRateVar = potentialProduct.qualifyingRate; // Used on Prequalification for < 5 Year or for LOC or for Variable
	            amortization = 25; // Maximum25 Years except on Conventional, can be 30 Years.
	            	            
	            double expectedPaymentAmount = 0;
	            if (SelectionHelper.compareSelection(AlgoProduct.MortgageType.LOC, potentialProduct.mortgageType)){
	            	expectedPaymentAmount = CalculatePayments.calculateMonthlyLOCPayment(interestRateVar, expectedMortgageAmount);
	            }
	            else if (SelectionHelper.compareSelection(AlgoProduct.MortgageType.Fixed, potentialProduct.mortgageType)){
	            	if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year5, potentialProduct.mortgageType)){
	            		expectedPaymentAmount = CalculatePayments.calculateMortgagePayment(productInterestRate, expectedMortgageAmount, amortization, AlgoOpportunity.FrequencyDesired.Monthly);
	                }
	            	else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year6, potentialProduct.mortgageType)){
	            		expectedPaymentAmount = CalculatePayments.calculateMortgagePayment(productInterestRate, expectedMortgageAmount, amortization, AlgoOpportunity.FrequencyDesired.Monthly);
	                }
	            	else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year7, potentialProduct.mortgageType)){
	            		expectedPaymentAmount = CalculatePayments.calculateMortgagePayment(productInterestRate, expectedMortgageAmount, amortization, AlgoOpportunity.FrequencyDesired.Monthly);
	                }
	            	else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year8, potentialProduct.mortgageType)){
	            		expectedPaymentAmount = CalculatePayments.calculateMortgagePayment(productInterestRate, expectedMortgageAmount, amortization, AlgoOpportunity.FrequencyDesired.Monthly);
	                }
	            	else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year9, potentialProduct.mortgageType)){
	            		expectedPaymentAmount = CalculatePayments.calculateMortgagePayment(productInterestRate, expectedMortgageAmount, amortization, AlgoOpportunity.FrequencyDesired.Monthly);
	                }
	            	else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year10, potentialProduct.mortgageType)){
	            		expectedPaymentAmount = CalculatePayments.calculateMortgagePayment(productInterestRate, expectedMortgageAmount, amortization, AlgoOpportunity.FrequencyDesired.Monthly);
	                }
	            	else {
	            		expectedPaymentAmount = CalculatePayments.calculateMortgagePayment(interestRateVar, expectedMortgageAmount, amortization, AlgoOpportunity.FrequencyDesired.Monthly);		
	            	}
	            }
	            else {
	        		expectedPaymentAmount = CalculatePayments.calculateMortgagePayment(interestRateVar, expectedMortgageAmount, amortization, AlgoOpportunity.FrequencyDesired.Monthly);		
	        	}
	            costToUse = expectedPaymentAmount;    
	            expectedFitnessPayment = CalculatePayments.calculateMortgagePayment(productInterestRate, expectedMortgageAmount, amortization, AlgoOpportunity.FrequencyDesired.Monthly);
	        }    
			
		} catch (Exception e) {
			e.printStackTrace();
            Task task1 = new Task(
            		"Error - Please report to Technical Support", 
            		e.getMessage(), 
            		"Assistant", "OpportunityID", "BrokerTeamID", deadline, "Assistant", (double)0, 0.05, currentDate, deadline, 1);
            assistantTasks.add(task1);
		}	
		
	}
	

    public void CalculateCommissions()
    {
    	
        try {
			baseCompAmount = (potentialProduct.baseCommission / 10000) * expectedMortgageAmount;  
			marketingCompAmount = (potentialProduct.marketingCommission / 10000) * expectedMortgageAmount;
			trailerCompAmount = (potentialProduct.trailerCommission / 10000) * expectedMortgageAmount;
			
			// Lender, Commitment, Private
			if (potentialProduct.trailerCommission > 0){
				if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year2, potentialProduct.term))
			    {
			        trailerCompAmount = ((potentialProduct.trailerCommission / 10000) * expectedMortgageAmount) * 2;    
			    }
			    else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year3, potentialProduct.term))
			    {
			        trailerCompAmount = ((potentialProduct.trailerCommission / 10000) * expectedMortgageAmount) * 3;
			    }
			    else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year4, potentialProduct.term))
			    {
			        trailerCompAmount = ((potentialProduct.trailerCommission / 10000) * expectedMortgageAmount) * 4;
			    }
			    else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year5, potentialProduct.term))
			    {
			        trailerCompAmount = ((potentialProduct.trailerCommission / 10000) * expectedMortgageAmount) * 5;
			    }
			    else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year6, potentialProduct.term))
			    {
			        trailerCompAmount = ((potentialProduct.trailerCommission / 10000) * expectedMortgageAmount) * 6;
			    }
			    else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year7, potentialProduct.term))
			    {
			        trailerCompAmount = ((potentialProduct.trailerCommission / 10000) * expectedMortgageAmount) * 7;
			    }
			    else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year8, potentialProduct.term))
			    {
			        trailerCompAmount = ((potentialProduct.trailerCommission / 10000) * expectedMortgageAmount) * 8;
			    }
			    else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year9, potentialProduct.term))
			    {
			        trailerCompAmount = ((potentialProduct.trailerCommission / 10000) * expectedMortgageAmount) * 9;
			    }
			    else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year10, potentialProduct.term))
			    {
			        trailerCompAmount = ((potentialProduct.trailerCommission / 10000) * expectedMortgageAmount) * 10;
			    }
			}
			else 
				trailerCompAmount = 0;
			
			// Calculate Volume Bonus ... there are many different Volume Bonus Comp plans.  This structure should address them all
			double volumeBonusCommissionRate = 1;
			
			//load Lender from db 
			// FIXME: #HIBERNATE_SESSION_PROBLEM
			CrudServices<Lender> crud = new CrudServices<Lender>(Lender.class, true);
			crud.setHsession(hsession);
			//logger.info("potentialProduct:" + potentialProduct.lender);
			Lender lender = crud.get(potentialProduct.lender);
			if(lender ==  null){
				totalCompAmount = baseCompAmount + marketingCompAmount + trailerCompAmount + brokerFee;
				return;
			}
				
			String selectionInt = "0";
			if (lender != null){
				if (lender.bonusCompPeriod != null){
					if (lender.bonusCompPeriod.contains("year"))
			        	selectionInt = "1";
			        else if (lender.bonusCompPeriod.contains("monthly"))
			        	selectionInt = "2";
			        else if (lender.bonusCompPeriod.contains("roll"))
			        	selectionInt = "3";
			        else if (lender.bonusCompPeriod.contains("quarter"))
			        	selectionInt = "4";
				}        	
			}
			
			//lender.bonusCompPeriod = "Quarterly";
			//lender.ytdVolume = 0.0;
			
//        Lender lender1 = new Lender(potentialProduct.lender);
//        AlgoLender lender = new AlgoLender(lender1);
			
			
			
//        if (lender.bonusCompPeriod.equalsIgnoreCase(AlgoLender.BonusCompMethod.YearToDate.name()))
			if (SelectionHelper.compareSelection(AlgoLender.BonusCompMethod.YearToDate, selectionInt))
			{
			    // These are not else if statements in that we want the "Max Applicable" rate and many lenders have only 1 threshold, while others may have 4 or 5
			    if (lender.ytdVolume < potentialProduct.volBonus1Threshold)
			        volumeBonusCommissionRate = 1;
			    if (lender.ytdVolume >= potentialProduct.volBonus1Threshold)
			        volumeBonusCommissionRate = potentialProduct.volBonus1;
			    if (lender.ytdVolume >= potentialProduct.volBonus2Threshold)
			        volumeBonusCommissionRate = potentialProduct.volBonus2;
			    if (lender.ytdVolume >= potentialProduct.volBonus3Threshold)
			        volumeBonusCommissionRate = potentialProduct.volBonus3;
			    if (lender.ytdVolume >= potentialProduct.volBonus4Threshold)
			        volumeBonusCommissionRate = potentialProduct.volBonus4;
			    if (lender.ytdVolume >= potentialProduct.volBonus5Threshold)
			        volumeBonusCommissionRate = potentialProduct.volBonus5;
			}
			else if (SelectionHelper.compareSelection(AlgoLender.BonusCompMethod.Rolling12Month, selectionInt))
			{
			    // These are not else if statements in that we want the "Max Applicable" rate and many lenders have only 1 threshold, while others may have 4 or 5
			    if (lender.rolling12MoVolume < potentialProduct.volBonus1Threshold)
			        volumeBonusCommissionRate = 1;
			    if (lender.rolling12MoVolume >= potentialProduct.volBonus1Threshold)
			        volumeBonusCommissionRate = potentialProduct.volBonus1;
			    if (lender.rolling12MoVolume >= potentialProduct.volBonus2Threshold)
			        volumeBonusCommissionRate = potentialProduct.volBonus2;
			    if (lender.rolling12MoVolume >= potentialProduct.volBonus3Threshold)
			        volumeBonusCommissionRate = potentialProduct.volBonus3;
			    if (lender.rolling12MoVolume >= potentialProduct.volBonus4Threshold)
			        volumeBonusCommissionRate = potentialProduct.volBonus4;
			    if (lender.rolling12MoVolume >= potentialProduct.volBonus5Threshold)
			        volumeBonusCommissionRate = potentialProduct.volBonus5;
			}
			else if (SelectionHelper.compareSelection(AlgoLender.BonusCompMethod.Quarterly, selectionInt))
			{
			    // How is Lender.Q1 Volume Determined?  
			    // Is it a script to run the report daily and drive data into field in Lender Object? 
			    // Is it run by Underwriting and fed back to OpenERP? 
			    // Natively in OpenERP may be the most efficient method ...
				
				Calendar cal = Calendar.getInstance();
				cal.setTime(algoClientOpportunity.fundingDate);
				int fundingMonth = cal.get(Calendar.MONTH)+1;
				
			    if (fundingMonth==1
			        || fundingMonth==2
			        || fundingMonth==3)
			    {
			        if (lender.q1Volume < potentialProduct.volBonus1Threshold)
			            volumeBonusCommissionRate = 1;
			        if (lender.q1Volume >= potentialProduct.volBonus1Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus1;
			        if (lender.q1Volume >= potentialProduct.volBonus2Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus2;
			        if (lender.q1Volume >= potentialProduct.volBonus3Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus3;
			        if (lender.q1Volume >= potentialProduct.volBonus4Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus4;
			        if (lender.q1Volume >= potentialProduct.volBonus5Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus5;
			    }
			    else if (fundingMonth == 4
			        || fundingMonth == 5
			        || fundingMonth == 6)
			    {
			        if (lender.q2Volume < potentialProduct.volBonus1Threshold)
			            volumeBonusCommissionRate = 1;
			        if (lender.q2Volume >= potentialProduct.volBonus1Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus1;
			        if (lender.q2Volume >= potentialProduct.volBonus2Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus2;
			        if (lender.q2Volume >= potentialProduct.volBonus3Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus3;
			        if (lender.q2Volume >= potentialProduct.volBonus4Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus4;
			        if (lender.q2Volume >= potentialProduct.volBonus5Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus5;
			    }
			    else if (fundingMonth == 7
			        || fundingMonth == 8
			        || fundingMonth == 9)
			    {
			        if (lender.q3Volume < potentialProduct.volBonus1Threshold)
			            volumeBonusCommissionRate = 1;
			        if (lender.q3Volume >= potentialProduct.volBonus1Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus1;
			        if (lender.q3Volume >= potentialProduct.volBonus2Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus2;
			        if (lender.q3Volume >= potentialProduct.volBonus3Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus3;
			        if (lender.q3Volume >= potentialProduct.volBonus4Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus4;
			        if (lender.q3Volume >= potentialProduct.volBonus5Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus5;
			    }
			    else if (fundingMonth == 7
			        || fundingMonth == 8
			        || fundingMonth == 9)
			    {
			        if (lender.q3Volume < potentialProduct.volBonus1Threshold)
			            volumeBonusCommissionRate = 1;
			        if (lender.q3Volume >= potentialProduct.volBonus1Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus1;
			        if (lender.q3Volume >= potentialProduct.volBonus2Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus2;
			        if (lender.q3Volume >= potentialProduct.volBonus3Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus3;
			        if (lender.q3Volume >= potentialProduct.volBonus4Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus4;
			        if (lender.q3Volume >= potentialProduct.volBonus5Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus5;
			    }
			    else if (fundingMonth == 10
			        || fundingMonth == 11
			        || fundingMonth == 12)
			    {
			        if (lender.q4Volume < potentialProduct.volBonus1Threshold)
			            volumeBonusCommissionRate = 1;
			        if (lender.q4Volume >= potentialProduct.volBonus1Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus1;
			        if (lender.q4Volume >= potentialProduct.volBonus2Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus2;
			        if (lender.q4Volume >= potentialProduct.volBonus3Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus3;
			        if (lender.q4Volume >= potentialProduct.volBonus4Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus4;
			        if (lender.q4Volume >= potentialProduct.volBonus5Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus5;
			    }
			}
			else if (SelectionHelper.compareSelection(AlgoLender.BonusCompMethod.Monthly, selectionInt))
			{
				Calendar cal = Calendar.getInstance();
				cal.setTime(algoClientOpportunity.fundingDate);
				int fundingMonth = cal.get(Calendar.MONTH)+1;
				
			    if (fundingMonth == 1)
			    {
			        if (lender.janVolume < potentialProduct.volBonus1Threshold)
			            volumeBonusCommissionRate = 1;
			        if (lender.janVolume >= potentialProduct.volBonus1Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus1;
			        if (lender.janVolume >= potentialProduct.volBonus2Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus2;
			        if (lender.janVolume >= potentialProduct.volBonus3Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus3;
			        if (lender.janVolume >= potentialProduct.volBonus4Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus4;
			        if (lender.janVolume >= potentialProduct.volBonus5Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus5;
			    }
			    else if (fundingMonth == 2)
			    {
			        if (lender.febVolume < potentialProduct.volBonus1Threshold)
			            volumeBonusCommissionRate = 1;
			        if (lender.febVolume >= potentialProduct.volBonus1Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus1;
			        if (lender.febVolume >= potentialProduct.volBonus2Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus2;
			        if (lender.febVolume >= potentialProduct.volBonus3Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus3;
			        if (lender.febVolume >= potentialProduct.volBonus4Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus4;
			        if (lender.febVolume >= potentialProduct.volBonus5Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus5;
			    }
			    else if (fundingMonth == 3)
			    {
			        if (lender.marVolume < potentialProduct.volBonus1Threshold)
			            volumeBonusCommissionRate = 1;
			        if (lender.marVolume >= potentialProduct.volBonus1Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus1;
			        if (lender.marVolume >= potentialProduct.volBonus2Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus2;
			        if (lender.marVolume >= potentialProduct.volBonus3Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus3;
			        if (lender.marVolume >= potentialProduct.volBonus4Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus4;
			        if (lender.marVolume >= potentialProduct.volBonus5Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus5;
			    }
			    else if (fundingMonth == 4)
			    {
			        if (lender.aprVolume < potentialProduct.volBonus1Threshold)
			            volumeBonusCommissionRate = 1;
			        if (lender.aprVolume >= potentialProduct.volBonus1Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus1;
			        if (lender.aprVolume >= potentialProduct.volBonus2Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus2;
			        if (lender.aprVolume >= potentialProduct.volBonus3Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus3;
			        if (lender.aprVolume >= potentialProduct.volBonus4Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus4;
			        if (lender.aprVolume >= potentialProduct.volBonus5Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus5;
			    }
			    else if (fundingMonth == 5)
			    {
			        if (lender.mayVolume < potentialProduct.volBonus1Threshold)
			            volumeBonusCommissionRate = 1;
			        if (lender.mayVolume >= potentialProduct.volBonus1Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus1;
			        if (lender.mayVolume >= potentialProduct.volBonus2Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus2;
			        if (lender.mayVolume >= potentialProduct.volBonus3Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus3;
			        if (lender.mayVolume >= potentialProduct.volBonus4Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus4;
			        if (lender.mayVolume >= potentialProduct.volBonus5Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus5;
			    }
			    else if (fundingMonth == 6)
			    {
			        if (lender.juneVolume < potentialProduct.volBonus1Threshold)
			            volumeBonusCommissionRate = 1;
			        if (lender.juneVolume >= potentialProduct.volBonus1Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus1;
			        if (lender.juneVolume >= potentialProduct.volBonus2Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus2;
			        if (lender.juneVolume >= potentialProduct.volBonus3Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus3;
			        if (lender.juneVolume >= potentialProduct.volBonus4Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus4;
			        if (lender.juneVolume >= potentialProduct.volBonus5Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus5;
			    }
			    else if (fundingMonth == 7)
			    {
			        if (lender.julyVolume < potentialProduct.volBonus1Threshold)
			            volumeBonusCommissionRate = 1;
			        if (lender.julyVolume >= potentialProduct.volBonus1Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus1;
			        if (lender.julyVolume >= potentialProduct.volBonus2Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus2;
			        if (lender.julyVolume >= potentialProduct.volBonus3Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus3;
			        if (lender.julyVolume >= potentialProduct.volBonus4Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus4;
			        if (lender.julyVolume >= potentialProduct.volBonus5Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus5;
			    }
			    else if (fundingMonth == 8)
			    {
			        if (lender.augVolume < potentialProduct.volBonus1Threshold)
			            volumeBonusCommissionRate = 1;
			        if (lender.augVolume >= potentialProduct.volBonus1Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus1;
			        if (lender.augVolume >= potentialProduct.volBonus2Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus2;
			        if (lender.augVolume >= potentialProduct.volBonus3Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus3;
			        if (lender.augVolume >= potentialProduct.volBonus4Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus4;
			        if (lender.augVolume >= potentialProduct.volBonus5Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus5;
			    }
			    else if (fundingMonth == 9)
			    {
			        if (lender.septVolume < potentialProduct.volBonus1Threshold)
			            volumeBonusCommissionRate = 1;
			        if (lender.septVolume >= potentialProduct.volBonus1Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus1;
			        if (lender.septVolume >= potentialProduct.volBonus2Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus2;
			        if (lender.septVolume >= potentialProduct.volBonus3Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus3;
			        if (lender.septVolume >= potentialProduct.volBonus4Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus4;
			        if (lender.septVolume >= potentialProduct.volBonus5Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus5;
			    }
			    else if (fundingMonth == 10)
			    {
			        if (lender.octVolume < potentialProduct.volBonus1Threshold)
			            volumeBonusCommissionRate = 1;
			        if (lender.octVolume >= potentialProduct.volBonus1Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus1;
			        if (lender.octVolume >= potentialProduct.volBonus2Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus2;
			        if (lender.octVolume >= potentialProduct.volBonus3Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus3;
			        if (lender.octVolume >= potentialProduct.volBonus4Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus4;
			        if (lender.octVolume >= potentialProduct.volBonus5Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus5;
			    }
			    else if (fundingMonth == 11)
			    {
			        if (lender.novVolume < potentialProduct.volBonus1Threshold)
			            volumeBonusCommissionRate = 1;
			        if (lender.novVolume >= potentialProduct.volBonus1Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus1;
			        if (lender.novVolume >= potentialProduct.volBonus2Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus2;
			        if (lender.novVolume >= potentialProduct.volBonus3Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus3;
			        if (lender.novVolume >= potentialProduct.volBonus4Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus4;
			        if (lender.novVolume >= potentialProduct.volBonus5Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus5;
			    }
			    else if (fundingMonth == 12)
			    {
			        if (lender.decVolume < potentialProduct.volBonus1Threshold)
			            volumeBonusCommissionRate = 1;
			        if (lender.decVolume >= potentialProduct.volBonus1Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus1;
			        if (lender.decVolume >= potentialProduct.volBonus2Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus2;
			        if (lender.decVolume >= potentialProduct.volBonus3Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus3;
			        if (lender.decVolume >= potentialProduct.volBonus4Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus4;
			        if (lender.decVolume >= potentialProduct.volBonus5Threshold)
			            volumeBonusCommissionRate = potentialProduct.volBonus5;
			    }
			}
			volumeBonusCompAmount = (volumeBonusCommissionRate / 10000) * expectedMortgageAmount;
			
			// In the event it is a B,C,D Lender that involves a Private Fee, Lender Fee, Broker Fee - Calculate the Broker Fees ... 
			// Also need to set Lender Fees into the Cost / Product / Solution ... 
			
			// Add Together for Total Compensation
			totalCompAmount = baseCompAmount + volumeBonusCompAmount + marketingCompAmount + trailerCompAmount + brokerFee;
			// Lender, Private and Commitment Fees may need to be added in here ... 
		} catch (Exception e) {
			
			StackTraceElement[] stack = e.getStackTrace();
		    String exception = "";
		    for (StackTraceElement s : stack) {
		        exception = exception + s.toString() + "\n\t\t";
		    }
		    logger.info(exception);
            
			Calendar currentDate2 = new GregorianCalendar();
			Calendar deadline2 = new GregorianCalendar();
			deadline2.add(Calendar.HOUR, 1);
			e.printStackTrace();
			Task task2 = new Task("There was an ERROR IN THE UNDERWRITE2 CLASS ERROR SECTION 4220.  Please copy-paste the text in the description of this task into an email and send it to techsupport@visdom.ca.", 
					"OpportunityID" + clientOpportunity.applicationNo + " - "  + e.getMessage() + exception,
			"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
            assistantTasks.add(task2); 
            
		}
    
    }



    public void calculateCashback()
    {
        // Needs to confirm Cashback is a percent, not Basis points.
        if (algoPotentialProduct.isCashback == true)
            cashbackAmount = (potentialProduct.cashback / 100) * clientOpportunity.desiredMortgageAmount;
        else
            cashbackAmount = (1 / 100) * clientOpportunity.desiredMortgageAmount; // ensureing we avoid divide by 0 errors.  Check Fitness Formula to see if this is required
    }

    public void calculatePayoutPenalty()
    {
        // Interest Rate differential
        // What rate they use
        // WHat is the term used ... Remaining Term or Original Term
        // Mentodology

        double payoutAttractiveness = 0;
        if (algoPotentialProduct.termInMonths > algoPotentialProduct.monthsClosed)
            payoutAttractiveness += 3;
        
        if (potentialProduct.isPenaltyGreater != null && potentialProduct.isPenaltyGreater==false)
            payoutAttractiveness += 10;
        if (potentialProduct.waivePenaltyIfRetain && potentialProduct.waivePenaltyIfRetain == true)
            payoutAttractiveness += 5;

        payoutAttractiveness += (6 - potentialProduct.monthsInterestPenalty);
        // This method needs an overhaul with Kendall ... TODO How are we going to manage this portion of the algorithm 


        // This is actuall not calculating the penalty ... It is calculating the attractiveness of a mortgage based on its
        // like payout penalty at some point in future
        // I am awaiting information from Kendall before this method can be completed.
        payoutAmount = payoutAttractiveness;
    }

    public void CalculatePaymentOptionsAttraction()
    {
        double totalPaymentOptionValue = 0;
        // Need to set the "value" of each payment option with Kendall during Code Review
        if (potentialProduct.weeklyPayments == true)
            totalPaymentOptionValue += 1;
        if (potentialProduct.biWeeklyPayments == true)
            totalPaymentOptionValue += 1;
        if (potentialProduct.semiMonthlyPayments == true)
            totalPaymentOptionValue += 1;
        if (potentialProduct.monthlyPayments == true)
            totalPaymentOptionValue += 1;
        if (potentialProduct.doubleUpPayments == true)
            totalPaymentOptionValue += 1;
        if (potentialProduct.increasePayments == true)
            totalPaymentOptionValue += 1;
        if (potentialProduct.extraAnnualPaydown == true)
            totalPaymentOptionValue += 1;
        if (potentialProduct.extraAppliedAnyTime == true)
            totalPaymentOptionValue += 1;
        if (potentialProduct.rewardPoints == true)
            totalPaymentOptionValue += 1;
        if (potentialProduct.skipPaymentPossible == true)
            totalPaymentOptionValue += 1;
        if (potentialProduct.prepayToReduce == true)
            totalPaymentOptionValue += 1;
        if (potentialProduct.allowTitleIns == true) // Provide a bit of extra credit if they allow Title Insurance
        	totalPaymentOptionValue += 4;
        
        paymentOptions = totalPaymentOptionValue;
    }

    public void calculateBranchAttend()
    {
        // In this variable, a Lender with Branch signing is less attractive than one where there is no branch signing.
        // Thus, to keep fitness formula consistent, true Branch signing has been assigned a low number and false has been assigned
        // a much much higher number in order to create a large variance in the fitness formula.
        if (potentialProduct.branchSigning == true)
            branchAttend = 1;
        else
            branchAttend = 4; // ensure that this feeds fitness to bias companies that are not Branch attend          
    }
    public void CalculateApplicationEase()
    {
        if (SelectionHelper.compareSelection(AlgoProduct.ApplicationMethod.Filelogix, potentialProduct.applicationMethod))
            applicationEase = 1;
        else
            applicationEase = 10;        
    }
    // Please note this entire Fitness Function may end up with a math change once we test and consider it a bit, but the factors will remain. 
    public double getFitness(double avgCost,double AvgOneTimeCost, double avgInterestRate, double avgRisk, double avgCashback,
        double avgPayoutPenalty, double avgPaymentOption, double avgBaseCompensation, double avgBonus, double avgMarketingComp,
        double avgTrailerComp, double avgSpeed, double avgBusinessEase, double avgBranchAttend, double avgUnderwriterPref, double lenderApplicationEase, double avgTotalCompensation)
    {
//    	logger.info("getFitness:" + avgCost + "," + AvgOneTimeCost+ "," + avgInterestRate+ "," + avgRisk+ "," + avgCashback+ "," + 
//    	        avgPayoutPenalty+ "," + avgPaymentOption+ "," + avgBaseCompensation+ "," + avgBonus+ "," + avgMarketingComp+ "," + 
//    	        avgTrailerComp+ "," + avgSpeed+ "," + avgBusinessEase+ "," + avgBranchAttend+ "," + avgUnderwriterPref+ "," + lenderApplicationEase+ "," + avgTotalCompensation);
        // As I am reviewing with Kendall ... Ensure that each formula creates a higher number as it is more attractive
        double productFitness = 0;
        double productRisk = 1; // this needs a calculation method if we decide to include risk;
        // Should be eliminate the effort to quanitfy risk and cover it in the proposal? 
        // If so, how do we want to consider risk? 
        //COST NEEDS  TO COSIDER FEES ... 

        // Need to review in detail with Kendall to confirm and to set weightings. 

        // Need to consider divide by 0 errors ... might need to ensure empty factors are not 0 ... 
        double costFactor = 0; // 
        if(expectedFitnessPayment != 0)//Lower is better so divisors are reversed i.e. the lower the cost, the higher the fitness < avg = > 1, 
        	costFactor = avgCost / expectedFitnessPayment * FitnessFactors.costWeight;        
        double OneTimeCostFactor = 0;
        if(oneTimeFees != 0)
        	OneTimeCostFactor = AvgOneTimeCost / oneTimeFees * FitnessFactors.oneTimeCostWeight;  //Lower is better so divisors are reversed
        double interestRateFactor = 0;
        if(avgInterestRate != 0)
        	interestRateFactor = avgInterestRate / interestRate * FitnessFactors.interestRateWeight;//Lower is better so divisors are reversed
        double riskFactor = 0;
        if(avgRisk != 0)
        	riskFactor = productRisk / avgRisk * FitnessFactors.riskWeight;
        double cashBackFactor = 0;
        if(avgCashback != 0)
        	cashBackFactor = cashbackAmount / avgCashback * FitnessFactors.cashbackWeight;
        double payoutPenaltyFactor = 0;
        if(payoutAmount != 0)
        	payoutPenaltyFactor = avgPayoutPenalty / payoutAmount * FitnessFactors.payoutPenaltyWeight; //Lower is better so divisors are reversed
        double paymentOptionsFactor = 0;
        if(avgPaymentOption != 0)
        	paymentOptionsFactor = paymentOptions / avgPaymentOption * FitnessFactors.paymentOptionsWeight;
        double baseCompFactor = 0;
        if(avgBaseCompensation != 0)
        	baseCompFactor = baseCompAmount / avgBaseCompensation * FitnessFactors.compensationWeight;
        double bonusCompFactor = 0;
        if(avgBonus != 0)
        	bonusCompFactor = volumeBonusCompAmount / avgBonus * FitnessFactors.bonusWeight;
        double marketingCompFactor = 0;
        if(avgMarketingComp != 0)
        	marketingCompFactor = marketingCompAmount / avgMarketingComp * FitnessFactors.marketingCompWeight;
        double trailerCompFactor = 0;
        if(avgTrailerComp != 0)
        	trailerCompFactor = trailerCompAmount / avgTrailerComp * FitnessFactors.trailerCompWeight;
        double totalCompFactor = 0;
        if(avgTotalCompensation != 0)
        	totalCompFactor = totalCompAmount / avgTotalCompensation * FitnessFactors.totalCompWeight;
        double speedFactor = 0;
        if(avgSpeed != 0)
        	speedFactor = avgProcessingSpeed / avgSpeed * FitnessFactors.speedWeight;
        double businessEaseFactor = 0;
        if(avgBusinessEase != 0 )
        	businessEaseFactor = businessEase / avgBusinessEase * FitnessFactors.businessEaseWeight;
        double branchAttendFactor = 0;
        if(avgBranchAttend != 0)
        	branchAttendFactor = branchAttend / avgBranchAttend * FitnessFactors.branchAttendWeight;
        double underwriterPrefFactor = 0;
        if(avgUnderwriterPref != 0)
        	underwriterPrefFactor = underwriterPref / avgUnderwriterPref * FitnessFactors.underwriterPrefWeight;
        
        double ApplicationEaseFactor = 0;
        if(lenderApplicationEase != 0)
        	lenderApplicationEase = applicationEase / lenderApplicationEase * FitnessFactors.applicationEase;


        productFitness = costFactor + interestRateFactor + riskFactor + cashBackFactor + payoutPenaltyFactor +
            paymentOptionsFactor + baseCompFactor + bonusCompFactor + marketingCompFactor + trailerCompFactor +
            speedFactor + businessEaseFactor + branchAttendFactor + underwriterPrefFactor + lenderApplicationEase;  
        //logger.info("Fitness: " + productFitness);        
        return productFitness;
    }

    public double roundedNumber(double inputDouble){
		double value = inputDouble;
		value = value*100;
		value = (double)((int) value);
		value = value /100;
		return value;
	}
    
    private double calculateReferralFee(double mortgageAmount){
		double calcReferralFee = 0;
		
		if(mortgageAmount < 50000){
			calcReferralFee = 0;
		}
		else if(mortgageAmount >= 50000 && mortgageAmount < 200000){
			calcReferralFee = 50;
		}
		else if(mortgageAmount >= 200000 && mortgageAmount < 350000){
			calcReferralFee = 200;
		}
		else if(mortgageAmount >= 350000 && mortgageAmount < 600000){
			calcReferralFee = 450;
		}
		else if(mortgageAmount >= 600000 && mortgageAmount < 900000){
			calcReferralFee = 700;
		}
		else if(mortgageAmount >= 900000){
			calcReferralFee = 1000;
		}
		return calcReferralFee;
	}
    
}