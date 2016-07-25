package com.syml.mail.vidomReferralResoursceMailTemplate;

import java.util.HashSet;
import java.util.Properties;

import com.syml.constants.SymlConstant;
import com.syml.helper.GenericHelperClass;
import com.syml.mail.SendEmail;

public class ReferralInstructionsMessageTemplate {
	
	public static void ReferralInstructions(String name,String email,String touchDeviceType,int referralId,String module,String file){
		String message="";
		String device="";
		if(touchDeviceType.equals("iPhone")){
		device= "" +
	            "     To provide a referral, all you need to do is go to Visdom's confidential referrals webpage, provide your name and email address (so we know to credit  you with the referral) and a little bit of informaiton about the  client <br><br>" +
	            "To make this process quick and simple on your iPhone, please take a moment to do the following steps:<br><br>" +
	            "1) Using your phone, click this weblink to open the Visdom Referrals Page in Safari <a style='text-decoration: none !important;' href=\"https://biz.visdom.ca/forms/SubmitReferalform1D.html\">Visdom Referrals Form </a>.<br><br>" +
	            "2) At the bottom of the screen you'll see an icon depicting an arrow that  looks like it's trying to get away from a square. Tap this button and you'll have a few options.<br><br>" +
	            "3) Tap the \"Add to Home Screen\" button.&nbsp; You'll  be asked to choose a name for the homescreen icon.<br><br> " +
	            "4) Name the Icon Visdom Referrals.<br><br>" +
	            "Please note we have also emailed you these instructions in case you are rushed for time currently. Now that you have this set up, all you need to do to send your first referral to Visdom is tap the icon on your home screen and begin earning referral fees.The referrals you send will  receive a welcome message by email which outlines the process of securing their financing quickly and easily through Visdom. As they are working though the financing process, you will receive status emails periodically to keep you informed of each important event.<br><br>" +
	            "Once again, welcome to Visdom and we look forward to helping you increase your income.<br><br><br>";	
		}else if(touchDeviceType.equals("Neither")){
			device=""+

"     To provide a referral, all you need to do is go to Visdom's confidential referrals webpage, provide your name and email address (so we know to credit you with the referral) and a little bit of  information  about the client.<br><br>"+ 

"To make this process quick and simple on your system or tablet, please take a moment to do the following steps:<br><br>"+

"1) On your computer or tablet, click this weblink to open the Visdom Referrals Page. <a style='text-decoration: none !important;' href=\"https://biz.visdom.ca/forms/SubmitReferalform1D.html\">Visdom Referrals Form</a>.<br><br>"+

"2) Press the bookmarks or favorites icon in your  browser.<br><br>"+

"3) Name the bookmark Visdom Referrals and click OK to add it.<br><br>"+

"4) When you need to submit a referral, open your bookmarks and click the Visdom Referrals bookmark you just created.<br><br>"+

"Please note we have also emailed you these instructions in case you are rushed for time currently.  Now that you have this set up, all you need to do to send your first referral to Visdom is open your bookmarks and tap the Visdom Referrals bookmark.  The referrals you send will receive a welcome message by email which outlines the process of securing their financing quickly and easily through Visdom.  As they are working though the financing process, you will receive status emails periodically to keep you informed of each important event.<br><br>"+

"Once again, welcome to Visdom and we look forward to helping you increase your income.<br><br><br>";
		}else if(touchDeviceType.equals("Android")){
			device=""+
		"    To provide a referral, all you need to do is go to Visdom's confidential referrals webpage, provide your name and email address (so we know to credit  you with the referral) and a little bit of informaiton about the  client.<br><br>"+
					"To make this process quick and simple on your Android phone, please take a moment to do the following steps:<br><br>"+
		"1) Using your phone, click this weblink to open the Visdom Referrals Page in your phone Browser  <a style='text-decoration: none !important;' href=\"https://biz.visdom.ca/forms/SubmitReferalform1D.html\">Visdom Referrals Form</a><br><br>"+
		 "2) Press the Menu button and select Bookmarks.3) Select the top left thumbnail labeled Add.<br><br>"+
		"4) Name the bookmark Visdom Referrals and click OK to add it.<br><br>" +
		"5) Press and  hold on the Visdom Referrals bookmark you just created, and select Add shortcut to Home.  This will put the Visdom Referrals shortcut icon on your Home screen to allow  one-touch access.<br><br>"+
		"Please note we have also emailed you these instructions in case you are rushed for time currently. Now that you have this set up, all you need to do to send your first referral to Visdom is tap the icon on your home screen and begin earning referral fees. The referrals you send will  receive a welcome message by email which outlines the process of securing their financing quickly and easily through Visdom. As they are working though the financing process, you will receive status emails periodically to keep you informed of each important event.<br><br>"+
		"Once again, welcome to Visdom and we look forward to helping you increase your income.<br><br><br>";
		}
		
		message="Good day "+name+",<br><br><br>"+
		
			"      Welcome to the Visdom Mortgage Solutions Referral Program. Now that we are working as a team, it is our goal to make the process of participating as easy and simple as possible for you. To provide a referral, all you need to do is email the referrals contact information (including their email address) to referrals@visdom.ca.<br><br>"+

              ""+device+""+
            
            "Best Regards,<br><br>"+
           "Kendall Raessler<br>"+
            "President<br>"+
            "Visdom Mortgage Solutions<br>"+
            "administration@visdom.ca<br>"+
            "www.visdom.ca<br><br>";
            
        		
		GenericHelperClass genericHelperClass=new GenericHelperClass();
		Properties prop=genericHelperClass.readEmailConfigfile();
				HashSet recipients=new HashSet();
				//recipients.add(prop.getProperty("referralnotfoundemailid"));
				genericHelperClass.createNote1(referralId, message, module, "Referral Agreement", email,file,"Referral Agreement");
			message	+=new SymlConstant().getMessage();
				
	SendEmail.sendEmailWithAttachment("Referral Agreement", message,email , recipients,file);
		
		
	}

}
