package com.sendwithus;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TimeZone;


import com.debortoliwines.openerp.api.FilterCollection;
import com.debortoliwines.openerp.api.ObjectAdapter;
import com.debortoliwines.openerp.api.Row;
import com.debortoliwines.openerp.api.RowCollection;
import com.debortoliwines.openerp.api.Session;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sendwithus.exception.SendWithUsException;
import com.sendwithus.model.Email;
import com.sendwithus.model.SendReceipt;
import com.sendwithus.exception.SendWithUsException;
import com.syml.helper.GenericHelperClass;



public class SendWithUsExample {

	 
    public static final String SENDWITHUS_API_KEY = "live_a7c95c3b0fb3acb519463955b1a2be67b2299734";

	 public static final String EMAIL_ID_WELCOME_EMAIL11 = "tem_9Ysc8sxZ3okq2xUtFao3Df";
	    public static final String EMAIL_ID_WELCOME_EMAIL12 = "tem_Jk3fuVCDzCYSxUt7maWhmR";
	    public static final String EMAIL_ID_WELCOME_EMAIL13 = "tem_5yCEPNPCmZLbNDHvMwmU7G";
	    public static final String EMAIL_ID_WELCOME_EMAIL14 = "tem_dwZRPNjCUxNpcTxfPpV6Lb";
	    public static final String EMAIL_ID_WELCOME_EMAIL15 = "tem_KQJMr9h3jTktZsrHxT8S4d";
	    public static final String EMAIL_ID_WELCOME_EMAIL16 = "tem_n339FNZymdBE8EPdqAdZ5n";
	    public static final String EMAIL_ID_WELCOME_EMAIL17 = "tem_FmJpYPxcRymahVMEruGthn";

	    public static final String EMAIL_ID_WELCOME_EMAIL2 = "tem_smDhYJa3CP3CyCPeJv5ALF";
	    public static final String EMAIL_ID_WELCOME_EMAIL3 = "tem_MoY2qvWuunLr7FqcnfrQCa";
	    public static final String EMAIL_ID_WELCOME_EMAIL4 = "tem_jydXQYWcDBhVyvof2D83Uc";
   
	    
public static void main(String[] args) throws IOException {
	//new SendWithUsExample().sentToSupportReferralMissing("test", "venkatesh.m@bizruntime.com");
	//.sentToSupportReferralMissing("test", "Venkatesh.m@bizruntime.com");
//new SendWithUsExample().getsendWithUsApplicant_CoApplicant("227", "Test", "venkatesh.m@bizruntime.com","venkatesh.m@bizruntime.com");
//.sendTOReferralSourceIphone("tests", "http://google.com","venkatesh.m@bizruntime.com", "/home/venkateshm/Desktop/Disclosurestset_2015-07-02 12:11:17.pdf");

	//new SendWithUsExample().sendDisclosuresToclientCompletedApp("tests", "venkatesh.m@bizruntime.com", "tests", "", "venkatesh.m@bizruntime.com", "/home/venkateshm/Desktop/Nagesh.YA.doc");
//new SendWithUsExample().sendTOclientCompletedApp("tset","venkatesh.m@bizruntime.com", "tests", "venkatesh.m@bizruntime.com", "venkatesh.m@bizruntime.com");
//new SendWithUsExample().getGreeting();
System.out.println(new SendWithUsExample().getGreeting());
}
String senderEmail="support@visdom.ca";


	public void sendTOReferralSourceIphone(String referralSourceFirstName,String submitReferralPage,String EmailId, String file) throws IOException {

	        SendWithUs sendwithusAPI = new SendWithUs(SENDWITHUS_API_KEY);

	        // Print list of available emails
	        
	System.out.println("First steps Email Ok");
	// Send Welcome Email
	Map<String, Object> recipientMap = new HashMap<String, Object>();
	recipientMap.put("name", "Referral - Signup (iPhone)"); // optional
	recipientMap.put("address", EmailId);





	Map<String, Object>[] ccMap = null;
	Map<String, Object>[] 		bbMap=null;
	bbMap=(Map<String, Object>[]) new Map[1];
	Map<String, Object> ccMap1 = new HashMap<String, Object>();
	
	ccMap1.put("address", "assistant@visdom.ca");

	bbMap[0]=ccMap1;

	// sender is optional
	Map<String, Object> senderMap = new HashMap<String, Object>();
	senderMap.put("name", "Welcome to Visdom's Referral program"); // optional
	senderMap.put("address", senderEmail);
	senderMap.put("reply_to", senderEmail); // optional

		// email data in to inject in the email template
	Map<String, Object> emailDataMap = new HashMap<String, Object>();
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	SendWithUsExample sendus=new SendWithUsExample();
	Calendar cal = Calendar.getInstance();
	cal.add(Calendar.DATE, 3);
	String currentDateTime=(dateFormat.format(cal.getTime())); 
	String mesage=sendus.getGreeting();
	
	emailDataMap.put("Greeting", mesage);
	emailDataMap.put("ReferralSourceName", referralSourceFirstName);
	emailDataMap.put("SubmitReferralPage", submitReferralPage);

	
  	String [] data={file};
  	
  	
  	System.out.println("file -----------"+file+"size "+data.length);

try{
	File fl=new File(file);
	System.out.println("file exissts "+fl.exists()+" file size"+(fl.length()/1024));
}catch(Exception e){

}


	        // Example sending a simple email
	        System.out.println("Before Try");
	        try {
	        
	        	
	        	
	           SendReceipt sendReceipt = sendwithusAPI.send(EMAIL_ID_WELCOME_EMAIL11,
	                recipientMap,
	                senderMap,
	                emailDataMap,
	                ccMap,
	                bbMap,
	                data
	            );
	            System.out.println(sendReceipt);
	        } catch (SendWithUsException e) {
	        	e.printStackTrace();
	            System.out.println(e.toString());
	        }
	        
	      
	    }
	    
	    public void sendTOReferralSourceAndroid(String referralSourceFirstName,String submitReferralPage,String EmailId,String file) throws JsonProcessingException {

	        SendWithUs sendwithusAPI = new SendWithUs(SENDWITHUS_API_KEY);

	        // Print list of available emails
	        
	System.out.println("First steps Email Ok");
	// Send Welcome Email
	Map<String, Object> recipientMap = new HashMap<String, Object>();
	recipientMap.put("name", "Referral - Signup (Android)"); // optional
	recipientMap.put("address", EmailId);



	Map<String, Object>[] ccMap = null;

	Map<String, Object>[] 		bbMap=null;
	bbMap=(Map<String, Object>[]) new Map[1];
	Map<String, Object> ccMap1 = new HashMap<String, Object>();
	
	ccMap1.put("address", "assistant@visdom.ca");

	bbMap[0]=ccMap1;



	// sender is optional
	Map<String, Object> senderMap = new HashMap<String, Object>();
	senderMap.put("name", "Welcome to Visdom's Referral program"); // optional
	senderMap.put("address", senderEmail);
	senderMap.put("reply_to", senderEmail); // optional

		// email data in to inject in the email template
	Map<String, Object> emailDataMap = new HashMap<String, Object>();
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	SendWithUsExample sendus=new SendWithUsExample();
	Calendar cal = Calendar.getInstance();
	cal.add(Calendar.DATE, 3);
	String currentDateTime=(dateFormat.format(cal.getTime())); 
	String mesage=sendus.getGreeting();
	emailDataMap.put("Greeting", mesage);
	emailDataMap.put("ReferralSourceName", referralSourceFirstName);
	emailDataMap.put("SubmitReferralPage", submitReferralPage);

  	String [] data={file};
  	System.out.println("file -----------"+file+"size "+data.length);
  	try{
  		File fl=new File(file);
  		System.out.println("file exissts "+fl.exists()+" file size"+(fl.length()/1024));
  	}catch(Exception e){
  		
  	}
	        // Example sending a simple email
	        System.out.println("Before Try");
	        try {
	        
	        	
	        	
	           SendReceipt sendReceipt = sendwithusAPI.send(EMAIL_ID_WELCOME_EMAIL12,
	                recipientMap,
	                senderMap,
	                emailDataMap,
	                ccMap,
	                bbMap,
	                data
	                
	            );
	        } catch (SendWithUsException e) {
	        	e.printStackTrace();
	            System.out.println(e.toString());
	        }
	        
	      
	    }
	    
	    public void sendTOReferralSourceNiether(String referralSourceFirstName,String submitReferralPage,String EmailId,String file) throws JsonProcessingException {

	        SendWithUs sendwithusAPI = new SendWithUs(SENDWITHUS_API_KEY);

	        // Print list of available emails
	        
	System.out.println("First steps Email Ok");
	// Send Welcome Email
	Map<String, Object> recipientMap = new HashMap<String, Object>();
	recipientMap.put("address", EmailId);



	Map<String, Object>[] ccMap = null;
	Map<String, Object>[] 		bbMap=null;
	bbMap=(Map<String, Object>[]) new Map[1];
	Map<String, Object> ccMap1 = new HashMap<String, Object>();
	
	ccMap1.put("address", "assistant@visdom.ca");

	bbMap[0]=ccMap1;




	// sender is optional
	Map<String, Object> senderMap = new HashMap<String, Object>();
	senderMap.put("name", "Welcome to Visdom's Referral program"); // optional
	senderMap.put("address", senderEmail);
	senderMap.put("reply_to", senderEmail); // optional

		// email data in to inject in the email template
	Map<String, Object> emailDataMap = new HashMap<String, Object>();
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	SendWithUsExample sendus=new SendWithUsExample();
	Calendar cal = Calendar.getInstance();
	cal.add(Calendar.DATE, 3);
	String currentDateTime=(dateFormat.format(cal.getTime())); 
	String mesage=sendus.getGreeting();
	emailDataMap.put("Greeting", mesage);
	emailDataMap.put("ReferralSourceName", referralSourceFirstName);
	emailDataMap.put("SubmitReferralPage", submitReferralPage);

  	String [] data={file};
  	System.out.println("file -----------"+file+"size "+data.length);
  	try{
  		File fl=new File(file);
  		System.out.println("file exissts "+fl.exists()+" file size"+(fl.length()/1024));
  	}catch(Exception e){
  		
  	}
	        // Example sending a simple email
	        System.out.println("Before Try");
	        try {
	        
	        	
	        	
	           SendReceipt sendReceipt = sendwithusAPI.send(EMAIL_ID_WELCOME_EMAIL4,
	                recipientMap,
	                senderMap,
	                emailDataMap,
	                ccMap,
	                bbMap,
	                data
	                
	            );
	        } catch (SendWithUsException e) {
	        	e.printStackTrace();
	            System.out.println(e.toString());
	        }
	        
	      
	    }
	    
	    
	    public void sentToSupportReferralMissing(String OpportuityName,String EmailId) throws JsonProcessingException {

	        SendWithUs sendwithusAPI = new SendWithUs(SENDWITHUS_API_KEY);

	        // Print list of available emails
	        
	System.out.println("First steps Email Ok");
	// Send Welcome Email
	Map<String, Object> recipientMap = new HashMap<String, Object>();
/*	recipientMap.put("name", ); // optional
*/	recipientMap.put("address", EmailId);



	Map<String, Object>[] ccMap = null;
	Map<String, Object>[] bbMap = null;



	bbMap=(Map<String, Object>[]) new Map[1];
	Map<String, Object> ccMap1 = new HashMap<String, Object>();
	
	ccMap1.put("address", "assistant@visdom.ca");

	bbMap[0]=ccMap1;

	// sender is optional
	Map<String, Object> senderMap = new HashMap<String, Object>();
	senderMap.put("name", "Missing Referral Source for "+OpportuityName); // optional
	senderMap.put("address", senderEmail);
	senderMap.put("reply_to", senderEmail); // optional

		// email data in to inject in the email template
	Map<String, Object> emailDataMap = new HashMap<String, Object>();
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	SendWithUsExample sendus=new SendWithUsExample();
	Calendar cal = Calendar.getInstance();
	cal.add(Calendar.DATE, 3);
	String currentDateTime=(dateFormat.format(cal.getTime())); 
	String mesage=sendus.getGreeting();
	emailDataMap.put("OpportuityName", OpportuityName);
  // Example sending a simple email
	        System.out.println("Before Try");
	        try {
	        
	        	
	        	
	           SendReceipt sendReceipt = sendwithusAPI.send(EMAIL_ID_WELCOME_EMAIL3,
	                recipientMap,
	                senderMap,
	                emailDataMap,
	                ccMap,
	                bbMap
	            );
	        } catch (SendWithUsException e) {
	        	e.printStackTrace();
	            System.out.println(e.toString());
	        }
	        
	      
	    }
	    
	    public void sendTOreferalSubmittedReferral(String leadFirstName,String referralSourceFirstName,String EmailId) throws JsonProcessingException {

	    	Map<String, Object>[] ccMap = (Map<String, Object>[]) new Map[1];
	        SendWithUs sendwithusAPI = new SendWithUs(SENDWITHUS_API_KEY);

	        // Print list of available emails
	        
	System.out.println("First steps Email Ok");
	// Send Welcome Email
	Map<String, Object> recipientMap = new HashMap<String, Object>();
	recipientMap.put("name", "Referral - Submitted Referral"); // optional
	recipientMap.put("address", EmailId);



	String mapAsJson = new ObjectMapper().writeValueAsString(ccMap);




	// sender is optional
	Map<String, Object> senderMap = new HashMap<String, Object>();
	senderMap.put("name", "We have received your referral for "+leadFirstName); // optional
	senderMap.put("address", senderEmail);
	senderMap.put("reply_to", senderEmail); // optional

		// email data in to inject in the email template
	Map<String, Object> emailDataMap = new HashMap<String, Object>();
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	SendWithUsExample sendus=new SendWithUsExample();
	Calendar cal = Calendar.getInstance();
	cal.add(Calendar.DATE, 3);
	String currentDateTime=(dateFormat.format(cal.getTime())); 
	String mesage=sendus.getGreeting();
	emailDataMap.put("Greeting", mesage);
	emailDataMap.put("ClientName", leadFirstName);
	emailDataMap.put("ReferralSourceName", referralSourceFirstName);

	Map<String, Object>[] 		bbMap=null;
	bbMap=(Map<String, Object>[]) new Map[1];
	Map<String, Object> ccMap1 = new HashMap<String, Object>();
	
	ccMap1.put("address", "assistant@visdom.ca");

	bbMap[0]=ccMap1;
	        // Example sending a simple email
	        System.out.println("Before Try");
	        try {
	        
	        	
	        	
	           SendReceipt sendReceipt = sendwithusAPI.send(EMAIL_ID_WELCOME_EMAIL13,
	                recipientMap,
	                senderMap,
	                emailDataMap,null,bbMap
	            );
	            System.out.println(sendReceipt);
	        } catch (SendWithUsException e) {
	            System.out.println(e.toString());
	        }
	        
	      
	    }
	    
	    public void sendTOclientMortgageApplication(String leadId,String leadFirstName,String referralSourceFirstName,String referralSourceLastName,String EmailId,String referralEmail) throws JsonProcessingException {

	    	Map<String, Object>[] ccMap = (Map<String, Object>[]) new Map[1];
	    	

	    	String contatId=getContactId(leadId);
	    	//String mortgageApplicationLink="<a style='text-decoration: none !important; display:block;padding: 5px 0px 0px 0px; background-color: #0000FF; width: 150px; height:38px; margin-left: 50px; color: #FFF;font-size: 13px; font-weight: bold;text-align: center; border-radius: 5px 5px; background-repeat: repeat no-repeat; 'href=\"https://dev-formsapp.visdom.ca/formsapp/mortgageForm?crmLeadId="+leadId+"&referrerEmail="+referralEmail+"\">Mortgage Application Form</a><br><br>";
	    	String mortgageApplicationLink="https://formsapp.visdom.ca/formsapp/mortgageForm?crmLeadId="+leadId+"&referrerEmail="+referralEmail+"&referralName="+referralSourceFirstName;
	    	String trackingUrl="https://videos.visdom.ca/clientA?LeadID="+leadId+"&ContactID="+contatId+"";
	    	
	    	SendWithUs sendwithusAPI = new SendWithUs(SENDWITHUS_API_KEY);
System.out.println("link "+mortgageApplicationLink);
	        // Print list of available emails
	        
	System.out.println("First steps Email Ok");
	// Send Welcome Email
	Map<String, Object> recipientMap = new HashMap<String, Object>();
	recipientMap.put("name", "Client - Mortgage Application"); // optional
	recipientMap.put("address", EmailId);

	Map<String, Object>[] 		bbMap=null;
	bbMap=(Map<String, Object>[]) new Map[1];
	Map<String, Object> ccMap1 = new HashMap<String, Object>();
	
	ccMap1.put("address", "assistant@visdom.ca");

	bbMap[0]=ccMap1;

	String mapAsJson = new ObjectMapper().writeValueAsString(ccMap);




	// sender is optional
	Map<String, Object> senderMap = new HashMap<String, Object>();
	senderMap.put("name", "Application Received"); // optional
	senderMap.put("address", senderEmail);
	senderMap.put("reply_to", senderEmail); // optional

		// email data in to inject in the email template
	Map<String, Object> emailDataMap = new HashMap<String, Object>();
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	SendWithUsExample sendus=new SendWithUsExample();
	Calendar cal = Calendar.getInstance();
	cal.add(Calendar.DATE, 3);
	String currentDateTime=(dateFormat.format(cal.getTime())); 
	String mesage=sendus.getGreeting();
	emailDataMap.put("Greeting", mesage);
	emailDataMap.put("ClientName", leadFirstName);
	emailDataMap.put("ReferralSource", referralSourceFirstName+"\t"+referralSourceLastName);
	emailDataMap.put("MortgageApplicationLink", mortgageApplicationLink);
	emailDataMap.put("TrackingUrl", trackingUrl);


	



	        // Example sending a simple email
	        System.out.println("Before Try");
	        try {
	        
	        	
	        	
	           SendReceipt sendReceipt = sendwithusAPI.send(EMAIL_ID_WELCOME_EMAIL14,
	                recipientMap,
	                senderMap,
	                emailDataMap,null,bbMap
	            );
	            System.out.println(sendReceipt);
	        } catch (SendWithUsException e) {
	            System.out.println(e.toString());
	        }
	        
	      
	    }
	    
	    
	    public void sendTOreferralCompletedApp(String referralSourceFirstName,String applicantFirstName,String EmailId,String coAppliantname) throws JsonProcessingException {

	    	Map<String, Object>[] ccMap = null;
	        SendWithUs sendwithusAPI = new SendWithUs(SENDWITHUS_API_KEY);

	        // Print list of available emails
	        
	System.out.println("First steps Email Ok");
	// Send Welcome Email
	Map<String, Object> recipientMap = new HashMap<String, Object>();
	recipientMap.put("name", "Referral - CompletedApp"); // optional
	recipientMap.put("address", EmailId);


	Map<String, Object>[] 		bbMap=null;
	bbMap=(Map<String, Object>[]) new Map[1];
	Map<String, Object> ccMap1 = new HashMap<String, Object>();
	
	ccMap1.put("address", "assistant@visdom.ca");

	bbMap[0]=ccMap1;
	String mapAsJson = new ObjectMapper().writeValueAsString(ccMap);



	// sender is optional
	Map<String, Object> senderMap = new HashMap<String, Object>();
	senderMap.put("name", "Received Application for "+applicantFirstName); // optional
	senderMap.put("address", senderEmail);
	senderMap.put("reply_to", senderEmail); // optional

		// email data in to inject in the email template
	Map<String, Object> emailDataMap = new HashMap<String, Object>();
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	SendWithUsExample sendus=new SendWithUsExample();
	Calendar cal = Calendar.getInstance();
	cal.add(Calendar.DATE, 3);
	String currentDateTime=(dateFormat.format(cal.getTime())); 
	String mesage=sendus.getGreeting();
	System.out.println("referral name -------------------------"+referralSourceFirstName);

	emailDataMap.put("Greeting", mesage);
	emailDataMap.put("ReferralSourceName", referralSourceFirstName);
	
	if(coAppliantname!=null&& !coAppliantname.isEmpty()){
		emailDataMap.put("ClientName", applicantFirstName+" and "+coAppliantname);

	}else{
		emailDataMap.put("ClientName", applicantFirstName);
	
	}

	



	        // Example sending a simple email
	        System.out.println("Before Try");
	        try {
	        
	        	
	        	
	           SendReceipt sendReceipt = sendwithusAPI.send(EMAIL_ID_WELCOME_EMAIL15,
	                recipientMap,
	                senderMap,
	                emailDataMap,null,bbMap
	            );
	            System.out.println(sendReceipt);
	        } catch (SendWithUsException e) {
	            System.out.println(e.toString());
	        }
	        
	      
	    }
	    
	    
	    public void sendTOclientCompletedApp(String applicantFirstName,String EmailId,String coApplicantName,String coApplicantEmial,String coApplicantTwoEmail) throws JsonProcessingException {

	    	Map<String, Object>[] ccMap =null;
	        SendWithUs sendwithusAPI = new SendWithUs(SENDWITHUS_API_KEY);

	        // Print list of available emails
	        
	System.out.println("First steps Email Ok");
	// Send Welcome Email
	Map<String, Object> recipientMap = new HashMap<String, Object>();
	recipientMap.put("name", "Client - CompletedApp"); // optional
	recipientMap.put("address", EmailId);
	if(coApplicantEmial!=null&& !coApplicantEmial.isEmpty()){
		ccMap=(Map<String, Object>[]) new Map[1];
		Map<String, Object> ccMap1 = new HashMap<String, Object>();
		ccMap1.put("address", coApplicantEmial);
		ccMap[0]=ccMap1;
	}


	String mapAsJson = new ObjectMapper().writeValueAsString(ccMap);

	Map<String, Object>[] 		bbMap=null;
	bbMap=(Map<String, Object>[]) new Map[1];
	Map<String, Object> ccMap1 = new HashMap<String, Object>();
	if(coApplicantTwoEmail!=null&&! coApplicantTwoEmail.isEmpty()){
	
		ccMap1.put("address", coApplicantTwoEmail);
	}
	ccMap1.put("address", "assistant@visdom.ca");

	bbMap[0]=ccMap1;


	// sender is optional
	Map<String, Object> senderMap = new HashMap<String, Object>();
	senderMap.put("name", "Thank you for your Visdom application "); // optional
	senderMap.put("address", senderEmail);
	senderMap.put("reply_to", senderEmail); // optional

		// email data in to inject in the email template
	Map<String, Object> emailDataMap = new HashMap<String, Object>();
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	SendWithUsExample sendus=new SendWithUsExample();
	Calendar cal = Calendar.getInstance();
	cal.add(Calendar.DATE, 3);
	String currentDateTime=(dateFormat.format(cal.getTime())); 
	String mesage=sendus.getGreeting();
	emailDataMap.put("Greeting", mesage);
	if(coApplicantName!=null&& !coApplicantEmial.isEmpty()){
		emailDataMap.put("ClientName", applicantFirstName+", "+coApplicantName);

	}else{
		emailDataMap.put("ClientName", applicantFirstName);

	}





	        // Example sending a simple email
	        System.out.println("Before Try");
	        try {
	        
	        	
	        	
	           SendReceipt sendReceipt = sendwithusAPI.send(EMAIL_ID_WELCOME_EMAIL16,
	                recipientMap,
	                senderMap,
	                emailDataMap,
	                ccMap,bbMap
	            );
	            System.out.println(sendReceipt);
	        } catch (SendWithUsException e) {
	            System.out.println(e.toString());
	        }
	        
	      
	    }
	    
	    
	    public void sendDisclosuresToclientCompletedApp(String applicantFirstName,String EmailId,String coAppliantname,String coApplicantEmail,String coApplicantTwoemail,String file) throws JsonProcessingException {

	    	Map<String, Object>[] ccMap = null;
	        SendWithUs sendwithusAPI = new SendWithUs(SENDWITHUS_API_KEY);

	        // Print list of available emails
	        
	System.out.println("First steps Email Ok");
	// Send Welcome Email
	Map<String, Object> recipientMap = new HashMap<String, Object>();
	recipientMap.put("name", "Client - CompletedApp"); // optional
	recipientMap.put("address", EmailId);



	String mapAsJson = new ObjectMapper().writeValueAsString(ccMap);

	if(coApplicantEmail!=null&&!coApplicantEmail.isEmpty()){
		ccMap=(Map<String, Object>[]) new Map[1];
		Map<String, Object> ccMap1 = new HashMap<String, Object>();
		ccMap1.put("address", coApplicantEmail);
		ccMap[0]=ccMap1;
	}
	Map<String, Object>[] 		bbMap=null;
	bbMap=(Map<String, Object>[]) new Map[1];
	Map<String, Object> ccMap1 = new HashMap<String, Object>();
	if(coApplicantTwoemail!=null&&! coApplicantTwoemail.isEmpty()){
	
		ccMap1.put("address", coApplicantTwoemail);
	}
	ccMap1.put("address", "assistant@visdom.ca");

	bbMap[0]=ccMap1;


	// sender is optional
	Map<String, Object> senderMap = new HashMap<String, Object>();
	senderMap.put("name", "Thank you for your Visdom application "); // optional
	senderMap.put("address", senderEmail);
	senderMap.put("reply_to", senderEmail); // optional

		// email data in to inject in the email template
	Map<String, Object> emailDataMap = new HashMap<String, Object>();
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	SendWithUsExample sendus=new SendWithUsExample();
	Calendar cal = Calendar.getInstance();
	cal.add(Calendar.DATE, 3);
	String currentDateTime=(dateFormat.format(cal.getTime())); 
	String mesage=sendus.getGreeting();
	emailDataMap.put("Greeting", mesage);
	
	if(coAppliantname!=null && coApplicantEmail.isEmpty()){
		emailDataMap.put("ClientName", applicantFirstName+", "+coAppliantname);

	}else{
		emailDataMap.put("ClientName", applicantFirstName);
	
	}
String [] data={file};
	




	        // Example sending a simple email
	        System.out.println("Before Try");
	        try {
	        
	        	
	        	
	           SendReceipt sendReceipt = sendwithusAPI.send(EMAIL_ID_WELCOME_EMAIL17,
	                recipientMap,
	                senderMap,
	                emailDataMap,ccMap,bbMap,data
	            );
	            System.out.println(sendReceipt);
	        } catch (SendWithUsException e) {
	            System.out.println(e.toString());
	        }
	        
	      
	    }
	    public String getGreeting(){
	    	String greeting="";
	    	Calendar time = new GregorianCalendar();  

	    					time.setTimeZone(TimeZone.getTimeZone("Canada/Mountain"));
	    
	    	int hour = time.get(Calendar.HOUR_OF_DAY);  
	    	int min = time.get(Calendar.MINUTE);  
	    	int day = time.get(Calendar.DAY_OF_MONTH);  
	    	int month = time.get(Calendar.MONTH) + 1;  
	    	int year = time.get(Calendar.YEAR);
	    	System.out.println("date Time "+time.getTime());

	    	System.out.println("The current time is \t" + hour + ":" + min);  
	    	System.out.println("Today's date is \t" + month + "/" + day + "/"  
	    	  + year);  
	

	    	if (hour < 12){
	    		greeting="Good Morning";
	    	 System.out.println("Good Morning");  
	    	}else if (hour >12 && hour < 16 ){  
	    		greeting="Good Afternoon";
	    	 System.out.println("Good Afternoon");  
	    	}else if (hour == 12)  {
	    		greeting="Good Afternoon";

	    	 System.out.println("Good Afternoon");  
	    	 
	    }else if (hour == 16)  {
			greeting="Good Evening";

		 System.out.println("Good Evening");  
		 
	}
	    	
	    	
	    	else if (hour > 16 && hour<24)  {
				greeting="Good Evening";


	    	}else  
	    	 System.out.println("Good Evening"); 
			return greeting;
	    	
	    	
	    	
	    }
	    
	    public void getsendWithUsApplicant_CoApplicant(String opprtunityId,String applicantname,String applicantEmail,String coApplicantEmail) throws JsonProcessingException {

	    	Map<String, Object>[] ccMap = (Map<String, Object>[]) new Map[1];
	        SendWithUs sendwithusAPI = new SendWithUs(SENDWITHUS_API_KEY);
	        String propersolLink="https://webserv.visdom.ca/index.php?option=com_mortgageapplication&view=mortgageapplicationedit&crm_lead="+opprtunityId  ;
	        // Print list of available emails
	        try {
	            Email[] emails = sendwithusAPI.emails();
	            for (int i = 0; i < emails.length; i++) {
	                System.out.println(emails[i].toString());
	            }
	        } catch (SendWithUsException e) {
	            System.out.println(e.toString());
	        }
	System.out.println("First steps Email Ok");
	// Send Welcome Email
	Map<String, Object> recipientMap = new HashMap<String, Object>();
	recipientMap.put("name", applicantname); // optional
	recipientMap.put("address",applicantEmail);





	if(coApplicantEmail!=null){
	Map<String, Object> ccMap1 = new HashMap<String, Object>();
	ccMap1.put("address", coApplicantEmail);
	ccMap[0]=ccMap1;

	}

	String mapAsJson = new ObjectMapper().writeValueAsString(ccMap);
	System.out.println("CCMAP is "+mapAsJson);




	// sender is optional
	Map<String, Object> senderMap = new HashMap<String, Object>();
	senderMap.put("name", "Client - Proposal Notification"); // optional
	senderMap.put("address", senderEmail);
	senderMap.put("reply_to", senderEmail); // optional

	// email data in to inject in the email template
	Map<String, Object> emailDataMap = new HashMap<String, Object>();
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	SendWithUsExample sendus=new SendWithUsExample();
	Calendar cal = Calendar.getInstance();
	cal.add(Calendar.DATE, 3);
	String currentDateTime=(dateFormat.format(cal.getTime())); 
	String mesage=sendus.getGreeting();
	emailDataMap.put("Greeting", mesage);
	emailDataMap.put("ClientName", applicantname);
	emailDataMap.put("ProposalLink", propersolLink);


	        // Example sending a simple email
	        System.out.println("Before Try");
	        try {
	        	
	        	if(coApplicantEmail!=null){
	        		
	        
	        	
	           SendReceipt sendReceipt = sendwithusAPI.send(EMAIL_ID_WELCOME_EMAIL2,
	                recipientMap,
	                senderMap,
	                emailDataMap,
	                ccMap
	                
	            );}else{
	            	 SendReceipt sendReceipt = sendwithusAPI.send(EMAIL_ID_WELCOME_EMAIL2,
	                         recipientMap,
	                         senderMap,
	                         emailDataMap
	                       
	                     );
	            }
	        } catch (SendWithUsException e) {
	            System.out.println(e.toString());
	        }
	        
	      
	    }
	    
	    public String getContactId(String leadId){
	    String contactId="0";
	    
	    try{
	    Session openErpConnection=new GenericHelperClass().getOdooConnection();
	    ObjectAdapter lead=openErpConnection.getObjectAdapter("crm.lead");
	    FilterCollection fileter=new FilterCollection();
	    fileter.add("id", "=", leadId);
	    RowCollection row=lead.searchAndReadObject(fileter, new String[]{"id","partner_id"});
	    for (Iterator iterator = row.iterator(); iterator.hasNext();) {
			Row row2 = (Row) iterator.next();
				Object [] object=(Object[])row2.get("partner_id");
				contactId=object[0].toString();
		}
	    }catch(Exception e){
	    	
	    }
	    	
	    	
	    	return contactId;
	    }

}
