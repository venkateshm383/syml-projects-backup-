package com.syml.bean.algo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.syml.domain.Applicant;
import com.syml.domain.Opportunity;
import com.syml.util.SelectionHelper;
import com.syml.util.Util;

public class AlgoOpportunity
{
	
	 public Opportunity opp;
	
    // These fields are in the OpenERP Opportunity Record, but are not required for the Underwriting Application Logic
    //  broker 					            Broker 	(
    //  assistant				            Assistant
    //  Underwriter				            Underwriter
    //	Application Start Time              application_start_time
    //	Applicant                           applicant_record_line	
    //  Note :-- applicant_record_line is a one2many field, its means that crm.lead object has a primary-forgin key relation with 'applicant.record' object. 

    public List<AlgoApplicant> applicants;  // Was Applicants The corresponds to applicant_record_line, however the record line is a small subset of the related Applicant Record. 

    //        ###Context
    // Variables in OpenERP not required in Underwriting App
    //desired_mortgage_type				Desired Mortgage Type        
    
    //public double desiredMortgageAmount; // Need to add in the Capacity to divide into a first Mortage Amount and 2nd Mortgage Amount // Was DesiredMortgageAmount - OpenERP =  desired_mortgage_amount
    
    //TODO in openerp is many to one, not selection type
    public enum DesiredTypeOfProduct { LOC, Variable, Cashback, Fixed, Recommend } // Was DesiredTypeOfProduct - OpenERP =  
    //public DesiredTypeOfProduct desiredProductType; // Was DesiredProductType - OpenERP =  desired_product_type
    public enum DesiredMortgageType { LOC, Variable, Fixed, Cashback, Combined, Recommend }
    public enum LendingGoal { Prequalify, Approval, Refinance }; // Was LendingGoal - OpenERP =  
    //public LendingGoal whatIsYourLendingGoal = LendingGoal.valueOf(opp.whatIsYourLendingGoal); // Was GoalOfLoan - OpenERP =  whatisyourlendinggoal
    
    // public LendingGoal OpportunityLendingGoal; // Was OpportunityLendingGoal - refactored so whatIsYourLendingGoal suffices. 
    public enum Looking { CondoMobileHome, HouseTownhouseDuplexAcreage, RentalProperty, RawLandLeasedLand, VacationPropertySecondHome, CottageRecProperty, DontKnow } // Was Looking - OpenERP =  
    //public Looking lookingFor; // Was LookingFor - OpenERP =  preapprovedimlookingfora
    
    //public double GDS; // General Debt Service Ratio ... Ratio of (Liability Payments + Property Taxes + Heat) / Income // Was GDSRatio - OpenERP =  GDS
    //public double TDS; // Total Debt Service Ratio ... Ratio of (GDS + Mortgage Payment) / Income // Was TDSRatio - OpenERP =  TDS
    public enum BuildingFunds { None, Complete, ProgressDraw, SelfBuildDraw } // Was BuildingFunds - OpenERP =  
    //public BuildingFunds buildingFunds; // Was FundsForBuilding - OpenERP =  building_funds
    //public int drawsRequired;  // Was DrawsRequired - OpenERP =  draws_required
    public enum LookingTo { Build, BuyExisting, Renovate, AdditionalFunds, KeepExisting, ReduceRate, Renew, Port, NotSure } // Was LookingTo - OpenERP =  
    public LookingTo lookingTo; // Was LookingToAchieve - OpenERP =  lookingto
    //public double ltv; // The Amount of Mortgage divided by the Value of the Property to get a ratio of what % is Borrowed // Was LoanToValue - OpenERP =  ltv
    public double allApplicantsTotalIncomes;  // The Total Income of all Applicants in the Opportunity as defined in the Underwrite class of the selected Product.
    public enum Charge { First, Second, Third, Fourth, Bridge }
    public Charge chargeOnTitle;
    //public boolean hasChargesBehind;
    //public double chargesBehindAmount;
    
    //        ###Downpayment
    // Variables in OpenERP not required in Underwriting App
    //public double propertyValue; // Was ValueOfProperty - OpenERP =  property_value
    //public double downPaymentAmount; // Was DesiredDownpayment - OpenERP =  downpayment_amount
    
    //###Sources
    public enum DownpaymentSource { BankAccount, Investments, Borrowed, SaleOfAsset, Gift, Other } // Was DownpaymentSource - OpenERP =  
    //public DownpaymentSource downpaymentComingFrom; // Where is the Downpayment Coming From?  // Was SourceOfFunds - OpenERP =  
    //public double 	personalCashAmount; // Was BankAccountAmount - OpenERP =  personal_cash_amount
    //public double 	rrspAmount; // Was InvestmentAmount - OpenERP =  rrsp_amount
    //public double giftedAmount; // Was GiftAmount - OpenERP =  gifted_amount
    //public double borrowedAmount; // Was BorrowedAmount - OpenERP =  borrowed_amount
    //public double saleOfExistingAmount; // Was SaleOfAssetAmount - OpenERP =  sale_of_existing_amount
    //public double sweatEquityAmount;	//			Sweat Equity Amount- OpenERP =  sweat_equity_amount
    //public double secondaryFinancingAmount;		//	Secondary Financing Amount- OpenERP =  secondary_financing_amount
    //public double otherAmount; // Was OtherAmount - OpenERP =  other_amount

    public enum BorrowedSource { SecuredLOC, Other } // Was BorrowedSource - OpenERP =  
    //public BorrowedSource sourceOfBorrowing; // Was SourceOfBorrowing - OpenERP =  
    
    //        ###Suitability// Values from the Web Form need to be Set into these Variables.
    // The answers on the Webform to all 7 questions are:
    // Strongly Disagree      = 1
    // Somewhat Disagree      = 2
    // Not Sure               = 3
    // Somewhat Agree         = 4
    // Strongly Agree         = 5
    // Choose not to Answer.  = 0
    public enum Suitability { Strongly_Disagree, Somewhat_Disagree, Not_Sure, Somewhat_Agree, Strongly_Agree, Choose_not_to_Answer }
    //public Suitability job_5_years;  // Was SameJob - OpenERP =  job_5_years
    //public Suitability income_decreased_worried; // Was IncomeDrop - OpenERP =  income_decreased_worried
    //public Suitability future_family; // Was LargerFamily - OpenERP =  future_family
    //public Suitability buy_new_vehicle; // Was NewVehicle - OpenERP =  buy_new_vehicle
    //public Suitability lifestyle_change; // Was LifestyleChange - OpenERP =  lifestyle_change
    //public Suitability financial_risk_taker; // Was RiskTaker - OpenERP =  financial_risk_taker
    //public Suitability property_less_than_5_years; // Was KeepLess5Yr - OpenERP =  property_less_then_5_years

    public int job_5_years_int;  // Was SameJob - OpenERP =  job_5_years
    public int income_decreased_worried_int; // Was IncomeDrop - OpenERP =  income_decreased_worried
    public int future_family_int; // Was LargerFamily - OpenERP =  future_family
    public int buy_new_vehicle_int; // Was NewVehicle - OpenERP =  buy_new_vehicle
    public int lifestyle_change_int; // Was LifestyleChange - OpenERP =  lifestyle_change
    public int financial_risk_taker_int; // Was RiskTaker - OpenERP =  financial_risk_taker
    public int property_less_than_5_years_int; // Was KeepLess5Yr - OpenERP =  property_less_then_5_years


    // Fields in OpeERP ... Recommendations may end up being another object that simply contains certain fields from the product record
    // Alternatively, the product_ids of the recommendation is returned and OpenERP Uses it to map data into the recommendation list
    //        ###Recommendations
    //product_ids 						Recommendations
    //Note :-- product_ids is a one2many field, its means that crm.lead object has a primary-forgin key relation with 'product.product' object. 
    //    ' product.product  filelds in Recommendation list		
    //        lender						Lender				
    //        name						Product Name		
    //        mortgage_type				Mortgage Type		
    //        term  					Term			
    //        maximum_amortization			Maximum Amortization
    //        interest_rate				Interest Rate	
    //        cash_back					Cashback Percent			
    
    //###Deal Notes
    //deal_ids 						Deal Notes
    //Note :-- deal_ids  is a one2many field, its means that crm.lead object has a primary-forgin key relation with 'deal' object. 
    //    ' deal  filelds	

    // At the moment, all note Lists are in the Underwrite Object.  This is because each underwrite gernates separate lists of notes.  However, wee will only transfer a single list of each type back to OpenERP to go into the Opprotunity Object

    // In OpenERP for Assistant / Broker to capture notes about the oppotunity.  Large Text field not required in UW App
    // internal_notes						Internal Notes


    //        #####Property###########################
    // In OpenERP for communication to Lender, but not required in UW App
    //plan							Plan
    //block							Block
    //lot							Lot
    //mls							MSL
    //new_home_warranty				New Home Warranty

    // Property Related Variables
    // PropertyStyle is presently not required for underwriting ... 
    //public enum StyleOfProperty { Bungalow, BiLevel, TwoStory, SplitLevel, StoryAndHalf, ThreeStory, Other } - OpenERP =  
    //public StyleOfProperty PropertyStyle; - OpenERP =  property_style
    public enum PropertyOfType { House, Duplex, FourPlex, TowHouse, RowHouse, ApartmentStyle, MobileHome, ModularHome, Other } // Was PropertyOfType - OpenERP =  
    //public PropertyOfType propertyType; // Was PropertyType - OpenERP =  property_type
    public enum StyleOfApartment { LowRise, HighRise } // Was StyleOfApartment - OpenERP =  
    //public StyleOfApartment apartmentStyle; // Was ApartmentStyle - OpenERP =  apartment_style
    
    //public String address; // Address field of Property Being Mortgaged // Was Address - OpenERP =  address
    //public String city;  // City field of Property Being Mortgaged // Was City - OpenERP =  city
    //public int age; // Was Age - OpenERP =  age
    //public int squareFootage; // Was SquareFootage - OpenERP =  square_footage
    //public int lotSize; // Was LotSize - OpenERP =  lot_size
    //public double acres; // Size of Lot / Acreage property is on.    // Was NumberOfAcres - OpenERP =  acres
    //public double propertyTaxes; // Was PropertyTaxes - OpenERP =  property_taxes
    
    // In OpenERP, not required in UW App at this time. 
    //min_heat_fee					Minimum Heat Fee
    //garage_type					Garage Type
    //garage_size						Garage Size
    
    //public double outbuildingsValue; // Appx Value of Detatched outbuildings (Shop / Barn, etc) // Was OutbuildingValue - OpenERP =  outbuildings_value
    public enum WhosLiving { Owner, Renter, OwnerAndRenter, SecondHome } // Was WhosLiving - OpenERP =  
    //public WhosLiving livingInProperty; // Was WhoIsLivingIn - OpenERP =  living_in_property
    public enum WhoPaysHeating { HeatIncluded, HeatSeparate } // Was WhoPaysHeating - OpenERP =  
    //public WhoPaysHeating renterPayHeating; // Was RenterPayHeating - OpenERP =  renter_pay_heating
    //public double monthlyRentalIncome; // Amount of rent anticipated if shopping for a rental (If existing, it is in the income class) // Was ExpectedMonthlyRent - OpenERP =  monthly_rental_income
    
    //        Internal Notes
    //public boolean isCountryResidential; // Was IsCountryResidential - OpenERP =  is_country_residential
    //public boolean isCondo; // Was IsCondo - OpenERP =  is_condo
    //public boolean isAgriculturalLess10Acres; // Was IsAgriculturalLess10Acres - OpenERP =  is_agricultural_less_then_10_acres
    //public boolean isCommercial; // Was IsCommercial - OpenERP =  is_commercial
    //public boolean isFractionalInterests; // Was IsFractionalInterests - OpenERP =  is_fractional_interests
    //public boolean isCooperativeHousing; // Was IsCooperativeHousing - OpenERP =  is_co_operative_housing
    //public boolean isGrowOps; // Was IsGrowOps - OpenERP =  is_grow_ops
    //public boolean isRentalPools; // Was IsRentalPools - OpenERP =  is_rental_pools
    //public boolean isAgeRestricted; // Was IsAgeRestricted - OpenERP =  is_age_restricted
    //public boolean isDuplex; // Was IsDuplex - OpenERP =  is_duplex
    //public boolean isFourPlex; // Was IsFourPlex - OpenERP =  is_four_plex
    //public boolean isSixPlex; // Was IsSixPlex - OpenERP =  is_six_plex
    //public boolean isEightPlex; // Was IsEightPlex - OpenERP =  is_eight_plex

    //public int distanceToMajorCenter; // OpenERP =  is_eight_plex
    //public boolean lenderRequiresInsurance; // boolean in Opportunity set by Assistant that Lender Requires Insurance on the tansaction; // Was LenderRequiresInsurance - OpenERP =  public boolean LenderRequiresInsurance; // boolean in Opportunity set by Assistant that Lender Requires Insurance on the tansaction; // Was LenderRequiresInsurance - OpenERP = public boolean LenderRequiresInsurance; // boolean in Opportunity set by Assistant that Lender Requires Insurance on the tansaction; // Was LenderRequiresInsurance - OpenERP =  lender_requires_insurance
    public double existingPayoutPenalty;
    
    // In OpenERP, but presently missing in UW app, 
    // selected Product and is_construction_mortgage may be required later. 
    //min_heat_fee						Minimum Heat Fee
    //selected_product					Selected Product
    //is_construction_mortgage				Is Construction Mortgage


    //public boolean isLifeLeasedProperty; // Was IsLifeLeasedProperty - OpenERP =  is_life_leased_property
    //public boolean isLeasedLand; // Was IsLeasedLand - OpenERP =  is_leased_land
    //public boolean isRawLand; // Was IsRawLand - OpenERP =  is_raw_land
    //public boolean isMobileHomes; // Was IsMobileHomes - OpenERP =  is_mobile_homes
    //public boolean isModularHomes; // Was IsModularHomes - OpenERP =  is_modular_homes
    //public boolean isFloatingHomes; // Was IsFloatingHomes - OpenERP =  is_floating_homes
    //public boolean isBoardingHouses; // Was IsBoardingHouses - OpenERP =  is_boarding_house
    //public boolean isRoomingHouses; // Was IsRoomingHouses - OpenERP =  is_rooming_houses
    //public boolean isNonConvConstruction; // Was IsNonConvConstruction - OpenERP =  is_non_conv_construction
    //public boolean isCottageRecProperty; // Was IsCottageRecProperty - OpenERP =  is_cottage_rec_property
    //public boolean isRentalProperty; // Was IsRentalProperty - OpenERP =  is_rental_property
    //public boolean isHighRatio2ndHome; // Was IsHighRatio2ndHome - OpenERP =  is_high_ratio_2nd_home
    //public boolean isUninsuredConv2ndHome; // Was IsUninsuredConv2ndHome - OpenERP =  is_uninsured_conv_2nd_home
    //public boolean isaSmallCentre; // True if the Property in the opportunity is in a small center // Was SmallCenterProperty - OpenERP =  is_a_small_centre
    
    // In OpenERP large Text field.  Not needed in UW App.
    //internal_note_property				Internal Notes		        
    
    // From OpenERP ... structure of the Assistant notes / Tasks list in Verificaiton Tab
    //        #####Verification############################################
 
    //task				Task
    //Note :-- task is a one2many field, its means that crm.lead object has a primary-forgin key relation with 'opp.task' object. 
    //    opp.task  filelds	
    //        name		Note Type
    //        done		Done
    //        urgency	Urgency
    //        description	Description
	
    //        internal_notes 		Internal Notes   one2many

    // A number of these fields will be calculated and communicated back to OpenERP in the last of the 3 WS calls
    // I will uncomment as the 3rd method takes shape. 
    //#####Final solution############################################
    //final_lender 		Lender
    //lender_name		Lender Name
    //lender_response		Lender Response
    //lender_ref		Lender Ref#
    //morweb_filogix		Morweb/Filogix Ref#
    //insurer			Insurer
    //insurerref			Insurer Ref#
    //insurerfee		Insurer Fee
    //purchase_price		Purchase Price
    //downpayment		DownPayment
    //closingdate		Closing Date
    //renewaldate		Renewal Date
    //total_mortgage_amount		Total Mortgage Amount
    //rate				Rate %
    //term				Amortization
    //monthly_payment		monthly_payment
    //mortgage_type			Mortgage Type
    //product_type			Product Type
    //cash_back			Cash Back
    //base_commission		Base Commissions
    //volume_commission		Volume Commissions
    //public double commitmentFee;	//		Commitment Fee
    //public double lenderFee;		//	Lender Fee
    //public double privateFee;		//	Private Fee ===> broker_fee
    public double totalOneTimeFees;	//total_one_time_fees;
    //verify_product			Verify Product
    
    // The Following field seem to still be missing from the OpenERP Opportunity Record.  I will confirm and communicate.
    //public boolean isAgricultural; // Was IsAgricultural - OpenERP =  
    //public String province; // Province field of Property Being Mortgaged // Was Province - OpenERP =  
    //public String postalCode; // Postal Code field of Property Being Mortgaged // Was PostalCode - OpenERP =          
    public enum FrequencyDesired { BiWeekly, SemiMonthly, Monthly, Weekly }; // Was FrequencyDesired - OpenERP =  
    public FrequencyDesired desiredFequency; // Was DesiredFequency - OpenERP =  
    //public int desiredAmortization; // Was DesiredAmortization - OpenERP =  
    public enum DesiredProductTerm { Month6, Year1, Year2, Year3, Year4, Year5, Year6, Year7, Year8, Year9, Year10, None } // Was DesiredProductTerm - OpenERP =  
    //public DesiredProductTerm desiredTerm; // Was DesiredTerm - OpenERP =  
    //public Date closingDate;  // Was ClosingDate - OpenERP =  
    //public double condoFees; // Was CondoFees - OpenERP =          
    //public double renovationValue; // The amount of money being borrowed for renovations. // Was RenovationValue - OpenERP =  
    public boolean refinanceInsured; // (Add to Opportunity in OpenERP) Needs to be set to true on opportunities where the property is already insured with current owner and is being refianced to increase the Mortgage Amount // Was RefinanceInsured - OpenERP =  
    //public boolean nonIncomeQualifer;  // Needs to be set to true on opportunites where applicant income is not proveable.  // Was NonIncomeQualifer - OpenERP =  
    //public boolean isMilitary; // true if applicant is Canadian Military. // Was IsMilitary - OpenERP =  
    public String potentialCities; // Cities field from OpenERP that need to be considered.              // Was Cities - OpenERP =  
    public boolean nonResidentApplicant; // True if the Opportunity involves a Non-Resident Applicant (Needs to be Set somehow ...) // Was NonResidentApplicant - OpenERP =  
    
    // The following variables are calculated and used later 
    //public double currentMortgageAmount; // Balance on current mortgage if it is being refinaced or renewed. // Was CurrentMortgageAmount - OpenERP =  
    //public double currentInterestRate; // Rate on current Mortgage used to calculate payout penalties in refinance.
    public Date currentRenewalDate; // Date current Mortgage Renews ... Used to Calculate payout penalty. 
    public boolean highRatio; // Specifies that Opportunity is High Ratio ...  // Was HighRatio - OpenERP =  
    public boolean isImmigrant; // true if applicant is Immigrated to Canada in last 5 years and not yet a resident. // Was IsImmigrant - OpenERP =  
    public int immigrationMonthsAgo; // Numner of Months since immigration Date. // Was ImmigrationMonthsAgo - OpenERP =  
    public int employmentDuration; // Number of months at employer / related employer. // Was EmploymentDuration - OpenERP =  
    public int avgSuitability; // Was AvgSuitability - OpenERP =  
    public DesiredProductTerm suitableTerm; // Was SuitableTerm - OpenERP =  
    public DesiredTypeOfProduct suitableType; // Was SuitableType - OpenERP =  
    public Date fundingDate; // Was FundingDate - OpenERP = 
    public List<String> consideredCities; // Was ConsideredCities - OpenERP = 
    
    // These variables may end up being deleted after I think though them a bit further ... 
    public AlgoApplicant applicant; // Was applicant - OpenERP =  
    public AlgoApplicant coapplicant; // Was coapplicant - OpenERP = 
    //public boolean isCottageRecProperty; // True if the property is a Recreational or Cottage Property // Was RecreationalProperty - OpenERP =  
    public boolean residenceProperty; // True if the Property in the opportunity is a primary residence // Was ResidenceProperty - OpenERP =  

    // Final Verify Fields that need to be written back to OpenERP: 
    //public double amortization;
    //public double baseCommission;
    //public double baseCompAmount;
    // public double bonus_comp_amount;  Not needed ... 
    //public double cashBack;
    //public double downpayment;
    //public double insurerfee;
    //public String lenderName;
    //public double marketingCompAmount;
    //public double monthlyPayment;
    //public AlgoProduct.TypeOfProduct mortgageType;
    //public AlgoProduct.OpenClosed openClosed;
    //public double rate;
    //public Date renewaldate;
    //public AlgoProduct.ProductTerm term;
    //public double totalMortgageAmount;
    //public double trailerCompAmount;
    //public double volumeBonusAmount;
    //public double totalCompAmount;
    
	private List<String> acresconsideredCities = new ArrayList<String>();

	public double riskPercentile;
	public double riskBias;
	public int answeredSuitabilityQuestions;
    

    //Need constructor buid to set values from the Webform that was submitted. Data Should not involve Identity 
    public AlgoOpportunity(Opportunity opp)
    {
    	this.opp = opp;
        // Cantor - I left the constructor for your coding depending on how you are managing the WebService Sending the Data.

        // The Large Text field "Cities" in OpenERP is optimally converted into a list with each city as a separate String.
    	//TODO Need to set Cities into the list  Presently Not done ... 
    	potentialCities = opp.consideredCites;
    	if (potentialCities != null){
    		consideredCities = Arrays.asList(potentialCities.split(","));
            acresconsideredCities.add(opp.city);
            System.out.println(opp.city);
    	}
    	
        // For the purposes of Underwriting, this will always be monthly.  Broker and assistants will manage with client while marketing.
        desiredFequency = FrequencyDesired.Monthly;
        
        // Logic that looks if Oppportunity is Non-Resident Applicant and set boolean.
        Applicant currApplicant = opp.applicants.get(0);
    	AlgoApplicant applicant = new AlgoApplicant(currApplicant);
        if (currApplicant.nonResident == true)
        	nonResidentApplicant = true;

        if (applicant.monthsSinceImmigration > 0)
        {
        	isImmigrant = true;
        	immigrationMonthsAgo = applicant.monthsSinceImmigration;
        }
        
        // Guy to create logic for employment Duration.  (Maybe in Applicant Object)

        if (SelectionHelper.compareSelection(LendingGoal.Prequalify, opp.whatIsYourLendingGoal))
            opp.desiredMortgageAmount = opp.propertyValue - opp.downpaymentAmount;

        opp.ltv = (opp.desiredMortgageAmount / opp.propertyValue) * 100; // Percentage of the Property Value represented by the Loan

        // Determine if Property being Mortgaged is a rental Property
        if (opp.isRentalProperty == true || (SelectionHelper.compareSelection(WhosLiving.Renter, opp.livingInProperty)))
        	opp.isRentalProperty = true;
        else
        	residenceProperty = true;
        
        double costBias = 0;
        riskBias = 0; 
        answeredSuitabilityQuestions = 0;
//        Suitability Questions:
        // How Important is it to Save Cost Now?
        // How Important is it to not have Cost rise in the future? 
//        	I easily imagine myself in the same job 5 years from now:  Cost impact?  Risk Impact?
        // 
//        	If my income went down by a few hundred dollars a month, I would start to really worry about my bills:  Cost impact?  Risk Impact? 
//        	I might have a larger family sometime over the next 5 years:  Cost impact?  Risk Impact? Likely to sell - Payout risk
//        	I am considering buying a new vehicle in the next 6-12 months:  Cost impact?  Risk Impact? 
//        	In the next 2-5 years, I am planning on significantly changing my recreation lifestyle and I might require much more storage at home:  Cost impact?  Risk Impact? 
//        	I consider myself to be a financial risk taker:  Cost impact?  Risk Impact? 
//        	I think it is very likely that I will have this property for less than 5 years:  Cost impact?  Risk Impact? 
                
        if(opp.job5Years != null && opp.job5Years.trim().length() > 0)
        {        	
        	System.out.println("opp.job5Years:" + opp.job5Years);
        	Suitability job5Years = Suitability.values()[Integer.parseInt(opp.job5Years)-1];
            switch (job5Years) {
    			case Choose_not_to_Answer: job_5_years_int = 0;
    				break;
    			case Strongly_Disagree: job_5_years_int = 1;
    				break;
    			case Somewhat_Disagree: job_5_years_int = 2;
    				break;
    			case Not_Sure: job_5_years_int = 3;
    				break;
    			case Somewhat_Agree: job_5_years_int = 4;
    				break;
    			case Strongly_Agree: job_5_years_int = 5;
    				break;
    			default:
    				
    		}
        }
        if (job_5_years_int != 0)
        {
        	answeredSuitabilityQuestions = answeredSuitabilityQuestions + 1;
        	costBias = costBias + (double)5;
        	riskBias = riskBias + (6-(double)job_5_years_int);
        }
        // I easily imagine myself in the same job 5 years from now:
        // 1) Cost Bias      
        // 2) Risk Bias
        //  - PayoutPenaty (Moving) If 5,  Low
        //  - Loss of Income          Low
        //  - Interest rate increase    n/a
        
        
        if(opp.incomeDecreasedWorried != null && opp.incomeDecreasedWorried.trim().length() > 0)
        {
        	Suitability incomeDecreasedWorried = Suitability.values()[Integer.parseInt(opp.incomeDecreasedWorried) - 1];
            switch (incomeDecreasedWorried) {
    			case Choose_not_to_Answer: income_decreased_worried_int = 0;
    				break;
    			case Strongly_Disagree: income_decreased_worried_int = 1;
    				break;
    			case Somewhat_Disagree: income_decreased_worried_int = 2;
    				break;
    			case Not_Sure: income_decreased_worried_int = 3;
    				break;
    			case Somewhat_Agree: income_decreased_worried_int = 4;
    				break;
    			case Strongly_Agree: income_decreased_worried_int = 5;
    				break;
    			default:
    				
    		}
        }
        // If my income went down by a few hundred dollars a month, I would start to really worry about my bills:
        // 1) Cost Bias      
        // 2) Risk Bias
        //  - PayoutPenaty (Moving) If 5,  Low
        //  - Loss of Income          High
        //  - Interest rate increase    n/a
        if (income_decreased_worried_int != 0)
        {
        	answeredSuitabilityQuestions = answeredSuitabilityQuestions + 1;
        	costBias = costBias + (double)5;
        	riskBias = riskBias + ((double)income_decreased_worried_int);
        }
        
        if(opp.futureFamily != null && opp.futureFamily.trim().length() > 0)
        {
        	Suitability futureFamily = Suitability.values()[Integer.parseInt(opp.futureFamily) - 1];
            switch (futureFamily) {
    			case Choose_not_to_Answer: future_family_int = 0;
    				break;
    			case Strongly_Disagree: future_family_int = 1;
    				break;
    			case Somewhat_Disagree: future_family_int = 2;
    				break;
    			case Not_Sure: future_family_int = 3;
    				break;
    			case Somewhat_Agree: future_family_int = 4;
    				break;
    			case Strongly_Agree: future_family_int = 5;
    				break;
    			default:
    				
    		}
        }
        // I might have a larger family sometime over the next 5 years:
        // 1) Cost Bias      
        // 2) Risk Bias
        //  - PayoutPenaty (Moving) If 5,  HIGH
        //  - Loss of Income          High
        //  - Interest rate increase    n/a
        if (future_family_int != 0)
        {
        	answeredSuitabilityQuestions = answeredSuitabilityQuestions + 1;
        	costBias = costBias + (double)5;
        	riskBias = riskBias + ((double)future_family_int);
        }
        

        if(opp.buyNewVehicle != null && opp.buyNewVehicle.trim().length() > 0)
        {
        	Suitability buyNewVehicle = Suitability.values()[Integer.parseInt(opp.buyNewVehicle) - 1];
            switch (buyNewVehicle) {
    			case Choose_not_to_Answer: buy_new_vehicle_int = 0;
    				break;
    			case Strongly_Disagree: buy_new_vehicle_int = 1;
    				break;
    			case Somewhat_Disagree: buy_new_vehicle_int = 2;
    				break;
    			case Not_Sure: buy_new_vehicle_int = 3;
    				break;
    			case Somewhat_Agree: buy_new_vehicle_int = 4;
    				break;
    			case Strongly_Agree: buy_new_vehicle_int = 5;
    				break;
    			default:
    				
    		}
        }
        // I am considering buying a new vehicle in the next 6-12 months:
        // 1) Cost Bias      
        // 2) Risk Bias
        //  - PayoutPenaty (Moving) If 5,  HIGH
        //  - Loss of Income          High
        //  - Interest rate increase    n/a
        if (buy_new_vehicle_int != 0)
        {
        	answeredSuitabilityQuestions = answeredSuitabilityQuestions + 1;
        	costBias = costBias + (double)5;
        	riskBias = riskBias + ((double)buy_new_vehicle_int);
        }
        
        if(opp.lifestyleChange != null && opp.lifestyleChange.trim().length() > 0)
        {
        	Suitability lifestyleChange = Suitability.values()[Integer.parseInt(opp.lifestyleChange) - 1];
            switch (lifestyleChange) {
    			case Choose_not_to_Answer: lifestyle_change_int = 0;
    				break;
    			case Strongly_Disagree: lifestyle_change_int = 1;
    				break;
    			case Somewhat_Disagree: lifestyle_change_int = 2;
    				break;
    			case Not_Sure: lifestyle_change_int = 3;
    				break;
    			case Somewhat_Agree: lifestyle_change_int = 4;
    				break;
    			case Strongly_Agree: lifestyle_change_int = 5;
    				break;
    			default:
    				
    		}
        }
        // In the next 2-5 years, I am planning on significantly changing my recreation lifestyle and I might require much more storage at home:
        // 1) Cost Bias      
        // 2) Risk Bias
        //  - PayoutPenaty (Moving) If 5,  HIGH
        //  - Loss of Income          High
        //  - Interest rate increase    n/a
        if (lifestyle_change_int != 0)
        {
        	answeredSuitabilityQuestions = answeredSuitabilityQuestions + 1;
        	costBias = costBias + (double)5;
        	riskBias = riskBias + ((double)lifestyle_change_int);
        }
        
        if(opp.financialRiskTaker != null && opp.financialRiskTaker.trim().length() > 0)
        {
        	Suitability financialRiskTaker = Suitability.values()[Integer.parseInt(opp.financialRiskTaker) - 1];
            switch (financialRiskTaker) {
    			case Choose_not_to_Answer: financial_risk_taker_int = 0;
    				break;
    			case Strongly_Disagree: financial_risk_taker_int = 1;
    				break;
    			case Somewhat_Disagree: financial_risk_taker_int = 2;
    				break;
    			case Not_Sure: financial_risk_taker_int = 3;
    				break;
    			case Somewhat_Agree: financial_risk_taker_int = 4;
    				break;
    			case Strongly_Agree: financial_risk_taker_int = 5;
    				break;
    			default:
    				
    		}
        }
        // In the next 2-5 years, I am planning on significantly changing my recreation lifestyle and I might require much more storage at home:
        // 1) Cost Bias      
        // 2) Risk Bias
        //  - PayoutPenaty (Moving) If 5,  HIGH
        //  - Loss of Income          High
        //  - Interest rate increase    n/a
        if (financial_risk_taker_int != 0)
        {
        	answeredSuitabilityQuestions = answeredSuitabilityQuestions + 1;
        	costBias = costBias + (double)5;
        	riskBias = riskBias + (6 - (double)financial_risk_taker_int);
        }
        
        if(opp.propertyLessThen5Years != null && opp.propertyLessThen5Years.trim().length() > 0)
        {
        	Suitability propertyLessThan5Years = Suitability.values()[Integer.parseInt(opp.propertyLessThen5Years) - 1];
            switch (propertyLessThan5Years) {
    			case Choose_not_to_Answer: property_less_than_5_years_int = 0;
    				break;
    			case Strongly_Disagree: property_less_than_5_years_int = 1;
    				break;
    			case Somewhat_Disagree: property_less_than_5_years_int = 2;
    				break;
    			case Not_Sure: property_less_than_5_years_int = 3;
    				break;
    			case Somewhat_Agree: property_less_than_5_years_int = 4;
    				break;
    			case Strongly_Agree: property_less_than_5_years_int = 5;
    				break;
    			default:
    				
    		}
        }
        // In the next 2-5 years, I am planning on significantly changing my recreation lifestyle and I might require much more storage at home:
        // 1) Cost Bias      
        // 2) Risk Bias
        //  - PayoutPenaty (Moving) If 5,  HIGH
        //  - Loss of Income          High
        //  - Interest rate increase    n/a
        if (property_less_than_5_years_int != 0)
        {
        	answeredSuitabilityQuestions = answeredSuitabilityQuestions + 1;
        	costBias = costBias + (double)5;
        	riskBias = riskBias + (6 - (double)property_less_than_5_years_int);
        }
        
        double totalBias = costBias + riskBias;
        riskPercentile = riskBias / totalBias * 100;
        
        // Need to check with Kendal Around how we want this handled with the use of assitants / what clients are likly to enter in Applicaiton
        checkSuitability();
    }

    public void checkSuitability()
    {
        // Discuss with Kendall whether we want to include any notes for the Marketing Template ...

        int job = job_5_years_int; // Variable 1-5 Fixed
        int drop = income_decreased_worried_int; // Variable 1-5 Fixed
        int famincrease = 6 - future_family_int; // Fixed 5-1 Variable
        int newcar = buy_new_vehicle_int; //  Variable Disagree ... Agree Fixed (1-5)
        int storage = 6 - lifestyle_change_int;  // Fixed 5-1 Variable 
        int risk = 6 - financial_risk_taker_int; // Fixed 5-1 Variable 
        int keepshort = 6 - property_less_than_5_years_int; // Fixed 5-1 Variable 

        // Guy and Kendall To Create Notes on Suitability ... 

        List<Integer> suitabilityFactors = new ArrayList<Integer>();
        if (job > 0 && job != 6)
            suitabilityFactors.add(job);
        if (drop > 0 && drop != 6)
            suitabilityFactors.add(drop);
        if (famincrease > 0 && famincrease != 6)
            suitabilityFactors.add(famincrease);
        if (newcar > 0 && newcar != 6)
            suitabilityFactors.add(newcar);
        if (storage > 0 && storage != 6)
            suitabilityFactors.add(storage);
        if (risk > 0 && risk != 6)
            suitabilityFactors.add(risk);
        if (keepshort > 0 && keepshort != 6)
            suitabilityFactors.add(keepshort);

        double averageSuitability;
        averageSuitability = Util.listAverage(suitabilityFactors); //TODO
        
        // Determining Risk and Cost with an average won't work ... 
        // Need to determine each separately ... Then the "Balance" of both ...   
        

        if ( suitabilityFactors.size() > 3 && averageSuitability <= 3)
            suitableType = DesiredTypeOfProduct.Variable;
        else if (suitabilityFactors.size() > 3 && averageSuitability > 3)
        	suitableType = DesiredTypeOfProduct.Fixed;
        else
        	suitableType = DesiredTypeOfProduct.Fixed;

        double roundedAvgSuitability = Math.round(averageSuitability);

        if (suitabilityFactors.size() > 3)
        {
	        if (roundedAvgSuitability > 0 && roundedAvgSuitability <= 1)
	        	suitableTerm = DesiredProductTerm.Year1;
	        else if (roundedAvgSuitability > 1 && roundedAvgSuitability <= 2)
	        	suitableTerm = DesiredProductTerm.Year2;
	        else if (roundedAvgSuitability > 2 && roundedAvgSuitability <= 3)
	        	suitableTerm = DesiredProductTerm.Year3;
	        else if (roundedAvgSuitability > 3 && roundedAvgSuitability <= 4)
	        	suitableTerm = DesiredProductTerm.Year4;
	        else if (roundedAvgSuitability > 4 && roundedAvgSuitability <= 5)
	        	suitableTerm = DesiredProductTerm.Year5;
	        else
	        	suitableTerm = DesiredProductTerm.Year5;
        }
        else
        	suitableTerm = DesiredProductTerm.None;
        // Discuss with Kendall whether we want to include any notes for the Marketing Template ...
    }

    /* Action Type (This is only a Text Header, not field)
           Purchase (These are the fields under the Text Header Product Type)
           Purchase plus improvements  (Edit, flatten out each program on Monday)
           Refinance
           Equity Take Out
           Switch/Transfer
           Port
           Portability Plus
           Line of Credit
           CashBack
           Non-Income Qualifier
           Non Resident
           Blanket Mortgage
           Vendor Take Back
    */



}
