package com.syml.mail;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MessageTemplate {
	
	
	
	public static String messageTemplate1(String name){
		 String message="";
		 SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//get current date time with Calendar()
		   Calendar cal = Calendar.getInstance();
		   cal.add(Calendar.DATE, 14);
		  String currentDateTime=(dateFormat.format(cal.getTime()));
		 
		 message="Hello  " +name+ "\n\n\n\n"+"We are contacting you because Guy Pallister let us know you are exploring mortgage options. Finding the best financing can be a time and effort consuming challenge because there are thousands of different mortgage products available in Canada. Many people go to a bank however, this effectively limits their \"shopping options\" to only that banks mortgages. Others try a Mortgage Broker, but again, the options are often limited to the 2-4 lenders the broker knows well (even though they have access to more).\n\n"+"Alternatively, at Visdom Mortgage Solutions, your Mortgage Application is underwritten by our Proprietary Mortgage Platform across 100s to 1000s of potential mortgage products. The result is a simple, clean, understandable proposal for the customers which contains only the best mortgages in Canada which suit the your goals of reducing cost, managing risk and providing flexibility. This process help you decrease costs of borrowing and better achieve the lifestyle and financial goals.\n\n"+

	"In order to begin the process, please click on the link below. It will take you to a secure, protected Mortgage Application which only takes most people 5 - 10 minutes to complete. Your access link is provided below.\n\n"+

	"Mortgage Application Form\n\n"+

	"Your application access credentials will be valid until:"+currentDateTime+".\n\n\n" +
		   
		

	"Thank you for choosing Visdom! If you have any questions, please do not hesitate to contact our broker team.\n\n"+

	"Touch Dial: 1-855-699-2400; 201\n"+
	"Manual Dial: 1-855-699-2400 extension: 201\n\n"+

	"Best Regards,\n\n"+

	"Visdom Mortgage Solutions\n"+
	"broker@visdom.ca\n\n"+

	"CONFIDENTIALITY: This e-mail message (including attachments, if any) is confidential and is intended only for the addressee. Any unauthorized use or disclosure is strictly prohibited. Disclosure of this e-mail to anyone other than the intended addressee does not constitute waiver of privilege. If you have received this communication in error, please notify us immediately and delete this. Thank you for your cooperation. This message has not been encrypted. Special arrangements can be made for encryption upon request. If you no longer wish to receive e-mail messages from Visdom Mortgage Solutions , please contact the sender.\n\n"+

	"Visit our website at www.visdom.ca for information about our company and the services we provide. You can subscribe to Visdom Mortgage Solutions free electronic communications, or unsubscribe at any time.\n\n";
	 
	 return message;
	 }
	
	
	
	
	public static String messageTemplate(String name,String firstname,String lastName){
		
		String message="";
		
		
		message="Hello   "+name+".\n\n\n"+
		
				
		" Welcome to  Visdom Mortgage Solutions informing you that enterd firstname \""+firstname +"\" and lastname \""+lastName +"\" of Referrer is  not  Exsist in our Contacts so kindly assit to enter referrer name manually\n\n\n"+
		
      "Best Regards,\n\n"+

	  "Visdom Mortgage Solutions\n"+
	   "broker@visdom.ca\n\n"+

      "CONFIDENTIALITY: This e-mail message (including attachments, if any) is confidential and is intended only for the addressee. Any unauthorized use or disclosure is strictly prohibited. Disclosure of this e-mail to anyone other than the intended addressee does not constitute waiver of privilege. If you have received this communication in error, please notify us immediately and delete this. Thank you for your cooperation. This message has not been encrypted. Special arrangements can be made for encryption upon request. If you no longer wish to receive e-mail messages from Visdom Mortgage Solutions , please contact the sender.\n\n"+

	  "Visit our website at www.visdom.ca for information about our company and the services we provide. You can subscribe to Visdom Mortgage Solutions free electronic communications, or unsubscribe at any time.\n\n";
	 
		
		
		
		
		
		
		return "";
	}

}
