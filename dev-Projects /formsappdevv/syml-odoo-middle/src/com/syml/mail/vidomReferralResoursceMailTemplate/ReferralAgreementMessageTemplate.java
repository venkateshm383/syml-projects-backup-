package com.syml.mail.vidomReferralResoursceMailTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Properties;

import com.syml.constants.SymlConstant;
import com.syml.helper.GenericHelperClass;
import com.syml.mail.SendEmail;

public class ReferralAgreementMessageTemplate {
	


	public static void ReferralAgreementMessage(String name,String email,String signature,int referralId,String modulle,String file){
		
		String message="";
		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar=Calendar.getInstance();
		String date=(dateFormat.format(calendar.getTime()));
		message="Hello "+name+".<br><br><br>"+
				
				
"       Welcome to the Referral Program with Visdom Mortgage Solutions. Please find below your copy of the Referral Agreement you completed in our website. In the event you feel you have receive this email in error, please reply and we will remove you from our referral program.<br><br><br>"+


"Best Regards,<br><br>"+
 "Kendall Raessler<br>"+
 "President,<br>"+
  "Visdom Mortgage Solutions<br>"+
   "administrator@visdom.ca<br><br><br>";
     
        
		
		GenericHelperClass genericHelperClass=new GenericHelperClass();
		Properties prop=genericHelperClass.readEmailConfigfile();
				HashSet recipients=new HashSet();
				recipients.add(prop.getProperty("referralnotfoundemailid"));
				genericHelperClass.createNote1(referralId, message, modulle, "Referral Agreement", email,file,"Referral Agreement");
			   message+=new SymlConstant().getMessage();
	SendEmail.sendEmailWithAttachment("Referral Agreement", message,email , recipients,file);
	}
	

}