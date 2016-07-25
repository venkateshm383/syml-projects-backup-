package com.syml.bean.algo;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Session;

import com.syml.domain.Applicant;
import com.syml.domain.Asset;
import com.syml.domain.BaseBean;
import com.syml.domain.Collection;
import com.syml.domain.Income;
import com.syml.domain.InsurerProducts;
import com.syml.domain.LatePayment;
import com.syml.domain.Lender;
import com.syml.domain.Liability;
import com.syml.domain.Mortgage;
import com.syml.domain.Opportunity;
import com.syml.domain.Product;
import com.syml.domain.Property;
import com.syml.domain.Task;
import com.syml.service.CrudServices;
import com.syml.util.SelectionHelper;

public class UnderwritingBase extends BaseBean {

	public enum TypeOfUnderwriting { DesiredProduct, AllProducts, FinalVerify, PostSelection }
    public TypeOfUnderwriting underwritingType;
 
    // Details
    public Product potentialProduct;
    public Opportunity clientOpportunity;
    
    public AlgoProduct algoPotentialProduct;
    public AlgoOpportunity algoClientOpportunity;
//    public AlgoProduct potentialProduct;
//    public AlgoOpportunity clientOpportunity;

    public int countOfAcceptableCritera;
    public int countOfUnacceptableCriteria;
    public int countOfQuestionableCriteria;

    public boolean hasSelfEmployedIncome;
    public double fitness;
    public double mortgageAmount;

    public int lenderFeesPercent; // Some Lenders will charge an additional fee on the deal. Usually this is with either private Mortgages or 2nd Mortgages. 
    public int lenderFeesFlat; // Some Lenders will charge an additional fee on the deal. Usually this is with either private Mortgages or 2nd Mortgages. 
    public int brokerFeesPercent; // Sometimes we as a Broker may charge an additional fee on the deal. Usually this is with either private Mortgages or 2nd Mortgages. (Only applies to certain Products / Lenders ... Ussually B-D Lenders)
    public int brokerFeesFlat; // Sometimes we as a Broker may charge an additional fee on the deal. Usually this is with either private Mortgages or 2nd Mortgages. (Only applies to certain Products / Lenders ... Ussually B-D Lenders) 
    public double lendableValue; // The Values of the Property, in some instances Lenders may not use entire value of Property

    public AlgoApplicant.CreditCategory OpportunityCreditCategory;

    public boolean opportunityQualifies; // There are no UnacceptableCriteria
    public int allowableGreyFlagGoodCredit; // Number of Grey Flags on the deal they will allow if the Credit is Good
    public int allowableGreyFlagMediumCredit;
    public int allowableGreyFlagPoorCredit;

    public int allowableBlackFlagGoodCredit;
    public int allowableBlackFlagMediumCredit;
    public int allowableBlackFlagPoorCredit;
    public int failedNotesCounter;
    public int assistantNotesCounter;
    
    public double totalDealGDSAmount;
	public double opportunityTDSAmount;
	public boolean failedGDS;
	public boolean failedTDS;
	public double totalDealIncome;
	public double anticipatedMortgagePayment;
	public double annualHeatCost;
	
    
    public double fixedCost;
    public double variableCost;
    public double LOCCost;
    public double costToUse;
    public double expectedFitnessPayment;
    
    public double interestRate;
    public double cashbackAmount;
    public double payoutAmount;
    public double paymentOptions;
    public double baseCompAmount;
    public double volumeBonusCompAmount;
    public double bonusCompAmount;
    public double marketingCompAmount;
    public double trailerCompAmount;
    public double lenderFee;
    public double brokerFee;
    
    public double branchAttend;
    public double applicationEase;
    public double avgProcessingSpeed;
    public double businessEase;
    public double underwriterPref;

    public double allApplicantsTotalIncomes;
    public double GDSRatio;
    public double TDSRatio;
    public double LTV;
    public double insuranceAmount;
    public double maximumMortgageNoCondo;
    public double maximumMortgageCondo;
    public double maximumMortgageNoCondoDebtRepay;
    public double maximumMortgageCondoDebtRepay;
    public double totalLiabilityPayments;
    public double expectedMortgageAmount;
    public double baseExpectedMortgageAmount;
    public double allApplicantsTotalAssets;
        
    // A few extra Compensation Related fields
    public double totalCompAmount;
    public double oneTimeFees;
    public List<String> allowedProvinces;
    public List<AlgoNote> assistantNotes;
    public List<AlgoNote> brokerNotes;
    public List<AlgoNote> lenderNotes;
    public List<AlgoNote> dealNotes;
    public List<AlgoNote> marketingTemplateNotes;
    public List<AlgoNote> nonOptimalRateNotes;
    public List<String> failReasons;
    public StringBuilder listOfLenderNotes;

    public Session hsession;

	public boolean hasEmployedIncome;
	public int totalDealPropertiesCount;
	protected double beaconScoreToUse;
	public double latePaymentsToUse;
	public boolean statedIncomeDeal;
	public String mortgageDescription;
	public double insuredThresholdForProperty;
	public double requiredNonInsureDownpayment;
	public double totalDealLiabilityPayments;
	public double totalDealLiabilities;

    public List<Liability> totalOpportunityLiabilities;
    public List<String> mobileProviders;

	public double totalDebtRepaid;
	public double insureAmountMaxMortgage;
	public double insureAmountMaxMortgagePlusDebtReduce;
	public double maximumMortgageNoCondoPlusDebtReduce;
	public double insureAmountMaxMortgageWithCondo;
	public double insureAmountMaxMortgageWithCondoPlusDebtReduce;
	public double maxMortgageWithCondoPlusDebtReduce;
	public double downPaymentWithDebtReduce;

	public double maxMortgageLTVWithCondo;
	public boolean nonTraditionalDownPayment;

	public int amortization;
	public ArrayList<Liability> masterLiabilitiesList;
	public ArrayList<Liability> addedLiabilities ;
	
	public UnderwritingBase() {
		super();
		this.failReasons = new ArrayList<String>();
		this.listOfLenderNotes = new StringBuilder();

	}

	public UnderwritingBase(Integer id) {
		super(id);
		this.failReasons = new ArrayList<String>();
		this.listOfLenderNotes = new StringBuilder();
	}

	public UnderwritingBase(AlgoOpportunity clientAlgoOpportunity,
			Product algoProduct, Session session) {
		super();
		hsession = session;
		this.failReasons = new ArrayList<String>();
		this.listOfLenderNotes = new StringBuilder();
	}
	
	public UnderwritingBase(AlgoOpportunity clientOpportunity1, AlgoProduct potentialProduct1, Session hsession){
		super();
		hsession = hsession;
		this.failReasons = new ArrayList<String>();
		this.listOfLenderNotes = new StringBuilder();
	}

	protected void checkUnderwritingRules() {
		
	}
		
	void addCellCompanies(){
    	mobileProviders.add("telus");
    	mobileProviders.add("rogers");
    	mobileProviders.add("bell"); //Fido
    	mobileProviders.add("fido");
    	mobileProviders.add("koodo");
    	mobileProviders.add("wind");
    }
	
	public void calcDealLiabilities(Applicant applicant){
		
		if (applicant.includeInOpportunity == true) // Need to ability in Opportunity to remove an applicant from the deal if credit is too damaged.
		{
			// Create Liability oriented tasks related to the Applicant
			double monthlyGDSLiabilities = 0;
			double totalLiabilities = 0;
			
			int countOfLiabilities = 0;
			if (applicant.liabilities != null && applicant.liabilities.size() > 0){
				for (Liability currentLiability : applicant.liabilities) {

					currentLiability.person = applicant.applicantName;
					currentLiability.paymentRatio = Double.parseDouble(currentLiability.monthlyPayment) / currentLiability.creditBalance;
					totalOpportunityLiabilities.add(currentLiability);

					// TODO What about closed Liabilities???  How is Status of liability determined?  May need real data here ... At a minimum, confer with Audrey
					// Check to see if liability is not closed.
					boolean liabilityOpen = checkLiabilityOpen(currentLiability);
					boolean checkMobileCompany = checkIfCellCompany(currentLiability);

					try {
						// Check if Fixed or Revolving or LOC
						if (liabilityOpen == true && checkMobileCompany == false){
							if (currentLiability.creditBalance > currentLiability.payOff){
								// We need to calculate whether a given Liability is an LOC.
								boolean probablyLOC = false;
								double locCutoff = currentLiability.creditBalance * 0.012;
								double currentPayment = Double.parseDouble(currentLiability.monthlyPayment);
								if (currentPayment > 0 && currentPayment <= locCutoff)
									probablyLOC = true;
								
								double revisedBalance = currentLiability.creditBalance - currentLiability.payOff;
								double calculateLiabilityPayment = 0;

								if (probablyLOC == true)
									calculateLiabilityPayment = Double.parseDouble(currentLiability.monthlyPayment);
								else
									calculateLiabilityPayment = calcLiabilityPayment(currentLiability,revisedBalance);
								
								if (revisedBalance > 0){
									
									boolean alreadyadded = isLiabilityAlreadyAdded(masterLiabilitiesList,currentLiability);

									if (alreadyadded==false){  
										masterLiabilitiesList.add(currentLiability);
										countOfLiabilities = countOfLiabilities + 1;
										//System.out.println(currentLiability.name + ", " + calculateLiabilityPayment + ", " + currentLiability.business + ", " + currentLiability.creditBalance + ", " + currentLiability.type);
										int MonthsRemainingInLoan = (int)Math.floor(currentLiability.creditBalance / calculateLiabilityPayment); // Months Remaining is Rounded Down
										//TimeSpan DaysToClose = clientOpportunity.ClosingDate - DateTime.Now;
										double MonthsToClose = monthsToClose();

										if (potentialProduct.removeLoansDonePreClose==true && potentialProduct.removeLoanMoRemaining != null){
											if (MonthsRemainingInLoan <= MonthsToClose + potentialProduct.removeLoanMoRemaining){
												totalLiabilities += currentLiability.creditBalance;											
											}
											else{
												monthlyGDSLiabilities += calculateLiabilityPayment;
												totalLiabilities += currentLiability.creditBalance;
												addedLiabilities.add(currentLiability);
											}
										}
										else{
											if (potentialProduct.removeLoanMoRemaining != null && MonthsRemainingInLoan <= potentialProduct.removeLoanMoRemaining){
												totalLiabilities += currentLiability.creditBalance;
											}
											else{
												monthlyGDSLiabilities += calculateLiabilityPayment;
												totalLiabilities += currentLiability.creditBalance;
												addedLiabilities.add(currentLiability);
											}
										}
									}    
								}

							}
							else {
								// Note may be required here during next iteration of Algorithm i.e. The Loan ____ is to be paid from Proceeds
							}	
						}
					} catch (Exception e) {
						StackTraceElement[] stack = e.getStackTrace();
					    String exception = "";
					    for (StackTraceElement s : stack) {
					        exception = exception + s.toString() + "\n\t\t";
					    }
					   // System.out.println(exception);
			            
						Calendar currentDate2 = new GregorianCalendar();
						Calendar deadline2 = new GregorianCalendar();
						deadline2.add(Calendar.HOUR, 1);
						e.printStackTrace();
						AlgoNote task2 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, 
								"There was an ERROR UnderwritingBase Class Line 273.  "
								+ "Liability Information has missing data", 
								"OpportunityID" + clientOpportunity.applicationNo + " - "  + e.getMessage() + exception);					  
						dealNotes.add(task2); 
					}
					
				}
			}
			
			// Monthly Child / Spousal Support Paid ....  
			if (applicant.monthlySupportPayment != null && applicant.monthlySupportPayment > 0)
			{	
				if (AlgoProduct.SupportTreatment.AddToLiabilities.equals(algoPotentialProduct.childSupportRreatment))
				{
					monthlyGDSLiabilities += applicant.monthlySupportPayment;
				}					
			}

			
			totalDealLiabilityPayments = totalDealLiabilityPayments + monthlyGDSLiabilities;
			totalDealLiabilities  = totalDealLiabilities + totalLiabilities;
			applicant.totalApplicantLiabilities = totalLiabilities;
			applicant.totalApplicantLiabilityPayments = monthlyGDSLiabilities;
			
		}
	}
	
	public void checkTaxCollections(Applicant applicant){
		try {
			boolean taxCollection = false;
			if (applicant.collection != null){
				for (Collection collection : applicant.collection) {
					if (collection.name != null && collection.balance != null && collection.status != null){
						if (collection.name.toLowerCase().contains("income tax")
								&& (collection.balance > 0 || collection.status == null)){
							taxCollection = true;
						}	
					}
				}	
			}
			
			if (taxCollection == true){
				countOfUnacceptableCriteria++;
				AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, 
						"Applicant " + applicant.applicantName + " has an outstanding collection to CCRA");
				dealNotes.add(note1);
				failReasons.add("Applicant " + applicant.applicantName + " has an outstanding collection to CCRA.");
				failedNotesCounter++;
			}
			else 
				countOfAcceptableCritera++;
			
		} catch (Exception e) {
			StackTraceElement[] stack = e.getStackTrace();
		    String exception = "";
		    for (StackTraceElement s : stack) {
		        exception = exception + s.toString() + "\n\t\t";
		    }
		    System.out.println(exception);
            
			Calendar currentDate2 = new GregorianCalendar();
			Calendar deadline2 = new GregorianCalendar();
			deadline2.add(Calendar.HOUR, 1);
			e.printStackTrace();
			AlgoNote task2 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, 
					"There was an ERROR UnderwritingBase Class Line 340.  Please copy-paste the text in the description of this task into an email and send it to techsupport@visdom.ca.", 
					"OpportunityID" + clientOpportunity.applicationNo + " - "  + e.getMessage() + exception);					  
			dealNotes.add(task2); 
		}	
	}

	protected void checkMiscDealCriteria(double totalDealCollections,
			boolean applicantIsImmigrant, Date applicantImmigrationDate,
			double MaxEmployedMonths, double beaconScoreToUse) {
		checkImmigrationCriteria(applicantIsImmigrant,applicantImmigrationDate, MaxEmployedMonths);
		checkProductUninsurable();
		checkAllowSelfEmployed();
		calcBusinessEaseFitness();
		checkNonResident();
		checkCanadianMilitary();
		checkPropertyLocation();
		checkMaximumApplicants();

		// TODO Check Maximum Amortization  Once Fixed in OpenERP, uncomment this ... 
		//checkMaxAmortization();
		checkDealCollections(totalDealCollections);
		checkBeaconScore(beaconScoreToUse);
		checkAllowedProvince();
		checkGift();
		checkBorrowedDownpay();	            
		checkDistanceToCity();
		checkQuickCloseDuration();
		checkConditionFinancing();
		checkMaxMortgage();
		checkMinMortgage();
	}

	protected void checkPropertyCriteria(double totalDealLiabilities,
			double totalDealMortgagesBalance, double totalDealCollections,
			double valueOfProperty, double beaconScoreToUse) {
		if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Prequalify, clientOpportunity.whatIsYourLendingGoal) == false){
			// Check on Networth of the Deal 
			if (statedIncomeDeal == false){
				checkNetWorth(totalDealLiabilities,totalDealMortgagesBalance, totalDealCollections);	
			}

			Boolean isAConstructionDeal = checkIfConstruction();	                
			checkConstructionCriteria(isAConstructionDeal);	
			checkChargeOnTitle(valueOfProperty);	                
			checkAcres();	
			checkMinSize();
			checkCottageRec(beaconScoreToUse);
			checkCountryResidential();
			checkLifeLeased();
			checkCondo();
			checkRawLand();
			checkLeasedLand();
			checkAgriLessThan10();
			checkMobileHome();
			checkAgricultural();
			checkModular();
			checkCommercial();
			checkFloatingHome();
			checkFractionalInterest();
			checkBoardingHouse();
			checkCooperative();
			checkRoomingHouse();
			checkGrowOp();
			checkNonConvConstruction();
			checkRentalPools();
			checkAgeRestricted();
			checkRentalProperty();
			checkDuplex();
			checkFourplex();
			checkSixplex();
			checkEightplex();
			checkHighRatio2ndHome();
			checkUninsured2ndHome();
			checkVendorTakeBack();	
			// Check on Shortest Average Funding days.
			checkFundingDaysTimeline();	                
			// Check on totalDealPropertiesCount and skew fitness in the event product is back end insured. 
			checkTotalProperties();

		}
	}

	public boolean nonTraditionalDownpay() {
		boolean nonTraditionalDownPayment = false;
		if (SelectionHelper.compareSelection(AlgoOpportunity.DownpaymentSource.Gift, clientOpportunity.downPaymentComingFrom)
				|| SelectionHelper.compareSelection(AlgoOpportunity.DownpaymentSource.Borrowed, clientOpportunity.downPaymentComingFrom))
			nonTraditionalDownPayment = true;
		else
			nonTraditionalDownPayment = false;
		
		this.nonTraditionalDownPayment = true;
		
		return this.nonTraditionalDownPayment;
	}
	
	private void checkMaxAmortization() {
		if (clientOpportunity.desiredAmortization <= potentialProduct.maximumAmortization)
		    countOfAcceptableCritera++;
		else
		{
		    countOfUnacceptableCriteria++;
		    countOfUnacceptableCriteria++;
		    countOfUnacceptableCriteria++;
		    AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Application includes an amortization that is longer than 'Top of List' Lender will lend against.", "FailedApplication" + failedNotesCounter);
		    dealNotes.add(note1);
		    failReasons.add("Desired Amortization is greater than this lender allows.");
		    failedNotesCounter++;
		}
	}

	public void checkOpportunityQualifies() {
		opportunityQualifies = true;
		if (OpportunityCreditCategory.equals(AlgoApplicant.CreditCategory.Good))
		{
		    if (countOfUnacceptableCriteria > potentialProduct.allowedRedFlagsGood)  
		        opportunityQualifies = false;
		    else if (countOfQuestionableCriteria > potentialProduct.allowedGreyFlagsGood)  
		        opportunityQualifies = false; 
		}
		else if (OpportunityCreditCategory.compareTo(AlgoApplicant.CreditCategory.Medium) == 0)
		{
			if (countOfUnacceptableCriteria > potentialProduct.allowedRedFlagsMed)  
		        opportunityQualifies = false;
		    else if (countOfQuestionableCriteria > potentialProduct.allowedGreyFlagsMed)  
		        opportunityQualifies = false;
		}
		else if (OpportunityCreditCategory.equals(AlgoApplicant.CreditCategory.Poor))
		{
			if (countOfUnacceptableCriteria > potentialProduct.allowedRedFlagsPoor)  
		        opportunityQualifies = false;
		    else if (countOfQuestionableCriteria > potentialProduct.allowedGreyFlagsPoor)  
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

	private void checkQuickCloseDuration() {
		if (clientOpportunity.expectedClosingDate != null){
			Calendar dateNow = Calendar.getInstance();
		    dateNow.add(Calendar.DAY_OF_MONTH, potentialProduct.quickCloseDuration);
		    if (clientOpportunity.expectedClosingDate.after(dateNow.getTime())){
		    	AlgoNote assistNote4 = new AlgoNote(AlgoNote.TypeOfNote.AssistantAction, AlgoNote.Priority.High, "Closing Date does not meet quick Close Duration.");
		        assistantNotes.add(assistNote4);
		        failReasons.add("Closing Date does not meet quick Close Duration.");
		    	countOfUnacceptableCriteria++;
		    	countOfUnacceptableCriteria++;
		    	countOfUnacceptableCriteria++;
		    }
		    else{
		    	countOfAcceptableCritera++;
		    }
		}
	}

	private void checkConditionFinancing() {
		if (clientOpportunity.conditionOfFinancingDate != null){
			Calendar dateNow = Calendar.getInstance();
		    dateNow.add(Calendar.HOUR, potentialProduct.avgProcessingSpeed.intValue());
		    if (clientOpportunity.conditionOfFinancingDate.before(dateNow.getTime())){
		        failReasons.add("Condition of financing date does not meet lender's processing speed.");
		    	countOfUnacceptableCriteria++;
		    	countOfUnacceptableCriteria++;
		    	countOfUnacceptableCriteria++;
		    }
		    else{
		    	countOfAcceptableCritera++;
		    }
		}
	}
	
	private void checkDistanceToCity() {
		if (clientOpportunity.distanceToMajorCenter > potentialProduct.requirDistanceToCity){
			countOfUnacceptableCriteria++;
			 AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "This Property is too far from a Major Center for this Lender's guidelines.", "FailedApplication" + failedNotesCounter);
		     dealNotes.add(note1);
		     failReasons.add("This Property is too far from a Major Center for this Lender's guidelines.");
		     failedNotesCounter++;
		}
		else{
			countOfAcceptableCritera++;
		}
	}

	private void checkBorrowedDownpay() {
		boolean checkBorrowedDownPay = false;
		if (clientOpportunity.borrowedAmount > 0 && clientOpportunity.downpaymentAmount > 0){
			double borrowedPercent = clientOpportunity.borrowedAmount / clientOpportunity.downpaymentAmount; 
			 if (borrowedPercent <= potentialProduct.maxBorrowAllow / 100)
		         countOfAcceptableCritera++;
		     else
		     {
		         countOfUnacceptableCriteria++;
		         AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Application includes a downpayment that is borrowed.  The amount of the borrowed portion of the downpayment is larger than 'Top of List' Lender will lend against.", "FailedApplication" + failedNotesCounter);
		         dealNotes.add(note1);
		         failReasons.add("Application includes a downpayment that is borrowed.  The amount of the borrowed portion of the downpayment is larger than 'Top of List' Lender will lend against.");
		         failedNotesCounter++;
		     }	
		}
	}

	private void checkGift() {
		boolean checkGift = false;
		if (clientOpportunity.giftedAmount > 0 && clientOpportunity.downpaymentAmount > 0){
			double giftPercent = clientOpportunity.giftedAmount / clientOpportunity.downpaymentAmount; // TODO Returns NaN deal with zeros
		    
		    if (giftPercent <= potentialProduct.maxGiftAllow / 100)
		        countOfAcceptableCritera++;
		    else
		    {
		        countOfUnacceptableCriteria++;
		        AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Application includes a downpayment that is gifted.  The amount of the gifted portion of the downpayment is larger than 'Top of List' Lender will lend against.", "FailedApplication" + failedNotesCounter);
		        dealNotes.add(note1);
		        failReasons.add("Application includes a downpayment that is gifted.  The amount of the gifted portion of the downpayment is larger than 'Top of List' Lender will lend against.");
		        failedNotesCounter++;
		    }	
		}
	}

	private void checkAllowedProvince() {
		boolean ProvinceAllowed = false;
		for (String prov : allowedProvinces) { 
			if (clientOpportunity.province.equalsIgnoreCase(prov)){
				ProvinceAllowed = true; 
				break;
			}	
		}
		
		if (ProvinceAllowed == false){
			countOfUnacceptableCriteria++;
		    AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "This Lender does not loan funds in the Province where the property is located.", "FailedApplication" + failedNotesCounter);
		    dealNotes.add(note1);
		    failReasons.add("This Lender does not loan funds in the Province where the property is located.");
		    failedNotesCounter++;
		}
		else {
			countOfAcceptableCritera++;
		}
	}

	private void checkBeaconScore(double beaconScoreToUse) {
		if (beaconScoreToUse < potentialProduct.minBeaconCutOff){
			countOfUnacceptableCriteria++;
			AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Client's Credit is lower than Lender's acceptable minimum.  This opportunity will need another applicant with strong credit", "FailedApplication" + failedNotesCounter);
			dealNotes.add(note1);
			failReasons.add("Client's Credit is lower than Lender's acceptable minimum.  This opportunity will need another applicant with strong credit");
			failedNotesCounter++;
		}   
		else if (beaconScoreToUse >= potentialProduct.minBeaconCutOff && beaconScoreToUse < potentialProduct.minBeacon){
			countOfQuestionableCriteria++;
			AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Client's Credit is lower than Lender's prefered minimum.  This opportunity might need another applicant with strong credit", "FailedApplication" + failedNotesCounter);
			dealNotes.add(note1);
			failReasons.add("Client's Credit is lower than Lender's prefered minimum.  This opportunity might need another applicant with strong credit");
			failedNotesCounter++;
		}
		else
		{
			countOfAcceptableCritera++;   
		}
	}

	private void checkDealCollections(double totalDealCollections) {
		if (totalDealCollections > 0){
			if (potentialProduct.outstandingCollectionPayment == true){
				countOfQuestionableCriteria++;
				AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Client needs to pay outstanding Collections. ", "FailedApplication" + failedNotesCounter);
				dealNotes.add(note1);
				failReasons.add("Client needs to pay outstanding Collections. ");
				failedNotesCounter++;
			}
			else{
				countOfAcceptableCritera++;
			}
		}
	}

	private void checkMaximumApplicants() {
		if (clientOpportunity.applicants.size() <= potentialProduct.maximumApplicants)
		    countOfAcceptableCritera++;
		else
		{
		    countOfUnacceptableCriteria++;
		    AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Application includes more applicants than 'Top of List' Lender will include in an application.", "FailedApplication" + failedNotesCounter);
		    failReasons.add("This Opportunity has too many applicants for this lender. ");
		    dealNotes.add(note1);
		    failedNotesCounter++;
		}
	}

	private void checkPropertyLocation() {
		if (SelectionHelper.compareSelection(AlgoProduct.ExcludeOrIncludeCities.Exclude, potentialProduct.exclOrInclCity))
		{// TODO Need to Test
			boolean isAcceptable = true; 
		    List<String> consideredCities; // Was ConsideredCities - OpenERP = 
		    if (potentialProduct.cities != null){
				consideredCities = Arrays.asList(potentialProduct.cities.split(","));
				if (algoClientOpportunity.consideredCities != null){
		        	for (String city : algoClientOpportunity.consideredCities)
		            {
		        		for (String productCity : consideredCities) {
							if (city.contentEquals(productCity)){
								isAcceptable = false;
							}
						}                   
		            }
		            if (isAcceptable == true)
		                countOfAcceptableCritera++;
		            else
		            {
		                countOfUnacceptableCriteria++;
		                AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Application is in a city where 'Top of List' Lender does not lend.", "FailedApplication" + failedNotesCounter);
		                dealNotes.add(note1);
		                failReasons.add("Application is in a city where 'Top of List' Lender does not lend.");
		                failedNotesCounter++;
		            }
		        }
		        else if (clientOpportunity.city != null){
		        	for (String productCity : consideredCities) {
						if (clientOpportunity.city.contentEquals(productCity)){
							isAcceptable = false;
						}
					}  
		        	if (isAcceptable == true)
		                countOfAcceptableCritera++;
		            else
		            {
		                countOfUnacceptableCriteria++;
		                AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Application is in a city where 'Top of List' Lender does not lend.", "FailedApplication" + failedNotesCounter);
		                dealNotes.add(note1);
		                failReasons.add("Application is in a city where 'Top of List' Lender does not lend.");
		                failedNotesCounter++;
		            }
		        }
			}
		    else{
		    	countOfAcceptableCritera++;
		    }
		}
		else if (SelectionHelper.compareSelection(AlgoProduct.ExcludeOrIncludeCities.Include, potentialProduct.exclOrInclCity))
		{
		    boolean isAcceptable = false; 
		    List<String> consideredCities; // Was ConsideredCities - OpenERP = 
		    if (potentialProduct.cities != null){
				consideredCities = Arrays.asList(potentialProduct.cities.split(","));
				if (algoClientOpportunity.consideredCities != null){
		        	for (String city : algoClientOpportunity.consideredCities)
		            {
		        		for (String productCity : consideredCities) {
							if (city.contentEquals(productCity)){
								isAcceptable = true;
							}
						}                   
		            }
		            if (isAcceptable == true)
		                countOfAcceptableCritera++;
		            else
		            {
		                countOfUnacceptableCriteria++;
		                AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Application is in a city where 'Top of List' Lender does not lend.", "FailedApplication" + failedNotesCounter);
		                dealNotes.add(note1);
		                failReasons.add("Application is in a city where 'Top of List' Lender does not lend.");
		                failedNotesCounter++;
		            }
		        }
		        else if (clientOpportunity.city != null){
		        	for (String productCity : consideredCities) {
						if (clientOpportunity.city.contentEquals(productCity)){
							isAcceptable = true;
						}
					}  
		        	if (isAcceptable == true)
		                countOfAcceptableCritera++;
		            else
		            {
		                countOfUnacceptableCriteria++;
		                AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Application is in a city where 'Top of List' Lender does not lend.", "FailedApplication" + failedNotesCounter);
		                dealNotes.add(note1);
		                failReasons.add("Application is in a city where 'Top of List' Lender does not lend.");
		                failedNotesCounter++;
		            }
		        }
			}
		    else{
		    	countOfAcceptableCritera++;
		    }
		    
		}
	}

	private void checkCanadianMilitary() {
		if (potentialProduct.canadianMilitaryAllow == false)
		{
		    if (clientOpportunity.isMilitary == false)
		        countOfAcceptableCritera++;
		    else
		    {
		        countOfUnacceptableCriteria++;
		        AlgoNote Note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Application includes a Military Borrower.  'Top of List' Lender does not lend against this type of applicant.", "FailedApplication" + failedNotesCounter);
		        dealNotes.add(Note1);
		        failReasons.add("Application includes a Military Borrower.  'Top of List' Lender does not lend against this type of applicant.");
		        failedNotesCounter++;
		    }
		}
		else
		    countOfAcceptableCritera++;
	}

	private void checkNonResident() {
		if (potentialProduct.nonResidentAllow == false)
		{
		    if (algoClientOpportunity.nonResidentApplicant == false)
		        countOfAcceptableCritera++;
		    else
		    {
		        countOfUnacceptableCriteria++;
		        AlgoNote Note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Application includes a Non-Resident Borrower.  'Top of List' Lender does not lend against this type of applicant.", "FailedApplication" + failedNotesCounter);
		        dealNotes.add(Note1);
		        failReasons.add("Application includes a Non-Resident Borrower.  'Top of List' Lender does not lend against this type of applicant.");
		        failedNotesCounter++;
		    }
		}
		else
		    countOfAcceptableCritera++;
	}

	private void calcBusinessEaseFitness() {
		if (statedIncomeDeal == false){
			if (hasSelfEmployedIncome == true){
		    	int extraFitness = 0;
		    	if (potentialProduct.lastYrNoa == true && potentialProduct.t1GeneralFormula == false && 
		    			potentialProduct.twoYrAvgNoaPercent == false && potentialProduct.twoYrAvgNoa == false)
		    		extraFitness = 5;
		    	else if (potentialProduct.lastYrNoa == false && potentialProduct.t1GeneralFormula == false && 
		    			potentialProduct.twoYrAvgNoaPercent == true && potentialProduct.twoYrAvgNoa == false)
		    		extraFitness = 4;
		    	else if (potentialProduct.lastYrNoa == false && potentialProduct.t1GeneralFormula == false && 
		    			potentialProduct.twoYrAvgNoaPercent == true && potentialProduct.twoYrAvgNoa == true)
		    		extraFitness = 4;
		    	else 
		    		extraFitness = 1;
		    	potentialProduct.businessEase = potentialProduct.businessEase + (double)extraFitness;
		    }
		    
		    if (hasEmployedIncome == true){
		    	int extraFitness = 0;
		    	if (potentialProduct.letterOfEmployment == true && potentialProduct.payStubProrate == false && 
		    			potentialProduct.lastYrNoa == false && potentialProduct.twoYrAverageNoa == false)
		    		extraFitness = 5;
		    	else if (potentialProduct.letterOfEmployment == true && potentialProduct.payStubProrate == true && 
		    			potentialProduct.lastYrNoa == false && potentialProduct.twoYrAverageNoa == false)
		    		extraFitness = 4;
		    	else 
		    		extraFitness = 1;
		    	potentialProduct.businessEase = potentialProduct.businessEase + (double)extraFitness;
		    }
		}
	}

	private void checkAllowSelfEmployed() {
		if (potentialProduct.allowSelfEmpIncome == false)
		{
		    if (hasSelfEmployedIncome == true)
		    {
		        countOfUnacceptableCriteria++;
		        AlgoNote Note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Application includes Self-Employed Income.  'Top of List' Lender does not lend against this type of income.", "FailedApplication" + failedNotesCounter);
		        dealNotes.add(Note1);
		        failReasons.add("Application includes Self-Employed Income.  'Top of List' Lender does not lend against this type of income.");
		        failedNotesCounter++;
		    }
		    else
		        countOfAcceptableCritera++;
		}
		else
		    countOfAcceptableCritera++;
	}

	private void checkImmigrationCriteria(boolean applicantIsImmigrant,
			Date applicantImmigrationDate, double MaxEmployedMonths) {
		if (applicantIsImmigrant == true){
			if (applicantImmigrationDate != null){
				Calendar dateNow = Calendar.getInstance();
		    	int MonthsAgo = Integer.valueOf(potentialProduct.immigrantMaxMo.intValue()) * -1;
		        dateNow.add(Calendar.MONTH, MonthsAgo);
		        if (applicantImmigrationDate.after(dateNow.getTime())){
		        	AlgoNote assistNote4 = new AlgoNote(AlgoNote.TypeOfNote.AssistantAction, AlgoNote.Priority.High, "Applicant does not qualify for immigrant product, must qualify normally.");
		            assistantNotes.add(assistNote4);
		        	countOfQuestionableCriteria++;
		        }
		        else{
		        	countOfAcceptableCritera++;
		        }
			}	
			if (statedIncomeDeal == false){
				if (MaxEmployedMonths < potentialProduct.immigrantMaxMo ){
					countOfUnacceptableCriteria++;
					failReasons.add("Applicant does not meet minimum Employment Months for this Product");
					AlgoNote assistNote4 = new AlgoNote(AlgoNote.TypeOfNote.AssistantAction, AlgoNote.Priority.High, "Applicant does not meet minimum Employment Months for this Product");
		            assistantNotes.add(assistNote4);
				}
				else{
					countOfAcceptableCritera++;
				}	
			}
			
			
			if (LTV > potentialProduct.immigrantMaxLtv){
				countOfUnacceptableCriteria++;
				failReasons.add("LTV exceeds maximum LTV for Immigration Program");
			}
			else{
				countOfAcceptableCritera++;
			}
		}
	}

	private void checkProductUninsurable() {
		if (potentialProduct.uninsurable == true){
			// TODO Need to test to ensure that on LOC, etc, this logic works ... i.e. Opporutnity is Calc as High Ratio on LOCs
		    if (algoClientOpportunity.highRatio == true){
		    	countOfUnacceptableCriteria++;
		    	countOfUnacceptableCriteria++;
		    	countOfUnacceptableCriteria++;
		        AlgoNote Note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "The LTV of this Property exceeds this product's maximum LTV.  This Product is not insurable.", "FailedApplication" + failedNotesCounter);
		        dealNotes.add(Note1);
		        failReasons.add("The LTV of this Property exceeds this product's maximum LTV.  This Product is not insurable.");
		        failedNotesCounter++;
		    } 
		    else{
		    	countOfUnacceptableCriteria++;
		    }            	            	
		}
	}

	private void checkTotalProperties() {
		if (totalDealPropertiesCount > 2){
			double AddValueToUnderwriterPref = 0;
			if (potentialProduct.isProductInsured == false){
				AddValueToUnderwriterPref = 5;
			}
			potentialProduct.underwriterPref = potentialProduct.underwriterPref + AddValueToUnderwriterPref;
		}
	}

	private void checkFundingDaysTimeline() {
		if (clientOpportunity.expectedClosingDate != null){
			Date closeDate;
		    long DaysToClose; 
			try {
				closeDate = clientOpportunity.expectedClosingDate;
				DaysToClose = (closeDate.getTime() - new Date().getTime())/( 24 * 60 * 60 * 1000 );
				if (DaysToClose < potentialProduct.shortAvgFundDay + 7){  // 7days added to account for lawyers, lender etc
					countOfQuestionableCriteria++;
					AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "THE CLOSING DATE IS SOONER THAN THE LENDER'S AVERAGE FUNDING TIMEFRAME. In the event this lender is used, it is wise to have the client extend the closing data on the purchase.", "FailedApplication" + failedNotesCounter);
		            dealNotes.add(note1);
		            failReasons.add("THE CLOSING DATE IS SOONER THAN THE LENDER'S AVERAGE FUNDING TIMEFRAME. In the event this lender is used, it is wise to have the client extend the closing data on the purchase.");
		            failedNotesCounter++;
				}
				else 
					countOfAcceptableCritera++;
				
			} catch (Exception e) {
				e.printStackTrace();
				DaysToClose = 0; 
			}	
		}
	}

	private void checkVendorTakeBack() {
		if (clientOpportunity.otherAmount > 0){
			if (this.LTV > potentialProduct.vtbMaxLtv){
				countOfUnacceptableCriteria++;
		        AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "The Loan to value of this Vendor takeback opportunity is larger than the maximum allowed for this lender.  In the event this opportunity is not Vendor Takeback, please reallocate the 'Other' Downayment Amount and re-run the algorithm. .", "FailedApplication" + failedNotesCounter);
		        dealNotes.add(note1);
		        failReasons.add("The Loan to value of this Vendor takeback opportunity is larger than the maximum allowed for this lender.  In the event this opportunity is not Vendor Takeback, please reallocate the 'Other' Downayment Amount and re-run the algorithm. .");
		        failedNotesCounter++;
			}
			else
				countOfAcceptableCritera++;
		}
	}

	private void checkUninsured2ndHome() {
		if (clientOpportunity.isUninsuredConv2ndHome == true)
		{
		    if (potentialProduct.uninsuredConv2ndHome == true && potentialProduct.allow2ndHomes == true)
		        countOfAcceptableCritera++;
		    else
		    {
		        countOfUnacceptableCriteria++;
		        AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Application is for a Conventional (non-insured) 2nd Home Property.  'Top of List' Lender does not lend against this property Type.", "FailedApplication" + failedNotesCounter);
		        dealNotes.add(note1);
		        failReasons.add("Application is for a Conventional (non-insured) 2nd Home Property.  'Top of List' Lender does not lend against this property Type.");
		        failedNotesCounter++;
		    }
		}
	}

	private void checkHighRatio2ndHome() {
		if (clientOpportunity.isHighRatio2ndHome == true)
		{
		    if (potentialProduct.highRatio2ndHome == true && potentialProduct.allow2ndHomes == true)
		        countOfAcceptableCritera++;
		    else
		    {
		        countOfUnacceptableCriteria++;
		        AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Application is for a High Ratio 2nd Home Property.  'Top of List' Lender does not lend against this property Type.", "FailedApplication" + failedNotesCounter);
		        dealNotes.add(note1);
		        failReasons.add("Application is for a High Ratio 2nd Home Property.  'Top of List' Lender does not lend against this property Type.");
		        failedNotesCounter++;
		    }
		}
	}

	private void checkEightplex() {
		if (clientOpportunity.isEightPlex == true)
		{
		    if (potentialProduct.allowEightPlex == true)
		        countOfAcceptableCritera++;
		    else
		    {
		        countOfUnacceptableCriteria++;
		        AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Application is for a Eightplex Property.  'Top of List' Lender does not lend against this property Type.", "FailedApplication" + failedNotesCounter);
		        dealNotes.add(note1);
		        failReasons.add("Application is for a Eightplex Property.  'Top of List' Lender does not lend against this property Type.");
		        failedNotesCounter++;
		    }
		}
	}

	private void checkSixplex() {
		if (clientOpportunity.isSixPlex == true)
		{
		    if (potentialProduct.allowSixPlex == true)
		        countOfAcceptableCritera++;
		    else
		    {
		        countOfUnacceptableCriteria++;
		        AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Application is for a Sixplex Property.  'Top of List' Lender does not lend against this property Type.", "FailedApplication" + failedNotesCounter);
		        dealNotes.add(note1);
		        failReasons.add("Application is for a Sixplex Property.  'Top of List' Lender does not lend against this property Type.");
		        failedNotesCounter++;
		    }
		}
	}

	private void checkFourplex() {
		if (clientOpportunity.isFourPlex == true)
		{
		    if (potentialProduct.allowFourPlex == true)
		        countOfAcceptableCritera++;
		    else
		    {
		        countOfUnacceptableCriteria++;
		        AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Application is for a Fourplex Property.  'Top of List' Lender does not lend against this property Type.", "FailedApplication" + failedNotesCounter);
		        dealNotes.add(note1);
		        failReasons.add("Application is for a Fourplex Property.  'Top of List' Lender does not lend against this property Type.");
		        failedNotesCounter++;
		    }
		}
	}

	private void checkDuplex() {
		if (clientOpportunity.isDuplex == true)
		{
		    if (potentialProduct.allowDuplex == true)
		        countOfAcceptableCritera++;
		    else
		    {
		        countOfUnacceptableCriteria++;
		        AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Application is for a Duplex Property.  'Top of List' Lender does not lend against this property Type.", "FailedApplication" + failedNotesCounter);
		        dealNotes.add(note1);
		        failReasons.add("Application is for a Duplex Property.  'Top of List' Lender does not lend against this property Type.");
		        failedNotesCounter++;
		    }
		}
	}

	private void checkRentalProperty() {
				
		if (SelectionHelper.compareSelection(AlgoOpportunity.WhosLiving.OwnerAndRenter, clientOpportunity.livingInProperty)
				|| (SelectionHelper.compareSelection(AlgoOpportunity.WhosLiving.Renter, clientOpportunity.livingInProperty))){
					clientOpportunity.isRentalProperty = true;
		}
		
		
		if (clientOpportunity.isRentalProperty == true)  // May need to consolidate booleans
		{
		    if (potentialProduct.rentalProperty == true)
		        countOfAcceptableCritera++;
		    else
		    {
		        countOfUnacceptableCriteria++;
		        countOfUnacceptableCriteria++;
		        countOfUnacceptableCriteria++;
		        AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Application is for a Rental Property.  'Top of List' Lender does not lend against this property Type.", "FailedApplication" + failedNotesCounter);
		        dealNotes.add(note1);
		        failReasons.add("Application is for a Rental Property.  'Top of List' Lender does not lend against this property Type.");
		        failedNotesCounter++;
		    }
		}
	}

	private void checkAgeRestricted() {
		if (clientOpportunity.isAgeRestricted == true)
		{
		    if (potentialProduct.ageRestricted == true)
		        countOfAcceptableCritera++;
		    else
		    {
		        countOfUnacceptableCriteria++;
		        AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Application is for an Age Restricted Property.  'Top of List' Lender does not lend against this property Type.", "FailedApplication" + failedNotesCounter);
		        dealNotes.add(note1);
		        failReasons.add("Application is for an Age Restricted Property.  'Top of List' Lender does not lend against this property Type.");
		        failedNotesCounter++;
		    }
		    
		    if (LTV > potentialProduct.ageRestrictedLtv){
	    		countOfUnacceptableCriteria++;
	            AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "This property is age restricted and the Loan to Value is larger than what is allowed by this lender.  This opportunity will need a larger downpayment.", "FailedApplication" + failedNotesCounter);
	            dealNotes.add(note1);
	            failedNotesCounter++;
	    	}
	    	else {
	    		countOfAcceptableCritera++;
	    	}		    
		}
	}

	private void checkRentalPools() {
		if (clientOpportunity.isRentalPools == true)
		{
		    if (potentialProduct.rentalPools == true)
		        countOfAcceptableCritera++;
		    else
		    {
		        countOfUnacceptableCriteria++;
		        AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Application is for a Rental Pool Property.  'Top of List' Lender does not lend against this property Type.", "FailedApplication" + failedNotesCounter);
		        dealNotes.add(note1);
		        failReasons.add("Application is for a Rental Pool Property.  'Top of List' Lender does not lend against this property Type.");
		        failedNotesCounter++;
		    }
		}
	}

	private void checkNonConvConstruction() {
		if (clientOpportunity.isNonConvConstruction == true)
		{
		    if (potentialProduct.nonConvConstruction == true)
		        countOfAcceptableCritera++;
		    else
		    {
		        countOfUnacceptableCriteria++;
		        AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Application is for a Non-Conventional Construction Property.  'Top of List' Lender does not lend against this property Type.", "FailedApplication" + failedNotesCounter);
		        dealNotes.add(note1);
		        failReasons.add("Application is for a Non-Conventional Construction Property.  'Top of List' Lender does not lend against this property Type.");
		        failedNotesCounter++;
		    }
		}
	}

	private void checkGrowOp() {
		if (clientOpportunity.isGrowOps == true)
		{
		    if (potentialProduct.growOps == true)
		        countOfAcceptableCritera++;
		    else
		    {
		        countOfUnacceptableCriteria++;
		        AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Application is for a Grow Op Property.  'Top of List' Lender does not lend against this property Type.", "FailedApplication" + failedNotesCounter);
		        dealNotes.add(note1);
		        failReasons.add("Application is for a Grow Op Property.  'Top of List' Lender does not lend against this property Type.");
		        failedNotesCounter++;
		    }
		}
	}

	private void checkRoomingHouse() {
		if (clientOpportunity.isRoomingHouses == true)
		{
		    if (potentialProduct.roomingHouses == true)
		        countOfAcceptableCritera++;
		    else
		    {
		        countOfUnacceptableCriteria++;
		        AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Application is for a Rooming House Property.  'Top of List' Lender does not lend against this property Type.", "FailedApplication" + failedNotesCounter);
		        dealNotes.add(note1);
		        failReasons.add("Application is for a Rooming House Property.  'Top of List' Lender does not lend against this property Type.");
		        failedNotesCounter++;
		    }
		}
	}

	private void checkCooperative() {
		if (clientOpportunity.isCoOperativeHousing == true)
		{
		    if (potentialProduct.coOperativeHousing == true)
		        countOfAcceptableCritera++;
		    else
		    {
		        countOfUnacceptableCriteria++;
		        AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Application is for a Cooperative Housing Property.  'Top of List' Lender does not lend against this property Type.", "FailedApplication" + failedNotesCounter);
		        dealNotes.add(note1);
		        failReasons.add("Application is for a Cooperative Housing Property.  'Top of List' Lender does not lend against this property Type.");
		        failedNotesCounter++;
		    }
		}
	}

	private void checkBoardingHouse() {
		if (clientOpportunity.isBoardingHouses == true)
		{
		    if (potentialProduct.boardingHouses == true)
		        countOfAcceptableCritera++;
		    else
		    {
		        countOfUnacceptableCriteria++;
		        AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Application is for a Boarding House Property.  'Top of List' Lender does not lend against this property Type.", "FailedApplication" + failedNotesCounter);
		        dealNotes.add(note1);
		        failReasons.add("Application is for a Boarding House Property.  'Top of List' Lender does not lend against this property Type.");
		        failedNotesCounter++;
		    }
		}
	}

	private void checkFractionalInterest() {
		if (clientOpportunity.isFractionalInterests == true)
		{
		    if (potentialProduct.fractionalInterests == true)
		        countOfAcceptableCritera++;
		    else
		    {
		        countOfUnacceptableCriteria++;
		        AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Application is for a Fractional Interest Property.  'Top of List' Lender does not lend against this property Type.", "FailedApplication" + failedNotesCounter);
		        dealNotes.add(note1);
		        failReasons.add("Application is for a Fractional Interest Property.  'Top of List' Lender does not lend against this property Type.");
		        failedNotesCounter++;
		    }
		}
	}

	private void checkFloatingHome() {
		if (clientOpportunity.isFloatingHomes == true)
		{
		    if (potentialProduct.floatingHomes == true)
		        countOfAcceptableCritera++;
		    else
		    {
		        countOfUnacceptableCriteria++;
		        AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Application is for a Floating Home Property.  'Top of List' Lender does not lend against this property Type.", "FailedApplication" + failedNotesCounter);
		        dealNotes.add(note1);
		        failReasons.add("Application is for a Floating Home Property.  'Top of List' Lender does not lend against this property Type.");
		        failedNotesCounter++;
		    }
		}
	}

	private void checkCommercial() {
		if (clientOpportunity.isCommercial == true)
		{
		    if (potentialProduct.commercial == true)
		        countOfAcceptableCritera++;
		    else
		    {
		        countOfUnacceptableCriteria++;
		        AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Application is for a Commercial Property.  'Top of List' Lender does not lend against this property Type.", "FailedApplication" + failedNotesCounter);
		        dealNotes.add(note1);
		        failReasons.add("Application is for a Commercial Property.  'Top of List' Lender does not lend against this property Type.");
		        failedNotesCounter++;
		    }
		}
	}

	private void checkModular() {
		if (clientOpportunity.isModularHomes == true)
		{
		    if (potentialProduct.modularHomes == true)
		        countOfAcceptableCritera++;
		    else
		    {
		        countOfUnacceptableCriteria++;
		        AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Application is for a Modular Home Property.  'Top of List' Lender does not lend against this property Type.", "FailedApplication" + failedNotesCounter);
		        dealNotes.add(note1);
		        failReasons.add("Application is for a Modular Home Property.  'Top of List' Lender does not lend against this property Type.");
		        failedNotesCounter++;
		    }
		}
	}

	private void checkAgricultural() {
		if (clientOpportunity.isAgricultural == true)
		{
		    if (potentialProduct.agricultural == true)
		        countOfAcceptableCritera++;
		    else
		    {
		        countOfUnacceptableCriteria++;
		        AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Application is for an agricultural Property.  'Top of List' Lender does not lend against this property Type.", "FailedApplication" + failedNotesCounter);
		        dealNotes.add(note1);
		        failReasons.add("Application is for an agricultural Property.  'Top of List' Lender does not lend against this property Type.");
		        failedNotesCounter++;
		    }
		}
	}

	private void checkMobileHome() {
		if (clientOpportunity.isMobileHomes == true)
		{
		    if (potentialProduct.mobileHomes == true)
		        countOfAcceptableCritera++;
		    else
		    {
		        countOfUnacceptableCriteria++;
		        AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Application is for a Mobile Home Property.  'Top of List' Lender does not lend against this property Type.", "FailedApplication" + failedNotesCounter);
		        dealNotes.add(note1);
		        failReasons.add("Application is for a Mobile Home Property.  'Top of List' Lender does not lend against this property Type.");
		        failedNotesCounter++;
		    }
		}
	}

	private void checkAgriLessThan10() {
		if (clientOpportunity.isAgriculturalLess10Acres == true)
		{
		    if (potentialProduct.agriculturalLessThen10Acres == true)
		        countOfAcceptableCritera++;
		    else
		    {
		        countOfUnacceptableCriteria++;
		        AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Application is for a small Agricultural Property.  'Top of List' Lender does not lend against this property Type.", "FailedApplication" + failedNotesCounter);
		        dealNotes.add(note1);
		        failReasons.add("Application is for a small Agricultural Property.  'Top of List' Lender does not lend against this property Type.");
		        failedNotesCounter++;
		    }
		}
	}

	private void checkLeasedLand() {
		if (clientOpportunity.isLeasedLand == true)
		{
		    if (potentialProduct.leasedLand == true)
		        countOfAcceptableCritera++;
		    else
		    {
		        countOfUnacceptableCriteria++;
		        AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Application is for a Leased Land Property.  'Top of List' Lender does not lend against this property Type.", "FailedApplication" + failedNotesCounter);
		        dealNotes.add(note1);
		        failReasons.add("Application is for a Leased Land Property.  'Top of List' Lender does not lend against this property Type.");
		        failedNotesCounter++;
		    }
		}
	}

	private void checkRawLand() {
		if (clientOpportunity.isRawLand == true)
		{
		    if (potentialProduct.allowRawLand == true)
		        countOfAcceptableCritera++;
		    else
		    {
		        countOfUnacceptableCriteria++;
		        AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Application is for a Raw Land Property.  'Top of List' Lender does not lend against this property Type.", "FailedApplication" + failedNotesCounter);
		        dealNotes.add(note1);
		        failReasons.add("Application is for a Raw Land Property.  'Top of List' Lender does not lend against this property Type.");
		        failedNotesCounter++;
		    }
		}
	}

	private void checkCondo() {
		if (clientOpportunity.isCondo == true || 
		(clientOpportunity.preapprovedImLookingFora != null && 
		SelectionHelper.compareSelection(AlgoOpportunity.Looking.CondoMobileHome,clientOpportunity.preapprovedImLookingFora)))
		{
		    if (potentialProduct.condo == true)
		        countOfAcceptableCritera++;
		    else
		    {
		        countOfUnacceptableCriteria++;
		        AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Application is for a Condo Property.  'Top of List' Lender does not lend against this property Type.", "FailedApplication" + failedNotesCounter);
		        dealNotes.add(note1);
		        failReasons.add("Application is for a Condo Property.  'Top of List' Lender does not lend against this property Type.");
		        failedNotesCounter++;
		    }
		}
	}

	private void checkLifeLeased() {
		if (clientOpportunity.isLifeLeasedProperty == true)
		{
		    if (potentialProduct.lifeLeasedProperty == true)
		        countOfAcceptableCritera++;
		    else
		    {
		        countOfUnacceptableCriteria++;
		        AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Application is for a Life Leased Property.  'Top of List' Lender does not lend against this property Type.", "FailedApplication" + failedNotesCounter);
		        dealNotes.add(note1);
		        failReasons.add("Application is for a Life Leased Property.  'Top of List' Lender does not lend against this property Type.");
		        failedNotesCounter++;
		    }
		}
	}

	private void checkCountryResidential() {
		if (clientOpportunity.isCountryResidential == true)
		{
		    if (potentialProduct.countryResidential == true)
		        countOfAcceptableCritera++;
		    else
		    {
		        countOfUnacceptableCriteria++;
		        AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Application is for a Country Residential Property.  'Top of List' Lender does not lend against this property Type.", "FailedApplication" + failedNotesCounter);
		        dealNotes.add(note1);
		        failReasons.add("Application is for a Country Residential Property.  'Top of List' Lender does not lend against this property Type.");
		        failedNotesCounter++;
		    }
		}
	}

	private void checkCottageRec(double beaconScoreToUse) {
		if (clientOpportunity.isCottageRecProperty == true ||
			(SelectionHelper.compareSelection(AlgoOpportunity.Looking.CottageRecProperty, clientOpportunity.preapprovedImLookingFora)
			|| SelectionHelper.compareSelection(AlgoOpportunity.Looking.CottageRecProperty, clientOpportunity.lookingToApproved)
			|| SelectionHelper.compareSelection(AlgoOpportunity.Looking.CottageRecProperty, clientOpportunity.lookingToRefinance)))
		{
			if (potentialProduct.cottageRecProperty == true)
                countOfAcceptableCritera++;
            else
            {
                countOfUnacceptableCriteria++;
                AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Application is for a Cottage or Recreation Property.  'Top of List' Lender does not lend against this property Type.", "FailedApplication" + failedNotesCounter);
                dealNotes.add(note1);
                failReasons.add("Application is for a Cottage or Recreation Property.  'Top of List' Lender does not lend against this property Type.");
                failedNotesCounter++;
            }
			
		    // Check if LTV of Cottage is acceptable
		    if (clientOpportunity.ltv < potentialProduct.cottageMaxLtv)
		        countOfAcceptableCritera++;
		    else
		    {
		        countOfUnacceptableCriteria++;
		        AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Application is for a Recreation Property.  The loan to value is higher than what the Lenders Allow.  Mortgage Amount needs to Decrease.", "FailedApplication" + failedNotesCounter);
		        dealNotes.add(note1);
		        failReasons.add("Application is for a Recreation Property.  The loan to value is higher than what the Lenders Allow.  Mortgage Amount needs to Decrease.");
		        failedNotesCounter++;
		    }
		    // Check if Beacon of Cottage buyer is acceptable
		    if (beaconScoreToUse >= potentialProduct.cottageMinBeacon)
		        countOfAcceptableCritera++;
		    else
		    {
		        countOfUnacceptableCriteria++;
		        AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Application is for a Recreation Property.  Lenders require a higher Credit rating to lend funds on this type of property.", "FailedApplication" + failedNotesCounter);
		        dealNotes.add(note1);
		        failReasons.add("Application is for a Recreation Property.  Lenders require a higher Credit rating to lend funds on this type of property.");
		        failedNotesCounter++;
		    }
		}
		
		
	}

	private void checkMinSize() {
		// Check for Condo
		if (clientOpportunity.isCondo == true
				|| (clientOpportunity.preapprovedImLookingFora != null && 
				SelectionHelper.compareSelection(AlgoOpportunity.Looking.CondoMobileHome,clientOpportunity.preapprovedImLookingFora)))
		{
		    if (clientOpportunity.squareFootage < potentialProduct.minSqAllowCondo &&
		        clientOpportunity.squareFootage < potentialProduct.minSqCutoffCondo)
		    {
		        countOfUnacceptableCriteria++;
		        AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Property is smaller than the maximum size 'Top Of List' Lender allows.", "FailedApplication" + failedNotesCounter);
		        dealNotes.add(note1);
		        failReasons.add("Property is smaller than the maximum size 'Top Of List' Lender allows.");
		        failedNotesCounter++;
		    }
		    else if (clientOpportunity.squareFootage < potentialProduct.minSqAllowCondo &&
		        clientOpportunity.squareFootage >= potentialProduct.minSqCutoffCondo)
		        countOfQuestionableCriteria++;
		    else
		        countOfAcceptableCritera++;
		    
		    
		}
		// Check for Other Property Types
		if (clientOpportunity.isAgricultural == false && clientOpportunity.isCommercial == false && clientOpportunity.isFloatingHomes == false && clientOpportunity.isMobileHomes == false  && clientOpportunity.isRawLand == false 
				&& clientOpportunity.isCondo == false && clientOpportunity.isMobileHomes == false && clientOpportunity.isCottageRecProperty== false)   
		{
		    if (clientOpportunity.squareFootage < potentialProduct.minSqAllowHouse &&
		        clientOpportunity.squareFootage < potentialProduct.minSqCutoffHouse)
		    {
		        countOfUnacceptableCriteria++;
		        AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Property is smaller than the maximum size 'Top Of List' Lender allows.", "FailedApplication" + failedNotesCounter);
		        dealNotes.add(note1);
		        failReasons.add("Property is smaller than the maximum size 'Top Of List' Lender allows.");
		        failedNotesCounter++;
		    }
		    else if (clientOpportunity.squareFootage < potentialProduct.minSqAllowHouse &&
		        clientOpportunity.squareFootage >= potentialProduct.minSqCutoffHouse)
		        countOfQuestionableCriteria++;
		    else
		        countOfAcceptableCritera++;
		}
	}

	private void checkAcres() {
		if (clientOpportunity.acres != null){
			if (clientOpportunity.acres > potentialProduct.maxAcreageCutoff)
		    {
		        countOfUnacceptableCriteria++;
		        AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Property has more acreage than the maximum size 'Top Of List' Lender allows.", "FailedApplication" + failedNotesCounter);
		        dealNotes.add(note1);
		        failReasons.add("Property has more acreage than the maximum size 'Top Of List' Lender allows.");
		        failedNotesCounter++;
		    }
		    else if (clientOpportunity.acres > potentialProduct.maxAcreageAllowed)
		    {
		        countOfQuestionableCriteria++;
		        AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Property has more acreage than the maximum size 'Top Of List' Lender allows, but less than the 'Cutoff'.  Check if lender will make an excpetion", "FailedApplication" + failedNotesCounter);
		        dealNotes.add(note1);
		        failReasons.add("Property has more acreage than the maximum size 'Top Of List' Lender allows, but less than the 'Cutoff'.  Check if lender will make an excpetion");
		        failedNotesCounter++;
		    }
		    else
		        countOfAcceptableCritera++;
		}
	}
	
	protected void setOpportunityCreditCategory(double currentBeacon){
		// Used to be the Below .... 
//		if (beaconScoreToUse >= potentialProduct.goodCreditBeaconSplit && latePaymentsToUse <= potentialProduct.goodCredit12MoLates)
//			OpportunityCreditCategory = AlgoApplicant.CreditCategory.Good;
//		else if (beaconScoreToUse < potentialProduct.poorCreditBeaconSplit || latePaymentsToUse > potentialProduct.poorCredit12MoLates)
//			OpportunityCreditCategory = AlgoApplicant.CreditCategory.Poor;
//		else
//			OpportunityCreditCategory = AlgoApplicant.CreditCategory.Medium;
		
		// Removed LatePayment with the debate that those are all wrapped up into the Credit Score anyways ... Just simplify
		String creditNote = "";   
		
		
		if (currentBeacon >= potentialProduct.goodCreditBeaconSplit){
			OpportunityCreditCategory = AlgoApplicant.CreditCategory.Good;
			creditNote = "This deal has Great credit."; 
		}
		else if (currentBeacon < potentialProduct.goodCreditBeaconSplit && currentBeacon >= potentialProduct.poorCreditBeaconSplit ){
			OpportunityCreditCategory = AlgoApplicant.CreditCategory.Medium;
			creditNote = "This deal has Good credit."; 
		}
		else if (currentBeacon < potentialProduct.poorCreditBeaconSplit){
			OpportunityCreditCategory = AlgoApplicant.CreditCategory.Poor;
			creditNote = "The credit for these clients needs some help. We have explained to them how credit works and will continue to assist them."; 
		}
		else{
			OpportunityCreditCategory = AlgoApplicant.CreditCategory.Medium;
			creditNote = "This deal has Good credit."; 
		}
		
		listOfLenderNotes.append(creditNote + "/n");
	}

	private void checkChargeOnTitle(double valueOfProperty) {
		if (algoClientOpportunity.chargeOnTitle != null && algoClientOpportunity.chargeOnTitle.equals(AlgoOpportunity.Charge.First))
		{
		    if (algoClientOpportunity.opp.hasChargesBehind == true)
		    {
		        // Need to add field into product record that checks LTV of charges behind. Compare to ensure it works. 
		        if (potentialProduct.chargesAllowedBehind == true)
		        {// TODO Need to test HasChargesBehind
		            if ((expectedMortgageAmount + clientOpportunity.chargesBehindAmount) / valueOfProperty <= algoPotentialProduct.maxLTVAllowedBehind / 100)
		            {
		                countOfAcceptableCritera++;
		            }
		            else
		            {
		                countOfUnacceptableCriteria++;
		                AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "The total amount of money being borrowed against this property (including LOCs, 2nd, 3rd, Bridge Mortgages) exceeds the amount this Lender will allow.", "FailedApplication" + failedNotesCounter);
		                dealNotes.add(note1);
		                failReasons.add("The total amount of money being borrowed against this property (including LOCs, 2nd, 3rd, Bridge Mortgages) exceeds the amount this Lender will allow.");
		                failedNotesCounter++;
		            }
		        }
		        else
		        {
		            countOfUnacceptableCriteria++;
		            AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Application is for a First Mortage on a property that also has a 2nd mortgage on it.  This Lender does not lend in that circumstance.", "FailedApplication" + failedNotesCounter);
		            dealNotes.add(note1);
		            failReasons.add("Application is for a First Mortage on a property that also has a 2nd mortgage on it.  This Lender does not lend in that circumstance.");
		            failedNotesCounter++;
		        }
		    }
		}
		else if (algoClientOpportunity.chargeOnTitle != null && algoClientOpportunity.chargeOnTitle.equals(AlgoOpportunity.Charge.Second))
		{
		    if (potentialProduct.allowedOn2nd == true)
		    {
		        countOfAcceptableCritera++;
		        if (potentialProduct.specificLendersOn1st == true)
		        {
		            countOfQuestionableCriteria++;
		            AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.AssistantAction, AlgoNote.Priority.High, "Please note that the 2nd Mortgage Lender Requires Specific other Lenders on the 1st Mortgage.", "AssistantNote " + assistantNotesCounter);
		            assistantNotes.add(note1);
		            StringBuilder AllowedFirstLenders = new StringBuilder();
		            for (String lender : algoPotentialProduct.listOfLendersAllowedOnFirst)
		            {
		                AllowedFirstLenders.append(lender + ", ");
		            }
		            AlgoNote note2 = new AlgoNote(AlgoNote.TypeOfNote.AssistantAction, AlgoNote.Priority.High, "Please ensure the first Lender on the Property is one of the following Companies: " + AllowedFirstLenders, "AssistantNote " + assistantNotesCounter);
		            assistantNotes.add(note2);                            
		        }
		        AlgoNote note5 = new AlgoNote(AlgoNote.TypeOfNote.AssistantAction, AlgoNote.Priority.High, "Please note the rate for this mortage is likely to be higher as the lender has more risk on this property with another lender ahead of them on title.", "BrokerNote " + assistantNotesCounter);
		        brokerNotes.add(note5);
		        AlgoNote note6 = new AlgoNote(AlgoNote.TypeOfNote.AssistantAction, AlgoNote.Priority.High, "Please encourage the client to pay down the higher interest rate mortgage first.", "BrokerNote " + assistantNotesCounter);
		        brokerNotes.add(note6);
		    }
		    else
		    {
		        countOfUnacceptableCriteria++;
		        AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Application is for a 2nd Mortage.  This Lender does not provide 2nd Mortgages.", "FailedApplication" + failedNotesCounter);
		        dealNotes.add(note1);
		        failReasons.add("Application is for a 2nd Mortage.  This Lender does not provide 2nd Mortgages.");
		        failedNotesCounter++;
		    }
		}
		else if (algoClientOpportunity.chargeOnTitle != null && algoClientOpportunity.chargeOnTitle.equals(AlgoOpportunity.Charge.Third))
		{
		    if (potentialProduct.allowedOn3rd == true)
		    {
		        countOfAcceptableCritera++;
		        AlgoNote note5 = new AlgoNote(AlgoNote.TypeOfNote.AssistantAction, AlgoNote.Priority.High, "Please note the rate for this mortage is likely to be higher as the lender has more risk on this property with other lenders ahead of them on title.", "AssistantNote " + assistantNotesCounter);
		        brokerNotes.add(note5);
		        AlgoNote note6 = new AlgoNote(AlgoNote.TypeOfNote.AssistantAction, AlgoNote.Priority.High, "Please encourage the client to play down the higher interest rate mortgage first.", "BrokerNote " + assistantNotesCounter);
		        brokerNotes.add(note6);
		    }
		    else
		    {
		        countOfUnacceptableCriteria++;
		        AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Application is for a 3rd Mortage.  This Lender does not provide 3rd Mortgages.", "FailedApplication" + failedNotesCounter);
		        dealNotes.add(note1);
		        failReasons.add("Application is for a 3rd Mortage.  This Lender does not provide 3rd Mortgages.");
		        failedNotesCounter++;
		    }
		}
		else if (algoClientOpportunity.chargeOnTitle != null && algoClientOpportunity.chargeOnTitle.equals(AlgoOpportunity.Charge.Fourth))
		{
		    if (potentialProduct.allowedOn3rd == true)
		    {
		        countOfAcceptableCritera++;
		        AlgoNote note5 = new AlgoNote(AlgoNote.TypeOfNote.AssistantAction, AlgoNote.Priority.High, "Please note the rate for this mortage is likely to be higher as the lender has more risk on this property with other lenders ahead of them on title.", "AssistantNote " + assistantNotesCounter);
		        brokerNotes.add(note5);
		        AlgoNote note6 = new AlgoNote(AlgoNote.TypeOfNote.AssistantAction, AlgoNote.Priority.High, "Please encourage the client to play down the higher interest rate mortgage first.", "BrokerNote " + assistantNotesCounter);
		        brokerNotes.add(note6);
		    }
		    else
		    {
		        countOfUnacceptableCriteria++;
		        AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Application is for a 4th Mortage.  This Lender does not provide 4th Mortgages.", "FailedApplication" + failedNotesCounter);
		        dealNotes.add(note1);
		        failReasons.add("Application is for a 4th Mortage.  This Lender does not provide 4th Mortgages.");
		        failedNotesCounter++;
		    }
		}
		else if (algoClientOpportunity.chargeOnTitle != null && algoClientOpportunity.chargeOnTitle.equals(AlgoOpportunity.Charge.Bridge))
		{
		    if (potentialProduct.allowedOnBridge == true)
		    {
		        countOfAcceptableCritera++;
		        AlgoNote note5 = new AlgoNote(AlgoNote.TypeOfNote.AssistantAction, AlgoNote.Priority.High, "Please note the rate for this mortage is likely to be higher as the lender has more risk on this property with other lenders ahead of them on title.", "AssistantNote " + assistantNotesCounter);
		        brokerNotes.add(note5);
		        AlgoNote note6 = new AlgoNote(AlgoNote.TypeOfNote.AssistantAction, AlgoNote.Priority.High, "Please encourage the client to play down the higher interest rate mortgage first.", "BrokerNote " + assistantNotesCounter);
		        brokerNotes.add(note6);
		    }
		    else
		    {
		        countOfUnacceptableCriteria++;
		        AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Application is for a Bridge Mortage.  This Lender does not provide Bridge Mortgages.", "FailedApplication" + failedNotesCounter);
		        dealNotes.add(note1);
		        failReasons.add("Application is for a Bridge Mortage.  This Lender does not provide Bridge Mortgages.");
		        failedNotesCounter++;
		    }
		}
	}

	private void checkConstructionCriteria(Boolean isAConstructionDeal) {
		if (isAConstructionDeal == true)
		{
		    if (clientOpportunity.drawsRequired <= potentialProduct.maxNumberOfDraws)
		        countOfAcceptableCritera++;
		    else
		    {
		        countOfUnacceptableCriteria++;
		        AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Application requires more building draws than 'Top of List' Lender allows.", "FailedApplication" + failedNotesCounter);
		        dealNotes.add(note1);
		        failReasons.add("Application requires more building draws than 'Top of List' Lender allows.");
		        failedNotesCounter++;
		    }

		    if (SelectionHelper.compareSelection(AlgoOpportunity.BuildingFunds.SelfBuildDraw, clientOpportunity.buildingFunds))
		    {
		        if (potentialProduct.selfBuildProduct == true)
		            countOfAcceptableCritera++;
		        else
		        {
		            countOfUnacceptableCriteria++;
		            AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Application is for a Self-Build Construction Property wit construction draws.  'Top of List' Lender does not lend against this property Type.", "FailedApplication" + failedNotesCounter);
		            dealNotes.add(note1);
		            failReasons.add("Application is for a Self-Build Construction Property with construction draws.  'Top of List' Lender does not lend against this property Type.");
		            failedNotesCounter++;
		        }
		    }

		    // Need to Check on Construction Product Not only when complete, bust also with Draws if Builder constructed.
		    if (!SelectionHelper.compareSelection(AlgoOpportunity.BuildingFunds.Complete, clientOpportunity.buildingFunds))
		    {
		        if (potentialProduct.constructionProduct == true)
		            countOfAcceptableCritera++;
		        else
		        {
		            countOfUnacceptableCriteria++;
		            AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Application is for a Property under construction.  'Top of List' Lender does not lend against this property Type.", "FailedApplication" + failedNotesCounter);
		            dealNotes.add(note1);
		            failReasons.add("Application is for a Property under construction.  'Top of List' Lender does not lend against this property Type.");
		            failedNotesCounter++;
		        }
		    }
		}
	}

	private Boolean checkIfConstruction() {
		Boolean isAConstructionDeal = false;
		if (SelectionHelper.compareSelection(AlgoOpportunity.BuildingFunds.Complete, clientOpportunity.buildingFunds)
				|| SelectionHelper.compareSelection(AlgoOpportunity.BuildingFunds.ProgressDraw, clientOpportunity.buildingFunds)
				|| SelectionHelper.compareSelection(AlgoOpportunity.BuildingFunds.SelfBuildDraw, clientOpportunity.buildingFunds))
		{        	
			isAConstructionDeal = true;
		}
		return isAConstructionDeal;
	}

	private void checkNetWorth(double totalDealLiabilities,
			double totalDealMortgagesBalance, double totalDealCollections) {
		double TotalDealNetWorth = allApplicantsTotalAssets - totalDealLiabilities - totalDealMortgagesBalance - totalDealCollections;
		if (TotalDealNetWorth > 0){
			countOfAcceptableCritera++;
		}
		else{
			countOfQuestionableCriteria++;
		}
	}

	protected void checkMinMortgage() {
		if (expectedMortgageAmount >= potentialProduct.minMortgageAllowed)
			countOfAcceptableCritera++;
		else
		{
			countOfUnacceptableCriteria++;
			AlgoNote Note1 = new AlgoNote(AlgoNote.TypeOfNote.Info, AlgoNote.Priority.Med, "Application requires a Smaller Mortgage than 'Top of List' provide.", "FailedApplication" + failedNotesCounter);
			dealNotes.add(Note1);
			failReasons.add("Application requires a Smaller Mortgage than Top of List provide.");
			failedNotesCounter++;
		}
	}

	protected void checkMaxMortgage() {
		if (expectedMortgageAmount <= potentialProduct.maxMortgageAllowed)
		       countOfAcceptableCritera++;
		   else
		   {
		       countOfUnacceptableCriteria++;
		       AlgoNote Note1 = new AlgoNote(AlgoNote.TypeOfNote.Info, AlgoNote.Priority.Med, "Application requires a Larger Mortgage than 'Top of List' provide.", "FailedApplication" + failedNotesCounter);
		       dealNotes.add(Note1);
		       failReasons.add("Application requires a Larger Mortgage than Top of List provide.");
		       failedNotesCounter++;
		   }
	}

	protected double calcPropertyNetCashFlow(double productInterestRate,
			double interestRateVar) {
		double NetCashFlow = 0;
		if (SelectionHelper.compareSelection(AlgoProduct.TreatmentOfRental.SubtractFromLiabilities, potentialProduct.rentalIncomeTreatment)){
			double amountOfRentToSubtract = ((clientOpportunity.monthlyRentalIncome * (potentialProduct.rentalIncomePercent / 100) * 12));
			double initialPaymentEstimate = calculateExpectedPayment(productInterestRate, interestRateVar, amortization);
			double annualizedExpectedPayment = initialPaymentEstimate * 12;   
			//Calculate the net cashflow on the property ... 
			NetCashFlow = amountOfRentToSubtract - annualizedExpectedPayment;
			String rentalIncomeNote = "Subject property has annual net cashflow of " + NetCashFlow + " This has been included in deal as per guidelines./n";
			listOfLenderNotes.append(rentalIncomeNote);
		}
		return NetCashFlow;
	}

	protected double addPropertyRentalIncome(double totalDealIncome) {
		if (clientOpportunity.monthlyRentalIncome > 0){
			
			double allowableRentalIncome = 0;			
			//totalDealIncome = totalDealIncome + (clientOpportunity.monthlyRentalIncome * 12);  (this would add the rental income twice)      	
			if (SelectionHelper.compareSelection(AlgoProduct.TreatmentOfRental.AddToIncome, potentialProduct.rentalIncomeTreatment))
		    {
				if (clientOpportunity.monthlyRentalIncome != null){
					allowableRentalIncome = clientOpportunity.monthlyRentalIncome * (potentialProduct.rentalIncomePercent / 100)* 12;
					totalDealIncome = totalDealIncome + allowableRentalIncome;
					String rentalIncomeNote = "Rent of " + clientOpportunity.monthlyRentalIncome + " has been multiplied by " + potentialProduct.rentalIncomePercent + " as per guidelines to include allowable rental income of " + Math.round(allowableRentalIncome) + "/n";
					listOfLenderNotes.append(rentalIncomeNote);
				}
				else
				{
					AlgoNote assistantNote1 = new AlgoNote(AlgoNote.TypeOfNote.AssistantAction, AlgoNote.Priority.Med, 
							"Please Note that " + clientOpportunity.applicants.get(0).applicantName  + 
							" is purchasing a rental property. Please ensure the field Monthly Rental Income " + 
							"contains the expected monthly rent.");
		            brokerNotes.add(assistantNote1);
				}
		    }
			
			
		}
		return totalDealIncome;
	}

	protected double calcInsuranceCost() {
		double insuranceCost = 0;
		if (algoClientOpportunity.highRatio == true || clientOpportunity.lenderRequiresInsurance == true){
			double refinanceTopUpBalance = calcRefiTopupBalance();	
			boolean nonTraditionalDownPayment = checkNonTradDownPayment();	
			InsurerProducts insureProd = new InsurerProducts(this);
			// Insurance is needed for the Mortgage.  The insurance amount needs to be Calculated and added to the Mortgage Amount ... 
			//insuranceCost = InsurancePremiums.calculateInsurancePremium(algoClientOpportunity.refinanceInsured, clientOpportunity.ltv, hasSelfEmployedIncome, clientOpportunity.desiredMortgageAmount, refinanceTopUpBalance, nonTraditionalDownPayment);
			insuranceCost = InsurancePremiums.calculateInsurancePremium(insureProd);
			insuranceAmount = insuranceCost;
		}
		return insuranceCost;
	}
	
	protected double calcInsuranceCostOnMax(double mortgageAmount, double propertyValue) {
		double insuranceCost = 0;
		
		// Determine if HighRatio 
		boolean isHighratio = checkIfHighRatio(propertyValue, mortgageAmount);
		double LoanToValue = mortgageAmount / propertyValue * 100;		
		
		if (isHighratio == true ){
			double refinanceTopUpBalance = calcRefiTopupBalance();	
			boolean nonTraditionalDownPayment = checkNonTradDownPayment();	
			// Insurance is needed for the Mortgage.  The insurance amount needs to be Calculated and added to the Mortgage Amount ... 
			//insuranceCost = InsurancePremiums.calculateInsurancePremium(algoClientOpportunity.refinanceInsured, LoanToValue, hasSelfEmployedIncome, mortgageAmount, refinanceTopUpBalance, nonTraditionalDownPayment);
			// insuranceAmount = insuranceCost; Commented out because this method is also used for hypothetical max ... It can mess up the Calcs ... if the set insurance amount on the deal is changed .. 
			InsurerProducts insureProd = new InsurerProducts(this);
			insuranceCost = InsurancePremiums.calculateInsurancePremium(insureProd);
		}
		return insuranceCost;
	}

	private boolean checkNonTradDownPayment() {
		boolean nonTraditionalDownPayment = false;
		if (SelectionHelper.compareSelection(AlgoOpportunity.DownpaymentSource.Gift, clientOpportunity.downPaymentComingFrom)
				|| SelectionHelper.compareSelection(AlgoOpportunity.DownpaymentSource.Borrowed, clientOpportunity.downPaymentComingFrom))
			nonTraditionalDownPayment = true;
		else
			nonTraditionalDownPayment = false;
		return nonTraditionalDownPayment;
	}

	private double calcRefiTopupBalance() {
		double refinanceTopUpBalance = 0;
		
		if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Refinance, clientOpportunity.whatIsYourLendingGoal)){
			if (clientOpportunity.mortgageInsured == true){
				refinanceTopUpBalance = expectedMortgageAmount - clientOpportunity.currentMortgageAmount;	
			}
			
			if (refinanceTopUpBalance > 0 && clientOpportunity.mortgageInsured == true)
				algoClientOpportunity.refinanceInsured = true;
			else 
				algoClientOpportunity.refinanceInsured = false;
		}
		return refinanceTopUpBalance;
	}

	protected void calcExpectedMortgageAmount(double valueOfProperty) {
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
		    			expectedMortgageAmount =  Math.min(ltvMortgageAmount,renoCurrentAndAdditionalAmount - clientOpportunity.chargesBehindAmount);
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
	}

	protected double calcValueOfProperty() {
		double valueOfProperty;
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
		
		// Adjust for renovation considerations
		if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Prequalify, clientOpportunity.whatIsYourLendingGoal)){        
        	valueOfProperty = clientOpportunity.desiredMortgageAmount + clientOpportunity.downpaymentAmount;  
        }
        else {
        	valueOfProperty = valueOfProperty + clientOpportunity.renovationValue;
        }  
		return valueOfProperty;
	}

	protected double calcApplicantTotalAssets(Applicant applicant) {
		double ApplicantAssetTotal = 0;
		for (Asset asset : applicant.assets) {
			if (asset.value != null){
				ApplicantAssetTotal = ApplicantAssetTotal + asset.value;
			}
		}
		if (applicant.money != null)
        	ApplicantAssetTotal = ApplicantAssetTotal + Double.parseDouble(applicant.money);
		
		return ApplicantAssetTotal;
	}

	protected void checkNonResident(Applicant applicant) {
		if (applicant.nonResident == true){
			if (potentialProduct.nonResidentAllow == true)
				countOfAcceptableCritera++;
			else{
				countOfUnacceptableCriteria++;
				failReasons.add("This product does not allow non-resident Borrowers.");
			    failedNotesCounter++;
			}
		}
	}

	protected void checkOrderlyDebtPayment(Applicant applicant) {
		if (applicant.orderlyDebtPayment == true){
			if (applicant.odpDischargeDate != null){
		    	Calendar dateNow = Calendar.getInstance();
		    	int MonthsAgo = Integer.valueOf(potentialProduct.mthsFromDischargeAllowable.intValue()) * -1;
		        dateNow.add(Calendar.MONTH, MonthsAgo);
		        if (applicant.odpDischargeDate.after(dateNow.getTime())){
		        	countOfUnacceptableCriteria++;
		        	failReasons.add("The time since ODP is not long enough for this product.");
				    failedNotesCounter++;
		        }
		    }	
			else {
				countOfQuestionableCriteria++;
			}
		}	        	
		else {
			countOfAcceptableCritera++;
		}
	}

	protected void checkBankruptcy(Applicant applicant) {
		
		int countOfLiabilities = 0;
		
		if (applicant.liabilities != null && applicant.liabilities.size() > 0){
			for (Liability liability : applicant.liabilities) {
				
				if (liability != null){
					boolean liabilityOpen = checkLiabilityOpen(liability);
	
					// Check if Fixed or Revolving or LOC
					if (liabilityOpen == true)
						countOfLiabilities = countOfLiabilities + 1;	
				}
			}
		}
		
		if (applicant.bankruptcy == true){
			if (applicant.bankruptcyDischargeDate != null){
				Calendar dateNow = Calendar.getInstance();
		    	int MonthsAgo = Integer.valueOf(potentialProduct.mthsFromDischargeAllowable.intValue()) * -1;
		        dateNow.add(Calendar.MONTH, MonthsAgo);
		        if (applicant.bankruptcyDischargeDate.after(dateNow.getTime()) || countOfLiabilities < potentialProduct.activeCreditTradesRequired){
		        	countOfUnacceptableCriteria++;
		        	countOfUnacceptableCriteria++;
		        	countOfUnacceptableCriteria++;
		        	failReasons.add("The time since bankruptcy is not long enough for this product.");
				    failedNotesCounter++;
		        }
		        String bankruptcyDischarge = applicant.applicantName + " had a bankruptcy discharged on " + applicant.bankruptcyDischargeDate + "/n";
				listOfLenderNotes.append(bankruptcyDischarge);
		    }	
			else {
				countOfQuestionableCriteria++;
				
				String bankruptcyDischarge = "Please note " + applicant.applicantName + " had a bankruptcy/n";
				listOfLenderNotes.append(bankruptcyDischarge);	
			}
		}	        	
		else {
			countOfAcceptableCritera++;
		}
	}

	protected double calcNetMortgagePayment(Applicant applicant, int PropertyID,
			Mortgage currenMortgage, int IncomePropertyID) {
		double netMortgagesPayments = 0;
		if (applicant.incomes != null && applicant.incomes.size() > 0){ 
			netMortgagesPayments = calcPropertyNetIncome(applicant,PropertyID,
					currenMortgage,IncomePropertyID,netMortgagesPayments);
		}
		return netMortgagesPayments;
	}

	protected double calcPropertyNetIncome(Applicant applicant, int PropertyID,
			Mortgage currenMortgage, int IncomePropertyID,
			double netMortgagesPayments) {
		double netIncomeFromProperty = 0;
		for (Income currentIncome : applicant.incomes) {
			if (currentIncome.propertyID != null)
				IncomePropertyID = Integer.parseInt(currentIncome.propertyID);
			
			if (IncomePropertyID == PropertyID && currenMortgage.selling == false){
				double allowableRentalIncome = ((Double.parseDouble(currentIncome.annualIncome) * (potentialProduct.rentalIncomePercent / 100)));
				double MortgagePayment = Double.parseDouble(currenMortgage.monthlyPayment);
				netIncomeFromProperty = (allowableRentalIncome / 12) - MortgagePayment;
				if (netIncomeFromProperty < 0){
					netMortgagesPayments = netMortgagesPayments + (netIncomeFromProperty * -1);  
				}
				// In the event net cashflow is negative, the net cashflow is added to Mortgage Payments
			}
		}
		return netMortgagesPayments;
	}

	protected void countLenderMortgages(List<String> lenderNames,
			Mortgage currenMortgage) {
		int countOfThisLender = 0;
		for (String lenderName : lenderNames) {
			if (lenderName.contains(currenMortgage.name.toLowerCase().trim()))
				countOfThisLender = countOfThisLender + 1;
		}
		if (countOfThisLender > potentialProduct.maxAllowProperty){
			countOfUnacceptableCriteria++;
		    AlgoNote Note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "The client already has too many mortgages with " + currenMortgage.name, "FailedApplication" + failedNotesCounter);
		    dealNotes.add(Note1);
		    failReasons.add("The client already has too many mortgages with " + currenMortgage.name);
		    failedNotesCounter++;
		}
	}

	protected int getMortgageID(Mortgage currenMortgage) {
		int MortgageID = 1;
		if (currenMortgage.propertyId != null)
			MortgageID = Integer.parseInt(currenMortgage.propertyId);
		return MortgageID;
	}

	protected int getPropertyID(Property currentProperty) {
		int PropertyID = 0;
		if (currentProperty.propertyId != null)
			PropertyID = Integer.parseInt(currentProperty.propertyId);
		return PropertyID;
	}

	protected boolean checkIncludeProperty(Property currentProperty) {
		boolean includePropertyInCalculatons = true;
		if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Refinance, clientOpportunity.whatIsYourLendingGoal)){
			if (clientOpportunity.propertyValue == currentProperty.value 
					&& (double)currentProperty.annualTax == clientOpportunity.propertyTaxes){
				includePropertyInCalculatons = false;
			}
		}
		return includePropertyInCalculatons;
	}

	protected double sumCollections(Applicant applicant) {
		double applicantCollectionsBalance = 0;	        		            
		String listOfCollections = "";      
		if (applicant.collection != null){ 
			if (applicant.collection.size() > 0){
				for (Collection collect : applicant.collection) {
					if (collect.name != null && collect.balance != null){
						listOfCollections += collect.name + ": " + collect.balance + ", ";	
					}
					
					if (collect.balance != null && collect.payOff != null){
						double balanceOwing = (double)collect.balance - collect.payOff;
						if (balanceOwing > 0){
							applicantCollectionsBalance = applicantCollectionsBalance + balanceOwing;	
						}
					}
					else if (collect.balance != null && (double)collect.balance > 0){
						applicantCollectionsBalance = applicantCollectionsBalance + (double)collect.balance;	
					}
					
					if (collect.name != null && collect.balance != null && collect.status != null){
						String collectionNote = applicant.applicantName + " collection: " + collect.name + ", Balance: " + collect.balance + ", Status:" + collect.status + " /n";
						listOfLenderNotes.append(collectionNote);
					}
					else if (collect.name != null && collect.balance != null){
						String collectionNote = applicant.applicantName + " collection: " + collect.name + ", Balance: " + collect.balance + " /n";
						listOfLenderNotes.append(collectionNote);
					} 
					else if (collect.name != null){
						String collectionNote = applicant.applicantName + " collection: " + collect.name + "/n";
						listOfLenderNotes.append(collectionNote);
					} 
					
				}
		    	AlgoNote AssistNote3 = new AlgoNote(AlgoNote.TypeOfNote.AssistantAction, AlgoNote.Priority.Med, "In the debt payments email to " + applicant.applicantName+ " please include a section requesting an explanation of the status of the following outstanding Collections: " + listOfCollections);
		        assistantNotes.add(AssistNote3);
			}		        	
		}
		return applicantCollectionsBalance;
	}

	protected boolean checkLiabilityOpen(Liability currentLiability) {
		boolean liabilityOpen = true;
		if (currentLiability.status != null){
			if (currentLiability.status.toLowerCase().contains("paid") == true
					|| currentLiability.status.toLowerCase().contains("sold") == true
					|| currentLiability.status.toLowerCase().contains("written") == true
					|| currentLiability.status.toLowerCase().contains("closed") == true
					|| currentLiability.status.toLowerCase().contains("lost") == true
					|| currentLiability.status.toLowerCase().contains("refinance") == true)
				liabilityOpen = false;	
		}
		return liabilityOpen;
	}
	
	protected boolean checkIfCellCompany(Liability currentLiability) {
		boolean cellCompanyBool = false;
		if (currentLiability.business != null){
			for (String cellCompany : mobileProviders) {
				if (currentLiability.business.toLowerCase().contains(cellCompany.toLowerCase()))
					cellCompanyBool = true;
			}			
		}
		return cellCompanyBool;
	}

	protected boolean isLiabilityAlreadyAdded(List<Liability> totalLiabilitiesList,
			Liability currentLiability) {
		boolean alreadyadded = false;
		for (Liability jointliability : totalLiabilitiesList)
		{
			if (currentLiability.name.equals(jointliability.name)
					&& currentLiability.creditBalance.equals(jointliability.creditBalance)
					&& currentLiability.creditLimit.equals(jointliability.creditLimit))
				alreadyadded = true;	            					
		}
		return alreadyadded;
	}

	protected double calcLiabilityPayment(Liability currentLiability,
			double revisedBalance) {
		double calculateLiabilityPayment = 0;  
		if (currentLiability.type != null){
			if (currentLiability.type.toLowerCase().trim().contains("loc")){
				calculateLiabilityPayment = Math.max(10,Double.parseDouble(currentLiability.monthlyPayment));
			}
			else if (currentLiability.type.trim().equalsIgnoreCase("R")){
				if (SelectionHelper.compareSelection(AlgoProduct.LiabilityCalculatonMethod.StatedMinimum, potentialProduct.revolvingDebtPayments))
		            calculateLiabilityPayment = Math.max(10,Double.parseDouble(currentLiability.monthlyPayment));
		        else if (SelectionHelper.compareSelection(AlgoProduct.LiabilityCalculatonMethod.ThreePercentBalance, potentialProduct.revolvingDebtPayments))
		        	calculateLiabilityPayment = revisedBalance * 0.03;
		        else if (SelectionHelper.compareSelection(AlgoProduct.LiabilityCalculatonMethod.ThreePercentLimit, potentialProduct.revolvingDebtPayments))
		        	calculateLiabilityPayment = currentLiability.creditLimit * 0.03;				
			}
			else if (currentLiability.type.trim().equalsIgnoreCase("I")){
				if (currentLiability.monthlyPayment != null){
					calculateLiabilityPayment = Double.parseDouble(currentLiability.monthlyPayment);
				}    		            					
			}
			else if (currentLiability.type.trim().equalsIgnoreCase("O")){
				if (SelectionHelper.compareSelection(AlgoProduct.LiabilityCalculatonMethod.StatedMinimum, potentialProduct.revolvingDebtPayments))
		            calculateLiabilityPayment = Math.max(10,Double.parseDouble(currentLiability.monthlyPayment));
		        else if (SelectionHelper.compareSelection(AlgoProduct.LiabilityCalculatonMethod.ThreePercentBalance, potentialProduct.revolvingDebtPayments))
		        	calculateLiabilityPayment = revisedBalance * 0.03;
		        else if (SelectionHelper.compareSelection(AlgoProduct.LiabilityCalculatonMethod.ThreePercentLimit, potentialProduct.revolvingDebtPayments))
		        	calculateLiabilityPayment = currentLiability.creditLimit * 0.03;	 		            					
			}
			else{
				if (currentLiability.monthlyPayment != null){
					calculateLiabilityPayment = Double.parseDouble(currentLiability.monthlyPayment);
				}  
			}
		}
		else{
			calculateLiabilityPayment = Double.parseDouble(currentLiability.monthlyPayment);
		}
		return calculateLiabilityPayment;
	}

	protected double calcApplicantIncome(Applicant applicant, Income currentIncome) {
		double revisedCurrentIncome = 0;
		
		if (currentIncome.annualIncome != null){
			if (SelectionHelper.compareSelection(AlgoIncome.IncomeType.Employed, currentIncome.typeOfIncome)){
				hasEmployedIncome = true;
				revisedCurrentIncome = Double.parseDouble(currentIncome.annualIncome);
			}
			else if (SelectionHelper.compareSelection(AlgoIncome.IncomeType.SelfEmployed, currentIncome.typeOfIncome)){
				hasSelfEmployedIncome = true;				
				// Concept here is to only take last year Current Income.  Depending on Product Selection and GDS need, this can then be changed later by Subtracting
				if (currentIncome.business.contains("Current")){
					revisedCurrentIncome = Double.parseDouble(currentIncome.annualIncome);	        			        		
				}
				else if (currentIncome.business.contains("Prior")){
					// Blank on Purpose to not Double count Income	        			        		
				}
				else{
					// Current and Prior Year have not been labelled so take the income as is.
					revisedCurrentIncome = Double.parseDouble(currentIncome.annualIncome);
				}
			}
			else if (SelectionHelper.compareSelection(AlgoIncome.IncomeType.Rental, currentIncome.typeOfIncome)){
				if (SelectionHelper.compareSelection(AlgoProduct.TreatmentOfRental.AddToIncome, potentialProduct.rentalIncomeTreatment))
			    {
					revisedCurrentIncome = ((Double.parseDouble(currentIncome.annualIncome) * (potentialProduct.rentalIncomePercent / 100)));
			    }
				else {    			    						
					double allowableRentalIncome = ((Double.parseDouble(currentIncome.annualIncome) * (potentialProduct.rentalIncomePercent / 100)));
					// Note, in the event netIncomeFromPrperty is negative, it is added to liabilities rather than added to income ... See Mortgages Loop for this logic ...
					revisedCurrentIncome = calcNetIncomeFromProperty(applicant,	revisedCurrentIncome, currentIncome, allowableRentalIncome);	        	    								    			    						
				}
			}
			else if (SelectionHelper.compareSelection(AlgoIncome.IncomeType.ChildTaxCredit, currentIncome.typeOfIncome)){				
				revisedCurrentIncome = Double.parseDouble(currentIncome.annualIncome) * (potentialProduct.childTaxCredit / 100);
			}
			else if (SelectionHelper.compareSelection(AlgoIncome.IncomeType.LivingAllowance, currentIncome.typeOfIncome)){
				revisedCurrentIncome = Double.parseDouble(currentIncome.annualIncome) * (potentialProduct.livingAllowance / 100);		    					
			}
			else if (SelectionHelper.compareSelection(AlgoIncome.IncomeType.VehicleAllowance, currentIncome.typeOfIncome)){
				revisedCurrentIncome = Double.parseDouble(currentIncome.annualIncome) * (potentialProduct.vehicleAllowance / 100);		    					
			} 
			else if (SelectionHelper.compareSelection(AlgoIncome.IncomeType.Bonus, currentIncome.typeOfIncome)){
				revisedCurrentIncome = Double.parseDouble(currentIncome.annualIncome);		    					
			} 
			else if (SelectionHelper.compareSelection(AlgoIncome.IncomeType.Commission, currentIncome.typeOfIncome)){
				revisedCurrentIncome = Double.parseDouble(currentIncome.annualIncome);		    					
			}
			else if (SelectionHelper.compareSelection(AlgoIncome.IncomeType.Interest, currentIncome.typeOfIncome)){
				revisedCurrentIncome = Double.parseDouble(currentIncome.annualIncome);		    					
			}  
			else if (SelectionHelper.compareSelection(AlgoIncome.IncomeType.Overtime, currentIncome.typeOfIncome)){
				revisedCurrentIncome = Double.parseDouble(currentIncome.annualIncome);		    					
			}  
			else if (SelectionHelper.compareSelection(AlgoIncome.IncomeType.Pension, currentIncome.typeOfIncome)){
				revisedCurrentIncome = Double.parseDouble(currentIncome.annualIncome);		    					
			}  
			else if (SelectionHelper.compareSelection(AlgoIncome.IncomeType.Retired, currentIncome.typeOfIncome)){
				revisedCurrentIncome = Double.parseDouble(currentIncome.annualIncome);		    					
			}
			else {
				revisedCurrentIncome = Double.parseDouble(currentIncome.annualIncome);		    									
			}
		}
		
		return revisedCurrentIncome;
	}

	protected double setMaxEmployedMonths(double MaxEmployedMonths,
			Income currentIncome) {
		if ((double)currentIncome.month > MaxEmployedMonths)
			MaxEmployedMonths = currentIncome.month;
		return MaxEmployedMonths;
	}

	protected double calcNetIncomeFromProperty(Applicant applicant,
			double totalApplicantIncome, Income currentIncome,
			double allowableRentalIncome) {
		double netIncomeFromProperty = 0;
		int PropertyID = 0;
		if (currentIncome.propertyID != null)
			PropertyID = Integer.parseInt(currentIncome.propertyID);
		if (applicant.mortgages != null && applicant.mortgages.size() > 0){ 
			for (Mortgage currenMortgage : applicant.mortgages) {
				
				int MortgageID = getMortgageID(currenMortgage);
				
				if (MortgageID == PropertyID && currenMortgage.selling == false){
					
					double MortgagePayment = Double.parseDouble(currenMortgage.monthlyPayment);
					netIncomeFromProperty = allowableRentalIncome - (MortgagePayment * 12);
				}
			}
		}
		if (netIncomeFromProperty > 0)
			totalApplicantIncome = totalApplicantIncome + netIncomeFromProperty;
		return totalApplicantIncome;
	}

	 boolean passFailTDS(double allowableRatioForTDS) {
		 boolean failedTDS = false;
		 if (TDSRatio >= allowableRatioForTDS){
			 failedTDS = true;
		 }
		 return failedTDS;
	 }

	 boolean passFailGDS(double allowableRatioForGDS) {
		 boolean failedGDS = false;
		 if (GDSRatio >= allowableRatioForGDS){
			 failedGDS = true;                	
		 }
		 return failedGDS;
	 }

	double maxMortgageWithCondoBasedOnTDS(double totalDealIncome, double totalDealMortgagesPayments, double totalDealLiabilities, 
			double totalDealTaxes, double totalDealHeat, double allowableRatioForTDS, double rateToUse) {
		double maxMortgage = 0;
		double MaxTDSAmountNoCondo = totalDealIncome * allowableRatioForTDS / 100;
		double amountToSubtractFromTDS = 0;
		if (clientOpportunity.condoFees > 0){
			amountToSubtractFromTDS = totalDealLiabilities + (clientOpportunity.condoFees * 12); // Subtract the Deal Liabilities and Condo fees to be left with the max which can be spent on a mortgage
			amountToSubtractFromTDS = amountToSubtractFromTDS + totalDealTaxes;
			amountToSubtractFromTDS = amountToSubtractFromTDS + totalDealHeat;
		}                    
		else{
			amountToSubtractFromTDS = totalDealLiabilities + (clientOpportunity.propertyValue * 0.01);
			amountToSubtractFromTDS = amountToSubtractFromTDS + totalDealTaxes;
			amountToSubtractFromTDS = amountToSubtractFromTDS + totalDealHeat;
		}		
		double maxAnnualPaymentNoCondo = MaxTDSAmountNoCondo - amountToSubtractFromTDS - (totalDealMortgagesPayments * 12);   
		double maxMonthlyPaymentNoCondo = maxAnnualPaymentNoCondo / 12;
		double maximumMortgageNoInsurance = CalculatePayments.calculateMaximumMortgageAmount(rateToUse, 25, maxMonthlyPaymentNoCondo);
		
		// If preapproval ... 1 path for determining if insured ... if refinance or purchase, use property value.
		boolean insureMaximumMortgageNoInsurance = false;
		if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Prequalify, clientOpportunity.whatIsYourLendingGoal)){
			insureMaximumMortgageNoInsurance = checkIfHighRatio(maximumMortgageNoInsurance + clientOpportunity.downpaymentAmount, maximumMortgageNoInsurance);	
		}
		else{
			insureMaximumMortgageNoInsurance = checkIfHighRatio(clientOpportunity.propertyValue, maximumMortgageNoInsurance);
		}		
		
		double insureMaxMortgageAmount = 0;
		double maxMortgageLTV = (maximumMortgageNoInsurance / (maximumMortgageNoInsurance + clientOpportunity.downpaymentAmount)) * 100;
		if (insureMaximumMortgageNoInsurance = true){
			InsurerProducts insureProd = new InsurerProducts(this);
			insureMaxMortgageAmount = InsurancePremiums.calculateInsurancePremium(insureProd);
			// insureMaxMortgageAmount = InsurancePremiums.calculateInsurancePremium(false, maxMortgageLTV, hasSelfEmployedIncome, maximumMortgageNoInsurance, 0, false);
		}
		else{
			insureMaxMortgageAmount = 0;
		}
		maxMortgage = maximumMortgageNoInsurance - insureMaxMortgageAmount;
		return maxMortgage;
	}

	double maxMortgageNoCondoBasedOnTDS(double totalDealIncome, double totalDealMortgagesPayments, double totalDealLiabilities, 
			double totalDealTaxes, double totalDealHeat, double allowableRatioForTDS, double rateToUse) {
		double maxMortgage = 0;
		double MaxTDSAmountNoCondo = totalDealIncome * allowableRatioForTDS / 100;
		double amountToSubtractFromTDS = 0;
		if (clientOpportunity.condoFees > 0){
			amountToSubtractFromTDS = totalDealLiabilities + (clientOpportunity.condoFees * 12); // Subtract the Deal Liabilities and Condo fees to be left with the max which can be spent on a mortgage
			amountToSubtractFromTDS = amountToSubtractFromTDS + totalDealTaxes;
			amountToSubtractFromTDS = amountToSubtractFromTDS + totalDealHeat;
		}                    
		else{
			amountToSubtractFromTDS = totalDealLiabilities; /// Was missing * 12 TODO .... Probelm here ... How is it accounted for? Refinance? Purchase? 
			amountToSubtractFromTDS = amountToSubtractFromTDS + totalDealTaxes;
			amountToSubtractFromTDS = amountToSubtractFromTDS + totalDealHeat;
		}		
		double maxAnnualPaymentNoCondo = MaxTDSAmountNoCondo - amountToSubtractFromTDS - (totalDealMortgagesPayments * 12);   
		double maxMonthlyPaymentNoCondo = maxAnnualPaymentNoCondo / 12;
		double maximumMortgageNoInsurance = CalculatePayments.calculateMaximumMortgageAmount(rateToUse, 25, maxMonthlyPaymentNoCondo);

		// If preapproval ... 1 path for determining if insured ... if refinance or purchase, use property value.
		boolean insureMaximumMortgageNoInsurance = false;
		if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Prequalify, clientOpportunity.whatIsYourLendingGoal)){
			insureMaximumMortgageNoInsurance = checkIfHighRatio(maximumMortgageNoInsurance + clientOpportunity.downpaymentAmount, maximumMortgageNoInsurance);	
		}
		else{
			insureMaximumMortgageNoInsurance = checkIfHighRatio(clientOpportunity.propertyValue, maximumMortgageNoInsurance);
		}		
				
		double insureMaxMortgageAmount = 0;
		double maxMortgageLTV = (maximumMortgageNoInsurance / (maximumMortgageNoInsurance + clientOpportunity.downpaymentAmount)) * 100;
		if (insureMaximumMortgageNoInsurance = true){
			InsurerProducts insureProd = new InsurerProducts(this);
			insureMaxMortgageAmount = InsurancePremiums.calculateInsurancePremium(insureProd);
			//insureMaxMortgageAmount = InsurancePremiums.calculateInsurancePremium(false, maxMortgageLTV, hasSelfEmployedIncome, maximumMortgageNoInsurance, 0, false);
		}
		else{
			insureMaxMortgageAmount = 0;
		}
		maxMortgage = maximumMortgageNoInsurance - insureMaxMortgageAmount;
		return maxMortgage;
	}

	double maxMortgageWithCondoBasedOnGDS(double totalDealIncome, double totalDealMortgagesPayments, double totalDealLiabilities, 
			double totalDealTaxes, double totalDealHeat, double allowableRatioForGDS, double rateToUse) {
		double maxMortgage = 0;
		double MaxGDSAmountNoCondo = totalDealIncome * allowableRatioForGDS / 100;
		double amountToSubtractFromMaxGDS = 0;
		if (clientOpportunity.condoFees > 0){
			amountToSubtractFromMaxGDS = clientOpportunity.condoFees * 12; // Subtract the Deal Liabilities and Condo fees to be left with the max which can be spent on a mortgage
			amountToSubtractFromMaxGDS = amountToSubtractFromMaxGDS + totalDealTaxes;
			amountToSubtractFromMaxGDS = amountToSubtractFromMaxGDS + totalDealHeat;
		}                    
		else{	
			amountToSubtractFromMaxGDS = clientOpportunity.propertyValue * 0.01;
			amountToSubtractFromMaxGDS = amountToSubtractFromMaxGDS + totalDealTaxes;
			amountToSubtractFromMaxGDS = amountToSubtractFromMaxGDS + totalDealHeat;
		}		
		double maxAnnualPaymentNoCondo = MaxGDSAmountNoCondo - amountToSubtractFromMaxGDS;   
		double maxMonthlyPaymentNoCondo = maxAnnualPaymentNoCondo / 12;
		double maximumMortgageNoInsurance = CalculatePayments.calculateMaximumMortgageAmount(rateToUse, 25, maxMonthlyPaymentNoCondo);

		// If preapproval ... 1 path for determining if insured ... if refinance or purchase, use property value.
		boolean insureMaximumMortgageNoInsurance = false;
		if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Prequalify, clientOpportunity.whatIsYourLendingGoal)){
			insureMaximumMortgageNoInsurance = checkIfHighRatio(maximumMortgageNoInsurance + clientOpportunity.downpaymentAmount, maximumMortgageNoInsurance);	
		}
		else{
			insureMaximumMortgageNoInsurance = checkIfHighRatio(clientOpportunity.propertyValue, maximumMortgageNoInsurance);
		}		
		
		double insureMaxMortgageAmount = 0;
		double maxMortgageLTV = (maximumMortgageNoInsurance / (maximumMortgageNoInsurance + clientOpportunity.downpaymentAmount)) * 100;
		if (insureMaximumMortgageNoInsurance = true){
			//insureMaxMortgageAmount = InsurancePremiums.calculateInsurancePremium(false, maxMortgageLTV, hasSelfEmployedIncome, maximumMortgageNoInsurance, 0, false);
			InsurerProducts insureProd = new InsurerProducts(this);
			insureMaxMortgageAmount = InsurancePremiums.calculateInsurancePremium(insureProd);
		}
		else{
			insureMaxMortgageAmount = 0;
		}
		maxMortgage = maximumMortgageNoInsurance - insureMaxMortgageAmount;
		return maxMortgage;
	}

	double maxMortgageNoCondoBasedOnGDS(double totalDealIncome, double totalDealMortgagesPayments, double totalDealLiabilities, 
			double totalDealTaxes, double totalDealHeat, double allowableRatioForGDS, double rateToUse) {
		double maxMortgage = 0;
		double MaxGDSAmountNoCondo = totalDealIncome * allowableRatioForGDS / 100;
		double amountToSubtractFromMaxGDS = 0;
		if (clientOpportunity.condoFees > 0){
			amountToSubtractFromMaxGDS = clientOpportunity.condoFees * 12; // Subtract the Deal Liabilities and Condo fees to be left with the max which can be spent on a mortgage
			amountToSubtractFromMaxGDS = amountToSubtractFromMaxGDS + totalDealTaxes;
			amountToSubtractFromMaxGDS = amountToSubtractFromMaxGDS + totalDealHeat;
		}                    
		else{			
			amountToSubtractFromMaxGDS = amountToSubtractFromMaxGDS + totalDealTaxes;
			amountToSubtractFromMaxGDS = amountToSubtractFromMaxGDS + totalDealHeat;
		}		
		double maxAnnualPaymentNoCondo = MaxGDSAmountNoCondo - amountToSubtractFromMaxGDS;   
		double maxMonthlyPaymentNoCondo = maxAnnualPaymentNoCondo / 12;
		double maximumMortgageNoInsurance = CalculatePayments.calculateMaximumMortgageAmount(rateToUse, 25, maxMonthlyPaymentNoCondo);

		// If preapproval ... 1 path for determining if insured ... if refinance or purchase, use property value.
		boolean insureMaximumMortgageNoInsurance = false;
		if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Prequalify, clientOpportunity.whatIsYourLendingGoal)){
			insureMaximumMortgageNoInsurance = checkIfHighRatio(maximumMortgageNoInsurance + clientOpportunity.downpaymentAmount, maximumMortgageNoInsurance);	
		}
		else{
			insureMaximumMortgageNoInsurance = checkIfHighRatio(clientOpportunity.propertyValue, maximumMortgageNoInsurance);
		}		
		
		double insureMaxMortgageAmount = 0;
		double maxMortgageLTV = (maximumMortgageNoInsurance / (maximumMortgageNoInsurance + clientOpportunity.downpaymentAmount)) * 100;
		if (insureMaximumMortgageNoInsurance = true){
			InsurerProducts insureProd = new InsurerProducts(this);
			insureMaxMortgageAmount = InsurancePremiums.calculateInsurancePremium(insureProd);
			// insureMaxMortgageAmount = InsurancePremiums.calculateInsurancePremium(false, maxMortgageLTV, hasSelfEmployedIncome, maximumMortgageNoInsurance, 0, false);
		}
		else{
			insureMaxMortgageAmount = 0;
		}
		maxMortgage = maximumMortgageNoInsurance - insureMaxMortgageAmount;
		return maxMortgage;
	}

	double rateToUse(double productInterestRate, double interestRateVar) {
		double rate = 0;
		if (SelectionHelper.compareSelection(AlgoProduct.MortgageType.LOC, potentialProduct.mortgageType)){
			rate = interestRateVar;
		}
		else if (SelectionHelper.compareSelection(AlgoProduct.MortgageType.Fixed, potentialProduct.mortgageType)){
			if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year5, potentialProduct.term)){
				rate = productInterestRate;
		    }
			else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year6, potentialProduct.term)){
				rate = productInterestRate;
		    }
			else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year7, potentialProduct.term)){
				rate = productInterestRate;
		    }
			else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year8, potentialProduct.term)){
				rate = productInterestRate;
		    }
			else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year9, potentialProduct.term)){
				rate = productInterestRate;
		    }
			else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year10, potentialProduct.term)){
				rate = productInterestRate;
		    }
			else {
				rate = interestRateVar;		
			}
		}
		else {
			rate = interestRateVar;
		}
		return rate;
	}
	
	protected void checkDownpayment(double maxMortgageAllowed, double propertyValue, double downpayment) {
		if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Prequalify, clientOpportunity.whatIsYourLendingGoal)
				|| (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Approval, clientOpportunity.whatIsYourLendingGoal))){
			double simpleExpectedMortgage = propertyValue - downpayment;
		    if (simpleExpectedMortgage <= maxMortgageAllowed)
		        countOfAcceptableCritera++;
		    else
		    {
		        countOfUnacceptableCriteria++;
		        countOfUnacceptableCriteria++;
		        countOfUnacceptableCriteria++;
		        double requiredDownpayment = 0;
		        if (propertyValue <= 500000){
		        	requiredDownpayment = propertyValue * 0.05;	
				}
				else {
					double valueOver500 = propertyValue - 500000;
					double downpayOver500 = valueOver500 * 0.1;
					double downpayUnder500 = (500000 * 0.05);
					requiredDownpayment = downpayOver500 + downpayUnder500;					
				}
		        
		        AlgoNote note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "The downpayment is not large enough for the Property Value.  "
		        		+ "For a property valued at " + propertyValue + "Applicant needs a downpayment of " + requiredDownpayment, "FailedApplication" + failedNotesCounter);
		        dealNotes.add(note1);
		        failReasons.add("The downpayment is not large enough for the Property Value. "
		        		+ "For a property valued at " + propertyValue + "Applicant needs a downpayment of " + requiredDownpayment);
		        failedNotesCounter++;
		    }
		}
	}

	double setMaxMortgageWithLTV(double valueOfProperty) {
		double MaxMortgageAllowed = 0;
		double maximumAllowedLTV = 0;
		if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Refinance, clientOpportunity.whatIsYourLendingGoal)){
			maximumAllowedLTV = insuredThresholdForProperty / valueOfProperty;
			MaxMortgageAllowed = valueOfProperty * maximumAllowedLTV; 
			MaxMortgageAllowed =  Math.max(MaxMortgageAllowed,clientOpportunity.currentMortgageAmount);
		}
		else if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Prequalify, clientOpportunity.whatIsYourLendingGoal)){
			if (SelectionHelper.compareSelection(AlgoOpportunity.Looking.CottageRecProperty, clientOpportunity.preapprovedImLookingFora)
		    		|| SelectionHelper.compareSelection(AlgoOpportunity.Looking.RentalProperty, clientOpportunity.preapprovedImLookingFora)){
				maximumAllowedLTV = 0.8;
			}
			else if (SelectionHelper.compareSelection(AlgoOpportunity.Looking.RawLandLeasedLand, clientOpportunity.preapprovedImLookingFora)){
				maximumAllowedLTV = 0.65;
			}
			else{
				if (valueOfProperty <= 500000){
					maximumAllowedLTV = 0.95;	
				}
				else {
					double valueOver500 = valueOfProperty - 500000;
					double ltvAmountOver500 = valueOver500 * 0.9;
					double ltvAmountUnder500 = (500000 * 0.95);
					maximumAllowedLTV = (ltvAmountUnder500 + ltvAmountOver500) / valueOfProperty;
				}
				
			}
			MaxMortgageAllowed = valueOfProperty * maximumAllowedLTV;
		}
		else{
			if (valueOfProperty <= 500000){
				maximumAllowedLTV = 0.95;	
			}
			else {
				double valueOver500 = valueOfProperty - 500000;
				double ltvAmountOver500 = valueOver500 * 0.9;
				double ltvAmountUnder500 = (500000 * 0.95);
				maximumAllowedLTV = (ltvAmountUnder500 + ltvAmountOver500) / valueOfProperty;
			}
			MaxMortgageAllowed = valueOfProperty * maximumAllowedLTV;
		}
		return MaxMortgageAllowed;
	}

	double setMaxAllowedGDS(double beaconScoreToUse) {
		double allowableRatioForGDS;
		if (OpportunityCreditCategory == AlgoApplicant.CreditCategory.Good)
		{
		    if (beaconScoreToUse >= potentialProduct.beaconTdsSplit)
		    {
		        allowableRatioForGDS = potentialProduct.greatGdsGreaterEqualSplit; // Set Max GDS
		    }
		    else
		    {
		        allowableRatioForGDS = potentialProduct.greatGdsLessSplit; // Set Max GDS
		    }
		}
		else if (OpportunityCreditCategory == AlgoApplicant.CreditCategory.Medium)
		{
		    if (beaconScoreToUse >= potentialProduct.beaconTdsSplit)
		    {
		        allowableRatioForGDS = potentialProduct.medGdsGreaterEqualSplit; // Set Max GDS
		    }
		    else
		    {
		        allowableRatioForGDS = potentialProduct.medGdsLessSplit; // Set Max GDS
		    }
		}
		else
		{
		    if (beaconScoreToUse >= potentialProduct.beaconTdsSplit)
		    {
		        allowableRatioForGDS = potentialProduct.poorGdsGreaterEqualSplit; // Set Max GDS
		    }
		    else
		    {
		        allowableRatioForGDS = potentialProduct.poorGdsLessSplit; // Set Max GDS
		    }
		}
		return allowableRatioForGDS;
	}

	double setMaxAllowedTDS(double beaconScoreToUse) {
		double allowableRatioForTDS;
		if (OpportunityCreditCategory == AlgoApplicant.CreditCategory.Good)
		{
		    if (beaconScoreToUse >= potentialProduct.beaconTdsSplit)
		    {
		        allowableRatioForTDS = potentialProduct.greatTdsGreaterEqualSplit; // Set Max TDS
		    }
		    else
		    {
		        allowableRatioForTDS = potentialProduct.greatTdsLessSplit; // Set Max TDS
		    }
		}
		else if (OpportunityCreditCategory == AlgoApplicant.CreditCategory.Medium)
		{
		    if (beaconScoreToUse >= potentialProduct.beaconTdsSplit)
		    {
		        allowableRatioForTDS = potentialProduct.medTdsGreaterEqualSplit; // Set Max TDS
		    }
		    else
		    {
		        allowableRatioForTDS = potentialProduct.medTdsLessSplit; // Set Max TDS
		    }
		}
		else
		{
		    if (beaconScoreToUse >= potentialProduct.beaconTdsSplit)
		    {
		        allowableRatioForTDS = potentialProduct.poorTdsGreaterEqualSplit; // Set Max TDS
		    }
		    else
		    {
		        allowableRatioForTDS = potentialProduct.poorTdsLessSplit; // Set Max TDS
		    }
		}
		return allowableRatioForTDS;
	}

	double beaconScore2Use() {
		double beaconScoreToUse = 0;
		if (AlgoProduct.WhoseIncomeUsed.Averaged.equals(algoPotentialProduct.whoseBeaconUsed))
		{
		    int sumBeacons = 0;
		    int applicantCount = 0;
		    for (Applicant applicant : clientOpportunity.applicants)
		    {
		    	AlgoApplicant aolgoApplicant = new AlgoApplicant(applicant);
		        if (applicant.includeInOpportunity == true)
		        {                    
		        	if (SelectionHelper.compareSelection(AlgoProduct.EquifaxScoring.Beacon5, potentialProduct.equifaxScoringUsed))
		        		applicant.beaconScore = applicant.beacon5Score;
		        	else if (SelectionHelper.compareSelection(AlgoProduct.EquifaxScoring.Beacon9, potentialProduct.equifaxScoringUsed))
		        		applicant.beaconScore = applicant.beacon9Score;
		        	else if (SelectionHelper.compareSelection(AlgoProduct.EquifaxScoring.CRP3, potentialProduct.equifaxScoringUsed))
		        		applicant.beaconScore = applicant.crp3Score;
		        	else if (SelectionHelper.compareSelection(AlgoProduct.EquifaxScoring.ERS2, potentialProduct.equifaxScoringUsed))
		        		applicant.beaconScore = applicant.ers2Score;
		        	
		            sumBeacons += applicant.beaconScore;
		            applicantCount++;
		            
		            String beaconName = applicant.applicantName + "'s beacon score: " + applicant.beaconScore + "/n";
					listOfLenderNotes.append(beaconName);	
		        }
		    }
		    beaconScoreToUse = Math.round((double)sumBeacons / applicantCount); //Math.round((double)SumBeacons / ApplicantCount, 0);
		    
		}
		else if (AlgoProduct.WhoseIncomeUsed.Highest.equals(algoPotentialProduct.whoseBeaconUsed))
		{
		    int MaxTempBeacon = 0;
		    for (Applicant applicant : clientOpportunity.applicants)
		    {
		    	
		    	AlgoApplicant aolgoApplicant = new AlgoApplicant(applicant);
		    	//System.out.println(applicant.includeInOpportunity);
		        if (applicant.includeInOpportunity == true)
		        {
		        	if (SelectionHelper.compareSelection(AlgoProduct.EquifaxScoring.Beacon5, potentialProduct.equifaxScoringUsed))
		        		applicant.beaconScore = applicant.beacon5Score;
		        	else if (SelectionHelper.compareSelection(AlgoProduct.EquifaxScoring.Beacon9, potentialProduct.equifaxScoringUsed))
		        		applicant.beaconScore = applicant.beacon9Score;
		        	else if (SelectionHelper.compareSelection(AlgoProduct.EquifaxScoring.CRP3, potentialProduct.equifaxScoringUsed))
		        		applicant.beaconScore = applicant.crp3Score;
		        	else if (SelectionHelper.compareSelection(AlgoProduct.EquifaxScoring.ERS2, potentialProduct.equifaxScoringUsed))
		        		applicant.beaconScore = applicant.ers2Score;

		        	//System.out.println("applicant.beaconScore:" + applicant.beaconScore);
		            if (applicant.beaconScore > MaxTempBeacon)
		            {
		                MaxTempBeacon = applicant.beaconScore;
		            }
		            String beaconName = applicant.applicantName + "'s beacon score: " + applicant.beaconScore + "/n";
					listOfLenderNotes.append(beaconName);	
		        }
		    }
		    beaconScoreToUse = MaxTempBeacon;
		}
		else if (AlgoProduct.WhoseIncomeUsed.Lowest.equals(algoPotentialProduct.whoseBeaconUsed))
		{
		    // Edit this so it deals with "else"
		    int MinTempBeacon = 10000;
		    for (Applicant applicant : clientOpportunity.applicants)
		    {
		    	AlgoApplicant aolgoApplicant = new AlgoApplicant(applicant);
		        if (applicant.includeInOpportunity == true)
		        {
		        	if (SelectionHelper.compareSelection(AlgoProduct.EquifaxScoring.Beacon5, potentialProduct.equifaxScoringUsed))
		        		applicant.beaconScore = applicant.beacon5Score;
		        	else if (SelectionHelper.compareSelection(AlgoProduct.EquifaxScoring.Beacon9, potentialProduct.equifaxScoringUsed))
		        		applicant.beaconScore = applicant.beacon9Score;
		        	else if (SelectionHelper.compareSelection(AlgoProduct.EquifaxScoring.CRP3, potentialProduct.equifaxScoringUsed))
		        		applicant.beaconScore = applicant.crp3Score;
		        	else if (SelectionHelper.compareSelection(AlgoProduct.EquifaxScoring.ERS2, potentialProduct.equifaxScoringUsed))
		        		applicant.beaconScore = applicant.ers2Score;

		            if (applicant.beaconScore < MinTempBeacon)
		            {
		                MinTempBeacon = applicant.beaconScore;
		            }
		            
		            String beaconName = applicant.applicantName + "'s beacon score: " + applicant.beaconScore + "/n";
					listOfLenderNotes.append(beaconName);	
		        }
		    }
		    beaconScoreToUse = MinTempBeacon;
		}
		else if (AlgoProduct.WhoseIncomeUsed.IncomeWeightAvg.equals(algoPotentialProduct.whoseBeaconUsed))
		{
		    double WeightedBeacon = 0;
		    for (Applicant applicant : clientOpportunity.applicants)
		    {
		    	AlgoApplicant aolgoApplicant = new AlgoApplicant(applicant);
		        if (applicant.includeInOpportunity == true)
		        {
		        	if (SelectionHelper.compareSelection(AlgoProduct.EquifaxScoring.Beacon5, potentialProduct.equifaxScoringUsed))
		        		applicant.beaconScore = applicant.beacon5Score;
		        	else if (SelectionHelper.compareSelection(AlgoProduct.EquifaxScoring.Beacon9, potentialProduct.equifaxScoringUsed))
		        		applicant.beaconScore = applicant.beacon9Score;
		        	else if (SelectionHelper.compareSelection(AlgoProduct.EquifaxScoring.CRP3, potentialProduct.equifaxScoringUsed))
		        		applicant.beaconScore = applicant.crp3Score;
		        	else if (SelectionHelper.compareSelection(AlgoProduct.EquifaxScoring.ERS2, potentialProduct.equifaxScoringUsed))
		        		applicant.beaconScore = applicant.ers2Score;
		        	
		            double PercentOfTotalIncome = applicant.totalIncome / allApplicantsTotalIncomes;
		            double ApplicantBeaconWeight = PercentOfTotalIncome * applicant.beaconScore;
		            WeightedBeacon += ApplicantBeaconWeight;
		            
		            String beaconName = applicant.applicantName + "'s beacon score: " + applicant.beaconScore + "/n";
					listOfLenderNotes.append(beaconName);	
		        }
		    }
		    beaconScoreToUse = WeightedBeacon;
		}
		else{
			int MaxTempBeacon = 0;
		    for (Applicant applicant : clientOpportunity.applicants)
		    {
		    	
		    	AlgoApplicant aolgoApplicant = new AlgoApplicant(applicant);
		    	//System.out.println(applicant.includeInOpportunity);
		        if (applicant.includeInOpportunity == true)
		        {
		        	if (SelectionHelper.compareSelection(AlgoProduct.EquifaxScoring.Beacon5, potentialProduct.equifaxScoringUsed))
		        		applicant.beaconScore = applicant.beacon5Score;
		        	else if (SelectionHelper.compareSelection(AlgoProduct.EquifaxScoring.Beacon9, potentialProduct.equifaxScoringUsed))
		        		applicant.beaconScore = applicant.beacon9Score;
		        	else if (SelectionHelper.compareSelection(AlgoProduct.EquifaxScoring.CRP3, potentialProduct.equifaxScoringUsed))
		        		applicant.beaconScore = applicant.crp3Score;
		        	else if (SelectionHelper.compareSelection(AlgoProduct.EquifaxScoring.ERS2, potentialProduct.equifaxScoringUsed))
		        		applicant.beaconScore = applicant.ers2Score;

		        	//System.out.println("applicant.beaconScore:" + applicant.beaconScore);
		            if (applicant.beaconScore > MaxTempBeacon)
		            {
		                MaxTempBeacon = applicant.beaconScore;
		            }
		            
		            String beaconName = applicant.applicantName + "'s beacon score: " + applicant.beaconScore + "/n";
					listOfLenderNotes.append(beaconName);	
		        }
		    }
		    beaconScoreToUse = MaxTempBeacon;
		}
		return beaconScoreToUse;
	}

	double latePayments2Use() {
		double latePaymentsToUse = 0;
		if (AlgoProduct.WhoseIncomeUsed.Averaged.equals(algoPotentialProduct.whoseBeaconUsed))
		{
		    int sumLatePayments = 0;
		    int applicantCount = 0;
		    for (Applicant applicant : clientOpportunity.applicants)
		    {
		    	
		    	AlgoApplicant aolgoApplicant = new AlgoApplicant(applicant);
		        if (applicant.includeInOpportunity == true)
		        {         
		            sumLatePayments += aolgoApplicant.late_payments;
		            applicantCount++;
		        }
		        
		        int latesIn2years = 0;
		        for (LatePayment latePayment : applicant.latePayments) {
		        	Calendar dateNow = Calendar.getInstance();
			        int MonthsAgo = 24 * -1;
			        dateNow.add(Calendar.MONTH, MonthsAgo);    
		        	if (latePayment.date.after(dateNow.getTime()))
		        		latesIn2years = latesIn2years + 1;
				}
		        String latesNote = applicant.applicantName + " has had " + latesIn2years + " in the past 2 years/n";
				listOfLenderNotes.append(latesNote);		
		        
		    }
		    latePaymentsToUse = ((double)sumLatePayments / (double)applicantCount);
		}
		else if (AlgoProduct.WhoseIncomeUsed.Highest.equals(algoPotentialProduct.whoseBeaconUsed))
		{
		    int MaxTempBeacon = 0;
		    int TempLates = 0;
		    for (Applicant applicant : clientOpportunity.applicants)
		    {
		    	
		    	AlgoApplicant aolgoApplicant = new AlgoApplicant(applicant);
		    	//System.out.println(applicant.includeInOpportunity);
		        if (applicant.includeInOpportunity == true)
		        {
		        	if (SelectionHelper.compareSelection(AlgoProduct.EquifaxScoring.Beacon5, potentialProduct.equifaxScoringUsed))
		        		applicant.beaconScore = applicant.beacon5Score;
		        	else if (SelectionHelper.compareSelection(AlgoProduct.EquifaxScoring.Beacon9, potentialProduct.equifaxScoringUsed))
		        		applicant.beaconScore = applicant.beacon9Score;
		        	else if (SelectionHelper.compareSelection(AlgoProduct.EquifaxScoring.CRP3, potentialProduct.equifaxScoringUsed))
		        		applicant.beaconScore = applicant.crp3Score;
		        	else if (SelectionHelper.compareSelection(AlgoProduct.EquifaxScoring.ERS2, potentialProduct.equifaxScoringUsed))
		        		applicant.beaconScore = applicant.ers2Score;

		        	//System.out.println("applicant.beaconScore:" + applicant.beaconScore);
		            if (applicant.beaconScore > MaxTempBeacon)
		            {
		                MaxTempBeacon = applicant.beaconScore;
		                TempLates = aolgoApplicant.late_payments;
		            }
		            
		            int latesIn2years = 0;
			        for (LatePayment latePayment : applicant.latePayments) {
			        	Calendar dateNow = Calendar.getInstance();
				        int MonthsAgo = 24 * -1;
				        dateNow.add(Calendar.MONTH, MonthsAgo);    
			        	if (latePayment.date.after(dateNow.getTime()))
			        		latesIn2years = latesIn2years + 1;
					}
			        String latesNote = applicant.applicantName + " has had " + latesIn2years + " in the past 2 years/n";
					listOfLenderNotes.append(latesNote);		
			       
		        }
		    }
		    latePaymentsToUse = TempLates;
		}
		else if (AlgoProduct.WhoseIncomeUsed.Lowest.equals(algoPotentialProduct.whoseBeaconUsed))
		{
		    // Edit this so it deals with "else"
		    int MinTempBeacon = 10000;
		    int TempLates = 0;
		    for (Applicant applicant : clientOpportunity.applicants)
		    {
		    	AlgoApplicant aolgoApplicant = new AlgoApplicant(applicant);
		        if (applicant.includeInOpportunity == true)
		        {
		        	if (SelectionHelper.compareSelection(AlgoProduct.EquifaxScoring.Beacon5, potentialProduct.equifaxScoringUsed))
		        		applicant.beaconScore = applicant.beacon5Score;
		        	else if (SelectionHelper.compareSelection(AlgoProduct.EquifaxScoring.Beacon9, potentialProduct.equifaxScoringUsed))
		        		applicant.beaconScore = applicant.beacon9Score;
		        	else if (SelectionHelper.compareSelection(AlgoProduct.EquifaxScoring.CRP3, potentialProduct.equifaxScoringUsed))
		        		applicant.beaconScore = applicant.crp3Score;
		        	else if (SelectionHelper.compareSelection(AlgoProduct.EquifaxScoring.ERS2, potentialProduct.equifaxScoringUsed))
		        		applicant.beaconScore = applicant.ers2Score;

		            if (applicant.beaconScore < MinTempBeacon)
		            {
		                MinTempBeacon = applicant.beaconScore;
		                TempLates = aolgoApplicant.late_payments;
		            }
		            
		            int latesIn2years = 0;
			        for (LatePayment latePayment : applicant.latePayments) {
			        	Calendar dateNow = Calendar.getInstance();
				        int MonthsAgo = 24 * -1;
				        dateNow.add(Calendar.MONTH, MonthsAgo);    
			        	if (latePayment.date.after(dateNow.getTime()))
			        		latesIn2years = latesIn2years + 1;
					}
			        String latesNote = applicant.applicantName + " has had " + latesIn2years + " in the past 2 years/n";
					listOfLenderNotes.append(latesNote);					       
					
		        }
		    }
		    latePaymentsToUse = TempLates;
		}
		else if (AlgoProduct.WhoseIncomeUsed.IncomeWeightAvg.equals(algoPotentialProduct.whoseBeaconUsed))
		{
		    double WeightedBeacon = 0;
		    double WeightedLatePayments = 0;
		    for (Applicant applicant : clientOpportunity.applicants)
		    {
		    	AlgoApplicant aolgoApplicant = new AlgoApplicant(applicant);
		        if (applicant.includeInOpportunity == true)
		        {
		        	if (SelectionHelper.compareSelection(AlgoProduct.EquifaxScoring.Beacon5, potentialProduct.equifaxScoringUsed))
		        		applicant.beaconScore = applicant.beacon5Score;
		        	else if (SelectionHelper.compareSelection(AlgoProduct.EquifaxScoring.Beacon9, potentialProduct.equifaxScoringUsed))
		        		applicant.beaconScore = applicant.beacon9Score;
		        	else if (SelectionHelper.compareSelection(AlgoProduct.EquifaxScoring.CRP3, potentialProduct.equifaxScoringUsed))
		        		applicant.beaconScore = applicant.crp3Score;
		        	else if (SelectionHelper.compareSelection(AlgoProduct.EquifaxScoring.ERS2, potentialProduct.equifaxScoringUsed))
		        		applicant.beaconScore = applicant.ers2Score;
		        	
		            double PercentOfTotalIncome = applicant.totalIncome / allApplicantsTotalIncomes;
		            double ApplicantBeaconWeight = PercentOfTotalIncome * applicant.beaconScore;
		            WeightedBeacon += ApplicantBeaconWeight;
		            double ApplicantLatePayWeight = PercentOfTotalIncome * aolgoApplicant.late_payments;
		            WeightedLatePayments += ApplicantLatePayWeight;
		            
		            int latesIn2years = 0;
			        for (LatePayment latePayment : applicant.latePayments) {
			        	Calendar dateNow = Calendar.getInstance();
				        int MonthsAgo = 24 * -1;
				        dateNow.add(Calendar.MONTH, MonthsAgo);    
			        	if (latePayment.date.after(dateNow.getTime()))
			        		latesIn2years = latesIn2years + 1;
					}
			        String latesNote = applicant.applicantName + " has had " + latesIn2years + " in the past 2 years/n";
					listOfLenderNotes.append(latesNote);		
			       
		        }
		    }
		    latePaymentsToUse = WeightedLatePayments;
		}
		else 
		{
		    int MaxTempBeacon = 0;
		    int TempLates = 0;
		    for (Applicant applicant : clientOpportunity.applicants)
		    {
		    	
		    	AlgoApplicant aolgoApplicant = new AlgoApplicant(applicant);
		    	//System.out.println(applicant.includeInOpportunity);
		        if (applicant.includeInOpportunity == true)
		        {
		        	if (SelectionHelper.compareSelection(AlgoProduct.EquifaxScoring.Beacon5, potentialProduct.equifaxScoringUsed))
		        		applicant.beaconScore = applicant.beacon5Score;
		        	else if (SelectionHelper.compareSelection(AlgoProduct.EquifaxScoring.Beacon9, potentialProduct.equifaxScoringUsed))
		        		applicant.beaconScore = applicant.beacon9Score;
		        	else if (SelectionHelper.compareSelection(AlgoProduct.EquifaxScoring.CRP3, potentialProduct.equifaxScoringUsed))
		        		applicant.beaconScore = applicant.crp3Score;
		        	else if (SelectionHelper.compareSelection(AlgoProduct.EquifaxScoring.ERS2, potentialProduct.equifaxScoringUsed))
		        		applicant.beaconScore = applicant.ers2Score;

		        	//System.out.println("applicant.beaconScore:" + applicant.beaconScore);
		            if (applicant.beaconScore > MaxTempBeacon)
		            {
		                MaxTempBeacon = applicant.beaconScore;
		                TempLates = aolgoApplicant.late_payments;
		            }
		            
		            int latesIn2years = 0;
			        for (LatePayment latePayment : applicant.latePayments) {
			        	Calendar dateNow = Calendar.getInstance();
				        int MonthsAgo = 24 * -1;
				        dateNow.add(Calendar.MONTH, MonthsAgo);    
			        	if (latePayment.date.after(dateNow.getTime()))
			        		latesIn2years = latesIn2years + 1;
					}
			        String latesNote = applicant.applicantName + " has had " + latesIn2years + " late payments in the past 2 years/n";
					listOfLenderNotes.append(latesNote);		
			       
		        }
		    }
		    latePaymentsToUse = TempLates;
		}
		return latePaymentsToUse;
	}


	double calculateQualifyingInterestRate(double productInterestRate, double interestRateVar) {
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
	
	double calculateMortgagePayment(double productInterestRate, double interestRateVar, int amortization, double mortgageAmount) {
		double expectedPaymentAmount = 0;
		if (SelectionHelper.compareSelection(AlgoProduct.MortgageType.LOC, potentialProduct.mortgageType)){
			expectedPaymentAmount = CalculatePayments.calculateMonthlyLOCPayment(interestRateVar, mortgageAmount);
		}
		else if (SelectionHelper.compareSelection(AlgoProduct.MortgageType.Fixed, potentialProduct.mortgageType)){
			if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year5, potentialProduct.term)){
				expectedPaymentAmount = CalculatePayments.calculateMortgagePayment(productInterestRate, mortgageAmount, amortization, AlgoOpportunity.FrequencyDesired.Monthly);
		    }
			else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year6, potentialProduct.term)){
				expectedPaymentAmount = CalculatePayments.calculateMortgagePayment(productInterestRate, mortgageAmount, amortization, AlgoOpportunity.FrequencyDesired.Monthly);
		    }
			else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year7, potentialProduct.term)){
				expectedPaymentAmount = CalculatePayments.calculateMortgagePayment(productInterestRate, mortgageAmount, amortization, AlgoOpportunity.FrequencyDesired.Monthly);
		    }
			else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year8, potentialProduct.term)){
				expectedPaymentAmount = CalculatePayments.calculateMortgagePayment(productInterestRate, mortgageAmount, amortization, AlgoOpportunity.FrequencyDesired.Monthly);
		    }
			else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year9, potentialProduct.term)){
				expectedPaymentAmount = CalculatePayments.calculateMortgagePayment(productInterestRate, mortgageAmount, amortization, AlgoOpportunity.FrequencyDesired.Monthly);
		    }
			else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year10, potentialProduct.term)){
				expectedPaymentAmount = CalculatePayments.calculateMortgagePayment(productInterestRate, mortgageAmount, amortization, AlgoOpportunity.FrequencyDesired.Monthly);
		    }
			else {
				expectedPaymentAmount = CalculatePayments.calculateMortgagePayment(interestRateVar, mortgageAmount, amortization, AlgoOpportunity.FrequencyDesired.Monthly);		
			}
		}
		else {
			expectedPaymentAmount = CalculatePayments.calculateMortgagePayment(interestRateVar, mortgageAmount, amortization, AlgoOpportunity.FrequencyDesired.Monthly);		
		}
		return expectedPaymentAmount;
	}
	
	double calculateExpectedPayment(double productInterestRate, double interestRateVar, int amortization) {
		double expectedPaymentAmount = 0;
		if (SelectionHelper.compareSelection(AlgoProduct.MortgageType.LOC, potentialProduct.mortgageType)){
			expectedPaymentAmount = CalculatePayments.calculateMonthlyLOCPayment(interestRateVar, expectedMortgageAmount);
		}
		else if (SelectionHelper.compareSelection(AlgoProduct.MortgageType.Fixed, potentialProduct.mortgageType)){
			if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year5, potentialProduct.term)){
				expectedPaymentAmount = CalculatePayments.calculateMortgagePayment(productInterestRate, expectedMortgageAmount, amortization, AlgoOpportunity.FrequencyDesired.Monthly);
		    }
			else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year6, potentialProduct.term)){
				expectedPaymentAmount = CalculatePayments.calculateMortgagePayment(productInterestRate, expectedMortgageAmount, amortization, AlgoOpportunity.FrequencyDesired.Monthly);
		    }
			else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year7, potentialProduct.term)){
				expectedPaymentAmount = CalculatePayments.calculateMortgagePayment(productInterestRate, expectedMortgageAmount, amortization, AlgoOpportunity.FrequencyDesired.Monthly);
		    }
			else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year8, potentialProduct.term)){
				expectedPaymentAmount = CalculatePayments.calculateMortgagePayment(productInterestRate, expectedMortgageAmount, amortization, AlgoOpportunity.FrequencyDesired.Monthly);
		    }
			else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year9, potentialProduct.term)){
				expectedPaymentAmount = CalculatePayments.calculateMortgagePayment(productInterestRate, expectedMortgageAmount, amortization, AlgoOpportunity.FrequencyDesired.Monthly);
		    }
			else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year10, potentialProduct.term)){
				expectedPaymentAmount = CalculatePayments.calculateMortgagePayment(productInterestRate, expectedMortgageAmount, amortization, AlgoOpportunity.FrequencyDesired.Monthly);
		    }
			else {
				expectedPaymentAmount = CalculatePayments.calculateMortgagePayment(interestRateVar, expectedMortgageAmount, amortization, AlgoOpportunity.FrequencyDesired.Monthly);		
			}
		}
		else {
			expectedPaymentAmount = CalculatePayments.calculateMortgagePayment(interestRateVar, expectedMortgageAmount, amortization, AlgoOpportunity.FrequencyDesired.Monthly);		
		}
		return expectedPaymentAmount;
	}

	protected boolean checkIfHighRatio(double valueOfProperty,double expectedMortgageAmount2) {
		
//		if (valueOfProperty < 480000)
//			System.out.println("Value: " + valueOfProperty);
		
		boolean propertyIsHighRatio = false; // Default setting, becomes true based on logic below
		insuredThresholdForProperty = 0;
		requiredNonInsureDownpayment = 0;
		if (clientOpportunity.isCottageRecProperty == true){
		    // Used to be this ... the challenge is that cottage properties also have a cutoff and we did not account for this in 
			// Product Record in the database.  Thus the closest match is small center rules ... So, small center rules are reused here
			                
		    double unInsureLTVBelow = Math.min(valueOfProperty, potentialProduct.smCenterConCutOff) * (potentialProduct.smCenterLtvCutOffLes / 100);
		    if (expectedMortgageAmount > potentialProduct.smCenterConCutOff)
		    {
		        double propertyValueAboveThreshold = expectedMortgageAmount - potentialProduct.smCenterConCutOff;
		        double UninsuredLTVAbove = propertyValueAboveThreshold * (potentialProduct.smCenterLtvCutOffGrt / 100);
		        insuredThresholdForProperty = unInsureLTVBelow + UninsuredLTVAbove;
		    }
		    else
		    {
		        insuredThresholdForProperty = unInsureLTVBelow;

		    }
		    requiredNonInsureDownpayment = valueOfProperty - insuredThresholdForProperty;
		}
		else if (clientOpportunity.isaSmallCentre == true){ 
		    double unInsureLTVBelow = Math.min(valueOfProperty, potentialProduct.smCenterConCutOff)  * (potentialProduct.smCenterLtvCutOffLes / 100);
		    if (expectedMortgageAmount > potentialProduct.smCenterConCutOff)
		    {
		        double propertyValueAboveThreshold = expectedMortgageAmount - potentialProduct.smCenterConCutOff;
		        double UninsuredLTVAbove = propertyValueAboveThreshold * (potentialProduct.smCenterLtvCutOffGrt / 100);
		        insuredThresholdForProperty = unInsureLTVBelow + UninsuredLTVAbove;
		    }
		    else
		    {
		        insuredThresholdForProperty = unInsureLTVBelow;

		    }
		    requiredNonInsureDownpayment = valueOfProperty - insuredThresholdForProperty;
		    
		    if (LTV > potentialProduct.smallCenterInsureLtv){
		    	countOfUnacceptableCriteria++;
		    	AlgoNote Note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "This Property is in a Small Center and the LTV is larger than what is allowed by this lender.", "FailedApplication" + failedNotesCounter);
		        dealNotes.add(Note1);
		        failReasons.add("This Property is in a Small Center and the LTV is larger than what is allowed by this lender.");
		        failedNotesCounter++;
		    }
		    else{
		    	countOfAcceptableCritera++;
		    }
		}
		else if (clientOpportunity.isRentalProperty == true){
		    double unInsureLTVBelow = Math.min(valueOfProperty, potentialProduct.rentalConCutOff) * (potentialProduct.rentalLtvCutOffLes / 100);
		    if (valueOfProperty > potentialProduct.rentalConCutOff){
		        double PropertyValueAboveThreshold = valueOfProperty - potentialProduct.rentalConCutOff;
		        double UninsuredLTVAbove = PropertyValueAboveThreshold * (potentialProduct.rentalLtvCutOffGrt / 100);
		        insuredThresholdForProperty = unInsureLTVBelow + UninsuredLTVAbove;
		    }
		    else{
		        insuredThresholdForProperty = unInsureLTVBelow;

		    }
		    requiredNonInsureDownpayment = valueOfProperty - insuredThresholdForProperty;                
		}
		else if (algoClientOpportunity.residenceProperty == true){
		    double unInsureLTVBelow = Math.min(valueOfProperty, potentialProduct.resConCutoff) * (potentialProduct.resLtvCutoffLes / 100);
		    if (valueOfProperty > potentialProduct.resConCutoff){
		        double propertyValueAboveThreshold = valueOfProperty - potentialProduct.resConCutoff;
		        double uninsuredLTVAbove = propertyValueAboveThreshold * (potentialProduct.resLtvCutOffGrt / 100);
		        insuredThresholdForProperty = unInsureLTVBelow + uninsuredLTVAbove;
		    }
		    else{
		        insuredThresholdForProperty = unInsureLTVBelow;

		    }
		    requiredNonInsureDownpayment = valueOfProperty - insuredThresholdForProperty;               
		}
		else{  
		    double unInsureLTVBelow = Math.min(valueOfProperty, potentialProduct.resConCutoff) * (potentialProduct.resLtvCutoffLes / 100);
		    if (valueOfProperty > potentialProduct.resConCutoff){
		        double propertyValueAboveThreshold = valueOfProperty - potentialProduct.resConCutoff;
		        double uninsuredLTVAbove = propertyValueAboveThreshold * (potentialProduct.resLtvCutOffGrt / 100);
		        insuredThresholdForProperty = unInsureLTVBelow + uninsuredLTVAbove;
		    }
		    else{
		        insuredThresholdForProperty = unInsureLTVBelow;

		    }
		    requiredNonInsureDownpayment = valueOfProperty - insuredThresholdForProperty;
		}
		
		if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Refinance, clientOpportunity.whatIsYourLendingGoal)){
			if (expectedMortgageAmount > insuredThresholdForProperty){
			    algoClientOpportunity.highRatio = true;
			    propertyIsHighRatio = true;
			}
			else 
				propertyIsHighRatio = false;
		}
		else{
			if (clientOpportunity.downpaymentAmount < requiredNonInsureDownpayment || expectedMortgageAmount > insuredThresholdForProperty){
			    algoClientOpportunity.highRatio = true;
			    propertyIsHighRatio = true;
			}
			else 
				propertyIsHighRatio = false;		
		}
		
//		if (propertyIsHighRatio == true)
//			System.out.println("high ratio");
		
		return propertyIsHighRatio;
	}
	
	protected boolean checkMaxHighRatio(double valueOfProperty,double expectedMortgageAmount2) {
		
//		if (valueOfProperty < 480000)
//			System.out.println("Value: " + valueOfProperty);
		
		boolean propertyIsHighRatio = false; // Default setting, becomes true based on logic below
		insuredThresholdForProperty = 0;
		requiredNonInsureDownpayment = 0;
		if (clientOpportunity.isCottageRecProperty == true){
		    // Used to be this ... the challenge is that cottage properties also have a cutoff and we did not account for this in 
			// Product Record in the database.  Thus the closest match is small center rules ... So, small center rules are reused here
			                
		    double unInsureLTVBelow = Math.min(valueOfProperty, potentialProduct.smCenterConCutOff) * (potentialProduct.smCenterLtvCutOffLes / 100);
		    if (expectedMortgageAmount > potentialProduct.smCenterConCutOff)
		    {
		        double propertyValueAboveThreshold = expectedMortgageAmount - potentialProduct.smCenterConCutOff;
		        double UninsuredLTVAbove = propertyValueAboveThreshold * (potentialProduct.smCenterLtvCutOffGrt / 100);
		        insuredThresholdForProperty = unInsureLTVBelow + UninsuredLTVAbove;
		    }
		    else
		    {
		        insuredThresholdForProperty = unInsureLTVBelow;

		    }
		    requiredNonInsureDownpayment = valueOfProperty - insuredThresholdForProperty;
		}
		else if (clientOpportunity.isaSmallCentre == true){ 
		    double unInsureLTVBelow = Math.min(valueOfProperty, potentialProduct.smCenterConCutOff)  * (potentialProduct.smCenterLtvCutOffLes / 100);
		    if (expectedMortgageAmount > potentialProduct.smCenterConCutOff)
		    {
		        double propertyValueAboveThreshold = expectedMortgageAmount - potentialProduct.smCenterConCutOff;
		        double UninsuredLTVAbove = propertyValueAboveThreshold * (potentialProduct.smCenterLtvCutOffGrt / 100);
		        insuredThresholdForProperty = unInsureLTVBelow + UninsuredLTVAbove;
		    }
		    else
		    {
		        insuredThresholdForProperty = unInsureLTVBelow;

		    }
		    requiredNonInsureDownpayment = valueOfProperty - insuredThresholdForProperty;
		    
		    if (LTV > potentialProduct.smallCenterInsureLtv){
		    	countOfUnacceptableCriteria++;
		    	AlgoNote Note1 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "This Property is in a Small Center and the LTV is larger than what is allowed by this lender.", "FailedApplication" + failedNotesCounter);
		        dealNotes.add(Note1);
		        failReasons.add("This Property is in a Small Center and the LTV is larger than what is allowed by this lender.");
		        failedNotesCounter++;
		    }
		    else{
		    	countOfAcceptableCritera++;
		    }
		}
		else if (clientOpportunity.isRentalProperty == true){
		    double unInsureLTVBelow = Math.min(valueOfProperty, potentialProduct.rentalConCutOff) * (potentialProduct.rentalLtvCutOffLes / 100);
		    if (valueOfProperty > potentialProduct.rentalConCutOff){
		        double PropertyValueAboveThreshold = valueOfProperty - potentialProduct.rentalConCutOff;
		        double UninsuredLTVAbove = PropertyValueAboveThreshold * (potentialProduct.rentalLtvCutOffGrt / 100);
		        insuredThresholdForProperty = unInsureLTVBelow + UninsuredLTVAbove;
		    }
		    else{
		        insuredThresholdForProperty = unInsureLTVBelow;

		    }
		    requiredNonInsureDownpayment = valueOfProperty - insuredThresholdForProperty;                
		}
		else if (algoClientOpportunity.residenceProperty == true){
		    double unInsureLTVBelow = Math.min(valueOfProperty, potentialProduct.resConCutoff) * (potentialProduct.resLtvCutoffLes / 100);
		    if (valueOfProperty > potentialProduct.resConCutoff){
		        double propertyValueAboveThreshold = valueOfProperty - potentialProduct.resConCutoff;
		        double uninsuredLTVAbove = propertyValueAboveThreshold * (potentialProduct.resLtvCutOffGrt / 100);
		        insuredThresholdForProperty = unInsureLTVBelow + uninsuredLTVAbove;
		    }
		    else{
		        insuredThresholdForProperty = unInsureLTVBelow;

		    }
		    requiredNonInsureDownpayment = valueOfProperty - insuredThresholdForProperty;               
		}
		else{  
		    double unInsureLTVBelow = Math.min(valueOfProperty, potentialProduct.resConCutoff) * (potentialProduct.resLtvCutoffLes / 100);
		    if (valueOfProperty > potentialProduct.resConCutoff){
		        double propertyValueAboveThreshold = valueOfProperty - potentialProduct.resConCutoff;
		        double uninsuredLTVAbove = propertyValueAboveThreshold * (potentialProduct.resLtvCutOffGrt / 100);
		        insuredThresholdForProperty = unInsureLTVBelow + uninsuredLTVAbove;
		    }
		    else{
		        insuredThresholdForProperty = unInsureLTVBelow;

		    }
		    requiredNonInsureDownpayment = valueOfProperty - insuredThresholdForProperty;
		}
		
		if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Refinance, clientOpportunity.whatIsYourLendingGoal)){
			if (expectedMortgageAmount > insuredThresholdForProperty){
			    algoClientOpportunity.highRatio = true;
			    propertyIsHighRatio = true;
			}
			else 
				propertyIsHighRatio = false;
		}
		else{
			if (clientOpportunity.downpaymentAmount < requiredNonInsureDownpayment || expectedMortgageAmount > insuredThresholdForProperty){
			    algoClientOpportunity.highRatio = true;
			    propertyIsHighRatio = true;
			}
			else 
				propertyIsHighRatio = false;		
		}
		
		if (propertyIsHighRatio == true)
			System.out.println("high ratio");
		
		return propertyIsHighRatio;
	}

	double monthsToClose() {
		Date closeDate;
		long DaysToClose; 
		double MonthsToClose = 0;
		try {
			if (clientOpportunity.expectedClosingDate != null){
				closeDate = clientOpportunity.expectedClosingDate;
				DaysToClose = (closeDate.getTime() - new Date().getTime())/( 24 * 60 * 60 * 1000 );
				MonthsToClose = DaysToClose / 30;
			}
//            	    	                                	
		} catch (Exception e) {
			e.printStackTrace();
			DaysToClose = 0; 
		}											
		
		
		return MonthsToClose;
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
    	
//    	System.out.println("getFitness:" + avgCost + "," + AvgOneTimeCost+ "," + avgInterestRate+ "," + avgRisk+ "," + avgCashback+ "," + 
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
        
        //System.out.println("Fitness: " + productFitness);        
        return productFitness;
    }
    
    public void CalculateOneTimeFees()
    {
        lenderFee = (expectedMortgageAmount * potentialProduct.lenderFeesPercent / 100) + potentialProduct.lenderFeesFlat; 
        brokerFee = (expectedMortgageAmount * potentialProduct.brokerFeesPercent / 100) + potentialProduct.brokerFeesFlat;
        oneTimeFees = lenderFee + brokerFee;
    }
    
	protected void checkNonIncomeQualifier() {
		// TODO Auto-generated method stub
		
	}

	public double getExpectedFitnessPayment() {
		return expectedFitnessPayment;
	}

	public void setExpectedFitnessPayment(double expectedFitnessPayment) {
		this.expectedFitnessPayment = expectedFitnessPayment;
	}

	public double getOneTimeFees() {
		return oneTimeFees;
	}

	public void setOneTimeFees(double oneTimeFees) {
		this.oneTimeFees = oneTimeFees;
	}

}