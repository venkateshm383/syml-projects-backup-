package com.syml.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.syml.bean.algo.AlgoNote;
import com.syml.bean.algo.AlgoOpportunity;
import com.syml.bean.algo.AlgoProduct;
import com.syml.bean.algo.UnderwritePostSel;
import com.syml.domain.Applicant;
import com.syml.domain.Income;
import com.syml.domain.Liability;
import com.syml.domain.Mortgage;
import com.syml.domain.Opportunity;
import com.syml.domain.Product;
import com.syml.domain.Property;
import com.syml.domain.Task;
import com.syml.util.SelectionHelper;
import com.syml.ws.PostSelection;
   
public class PostSelectionAlgorithm {
	private static final Logger logger = LoggerFactory.getLogger(PostSelectionAlgorithm.class);

	public Opportunity clientOpportunity;
    public Product potentialProduct;
    public boolean nonIncomeQualifer;
    public int highRatioLTVPercentage;
    public List<Task> assistantTasks;
    public List<Task> brokerTasks;
    public List<AlgoNote> dealNotes;
    public UnderwritePostSel qualifying;

    public PostSelectionAlgorithm(Opportunity clientOpportunity, Product potentialProduct, Session session)
    {
        try {
			this.clientOpportunity = clientOpportunity;
			this.potentialProduct = potentialProduct;
			assistantTasks = new ArrayList<Task>();
			brokerTasks = new ArrayList<Task>();
			dealNotes = new ArrayList<AlgoNote>();
			            
			// Underwrite to get final tasks for Assistant and Broker & Ensure Rules all are fulfilled
			AlgoOpportunity algoOpp = new AlgoOpportunity(clientOpportunity);
			AlgoProduct algoProduct = new AlgoProduct(potentialProduct);
			
			logger.info("-----------------------------Inside PostSelectionAlgorithm Confirm Data Quality-------------------------");
			
			boolean missingInformation = false;
			Calendar currentDate2 = new GregorianCalendar();
			Calendar deadline2 = new GregorianCalendar();
			deadline2.add(Calendar.HOUR, 1);
			
			boolean oneBeacon = false;
			boolean nullIncome = false;
			boolean nullMortgage = false;
			boolean nullProperty = false;
			boolean nullLiability = false;
			
			for (Applicant applicant : clientOpportunity.applicants) {
				if (applicant.beacon5Score == null || applicant.beacon5Score == 0){				
			        Task task2 = new Task("INFORMATION MISSING - " + applicant.applicantName + " has no beacon 5 score.  Please confirm they have no credit history.  If they do, please revise opportunity and and re-run all Products.", 
							"",
					"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
			        assistantTasks.add(task2);
				}
				else
					oneBeacon = true;
				
				// Null Check of Incomes
				if (applicant.incomes.size() > 0){
					for (Income income : applicant.incomes) {
						if (income.historical == false){
							if (income.annualIncome == null){
								nullIncome = true;
								Task task2 = new Task("INFORMATION MISSING - " + applicant.applicantName + " has an income with no number at " + income.business + " it must be revised to have a value or deleted.", 
										"Once it is revised or deleted, Re-Run the All Products Algorithm",
								"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
						        assistantTasks.add(task2);
							}
							else{
								double annualIncome = Double.parseDouble(income.annualIncome);
								if (annualIncome == 0){
									nullIncome = true;
									Task task2 = new Task("INFORMATION MISSING - " + applicant.applicantName + " has an income with no number at " + income.business + " it must be revised to have a value or deleted.", 
											"Once it is revised or deleted, Re-Run the All Products Algorithm",
									"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
							        assistantTasks.add(task2);
								}										
							}
						}
					}
					
					// Null Check of Mortgages
					for (Mortgage mortgage : applicant.mortgages) {
						if (mortgage.balance == null
								|| (mortgage.balance != null && 
								Double.parseDouble(mortgage.balance) == 0
								|| (mortgage.monthlyPayment == null
								|| mortgage.interestRate == null
								|| mortgage.name == null
								|| mortgage.renewal == null
								|| mortgage.propertyId == null))){
							nullMortgage = true;
							Task task2 = new Task("INFORMATION MISSING - " + applicant.applicantName + "'s mortgage in the the Credit-Liabilities Tab needs clarification.  See Task Details.", 
									applicant.applicantName + "has a mortgage in the list of mortgages in the the Credit-Liabilities Tab of their applicant record with missing data.  "
											+ "Data is absent for one or more of these fields: PropertyID, Balance, Interest Rate, Monthly Payment, Lender Name. Renewal Date  "
											+ "Please correct the missing information and re-run the All Products Algorithm",
							"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
					        assistantTasks.add(task2);
						}
					}
					
					// Null Check of Properties
					for (Property property : applicant.properties) {
						if (property.annualTax == null || property.value == null || property.propertyId == null){
							nullProperty = true;
							Task task2 = new Task("INFORMATION MISSING - " + applicant.applicantName + "'s Property - " + property.name + " needs clarification.  See Task Details.", 
									"The Property " + property.name + " is missing once of these items: PropertyID, Value, Annual Taxes.  Please correct the missing information and re-run the All Products Algorithm",
							"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
					        assistantTasks.add(task2);
						}
						else if (property.annualTax <= 1 || property.value <= 1) {
							nullProperty = true;
							Task task2 = new Task("INFORMATION MISSING - " + applicant.applicantName + "'s Property - " + property.name + " needs clarification.  See Task Details.", 
									"The Property " + property.name + " is missing once of these items: Value, Annual Taxes.  Please correct the missing informaiton and re-run the All Products Algorithm",
							"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
					        assistantTasks.add(task2);
						} 
						
					}
					
					// Null Check of Liabilities
					if (applicant.liabilities != null && applicant.liabilities.size() > 0){
						for (Liability liability : applicant.liabilities) {
							if (liability.creditLimit == null || liability.creditBalance == null
									|| liability.monthlyPayment == null){
								nullLiability = true;
								Task task2 = new Task("INFORMATION MISSING - " + applicant.applicantName + "'s Liability with " + liability.business + " needs clarification.  See Task Details.", 
										"The liability " + liability.name + " with " + liability.business  + " is missing once of these items: Credit Limit, Credit Balance or Monthly Payment "
												+ " If the Balance of the liabiltiy is 0, please ensure it is typed as 0 rather than left blank."
												+ " Please correct the missing informaiton and re-run the All Products Algorithm",
								"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
						        assistantTasks.add(task2);
							}
						}	
					}					
				}
				
				
			}
			
			
			
			if (nullIncome == true || nullMortgage == true || nullProperty == true || nullLiability == true){
				missingInformation = true;
			}

			if (oneBeacon == false){
				missingInformation = true; 
			    Task task2 = new Task("INFORMATION MISSING - " + "Applicant has no beacon 5 score.  Please confirm they have no credit history.  If they don't, please talk with manager and revise opportunity.  Then re-run all Products.", 
						"",
				"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
			    assistantTasks.add(task2);
			}
			
			if (clientOpportunity.applicants.size() == 0){
				missingInformation = true;
			    Task task2 = new Task("INFORMATION MISSING - " + "This Oportunity has no Applicants.  Please ensure applicants are added into the applicants list and re-run.", 
						"",
				"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
			    assistantTasks.add(task2);
			}
			
			if (clientOpportunity.expectedClosingDate == null){
				missingInformation = true;
		        Task task2 = new Task("INFORMATION MISSING - " + "This Opportunity has no Expected Closing Date. Please adjust this date estimate and re-run.", 
						"",
				"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
		        assistantTasks.add(task2);
			}
			
			if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Refinance, clientOpportunity.whatIsYourLendingGoal)  ){
				if (clientOpportunity.currentMortgageAmount == null || (clientOpportunity.currentMortgageAmount == 0 && clientOpportunity.currentBalance == 0)){
					missingInformation = true;
			        Task task2 = new Task("INFORMATION MISSING - " + "This refinance Opportunity has no Current Mortgage Amount & Additional Amount.  Please adjust these values and re-run.", 
							"",
					"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
			        assistantTasks.add(task2);
				}
				// TODO Was below including "additionalAmount" ... In OpenERP, this is "current_balance" ... optimally needs to be changed with an overhaul of webform. 
//        	if (clientOpportunity.currentMortgageAmount == null || (clientOpportunity.currentMortgageAmount == 0 && clientOpportunity.additionalAmount == 0)){
//        		AlgoNote assistNote4 = new AlgoNote(AlgoNote.TypeOfNote.AssistantAction, AlgoNote.Priority.High, "This refinance Opportunity has no Current Mortgage Amount & Additional Amount.  Please adjust these values and re-run.");
//	            assistantNotes.add(assistNote4); 
				
				if (clientOpportunity.desiredMortgageAmount == null){
					missingInformation = true;
			        Task task2 = new Task("INFORMATION MISSING - " + "This refinance Opportunity has no Desired Mortgage Amount. Please adjust this value to the mortgage Amount the client wants and re-run.", 
							"",
					"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
			        assistantTasks.add(task2);
				}
//        	}
				if (clientOpportunity.propertyValue == null || clientOpportunity.propertyValue == 0){
					missingInformation = true;
			        Task task2 = new Task("INFORMATION MISSING - " + "This refinance Opportunity has no Property Value Amount.  The Property Value is required. Please adjust this value and re-run.", 
							"",
					"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
			        assistantTasks.add(task2);
				}       
				
				if (clientOpportunity.currentLender == null){
					missingInformation = true;
			        Task task2 = new Task("INFORMATION MISSING - " + "This refinance Opportunity has no Current Lender.  The current Lender (In Opportunity General Tab) is needed. Please adjust this value and re-run.", 
							"",
					"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
			        assistantTasks.add(task2);
				}				

				if (clientOpportunity.currentInterestRate == null){
					missingInformation = true;
			        Task task2 = new Task("INFORMATION MISSING - " + "This refinance Opportunity has no Current Interest Rate.  The current interest rate (In Opportunity General Tab) is needed. Please adjust this value and re-run.", 
							"",
					"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
			        assistantTasks.add(task2);
				}
				
				if (clientOpportunity.currentRenewalDate == null){
					missingInformation = true;
			        Task task2 = new Task("INFORMATION MISSING - " + "This refinance Opportunity has no Current Renewal Date.  The current Renewal Date (In Opportunity General Tab) is needed. Please adjust this value and re-run.", 
							"",
					"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
			        assistantTasks.add(task2);
				}
				
				if (clientOpportunity.currentMortgageType == null){
					missingInformation = true;
			        Task task2 = new Task("INFORMATION MISSING - " + "This refinance Opportunity has no Current Mortgage Type.  The Current Mortgage Type (In Opportunity General Tab) is needed. Please adjust this value and re-run.", 
							"",
					"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
			        assistantTasks.add(task2);
				}
			}             
			else if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Approval, clientOpportunity.whatIsYourLendingGoal)){
				//expectedMortgageAmount = clientOpportunity.propertyValue + clientOpportunity.renovationValue - clientOpportunity.downpaymentAmount;
				if (clientOpportunity.propertyValue == null || clientOpportunity.propertyValue == 0){
					missingInformation = true;
			        Task task2 = new Task("INFORMATION MISSING - " + "This Purchase Opportunity has no Property Value Amount.  The Property Value is required. Please adjust this value and re-run.", 
							"",
					"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
			        assistantTasks.add(task2); 
				}  
				if (clientOpportunity.downpaymentAmount == null || clientOpportunity.downpaymentAmount == 0){
			    	missingInformation = true;
			        Task task2 = new Task("INFORMATION MISSING - " + "This Purchase Opportunity has no down payment Amount.  The down payment amount is required. Please adjust this value and re-run.", 
							"",
					"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
			        assistantTasks.add(task2);
			    }   
				double SeparateSourceDownpayment = clientOpportunity.bankAccount + clientOpportunity.personalCashAmount + clientOpportunity.rrspAmount + clientOpportunity.giftedAmount + clientOpportunity.borrowedAmount + clientOpportunity.existingEquityAmount + clientOpportunity.saleOfExistingAmount + clientOpportunity.sweatEquityAmount + clientOpportunity.secondaryFinancingAmount + clientOpportunity.otherAmount;
				if (clientOpportunity.downpaymentAmount != SeparateSourceDownpayment){
					missingInformation = true;
					Task task2 = new Task("INFORMATION MISSING - " + "The Multiple sources of Downpayment amounts do not equal the Opportunity Down Payment Amount.  Confirm the downpayment amounts, adjust fields and re-run the opportunty.", 
							"",
					"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
			        assistantTasks.add(task2);
				}
			}             
			else if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Prequalify, clientOpportunity.whatIsYourLendingGoal)){
				// expectedMortgageAmount = clientOpportunity.desiredMortgageAmount;
				if (clientOpportunity.downpaymentAmount == null || clientOpportunity.downpaymentAmount == 0){
			    	missingInformation = true;
			        Task task2 = new Task("INFORMATION MISSING - " + "This Purchase Opportunity has no down payment Amount.  The down payment amount is required. Please adjust this value and re-run.", 
							"",
					"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
			        assistantTasks.add(task2);
			    }
				if (clientOpportunity.desiredMortgageAmount == null || clientOpportunity.desiredMortgageAmount == 0){
			    	missingInformation = true;
			        Task task2 = new Task("INFORMATION MISSING - " + "This Pre-approval Opportunity has no Desired Mortgage Amount.  The Desired Mortgage amount is required. Please adjust this value and re-run.", 
							"",
					"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
			        assistantTasks.add(task2);
			    }
				double SeparateSourceDownpayment = clientOpportunity.bankAccount + clientOpportunity.personalCashAmount + clientOpportunity.rrspAmount + clientOpportunity.giftedAmount + clientOpportunity.borrowedAmount + clientOpportunity.existingEquityAmount + clientOpportunity.saleOfExistingAmount + clientOpportunity.sweatEquityAmount + clientOpportunity.secondaryFinancingAmount + clientOpportunity.otherAmount;
				if (clientOpportunity.downpaymentAmount != SeparateSourceDownpayment){
					missingInformation = true;
					Task task2 = new Task("INFORMATION MISSING - " + "The Multiple sources of Downpayment amounts do not equal the Opportunity Down Payment Amount.  Confirm the downpayment amounts, adjust fields and re-run the opportunty.", 
							"",
					"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
			        assistantTasks.add(task2);
				}
			}
			if (clientOpportunity.desiredAmortization == null || clientOpportunity.desiredAmortization <= 2){
				missingInformation = true;
				Task task2 = new Task("INFORMATION MISSING - " + "This Opporunity has a desired amortization which is either missing or too short.  Confirm the desired amortization and re-run the opportunty.", 
						"",
				"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
			    assistantTasks.add(task2);
			}
			
			String province = "";
			if (clientOpportunity.province == null){
				missingInformation = true;
				Task task2 = new Task("INFORMATION MISSING - " + "There is no province " +"indicated where the client intends to puchase a property.  " + "Please review considered cities field " + "and complete the 'Province' field in the Property Tab of the Opportunity Record.", 
						"",
				"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
			    assistantTasks.add(task2);
			}
			else if(clientOpportunity.province.contains("AB") == true
					|| clientOpportunity.province.contains("BC") == true
					|| clientOpportunity.province.contains("SK") == true
					|| clientOpportunity.province.contains("MB") == true
					|| clientOpportunity.province.contains("ON") == true
					|| clientOpportunity.province.contains("QC") == true
					|| clientOpportunity.province.contains("NS") == true
					|| clientOpportunity.province.contains("NB") == true
					|| clientOpportunity.province.contains("NL") == true
					|| clientOpportunity.province.contains("PE") == true
					|| clientOpportunity.province.contains("YT") == true
					|| clientOpportunity.province.contains("NU") == true
					|| clientOpportunity.province.contains("NT") == true){
				// Check Formatting of Province
				province = clientOpportunity.province;
			}
			else{
				// Check Formatting of Province
				missingInformation = true;
				Task task2 = new Task("INFORMATION MISSING - " + "Province is incorrectly formatted. Please change to 2 letter Province Abbreviation. (eg AB instead of Alberta)" , 
						"",
				"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
			    assistantTasks.add(task2);
			}
			
			// TODO Was below ... Issues with OpenERP "Additional Amount v.s. "Current Balance"
//        if (clientOpportunity.additionalAmount == null)
//    		clientOpportunity.additionalAmount = (double)0;
			if (clientOpportunity.renovationValue == null)
				clientOpportunity.renovationValue = (double)0;
			if (clientOpportunity.currentBalance == null)
				clientOpportunity.currentBalance = (double)0;
			
			
			if (missingInformation == false){
				logger.info("-----------------------------inside  PostSelectionAlgorithm  Rest  Call  Class-----------------------------------------------------------------");
				qualifying = new UnderwritePostSel(algoOpp, algoProduct, session);
				// Ensure UnderwritingType is set so that the right lists of notes are created and transferred as the deal is underwritten.
				qualifying.underwritingType = UnderwritePostSel.TypeOfUnderwriting.PostSelection;
				for(Applicant applicant : clientOpportunity.applicants)
				{
					applicant.beaconScore = 5;
				}
				qualifying.checkUnderwritingRules();
				qualifying.calculateCashback();
				qualifying.CalculateOneTimeFees();
				qualifying.CalculateOneTimeFees();
				qualifying.CalculateCommissions();
				    
				// Testing the GDS of CurrnetRecommend ... 
			    System.out.println("Reccommend GDS: " + qualifying.GDSRatio);
			    System.out.println("Reccommend TDS: " + qualifying.TDSRatio);
				
				algoProduct.qualifiesForOpportunity = qualifying.opportunityQualifies;
				algoProduct.fixedCost = qualifying.fixedCost;
				algoProduct.variableCost = qualifying.variableCost;
				algoProduct.LOCCost = qualifying.LOCCost;
				algoProduct.cashbackAmount = qualifying.cashbackAmount;
				algoProduct.payoutAmount = qualifying.payoutAmount;
				algoProduct.paymentOptions = qualifying.paymentOptions;
				algoProduct.baseCompAmount = qualifying.baseCompAmount;
				algoProduct.bonusCompAmount = qualifying.volumeBonusCompAmount;
				algoProduct.marketingCompAmount = qualifying.marketingCompAmount;
				algoProduct.trailerCompAmount = qualifying.trailerCompAmount;
				algoProduct.totalCompAmount = qualifying.totalCompAmount;
				algoProduct.branchAttend = qualifying.branchAttend;
				// Double check and ensure all calculated Properties have been set.

				if (qualifying.opportunityQualifies == true)
				{
				    AlgoNote DealNote1 = new AlgoNote(AlgoNote.TypeOfNote.Info, AlgoNote.Priority.High, "All Underwriting Criteria for " + potentialProduct.name + " are confirmed.  Please see Broker and Assistant Tasks for final items prior to Sending to Lender");
				    dealNotes.add(DealNote1);
				    // Write this note back into OpenERP into the List of Deal Notes related to this Opportunity
				}
				else
				{
				    AlgoNote DealNote1 = new AlgoNote(AlgoNote.TypeOfNote.Info, AlgoNote.Priority.High, "The Selected Product: " + potentialProduct.name + " Does not presently fit for this Opportunity.  Please review Deal Notes for reasons.");
				    dealNotes.add(DealNote1);
				    // Write this note back into OpenERP into the List of Deal Notes related to this Opportunity
				}
			}
			
			//#region Communicate PreQualification Data Back to OpenERP
			// There are 3 things that need to be Communicated back to OpenERP:
			// The list of Deal Notes notes from the qualifying object created with the selected Product.
			// The list of Assistant notes from the qualifying object created with the selected Product.
			// The list of Broker notes from the qualifying object created with the selected Product.

			for (AlgoNote note : qualifying.dealNotes)
			{
				dealNotes.add(note);
			    // Write each note back into OpenERP into the List of Deal Notes related to this Opportunity
			}
			for (Task note : qualifying.assistantTasks)
			{
				// Write each note back into OpenERP into the List of Assistant Tasks related to this Opportunity
				assistantTasks.add(note);
				// Lender Notes for the deal are in Line 1164 of Underwrite2 
			}
			for (Task note : qualifying.brokerTasks)
			{
				brokerTasks.add(note);
			    // Write each note back into OpenERP into the List of Broker Tasks related to this Opportunity
			}
			
			
			
			
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
			Task task2 = new Task("There was an ERROR IN THE UNDERWRITING APPLICATION POST SELECT 120.  Please copy-paste the text in the description of this task into an email and send it to techsupport@visdom.ca.", 
					"OpportunityID" + clientOpportunity.applicationNo + " - "  + exception,
			"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
            assistantTasks.add(task2); 
		}
        
        
    }
}