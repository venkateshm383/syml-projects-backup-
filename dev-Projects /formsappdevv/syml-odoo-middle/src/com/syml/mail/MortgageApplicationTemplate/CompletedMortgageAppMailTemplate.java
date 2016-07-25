package com.syml.mail.MortgageApplicationTemplate;

import java.util.HashSet;
import java.util.Properties;

import com.syml.constants.SymlConstant;
import com.syml.helper.GenericHelperClass;
import com.syml.mail.SendEmail;

public class CompletedMortgageAppMailTemplate {
	
	public static void CompletedMortgageAppMailTemplateMethod(int id,String module,String opprunityName,String applicantOneFirstName,String applicantOneLastName){
		
		String message=" Hello ,      <br><br><br>   " +
				" 			There is a new Mortgage Application for " + applicantOneFirstName+ " " + applicantOneLastName + ". Please review the new Opportunity and change stage to credit in the event Personal Information (Birthday, Social Insurance Number, Address) is complete.<br><br>"+
		

		"Sincerely, <br><br>"+

		"Visdom Mortgage Solutions Inc.<br>"+
		 "broker@visdom.ca<br>"+
		 "www.visdom.ca<br><br><br>"; 
		
		
		

		GenericHelperClass genericHelperClass=new GenericHelperClass();
		Properties prop=genericHelperClass.readEmailConfigfile();
		HashSet recipients=new HashSet();
		//recipients.add(prop.getProperty("supportEmail"));
		genericHelperClass.createNote(id, message, module, "Completed Application for "+opprunityName, "");
		message+=	new SymlConstant().getMessage();
		SendEmail.sendEmailWithButton("Completed Application for "+opprunityName,message,prop.getProperty("supportEmail"), recipients);
		
	}
	
	

}
