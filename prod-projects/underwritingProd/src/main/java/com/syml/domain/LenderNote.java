package com.syml.domain;

public class LenderNote {

	public String applicantName;
	public String category;
	public String content;
	
	public LenderNote(String applicantName, String category, String content) {
		super();
		this.applicantName = applicantName;
		this.category = category;
		this.content = content;
	}
	
	public LenderNote(){}
	
	public String getApplicantName() {
		return applicantName;
	}
	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
