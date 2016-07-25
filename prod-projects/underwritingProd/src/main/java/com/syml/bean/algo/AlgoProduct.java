package com.syml.bean.algo;

import java.util.ArrayList;
import java.util.List;

import com.syml.domain.Product;

public class AlgoProduct
{
    // Details
    //#region Product Characteristics Variables

    public String name;  // Name of Product	

    //#####################Details Tab################

    //General Info:###        
    //Product Characteristics
    // Was LenderName
    //public AlgoLender lender; // Was Lender
    public enum MortgageType { LOC, Variable, Fixed, Cashback, Combined } // Was TypeOfProduct
    public MortgageType mortgageType; // Was ProductType
    //public double interestRate; // Was InterestRate
    //public double qualifyingRate; // Was QualifyingInterestRate
    public enum ProductTerm { Month6, Year1, Year2, Year3, Year4, Year5, Year6, Year7, Year8, Year9, Year10, none } // Was ProductTerm
    public ProductTerm term; // Was Term
    //public double cashbackPercent; // Percentage of Loan amount that is provided as cashback // Was CashbackPercent
    //public int maximumAmortization; 
    public enum OpenClosed { Open, Closed }
    //public OpenClosed openClosed;

    
    public int termInMonths; // Was TermInMonths
    public enum LOCRateForQualifying { NotLOC, QualifyingRate, ProductRate } // Was LOCRateForQualifying
    //public LOCRateForQualifying lOCQualifyingRate; // Was LOCQualifyingRate

    //Available Payment Options:###
    // Payment Options Related Variables
    //public boolean weeklyPayments; // Was WeeklyPayAvailable
    //public boolean biWeeklyPayments; // Was BiWeeklyPayAvailable
    //public boolean semiMonthlyPayments; // Was SemiMonthlyPayAvailable
    //public boolean monthlyPayments;
    //public boolean increasePayments; // Was IncreasePayAvailable
    //public boolean doubleUpPayments; // Was DoubleUpPayAvailable
    //public boolean extraAnnualPaydown; // Was ExtraPercentAnnualAvailable
    //public boolean extraAppliedAnyTime; // Was ExtraAtAnytimeAvailable
    //public boolean skipPaymentPossible; // Was SkipPaymentAvailable
    //public boolean prepayToReduce; // Was PrepayPenaltyReduceAvailable
    //public boolean rewardPoints; // Was RewardPointsAvailable

    //Penalty Info:###

    // These 2 variables are not presently used.  Caclulating Payout Penalty Attractiveness needs an overhaul with Kendall
    //Avg Differential Used		avg_diff_used        
    //Closed Period (Months)		closing_period
    // Variables Related to Payout Penalty Calculations
    //public boolean isPenaltyGreater; // Was IsGreaterOfPenalty
    //public int monthsInterestPenalty; // Was MonthsOfInterest
    public double interestRateInDifferential; // Was InterestRateInDifferential
    public enum PenaltyLength { RemainingMonths, FullTerm } // Was PenaltyLength
    //public PenaltyLength diffLength; // Was LengthOfPenaltyTerm
    //public boolean waivePenaltyIfRetain; // Was WaivePenaltyIfRetain
    public int monthsClosed; // Number of months the product is closed and subject to penalties.  // Was MonthsClosed
    
    //        Lender Qualities:###
    //Fitness Related Variables (Most of these are Calculated, not input from OpenERP
    public double fitness; // Was Fitness 
    public boolean qualifiesForOpportunity; // Was QualifiesForOpportunity
    public double fixedCost; // Was FixedCost
    public double variableCost; // Was VariableCost
    public double LOCCost; // Was LOCCost
    public double expectedPayment; // Was ExpectedPayment
    public double cashbackAmount; // Was CashbackAmount
    public double payoutAmount; // Was PayoutAmount
    public double paymentOptions; // Was PaymentOptions
    public double baseCompAmount; // Was BaseCompAmount
    public double bonusCompAmount; // Was BonusCompAmount
    public double marketingCompAmount; // Was MarketingCompAmount
    public double trailerCompAmount; // Was TrailerCompAmount
    public double totalCompAmount; // Was TrailerCompAmount
    //public double avgProcessingSpeed; // Was Speed
    //public double businessEase; // Was BusinessEase
    public double branchAttend; // Was BranchAttend
    //public double underwriterPref; // Was UnderwriterPreference
    public double applicationEase; // Is used only for fitness - no need to map to OpenERP.

    // These fields need to be added to Product Details wireframe of OpenERP
    // Commission Related Variables
    //public int baseCommission; // This is in Basis Points (1/100 of 1%)  // Was BaseCommission
    //public int marketingCommission; // This is in Basis Points (1/100 of 1%) // Was MarketingCommission
    //public int trailerCommission; // This is in Basis Points (1/100 of 1%) // Was TrailerCommission
    //public int volBonus1; // This is in Basis Points (1/100 of 1%) // Was BonusCommission1
    //public int volBonus1Threshold; // This is in Basis Points (1/100 of 1%) // Was BonusThreshold1
    //public int volBonus2; // This is in Basis Points (1/100 of 1%) // Was BonusCommission2
    //public int volBonus2Threshold; // This is in Basis Points (1/100 of 1%) // Was BonusThreshold2
    //public int volBonus3; // This is in Basis Points (1/100 of 1%) // Was BonusCommission3
    //public int volBonus3Threshold; // This is in Basis Points (1/100 of 1%) // Was BonusThreshold3
    //public int volBonus4; // This is in Basis Points (1/100 of 1%) // Was BonusCommission4
    //public int volBonus4Threshold; // This is in Basis Points (1/100 of 1%) // Was BonusThreshold4
    //public int volBonus5; // This is in Basis Points (1/100 of 1%) // Was BonusCommission5
    //public int volBonus5Threshold; // This is in Basis Points (1/100 of 1%) // Was BonusThreshold5



    // Presently Missing in OpenERP...
    public enum ApplicationMethod { Morweb, Filelogix, Either } // Was ApplicationMethod
    //public ApplicationMethod applicationMethod; // Was AppToLender

    // These 2 cashback fields need to be added to Product Details wireframe of OpenERP
    public boolean isCashback; // If product is a cashback product, true   // Was IsCashback

    //#endregion

    // Underwriting Variables
    //#region Underwriting Variables

    //#####################Underwriting Tab################	
    public enum EquifaxScoring { Beacon5, Beacon9, CRP3, ERS2 } // Was EquifaxScoring
    //public EquifaxScoring equifaxScoringUsed; // Was ScoringMethod
    
    //public int beaconTdsSplit;  // The Published Credit Score at which they will allow a higher TDS and GDS // Was BeaconTDSSplit
    //public int beaconTdsCutOff; // The absolute Minimum Credit score at which they will allow a Higher TDS and GDS // Was BeaconTDSSplitCutOff
    //public int minBeacon;  // Published Lowest Beacon score they will allow this product to be provided // Was MinBeacon
    //public int minBeaconCutOff;  // Absolute Lowest Beacon score they will allow this product to be provided  // Was MinBeaconCutOff
    public enum WhoseIncomeUsed { Highest, Lowest, Averaged, IncomeWeightAvg }; // This field determines the applicant upon whose Beacon Score will drive the Credit scoring logic & Debt Service Ratios // Was WhoseIncomeUsed
    public WhoseIncomeUsed whoseBeaconUsed;         // Was UseWhoseIncome
    //public int goodCreditBeaconSplit; // The Beacon score above which the Lender considers the applicant to have "Good Credit"  // Was GoodCreditAboveBeacon
    //public int goodCredit12MoLates; // The number of Late payments in the last 12 months below which the Lender considers the applicant to have "Good Credit" // Was GoodCreditBelow12MoLates
    //public int maximumApplicants; // How many applicants will the Lender allow onto this product for the purposes of qualifying TDS / GDS? // Was MaximumApplicants
    //public int poorCreditBeaconSplit; // The Beacon score below which the Lender considers the applicant to have "Poor Credit" // Was PoorCreditBelowBeacon
    //public int poorCredit12MoLates; // The number of Late payments in the last 12 months above which the Lender considers the applicant to have "Poor Credit" // Was PoorCreditAbove12MoLates

    //        Bankruptcy:###	
    //public int mthsFromDischargeAllowable; // Number of Months from Bankruptcy Discharge Date in order to be considered // Was MthsFromDischargeAllowable
    //public int activeCreditTradesRequired; // Number of newly established active lines of credit Applicant must have in order to be considered // Was ActiveCreditTradesRequired

    //Maximum Ratios:###
    //Great Credit:###
    //public int greatTdsGreaterEqualSplit;  // Highest TDS Ratio Lender will allow for this product for a person with Good Credit if their Beacon is >= BeaconSplitCutOff // Was GoodCreditAllowableMaxTDSAboveCutOff
    //public int greatTdsLessSplit;  // Highest TDS Ratio Lender will allow for this product for a person with Good Credit if their Beacon is < BeaconSplitCutOff // Was GoodCreditAllowableMaxTDSBelowCutOff
    //public int greatGdsGreaterEqualSplit;  // Highest GDS Ratio Lender will allow for this product for a person with Good Credit if their Beacon is >= BeaconSplitCutOff // Was GoodCreditAllowableMaxGDSAboveCutOff
    //public int greatGdsLessSplit;  // Highest GDS Ratio Lender will allow for this product for a person with Good Credit if their Beacon is < BeaconSplitCutOff // Was GoodCreditAllowableMaxGDSBelowCutOff
    
    //Med Credit:###
    //public int medTdsGreaterEqualSplit;  // Highest TDS Ratio Lender will allow for this product for a person with Medium Credit if their Beacon is >= BeaconSplitCutOff // Was MedCreditAllowableMaxTDSAboveCutOff
    //public int medTdsLessSplit;  // Highest TDS Ratio Lender will allow for this product for a person with Medium Credit if their Beacon is < BeaconSplitCutOff // Was MedCreditAllowableMaxTDSBelowCutOff
    //public int medGdsGreaterEqualSplit;  // Highest GDS Ratio Lender will allow for this product for a person with Medium Credit if their Beacon is >= BeaconSplitCutOff // Was MedCreditAllowableMaxGDSAboveCutOff
    //public int medGdsLessSplit;  // Highest GDS Ratio Lender will allow for this product for a person with Medium Credit if their Beacon is < BeaconSplitCutOff // Was MedCreditAllowableMaxGDSBelowCutOff
    
    //Poor Credit:###
    //public int poorTdsGreaterEqualSplit;  // Highest TDS Ratio Lender will allow for this product for a person with Poor Credit if their Beacon is >= BeaconSplitCutOff // Was PoorCreditAllowableMaxTDSAboveCutOff
    //public int poorTdsLessSplit;  // Highest TDS Ratio Lender will allow for this product for a person with Poor Credit if their Beacon is < BeaconSplitCutOff // Was PoorCreditAllowableMaxTDSBelowCutOff
    //public int poorGdsGreaterEqualSplit;  // Highest GDS Ratio Lender will allow for this product for a person with Poor Credit if their Beacon is >= BeaconSplitCutOff // Was PoorCreditAllowableMaxGDSAboveCutOff
    //public int poorGdsLessSplit;  // Highest GDS Ratio Lender will allow for this product for a person with Poor Credit if their Beacon is < BeaconSplitCutOff // Was PoorCreditAllowableMaxGDSBelowCutOff

    //Calculation Methods:###
    //Employed Income Methods:###
    //public boolean letterOfEmployment; // Lender will allow calculation of income based purely on stated income from employer // Was EmployedLetterOfEmployment
    //public boolean payStubProrate; // Lender will allow calculation of income based upon prorating income from a pay stub // Was EmployedPayStubProRate
    //public boolean twoYrAverageN; // 2_yr_average_noa in OpenERP ... Lender will allow calculation of income based upon last 2 years averaged Notice Of Assessment from CCRA // Was EmployedTwoYrAverageNOA
    //public boolean lastYrNoa; // Lender will allow calculation of income based upon last Year Notice Of Assessment from CCRA // Was EmployedLastYrNOA
    
    //Self-Employed Income Methods:###
    public boolean selfEmployedTwoYrAverageNOA; // Lender will allow calculation of income based upon last 2 years averaged Notice Of Assessment from CCRA // Was SelfEmployedTwoYrAverageNOA
    public boolean selfEmployedTwoYrAverageNOAPlusPercent; // Lender will allow calculation of income based upon last 2 years averaged Notice Of Assessment from CCRA grossed up by 15% // Was SelfEmployedTwoYrAverageNOAPlusPercent
    public int selfEmployedGrossUpPercentage; // The gross up percentage a Lender will attribute to Self-Employed Income. // Was SelfEmployedGrossUpPercentage
    public boolean selfEmployedLastYrNOA; // Lender will allow calculation of income based upon last Year Notice Of Assessment from CCRA // Was SelfEmployedLastYrNOA
    public boolean selfEmployedT1General; // Lender will allow calculation of income based upon last Year T1 General from CCRA ... Formula to be determined // Was SelfEmployedT1General
    
    //Rental Income Methods:###
    public enum TreatmentOfRental { AddToIncome, SubtractFromLiabilities } // Determine how lender treats rental income ...  // Was RentalIncomeTreatent
    //public TreatmentOfRental rentalIncomeTreatment; // Was TreatmentOfRental
    //public int rentalIncomePercentIncluded; // Percentage of the income from a Rental Property the Lender will allow to count toward Additional Income for the Applicant // Was RentalIncomePercentIncluded
    
    //        Fluctuating Income Methods:###
    //2 Yr Average NOA		rental_2_yr_avg_noa
    //Last Yr NOA			rental_last_yr_noa
    // For the sake of mapping, these two variable names have been changed even though the DB variables in OpenERP and poorly named.
    //public boolean rental2YrAvgNoa; // In OpenERP is rental_2_yr_avg_noa ... This is poorly named as it does not apply to rental income, but rather the methods Lender will allow calculation of income (Used for Commission Income, Bonus Income, tips income etc) based upon last 2 years averaged Notice Of Assessment from CCRA // Was FluctuateTwoYrAverageNOA
    //public boolean rentalLastYrNoa; // In OpenERP is rental_last_yr_noa ... This is poorly named as it does not apply to rental income, but rather the methods Lender will allow calculation of income (Used for Commission Income, Bonus Income, tips income etc) based upon last year Notice Of Assessment from CCRA // Was FluctuateLastYrNOA
    
    //Liability Calculation Methods:###
    public enum LiabilityCalculatonMethod { ThreePercentBalance, ThreePercentLimit, StatedMinimum }; // Options for how Lender caculate the payment on a Revolving Debt?  // Was  LiabilityCalculatonMethod
    //public LiabilityCalculatonMethod revolvingDebtPayments; // This is the field from OpenERP that is how the Lender Caclulates // Was RevolvingDebtPaymentCalc
    //public int removeLoanMoRemaining; // Number of months remaining in a Loan where lender will all it to NOT be included in GDS & TDS // Was RemoveLoanMonthsRemaining
    //public boolean removeLoansDonePreClose; // New field added.

    //Other Income Treatment:###	
    public enum SupportTreatment { AddToLiabilities, SubtractFromIncome } // How does Lender treat Child and Spousal Support Payments?  // Was SupportTreatment
    public SupportTreatment childSupportRreatment;  // If Applicant pays support, how is this treated in the Calculations?  // Was PayingSupport
    //public int childTaxCredit; // Was ChildTaxCreditPercentIncluded
    //public int livingAllowance; // Was LivingAllowancePercentIncluded
    //public int vehicleAllowance;  // Was VehicleAllowancePercentIncluded

    // Allowed Charge on Title:###  (Affects Whether product can be used in calculated "Strategy")
    //public boolean allowedOn1st; // The Product is allowed to be a 1st Mortgage in the "Strategy" // Was AllowedChargeOn1st
    //public boolean allowedOn2nd; // The Product is allowed to be a 2nd Mortgage in the "Strategy" // Was AllowedChargeOn2nd
    //public boolean chargesAllowedBehind; // The Lender will allow other charges on title behind them // Was LenderAllowsCharges
    //public boolean allowedOn3rd; // The Product is allowed to be a 3rd Mortgage in the "Strategy" // Was AllowedChargeOn3rd
    //public boolean allowedOnBridge; // The Product is allowed to be a Bridge Mortgage in the "Strategy" // Was AllowedChargeOnBridge
    //public boolean specificLendersOn1st; // The Lender only allows specific Lenders ahead of them on title // Was SpecificLendersOn1st
    public List<String> listOfLendersAllowedOnFirst;
    public int maxLTVAllowedBehind; 
    
    // Vendor Takeback info (Affects Whether product can be used in calculated "Strategy")
    public enum MinVendorTakeBackCharge { None, Second, Third, Fourth }; // The Product will allow the Vendor Takeback Charge in what position?  // Was MinVendorTakeBackCharge
    public MinVendorTakeBackCharge minAllowedCharge;
    public int maxLoanToValue; // Maximum LTV the Lender will allow the VTB Opportunity to be // Was VendorTakeBackMaxLoanToValue
     public  double  maxcombinedLTV;
    //        Allowed Properties:###
    //public boolean countryResidential; // Was AllowCountryResidential
    //public boolean condo; // Was AllowCondo
    //public boolean agricultural; // Was AllowAgricultural
    //public boolean commercial; // Was AllowCommercial
    //public boolean fractionalInterests; // Was AllowFractionalInterest
    //public boolean coOperativeHousing; // Was AllowCooperativeHousing
    //public boolean growOps; // Was AllowGrowOps
    //public boolean rentalPools; // Was AllowRentalPools
    //public boolean allow2ndHomes; // Was Allow2ndHome
    //public boolean highRatio2ndHome; // Was AllowHighRatio2ndHome
    //public boolean uninsuredConv2ndHome; // Was AllowUninsured2ndHome
    //public int minSqAllowCondo; // The smallest Sq Ft the Lender will allow to underwrite for a condo  // Was MinSqFtAllowedCondo
    //public int minSqCutoffCondo; // The ABSOLUTE smallest Sq Ft the Lender will allow to underwrite for a condo  // Was MinSqFtCutOffCondo
    //public int minSqAllowHouse; // The smallest Sq Ft the Lender will allow to underwrite for a House // Was MinSqFtAllowedHouse
    //public int minSqCutoffHouse; // The ABSOLUTE smallest Sq Ft the Lender will allow to underwrite for a House // Was MinSqFtCutOffHouse
    //public int cottageInsureLtv; // The loan to value at which the lender requires insurance in order to lend against a cottage or Recreational Property        // Was CottageInsureLTV
    //public int cottageMaxLtv; // The maximum LTV the Lender will go to on a cottage property // Was CottageMaxLTV
    //public boolean ageRestricted; // Was AllowAgeRestricted      
    //public boolean lifeLeasedProperty; // Was AllowLifeLeased
    //public boolean leasedLand; // Was AllowLeasedLand
    //public boolean mobileHomes; // Was AllowMobileHomes
    //public boolean modularHomes; // Was AllowModularHomes
    //public boolean floatingHomes; // Was AllowFloatingHomes
    //public boolean boardingHous; // Was AllowBoardingHouses
    //public boolean roomingHouses; // Was AllowRoomingHouses
    //public boolean nonConvConstruction; // Was AllowNonConventionalConstruction
    //public boolean cottageRecProperty; // Was AllowCottage
    //public boolean rentalProperty; // Was AllowRentalProperty
    //public int outbuildingInclude; // The percetnage of the value of Outbuilding the Lender will lend against. // Was PercentOutbuildingIncluded
    //public int ageRestrictedLtv; // Maximum Loan To Value Percentage Allowed for an Age Restrcited Prperty // Was AgeRestrictedMaxLTV
    //public int maxAcreageAllowed; // The largest Property the Lender will allow in the Valuation to Drive LTV // Was MaxAcreageAllowed
    //public int maxAcreageCutoff; // The ABSOLUTE largest Property the Lender will allow in the Valuation to Drive LTV. Properties between MaxAcreageAllowed and MaxAcreageCutOff are "Grey" - Doable,but more work.  // Was MaxAcreageCutOff
    //public int maxMortgageAllowed; // The Largest amount a lender will loan on a property  // Was MaximumMortgageAmount
    //public int minMortgageAllowed; // The Smallest amount a lender will loan on a property  // Was MinimumMortgageAmount
    //public int cottageMinBeacon; // The minimum Beacon the lender requires in order to lend against a cottge or recreational property // Was CottageBeacon

    // Missing From OpenERP
    //public boolean allowDuplex; // Was AllowDuplex
    //public boolean allowFourPlex; // Was AllowFourPlex
    //public boolean allowSixPlex; // Was AllowSixPlex
    //public boolean allowEightPlex; // Was AllowEightPlex       
    //public boolean allowRawLand; // Was AllowRawLand
    //public boolean allowAgriculturalLess10Acres; // Was AllowAgriculturalLess10Acres

    //        Locations:###
    //public int requirDistanceToCity; // Maximum Allowable distance from Property to a Major Center (Populaiton >= 50,000) // Was RequiredDistanceToCity
    //public String allowProvinces; // Provinces in which Lender and Product will allow mortgage to be secured // Was AllowableProvinces
    public enum ExcludeOrIncludeCities { Exclude, Include }; // Some Lenders maintain a list of towns they will NOT Lend in, others maintain a list they will ONLY lend in.  This boolean allows Cities field to be used in either direction // Was ExcludeOrIncludeCities
    //public ExcludeOrIncludeCities exclOrInclCity; // Was CitesToManage
    //public String cities; // Cites in which Lender and Product will allow / disallow mortgage to be secured // Was Cities

    //        Fees:###
    //public int resConCutoff; // What is the threshold at which allowable Loan to value changes?  // Was ResidenceConventionalCutOff
    //public int resLtvCutOffGrt; // What is the Maximum Percent of Loan To Value above the CutOff? // Was ResidenceLTVPercentAboveCutOff
    //public int resLtvCutoffLes; // What is the Maximum Percent of Loan To Value below the CutOff? // Was ResidenceLTVPercentBelowCutOff
    //public int rentalConCutOff; // What is the threshold at which allowable Loan to value changes?  // Was RentalConventionalCutOff
    //public int rentalLtvCutOffGrt; // What is the Maximum Percent of Loan To Value above the CutOff? // Was RentalLTVPercentAboveCutOff
    //public int rentalLtvCutOffLes; // What is the Maximum Percent of Loan To Value below the CutOff? // Was RentalLTVPercentBelowCutOff
    //public int smCenterConCutOff; // What is the threshold at which allowable Loan to value changes?  // Was SmallCenterConventionalCutOff
    //public int smCenterLtvCutOffGrt; // What is the Maximum Percent of Loan To Value above the CutOff? // Was SmallCenterLTVPercentAboveCutOff
    //public int smCenterLtvCutOffLes; // What is the Maximum Percent of Loan To Value below the CutOff? // Was SmallCenterLTVPercentBelowCutOff
    //public int lenderFeesPercent; // Some Lenders will charge an additional fee on the deal. Ussually this is with either private Mortgages or 2nd Mortgages.  // Was LenderFeesPercent
    //public int lenderFeesFlat; // Some Lenders will charge an additional fee on the deal. Ussually this is with either private Mortgages or 2nd Mortgages.  // Was LenderFeesFlat
    //public int brokerFeesPercent; // Sometimes we as a Broker may charge an additional fee on the deal. Ussually this is with either private Mortgages or 2nd Mortgages. (Only applies to certain Products / Lenders ... Ussually B-D Lenders) // Was BrokerFeesPercent
    //public int brokerFeesFlat; // Sometimes we as a Broker may charge an additional fee on the deal. Ussually this is with either private Mortgages or 2nd Mortgages. (Only applies to certain Products / Lenders ... Ussually B-D Lenders)  // Was BrokerFeesFlat
    // Missing from OpenERP
    //public boolean isProductInsured; // Is this particular Product insureable by Lender with CMHC or GE Capital?  In case of Second Mortgage, Bridge, LOC, no - In Case of non-private 1st Mortgages, likely yes // Was IsProductInsured
    
    // Miscellaneous ###	
    //public boolean allowSelfEmpIncome; // Some Lenders will only allow employed income into the qualification process // Was AllowSelfEmployedIncome
    //public int statedIncomeMaxLtv; // Some products allow Stated income rather than provable income.  In these instances the allowed LTV is often lower and can vary from lender to lender. // Was StatedIncomeMaxLTV
    //public int statedIncomeMinBeacon; // The minimum beacon score a lender will accept for a Stated Income Approval // Was StatedIncomeMinBeacon
    //public int shortAvgFundDay; // The time it typically takes to fund this product if things are needed quickly // Was ShortestAvgFundingDays
    //public boolean canadianMilitaryAllow; // Some Lenders will not underwrite properties for military personell due to the changing nature of their locations // Was CanadianMilitaryAllowed
    //public boolean branchSigning; // Some Lenders make signings in the Branch as well as Lawyer manditory - this is a significant negative and inconvenience. // Was BranchSigning
    //public boolean nonResidentAllow; // The lender will accept Non-Resident Borrowers // Was NonResidentAllowed
    //Line of Credit			line_of_credit
    //Capped Variable Allowed		capped_variable
    //public int maxGiftAllow; // The maximum percetnage of the downpayment that cancome from gifted sources // Was MaximumGiftDownPayPercent
    //% Min Gift Allowed		min_gift_allow   This field is presently in OpenERP, but I see no use for it - needs modifcation
    //public int maxBorrowAllow; // The maximum percetnage of the downpayment that cancome from gifted sources // Was MaximumBorrowedDownPayPercent
    //% Min Borrowed Allowed		min_borrow_allow   This field is presently in OpenERP, but I see no use for it - needs modifcation
    //public boolean allowPowerOfAttorney; // Not used in formuala and rarely used.  Only there for information for our assitants and underwriters // Was AllowPowerOfAttourney
    //public int maxAllowProperty; // The Maximum number of properites the lender will allow the applicant to have mortgaged with them. // Was MaxAllowedProperties
    //public boolean allowTitleIns; // Some lenders do not allow, in which case transfers take longer.  Ussually not an issue unless deal needs to fund quickly // Was AllowTitleInsurance
    //public int immigrantMinEmpMo; // The minimum number of months the lender will allow as Canadian Employment for an immigrant to qualify for a mortgage. // Was ImmigrantMinEmployMonths
    //public int immigrantMaxMo; // The maximum number of months the lender will utilize as qualifying under "New To Canada" rules.  Algorithm would prefer to use regular rules as they are less Stringent // Was ImmigrantMaxMonthsInCanada
    //public int immigrantMaxLtv; // The maximum Loan to Value the lender will allow for an immigrant to qualify for a mortgage. // Was ImmigrantMaxLTV
    //public double minHeatCost; // The minimum Amount / month the lender uses as a Heating Cost. // Was MinimumHeatingCost
    //public int maxPropertyAge; // Maximum Age of Property that lender will loan against. // Was MaxPropertyAge
    
    
    // The Remainder of these fields are missing from OpenERP
    public int maximumMortgageAllowed; // Largest Amount the lender will loan on a property // Was MaximumMortgageAllowed
    public int minimumMortgageAllowed; // Smallest Amount the lender will loan on a property // Was MinimumMortgageAllowed
    //public int maxNumberOfDraws; // Maximum Number of Draws Lender will allow in construction Project. // Was MaxNumberOfDraws
    //public boolean constructionProduct; // Product can be used for Construction. // Was ConstructionProduct
    //public boolean selfBuildProduct; // Product can be used for Self Build Construction. // Was SelfBuildProduct


    public int allowableGreyFlagGoodCredit; // Number of Grey Flags on the deal they will allow if the Credit is Good // Was AllowableGreyFlagGoodCredit
    public int allowableGreyFlagMediumCredit; // Was AllowableGreyFlagMediumCredit
    public int allowableGreyFlagPoorCredit; // Was AllowableGreyFlagPoorCredit

    public int allowableBlackFlagGoodCredit; // Was AllowableBlackFlagGoodCredit
    public int allowableBlackFlagMediumCredit; // Was AllowableBlackFlagMediumCredit
    public int allowableBlackFlagPoorCredit; // Was AllowableBlackFlagPoorCredit

    //#endregion

    public Product product;
    public AlgoProduct(Product product)
    {
    	this.product = product; 
        // Cantor - I left the constructor for your coding depending on how you are managing the WebService Sending the Data.

        // Set IsCashBack boolean
        if (product.cashback > 0)
        	isCashback = true;
        listOfLendersAllowedOnFirst = new ArrayList<String>();
    }
//	@Override
//	public String toString() {
//		return "AlgoProduct [name=" + name + ", termInMonths=" + termInMonths
//				+ ", interestRateInDifferential=" + interestRateInDifferential
//				+ ", monthsClosed=" + monthsClosed + ", fitness=" + fitness
//				+ ", qualifiesForOpportunity=" + qualifiesForOpportunity
//				+ ", fixedCost=" + fixedCost + ", variableCost=" + variableCost
//				+ ", LOCCost=" + LOCCost + ", expectedPayment="
//				+ expectedPayment + ", cashbackAmount=" + cashbackAmount
//				+ ", payoutAmount=" + payoutAmount + ", paymentOptions="
//				+ paymentOptions + ", baseCompAmount=" + baseCompAmount
//				+ ", bonusCompAmount=" + bonusCompAmount
//				+ ", marketingCompAmount=" + marketingCompAmount
//				+ ", trailerCompAmount=" + trailerCompAmount
//				+ ", totalCompAmount=" + totalCompAmount + ", branchAttend="
//				+ branchAttend + ", applicationEase=" + applicationEase
//				+ ", isCashback=" + isCashback + ", whoseBeaconUsed="
//				+ whoseBeaconUsed + ", selfEmployedTwoYrAverageNOA="
//				+ selfEmployedTwoYrAverageNOA
//				+ ", selfEmployedTwoYrAverageNOAPlusPercent="
//				+ selfEmployedTwoYrAverageNOAPlusPercent
//				+ ", selfEmployedGrossUpPercentage="
//				+ selfEmployedGrossUpPercentage + ", selfEmployedLastYrNOA="
//				+ selfEmployedLastYrNOA + ", selfEmployedT1General="
//				+ selfEmployedT1General + ", childSupportRreatment="
//				+ childSupportRreatment + ", listOfLendersAllowedOnFirst="
//				+ listOfLendersAllowedOnFirst + ", maxLTVAllowedBehind="
//				+ maxLTVAllowedBehind + ", minAllowedCharge="
//				+ minAllowedCharge + ", maxLoanToValue=" + maxLoanToValue
//				+ ", maximumMortgageAllowed=" + maximumMortgageAllowed
//				+ ", minimumMortgageAllowed=" + minimumMortgageAllowed
//				+ ", allowableGreyFlagGoodCredit="
//				+ allowableGreyFlagGoodCredit
//				+ ", allowableGreyFlagMediumCredit="
//				+ allowableGreyFlagMediumCredit
//				+ ", allowableGreyFlagPoorCredit="
//				+ allowableGreyFlagPoorCredit
//				+ ", allowableBlackFlagGoodCredit="
//				+ allowableBlackFlagGoodCredit
//				+ ", allowableBlackFlagMediumCredit="
//				+ allowableBlackFlagMediumCredit
//				+ ", allowableBlackFlagPoorCredit="
//				+ allowableBlackFlagPoorCredit + ", product=" + product + "]";
//	}





    /* 
     
    * Approval Concept ... How Many Grey's allowed?  How Many Black's allowed?
    * What fields are Abolutle BLACK NO DEAL fields? (Multi-Select Dropdown field)
    * 
    * The Logic that lands on the most efficient way of doing it need to deliver the analysis back to OpenERP
    * to place it as text in the "Proposal".
       
    
    * Sliding Scale for Conventional Mortgages
     * Land Transfer Tax (BC)

    * Primary Residence
       threshold field  (#field)
       below threshold field (%)
       above threshold field (%)
       
    * Rental
       threshold field  (#field)
       below threshold field (%)
       above threshold field (%)
   
     * 
     * 
     * We need to Start Narrowing Products loaded from DB by Province.
     * Maintain a List of Provinces that the Lender WILL Lend in (based on fact that 80% of lenders will lend in < 5 Provinces
     * Then Check on City in this fashion ... 
     * Have a Dropdown Include Cities / Exclude Cities
     * Then a Large Textbox where they just type in the list of cities .... 
     * 
     * Lastly we need to check on whether small community rules apply ... 
     * Assistant needs to put into - just use 50km and 5000 population Rerun the Algorithm....

     
    * 
    * 
    * Allowed Exceptions
    * currently renting
      yes
       no
   * remove double liabilities
      yes
       no
   * prepayment options
      yes
       no
   * company name
      yes
       no
   * rental offset
   * last year income
      yes
       no
   * pro rate income
      yes
       no
   * open lines of credit
      yes
       no
   * credit bureau liabilities
   * combine Credit card debt
      yes
       no
   * payment on credit card loan
   * income grossed up
   * bridge financing
      yes
       no        
   * Remove Co-Applicant (If income is small & liabilities are large ... get rid of the poor credit person if application is stronger and will fly ... )
    

    * 
    * 
    * 
    */





}
