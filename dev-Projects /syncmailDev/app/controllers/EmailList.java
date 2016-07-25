package controllers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmailList {
	
	String visdomemailid;
	String visdompassword;
	String key;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getVisdomemailid() {
		return visdomemailid;
	}
	public void setVisdomemailid(String visdomemailid) {
		this.visdomemailid = visdomemailid;
	}
	public String getVisdompassword() {
		return visdompassword;
	}
	public void setVisdompassword(String visdompassword) {
		this.visdompassword = visdompassword;
	}
	

}
