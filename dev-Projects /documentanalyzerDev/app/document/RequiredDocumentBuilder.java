package document;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dto.Applicant;
import dto.ApplicantDocument;
import dto.Income;
import dto.Opportunity;
import dto.Property;
import email.SendWithUsMail;

public class RequiredDocumentBuilder {
	static Logger log = LoggerFactory.getLogger(RequiredDocumentBuilder.class);
	StringBuilder listOfDocsNeeded = null;
	// ApplicantDocumentclass to store list of documents
	ApplicantDocument applicantDocumentsList = new ApplicantDocument();
	Set<String> applicantDocuments = new HashSet<String>();
	Set<String> co_ApplicantDocuments = new HashSet<String>();
	Set<String> propertyDocuments = new HashSet<String>();
	Set<String> downpaymentDocuments = new HashSet<String>();
	Set<String> applicantCreditDocuments = new HashSet<String>();
	Set<String> coApplicantCreditDocuments = new HashSet<String>();

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

	public ApplicantDocument RequiredDocumentBuilderMethod(
			Opportunity opportunity) {
		log.debug("inside document builder class -----------");
		int numberOfApplicants = 1;

		// listing property documents based on leading goals  //Purchase
		if (opportunity.getWhat_is_your_lending_goal() != null
				&& opportunity.getWhat_is_your_lending_goal().equalsIgnoreCase("2")) {
			opportunity.setWhat_is_your_lending_goal("Purchase");
			HashMap map = getProperAddress(opportunity);
			if (!map.isEmpty()) {

				propertyDocuments.add("Offer to Purchase ("
						+ map.get("address1") + ", " + map.get("city") + ")");
				propertyDocumentsArrayList.add("Offer to Purchase ("
						+ map.get("address1") + ", " + map.get("city") + ")");

				if (opportunity.getMls() != null) {

					if (opportunity.getMls().equalsIgnoreCase("MLS Listed")) {
						propertyDocuments.add("MLS Listing ("
								+ map.get("address1") + ", " + map.get("city")
								+ ")");
						propertyDocumentsArrayList.add("MLS Listing ("
								+ map.get("address1") + ", " + map.get("city")
								+ ")");

					} else if (opportunity.getMls().equalsIgnoreCase(
							"New Build")) {
						propertyDocuments.add("Floor Plans/Specs");
						propertyDocumentsArrayList.add("Floor Plans/Specs");
					}
				}

				createPurchaseTasks(listOfDocsNeeded, opportunity);
				if (opportunity.getIsRentalProperty() != null
						&& opportunity.getIsRentalProperty() == true) {
					propertyDocuments.add("Appraisal and Schedule A");
					propertyDocumentsArrayList.add("Appraisal and Schedule A");
				}

			}

			propertyDocuments.add("Sale MLS Listing");
			propertyDocumentsArrayList.add("Sale MLS Listing");
		
			
			/////Refinance------------------3
		} else if (opportunity.getWhat_is_your_lending_goal() != null
				&& opportunity.getWhat_is_your_lending_goal().equalsIgnoreCase(
						"3")) {
			opportunity.setWhat_is_your_lending_goal("Refinance");

			HashMap map = getProperAddress(opportunity);
			if (!map.isEmpty()) {

				propertyDocuments.add("Mortgage Statement ("
						+ map.get("address1") + ", " + map.get("city") + ")");
				propertyDocuments.add("Property Tax Assessment ("
						+ map.get("address1") + ", " + map.get("city") + ")");

				propertyDocumentsArrayList.add("Mortgage Statement ("
						+ map.get("address1") + ", " + map.get("city") + ")");
				propertyDocumentsArrayList.add("Property Tax Assessment ("
						+ map.get("address1") + ", " + map.get("city") + ")");

			}
			// propertyDocuments.add("Floor plans/Specs");
			propertyDocuments.add("Fire Insurance");
			propertyDocumentsArrayList.add("Fire Insurance");

		} else{
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
						if (applicant.bankruptcy!=null&applicant.bankruptcy) {
						

							applicantCreditDocumentsArrayList
									.add("Bankruptcy Document");

							applicantCreditDocumentsArrayList
									.add("Discharge Certificate");
						}

					} catch (Exception e) {
						log.error("exception in get Brankruptcy " + e);

					}
					try {

						if (applicant.getOrderly_debt_payment()!=null && applicant.getOrderly_debt_payment()) {

						
							applicantCreditDocumentsArrayList
									.add("Orderly Debt Payment (ODP) Documents");
						}
					} catch (Exception e) {
						log.error("error in get Orderly Debt Payment (ODP) Documents");
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
						log.error("exception in get colletions " + e);

					}

					try {
						if ( applicant.getPayment()>0) {


							applicantCreditDocumentsArrayList
									.add("Explanation for late payments");
						}

					} catch (Exception e) {
						log.error("exception in get late paymenta" + e);
					}

					for (Income currentIncome : applicant.incomes) {
						createIncomeDocsList(applicant, currentIncome,
								applicantDocuments, applicantDocumentsArrayList);
					}

					applicantDocuments
							.add("Copy of Photo ID (Driver's Licence or Passport)");
					applicantDocumentsArrayList
							.add("Copy of Photo ID (Driver's Licence or Passport)");
					createPropertyTasks(listOfDocsNeeded,
							applicant.getProperties(), applicantDocuments,
							applicantDocumentsArrayList, opportunity);
					log.info("Applicants documents-----------"
							+ applicantDocuments);

				} else {
					// if he if co_apllicant list the coApplicant details
					for (Income currentIncome : applicant.incomes) {
						createIncomeDocsList(applicant, currentIncome,
								co_ApplicantDocuments,
								co_ApplicantDocumentsArrayList);
					}
					log.debug("co_ApplicantDocuments" + co_ApplicantDocuments);
					applicantDocumentsList
							.setCo_firstName(applicant.applicant_name);
					applicantDocumentsList
							.setCo_lastName(applicant.applicant_last_name);
					applicantDocumentsList
							.setCo_emailAddress(applicant.email_personal);

					

					try {
						if (applicant.bankruptcy!=null&applicant.bankruptcy) {

							co_ApplicantCreditDocumentsArrayList
									.add("Bankruptcy Document");

							co_ApplicantCreditDocumentsArrayList
									.add("Discharge Certificate");
						}

					} catch (Exception e) {
						log.error("excepetion in coapplicant get brankrotcyv document "
										+ e);
					}

					try {
						if (applicant.getOrderly_debt_payment()!=null && applicant.getOrderly_debt_payment()) {
							co_ApplicantCreditDocumentsArrayList
									.add("Orderly Debt Payment (ODP) Documents");
						}

					} catch (Exception e) {
						log.error("excepetion in coapplicant get isOrderlyDebtPayment document "
										+ e);

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
						log.error("excepetion in coapplicant get getCollections document "
										+ e);

					}
					try {
						if (applicant.getPayment()>0) {
							co_ApplicantCreditDocumentsArrayList
									.add("Explanation for late payments");
						}

					} catch (Exception e) {
						log.error("excepetion in coapplicant get getLatePayment document "
										+ e);

					}
					// createImmigrantTasks(applicant, co_ApplicantDocuments);
					// createNonResidentTasks(applicant, co_ApplicantDocuments);
					co_ApplicantDocuments
							.add("Copy of Photo ID (Driver's Licence or Passport)");
					co_ApplicantDocumentsArrayList
							.add("Copy of Photo ID (Driver's Licence or Passport)");
					createPropertyTasks(listOfDocsNeeded,
							applicant.getProperties(), co_ApplicantDocuments,
							co_ApplicantDocumentsArrayList, opportunity);
					log.info("Co_Applicants documents-----------"
							+ co_ApplicantDocuments);

				}

			}

			numberOfApplicants = numberOfApplicants + 1;
		}

		// listing downpayment documents
		createDownPaymentTasks(listOfDocsNeeded, opportunity,
				downpaymentDocuments, downpaymentDocumentsArrayList);

		if (opportunity.getId()!=0) {
			applicantDocumentsList.setOpprtunityId(opportunity.getId()+"");
		}
		applicantDocumentsList.setLendingGoal(opportunity
				.getWhat_is_your_lending_goal());
		applicantDocumentsList.setDocuments(applicantDocuments);
		applicantDocumentsList.setCo_documents(co_ApplicantDocuments);
		applicantDocumentsList.setPropertyDocments(propertyDocuments);
		applicantDocumentsList.setDownPayments(downpaymentDocuments);

		
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
	
		applicantDocumentsList
				.setApplicantCreditDocuments(duplicateRemovedApplicantCreditInforamtion);
		applicantDocumentsList
				.setCo_applicantCreditDocuments(duplicateRemovedCoApplcantCreditInforamtion);


		if (numberOfApplicants ==2) {
			
			sendingMail(applicantDocumentsList,
					duplicateRemovedApplicantDocument,
					duplicateRemovedCoApplicantDocument,
					duplicateRemovedpropertyDocumentsArrayList,
					duplicateRemoveddownpaymentDocumentsArrayList,
					duplicateRemovedApplicantCreditInforamtion);

		} else if (numberOfApplicants>2) {
					sendingMail(applicantDocumentsList,
					duplicateRemovedApplicantDocument,
					duplicateRemovedCoApplicantDocument,
					duplicateRemovedpropertyDocumentsArrayList,
					duplicateRemoveddownpaymentDocumentsArrayList,
					duplicateRemovedApplicantCreditInforamtion);
			try{
			if(applicantDocumentsList.getCo_documents()!=null && !applicantDocumentsList.getCo_documents().isEmpty()){
			sendingMailCoApplicant(applicantDocumentsList,
					duplicateRemovedApplicantDocument,
					duplicateRemovedCoApplicantDocument,
					duplicateRemovedpropertyDocumentsArrayList,
					duplicateRemoveddownpaymentDocumentsArrayList,
					duplicateRemovedCoApplcantCreditInforamtion);
		}
			}catch(Exception e){
				log.error("coapllicant not exsist "+e);
			}
		}

		return applicantDocumentsList;

	}

	// ------------------------------------supporting methods to list documents
	// of Opprtunity------------------

	private void createPurchaseTasks(StringBuilder listOfDocsNeeded,
			Opportunity opportunity) {
		if (opportunity.getMonthlyRentalIncome() != null
				&& opportunity.getMonthlyRentalIncome() > 0) {

			propertyDocuments.add("Lease Agreement");

		}

	}

	private void createIncomeDocsList(Applicant applicant,
			Income currentIncome, Set<String> incomesList,
			ArrayList<String> arralist) {

		Calendar deadline1 = new GregorianCalendar();
		deadline1.add(Calendar.HOUR, 1);
		//Employeed Or Self-Employeed
		if (currentIncome.getIncome_type().equalsIgnoreCase("1")
				|| currentIncome.getIncome_type()
						.equalsIgnoreCase("2")) {

			Calendar year1 = Calendar.getInstance();
			int currentMonth = year1.get(Calendar.MONTH) + 1;
			int year=year1.get(Calendar.YEAR)-1;
			if (currentIncome.getIncome_type().equalsIgnoreCase("1")) {

				incomesList.add("Letter of Employment");
				incomesList.add("Current Paystub");
				arralist.add("Letter of Employment");
				arralist.add("Current Paystub");
				if (currentMonth < 3) {
					incomesList
							.add(year+" December 31st Pay Stub showing Year to Date Income");
					arralist.add(year+" December 31st Pay Stub showing Year to Date Income");
			
					incomesList.add(year-1 + " Notice of Assessment");
					incomesList.add(year-2 + " Notice of Assessment");
					arralist.add(year-1 + " Notice of Assessment");
					arralist.add(year-2 + " Notice of Assessment");
					
					
				}
			}

			if (currentMonth >= 3 && currentMonth < 5) {

				int startYear = year1.get(Calendar.YEAR) - 1;

				int priorYear = startYear - 1;
				incomesList.add(priorYear + " Notice of Assessment");
				arralist.add(priorYear + " Notice of Assessment");

				if (currentIncome.getIncome_type()
						.equalsIgnoreCase("2")) {
					int priorYear2 = startYear - 2;
					incomesList.add(priorYear2 + " Notice of Assessment");
					arralist.add(priorYear2 + " Notice of Assessment");

				}
				if (currentIncome.getIncome_type().equalsIgnoreCase("1")) {
					incomesList.add(startYear + " T4");
					arralist.add(startYear + " T4");

				}

			} else if (currentMonth >= 5) {
				int startYear = year1.get(Calendar.YEAR) - 1;

				int priorYear = startYear - 1;
				incomesList.add(priorYear + " Notice of Assessment");
				incomesList.add(startYear + " Notice of Assessment");
				arralist.add(priorYear + " Notice of Assessment");
				arralist.add(startYear + " Notice of Assessment");
			}

		} else if (currentIncome.getIncome_type().equalsIgnoreCase("9")) {

			// incomesList.add("Lease agreement or schedule A for the property ");

		}

		else if (currentIncome.getIncome_type().equalsIgnoreCase("Spouse Support")
				|| currentIncome.getIncome_type().equalsIgnoreCase("10")) {

			incomesList.add("Divorce/Separation Agreement");

		} else if (currentIncome.getIncome_type()
				.equalsIgnoreCase("10")) {

			incomesList.add("Child Tax Credit");

		} else if (currentIncome.getIncome_type()
				.equalsIgnoreCase("11")) {

			incomesList.add("Living Allowance");
		} else if (currentIncome.getIncome_type().equalsIgnoreCase("12")) {

			incomesList.add("Vehicle Allownce Statement");

		} else if (currentIncome.getIncome_type().equalsIgnoreCase("8")) {

			incomesList.add("Bonus Statement");

		} else if (currentIncome.getIncome_type().equalsIgnoreCase("4")) {

			incomesList.add("Commission Statement");

		} else if (currentIncome.getIncome_type().equalsIgnoreCase("5")) {

			incomesList.add("Interest Statements");

		} else if (currentIncome.getIncome_type().equalsIgnoreCase("7")) {

			incomesList.add("Overtime Statements");

		} else if (currentIncome.getIncome_type().equalsIgnoreCase("6")) {

			incomesList.add("3 Months Bank Statements");

		} else if (currentIncome.getIncome_type().equalsIgnoreCase("3")) {

			incomesList.add("3 Months Bank Statements");

		}

	}

	private void createPropertyTasks(StringBuilder listOfDocsNeeded,
			List<Property> properties, Set<String> propertys,
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
							propertys.add("Mortgage Statement ("
									+ map.get("address1") + ", "
									+ map.get("city") + ")");
							propertys.add("Property Tax Assessment ("
									+ map.get("address1") + ", "
									+ map.get("city") + ")");
							arrayList.add("Mortgage Statement ("
									+ map.get("address1") + ", "
									+ map.get("city") + ")");
							arrayList.add("Property Tax Assessment ("
									+ map.get("address1") + ", "
									+ map.get("city") + ")");

						}
					} else {
						propertys.add("Mortgage Statement ("
								+ map.get("address1") + ", " + map.get("city")
								+ ")");
						propertys.add("Property Tax Assessment ("
								+ map.get("address1") + ", " + map.get("city")
								+ ")");
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
							propertys.add("Property Tax Assessment ("
									+ map.get("address1") + ", "
									+ map.get("city") + ")");

							arrayList.add("Property Tax Assessment ("
									+ map.get("address1") + ", "
									+ map.get("city") + ")");
						}
					} else {
						propertys.add("Property Tax Assessment ("
								+ map.get("address1") + ", " + map.get("city")
								+ ")");

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
					propertys.add("Lease Agreement (" + map.get("address1")
							+ ", " + map.get("city") + ")");
					arrayList.add("Lease Agreement (" + map.get("address1")
							+ ", " + map.get("city") + ")");
				}
			}
		}

	}

	private void createDownPaymentTasks(StringBuilder listOfDocsNeeded,
			Opportunity opportunity, Set<String> downPayementSoursce,
			ArrayList<String> arrayList) {

		if (opportunity.getDown_payment_amount() != null) {

			if (opportunity.getDown_payment_amount() > 0) {

			}

			if (opportunity.getBank_account() != null
					&& opportunity.getBank_account() > 0) {

				downPayementSoursce.add("90 Days History Bank Statements");
				arrayList.add("90 Days History Bank Statements");
			}
			if (opportunity.getPersonalCashAmount() != null
					&& opportunity.getPersonalCashAmount() > 0) {

				downPayementSoursce.add("90 Days History Investment");
				arrayList.add("90 Days History Investment");
			}
			if (opportunity.getRrsp_amount() != null
					&& opportunity.getRrsp_amount() > 0) {

				downPayementSoursce.add("90 Days History RRSP");
				arrayList.add("90 Days History RRSP");
			}
			if (opportunity.getGifted_amount() != null
					&& opportunity.getGifted_amount() > 0) {

				downPayementSoursce.add("Gift Letter");
				arrayList.add("Gift Letter");
			}
			if (opportunity.getBorrowed_amount() != null
					&& opportunity.getBorrowed_amount() > 0) {

				downPayementSoursce
						.add("Verification of Loan, Line of Credit or Credit Card Statement");
				arrayList
						.add("Verification of Loan, Line of Credit or Credit Card Statement");
			}
			/*
			 * if (opportunity.getSaleOfExistingAmount() != null &&
			 * opportunity.getSaleOfExistingAmount() > 0) {
			 * 
			 * downPayementSoursce.add("Sale MLS Listing");
			 * arrayList.add("Sale MLS Listing"); }
			 */
			if (opportunity.getExistingEquityAmount() != null
					&& opportunity.getExistingEquityAmount() > 0) {

				downPayementSoursce.add("Offer to Sale of Existing");
				arrayList.add("Offer to Sale of Existing");
			}
			if (opportunity.getSweat_equity_amount() != null
					&& opportunity.getSweat_equity_amount() > 0) {

				downPayementSoursce
						.add("Verification of Property Address with Sweat Equity");
				arrayList
						.add("Verification of Property Address with Sweat Equity");
			}
			if (opportunity.getSecondaryFinancingAmount() != null
					&& opportunity.getSecondaryFinancingAmount() > 0) {

				downPayementSoursce
						.add("Verification of Property Address with Secondary Financing (Loan Agreement)");
				arrayList
						.add("Verification of Property Address with Secondary Financing (Loan Agreement)");
			}
			if (opportunity.getOther_amount() != null
					&& opportunity.getOther_amount() > 0) {

				downPayementSoursce
						.add("Verification of Explanation in writing of other Source of Downpayment");
				arrayList
						.add("Verification of Explanation in writing of other Source of Downpayment");
			}
		}

	}

	// split address
	
	public HashMap getProperAddress(String address) {
		String add[] = null;
		HashMap map = new HashMap();
		log.debug("insisde split address method with given address " + address);
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
				log.error("error in spliting address" + e);
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
				if(opportunity.getAddress()!=null){
					map.put("address1", opportunity.getAddress());

				}
				if(opportunity.getCity()!=null){
					map.put("city", opportunity.getCity());

				}

			} catch (Exception e) {
				log.error("error in spliting address" + e);
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

			e.printStackTrace();
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

			e.printStackTrace();
		}

	}
	
}
