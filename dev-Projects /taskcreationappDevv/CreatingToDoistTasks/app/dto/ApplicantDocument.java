package dto;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplicantDocument {

	
	public String firstName;
	public String lastName;
	public String emailAddress;
	public Set<String>  documents;
	public Set<String>  applicantCreditDocuments;
	public Set<String>  co_applicantCreditDocuments;

	public Set<String> getApplicantCreditDocuments() {
		return applicantCreditDocuments;
	}

	public void setApplicantCreditDocuments(Set<String> applicantCreditDocuments) {
		this.applicantCreditDocuments = applicantCreditDocuments;
	}

	public Set<String> getCo_applicantCreditDocuments() {
		return co_applicantCreditDocuments;
	}

	public void setCo_applicantCreditDocuments(
			Set<String> co_applicantCreditDocuments) {
		this.co_applicantCreditDocuments = co_applicantCreditDocuments;
	}

	public String co_firstName;
	public String co_lastName;
	public String co_emailAddress;
	
	public Set<String>  co_documents;
	
	public Set<String> propertyDocments;
	public Set<String> downPayments;
	
	public String opprtunityId;
	
	public String lendingGoal;
	//public String opprtunityUrl;
	//public String doclisturl;

	public String getLendingGoal() {
		return lendingGoal;
	}

	public void setLendingGoal(String lendingGoal) {
		this.lendingGoal = lendingGoal;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Set<String> getDocuments() {
		return documents;
	}

	public void setDocuments(Set<String> documents) {
		this.documents = documents;
	}

	public String getCo_firstName() {
		return co_firstName;
	}

	public void setCo_firstName(String co_firstName) {
		this.co_firstName = co_firstName;
	}

	public String getCo_lastName() {
		return co_lastName;
	}

	public void setCo_lastName(String co_lastName) {
		this.co_lastName = co_lastName;
	}

	public String getCo_emailAddress() {
		return co_emailAddress;
	}

	public void setCo_emailAddress(String co_emailAddress) {
		this.co_emailAddress = co_emailAddress;
	}

	public Set<String> getCo_documents() {
		return co_documents;
	}

	public void setCo_documents(Set<String> co_documents) {
		this.co_documents = co_documents;
	}

	public Set<String> getPropertyDocments() {
		return propertyDocments;
	}

	public void setPropertyDocments(Set<String> propertyDocments) {
		this.propertyDocments = propertyDocments;
	}

	public Set<String> getDownPayments() {
		return downPayments;
	}

	public void setDownPayments(Set<String> downPayments) {
		this.downPayments = downPayments;
	}

	public String getOpprtunityId() {
		return opprtunityId;
	}

	public void setOpprtunityId(String opprtunityId) {
		this.opprtunityId = opprtunityId;
	}

	
	
	/*public String getOpprtunityUrl() {
		return opprtunityUrl;
	}
	public void setOpprtunityUrl(String opprtunityUrl) {
		this.opprtunityUrl = opprtunityUrl;
	}
	public String getDoclisturl() {
		return doclisturl;
	}
	public void setDoclisturl(String doclisturl) {
		this.doclisturl = doclisturl;
	}*/
	
	
	
}
