package com.syml.service;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import javax.swing.GroupLayout.Alignment;

import javassist.bytecode.stackmap.BasicBlock.Catch;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

//import com.sun.corba.se.pept.transport.Acceptor;
import com.syml.bean.algo.AlgoApplicant;
import com.syml.bean.algo.AlgoIncome;
import com.syml.bean.algo.AlgoLiability;
import com.syml.bean.algo.AlgoNote;
import com.syml.bean.algo.AlgoOpportunity;
import com.syml.bean.algo.AlgoProduct;
import com.syml.bean.algo.CalculatePayments;
import com.syml.bean.algo.InsurancePremiums;
import com.syml.bean.algo.UnderwriteAll;
import com.syml.bean.algo.UnderwritingBase;
import com.syml.bean.algo.AlgoOpportunity.FrequencyDesired;
import com.syml.dao.BaseDAO;
import com.syml.dao.impl.BaseDAOImpl;
import com.syml.domain.Applicant;
import com.syml.domain.Asset;
import com.syml.domain.Collection;
import com.syml.domain.Income;
import com.syml.domain.InsurerProducts;
import com.syml.domain.LatePayment;
import com.syml.domain.Liability;
import com.syml.domain.Mortgage;
import com.syml.domain.Opportunity;
import com.syml.domain.Product;
import com.syml.domain.Property;
import com.syml.domain.Task;
import com.syml.util.GenericComparator;
import com.syml.util.ParserUtil;
import com.syml.util.SelectionHelper;
import com.syml.util.Util;

public class DesiredProductAlgorithm
{
    public AlgoOpportunity clientAlgoOpportunity;
    public Opportunity clientOpportunity;
    public Product potentialProduct;
    public boolean NonIncomeQualifer;
    public int HighRatioLTVPercentage;  
    public List<AlgoNote> dealNotes;
    public List<Task> assistantTasks;
    public List<Task> brokerTasks;  
    public double expectedMortgageAmount;
    
    private Session session;
    public DesiredProductAlgorithm(AlgoOpportunity clientAlgoOpportunity, Session session) throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException
    {
	    	try {
				this.session = session;
				this.clientAlgoOpportunity = clientAlgoOpportunity;
				this.clientOpportunity = clientAlgoOpportunity.opp;
				System.out.println("1clientOpportunity.desiredMortgageType:" + clientOpportunity.desiredMortgageType); 
				System.out.println("1clientOpportunity.desiredTerm:" + clientOpportunity.desiredTerm);
				assistantTasks = new ArrayList<Task>();
				brokerTasks = new ArrayList<Task>();
				dealNotes = new ArrayList<AlgoNote>();
				// I need to think through the value of sorting the Applicants ... It may not need to be done, except to communicate a re-ordering of applicants back to assistants... 
				// Set the Applicant and CoApplicant Based upon highest beacon score.
				
				double totalDealIncome = 0;
				double totalDealLiabilityPayments = 0;
				double totalDealLiabilities = 0;
				double totalDealMortgagesBalance = 0;
				double totalDealMortgagesPayments = 0;
				double totalDealPropertyTaxes = 0;
				double totalDealCondoFees = 0;
				double totalDealPropertyValues = 0;
				double totalDealPropertyHeating = 0; 
				double allApplicantsTotalAssets = 0;
				boolean statedIncomeDeal = false;
				//boolean refinancePropertyNotInTotals = false;
				// TODO Switch Primary Applicant to best credit is Income is over 20,000 
				List<Liability> joinLiabilities = new ArrayList<Liability>();
				
				// Assign 0 to null references
				if (clientOpportunity.currentBalance == null)
					clientOpportunity.currentBalance = (double) 0;
				if (clientOpportunity.currentMortgageAmount == null)
					clientOpportunity.currentMortgageAmount = (double) 0;
				if (clientOpportunity.renovationValue == null)
					clientOpportunity.renovationValue = (double) 0;
				if (clientOpportunity.additionalAmount == null)
					clientOpportunity.additionalAmount = (double) 0;

				StringBuilder listOfDocsRealtor = new StringBuilder();
				StringBuilder listOfDocsEmail = new StringBuilder();
				StringBuilder listOfIncomeDocsWeb = new StringBuilder();
				StringBuilder listOfPropertyDocsWeb = new StringBuilder();
				StringBuilder listOfDownpayDocsWeb = new StringBuilder();
				StringBuilder listOfMiscDocsWeb = new StringBuilder();
				
				if (clientOpportunity.applicants != null && clientOpportunity.applicants.size() > 0)
				{
					
					double valueOfProperty = 0;
					statedIncomeDeal = false;
					
					double beaconScoreToUse;
					double latePaymentsToUse;
					// Determine the beacon score to use
					int MaxTempBeacon = 0;
				    int TempLates = 0;
				    for (Applicant applicant : clientOpportunity.applicants)
				    {
				    	AlgoApplicant aolgoApplicant = new AlgoApplicant(applicant);
				    	System.out.println(applicant.includeInOpportunity);
				        if (applicant.includeInOpportunity == true)
				        {
//                	if (SelectionHelper.compareSelection(AlgoProduct.EquifaxScoring.Beacon5, potentialProduct.equifaxScoringUsed))
//                		applicant.beaconScore = applicant.beacon5Score;
//                	else if (SelectionHelper.compareSelection(AlgoProduct.EquifaxScoring.Beacon9, potentialProduct.equifaxScoringUsed))
//                		applicant.beaconScore = applicant.beacon9Score;
//                	else if (SelectionHelper.compareSelection(AlgoProduct.EquifaxScoring.CRP3, potentialProduct.equifaxScoringUsed))
//                		applicant.beaconScore = applicant.crp3Score;
//                	else if (SelectionHelper.compareSelection(AlgoProduct.EquifaxScoring.ERS2, potentialProduct.equifaxScoringUsed))
//                		applicant.beaconScore = applicant.ers2Score;

				        	applicant.beaconScore = applicant.beacon5Score;
				        	System.out.println("applicant.beaconScore:" + applicant.beaconScore);
				            if (applicant.beaconScore > MaxTempBeacon)
				            {
				                MaxTempBeacon = applicant.beaconScore;
				                TempLates = aolgoApplicant.late_payments;
				            }
				        }
				    }
				    beaconScoreToUse = MaxTempBeacon;
				    latePaymentsToUse = TempLates;
				    System.out.println("beaconScoreToUse:" + beaconScoreToUse);
				    
				    boolean beaconAcceptable = false;
				    if (beaconScoreToUse >= 650){
				    	beaconAcceptable = true;
				    }
				    else{
				    	beaconAcceptable = false;
				    }
				    
				    // Calculate LTV .... 
				    valueOfProperty = clientOpportunity.propertyValue;
				    double lendableValue = 0;
				    if (clientOpportunity.outbuildingsValue > 0)
				    {
				        double lenderAcceptedOutBuildingVal = clientOpportunity.outbuildingsValue * (80 / 100);
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
				    	// Confirm this is the way to determine LTV in case of Prequalify
				    	// TODO Test Webform Downpayment Amount ... Confirm from Webform fields All 3 LendingGoals 
				    	// TODO Put a Note in here to check the web form to confirm whether there is a question "how much are you planning to buy"? Kendall thinks so - if so, alternative method 
				    }
				    else {
				    	valueOfProperty = valueOfProperty + clientOpportunity.renovationValue;
				    }  
				      
				    Calendar currentDate = new GregorianCalendar();
				    Calendar deadline = new GregorianCalendar();
				    deadline.add(Calendar.HOUR, 1);
				    
				    expectedMortgageAmount = 0;
				    if (clientOpportunity.desiredMortgageAmount == 0){
				    	expectedMortgageAmount = clientOpportunity.propertyValue - clientOpportunity.downpaymentAmount;        			
				    }        
				    if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Refinance, clientOpportunity.whatIsYourLendingGoal)  ){
				    	expectedMortgageAmount = Math.max(clientOpportunity.desiredMortgageAmount, clientOpportunity.currentMortgageAmount + clientOpportunity.renovationValue + clientOpportunity.currentBalance);
				    	// TODO What about if they want extra funds?  Where is that variable arriving from?         	
				    	
				    }             
				    else if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Approval, clientOpportunity.whatIsYourLendingGoal)){
				    	expectedMortgageAmount = clientOpportunity.propertyValue + clientOpportunity.renovationValue - clientOpportunity.downpaymentAmount;
				    	
				    	// Add in assistant task to request documents from Realtor
//				    	Task task2 = new Task("Please request the following documents from the realtor on this opportunity (see task details)", 
//				    			"The following documents should be requested of the realtor: \n"
//				            			+ "Copy of MLS Listing \n"
//				            			+ "Copy of Offer to Purchase \n", 
//				            			"Assistant", 
//				            			"Opportunity.ID", 
//				            			"Opportunity.Team", 
//				            			deadline, 
//				            			"", 
//				            			(double)0, 
//				            			0.1, 
//				            			currentDate, 
//				            			deadline);   
//				        assistantTasks.add(task2);
				    	
				        
				        // On next iteration of algorithm, add in the note that assistant needs to generate the "Property Checklist Report" in the event it is a private sale.
				    }             
				    else if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Prequalify, clientOpportunity.whatIsYourLendingGoal)){
				    	expectedMortgageAmount = clientOpportunity.desiredMortgageAmount;
				    	// Next iteration of Algo ... Determine if Broker notes are required  Maximum will be determined after paperwork is verified and put into proposal        	
				    }
				    
				    boolean acceptableLTV = false;
				    double loanToValue = (expectedMortgageAmount / valueOfProperty) * 100;
				    clientOpportunity.ltv = loanToValue;   
				    
				    // In the future build out a potential path to reduce paperwork on some refinances / purchases through using stated income ... 

//
//				    		Here is the way we want it to work ...
//				    		We want to run both Stated and non-stated ...
//				    		To to this ...
//				    		If stated is not feasible, forget about it.
//				    		We need to get the lowest cost stated and lowest cost non-stated ...
//				    		Then if the rate differential is high enough, don't provide the stated option. If the rate differential is low enough, go the stated income path in terms of documentation.
//
//				    		Make this a future enhancement
//				    if (loanToValue <= 65){
//				    	acceptableLTV = true;
//				    }
//				    else{
//				    	acceptableLTV = false;
//				    }
//				    StringBuilder OpportunityInfo = new StringBuilder();
//				    
//				    if (acceptableLTV == true && beaconAcceptable == true){
//				    	statedIncomeDeal = true;
//				    	String assistNote4 = "This Opportunity potentially qualifies upon stated income and may allow for reduced paperwork.";
//				    	OpportunityInfo.append(assistNote4);
//				    }
//				    else{
//				    	statedIncomeDeal = false;
//				    }
				    
				    if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Refinance, clientOpportunity.whatIsYourLendingGoal)){
				    	Task task2 = new Task("Check to ensure this property is not double counted", 
				    			"PLEASE NOTE: This opportunity is a refinance opportunity.  "
				            			+ "The Property being refinanced is potnetially being inaccurately "
				            			+ "double counted.  Please ensure the property "
				            			+ "DOES NOT exist in the applicants' lists of "
				            			+ "properties, mortgages, and incomes (if a rental).  "
				            			+ "If it is present in the Applicant Record, "
				            			+ "please delete it from the list of properties, "
				            			+ "mortgages and incomes in the applicant record.", 
				            			"Assistant", 
				            			"Opportunity.ID", 
				            			"Opportunity.Team", 
				            			deadline, 
				            			"", 
				            			(double)0, 
				            			0.1, 
				            			currentDate, 
				            			deadline);   
				        assistantTasks.add(task2);
				    }
				    
				    boolean webDocAdded = false;
				    boolean noaDocAdded = false;
				    
				    for (Applicant applicant : clientOpportunity.applicants) {
				    	double totalApplicantIncome = 0;
				    	if (applicant.incomes != null && applicant.incomes.size() > 0 && statedIncomeDeal == false){
				    		
				    		if (clientOpportunity.applicants.size() > 1)
				    			listOfDocsEmail.append(applicant.applicantName + "\n");
				    		
				        	boolean addedSelfEmployed = false;
				        	boolean addedEmployed = false;
				        	
				    		for (Income currentIncome : applicant.incomes) {
				        		noaDocAdded = createIncomeDocsList(
										listOfDocsEmail, listOfIncomeDocsWeb,
										webDocAdded, noaDocAdded, applicant,
										addedSelfEmployed, addedEmployed,
										currentIncome);	
				        	}
				    	}				    	
				    	
				        // Monthly Child / Spousal Support Received .... 
				    	if (applicant.monthlychildsupport > 0 && statedIncomeDeal == false){
//				    		double annualChildSupport = applicant.monthlychildsupport * 12;
//				    		totalApplicantIncome = totalApplicantIncome + annualChildSupport;
				    		
				    		// Assistant Tasks
							createChildSupportDocs(listOfDocsEmail,
									listOfMiscDocsWeb, webDocAdded, applicant);	
				    	}
				    	
				    	
				    	// Include this Applicant's Total Income into the Total Deal Income
				    	//totalDealIncome = totalDealIncome + totalApplicantIncome;
				    	
//				    	// Create Liability oriented tasks related to the Applicant
//				    	double monthlyLiabilities = 0;
//				        double totalLiabilities = 0;
//				        ArrayList<String> applicantLiabilitiesList = new ArrayList<String>();
//				        
//				        if (applicant.liabilities != null && applicant.liabilities.size() > 0 && statedIncomeDeal == false){
//				        	for (Liability currentLiability : applicant.liabilities) {
//
//				        		// Check if Fixed or Revolving
//				        		if (currentLiability.creditBalance > currentLiability.payOff)
//				        		{
//				        			// I may need to double check to see if it is an LOC ... LOC may need different treatment ... 
//				        			double calculateLiabilityPayment = 0;  
//				        			double revisedBalance = currentLiability.creditBalance - currentLiability.payOff;
//				        			if (currentLiability.type.equalsIgnoreCase("R") && currentLiability.creditBalance > currentLiability.payOff)
//				        			{
//				        				calculateLiabilityPayment = Math.max(10, revisedBalance * 0.03);
//				        			}
//				        			else {
//				        				if (currentLiability.monthlyPayment != null)
//				        					calculateLiabilityPayment = Double.parseDouble(currentLiability.monthlyPayment);
//				        			}
//
//				        			if (currentLiability.name.equalsIgnoreCase("j")) 
//				        			{
//				        				// Check to see if Liability has already been added to a different Applicant
//				        				boolean alreadyadded = false;
//				        				for (Liability jointliability : joinLiabilities)
//				        				{
//				        					if (currentLiability.name.equals(jointliability.name)
//				        							&& currentLiability.creditBalance.equals(jointliability.creditBalance)
//				        							&& currentLiability.creditLimit.equals(jointliability.creditLimit)
//				        							&& currentLiability.opened.equals(jointliability.opened))
//				        						alreadyadded = true;
//
////	            					Task AssistNote1 = new Task(AlgoNote.TypeOfNote.AssistantAction, AlgoNote.Priority.Med, "Please Note that there is a Joint Liability liability from " + jointliability.business + ".  This liability shows on the credit Bureau of the Secondary applicant, but has been removed form the list of liabilites for " + applicant.applicantName + ".");
////	            					assistantTasks.add(AssistNote1);
//				        				}
//
//				        				if (alreadyadded==false)
//				        				{
//				        					monthlyLiabilities += calculateLiabilityPayment;
//				        					totalLiabilities += currentLiability.creditBalance;
//				        					joinLiabilities.add(currentLiability);
//				        					applicantLiabilitiesList.add(currentLiability.business);
//				        				}                        
//				        			}
//				        			else {
//				        				monthlyLiabilities += calculateLiabilityPayment;
//				        				totalLiabilities += currentLiability.creditBalance;
//				        				applicantLiabilitiesList.add(currentLiability.business);
//				        			}
//				        		}
//				        		else {
//				        			// Note may be required here during next iteration of Algorithm  
//				        		}
//				        	}
//				    	}
				        
				     // Monthly Child / Spousal Support Paid ....  
//				    	createChildSupportTask(statedIncomeDeal, applicant);
//				    	
//				    	totalDealLiabilityPayments = totalDealLiabilityPayments + monthlyLiabilities;
//				    	totalDealLiabilities  = totalDealLiabilities + totalLiabilities;
//				        applicant.totalIncome = totalApplicantIncome;
//				        applicant.totalApplicantIncome = totalApplicantIncome;
//				        applicant.totalApplicantLiabilities = totalLiabilities;
//				        applicant.totalApplicantLiabilityPayments = monthlyLiabilities;
//				        
//				    	String listOfLiabilities = "";
//				        for(String aValue : applicantLiabilitiesList) {
//				        	listOfLiabilities += aValue + "\n";
//				        }        
//				        if (statedIncomeDeal == false){
//				        	Calendar currentDate2 = new GregorianCalendar();
//				            Calendar deadline2 = new GregorianCalendar();
//				            deadline2.add(Calendar.HOUR, 1);
//							Task task2 = new Task("Send Open Credit Verification Message", 
//									"Please send debt payments email to " + applicant.applicantName+ " confirming they have presently open credit lines with the following Companies: " + "\n" + listOfLiabilities,
//							"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
//				            assistantTasks.add(task2);		            
//				        }
				        
//	         // Need to Determine whether there are any debts which might be able to be paid off from Downpayment ... 
//                double downpaymentRatio = clientOpportunity.downpaymentAmount / valueOfProperty;
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
//                Task task3 = new Task("Create a new lead for " + applicant.applicantName + "s property at " + currentProperty.name, 
//		                "Please create a lead for " + applicant.applicantName+ "s property at " + currentProperty.name + ".  "
//		                + "It has a value of " + currentProperty.value.toString() + " with a mortgage of " + matchingMortgage.balance + " "
//		                + "Interest Rate = " + matchingMortgage.interestRate + " "
//		                + "Monthly Payment = " + matchingMortgage.monthlyPayment + " "
//		                + "Renewal Date = " + matchingMortgage.renewal,
//						"Broker", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
//		                brokerTasks.add(task3);
				        
				    	// Create a task related to sending an email for late payment reasons ... 
				        createLatePaymentsTask(statedIncomeDeal, applicant);	        	

				        // Create a task related to sending an email for Collections Status... double applicantCollectionsBalance = 0;
				        //double applicantCollectionsBalance = 0;
				        createCollectionsTask(statedIncomeDeal, applicant);
				        //applicant.totalApplicantCollections = applicantCollectionsBalance;
				        
				    	// Create Asset / Properties oriented tasks related to the Applicant
				        double mortgagePayments = 0;
				        double mortgagesBalance = 0;
				        double propertyTaxTotal = 0;
				        double propertyValueTotal = 0;
				        double condoFeesTotal = 0;
				        int propertyCounter = 0;  // Use to count total Heating costs    TODO What about refinance no double count?
				        if (applicant.properties != null && applicant.properties.size() > 0 && statedIncomeDeal == false){
				            for (Property currentProperty : applicant.properties) {
				            	if (currentProperty.selling != true){
				            		boolean includePropertyInCalculatons = true;
				            		if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Refinance, clientOpportunity.whatIsYourLendingGoal)){
				            			if (clientOpportunity.propertyValue == currentProperty.value 
				            					&& (double)currentProperty.annualTax == clientOpportunity.propertyTaxes){
				            				//refinancePropertyNotInTotals = true;
				            				includePropertyInCalculatons = false;
				            				// TODO Test this skips the current Property in all Logic ... 
				            				// TODO DO we need to add in an assistant Task remove property from Properties List? 
				            				// If we have them remove, all remaining logic will need to reference The Property in the opportunity, not 
				            				// the property in applicant.Properties list.
				            			}
				                	}
				            		
									Mortgage matchingMortgage;
									int PropertyID = 0;
									if (currentProperty.propertyId != null)
										PropertyID = Integer.parseInt(currentProperty.propertyId);
									if (applicant.mortgages != null && applicant.mortgages.size() > 0){ 
										for (Mortgage currenMortgage : applicant.mortgages) {
											
											int MortgageID = 1;
											if (currentProperty.propertyId != null)
												MortgageID = Integer.parseInt(currenMortgage.propertyId);
											
											if (MortgageID == PropertyID && includePropertyInCalculatons == true){
												matchingMortgage = currenMortgage;
												
												if (currenMortgage.monthlyPayment != null)
													mortgagePayments = mortgagePayments + Double.parseDouble(currenMortgage.monthlyPayment);
												if (currenMortgage.balance != null)
													mortgagesBalance = mortgagesBalance + Double.parseDouble(currenMortgage.balance);
												
												Calendar currentDate2 = new GregorianCalendar();
									            Calendar deadline2 = new GregorianCalendar();
									            deadline2.add(Calendar.HOUR, 1);
								                Task task3 = new Task("Create a new lead for " + applicant.applicantName + "s property at " + currentProperty.name, 
								                "Please create a lead for " + applicant.applicantName+ "s property at " + currentProperty.name + ".  "
								                + "It has a value of " + currentProperty.value.toString() + " with a mortgage of " + matchingMortgage.balance + " "
								                + "Interest Rate = " + matchingMortgage.interestRate + " "
								                + "Monthly Payment = " + matchingMortgage.monthlyPayment + " "
								                + "Renewal Date = " + matchingMortgage.renewal,
												"Broker", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
								                brokerTasks.add(task3);
											}
										}
					            	}
									
									condoFeesTotal = createPropertyTasks(
											listOfDocsEmail,
											listOfPropertyDocsWeb,
											condoFeesTotal, currentProperty,
											includePropertyInCalculatons);
									
//						            if (currentProperty.value > 0 && includePropertyInCalculatons == true){ // Screen out - Property IDs must match ... 
//						            	propertyTaxTotal = propertyTaxTotal + (double)currentProperty.annualTax;
//						            	propertyValueTotal = propertyValueTotal + currentProperty.value;
//						            	propertyCounter = propertyCounter + 1;
//						            }
					            }
							}      
				    	}
				        // Need to Add the Mortgages, Property Values, etc for all Applicants
//				        totalDealPropertyHeating = totalDealPropertyHeating + ((double)propertyCounter * 85);
//				        totalDealCondoFees = totalDealCondoFees + condoFeesTotal;
//				        totalDealPropertyTaxes = totalDealPropertyTaxes + propertyTaxTotal;
//				        totalDealPropertyValues = totalDealPropertyValues + propertyValueTotal;
//				        totalDealMortgagesBalance = totalDealMortgagesBalance + mortgagesBalance;
//				        totalDealMortgagesPayments = totalDealMortgagesPayments + mortgagePayments;
//				        applicant.totalApplicantMortgages = mortgagesBalance;
//				        applicant.totalApplicantMortgagePayments = mortgagePayments;
//				        applicant.totalApplicantPropertyTaxes = propertyTaxTotal;
//				        applicant.totalApplicantCondoFees = condoFeesTotal;
				        
				    	// Create "Personal Info" oriented tasks related to the Applicant
				        createBankruptcyTasks(listOfDocsEmail,listOfMiscDocsWeb, applicant);
				        createOrderlyDebtPaymentTasks(listOfDocsEmail,listOfMiscDocsWeb, applicant);
				        createNonResidentTasks(applicant);				        
				        createImmigrantTasks(applicant);	 
				        
				       // Calculate Applicant Assets for later usage in determining positive NetWorth
//				        double ApplicantAssetTotal = 0;
//				        for (Asset asset : applicant.assets) {
//							if (asset.value != null){
//								ApplicantAssetTotal = ApplicantAssetTotal + asset.value;
//							}
//						}
//				        if (applicant.money != null)
//				        	ApplicantAssetTotal = ApplicantAssetTotal + Double.parseDouble(applicant.money);
//				        
//				        ApplicantAssetTotal = ApplicantAssetTotal + propertyValueTotal;
//				        applicant.totalApplicantAssets = ApplicantAssetTotal;
//				        allApplicantsTotalAssets = allApplicantsTotalAssets + ApplicantAssetTotal;
				        
				        // End of Applicants Loop	
				        listOfDocsEmail.append("\n");
					}
				    
				    webDocAdded = true;
				}
				else {
					Calendar currentDate2 = new GregorianCalendar();
				    Calendar deadline2 = new GregorianCalendar();
				    deadline2.add(Calendar.HOUR, 1);
					Task task2 = new Task("This Opportunity has no Applicants - Please attach appropriate Applicants", 
							"",
					"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
				    assistantTasks.add(task2);
				}
				
				
				// TODO Include refinance Oriented tasks ... 
				if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Refinance, clientOpportunity.whatIsYourLendingGoal)  ){
					expectedMortgageAmount = clientOpportunity.currentMortgageAmount + clientOpportunity.renovationValue + clientOpportunity.currentBalance + clientOpportunity.additionalAmount;
					
					createRefinanceTasks();
				       
				}             
				else if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Approval, clientOpportunity.whatIsYourLendingGoal)){
					expectedMortgageAmount = clientOpportunity.propertyValue + clientOpportunity.renovationValue - clientOpportunity.downpaymentAmount;
					createPurchaseTasks(listOfDocsEmail, listOfPropertyDocsWeb);
				    
					
				}             
				else if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Prequalify, clientOpportunity.whatIsYourLendingGoal)){
					expectedMortgageAmount = clientOpportunity.desiredMortgageAmount;   	
				}   
				        
				createCOFTasks();
				
				// Create Deal oriented tasks related to the Applicant including Property Related & Down payment Related        
				// Rennovation        
				createRennovationTasks();
				// Outbuildings
				createOutbuildingTasks();
				
				createWellWaterTasks();
				
				double valueOfProperty = clientOpportunity.propertyValue + clientOpportunity.renovationValue;
				if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Prequalify, clientOpportunity.whatIsYourLendingGoal)){        
					valueOfProperty = clientOpportunity.desiredMortgageAmount + clientOpportunity.downpaymentAmount;  
					// Confirm this is the way to determine LTV in case of Prequalify
					// TODO Test Webform Downpayment Amount ... Confirm from Webform fields All 3 LendingGoals 
					// TODO Put a Note in here to check the web form to confirm whether there is a question "how much are you planning to buy"? Kendall thinks so - if so, alternative method 
				}
				else {
					valueOfProperty = clientOpportunity.propertyValue + clientOpportunity.renovationValue;
				}  
				
				if (valueOfProperty == 0){
					Calendar currentDate2 = new GregorianCalendar();
				    Calendar deadline2 = new GregorianCalendar();
				    deadline2.add(Calendar.HOUR, 1);
					Task task7 = new Task("Property Value has been passed to the Underwriting Application as '0'.  "
							+ "Please confirm the approximate value of the Property, "
							+ "save the Property Value in 'General' tab of the Opportunity Record and re-create Assistant "
							+ "and Broker Tasks using the Assistant Tasks Button",
					"",
					"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
				    assistantTasks.add(task7);
				}
				
				// TODO Checkwith Audrey Around these Notes and their "timing"
//      if (this.underwritingType.equals(TypeOfUnderwriting.PostSelection))
//      {
//          // Broker Tasks
//      	Task brokerNote1 = new Task(AlgoNote.TypeOfNote.BrokerAction, AlgoNote.Priority.Med, "Follow up with the client if additional paperwork is required to support in regards to the pulling of the title and additional criteria");
//          brokerNotes.add(brokerNote1);
//          // Assistant Tasks
//          Task assistNote1 = new Task(AlgoNote.TypeOfNote.AssistantAction, AlgoNote.Priority.High, "Depending on the LTV order an appraisal to confirm the value from the lenders approved appraiser list for 'this' specific lending area");
//          assistantNotes.add(assistNote1);
//          Task assistNote2 = new Task(AlgoNote.TypeOfNote.AssistantAction, AlgoNote.Priority.High, "Pull title of the property through the legal description provided or by civic address including postal code");
//          assistantNotes.add(assistNote2);
//          Task assistNote3 = new Task(AlgoNote.TypeOfNote.AssistantAction, AlgoNote.Priority.Med, "Review title for any additional charges on the title");
//          assistantNotes.add(assistNote3);
//          Task assistNote4 = new Task(AlgoNote.TypeOfNote.AssistantAction, AlgoNote.Priority.Med, "Notify broker and client of any additional charges on the property such as lis pendis, caveat, judgements, etc.");
//          assistantNotes.add(assistNote4);
//      }
//      else if (this.underwritingType.equals(TypeOfUnderwriting.FinalVerify)){
//      	Task assistNote5 = new Task(AlgoNote.TypeOfNote.AssistantAction, AlgoNote.Priority.Low, "Notify lender and send supporting documents");
//          assistantNotes.add(assistNote5);
//      }
				
				createAppraisalTasks();

				
				/*
					Please verify with client the reason, status of additional charges on Title
					Please notifiy Assistant of reason, status of additional charges on Title
					Please request supporting documentation to match with Title
					 
					
				 * 
				 */
				    
				double loanToValue = (expectedMortgageAmount / valueOfProperty) * 100;
				clientOpportunity.ltv = loanToValue;     
				boolean highRatioProperty = false; 
				double insuranceCost = 0;
				if (statedIncomeDeal == false){

				    double unInsureLTVBelow = (double)750000 * ((double)80 / (double)100); // Calculate the portion of the Below threshold Property Value which needs no insurance
				    double insuredThresholdForProperty = 0;
				    if (valueOfProperty > 750000)
				    {
				        double propertyValueAboveThreshold = valueOfProperty - 750000;
				        double uninsuredLTVAbove = propertyValueAboveThreshold * (50 / 100);
				        insuredThresholdForProperty = unInsureLTVBelow + uninsuredLTVAbove;
				    }
				    else
				    {
				        insuredThresholdForProperty = unInsureLTVBelow;

				    }
				    // double requiredNonInsureDownpayment = valueOfProperty - insuredThresholdForProperty;
				    highRatioProperty = createHighRatioTasks(highRatioProperty, insuredThresholdForProperty);
				    
				    if (highRatioProperty == true
				        || clientOpportunity.lenderRequiresInsurance == true)
				    {
				        boolean nonTraditionalDownPayment = false;
				        if (SelectionHelper.compareSelection(AlgoOpportunity.DownpaymentSource.Gift, clientOpportunity.downPaymentComingFrom)
				            || SelectionHelper.compareSelection(AlgoOpportunity.DownpaymentSource.Borrowed, clientOpportunity.downPaymentComingFrom))
				            nonTraditionalDownPayment = true;
				        else
				            nonTraditionalDownPayment = false;


//	                	UnderwritingBase underwriteBase = new UnderwritingBase();
//	                	InsurerProducts insureProd = new InsurerProducts(underwriteBase);
//				        insuranceCost = InsurancePremiums.calculateInsurancePremium(insureProd);
//				        
				    }
				    expectedMortgageAmount = expectedMortgageAmount + insuranceCost;

				}        
				// Check on whether an Appraisal is required.  Appraisal is ordered after lender and product is selected.
//        if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Approval, clientOpportunity.whatIsYourLendingGoal)
//        		|| SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Refinance, clientOpportunity.whatIsYourLendingGoal)){
//        	if (highRatioProperty == false){
//        		Task assistNote3 = new Task(AlgoNote.TypeOfNote.AssistantAction, AlgoNote.Priority.High, "Please order Appriasal for this property");
//        		assistantTasks.add(assistNote3);                
//        	}
//        	if (clientOpportunity.mls == null){
//        		Task assistNote3 = new Task(AlgoNote.TypeOfNote.AssistantAction, AlgoNote.Priority.High, "Please Revew Purchase agreement to determine whether a private sale.  If so, please order appraisal.");
//        		assistantTasks.add(assistNote3);
//        	}
//        }   
				
				// Create Tasks for Downpayment
				createDownPaymentTasks(listOfDocsEmail, listOfDownpayDocsWeb);
				
				
				// Calculate MortgageCosts
				// Get Products so that interest Rates Can be determined http://localhost:8080/UnderwrittingApp/services/desiredProduct/3433
//				List<Product> productsToConsider = findProductsToConsider();     
//				System.out.println("productsToConsider:" + productsToConsider.size());
//				
//				if (productsToConsider.size() > 0)
//				{
//				    for (Product product : productsToConsider) {
//				    	if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year5, "6")){
//				        	potentialProduct = product;
//							break;
//				    	}				
//					}
//				}
//				else {
//					Calendar currentDate2 = new GregorianCalendar();
//				    Calendar deadline2 = new GregorianCalendar();
//				    deadline2.add(Calendar.HOUR, 1);
//					Task task7 = new Task("Qualifying Product is not found in Database.  Please report this incident to your manager, Technical Support or an Underwriter.",
//					"",
//					"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
//				    assistantTasks.add(task7);
//				}
//				
//				double productInterestRate = potentialProduct.interestRate; // Used on 5 Yr or Longer Terms or Actual Deals
//				double interestRateVar = potentialProduct.qualifyingRate; // Used on Prequalification for < 5 Year or for LOC or for Variable
				
				int amortization = createAmortizationTasks();
				
//				// This also needs discussion ... Terms < 5 Years use qualifying rate for getting approved, but actually pay the product rate.
//				// TODO ... Match this to the type of product in the All Products Algorithm.
//				// For the purposes of underwriting, one rate is used.  For the purposes of fitness, another rate should be used. 
//				double fixedPayment = CalculatePayments.calculateMortgagePayment(productInterestRate, expectedMortgageAmount, amortization, AlgoOpportunity.FrequencyDesired.Monthly);
//				//double fixedCost = fixedPayment;
//				
//				// Calculate the payment for an Variable
//				double variablePayment = CalculatePayments.calculateMortgagePayment(interestRateVar, expectedMortgageAmount, amortization, AlgoOpportunity.FrequencyDesired.Monthly);
//				//double variableCost = variablePayment;
//				// TODO Confirm Payment Amounts with Audrey and Filelogix.
//				// Calculate the payment for an LOC // TODO Confirm interest only payment works here ... 
//				double paymentLOC = CalculatePayments.calculateMonthlyLOCPayment(interestRateVar, expectedMortgageAmount);
//				//double LOCCost = paymentLOC;
//				
//				
//				// In the event the Opportunity is a Prequlification and not yet owned, The expected rental income needs to be added to the total Income
//				double expectedPaymentAmount = variablePayment;
//				if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Prequalify, clientOpportunity.whatIsYourLendingGoal)){
//					
//					totalDealIncome = totalDealIncome + (clientOpportunity.monthlyRentalIncome * 12);
//					
//					if (SelectionHelper.compareSelection(AlgoProduct.TreatmentOfRental.AddToIncome, potentialProduct.rentalIncomeTreatment))
//				    {
//						if (clientOpportunity.monthlyRentalIncome != null)
//							totalDealIncome = totalDealIncome + ((clientOpportunity.monthlyRentalIncome * (potentialProduct.rentalIncomePercent / 100)* 12));
//						else
//						{
//							// TODO Insert note that the rental income field needs tobe filled in.
//						}
//				    }
//					else {
//						double amountOfRentToSubtract = ((clientOpportunity.monthlyRentalIncome * (potentialProduct.rentalIncomePercent / 100) * 12));
//				     double annualizedExpectedPayment = expectedPaymentAmount * 12;   
//						//Calculate the net cashflow on the property ... 
//						double NetCashFlow = amountOfRentToSubtract - annualizedExpectedPayment;
//				        if (NetCashFlow > 0)
//				        {
//				     	   totalDealIncome = totalDealIncome + NetCashFlow;                   	
//				        }
//				        else
//				        {
//				     	   totalDealLiabilities = totalDealLiabilities + NetCashFlow;
//				        }		    						
//					}
//				}
				
//				// Determine GDS and TDS
//				// get Taxes and heat for property being financed and remove monthly payment if already being counted
//				double taxesForThisProperty = 0;
//				double heatForThisProperty = 0;
//				double condoFeesForThisProperty = 0;
//				
//				if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Prequalify, clientOpportunity.whatIsYourLendingGoal)){
//					taxesForThisProperty = (clientOpportunity.desiredMortgageAmount + clientOpportunity.downpaymentAmount) * 0.01;
//					heatForThisProperty = 85;       
//				}
//				else if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Approval, clientOpportunity.whatIsYourLendingGoal)){
//					taxesForThisProperty = clientOpportunity.propertyTaxes;
//					heatForThisProperty = 85;
//					condoFeesForThisProperty = clientOpportunity.condoFees;
//				}
//				else if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Refinance, clientOpportunity.whatIsYourLendingGoal)){
//					
//					// In theory, this should always be counted because these totals have been "Skipped" if the property is already in properties list.
//					// TODO the above logic needs to be tested.
//					heatForThisProperty = 85;
//					taxesForThisProperty = clientOpportunity.propertyTaxes;
//					condoFeesForThisProperty = clientOpportunity.condoFees;
//					totalDealMortgagesBalance = totalDealMortgagesBalance + expectedMortgageAmount;
//					totalDealPropertyValues = totalDealPropertyValues + valueOfProperty;
//					totalDealMortgagesPayments = totalDealMortgagesPayments + fixedPayment;
//					// TODO Check to ensure the property being underwritten is not being double counted for Value, Mortgages, Condo Fees, Heat.
//					//if (refinancePropertyNotInTotals == true){
////        		heatForThisProperty = 85;
////        		taxesForThisProperty = clientOpportunity.propertyTaxes;
////        		condoFeesForThisProperty = clientOpportunity.condoFees;
////        		totalDealMortgagesBalance = totalDealMortgagesBalance + expectedMortgageAmount;
////        		totalDealPropertyValues = totalDealPropertyValues + valueOfProperty;
////        		totalDealMortgagesPayments = totalDealMortgagesPayments + fixedPayment;
////        		// TODO May Need notes to Double check this ... 
////        		
////    		}
////        	else{
////        		heatForThisProperty = 0;
////        		taxesForThisProperty = 0;
////        		condoFeesForThisProperty = 0;
////        	}
//					
//				}
//				totalDealPropertyTaxes = totalDealPropertyTaxes + taxesForThisProperty;
//				totalDealPropertyHeating = totalDealPropertyHeating + heatForThisProperty;
//				totalDealCondoFees = totalDealCondoFees + condoFeesForThisProperty;
//				double AnnualizedHeating = totalDealPropertyHeating * 12;
//				double AnnualizedCondoFees = totalDealCondoFees * 12;
//				double AnnualizedLiabilities = totalDealLiabilityPayments * 12;
//				// TODO ... Need to Create Tests for all "condo fees, mortgages, liabilities, etc, etc to verify totals ... )
//				// Ensure Renovation Scenarios are included ... 
//
//				double totalDealGDSAmount = AnnualizedHeating + AnnualizedCondoFees + AnnualizedLiabilities;
//				double OpportunityGDS = totalDealGDSAmount / totalDealIncome * 100;
//				double AnnualizedMortgagePayments = totalDealMortgagesPayments * 12;
//				double totalDealTDSAmount = totalDealGDSAmount + AnnualizedMortgagePayments;
//				double OpportunityTDS = totalDealTDSAmount / totalDealIncome * 100;
//				
//				// TODO run a calculation To determine whether any applicants should be removed from the Algorithm ... 
//				int ApplicantCounter = 0;
//				double MaxIndividualGDS = 0;
//				int MaxGDSApplicant = 0;
//				boolean GDSIssues = false;
//				
//				double IndividualGDS = 0;
//				for (Applicant appl : clientOpportunity.applicants) {
//					if (appl.totalApplicantIncome > 0){
//						if (appl.totalApplicantLiabilityPayments > 0){
//							IndividualGDS = (appl.totalApplicantLiabilityPayments * 12) / appl.totalApplicantIncome;
//							if (IndividualGDS > MaxIndividualGDS){
//								MaxIndividualGDS = IndividualGDS;
//								MaxGDSApplicant = ApplicantCounter;
//							}
//						}
//					}
//					else{
//						if (appl.totalApplicantLiabilityPayments > 0){
//							IndividualGDS = (appl.totalApplicantLiabilityPayments * 12) / 10;
//							if (IndividualGDS > MaxIndividualGDS){
//								MaxIndividualGDS = IndividualGDS;
//								MaxGDSApplicant = ApplicantCounter;
//							}
//						}
//					}
//					ApplicantCounter++;
//				}
				
//				// The Applicant with the Worst GDS has been identified above, the below code removes them from the Deal Totals and Recalculates GDS.
//				double newtotalDealGDSAmount = totalDealGDSAmount - (clientOpportunity.applicants.get(MaxGDSApplicant).totalApplicantLiabilityPayments * 12);
//				double newTotalDealIncome = totalDealIncome - clientOpportunity.applicants.get(MaxGDSApplicant).totalApplicantIncome;
//				double newGDSRatio = newtotalDealGDSAmount / newTotalDealIncome * 100;
//				
//				if (OpportunityGDS > 30 && OpportunityGDS <= 35 && statedIncomeDeal == false){
//					if (newGDSRatio < OpportunityGDS){
//						Calendar currentDate2 = new GregorianCalendar();
//				        Calendar deadline2 = new GregorianCalendar();
//				        deadline2.add(Calendar.HOUR, 1);
//				        Task task3 = new Task("Contact client to see if paperwork can be simplified by removing applicant.", 
//				        		"NOTE: This Opportunity has a potential Challenge with GDS.  "
//				        		+ "If " + clientOpportunity.applicants.get(MaxGDSApplicant).applicantName + " "
//								+ "is included in the application, the GDS ratio is " 
//								+ OpportunityGDS + ".  However, if they are removed from the application, "
//								+ "the GDS ratio drops to " + newGDSRatio + ".  "
//								+ "Please contact the Client to determine whether it is necessary to gather paperwork from " 
//								+ clientOpportunity.applicants.get(MaxGDSApplicant).applicantName + " or whether they can be "
//								+ "removed from the application to both strengthen and simplify it.",
//						"Broker", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
//				        brokerTasks.add(task3);
//					}
//					else{
//						
//					}
//				}
//				else if (OpportunityGDS > 35 && statedIncomeDeal == false){ // AND Over 30 ... 
//					if (newGDSRatio < OpportunityGDS){
//						Calendar currentDate2 = new GregorianCalendar();
//				        Calendar deadline2 = new GregorianCalendar();
//				        deadline2.add(Calendar.HOUR, 1);
//				        Task task3 = new Task("Contact client to see if paperwork can be simplified by removing applicant.", 
//				        		"NOTE: This Opportunity has a potential Challenge with GDS.  "
//				        		+ "If " + clientOpportunity.applicants.get(MaxGDSApplicant).applicantName + " "
//								+ "is included in the application, the GDS ratio is " 
//								+ OpportunityGDS + ".  However, if they are removed from the application, "
//								+ "the GDS ratio drops to " + newGDSRatio + ".  "
//								+ "Please contact the Client to determine whether it is necessary to gather paperwork from " 
//								+ clientOpportunity.applicants.get(MaxGDSApplicant).applicantName + " or whether they can be "
//								+ "removed from the application to both strengthen and simplify it.  "
//								+ "If " + clientOpportunity.applicants.get(MaxGDSApplicant).applicantName + " is included in "
//								+ "the application, the GDS ratio is " + OpportunityGDS + ".  However, if they are removed "
//								+ "from the application, the GDS ratio drops to " + newGDSRatio + ".  Please contact the "
//								+ "client and explore the concept of removing " + clientOpportunity.applicants.get(MaxGDSApplicant).applicantName + "from the application.",
//						"Broker", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
//				        brokerTasks.add(task3);                
//					}
//					else if (statedIncomeDeal == false){
//						
//					}
//				}
//				else if (OpportunityGDS < 30 && statedIncomeDeal == false){  
//					
//				}
//				
//				// TODO Need to Test This ...
//				// Then run the same calculation without the applicant who has poor income ratios ...   
//				if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Prequalify, clientOpportunity.whatIsYourLendingGoal)){
//					
//					// Calculate the Maximum Mortgage Amount with All Applicants
//					double MaxTDSAmountNoCondo = totalDealIncome * 42 / 100;
//				    double gdsWithoutCondo = totalDealGDSAmount;
//				    double maxAnnualPaymentNoCondo = MaxTDSAmountNoCondo - gdsWithoutCondo; // TODO Something is Broken in this calculation ...  
//				    double maxMonthlyPaymentNoCondo = maxAnnualPaymentNoCondo / 12;
//				    double maximumMortgageNoInsurance = CalculatePayments.calculateMaximumMortgageAmount(productInterestRate, 25, maxMonthlyPaymentNoCondo);
//				    double maximumMortgageNoCondo = maximumMortgageNoInsurance - insuranceCost;
//				    System.out.println("maximumMortgageNoCondo: " + maximumMortgageNoCondo);
//					
//
//					// Calculate the Maximum Mortgage Amount with removing worst Credit Applicant
//				    
//					double newMaxTDSAmountNoCondo = newTotalDealIncome * 42 / 100;
//				    double newgdsWithoutCondo = newtotalDealGDSAmount;
//				    double newMaxAnnualPaymentNoCondo = newMaxTDSAmountNoCondo - newgdsWithoutCondo; // TODO Something is Broken in this calculation ...  
//				    double newMaxMonthlyPaymentNoCondo = newMaxAnnualPaymentNoCondo / 12;
//				    double newMaximumMortgageNoInsurance = CalculatePayments.calculateMaximumMortgageAmount(productInterestRate, 25, newMaxMonthlyPaymentNoCondo);
//				    double NewMaximumMortgageNoCondo = newMaximumMortgageNoInsurance - insuranceCost;
//				    System.out.println("maximumMortgageNoCondo: " + NewMaximumMortgageNoCondo);
//					
//					double IncreasedMaxMortgage = (NewMaximumMortgageNoCondo - maximumMortgageNoCondo) * 0.9; // 90% of calculated amount to adjust for potential income discrepancies
//					
//					if (IncreasedMaxMortgage > 0 && statedIncomeDeal == false){
//						Calendar currentDate2 = new GregorianCalendar();
//				        Calendar deadline2 = new GregorianCalendar();
//				        deadline2.add(Calendar.HOUR, 1);
//				        Task task3 = new Task("Contact client to see if paperwork can be simplified by removing applicant.", 
//						"NOTE: This Opportunity has a potential Opportunity with GDS.  If " + clientOpportunity.applicants.get(MaxGDSApplicant).applicantName + " is "
//						+ "removed from the the application, they might be able to prequalify for" 
//						+ Math.round(IncreasedMaxMortgage) + " more than they otherwise would be able to.  "
//						+ "Please have a conversation with the clients around this option.",
//						"Broker", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
//				        brokerTasks.add(task3);    
//					}
//				}
				
				createCloseDateTasks();   
		        
				
				createBuildingTasks();
				
				Calendar currentDate2 = new GregorianCalendar();
				Calendar deadline2 = new GregorianCalendar();
				deadline2.add(Calendar.MINUTE, 10);
				createMiscAssistantTasks(currentDate2, deadline2);
				
				createDocumentationMessageTask(listOfDocsEmail, currentDate2,
						deadline2);
				
				
//				for (String string : documentsListApplicant1) {
//					listOfDocs.append("- " + string + "\n");
//				}  listOfDocsRealtor  to realtorDocuments1 
				
				AlgoNote docListNote = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.High, listOfDocsEmail.toString(),"clientDocumentationList1");
				dealNotes.add(docListNote);
				
				AlgoNote docListRealtorNote = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.High, listOfDocsRealtor.toString(),"realtorDocuments1");
				dealNotes.add(docListRealtorNote);
				
				if (listOfIncomeDocsWeb.length() > 4){
					AlgoNote docIncomeNote = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.High, listOfIncomeDocsWeb.toString(),"clientIncomeDocListWeb");
					dealNotes.add(docIncomeNote);
				}
				
				if (listOfPropertyDocsWeb.length() > 4){
					AlgoNote docPropertyNote = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.High, listOfPropertyDocsWeb.toString(),"clientPropertyDocListWeb");
					dealNotes.add(docPropertyNote);
				}
				
				if (listOfDownpayDocsWeb.length() > 4){
					AlgoNote docDownpayNote = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.High, listOfDownpayDocsWeb.toString(),"clientDownpayDocListWeb");
					dealNotes.add(docDownpayNote);		
				}
				
				if (listOfMiscDocsWeb.length() > 4){
					AlgoNote docMiscNote = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.High, listOfMiscDocsWeb.toString(),"clientMiscDocListWeb");
					dealNotes.add(docMiscNote);
				}
				
				 
				System.out.println("DesiredProductAlgo end");
			} catch (Exception e) {
				e.printStackTrace();
				Calendar currentDate2 = new GregorianCalendar();
				Calendar deadline2 = new GregorianCalendar();
				deadline2.add(Calendar.HOUR, 1);
				e.printStackTrace();
				Task task2 = new Task("There was an ERROR IN THE UNDERWRITING APPLICATION DESIRED PRODUCT ALGO 1747.  Please copy-paste the text in the description of this task into an email and send it to techsupport@visdom.ca.", 
						"OpportunityID" + clientOpportunity.applicationNo + " - "  + e.getMessage() + " - " + e.getStackTrace().toString(),
				"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
	            assistantTasks.add(task2); 
			}
	       
    }

	private void createMiscAssistantTasks(Calendar currentDate2,
			Calendar deadline2) {
		Task task7 = new Task("Please review all client Documentation and revise all field in the Opportunity and Applicant Records to match the returned Documentation.",
		"",
		"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
		assistantTasks.add(task7);
		
		Task task8 = new Task("Please review Residence history for all applicants.  Ensure there is at least 3 years prior history and please delete any duplicate addresses that may have been imported from Equifax.",
		"",
		"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
		assistantTasks.add(task8);

		Task task9 = new Task("Please review Incomes list in each applicant.  See task Details for context.",
				"Please review Income history for all applicants.  Ensure there is at least 3 years prior history and please delete any duplicate incomes / Employers that may have been imported from Equifax.  If the employer / income is not duplicate, but appears to be imported from Equifax (has no 'type') please check the historical box for that income.",
		"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
		assistantTasks.add(task9);
	}

	private void createDocumentationMessageTask(StringBuilder listOfDocsEmail,
			Calendar currentDate2, Calendar deadline2) {
		StringBuilder clientsNames = new StringBuilder();
		int applicantCount = 0;
		for (Applicant applicant :clientOpportunity.applicants) {
			if (applicantCount < clientOpportunity.applicants.size() - 2){
				applicantCount = applicantCount + 1;
				clientsNames.append(applicant.applicantName + " and ");
			}
			else if (applicantCount == clientOpportunity.applicants.size() - 1){
				applicantCount = applicantCount + 1;
				clientsNames.append(applicant.applicantName);
			}
		}
		
		Calendar currentDate3 = new GregorianCalendar();
		Calendar deadline3 = new GregorianCalendar();
		deadline3.add(Calendar.MINUTE, 1);
		Task task10 = new Task("Send Documentation Request to " + clientsNames.toString() + " using the Documentation Required template" ,
		"Send request using the Documentation Required template (copy paste the following list) for the following Documents: " + listOfDocsEmail.toString(),
		"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
		assistantTasks.add(task10);
	}

	private void createBuildingTasks() {
		if (SelectionHelper.compareSelection(AlgoOpportunity.BuildingFunds.Complete, clientOpportunity.buildingFunds)
				|| SelectionHelper.compareSelection(AlgoOpportunity.BuildingFunds.ProgressDraw, clientOpportunity.buildingFunds)
				|| SelectionHelper.compareSelection(AlgoOpportunity.BuildingFunds.SelfBuildDraw, clientOpportunity.buildingFunds))
		{        	
			Calendar currentDate2 = new GregorianCalendar();
		    Calendar deadline2 = new GregorianCalendar();
		    deadline2.add(Calendar.HOUR, 1);
			Task task7 = new Task("Check for floor plan and specifications.",
			"Please confirm that floor plan and specifications were included with the Copy of the Offer to Purchase.  "
			+ "If not, please request these items using the 'Building Documentation Template'.",
			"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
		    assistantTasks.add(task7);            
		}
	}

	private void createCloseDateTasks() {
		Calendar currentDate5 = new GregorianCalendar();
		Calendar deadline5 = new GregorianCalendar();
		deadline5.add(Calendar.HOUR, 1);
		Task task5 = new Task("Confirm the expected Closing Date of this Opportunity and enter it into Expected Closing Date field in the general tab.", 
		"",
		"Broker", "Opportunity.ID", "Opportunity.Team", deadline5,"",(double)0, 0.1, currentDate5, deadline5);   
		brokerTasks.add(task5);
	}

	private int createAmortizationTasks() {
		int amortization = 25;
		if (amortization < 25){
			Calendar currentDate2 = new GregorianCalendar();
		    Calendar deadline2 = new GregorianCalendar();
		    deadline2.add(Calendar.HOUR, 1);
		    Task task3 = new Task("Confirm Amortization.", 
		    "Please Note that " + clientOpportunity.applicants.get(0).applicantName  + " has selected an amortization of " + clientOpportunity.amortization.toString() + ".  "
		    		+ "This will create a higher monthly payment amount. "
		    		+ "Please confirm the client does not wish a 25 year amortization."
		    		+ " It is wise to provide the client with some education around this fact and to manage their expectations.",
			"Broker", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
		    brokerTasks.add(task3); 
		}
		return amortization;
	}

	private void createDownPaymentTasks(StringBuilder listOfDocsEmail,
			StringBuilder listOfDownpayDocsWeb) {
		if (clientOpportunity.downpaymentAmount > 0){
			listOfDocsEmail.append("\n");
			listOfDocsEmail.append("Down Payment " + "\n");	
				
		}
		ArrayList<String> downpaymentSources = new ArrayList<String>();
		
		if (clientOpportunity.bankAccount > 0){
			//documentsListApplicant1.add("Downpayment Verification - Bank Account.");
			downpaymentSources.add("Downpayment Verification - Bank Account.");
			listOfDocsEmail.append("\t - " + "Verification of $" + clientOpportunity.personalCashAmount + " from Bank Account (Bank Statement)" + "\n");	
		    listOfDownpayDocsWeb.append("Bank Account Statement" + "\n");
		}
		if (clientOpportunity.personalCashAmount > 0){
			//documentsListApplicant1.add("Downpayment Verification - Bank Account.");
			downpaymentSources.add("Downpayment Verification - Bank Account.");
			listOfDocsEmail.append("\t - " + "Verification of $" + clientOpportunity.personalCashAmount + " from Bank Account (Bank Statement)" + "\n");	
		    listOfDownpayDocsWeb.append("Bank Account Statement" + "\n");
		}
		if (clientOpportunity.rrspAmount > 0){
			//documentsListApplicant1.add("Downpayment Verification - RRSP.");
			downpaymentSources.add("Downpayment Verification - RRSP.");
			listOfDocsEmail.append("\t - " + "Verification of $" + clientOpportunity.rrspAmount + " from RRSP (RRSP Statements)" + "\n");
			listOfDownpayDocsWeb.append("RRSP Statements" + "\n");
		}
		if (clientOpportunity.giftedAmount > 0){
			// Broker Tasks
			//documentsListApplicant1.add("Downpayment Verification - Gift Letter.");
			downpaymentSources.add("Downpayment Verification - Gift Letter.");
			listOfDocsEmail.append("\t - " + "Verification of $" + clientOpportunity.giftedAmount + " from gifted sources (Gift Letter)" + "\n");
			listOfDownpayDocsWeb.append("Gift Letter" + "\n");
			
			Calendar currentDate2 = new GregorianCalendar();
		    Calendar deadline2 = new GregorianCalendar();
		    deadline2.add(Calendar.HOUR, 1);
		    Task task3 = new Task("Contact client regarding Gift Letter Downpayment ", 
		    		"Please explain gift letter requirements and provide the client with a copy of the gift letter template. "
		    		+ "If the gift letter has not been received from the client within 2 days from sending the sample template, "
		    		+ "please follow up with the client.",
			"Broker", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
		    brokerTasks.add(task3);            
		}
		if (clientOpportunity.borrowedAmount > 0){
			//documentsListApplicant1.add("Downpayment Verification - Borrowed Amount.");
			downpaymentSources.add("Downpayment Verification - Borrowed Amount.");
			listOfDocsEmail.append("\t - " + "Verification of $" + clientOpportunity.borrowedAmount + " from borrowed sources (Loan, Line of Credit or Credit Card Statement)" + "\n");
			listOfDownpayDocsWeb.append("Borrowed Downpayment Statement (LOC)" + "\n");
			
		}
		if (clientOpportunity.saleOfExistingAmount > 0){
			//documentsListApplicant1.add("Downpayment Verification - Sale of Existing Property, Listing Sheet");
			downpaymentSources.add("Downpayment Verification - Sale of Existing Property, Listing Sheet");
			listOfDocsEmail.append("\t - " + "Verification of $" + clientOpportunity.saleOfExistingAmount + " from Sale of Existing Property (Listing Sheet or Offer to Purchase)" + "\n");
			listOfDownpayDocsWeb.append("Listing Sheet or Offer to Purchase" + "\n");
		}
		if (clientOpportunity.existingEquityAmount > 0){
			//documentsListApplicant1.add("Downpayment Verification - Existing Equity");
			downpaymentSources.add("Downpayment Verification - Existing Equity");
			listOfDocsEmail.append("\t - " + "Verification of $" + clientOpportunity.existingEquityAmount + " from Existing Equity (Property address with existing equity)" + "\n");
			listOfDownpayDocsWeb.append("Existing Equity Verification" + "\n");
			
			Calendar currentDate2 = new GregorianCalendar();
		    Calendar deadline2 = new GregorianCalendar();
		    deadline2.add(Calendar.HOUR, 1);
		    Task task3 = new Task("The client intends to utilize equity from their existing property to purchase this property.  Please create a lead for the existing proeprty and contact the client to determine their plans for the existing property.", 
		    		"",
			"Broker", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
		    brokerTasks.add(task3);
		}
		if (clientOpportunity.sweatEquityAmount > 0){http://localhost:8080/UnderwrittingApp/services/desiredProduct/3433
			//documentsListApplicant1.add("Downpayment Verification - Sweat Equity");
			downpaymentSources.add("Downpayment Verification - Sweat Equity");
			listOfDocsEmail.append("\t - " + "Verification of $" + clientOpportunity.sweatEquityAmount + " from Sweat Equity (Property address with sweat equity)" + "\n");
			listOfDownpayDocsWeb.append("Sweat Equity Verficiation" + "\n");
			
		}
		if (clientOpportunity.secondaryFinancingAmount > 0){
			//documentsListApplicant1.add("Downpayment Verification - Secondary Financing");
			downpaymentSources.add("Downpayment Verification - Secondary Financing");
			listOfDocsEmail.append("\t - " + "Verification of $" + clientOpportunity.secondaryFinancingAmount + " from Secondary Financing (Loan Agreement)" + "\n");
			listOfDownpayDocsWeb.append("Secondary Financing Verification" + "\n");
			
			Calendar currentDate2 = new GregorianCalendar();
		    Calendar deadline2 = new GregorianCalendar();
		    deadline2.add(Calendar.HOUR, 1);
		    Task task3 = new Task("The client intends to utilize secondary financing from their existing property to purchase this property.  Please create a lead for the existing proeprty and contact the client to determine their plans for the existing property.", 
		    		"",
			"Broker", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
		    brokerTasks.add(task3);
		}
		if (clientOpportunity.otherAmount > 0){
			//documentsListApplicant1.add("Downpayment Verification - Other Amount");
			downpaymentSources.add("Downpayment Verification - Other Amount");
			listOfDocsEmail.append("\t - " + "Verification of $" + clientOpportunity.otherAmount + " from Other Sources (Explanation in writing of other source of downpayment)" + "\n");
			listOfDownpayDocsWeb.append("Other Sources Verification" + "\n");
			
			Calendar currentDate2 = new GregorianCalendar();
		    Calendar deadline2 = new GregorianCalendar();
		    deadline2.add(Calendar.HOUR, 1);
		    Task task3 = new Task("Please contact the client to clarify the source of funds for Other Downpayment.", 
		    		"",
			"Broker", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
		    brokerTasks.add(task3);            
		}
		
		if (downpaymentSources.size() > 0){
			for (String string : downpaymentSources) {
				Calendar currentDate2 = new GregorianCalendar();
		        Calendar deadline2 = new GregorianCalendar();
		        deadline2.add(Calendar.HOUR, 1);
		    	Task task7 = new Task("When you receive the downpayment verification, confirm " + string,
		    	"Edit the " + string + " field in the Opportunity Record General Tab."
		    			+ "When all sources are verified, Please add up all Downpayment amounts from all sources "
		    			+ "and enter the total in the Down Payment Amount field in the General Tab of the Opportunity Record.  "
		    			+ "Also please select dropdown in Down Payment Coming From field that most closly matches the "
		    			+ "downpayment source of the largest 'sources' amount.",
				"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
		        assistantTasks.add(task7);
			}
			
			
		}
	}

	private boolean createHighRatioTasks(boolean highRatioProperty,
			double insuredThresholdForProperty) {
		if (expectedMortgageAmount > insuredThresholdForProperty || clientOpportunity.desiredMortgageAmount > insuredThresholdForProperty)
		{
			highRatioProperty = true;
			Calendar currentDate2 = new GregorianCalendar();
		    Calendar deadline2 = new GregorianCalendar();
		    deadline2.add(Calendar.HOUR, 1);
		    Task task3 = new Task("Please Note that this Opportunity requires Insurance from CHMC or GE.  It is wise to provide the client with some education around this fact and to manage their expectations.", 
		    		"",
			"Broker", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
		    brokerTasks.add(task3);
		}
		return highRatioProperty;
	}

	private void createAppraisalTasks() {
		if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Refinance, clientOpportunity.whatIsYourLendingGoal)  ){
			double valueOfProperty = clientOpportunity.propertyValue + clientOpportunity.renovationValue;
			double existingEquity = valueOfProperty - expectedMortgageAmount;
			Calendar currentDate2 = new GregorianCalendar();
		    Calendar deadline2 = new GregorianCalendar();
		    deadline2.add(Calendar.HOUR, 1);
			Task task7 = new Task("Enter existing equity as a downpayment source",
			"When Appraisal of approximately " + valueOfProperty + "is received, "
			+ "please enter existing equity amount of " + existingEquity + " "
			+ "into the downpayment field and existing equity field.",
			"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
		    assistantTasks.add(task7);
		}
	}

	private void createWellWaterTasks() {
		if (clientOpportunity.water == "Well"){
			Calendar currentDate2 = new GregorianCalendar();
		    Calendar deadline2 = new GregorianCalendar();
		    deadline2.add(Calendar.HOUR, 1);
			Task task7 = new Task("Send email to customer using 'Well Water' email template to confirm the water quality for the future lender.",
			"",
			"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
		    assistantTasks.add(task7);
		}
	}

	private void createOutbuildingTasks() {
		if (clientOpportunity.outbuildingsValue > 0)
		{
			Calendar currentDate2 = new GregorianCalendar();
		    Calendar deadline2 = new GregorianCalendar();
		    deadline2.add(Calendar.HOUR, 1);
			Task task7 = new Task("Send email to customer using 'outbuildings values' email template to confirm the number of outbuildings, type of outbuildings and approximate value of outbuildings",
			"If you do not receive a return email from client email within 48 hours, email Broker asking them to contact client to get the required information.  " +
			"Once you receive back the information, Please verify that the value of outbuildings on the appraisal is greater than or equal to " + clientOpportunity.outbuildingsValue,
			"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
		    assistantTasks.add(task7);
		    
		}
	}

	private void createRennovationTasks() {
		if (clientOpportunity.renovationValue > 0)
		{ 
		    Calendar currentDate2 = new GregorianCalendar();
		    Calendar deadline2 = new GregorianCalendar();
		    deadline2.add(Calendar.HOUR, 1);
		    Task task3 = new Task("Please contact the client and confirm their the estimate of " + clientOpportunity.renovationValue + " will add a minimum of " + clientOpportunity.renovationValue + " to the value of their property", 
		    		"Once you have contacted the "
		            		+ "client to confirm the estimated cost of the Renovation and an estimate of the increased Property Value, please edit the opportunity 'Renovation Value' to reflect "
		            		+ "the estimated INCREASE in Property Value (Not total property value) rather than the cost of renovation.  "
		            		+ "Please note that it is important to be reasonably accurate with the increase in value or else it can "
		            		+ "create a problem if the client thinks they are going to get 100,000 for renovations, but the appraisal "
		            		+ "once completed only adds 20,000 of value.",
			"Broker", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
		    brokerTasks.add(task3);
		    // Assistant Tasks
		    
		    Task task7 = new Task("Send the 'Renovation Funds Process' email to the client", 
					"Please contact the client using the 'Renovation Funds Process' email template to inform them that most "
					+ "lenders only provide funds on Completion, not Progress.  "
					+ "This template must include a section about Getting Renovation Quotes which states they must provide 2 "
					+ "quotes from a certified contractor verifying the renovations to be completed and their cost.  "
					+ "The Lender will utilize the lower of the 2 quotes to determine post renovation property value",
					"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
		            assistantTasks.add(task7);
		    
		}
	}

	private void createCOFTasks() {
		if (clientOpportunity.conditionOfFinancingDate != null){
//					SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
//				    String cofDateString = clientOpportunity.conditionOfFinancingDate.to;
			java.util.Date COFdate;
		    try {
				COFdate = clientOpportunity.conditionOfFinancingDate; //formatter.parse(cofDateString);
				 Calendar dateNow = Calendar.getInstance();
				 dateNow.add(Calendar.DAY_OF_YEAR, 7);
				 if (COFdate.after(dateNow.getTime())){
					Calendar currentDate2 = new GregorianCalendar();
		            Calendar deadline2 = new GregorianCalendar();
		            deadline2.add(Calendar.HOUR, 1);
		        	Task task7 = new Task("URGENT - This property has a COF date very Soon. Prioritize it and press all parties for urgency", 
					"",
					"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
		            assistantTasks.add(task7);
				 }
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
	}

	private void createPurchaseTasks(StringBuilder listOfDocsEmail,
			StringBuilder listOfPropertyDocsWeb) {
		Calendar currentDate2 = new GregorianCalendar();
		Calendar deadline2 = new GregorianCalendar();
		deadline2.add(Calendar.HOUR, 1);
		Task task7 = new Task("Confirm Property Details from MLS listing sheet", 
				"When MLS Sheet is received, please verify all details pertaining to legal description (lot,block,plan), property value, annual property taxes, square footage, heat, water, garage size, property style and property type",
		"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
		assistantTasks.add(task7);
		
		listOfDocsEmail.append("Property:");
		listOfDocsEmail.append("- Realtor name and contact information");
		listOfDocsEmail.append("- Copy of MLS Listing for " + clientOpportunity.address + " (Or let us know we should get this from your realtor and we can contact them.) " + "\n");	
		listOfPropertyDocsWeb.append("MLS Listing for " + clientOpportunity.address + "\n");
		
		listOfDocsEmail.append("- Copy of Offer to Purchase for " + clientOpportunity.address + " including waivers and addendums (Or let us know we should get this from your realtor and we can contact them.) " + "\n");	
		listOfPropertyDocsWeb.append("Offer to Purchase for " + clientOpportunity.address + "\n");
		
		if (clientOpportunity.monthlyRentalIncome > 0){
			String doc = "Lease agreement or Schedule A (Rental Fair Market Value Assessment) to verify the likely rental income from the property being financed ";
			listOfDocsEmail.append("\t - " + doc + "\n");	
		}
	}

	private void createRefinanceTasks() {
		Calendar currentDate2 = new GregorianCalendar();
		Calendar deadline2 = new GregorianCalendar();
		deadline2.add(Calendar.HOUR, 1);
		Task task1 = new Task("Please ENSURE the Property being re-financed is Entered in the List of Prpoerties in the Assets Tab of the Primay Applicant before running All Product Algo", 
				"",
		"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
		assistantTasks.add(task1);
		
		Task task2 = new Task("When Appraisal (or MLS Sheet in event of purchase) is received, please verify all details pertaining to legal description (lot,block,plan); square footage, heat, water, garage size, property style and property type", 
				"",
		"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
		assistantTasks.add(task2);
		  
		Task task3 = new Task("When Property Tax Assessment is received, please verify annual property taxes and legal description", 
				"",
		"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
		assistantTasks.add(task3);
		
		Task task4 = new Task("When Property Tax Assessment is received, please confirm assessed value and increase property value by 15% ", 
				"",
		"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
		assistantTasks.add(task4);
		
		Task task5 = new Task("When Property Tax Assessment is received, pull title to confirm mortgage holder and value", 
				"",
		"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
		assistantTasks.add(task5);
		
		Task task6 = new Task("When Title is received verify to see if there are any additional charges, if so contact Broker to find out status of these additional charges.", 
				"",
		"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
		assistantTasks.add(task6);
		
		Task task7 = new Task("When Mortgage Statement is received please verify current mortgage amount, interest rate, monthly payment, renewal date, mortgage type.", 
				"",
		"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
		assistantTasks.add(task7);
	}

	private void createImmigrantTasks(Applicant applicant) {
		if (applicant.immigrationDate != null){
			Calendar dateNow = Calendar.getInstance();
		    dateNow.add(Calendar.YEAR, -4);
		    if (applicant.immigrationDate.after(dateNow.getTime())){
		    	Calendar currentDate2 = new GregorianCalendar();
		        Calendar deadline2 = new GregorianCalendar();
		        deadline2.add(Calendar.HOUR, 1);
				Task task2 = new Task("Send  " + applicant.applicantName + " the non-Resident email Template requesting internaional credit bureau, work permits, etc", 
						"Please ask for a copy of immigration permit / work visa for " + applicant.applicantName + ".  Confirm that a minimum of 6 months remains on the immigration period." + "\n" +
								"Please ask for a copy of an international credit bureau for " + applicant.applicantName + ".  This will help in the approval process.",
				"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
		        assistantTasks.add(task2);
		        
		    }
		}
	}

	private void createNonResidentTasks(Applicant applicant) {
		if (applicant.nonResident == true){ 
		    Calendar currentDate2 = new GregorianCalendar();
		    Calendar deadline2 = new GregorianCalendar();
		    deadline2.add(Calendar.HOUR, 1);
			Task task2 = new Task("Send  " + applicant.applicantName + " the non-Resident email Template requesting internaional credit bureau, work permits, etc", 
					"",
			"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
		    assistantTasks.add(task2);

		    Task task3 = new Task("Non-Resident - " + applicant.applicantName, 
		    		"Please note the client is non-resident.  The range of potnetial products they can qualify for might be limited.",
			"Broker", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
		    brokerTasks.add(task3);
		}
	}

	private void createOrderlyDebtPaymentTasks(StringBuilder listOfDocsEmail,
			StringBuilder listOfMiscDocsWeb, Applicant applicant) {
		if (applicant.orderlyDebtPayment == true){
			//documentsListApplicant1.add(applicant.applicantName + " - Orderly Debt Payments Docs");
			listOfDocsEmail.append("\t - " + applicant.applicantName + " - Orderly Debt Payments Docs" + "\n");
		    listOfMiscDocsWeb.append("- " + applicant.applicantName + " - Orderly Debt Payments Docs" + "\n");
		    
			Calendar currentDate2 = new GregorianCalendar();
		    Calendar deadline2 = new GregorianCalendar();
		    deadline2.add(Calendar.HOUR, 1);
			Task task2 = new Task("Review Orderly Debt Payments paperwork to confirm dicharge date and ensure there was no property involved in the bankruptcy.", 
					"",
			"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
		    assistantTasks.add(task2);

		    Task task3 = new Task("Orderly Debt Payments - " + applicant.applicantName, 
		    		"Please note the client has had Orderly Debt Payments.  The range of potnetial products they can qualify for might be limited.",
			"Broker", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
		    brokerTasks.add(task3);
		}
	}

	private void createBankruptcyTasks(StringBuilder listOfDocsEmail,
			StringBuilder listOfMiscDocsWeb, Applicant applicant) {
		if (applicant.bankruptcy == true){
			//documentsListApplicant1.add(applicant.applicantName + " - Bankruptcy Discharge"); 
			listOfDocsEmail.append("\t - " + applicant.applicantName + " - Bankruptcy Discharge" + "\n");
		    listOfMiscDocsWeb.append("Bankruptcy Discharge" + "\n");
		   		            
		    Calendar currentDate2 = new GregorianCalendar();
		    Calendar deadline2 = new GregorianCalendar();
		    deadline2.add(Calendar.HOUR, 1);
			Task task2 = new Task("Review bankruptcy paperwork to confirm dicharge date and ensure there was no property involved in the bankruptcy.", 
					"",
			"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
		    assistantTasks.add(task2);

		    Task task3 = new Task("Prior Bankruptcy - " + applicant.applicantName, 
		    		"Please note the client has had a bankruptcy.  The range of potnetial products they can qualify for might be limited.",
			"Broker", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
		    brokerTasks.add(task3);
		}
	}

	private double createPropertyTasks(StringBuilder listOfDocsEmail,
			StringBuilder listOfPropertyDocsWeb, double condoFeesTotal,
			Property currentProperty, boolean includePropertyInCalculatons) {
		if (currentProperty.value > 0 && currentProperty.owed > 0 && currentProperty.moCondoFees == 0){
			listOfDocsEmail.append("\t - " + currentProperty.name + " - Property Tax Statement" + "\n");										
			listOfPropertyDocsWeb.append("Property Tax Statement for " + currentProperty.name + "\n");	
		    // documentsListApplicant1.add(doc);
			listOfDocsEmail.append("\t - " + currentProperty.name + " - Mortgage Statement" + "\n");
			listOfPropertyDocsWeb.append("Mortgage Statement for " + currentProperty.name + "\n");
			//documentsListApplicant1.add(currentProperty.name + " - Mortgage Statement");
		    String AssistNote6 = "Verify the Mortgage Amount, interest rate and payment for the client's property at " + currentProperty.name + "from the returned Mortgage Statement.";
		    Calendar currentDate2 = new GregorianCalendar();
		    Calendar deadline2 = new GregorianCalendar();
		    deadline2.add(Calendar.HOUR, 1);
			Task task2 = new Task(AssistNote6, 
					"",
			"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
		    assistantTasks.add(task2);
		}
		else if (currentProperty.value > 0 && currentProperty.owed > 0 && currentProperty.moCondoFees > 0){
			         
		    String AssistNote6 = "Verify the Mortgage Amount, interest rate and payment for the client's property at " + currentProperty.name + "from the returned Mortgage Statement. ";
		    
		    listOfDocsEmail.append("\t - " + currentProperty.name + " - Property Tax Statement" + "\n");
		    listOfPropertyDocsWeb.append("Property Tax Statement for " + currentProperty.name + "\n");		
		    // documentsListApplicant1.add(doc);
			listOfDocsEmail.append("\t - " + currentProperty.name + " - Mortgage Statement" + "\n");
			listOfPropertyDocsWeb.append("Mortgage Statement for " + currentProperty.name + "\n");	
			//documentsListApplicant1.add(currentProperty.name + " - Mortgage Statement");
			listOfDocsEmail.append("\t - " + currentProperty.name + " - Condo Documents" + "\n");
			listOfPropertyDocsWeb.append("Condo Documents for " + currentProperty.name + "\n");
			//documentsListApplicant1.add(currentProperty.name + " - Condo Documents");
		    Calendar currentDate2 = new GregorianCalendar();
		    Calendar deadline2 = new GregorianCalendar();
		    deadline2.add(Calendar.HOUR, 1);
			Task task2 = new Task(AssistNote6, 
					"",
			"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
		    assistantTasks.add(task2);
		    
		    if (includePropertyInCalculatons == true)
		    	condoFeesTotal = condoFeesTotal + currentProperty.moCondoFees;
		}
		else if (currentProperty.value > 0 && currentProperty.owed == 0 && currentProperty.moCondoFees > 0){
			listOfDocsEmail.append("\t - " + currentProperty.name + " - Property Tax Statement" + "\n");	
			listOfDocsEmail.append("\t - " + currentProperty.name + " - Condo Documents" + "\n");
			listOfPropertyDocsWeb.append("Property Tax Statement for " + currentProperty.name + "\n");	
			listOfPropertyDocsWeb.append("Condo Documents for " + currentProperty.name + "\n");
		    if (includePropertyInCalculatons == true)
		    	condoFeesTotal = condoFeesTotal + currentProperty.moCondoFees;
		}
		else if (currentProperty.value > 0 && currentProperty.owed == 0 && currentProperty.moCondoFees == 0){  
			listOfDocsEmail.append("\t - " + currentProperty.name + " - Property Tax Statement" + "\n");
			listOfPropertyDocsWeb.append("Property Tax Statement for " + currentProperty.name + "\n");		   
		}	
		
		if (currentProperty.value > 0){
			String AssistNote5 ="Verify the Taxes and Property Value for the client's property at " + currentProperty.name + " from the returned Property Tax Statement ";
			Calendar currentDate2 = new GregorianCalendar();
		    Calendar deadline2 = new GregorianCalendar();
		    deadline2.add(Calendar.HOUR, 1);
			Task task2 = new Task(AssistNote5, 
					"",
			"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
		    assistantTasks.add(task2);	
		}
		return condoFeesTotal;
	}

	private void createCollectionsTask(boolean statedIncomeDeal,
			Applicant applicant) {
		String listOfCollections = "";      
		if (applicant.collection != null && statedIncomeDeal == false){ 
			if (applicant.collection.size() > 0){
				for (Collection collect : applicant.collection) {
		    		listOfCollections += collect.name + ": " + collect.balance + ", ";
		    		//applicantCollectionsBalance = applicantCollectionsBalance + (double)collect.balance;
				}
				Calendar currentDate2 = new GregorianCalendar();
		        Calendar deadline2 = new GregorianCalendar();
		        deadline2.add(Calendar.HOUR, 1);
				Task task2 = new Task("Include Collections section in Credit Verification Message", 
						"In the debt payments email to " + applicant.applicantName+ " please include a section requesting an explanation of the status of the following outstanding Collections: " + "\n" + listOfCollections,
				"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
		        assistantTasks.add(task2);
			}		        	
		}
	}

	private void createLatePaymentsTask(boolean statedIncomeDeal,
			Applicant applicant) {
		String listOfLatePayments = "";            
		if (applicant.latePayments != null && statedIncomeDeal == false){
			if (applicant.latePayments.size() > 0){
				for (LatePayment latePayment : applicant.latePayments) {
		    		listOfLatePayments += latePayment.name + ": " + latePayment.days + "\n";
				}
				Calendar currentDate2 = new GregorianCalendar();
		        Calendar deadline2 = new GregorianCalendar();
		        deadline2.add(Calendar.HOUR, 1);
				Task task2 = new Task("Include Late Payment section in Credit Verification Message", 
						"In the debt payments email to " + applicant.applicantName+ " please include a section requesting an explanation of the following late payment events: " + listOfLatePayments,
				"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
		        assistantTasks.add(task2);	
			}		        	
		}
	}

	private void createChildSupportTask(boolean statedIncomeDeal,
			Applicant applicant) {
		if (applicant.monthlySupportPayment != null && applicant.monthlySupportPayment > 0 && statedIncomeDeal == false)
		{	
			// Subtract from Income Method totalApplicantIncome = totalApplicantIncome - (applicant.monthlySupportPayment * 12);
		    // Notes for indicating that Support has been subtracted from income ... What does Lender / Broker / Assist need to know / do?
		    // Add to Liabilities Method ... 
			//monthlyLiabilities += applicant.monthlySupportPayment;
			
			Calendar currentDate2 = new GregorianCalendar();
		    Calendar deadline2 = new GregorianCalendar();
		    deadline2.add(Calendar.HOUR, 1);
			Task task2 = new Task("Verify that " + applicant.applicantName + " is paying monthly support payments of " + applicant.monthlySupportPayment, 
					"Verify supporting documentation that " + applicant.applicantName + " is paying " + applicant.monthlySupportPayment + " in support payments.  "
					+ "A copy of divorce agreement will be required.",
			"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
		    assistantTasks.add(task2);
		}
	}

	private void createChildSupportDocs(StringBuilder listOfDocsEmail,
			StringBuilder listOfMiscDocsWeb, boolean webDocAdded,
			Applicant applicant) {
		String doc = "Divorce agreement or Separation agreement  - " + applicant.applicantName ;
		listOfDocsEmail.append("\t - " + doc + "\n");
		String income4Web = doc + "\n";
		listOfMiscDocsWeb.append(income4Web);	
		// documentsListApplicant1.add(doc);	
		String doc1 = "Bank statements (6 months) which verify support payments  - " + applicant.applicantName ;
		listOfDocsEmail.append("\t - " + doc1 + "\n");
		String doc2 = "Bank statements (6 months)" + "\n";							
		if (webDocAdded == false){
			listOfMiscDocsWeb.append(doc2);
		}
	}

	private boolean createIncomeDocsList(StringBuilder listOfDocsEmail,
			StringBuilder listOfIncomeDocsWeb, boolean webDocAdded,
			boolean noaDocAdded, Applicant applicant,
			boolean addedSelfEmployed, boolean addedEmployed,
			Income currentIncome) {
		if (currentIncome.historical != true){   
			Calendar currentDate1 = new GregorianCalendar();
		    Calendar deadline1 = new GregorianCalendar();

		    deadline1.add(Calendar.HOUR, 1);
			if (SelectionHelper.compareSelection(AlgoIncome.IncomeType.Employed, currentIncome.typeOfIncome)){
//				    					if (currentIncome.annualIncome != null)
//				    						totalApplicantIncome = totalApplicantIncome + Double.parseDouble(currentIncome.annualIncome);
				
		        // Notes for indicating the income is Employed ... What does Lender / Broker / Assist need to know / do?
				// Assistant Tasks
				
				String letter = "\t" + "Letter of Employment for your job with " + currentIncome.business + "\n";
				String paystubs = "\t" + "Recent paystubs for your job with " + currentIncome.business + "\n";
				if (addedEmployed == false){
					listOfDocsEmail.append(letter);
					listOfDocsEmail.append(paystubs);
					String loeWeb = "Letter of Employment " + "\n";
					String payStubWeb = "PayStubs " + "\n";
					if (webDocAdded == false && noaDocAdded == false){
						listOfIncomeDocsWeb.append(loeWeb);
						listOfIncomeDocsWeb.append(payStubWeb);
					}
					
					addedEmployed = true;
				}
				
				//documentsListApplicant1.add(letter);
				if (addedSelfEmployed == false){
					Calendar year1 =  Calendar.getInstance();
					
					int currentMonth = year1.get(Calendar.MONTH);
					if (currentMonth <= 5){
						int startYear = year1.get(Calendar.YEAR) - 2;
						//year1.add(Calendar.YEAR, -2);
						String noa1Year = "\t - " + startYear + " Notice of Assessment from Revenue Canada" + "\n";
						listOfDocsEmail.append(noa1Year);
						String noa1YearWeb =  startYear  + " Notice of Assessment" + "\n";
						if (webDocAdded == false && noaDocAdded == false){
							listOfIncomeDocsWeb.append(noa1YearWeb);
						}
						int priorYear = startYear - 1;
						String noa2Year = "\t - " + priorYear + " Notice of Assessment from Revenue Canada" + "\n";
						listOfDocsEmail.append(noa2Year);
						String noa2YearWeb = priorYear + " Notice of Assessment" + "\n";
						if (webDocAdded == false && noaDocAdded == false){
							listOfIncomeDocsWeb.append(noa2YearWeb);				    						
						}
						noaDocAdded = true;
					}
					else{
						int startYear = year1.get(Calendar.YEAR) - 1;
						String noa1Year = "\t - " + startYear+ " Notice of Assessment from Revenue Canada" + "\n";
						listOfDocsEmail.append(noa1Year);
						String noa1YearWeb =  startYear  + " Notice of Assessment" + "\n";
						if (webDocAdded == false && noaDocAdded == false){
							listOfIncomeDocsWeb.append(noa1YearWeb);
						}
						
						int priorYear = startYear - 1;
						String noa2Year = "\t - " + priorYear + " Notice of Assessment from Revenue Canada" + "\n";
						listOfDocsEmail.append(noa2Year);
						String noa2YearWeb = priorYear + " Notice of Assessment" + "\n";
						if (webDocAdded == false && noaDocAdded == false){
							listOfIncomeDocsWeb.append(noa2YearWeb);				    						
						}	
						noaDocAdded = true;
					}
					
					addedSelfEmployed = true;
				}
				
				Task task2 = new Task("Confirm Employment Income with " + currentIncome.business + " of $" + currentIncome.annualIncome + " for " + applicant.applicantName, 
						"While Verifying Incomes, Please note that each type of income needs to be on it's own row in the list of incomes. "
						+ "(Eg. Vehicle allowance cannot be combined with Employment Income.  Each needs to be on a Separate row.)" + "\n" + 
						"Confirm letter of employent for " + applicant.applicantName + " from " + currentIncome.business 
						+ " is not older than 60 days, if so an updated letter of employment is required." + "\n" +
						"When the letter of employent for " + applicant.applicantName + " from " + currentIncome.business + 
						" is received, please verify job title, length of time employed, income amounts, and overtime amounts."	+ "\n" +	
						"When the NOAs for " + applicant.applicantName + " are received, confirm there are no outstanding taxes owing.  "
						+ "If taxes are owing then request confirmation that outstanding taxes have been paid in full." + "\n" +
						"Verity supporting documents match the Total Annual Income in the list of incomes for " + applicant.applicantName + "\n" +
						"For " + applicant.applicantName + "'s income, please edit to use the larger of 2 year average NOA or employment letter or pro-rated paystubs."+ "\n", 
				"Assistant", 
				"Opportunity.ID", 
				"Opportunity.Team", 
				deadline1, 
				"", 
				(double)0, 
				1.1, 
				currentDate1, 
				deadline1);   
		        assistantTasks.add(task2);
		        
				Task brokertask2 = new Task("Verify employment Letter for " + applicant.applicantName, 
						"Make a phone call to " + applicant.applicantName + "'s employer (" + currentIncome.business + 
						") verifying start date, job title, guaranteed salary-wage, annual income, any overtime and any probation", 
				"Broker", 
				"Opportunity.ID", 
				"Opportunity.Team", 
				deadline1, 
				"", 
				(double)0, 
				0.1, 
				currentDate1, 
				deadline1);   
				brokerTasks.add(brokertask2);    
			}
			else if (SelectionHelper.compareSelection(AlgoIncome.IncomeType.SelfEmployed, currentIncome.typeOfIncome)){
				
				//if (currentIncome.annualIncome != null)
					//totalApplicantIncome = totalApplicantIncome + (Double.parseDouble(currentIncome.annualIncome) * 1.15);
				
				// Broker Tasks
				Task brokertask2 = new Task("Contact " + applicant.applicantName + " to understand details of Self-Employment and reflect findings in Deal Notes", 
						"Contact " + applicant.applicantName + " to confirm they are operating as an corporation or sole-prior" + "\n" +
						"Follow up with client if documentation confirms self employed income indicates less than 3 years" + "\n" +
						"Find out from " + applicant.applicantName + " how long they have been in the industry and are they doing the same line of work with previous employer", 
				"Broker", 
				"Opportunity.ID", 
				"Opportunity.Team", 
				deadline1, 
				"", 
				(double)0, 
				0.1, 
				currentDate1, 
				deadline1);   
		        brokerTasks.add(brokertask2);
		        

		        // Assistant Tasks
		        
				if (addedSelfEmployed == false){
					Calendar year1 =  Calendar.getInstance();
									    						
					int currentMonth = year1.get(Calendar.MONTH);
					if (currentMonth <= 5){
						int startYear = year1.get(Calendar.YEAR) - 2;
						//year1.add(Calendar.YEAR, -2);
						String noa1Year = "\t - " + startYear + " Notice of Assessment from Revenue Canada" + "\n";
						listOfDocsEmail.append(noa1Year);
						String noa1YearWeb = startYear + " Notice of Assessment" + "\n";
						if (webDocAdded == false && noaDocAdded == false){
							listOfIncomeDocsWeb.append(noa1YearWeb);				    						
						}
						
						int priorYear = startYear - 1;
						String noa2Year = "\t - " + priorYear + " Notice of Assessment from Revenue Canada" + "\n";
						listOfDocsEmail.append(noa2Year);
						String noa2YearWeb = priorYear + " Notice of Assessment" + "\n";
						if (webDocAdded == false && noaDocAdded == false){
							listOfIncomeDocsWeb.append(noa2YearWeb);				    						
						}
						noaDocAdded = true;
					}
					else{
						int startYear = year1.get(Calendar.YEAR) - 1;
						String noa1Year = "\t - " + startYear+ " Notice of Assessment from Revenue Canada" + "\n";
						listOfDocsEmail.append(noa1Year);
						String noa1YearWeb = startYear + " Notice of Assessment" + "\n";
						if (webDocAdded == false && noaDocAdded == false){
							listOfIncomeDocsWeb.append(noa1YearWeb);				    						
						}
						
						int priorYear = startYear - 1;
						String noa2Year = "\t - " + priorYear + " Notice of Assessment from Revenue Canada" + "\n";
						listOfDocsEmail.append(noa2Year);
						String noa2YearWeb = priorYear + " Notice of Assessment" + "\n";
						if (webDocAdded == false && noaDocAdded == false){
							listOfIncomeDocsWeb.append(noa2YearWeb);				    						
						}	
						noaDocAdded = true;
					}
					
					addedSelfEmployed = true;				    						
		        }
		        //documentsListApplicant1.add(NOAs);	
				
				Task task2 = new Task("Confirm Self Employed Income of " + currentIncome.annualIncome + " with " + currentIncome.business + " for " + applicant.applicantName, 
						"While Verifying Incomes, Please note that each type of income needs to be on it's own row in the list of incomes. "
						+ "(Eg. Vehicle allowance cannot be combined with Employment Income.  Each needs to be on a Separate row.)" + "\n" + 
						"When the NOAs for " + applicant.applicantName + " are received, confirm there are no outstanding taxes owing.  "
						+ "If taxes are owing then request confirmation that outstanding taxes have been paid in full." + "\n" +
						"Verity income on NOA matches the self-employed income amount of "+ currentIncome.annualIncome + "for " + applicant.applicantName + "\n" +
						"Pull corporate documents (if SE income is corporation) from Registry office to confirm the ownership of company and date registered",
				"Assistant", 
				"Opportunity.ID", 
				"Opportunity.Team", 
				deadline1, 
				"", 
				(double)0, 
				1.1, 
				currentDate1, 
				deadline1);   
		        assistantTasks.add(task2);
			}
			else if (SelectionHelper.compareSelection(AlgoIncome.IncomeType.Rental, currentIncome.typeOfIncome)){
				
//				    					if (currentIncome.annualIncome != null)
//				    						totalApplicantIncome = totalApplicantIncome + Double.parseDouble(currentIncome.annualIncome);
				
				String doc = "Lease agreement or schedule A for the property at " + currentIncome.name;
				listOfDocsEmail.append("\t - " + doc + "\n");
				//documentsListApplicant1.add(doc);	
				String income4Web = doc + "\n";
				if (webDocAdded == false){
					listOfIncomeDocsWeb.append(income4Web);
				}
		        deadline1.add(Calendar.HOUR, 1);
				Task task2 = new Task("Confirm rental income for " + currentIncome.name, 
						"Verify the Annual Rental Amount for the client's property at " + currentIncome.name + 
						"from the returned lease agreement or schedule A.",
				"Assistant", 
				"Opportunity.ID", 
				"Opportunity.Team", 
				deadline1, 
				"", 
				(double)0, 
				1.1, 
				currentDate1, 
				deadline1);   
		        assistantTasks.add(task2);
			}
			else if (SelectionHelper.compareSelection(AlgoIncome.IncomeType.ChildTaxCredit, currentIncome.typeOfIncome)){
				
//				    					if (currentIncome.annualIncome != null)
//				    						totalApplicantIncome = totalApplicantIncome + Double.parseDouble(currentIncome.annualIncome);
				
				// Assistant Tasks
		        String doc = "Child tax credit form";
		        listOfDocsEmail.append("\t - " + doc + "\n");
		        String income4Web = doc + "\n";
		        if (webDocAdded == false){
		        	listOfIncomeDocsWeb.append(income4Web);	
		        }
				Task task2 = new Task("Confirm child tax credit income for " + currentIncome.name, 
						"Verify supporting documents for child tax credit of " + currentIncome.annualIncome,
				"Assistant", "Opportunity.ID", "Opportunity.Team", deadline1,"",(double)0, 0.1, currentDate1, deadline1);   
		        assistantTasks.add(task2);
			}
			else if (SelectionHelper.compareSelection(AlgoIncome.IncomeType.LivingAllowance, currentIncome.typeOfIncome)){
				
//				    					if (currentIncome.annualIncome != null)
//				    						totalApplicantIncome = totalApplicantIncome + Double.parseDouble(currentIncome.annualIncome);
				
				// Assistant Tasks
		        
				Task task2 = new Task("Verify Living Allowance income for " + currentIncome.name, 
						"Verify supporting documents through clients letter of employment if living allowance is included in clients wage." +
								"  If living allowance information is not included on the letter of employment when verified, kindly request an updated letter of employment." +	
								"  If a new Letter of employment is required, send email to client and broker advising of change on letter of employment to include this new information" +
								"  Follow up with client and broker if a new letter of employment has not been provided by the lender within 3 days or 72 hours and ask for some assistance.",
				"Assistant", "Opportunity.ID", "Opportunity.Team", deadline1,"",(double)0, 0.1, currentDate1, deadline1);   
		        assistantTasks.add(task2);
			}
			else if (SelectionHelper.compareSelection(AlgoIncome.IncomeType.VehicleAllowance, currentIncome.typeOfIncome)){
				//if (currentIncome.annualIncome != null)
					//totalApplicantIncome = totalApplicantIncome + Double.parseDouble(currentIncome.annualIncome);
				
				Task task2 = new Task("Verify Vehicle Allowance income for " + currentIncome.name, 
						"Verify supporting documents through clients letter of employment if vehicle allowance is included in clients wage." +
								"  If vehicle allowance information is not included on the letter of employment when verified, kindly request an updated letter of employment." +	
								"  If a new Letter of employment is required, send email to client and broker advising of change on letter of employment to include this new information" +
								"  Follow up with client and broker if a new letter of employment has not been provided by the lender within 3 days or 72 hours and ask for some assistance.",
				"Assistant", "Opportunity.ID", "Opportunity.Team", deadline1,"",(double)0, 0.1, currentDate1, deadline1);   
		        assistantTasks.add(task2);
			} 
			else if (SelectionHelper.compareSelection(AlgoIncome.IncomeType.Bonus, currentIncome.typeOfIncome)){
				//if (currentIncome.annualIncome != null)
					//totalApplicantIncome = totalApplicantIncome + Double.parseDouble(currentIncome.annualIncome);
				
				 // Assistant Tasks
				String doc = "Bonus Statement";
				listOfDocsEmail.append("\t - " + doc + "\n");
				String income4Web = doc + "\n";
				if (webDocAdded == false){
					listOfIncomeDocsWeb.append(income4Web);
				}
		        // documentsListApplicant1.add(doc);	
			} 
			else if (SelectionHelper.compareSelection(AlgoIncome.IncomeType.Commission, currentIncome.typeOfIncome)){
				//if (currentIncome.annualIncome != null)
					//totalApplicantIncome = totalApplicantIncome + Double.parseDouble(currentIncome.annualIncome);
				
				 // Assistant Tasks
				String doc = "Commission Statement";
				listOfDocsEmail.append("\t - " + doc + "\n");
		        // documentsListApplicant1.add(doc);
				String income4Web = doc + "\n";
				if (webDocAdded == false){
					listOfIncomeDocsWeb.append(income4Web);
				}
			}
			else if (SelectionHelper.compareSelection(AlgoIncome.IncomeType.Interest, currentIncome.typeOfIncome)){
				//if (currentIncome.annualIncome != null)
					//totalApplicantIncome = totalApplicantIncome + Double.parseDouble(currentIncome.annualIncome);
				
				// Assistant Tasks
				String doc = "Interest Statements";
				listOfDocsEmail.append("\t - " + doc + "\n");
		        // documentsListApplicant1.add(doc);
				String income4Web = doc + "\n";
				if (webDocAdded == false){
					listOfIncomeDocsWeb.append(income4Web);
				}
			}  
			else if (SelectionHelper.compareSelection(AlgoIncome.IncomeType.Overtime, currentIncome.typeOfIncome)){
				//if (currentIncome.annualIncome != null)
					//totalApplicantIncome = totalApplicantIncome + Double.parseDouble(currentIncome.annualIncome);
				
				// Assistant Tasks
				String doc = "Overtime Statements";
				listOfDocsEmail.append("\t - " + doc + "\n");
		        // documentsListApplicant1.add(doc);
				String income4Web = doc + "\n";
				if (webDocAdded == false){
					listOfIncomeDocsWeb.append(income4Web);
				}
			}  
			else if (SelectionHelper.compareSelection(AlgoIncome.IncomeType.Pension, currentIncome.typeOfIncome)){
				//if (currentIncome.annualIncome != null)
					//totalApplicantIncome = totalApplicantIncome + Double.parseDouble(currentIncome.annualIncome);
				
				// Assistant Tasks
				String doc = "Pension Statements";
				listOfDocsEmail.append("\t - " + doc + "\n");
		        // documentsListApplicant1.add(doc);
				String income4Web = doc + "\n";
				if (webDocAdded == false){
					listOfIncomeDocsWeb.append(income4Web);
				}
			}  
			else if (SelectionHelper.compareSelection(AlgoIncome.IncomeType.Retired, currentIncome.typeOfIncome)){
				//if (currentIncome.annualIncome != null)
					//totalApplicantIncome = totalApplicantIncome + Double.parseDouble(currentIncome.annualIncome);
				
				// Assistant Tasks
				String doc = "Investment Income Statements";
				listOfDocsEmail.append("\t - " + doc + "\n");
		        // documentsListApplicant1.add(doc);
				String income4Web = doc + "\n";
				if (webDocAdded == false){
					listOfIncomeDocsWeb.append(income4Web);
				}
			}
			else {
				//if (currentIncome.annualIncome != null)
					//totalApplicantIncome = totalApplicantIncome + Double.parseDouble(currentIncome.annualIncome);
				// Assistant Tasks
				String doc = "Other Income Statements - ";
				listOfDocsEmail.append("\t - " + doc + "\n");
		        // documentsListApplicant1.add(doc);		
				String income4Web = doc + "\n";
				if (webDocAdded == false){
					listOfIncomeDocsWeb.append(income4Web);
				}
			}	
		}
		return noaDocAdded;
	}

    /// <summary>
    /// This method is the check the potential ways the client application could be strengthened and to communicate assistant tasks back to OpenERP
    /// </summary>
    public void checkExceptions()
    {
        // Check Pre Payment Options
        // Check Open LOC
        // Combine Credit card/Vehicle loan into Mortgage
        // Company Debt
        // Loan Completion
        // Unused Credit
        // Available Cash
        
        // Add Lender note into Loan Completion.
        // Add Assistant Task to check on Company Debt
        // Add assitant task to email all other items if TDS > ______
        // Put the assistant task for Check Prepayment Options when "selling" property or Refianance. 


        // Change of strategy ... These will be dealt with via an email form the assistant Guy to Work each of these into regular underwriting
        // Company debt added as and assistant task if self-employed
        // Ensure double liabilities logic is already in the Applicant
        // Communicate Pre-payment as possibility by email if refinance before term is done
        // Look at current Mortgage being "Sold" ... if term not done, include this email ... 
        // 
        
    }
    
    public List<Product> findProductsToConsider(){  // was List<AlgoProduct>
    	
    	BaseDAO<Product, Integer> dao = new BaseDAOImpl<Product, Integer>(Product.class, session);
    	Criteria crti = session.createCriteria(Product.class);
//    	//String trimmedDesiredMortgageType = clientOpportunity.desiredMortgageType.trim();
//    	if(clientOpportunity.desiredMortgageType != null){ // TODO - Fix Data mapping ... 
//    		//crti.add(Restrictions.eq("mortgageType", clientOpportunity.desiredMortgageType)); // Returning "1" as
//    		crti.add(Restrictions.eq("mortgageType", "3")); // TODO For debugging purposes only.  Needs to be commented out once OpenERP fixes selection values
//    	}    		 
//    	else 
//    		crti.add(Restrictions.eq("type", "3"));
//    	// insert else in the event that desiredproductType is null. 
//    	//String trimmedDesiredTerm = clientOpportunity.desiredTerm.trim();
//    	if(clientOpportunity.desiredTerm != null){
//    		//crti.add(Restrictions.eq("term", clientOpportunity.desiredTerm)); // Returning "7"
//    		crti.add(Restrictions.eq("term", "6")); // TODO For debugging purposes only.  Needs to be commented out once OpenERP fixes selection values
//    	}    		 
//    	else 
//    		crti.add(Restrictions.eq("term", "6"));
    	
    	if(clientOpportunity.province != null){
    		crti.add(Restrictions.like("allowProvinces", "%" + clientOpportunity.province + "%"));
    	}
    	crti.add(Restrictions.not(Restrictions.like("name", "%Xternal%"))); // Do not allow Competition Products
    	
    	List<Product> products = crti.list();
    	System.out.println("clientOpportunity.desiredMortgageType:" + clientOpportunity.desiredMortgageType); 
    	System.out.println("clientOpportunity.desiredTerm:" + clientOpportunity.desiredTerm);
    	//List<Product> products = dao.findAll();
    	
    	
    	
    	return products;  // was return algoProducts;
    }
    
}