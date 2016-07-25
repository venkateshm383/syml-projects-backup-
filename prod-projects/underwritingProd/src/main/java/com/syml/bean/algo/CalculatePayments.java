package com.syml.bean.algo;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CalculatePayments
{

    public static double calculateMortgagePayment(double InterestRate, double AmountOfMortgage, int AmortizationYears, AlgoOpportunity.FrequencyDesired Frequency)
    {
        int numberOfPayment = 0;
        if (Frequency.equals(AlgoOpportunity.FrequencyDesired.Monthly))
            numberOfPayment = 12;
        else if (Frequency.equals(AlgoOpportunity.FrequencyDesired.SemiMonthly))
            numberOfPayment = 24;
        else if (Frequency.equals(AlgoOpportunity.FrequencyDesired.BiWeekly))
            numberOfPayment = 26;
        else if (Frequency.equals(AlgoOpportunity.FrequencyDesired.Weekly))
            numberOfPayment = 52;

        double semiAnnualInterest = (InterestRate / (double)100 / (double)2); // By law, in Canada, all Mortgages are Calculates on Semi-Annual Interest
        double powerSemiAnnualInterest = Math.pow((double)1 + semiAnnualInterest, 2);
        double PowerTwelvethSemiAnnual = Math.pow(powerSemiAnnualInterest, (double)1 / (double)numberOfPayment);//Math.round((double)1 / NumberOfPayment, 15)  // Monthly ... Convert to Based on Period
        double monthlyInterestRate = PowerTwelvethSemiAnnual - (double)1;
        int AmortizationPayments = AmortizationYears * numberOfPayment;
        double Payment = (AmountOfMortgage * monthlyInterestRate) / (1 - Math.pow(((double)1 + monthlyInterestRate), (AmortizationPayments * (double)-1)));
        double RoundedPayment = Math.round(Payment*100)/100.0d; //d = Math.round(Payment*100)/100.0d;
        return RoundedPayment;
    }

    public static double calculateMonthlyLOCPayment(double InterestRate, double AmountOfMortgage)
    {
        double semiAnnualInterestLOC = (InterestRate / 100 / 2); // By law, in Canada, all Mortgages are Calculates on Semi-Annual Interest
        double powerSemiAnnualInterestLOC = Math.pow(1 + semiAnnualInterestLOC, 2);
        double PowerTwelvethSemiAnnualLOC = Math.pow(powerSemiAnnualInterestLOC, (double)1 / (double)12); //round((double)1 / 12, 15) // Monthly only
        double monthlyInterestRateLOC = PowerTwelvethSemiAnnualLOC - (double)1;
        double paymentLOC = AmountOfMortgage * monthlyInterestRateLOC;
        paymentLOC = Math.round(paymentLOC*100)/100.0d;
        return paymentLOC;
    }

    public static double calculateMaximumMortgageAmount(double interestRate, int amortization, double monthlyPayment)
    {
        int maxAmortizationMonths = amortization * 12;
        double semiAnnualInterest = (interestRate / 100 / 2);
        double powerSemiAnnualInterest = Math.pow(1 + semiAnnualInterest, 2);
        double PowerTwelvethSemiAnnual = Math.pow(powerSemiAnnualInterest, (double)1 / 12); //round((double)1 / 12, 15) // Monthly ... Convert to Based on Period
        double monthlyInterestRate = PowerTwelvethSemiAnnual - 1;
       
        double availableMortgageAmount = monthlyPayment * (Math.pow((1 + monthlyInterestRate), maxAmortizationMonths) - 1) / (monthlyInterestRate * Math.pow((1 + monthlyInterestRate), maxAmortizationMonths));
        double availableMortgageAmount99 = availableMortgageAmount * 0.99; // Remove 1% of total Available amount to account for legal fees, and misc costs.
        availableMortgageAmount99 = Math.round(availableMortgageAmount99);
        return availableMortgageAmount99;
    }
    
    public static Integer differenceInMonths(Date beginningDate, Date endingDate) {
        if (beginningDate == null || endingDate == null) {
            return 0;
        }
        Calendar cal1 = new GregorianCalendar();
        cal1.setTime(beginningDate);
        Calendar cal2 = new GregorianCalendar();
        cal2.setTime(endingDate);
        return differenceInMonths(cal1, cal2);
    }

    private static Integer differenceInMonths(Calendar beginningDate, Calendar endingDate) {
        if (beginningDate == null || endingDate == null) {
            return 0;
        }
        int m1 = beginningDate.get(Calendar.YEAR) * 12 + beginningDate.get(Calendar.MONTH);
        int m2 = endingDate.get(Calendar.YEAR) * 12 + endingDate.get(Calendar.MONTH);
        return m2 - m1;
    }
    
}

