package com.syml.bean.algo;

import java.util.Date;

import com.syml.domain.LatePayment;

public class AlgoLatePayment
{   
	public LatePayment latePayment;
    //public String name; // Lenders Name (Company)
    //public Date date; // The Month and Year the payment was due and missed (late)
    public enum Rating { R0, R1, R2, R3, R4, R5, R6, R7, R8, R9, I0, I1, I2, I3, I4, I5, I6, I7, I8, I9 }; // Credit Code on the Liability ... "I" means a fixed Loan (e.g. Car Loan, "R" means Reveolving e.g. Credit Card)
    //public Rating rating;
    //public int days; // Interpreted length of time the payment was late before being paid comes from Rating
   
    public AlgoLatePayment(LatePayment latePayment)
    {
    	this.latePayment = latePayment;
        // Cantor - I left the constructor for your coding depending on how you are managing the WebService Sending the Data.
        
    }
}
