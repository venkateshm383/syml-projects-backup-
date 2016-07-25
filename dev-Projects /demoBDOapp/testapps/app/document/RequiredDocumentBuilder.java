package document;

import helper.Odoo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;



import dto.Applicant;
import dto.ApplicantDocument;
import dto.Income;
import dto.Opportunity;
import dto.Property;
import email.SendWithUsMail;

public class RequiredDocumentBuilder {
	
	private static org.slf4j.Logger logger = play.Logger.underlying();
	
	StringBuilder listOfDocsNeeded = null;
	// ApplicantDocumentclass to store list of documents
	ApplicantDocument applicantDocumentsList = new ApplicantDocument();
	Set<String> applicantDocuments = new HashSet<String>();
	Set<String> co_ApplicantDocuments = new HashSet<String>();
	Set<String> propertyDocuments = new HashSet<String>();
	Set<String> downpaymentDocuments = new HashSet<String>();

	ArrayList<String> applicantDocumentsArrayList = new ArrayList<String>();
	ArrayList<String> co_ApplicantDocumentsArrayList = new ArrayList<String>();

	ArrayList<String> propertyDocumentsArrayList = new ArrayList<String>();

	ArrayList<String> downpaymentDocumentsArrayList = new ArrayList<String>();
	
	
	Set<String> duplicateRemovedApplicantDocument=null;
	Set<String> duplicateRemovedCoApplicantDocument=null;
	Set<String> duplicateRemovedpropertyDocumentsArrayList=null;
	Set<String> duplicateRemoveddownpaymentDocumentsArrayList=null;

	public ApplicantDocument RequiredDocumentBuilderMethod(
			Opportunity opportunity) {
        logger.debug("inside document builder class -----------");
		int numberOfApplicants = 1;

		// listing property documents based on leading goals
		if (opportunity.getWhatIsYourLendingGoal() != null
				&& opportunity.getWhatIsYourLendingGoal().equalsIgnoreCase(
						"Purchase")) {
			HashMap map = getProperAddress(opportunity.getAddress());
			if (!map.isEmpty()) {

			    propertyDocuments.add("Offer to Purchase ("+map.get("address1") + ", " + map.get("city") + ")");
							propertyDocumentsArrayList.add("Offer to Purchase ("+ map.get("address1") + ", " + map.get("city") + ")");
								
							if (opportunity.getMls()!=null){	
								
							if(opportunity.getMls().equalsIgnoreCase("MLS Listed")){	
								propertyDocuments.add("MLS Listing (" + map.get("address1") + ", " + map.get("city") + ")");
								propertyDocumentsArrayList.add("MLS Listing (" + map.get("address1") + ", " + map.get("city") + ")");
							
							}else if (opportunity.getMls().equalsIgnoreCase("New Build")){
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
				
				
				/*propertyDocuments.add("Offer to Purchase ("
						+ map.get("address1") + ", " + map.get("city") + ")");
				propertyDocuments.add("MLS Listing (" + map.get("address1")
						+ ", " + map.get("city") + ")");
				propertyDocuments.add("Mortgage Statement ("
						+ map.get("address1") + ", " + map.get("city") + ")");
				propertyDocuments.add("Property Tax Assessment ("
						+ map.get("address1") + ", " + map.get("city") + ")");

				propertyDocumentsArrayList.add("Offer to Purchase ("
						+ map.get("address1") + ", " + map.get("city") + ")");
				propertyDocumentsArrayList.add("MLS Listing ("
						+ map.get("address1") + ", " + map.get("city") + ")");
				propertyDocumentsArrayList.add("Mortgage Statement ("
						+ map.get("address1") + ", " + map.get("city") + ")");
				propertyDocumentsArrayList.add("Property Tax Assessment ("
						+ map.get("address1") + ", " + map.get("city") + ")");
*/
			}
		
			propertyDocuments.add("Sale MLS Listing");
			propertyDocumentsArrayList.add("Sale MLS Listing");
		//	propertyDocuments.add("Floor Plans/Specs");
			//propertyDocumentsArrayList.add("Floor Plans/Specs");

		/*	createPurchaseTasks(listOfDocsNeeded, opportunity);
			if (opportunity.getIsRentalProperty() != null
					&& opportunity.getIsRentalProperty() == true) {
				propertyDocuments.add("Schedule A");
				propertyDocumentsArrayList.add("Schedule A");
			}*/
		} else if (opportunity.getWhatIsYourLendingGoal() != null
				&& opportunity.getWhatIsYourLendingGoal().equalsIgnoreCase(
						"Refinance")) {
			HashMap map = getProperAddress(opportunity.getAddress());
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

		} else if (opportunity.getWhatIsYourLendingGoal() != null
				&& opportunity.getWhatIsYourLendingGoal().equalsIgnoreCase(
						"Pre-Approval")) {

		}

		for (Applicant applicant : opportunity.getApplicants()) {

			if (applicant.applicantName != null) {

				// if he is applicant then list the follwing doucments
				if (numberOfApplicants == 1) {
					applicantDocumentsList
							.setFirstName(applicant.applicantName);
					applicantDocumentsList
							.setLastName(applicant.applicantLastName);
					applicantDocumentsList
							.setEmailAddress(applicant.emailPersonal);
					// createImmigrantTasks(applicant, applicantDocuments);
					// createNonResidentTasks(applicant, applicantDocuments);

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
					logger.info("Applicants documents-----------"+ applicantDocuments);

				} else {
					// if he if co_apllicant list the coApplicant details
					for (Income currentIncome : applicant.incomes) {
						createIncomeDocsList(applicant, currentIncome,
								co_ApplicantDocuments,
								co_ApplicantDocumentsArrayList);
					}
					logger.debug("co_ApplicantDocuments"
							+ co_ApplicantDocuments);
					applicantDocumentsList
							.setCo_firstName(applicant.applicantName);
					applicantDocumentsList
							.setCo_lastName(applicant.applicantLastName);
					applicantDocumentsList
							.setCo_emailAddress(applicant.emailPersonal);

					// createImmigrantTasks(applicant, co_ApplicantDocuments);
					// createNonResidentTasks(applicant, co_ApplicantDocuments);
					co_ApplicantDocuments
							.add("Copy of Photo ID (Driver's Licence or Passport)");
					co_ApplicantDocumentsArrayList
							.add("Copy of Photo ID (Driver's Licence or Passport)");
					createPropertyTasks(listOfDocsNeeded,
							applicant.getProperties(), co_ApplicantDocuments,
							co_ApplicantDocumentsArrayList, opportunity);
					logger.info("Co_Applicants documents-----------"+co_ApplicantDocuments);
				}
			}
			numberOfApplicants = numberOfApplicants + 1;
		}

		// listing downpayment documents
		createDownPaymentTasks(listOfDocsNeeded, opportunity,
				downpaymentDocuments, downpaymentDocumentsArrayList);

		if (opportunity.getPartnerId() != null) {
			applicantDocumentsList.setOpprtunityId(opportunity.getPartnerId()
					.toString());
		}
		applicantDocumentsList.setLendingGoal(opportunity
				.getWhatIsYourLendingGoal());
		applicantDocumentsList.setDocuments(applicantDocuments);
		applicantDocumentsList.setCo_documents(co_ApplicantDocuments);
		applicantDocumentsList.setPropertyDocments(propertyDocuments);
		applicantDocumentsList.setDownPayments(downpaymentDocuments);

	logger.debug("all documentList-----------"+applicantDocumentsArrayList);
 duplicateRemovedApplicantDocument = new LinkedHashSet<String>(applicantDocumentsArrayList);
 duplicateRemovedCoApplicantDocument=new LinkedHashSet<String>(co_ApplicantDocumentsArrayList);
	 duplicateRemovedpropertyDocumentsArrayList=new LinkedHashSet<String>(propertyDocumentsArrayList);
 duplicateRemoveddownpaymentDocumentsArrayList=new LinkedHashSet<String>(downpaymentDocumentsArrayList);
	logger.debug("duplicate removed "+duplicateRemovedApplicantDocument);
		sendingMail(applicantDocumentsList, duplicateRemovedApplicantDocument,
				duplicateRemovedCoApplicantDocument, duplicateRemovedpropertyDocumentsArrayList,
				duplicateRemoveddownpaymentDocumentsArrayList);

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

		if (currentIncome.typeOfIncome.equalsIgnoreCase("Employeed")
				|| currentIncome.typeOfIncome
						.equalsIgnoreCase("Self-Employeed")) {

			Calendar year1 = Calendar.getInstance();
			int currentMonth = year1.get(Calendar.MONTH) + 1;

			if (currentIncome.typeOfIncome.equalsIgnoreCase("Employeed")) {

				incomesList.add("Letter of Employment");
				incomesList.add("Current Paystub");
				arralist.add("Letter of Employment");
				arralist.add("Current Paystub");
				if (currentMonth < 3) {
					incomesList
							.add("December 31st Pay Stub showing Year to Date Income");
					arralist.add("December 31st Pay Stub showing Year to Date Income");
				}
			}

			if (currentMonth >= 3 && currentMonth < 5) {

				int startYear = year1.get(Calendar.YEAR) - 1;

				int priorYear = startYear - 1;
				incomesList.add(priorYear + " Notice of Assessment");
				arralist.add(priorYear + " Notice of Assessment");

				if (currentIncome.typeOfIncome
						.equalsIgnoreCase("Self-Employeed")) {
					int priorYear2 = startYear - 2;
					incomesList.add(priorYear2 + " Notice of Assessment");
					arralist.add(priorYear2 + " Notice of Assessment");

				}
				if (currentIncome.typeOfIncome.equalsIgnoreCase("Employeed")) {
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

		} /*
		 * else if (currentIncome.typeOfIncome
		 * .equalsIgnoreCase("Self-Employeed")) {
		 * 
		 * 
		 * Calendar year1 = Calendar.getInstance();
		 * 
		 * int currentMonth = year1.get(Calendar.MONTH);
		 * 
		 * if (currentMonth < 3) {
		 * incomesList.add("December 31st Pay stub showing Year to date income"
		 * );
		 * 
		 * }
		 * 
		 * if (currentMonth >= 3 && currentMonth < 5) { int startYear =
		 * year1.get(Calendar.YEAR) - 1; incomesList.add(startYear + " T4"); int
		 * priorYear = startYear - 1; incomesList.add(priorYear +
		 * " Notice of Assessment");
		 * 
		 * } else if (currentMonth >= 5) { int startYear =
		 * year1.get(Calendar.YEAR) - 1;
		 * 
		 * incomesList.add(startYear + " Notice of Assessment"); int priorYear =
		 * startYear - 1; incomesList.add(priorYear + " Notice of Assessment");
		 * 
		 * }
		 * 
		 * 
		 * }
		 */else if (currentIncome.typeOfIncome.equalsIgnoreCase("Rental")) {

			// incomesList.add("Lease agreement or schedule A for the property ");

		}

		else if (currentIncome.typeOfIncome.equalsIgnoreCase("Spouse Support")
				|| currentIncome.typeOfIncome.equalsIgnoreCase("Child Support")) {

			incomesList.add("Divorce/Separation Agreement");

		} else if (currentIncome.typeOfIncome
				.equalsIgnoreCase("Child tax credit")) {

			incomesList.add("Child Tax Credit");

		} else if (currentIncome.typeOfIncome
				.equalsIgnoreCase("Living Allowance")) {

			incomesList.add("Living Allowance");
		} else if (currentIncome.typeOfIncome.equalsIgnoreCase("Vehicle")) {

			incomesList.add("Vehicle Allownce Statement");

		} else if (currentIncome.typeOfIncome.equalsIgnoreCase("Bonus")) {

			incomesList.add("Bonus Statement");

		} else if (currentIncome.typeOfIncome.equalsIgnoreCase("Commission")) {

			incomesList.add("Commission Statement");

		} else if (currentIncome.typeOfIncome.equalsIgnoreCase("Interest")) {

			incomesList.add("Interest Statements");

		} else if (currentIncome.typeOfIncome.equalsIgnoreCase("Overtime")) {

			incomesList.add("Overtime Statements");

		} else if (currentIncome.typeOfIncome.equalsIgnoreCase("Pension")) {

			incomesList.add("3 Months Bank Statements");

		} else if (currentIncome.typeOfIncome.equalsIgnoreCase("Retired")) {

			incomesList.add("3 Months Bank Statements");

		}

	}

	private void createPropertyTasks(StringBuilder listOfDocsNeeded,
			List<Property> properties, Set<String> propertys,
			ArrayList<String> arrayList, Opportunity opportunity) {
		HashMap mapData = getProperAddress(opportunity.getAddress());

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

			/*	if (currentProperty.getMoCondoFees() != null) {
					if (currentProperty.getMoCondoFees() > 0) {

						propertys.add("Condo Documents for ("
								+ map.get("address1") + "," + map.get("city")
								+ ")");
						arrayList.add("Condo Documents for ("
								+ map.get("address1") + "," + map.get("city")
								+ ")");

					}
				}*/
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

		if (opportunity.getDownpaymentAmount() != null) {

			if (opportunity.getDownpaymentAmount() > 0) {

			}

			if (opportunity.getBankAccount() != null
					&& opportunity.getBankAccount() > 0) {

				downPayementSoursce.add("90 Days History Bank Statements");
				arrayList.add("90 Days History Bank Statements");
			}
			if (opportunity.getPersonalCashAmount() != null
					&& opportunity.getPersonalCashAmount() > 0) {

				downPayementSoursce.add("90 Days History Investment");
				arrayList.add("90 Days History Investment");
			}
			if (opportunity.getRrspAmount() != null
					&& opportunity.getRrspAmount() > 0) {

				downPayementSoursce.add("90 Days History RRSP");
				arrayList.add("90 Days History RRSP");
			}
			if (opportunity.getGiftedAmount() != null
					&& opportunity.getGiftedAmount() > 0) {

				downPayementSoursce.add("Gift Letter");
				arrayList.add("Gift Letter");
			}
			if (opportunity.getBorrowedAmount() != null
					&& opportunity.getBorrowedAmount() > 0) {

				downPayementSoursce
						.add("Verification of Loan, Line of Credit or Credit Card Statement");
				arrayList
						.add("Verification of Loan, Line of Credit or Credit Card Statement");
			}
			/*if (opportunity.getSaleOfExistingAmount() != null
					&& opportunity.getSaleOfExistingAmount() > 0) {

				downPayementSoursce.add("Sale MLS Listing");
				arrayList.add("Sale MLS Listing");
			}*/
			if (opportunity.getExistingEquityAmount() != null
					&& opportunity.getExistingEquityAmount() > 0) {

				downPayementSoursce.add("Offer to Sale of Existing");
				arrayList.add("Offer to Sale of Existing");
			}
			if (opportunity.getSweatEquityAmount() != null
					&& opportunity.getSweatEquityAmount() > 0) {

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
			if (opportunity.getOtherAmount() != null
					&& opportunity.getOtherAmount() > 0) {

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

	public void sendingMail(ApplicantDocument applicantDocumentList,
			Set<String> applicantDocumentsArrayList,
			Set<String> co_ApplicantDocumentsArrayList,
			Set<String> propertyDocumentsArrayList,
			Set<String> downpaymentDocumentsArrayList) {
		co_ApplicantDocumentsArrayList=new HashSet<String>();
		try {
			SendWithUsMail sendWithUsMail = new SendWithUsMail();
			if (applicantDocumentList.getLendingGoal().equalsIgnoreCase(
					"Purchase")) {
				SendWithUsMail.mailTemplateOfPurchaseDocuments(
						applicantDocumentList, applicantDocumentsArrayList,
						co_ApplicantDocumentsArrayList,
						propertyDocumentsArrayList,
						downpaymentDocumentsArrayList);
				ArrayList list =  Odoo
						.getReferralname(applicantDocumentList
								.getOpprtunityId());
				try {
					if (list.get(2).toString().equalsIgnoreCase("Realtor")) {
						sendWithUsMail.mailTemplateOfRealtor(
								applicantDocumentList, list.get(0).toString(),
								list.get(1).toString());
					}
				} catch (Exception e) {
					logger.error("error occures while processing sendingMail" +e.getMessage());
				}
			} else if (applicantDocumentList.getLendingGoal().equalsIgnoreCase(
					"Refinance")) {
				SendWithUsMail.mailTemplateOfRefinanceDocuments(
						applicantDocumentList, applicantDocumentsArrayList,
						co_ApplicantDocumentsArrayList,
						propertyDocumentsArrayList,
						downpaymentDocumentsArrayList);

			} else if (applicantDocumentList.getLendingGoal().equalsIgnoreCase(
					"Pre-Approval")) {

				sendWithUsMail.mailTemplateOfPreeApprovalDocuments(
						applicantDocumentList, applicantDocumentsArrayList,
						co_ApplicantDocumentsArrayList,
						propertyDocumentsArrayList,
						downpaymentDocumentsArrayList);
				ArrayList list =  Odoo
						.getReferralname(applicantDocumentList
								.getOpprtunityId());
				try {
					if (list.get(2).toString().equalsIgnoreCase("Realtor")) {
						sendWithUsMail.mailTemplateOfRealtor(
								applicantDocumentList, list.get(0).toString(),
								list.get(1).toString());
					}
				} catch (Exception e) {
					logger.error("error occures while processing sendingMail" +e.getMessage());
				}
			}
		} catch (Exception e) {
			
			logger.error("error occures while processing sendingMail" +e.getMessage());
		}
	}
}
