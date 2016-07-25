package com.syml.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.syml.bean.algo.UnderwritePostSel;
import com.syml.domain.Address;
import com.syml.domain.Applicant;
import com.syml.domain.Asset;
import com.syml.domain.Income;
import com.syml.domain.Liability;
import com.syml.domain.Mortgage;
import com.syml.domain.Opportunity;
import com.syml.domain.Product;
import com.syml.domain.Property;
import com.syml.morweb.CustomerAddressPrimaryResidence;
import com.syml.morweb.CustomerAssetList;
import com.syml.morweb.CustomerAssetOther;
import com.syml.morweb.CustomerData;
import com.syml.morweb.CustomerLiabilityList;
import com.syml.morweb.CustomerLiabilityOther;
import com.syml.morweb.CustomerList;
import com.syml.morweb.CustomerTelephoneNumberList;
import com.syml.morweb.EarnedIncome;
import com.syml.morweb.EarnedIncomeList;
import com.syml.morweb.Employment;
import com.syml.morweb.EmploymentList;
import com.syml.morweb.ObjectFactory;
import com.syml.morweb.TypeApplicationAddress;
import com.syml.morweb.TypeApplicationAddressCanada;
import com.syml.morweb.TypeAssetOther;
import com.syml.morweb.TypeAssetRealEstate;
import com.syml.morweb.TypeCustomer;
import com.syml.morweb.TypeCustomerAddress;
import com.syml.morweb.TypeCustomerAddressList;
import com.syml.morweb.TypeCustomerReference;
import com.syml.morweb.TypeLiabilityOther;
import com.syml.morweb.TypeLiabilityRealEstate;
import com.syml.morweb.UnearnedIncome;
import com.syml.morweb.UnearnedIncomeList;

import static com.syml.util.StringUtil.*;
import static com.syml.service.MorwebServiceUtil.*;

public class MorwebCustomerDataVO {

	private static final Logger logger = LoggerFactory.getLogger(MorwebCustomerDataVO.class);

	private final UnderwritePostSel underwrite;
	private final ObjectFactory objectFactory;

	private final List<JAXBElement<? extends TypeApplicationAddress>> customerAddresses;
	private final List<JAXBElement<TypeAssetRealEstate>> customerAssetRealEstates;
	private final List<JAXBElement<TypeAssetOther>> customerAssetOthers;
	private final List<JAXBElement<TypeLiabilityRealEstate>> customerLiabilityRealEstates;
	private final List<JAXBElement<TypeLiabilityOther>> customerLiabilityOthers;

	public MorwebCustomerDataVO(UnderwritePostSel underwrite) {
		this.underwrite = underwrite;
		this.objectFactory = new ObjectFactory();
		this.customerAddresses = new ArrayList<JAXBElement<? extends TypeApplicationAddress>>();
		this.customerAssetRealEstates = new ArrayList<JAXBElement<TypeAssetRealEstate>>();
		this.customerAssetOthers = new ArrayList<JAXBElement<TypeAssetOther>>();
		this.customerLiabilityRealEstates = new ArrayList<JAXBElement<TypeLiabilityRealEstate>>();
		this.customerLiabilityOthers = new ArrayList<JAXBElement<TypeLiabilityOther>>();
	}


	public CustomerData createCustomerData() {
		final CustomerData customerData = this.objectFactory.createCustomerData();
		final Opportunity opportunity = this.underwrite.clientOpportunity;
		final List<Applicant> applicants = opportunity.getApplicants();
		final Product potentialProduct = this.underwrite.potentialProduct;

		customerData.setCustomerList( createCustomerList(applicants) );
		customerData.setCustomerAddressList( createTypeCustomerAddressList(applicants) );
		customerData.setCustomerAssetList( createCustomerAssetList(applicants) );
		customerData.setCustomerLiabilityList( createTypeCustomerLiabilityList(applicants, potentialProduct) );

		return customerData;
	}


	protected CustomerList createCustomerList(List<Applicant> applicants) {
		final CustomerList customerList = this.objectFactory.createCustomerList();
		int customerCounter = 0;
		for (final Applicant applicant : applicants) {
			final TypeCustomer customer = this.objectFactory.createTypeCustomer();
			customer.setKey(String.valueOf(applicant.getId()));
			customer.setFirstName(applicant.getApplicantName());
			customer.setLastName(applicant.getApplicantLastName());
			customer.setMaritalStatus( createMorwebCustomerMaritalStatus(applicant.getRelationshipStatus()) );
			if ( !isNullOrEmpty(applicant.getSin()) )
				customer.setSIN(applicant.getSin().replace("-", "").replace(".", ""));

			customer.setEmailAddress1(applicant.getEmailPersonal());
			customer.setEmailAddress2(applicant.getEmailWork());
			customer.setDateBirth(createLocalMorwebDate(applicant.getDob()));
			customer.setCustomerTelephoneNumberList( this.createCustomerTelephoneNumberList(applicant) );

			final List<Income> incomes = applicant.getIncomes();
			if (incomes != null) {
				final EmploymentList employmentList = this.objectFactory.createEmploymentList();
				for (final Income income : incomes) {
					final MorwebEmploymentDTO employmentDTO = parseCRMTypeOfIncomeToMorwebEmploymentDTO(income.getTypeOfIncome());
					final Employment employment = this.objectFactory.createEmployment();

					employment.setEmploymentType(employmentDTO.employmentType);
					employment.setEmploymentStatus(employmentDTO.employmentStatus);
					employment.setJobTitle(income.getPosition());
					employment.setDateStart(createLocalMorwebDate(new Date())); // FIXME: This is completely wrong!

					if (income.getHistorical() == false) {
						if (employmentDTO.isEarnedIncomeType) {
						final EarnedIncome earnedIncome = this.objectFactory.createEarnedIncome();
							earnedIncome.setEarnedIncomeType(employmentDTO.earnedIncomeType);
							earnedIncome.setPaymentFrequency(employmentDTO.paymentFrequency);
							earnedIncome.setEarnedIncomeAmount(new BigDecimal(income.getAnnualIncome()));
	
							final EarnedIncomeList earnedIncomeList = this.objectFactory.createEarnedIncomeList();
							earnedIncomeList.getEarnedIncome().add(earnedIncome);
							employment.setEarnedIncomeList(earnedIncomeList);
						} else {
							final UnearnedIncome unearnedIncome = this.objectFactory.createUnearnedIncome();
							unearnedIncome.setUnearnedIncomeType(employmentDTO.unEarnedIncomeType);
							unearnedIncome.setPaymentFrequency(employmentDTO.paymentFrequency);
							unearnedIncome.setUnearnedIncomeAmount(new BigDecimal(income.getAnnualIncome()));

							final UnearnedIncomeList unearnedIncomeList = this.objectFactory.createUnearnedIncomeList();
							unearnedIncomeList.getUnearnedIncome().add(unearnedIncome);
							customer.setUnearnedIncomeList(unearnedIncomeList);
						}
					}
					employmentList.getEmployment().add(employment);
				}
				customer.setEmploymentList(employmentList);
			}

			if (customerCounter == 0) {
				customerList.setCustomerBorrower(customer);
			} else {
				final JAXBElement<TypeCustomer> customerJaxbElement = this.objectFactory.createCustomerBorrower(customer);
				customerList.getCustomer().add(customerJaxbElement);
			}
			customerCounter ++;
		}

		return customerList;
	}


	protected TypeCustomerAddressList createTypeCustomerAddressList(List<Applicant> applicants) {
		final TypeCustomerAddressList customerAddressList = this.objectFactory.createTypeCustomerAddressList();

		for (final Applicant applicant : applicants) {
			final Integer applicantId = applicant.getId();
			final TypeCustomerReference customerReference = createCustomerReference(this.objectFactory, applicantId);
			final List<Address> addresses = applicant.addresses;

			logger.debug(">>> createTypeCustomerAddressList(). applicant.addresses size: {}", addresses.size());
			if (addresses != null) {
				for (final Address address : addresses) {
					// Address for CommonData begin
					final int currentAddressIndex = applicant.addresses.indexOf(address);
					final TypeApplicationAddressCanada addressCanada = this.objectFactory.createTypeApplicationAddressCanada();
					addressCanada.setKey("APPL-" + applicantId + "_ADDR.CA-" + currentAddressIndex);
					addressCanada.setCityTown(address.getCity());
					addressCanada.setPostalCode(address.getPostalCode());
					addressCanada.setProvinceCode( createProvinceCode(address.getProvince()) );
					addressCanada.setCountryCode("CA");

					JAXBElement<TypeApplicationAddressCanada> addressCanadaJaxbElement = this.objectFactory.createApplicationAddressCanada(addressCanada);
					this.customerAddresses.add(addressCanadaJaxbElement);
					// Address for common data end.

					// Address for references in Customer data begin.
					final CustomerAddressPrimaryResidence customerAddressPrimaryResidence = this.objectFactory.createCustomerAddressPrimaryResidence();
					customerAddressPrimaryResidence.getCustomerReference().add(customerReference);
					customerAddressPrimaryResidence.setRefkeyAddress(addressCanada.getKey());

					JAXBElement<? extends TypeCustomerAddress> customerAddressJaxbElement = this.objectFactory.createCustomerAddressPrimaryResidence(customerAddressPrimaryResidence);
					customerAddressList.getCustomerAddress().add(customerAddressJaxbElement);
					// Address for references in Customer data end.
				}
			}
		}
		return customerAddressList;
	}


	protected CustomerAssetList createCustomerAssetList(List<Applicant> applicants) {
		CustomerAssetList customerAssetList = this.objectFactory.createCustomerAssetList();

		for (final Applicant applicant : applicants) {

			final Integer applicantId = applicant.getId();
			final TypeCustomerReference customerReference = createCustomerReference(this.objectFactory, applicantId);

			final List<Property> properties = applicant.getProperties();
			if (properties != null) {
				for (final Property property : properties) {
					final TypeAssetRealEstate realEstate = this.objectFactory.createTypeAssetRealEstate();
					realEstate.setKey(Integer.toString(property.getId()));
					realEstate.setValue(new BigDecimal(property.getValue()));
					final JAXBElement<TypeAssetRealEstate> realEstateJaxbElement = this.objectFactory.createAssetRealEstate(realEstate);

					this.customerAssetRealEstates.add(realEstateJaxbElement);

					final CustomerAssetOther customerAssetOther = this.objectFactory.createCustomerAssetOther();
					customerAssetOther.setCustomerReference(customerReference);
					customerAssetOther.setRefkeyAsset(realEstate.getKey());

					customerAssetList.getCustomerAssetOther().add(customerAssetOther);
				}
			}

			final List<Asset> assets = applicant.getAssets();
			if (assets != null) {
				for (final Asset asset : assets) {
					final TypeAssetOther assetOther = this.objectFactory.createTypeAssetOther(asset.getId(), asset, applicant);
					assetOther.setKey(asset.getId().toString()); // Replace current logic in constructor of TypeAssetOther
					final JAXBElement<TypeAssetOther> assetOtherJaxbElement = this.objectFactory.createAssetOther(assetOther);

					this.customerAssetOthers.add(assetOtherJaxbElement);

					final CustomerAssetOther customerAssetOther = this.objectFactory.createCustomerAssetOther();
					customerAssetOther.setCustomerReference(customerReference);
					customerAssetOther.setRefkeyAsset(assetOther.getKey());

					customerAssetList.getCustomerAssetOther().add(customerAssetOther);
				}
			}
		}

		return customerAssetList;
	}


	protected CustomerLiabilityList createTypeCustomerLiabilityList(List<Applicant> applicants, Product potentialProduct) {
		CustomerLiabilityList customerLiabilityList = this.objectFactory.createCustomerLiabilityList();

		for (final Applicant applicant : applicants) {
			final Integer applicantId = applicant.getId();
			final TypeCustomerReference customerReference = createCustomerReference(this.objectFactory, applicantId);

			final List<Mortgage> mortgages = applicant.getMortgages();
			if (mortgages != null) {
				for (final Mortgage mortgage : mortgages) {
					if ( !isNullOrEmpty(mortgage.getType()) ) {
						/** This is to fulfill "Describes a current property with an existing mortgage". (MSCIS BDI Request Schema v1: Line 39). */
						if (mortgage.type.toLowerCase().contains("open")) {
							final TypeLiabilityRealEstate mortgageLiability = this.objectFactory.createTypeLiabilityRealEstate();
							final String mortgageId = mortgage.getId().toString();
							mortgageLiability.setKey(mortgageId);
							mortgageLiability.setOutstandingBalance(BigDecimal.valueOf(Double.parseDouble(mortgage.getBalance())));
							mortgageLiability.setMonthlyRepayment(BigDecimal.valueOf(Double.parseDouble(mortgage.getMonthlyPayment())));
							mortgageLiability.setLenderName(String.valueOf(potentialProduct.getLender()));
							mortgageLiability.setOriginalLoanAmount(BigDecimal.ZERO); // FIXME: Required, but unknown value.
							mortgageLiability.setChargeType("99"); // FIXME: Required, but unknown value.
							mortgageLiability.setRepaymentType("1"); // FIXME: Required, but unknown value.
							mortgageLiability.setRedemptionStatus("1"); // FIXME: Required, but unknown value.

							JAXBElement<TypeLiabilityRealEstate> liabilityRealEstateJAJaxbElement = this.objectFactory.createLiabilityRealEstate(mortgageLiability);
							this.customerLiabilityRealEstates.add(liabilityRealEstateJAJaxbElement);

							final CustomerLiabilityOther customerLiabilityOther = this.objectFactory.createCustomerLiabilityOther();
							customerLiabilityOther.setCustomerReference(customerReference);
							customerLiabilityOther.setRefkeyLiability(mortgageLiability.getKey());

							customerLiabilityList.getCustomerLiabilityOther().add(customerLiabilityOther);
						} else continue;
					} else continue;
				}
			}

			final List<Liability> liabilities = applicant.getLiabilities();
			if (liabilities != null) {
				for (final Liability liability : liabilities) {
					final TypeLiabilityOther liabilityOther = this.objectFactory.createTypeLiabilityOther();
					final String mortgageId = liability.getId().toString();
					liabilityOther.setKey(mortgageId);
					liabilityOther.setLenderName(potentialProduct.lender.toString());
					liabilityOther.setLiabilityType(MorwebServiceUtil.getLiabilityType(liability.getType()));

					JAXBElement<TypeLiabilityOther> liabilityOtherJaxbElement = this.objectFactory.createLiabilityOther(liabilityOther);
					this.customerLiabilityOthers.add(liabilityOtherJaxbElement);

					final CustomerLiabilityOther customerLiabilityOther = this.objectFactory.createCustomerLiabilityOther();
					customerLiabilityOther.setCustomerReference(customerReference);
					customerLiabilityOther.setRefkeyLiability(liabilityOther.getKey());

					customerLiabilityList.getCustomerLiabilityOther().add(customerLiabilityOther);
				}
			}
		}

		return customerLiabilityList;
	}


	private CustomerTelephoneNumberList createCustomerTelephoneNumberList(final Applicant applicant) {
		CustomerTelephoneNumberList telephones = this.objectFactory.createCustomerTelephoneNumberList();

		final String homeNumber = applicant.getHome();
		if (!isNullOrEmpty(homeNumber)) {
			telephones.addTelephoneNumber( this.objectFactory.createCustomerTelephoneNumber(homeNumber) );
		}

		final String cellNumber = applicant.getCell();
		if (!isInteger(cellNumber)) {
			telephones.addTelephoneNumber( this.objectFactory.createCustomerTelephoneNumber(cellNumber, true) );
		}

		final String workNumber = applicant.getWork();
		if (!isNullOrEmpty(workNumber)) {
			telephones.addTelephoneNumber( this.objectFactory.createCustomerTelephoneNumber(workNumber) );
		}

		return telephones;
	}


	public List<JAXBElement<? extends TypeApplicationAddress>> getCustomerAddressesAsJaxbElement() {
		return customerAddresses;
	}

	public List<JAXBElement<TypeAssetRealEstate>> getCustomerAssetRealEstates() {
		return customerAssetRealEstates;
	}

	public List<JAXBElement<TypeAssetOther>> getCustomerAssetOthers() {
		return customerAssetOthers;
	}

	public List<JAXBElement<TypeLiabilityRealEstate>> getCustomerLiabilityRealEstates() {
		return customerLiabilityRealEstates;
	}

	public List<JAXBElement<TypeLiabilityOther>> getCustomerLiabilityOthers() {
		return customerLiabilityOthers;
	}

}
