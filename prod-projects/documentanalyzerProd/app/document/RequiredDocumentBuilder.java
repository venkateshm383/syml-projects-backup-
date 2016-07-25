package document;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import play.Logger;

import dto.Applicant;
import dto.ApplicantDocument;
import dto.Income;
import dto.Opportunity;
import dto.Property;
import email.SendWithUsMail;

public class RequiredDocumentBuilder {
	
	private static org.slf4j.Logger logger = play.Logger.underlying();
	
	// ApplicantDocumentclass to store list of documents
	ApplicantDocument applicantDocumentsList = new ApplicantDocument();


	ArrayList<String> applicantDocumentsArrayList = new ArrayList<String>();
	ArrayList<String> applicantCreditDocumentsArrayList = new ArrayList<String>();

	ArrayList<String> co_ApplicantDocumentsArrayList = new ArrayList<String>();
	ArrayList<String> co_ApplicantCreditDocumentsArrayList = new ArrayList<String>();

	ArrayList<String> propertyDocumentsArrayList = new ArrayList<String>();

	ArrayList<String> downpaymentDocumentsArrayList = new ArrayList<String>();

	Set<String> duplicateRemovedApplicantDocument = null;
	Set<String> duplicateRemovedCoApplicantDocument = null;
	Set<String> duplicateRemovedpropertyDocumentsArrayList = null;
	Set<String> duplicateRemoveddownpaymentDocumentsArrayList = null;
	Set<String> duplicateRemovedApplicantCreditInforamtion = null;
	Set<String> duplicateRemovedCoApplcantCreditInforamtion = null;

	public ApplicantDocument requiredDocumentBuilderMethod(
			Opportunity opportunity) {
		logger.debug("inside document builder class -----------");
		int numberOfApplicants = 1;

		// listing property documents based on leading goals //Purchase
		if (opportunity.getWhat_is_your_lending_goal() != null
				&& opportunity.getWhat_is_your_lending_goal().equalsIgnoreCase(
						"2")) {
			opportunity.setWhat_is_your_lending_goal("Purchase");
			HashMap map = getProperAddress(opportunity);
			if (!map.isEmpty()) {

			
				propertyDocumentsArrayList.add("Offer to Purchase ("
						+ map.get("address1") + ", " + map.get("city") + ")");

				if (opportunity.getMls() != null) {

					if (opportunity.getMls().equalsIgnoreCase("MLS Listed")) {
					
						propertyDocumentsArrayList.add("MLS Listing ("
								+ map.get("address1") + ", " + map.get("city")
								+ ")");

					} else if (opportunity.getMls().equalsIgnoreCase(
							"New Build")) {
					
						propertyDocumentsArrayList.add("Floor Plans/Specs");
					}
				}

				createPurchaseTasks( opportunity,propertyDocumentsArrayList);
				if (opportunity.getIsRentalProperty() != null
						&& opportunity.getIsRentalProperty() == true) {
					
					propertyDocumentsArrayList.add("Appraisal and Schedule A");
				}

			}

	
			propertyDocumentsArrayList.add("Sale MLS Listing");

			// ///Refinance------------------3
		} else if (opportunity.getWhat_is_your_lending_goal() != null
				&& opportunity.getWhat_is_your_lending_goal().equalsIgnoreCase(
						"3")) {
			opportunity.setWhat_is_your_lending_goal("Refinance");

			HashMap map = getProperAddress(opportunity);
			if (!map.isEmpty()) {

		
				

				propertyDocumentsArrayList.add("Mortgage Statement ("
						+ map.get("address1") + ", " + map.get("city") + ")");
				propertyDocumentsArrayList.add("Property Tax Assessment ("
						+ map.get("address1") + ", " + map.get("city") + ")");

			}
			// propertyDocuments.add("Floor plans/Specs");
			propertyDocumentsArrayList.add("Fire Insurance");

		} else {
			opportunity.setWhat_is_your_lending_goal("Pre-Approval");

		}

		for (Applicant applicant : opportunity.getApplicants()) {

			if (applicant.applicant_name != null) {

				// if he is applicant then list the follwing doucments
				if (numberOfApplicants == 1) {
					applicantDocumentsList
							.setFirstName(applicant.applicant_name);
					applicantDocumentsList
							.setLastName(applicant.applicant_last_name);
					applicantDocumentsList
							.setEmailAddress(applicant.email_personal);

					try {
						if (applicant.bankruptcy != null & applicant.bankruptcy) {

							applicantCreditDocumentsArrayList
									.add("Bankruptcy Documents");

							applicantCreditDocumentsArrayList
									.add("Discharge Certificate");
						}

					} catch (Exception e) {
						logger.error("exception in get Brankruptcy " + e.getMessage());

					}
					try {

						if (applicant.getOrderly_debt_payment() != null
								&& applicant.getOrderly_debt_payment()) {

							applicantCreditDocumentsArrayList
									.add("Orderly Debt Payment (ODP) Documents");
						}
					} catch (Exception e) {
						logger.error("error in get Orderly Debt Payment (ODP) Documents"+e.getMessage());
					}
					try {

						for (String business : applicant.getCollections()) {
							applicantCreditDocumentsArrayList
									.add("Confirmation of paid collection with "
											+ business);

							applicantCreditDocumentsArrayList
									.add("Explanation of collection with "
											+ business);

						}

					} catch (Exception e) {
						logger.error("exception in get colletions " + e.getMessage());

					}

					try {
						if (applicant.getPayment() > 0) {

							applicantCreditDocumentsArrayList
									.add("Explanation for late payments");
						}

					} catch (Exception e) {
						logger.error("exception in get late paymenta" +e.getMessage());
					}

					for (Income currentIncome : applicant.incomes) {
						createIncomeDocsList(applicant, currentIncome,
								 applicantDocumentsArrayList);
					}

			
					applicantDocumentsArrayList
							.add("Copy of Photo ID (Driver's Licence or Passport)");
					createPropertyTasks(
							applicant.getProperties(),
							applicantDocumentsArrayList, opportunity);

					try{
					if ((applicant.getMonthlychildsupport()!=null&& applicant.getMonthlychildsupport() > 0) || (applicant.getRelationship_status()!=null && applicant.getRelationship_status().equalsIgnoreCase("Divorced")) ) {
						applicantDocumentsArrayList
								.add("Divorce/Separation Agreement");

					}
					}catch(Exception e){
						logger.error("error in listing Divorce/Separation Agreement "+e.getMessage());
					}

					if (applicant.non_resident || applicant.sin != null
							&& applicant.sin.startsWith("9")) {

								applicantDocumentsArrayList
								.add(" Valid work permit OR Verification of landed immigrant status");
						applicantDocumentsArrayList
								.add("International credit report");
						applicantDocumentsArrayList
								.add("Six (6) months Canadian account bank statements or Letter of reference from recognized financial institution");

					}
					

				} else {
					// if he if co_apllicant list the coApplicant details
					for (Income currentIncome : applicant.incomes) {
						createIncomeDocsList(applicant, currentIncome,
							
								co_ApplicantDocumentsArrayList);
					}
					applicantDocumentsList
							.setCo_firstName(applicant.applicant_name);
					applicantDocumentsList
							.setCo_lastName(applicant.applicant_last_name);
					applicantDocumentsList
							.setCo_emailAddress(applicant.email_personal);

					try {
						if (applicant.bankruptcy != null & applicant.bankruptcy) {

							co_ApplicantCreditDocumentsArrayList
									.add("Bankruptcy Documents");

							co_ApplicantCreditDocumentsArrayList
									.add("Discharge Certificate");
						}

					} catch (Exception e) {
						logger.error("excepetion in coapplicant get brankrotcyv document "
								+e.getMessage());
					}

					try {
						if (applicant.getOrderly_debt_payment() != null
								&& applicant.getOrderly_debt_payment()) {
							co_ApplicantCreditDocumentsArrayList
									.add("Orderly Debt Payment (ODP) Documents");
						}

					} catch (Exception e) {
						logger.error("excepetion in coapplicant get isOrderlyDebtPayment document "
								+e.getMessage());

					}
					try {
						for (String business : applicant.getCollections()) {

							co_ApplicantCreditDocumentsArrayList
									.add("Confirmation of paid collection with "
											+ business);

							co_ApplicantCreditDocumentsArrayList
									.add("Explanation of collection with "
											+ business);

						}

					} catch (Exception e) {
						logger.error("excepetion in coapplicant get getCollections document "
								+e.getMessage());

					}
					try {
						if (applicant.getPayment() > 0) {
							co_ApplicantCreditDocumentsArrayList
									.add("Explanation for late payments");
						}

					} catch (Exception e) {
						logger.error("excepetion in coapplicant get getLatePayment document "
								+e.getMessage());

					}
					// createImmigrantTasks(applicant, co_ApplicantDocuments);
					// createNonResidentTasks(applicant, co_ApplicantDocuments);
					
					co_ApplicantDocumentsArrayList
							.add("Copy of Photo ID (Driver's Licence or Passport)");
					createPropertyTasks(
							applicant.getProperties(),
							co_ApplicantDocumentsArrayList, opportunity);
					try{
						if ((applicant.getMonthlychildsupport()!=null&& applicant.getMonthlychildsupport() > 0) || (applicant.getRelationship_status()!=null && applicant.getRelationship_status().equalsIgnoreCase("Divorced")) ) {
							co_ApplicantDocumentsArrayList
									.add("Divorce/Separation Agreement");

						}
						}catch(Exception e){
							logger.error("error in listing Divorce/Separation Agreement "+e.getMessage());
						}
				
					if (applicant.non_resident || applicant.sin != null
							&& applicant.sin.startsWith("9")) {

						co_ApplicantDocumentsArrayList
								.add(" Valid work permit OR Verification of landed immigrant status");
						co_ApplicantDocumentsArrayList
								.add("International credit report");
						co_ApplicantDocumentsArrayList
								.add("Six (6) months Canadian account bank statements or Letter of reference from recognized financial institution");

					}
				

				}

			}

			numberOfApplicants = numberOfApplicants + 1;
		}

		// listing downpayment documents
		createDownPaymentTasks( opportunity,
				 downpaymentDocumentsArrayList);

		if (opportunity.getId() != 0) {
			applicantDocumentsList.setOpprtunityId(opportunity.getId() + "");
		}
		applicantDocumentsList.setLendingGoal(opportunity
				.getWhat_is_your_lending_goal());
	

		duplicateRemovedApplicantDocument = new LinkedHashSet<String>(
				applicantDocumentsArrayList);
		duplicateRemovedCoApplicantDocument = new LinkedHashSet<String>(
				co_ApplicantDocumentsArrayList);
		duplicateRemovedpropertyDocumentsArrayList = new LinkedHashSet<String>(
				propertyDocumentsArrayList);
		duplicateRemoveddownpaymentDocumentsArrayList = new LinkedHashSet<String>(
				downpaymentDocumentsArrayList);
		duplicateRemovedApplicantCreditInforamtion = new LinkedHashSet<String>(
				applicantCreditDocumentsArrayList);
		duplicateRemovedCoApplcantCreditInforamtion = new LinkedHashSet<String>(
				co_ApplicantCreditDocumentsArrayList);
		applicantDocumentsList.setDocuments(duplicateRemovedApplicantDocument);
		applicantDocumentsList.setCo_documents(duplicateRemovedCoApplicantDocument);
		applicantDocumentsList.setPropertyDocments(duplicateRemovedpropertyDocumentsArrayList);
		applicantDocumentsList.setDownPayments(duplicateRemoveddownpaymentDocumentsArrayList);
		applicantDocumentsList
				.setApplicantCreditDocuments(duplicateRemovedApplicantCreditInforamtion);
		applicantDocumentsList
				.setCo_applicantCreditDocuments(duplicateRemovedCoApplcantCreditInforamtion);

		if (numberOfApplicants == 2) {

			sendingMail(applicantDocumentsList,
					duplicateRemovedApplicantDocument,
					duplicateRemovedCoApplicantDocument,
					duplicateRemovedpropertyDocumentsArrayList,
					duplicateRemoveddownpaymentDocumentsArrayList,
					duplicateRemovedApplicantCreditInforamtion);

		} else if (numberOfApplicants > 2) {
			sendingMail(applicantDocumentsList,
					duplicateRemovedApplicantDocument,
					duplicateRemovedCoApplicantDocument,
					duplicateRemovedpropertyDocumentsArrayList,
					duplicateRemoveddownpaymentDocumentsArrayList,
					duplicateRemovedApplicantCreditInforamtion);
			try {
				if (applicantDocumentsList.getCo_documents() != null
						&& !applicantDocumentsList.getCo_documents().isEmpty()) {
					sendingMailCoApplicant(applicantDocumentsList,
							duplicateRemovedApplicantDocument,
							duplicateRemovedCoApplicantDocument,
							duplicateRemovedpropertyDocumentsArrayList,
							duplicateRemoveddownpaymentDocumentsArrayList,
							duplicateRemovedCoApplcantCreditInforamtion);
				}
			} catch (Exception e) {
				logger.error("coapllicant not exsist "+e.getMessage());
			}
		}

		return applicantDocumentsList;

	}

	// ------------------------------------supporting methods to list documents
	// of Opprtunity------------------

	private void createPurchaseTasks(
			Opportunity opportunity,ArrayList<String> propertyDocumentsArrayList) {
		if (opportunity.getMonthlyRentalIncome() != null
				&& opportunity.getMonthlyRentalIncome() > 0) {

			propertyDocumentsArrayList.add("Lease Agreement");

		}

	}

	private void createIncomeDocsList(Applicant applicant,
			Income currentIncome,
			ArrayList<String> incomeList) {
		play.Logger.info("current income type " + currentIncome);
		Calendar deadline1 = new GregorianCalendar();
		deadline1.add(Calendar.HOUR, 1);
		// Employeed =1   and Self-Employeed=2

		if (currentIncome.getIncome_type() != null) {
			if (currentIncome.getIncome_type().equalsIgnoreCase("1")
					|| currentIncome.getIncome_type().equalsIgnoreCase("2")) {

				Calendar year1 = Calendar.getInstance();
				int currentMonth = year1.get(Calendar.MONTH) + 1;
				int year = year1.get(Calendar.YEAR) - 1;
				
				logger.debug("current month = "+currentMonth);
				 if (currentIncome.getIncome_type().equalsIgnoreCase("2")) {
					if (currentMonth < 3) {
						
					for (Iterator iterator = incomeList.iterator(); iterator
							.hasNext();) {
						String string = (String) iterator.next();
						if(string.contains("Notice of Assessment")){
							incomeList.remove(string);
						}
						
					}
								incomeList.add(year
								- 1
								+ " Notice of Assessment  &  T1 Generals w/statement of business activities");
						incomeList.add(year
								- 2
								+ " Notice of Assessment &  T1 Generals w/statement of business activities");
					}
				} else if (currentIncome.getIncome_type().equalsIgnoreCase("1")) {
					incomeList.add("Letter of Employment");
					incomeList.add("Current Paystub");
					if (currentMonth < 3) {
						incomeList.add(year
								+ " December 31st Pay Stub showing Year to Date Income");
						 boolean exist=false;
						 for (Iterator iterator = incomeList.iterator(); iterator
									.hasNext();) {
								String string = (String) iterator.next();
								if(string.contains("T1 Generals w/statement of business activities"))
									exist=true;
									break;
									
								
							}
						 if(!exist){
						incomeList.add(year - 1 + " Notice of Assessment");
						incomeList.add(year - 2 + " Notice of Assessment");
						 }

					}

				}

				if (currentMonth >= 3 && currentMonth < 5) {

					int startYear = year1.get(Calendar.YEAR) - 1;

					int priorYear = startYear - 1;
					int priorYear2 = startYear - 2;

					 if (currentIncome.getIncome_type().equalsIgnoreCase(
							"1")) {
						 boolean exist=false;
						 for (Iterator iterator = incomeList.iterator(); iterator
									.hasNext();) {
								String string = (String) iterator.next();
								if(string.contains("T1 Generals w/statement of business activities"))
									exist=true;
									break;
									
								
							}
						 if(!exist){
						incomeList.add(priorYear + " Notice of Assessment");
						 }
						incomeList.add(startYear + " T4");

					} else if (currentIncome.getIncome_type().equalsIgnoreCase(
							"2")) {
						
						for (Iterator iterator = incomeList.iterator(); iterator
								.hasNext();) {
							String string = (String) iterator.next();
							if(string.contains("Notice of Assessment")){
								incomeList.remove(string);
							}
							
						}
						incomeList.add(priorYear
								+ " Notice of Assessment & T1 Generals w/statement of business activities");
						incomeList.add(priorYear2
								+ " Notice of Assessment  & T1 Generals w/statement of business activities");

					}

				} else if (currentMonth >= 5) {
					int startYear = year1.get(Calendar.YEAR) - 1;

					int priorYear = startYear - 1;

					 if (currentIncome.getIncome_type().equalsIgnoreCase(
							"1")) {
						 boolean exist=false;
						 for (Iterator iterator = incomeList.iterator(); iterator
									.hasNext();) {
								String string = (String) iterator.next();
								if(string.contains("T1 Generals w/statement of business activities"))
									exist=true;
									break;
									
								
							}
						 if(!exist){
						incomeList.add(priorYear + " Notice of Assessment");
						incomeList.add(startYear + " Notice of Assessment");
						 }
					} else if (currentIncome.getIncome_type().equalsIgnoreCase(
							"2")) {
						
						for (Iterator iterator = incomeList.iterator(); iterator
								.hasNext();) {
							String string = (String) iterator.next();
							if(string.contains("Notice of Assessment")){
								incomeList.remove(string);
							}
							
						}
		incomeList.add(priorYear
								+ " Notice of Assessment  & T1 Generals w/statement of business activities");
						incomeList.add(startYear
								+ " Notice of Assessment  & T1 Generals w/statement of business activities");
					}
				}

			}
			if (currentIncome.getIncome_type().equalsIgnoreCase("10")
					|| currentIncome.getIncome_type().equalsIgnoreCase("10")) {

				incomeList.add("Confirmation of child tax credit");

			}
			if (currentIncome.getIncome_type().equalsIgnoreCase("11")) {

				incomeList.add("Living Allowance");

			}
			if (currentIncome.getIncome_type().equalsIgnoreCase("12")) {

				incomeList.add("Vehicle Allownce Statement");

			}
			if (currentIncome.getIncome_type().equalsIgnoreCase("8")) {

				incomeList.add("Bonus Statement");

			}
			if (currentIncome.getIncome_type().equalsIgnoreCase("4")) {

				incomeList.add("Commission Statement");

			}
			if (currentIncome.getIncome_type().equalsIgnoreCase("5")) {


				incomeList.add("Interest Statements");

			}
			if (currentIncome.getIncome_type().equalsIgnoreCase("7")) {

				incomeList.add("Overtime Statements");

			}
			if (currentIncome.getIncome_type().equalsIgnoreCase("6")) {


				incomeList.add("3 Months Bank Statements");

			}
			if (currentIncome.getIncome_type().equalsIgnoreCase("3")) {

				incomeList.add("3 Months Bank Statements");

			}
		}

	}

	private void createPropertyTasks(
			List<Property> properties,
			ArrayList<String> arrayList, Opportunity opportunity) {
		HashMap mapData = getProperAddress(opportunity);

		for (Property currentProperty : properties) {
			// split full address to address ,city
			HashMap map = getProperAddress(currentProperty.getName());

			if (currentProperty.getName() != null
					&& currentProperty.mortgageYesNo != null
					&& currentProperty.mortgageYesNo.equalsIgnoreCase("Yes")) {

				if (!map.isEmpty()) {
					if (!mapData.isEmpty()) {
						if (map.get("address1")
								.toString()
								.trim()
								.equalsIgnoreCase(
										mapData.get("address1").toString()
												.trim())
								&& map.get("city")
										.toString()
										.trim()
										.equalsIgnoreCase(
												mapData.get("city").toString()
														.trim())) {

						} else {
						
							arrayList.add("Mortgage Statement ("
									+ map.get("address1") + ", "
									+ map.get("city") + ")");
							arrayList.add("Property Tax Assessment ("
									+ map.get("address1") + ", "
									+ map.get("city") + ")");

						}
					} else {
						
						arrayList.add("Mortgage Statement ("
								+ map.get("address1") + ", " + map.get("city")
								+ ")");
						arrayList.add("Property Tax Assessment ("
								+ map.get("address1") + ", " + map.get("city")
								+ ")");
					}

				}

			} else {
				if (!map.isEmpty()) {

					if (!mapData.isEmpty()) {
						if (map.get("address1")
								.toString()
								.trim()
								.equalsIgnoreCase(
										mapData.get("address1").toString()
												.trim())
								&& map.get("city")
										.toString()
										.trim()
										.equalsIgnoreCase(
												mapData.get("city").toString()
														.trim())) {

						} else {
							

							arrayList.add("Property Tax Assessment ("
									+ map.get("address1") + ", "
									+ map.get("city") + ")");
						}
					} else {
					

						arrayList.add("Property Tax Assessment ("
								+ map.get("address1") + ", " + map.get("city")
								+ ")");
					}
				}
			}

			if (currentProperty.getRentalProperty() != null
					&& currentProperty.getRentalProperty().equalsIgnoreCase(
							"yes")) {
				if (!map.isEmpty()) {
					
					arrayList.add("Lease Agreement (" + map.get("address1")
							+ ", " + map.get("city") + ")");
				}
			}
		}

	}

	private void createDownPaymentTasks(
			Opportunity opportunity,
			ArrayList<String> downPayementSoursce) {

		if (opportunity.getDown_payment_amount() != null) {

			if (opportunity.getDown_payment_amount() > 0) {

			}

			if (opportunity.getBank_account() != null
					&& opportunity.getBank_account() > 0) {

				downPayementSoursce.add("90 Days History Bank Statements");
			}
			if (opportunity.getPersonalCashAmount() != null
					&& opportunity.getPersonalCashAmount() > 0) {

				downPayementSoursce.add("90 Days History Investment");
			}
			if (opportunity.getRrsp_amount() != null
					&& opportunity.getRrsp_amount() > 0) {

				downPayementSoursce.add("90 Days History RRSP");
			}
			if (opportunity.getGifted_amount() != null
					&& opportunity.getGifted_amount() > 0) {

				downPayementSoursce.add("Gift Letter");
			}
			if (opportunity.getBorrowed_amount() != null
					&& opportunity.getBorrowed_amount() > 0) {

					downPayementSoursce
						.add("Verification of Loan, Line of Credit or Credit Card Statement");
			}
			/*
			 * if (opportunity.getSaleOfExistingAmount() != null &&
			 * opportunity.getSaleOfExistingAmount() > 0) {
			 * 
			 * downPayementSoursce.add("Sale MLS Listing");
			 * downPayementSoursce.add("Sale MLS Listing"); }
			 */
			if (opportunity.getExistingEquityAmount() != null
					&& opportunity.getExistingEquityAmount() > 0) {

				downPayementSoursce.add("Offer to Sale of Existing");
			}
			if (opportunity.getSweat_equity_amount() != null
					&& opportunity.getSweat_equity_amount() > 0) {

				downPayementSoursce
						.add("Verification of Property Address with Sweat Equity");
			}
			if (opportunity.getSecondaryFinancingAmount() != null
					&& opportunity.getSecondaryFinancingAmount() > 0) {
		downPayementSoursce
						.add("Verification of Property Address with Secondary Financing (Loan Agreement)");
			}
			if (opportunity.getOther_amount() != null
					&& opportunity.getOther_amount() > 0) {

				downPayementSoursce
						.add("Verification of Explanation in writing of other Source of Downpayment");
					}
		}

	}
	
	private Set<String> getSetOfIncomes(){
		
		return null;
	}

	// split address

	public HashMap getProperAddress(String address) {
		String add[] = null;
		HashMap map = new HashMap();
		logger.debug("insisde split address method with given address " + address);
		if (address == null) {
			return map;
		} else {
			try {
				add = address.split(",");

				map.put("address1", add[0].trim());
				map.put("city", add[1].trim());
				String code[] = add[2].trim().split(" ");
				map.put("Province", code[0]);
				map.put("postalcode", code[1] + " " + code[2]);
				map.put("country", add[3].trim());

			} catch (Exception e) {
				logger.error("error in spliting address" +e.getMessage());
			}

		}
		return map;
	}

	public HashMap getProperAddress(Opportunity opportunity) {
		HashMap map = new HashMap();
		if (opportunity == null) {
			return map;
		} else {
			try {
				if (opportunity.getAddress() != null) {
					map.put("address1", opportunity.getAddress());

				}
				if (opportunity.getCity() != null) {
					map.put("city", opportunity.getCity());

				}

			} catch (Exception e) {
				logger.error("error in spliting address"+e.getMessage());
			}
		}
		return map;
	}

	public void sendingMail(ApplicantDocument applicantDocumentList,
			Set<String> applicantDocumentsArrayList,
			Set<String> co_ApplicantDocumentsArrayList,
			Set<String> propertyDocumentsArrayList,
			Set<String> downpaymentDocumentsArrayList,
			Set<String> creditDocumenList) {

		try {
			SendWithUsMail sendWithUsMail = new SendWithUsMail();
			if (applicantDocumentList.getLendingGoal().equalsIgnoreCase(
					"Purchase")) {
				SendWithUsMail.mailTemplateOfPurchaseDocuments(
						applicantDocumentList, applicantDocumentsArrayList,
						co_ApplicantDocumentsArrayList,
						propertyDocumentsArrayList,
						downpaymentDocumentsArrayList, creditDocumenList);

			} else if (applicantDocumentList.getLendingGoal().equalsIgnoreCase(
					"Refinance")) {
				SendWithUsMail.mailTemplateOfRefinanceDocuments(
						applicantDocumentList, applicantDocumentsArrayList,
						co_ApplicantDocumentsArrayList,
						propertyDocumentsArrayList,
						downpaymentDocumentsArrayList, creditDocumenList);

			} else if (applicantDocumentList.getLendingGoal().equalsIgnoreCase(
					"Pre-Approval")) {

				sendWithUsMail.mailTemplateOfPreeApprovalDocuments(
						applicantDocumentList, applicantDocumentsArrayList,
						co_ApplicantDocumentsArrayList,
						propertyDocumentsArrayList,
						downpaymentDocumentsArrayList, creditDocumenList);

			}
		} catch (Exception e) {

			logger.error("error in Sending Mail"+e.getMessage());
		}

	}

	public void sendingMailCoApplicant(ApplicantDocument applicantDocumentList,
			Set<String> applicantDocumentsArrayList,
			Set<String> co_ApplicantDocumentsArrayList,
			Set<String> propertyDocumentsArrayList,
			Set<String> downpaymentDocumentsArrayList,
			Set<String> creditDocumenList) {

		try {
			SendWithUsMail sendWithUsMail = new SendWithUsMail();
			if (applicantDocumentList.getLendingGoal().equalsIgnoreCase(
					"Purchase")) {
				SendWithUsMail.mailTemplateOfCoApplicantPurchaseDocuments(
						applicantDocumentList, applicantDocumentsArrayList,
						co_ApplicantDocumentsArrayList,
						propertyDocumentsArrayList,
						downpaymentDocumentsArrayList, creditDocumenList);

			} else if (applicantDocumentList.getLendingGoal().equalsIgnoreCase(
					"Refinance")) {
				SendWithUsMail.mailTemplateOfCoApplicantRefinanceDocuments(
						applicantDocumentList, applicantDocumentsArrayList,
						co_ApplicantDocumentsArrayList,
						propertyDocumentsArrayList,
						downpaymentDocumentsArrayList, creditDocumenList);

			} else if (applicantDocumentList.getLendingGoal().equalsIgnoreCase(
					"Pre-Approval")) {

				sendWithUsMail.mailTemplateOfPreeApprovalCoApplicantDocuments(
						applicantDocumentList, applicantDocumentsArrayList,
						co_ApplicantDocumentsArrayList,
						propertyDocumentsArrayList,
						downpaymentDocumentsArrayList, creditDocumenList);
			}
		} catch (Exception e) {

			logger.error("error in sending Mail to CoApplicant"+e.getMessage());
		}

	}

}
