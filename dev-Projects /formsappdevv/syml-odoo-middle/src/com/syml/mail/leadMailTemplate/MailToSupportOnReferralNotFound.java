package com.syml.mail.leadMailTemplate;

import java.util.HashSet;
import java.util.Properties;

import com.syml.constants.SymlConstant;
import com.syml.helper.GenericHelperClass;
import com.syml.mail.SendEmail;

public class MailToSupportOnReferralNotFound {
	
	
	public static void mailToSupportOnReferralNotFound(String leadName,String leadLastName,String toEmail, String referralEmail,String referralName){
		
		String message ="Hello ,<br><br><br>"+
		
				
	"A new Lead has been created for "+leadName+ " "+leadLastName+".  However, the search process has been unable to automatically match the information in the Referral Source Fields to existing Referral Sources in our database.  Please review the below information and edit the Lead Record to include the correct Referral Source.<br><br>"+
	"Referral Source email:  "+referralEmail+"<br>"+
"Referral Source name: "+referralName+"<br><br>"+
	"In the event you need more information you can review the submitted raw Visdom Referral Information in the notes of the Lead Record below Lead Json Data "+
				
		"Best Regards,<br><br>"+

				"Visdom Mortgage Solutions<br>"+ 
				 "Borker@visdom.ca<br><br>";
				
		GenericHelperClass genericHelperClass=new GenericHelperClass();
		Properties prop=genericHelperClass.readEmailConfigfile();
		HashSet recipients=new HashSet();
	
		//genericHelperClass.createNote(0, message, "", " Additional Information Required for Lead.name", referrermail);
		message+=	new SymlConstant().getMessage();
		SendEmail.sendEmailWithButton(" Referral Source Needs to be Located for "+leadName, message,toEmail, recipients);
		
		
	}

}
