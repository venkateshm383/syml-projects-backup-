package com.syml.mail.MortgageApplicationTemplate;

import java.util.HashSet;
import java.util.Properties;

import com.syml.constants.SymlConstant;
import com.syml.helper.GenericHelperClass;
import com.syml.mail.SendEmail;

public class ThankyouMailTemplateforApplicant {
	
	
	public static void  ThankyouMailTemplateforApplicantMethod(String applicantOneEmail,String applicantName, String applicantTwoEmail, String applicantThreeEmail ,int id,int id2,String module,String file){
		
		
		
		String message="Good Day  "+applicantName +" ,<br><br><br>"+
		
 


"When you submitted your mortgage application with us, we indicated we would provide you a copy of the credit consent form which you read.  " +
"This email contains your copy of that consent. Canadian Regulatory Bodies also require Mortgage Brokerages to communicate some additional information to you. "+ 
"This information includes: the specific nature of our relationship to you, our service expectations, and compensation information. Please refer to the disclosures below. " +
"If you have any questions, please do not hesitate to contact our broker team.<br><br>"+
				
				

		"Touch Dial:  1-855-699-2400; 201<br>"+
		"Manual Dial:  1-855-699-2400  extension:201<br><br>"+

		"Sincerely, <br><br>"+

		"Visdom Mortgage Solutions Inc.<br>"+
		 "broker@visdom.ca<br>"+
		 "www.visdom.ca<br><br><br>"; 
		
		GenericHelperClass genericHelperClass=new GenericHelperClass();
		Properties prop=genericHelperClass.readEmailConfigfile();
		HashSet recipients=new HashSet();
		//recipients.add(prop.getProperty("onsucessCreateLead"));

	
		//recipients.add(applicantThreeEmail);
		genericHelperClass.createNote1(id, message, module, "Your copy of Mortgage Brokerage Disclosures", applicantOneEmail,file,"ApplicantAgreement");
		if(!applicantTwoEmail.isEmpty()){
			recipients.add(applicantTwoEmail);
			genericHelperClass.createNote1(id2, message, module, "Your copy of Mortgage Brokerage Disclosures", applicantTwoEmail,file,"ApplicantAgreement");

		}
		message+=	new SymlConstant().getMessage();
		SendEmail.sendEmailWithAttachment("Your copy of Mortgage Brokerage Disclosures", message,applicantOneEmail, recipients,file);
		

	}

}
