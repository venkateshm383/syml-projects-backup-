package com.sendwithus.model;

public class SendMailUsingThreadclass extends Thread {

	
	String referralName;
	String referralEmail;
	
	String referralSoursceName;
	 public SendMailUsingThreadclass(String referralName, String referralEmail,
			String referralSoursceName) {
	
		this.referralName = referralName;
		this.referralEmail = referralEmail;
		this.referralSoursceName = referralSoursceName;
	}

	
	 public SendMailUsingThreadclass(){
		 
	 }
}
