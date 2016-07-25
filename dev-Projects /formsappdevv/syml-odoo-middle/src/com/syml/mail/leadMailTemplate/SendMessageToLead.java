package com.syml.mail.leadMailTemplate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Properties;

import com.syml.constants.SymlConstant;
import com.syml.helper.GenericHelperClass;
import com.syml.mail.SendEmail;

public class SendMessageToLead {
	public static void main(String[] args) {
	SendMessageToLeadMethod("ravi","kumar","venkatesh.m@bizruntime.com", "venkatesh", "m", "venkatesh.m@bizruntime.com", 0, "");
	}
	
	public static void  SendMessageToLeadMethod(String referrerName,String referrarLastName,String referrerEmail,String leadFirstName,String leadLastName,String leadEmail,int id,String module){

		
		
		
		// String message="";
		 SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//get current date time with Calendar()
		   Calendar cal = Calendar.getInstance();
		   cal.add(Calendar.DATE, 14);
		  String currentDateTime=(dateFormat.format(cal.getTime()));
		 
			String message="Hello "+leadFirstName+", <br><br><br>"+
					"We are contacting you because "+referrerName+ " "+referrarLastName+" let us know you are exploring "+
				"mortgage options. Finding the best financing can be a time and effort consuming "+
				"challenge because there are thousands of different mortgage products available "+
				"in Canada.  Many people go to a bank, however this effectively limits their "+
				"\"shopping options\" to only that banks mortgages.  Others try a Mortgage Broker, "+
				"but again, the options are often limited to the 2-4 lenders the broker knows well "+
				"(even though they have access to more).<br><br>"+
				"Alternatively, at Visdom Mortgage Solutions, your Mortgage Application is "+
				"underwritten by our Proprietary Mortgage Platform across 100s to 1000s of "+
				"potential mortgage products.  The result is a simple, clean, understandable "+
				"proposal for the customers which contains only the best mortgages in Canada "+
				"which suit the your goals of reducing cost, managing risk and providing flexibility. "+
				"This process helps you decrease costs of borrowing and better achieve the lifestyle "+
				"and financial goals.<br><br>"+

				" In order to begin the process, please <b>click</b> the link below.  It will take you to a SECURE protected Mortgage Application which will only take 5-10 minutes to complete.</b><br><br>"+

				"<a style='text-decoration: none !important; display:block;padding: 5px 0px 0px 0px; background-color: #0000FF; width: 150px; height:38px; margin-left: 50px; color: #FFF;font-size: 13px; font-weight: bold;text-align: center; border-radius: 5px 5px; background-repeat: repeat no-repeat; 'href=\"https://dev-formsapp.visdom.ca/formsapp/mortgageForm?crmLeadId="+id+"&referrerEmail="+referrerEmail+"\">Mortgage Application Form</a><br><br>"+



				"Your application access credentials will be valid until: "+currentDateTime+".<br><br><br>" +

					

				"Thank you for choosing Visdom! If you have any questions, please do not hesitate to contact our broker team.<br><br>"+

				"Touch Dial: <b>1-855-699-2400</b>; <b>201</b><br>"+
				"Manual Dial:<b> 1-855-699-2400 extension: 201</b><br><br>"+

				"Best Regards,<br><br>"+

				"Visdom Mortgage Solutions<br>"+
				"broker@visdom.ca<br><br>";
					
					
					String subjectLine = "";
					if (referrerName != "")
						subjectLine = " Your Visdom Mortgage Application";
					else
						subjectLine = "Your Next Steps with Visdom Mortgage Solutions";



	GenericHelperClass genericHelperClass=new GenericHelperClass();
	Properties prop=genericHelperClass.readEmailConfigfile();
			HashSet recipients=new HashSet();
			//recipients.add(prop.getProperty("onsucessCreateLead"));
			genericHelperClass.createNote(id, message, module, subjectLine, leadEmail);
			message+=		new SymlConstant().getMessage();
SendEmail.sendEmailWithButton(subjectLine,message, leadEmail, recipients);
	
	}

}