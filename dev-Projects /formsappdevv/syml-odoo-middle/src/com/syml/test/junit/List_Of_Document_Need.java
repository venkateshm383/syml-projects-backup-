package com.syml.test.junit;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;




public class List_Of_Document_Need {
	
	StringBuilder listOfDocsNeeded=null;
	
	public JSONObject list_Of_Document_Needmethod(Opportunity opportunity) throws JSONException {
		JSONObject jsonObject=new JSONObject();
		 listOfDocsNeeded=new StringBuilder();
		
		boolean webDocAdded = false;
		boolean noaDocAdded= false;
		//System.out.println(opportunity.getApplicants().get(0).applicantName);
		int i=1;
		JSONArray jsonArray=new JSONArray();
		for(Applicant applicant:opportunity.getApplicants()){
		
			JSONObject jsonApllicant=new JSONObject();
			 listOfDocsNeeded=new StringBuilder();
			 JSONObject jsonDocment=new JSONObject();
			if(applicant.applicantName!=null){
		//	System.out.println("applicant.applicantName  "+i+""+applicant.applicantName);
				
			
			if(applicant.incomes!=null && applicant.incomes.size()>0){
				
					listOfDocsNeeded.append(applicant.applicantName + "\n");
					//ap.setFirstName()
					
				boolean addedSelfEmployed = false;
				boolean addedEmployed = false;
				
				for (Income currentIncome : applicant.incomes) {
					jsonDocment = createIncomeDocsList(
							listOfDocsNeeded,
							webDocAdded, noaDocAdded, applicant,
							addedSelfEmployed, addedEmployed,
							currentIncome,jsonDocment);	
					
					
				
					
				}
				
				
				
				jsonDocment=createImmigrantTasks(applicant,jsonDocment);
				jsonDocment=createNonResidentTasks(applicant,jsonDocment);
				//System.out.println("---------------------->"+i+""+listOfDocsNeeded);
				jsonDocment=createPropertyTasks(listOfDocsNeeded, applicant.getProperties(),jsonDocment);
				
				
			
			
				
				if(i==1){
						jsonApllicant.put("Applicant_Name", applicant.applicantName);
					jsonApllicant.put("Applicant_Email", applicant.emailPersonal);
					jsonApllicant.put("Documents",jsonDocment);
					jsonArray.put(jsonApllicant);
				}else{
					
					jsonApllicant.put("CO_Applicant_Name", applicant.applicantName);
					jsonApllicant.put("CO_Applicant_Email", applicant.emailPersonal);
					jsonApllicant.put("Documents",jsonDocment);
					jsonArray.put(jsonApllicant);
				}
				
			}}	/*if (applicant.monthlychildsupport > 0 ){		
				// Assistant Tasks
				createChildSupportDocs(listOfDocsNeeded, webDocAdded, applicant);	
			}*/
			
				i=i+1;
		}
		 listOfDocsNeeded=new StringBuilder();
		 JSONObject jsonOpprunity=new JSONObject();
		if(opportunity.getWhatIsYourLendingGoal()!=null && opportunity.getWhatIsYourLendingGoal().equalsIgnoreCase("Purchase")){
			jsonOpprunity=createPurchaseTasks( listOfDocsNeeded, opportunity,jsonOpprunity);
			
			}
		if(opportunity.getWhatIsYourLendingGoal()!=null && opportunity.getWhatIsYourLendingGoal().equalsIgnoreCase("Refinance")){
			listOfDocsNeeded.append("Property Tax Statement for " + opportunity.getAddress() + "\n");	
			//jsonOpprunity.put(key, value)
				}
		if(opportunity.getWhatIsYourLendingGoal()!=null && opportunity.getWhatIsYourLendingGoal().equalsIgnoreCase("Pre-Approval")){
			listOfDocsNeeded.append("Property Tax Statement for " + opportunity.getAddress() + "\n");	
			//jsonOpprunity.put(key, value)
					}
		jsonOpprunity=createDownPaymentTasks(listOfDocsNeeded, opportunity,jsonOpprunity);
		
		System.out.println(listOfDocsNeeded);
		//jsonOpprunity.put("Opprtunity_DocumentNeeded", listOfDocsNeeded); 
		

		jsonOpprunity.put("Opportunity ID", opportunity.getPartnerId());
		jsonOpprunity.put("Opportunity URL", "");
		
		jsonOpprunity.put("DocListURL ", "");
		
		
		jsonArray.put(jsonOpprunity);
		jsonObject.put("Documentation", jsonArray);
		return jsonObject;
		
	}
	
	private JSONObject createPurchaseTasks(StringBuilder listOfDocsNeeded,Opportunity opportunity,JSONObject jsonDocment) throws JSONException {
		listOfDocsNeeded.append("Property:");
		
		listOfDocsNeeded.append("- Realtor name and contact information");
		listOfDocsNeeded.append("- Copy of MLS Listing for " + opportunity.getAddress() + " (Or let us know we should get this from your realtor and we can contact them.) " + "\n");	
		jsonDocment.put("Offer to Purchase ,MLS Listing", "Copy of MLS Listing and Copy of Offer to Purchase for " + opportunity.getAddress() );
		listOfDocsNeeded.append("\t- Copy of Offer to Purchase for " + opportunity.getAddress()+ " including waivers and addendums (Or let us know we should get this from your realtor and we can contact them.) " + "\n");	
		if (opportunity.getMonthlyRentalIncome()!=null && opportunity.getMonthlyRentalIncome() > 0){
			String doc = " Lease agreement or Schedule A (Rental Fair Market Value Assessment) to verify the likely rental income from the property being financed ";
			listOfDocsNeeded.append("\t - " + doc + "\n");	
			jsonDocment.put("Lease agreement", doc );

		}
		return jsonDocment;
	}
	private JSONObject createImmigrantTasks(Applicant applicant,JSONObject jsonDocment) throws JSONException {
		if (applicant.immigrationDate != null){
			Calendar dateNow = Calendar.getInstance();
			dateNow.add(Calendar.YEAR, -4);
			if (applicant.immigrationDate.after(dateNow.getTime())){
			
				String doc =  "Immigration permit / work visa for " + applicant.applicantName;
				jsonDocment.put("Immigration permit", doc);
				listOfDocsNeeded.append("\t - " + doc + "\n");	
				
			doc =  "International credit bureau " + applicant.applicantName;
				listOfDocsNeeded.append("\t - " + doc + "\n");	
				
			}
		}
		return jsonDocment;
	}

	private JSONObject createNonResidentTasks(Applicant applicant,JSONObject jsonDocment) throws JSONException {
		if(applicant.nonResident!=null){
		if (applicant.nonResident == true){ 
			String doc =  "Immigration permit / work visa for " + applicant.applicantName;
			listOfDocsNeeded.append("\t - " + doc + "\n");	
			jsonDocment.put("Immigration permit", doc);
			 doc =  "International credit bureau " + applicant.applicantName;
			listOfDocsNeeded.append("\t - " + doc + "\n");	
		}}
		return jsonDocment;
	}
	

private void createChildSupportDocs(StringBuilder listOfDocsNeeded, boolean webDocAdded, Applicant applicant) {
			
		String doc = "Divorce agreement or Separation agreement  - " + applicant.applicantName ;
		listOfDocsNeeded.append("\t - " + doc + "\n");
		String doc1 = "Bank statements (6 months) which verify support payments  - " + applicant.applicantName ;
		listOfDocsNeeded.append("\t - " + doc1 + "\n");
}

	private JSONObject createIncomeDocsList(StringBuilder listOfDocsNeeded,
			boolean webDocAdded, boolean noaDocAdded, Applicant applicant,
			boolean addedSelfEmployed, boolean addedEmployed,
			Income currentIncome,JSONObject jsonObject) throws JSONException {
		Calendar currentDate1 = new GregorianCalendar();
		Calendar deadline1 = new GregorianCalendar();

		deadline1.add(Calendar.HOUR, 1);
		if ( currentIncome.typeOfIncome.equalsIgnoreCase("Employeed")){
		
			String letter = "\t" + "Letter of Employment for your job with " + currentIncome.business + "\n";
			String paystubs = "\t" + "Recent paystubs for your job with " + currentIncome.business + "\n";
			if (addedEmployed == false){
				String loeWeb = "Letter of Employment " + "\n";
				String payStubWeb = "PayStubs " + "\n";
				if (webDocAdded == false && noaDocAdded == false){
					listOfDocsNeeded.append(loeWeb);
					listOfDocsNeeded.append(payStubWeb);
					jsonObject.put("Letter of EmploymentDoc", "Letter of Employment");
					jsonObject.put("payStubWeb","PayStubs");
				}
				
				addedEmployed = true;
			}
		}else if (currentIncome.typeOfIncome.equalsIgnoreCase("Self-Employeed")) {
			
	
			
			if (addedSelfEmployed == false){
				Calendar year1 =  Calendar.getInstance();
															
				int currentMonth = year1.get(Calendar.MONTH);
				if (currentMonth <= 5){
					int startYear = year1.get(Calendar.YEAR) - 2;
					//year1.add(Calendar.YEAR, -2);
					String noa1Year = "\t - " + startYear + " Notice of Assessment from Revenue Canada" + "\n";
					String noa1YearWeb = startYear + " Notice of Assessment" + "\n";
					if (webDocAdded == false && noaDocAdded == false){
						listOfDocsNeeded.append(noa1YearWeb);
						jsonObject.put("Confirmation of Taxes Paid",startYear + " Notice of Assessment");
						
					}
					
					int priorYear = startYear - 1;
					String noa2Year = "\t - " + priorYear + " Notice of Assessment from Revenue Canada" + "\n";
					String noa2YearWeb = priorYear + " Notice of Assessment" + "\n";
					if (webDocAdded == false && noaDocAdded == false){
						listOfDocsNeeded.append(noa2YearWeb);	
						jsonObject.put("Confirmation of Taxes Paid",noa1YearWeb);

					}
					noaDocAdded = true;
				}
				else{
					int startYear = year1.get(Calendar.YEAR) - 1;
					String noa1Year = "\t - " + startYear+ " Notice of Assessment from Revenue Canada" + "\n";
					String noa1YearWeb = startYear + " Notice of Assessment" + "\n";
					if (webDocAdded == false && noaDocAdded == false){
						listOfDocsNeeded.append(noa1YearWeb);		
						jsonObject.put("Confirmation of Taxes Paid",startYear + " Notice of Assessment");

					}
					
					int priorYear = startYear - 1;
					String noa2Year = "\t - " + priorYear + " Notice of Assessment from Revenue Canada" + "\n";
					String noa2YearWeb = priorYear + " Notice of Assessment" + "\n";
					if (webDocAdded == false && noaDocAdded == false){
						listOfDocsNeeded.append(noa2YearWeb);		
						jsonObject.put("Confirmation of Taxes Paid",priorYear + " Notice of Assessment");

					}	
					noaDocAdded = true;
				}
				
				addedSelfEmployed = true;				    						
			}
		}
		else if (currentIncome.typeOfIncome.equalsIgnoreCase("Rental")){
	
			String doc = "Lease agreement or schedule A for the property at " + currentIncome.name;
			String income4Web = doc + "\n";
			if (webDocAdded == false){
				listOfDocsNeeded.append(income4Web);
				jsonObject.put("Income Rental ",income4Web);

			}			
		} 
		
		else if (currentIncome.typeOfIncome.equalsIgnoreCase("Spouse Support") || currentIncome.typeOfIncome.equalsIgnoreCase("Child Support")){
			
			String doc = "Divorce / Separation Agreeement";
			String income4Web = doc + "\n";
			if (webDocAdded == false){
				listOfDocsNeeded.append(income4Web);	
				jsonObject.put("Divorce/Separation Agreement ",income4Web);

				
			}
		}
		else if (currentIncome.typeOfIncome.equalsIgnoreCase("Child tax credit")){
			
			String doc = "Child tax credit form";
			String income4Web = doc + "\n";
			if (webDocAdded == false){
				listOfDocsNeeded.append(income4Web);
				jsonObject.put("Child tax credit ",income4Web);

			}
		}
		else if (currentIncome.typeOfIncome.equalsIgnoreCase("LivingAllowance")){
			
			
		}
		else if (currentIncome.typeOfIncome.equalsIgnoreCase("Vehicle")){
			String doc = "Vehicle Allownce Statement";
			String income4Web = doc + "\n";
			if (webDocAdded == false){
				listOfDocsNeeded.append(income4Web);
				jsonObject.put("Vehicle Allownce Statement",income4Web);
			}
		} 
		else if (currentIncome.typeOfIncome.equalsIgnoreCase("Bonus")){
			
			String doc = "Bonus Statement";
			String income4Web = doc + "\n";
			if (webDocAdded == false){
				listOfDocsNeeded.append(income4Web);
				jsonObject.put("Bonus Statement",income4Web);
			}
		} 
		else if (currentIncome.typeOfIncome.equalsIgnoreCase("Commission")){
		
			String doc = "Commission Statement";
			String income4Web = doc + "\n";
			if (webDocAdded == false){
				listOfDocsNeeded.append(income4Web);
				jsonObject.put("Commission Statement",income4Web);
			}
		}
		else if (currentIncome.typeOfIncome.equalsIgnoreCase("Interest")){
			String doc = "Interest Statements";
			String income4Web = doc + "\n";
			if (webDocAdded == false){
				listOfDocsNeeded.append(income4Web);
				jsonObject.put("Interest Statements",income4Web);
			}
		}  
		else if (currentIncome.typeOfIncome.equalsIgnoreCase("Overtime")){
			String doc = "Overtime Statements";
			String income4Web = doc + "\n";
			if (webDocAdded == false){
				listOfDocsNeeded.append(income4Web);
				jsonObject.put("Overtime Statements",income4Web);
			}
		}  
		else if (currentIncome.typeOfIncome.equalsIgnoreCase("Pension")){
			String doc = "Pension Statements";
			String income4Web = doc + "\n";
			if (webDocAdded == false){
				listOfDocsNeeded.append(income4Web);
				jsonObject.put("Pension Statements",income4Web);
			}
		}  
		else if (currentIncome.typeOfIncome.equalsIgnoreCase("Retired")){
			String doc = "Investment Income Statements";
			String income4Web = doc + "\n";
			if (webDocAdded == false){
				listOfDocsNeeded.append(income4Web);
				jsonObject.put("Investment Income Statements",income4Web);
			}
		}
		else {
			String doc = "Other Income Statements ";
			// documentsListApplicant1.add(doc);		
			String income4Web = doc + "\n\n";
			if (webDocAdded == false){
				listOfDocsNeeded.append(income4Web);
				jsonObject.put("Other Income Statements",income4Web);
			}
		}
		return jsonObject;
	}

private JSONObject createPropertyTasks(StringBuilder listOfDocsNeeded, List<Property> properties,JSONObject jsonDocument ) throws JSONException {
	System.out.println("---------------------->");
	for(Property currentProperty:properties){
		if(currentProperty.getName()!=null){
			jsonDocument.put("Mortgage Statement",currentProperty.getName());
			jsonDocument.put("Property Tax Assessment",currentProperty.getName());

	listOfDocsNeeded.append("Property Tax Statement for " + currentProperty.getName() + "\n");	
	if(currentProperty.getMoCondoFees()!=null){
	 if (currentProperty.getMoCondoFees() > 0){
		listOfDocsNeeded.append("\t - " + currentProperty.getName() + " - Condo Documents" + "\n");
		jsonDocument.put("Condo Documents", currentProperty.getName());
		}
	}
	}}
	return jsonDocument;
}
	
	
	private JSONObject createDownPaymentTasks(StringBuilder listOfDocsNeeded,
			Opportunity opportunity,JSONObject jsonObject) throws JSONException {
		
		System.out.println(opportunity.getDownpaymentAmount());
	
		if(opportunity.getDownpaymentAmount()!=null){
		
		if (opportunity.getDownpaymentAmount() > 0){
		
			listOfDocsNeeded.append("\n");
			listOfDocsNeeded.append("Down Payment " + "\n");	
				jsonObject.put("Down Payment", "Down Payment");
		}
		ArrayList<String> downpaymentSources = new ArrayList<String>();
		
		if (opportunity.getBankAccount()!=null && opportunity.getBankAccount() > 0){
			//documentsListApplicant1.add("Downpayment Verification - Bank Account.");
			downpaymentSources.add("Downpayment Verification - Bank Account.");
			listOfDocsNeeded.append("\t - " + "Verification of Bank Statement" + "\n");	
			jsonObject.put("Downpayment Verification - Bank Account", "Verification of Bank Statement");
			//listOfDownpayDocsWeb.append("Bank Account Statement" + "\n");
		}
		if (opportunity.getPersonalCashAmount()!=null && opportunity.getPersonalCashAmount() > 0){
			//documentsListApplicant1.add("Downpayment Verification - Bank Account.");
			downpaymentSources.add("Downpayment Verification - Bank Account.");
			listOfDocsNeeded.append("\t - " + "Verification Bank Statement" + "\n");	
			jsonObject.put("Downpayment Verification - Bank Account", "Verification of Bank Statement");

			//listOfDownpayDocsWeb.append("Bank Account Statement" + "\n");
		}
		if (opportunity.getRrspAmount()!=null && opportunity.getRrspAmount() > 0){
			//documentsListApplicant1.add("Downpayment Verification - RRSP.");
			downpaymentSources.add("Downpayment Verification - RRSP.");
			listOfDocsNeeded.append("\t - " + "Verification of RRSP Statements" + "\n");
			jsonObject.put("Downpayment Verification - RRSP", "Downpayment Verification - RRSP");

			//listOfDownpayDocsWeb.append("RRSP Statements" + "\n");
		}
		if (opportunity.getGiftedAmount()!=null && opportunity.getGiftedAmount() > 0){
			// Broker Tasks
			//documentsListApplicant1.add("Downpayment Verification - Gift Letter.");
			downpaymentSources.add("Downpayment Verification - Gift Letter.");
			listOfDocsNeeded.append("\t - " + "Verification of Gift Letter" + "\n");
			jsonObject.put("Verification of Gift Letter", "Verification of Gift Letter");

			//listOfDownpayDocsWeb.append("Gift Letter" + "\n");
			         
		}
		if (opportunity.getBorrowedAmount()!=null && opportunity.getBorrowedAmount() > 0){
			//documentsListApplicant1.add("Downpayment Verification - Borrowed Amount.");
			downpaymentSources.add("Downpayment Verification - Borrowed Amount.");
			listOfDocsNeeded.append("\t - " + "Verification of Loan, Line of Credit or Credit Card Statement" + "\n");
			jsonObject.put("Downpayment Verification - Borrowed Amount.", "Verification of Loan, Line of Credit or Credit Card Statement");

			//listOfDownpayDocsWeb.append("Borrowed Downpayment Statement (LOC)" + "\n");
			
		}
		if (opportunity.getSaleOfExistingAmount()!=null && opportunity.getSaleOfExistingAmount() > 0){
			downpaymentSources.add("Downpayment Verification - Sale of Existing Property, Listing Sheet");
			listOfDocsNeeded.append("\t - " + "Verification of Listing Sheet or Offer to Purchase" + "\n");
			jsonObject.put("Downpayment Verification - Sale of Existing Property, Listing Sheet", "Verification of Listing Sheet or Offer to Purchase");

			//listOfDownpayDocsWeb.append("Listing Sheet or Offer to Purchase" + "\n");
		}
		if ( opportunity.getExistingEquityAmount()!=null && opportunity.getExistingEquityAmount() > 0){
			downpaymentSources.add("Downpayment Verification - Existing Equity");
			listOfDocsNeeded.append("\t - " + "Verification of Property address with existing equity" + "\n");
			jsonObject.put("Downpayment Verification - Existing Equity", "Verification of Property address with existing equity");

		}
		if (opportunity.getSweatEquityAmount()!=null && opportunity.getSweatEquityAmount() > 0){http://localhost:8080/UnderwrittingApp/services/desiredProduct/3433
			//documentsListApplicant1.add("Downpayment Verification - Sweat Equity");
			downpaymentSources.add("Downpayment Verification - Sweat Equity");
			listOfDocsNeeded.append("\t - " + "Verification of Property address with sweat equity" + "\n");
			jsonObject.put("Downpayment Verification - Sweat Equity", "Verification of Property address with sweat equity");

		}
		if (opportunity.getSecondaryFinancingAmount()!=null && opportunity.getSecondaryFinancingAmount()> 0){
			//documentsListApplicant1.add("Downpayment Verification - Secondary Financing");
			downpaymentSources.add("Downpayment Verification - Secondary Financing");
			listOfDocsNeeded.append("\t - " + "Verification of $" + opportunity.getSecondaryFinancingAmount() + " from Secondary Financing (Loan Agreement)" + "\n");
			jsonObject.put("Downpayment Verification - Secondary Financing", "Verification of $" + opportunity.getSecondaryFinancingAmount() + " from Secondary Financing (Loan Agreement)" + "\n");

		}
		if (opportunity.getOtherAmount()!=null && opportunity.getOtherAmount() > 0){
			listOfDocsNeeded.append("\t - " + "Verification of Explanation in writing of other source of downpayment" + "\n");
			jsonObject.put("Verification of Explanation in writing of other source of downpayment","Verification of Explanation in writing of other source of downpayment");

		}
		}
		return jsonObject;
	}
	

}
