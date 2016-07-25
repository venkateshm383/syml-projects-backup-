package com.syml.mail.leadMailTemplate;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;

import com.syml.constants.SymlConstant;
import com.syml.helper.GenericHelperClass;
import com.syml.mail.SendEmail;

public class ErrorInCreatingLeadMessageTemplate {
	
	public static void ErrorInCreatingLeadMessageTemplateMethod(String referrarFirstName,String lastName,String Email,String leadFirstName,String leadLastName,String leadEmailid,int id,String module){
		
		//todo guy  mail template for error in creatingLead 
		String message="Hello   "+referrarFirstName+",<br><br><br>"+

				"We have received your referral to "+leadFirstName+" "+leadLastName+" with the email address of "+leadEmailid+".<br>"+

							 "We have sent them a link to their confidential Mortgage Application and we will keep you informed of "+

							"key events throughout the financing process. <br><br>"+

							 "Unfortunately we encountered a minor challenge in either creating the lead to "+leadFirstName+" or in finding your own "+

							 "information in our Referral Source database.  We will be in touch with you shortly by email to ensure everything"+

							" is arranged correctly.<br><br>" +

							 "With Appreciation, <br><br>"+

							 "Visdom Mortgage Solutions <br><br>";
						

		GenericHelperClass genericHelperClass=new GenericHelperClass();
		Properties prop=genericHelperClass.readEmailConfigfile();
				HashSet recipients=new HashSet();
				//recipients.add(prop.getProperty("referralnotfoundemailid"));
				genericHelperClass.createNote(id, message, module, "Error in creating Lead", Email);
				message+=	new SymlConstant().getMessage();	
		SendEmail.sendEmailWithButton("Error in creating Lead", message, prop.getProperty("referralnotfoundemailid"), recipients);
	}

}
