package com.syml.test.junit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class Applicant {
	
	
	public String applicantId;
	public String getApplicantId() {
		return applicantId;
	}
	public void setApplicantId(String applicantId) {
		this.applicantId = applicantId;
	}
		//total_rental_income
		//@ERP( name = "total_rental_income" )
		public String totalRentalIncome;

		//crp_3_score
		//@ERP( name = "crp_3_score" )
		public Integer crp3Score;

		//total_asset
		//@ERP( name = "total_asset" )
		public Integer totalAsset;

		//bankruptcy_discharge_date
		//@Temporal(TemporalType.TIMESTAMP)
		//@ERP( name = "bankruptcy_discharge_date" )
		public Date bankruptcyDischargeDate;

		//applicant_name
		//@ERP( name = "applicant_name" )
		public String applicantName;

		//monthly_support_payment
		//@ERP( name = "monthly_support_payment" )
		public Double monthlySupportPayment;

		//immigration_date
		//@ERP( name = "immigration_date" )
		public Date immigrationDate;

		//orderly_debt_payment
		//@ERP( name = "orderly_debt_payment" )
		public Boolean orderlyDebtPayment;

		//primary
		public Boolean primary1;

		//consent_dateTime
		//@ERP( name = "consent_dateTime" )
		public Date consentDateTime;

		//non_resident
		//@ERP( name = "non_resident" )
		public Boolean nonResident;

		//date_time_bureau_obtained
		//@ERP( name = "date_time_bureau_obtained" )
		public Date dateTimeBureauObtained;

		//signature
		public String signature;

		//work
		public String work;

		//email_personal
		//@ERP( name = "email_personal" )
		public String emailPersonal;

		//ers_2_score
		//@ERP( name = "ers_2_score" )
		public Integer ers2Score;

		//cell
		public String cell;

		//total_net_worth
		//@ERP( name = "total_net_worth" )
		public String totalNetWorth;

		//bankruptcy
		public Boolean bankruptcy;

		//message_is_follower
		//@ERP( name = "message_is_follower" )
		public Boolean messageIsFollower;

		//total_income
		//@ERP( name = "total_income" )
		public Double totalIncome;

		//total_inquires
		//@ERP( name = "total_inquires" )
		public Integer totalInquires;

		//home
		public String home;

		//applicant_last_name
		//@ERP( name = "applicant_last_name" )
		public String applicantLastName;

		//credit_report
		//@Column(length=9999)
		//@ERP( name = "credit_report" )
		public String creditReport;

		//odp_discharge_date
		//@Temporal(TemporalType.TIMESTAMP)
		//@ERP( name = "odp_discharge_date" )
		public Date odpDischargeDate;

		//include_in_opportunity
		//@ERP( name = "include_in_opportunity" )
		public Boolean includeInOpportunity;

		//monthlychildsupport
		public Double monthlychildsupport;

		//beacon_9_score
		//@ERP( name = "beacon_9_score" )
		public Integer beacon9Score;

		//beacon_5_score
		//@ERP( name = "beacon_5_score" )
		public Integer beacon5Score;

		//money
		public String money;

		//identity_attached
		//@ERP( name = "identity_attached" )
		public Boolean identityAttached;

		//contact_record
		//@ERP( name = "contact_record" )
		public String contactRecord;

		//ninqidx
		public Integer ninqidx;

		//total_employed_income
		//@ERP( name = "total_employed_income" )
		public String totalEmployedIncome;

		//email_work
		//@ERP( name = "email_work" )
		public String emailWork;

		//sin
		public String sin;

		//dob
		//@Temporal(TemporalType.TIMESTAMP)
		public Date dob;

		//signature_ip
		//@ERP( name = "signature_ip" )
		public String signatureIp;

		//passport
		public String passport;

		//total_other_income
		//@ERP( name = "total_other_income" )
		public String totalOtherIncome;

		//message_unread
		//@ERP( name = "message_unread" )
		public Boolean messageUnread;

		//message_summary
		//@Column(length=9999)
		//@ERP( name = "message_summary" )
		public String messageSummary;

		//total_self_employed
		//@ERP( name = "total_self_employed" )
		public String totalSelfEmployed;

		/*********************************selection type******************************/
		//relationship_status
		//@ERP( type = OpenERPType.selection, name = "relationship_status" )
		public String relationshipStatus;

		//best_number
		//@ERP( type = OpenERPType.selection, name = "best_number" )
		public String bestNumber;

		/*********************************one2many type******************************/

		//many to one : mapping table : applicant_mortgage(com.syml.domain.Mortgage)-->applicant_id
		//@ERP(name="mortgage", type = OpenERPType.one2many )
		//@JsonIgnore
		//@Transient
		public List<Integer> mortgageIds = new ArrayList<Integer>();
		
		//@Transient
		//public List<Mortgage> mortgages = new ArrayList<Mortgage>();

		//many to one : mapping table : income_employer(com.syml.domain.Income)-->applicant_id
		//@ERP(name="incomes", type = OpenERPType.one2many )
		//@JsonIgnore
		//@Transient
		public List<Integer> incomeIds = new ArrayList<Integer>();
		
		//@Transient
		//@ERP(type = OpenERPType.ignore )
		public List<Income> incomes = new ArrayList<Income>();

		//many to one : mapping table : applicant_property(com.syml.domain.Property)-->applicant_id
		//@ERP(name="properties", type = OpenERPType.one2many )
		//@JsonIgnore
		//@Transient
		public List<Integer> propertyIds = new ArrayList<Integer>();
		
		//@Transient
		//@ERP(type = OpenERPType.ignore )
		public List<Property> properties = new ArrayList<Property>();

		//many to one : mapping table : crm_asset(com.syml.domain.Asset)-->opportunity_id
		//@ERP(name="asset_ids", type = OpenERPType.one2many )
		//@JsonIgnore
		//@Transient
		public List<Integer> assetIds = new ArrayList<Integer>();
		
		//@Transient
		//@ERP(type = OpenERPType.ignore )
	//	public List<Asset> assets = new ArrayList<Asset>();

		//many to one : mapping table : applicant_collection(com.syml.domain.Collection)-->applicant_id
		//@ERP(name="collection", type = OpenERPType.one2many )
		//@JsonIgnore
		//@Transient
		public List<Integer> collectionIds = new ArrayList<Integer>();
		
		
		//@Transient
		public List<Collection> collection = null;

		//many to one : mapping table : applicant_liabilities(com.syml.domain.Liability)-->applicant_id
		//@ERP(name="liabilities", type = OpenERPType.one2many )
		//@JsonIgnore
		//@Transient
		public List<Integer> liabilityIds = new ArrayList<Integer>();
		
		
		// There are a few variables which are needed to hold totals for the applicant.  Some of these values may be sent back to OpenERP	
		public double totalApplicantIncome;
		public double totalApplicantLiabilities;
		public String getTotalRentalIncome() {
			return totalRentalIncome;
		}
		public void setTotalRentalIncome(String totalRentalIncome) {
			this.totalRentalIncome = totalRentalIncome;
		}
		public Integer getCrp3Score() {
			return crp3Score;
		}
		public void setCrp3Score(Integer crp3Score) {
			this.crp3Score = crp3Score;
		}
		public Integer getTotalAsset() {
			return totalAsset;
		}
		public void setTotalAsset(Integer totalAsset) {
			this.totalAsset = totalAsset;
		}
		public Date getBankruptcyDischargeDate() {
			return bankruptcyDischargeDate;
		}
		public void setBankruptcyDischargeDate(Date bankruptcyDischargeDate) {
			this.bankruptcyDischargeDate = bankruptcyDischargeDate;
		}
		public String getApplicantName() {
			return applicantName;
		}
		public void setApplicantName(String applicantName) {
			this.applicantName = applicantName;
		}
		public Double getMonthlySupportPayment() {
			return monthlySupportPayment;
		}
		public void setMonthlySupportPayment(Double monthlySupportPayment) {
			this.monthlySupportPayment = monthlySupportPayment;
		}
		public Date getImmigrationDate() {
			return immigrationDate;
		}
		public void setImmigrationDate(Date immigrationDate) {
			this.immigrationDate = immigrationDate;
		}
		public Boolean getOrderlyDebtPayment() {
			return orderlyDebtPayment;
		}
		public void setOrderlyDebtPayment(Boolean orderlyDebtPayment) {
			this.orderlyDebtPayment = orderlyDebtPayment;
		}
		public Boolean getPrimary1() {
			return primary1;
		}
		public void setPrimary1(Boolean primary1) {
			this.primary1 = primary1;
		}
		public Date getConsentDateTime() {
			return consentDateTime;
		}
		public void setConsentDateTime(Date consentDateTime) {
			this.consentDateTime = consentDateTime;
		}
		public Boolean getNonResident() {
			return nonResident;
		}
		public void setNonResident(Boolean nonResident) {
			this.nonResident = nonResident;
		}
		public Date getDateTimeBureauObtained() {
			return dateTimeBureauObtained;
		}
		public void setDateTimeBureauObtained(Date dateTimeBureauObtained) {
			this.dateTimeBureauObtained = dateTimeBureauObtained;
		}
		public String getSignature() {
			return signature;
		}
		public void setSignature(String signature) {
			this.signature = signature;
		}
		public String getWork() {
			return work;
		}
		public void setWork(String work) {
			this.work = work;
		}
		public String getEmailPersonal() {
			return emailPersonal;
		}
		public void setEmailPersonal(String emailPersonal) {
			this.emailPersonal = emailPersonal;
		}
		public Integer getErs2Score() {
			return ers2Score;
		}
		public void setErs2Score(Integer ers2Score) {
			this.ers2Score = ers2Score;
		}
		public String getCell() {
			return cell;
		}
		public void setCell(String cell) {
			this.cell = cell;
		}
		public String getTotalNetWorth() {
			return totalNetWorth;
		}
		public void setTotalNetWorth(String totalNetWorth) {
			this.totalNetWorth = totalNetWorth;
		}
		public Boolean getBankruptcy() {
			return bankruptcy;
		}
		public void setBankruptcy(Boolean bankruptcy) {
			this.bankruptcy = bankruptcy;
		}
		public Boolean getMessageIsFollower() {
			return messageIsFollower;
		}
		public void setMessageIsFollower(Boolean messageIsFollower) {
			this.messageIsFollower = messageIsFollower;
		}
		public Double getTotalIncome() {
			return totalIncome;
		}
		public void setTotalIncome(Double totalIncome) {
			this.totalIncome = totalIncome;
		}
		public Integer getTotalInquires() {
			return totalInquires;
		}
		public void setTotalInquires(Integer totalInquires) {
			this.totalInquires = totalInquires;
		}
		public String getHome() {
			return home;
		}
		public void setHome(String home) {
			this.home = home;
		}
		public String getApplicantLastName() {
			return applicantLastName;
		}
		public void setApplicantLastName(String applicantLastName) {
			this.applicantLastName = applicantLastName;
		}
		public String getCreditReport() {
			return creditReport;
		}
		public void setCreditReport(String creditReport) {
			this.creditReport = creditReport;
		}
		public Date getOdpDischargeDate() {
			return odpDischargeDate;
		}
		public void setOdpDischargeDate(Date odpDischargeDate) {
			this.odpDischargeDate = odpDischargeDate;
		}
		public Boolean getIncludeInOpportunity() {
			return includeInOpportunity;
		}
		public void setIncludeInOpportunity(Boolean includeInOpportunity) {
			this.includeInOpportunity = includeInOpportunity;
		}
		public Double getMonthlychildsupport() {
			return monthlychildsupport;
		}
		public void setMonthlychildsupport(Double monthlychildsupport) {
			this.monthlychildsupport = monthlychildsupport;
		}
		public Integer getBeacon9Score() {
			return beacon9Score;
		}
		public void setBeacon9Score(Integer beacon9Score) {
			this.beacon9Score = beacon9Score;
		}
		public Integer getBeacon5Score() {
			return beacon5Score;
		}
		public void setBeacon5Score(Integer beacon5Score) {
			this.beacon5Score = beacon5Score;
		}
		public String getMoney() {
			return money;
		}
		public void setMoney(String money) {
			this.money = money;
		}
		public Boolean getIdentityAttached() {
			return identityAttached;
		}
		public void setIdentityAttached(Boolean identityAttached) {
			this.identityAttached = identityAttached;
		}
		public String getContactRecord() {
			return contactRecord;
		}
		public void setContactRecord(String contactRecord) {
			this.contactRecord = contactRecord;
		}
		public Integer getNinqidx() {
			return ninqidx;
		}
		public void setNinqidx(Integer ninqidx) {
			this.ninqidx = ninqidx;
		}
		public String getTotalEmployedIncome() {
			return totalEmployedIncome;
		}
		public void setTotalEmployedIncome(String totalEmployedIncome) {
			this.totalEmployedIncome = totalEmployedIncome;
		}
		public String getEmailWork() {
			return emailWork;
		}
		public void setEmailWork(String emailWork) {
			this.emailWork = emailWork;
		}
		public String getSin() {
			return sin;
		}
		public void setSin(String sin) {
			this.sin = sin;
		}
		public Date getDob() {
			return dob;
		}
		public void setDob(Date dob) {
			this.dob = dob;
		}
		public String getSignatureIp() {
			return signatureIp;
		}
		public void setSignatureIp(String signatureIp) {
			this.signatureIp = signatureIp;
		}
		public String getPassport() {
			return passport;
		}
		public void setPassport(String passport) {
			this.passport = passport;
		}
		public String getTotalOtherIncome() {
			return totalOtherIncome;
		}
		public void setTotalOtherIncome(String totalOtherIncome) {
			this.totalOtherIncome = totalOtherIncome;
		}
		public Boolean getMessageUnread() {
			return messageUnread;
		}
		public void setMessageUnread(Boolean messageUnread) {
			this.messageUnread = messageUnread;
		}
		public String getMessageSummary() {
			return messageSummary;
		}
		public void setMessageSummary(String messageSummary) {
			this.messageSummary = messageSummary;
		}
		public String getTotalSelfEmployed() {
			return totalSelfEmployed;
		}
		public void setTotalSelfEmployed(String totalSelfEmployed) {
			this.totalSelfEmployed = totalSelfEmployed;
		}
		public String getRelationshipStatus() {
			return relationshipStatus;
		}
		public void setRelationshipStatus(String relationshipStatus) {
			this.relationshipStatus = relationshipStatus;
		}
		public String getBestNumber() {
			return bestNumber;
		}
		public void setBestNumber(String bestNumber) {
			this.bestNumber = bestNumber;
		}
		public List<Integer> getMortgageIds() {
			return mortgageIds;
		}
		public void setMortgageIds(List<Integer> mortgageIds) {
			this.mortgageIds = mortgageIds;
		}
		
		public List<Integer> getIncomeIds() {
			return incomeIds;
		}
		public void setIncomeIds(List<Integer> incomeIds) {
			this.incomeIds = incomeIds;
		}
		public List<Income> getIncomes() {
			return incomes;
		}
		public void setIncomes(List<Income> incomes) {
			this.incomes = incomes;
		}
		public List<Integer> getPropertyIds() {
			return propertyIds;
		}
		public void setPropertyIds(List<Integer> propertyIds) {
			this.propertyIds = propertyIds;
		}
		public List<Property> getProperties() {
			return properties;
		}
		public void setProperties(List<Property> properties) {
			this.properties = properties;
		}
		public List<Integer> getAssetIds() {
			return assetIds;
		}
		public void setAssetIds(List<Integer> assetIds) {
			this.assetIds = assetIds;
		}
		
		public List<Integer> getCollectionIds() {
			return collectionIds;
		}
		public void setCollectionIds(List<Integer> collectionIds) {
			this.collectionIds = collectionIds;
		}
		public List<Collection> getCollection() {
			return collection;
		}
		public void setCollection(List<Collection> collection) {
			this.collection = collection;
		}
		public List<Integer> getLiabilityIds() {
			return liabilityIds;
		}
		public void setLiabilityIds(List<Integer> liabilityIds) {
			this.liabilityIds = liabilityIds;
		}
		public double getTotalApplicantIncome() {
			return totalApplicantIncome;
		}
		public void setTotalApplicantIncome(double totalApplicantIncome) {
			this.totalApplicantIncome = totalApplicantIncome;
		}
		public double getTotalApplicantLiabilities() {
			return totalApplicantLiabilities;
		}
		public void setTotalApplicantLiabilities(double totalApplicantLiabilities) {
			this.totalApplicantLiabilities = totalApplicantLiabilities;
		}
		public double getTotalApplicantLiabilityPayments() {
			return totalApplicantLiabilityPayments;
		}
		public void setTotalApplicantLiabilityPayments(
				double totalApplicantLiabilityPayments) {
			this.totalApplicantLiabilityPayments = totalApplicantLiabilityPayments;
		}
		public double getTotalApplicantAssets() {
			return totalApplicantAssets;
		}
		public void setTotalApplicantAssets(double totalApplicantAssets) {
			this.totalApplicantAssets = totalApplicantAssets;
		}
		public double getTotalApplicantMortgages() {
			return totalApplicantMortgages;
		}
		public void setTotalApplicantMortgages(double totalApplicantMortgages) {
			this.totalApplicantMortgages = totalApplicantMortgages;
		}
		public double getTotalApplicantMortgagePayments() {
			return totalApplicantMortgagePayments;
		}
		public void setTotalApplicantMortgagePayments(
				double totalApplicantMortgagePayments) {
			this.totalApplicantMortgagePayments = totalApplicantMortgagePayments;
		}
		public double getTotalApplicantCollections() {
			return totalApplicantCollections;
		}
		public void setTotalApplicantCollections(double totalApplicantCollections) {
			this.totalApplicantCollections = totalApplicantCollections;
		}
		public double getTotalApplicantPropertyTaxes() {
			return totalApplicantPropertyTaxes;
		}
		public void setTotalApplicantPropertyTaxes(double totalApplicantPropertyTaxes) {
			this.totalApplicantPropertyTaxes = totalApplicantPropertyTaxes;
		}
		public double getTotalApplicantCondoFees() {
			return totalApplicantCondoFees;
		}
		public void setTotalApplicantCondoFees(double totalApplicantCondoFees) {
			this.totalApplicantCondoFees = totalApplicantCondoFees;
		}
		public double totalApplicantLiabilityPayments;
		public double totalApplicantAssets;
		public double totalApplicantMortgages;
		public double totalApplicantMortgagePayments;
		public double totalApplicantCollections;
		public double totalApplicantPropertyTaxes;
		public double totalApplicantCondoFees;

}
