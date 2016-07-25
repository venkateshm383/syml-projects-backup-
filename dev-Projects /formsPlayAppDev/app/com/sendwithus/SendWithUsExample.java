package com.sendwithus;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import play.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sendwithus.exception.SendWithUsException;
import com.syml.Constants;

import controllers.Lead;

public class SendWithUsExample {

	public static final String SENDWITHUS_API_KEY = "live_a7c95c3b0fb3acb519463955b1a2be67b2299734";
	
	public static final String EMAIL_TO_MORTGAGE_APPLICATION_LINK = "tem_dwZRPNjCUxNpcTxfPpV6Lb";
	public static final String EMAIL_TO_SUBMITED_REFERRAL = "tem_5yCEPNPCmZLbNDHvMwmU7G";
	public static final String EMAIL_TO_REFERRAL_MORTGAGE_APPLICETION_COMPLETED = "tem_KQJMr9h3jTktZsrHxT8S4d";
	public static final String EMAIL_TO_CLINET_MORTGAGE_APPLICETION_COMPLETED = "tem_n339FNZymdBE8EPdqAdZ5n";
	public static final String EMAIL_TO_CLINET_DISCOLSUER_ATTACHMENT= "tem_FmJpYPxcRymahVMEruGthn";
	public static final String EMAIL_TO_REFERRAL_ATTACHMENT = "tem_q4ouKbPraQiCth2rqTC9mX";
	public static final String EMAIL_TO_REFERRAL_MISSING = "tem_MoY2qvWuunLr7FqcnfrQCa";
	public static final String EMAIL_TO_DUPLICATE_REFERRAL = "tem_aEmvUkiU3CXczGwR9hpGH9";
	public static final String EMAIL_TO_SUBMIT_REFERAL_EMAIL_CHANGE_ID = "tem_kHMsLNQM3Dht87JVTDypZ7";

	public static final String EMAIL_TO_REFERAL_SOURCE_EMAIL_CHANGE = "tem_Z7tD2dnCoVXP85ryGYoKvK";

	String senderEmail = "support@visdom.ca";

	@SuppressWarnings("unchecked")
	public void sentToSupportReferralMissing(String OpportuityName,
			String EmailId) throws JsonProcessingException {
		SendWithUs sendwithusAPI = new SendWithUs(SENDWITHUS_API_KEY);
		// Print list of available emails
		// Send Welcome Email
		Map<String, Object> recipientMap = new HashMap<String, Object>();
		/*
		 * recipientMap.put("name", ); // optional
		 */recipientMap.put("address", EmailId);

		Map<String, Object>[] ccMap = null;
		Map<String, Object>[] bbMap = null;

		bbMap = (Map<String, Object>[]) new Map[1];
		Map<String, Object> ccMap1 = new HashMap<String, Object>();

		ccMap1.put("address", "assistant@visdom.ca");

		bbMap[0] = ccMap1;

		// sender is optional
		Map<String, Object> senderMap = new HashMap<String, Object>();
		senderMap.put("name", "Missing Referral Source for " + OpportuityName); // optional
		senderMap.put("address", senderEmail);
		senderMap.put("reply_to", senderEmail); // optional

		// email data in to inject in the email template
		Map<String, Object> emailDataMap = new HashMap<String, Object>();
	
		
		emailDataMap.put("OpportuityName", OpportuityName);
		// Example sending a simple email
		try {
			 sendwithusAPI.send(
					EMAIL_TO_REFERRAL_MISSING, recipientMap, senderMap,
					emailDataMap, ccMap, bbMap);
		} catch (SendWithUsException e) {
			Logger.error(
					"error in sending Mail for Referral Resource Missing for opporunity =  "
							+ OpportuityName, e);
		}

	}

	@SuppressWarnings("unchecked")
	public void sendTOclientMortgageApplication(String leadId,
			String leadFirstName, String referralSourceFirstName,
			String referralSourceLastName, String EmailId, String referralEmail)
			throws JsonProcessingException {


		String expireDate = "";
		try {
			expireDate = new SendWithUsExample().getExpiredDate();
		} catch (Exception e) {
			Logger.error("Error when calculating expiration of date ", e);
		}

		// String
		// mortgageApplicationLink="<a style='text-decoration: none !important; display:block;padding: 5px 0px 0px 0px; background-color: #0000FF; width: 150px; height:38px; margin-left: 50px; color: #FFF;font-size: 13px; font-weight: bold;text-align: center; border-radius: 5px 5px; background-repeat: repeat no-repeat; 'href=\"https://dev-formsapp.visdom.ca/formsapp/mortgageForm?crmLeadId="+leadId+"&referrerEmail="+referralEmail+"\">Mortgage Application Form</a><br><br>";
		// String
		// mortgageApplicationLink="https://dev-f.visdom.ca/formsapp/mortgageForm?crmLeadId="+leadId+"&referrerEmail="+referralEmail+"&referralName="+referralSourceFirstName;
		String mortgageApplicationLink = "https://dev-forms.visdom.ca/mortgagePage1?crmLeadId="
				+ leadId
				+ "&referrerEmail="
				+ referralEmail
				+ "&referralName="
				+ referralSourceFirstName + "&role=C&expireDate=" + expireDate;
		String trackingUrl = "https://dev-videos.visdom.ca/clientA?LeadID="
				+ leadId;

		SendWithUs sendwithusAPI = new SendWithUs(SENDWITHUS_API_KEY);

		// Send Welcome Email
		Map<String, Object> recipientMap = new HashMap<String, Object>();
		recipientMap.put("name", "Client - Mortgage Application"); // optional
		recipientMap.put("address", EmailId);

		Map<String, Object>[] bbMap = null;
		bbMap = (Map<String, Object>[]) new Map[1];
		Map<String, Object> ccMap1 = new HashMap<String, Object>();

		ccMap1.put("address", senderEmail);

		bbMap[0] = ccMap1;

		// sender is optional
		Map<String, Object> senderMap = new HashMap<String, Object>();
		senderMap.put("name", "Application Received"); // optional
		senderMap.put("address", senderEmail);
		senderMap.put("reply_to", senderEmail); // optional

		// email data in to inject in the email template
		Map<String, Object> emailDataMap = new HashMap<String, Object>();
		SendWithUsExample sendus = new SendWithUsExample();

		String mesage = sendus.getGreeting();
		emailDataMap.put("Greeting", mesage);
		emailDataMap.put("ClientName", leadFirstName);
		emailDataMap.put("ReferralSource", referralSourceFirstName + "\t"
				+ referralSourceLastName);
		emailDataMap.put("MortgageApplicationLink", mortgageApplicationLink);
		emailDataMap.put("TrackingUrl", trackingUrl);

		// Example sending a simple email
		try {
			 sendwithusAPI.send(
					EMAIL_TO_MORTGAGE_APPLICATION_LINK, recipientMap, senderMap,
					emailDataMap, null, bbMap);
		} catch (SendWithUsException e) {
			Logger.error("error in sending Mail for Lead {{" + leadFirstName
					+ "}}about MortagageApplication Link   ", e);
		}

	}

	@SuppressWarnings("unchecked")
	public void sentToSupportAboutDuplicateReferralSource(
			String OpportuityName, String EmailId)
			throws JsonProcessingException {
		SendWithUs sendwithusAPI = new SendWithUs(SENDWITHUS_API_KEY);
		// Print list of available emails

		// Send Welcome Email
		Map<String, Object> recipientMap = new HashMap<String, Object>();
		/*
		 * recipientMap.put("name", ); // optional
		 */recipientMap.put("address", EmailId);

		Map<String, Object>[] ccMap = null;
		Map<String, Object>[] bbMap = null;

		bbMap = (Map<String, Object>[]) new Map[1];
		Map<String, Object> ccMap1 = new HashMap<String, Object>();

		ccMap1.put("address", "assistant@visdom.ca");

		bbMap[0] = ccMap1;

		// sender is optional
		Map<String, Object> senderMap = new HashMap<String, Object>();
		senderMap.put("name", "Duplicate Referral Sources  for "
				+ OpportuityName); // optional
		senderMap.put("address", senderEmail);
		senderMap.put("reply_to", senderEmail); // optional

		// email data in to inject in the email template
		Map<String, Object> emailDataMap = new HashMap<String, Object>();

		emailDataMap.put("OpportuityName", OpportuityName);

		try {
			sendwithusAPI.send(EMAIL_TO_DUPLICATE_REFERRAL, recipientMap,
					senderMap, emailDataMap, ccMap, bbMap);
		} catch (SendWithUsException e) {
			Logger.error(
					"error in sending Mail of sentToSupportAboutDuplicateReferralSource for opporunity =  "
							+ OpportuityName, e);

		}

	}

	@SuppressWarnings("unchecked")
	public void sentToReferralSubmitReferralEMailChangeID(Lead lead)
			throws JsonProcessingException {
		SendWithUs sendwithusAPI = new SendWithUs(SENDWITHUS_API_KEY);
		// Print list of available emails
		// Send Welcome Email
		Map<String, Object> recipientMap = new HashMap<String, Object>();
		/*
		 * recipientMap.put("name", ); // optional
		 */recipientMap.put("address", lead.getReferral_Source_Email());

		Map<String, Object>[] ccMap = null;
		Map<String, Object>[] bbMap = null;

		bbMap = (Map<String, Object>[]) new Map[1];
		Map<String, Object> ccMap1 = new HashMap<String, Object>();

		ccMap1.put("address", "assistant@visdom.ca");

		bbMap[0] = ccMap1;

		// sender is optional
		Map<String, Object> senderMap = new HashMap<String, Object>();
		senderMap.put("name",
				"We have received your referral for " + lead.getName()); // optional
		senderMap.put("address", senderEmail);
		senderMap.put("reply_to", senderEmail); // optional

		// email data in to inject in the email template
		Map<String, Object> emailDataMap = new HashMap<String, Object>();

		SendWithUsExample sendus = new SendWithUsExample();

		String mesage = sendus.getGreeting();
		emailDataMap.put("ClientName", lead.getName());
		emailDataMap.put("ReferralSourceName",
				lead.getReferral_Source_First_Name());
		emailDataMap.put("Greeting", mesage);

		try {
			sendwithusAPI.send(EMAIL_TO_SUBMIT_REFERAL_EMAIL_CHANGE_ID,
					recipientMap, senderMap, emailDataMap, ccMap, bbMap);
		} catch (SendWithUsException e) {
			Logger.error(
					"error in sending Mail of sentToReferralSubmitReferralEMailChangeID for referral Source =  "
							+ lead.getReferral_Source_First_Name()
							+ "  leadName=" + lead.getFirstName(), e);

		}

	}

	@SuppressWarnings("unchecked")
	public void sendTOreferalSubmittedReferral(String leadFirstName,
			String referralSourceFirstName, String EmailId)
			throws JsonProcessingException {
		SendWithUs sendwithusAPI = new SendWithUs(SENDWITHUS_API_KEY);

		// Send Welcome Email
		Map<String, Object> recipientMap = new HashMap<String, Object>();
		recipientMap.put("name", "Referral - Submitted Referral"); // optional
		recipientMap.put("address", EmailId);

		// sender is optional
		Map<String, Object> senderMap = new HashMap<String, Object>();
		senderMap.put("name", "We have received your referral for "
				+ leadFirstName); // optional
		senderMap.put("address", senderEmail);
		senderMap.put("reply_to", senderEmail); // optional

		// email data in to inject in the email template
		Map<String, Object> emailDataMap = new HashMap<String, Object>();
		
		SendWithUsExample sendus = new SendWithUsExample();
		String mesage = sendus.getGreeting();
		emailDataMap.put("Greeting", mesage);
		emailDataMap.put("ClientName", leadFirstName);
		emailDataMap.put("ReferralSourceName", referralSourceFirstName);

		Map<String, Object>[] bbMap = null;
		bbMap = (Map<String, Object>[]) new Map[1];
		Map<String, Object> ccMap1 = new HashMap<String, Object>();

		ccMap1.put("address", "assistant@visdom.ca");

		bbMap[0] = ccMap1;

		try {

			 sendwithusAPI.send(
					EMAIL_TO_SUBMITED_REFERRAL, recipientMap, senderMap,
					emailDataMap, null, bbMap);
		} catch (SendWithUsException e) {
			Logger.error(
					"error in sending Mail of sendTOreferalSubmittedReferral for referral Source =  "
							+ referralSourceFirstName
							+ "  leadName="
							+ leadFirstName, e);
		}

	}

	@SuppressWarnings("unchecked")
	public void sendTOreferralCompletedApp(String referralSourceFirstName,
			String applicantFirstName, String EmailId, String coAppliantname)
			throws JsonProcessingException {

		SendWithUs sendwithusAPI = new SendWithUs(SENDWITHUS_API_KEY);

		// Print list of available emails

		Map<String, Object> recipientMap = new HashMap<String, Object>();
		recipientMap.put("name", "Referral - CompletedApp"); // optional
		recipientMap.put("address", EmailId);

		Map<String, Object>[] bbMap = null;
		bbMap = (Map<String, Object>[]) new Map[1];
		Map<String, Object> ccMap1 = new HashMap<String, Object>();

		ccMap1.put("address", "assistant@visdom.ca");

		bbMap[0] = ccMap1;

		// sender is optional
		Map<String, Object> senderMap = new HashMap<String, Object>();
		senderMap.put("name", "Received Application for " + applicantFirstName); // optional
		senderMap.put("address", senderEmail);
		senderMap.put("reply_to", senderEmail); // optional

		// email data in to inject in the email template
		Map<String, Object> emailDataMap = new HashMap<String, Object>();
		SendWithUsExample sendus = new SendWithUsExample();
		String mesage = sendus.getGreeting();

		emailDataMap.put("Greeting", mesage);
		emailDataMap.put("ReferralSourceName", referralSourceFirstName);

		if (coAppliantname != null && !coAppliantname.isEmpty()) {
			emailDataMap.put("ClientName", applicantFirstName + " and "
					+ coAppliantname);

		} else {
			emailDataMap.put("ClientName", applicantFirstName);

		}

		// Example sending a simple email
		try {

			 sendwithusAPI.send(
					EMAIL_TO_REFERRAL_MORTGAGE_APPLICETION_COMPLETED, recipientMap, senderMap,
					emailDataMap, null, bbMap);
		} catch (SendWithUsException e) {
			Logger.error(
					"error in sending Mail of sendTOreferralCompletedApp for referral Source =  "
							+ referralSourceFirstName + "  applicantName="
							+ applicantFirstName, e);

		}

	}

	@SuppressWarnings("unchecked")
	public void sendTOclientCompletedApp(String applicantFirstName,
			String EmailId, String coApplicantName, String coApplicantEmial,
			String coApplicantTwoEmail) throws JsonProcessingException {

		Map<String, Object>[] ccMap = null;
		SendWithUs sendwithusAPI = new SendWithUs(SENDWITHUS_API_KEY);

		// Send Welcome Email
		Map<String, Object> recipientMap = new HashMap<String, Object>();
		recipientMap.put("name", "Client - CompletedApp"); // optional
		recipientMap.put("address", EmailId);
		if (coApplicantEmial != null && !coApplicantEmial.isEmpty()) {
			ccMap = (Map<String, Object>[]) new Map[1];
			Map<String, Object> ccMap1 = new HashMap<String, Object>();
			ccMap1.put("address", coApplicantEmial);
			ccMap[0] = ccMap1;
		}


		Map<String, Object>[] bbMap = null;
		bbMap = (Map<String, Object>[]) new Map[1];
		Map<String, Object> ccMap1 = new HashMap<String, Object>();
		if (coApplicantTwoEmail != null && !coApplicantTwoEmail.isEmpty()) {

			ccMap1.put("address", coApplicantTwoEmail);
		}
		ccMap1.put("address", "assistant@visdom.ca");

		bbMap[0] = ccMap1;

		// sender is optional
		Map<String, Object> senderMap = new HashMap<String, Object>();
		senderMap.put("name", "Thank you for your Visdom application "); // optional
		senderMap.put("address", senderEmail);
		senderMap.put("reply_to", senderEmail); // optional

		// email data in to inject in the email template
		Map<String, Object> emailDataMap = new HashMap<String, Object>();
		SendWithUsExample sendus = new SendWithUsExample();
		String mesage = sendus.getGreeting();
		emailDataMap.put("Greeting", mesage);
		if (coApplicantName != null && !coApplicantEmial.isEmpty()) {
			emailDataMap.put("ClientName", applicantFirstName + ", "
					+ coApplicantName);

		} else {
			emailDataMap.put("ClientName", applicantFirstName);

		}

		try {

			 sendwithusAPI.send(
					EMAIL_TO_CLINET_MORTGAGE_APPLICETION_COMPLETED, recipientMap, senderMap,
					emailDataMap, ccMap, bbMap);
		} catch (SendWithUsException e) {
			Logger.error(
					"error in sending Mail of sendTOclientCompletedApp for   applicantName="
							+ applicantFirstName, e);
		}

	}

	@SuppressWarnings("unchecked")
	public void sentToReferralEMailChange(Lead lead)
			throws JsonProcessingException {
		SendWithUs sendwithusAPI = new SendWithUs(SENDWITHUS_API_KEY);

		Map<String, Object> recipientMap = new HashMap<String, Object>();

		recipientMap.put("address", Constants.SUPPORT_MAIL_ID);

		Map<String, Object>[] ccMap = null;
		Map<String, Object>[] bbMap = null;

		bbMap = (Map<String, Object>[]) new Map[1];
		Map<String, Object> ccMap1 = new HashMap<String, Object>();

		ccMap1.put("address", "assistant@visdom.ca");

		bbMap[0] = ccMap1;

		// sender is optional
		Map<String, Object> senderMap = new HashMap<String, Object>();
		senderMap.put("name", "Referral Changed Email Id "); // optional
		senderMap.put("address", senderEmail);
		senderMap.put("reply_to", senderEmail); // optional

		// email data in to inject in the email template
		Map<String, Object> emailDataMap = new HashMap<String, Object>();

		SendWithUsExample sendus = new SendWithUsExample();

		String mesage = sendus.getGreeting();
		emailDataMap.put("FirstName", lead.getReferral_Source_First_Name());
		emailDataMap.put("LastName", lead.getReferral_Source_Last_Name());
		emailDataMap.put("OpportuityName", lead.getFirstName());

		emailDataMap.put("ReferralSourceID", lead.getReferref_source());
		emailDataMap.put("LastName", lead.getReferral_Source_First_Name());
		emailDataMap.put("ReferralSourceEmail", mesage);

		emailDataMap.put("LeadLastName", lead.getName());
		emailDataMap.put("LeadEmail", lead.getEmail_from());

		try {
			sendwithusAPI.send(EMAIL_TO_REFERAL_SOURCE_EMAIL_CHANGE,
					recipientMap, senderMap, emailDataMap, ccMap, bbMap);
		} catch (SendWithUsException e) {
			Logger.error(
					"error in sending Mail of sentToReferralEMailChange for    lead.getFirstName()="
							+ lead.getFirstName()
							+ " Referral Source ="
							+ lead.getReferral_Source_First_Name(), e);

		}

	}

	@SuppressWarnings("unchecked")
	public void sendDisclosuresToclientCompletedApp(String applicantFirstName,
			String EmailId, String coAppliantname, String coApplicantEmail,
			String coApplicantTwoemail, String file)
			throws JsonProcessingException {

		Map<String, Object>[] ccMap = null;
		SendWithUs sendwithusAPI = new SendWithUs(SENDWITHUS_API_KEY);

		// Print list of available emails

		// Send Welcome Email
		Map<String, Object> recipientMap = new HashMap<String, Object>();
		recipientMap.put("name", "Client - CompletedApp"); // optional
		recipientMap.put("address", EmailId);

		if (coApplicantEmail != null && !coApplicantEmail.isEmpty()) {
			ccMap = (Map<String, Object>[]) new Map[1];
			Map<String, Object> ccMap1 = new HashMap<String, Object>();
			ccMap1.put("address", coApplicantEmail);
			ccMap[0] = ccMap1;
		}
		Map<String, Object>[] bbMap = null;
		bbMap = (Map<String, Object>[]) new Map[1];
		Map<String, Object> ccMap1 = new HashMap<String, Object>();
		if (coApplicantTwoemail != null && !coApplicantTwoemail.isEmpty()) {

			ccMap1.put("address", coApplicantTwoemail);
		}
		ccMap1.put("address", "assistant@visdom.ca");

		bbMap[0] = ccMap1;

		// sender is optional
		Map<String, Object> senderMap = new HashMap<String, Object>();
		senderMap.put("name", "Thank you for your Visdom application "); // optional
		senderMap.put("address", senderEmail);
		senderMap.put("reply_to", senderEmail); // optional

		// email data in to inject in the email template
		Map<String, Object> emailDataMap = new HashMap<String, Object>();
		SendWithUsExample sendus = new SendWithUsExample();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 3);
		String mesage = sendus.getGreeting();
		emailDataMap.put("Greeting", mesage);

		if (coAppliantname != null && !coApplicantEmail.isEmpty()) {
			emailDataMap.put("ClientName", applicantFirstName + ", "
					+ coAppliantname);

		} else {
			emailDataMap.put("ClientName", applicantFirstName);

		}
		String[] data = { file };

		try {

		 sendwithusAPI.send(
					EMAIL_TO_CLINET_DISCOLSUER_ATTACHMENT, recipientMap, senderMap,
					emailDataMap, ccMap, bbMap, data);
		} catch (SendWithUsException e) {
			Logger.error(
					"error in sending Mail of sendDisclosuresToclientCompletedApp for    applicantFirstName="
							+ applicantFirstName, e);
		}

	}

	public String getGreeting() {
		String greeting = "";
		Calendar time = new GregorianCalendar();

		time.setTimeZone(TimeZone.getTimeZone("Canada/Mountain"));

		int hour = time.get(Calendar.HOUR_OF_DAY);
		
		if (hour < 12) {
			greeting = "Good Morning";
		} else if (hour > 12 && hour < 16) {
			greeting = "Good Afternoon";
		} else if (hour == 12) {
			greeting = "Good Afternoon";

		} else if (hour == 16) {
			greeting = "Good Evening";

		}

		else if (hour > 16 && hour < 24) {
			greeting = "Good Evening";

		} else

			return greeting;
		return greeting;

	}

	@SuppressWarnings("unchecked")
	public void sendTOReferralAttachment(String referralSourceFirstName,

	String submitReferralPage, String EmailId, String file) throws IOException {

		SendWithUs sendwithusAPI = new SendWithUs(SENDWITHUS_API_KEY);

		// Print list of available emails

		// Send Welcome Email
		Map<String, Object> recipientMap = new HashMap<String, Object>();
		recipientMap.put("name", "Referral Agreement"); // optional
		recipientMap.put("address", EmailId);

		Map<String, Object>[] ccMap = null;
		Map<String, Object>[] bbMap = null;
		bbMap = (Map<String, Object>[]) new Map[1];
		Map<String, Object> ccMap1 = new HashMap<String, Object>();

		ccMap1.put("address", "assistant@visdom.ca");

		bbMap[0] = ccMap1;

		// sender is optional
		Map<String, Object> senderMap = new HashMap<String, Object>();
		senderMap.put("name", "Welcome to Visdom's Referral program"); // optional
		senderMap.put("address", senderEmail);
		senderMap.put("reply_to", senderEmail); // optional

		// email data in to inject in the email template
		Map<String, Object> emailDataMap = new HashMap<String, Object>();
		SendWithUsExample sendus = new SendWithUsExample();

		String mesage = sendus.getGreeting();

		emailDataMap.put("Greeting", mesage);
		emailDataMap.put("ReferralSourceName", referralSourceFirstName);
		emailDataMap.put("SubmitReferralLink", submitReferralPage);

		String[] data = { file };

		try {
			 sendwithusAPI.send(
					EMAIL_TO_REFERRAL_ATTACHMENT, recipientMap, senderMap,
					emailDataMap, ccMap, bbMap, data);

		} catch (SendWithUsException e) {
			Logger.error(
					"error in sending Mail of sendTOReferralAttachment for    Referral SOurcee="
							+ referralSourceFirstName, e);
		}

	}

	public String getExpiredDate() {
		String output = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Calendar c = Calendar.getInstance();
			c.setTime(new Date()); // today date.
			c.add(Calendar.DATE, 14); // Adding 14 days
			output = sdf.format(c.getTime());
		} catch (Exception e) {
			Logger.error("Error when calculating Expired Date based on current date inside getExpiredDate() of SendWithUsExample "
					+ e);
		}
		return output;
	}

}