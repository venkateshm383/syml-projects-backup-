package com.syml.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.syml.domain.BaseBean;

import javax.persistence.Column;

import com.syml.generator.domain.annotation.ERP;

import javax.persistence.Entity;

import java.util.List;

import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.syml.generator.domain.annotation.ERP.OpenERPType;

import java.util.ArrayList;
/**
* mapping open erp object : product.product
* @author .x.m.
*
*/
@XmlRootElement
@Entity(name="product_product")
public class Product extends BaseBean {
	
	
	//lendername
	@ERP( name = "lendername" )
	public String lendername;

	//combinebase
	@ERP( name = "combinebase" )
	public Boolean combinebase;

	
	//uninsurable
	@ERP( name = "uninsurable" )
	public Boolean uninsurable;

	
	//poor_tds_greater_equal_split
	@ERP( name = "poor_tds_greater_equal_split" )
	public Double poorTdsGreaterEqualSplit;

	//semi_monthly_payments
	@ERP( name = "semi_monthly_payments" )
	public Boolean semiMonthlyPayments;

	//outstanding_collection_payment
	@ERP( name = "outstanding_collection_payment" )
	public Boolean outstandingCollectionPayment;

	//age_restricted
	@ERP( name = "age_restricted" )
	public Boolean ageRestricted;

	//description_sale
	@Column(length=9999)
	@ERP( name = "description_sale" )
	public String descriptionSale;

	//price_extra
	@ERP( name = "price_extra" )
	public Double priceExtra;

	//vol_bonus_1_threshold
	@ERP( name = "vol_bonus_1_threshold" )
	public Double volBonus1Threshold;

	//line_of_credit
	@ERP( name = "line_of_credit" )
	public Boolean lineOfCredit;

	//active
	@ERP( name = "active" )
	public Boolean active;

	//loc_row
	@ERP( name = "loc_row" )
	public String locRow;

	//broker_fees_percent
	@ERP( name = "broker_fees_percent" )
	public Double brokerFeesPercent;

	//capped_variable
	@ERP( name = "capped_variable" )
	public Boolean cappedVariable;

	//variants
	public String variants;

	//last_yr_noa
	@ERP( name = "last_yr_noa" )
	public Boolean lastYrNoa;

	//grow_ops
	@ERP( name = "grow_ops" )
	public Boolean growOps;

	//med_gds_less_split
	@ERP( name = "med_gds_less_split" )
	public Double medGdsLessSplit;

	//double_up_payments
	@ERP( name = "double_up_payments" )
	public Boolean doubleUpPayments;

	//uninsured_conv_2nd_home
	@ERP( name = "uninsured_conv_2nd_home" )
	public Boolean uninsuredConv2ndHome;

	//image
	@ERP( name = "image", type=OpenERPType.bytes )
	@XmlElement
	public String image;

	//allow_title_ins
	@ERP( name = "allow_title_ins" )
	public Boolean allowTitleIns;

	//living_allowance
	@ERP( name = "living_allowance" )
	public Double livingAllowance;

	//mths_from_discharge_allowable
	@ERP( name = "mths_from_discharge_allowable" )
	public Double mthsFromDischargeAllowable;

	//price
	public Double price;

	//cities
	@Column(length=9999)
	public String cities;

	//sm_center_con_cutOff
	@ERP( name = "sm_center_con_cutOff" )
	public Double smCenterConCutOff;

	//country_residential
	@ERP( name = "country_residential" )
	public Boolean countryResidential;

	//message_is_follower
	@ERP( name = "message_is_follower" )
	public Boolean messageIsFollower;

	//small_center_insure_ltv
	@ERP( name = "small_center_insure_ltv" )
	public Double smallCenterInsureLtv;

	//weight_net
	@ERP( name = "weight_net" )
	public Double weightNet;

	//cottage_max_ltv
	@ERP( name = "cottage_max_ltv" )
	public Double cottageMaxLtv;

	//description_purchase
	@Column(length=9999)
	@ERP( name = "description_purchase" )
	public String descriptionPurchase;

	//rental_con_cutOff
	@ERP( name = "rental_con_cutOff" )
	public Double rentalConCutOff;

	//vol_bonus_4_threshold
	@ERP( name = "vol_bonus_4_threshold" )
	public Double volBonus4Threshold;

	//non_conv_construction
	@ERP( name = "non_conv_construction" )
	public Boolean nonConvConstruction;

	//max_acreage_cutoff
	@ERP( name = "max_acreage_cutoff" )
	public Double maxAcreageCutoff;

	//min_sq_allow_house
	@ERP( name = "min_sq_allow_house" )
	public Double minSqAllowHouse;

	//rental_property
	@ERP( name = "rental_property" )
	public Boolean rentalProperty;

	//produce_delay
	@ERP( name = "produce_delay" )
	public Double produceDelay;

	//list_price
	@ERP( name = "list_price" )
	public Double listPrice;

	//underwriter_pref
	@ERP( name = "underwriter_pref" )
	public Double underwriterPref;

	//poor_gds_greater_equal_split
	@ERP( name = "poor_gds_greater_equal_split" )
	public Double poorGdsGreaterEqualSplit;

	//vehicle_allowance
	@ERP( name = "vehicle_allowance" )
	public Double vehicleAllowance;

	//beacon_tds_split
	@ERP( name = "beacon_tds_split" )
	public Double beaconTdsSplit;

	//med_gds_greater_equal_split
	@ERP( name = "med_gds_greater_equal_split" )
	public Double medGdsGreaterEqualSplit;

	//med_tds_less_split
	@ERP( name = "med_tds_less_split" )
	public Double medTdsLessSplit;

	//track_incoming
	@ERP( name = "track_incoming" )
	public Boolean trackIncoming;

	//self_build_product
	@ERP( name = "self_build_product" )
	public Boolean selfBuildProduct;

	//volume
	public Double volume;

	//specific_lenders_on_1st
	@ERP( name = "specific_lenders_on_1st" )
	public Boolean specificLendersOn1st;

	//2_yr_avg_noa
	@ERP( name = "2_yr_avg_noa" )
	public Boolean twoYrAvgNoa;

	//ean13
	@ERP( name = "ean13" )
	public String ean13;

	//track_outgoing
	@ERP( name = "track_outgoing" )
	public Boolean trackOutgoing;

	//res_con_cutoff
	@ERP( name = "res_con_cutoff" )
	public Double resConCutoff;

	//broker_fees_flat
	@ERP( name = "broker_fees_flat" )
	public Double brokerFeesFlat;

	//skip_payment_possible
	@ERP( name = "skip_payment_possible" )
	public Boolean skipPaymentPossible;

	//allowed_on_3rd
	@ERP( name = "allowed_on_3rd" )
	public Boolean allowedOn3rd;

	//short_avg_fund_day
	@ERP( name = "short_avg_fund_day" )
	public Double shortAvgFundDay;

	//mortgage_amount
	@ERP( name = "mortgage_amount" )
	public Integer mortgageAmount;

	//allowed_red_flags_good
	@ERP( name = "allowed_red_flags_good" )
	public Double allowedRedFlagsGood;

	//poor_tds_less_split
	@ERP( name = "poor_tds_less_split" )
	public Double poorTdsLessSplit;

	//mobile_homes
	@ERP( name = "mobile_homes" )
	public Boolean mobileHomes;

	//vol_bonus_3_threshold
	@ERP( name = "vol_bonus_3_threshold" )
	public Double volBonus3Threshold;

	//vol_bonus_4
	@ERP( name = "vol_bonus_4" )
	public Double volBonus4;

	//description
	@Column(length=9999)
	public String description;

	//vol_bonus_5
	@ERP( name = "vol_bonus_5" )
	public Double volBonus5;

	//vol_bonus_2
	@ERP( name = "vol_bonus_2" )
	public Double volBonus2;

	//vol_bonus_3
	@ERP( name = "vol_bonus_3" )
	public Double volBonus3;

	//vol_bonus_1
	@ERP( name = "vol_bonus_1" )
	public Double volBonus1;

	//rental_last_yr_noa
	@ERP( name = "rental_last_yr_noa" )
	public Boolean rentalLastYrNoa;

	//interest_rate
	@ERP( name = "interest_rate" )
	public Double interestRate;

	//interest_rate
	@ERP( name = "posted_rate" )
	public Double postedRate;

	//outgoing_qty
	@ERP( name = "outgoing_qty" )
	public Double outgoingQty;

	//image_medium
	@ERP( name = "image_medium", type=OpenERPType.bytes )
	@XmlElement
	public String imageMedium;

	//max_mortgage_allowed
	@ERP( name = "max_mortgage_allowed" )
	public Double maxMortgageAllowed;

	//prepay_to_reduce
	@ERP( name = "prepay_to_reduce" )
	public Boolean prepayToReduce;

	//loc_case
	@ERP( name = "loc_case" )
	public String locCase;

	//vol_bonus_5_threshold
	@ERP( name = "vol_bonus_5_threshold" )
	public Double volBonus5Threshold;

	//code
	public String code;

	//track_production
	@ERP( name = "track_production" )
	public Boolean trackProduction;

	//price_margin
	@ERP( name = "price_margin" )
	public Double priceMargin;

	//allow_provinces
	@Column(length=9999)
	@ERP( name = "allow_provinces" )
	public String allowProvinces;

	//boarding_houses
	@ERP( name = "boarding_houses" )
	public Boolean boardingHouses;

	//allowed_on_bridge
	@ERP( name = "allowed_on_bridge" )
	public Boolean allowedOnBridge;

	//vtb_max_ltv
	@ERP( name = "vtb_max_ltv" )
	public Double vtbMaxLtv;

	//t1_general_formula
	@ERP( name = "t1_general_formula" )
	public Boolean t1GeneralFormula;

	//default_code
	@ERP( name = "default_code" )
	public String defaultCode;

	//co_operative_housing
	@ERP( name = "co_operative_housing" )
	public Boolean coOperativeHousing;

	//rental_2_yr_avg_noa
	@ERP( name = "rental_2_yr_avg_noa" )
	public Boolean rental2YrAvgNoa;

	//qualifying_rate
	@ERP( name = "qualifying_rate" )
	public Double qualifyingRate;

	//weight
	public Double weight;

	//trailer_commission
	@ERP( name = "trailer_commission" )
	public Double trailerCommission;

	//rooming_houses
	@ERP( name = "rooming_houses" )
	public Boolean roomingHouses;

	//product_name
	@ERP( name = "product_name" )
	public String productName;

	//allowed_red_flags_poor
	@ERP( name = "allowed_red_flags_poor" )
	public Double allowedRedFlagsPoor;

	//immigrant_max_ltv
	@ERP( name = "immigrant_max_ltv" )
	public Double immigrantMaxLtv;

	//allowed_grey_flags_good
	@ERP( name = "allowed_grey_flags_good" )
	public Double allowedGreyFlagsGood;

	//allowed_grey_flags_poor
	@ERP( name = "allowed_grey_flags_poor" )
	public Double allowedGreyFlagsPoor;

	//allow_power_of_attorney
	@ERP( name = "allow_power_of_attorney" )
	public Boolean allowPowerOfAttorney;

	//allowed_on_1st
	@ERP( name = "allowed_on_1st" )
	public Boolean allowedOn1st;

	//rental_ltv_cutOff_grt
	@ERP( name = "rental_ltv_cutOff_grt" )
	public Double rentalLtvCutOffGrt;

	//name
	public String name;

	//is_product_insured
	@ERP( name = "is_product_insured" )
	public Boolean isProductInsured;

	//res_ltv_cutOff_grt
	@ERP( name = "res_ltv_cutOff_grt" )
	public Double resLtvCutOffGrt;

	//purchase_ok
	@ERP( name = "purchase_ok" )
	public Boolean purchaseOk;

	//max_property_age
	@ERP( name = "max_property_age" )
	public Integer maxPropertyAge;

	//max_borrow_allow
	@ERP( name = "max_borrow_allow" )
	public Double maxBorrowAllow;

	//bi_weekly_payments
	@ERP( name = "bi_weekly_payments" )
	public Boolean biWeeklyPayments;

	//max_allow_property
	@ERP( name = "max_allow_property" )
	public Double maxAllowProperty;

	//pay_stub_prorate
	@ERP( name = "pay_stub_prorate" )
	public Boolean payStubProrate;

	//closing_period
	@ERP( name = "closing_period" )
	public Integer closingPeriod;

	//se_grossup_percent
	@ERP( name = "se_grossup_percent" )
	public Double seGrossupPercent;

	//agricultural
	public Boolean agricultural;

	//min_beacon
	@ERP( name = "min_beacon" )
	public Double minBeacon;

	//maximum_amortization
	@ERP( name = "maximum_amortization" )
	public Integer maximumAmortization;

	//color
	public Integer color;

	//lst_price
	@ERP( name = "lst_price" )
	public Double lstPrice;

	//delivery_count
	@ERP( name = "delivery_count" )
	public Integer deliveryCount;

	//max_number_of_draws
	@ERP( name = "max_number_of_draws" )
	public Integer maxNumberOfDraws;

	//reward_points
	@ERP( name = "reward_points" )
	public Boolean rewardPoints;

	//immigrant_max_mo
	@ERP( name = "immigrant_max_mo" )
	public Double immigrantMaxMo;

	//construction_product
	@ERP( name = "construction_product" )
	public Boolean constructionProduct;

	//message_summary
	@Column(length=9999)
	@ERP( name = "message_summary" )
	public String messageSummary;

	//allow_2nd_homes
	@ERP( name = "allow_2nd_homes" )
	public Boolean allow2ndHomes;

	//leased_land
	@ERP( name = "leased_land" )
	public Boolean leasedLand;

	//months_interest_penalty
	@ERP( name = "months_interest_penalty" )
	public Integer monthsInterestPenalty;

	//business_ease
	@ERP( name = "business_ease" )
	public Double businessEase;

	//extra_applied_any_time
	@ERP( name = "extra_applied_any_time" )
	public Boolean extraAppliedAnyTime;

	//letter_of_employment
	@ERP( name = "letter_of_employment" )
	public Boolean letterOfEmployment;

	//allow_duplex
	@ERP( name = "allow_duplex" )
	public Boolean allowDuplex;

	//allow_eight_plex
	@ERP( name = "allow_eight_plex" )
	public Boolean allowEightPlex;

	//seller_delay
	@ERP( name = "seller_delay" )
	public Integer sellerDelay;

	//min_beacon_cut_off
	@ERP( name = "min_beacon_cut_off" )
	public Double minBeaconCutOff;

	//commercial
	public Boolean commercial;

	//fractional_interests
	@ERP( name = "fractional_interests" )
	public Boolean fractionalInterests;

	//min_heat_cost
	@ERP( name = "min_heat_cost" )
	public Double minHeatCost;

	//rental_ltv_cutOff_les
	@ERP( name = "rental_ltv_cutOff_les" )
	public Double rentalLtvCutOffLes;

	//med_tds_greater_equal_split
	@ERP( name = "med_tds_greater_equal_split" )
	public Double medTdsGreaterEqualSplit;

	//mortgage_payment
	@ERP( name = "mortgage_payment" )
	public Integer mortgagePayment;
	
	//quick_close_duration
	@ERP( name = "quick_close_duration" )
	public Integer quickCloseDuration;

	//weekly_payments
	@ERP( name = "weekly_payments" )
	public Boolean weeklyPayments;

	//marketing_commission
	@ERP( name = "marketing_commission" )
	public Double marketingCommission;

	//res_ltv_cutoff_les
	@ERP( name = "res_ltv_cutoff_les" )
	public Double resLtvCutoffLes;

	//good_credit_12_mo_lates
	@ERP( name = "good_credit_12_mo_lates" )
	public Integer goodCredit12MoLates;

	//max_gift_allow
	@ERP( name = "max_gift_allow" )
	public Double maxGiftAllow;

	//waive_penalty_if_retain
	@ERP( name = "waive_penalty_if_retain" )
	public Boolean waivePenaltyIfRetain;

	//reception_count
	@ERP( name = "reception_count" )
	public Integer receptionCount;

	//poor_credit_beacon_split
	@ERP( name = "poor_credit_beacon_split" )
	public Double poorCreditBeaconSplit;

	//2_yr_average_noa
	@ERP( name = "2_yr_average_noa" )
	public Boolean twoYrAverageNoa;

	//age_restricted_ltv
	@ERP( name = "age_restricted_ltv" )
	public Double ageRestrictedLtv;

	//great_gds_greater_equal_split
	@ERP( name = "great_gds_greater_equal_split" )
	public Double greatGdsGreaterEqualSplit;

	//sm_center_ltv_cutOff_grt
	@ERP( name = "sm_center_ltv_cutOff_grt" )
	public Double smCenterLtvCutOffGrt;

	//cashback
	public Double cashback;

	//warranty
	public Double warranty;

	//modular_homes
	@ERP( name = "modular_homes" )
	public Boolean modularHomes;

	//position
	public String position;

	//min_sq_allow_condo
	@ERP( name = "min_sq_allow_condo" )
	public Integer minSqAllowCondo;

	//requir_distance_to_city
	@ERP( name = "requir_distance_to_city" )
	public Double requirDistanceToCity;

	//lender_fees_percent
	@ERP( name = "lender_fees_percent" )
	public Double lenderFeesPercent;

	//immigrant_min_emp_mo
	@ERP( name = "immigrant_min_emp_mo" )
	public Double immigrantMinEmpMo;

	//cottage_min_beacon
	@ERP( name = "cottage_min_beacon" )
	public Double cottageMinBeacon;

	//name_template
	@ERP( name = "name_template" )
	public String nameTemplate;

	//stated_income_min_beacon
	@ERP( name = "stated_income_min_beacon" )
	public Double statedIncomeMinBeacon;

	//avg_diff_used
	@ERP( name = "avg_diff_used" )
	public Double avgDiffUsed;

	//good_credit_beacon_split
	@ERP( name = "good_credit_beacon_split" )
	public Integer goodCreditBeaconSplit;

	//canadian_military_allow
	@ERP( name = "canadian_military_allow" )
	public Boolean canadianMilitaryAllow;

	//remove_loans_done_pre_close
	@ERP( name = "remove_loans_done_pre_close" )
	public Boolean removeLoansDonePreClose;

	//maximum_applicants
	@ERP( name = "maximum_applicants" )
	public Double maximumApplicants;

	//2_yr_avg_noa_percent
	@ERP( name = "2_yr_avg_noa_percent" )
	public Boolean twoYrAvgNoaPercent;

	//min_sq_cutoff_condo
	@ERP( name = "min_sq_cutoff_condo" )
	public Integer minSqCutoffCondo;

	//allow_agricultural_less_then_10_acres
	@ERP( name = "allow_agricultural_less_then_10_acres" )
	public Boolean allowAgriculturalLessThen10Acres;

	//avg_processing_speed
	@ERP( name = "avg_processing_speed" )
	public Double avgProcessingSpeed;

	//sale_delay
	@ERP( name = "sale_delay" )
	public Double saleDelay;

	//outbuilding_included
	@ERP( name = "outbuilding_included" )
	public Integer outbuildingIncluded;

	//stated_income_max_ltv
	@ERP( name = "stated_income_max_ltv" )
	public Double statedIncomeMaxLtv;

	//min_sq_cutoff_house
	@ERP( name = "min_sq_cutoff_house" )
	public Integer minSqCutoffHouse;

	//non_resident_allow
	@ERP( name = "non_resident_allow" )
	public Boolean nonResidentAllow;

	//beacon_tds_cut_off
	@ERP( name = "beacon_tds_cut_off" )
	public Double beaconTdsCutOff;

	//min_mortgage_allowed
	@ERP( name = "min_mortgage_allowed" )
	public Double minMortgageAllowed;

	//great_tds_less_split
	@ERP( name = "great_tds_less_split" )
	public Double greatTdsLessSplit;

	//message_unread
	@ERP( name = "message_unread" )
	public Boolean messageUnread;

	//max_acreage_allowed
	@ERP( name = "max_acreage_allowed" )
	public Double maxAcreageAllowed;

	//loc_rack
	@ERP( name = "loc_rack" )
	public String locRack;

	//condo
	public Boolean condo;

	//lender_fees_flat
	@ERP( name = "lender_fees_flat" )
	public Double lenderFeesFlat;

	//uos_coeff
	@ERP( name = "uos_coeff" )
	public Double uosCoeff;

	//allow_four_plex
	@ERP( name = "allow_four_plex" )
	public Boolean allowFourPlex;

	//monthly_payments
	@ERP( name = "monthly_payments" )
	public Boolean monthlyPayments;

	//cottage_rec_property
	@ERP( name = "cottage_rec_property" )
	public Boolean cottageRecProperty;

	//virtual_available
	@ERP( name = "virtual_available" )
	public Double virtualAvailable;

	//poor_gds_less_split
	@ERP( name = "poor_gds_less_split" )
	public Double poorGdsLessSplit;

	//cottage_insure_ltv
	@ERP( name = "cottage_insure_ltv" )
	public Double cottageInsureLtv;

	//active_credit_trades_required
	@ERP( name = "active_credit_trades_required" )
	public Double activeCreditTradesRequired;

	//vol_bonus_2_threshold
	@ERP( name = "vol_bonus_2_threshold" )
	public Double volBonus2Threshold;

	//rental_pools
	@ERP( name = "rental_pools" )
	public Boolean rentalPools;

	//extra_annual_paydown
	@ERP( name = "extra_annual_paydown" )
	public Boolean extraAnnualPaydown;

	//child_tax_credit
	@ERP( name = "child_tax_credit" )
	public Double childTaxCredit;

	//allowed_red_flags_med
	@ERP( name = "allowed_red_flags_med" )
	public Double allowedRedFlagsMed;

	//charges_allowed_behind
	@ERP( name = "charges_allowed_behind" )
	public Boolean chargesAllowedBehind;

	//rental
	public Boolean rental;

	//remove_loan_mo_remaining
	@ERP( name = "remove_loan_mo_remaining" )
	public Double removeLoanMoRemaining;

	//qty_available
	@ERP( name = "qty_available" )
	public Double qtyAvailable;

	//rental_income_percent
	@ERP( name = "rental_income_percent" )
	public Double rentalIncomePercent;

	//allow_self_emp_income
	@ERP( name = "allow_self_emp_income" )
	public Boolean allowSelfEmpIncome;

	//allowed_on_2nd
	@ERP( name = "allowed_on_2nd" )
	public Boolean allowedOn2nd;

	//seller_qty
	@ERP( name = "seller_qty" )
	public Double sellerQty;

	//allow_six_plex
	@ERP( name = "allow_six_plex" )
	public Boolean allowSixPlex;

	//great_gds_less_split
	@ERP( name = "great_gds_less_split" )
	public Double greatGdsLessSplit;

	//image_small
	@ERP( name = "image_small", type=OpenERPType.bytes )
	@XmlElement
	public String imageSmall;

	//branch_signing
	@ERP( name = "branch_signing" )
	public Boolean branchSigning;

	//great_tds_greater_equal_split
	@ERP( name = "great_tds_greater_equal_split" )
	public Double greatTdsGreaterEqualSplit;

	//base_commission
	@ERP( name = "base_commission" )
	public Double baseCommission;

	//sm_center_ltv_cutOff_les
	@ERP( name = "sm_center_ltv_cutOff_les" )
	public Double smCenterLtvCutOffLes;

	//incoming_qty
	@ERP( name = "incoming_qty" )
	public Double incomingQty;

	//allowed_grey_flags_med
	@ERP( name = "allowed_grey_flags_med" )
	public Double allowedGreyFlagsMed;

	//life_leased_property
	@ERP( name = "life_leased_property" )
	public Boolean lifeLeasedProperty;

	//floating_homes
	@ERP( name = "floating_homes" )
	public Boolean floatingHomes;

	//is_penalty_greater
	@ERP( name = "is_penalty_greater" )
	public Boolean isPenaltyGreater;

	//raw_land
	@ERP( name = "raw_land" )
	public Boolean rawLand;

	//sale_ok
	@ERP( name = "sale_ok" )
	public Boolean saleOk;

	//poor_credit_12_mo_lates
	@ERP( name = "poor_credit_12_mo_lates" )
	public Double poorCredit12MoLates;

	//partner_ref
	@ERP( name = "partner_ref" )
	public String partnerRef;

	//increase_payments
	@ERP( name = "increase_payments" )
	public Boolean increasePayments;

	//standard_price
	@ERP( name = "standard_price" )
	public Double standardPrice;

	//high_ratio_2nd_home
	@ERP( name = "high_ratio_2nd_home" )
	public Boolean highRatio2ndHome;

	/*********************************selection type******************************/
	//mortgage_type
	@ERP( type = OpenERPType.selection, name = "mortgage_type" )
	public String mortgageType;

	//valuation
	@ERP( type = OpenERPType.selection, name = "valuation" )
	public String valuation;

	//excl_or_incl_city
	@ERP( type = OpenERPType.selection, name = "excl_or_incl_city" )
	public String exclOrInclCity;

	//cost_method
	@ERP( type = OpenERPType.selection, name = "cost_method" )
	public String costMethod;

	//supply_method
	@ERP( type = OpenERPType.selection, name = "supply_method" )
	public String supplyMethod;

	//child_support_treatment
	@ERP( type = OpenERPType.selection, name = "child_support_treatment" )
	public String childSupportTreatment;

	//revolving_debt_payments
	@ERP( type = OpenERPType.selection, name = "revolving_debt_payments" )
	public String revolvingDebtPayments;

	//rental_income_treatment
	@ERP( type = OpenERPType.selection, name = "rental_income_treatment" )
	public String rentalIncomeTreatment;

	//equifax_scoring_used
	@ERP( type = OpenERPType.selection, name = "equifax_scoring_used" )
	public String equifaxScoringUsed;

	//loc_qualifying_rate
	@ERP( type = OpenERPType.selection, name = "loc_qualifying_rate" )
	public String locQualifyingRate;

	//procure_method
	@ERP( type = OpenERPType.selection, name = "procure_method" )
	public String procureMethod;

	//application_method
	@ERP( type = OpenERPType.selection, name = "application_method" )
	public String applicationMethod;

	//mes_type
	@ERP( type = OpenERPType.selection, name = "mes_type" )
	public String mesType;

	//type
	@ERP( type = OpenERPType.selection, name = "type" )
	public String type;

	//whose_income_used
	@ERP( type = OpenERPType.selection, name = "whose_income_used" )
	public String whoseIncomeUsed;

	//open_closed
	@ERP( type = OpenERPType.selection, name = "open_closed" )
	public String openClosed;

	//diff_length
	@ERP( type = OpenERPType.selection, name = "diff_length" )
	public String diffLength;

	//state
	@ERP( type = OpenERPType.selection, name = "state" )
	public String state;

	//term
	@ERP( type = OpenERPType.selection, name = "term" )
	public String term;

	/*********************************one2many type******************************/

	//many to one : mapping table : res_partner(com.syml.domain.Lender)-->lender_id
	@ERP(name="lender_line", type = OpenERPType.one2many )
	@JsonIgnore
	@Transient
	public List<Lender> lenderLine = new ArrayList<Lender>();	
	

	/*********************************many2one type******************************/

	//many to one : mapping table : crm_lead(com.syml.domain.Opportunity)
	@ERP(name="opportunity_id", type = OpenERPType.many2one )
	@JsonIgnore
	public Integer opportunityId;

	//many to one : mapping table : res_partner(com.syml.domain.Lender)
	@ERP(name="seller_id", type = OpenERPType.many2one )
	@JsonIgnore
	public Integer sellerId;

	//many to one : mapping table : res_partner(com.syml.domain.Lender)
	@ERP(name="lender", type = OpenERPType.many2one )
	@JsonIgnore
	public Integer lender;
	
	//many to one : mapping table : product_product(com.syml.domain.Product)
	@ERP(name="combined_product", type = OpenERPType.many2one )
	@JsonIgnore
	public Integer combinedProduct;

	@Transient
	public String treatmentOfRental;
	@Transient
	public boolean allowRawLand;
	@Transient
	public boolean agriculturalLessThen10Acres;


	public void setPoorTdsGreaterEqualSplit(Double poorTdsGreaterEqualSplit){
		this.poorTdsGreaterEqualSplit = poorTdsGreaterEqualSplit;
	}

	public Double getPoorTdsGreaterEqualSplit(){
		return this.poorTdsGreaterEqualSplit;
	}

	public void setSemiMonthlyPayments(Boolean semiMonthlyPayments){
		this.semiMonthlyPayments = semiMonthlyPayments;
	}

	public Boolean getSemiMonthlyPayments(){
		return this.semiMonthlyPayments;
	}

	public void setAgeRestricted(Boolean ageRestricted){
		this.ageRestricted = ageRestricted;
	}

	public Boolean getAgeRestricted(){
		return this.ageRestricted;
	}

	public void setDescriptionSale(String descriptionSale){
		this.descriptionSale = descriptionSale;
	}

	public String getDescriptionSale(){
		return this.descriptionSale;
	}

	public void setPriceExtra(Double priceExtra){
		this.priceExtra = priceExtra;
	}

	public Double getPriceExtra(){
		return this.priceExtra;
	}

	public void setMortgageType(String mortgageType){
		this.mortgageType = mortgageType;
	}

	public String getMortgageType(){
		return this.mortgageType;
	}

	public void setVolBonus1Threshold(Double volBonus1Threshold){
		this.volBonus1Threshold = volBonus1Threshold;
	}

	public Double getVolBonus1Threshold(){
		return this.volBonus1Threshold;
	}

	public void setLineOfCredit(Boolean lineOfCredit){
		this.lineOfCredit = lineOfCredit;
	}

	public Boolean getLineOfCredit(){
		return this.lineOfCredit;
	}

	public void setLocRow(String locRow){
		this.locRow = locRow;
	}

	public String getLocRow(){
		return this.locRow;
	}

	public void setBrokerFeesPercent(Double brokerFeesPercent){
		this.brokerFeesPercent = brokerFeesPercent;
	}

	public Double getBrokerFeesPercent(){
		return this.brokerFeesPercent;
	}

	public void setValuation(String valuation){
		this.valuation = valuation;
	}

	public String getValuation(){
		return this.valuation;
	}

	public void setCappedVariable(Boolean cappedVariable){
		this.cappedVariable = cappedVariable;
	}

	public Boolean getCappedVariable(){
		return this.cappedVariable;
	}

	public void setVariants(String variants){
		this.variants = variants;
	}

	public String getVariants(){
		return this.variants;
	}

	public void setLastYrNoa(Boolean lastYrNoa){
		this.lastYrNoa = lastYrNoa;
	}

	public Boolean getLastYrNoa(){
		return this.lastYrNoa;
	}

	public void setGrowOps(Boolean growOps){
		this.growOps = growOps;
	}

	public Boolean getGrowOps(){
		return this.growOps;
	}

	public void setMedGdsLessSplit(Double medGdsLessSplit){
		this.medGdsLessSplit = medGdsLessSplit;
	}

	public Double getMedGdsLessSplit(){
		return this.medGdsLessSplit;
	}

	public void setOpportunityId(Integer opportunityId){
		this.opportunityId = opportunityId;
	}

	public Integer getOpportunityId(){
		return this.opportunityId;
	}

	public void setDoubleUpPayments(Boolean doubleUpPayments){
		this.doubleUpPayments = doubleUpPayments;
	}

	public Boolean getDoubleUpPayments(){
		return this.doubleUpPayments;
	}

	public void setUninsuredConv2ndHome(Boolean uninsuredConv2ndHome){
		this.uninsuredConv2ndHome = uninsuredConv2ndHome;
	}

	public Boolean getUninsuredConv2ndHome(){
		return this.uninsuredConv2ndHome;
	}

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return this.image;
	}

	public void setAllowTitleIns(Boolean allowTitleIns){
		this.allowTitleIns = allowTitleIns;
	}

	public Boolean getAllowTitleIns(){
		return this.allowTitleIns;
	}

	public void setLivingAllowance(Double livingAllowance){
		this.livingAllowance = livingAllowance;
	}

	public Double getLivingAllowance(){
		return this.livingAllowance;
	}

	public void setMthsFromDischargeAllowable(Double mthsFromDischargeAllowable){
		this.mthsFromDischargeAllowable = mthsFromDischargeAllowable;
	}

	public Double getMthsFromDischargeAllowable(){
		return this.mthsFromDischargeAllowable;
	}

	public void setPrice(Double price){
		this.price = price;
	}

	public Double getPrice(){
		return this.price;
	}

	public void setCities(String cities){
		this.cities = cities;
	}

	public String getCities(){
		return this.cities;
	}

	public void setSmCenterConCutOff(Double smCenterConCutOff){
		this.smCenterConCutOff = smCenterConCutOff;
	}

	public Double getSmCenterConCutOff(){
		return this.smCenterConCutOff;
	}

	public void setCountryResidential(Boolean countryResidential){
		this.countryResidential = countryResidential;
	}

	public Boolean getCountryResidential(){
		return this.countryResidential;
	}

	public void setMessageIsFollower(Boolean messageIsFollower){
		this.messageIsFollower = messageIsFollower;
	}

	public Boolean getMessageIsFollower(){
		return this.messageIsFollower;
	}

	public void setSmallCenterInsureLtv(Double smallCenterInsureLtv){
		this.smallCenterInsureLtv = smallCenterInsureLtv;
	}

	public Double getSmallCenterInsureLtv(){
		return this.smallCenterInsureLtv;
	}

	public void setWeightNet(Double weightNet){
		this.weightNet = weightNet;
	}

	public Double getWeightNet(){
		return this.weightNet;
	}

	public void setCottageMaxLtv(Double cottageMaxLtv){
		this.cottageMaxLtv = cottageMaxLtv;
	}

	public Double getCottageMaxLtv(){
		return this.cottageMaxLtv;
	}

	public void setDescriptionPurchase(String descriptionPurchase){
		this.descriptionPurchase = descriptionPurchase;
	}

	public String getDescriptionPurchase(){
		return this.descriptionPurchase;
	}

	public void setRentalConCutOff(Double rentalConCutOff){
		this.rentalConCutOff = rentalConCutOff;
	}

	public Double getRentalConCutOff(){
		return this.rentalConCutOff;
	}

	public void setVolBonus4Threshold(Double volBonus4Threshold){
		this.volBonus4Threshold = volBonus4Threshold;
	}

	public Double getVolBonus4Threshold(){
		return this.volBonus4Threshold;
	}

	public void setNonConvConstruction(Boolean nonConvConstruction){
		this.nonConvConstruction = nonConvConstruction;
	}

	public Boolean getNonConvConstruction(){
		return this.nonConvConstruction;
	}

	public void setMaxAcreageCutoff(Double maxAcreageCutoff){
		this.maxAcreageCutoff = maxAcreageCutoff;
	}

	public Double getMaxAcreageCutoff(){
		return this.maxAcreageCutoff;
	}

	public void setMinSqAllowHouse(Double minSqAllowHouse){
		this.minSqAllowHouse = minSqAllowHouse;
	}

	public Double getMinSqAllowHouse(){
		return this.minSqAllowHouse;
	}

	public void setRentalProperty(Boolean rentalProperty){
		this.rentalProperty = rentalProperty;
	}

	public Boolean getRentalProperty(){
		return this.rentalProperty;
	}

	public void setProduceDelay(Double produceDelay){
		this.produceDelay = produceDelay;
	}

	public Double getProduceDelay(){
		return this.produceDelay;
	}

	public void setListPrice(Double listPrice){
		this.listPrice = listPrice;
	}

	public Double getListPrice(){
		return this.listPrice;
	}

	public void setUnderwriterPref(Double underwriterPref){
		this.underwriterPref = underwriterPref;
	}

	public Double getUnderwriterPref(){
		return this.underwriterPref;
	}

	public void setPoorGdsGreaterEqualSplit(Double poorGdsGreaterEqualSplit){
		this.poorGdsGreaterEqualSplit = poorGdsGreaterEqualSplit;
	}

	public Double getPoorGdsGreaterEqualSplit(){
		return this.poorGdsGreaterEqualSplit;
	}

	public void setVehicleAllowance(Double vehicleAllowance){
		this.vehicleAllowance = vehicleAllowance;
	}

	public Double getVehicleAllowance(){
		return this.vehicleAllowance;
	}

	public void setBeaconTdsSplit(Double beaconTdsSplit){
		this.beaconTdsSplit = beaconTdsSplit;
	}

	public Double getBeaconTdsSplit(){
		return this.beaconTdsSplit;
	}

	public void setMedGdsGreaterEqualSplit(Double medGdsGreaterEqualSplit){
		this.medGdsGreaterEqualSplit = medGdsGreaterEqualSplit;
	}

	public Double getMedGdsGreaterEqualSplit(){
		return this.medGdsGreaterEqualSplit;
	}

	public void setMedTdsLessSplit(Double medTdsLessSplit){
		this.medTdsLessSplit = medTdsLessSplit;
	}

	public Double getMedTdsLessSplit(){
		return this.medTdsLessSplit;
	}

	public void setTrackIncoming(Boolean trackIncoming){
		this.trackIncoming = trackIncoming;
	}

	public Boolean getTrackIncoming(){
		return this.trackIncoming;
	}

	public void setSelfBuildProduct(Boolean selfBuildProduct){
		this.selfBuildProduct = selfBuildProduct;
	}

	public Boolean getSelfBuildProduct(){
		return this.selfBuildProduct;
	}

	public void setVolume(Double volume){
		this.volume = volume;
	}

	public Double getVolume(){
		return this.volume;
	}

	public void setSpecificLendersOn1st(Boolean specificLendersOn1st){
		this.specificLendersOn1st = specificLendersOn1st;
	}

	public Boolean getSpecificLendersOn1st(){
		return this.specificLendersOn1st;
	}

	public void setTwoYrAvgNoa(Boolean twoYrAvgNoa){
		this.twoYrAvgNoa = twoYrAvgNoa;
	}

	public Boolean getTwoYrAvgNoa(){
		return this.twoYrAvgNoa;
	}

	public void setEan13(String ean13){
		this.ean13 = ean13;
	}

	public String getEan13(){
		return this.ean13;
	}

	public void setTrackOutgoing(Boolean trackOutgoing){
		this.trackOutgoing = trackOutgoing;
	}

	public Boolean getTrackOutgoing(){
		return this.trackOutgoing;
	}

	public void setResConCutoff(Double resConCutoff){
		this.resConCutoff = resConCutoff;
	}

	public Double getResConCutoff(){
		return this.resConCutoff;
	}

	public void setBrokerFeesFlat(Double brokerFeesFlat){
		this.brokerFeesFlat = brokerFeesFlat;
	}

	public Double getBrokerFeesFlat(){
		return this.brokerFeesFlat;
	}

	public void setExclOrInclCity(String exclOrInclCity){
		this.exclOrInclCity = exclOrInclCity;
	}

	public String getExclOrInclCity(){
		return this.exclOrInclCity;
	}

	public void setSkipPaymentPossible(Boolean skipPaymentPossible){
		this.skipPaymentPossible = skipPaymentPossible;
	}

	public Boolean getSkipPaymentPossible(){
		return this.skipPaymentPossible;
	}

	public void setAllowedOn3rd(Boolean allowedOn3rd){
		this.allowedOn3rd = allowedOn3rd;
	}

	public Boolean getAllowedOn3rd(){
		return this.allowedOn3rd;
	}

	public void setShortAvgFundDay(Double shortAvgFundDay){
		this.shortAvgFundDay = shortAvgFundDay;
	}

	public Double getShortAvgFundDay(){
		return this.shortAvgFundDay;
	}

	public void setMortgageAmount(Integer mortgageAmount){
		this.mortgageAmount = mortgageAmount;
	}

	public Integer getMortgageAmount(){
		return this.mortgageAmount;
	}

	public void setAllowedRedFlagsGood(Double allowedRedFlagsGood){
		this.allowedRedFlagsGood = allowedRedFlagsGood;
	}

	public Double getAllowedRedFlagsGood(){
		return this.allowedRedFlagsGood;
	}

	public void setPoorTdsLessSplit(Double poorTdsLessSplit){
		this.poorTdsLessSplit = poorTdsLessSplit;
	}

	public Double getPoorTdsLessSplit(){
		return this.poorTdsLessSplit;
	}

	public void setMobileHomes(Boolean mobileHomes){
		this.mobileHomes = mobileHomes;
	}

	public Boolean getMobileHomes(){
		return this.mobileHomes;
	}

	public void setVolBonus3Threshold(Double volBonus3Threshold){
		this.volBonus3Threshold = volBonus3Threshold;
	}

	public Double getVolBonus3Threshold(){
		return this.volBonus3Threshold;
	}

	public void setVolBonus4(Double volBonus4){
		this.volBonus4 = volBonus4;
	}

	public Double getVolBonus4(){
		return this.volBonus4;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return this.description;
	}

	public void setVolBonus5(Double volBonus5){
		this.volBonus5 = volBonus5;
	}

	public Double getVolBonus5(){
		return this.volBonus5;
	}

	public void setVolBonus2(Double volBonus2){
		this.volBonus2 = volBonus2;
	}

	public Double getVolBonus2(){
		return this.volBonus2;
	}

	public void setVolBonus3(Double volBonus3){
		this.volBonus3 = volBonus3;
	}

	public Double getVolBonus3(){
		return this.volBonus3;
	}

	public void setVolBonus1(Double volBonus1){
		this.volBonus1 = volBonus1;
	}

	public Double getVolBonus1(){
		return this.volBonus1;
	}

	public void setRentalLastYrNoa(Boolean rentalLastYrNoa){
		this.rentalLastYrNoa = rentalLastYrNoa;
	}

	public Boolean getRentalLastYrNoa(){
		return this.rentalLastYrNoa;
	}

	public void setInterestRate(Double interestRate){
		this.interestRate = interestRate;
	}

	public Double getInterestRate(){
		return this.interestRate;
	}

	public void setOutgoingQty(Double outgoingQty){
		this.outgoingQty = outgoingQty;
	}

	public Double getOutgoingQty(){
		return this.outgoingQty;
	}

	public void setImageMedium(String imageMedium){
		this.imageMedium = imageMedium;
	}

	public String getImageMedium(){
		return this.imageMedium;
	}

	public void setMaxMortgageAllowed(Double maxMortgageAllowed){
		this.maxMortgageAllowed = maxMortgageAllowed;
	}

	public Double getMaxMortgageAllowed(){
		return this.maxMortgageAllowed;
	}

	public void setPrepayToReduce(Boolean prepayToReduce){
		this.prepayToReduce = prepayToReduce;
	}

	public Boolean getPrepayToReduce(){
		return this.prepayToReduce;
	}

	public void setLocCase(String locCase){
		this.locCase = locCase;
	}

	public String getLocCase(){
		return this.locCase;
	}

	public void setVolBonus5Threshold(Double volBonus5Threshold){
		this.volBonus5Threshold = volBonus5Threshold;
	}

	public Double getVolBonus5Threshold(){
		return this.volBonus5Threshold;
	}

	public void setCode(String code){
		this.code = code;
	}

	public String getCode(){
		return this.code;
	}

	public void setTrackProduction(Boolean trackProduction){
		this.trackProduction = trackProduction;
	}

	public Boolean getTrackProduction(){
		return this.trackProduction;
	}

	public void setPriceMargin(Double priceMargin){
		this.priceMargin = priceMargin;
	}

	public Double getPriceMargin(){
		return this.priceMargin;
	}

	public void setCostMethod(String costMethod){
		this.costMethod = costMethod;
	}

	public String getCostMethod(){
		return this.costMethod;
	}

	public void setAllowProvinces(String allowProvinces){
		this.allowProvinces = allowProvinces;
	}

	public String getAllowProvinces(){
		return this.allowProvinces;
	}

	public void setSupplyMethod(String supplyMethod){
		this.supplyMethod = supplyMethod;
	}

	public String getSupplyMethod(){
		return this.supplyMethod;
	}

	public void setActive(Boolean active){
		this.active = active;
	}

	public Boolean getActive(){
		return this.active;
	}

	public void setBoardingHouses(Boolean boardingHouses){
		this.boardingHouses = boardingHouses;
	}

	public Boolean getBoardingHouses(){
		return this.boardingHouses;
	}

	public void setAllowedOnBridge(Boolean allowedOnBridge){
		this.allowedOnBridge = allowedOnBridge;
	}

	public Boolean getAllowedOnBridge(){
		return this.allowedOnBridge;
	}

	public void setVtbMaxLtv(Double vtbMaxLtv){
		this.vtbMaxLtv = vtbMaxLtv;
	}

	public Double getVtbMaxLtv(){
		return this.vtbMaxLtv;
	}

	public void setT1GeneralFormula(Boolean t1GeneralFormula){
		this.t1GeneralFormula = t1GeneralFormula;
	}

	public Boolean getT1GeneralFormula(){
		return this.t1GeneralFormula;
	}

	public void setDefaultCode(String defaultCode){
		this.defaultCode = defaultCode;
	}

	public String getDefaultCode(){
		return this.defaultCode;
	}

	public void setCoOperativeHousing(Boolean coOperativeHousing){
		this.coOperativeHousing = coOperativeHousing;
	}

	public Boolean getCoOperativeHousing(){
		return this.coOperativeHousing;
	}

	public void setRental2YrAvgNoa(Boolean rental2YrAvgNoa){
		this.rental2YrAvgNoa = rental2YrAvgNoa;
	}

	public Boolean getRental2YrAvgNoa(){
		return this.rental2YrAvgNoa;
	}

	public void setQualifyingRate(Double qualifyingRate){
		this.qualifyingRate = qualifyingRate;
	}

	public Double getQualifyingRate(){
		return this.qualifyingRate;
	}

	public void setWeight(Double weight){
		this.weight = weight;
	}

	public Double getWeight(){
		return this.weight;
	}

	public void setTrailerCommission(Double trailerCommission){
		this.trailerCommission = trailerCommission;
	}

	public Double getTrailerCommission(){
		return this.trailerCommission;
	}

	public void setRoomingHouses(Boolean roomingHouses){
		this.roomingHouses = roomingHouses;
	}

	public Boolean getRoomingHouses(){
		return this.roomingHouses;
	}

	public void setProductName(String productName){
		this.productName = productName;
	}

	public String getProductName(){
		return this.productName;
	}

	public void setChildSupportTreatment(String childSupportTreatment){
		this.childSupportTreatment = childSupportTreatment;
	}

	public String getChildSupportTreatment(){
		return this.childSupportTreatment;
	}

	public void setAllowedRedFlagsPoor(Double allowedRedFlagsPoor){
		this.allowedRedFlagsPoor = allowedRedFlagsPoor;
	}

	public Double getAllowedRedFlagsPoor(){
		return this.allowedRedFlagsPoor;
	}

	public void setImmigrantMaxLtv(Double immigrantMaxLtv){
		this.immigrantMaxLtv = immigrantMaxLtv;
	}

	public Double getImmigrantMaxLtv(){
		return this.immigrantMaxLtv;
	}

	public void setAllowedGreyFlagsGood(Double allowedGreyFlagsGood){
		this.allowedGreyFlagsGood = allowedGreyFlagsGood;
	}

	public Double getAllowedGreyFlagsGood(){
		return this.allowedGreyFlagsGood;
	}

	public void setAllowedGreyFlagsPoor(Double allowedGreyFlagsPoor){
		this.allowedGreyFlagsPoor = allowedGreyFlagsPoor;
	}

	public Double getAllowedGreyFlagsPoor(){
		return this.allowedGreyFlagsPoor;
	}

	public void setAllowPowerOfAttorney(Boolean allowPowerOfAttorney){
		this.allowPowerOfAttorney = allowPowerOfAttorney;
	}

	public Boolean getAllowPowerOfAttorney(){
		return this.allowPowerOfAttorney;
	}

	public void setRevolvingDebtPayments(String revolvingDebtPayments){
		this.revolvingDebtPayments = revolvingDebtPayments;
	}

	public String getRevolvingDebtPayments(){
		return this.revolvingDebtPayments;
	}

	public void setAllowedOn1st(Boolean allowedOn1st){
		this.allowedOn1st = allowedOn1st;
	}

	public Boolean getAllowedOn1st(){
		return this.allowedOn1st;
	}

	public void setRentalLtvCutOffGrt(Double rentalLtvCutOffGrt){
		this.rentalLtvCutOffGrt = rentalLtvCutOffGrt;
	}

	public Double getRentalLtvCutOffGrt(){
		return this.rentalLtvCutOffGrt;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setIsProductInsured(Boolean isProductInsured){
		this.isProductInsured = isProductInsured;
	}

	public Boolean getIsProductInsured(){
		return this.isProductInsured;
	}

	public void setResLtvCutOffGrt(Double resLtvCutOffGrt){
		this.resLtvCutOffGrt = resLtvCutOffGrt;
	}

	public Double getResLtvCutOffGrt(){
		return this.resLtvCutOffGrt;
	}

	public void setPurchaseOk(Boolean purchaseOk){
		this.purchaseOk = purchaseOk;
	}

	public Boolean getPurchaseOk(){
		return this.purchaseOk;
	}

	public void setMaxPropertyAge(Integer maxPropertyAge){
		this.maxPropertyAge = maxPropertyAge;
	}

	public Integer getMaxPropertyAge(){
		return this.maxPropertyAge;
	}

	public void setMaxBorrowAllow(Double maxBorrowAllow){
		this.maxBorrowAllow = maxBorrowAllow;
	}

	public Double getMaxBorrowAllow(){
		return this.maxBorrowAllow;
	}

	public void setBiWeeklyPayments(Boolean biWeeklyPayments){
		this.biWeeklyPayments = biWeeklyPayments;
	}

	public Boolean getBiWeeklyPayments(){
		return this.biWeeklyPayments;
	}

	public void setMaxAllowProperty(Double maxAllowProperty){
		this.maxAllowProperty = maxAllowProperty;
	}

	public Double getMaxAllowProperty(){
		return this.maxAllowProperty;
	}

	public void setPayStubProrate(Boolean payStubProrate){
		this.payStubProrate = payStubProrate;
	}

	public Boolean getPayStubProrate(){
		return this.payStubProrate;
	}

	public void setClosingPeriod(Integer closingPeriod){
		this.closingPeriod = closingPeriod;
	}

	public Integer getClosingPeriod(){
		return this.closingPeriod;
	}

	public void setSeGrossupPercent(Double seGrossupPercent){
		this.seGrossupPercent = seGrossupPercent;
	}

	public Double getSeGrossupPercent(){
		return this.seGrossupPercent;
	}

	public void setAgricultural(Boolean agricultural){
		this.agricultural = agricultural;
	}

	public Boolean getAgricultural(){
		return this.agricultural;
	}

	public void setRentalIncomeTreatment(String rentalIncomeTreatment){
		this.rentalIncomeTreatment = rentalIncomeTreatment;
	}

	public String getRentalIncomeTreatment(){
		return this.rentalIncomeTreatment;
	}

	public void setMinBeacon(Double minBeacon){
		this.minBeacon = minBeacon;
	}

	public Double getMinBeacon(){
		return this.minBeacon;
	}

	public void setMaximumAmortization(Integer maximumAmortization){
		this.maximumAmortization = maximumAmortization;
	}

	public Integer getMaximumAmortization(){
		return this.maximumAmortization;
	}

	public void setColor(Integer color){
		this.color = color;
	}

	public Integer getColor(){
		return this.color;
	}

	public void setLstPrice(Double lstPrice){
		this.lstPrice = lstPrice;
	}

	public Double getLstPrice(){
		return this.lstPrice;
	}

	public void setDeliveryCount(Integer deliveryCount){
		this.deliveryCount = deliveryCount;
	}

	public Integer getDeliveryCount(){
		return this.deliveryCount;
	}

	public void setMaxNumberOfDraws(Integer maxNumberOfDraws){
		this.maxNumberOfDraws = maxNumberOfDraws;
	}

	public Integer getMaxNumberOfDraws(){
		return this.maxNumberOfDraws;
	}

	public void setRewardPoints(Boolean rewardPoints){
		this.rewardPoints = rewardPoints;
	}

	public Boolean getRewardPoints(){
		return this.rewardPoints;
	}

	public void setImmigrantMaxMo(Double immigrantMaxMo){
		this.immigrantMaxMo = immigrantMaxMo;
	}

	public Double getImmigrantMaxMo(){
		return this.immigrantMaxMo;
	}

	public void setConstructionProduct(Boolean constructionProduct){
		this.constructionProduct = constructionProduct;
	}

	public Boolean getConstructionProduct(){
		return this.constructionProduct;
	}

	public void setMessageSummary(String messageSummary){
		this.messageSummary = messageSummary;
	}

	public String getMessageSummary(){
		return this.messageSummary;
	}

	public void setAllow2ndHomes(Boolean allow2ndHomes){
		this.allow2ndHomes = allow2ndHomes;
	}

	public Boolean getAllow2ndHomes(){
		return this.allow2ndHomes;
	}

	public void setLeasedLand(Boolean leasedLand){
		this.leasedLand = leasedLand;
	}

	public Boolean getLeasedLand(){
		return this.leasedLand;
	}

	public void setEquifaxScoringUsed(String equifaxScoringUsed){
		this.equifaxScoringUsed = equifaxScoringUsed;
	}

	public String getEquifaxScoringUsed(){
		return this.equifaxScoringUsed;
	}

	public void setMonthsInterestPenalty(Integer monthsInterestPenalty){
		this.monthsInterestPenalty = monthsInterestPenalty;
	}

	public Integer getMonthsInterestPenalty(){
		return this.monthsInterestPenalty;
	}

	public void setBusinessEase(Double businessEase){
		this.businessEase = businessEase;
	}

	public Double getBusinessEase(){
		return this.businessEase;
	}

	public void setExtraAppliedAnyTime(Boolean extraAppliedAnyTime){
		this.extraAppliedAnyTime = extraAppliedAnyTime;
	}

	public Boolean getExtraAppliedAnyTime(){
		return this.extraAppliedAnyTime;
	}

	public void setLetterOfEmployment(Boolean letterOfEmployment){
		this.letterOfEmployment = letterOfEmployment;
	}

	public Boolean getLetterOfEmployment(){
		return this.letterOfEmployment;
	}

	public void setAllowDuplex(Boolean allowDuplex){
		this.allowDuplex = allowDuplex;
	}

	public Boolean getAllowDuplex(){
		return this.allowDuplex;
	}

	public void setAllowEightPlex(Boolean allowEightPlex){
		this.allowEightPlex = allowEightPlex;
	}

	public Boolean getAllowEightPlex(){
		return this.allowEightPlex;
	}

	public void setSellerDelay(Integer sellerDelay){
		this.sellerDelay = sellerDelay;
	}

	public Integer getSellerDelay(){
		return this.sellerDelay;
	}

	public void setMinBeaconCutOff(Double minBeaconCutOff){
		this.minBeaconCutOff = minBeaconCutOff;
	}

	public Double getMinBeaconCutOff(){
		return this.minBeaconCutOff;
	}

	public void setCommercial(Boolean commercial){
		this.commercial = commercial;
	}

	public Boolean getCommercial(){
		return this.commercial;
	}

	public void setFractionalInterests(Boolean fractionalInterests){
		this.fractionalInterests = fractionalInterests;
	}

	public Boolean getFractionalInterests(){
		return this.fractionalInterests;
	}

	public void setMinHeatCost(Double minHeatCost){
		this.minHeatCost = minHeatCost;
	}

	public Double getMinHeatCost(){
		return this.minHeatCost;
	}

	public void setRentalLtvCutOffLes(Double rentalLtvCutOffLes){
		this.rentalLtvCutOffLes = rentalLtvCutOffLes;
	}

	public Double getRentalLtvCutOffLes(){
		return this.rentalLtvCutOffLes;
	}

	public void setMedTdsGreaterEqualSplit(Double medTdsGreaterEqualSplit){
		this.medTdsGreaterEqualSplit = medTdsGreaterEqualSplit;
	}

	public Double getMedTdsGreaterEqualSplit(){
		return this.medTdsGreaterEqualSplit;
	}

	public void setMortgagePayment(Integer mortgagePayment){
		this.mortgagePayment = mortgagePayment;
	}

	public Integer getMortgagePayment(){
		return this.mortgagePayment;
	}

	public void setWeeklyPayments(Boolean weeklyPayments){
		this.weeklyPayments = weeklyPayments;
	}

	public Boolean getWeeklyPayments(){
		return this.weeklyPayments;
	}

	public void setMarketingCommission(Double marketingCommission){
		this.marketingCommission = marketingCommission;
	}

	public Double getMarketingCommission(){
		return this.marketingCommission;
	}

	public void setResLtvCutoffLes(Double resLtvCutoffLes){
		this.resLtvCutoffLes = resLtvCutoffLes;
	}

	public Double getResLtvCutoffLes(){
		return this.resLtvCutoffLes;
	}

	public void setGoodCredit12MoLates(Integer goodCredit12MoLates){
		this.goodCredit12MoLates = goodCredit12MoLates;
	}

	public Integer getGoodCredit12MoLates(){
		return this.goodCredit12MoLates;
	}

	public void setMaxGiftAllow(Double maxGiftAllow){
		this.maxGiftAllow = maxGiftAllow;
	}

	public Double getMaxGiftAllow(){
		return this.maxGiftAllow;
	}

	public void setWaivePenaltyIfRetain(Boolean waivePenaltyIfRetain){
		this.waivePenaltyIfRetain = waivePenaltyIfRetain;
	}

	public Boolean getWaivePenaltyIfRetain(){
		return this.waivePenaltyIfRetain;
	}

	public void setReceptionCount(Integer receptionCount){
		this.receptionCount = receptionCount;
	}

	public Integer getReceptionCount(){
		return this.receptionCount;
	}

	public void setPoorCreditBeaconSplit(Double poorCreditBeaconSplit){
		this.poorCreditBeaconSplit = poorCreditBeaconSplit;
	}

	public Double getPoorCreditBeaconSplit(){
		return this.poorCreditBeaconSplit;
	}

	public void setTwoYrAverageNoa(Boolean twoYrAverageNoa){
		this.twoYrAverageNoa = twoYrAverageNoa;
	}

	public Boolean getTwoYrAverageNoa(){
		return this.twoYrAverageNoa;
	}

	public void setAgeRestrictedLtv(Double ageRestrictedLtv){
		this.ageRestrictedLtv = ageRestrictedLtv;
	}

	public Double getAgeRestrictedLtv(){
		return this.ageRestrictedLtv;
	}

	public void setGreatGdsGreaterEqualSplit(Double greatGdsGreaterEqualSplit){
		this.greatGdsGreaterEqualSplit = greatGdsGreaterEqualSplit;
	}

	public Double getGreatGdsGreaterEqualSplit(){
		return this.greatGdsGreaterEqualSplit;
	}

	public void setSmCenterLtvCutOffGrt(Double smCenterLtvCutOffGrt){
		this.smCenterLtvCutOffGrt = smCenterLtvCutOffGrt;
	}

	public Double getSmCenterLtvCutOffGrt(){
		return this.smCenterLtvCutOffGrt;
	}

	public void setCashback(Double cashback){
		this.cashback = cashback;
	}

	public Double getCashback(){
		return this.cashback;
	}

	public void setSellerId(Integer sellerId){
		this.sellerId = sellerId;
	}

	public Integer getSellerId(){
		return this.sellerId;
	}

	public void setWarranty(Double warranty){
		this.warranty = warranty;
	}

	public Double getWarranty(){
		return this.warranty;
	}

	public void setLender(Integer lender){
		this.lender = lender;
	}

	public Integer getLender(){
		return this.lender;
	}

	public void setModularHomes(Boolean modularHomes){
		this.modularHomes = modularHomes;
	}

	public Boolean getModularHomes(){
		return this.modularHomes;
	}

	public void setPosition(String position){
		this.position = position;
	}

	public String getPosition(){
		return this.position;
	}

	public void setMinSqAllowCondo(Integer minSqAllowCondo){
		this.minSqAllowCondo = minSqAllowCondo;
	}

	public Integer getMinSqAllowCondo(){
		return this.minSqAllowCondo;
	}

	public void setRequirDistanceToCity(Double requirDistanceToCity){
		this.requirDistanceToCity = requirDistanceToCity;
	}

	public Double getRequirDistanceToCity(){
		return this.requirDistanceToCity;
	}

	public void setLenderFeesPercent(Double lenderFeesPercent){
		this.lenderFeesPercent = lenderFeesPercent;
	}

	public Double getLenderFeesPercent(){
		return this.lenderFeesPercent;
	}

	public void setImmigrantMinEmpMo(Double immigrantMinEmpMo){
		this.immigrantMinEmpMo = immigrantMinEmpMo;
	}

	public Double getImmigrantMinEmpMo(){
		return this.immigrantMinEmpMo;
	}

	public void setCottageMinBeacon(Double cottageMinBeacon){
		this.cottageMinBeacon = cottageMinBeacon;
	}

	public Double getCottageMinBeacon(){
		return this.cottageMinBeacon;
	}

	public void setNameTemplate(String nameTemplate){
		this.nameTemplate = nameTemplate;
	}

	public String getNameTemplate(){
		return this.nameTemplate;
	}

	public void setStatedIncomeMinBeacon(Double statedIncomeMinBeacon){
		this.statedIncomeMinBeacon = statedIncomeMinBeacon;
	}

	public Double getStatedIncomeMinBeacon(){
		return this.statedIncomeMinBeacon;
	}

	public void setAvgDiffUsed(Double avgDiffUsed){
		this.avgDiffUsed = avgDiffUsed;
	}

	public Double getAvgDiffUsed(){
		return this.avgDiffUsed;
	}

	public void setGoodCreditBeaconSplit(Integer goodCreditBeaconSplit){
		this.goodCreditBeaconSplit = goodCreditBeaconSplit;
	}

	public Integer getGoodCreditBeaconSplit(){
		return this.goodCreditBeaconSplit;
	}

	public void setCanadianMilitaryAllow(Boolean canadianMilitaryAllow){
		this.canadianMilitaryAllow = canadianMilitaryAllow;
	}

	public Boolean getCanadianMilitaryAllow(){
		return this.canadianMilitaryAllow;
	}

	public void setRemoveLoansDonePreClose(Boolean removeLoansDonePreClose){
		this.removeLoansDonePreClose = removeLoansDonePreClose;
	}

	public Boolean getRemoveLoansDonePreClose(){
		return this.removeLoansDonePreClose;
	}

	public void setMaximumApplicants(Double maximumApplicants){
		this.maximumApplicants = maximumApplicants;
	}

	public Double getMaximumApplicants(){
		return this.maximumApplicants;
	}

	public void setTwoYrAvgNoaPercent(Boolean twoYrAvgNoaPercent){
		this.twoYrAvgNoaPercent = twoYrAvgNoaPercent;
	}

	public Boolean getTwoYrAvgNoaPercent(){
		return this.twoYrAvgNoaPercent;
	}

	public void setMinSqCutoffCondo(Integer minSqCutoffCondo){
		this.minSqCutoffCondo = minSqCutoffCondo;
	}

	public Integer getMinSqCutoffCondo(){
		return this.minSqCutoffCondo;
	}

	public void setAllowAgriculturalLessThen10Acres(Boolean allowAgriculturalLessThen10Acres){
		this.allowAgriculturalLessThen10Acres = allowAgriculturalLessThen10Acres;
	}

	public Boolean getAllowAgriculturalLessThen10Acres(){
		return this.allowAgriculturalLessThen10Acres;
	}

	public void setLocQualifyingRate(String locQualifyingRate){
		this.locQualifyingRate = locQualifyingRate;
	}

	public String getLocQualifyingRate(){
		return this.locQualifyingRate;
	}

	public void setAvgProcessingSpeed(Double avgProcessingSpeed){
		this.avgProcessingSpeed = avgProcessingSpeed;
	}

	public Double getAvgProcessingSpeed(){
		return this.avgProcessingSpeed;
	}

	public void setSaleDelay(Double saleDelay){
		this.saleDelay = saleDelay;
	}

	public Double getSaleDelay(){
		return this.saleDelay;
	}

	public void setOutbuildingIncluded(Integer outbuildingIncluded){
		this.outbuildingIncluded = outbuildingIncluded;
	}

	public Integer getOutbuildingIncluded(){
		return this.outbuildingIncluded;
	}

	public void setStatedIncomeMaxLtv(Double statedIncomeMaxLtv){
		this.statedIncomeMaxLtv = statedIncomeMaxLtv;
	}

	public Double getStatedIncomeMaxLtv(){
		return this.statedIncomeMaxLtv;
	}

	public void setMinSqCutoffHouse(Integer minSqCutoffHouse){
		this.minSqCutoffHouse = minSqCutoffHouse;
	}

	public Integer getMinSqCutoffHouse(){
		return this.minSqCutoffHouse;
	}

	public void setNonResidentAllow(Boolean nonResidentAllow){
		this.nonResidentAllow = nonResidentAllow;
	}

	public Boolean getNonResidentAllow(){
		return this.nonResidentAllow;
	}

	public void setLenderLine(List<Lender> lenderLine){
		this.lenderLine = lenderLine;
	}

	public List<Lender> getLenderLine(){
		return this.lenderLine;
	}

	public void setBeaconTdsCutOff(Double beaconTdsCutOff){
		this.beaconTdsCutOff = beaconTdsCutOff;
	}

	public Double getBeaconTdsCutOff(){
		return this.beaconTdsCutOff;
	}

	public void setMinMortgageAllowed(Double minMortgageAllowed){
		this.minMortgageAllowed = minMortgageAllowed;
	}

	public Double getMinMortgageAllowed(){
		return this.minMortgageAllowed;
	}

	public void setGreatTdsLessSplit(Double greatTdsLessSplit){
		this.greatTdsLessSplit = greatTdsLessSplit;
	}

	public Double getGreatTdsLessSplit(){
		return this.greatTdsLessSplit;
	}

	public void setMessageUnread(Boolean messageUnread){
		this.messageUnread = messageUnread;
	}

	public Boolean getMessageUnread(){
		return this.messageUnread;
	}

	public void setProcureMethod(String procureMethod){
		this.procureMethod = procureMethod;
	}

	public String getProcureMethod(){
		return this.procureMethod;
	}

	public void setMaxAcreageAllowed(Double maxAcreageAllowed){
		this.maxAcreageAllowed = maxAcreageAllowed;
	}

	public Double getMaxAcreageAllowed(){
		return this.maxAcreageAllowed;
	}

	public void setLocRack(String locRack){
		this.locRack = locRack;
	}

	public String getLocRack(){
		return this.locRack;
	}

	public void setApplicationMethod(String applicationMethod){
		this.applicationMethod = applicationMethod;
	}

	public String getApplicationMethod(){
		return this.applicationMethod;
	}

	public void setCondo(Boolean condo){
		this.condo = condo;
	}

	public Boolean getCondo(){
		return this.condo;
	}

	public void setLenderFeesFlat(Double lenderFeesFlat){
		this.lenderFeesFlat = lenderFeesFlat;
	}

	public Double getLenderFeesFlat(){
		return this.lenderFeesFlat;
	}

	public void setMesType(String mesType){
		this.mesType = mesType;
	}

	public String getMesType(){
		return this.mesType;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return this.type;
	}

	public void setUosCoeff(Double uosCoeff){
		this.uosCoeff = uosCoeff;
	}

	public Double getUosCoeff(){
		return this.uosCoeff;
	}

	public void setAllowFourPlex(Boolean allowFourPlex){
		this.allowFourPlex = allowFourPlex;
	}

	public Boolean getAllowFourPlex(){
		return this.allowFourPlex;
	}

	public void setMonthlyPayments(Boolean monthlyPayments){
		this.monthlyPayments = monthlyPayments;
	}

	public Boolean getMonthlyPayments(){
		return this.monthlyPayments;
	}

	public void setCottageRecProperty(Boolean cottageRecProperty){
		this.cottageRecProperty = cottageRecProperty;
	}

	public Boolean getCottageRecProperty(){
		return this.cottageRecProperty;
	}

	public void setVirtualAvailable(Double virtualAvailable){
		this.virtualAvailable = virtualAvailable;
	}

	public Double getVirtualAvailable(){
		return this.virtualAvailable;
	}

	public void setPoorGdsLessSplit(Double poorGdsLessSplit){
		this.poorGdsLessSplit = poorGdsLessSplit;
	}

	public Double getPoorGdsLessSplit(){
		return this.poorGdsLessSplit;
	}

	public void setCottageInsureLtv(Double cottageInsureLtv){
		this.cottageInsureLtv = cottageInsureLtv;
	}

	public Double getCottageInsureLtv(){
		return this.cottageInsureLtv;
	}

	public void setActiveCreditTradesRequired(Double activeCreditTradesRequired){
		this.activeCreditTradesRequired = activeCreditTradesRequired;
	}

	public Double getActiveCreditTradesRequired(){
		return this.activeCreditTradesRequired;
	}

	public void setVolBonus2Threshold(Double volBonus2Threshold){
		this.volBonus2Threshold = volBonus2Threshold;
	}

	public Double getVolBonus2Threshold(){
		return this.volBonus2Threshold;
	}

	public void setRentalPools(Boolean rentalPools){
		this.rentalPools = rentalPools;
	}

	public Boolean getRentalPools(){
		return this.rentalPools;
	}

	public void setExtraAnnualPaydown(Boolean extraAnnualPaydown){
		this.extraAnnualPaydown = extraAnnualPaydown;
	}

	public Boolean getExtraAnnualPaydown(){
		return this.extraAnnualPaydown;
	}

	public void setWhoseIncomeUsed(String whoseIncomeUsed){
		this.whoseIncomeUsed = whoseIncomeUsed;
	}

	public String getWhoseIncomeUsed(){
		return this.whoseIncomeUsed;
	}

	public void setChildTaxCredit(Double childTaxCredit){
		this.childTaxCredit = childTaxCredit;
	}

	public Double getChildTaxCredit(){
		return this.childTaxCredit;
	}

	public void setAllowedRedFlagsMed(Double allowedRedFlagsMed){
		this.allowedRedFlagsMed = allowedRedFlagsMed;
	}

	public Double getAllowedRedFlagsMed(){
		return this.allowedRedFlagsMed;
	}

	public void setChargesAllowedBehind(Boolean chargesAllowedBehind){
		this.chargesAllowedBehind = chargesAllowedBehind;
	}

	public Boolean getChargesAllowedBehind(){
		return this.chargesAllowedBehind;
	}

	public void setOpenClosed(String openClosed){
		this.openClosed = openClosed;
	}

	public String getOpenClosed(){
		return this.openClosed;
	}

	public void setRental(Boolean rental){
		this.rental = rental;
	}

	public Boolean getRental(){
		return this.rental;
	}

	public void setRemoveLoanMoRemaining(Double removeLoanMoRemaining){
		this.removeLoanMoRemaining = removeLoanMoRemaining;
	}

	public Double getRemoveLoanMoRemaining(){
		return this.removeLoanMoRemaining;
	}

	public void setQtyAvailable(Double qtyAvailable){
		this.qtyAvailable = qtyAvailable;
	}

	public Double getQtyAvailable(){
		return this.qtyAvailable;
	}

	public void setRentalIncomePercent(Double rentalIncomePercent){
		this.rentalIncomePercent = rentalIncomePercent;
	}

	public Double getRentalIncomePercent(){
		return this.rentalIncomePercent;
	}

	public void setAllowSelfEmpIncome(Boolean allowSelfEmpIncome){
		this.allowSelfEmpIncome = allowSelfEmpIncome;
	}

	public Boolean getAllowSelfEmpIncome(){
		return this.allowSelfEmpIncome;
	}

	public void setDiffLength(String diffLength){
		this.diffLength = diffLength;
	}

	public String getDiffLength(){
		return this.diffLength;
	}

	public void setAllowedOn2nd(Boolean allowedOn2nd){
		this.allowedOn2nd = allowedOn2nd;
	}

	public Boolean getAllowedOn2nd(){
		return this.allowedOn2nd;
	}

	public void setSellerQty(Double sellerQty){
		this.sellerQty = sellerQty;
	}

	public Double getSellerQty(){
		return this.sellerQty;
	}

	public void setAllowSixPlex(Boolean allowSixPlex){
		this.allowSixPlex = allowSixPlex;
	}

	public Boolean getAllowSixPlex(){
		return this.allowSixPlex;
	}

	public void setGreatGdsLessSplit(Double greatGdsLessSplit){
		this.greatGdsLessSplit = greatGdsLessSplit;
	}

	public Double getGreatGdsLessSplit(){
		return this.greatGdsLessSplit;
	}

	public void setState(String state){
		this.state = state;
	}

	public String getState(){
		return this.state;
	}

	public void setImageSmall(String imageSmall){
		this.imageSmall = imageSmall;
	}

	public String getImageSmall(){
		return this.imageSmall;
	}

	public void setBranchSigning(Boolean branchSigning){
		this.branchSigning = branchSigning;
	}

	public Boolean getBranchSigning(){
		return this.branchSigning;
	}

	public void setGreatTdsGreaterEqualSplit(Double greatTdsGreaterEqualSplit){
		this.greatTdsGreaterEqualSplit = greatTdsGreaterEqualSplit;
	}

	public Double getGreatTdsGreaterEqualSplit(){
		return this.greatTdsGreaterEqualSplit;
	}

	public void setBaseCommission(Double baseCommission){
		this.baseCommission = baseCommission;
	}

	public Double getBaseCommission(){
		return this.baseCommission;
	}

	public void setSmCenterLtvCutOffLes(Double smCenterLtvCutOffLes){
		this.smCenterLtvCutOffLes = smCenterLtvCutOffLes;
	}

	public Double getSmCenterLtvCutOffLes(){
		return this.smCenterLtvCutOffLes;
	}

	public void setIncomingQty(Double incomingQty){
		this.incomingQty = incomingQty;
	}

	public Double getIncomingQty(){
		return this.incomingQty;
	}

	public void setAllowedGreyFlagsMed(Double allowedGreyFlagsMed){
		this.allowedGreyFlagsMed = allowedGreyFlagsMed;
	}

	public Double getAllowedGreyFlagsMed(){
		return this.allowedGreyFlagsMed;
	}

	public void setLifeLeasedProperty(Boolean lifeLeasedProperty){
		this.lifeLeasedProperty = lifeLeasedProperty;
	}

	public Boolean getLifeLeasedProperty(){
		return this.lifeLeasedProperty;
	}

	public void setFloatingHomes(Boolean floatingHomes){
		this.floatingHomes = floatingHomes;
	}

	public Boolean getFloatingHomes(){
		return this.floatingHomes;
	}

	public void setIsPenaltyGreater(Boolean isPenaltyGreater){
		this.isPenaltyGreater = isPenaltyGreater;
	}

	public Boolean getIsPenaltyGreater(){
		return this.isPenaltyGreater;
	}

	public void setRawLand(Boolean rawLand){
		this.rawLand = rawLand;
	}

	public Boolean getRawLand(){
		return this.rawLand;
	}

	public void setSaleOk(Boolean saleOk){
		this.saleOk = saleOk;
	}

	public Boolean getSaleOk(){
		return this.saleOk;
	}

	public void setTerm(String term){
		this.term = term;
	}

	public String getTerm(){
		return this.term;
	}

	public void setPoorCredit12MoLates(Double poorCredit12MoLates){
		this.poorCredit12MoLates = poorCredit12MoLates;
	}

	public Double getPoorCredit12MoLates(){
		return this.poorCredit12MoLates;
	}

	public void setPartnerRef(String partnerRef){
		this.partnerRef = partnerRef;
	}

	public String getPartnerRef(){
		return this.partnerRef;
	}

	public void setIncreasePayments(Boolean increasePayments){
		this.increasePayments = increasePayments;
	}

	public Boolean getIncreasePayments(){
		return this.increasePayments;
	}

	public void setStandardPrice(Double standardPrice){
		this.standardPrice = standardPrice;
	}

	public Double getStandardPrice(){
		return this.standardPrice;
	}

	public void setHighRatio2ndHome(Boolean highRatio2ndHome){
		this.highRatio2ndHome = highRatio2ndHome;
	}

	public Boolean getHighRatio2ndHome(){
		return this.highRatio2ndHome;
	}

	public Integer getQuickCloseDuration() {
		return quickCloseDuration;
	}

	public void setQuickCloseDuration(Integer quickCloseDuration) {
		this.quickCloseDuration = quickCloseDuration;
	}

	public String getTreatmentOfRental() {
		return treatmentOfRental;
	}

	public void setTreatmentOfRental(String treatmentOfRental) {
		this.treatmentOfRental = treatmentOfRental;
	}

	public boolean isAllowRawLand() {
		return allowRawLand;
	}

	public void setAllowRawLand(boolean allowRawLand) {
		this.allowRawLand = allowRawLand;
	}

	public boolean isAgriculturalLessThen10Acres() {
		return agriculturalLessThen10Acres;
	}

	public void setAgriculturalLessThen10Acres(boolean agriculturalLessThen10Acres) {
		this.agriculturalLessThen10Acres = agriculturalLessThen10Acres;
	}

	public Boolean getOutstandingCollectionPayment() {
		return outstandingCollectionPayment;
	}

	public void setOutstandingCollectionPayment(Boolean outstandingCollectionPayment) {
		this.outstandingCollectionPayment = outstandingCollectionPayment;
	}

	public Double getPostedRate() {
		return postedRate;
	}

	public void setPostedRate(Double postedRate) {
		this.postedRate = postedRate;
	}



//	@Override
//	public String toString() {
//		return "Product [poorTdsGreaterEqualSplit=" + poorTdsGreaterEqualSplit
//				+ ", semiMonthlyPayments=" + semiMonthlyPayments
//				+ ", ageRestricted=" + ageRestricted + ", descriptionSale="
//				+ descriptionSale + ", priceExtra=" + priceExtra
//				+ ", volBonus1Threshold=" + volBonus1Threshold
//				+ ", lineOfCredit=" + lineOfCredit + ", locRow=" + locRow
//				+ ", brokerFeesPercent=" + brokerFeesPercent
//				+ ", cappedVariable=" + cappedVariable + ", variants="
//				+ variants + ", lastYrNoa=" + lastYrNoa + ", growOps="
//				+ growOps + ", medGdsLessSplit=" + medGdsLessSplit
//				+ ", doubleUpPayments=" + doubleUpPayments
//				+ ", uninsuredConv2ndHome=" + uninsuredConv2ndHome + ", image="
//				+ image + ", allowTitleIns=" + allowTitleIns
//				+ ", livingAllowance=" + livingAllowance
//				+ ", mthsFromDischargeAllowable=" + mthsFromDischargeAllowable
//				+ ", price=" + price + ", cities=" + cities
//				+ ", smCenterConCutOff=" + smCenterConCutOff
//				+ ", countryResidential=" + countryResidential
//				+ ", messageIsFollower=" + messageIsFollower
//				+ ", smallCenterInsureLtv=" + smallCenterInsureLtv
//				+ ", weightNet=" + weightNet + ", cottageMaxLtv="
//				+ cottageMaxLtv + ", descriptionPurchase="
//				+ descriptionPurchase + ", rentalConCutOff=" + rentalConCutOff
//				+ ", volBonus4Threshold=" + volBonus4Threshold
//				+ ", nonConvConstruction=" + nonConvConstruction
//				+ ", maxAcreageCutoff=" + maxAcreageCutoff
//				+ ", minSqAllowHouse=" + minSqAllowHouse + ", rentalProperty="
//				+ rentalProperty + ", produceDelay=" + produceDelay
//				+ ", listPrice=" + listPrice + ", underwriterPref="
//				+ underwriterPref + ", poorGdsGreaterEqualSplit="
//				+ poorGdsGreaterEqualSplit + ", vehicleAllowance="
//				+ vehicleAllowance + ", beaconTdsSplit=" + beaconTdsSplit
//				+ ", medGdsGreaterEqualSplit=" + medGdsGreaterEqualSplit
//				+ ", medTdsLessSplit=" + medTdsLessSplit + ", trackIncoming="
//				+ trackIncoming + ", selfBuildProduct=" + selfBuildProduct
//				+ ", volume=" + volume + ", specificLendersOn1st="
//				+ specificLendersOn1st + ", twoYrAvgNoa=" + twoYrAvgNoa
//				+ ", ean13=" + ean13 + ", trackOutgoing=" + trackOutgoing
//				+ ", resConCutoff=" + resConCutoff + ", brokerFeesFlat="
//				+ brokerFeesFlat + ", skipPaymentPossible="
//				+ skipPaymentPossible + ", allowedOn3rd=" + allowedOn3rd
//				+ ", shortAvgFundDay=" + shortAvgFundDay + ", mortgageAmount="
//				+ mortgageAmount + ", allowedRedFlagsGood="
//				+ allowedRedFlagsGood + ", poorTdsLessSplit="
//				+ poorTdsLessSplit + ", mobileHomes=" + mobileHomes
//				+ ", volBonus3Threshold=" + volBonus3Threshold + ", volBonus4="
//				+ volBonus4 + ", description=" + description + ", volBonus5="
//				+ volBonus5 + ", volBonus2=" + volBonus2 + ", volBonus3="
//				+ volBonus3 + ", volBonus1=" + volBonus1 + ", rentalLastYrNoa="
//				+ rentalLastYrNoa + ", interestRate=" + interestRate
//				+ ", outgoingQty=" + outgoingQty + ", imageMedium="
//				+ imageMedium + ", maxMortgageAllowed=" + maxMortgageAllowed
//				+ ", prepayToReduce=" + prepayToReduce + ", locCase=" + locCase
//				+ ", volBonus5Threshold=" + volBonus5Threshold + ", code="
//				+ code + ", trackProduction=" + trackProduction
//				+ ", priceMargin=" + priceMargin + ", allowProvinces="
//				+ allowProvinces + ", active=" + active + ", boardingHouses="
//				+ boardingHouses + ", allowedOnBridge=" + allowedOnBridge
//				+ ", vtbMaxLtv=" + vtbMaxLtv + ", t1GeneralFormula="
//				+ t1GeneralFormula + ", defaultCode=" + defaultCode
//				+ ", coOperativeHousing=" + coOperativeHousing
//				+ ", rental2YrAvgNoa=" + rental2YrAvgNoa + ", qualifyingRate="
//				+ qualifyingRate + ", weight=" + weight
//				+ ", trailerCommission=" + trailerCommission
//				+ ", roomingHouses=" + roomingHouses + ", productName="
//				+ productName + ", allowedRedFlagsPoor=" + allowedRedFlagsPoor
//				+ ", immigrantMaxLtv=" + immigrantMaxLtv
//				+ ", allowedGreyFlagsGood=" + allowedGreyFlagsGood
//				+ ", allowedGreyFlagsPoor=" + allowedGreyFlagsPoor
//				+ ", allowPowerOfAttorney=" + allowPowerOfAttorney
//				+ ", allowedOn1st=" + allowedOn1st + ", rentalLtvCutOffGrt="
//				+ rentalLtvCutOffGrt + ", name=" + name + ", isProductInsured="
//				+ isProductInsured + ", resLtvCutOffGrt=" + resLtvCutOffGrt
//				+ ", purchaseOk=" + purchaseOk + ", maxPropertyAge="
//				+ maxPropertyAge + ", maxBorrowAllow=" + maxBorrowAllow
//				+ ", biWeeklyPayments=" + biWeeklyPayments
//				+ ", maxAllowProperty=" + maxAllowProperty
//				+ ", payStubProrate=" + payStubProrate + ", closingPeriod="
//				+ closingPeriod + ", seGrossupPercent=" + seGrossupPercent
//				+ ", agricultural=" + agricultural + ", minBeacon=" + minBeacon
//				+ ", maximumAmortization=" + maximumAmortization + ", color="
//				+ color + ", lstPrice=" + lstPrice + ", deliveryCount="
//				+ deliveryCount + ", maxNumberOfDraws=" + maxNumberOfDraws
//				+ ", rewardPoints=" + rewardPoints + ", immigrantMaxMo="
//				+ immigrantMaxMo + ", constructionProduct="
//				+ constructionProduct + ", messageSummary=" + messageSummary
//				+ ", allow2ndHomes=" + allow2ndHomes + ", leasedLand="
//				+ leasedLand + ", monthsInterestPenalty="
//				+ monthsInterestPenalty + ", businessEase=" + businessEase
//				+ ", extraAppliedAnyTime=" + extraAppliedAnyTime
//				+ ", letterOfEmployment=" + letterOfEmployment
//				+ ", allowDuplex=" + allowDuplex + ", allowEightPlex="
//				+ allowEightPlex + ", sellerDelay=" + sellerDelay
//				+ ", minBeaconCutOff=" + minBeaconCutOff + ", commercial="
//				+ commercial + ", fractionalInterests=" + fractionalInterests
//				+ ", minHeatCost=" + minHeatCost + ", rentalLtvCutOffLes="
//				+ rentalLtvCutOffLes + ", medTdsGreaterEqualSplit="
//				+ medTdsGreaterEqualSplit + ", mortgagePayment="
//				+ mortgagePayment + ", weeklyPayments=" + weeklyPayments
//				+ ", marketingCommission=" + marketingCommission
//				+ ", resLtvCutoffLes=" + resLtvCutoffLes
//				+ ", goodCredit12MoLates=" + goodCredit12MoLates
//				+ ", maxGiftAllow=" + maxGiftAllow + ", waivePenaltyIfRetain="
//				+ waivePenaltyIfRetain + ", receptionCount=" + receptionCount
//				+ ", poorCreditBeaconSplit=" + poorCreditBeaconSplit
//				+ ", twoYrAverageNoa=" + twoYrAverageNoa
//				+ ", ageRestrictedLtv=" + ageRestrictedLtv
//				+ ", greatGdsGreaterEqualSplit=" + greatGdsGreaterEqualSplit
//				+ ", smCenterLtvCutOffGrt=" + smCenterLtvCutOffGrt
//				+ ", cashback=" + cashback + ", warranty=" + warranty
//				+ ", modularHomes=" + modularHomes + ", position=" + position
//				+ ", minSqAllowCondo=" + minSqAllowCondo
//				+ ", requirDistanceToCity=" + requirDistanceToCity
//				+ ", lenderFeesPercent=" + lenderFeesPercent
//				+ ", immigrantMinEmpMo=" + immigrantMinEmpMo
//				+ ", cottageMinBeacon=" + cottageMinBeacon + ", nameTemplate="
//				+ nameTemplate + ", statedIncomeMinBeacon="
//				+ statedIncomeMinBeacon + ", avgDiffUsed=" + avgDiffUsed
//				+ ", goodCreditBeaconSplit=" + goodCreditBeaconSplit
//				+ ", canadianMilitaryAllow=" + canadianMilitaryAllow
//				+ ", removeLoansDonePreClose=" + removeLoansDonePreClose
//				+ ", maximumApplicants=" + maximumApplicants
//				+ ", twoYrAvgNoaPercent=" + twoYrAvgNoaPercent
//				+ ", minSqCutoffCondo=" + minSqCutoffCondo
//				+ ", allowAgriculturalLessThen10Acres="
//				+ allowAgriculturalLessThen10Acres + ", avgProcessingSpeed="
//				+ avgProcessingSpeed + ", saleDelay=" + saleDelay
//				+ ", outbuildingIncluded=" + outbuildingIncluded
//				+ ", statedIncomeMaxLtv=" + statedIncomeMaxLtv
//				+ ", minSqCutoffHouse=" + minSqCutoffHouse
//				+ ", nonResidentAllow=" + nonResidentAllow
//				+ ", beaconTdsCutOff=" + beaconTdsCutOff
//				+ ", minMortgageAllowed=" + minMortgageAllowed
//				+ ", greatTdsLessSplit=" + greatTdsLessSplit
//				+ ", messageUnread=" + messageUnread + ", maxAcreageAllowed="
//				+ maxAcreageAllowed + ", locRack=" + locRack + ", condo="
//				+ condo + ", lenderFeesFlat=" + lenderFeesFlat + ", uosCoeff="
//				+ uosCoeff + ", allowFourPlex=" + allowFourPlex
//				+ ", monthlyPayments=" + monthlyPayments
//				+ ", cottageRecProperty=" + cottageRecProperty
//				+ ", virtualAvailable=" + virtualAvailable
//				+ ", poorGdsLessSplit=" + poorGdsLessSplit
//				+ ", cottageInsureLtv=" + cottageInsureLtv
//				+ ", activeCreditTradesRequired=" + activeCreditTradesRequired
//				+ ", volBonus2Threshold=" + volBonus2Threshold
//				+ ", rentalPools=" + rentalPools + ", extraAnnualPaydown="
//				+ extraAnnualPaydown + ", childTaxCredit=" + childTaxCredit
//				+ ", allowedRedFlagsMed=" + allowedRedFlagsMed
//				+ ", chargesAllowedBehind=" + chargesAllowedBehind
//				+ ", rental=" + rental + ", removeLoanMoRemaining="
//				+ removeLoanMoRemaining + ", qtyAvailable=" + qtyAvailable
//				+ ", rentalIncomePercent=" + rentalIncomePercent
//				+ ", allowSelfEmpIncome=" + allowSelfEmpIncome
//				+ ", allowedOn2nd=" + allowedOn2nd + ", sellerQty=" + sellerQty
//				+ ", allowSixPlex=" + allowSixPlex + ", greatGdsLessSplit="
//				+ greatGdsLessSplit + ", imageSmall=" + imageSmall
//				+ ", branchSigning=" + branchSigning
//				+ ", greatTdsGreaterEqualSplit=" + greatTdsGreaterEqualSplit
//				+ ", baseCommission=" + baseCommission
//				+ ", smCenterLtvCutOffLes=" + smCenterLtvCutOffLes
//				+ ", incomingQty=" + incomingQty + ", allowedGreyFlagsMed="
//				+ allowedGreyFlagsMed + ", lifeLeasedProperty="
//				+ lifeLeasedProperty + ", floatingHomes=" + floatingHomes
//				+ ", isPenaltyGreater=" + isPenaltyGreater + ", rawLand="
//				+ rawLand + ", saleOk=" + saleOk + ", poorCredit12MoLates="
//				+ poorCredit12MoLates + ", partnerRef=" + partnerRef
//				+ ", increasePayments=" + increasePayments + ", standardPrice="
//				+ standardPrice + ", highRatio2ndHome=" + highRatio2ndHome
//				+ ", mortgageType=" + mortgageType + ", valuation=" + valuation
//				+ ", exclOrInclCity=" + exclOrInclCity + ", costMethod="
//				+ costMethod + ", supplyMethod=" + supplyMethod
//				+ ", childSupportTreatment=" + childSupportTreatment
//				+ ", revolvingDebtPayments=" + revolvingDebtPayments
//				+ ", rentalIncomeTreatment=" + rentalIncomeTreatment
//				+ ", equifaxScoringUsed=" + equifaxScoringUsed
//				+ ", locQualifyingRate=" + locQualifyingRate
//				+ ", procureMethod=" + procureMethod + ", applicationMethod="
//				+ applicationMethod + ", mesType=" + mesType + ", type=" + type
//				+ ", whoseIncomeUsed=" + whoseIncomeUsed + ", openClosed="
//				+ openClosed + ", diffLength=" + diffLength + ", state="
//				+ state + ", term=" + term + ", lenderLine=" + lenderLine
//				+ ", opportunityId=" + opportunityId + ", sellerId=" + sellerId
//				+ ", lender=" + lender + ", treatmentOfRental="
//				+ treatmentOfRental + ", allowRawLand=" + allowRawLand
//				+ ", agriculturalLessThen10Acres="
//				+ agriculturalLessThen10Acres + "]";
//	}

	public Boolean getUninsurable() {
		return uninsurable;
	}

	public void setUninsurable(Boolean uninsurable) {
		this.uninsurable = uninsurable;
	}

	public Integer getCombinedProduct() {
		return combinedProduct;
	}

	public void setCombinedProduct(Integer combinedProduct) {
		this.combinedProduct = combinedProduct;
	}

	public String getLendername() {
		return lendername;
	}

	public void setLendername(String lendername) {
		this.lendername = lendername;
	}

	public Boolean getCombinebase() {
		return combinebase;
	}

	public void setCombinebase(Boolean combinebase) {
		this.combinebase = combinebase;
	}

	
}