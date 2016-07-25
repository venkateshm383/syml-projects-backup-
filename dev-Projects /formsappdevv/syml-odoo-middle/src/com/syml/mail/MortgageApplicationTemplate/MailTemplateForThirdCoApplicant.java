package com.syml.mail.MortgageApplicationTemplate;

import java.util.HashSet;
import java.util.Properties;

import com.syml.constants.SymlConstant;
import com.syml.helper.GenericHelperClass;
import com.syml.mail.SendEmail;

public class MailTemplateForThirdCoApplicant {
	
	 public static void  MailTemplateForThirdCoApplicantMethod(String applicantOneEmail,String applicantName, String coApplicantname,String coApplicantEmail ,String coApplicantTwoEmail ,int id,String module){
		
		
		
		String message="Hello ,<br><br><br>"+
		
 


"The Applicant "+applicantName+"  with email "+applicantOneEmail+", co-applicant name "+coApplicantname+ " with email "+coApplicantEmail+", and another co-applicant with email "+coApplicantTwoEmail +". For next forther process with third applicant. <br><br>"+
				
				

	

		"Sincerely, <br><br>"+

		"Visdom Mortgage Solutions Inc.<br>"+
		 "broker@visdom.ca<br>"+
		 "www.visdom.ca<br><br><br>"; 
		
		GenericHelperClass genericHelperClass=new GenericHelperClass();
		Properties prop=genericHelperClass.readEmailConfigfile();
		HashSet recipients=new HashSet();
		//recipients.add(prop.getProperty("onsucessCreateLead"));
		
	
		//recipients.add(applicantThreeEmail);
		genericHelperClass.createNote(id, message, module, "Third Applicant ", applicantOneEmail);
	
		message+=	new SymlConstant().getMessage();
		SendEmail.sendEmailWithButton("Third Applicant", message,prop.getProperty("supportEmail"), recipients);
		

	}


}
