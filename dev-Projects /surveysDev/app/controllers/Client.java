package controllers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Client {
	
	private String applicantId;
	private String  opprtunityId;
	private String   applicantname;

	public String getApplicantname() {
		return applicantname;
	}
	public void setApplicantname(String applicantname) {
		this.applicantname = applicantname;
	}
	public String getApplicantId() {
		return applicantId;
	}
	public void setApplicantId(String applicantId) {
		this.applicantId = applicantId;
	}
	public String getOpprtunityId() {
		return opprtunityId;
	}
	public void setOpprtunityId(String opprtunityId) {
		this.opprtunityId = opprtunityId;
	}

}
