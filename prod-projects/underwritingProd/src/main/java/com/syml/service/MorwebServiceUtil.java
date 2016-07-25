package com.syml.service;

import static com.syml.util.StringUtil.isNullOrEmpty;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.syml.bean.algo.AlgoLiability.LiabilityType;
import com.syml.domain.Opportunity;
import com.syml.morweb.Condo;
import com.syml.morweb.Employment;
import com.syml.morweb.LegalAddress;
import com.syml.morweb.ObjectFactory;
import com.syml.morweb.PropertyTax;
import com.syml.morweb.RentalDetails;
import com.syml.morweb.SubjectPropertyAddress;
import com.syml.morweb.SubjectPropertyOccupancyOwnerOccupied;
import com.syml.morweb.SubjectPropertyOccupancyPartialOwnerOccupied;
import com.syml.morweb.TypeApplicationAddress;
import com.syml.morweb.TypeApplicationAddressCanada;
import com.syml.morweb.TypeCustomerReference;

import static com.syml.util.StringUtil.*;

public final class MorwebServiceUtil {

	private static final Logger logger = LoggerFactory.getLogger(MorwebServiceUtil.class);


	public static class SubjectPropertyAddressDTO {
		private final SubjectPropertyAddress subjectPropertyAddress;
		private final TypeApplicationAddress typeApplicationAddress;

		public SubjectPropertyAddressDTO(final SubjectPropertyAddress address, final TypeApplicationAddress typeApplicationAddress) {
			this.subjectPropertyAddress = address;
			this.typeApplicationAddress = typeApplicationAddress;
		}

		public SubjectPropertyAddress getSubjectPropertyAddress() {
			return subjectPropertyAddress;
		}

		public TypeApplicationAddress getTypeApplicationAddress() {
			return typeApplicationAddress;
		}
	}


	public static final class MorwebEmploymentDTO {
		public String employmentType;
		public String employmentStatus;

		/** {@link #earnedIncomeType} and {@link #unEarnedIncomeType} is inter-changeable. In one business logic 
		 * (for example in creation of {@link Employment} object), {@link #earnedIncomeType} and {@link #unEarnedIncomeType}
		 * cannot be used together. To check this, see {@link #isEarnedIncomeType}.    
		 */
		public String earnedIncomeType;

		/** @see #earnedIncomeType */
		public String unEarnedIncomeType;
		public String paymentFrequency;

		/** Used to check whether implementation need to create UnearnedIncome or EarnedIncome object. */
		public boolean isEarnedIncomeType;

		public MorwebEmploymentDTO() {}

		public MorwebEmploymentDTO(String employmentType, String employmentStatus, String earnedIncomeType, String unEarnedIncomeType, String paymentFrequency) {
			this.employmentType = employmentType; 
			this.employmentStatus = employmentStatus; 
			this.unEarnedIncomeType = unEarnedIncomeType; 
			this.earnedIncomeType = earnedIncomeType; 
			this.paymentFrequency = paymentFrequency;
		}
	}


	/**
	 * Create {@link XMLGregorianCalendar} instance without specifics time-zone 
	 * (UTC, etc) involved. If <code>pattern</code> parameter is null, default 
	 * <code>pattern</code> is <code>"dd-MM-yyyy"</code>. If <code>pattern</code> 
	 * parameter size is more than 1, only first index would be used as valid 
	 * pattern. 
	 */
	public static final XMLGregorianCalendar createLocalMorwebDate(Date date, String... pattern) {
		String defaultPattern = "yyyy-MM-dd";
		if (pattern != null && pattern.length > 0) 
			defaultPattern = pattern[0];

		final DateFormat dateFormat = new SimpleDateFormat(defaultPattern);
		final String dateAsString = dateFormat.format(date);
		try {
			return DatatypeFactory.newInstance().newXMLGregorianCalendar(dateAsString);
		} catch (DatatypeConfigurationException e) {
			throw new RuntimeException(e);
		}
	}


	public static final String createProvinceCode(final String crmProvince) {
		if (isNullOrEmpty(crmProvince))
			return "99999";

		final String province = crmProvince.toUpperCase();
		if (province.contains("BC")) return "10";
		else if (province.contains("AB")) return "20";
		else if (province.contains("SK")) return "30";
		else if (province.contains("MB")) return "40";
		else if (province.contains("ON")) return "50";
		else if (province.contains("QC")) return "60";
		else if (province.contains("NB")) return "70";
		else if (province.contains("NS")) return "80";
		else if (province.contains("NL")) return "90";
		else if (province.contains("PE")) return "100";
		else if (province.contains("YK")) return "110";
		else if (province.contains("NT")) return "120";
		else if (province.contains("NU")) return "130";
		else return "99999";
	}


	public static final TypeCustomerReference createCustomerReference(final ObjectFactory of, final Integer applicantId) {
		TypeCustomerReference result = of.createTypeCustomerReference();
		final String refKeyCustomer = String.valueOf(applicantId);
		result.setRefkeyCustomer(refKeyCustomer);
		return result;
	}


	/**
	 * Original value from Morweb: 
	 * 1.Personal Loan; 2.Secured Loan; 3.Car Loan; 4.Lease Agreement; 6.Credit Card; 7.Line Of Credit; 8.Child Support; 99.Other 
	 */
	public static final String getLiabilityType(final String domainLiabilityType) {
		if (isNullOrEmpty(domainLiabilityType)) return "99";
		if (domainLiabilityType.equalsIgnoreCase(LiabilityType.LineOfCredit.name())) {
			return "7";
		} else {
			return "99";
		}
	}


	public static final MorwebEmploymentDTO parseCRMTypeOfIncomeToMorwebEmploymentDTO(final String crmTypeOfIncome) {
		logger.debug(">>> crmTypeOfIncome: {}", crmTypeOfIncome);

		if (isNullOrEmpty(crmTypeOfIncome))
			return new MorwebEmploymentDTO("7", "99", "99", "99", "1");

		final String income = crmTypeOfIncome.toLowerCase();
		MorwebEmploymentDTO dto = new MorwebEmploymentDTO();

		/** Reference: MorwebCall line 106 - 375 */
		if (income.contains("employed")) { // Employed
			dto.employmentType = "1";
			dto.employmentStatus = "10";
			dto.earnedIncomeType = "1";
			dto.unEarnedIncomeType = "99"; // Not used in actual business logic where income = employed.
			dto.paymentFrequency = "1";
			dto.isEarnedIncomeType = true;
		} else if (income.contains("self")) { // SelfEmployed
			dto.employmentType = "1";
			dto.employmentStatus = "20";
			dto.earnedIncomeType = "1";
			dto.unEarnedIncomeType = "99"; // Not used in actual business logic where income = self-employed.
			dto.paymentFrequency = "1";
			dto.isEarnedIncomeType = true;
		} else if (income.contains("rental")) { // Rental
			dto.employmentType = "7";
			dto.employmentStatus = "99";
			dto.earnedIncomeType = "99"; // Not used in actual business logic where income = rental
			dto.unEarnedIncomeType = "3"; 
			dto.paymentFrequency = "1";
			dto.isEarnedIncomeType = false;
		} else if (income.contains("child")) { // ChildTaxCredit
			dto.employmentType = "7";
			dto.employmentStatus = "99";
			dto.earnedIncomeType = "99"; // Not used in actual business logic where income = ChildTaxCredit
			dto.unEarnedIncomeType = "3"; 
			dto.paymentFrequency = "1";
			dto.isEarnedIncomeType = false;
		} else if (income.contains("living")) { // LivingAllowance
			dto.employmentType = "7";
			dto.employmentStatus = "99";
			dto.earnedIncomeType = "99"; // Not used in actual business logic where income = LivingAllowance
			dto.unEarnedIncomeType = "3"; 
			dto.paymentFrequency = "1";
			dto.isEarnedIncomeType = false;
		} else if (income.contains("vehicle")) { // VehicleAllowance
			dto.employmentType = "7";
			dto.employmentStatus = "99";
			dto.earnedIncomeType = "99"; // Not used in actual business logic where income = VehicleAllowance
			dto.unEarnedIncomeType = "3"; 
			dto.paymentFrequency = "1";
			dto.isEarnedIncomeType = false;
		} else if (income.contains("bonus")) { // Bonus
			dto.employmentType = "1";
			dto.employmentStatus = "10";
			dto.earnedIncomeType = "3"; 
			dto.unEarnedIncomeType = "99"; // Not used in actual business logic where income = Bonus
			dto.paymentFrequency = "1";
			dto.isEarnedIncomeType = true;
		} else if (income.contains("commission")) { // Commission
			dto.employmentType = "1";
			dto.employmentStatus = "30";
			dto.earnedIncomeType = "4"; 
			dto.unEarnedIncomeType = "99"; // Not used in actual business logic where income = Commission
			dto.paymentFrequency = "1";
			dto.isEarnedIncomeType = true;
		} else if (income.contains("interest")) { // Interest
			dto.employmentType = "7";
			dto.employmentStatus = "99";
			dto.earnedIncomeType = "5"; 
			dto.unEarnedIncomeType = "99"; // Not used in actual business logic where income = Interest
			dto.paymentFrequency = "1";
			dto.isEarnedIncomeType = true;
		} else if (income.contains("overtime")) { // Overtime
			dto.employmentType = "1";
			dto.employmentStatus = "10";
			dto.earnedIncomeType = "1"; 
			dto.unEarnedIncomeType = "99"; // Not used in actual business logic where income = Overtime
			dto.paymentFrequency = "1";
			dto.isEarnedIncomeType = true;
		} else if (income.contains("pension")) { // Pension
			dto.employmentType = "1";
			dto.employmentStatus = "10";
			dto.earnedIncomeType = "6"; 
			dto.unEarnedIncomeType = "99"; // Not used in actual business logic where income = Pension
			dto.paymentFrequency = "1";
			dto.isEarnedIncomeType = true;
		} else if (income.contains("retired")) { // Retired
			dto.employmentType = "7";
			dto.employmentStatus = "99";
			dto.earnedIncomeType = "99"; // Not used in actual business logic where income = Retired
			dto.unEarnedIncomeType = "2"; 
			dto.paymentFrequency = "1";
			dto.isEarnedIncomeType = false;
		} else {
			dto = new MorwebEmploymentDTO("7", "99", "99", "99", "1");
		}
		return dto;
	}


	public static final String createMorwebCustomerMaritalStatus(final String uwRelationshipStatus) {
		if (isNullOrEmpty(uwRelationshipStatus)) {
			return "99";
		}

		if (uwRelationshipStatus.toLowerCase().contains("single")) {
			return "1";
		} else if (uwRelationshipStatus.toLowerCase().contains("married")) {
			return "2";
		} else if (uwRelationshipStatus.toLowerCase().contains("common")) {
			return "6";
		} else if (uwRelationshipStatus.toLowerCase().contains("separated")) {
			return "4";
		} else if (uwRelationshipStatus.toLowerCase().contains("divorced")) {
			return "5";
		} else {
			return "99";
		}
	}


	public static final Condo createCondo(final Opportunity clientOpportunity, final ObjectFactory objectFactory) {
		if (clientOpportunity.getCondoFees() != null) {
			if (clientOpportunity.getCondoFees() > 0 || clientOpportunity.getIsCondo() == true) {
				final Condo condo = objectFactory.createCondo();
				condo.setAnnualCondoFees(BigDecimal.valueOf(clientOpportunity.getCondoFees() * 12));
				return condo;
			}
		}
		return null;
	}


	public static final LegalAddress createLegalAddress(final Opportunity clientOpportunity, final ObjectFactory objectFactory) {
		final String townshipId = clientOpportunity.townshipPID;
		final String plan = clientOpportunity.getPlan();
		final String lot = clientOpportunity.getLot();
		final String block = clientOpportunity.getBlock();

		if (allIsNotNullAndNotEmpty(plan, lot, block)) {
			final String details = "PID / PIN:" + townshipId + ", Block:" + block + ", Lot: " + lot + ", Plan:" + plan;
			final LegalAddress legalAddress = objectFactory.createLegalAddress();
			legalAddress.setLotNumber(clientOpportunity.lot);
			legalAddress.setPlanNumber(clientOpportunity.plan);
			legalAddress.setPIN(clientOpportunity.block);
			legalAddress.setDetails(details);

			return legalAddress;
		} else {
			logger.warn(">>> Cannot createLegalAddress(). Plan, Lot, or Block is null or empty. Plan={}, Lot={}, Block={}", plan, lot, block);
			return null;
		}
	}


	public static final PropertyTax createPropertyTax(final Opportunity clientOpportunity, final ObjectFactory objectFactory) {
		if (clientOpportunity.getPropertyTaxes() != null) {
			final PropertyTax propertyTax = objectFactory.createPropertyTax();
			propertyTax.setAnnualTaxAmount(BigDecimal.valueOf(clientOpportunity.getPropertyTaxes()));
			return propertyTax;
		}
		return null;
	}


	/** 
	 * No default value. If <code>crmLivingInProperty == null</code>, this method 
	 * would return null. For value references, see BDIRequest.xsd line 2789.  
	 * @see MorwebCall MorwebCall line 568. 
	 */
	public static final String createOccupancyPurpose(final String crmLivingInProperty) {
		if (isNullOrEmpty(crmLivingInProperty))
			return null;

		final String value = crmLivingInProperty.toLowerCase();
		if (value.contains("owner") && value.contains("renter")) return "1";
		else if (value.contains("renter")) return "4";
		else if (value.contains("owner")) return "1";
		else return null;
	}


	/** @see MorwebCall MorwebCall line 544 */
	public static final SubjectPropertyOccupancyPartialOwnerOccupied createSubjectPropertyOccupancyPartialOwnerOccupied(final Opportunity clientOpportunity, final ObjectFactory of) {
		final String crmLivingInProperty = clientOpportunity.getLivingInProperty();
		if (isNullOrEmpty(crmLivingInProperty)) 
			return null;

		if (crmLivingInProperty.toLowerCase().contains("owner") && crmLivingInProperty.toLowerCase().contains("renter")) {
			final Double yearlyIncome = clientOpportunity.getMonthlyRentalIncome() == null ? 0d : clientOpportunity.getMonthlyRentalIncome() * 12;

			final RentalDetails rentalDetails = of.createRentalDetails();
			rentalDetails.setAnnualGrossRentalIncome(BigDecimal.valueOf(yearlyIncome));

			final SubjectPropertyOccupancyPartialOwnerOccupied ownerOccupied = of.createSubjectPropertyOccupancyPartialOwnerOccupied();
			ownerOccupied.setRentalDetails(rentalDetails);

			return ownerOccupied;
		}
		return null;
	}


	/** @see MorwebCall MorwebCall line 544 */
	public static final SubjectPropertyOccupancyOwnerOccupied createSubjectPropertyOccupancyOwnerOccupied(final Opportunity clientOpportunity, final ObjectFactory of) {
		final String crmLivingInProperty = clientOpportunity.getLivingInProperty();
		if (isNullOrEmpty(crmLivingInProperty))
			return null;
		return crmLivingInProperty.toLowerCase().contains("owner") ? of.createSubjectPropertyOccupancyOwnerOccupied() : null;
	}


	public static final SubjectPropertyAddressDTO createSubjectPropertyAddressDTO(final Opportunity clientOpportunity, final ObjectFactory of) {
		final TypeApplicationAddressCanada addressCanada = of.createTypeApplicationAddressCanada();
		addressCanada.setKey(clientOpportunity.getId().toString());
		addressCanada.setCityTown(clientOpportunity.getCity());
		addressCanada.setPostalCode(clientOpportunity.getPostalCode());
		addressCanada.setProvinceCode( createProvinceCode(clientOpportunity.getProvince()) );
		addressCanada.setCountryCode("1");

		final SubjectPropertyAddress address = of.createSubjectPropertyAddress();
		address.setRefkeyAddress(addressCanada.getKey());

		final SubjectPropertyAddressDTO dto = new SubjectPropertyAddressDTO(address, addressCanada);
		return dto;
	}


	/** @see MorwebCall MorwebCall line 597 */
	public static final String createPropertyType(final String crmPropertyStyle) {
		if (isNullOrEmpty(crmPropertyStyle)) 
			return "99";
		
		if (crmPropertyStyle.contains("1")) return "1";
		else if (crmPropertyStyle.contains("2")) return "2";
		else if (crmPropertyStyle.contains("3")) return "3";
		else if (crmPropertyStyle.contains("4")) return "4";
		else if (crmPropertyStyle.contains("5")) return "5";
		else if (crmPropertyStyle.contains("6")) return "6";
		else return "99";
	}


	/** @see MorwebCall MorwebCall line 632 */
	public static final String createPropertyDescriptionType(final String crmPropertyType) {
		if (isNullOrEmpty(crmPropertyType)) 
			return "99";

		if (crmPropertyType.contains("1")) return "1";
		else if (crmPropertyType.contains("2")) return "3";
		else if (crmPropertyType.contains("3")) return "5";
		else if (crmPropertyType.contains("4")) return "6";
		else if (crmPropertyType.contains("5")) return "7";
		else if (crmPropertyType.contains("6")) return "13";
		else if (crmPropertyType.contains("7")) return "99";
		else return "99";
	}


	/** @see MorwebCall MorwebCall line 654 */
	public static final String createHeatingType(final String crmHeating) {
		if (isNullOrEmpty(crmHeating))
			return "99";

		if (crmHeating.contains("1")) return "2";
		else if (crmHeating.contains("2")) return "1";
		else if (crmHeating.contains("3")) return "3";
		else if (crmHeating.contains("4")) return "99";
		return "99";
	}


	public static final String createWaterSupplyType(final String crmWater) {
		if (isNullOrEmpty(crmWater))
			return "99";

		if (crmWater.contains("1")) return "10";
		else if (crmWater.contains("2")) return "20";
		else if (crmWater.contains("3")) return "99";
		else return "99";
	}


	public static final String createWaterWasteType(final String crmSewage) {
		if (isNullOrEmpty(crmSewage)) 
			return "99";

		if (crmSewage.contains("1")) return "10";
		else if (crmSewage.contains("2")) return "20";
		else if (crmSewage.contains("3")) return "30";
		else if (crmSewage.contains("4")) return "99";
		else return "99";
	}


	public static final String createParkingType(final String garageSize, final String garageType) {
		if (garageType != null) {
			if (garageType.contains("1")) {
				if (garageSize != null) { // Attached
					if (garageSize.contains("1")) return "1";
					else if (garageSize.contains("2")) return "3";
					else if (garageSize.contains("3")) return "5";
					else if (garageSize.contains("4")) return "5";
				}
			} else if (garageType.contains("2")) {
				if (garageSize != null) { // Detached
					if (garageSize.contains("1")) return "2";
					else if (garageSize.contains("2")) return "4";
					else if (garageSize.contains("3")) return "6";
					else if (garageSize.contains("4")) return "6";
				}
			} else if (garageType.contains("3")) return "7";
		}
		return "7";
	}
}
