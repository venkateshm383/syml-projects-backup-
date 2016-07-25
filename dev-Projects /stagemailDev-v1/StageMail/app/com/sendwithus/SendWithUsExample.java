package com.sendwithus;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sendwithus.exception.SendWithUsException;
import com.sendwithus.model.Email;
import com.sendwithus.model.SendReceipt;

import controllers.Recommendation;

public class SendWithUsExample {

	public static final String SENDWITHUS_API_KEY = "live_a7c95c3b0fb3acb519463955b1a2be67b2299734";
	public static final String EMAIL_ID_WELCOME_EMAIL = "tem_su5msryYNEqXUvdV9CnNgb";
	public static final String EMAIL_ID_WELCOME_EMAIL1 = "tem_GwD5YrZnJgisMx8XPXWonT";
	public static final String EMAIL_ID_WELCOME_EMAIL2 = "tem_smDhYJa3CP3CyCPeJv5ALF";
	public static final String EMAIL_ID_WELCOME_EMAIL3 = "tem_vZkNdBwuKrmjKKBR5axGj3";
	public static final String EMAIL_ID_FOR_COMBINEPRODUCTS = "tem_4Wk5vhmrHnnC3yYz2zTkVQ";

	public static final String EMAIL_ID_WELCOME_EMAIL4 = "tem_dARGRv2m4bhdBKtD5VPpmn";
	public static final String EMAIL_ID_WELCOME_EMAIL5 = "tem_e4TpkWbKTYcyKmL2tJ9MRT";
	public static final String EMAIL_ID_WELCOME_EMAIL6 = "tem_Gy5GoFyzR6zwHL7NJkwKiC";
	public static final String EMAIL_ID_WELCOME_EMAIL7 = "tem_WawpgMtMfXCSiCwXwRiW7B";
	public static final String EMAIL_ID_WELCOME_EMAIL8 = "tem_2UVVLDiFtRR3oMEBukDfWT";
	public static final String EMAIL_ID_WELCOME_EMAIL9 = "tem_89qDEGb4ewqiwbzFAK6Ckf";
	public static final String EMAIL_ID_WELCOME_EMAIL10 = "tem_xX4UhhfXpcTNNqMWqt9PeW";
	public static final String EMAIL_ID_WELCOME_EMAIL11 = "tem_9Ysc8sxZ3okq2xUtFao3Df";
	public static final String EMAIL_ID_WELCOME_EMAIL12 = "tem_Jk3fuVCDzCYSxUt7maWhmR";
	public static final String EMAIL_ID_WELCOME_EMAIL13 = "tem_5yCEPNPCmZLbNDHvMwmU7G";
	public static final String EMAIL_ID_WELCOME_EMAIL14 = "tem_dwZRPNjCUxNpcTxfPpV6Lb";
	public static final String EMAIL_ID_WELCOME_EMAIL15 = "tem_KQJMr9h3jTktZsrHxT8S4d";
	public static final String EMAIL_ID_WELCOME_EMAIL16 = "tem_n339FNZymdBE8EPdqAdZ5n";
	public static final String EMAIL_ID_WELCOME_EMAIL17 = "tem_JvJnHyoxvAwenBrrQDKDcj";

	public static void main(String[] args) throws JsonProcessingException {
		SendWithUsExample exm = new SendWithUsExample();
		exm.getGreeting();
		// exm.getsendWithUsApplicant_CoApplicant("227", "test",
		// "venkatesh.m@bizruntime.com", "venkatesh.m@bizruntime.com");
	}

	public static String fromEmail = "support@visdom.ca";

	public void getsendWithUs(String applicantname, String emailAddress)
			throws JsonProcessingException {
		Map<String, Object>[] ccMap = (Map<String, Object>[]) new Map[1];
		SendWithUs sendwithusAPI = new SendWithUs(SENDWITHUS_API_KEY);

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
		recipientMap.put("address", emailAddress);

		Map<String, Object> ccMap1 = new HashMap<String, Object>();
		ccMap1.put("address", emailAddress);
		ccMap[0] = ccMap1;

		String mapAsJson = new ObjectMapper().writeValueAsString(ccMap);
		System.out.println("CCMAP is " + mapAsJson);

		// sender is optional
		Map<String, Object> senderMap = new HashMap<String, Object>();
		// senderMap.put("name", "Company"); // optional
		senderMap.put("address", fromEmail);
		senderMap.put("reply_to", fromEmail); // optional

		String myvar = "{"
				+ "            \"firstName\": \"John\","
				+ "            \"lastName\": \"Soiseth\","
				+ "            \"email\": \"soiseth@live.com\","
				+ "            \"documents\": ["
				+ "                \"2013 Notice of Assessment\","
				+ "                \"2014 T4\","
				+ "                \"Copy of Photo ID (Driver?s Licence or Passport)\","
				+ "                \"Child Tax Credit\""
				+ "            ]"
				+ "        },"
				+ "        {"
				+ "            \"firstName\": \"Joslyn\","
				+ "            \"lastName\": \"Davidson\","
				+ "            \"email\": \"soiseth@live.com\","
				+ "            \"documents\": ["
				+ "                \"Copy of Photo ID (Driver?s Licence or Passport)\","
				+ "                \"Child Tax Credit\"" + "            ]"
				+ "        }";

		String[] attachments = { "firstName" };

		// email data in to inject in the email template
		Map<String, Object> emailDataMap = new HashMap<String, Object>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SendWithUsExample sendus = new SendWithUsExample();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 3);
		String currentDateTime = (dateFormat.format(cal.getTime()));
		String mesage = sendus.getGreeting();
		emailDataMap.put("Greeting", mesage + "\t" + applicantname);
		emailDataMap.put("ClientName", applicantname);
		emailDataMap.put("DocumentsRequiredList", myvar);
		emailDataMap.put("DueDate", currentDateTime);

		// Example sending a simple email
		System.out.println("Before Try");
		try {
			/*
			 * SendReceipt sendReceipt = sendwithusAPI.send(
			 * EMAIL_ID_WELCOME_EMAIL, recipientMap, senderMap, emailDataMap,
			 * null, null, attachments
			 * 
			 * );
			 */

			SendReceipt sendReceipt = sendwithusAPI.send(
					EMAIL_ID_WELCOME_EMAIL, recipientMap, senderMap,
					emailDataMap, ccMap

			);
			System.out.println(sendReceipt);
		} catch (SendWithUsException e) {
			System.out.println(e.toString());
		}

	}

	public void sendTOreferralCompletedApp(String referralSourceFirstName,
			String applicantFirstName, String EmailId,
			String coApplicantFirstName) throws JsonProcessingException {

		Map<String, Object>[] ccMap = (Map<String, Object>[]) new Map[1];
		SendWithUs sendwithusAPI = new SendWithUs(SENDWITHUS_API_KEY);

		// Print list of available emails

		System.out.println("First steps Email Ok");
		// Send Welcome Email
		Map<String, Object> recipientMap = new HashMap<String, Object>();
		recipientMap.put("name", "Referral - CompletedApp"); // optional
		recipientMap.put("address", EmailId);

		String mapAsJson = new ObjectMapper().writeValueAsString(ccMap);

		Map<String, Object>[] bbMap = null;
		bbMap = (Map<String, Object>[]) new Map[1];
		Map<String, Object> ccMap1 = new HashMap<String, Object>();

		ccMap1.put("address", "assistant@visdom.ca");

		bbMap[0] = ccMap1;

		// sender is optional
		Map<String, Object> senderMap = new HashMap<String, Object>();
		senderMap.put("name", "Referral - CompletedApp"); // optional
		senderMap.put("address", fromEmail);
		senderMap.put("reply_to", fromEmail); // optional

		// email data in to inject in the email template
		Map<String, Object> emailDataMap = new HashMap<String, Object>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SendWithUsExample sendus = new SendWithUsExample();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 3);
		String currentDateTime = (dateFormat.format(cal.getTime()));
		String mesage = sendus.getGreeting();
		System.out.println("greeting Before sending" + mesage
				+ "referalSourceName " + referralSourceFirstName);

		emailDataMap.put("Greeting", mesage);
		emailDataMap.put("ReferralSourceName", referralSourceFirstName);
		emailDataMap.put("ClientName", applicantFirstName + " and "
				+ coApplicantFirstName);

		// Example sending a simple email
		System.out.println("Before Try");
		try {

			SendReceipt sendReceipt = sendwithusAPI.send(
					EMAIL_ID_WELCOME_EMAIL15, recipientMap, senderMap,
					emailDataMap, null, bbMap);
			System.out.println(sendReceipt);
		} catch (SendWithUsException e) {
			System.out.println(e.toString());
		}

	}

	public void sendTOclientCompletedApp(String applicantFirstName,
			String EmailId, String coApplicantFirstName)
			throws JsonProcessingException {

		Map<String, Object>[] ccMap = (Map<String, Object>[]) new Map[1];
		SendWithUs sendwithusAPI = new SendWithUs(SENDWITHUS_API_KEY);

		// Print list of available emails

		System.out.println("First steps Email Ok");
		// Send Welcome Email
		Map<String, Object> recipientMap = new HashMap<String, Object>();
		recipientMap.put("name", "Client - CompletedApp"); // optional
		recipientMap.put("address", EmailId);

		String mapAsJson = new ObjectMapper().writeValueAsString(ccMap);

		// sender is optional
		Map<String, Object> senderMap = new HashMap<String, Object>();
		senderMap.put("name", "Client - CompletedApp"); // optional
		senderMap.put("address", fromEmail);
		senderMap.put("reply_to", fromEmail); // optional

		// email data in to inject in the email template
		Map<String, Object> emailDataMap = new HashMap<String, Object>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SendWithUsExample sendus = new SendWithUsExample();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 3);
		String currentDateTime = (dateFormat.format(cal.getTime()));
		String mesage = sendus.getGreeting();
		emailDataMap.put("Greeting", mesage);
		emailDataMap.put("ClientName", applicantFirstName + " and "
				+ coApplicantFirstName);

		Map<String, Object>[] bbMap = null;
		bbMap = (Map<String, Object>[]) new Map[1];
		Map<String, Object> ccMap1 = new HashMap<String, Object>();

		ccMap1.put("address", "assistant@visdom.ca");

		bbMap[0] = ccMap1;

		// Example sending a simple email
		System.out.println("Before Try");
		try {

			SendReceipt sendReceipt = sendwithusAPI.send(
					EMAIL_ID_WELCOME_EMAIL16, recipientMap, senderMap,
					emailDataMap, null, bbMap);
			System.out.println(sendReceipt);
		} catch (SendWithUsException e) {
			System.out.println(e.toString());
		}

	}

	public void sendTOApplicantDisclosures(String applicantFirstName,
			String EmailId, String coApplicantFirstName)
			throws JsonProcessingException {

		Map<String, Object>[] ccMap = (Map<String, Object>[]) new Map[1];
		SendWithUs sendwithusAPI = new SendWithUs(SENDWITHUS_API_KEY);

		// Print list of available emails

		System.out.println("First steps Email Ok");
		// Send Welcome Email
		Map<String, Object> recipientMap = new HashMap<String, Object>();
		recipientMap.put("name", "Client - CompletedApp"); // optional
		recipientMap.put("address", EmailId);

		String mapAsJson = new ObjectMapper().writeValueAsString(ccMap);

		// sender is optional
		Map<String, Object> senderMap = new HashMap<String, Object>();
		senderMap.put("name", "Client - CompletedApp"); // optional
		senderMap.put("address", fromEmail);
		senderMap.put("reply_to", fromEmail); // optional

		// email data in to inject in the email template
		Map<String, Object> emailDataMap = new HashMap<String, Object>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SendWithUsExample sendus = new SendWithUsExample();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 3);
		String currentDateTime = (dateFormat.format(cal.getTime()));
		String mesage = sendus.getGreeting();
		System.out.println("greeting Before sending" + mesage);

		emailDataMap.put("Greeting", mesage);
		emailDataMap.put("ClientName", applicantFirstName + " and "
				+ coApplicantFirstName);

		Map<String, Object>[] bbMap = null;
		bbMap = (Map<String, Object>[]) new Map[1];
		Map<String, Object> ccMap1 = new HashMap<String, Object>();

		ccMap1.put("address", "assistant@visdom.ca");

		bbMap[0] = ccMap1;

		// Example sending a simple email
		System.out.println("Before Try");
		try {

			SendReceipt sendReceipt = sendwithusAPI.send(
					EMAIL_ID_WELCOME_EMAIL16, recipientMap, senderMap,
					emailDataMap, null, bbMap);
			System.out.println(sendReceipt);
		} catch (SendWithUsException e) {
			System.out.println(e.toString());
		}

	}

	public void getsendWithUsRefferal(String applicantname,
			String referralEmail, String referralSourceName)
			throws JsonProcessingException {

		Map<String, Object>[] ccMap = (Map<String, Object>[]) new Map[1];
		SendWithUs sendwithusAPI = new SendWithUs(SENDWITHUS_API_KEY);

		// Print list of available emails

		System.out.println("First steps Email getsendWithUsRefferal  Ok");
		// Send Welcome Email
		Map<String, Object> recipientMap = new HashMap<String, Object>();
		recipientMap.put("name", referralSourceName); // optional
		recipientMap.put("address", referralEmail);

		String mapAsJson = new ObjectMapper().writeValueAsString(ccMap);

		// sender is optional
		Map<String, Object> senderMap = new HashMap<String, Object>();
		senderMap.put("name", " Referral - Proposal Notification"); // optional
		senderMap.put("address", fromEmail);
		senderMap.put("reply_to", fromEmail); // optional

		// email data in to inject in the email template
		Map<String, Object> emailDataMap = new HashMap<String, Object>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SendWithUsExample sendus = new SendWithUsExample();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 3);
		String currentDateTime = (dateFormat.format(cal.getTime()));
		String mesage = sendus.getGreeting();
		System.out.println("greeting Before sending" + mesage
				+ "referalSourceName " + referralSourceName);

		emailDataMap.put("Greeting", mesage);
		emailDataMap.put("ReferralSourceName", referralSourceName);
		emailDataMap.put("ClientName", applicantname);
		Map<String, Object>[] bbMap = null;
		bbMap = (Map<String, Object>[]) new Map[1];
		Map<String, Object> ccMap1 = new HashMap<String, Object>();

		ccMap1.put("address", "assistant@visdom.ca");

		bbMap[0] = ccMap1;

		// Example sending a simple email
		System.out.println("Before Try");
		try {

			SendReceipt sendReceipt = sendwithusAPI.send(
					EMAIL_ID_WELCOME_EMAIL1, recipientMap, senderMap,
					emailDataMap, null, bbMap);
			System.out.println(sendReceipt);
		} catch (SendWithUsException e) {
			System.out.println(e.toString());
		}

	}

	public void getsendWithUsApplicant_CoApplicant(String opprtunityId,
			String applicantname, String applicantEmail,
			String coApplicantEmail, String TrackingUrl)
			throws JsonProcessingException {
		System.out.println("inside  mail  method ");

		Map<String, Object>[] ccMap = null;
		SendWithUs sendwithusAPI = new SendWithUs(SENDWITHUS_API_KEY);
		String propersolLink = "https://proposal.visdom.ca/proposal?action=c&opp_id="
				+ opprtunityId;
		// Print list of available emails
		System.out.println("First steps Email Ok");
		// Send Welcome Email
		Map<String, Object> recipientMap = new HashMap<String, Object>();
		recipientMap.put("name", applicantname); // optional
		recipientMap.put("address", applicantEmail);

		if (coApplicantEmail != null) {
			ccMap = (Map<String, Object>[]) new Map[1];
			Map<String, Object> ccMap1 = new HashMap<String, Object>();
			ccMap1.put("address", coApplicantEmail);
			ccMap[0] = ccMap1;

		}

		String mapAsJson = new ObjectMapper().writeValueAsString(ccMap);
		System.out.println("CCMAP is " + mapAsJson);

		Map<String, Object>[] bbMap = null;
		bbMap = (Map<String, Object>[]) new Map[1];
		Map<String, Object> ccMap1 = new HashMap<String, Object>();

		ccMap1.put("address", "assistant@visdom.ca");

		bbMap[0] = ccMap1;

		// sender is optional
		Map<String, Object> senderMap = new HashMap<String, Object>();
		senderMap.put("name", "Client - Proposal Notification"); // optional
		senderMap.put("address", fromEmail);
		senderMap.put("reply_to", fromEmail); // optional

		// email data in to inject in the email template
		Map<String, Object> emailDataMap = new HashMap<String, Object>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SendWithUsExample sendus = new SendWithUsExample();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 3);
		String currentDateTime = (dateFormat.format(cal.getTime()));
		String mesage = sendus.getGreeting();
		System.out.println("greeting Before sending" + mesage);

		emailDataMap.put("Greeting", mesage);
		emailDataMap.put("ClientName", applicantname);
		emailDataMap.put("ProposalLink", propersolLink);
		emailDataMap.put("TrackingUrl", TrackingUrl);

		// Example sending a simple email
		System.out.println("Before Try");
		try {

			if (coApplicantEmail != null) {

				SendReceipt sendReceipt = sendwithusAPI.send(
						EMAIL_ID_WELCOME_EMAIL2, recipientMap, senderMap,
						emailDataMap, ccMap, bbMap

				);
			} else {
				SendReceipt sendReceipt = sendwithusAPI.send(
						EMAIL_ID_WELCOME_EMAIL2, recipientMap, senderMap,
						emailDataMap, null, bbMap

				);
			}
		} catch (SendWithUsException e) {
			e.printStackTrace();
			System.out.println(e.toString());
		}

	}

	public void getsendWithUsReferalProposal(String applicantname,
			String referalSourceName, String referralEmail,
			String singulerPlureler, String lenderNAme)
			throws JsonProcessingException {

		Map<String, Object>[] ccMap = (Map<String, Object>[]) new Map[1];
		SendWithUs sendwithusAPI = new SendWithUs(SENDWITHUS_API_KEY);

		// Print list of available emails
		System.out.println("First steps Email Ok");
		// Send Welcome Email
		Map<String, Object> recipientMap = new HashMap<String, Object>();
		recipientMap.put("name", referalSourceName); // optional
		recipientMap.put("address", referralEmail);

		String mapAsJson = new ObjectMapper().writeValueAsString(ccMap);
		System.out.println("CCMAP is " + mapAsJson);

		// sender is optional
		Map<String, Object> senderMap = new HashMap<String, Object>();
		senderMap.put("name", "Referral - Product Selection"); // optional
		senderMap.put("address", fromEmail);
		senderMap.put("reply_to", fromEmail); // optional

		// email data in to inject in the email template
		Map<String, Object> emailDataMap = new HashMap<String, Object>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SendWithUsExample sendus = new SendWithUsExample();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 3);
		String currentDateTime = (dateFormat.format(cal.getTime()));
		String mesage = sendus.getGreeting();
		System.out.println("greeting Before sending" + mesage
				+ "referalSourceName " + referalSourceName);
		emailDataMap.put("Greeting", mesage);

		emailDataMap.put("ReferralSourceName", referalSourceName);
		emailDataMap.put("ClientName", applicantname);
		emailDataMap.put("SingularPlural", singulerPlureler);
		emailDataMap.put("LenderName", lenderNAme);

		emailDataMap.put("ClientNamePossessive", applicantname + "‘s");
		Map<String, Object>[] bbMap = null;
		bbMap = (Map<String, Object>[]) new Map[1];
		Map<String, Object> ccMap1 = new HashMap<String, Object>();

		ccMap1.put("address", "assistant@visdom.ca");

		bbMap[0] = ccMap1;

		// Example sending a simple email
		System.out.println("Before Try");
		try {

			SendReceipt sendReceipt = sendwithusAPI.send(
					EMAIL_ID_WELCOME_EMAIL17, recipientMap, senderMap,
					emailDataMap, null, bbMap

			);
			System.out.println(sendReceipt);
		} catch (SendWithUsException e) {
			System.out.println(e.toString());
		}

	}

	public void getsendWithUsProductSelection(String applicantname,
			String lenderNAme, String productName, String mortgageType,
			String term, String amortization, String interestRate,
			String expectedPayment, String applicantEmail,
			String coApplicantEmail) throws JsonProcessingException {

		Map<String, Object>[] ccMap = (Map<String, Object>[]) new Map[1];
		SendWithUs sendwithusAPI = new SendWithUs(SENDWITHUS_API_KEY);

		// Print list of available emails

		// Send Welcome Email
		Map<String, Object> recipientMap = new HashMap<String, Object>();
		recipientMap.put("name", applicantname); // optional
		recipientMap.put("address", applicantEmail);

		if (coApplicantEmail != null) {
			Map<String, Object> ccMap1 = new HashMap<String, Object>();
			ccMap1.put("address", coApplicantEmail);
			ccMap[0] = ccMap1;
		}

		Map<String, Object>[] bbMap = null;
		bbMap = (Map<String, Object>[]) new Map[1];
		Map<String, Object> ccMap1 = new HashMap<String, Object>();

		ccMap1.put("address", "assistant@visdom.ca");

		bbMap[0] = ccMap1;

		String mapAsJson = new ObjectMapper().writeValueAsString(ccMap);
		// sender is optional
		Map<String, Object> senderMap = new HashMap<String, Object>();
		senderMap.put("name", "Client - Product Selection"); // optional
		senderMap.put("address", fromEmail);
		senderMap.put("reply_to", fromEmail); // optional

		// email data in to inject in the email template
		Map<String, Object> emailDataMap = new HashMap<String, Object>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SendWithUsExample sendus = new SendWithUsExample();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 3);
		String currentDateTime = (dateFormat.format(cal.getTime()));
		String mesage = sendus.getGreeting();
		System.out.println("gretting message inside ----" + mesage);

		emailDataMap.put("Greeting", mesage);
		emailDataMap.put("ClientName", applicantname);
		emailDataMap.put("LenderName", lenderNAme);
		emailDataMap.put("ProductName", productName);
		emailDataMap.put("MortgageType", mortgageType);
		emailDataMap.put("Term", term);
		emailDataMap.put("Amortization", amortization);
		emailDataMap.put("InterestRate", interestRate);
		emailDataMap.put("ExpectedPayment", expectedPayment);

		// Example sending a simple email
		System.out.println("Before Try");
		try {
			if (coApplicantEmail != null) {
				SendReceipt sendReceipt = sendwithusAPI.send(
						EMAIL_ID_WELCOME_EMAIL3, recipientMap, senderMap,
						emailDataMap, ccMap, bbMap

				);
			} else {
				SendReceipt sendReceipt = sendwithusAPI.send(
						EMAIL_ID_WELCOME_EMAIL3, recipientMap, senderMap,
						emailDataMap, null, bbMap

				);
			}
		} catch (SendWithUsException e) {
			System.out.println(e.toString());
		}

	}

	
	public void getsendWithUsProductSelectionForCombine(Recommendation rec ,String applicantname,
			 String applicantEmail,
			String coApplicantEmail) throws JsonProcessingException {
		
		
		System.out.println("----------ec.toString()-----------"+rec.toString());
		
		System.out.println("-----------applicantEmail----------"+applicantEmail);
		System.out.println("-----------coApplicantEmail----------"+coApplicantEmail);

		Map<String, Object>[] ccMap = (Map<String, Object>[]) new Map[1];
		SendWithUs sendwithusAPI = new SendWithUs(SENDWITHUS_API_KEY);

		// Print list of available emails

		// Send Welcome Email
		Map<String, Object> recipientMap = new HashMap<String, Object>();
		recipientMap.put("name", applicantname); // optional
		recipientMap.put("address", applicantEmail);

		if (coApplicantEmail != null) {
			Map<String, Object> ccMap1 = new HashMap<String, Object>();
			ccMap1.put("address", coApplicantEmail);
			ccMap[0] = ccMap1;
		}

		Map<String, Object>[] bbMap = null;
		bbMap = (Map<String, Object>[]) new Map[1];
		Map<String, Object> ccMap1 = new HashMap<String, Object>();

		ccMap1.put("address", "assistant@visdom.ca");

		bbMap[0] = ccMap1;
System.out.println("---------------------------"+fromEmail);
		String mapAsJson = new ObjectMapper().writeValueAsString(ccMap);
		// sender is optional
		Map<String, Object> senderMap = new HashMap<String, Object>();
		senderMap.put("name", "Client - Product Selection"); // optional
		senderMap.put("address", fromEmail);
		senderMap.put("reply_to", fromEmail); // optional

		// email data in to inject in the email template
		Map<String, Object> emailDataMap = new HashMap<String, Object>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SendWithUsExample sendus = new SendWithUsExample();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 3);
		String currentDateTime = (dateFormat.format(cal.getTime()));
		String mesage = sendus.getGreeting();

		emailDataMap.put("Greeting", mesage);
		emailDataMap.put("ClientName", applicantname);
		emailDataMap.put("LenderName", rec.getSelectedProductLender());
		//emailDataMap.put("LenderName", "TEST");
		emailDataMap.put("ProductName1", rec.getSelectedBaseProductName());
		
		emailDataMap.put("MortgageType1", rec.getSelectedBaseMortgageType());
		emailDataMap.put("Term1", rec.getSelectedBaseProductTerm());
		emailDataMap.put("InterestRate1 ", rec.getSelectedBaseInterestRate());
		emailDataMap.put("InterestRate1", rec.getSelectedBaseMortageAmount());
		
		emailDataMap.put("ProductName2", rec.getSelectedAdditionalProductName());
		
		emailDataMap.put("MortgageType2", rec.getSelectedAdditionalMortgageType());
		emailDataMap.put("Term2", rec.getSelectedAdditionalProductTerm());
		emailDataMap.put("InterestRate2 ", rec.getSelectedAdditionalInterestRate());
		emailDataMap.put("InterestRate2", rec.getSelectedAdditionalMortageAmount());
		
		emailDataMap.put("TotalMortgageAmount", rec.getTotalMortgageAmount());
		emailDataMap.put("Amortization", rec.getAmortization());
		emailDataMap.put("TotalExpectedPayment", rec.getTotalMonthlyPayment());
		
		
		//emailDataMap.put("ppo", rec.getProductType());

		// Example sending a simple email
		System.out.println("Before Try");
		try {
			if (coApplicantEmail != null) {
				SendReceipt sendReceipt = sendwithusAPI.send(
						EMAIL_ID_FOR_COMBINEPRODUCTS, recipientMap, senderMap,
						emailDataMap, ccMap, bbMap

				);
			} else {
				SendReceipt sendReceipt = sendwithusAPI.send(
						EMAIL_ID_FOR_COMBINEPRODUCTS, recipientMap, senderMap,
						emailDataMap, null, bbMap

				);
			}
		} catch (SendWithUsException e) {
			System.out.println(e.toString());
		}

	}
	public void getsendWithUsClient_LawyerDetail(String applicantname,
			String applicantEmail) throws JsonProcessingException {

		Map<String, Object>[] ccMap = (Map<String, Object>[]) new Map[1];
		SendWithUs sendwithusAPI = new SendWithUs(SENDWITHUS_API_KEY);

		// Print list of available emails

		// Send Welcome Email
		Map<String, Object> recipientMap = new HashMap<String, Object>();
		// recipientMap.put("name", "vikash"); // optional
		recipientMap.put("address", applicantEmail);

		Map<String, Object> ccMap1 = new HashMap<String, Object>();
		ccMap1.put("address", applicantEmail);
		ccMap[0] = ccMap1;

		Map<String, Object>[] bbMap = null;
		bbMap = (Map<String, Object>[]) new Map[1];
		Map<String, Object> ccMap2 = new HashMap<String, Object>();

		ccMap2.put("address", "assistant@visdom.ca");

		bbMap[0] = ccMap2;

		String mapAsJson = new ObjectMapper().writeValueAsString(ccMap);
		// sender is optional
		Map<String, Object> senderMap = new HashMap<String, Object>();
		senderMap.put("name", "Client - Lawyer Details"); // optional
		senderMap.put("address", fromEmail);
		senderMap.put("reply_to", fromEmail); // optional

		// email data in to inject in the email template
		Map<String, Object> emailDataMap = new HashMap<String, Object>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SendWithUsExample sendus = new SendWithUsExample();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 3);
		String currentDateTime = (dateFormat.format(cal.getTime()));

		String mesage = sendus.getGreeting();
		System.out.println("greeting Before sending " + mesage);

		emailDataMap.put("Greeting", mesage);
		emailDataMap.put("ClientName", applicantname);

		// Example sending a simple email
		System.out.println("Before Try");
		try {

			SendReceipt sendReceipt = sendwithusAPI.send(
					EMAIL_ID_WELCOME_EMAIL4, recipientMap, senderMap,
					emailDataMap, ccMap, bbMap

			);
			System.out.println(sendReceipt);
		} catch (SendWithUsException e) {
			System.out.println(e.toString());
		}

	}

	public void getsendWithUsClient_RefferalSource(String applicantname,
			String referralSourceName, String referralEmail)
			throws JsonProcessingException {

		Map<String, Object>[] ccMap = (Map<String, Object>[]) new Map[1];
		SendWithUs sendwithusAPI = new SendWithUs(SENDWITHUS_API_KEY);

		// Print list of available emails

		// Send Welcome Email
		Map<String, Object> recipientMap = new HashMap<String, Object>();
		recipientMap.put("name", referralSourceName); // optional
		recipientMap.put("address", referralEmail);

		/*
		 * Map<String, Object> ccMap1 = new HashMap<String, Object>();
		 * ccMap1.put("address", "venkatesh.m@bizruntime.com"); ccMap[0]=ccMap1;
		 */

		String mapAsJson = new ObjectMapper().writeValueAsString(ccMap);
		// sender is optional
		Map<String, Object> senderMap = new HashMap<String, Object>();
		senderMap.put("name", "Referral - Lender Approval"); // optional
		senderMap.put("address", fromEmail);
		senderMap.put("reply_to", fromEmail); // optional

		// email data in to inject in the email template
		Map<String, Object> emailDataMap = new HashMap<String, Object>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SendWithUsExample sendus = new SendWithUsExample();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 3);
		String currentDateTime = (dateFormat.format(cal.getTime()));
		String mesage = sendus.getGreeting();

		System.out.println("greeting Before sending" + mesage
				+ "referalSourceName " + referralSourceName);

		emailDataMap.put("Greeting", mesage);
		emailDataMap.put("ReferralSourceName", referralSourceName);
		emailDataMap.put("ClientName", applicantname);

		Map<String, Object>[] bbMap = null;
		bbMap = (Map<String, Object>[]) new Map[1];
		Map<String, Object> ccMap1 = new HashMap<String, Object>();

		ccMap1.put("address", "assistant@visdom.ca");

		bbMap[0] = ccMap1;

		// Example sending a simple email
		System.out.println("Before Try");
		try {

			SendReceipt sendReceipt = sendwithusAPI.send(
					EMAIL_ID_WELCOME_EMAIL5, recipientMap, senderMap,
					emailDataMap, null, bbMap

			);
			System.out.println(sendReceipt);
		} catch (SendWithUsException e) {
			System.out.println(e.toString());
		}

	}

	public void getsendWithUsApplicant_Client_FinalCongratulations(
			String applicantname, String surveyLink, String emailAddress)
			throws JsonProcessingException {

		Map<String, Object>[] ccMap = (Map<String, Object>[]) new Map[1];
		SendWithUs sendwithusAPI = new SendWithUs(SENDWITHUS_API_KEY);

		// Print list of available emails

		// Send Welcome Email
		Map<String, Object> recipientMap = new HashMap<String, Object>();
		// recipientMap.put("name", "vikash"); // optional
		recipientMap.put("address", emailAddress);

		Map<String, Object> ccMap1 = new HashMap<String, Object>();
		ccMap1.put("address", emailAddress);
		ccMap[0] = ccMap1;

		String mapAsJson = new ObjectMapper().writeValueAsString(ccMap);
		// sender is optional
		Map<String, Object> senderMap = new HashMap<String, Object>();
		senderMap.put("name", "Client - Final Congratulations"); // optional
		senderMap.put("address", fromEmail);
		senderMap.put("reply_to", fromEmail); // optional
		Map<String, Object>[] bbMap = null;
		bbMap = (Map<String, Object>[]) new Map[1];
		Map<String, Object> ccMap2 = new HashMap<String, Object>();

		ccMap2.put("address", "assistant@visdom.ca");

		bbMap[0] = ccMap1;
		// email data in to inject in the email template
		Map<String, Object> emailDataMap = new HashMap<String, Object>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SendWithUsExample sendus = new SendWithUsExample();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 3);
		String currentDateTime = (dateFormat.format(cal.getTime()));
		System.out.println("Get Greetings is");
		String mesage = sendus.getGreeting();
		emailDataMap.put("Greeting", sendus.getGreeting().toString());
		emailDataMap.put("ClientName", applicantname);
		// emailDataMap.put("SurveyLink", surveyLink);

		// Example sending a simple email
		System.out.println("Before Try");
		try {

			SendReceipt sendReceipt = sendwithusAPI.send(
					EMAIL_ID_WELCOME_EMAIL8, recipientMap, senderMap,
					emailDataMap, null, bbMap

			);
			System.out.println(sendReceipt);
		} catch (SendWithUsException e) {
			System.out.println(e.toString());
		}

	}

	public void getsendWithUsReferral_Final_Congratulations(
			String applicantname, String refSource, String referralEmail,
			String referralFeeAmount) throws JsonProcessingException {

		SendWithUs sendwithusAPI = new SendWithUs(SENDWITHUS_API_KEY);

		// Send Welcome Email
		Map<String, Object> recipientMap = new HashMap<String, Object>();
		recipientMap.put("name", refSource); // optional
		recipientMap.put("address", referralEmail);
		Map<String, Object> ccMap1 = new HashMap<String, Object>();

		// sender is optional
		Map<String, Object> senderMap = new HashMap<String, Object>();
		senderMap.put("name", "Referral Final Congratulations"); // optional
		senderMap.put("address", fromEmail);
		senderMap.put("reply_to", fromEmail); // optional
		Map<String, Object>[] bbMap = null;
		bbMap = (Map<String, Object>[]) new Map[1];
		Map<String, Object> ccMap2 = new HashMap<String, Object>();

		ccMap2.put("address", "assistant@visdom.ca");

		bbMap[0] = ccMap1;
		// email data in to inject in the email template
		Map<String, Object> emailDataMap = new HashMap<String, Object>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SendWithUsExample sendus = new SendWithUsExample();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 3);
		String currentDateTime = (dateFormat.format(cal.getTime()));
		String mesage = sendus.getGreeting();
		System.out.println("Greeting Data is "
				+ sendus.getGreeting().toString());
		emailDataMap.put("Greeting", sendus.getGreeting());
		emailDataMap.put("ReferralSourceName", refSource);
		emailDataMap.put("ClientName", applicantname);
		emailDataMap.put("ReferralFeeAmount", referralFeeAmount);

		// Example sending a simple email
		System.out.println("Before Try");
		try {

			SendReceipt sendReceipt = sendwithusAPI.send(
					EMAIL_ID_WELCOME_EMAIL9, recipientMap, senderMap,
					emailDataMap, null, bbMap

			);
		} catch (SendWithUsException e) {
			System.out.println(e.toString());
		}

	}

	public void getsendWithUsReferral_Referral_Paid(String applicantname,
			String refSource, String referralMail, String referralFeeAmount,
			String surveyrul) throws JsonProcessingException {

		Map<String, Object>[] ccMap = (Map<String, Object>[]) new Map[1];
		SendWithUs sendwithusAPI = new SendWithUs(SENDWITHUS_API_KEY);

		// Send Welcome Email
		Map<String, Object> recipientMap = new HashMap<String, Object>();
		recipientMap.put("name", refSource); // optional
		recipientMap.put("address", referralMail);

		String mapAsJson = new ObjectMapper().writeValueAsString(ccMap);
		// sender is optional
		Map<String, Object> senderMap = new HashMap<String, Object>();
		senderMap.put("name", "Referral - Paid"); // optional
		senderMap.put("address", fromEmail);
		senderMap.put("reply_to", fromEmail); // optional
		Map<String, Object>[] bbMap = null;
		bbMap = (Map<String, Object>[]) new Map[1];
		Map<String, Object> ccMap1 = new HashMap<String, Object>();

		ccMap1.put("address", "assistant@visdom.ca");

		bbMap[0] = ccMap1;
		// email data in to inject in the email template
		Map<String, Object> emailDataMap = new HashMap<String, Object>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SendWithUsExample sendus = new SendWithUsExample();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 3);
		String currentDateTime = (dateFormat.format(cal.getTime()));
		String mesage = sendus.getGreeting();
		System.out.println("Greeting Message IS "
				+ sendus.getGreeting().toString());
		emailDataMap.put("Greeting", sendus.getGreeting().toString());
		emailDataMap.put("ReferralSourceName", refSource);
		emailDataMap.put("ClientName", applicantname);
		emailDataMap.put("ReferralFeeAmount", referralFeeAmount);
		emailDataMap.put("SurveyLink", surveyrul);

		// Example sending a simple email
		System.out.println("Before Try");
		try {

			SendReceipt sendReceipt = sendwithusAPI.send(
					EMAIL_ID_WELCOME_EMAIL10, recipientMap, senderMap,
					emailDataMap, null, bbMap

			);
			System.out.println(sendReceipt);
		} catch (SendWithUsException e) {
			System.out.println(e.toString());
		}

	}

	public void getSendWithCompenstionFileComplete(String applicantname,
			String applicantEmailone, String applicantEmailTwo,
			String lendername, String closingDate, String surveyLink)
			throws JsonProcessingException {

		Map<String, Object>[] ccMap = null;
		SendWithUs sendwithusAPI = new SendWithUs(SENDWITHUS_API_KEY);

		// Send Welcome Email
		Map<String, Object> recipientMap = new HashMap<String, Object>();
		recipientMap.put("address", applicantEmailone);

		if (applicantEmailTwo != null) {
			ccMap = (Map<String, Object>[]) new Map[1];
			Map<String, Object> ccMap1 = new HashMap<String, Object>();
			ccMap1.put("address", applicantEmailTwo);
			ccMap[0] = ccMap1;
		}
		Map<String, Object>[] bbMap = null;
		bbMap = (Map<String, Object>[]) new Map[1];
		Map<String, Object> ccMap1 = new HashMap<String, Object>();

		ccMap1.put("address", "assistant@visdom.ca");

		bbMap[0] = ccMap1;

		String mapAsJson = new ObjectMapper().writeValueAsString(ccMap);
		// sender is optional
		Map<String, Object> senderMap = new HashMap<String, Object>();
		senderMap.put("name", "File Complete with " + lendername); // optional
		senderMap.put("address", fromEmail);
		senderMap.put("reply_to", fromEmail); // optional

		// email data in to inject in the email template
		Map<String, Object> emailDataMap = new HashMap<String, Object>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SendWithUsExample sendus = new SendWithUsExample();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 3);
		String currentDateTime = (dateFormat.format(cal.getTime()));
		String mesage = sendus.getGreeting();
		System.out.println("Greeting Message IS "
				+ sendus.getGreeting().toString());
		emailDataMap.put("Greeting", sendus.getGreeting().toString());
		emailDataMap.put("ClientName", applicantname);
		emailDataMap.put("LenderName", lendername);
		emailDataMap.put("ClosingDate", closingDate);
		emailDataMap.put("SurveyLink", surveyLink);

		// Example sending a simple email
		System.out.println("Before Try");
		try {

			SendReceipt sendReceipt = sendwithusAPI.send(
					EMAIL_ID_WELCOME_EMAIL6, recipientMap, senderMap,
					emailDataMap, ccMap, bbMap

			);
			System.out.println(sendReceipt);
		} catch (SendWithUsException e) {
			System.out.println(e.toString());
		}

	}

	public String getGreeting() {
		String greeting = "";
		Calendar time = new GregorianCalendar();

		time.setTimeZone(TimeZone.getTimeZone("Canada/Mountain"));
		int hour = time.get(Calendar.HOUR_OF_DAY);
		int min = time.get(Calendar.MINUTE);
		int day = time.get(Calendar.DAY_OF_MONTH);
		int month = time.get(Calendar.MONTH) + 1;
		int year = time.get(Calendar.YEAR);

		System.out.println("The current time is \t" + hour + ":" + min);
		System.out.println("Today's date is \t" + month + "/" + day + "/"
				+ year);

		if (hour < 12) {
			greeting = "Good Morning ";
			System.out.println("Good Morning!");
		} else if (hour > 12 && hour < 16) {
			greeting = "Good Afternoon";
			System.out.println("Good Afternoon");
		} else if (hour == 12) {
			greeting = "Good Afternoon";

			System.out.println("Good Afternoon");

		} else if (hour == 16) {
			greeting = "Good Evening";

			System.out.println("Good Evening");

		}

		else if (hour > 16 && hour < 24) {
			greeting = "Good Evening";

		} else
			System.out.println("Good Evening");
		return greeting;

	}

}
