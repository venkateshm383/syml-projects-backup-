package com.syml.constants;

import java.util.UUID;

public class SymlConstant {
	
	private final  String odooIP="107.23.27.61";
	private final  int odooPort=8069;
	private final  String odooDB="symlsys";
	private final  String odooUsername="guy@visdom.ca";
	private final  String odoopassword="VisdomTesting";
	private final  String couchBaseUrl="http://107.23.89.76:8091/pools";
	private final  String couchBaseBucketName="syml";
	private final  String couchBaseBucketPassword="symL@0115";
	private final  String jotformApiKey="a337963c3dfaba4d38ea021fe6b457c0";
	private final long  submitReferalForm1Id=50233549962964l;
	private final long submitReferalForm2Id=50234255984963l;
	private final long applyNowForm=50264953649464l;
	private final long mortgageForm1aId = 50265290718961l;
private final long mortgageForm2Id = 50264983570965l;
private final long mortgageForm4Id = 50265051738958l;
private final long mortgageForm5Id = 50274398739973l;

String message="CONFIDENTIALITY: This e-mail message (including attachments, if any) is confidential and is intended only for the addressee. Any unauthorized use or disclosure is strictly prohibited. Disclosure of this e-mail to anyone other than the intended addressee does not constitute waiver of privilege. If you have received this communication in error, please notify us immediately and delete this. Thank you for your cooperation. This message has not been encrypted. Special arrangements can be made for encryption upon request. If you no longer wish to receive e-mail messages from Visdom Mortgage Solutions, please contact the sender. "+

	  "Visit our website at www.visdom.ca for information about our company and the services we provide. You can subscribe to Visdom Mortgage Solutions free electronic communications, or unsubscribe at any time.\n\n";
	 
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
public long getMortgageForm4Id() {
	return mortgageForm4Id;
}
public long getMortgageForm5Id() {
	return mortgageForm5Id;
}
	private final long mortgageForm3Id = 50083535689968l;
	
	public static int  propertyCounter;
	

public static int getPropertyCounter() {
		return propertyCounter;
	}
	public static void setPropertyCounter(int propertyCounter) {
		SymlConstant.propertyCounter = propertyCounter;
	}
public long getMortgageForm3Id() {
		return mortgageForm3Id;
	}
	public long getMortgageForm2Id() {
		return mortgageForm2Id;
	}

	public long getMortgageForm1aId() {
		return mortgageForm1aId;
	}
	public long getMortgageForm1bId() {
		return mortgageForm1bId;
	}
	public long getMortgageForm1cId() {
		return mortgageForm1cId;
	}
	private final long mortgageForm1bId = 50264938264965l;
	private final long mortgageForm1cId = 50264859730965l;
	private final String fromEmail="venkateshm384@gmail.com";
	private final String toEmail="dsdeepali6@gmail.com";
	private final String password="venky143";
	private final String smtpHost="smtp.gmail.com";
	private final String smtpPort="587";
	private final String  smtpAuth="true";
	private final String smtpTTLSEnabled="true";


	
	public String getOdooIP() {
		return odooIP;
	}
	public int getOdooPort() {
		return odooPort;
	}
	public String getOdooDB() {
		return odooDB;
	}
	public String getOdooUsername() {
		return odooUsername;
	}
	public String getOdoopassword() {
		return odoopassword;
	}
	public String getCouchBaseUrl() {
		return couchBaseUrl;
	}
	public String getCouchBaseBucketName() {
		return couchBaseBucketName;
	}
	public String getCouchBaseBucketPassword() {
		return couchBaseBucketPassword;
	}
	public String getJotformApiKey() {
		return jotformApiKey;
	}
	public long getSubmitReferalForm1Id() {
		return submitReferalForm1Id;
	}
	public long getSubmitReferalForm2Id() {
		return submitReferalForm2Id;
	}
	public String getFromEmail() {
		return fromEmail;
	}
	public String getToEmail() {
		return toEmail;
	}
	public String getPassword() {
		return password;
	}
	public String getSmtpHost() {
		return smtpHost;
	}
	public String getSmtpPort() {
		return smtpPort;
	}
	public String getSmtpAuth() {
		return smtpAuth;
	}
	public String getSmtpTTLSEnabled() {
		return smtpTTLSEnabled;
	}
	public long getApplyNowForm() {
		return applyNowForm;
	}
	
	
	
}
