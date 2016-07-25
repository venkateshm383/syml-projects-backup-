package com.syml.bean.algo;

import java.util.List;

public class Match
{
    public AlgoOpportunity clientOpportunity;
    public AlgoProduct potentialProduct;
    public AlgoApplicant applicant;
    public AlgoApplicant coapplicant;

    public int applicantBeacon;
    public int coApplicantBeacon;

    public int applicantLongestRunningCreditLine;
    public int applicantActive12MonthLatePayments;
    public int coApplicantLongestRunningCreditLine;
    public int coApplicantActive12MonthLatePayments;

    public int applicantMonthsSinceBankruptcy;
    public boolean applicantBankruptcy;
    public int applicantMonthsSinceOrderlyDebtPayment;
    public boolean applicantOrderlyDebtPayment;
    public int coApplicantMonthsSinceBankruptcy;
    public boolean coApplicantBankruptcy;
    public int coApplicantMonthsSinceOrderlyDebtPayment;
    public boolean coApplicantOrderlyDebtPayment;

    public List<AlgoLiability> applicantActiveLiabilities;
    public List<AlgoLiability> coApplicantActiveLiabilities;

    public double gDSRatio; // The Calculated Genderal Debt Service Ratio for this Opportunity 
    public double tDSRatio; // The Calculated Total Debt Service Ratio for this Opportunity

    public int whitFlags;
    public int greyFlags;
    public int blackFlags;
    

    public int workingCreditScore; // Highest Credit Score of the Applicants - If not the 1st Applicant in the List of Applicants, need to communicate to Assistants to Switch the Applicants in the Opportunity Record
	
	// Need to create a Flag enum for every underwriting variable
	public enum MinBeaconFlag { White, Greg, Black };
	
	
	public Match(AlgoOpportunity clientOpportunity, AlgoProduct potentialProduct)
	{
	//    // Cantor - I left Most of the constructor for your coding depending on how you are managing the WebService Sending the Data.
	    
	    this.clientOpportunity = clientOpportunity;
	    this.potentialProduct = potentialProduct;
	    
	    
	//    // Calculate Applicant's longest running active Credit line.
	//    applicantLongestRunningCreditLine = 0;
	//    for (AlgoLiability liability : clientOpportunity.applicant.activeLiabilities)
	//    {
	//        if (liability.ageOfLiability > applicantLongestRunningCreditLine)
	//            applicantLongestRunningCreditLine = liability.ageOfLiability;
	//    }
	//
	//    coApplicantLongestRunningCreditLine = 0;
	//    for (AlgoLiability liability : clientOpportunity.coapplicant.activeLiabilities)
	//    {
	//        if (liability.ageOfLiability > coApplicantLongestRunningCreditLine)
	//            coApplicantLongestRunningCreditLine = liability.ageOfLiability;
	//    }
	//                    
	//    // Section of Code to Categorize Client's Credit into a Category of Good / Medium / Poor for later usage
	//	//[Cantor: need more detail]
	//    //#region ClientCreditCategorization
	//    
	//    // Check this Section of Code with Audrey and Kendall ... It may need adapting for the Bankruptcy / OPD Section.
	//	 // [Cantor: Not sure this section of codes logic]
	//    // Cantor, this section of code is to categorize each applicant into having "Good, Poor, or Medium" Credit.
	//    // Depending on their categorization, the lender will have more or less flexibitiy with Underwriting rules, particularly GDS and TDS Ratios.
	//    if (applicantLongestRunningCreditLine <= 12
	//        || (applicantBankruptcy == true && applicantMonthsSinceBankruptcy <= 12)
	//        || (applicantOrderlyDebtPayment == true && applicantMonthsSinceOrderlyDebtPayment <= 12)) // Add in Bankruptcy
	//        applicant.applicantCreditCategory = AlgoApplicant.CreditCategory.Poor;
	//    else if ((applicantLongestRunningCreditLine > 12 && applicantLongestRunningCreditLine <= 24)
	//        || (applicantBankruptcy == true && applicantMonthsSinceBankruptcy > 12 && applicantMonthsSinceBankruptcy <= 36)
	//        || (applicantOrderlyDebtPayment == true && applicantMonthsSinceOrderlyDebtPayment > 12 && applicantMonthsSinceOrderlyDebtPayment <= 36))
	//    {
	//        if (applicantBeacon > potentialProduct.goodCreditBeaconSplit)
	//        {
	//            if (applicantActive12MonthLatePayments <= potentialProduct.goodCredit12MoLates)
	//                applicant.applicantCreditCategory = AlgoApplicant.CreditCategory.Good;
	//            else if (applicantActive12MonthLatePayments > potentialProduct.goodCredit12MoLates && applicantActive12MonthLatePayments <= potentialProduct.poorCredit12MoLates)
	//                applicant.applicantCreditCategory = AlgoApplicant.CreditCategory.Medium;
	//            else
	//                applicant.applicantCreditCategory = AlgoApplicant.CreditCategory.Poor;
	//        }
	//        else if (applicantBeacon <= potentialProduct.goodCreditBeaconSplit && applicantBeacon > potentialProduct.poorCreditBeaconSplit)
	//        {
	//            if (applicantActive12MonthLatePayments <= potentialProduct.goodCredit12MoLates)
	//                applicant.applicantCreditCategory = AlgoApplicant.CreditCategory.Good;
	//            else if (applicantActive12MonthLatePayments > potentialProduct.goodCredit12MoLates && applicantActive12MonthLatePayments <= potentialProduct.poorCredit12MoLates)
	//                applicant.applicantCreditCategory = AlgoApplicant.CreditCategory.Medium;
	//            else
	//                applicant.applicantCreditCategory = AlgoApplicant.CreditCategory.Poor;
	//        }
	//        else
	//        {
	//            if (applicantActive12MonthLatePayments == 0)
	//                applicant.applicantCreditCategory = AlgoApplicant.CreditCategory.Good;
	//            else if (applicantActive12MonthLatePayments > 0 && applicantActive12MonthLatePayments <= potentialProduct.goodCredit12MoLates)
	//                applicant.applicantCreditCategory = AlgoApplicant.CreditCategory.Medium;
	//            else
	//                applicant.applicantCreditCategory = AlgoApplicant.CreditCategory.Poor;
	//        }
	//    }
	//    else
	//    {
	//        if (applicantBeacon > potentialProduct.goodCreditBeaconSplit)
	//        {
	//            if (applicantActive12MonthLatePayments <= potentialProduct.goodCredit12MoLates)
	//                applicant.applicantCreditCategory = AlgoApplicant.CreditCategory.Good;
	//            else if (applicantActive12MonthLatePayments > potentialProduct.goodCredit12MoLates && applicantActive12MonthLatePayments <= potentialProduct.poorCredit12MoLates)
	//                applicant.applicantCreditCategory = AlgoApplicant.CreditCategory.Medium;
	//            else
	//                applicant.applicantCreditCategory = AlgoApplicant.CreditCategory.Poor;
	//        }
	//        else if (applicantBeacon <= potentialProduct.goodCreditBeaconSplit && applicantBeacon > potentialProduct.poorCreditBeaconSplit)
	//        {
	//            if (applicantActive12MonthLatePayments <= potentialProduct.goodCredit12MoLates)
	//                applicant.applicantCreditCategory = AlgoApplicant.CreditCategory.Good;
	//            else if (applicantActive12MonthLatePayments > potentialProduct.goodCredit12MoLates && applicantActive12MonthLatePayments <= potentialProduct.poorCredit12MoLates)
	//                applicant.applicantCreditCategory = AlgoApplicant.CreditCategory.Medium;
	//            else
	//                applicant.applicantCreditCategory = AlgoApplicant.CreditCategory.Poor;
	//        }
	//        else
	//        {
	//            if (applicantActive12MonthLatePayments == 0)
	//                applicant.applicantCreditCategory = AlgoApplicant.CreditCategory.Good;
	//            else if (applicantActive12MonthLatePayments > 0 && applicantActive12MonthLatePayments <= potentialProduct.goodCredit12MoLates)
	//                applicant.applicantCreditCategory = AlgoApplicant.CreditCategory.Medium;
	//            else
	//                applicant.applicantCreditCategory = AlgoApplicant.CreditCategory.Poor;
	//        }
	//    }
	//
	//
	//    if (coApplicantLongestRunningCreditLine <= 12
	//        || (coApplicantBankruptcy == true && coApplicantMonthsSinceBankruptcy <= 12)
	//        || (coApplicantOrderlyDebtPayment == true && coApplicantMonthsSinceOrderlyDebtPayment <= 12)) // Add in Bankruptcy
	//        coapplicant.applicantCreditCategory = AlgoApplicant.CreditCategory.Poor;
	//    else if ((coApplicantLongestRunningCreditLine > 12 && coApplicantLongestRunningCreditLine <= 24)
	//        || (coApplicantBankruptcy == true && coApplicantMonthsSinceBankruptcy > 12 && coApplicantMonthsSinceBankruptcy <= 36)
	//        || (coApplicantOrderlyDebtPayment == true && coApplicantMonthsSinceOrderlyDebtPayment > 12 && coApplicantMonthsSinceOrderlyDebtPayment <= 36))
	//    {
	//       if (coApplicantBeacon > potentialProduct.goodCreditBeaconSplit)
	//        {
	//            if (coApplicantActive12MonthLatePayments <= potentialProduct.goodCredit12MoLates)
	//                coapplicant.applicantCreditCategory = AlgoApplicant.CreditCategory.Good;
	//            else if (coApplicantActive12MonthLatePayments > potentialProduct.goodCredit12MoLates && coApplicantActive12MonthLatePayments <= potentialProduct.poorCredit12MoLates)
	//                coapplicant.applicantCreditCategory = AlgoApplicant.CreditCategory.Medium;
	//            else
	//                coapplicant.applicantCreditCategory = AlgoApplicant.CreditCategory.Poor;
	//        }
	//        else if (coApplicantBeacon <= potentialProduct.goodCreditBeaconSplit && coApplicantBeacon > potentialProduct.poorCreditBeaconSplit)
	//        {
	//            if (coApplicantActive12MonthLatePayments <= potentialProduct.goodCredit12MoLates)
	//                coapplicant.applicantCreditCategory = AlgoApplicant.CreditCategory.Good;
	//            else if (coApplicantActive12MonthLatePayments > potentialProduct.goodCredit12MoLates && coApplicantActive12MonthLatePayments <= potentialProduct.poorCredit12MoLates)
	//                coapplicant.applicantCreditCategory = AlgoApplicant.CreditCategory.Medium;
	//            else
	//                coapplicant.applicantCreditCategory = AlgoApplicant.CreditCategory.Poor;
	//        }
	//        else
	//        {
	//            if (coApplicantActive12MonthLatePayments == 0)
	//                coapplicant.applicantCreditCategory = AlgoApplicant.CreditCategory.Good;
	//            else if (coApplicantActive12MonthLatePayments > 0 && coApplicantActive12MonthLatePayments <= potentialProduct.goodCredit12MoLates)
	//                coapplicant.applicantCreditCategory = AlgoApplicant.CreditCategory.Medium;
	//            else
	//                coapplicant.applicantCreditCategory = AlgoApplicant.CreditCategory.Poor;
	//        }
	//    }
	//    else
	//    {
	//        if (coApplicantBeacon > potentialProduct.goodCreditBeaconSplit)
	//        {
	//            if (coApplicantActive12MonthLatePayments <= potentialProduct.goodCredit12MoLates)
	//                coapplicant.applicantCreditCategory = AlgoApplicant.CreditCategory.Good;
	//            else if (coApplicantActive12MonthLatePayments > potentialProduct.goodCredit12MoLates && coApplicantActive12MonthLatePayments <= potentialProduct.poorCredit12MoLates)
	//                coapplicant.applicantCreditCategory = AlgoApplicant.CreditCategory.Medium;
	//            else
	//                coapplicant.applicantCreditCategory = AlgoApplicant.CreditCategory.Poor;
	//        }
	//        else if (coApplicantBeacon <= potentialProduct.goodCreditBeaconSplit && coApplicantBeacon > potentialProduct.poorCreditBeaconSplit)
	//        {
	//            if (coApplicantActive12MonthLatePayments <= potentialProduct.goodCredit12MoLates)
	//                coapplicant.applicantCreditCategory = AlgoApplicant.CreditCategory.Good;
	//            else if (coApplicantActive12MonthLatePayments > potentialProduct.goodCredit12MoLates && coApplicantActive12MonthLatePayments <= potentialProduct.poorCredit12MoLates)
	//                coapplicant.applicantCreditCategory = AlgoApplicant.CreditCategory.Medium;
	//            else
	//                coapplicant.applicantCreditCategory = AlgoApplicant.CreditCategory.Poor;
	//        }
	//        else
	//        {
	//            if (coApplicantActive12MonthLatePayments == 0)
	//                coapplicant.applicantCreditCategory = AlgoApplicant.CreditCategory.Good;
	//            else if (coApplicantActive12MonthLatePayments > 0 && coApplicantActive12MonthLatePayments <= potentialProduct.goodCredit12MoLates)
	//                coapplicant.applicantCreditCategory = AlgoApplicant.CreditCategory.Medium;
	//            else
	//                coapplicant.applicantCreditCategory = AlgoApplicant.CreditCategory.Poor;
	//        }
	//    }
	//
	//    //#endregion
	//
	//
	//    // This section on summing Libilities is located here in the Match Class because different Lenders have different
	//    // underwriting rules around how they will treat liabilities for the purposes of driving a TDS and GDS Calculation.
	//    // The same it true with income.  They treat income in a variety of ways and thus the summation of income and liabilities
	//    // and mortgages cannot be in the Applicant record where it only needs to be summed once.
	//    // Having it here in the Match is is less efficient in terms of computing, but it needs to be lender (and thus product) dependent.
	//    // Sum Liabilities to get TDS Liabilities and GDS Liabilities
	//	//[Cantor: suggest to cache this in UA side for each Applicant, to save performance when the same calcuation is needed] 
	//    //#region ClientGDS
	//    double allApplicantsAnnualPropertyTaxes = 0;
	//    double allApplicantsMonthlyCondoFees = 0;
	//
	//    for (AlgoApplicant currentApplicant : clientOpportunity.applicants)
	//    {
	//        double monthlyGDSLiabilities = 0;
	//        double monthlyTDSLiabilities = 0;
	//        double totalLiabilities = 0;
	//        double totalIncome = 0;
	//        // Sum Liabilities for each Applicant
	//        for (AlgoLiability liability : currentApplicant.activeLiabilities)
	//        {
	//            // Check if Fixed or Revolving
	//            if (liability.type == AlgoLiability.LiabilityType.Revolving && liability.creditBalance > liability.payOff)
	//            {
	//                // I may need to double check to see if it is an LOC ... LOC may need different treatment ... 
	//                double calculateLiabilityPayment = liability.creditLimit * 0.03; 
	//                if (potentialProduct.revolvingDebtPayments == AlgoProduct.LiabilityCalculatonMethod.StatedMinimum)
	//                    calculateLiabilityPayment = liability.monthlyPayment;
	//                else if (potentialProduct.revolvingDebtPayments == AlgoProduct.LiabilityCalculatonMethod.ThreePercentBalance)
	//                    calculateLiabilityPayment = liability.creditBalance * 0.03;
	//                else if (potentialProduct.revolvingDebtPayments == AlgoProduct.LiabilityCalculatonMethod.ThreePercentLimit)
	//                    calculateLiabilityPayment = liability.creditLimit * 0.03;
	//                
	//                monthlyGDSLiabilities += calculateLiabilityPayment;
	//                monthlyTDSLiabilities += calculateLiabilityPayment;
	//                totalLiabilities += liability.creditBalance;
	//
	//            }
	//            else
	//            {
	//                monthlyGDSLiabilities += liability.monthlyPayment;
	//                monthlyTDSLiabilities += liability.monthlyPayment;
	//                totalLiabilities += liability.creditBalance;
	//            }
	//           
	//        }
	//        if (currentApplicant.monthlySupportPayment > 0)
	//        {
	//            if (potentialProduct.childSupportRreatment == AlgoProduct.SupportTreatment.AddToLiabilities)
	//            {
	//                monthlyGDSLiabilities += currentApplicant.monthlySupportPayment;
	//                monthlyTDSLiabilities += currentApplicant.monthlySupportPayment;
	//            }
	//        }
	//        currentApplicant.monthlyGDSLiabilities = monthlyGDSLiabilities;
	//        currentApplicant.annualTDSLiabilities = monthlyTDSLiabilities * 12;
	//
	//        // Sum Incomes for each Applicant
	//        // Sum Incomes List
	//
	//        // I need confirm these income calculaitons with Audrey and Ensure I am not missing anything
	//        // Also need to ensure all incomes in OpenERPhave already been converted to Annual ... 
	//        for (AlgoIncome currentIncome : currentApplicant.incomes)
	//        {
	//            if (currentIncome.typeOfIncome == AlgoIncome.IncomeType.Rental)
	//            {
	//                double allowableRentalIncome = currentIncome.annualIncome * (potentialProduct.rentalIncomePercentIncluded / 100);
	//                totalIncome += allowableRentalIncome;
	//            }
	//            if (currentIncome.typeOfIncome == AlgoIncome.IncomeType.SelfEmployed)
	//            {
	//                if (potentialProduct.selfEmployedTwoYrAverageNOAPlusPercent == true)
	//                {
	//                    double selfEmployedGrossUp = currentIncome.annualIncome * 1.15;
	//                    totalIncome += selfEmployedGrossUp;
	//                }
	//                else
	//                    totalIncome += currentIncome.annualIncome;
	//            }
	//            totalIncome += currentIncome.annualIncome;
	//        }
	//
	//        if (currentApplicant.monthlySupportPayment > 0)
	//        {
	//            if (potentialProduct.childSupportRreatment == AlgoProduct.SupportTreatment.SubtractFromIncome)
	//            {
	//                totalIncome -= currentApplicant.monthlySupportPayment * 12;
	//            }
	//        }
	//        currentApplicant.totalOtherIncome = totalIncome;
	//      
	//    }
	//
	//    // Sum GDS for all applicants and Property
	//    double totalGDSPayments = 0;
	//    double totalIncomes = 0;
	//    double ownedPropertyCount = 0;
	//    for (AlgoApplicant currentApplicant : clientOpportunity.applicants)
	//    {
	//        totalGDSPayments += currentApplicant.monthlyGDSLiabilities;
	//        allApplicantsAnnualPropertyTaxes += currentApplicant.totalAnnualPropertyTaxes;
	//        allApplicantsMonthlyCondoFees += currentApplicant.totalMonthlyCondoFees;
	//        totalIncomes += currentApplicant.totalOtherIncome;
	//        ownedPropertyCount += currentApplicant.numberOfOwnedProperties;
	//    }
	//
	//    // Calculate GDS for Opportunity.
	//    totalGDSPayments = totalGDSPayments + (allApplicantsAnnualPropertyTaxes / 12) + allApplicantsMonthlyCondoFees + (ownedPropertyCount *85); // 85 is average monthly heating cost / Property ... 
	//    gDSRatio = totalGDSPayments / (totalIncomes / 12);
	//
	//    //#endregion
	//
	//    // Sum TDS for all Applicants and Property
	//
	//    // Before this Can be Done ... Strategy and Payment needs to be Calculated ... 
	//
	//
	//
	//    // We also need to add in any child / spousal support being paid.
	//    // In incomes calculation, need to add in any spousal or child support being received. 
	//
	//    // When summing Liabilities for TDSR & GDSR, Secured LOC may not be 3% of Balance ... they might be interest only 
	//    // Confirm with Audrey 

    }




    
    // 2) Determine Type of Algo
    // - Pre-Qualify
    // - Purchase
    // - Refinance
    // 3) Determine Strategy (Pull Sample Product to determine Ratios)
    // 4) Run Suitability Algorithm 
    // 5) Pick likely products (of a couple groups) for solution (Considering Suitability, Their Desires, Strategy Math)
    // 6) Load Product List from DB based upon likely product group
    // 7) Underwrite the Product (Determine Match) & Assign White/Grey/Black for each product in the list
    // 8) If Total Greys < AllowableGrey for that product && None of the Blacks are a Total NoGo, Add MatchRecord into a list of likely Products
    // 9) Run fitness algorithm on Remaining list of Likely products
    // 10) Sort List
    // 11) Select Lender

    // Second Run Deal through Sample Poduct to get payments
    // Second thing - Based Upon the Opportunity.ClientCreditCategory what following TDS and GDS Rules will apply.
    // How does co-applicant fit into the match? 
    // 
    // How does guarantor fit in? 
    
    // How do Assets affect the Underwriting Formula?  They simply need to exceed the liabilities.  Audrey and Kendall have
    // Spoken of "strengthening the app" but there seems to be no formula for doing so except with non-income qualification
    
    
    
    // First Thing, do an analysis on the Credit Info and Categorize each person as Good, Medium, Poor Credit. 
    // Second thing - look at Beacon to determine what following TDS and GDS Rules will apply.


    /* 
     * Strategy ... 
     * 
     * 
     * 
     * Beacon Score Area ... 
     * The Acceptable TDS / GDS for a gien Lender/Product shifts depending on the Beacon
     * if (OpportunityBeacon < MinBeaconCutOff, deal is Dead & BlackFlag is added)
     * if (OpportunityBeacon >= MinBeaconCutOff && OpportunityBeacon <= MinBeacon, deal is Possible & GreyFlag is Added)
     * if (OpportunityBeacon > MinBeacon, deal is Possible & WhiteFlag is Added)
     * MinBeaconFlag == 
     *  
     * When there is a guarantor or Co-Applicaiton (On the income side of things, their income is simply added to the TDS and GDS Calculaitons
     * Liabilities are added also added to liabilities (check needs to made they are not doubled)
     * Assets are added to assets (check needs to made they are not doubled)
     * 
     * 
     * 
     * Product Record (Actual Fields)
        
        - Lender Name (Look up field)            �?
      
     * Related to Refinance
     * Match Record needs to determine what product or combination of products we are going to reccommend. 
     * In doing whether it is in their interest to get rid of the current first, or add a second or add a LOC
     * TO do so:
     * 1) Calculate the Interest payable for the next 3 months.  Calculate (CurrentMortageAmount * AnnualInterest / 12 * 3)
     * 2) Calculate Interest Rate Differential ... (CurrentInterestRateOnMortgage - AvailablePostedRate) * CurrentMortgageBalance / 12 * RemainingMonthsinTerm 
     * AvailablePostedRate needs to be queried for the same term Product from the same lender as their current Lender.
     * 3) The greater of the interest rate differential and the 3 Months interest is the payout penalty ... 
     * 
     * To determine the mixture of Products ... we need to Calculate COST in both Cashflow and Interest
     * Calcuate Remaining Interest on their existing term.  
     * This means running the current interest rate and payment out to the point where Balance is 0
     * As this is done, each month needs to model the interest amount and paydown amount 
     * This schedule becomes the basis for determining the current interest and cashflow cost of the mortgage
     * The current cost is the number of months from Date.Now to RenewalDate, then Sum the Interest component and cashflow component in the schedule over this number of months.
     * 
     * 
     * Check Guidelines Rules to check on "how the deal will fit"
     * Calculate TDS, GDS, LTV based upon our single "SpecialAveragedProduct" Record.
     * Calculating LTV ...
     * if(AdditionalMoney == 0 && MaximumAdditionalAmount == false)
     * LTV = (CurrentMortgageAmount + AdditionalMoney) / EstimatedPropertyValue
     * else
     * if (WhoWillBeLiving == Renter)
     * LTV = (EstimatedPropertyValue * .80) - CurrentMortgageAmount
     * else (if( WhoWillBeLiving == Owner || WhoWillBeLiving == OwnerAndRenter  || WhoWillBeLiving == OwnerAndRenter))
     * LTV = (EstimatedPropertyValue * .85) - CurrentMortgageAmount
     * 
     * Calculating GDS ... 35% (A-Lender) ... (B-Lender)
     * Credit Card Payments, Loan Payments, Heat($85), Property Taxes 
     * 
     * Calculating TDS ... 42% (Beacon >= 680) ... 40% (if Becon < 680)
     * All Debt Payments including property + Heat($85) + Property Taxes
     * (Debt payments = Summed Credit Bureau.  If a line is an R-Line, then 3% available limit)
     * 
     * Strategy Logic ..... 
     * With that all calculated now need to know what products are likely to fit ... 
     * Goes through List of Generic Solution Sets relate to "products"
     * 1) Start with LTV ... Eliminate which ones won't work.  From there, figure out which I want. 
     * 2) Then take a look at Type of Property (Size / Type / What Lenders will Allow)
     * 
     * 3) Then with that context, distill down to type of Solution 
     * 
     * We need to Maintain our "Generic Visdom Products" ... these are one of each type that are "Averages".
     * Strategy Calculation ... 
     * a) Start by Looking at what they picked in the opportunity as what they want ... E.g. LOC - Going to try to Upsell to something else if appropriate
     * Will they fit an LOC.
     * Then find out if they will fit a Variable.
     * Then find out if they will fit a fixed. 
     * a) Do they fit?
     * b) Which product provides highest amount? (Leave a buffer of 1%)
     * If one or all products fit ... Which Pays?  What is Risk tolerance? 
     * We need to select 3 products and the reasons they were selected and they all need to go to a proposal ...
     * 
     * If Fail ... 
     * 
     * 
     * 
     * 
     * a) 65% LTV on LOC will work or wont?  (Kendall will tell me about partial & position for LOC > 65%)
     * b) Conventional Variable
     * c) Conventional Fixed
     * d) High Ratio (CMHC) fixed (85% on Rental)  (95% Principle Purchase)  (85% Principle Refinance) 
     * Qualifying rate is used for TDS instead of product rate if fixed is < 5 Years
     * e) High Ratio (CMHC) variable Same LTV as fixed, but Qualifying rate is used for TDS instead of product rate.
     * f) Combinations ... Conventional 1st + Second
     * g) Combinations ... Combination Mortgate (1st + LOC with 1 charge on title) (Same rules for) Qualifying Rate.
     * h) Leave Existing and get a second.
     * i) If Self-Employed and Stated income signifiacantly different than provable ...
     * Option 1 is qualifying on a Non-Income Qualifier product
     * Option 2 is find out of they have co-applicant
     * Add their provable incomes together and qualify on a "normal" product rather than a Non-Income Product
     * 
     * 
     * If underwriting a Second, see if there is a way to "Downlist" Products where boolean SpecificLendersOn1st is true
     * 
     * 
     * No Variable Rate for Non-Income Qualifier So an non-income qualifer cannot have a variable rate product.
     * 
     * 
     * If either Applicant Has had a Bankruptcy, the Appropriate Bankruptcy rules in the Product Record must prove true, otherwise, product will not work. 
     * 
     * 
     * Once Solution Set is Picked, Then we need to run the rough solution through the 150 potential products that fit that goal ... 
     * From there, the "Best" is selected and worked with.
     * 
     * Once the actual product/Lender is Picked, then, run the Math over desired amotization & possibly longer amortization to determine schedule.
     * Once Schedule is figured out - compare numbers to see if solution saves Cashflow & / or Interest costs.
     * 
     * From there, report the required information up to OpenERP to go into Solution Tab of Opportunity including Savings info.
     * Once Verification is complete, then info is pulled from "Solution Tab" of Opportunity Record and place into the Proposal Document.
     * 
     * 
     * Approval Concept ... How Many Grey's allowed?  How Many Black's allowed?
     * What fields are Abolutle BLACK NO DEAL fields? (Multi-Select Dropdown field)
     * 
     * 
     * The Logic that lands on the most efficient way of doing it need to deliver the analysis back to OpenERP
     * to place it as text in the "Proposal".
     * 
     * 
        - Allowed1stLenders (Multi-Select Text field) - There could be 3 to 10 Company names in this field. 
          If applying for a 2nd mortgage Need to look at the Lender 

     * Product Type (This is only a Text Header, not field)
            Purchase (These are the fields under the Text Header “Product Type�?
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

     * What title position does this lender allow?(Text Header; not a field)  ( This s/b yes, no. maybe)

     * We need to ensure that the "Strategy" dictates the "Charge Position" 1st,2nd,3rd,4th,Bridge
     * Based upon the ChargePosition, we need to limit "potential Product List to only those that allow that charge Position
     * The Product Fields we will be comparing are AllowedChargeOn1st, AllowedChargeOn2nd, AllowedChargeOn3rd, AllowedChargeOnBridge
       
     * Title Insurance ... If Funding date 14 days from now, selected Lender MUST have allow title Insurance. 
     * We also want to ensure shortest Avg Funding Days work ... 
    
    * Canadian Military Personnel ... If Current Employer Name includes "Military" OR "Armed Forces", then only Products available are those where Military boolean == true
    
     * Insuring ... We want to bias against Products that insure below 80% (Conventional)
        Calculate fees based upon 
     * Branch Signing .... In Fitness (In fitness algo, we want to bias against Branch Signing Products
     * Fees ... Calculate Fees and add to deal costs for the client ... bias against fees in fitness
     * 
     * We need to check if applicant is New to Canada (Different length depending on Lender) ... we prefer products that are NOT new to Canada i.e. Applicant.ImigrationMonthsAgo < Product.NewToCanadaMonths
     * 
     * Allow Self-Employed Income - if person is self-employed ... Needs to be true
     * If Stated Income Qualifyng (used when TDS or GDS is > Max CutOffs) && Verifiable Income is < What they put on original App
     * We need Separate Income Fields in Verification Tab of Opportunity.
     * 
     * Power of Attourney is available, but not used in Algo.
     * 
     * Need to Calculate Number of Properties Client has with Single Lender ... Try to find a way to fuzzy match Lender Names
     * Once we know how many properites client has with each lender, we need to ensure we are not trying to place mortgage with 
     * A lender where the total Proprty Count with that lender exceeds the AllowedMaxProperties
     * 
     * 
     * 
     */





}
