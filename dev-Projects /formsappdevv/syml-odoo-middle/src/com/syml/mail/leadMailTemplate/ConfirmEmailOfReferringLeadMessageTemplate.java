package com.syml.mail.leadMailTemplate;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;

import com.syml.constants.SymlConstant;
import com.syml.helper.GenericHelperClass;
import com.syml.mail.SendEmail;

public class ConfirmEmailOfReferringLeadMessageTemplate {
	
	
	public static void ConfirmEmailOfReferringLeadMessageTemplateMethod(String referralName,String referralEmail,String leadFirstName,String leadLastname,String leadEmail,String message,int id,String module){
		
		//todo guy email template for confirmtion mail
		
		String message1="";
				
				
		message1 =	"Good Day    "+referralName+" ,<br><br><br>"+

"On behalf of Visdom Mortgage Solutions, we would like to THANK YOU very much for the referral to "+leadFirstName +" "+ leadLastname+". We have sent "+leadFirstName+" a link to continue our process through our online web application. We will advise you when we have received their application.<br><br>"+

			"In the event we do not get an application within 24 hours, we will follow up with "+leadFirstName+" via a phone call. If for some reason this timing should be different please reply to this message with an approximate time to contact  "+leadFirstName+" .<br><br>"+ 

"Again, Thank you very much for the referral and we look forward to assisting your clients.<br><br>"+

					"Best Regards,<br><br>"+


					

								 "Visdom Mortgage Solutions <br><br>";
				
				
				GenericHelperClass genericHelperClass=new GenericHelperClass();
				Properties prop=genericHelperClass.readEmailConfigfile();
				HashSet recipients=new HashSet();
			//	recipients.add(prop.getProperty("onsucessCreateLead"));
				genericHelperClass.createNote(id, message1, module,"We have received your referral for "+leadFirstName , referralEmail);
				message1+=	new SymlConstant().getMessage();
				SendEmail.sendEmailWithButton("We have received your referral for "+leadFirstName, message1, referralEmail, recipients);
		
	}

}
