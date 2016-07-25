package com.syml.bean.algo;

import com.syml.domain.InsurerProducts;

public class InsurancePremiums {
	 /*
     * LTV Ratio Total Loan Amount Top-up 
     * Portion Up to 65% 0.60% 0.60% 
     * 65.01% - 75% 0.75% 2.60% 
     * 75.01% - 80% 1.25% 3.15% 
     * 80.01% - 85% 1.80% 4.00% 
     * 85.01% - 90% 2.40% 4.90% 
     * 90.01% - 95%* 3.15% 4.90% 
     * - See more at: http://genworth.ca/en/products/homebuyer-95-program.aspx#sthash.XxSzjYDO.dpuf
     */
    public static double standardLtv65Percent = 0.60;
    public static double standardLtv75Percent = 0.75;
    public static double standardLtv80Percent = 1.25;
    public static double standardLtv85Percent = 1.80;
    public static double standardLtv90Percent = 2.40;
    public static double standardLtv95Percent = 3.6;

    public static double standardLtvTopUp65Percent = 0.60;
    public static double standardLtvTopUp75Percent = 2.60;
    public static double standardLtvTopUp80Percent = 3.15;
    public static double standardLtvTopUp85Percent = 4.0;
    public static double standardLtvTopUp90Percent = 4.90;
    public static double standardLtvTopUp95Percent = 4.90;
    
//    public static double refiIncStandardLtv65Percent = 0.50;
//    public static double refiIncStandardLtv75Percent = 2.25;
//    public static double refiIncStandardLtv80Percent = 2.75;
//    public static double refiIncStandardLtv85Percent = 3.50;
//    public static double refiIncStandardLtv90Percent = 4.25;
    
    /*
     * LTV Ratio Purchase Premium Rate / Refinance Premium Rate / Top-Up Rate 
     * Up to 65% 	0.90% 0.90% 1.75% 
     * 65.01% - 75% 1.15% 1.15% 3.00% 
     * 75.01% - 80% 1.90% 1.90% 4.45% 
     * 80.01% - 85% 3.35% N/A 	6.35% 
     * 85.01% - 90% 5.45% N/A 	8.05% - 
     * See more at: http://genworth.ca/en/products/business-for-self-program.aspx#sthash.P4YtlAPB.dpuf
     * 
     */
    public static double selfEmpNoIncLtv65Percent = 0.80;
    public static double selfEmpNoIncLtv75Percent = 1.00;
    public static double selfEmpNoIncLtv80Percent = 1.64;
    public static double selfEmpNoIncLtv85Percent = 2.90;
    public static double selfEmpNoIncLtv90Percent = 4.75;
    
    public static double selfEmpNoIncReFiLtv65Percent = 0.80;
    public static double selfEmpNoIncReFiLtv75Percent = 1.00;
    public static double selfEmpNoIncReFiLtv80Percent = 1.64;
    public static double selfEmpNoIncReFiLtv85Percent = 2.90;
    public static double selfEmpNoIncReFiLtv90Percent = 4.75;
    
    public static double selfEmpNoIncTopUpLtv65Percent = 0.80;
    public static double selfEmpNoIncTopUpLtv75Percent = 1.00;
    public static double selfEmpNoIncTopUpLtv80Percent = 1.64;
    public static double selfEmpNoIncTopUpLtv85Percent = 2.90;
    public static double selfEmpNoIncTopUpLtv90Percent = 4.75;
    
    /*
     *LTV Ratio Type A properties / Type B properties Premium Rate Top-up Rate Premium Rate Top-up 
     *Rate Up to 65% 0.60% 0.60% 1.45% 1.45% 
     *65.01% - 75% 0.75% 2.60% 1.60% 3.45% \
     *75.01% - 80% 1.25% 3.15% 2.00% 4.00% 
     *80.01% - 85% 1.80% 4.00% 2.90% 4.90% 
     *85.01% - 90% 2.40% 4.90% 3.15% 5.75% 
     *90.01% - 95% 3.15% 4.90% N/A N/A 
     *- See more at: http://genworth.ca/en/products/vacation-secondary-homes-program.aspx#sthash.O2aEkjAW.dpuf 
     */
    
    public static double vacation2ndATypeLtv65Percent = 0.60;
    public static double vacation2ndATypeLtv75Percent = 0.75;
    public static double vacation2ndATypeLtv80Percent = 1.25;
    public static double vacation2ndATypeLtv85Percent = 1.80;
    public static double vacation2ndATypeLtv90Percent = 2.40;
    public static double vacation2ndATypeLtv95Percent = 3.15;

    public static double vacation2ndATypeLtvTopUp65Percent = 0.60;
    public static double vacation2ndATypeLtvTopUp75Percent = 2.60;
    public static double vacation2ndATypeLtvTopUp80Percent = 3.15;
    public static double vacation2ndATypeLtvTopUp85Percent = 4.0;
    public static double vacation2ndATypeLtvTopUp90Percent = 4.90;
    public static double vacation2ndATypeLtvTopUp95Percent = 4.90;
    
    public static double vacation2ndBTypeLtv65Percent = 1.45;
    public static double vacation2ndBTypeLtv75Percent = 1.6;
    public static double vacation2ndBTypeLtv80Percent = 2;
    public static double vacation2ndBTypeLtv85Percent = 2.90;
    public static double vacation2ndBTypeLtv90Percent = 3.15;
    public static double vacation2ndBTypeLtv95Percent = 3.15;

    public static double vacation2ndBTypeLtvTopUp65Percent = 1.45;
    public static double vacation2ndBTypeLtvTopUp75Percent = 3.45;
    public static double vacation2ndBTypeLtvTopUp80Percent = 4;
    public static double vacation2ndBTypeLtvTopUp85Percent = 4.9;
    public static double vacation2ndBTypeLtvTopUp90Percent = 5.74;
    public static double vacation2ndBTypeLtvTopUp95Percent = 5.75;
    
    /*	Rental
     * LTV Ratio Recommended Credit Scores Premium Rate Top-Up 
     * Rate Up to 65% 640 1.45% 3.15% 
     * 65.01% - 75% 640 2.00% 3.45% 
     * 75.01% - 80% 660 2.90% 4.30% 
     * - See more at: http://genworth.ca/en/products/investment-property-program.aspx#sthash.4PZBrYkk.dpuf
     */
    public static double rentalLtv65Percent = 1.45;
    public static double rentalLtv75Percent = 2;
    public static double rentalLtv80Percent = 2.9;
    
    public static double rentalLtvTopUp65Percent = 3.15;
    public static double rentalLtvTopUp75Percent = 3.45;
    public static double rentalLtvTopUp80Percent = 4.3;
    
    // Second Mortgage
    /*
     * LTV Ratio Recommended Credit Scores Combined 1st & 2nd Loan Amounts 2nd Mortgage Amount 
     * Up to 65% 620 0.60% 0.60% 
     * 65.01% - 75% 620 0.75% 2.60% 
     * 75.01% - 80% 620 1.25% 3.15% 
     * 80.01% - 85% 620 1.80% 4.00% 
     * 85.01% - 90% 660 2.40% 4.90% 
     * 90.01% - 95% 700 3.15% 4.90% 
     * - See more at: http://genworth.ca/en/products/second-mortgage-program.aspx#sthash.Rd31Og0o.dpuf
     */
    public static double firstAndSecondLtvTopUp65Percent = 0.6;
    public static double firstAndSecondLtvTopUp75Percent = 0.75;
    public static double firstAndSecondLtvTopUp80Percent = 1.25;
    public static double firstAndSecondLtvTopUp85Percent = 1.8;
    public static double firstAndSecondLtvTopUp90Percent = 2.4;
    public static double firstAndSecondLtvTopUp95Percent = 3.6;
    
    public static double secondOnlyLtvTopUp65Percent = 0.6;
    public static double secondOnlyLtvTopUp75Percent = 2.6;
    public static double secondOnlyLtvTopUp80Percent = 3.15;
    public static double secondOnlyLtvTopUp85Percent = 4;
    public static double secondOnlyLtvTopUp90Percent = 4.9;
    public static double secondOnlyLtvTopUp95Percent = 4.9;
    
    public static double moreThan25YearAmortizeAdder = 0.25;
    
//    public static double calculateInsurancePremium(boolean reFinanceTopUp, double loanToValue, boolean standard, boolean selfEmployed, double mortgageAmount, double reFiBalance, boolean nonTradDownPay, 
//    		boolean rental, boolean vacation2ndHomeTypeA, boolean vacation2ndHomeTypeB, boolean firstAndSecond, boolean secondOnly, int amortization)
//   
    
    public static double calculateInsurancePremium(InsurerProducts insureProd)
    {
        double percentPrem = 0;
        if (insureProd.standard == true)
        {
        	double rateToUse = 0;
        	if (insureProd.reFinanceTopUp == false){
        		if (insureProd.loanToValue <= 65)
            		rateToUse = standardLtv65Percent;
                else if (insureProd.loanToValue > 65 && insureProd.loanToValue <= 75)
                	rateToUse = standardLtv75Percent;
                else if (insureProd.loanToValue > 75 && insureProd.loanToValue <= 80)
                	rateToUse = standardLtv80Percent;
                else if (insureProd.loanToValue > 80 && insureProd.loanToValue <= 85)
                	rateToUse = standardLtv85Percent;
                else if (insureProd.loanToValue > 85 && insureProd.loanToValue <= 90)
                    percentPrem = standardLtv90Percent;
                else
                	rateToUse = standardLtv95Percent;	
        	}
        	else{
        		if (insureProd.loanToValue <= 65)
            		rateToUse = standardLtvTopUp65Percent;
                else if (insureProd.loanToValue > 65 && insureProd.loanToValue <= 75)
                	rateToUse = standardLtvTopUp75Percent;
                else if (insureProd.loanToValue > 75 && insureProd.loanToValue <= 80)
                	rateToUse = standardLtvTopUp80Percent;
                else if (insureProd.loanToValue > 80 && insureProd.loanToValue <= 85)
                	rateToUse = standardLtvTopUp85Percent;
                else if (insureProd.loanToValue > 85 && insureProd.loanToValue <= 90)
                    percentPrem = standardLtvTopUp90Percent;
                else
                	rateToUse = standardLtvTopUp95Percent;	
        	}      
        	
        	if (rateToUse > percentPrem)
        		percentPrem = rateToUse;
        }
        
        if (insureProd.selfEmployed == true)
        {
        	double rateToUse = 0;
        	if (insureProd.reFinanceTopUp == false){
        		if (insureProd.loanToValue <= 65)
            		rateToUse = selfEmpNoIncLtv65Percent;
                else if (insureProd.loanToValue > 65 && insureProd.loanToValue <= 75)
                	rateToUse = selfEmpNoIncLtv75Percent;
                else if (insureProd.loanToValue > 75 && insureProd.loanToValue <= 80)
                	rateToUse = selfEmpNoIncLtv80Percent;
                else if (insureProd.loanToValue > 80 && insureProd.loanToValue <= 85)
                	rateToUse = selfEmpNoIncLtv85Percent;
                else if (insureProd.loanToValue > 85 && insureProd.loanToValue <= 90)
                    percentPrem = selfEmpNoIncLtv90Percent;
                else
                	rateToUse = selfEmpNoIncLtv90Percent;	
        	}
        	else{
        		if (insureProd.loanToValue <= 65)
            		rateToUse = selfEmpNoIncReFiLtv65Percent;
                else if (insureProd.loanToValue > 65 && insureProd.loanToValue <= 75)
                	rateToUse = selfEmpNoIncReFiLtv75Percent;
                else if (insureProd.loanToValue > 75 && insureProd.loanToValue <= 80)
                	rateToUse = selfEmpNoIncReFiLtv80Percent;
                else if (insureProd.loanToValue > 80 && insureProd.loanToValue <= 85)
                	rateToUse = selfEmpNoIncReFiLtv85Percent;
                else if (insureProd.loanToValue > 85 && insureProd.loanToValue <= 90)
                    percentPrem = selfEmpNoIncReFiLtv90Percent;
                else
                	rateToUse = selfEmpNoIncReFiLtv90Percent;	
        	}      
        	
        	if (rateToUse > percentPrem)
        		percentPrem = rateToUse;
        }
        
        if (insureProd.rental == true)
        {
        	double rateToUse = 0;
        	if (insureProd.reFinanceTopUp == false){
        		if (insureProd.loanToValue <= 65)
            		rateToUse = rentalLtv65Percent;
                else if (insureProd.loanToValue > 65 && insureProd.loanToValue <= 75)
                	rateToUse = rentalLtv75Percent;
                else if (insureProd.loanToValue > 75 && insureProd.loanToValue <= 80)
                	rateToUse = rentalLtv80Percent;
                else 
                	rateToUse = rentalLtv80Percent;
        	}
        	else{
        		if (insureProd.loanToValue <= 65)
            		rateToUse = rentalLtvTopUp65Percent;
                else if (insureProd.loanToValue > 65 && insureProd.loanToValue <= 75)
                	rateToUse = rentalLtvTopUp75Percent;
                else if (insureProd.loanToValue > 75 && insureProd.loanToValue <= 80)
                	rateToUse = rentalLtvTopUp80Percent;
                else if (insureProd.loanToValue > 80 && insureProd.loanToValue <= 85)
                	rateToUse = rentalLtvTopUp80Percent;
                else 
                	rateToUse = rentalLtvTopUp80Percent;
        	}      
        	
        	if (rateToUse > percentPrem)
        		percentPrem = rateToUse;
        }
        
        if (insureProd.vacation2ndHomeTypeA == true)
        {
        	double rateToUse = 0;
        	if (insureProd.reFinanceTopUp == false){
        		if (insureProd.loanToValue <= 65)
            		rateToUse = vacation2ndATypeLtv65Percent;
                else if (insureProd.loanToValue > 65 && insureProd.loanToValue <= 75)
                	rateToUse = vacation2ndATypeLtv75Percent;
                else if (insureProd.loanToValue > 75 && insureProd.loanToValue <= 80)
                	rateToUse = vacation2ndATypeLtv80Percent;
                else if (insureProd.loanToValue > 80 && insureProd.loanToValue <= 85)
                	rateToUse = vacation2ndATypeLtv85Percent;
                else if (insureProd.loanToValue > 85 && insureProd.loanToValue <= 90)
                    percentPrem = vacation2ndATypeLtv90Percent;
                else
                	rateToUse = vacation2ndATypeLtv95Percent;	
        	}
        	else{
        		if (insureProd.loanToValue <= 65)
            		rateToUse = vacation2ndATypeLtvTopUp65Percent;
                else if (insureProd.loanToValue > 65 && insureProd.loanToValue <= 75)
                	rateToUse = vacation2ndATypeLtvTopUp75Percent;
                else if (insureProd.loanToValue > 75 && insureProd.loanToValue <= 80)
                	rateToUse = vacation2ndATypeLtvTopUp80Percent;
                else if (insureProd.loanToValue > 80 && insureProd.loanToValue <= 85)
                	rateToUse = vacation2ndATypeLtvTopUp85Percent;
                else if (insureProd.loanToValue > 85 && insureProd.loanToValue <= 90)
                    percentPrem = vacation2ndATypeLtvTopUp90Percent;
                else
                	rateToUse = vacation2ndATypeLtvTopUp95Percent;	
        	}      
        	
        	if (rateToUse > percentPrem)
        		percentPrem = rateToUse;
        }
        
        if (insureProd.vacation2ndHomeTypeB == true)
        {
        	double rateToUse = 0;
        	if (insureProd.reFinanceTopUp == false){
        		if (insureProd.loanToValue <= 65)
            		rateToUse = vacation2ndBTypeLtv65Percent;
                else if (insureProd.loanToValue > 65 && insureProd.loanToValue <= 75)
                	rateToUse = vacation2ndBTypeLtv75Percent;
                else if (insureProd.loanToValue > 75 && insureProd.loanToValue <= 80)
                	rateToUse = vacation2ndBTypeLtv80Percent;
                else if (insureProd.loanToValue > 80 && insureProd.loanToValue <= 85)
                	rateToUse = vacation2ndBTypeLtv85Percent;
                else if (insureProd.loanToValue > 85 && insureProd.loanToValue <= 90)
                    percentPrem = vacation2ndBTypeLtv90Percent;
                else
                	rateToUse = vacation2ndBTypeLtv95Percent;	
        	}
        	else{
        		if (insureProd.loanToValue <= 65)
            		rateToUse = vacation2ndBTypeLtvTopUp65Percent;
                else if (insureProd.loanToValue > 65 && insureProd.loanToValue <= 75)
                	rateToUse = vacation2ndBTypeLtvTopUp75Percent;
                else if (insureProd.loanToValue > 75 && insureProd.loanToValue <= 80)
                	rateToUse = vacation2ndBTypeLtvTopUp80Percent;
                else if (insureProd.loanToValue > 80 && insureProd.loanToValue <= 85)
                	rateToUse = vacation2ndBTypeLtvTopUp85Percent;
                else if (insureProd.loanToValue > 85 && insureProd.loanToValue <= 90)
                    percentPrem = vacation2ndBTypeLtvTopUp90Percent;
                else
                	rateToUse = vacation2ndBTypeLtvTopUp95Percent;	
        	}      
        	
        	if (rateToUse > percentPrem)
        		percentPrem = rateToUse;
        }
        
        if (insureProd.firstAndSecond == true)
        {
        	double rateToUse = 0;
        	if (insureProd.reFinanceTopUp == false){
        		if (insureProd.loanToValue <= 65)
            		rateToUse = firstAndSecondLtvTopUp65Percent;
                else if (insureProd.loanToValue > 65 && insureProd.loanToValue <= 75)
                	rateToUse = firstAndSecondLtvTopUp75Percent;
                else if (insureProd.loanToValue > 75 && insureProd.loanToValue <= 80)
                	rateToUse = firstAndSecondLtvTopUp80Percent;
                else if (insureProd.loanToValue > 80 && insureProd.loanToValue <= 85)
                	rateToUse = firstAndSecondLtvTopUp85Percent;
                else if (insureProd.loanToValue > 85 && insureProd.loanToValue <= 90)
                    percentPrem = firstAndSecondLtvTopUp90Percent;
                else
                	rateToUse = firstAndSecondLtvTopUp95Percent;	
        	}   
        	
        	if (rateToUse > percentPrem)
        		percentPrem = rateToUse;
        }
        
        if (insureProd.secondOnly == true)
        {
        	double rateToUse = 0;
        	if (insureProd.reFinanceTopUp == false){
        		if (insureProd.loanToValue <= 65)
            		rateToUse = secondOnlyLtvTopUp65Percent;
                else if (insureProd.loanToValue > 65 && insureProd.loanToValue <= 75)
                	rateToUse = secondOnlyLtvTopUp75Percent;
                else if (insureProd.loanToValue > 75 && insureProd.loanToValue <= 80)
                	rateToUse = secondOnlyLtvTopUp80Percent;
                else if (insureProd.loanToValue > 80 && insureProd.loanToValue <= 85)
                	rateToUse = secondOnlyLtvTopUp85Percent;
                else if (insureProd.loanToValue > 85 && insureProd.loanToValue <= 90)
                    percentPrem = secondOnlyLtvTopUp90Percent;
                else
                	rateToUse = secondOnlyLtvTopUp95Percent;	
        	}   
        	
        	if (rateToUse > percentPrem)
        		percentPrem = rateToUse;
        }

        double premium = 0;

        if (insureProd.reFinanceTopUp == false)
            premium = (percentPrem / (double)100) * insureProd.mortgageAmount;
        else
            premium = (percentPrem / (double)100) * insureProd.reFiBalance; // Needs to be confirmed with Kendall / Audrey

        return premium;
    }
}
