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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.persistence.TemporalType;

import com.syml.generator.domain.annotation.ERP.OpenERPType;

import java.util.ArrayList;
/**
* mapping open erp object : crm.lead
* @author .x.m.
*
*/
@XmlTransient
@XmlRootElement
@Entity(name="crm_lead")
public class Opportunity extends BaseBean {
	
	//company
	@ERP( name = "x_company" )
	public String company;
	
	//base_ltv
	@ERP( name = "x_base_ltv" )
	public Double baseLtv;
	
	//x_selected_recommendation
	@ERP( name = "x_selected_recommendation" )
	public String xSelectedRecommendation;	
	
	@ERP( name = "x_bypass_proposal" )
	public Boolean isBypassProposal;	
	
	
	public Boolean getIsBypassProposal() {
		return isBypassProposal;
	}

	public void setIsBypassProposal(Boolean isBypassProposal) {
		this.isBypassProposal = isBypassProposal;
	}



	//renovation_value
	@ERP( name = "renovation_value" )
	public Double renovationValue;

	//is_co_operative_housing
	@ERP( name = "is_co_operative_housing" )
	public Boolean isCoOperativeHousing;

	//monthly_rental_income
	@ERP( name = "monthly_rental_income" )
	public Double monthlyRentalIncome;

	//GDS
	@ERP( name = "GDS" )
	public Double GDS;

	//isUpdatedToUA
	public Boolean isUpdatedToUA;

	//broker_fee
	@ERP( name = "broker_fee" )
	public Double brokerFee;

	//insurerref
	public String insurerref;

	//spouse
	public String spouse;

	//is_agricultural_less_10_acres
	@ERP( name = "is_agricultural_less_10_acres" )
	public Boolean isAgriculturalLess10Acres;

	//email_from
	@ERP( name = "email_from" )
	public String emailFrom;

	//is_life_leased_property
	@ERP( name = "is_life_leased_property" )
	public Boolean isLifeLeasedProperty;

	//mortgage_insured
	@ERP( name = "mortgage_insured" )
	public Boolean mortgageInsured;

	//date_action_last
	@ERP( name = "date_action_last" )
	public Date dateActionLast;

	//is_mobile_homes
	@ERP( name = "is_mobile_homes" )
	public Boolean isMobileHomes;

	//is_age_restricted
	@ERP( name = "is_age_restricted" )
	public Boolean isAgeRestricted;

	//monthly_payment
	@ERP( name = "monthly_payment" )
	public Double monthlyPayment;

	//secondary_financing_amount
	@ERP( name = "secondary_financing_amount" )
	public Double secondaryFinancingAmount;

	//is_agricultural
	@ERP( name = "is_agricultural" )
	public Boolean isAgricultural;

	//is_rental_property
	@ERP( name = "is_rental_property" )
	public Boolean isRentalProperty;

	//total_comp_amount
	@ERP( name = "total_comp_amount" )
	public Double totalCompAmount;

	//date_created_co_applicant
	@Temporal(TemporalType.TIMESTAMP)
	@ERP( name = "date_created_co_applicant" )
	public Date dateCreatedCoApplicant;

	//application_no
	@ERP( name = "application_no" )
	public String applicationNo;

	//lot_size
	@ERP( name = "lot_size" )
	public String lotSize;

	//is_rooming_houses
	@ERP( name = "is_rooming_houses" )
	public Boolean isRoomingHouses;

	//condo_fees
	@ERP( name = "condo_fees" )
	public Double condoFees;

	//lot
	public String lot;

	//referred
	public String referred;

	//message_is_follower
	@ERP( name = "message_is_follower" )
	public Boolean messageIsFollower;

	//base_comp_amount
	@ERP( name = "base_comp_amount" )
	public Double baseCompAmount;

	//applicant_last_name
	@ERP( name = "applicant_last_name" )
	public String applicantLastName;

	//is_duplex
	@ERP( name = "is_duplex" )
	public Boolean isDuplex;

	//total_mortgage_amount
	@ERP( name = "total_mortgage_amount" )
	public Double totalMortgageAmount;

	//existing_equity_amount
	@ERP( name = "existing_equity_amount" )
	public Double existingEquityAmount;

	//current_balance
	@ERP( name = "current_balance" )
	public Double currentBalance;
	
	//current_mortgage_amount
	@ERP( name = "current_lender" )
	public String currentLender;
		
	//current_mortgage_amount
	@ERP( name = "current_mortgage_amount" )
	public Double currentMortgageAmount;

	//current_monthly_payment
	@ERP( name = "current_monthly_payment" )
	public Double currentMonthlyPayment;
		
	//write_date
	@ERP( name = "write_date" )
	public Date writeDate;

	//condition_of_financing_date
	@ERP( name = "condition_of_financing_date" )
	public Date conditionOfFinancingDate;

	//is_high_ratio_2nd_home
	@ERP( name = "is_high_ratio_2nd_home" )
	public Boolean isHighRatio2ndHome;

	//marketing_comp_amount
	@ERP( name = "marketing_comp_amount" )
	public Double marketingCompAmount;

	//draws_required
	@ERP( name = "draws_required" )
	public Integer drawsRequired;

	//is_condo
	@ERP( name = "is_condo" )
	public Boolean isCondo;

	//lender_response
	@ERP( name = "lender_response" )
	public Date lenderResponse;

	//ref
	public String ref;

	//is_boarding_houses
	@ERP( name = "is_boarding_houses" )
	public Boolean isBoardingHouses;

	//is_grow_ops
	@ERP( name = "is_grow_ops" )
	public Boolean isGrowOps;

	//non_income_qualifer
	@ERP( name = "non_income_qualifer" )
	public Boolean nonIncomeQualifer;

	//outbuildings_value
	@ERP( name = "outbuildings_value" )
	public Double outbuildingsValue;

	//is_modular_homes
	@ERP( name = "is_modular_homes" )
	public Boolean isModularHomes;

	//expected_closing_date
	@ERP( name = "expected_closing_date" )
	public Date expectedClosingDate;

	//existing_lender
	@ERP( name = "existing_lender" )
	public String existingLender;

	//future_mortgage
	@ERP( name = "future_mortgage" )
	public String futureMortgage;

	//marketing_auto
	@ERP( name = "marketing_auto" )
	public String marketingAuto;

	//verify_product
	@ERP( name = "verify_product" )
	public Boolean verifyProduct;

	//personal_cash_amount
	@ERP( name = "personal_cash_amount" )
	public Double personalCashAmount;

	//new_home_warranty
	@ERP( name = "new_home_warranty" )
	public String newHomeWarranty;

	//is_country_residential
	@ERP( name = "is_country_residential" )
	public Boolean isCountryResidential;

	//ltv
	public Double ltv;

	//lender_requires_insurance
	@ERP( name = "lender_requires_insurance" )
	public Boolean lenderRequiresInsurance;

	//internal_note_property
	@Column(length=9999)
	@ERP( name = "internal_note_property" )
	public String internalNoteProperty;

	//concerns_addressed_check
	@ERP( name = "concerns_addressed_check" )
	public Boolean concernsAddressedCheck;

	//description
	@Column(length=9999)
	public String description;

	//province
	public String province;

	//planned_revenue
	@ERP( name = "planned_revenue" )
	public Double plannedRevenue;

	//fax
	public String fax;

	//user_login
	@ERP( name = "user_login" )
	public String userLogin;

	//date_action
	@Temporal(TemporalType.TIMESTAMP)
	@ERP( name = "date_action" )
	public Date dateAction;

	//email_cc
	@Column(length=9999)
	@ERP( name = "email_cc" )
	public String emailCc;

	//existing_mortgage
	@ERP( name = "existing_mortgage" )
	public String existingMortgage;

	//min_heat_fee
	@ERP( name = "min_heat_fee" )
	public Double minHeatFee;

	//qualified_check
	@ERP( name = "qualified_check" )
	public Boolean qualifiedCheck;

	//postal_code
	@ERP( name = "postal_code" )
	public String postalCode;

	//active
	public Boolean active;

	//property_taxes
	@ERP( name = "property_taxes" )
	public Double propertyTaxes;

	//is_floating_homes
	@ERP( name = "is_floating_homes" )
	public Boolean isFloatingHomes;

	//mobile
	public String mobile;

	//opp_info_rate
	@ERP( name = "opp_info_rate" )
	public Integer oppInfoRate;

	//charges_behind_amount
	@ERP( name = "charges_behind_amount" )
	public Double chargesBehindAmount;

	//application_date
	@ERP( name = "application_date" )
	public Date applicationDate;

	//user_email
	@ERP( name = "user_email" )
	public String userEmail;

	//name
	public String name;

	//desired_mortgage_amount
	@ERP( name = "desired_mortgage_amount" )
	public Double desiredMortgageAmount;

	//volume_commission
	@ERP( name = "volume_commission" )
	public Double volumeCommission;

	//partner_address_name
	@ERP( name = "partner_address_name" )
	public String partnerAddressName;

	//is_leased_land
	@ERP( name = "is_leased_land" )
	public Boolean isLeasedLand;

	//square_footage
	@ERP( name = "square_footage" )
	public Integer squareFootage;

	//color
	public Integer color;

	//email_work
	@ERP( name = "email_work" )
	public String emailWork;

	//is_a_small_centre
	@ERP( name = "is_a_small_centre" )
	public Boolean isaSmallCentre;

	//sale_of_existing_amount
	@ERP( name = "sale_of_existing_amount" )
	public Double saleOfExistingAmount;

	//is_non_conv_construction
	@ERP( name = "is_non_conv_construction" )
	public Boolean isNonConvConstruction;

	//partner_name
	@ERP( name = "partner_name" )
	public String partnerName;

	//message_summary
	@Column(length=9999)
	@ERP( name = "message_summary" )
	public String messageSummary;

	//process_presntedutio_check
	@ERP( name = "process_presntedutio_check" )
	public Boolean processPresntedutioCheck;

	//street
	public String street;

	//title_action
	@ERP( name = "title_action" )
	public String titleAction;

	//opp_info_start_date
	@Temporal(TemporalType.TIMESTAMP)
	@ERP( name = "opp_info_start_date" )
	public Date oppInfoStartDate;

	//city
	public String city;

	//create_date
	@ERP( name = "create_date" )
	public Date createDate;

	//has_charges_behind
	@ERP( name = "has_charges_behind" )
	public Boolean hasChargesBehind;

	//renewaldate
	@Temporal(TemporalType.TIMESTAMP)
	public Date renewaldate;

	//lender_name
	@ERP( name = "lender_name" )
	public String lenderName;

	//day_close
	@ERP( name = "day_close" )
	public Double dayClose;

	//date_closed
	@ERP( name = "date_closed" )
	public Date dateClosed;

	//date_deadline
	@Temporal(TemporalType.TIMESTAMP)
	@ERP( name = "date_deadline" )
	public Date dateDeadline;

	//approached_check
	@ERP( name = "approached_check" )
	public Boolean approachedCheck;

	//day_open
	@ERP( name = "day_open" )
	public Double dayOpen;

	//is_uninsured_conv_2nd_home
	@ERP( name = "is_uninsured_conv_2nd_home" )
	public Boolean isUninsuredConv2ndHome;

	//is_four_plex
	@ERP( name = "is_four_plex" )
	public Boolean isFourPlex;

	//pending_application_check
	@ERP( name = "pending_application_check" )
	public Boolean pendingApplicationCheck;

	//lender_fee
	@ERP( name = "lender_fee" )
	public Double lenderFee;

	//is_raw_land
	@ERP( name = "is_raw_land" )
	public Boolean isRawLand;

	//is_cottage_rec_property
	@ERP( name = "is_cottage_rec_property" )
	public Boolean isCottageRecProperty;

	//rrsp_amount
	@ERP( name = "rrsp_amount" )
	public Double rrspAmount;

	//rrsp_amount
	@ERP( name = "bank_account" )
	public Double bankAccount;

	//purchase_price
	@ERP( name = "purchase_price" )
	public Double purchasePrice;

	//is_military
	@ERP( name = "is_military" )
	public Boolean isMilitary;

	//work_phone
	@ERP( name = "work_phone" )
	public String workPhone;

	//trailer_comp_amount
	@ERP( name = "trailer_comp_amount" )
	public Double trailerCompAmount;

	//opp_info_renewal_date
	@Temporal(TemporalType.TIMESTAMP)
	@ERP( name = "opp_info_renewal_date" )
	public Date oppInfoRenewalDate;

	//block
	public String block;
	
	//PID
	public String townshipPID;

	//TDS
	@ERP( name = "TDS" )
	public Double TDS;

	//lender_ref
	@ERP( name = "lender_ref" )
	public String lenderRef;

	//sweat_equity_amount
	@ERP( name = "sweat_equity_amount" )
	public Double sweatEquityAmount;

	//internal_notes_final
	@Column(length=9999)
	@ERP( name = "internal_notes_final" )
	public String internalNotesFinal;

	//other_amount
	@ERP( name = "other_amount" )
	public Double otherAmount;

	//probability
	public Double probability;

	//opt_out
	@ERP( name = "opt_out" )
	public Boolean optOut;

	//acres
	public Double acres;

	//estimated_valueof_home
	@ERP( name = "estimated_valueof_home" )
	public String estimatedValueofHome;

	//current_interest_rate
	@ERP( name = "current_interest_rate" )
	public Double currentInterestRate;

	//cash_back
	@ERP( name = "cash_back" )
	public Integer cashBack;

	//planned_cost
	@ERP( name = "planned_cost" )
	public Double plannedCost;

	//gifted_amount
	@ERP( name = "gifted_amount" )
	public Double giftedAmount;

	//is_rental_pools
	@ERP( name = "is_rental_pools" )
	public Boolean isRentalPools;

	//is_fractional_interests
	@ERP( name = "is_fractional_interests" )
	public Boolean isFractionalInterests;

	//message_unread
	@ERP( name = "message_unread" )
	public Boolean messageUnread;

	//opp_info_loan_amnt
	@ERP( name = "opp_info_loan_amnt" )
	public Integer oppInfoLoanAmnt;

	//phone
	public String phone;

	//web_response
	@ERP( name = "web_response" )
	public String webResponse;

	//mls
	public String mls;

	//current_renewal_date
	@Temporal(TemporalType.TIMESTAMP)
	@ERP( name = "current_renewal_date" )
	public Date currentRenewalDate;

	//partner_address_email
	@ERP( name = "partner_address_email" )
	public String partnerAddressEmail;

	//is_commercial
	@ERP( name = "is_commercial" )
	public Boolean isCommercial;

	//distance_to_major_center
	@ERP( name = "distance_to_major_center" )
	public Double distanceToMajorCenter;

	//function
	public String function;

	//age
	public Integer age;

	//contact_name
	@ERP( name = "contact_name" )
	public String contactName;

	//date_open
	@ERP( name = "date_open" )
	public Date dateOpen;

	//internal_note
	@Column(length=9999)
	@ERP( name = "internal_note" )
	public String internalNote;

	//volume_bonus_amount
	@ERP( name = "volume_bonus_amount" )
	public Double volumeBonusAmount;

	//is_construction_mortgage
	@ERP( name = "is_construction_mortgage" )
	public Boolean isConstructionMortgage;

	//amortization
	public Double amortization;

	//ref2
	@ERP( name = "ref2" )
	public String ref2;

	//date_action_next
	@ERP( name = "date_action_next" )
	public Date dateActionNext;

	//total_one_time_fees
	@ERP( name = "total_one_time_fees" )
	public Double totalOneTimeFees;

	//down_payment_amount
	@ERP( name = "down_payment_amount" )
	public Double downpaymentAmount;

	//rate
	public Double rate;

	//base_commission
	@ERP( name = "base_commission" )
	public Double baseCommission;

	//insurerfee
	public Double insurerfee;

	//street2
	@ERP( name = "street2" )
	public String street2;

	//zip
	public String zip;

	//commitment_fee
	@ERP( name = "commitment_fee" )
	public Double commitmentFee;

	//additional_amount
	@ERP( name = "additional_amount" )
	public Double additionalAmount;
	
	//credit_story
	@Column(length=9999)
	@ERP( name = "credit_story" )
	public String creditStory;

	//closing_date
	@Temporal(TemporalType.TIMESTAMP)
	@ERP( name = "closing_date" )
	public Date closingDate;

	//considered_cites
	@ERP( name = "considered_cites" )
	public String consideredCites;

	//morweb_filogix
	@ERP( name = "morweb_filogix" )
	public String morwebFilogix;

	//is_eight_plex
	@ERP( name = "is_eight_plex" )
	public Boolean isEightPlex;

	//maximum_amount
	@ERP( name = "maximum_amount" )
	public Boolean maximumAmount;
		
	//internal_notes
	@Column(length=9999)
	@ERP( name = "internal_notes" )
	public String internalNotes;

	//property_value
	@ERP( name = "property_value" )
	public Double propertyValue;

	//plan
	public String plan;

	//desired_amortization
	@ERP( name = "desired_amortization" )
	public Integer desiredAmortization;

	//address
	public String address;

	//is_six_plex
	@ERP( name = "is_six_plex" )
	public Boolean isSixPlex;

	//borrowed_amount
	@ERP( name = "borrowed_amount" )
	public Double borrowedAmount;
	
	//percent variable
	@ERP( name = "percent_variable" )
	public Double percentVariable;
	

	//has self employed income
	public Boolean hasSelfEmployedIncome;

	/*********************************selection type******************************/
	//mortgage_type
	@ERP( type = OpenERPType.selection, name = "mortgage_type" )
	public String mortgageType;

	//preapproved_im_looking_fora
	@ERP( type = OpenERPType.selection, name = "preapproved_im_looking_fora" )
	public String preapprovedImLookingFora;

	//buy_new_vehicle
	@ERP( type = OpenERPType.selection, name = "buy_new_vehicle" )
	public String buyNewVehicle;

	//charge_on_title
	@ERP( type = OpenERPType.selection, name = "charge_on_title" )
	public String chargeOnTitle;

	//future_family
	@ERP( type = OpenERPType.selection, name = "future_family" )
	public String futureFamily;

	//desired_mortgage_type
	@ERP( type = OpenERPType.selection, name = "desired_mortgage_type" )
	public String desiredMortgageType;

	//desired_term
	@ERP( type = OpenERPType.selection, name = "desired_term" )
	public String desiredTerm;

	//priority
	@ERP( type = OpenERPType.selection, name = "priority" )
	public String priority;

	//op_info_type
	@ERP( type = OpenERPType.selection, name = "op_info_type" )
	public String opInfoType;

	//income_decreased_worried
	@ERP( type = OpenERPType.selection, name = "income_decreased_worried" )
	public String incomeDecreasedWorried;

	//garage_type
	@ERP( type = OpenERPType.selection, name = "garage_type" )
	public String garageType;

	//product_type
	@ERP( type = OpenERPType.selection, name = "product_type" )
	public String productType;

	//job_5_years
	@ERP( type = OpenERPType.selection, name = "job_5_years" )
	public String job5Years;

	//property_type
	@ERP( type = OpenERPType.selection, name = "property_type" )
	public String propertyType;

	//source_of_borrowing
	@ERP( type = OpenERPType.selection, name = "source_of_borrowing" )
	public String sourceOfBorrowing;

	//desired_product_type
	@ERP( type = OpenERPType.selection, name = "desired_product_type" )
	public String desiredProductType;

	//heating
	@ERP( type = OpenERPType.selection, name = "heating" )
	public String heating;

	//term_rate
	@ERP( type = OpenERPType.selection, name = "term_rate" )
	public String termRate;

	//property_style
	@ERP( type = OpenERPType.selection, name = "property_style" )
	public String propertyStyle;

	//garage_size
	@ERP( type = OpenERPType.selection, name = "garage_size" )
	public String garageSize;

	//approvedimlookingfora
	@ERP( type = OpenERPType.selection, name = "approvedimlookingfora" )
	public String approvedimlookingfora;

	//down_payment_coming_from
	@ERP( type = OpenERPType.selection, name = "down_payment_coming_from" )
	public String downPaymentComingFrom;

	//insurer
	@ERP( type = OpenERPType.selection, name = "insurer" )
	public String insurer;

	//sewage
	@ERP( type = OpenERPType.selection, name = "sewage" )
	public String sewage;

	//lifestyle_change
	@ERP( type = OpenERPType.selection, name = "lifestyle_change" )
	public String lifestyleChange;

	//opp_info_type
	@ERP( type = OpenERPType.selection, name = "opp_info_type" )
	public String oppInfoType;

	//financial_risk_taker
	@ERP( type = OpenERPType.selection, name = "financial_risk_taker" )
	public String financialRiskTaker;

	//what_is_your_lending_goal
	@ERP( type = OpenERPType.selection, name = "what_is_your_lending_goal" )
	public String whatIsYourLendingGoal;

	//water
	@ERP( type = OpenERPType.selection, name = "water" )
	public String water;

	//trigger
	@ERP( type = OpenERPType.selection, name = "trigger" )
	public String trigger;

	//looking_to_approved
	@ERP( type = OpenERPType.selection, name = "looking_to_approved" )
	public String lookingToApproved;

	//current_mortgage_type
	@ERP( type = OpenERPType.selection, name = "current_mortgage_type" )
	public String currentMortgageType;

	//refinancelookingfora
	@ERP( type = OpenERPType.selection, name = "refinancelookingfora" )
	public String refinancelookingfora;

	//living_in_property
	@ERP( type = OpenERPType.selection, name = "living_in_property" )
	public String livingInProperty;

	//property_less_then_5_years
	@ERP( type = OpenERPType.selection, name = "property_less_then_5_years" )
	public String propertyLessThen5Years;

	//apartment_style
	@ERP( type = OpenERPType.selection, name = "apartment_style" )
	public String apartmentStyle;

	//type
	@ERP( type = OpenERPType.selection, name = "type" )
	public String type;

	//looking_to_refinance
	@ERP( type = OpenERPType.selection, name = "looking_to_refinance" )
	public String lookingToRefinance;

	//open_closed
	@ERP( type = OpenERPType.selection, name = "open_closed" )
	public String openClosed;

	//renter_pay_heating
	@ERP( type = OpenERPType.selection, name = "renter_pay_heating" )
	public String renterPayHeating;

	//state
	@ERP( type = OpenERPType.selection, name = "state" )
	public String state;

	//building_funds
	@ERP( type = OpenERPType.selection, name = "building_funds" )
	public String buildingFunds;

	//preferred_number
	@ERP( type = OpenERPType.selection, name = "preferred_number" )
	public String preferredNumber;

	//term
	@ERP( type = OpenERPType.selection, name = "term" )
	public String term;

	/*********************************one2many type******************************/

	//many to one : mapping table : product_product(com.syml.domain.Product)-->opportunity_id
	@ERP(name="product_ids", type = OpenERPType.one2many )
	@JsonIgnore
	@Transient
	public List<Integer> productIds = new ArrayList<Integer>();

	//many to one : mapping table : applicant_record(com.syml.domain.Applicant)-->applicant_id
	@ERP(name="applicant_record_line", type = OpenERPType.one2many )
	@JsonIgnore
	@Transient
	public List<Integer> applicantRecordLine = new ArrayList<Integer>();

	// This Variable was added as a many to many replacement of applicant_record_line
	//many to many : mapping table : applicant_record(com.syml.domain.Applicant)-->applicant_id
	@ERP(name="app_rec_ids", type = OpenERPType.many2many )
	@JsonIgnore
	@Transient
	public List<Integer> appRecIds = new ArrayList<Integer>();
		
	//many to one : mapping table : deal(com.syml.domain.Note)-->opportunity_id
	@ERP(name="deal_ids", type = OpenERPType.one2many )
	@JsonIgnore
	@Transient
	public List<Integer> dealIds = new ArrayList<Integer>();

	/*********************************many2one type******************************/

	//many to one : mapping table : res_partner(com.syml.domain.Lender)
	@ERP(name="partner_id", type = OpenERPType.many2one )
	@JsonIgnore
	public Integer partnerId;

	//many to one : mapping table : res_partner(com.syml.domain.Lender)
	@ERP(name="referral_source", type = OpenERPType.many2one )
	@JsonIgnore
	public Integer referralSource;

	//many to one : mapping table : res_partner(com.syml.domain.Lender)
	@ERP(name="final_lender", type = OpenERPType.many2one )
	@JsonIgnore
	public Integer finalLender;

	//many to one : mapping table : res_partner(com.syml.domain.Lender)
	@ERP(name="lead_source", type = OpenERPType.many2one )
	@JsonIgnore
	public Integer leadSource;

	//many to one : mapping table : res_partner(com.syml.domain.Lender)
	@ERP(name="realtor", type = OpenERPType.many2one )
	@JsonIgnore
	public Integer realtor;

	//many to one : mapping table : res_partner(com.syml.domain.Lender)
	@ERP(name="lender", type = OpenERPType.many2one )
	@JsonIgnore
	public Integer lender;

//	//many to one : mapping table : product_product(com.syml.domain.Product)
//	@ERP(name="selected_product", type = OpenERPType.many2one )
//	@JsonIgnore
//	public Integer selectedProduct;
	
	//desired_amortization
	@ERP( name = "selected_product" )
	public Integer selectedProduct;

	//many to one : mapping table : res_partner(com.syml.domain.Lender)
	@ERP(name="referred_source", type = OpenERPType.many2one )
	@JsonIgnore
	public Integer referredSource;

	@Transient
	public List<Applicant> applicants;
	
	public boolean wfgDeal;


	public void setRenovationValue(Double renovationValue){
		this.renovationValue = renovationValue;
	}

	public Double getRenovationValue(){
		return this.renovationValue;
	}

	public void setIsCoOperativeHousing(Boolean isCoOperativeHousing){
		this.isCoOperativeHousing = isCoOperativeHousing;
	}

	public Boolean getIsCoOperativeHousing(){
		return this.isCoOperativeHousing;
	}

	public void setMonthlyRentalIncome(Double monthlyRentalIncome){
		this.monthlyRentalIncome = monthlyRentalIncome;
	}

	public Double getMonthlyRentalIncome(){
		return this.monthlyRentalIncome;
	}

	public void setMortgageType(String mortgageType){
		this.mortgageType = mortgageType;
	}

	public String getMortgageType(){
		return this.mortgageType;
	}

	public void setPreapprovedImLookingFora(String preapprovedImLookingFora){
		this.preapprovedImLookingFora = preapprovedImLookingFora;
	}

	public String getPreapprovedImLookingFora(){
		return this.preapprovedImLookingFora;
	}

	public void setGDS(Double GDS){
		this.GDS = GDS;
	}

	public Double getGDS(){
		return this.GDS;
	}

	public void setIsUpdatedToUA(Boolean isUpdatedToUA){
		this.isUpdatedToUA = isUpdatedToUA;
	}

	public Boolean getIsUpdatedToUA(){
		return this.isUpdatedToUA;
	}

	public void setBrokerFee(Double brokerFee){
		this.brokerFee = brokerFee;
	}

	public Double getBrokerFee(){
		return this.brokerFee;
	}

	public void setInsurerref(String insurerref){
		this.insurerref = insurerref;
	}

	public String getInsurerref(){
		return this.insurerref;
	}

	public void setSpouse(String spouse){
		this.spouse = spouse;
	}

	public String getSpouse(){
		return this.spouse;
	}

	public void setIsAgriculturalLess10Acres(Boolean isAgriculturalLess10Acres){
		this.isAgriculturalLess10Acres = isAgriculturalLess10Acres;
	}

	public Boolean getIsAgriculturalLess10Acres(){
		return this.isAgriculturalLess10Acres;
	}

	public void setEmailFrom(String emailFrom){
		this.emailFrom = emailFrom;
	}

	public String getEmailFrom(){
		return this.emailFrom;
	}

	public void setIsLifeLeasedProperty(Boolean isLifeLeasedProperty){
		this.isLifeLeasedProperty = isLifeLeasedProperty;
	}

	public Boolean getIsLifeLeasedProperty(){
		return this.isLifeLeasedProperty;
	}

	public void setBuyNewVehicle(String buyNewVehicle){
		this.buyNewVehicle = buyNewVehicle;
	}

	public String getBuyNewVehicle(){
		return this.buyNewVehicle;
	}

	public void setDateActionLast(Date dateActionLast){
		this.dateActionLast = dateActionLast;
	}

	public Date getDateActionLast(){
		return this.dateActionLast;
	}

	public void setPartnerId(Integer partnerId){
		this.partnerId = partnerId;
	}

	public Integer getPartnerId(){
		return this.partnerId;
	}

	public void setIsMobileHomes(Boolean isMobileHomes){
		this.isMobileHomes = isMobileHomes;
	}

	public Boolean getIsMobileHomes(){
		return this.isMobileHomes;
	}

	public void setIsAgeRestricted(Boolean isAgeRestricted){
		this.isAgeRestricted = isAgeRestricted;
	}

	public Boolean getIsAgeRestricted(){
		return this.isAgeRestricted;
	}

	public void setMonthlyPayment(Double monthlyPayment){
		this.monthlyPayment = monthlyPayment;
	}

	public Double getMonthlyPayment(){
		return this.monthlyPayment;
	}

	public void setReferralSource(Integer referralSource){
		this.referralSource = referralSource;
	}

	public Integer getReferralSource(){
		return this.referralSource;
	}

	public void setSecondaryFinancingAmount(Double secondaryFinancingAmount){
		this.secondaryFinancingAmount = secondaryFinancingAmount;
	}

	public Double getSecondaryFinancingAmount(){
		return this.secondaryFinancingAmount;
	}

	public void setIsAgricultural(Boolean isAgricultural){
		this.isAgricultural = isAgricultural;
	}

	public Boolean getIsAgricultural(){
		return this.isAgricultural;
	}

	public void setChargeOnTitle(String chargeOnTitle){
		this.chargeOnTitle = chargeOnTitle;
	}

	public String getChargeOnTitle(){
		return this.chargeOnTitle;
	}

	public void setIsRentalProperty(Boolean isRentalProperty){
		this.isRentalProperty = isRentalProperty;
	}

	public Boolean getIsRentalProperty(){
		return this.isRentalProperty;
	}

	public void setTotalCompAmount(Double totalCompAmount){
		this.totalCompAmount = totalCompAmount;
	}

	public Double getTotalCompAmount(){
		return this.totalCompAmount;
	}

	public void setDateCreatedCoApplicant(Date dateCreatedCoApplicant){
		this.dateCreatedCoApplicant = dateCreatedCoApplicant;
	}

	public Date getDateCreatedCoApplicant(){
		return this.dateCreatedCoApplicant;
	}

	public void setApplicationNo(String applicationNo){
		this.applicationNo = applicationNo;
	}

	public String getApplicationNo(){
		return this.applicationNo;
	}

	public void setLotSize(String lotSize){
		this.lotSize = lotSize;
	}

	public String getLotSize(){
		return this.lotSize;
	}

	public void setIsRoomingHouses(Boolean isRoomingHouses){
		this.isRoomingHouses = isRoomingHouses;
	}

	public Boolean getIsRoomingHouses(){
		return this.isRoomingHouses;
	}

	public void setCondoFees(Double condoFees){
		this.condoFees = condoFees;
	}

	public Double getCondoFees(){
		return this.condoFees;
	}

	public void setLot(String lot){
		this.lot = lot;
	}

	public String getLot(){
		return this.lot;
	}

	public void setReferred(String referred){
		this.referred = referred;
	}

	public String getReferred(){
		return this.referred;
	}

	public void setMessageIsFollower(Boolean messageIsFollower){
		this.messageIsFollower = messageIsFollower;
	}

	public Boolean getMessageIsFollower(){
		return this.messageIsFollower;
	}

	public void setBaseCompAmount(Double baseCompAmount){
		this.baseCompAmount = baseCompAmount;
	}

	public Double getBaseCompAmount(){
		return this.baseCompAmount;
	}

	public void setApplicantLastName(String applicantLastName){
		this.applicantLastName = applicantLastName;
	}

	public String getApplicantLastName(){
		return this.applicantLastName;
	}

	public void setIsDuplex(Boolean isDuplex){
		this.isDuplex = isDuplex;
	}

	public Boolean getIsDuplex(){
		return this.isDuplex;
	}

	public void setTotalMortgageAmount(Double totalMortgageAmount){
		this.totalMortgageAmount = totalMortgageAmount;
	}

	public Double getTotalMortgageAmount(){
		return this.totalMortgageAmount;
	}

	public void setExistingEquityAmount(Double existingEquityAmount){
		this.existingEquityAmount = existingEquityAmount;
	}

	public Double getExistingEquityAmount(){
		return this.existingEquityAmount;
	}

	public void setCurrentMortgageAmount(Double currentMortgageAmount){
		this.currentMortgageAmount = currentMortgageAmount;
	}

	public Double getCurrentMortgageAmount(){
		return this.currentMortgageAmount;
	}

	public void setFutureFamily(String futureFamily){
		this.futureFamily = futureFamily;
	}

	public String getFutureFamily(){
		return this.futureFamily;
	}

	public void setWriteDate(Date writeDate){
		this.writeDate = writeDate;
	}

	public Date getWriteDate(){
		return this.writeDate;
	}

	public void setConditionOfFinancingDate(Date conditionOfFinancingDate){
		this.conditionOfFinancingDate = conditionOfFinancingDate;
	}

	public Date getConditionOfFinancingDate(){
		return this.conditionOfFinancingDate;
	}

	public void setFinalLender(Integer finalLender){
		this.finalLender = finalLender;
	}

	public Integer getFinalLender(){
		return this.finalLender;
	}

	public void setDesiredMortgageType(String desiredMortgageType){
		this.desiredMortgageType = desiredMortgageType;
	}

	public String getDesiredMortgageType(){
		return this.desiredMortgageType;
	}

	public void setIsHighRatio2ndHome(Boolean isHighRatio2ndHome){
		this.isHighRatio2ndHome = isHighRatio2ndHome;
	}

	public Boolean getIsHighRatio2ndHome(){
		return this.isHighRatio2ndHome;
	}

	public void setMarketingCompAmount(Double marketingCompAmount){
		this.marketingCompAmount = marketingCompAmount;
	}

	public Double getMarketingCompAmount(){
		return this.marketingCompAmount;
	}

	public void setDrawsRequired(Integer drawsRequired){
		this.drawsRequired = drawsRequired;
	}

	public Integer getDrawsRequired(){
		return this.drawsRequired;
	}

	public void setIsCondo(Boolean isCondo){
		this.isCondo = isCondo;
	}

	public Boolean getIsCondo(){
		return this.isCondo;
	}

	public void setLenderResponse(Date lenderResponse){
		this.lenderResponse = lenderResponse;
	}

	public Date getLenderResponse(){
		return this.lenderResponse;
	}

	public void setRef(String ref){
		this.ref = ref;
	}

	public String getRef(){
		return this.ref;
	}

	public void setIsBoardingHouses(Boolean isBoardingHouses){
		this.isBoardingHouses = isBoardingHouses;
	}

	public Boolean getIsBoardingHouses(){
		return this.isBoardingHouses;
	}

	public void setIsGrowOps(Boolean isGrowOps){
		this.isGrowOps = isGrowOps;
	}

	public Boolean getIsGrowOps(){
		return this.isGrowOps;
	}

	public void setNonIncomeQualifer(Boolean nonIncomeQualifer){
		this.nonIncomeQualifer = nonIncomeQualifer;
	}

	public Boolean getNonIncomeQualifer(){
		return this.nonIncomeQualifer;
	}

	public void setOutbuildingsValue(Double outbuildingsValue){
		this.outbuildingsValue = outbuildingsValue;
	}

	public Double getOutbuildingsValue(){
		return this.outbuildingsValue;
	}

	public void setIsModularHomes(Boolean isModularHomes){
		this.isModularHomes = isModularHomes;
	}

	public Boolean getIsModularHomes(){
		return this.isModularHomes;
	}

	public void setExpectedClosingDate(Date expectedClosingDate){
		this.expectedClosingDate = expectedClosingDate;
	}

	public Date getExpectedClosingDate(){
		return this.expectedClosingDate;
	}

	public void setExistingLender(String existingLender){
		this.existingLender = existingLender;
	}

	public String getExistingLender(){
		return this.existingLender;
	}

	public void setFutureMortgage(String futureMortgage){
		this.futureMortgage = futureMortgage;
	}

	public String getFutureMortgage(){
		return this.futureMortgage;
	}

	public void setMarketingAuto(String marketingAuto){
		this.marketingAuto = marketingAuto;
	}

	public String getMarketingAuto(){
		return this.marketingAuto;
	}

	public void setVerifyProduct(Boolean verifyProduct){
		this.verifyProduct = verifyProduct;
	}

	public Boolean getVerifyProduct(){
		return this.verifyProduct;
	}

	public void setPersonalCashAmount(Double personalCashAmount){
		this.personalCashAmount = personalCashAmount;
	}

	public Double getPersonalCashAmount(){
		return this.personalCashAmount;
	}

	public void setNewHomeWarranty(String newHomeWarranty){
		this.newHomeWarranty = newHomeWarranty;
	}

	public String getNewHomeWarranty(){
		return this.newHomeWarranty;
	}

	public void setIsCountryResidential(Boolean isCountryResidential){
		this.isCountryResidential = isCountryResidential;
	}

	public Boolean getIsCountryResidential(){
		return this.isCountryResidential;
	}

	public void setLtv(Double ltv){
		this.ltv = ltv;
	}

	public Double getLtv(){
		return this.ltv;
	}

	public void setLenderRequiresInsurance(Boolean lenderRequiresInsurance){
		this.lenderRequiresInsurance = lenderRequiresInsurance;
	}

	public Boolean getLenderRequiresInsurance(){
		return this.lenderRequiresInsurance;
	}

	public void setInternalNoteProperty(String internalNoteProperty){
		this.internalNoteProperty = internalNoteProperty;
	}

	public String getInternalNoteProperty(){
		return this.internalNoteProperty;
	}

	public void setConcernsAddressedCheck(Boolean concernsAddressedCheck){
		this.concernsAddressedCheck = concernsAddressedCheck;
	}

	public Boolean getConcernsAddressedCheck(){
		return this.concernsAddressedCheck;
	}

	public void setDesiredTerm(String desiredTerm){
		this.desiredTerm = desiredTerm;
	}

	public String getDesiredTerm(){
		return this.desiredTerm;
	}

	public void setPriority(String priority){
		this.priority = priority;
	}

	public String getPriority(){
		return this.priority;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return this.description;
	}

	public void setProvince(String province){
		this.province = province;
	}

	public String getProvince(){
		return this.province;
	}

	public void setPlannedRevenue(Double plannedRevenue){
		this.plannedRevenue = plannedRevenue;
	}

	public Double getPlannedRevenue(){
		return this.plannedRevenue;
	}

	public void setFax(String fax){
		this.fax = fax;
	}

	public String getFax(){
		return this.fax;
	}

	public void setOpInfoType(String opInfoType){
		this.opInfoType = opInfoType;
	}

	public String getOpInfoType(){
		return this.opInfoType;
	}

	public void setUserLogin(String userLogin){
		this.userLogin = userLogin;
	}

	public String getUserLogin(){
		return this.userLogin;
	}

	public void setDateAction(Date dateAction){
		this.dateAction = dateAction;
	}

	public Date getDateAction(){
		return this.dateAction;
	}

	public void setEmailCc(String emailCc){
		this.emailCc = emailCc;
	}

	public String getEmailCc(){
		return this.emailCc;
	}

	public void setIncomeDecreasedWorried(String incomeDecreasedWorried){
		this.incomeDecreasedWorried = incomeDecreasedWorried;
	}

	public String getIncomeDecreasedWorried(){
		return this.incomeDecreasedWorried;
	}

	public void setProductIds(List<Integer> productIds){
		this.productIds = productIds;
	}

	public List<Integer> getProductIds(){
		return this.productIds;
	}

	public void setExistingMortgage(String existingMortgage){
		this.existingMortgage = existingMortgage;
	}

	public String getExistingMortgage(){
		return this.existingMortgage;
	}

	public void setMinHeatFee(Double minHeatFee){
		this.minHeatFee = minHeatFee;
	}

	public Double getMinHeatFee(){
		return this.minHeatFee;
	}

	public void setLeadSource(Integer leadSource){
		this.leadSource = leadSource;
	}

	public Integer getLeadSource(){
		return this.leadSource;
	}

	public void setQualifiedCheck(Boolean qualifiedCheck){
		this.qualifiedCheck = qualifiedCheck;
	}

	public Boolean getQualifiedCheck(){
		return this.qualifiedCheck;
	}

	public void setPostalCode(String postalCode){
		this.postalCode = postalCode;
	}

	public String getPostalCode(){
		return this.postalCode;
	}

	public void setActive(Boolean active){
		this.active = active;
	}

	public Boolean getActive(){
		return this.active;
	}

	public void setPropertyTaxes(Double propertyTaxes){
		this.propertyTaxes = propertyTaxes;
	}

	public Double getPropertyTaxes(){
		return this.propertyTaxes;
	}

	public void setIsFloatingHomes(Boolean isFloatingHomes){
		this.isFloatingHomes = isFloatingHomes;
	}

	public Boolean getIsFloatingHomes(){
		return this.isFloatingHomes;
	}

	public void setMobile(String mobile){
		this.mobile = mobile;
	}

	public String getMobile(){
		return this.mobile;
	}

	public void setOppInfoRate(Integer oppInfoRate){
		this.oppInfoRate = oppInfoRate;
	}

	public Integer getOppInfoRate(){
		return this.oppInfoRate;
	}

	public void setChargesBehindAmount(Double chargesBehindAmount){
		this.chargesBehindAmount = chargesBehindAmount;
	}

	public Double getChargesBehindAmount(){
		return this.chargesBehindAmount;
	}

	public void setApplicationDate(Date applicationDate){
		this.applicationDate = applicationDate;
	}

	public Date getApplicationDate(){
		return this.applicationDate;
	}

	public void setUserEmail(String userEmail){
		this.userEmail = userEmail;
	}

	public String getUserEmail(){
		return this.userEmail;
	}

	public void setRealtor(Integer realtor){
		this.realtor = realtor;
	}

	public Integer getRealtor(){
		return this.realtor;
	}

	public void setGarageType(String garageType){
		this.garageType = garageType;
	}

	public String getGarageType(){
		return this.garageType;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setDesiredMortgageAmount(Double desiredMortgageAmount){
		this.desiredMortgageAmount = desiredMortgageAmount;
	}

	public Double getDesiredMortgageAmount(){
		return this.desiredMortgageAmount;
	}

	public void setVolumeCommission(Double volumeCommission){
		this.volumeCommission = volumeCommission;
	}

	public Double getVolumeCommission(){
		return this.volumeCommission;
	}

	public void setPartnerAddressName(String partnerAddressName){
		this.partnerAddressName = partnerAddressName;
	}

	public String getPartnerAddressName(){
		return this.partnerAddressName;
	}

	public void setProductType(String productType){
		this.productType = productType;
	}

	public String getProductType(){
		return this.productType;
	}

	public void setIsLeasedLand(Boolean isLeasedLand){
		this.isLeasedLand = isLeasedLand;
	}

	public Boolean getIsLeasedLand(){
		return this.isLeasedLand;
	}

	public void setJob5Years(String job5Years){
		this.job5Years = job5Years;
	}

	public String getJob5Years(){
		return this.job5Years;
	}

	public void setPropertyType(String propertyType){
		this.propertyType = propertyType;
	}

	public String getPropertyType(){
		return this.propertyType;
	}

	public void setSourceOfBorrowing(String sourceOfBorrowing){
		this.sourceOfBorrowing = sourceOfBorrowing;
	}

	public String getSourceOfBorrowing(){
		return this.sourceOfBorrowing;
	}

	public void setSquareFootage(Integer squareFootage){
		this.squareFootage = squareFootage;
	}

	public Integer getSquareFootage(){
		return this.squareFootage;
	}

	public void setColor(Integer color){
		this.color = color;
	}

	public Integer getColor(){
		return this.color;
	}

	public void setEmailWork(String emailWork){
		this.emailWork = emailWork;
	}

	public String getEmailWork(){
		return this.emailWork;
	}

	public void setIsaSmallCentre(Boolean isaSmallCentre){
		this.isaSmallCentre = isaSmallCentre;
	}

	public Boolean getIsaSmallCentre(){
		return this.isaSmallCentre;
	}

	public void setDesiredProductType(String desiredProductType){
		this.desiredProductType = desiredProductType;
	}

	public String getDesiredProductType(){
		return this.desiredProductType;
	}

	public void setSaleOfExistingAmount(Double saleOfExistingAmount){
		this.saleOfExistingAmount = saleOfExistingAmount;
	}

	public Double getSaleOfExistingAmount(){
		return this.saleOfExistingAmount;
	}

	public void setHeating(String heating){
		this.heating = heating;
	}

	public String getHeating(){
		return this.heating;
	}

	public void setIsNonConvConstruction(Boolean isNonConvConstruction){
		this.isNonConvConstruction = isNonConvConstruction;
	}

	public Boolean getIsNonConvConstruction(){
		return this.isNonConvConstruction;
	}

	public void setPartnerName(String partnerName){
		this.partnerName = partnerName;
	}

	public String getPartnerName(){
		return this.partnerName;
	}

	public void setMessageSummary(String messageSummary){
		this.messageSummary = messageSummary;
	}

	public String getMessageSummary(){
		return this.messageSummary;
	}

	public void setTermRate(String termRate){
		this.termRate = termRate;
	}

	public String getTermRate(){
		return this.termRate;
	}

	public void setProcessPresntedutioCheck(Boolean processPresntedutioCheck){
		this.processPresntedutioCheck = processPresntedutioCheck;
	}

	public Boolean getProcessPresntedutioCheck(){
		return this.processPresntedutioCheck;
	}

	public void setPropertyStyle(String propertyStyle){
		this.propertyStyle = propertyStyle;
	}

	public String getPropertyStyle(){
		return this.propertyStyle;
	}

	public void setGarageSize(String garageSize){
		this.garageSize = garageSize;
	}

	public String getGarageSize(){
		return this.garageSize;
	}

	public void setStreet(String street){
		this.street = street;
	}

	public String getStreet(){
		return this.street;
	}

	public void setTitleAction(String titleAction){
		this.titleAction = titleAction;
	}

	public String getTitleAction(){
		return this.titleAction;
	}

	public void setApprovedimlookingfora(String approvedimlookingfora){
		this.approvedimlookingfora = approvedimlookingfora;
	}

	public String getApprovedimlookingfora(){
		return this.approvedimlookingfora;
	}

	public void setOppInfoStartDate(Date oppInfoStartDate){
		this.oppInfoStartDate = oppInfoStartDate;
	}

	public Date getOppInfoStartDate(){
		return this.oppInfoStartDate;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return this.city;
	}

	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}

	public Date getCreateDate(){
		return this.createDate;
	}

	public void setHasChargesBehind(Boolean hasChargesBehind){
		this.hasChargesBehind = hasChargesBehind;
	}

	public Boolean getHasChargesBehind(){
		return this.hasChargesBehind;
	}

	public void setRenewaldate(Date renewaldate){
		this.renewaldate = renewaldate;
	}

	public Date getRenewaldate(){
		return this.renewaldate;
	}

	public void setLenderName(String lenderName){
		this.lenderName = lenderName;
	}

	public String getLenderName(){
		return this.lenderName;
	}

	public void setDayClose(Double dayClose){
		this.dayClose = dayClose;
	}

	public Double getDayClose(){
		return this.dayClose;
	}

	public void setDateClosed(Date dateClosed){
		this.dateClosed = dateClosed;
	}

	public Date getDateClosed(){
		return this.dateClosed;
	}

	public void setDownPaymentComingFrom(String downPaymentComingFrom){
		this.downPaymentComingFrom = downPaymentComingFrom;
	}

	public String getDownPaymentComingFrom(){
		return this.downPaymentComingFrom;
	}

	public void setDateDeadline(Date dateDeadline){
		this.dateDeadline = dateDeadline;
	}

	public Date getDateDeadline(){
		return this.dateDeadline;
	}

	public void setApproachedCheck(Boolean approachedCheck){
		this.approachedCheck = approachedCheck;
	}

	public Boolean getApproachedCheck(){
		return this.approachedCheck;
	}

	public void setDayOpen(Double dayOpen){
		this.dayOpen = dayOpen;
	}

	public Double getDayOpen(){
		return this.dayOpen;
	}

	public void setInsurer(String insurer){
		this.insurer = insurer;
	}

	public String getInsurer(){
		return this.insurer;
	}

	public void setSewage(String sewage){
		this.sewage = sewage;
	}

	public String getSewage(){
		return this.sewage;
	}

	public void setIsUninsuredConv2ndHome(Boolean isUninsuredConv2ndHome){
		this.isUninsuredConv2ndHome = isUninsuredConv2ndHome;
	}

	public Boolean getIsUninsuredConv2ndHome(){
		return this.isUninsuredConv2ndHome;
	}

	public void setIsFourPlex(Boolean isFourPlex){
		this.isFourPlex = isFourPlex;
	}

	public Boolean getIsFourPlex(){
		return this.isFourPlex;
	}

	public void setLifestyleChange(String lifestyleChange){
		this.lifestyleChange = lifestyleChange;
	}

	public String getLifestyleChange(){
		return this.lifestyleChange;
	}

	public void setPendingApplicationCheck(Boolean pendingApplicationCheck){
		this.pendingApplicationCheck = pendingApplicationCheck;
	}

	public Boolean getPendingApplicationCheck(){
		return this.pendingApplicationCheck;
	}

	public void setLenderFee(Double lenderFee){
		this.lenderFee = lenderFee;
	}

	public Double getLenderFee(){
		return this.lenderFee;
	}

	public void setIsRawLand(Boolean isRawLand){
		this.isRawLand = isRawLand;
	}

	public Boolean getIsRawLand(){
		return this.isRawLand;
	}

	public void setIsCottageRecProperty(Boolean isCottageRecProperty){
		this.isCottageRecProperty = isCottageRecProperty;
	}

	public Boolean getIsCottageRecProperty(){
		return this.isCottageRecProperty;
	}

	public void setRrspAmount(Double rrspAmount){
		this.rrspAmount = rrspAmount;
	}

	public Double getRrspAmount(){
		return this.rrspAmount;
	}

	public void setPurchasePrice(Double purchasePrice){
		this.purchasePrice = purchasePrice;
	}

	public Double getPurchasePrice(){
		return this.purchasePrice;
	}

	public void setOppInfoType(String oppInfoType){
		this.oppInfoType = oppInfoType;
	}

	public String getOppInfoType(){
		return this.oppInfoType;
	}

	public void setIsMilitary(Boolean isMilitary){
		this.isMilitary = isMilitary;
	}

	public Boolean getIsMilitary(){
		return this.isMilitary;
	}

	public void setWorkPhone(String workPhone){
		this.workPhone = workPhone;
	}

	public String getWorkPhone(){
		return this.workPhone;
	}

	public void setFinancialRiskTaker(String financialRiskTaker){
		this.financialRiskTaker = financialRiskTaker;
	}

	public String getFinancialRiskTaker(){
		return this.financialRiskTaker;
	}

	public void setLender(Integer lender){
		this.lender = lender;
	}

	public Integer getLender(){
		return this.lender;
	}

	public void setTrailerCompAmount(Double trailerCompAmount){
		this.trailerCompAmount = trailerCompAmount;
	}

	public Double getTrailerCompAmount(){
		return this.trailerCompAmount;
	}

	public void setApplicantRecordLine(List<Integer> applicantRecordLine){
		this.applicantRecordLine = applicantRecordLine;
	}

	public List<Integer> getApplicantRecordLine(){
		return this.applicantRecordLine;
	}

	public void setAppRecIds(List<Integer> appRecIds){
		this.appRecIds = appRecIds;
	}

	public List<Integer> getAppRecIds(){
		return this.appRecIds;
	}
	
	public void setOppInfoRenewalDate(Date oppInfoRenewalDate){
		this.oppInfoRenewalDate = oppInfoRenewalDate;
	}

	public Date getOppInfoRenewalDate(){
		return this.oppInfoRenewalDate;
	}

	public void setBlock(String block){
		this.block = block;
	}

	public String getBlock(){
		return this.block;
	}

	public void setTDS(Double TDS){
		this.TDS = TDS;
	}

	public Double getTDS(){
		return this.TDS;
	}

	public void setLenderRef(String lenderRef){
		this.lenderRef = lenderRef;
	}

	public String getLenderRef(){
		return this.lenderRef;
	}

	public void setSweatEquityAmount(Double sweatEquityAmount){
		this.sweatEquityAmount = sweatEquityAmount;
	}

	public Double getSweatEquityAmount(){
		return this.sweatEquityAmount;
	}

	public void setSelectedProduct(Integer selectedProduct){
		this.selectedProduct = selectedProduct;
	}

	public Integer getSelectedProduct(){
		return this.selectedProduct;
	}

	public void setInternalNotesFinal(String internalNotesFinal){
		this.internalNotesFinal = internalNotesFinal;
	}

	public String getInternalNotesFinal(){
		return this.internalNotesFinal;
	}

	public void setOtherAmount(Double otherAmount){
		this.otherAmount = otherAmount;
	}

	public Double getOtherAmount(){
		return this.otherAmount;
	}

	public void setDealIds(List<Integer> dealIds){
		this.dealIds = dealIds;
	}

	public List<Integer> getDealIds(){
		return this.dealIds;
	}

	public void setWhatIsYourLendingGoal(String whatIsYourLendingGoal){
		this.whatIsYourLendingGoal = whatIsYourLendingGoal;
	}

	public String getWhatIsYourLendingGoal(){
		return this.whatIsYourLendingGoal;
	}

	public void setProbability(Double probability){
		this.probability = probability;
	}

	public Double getProbability(){
		return this.probability;
	}

	public void setOptOut(Boolean optOut){
		this.optOut = optOut;
	}

	public Boolean getOptOut(){
		return this.optOut;
	}

	public void setAcres(Double acres){
		this.acres = acres;
	}

	public Double getAcres(){
		return this.acres;
	}

	public void setEstimatedValueofHome(String estimatedValueofHome){
		this.estimatedValueofHome = estimatedValueofHome;
	}

	public String getEstimatedValueofHome(){
		return this.estimatedValueofHome;
	}

	public void setWater(String water){
		this.water = water;
	}

	public String getWater(){
		return this.water;
	}

	public void setTrigger(String trigger){
		this.trigger = trigger;
	}

	public String getTrigger(){
		return this.trigger;
	}

	public void setCurrentInterestRate(Double currentInterestRate){
		this.currentInterestRate = currentInterestRate;
	}

	public Double getCurrentInterestRate(){
		return this.currentInterestRate;
	}

	public void setLookingToApproved(String lookingToApproved){
		this.lookingToApproved = lookingToApproved;
	}

	public String getLookingToApproved(){
		return this.lookingToApproved;
	}

	public void setCurrentMortgageType(String currentMortgageType){
		this.currentMortgageType = currentMortgageType;
	}

	public String getCurrentMortgageType(){
		return this.currentMortgageType;
	}

	public void setRefinancelookingfora(String refinancelookingfora){
		this.refinancelookingfora = refinancelookingfora;
	}

	public String getRefinancelookingfora(){
		return this.refinancelookingfora;
	}

	public void setCashBack(Integer cashBack){
		this.cashBack = cashBack;
	}

	public Integer getCashBack(){
		return this.cashBack;
	}

	public void setPlannedCost(Double plannedCost){
		this.plannedCost = plannedCost;
	}

	public Double getPlannedCost(){
		return this.plannedCost;
	}

	public void setGiftedAmount(Double giftedAmount){
		this.giftedAmount = giftedAmount;
	}

	public Double getGiftedAmount(){
		return this.giftedAmount;
	}

	public void setIsRentalPools(Boolean isRentalPools){
		this.isRentalPools = isRentalPools;
	}

	public Boolean getIsRentalPools(){
		return this.isRentalPools;
	}

	public void setLivingInProperty(String livingInProperty){
		this.livingInProperty = livingInProperty;
	}

	public String getLivingInProperty(){
		return this.livingInProperty;
	}

	public void setIsFractionalInterests(Boolean isFractionalInterests){
		this.isFractionalInterests = isFractionalInterests;
	}

	public Boolean getIsFractionalInterests(){
		return this.isFractionalInterests;
	}

	public void setPropertyLessThen5Years(String propertyLessThen5Years){
		this.propertyLessThen5Years = propertyLessThen5Years;
	}

	public String getPropertyLessThen5Years(){
		return this.propertyLessThen5Years;
	}

	public void setMessageUnread(Boolean messageUnread){
		this.messageUnread = messageUnread;
	}

	public Boolean getMessageUnread(){
		return this.messageUnread;
	}

	public void setOppInfoLoanAmnt(Integer oppInfoLoanAmnt){
		this.oppInfoLoanAmnt = oppInfoLoanAmnt;
	}

	public Integer getOppInfoLoanAmnt(){
		return this.oppInfoLoanAmnt;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return this.phone;
	}

	public void setWebResponse(String webResponse){
		this.webResponse = webResponse;
	}

	public String getWebResponse(){
		return this.webResponse;
	}

	public void setApartmentStyle(String apartmentStyle){
		this.apartmentStyle = apartmentStyle;
	}

	public String getApartmentStyle(){
		return this.apartmentStyle;
	}

	public void setMls(String mls){
		this.mls = mls;
	}

	public String getMls(){
		return this.mls;
	}

	public void setCurrentRenewalDate(Date currentRenewalDate){
		this.currentRenewalDate = currentRenewalDate;
	}

	public Date getCurrentRenewalDate(){
		return this.currentRenewalDate;
	}

	public void setPartnerAddressEmail(String partnerAddressEmail){
		this.partnerAddressEmail = partnerAddressEmail;
	}

	public String getPartnerAddressEmail(){
		return this.partnerAddressEmail;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return this.type;
	}

	public void setIsCommercial(Boolean isCommercial){
		this.isCommercial = isCommercial;
	}

	public Boolean getIsCommercial(){
		return this.isCommercial;
	}

	public void setDistanceToMajorCenter(Double distanceToMajorCenter){
		this.distanceToMajorCenter = distanceToMajorCenter;
	}

	public Double getDistanceToMajorCenter(){
		return this.distanceToMajorCenter;
	}

	public void setFunction(String function){
		this.function = function;
	}

	public String getFunction(){
		return this.function;
	}

	public void setAge(Integer age){
		this.age = age;
	}

	public Integer getAge(){
		return this.age;
	}

	public void setContactName(String contactName){
		this.contactName = contactName;
	}

	public String getContactName(){
		return this.contactName;
	}

	public void setDateOpen(Date dateOpen){
		this.dateOpen = dateOpen;
	}

	public Date getDateOpen(){
		return this.dateOpen;
	}

	public void setLookingToRefinance(String lookingToRefinance){
		this.lookingToRefinance = lookingToRefinance;
	}

	public String getLookingToRefinance(){
		return this.lookingToRefinance;
	}

	public void setInternalNote(String internalNote){
		this.internalNote = internalNote;
	}

	public String getInternalNote(){
		return this.internalNote;
	}

	public void setVolumeBonusAmount(Double volumeBonusAmount){
		this.volumeBonusAmount = volumeBonusAmount;
	}

	public Double getVolumeBonusAmount(){
		return this.volumeBonusAmount;
	}

	public void setIsConstructionMortgage(Boolean isConstructionMortgage){
		this.isConstructionMortgage = isConstructionMortgage;
	}

	public Boolean getIsConstructionMortgage(){
		return this.isConstructionMortgage;
	}

	public void setAmortization(Double amortization){
		this.amortization = amortization;
	}

	public Double getAmortization(){
		return this.amortization;
	}

	public void setOpenClosed(String openClosed){
		this.openClosed = openClosed;
	}

	public String getOpenClosed(){
		return this.openClosed;
	}

	public void setRef2(String ref2){
		this.ref2 = ref2;
	}

	public String getRef2(){
		return this.ref2;
	}

	public void setDateActionNext(Date dateActionNext){
		this.dateActionNext = dateActionNext;
	}

	public Date getDateActionNext(){
		return this.dateActionNext;
	}

	public void setTotalOneTimeFees(Double totalOneTimeFees){
		this.totalOneTimeFees = totalOneTimeFees;
	}

	public Double getTotalOneTimeFees(){
		return this.totalOneTimeFees;
	}

	public void setRenterPayHeating(String renterPayHeating){
		this.renterPayHeating = renterPayHeating;
	}

	public String getRenterPayHeating(){
		return this.renterPayHeating;
	}

	public void setState(String state){
		this.state = state;
	}

	public String getState(){
		return this.state;
	}

	public void setDownpaymentAmount(Double downpaymentAmount){
		this.downpaymentAmount = downpaymentAmount;
	}

	public Double getDownpaymentAmount(){
		return this.downpaymentAmount;
	}

	public void setRate(Double rate){
		this.rate = rate;
	}

	public Double getRate(){
		return this.rate;
	}

	public void setBaseCommission(Double baseCommission){
		this.baseCommission = baseCommission;
	}

	public Double getBaseCommission(){
		return this.baseCommission;
	}

	public void setInsurerfee(Double insurerfee){
		this.insurerfee = insurerfee;
	}

	public Double getInsurerfee(){
		return this.insurerfee;
	}

	public void setBuildingFunds(String buildingFunds){
		this.buildingFunds = buildingFunds;
	}

	public String getBuildingFunds(){
		return this.buildingFunds;
	}

	public void setStreet2(String street2){
		this.street2 = street2;
	}

	public String getStreet2(){
		return this.street2;
	}

	public void setZip(String zip){
		this.zip = zip;
	}

	public String getZip(){
		return this.zip;
	}

	public void setPreferredNumber(String preferredNumber){
		this.preferredNumber = preferredNumber;
	}

	public String getPreferredNumber(){
		return this.preferredNumber;
	}

	public void setCommitmentFee(Double commitmentFee){
		this.commitmentFee = commitmentFee;
	}

	public Double getCommitmentFee(){
		return this.commitmentFee;
	}

	public void setCreditStory(String creditStory){
		this.creditStory = creditStory;
	}

	public String getCreditStory(){
		return this.creditStory;
	}

	public void setReferredSource(Integer referredSource){
		this.referredSource = referredSource;
	}

	public Integer getReferredSource(){
		return this.referredSource;
	}

	public void setClosingDate(Date closingDate){
		this.closingDate = closingDate;
	}

	public Date getClosingDate(){
		return this.closingDate;
	}

	public void setConsideredCites(String consideredCites){
		this.consideredCites = consideredCites;
	}

	public String getConsideredCites(){
		return this.consideredCites;
	}

	public void setMorwebFilogix(String morwebFilogix){
		this.morwebFilogix = morwebFilogix;
	}

	public String getMorwebFilogix(){
		return this.morwebFilogix;
	}

	public void setIsEightPlex(Boolean isEightPlex){
		this.isEightPlex = isEightPlex;
	}

	public Boolean getIsEightPlex(){
		return this.isEightPlex;
	}

	public void setInternalNotes(String internalNotes){
		this.internalNotes = internalNotes;
	}

	public String getInternalNotes(){
		return this.internalNotes;
	}

	public void setPropertyValue(Double propertyValue){
		this.propertyValue = propertyValue;
	}

	public Double getPropertyValue(){
		return this.propertyValue;
	}

	public void setPlan(String plan){
		this.plan = plan;
	}

	public String getPlan(){
		return this.plan;
	}

	public void setDesiredAmortization(Integer desiredAmortization){
		this.desiredAmortization = desiredAmortization;
	}

	public Integer getDesiredAmortization(){
		return this.desiredAmortization;
	}

	public void setTerm(String term){
		this.term = term;
	}

	public String getTerm(){
		return this.term;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return this.address;
	}

	public void setIsSixPlex(Boolean isSixPlex){
		this.isSixPlex = isSixPlex;
	}

	public Boolean getIsSixPlex(){
		return this.isSixPlex;
	}

	public void setBorrowedAmount(Double borrowedAmount){
		this.borrowedAmount = borrowedAmount;
	}

	public Double getBorrowedAmount(){
		return this.borrowedAmount;
	}

	public Double getAdditionalAmount() {
		return additionalAmount;
	}

	public void setAdditionalAmount(Double additionalAmount) {
		this.additionalAmount = additionalAmount;
	}

	public Boolean getMaximumAmount() {
		return maximumAmount;
	}

	public void setMaximumAmount(Boolean maximumAmount) {
		this.maximumAmount = maximumAmount;
	}

	public Boolean getMortgageInsured() {
		return mortgageInsured;
	}

	public void setMortgageInsured(Boolean mortgageInsured) {
		this.mortgageInsured = mortgageInsured;
	}

	public Double getCurrentBalance() {
		return currentBalance;
	}

	public void setCurrentBalance(Double currentBalance) {
		this.currentBalance = currentBalance;
	}

	public Double getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(Double bankAccount) {
		this.bankAccount = bankAccount;
	}

	@Override
	public String toString() {
		return "Opportunity [renovationValue=" + renovationValue
				+ ", isCoOperativeHousing=" + isCoOperativeHousing
				+ ", monthlyRentalIncome=" + monthlyRentalIncome + ", GDS="
				+ GDS + ", isUpdatedToUA=" + isUpdatedToUA + ", brokerFee="
				+ brokerFee + ", insurerref=" + insurerref + ", spouse="
				+ spouse + ", isAgriculturalLess10Acres="
				+ isAgriculturalLess10Acres + ", emailFrom=" + emailFrom
				+ ", isLifeLeasedProperty=" + isLifeLeasedProperty
				+ ", dateActionLast=" + dateActionLast + ", isMobileHomes="
				+ isMobileHomes + ", isAgeRestricted=" + isAgeRestricted
				+ ", monthlyPayment=" + monthlyPayment
				+ ", secondaryFinancingAmount=" + secondaryFinancingAmount
				+ ", isAgricultural=" + isAgricultural + ", isRentalProperty="
				+ isRentalProperty + ", totalCompAmount=" + totalCompAmount
				+ ", dateCreatedCoApplicant=" + dateCreatedCoApplicant
				+ ", applicationNo=" + applicationNo + ", lotSize=" + lotSize
				+ ", isRoomingHouses=" + isRoomingHouses + ", condoFees="
				+ condoFees + ", lot=" + lot + ", referred=" + referred
				+ ", messageIsFollower=" + messageIsFollower
				+ ", baseCompAmount=" + baseCompAmount + ", applicantLastName="
				+ applicantLastName + ", isDuplex=" + isDuplex
				+ ", totalMortgageAmount=" + totalMortgageAmount
				+ ", existingEquityAmount=" + existingEquityAmount
				+ ", currentMortgageAmount=" + currentMortgageAmount
				+ ", writeDate=" + writeDate + ", conditionOfFinancingDate="
				+ conditionOfFinancingDate + ", isHighRatio2ndHome="
				+ isHighRatio2ndHome + ", marketingCompAmount="
				+ marketingCompAmount + ", drawsRequired=" + drawsRequired
				+ ", isCondo=" + isCondo + ", lenderResponse=" + lenderResponse
				+ ", ref=" + ref + ", isBoardingHouses=" + isBoardingHouses
				+ ", isGrowOps=" + isGrowOps + ", nonIncomeQualifer="
				+ nonIncomeQualifer + ", outbuildingsValue="
				+ outbuildingsValue + ", isModularHomes=" + isModularHomes
				+ ", expectedClosingDate=" + expectedClosingDate
				+ ", existingLender=" + existingLender + ", futureMortgage="
				+ futureMortgage + ", marketingAuto=" + marketingAuto
				+ ", verifyProduct=" + verifyProduct + ", personalCashAmount="
				+ personalCashAmount + ", newHomeWarranty=" + newHomeWarranty
				+ ", isCountryResidential=" + isCountryResidential + ", ltv="
				+ ltv + ", lenderRequiresInsurance=" + lenderRequiresInsurance
				+ ", internalNoteProperty=" + internalNoteProperty
				+ ", concernsAddressedCheck=" + concernsAddressedCheck
				+ ", description=" + description + ", province=" + province
				+ ", plannedRevenue=" + plannedRevenue + ", fax=" + fax
				+ ", userLogin=" + userLogin + ", dateAction=" + dateAction
				+ ", emailCc=" + emailCc + ", existingMortgage="
				+ existingMortgage + ", minHeatFee=" + minHeatFee
				+ ", qualifiedCheck=" + qualifiedCheck + ", postalCode="
				+ postalCode + ", active=" + active + ", propertyTaxes="
				+ propertyTaxes + ", isFloatingHomes=" + isFloatingHomes
				+ ", mobile=" + mobile + ", oppInfoRate=" + oppInfoRate
				+ ", chargesBehindAmount=" + chargesBehindAmount
				+ ", applicationDate=" + applicationDate + ", userEmail="
				+ userEmail + ", name=" + name + ", desiredMortgageAmount="
				+ desiredMortgageAmount + ", volumeCommission="
				+ volumeCommission + ", partnerAddressName="
				+ partnerAddressName + ", isLeasedLand=" + isLeasedLand
				+ ", squareFootage=" + squareFootage + ", color=" + color
				+ ", emailWork=" + emailWork + ", isaSmallCentre="
				+ isaSmallCentre + ", saleOfExistingAmount="
				+ saleOfExistingAmount + ", isNonConvConstruction="
				+ isNonConvConstruction + ", partnerName=" + partnerName
				+ ", messageSummary=" + messageSummary
				+ ", processPresntedutioCheck=" + processPresntedutioCheck
				+ ", street=" + street + ", titleAction=" + titleAction
				+ ", oppInfoStartDate=" + oppInfoStartDate + ", city=" + city
				+ ", createDate=" + createDate + ", hasChargesBehind="
				+ hasChargesBehind + ", renewaldate=" + renewaldate
				+ ", lenderName=" + lenderName + ", dayClose=" + dayClose
				+ ", dateClosed=" + dateClosed + ", dateDeadline="
				+ dateDeadline + ", approachedCheck=" + approachedCheck
				+ ", dayOpen=" + dayOpen + ", isUninsuredConv2ndHome="
				+ isUninsuredConv2ndHome + ", isFourPlex=" + isFourPlex
				+ ", pendingApplicationCheck=" + pendingApplicationCheck
				+ ", lenderFee=" + lenderFee + ", isRawLand=" + isRawLand
				+ ", isCottageRecProperty=" + isCottageRecProperty
				+ ", rrspAmount=" + rrspAmount + ", purchasePrice="
				+ purchasePrice + ", isMilitary=" + isMilitary + ", workPhone="
				+ workPhone + ", trailerCompAmount=" + trailerCompAmount
				+ ", oppInfoRenewalDate=" + oppInfoRenewalDate + ", block="
				+ block + ", TDS=" + TDS + ", lenderRef=" + lenderRef
				+ ", sweatEquityAmount=" + sweatEquityAmount
				+ ", internalNotesFinal=" + internalNotesFinal
				+ ", otherAmount=" + otherAmount + ", probability="
				+ probability + ", optOut=" + optOut + ", acres=" + acres
				+ ", estimatedValueofHome=" + estimatedValueofHome
				+ ", currentInterestRate=" + currentInterestRate
				+ ", cashBack=" + cashBack + ", plannedCost=" + plannedCost
				+ ", giftedAmount=" + giftedAmount + ", isRentalPools="
				+ isRentalPools + ", isFractionalInterests="
				+ isFractionalInterests + ", messageUnread=" + messageUnread
				+ ", oppInfoLoanAmnt=" + oppInfoLoanAmnt + ", phone=" + phone
				+ ", webResponse=" + webResponse + ", mls=" + mls
				+ ", currentRenewalDate=" + currentRenewalDate
				+ ", partnerAddressEmail=" + partnerAddressEmail
				+ ", isCommercial=" + isCommercial + ", distanceToMajorCenter="
				+ distanceToMajorCenter + ", function=" + function + ", age="
				+ age + ", contactName=" + contactName + ", dateOpen="
				+ dateOpen + ", internalNote=" + internalNote
				+ ", volumeBonusAmount=" + volumeBonusAmount
				+ ", isConstructionMortgage=" + isConstructionMortgage
				+ ", amortization=" + amortization + ", ref2=" + ref2
				+ ", dateActionNext=" + dateActionNext + ", totalOneTimeFees="
				+ totalOneTimeFees + ", downpaymentAmount=" + downpaymentAmount
				+ ", rate=" + rate + ", baseCommission=" + baseCommission
				+ ", insurerfee=" + insurerfee + ", street2=" + street2
				+ ", zip=" + zip + ", commitmentFee=" + commitmentFee
				+ ", creditStory=" + creditStory + ", closingDate="
				+ closingDate + ", consideredCites=" + consideredCites
				+ ", morwebFilogix=" + morwebFilogix + ", isEightPlex="
				+ isEightPlex + ", internalNotes=" + internalNotes
				+ ", propertyValue=" + propertyValue + ", plan=" + plan
				+ ", desiredAmortization=" + desiredAmortization + ", address="
				+ address + ", isSixPlex=" + isSixPlex + ", borrowedAmount="
				+ borrowedAmount + ", mortgageType=" + mortgageType
				+ ", preapprovedImLookingFora=" + preapprovedImLookingFora
				+ ", buyNewVehicle=" + buyNewVehicle + ", chargeOnTitle="
				+ chargeOnTitle + ", futureFamily=" + futureFamily
				+ ", desiredMortgageType=" + desiredMortgageType
				+ ", desiredTerm=" + desiredTerm + ", priority=" + priority
				+ ", opInfoType=" + opInfoType + ", incomeDecreasedWorried="
				+ incomeDecreasedWorried + ", garageType=" + garageType
				+ ", productType=" + productType + ", job5Years=" + job5Years
				+ ", propertyType=" + propertyType + ", sourceOfBorrowing="
				+ sourceOfBorrowing + ", desiredProductType="
				+ desiredProductType + ", heating=" + heating + ", termRate="
				+ termRate + ", propertyStyle=" + propertyStyle
				+ ", garageSize=" + garageSize + ", approvedimlookingfora="
				+ approvedimlookingfora + ", downPaymentComingFrom="
				+ downPaymentComingFrom + ", insurer=" + insurer + ", sewage="
				+ sewage + ", lifestyleChange=" + lifestyleChange
				+ ", oppInfoType=" + oppInfoType + ", financialRiskTaker="
				+ financialRiskTaker + ", whatIsYourLendingGoal="
				+ whatIsYourLendingGoal + ", water=" + water + ", trigger="
				+ trigger + ", lookingToApproved=" + lookingToApproved
				+ ", currentMortgageType=" + currentMortgageType
				+ ", refinancelookingfora=" + refinancelookingfora
				+ ", livingInProperty=" + livingInProperty
				+ ", propertyLessThen5Years=" + propertyLessThen5Years
				+ ", apartmentStyle=" + apartmentStyle + ", type=" + type
				+ ", lookingToRefinance=" + lookingToRefinance
				+ ", openClosed=" + openClosed + ", renterPayHeating="
				+ renterPayHeating + ", state=" + state + ", buildingFunds="
				+ buildingFunds + ", preferredNumber=" + preferredNumber
				+ ", term=" + term + ", productIds=" + productIds
				+ ", applicantRecordLine=" + applicantRecordLine + ", dealIds="
				+ ", appRecIds=" + appRecIds + ", dealIds="
				+ dealIds + ", partnerId=" + partnerId + ", referralSource="
				+ referralSource + ", finalLender=" + finalLender
				+ ", leadSource=" + leadSource + ", realtor=" + realtor
				+ ", lender=" + lender + ", selectedProduct=" + selectedProduct
				+ ", referredSource=" + referredSource + ", applicants="
				+ applicants + "]";
		
	}

	public Double getPercentVariable() {
		return percentVariable;
	}

	public void setPercentVariable(Double percentVariable) {
		this.percentVariable = percentVariable;
	}

	public List<Applicant> getApplicants() {
		return applicants;
	}

	public void setApplicants(List<Applicant> applicants) {
		this.applicants = applicants;
	}

	public Double getCurrentMonthlyPayment() {
		return currentMonthlyPayment;
	}

	public void setCurrentMonthlyPayment(Double currentMonthlyPayment) {
		this.currentMonthlyPayment = currentMonthlyPayment;
	}

	public String getCurrentLender() {
		return currentLender;
	}

	public void setCurrentLender(String currentLender) {
		this.currentLender = currentLender;
	}

	public Boolean getHasSelfEmployedIncome() {
		return hasSelfEmployedIncome;
	}

	public void setHasSelfEmployedIncome(Boolean hasSelfEmployedIncome) {
		this.hasSelfEmployedIncome = hasSelfEmployedIncome;
	}

	public String getPid() {
		return townshipPID;
	}

	public void setPid(String pid) {
		this.townshipPID = pid;
	}
	

	
int opportunityId;


public int getOpportunityId() {
	return opportunityId;
}

public void setOpportunityId(int opportunityId) {
	this.opportunityId = opportunityId;
}

public String getCompany() {
	return company;
}

public void setCompany(String company) {
	this.company = company;
}

/*public Double getBaseltv() {
	return baseLtv;
}

public void setBaseltv(Double baseltv) {
	this.baseLtv = baseltv;
}*/

public Double getBaseLtv() {
	return baseLtv;
}

public void setBaseLtv(Double baseLtv) {
	this.baseLtv = baseLtv;
}

public String getxSelectedRecommendation() {
	return xSelectedRecommendation;
}

public void setxSelectedRecommendation(String xSelectedRecommendation) {
	this.xSelectedRecommendation = xSelectedRecommendation;
}

public String getTownshipPID() {
	return townshipPID;
}

public void setTownshipPID(String townshipPID) {
	this.townshipPID = townshipPID;
}



}