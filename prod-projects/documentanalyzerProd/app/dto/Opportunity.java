package dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Opportunity {
	
	
	
	public int id;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}





	//monthly_rental_income
	private Double monthlyRentalIncome;

	//GDS
	private Double GDS;

	//isUpdatedToUA
	private Boolean isUpdatedToUA;

	//broker_fee
	private Double brokerFee;

	//insurerref
	private String insurerref;

	//spouse
	private String spouse;

	//is_agricultural_less_10_acres
	private Boolean isAgriculturalLess10Acres;

	//email_from
	private String emailFrom;

	//is_life_leased_property
	private Boolean isLifeLeasedProperty;

	//mortgage_insured
	private Boolean mortgageInsured;

	//date_action_last
	private Date dateActionLast;

	//is_mobile_homes
	private Boolean isMobileHomes;

	//is_age_restricted
	private Boolean isAgeRestricted;

	//monthly_payment
	private Double monthlyPayment;

	//secondary_financing_amount
	private Double secondaryFinancingAmount;

	//is_agricultural
	private Boolean isAgricultural;

	//is_rental_property
	private Boolean isRentalProperty;

	//total_comp_amount
	private Double totalCompAmount;

	//date_created_co_applicant
	private Date dateCreatedCoApplicant;

	//application_no
	private String applicationNo;

	//lot_size
	private String lotSize;

	//is_rooming_houses
	private Boolean isRoomingHouses;

	//condo_fees
	private Double condoFees;

	//lot
	private String lot;

	//referred
	private String referred;

	//message_is_follower
	private Boolean messageIsFollower;

	//base_comp_amount
	private Double baseCompAmount;

	//applicant_last_name
	private String applicantLastName;

	//is_duplex
	private Boolean isDuplex;

	//total_mortgage_amount
	private Double totalMortgageAmount;

	//existing_equity_amount
	private Double existingEquityAmount;

	//current_balance
	private Double currentBalance;
	
	//current_mortgage_amount
	private String currentLender;
		
	//current_mortgage_amount
	private Double currentMortgageAmount;

	//current_monthly_payment
	private Double currentMonthlyPayment;
		
	//write_date
	private Date writeDate;

	//condition_of_financing_date
	private Date conditionOfFinancingDate;

	//is_high_ratio_2nd_home
	private Boolean isHighRatio2ndHome;

	//marketing_comp_amount
	private Double marketingCompAmount;

	//draws_required
	private Integer drawsRequired;

	//is_condo
	private Boolean isCondo;

	//lender_response
	private Date lenderResponse;

	//ref
	private String ref;

	//is_boarding_houses
	private Boolean isBoardingHouses;

	//is_grow_ops
	private Boolean isGrowOps;

	//non_income_qualifer
	private Boolean nonIncomeQualifer;

	//outbuildings_value
	private Double outbuildingsValue;

	//is_modular_homes
	private Boolean isModularHomes;

	//expected_closing_date
	private Date expectedClosingDate;

	//existing_lender
	private String existingLender;

	//future_mortgage
	private String futureMortgage;

	//marketing_auto
	private String marketingAuto;

	//verify_product
	private Boolean verifyProduct;

	//personal_cash_amount
	private Double personalCashAmount;

	//new_home_warranty
	private String newHomeWarranty;

	//is_country_residential
	private Boolean isCountryResidential;

	//ltv
	private Double ltv;

	//lender_requires_insurance
	private Boolean lenderRequiresInsurance;

	//internal_note_property
	private String internalNoteProperty;

	//concerns_addressed_check
	private Boolean concernsAddressedCheck;

	//description
	private String description;

	//province
	private String province;

	//planned_revenue
	private Double plannedRevenue;

	//fax
	private String fax;

	//user_login
	private String userLogin;

	//date_action
	private Date dateAction;

	//email_cc
	private String emailCc;

	//existing_mortgage
	private String existingMortgage;

	//min_heat_fee
	private Double minHeatFee;

	//qualified_check
	private Boolean qualifiedCheck;

	//postal_code
	private String postalCode;

	//active
	private Boolean active;

	//property_taxes
	private Double propertyTaxes;

	//is_floating_homes
	private Boolean isFloatingHomes;

	//mobile
	private String mobile;

	//opp_info_rate
	private Integer oppInfoRate;

	//charges_behind_amount
	private Double chargesBehindAmount;

	//application_date
	private Date applicationDate;

	//user_email
	private String userEmail;

	//name
	private String name;

	//desired_mortgage_amount
	private Double desiredMortgageAmount;

	//volume_commission
	private Double volumeCommission;

	//partner_address_name
	private String partnerAddressName;

	//is_leased_land
	private Boolean isLeasedLand;

	//square_footage
	private Integer squareFootage;

	//color
	private Integer color;

	//email_work
	private String emailWork;

	//is_a_small_centre
	private Boolean isaSmallCentre;

	//sale_of_existing_amount
	
	private Double saleOfExistingAmount;

	//is_non_conv_construction
	////@ERP( name = "is_non_conv_construction" )
	private Boolean isNonConvConstruction;

	//partner_name
	////@ERP( name = "partner_name" )
	private String partnerName;

	//message_summary
	//@Column(length=9999)
	////@ERP( name = "message_summary" )
	private String messageSummary;

	//process_presntedutio_check
	////@ERP( name = "process_presntedutio_check" )
	private Boolean processPresntedutioCheck;

	//street
	private String street;

	//title_action
	////@ERP( name = "title_action" )
	private String titleAction;

	//opp_info_start_date
	//@Temporal(TemporalType.TIMESTAMP)
	////@ERP( name = "opp_info_start_date" )
	private Date oppInfoStartDate;

	//city
	private String city;

	//create_date
	////@ERP( name = "create_date" )
	private Date createDate;

	//has_charges_behind
	////@ERP( name = "has_charges_behind" )
	private Boolean hasChargesBehind;

	//renewaldate
	//@Temporal(TemporalType.TIMESTAMP)
	private Date renewaldate;

	//lender_name
	////@ERP( name = "lender_name" )
	private String lenderName;

	//day_close
	////@ERP( name = "day_close" )
	private Double dayClose;

	//date_closed
	////@ERP( name = "date_closed" )
	private Date dateClosed;

	//date_deadline
	//@Temporal(TemporalType.TIMESTAMP)
	////@ERP( name = "date_deadline" )
	private Date dateDeadline;

	//approached_check
	////@ERP( name = "approached_check" )
	private Boolean approachedCheck;

	//day_open
	////@ERP( name = "day_open" )
	private Double dayOpen;

	//is_uninsured_conv_2nd_home
	////@ERP( name = "is_uninsured_conv_2nd_home" )
	private Boolean isUninsuredConv2ndHome;

	//is_four_plex
	////@ERP( name = "is_four_plex" )
	private Boolean isFourPlex;

	//pending_application_check
	////@ERP( name = "pending_application_check" )
	private Boolean pendingApplicationCheck;

	//lender_fee
	////@ERP( name = "lender_fee" )
	private Double lenderFee;

	//is_raw_land
	////@ERP( name = "is_raw_land" )
	private Boolean isRawLand;

	//is_cottage_rec_property
	////@ERP( name = "is_cottage_rec_property" )
	private Boolean isCottageRecProperty;

	//rrsp_amount
	////@ERP( name = "rrsp_amount" )
	private Double rrsp_amount;

	//rrsp_amount
	////@ERP( name = "bank_account" )
	private Double bank_account;

	//purchase_price
	////@ERP( name = "purchase_price" )
	private Double purchase_price;

	//is_military
	////@ERP( name = "is_military" )
	private Boolean is_military;

	//work_phone
	////@ERP( name = "work_phone" )
	private String work_phone;

	//trailer_comp_amount
	////@ERP( name = "trailer_comp_amount" )
	private Double trailer_comp_amount;

	//opp_info_renewal_date
	//@Temporal(TemporalType.TIMESTAMP)
	////@ERP( name = "opp_info_renewal_date" )
	private Date opp_info_renewal_date;

	//block
	private String block;
	
	//PID
	private String townshipPID;

	//TDS
	////@ERP( name = "TDS" )
	private Double TDS;

	//lender_ref
	////@ERP( name = "lender_ref" )
	private String lender_ref;

	//sweat_equity_amount
	////@ERP( name = "sweat_equity_amount" )
	private Double sweat_equity_amount;

	//internal_notes_final
	//@Column(length=9999)
	////@ERP( name = "internal_notes_final" )
	private String internal_notes_final;

	//other_amount
	////@ERP( name = "other_amount" )
	private Double other_amount;

	//probability
	private Double probability;

	//opt_out
	////@ERP( name = "opt_out" )
	private Boolean optOut;

	//acres
	private Double acres;

	//estimated_valueof_home
	////@ERP( name = "estimated_valueof_home" )
	private String estimated_valueof_home;

	//current_interest_rate
	////@ERP( name = "current_interest_rate" )
	private Double current_interest_rate;

	//cash_back
	////@ERP( name = "cash_back" )
	private Integer cash_back;

	//planned_cost
	////@ERP( name = "planned_cost" )
	private Double planned_cost;

	//gifted_amount
	////@ERP( name = "gifted_amount" )
	private Double gifted_amount;

	//is_rental_pools
	////@ERP( name = "is_rental_pools" )
	private Boolean is_rental_pools;

	//is_fractional_interests
	////@ERP( name = "is_fractional_interests" )
	private Boolean isFractionalInterests;

	//message_unread
	////@ERP( name = "message_unread" )
	private Boolean messageUnread;

	//opp_info_loan_amnt
	////@ERP( name = "opp_info_loan_amnt" )
	private Integer opp_info_loan_amnt;

	//phone
	private String phone;

	//web_response
	////@ERP( name = "web_response" )
	private String webResponse;

	//mls
	private String mls;

	//current_renewal_date
	//@Temporal(TemporalType.TIMESTAMP)
	////@ERP( name = "current_renewal_date" )
	private Date currentRenewalDate;

	//partner_address_email
	////@ERP( name = "partner_address_email" )
	private String partnerAddressEmail;

	//is_commercial
	////@ERP( name = "is_commercial" )
	private Boolean isCommercial;

	//distance_to_major_center
	////@ERP( name = "distance_to_major_center" )
	private Double distanceToMajorCenter;

	//function
	private String function;

	//age
	private Integer age;

	//contact_name
	////@ERP( name = "contact_name" )
	private String contactName;

	//date_open
	////@ERP( name = "date_open" )
	private Date dateOpen;

	//internal_note
	//@Column(length=9999)
	////@ERP( name = "internal_note" )
	private String internalNote;

	//volume_bonus_amount
	////@ERP( name = "volume_bonus_amount" )
	private Double volume_bonus_amount;

	//is_construction_mortgage
	////@ERP( name = "is_construction_mortgage" )
	private Boolean is_construction_mortgage;

	//amortization
	private Double amortization;

	//ref2
	////@ERP( name = "ref2" )
	private String ref2;

	//date_action_next
	////@ERP( name = "date_action_next" )
	private Date dateActionNext;

	//total_one_time_fees
	////@ERP( name = "total_one_time_fees" )
	private Double totalOneTimeFees;

	//down_payment_amount
	////@ERP( name = "down_payment_amount" )
	private Double down_payment_amount;

	//rate
	private Double rate;

	//base_commission
	////@ERP( name = "base_commission" )
	private Double base_commission;

	//insurerfee
	private Double insurerfee;

	//street2
	////@ERP( name = "street2" )
	private String street2;

	//zip
	private String zip;

	//commitment_fee
	////@ERP( name = "commitment_fee" )
	private Double commitment_fee;

	//additional_amount
	////@ERP( name = "additional_amount" )
	private Double additional_amount;
	
	//credit_story
	//@Column(length=9999)
	////@ERP( name = "credit_story" )
	private String credit_story;

	//closing_date
	//@Temporal(TemporalType.TIMESTAMP)
	////@ERP( name = "closing_date" )
	private Date closingDate;

	//considered_cites
	////@ERP( name = "considered_cites" )
	private String closing_date;

	//morweb_filogix
	////@ERP( name = "morweb_filogix" )
	private String morwebFilogix;

	//is_eight_plex
	////@ERP( name = "is_eight_plex" )
	private Boolean isEightPlex;

	//maximum_amount
	////@ERP( name = "maximum_amount" )
	private Boolean maximum_amount;
		
	//internal_notes
	//@Column(length=9999)
	////@ERP( name = "internal_notes" )
	private String internalNotes;

	//property_value
	////@ERP( name = "property_value" )
	private Double property_value;

	//plan
	private String plan;

	//desired_amortization
	////@ERP( name = "desired_amortization" )
	private Integer desired_amortization;

	//address
	private String address;

	//is_six_plex
	////@ERP( name = "is_six_plex" )
	private Boolean isSixPlex;

	//borrowed_amount
	////@ERP( name = "borrowed_amount" )
	private Double borrowed_amount;
	
	//percent variable
	////@ERP( name = "percent_variable" )
	private Double percent_variable;
	

	//has self employed income
	private Boolean hasSelfEmployedIncome;

	/*********************************selection type******************************/
	//mortgage_type
	////@ERP( type = OpenERPType.selection, name = "mortgage_type" )
	private String mortgage_type;

	//preapproved_im_looking_fora
	////@ERP( type = OpenERPType.selection, name = "preapproved_im_looking_fora" )
	private String preapproved_im_looking_fora;

	//buy_new_vehicle
	////@ERP( type = OpenERPType.selection, name = "buy_new_vehicle" )
	private String buy_new_vehicle;

	//charge_on_title
	////@ERP( type = OpenERPType.selection, name = "charge_on_title" )
	private String charge_on_title;

	//future_family
	////@ERP( type = OpenERPType.selection, name = "future_family" )
	private String future_family;

	//desired_mortgage_type
	////@ERP( type = OpenERPType.selection, name = "desired_mortgage_type" )
	private String desiredMortgageType;

	//desired_term
	////@ERP( type = OpenERPType.selection, name = "desired_term" )
	private String desired_mortgage_type;

	//priority
	////@ERP( type = OpenERPType.selection, name = "priority" )
	private String priority;

	

	//income_decreased_worried
	////@ERP( type = OpenERPType.selection, name = "income_decreased_worried" )
	private String incomeDecreasedWorried;

	//garage_type
	////@ERP( type = OpenERPType.selection, name = "garage_type" )
	private String garageType;

	//product_type
	////@ERP( type = OpenERPType.selection, name = "product_type" )
	private String productType;

	//job_5_years
	////@ERP( type = OpenERPType.selection, name = "job_5_years" )
	private String job5Years;

	//property_type
	////@ERP( type = OpenERPType.selection, name = "property_type" )
	private String propertyType;

	//source_of_borrowing
	////@ERP( type = OpenERPType.selection, name = "source_of_borrowing" )
	private String sourceOfBorrowing;

	//desired_product_type
	////@ERP( type = OpenERPType.selection, name = "desired_product_type" )
	private String desiredProductType;

	//heating
	////@ERP( type = OpenERPType.selection, name = "heating" )
	private String heating;

	//term_rate
	////@ERP( type = OpenERPType.selection, name = "term_rate" )
	private String termRate;

	//property_style
	////@ERP( type = OpenERPType.selection, name = "property_style" )
	private String propertyStyle;

	//garage_size
	////@ERP( type = OpenERPType.selection, name = "garage_size" )
	private String garageSize;

	//approvedimlookingfora
	////@ERP( type = OpenERPType.selection, name = "approvedimlookingfora" )
	private String approvedimlookingfora;

	//down_payment_coming_from
	////@ERP( type = OpenERPType.selection, name = "down_payment_coming_from" )
	private String downPaymentComingFrom;

	//insurer
	////@ERP( type = OpenERPType.selection, name = "insurer" )
	private String insurer;

	//sewage
	////@ERP( type = OpenERPType.selection, name = "sewage" )
	private String sewage;

	//lifestyle_change
	////@ERP( type = OpenERPType.selection, name = "lifestyle_change" )
	private String lifestyleChange;

	//opp_info_type
	////@ERP( type = OpenERPType.selection, name = "opp_info_type" )
	private String oppInfoType;

	//financial_risk_taker
	////@ERP( type = OpenERPType.selection, name = "financial_risk_taker" )
	private String financial_risk_taker;

	//what_is_your_lending_goal
	////@ERP( type = OpenERPType.selection, name = "what_is_your_lending_goal" )
	private String what_is_your_lending_goal;

	//water
	////@ERP( type = OpenERPType.selection, name = "water" )
	private String water;

	//trigger
	////@ERP( type = OpenERPType.selection, name = "trigger" )
	private String trigger;

	//looking_to_approved
	////@ERP( type = OpenERPType.selection, name = "looking_to_approved" )
	private String lookingToApproved;

	//current_mortgage_type
	////@ERP( type = OpenERPType.selection, name = "current_mortgage_type" )
	private String currentMortgageType;

	//refinancelookingfora
	////@ERP( type = OpenERPType.selection, name = "refinancelookingfora" )
	private String refinancelookingfora;

	//living_in_property
	////@ERP( type = OpenERPType.selection, name = "living_in_property" )
	private String livingInProperty;

	//property_less_then_5_years
	////@ERP( type = OpenERPType.selection, name = "property_less_then_5_years" )
	private String propertyLessThen5Years;

	//apartment_style
	////@ERP( type = OpenERPType.selection, name = "apartment_style" )
	private String apartmentStyle;

	//type
	////@ERP( type = OpenERPType.selection, name = "type" )
	private String type;

	//looking_to_refinance
	////@ERP( type = OpenERPType.selection, name = "looking_to_refinance" )
	private String lookingToRefinance;

	//open_closed
	////@ERP( type = OpenERPType.selection, name = "open_closed" )
	private String openClosed;

	//renter_pay_heating
	////@ERP( type = OpenERPType.selection, name = "renter_pay_heating" )
	private String renterPayHeating;

	//state
	////@ERP( type = OpenERPType.selection, name = "state" )
	private String state;

	//building_funds
	////@ERP( type = OpenERPType.selection, name = "building_funds" )
	private String buildingFunds;

	//preferred_number
	////@ERP( type = OpenERPType.selection, name = "preferred_number" )
	private String preferredNumber;

	//term
	////@ERP( type = OpenERPType.selection, name = "term" )
	private String term;

	/*********************************one2many type******************************/

	//many to one : mapping table : product_product(com.syml.domain.Product)-->opportunity_id
	////@ERP(name="product_ids", type = OpenERPType.one2many )
	//@JsonIgnore
	//@Transient
	private List<Integer> productIds = new ArrayList<Integer>();

	//many to one : mapping table : applicant_record(com.syml.domain.Applicant)-->applicant_id
	////@ERP(name="applicant_record_line", type = OpenERPType.one2many )
	//@JsonIgnore
	//@Transient
	private List<Integer> applicantRecordLine = new ArrayList<Integer>();

	// This Variable was added as a many to many replacement of applicant_record_line
	//many to many : mapping table : applicant_record(com.syml.domain.Applicant)-->applicant_id
	////@ERP(name="app_rec_ids", type = OpenERPType.many2many )
	//@JsonIgnore
	//@Transient
	private List<Integer> appRecIds = new ArrayList<Integer>();
		
	//many to one : mapping table : deal(com.syml.domain.Note)-->opportunity_id
	////@ERP(name="deal_ids", type = OpenERPType.one2many )
	//@JsonIgnore
	//@Transient
	private List<Integer> dealIds = new ArrayList<Integer>();

	/*********************************many2one type******************************/

	//many to one : mapping table : res_partner(com.syml.domain.Lender)
	////@ERP(name="partner_id", type = OpenERPType.many2one )
	//@JsonIgnore
	private Integer partnerId;

	//many to one : mapping table : res_partner(com.syml.domain.Lender)
	////@ERP(name="referral_source", type = OpenERPType.many2one )
	//@JsonIgnore
	private Integer referralSource;

	//many to one : mapping table : res_partner(com.syml.domain.Lender)
	////@ERP(name="final_lender", type = OpenERPType.many2one )
	//@JsonIgnore
	private Integer finalLender;

	//many to one : mapping table : res_partner(com.syml.domain.Lender)
	////@ERP(name="lead_source", type = OpenERPType.many2one )
	//@JsonIgnore
	private Integer leadSource;

	//many to one : mapping table : res_partner(com.syml.domain.Lender)
	////@ERP(name="realtor", type = OpenERPType.many2one )
	//@JsonIgnore
	private Integer realtor;

	//many to one : mapping table : res_partner(com.syml.domain.Lender)
	////@ERP(name="lender", type = OpenERPType.many2one )
	//@JsonIgnore
	private Integer lender;

	//many to one : mapping table : product_product(com.syml.domain.Product)
	////@ERP(name="selected_product", type = OpenERPType.many2one )
	//@JsonIgnore
	private Integer selectedProduct;

	//many to one : mapping table : res_partner(com.syml.domain.Lender)
	////@ERP(name="referred_source", type = OpenERPType.many2one )
	//@JsonIgnore
	private Integer referredSource;

	//@Transient
	private List<Applicant> applicants;
	
	public Double getMonthlyRentalIncome() {
		return monthlyRentalIncome;
	}

	public void setMonthlyRentalIncome(Double monthlyRentalIncome) {
		this.monthlyRentalIncome = monthlyRentalIncome;
	}

	public Double getGDS() {
		return GDS;
	}

	public void setGDS(Double gDS) {
		GDS = gDS;
	}

	public Boolean getIsUpdatedToUA() {
		return isUpdatedToUA;
	}

	public void setIsUpdatedToUA(Boolean isUpdatedToUA) {
		this.isUpdatedToUA = isUpdatedToUA;
	}

	public Double getBrokerFee() {
		return brokerFee;
	}

	public void setBrokerFee(Double brokerFee) {
		this.brokerFee = brokerFee;
	}

	public String getInsurerref() {
		return insurerref;
	}

	public void setInsurerref(String insurerref) {
		this.insurerref = insurerref;
	}

	public String getSpouse() {
		return spouse;
	}

	public void setSpouse(String spouse) {
		this.spouse = spouse;
	}

	public Boolean getIsAgriculturalLess10Acres() {
		return isAgriculturalLess10Acres;
	}

	public void setIsAgriculturalLess10Acres(Boolean isAgriculturalLess10Acres) {
		this.isAgriculturalLess10Acres = isAgriculturalLess10Acres;
	}

	public String getEmailFrom() {
		return emailFrom;
	}

	public void setEmailFrom(String emailFrom) {
		this.emailFrom = emailFrom;
	}

	public Boolean getIsLifeLeasedProperty() {
		return isLifeLeasedProperty;
	}

	public void setIsLifeLeasedProperty(Boolean isLifeLeasedProperty) {
		this.isLifeLeasedProperty = isLifeLeasedProperty;
	}

	public Boolean getMortgageInsured() {
		return mortgageInsured;
	}

	public void setMortgageInsured(Boolean mortgageInsured) {
		this.mortgageInsured = mortgageInsured;
	}

	public Date getDateActionLast() {
		return dateActionLast;
	}

	public void setDateActionLast(Date dateActionLast) {
		this.dateActionLast = dateActionLast;
	}

	public Boolean getIsMobileHomes() {
		return isMobileHomes;
	}

	public void setIsMobileHomes(Boolean isMobileHomes) {
		this.isMobileHomes = isMobileHomes;
	}

	public Boolean getIsAgeRestricted() {
		return isAgeRestricted;
	}

	public void setIsAgeRestricted(Boolean isAgeRestricted) {
		this.isAgeRestricted = isAgeRestricted;
	}

	public Double getMonthlyPayment() {
		return monthlyPayment;
	}

	public void setMonthlyPayment(Double monthlyPayment) {
		this.monthlyPayment = monthlyPayment;
	}

	public Double getSecondaryFinancingAmount() {
		return secondaryFinancingAmount;
	}

	public void setSecondaryFinancingAmount(Double secondaryFinancingAmount) {
		this.secondaryFinancingAmount = secondaryFinancingAmount;
	}

	public Boolean getIsAgricultural() {
		return isAgricultural;
	}

	public void setIsAgricultural(Boolean isAgricultural) {
		this.isAgricultural = isAgricultural;
	}

	public Boolean getIsRentalProperty() {
		return isRentalProperty;
	}

	public void setIsRentalProperty(Boolean isRentalProperty) {
		this.isRentalProperty = isRentalProperty;
	}

	public Double getTotalCompAmount() {
		return totalCompAmount;
	}

	public void setTotalCompAmount(Double totalCompAmount) {
		this.totalCompAmount = totalCompAmount;
	}

	public Date getDateCreatedCoApplicant() {
		return dateCreatedCoApplicant;
	}

	public void setDateCreatedCoApplicant(Date dateCreatedCoApplicant) {
		this.dateCreatedCoApplicant = dateCreatedCoApplicant;
	}

	public String getApplicationNo() {
		return applicationNo;
	}

	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}

	public String getLotSize() {
		return lotSize;
	}

	public void setLotSize(String lotSize) {
		this.lotSize = lotSize;
	}

	public Boolean getIsRoomingHouses() {
		return isRoomingHouses;
	}

	public void setIsRoomingHouses(Boolean isRoomingHouses) {
		this.isRoomingHouses = isRoomingHouses;
	}

	public Double getCondoFees() {
		return condoFees;
	}

	public void setCondoFees(Double condoFees) {
		this.condoFees = condoFees;
	}

	public String getLot() {
		return lot;
	}

	public void setLot(String lot) {
		this.lot = lot;
	}

	public String getReferred() {
		return referred;
	}

	public void setReferred(String referred) {
		this.referred = referred;
	}

	public Boolean getMessageIsFollower() {
		return messageIsFollower;
	}

	public void setMessageIsFollower(Boolean messageIsFollower) {
		this.messageIsFollower = messageIsFollower;
	}

	public Double getBaseCompAmount() {
		return baseCompAmount;
	}

	public void setBaseCompAmount(Double baseCompAmount) {
		this.baseCompAmount = baseCompAmount;
	}

	public String getApplicantLastName() {
		return applicantLastName;
	}

	public void setApplicantLastName(String applicantLastName) {
		this.applicantLastName = applicantLastName;
	}

	public Boolean getIsDuplex() {
		return isDuplex;
	}

	public void setIsDuplex(Boolean isDuplex) {
		this.isDuplex = isDuplex;
	}

	public Double getTotalMortgageAmount() {
		return totalMortgageAmount;
	}

	public void setTotalMortgageAmount(Double totalMortgageAmount) {
		this.totalMortgageAmount = totalMortgageAmount;
	}

	public Double getExistingEquityAmount() {
		return existingEquityAmount;
	}

	public void setExistingEquityAmount(Double existingEquityAmount) {
		this.existingEquityAmount = existingEquityAmount;
	}

	public Double getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(Double currentBalance) {
		this.currentBalance = currentBalance;
	}

	public String getCurrentLender() {
		return currentLender;
	}

	public void setCurrentLender(String currentLender) {
		this.currentLender = currentLender;
	}

	public Double getCurrentMortgageAmount() {
		return currentMortgageAmount;
	}

	public void setCurrentMortgageAmount(Double currentMortgageAmount) {
		this.currentMortgageAmount = currentMortgageAmount;
	}

	public Double getCurrentMonthlyPayment() {
		return currentMonthlyPayment;
	}

	public void setCurrentMonthlyPayment(Double currentMonthlyPayment) {
		this.currentMonthlyPayment = currentMonthlyPayment;
	}

	public Date getWriteDate() {
		return writeDate;
	}

	public void setWriteDate(Date writeDate) {
		this.writeDate = writeDate;
	}

	public Date getConditionOfFinancingDate() {
		return conditionOfFinancingDate;
	}

	public void setConditionOfFinancingDate(Date conditionOfFinancingDate) {
		this.conditionOfFinancingDate = conditionOfFinancingDate;
	}

	public Boolean getIsHighRatio2ndHome() {
		return isHighRatio2ndHome;
	}

	public void setIsHighRatio2ndHome(Boolean isHighRatio2ndHome) {
		this.isHighRatio2ndHome = isHighRatio2ndHome;
	}

	public Double getMarketingCompAmount() {
		return marketingCompAmount;
	}

	public void setMarketingCompAmount(Double marketingCompAmount) {
		this.marketingCompAmount = marketingCompAmount;
	}

	public Integer getDrawsRequired() {
		return drawsRequired;
	}

	public void setDrawsRequired(Integer drawsRequired) {
		this.drawsRequired = drawsRequired;
	}

	public Boolean getIsCondo() {
		return isCondo;
	}

	public void setIsCondo(Boolean isCondo) {
		this.isCondo = isCondo;
	}

	public Date getLenderResponse() {
		return lenderResponse;
	}

	public void setLenderResponse(Date lenderResponse) {
		this.lenderResponse = lenderResponse;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public Boolean getIsBoardingHouses() {
		return isBoardingHouses;
	}

	public void setIsBoardingHouses(Boolean isBoardingHouses) {
		this.isBoardingHouses = isBoardingHouses;
	}

	public Boolean getIsGrowOps() {
		return isGrowOps;
	}

	public void setIsGrowOps(Boolean isGrowOps) {
		this.isGrowOps = isGrowOps;
	}

	public Boolean getNonIncomeQualifer() {
		return nonIncomeQualifer;
	}

	public void setNonIncomeQualifer(Boolean nonIncomeQualifer) {
		this.nonIncomeQualifer = nonIncomeQualifer;
	}

	public Double getOutbuildingsValue() {
		return outbuildingsValue;
	}

	public void setOutbuildingsValue(Double outbuildingsValue) {
		this.outbuildingsValue = outbuildingsValue;
	}

	public Boolean getIsModularHomes() {
		return isModularHomes;
	}

	public void setIsModularHomes(Boolean isModularHomes) {
		this.isModularHomes = isModularHomes;
	}

	public Date getExpectedClosingDate() {
		return expectedClosingDate;
	}

	public void setExpectedClosingDate(Date expectedClosingDate) {
		this.expectedClosingDate = expectedClosingDate;
	}

	public String getExistingLender() {
		return existingLender;
	}

	public void setExistingLender(String existingLender) {
		this.existingLender = existingLender;
	}

	public String getFutureMortgage() {
		return futureMortgage;
	}

	public void setFutureMortgage(String futureMortgage) {
		this.futureMortgage = futureMortgage;
	}

	public String getMarketingAuto() {
		return marketingAuto;
	}

	public void setMarketingAuto(String marketingAuto) {
		this.marketingAuto = marketingAuto;
	}

	public Boolean getVerifyProduct() {
		return verifyProduct;
	}

	public void setVerifyProduct(Boolean verifyProduct) {
		this.verifyProduct = verifyProduct;
	}

	public Double getPersonalCashAmount() {
		return personalCashAmount;
	}

	public void setPersonalCashAmount(Double personalCashAmount) {
		this.personalCashAmount = personalCashAmount;
	}

	public String getNewHomeWarranty() {
		return newHomeWarranty;
	}

	public void setNewHomeWarranty(String newHomeWarranty) {
		this.newHomeWarranty = newHomeWarranty;
	}

	public Boolean getIsCountryResidential() {
		return isCountryResidential;
	}

	public void setIsCountryResidential(Boolean isCountryResidential) {
		this.isCountryResidential = isCountryResidential;
	}

	public Double getLtv() {
		return ltv;
	}

	public void setLtv(Double ltv) {
		this.ltv = ltv;
	}

	public Boolean getLenderRequiresInsurance() {
		return lenderRequiresInsurance;
	}

	public void setLenderRequiresInsurance(Boolean lenderRequiresInsurance) {
		this.lenderRequiresInsurance = lenderRequiresInsurance;
	}

	public String getInternalNoteProperty() {
		return internalNoteProperty;
	}

	public void setInternalNoteProperty(String internalNoteProperty) {
		this.internalNoteProperty = internalNoteProperty;
	}

	public Boolean getConcernsAddressedCheck() {
		return concernsAddressedCheck;
	}

	public void setConcernsAddressedCheck(Boolean concernsAddressedCheck) {
		this.concernsAddressedCheck = concernsAddressedCheck;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public Double getPlannedRevenue() {
		return plannedRevenue;
	}

	public void setPlannedRevenue(Double plannedRevenue) {
		this.plannedRevenue = plannedRevenue;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getUserLogin() {
		return userLogin;
	}

	public void setUserLogin(String userLogin) {
		this.userLogin = userLogin;
	}

	public Date getDateAction() {
		return dateAction;
	}

	public void setDateAction(Date dateAction) {
		this.dateAction = dateAction;
	}

	public String getEmailCc() {
		return emailCc;
	}

	public void setEmailCc(String emailCc) {
		this.emailCc = emailCc;
	}

	public String getExistingMortgage() {
		return existingMortgage;
	}

	public void setExistingMortgage(String existingMortgage) {
		this.existingMortgage = existingMortgage;
	}

	public Double getMinHeatFee() {
		return minHeatFee;
	}

	public void setMinHeatFee(Double minHeatFee) {
		this.minHeatFee = minHeatFee;
	}

	public Boolean getQualifiedCheck() {
		return qualifiedCheck;
	}

	public void setQualifiedCheck(Boolean qualifiedCheck) {
		this.qualifiedCheck = qualifiedCheck;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Double getPropertyTaxes() {
		return propertyTaxes;
	}

	public void setPropertyTaxes(Double propertyTaxes) {
		this.propertyTaxes = propertyTaxes;
	}

	public Boolean getIsFloatingHomes() {
		return isFloatingHomes;
	}

	public void setIsFloatingHomes(Boolean isFloatingHomes) {
		this.isFloatingHomes = isFloatingHomes;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getOppInfoRate() {
		return oppInfoRate;
	}

	public void setOppInfoRate(Integer oppInfoRate) {
		this.oppInfoRate = oppInfoRate;
	}

	public Double getChargesBehindAmount() {
		return chargesBehindAmount;
	}

	public void setChargesBehindAmount(Double chargesBehindAmount) {
		this.chargesBehindAmount = chargesBehindAmount;
	}

	public Date getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(Date applicationDate) {
		this.applicationDate = applicationDate;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getDesiredMortgageAmount() {
		return desiredMortgageAmount;
	}

	public void setDesiredMortgageAmount(Double desiredMortgageAmount) {
		this.desiredMortgageAmount = desiredMortgageAmount;
	}

	public Double getVolumeCommission() {
		return volumeCommission;
	}

	public void setVolumeCommission(Double volumeCommission) {
		this.volumeCommission = volumeCommission;
	}

	public String getPartnerAddressName() {
		return partnerAddressName;
	}

	public void setPartnerAddressName(String partnerAddressName) {
		this.partnerAddressName = partnerAddressName;
	}

	public Boolean getIsLeasedLand() {
		return isLeasedLand;
	}

	public void setIsLeasedLand(Boolean isLeasedLand) {
		this.isLeasedLand = isLeasedLand;
	}

	public Integer getSquareFootage() {
		return squareFootage;
	}

	public void setSquareFootage(Integer squareFootage) {
		this.squareFootage = squareFootage;
	}

	public Integer getColor() {
		return color;
	}

	public void setColor(Integer color) {
		this.color = color;
	}

	public String getEmailWork() {
		return emailWork;
	}

	public void setEmailWork(String emailWork) {
		this.emailWork = emailWork;
	}

	public Boolean getIsaSmallCentre() {
		return isaSmallCentre;
	}

	public void setIsaSmallCentre(Boolean isaSmallCentre) {
		this.isaSmallCentre = isaSmallCentre;
	}

	public Double getSaleOfExistingAmount() {
		return saleOfExistingAmount;
	}

	public void setSaleOfExistingAmount(Double saleOfExistingAmount) {
		this.saleOfExistingAmount = saleOfExistingAmount;
	}

	public Boolean getIsNonConvConstruction() {
		return isNonConvConstruction;
	}

	public void setIsNonConvConstruction(Boolean isNonConvConstruction) {
		this.isNonConvConstruction = isNonConvConstruction;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getMessageSummary() {
		return messageSummary;
	}

	public void setMessageSummary(String messageSummary) {
		this.messageSummary = messageSummary;
	}

	public Boolean getProcessPresntedutioCheck() {
		return processPresntedutioCheck;
	}

	public void setProcessPresntedutioCheck(Boolean processPresntedutioCheck) {
		this.processPresntedutioCheck = processPresntedutioCheck;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getTitleAction() {
		return titleAction;
	}

	public void setTitleAction(String titleAction) {
		this.titleAction = titleAction;
	}

	public Date getOppInfoStartDate() {
		return oppInfoStartDate;
	}

	public void setOppInfoStartDate(Date oppInfoStartDate) {
		this.oppInfoStartDate = oppInfoStartDate;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Boolean getHasChargesBehind() {
		return hasChargesBehind;
	}

	public void setHasChargesBehind(Boolean hasChargesBehind) {
		this.hasChargesBehind = hasChargesBehind;
	}

	public Date getRenewaldate() {
		return renewaldate;
	}

	public void setRenewaldate(Date renewaldate) {
		this.renewaldate = renewaldate;
	}

	public String getLenderName() {
		return lenderName;
	}

	public void setLenderName(String lenderName) {
		this.lenderName = lenderName;
	}

	public Double getDayClose() {
		return dayClose;
	}

	public void setDayClose(Double dayClose) {
		this.dayClose = dayClose;
	}

	public Date getDateClosed() {
		return dateClosed;
	}

	public void setDateClosed(Date dateClosed) {
		this.dateClosed = dateClosed;
	}

	public Date getDateDeadline() {
		return dateDeadline;
	}

	public void setDateDeadline(Date dateDeadline) {
		this.dateDeadline = dateDeadline;
	}

	public Boolean getApproachedCheck() {
		return approachedCheck;
	}

	public void setApproachedCheck(Boolean approachedCheck) {
		this.approachedCheck = approachedCheck;
	}

	public Double getDayOpen() {
		return dayOpen;
	}

	public void setDayOpen(Double dayOpen) {
		this.dayOpen = dayOpen;
	}

	public Boolean getIsUninsuredConv2ndHome() {
		return isUninsuredConv2ndHome;
	}

	public void setIsUninsuredConv2ndHome(Boolean isUninsuredConv2ndHome) {
		this.isUninsuredConv2ndHome = isUninsuredConv2ndHome;
	}

	public Boolean getIsFourPlex() {
		return isFourPlex;
	}

	public void setIsFourPlex(Boolean isFourPlex) {
		this.isFourPlex = isFourPlex;
	}

	public Boolean getPendingApplicationCheck() {
		return pendingApplicationCheck;
	}

	public void setPendingApplicationCheck(Boolean pendingApplicationCheck) {
		this.pendingApplicationCheck = pendingApplicationCheck;
	}

	public Double getLenderFee() {
		return lenderFee;
	}

	public void setLenderFee(Double lenderFee) {
		this.lenderFee = lenderFee;
	}

	public Boolean getIsRawLand() {
		return isRawLand;
	}

	public void setIsRawLand(Boolean isRawLand) {
		this.isRawLand = isRawLand;
	}

	public Boolean getIsCottageRecProperty() {
		return isCottageRecProperty;
	}

	public void setIsCottageRecProperty(Boolean isCottageRecProperty) {
		this.isCottageRecProperty = isCottageRecProperty;
	}

	public Double getRrsp_amount() {
		return rrsp_amount;
	}

	public void setRrsp_amount(Double rrsp_amount) {
		this.rrsp_amount = rrsp_amount;
	}

	public Double getBank_account() {
		return bank_account;
	}

	public void setBank_account(Double bank_account) {
		this.bank_account = bank_account;
	}

	public Double getPurchase_price() {
		return purchase_price;
	}

	public void setPurchase_price(Double purchase_price) {
		this.purchase_price = purchase_price;
	}

	public Boolean getIs_military() {
		return is_military;
	}

	public void setIs_military(Boolean is_military) {
		this.is_military = is_military;
	}

	public String getWork_phone() {
		return work_phone;
	}

	public void setWork_phone(String work_phone) {
		this.work_phone = work_phone;
	}

	public Double getTrailer_comp_amount() {
		return trailer_comp_amount;
	}

	public void setTrailer_comp_amount(Double trailer_comp_amount) {
		this.trailer_comp_amount = trailer_comp_amount;
	}

	public Date getOpp_info_renewal_date() {
		return opp_info_renewal_date;
	}

	public void setOpp_info_renewal_date(Date opp_info_renewal_date) {
		this.opp_info_renewal_date = opp_info_renewal_date;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public String getTownshipPID() {
		return townshipPID;
	}

	public void setTownshipPID(String townshipPID) {
		this.townshipPID = townshipPID;
	}

	public Double getTDS() {
		return TDS;
	}

	public void setTDS(Double tDS) {
		TDS = tDS;
	}

	public String getLender_ref() {
		return lender_ref;
	}

	public void setLender_ref(String lender_ref) {
		this.lender_ref = lender_ref;
	}

	public Double getSweat_equity_amount() {
		return sweat_equity_amount;
	}

	public void setSweat_equity_amount(Double sweat_equity_amount) {
		this.sweat_equity_amount = sweat_equity_amount;
	}

	public String getInternal_notes_final() {
		return internal_notes_final;
	}

	public void setInternal_notes_final(String internal_notes_final) {
		this.internal_notes_final = internal_notes_final;
	}

	public Double getOther_amount() {
		return other_amount;
	}

	public void setOther_amount(Double other_amount) {
		this.other_amount = other_amount;
	}

	public Double getProbability() {
		return probability;
	}

	public void setProbability(Double probability) {
		this.probability = probability;
	}

	public Boolean getOptOut() {
		return optOut;
	}

	public void setOptOut(Boolean optOut) {
		this.optOut = optOut;
	}

	public Double getAcres() {
		return acres;
	}

	public void setAcres(Double acres) {
		this.acres = acres;
	}

	public String getEstimated_valueof_home() {
		return estimated_valueof_home;
	}

	public void setEstimated_valueof_home(String estimated_valueof_home) {
		this.estimated_valueof_home = estimated_valueof_home;
	}

	public Double getCurrent_interest_rate() {
		return current_interest_rate;
	}

	public void setCurrent_interest_rate(Double current_interest_rate) {
		this.current_interest_rate = current_interest_rate;
	}

	public Integer getCash_back() {
		return cash_back;
	}

	public void setCash_back(Integer cash_back) {
		this.cash_back = cash_back;
	}

	public Double getPlanned_cost() {
		return planned_cost;
	}

	public void setPlanned_cost(Double planned_cost) {
		this.planned_cost = planned_cost;
	}

	public Double getGifted_amount() {
		return gifted_amount;
	}

	public void setGifted_amount(Double gifted_amount) {
		this.gifted_amount = gifted_amount;
	}

	public Boolean getIs_rental_pools() {
		return is_rental_pools;
	}

	public void setIs_rental_pools(Boolean is_rental_pools) {
		this.is_rental_pools = is_rental_pools;
	}

	public Boolean getIsFractionalInterests() {
		return isFractionalInterests;
	}

	public void setIsFractionalInterests(Boolean isFractionalInterests) {
		this.isFractionalInterests = isFractionalInterests;
	}

	public Boolean getMessageUnread() {
		return messageUnread;
	}

	public void setMessageUnread(Boolean messageUnread) {
		this.messageUnread = messageUnread;
	}

	public Integer getOpp_info_loan_amnt() {
		return opp_info_loan_amnt;
	}

	public void setOpp_info_loan_amnt(Integer opp_info_loan_amnt) {
		this.opp_info_loan_amnt = opp_info_loan_amnt;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getWebResponse() {
		return webResponse;
	}

	public void setWebResponse(String webResponse) {
		this.webResponse = webResponse;
	}

	public String getMls() {
		return mls;
	}

	public void setMls(String mls) {
		this.mls = mls;
	}

	public Date getCurrentRenewalDate() {
		return currentRenewalDate;
	}

	public void setCurrentRenewalDate(Date currentRenewalDate) {
		this.currentRenewalDate = currentRenewalDate;
	}

	public String getPartnerAddressEmail() {
		return partnerAddressEmail;
	}

	public void setPartnerAddressEmail(String partnerAddressEmail) {
		this.partnerAddressEmail = partnerAddressEmail;
	}

	public Boolean getIsCommercial() {
		return isCommercial;
	}

	public void setIsCommercial(Boolean isCommercial) {
		this.isCommercial = isCommercial;
	}

	public Double getDistanceToMajorCenter() {
		return distanceToMajorCenter;
	}

	public void setDistanceToMajorCenter(Double distanceToMajorCenter) {
		this.distanceToMajorCenter = distanceToMajorCenter;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public Date getDateOpen() {
		return dateOpen;
	}

	public void setDateOpen(Date dateOpen) {
		this.dateOpen = dateOpen;
	}

	public String getInternalNote() {
		return internalNote;
	}

	public void setInternalNote(String internalNote) {
		this.internalNote = internalNote;
	}

	public Double getVolume_bonus_amount() {
		return volume_bonus_amount;
	}

	public void setVolume_bonus_amount(Double volume_bonus_amount) {
		this.volume_bonus_amount = volume_bonus_amount;
	}

	public Boolean getIs_construction_mortgage() {
		return is_construction_mortgage;
	}

	public void setIs_construction_mortgage(Boolean is_construction_mortgage) {
		this.is_construction_mortgage = is_construction_mortgage;
	}

	public Double getAmortization() {
		return amortization;
	}

	public void setAmortization(Double amortization) {
		this.amortization = amortization;
	}

	public String getRef2() {
		return ref2;
	}

	public void setRef2(String ref2) {
		this.ref2 = ref2;
	}

	public Date getDateActionNext() {
		return dateActionNext;
	}

	public void setDateActionNext(Date dateActionNext) {
		this.dateActionNext = dateActionNext;
	}

	public Double getTotalOneTimeFees() {
		return totalOneTimeFees;
	}

	public void setTotalOneTimeFees(Double totalOneTimeFees) {
		this.totalOneTimeFees = totalOneTimeFees;
	}

	public Double getDown_payment_amount() {
		return down_payment_amount;
	}

	public void setDown_payment_amount(Double down_payment_amount) {
		this.down_payment_amount = down_payment_amount;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Double getBase_commission() {
		return base_commission;
	}

	public void setBase_commission(Double base_commission) {
		this.base_commission = base_commission;
	}

	public Double getInsurerfee() {
		return insurerfee;
	}

	public void setInsurerfee(Double insurerfee) {
		this.insurerfee = insurerfee;
	}

	public String getStreet2() {
		return street2;
	}

	public void setStreet2(String street2) {
		this.street2 = street2;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public Double getCommitment_fee() {
		return commitment_fee;
	}

	public void setCommitment_fee(Double commitment_fee) {
		this.commitment_fee = commitment_fee;
	}

	public Double getAdditional_amount() {
		return additional_amount;
	}

	public void setAdditional_amount(Double additional_amount) {
		this.additional_amount = additional_amount;
	}

	public String getCredit_story() {
		return credit_story;
	}

	public void setCredit_story(String credit_story) {
		this.credit_story = credit_story;
	}

	public Date getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(Date closingDate) {
		this.closingDate = closingDate;
	}

	public String getClosing_date() {
		return closing_date;
	}

	public void setClosing_date(String closing_date) {
		this.closing_date = closing_date;
	}

	public String getMorwebFilogix() {
		return morwebFilogix;
	}

	public void setMorwebFilogix(String morwebFilogix) {
		this.morwebFilogix = morwebFilogix;
	}

	public Boolean getIsEightPlex() {
		return isEightPlex;
	}

	public void setIsEightPlex(Boolean isEightPlex) {
		this.isEightPlex = isEightPlex;
	}

	public Boolean getMaximum_amount() {
		return maximum_amount;
	}

	public void setMaximum_amount(Boolean maximum_amount) {
		this.maximum_amount = maximum_amount;
	}

	public String getInternalNotes() {
		return internalNotes;
	}

	public void setInternalNotes(String internalNotes) {
		this.internalNotes = internalNotes;
	}

	public Double getProperty_value() {
		return property_value;
	}

	public void setProperty_value(Double property_value) {
		this.property_value = property_value;
	}

	public String getPlan() {
		return plan;
	}

	public void setPlan(String plan) {
		this.plan = plan;
	}

	public Integer getDesired_amortization() {
		return desired_amortization;
	}

	public void setDesired_amortization(Integer desired_amortization) {
		this.desired_amortization = desired_amortization;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Boolean getIsSixPlex() {
		return isSixPlex;
	}

	public void setIsSixPlex(Boolean isSixPlex) {
		this.isSixPlex = isSixPlex;
	}

	public Double getBorrowed_amount() {
		return borrowed_amount;
	}

	public void setBorrowed_amount(Double borrowed_amount) {
		this.borrowed_amount = borrowed_amount;
	}

	public Double getPercent_variable() {
		return percent_variable;
	}

	public void setPercent_variable(Double percent_variable) {
		this.percent_variable = percent_variable;
	}

	public Boolean getHasSelfEmployedIncome() {
		return hasSelfEmployedIncome;
	}

	public void setHasSelfEmployedIncome(Boolean hasSelfEmployedIncome) {
		this.hasSelfEmployedIncome = hasSelfEmployedIncome;
	}

	public String getMortgage_type() {
		return mortgage_type;
	}

	public void setMortgage_type(String mortgage_type) {
		this.mortgage_type = mortgage_type;
	}

	public String getPreapproved_im_looking_fora() {
		return preapproved_im_looking_fora;
	}

	public void setPreapproved_im_looking_fora(String preapproved_im_looking_fora) {
		this.preapproved_im_looking_fora = preapproved_im_looking_fora;
	}

	public String getBuy_new_vehicle() {
		return buy_new_vehicle;
	}

	public void setBuy_new_vehicle(String buy_new_vehicle) {
		this.buy_new_vehicle = buy_new_vehicle;
	}

	public String getCharge_on_title() {
		return charge_on_title;
	}

	public void setCharge_on_title(String charge_on_title) {
		this.charge_on_title = charge_on_title;
	}

	public String getFuture_family() {
		return future_family;
	}

	public void setFuture_family(String future_family) {
		this.future_family = future_family;
	}

	public String getDesiredMortgageType() {
		return desiredMortgageType;
	}

	public void setDesiredMortgageType(String desiredMortgageType) {
		this.desiredMortgageType = desiredMortgageType;
	}

	public String getDesired_mortgage_type() {
		return desired_mortgage_type;
	}

	public void setDesired_mortgage_type(String desired_mortgage_type) {
		this.desired_mortgage_type = desired_mortgage_type;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getIncomeDecreasedWorried() {
		return incomeDecreasedWorried;
	}

	public void setIncomeDecreasedWorried(String incomeDecreasedWorried) {
		this.incomeDecreasedWorried = incomeDecreasedWorried;
	}

	public String getGarageType() {
		return garageType;
	}

	public void setGarageType(String garageType) {
		this.garageType = garageType;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getJob5Years() {
		return job5Years;
	}

	public void setJob5Years(String job5Years) {
		this.job5Years = job5Years;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public String getSourceOfBorrowing() {
		return sourceOfBorrowing;
	}

	public void setSourceOfBorrowing(String sourceOfBorrowing) {
		this.sourceOfBorrowing = sourceOfBorrowing;
	}

	public String getDesiredProductType() {
		return desiredProductType;
	}

	public void setDesiredProductType(String desiredProductType) {
		this.desiredProductType = desiredProductType;
	}

	public String getHeating() {
		return heating;
	}

	public void setHeating(String heating) {
		this.heating = heating;
	}

	public String getTermRate() {
		return termRate;
	}

	public void setTermRate(String termRate) {
		this.termRate = termRate;
	}

	public String getPropertyStyle() {
		return propertyStyle;
	}

	public void setPropertyStyle(String propertyStyle) {
		this.propertyStyle = propertyStyle;
	}

	public String getGarageSize() {
		return garageSize;
	}

	public void setGarageSize(String garageSize) {
		this.garageSize = garageSize;
	}

	public String getApprovedimlookingfora() {
		return approvedimlookingfora;
	}

	public void setApprovedimlookingfora(String approvedimlookingfora) {
		this.approvedimlookingfora = approvedimlookingfora;
	}

	public String getDownPaymentComingFrom() {
		return downPaymentComingFrom;
	}

	public void setDownPaymentComingFrom(String downPaymentComingFrom) {
		this.downPaymentComingFrom = downPaymentComingFrom;
	}

	public String getInsurer() {
		return insurer;
	}

	public void setInsurer(String insurer) {
		this.insurer = insurer;
	}

	public String getSewage() {
		return sewage;
	}

	public void setSewage(String sewage) {
		this.sewage = sewage;
	}

	public String getLifestyleChange() {
		return lifestyleChange;
	}

	public void setLifestyleChange(String lifestyleChange) {
		this.lifestyleChange = lifestyleChange;
	}

	public String getOppInfoType() {
		return oppInfoType;
	}

	public void setOppInfoType(String oppInfoType) {
		this.oppInfoType = oppInfoType;
	}

	public String getFinancial_risk_taker() {
		return financial_risk_taker;
	}

	public void setFinancial_risk_taker(String financial_risk_taker) {
		this.financial_risk_taker = financial_risk_taker;
	}

	public String getWhat_is_your_lending_goal() {
		return what_is_your_lending_goal;
	}

	public void setWhat_is_your_lending_goal(String what_is_your_lending_goal) {
		this.what_is_your_lending_goal = what_is_your_lending_goal;
	}

	public String getWater() {
		return water;
	}

	public void setWater(String water) {
		this.water = water;
	}

	public String getTrigger() {
		return trigger;
	}

	public void setTrigger(String trigger) {
		this.trigger = trigger;
	}

	public String getLookingToApproved() {
		return lookingToApproved;
	}

	public void setLookingToApproved(String lookingToApproved) {
		this.lookingToApproved = lookingToApproved;
	}

	public String getCurrentMortgageType() {
		return currentMortgageType;
	}

	public void setCurrentMortgageType(String currentMortgageType) {
		this.currentMortgageType = currentMortgageType;
	}

	public String getRefinancelookingfora() {
		return refinancelookingfora;
	}

	public void setRefinancelookingfora(String refinancelookingfora) {
		this.refinancelookingfora = refinancelookingfora;
	}

	public String getLivingInProperty() {
		return livingInProperty;
	}

	public void setLivingInProperty(String livingInProperty) {
		this.livingInProperty = livingInProperty;
	}

	public String getPropertyLessThen5Years() {
		return propertyLessThen5Years;
	}

	public void setPropertyLessThen5Years(String propertyLessThen5Years) {
		this.propertyLessThen5Years = propertyLessThen5Years;
	}

	public String getApartmentStyle() {
		return apartmentStyle;
	}

	public void setApartmentStyle(String apartmentStyle) {
		this.apartmentStyle = apartmentStyle;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLookingToRefinance() {
		return lookingToRefinance;
	}

	public void setLookingToRefinance(String lookingToRefinance) {
		this.lookingToRefinance = lookingToRefinance;
	}

	public String getOpenClosed() {
		return openClosed;
	}

	public void setOpenClosed(String openClosed) {
		this.openClosed = openClosed;
	}

	public String getRenterPayHeating() {
		return renterPayHeating;
	}

	public void setRenterPayHeating(String renterPayHeating) {
		this.renterPayHeating = renterPayHeating;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getBuildingFunds() {
		return buildingFunds;
	}

	public void setBuildingFunds(String buildingFunds) {
		this.buildingFunds = buildingFunds;
	}

	public String getPreferredNumber() {
		return preferredNumber;
	}

	public void setPreferredNumber(String preferredNumber) {
		this.preferredNumber = preferredNumber;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public List<Integer> getProductIds() {
		return productIds;
	}

	public void setProductIds(List<Integer> productIds) {
		this.productIds = productIds;
	}

	public List<Integer> getApplicantRecordLine() {
		return applicantRecordLine;
	}

	public void setApplicantRecordLine(List<Integer> applicantRecordLine) {
		this.applicantRecordLine = applicantRecordLine;
	}

	public List<Integer> getAppRecIds() {
		return appRecIds;
	}

	public void setAppRecIds(List<Integer> appRecIds) {
		this.appRecIds = appRecIds;
	}

	
	public Integer getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
	}

	public Integer getReferralSource() {
		return referralSource;
	}

	public void setReferralSource(Integer referralSource) {
		this.referralSource = referralSource;
	}

	
	public Integer getRealtor() {
		return realtor;
	}

	public void setRealtor(Integer realtor) {
		this.realtor = realtor;
	}

	public Integer getLender() {
		return lender;
	}

	public void setLender(Integer lender) {
		this.lender = lender;
	}

	public Integer getSelectedProduct() {
		return selectedProduct;
	}

	public void setSelectedProduct(Integer selectedProduct) {
		this.selectedProduct = selectedProduct;
	}

	public Integer getReferredSource() {
		return referredSource;
	}

	public void setReferredSource(Integer referredSource) {
		this.referredSource = referredSource;
	}

	public List<Applicant> getApplicants() {
		return applicants;
	}

	public void setApplicants(List<Applicant> applicants) {
		this.applicants = applicants;
	}

	
	

}
