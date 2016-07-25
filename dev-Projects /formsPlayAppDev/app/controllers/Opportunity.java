package controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="crm_lead")

public class Opportunity{

	@Id 
	@GenericGenerator(name="kaugen" , strategy="increment")
	@GeneratedValue(generator="kaugen")
	@Column(name="id",unique = true)
	private int id;
	
	@Column(name="application_no")
	private String application_no;
	@Column(name="hr_department_id")
	private Integer	hr_department_id;
	@Column(name="application_date")
	private Date application_date;
 
	@Column(name="what_is_your_lending_goal")
	private String what_is_your_lending_goal;
	
	@Column(name="province")
	private String province;
	
	@Column(name="down_payment_amount") 
	private Double down_payment_amount;
	
	@Column(name="purchase_price")
	private Double purchase_price;
	
	@Column(name="bank_account")
	private Double bank_account;
	
	@Column(name="rrsp_amount")
	private Double rrsp_amount;
	@Column(name="stage_id")
	private Integer stage_id;
	
	@Column(name="type")
	private String type;
	
	@Column(name="active")
	private Boolean active;
	 
	@Column(name="borrowed_amount")
	private Double borrowed_amount;
	
	@Column(name="sale_of_existing_amount")
	private Double sale_of_existing_amount;

	@Column(name="gifted_amount")
	private Double gifted_amount;
	
	@Column(name="personal_cash_amount")
	private Double personal_cash_amount;
	
	@Column(name="existing_equity_amount")
	private Double existing_equity_amount;

	@Column(name="sweat_equity_amount")
	private Double sweat_equity_amount;
	
	@Column(name="monthly_rental_income")
	private Double monthly_rental_income;

	@Column(name="mls")
	private String mls;
	
	@Column(name="address")
	private String address;

	@Column(name="city")
	private String city;
	
	@Column(name="living_in_property")
	private Integer living_in_property;
	
	@Column(name="postal_code")
	private String postal_code;

	@Column(name="buy_new_vehicle")
	private String buy_new_vehicle;
	
	@Column(name="property_value")
	private Double property_value;
	
	@Column(name="heating")
	private String heating;
	
	@Column(name="water")
	private String water;

	@Column(name="property_type")
	private String property_type;

	@Column(name="property_style")
	private String property_style;

	@Column(name="garage_type")
	private String garage_type;
	
	@Column(name="garage_size")
	private String garage_size;
	
	@Column(name="square_footage")
	private Integer square_footage;

	@Column(name="sewage")
	private String sewage;
	
	@Column(name="desired_mortgage_type")
	private String desired_mortgage_type;
	
	@Column(name="desired_term")
	private String desired_term;
	
	@Column(name="desired_amortization")
	private Integer desired_amortization;
	
	@Column(name="income_decreased_worried")
	private String income_decreased_worried;
	
	@Column(name="future_family")
	private String future_family;
	
	@Column(name="lifestyle_change")
	private String lifestyle_change;
	
	@Column(name="financial_risk_taker")
	private String financial_risk_taker;
	
	@Column(name="property_less_then_5_years")
	private String property_less_then_5_years;
	
	@Column(name="job_5_years")
	private String job_5_years;
	
	@Column(name="opportunity_id")
	private Integer opportunity_id;
	
	@Column(name="ltv")
	private Float ltv;
	
	public Float getLtv() {
		return ltv;
	}

	public void setLtv(Float ltv) {
		this.ltv = ltv;
	}

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "opportunity_applicant_rel",  joinColumns = { 
			@JoinColumn(name = "opp_id", nullable = false, updatable = false) }, 
			inverseJoinColumns = { @JoinColumn(name = "app_id", 
					nullable = false, updatable = false) })
	private List<Applicant> applicants = new ArrayList<Applicant>();
	
	@Transient
	private String areUsingTouchScreenDevice;
	 
	/**-------------------- FIELDS THAT ARE NOT IN POSTGRESS DB ----------------------*/
	@Transient
	private String isAdditionalApplicantExist;
	
	@Transient
	private String propertyAddress;
	
	@Transient
	private String liveDetails;
	
	@Transient
	private int pogressStatus;
	@Transient
	private Double investment;
	@Transient
	private Double additional_amount;
	
	@Transient
	private String expireDate;
	
	@Transient
	private Forms forms = new Forms();
	@Transient
	private String referralSourceName;
	@Transient
	private String referralSourceEmail;
	@Transient
	private String mortgage_Brokerage_Disclosures_Id;
	
	@Column(name="user_id")
	private Integer user_id;
	
	
	@Column(name="create_uid")
	private Integer create_uid;
	
	@Transient
	private String role;
	
	/** Newly Added */ 
	@Transient
	private Set<String> additionalFunds;
	/** Page2Pur All CouchBase Data mapping Variable */
	@Transient
	private String mlsListed;
	
	/**Page1 All CouchBase data mapping variable*/
	@Transient
	private String lendingGoal;
	
	/**Page3 All CouchBase data mapping variable*/
	@Transient
	private String propertyType;
	@Transient
	private String propertyStyle;
	@Transient
	private String squareFootage;
	@Transient 
	private String propertyHeated;
	@Transient
	private String propertyWater;
	@Transient
	private String propertySewage;
	@Transient
	private String garageType;
	@Transient
	private String garageSize;
	
	/**page4 All couchbase data mapping variable*/
	@Transient
	private String desiredMortgageTypeCB;
	@Transient
	private String desiredTermCB;
	@Transient
	private String desiredAmortizationCB;
	@Transient
	private Integer amortizeYear;
		
	/** page5a all couchbase data mapping variable*/
	@Transient
	private String incomeDecreasedWorried;
	@Transient
	private String futureFamily;
	@Transient
	private String buyNewVehicle;
	@Transient
	private String lifestyleChange;
	@Transient
	private String financialRiskTaker;
		
	/**page5b all COUCHBASE data mapping variable*/
	@Transient
	private String propertyLessThen5Years;
	@Transient
	private String job5Years;
	@Transient
	private String incomeRaise;
	@Transient
	private String rentalProperty;
	
	@Transient
	private String otherArea;
	
	public String getOtherArea() {
		return otherArea;
	}

	public void setOtherArea(String otherArea) {
		this.otherArea = otherArea;
	}

	public String getPropertyLessThen5Years() {
		return propertyLessThen5Years;
	}

	public void setPropertyLessThen5Years(String propertyLessThen5Years) {
		this.propertyLessThen5Years = propertyLessThen5Years;
	}

	public String getJob5Years() {
		return job5Years;
	}

	public void setJob5Years(String job5Years) {
		this.job5Years = job5Years;
	}

	public String getIncomeRaise() {
		return incomeRaise;
	}

	public void setIncomeRaise(String incomeRaise) {
		this.incomeRaise = incomeRaise;
	}

	public String getRentalProperty() {
		return rentalProperty;
	}

	public void setRentalProperty(String rentalProperty) {
		this.rentalProperty = rentalProperty;
	}

	public String getIncomeDecreasedWorried() {
		return incomeDecreasedWorried;
	}

	public void setIncomeDecreasedWorried(String incomeDecreasedWorried) {
		this.incomeDecreasedWorried = incomeDecreasedWorried;
	}

	public String getFutureFamily() {
		return futureFamily;
	}

	public void setFutureFamily(String futureFamily) {
		this.futureFamily = futureFamily;
	}

	public String getBuyNewVehicle() {
		return buyNewVehicle;
	}

	public void setBuyNewVehicle(String buyNewVehicle) {
		this.buyNewVehicle = buyNewVehicle;
	}

	public String getLifestyleChange() {
		return lifestyleChange;
	}

	public void setLifestyleChange(String lifestyleChange) {
		this.lifestyleChange = lifestyleChange;
	}

	public String getFinancialRiskTaker() {
		return financialRiskTaker;
	}

	public void setFinancialRiskTaker(String financialRiskTaker) {
		this.financialRiskTaker = financialRiskTaker;
	}

	public String getDesiredMortgageTypeCB() {
		return desiredMortgageTypeCB;
	}

	public void setDesiredMortgageTypeCB(String desiredMortgageTypeCB) {
		this.desiredMortgageTypeCB = desiredMortgageTypeCB;
	}

	public String getDesiredTermCB() {
		return desiredTermCB;
	}

	public void setDesiredTermCB(String desiredTermCB) {
		this.desiredTermCB = desiredTermCB;
	}

	public String getDesiredAmortizationCB() {
		return desiredAmortizationCB;
	}

	public void setDesiredAmortizationCB(String desiredAmortizationCB) {
		this.desiredAmortizationCB = desiredAmortizationCB;
	}

	public Integer getAmortizeYear() {
		return amortizeYear;
	}

	public void setAmortizeYear(Integer amortizeYear) {
		this.amortizeYear = amortizeYear;
	}

	
	
	public String getPropertyAddress() {
		return propertyAddress;
	}

	public void setPropertyAddress(String propertyAddress) {
		this.propertyAddress = propertyAddress;
	}

	//NO-ARG CONSTRUCTOR
	public Opportunity() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWhat_is_your_lending_goal() {
		return what_is_your_lending_goal;
	}

	public void setWhat_is_your_lending_goal(String what_is_your_lending_goal) {
		this.what_is_your_lending_goal = what_is_your_lending_goal;
	}

	public String getIsAdditionalApplicantExist() {
		return isAdditionalApplicantExist;
	}

	public void setIsAdditionalApplicantExist(String isAdditionalApplicantExist) {
		this.isAdditionalApplicantExist = isAdditionalApplicantExist;
	}

	public List<Applicant> getApplicants() {
		return applicants;
	}

	public void setApplicants(List<Applicant> applicants) {
		this.applicants = applicants;
	}

	public int getPogressStatus() {
		return pogressStatus;
	}

	public void setPogressStatus(int pogressStatus) {
		this.pogressStatus = pogressStatus;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public Double getDown_payment_amount() {
		return down_payment_amount;
	}

	public void setDown_payment_amount(Double down_payment_amount) {
		this.down_payment_amount = down_payment_amount;
	}

	public Double getPurchase_price() {
		return purchase_price;
	}

	public void setPurchase_price(Double purchase_price) {
		this.purchase_price = purchase_price;
	}

	public Double getBank_account() {
		return bank_account;
	}

	public void setBank_account(Double bank_account) {
		this.bank_account = bank_account;
	}

	public Double getRrsp_amount() {
		return rrsp_amount;
	}

	public void setRrsp_amount(Double rrsp_amount) {
		this.rrsp_amount = rrsp_amount;
	}

	public Double getInvestment() {
		return investment;
	}

	public void setInvestment(Double investment) {
		this.investment = investment;
	}

	public Double getBorrowed_amount() {
		return borrowed_amount;
	}

	public void setBorrowed_amount(Double borrowed_amount) {
		this.borrowed_amount = borrowed_amount;
	}

	public Double getSale_of_existing_amount() {
		return sale_of_existing_amount;
	}

	public void setSale_of_existing_amount(Double sale_of_existing_amount) {
		this.sale_of_existing_amount = sale_of_existing_amount;
	}

	public Double getGifted_amount() {
		return gifted_amount;
	}

	public void setGifted_amount(Double gifted_amount) {
		this.gifted_amount = gifted_amount;
	}

	public Double getPersonal_cash_amount() {
		return personal_cash_amount;
	}

	public void setPersonal_cash_amount(Double personal_cash_amount) {
		this.personal_cash_amount = personal_cash_amount;
	}

	public Double getExisting_equity_amount() {
		return existing_equity_amount;
	}

	public void setExisting_equity_amount(Double existing_equity_amount) {
		this.existing_equity_amount = existing_equity_amount;
	}

	public Double getSweat_equity_amount() {
		return sweat_equity_amount;
	}

	public void setSweat_equity_amount(Double sweat_equity_amount) {
		this.sweat_equity_amount = sweat_equity_amount;
	}

	public Integer getLiving_in_property() {
		return living_in_property;
	}

	public void setLiving_in_property(Integer living_in_property) {
		this.living_in_property = living_in_property;
	}

	public Double getMonthly_rental_income() {
		return monthly_rental_income;
	}

	public void setMonthly_rental_income(Double monthly_rental_income) {
		this.monthly_rental_income = monthly_rental_income;
	}

	public String getMls() {
		return mls;
	}

	public void setMls(String mls) {
		this.mls = mls;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPostal_code() {
		return postal_code;
	}

	public void setPostal_code(String postal_code) {
		this.postal_code = postal_code;
	}

	public String getBuy_new_vehicle() {
		return buy_new_vehicle;
	}

	public void setBuy_new_vehicle(String buy_new_vehicle) {
		this.buy_new_vehicle = buy_new_vehicle;
	}

	public Double getProperty_value() {
		return property_value;
	}

	public void setProperty_value(Double property_value) {
		this.property_value = property_value;
	}

	public Double getAdditional_amount() {
		return additional_amount;
	}

	public void setAdditional_amount(Double additional_amount) {
		this.additional_amount = additional_amount;
	}

	public String getLiveDetails() {
		return liveDetails;
	}

	public void setLiveDetails(String liveDetails) {
		this.liveDetails = liveDetails;
	}

	public Forms getForms() {
		return forms;
	}

	public void setForms(Forms forms) {
		this.forms = forms;
	}

	public String getReferralSourceName() {
		return referralSourceName;
	}

	public void setReferralSourceName(String referralSourceName) {
		this.referralSourceName = referralSourceName;
	}

	public String getReferralSourceEmail() {
		return referralSourceEmail;
	}

	public void setReferralSourceEmail(String referralSourceEmail) {
		this.referralSourceEmail = referralSourceEmail;
	}

	public String getHeating() {
		return heating;
	}

	public void setHeating(String heating) {
		this.heating = heating;
	}

	public String getWater() {
		return water;
	}

	public void setWater(String water) {
		this.water = water;
	}

	public String getProperty_type() {
		return property_type;
	}

	public void setProperty_type(String property_type) {
		this.property_type = property_type;
	}

	public String getProperty_style() {
		return property_style;
	}

	public void setProperty_style(String property_style) {
		this.property_style = property_style;
	}

	public String getGarage_type() {
		return garage_type;
	}

	public void setGarage_type(String garage_type) {
		this.garage_type = garage_type;
	}

	public String getGarage_size() {
		return garage_size;
	}

	public void setGarage_size(String garage_size) {
		this.garage_size = garage_size;
	}

	public Integer getSquare_footage() {
		return square_footage;
	}

	public void setSquare_footage(Integer square_footage) {
		this.square_footage = square_footage;
	}

	public String getDesired_mortgage_type() {
		return desired_mortgage_type;
	}

	public void setDesired_mortgage_type(String desired_mortgage_type) {
		this.desired_mortgage_type = desired_mortgage_type;
	}

	public String getDesired_term() {
		return desired_term;
	}

	public void setDesired_term(String desired_term) {
		this.desired_term = desired_term;
	}

	public Integer getDesired_amortization() {
		return desired_amortization;
	}

	public void setDesired_amortization(Integer desired_amortization) {
		this.desired_amortization = desired_amortization;
	}

	public String getIncome_decreased_worried() {
		return income_decreased_worried;
	}

	public void setIncome_decreased_worried(String income_decreased_worried) {
		this.income_decreased_worried = income_decreased_worried;
	}

	public String getFuture_family() {
		return future_family;
	}

	public void setFuture_family(String future_family) {
		this.future_family = future_family;
	}

	public String getLifestyle_change() {
		return lifestyle_change;
	}

	public void setLifestyle_change(String lifestyle_change) {
		this.lifestyle_change = lifestyle_change;
	}

	public String getFinancial_risk_taker() {
		return financial_risk_taker;
	}

	public void setFinancial_risk_taker(String financial_risk_taker) {
		this.financial_risk_taker = financial_risk_taker;
	}

	public String getProperty_less_then_5_years() {
		return property_less_then_5_years;
	}

	public void setProperty_less_then_5_years(String property_less_then_5_years) {
		this.property_less_then_5_years = property_less_then_5_years;
	}

	public String getJob_5_years() {
		return job_5_years;
	}

	public void setJob_5_years(String job_5_years) {
		this.job_5_years = job_5_years;
	}

	public String getMortgage_Brokerage_Disclosures_Id() {
		return mortgage_Brokerage_Disclosures_Id;
	}

	public void setMortgage_Brokerage_Disclosures_Id(
			String mortgage_Brokerage_Disclosures_Id) {
		this.mortgage_Brokerage_Disclosures_Id = mortgage_Brokerage_Disclosures_Id;
	}

	public String getSewage() {
		return sewage;
	}

	public void setSewage(String sewage) {
		this.sewage = sewage;
	}

	public Integer getOpportunity_id() {
		return opportunity_id;
	}

	public void setOpportunity_id(Integer opportunity_id) {
		this.opportunity_id = opportunity_id;
	}

	public Set<String> getAdditionalFunds() {
		return additionalFunds;
	}

	public void setAdditionalFunds(Set<String> additionalFunds) {
		this.additionalFunds = additionalFunds;
	}

	public String getLendingGoal() {
		return lendingGoal;
	}

	public void setLendingGoal(String lendingGoal) {
		this.lendingGoal = lendingGoal;
	}

	public String getMlsListed() {
		return mlsListed;
	}

	public void setMlsListed(String mlsListed) {
		this.mlsListed = mlsListed;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public String getPropertyStyle() {
		return propertyStyle;
	}

	public void setPropertyStyle(String propertyStyle) {
		this.propertyStyle = propertyStyle;
	}

	public String getSquareFootage() {
		return squareFootage;
	}

	public void setSquareFootage(String squareFootage) {
		this.squareFootage = squareFootage;
	}

	public String getPropertyHeated() {
		return propertyHeated;
	}

	public void setPropertyHeated(String propertyHeated) {
		this.propertyHeated = propertyHeated;
	}

	public String getPropertyWater() {
		return propertyWater;
	}

	public void setPropertyWater(String propertyWater) {
		this.propertyWater = propertyWater;
	}

	public String getPropertySewage() {
		return propertySewage;
	}

	public void setPropertySewage(String propertySewage) {
		this.propertySewage = propertySewage;
	}

	public String getGarageType() {
		return garageType;
	}

	public void setGarageType(String garageType) {
		this.garageType = garageType;
	}

	public String getGarageSize() {
		return garageSize;
	}

	public void setGarageSize(String garageSize) {
		this.garageSize = garageSize;
	}

	public String getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public String getApplication_no() {
		return application_no;
	}

	public void setApplication_no(String application_no) {
		this.application_no = application_no;
	}

	 

	public Date getApplication_date() {
		return application_date;
	}

	public void setApplication_date(Date application_date) {
		this.application_date = application_date;
	}
	
	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getStage_id() {
		return stage_id;
	}

	public void setStage_id(Integer stage_id) {
		this.stage_id = stage_id;
	}
	
	public String getAreUsingTouchScreenDevice() {
		return areUsingTouchScreenDevice;
	}

	public void setAreUsingTouchScreenDevice(String areUsingTouchScreenDevice) {
		this.areUsingTouchScreenDevice = areUsingTouchScreenDevice;
	}
	
	@Override
	public String toString() {
		return "Opportunity [id=" + id + ", application_no=" + application_no
				+ ", hr_department_id=" + getHr_department_id()
				+ ", application_date=" + application_date
				+ ", what_is_your_lending_goal=" + what_is_your_lending_goal
				+ ", province=" + province + ", down_payment_amount="
				+ down_payment_amount + ", purchase_price=" + purchase_price
				+ ", bank_account=" + bank_account + ", rrsp_amount="
				+ rrsp_amount + ", stage_id=" + stage_id + ", type=" + type
				+ ", active=" + active + ", borrowed_amount=" + borrowed_amount
				+ ", sale_of_existing_amount=" + sale_of_existing_amount
				+ ", gifted_amount=" + gifted_amount
				+ ", personal_cash_amount=" + personal_cash_amount
				+ ", existing_equity_amount=" + existing_equity_amount
				+ ", sweat_equity_amount=" + sweat_equity_amount
				+ ", monthly_rental_income=" + monthly_rental_income + ", mls="
				+ mls + ", address=" + address + ", city=" + city
				+ ", living_in_property=" + living_in_property
				+ ", postal_code=" + postal_code + ", buy_new_vehicle="
				+ buy_new_vehicle + ", property_value=" + property_value
				+ ", heating=" + heating + ", water=" + water
				+ ", property_type=" + property_type + ", property_style="
				+ property_style + ", garage_type=" + garage_type
				+ ", garage_size=" + garage_size + ", square_footage="
				+ square_footage + ", sewage=" + sewage
				+ ", desired_mortgage_type=" + desired_mortgage_type
				+ ", desired_term=" + desired_term + ", desired_amortization="
				+ desired_amortization + ", income_decreased_worried="
				+ income_decreased_worried + ", future_family=" + future_family
				+ ", lifestyle_change=" + lifestyle_change
				+ ", financial_risk_taker=" + financial_risk_taker
				+ ", property_less_then_5_years=" + property_less_then_5_years
				+ ", job_5_years=" + job_5_years + ", opportunity_id="
				+ opportunity_id + ", applicants=" + applicants
				+ ", areUsingTouchScreenDevice=" + areUsingTouchScreenDevice
				+ ", isAdditionalApplicantExist=" + isAdditionalApplicantExist
				+ ", liveDetails=" + liveDetails + ", pogressStatus="
				+ pogressStatus + ", investment=" + investment
				+ ", additional_amount=" + additional_amount + ", expireDate="
				+ expireDate + ", forms=" + forms + ", referralSourceName="
				+ referralSourceName + ", referralSourceEmail="
				+ referralSourceEmail + ", mortgage_Brokerage_Disclosures_Id="
				+ mortgage_Brokerage_Disclosures_Id + ", user_id=" + getUser_id()
				+ ", create_uid=" + getCreate_uid() + ", role=" + role
				+ ", additionalFunds=" + additionalFunds + ", mlsListed="
				+ mlsListed + ", lendingGoal=" + lendingGoal
				+ ", propertyType=" + propertyType + ", propertyStyle="
				+ propertyStyle + ", squareFootage=" + squareFootage
				+ ", propertyHeated=" + propertyHeated + ", propertyWater="
				+ propertyWater + ", propertySewage=" + propertySewage
				+ ", garageType=" + garageType + ", garageSize=" + garageSize
				+ ", propertyAddress=" + propertyAddress + "]";
	}

	public Integer getHr_department_id() {
		return hr_department_id;
	}

	public void setHr_department_id(Integer hr_department_id) {
		this.hr_department_id = hr_department_id;
	}

	public Integer getCreate_uid() {
		return create_uid;
	}

	public void setCreate_uid(Integer create_uid) {
		this.create_uid = create_uid;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}
	
}