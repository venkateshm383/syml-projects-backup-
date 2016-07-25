package com.syml.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.syml.domain.BaseBean;

import javax.persistence.Column;

import com.syml.generator.domain.annotation.ERP;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.Entity;

import java.util.List;

import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.persistence.TemporalType;

import com.syml.generator.domain.annotation.ERP.OpenERPType;

import java.util.ArrayList;
/**
* mapping open erp object : applicant.record
* @author .x.m.
*
*/
@XmlTransient
@XmlRootElement
@Entity(name="applicant_record")
public class Applicant extends BaseBean {
	//total_rental_income
	@ERP( name = "total_rental_income" )
	public String totalRentalIncome;

	//crp_3_score
	@ERP( name = "crp_3_score" )
	public Integer crp3Score;

	//total_asset
	@ERP( name = "total_asset" )
	public Integer totalAsset;

	//bankruptcy_discharge_date
	@Temporal(TemporalType.TIMESTAMP)
	@ERP( name = "bankruptcy_discharge_date" )
	public Date bankruptcyDischargeDate;

	//applicant_name
	@ERP( name = "applicant_name" )
	public String applicantName;

	//monthly_support_payment
	@ERP( name = "monthly_support_payment" )
	public Double monthlySupportPayment;

	//immigration_date
	@ERP( name = "immigration_date" )
	public Date immigrationDate;

	//orderly_debt_payment
	@ERP( name = "orderly_debt_payment" )
	public Boolean orderlyDebtPayment;

	//primary
	public Boolean primary1;

	//consent_dateTime
	@ERP( name = "consent_dateTime" )
	public Date consentDateTime;

	//non_resident
	@ERP( name = "non_resident" )
	public Boolean nonResident;

	//date_time_bureau_obtained
	@ERP( name = "date_time_bureau_obtained" )
	public Date dateTimeBureauObtained;

	//signature
	public String signature;

	//work
	public String work;

	//email_personal
	@ERP( name = "email_personal" )
	public String emailPersonal;

	//ers_2_score
	@ERP( name = "ers_2_score" )
	public Integer ers2Score;

	//cell
	public String cell;

	//total_net_worth
	@ERP( name = "total_net_worth" )
	public String totalNetWorth;

	//bankruptcy
	public Boolean bankruptcy;

	//message_is_follower
	@ERP( name = "message_is_follower" )
	public Boolean messageIsFollower;

	//total_income
	@ERP( name = "total_income" )
	public Double totalIncome;

	//total_inquires
	@ERP( name = "total_inquires" )
	public Integer totalInquires;

	//home
	public String home;

	//applicant_last_name
	@ERP( name = "applicant_last_name" )
	public String applicantLastName;

	//credit_report
	@Column(length=9999)
	@ERP( name = "credit_report" )
	public String creditReport;

	//odp_discharge_date
	@Temporal(TemporalType.TIMESTAMP)
	@ERP( name = "odp_discharge_date" )
	public Date odpDischargeDate;

	//include_in_opportunity
	@ERP( name = "include_in_opportunity" )
	public Boolean includeInOpportunity;

	//monthlychildsupport
	public Double monthlychildsupport;

	//beacon_9_score
	@ERP( name = "beacon_9_score" )
	public Integer beacon9Score;

	//beacon_5_score
	@ERP( name = "beacon_5_score" )
	public Integer beacon5Score;

	//money
	public String money;

	//identity_attached
	@ERP( name = "identity_attached" )
	public Boolean identityAttached;

	//contact_record
	@ERP( name = "contact_record" )
	public String contactRecord;

	//ninqidx
	public Integer ninqidx;

	//total_employed_income
	@ERP( name = "total_employed_income" )
	public String totalEmployedIncome;

	//email_work
	@ERP( name = "email_work" )
	public String emailWork;

	//sin
	public String sin;

	//dob
	@Temporal(TemporalType.TIMESTAMP)
	public Date dob;

	//signature_ip
	@ERP( name = "signature_ip" )
	public String signatureIp;

	//passport
	public String passport;

	//total_other_income
	@ERP( name = "total_other_income" )
	public String totalOtherIncome;

	//message_unread
	@ERP( name = "message_unread" )
	public Boolean messageUnread;

	//message_summary
	@Column(length=9999)
	@ERP( name = "message_summary" )
	public String messageSummary;

	//total_self_employed
	@ERP( name = "total_self_employed" )
	public String totalSelfEmployed;

	/*********************************selection type******************************/
	//relationship_status
	@ERP( type = OpenERPType.selection, name = "relationship_status" )
	public String relationshipStatus;

	//best_number
	@ERP( type = OpenERPType.selection, name = "best_number" )
	public String bestNumber;

	/*********************************one2many type******************************/

	//many to one : mapping table : applicant_mortgage(com.syml.domain.Mortgage)-->applicant_id
	@ERP(name="mortgage", type = OpenERPType.one2many )
	@JsonIgnore
	@Transient
	public List<Integer> mortgageIds = new ArrayList<Integer>();
	
	@Transient
	public List<Mortgage> mortgages = new ArrayList<Mortgage>();

	//many to one : mapping table : income_employer(com.syml.domain.Income)-->applicant_id
	@ERP(name="incomes", type = OpenERPType.one2many )
	@JsonIgnore
	@Transient
	public List<Integer> incomeIds = new ArrayList<Integer>();
	
	@Transient
	@ERP(type = OpenERPType.ignore )
	public List<Income> incomes = new ArrayList<Income>();

	//many to one : mapping table : applicant_property(com.syml.domain.Property)-->applicant_id
	@ERP(name="properties", type = OpenERPType.one2many )
	@JsonIgnore
	@Transient
	public List<Integer> propertyIds = new ArrayList<Integer>();
	
	@Transient
	@ERP(type = OpenERPType.ignore )
	public List<Property> properties = new ArrayList<Property>();

	//many to one : mapping table : crm_asset(com.syml.domain.Asset)-->opportunity_id
	@ERP(name="asset_ids", type = OpenERPType.one2many )
	@JsonIgnore
	@Transient
	public List<Integer> assetIds = new ArrayList<Integer>();
	
	@Transient
	@ERP(type = OpenERPType.ignore )
	public List<Asset> assets = new ArrayList<Asset>();

	//many to one : mapping table : crm_asset(com.syml.domain.Asset)-->opportunity_id
	@ERP(name="address_ids", type = OpenERPType.one2many )
	@JsonIgnore
	@Transient
	public List<Integer> addressIds = new ArrayList<Integer>();

	@Transient
	@ERP(type = OpenERPType.ignore )
	public List<Address> addresses = new ArrayList<Address>();
		
	//many to one : mapping table : applicant_collection(com.syml.domain.Collection)-->applicant_id
	@ERP(name="collection", type = OpenERPType.one2many )
	@JsonIgnore
	@Transient
	public List<Integer> collectionIds = new ArrayList<Integer>();
	
	
	@Transient
	public List<Collection> collection = null;

	//many to one : mapping table : applicant_liabilities(com.syml.domain.Liability)-->applicant_id
	@ERP(name="liabilities", type = OpenERPType.one2many )
	@JsonIgnore
	@Transient
	public List<Integer> liabilityIds = new ArrayList<Integer>();
	
	

	
	// There are a few variables which are needed to hold totals for the applicant.  Some of these values may be sent back to OpenERP	
	public double totalApplicantIncome;
	public double totalApplicantLiabilities;
	public double totalApplicantLiabilityPayments;
	public double totalApplicantAssets;
	public double totalApplicantMortgages;
	public double totalApplicantMortgagePayments;
	public double totalApplicantCollections;
	public double totalApplicantPropertyTaxes;
	public double totalApplicantCondoFees;
	
	

	public List<Integer> getMortgageIds() {
		return mortgageIds;
	}

	public void setMortgageIds(List<Integer> mortgageIds) {
		this.mortgageIds = mortgageIds;
	}

	public List<Mortgage> getMortgages() {
		return mortgages;
	}

	public void setMortgages(List<Mortgage> mortgages) {
		this.mortgages = mortgages;
	}

	public List<Integer> getIncomeIds() {
		return incomeIds;
	}

	public void setIncomeIds(List<Integer> incomeIds) {
		this.incomeIds = incomeIds;
	}

	public List<Integer> getPropertyIds() {
		return propertyIds;
	}

	public void setPropertyIds(List<Integer> propertyIds) {
		this.propertyIds = propertyIds;
	}

	public List<Integer> getAssetIds() {
		return assetIds;
	}

	public void setAssetIds(List<Integer> assetIds) {
		this.assetIds = assetIds;
	}

	public List<Asset> getAssets() {
		return assets;
	}

	public void setAssets(List<Asset> assets) {
		this.assets = assets;
	}

	public List<Integer> getAddressIds() {
		return addressIds;
	}

	public void setAddressIds(List<Integer> addressIds) {
		this.addressIds = addressIds;
	}

	public List<Address> getAddresss() {
		return addresses;
	}

	public void setAddresss(List<Address> addresses) {
		this.addresses = addresses;
	}
	
	public List<Integer> getCollectionIds() {
		return collectionIds;
	}

	public void setCollectionIds(List<Integer> collectionIds) {
		this.collectionIds = collectionIds;
	}

	public List<Integer> getLiabilityIds() {
		return liabilityIds;
	}

	public void setLiabilityIds(List<Integer> liabilityIds) {
		this.liabilityIds = liabilityIds;
	}

	@Transient
	@ERP(type = OpenERPType.ignore )
	public List<Liability> liabilities = null;
	
	
	
	public List<Liability> getLiabilities() {
		return liabilities;
	}

	public void setLiabilities(List<Liability> liabilities) {
		this.liabilities = liabilities;
	}

	//many to one : mapping table : applicant_payment(com.syml.domain.LatePayment)-->applicant_id
	@ERP(name="late_payments", type = OpenERPType.one2many )
	@JsonIgnore
	@Transient
	public List<Integer> latePaymentIds = new ArrayList<Integer>();
	
	@Transient
	public List<LatePayment> latePayments = new ArrayList<LatePayment>();
	
	

	public List<Integer> getLatePaymentIds() {
		return latePaymentIds;
	}

	public void setLatePaymentIds(List<Integer> latePaymentIds) {
		this.latePaymentIds = latePaymentIds;
	}

	/*********************************many2one type******************************/
	
	/*@ERP(name="id", type = OpenERPType.ignore )
	@JsonIgnore
public Integer id;*/
	//many to one : mapping table : crm_lead(com.syml.domain.Opportunity)
	@ERP(name="applicant_id", type = OpenERPType.many2one )
	@JsonIgnore
	public Integer applicantId;
	

	/*public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}*/

	//many to one : mapping table : crm_lead(com.syml.domain.Opportunity)
	@ERP(name="opportunity", type = OpenERPType.many2one )
	@JsonIgnore
	public Integer opportunity;

	@Transient
	public Integer beaconScore;


	public void setApplicantId(Integer applicantId){
		this.applicantId = applicantId;
	}

	public Integer getApplicantId(){
		return this.applicantId;
	}

	public void setTotalRentalIncome(String totalRentalIncome){
		this.totalRentalIncome = totalRentalIncome;
	}

	public String getTotalRentalIncome(){
		return this.totalRentalIncome;
	}

	public void setCrp3Score(Integer crp3Score){
		this.crp3Score = crp3Score;
	}

	public Integer getCrp3Score(){
		return this.crp3Score;
	}

	public void setTotalAsset(Integer totalAsset){
		this.totalAsset = totalAsset;
	}

	public Integer getTotalAsset(){
		return this.totalAsset;
	}

	public void setBankruptcyDischargeDate(Date bankruptcyDischargeDate){
		this.bankruptcyDischargeDate = bankruptcyDischargeDate;
	}

	public Date getBankruptcyDischargeDate(){
		return this.bankruptcyDischargeDate;
	}

	public void setRelationshipStatus(String relationshipStatus){
		this.relationshipStatus = relationshipStatus;
	}

	public String getRelationshipStatus(){
		return this.relationshipStatus;
	}

	public void setApplicantName(String applicantName){
		this.applicantName = applicantName;
	}

	public String getApplicantName(){
		return this.applicantName;
	}

	public void setMonthlySupportPayment(Double monthlySupportPayment){
		this.monthlySupportPayment = monthlySupportPayment;
	}

	public Double getMonthlySupportPayment(){
		return this.monthlySupportPayment;
	}

	public void setImmigrationDate(Date immigrationDate){
		this.immigrationDate = immigrationDate;
	}

	public Date getImmigrationDate(){
		return this.immigrationDate;
	}

	public void setOrderlyDebtPayment(Boolean orderlyDebtPayment){
		this.orderlyDebtPayment = orderlyDebtPayment;
	}

	public Boolean getOrderlyDebtPayment(){
		return this.orderlyDebtPayment;
	}

	public void setPrimary1(Boolean primary1){
		this.primary1 = primary1;
	}

	public Boolean getPrimary1(){
		return this.primary1;
	}

	public void setBestNumber(String bestNumber){
		this.bestNumber = bestNumber;
	}

	public String getBestNumber(){
		return this.bestNumber;
	}

	public void setConsentDateTime(Date consentDateTime){
		this.consentDateTime = consentDateTime;
	}

	public Date getConsentDateTime(){
		return this.consentDateTime;
	}

	public void setNonResident(Boolean nonResident){
		this.nonResident = nonResident;
	}

	public Boolean getNonResident(){
		return this.nonResident;
	}

	public void setDateTimeBureauObtained(Date dateTimeBureauObtained){
		this.dateTimeBureauObtained = dateTimeBureauObtained;
	}

	public Date getDateTimeBureauObtained(){
		return this.dateTimeBureauObtained;
	}

	public void setSignature(String signature){
		this.signature = signature;
	}

	public String getSignature(){
		return this.signature;
	}

	public void setWork(String work){
		this.work = work;
	}

	public String getWork(){
		return this.work;
	}

	public void setEmailPersonal(String emailPersonal){
		this.emailPersonal = emailPersonal;
	}

	public String getEmailPersonal(){
		return this.emailPersonal;
	}

	public void setErs2Score(Integer ers2Score){
		this.ers2Score = ers2Score;
	}

	public Integer getErs2Score(){
		return this.ers2Score;
	}

	public void setCell(String cell){
		this.cell = cell;
	}

	public String getCell(){
		return this.cell;
	}

	public void setMortgage(List<Mortgage> mortgage){
		this.mortgages = mortgage;
	}

	public List<Mortgage> getMortgage(){
		return this.mortgages;
	}

	public void setIncomes(List<Income> incomes){
		this.incomes = incomes;
	}

	public List<Income> getIncomes(){
		return this.incomes;
	}

	public void setProperties(List<Property> properties){
		this.properties = properties;
	}

	public List<Property> getProperties(){
		return this.properties;
	}

	public void setTotalNetWorth(String totalNetWorth){
		this.totalNetWorth = totalNetWorth;
	}

	public String getTotalNetWorth(){
		return this.totalNetWorth;
	}

	public void setBankruptcy(Boolean bankruptcy){
		this.bankruptcy = bankruptcy;
	}

	public Boolean getBankruptcy(){
		return this.bankruptcy;
	}

	

	public void setMessageIsFollower(Boolean messageIsFollower){
		this.messageIsFollower = messageIsFollower;
	}

	public Boolean getMessageIsFollower(){
		return this.messageIsFollower;
	}

	public void setTotalIncome(Double totalIncome){
		this.totalIncome = totalIncome;
	}

	public Double getTotalIncome(){
		return this.totalIncome;
	}

	public void setTotalInquires(Integer totalInquires){
		this.totalInquires = totalInquires;
	}

	public Integer getTotalInquires(){
		return this.totalInquires;
	}

	public void setHome(String home){
		this.home = home;
	}

	public String getHome(){
		return this.home;
	}

	public void setApplicantLastName(String applicantLastName){
		this.applicantLastName = applicantLastName;
	}

	public String getApplicantLastName(){
		return this.applicantLastName;
	}

	public void setCreditReport(String creditReport){
		this.creditReport = creditReport;
	}

	public String getCreditReport(){
		return this.creditReport;
	}

	public void setOdpDischargeDate(Date odpDischargeDate){
		this.odpDischargeDate = odpDischargeDate;
	}

	public Date getOdpDischargeDate(){
		return this.odpDischargeDate;
	}

	public void setIncludeInOpportunity(Boolean includeInOpportunity){
		this.includeInOpportunity = includeInOpportunity;
	}

	public Boolean getIncludeInOpportunity(){
		return this.includeInOpportunity;
	}

	public void setMonthlychildsupport(Double monthlychildsupport){
		this.monthlychildsupport = monthlychildsupport;
	}

	public Double getMonthlychildsupport(){
		return this.monthlychildsupport;
	}

	public void setBeacon9Score(Integer beacon9Score){
		this.beacon9Score = beacon9Score;
	}

	public Integer getBeacon9Score(){
		return this.beacon9Score;
	}

	public void setCollection(List<Collection> collection){
		this.collection = collection;
	}

	public List<Collection> getCollection(){
		return this.collection;
	}

	public void setBeacon5Score(Integer beacon5Score){
		this.beacon5Score = beacon5Score;
	}

	public Integer getBeacon5Score(){
		return this.beacon5Score;
	}

	public void setMoney(String money){
		this.money = money;
	}

	public String getMoney(){
		return this.money;
	}

	public void setIdentityAttached(Boolean identityAttached){
		this.identityAttached = identityAttached;
	}

	public Boolean getIdentityAttached(){
		return this.identityAttached;
	}

	public void setContactRecord(String contactRecord){
		this.contactRecord = contactRecord;
	}

	public String getContactRecord(){
		return this.contactRecord;
	}

	public void setNinqidx(Integer ninqidx){
		this.ninqidx = ninqidx;
	}

	public Integer getNinqidx(){
		return this.ninqidx;
	}

	public void setTotalEmployedIncome(String totalEmployedIncome){
		this.totalEmployedIncome = totalEmployedIncome;
	}

	public String getTotalEmployedIncome(){
		return this.totalEmployedIncome;
	}

	

	public void setOpportunity(Integer opportunity){
		this.opportunity = opportunity;
	}

	public Integer getOpportunity(){
		return this.opportunity;
	}

	public void setLatePayments(List<LatePayment> latePayments){
		this.latePayments = latePayments;
	}

	public List<LatePayment> getLatePayments(){
		return this.latePayments;
	}

	public void setEmailWork(String emailWork){
		this.emailWork = emailWork;
	}

	public String getEmailWork(){
		return this.emailWork;
	}

	public void setSin(String sin){
		this.sin = sin;
	}

	public String getSin(){
		return this.sin;
	}

	public void setDob(Date dob){
		this.dob = dob;
	}

	public Date getDob(){
		return this.dob;
	}

	public void setSignatureIp(String signatureIp){
		this.signatureIp = signatureIp;
	}

	public String getSignatureIp(){
		return this.signatureIp;
	}

	public void setPassport(String passport){
		this.passport = passport;
	}

	public String getPassport(){
		return this.passport;
	}

	public void setTotalOtherIncome(String totalOtherIncome){
		this.totalOtherIncome = totalOtherIncome;
	}

	public String getTotalOtherIncome(){
		return this.totalOtherIncome;
	}

	public void setMessageUnread(Boolean messageUnread){
		this.messageUnread = messageUnread;
	}

	public Boolean getMessageUnread(){
		return this.messageUnread;
	}

	public void setMessageSummary(String messageSummary){
		this.messageSummary = messageSummary;
	}

	public String getMessageSummary(){
		return this.messageSummary;
	}

	public void setTotalSelfEmployed(String totalSelfEmployed){
		this.totalSelfEmployed = totalSelfEmployed;
	}

	public String getTotalSelfEmployed(){
		return this.totalSelfEmployed;
	}

	@Override
	public String toString() {
		return "Applicant [applicantID="+this.getId()+", applicantName=" + applicantName 
				+ ", opportunityID=" + applicantId + "]\n";
	}

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

}