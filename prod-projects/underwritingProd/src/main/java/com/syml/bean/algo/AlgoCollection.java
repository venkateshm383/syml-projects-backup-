package com.syml.bean.algo;

import java.util.Date;

import com.syml.domain.Collection;

public class AlgoCollection
{       
	public Collection collection;
    //public String name; // To what company is the collection owed?
    //public double amount; // How much was the original collection amount?
    //public double balance; // How much is the current collection amount? 
    //public Date date; // When was the collection Registered with the Credit Agency? 
    //public double payOff; // Used in determining if this Liability will be eliminated in order to make TDSR work within Lender Limits
    
    // Presently Missing in OpenERP
    public enum CollectionStatus { PaidAndClosed, Paid, Closed, TransferSoldPaid, LostStolen, Refinanced, TransferSold, Active, Inactive, WrittenOff }; // Is it paid or still outstanding?  Guy to Check with Audrey and Kendall how this is best determined.
    public CollectionStatus statusOfCollection;

    public AlgoCollection(Collection collection)
    {
    	this.collection = collection;
        // Cantor - I left the constructor for your coding depending on how you are managing the WebService Sending the Data.
    }


    //A Collection is a liability that has been sent to a legal process for collection
    // Outstanding collections need to be paid before a deal will be accepted by the lender
    // Paid collecitons do not matter.
    // Sometimes collections might also show in the Applicant.Liabilities, however, possibly not.
    // It might be that a dentist sent an unpaid bill to collections, but the unpaid bill does not show in liabilities



}
