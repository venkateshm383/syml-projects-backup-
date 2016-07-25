package com.syml.mail.leadMailTemplate;

import java.util.HashSet;
import java.util.Properties;

import com.syml.constants.SymlConstant;
import com.syml.helper.GenericHelperClass;
import com.syml.mail.SendEmail;

public class ErrorInFillingForm {
	
	
public static void ErrorInFillingFormMethod(String referralName,String referralEmail,int id,String module){
		
		//todo guy email template for errorin filling form
	
		String message="";
				
				
	message =	"Hello "+referralName+" ,<br><br>"+				

		"We appreciate the referral you are sending to Visdom.  Unfortunately, we ran into a technical hiccup while you were providing"+

		 " the information in the referral form and it seems the previous form was not completed. "+

		 "This may be because of internet quality or an interruption in the security of your browser session. " +

		 "Please close any open browser tabs you have to visdom.ca and then open this link (http://107.23.89.76:8080/syml-odoo-middle-0.0.1-SNAPSHOT/SubmitReferalform1D.html) "+

		 "in a new browser tab, to provide us with your referral information.<br><br>"+
		 "With Appreciation, <br><br>"+

	 "Visdom Mortgage Solutions <br><br>";
			
				GenericHelperClass genericHelperClass=new GenericHelperClass();
				Properties prop=genericHelperClass.readEmailConfigfile();
				HashSet recipients=new HashSet();
			
				genericHelperClass.createNote(id, message, module,"Error while filling form " , referralEmail);
				message+=new SymlConstant().getMessage();
				SendEmail.sendEmailWithButton("Error while filling form ", message, referralEmail, recipients);
		
	}

}
