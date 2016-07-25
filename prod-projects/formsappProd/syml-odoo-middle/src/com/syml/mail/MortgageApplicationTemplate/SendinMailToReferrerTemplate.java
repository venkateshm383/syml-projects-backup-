package com.syml.mail.MortgageApplicationTemplate;

import java.util.HashSet;
import java.util.Properties;

import com.syml.constants.SymlConstant;
import com.syml.helper.GenericHelperClass;
import com.syml.mail.SendEmail;

public class SendinMailToReferrerTemplate {
	
	
	
	public static void SendinMailToReferrerTemplateMethod(String referrerEmail,String applicantOneFirstName,String applicantOneLastName,String applicantTwoFirstName,String applicantTwoLastName, int id,String module){
		
		
		String message="Good Day   ,<br><br><br>"+
		
"We are sending this message just to let you know that we have received the mortgage application for "+ applicantOneFirstName+" "+applicantOneLastName +"  " ;
if(!applicantTwoFirstName.isEmpty()){
	message+="and "+applicantTwoFirstName +" "+applicantTwoLastName+".<br><br>";
}else{
	message+="<br><br>";
}

		message+="We are reviewing their application and will be in contact with them by email within one hour. In the event, they completed their application after midnight (EST) we will be in touch with them first thing in the morning. <br><br>"+

				
"If you have any questions kindly feel free to contact our team at Broker@visdom.ca .<br><br> "+  


				"Warmest Regards,<br><br>"+
				"Broker@visdom.ca <br>"+
					"www.visdom.ca <br><br>"; 	
		
		
		GenericHelperClass genericHelperClass=new GenericHelperClass();
		Properties prop=genericHelperClass.readEmailConfigfile();
		HashSet recipients=new HashSet();
		//recipients.add(prop.getProperty("supportEmail"));
		genericHelperClass.createNote(id, message, module, "Mortgage Application for "+applicantOneFirstName +" "+applicantOneLastName, referrerEmail);
		message+=	new SymlConstant().getMessage();
		SendEmail.sendEmailWithButton("Mortgage Application for "+applicantOneFirstName, message,referrerEmail, recipients);
		
	}

}
