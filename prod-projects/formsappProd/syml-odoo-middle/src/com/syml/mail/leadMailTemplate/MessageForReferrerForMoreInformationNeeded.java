package com.syml.mail.leadMailTemplate;

import java.util.HashSet;
import java.util.Properties;

import com.syml.constants.SymlConstant;
import com.syml.helper.GenericHelperClass;
import com.syml.mail.SendEmail;


public class MessageForReferrerForMoreInformationNeeded {
	
	
	public static void MessageForReferrer(String referrermail, String leadname){
		
		String message="Good Day , <br><br><br>"+
				
				"Thank you very much for your referral.  We are needing a bit more information on your referral.  Please kindly provide us the with the following details. <br><br>"+
				".home email address<br><br>"+
				"Again, thank you for the referral.  Please feel free to contact our team at  Assistent@visdom.ca or by phone (see below) if you have ay questions.<br><br><br>"+ 
				"Touch Dial:  1-855-699-2400; 201<br>"+
				"Manual Dial:  1-855-699-2400  extension:201<br><br>"+

				"Best Regards,<br><br>"+

				"Visdom Mortgage Solutions<br>"+ 
				 "Borker@visdom.ca<br><br>";
				
		GenericHelperClass genericHelperClass=new GenericHelperClass();
		Properties prop=genericHelperClass.readEmailConfigfile();
		HashSet recipients=new HashSet();
	
		genericHelperClass.createNote(0, message, "", " Additional Information Required for Lead.name", referrermail);
		message+=	new SymlConstant().getMessage();
		SendEmail.sendEmailWithButton(" Additional Information Required for "+leadname, message,referrermail, recipients);
		
		
	}

}
