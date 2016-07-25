package com.sendwithus;
import java.util.HashMap;
import java.util.Map;

import com.sendwithus.SendWithUs;
import java.util.Map;

import com.sendwithus.SendWithUs;
import com.sendwithus.exception.SendWithUsException;
import com.sendwithus.model.DeactivatedDrips;
import com.sendwithus.model.Email;
import com.sendwithus.model.SendReceipt;

public class TestEmail {
public static void main(String[] args) throws SendWithUsException {
	
	final String SENDWITHUS_API_KEY = "test_5fa2cfdb8de3b6229c1bd8a2645842efcd80503e";
	final String EMAIL_ID_WELCOME_EMAIL = "guy@syml.ca";

	SendWithUs sendwithusAPI = new SendWithUs(SENDWITHUS_API_KEY);

	// Send Welcome Email
	Map<String, Object> recipientMap = new HashMap<String, Object>();
	recipientMap.put("name", "Matt"); // optional
	recipientMap.put("address", "us@sendwithus.com");

	// sender is optional
	Map<String, Object> senderMap = new HashMap<String, Object>();
	senderMap.put("name", "Company"); // optional
	senderMap.put("address", "company@company.com");
	senderMap.put("reply_to", "info@company.com"); // optional

	// ESP account is an advanced feature, optional
	String espAccount = "tdf_123981";

	Map<String, Object> emailDataMap = new HashMap<String, Object>();
	emailDataMap.put("first_name", "Brad");
	emailDataMap.put("link", "http://sendwithus.com/some_link");

	String[] attachments = {"test.png"};

	// Sending the email using the legacy send() java API
	SendReceipt sendReceipt = sendwithusAPI.send(
	    EMAIL_ID_WELCOME_EMAIL,
	    recipientMap,
	    senderMap,
	    emailDataMap,
	    null,
	    null,
	    attachments,
	    espAccount
	);

	// Example sending the same email using the new SendWithUsSendRequest class
	SendWithUsSendRequest request = new SendWithUsSendRequest()
	    .setEmailId(EMAIL_ID_WELCOME_EMAIL)
	    .setRecipient(recipientMap)
	    .setSender(senderMap)
	    .setEmailData(emailDataMap)
	    .setAttachmentPaths(attachments)
	    .setEspAccount(espAccount);
	SendReceipt sendReceipt1 = sendwithusAPI.send(request);
}

}
