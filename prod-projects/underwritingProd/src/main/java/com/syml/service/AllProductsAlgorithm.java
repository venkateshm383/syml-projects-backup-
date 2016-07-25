package com.syml.service;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import javax.json.JsonException;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.sun.xml.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;
import com.syml.bean.algo.AlgoApplicant;
import com.syml.bean.algo.AlgoNote;
import com.syml.bean.algo.AlgoOpportunity;
import com.syml.bean.algo.AlgoProduct;
import com.syml.bean.algo.ApplicantBeacon;
import com.syml.bean.algo.ApplicantsID;
import com.syml.bean.algo.ApplicantsNames;
import com.syml.bean.algo.CalculatePayments;
import com.syml.bean.algo.CombinedRecommendation;
import com.syml.bean.algo.DesiredProduct;
import com.syml.bean.algo.FailureReasons;
import com.syml.bean.algo.InsurancePremiums;
import com.syml.bean.algo.LenderProductGroup;
import com.syml.bean.algo.MarketingNotes;
import com.syml.bean.algo.ProposalSummary;
import com.syml.bean.algo.RecommendedProduct;
import com.syml.bean.algo.UnderwriteAll;
import com.syml.bean.algo.AlgoOpportunity.FrequencyDesired;
import com.syml.bean.algo.UnderwriteCombined;
import com.syml.bean.algo.UnderwritingBase;
import com.syml.bean.algo.UwAppAllAlgo;
import com.syml.bean.algo.UwAppAllProduct;
import com.syml.bean.response.Recommendation;
import com.syml.dao.BaseDAO;
import com.syml.dao.impl.BaseDAOImpl;
import com.syml.domain.Applicant;
import com.syml.domain.Income;
import com.syml.domain.InsurerProducts;
import com.syml.domain.Liability;
import com.syml.domain.Mortgage;
import com.syml.domain.Opportunity;
import com.syml.domain.Product;
import com.syml.domain.Property;
import com.syml.domain.Task;
import com.syml.generator.domain.Test;
import com.syml.util.DataSynHelper;
import com.syml.util.GenericComparator;
import com.syml.util.SelectionHelper;
import com.syml.util.Util;

public class AllProductsAlgorithm
{
	//Added  to  test  info
	private static final Logger logger = LoggerFactory.getLogger(AllProductsAlgorithm.class);

	String reasonsForFailure1 = "";
    int countOfQualifyingProducts = 0;			    
	double lowestLocRateLtv = 100;
	double lowestVariableRateLtv = 100;
	double lowestLocRateLtv1 = 100;
	double lowestVariableRateLtv1 = 100;
	double lowestLocRateLtv2 = 100;
	double lowestVariableRateLtv2 = 100;
	 UnderwriteAll productRecommended = null;  
	
	int totalCombinedProducts = 0 ;
	double lowRate = 100;
	  UnderwriteAll currentRecommend = null;
	double additionalLTV = 0;
	double baseLtv1 = 65;
	double additionalLtv1 = 15;		
	double baseLtv2 = 15;
	double additionalLtv2 = 65;		
	 double max5YrFixedDifferential = 0;
	 int locVariableTotal = 0;
		int locFixedTotal = 0;
		int variableFixedTotal = 0;
	 double averageInterestRate;
	   double averageOneTimeCost;
	   double averageSpeed;
 	  double averageBusinessEase;
		String reasonsForFailure = "";
		 int countVariableRecommendations = 0;
		    int countFixedRecommendations = 0;
		    int countLOCRecommendations = 0;
		    int countCashbackRecommendations = 0;
		    int countCombinedRecommendations = 0;
			String desiredType = "";
			
			
		  double lowestRate = 30;
		  String desiredTerm = "";
		  Recommendation originalDesiredProduct = new Recommendation() ;
 	   Product fiveYearProduct = null;
    public Opportunity clientOpportunity;
    public AlgoOpportunity algoClientOpportunity;
    public boolean nonIncomeQualifer;
    public int highRatioLTVPercentage;
    double averageUnderwriterPreference;
    double productFitness = 0;

    public List<List<UnderwriteAll>> listOfProductLists;
    public  List<UwAppAllProduct> productsJsonList;
    public List<AlgoNote> marketingTemplateNotes;
    public List<AlgoNote> dealNotes;
    public List<Task> assistantTasks;
    public List<Task> brokerTasks;
    public List<Recommendation> Recommendations;
    
    int suitabilityNoteCounter;
    int preQualMktingNoteCounter;
    int desiredMktingNoteCounter;
    int vsMktingNoteCounter;
    int variableNoteCounter;
    int fixedNoteCounter;
    int cashbackNoteCounter;
    int assistantNoteCounter;
    int opportunityID;

    String currentRateTrend;
    private Session session;
    
    
    
    
    public static String getLenderName(int lenderid) {
    	String lenderName = null;
		switch (lenderid) {
		case 12:
			lenderName = "Lendwise";
			break;
		case 13:
			lenderName = "MCAP";
			break;
		case 14:
			lenderName = "Merix";
			break;
		case 18:
			lenderName = "TD";
			break;
		case 11:
			lenderName = "Home Trust";
			break;
		case 17:
			lenderName = "Scotia";
			break;

		case 10:
			lenderName = "First National";
			break;
		case 15:
			lenderName = "Optimum";
			break;
		case 16:
			lenderName = "RMG";
			break;
			
		case 545:
			lenderName = "Alberta Treasury Branch";
			
			break;
			
		case 463:
			lenderName = "Canadiana";
			
			break;
		default:
			lenderName = "Gathering Info";

			break;
		}
		return lenderName;

	}
    
    public AllProductsAlgorithm(AlgoOpportunity clientOpportunity1, Session session) throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException, ParseException
    {
    	try {
			this.session = session;
			this.clientOpportunity = clientOpportunity1.opp;
			this.algoClientOpportunity = clientOpportunity1;
			//HighRatioLTVPercentage = 80;

			marketingTemplateNotes = new ArrayList<AlgoNote>();
			dealNotes = new ArrayList<AlgoNote>();
			assistantTasks = new ArrayList<Task>();
			brokerTasks = new ArrayList<Task>();

			suitabilityNoteCounter = 0;
			preQualMktingNoteCounter = 0;
			desiredMktingNoteCounter = 0;
			vsMktingNoteCounter = 0;
			variableNoteCounter = 0;
			fixedNoteCounter = 0;
			cashbackNoteCounter = 0;
			assistantNoteCounter = 0;
			
			// Check Company
			
			clientOpportunity.wfgDeal = false;
			
			try{
			    if (clientOpportunity.company != null && clientOpportunity.company.toLowerCase().contains("wfg"))
			    	clientOpportunity.wfgDeal = true;
			    }catch(NullPointerException e){
			    	logger.error("WFG  Not  found"+e);
			    }
			
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
											+ "Data is absent for one or more of these fields: PropertyID, Balance, Interest Rate, Monthly Payment, Lender Name. Renewal Date "
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
			if (clientOpportunity1.opp.province == null){
				missingInformation = true;
				Task task2 = new Task("INFORMATION MISSING - " + "There is no province " +"indicated where the client intends to puchase a property.  " + "Please review considered cities field " + "and complete the 'Province' field in the Property Tab of the Opportunity Record.", 
						"",
				"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
			    assistantTasks.add(task2);
			}
			else if(clientOpportunity1.opp.province.contains("AB") == true
					|| clientOpportunity1.opp.province.contains("BC") == true
					|| clientOpportunity1.opp.province.contains("SK") == true
					|| clientOpportunity1.opp.province.contains("MB") == true
					|| clientOpportunity1.opp.province.contains("ON") == true
					|| clientOpportunity1.opp.province.contains("QC") == true
					|| clientOpportunity1.opp.province.contains("NS") == true
					|| clientOpportunity1.opp.province.contains("NB") == true
					|| clientOpportunity1.opp.province.contains("NL") == true
					|| clientOpportunity1.opp.province.contains("PE") == true
					|| clientOpportunity1.opp.province.contains("YT") == true
					|| clientOpportunity1.opp.province.contains("NU") == true
					|| clientOpportunity1.opp.province.contains("NT") == true){
				// Check Formatting of Province
				province = clientOpportunity1.opp.province;
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
				
				UwAppAllAlgo allProductAlgoJSON = new UwAppAllAlgo();
			    allProductAlgoJSON.setAlgo("AllProductAlgo");
			  
			   allProductAlgoJSON.setOpportunityName(clientOpportunity.name);
			    allProductAlgoJSON.setAddress(clientOpportunity.address);
			    allProductAlgoJSON.setCity(clientOpportunity.city);
			    allProductAlgoJSON.setProvince(clientOpportunity.province);
                allProductAlgoJSON.setOpportunityID(clientOpportunity1.opp.getOpportunityId());

			    
			  
			    ProposalSummary  proposalSummaryJson=new ProposalSummary();
			    
			    proposalSummaryJson.setType("ProposalSummary");
			    DesiredProduct desiredProduct = new DesiredProduct();	    
			    
			    HashMap<String, LenderProductGroup> lenderProductsHash =  new HashMap<>();
			    List<AlgoProduct> productsToConsider = getAllProductsByProvince(province);
			
				
				//System.out.println("productsToConsider:" + productsToConsider.size());
			    listOfProductLists = new ArrayList<List<UnderwriteAll>>();

			    List<UnderwriteAll> acceptableLOCProducts = new ArrayList<UnderwriteAll>();
			    List<UnderwriteAll> acceptableVariable1YrProducts = new ArrayList<UnderwriteAll>();
			    List<UnderwriteAll> acceptableVariable2YrProducts = new ArrayList<UnderwriteAll>();
			    List<UnderwriteAll> acceptableVariable3YrProducts = new ArrayList<UnderwriteAll>();
			    List<UnderwriteAll> acceptableVariable4YrProducts = new ArrayList<UnderwriteAll>();
			    List<UnderwriteAll> acceptableVariable5YrProducts = new ArrayList<UnderwriteAll>();
			    List<UnderwriteAll> acceptableVariable6YrProducts = new ArrayList<UnderwriteAll>();
			    List<UnderwriteAll> acceptableVariable7YrProducts = new ArrayList<UnderwriteAll>();
			    List<UnderwriteAll> acceptableVariable8YrProducts = new ArrayList<UnderwriteAll>();
			    List<UnderwriteAll> acceptableVariable9YrProducts = new ArrayList<UnderwriteAll>();
			    List<UnderwriteAll> acceptableVariable10YrProducts = new ArrayList<UnderwriteAll>();
			    List<UnderwriteAll> acceptableCashback1YrProducts = new ArrayList<UnderwriteAll>();
			    List<UnderwriteAll> acceptableCashback2YrProducts = new ArrayList<UnderwriteAll>();
			    List<UnderwriteAll> acceptableCashback3YrProducts = new ArrayList<UnderwriteAll>();
			    List<UnderwriteAll> acceptableCashback4YrProducts = new ArrayList<UnderwriteAll>();
			    List<UnderwriteAll> acceptableCashback5YrProducts = new ArrayList<UnderwriteAll>();
			    List<UnderwriteAll> acceptableCashback6YrProducts = new ArrayList<UnderwriteAll>();
			    List<UnderwriteAll> acceptableCashback7YrProducts = new ArrayList<UnderwriteAll>();
			    List<UnderwriteAll> acceptableCashback8YrProducts = new ArrayList<UnderwriteAll>();
			    List<UnderwriteAll> acceptableCashback9YrProducts = new ArrayList<UnderwriteAll>();
			    List<UnderwriteAll> acceptableCashback10YrProducts = new ArrayList<UnderwriteAll>();
			    List<UnderwriteAll> acceptableFixed6MoProducts = new ArrayList<UnderwriteAll>();
			    List<UnderwriteAll> acceptableFixed1YrProducts = new ArrayList<UnderwriteAll>();
			    List<UnderwriteAll> acceptableFixed2YrProducts = new ArrayList<UnderwriteAll>();
			    List<UnderwriteAll> acceptableFixed3YrProducts = new ArrayList<UnderwriteAll>();
			    List<UnderwriteAll> acceptableFixed4YrProducts = new ArrayList<UnderwriteAll>();
			    List<UnderwriteAll> acceptableFixed5YrProducts = new ArrayList<UnderwriteAll>();
			    List<UnderwriteAll> acceptableFixed6YrProducts = new ArrayList<UnderwriteAll>();
			
			    List<UnderwriteAll> acceptableFixed7YrProducts = new ArrayList<UnderwriteAll>();
			    List<UnderwriteAll> acceptableFixed8YrProducts = new ArrayList<UnderwriteAll>();
			    List<UnderwriteAll> acceptableFixed9YrProducts = new ArrayList<UnderwriteAll>();
			    List<UnderwriteAll> acceptableFixed10YrProducts = new ArrayList<UnderwriteAll>();
			    List<UnderwriteAll> acceptableCombined1YrProducts = new ArrayList<UnderwriteAll>();
			    List<UnderwriteAll> acceptableCombined2YrProducts = new ArrayList<UnderwriteAll>();
			    List<UnderwriteAll> acceptableCombined3YrProducts = new ArrayList<UnderwriteAll>();
			    List<UnderwriteAll> acceptableCombined4YrProducts = new ArrayList<UnderwriteAll>();
			    List<UnderwriteAll> acceptableCombined5YrProducts = new ArrayList<UnderwriteAll>();
			    List<UnderwriteAll> acceptableCombined6YrProducts = new ArrayList<UnderwriteAll>();
			    List<UnderwriteAll> acceptableCombined7YrProducts = new ArrayList<UnderwriteAll>();
			    List<UnderwriteAll> acceptableCombined8YrProducts = new ArrayList<UnderwriteAll>();
			    List<UnderwriteAll> acceptableCombined9YrProducts = new ArrayList<UnderwriteAll>();
			    List<UnderwriteAll> acceptableCombined10YrProducts = new ArrayList<UnderwriteAll>();

			    // Failed Lists to be able to explain to Broker / Client why they may not qualify and what needs to change in order to qualify
			    // Only needed for 5 Year Products
			    List<UnderwriteAll> failedVariable5YrProducts = new ArrayList<UnderwriteAll>();
			    List<UnderwriteAll> failedFixed5YrProducts = new ArrayList<UnderwriteAll>();
			    List<UnderwriteAll> allFailedProducts = new ArrayList<UnderwriteAll>();
			    List<String> failedProducts = new ArrayList<String>();
			    
			    listOfProductLists.add(acceptableLOCProducts);
			    listOfProductLists.add(acceptableFixed6MoProducts);
			    listOfProductLists.add(acceptableFixed1YrProducts);
			    listOfProductLists.add(acceptableFixed2YrProducts);
			    listOfProductLists.add(acceptableFixed3YrProducts);
			    listOfProductLists.add(acceptableFixed4YrProducts);
			    listOfProductLists.add(acceptableFixed5YrProducts);
			    listOfProductLists.add(acceptableFixed6YrProducts);
			    listOfProductLists.add(acceptableFixed7YrProducts);
			    listOfProductLists.add(acceptableFixed8YrProducts);
			    listOfProductLists.add(acceptableFixed9YrProducts);
			    listOfProductLists.add(acceptableFixed10YrProducts);
			    listOfProductLists.add(acceptableVariable1YrProducts);
			    listOfProductLists.add(acceptableVariable2YrProducts);
			    listOfProductLists.add(acceptableVariable3YrProducts);
			    listOfProductLists.add(acceptableVariable4YrProducts);
			    listOfProductLists.add(acceptableVariable5YrProducts);
			    listOfProductLists.add(acceptableVariable6YrProducts);
			    listOfProductLists.add(acceptableVariable7YrProducts);
			    listOfProductLists.add(acceptableVariable8YrProducts);
			    listOfProductLists.add(acceptableVariable9YrProducts);
			    listOfProductLists.add(acceptableVariable10YrProducts);
			    listOfProductLists.add(acceptableCombined1YrProducts);
			    listOfProductLists.add(acceptableCombined2YrProducts);
			    listOfProductLists.add(acceptableCombined3YrProducts);
			    listOfProductLists.add(acceptableCombined4YrProducts);
			    listOfProductLists.add(acceptableCombined5YrProducts);
			    listOfProductLists.add(acceptableCombined6YrProducts);
			    listOfProductLists.add(acceptableCombined7YrProducts);
			    listOfProductLists.add(acceptableCombined8YrProducts);
			    listOfProductLists.add(acceptableCombined9YrProducts);
			    listOfProductLists.add(acceptableCombined10YrProducts);
			    listOfProductLists.add(acceptableCashback1YrProducts);
			    listOfProductLists.add(acceptableCashback2YrProducts);
			    listOfProductLists.add(acceptableCashback3YrProducts);
			    listOfProductLists.add(acceptableCashback4YrProducts);
			    listOfProductLists.add(acceptableCashback5YrProducts);
			    listOfProductLists.add(acceptableCashback6YrProducts);
			    listOfProductLists.add(acceptableCashback7YrProducts);
			    listOfProductLists.add(acceptableCashback8YrProducts);
			    listOfProductLists.add(acceptableCashback9YrProducts);
			    listOfProductLists.add(acceptableCashback10YrProducts);
			    



			    boolean wfgDeal = false;
			    if (clientOpportunity.company != null && (clientOpportunity.company.toLowerCase().contains("wfg")
						|| clientOpportunity.company.toLowerCase().contains("world"))){
					wfgDeal = true;
					try{
					logger.info("--------------Company  Name-------------"+clientOpportunity.company);
					logger.info("--------------Base  LTV-------------"+clientOpportunity.baseLtv);

					}catch(NullPointerException e){
						logger.error("--------------Company  Name----is  Null"+e.getMessage());

					}
					}
               productsToConsider.stream().parallel().forEach(
			    	algoProduct -> {
			    		// Create UnderwriteAll instances to pass / fail Products
			    		Product product = algoProduct.product; // AlgoProducts are coming from Database, not OpenERP query ...
			    		
			    		
			    		//logger.info("#########List  Of  Product  is####"+algoProduct.product.lendername);
			    		if (product.interestRate > 0){
			    			// Create a PrequalifyUnderwrite add to Dictionary
			    			UnderwriteAll qualifying = new UnderwriteAll(clientOpportunity1, algoProduct, session);
			    			qualifying.underwritingType = UnderwritingBase.TypeOfUnderwriting.AllProducts;
			    			qualifying.checkUnderwritingRules();
			    			
			    			//logger.info("*****Qualifing  data  is********"+qualifying);

			    			if (qualifying.opportunityQualifies == true){
			    				// All this is moved so that it is not being calculated on the failed products
			    				countOfQualifyingProducts = countOfQualifyingProducts + 1;
			    				qualifying.calculateCashback();
			    				qualifying.calculatePayoutPenalty(); //TODO Need to check this in more detail ...
			    				qualifying.CalculateOneTimeFees();
			    				qualifying.CalculatePaymentOptionsAttraction();
			    				qualifying.CalculateCommissions();
			    				qualifying.calculateBranchAttend();
			    				qualifying.CalculateApplicationEase();
			    				//System.out.println("product.id:" + product.getId());
			    				//System.out.println("product.mortgageType:" + product.mortgageType);
			    				//System.out.println("product.term:" + product.term);

			    				algoProduct.qualifiesForOpportunity = qualifying.opportunityQualifies;

			    				//System.out.println("qualifying.opportunityQualifies:" + qualifying.opportunityQualifies);

			    				algoProduct.cashbackAmount = qualifying.cashbackAmount; // TODO May not need algoProduct ... 
			    				algoProduct.payoutAmount = qualifying.payoutAmount;
			    				algoProduct.paymentOptions = qualifying.paymentOptions; // TODO Needs to be set
			    				algoProduct.baseCompAmount = qualifying.baseCompAmount;
			    				algoProduct.bonusCompAmount = qualifying.volumeBonusCompAmount;// TODO Has an Issue
			    				algoProduct.marketingCompAmount = qualifying.marketingCompAmount;
			    				algoProduct.trailerCompAmount = qualifying.trailerCompAmount;
			    				algoProduct.totalCompAmount = qualifying.totalCompAmount; 
			    				algoProduct.branchAttend = qualifying.branchAttend;
			    				algoProduct.applicationEase = qualifying.applicationEase;
			    				// Double check and ensure all calculated Properties have been set.
			    				// Payment Cost is recalucated using the actual Product rate, not the qualifying rate.
			    				double costToUse = qualifying.expectedFitnessPayment;            
			    				algoProduct.expectedPayment = costToUse;

			    				// Sort UnderwriteAll instances into lists 
			    				String typeOfMorgageRecommendation = null;
			    				if (qualifying.potentialProduct.mortgageType.contains("2")){
			    					typeOfMorgageRecommendation = "Variable";
			    					if (qualifying.potentialProduct.term.contains("2"))
			    						acceptableVariable1YrProducts.add(qualifying);
			    					else if (qualifying.potentialProduct.term.contains("3"))
			    						acceptableVariable2YrProducts.add(qualifying);
			    					else if (qualifying.potentialProduct.term.contains("4"))
			    						acceptableVariable3YrProducts.add(qualifying);
			    					else if (qualifying.potentialProduct.term.contains("5"))
			    						acceptableVariable4YrProducts.add(qualifying);
			    					else if (qualifying.potentialProduct.term.contains("6"))
			    						acceptableVariable5YrProducts.add(qualifying);
			    					else if (qualifying.potentialProduct.term.contains("7"))
			    						acceptableVariable6YrProducts.add(qualifying);
			    					else if (qualifying.potentialProduct.term.contains("8"))
			    						acceptableVariable7YrProducts.add(qualifying);
			    					else if (qualifying.potentialProduct.term.contains("9"))
			    						acceptableVariable8YrProducts.add(qualifying);
			    					else if (qualifying.potentialProduct.term.contains("10"))
			    						acceptableVariable9YrProducts.add(qualifying);
			    					else if (qualifying.potentialProduct.term.contains("11"))
			    						acceptableVariable10YrProducts.add(qualifying);
			    				}
			    				else if (qualifying.potentialProduct.mortgageType.contains("3")){
			    					typeOfMorgageRecommendation = "Fixed";
			    					if (qualifying.potentialProduct.term.contains("1"))
			    						acceptableFixed6MoProducts.add(qualifying);
			    					else if (qualifying.potentialProduct.term.contains("2"))
			    						acceptableFixed1YrProducts.add(qualifying);
			    					else if (qualifying.potentialProduct.term.contains("3"))
			    						acceptableFixed2YrProducts.add(qualifying);
			    					else if (qualifying.potentialProduct.term.contains("4"))
			    						acceptableFixed3YrProducts.add(qualifying);
			    					else if (qualifying.potentialProduct.term.contains("5"))
			    						acceptableFixed4YrProducts.add(qualifying);
			    					else if (qualifying.potentialProduct.term.contains("6"))
			    						acceptableFixed5YrProducts.add(qualifying);
			    					else if (qualifying.potentialProduct.term.contains("7"))
			    						acceptableFixed6YrProducts.add(qualifying);
			    					else if (qualifying.potentialProduct.term.contains("8"))
			    						acceptableFixed7YrProducts.add(qualifying);
			    					else if (qualifying.potentialProduct.term.contains("9"))
			    						acceptableFixed8YrProducts.add(qualifying);
			    					else if (qualifying.potentialProduct.term.contains("10"))
			    						acceptableFixed9YrProducts.add(qualifying);
			    					else if (qualifying.potentialProduct.term.contains("11"))
			    						acceptableFixed10YrProducts.add(qualifying);
			    				}
			    				else if (qualifying.potentialProduct.mortgageType.contains("1")){
			    					acceptableLOCProducts.add(qualifying);
			    				}
			    				else if (qualifying.potentialProduct.mortgageType.contains("4")){
			    					typeOfMorgageRecommendation = "Cashback";
			    					if (qualifying.potentialProduct.term.contains("2"))
			    						acceptableCashback1YrProducts.add(qualifying);
			    					else if (qualifying.potentialProduct.term.contains("3"))
			    						acceptableCashback2YrProducts.add(qualifying);
			    					else if (qualifying.potentialProduct.term.contains("4"))
			    						acceptableCashback3YrProducts.add(qualifying);
			    					else if (qualifying.potentialProduct.term.contains("5"))
			    						acceptableCashback4YrProducts.add(qualifying);
			    					else if (qualifying.potentialProduct.term.contains("6"))
			    						acceptableCashback5YrProducts.add(qualifying);
			    					else if (qualifying.potentialProduct.term.contains("7"))
			    						acceptableCashback6YrProducts.add(qualifying);
			    					else if (qualifying.potentialProduct.term.contains("8"))
			    						acceptableCashback7YrProducts.add(qualifying);
			    					else if (qualifying.potentialProduct.term.contains("9"))
			    						acceptableCashback8YrProducts.add(qualifying);
			    					else if (qualifying.potentialProduct.term.contains("10"))
			    						acceptableCashback9YrProducts.add(qualifying);
			    					else if (qualifying.potentialProduct.term.contains("11"))
			    						acceptableCashback10YrProducts.add(qualifying);
			    				}
			    				else if (qualifying.potentialProduct.mortgageType.contains("5")){
			    					typeOfMorgageRecommendation = "Combined";
			    					if (qualifying.potentialProduct.term.contains("2"))
			    						acceptableCombined1YrProducts.add(qualifying);
			    					else if (qualifying.potentialProduct.term.contains("3"))
			    						acceptableCombined2YrProducts.add(qualifying);
			    					else if (qualifying.potentialProduct.term.contains("4"))
			    						acceptableCombined3YrProducts.add(qualifying);
			    					else if (qualifying.potentialProduct.term.contains("5"))
			    						acceptableCombined4YrProducts.add(qualifying);
			    					else if (qualifying.potentialProduct.term.contains("6"))
			    						acceptableCombined5YrProducts.add(qualifying);
			    					else if (qualifying.potentialProduct.term.contains("7"))
			    						acceptableCombined6YrProducts.add(qualifying);
			    					else if (qualifying.potentialProduct.term.contains("8"))
			    						acceptableCombined7YrProducts.add(qualifying);
			    					else if (qualifying.potentialProduct.term.contains("9"))
			    						acceptableCombined8YrProducts.add(qualifying);
			    					else if (qualifying.potentialProduct.term.contains("10"))
			    						acceptableCombined9YrProducts.add(qualifying);
			    					else if (qualifying.potentialProduct.term.contains("11"))
			    						acceptableCombined10YrProducts.add(qualifying);
			    				}
			    			}
			    			else
			    			{
			    				if (qualifying.potentialProduct.mortgageType.contains("2")){
			    					if (qualifying.potentialProduct.term.contains("6"))
			    						failedVariable5YrProducts.add(qualifying);
			    				}
			    				else if (qualifying.potentialProduct.mortgageType.contains("3")){
			    					if (qualifying.potentialProduct.term.contains("6"))
			    						failedFixed5YrProducts.add(qualifying);
			    				}

			    				
			    				reasonsForFailure1 = qualifying.potentialProduct.name + " : ";
			    				
			    				
			    			/*	for (String failureReason : qualifying.failReasons) {
			    					reasonsForFailure1 = reasonsForFailure1 + failureReason + " - ";
			    				}*/
			    				//Added  Java  8  strem  
			    				qualifying.failReasons.stream().parallel().forEach(
			    						failureReason -> {
			    							reasonsForFailure1 = reasonsForFailure1 + failureReason + " - ";
			    						});

			    				failedProducts.add(reasonsForFailure1);

			    				allFailedProducts.add(qualifying);
			    			}

			    			/*for (String failureReason : qualifying.failReasons) {
			    				reasonsForFailure = reasonsForFailure + failureReason + " - ";
			    			}*/
			    			qualifying.failReasons.stream().parallel().forEach(
		    						failureReason -> {
		    							reasonsForFailure = reasonsForFailure + failureReason + " - ";
		    						});
			    		

			    
			    		}
			    		
			    	
			    	
			    	
			    	// End of Sorting Products by Lender
			    	}
			    ); // Completes the Parallel Streams ForEach Statement 
			    
			 // Create UwAppAllProduct allProductJSON for Failed Products ... Passed Products will be added later once fitness is calculated
			    productsJsonList = new ArrayList<UwAppAllProduct>();
			    
			    for (AlgoProduct algoProduct : productsToConsider) {
			    	try{
			    		Product product = algoProduct.product;
		    			// Not Needed Already defined in Loop Product product4hash = algoproduct.product;

		    			String lenderName = null;
		    			if (product.lendername != null){
		    				lenderName = product.lendername;
		    				//logger.info("inside lendername : "+lenderName);
		    			}
		    			else{
		    				//logger.error("No Lender Name for : "+product.name);
		    				
		    			}

		    			if (lenderProductsHash.containsKey(lenderName) == false ){
		    				//logger.info("for false condition : ");

		    				LenderProductGroup lenderProductGroup = new LenderProductGroup(lenderName);  
		    				if (product.mortgageType.contains("1")){
		    					//logger.info("inside mortage type 1 :"); 

		    					lenderProductGroup.locList.add(product);
		    					//logger.info("inside mortage type 1 :"+lenderProductGroup.getLocList()); 

		    					if (product.combinebase != null && product.combinebase == true){
		    						lenderProductGroup.setLocCombineCounter(lenderProductsHash.size());
		    						//logger.info("for 1 counter :"+lenderProductsHash.size());								
		    					}

		    				}
		    				else if (product.mortgageType.contains("2")){
		    					//logger.info("inside mortage type 2 :"); 
		    					lenderProductGroup.variableList.add(product);
		    					lenderProductGroup.setVariableCombineCounter(lenderProductsHash.size());
		    					//logger.info("inside mortage type 2 :"+lenderProductGroup.getVariableList());
		    					//logger.info("for 2 counter :"+lenderProductsHash.size());
		    				}
		    				else if (product.mortgageType.contains("3")){
		    					//logger.info("inside mortage type 3 :");

		    					lenderProductGroup.fixedList.add(product); 
		    					lenderProductGroup.setFixedCombineCounter(lenderProductsHash.size());

		    					//logger.info("inside mortage type 3 :"+lenderProductGroup.getFixeList());
		    					//logger.info("for 2 counter :"+lenderProductsHash.size());
		    				}

		    				lenderProductsHash.put(lenderName, lenderProductGroup);
		    			}
		    			else if(lenderProductsHash.containsKey(lenderName) ==true){
		    				//logger.info("for true condition : ");
		    				// This could be cleaner ... SHould not be using temp lenderProductGroup .... 
		    				// Rather, directly access the correct lenderProductsHash by lenderProductsHash.get(lenderName) 
		    				//LenderProductGroup lenderProductGroup = lenderProductsHash.get(lenderName);  Commented out and replaced.
		    				if (product.mortgageType.contains("1")){
		    					//logger.info("inside mortage type 1 :"); 
		    					lenderProductsHash.get(lenderName).locList.add(product);
		    					lenderProductsHash.get(lenderName).setLocCombineCounter(lenderProductsHash.size());
		    					//logger.info("for 1 counter :"+lenderProductsHash.size());
		    				}
		    				else if (product.mortgageType.contains("2")){  // for varibale
		    					//logger.info("inside mortage type 2 :"); 
		    					lenderProductsHash.get(lenderName).variableList.add(product);
		    					lenderProductsHash.get(lenderName).setVariableCombineCounter(lenderProductsHash.size());
		    					//logger.info("for 2 counter :"+lenderProductsHash.size());
		    					//lenderProductGroup.variableProducts.Add(product);
		    				}
		    				else if (product.mortgageType.contains("3")) {// for Fixed
		    					//logger.info("inside mortage type 3 :"); 
		    					lenderProductsHash.get(lenderName).fixedList.add(product); 
		    					lenderProductsHash.get(lenderName).setFixedCombineCounter(lenderProductsHash.size());
		    					//lenderProductGroup.fixedProducts.Add(product);
		    					//logger.info("for 3 counter :"+lenderProductsHash.size());
		    				}
		    				lenderProductsHash.put(lenderName, lenderProductsHash.get(lenderName));
		    			}
		    			// Products have been set into Hashmap  ... 

		    		}catch(Exception e){
		    			logger.error("error has occured during mapping of product into lenderProductGroup hashmap ",e);
		    		}
				}
		
			    allFailedProducts.stream().parallel().forEach(
			    		failedProduct -> {

			    			try{
				    			UwAppAllProduct failProductJSON = new UwAppAllProduct(failedProduct);	
			    			 failProductJSON.setAlgo("failedProduct");
			    			
			    			 productsJsonList.add(failProductJSON);
			    			}catch(Exception e){
			    				
			    			}
			    		});
						
			    			    
			    // Sort Failed Lists by interest rate... 
			    GenericComparator orderbyInterestRate1 = Util.newGenericComparator("interestRate","asc"); 
	            if (failedVariable5YrProducts.size() > 0){
	            	try{
	            	Collections.sort(failedVariable5YrProducts, orderbyInterestRate1);
	            	}catch(Exception  e){

	            		logger.error("Eror  in  sorting");
	            	}
	            	UnderwriteAll lowestRateFailedVariable = failedVariable5YrProducts.get(0);
		            
		            StringBuilder failedVaribaleReasons = new StringBuilder();
		            for (String reason : lowestRateFailedVariable.failReasons) {
		            	failedVaribaleReasons.append(reason + "\n");
					}
		            
		            String variableFailures = failedVaribaleReasons.toString();
		            AlgoNote noteVariableFail = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.High,lowestRateFailedVariable.potentialProduct.name 
		            		+ " - " + lowestRateFailedVariable.potentialProduct.interestRate + "\n" + variableFailures,"VariableFailures");
				    dealNotes.add(noteVariableFail);						    
	            }
	            
	            if (failedFixed5YrProducts.size() > 0){
	            	Collections.sort(failedFixed5YrProducts, orderbyInterestRate1);
		            UnderwriteAll lowestRateFailedFixed = failedFixed5YrProducts.get(0);
		            
		            StringBuilder failedFixedReasons = new StringBuilder();
		            for (String reason : lowestRateFailedFixed.failReasons) {
		            	failedFixedReasons.append(reason + "\n");
					}
		            
		            String fixedFailures = failedFixedReasons.toString();
		            AlgoNote noteFixedFail = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.High,lowestRateFailedFixed.potentialProduct.name 
		            		+ " - " + lowestRateFailedFixed.potentialProduct.interestRate + "\n" + fixedFailures,"FixedFailures");
				    dealNotes.add(noteFixedFail);
	            }	           

	            // This Section Deals with the Creating the Combined Product Recommendations
	            // TODO Need to look at these lists ... Might need to separate into Fixed and Variable Combinations ... Double Check Requirements Proposal Table Section
				List<UnderwriteCombined> acceptableLocVarBaseLTV = new ArrayList<UnderwriteCombined>(); 	
				List<UnderwriteCombined> failedLocVarBaseLTV = new ArrayList<UnderwriteCombined>(); 					
				List<UnderwriteCombined> acceptableLocVarBaseLtv1 = new ArrayList<UnderwriteCombined>(); 	
				List<UnderwriteCombined> failedLocVarBaseLtv1 = new ArrayList<UnderwriteCombined>(); 		
				List<UnderwriteCombined> acceptableLocVarBaseLtv2 = new ArrayList<UnderwriteCombined>(); 	
				List<UnderwriteCombined> failedLocVarBaseLtv2 = new ArrayList<UnderwriteCombined>(); 		
				
				List<UnderwriteCombined> acceptableLocFixedBaseLTV = new ArrayList<UnderwriteCombined>(); 	
				List<UnderwriteCombined> failedLocFixedBaseLTV = new ArrayList<UnderwriteCombined>(); 					
				List<UnderwriteCombined> acceptableLocFixedBaseLtv1 = new ArrayList<UnderwriteCombined>(); 	
				List<UnderwriteCombined> failedLocFixedBaseLtv1 = new ArrayList<UnderwriteCombined>(); 		
				List<UnderwriteCombined> acceptableLocFixedBaseLtv2 = new ArrayList<UnderwriteCombined>(); 	
				List<UnderwriteCombined> failedLocFixedBaseLtv2 = new ArrayList<UnderwriteCombined>(); 	
				
				List<UnderwriteCombined> acceptableVarFixedBaseLTV = new ArrayList<UnderwriteCombined>(); 	
				List<UnderwriteCombined> failedVarFixedBaseLTV = new ArrayList<UnderwriteCombined>(); 					
				List<UnderwriteCombined> acceptableVarFixedBaseLtv1 = new ArrayList<UnderwriteCombined>(); 	
				List<UnderwriteCombined> failedVarFixedBaseLtv1 = new ArrayList<UnderwriteCombined>(); 		
				List<UnderwriteCombined> acceptableVarFixedBaseLtv2 = new ArrayList<UnderwriteCombined>(); 	
				List<UnderwriteCombined> failedVarFixedBaseLtv2 = new ArrayList<UnderwriteCombined>(); 
				
				
				double initalMortgageAmount = clientOpportunity.propertyValue - clientOpportunity.downpaymentAmount;
				double	initalLTV = initalMortgageAmount  /  clientOpportunity.propertyValue * 100;
				if (clientOpportunity.baseLtv != null){					
					additionalLtv1 = Math.max(initalLTV - baseLtv1, 0);
				}
						
				if (initalLTV <= 80){ 
					additionalLtv1 = Math.max(initalLTV - baseLtv1, 0);
					additionalLtv2 = Math.max(initalLTV - baseLtv2, 0);
				}
								
				lenderProductsHash.forEach((k,v)->{
					//logger.info("LenderProductGroup : " + k + " Count : " + v); // TODO Insert Count of Lists .... 
					
					
					for (Product baseProduct : v.locList) {
						// Total Possible Combined Products for later Tables Description. 
						locVariableTotal = locVariableTotal + v.variableList.size();
						locFixedTotal = locFixedTotal + v.fixedList.size();
						
						// Check to see if LOC can be combined
						if (baseProduct.combinebase == true){
							// Work Through the Combined LOC with  Variables
							for (Product variableProduct : v.variableList){
								if (clientOpportunity.baseLtv != null && clientOpportunity.baseLtv > 0){											
									UnderwriteCombined underwriteCombined = new UnderwriteCombined(clientOpportunity1, baseProduct, variableProduct,  clientOpportunity.baseLtv,  additionalLTV,  session);
									if (underwriteCombined.opportunityQualifies == true){												
										if (baseProduct.interestRate <= lowestLocRateLtv + (double)0.1){
											lowestLocRateLtv = baseProduct.interestRate;
											acceptableLocVarBaseLTV.add(underwriteCombined);
										}
									}												
									else
										failedLocVarBaseLTV.add(underwriteCombined);
								}
								else{
									// Underwrite Base LTV 65
									UnderwriteCombined underwriteCombined1 = new UnderwriteCombined(clientOpportunity1, baseProduct, variableProduct,  baseLtv1,  additionalLtv1,  session);
									if (underwriteCombined1.opportunityQualifies == true){
										if (baseProduct.interestRate <= lowestLocRateLtv1 + (double)0.1){
											lowestLocRateLtv1 = baseProduct.interestRate;
											acceptableLocVarBaseLtv1.add(underwriteCombined1);
										}	
									}												
									else
										failedLocVarBaseLtv1.add(underwriteCombined1);
									
									// Underwrite Base LTV 15
									UnderwriteCombined underwriteCombined2 = new UnderwriteCombined(clientOpportunity1, baseProduct, variableProduct,  baseLtv2,  additionalLtv2,  session);
									if (underwriteCombined2.opportunityQualifies == true){
										if (baseProduct.interestRate <= lowestLocRateLtv2 + (double)0.1){
											lowestLocRateLtv2 = baseProduct.interestRate;
											acceptableLocVarBaseLtv2.add(underwriteCombined2);
										}	
									}												
									else
										failedLocVarBaseLtv2.add(underwriteCombined2);
								}
							}
							
							// Work Through the Combined LOC with Fixed
							for(Product fixedProduct : v.fixedList){
								if (clientOpportunity.baseLtv != null && clientOpportunity.baseLtv > 0){											
									UnderwriteCombined underwriteCombined = new UnderwriteCombined(clientOpportunity1, baseProduct, fixedProduct,  clientOpportunity.baseLtv,  additionalLTV,  session);
									if (underwriteCombined.opportunityQualifies == true){												
										if (baseProduct.interestRate <= lowestLocRateLtv + (double)0.1){
											lowestLocRateLtv = baseProduct.interestRate;
											acceptableLocFixedBaseLTV.add(underwriteCombined);
										}
									}												
									else
										failedLocFixedBaseLTV.add(underwriteCombined);
								}
								else{
									// Underwrite Base LTV 65
									UnderwriteCombined underwriteCombined1 = new UnderwriteCombined(clientOpportunity1, baseProduct, fixedProduct,  baseLtv1,  additionalLtv1,  session);
									if (underwriteCombined1.opportunityQualifies == true){
										if (baseProduct.interestRate <= lowestLocRateLtv1 + (double)0.1){
											lowestLocRateLtv1 = baseProduct.interestRate;
											acceptableLocFixedBaseLtv1.add(underwriteCombined1);
										}	
									}												
									else
										failedLocFixedBaseLtv1.add(underwriteCombined1);
									
									// Underwrite Base LTV 15
									UnderwriteCombined underwriteCombined2 = new UnderwriteCombined(clientOpportunity1, baseProduct, fixedProduct,  baseLtv2,  additionalLtv2,  session);
									if (underwriteCombined2.opportunityQualifies == true){
										if (baseProduct.interestRate <= lowestLocRateLtv2 + (double)0.1){
											lowestLocRateLtv2 = baseProduct.interestRate;
											acceptableLocFixedBaseLtv2.add(underwriteCombined2);
										}	
									}												
									else
										failedLocFixedBaseLtv2.add(underwriteCombined2);
								}
							}		
							
							// Deleted ParallelStreams in this case, want serial processing of loop.
						}
					}
					
					
				
					// add to totalCombinedProducts
					totalCombinedProducts = totalCombinedProducts + locVariableTotal + locFixedTotal;
					logger.info("-------------------clientOpportunity.baseLtv-----------"+clientOpportunity.baseLtv);

					
					// TODO Now Add Variable-Fixed Loop for this LenderProductGroup
					/*for (Product baseProduct : v.variableList) {*/
					// Removed ParallelStream ... not needed in this case.
					logger.info("--------------v.variableList--------------"+v.variableList.size());

					for (Product baseProduct : v.variableList){
						// Total Possible Combined Products for later Tables Description. 
						variableFixedTotal = variableFixedTotal + v.fixedList.size();
						// Check to see if Variable can be combined
						
						logger.info("--------------v.variableList--------------"+baseProduct.combinebase);

						if (baseProduct.combinebase == true){
							logger.info("------------baseProduct.combinebase   is  true--------------------");

							
							// Work Through the Combined LOC with Fixed
							for(Product fixedProduct : v.fixedList){
								
								logger.info("------------fixedProduct  for  loop  Work Through the Combined LOC with Fixed-------------------");
								
								if (clientOpportunity.baseLtv != null && clientOpportunity.baseLtv > 0){											
									UnderwriteCombined underwriteCombined = new UnderwriteCombined(clientOpportunity1, baseProduct, fixedProduct,  clientOpportunity.baseLtv,  additionalLTV,  session);
									logger.info("--------------underwriteCombined  if  case ----------"+underwriteCombined.opportunityQualifies);
									if (underwriteCombined.opportunityQualifies == true){												
										if (baseProduct.interestRate <= lowestVariableRateLtv + (double)0.1){
											lowestVariableRateLtv = baseProduct.interestRate;
											acceptableVarFixedBaseLTV.add(underwriteCombined);
										}
									}												
									else
										failedVarFixedBaseLTV.add(underwriteCombined);
								}
								else{

									
									// Underwrite Base LTV 65
									UnderwriteCombined underwriteCombined1 = new UnderwriteCombined(clientOpportunity1, baseProduct, fixedProduct,  baseLtv1,  additionalLtv1,  session);
									logger.info("--------------underwriteCombined  else  case-----------"+underwriteCombined1.opportunityQualifies);

									if (underwriteCombined1.opportunityQualifies == true){
										if (baseProduct.interestRate <= lowestVariableRateLtv1 + (double)0.1){
											lowestVariableRateLtv1 = baseProduct.interestRate;
											acceptableVarFixedBaseLtv1.add(underwriteCombined1);
										}	
									}												
									else
										failedVarFixedBaseLtv1.add(underwriteCombined1);
									
									// Underwrite Base LTV 15
									UnderwriteCombined underwriteCombined2 = new UnderwriteCombined(clientOpportunity1, baseProduct, fixedProduct,  baseLtv2,  additionalLtv2,  session);
									if (underwriteCombined2.opportunityQualifies == true){
										if (baseProduct.interestRate <= lowestVariableRateLtv2 + (double)0.1){
											lowestVariableRateLtv2 = baseProduct.interestRate;
											acceptableVarFixedBaseLtv2.add(underwriteCombined2);
										}	
									}												
									else
										failedVarFixedBaseLtv2.add(underwriteCombined2);
								}
							}
							// Deleted ParallelStream() for fixed list.  In this case serial processing is needed							
						}					
					}
					// add to totalCombinedProducts
					totalCombinedProducts = totalCombinedProducts + variableFixedTotal;
						
				}
				); // End of lenderProductsHash foreach
				
				
				//Double averagePayment = acceptableVarFixedBaseLtv2.stream().collect(Collectors.averagingDouble(p -> p.))
			    //#region Determine Fitness of Product
	            
			    // Need to figure out which product cost will be passed into the fitness formula (Annual / Total / Interest Only?)
			    
			    //COST NEEDS  TO COSIDER FEES ... 
	         
	           
	            if (acceptableFixed5YrProducts.size() > 0){
	            	// Vikash - Please convert this loop to a parrallelStream (Or Streams().Parallel.Sort lambda and Test
	            	/*for (UnderwriteAll underwriteAll : acceptableFixed5YrProducts) {
						double differential = underwriteAll.potentialProduct.postedRate - underwriteAll.potentialProduct.interestRate;
						if (differential > max5YrFixedDifferential)
							max5YrFixedDifferential = differential;
					}*/
	            	//In  Java  8 Parralel  strem
	            	acceptableFixed5YrProducts.stream().parallel().forEach(
	            			 underwriteAll -> {
	            				 double differential = underwriteAll.potentialProduct.postedRate - underwriteAll.potentialProduct.interestRate;
	     						if (differential > max5YrFixedDifferential)
	     							max5YrFixedDifferential = differential;
	            			 });
	            	
	            	
	            	
	            
	            }
	            
	            
			    Recommendations = new ArrayList<Recommendation>();
			    ArrayList<UnderwriteAll> ProductRecommendations1 = new ArrayList<UnderwriteAll>();
			    			    
			    
			    Comparator<UnderwriteCombined> byAscPayment = (UnderwriteCombined combinedUW1, UnderwriteCombined combinedUW2) -> 
			    Double.compare(combinedUW1.expectedFitnessPayment, combinedUW2.expectedFitnessPayment);
			    
			    //Comparator<Book> descPriceComp = (Book b1, Book b2) -> (int) (b2.getPrice() - b1.getPrice());
			    Comparator<UnderwriteCombined> byAscFitness = (UnderwriteCombined combinedUW1, UnderwriteCombined combinedUW2) -> 
			    Double.compare(combinedUW1.fitness, combinedUW2.fitness);
			    //(int) (combinedUW2.fitness - combinedUW1.fitness);
			    // TODO Vikash, Please ensure the above Comparators Work by adding some temporary Print out of 
			    // the fitness and expectedFitnessPayment of the below Lists
			    
			    // TODO Guy - I may want to move these methods into the UnderwriteCombined near end of stack.
				// Set Values for all acceptableLocVarBaseLTV Instances
			    		    
			    double acceptableLocVarBaseLTVPayment = acceptableLocVarBaseLTV.parallelStream().collect(Collectors.averagingDouble(p -> p.expectedFitnessPayment));
			    double acceptableLocVarBaseLTV1Time = acceptableLocVarBaseLTV.parallelStream().collect(Collectors.averagingDouble(p -> p.oneTimeFees));
			    double acceptableLocVarBaseLTVInterestRate = acceptableLocVarBaseLTV.parallelStream().collect(Collectors.averagingDouble(p -> p.interestRate));
			    double acceptableLocVarBaseLTVRisk = 1;
			    double acceptableLocVarBaseLTVCashBack = 1;
			    double acceptableLocVarBaseLTVPayOut = acceptableLocVarBaseLTV.parallelStream().collect(Collectors.averagingDouble(p -> p.payoutAmount));
			    double acceptableLocVarBaseLTVPayOption = acceptableLocVarBaseLTV.parallelStream().collect(Collectors.averagingDouble(p -> p.paymentOptions));
			    double acceptableLocVarBaseLTVBaseComp = acceptableLocVarBaseLTV.parallelStream().collect(Collectors.averagingDouble(p -> p.baseCompAmount));
			    double acceptableLocVarBaseLTVBonusComp = acceptableLocVarBaseLTV.parallelStream().collect(Collectors.averagingDouble(p -> p.bonusCompAmount));
			    double acceptableLocVarBaseLTVMarketingComp = acceptableLocVarBaseLTV.parallelStream().collect(Collectors.averagingDouble(p -> p.marketingCompAmount));
			    double acceptableLocVarBaseLTVTrailerrComp = acceptableLocVarBaseLTV.parallelStream().collect(Collectors.averagingDouble(p -> p.trailerCompAmount));
			    double acceptableLocVarBaseLTVTotalComp = acceptableLocVarBaseLTV.parallelStream().collect(Collectors.averagingDouble(p -> p.totalCompAmount));
			    double acceptableLocVarBaseLTVSpeed = acceptableLocVarBaseLTV.parallelStream().collect(Collectors.averagingDouble(p -> p.avgProcessingSpeed));
			    double acceptableLocVarBaseLTVBusEase = acceptableLocVarBaseLTV.parallelStream().collect(Collectors.averagingDouble(p -> p.businessEase));
			    double acceptableLocVarBaseLTVBranchAttend = acceptableLocVarBaseLTV.parallelStream().collect(Collectors.averagingDouble(p -> p.branchAttend));
			    double acceptableLocVarBaseLTVUwPref = acceptableLocVarBaseLTV.parallelStream().collect(Collectors.averagingDouble(p -> p.underwriterPref));
			    double acceptableLocVarBaseLTVAppEase = acceptableLocVarBaseLTV.parallelStream().collect(Collectors.averagingDouble(p -> p.applicationEase));
			    acceptableLocVarBaseLTV.parallelStream().forEach(
			    		combinedUW -> {
			    			double productFitness = 0;
			                productFitness = combinedUW.getFitness(acceptableLocVarBaseLTVPayment, acceptableLocVarBaseLTV1Time, acceptableLocVarBaseLTVInterestRate, 
			                		acceptableLocVarBaseLTVRisk, acceptableLocVarBaseLTVCashBack,acceptableLocVarBaseLTVPayOut, acceptableLocVarBaseLTVPayOption, 
			                		acceptableLocVarBaseLTVBaseComp, acceptableLocVarBaseLTVBonusComp, acceptableLocVarBaseLTVMarketingComp, acceptableLocVarBaseLTVTrailerrComp, 
			                		acceptableLocVarBaseLTVSpeed, acceptableLocVarBaseLTVBusEase, acceptableLocVarBaseLTVBranchAttend, 
			                		acceptableLocVarBaseLTVUwPref, acceptableLocVarBaseLTVAppEase, acceptableLocVarBaseLTVTotalComp);
			                combinedUW.fitness = productFitness;
			                
			                UwAppAllProduct failProductJSON = new UwAppAllProduct(combinedUW);	
			                failProductJSON.setAlgo("failedProduct");
					    	productsJsonList.add(failProductJSON);									
			    		}
			    		);
			    
			    if (wfgDeal == true){
			    	// Sort Combined by order of Ascending Paymentthe Products in order of Descending Fitness and place into a new ArrayList.	
			    	Collections.sort(acceptableLocVarBaseLTV, byAscPayment);
			    	//acceptableLocVarBaseLTV.stream().sorted((combinedUW1, combinedUW2) -> Double.compare(combinedUW1.expectedFitnessPayment, combinedUW2.expectedFitnessPayment));
			    }
			    else{
			    	// Sort the Products in order of Descending Fitness 
			    	Collections.sort(acceptableLocVarBaseLTV, byAscFitness.reversed());
			    	//acceptableLocVarBaseLTV.stream().sorted((combinedUW1, combinedUW2) -> Double.compare(combinedUW1.fitness, combinedUW2.fitness));
			    }
				
				// Set Values for all acceptableLocVarBaseLtv1 Instances
			    double acceptableLocVarBaseLtv1Payment = acceptableLocVarBaseLtv1.parallelStream().collect(Collectors.averagingDouble(p -> p.expectedFitnessPayment));
			    double acceptableLocVarBaseLtv11Time = acceptableLocVarBaseLtv1.parallelStream().collect(Collectors.averagingDouble(p -> p.oneTimeFees));
			    double acceptableLocVarBaseLtv1InterestRate = acceptableLocVarBaseLtv1.parallelStream().collect(Collectors.averagingDouble(p -> p.interestRate));
			    double acceptableLocVarBaseLtv1Risk = 1;
			    double acceptableLocVarBaseLtv1CashBack = 1;
			    double acceptableLocVarBaseLtv1PayOut = acceptableLocVarBaseLtv1.parallelStream().collect(Collectors.averagingDouble(p -> p.payoutAmount));
			    double acceptableLocVarBaseLtv1PayOption = acceptableLocVarBaseLtv1.parallelStream().collect(Collectors.averagingDouble(p -> p.paymentOptions));
			    double acceptableLocVarBaseLtv1BaseComp = acceptableLocVarBaseLtv1.parallelStream().collect(Collectors.averagingDouble(p -> p.baseCompAmount));
			    double acceptableLocVarBaseLtv1BonusComp = acceptableLocVarBaseLtv1.parallelStream().collect(Collectors.averagingDouble(p -> p.bonusCompAmount));
			    double acceptableLocVarBaseLtv1MarketingComp = acceptableLocVarBaseLtv1.parallelStream().collect(Collectors.averagingDouble(p -> p.marketingCompAmount));
			    double acceptableLocVarBaseLtv1TrailerrComp = acceptableLocVarBaseLtv1.parallelStream().collect(Collectors.averagingDouble(p -> p.trailerCompAmount));
			    double acceptableLocVarBaseLtv1TotalComp = acceptableLocVarBaseLtv1.parallelStream().collect(Collectors.averagingDouble(p -> p.totalCompAmount));
			    double acceptableLocVarBaseLtv1Speed = acceptableLocVarBaseLtv1.parallelStream().collect(Collectors.averagingDouble(p -> p.avgProcessingSpeed));
			    double acceptableLocVarBaseLtv1BusEase = acceptableLocVarBaseLtv1.parallelStream().collect(Collectors.averagingDouble(p -> p.businessEase));
			    double acceptableLocVarBaseLtv1BranchAttend = acceptableLocVarBaseLtv1.parallelStream().collect(Collectors.averagingDouble(p -> p.branchAttend));
			    double acceptableLocVarBaseLtv1UwPref = acceptableLocVarBaseLtv1.parallelStream().collect(Collectors.averagingDouble(p -> p.underwriterPref));
			    double acceptableLocVarBaseLtv1AppEase = acceptableLocVarBaseLtv1.parallelStream().collect(Collectors.averagingDouble(p -> p.applicationEase));
			    acceptableLocVarBaseLtv1.parallelStream().forEach(
			    		combinedUW -> {
			    			double productFitness = 0;
			                productFitness = combinedUW.getFitness(acceptableLocVarBaseLtv1Payment, acceptableLocVarBaseLtv11Time, acceptableLocVarBaseLtv1InterestRate, 
			                		acceptableLocVarBaseLtv1Risk, acceptableLocVarBaseLtv1CashBack,acceptableLocVarBaseLtv1PayOut, acceptableLocVarBaseLtv1PayOption, 
			                		acceptableLocVarBaseLtv1BaseComp, acceptableLocVarBaseLtv1BonusComp, acceptableLocVarBaseLtv1MarketingComp, acceptableLocVarBaseLtv1TrailerrComp, 
			                		acceptableLocVarBaseLtv1Speed, acceptableLocVarBaseLtv1BusEase, acceptableLocVarBaseLtv1BranchAttend, 
			                		acceptableLocVarBaseLtv1UwPref, acceptableLocVarBaseLtv1AppEase, acceptableLocVarBaseLtv1TotalComp);
			                combinedUW.fitness = productFitness;
			                UwAppAllProduct failProductJSON = new UwAppAllProduct(combinedUW);
			                failProductJSON.setAlgo("failedProduct");
					    	productsJsonList.add(failProductJSON);
			    		}
			    		);
			    if (wfgDeal == true){
			    	// Sort Combined by order of Ascending Paymentthe Products in order of Descending Fitness and place into a new ArrayList.	
			    	Collections.sort(acceptableLocVarBaseLtv1, byAscPayment);
			    	//acceptableLocVarBaseLtv1.stream().sorted((combinedUW1, combinedUW2) -> Double.compare(combinedUW1.expectedFitnessPayment, combinedUW2.expectedFitnessPayment));
			    }
			    else{
			    	// Sort the Products in order of Descending Fitness 
			    	Collections.sort(acceptableLocVarBaseLtv1, byAscFitness.reversed());
			    	//acceptableLocVarBaseLtv1.stream().sorted((combinedUW1, combinedUW2) -> Double.compare(combinedUW1.fitness, combinedUW2.fitness));
			    }
				
				// Set Values for all acceptableLocVarBaseLtv2 Instances
			    double acceptableLocVarBaseLtv2Payment = acceptableLocVarBaseLtv2.parallelStream().collect(Collectors.averagingDouble(p -> p.expectedFitnessPayment));
			    double acceptableLocVarBaseLtv21Time = acceptableLocVarBaseLtv2.parallelStream().collect(Collectors.averagingDouble(p -> p.oneTimeFees));
			    double acceptableLocVarBaseLtv2InterestRate = acceptableLocVarBaseLtv2.parallelStream().collect(Collectors.averagingDouble(p -> p.interestRate));
			    double acceptableLocVarBaseLtv2Risk = 1;
			    double acceptableLocVarBaseLtv2CashBack = 1;
			    double acceptableLocVarBaseLtv2PayOut = acceptableLocVarBaseLtv2.parallelStream().collect(Collectors.averagingDouble(p -> p.payoutAmount));
			    double acceptableLocVarBaseLtv2PayOption = acceptableLocVarBaseLtv2.parallelStream().collect(Collectors.averagingDouble(p -> p.paymentOptions));
			    double acceptableLocVarBaseLtv2BaseComp = acceptableLocVarBaseLtv2.parallelStream().collect(Collectors.averagingDouble(p -> p.baseCompAmount));
			    double acceptableLocVarBaseLtv2BonusComp = acceptableLocVarBaseLtv2.parallelStream().collect(Collectors.averagingDouble(p -> p.bonusCompAmount));
			    double acceptableLocVarBaseLtv2MarketingComp = acceptableLocVarBaseLtv2.parallelStream().collect(Collectors.averagingDouble(p -> p.marketingCompAmount));
			    double acceptableLocVarBaseLtv2TrailerrComp = acceptableLocVarBaseLtv2.parallelStream().collect(Collectors.averagingDouble(p -> p.trailerCompAmount));
			    double acceptableLocVarBaseLtv2TotalComp = acceptableLocVarBaseLtv2.parallelStream().collect(Collectors.averagingDouble(p -> p.totalCompAmount));
			    double acceptableLocVarBaseLtv2Speed = acceptableLocVarBaseLtv2.parallelStream().collect(Collectors.averagingDouble(p -> p.avgProcessingSpeed));
			    double acceptableLocVarBaseLtv2BusEase = acceptableLocVarBaseLtv2.parallelStream().collect(Collectors.averagingDouble(p -> p.businessEase));
			    double acceptableLocVarBaseLtv2BranchAttend = acceptableLocVarBaseLtv2.parallelStream().collect(Collectors.averagingDouble(p -> p.branchAttend));
			    double acceptableLocVarBaseLtv2UwPref = acceptableLocVarBaseLtv2.parallelStream().collect(Collectors.averagingDouble(p -> p.underwriterPref));
			    double acceptableLocVarBaseLtv2AppEase = acceptableLocVarBaseLtv2.parallelStream().collect(Collectors.averagingDouble(p -> p.applicationEase));
			    acceptableLocVarBaseLtv2.parallelStream().forEach(
			    		combinedUW -> {
			    			double productFitness = 0;
			                productFitness = combinedUW.getFitness(acceptableLocVarBaseLtv2Payment, acceptableLocVarBaseLtv21Time, acceptableLocVarBaseLtv2InterestRate, 
			                		acceptableLocVarBaseLtv2Risk, acceptableLocVarBaseLtv2CashBack,acceptableLocVarBaseLtv2PayOut, acceptableLocVarBaseLtv2PayOption, 
			                		acceptableLocVarBaseLtv2BaseComp, acceptableLocVarBaseLtv2BonusComp, acceptableLocVarBaseLtv2MarketingComp, acceptableLocVarBaseLtv2TrailerrComp, 
			                		acceptableLocVarBaseLtv2Speed, acceptableLocVarBaseLtv2BusEase, acceptableLocVarBaseLtv2BranchAttend, 
			                		acceptableLocVarBaseLtv2UwPref, acceptableLocVarBaseLtv2AppEase, acceptableLocVarBaseLtv2TotalComp);
			                combinedUW.fitness = productFitness;
			                UwAppAllProduct failProductJSON = new UwAppAllProduct(combinedUW);	
			                failProductJSON.setAlgo("failedProduct");
					    	productsJsonList.add(failProductJSON);
			    		}
			    		);
			    if (wfgDeal == true){
			    	// Sort Combined by order of Ascending Paymentthe Products in order of Descending Fitness and place into a new ArrayList.	
			    	Collections.sort(acceptableLocVarBaseLtv2, byAscPayment);
			    	//acceptableLocVarBaseLtv2.stream().sorted((combinedUW1, combinedUW2) -> Double.compare(combinedUW1.expectedFitnessPayment, combinedUW2.expectedFitnessPayment));
			    }
			    else{
			    	// Sort the Products in order of Descending Fitness 
			    	Collections.sort(acceptableLocVarBaseLtv2, byAscFitness.reversed());
			    	//acceptableLocVarBaseLtv2.stream().sorted((combinedUW1, combinedUW2) -> Double.compare(combinedUW1.fitness, combinedUW2.fitness));
			    }
				
				
				// Set Values for all acceptableLocFixedBaseLTV Instances
			    double acceptableLocFixedBaseLTVPayment = acceptableLocFixedBaseLTV.parallelStream().collect(Collectors.averagingDouble(p -> p.expectedFitnessPayment));
			    double acceptableLocFixedBaseLTV1Time = acceptableLocFixedBaseLTV.parallelStream().collect(Collectors.averagingDouble(p -> p.oneTimeFees));
			    double acceptableLocFixedBaseLTVInterestRate = acceptableLocFixedBaseLTV.parallelStream().collect(Collectors.averagingDouble(p -> p.interestRate));
			    double acceptableLocFixedBaseLTVRisk = 1;
			    double acceptableLocFixedBaseLTVCashBack = 1;
			    double acceptableLocFixedBaseLTVPayOut = acceptableLocFixedBaseLTV.parallelStream().collect(Collectors.averagingDouble(p -> p.payoutAmount));
			    double acceptableLocFixedBaseLTVPayOption = acceptableLocFixedBaseLTV.parallelStream().collect(Collectors.averagingDouble(p -> p.paymentOptions));
			    double acceptableLocFixedBaseLTVBaseComp = acceptableLocFixedBaseLTV.parallelStream().collect(Collectors.averagingDouble(p -> p.baseCompAmount));
			    double acceptableLocFixedBaseLTVBonusComp = acceptableLocFixedBaseLTV.parallelStream().collect(Collectors.averagingDouble(p -> p.bonusCompAmount));
			    double acceptableLocFixedBaseLTVMarketingComp = acceptableLocFixedBaseLTV.parallelStream().collect(Collectors.averagingDouble(p -> p.marketingCompAmount));
			    double acceptableLocFixedBaseLTVTrailerrComp = acceptableLocFixedBaseLTV.parallelStream().collect(Collectors.averagingDouble(p -> p.trailerCompAmount));
			    double acceptableLocFixedBaseLTVTotalComp = acceptableLocFixedBaseLTV.parallelStream().collect(Collectors.averagingDouble(p -> p.totalCompAmount));
			    double acceptableLocFixedBaseLTVSpeed = acceptableLocFixedBaseLTV.parallelStream().collect(Collectors.averagingDouble(p -> p.avgProcessingSpeed));
			    double acceptableLocFixedBaseLTVBusEase = acceptableLocFixedBaseLTV.parallelStream().collect(Collectors.averagingDouble(p -> p.businessEase));
			    double acceptableLocFixedBaseLTVBranchAttend = acceptableLocFixedBaseLTV.parallelStream().collect(Collectors.averagingDouble(p -> p.branchAttend));
			    double acceptableLocFixedBaseLTVUwPref = acceptableLocFixedBaseLTV.parallelStream().collect(Collectors.averagingDouble(p -> p.underwriterPref));
			    double acceptableLocFixedBaseLTVAppEase = acceptableLocFixedBaseLTV.parallelStream().collect(Collectors.averagingDouble(p -> p.applicationEase));
			    acceptableLocFixedBaseLTV.parallelStream().forEach(
			    		combinedUW -> {
			    			double productFitness = 0;
			                productFitness = combinedUW.getFitness(acceptableLocFixedBaseLTVPayment, acceptableLocFixedBaseLTV1Time, acceptableLocFixedBaseLTVInterestRate, 
			                		acceptableLocFixedBaseLTVRisk, acceptableLocFixedBaseLTVCashBack,acceptableLocFixedBaseLTVPayOut, acceptableLocFixedBaseLTVPayOption, 
			                		acceptableLocFixedBaseLTVBaseComp, acceptableLocFixedBaseLTVBonusComp, acceptableLocFixedBaseLTVMarketingComp, acceptableLocFixedBaseLTVTrailerrComp, 
			                		acceptableLocFixedBaseLTVSpeed, acceptableLocFixedBaseLTVBusEase, acceptableLocFixedBaseLTVBranchAttend, 
			                		acceptableLocFixedBaseLTVUwPref, acceptableLocFixedBaseLTVAppEase, acceptableLocFixedBaseLTVTotalComp);
			                combinedUW.fitness = productFitness;
			                UwAppAllProduct failProductJSON = new UwAppAllProduct(combinedUW);	
			                failProductJSON.setAlgo("failedProduct");
					    	productsJsonList.add(failProductJSON);
			    		}
			    		);
			    if (wfgDeal == true){
			    	// Sort Combined by order of Ascending Paymentthe Products in order of Descending Fitness and place into a new ArrayList.	
			    	Collections.sort(acceptableLocFixedBaseLTV, byAscPayment);
			    	//acceptableLocFixedBaseLTV.stream().sorted((combinedUW1, combinedUW2) -> Double.compare(combinedUW1.expectedFitnessPayment, combinedUW2.expectedFitnessPayment));
			    }
			    else{
			    	// Sort the Products in order of Descending Fitness 
			    	Collections.sort(acceptableLocFixedBaseLTV, byAscFitness.reversed());
			    	//acceptableLocFixedBaseLTV.stream().sorted((combinedUW1, combinedUW2) -> Double.compare(combinedUW1.fitness, combinedUW2.fitness));
			    }				
				
				// Set Values for all acceptableLocFixedBaseLtv1 Instances
			    double acceptableLocFixedBaseLtv1Payment = acceptableLocFixedBaseLtv1.parallelStream().collect(Collectors.averagingDouble(p -> p.expectedFitnessPayment));
			    double acceptableLocFixedBaseLtv11Time = acceptableLocFixedBaseLtv1.parallelStream().collect(Collectors.averagingDouble(p -> p.oneTimeFees));
			    double acceptableLocFixedBaseLtv1InterestRate = acceptableLocFixedBaseLtv1.parallelStream().collect(Collectors.averagingDouble(p -> p.interestRate));
			    double acceptableLocFixedBaseLtv1Risk = 1;
			    double acceptableLocFixedBaseLtv1CashBack = 1;
			    double acceptableLocFixedBaseLtv1PayOut = acceptableLocFixedBaseLtv1.parallelStream().collect(Collectors.averagingDouble(p -> p.payoutAmount));
			    double acceptableLocFixedBaseLtv1PayOption = acceptableLocFixedBaseLtv1.parallelStream().collect(Collectors.averagingDouble(p -> p.paymentOptions));
			    double acceptableLocFixedBaseLtv1BaseComp = acceptableLocFixedBaseLtv1.parallelStream().collect(Collectors.averagingDouble(p -> p.baseCompAmount));
			    double acceptableLocFixedBaseLtv1BonusComp = acceptableLocFixedBaseLtv1.parallelStream().collect(Collectors.averagingDouble(p -> p.bonusCompAmount));
			    double acceptableLocFixedBaseLtv1MarketingComp = acceptableLocFixedBaseLtv1.parallelStream().collect(Collectors.averagingDouble(p -> p.marketingCompAmount));
			    double acceptableLocFixedBaseLtv1TrailerrComp = acceptableLocFixedBaseLtv1.parallelStream().collect(Collectors.averagingDouble(p -> p.trailerCompAmount));
			    double acceptableLocFixedBaseLtv1TotalComp = acceptableLocFixedBaseLtv1.parallelStream().collect(Collectors.averagingDouble(p -> p.totalCompAmount));
			    double acceptableLocFixedBaseLtv1Speed = acceptableLocFixedBaseLtv1.parallelStream().collect(Collectors.averagingDouble(p -> p.avgProcessingSpeed));
			    double acceptableLocFixedBaseLtv1BusEase = acceptableLocFixedBaseLtv1.parallelStream().collect(Collectors.averagingDouble(p -> p.businessEase));
			    double acceptableLocFixedBaseLtv1BranchAttend = acceptableLocFixedBaseLtv1.parallelStream().collect(Collectors.averagingDouble(p -> p.branchAttend));
			    double acceptableLocFixedBaseLtv1UwPref = acceptableLocFixedBaseLtv1.parallelStream().collect(Collectors.averagingDouble(p -> p.underwriterPref));
			    double acceptableLocFixedBaseLtv1AppEase = acceptableLocFixedBaseLtv1.parallelStream().collect(Collectors.averagingDouble(p -> p.applicationEase));
			    acceptableLocFixedBaseLtv1.parallelStream().forEach(
			    		combinedUW -> {
			    			double productFitness = 0;
			                productFitness = combinedUW.getFitness(acceptableLocFixedBaseLtv1Payment, acceptableLocFixedBaseLtv11Time, acceptableLocFixedBaseLtv1InterestRate, 
			                		acceptableLocFixedBaseLtv1Risk, acceptableLocFixedBaseLtv1CashBack,acceptableLocFixedBaseLtv1PayOut, acceptableLocFixedBaseLtv1PayOption, 
			                		acceptableLocFixedBaseLtv1BaseComp, acceptableLocFixedBaseLtv1BonusComp, acceptableLocFixedBaseLtv1MarketingComp, acceptableLocFixedBaseLtv1TrailerrComp, 
			                		acceptableLocFixedBaseLtv1Speed, acceptableLocFixedBaseLtv1BusEase, acceptableLocFixedBaseLtv1BranchAttend, 
			                		acceptableLocFixedBaseLtv1UwPref, acceptableLocFixedBaseLtv1AppEase, acceptableLocFixedBaseLtv1TotalComp);
			                combinedUW.fitness = productFitness;
			                UwAppAllProduct failProductJSON = new UwAppAllProduct(combinedUW);		
			                failProductJSON.setAlgo("failedProduct");
					    	productsJsonList.add(failProductJSON);
			    		}
			    		);
			   if (wfgDeal == true){
			    	// Sort Combined by order of Ascending Paymentthe Products in order of Descending Fitness and place into a new ArrayList.	
			    	Collections.sort(acceptableLocFixedBaseLtv1, byAscPayment);
			    	//acceptableLocFixedBaseLtv1.stream().sorted((combinedUW1, combinedUW2) -> Double.compare(combinedUW1.expectedFitnessPayment, combinedUW2.expectedFitnessPayment));
			    }
			    else{
			    	// Sort the Products in order of Descending Fitness 
			    	Collections.sort(acceptableLocFixedBaseLtv1, byAscFitness.reversed());
			    	//acceptableLocFixedBaseLtv1.stream().sorted((combinedUW1, combinedUW2) -> Double.compare(combinedUW1.fitness, combinedUW2.fitness));
			    }
				
				// Set Values for all acceptableLocFixedBaseLtv2 Instances
			    double acceptableLocFixedBaseLtv2Payment = acceptableLocFixedBaseLtv2.parallelStream().collect(Collectors.averagingDouble(p -> p.expectedFitnessPayment));
			    double acceptableLocFixedBaseLtv21Time = acceptableLocFixedBaseLtv2.parallelStream().collect(Collectors.averagingDouble(p -> p.oneTimeFees));
			    double acceptableLocFixedBaseLtv2InterestRate = acceptableLocFixedBaseLtv2.parallelStream().collect(Collectors.averagingDouble(p -> p.interestRate));
			    double acceptableLocFixedBaseLtv2Risk = 1;
			    double acceptableLocFixedBaseLtv2CashBack = 1;
			    double acceptableLocFixedBaseLtv2PayOut = acceptableLocFixedBaseLtv2.parallelStream().collect(Collectors.averagingDouble(p -> p.payoutAmount));
			    double acceptableLocFixedBaseLtv2PayOption = acceptableLocFixedBaseLtv2.parallelStream().collect(Collectors.averagingDouble(p -> p.paymentOptions));
			    double acceptableLocFixedBaseLtv2BaseComp = acceptableLocFixedBaseLtv2.parallelStream().collect(Collectors.averagingDouble(p -> p.baseCompAmount));
			    double acceptableLocFixedBaseLtv2BonusComp = acceptableLocFixedBaseLtv2.parallelStream().collect(Collectors.averagingDouble(p -> p.bonusCompAmount));
			    double acceptableLocFixedBaseLtv2MarketingComp = acceptableLocFixedBaseLtv2.parallelStream().collect(Collectors.averagingDouble(p -> p.marketingCompAmount));
			    double acceptableLocFixedBaseLtv2TrailerrComp = acceptableLocFixedBaseLtv2.parallelStream().collect(Collectors.averagingDouble(p -> p.trailerCompAmount));
			    double acceptableLocFixedBaseLtv2TotalComp = acceptableLocFixedBaseLtv2.parallelStream().collect(Collectors.averagingDouble(p -> p.totalCompAmount));
			    double acceptableLocFixedBaseLtv2Speed = acceptableLocFixedBaseLtv2.parallelStream().collect(Collectors.averagingDouble(p -> p.avgProcessingSpeed));
			    double acceptableLocFixedBaseLtv2BusEase = acceptableLocFixedBaseLtv2.parallelStream().collect(Collectors.averagingDouble(p -> p.businessEase));
			    double acceptableLocFixedBaseLtv2BranchAttend = acceptableLocFixedBaseLtv2.parallelStream().collect(Collectors.averagingDouble(p -> p.branchAttend));
			    double acceptableLocFixedBaseLtv2UwPref = acceptableLocFixedBaseLtv2.parallelStream().collect(Collectors.averagingDouble(p -> p.underwriterPref));
			    double acceptableLocFixedBaseLtv2AppEase = acceptableLocFixedBaseLtv2.parallelStream().collect(Collectors.averagingDouble(p -> p.applicationEase));
			    acceptableLocFixedBaseLtv2.parallelStream().forEach(
			    		combinedUW -> {
			    			double productFitness = 0;
			                productFitness = combinedUW.getFitness(acceptableLocFixedBaseLtv2Payment, acceptableLocFixedBaseLtv21Time, acceptableLocFixedBaseLtv2InterestRate, 
			                		acceptableLocFixedBaseLtv2Risk, acceptableLocFixedBaseLtv2CashBack,acceptableLocFixedBaseLtv2PayOut, acceptableLocFixedBaseLtv2PayOption, 
			                		acceptableLocFixedBaseLtv2BaseComp, acceptableLocFixedBaseLtv2BonusComp, acceptableLocFixedBaseLtv2MarketingComp, acceptableLocFixedBaseLtv2TrailerrComp, 
			                		acceptableLocFixedBaseLtv2Speed, acceptableLocFixedBaseLtv2BusEase, acceptableLocFixedBaseLtv2BranchAttend, 
			                		acceptableLocFixedBaseLtv2UwPref, acceptableLocFixedBaseLtv2AppEase, acceptableLocFixedBaseLtv2TotalComp);
			                combinedUW.fitness = productFitness;
			                UwAppAllProduct failProductJSON = new UwAppAllProduct(combinedUW);
			                failProductJSON.setAlgo("failedProduct");
					    	productsJsonList.add(failProductJSON);
			    		}
			    		);
			    if (wfgDeal == true){
			    	// Sort Combined by order of Ascending Paymentthe Products in order of Descending Fitness and place into a new ArrayList.	
			    	Collections.sort(acceptableLocFixedBaseLtv2, byAscPayment);
			    	//acceptableLocFixedBaseLtv2.stream().sorted((combinedUW1, combinedUW2) -> Double.compare(combinedUW1.expectedFitnessPayment, combinedUW2.expectedFitnessPayment));
			    }
			    else{
			    	// Sort the Products in order of Descending Fitness 
			    	Collections.sort(acceptableLocFixedBaseLtv2, byAscFitness.reversed());
			    	//acceptableLocFixedBaseLtv2.stream().sorted((combinedUW1, combinedUW2) -> Double.compare(combinedUW1.fitness, combinedUW2.fitness));
			    }
				
				// Set Values for all acceptableVarFixedBaseLTV Instances
			    double acceptableVarFixedBaseLTVPayment = acceptableVarFixedBaseLTV.parallelStream().collect(Collectors.averagingDouble(p -> p.expectedFitnessPayment));
			    double acceptableVarFixedBaseLTV1Time = acceptableVarFixedBaseLTV.parallelStream().collect(Collectors.averagingDouble(p -> p.oneTimeFees));
			    double acceptableVarFixedBaseLTVInterestRate = acceptableVarFixedBaseLTV.parallelStream().collect(Collectors.averagingDouble(p -> p.interestRate));
			    double acceptableVarFixedBaseLTVRisk = 1;
			    double acceptableVarFixedBaseLTVCashBack = 1;
			    double acceptableVarFixedBaseLTVPayOut = acceptableVarFixedBaseLTV.parallelStream().collect(Collectors.averagingDouble(p -> p.payoutAmount));
			    double acceptableVarFixedBaseLTVPayOption = acceptableVarFixedBaseLTV.parallelStream().collect(Collectors.averagingDouble(p -> p.paymentOptions));
			    double acceptableVarFixedBaseLTVBaseComp = acceptableVarFixedBaseLTV.parallelStream().collect(Collectors.averagingDouble(p -> p.baseCompAmount));
			    double acceptableVarFixedBaseLTVBonusComp = acceptableVarFixedBaseLTV.parallelStream().collect(Collectors.averagingDouble(p -> p.bonusCompAmount));
			    double acceptableVarFixedBaseLTVMarketingComp = acceptableVarFixedBaseLTV.parallelStream().collect(Collectors.averagingDouble(p -> p.marketingCompAmount));
			    double acceptableVarFixedBaseLTVTrailerrComp = acceptableVarFixedBaseLTV.parallelStream().collect(Collectors.averagingDouble(p -> p.trailerCompAmount));
			    double acceptableVarFixedBaseLTVTotalComp = acceptableVarFixedBaseLTV.parallelStream().collect(Collectors.averagingDouble(p -> p.totalCompAmount));
			    double acceptableVarFixedBaseLTVSpeed = acceptableVarFixedBaseLTV.parallelStream().collect(Collectors.averagingDouble(p -> p.avgProcessingSpeed));
			    double acceptableVarFixedBaseLTVBusEase = acceptableVarFixedBaseLTV.parallelStream().collect(Collectors.averagingDouble(p -> p.businessEase));
			    double acceptableVarFixedBaseLTVBranchAttend = acceptableVarFixedBaseLTV.parallelStream().collect(Collectors.averagingDouble(p -> p.branchAttend));
			    double acceptableVarFixedBaseLTVUwPref = acceptableVarFixedBaseLTV.parallelStream().collect(Collectors.averagingDouble(p -> p.underwriterPref));
			    double acceptableVarFixedBaseLTVAppEase = acceptableVarFixedBaseLTV.parallelStream().collect(Collectors.averagingDouble(p -> p.applicationEase));
			    acceptableVarFixedBaseLTV.parallelStream().forEach(
			    		combinedUW -> {
			    			double productFitness = 0;
			                productFitness = combinedUW.getFitness(acceptableVarFixedBaseLTVPayment, acceptableVarFixedBaseLTV1Time, acceptableVarFixedBaseLTVInterestRate, 
			                		acceptableVarFixedBaseLTVRisk, acceptableVarFixedBaseLTVCashBack,acceptableVarFixedBaseLTVPayOut, acceptableVarFixedBaseLTVPayOption, 
			                		acceptableVarFixedBaseLTVBaseComp, acceptableVarFixedBaseLTVBonusComp, acceptableVarFixedBaseLTVMarketingComp, acceptableVarFixedBaseLTVTrailerrComp, 
			                		acceptableVarFixedBaseLTVSpeed, acceptableVarFixedBaseLTVBusEase, acceptableVarFixedBaseLTVBranchAttend, 
			                		acceptableVarFixedBaseLTVUwPref, acceptableVarFixedBaseLTVAppEase, acceptableVarFixedBaseLTVTotalComp);
			                combinedUW.fitness = productFitness;
			                UwAppAllProduct failProductJSON = new UwAppAllProduct(combinedUW);	
			                failProductJSON.setAlgo("failedProduct");
					    	productsJsonList.add(failProductJSON);
			    		}
			    		);
			   if (wfgDeal == true){
			    	// Sort Combined by order of Ascending Paymentthe Products in order of Descending Fitness and place into a new ArrayList.	
			    	Collections.sort(acceptableVarFixedBaseLTV, byAscPayment);
			    	//acceptableVarFixedBaseLTV.stream().sorted((combinedUW1, combinedUW2) -> Double.compare(combinedUW1.expectedFitnessPayment, combinedUW2.expectedFitnessPayment));
			    }
			    else{
			    	// Sort the Products in order of Descending Fitness 
			    	Collections.sort(acceptableVarFixedBaseLTV, byAscFitness.reversed());
			    	//acceptableVarFixedBaseLTV.stream().sorted((combinedUW1, combinedUW2) -> Double.compare(combinedUW1.fitness, combinedUW2.fitness));
			    }
				
				
				// Set Values for all acceptableVarFixedBaseLtv1 Instances
			    double acceptableVarFixedBaseLtv1Payment = acceptableVarFixedBaseLtv1.parallelStream().collect(Collectors.averagingDouble(p -> p.expectedFitnessPayment));
			    double acceptableVarFixedBaseLtv11Time = acceptableVarFixedBaseLtv1.parallelStream().collect(Collectors.averagingDouble(p -> p.oneTimeFees));
			    double acceptableVarFixedBaseLtv1InterestRate = acceptableVarFixedBaseLtv1.parallelStream().collect(Collectors.averagingDouble(p -> p.interestRate));
			    double acceptableVarFixedBaseLtv1Risk = 1;
			    double acceptableVarFixedBaseLtv1CashBack = 1;
			    double acceptableVarFixedBaseLtv1PayOut = acceptableVarFixedBaseLtv1.parallelStream().collect(Collectors.averagingDouble(p -> p.payoutAmount));
			    double acceptableVarFixedBaseLtv1PayOption = acceptableVarFixedBaseLtv1.parallelStream().collect(Collectors.averagingDouble(p -> p.paymentOptions));
			    double acceptableVarFixedBaseLtv1BaseComp = acceptableVarFixedBaseLtv1.parallelStream().collect(Collectors.averagingDouble(p -> p.baseCompAmount));
			    double acceptableVarFixedBaseLtv1BonusComp = acceptableVarFixedBaseLtv1.parallelStream().collect(Collectors.averagingDouble(p -> p.bonusCompAmount));
			    double acceptableVarFixedBaseLtv1MarketingComp = acceptableVarFixedBaseLtv1.parallelStream().collect(Collectors.averagingDouble(p -> p.marketingCompAmount));
			    double acceptableVarFixedBaseLtv1TrailerrComp = acceptableVarFixedBaseLtv1.parallelStream().collect(Collectors.averagingDouble(p -> p.trailerCompAmount));
			    double acceptableVarFixedBaseLtv1TotalComp = acceptableVarFixedBaseLtv1.parallelStream().collect(Collectors.averagingDouble(p -> p.totalCompAmount));
			    double acceptableVarFixedBaseLtv1Speed = acceptableVarFixedBaseLtv1.parallelStream().collect(Collectors.averagingDouble(p -> p.avgProcessingSpeed));
			    double acceptableVarFixedBaseLtv1BusEase = acceptableVarFixedBaseLtv1.parallelStream().collect(Collectors.averagingDouble(p -> p.businessEase));
			    double acceptableVarFixedBaseLtv1BranchAttend = acceptableVarFixedBaseLtv1.parallelStream().collect(Collectors.averagingDouble(p -> p.branchAttend));
			    double acceptableVarFixedBaseLtv1UwPref = acceptableVarFixedBaseLtv1.parallelStream().collect(Collectors.averagingDouble(p -> p.underwriterPref));
			    double acceptableVarFixedBaseLtv1AppEase = acceptableVarFixedBaseLtv1.parallelStream().collect(Collectors.averagingDouble(p -> p.applicationEase));
			    acceptableVarFixedBaseLtv1.parallelStream().forEach(
			    		combinedUW -> {
			    			double productFitness = 0;
			                productFitness = combinedUW.getFitness(acceptableVarFixedBaseLtv1Payment, acceptableVarFixedBaseLtv11Time, acceptableVarFixedBaseLtv1InterestRate, 
			                		acceptableVarFixedBaseLtv1Risk, acceptableVarFixedBaseLtv1CashBack,acceptableVarFixedBaseLtv1PayOut, acceptableVarFixedBaseLtv1PayOption, 
			                		acceptableVarFixedBaseLtv1BaseComp, acceptableVarFixedBaseLtv1BonusComp, acceptableVarFixedBaseLtv1MarketingComp, acceptableVarFixedBaseLtv1TrailerrComp, 
			                		acceptableVarFixedBaseLtv1Speed, acceptableVarFixedBaseLtv1BusEase, acceptableVarFixedBaseLtv1BranchAttend, 
			                		acceptableVarFixedBaseLtv1UwPref, acceptableVarFixedBaseLtv1AppEase, acceptableVarFixedBaseLtv1TotalComp);
			                combinedUW.fitness = productFitness;
			                try{
			                UwAppAllProduct failProductJSON = new UwAppAllProduct(combinedUW);	
			                failProductJSON.setAlgo("failedProduct");
					    	productsJsonList.add(failProductJSON);
			                }catch(Exception e){
			                	
			                }
			                }
			    		);
			    if (wfgDeal == true){
			    	// Sort Combined by order of Ascending Paymentthe Products in order of Descending Fitness and place into a new ArrayList.	
			    	Collections.sort(acceptableVarFixedBaseLtv1, byAscPayment);
			    	//acceptableVarFixedBaseLtv1.stream().sorted((combinedUW1, combinedUW2) -> Double.compare(combinedUW1.expectedFitnessPayment, combinedUW2.expectedFitnessPayment));
			    }
			    else{
			    	// Sort the Products in order of Descending Fitness 
			    	Collections.sort(acceptableVarFixedBaseLtv1, byAscFitness.reversed());
			    	//acceptableVarFixedBaseLtv1.stream().sorted((combinedUW1, combinedUW2) -> Double.compare(combinedUW1.fitness, combinedUW2.fitness));
			    }
			    
			    // Set Values for all acceptableVarFixedBaseLtv2 Instances
			    double acceptableVarFixedBaseLtv2Payment = acceptableVarFixedBaseLtv2.parallelStream().collect(Collectors.averagingDouble(p -> p.expectedFitnessPayment));
			    double acceptableVarFixedBaseLtv21Time = acceptableVarFixedBaseLtv2.parallelStream().collect(Collectors.averagingDouble(p -> p.oneTimeFees));
			    double acceptableVarFixedBaseLtv2InterestRate = acceptableVarFixedBaseLtv2.parallelStream().collect(Collectors.averagingDouble(p -> p.interestRate));
			    double acceptableVarFixedBaseLtv2Risk = 1;
			    double acceptableVarFixedBaseLtv2CashBack = 1;
			    double acceptableVarFixedBaseLtv2PayOut = acceptableVarFixedBaseLtv2.parallelStream().collect(Collectors.averagingDouble(p -> p.payoutAmount));
			    double acceptableVarFixedBaseLtv2PayOption = acceptableVarFixedBaseLtv2.parallelStream().collect(Collectors.averagingDouble(p -> p.paymentOptions));
			    double acceptableVarFixedBaseLtv2BaseComp = acceptableVarFixedBaseLtv2.parallelStream().collect(Collectors.averagingDouble(p -> p.baseCompAmount));
			    double acceptableVarFixedBaseLtv2BonusComp = acceptableVarFixedBaseLtv2.parallelStream().collect(Collectors.averagingDouble(p -> p.bonusCompAmount));
			    double acceptableVarFixedBaseLtv2MarketingComp = acceptableVarFixedBaseLtv2.parallelStream().collect(Collectors.averagingDouble(p -> p.marketingCompAmount));
			    double acceptableVarFixedBaseLtv2TrailerrComp = acceptableVarFixedBaseLtv2.parallelStream().collect(Collectors.averagingDouble(p -> p.trailerCompAmount));
			    double acceptableVarFixedBaseLtv2TotalComp = acceptableVarFixedBaseLtv2.parallelStream().collect(Collectors.averagingDouble(p -> p.totalCompAmount));
			    double acceptableVarFixedBaseLtv2Speed = acceptableVarFixedBaseLtv2.parallelStream().collect(Collectors.averagingDouble(p -> p.avgProcessingSpeed));
			    double acceptableVarFixedBaseLtv2BusEase = acceptableVarFixedBaseLtv2.parallelStream().collect(Collectors.averagingDouble(p -> p.businessEase));
			    double acceptableVarFixedBaseLtv2BranchAttend = acceptableVarFixedBaseLtv2.parallelStream().collect(Collectors.averagingDouble(p -> p.branchAttend));
			    double acceptableVarFixedBaseLtv2UwPref = acceptableVarFixedBaseLtv2.parallelStream().collect(Collectors.averagingDouble(p -> p.underwriterPref));
			    double acceptableVarFixedBaseLtv2AppEase = acceptableVarFixedBaseLtv2.parallelStream().collect(Collectors.averagingDouble(p -> p.applicationEase));
			    acceptableVarFixedBaseLtv2.parallelStream().forEach(
			    		combinedUW -> {
			    			double productFitness = 0;
			                productFitness = combinedUW.getFitness(acceptableVarFixedBaseLtv2Payment, acceptableVarFixedBaseLtv21Time, acceptableVarFixedBaseLtv2InterestRate, 
			                		acceptableVarFixedBaseLtv2Risk, acceptableVarFixedBaseLtv2CashBack,acceptableVarFixedBaseLtv2PayOut, acceptableVarFixedBaseLtv2PayOption, 
			                		acceptableVarFixedBaseLtv2BaseComp, acceptableVarFixedBaseLtv2BonusComp, acceptableVarFixedBaseLtv2MarketingComp, acceptableVarFixedBaseLtv2TrailerrComp, 
			                		acceptableVarFixedBaseLtv2Speed, acceptableVarFixedBaseLtv2BusEase, acceptableVarFixedBaseLtv2BranchAttend, 
			                		acceptableVarFixedBaseLtv2UwPref, acceptableVarFixedBaseLtv2AppEase, acceptableVarFixedBaseLtv2TotalComp);
			                combinedUW.fitness = productFitness;
			                try{
			                UwAppAllProduct failProductJSON = new UwAppAllProduct(combinedUW);	
			                failProductJSON.setAlgo("failedProduct");
					    	productsJsonList.add(failProductJSON);
			                }catch(Exception e){
			                	
			                }
			                }
			    		);
			    if (wfgDeal == true){
			    	// Sort Combined by order of Ascending Paymentthe Products in order of Descending Fitness and place into a new ArrayList.	
			    	Collections.sort(acceptableVarFixedBaseLtv2, byAscPayment);
			    	//acceptableVarFixedBaseLtv2.stream().sorted((combinedUW1, combinedUW2) -> Double.compare(combinedUW1.expectedFitnessPayment, combinedUW2.expectedFitnessPayment));
			    }
			    else{
			    	// Sort the Products in order of Descending Fitness 
			    	Collections.sort(acceptableVarFixedBaseLtv2, byAscFitness.reversed());
			    	//acceptableVarFixedBaseLtv2.stream().sorted((combinedUW1, combinedUW2) -> Double.compare(combinedUW1.fitness, combinedUW2.fitness));
			    }
			    
			    // Need to Add Failed Combined Products to CB ... 
			    failedLocVarBaseLTV.parallelStream().forEach(
			    		combinedUW -> {	
			    			try{
			    			UwAppAllProduct failProductJSON = new UwAppAllProduct(combinedUW);
			    			 failProductJSON.setAlgo("failedProduct");
			    			productsJsonList.add(failProductJSON);
			    		}catch(Exception e){
			    			
			    		}
			    		}	
			    		);
			    failedLocVarBaseLtv1.parallelStream().forEach(
			    		combinedUW -> {			    			
			    			try{
				    			UwAppAllProduct failProductJSON = new UwAppAllProduct(combinedUW);
				    			 failProductJSON.setAlgo("failedProduct");
				    			productsJsonList.add(failProductJSON);
				    		}catch(Exception e){
				    			
				    		}
			    		}
			    		);
			    failedLocVarBaseLtv2.parallelStream().forEach(
			    		combinedUW -> {			    			
			    			try{
				    			UwAppAllProduct failProductJSON = new UwAppAllProduct(combinedUW);
				    			 failProductJSON.setAlgo("failedProduct");
				    			productsJsonList.add(failProductJSON);
				    		}catch(Exception e){
				    			
				    		}
			    		}
			    		);
			    failedLocFixedBaseLTV.parallelStream().forEach(
			    		combinedUW -> {			    			
			    			try{
				    			UwAppAllProduct failProductJSON = new UwAppAllProduct(combinedUW);
				    			 failProductJSON.setAlgo("failedProduct");
				    			productsJsonList.add(failProductJSON);
				    		}catch(Exception e){
				    			
				    		}
			    		}
			    		);
			    failedLocFixedBaseLtv1.parallelStream().forEach(
			    		combinedUW -> {			    			
			    			try{
				    			UwAppAllProduct failProductJSON = new UwAppAllProduct(combinedUW);
				    			 failProductJSON.setAlgo("failedProduct");
				    			productsJsonList.add(failProductJSON);
				    		}catch(Exception e){
				    			
				    		}
			    		}
			    		);
			    failedLocFixedBaseLtv2.parallelStream().forEach(
			    		combinedUW -> {			    			
			    			try{
				    			UwAppAllProduct failProductJSON = new UwAppAllProduct(combinedUW);
				    			 failProductJSON.setAlgo("failedProduct");
				    			productsJsonList.add(failProductJSON);
				    		}catch(Exception e){
				    			
				    		}
			    		}
			    		); 					
			    failedVarFixedBaseLTV.parallelStream().forEach(
			    		combinedUW -> {			    			
			    			try{
				    			UwAppAllProduct failProductJSON = new UwAppAllProduct(combinedUW);
				    			 failProductJSON.setAlgo("failedProduct");
				    			productsJsonList.add(failProductJSON);
				    		}catch(Exception e){
				    			
				    		}
			    		}
			    		);
			    failedVarFixedBaseLtv1.parallelStream().forEach(
			    		combinedUW -> {			    			
			    			try{
				    			UwAppAllProduct failProductJSON = new UwAppAllProduct(combinedUW);
				    			 failProductJSON.setAlgo("failedProduct");
				    			productsJsonList.add(failProductJSON);
				    		}catch(Exception e){
				    			
				    		}
			    		}
			    		);		
			    failedVarFixedBaseLtv2.parallelStream().forEach(
			    		combinedUW -> {			    			
			    			try{
				    			UwAppAllProduct failProductJSON = new UwAppAllProduct(combinedUW);
				    			 failProductJSON.setAlgo("failedProduct");
				    			productsJsonList.add(failProductJSON);
				    		}catch(Exception e){
				    			
				    		}
			    		}
			    		);
				
			  
			    /* // Just for testing of sorting
			    for (int i = 1; i < acceptableVarFixedBaseLtv2.size(); i++) {
			    	
			    	UnderwriteCombined curentUnderwriteAll=acceptableVarFixedBaseLtv2.get(i);
			    	UnderwriteCombined preeviousUnderwriteAll=acceptableVarFixedBaseLtv2.get(i-1);

					if(curentUnderwriteAll.getExpectedFitnessPayment() > preeviousUnderwriteAll.getExpectedFitnessPayment()){
						//logger.info("Problem with Ascending payment:"+"element: " + i + ": "+curentUnderwriteAll.getExpectedFitnessPayment()+ " previouselement: " + (i - 1) + ": " +preeviousUnderwriteAll.getExpectedFitnessPayment());
					}
					
				}
			    
			    
			 // Just for testing of sorting
			    for (int i = 1; i < acceptableVarFixedBaseLtv2.size(); i++) {
			    	
			    	UnderwriteCombined curentUnderwriteAll=acceptableVarFixedBaseLtv2.get(i);
			    	UnderwriteCombined preeviousUnderwriteAll=acceptableVarFixedBaseLtv2.get(i-1);

					if(curentUnderwriteAll.fitness < preeviousUnderwriteAll.fitness){
						//logger.info("Problem with Desending Fitness:"+"element: " + i + ": "+curentUnderwriteAll.fitness+ " previouselement: " + (i - 1) + ": " + preeviousUnderwriteAll.fitness);
					}
					
				}*/

			    
			 
			   logger.info("About to Streal Underwriting calc");
			    
			    listOfProductLists.stream().parallel().forEach(
			    		listOfAcceptableProducts -> {
			    			if (listOfAcceptableProducts.size() > 0){
					        	double averageCostToUse = Util.listAlgoAverage(listOfAcceptableProducts, "expectedFitnessPayment");	//ListOfAcceptableProducts.Average(product => product.potentialProduct.ExpectedPayment);                                
					            								try {
									averageOneTimeCost = Util.listAverage(listOfAcceptableProducts, "oneTimeFees");
								} catch (Exception e1) {
									// TODO Auto-generated catch block
									//e1.printStackTrace();
								}		// ListOfAcceptableProducts.Average(product => product.OneTimeFees);
					           
								try {
									averageInterestRate = Util.listAverage(listOfAcceptableProducts, "interestRate");
								} catch (Exception e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}	//ListOfAcceptableProducts.Average(product => product.potentialProduct.interest_rate);
					            double averageRisk = 1; // Need to talk with Kendall whether we will use Risk of instead get each of the best of LOC, Variable, Fixed
					            double averageCashback = Util.listAlgoAverage(listOfAcceptableProducts, "cashbackAmount");	//ListOfAcceptableProducts.Average(product => product.CashbackAmount);
					            double averagePayoutPenalty = Util.listAlgoAverage(listOfAcceptableProducts, "payoutAmount");	//ListOfAcceptableProducts.Average(product => product.PayoutAmount);
					            double averagePaymentOption = Util.listAlgoAverage(listOfAcceptableProducts, "paymentOptions");	//ListOfAcceptableProducts.Average(product => product.PaymentOptions);
					            double averageBaseComp = Util.listAlgoAverage(listOfAcceptableProducts, "baseCompAmount");	//ListOfAcceptableProducts.Average(product => product.BaseCompAmount);
					            double averageBonusComp = Util.listAlgoAverage(listOfAcceptableProducts, "bonusCompAmount");	//ListOfAcceptableProducts.Average(product => product.BonusCompAmount);
					            double averageMarketingComp = Util.listAlgoAverage(listOfAcceptableProducts, "marketingCompAmount");	//ListOfAcceptableProducts.Average(product => product.MarketingCompAmount);
					            double averageTrailerComp = Util.listAlgoAverage(listOfAcceptableProducts, "trailerCompAmount");	//ListOfAcceptableProducts.Average(product => product.TrailerCompAmount);
					            double averageTotalComp = Util.listAlgoAverage(listOfAcceptableProducts, "totalCompAmount");	//ListOfAcceptableProducts.Average(product => product.TotalCompAmount);
					           
								try {
									averageSpeed = Util.listAverage(listOfAcceptableProducts, "avgProcessingSpeed");
								} catch (Exception e1) {
									// TODO Auto-generated catch block
									//e1.printStackTrace();
								}	//ListOfAcceptableProducts.Average(product => product.potentialProduct.avg_processing_speed);
					           
								try {
									averageBusinessEase = Util.listAverage(listOfAcceptableProducts, "businessEase");
								} catch (Exception e1) {
									// TODO Auto-generated catch block
									//e1.printStackTrace();
								}	//ListOfAcceptableProducts.Average(product => product.potentialProduct.business_ease);
					            double averageBranchAttend = Util.listAlgoAverage(listOfAcceptableProducts, "branchAttend");	//ListOfAcceptableProducts.Average(product => product.BranchAttend);
					           
								try {
									averageUnderwriterPreference = Util.listAverage(listOfAcceptableProducts, "underwriterPref");
								} catch (Exception e1) {
									logger.error("List  Of  average  Not  accepetd  "+e1);
									//e1.printStackTrace();
								}	//ListOfAcceptableProducts.Average(product => product.potentialProduct.underwriter_pref);
					            double averageApplicationEase = Util.listAlgoAverage(listOfAcceptableProducts, "applicationEase");	//ListOfAcceptableProducts.Average(product => product.potentialProduct.ApplicationEase);

					            
					            
					           /* for (UnderwriteAll qualifiedProduct : listOfAcceptableProducts)*/
					            
					            
					            listOfAcceptableProducts.stream().parallel().forEach(
					            		qualifiedProduct -> 
					            {
					               try{
					                productFitness = qualifiedProduct.getFitness(averageCostToUse, averageOneTimeCost, averageInterestRate, averageRisk, averageCashback,
					                        averagePayoutPenalty, averagePaymentOption, averageBaseComp, averageBonusComp, averageMarketingComp,
					                        averageTrailerComp, averageSpeed, averageBusinessEase, averageBranchAttend, averageUnderwriterPreference, averageApplicationEase, averageTotalComp);
					                qualifiedProduct.fitness = productFitness;
					               
					               }catch(Exception e){
					            	   logger.error("productFitness  not  found"+e.getMessage());
					            	   
					               }
					               
					               try{
					                UwAppAllProduct passedProductJSON = new UwAppAllProduct(qualifiedProduct);
					                passedProductJSON.setAlgo("passedProduct");
							    	productsJsonList.add(passedProductJSON);
							    	
					               }catch(Exception  e){
					            	   logger.error("passedProduct  not  found"+e.getMessage());

					               }
					          /*  }*/
			    			});
					          //  System.out.println("*****************product json size************* "+productsJsonList.size());
					            
					           /* for (Iterator iterator = productsJsonList
										.iterator(); iterator.hasNext();) {
					            	UnderwriteAll underwriteAll = (UnderwriteAll) iterator
											.next();
									System.out.println("product Name "+underwriteAll.getExpectedFitnessPayment());
									
								}*/
					         
					            // Sort each List by interest Rate and determine the lowest interest rate Product
					            GenericComparator orderbyInterestRate = Util.newGenericComparator("interestRate","asc"); 
					            Collections.sort(listOfAcceptableProducts, orderbyInterestRate);
					            Product LowestRateProduct = listOfAcceptableProducts.get(0).potentialProduct;  
					            UnderwriteAll LowestRateUnderwrite = listOfAcceptableProducts.get(0);
					            
					            
					            // Sort the Products in order of Descending Fitness and place into a new ArrayList.
					            //List<Underwrite> SortedList = ListOfAcceptableProducts.OrderByDescending(ProductFitness => ProductFitness.Fitness).ToList();
//					            GenericComparator orderby = Util.newGenericComparator("fitness","desc");                     
//					            Collections.sort(listOfAcceptableProducts, orderby);
					            
					            double highestFitness = 0;
					            UnderwriteAll bestProduct = null;
					            for (int productCounter = 0; productCounter < listOfAcceptableProducts.size(); productCounter++){
					            	if (productCounter == 0){
					            		highestFitness = listOfAcceptableProducts.get(productCounter).fitness;
					            		bestProduct = listOfAcceptableProducts.get(productCounter);
					            		//logger.info(bestProduct.potentialProduct.name + " - " + bestProduct.potentialProduct.interestRate);
					            	}
					            	else{
					            		if (listOfAcceptableProducts.get(productCounter).interestRate == listOfAcceptableProducts.get(productCounter - 1).interestRate){
					            			if (listOfAcceptableProducts.get(productCounter).fitness > listOfAcceptableProducts.get(productCounter - 1).fitness){
					            				highestFitness = listOfAcceptableProducts.get(productCounter).fitness;
							            		bestProduct = listOfAcceptableProducts.get(productCounter);
							            		//logger.info(bestProduct.potentialProduct.name + " - " + bestProduct.potentialProduct.interestRate);
					            			}
					            			else{
					            				
					            				//logger.info(bestProduct.potentialProduct.name + " - " + bestProduct.potentialProduct.interestRate);
					            			}
					            		}
					            		else if (listOfAcceptableProducts.get(productCounter).interestRate > listOfAcceptableProducts.get(productCounter - 1).interestRate){
					            			
					            			//logger.info(bestProduct.potentialProduct.name + " - " + bestProduct.potentialProduct.interestRate);
						            		break;
					            		}
					            		else if (listOfAcceptableProducts.get(productCounter).interestRate < listOfAcceptableProducts.get(productCounter - 1).interestRate){
					            			// Should never be called, assuming that the ascending sorting by rate worked
					            			highestFitness = listOfAcceptableProducts.get(productCounter).fitness;
						            		bestProduct = listOfAcceptableProducts.get(productCounter);
						            		//logger.info(bestProduct.potentialProduct.name + " - " + bestProduct.potentialProduct.interestRate);
					            		}
					            	}
					            }
					            
					            // Add the Lowest Rate Product to the list of Recommendations
					            if(bestProduct != null){                    	
					            	ProductRecommendations1.add(bestProduct);
					            }
					            
					            
					         // 5 Year Product ... Capture for later 
					            
					            if(listOfAcceptableProducts.get(0).potentialProduct.term == "6"){
					            	fiveYearProduct = listOfAcceptableProducts.get(0).potentialProduct;
					            }
					            
					            if(listOfAcceptableProducts.get(0).interestRate < 1)
					            	logger.info("Rate Issue");
					            
					        }        
			    
				    	}); 
               
               		

               	// Please Do not Delete or Alter These below loops - they will be used in Determining Recommendation	
			    List<List<UnderwriteCombined>> listOfListsUwCombinedLTV = new ArrayList<List<UnderwriteCombined>>();
			    listOfListsUwCombinedLTV.add(acceptableLocVarBaseLTV);
			    listOfListsUwCombinedLTV.add(acceptableLocFixedBaseLTV);
			    listOfListsUwCombinedLTV.add(acceptableVarFixedBaseLTV);
			    int listCounterLTV = 0;
			    int minPaymentListLTV = 0;
			    double minPaymentLTV = 10000000.00;
			    for (List<UnderwriteCombined> list : listOfListsUwCombinedLTV) {
					if (list.size() > 0){		
						listCounterLTV = listCounterLTV + 1;
						if (list.get(0).expectedFitnessPayment < minPaymentLTV){
							minPaymentListLTV = listCounterLTV;
						}
					}
					else{
						listCounterLTV = listCounterLTV + 1;
					}
				}
			    
			    List<List<UnderwriteCombined>> listOfListsUwCombinedLtv1 = new ArrayList<List<UnderwriteCombined>>();
			    listOfListsUwCombinedLtv1.add(acceptableLocVarBaseLtv1);
			    listOfListsUwCombinedLtv1.add(acceptableLocFixedBaseLtv1);
			    listOfListsUwCombinedLtv1.add(acceptableVarFixedBaseLtv1);
			    int listCounterLtv1 = 0;
			    int minPaymentListLtv1 = 0;
			    double minPaymentLtv1 = 10000000.00;
			    for (List<UnderwriteCombined> list : listOfListsUwCombinedLtv1) {
					if (list.size() > 0){		
						listCounterLtv1 = listCounterLtv1 + 1;
						if (list.get(0).expectedFitnessPayment < minPaymentLtv1){
							minPaymentListLtv1 = listCounterLtv1;
						}
					}
					else{
						listCounterLtv1 = listCounterLtv1 + 1;
					}
				}
			    
			    List<List<UnderwriteCombined>> listOfListsUwCombinedLtv2 = new ArrayList<List<UnderwriteCombined>>();
			    listOfListsUwCombinedLtv2.add(acceptableLocVarBaseLtv2);
			    listOfListsUwCombinedLtv2.add(acceptableLocFixedBaseLtv2);
			    listOfListsUwCombinedLtv2.add(acceptableVarFixedBaseLtv2);
			    int listCounterLtv2 = 0;
			    int minPaymentListLtv2 = 0;
			    double minPaymentLtv2 = 10000000.00;
			    for (List<UnderwriteCombined> list : listOfListsUwCombinedLtv2) {
					if (list.size() > 0){		
						listCounterLtv2 = listCounterLtv2 + 1;
						if (list.get(0).expectedFitnessPayment < minPaymentLtv2){
							minPaymentListLtv2 = listCounterLtv2;
						}
					}
					else{
						listCounterLtv2 = listCounterLtv2 + 1;
					}
				}
			    
			    
			    //Create CombinedRecommendations
			    ArrayList<CombinedRecommendation> combinedRecommendationsList = new ArrayList<>();
			    
			    CombinedRecommendation combRecommendation=null;
			    CombinedRecommendation combRecommendationjson=null;
			    
                     
			    
			    Set<com.syml.bean.algo.CombinedRecommendation>  setCombinedRecommendation=new HashSet<com.syml.bean.algo.CombinedRecommendation>();
			   
			    try{
			    	logger.info("clientOpportunity.wfgDeal  is"+clientOpportunity.wfgDeal);
			    }catch(NullPointerException  e){
			    	logger.error("wfgDeal  is  null"+e.getMessage());
			    	
			    }
			    if (clientOpportunity.wfgDeal == false){
			    	
			    	logger.info("********clientOpportunity.wfgDeal  is  false*****************************************");
			    	
			    	logger.info("clientOpportunity.baseLtv  is"+clientOpportunity.baseLtv);
			    	// Exclude Line of Credit Combined Products
			    	if (clientOpportunity.baseLtv != null && clientOpportunity.baseLtv > 0){	
				    	logger.info("clientOpportunity.baseLtv is  not null && clientOpportunity.baseLtv > 0");

			    		if (acceptableVarFixedBaseLTV.size() > 0){
			    			logger.info("clientOpportunity.baseLtv  is  Not  Null and  greater  then  0");
			    			
			    			logger.info("--------------------------acceptableVarFixedBaseLTV------------------------"+acceptableVarFixedBaseLTV.size());
			    			try{
			    			combRecommendation = new CombinedRecommendation(acceptableVarFixedBaseLTV.get(0));
			    			}catch(Exception  e){
			    				logger.error("acceptableVarFixedBaseLTV  is  null");
			    			}
			    			 logger.info("pojo rec {}-----------------------------: "+combRecommendation);
			    			combinedRecommendationsList.add(combRecommendation);
			    			///logger.info(""+combRecommendation.toString());
			    			
			    			
			    		}
//			    		if (acceptableVarFixedBaseLtv1.size() > 0){
//			    			CombinedRecommendation combRecommendation = new CombinedRecommendation(acceptableVarFixedBaseLtv1.get(0));
//			    			combinedRecommendationsList.add(combRecommendation);
//			    		}			    		
			    		if (acceptableVarFixedBaseLtv2.size() > 0){
			    			logger.info("acceptableVarFixedBaseLtv2.size() > 0");

			    			 combRecommendation = new CombinedRecommendation(acceptableVarFixedBaseLtv2.get(0));
			    			combinedRecommendationsList.add(combRecommendation);
			    		}
					}
			    	else{
			    		if (acceptableVarFixedBaseLtv1.size() > 0){
			    			logger.info("acceptableVarFixedBaseLtv1  greater  then  0");

			    			
			    			 combRecommendation = new CombinedRecommendation(acceptableVarFixedBaseLtv1.get(0));
			    			combinedRecommendationsList.add(combRecommendation);
			    		}			    		
			    		if (acceptableVarFixedBaseLtv2.size() > 0){
			    			 combRecommendation = new CombinedRecommendation(acceptableVarFixedBaseLtv2.get(0));
			    			combinedRecommendationsList.add(combRecommendation);
			    		}
			    		if (acceptableLocVarBaseLtv2.size() > 0){
			    			 combRecommendation = new CombinedRecommendation(acceptableLocVarBaseLtv2.get(0));
			    			combinedRecommendationsList.add(combRecommendation);
			    		}
			    		if (acceptableLocFixedBaseLtv2.size() > 0){
			    			 combRecommendation = new CombinedRecommendation(acceptableLocFixedBaseLtv2.get(0));
			    			combinedRecommendationsList.add(combRecommendation);
			    		}
			    	}
			    }
			    else{
			    	logger.info("------------clientOpportunity.baseLtv---------"+clientOpportunity.baseLtv);
			    	if (clientOpportunity.baseLtv != null && clientOpportunity.baseLtv > 0){
				    	logger.info("------------clientOpportunity.baseLtv  is  not  Null-------");

			    		// Use Lowest Payment Combined Products LTV
//			    		if (minPaymentListLTV == 1){
//			    			CombinedRecommendation combRecommendation = new CombinedRecommendation(acceptableLocVarBaseLTV.get(0));
//			    			combinedRecommendationsList.add(combRecommendation);
//			    		}
//			    		else if (minPaymentListLTV == 2){
//			    			CombinedRecommendation combRecommendation = new CombinedRecommendation(acceptableLocFixedBaseLTV.get(0));
//			    			combinedRecommendationsList.add(combRecommendation);
//			    		}
//			    		else if (minPaymentListLTV == 3){
//			    			CombinedRecommendation combRecommendation = new CombinedRecommendation(acceptableVarFixedBaseLTV.get(0));
//			    			combinedRecommendationsList.add(combRecommendation);
//			    		}
				    	logger.info("------------acceptableVarFixedBaseLTV------"+acceptableVarFixedBaseLTV.size());
				    	logger.info("------------acceptableVarFixedBaseLtv1------"+acceptableVarFixedBaseLtv1.size());
				    	logger.info("------------acceptableVarFixedBaseLtv2------"+acceptableVarFixedBaseLtv2.size());



			    		if (acceptableVarFixedBaseLTV.size() > 0){

			    			 combRecommendation = new CombinedRecommendation(acceptableVarFixedBaseLTV.get(0));
			    			 
			    			combinedRecommendationsList.add(combRecommendation);
			    		}
			    		if (acceptableVarFixedBaseLtv1.size() > 0){
			    			combRecommendation = new CombinedRecommendation(acceptableVarFixedBaseLtv1.get(0));
			    			combinedRecommendationsList.add(combRecommendation);
			    		}
			    		
			    		if (acceptableVarFixedBaseLtv2.size() > 0){
			    			 combRecommendation = new CombinedRecommendation(acceptableVarFixedBaseLtv2.get(0));
			    			combinedRecommendationsList.add(combRecommendation);
			    		}
					}
			    	else{
			    		
			    		logger.info("---------------clientOpportunity.baseLtv  Else  Case------------------------"+clientOpportunity.baseLtv.SIZE);
			    		// Use Lowest Payment Combined Products LTv1
			    		if (acceptableVarFixedBaseLtv1.size() > 0){
			    			 combRecommendation = new CombinedRecommendation(acceptableVarFixedBaseLtv1.get(0));
					    		logger.info("---------------CombinedRecommendation Pojo------------------------"+combRecommendation.toString());

			    			combinedRecommendationsList.add(combRecommendation);
			    		}			    		
			    		if (acceptableVarFixedBaseLtv2.size() > 0){
			    			combRecommendation = new CombinedRecommendation(acceptableVarFixedBaseLtv2.get(0));
			    			combinedRecommendationsList.add(combRecommendation);
			    		}
			    		if (acceptableLocVarBaseLtv1.size() > 0){
			    			 combRecommendation = new CombinedRecommendation(acceptableLocVarBaseLtv1.get(0));
			    			combinedRecommendationsList.add(combRecommendation);
			    		}
			    		if (acceptableLocFixedBaseLtv1.size() > 0){
			    			 combRecommendation = new CombinedRecommendation(acceptableLocFixedBaseLtv1.get(0));
			    			combinedRecommendationsList.add(combRecommendation);
			    		}
			    		if (acceptableLocVarBaseLtv2.size() > 0){
			    			 combRecommendation = new CombinedRecommendation(acceptableLocVarBaseLtv2.get(0));
			    			combinedRecommendationsList.add(combRecommendation);
			    		}
			    		if (acceptableLocFixedBaseLtv2.size() > 0){
			    			combRecommendation = new CombinedRecommendation(acceptableLocFixedBaseLtv2.get(0));
			    			combinedRecommendationsList.add(combRecommendation);
			    		}
			    	}
			    }
			    
			    
			    
			    
			    // Combined Recommendations List Has Now been Populated.  
			    
			    String combinedTableText = "The below table represents the best " + combinedRecommendationsList.size()
			    		+ " qualifying options from the " + totalCombinedProducts
			    		+ " combined mortgage options available through Visdom’s Lender network";
			    AlgoNote combinedTableNote = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.High,combinedTableText,"CombinedTable");
			    marketingTemplateNotes.add(combinedTableNote);
			    
			   
			    // TODO Guy - Question now remains how the Recommended Product is going to Change ... 
			    // Concept is if WFG Deal, Simply go with lowest Payment
			    // But if Non WFG, What then?  Do we still use lowest payment?  (considering there is no loc? 
			    // Additionally a Discussion is needed with Kendall to double check exactly which combined recommendations we want in the Proposal
			    
			    /* TODO Vikash ... Please do the Following 
			    1) Write the list of CombinedRecommendations to Couchbase (if it has items in it)
			    2) Create the Combined Recommendation Table in Proposal that Draws the Combined Recommendations from Couchbase. 
			    3) Test
			    
			    */
			    
	           //  logger.info("combinedRecommendation  Data    is"+combRecommendation);
	             
	             logger.info("***********size  of combinedRecommendationsList ******************* "+combinedRecommendationsList.size());

	             for (CombinedRecommendation combinedRecommendation : combinedRecommendationsList) {
	            	 try{
	            		 
	            		 combRecommendationjson=new CombinedRecommendation();
	            		 //logger.info("combinedRecommendation getBaseLender  Data    is"+combinedRecommendation.getBaseLender());
	            		 combRecommendationjson.setBaseProduct(combinedRecommendation.getBaseProduct());
	            		 combRecommendationjson.setBaseLender(combinedRecommendation.getBaseLender());
	            		 combRecommendationjson.setBaseMortgageType(combinedRecommendation.getBaseMortgageType());
	            		 combRecommendationjson.setBaseTerm(combinedRecommendation.getBaseTerm());
	            		 combRecommendationjson.setBaseAmortization(combinedRecommendation.getBaseAmortization());
	            		 combRecommendationjson.setBaseMortgageAmount(combinedRecommendation.getBaseMortgageAmount());
	            		 combRecommendationjson.setBasePayment(combinedRecommendation.getBasePayment());
	            		 combRecommendationjson.setTotalMortgageAmount(combinedRecommendation.getTotalMortgageAmount());
	            		 combRecommendationjson.setTotalPayment(combinedRecommendation.getTotalPayment());
	            		 
	            		 combRecommendationjson.setBaseInterestRate(combinedRecommendation.getBaseInterestRate());
	            		 combRecommendationjson.setBaseCashbackPercent(combinedRecommendation.getBaseCashbackPercent());
	            		 combRecommendationjson.setBasePosition(combinedRecommendation.getBasePosition());
	            		 combRecommendationjson.setBaseProductID(combinedRecommendation.getBaseProductID());
	            		 combRecommendationjson.setAdditionalLender(combinedRecommendation.getAdditionalLender());
	            		 combRecommendationjson.setAdditionalProduct(combinedRecommendation.getAdditionalProduct());
	            		 combRecommendationjson.setAdditionalMortgageType(combinedRecommendation.getAdditionalMortgageType());
	            		 
	            		 combRecommendationjson.setAdditionalTerm(combinedRecommendation.getAdditionalTerm());
	            		 combRecommendationjson.setAdditionalAmortization(combinedRecommendation.getAdditionalAmortization());
	            		 combRecommendationjson.setAdditionalInterestRate(combinedRecommendation.getAdditionalInterestRate());
	            		 combRecommendationjson.setAdditionalMortgageAmount(combinedRecommendation.getAdditionalMortgageAmount());
	            		 combRecommendationjson.setAdditionalPayment(combinedRecommendation.getAdditionalPayment());
	            		 
	            		 combRecommendationjson.setAdditionalCashbackPercent(combinedRecommendation.getAdditionalCashbackPercent());
	            		 combRecommendationjson.setAdditionalPosition(combinedRecommendation.getAdditionalPosition());
	            		 combRecommendationjson.setAdditionalProductID(combinedRecommendation.getAdditionalProductID());
	            		 
		            	 setCombinedRecommendation.add(combRecommendationjson);

	            	 }catch(NullPointerException e){
	            		 logger.error("error  in getting  combRecommendationjson  Value"+e.getMessage());

	            	 }
	             }
	             try{

	            	 allProductAlgoJSON.setCombinedRecommendation(setCombinedRecommendation);
	             }catch(NullPointerException  e){
	            	 logger.error("Error  in  getting  Combined  Recoomadation"+e.getMessage());
	             }
			    
			    
			    
/*
			    ExecutorService ex = Executors.newFixedThreadPool(4);
			    CouchbaseExecutorService executorservice1=null;
			    try{

			    	executorservice1= new CouchbaseExecutorService(productsJsonList); 
			    	ex.submit(executorservice1);
			    
			    ex.shutdown();
			    }catch (Exception e) {
			    	e.printStackTrace();				
			    }
*/
			    
			    
			  
			    if (ProductRecommendations1.size() > 0){
			    	GenericComparator orderbyInterestRate = Util.newGenericComparator("interestRate","asc");
				    Collections.sort(ProductRecommendations1, orderbyInterestRate);
				    lowestRate = ProductRecommendations1.get(0).interestRate;
				    // Take the lowest one.            
				    // In a loop of Properties, Calculate the interest costs in the remaining term.
				/*    for (Applicant currentApplicant : clientOpportunity.applicants)*/
				    
				    clientOpportunity.applicants.stream().parallel().forEach(
				    		currentApplicant ->
				    {
				    	for (Property currentProperty : currentApplicant.properties) {
				    		// Calculate the current interest only cost ...
				    		int PropertyID = Integer.parseInt(currentProperty.propertyId);
				    		for (Mortgage currentMortgage : currentApplicant.mortgages) {
				    			if (currentMortgage.propertyId != null){
				    				int MortgageID = Integer.parseInt(currentMortgage.propertyId);
				        			if (MortgageID == PropertyID){
				        				// Calculate LTV
				        				double TotalMortgageAmount = 1;
				        				if (currentMortgage.balance != null){
				        					TotalMortgageAmount = Double.parseDouble(currentMortgage.balance);
				        				}
				        				else{
				        					Task task2 = new Task("There is a possible error in Total Mortgage amount for the Property " + currentProperty.name + " Please "
				        							+ "inform Tech Support and send the information in the details of this task.", 
			                                		"Possible null reference error in Opportunity + " + clientOpportunity.name 
					                                		+ "See line 581 of all products Algorithm. ",
			                				"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
			                	            assistantTasks.add(task2); 
				        				}
				        				
				        				double currentPropertyLTV = TotalMortgageAmount / currentProperty.value;            				
				        				
				        				if (currentPropertyLTV < 0.8 && currentProperty.selling == false){
				        					double interestRate = Double.parseDouble(currentMortgage.interestRate);
				                    		double monthlyPayment = Double.parseDouble(currentMortgage.monthlyPayment);
				                    		double RemainingBalance = TotalMortgageAmount;
				                    		// Calculate number of Months remaining in term
				                    		int remainingMonths = 0;
				                    		if (currentMortgage.renewal != null){
				                    			String renewalDateString = currentMortgage.renewal.toString();
				                    			SimpleDateFormat curFormater = new SimpleDateFormat("yyyy-MM-dd");											
												SimpleDateFormat curFormater2 = new SimpleDateFormat("yyyy/MM/dd");
												if (renewalDateString.contains("-")){
													Date renewalDate = null;
													try {
														
															renewalDate = curFormater.parse(renewalDateString);
														
													} catch (Exception e) {
														// TODO Auto-generated catch block
														logger.error("renewalDateString not  Parsed"+e);
													}
													Date currentDate = new Date();
													remainingMonths = CalculatePayments.differenceInMonths(currentDate, renewalDate).intValue();	
												}
												else if (renewalDateString.contains("/")){
													Date renewalDate = null;
													try {
														renewalDate = curFormater2.parse(renewalDateString);
													} catch (Exception e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													}
													Date currentDate = new Date();
													remainingMonths = CalculatePayments.differenceInMonths(currentDate, renewalDate).intValue();
												}
												
											}
											else{	                            				
												remainingMonths = 275;
											} 
				                    		
				                    		// problem is determining remaining amortization ....
				                    		double amortizationBalance = TotalMortgageAmount;
				                    		int amortizationMonths = 0;
				                    		for (int i = 0; i < 400; i++) {
				                    			double InterestOnlyPayment = CalculatePayments.calculateMonthlyLOCPayment(interestRate, amortizationBalance);
				                    			double PrinciplePaid = monthlyPayment - InterestOnlyPayment;
				                    			amortizationBalance = amortizationBalance - PrinciplePaid;
				                    			if (amortizationBalance > 0){
				                    				amortizationMonths = amortizationMonths + 1;
				                    			}
				                    			else{
				                    				break;
				                    			}
				                    		}
				                    		int amortizationYears = (int) Math.round((double)amortizationMonths / 12);
				                    		double newPayment = CalculatePayments.calculateMortgagePayment(lowestRate, TotalMortgageAmount, amortizationYears, FrequencyDesired.Monthly);
				                    		double payoutPenalty = 0;
				                    		double interestInTerm = 0;
				                    		double newInterestInTerm = 0;
				                    		double newRemainingBalance = TotalMortgageAmount;
				                    		for (int i = 0; i < remainingMonths; i++) {
				                    			double InterestOnlyPayment = CalculatePayments.calculateMonthlyLOCPayment(interestRate, RemainingBalance);
				                    			double PrinciplePaid = monthlyPayment - InterestOnlyPayment;
				                    			RemainingBalance = RemainingBalance - PrinciplePaid;
				                    			if (i < 3){
				                    				// Calculate the payout penalty. (Assumes 3 months interest)
				                    				payoutPenalty = payoutPenalty + InterestOnlyPayment;
				                    			}
				                    			interestInTerm = interestInTerm + InterestOnlyPayment;
				                    			double newInterestOnly = CalculatePayments.calculateMonthlyLOCPayment(lowestRate, newRemainingBalance);
				                    			double newPrinciplePaid = newPayment - newInterestOnly;
				                    			newRemainingBalance = newRemainingBalance - newPrinciplePaid;
				                        		newInterestInTerm = newInterestInTerm + newInterestOnly;
				                    			//System.out.println("InterestOnlyPayment = " + InterestOnlyPayment + ", PrinciplePaid = " + PrinciplePaid + ", RemainingBalance = " + RemainingBalance);
				                    		}
				                            
				                            // Compare total to determine whether there is an opportunity
				                            if (newInterestInTerm + payoutPenalty < interestInTerm){
				                            	
				                                Task task1 = new Task(currentApplicant.applicantName + " has an opportunity to save money by refinancing their property at " + currentProperty.name + ". See Task Details", 
				                                		currentApplicant.applicantName + " has an opportunity to save money by refinancing their property at " + currentProperty.name + ". "
				                                		+ "Over the remaining " + remainingMonths + "months in the current term of their mortgage, they will be paying "
				                                		+ "$" + interestInTerm + " in interest costs.  If their current payout penalty is 3 months of interest, "
				                                		+ "a new mortgage would only have " + Math.round(newInterestInTerm) + " of interest costs.  "
				                                		+ "Please have " + currentApplicant.applicantName + " confirm the payout penalty on their current mortgage.",
				                				"Broker", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
				                	            brokerTasks.add(task1); 
				                                
				                                Task task2 = new Task("Please disuss with the Broker whether this Opportunty should be cloned and a new Opportunity Created.  See task Details.", 
				                                		"Please clone this opportunity for " + currentApplicant.applicantName + ".  "
						                                		+ "The cloned Opportunity should be a refinace Opportunity for the " + currentProperty.name + " proeprty. "
						                                		+ "Please use the information in this property's tax statments to confirm value and work with the Broker "
						                                		+ "to determine other opportunity details.",
				                				"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
				                	            assistantTasks.add(task2); 
				                            }  
				        				}                    	
				        			}
				    			}
							} 
						}	
				/*	}*/
				    
				    
			    });
			    }
		    	Set<com.syml.bean.algo.Recommendation>  setRecommendation=new HashSet<com.syml.bean.algo.Recommendation>();

		    	
		    

		    	
			    // Add Products from  ProductRecommendations1 into list of Recommendations.
			   /* for (UnderwriteAll underwrite : ProductRecommendations1)*/ 
		    	ProductRecommendations1.stream().parallel().forEach(
		    			underwrite ->{
			    	if(underwrite.expectedMortgageAmount < 20000)
			    		logger.info("Challenge with Mortgage Amount");
			    	
			    	Recommendation recommend = new Recommendation(underwrite);
			    	Recommendations.add(recommend);
			    	//logger.info(recommend.toString());
			    	//logger.info("---------------Recoomaned  TERM---------------------"+recommend.term);
			    	//logger.info("------------------Recommend  ProductName------------------"+recommend.productName);
			    	
			    	com.syml.bean.algo.Recommendation recommendJSON = new com.syml.bean.algo.Recommendation();			    	
			    	recommendJSON.setCashbackPercent(Double.toString(recommend.cashback));
			    	recommendJSON.setInterestRate(Double.toString(recommend.rate));
			    	recommendJSON.setLender(recommend.lenderName);
			    	recommendJSON.setMaximumAmortization(Double.toString(recommend.amortization));
			    	recommendJSON.setMortgageAmount(Double.toString(recommend.mortaggeAmout));
			    	recommendJSON.setMortgageType(recommend.mortgageType);
			    	recommendJSON.setPayment(Double.toString(recommend.payment));
			    	recommendJSON.setPosition(recommend.position);
			    	recommendJSON.setProduct(recommend.productName);
			    	recommendJSON.setProductID(Integer.toString(recommend.productID));
			    	recommendJSON.setTerm(recommend.term);
			    	
			    	try{
			    	
			    	setRecommendation.add(recommendJSON);
			    	allProductAlgoJSON.setRecommendations(setRecommendation);
			    	}catch(Exception  e){
			    		
			    	}
			    });

		    	//For WFG Deals choose the lowest payment Possibilities from each list base on all instances < Lowest Payment + 2%
		    	List<UnderwriteCombined> minPayLocVarBaseLTV = new ArrayList<UnderwriteCombined>(); 	
		    	List<UnderwriteCombined> minPayLocFixedBaseLTV = new ArrayList<UnderwriteCombined>(); 
		    	List<UnderwriteCombined> minPayVarFixedBaseLTV = new ArrayList<UnderwriteCombined>();
		    	List<UnderwriteCombined> minPayLocVarBaseLtv1 = new ArrayList<UnderwriteCombined>(); 	
		    	List<UnderwriteCombined> minPayLocFixedBaseLtv1 = new ArrayList<UnderwriteCombined>(); 
		    	List<UnderwriteCombined> minPayVarFixedBaseLtv1 = new ArrayList<UnderwriteCombined>();
		    	List<UnderwriteCombined> minPayLocVarBaseLtv2 = new ArrayList<UnderwriteCombined>(); 	
		    	List<UnderwriteCombined> minPayLocFixedBaseLtv2 = new ArrayList<UnderwriteCombined>(); 
		    	List<UnderwriteCombined> minPayVarFixedBaseLtv2 = new ArrayList<UnderwriteCombined>();
		    	double  dLowestLocVarBasePayLTV=0.0;
		    	double  dLowestLocFixedBasePayLTV=0.0;
		    	if (clientOpportunity.baseLtv != null && clientOpportunity.baseLtv > 0){
		    		OptionalDouble lowestLocVarBasePayLTV = acceptableLocVarBaseLTV.parallelStream().mapToDouble(uwComb -> uwComb.expectedFitnessPayment).min();
		    		try{
		    			dLowestLocVarBasePayLTV = lowestLocVarBasePayLTV.getAsDouble() * 1.02;		    	
		    		}catch(Exception  e){
		    			logger.error("lowestLocVarBasePayLTV  Eror"+e.getMessage());
		    		}
		    		for (UnderwriteCombined underwriteCombined : acceptableLocVarBaseLTV) {
		    			if (underwriteCombined.expectedFitnessPayment <= dLowestLocVarBasePayLTV)
		    				minPayLocVarBaseLTV.add(underwriteCombined); 
		    		}

		    		OptionalDouble lowestLocFixedBasePayLTV = acceptableLocFixedBaseLTV.parallelStream().mapToDouble(uwComb -> uwComb.expectedFitnessPayment).min();

		    		try{
		    			dLowestLocFixedBasePayLTV = lowestLocFixedBasePayLTV.getAsDouble() * 1.02;		    	
		    		}catch(Exception e){
		    			logger.error("lowestLocFixedBasePayLTV  Eror"+e.getMessage());

		    		}
		    		for (UnderwriteCombined underwriteCombined : acceptableLocFixedBaseLTV) {
		    			if (underwriteCombined.expectedFitnessPayment <= dLowestLocFixedBasePayLTV)
		    				minPayLocFixedBaseLTV.add(underwriteCombined); 
		    		}

		    		OptionalDouble lowestVarFixedBasePayLTV = acceptableVarFixedBaseLTV.parallelStream().mapToDouble(uwComb -> uwComb.expectedFitnessPayment).min();
		    		double dLowestVarFixedBasePayLTV=0.0;
		    		try{
		    			dLowestVarFixedBasePayLTV = lowestVarFixedBasePayLTV.getAsDouble() * 1.02;		    	
		    		}catch(Exception e){
		    			logger.error("lowestLocFixedBasePayLTV  Eror"+e.getMessage());


		    		}
		    		for (UnderwriteCombined underwriteCombined : acceptableVarFixedBaseLTV) {
		    			if (underwriteCombined.expectedFitnessPayment <= dLowestVarFixedBasePayLTV)
		    				minPayVarFixedBaseLTV.add(underwriteCombined); 
		    		}


		    		// Sort Lists By Fitness.
		    		minPayLocVarBaseLTV.parallelStream().sorted(byAscFitness.reversed());
		    		minPayLocFixedBaseLTV.parallelStream().sorted(byAscFitness.reversed());
		    		minPayVarFixedBaseLTV.parallelStream().sorted(byAscFitness.reversed());			    	
		    	}
		    	else{

		    		OptionalDouble lowestLocVarBasePayLtv1 = acceptableLocVarBaseLtv1.parallelStream().mapToDouble(uwComb -> uwComb.expectedFitnessPayment).min();
		    		double  dLowestLocVarBasePayLtv1 =0.0;
		    		try{
		    			dLowestLocVarBasePayLtv1 = lowestLocVarBasePayLtv1.getAsDouble() * 1.02;		    	
		    		}catch(Exception e){
		    			logger.error("lowestLocFixedBasePayLTV  Eror"+e.getMessage());


		    		}
		    		for (UnderwriteCombined underwriteCombined : acceptableLocVarBaseLtv1) {
		    			if (underwriteCombined.expectedFitnessPayment <= dLowestLocVarBasePayLtv1)
		    				minPayLocVarBaseLtv1.add(underwriteCombined); 
		    		}

		    		OptionalDouble lowestLocFixedBasePayLtv1 = acceptableLocFixedBaseLtv1.parallelStream().mapToDouble(uwComb -> uwComb.expectedFitnessPayment).min();
		    		double  dLowestLocFixedBasePayLtv1=0.0;
		    		try{
		    			dLowestLocFixedBasePayLtv1 = lowestLocFixedBasePayLtv1.getAsDouble() * 1.02;		    	
		    		}catch(Exception e){
		    			logger.error("lowestLocFixedBasePayLTV  Eror"+e.getMessage());

		    		}
		    		for (UnderwriteCombined underwriteCombined : acceptableLocFixedBaseLtv1) {
		    			if (underwriteCombined.expectedFitnessPayment <= dLowestLocFixedBasePayLtv1)
		    				minPayLocFixedBaseLtv1.add(underwriteCombined); 
		    		}

		    		OptionalDouble lowestVarFixedBasePayLtv1 = acceptableVarFixedBaseLtv1.parallelStream().mapToDouble(uwComb -> uwComb.expectedFitnessPayment).min();
		    		double  dLowestVarFixedBasePayLtv1=0.0;
		    		try{
		    			dLowestVarFixedBasePayLtv1 = lowestVarFixedBasePayLtv1.getAsDouble() * 1.02;		    	
		    		}catch(Exception  e){
		    			logger.error("lowestLocFixedBasePayLTV  Eror"+e.getMessage());

		    		}
		    		for (UnderwriteCombined underwriteCombined : acceptableVarFixedBaseLtv1) {
		    			if (underwriteCombined.expectedFitnessPayment <= dLowestVarFixedBasePayLtv1)
		    				minPayVarFixedBaseLtv1.add(underwriteCombined); 
		    		}

		    		OptionalDouble lowestLocVarBasePayLtv2 = acceptableLocVarBaseLtv2.parallelStream().mapToDouble(uwComb -> uwComb.expectedFitnessPayment).min();
		    		double  dLowestLocVarBasePayLtv2=0.0;
		    		try{
		    			dLowestLocVarBasePayLtv2 = lowestLocVarBasePayLtv2.getAsDouble() * 1.02;		    	
		    		}catch(Exception e){
		    			logger.error("lowestLocFixedBasePayLTV  Eror"+e.getMessage());


		    		}
		    		for (UnderwriteCombined underwriteCombined : acceptableLocVarBaseLtv2) {
		    			if (underwriteCombined.expectedFitnessPayment <= dLowestLocVarBasePayLtv2)
		    				minPayLocVarBaseLtv2.add(underwriteCombined); 
		    		}

		    		OptionalDouble lowestLocFixedBasePayLtv2 = acceptableLocFixedBaseLtv2.parallelStream().mapToDouble(uwComb -> uwComb.expectedFitnessPayment).min();
		    		double  dLowestLocFixedBasePayLtv2=0.0;
		    		try{
		    			dLowestLocFixedBasePayLtv2 = lowestLocFixedBasePayLtv2.getAsDouble() * 1.02;		    	
		    		}catch(Exception  e){
		    			logger.error("lowestLocFixedBasePayLTV  Eror"+e.getMessage());


		    		}
		    		for (UnderwriteCombined underwriteCombined : acceptableLocFixedBaseLtv2) {
		    			if (underwriteCombined.expectedFitnessPayment <= dLowestLocFixedBasePayLtv2)
		    				minPayLocFixedBaseLtv2.add(underwriteCombined); 
		    		}

		    		OptionalDouble lowestVarFixedBasePayLtv2 = acceptableVarFixedBaseLtv2.parallelStream().mapToDouble(uwComb -> uwComb.expectedFitnessPayment).min();
		    		double  dLowestVarFixedBasePayLtv2=0.0;
		    		try{
		    			dLowestVarFixedBasePayLtv2 = lowestVarFixedBasePayLtv2.getAsDouble() * 1.02;		    	
		    		}catch(Exception e){

		    			logger.error("lowestLocFixedBasePayLTV  Eror"+e.getMessage());

		    		}
		    		for (UnderwriteCombined underwriteCombined : acceptableVarFixedBaseLtv2) {
		    			if (underwriteCombined.expectedFitnessPayment <= dLowestVarFixedBasePayLtv2)
		    				minPayVarFixedBaseLtv2.add(underwriteCombined); 
		    		}

		    		// Sort Lists By Fitness.
//		    		Comparator<UnderwriteCombined> descendingFitness = (UnderwriteCombined combinedUW1, UnderwriteCombined combinedUW2) -> 
//				    Double.compare(combinedUW2.fitness, combinedUW1.fitness);
		    		Comparator<UnderwriteCombined> reversedFitness = byAscFitness.reversed();
		    		
		    		minPayLocVarBaseLtv1.sort(reversedFitness);
		    		minPayLocFixedBaseLtv1.sort(reversedFitness);
		    		minPayVarFixedBaseLtv1.sort(reversedFitness);
		    		minPayLocVarBaseLtv2.sort(reversedFitness);
		    		minPayLocFixedBaseLtv2.sort(reversedFitness);
		    		minPayVarFixedBaseLtv2.sort(reversedFitness);
		    	}
		    	
		    	// Comment out this section or delete when sort is proven. 
		    	//logger.info("minPayLocVarBaseLtv2 Values: ");
		    	/*for (UnderwriteCombined underwriteCombined : minPayLocVarBaseLtv2) {
		    		logger.info("Payment: "  + underwriteCombined.expectedFitnessPayment + " Fitness: " + underwriteCombined.fitness);	
				}
		    	
		    	logger.info("minPayLocFixedBaseLtv2 Values: ");
		    	for (UnderwriteCombined underwriteCombined : minPayLocFixedBaseLtv2) {
		    		logger.info("Payment: "  + underwriteCombined.expectedFitnessPayment + " Fitness: " + underwriteCombined.fitness);	
				}
		    	
		    	logger.info("minPayVarFixedBaseLtv2 Values: ");
		    	for (UnderwriteCombined underwriteCombined : minPayVarFixedBaseLtv2) {
		    		//logger.info("Payment: "  + underwriteCombined.expectedFitnessPayment + " Fitness: " + underwriteCombined.fitness);	
				}*/
		    //	logger.info("End Sorting Test: ");
		    	// Now Recommendations Can be Set because Lowest Payment group has been chosen and Ranked by fitness.
		    	// Next Steps ... 
		    	// 1) Create the Combined Recommendations Table
		    	// 2) Compare all for highest Fitness (including against variable only list, fixed only list and LOC only list.
		    	// Highest Fitness of them all is selected as recommendation.


		    	//Create CombinedRecommendations
		    	// ArrayList<CombinedRecommendation> combinedRecommendationsList = new ArrayList<>();

		    	//CombinedRecommendation combRecommendation=null;
		    	//CombinedRecommendation combRecommendationjson=null;

		    	Set<com.syml.bean.algo.CombinedRecommendation>  setCombinedRecommendation1=new HashSet<com.syml.bean.algo.CombinedRecommendation>();

		    	try{
		    		logger.info("clientOpportunity.wfgDeal"+clientOpportunity.wfgDeal);
		    	}catch(NullPointerException  e){
		    		logger.error("wfgDeal  is  null"+e.getMessage());

		    	}
			    
			    
			    if (clientOpportunity.baseLtv != null && clientOpportunity.baseLtv > 0){
		    		
			    	logger.info("clientOpportunity.baseLtv  is  not  Null");		    		// Use Lowest Payment Combined Products LTV
//		    		
		    		if (minPayLocVarBaseLTV.size() > 0){
		    			 combRecommendation = new CombinedRecommendation(minPayLocVarBaseLTV.get(0));
		    			combinedRecommendationsList.add(combRecommendation);
		    		}
		    		if (minPayLocFixedBaseLTV.size() > 0){
		    			combRecommendation = new CombinedRecommendation(minPayLocFixedBaseLTV.get(0));
		    			combinedRecommendationsList.add(combRecommendation);
		    		}
		    		
		    		if (minPayVarFixedBaseLTV.size() > 0){
		    			 combRecommendation = new CombinedRecommendation(minPayVarFixedBaseLTV.get(0));
		    			combinedRecommendationsList.add(combRecommendation);
		    		}
				}
		    	else{
		    		
		    		logger.info("******************clientOpportunity.baseLtv  Else  Case*********");
		    		// Use Lowest Payment Combined Products LTv1
		    		if (minPayLocVarBaseLtv1.size() > 0){
		    			 combRecommendation = new CombinedRecommendation(minPayLocVarBaseLtv1.get(0));
		    			combinedRecommendationsList.add(combRecommendation);
		    		}
		    		if (minPayLocFixedBaseLtv1.size() > 0){
		    			combRecommendation = new CombinedRecommendation(minPayLocFixedBaseLtv1.get(0));
		    			combinedRecommendationsList.add(combRecommendation);
		    		}
		    		
		    		if (minPayVarFixedBaseLtv1.size() > 0){
		    			 combRecommendation = new CombinedRecommendation(minPayVarFixedBaseLtv1.get(0));
		    			combinedRecommendationsList.add(combRecommendation);
		    		}
		    		
		    		if (minPayLocVarBaseLtv2.size() > 0){
		    			 combRecommendation = new CombinedRecommendation(minPayLocVarBaseLtv2.get(0));
		    			combinedRecommendationsList.add(combRecommendation);
		    		}
		    		if (minPayLocFixedBaseLtv2.size() > 0){
		    			combRecommendation = new CombinedRecommendation(minPayLocFixedBaseLtv2.get(0));
		    			combinedRecommendationsList.add(combRecommendation);
		    		}
		    		
		    		if (minPayVarFixedBaseLtv2.size() > 0){
		    			 combRecommendation = new CombinedRecommendation(minPayVarFixedBaseLtv2.get(0));
		    			combinedRecommendationsList.add(combRecommendation);
		    		}
		    	}
			    logger.info("Combined Recommendations List Has Now been Populated.");
			    
			  
			    
			    // Write the list of CombinedRecommendations to Couchbase (if it has items in it)
			    logger.info("Getting  Data  From  Combined  Recoomadation  List");
			    //  logger.info("combinedRecommendation  Data    is"+combRecommendation);

			    logger.info("--------size  of combinedRecommendationsList--------------"+combinedRecommendationsList.size());

			    for (CombinedRecommendation combinedRecommendation : combinedRecommendationsList) {
			    	//logger.info("---------------------------------Inside CombinedRecommendation  For  Loop----------- "+combinedRecommendation);
			    	try{
			    		combRecommendationjson.setBaseProduct(combinedRecommendation.getBaseProduct());
			    		combRecommendationjson.setBaseLender(combinedRecommendation.getBaseLender());
			    		combRecommendationjson.setBaseMortgageType(combinedRecommendation.getBaseMortgageType());
			    		combRecommendationjson.setBaseTerm(combinedRecommendation.getBaseTerm());
			    		combRecommendationjson.setBaseAmortization(combinedRecommendation.getBaseAmortization());
			    		combRecommendationjson.setBaseMortgageAmount(combinedRecommendation.getBaseMortgageAmount());
			    		combRecommendationjson.setBasePayment(combinedRecommendation.getBasePayment());
			    		combRecommendationjson.setTotalMortgageAmount(combinedRecommendation.getTotalMortgageAmount());
			    		combRecommendationjson.setTotalPayment(combinedRecommendation.getTotalPayment());
			    	}catch(NullPointerException e){
			    		logger.error("error  in getting  combRecommendationjson  Value"+e.getMessage());

			    	}
			    }
			    try{

			    	setCombinedRecommendation.add(combRecommendationjson);
			    	//allProductAlgoJSON.setCombinedRecommendation(setCombinedRecommendation);
			    }catch(NullPointerException  e){
			    	logger.error("Error  in  getting  Combined  Recoomadation"+e.getMessage());
			    }
			    /*
			    ExecutorService ex1 = Executors.newFixedThreadPool(4);
			    CouchbaseExecutorService executorservice12=null;
			    try{

			    	executorservice12= new CouchbaseExecutorService(productsJsonList); 
			    	ex1.submit(executorservice12);
			    
			    ex1.shutdown();
			    }catch (Exception e) {
			    	logger.error("Coucbase  store  failed"+e.getMessage());			    
			    }*/
			   
			    
			    // Should be able to just take the Best 5 Year from the 5 Year list .... 
			    Recommendation Recommend5YearFixed = null;
			    if (acceptableFixed5YrProducts.size() > 0){
			    	try{
			    	Recommend5YearFixed = new Recommendation(acceptableFixed5YrProducts.get(0));
			    	}catch(Exception e){
			    		logger.error("acceptableFixed5YrProducts  not  found"+e);
			    		
			    	}
			    	}
			    else{
			    	try{
			    	Recommend5YearFixed = new Recommendation(failedFixed5YrProducts.get(0));
			    	}catch(Exception e){
			    		logger.error("failedFixed5YrProducts  Not  found"+e);
			    	}
			    	}
			    
			    
			    // Calculate Original Desired Recommendation ... 
			    //desiredType = mortgageTypeString(clientOpportunity.desiredMortgageType);
			    
			    
			    
			    if (desiredType.contains("LOC")){
			    	desiredTerm = "None";
			    }
			    else
			    	//desiredTerm = mortgageTermString(clientOpportunity.desiredTerm);
			    	 if (clientOpportunity.desiredTerm.contains("1"))
			            	desiredTerm = "My Best Option";
			            else if (clientOpportunity.desiredTerm.contains("2"))
			            	desiredTerm = "6 Months";
			            else if (clientOpportunity.desiredTerm.contains("3"))
			            	desiredTerm = "1 Year";
			            else if (clientOpportunity.desiredTerm.contains("4"))
			            	desiredTerm = "2 Year";
			            else if (clientOpportunity.desiredTerm.contains("5"))
			            	desiredTerm = "3 Year";
			            else if (clientOpportunity.desiredTerm.contains("6"))
			            	desiredTerm = "4 Year";
			            else if (clientOpportunity.desiredTerm.contains("7"))
			            	desiredTerm = "5 Year";
			            else if (clientOpportunity.desiredTerm.contains("8"))
			            	desiredTerm = "7 Year";
			            else if (clientOpportunity.desiredTerm.contains("9"))
			            	desiredTerm = "10 Year";
			            else
			            	desiredTerm = "5 Year";
			    
			   /* for (Recommendation rec : Recommendations)*/
//			    Recommendations.stream().parallel().forEach(
//			    		rec ->
			    for (Recommendation rec : Recommendations)
			    { 
			    	String typeOfMorgageRecommendation = null;
			    	String termOfMorgageRecommendation = null;
			    	

			    
			    	try{
			    		typeOfMorgageRecommendation = mortgageTypeString(rec.mortgageType);
			    		termOfMorgageRecommendation = mortgageTermString(rec.term);  
			    		
			    		logger.info("------------------------termOfMorgageRecommendation--------------------"+termOfMorgageRecommendation);
			    		logger.info("------------------------DesiredType--------------------"+desiredType);

			    		if (typeOfMorgageRecommendation.contains(desiredType)){	
			    			if (desiredType.contains("LOC")){
	                        	 logger.info("inside  Desired  type  LOC");

			    				originalDesiredProduct = rec;
			    			}
                         else if(desiredType.contains("Variable")){
                        	 logger.info("inside  Desired  type  Variable");
			    				originalDesiredProduct = rec;

			    			}else if(desiredType.contains("Fixed")){
			    				originalDesiredProduct = rec;

			    			}
			    			
			    			else if (termOfMorgageRecommendation.contains(desiredTerm)){
				    				originalDesiredProduct = rec;	
				    					//break;
				    			
			    			}
			    		}	
			    	}catch (Exception e) {
						logger.error("Variable  Not  added"+e.getMessage());
					}
				/*}*/
			    
			}
			    //);
			    
			    // If there are only 5 year fixed products, add a couple more 5 years in.
			    if (Recommendations.size() == 1){
			    	int total5YearProducts = acceptableFixed5YrProducts.size();
			    	int min5YearProducts = Math.min(total5YearProducts, 5);
			    	
			    	for (int extraProducts = 1; extraProducts < min5YearProducts; extraProducts++){			    			
		    			Recommendation newRecommend = new Recommendation(acceptableFixed5YrProducts.get(extraProducts));	
		    			Recommendations.add(newRecommend);
		    		}
			    }
			    
			    // Calculate the recommended Product
			    // Recommended Product         
			    // Client's Cost - Risk Profile            
			    // Presently, risk is determined by answers to the 7 suitability Questions and set in the AlgoOpportunity Constructor ... 
			    // A client with a high riskBais (35) would be extremely risk adverse and need a fixed mortgage
			    // A client with a low roskBias (7) would be risk accepting and need whatever is cheapest.            
			    // Idea ... Don't profile Products ... 
			    // Create a risk "tipping point" where it must be fixed.
			    // If risk is under tipping point, cheapest (probably variable), if over, fixed.
			    // Then look at the risk only based on the largest possible risk score ... Use that to determine optimal term.
			    // Start by determining where between 7 and 35 they land (consider the number of suitabiity questions they answered ... If <= 4, go with cost savings only
			    // TODO need to review the recommendation Selection Method with Kendall ... 
			    int clientRiskBias = 0;
			    if (clientOpportunity1.answeredSuitabilityQuestions >= 4){
			    	double maxRiskScore = (double)clientOpportunity1.answeredSuitabilityQuestions * 5;
			    	double riskPercent = clientOpportunity1.riskBias / maxRiskScore * 100;
			    	if (riskPercent >= 70){
			    		clientRiskBias = 3;
			    	}
			    	else if (riskPercent >= 50 && riskPercent < 70){
			    		clientRiskBias = 2;
			    	} 
			    	else{
			    		clientRiskBias = 1;
			    	}            	
			    }
			    else{
			    	clientRiskBias = 0;
			    }
			  
			    if (clientRiskBias == 0 || clientRiskBias == 1){
			    
			    	/*for (UnderwriteAll rec : ProductRecommendations1)*/
//			    	 ProductRecommendations1.stream().parallel().forEach(
//			    			 singleRec ->{
			    	for (UnderwriteAll singleRec : ProductRecommendations1){
			    		
			    		// Need to Account for WFG Deal. 
			    		if (singleRec.interestRate < lowRate){
			    			currentRecommend = singleRec;
			    			lowRate = singleRec.interestRate;
			    		}
			    		else if (singleRec.interestRate == lowRate){
			    			if (currentRecommend != null){
			    				if (singleRec.fitness > currentRecommend.fitness){
			    					currentRecommend = singleRec;
			    					lowRate = singleRec.interestRate;
			    				}
			    			}
			    			else{
			    				currentRecommend = singleRec;
			    				lowRate = singleRec.interestRate;
			    			}
			    		}						

			    	}
			    	
			    }
			    else if (clientRiskBias == 2){
			    	// Goal here is to balance cost and risk ... 
			    	// How????  The client wants the lowest cost at an acceptable risk ... Make term >= 3 years, then lowest
			    	
			    	/*for (UnderwriteAll rec : ProductRecommendations1)*/
			    	
//			    	ProductRecommendations1.stream().parallel().forEach(
//			    			rec ->
			    			
			    	for (UnderwriteAll rec : ProductRecommendations1)	{
			    		if (rec.potentialProduct.mortgageType.contains("2") || rec.potentialProduct.mortgageType.contains("3")){
			    			double termLength = 0;
			    			if (rec.potentialProduct.term.contains("2"))
			    				termLength = 1;
			    			else if (rec.potentialProduct.term.contains("3"))
			    				termLength = 2;
			    			else if (rec.potentialProduct.term.contains("4"))
			    				termLength = 3;
			    			else if (rec.potentialProduct.term.contains("5"))
			    				termLength = 4;
			    			else if (rec.potentialProduct.term.contains("6"))
			    				termLength = 5;
			    			else if (rec.potentialProduct.term.contains("7"))
			    				termLength = 6;
			    			else if (rec.potentialProduct.term.contains("8"))
			    				termLength = 7;
			    			else if (rec.potentialProduct.term.contains("9"))
			    				termLength = 8;
			    			else if (rec.potentialProduct.term.contains("10"))
			    				termLength = 9;
			    			else if (rec.potentialProduct.term.contains("11"))
			    				termLength = 10;
			    			else if (rec.potentialProduct.term.contains("1"))
			    				termLength = 0.5;
			    			else 
			    				termLength = 100;

			    			if (termLength >= 3){
			    				if (rec.potentialProduct.interestRate < lowRate){
			    					currentRecommend = rec;
			    					lowRate = rec.potentialProduct.interestRate;
			    				}
			    				else if (rec.potentialProduct.interestRate == lowRate){
			    					if (currentRecommend != null){
			    						if(rec.totalCompAmount > currentRecommend.totalCompAmount){
			    							currentRecommend = rec;
			    							lowRate = rec.potentialProduct.interestRate;
			    						}
			    						else{
			    							if (rec.fitness > currentRecommend.fitness){
			    								currentRecommend = rec;
			    								lowRate = rec.potentialProduct.interestRate;
			    							}
			    						}
			    					}
			    					else{
			    						currentRecommend = rec;
			    						lowRate = rec.potentialProduct.interestRate;
			    					}
			    				}	
			    			}
			    		}
			    		/*	}*/


			    	}
			    }
			    else if (clientRiskBias == 3){
			    	// Provide 5 Year fixed
			    	double lowRate = 100;
			    	for (UnderwriteAll rec : ProductRecommendations1) {
						if (rec.potentialProduct.term.contains("6")){
							if (rec.potentialProduct.interestRate < lowRate){
								currentRecommend = rec;
								lowRate = rec.potentialProduct.interestRate;
							}
							else if (rec.potentialProduct.interestRate == lowRate){
								if (currentRecommend != null){
									if (rec.fitness > currentRecommend.fitness){
										currentRecommend = rec;
										lowRate = rec.potentialProduct.interestRate;
									}
								}
								else{
									currentRecommend = rec;
									lowRate = rec.potentialProduct.interestRate;
								}
							}	
						}					
					}
			    }
			    
		    	logger.info("----------------originalDesiredProduct-------------"+originalDesiredProduct);
			    // Proposal Goals Text:
			    StringBuilder goalsString = new StringBuilder();
			    if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Approval, clientOpportunity.whatIsYourLendingGoal)){
			    	goalsString.append("Congratulations on your goal to own the property at ");
			        goalsString.append(clientOpportunity.address + "," + clientOpportunity.city + ", " + clientOpportunity.province + ". ");
			        goalsString.append("In your application, you indicated you wanted to purchase this property valued at $" + Math.round(clientOpportunity.propertyValue));
			        goalsString.append(" with a down payment of $" + Math.round(clientOpportunity.downpaymentAmount) + " and financing of $" + Math.round(clientOpportunity.desiredMortgageAmount) + ". ");
			    }
			    else if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Refinance, clientOpportunity.whatIsYourLendingGoal)){
			    	goalsString.append("Congratulations on your goal to refinance the property at ");
			        goalsString.append(clientOpportunity.address + "," + clientOpportunity.city + ", " + clientOpportunity.province + ". ");
			        // Put in text for additional money
			        if (Recommend5YearFixed != null){
			        	if (Recommend5YearFixed.mortaggeAmout > clientOpportunity.currentMortgageAmount){
			        		double extraCashAmount =  Recommend5YearFixed.mortaggeAmout - clientOpportunity.currentMortgageAmount - Recommend5YearFixed.insuranceAmount;
			        		
			        		goalsString.append("In your application, you indicated you wanted to refinance (or renew financing) this property "
					        		+ "and free up additional funds for other goals."
					        		+ "The following proposal frees up approximately $" + Math.round(extraCashAmount) 
					        		+ " and outlines the best options for achieving your goal.");
			        		// if expectedMortgage Amount > current Mortgage Amount ... Add text
			        	}
			        	else{
			        		goalsString.append("In your application, you indicated you wanted to refinance (or renew financing) this property."
					        		+ "The following proposal outlines the best options for achieving your goal.");
			        	}
			        }
			        else{
			        	goalsString.append("In your application, you indicated you wanted to refinance (or renew financing) this property."
				        		+ "The following proposal outlines the best options for achieving your goal.");
			        }
			    }
			    else if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Prequalify, clientOpportunity.whatIsYourLendingGoal)){
			    	goalsString.append("Congratulations on your goal to own the property" +
			    			" in " + province + ".  In your application, you indicated you wanted to purchase a property valued at $" + Math.round(clientOpportunity.propertyValue));
			        goalsString.append(" with a down payment of $" + Math.round(clientOpportunity.downpaymentAmount) + " and financing of $" + Math.round(clientOpportunity.desiredMortgageAmount) + ". ");
				}
			    String finishedGoalsString = goalsString.toString();
			    AlgoNote noteGoal = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.High,finishedGoalsString,"Goals");
			    marketingTemplateNotes.add(noteGoal);
			    
			    // Original Application: 
			    // On your original mortgage application, you requested a 5 Year Fixed Term Mortgage.  The below details represent the best option in the category of mortgage which initially interested you
			    
			    for (Recommendation rec : Recommendations){
			    	try{
			    	if (rec.mortgageType.contains("2")){
						countVariableRecommendations = countVariableRecommendations + 1;
					}
					else if (rec.mortgageType.contains("3")){
						countFixedRecommendations = countFixedRecommendations + 1;
					}
					else if (rec.mortgageType.contains("1")){
						countLOCRecommendations = countLOCRecommendations + 1;
					}
					else if (rec.mortgageType.contains("4")){
						countCashbackRecommendations = countCashbackRecommendations + 1;
					}
					else if (rec.mortgageType.contains("5")){
						countCombinedRecommendations = countCombinedRecommendations + 1;
					}
			    	}catch(NullPointerException e){
			    		logger.error("mortgageType  Null"+e);
			    		
			    	}
			    	
			    }
			    
			    StringBuilder originalAp = new StringBuilder();
			    StringBuilder originalDetails = new StringBuilder();
			    
			    JSONObject originalDetailsJson=new JSONObject();
			    JSONObject ourRecommendationJson=new JSONObject();

		    	logger.info("----------------originalDesiredProduct-------------"+originalDesiredProduct);

			    
			    if ((SelectionHelper.compareSelection(AlgoOpportunity.DesiredMortgageType.Recommend, clientOpportunity.desiredMortgageType))== false
			    		&& (SelectionHelper.compareSelection(AlgoOpportunity.DesiredProductTerm.None, clientOpportunity.desiredTerm))== false
			    		&& clientOpportunity.desiredTerm.contains("1") == false
			    		&& originalDesiredProduct != null){
			    	originalAp.append("On your original mortgage application, you requested a ");
			    	originalAp.append(desiredType + " mortgage with a " + desiredTerm + " term.");
			    	originalAp.append("  The below details represent the best option in the category of mortgage which initially interested you.");
			    	
			    	// TODO In pre-approval, these might be blank ... I should fill them in with below maximum amounts.  
			    	
			    	double mortgageAmountWithNoIns = originalDesiredProduct.mortaggeAmout - originalDesiredProduct.insuranceAmount;
			    	double equityOrDownpayment = clientOpportunity.propertyValue - mortgageAmountWithNoIns;
			    	logger.info("----------------originalDesiredProduct-------------"+originalDesiredProduct);

			    	if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Prequalify, clientOpportunity.whatIsYourLendingGoal)){
				    	equityOrDownpayment = clientOpportunity.downpaymentAmount;	
					}
					else{			
				    	equityOrDownpayment = clientOpportunity.propertyValue - mortgageAmountWithNoIns;	
					}
			    	logger.info("----------------originalDesiredProduct-------------"+originalDesiredProduct);
			    	if (originalDesiredProduct != null){			    	
			    		
			    		logger.info("---------------inside -originalDesiredProduct-------------"+originalDesiredProduct);

						if (clientOpportunity.desiredMortgageType.contains("0"))
			            	desiredType = "LOC";
						else if (clientOpportunity.desiredMortgageType.contains("1"))
			            	desiredType = "Fixed";
			            else if (clientOpportunity.desiredMortgageType.contains("2"))
			            	desiredType = "Variable";
			            else if (clientOpportunity.desiredMortgageType.contains("3"))
			            	desiredType = "Cashback";
			            else if (clientOpportunity.desiredMortgageType.contains("4"))
			            	desiredType = "Recommended";
			            else if (clientOpportunity.desiredMortgageType.contains("5"))
			            	desiredType = "Combined";	
						
						
			    		
					    originalDetailsJson.put("propertyValue", String.format("%1$,.2f", clientOpportunity.propertyValue));
					    originalDetailsJson.put("downpaymentEquity", String.format("%1$,.2f", equityOrDownpayment));
					    originalDetailsJson.put("mortgageAmount", String.format("%1$,.2f", mortgageAmountWithNoIns));
					    originalDetailsJson.put("insuranceAmount", String.format("%1$,.2f", originalDesiredProduct.insuranceAmount));
					    originalDetailsJson.put("totalMortgage", String.format("%1$,.2f", originalDesiredProduct.mortaggeAmout));
					    originalDetailsJson.put("amortization", String.format("%1$,.0f", originalDesiredProduct.amortization));
					    originalDetailsJson.put("mortgageType",desiredType);
					    originalDetailsJson.put("mortgageTerm", desiredTerm);
					    originalDetailsJson.put("paymentAmount", String.format("%1$,.2f", originalDesiredProduct.payment));
					    originalDetailsJson.put("interestRate", String.format("%1$,.2f", originalDesiredProduct.rate));

					    originalDetailsJson.put("lender", originalDesiredProduct.lenderName);
					    originalDetailsJson.put("productID", originalDesiredProduct.productID);
					    originalDetailsJson.put("ProductName", originalDesiredProduct.productName);
					  

			    	
			        	
			        	desiredProduct.setCashbackPercent(Double.toString(originalDesiredProduct.cashback));
			        	desiredProduct.setInterestRate(Double.toString(originalDesiredProduct.rate));
			        	desiredProduct.setLender(originalDesiredProduct.lenderName);
			        	desiredProduct.setMaximumAmortization(Double.toString(originalDesiredProduct.amortization));
			        	desiredProduct.setMortgageAmount(Double.toString(originalDesiredProduct.mortaggeAmout));
			        	desiredProduct.setMortgageType(originalDesiredProduct.mortgageType);
			        	desiredProduct.setPayment(Double.toString(originalDesiredProduct.payment));
			        	desiredProduct.setPosition(originalDesiredProduct.position);
			        	desiredProduct.setProduct(originalDesiredProduct.productName);
			        	desiredProduct.setProductID(Integer.toString(originalDesiredProduct.productID));
			        	desiredProduct.setTerm(originalDesiredProduct.term);
			        		
			        	//********************Disired  Product************
			        	
			        	Set<DesiredProduct>  desiredSet=new HashSet<DesiredProduct>();
			        	
			        	desiredSet.add(desiredProduct);
			        	
			        	allProductAlgoJSON.setDesiredProduct(desiredSet);
			        	
			        	
			        	
			        	
			    	}
			    	else{ // TODO If originalDesiredProduct == null, then have different text that the "Lender Requirements" text.  Basically, they were considering nothing ... 
			    		if (Recommendations.size() > 0){
			    			originalDetails.append("Due to lender requirements, the type and term of mortgage you were originally considering " +
			        				"does not fit your present financing situation.  However, there are additional Mortgage Types and Terms " +
			        				"which fit your situation nicely.  Please refer to the Recommended section of this proposal.");	
			    		}
			    		else{
			    			originalDetails.append("Due to lender requirements, the type and term of mortgage you were originally considering " +
			        				"does not fit your present financing situation.  Please work with your Broker to explore options around how your application can be strengthened.");
			    		}
			    	}
			    }
			    else{
			    	if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Prequalify, clientOpportunity.whatIsYourLendingGoal)){
			    		originalAp.append("Because you are Prequalifying for financing prior to purchasing a property, the exact purchase price and mortage amount is currently unknown. "
			    				+ "In the below proposal, we have modelled an approximate purchase price based upon the income and down payment information you shared." +
			        			"Please see the below section for our recommendation."+ "\n");
			    		originalDetails.append("Mortgage Type: " + "\t\t\t" + "Best Fit" + "\n");
			    		originalDetails.append("Mortgage Term: " + "\t\t" + "Best Fit (Please see the below recommendation)" + "\n");
					}
			    	else{
			    		if (Recommendations.size() > 0){
				    		originalAp.append("On your original mortgage application, you requested we recommend the type and term of mortgage which best suits your needs.  " +
				        			"Please see the below section for our recommendation.");
				    		originalDetails.append("Mortgage Type: " + "\t\t\t" + "Best Fit" + "\n");
				    		originalDetails.append("Mortgage Term: " + "\t\t" + "Best Fit (Please see the below recommendation)" + "\n");
						}
						else{
							originalAp.append("Due to lender requirements and the credit considerations in your original application " +
				    				"the options for your present financing situation are presently limited.  Please work with your Broker to explore options around how your application can be strengthened.");
						}	
			    	}			    	
			    }

			   // System.out.println(originalDetails.toString());
			    
			    String finishedOriginalApString = originalAp.toString();
			    AlgoNote originalDesired = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.High,finishedOriginalApString,"OriginalDesired");
			    marketingTemplateNotes.add(originalDesired);
			    
			    //Added  Json   Data
			    

			    
			    
			    String finishedOriginalDetails = originalDetailsJson.toString();
			    AlgoNote originalDetail = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.High,finishedOriginalDetails,"OriginalDetails");
			    marketingTemplateNotes.add(originalDetail);
			    
			    // Helping you achieving your goals  
			    List<AlgoProduct> allProducts = getAllProducts();     // TODO uncomment system.out in       getAllProducts method. 
			    int totalProductCount = allProducts.size() + totalCombinedProducts;
			    
			    StringBuilder helpingAchieve = new StringBuilder();
			    helpingAchieve.append("To help you achieve your goals, we have virtually underwritten your application across ");
			    
			    if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Prequalify, clientOpportunity.whatIsYourLendingGoal)){
			    	helpingAchieve.append(totalProductCount + " Mortgage options."); //  Of those options, you pre-qualify ");
			    	//helpingAchieve.append("for " + countOfQualifyingProducts + " different possibilities.  ");                
			    }
			    else{
			    	helpingAchieve.append(totalProductCount + " Mortgage options."); //   Of those options, the property you are financing could ");
			    	//helpingAchieve.append("potentially qualify for " + countOfQualifyingProducts + " different possibilities.  ");                	
			    }
			    helpingAchieve.append("We have filtered these options for you (based upon cost effectiveness, interest rate, flexibility and other criteria) ");
			    helpingAchieve.append("to the best " + Recommendations.size() + " mortgages presently offered by lenders which fit your goals.  ");
			    helpingAchieve.append("And finally, we have also provided a single recommendation for your consideration along with the reasons for our recommendation.");
			    
			    String finishedHelpingAchieve = helpingAchieve.toString();
			    AlgoNote helpingAchieveNote = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.High,finishedHelpingAchieve,"HelpingAchieve");
			    marketingTemplateNotes.add(helpingAchieveNote);
			    
			    if (currentRecommend == null){
			    	if (ProductRecommendations1.size() > 0){
			    		double lowRate = 100;
			        	for (UnderwriteAll rec : ProductRecommendations1) {
							if (rec.potentialProduct.interestRate < lowRate){
								currentRecommend = rec;
								lowRate = rec.potentialProduct.interestRate;
							}
							else if (rec.potentialProduct.interestRate == lowRate){
								if (currentRecommend != null){
									if (rec.fitness > currentRecommend.fitness){
										currentRecommend = rec;
										lowRate = rec.potentialProduct.interestRate;
									}
								}
								else{
									currentRecommend = rec;
									lowRate = rec.potentialProduct.interestRate;
								}
							}						
						}
			    	}
			    	else{
			    		AlgoNote difficultApplication = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "You have a complex lending situation.  Please work with your Broker to explore options around how your application can be strengthened.", "Recommend");
			    		marketingTemplateNotes.add(difficultApplication);
			    	}
			    }
			    
			 // Calculate Combined Recommendation
			    // Logic if WFG deal highest Must be within x percent of lowest payment, then Highest fitness
			    // if not WFG Deal, highest Fitness. 
			    // Lastly, decide on Combined or Single Recommendation.
			    // Calculate best Combined Recommendaiton.
			    CombinedRecommendation bestCombined = null;
			    if (combinedRecommendationsList.size() > 0){
			    	if (wfgDeal == true){
						 // Select Lowest Payment
						 for (CombinedRecommendation combinedRecommendation : combinedRecommendationsList) {
							 if (bestCombined == null)
								 bestCombined = combinedRecommendation;
							 else{
								 if (Double.parseDouble(combinedRecommendation.getTotalPayment())  < Double.parseDouble(bestCombined.getTotalPayment()))
										bestCombined = combinedRecommendation;	 
							 }
							
						}
					 }
					 else{
						 // Select Highest Fitness
						 for (CombinedRecommendation combinedRecommendation : combinedRecommendationsList) {
							 if (bestCombined == null)
								 bestCombined = combinedRecommendation;
							 else{
								 if (combinedRecommendation.underwriteCombined.fitness > bestCombined.underwriteCombined.fitness)
										bestCombined = combinedRecommendation;	 
							 }
							 
						 }
					 }
			    }
				
			    String recommendationType = null;
			    logger.info("Single  Type"+currentRecommend);
			    logger.info("Combined  Type"+bestCombined);

			    if (currentRecommend != null && bestCombined != null){
			    	if (wfgDeal == true){
						 // Select Lowest Payment
			    		logger.info("wfgDeal  True");
			    		if (currentRecommend.expectedFitnessPayment > bestCombined.underwriteCombined.expectedFitnessPayment)
			    		{
			    			recommendationType = "Combined";
				    		logger.info("currentRecommend.expectedFitnessPayment > recommendationType  and  Combined");

			    		}else{
			    			recommendationType = "Single";	
				    		logger.info("recommendationType  Single");

			    	}}
			    	else{
			    		
			    		logger.debug("<<<<<<<<<<<<<.else part >>>>>>>>>>>>>>>");
			    		  recommendationType = "Single";    
			    	       logger.info("recommendationType  Single");
			    		/*
			    		if (currentRecommend.fitness < bestCombined.underwriteCombined.fitness){
			    			recommendationType = "Combined";	
			    		logger.info("currentRecommend.expectedFitnessPayment < recommendationType  and  Combined");

			    		}	else{
			    			recommendationType = "Single";	
				    		logger.info("recommendationType  Single");

			    	}
				    		*/}
			    }
			    else if (currentRecommend != null && bestCombined == null)
			    	recommendationType = "Single";			    
				else if (currentRecommend == null && bestCombined != null)
			    	recommendationType = "Combined";
			    
	    	
			    StringBuilder notes = new StringBuilder();
			    // Set Type and Term of Recommendation to Strings
			    String recommendedType = "None";
			    String recommendedTerm = "None";
			    if (recommendationType != null){
			    	
			    	if (recommendationType.contains("Single"))
			    		recommendedType = mortgageTypeString(currentRecommend.potentialProduct.mortgageType);
			    	else if (recommendationType.contains("Combined"))
			    		recommendedType = "Combined";
				    
			    	if (recommendationType.contains("Single"))
			    		recommendedTerm = mortgageTermString(currentRecommend.potentialProduct.term);
			    	else  if (recommendationType.contains("Combined"))
			    		recommendedTerm = bestCombined.getBaseTerm() + " & " + bestCombined.getAdditionalTerm();
			    }
			    
			    // Strip Out Lender Name
			    String lenderName = "None";
			    if (recommendationType != null){
			    	
			    	if (recommendationType.contains("Single"))
			    		lenderName = currentRecommend.lenderName;
			    	else  if (recommendationType.contains("Combined"))
			    		lenderName = bestCombined.getBaseLender();			    	
			    }
			    
				double mortgageAmountWithNoIns = clientOpportunity.desiredMortgageAmount; 
		    	double equityOrDownpayment = Math.max(clientOpportunity.propertyValue - clientOpportunity.currentMortgageAmount, clientOpportunity.downpaymentAmount);
		    	double projectedPropertyValue = clientOpportunity.propertyValue;
		    	
		    	if (originalDesiredProduct != null && currentRecommend != null){
		    		if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Prequalify, clientOpportunity.whatIsYourLendingGoal)){
						mortgageAmountWithNoIns = currentRecommend.expectedMortgageAmount - currentRecommend.insuranceAmount;
				    	equityOrDownpayment = clientOpportunity.downpaymentAmount;
				    	projectedPropertyValue = mortgageAmountWithNoIns + clientOpportunity.downpaymentAmount;			
					}
					else{
						if (Double.isNaN(originalDesiredProduct.insuranceAmount)){
							mortgageAmountWithNoIns = originalDesiredProduct.mortaggeAmout;
						}
						else{
							mortgageAmountWithNoIns = originalDesiredProduct.mortaggeAmout - originalDesiredProduct.insuranceAmount;
						}					
				    	equityOrDownpayment = clientOpportunity.propertyValue - mortgageAmountWithNoIns;	
				    	projectedPropertyValue = clientOpportunity.propertyValue;
					}
		    	}
		    	else if (currentRecommend != null){
		    		if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Prequalify, clientOpportunity.whatIsYourLendingGoal)){
						mortgageAmountWithNoIns = currentRecommend.expectedMortgageAmount - currentRecommend.insuranceAmount;
				    	equityOrDownpayment = clientOpportunity.downpaymentAmount;
				    	projectedPropertyValue = mortgageAmountWithNoIns + clientOpportunity.downpaymentAmount;			
					}
					else{
						mortgageAmountWithNoIns = currentRecommend.expectedMortgageAmount - currentRecommend.insuranceAmount;				
				    	equityOrDownpayment = clientOpportunity.propertyValue - mortgageAmountWithNoIns;	
				    	projectedPropertyValue = clientOpportunity.propertyValue;
					}
		    	}

			    // Recommendation should be determined ... Set the values into table.
			    StringBuilder recommendDetails = new StringBuilder();
			    RecommendedProduct recommendedProduct = new RecommendedProduct();	
			    if (recommendationType!=null && recommendationType.contains("Single")){
			    	Double mortgageamt = projectedPropertyValue+equityOrDownpayment;
			    	logger.info("-----------------------mortgageamt  ++++++++--------------"+mortgageamt);
			    	
			    	//logger.info("---------------BaseMortgaeType---------------------------------------"+bestCombined.getBaseMortgageType());
			    	//logger.info("--------------------AdditionalMortgaeType----------------------------------"+ bestCombined.getAdditionalMortgageType());
			    	//logger.info("--------------------BaseLenderName----------------------------------"+bestCombined.getBaseLender());
			    	//logger.info("--------------------AdditionalLenderName----------------------------------"+bestCombined.getAdditionalLender());

			    	//logger.info("---------------Property  Value  is -------------"+clientOpportunity.propertyValue);
			    	Recommendation tempCurrentRecommend = new Recommendation(currentRecommend);	
			    	ourRecommendationJson.put("propertyValue", String.format("%1$,.2f", projectedPropertyValue));
			    	ourRecommendationJson.put("downpaymentEquity", String.format("%1$,.2f", equityOrDownpayment));
			    	ourRecommendationJson.put("mortgageAmount", String.format("%1$,.2f", mortgageAmountWithNoIns));
			    	ourRecommendationJson.put("insuranceAmount", String.format("%1$,.2f", currentRecommend.insuranceAmount));
			    	ourRecommendationJson.put("totalMortgage", String.format("%1$,.2f",  currentRecommend.expectedMortgageAmount));
			    	ourRecommendationJson.put("amortization",  currentRecommend.amortization);
			    	ourRecommendationJson.put("mortgageType",recommendedType);
			    	ourRecommendationJson.put("mortgageTerm", recommendedTerm);
			    	ourRecommendationJson.put("paymentAmount", String.format("%1$,.2f", currentRecommend.expectedFitnessPayment));
			    	ourRecommendationJson.put("interestRate", String.format("%1$,.2f",  currentRecommend.potentialProduct.interestRate));
			    	ourRecommendationJson.put("lender", tempCurrentRecommend.lenderName);
			    	ourRecommendationJson.put("productID", currentRecommend.potentialProduct.getId());
			    	ourRecommendationJson.put("productName", currentRecommend.potentialProduct.name);
			  
			    	recommendedProduct.setCashbackPercent(Double.toString(currentRecommend.potentialProduct.cashback));
			    	recommendedProduct.setInterestRate(Double.toString(currentRecommend.interestRate));
			    	recommendedProduct.setLender(tempCurrentRecommend.lenderName);
			    	recommendedProduct.setMaximumAmortization(Double.toString(currentRecommend.amortization));
			    	recommendedProduct.setMortgageAmount(Double.toString(currentRecommend.expectedMortgageAmount));
			    	recommendedProduct.setMortgageType(recommendedType);
			    	recommendedProduct.setPayment(Double.toString(currentRecommend.expectedFitnessPayment));
		        	
		        	String recommendPosition = "1st";
		        	if (currentRecommend.clientOpportunity.chargeOnTitle.contains("1"))
		        		recommendPosition = "2nd";
		        	else if (currentRecommend.clientOpportunity.chargeOnTitle.contains("2"))
		        		recommendPosition = "3rd";
		        	
		        	recommendedProduct.setPosition(recommendPosition);
		        	recommendedProduct.setProduct(currentRecommend.potentialProduct.name);
		        	recommendedProduct.setProductID(Integer.toString(currentRecommend.potentialProduct.getId()));
		        	recommendedProduct.setTerm(recommendedTerm);
		        	/////////////////////////////////////////////////////
		        	Set<RecommendedProduct>  desiredSet=new HashSet<RecommendedProduct>();
		        	
		        	desiredSet.add(recommendedProduct);
		        	allProductAlgoJSON.setRecommendedProduct(desiredSet);
			    	
			    	for (AlgoNote note : currentRecommend.marketingTemplateNotes) {
						notes.append(note.description + "\n");  
					}
			    }		    		
		    	else if (recommendationType!=null && recommendationType.contains("Combined")){
			    	Double mortgageamt = projectedPropertyValue+equityOrDownpayment;
			    	
			    	logger.info("--------------------mortgageamt         -------"+mortgageamt);

			    	Recommendation tempCurrentRecommend = new Recommendation(currentRecommend);	
			    				    	ourRecommendationJson.put("propertyValue", String.format("%1$,.2f", projectedPropertyValue));
			    	ourRecommendationJson.put("downpaymentEquity", String.format("%1$,.2f", equityOrDownpayment));
			    	
			    	// Base Product in Combined Product
			    	ourRecommendationJson.put("mortgageAmount", bestCombined.getBaseMortgageAmount());
			    	//ourRecommendationJson.put("insuranceAmount1", "0"); // TODO Needs revision depending on Answer to insurance question
			    	// ourRecommendationJson.put("totalMortgage1", String.format("%1$,.2f",  currentRecommend.expectedMortgageAmount));  // TODO Needs revision depending on Answer to insurance question
			    	ourRecommendationJson.put("amortization",  bestCombined.getBaseAmortization());
			    	ourRecommendationJson.put("mortgageType", bestCombined.getBaseMortgageType());
			    	ourRecommendationJson.put("mortgageTerm", bestCombined.getBaseTerm());
			    	ourRecommendationJson.put("paymentAmount", bestCombined.getBasePayment());
			    	ourRecommendationJson.put("interestRate", bestCombined.getBaseInterestRate());
			    	ourRecommendationJson.put("totalMortgageAmount",bestCombined.getTotalMortgageAmount());
			    	ourRecommendationJson.put("totalPayment",bestCombined.getTotalPayment());


			    	ourRecommendationJson.put("lender", bestCombined.getBaseLender());
			    	ourRecommendationJson.put("productID", bestCombined.getBaseProductID());
			    	ourRecommendationJson.put("productName", bestCombined.getBaseProduct());
			    	
			    	// Additional Product in Combined Product
			    	ourRecommendationJson.put("mortgageAmount1", bestCombined.getAdditionalMortgageAmount());
			    	//ourRecommendationJson.put("insuranceAmount1", "0"); // TODO Needs revision depending on Answer to insurance question
			    	// ourRecommendationJson.put("totalMortgage1", String.format("%1$,.2f",  currentRecommend.expectedMortgageAmount));  // TODO Needs revision depending on Answer to insurance question
			    	ourRecommendationJson.put("amortization1",  bestCombined.getAdditionalAmortization());
			    	ourRecommendationJson.put("mortgageType1", bestCombined.getAdditionalMortgageType());
			    	ourRecommendationJson.put("mortgageTerm1", bestCombined.getAdditionalTerm());
			    	ourRecommendationJson.put("paymentAmount1", bestCombined.getAdditionalPayment());
			    	ourRecommendationJson.put("interestRate1", bestCombined.getAdditionalInterestRate());
			    	ourRecommendationJson.put("lender1", bestCombined.getAdditionalLender());
			    	ourRecommendationJson.put("productID1", bestCombined.getAdditionalProductID());
			    	ourRecommendationJson.put("productName1", bestCombined.getAdditionalProduct());
			    	
			   
			    	
			    	// TODO Vikash - I am not sure of the use of recommendedProduct - in the below ... Can it be removed? 
			    	recommendedProduct.setCashbackPercent(Double.toString(currentRecommend.potentialProduct.cashback));
			    	recommendedProduct.setInterestRate(Double.toString(currentRecommend.interestRate));
			    	recommendedProduct.setLender(tempCurrentRecommend.lenderName);
			    	recommendedProduct.setMaximumAmortization(Double.toString(currentRecommend.amortization));
			    	recommendedProduct.setMortgageAmount(Double.toString(currentRecommend.expectedMortgageAmount));
			    	recommendedProduct.setMortgageType(recommendedType);
			    	recommendedProduct.setPayment(Double.toString(currentRecommend.expectedFitnessPayment));
		        	
		        	String recommendPosition = "1st";
		        	if (currentRecommend.clientOpportunity.chargeOnTitle.contains("1"))
		        		recommendPosition = "2nd";
		        	else if (currentRecommend.clientOpportunity.chargeOnTitle.contains("2"))
		        		recommendPosition = "3rd";
		        	
		        	recommendedProduct.setPosition(recommendPosition);
		        	recommendedProduct.setProduct(currentRecommend.potentialProduct.name);
		        	recommendedProduct.setProductID(Integer.toString(currentRecommend.potentialProduct.getId()));
		        	recommendedProduct.setTerm(recommendedTerm);
		        	/////////////////////////////////////////////////////
		        	Set<RecommendedProduct>  desiredSet=new HashSet<RecommendedProduct>();
		        	
		        	desiredSet.add(recommendedProduct);
		        	allProductAlgoJSON.setRecommendedProduct(desiredSet);
			    	
			    	for (AlgoNote note : currentRecommend.marketingTemplateNotes) {
						notes.append(note.description + "\n");  
					}
		    	}
		    	else{
		    		recommendDetails.append("Due to lender requirements, the type and term of mortgage you were originally considering " +
							"does not fit your present financing situation.  Please work with your Broker to explore options around how your application can be strengthened.");
				}
		    		
			    
			    String finishedRecommendString = ourRecommendationJson.toString();
			    AlgoNote recomendNote = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.High,finishedRecommendString,"RecommendDetails");
			    marketingTemplateNotes.add(recomendNote);
			    
			    StringBuilder ratiosDetails = new StringBuilder();
			    if (recommendationType!=null && recommendationType.contains("Single")){
			    	ratiosDetails.append("Lender: " + currentRecommend.lenderName);	
			    	ratiosDetails.append("GDS: " + currentRecommend.GDSRatio + "\n");
				    ratiosDetails.append("TDS: " + currentRecommend.TDSRatio);	
			    }
			    else if (recommendationType!=null && recommendationType.contains("Combined")){
			    	ratiosDetails.append("Lender: " + bestCombined.getBaseLender());	
			    	ratiosDetails.append("GDS: " + bestCombined.underwriteCombined.GDSRatio + "\n");
				    ratiosDetails.append("TDS: " + bestCombined.underwriteCombined.TDSRatio);	
			    }
			    else{
			    	double lowestRate1 = 100;
			    	UnderwriteAll lowestRate5Year = null;
			    	GenericComparator orderbyInterestRate2 = Util.newGenericComparator("interestRate","asc"); 
			    	Collections.sort(failedFixed5YrProducts, orderbyInterestRate2);
			    	try{
			    		lowestRate5Year = failedFixed5YrProducts.get(0);
			    	}
			    	catch(NullPointerException e){
			    		logger.error("failedFixed5YrProducts  getting  Null"+e.getMessage());				    		
			    	}

			    	ratiosDetails.append(lowestRate5Year.potentialProduct.name + " - ");
			    	ratiosDetails.append("GDS: " + lowestRate5Year.GDSRatio + "\n");
				    ratiosDetails.append("TDS: " + lowestRate5Year.TDSRatio);
				    
				    StringBuilder failurestring = new StringBuilder();
					for (String reason : lowestRate5Year.failReasons) {
						failurestring.append(reason + ", ");
					}
				    
				    AlgoNote failureNote = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.High,"Failure Reasons: " + failurestring, "failureReasons1");
				    marketingTemplateNotes.add(failureNote);
			    }
			    		
			    		
			    
			    AlgoNote ratiosNote = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.High,ratiosDetails.toString(),"GDS - TDS");
			    marketingTemplateNotes.add(ratiosNote);
			    
			    // Testing the GDS of CurrnetRecommend ... 
			  //  System.out.println(finishedRecommendString);
			    //System.out.println("Reccommend TDS: " + currentRecommend.TDSRatio);
			    
			  
			    
			    // Describe how we reached Recommendation ... 
			    StringBuilder finishedRiskBias = new StringBuilder();
			    if (clientRiskBias == 0){
			    	finishedRiskBias.append("Your original mortgage application included limited future lifestyle information " +
			    			"thus we did not have enough context to determine your personal bias " +
			    			"towards reducing future risk (the possibility of rising interest rates " +
			    			"and/or payout penalties) in your mortgage." +
			    			"  Thus we have based our above recommendation primarily upon reducing cost " +
			    			"while still maintaining reasonable flexibility."); 
			    	//AlgoNote NoteRecommend = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Your application included limited Lifestyle information, thus our recommentation is limited, in the event your primary concern is a low monthly payment, a 1 Year Fixed or 3 Year Variable Mortgage will be your best option." , "Recommendation1");
			        //marketingTemplateNotes.add(NoteRecommend);            	           	
			    }
			    else if (clientRiskBias == 1){
			    	finishedRiskBias.append("Based upon the future lifestyle questions you answered in your application, " +
			    			"your bias towards reducing future risk (the possibility of rising interest rates " +
			    			"and/or payout penalties) or saving current mortgage costs leans strongly toward " +
			    			"reducing cost even if it means a little bit of future risk.  " +
			    			"We recommended the above mortgage solution because it most closely aligns " +
			    			"with your personal balance between reducing risk and reducing cost. ");              	
			    }
			    else if (clientRiskBias == 2){
			    	finishedRiskBias.append("Based upon the future lifestyle questions you answered in your application, " +
			    			"your bias towards reducing future risk (the possibility of rising interest rates " +
			    			"and/or payout penalties) or saving current mortgage costs is balanced.  " +            			
			    			"We recommended the above mortgage solution because it most closely aligns " +
			    			"with your personal balance between reducing risk and reducing cost. ");           
			    }
			    else if (clientRiskBias == 3){
			    	finishedRiskBias.append("Based upon the future lifestyle questions you answered in your application, " +
			    			"your bias towards reducing future risk (the possibility of rising interest rates " +
			    			"and/or payout penalties) or saving current mortgage costs leans strongly toward " +
			    			"reducing future risk even if it means a little more cost in the present.  " +
			    			"We recommended the above mortgage solution because it most closely aligns " +
			    			"with your personal balance between reducing risk and reducing cost. ");           
			    }
			    if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Prequalify, clientOpportunity.whatIsYourLendingGoal)){
					finishedRiskBias.append("However, when in the house purchase process, there is an additional consideration ... the purchase price of the property you desire.");
				}
			    String riskBiasToString = finishedRiskBias.toString();
			    AlgoNote riskBiasNote = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.High,riskBiasToString,"RiskBias");
			    marketingTemplateNotes.add(riskBiasNote);
			    
			    // If not the lowest rate product, make a note of it and explain why.  Leave with the option still for a lower rate product.
			    // How does raising rate risk factor into the above recommendation?  
			    // Is it simply a foot note to help alleviate objection or is it weighted into the recommendation somehow?
			    if (currentRecommend != null){
				    if (currentRecommend.interestRate > lowestRate){
				    	String notLowest = "Please note that Mortgages which reduce your future interest rate risk often do not have the lowest cost.  " +
				    			"There are lower cost Mortgages available for you, however they also carry more future interest rate risk.";
				    	AlgoNote notLowestNote = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.High,notLowest,"NotLowest");
				        marketingTemplateNotes.add(notLowestNote);            			
				    }
			    }
			    // Calculate Trend for Considerations Notes.
			    CalculateMortgageTrend();   
			    
			  //System.out.println("AllProductAlgo end");
			    if (Recommendations.size() == 0){
			    	String QualifyingInfo = failedFixed5YrProducts.get(0).potentialProduct.name 
							+ ", opportunityQualifies: " + failedFixed5YrProducts.get(0).opportunityQualifies
							+ ", GDS: " + failedFixed5YrProducts.get(0).GDSRatio + ", GdsAmount: " + failedFixed5YrProducts.get(0).totalDealGDSAmount 
							+ ", TDS: " + failedFixed5YrProducts.get(0).TDSRatio + ", TdsAmount: " + failedFixed5YrProducts.get(0).opportunityTDSAmount
							+ ", FailGDS: " + failedFixed5YrProducts.get(0).failedGDS + ", FailTDS: " + failedFixed5YrProducts.get(0).failedTDS 
							+ ", Income: " + failedFixed5YrProducts.get(0).totalDealIncome 
							+ ", Payment: " + failedFixed5YrProducts.get(0).anticipatedMortgagePayment + ", annualHeatCost: " + failedFixed5YrProducts.get(0).annualHeatCost 
							+ ", propertyTaxes: " + clientOpportunity.propertyTaxes + ", condoFees: " + clientOpportunity.condoFees
							+ ", Insurance: " + failedFixed5YrProducts.get(0).insuranceAmount; 
			    	 // logger.info(QualifyingInfo);
					
//					StringBuilder failurestring = new StringBuilder();
//					for (String reason : failedFixed5YrProducts.get(0).failReasons) {
//						failurestring.append(reason + ", ");
//						System.out.println( "Failure Reasons:" + failurestring);
//					}
//					
//					AlgoNote Note11 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Failure Reasons:" + failurestring, "failureReasons2");
//					dealNotes.add(Note11);
//					
//					AlgoNote Note12 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, QualifyingInfo);
//					dealNotes.add(Note11);	
			    }
				
			    
			    if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Prequalify, clientOpportunity.whatIsYourLendingGoal)){
			    	StringBuilder maxMortgage = new StringBuilder();
			    	maxMortgage.append("When qualifying for a mortgage with fluctuating interest rates or a shorter term, " +
			    			"the Government of Canada requires that lenders use a higher interest in their qualification formulas " +
			    			"than the actual rate of the product.  This is to ensure that home buyers have " +
			    			"some 'financial margin' if interest rates happen to rise over the next 5 years.  " +
			    			"Condo fees also present an additional consideration because lenders consider these as " +
			    			"fixed monthly cost which is not required if you purchase a house.  " +
			    			"The following section outlines the various maximum purchase amounts you qualify for depending " +
			    			"on the type and term of mortgage you select. This pre-approval is based on the " +
			    			"lenders and insurers acceptance of all supporting documents and property. ");
			    	String maxMortgageIntro = maxMortgage.toString();
			        AlgoNote maxMortgageIntroNote = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.High,maxMortgageIntro,"MaxMortgageIntro");
			        marketingTemplateNotes.add(maxMortgageIntroNote);
			        
			                        	
			        UnderwriteAll VariableMortgage = null;
			        UnderwriteAll FixedMortgage = null;
			        UnderwritingBase LOCMortgage = null;
			        if (acceptableVariable5YrProducts.size() > 0)
			        	VariableMortgage = acceptableVariable5YrProducts.get(0);
			        else if (acceptableVariable3YrProducts.size() > 0)
			        	VariableMortgage = acceptableVariable3YrProducts.get(0);

			        if (acceptableFixed5YrProducts.size() > 0)
			        	FixedMortgage = acceptableFixed5YrProducts.get(0);
			        else if (acceptableFixed7YrProducts.size() > 0)
			        	FixedMortgage = acceptableFixed7YrProducts.get(0);

			        if (acceptableLOCProducts.size() > 0)
			        	LOCMortgage = acceptableLOCProducts.get(0);

			        StringBuilder maximumTable = new StringBuilder();
			        
			        
			        JSONObject maximumTableJson=new JSONObject();
			        
			        JSONObject maximumTable123=new JSONObject();
			        
			        
			        
			        if (VariableMortgage != null){
			        	  logger.info("VariableMortgageMMMMMMMMMMMMMMM"+VariableMortgage);
			        	// House
			        	double insuranceMaxRatio = 95;
			        	double maxPropertyAmountHouse = Math.min(VariableMortgage.maximumMortgageNoCondo, 1000000) / insuranceMaxRatio * 100;						
			        	double downpay5percentHouse = neededDownPayment(maxPropertyAmountHouse);
			        	double largestDownpaymentHouse = Math.max(downpay5percentHouse, VariableMortgage.clientOpportunity.downpaymentAmount);
			        	double variableMortHouseNoDebtRepayValue = largestDownpaymentHouse + VariableMortgage.maximumMortgageNoCondo;
			        	double variableMortHouseNoDebtRepayLTV = VariableMortgage.maximumMortgageNoCondo / variableMortHouseNoDebtRepayValue * 100;
			        	double variableMortHouseNoDebtRepayThresh = Math.round(insuranceToThreshold(variableMortHouseNoDebtRepayLTV, variableMortHouseNoDebtRepayValue));
			        	

			        	
			        	if (variableMortHouseNoDebtRepayThresh == 0){
			        		//maximumTable.append("Variable Mortgage (House)" + "\t" + "0"  + "\t" + Math.round(largestDownpaymentHouse)  + "\t" + Math.round(VariableMortgage.maximumMortgageNoCondo)  + "\t" + Math.round(VariableMortgage.insureAmountMaxMortgage)  + "\t" + Math.round(variableMortHouseNoDebtRepayValue)  + "\n");		
			        		//maximumTableJson.put("variableMortgage(House)","0"  + "\t" + Math.round(largestDownpaymentHouse)  + "\t" + Math.round(VariableMortgage.maximumMortgageNoCondo)  + "\t" + Math.round(VariableMortgage.insureAmountMaxMortgage)  + "\t" + Math.round(variableMortHouseNoDebtRepayValue))	;		        	
			        		maximumTableJson.put("variableMortgage(House)", maximumTable123);
			        	
			        		maximumTable123.put("largestDownpaymentHouse", Math.round(largestDownpaymentHouse));
			        		maximumTable123.put("maximumMortgageNoCondo", Math.round(VariableMortgage.maximumMortgageNoCondo));
			        		maximumTable123.put("insureAmountMaxMortgage",  Math.round(VariableMortgage.insureAmountMaxMortgage));
			        		maximumTable123.put("variableMortHouseNoDebtRepayValue", Math.round(variableMortHouseNoDebtRepayValue));
	
			        	}
			        	else{
			        		double newMortgageAmount = variableMortHouseNoDebtRepayValue - (variableMortHouseNoDebtRepayThresh + 10);
			        		double newLTV = newMortgageAmount / variableMortHouseNoDebtRepayValue * 100;
			        		
		                	InsurerProducts insureProd = new InsurerProducts(this, newMortgageAmount, VariableMortgage.amortization);
					        double newInsuranceAmount = InsurancePremiums.calculateInsurancePremium(insureProd);
			        		//double newInsuranceAmount = InsurancePremiums.calculateInsurancePremium(false, newLTV, VariableMortgage.hasSelfEmployedIncome, newMortgageAmount, 0, false);
			        	
			        		//maximumTable.append("Variable Mortgage (House)" + "\t" + "0"  + "\t" +  Math.round(largestDownpaymentHouse)  + "\t" + Math.round(VariableMortgage.maximumMortgageNoCondo)  + "\t" + Math.round(VariableMortgage.insureAmountMaxMortgage) + "\u00B1" + "\t" + Math.round(variableMortHouseNoDebtRepayValue)  + "\n");
			        		//maximumTableJson.put("variableMortgage(House)", "0"  + "\t" +  Math.round(largestDownpaymentHouse)  + "\t" + Math.round(VariableMortgage.maximumMortgageNoCondo)  + "\t" + Math.round(VariableMortgage.insureAmountMaxMortgage) + "\u00B1" + "\t" + Math.round(variableMortHouseNoDebtRepayValue));
					        maximumTableJson.put("variableMortgage(House)", maximumTable123);
					        maximumTable123.put("largestDownpaymentHouse", Math.round(largestDownpaymentHouse));
					        maximumTable123.put("maximumMortgageNoCondo", Math.round(VariableMortgage.maximumMortgageNoCondo));
					        maximumTable123.put("insureAmountMaxMortgage",  Math.round(VariableMortgage.insureAmountMaxMortgage));
					        maximumTable123.put("variableMortHouseNoDebtRepayValue", Math.round(variableMortHouseNoDebtRepayValue));
			        		
			        		String reduceIns = "- In this maximum mortgage scenario, please please note that the insurance amount to CMHC / GE could be " +
			        				"reduced from $" + Math.round(VariableMortgage.insureAmountMaxMortgage) + " to $" + Math.round(newInsuranceAmount) + " if you were able to " +
			        						"increase your downpayment and reduce the maximum mortgage amount by $" + Math.round(variableMortHouseNoDebtRepayThresh) + "\n";
			        		notes.append(reduceIns);                		
			        	}
			        	
			        	// Debt Repayment makes no sense if it means reducing Downpayment with a 5% down Property ... 
//                	double variableMortHouseDebtRepayValue = VariableMortgage.downPaymentWithDebtReduce + VariableMortgage.maximumMortgageNoCondo;
//                	double variableMortHouseDebtRepayLTV = VariableMortgage.maximumMortgageNoCondoPlusDebtReduce / variableMortHouseDebtRepayValue * 100;
//                	double variableMortHouseDebtRepayThresh = insuranceToThreshold(variableMortHouseDebtRepayLTV, variableMortHouseDebtRepayValue);
//                	if (variableMortHouseDebtRepayThresh == 0){
//                		maximumTable.append("Variable Mortgage (House)" + "\t" + Math.round(VariableMortgage.totalDebtRepaid)  + "\t" +  Math.round(VariableMortgage.downPaymentWithDebtReduce)  + "\t" + Math.round(VariableMortgage.maximumMortgageNoCondoPlusDebtReduce)  + "\t" + Math.round(VariableMortgage.insureAmountMaxMortgagePlusDebtReduce)  + "\t" + Math.round(variableMortHouseDebtRepayValue)  + "\n");
//                	}
//                	else{
//                		double newMortgageAmount = variableMortHouseDebtRepayValue - (variableMortHouseDebtRepayThresh + 10);
//                		double newLTV = newMortgageAmount / variableMortHouseDebtRepayValue * 100;
//                		double newInsuranceAmount = InsurancePremiums.calculateInsurancePremium(false, newLTV, VariableMortgage.hasSelfEmployedIncome, newMortgageAmount, 0, false);
//                	
//                		maximumTable.append("Variable Mortgage (House)" + "\t" + Math.round(VariableMortgage.totalDebtRepaid)  + "\t" +  Math.round(VariableMortgage.downPaymentWithDebtReduce)  + "\t" + Math.round(VariableMortgage.maximumMortgageNoCondoPlusDebtReduce)  + "\t" + Math.round(VariableMortgage.insureAmountMaxMortgagePlusDebtReduce) + "\u00B2" + "\t" + Math.round(variableMortHouseDebtRepayValue)  + "\n");
//                		String reduceIns = "2. In this maximum mortgage scenario, please please note that the insurance amount to CMHC / GE could be " +
//                				"reduced from $" + Math.round(VariableMortgage.insureAmountMaxMortgage) + " to $" + Math.round(newInsuranceAmount) + " if you were able to " +
//                						"increase your downpayment and reduce the maximum mortgage amount by $" + Math.round(variableMortHouseDebtRepayThresh) + "\n";
//                		notes.append(reduceIns);
//                	}
			        		
			        	// Condo
			        	double maxPropertyAmountCondo = Math.min(VariableMortgage.maximumMortgageCondo, 1000000) / insuranceMaxRatio * 100;
			        	double downpay5percentCondo = neededDownPayment(maxPropertyAmountCondo);
			        	double largestDownpaymentCondo = Math.max(downpay5percentCondo, VariableMortgage.clientOpportunity.downpaymentAmount);
			        	double variableMortCondoNoDebtRepayValue = largestDownpaymentCondo + VariableMortgage.maximumMortgageCondo;
			        	double variableMortCondoNoDebtRepayLTV = VariableMortgage.maximumMortgageCondo / variableMortCondoNoDebtRepayValue * 100;
			        	double variableMortCondoNoDebtRepayThresh = insuranceToThreshold(variableMortCondoNoDebtRepayLTV, variableMortCondoNoDebtRepayValue);
			        	if (variableMortCondoNoDebtRepayThresh == 0){
			        		//maximumTable.append("Variable Mortgage (Condo)" + "\t" + "0"  + "\t" +  Math.round(largestDownpaymentCondo)  + "\t" + Math.round(VariableMortgage.maximumMortgageCondo)  + "\t" + Math.round(VariableMortgage.insureAmountMaxMortgageWithCondo)  + "\t" + Math.round(variableMortCondoNoDebtRepayValue)  + "\n");		
			        		//maximumTableJson.put("variableMortgage(House)","0"  + "\t" + Math.round(largestDownpaymentHouse)  + "\t" + Math.round(VariableMortgage.maximumMortgageNoCondo)  + "\t" + Math.round(VariableMortgage.insureAmountMaxMortgage)  + "\t" + Math.round(variableMortHouseNoDebtRepayValue))	;
			        		 maximumTableJson.put("VariableMortgageCondo", maximumTable123);
			        		 maximumTable123.put("largestDownpaymentHouse", Math.round(largestDownpaymentHouse));
			        		 maximumTable123.put("maximumMortgageNoCondo", Math.round(VariableMortgage.maximumMortgageNoCondo));
			        		 maximumTable123.put("insureAmountMaxMortgage",  Math.round(VariableMortgage.insureAmountMaxMortgage));
			        		 maximumTable123.put("variableMortHouseNoDebtRepayValue", Math.round(variableMortHouseNoDebtRepayValue));
				        					        
			        	}	
			        	else{
			        		double newMortgageAmount = variableMortCondoNoDebtRepayValue - (variableMortCondoNoDebtRepayThresh + 10);
			        		double newLTV = newMortgageAmount / variableMortCondoNoDebtRepayValue * 100;
			        		//InsurerProducts insureProd = new InsurerProducts(VariableMortgage);
			        		InsurerProducts insureProd = new InsurerProducts(this, newMortgageAmount, VariableMortgage.amortization);
					        double newInsuranceAmount = InsurancePremiums.calculateInsurancePremium(insureProd);
			        		//double newInsuranceAmount = InsurancePremiums.calculateInsurancePremium(false, newLTV, VariableMortgage.hasSelfEmployedIncome, newMortgageAmount, 0, false);
			        	
			        		//maximumTable.append("Variable Mortgage (Condo)" + "\t" + "0"  + "\t" +  Math.round(largestDownpaymentCondo)  + "\t" + Math.round(VariableMortgage.maximumMortgageCondo)  + "\t" + Math.round(VariableMortgage.insureAmountMaxMortgageWithCondo) + "\u00B3" + "\t" + Math.round(variableMortCondoNoDebtRepayValue)  + "\n");
			        		maximumTableJson.put("variableMortgage(House)","0"  + "\t" + Math.round(largestDownpaymentHouse)  + "\t" + Math.round(VariableMortgage.maximumMortgageNoCondo)  + "\t" + Math.round(VariableMortgage.insureAmountMaxMortgage)  + "\t" + Math.round(variableMortHouseNoDebtRepayValue))	;
			        		String reduceIns = "- In this maximum mortgage scenario, please please note that the insurance amount to CMHC / GE could be " +
			        				"reduced from $" + Math.round(VariableMortgage.insureAmountMaxMortgage) + " to $" + Math.round(newInsuranceAmount) + " if you were able to " +
			        						"increase your downpayment and reduce the maximum mortgage amount by $" + Math.round(variableMortCondoNoDebtRepayThresh) + "\n";
			        		notes.append(reduceIns);
			        	}
			        	
			        	// Debt Repayment makes no sense if it means reducing Downpayment with a 5% down Property ... 
//                	double variableMortCondoDebtRepayValue = VariableMortgage.downPaymentWithDebtReduce + VariableMortgage.maximumMortgageNoCondo;
//                	double variableMortCondoDebtRepayLTV = VariableMortgage.maxMortgageWithCondoPlusDebtReduce / variableMortCondoDebtRepayValue * 100;
//                	double variableMortCondoDebtRepayThresh = insuranceToThreshold(variableMortCondoDebtRepayLTV, variableMortCondoDebtRepayValue);
//                	if (variableMortCondoDebtRepayThresh == 0){
//                		maximumTable.append("Variable Mortgage (Condo)" + "\t" + Math.round(VariableMortgage.totalDebtRepaid)  + "\t" +  Math.round(VariableMortgage.downPaymentWithDebtReduce)  + "\t" + Math.round(VariableMortgage.maxMortgageWithCondoPlusDebtReduce)  + "\t" + Math.round(VariableMortgage.insureAmountMaxMortgageWithCondoPlusDebtReduce)  + "\t" + Math.round(variableMortCondoDebtRepayValue)  + "\n");	
//                	}
//                	else{
//                		double newMortgageAmount = variableMortCondoDebtRepayValue - (variableMortCondoDebtRepayThresh + 10);
//                		double newLTV = newMortgageAmount / variableMortCondoDebtRepayValue * 100;
//                		double newInsuranceAmount = InsurancePremiums.calculateInsurancePremium(false, newLTV, VariableMortgage.hasSelfEmployedIncome, newMortgageAmount, 0, false);
//                	
//                		maximumTable.append("Variable Mortgage (Condo)" + "\t" + Math.round(VariableMortgage.totalDebtRepaid)  + "\t" +  Math.round(VariableMortgage.downPaymentWithDebtReduce)  + "\t" + Math.round(VariableMortgage.maxMortgageWithCondoPlusDebtReduce)  + "\t" + Math.round(VariableMortgage.insureAmountMaxMortgageWithCondoPlusDebtReduce)  + "\u00B4" + "\t" + Math.round(variableMortCondoDebtRepayValue)  + "\n");
//                		String reduceIns = "4. In this maximum mortgage scenario, please please note that the insurance amount to CMHC / GE could be " +
//                				"reduced from $" + Math.round(VariableMortgage.insureAmountMaxMortgage) + " to $" + Math.round(newInsuranceAmount) + " if you were able to " +
//                						"increase your downpayment and reduce the maximum mortgage amount by $" + Math.round(variableMortCondoDebtRepayThresh) + "\n";
//                		notes.append(reduceIns);
//                	}
			        	           	
			        }
			        if (FixedMortgage != null){
			        	// House
			        	double insuranceMaxRatio = 95;
			        	double maxPropertyAmountHouse = Math.min(FixedMortgage.maximumMortgageNoCondo, 1000000) / insuranceMaxRatio * 100;
			        	double downpay5percentHouse = neededDownPayment(maxPropertyAmountHouse);
			        	double largestDownpaymentHouse = Math.max(downpay5percentHouse, FixedMortgage.clientOpportunity.downpaymentAmount);
			        	double fixedMortHouseNoDebtRepayValue = largestDownpaymentHouse + FixedMortgage.maximumMortgageNoCondo;
			        	double fixedMortHouseNoDebtRepayLTV = FixedMortgage.maximumMortgageNoCondo / fixedMortHouseNoDebtRepayValue * 100;
			        	double fixedMortHouseNoDebtRepayThresh = insuranceToThreshold(fixedMortHouseNoDebtRepayLTV, fixedMortHouseNoDebtRepayValue);
			        	
			        	//System.out.println("info from here to  get the data : ");
			        	if (fixedMortHouseNoDebtRepayThresh == 0){
				        	//System.out.println("info from here to  get the data inside  : ");
			        		
			        		//maximumTable.append("Fixed Mortgage (House)" + "\t" + "0"  + "\t" +  Math.round(largestDownpaymentHouse)  + "\t" + Math.round(FixedMortgage.maximumMortgageNoCondo)  + "\t" + Math.round(FixedMortgage.insureAmountMaxMortgage)  + "\t" + Math.round(fixedMortHouseNoDebtRepayValue)  + "\n");
			        		//maximumTableJson.put("fixedMortgage(House)","0"  + "\t" + Math.round(largestDownpaymentHouse)  + "\t" + Math.round(FixedMortgage.maximumMortgageNoCondo)  + "\t" + Math.round(FixedMortgage.insureAmountMaxMortgage)  + "\t" + Math.round(fixedMortHouseNoDebtRepayValue))	;
			        	
			        		 maximumTableJson.put("fixedMortgageHouse", maximumTable123);
			        		 maximumTable123.put("largestDownpaymentHouse", Math.round(largestDownpaymentHouse));
			        		 maximumTable123.put("maximumMortgageNoCondo", Math.round(FixedMortgage.maximumMortgageNoCondo));
			        		 maximumTable123.put("insureAmountMaxMortgage", Math.round(FixedMortgage.insureAmountMaxMortgage));
			        		 maximumTable123.put("fixedMortHouseNoDebtRepayValue", Math.round(fixedMortHouseNoDebtRepayValue));
				        			
			        	}
			        	else{
			        		double newMortgageAmount = fixedMortHouseNoDebtRepayValue - (fixedMortHouseNoDebtRepayThresh + 10);
			        		double newLTV = newMortgageAmount / fixedMortHouseNoDebtRepayValue * 100;
			        		//InsurerProducts insureProd = new InsurerProducts(VariableMortgage);
			        		InsurerProducts insureProd = new InsurerProducts(this, newMortgageAmount, VariableMortgage.amortization);
					        double newInsuranceAmount = InsurancePremiums.calculateInsurancePremium(insureProd);
			        		//double newInsuranceAmount = InsurancePremiums.calculateInsurancePremium(false, newLTV, VariableMortgage.hasSelfEmployedIncome, newMortgageAmount, 0, false);
			        	
			        		
					        //maximumTable.append("- Fixed Mortgage (House)" + "\t" + "0"  + "\t" +  Math.round(largestDownpaymentHouse)  + "\t" + Math.round(FixedMortgage.maximumMortgageNoCondo)  + "\t" + Math.round(FixedMortgage.insureAmountMaxMortgage)  + "\u00B5" + "\t" + Math.round(fixedMortHouseNoDebtRepayValue)  + "\n");
					       // maximumTableJson.put("fixedMortgage(House)","0"  + "\t" + Math.round(largestDownpaymentHouse)  + "\t" + Math.round(FixedMortgage.maximumMortgageNoCondo)  + "\t" + Math.round(FixedMortgage.insureAmountMaxMortgage)  + "\t" + Math.round(fixedMortHouseNoDebtRepayValue))	;
					        maximumTableJson.put("fixedMortgageHouse", "0");
			        		maximumTableJson.put("largestDownpaymentHouse", Math.round(largestDownpaymentHouse));
			        		maximumTableJson.put("maximumMortgageNoCondo", Math.round(FixedMortgage.maximumMortgageNoCondo));
			        		maximumTableJson.put("insureAmountMaxMortgage", Math.round(FixedMortgage.insureAmountMaxMortgage));
			        		maximumTableJson.put("fixedMortHouseNoDebtRepayValue", Math.round(fixedMortHouseNoDebtRepayValue));
			        			
					        
					        String reduceIns = "In this maximum mortgage scenario, please please note that the insurance amount to CMHC / GE could be " +
			        				"reduced from $" + Math.round(FixedMortgage.insureAmountMaxMortgage) + " to $" + Math.round(newInsuranceAmount) + " if you were able to " +
			        						"increase your downpayment and reduce the maximum mortgage amount by $" + Math.round(fixedMortHouseNoDebtRepayThresh) + "\n";
			        		notes.append(reduceIns);
			        	}
			        	
			        	// Debt Repayment makes no sense if it means reducing Downpayment with a 5% down Property ... 
//                	double fixedMortHouseDebtRepayValue = FixedMortgage.downPaymentWithDebtReduce + FixedMortgage.maximumMortgageNoCondo;
//                	double fixedMortHouseDebtRepayLTV = FixedMortgage.maximumMortgageNoCondoPlusDebtReduce / fixedMortHouseDebtRepayValue * 100;
//                	double fixedMortHouseDebtRepayThresh = insuranceToThreshold(fixedMortHouseDebtRepayLTV, fixedMortHouseDebtRepayValue);
//                	if (fixedMortHouseDebtRepayThresh == 0){
//                		maximumTable.append("Fixed Mortgage (House)" + "\t" + Math.round(FixedMortgage.totalDebtRepaid)  + "\t" +  Math.round(FixedMortgage.downPaymentWithDebtReduce)  + "\t" + Math.round(FixedMortgage.maximumMortgageNoCondoPlusDebtReduce)  + "\t" + Math.round(FixedMortgage.insureAmountMaxMortgagePlusDebtReduce)  + "\t" + Math.round(fixedMortHouseDebtRepayValue)  + "\n");	
//                	}
//                	else{
//                		double newMortgageAmount = fixedMortHouseDebtRepayValue - (fixedMortHouseDebtRepayThresh + 10);
//                		double newLTV = newMortgageAmount / fixedMortHouseDebtRepayValue * 100;
//                		double newInsuranceAmount = InsurancePremiums.calculateInsurancePremium(false, newLTV, VariableMortgage.hasSelfEmployedIncome, newMortgageAmount, 0, false);
//                	
//                		maximumTable.append("6. Fixed Mortgage (House)" + "\t" + Math.round(FixedMortgage.totalDebtRepaid)  + "\t" +  Math.round(FixedMortgage.downPaymentWithDebtReduce)  + "\t" + Math.round(FixedMortgage.maximumMortgageNoCondoPlusDebtReduce)  + "\t" + Math.round(FixedMortgage.insureAmountMaxMortgagePlusDebtReduce)  + "\u00B6" + "\t" + Math.round(fixedMortHouseDebtRepayValue)  + "\n");
//                		String reduceIns = "In this maximum mortgage scenario, please please note that the insurance amount to CMHC / GE could be " +
//                				"reduced from $" + Math.round(FixedMortgage.insureAmountMaxMortgage) + " to $" + Math.round(newInsuranceAmount) + " if you were able to " +
//                						"increase your downpayment and reduce the maximum mortgage amount by $" + Math.round(fixedMortHouseDebtRepayThresh) + "\n";
//                		notes.append(reduceIns);
//                	}
			        		
			        	// Condo
			        	double maxPropertyAmountCondo = Math.min(FixedMortgage.maximumMortgageCondo, 1000000) / insuranceMaxRatio * 100;
			        	double downpay5percentCondo = neededDownPayment(maxPropertyAmountCondo);
			        	double largestDownpaymentCondo = Math.max(downpay5percentCondo, FixedMortgage.clientOpportunity.downpaymentAmount);
			        	double fixedMortCondoNoDebtRepayValue = largestDownpaymentCondo + FixedMortgage.maximumMortgageCondo;
			        	double fixedMortCondoNoDebtRepayLTV = FixedMortgage.maximumMortgageCondo / fixedMortCondoNoDebtRepayValue * 100;
			        	double fixedMortCondoNoDebtRepayThresh = insuranceToThreshold(fixedMortCondoNoDebtRepayLTV, fixedMortCondoNoDebtRepayValue);
			        
			        //	System.out.println("info from here to  get the data 2 : ");
			        	if (fixedMortCondoNoDebtRepayThresh == 0){
			        		  logger.info("info from here to  get the data inside 2 : ");
			        		//maximumTable.append("Fixed Mortgage (Condo)" + "\t" + "0"  + "\t" +  Math.round(largestDownpaymentCondo)  + "\t" + Math.round(FixedMortgage.maximumMortgageCondo)  + "\t" + Math.round(FixedMortgage.insureAmountMaxMortgageWithCondo)  + "\t" + Math.round(fixedMortCondoNoDebtRepayValue)  + "\n");		
			        		//maximumTableJson.put("fixedMortgage(House)","0"  + "\t" + Math.round(largestDownpaymentHouse)  + "\t" + Math.round(FixedMortgage.maximumMortgageNoCondo)  + "\t" + Math.round(FixedMortgage.insureAmountMaxMortgage)  + "\t" + Math.round(fixedMortCondoNoDebtRepayValue))	;
			        		  maximumTableJson.put("FixedMortgageCondo", maximumTable123);
			        		  maximumTable123.put("largestDownpaymentHouse", Math.round(largestDownpaymentHouse));
			        		  maximumTable123.put("maximumMortgageNoCondo", Math.round(FixedMortgage.maximumMortgageNoCondo));
			        		  maximumTable123.put("insureAmountMaxMortgage", Math.round(FixedMortgage.insureAmountMaxMortgage));
			        		  maximumTable123.put("fixedMortHouseNoDebtRepayValue", Math.round(fixedMortHouseNoDebtRepayValue));
				        			
			        	}
			        	else{
			        		double newMortgageAmount = fixedMortCondoNoDebtRepayValue - (fixedMortCondoNoDebtRepayThresh + 10);
			        		double newLTV = newMortgageAmount / fixedMortCondoNoDebtRepayValue * 100;
			        		//InsurerProducts insureProd = new InsurerProducts(VariableMortgage);
			        		InsurerProducts insureProd = new InsurerProducts(this, newMortgageAmount, VariableMortgage.amortization);
					        double newInsuranceAmount = InsurancePremiums.calculateInsurancePremium(insureProd);
			        		//double newInsuranceAmount = InsurancePremiums.calculateInsurancePremium(false, newLTV, VariableMortgage.hasSelfEmployedIncome, newMortgageAmount, 0, false);
					        logger.info("info from here to  get the data 3 : ");
			        	//	maximumTable.append("Fixed Mortgage (Condo)" + "\t" + "0"  + "\t" +  Math.round(largestDownpaymentCondo)  + "\t" + Math.round(FixedMortgage.maximumMortgageCondo)  + "\t" + Math.round(FixedMortgage.insureAmountMaxMortgageWithCondo)  + "\u00B7" + "\t" + Math.round(fixedMortCondoNoDebtRepayValue)  + "\n");
			        		//maximumTableJson.put("fixedMortgage(House)","0"  + "\t" + Math.round(largestDownpaymentHouse)  + "\t" + Math.round(FixedMortgage.maximumMortgageNoCondo)  + "\t" + Math.round(FixedMortgage.insureAmountMaxMortgage)  + "\t" + Math.round(fixedMortCondoNoDebtRepayValue))	;
					        maximumTableJson.put("FixedMortgage(Condo)",maximumTable123);
					        maximumTable123.put("largestDownpaymentHouse", Math.round(largestDownpaymentHouse));
					        maximumTable123.put("maximumMortgageNoCondo", Math.round(FixedMortgage.maximumMortgageNoCondo));
					        maximumTable123.put("insureAmountMaxMortgage", Math.round(FixedMortgage.insureAmountMaxMortgage));
					        maximumTable123.put("fixedMortHouseNoDebtRepayValue", Math.round(fixedMortHouseNoDebtRepayValue));
			        			
			        		String reduceIns = "- In this maximum mortgage scenario, please please note that the insurance amount to CMHC / GE could be " +
			        				"reduced from $" + Math.round(FixedMortgage.insureAmountMaxMortgage) + " to $" + Math.round(newInsuranceAmount) + " if you were able to " +
			        						"increase your downpayment and reduce the maximum mortgage amount by $" + Math.round(fixedMortCondoNoDebtRepayThresh) + "\n";
			        		notes.append(reduceIns);
			        	}
			        	
			        	// Debt Repayment makes no sense if it means reducing Downpayment with a 5% down Property ... 
//                	double fixedMortCondoDebtRepayValue = FixedMortgage.downPaymentWithDebtReduce + FixedMortgage.maximumMortgageNoCondo;
//                	double fixedMortCondoDebtRepayLTV = FixedMortgage.maxMortgageWithCondoPlusDebtReduce / fixedMortCondoDebtRepayValue * 100;
//                	double fixedMortCondoDebtRepayThresh = insuranceToThreshold(fixedMortCondoDebtRepayLTV, fixedMortCondoDebtRepayValue);
//                	if (fixedMortCondoDebtRepayThresh == 0){
//                		maximumTable.append("Fixed Mortgage (Condo)" + "\t" + Math.round(FixedMortgage.totalDebtRepaid)  + "\t" +  Math.round(FixedMortgage.downPaymentWithDebtReduce)  + "\t" + Math.round(FixedMortgage.maxMortgageWithCondoPlusDebtReduce)  + "\t" + Math.round(FixedMortgage.insureAmountMaxMortgageWithCondoPlusDebtReduce)  + "\t" + Math.round(fixedMortCondoDebtRepayValue)  + "\n");		
//                	}
//                	else{
//                		double newMortgageAmount = fixedMortCondoDebtRepayValue - (fixedMortCondoDebtRepayThresh + 10);
//                		double newLTV = newMortgageAmount / fixedMortCondoDebtRepayValue * 100;
//                		double newInsuranceAmount = InsurancePremiums.calculateInsurancePremium(false, newLTV, VariableMortgage.hasSelfEmployedIncome, newMortgageAmount, 0, false);
//                	
//                		maximumTable.append("8. Fixed Mortgage (Condo)" + "\t" + Math.round(FixedMortgage.totalDebtRepaid)  + "\t" +  Math.round(FixedMortgage.downPaymentWithDebtReduce)  + "\t" + Math.round(FixedMortgage.maxMortgageWithCondoPlusDebtReduce)  + "\t" + Math.round(FixedMortgage.insureAmountMaxMortgageWithCondoPlusDebtReduce)  + "\u00B8" + "\t" + Math.round(fixedMortCondoDebtRepayValue)  + "\n");
//                		String reduceIns = "In this maximum mortgage scenario, please please note that the insurance amount to CMHC / GE could be " +
//                				"reduced from $" + Math.round(FixedMortgage.insureAmountMaxMortgage) + " to $" + Math.round(newInsuranceAmount) + " if you were able to " +
//                						"increase your downpayment and reduce the maximum mortgage amount by $" + Math.round(fixedMortCondoDebtRepayThresh) + "\n";
//                		notes.append(reduceIns);
//                	}
			        }
			        logger.info("info from here to  get the data 4 : ");
			        //new modify here
			       // String maximumTableFinal = maximumTable.toString();
			        String maximumTableFinal = maximumTableJson.toString();
			        try{
			        	  logger.info("###########MaximiumTable  Final#######3"+maximumTableFinal);
			        }catch(Exception e){
			        	
			        }
			        
			        AlgoNote maximumTableNote = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.High,maximumTableFinal,"MaxMortgageTable");
			        marketingTemplateNotes.add(maximumTableNote);
			        
			        String maxMortgageConclusion = "To give you an idea of the some of the best mortgages " +
			        		"which you pre-qualify for, we have also provided core details for an additional " + Recommendations.size() + " mortgages.  " +
			        		"You will find these listed in the tables below.  On behalf of Visdom - Happy House Hunting.";
			        AlgoNote maxMortgageConcludeNote = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.High,maxMortgageConclusion,"MaxMortgageConclude");
			        marketingTemplateNotes.add(maxMortgageConcludeNote);
			    
			        // VariableRecommendations
			        List<AlgoProduct> variableProducts = getAllProductsByType("2");
			        String variableRecommendString = "The below table represents the best "
			        		+ countVariableRecommendations + " qualifying options* from the " + variableProducts.size() + " variable rate mortgages presently available through Visdom's Lender Network.";
			        AlgoNote variabeRecommendNote = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.High,variableRecommendString,"VariableRecommendations");
			        marketingTemplateNotes.add(variabeRecommendNote);
			        
			        // FixedRecommendations
			        List<AlgoProduct> fixedProducts = getAllProductsByType("3");
			        String fixedRecommendString = "The below table represents the best "
			        		+ countFixedRecommendations + " qualifying options* from the " + fixedProducts.size() + " fixed rate mortgages presently available through Visdom's Lender Network.";
			        AlgoNote fixedRecommendNote = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.High,fixedRecommendString,"FixedRecommendations");
			        marketingTemplateNotes.add(fixedRecommendNote);
			        
			        // LOCRecommendations
			        List<AlgoProduct> locProducts = getAllProductsByType("1");
			        String locRecommendString = "The below table represents the best "
			        		+ countLOCRecommendations + " qualifying options* from the " + locProducts.size() + " Home Equity Line of Credit presently available through Visdom's Lender Network.";
			        AlgoNote locRecommendNote = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.High,locRecommendString,"LOCRecommendations");
			        marketingTemplateNotes.add(locRecommendNote);
			        
			        
			        String disclosure1 = "- This preapproval will be based on the lenders and insurers acceptance of all supporting documents and property." + "\n";
	        		notes.append(disclosure1);
	        		String disclosure2 = "- This preapproval is based on your current credit and employment, if there are any changes or adjustments please notify us as soon as possible." + "\n";
	        		notes.append(disclosure2);
	        		String disclosure3 = "- A variety of rates/products have been saved for you and when you find a property we will offer you the lenders who will offer you the best rate/product." + "\n";
	        		notes.append(disclosure3);
	        		String disclosure4 = "- Between now and when you purchase your home, you may be able to qualify for a larger mortgage in the event any of the following things occur:"
	        				+ " a) pay down some debts b) increase your income, c) increase your downpayment.  In order to understand which of these possibilities might be most "
	        				+ "advantageous for you, please talk with our broker team at at 1 (855) 699-2400." + "\n";
	        		notes.append(disclosure4);
	        		String disclosure5 = "- This pre-approval will expiry in 120 days from application date." + "\n";
	        		notes.append(disclosure5);
			        AlgoNote footNote = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.High,notes.toString(),"Notes");
			        marketingTemplateNotes.add(footNote);
			        
			        
			        
			        Set<MarketingNotes>  setMarketingNotes=new HashSet<MarketingNotes>();
			        logger.info("Marketing  Template Size"+marketingTemplateNotes.size());
				    for (AlgoNote marketingNote : marketingTemplateNotes) {
						MarketingNotes note = new MarketingNotes();
						note.setNoteName(marketingNote.marketingField);
						note.setNoteContent(marketingNote.description);
						
						
						
						
						
						
						
						try{
					setMarketingNotes.add(note);
				allProductAlgoJSON.setMarketingNotes(setMarketingNotes);
						}catch (Exception e) {
							  logger.error("setMarketingNotes  Not  added"+e);
						}
						
						
						
						//System.out.println("****************For  Couchbase Executor  Proposals*********");
						
						/*ExecutorService exs = Executors.newFixedThreadPool(4);
						CouchbaseExecutorProposal executorserviceproposalJSON=null;
					    try{

					    	executorserviceproposalJSON= new CouchbaseExecutorProposal(proposalSummaryJson); 
					    exs.submit(executorserviceproposalJSON);
					    
					    exs.shutdown();
					    }catch (Exception e) {
					    	logger.error("Couchbase  connection  ShutDown "+e);
				}*/
						
						//allProductAlgoJSON.getMarketingNotes().add(note);
					}
			        
			        
			    }
			    else{
			    	//  The following characteristics of this mortgage may help you consider this recommendation: 
			    	//  Cost Comparison:  This recommendation is $157 / month less expensive than a 5 year fixed mortgage.
			    	//  Payout Risk Comparison:  This recommendation has a maximum payout penalty of 3 months of interest (currently $4,327) 
			    	//  as opposed to a Fixed Mortgage which could have a payout penalty of as much as $12,543.
			    	
			    	StringBuilder comparisionDetails = new StringBuilder();
				    double costDifference = 0;	
						
					double rateForDifferential = max5YrFixedDifferential / 100;
					double projectedRemainingMonths = 48;
					double projectedPenalty = Math.round((Recommend5YearFixed.mortaggeAmout * rateForDifferential * projectedRemainingMonths) / 12);
					
					// 3 month interest 
					double oneMonthsInterest = 0;
					double threeMonthInterest = 0;
					if (recommendationType!=null && recommendationType.contains("Single")){
						if (currentRecommend!=null && currentRecommend != null){			    		
							if (Recommend5YearFixed != null){
								if (Recommend5YearFixed.rate > currentRecommend.interestRate){
									costDifference = Math.round(Recommend5YearFixed.payment - currentRecommend.expectedFitnessPayment);
									String costCompareDetails = "Cost Comparison:  This recommendation is $" + Math.round(costDifference) + " / month less expensive than a 5 year fixed mortgage." + "\n";
									comparisionDetails.append(costCompareDetails);
								}
								oneMonthsInterest = ((currentRecommend.interestRate / 100) * currentRecommend.expectedMortgageAmount) / 12;
								threeMonthInterest = Math.round(oneMonthsInterest * 3);
				    		}
				    	}
				    	else{
				    		// If there is no 5 year fixed do we still want a comparision of any sort? 
				    	}
					}
					else  if (recommendationType!=null  && recommendationType.contains("Combined")){
						if (bestCombined != null){			    		
							if (Recommend5YearFixed != null){
								if (Recommend5YearFixed.rate > bestCombined.underwriteCombined.interestRate){
									costDifference = Math.round(Recommend5YearFixed.payment - bestCombined.underwriteCombined.expectedFitnessPayment);
									String costCompareDetails = "Cost Comparison:  This recommendation is $" + Math.round(costDifference) + " / month less expensive than a 5 year fixed mortgage." + "\n";
									comparisionDetails.append(costCompareDetails);
								}
								oneMonthsInterest = ((bestCombined.underwriteCombined.interestRate / 100) * currentRecommend.expectedMortgageAmount) / 12;
								threeMonthInterest = Math.round(oneMonthsInterest * 3);
				    		}
				    	}
				    	else{
				    		// If there is no 5 year fixed do we still want a comparision of any sort? 
				    	}
					}
					
					String payoutCompareDetails = null;					
					if (recommendationType!=null && recommendationType.contains("Single")){
						if (mortgageTypeString(currentRecommend.potentialProduct.mortgageType).contains("Variable")){
						 payoutCompareDetails = "Payout Risk Comparison:  This recommendation has a maximum payout penalty of 3 months of interest " +
								"(currently approximately $" + Math.round(threeMonthInterest) + ").  In contrast, a 5 Year Fixed Mortgage from some lenders " +
								"could have a payout penalty as high as $" + Math.round(projectedPenalty) + "." + "\n";							
						}
						else if (mortgageTypeString(currentRecommend.potentialProduct.mortgageType).contains("Fixed")){            		
							 payoutCompareDetails = "Payout Risk Comparison:  Please note this type of mortgage may have a much higher payout penalty (than a variable mortgage or line of credit) " +
									"The amount of the penalty cannot be calculated until the mortgage is terminated (e.g. as a result of you selling this property a year form now), " +
									"however, with some lenders it may range as high as approximately $" + Math.round(projectedPenalty) + "\n";							
						}
						else if (mortgageTypeString(currentRecommend.potentialProduct.mortgageType).contains("LOC")){            		
							 payoutCompareDetails = "Payout Risk Comparison:  Please note that most LOCs we recommend have no payout penalty.  " +
									"In contrast, a typical variable mortgage would have a potnetial payout penalty of $" + Math.round(threeMonthInterest) + " and" +
									"a 5 Year Fixed Mortgage from some lenders could range as high as  approximately $" + Math.round(projectedPenalty) + "\n";
						}
						else{
							 payoutCompareDetails = "Payout Risk Comparison:  Please discuss the risk of payout penalties with your broker." + "\n";
						}
						comparisionDetails.append(payoutCompareDetails);
					}
					else  if (recommendationType!=null  && recommendationType.contains("Combined")){						
						payoutCompareDetails = "Payout Risk Comparison:  Please note that this combined mortgage recommendation"
								+ " has a lower potential payout penalty than a typical 5 year fixed mortgages.  "
								+ "This is especially advantageous if you refinance or sell your home before the end of the term. \n";
						comparisionDetails.append(payoutCompareDetails);
					}					
					comparisionDetails.append(payoutCompareDetails);
					String finishedCompareString = comparisionDetails.toString();
					AlgoNote compareNote = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.High,finishedCompareString,"CompareDetails");
					marketingTemplateNotes.add(compareNote);
			    	
			    	

					// This logic needs review ... I think problem might be a missing product in the subtraction ... Test With Bob Hope.
					if (recommendationType!=null && recommendationType.contains("Single")){
						if (currentRecommend != null){
				    		double rateSpreadFixed5toRecommend = Recommend5YearFixed.rate - currentRecommend.interestRate;
					    	if (rateSpreadFixed5toRecommend > 0){
					    		double total3YrRateSpread = rateSpreadFixed5toRecommend * 3;
					    		double rateIncreaseToCostMore = (total3YrRateSpread / 2) + rateSpreadFixed5toRecommend;    		
					    		AlgoNote note5 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Interest rates would need to rise by more than approximately " + roundedNumber(rateIncreaseToCostMore)  + "% over the next 2 years before a 5 Year Fixed mortgage would save you any money.", "VarVsFixedAnalysis");
					    		marketingTemplateNotes.add(note5);
					    		// TODO Test and confirm this calculation. 
					    	}	
				    	}
					}
					else  if (recommendationType!=null && recommendationType.contains("Combined")){
						if (currentRecommend != null){
				    		double rateSpreadFixed5toRecommend = Recommend5YearFixed.rate - bestCombined.underwriteCombined.interestRate;
					    	if (rateSpreadFixed5toRecommend > 0){
					    		double total3YrRateSpread = rateSpreadFixed5toRecommend * 3;
					    		double rateIncreaseToCostMore = (total3YrRateSpread / 2) + rateSpreadFixed5toRecommend;					    							    		
					    		AlgoNote note5 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "Interest rates would need to rise by more than approximately " + roundedNumber(rateIncreaseToCostMore)  + "% over the next 2 years before a 5 Year Fixed mortgage would save you any money.", "VarVsFixedAnalysis");
					    		marketingTemplateNotes.add(note5);
					    		// TODO Test and confirm this calculation. 
					    	}	
				    	}
					}
				    	
					// VariableRecommendations
					List<AlgoProduct> variableProducts = getAllProductsByType("2");
					String variableRecommendString = "The below table represents the best "
							+ countVariableRecommendations + " qualifying options* from the " + variableProducts.size() + " variable rate mortgages presently available through Visdom's Lender Network.";
					AlgoNote variabeRecommendNote = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.High,variableRecommendString,"VariableRecommendations");
					marketingTemplateNotes.add(variabeRecommendNote);

					// FixedRecommendations
					List<AlgoProduct> fixedProducts = getAllProductsByType("3");
					String fixedRecommendString = "The below table represents the best "
							+ countFixedRecommendations + " qualifying options* from the " + fixedProducts.size() + " fixed rate mortgages presently available through Visdom's Lender Network.";
					AlgoNote fixedRecommendNote = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.High,fixedRecommendString,"FixedRecommendations");
					marketingTemplateNotes.add(fixedRecommendNote);

					// LOCRecommendations
					List<AlgoProduct> locProducts = getAllProductsByType("1");
					String locRecommendString = "The below table represents the best "
							+ countLOCRecommendations + " qualifying options* from the " + locProducts.size() + " Home Equity Line of Credit presently available through Visdom's Lender Network.";
					AlgoNote locRecommendNote = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.High,locRecommendString,"LOCRecommendations");
					marketingTemplateNotes.add(locRecommendNote);
				        
					
					if (recommendationType!=null && recommendationType.contains("Single")){
						if (currentRecommend != null){
				        	if (clientOpportunity.desiredAmortization < currentRecommend.amortization){
					        	String shortAmortization = "- In this proposal we amortized your mortgage over " + clientOpportunity.amortization + " years. You originally indicated you "
					        			+ "wanted a mortgage amortized over " + clientOpportunity.desiredAmortization  + " years, however, this creates a higher "
			        					+ "payment with little flexibility to adjust it downward if circumstances change.  By amortizing over " + currentRecommend.amortization + " years"
			        					+ "you have a lower payment and are still able to pay your mortgage off faster with accelerated payments."
			        					+ "Ultimately, the longer amortization provides you more financial flexibility." + "\n";
					        	notes.append(shortAmortization);
					        }	
				        }
					}
				   else  if (recommendationType!=null && recommendationType.contains("Combined")){
					   if (bestCombined != null){
				        	try {
				        		String shortAmortization = "- In this proposal we amortized your mortgage over " + clientOpportunity.amortization + " years. You originally indicated you "
										+ "wanted a mortgage amortized over " + clientOpportunity.desiredAmortization  + " years, however, this creates a higher "
										+ "payment with little flexibility to adjust it downward if circumstances change.  By utilizing a combined product with "
										+ "amortizations of " + bestCombined.getBaseAmortization() + " years and " + bestCombined.getAdditionalAmortization() + " years "
										+ "you have a lower payment and are still able to pay your mortgage off faster with accelerated payments."
										+ "Ultimately, the longer amortization provides you more financial flexibility." + "\n";
								notes.append(shortAmortization);
							} catch (Exception e) {
								e.printStackTrace();
							}	
				        }
					}
					 
					 
				        
				        
//				        else if (clientOpportunity.amortization > 25){
//				        	String longAmortization = "- In this proposal we amortized your mortgage over "+ clientOpportunity.amortization + " years. You originally indicated you "
//				        			+ "wanted a mortgage amortized over " + clientOpportunity.desiredAmortization  + " years, however, most mortgages "
//		        					+ "of with this longer amortization have a higher interest rate.  In order to reduce your interest rate, in this "
//		        					+ "proposal we amortized the mortgage over 25 years.  In the event you need a longer amortization, please discuss "
//		        					+ "this with your Visdom Mortgage Specialist." + "\n";
//				        	notes.append(longAmortization);
//				        }
				        
				        
					String disclosure = "- Please note that this proposal may contain forward-looking statements and Visdom does not assume any obligation "
							+ "to update forward-looking statements if management beliefs, expectations or opinions should change due to reasons including "
							+ "interest rate changes, lender product changes, time delays, lender acceptance or any other reason." + "\n";
					notes.append(disclosure);
					AlgoNote footNote = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.High,notes.toString(),"Notes");
					marketingTemplateNotes.add(footNote);
			    	
//			    	else{
//			    		Task task2 = new Task("There were no recommendations for this Opportunity ... Please review Failed Deal Reasons", 
//                        		"",
//        				"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
//        	            assistantTasks.add(task2); 
//			    	}
			    	
			    	
//			    	StringBuilder ratiosDetails2 = new StringBuilder();
//				    if (currentRecommend != null){
//				    	ratiosDetails2.append("GDS: " + currentRecommend.GDSRatio + "\n");
//					    ratiosDetails2.append("TDS: " + currentRecommend.TDSRatio);	
//				    }
//				    else{
//				    	double lowestRate1 = 100;
//				    	UnderwriteAll lowestRate5Year = null;
//				    	GenericComparator orderbyInterestRate2 = Util.newGenericComparator("interestRate","asc"); 
//				    	Collections.sort(failedFixed5YrProducts, orderbyInterestRate2);
//				    	lowestRate5Year = failedFixed5YrProducts.get(0);
//				    	
//				    	ratiosDetails2.append(lowestRate5Year.potentialProduct.name + " - ");
//				    	ratiosDetails2.append("GDS: " + lowestRate5Year.GDSRatio + "\n");
//					    ratiosDetails2.append("TDS: " + lowestRate5Year.TDSRatio);
//					    
//					    StringBuilder failurestring = new StringBuilder();
//						for (String reason : lowestRate5Year.failReasons) {
//							failurestring.append(reason + ", ");
//							System.out.println( "Failure Reasons:" + failurestring);
//						}
//					    
//					    AlgoNote failureNote = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.High,"Failure Reasons: " + failurestring, "failureReasons3");
//					    marketingTemplateNotes.add(failureNote);
//				    }
//				    AlgoNote ratiosNote2 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.High,ratiosDetails2.toString(),"GDS - TDS");
//				    marketingTemplateNotes.add(ratiosNote2);
				    
			    }
			   
			    // Save Underwrite in Recommendation. 
			   
			    if (recommendationType!=null && recommendationType.contains("Single")){
			    	if (currentRecommend != null){
			    		productRecommended = currentRecommend;   
			    		algoClientOpportunity.allApplicantsTotalIncomes = productRecommended.allApplicantsTotalIncomes;
			    		clientOpportunity.GDS = productRecommended.GDSRatio;
			    		clientOpportunity.TDS = productRecommended.TDSRatio;
			    		clientOpportunity.ltv = productRecommended.LTV;				    	
			    	}
			    }
			    else  if (recommendationType!=null && recommendationType.contains("Combined")){
			    	algoClientOpportunity.allApplicantsTotalIncomes = bestCombined.underwriteCombined.allApplicantsTotalIncomes;
		    		clientOpportunity.GDS = bestCombined.underwriteCombined.GDSRatio;
		    		clientOpportunity.TDS = bestCombined.underwriteCombined.TDSRatio;
		    		clientOpportunity.ltv = bestCombined.underwriteCombined.LTV;	
			    }
			    
			    
			    
			 // TODO This Areas notes need to be reviewed with Kendall to Determine what we want to Do / Say In Assistant Tasks
			    // Qualification
			    // Determine if GDS or TDS is "borderline"
			    boolean borderlineRatios = false;
			    boolean borderlineCredit = false;
			    if (acceptableVariable5YrProducts.size() <= 1
			        || (acceptableFixed5YrProducts.size() > 0 && acceptableFixed5YrProducts.get(0).TDSRatio > 41.5)
			        || (acceptableFixed5YrProducts.size() > 0 && acceptableFixed5YrProducts.get(0).GDSRatio > 31.5))
			        borderlineRatios = true;
			    
			    if (acceptableFixed5YrProducts.size() > 0 && acceptableFixed5YrProducts.get(0).OpportunityCreditCategory == AlgoApplicant.CreditCategory.Poor)
			        borderlineCredit = true;
			    
			    // Removed else Statement - All these notes should be added into all opportunities including Pre-Qualification
			    if (borderlineRatios == true)
			    {
			    	Task task2 = new Task("Please note, the client is very close to exceeding the maximum TDS or GDS ratio.", 
							"You mmay want to encourage them to reduce any debt possibe",
					"Broker", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
			        brokerTasks.add(task2);  
			    }
			    if (borderlineCredit == true)
			    {
			    	Task task2 = new Task("Please note, the client has poor credit.", 
							"",
					"Broker", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
			        brokerTasks.add(task2);    
			    }
			    
			                
			    
			               
			    // Check for removal of Applicants with weak credit. 
			    // Test if someone is poor credit and had 70% of the income ... Message that probably higher, and considering adding applicant
			    if (recommendationType!=null && recommendationType.contains("Single")){
			    	if (clientOpportunity.applicants.size() > 1 && productRecommended != null)
				    {
				        int counter = 0;
				        for (Applicant applicant : clientOpportunity.applicants){
				        	AlgoApplicant algoApplicant = new AlgoApplicant(applicant);
				            if (clientOpportunity.applicants.get(counter).totalIncome / algoClientOpportunity.allApplicantsTotalIncomes <= 0.3)
				            {
				                if (algoApplicant.applicantCreditCategory == AlgoApplicant.CreditCategory.Poor)
				                {
				                    double GDSwithoutApplicant = productRecommended.totalLiabilityPayments - algoApplicant.monthlyGDSLiabilities;
				                    double IncomeWithoutApplicant = algoClientOpportunity.allApplicantsTotalIncomes - applicant.totalIncome;
				                    double newGDSRatio = GDSwithoutApplicant / IncomeWithoutApplicant;
				                    double NewTDSAmount = GDSwithoutApplicant + (productRecommended.algoPotentialProduct.expectedPayment * 12);
				                    double newTDSRatio = NewTDSAmount / IncomeWithoutApplicant;
				                    if (newGDSRatio <= productRecommended.potentialProduct.poorGdsGreaterEqualSplit && newTDSRatio <= productRecommended.potentialProduct.poorTdsGreaterEqualSplit)
				                    {                                
				                        Task task2 = new Task("This Oppotunity may need to have the applicant " + applicant.applicantName + " removed from the Opportunity.", 
				                        		"Please check with the Broker to determine whether this opportunity should have an applicant removed to strengthen the application."
				                        		+ "The GDS Ratio with " + applicant.applicantName + " is " + clientOpportunity.GDS + "%.  And TDS Ratio is " + clientOpportunity.GDS + "%." + 
				                        				"If " + applicant.applicantName + " is removed from the Opportunity, the New GDS Ratio will be " + newGDSRatio + "%.  And the new TDS Ratio will be " + newTDSRatio + "%.",
				        				"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
				                        assistantTasks.add(task2); 
				                    }
				                }
				            }
				        }
				    }
			    }
			    else  if (recommendationType!=null  && recommendationType.contains("Combined")){
			    	if (clientOpportunity.applicants.size() > 1 && bestCombined != null)
				    {
				        int counter = 0;
				        for (Applicant applicant : clientOpportunity.applicants){
				        	AlgoApplicant algoApplicant = new AlgoApplicant(applicant);
				            if (clientOpportunity.applicants.get(counter).totalIncome / algoClientOpportunity.allApplicantsTotalIncomes <= 0.3)
				            {
				                if (algoApplicant.applicantCreditCategory == AlgoApplicant.CreditCategory.Poor)
				                {
				                    double GDSwithoutApplicant = bestCombined.underwriteCombined.totalLiabilityPayments - algoApplicant.monthlyGDSLiabilities;
				                    double IncomeWithoutApplicant = algoClientOpportunity.allApplicantsTotalIncomes - applicant.totalIncome;
				                    double newGDSRatio = GDSwithoutApplicant / IncomeWithoutApplicant;
				                    double NewTDSAmount = GDSwithoutApplicant + (bestCombined.underwriteCombined.expectedFitnessPayment * 12);
				                    double newTDSRatio = NewTDSAmount / IncomeWithoutApplicant;
				                    if (newGDSRatio <= bestCombined.underwriteCombined.potentialProduct.poorGdsGreaterEqualSplit 
				                    		&& newTDSRatio <= bestCombined.underwriteCombined.potentialProduct.poorTdsGreaterEqualSplit)
				                    {                                
				                        Task task2 = new Task("This Oppotunity may need to have the applicant " + applicant.applicantName + " removed from the Opportunity.", 
				                        		"Please check with the Broker to determine whether this opportunity should have an applicant removed to strengthen the application."
				                        		+ "The GDS Ratio with " + applicant.applicantName + " is " + clientOpportunity.GDS + "%.  And TDS Ratio is " + clientOpportunity.GDS + "%." + 
				                        				"If " + applicant.applicantName + " is removed from the Opportunity, the New GDS Ratio will be " + newGDSRatio + "%.  And the new TDS Ratio will be " + newTDSRatio + "%.",
				        				"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
				                        assistantTasks.add(task2); 
				                    }
				                }
				            }
				        }
				    }
			    }
			    
			    
			    
			    Task task4 = new Task("Please Review the Opportunity Recomendations and Deal Notes. If there are fewer than 5 Recommendations in the list or if the Deal notes to appear "
			    		+ "to be ready for the proposal, contact your manager to review this opportunity.  Otherwise, click the Proposal Stage", 
                		"",
				"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
                assistantTasks.add(task4); 

			    //#region Check if Recommended Product is "acceptable" in terms of interest rate
			    // Retrieve the "Best Rate" from the Product Database that is equivalent type and term of the recommended product
			    double minRate = lowestRate;
			    // double minRate = select interest_rate, min(interest_rate)from Products WHERE mortgage_type = RecommendedType AND term = RecommendedTerm

			    // TODO This needs a talk with Kendall around what we are trying to achieve here and how to best acheive it ... 
			    if (productRecommended != null){
			    	if (productRecommended.potentialProduct.interestRate - minRate > 0.2)
				    {
				        // We need Broker notes that provide rationale why client is not receiving a rate that is "cheapest".
				        AlgoNote NonOptimalRate = new AlgoNote(AlgoNote.TypeOfNote.BrokerAction, AlgoNote.Priority.Med, "Please note that the client does not qualify for absolute lowest rates in the market for the following reasons: ", "BrokerNote");
				        // Add the Note into OpenERP Broker Notes
				        StringBuilder nonOptimalString = new StringBuilder();
				        for (AlgoNote note : productRecommended.nonOptimalRateNotes)
				        {
				            nonOptimalString.append("- " + note.description + "\n");
				        }
				        if (productRecommended.potentialProduct.interestRate - minRate >= 0.5)
				        {
				            Task task2 = new Task("Due to the reasons listed in the description of this task, the rate this client qualifies for is higher than current lowest market rates.  Please manage the client's expectations around this.", 
									nonOptimalString.toString(),
							"Broker", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
				            brokerTasks.add(task2); 
				        }
				        
				    }
			    }
			    
			    // Check to see whether a 2nd mortgage may make more sense than an insured 1st ...
			    // Client seems to only qualify for a product that is substantially above current market rates.
			    // This may be due to Poor Credit, recent Debt Servicing problems, Prior Bankruptcy, or a Type of Property that carries more risk
			    // In some instances, it can make sense to Split the Opportunity into 2 Opportunities, a 1st Mortgage at an optimal rate and 2nd Mortgage at a higher rate
			    // The purpose of this code segment is to determine whether this may be feasible for this customer.
			    // This strategy will only work if the Loan To Value of the Opportunity is between 60 and 90%
			    
			    // 1) Find create a sublist from All Approved Products where Allowed on 2nd == true
			    // 2) Sort the List by interest rate descending
			    // 3) Take the top product
			    // 4) Put both through a decline curve until the remaining balance no longer requires insurance.
			    // 5) Total the interest costs on the 2nd for this period
			    // 6) Compare interest costs on 1st + 2nd to Insurance Cost.  
			    // 7) If less, create the note in the Opportunity and also a Broker task with explanation.  And also assistant task how to split the Opportunity. 
			    
			    // 1) Find create a sublist from All Approved Products where Allowed on 2nd == true
			    if (clientOpportunity1.highRatio == true){
			        List<UnderwriteAll> secondProducts = new ArrayList<UnderwriteAll>();
			      /*  for (List<UnderwriteAll> listOfSortedAcceptedProducts : listOfProductLists)*/
			        listOfProductLists.stream().parallel().forEach(
			        		listOfSortedAcceptedProducts ->
			        {
			        	
			        	
			        	for (UnderwriteAll underwrite : listOfSortedAcceptedProducts) {
							if (underwrite.potentialProduct.allowedOn2nd == true || underwrite.potentialProduct.allowedOn3rd == true){
								secondProducts.add(underwrite);
							}
						}
			      /*  }*/
			        
			    });
			        
			        // 2) Sort the List by interest rate descending
			        if (secondProducts.size() > 0 && productRecommended != null){
			        	GenericComparator order2ndbyInterestRate = Util.newGenericComparator("interestRate","asc"); // When infoging,need to ensure interest rate is set from Product into Underwrite
			            Collections.sort(secondProducts, order2ndbyInterestRate);
			            // 3) Take the top product
			            double lowestRate2nd = secondProducts.get(0).interestRate;
			            
			            // 4) Put both through a decline curve until the remaining balance no longer requires insurance.
			            // This is the heart of the calculation ... The Goal here is as Follows: 
			            // a) What are the interest costs of a first without the CMHC amount for the length of time until Insurance is not needed?
			            // b) What are the interest costs of a second for the rest of the borrowed amount for the length of time until Insurance is not needed?
			            // c) What are the interest costs of a first over the full amortization on a mortgage which is the size of the insurance costs (interest on the insurance that was added to the mortage)
			            // d) compare the a + b to insurance amount + c  ... if a + b is lower, write the notes and tasks to Broker, Assistant and Prorosal notes 
			            
			            // Calculate the interest costs including insurance until the Balance is < insurance threshold.
			            int amortizationYears = 25;
			            double originalMonths = amortizationYears * 12;
			    		double oringinalInterestPaid = 0;
			    		double originalBalanceWithInsurance = productRecommended.expectedMortgageAmount;
			    		double originalRemainingBalance = originalBalanceWithInsurance;
			    		double originalInsuranceAmount = productRecommended.insuranceAmount;
			    		double noInsuranceThreshold = productRecommended.insuredThresholdForProperty;
			    		int originalMonthsToNoInsurance = 0;
			    		for (int i = 0; i < originalMonths; i++) {
			    			double InterestOnlyPayment = CalculatePayments.calculateMonthlyLOCPayment(productRecommended.interestRate, originalRemainingBalance);
			    			double PrinciplePaid = productRecommended.expectedFitnessPayment - InterestOnlyPayment;
			    			originalRemainingBalance = originalRemainingBalance - PrinciplePaid;
			    			oringinalInterestPaid = oringinalInterestPaid + InterestOnlyPayment;
			    			if (originalRemainingBalance < noInsuranceThreshold){
			    				originalMonthsToNoInsurance = i;
			    				break;
			    			}
			    		}
			    		
			    		double firstInterestPaid = 0;
			    		double secondInterestPaid = 0;
			    		double firstBalanceNoInsurance = productRecommended.insuredThresholdForProperty;
			    		double firstRemainingBalance = firstBalanceNoInsurance;
			    		double secondBalanceNoInsurance = productRecommended.expectedMortgageAmount - originalInsuranceAmount - firstRemainingBalance;
			    		double secondRemainingBalance = secondBalanceNoInsurance;
			    		int monthsToNoInsurance = 0;
			    		double paymentForFirst = CalculatePayments.calculateMortgagePayment(productRecommended.interestRate, firstBalanceNoInsurance, amortizationYears, FrequencyDesired.Monthly);
			    		double paymentForSecond = CalculatePayments.calculateMortgagePayment(lowestRate2nd, secondBalanceNoInsurance, amortizationYears, FrequencyDesired.Monthly);
			    		
			    		for (int i = 0; i < originalMonths; i++) {
			    			double firstInterestOnlyPayment = CalculatePayments.calculateMonthlyLOCPayment(productRecommended.interestRate, firstRemainingBalance);
			    			double firstPrinciplePaid = paymentForFirst - firstInterestOnlyPayment;
			    			firstRemainingBalance = firstRemainingBalance - firstPrinciplePaid;
			    			firstInterestPaid = firstInterestPaid + firstInterestOnlyPayment;
			    			
			    			double secondInterestOnlyPayment = CalculatePayments.calculateMonthlyLOCPayment(lowestRate2nd, secondRemainingBalance);
			    			double secondPrinciplePaid = paymentForSecond - secondInterestOnlyPayment;
			    			secondRemainingBalance = secondRemainingBalance - secondPrinciplePaid;
			    			secondInterestPaid = secondInterestPaid + secondInterestOnlyPayment;
			    			
			    			if (firstRemainingBalance + secondRemainingBalance < noInsuranceThreshold){
			    				monthsToNoInsurance = i;
			    				break;
			    			}
			    		}
			    		
			    		// Compare interest costs on 1st + 2nd to Insurance Cost.  
			            // If less, create the note in the Opportunity and also a Broker task with explanation.  And also assistant task how to split the Opportunity.
			            if (firstInterestPaid + secondInterestPaid < oringinalInterestPaid){
			            	Task task2 = new Task("It is more cost effective for the client to have a first and second mortgage than it is to have only an insured first.  Please see Task Details.", 
							"An insured first mortgage of " + originalBalanceWithInsurance + " will cost the client $" + Math.round(oringinalInterestPaid) +
							" before the mortgage is reduced to $" + Math.round(noInsuranceThreshold) + " where the property no longer requires insurance. " +
							"In contrast, the insurace amount of $" + Math.round(originalInsuranceAmount) + " (and the interest costs on this amount) could be avoided "
							+ "by having a 1st Mortgage for $" + Math.round(firstBalanceNoInsurance) + " and a 2nd Mortgage for $" + Math.round(secondBalanceNoInsurance) +
							". If they take the insured 1st path, interest costs to the point where insurance is no longer needed (" + originalMonthsToNoInsurance + " months) "
							+ "would be $" + Math.round(oringinalInterestPaid) + ".  Alternatively, if they took the 1st + 2nd, it would be " + monthsToNoInsurance + " months"
							+ " until the balance of the 1st and the 2nds together are reduced to $" + Math.round(noInsuranceThreshold) + ". The amount of interest paid on "
							+ "the 1st would be $" + Math.round(firstInterestPaid) + " and the interest costs for the 2nd would be $" + Math.round(secondInterestPaid) + 
							" totaling $" + Math.round(firstInterestPaid + secondInterestPaid) + ".  The 1st + 2nd path would save the client $" + Math.round(oringinalInterestPaid - (firstInterestPaid + secondInterestPaid)) + ".",
							"Broker", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
			                brokerTasks.add(task2); 
			            	
			                double newFirstBalance = firstBalanceNoInsurance - 100;
			                double newSecondBalance = secondBalanceNoInsurance + 100;
			                
			                Task task3 = new Task("Discuss with the broker the possibility of splitting this Opportunity into a 1st and 2nd Mortgage (2 Opportunities).  Please see task description for details.", 
							"Please contact the broker and determine whether the client is willing to complete and 1st and 2nd Mortage on this Property rather than only a 1st.  "
							+ "If so, Please Clone this Opportunity twice.  On the first Opportunity, please reduce the Desired Mortgage Amount to $" + Math.round(newFirstBalance) 
							+ ". You will also need to checkmark the field 'Has Charges Behind' in the general Tab of the Opportunity. On the second clone, please change the desired Amount of the cloned opportunity to " 
							+  Math.round(newSecondBalance) + ".  You will also need to change 'Charge On Title' field to 'Second'.  Re-Underwrite ONLY THE 2nd Opportunity with the All-Products button." +
							"On the Primary Applicant for the 1st Cloned Opportunity, Add a Mortgage to the list of Mortgages for the primary Applicant.  "
							+ "The Details of the new Mortage should be from the Recommended Product of the 2nd Mortgage you just ran.  Following this, Re-Underwrite the 1st cloned Opportunity. "
							+ "Once the client has chosen a path, the unused Opportunity(s) can be deleted.",
							"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
			                assistantTasks.add(task3); 
			            }
			        	
			        }	            
			        
			    }
			    
			   
			    
			    
			    
			 /*   for (Applicant applicant : clientOpportunity.applicants)*/ 
			    for (Applicant applicant : clientOpportunity.applicants){
			    	ApplicantsNames applicantName = new ApplicantsNames();
			    	applicantName.setFirstName(applicant.applicantName);
			    	applicantName.setLastName(applicant.applicantLastName);
			    	try{
			    		Set<ApplicantsNames> setApplicantsNames=new HashSet<ApplicantsNames>();

			    		setApplicantsNames.add(applicantName);

			    		allProductAlgoJSON.setApplicantsNames(setApplicantsNames);
			    		proposalSummaryJson.setApplicantsNames(setApplicantsNames);
			    	}catch (Exception e) {
			    		// TODO: handle exception
			    	}
			    	//allProductAlgoJSON.getApplicantsNames().add(applicantName);

			    	ApplicantsID applicantID = new ApplicantsID();
			    	applicantID.setApplicantID(applicant.applicantId);
			    	applicantID.setApplicantName(applicantName);
			    	//System.out.println("############allProductAlgoJSON For  Applicant################33"+applicant.applicantId);
			    	try{


			    		Set<ApplicantsID>  applicantApplicantsIds=new HashSet<ApplicantsID>();

			    		applicantApplicantsIds.add(applicantID);

			    		allProductAlgoJSON.setApplicantsID(applicantApplicantsIds);
			    		proposalSummaryJson.setApplicantsID(applicantApplicantsIds);

			    	}catch (Exception e) {

			    		logger.error("ApplicantsID   Not Found"+e);
			    	}

			    	//allProductAlgoJSON.getApplicantsID().add(applicantID);


			    	/*}*/

			    }
			   // System.out.println("*****************************MARKETINGTEMPLATES ***********"+marketingTemplateNotes.size());
			    
				Set<MarketingNotes>  setMarketingNotes=new HashSet<MarketingNotes>();

				/*  for (AlgoNote marketingNote : marketingTemplateNotes)*/ 
				marketingTemplateNotes.stream().parallel().forEach(
						marketingNote ->
						{
							MarketingNotes note = new MarketingNotes();
							note.setNoteName(marketingNote.marketingField);
							note.setNoteContent(marketingNote.description);

							try{
								setMarketingNotes.add(note);
								//System.out.println("**********Size  of Marketting   Notes************"+setMarketingNotes.size());
								allProductAlgoJSON.setMarketingNotes(setMarketingNotes);
							}catch (Exception e) {
								// TODO: handle exception
							}

							//System.out.println("****************For  Couchbase Executor  Proposals*********");

							/*ExecutorService exs = Executors.newFixedThreadPool(4);
							CouchbaseExecutorProposal executorserviceproposalJSON=null;
							try{

								executorserviceproposalJSON= new CouchbaseExecutorProposal(proposalSummaryJson); 
								exs.submit(executorserviceproposalJSON);

								exs.shutdown();
							}catch (Exception e) {				    	
															}*/

							//allProductAlgoJSON.getMarketingNotes().add(note);
							/*}*/

						});
			    
			    // Vikash - At this Point all Objects have been set into the UwAppAllAlgo instance "allProductAlgoJSON" ... 
			    // allProductAlgoJSON can be written to couchbase
			    
			    //Code  to  store Data  to  coucbase
			    
			
			    ExecutorService exs = Executors.newFixedThreadPool(4);
			    CouchbaseExecutorAlgo executorserviceallProductAlgoJSON=null;
			    try{

			    	executorserviceallProductAlgoJSON= new CouchbaseExecutorAlgo(allProductAlgoJSON); 
			    exs.submit(executorserviceallProductAlgoJSON);
			    
			    exs.shutdown();
			    }catch (JsonException e) {
			    	logger.error("executorserviceallProductAlgoJSON  for  couchabse"+e.getMessage());
			    	
				}
			    
			    ExecutorService ex1 = Executors.newFixedThreadPool(4);
			    CouchbaseExecutorService executorservice12=null;
			    try{

			    	executorservice12= new CouchbaseExecutorService(productsJsonList); 
			    	ex1.submit(executorservice12);
			    
			    ex1.shutdown();
			    }catch (Exception e) {
			    	logger.error("Coucbase  store  failed"+e.getMessage());			    
			    }
			    
			    
			    
			}
			
		} catch (Exception e) {
			
//			StringBuilder errorString = new StringBuilder();
//            for (List<Underwrite> listOfAcceptableProducts : listOfProductLists)
//		    {
//		        if (listOfAcceptableProducts.size() > 0){
//		        	for (Underwrite underwrite : listOfAcceptableProducts) {
//						for (AlgoNote assistNote : underwrite.assistantNotes) {
//							errorString.append(underwrite.potentialProduct.productName + " - " + assistNote.description + "\n");
//						}
//					}
//		        }
//		    }
			
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
			Task task2 = new Task("There was an ERROR IN THE ALL PRODUCTS ALGO SEE ERROR SECTION 2376.  Please copy-paste the text in the description of this task into an email and send it to techsupport@visdom.ca.", 
					"OpportunityID" + clientOpportunity.applicationNo + " - "  + e.getMessage() + exception,
			"Assistant", "Opportunity.ID", "Opportunity.Team", deadline2,"",(double)0, 0.1, currentDate2, deadline2);   
            assistantTasks.add(task2); 
            
		}
    }

	public double neededDownPayment(double maxPropertyAmount) {
		double requiredDownPayment = 0;
		if (maxPropertyAmount <= 500000){
			requiredDownPayment = maxPropertyAmount* 0.05;
		}
		else{
			double valueOver500 = maxPropertyAmount - 500000;
			double downpayOver500 = valueOver500 * 0.1;
			double downpayUnder500 = (500000 * 0.05);
			requiredDownPayment = downpayOver500 + downpayUnder500;
		}
		return requiredDownPayment;
	}

    private void paymentOptions(AlgoProduct algoProduct)
    {
    	Product product = algoProduct.product;
        List<String> listOfPaymentOptions = new ArrayList<String>();
        if (product.weeklyPayments == true)
            listOfPaymentOptions.add("Weekly Payments");
        if (product.biWeeklyPayments == true)
            listOfPaymentOptions.add("Bi-Weekly Payments");
        if (product.semiMonthlyPayments == true)
            listOfPaymentOptions.add("Semi-Monthly Payments");
        if (product.monthlyPayments == true)
            listOfPaymentOptions.add("Monthly Payments");
        if (product.increasePayments == true)
            listOfPaymentOptions.add("Increase Scheduled Payments");
        if (product.doubleUpPayments == true)
            listOfPaymentOptions.add("Double-Up Payments");
        if (product.extraAnnualPaydown == true)
            listOfPaymentOptions.add("Annual Extra Payments");
        if (product.extraAppliedAnyTime == true)
            listOfPaymentOptions.add("Extra Payments At Any Time");
        if (product.skipPaymentPossible == true)
            listOfPaymentOptions.add("Skip Payment");
        if (product.prepayToReduce == true)
            listOfPaymentOptions.add("Prepay to Reduce Payout Penalty");
        if (product.rewardPoints == true)
            listOfPaymentOptions.add("Reward Points");

        int Counter = 0;
        for (String paymentOption : listOfPaymentOptions)
        {
            Counter++;
            AlgoNote note6 = new AlgoNote(AlgoNote.TypeOfNote.ProposalInfo, AlgoNote.Priority.Med, "- " + paymentOption, "Payment Option" + Counter);
            marketingTemplateNotes.add(note6);
        }
    }
    
    public List<AlgoProduct> getAllProductsByProvince(String province)
    {
    	// TODO Add Restriction where .active == true ... 
    	String currentLender = "nonsenseInput";
    	if (clientOpportunity.currentLender != null){
    		if (clientOpportunity.currentLender.isEmpty() == false){
        		currentLender = clientOpportunity.currentLender;    		
        	}
        	if (currentLender == ""){
        		currentLender = "nonsenseInput";
        	}	
    	}
    	else{
    		currentLender = "nonsenseInput";
    	}
    	
    	double cashbackMax = 100.1;
    	String includeCashBack = "yes";
    	if (SelectionHelper.compareSelection(AlgoOpportunity.LendingGoal.Refinance, clientOpportunity.whatIsYourLendingGoal)){
    		includeCashBack = "Cash";
    		cashbackMax = 0.00000001;
    	}
    	
    	// Check if Pre-Approval or Null Province
    	boolean noProvince = false;
    	if (clientOpportunity.province.isEmpty() || clientOpportunity.province == null){
    		noProvince = true;
    	}
    	
    	BaseDAO<Product, Integer> dao = new BaseDAOImpl<Product, Integer>(Product.class, session);
    	
    	if (noProvince == false){
    		//Integer[] ids = new Integer[]{3,4,5,6};
        	List<Product> products = session.createCriteria(Product.class)
        			.add(Restrictions.like("allowProvinces", province, MatchMode.ANYWHERE))
        			.add(Restrictions.not(Restrictions.like("name", "%Xternal%"))) // Do not allow Competition Products
        			.add(Restrictions.not(Restrictions.like("name", "%inactive%"))) // Do not allow Inactive Products
        			.add(Restrictions.not(Restrictions.ilike("name", "%" + currentLender + "%"))) // Do not allow current Lender Products
        			.add(Restrictions.not(Restrictions.like("name", "%" + includeCashBack + "%"))) // Do not allow Cashback Products
        			.add(Restrictions.between("cashback", 0d, cashbackMax))
        			//.add(Restrictions.in("id", ids))
        			.list();
        	
        	//List<Product> products = dao.findAll();
        	//System.out.println("getAllProductsByProvince.province:" + province);
        	//System.out.println("getAllProductsByProvince:" + products.size());
        	
        	List<AlgoProduct> algoProducts = new ArrayList<AlgoProduct>();
        	for (Product product : products) {
    			AlgoProduct algo = new AlgoProduct(product);
    			algoProducts.add(algo);
    			//product.goodCreditBeaconSplit = 0;
    			product.locQualifyingRate = product.locQualifyingRate == null ? "" : product.locQualifyingRate;
    			algo.whoseBeaconUsed = AlgoProduct.WhoseIncomeUsed.Highest;
//    			product.exclOrInclCity = "Exclude";
//    			product.volBonus1Threshold = 1.0;
//    			product.applicationMethod = "";
//    			product.equifaxScoringUsed = "Beacon5";
    			//algo.allowableBlackFlagGoodCredit = 10000;
    		}
        	
        	return algoProducts;
    	}
    	else {
    		//Integer[] ids = new Integer[]{3,4,5,6};
        	List<Product> products = session.createCriteria(Product.class)        			
        			.add(Restrictions.not(Restrictions.like("name", "%Xternal%"))) // Do not allow Competition Products
        			.add(Restrictions.not(Restrictions.like("name", "%inactive%"))) // Do not allow Inactive Products
        			.add(Restrictions.not(Restrictions.ilike("name", "%" + currentLender + "%"))) // Do not allow current Lender Products
        			.add(Restrictions.not(Restrictions.like("name", "%" + includeCashBack + "%"))) // Do not allow Cashback Products
        			.add(Restrictions.between("cashback", 0, cashbackMax))
        			//.add(Restrictions.in("id", ids))
        			.list();
        	
        	//List<Product> products = dao.findAll();
        	//System.out.println("getAllProductsByProvince.province:" + province);
        	//System.out.println("getAllProductsByProvince:" + products.size());
        	
        	List<AlgoProduct> algoProducts = new ArrayList<AlgoProduct>();
        	for (Product product : products) {
    			AlgoProduct algo = new AlgoProduct(product);
    			algoProducts.add(algo);
    			//product.goodCreditBeaconSplit = 0;
    			product.locQualifyingRate = product.locQualifyingRate == null ? "" : product.locQualifyingRate;
    			algo.whoseBeaconUsed = AlgoProduct.WhoseIncomeUsed.Highest;
//    			product.exclOrInclCity = "Exclude";
//    			product.volBonus1Threshold = 1.0;
//    			product.applicationMethod = "";
//    			product.equifaxScoringUsed = "Beacon5";
    			//algo.allowableBlackFlagGoodCredit = 10000;
    		}
        	
        	return algoProducts;
    	}
    	
    }
    
    public List<AlgoProduct> getAllProducts()
    {
    	// TODO Add Restriction where .active == true ... 
    	
    	BaseDAO<Product, Integer> dao = new BaseDAOImpl<Product, Integer>(Product.class, session);
    	//Integer[] ids = new Integer[]{3,4,5,6};
    	List<Product> products = session.createCriteria(Product.class)
    			.add(Restrictions.not(Restrictions.like("name", "%Xternal%"))) // Do not allow Competition Products  
    			.add(Restrictions.not(Restrictions.like("name", "%inactive%"))) // Do not allow Inactive Products		
    			//.add(Restrictions.in("id", ids))
    			.list();
    	
    	//List<Product> products = dao.findAll();
    	
    	List<AlgoProduct> algoProducts = new ArrayList<AlgoProduct>();
    	for (Product product : products) {
			AlgoProduct algo = new AlgoProduct(product);
			algoProducts.add(algo);
			//product.goodCreditBeaconSplit = 0;
			product.locQualifyingRate = product.locQualifyingRate == null ? "" : product.locQualifyingRate;
			algo.whoseBeaconUsed = AlgoProduct.WhoseIncomeUsed.Highest;
//			product.exclOrInclCity = "Exclude";
//			product.volBonus1Threshold = 1.0;
//			product.applicationMethod = "";
//			product.equifaxScoringUsed = "Beacon5";
//			algo.allowableBlackFlagGoodCredit = 10000;
		}
    	
    	return algoProducts;
    }
    
    public List<AlgoProduct> getAllProductsByType(String productType)
    {
    	// TODO Add Restriction where .active == true ... 
    	
    	BaseDAO<Product, Integer> dao = new BaseDAOImpl<Product, Integer>(Product.class, session);
    	//Integer[] ids = new Integer[]{3,4,5,6};
		List<Product> products = session.createCriteria(Product.class)
    			.add(Restrictions.like("mortgageType", productType, MatchMode.ANYWHERE)) 
    			.add(Restrictions.not(Restrictions.like("name", "%Xternal%")))
    			.add(Restrictions.not(Restrictions.like("name", "%inactive%"))) // Do not allow Inactive Products
    			//.add(Restrictions.in("id", ids))
    			.list();
    	
    	
    	List<AlgoProduct> algoProducts = new ArrayList<AlgoProduct>();
    	for (Product product : products) {
			AlgoProduct algo = new AlgoProduct(product);
			algoProducts.add(algo);
			//product.goodCreditBeaconSplit = 0;
			product.locQualifyingRate = product.locQualifyingRate == null ? "" : product.locQualifyingRate;
//			algo.whoseBeaconUsed = product.whoseIncomeUsed;
//			product.exclOrInclCity = "Exclude";
//			product.volBonus1Threshold = 1.0;
//			product.applicationMethod = ""; 
//			product.equifaxScoringUsed = "Beacon5";
//			algo.allowableBlackFlagGoodCredit = 10000;
		}
    	
    	return algoProducts;
    }
    
    public double insuranceToThreshold(double LTV, double propertyValue){
    	double LTVDifference = 0;
    	if (LTV > 65 && LTV <= 75){
    		LTVDifference = LTV - 65;
    	}
    	else if (LTV > 75 && LTV <= 80){
    		LTVDifference = LTV - 75;
    	}
    	else if (LTV > 80 && LTV <= 85){
    		LTVDifference = LTV - 80;
    	}
    	else if (LTV > 85 && LTV <= 90){
    		LTVDifference = LTV - 85;
    	}
    	else if (LTV > 90 && LTV <= 95){
    		LTVDifference = LTV - 90;
    	}
    	
    	double amountToThreshold = 0;
    	if (LTVDifference <= 0.5){
    		amountToThreshold = LTVDifference * propertyValue;
    	}
    	else{
    		amountToThreshold = 0;
    	}
    	return amountToThreshold;
    }
    
    public Double getEMA(List<Double> interestRates, int idx, int period, double previousEMA) {  
        
        double smoothingConstant = 2.0d / (period +1);  
        List<Double> maArray = new ArrayList<Double>();  
          
        if (idx == (period - 1)) {  
                int startMaArray = idx- (period -1);  
                int endMaArray = startMaArray + period;  
                maArray = interestRates.subList(startMaArray, endMaArray);  
                  
                float sum = 0.0f;  
                for (double sp : maArray) {  
                        sum += sp;  
                }  
                  
                double ema = sum / period;  
                return ema;  
        } 
        else if (idx > (period-1) && previousEMA != 0) {  
                Double currentClose = interestRates.get(idx);  
                double ema = ( (smoothingConstant * (currentClose - previousEMA)) + previousEMA );  
                return ema;  
        } 
        else {  
                return 0d;  
        }  
          
    }
    
    public void CalculateMortgageTrend()
    {
    	// Currently from Bank of Canada Site http://www.bankofcanada.ca/wp-content/uploads/2010/09/selected_historical_page57_58.pdf 
    	// As time allows, convert this list from hard coded to a lookup from somewhere - maybe morningstar. 
    	String historicMortgageRates =  "5.00 , 5.00 , 5.00 , 5.25 , 5.50 , 5.50 , 5.62 , 5.62 , 5.75 , 5.75 , 5.75 , 5.75 , "+
    			"5.70 , 5.70 , 5.70 , 5.70 , 5.80 , 5.80 , 5.85 , 5.85 , 5.75 , 5.80 , 5.80 , 5.80 , "+
    			"5.90 , 5.90 , 5.90 , 5.90 , 5.90 , 5.95 , 5.95 , 5.95 , 6.05 , 6.05 , 6.10 , 6.10 , "+
    			" 6.05 , 6.05 , 6.05 , 6.00 , 6.00 , 6.00 , 6.00 , 6.00 , 6.00 , 6.00 , 6.00 , 6.00 , "+
    			" 6.00 , 6.00 , 6.00 , 6.00 , 5.75 , 5.75 , 5.75 , 5.70 , 5.80 , 5.90 , 5.95 , 5.95 , "+
    			" 5.95 , 5.95 , 6.00 , 6.00 , 6.00 , 6.05 , 6.15 , 6.35 , 6.40 , 6.55 , 6.65 , 6.65 , "+
    			" 6.70 , 6.75 , 6.75 , 6.75 , 6.75 , 6.85 , 6.85 , 6.90 , 7.00 , 7.00 , 7.00 , 6.95 , "+
    			 "6.95 , 6.90 , 6.80 , 6.75 , 6.75 , 6.75 , 6.75 , 6.75 , 6.75 , 6.80 , 6.80 , 6.80 , "+
    			" 6.85 , 6.85 , 6.85 , 6.80 , 6.80 , 6.85 , 6.85 , 6.95 , 7.20 , 7.20 , 7.25 , 7.25 , "+
    			 "7.30 , 7.30 , 7.30 , 7.30 , 7.25 , 7.25 , 7.15 , 7.15 , 7.10 , 7.00 , 7.00 , 7.00 , "+
    			" 7.00 , 7.00 , 7.00 , 7.00 , 7.00 , 7.00 , 7.00 , 7.00 , 7.00 , 7.00 , 7.00 , 7.00 , "+
    			 "7.00 , 7.00 , 7.00 , 6.90 , 6.80 , 6.95 , 7.00 , 7.00 , 7.00 , 7.00 , 7.00 , 7.00 , "+
    			" 7.00 , 7.00 , 7.00 , 6.94 , 6.91 , 6.91 , 6.91 , 7.00 , 7.00 , 7.00 , 7.00 , 7.00 , "+
    			 "7.00 , 7.00 , 7.00 , 6.95 , 6.88 , 6.88 , 6.88 , 7.00 , 7.00 , 7.00 , 7.00 , 7.00 , "+
    			" 6.90 , 6.85 , 6.82 , 6.82 , 6.83 , 6.83 , 7.02 , 7.13 , 7.15 , 7.25 , 7.29 , 7.40 , "+
    			 "7.38 , 7.45 , 7.46 , 7.48 , 7.51 , 7.57 , 7.68 , 7.80 , 7.84 , 7.87 , 7.91 , 7.95 , "+
    			" 7.93 , 7.89 , 7.83 , 7.80 , 7.77 , 7.88 , 8.02 , 8.05 , 8.10 , 8.49 , 8.52 , 8.52 , "+
    			 "8.83 , 8.84 , 8.96 , 9.20 , 9.23 , 9.18 , 9.14 , 9.12 , 9.03 , 9.01 , 9.09 , 9.10 , "+
    			" 9.45 , 9.45 , 9.48 , 9.52 , 9.46 , 9.69 , 9.90 , 9.99 , 10.11 , 10.21 , 10.30 , 10.50, "+  
    			 "10.58 , 10.54 , 10.58 , 10.60 , 10.58 , 10.53 , 10.38 , 10.40 , 10.36 , 10.35 , 10.28, 10.16 ,"+
    			" 9.94 , 9.72 , 9.28 , 9.20 , 9.25 , 9.34 , 9.46 , 9.53 , 9.55 , 9.55 , 9.26 , 9.10 , "+
    			 "9.04 , 8.93 , 8.97 , 9.03 , 9.16 , 9.37 , 9.41 , 9.41 , 9.38 , 9.35 , 9.30 , 9.22 , "+
    			" 9.09 , 9.02 , 9.07 , 9.15 , 9.30 , 9.52 , 9.71 , 9.91 , 10.13 , 10.13 , 10.08 , 10.02,  "+
    			 "10.02 , 10.01 , 10.04 , 10.70 , 11.26 , 11.37 , 11.60 , 11.85 , 12.05 , 12.05 , 12.00, 11.88 ,"+
    			" 11.81 , 10.95 , 10.65 , 10.67 , 10.99 , 11.23 , 11.35 , 11.52 , 11.94 , 12.15 , 11.97, 11.89 ,"+
    			 "11.84 , 11.80 , 11.90 , 12.03 , 11.99 , 11.93 , 11.86 , 11.83 , 11.76 , 11.60 , 11.56, 11.27 ,"+
    			" 10.75 , 10.25 , 10.25 , 10.25 , 10.38 , 10.35 , 10.40 , 10.33 , 10.32 , 10.34 , 10.34, 10.33 ,"+
    			 "10.32 , 10.31 , 10.33 , 10.42 , 10.43 , 10.32 , 10.31 , 10.31 , 10.67 , 10.93 , 11.25, 11.53 ,"+
    			" 11.28 , 11.25 , 11.11 , 11.05 , 11.06 , 11.16 , 11.20 , 11.80 , 12.25 , 13.50 , 14.46, 13.58 ,"+
    			 "13.26 , 13.50 , 14.69 , 16.94 , 13.99 , 12.92 , 13.09 , 13.44 , 14.50 , 14.87 , 15.00, 15.60 ,"+
    			" 15.17 , 15.27 , 15.75 , 16.45 , 17.82 , 18.55 , 18.90 , 21.30 , 21.46 , 20.54 , 18.80, 17.79 ,"+
    			 "18.21 , 18.97 , 19.41 , 19.28 , 19.11 , 19.10 , 19.22 , 18.72 , 17.49 , 16.02 , 14.79, 14.34 ,"+
    			" 14.05 , 13.60 , 13.45 , 13.26 , 13.16 , 12.98 , 13.08 , 13.57 , 13.88 , 13.10 , 12.84, 12.55 ,"+
    			 "12.55 , 12.52 , 12.82 , 13.51 , 14.26 , 14.53 , 14.96 , 14.45 , 13.99 , 13.72 , 13.25, 12.74 ,"+
    			" 12.44 , 12.57 , 13.43 , 12.77 , 12.38 , 11.89 , 11.75 , 11.77 , 11.85 , 11.96 , 11.75, 11.61 ,"+
    			 "11.67 , 11.94 , 11.66 , 11.12 , 10.60 , 10.87 , 11.06 , 11.00 , 11.10 , 11.25 , 11.25, 11.17 ,"+
    			" 10.85 , 10.46 , 10.20 , 10.39 , 11.04 , 11.26 , 11.26 , 11.49 , 11.72 , 11.97 , 11.49, 11.56 ,"+
    			 "11.73 , 11.49 , 11.08 , 11.04 , 11.29 , 11.41 , 11.40 , 11.92 , 12.16 , 11.79 , 11.78, 12.13 ,"+
    			" 12.24 , 12.23 , 12.41 , 12.72 , 12.29 , 11.93 , 11.85 , 11.76 , 11.75 , 11.75 , 11.75, 11.95 ,"+
    			 "12.01 , 12.42 , 12.92 , 13.67 , 14.21 , 14.03 , 13.97 , 13.56 , 13.40 , 13.21 , 13.04, 12.49 ,"+
    			" 12.13 , 11.58 , 11.45 , 11.27 , 11.23 , 11.24 , 11.31 , 11.47 , 11.38 , 10.84 , 10.14, 9.84 ,"+
    			 "9.71 , 9.68 , 10.06 , 10.37 , 10.14 , 9.72 , 9.26 , 8.72 , 8.54 , 9.23 , 9.33 , 9.48 , "+
    			" 9.47 , 9.44 , 8.97 , 8.89 , 8.88 , 8.86 , 8.68 , 8.58 , 8.57 , 8.55 , 7.84 , 7.71 , "+
    			 "7.33 , 7.20 , 7.89 , 9.43 , 9.48 , 9.80 , 10.69 , 10.33 , 10.01 , 9.84 , 9.85 , 10.25,  "+
    			" 10.60 , 10.48 , 9.93 , 9.66 , 8.98 , 8.67 , 8.54 , 8.94 , 8.95 , 8.75 , 8.66 , 8.46 , "+
    			 "8.02 , 7.79 , 8.16 , 8.48 , 8.47 , 8.48 , 8.48 , 8.01 , 7.94 , 7.50 , 7.01 , 6.94 , "+
    			" 7.14 , 7.12 , 7.06 , 7.56 , 7.46 , 7.22 , 6.98 , 7.00 , 6.96 , 6.73 , 6.69 , 6.90 , "+
    			 "6.90 , 6.84 , 6.84 , 6.79 , 6.92 , 6.90 , 6.90 , 7.08 , 7.32 , 6.73 , 6.94 , 6.69 , "+
    			" 6.72 , 6.79 , 7.03 , 6.71 , 6.99 , 7.35 , 7.42 , 7.80 , 7.67 , 7.90 , 8.13 , 8.13 , "+
    			 "8.34 , 8.43 , 8.24 , 8.23 , 8.50 , 8.34 , 8.18 , 8.08 , 8.08 , 8.08 , 8.04 , 7.81 , "+
    			" 7.58 , 7.52 , 7.19 , 7.22 , 7.38 , 7.47 , 7.46 , 7.39 , 7.03 , 6.74 , 6.51 , 6.64 , "+
    			 "6.62 , 6.59 , 6.80 , 7.00 , 7.00 , 6.98 , 6.90 , 6.61 , 6.49 , 6.50 , 6.47 , 6.39 , "+
    			" 6.26 , 6.29 , 6.33 , 6.44 , 6.10 , 5.62 , 5.71 , 5.87 , 5.97 , 5.83 , 6.02 , 6.00 , "+
    			 "5.78 , 5.51 , 5.31 , 5.56 , 5.82 , 6.06 , 6.10 , 5.97 , 5.94 , 5.95 , 5.87 , 5.69 , "+
    			" 5.60 , 5.59 , 5.60 , 5.67 , 5.55 , 5.31 , 5.26 , 5.32 , 5.30 , 5.39 , 5.56 , 5.60 , "+
    			 "5.65 , 5.75 , 5.78 , 5.88 , 6.05 , 6.12 , 6.26 , 6.24 , 6.13 , 6.01 , 5.99 , 5.89 , "+
    			" 5.91 , 6.00 , 5.91 , 5.92 , 6.01 , 6.51 , 6.60 , 6.62 , 6.61 , 6.69 , 6.73 , 6.75 , "+
    			 "6.81 , 6.72 , 6.60 , 6.40 , 6.21 , 6.20 , 6.37 , 6.25 , 6.16 , 6.46 , 6.51 , 6.17 , "+
    			" 5.78 , 5.28 , 5.14 , 4.79 , 4.62 , 4.90 , 5.14 , 5.13 , 4.97 , 5.00 , 5.06 , 4.83 , "+
    			 "4.80 , 4.73 , 4.71 , 5.15 , 5.30 , 5.18 , 5.02 , 4.82 , 4.60 , 4.52 , 4.45 , 4.50 , "+
    			" 4.55 , 4.68 , 4.70 , 4.87 , 4.83 , 4.60 , 4.57 , 4.52 , 4.42 , 4.35 , 4.38 , 4.36 , "+
    			 "4.31 , 4.23 , 4.21 , 4.36 , 4.35 , 4.25 , 4.23 , 4.23 , 4.20 , 4.17 , 4.15 , 4.15 , "+
    			" 4.14 , 4.11 , 4.02 , 4.02 , 4.02 , 4.00 , 4.08 , 4.14 , 4.33 , 4.40 , 4.39 , 4.39 , "+
    			" 5.24 , 5.24 ,	4.99 , 4.79 , 4.79 , 4.79, 4.79, 4.79, 4.79"; // Current end is Spet 2014
    	// From http://www.bankofcanada.ca/rates/interest-rates/canadian-interest-rates/
    	// V122521: Conventional mortgage - 5-year
    	// Mortgage Series: http://www.bankofcanada.ca/rates/interest-rates/canadian-interest-rates/?rangeType=dates&rangeValue=1&rangeWeeklyValue=1&rangeMonthlyValue=1&ByDate_frequency=daily&lP=lookup_canadian_interest.php&sR=2004-07-26&se=L_V122521&dF=2013-08-26&dT=2014-07-26
    	
    	String[] splitMotgageRates = historicMortgageRates.split(",");
    	List<Double> historicRatesMort = new ArrayList<Double>();
    	  	
    	for (String rate : splitMotgageRates) {
    		double parsedRate = Double.parseDouble(rate);
    		historicRatesMort.add(parsedRate);
		}
    	
    	List<Double> ema34RatesMort = new ArrayList<Double>();
    	List<Double> ema13RatesMort = new ArrayList<Double>();
    	List<Double> ema8RatesMort = new ArrayList<Double>();
    	
    	int indexCounter = 0;
    	double prior34 = 0;
    	double prior13 = 0;
    	double prior8 = 0;
    	for (Double double1 : historicRatesMort) {
    		double emaMortgage34 = getEMA(historicRatesMort, indexCounter, 34, prior34);
        	double emaMortgage13 = getEMA(historicRatesMort, indexCounter, 13, prior13);
        	double emaMortgage8 = getEMA(historicRatesMort, indexCounter, 8, prior8);
        	
        	indexCounter = indexCounter + 1;
        	prior34 = emaMortgage34;
        	prior13 = emaMortgage13;
        	prior8 = emaMortgage8;	
        	
        	ema34RatesMort.add(emaMortgage34);
        	ema13RatesMort.add(emaMortgage13);
        	ema8RatesMort.add(emaMortgage8);
		}
    	
    	String historicBondRates = "2.56, 2.62, 2.67, 2.51, 2.44, 2.47, 2.56, 2.56, 2.56, 2.57, 2.84, 2.97," +
    			"2.96, 2.99, 3.02, 3.02, 3.14, 3.23, 3.38, 3.44, 3.44, 3.42, 3.43, 3.43," +
    			"3.40, 3.34, 3.37, 3.33, 3.35, 3.38, 3.44, 3.50, 3.49, 3.59, 3.60, 3.57," +
    			"3.46, 3.19, 2.71, 2.74, 2.72, 2.58, 2.52, 2.48, 2.27, 2.46, 2.46, 2.43," +
    			"2.35, 2.33, 2.30, 2.76, 2.68, 2.73, 2.62, 2.93, 3.00, 3.04, 3.32, 3.43," +
    			"3.21, 3.21, 3.31, 3.69, 3.66, 3.42, 3.62, 3.83, 3.97, 4.14, 4.47, 4.56," +
    			"4.80, 4.43, 4.39, 4.60, 4.67, 4.91, 4.86, 4.92, 4.86, 4.50, 3.92, 3.92," +
    			"3.64, 3.72, 3.41, 3.36, 3.17, 3.46, 2.66, 2.82, 3.42, 3.62, 3.93, 4.42," +
    			"4.50, 4.53, 4.71, 4.86, 4.91, 4.87, 4.98, 5.28, 5.44, 5.05, 4.95, 5.18," +
    			"5.49, 5.28, 5.08, 4.54, 4.28, 4.08, 4.00, 3.69, 3.68, 4.51, 4.74, 4.86," +
    			"4.60, 4.42, 4.66, 4.73, 4.78, 4.52, 4.40, 4.25, 4.26, 3.97, 3.89, 4.02," +
    			"4.14, 4.07, 4.01, 3.89, 4.32, 5.50, 5.41, 5.40, 5.21, 4.50, 4.36, 4.39," +
    			"4.43, 4.51, 4.61, 4.54, 4.32, 4.18, 4.45, 4.60, 4.54, 4.45, 4.55, 4.58," +
    			"4.64, 4.57, 4.74, 4.81, 4.71, 4.66, 4.74, 4.80, 4.79, 4.77, 4.79, 4.63," +
    			"4.53, 4.66, 4.62, 4.58, 4.64, 4.87, 5.05, 5.18, 5.09, 5.12, 5.22, 5.23," +
    			"5.24, 5.38, 5.37, 5.39, 5.37, 5.39, 5.55, 6.09, 5.76, 5.69, 5.77, 5.58," +
    			"5.20, 5.16, 4.76, 4.81, 5.40, 5.68, 5.89, 5.98, 6.10, 6.05, 6.17, 6.48," +
    			"6.53, 6.77, 7.12, 6.87, 7.08, 6.79, 6.44, 6.21, 6.25, 6.48, 6.53, 7.06," +
    			"6.99, 7.03, 7.27, 7.33, 7.69, 7.62, 7.64, 7.71, 8.06, 8.02, 8.31, 8.29," +
    			"8.23, 8.00, 7.32, 7.35, 7.38, 7.07, 6.96, 7.21, 7.12, 7.08, 6.12, 5.42," +
    			"5.37, 5.54, 5.19, 5.52, 5.86, 6.02, 6.46, 6.00, 5.63, 5.04, 4.94, 5.09," +
    			"5.46, 5.84, 6.29, 6.43, 6.53, 6.68, 6.59, 6.71, 6.57, 6.21, 5.77, 6.00," +
    			"6.25, 6.30, 6.50, 6.67, 7.40, 7.19, 7.39, 7.54, 7.25, 7.09, 6.98, 7.25," +
    			"6.99, 6.76, 7.57, 8.56, 8.74, 9.24, 9.27, 9.38, 8.89, 7.80, 7.32, 6.96," +
    			"6.40, 6.38, 6.80, 7.55, 7.31, 7.53, 8.10, 8.36, 8.85, 8.30, 8.64, 8.41," +
    			"8.21, 8.38, 8.53, 8.46, 8.35, 8.50, 8.55, 8.43, 8.49, 8.44, 8.15, 7.71," +
    			"7.74, 7.68, 7.85, 8.01, 7.74, 7.76, 7.81, 7.90, 8.01, 8.12, 8.11, 8.10," +
    			"8.36, 8.46, 8.69, 8.82, 8.85, 8.86, 8.86, 8.86, 8.96, 9.51, 9.84, 9.92," +
    			"9.88, 10.00, 9.89, 9.52, 9.65, 9.73, 9.92, 10.52, 10.89, 11.75, 11.60, 11.68," +
    			"12.49, 13.16, 13.88, 11.77, 10.86, 10.51, 11.53, 12.30, 12.97, 13.22, 13.18, 12.58," +
    			"13.14, 13.57, 13.95, 15.47, 15.63, 15.75, 18.03, 17.80, 18.77, 17.08, 13.75, 15.17," +
    			"16.08, 14.95, 15.07, 14.88, 14.85, 16.15, 15.78, 13.64, 12.90, 11.88, 11.19, 10.64," +
    			"10.81, 10.49, 10.46, 10.17, 10.18, 10.44, 10.83, 11.27, 10.67, 10.61, 10.58, 10.84," +
    			"10.73, 11.31, 11.87, 12.19, 13.16, 13.00, 12.95, 12.33, 12.14, 11.48, 10.97, 10.76," +
    			"10.46, 11.62, 11.41, 10.86, 10.24, 10.36, 10.40, 10.17, 10.34, 9.88, 9.56, 9.33," +
    			"9.95, 9.71, 9.30, 8.96, 9.20, 9.06, 9.19, 9.09, 9.23, 9.18, 8.88, 8.81," +
    			"8.20, 8.24, 7.97, 9.35, 9.41, 9.27, 9.89, 10.06, 10.86, 9.79, 10.00, 9.95," +
    			"9.23, 9.03, 9.31, 9.56, 9.61, 9.63, 9.92, 10.40, 10.14, 9.78, 10.25, 10.40," +
    			"10.30, 10.82, 11.04, 10.47, 10.06, 9.67, 9.59, 9.90, 10.16, 9.88, 10.36, 10.19," +
    			"10.34, 11.45, 11.66, 12.40, 11.67, 11.35, 11.25, 10.79, 11.36, 11.02, 10.52, 10.50," +
    			"10.08, 9.57, 9.56, 9.51, 9.38, 9.77, 9.65, 9.39, 8.84, 8.06, 8.19, 7.95," +
    			"7.90, 7.94, 8.48, 8.32, 7.80, 7.32, 6.36, 6.30, 7.30, 6.71, 7.40, 7.35," +
    			"7.46, 7.02, 6.96, 6.98, 6.85, 6.52, 6.22, 6.02, 6.27, 5.87, 5.86, 5.47," +
    			"5.09, 5.81, 7.37, 7.28, 7.95, 8.75, 8.80, 8.18, 8.20, 8.37, 8.64, 9.00," +
    			"9.15, 8.44, 8.25, 7.92, 7.39, 7.22, 7.70, 7.49, 7.47, 7.47, 6.65, 6.51," +
    			"6.12, 6.66, 6.89, 6.92, 6.84, 6.88, 6.69, 6.32, 5.99, 5.29, 4.77, 5.20," +
    			"5.37, 5.19, 5.61, 5.81, 5.76, 5.23, 5.16, 5.31, 5.08, 4.94, 5.13, 5.37," +
    			"5.08, 5.27, 5.13, 5.34, 5.23, 5.31, 5.44, 5.66, 4.83, 4.76, 5.09, 4.81," +
    			"4.83, 5.28, 5.00, 5.00, 5.36, 5.38, 5.56, 5.53, 5.68, 6.24, 6.01, 6.14," +
    			"6.39, 6.31, 6.17, 6.20, 6.21, 6.08, 6.03, 5.94, 5.81, 5.79, 5.63, 5.32," +
    			"5.13, 5.06, 4.99, 5.17, 5.52, 5.34, 5.30, 4.86, 4.42, 3.83, 4.39, 4.54," +
    			"4.56, 4.45, 5.20, 4.94, 4.80, 4.60, 4.22, 4.21, 4.05, 4.15, 4.20, 3.93," +
    			"4.15, 4.06, 4.37, 4.09, 3.60, 3.50, 3.67, 3.89, 3.67, 3.88, 3.88, 3.75," +
    			"3.52, 3.26, 3.14, 3.59, 3.77, 4.01, 4.01, 3.77, 3.84, 3.79, 3.70, 3.58," +
    			"3.37, 3.48, 3.70, 3.42, 3.32, 3.17, 3.35, 3.22, 3.51, 3.80, 3.86, 3.85," +
    			"3.95, 4.00, 4.11, 4.34, 4.29, 4.53, 4.25, 4.01, 3.87, 4.07, 3.85, 3.94," +
    			"4.08, 3.94, 3.98, 4.11, 4.54, 4.64, 4.64, 4.28, 4.28, 4.22, 3.88, 3.96," +
    			"3.45, 3.35, 2.88, 2.99, 3.24, 3.40, 3.33, 2.98, 3.12, 2.73, 2.39, 1.61," +
    			"1.96, 1.85, 1.69, 1.63, 2.11, 2.44, 2.49, 2.33, 2.32, 2.42, 2.12, 2.48," +
    			"2.21, 2.26, 2.59, 2.80, 2.28, 2.20, 2.29, 1.79, 1.79, 1.84, 2.24, 2.23," +
    			"2.31, 2.36, 2.38, 2.34, 2.10, 2.10, 1.92, 1.49, 1.30, 1.46, 1.31, 1.18," +
    			"1.25, 1.32, 1.47, 1.62, 1.25, 1.18, 1.12, 1.31, 1.25, 1.27, 1.25, 1.32," +
    			"1.43, 1.25, 1.21, 1.13, 1.35, 1.69, 1.57, 1.72, 1.72, 1.53, 1.51, 1.62," +
    			"1.33, 1.34, 1.47, 1.44, 1.31, 1.47, 1.42, 1.35, 1.52" ; // Current end is Sept 2014 
    	// From http://www.bankofcanada.ca/rates/interest-rates/lookup-bond-yields/      	
    	// Bond Series: V122485: Government of Canada marketable bonds - average yield - 3-5 year

    	String[] splitBondRates = historicBondRates.split(",");
    	List<Double> historicRatesBond = new ArrayList<Double>();
    	for (String rate : splitBondRates) {
    		historicRatesBond.add(Double.parseDouble(rate));
		}
    	    	
    	List<Double> ema34RatesBond = new ArrayList<Double>();
    	List<Double> ema13RatesBond = new ArrayList<Double>();
    	List<Double> ema8RatesBond = new ArrayList<Double>();
    	
    	int indexCounterBond = 0;
    	double prior34Bond = 0;
    	double prior13Bond = 0;
    	double prior8Bond = 0;
    	for (int i = 0; i < historicRatesBond.size() - 1; i++) {
    		double emaBond34 = getEMA(historicRatesBond, indexCounterBond, 34, prior34Bond);
        	double emaBond13 = getEMA(historicRatesBond, indexCounterBond, 13, prior13Bond);
        	double emaBond8 = getEMA(historicRatesBond, indexCounterBond, 8, prior8Bond);
        	
        	indexCounterBond = indexCounterBond + 1;
        	prior34Bond = emaBond34;
        	prior13Bond = emaBond13;
        	prior8Bond = emaBond8;	
        	
        	ema34RatesBond.add(emaBond34);
        	ema13RatesBond.add(emaBond13);
        	ema8RatesBond.add(emaBond8);
		}
    	
    	currentRateTrend = "";
    	if (prior8Bond <= prior13Bond && prior13Bond <= prior34Bond){
    		currentRateTrend = "falling";
    	}
    	else if (prior8Bond > prior13Bond && prior13Bond > prior34Bond){
    		currentRateTrend = "rising";
    	}
    	else {
    		currentRateTrend = "level";
    	}
    	// Need to achieve 2 things ...determine current "trend" of Bond rates to for "Low, Med, High likelihood of rate increases ... 
    	// Profile "duration" and scope of all upward, downward and level trend periods. 
    	// For the moment, "duration" and "size" of Rising and Falling and Level environments was calculated manually in excel
    	// In the event time allows later, perhaps place logic here to calculate those factors algorithically
    	
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
    	else {
    		termOfMorgage = "5 Year";
    	}
    	return termOfMorgage;
	}
	
    public double CalcExponent(double Length){
    	double value = 0;
    	value = (double)2 / ((double)1 + Length);
    	return value;
    }
    
    public double CalcEMA(double lastValue, double currentValue, double exponet){
    	double value = 0;
    			
    			
    			
    	return value;
    }
    
    public double roundedNumber(double inputDouble){
		double value = inputDouble;
		value = value*100;
		value = (double)((int) value);
		value = value /100;
		return value;
	}
    
    public static String fmt(double d)
    {
        if(d == (long) d)
            return String.format("%d",(long)d);
        else
            return String.format("%s",d);
    }
}
