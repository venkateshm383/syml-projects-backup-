package controllers;

public class Applicants {

	
	private String applicantFirstName;
	private String applicantlastName;
	private String applicantEmail;
	private String coApplicantEmail;
	private String applicantId;

	public String getApplicantId() {
		return applicantId;
	}
	public void setApplicantId(String applicantId) {
		this.applicantId = applicantId;
	}
	public String getApplicantEmail() {
		return applicantEmail;
	}
	public void setApplicantEmail(String applicantEmail) {
		this.applicantEmail = applicantEmail;
	}
	public String getCoApplicantEmail() {
		return coApplicantEmail;
	}
	public void setCoApplicantEmail(String coApplicantEmail) {
		this.coApplicantEmail = coApplicantEmail;
	}
	private String coApplicantFirstName;
	private String coApplicantLastName;
	public String getApplicantFirstName() {
		return applicantFirstName;
	}
	public void setApplicantFirstName(String applicantFirstName) {
		this.applicantFirstName = applicantFirstName;
	}
	public String getApplicantlastName() {
		return applicantlastName;
	}
	public void setApplicantlastName(String applicantlastName) {
		this.applicantlastName = applicantlastName;
	}
	public String getCoApplicantFirstName() {
		return coApplicantFirstName;
	}
	public void setCoApplicantFirstName(String coApplicantFirstName) {
		this.coApplicantFirstName = coApplicantFirstName;
	}
	public String getCoApplicantLastName() {
		return coApplicantLastName;
	}
	public void setCoApplicantLastName(String coApplicantLastName) {
		this.coApplicantLastName = coApplicantLastName;
	}
	
}
