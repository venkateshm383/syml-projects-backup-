package com.syml.bean.algo;

import java.util.Date;

import com.syml.domain.Liability;

public class AlgoLiability
{
	public Liability liability;
    // Party is presently missing in OpenERP ... 
	public enum Party { A, B, C, I, J, M, S, T }; // J is Joint, I is Individual.  It comes as only letters from Credit Report
	//public Party client;
    
    //public String business; // Lenders Name (Company)
    public enum LiabilityStatus { PaidAndClosed, paid, closed, transfer_sold_paid, lost_stolen, refinanced, transfer_sold,  Active, Inactive, written_off, paidnclosed }; // Helpful in determining whether liability is still active - Most inactive Liabilities have a status.  Many Active ones don't.  Need error handling on the translation of Info from OpenERP to This object - do not presently know the range of options for this field.
    //public LiabilityStatus status;
    public enum LiabilityType { LineOfCredit, Installment, Mortgage, OpenAccount, Revolving, NoPortfolio }; // Need to determine if the Worweb DD are required or simply Fixed / Revolving
    //public LiabilityType type;
    //public double creditLimit; // Total Amount of Credit Available on Liability including the portion presently used (Balance)
    //public double creditBalance; // Current Outstanding amount of Credit
    public double payment; // Payment Amount (Might be weekly, Bi-Weekly, Monthly, etc)
    public enum PaymentFrequency { Weekly, BiWeekly, Monthly }; //Payment needs to be made into Monthly - may need error handler to deal with a message that is something different - unlikely - but wise
    public PaymentFrequency frequency;
    //public double monthlyPayment; // All payments converted to Monthly for GDS Calc.  Payments Converted to Annual for GDS
    //public Date opened; // The Month and Year a given liability was opened - One possible factor in determining whether liability is still active
    //public Date reported; // The Month and Year a given liability was last reported - One possible factor in determining whether liability is still active
    //public Date dla; // The Month and Year a given liability last had a payment - One possible factor in determining whether liability is still active
    public enum Rating { R0, R1, R2, R3, R4, R5, R6, R7, R8, R9, I0, I1, I2, I3, I4, I5, I6, I7, I8, I9 }; // Credit Code on the Liability ... "I" means a fixed Loan (e.g. Car Loan, "R" means Reveolving e.g. Credit Card)
    //public Rating rating; 
    //public double payOff; // Used in determining if this Liability will be eliminated in order to make TDSR work within Lender Limits
    public enum LiabilityAction { Retain, Eliminate }; // Decision whether this debt will be consolidated into Mortgage
    public int ageOfLiability; // What is the timespan in Months since this liability was opened? 
    public boolean active; // Is the Liability still Active?
    /*
        Rating Codes: 
        R00--Too new to rate; approved but not used;
        R1--Pays (or paid) within 30 days of payment due date or not over one payment past due
        R2--Pays (or paid) in more than 30 days from payment due date, but not more than 60 days, or not more than two payments past due
        R3--Pays (or paid) in more than 60 days from payment due date, but not more than 90 days, or not more than three payments past due
        R4--Pays (or paid) in more than 90 days from payment due date, but not more than 120 days, or four payments past due
        R5--Account is at least 120 days overdue, but is not yet rated "9"
        R6--This rating does not exist.
        R7--Making regular payments through a special arrangement to settle your debts (i.e., credit counseling)
        R8--Repossession (voluntary or involuntary return of merchandise)
        R9--Bad debt; placed for collection; moved without giving a new address or bankruptcy.
     */


    public AlgoLiability(Liability liability)
    {
    	this.liability = liability;
        // Need to set properties in constructor first ... 
        // Cantor - I left the constructor for your coding depending on how you are managing the WebService Sending the Data.
//        TimeSpan Age = DateTime.Now - opened;
//        AgeOfLiability = (int)Math.Ceiling(Age.Days / (double)30);
        
    	long age = (new Date().getTime() - liability.opened.getTime())/( 24 * 60 * 60 * 1000 );
    	ageOfLiability =(int)Math.ceil((double)(age/30)); //(int)Math.Round((double)(LateAgo.Days / 30), 0);
        
        // Calculate how long ago the was the DateLastReported (In Months)
    	long timeFromReported = (new Date().getTime() - liability.reported.getTime())/( 24 * 60 * 60 * 1000 );
    	double monthsAgoReported = Math.round(timeFromReported / (double)30);
    	
        // Determine whether the Liability is Still Active (simplify the Liability Status from Credit Bureau to either Inactive OR Active).
        // Audrey to confirm potential Status to me.
        if (liability.status.equalsIgnoreCase(AlgoLiability.LiabilityStatus.written_off.name())
            || liability.status.equalsIgnoreCase(AlgoLiability.LiabilityStatus.Inactive.name())
            || liability.status.equalsIgnoreCase(AlgoLiability.LiabilityStatus.closed.name())
            || liability.status.equalsIgnoreCase(AlgoLiability.LiabilityStatus.lost_stolen.name())
            || liability.status.equalsIgnoreCase(AlgoLiability.LiabilityStatus.paid.name())
            || liability.status.equalsIgnoreCase(AlgoLiability.LiabilityStatus.PaidAndClosed.name())
            || liability.status.equalsIgnoreCase(AlgoLiability.LiabilityStatus.refinanced.name())
            || liability.status.equalsIgnoreCase(AlgoLiability.LiabilityStatus.transfer_sold.name())
            || liability.status.equalsIgnoreCase(AlgoLiability.LiabilityStatus.transfer_sold_paid.name())
            || liability.status.equalsIgnoreCase(AlgoLiability.LiabilityStatus.paidnclosed.name())
            || monthsAgoReported >= 5) 
        {
        	liability.status = LiabilityStatus.Inactive.name();
        }
        else
        {
        	liability.status = LiabilityStatus.Active.name();
        }

        // Set Type of Liability
        if (liability.type != null){
        	if (liability.type.toLowerCase().trim().contains("loc")){
        		liability.type = LiabilityType.LineOfCredit.name();
        	}
        	else if (liability.rating.toString().contains("R"))
                liability.type = LiabilityType.Revolving.name();
            else
            	liability.type = LiabilityType.Installment.name();
        }
        else{
        	if (liability.rating.toString().contains("R"))
                liability.type = LiabilityType.Revolving.name();
            else
            	liability.type = LiabilityType.Installment.name();	
        }
        
        // Set Monthly Payment
        if (frequency == PaymentFrequency.Weekly)
            liability.monthlyPayment = payment * 52 / 12+"";
        else if (frequency == PaymentFrequency.BiWeekly)
        	liability.monthlyPayment = payment * 26 / 12+"";
        else if (frequency == PaymentFrequency.Monthly)
        	liability.monthlyPayment = payment+"";
        else
        	liability.monthlyPayment = payment+"";
    
    
    
    
    }





}
