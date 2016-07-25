package com.syml.bean.algo;

import java.util.Date;

import com.syml.domain.Mortgage;

public class AlgoMortgage
{        
	public Mortgage mortgage;
    //public Guid ApplicantID; // May not be required depending on WebServices Design, but I put it here so we can ensure that the Right List of Liabilites ends up with the Right Applicant
    // Lender was deleted. 
    //public String name; // Name of Company / Lender the mortgage is with
    //public double interestRate;  // The rate of interest on the mortgage - Needed for Caclulating Payout penalty
    //public double balance;  // The Amount of Money current outstanding on the Mortgage - Needed for Caclulating Payout penalty
    //public double monthlyPayment; // The amount of money paid monthly.  Weekly or Bi-Weekly or Semi-Monthly Payment are converted to Monthly.  - Needed for Caclulating Payout penalty & TDS
    public enum MortgageType { Open, Closed };
    //public MortgageType type;
    //public Date renewal; // The Date the present Term is completed - Needed for Caclulating Payout penalty
    //public double monthlyRent;  // Technically not part of Mortgage, but some lenders will reduce the liability rather than adding to income ... 
    //public double payOff; // Used in determining if this Liability will be eliminated in order to make TDSR work within Lender Limits Or if "Renewal / Refianance"
    //public boolean selling; // If Property is being sold as part of transaction, true and Mortgage is not included in TDS
    //public int propertyId; // ID in the Application that ties the mortgage to a Property for Assistant and Broker Reference
    
    // Need to confirm whether we should include term ... I a suspecting we do not need to, but 
    // We might need a payment completion where I run out the Payment Schedule and calculate LoanCompletionDate if it is needed in UNderwriting ... It may only need to be run if completion is likely in 12 months ...
    public boolean propertyTaxesIncluded; // The property tax amount for the property related to this mortgage is included in the monthly paymment amount.
    
    public AlgoMortgage (Mortgage mortgage)
    {
    	this.mortgage = mortgage;
        // Cantor - I left the constructor for your coding depending on how you are managing the WebService Sending the Data.
        
        // Calculate the Monthly Payment in the event property Taxes are not included... 

    }
}