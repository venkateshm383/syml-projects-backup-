package com.syml.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import com.syml.bean.algo.AlgoNote;
import com.syml.bean.algo.AlgoOpportunity;
import com.syml.bean.algo.AlgoProduct;
import com.syml.bean.algo.UnderwriteAll;
import com.syml.bean.algo.AlgoNote.Priority;
import com.syml.bean.algo.AlgoNote.TypeOfNote;
import com.syml.bean.algo.AlgoProduct.ProductTerm;
import com.syml.bean.algo.UnderwritingBase;
import com.syml.domain.Lender;
import com.syml.domain.Opportunity;
import com.syml.domain.Product;
import com.syml.util.SelectionHelper;

public class FinalVerifyAlgorithm {
    public Opportunity clientOpportunity;
    public Product potentialProduct;
    
    public AlgoOpportunity algoClientOpportunity;
    public AlgoProduct algoProduct;
    
    public Boolean NonIncomeQualifer;
    public int HighRatioLTVPercentage;
    public List<AlgoNote> AssistantTasks;
    public List<AlgoNote> BrokerTasks;
    public List<AlgoNote> DealNotes;
    public List<AlgoNote> LenderNotes;

    public FinalVerifyAlgorithm(AlgoOpportunity algoClientOpportunity, AlgoProduct algoPotentialProduct, Session session)
    {
    	this.algoClientOpportunity = algoClientOpportunity;
    	this.algoProduct = algoPotentialProduct;
        clientOpportunity = algoClientOpportunity.opp;
        potentialProduct = algoPotentialProduct.product;
        AssistantTasks = new ArrayList<AlgoNote>();
        BrokerTasks = new ArrayList<AlgoNote>();
        DealNotes = new ArrayList<AlgoNote>();
        LenderNotes = new ArrayList<AlgoNote>();
                 
        // Underwrite to get final notes for Lender, Assistant and Broker & Ensure Rules all are fulfilled
        UnderwriteAll qualifying = new UnderwriteAll(algoClientOpportunity, algoProduct, session);
        // Ensure UnderwritingType is set so that the right lists of notes are created and transferred as the deal is underwritten.
        qualifying.underwritingType = UnderwritingBase.TypeOfUnderwriting.FinalVerify;
        qualifying.checkUnderwritingRules();
        qualifying.calculateCashback();
        qualifying.CalculateOneTimeFees();
        qualifying.CalculateCommissions();
            
        algoProduct.qualifiesForOpportunity = qualifying.opportunityQualifies;
        algoProduct.fixedCost = qualifying.fixedCost;
        algoProduct.variableCost = qualifying.variableCost;
        algoProduct.LOCCost = qualifying.LOCCost;
        algoProduct.cashbackAmount = qualifying.cashbackAmount;
        algoProduct.payoutAmount = qualifying.payoutAmount;
        algoProduct.paymentOptions = qualifying.paymentOptions;
        algoProduct.baseCompAmount = qualifying.baseCompAmount;
        algoProduct.bonusCompAmount = qualifying.volumeBonusCompAmount;
        algoProduct.marketingCompAmount = qualifying.marketingCompAmount;
        algoProduct.trailerCompAmount = qualifying.trailerCompAmount;
        algoProduct.totalCompAmount = qualifying.totalCompAmount;
        algoProduct.branchAttend = qualifying.branchAttend;
        // Double check and ensure all calculated Properties have been set.

        if (qualifying.opportunityQualifies == true)
        {
            AlgoNote DealNote1 = new AlgoNote(AlgoNote.TypeOfNote.Info, AlgoNote.Priority.High, "All Underwriting Criteria for " + potentialProduct.name + " are confirmed.  Please see Broker and Assistant Tasks for final items prior to Sending to Lender");
            DealNotes.add(DealNote1);
            // Write this note back into OpenERP into the List of Deal Notes related to this Opportunity
        }
        else
        {
            AlgoNote DealNote1 = new AlgoNote(AlgoNote.TypeOfNote.Info, AlgoNote.Priority.High, "The Selected Product: " + potentialProduct.name + " Does not presently fit for this Opportunity.  Please review Deal Notes for reasons.");
            DealNotes.add(DealNote1);
            // Write this note back into OpenERP into the List of Deal Notes related to this Opportunity
        }
        
        //#region Communicate PreQualification Data Back to OpenERP
        // There are 5 things that need to be Communicated back to OpenERP: 
        // The list of Deal Notes notes from the qualifying object created with the selected Product.
        // The list of Assistant notes from the qualifying object created with the selected Product.
        // The list of Broker notes from the qualifying object created with the selected Product.
        // The list of Lender notes from the qualifying object created with the selected Product.
        // All the fields in the "Final Solution" Tab of the Opportunity Record in OpenERP (Compenation, etc.)
        // See Below for notes. 

        for (AlgoNote note : qualifying.dealNotes)
        {
        	this.DealNotes.add(note);
            // Write each note back into OpenERP into the List of Deal Notes related to this Opportunity
        }
        for (AlgoNote note : qualifying.assistantNotes)
        {
        	this.AssistantTasks.add(note);
            // Write each note back into OpenERP into the List of Assistant Tasks related to this Opportunity
        }
        for (AlgoNote note : qualifying.brokerNotes)
        {
        	this.BrokerTasks.add(note);
            // Write each note back into OpenERP into the List of Broker Tasks related to this Opportunity
        }
        for (AlgoNote note : qualifying.lenderNotes)
        {
        	this.LenderNotes.add(note);
            // Write each note back into OpenERP into the List of Lender Notes related to this Opportunity
        }

        // We also need to write back into the OpenERP Opportunity information Related to the "Final Solution"
        // See Below for Mapping of the information we need to write back. 
        // For the sake of ease in writing notes, I created a dummy Opportunity Object Below Called OpenERPOpportunityRecord
        // This was simply to represent the actual Opportunty Record residing in the OpenERP Database for the purposes of creating notes of what needs to happen.

     // There are fields in the Opprotunity Record that now need to be set...
        // We need to pass back to OpenERP the data that goes into the final Verify Tab of the Opportunty Record.
        // For the sake of illutrating the fields, I have used the clientOpportunity object 
        // in plave of the actual Json object that would in reality be passed, as it is the Opportunity in OpenERP that 
        // the data needs to be written to.

        clientOpportunity.amortization = clientOpportunity.amortization;
        clientOpportunity.cashBack = (int)(qualifying.cashbackAmount);
        clientOpportunity.downpaymentAmount = clientOpportunity.downpaymentAmount;
        clientOpportunity.insurerfee = qualifying.insuranceAmount;
        Lender lender = new Lender(potentialProduct.lender);
        clientOpportunity.lenderName = lender.name;
        clientOpportunity.monthlyPayment = qualifying.algoPotentialProduct.expectedPayment;
        
        clientOpportunity.mortgageType = potentialProduct.mortgageType;
        clientOpportunity.openClosed = potentialProduct.openClosed; 
        clientOpportunity.rate = potentialProduct.interestRate;
        clientOpportunity.term = potentialProduct.term;
        clientOpportunity.totalMortgageAmount = clientOpportunity.totalMortgageAmount;

        // Compensation Related items ... being clarified ... 
        clientOpportunity.trailerCompAmount = algoPotentialProduct.trailerCompAmount;
        clientOpportunity.volumeBonusAmount = qualifying.volumeBonusCompAmount;
        clientOpportunity.baseCompAmount = algoPotentialProduct.baseCompAmount;
        clientOpportunity.marketingCompAmount = algoPotentialProduct.marketingCompAmount;
        clientOpportunity.lenderFee = qualifying.lenderFee;
        clientOpportunity.brokerFee = qualifying.brokerFee;
        clientOpportunity.totalOneTimeFees = qualifying.oneTimeFees;
        clientOpportunity.totalCompAmount = algoPotentialProduct.totalCompAmount;


        if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Month6, potentialProduct.term))
            clientOpportunity.renewaldate = add(clientOpportunity.closingDate, Calendar.MONTH, 6);	//clientOpportunity.ClosingDate.AddMonths(6);
        else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year1, potentialProduct.term))
            clientOpportunity.renewaldate = add(clientOpportunity.closingDate, Calendar.YEAR, 1);	//ClientOpportunity.closingDate.AddYears(1);
        else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year2, potentialProduct.term))
            clientOpportunity.renewaldate = add(clientOpportunity.closingDate, Calendar.YEAR, 2);	//ClientOpportunity.closingDate.AddYears(2);
        else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year3, potentialProduct.term))
            clientOpportunity.renewaldate = add(clientOpportunity.closingDate, Calendar.YEAR, 3);	//ClientOpportunity.closingDate.AddYears(3);
        else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year4, potentialProduct.term))
            clientOpportunity.renewaldate = add(clientOpportunity.closingDate, Calendar.YEAR, 4);	//ClientOpportunity.closingDate.AddYears(4);
        else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year5, potentialProduct.term))
            clientOpportunity.renewaldate = add(clientOpportunity.closingDate, Calendar.YEAR, 5);	//ClientOpportunity.closingDate.AddYears(5);
        else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year6, potentialProduct.term))
            clientOpportunity.renewaldate = add(clientOpportunity.closingDate, Calendar.YEAR, 6);	//ClientOpportunity.closingDate.AddYears(6);
        else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year7, potentialProduct.term))
            clientOpportunity.renewaldate = add(clientOpportunity.closingDate, Calendar.YEAR, 7);	//ClientOpportunity.closingDate.AddYears(7);
        else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year8, potentialProduct.term))
            clientOpportunity.renewaldate = add(clientOpportunity.closingDate, Calendar.YEAR, 8);	//ClientOpportunity.closingDate.AddYears(8);
        else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year9, potentialProduct.term))
            clientOpportunity.renewaldate = add(clientOpportunity.closingDate, Calendar.YEAR, 9);	//ClientOpportunity.closingDate.AddYears(9);
        else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.Year10, potentialProduct.term))
            clientOpportunity.renewaldate = add(clientOpportunity.closingDate, Calendar.YEAR, 10);	//ClientOpportunity.closingDate.AddYears(10);
        else if (SelectionHelper.compareSelection(AlgoProduct.ProductTerm.none, potentialProduct.term))
            clientOpportunity.renewaldate = add(clientOpportunity.closingDate, Calendar.YEAR, clientOpportunity.amortization.intValue());	//ClientOpportunity.closingDate.AddYears((int)ClientOpportunity.amortization);



        //#endregion
    }
    
    private Date add(Date date, int calendarField, int number){
    	Date d = (Date) date.clone();
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(d);
    	cal.add(calendarField, number);
    	return cal.getTime();
    }
    
}
