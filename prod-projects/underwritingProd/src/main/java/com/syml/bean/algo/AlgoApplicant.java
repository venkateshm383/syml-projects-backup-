package com.syml.bean.algo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.syml.bean.algo.AlgoAsset.AssetType;
import com.syml.bean.algo.AlgoLiability.LiabilityStatus;
import com.syml.domain.Applicant;
import com.syml.domain.Asset;
import com.syml.domain.Collection;
import com.syml.domain.Income;
import com.syml.domain.LatePayment;
import com.syml.domain.Liability;
import com.syml.domain.Property;

public class AlgoApplicant
{
	public Applicant app;
    // Personal Info
    public enum RelationshipStatus { Single, Married, CommonLaw, Divorced, Separated }
    //public RelationshipStatus relationshipStatus;
    
    //public String name;
    //public boolean nonResident; // Applicant is a non-Resident  // Was NonResidentBorrower       
    ////public Guid ContactID;  // Used for reference in OpenERP to tie Contact, Lead, Opportunty // Was ContactID
    ////public Guid OpportunityID; // May not be required depending on WebServices Design, but I put it here so we can ensure that the Right List of Applicants end up with the Right Opportunity // Was OpportunityID
    
    // Credit & Liabilities Tab
    //public int beacon9Score; // Credit Score from Equifax Beacon 9  // Was Beacon9Score
    //public int beacon5Score; // Credit Score from Equifax Beacon 4 /5  // Was Beacon5Score
    //public int CRP3Score; // Credit Score from Equifax CRP 2  // Was CRP3Score
    //public int ERS2Score; // Credit Score from Equifax ERS 3   // Was ERS2Score
    //public int beaconScore; // Used as overall Creditworthiness.  Highest BeaconScore should be primary applicant // Was BeaconScore
    //public boolean bankruptcy; // Used in Selection of Product // Was Bankruptcy
    //public Date bankruptcyDischargeDate; // Used in Selection of Product // Was BankruptcyDischargeDate
    //public double monthlySupportPayment; // Amount of child support and spousal support Applicant is paying each month // Was MonthlySupportPayments
    //public int totalInquires; // Used in overall CreditWorthiness  // Was TotalInquiries
    //public boolean orderlyDebtPayment; // Used in Selection of Product // Was OrderlyPaymentOfDebts
    //public Date odpDischargeDate; // Used in Selection of Product // Was OrderlyPaymentOfDebtsDischargeDate        
    
    public int monthsFromBankruptcy; // How long ago was Bankruptcy Discharged? // Was MonthsFromBankruptcy
    public int monthsFromOrderlyPaymentOfDebts; // How long ago was Bankruptcy Discharged? // Was MonthsFromOrderlyPaymentOfDebts
    
    //TODO this field no references?
    public List<AlgoMortgage> mortgages; // Used in Calculation of Total Debt Service Ratio & Net Worth // Was <
    //public List<AlgoLiability> liabilities; // Used in Calculation of Total Debt Service Ratio & Net Worth // Was <
    //public List<AlgoCollection> collections; // Used in Excpetions Calculations // Was <
    //public List<AlgoLatePayment> latePayments; // Used in Credit Worthiness and Product Selection  // Was <
    
    public List<AlgoLiability> activeLiabilities; // Extra list of Liabilities for summing, etc.  // Was <
    //public List<AlgoProperty> ownedProperties; // Used to determine Assets, Costs, TDS, GDS.  // Was <
    
    public int late_payments; // Was ApplicantActive12MonthLatePayments
    
    //public double totalNetWorth;  // Was NetWorth
    //public double totalAsset; // Was TotalAssets
    //public List<AlgoAsset> assets; // Used in Net Worth Calculation // Was 

    //public double monthlychildsupport; // Amount of Child / Spousal Support Received by Applicant // Was MonthlySupportIncome
    //public double totalEmployedIncome; // Was AnnualEmployIncome
    //public double totalSelfEmployed; // Was AnnualSelfEmployIncome
    //public double totalRentalIncome; // Was AnnualRentalIncome
    //public double totalOtherIncome; // Was AnnualUseableIncome
    //public double totalIncome; // Was TotalIncomes
    
    // Calculated Sums that are used Later
    public double monthlyGDSLiabilities;
    public double annualTDSLiabilities; // Was double
    public double totalLiabilities; // Was TotalLiabilities
    public double monthlyMortgagePayments; // Was MonthlyMortgagePayments        
    public double liquidAssests; // Needed for certain lending product in order to Underwrite RSPs, Investments // Was LiquidAssests
    public double totalMortgagesBalance; // Total Amount of OutStanding Mortgage Balance in the list of Mortgages where prperty is not being sold // Was TotalMortgagesBalance
    public double outstandingCollections; // Total amount of unpaid Collections outstanding // Was OutstandingCollections
    public double totalAnnualPropertyTaxes; // Amount of Property Taxes from all Proeprties they own // Was TotalAnnualPropertyTaxes
    public double totalMonthlyCondoFees; // Amount of Condo Fees from all Proeprties they own // Was TotalMonthlyCondoFees
    public int numberOfOwnedProperties; // Number of Properties they own. // Was NumberOfOwnedProperties

    // Miscelaneous Calculated Fields
    //public boolean includeInOpportunity; // Potentially Changed by Assistant if Applicant doesn't help the deal ...  // Was IncludeInOpportunity
    public int monthsSinceImmigration; // Check to see if null, if not, calculate months. // Was MonthsSinceImmigration
    //public Date immigrationDate; // Date Immigrated to Canada    // Was ImmigrationDate
    public int employmentDurationMonths; // Number of Months in Most recent employment // Was EmploymentDurationMonths
    public int applicantLongestRunningCreditLine; // Was ApplicantLongestRunningCreditLine        
    //public List<AlgoIncome> incomes; // Used in Calculation of Total Debt Service Ratio & Net Worth // Was Incomes
    public enum CreditCategory { Good, Medium, Poor }; // Was CreditCategory
    public CreditCategory applicantCreditCategory; // Was ApplicantCreditCategory
    

   
    public AlgoApplicant(Applicant app)
    {
    	this.app = app;
        // Cantor - I left the most of constructor for your coding depending on how you are managing the WebService Sending the Data.
        // Items I included in Constructor are the caclulations needed elsewhere in the application

        activeLiabilities = new ArrayList<AlgoLiability>();

        // If there was a Bankruptcy or OrderlyPaymentOfDebts, Calculate How Long Ago it was ... 
        if (app.bankruptcy == true && app.bankruptcyDischargeDate != null)
        {
        	long daysSinceDischarge = (new Date().getTime() - app.bankruptcyDischargeDate.getTime())/( 24 * 60 * 60 * 1000 );
        	monthsFromBankruptcy =(int)Math.round((double)(daysSinceDischarge/30)); //(int)Math.Round((double)(LateAgo.Days / 30), 0);

//            TimeSpan DaysSinceDischarge = DateTime.Now - bankruptcy_discharge_date;
//            MonthsFromBankruptcy = (int)Math.ceil((double)DaysSinceDischarge.Days / 30);

        }
        if (app.orderlyDebtPayment == true && app.odpDischargeDate != null)
        {
        	long daysSinceDischarge = (new Date().getTime() - app.odpDischargeDate.getTime())/( 24 * 60 * 60 * 1000 );
        	monthsFromOrderlyPaymentOfDebts =(int)Math.round((double)(daysSinceDischarge/30)); //(int)Math.Round((double)(LateAgo.Days / 30), 0);

//            TimeSpan DaysSinceDischarge = DateTime.Now - odp_discharge_date;
//            MonthsFromOrderlyPaymentOfDebts = (int)Math.ceil((double)DaysSinceDischarge.Days / 30);
        }

        // Create the list of Active Liabilties - The status of each liability has been set already in the Constructor of the Liability
        if(app.liabilities != null)
        {
        	for (Liability liability : app.liabilities)
            {
            	//System.out.println("liability.status:" + liability.status);
                if (liability.status != null && liability.status.trim().length() > 0 && LiabilityStatus.valueOf(liability.status).compareTo(AlgoLiability.LiabilityStatus.Active)==0){
                	AlgoLiability lia = new AlgoLiability(liability);
                	activeLiabilities.add(lia);
                }
            }
        }
        

        // Sum Collections
        outstandingCollections = 0;
        if(app.collection != null)
        {
        	for (Collection currentCollection : app.collection)  // was collections
            {
                if (currentCollection.balance != 0)
                {
                	outstandingCollections += currentCollection.balance;
                }
            }
        }
        

        // Sum Assets
        liquidAssests = 0;
        app.totalAsset = 0;
        for (Asset currentAsset : app.assets)
        {
        	//System.out.println("currentAsset.assetType:" + currentAsset.assetType);
        	if(currentAsset.assetType == null)
        		 continue;
        	AssetType astType = AssetType.valueOf(currentAsset.assetType.replace("-", ""));
            if (astType.compareTo(AlgoAsset.AssetType.Gift)==0 ||
            		astType.compareTo(AlgoAsset.AssetType.RRSPs)==0 ||
            				astType.compareTo(AlgoAsset.AssetType.Savings)==0 ||
            						astType.compareTo(AlgoAsset.AssetType.StocksOrBondsOrMutuals)==0)
            	liquidAssests += currentAsset.value;

            app.totalAsset += currentAsset.value.intValue();
        }

        numberOfOwnedProperties = 0;
        // Sum Properties
        for (Property currentProperty : app.properties)
        {
            if (currentProperty.selling == false)
            { 
            	totalAnnualPropertyTaxes += currentProperty.annualTax;
            	totalMonthlyCondoFees = currentProperty.moCondoFees;
            	app.totalAsset += currentProperty.value.intValue();
            	numberOfOwnedProperties++;
            }
            else
            {
            	app.totalAsset += (currentProperty.value.intValue() - currentProperty.owed);
            }
        }

        late_payments = 0;
        // Need to Count Number of Late payments in last 12 and 24 Months
        for (LatePayment latePayment : app.latePayments)
        {
//            TimeSpanDate = (DateTime.Now - latePayment.date);
//            int MonthsLateAgo =(int)Math.round((double)(LateAgo.Days / 30)); //(int)Math.Round((double)(LateAgo.Days / 30), 0);
            
            long lateAgo = (new Date().getTime() - latePayment.date.getTime())/( 24 * 60 * 60 * 1000 );
            int monthsLateAgo =(int)Math.round((double)(lateAgo / 30)); //(int)Math.Round((double)(LateAgo.Days / 30), 0);
            
            if (monthsLateAgo <= 12)
                late_payments++;
        }

        // Determine Credit Category of Applicant ... 
        applicantLongestRunningCreditLine = 0;
        for (AlgoLiability liability : activeLiabilities)
        {
            if (liability.ageOfLiability > applicantLongestRunningCreditLine)
                applicantLongestRunningCreditLine = liability.ageOfLiability;
        }

        // Check to see if Immigration is in last 5 years and calculate Immigration Months Ago
        if (app.immigrationDate != null)
        {
        	long immigrationAgo = (new Date().getTime() - app.immigrationDate.getTime())/( 24 * 60 * 60 * 1000 );
        	monthsSinceImmigration =(int)Math.round((double)(immigrationAgo/30)); //(int)Math.Round((double)(LateAgo.Days / 30), 0);
//            TimeSpan ImmigrationAgo = (DateTime.Now - ImmigrationDate);
//            MonthsSinceImmigration = (int)Math.Round((double)(ImmigrationAgo.Days / 30), 0);
            
        }

        // Set Current Employment Duration
        List<Income> incomes = new ArrayList<Income>(app.incomes);
        if(incomes != null && incomes.size() > 0)
        	employmentDurationMonths = incomes.get(0).month;

    }
}
