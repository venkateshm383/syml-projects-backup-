package com.sendwithus;
import java.util.Calendar;
import java.util.GregorianCalendar;

import play.Logger;

import java.util.TimeZone;



public class SendWithUsExample {

	private static org.slf4j.Logger logger = play.Logger.underlying();
	
    public static final String SENDWITHUS_API_KEY = "live_a7c95c3b0fb3acb519463955b1a2be67b2299734";
    public static final String EMAIL_ID_WELCOME_EMAIL = "tem_su5msryYNEqXUvdV9CnNgb";
    public static final String EMAIL_ID_WELCOME_EMAIL1 = "tem_GwD5YrZnJgisMx8XPXWonT";
    public static final String EMAIL_ID_WELCOME_EMAIL2 = "tem_smDhYJa3CP3CyCPeJv5ALF";
    public static final String EMAIL_ID_WELCOME_EMAIL3 = "tem_vZkNdBwuKrmjKKBR5axGj3";
    public static final String EMAIL_ID_WELCOME_EMAIL4 = "tem_dARGRv2m4bhdBKtD5VPpmn";
    public static final String EMAIL_ID_WELCOME_EMAIL5 = "tem_e4TpkWbKTYcyKmL2tJ9MRT";
    public static final String EMAIL_ID_WELCOME_EMAIL6 = "tem_Gy5GoFyzR6zwHL7NJkwKiC";
    public static final String EMAIL_ID_WELCOME_EMAIL7 = "tem_WawpgMtMfXCSiCwXwRiW7B";
    public static final String EMAIL_ID_WELCOME_EMAIL8 = "tem_2UVVLDiFtRR3oMEBukDfWT";
    public static final String EMAIL_ID_WELCOME_EMAIL9 = "tem_89qDEGb4ewqiwbzFAK6Ckf";
    public static final String EMAIL_ID_WELCOME_EMAIL10 = "tem_xX4UhhfXpcTNNqMWqt9PeW";

    
    
    
 
   
    
    

    
    
   
    
    
   
    public String getGreeting(){
    	String greeting="";
    	Calendar time = new GregorianCalendar();  
    	TimeZone timeZone = TimeZone.getDefault();

    	TimeZone timeZone1 = TimeZone.getTimeZone("Canada/Mountain");
    					time.setTimeZone(TimeZone.getTimeZone("Canada/Mountain"));
    	
    	int hour = time.get(Calendar.HOUR_OF_DAY);  
    	int min = time.get(Calendar.MINUTE);  
    	int day = time.get(Calendar.DAY_OF_MONTH);  
    	int month = time.get(Calendar.MONTH) + 1;  
    	int year = time.get(Calendar.YEAR);  

    	logger.debug("The current time is \t" + hour + ":" + min);  
    	logger.debug("Today's date is \t" + month + "/" + day + "/"  
    	  + year);  

    	if (hour < 12){
    		greeting="Good Morning";
    	 logger.debug("Good Morning!");  
    	}else if (hour >12 && hour < 16 ){  
    		greeting="Good Afternoon";
    	 logger.debug("Good Afternoon");  
    	}else if (hour == 12)  {
    		greeting="Good Afternoon";

    	 logger.debug("Good Afternoon");  
    	 
    }else if (hour == 16)  {
		greeting="Good Evening";

	 logger.debug("Good Evening");  
	 
}
    	
    	
    	else if (hour > 16 && hour<24)  {
    		 logger.debug("Hello");  
    	}else  
    	 logger.debug("Good Evening"); 
		return greeting;
    	
    	
    	
    }

}
