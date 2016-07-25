package com.syml.test.junit;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


import org.codehaus.jettison.json.JSONException;

public class List_Of_Document_NeedD {

	StringBuilder listOfDocsNeeded = null;
	ApplicantDocument ap = new ApplicantDocument();
	Set<String> applicantDocuments = new HashSet<String>();
	Set<String> co_ApplicantDocuments = new HashSet<String>();
	Set<String> propertyDocuments = new HashSet<String>();
	Set<String> downpaymentDocuments = new HashSet<String>();

	public ApplicantDocument list_Of_Document_Needmethod(Opportunity opportunity)
			throws JSONException {
		listOfDocsNeeded = new StringBuilder();

		boolean webDocAdded = false;
		boolean noaDocAdded = false;

		int i = 1;

		for (Applicant applicant : opportunity.getApplicants()) {

			listOfDocsNeeded = new StringBuilder();

			if (applicant.applicantName != null) {
				// System.out.println("applicant.applicantName  "+i+""+applicant.applicantName);

				if (applicant.incomes != null && applicant.incomes.size() > 0) {

					listOfDocsNeeded.append(applicant.applicantName + "\n");
					// ap.setFirstName()

					boolean addedSelfEmployed = false;
					boolean addedEmployed = false;

					if (i == 1) {
						ap.setFirstName(applicant.applicantName);
						ap.setLastName(applicant.applicantLastName);
						ap.setEmailAddress(applicant.emailPersonal);
						createImmigrantTasks(applicant, applicantDocuments);
						createNonResidentTasks(applicant, applicantDocuments);
						for (Income currentIncome : applicant.incomes) {
							noaDocAdded = createIncomeDocsList(
									listOfDocsNeeded, webDocAdded, noaDocAdded,
									applicant, addedSelfEmployed,
									addedEmployed, currentIncome,
									applicantDocuments);
						}
						// System.out.println("---------------------->"+i+""+listOfDocsNeeded);
						createPropertyTasks(listOfDocsNeeded,
								applicant.getProperties(), applicantDocuments);

					} else {
						for (Income currentIncome : applicant.incomes) {
							noaDocAdded = createIncomeDocsList(
									listOfDocsNeeded, webDocAdded, noaDocAdded,
									applicant, addedSelfEmployed,
									addedEmployed, currentIncome,
									co_ApplicantDocuments);

						}
						ap.setCo_firstName(applicant.applicantName);
						ap.setCo_lastName(applicant.applicantLastName);
						ap.setCo_emailAddress(applicant.emailPersonal);
						createImmigrantTasks(applicant, co_ApplicantDocuments);
						createNonResidentTasks(applicant, co_ApplicantDocuments);

						createPropertyTasks(listOfDocsNeeded,
								applicant.getProperties(),
								co_ApplicantDocuments);

					}

				}
			} /*
			 * if (applicant.monthlychildsupport > 0 ){ // Assistant Tasks
			 * createChildSupportDocs(listOfDocsNeeded, webDocAdded, applicant);
			 * }
			 */

			i = i + 1;
		}
		System.out.println("opportunity.getWhatIsYourLendingGoal()"+opportunity.getWhatIsYourLendingGoal());
		listOfDocsNeeded = new StringBuilder();
		if (opportunity.getWhatIsYourLendingGoal() != null
				&& opportunity.getWhatIsYourLendingGoal().equalsIgnoreCase(
						"Purchase")) {
			createPurchaseTasks(listOfDocsNeeded, opportunity);
			if (opportunity.getIsRentalProperty() != null
					&& opportunity.getIsRentalProperty() == true) {
				propertyDocuments.add("Schedule A");
			}
			
		}
		if (opportunity.getWhatIsYourLendingGoal() != null
				&& opportunity.getWhatIsYourLendingGoal().equalsIgnoreCase(
						"Refinance")) {
			listOfDocsNeeded.append("Property Tax Statement for "
					+ opportunity.getAddress() + "\n");
			// jsonOpprunity.put(key, value)
			propertyDocuments.add("Property Tax Statement for "
					+ opportunity.getAddress());
			propertyDocuments.add("Mortgage  Statement for "
					+ opportunity.getAddress());
			
			if (opportunity.getIsRentalProperty() != null
					&& opportunity.getIsRentalProperty() == true) {
				propertyDocuments.add(" Lease Agreement");
			}
			propertyDocuments.add("Fire Insurance.");
			
		}
			if (opportunity.getWhatIsYourLendingGoal() != null
					&& opportunity.getWhatIsYourLendingGoal().equalsIgnoreCase(
							"Pre-Approval")) {
				
				listOfDocsNeeded.append("Property Tax Statement for "
						+ opportunity.getAddress() + "\n");
				// jsonOpprunity.put(key, value)
				propertyDocuments.add("Property Tax Statement for "
						+ opportunity.getAddress());
				propertyDocuments.add("Mortgage  Statement for "
						+ opportunity.getAddress());
			}
			createDownPaymentTasks(listOfDocsNeeded, opportunity,
					downpaymentDocuments);

			System.out.println(listOfDocsNeeded);
			//ap.setOpprtunityId(opportunity.getPartnerId().toString());
			ap.setOpprtunityUrl("Opportunity URL");
			ap.setDoclisturl("DocListURL");
			//jsonOpprunity.put("Opportunity ID", opportunity.getPartnerId());
			//jsonOpprunity.put("Opportunity URL", "");

			//jsonOpprunity.put("DocListURL ", "");

			ap.setDocuments(applicantDocuments);
			ap.setCo_documents(co_ApplicantDocuments);
			ap.setPropertyDocments(propertyDocuments);
			ap.setDownPayments(downpaymentDocuments);

		
		return ap;
	}

	private void createPurchaseTasks(StringBuilder listOfDocsNeeded,
			Opportunity opportunity) throws JSONException {
		listOfDocsNeeded.append("Property:");

		listOfDocsNeeded.append("- Realtor name and contact information");
		listOfDocsNeeded
				.append("- Copy of MLS Listing for "
						+ opportunity.getAddress()
						+ " (Or let us know we should get this from your realtor and we can contact them.) "
						+ "\n");
		listOfDocsNeeded
				.append("\t- Copy of Offer to Purchase for "
						+ opportunity.getAddress()
						+ " including waivers and addendums (Or let us know we should get this from your realtor and we can contact them.) "
						+ "\n");
		propertyDocuments
				.add("Offer to Purchase ,MLS Listing ,Copy of MLS Listing and Copy of Offer to Purchase for "
						+ opportunity.getAddress());
		propertyDocuments.add("Copy of MLS Listing for "+ opportunity.getAddress()+
						 " Copy of Offer to Purchase  ");
		if (opportunity.getMonthlyRentalIncome() != null
				&& opportunity.getMonthlyRentalIncome() > 0) {
			String doc = " Lease agreement or Schedule A (Rental Fair Market Value Assessment) to verify the likely rental income from the property being financed ";
			listOfDocsNeeded.append("\t - " + doc + "\n");
			propertyDocuments.add(doc);

		}

	}

	private void createImmigrantTasks(Applicant applicant, Set<String> set)
			throws JSONException {
		if (applicant.immigrationDate != null) {
			Calendar dateNow = Calendar.getInstance();
			dateNow.add(Calendar.YEAR, -4);
			if (applicant.immigrationDate.after(dateNow.getTime())) {

				String doc = "Immigration permit / work visa for "
						+ applicant.applicantName;
				set.add(doc);
				listOfDocsNeeded.append("\t - " + doc + "\n");

				doc = "International credit bureau " + applicant.applicantName;
				listOfDocsNeeded.append("\t - " + doc + "\n");

			}
		}

	}

	private void createNonResidentTasks(Applicant applicant, Set<String> set)
			throws JSONException {
		if (applicant.nonResident != null) {
			if (applicant.nonResident == true) {
				String doc = "Immigration permit / work visa for "
						+ applicant.applicantName;
				listOfDocsNeeded.append("\t - " + doc + "\n");
				set.add(doc);
				doc = "International credit bureau " + applicant.applicantName;
				listOfDocsNeeded.append("\t - " + doc + "\n");
			}
		}

	}

	private void createChildSupportDocs(StringBuilder listOfDocsNeeded,
			boolean webDocAdded, Applicant applicant) {

		String doc = "Divorce agreement or Separation agreement  - "
				+ applicant.applicantName;
		listOfDocsNeeded.append("\t - " + doc + "\n");
		String doc1 = "Bank statements (6 months) which verify support payments  - "
				+ applicant.applicantName;
		listOfDocsNeeded.append("\t - " + doc1 + "\n");
	}

	private boolean createIncomeDocsList(StringBuilder listOfDocsNeeded,
			boolean webDocAdded, boolean noaDocAdded, Applicant applicant,
			boolean addedSelfEmployed, boolean addedEmployed,
			Income currentIncome, Set<String> set) throws JSONException {
		Calendar currentDate1 = new GregorianCalendar();
		Calendar deadline1 = new GregorianCalendar();

		deadline1.add(Calendar.HOUR, 1);
		if (currentIncome.typeOfIncome.equalsIgnoreCase("Employeed")) {

			String letter = "\t" + "Letter of Employment for your job with "
					+ currentIncome.business + "\n";
			String paystubs = "\t" + "Recent paystubs for your job with "
					+ currentIncome.business + "\n";
			if (addedEmployed == false) {
				String loeWeb = "Letter of Employment " + "\n";
				String payStubWeb = "PayStubs " + "\n";
				if (webDocAdded == false && noaDocAdded == false) {
					listOfDocsNeeded.append(loeWeb);
					listOfDocsNeeded.append(payStubWeb);
					set.add("Letter of Employment");
					set.add("PayStubs");
				}

				addedEmployed = true;
			}
		} else if (currentIncome.typeOfIncome
				.equalsIgnoreCase("Self-Employeed")) {

			if (addedSelfEmployed == false) {
				Calendar year1 = Calendar.getInstance();

				int currentMonth = year1.get(Calendar.MONTH);
				if (currentMonth <= 5) {
					int startYear = year1.get(Calendar.YEAR) - 2;
					// year1.add(Calendar.YEAR, -2);
					String noa1Year = "\t - " + startYear
							+ " Notice of Assessment from Revenue Canada"
							+ "\n";
					String noa1YearWeb = startYear + " Notice of Assessment"
							+ "\n";
					if (webDocAdded == false && noaDocAdded == false) {
						listOfDocsNeeded.append(noa1YearWeb);
						set.add(startYear + " Notice of Assessment");

					}

					int priorYear = startYear - 1;
					String noa2Year = "\t - " + priorYear
							+ " Notice of Assessment from Revenue Canada"
							+ "\n";
					String noa2YearWeb = priorYear + " Notice of Assessment"
							+ "\n";
					if (webDocAdded == false && noaDocAdded == false) {
						listOfDocsNeeded.append(noa2YearWeb);
						set.add(priorYear + " Notice of Assessment");

					}
					noaDocAdded = true;
				} else {
					int startYear = year1.get(Calendar.YEAR) - 1;
					String noa1Year = "\t - " + startYear
							+ " Notice of Assessment from Revenue Canada"
							+ "\n";
					String noa1YearWeb = startYear + " Notice of Assessment"
							+ "\n";
					if (webDocAdded == false && noaDocAdded == false) {
						listOfDocsNeeded.append(noa1YearWeb);
						set.add(startYear + " Notice of Assessment");

					}

					int priorYear = startYear - 1;
					String noa2Year = "\t - " + priorYear
							+ " Notice of Assessment from Revenue Canada"
							+ "\n";
					String noa2YearWeb = priorYear + " Notice of Assessment"
							+ "\n";
					if (webDocAdded == false && noaDocAdded == false) {
						listOfDocsNeeded.append(noa2YearWeb);
						set.add(priorYear + " Notice of Assessment");

					}
					noaDocAdded = true;
				}

				addedSelfEmployed = true;
			}
		} else if (currentIncome.typeOfIncome.equalsIgnoreCase("Rental")) {

			String doc = "Lease agreement or schedule A for the property at "
					+ currentIncome.name;
			String income4Web = doc + "\n";
			if (webDocAdded == false) {
				listOfDocsNeeded.append(income4Web);
				set.add(income4Web);

			}
		}

		else if (currentIncome.typeOfIncome.equalsIgnoreCase("Spouse Support")
				|| currentIncome.typeOfIncome.equalsIgnoreCase("Child Support")) {

			String doc = "Divorce / Separation Agreeement";
			String income4Web = doc + "\n";
			if (webDocAdded == false) {
				listOfDocsNeeded.append(income4Web);
				set.add(income4Web);

			}
		} else if (currentIncome.typeOfIncome
				.equalsIgnoreCase("Child tax credit")) {

			String doc = "Child tax credit form";
			String income4Web = doc + "\n";
			if (webDocAdded == false) {
				listOfDocsNeeded.append(income4Web);
				set.add(income4Web);

			}
		} else if (currentIncome.typeOfIncome
				.equalsIgnoreCase("LivingAllowance")) {

			set.add("LivingAllowance");
		} else if (currentIncome.typeOfIncome.equalsIgnoreCase("Vehicle")) {
			String doc = "Vehicle Allownce Statement";
			String income4Web = doc + "\n";
			if (webDocAdded == false) {
				listOfDocsNeeded.append(income4Web);
				set.add(income4Web);
			}
		} else if (currentIncome.typeOfIncome.equalsIgnoreCase("Bonus")) {

			String doc = "Bonus Statement";
			String income4Web = doc + "\n";
			if (webDocAdded == false) {
				listOfDocsNeeded.append(income4Web);
				set.add(income4Web);
			}
		} else if (currentIncome.typeOfIncome.equalsIgnoreCase("Commission")) {

			String doc = "Commission Statement";
			String income4Web = doc + "\n";
			if (webDocAdded == false) {
				listOfDocsNeeded.append(income4Web);
				set.add(income4Web);
			}
		} else if (currentIncome.typeOfIncome.equalsIgnoreCase("Interest")) {
			String doc = "Interest Statements";
			String income4Web = doc + "\n";
			if (webDocAdded == false) {
				listOfDocsNeeded.append(income4Web);
				set.add(income4Web);
			}
		} else if (currentIncome.typeOfIncome.equalsIgnoreCase("Overtime")) {
			String doc = "Overtime Statements";
			String income4Web = doc + "\n";
			if (webDocAdded == false) {
				listOfDocsNeeded.append(income4Web);
				set.add(income4Web);
			}
		} else if (currentIncome.typeOfIncome.equalsIgnoreCase("Pension")) {
			String doc = "Pension Statements";
			String income4Web = doc + "\n";
			if (webDocAdded == false) {
				listOfDocsNeeded.append(income4Web);
				set.add(income4Web);
			}
		} else if (currentIncome.typeOfIncome.equalsIgnoreCase("Retired")) {
			String doc = "Investment Income Statements";
			String income4Web = doc + "\n";
			if (webDocAdded == false) {
				listOfDocsNeeded.append(income4Web);
				set.add(income4Web);
			}
		} else {
			String doc = "Other Income Statements ";
			// documentsListApplicant1.add(doc);
			String income4Web = doc + "\n\n";
			if (webDocAdded == false) {
				listOfDocsNeeded.append(income4Web);
				set.add(income4Web);
			}
		}
		return noaDocAdded;
	}

	private void createPropertyTasks(StringBuilder listOfDocsNeeded,
			List<Property> properties, Set<String> set) throws JSONException {
		System.out.println("----------------------> hello");
		for (Property currentProperty : properties) {
			if (currentProperty.getName() != null) {
				set.add("Mortgage Statement for " + currentProperty.getName());
				set.add("Property Tax Assessment" + currentProperty.getName());

				listOfDocsNeeded.append("Property Tax Statement for "
						+ currentProperty.getName() + "\n");
				if (currentProperty.getMoCondoFees() != null) {
					if (currentProperty.getMoCondoFees() > 0) {
						listOfDocsNeeded.append("\t - "
								+ currentProperty.getName()
								+ " - Condo Documents" + "\n");
						set.add("Condo Documents for"
								+ currentProperty.getName());
					}
				}
			}
		}

	}

	private void createDownPaymentTasks(StringBuilder listOfDocsNeeded,
			Opportunity opportunity, Set<String> set) throws JSONException {

		System.out.println(opportunity.getDownpaymentAmount());

		if (opportunity.getDownpaymentAmount() != null) {

			if (opportunity.getDownpaymentAmount() > 0) {

				listOfDocsNeeded.append("\n");
				listOfDocsNeeded.append("Down Payment " + "\n");
				set.add("Down Payment");
			}
			ArrayList<String> downpaymentSources = new ArrayList<String>();

			if (opportunity.getBankAccount() != null
					&& opportunity.getBankAccount() > 0) {
				// documentsListApplicant1.add("Downpayment Verification - Bank Account.");
				downpaymentSources
						.add("Downpayment Verification - Bank Account.");
				listOfDocsNeeded.append("\t - "
						+ "Verification of Bank Statement" + "\n");
				set.add("Downpayment Verification - Bank Account.");
				// listOfDownpayDocsWeb.append("Bank Account Statement" + "\n");
			}
			if (opportunity.getPersonalCashAmount() != null
					&& opportunity.getPersonalCashAmount() > 0) {
				// documentsListApplicant1.add("Downpayment Verification - Bank Account.");
				downpaymentSources
						.add("Downpayment Verification - Bank Account.");
				listOfDocsNeeded.append("\t - " + "Verification Bank Statement"
						+ "\n");
				set.add("Verification of Bank Statement");

				// listOfDownpayDocsWeb.append("Bank Account Statement" + "\n");
			}
			if (opportunity.getRrspAmount() != null
					&& opportunity.getRrspAmount() > 0) {
				// documentsListApplicant1.add("Downpayment Verification - RRSP.");
				downpaymentSources.add("Downpayment Verification - RRSP.");
				listOfDocsNeeded.append("\t - "
						+ "Verification of RRSP Statements" + "\n");
				set.add("Downpayment Verification - RRSP");

				// listOfDownpayDocsWeb.append("RRSP Statements" + "\n");
			}
			if (opportunity.getGiftedAmount() != null
					&& opportunity.getGiftedAmount() > 0) {
				// Broker Tasks
				// documentsListApplicant1.add("Downpayment Verification - Gift Letter.");
				downpaymentSources
						.add("Downpayment Verification - Gift Letter.");
				listOfDocsNeeded.append("\t - " + "Verification of Gift Letter"
						+ "\n");
				set.add("Verification of Gift Letter");

				// listOfDownpayDocsWeb.append("Gift Letter" + "\n");

			}
			if (opportunity.getBorrowedAmount() != null
					&& opportunity.getBorrowedAmount() > 0) {
				// documentsListApplicant1.add("Downpayment Verification - Borrowed Amount.");
				downpaymentSources
						.add("Downpayment Verification - Borrowed Amount.");
				listOfDocsNeeded
						.append("\t - "
								+ "Verification of Loan, Line of Credit or Credit Card Statement"
								+ "\n");
				set.add("Verification of Loan, Line of Credit or Credit Card Statement");

				// listOfDownpayDocsWeb.append("Borrowed Downpayment Statement (LOC)"
				// + "\n");

			}
			if (opportunity.getSaleOfExistingAmount() != null
					&& opportunity.getSaleOfExistingAmount() > 0) {
				downpaymentSources
						.add("Downpayment Verification - Sale of Existing Property, Listing Sheet");
				listOfDocsNeeded.append("\t - "
						+ "Verification of Listing Sheet or Offer to Purchase"
						+ "\n");
				set.add("Verification of Listing Sheet or Offer to Purchase");

				// listOfDownpayDocsWeb.append("Listing Sheet or Offer to Purchase"
				// + "\n");
			}
			if (opportunity.getExistingEquityAmount() != null
					&& opportunity.getExistingEquityAmount() > 0) {
				downpaymentSources
						.add("Downpayment Verification - Existing Equity");
				listOfDocsNeeded
						.append("\t - "
								+ "Verification of Property address with existing equity"
								+ "\n");
				set.add("Verification of Property address with existing equity");

			}
			if (opportunity.getSweatEquityAmount() != null
					&& opportunity.getSweatEquityAmount() > 0) {
				http: // localhost:8080/UnderwrittingApp/services/desiredProduct/3433
				// documentsListApplicant1.add("Downpayment Verification - Sweat Equity");
				downpaymentSources
						.add("Downpayment Verification - Sweat Equity");
				listOfDocsNeeded.append("\t - "
						+ "Verification of Property address with sweat equity"
						+ "\n");
				set.add("Verification of Property address with sweat equity");

			}
			if (opportunity.getSecondaryFinancingAmount() != null
					&& opportunity.getSecondaryFinancingAmount() > 0) {
				// documentsListApplicant1.add("Downpayment Verification - Secondary Financing");
				downpaymentSources
						.add("Downpayment Verification - Secondary Financing");
				listOfDocsNeeded.append("\t - " + "Verification of $"
						+ opportunity.getSecondaryFinancingAmount()
						+ " from Secondary Financing (Loan Agreement)" + "\n");
				set.add("Verification of $"
						+ opportunity.getSecondaryFinancingAmount()
						+ " from Secondary Financing (Loan Agreement)" + "\n");

			}
			if (opportunity.getOtherAmount() != null
					&& opportunity.getOtherAmount() > 0) {
				listOfDocsNeeded
						.append("\t - "
								+ "Verification of Explanation in writing of other source of downpayment"
								+ "\n");
				set.add("Verification of Explanation in writing of other source of downpayment");

			}
		}

	}

}
