package com.syml.mail.leadMailTemplate;

import java.util.HashSet;
import java.util.Properties;

import com.syml.constants.SymlConstant;
import com.syml.helper.GenericHelperClass;
import com.syml.mail.SendEmail;

public class LeadChangingToPendingApplicationMessageTemplate {

	
	public static void LeadChangingToPendingApplicationMessageTemplateMethod(String referrarFirstName,String lastName,String Email,String leadFirstName,String leadLastName,String leadEmailid,String leadPhoneNumber,int id,String module){
		
		
		//todo Guy give email template for trigring mail after lead changed to Pennding Application
		String message="Hello Team,<br><br>" +
				
				
				"There is a new referral from " + referrarFirstName+" "+lastName+" . "+

				 "They have sent us a referral to "+leadFirstName  + " " +leadLastName + ". <br>"+ 

			 "Please contact " + leadFirstName + " at " + "insert phone " + " to welcome them to Visdom, explain the process "+

				 "and answer any questions they many have. <br><br>"+

			"With Appreciation,<br><br>"+

			 "Visdom Mortgage Solutions<br><br>";
		
				
				
				
		GenericHelperClass genericHelperClass=new GenericHelperClass();
		Properties prop=genericHelperClass.readEmailConfigfile();
		HashSet recipients=new HashSet();
	
		genericHelperClass.createNote(id, message, module, "Changing Lead Stage to PendingApllicaton", Email);
		message+=	new SymlConstant().getMessage();
		SendEmail.sendEmailWithButton("Changing Lead Stage to PendingApllicaton", message,prop.getProperty("onsucessCreateLead"), recipients);
		
	}
}
