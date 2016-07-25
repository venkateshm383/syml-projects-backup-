package com.syml.mail.leadMailTemplate;

import java.util.HashSet;
import java.util.Properties;

import com.syml.constants.SymlConstant;
import com.syml.helper.GenericHelperClass;
import com.syml.mail.SendEmail;

public class LeadAlreadyExsistMessageTemplate {
	
	public static void LeadAlreadyExsistMessageTemplateMthod(String referrarFirstName,String Email,String leadFirstName,String leadLastName,String leadEmailid,String message1,int id,String module){
		String message ="";
		//tode guy mail template for lead already exsist
		message =	"Hello   "+referrarFirstName+",<br><br><br>"+

				"We have received your referral to "+leadFirstName+" "+leadLastName+" with the email address of "+leadEmailid+".<br>"+

							 "Unfortunately we encountered a minor challenge in creating the lead to "+leadFirstName+" because it previously exists in "+

								 "our systen.  We want to ensure eveything is correct though so "+

								 "we will be in touch with you shortly by email to ensure everything is arranged properly.<br><br>" +

								"With Appreciation, <br><br>"+

							 "Visdom Mortgage Solutions <br><br>";
				

		GenericHelperClass genericHelperClass=new GenericHelperClass();
		Properties prop=genericHelperClass.readEmailConfigfile();
				HashSet recipients=new HashSet();
				//recipients.add(prop.getProperty("referralnotfoundemailid"));
				genericHelperClass.createNote(id, message, module, "Lead Already Exsist", Email);
				message +=new SymlConstant().getMessage();
	SendEmail.sendEmailWithButton("Lead Already Exsist", message,Email , recipients);
	}

}
