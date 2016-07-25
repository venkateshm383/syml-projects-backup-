package com.syml.bean.algo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import com.syml.domain.Applicant;
import com.syml.domain.Income;
import com.syml.domain.Lender;
import com.syml.domain.Liability;
import com.syml.domain.Mortgage;
import com.syml.domain.Product;
import com.syml.domain.Property;
import com.syml.service.CrudServices;
import com.syml.util.SelectionHelper;


public class UnderwriteAll extends UnderwritingBase
{
	private Session hsession;
	public double propertyTaxTotal = 0;
	public double condoFeesTotal = 0;
	public double heatCostsToUse = 0;
	public double condoFeesToUse = 0;
	public double propertyTaxesToUse;
	public String lenderName;
	

    public UnderwriteAll(AlgoOpportunity clientOpportunity1, AlgoProduct potentialProduct1, Session hsession)
    {
    	super(clientOpportunity1, potentialProduct1, hsession);
    	this.hsession = hsession;
        this.clientOpportunity = clientOpportunity1.opp;
        this.potentialProduct = potentialProduct1.product;
        this.algoClientOpportunity = clientOpportunity1;
        this.algoPotentialProduct = potentialProduct1;
        
        interestRate = potentialProduct.interestRate;
        avgProcessingSpeed = potentialProduct.avgProcessingSpeed;
        businessEase = potentialProduct.businessEase;
        underwriterPref = potentialProduct.underwriterPref;
        lenderName = potentialProduct.lendername;
        hasEmployedIncome = false;
        allowedProvinces = Arrays.asList(potentialProduct.allowProvinces.split(","));
        assistantNotes = new ArrayList<AlgoNote>();
        brokerNotes = new ArrayList<AlgoNote>();
        lenderNotes = new ArrayList<AlgoNote>();
        dealNotes = new ArrayList<AlgoNote>();
        marketingTemplateNotes = new ArrayList<AlgoNote>();
        nonOptimalRateNotes = new ArrayList<AlgoNote>();
        totalOpportunityLiabilities = new ArrayList<Liability>();
        failReasons = new ArrayList<String>();

        countOfAcceptableCritera = 0;
        countOfQuestionableCriteria = 0;
        countOfUnacceptableCriteria = 0;
        failedNotesCounter = 0;
        assistantNotesCounter = 0;
		masterLiabilitiesList = new ArrayList<Liability>();
		addedLiabilities = new ArrayList<Liability>();
		mobileProviders = new ArrayList<String>();
		addCellCompanies();
        if (interestRate < 1)
			System.out.println("Rate Issue");
    }

    public UnderwriteAll(AlgoOpportunity clientAlgoOpportunity,
			Product algoProduct, Session session) {
    	super(clientAlgoOpportunity, algoProduct, session);
    	this.hsession = hsession;
        this.clientOpportunity = clientAlgoOpportunity.opp;
        this.potentialProduct = algoProduct;
        this.algoClientOpportunity = clientAlgoOpportunity;
        
        interestRate = potentialProduct.interestRate;
        avgProcessingSpeed = potentialProduct.avgProcessingSpeed;
        businessEase = potentialProduct.businessEase;
        underwriterPref = potentialProduct.underwriterPref;
        hasEmployedIncome = false;
        // Create Provinces in List of Allowed Provinces
        allowedProvinces = Arrays.asList(potentialProduct.allowProvinces.split(","));
        
        assistantNotes = new ArrayList<AlgoNote>();
        brokerNotes = new ArrayList<AlgoNote>();
        lenderNotes = new ArrayList<AlgoNote>();
        dealNotes = new ArrayList<AlgoNote>();
        nonOptimalRateNotes = new ArrayList<AlgoNote>();
        totalOpportunityLiabilities = new ArrayList<Liability>();
        failReasons = new ArrayList<String>();
        
        totalDealLiabilityPayments = 0;
    	totalDealLiabilities = 0;
        
        countOfAcceptableCritera = 0;
        countOfQuestionableCriteria = 0;
        countOfUnacceptableCriteria = 0;
        failedNotesCounter = 0;
        assistantNotesCounter = 0;
		masterLiabilitiesList = new ArrayList<Liability>();
		addedLiabilities = new ArrayList<Liability>();
		mobileProviders = new ArrayList<String>();
		addCellCompanies();
        if (interestRate < 1)
			System.out.println("Rate Issue");
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
			System.out.println("Rate Issue");
			
		try {
	
			totalDealIncome = 0;
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
	
			List<Liability> joinLiabilities = new ArrayList<Liability>();
	
			boolean applicantIsImmigrant = false;
			Date applicantImmigrationDate = new Date();
			double MaxEmployedMonths = 0;
	
			double beaconScoreToUse = 0;
			double latePaymentsToUse = 0;
	
			totalDealCollections = 0;
			boolean  refinancePropertyMatched = false;
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
	
							// Monthly Child / Spousal Support Received .... 
							if (applicant.monthlychildsupport != null && applicant.monthlychildsupport > 0){
								double annualChildSupport = applicant.monthlychildsupport * 12;
								totalApplicantIncome = totalApplicantIncome + annualChildSupport;
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
							}
	
							calcDealLiabilities(applicant);
							//For Debugging ... 
//							for (Liability liability : addedLiabilities) {
//								System.out.println(liability.toString());	
//							}							
	
							if (potentialProduct.childSupportTreatment != null){
								if (SelectionHelper.compareSelection(AlgoProduct.SupportTreatment.SubtractFromIncome, potentialProduct.childSupportTreatment)){
									if (applicant.monthlySupportPayment != null){
										double annualChildSupportPayment = applicant.monthlySupportPayment * 12;
										totalApplicantIncome = totalApplicantIncome - annualChildSupportPayment;
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
							double propertyValueTotal = 0;
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
													}
													else
													{
														int IncomePropertyID = 0;
														double netMortgagesPayments = calcNetMortgagePayment(
																applicant,PropertyID,currenMortgage,IncomePropertyID);
														mortgagePayments = mortgagePayments + netMortgagesPayments;
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
								// Set values for later comparision once LTV is calculated.
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
//					System.out.println("Value: " + valueOfProperty);
				
				//#region Determine if HighRatio
				algoClientOpportunity.highRatio = checkIfHighRatio(valueOfProperty, expectedMortgageAmount);
	
//				if (valueOfProperty < 40000)
//					System.out.println("Value: " + valueOfProperty);
				
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
				double incomeToUse = totalDealIncome;
				double mortgagePaymentsToUse = totalDealMortgagesPayments;
				double liabilitiesToUse = totalDealLiabilityPayments * 12; // Problem with Dual Count (Is it really 6000??? or mortgages 2x ?
				annualHeatCost = potentialProduct.minHeatCost * 12;
				propertyTaxesToUse = totalDealPropertyTaxes;
				if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Refinance, clientOpportunity.whatIsYourLendingGoal)){
					heatCostsToUse = totalDealPropertyHeating * 12;
					condoFeesToUse = totalDealCondoFees * 12; 
					propertyTaxesToUse = totalDealPropertyTaxes; 
				}
				else if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Prequalify, clientOpportunity.whatIsYourLendingGoal)){
					heatCostsToUse = (totalDealPropertyHeating * 12) + annualHeatCost; 
					if (clientOpportunity.isCondo == true)
						condoFeesToUse = totalDealCondoFees + ((clientOpportunity.desiredMortgageAmount + clientOpportunity.downpaymentAmount) * 0.008);
					else
						condoFeesToUse = 0;
					
					propertyTaxesToUse = totalDealPropertyTaxes + ((clientOpportunity.desiredMortgageAmount + clientOpportunity.downpaymentAmount) * 0.01);
				}
				else{
					heatCostsToUse = (totalDealPropertyHeating * 12) + annualHeatCost; 
					condoFeesToUse = (totalDealCondoFees + clientOpportunity.condoFees) * 12; 
					propertyTaxesToUse = totalDealPropertyTaxes + clientOpportunity.propertyTaxes; 
				}
	
				// For Debugging
//				if (insuranceAmount > 0)
//					System.out.println("Insurance amount: " + insuranceAmount);
				
				double mortgageAmountWithNoIns = expectedMortgageAmount - insuranceAmount;
		    	double equityOrDownpayment = clientOpportunity.downpaymentAmount;
		    	double projectedPropertyValue = mortgageAmountWithNoIns + clientOpportunity.downpaymentAmount;	
		    	
				// Determine the Maximum Allowable Mortgage Amount for no Condo based upon GDS (This mortgage only)
				double maxMortgageGdsNoCondo = maxMortgageNoCondoBasedOnGDS(totalDealIncome, totalDealMortgagesPayments, totalDealLiabilityPayments, 
						propertyTaxesToUse, heatCostsToUse, allowableRatioForGDS, rateToUse); 
	
				// Determine the Maximum Allowable Mortgage Amount for with a Condo based upon GDS (This mortgage only)
				double maxMortgageGdsWithCondo = maxMortgageWithCondoBasedOnGDS(totalDealIncome, totalDealMortgagesPayments, totalDealLiabilityPayments, 
						propertyTaxesToUse, heatCostsToUse, allowableRatioForGDS, rateToUse);               
	
				// Determine the Maximum Allowable Mortgage Amount for no Condo based upon TDS (Other Mortgages & Other Debts)
				double maxMortgageTdsNoCondo = maxMortgageNoCondoBasedOnTDS(totalDealIncome, totalDealMortgagesPayments, totalDealLiabilityPayments, 
						propertyTaxesToUse, heatCostsToUse, allowableRatioForTDS, rateToUse);             	 
	
				// Determine the Maximum Allowable Mortgage Amount with a Condo based upon TDS (Other Mortgages & Other Debts)
				double maxMortgageTdsWithCondo = maxMortgageWithCondoBasedOnTDS(totalDealIncome, totalDealMortgagesPayments, totalDealLiabilityPayments, 
						propertyTaxesToUse, heatCostsToUse, allowableRatioForTDS, rateToUse); 
	
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
							totalDealPropertyValues = totalDealPropertyValues + valueOfProperty;
							// expectedMortgageAmount = maxMortgageAllowed;  Was this ... Changed for Kevin because bad calc on Line 504 - 522
							expectedMortgageAmount = Math.max(maxMortgageAllowed, clientOpportunity.currentMortgageAmount);
						}
						else{
							double maxMortgageAllowed = Math.min(maxMortgageTdsNoCondo,maxMortgageGdsNoCondo);
							maxMortgageAllowed = Math.min(maxMortgageAllowed, maximumMortgageLTV);     
							totalDealMortgagesBalance = totalDealMortgagesBalance + maxMortgageAllowed - clientOpportunity.currentMortgageAmount;
							totalDealPropertyValues = totalDealPropertyValues + valueOfProperty;
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
						totalDealPropertyValues = totalDealPropertyValues + valueOfProperty;
					}
					else{
						costToUse = calculateExpectedPayment(productInterestRate, interestRateVar, amortization);  
						totalDealMortgagesBalance = totalDealMortgagesBalance + expectedMortgageAmount - clientOpportunity.currentMortgageAmount;
						totalDealPropertyValues = totalDealPropertyValues + valueOfProperty;
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
	
//				if (insuranceAmount > 0)
//					System.out.println("Insurance amount: " + insuranceAmount);
				
				double mortgageAmountWithNoIns2 = expectedMortgageAmount - insuranceAmount;
		    	double equityOrDownpayment2 = clientOpportunity.downpaymentAmount;
		    	double projectedPropertyValue2 = mortgageAmountWithNoIns + clientOpportunity.downpaymentAmount;	
		    	
		    	if (Math.round(mortgageAmountWithNoIns2) != Math.round(mortgageAmountWithNoIns)){
		    		System.out.println("mortgageAmountWithNoIns: " + mortgageAmountWithNoIns + ", " + mortgageAmountWithNoIns2);
			    	System.out.println("equityOrDownpayment: " + equityOrDownpayment + ", " + equityOrDownpayment2);
			    	System.out.println("projectedPropertyValue: " + projectedPropertyValue + ", " + projectedPropertyValue2);			    		
		    	}
		    		
				// Calculate GDS and TDS 
		    	double qualifyMortgagePayment = 0;
				if (SelectionHelper.compareSelection(AlgoProduct.MortgageType.LOC, potentialProduct.mortgageType))
					qualifyMortgagePayment = CalculatePayments.calculateMonthlyLOCPayment(interestRateVar, expectedMortgageAmount);
				else
					qualifyMortgagePayment = calculateExpectedPayment(productInterestRate, interestRateVar, this.amortization);
				
				//anticipatedMortgagePayment = calculateExpectedPayment(productInterestRate, interestRateVar, amortization); 
				
				totalDealGDSAmount = (qualifyMortgagePayment * 12) + heatCostsToUse + propertyTaxesToUse + condoFeesToUse;
				//totalDealGDSAmount = (anticipatedMortgagePayment * 12) + annualHeatCost + clientOpportunity.propertyTaxes + (clientOpportunity.condoFees * 12);
				double OpportunityGDSRatio = totalDealGDSAmount / totalDealIncome * 100;         
				
				opportunityTDSAmount = 0;
				if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Refinance, clientOpportunity.whatIsYourLendingGoal)){ 					
					// if refinance included in properties list this formula is true, If not, it is creating negative mortgage payment
					if (refinancePropertyMatched == false){
						opportunityTDSAmount = (qualifyMortgagePayment * 12) + heatCostsToUse + propertyTaxesToUse + condoFeesToUse + (mortgagePaymentsToUse * 12) + liabilitiesToUse;
					}
					else{
						opportunityTDSAmount = (qualifyMortgagePayment * 12) + heatCostsToUse + propertyTaxesToUse + condoFeesToUse + ((mortgagePaymentsToUse - clientOpportunity.currentMonthlyPayment) * 12) + liabilitiesToUse;	
					}
					
				}
				else{
					opportunityTDSAmount = (qualifyMortgagePayment * 12) + (mortgagePaymentsToUse * 12) + heatCostsToUse + propertyTaxesToUse + condoFeesToUse + liabilitiesToUse;
				}                	
	
				double opportunityTDSRatio =	opportunityTDSAmount / totalDealIncome * 100;
	
				// Set Key Values into class variables for later usage ...                
				// TODO Need to test these TDS / Mortgage Amount Calculations ... 
				LTV = preInsuranceMortgageAmount / valueOfProperty * 100;
				GDSRatio = OpportunityGDSRatio;
				TDSRatio = opportunityTDSRatio;         
				allApplicantsTotalIncomes = totalDealIncome;
				totalLiabilityPayments = totalDealGDSAmount;
	
				failedGDS = passFailGDS(allowableRatioForGDS);
	
				failedTDS = passFailTDS(allowableRatioForTDS);
	
	
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
								OpportunityTDSAmount2 = (qualifyMortgagePayment2 * 12) + heatCostsToUse + propertyTaxesToUse + condoFeesToUse + (mortgagePaymentsToUse  * 12) + liabilitiesToUse;
							}
							else{
								OpportunityTDSAmount2 = (qualifyMortgagePayment2 * 12) + heatCostsToUse + propertyTaxesToUse + condoFeesToUse + ((mortgagePaymentsToUse - clientOpportunity.currentMonthlyPayment) * 12) + liabilitiesToUse;	
							}
							
						}
						else{
							OpportunityTDSAmount2 = (qualifyMortgagePayment2 * 12) + heatCostsToUse + propertyTaxesToUse + condoFeesToUse + (mortgagePaymentsToUse * 12) + liabilitiesToUse;
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
						// expectedFitnessPayment = CalculatePayments.calculateMortgagePayment(productInterestRate, expectedMortgageAmount, amortization, AlgoOpportunity.FrequencyDesired.Monthly);
	
					}                    
				}
	
				if(expectedMortgageAmount < 20000)
					System.out.println("Challenge with Mortgage Amount");
				
				if (failedGDS == true || failedTDS == true){
					//checkNonIncomeQualifier();                   	
	
					if(expectedMortgageAmount < 20000)
						System.out.println("Challenge with Mortgage Amount");
	
	
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
	
			// opportunityQualifies = true;
			//System.out.println("OpportunityCreditCategory:" + OpportunityCreditCategory);
			//System.out.println("countOfUnacceptableCriteria:" + countOfUnacceptableCriteria);
			//System.out.println("algoPotentialProduct.allowableBlackFlagGoodCredit:" + algoPotentialProduct.allowableBlackFlagGoodCredit);
			checkOpportunityQualifies();            
	
			if(expectedMortgageAmount < 20000)
				System.out.println("Challenge with Mortgage Amount");
			
//			if (opportunityQualifies == true){				  
//				System.out.println(potentialProduct.name.toString() + " qualitifed with " + countOfUnacceptableCriteria);
//			}
	
			//System.out.println("AllProductAlgo end");
			String QualifyingInfo = this.potentialProduct.name 
					+ ", opportunityQualifies: " + opportunityQualifies
					+ ", GDS: " + GDSRatio + ", GdsAmount: " + totalDealGDSAmount 
					+ ", TDS: " + TDSRatio + ", TdsAmount: " + opportunityTDSAmount
					+ ", FailGDS: " + failedGDS + ", FailTDS: " + failedTDS 
					+ ", Income: " + totalDealIncome + ", Payment: " + anticipatedMortgagePayment + ", annualHeatCost: " + annualHeatCost 
					+ ", propertyTaxes: " + clientOpportunity.propertyTaxes + ", condoFees: " + clientOpportunity.condoFees
					+ ", Insurance: " + insuranceAmount; 
			//System.out.println(QualifyingInfo);
			
			StringBuilder failurestring = new StringBuilder();
			for (String reason : failReasons) {
				failurestring.append(reason + ", ");
				//System.out.println( "Failure Reasons:" + failurestring);
			}
			
			
	
		} catch (Exception e) {
			e.printStackTrace();
			AlgoNote assistNote1 = new AlgoNote(AlgoNote.TypeOfNote.AssistantAction, AlgoNote.Priority.High, e.getMessage());
			assistantNotes.add(assistNote1);
		}	
	}
	
	
	public void checkNonIncomeQualifier(){
		try {
			double valueOfProperty = 0;
			statedIncomeDeal = false;
	    	
	    	// Determine the beacon score to use
			// In this area, set Credit Categorization ....             
            beaconScoreToUse = beaconScore2Use();   
            latePaymentsToUse = latePayments2Use();  
	        //System.out.println("beaconScoreToUse:" + beaconScoreToUse);
	        
	        boolean beaconAcceptable = false;
	        if (beaconScoreToUse >= potentialProduct.statedIncomeMinBeacon){
	        	countOfAcceptableCritera++;
	        	beaconAcceptable = true;
	        }
	        else{
	        	countOfUnacceptableCriteria++;
	        	AlgoNote Note1 = new AlgoNote(AlgoNote.TypeOfNote.Info, AlgoNote.Priority.Med, "The client Credit Rating does not meet the requirements for this non-income qualification product.", "FailedApplication" + failedNotesCounter);
	            failReasons.add("The client Credit Rating does not meet the requirements for this non-income qualification product.");
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
	        	// TODO This needs to be verified with debugging once OpenERP field values are ser
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
	        
	        double preInsuranceMortgageAmount = expectedMortgageAmount;
	        
	     // Check to see that loan amount is < MaximumLoanAmount
	        if (expectedMortgageAmount <= potentialProduct.maxMortgageAllowed)
	            countOfAcceptableCritera++;
	        else
	        {
	            countOfUnacceptableCriteria++;
	            AlgoNote Note1 = new AlgoNote(AlgoNote.TypeOfNote.Info, AlgoNote.Priority.Med, "Application requires a Larger Mortgage than 'Top of List' provide.", "FailedApplication" + failedNotesCounter);
	            failReasons.add("Application requires a Larger Mortgage than 'Top of List' provide.");	            dealNotes.add(Note1);
	            failedNotesCounter++;
	        }

	        // Check to see that loan amount is > MaximumLoanAmount
	        if (expectedMortgageAmount >= potentialProduct.minMortgageAllowed)
	            countOfAcceptableCritera++;
	        else
	        {
	            countOfUnacceptableCriteria++;
	            AlgoNote Note1 = new AlgoNote(AlgoNote.TypeOfNote.Info, AlgoNote.Priority.Med, "Application requires a Smaller Mortgage than 'Top of List' provide.", "FailedApplication" + failedNotesCounter);
	            failReasons.add("Application requires a Smaller Mortgage than 'Top of List' provide.");
	            dealNotes.add(Note1);
	            failedNotesCounter++;
	        }
	        
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
	            failReasons.add("The Loan to Value of this Opportunity is too large for this Product.");
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
	            	AlgoNote assistNote4 = new AlgoNote(AlgoNote.TypeOfNote.AssistantAction, AlgoNote.Priority.High, "The Multiple sources of Downpayment amounts do not equal the Opportunity Down Payment Amount.  Confirm the downpayment amount and adjust fields");
	                assistantNotes.add(assistNote4);
	            }
	            
	            double productInterestRate = potentialProduct.interestRate; // Used on 5 Yr or Longer Terms or Actual Deals
	            double interestRateVar = potentialProduct.qualifyingRate; // Used on Prequalification for < 5 Year or for LOC or for Variable
	            amortization = 25; // Maximum25 Years except on Conventional, can be 30 Years.
	            
	            if (LTV > 80){
					amortization = Math.min(25, clientOpportunity.desiredAmortization);
				}
				else 
					amortization = clientOpportunity.desiredAmortization;
	            
	            // We decided to force a 25 year amortization and simply place deal notes in the proposal to manage desired amortizations longer or shorter 
	            if (amortization < 25){
	            	AlgoNote brokerNote1 = new AlgoNote(AlgoNote.TypeOfNote.BrokerAction, AlgoNote.Priority.Med, "Please Note that " + clientOpportunity.applicants.get(0).applicantName  + " has selected an amortization of " + clientOpportunity.amortization.toString() + ".  This will create a higher monthly payment amount. It is wise to provide the client with some education around this fact and to manage their expectations.");
	                brokerNotes.add(brokerNote1);
	            }
	            
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
	        
	        if (interestRate < 1)
				System.out.println("Rate Issue");
	        
			
		} catch (Exception e) {
			e.printStackTrace();
			AlgoNote assistNote1 = new AlgoNote(AlgoNote.TypeOfNote.AssistantAction, AlgoNote.Priority.High, e.getMessage());
            assistantNotes.add(assistNote1);
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
			CrudServices<Lender> crud = new CrudServices<Lender>(Lender.class, true);
			crud.setHsession(hsession);
			//System.out.println("potentialProduct:" + potentialProduct.lender);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
			AlgoNote assistNote1 = new AlgoNote(AlgoNote.TypeOfNote.AssistantAction, AlgoNote.Priority.High, e.getMessage());
            assistantNotes.add(assistNote1);
		}
    
    }

	@Override
	public String toString() {
		return "UnderwriteAll [hsession=" + hsession + ", propertyTaxTotal="
				+ propertyTaxTotal + ", condoFeesTotal=" + condoFeesTotal
				+ ", heatCostsToUse=" + heatCostsToUse + ", condoFeesToUse="
				+ condoFeesToUse + ", propertyTaxesToUse=" + propertyTaxesToUse
				+ ", underwritingType=" + underwritingType
				+ ", potentialProduct=" + potentialProduct
				+ ", clientOpportunity=" + clientOpportunity
				+ ", algoPotentialProduct=" + algoPotentialProduct
				+ ", algoClientOpportunity=" + algoClientOpportunity
				+ ", countOfAcceptableCritera=" + countOfAcceptableCritera
				+ ", countOfUnacceptableCriteria="
				+ countOfUnacceptableCriteria
				+ ", countOfQuestionableCriteria="
				+ countOfQuestionableCriteria + ", hasSelfEmployedIncome="
				+ hasSelfEmployedIncome + ", fitness=" + fitness
				+ ", mortgageAmount=" + mortgageAmount + ", lenderFeesPercent="
				+ lenderFeesPercent + ", lenderFeesFlat=" + lenderFeesFlat
				+ ", brokerFeesPercent=" + brokerFeesPercent
				+ ", brokerFeesFlat=" + brokerFeesFlat + ", lendableValue="
				+ lendableValue + ", OpportunityCreditCategory="
				+ OpportunityCreditCategory + ", opportunityQualifies="
				+ opportunityQualifies + ", allowableGreyFlagGoodCredit="
				+ allowableGreyFlagGoodCredit
				+ ", allowableGreyFlagMediumCredit="
				+ allowableGreyFlagMediumCredit
				+ ", allowableGreyFlagPoorCredit="
				+ allowableGreyFlagPoorCredit
				+ ", allowableBlackFlagGoodCredit="
				+ allowableBlackFlagGoodCredit
				+ ", allowableBlackFlagMediumCredit="
				+ allowableBlackFlagMediumCredit
				+ ", allowableBlackFlagPoorCredit="
				+ allowableBlackFlagPoorCredit + ", failedNotesCounter="
				+ failedNotesCounter + ", assistantNotesCounter="
				+ assistantNotesCounter + ", totalDealGDSAmount="
				+ totalDealGDSAmount + ", opportunityTDSAmount="
				+ opportunityTDSAmount + ", failedGDS=" + failedGDS
				+ ", failedTDS=" + failedTDS + ", totalDealIncome="
				+ totalDealIncome + ", anticipatedMortgagePayment="
				+ anticipatedMortgagePayment + ", annualHeatCost="
				+ annualHeatCost + ", fixedCost=" + fixedCost
				+ ", variableCost=" + variableCost + ", LOCCost=" + LOCCost
				+ ", costToUse=" + costToUse + ", expectedFitnessPayment="
				+ expectedFitnessPayment + ", interestRate=" + interestRate
				+ ", cashbackAmount=" + cashbackAmount + ", payoutAmount="
				+ payoutAmount + ", paymentOptions=" + paymentOptions
				+ ", baseCompAmount=" + baseCompAmount
				+ ", volumeBonusCompAmount=" + volumeBonusCompAmount
				+ ", bonusCompAmount=" + bonusCompAmount
				+ ", marketingCompAmount=" + marketingCompAmount
				+ ", trailerCompAmount=" + trailerCompAmount + ", lenderFee="
				+ lenderFee + ", brokerFee=" + brokerFee + ", branchAttend="
				+ branchAttend + ", applicationEase=" + applicationEase
				+ ", avgProcessingSpeed=" + avgProcessingSpeed
				+ ", businessEase=" + businessEase + ", underwriterPref="
				+ underwriterPref + ", allApplicantsTotalIncomes="
				+ allApplicantsTotalIncomes + ", GDSRatio=" + GDSRatio
				+ ", TDSRatio=" + TDSRatio + ", LTV=" + LTV
				+ ", insuranceAmount=" + insuranceAmount
				+ ", maximumMortgageNoCondo=" + maximumMortgageNoCondo
				+ ", maximumMortgageCondo=" + maximumMortgageCondo
				+ ", maximumMortgageNoCondoDebtRepay="
				+ maximumMortgageNoCondoDebtRepay
				+ ", maximumMortgageCondoDebtRepay="
				+ maximumMortgageCondoDebtRepay + ", totalLiabilityPayments="
				+ totalLiabilityPayments + ", expectedMortgageAmount="
				+ expectedMortgageAmount + ", baseExpectedMortgageAmount="
				+ baseExpectedMortgageAmount + ", allApplicantsTotalAssets="
				+ allApplicantsTotalAssets + ", totalCompAmount="
				+ totalCompAmount + ", oneTimeFees=" + oneTimeFees
				+ ", allowedProvinces=" + allowedProvinces
				+ ", assistantNotes=" + assistantNotes + ", brokerNotes="
				+ brokerNotes + ", lenderNotes=" + lenderNotes + ", dealNotes="
				+ dealNotes + ", marketingTemplateNotes="
				+ marketingTemplateNotes + ", nonOptimalRateNotes="
				+ nonOptimalRateNotes + ", failReasons=" + failReasons
				+ ", listOfLenderNotes=" + listOfLenderNotes
				+ ", hasEmployedIncome=" + hasEmployedIncome
				+ ", totalDealPropertiesCount=" + totalDealPropertiesCount
				+ ", beaconScoreToUse=" + beaconScoreToUse
				+ ", latePaymentsToUse=" + latePaymentsToUse
				+ ", statedIncomeDeal=" + statedIncomeDeal
				+ ", mortgageDescription=" + mortgageDescription
				+ ", insuredThresholdForProperty="
				+ insuredThresholdForProperty
				+ ", requiredNonInsureDownpayment="
				+ requiredNonInsureDownpayment
				+ ", totalDealLiabilityPayments=" + totalDealLiabilityPayments
				+ ", totalDealLiabilities=" + totalDealLiabilities
				+ ", totalOpportunityLiabilities="
				+ totalOpportunityLiabilities + ", mobileProviders="
				+ mobileProviders + ", totalDebtRepaid=" + totalDebtRepaid
				+ ", insureAmountMaxMortgage=" + insureAmountMaxMortgage
				+ ", insureAmountMaxMortgagePlusDebtReduce="
				+ insureAmountMaxMortgagePlusDebtReduce
				+ ", maximumMortgageNoCondoPlusDebtReduce="
				+ maximumMortgageNoCondoPlusDebtReduce
				+ ", insureAmountMaxMortgageWithCondo="
				+ insureAmountMaxMortgageWithCondo
				+ ", insureAmountMaxMortgageWithCondoPlusDebtReduce="
				+ insureAmountMaxMortgageWithCondoPlusDebtReduce
				+ ", maxMortgageWithCondoPlusDebtReduce="
				+ maxMortgageWithCondoPlusDebtReduce
				+ ", downPaymentWithDebtReduce=" + downPaymentWithDebtReduce
				+ ", maxMortgageLTVWithCondo=" + maxMortgageLTVWithCondo
				+ ", nonTraditionalDownPayment=" + nonTraditionalDownPayment
				+ ", amortization=" + amortization + ", masterLiabilitiesList="
				+ masterLiabilitiesList + ", addedLiabilities="
				+ addedLiabilities + "]";
	}
	
}