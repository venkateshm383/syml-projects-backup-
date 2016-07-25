package com.syml.morweb;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.UUID;

import com.mscanada.Clipboard;
import com.syml.bean.algo.AlgoIncome;
import com.syml.bean.algo.AlgoOpportunity;
import com.syml.bean.algo.AlgoProduct;
import com.syml.bean.algo.UnderwritePostSel;
import com.syml.constant.Constant;
import com.syml.domain.Applicant;
import com.syml.domain.Asset;
import com.syml.domain.Income;
import com.syml.domain.Mortgage;
import com.syml.domain.Property;
import com.syml.service.MorwebService;
import com.syml.util.SelectionHelper;

import javax.xml.bind.JAXBElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * This class is now deprecated, and only use as a business logic references. 
 * {@link MorwebService} is replacement for this class.
 * @see MorwebService
 */
@Deprecated
public class MorwebCall {


	/**
	 * Indicated that, Morweb's field set by this value need more
	 * alignment/discussion in business process point of view.
	 */
	private static final String FIXME = "-FIXME-";

	private Clipboard clipboard;
	private ObjectFactory morwebObjectFactory;
	private com.mscanada.ObjectFactory msCanadaObjectFactory;
	private BDIRequest bdiRequest;

	private SimpleDateFormat morwebDateTimeTFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss"); // Usually used by morweb system.
	private SimpleDateFormat morwebDateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Usually used as business logic constraint.
	private DatatypeFactory xmlDataTypeFactory;

	public MorwebCall(UnderwritePostSel underwrite) {
		super();
		try {
			this.xmlDataTypeFactory = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException e) {
			e.printStackTrace();
		}

		this.morwebObjectFactory = new ObjectFactory();
		this.msCanadaObjectFactory = new com.mscanada.ObjectFactory();

		bdiRequest = this.morwebObjectFactory.createBDIRequest();
		CustomerData customerData = this.morwebObjectFactory.createCustomerData();
		LiabilityList liabilityList = this.morwebObjectFactory.createLiabilityList();
		AssetList assetList = this.morwebObjectFactory.createAssetList();
		int assetKey = 0;
		int applicantCounter = 0;

		for (Applicant applicant : underwrite.clientOpportunity.applicants) {
			TypeCustomer customer = this.morwebObjectFactory.createTypeCustomer();

			customer.customerTelephoneNumberList = new CustomerTelephoneNumberList();
			customer.employmentList = new EmploymentList();
			customer.bankruptcyHistoryList = new BankruptcyHistoryList();
			customer.unearnedIncomeList = new UnearnedIncomeList();
			customer.customerNoteList = new CustomerNoteList();

			if (applicant.home != null) {
				CustomerTelephoneNumber homeNumber = new CustomerTelephoneNumber();
				TelephoneNumber homeNumber1 = new TelephoneNumber();
				homeNumber1.phoneNumber = applicant.home;
				homeNumber.telephoneNumber = homeNumber1;
				customer.customerTelephoneNumberList.getCustomerTelephoneNumber().add(0, homeNumber);
			}

			if (applicant.cell != null) {
				CustomerTelephoneNumber cellNumber = new CustomerTelephoneNumber();
				TelephoneNumber cellNumber1 = new TelephoneNumber();
				cellNumber1.phoneNumber = applicant.cell;
				cellNumber.telephoneNumber = cellNumber1;
				customer.customerTelephoneNumberList.getCustomerTelephoneNumber().add(1, cellNumber);
			}

			if (applicant.work != null) {
				CustomerTelephoneNumber workNumber = new CustomerTelephoneNumber();
				TelephoneNumber workNumber1 = new TelephoneNumber();
				workNumber1.phoneNumber = applicant.work;
				workNumber.telephoneNumber = workNumber1;
				customer.customerTelephoneNumberList.getCustomerTelephoneNumber().add(2, workNumber);
			}

			int unearnedIncomeCounter = 0;
			// FIXME: I think this is wrong. Should actual customer employment end-date, no?
			GregorianCalendar priorDate = new GregorianCalendar();

			for (Income income : applicant.incomes) {
				if (SelectionHelper.compareSelection(AlgoIncome.IncomeType.Employed, income.typeOfIncome)) {
					// Employment Type
					// 1 Permanent full time
					// 2 Permanent part time
					// 3 Temporary full time
					// 4 Temporary part time
					// 5 Permanent - Seasonal
					// 6 Temporary - Seasonal
					// 7 Not applicable

					// Status
					// 10 Employed
					// 20 Self-Employed
					// 30 Commission Sales
					// 40 Hourly Wages
					// 70 Retired
					// 80 Homemaker
					// 99 Other

					// An employment instance is created. Then an income, then
					// the income is set into the employment object.
					Employment employment = new Employment();
					employment.earnedIncomeList = new EarnedIncomeList();

					if (income.business != null)
						employment.companyName = income.business;

					if (income.business != null)
						employment.jobTitle = income.position;

					employment.employmentType = "1";
					employment.employmentStatus = "10";

					String priorDateAsString = this.morwebDateFormat.format(priorDate.getTime());
					XMLGregorianCalendar xmlEndDate = xmlDataTypeFactory.newXMLGregorianCalendar(priorDateAsString);
					employment.dateEnd = xmlEndDate;

					if (income.month != null)
						priorDate.add((GregorianCalendar.MONTH), (income.month * -1));

					priorDateAsString = this.morwebDateFormat.format(priorDate.getTime());
					XMLGregorianCalendar xmlStartDate = xmlDataTypeFactory.newXMLGregorianCalendar(priorDateAsString);
					employment.dateStart = xmlStartDate;

					if (income.historical == false) {
						EarnedIncome earnedIncome = new EarnedIncome();
						earnedIncome.earnedIncomeType = "1";
						earnedIncome.paymentFrequency = "1";
						double annualIncome = Double.parseDouble(income.annualIncome);
						earnedIncome.earnedIncomeAmount = BigDecimal.valueOf(annualIncome);
						employment.earnedIncomeList.getEarnedIncome().add(earnedIncome);
					}

					// Add to employment list
					customer.employmentList.getEmployment().add(employment);

				} else if (SelectionHelper.compareSelection(AlgoIncome.IncomeType.SelfEmployed, income.typeOfIncome)) {

					// An employment instance is created. Then an income, then
					// the income is set into the employment object.
					Employment employment = new Employment();
					employment.earnedIncomeList = new EarnedIncomeList();
					employment.companyName = income.business;
					employment.jobTitle = income.position;
					employment.employmentType = "1";
					employment.employmentStatus = "20";

					XMLGregorianCalendar xmlEndDate = xmlDataTypeFactory.newXMLGregorianCalendar(priorDate);
					employment.dateEnd = xmlEndDate;
					priorDate.add((GregorianCalendar.MONTH), (income.month * -1));

					XMLGregorianCalendar xmlStartDate = xmlDataTypeFactory.newXMLGregorianCalendar(priorDate);
					employment.dateStart = xmlStartDate;

					if (income.historical == false) {
						EarnedIncome earnedIncome = new EarnedIncome();
						earnedIncome.earnedIncomeType = "1";
						earnedIncome.paymentFrequency = "1";
						double annualIncome = Double.parseDouble(income.annualIncome);
						earnedIncome.earnedIncomeAmount = BigDecimal.valueOf(annualIncome);
						employment.earnedIncomeList.getEarnedIncome().add(earnedIncome);
					}

					// Add to employment list
					customer.employmentList.getEmployment().add(employment);
				} else if (SelectionHelper.compareSelection(AlgoIncome.IncomeType.Rental, income.typeOfIncome)) {
					// unearnedIncomeType Description
					// 1 Pension
					// 2 Investments
					// 3 Support/Alimony
					// 4 Car Allowance
					// 99 Other

					// Frequency
					// 1 Annually
					// 2 Semi-annually
					// 4 Quarterly
					// 12 Monthly
					// 24 Semi-monthly
					// 26 Bi-weekly
					// 52 Weekly
					if (income.historical == false) {
						UnearnedIncome unearnedIncome = new UnearnedIncome();
						unearnedIncome.unearnedIncomeType = "2";
						unearnedIncome.paymentFrequency = "1";
						double annualIncome = Double.parseDouble(income.annualIncome);
						unearnedIncome.unearnedIncomeAmount = BigDecimal.valueOf(annualIncome);
						customer.unearnedIncomeList.getUnearnedIncome().add(unearnedIncomeCounter, unearnedIncome);
						unearnedIncomeCounter++;
					}
				} else if (SelectionHelper.compareSelection(AlgoIncome.IncomeType.ChildTaxCredit, income.typeOfIncome)) {
					if (income.historical == false) {
						UnearnedIncome unearnedIncome = new UnearnedIncome();
						unearnedIncome.unearnedIncomeType = "3";
						unearnedIncome.paymentFrequency = "1";
						double annualIncome = Double.parseDouble(income.annualIncome);
						unearnedIncome.unearnedIncomeAmount = BigDecimal.valueOf(annualIncome);
						customer.unearnedIncomeList.getUnearnedIncome().add(unearnedIncomeCounter, unearnedIncome);
						unearnedIncomeCounter++;
					}
				} else if (SelectionHelper.compareSelection(AlgoIncome.IncomeType.LivingAllowance, income.typeOfIncome)) {
					if (income.historical == false) {
						UnearnedIncome unearnedIncome = new UnearnedIncome();
						unearnedIncome.unearnedIncomeType = "3";
						unearnedIncome.paymentFrequency = "1";
						double annualIncome = Double.parseDouble(income.annualIncome);
						unearnedIncome.unearnedIncomeAmount = BigDecimal.valueOf(annualIncome);
						customer.unearnedIncomeList.getUnearnedIncome().add(unearnedIncomeCounter, unearnedIncome);
						unearnedIncomeCounter++;
					}
				} else if (SelectionHelper.compareSelection(AlgoIncome.IncomeType.VehicleAllowance, income.typeOfIncome)) {
					if (income.historical == false) {
						UnearnedIncome unearnedIncome = new UnearnedIncome();
						unearnedIncome.unearnedIncomeType = "4";
						unearnedIncome.paymentFrequency = "1";
						double annualIncome = Double.parseDouble(income.annualIncome);
						unearnedIncome.unearnedIncomeAmount = BigDecimal.valueOf(annualIncome);
						customer.unearnedIncomeList.getUnearnedIncome().add(unearnedIncomeCounter, unearnedIncome);
						unearnedIncomeCounter++;
					}
				} else if (SelectionHelper.compareSelection(AlgoIncome.IncomeType.Bonus, income.typeOfIncome)) {
					// An employment instance is created. Then an income, then
					// the income is set into the employment object.
					Employment employment = new Employment();
					employment.earnedIncomeList = new EarnedIncomeList();
					employment.companyName = income.business;
					employment.jobTitle = income.position;
					employment.employmentType = "1";
					employment.employmentStatus = "10";

					XMLGregorianCalendar xmlEndDate = xmlDataTypeFactory.newXMLGregorianCalendar(priorDate);
					employment.dateEnd = xmlEndDate;

					priorDate.add((GregorianCalendar.MONTH), (income.month * -1));
					XMLGregorianCalendar xmlStartDate = xmlDataTypeFactory.newXMLGregorianCalendar(priorDate);
					employment.dateStart = xmlStartDate;

					if (income.historical == false) {
						EarnedIncome earnedIncome = new EarnedIncome();
						earnedIncome.earnedIncomeType = "3";
						earnedIncome.paymentFrequency = "1";
						double annualIncome = Double.parseDouble(income.annualIncome);
						earnedIncome.earnedIncomeAmount = BigDecimal.valueOf(annualIncome);
						employment.earnedIncomeList.getEarnedIncome().add(earnedIncome);
					}

					// Add to employment list
					customer.employmentList.getEmployment().add(employment);
				} else if (SelectionHelper.compareSelection(AlgoIncome.IncomeType.Commission, income.typeOfIncome)) {
					// An employment instance is created. Then an income, then
					// the income is set into the employment object.
					Employment employment = new Employment();
					employment.earnedIncomeList = new EarnedIncomeList();
					employment.companyName = income.business;
					employment.jobTitle = income.position;
					employment.employmentType = "1";
					employment.employmentStatus = "30";

					XMLGregorianCalendar xmlEndDate = xmlDataTypeFactory.newXMLGregorianCalendar(priorDate);
					employment.dateEnd = xmlEndDate;
					priorDate.add((GregorianCalendar.MONTH), (income.month * -1));

					XMLGregorianCalendar xmlStartDate = xmlDataTypeFactory.newXMLGregorianCalendar(priorDate);
					employment.dateStart = xmlStartDate;

					if (income.historical == false) {
						EarnedIncome earnedIncome = new EarnedIncome();
						earnedIncome.earnedIncomeType = "4";
						earnedIncome.paymentFrequency = "1";
						double annualIncome = Double.parseDouble(income.annualIncome);
						earnedIncome.earnedIncomeAmount = BigDecimal.valueOf(annualIncome);
						employment.earnedIncomeList.getEarnedIncome().add(earnedIncome);
					}
				} else if (SelectionHelper.compareSelection(AlgoIncome.IncomeType.Interest, income.typeOfIncome)) {
					if (income.historical == false) {
						EarnedIncome earnedIncome = new EarnedIncome();
						earnedIncome.earnedIncomeType = "5";
						earnedIncome.paymentFrequency = "1";
						double annualIncome = Double.parseDouble(income.annualIncome);
						earnedIncome.earnedIncomeAmount = BigDecimal.valueOf(annualIncome);
					}
				} else if (SelectionHelper.compareSelection(AlgoIncome.IncomeType.Overtime, income.typeOfIncome)) {
					// An employment instance is created. Then an income, then
					// the income is set into the employment object.
					Employment employment = new Employment();
					employment.earnedIncomeList = new EarnedIncomeList();
					employment.companyName = income.business;
					employment.jobTitle = income.position;
					employment.employmentType = "1";
					employment.employmentStatus = "10";

					XMLGregorianCalendar xmlEndDate = xmlDataTypeFactory.newXMLGregorianCalendar(priorDate);
					employment.dateEnd = xmlEndDate;
					priorDate.add((GregorianCalendar.MONTH), (income.month * -1));

					XMLGregorianCalendar xmlStartDate = xmlDataTypeFactory.newXMLGregorianCalendar(priorDate);
					employment.dateStart = xmlStartDate;

					if (income.historical == false) {
						EarnedIncome earnedIncome = new EarnedIncome();
						earnedIncome.earnedIncomeType = "1";
						earnedIncome.paymentFrequency = "1";
						double annualIncome = Double.parseDouble(income.annualIncome);
						earnedIncome.earnedIncomeAmount = BigDecimal.valueOf(annualIncome);
						employment.earnedIncomeList.getEarnedIncome().add(earnedIncome);
					}

					// Add to employment list
					customer.employmentList.getEmployment().add(employment);
				} else if (SelectionHelper.compareSelection(AlgoIncome.IncomeType.Pension, income.typeOfIncome)) {
					// An employment instance is created. Then an income, then
					// the income is set into the employment object.
					Employment employment = new Employment();
					employment.earnedIncomeList = new EarnedIncomeList();
					employment.companyName = income.business;
					employment.jobTitle = income.position;
					employment.employmentType = "1";
					employment.employmentStatus = "10";

					XMLGregorianCalendar xmlEndDate = xmlDataTypeFactory.newXMLGregorianCalendar(priorDate);
					employment.dateEnd = xmlEndDate;
					priorDate.add((GregorianCalendar.MONTH), (income.month * -1));

					XMLGregorianCalendar xmlStartDate = xmlDataTypeFactory.newXMLGregorianCalendar(priorDate);
					employment.dateStart = xmlStartDate;

					if (income.historical == false) {
						EarnedIncome earnedIncome = new EarnedIncome();
						earnedIncome.earnedIncomeType = "6";
						earnedIncome.paymentFrequency = "1";
						double annualIncome = Double.parseDouble(income.annualIncome);
						earnedIncome.earnedIncomeAmount = BigDecimal.valueOf(annualIncome);
						employment.earnedIncomeList.getEarnedIncome().add(earnedIncome);
					}

					// Add to employment list
					customer.employmentList.getEmployment().add(employment);
				} else if (SelectionHelper.compareSelection(AlgoIncome.IncomeType.Retired, income.typeOfIncome)) {
					if (income.historical == false) {
						UnearnedIncome unearnedIncome = new UnearnedIncome();
						unearnedIncome.unearnedIncomeType = "2";
						unearnedIncome.paymentFrequency = "1";
						double annualIncome = Double.parseDouble(income.annualIncome);
						unearnedIncome.unearnedIncomeAmount = BigDecimal.valueOf(annualIncome);
						customer.unearnedIncomeList.getUnearnedIncome().add(unearnedIncomeCounter, unearnedIncome);
						unearnedIncomeCounter++;
					}
				}
			} // Incomes List Complete.

			// Bankruptcy
			if (applicant.bankruptcy == true) {
				BankruptcyHistory bankruptcy = new BankruptcyHistory();
				@SuppressWarnings("deprecation")
				GregorianCalendar dischargeDate = new GregorianCalendar(applicant.bankruptcyDischargeDate.getYear(), applicant.bankruptcyDischargeDate.getMonth(), applicant.bankruptcyDischargeDate.getDay());
				bankruptcy.dateDischarged = xmlDataTypeFactory.newXMLGregorianCalendar(dischargeDate);
				customer.bankruptcyHistoryList.bankruptcyHistory.add(bankruptcy);
			}

			// Birthday
			final String birthAsString = this.morwebDateFormat.format(applicant.getDob());
			customer.dateBirth = xmlDataTypeFactory.newXMLGregorianCalendar(birthAsString);

			// FirstName
			if (applicant.applicantName != null)
				customer.firstName = applicant.applicantName;

			// LastName
			if (applicant.applicantLastName != null)
				customer.lastName = applicant.applicantLastName;
			// Middle Name
			// Email Address
			if (applicant.emailPersonal != null)
				customer.emailAddress1 = applicant.emailPersonal;

			if (applicant.emailWork != null)
				customer.emailAddress2 = applicant.emailWork;

			// SIN
			if (applicant.sin != null) {
				final String sin = applicant.sin.replaceAll("-", "");
				customer.sin = sin;
			}

			// Marital Status
			if (applicant.relationshipStatus != null)
				customer.maritalStatus = "99"; // FIXME: 99 mean other. applicant.relationshipStatus from UW is contains alphabet, which is forbidden.

			customer.setKey(applicant.applicantId == null ? UUID.randomUUID().toString() : applicant.applicantId.toString()); // FIXME: Is this correct value?

			// TypeCustomer customer =
			// this.morwebObjectFactory.createTypeCustomer(applicant); // This
			// line is the problem
			CustomerList customerList = this.morwebObjectFactory.createCustomerList();
			customerList.setCustomerBorrower(customer);
			customerData.setCustomerList(customerList); // customerList

			// ----- Temporary added. ----- //
			// This below added to handle validation error in
			// <CustomerAddressList />
			// <CustomerAssetList />
			// <CustomerLiabilityList />

			TypeCustomerAddressList customerAddressList = this.morwebObjectFactory.createTypeCustomerAddressList();
			TypeAddressOccupancyOwnerOccupied ownerOccupied = this.morwebObjectFactory.createTypeAddressOccupancyOwnerOccupied();
			ownerOccupied.setRefkeyAsset(underwrite.clientOpportunity.address);

			CustomerAddressPrimaryResidence primaryResidence = this.morwebObjectFactory.createCustomerAddressPrimaryResidence();
			final String residenceDateAsString = this.morwebDateFormat.format(underwrite.clientOpportunity.conditionOfFinancingDate); // FIXME: This is wrong!
			primaryResidence.setFromDate(this.xmlDataTypeFactory.newXMLGregorianCalendar(residenceDateAsString));
			primaryResidence.setAddressOccupancyOwnerOccupied(ownerOccupied);

			JAXBElement<CustomerAddressPrimaryResidence> jaxbTypeCustomerAddress = this.morwebObjectFactory.createCustomerAddressPrimaryResidence(primaryResidence);
			customerAddressList.getCustomerAddress().add(jaxbTypeCustomerAddress);
			customerData.setCustomerAddressList(customerAddressList); // customerAddressList

			// Is this logic true?
			CustomerAssetList customerAssetList = this.morwebObjectFactory.createCustomerAssetList();
			for (final Asset asset : applicant.getAssets()) {
				TypeCustomerReference typeCustomerReference = this.morwebObjectFactory.createTypeCustomerReference();
				// typeCustomerReference.setRefkeyCustomer(String.valueOf(applicant.getApplicantId()));
				// // THIS IS RETURN NULL!
				typeCustomerReference.setRefkeyCustomer(UUID.randomUUID().toString()); // FIXME: Use real value.
				CustomerAssetOther customerAssetOther = this.morwebObjectFactory.createCustomerAssetOther();
				customerAssetOther.setCustomerReference(typeCustomerReference);
				customerAssetOther.setRefkeyAsset(String.valueOf(asset.getId()));
				customerAssetList.getCustomerAssetOther().add(customerAssetOther);
			}
			customerData.setCustomerAssetList(customerAssetList); // customerAssetList

			CustomerLiabilityList customerLiabilityList = this.morwebObjectFactory.createCustomerLiabilityList();
			/*for (final Liability liability : applicant.getLiabilities()) {
				TypeCustomerReference typeCustomerReference = this.morwebObjectFactory.createTypeCustomerReference();
				// typeCustomerReference.setRefkeyCustomer(String.valueOf(applicant.getApplicantId()));
				// // THIS IS RETURN NULL!
				typeCustomerReference.setRefkeyCustomer(UUID.randomUUID().toString());
				CustomerLiabilityOther customerLiabilityOther = this.morwebObjectFactory.createCustomerLiabilityOther();
				customerLiabilityOther.setCustomerReference(typeCustomerReference);
				customerLiabilityOther.setRefkeyLiability(String.valueOf(liability.getId()));
				customerLiabilityList.getCustomerLiabilityOther().add(customerLiabilityOther);
			}*/

			CustomerCreditBureauList creditBureauList = this.morwebObjectFactory.createCustomerCreditBureauList();
			TypeCreditReport typeCreditReport = this.morwebObjectFactory.createTypeCreditReport();
			typeCreditReport.setValue(applicant.creditReport);
			typeCreditReport.setReportDate(xmlDataTypeFactory.newXMLGregorianCalendar()); // absurd.
			CustomerCreditBureau creditBureau = this.morwebObjectFactory.createCustomerCreditBureau();
			creditBureau.setRefkeyCreditBureau(applicant.contactRecord); // Absurd.
			creditBureauList.getCustomerCreditBureau().add(creditBureau);
			customerData.setCustomerCreditBureauList(creditBureauList); // customerCreditBureauList

			// ----- Temporary added end. ----- //

			for (Property property : applicant.properties) {
				TypeAssetOther asset = this.morwebObjectFactory.createTypeAssetOther(assetKey, property, applicant);
				JAXBElement<TypeAssetOther> assetAsElement = this.morwebObjectFactory.createAssetOther(asset);
				assetKey++;
				assetList.getAsset().add(assetAsElement);
			}

			for (Asset asset : applicant.assets) {
				TypeAssetOther asset1 = new TypeAssetOther(assetKey, asset, applicant);
				JAXBElement<TypeAssetOther> assetAsElement = this.morwebObjectFactory.createAssetOther(asset1);
				assetKey++;
				assetList.getAsset().add(assetAsElement);
			}

			for (Mortgage mortgage : applicant.mortgages) {
				TypeLiabilityOther liability = this.morwebObjectFactory.createTypeLiabilityOther();
				liability.setOutstandingBalance(BigDecimal.valueOf(Double.parseDouble(mortgage.balance)));
				liability.setMonthlyRepayment(BigDecimal.valueOf(Double.parseDouble(mortgage.monthlyPayment)));
				liability.setLenderName(underwrite.potentialProduct.lenderLine.toString()); // FIXME: This is lender ID
				liability.setLiabilityType("99"); // FIXME: 99 means "other"
				liability.setKey("Liability" + String.valueOf(applicant.mortgages.indexOf(mortgage))); // FIXME Random value.

				JAXBElement<TypeLiabilityOther> liabilityAsElement = this.morwebObjectFactory.createLiabilityOther(liability);
				liabilityList.getLiability().add(liabilityAsElement);
			}

			applicantCounter = applicantCounter + 1;
		}

		// Add Subject Property Address INfo
		SubjectProperty subjectProperty = new SubjectProperty();

		if (underwrite.potentialProduct.minHeatCost != null) {
			subjectProperty.setAnnualHeatingAmount(BigDecimal.valueOf(underwrite.potentialProduct.minHeatCost * 12));
		}

		if (underwrite.clientOpportunity.condoFees != null) {
			if (underwrite.clientOpportunity.condoFees > 0 || underwrite.clientOpportunity.isCondo == true) {
				Condo condo = new Condo();
				condo.setAnnualCondoFees(BigDecimal.valueOf(underwrite.clientOpportunity.condoFees * 12));
				subjectProperty.setCondo(condo);
			}
		}

		if (underwrite.clientOpportunity.plan != null && underwrite.clientOpportunity.lot != null && underwrite.clientOpportunity.block != null) {
			LegalAddress legalAddress = new LegalAddress();
			legalAddress.setLotNumber(underwrite.clientOpportunity.lot);
			legalAddress.setPlanNumber(underwrite.clientOpportunity.plan);

			legalAddress.setPIN(underwrite.clientOpportunity.block);

			String details = "PID / PIN: " + underwrite.clientOpportunity.townshipPID + "  Block: " + underwrite.clientOpportunity.block + "  Lot: " + underwrite.clientOpportunity.lot + "  Plan: " + underwrite.clientOpportunity.plan;
			legalAddress.setDetails(details);
			subjectProperty.setLegalAddress(legalAddress);
		}

		if (underwrite.clientOpportunity.mls != null) {
			subjectProperty.setMLSListed(true);
			subjectProperty.setMLSNumber(underwrite.clientOpportunity.mls);
		}

		subjectProperty.setNumberOfUnitsTotal(1);

		if (underwrite.clientOpportunity.livingInProperty != null) {
			String occupancyPurpose = "";
			if (SelectionHelper.compareSelection(AlgoOpportunity.WhosLiving.OwnerAndRenter, underwrite.clientOpportunity.livingInProperty)) {
				occupancyPurpose = "Residence";

				if (underwrite.clientOpportunity.monthlyRentalIncome != null) {
					SubjectPropertyOccupancyPartialOwnerOccupied partial = new SubjectPropertyOccupancyPartialOwnerOccupied();
					RentalDetails rentDetails = new RentalDetails();
					rentDetails.setAnnualGrossRentalIncome(BigDecimal.valueOf(underwrite.clientOpportunity.monthlyRentalIncome * 12));
					partial.setRentalDetails(rentDetails);
					subjectProperty.setSubjectPropertyOccupancyPartialOwnerOccupied(partial);
				}

			} else if (SelectionHelper.compareSelection(AlgoOpportunity.WhosLiving.Renter, underwrite.clientOpportunity.livingInProperty)) {
				occupancyPurpose = "Rental";

				if (underwrite.clientOpportunity.monthlyRentalIncome != null) {
					SubjectPropertyOccupancyRental rental = new SubjectPropertyOccupancyRental();
					RentalDetails rentDetails = new RentalDetails();
					rentDetails.setAnnualGrossRentalIncome(BigDecimal.valueOf(underwrite.clientOpportunity.monthlyRentalIncome * 12));
					rental.setRentalDetails(rentDetails);
					subjectProperty.setSubjectPropertyOccupancyRental(rental);
				}
			} else if (SelectionHelper.compareSelection(AlgoOpportunity.WhosLiving.Owner, underwrite.clientOpportunity.livingInProperty)) {
				occupancyPurpose = "Residence";

				SubjectPropertyOccupancyOwnerOccupied ownerOccupied = new SubjectPropertyOccupancyOwnerOccupied();
				subjectProperty.setSubjectPropertyOccupancyOwnerOccupied(ownerOccupied);

			}
			subjectProperty.setOccupancyPurpose(occupancyPurpose);
		}

		if (underwrite.clientOpportunity.squareFootage != null) {
			subjectProperty.setPropertySize(underwrite.clientOpportunity.squareFootage);
			subjectProperty.setPropertySizeUnits("Feet");
		}

		if (underwrite.clientOpportunity.propertyTaxes != null) {
			PropertyTax propertyTax = new PropertyTax();
			propertyTax.setAnnualTaxAmount(BigDecimal.valueOf(underwrite.clientOpportunity.propertyTaxes));
			subjectProperty.setPropertyTax(propertyTax);
		}

		// Property Type
		// 1 One Storey
		// 2 Bi-Level
		// 3 Two Storey
		// 4 Split Level
		// 5 One and Half Storey
		// 6 Three Storey
		// 99 Other

		if (underwrite.clientOpportunity.propertyStyle != null) {
			if (underwrite.clientOpportunity.propertyStyle.contains("1"))
				subjectProperty.setPropertyType("1");
			else if (underwrite.clientOpportunity.propertyStyle.contains("2"))
				subjectProperty.setPropertyType("2");
			else if (underwrite.clientOpportunity.propertyStyle.contains("3"))
				subjectProperty.setPropertyType("3");
			else if (underwrite.clientOpportunity.propertyStyle.contains("4"))
				subjectProperty.setPropertyType("4");
			else if (underwrite.clientOpportunity.propertyStyle.contains("5"))
				subjectProperty.setPropertyType("5");
			else if (underwrite.clientOpportunity.propertyStyle.contains("6"))
				subjectProperty.setPropertyType("6");
			else
				subjectProperty.setPropertyType("99");
		}

		// Property Description
		// 1 Detached
		// 2 Semi-detached
		// 3 Duplex
		// 4 Triplex
		// 5 Fourplex
		// 6 Apartment
		// 7 Townhouse
		// 8 Strip
		// 9 High Rise
		// 11 Row
		// 12 Mobile
		// 13 Modular Home
		// 14 Co-op
		// 15 Fiveplex
		// 16 Sixplex
		// 99 Other

		if (underwrite.clientOpportunity.propertyStyle != null) {
			if (underwrite.clientOpportunity.propertyType.contains("1"))
				subjectProperty.setPropertyDescriptionType("1");
			else if (underwrite.clientOpportunity.propertyType.contains("2"))
				subjectProperty.setPropertyDescriptionType("3");
			else if (underwrite.clientOpportunity.propertyType.contains("3"))
				subjectProperty.setPropertyDescriptionType("5");
			else if (underwrite.clientOpportunity.propertyType.contains("4"))
				subjectProperty.setPropertyDescriptionType("6");
			else if (underwrite.clientOpportunity.propertyType.contains("5"))
				subjectProperty.setPropertyDescriptionType("7");
			else if (underwrite.clientOpportunity.propertyType.contains("6"))
				subjectProperty.setPropertyDescriptionType("13");
			else if (underwrite.clientOpportunity.propertyType.contains("7"))
				subjectProperty.setPropertyDescriptionType("99");
		}

		// Heating Type
		// 1 Electric baseboard
		// 2 Force Air/Gas/Oil/Electric
		// 3 Hot Water
		// 4 Other
		if (underwrite.clientOpportunity.heating != null) {
			if (underwrite.clientOpportunity.heating.contains("1"))
				subjectProperty.setHeatingType("2");
			else if (underwrite.clientOpportunity.heating.contains("2"))
				subjectProperty.setHeatingType("1");
			else if (underwrite.clientOpportunity.heating.contains("3"))
				subjectProperty.setHeatingType("3");
			else if (underwrite.clientOpportunity.heating.contains("4"))
				subjectProperty.setHeatingType("99");
		}

		// Water Supply
		// 10 Municipal
		// 20 Private Well
		// 30 Shared Well
		// 40 Cistern
		// 99 Other
		if (underwrite.clientOpportunity.water != null) {
			if (underwrite.clientOpportunity.water.contains("1"))
				subjectProperty.setWaterSupplyType("10");
			else if (underwrite.clientOpportunity.water.contains("2"))
				subjectProperty.setWaterSupplyType("20");
			else if (underwrite.clientOpportunity.water.contains("3"))
				subjectProperty.setWaterSupplyType("99");
		}

		// Waste Disposal
		// 10 Sewer
		// 20 Septic
		// 30 Holding Tank
		// 99 Other
		if (underwrite.clientOpportunity.water != null) {
			if (underwrite.clientOpportunity.sewage.contains("1"))
				subjectProperty.setWaterWasteType("10");
			else if (underwrite.clientOpportunity.sewage.contains("2"))
				subjectProperty.setWaterWasteType("20");
			else if (underwrite.clientOpportunity.sewage.contains("3"))
				subjectProperty.setWaterWasteType("30");
			else if (underwrite.clientOpportunity.sewage.contains("4"))
				subjectProperty.setWaterWasteType("99");
		}

		// Parking Arrangements
		// 1 Single Attached Garage
		// 2 Single Detached Garage
		// 3 Double Attached Garage
		// 4 Double Detached Garage
		// 5 Triple Attached Garage
		// 6 Triple Detached Garage
		// 7 None
		if (underwrite.clientOpportunity.garageType != null) {
			if (underwrite.clientOpportunity.garageType.contains("1")) {
				// Attached
				if (underwrite.clientOpportunity.garageSize != null) {
					if (underwrite.clientOpportunity.garageSize.contains("1")) {
						subjectProperty.setParkingType("1");
					} else if (underwrite.clientOpportunity.garageSize.contains("2")) {
						subjectProperty.setParkingType("3");
					} else if (underwrite.clientOpportunity.garageSize.contains("3")) {
						subjectProperty.setParkingType("5");
					} else if (underwrite.clientOpportunity.garageSize.contains("4")) {
						subjectProperty.setParkingType("5");
					}
				}
			} else if (underwrite.clientOpportunity.garageType.contains("2")) {
				// Detached
				if (underwrite.clientOpportunity.garageSize != null) {
					if (underwrite.clientOpportunity.garageSize.contains("1")) {
						subjectProperty.setParkingType("2");
					} else if (underwrite.clientOpportunity.garageSize.contains("2")) {
						subjectProperty.setParkingType("4");
					} else if (underwrite.clientOpportunity.garageSize.contains("3")) {
						subjectProperty.setParkingType("6");
					} else if (underwrite.clientOpportunity.garageSize.contains("4")) {
						subjectProperty.setParkingType("6");
					}
				}
			} else if (underwrite.clientOpportunity.garageType.contains("3")) {
				// none
				subjectProperty.setParkingType("7");
			}
		}

		if (underwrite.clientOpportunity.buildingFunds != null) {
			if (underwrite.clientOpportunity.buildingFunds.contains("3")) {
				subjectProperty.setSelfBuildIndicator(true);
			} else
				subjectProperty.setSelfBuildIndicator(false);
		}

		// Property Usage
		// 1 Single Family
		// 5 Multiple with unit
		// 6 Apts with Stores
		// 7 Retail
		// 8 Office Building
		// 9 Industrial
		// 10 Hotel/Motel
		// 11 Recreational
		// 12 Land/Farm
		// 90 Other

		if (underwrite.clientOpportunity.preapprovedImLookingFora != null) {
			if (SelectionHelper.compareSelection(AlgoOpportunity.Looking.CottageRecProperty, underwrite.clientOpportunity.preapprovedImLookingFora)) {
				subjectProperty.setPropertyUsageType("11");
			} else if (SelectionHelper.compareSelection(AlgoOpportunity.Looking.RawLandLeasedLand, underwrite.clientOpportunity.preapprovedImLookingFora)) {
				subjectProperty.setPropertyUsageType("12");
			} else
				subjectProperty.setPropertyUsageType("1");
		}

		if (underwrite.clientOpportunity.lookingToApproved != null) {
			if (SelectionHelper.compareSelection(AlgoOpportunity.Looking.CottageRecProperty, underwrite.clientOpportunity.lookingToApproved)) {
				subjectProperty.setPropertyUsageType("11");
			} else if (SelectionHelper.compareSelection(AlgoOpportunity.Looking.RawLandLeasedLand, underwrite.clientOpportunity.lookingToApproved)) {
				subjectProperty.setPropertyUsageType("12");
			} else
				subjectProperty.setPropertyUsageType("1");
		}

		if (underwrite.clientOpportunity.lookingToRefinance != null) {
			if (SelectionHelper.compareSelection(AlgoOpportunity.Looking.CottageRecProperty, underwrite.clientOpportunity.lookingToRefinance)) {
				subjectProperty.setPropertyUsageType("11");
			} else if (SelectionHelper.compareSelection(AlgoOpportunity.Looking.RawLandLeasedLand, underwrite.clientOpportunity.lookingToRefinance)) {
				subjectProperty.setPropertyUsageType("12");
			} else
				subjectProperty.setPropertyUsageType("1");
		}

		if (underwrite.clientOpportunity.age != null) {

			XMLGregorianCalendar propertyDate = xmlDataTypeFactory.newXMLGregorianCalendar();
			propertyDate.setYear(underwrite.clientOpportunity.age);
			subjectProperty.setYearBuilt(propertyDate);
		}

		TypeApplicationAddressCanada addressProperty = new TypeApplicationAddressCanada();
		addressProperty.setPostalCode(underwrite.clientOpportunity.postalCode);

		TypePostalAddressStreetAddress postalAddress = new TypePostalAddressStreetAddress();
		if (underwrite.clientOpportunity.address != null) {
			postalAddress.setStreetName(underwrite.clientOpportunity.address);
			postalAddress.setUnitNumber(FIXME);
			postalAddress.setStreetNumber(FIXME);
			postalAddress.setStreetDirection("-1"); // FIXME -1 for unknown. Need more clarification.
			postalAddress.setPOBoxRRNumber(FIXME);
			postalAddress.setStreetType("-1"); // FIXME -1 for unknown. Need more clarification.
		}

		JAXBElement<TypePostalAddressStreetAddress> typePostalAddressStreetAddressElement = this.morwebObjectFactory.createPostalAddressStreetAddress(postalAddress);
		addressProperty.setPostalAddress(typePostalAddressStreetAddressElement);
		addressProperty.setKey(UUID.randomUUID().toString());

		if (underwrite.clientOpportunity.city != null)
			addressProperty.setCityTown(underwrite.clientOpportunity.city);

		if (underwrite.clientOpportunity.province != null) {
			if (underwrite.clientOpportunity.province.contains("BC"))
				addressProperty.setProvinceCode("10");
			else if (underwrite.clientOpportunity.province.contains("AB"))
				addressProperty.setProvinceCode("20");
			else if (underwrite.clientOpportunity.province.contains("SK"))
				addressProperty.setProvinceCode("30");
			else if (underwrite.clientOpportunity.province.contains("MB"))
				addressProperty.setProvinceCode("40");
			else if (underwrite.clientOpportunity.province.contains("ON"))
				addressProperty.setProvinceCode("50");
			else if (underwrite.clientOpportunity.province.contains("QC"))
				addressProperty.setProvinceCode("60");
			else if (underwrite.clientOpportunity.province.contains("NB"))
				addressProperty.setProvinceCode("70");
			else if (underwrite.clientOpportunity.province.contains("NS"))
				addressProperty.setProvinceCode("80");
			else if (underwrite.clientOpportunity.province.contains("NL"))
				addressProperty.setProvinceCode("90");
			else if (underwrite.clientOpportunity.province.contains("PE"))
				addressProperty.setProvinceCode("100");
			else if (underwrite.clientOpportunity.province.contains("YK"))
				addressProperty.setProvinceCode("110");
			else if (underwrite.clientOpportunity.province.contains("NT"))
				addressProperty.setProvinceCode("120");
			else if (underwrite.clientOpportunity.province.contains("NU"))
				addressProperty.setProvinceCode("130");
			else
				addressProperty.setProvinceCode("99999");
		}

		addressProperty.setCountryCode("1");

		Loan loan = new Loan();

		loan.setLoanAmount(BigDecimal.valueOf(underwrite.expectedMortgageAmount));
		loan.setAmortizationMonths(BigInteger.valueOf(underwrite.amortization));

		if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Month6, underwrite.potentialProduct.term))
			loan.setTermMonths(BigInteger.valueOf(6));
		else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year1, underwrite.potentialProduct.term))
			loan.setTermMonths(BigInteger.valueOf(12));
		else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year2, underwrite.potentialProduct.term))
			loan.setTermMonths(BigInteger.valueOf(24));
		else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year3, underwrite.potentialProduct.term))
			loan.setTermMonths(BigInteger.valueOf(36));
		else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year4, underwrite.potentialProduct.term))
			loan.setTermMonths(BigInteger.valueOf(48));
		else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year5, underwrite.potentialProduct.term))
			loan.setTermMonths(BigInteger.valueOf(60));
		else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year6, underwrite.potentialProduct.term))
			loan.setTermMonths(BigInteger.valueOf(72));
		else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year7, underwrite.potentialProduct.term))
			loan.setTermMonths(BigInteger.valueOf(84));
		else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year8, underwrite.potentialProduct.term))
			loan.setTermMonths(BigInteger.valueOf(96));
		else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year9, underwrite.potentialProduct.term))
			loan.setTermMonths(BigInteger.valueOf(108));
		else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year10, underwrite.potentialProduct.term))
			loan.setTermMonths(BigInteger.valueOf(120));

		if (underwrite.clientOpportunity.chargeOnTitle.contains("1")) {
			loan.setChargeType("1");
		} else if (underwrite.clientOpportunity.chargeOnTitle.contains("2")) {
			loan.setChargeType("2");
		} else if (underwrite.clientOpportunity.chargeOnTitle.contains("3")) {
			loan.setChargeType("3");
		} else if (underwrite.clientOpportunity.chargeOnTitle.contains("4")) {
			loan.setChargeType("4");
		} else if (underwrite.clientOpportunity.chargeOnTitle.contains("5")) {
			loan.setChargeType("5");
		}

		bdiRequest.setCustomerData(customerData);

		// Set Common Data
		CommonData commonData = new CommonData();

		// TODO Set all common Data methods

		// commonData.setAddressList(); // FIXME This is required by Morweb.
		commonData.setAssetList(assetList);
		commonData.setLiabilityList(liabilityList);
		// commonData.setCreditBureauList(value);
		AddressList addressList = this.morwebObjectFactory.createAddressList();
		JAXBElement<TypeApplicationAddressCanada> typeApplicationAddressElement = this.morwebObjectFactory.createApplicationAddressCanada(addressProperty); // FIXME: This is direct canada address.
		addressList.getApplicationAddress().add(typeApplicationAddressElement);
		commonData.setAddressList(addressList);

		bdiRequest.setCommonData(commonData);

		// // Set Mortgage Application
		MortgageApplication mortgageApplication = new MortgageApplication();
		// FIXME Fix MortgageApplication
		bdiRequest.setMortgageApplication(mortgageApplication);

		// // Set Created Date Time
		GregorianCalendar todayDate = (GregorianCalendar) GregorianCalendar.getInstance();
		final String dateAsString = this.morwebDateTimeTFormat.format(todayDate.getTime());
		XMLGregorianCalendar xgcal = xmlDataTypeFactory.newXMLGregorianCalendar(dateAsString);
		bdiRequest.setCreatedDateTime(xgcal);

		// // Set Unit ID
		bdiRequest.setCreatedUnitId(Constant.morwebCreatedUnitId);
		//
		// // Set UserID
		bdiRequest.setCreatedUserId(Constant.morwebCreatedUserId);

		Clipboard clipboard = this.msCanadaObjectFactory.createClipboard();

		Clipboard.Context context = this.msCanadaObjectFactory.createClipboardContext();
		context.setApplicationName(com.syml.constant.Constant.morwebApplicationName);
		context.setDateTime(morwebDateTimeTFormat.format(new java.util.Date()));
		context.setLanguage(com.syml.constant.Constant.morwebLanguage);
		context.setSessionId(com.syml.constant.Constant.morwebSessionId);
		context.setUserid(com.syml.constant.Constant.morwebUserid);
		clipboard.setContext(context);

		String applicationID = "1000";
		// if (underwrite.clientOpportunity.applicationNo != null)
		// applicationID =
		// underwrite.clientOpportunity.applicationNo.toString();

		Clipboard.MsgRequest.Comment comment = this.msCanadaObjectFactory.createClipboardMsgRequestComment();
		comment.setCommentText("Message request fron MorwebCall - underwriting app.");

		Clipboard.MsgRequest msgRequest = this.msCanadaObjectFactory.createClipboardMsgRequest();
		msgRequest.setComment(comment);
		msgRequest.setProvider(com.syml.constant.Constant.morwebProvider);
		msgRequest.setRecipient(com.syml.constant.Constant.morwebRecipient);
		msgRequest.setRequestId(applicationID);
		msgRequest.setServiceType(com.syml.constant.Constant.morwebServiceType);
		msgRequest.setSource(com.syml.constant.Constant.morwebSource);
		clipboard.setMsgRequest(msgRequest);

		Clipboard.ApplicationData applicationData = this.msCanadaObjectFactory.createClipboardApplicationData();
		applicationData.getContent().add(bdiRequest);
		clipboard.setApplicationData(applicationData);

		this.clipboard = clipboard;

	}

	public Clipboard getClipboard() {
		return this.clipboard;
	}

	public BDIRequest getBdiRequest() {
		return bdiRequest;
	}

	public void setBdiRequest(BDIRequest toSet) {
		this.bdiRequest = toSet;
	}
}
