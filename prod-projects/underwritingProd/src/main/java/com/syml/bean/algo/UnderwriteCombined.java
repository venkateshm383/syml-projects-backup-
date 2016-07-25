package com.syml.bean.algo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.syml.bean.algo.UnderwritingBase.TypeOfUnderwriting;
import com.syml.domain.Applicant;
import com.syml.domain.Income;
import com.syml.domain.Lender;
import com.syml.domain.Liability;
import com.syml.domain.Mortgage;
import com.syml.domain.Product;
import com.syml.domain.Property;
import com.syml.service.CrudServices;
import com.syml.util.SelectionHelper;

public class UnderwriteCombined extends UnderwritingBase{
	
	@Override
	public String toString() {
		return "UnderwriteCombined [baseLTV=" + baseLTV + ", addLTV=" + addLTV
				+ ", additionalProduct=" + additionalProduct
				+ ", propertyTaxTotal=" + propertyTaxTotal
				+ ", condoFeesTotal=" + condoFeesTotal + ", heatCostsToUse="
				+ heatCostsToUse + ", condoFeesToUse=" + condoFeesToUse
				+ ", propertyTaxesToUse=" + propertyTaxesToUse
				+ ", baseProductMortgageAmount=" + baseProductMortgageAmount
				+ ", additionalProductMortgageAmount="
				+ additionalProductMortgageAmount + ", baseProductPayment="
				+ baseProductPayment + ", additionalProductPayment="
				+ additionalProductPayment + "]";
	}

	public double baseLTV;
    public double addLTV;
    public Product additionalProduct;
	
	public double propertyTaxTotal = 0;
	public double condoFeesTotal = 0;
	public double heatCostsToUse = 0;
	public double condoFeesToUse = 0;
	public double propertyTaxesToUse;
	public double baseProductMortgageAmount = 0;
	public double additionalProductMortgageAmount;
	public double baseProductPayment;
	public double additionalProductPayment;

	public UnderwriteCombined(AlgoOpportunity clientOpportunity1, Product product1, Product product2, double baseLTV, double addLTV, Session hsession)
    {
    	super(clientOpportunity1, product1, hsession);
    	this.hsession = hsession;
        this.clientOpportunity = clientOpportunity1.opp;
        this.potentialProduct = product1;
        this.algoClientOpportunity = clientOpportunity1;
        AlgoProduct algoProduct = new AlgoProduct(product1);
        this.algoPotentialProduct = algoProduct;
        this.baseLTV = baseLTV;
        this.addLTV = addLTV;
        this.additionalProduct = product2;
        
        interestRate = ((product1.interestRate * baseLTV) +  (product2.interestRate * addLTV)) / (baseLTV + addLTV);
        avgProcessingSpeed = ((product1.avgProcessingSpeed * baseLTV) +  (product2.avgProcessingSpeed * addLTV)) / (baseLTV + addLTV);
        businessEase = ((product1.businessEase * baseLTV) +  (product2.businessEase * addLTV)) / (baseLTV + addLTV);
        underwriterPref = ((product1.underwriterPref * baseLTV) +  (product2.underwriterPref * addLTV)) / (baseLTV + addLTV);

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
        if (potentialProduct.interestRate < 1 || additionalProduct.interestRate < 1)
			System.out.println("Rate Issue");
        
        checkUnderwritingRules();
    }
	
	@Override
	public void checkUnderwritingRules() {
		
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
	
			boolean applicantIsImmigrant = false;
			Date applicantImmigrationDate = new Date();
			double MaxEmployedMonths = 0;
	
			double beaconScoreToUse = 0;
	
			totalDealCollections = 0;
			boolean  refinancePropertyMatched = false;
			
			
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
//						for (Liability liability : addedLiabilities) {
//							System.out.println(liability.toString());	
//						}							

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
			expectedMortgageAmount = calcCombinedMortgageAmount(valueOfProperty);
			
			double preInsuranceMortgageAmount = expectedMortgageAmount;

			double loanToValue = (preInsuranceMortgageAmount / valueOfProperty) * 100;
			clientOpportunity.ltv = loanToValue;   
			LTV = loanToValue;
			algoClientOpportunity.highRatio = checkIfHighRatio(valueOfProperty, expectedMortgageAmount);		
			
			boolean wfgDeal = false;
			if (clientOpportunity.wfgDeal != false)
				wfgDeal = clientOpportunity.wfgDeal;
			
			// Set Amortization to Determine payment input
			if (clientOpportunity.desiredAmortization != null){
				if (wfgDeal == true){
					if (LTV <= 80){
						amortization = 30;
					}
					else{
						amortization = Math.min(25, clientOpportunity.desiredAmortization);
					}
				}
				else{
					if (LTV > 80){
						amortization = Math.min(25, clientOpportunity.desiredAmortization);
					}
					else 
						amortization = clientOpportunity.desiredAmortization;
				}					
				
			}
			else{
				if (wfgDeal == true){
					if (LTV <= 80){
						amortization = 30;
					}
					else{
						amortization = Math.min(25, clientOpportunity.desiredAmortization);
					}
				}
				else{
					amortization = 25; // Maximum25 Years except on Conventional, can be 30 Years.
				}
			}
			
			//int amortization = 25; Was just used in temp when there was an issue with OpenERP Desired Amortization.
			if (amortization < 25){
				AlgoNote brokerNote1 = new AlgoNote(AlgoNote.TypeOfNote.BrokerAction, AlgoNote.Priority.Med, "Please Note that " + clientOpportunity.applicants.get(0).applicantName  + " has selected an amortization of " + clientOpportunity.amortization.toString() + ".  This will create a higher monthly payment amount. It is wise to provide the client with some education around this fact and to manage their expectations.");
				brokerNotes.add(brokerNote1);
			}
			
			// These  amounts will be used for determining total insurance amount ... Not an issue for Refinance, but for  high ratio - it is an issue
			// TODO It will need to be determined whether the Insurance should be "split" into each of the products Depending on "Ratio of product"
			mortgageAmount = expectedMortgageAmount;			
			double insuranceCost = calcInsuranceCost();
			expectedMortgageAmount = expectedMortgageAmount + insuranceCost;

			double productInterestRate1 = potentialProduct.interestRate; // Used on 5 Yr or Longer Terms or Actual Deals
			double qualifyingRate1 = potentialProduct.qualifyingRate; // Used on Prequalification for < 5 Year or for LOC or for Variable
			double productInterestRate2 = additionalProduct.interestRate; // Used on 5 Yr or Longer Terms or Actual Deals
			double qualifyingRate2 = additionalProduct.qualifyingRate; // Used on Prequalification for < 5 Year or for LOC or for Variable
			
			// In the event the Opportunity is a rental property, The expected rental income needs to be added to the total Income
			// Below method only runs on Product.AddToIncome
			totalDealIncome = addPropertyRentalIncome(totalDealIncome);

			// Below method only runs on Product.SubtractFromLiabilities
			double NetCashFlow = calcPropertyNetCashFlow(productInterestRate1, qualifyingRate1);	                
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
			
			// Set the Values needed to feed the Maximum Mortgage Methods
			double rateToUse = rateToUse(productInterestRate2, qualifyingRate2);
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

			additionalProductMortgageAmount = 0;
			
			// TODO Needs to be debugged and tested
			// TODO Also need to Include a "Custom Split" ... Consider how baseLTV is Set ... Maybe not needed here, but rather in constructor when instance created
			if (algoClientOpportunity.highRatio == true){
				baseProductMortgageAmount = Math.round((baseLTV / 100) * valueOfProperty);
				addLTV = LTV - baseLTV;
				additionalProductMortgageAmount = Math.round((addLTV / 100) * valueOfProperty);
			}
			else{
				// Need to insert Logic to determine these mortgage amounts if the desired Mortgage Amount is < 80% LTV ... 
				// base 15  500000 value, desired amount 300,000 ... base = 75,000
				baseProductMortgageAmount = Math.round((baseLTV / 100) * valueOfProperty);
				addLTV = LTV - baseLTV; // addLTV = 60 - 15  (45)
				additionalProductMortgageAmount = Math.round((addLTV / 100) * valueOfProperty); // =.45 * 500000 = 225,000 
				// Does the above work for Desired LTV 80?  (Yes it does - example below.  
				// base 15  500000 value, desired amount 400,000 (80% LTV) ... base = 75,000
				// addLTV = 80 - 15  (65)
				// =.65 * 500000 = 325,000 
				// End result is the same as highRatio, but for the moment, leave in high ration in case insurance is dealt with differently 
				// e.g. (only on 2nd portion in case of Spit Product)
			}
			
			// Determine what the loan amount is going to be (Either Desired or Max).  Max is used in Both Pre-Qualify and Refiance
			// Max is the smallest of the above Maximum amount Calculations, GDS, TDS, LTV
			double combinedPayment4Qualify = 0;
			double product1Cost = 0;
			if (SelectionHelper.compareSelection(AlgoProduct.MortgageType.LOC, potentialProduct.mortgageType))
				product1Cost = CalculatePayments.calculateMonthlyLOCPayment(qualifyingRate1, baseProductMortgageAmount);  
			else
				product1Cost = calculateMortgagePayment(productInterestRate1, qualifyingRate1, amortization,baseProductMortgageAmount);
			
			double product2Cost = calculateMortgagePayment(productInterestRate2, qualifyingRate2, amortization, additionalProductMortgageAmount);  
			combinedPayment4Qualify = product1Cost + product2Cost;
			
			if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Refinance, clientOpportunity.whatIsYourLendingGoal)){
				//clientOpportunity.maximumAmount = false; // TODO For testing
				if (clientOpportunity.maximumAmount == true){ 
					double maxMortgageAllowed = Math.min(clientOpportunity.desiredMortgageAmount,clientOpportunity.currentMortgageAmount);
					totalDealMortgagesBalance = totalDealMortgagesBalance + maxMortgageAllowed - clientOpportunity.currentMortgageAmount;
					totalDealPropertyValues = totalDealPropertyValues;
					// expectedMortgageAmount = maxMortgageAllowed;  Was this ... Changed for Kevin because bad calc on Line 504 - 522
					expectedMortgageAmount = maxMortgageAllowed; // Note that this does not Currently Include Insurance ... 
					costToUse = combinedPayment4Qualify;                  	
					//totalDealMortgagesPayments = totalDealMortgagesPayments + costToUse;
					clientOpportunity.totalMortgageAmount = expectedMortgageAmount;
					AlgoNote assistNote2 = new AlgoNote(AlgoNote.TypeOfNote.Info, AlgoNote.Priority.High, "The Maximum Mortgage amount allowable is: " + expectedMortgageAmount);
					assistantNotes.add(assistNote2);
				}
			}
			else{
				double maxMortgageAllowed = clientOpportunity.desiredMortgageAmount;
				totalDealMortgagesBalance = totalDealMortgagesBalance + maxMortgageAllowed;
				totalDealPropertyValues = totalDealPropertyValues + valueOfProperty;
				expectedMortgageAmount = maxMortgageAllowed;   
				costToUse = combinedPayment4Qualify;                	
				//totalDealMortgagesPayments = totalDealMortgagesPayments + costToUse;
				clientOpportunity.totalMortgageAmount = expectedMortgageAmount;
				AlgoNote assistNote2 = new AlgoNote(AlgoNote.TypeOfNote.Info, AlgoNote.Priority.High, "The Maximum Mortgage amount allowable is: " + expectedMortgageAmount);
				assistantNotes.add(assistNote2);
			}
			
			double product1FitnessPayment = 0;
			if (SelectionHelper.compareSelection(AlgoProduct.MortgageType.LOC, potentialProduct.mortgageType))
				product1FitnessPayment = CalculatePayments.calculateMonthlyLOCPayment(productInterestRate1, baseProductMortgageAmount);
			else
				product1FitnessPayment = CalculatePayments.calculateMortgagePayment(productInterestRate1, baseProductMortgageAmount, amortization, AlgoOpportunity.FrequencyDesired.Monthly);
			
			double product2FitnessPayment = CalculatePayments.calculateMortgagePayment(productInterestRate2, additionalProductMortgageAmount, amortization, AlgoOpportunity.FrequencyDesired.Monthly);
			expectedFitnessPayment = product1FitnessPayment + product2FitnessPayment;
			
			baseProductPayment = product1FitnessPayment;
			additionalProductPayment = product2FitnessPayment;
			
			oneTimeFees = CalculateCombinedOneTimeFees(baseProductMortgageAmount, additionalProductMortgageAmount);
			// Below Section for Debugging to ensure Mortgage Amounts are correct ... High Ratio with insurance probably is not.
//			if (insuranceAmount > 0)
//				System.out.println("Insurance amount: " + insuranceAmount);
			
//			double mortgageAmountWithNoIns2 = expectedMortgageAmount - insuranceAmount;
//	    	double equityOrDownpayment2 = clientOpportunity.downpaymentAmount;
//	    	double projectedPropertyValue2 = mortgageAmountWithNoIns + clientOpportunity.downpaymentAmount;	
//	    	
//	    	if (Math.round(mortgageAmountWithNoIns2) != Math.round(mortgageAmountWithNoIns)){
//	    		System.out.println("mortgageAmountWithNoIns: " + mortgageAmountWithNoIns + ", " + mortgageAmountWithNoIns2);
//		    	System.out.println("equityOrDownpayment: " + equityOrDownpayment + ", " + equityOrDownpayment2);
//		    	System.out.println("projectedPropertyValue: " + projectedPropertyValue + ", " + projectedPropertyValue2);			    		
//	    	}
			
			// TODO next ... GDS and TDS ... :)  
			// Calculate GDS and TDS 
					
			anticipatedMortgagePayment = combinedPayment4Qualify; 
			totalDealGDSAmount = (anticipatedMortgagePayment * 12) + heatCostsToUse + propertyTaxesToUse + condoFeesToUse;
			//totalDealGDSAmount = (anticipatedMortgagePayment * 12) + annualHeatCost + clientOpportunity.propertyTaxes + (clientOpportunity.condoFees * 12);
			double OpportunityGDSRatio = totalDealGDSAmount / totalDealIncome * 100;         
			
			opportunityTDSAmount = 0;
			if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Refinance, clientOpportunity.whatIsYourLendingGoal)){ 					
				// if refinance included in properties list this formula is true, If not, it is creating negative mortgage payment
				if (refinancePropertyMatched == false){
					opportunityTDSAmount = (anticipatedMortgagePayment * 12) + heatCostsToUse + propertyTaxesToUse + condoFeesToUse + (mortgagePaymentsToUse * 12) + liabilitiesToUse;
				}
				else{
					opportunityTDSAmount = (anticipatedMortgagePayment * 12) + heatCostsToUse + propertyTaxesToUse + condoFeesToUse + ((mortgagePaymentsToUse - clientOpportunity.currentMonthlyPayment) * 12) + liabilitiesToUse;	
				}
				
			}
			else{
				opportunityTDSAmount = (anticipatedMortgagePayment * 12) + (mortgagePaymentsToUse * 12) + heatCostsToUse + propertyTaxesToUse + condoFeesToUse + liabilitiesToUse;
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
					
					double product1Cost2Qualify = 0;
					if (SelectionHelper.compareSelection(AlgoProduct.MortgageType.LOC, potentialProduct.mortgageType))
						product1Cost2Qualify = CalculatePayments.calculateMonthlyLOCPayment(qualifyingRate1, baseProductMortgageAmount); 
					else
						product1Cost2Qualify = calculateMortgagePayment(productInterestRate1, qualifyingRate1, amortization,baseProductMortgageAmount);
					
					double product2Cost2Qualify = calculateMortgagePayment(productInterestRate2, qualifyingRate2, amortization, additionalProductMortgageAmount);  
					double combinedPayment2 = product1Cost2Qualify + product2Cost2Qualify;
					
					double anticipatedMortgagePayment2 = combinedPayment2; 
					double totalDealGDSAmount2 = (anticipatedMortgagePayment2 * 12) + annualHeatCost + clientOpportunity.propertyTaxes + (clientOpportunity.condoFees * 12);
					double OpportunityGDSRatio2 = totalDealGDSAmount2 / totalDealIncome * 100;              

					double OpportunityTDSAmount2 = 0;
					if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Refinance, clientOpportunity.whatIsYourLendingGoal)){
						if (refinancePropertyMatched == false){
							OpportunityTDSAmount2 = (anticipatedMortgagePayment2 * 12) + heatCostsToUse + propertyTaxesToUse + condoFeesToUse + (mortgagePaymentsToUse  * 12) + liabilitiesToUse;
						}
						else{
							OpportunityTDSAmount2 = (anticipatedMortgagePayment2 * 12) + heatCostsToUse + propertyTaxesToUse + condoFeesToUse + ((mortgagePaymentsToUse - clientOpportunity.currentMonthlyPayment) * 12) + liabilitiesToUse;	
						}
						
					}
					else{
						OpportunityTDSAmount2 = (anticipatedMortgagePayment2 * 12) + heatCostsToUse + propertyTaxesToUse + condoFeesToUse + (mortgagePaymentsToUse * 12) + liabilitiesToUse;
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
						product1FitnessPayment2 = CalculatePayments.calculateMonthlyLOCPayment(productInterestRate1, baseProductMortgageAmount);
					else
						product1FitnessPayment2 = CalculatePayments.calculateMortgagePayment(productInterestRate1, baseProductMortgageAmount, amortization, AlgoOpportunity.FrequencyDesired.Monthly);
					
					double product2FitnessPayment2 = CalculatePayments.calculateMortgagePayment(productInterestRate2, additionalProductMortgageAmount, amortization, AlgoOpportunity.FrequencyDesired.Monthly);
					expectedFitnessPayment = product1FitnessPayment2 + product2FitnessPayment2;
					
					baseProductPayment = product1FitnessPayment2;
					additionalProductPayment = product2FitnessPayment2;
					
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
			
			// These criteria are not for pre-qualification as they all involve the property All these additional criteria may not be needed ... Separate into those which are and those which are not ...
			// TODO Custom Methods to deal with the double product factor ... ... For Combined Products, probably not required.  For Mixed Lenders, probably is required
			checkPropertyCriteria(totalDealLiabilities, totalDealMortgagesBalance,totalDealCollections, valueOfProperty, beaconScoreToUse);
		
			// These are criteria that apply to all Deals, both Prequalification and non-prequalification
			// TODO Custom Methods to deal with the double product factor ... For Combined Products, probably not required.  For Mixed Lenders, probably is required
			checkMiscDealCriteria(totalDealCollections, applicantIsImmigrant,applicantImmigrationDate, MaxEmployedMonths, beaconScoreToUse);	

			// Please note, for the moment, because the above 2 methods do not get called for each combined, product, the  
			// checkOpportunityQualifies() method is being used rather than checkCombinedOpportunityQualifies()
			checkOpportunityQualifies();        
						
			try {
				CalculateCombinedOneTimeFees(baseProductMortgageAmount, additionalProductMortgageAmount);
				CalculateCombinedPayoutPenalty(baseProductMortgageAmount, additionalProductMortgageAmount);
				CalculateCombinedPaymentOptionsAttraction();
				CalculateCombinedBranchAttend();
				CalculateCombinedApplicationEase();
				CalculateBaseCompAmount(baseProductMortgageAmount, additionalProductMortgageAmount);
				double ytdVolume = 1000000.00; // TODO simply a place holder for the moment.  Needs to become a query of all paid deals by lender
				CalculateBonusCompAmount(baseProductMortgageAmount, additionalProductMortgageAmount, ytdVolume);
				CalculateMarketingCompAmount(baseProductMortgageAmount, additionalProductMortgageAmount);
				CalculateTrailerCompAmount(baseProductMortgageAmount, additionalProductMortgageAmount);
				CalculateTotalCompAmount();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if(expectedMortgageAmount < 20000)
				System.out.println("Challenge with Mortgage Amount");

			//						if (opportunityQualifies == true){				  
			//							System.out.println(potentialProduct.name.toString() + " qualitifed with " + countOfUnacceptableCriteria);
			//						}

			//System.out.println("AllProductAlgo end");
			String QualifyingInfo = this.potentialProduct.name 
					+ ", opportunityQualifies: " + opportunityQualifies
					+ ", GDS: " + GDSRatio + ", GdsAmount: " + totalDealGDSAmount 
					+ ", TDS: " + TDSRatio + ", TdsAmount: " + opportunityTDSAmount
					+ ", FailGDS: " + failedGDS + ", FailTDS: " + failedTDS 
					+ ", Income: " + totalDealIncome + ", Payment: " + anticipatedMortgagePayment + ", annualHeatCost: " + annualHeatCost 
					+ ", propertyTaxes: " + clientOpportunity.propertyTaxes + ", condoFees: " + clientOpportunity.condoFees
					+ ", Insurance: " + insuranceAmount; 
		//	System.out.println(QualifyingInfo);

			StringBuilder failurestring = new StringBuilder();
			for (String reason : failReasons) {
				failurestring.append(reason + ", ");
				//System.out.println( "Failure Reasons:" + failurestring);
			}
						
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
    
    public double CalculateCombinedOneTimeFees(double baseProductMortgageAmount, double additionalProductMortgageAmount)
    {
        double lenderFee1 = (baseProductMortgageAmount * potentialProduct.lenderFeesPercent / 100) + potentialProduct.lenderFeesFlat; 
        double brokerFee1 = (baseProductMortgageAmount * potentialProduct.brokerFeesPercent / 100) + potentialProduct.brokerFeesFlat;
        double lenderFee2 = (additionalProductMortgageAmount * additionalProduct.lenderFeesPercent / 100) + additionalProduct.lenderFeesFlat; 
        double brokerFee2 = (additionalProductMortgageAmount * potentialProduct.brokerFeesPercent / 100) + potentialProduct.brokerFeesFlat;
        lenderFee = (lenderFee1 + lenderFee2) / 2;
        brokerFee = (brokerFee1 + brokerFee2) / 2;
        oneTimeFees = lenderFee + brokerFee;
        
        return oneTimeFees;
    }
    
    public void CalculateCombinedPayoutPenalty(double baseProductMortgageAmount, double additionalProductMortgageAmount)
    {
       
        double payoutAttractiveness1 = 0;
        if (SelectionHelper.compareSelection(AlgoProduct.MortgageType.LOC, potentialProduct.mortgageType)){
        	payoutAttractiveness1 += 10;
    	}
    	else if (SelectionHelper.compareSelection(AlgoProduct.MortgageType.Variable, potentialProduct.mortgageType)){
    		payoutAttractiveness1 +=5;
    	}
    	else if (SelectionHelper.compareSelection(AlgoProduct.MortgageType.Fixed, potentialProduct.mortgageType)){
    		payoutAttractiveness1 += 2;
    	}
        if (potentialProduct.isPenaltyGreater != null && potentialProduct.isPenaltyGreater==false)
            payoutAttractiveness1 += 10;
        if (potentialProduct.waivePenaltyIfRetain && potentialProduct.waivePenaltyIfRetain == true)
            payoutAttractiveness1 += 5;

        payoutAttractiveness1 += (6 - potentialProduct.monthsInterestPenalty);
        // This method needs an overhaul with Kendall ... TODO How are we going to manage this portion of the algorithm 
        double payoutAttractiveness2 = 0;
        if (SelectionHelper.compareSelection(AlgoProduct.MortgageType.LOC, additionalProduct.mortgageType)){
        	payoutAttractiveness1 += 10;
    	}
    	else if (SelectionHelper.compareSelection(AlgoProduct.MortgageType.Variable, additionalProduct.mortgageType)){
    		payoutAttractiveness1 +=5;
    	}
    	else if (SelectionHelper.compareSelection(AlgoProduct.MortgageType.Fixed, additionalProduct.mortgageType)){
    		payoutAttractiveness1 += 2;
    	}
        
        if (additionalProduct.isPenaltyGreater != null && additionalProduct.isPenaltyGreater==false)
            payoutAttractiveness2 += 10;
        if (additionalProduct.waivePenaltyIfRetain && additionalProduct.waivePenaltyIfRetain == true)
            payoutAttractiveness2 += 5;

        payoutAttractiveness2 += (6 - potentialProduct.monthsInterestPenalty);
        
        // This is actuall not calculating the penalty ... It is calculating the attractiveness of a mortgage based on its
        // like payout penalty at some point in future
        // Now "weight" the payout Attractiveness of each.
        payoutAmount = ((payoutAttractiveness1 * baseLTV) +  (payoutAttractiveness2 * addLTV)) / (baseLTV + addLTV);
        
    }
	
    public void CalculateCombinedPaymentOptionsAttraction()
    {
        double totalPaymentOptionValue1 = 0;
        // Need to set the "value" of each payment option with Kendall during Code Review
        if (potentialProduct.weeklyPayments == true)
            totalPaymentOptionValue1 += 1;
        if (potentialProduct.biWeeklyPayments == true)
            totalPaymentOptionValue1 += 1;
        if (potentialProduct.semiMonthlyPayments == true)
            totalPaymentOptionValue1 += 1;
        if (potentialProduct.monthlyPayments == true)
            totalPaymentOptionValue1 += 1;
        if (potentialProduct.doubleUpPayments == true)
            totalPaymentOptionValue1 += 1;
        if (potentialProduct.increasePayments == true)
            totalPaymentOptionValue1 += 1;
        if (potentialProduct.extraAnnualPaydown == true)
            totalPaymentOptionValue1 += 1;
        if (potentialProduct.extraAppliedAnyTime == true)
            totalPaymentOptionValue1 += 1;
        if (potentialProduct.rewardPoints == true)
            totalPaymentOptionValue1 += 1;
        if (potentialProduct.skipPaymentPossible == true)
            totalPaymentOptionValue1 += 1;
        if (potentialProduct.prepayToReduce == true)
            totalPaymentOptionValue1 += 1;
        if (potentialProduct.allowTitleIns == true) // Provide a bit of extra credit if they allow Title Insurance
        	totalPaymentOptionValue1 += 4;
        
        double totalPaymentOptionValue2 = 0;
        // Need to set the "value" of each payment option with Kendall during Code Review
        if (additionalProduct.weeklyPayments == true)
            totalPaymentOptionValue2 += 1;
        if (additionalProduct.biWeeklyPayments == true)
            totalPaymentOptionValue2 += 1;
        if (additionalProduct.semiMonthlyPayments == true)
            totalPaymentOptionValue2 += 1;
        if (additionalProduct.monthlyPayments == true)
            totalPaymentOptionValue2 += 1;
        if (additionalProduct.doubleUpPayments == true)
            totalPaymentOptionValue2 += 1;
        if (additionalProduct.increasePayments == true)
            totalPaymentOptionValue2 += 1;
        if (additionalProduct.extraAnnualPaydown == true)
            totalPaymentOptionValue2 += 1;
        if (additionalProduct.extraAppliedAnyTime == true)
            totalPaymentOptionValue2 += 1;
        if (additionalProduct.rewardPoints == true)
            totalPaymentOptionValue2 += 1;
        if (additionalProduct.skipPaymentPossible == true)
            totalPaymentOptionValue2 += 1;
        if (additionalProduct.prepayToReduce == true)
            totalPaymentOptionValue2 += 1;
        if (additionalProduct.allowTitleIns == true) // Provide a bit of extra credit if they allow Title Insurance
        	totalPaymentOptionValue2 += 4;
        
        paymentOptions = ((totalPaymentOptionValue1 * baseLTV) +  (totalPaymentOptionValue2 * addLTV)) / (baseLTV + addLTV);
    }
    
    public void CalculateCombinedBranchAttend()
    {
        // In this variable, a Lender with Branch signing is less attractive than one where there is no branch signing.
        // Thus, to keep fitness formula consistent, true Branch signing has been assigned a low number and false has been assigned
        // a much much higher number in order to create a large variance in the fitness formula.
    	double branchAttend1 = 0;
    	double branchAttend2 = 0;
    	
        if (potentialProduct.branchSigning == true)
            branchAttend1 = 1;
        else
            branchAttend1 = 4; // ensure that this feeds fitness to bias companies that are not Branch attend    
        
        if (additionalProduct.branchSigning == true)
            branchAttend2 = 1;
        else
            branchAttend2 = 4; // ensure that this feeds fitness to bias companies that are not Branch attend      
        
        branchAttend = ((branchAttend1 * baseLTV) +  (branchAttend2 * addLTV)) / (baseLTV + addLTV);
    }
    
    public void CalculateCombinedApplicationEase()
    {
    	double applicationEase1 = 0;
    	double applicationEase2 = 0;
    	
        if (SelectionHelper.compareSelection(AlgoProduct.ApplicationMethod.Filelogix, potentialProduct.applicationMethod))
            applicationEase1 = 10;
        else
            applicationEase1 = 1;       
        
        if (SelectionHelper.compareSelection(AlgoProduct.ApplicationMethod.Filelogix, additionalProduct.applicationMethod))
            applicationEase2 = 10;
        else
            applicationEase2 = 1;    
        
        applicationEase = ((applicationEase1 * baseLTV) +  (applicationEase2 * addLTV)) / (baseLTV + addLTV);
    }
    
    public void CalculateBaseCompAmount(double baseProductMortgageAmount, double additionalProductMortgageAmount)
    {
    	double baseCompAmount1 = (potentialProduct.baseCommission / 10000) * baseProductMortgageAmount;  
    	double baseCompAmount2 = (additionalProduct.baseCommission / 10000) * additionalProductMortgageAmount;  
    	
    	baseCompAmount = baseCompAmount1 + baseCompAmount2;  
    }
    
    public void CalculateBonusCompAmount(double baseProductMortgageAmount, double additionalProductMortgageAmount, double ytdVolume)
    {
    	double volumeBonusCommissionRate1 = 0;
    	double volumeBonusCommissionRate2 = 0;
    	
    	// These are not else if statements in that we want the "Max Applicable" rate and many lenders have only 1 threshold, while others may have 4 or 5
	    if (ytdVolume < potentialProduct.volBonus1Threshold)
	        volumeBonusCommissionRate1 = 1;
	    if (ytdVolume >= potentialProduct.volBonus1Threshold)
	        volumeBonusCommissionRate1 = potentialProduct.volBonus1;
	    if (ytdVolume >= potentialProduct.volBonus2Threshold)
	        volumeBonusCommissionRate1 = potentialProduct.volBonus2;
	    if (ytdVolume >= potentialProduct.volBonus3Threshold)
	        volumeBonusCommissionRate1 = potentialProduct.volBonus3;
	    if (ytdVolume >= potentialProduct.volBonus4Threshold)
	        volumeBonusCommissionRate1 = potentialProduct.volBonus4;
	    if (ytdVolume >= potentialProduct.volBonus5Threshold)
	        volumeBonusCommissionRate1 = potentialProduct.volBonus5;
	    
	    if (ytdVolume < additionalProduct.volBonus1Threshold)
	        volumeBonusCommissionRate2 = 1;
	    if (ytdVolume >= additionalProduct.volBonus1Threshold)
	        volumeBonusCommissionRate2 = additionalProduct.volBonus1;
	    if (ytdVolume >= additionalProduct.volBonus2Threshold)
	        volumeBonusCommissionRate2 = additionalProduct.volBonus2;
	    if (ytdVolume >= additionalProduct.volBonus3Threshold)
	        volumeBonusCommissionRate2 = additionalProduct.volBonus3;
	    if (ytdVolume >= additionalProduct.volBonus4Threshold)
	        volumeBonusCommissionRate2 = additionalProduct.volBonus4;
	    if (ytdVolume >= additionalProduct.volBonus5Threshold)
	        volumeBonusCommissionRate2 = additionalProduct.volBonus5;
	    
	    double volumeBonusCompAmount1 = (volumeBonusCommissionRate1 / 10000) * baseProductMortgageAmount;
	    double volumeBonusCompAmount2 = (volumeBonusCommissionRate2 / 10000) * additionalProductMortgageAmount;
	    volumeBonusCompAmount = volumeBonusCompAmount1 + volumeBonusCompAmount2;
    }
    
    public void CalculateMarketingCompAmount(double baseProductMortgageAmount, double additionalProductMortgageAmount)
    {
    	double marketingCompAmount1 = (potentialProduct.marketingCommission / 10000) * baseProductMortgageAmount;  
    	double marketingCompAmount2 = (additionalProduct.marketingCommission / 10000) * additionalProductMortgageAmount;  
    	
    	marketingCompAmount = marketingCompAmount1 + marketingCompAmount2;  
    }
    
    public void CalculateTrailerCompAmount(double baseProductMortgageAmount, double additionalProductMortgageAmount)
    {
    	double trailerCompAmount1 = 0;  
    	double trailerCompAmount2 = (additionalProduct.marketingCommission / 10000) * additionalProductMortgageAmount;  
    	
    	// trailerCompAmount1
    	if (potentialProduct.trailerCommission > 0){
    		if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year2, potentialProduct.term))
    		{
    			trailerCompAmount1 = ((potentialProduct.trailerCommission / 10000) * baseProductMortgageAmount) * 2;    
    		}
    		else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year3, potentialProduct.term))
    		{
    			trailerCompAmount1 = ((potentialProduct.trailerCommission / 10000) * baseProductMortgageAmount) * 3;
    		}
    		else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year4, potentialProduct.term))
    		{
    			trailerCompAmount1 = ((potentialProduct.trailerCommission / 10000) * baseProductMortgageAmount) * 4;
    		}
    		else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year5, potentialProduct.term))
    		{
    			trailerCompAmount1 = ((potentialProduct.trailerCommission / 10000) * baseProductMortgageAmount) * 5;
    		}
    		else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year6, potentialProduct.term))
    		{
    			trailerCompAmount1 = ((potentialProduct.trailerCommission / 10000) * baseProductMortgageAmount) * 6;
    		}
    		else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year7, potentialProduct.term))
    		{
    			trailerCompAmount1 = ((potentialProduct.trailerCommission / 10000) * baseProductMortgageAmount) * 7;
    		}
    		else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year8, potentialProduct.term))
    		{
    			trailerCompAmount1 = ((potentialProduct.trailerCommission / 10000) * baseProductMortgageAmount) * 8;
    		}
    		else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year9, potentialProduct.term))
    		{
    			trailerCompAmount1 = ((potentialProduct.trailerCommission / 10000) * baseProductMortgageAmount) * 9;
    		}
    		else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year10, potentialProduct.term))
    		{
    			trailerCompAmount1 = ((potentialProduct.trailerCommission / 10000) * baseProductMortgageAmount) * 10;
    		}
    	}
    	else 
    		trailerCompAmount1 = 0;
    	
    	// trailerCompAmount2
    	if (additionalProduct.trailerCommission > 0){
    		if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year2, additionalProduct.term))
    		{
    			trailerCompAmount2 = ((additionalProduct.trailerCommission / 10000) * baseProductMortgageAmount) * 2;    
    		}
    		else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year3, additionalProduct.term))
    		{
    			trailerCompAmount2 = ((additionalProduct.trailerCommission / 10000) * baseProductMortgageAmount) * 3;
    		}
    		else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year4, additionalProduct.term))
    		{
    			trailerCompAmount2 = ((additionalProduct.trailerCommission / 10000) * baseProductMortgageAmount) * 4;
    		}
    		else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year5, additionalProduct.term))
    		{
    			trailerCompAmount2 = ((additionalProduct.trailerCommission / 10000) * baseProductMortgageAmount) * 5;
    		}
    		else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year6, additionalProduct.term))
    		{
    			trailerCompAmount2 = ((additionalProduct.trailerCommission / 10000) * baseProductMortgageAmount) * 6;
    		}
    		else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year7, additionalProduct.term))
    		{
    			trailerCompAmount2 = ((additionalProduct.trailerCommission / 10000) * baseProductMortgageAmount) * 7;
    		}
    		else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year8, additionalProduct.term))
    		{
    			trailerCompAmount2 = ((additionalProduct.trailerCommission / 10000) * baseProductMortgageAmount) * 8;
    		}
    		else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year9, additionalProduct.term))
    		{
    			trailerCompAmount2 = ((additionalProduct.trailerCommission / 10000) * baseProductMortgageAmount) * 9;
    		}
    		else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year10, additionalProduct.term))
    		{
    			trailerCompAmount2 = ((additionalProduct.trailerCommission / 10000) * baseProductMortgageAmount) * 10;
    		}
    	}
    	else 
    		trailerCompAmount2 = 0;
    	
    	
    	trailerCompAmount = trailerCompAmount1 + trailerCompAmount2;  
    }
    
    public void CalculateTotalCompAmount(){
    	totalCompAmount = baseCompAmount + volumeBonusCompAmount + marketingCompAmount + trailerCompAmount;
    }
    
    public void checkCombinedOpportunityQualifies() {
		opportunityQualifies = true;
		if (OpportunityCreditCategory.equals(AlgoApplicant.CreditCategory.Good))
		{
		    if (countOfUnacceptableCriteria > potentialProduct.allowedRedFlagsGood + additionalProduct.allowedRedFlagsGood)  
		        opportunityQualifies = false;
		    else if (countOfQuestionableCriteria > potentialProduct.allowedGreyFlagsGood + additionalProduct.allowedGreyFlagsGood)  
		        opportunityQualifies = false; 
		}
		else if (OpportunityCreditCategory.compareTo(AlgoApplicant.CreditCategory.Medium) == 0)
		{
			if (countOfUnacceptableCriteria > potentialProduct.allowedRedFlagsMed + additionalProduct.allowedRedFlagsMed)  
		        opportunityQualifies = false;
		    else if (countOfQuestionableCriteria > potentialProduct.allowedGreyFlagsMed + additionalProduct.allowedRedFlagsMed)  
		        opportunityQualifies = false;
		}
		else if (OpportunityCreditCategory.equals(AlgoApplicant.CreditCategory.Poor))
		{
			if (countOfUnacceptableCriteria > potentialProduct.allowedRedFlagsPoor + additionalProduct.allowedRedFlagsMed)  
		        opportunityQualifies = false;
		    else if (countOfQuestionableCriteria > potentialProduct.allowedGreyFlagsPoor + additionalProduct.allowedRedFlagsMed)  
		        opportunityQualifies = false;
		}
		
		if (opportunityQualifies == false){
			StringBuilder failReasonsString = new StringBuilder();
			for (String failure : failReasons) {
				failReasonsString.append(failure + ", ");
			}

			//System.out.println(">>>>> " + this.potentialProduct.name + " failed: " + failReasonsString.toString());
		}
	}
    
    public double getCombinedFitness(double avgCost,double AvgOneTimeCost, double avgInterestRate, double avgRisk, double avgCashback,
            double avgPayoutPenalty, double avgPaymentOption, double avgBaseCompensation, double avgBonus, double avgMarketingComp,
            double avgTrailerComp, double avgSpeed, double avgBusinessEase, double avgBranchAttend, double avgUnderwriterPref, double lenderApplicationEase, double avgTotalCompensation)
        {
        	
//        	System.out.println("getFitness:" + avgCost + "," + AvgOneTimeCost+ "," + avgInterestRate+ "," + avgRisk+ "," + avgCashback+ "," + 
//        	        avgPayoutPenalty+ "," + avgPaymentOption+ "," + avgBaseCompensation+ "," + avgBonus+ "," + avgMarketingComp+ "," + 
//        	        avgTrailerComp+ "," + avgSpeed+ "," + avgBusinessEase+ "," + avgBranchAttend+ "," + avgUnderwriterPref+ "," + lenderApplicationEase+ "," + avgTotalCompensation);
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
            
            //System.out.println("Fitness: " + productFitness);        
            return productFitness;
        }
    
    private double calcCombinedMortgageAmount(double valueOfProperty) {
		expectedMortgageAmount = 0;
		baseExpectedMortgageAmount = 0;
		double MaxLTV = 0;
		if (clientOpportunity.isRentalProperty == true)
			MaxLTV = 0.8;
		// TODO How do I know what the maximum allowed LTV is? If Residence? If rental?  If Small Center?  If insured?  If uninsured? 
			
		// TODO This creates an issue with 2nd Mortgages ... Need to fix to allow combination of 1st and 2nd. 
		if (clientOpportunity.desiredMortgageAmount == 0){
			expectedMortgageAmount = valueOfProperty - clientOpportunity.downpaymentAmount;        			
		}        
		
		if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Refinance, clientOpportunity.whatIsYourLendingGoal)  ){
			// TODO Need to confirm this logic with Kendall's Test Opportunities. 
			double ltvMortgageAmount = valueOfProperty * ((double)80 / (double)100);             	
			
			// There was an issue with fields "additionalAmount" v.s. "currentBalance" ... 
			// Was double renoCurrentAndAdditionalAmount = clientOpportunity.currentMortgageAmount + clientOpportunity.additionalAmount + clientOpportunity.renovationValue;
			double renoCurrentAndAdditionalAmount = clientOpportunity.currentMortgageAmount + clientOpportunity.currentBalance + clientOpportunity.renovationValue;
			
			if (SelectionHelper.compareSelection(AlgoOpportunity.Charge.First, clientOpportunity.chargeOnTitle)){
				if (clientOpportunity.maximumAmount == true){
		    		// The Maximum formula is Temporary ... it will be revised after GDS is calculated.
		    		expectedMortgageAmount =  Math.max(ltvMortgageAmount, clientOpportunity.currentMortgageAmount);         		  
		    	}
		    	else{
		    		if (clientOpportunity.currentMortgageAmount < ltvMortgageAmount){
		    			// TODO Need to test this on a refinance ... 
		    			if (clientOpportunity.desiredMortgageAmount != 0){
		    				expectedMortgageAmount = Math.max(clientOpportunity.desiredMortgageAmount, clientOpportunity.currentMortgageAmount);        			
		    			} 
		    			else{
		    				expectedMortgageAmount =  Math.min(ltvMortgageAmount,renoCurrentAndAdditionalAmount - clientOpportunity.chargesBehindAmount);	
		    			}		    			
		    		}
		    		else{
		    			expectedMortgageAmount =  Math.max(ltvMortgageAmount,clientOpportunity.currentMortgageAmount);
		    		}
		    	}	
			}
			else{
				expectedMortgageAmount = clientOpportunity.desiredMortgageAmount;
			}
			
		}             
		else if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Approval, clientOpportunity.whatIsYourLendingGoal)){
			if (SelectionHelper.compareSelection(AlgoOpportunity.Charge.First, clientOpportunity.chargeOnTitle)){
				expectedMortgageAmount = valueOfProperty - clientOpportunity.downpaymentAmount - clientOpportunity.chargesBehindAmount;	
			}
			else {
				expectedMortgageAmount = clientOpportunity.desiredMortgageAmount;
			}
		}             
		else if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Prequalify, clientOpportunity.whatIsYourLendingGoal)){
			// expectedMortgageAmount = clientOpportunity.desiredMortgageAmount;  I decided better to subtract downpayment ...  
			expectedMortgageAmount = valueOfProperty - clientOpportunity.downpaymentAmount - clientOpportunity.chargesBehindAmount;
		}
		baseExpectedMortgageAmount = expectedMortgageAmount;
		return baseExpectedMortgageAmount;
	}
    
}
