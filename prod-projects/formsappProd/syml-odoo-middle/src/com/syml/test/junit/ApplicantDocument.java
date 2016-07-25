package com.syml.test.junit;

import java.util.Set;

public class ApplicantDocument {

	
	public String firstName;
	public String lastName;
	public String emailAddress;
	public String letterOfEmployee;
	public String paystub;
	public String OpprtunityId;
	public String opprtunityUrl;
	public String doclisturl;

public Set<String>    documents;
	
	public String Co_firstName;
	public String Co_lastName;
	public String Co_emailAddress;
	
	public Set<String>    Co_documents;
	public Set<String> propertyDocments;
	public Set<String> downPayments;
	/*public String _2012_NOA;
	public String _2013_NOA;
	public String _2014_NOA;
	public String Confirmation_of_Taxes_Paid;
	public String Copy_of_Photo_ID;
	public String Divorce_Separation_Agreement;
	public String Child_Tax_Credit;
	public String Mortgage_Statement;
	public String Property_Tax_Assessment;*/
	public String getOpprtunityId() {
		return OpprtunityId;
	}
	public void setOpprtunityId(String opprtunityId) {
		OpprtunityId = opprtunityId;
	}
	public String getOpprtunityUrl() {
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
	}
	public String getLetterOfEmployee() {
		return letterOfEmployee;
	}
	public void setLetterOfEmployee(String letterOfEmployee) {
		this.letterOfEmployee = letterOfEmployee;
	}
	public String getPaystub() {
		return paystub;
	}
	public void setPaystub(String paystub) {
		this.paystub = paystub;
	}
	/*public String get_2012_NOA() {
		return _2012_NOA;
	}
	public void set_2012_NOA(String _2012_NOA) {
		this._2012_NOA = _2012_NOA;
	}
	public String get_2013_NOA() {
		return _2013_NOA;
	}
	public void set_2013_NOA(String _2013_NOA) {
		this._2013_NOA = _2013_NOA;
	}
	public String get_2014_NOA() {
		return _2014_NOA;
	}
	public void set_2014_NOA(String _2014_NOA) {
		this._2014_NOA = _2014_NOA;
	}
	public String getConfirmation_of_Taxes_Paid() {
		return Confirmation_of_Taxes_Paid;
	}
	public void setConfirmation_of_Taxes_Paid(String confirmation_of_Taxes_Paid) {
		Confirmation_of_Taxes_Paid = confirmation_of_Taxes_Paid;
	}
	public String getCopy_of_Photo_ID() {
		return Copy_of_Photo_ID;
	}
	public void setCopy_of_Photo_ID(String copy_of_Photo_ID) {
		Copy_of_Photo_ID = copy_of_Photo_ID;
	}
	public String getDivorce_Separation_Agreement() {
		return Divorce_Separation_Agreement;
	}
	public void setDivorce_Separation_Agreement(String divorce_Separation_Agreement) {
		Divorce_Separation_Agreement = divorce_Separation_Agreement;
	}
	public String getChild_Tax_Credit() {
		return Child_Tax_Credit;
	}
	public void setChild_Tax_Credit(String child_Tax_Credit) {
		Child_Tax_Credit = child_Tax_Credit;
	}
	public String getMortgage_Statement() {
		return Mortgage_Statement;
	}
	public void setMortgage_Statement(String mortgage_Statement) {
		Mortgage_Statement = mortgage_Statement;
	}
	public String getProperty_Tax_Assessment() {
		return Property_Tax_Assessment;
	}
	public void setProperty_Tax_Assessment(String property_Tax_Assessment) {
		Property_Tax_Assessment = property_Tax_Assessment;
	}*/
	
	public String getCo_firstName() {
		return Co_firstName;
	}
	public void setCo_firstName(String co_firstName) {
		Co_firstName = co_firstName;
	}
	public String getCo_lastName() {
		return Co_lastName;
	}
	public void setCo_lastName(String co_lastName) {
		Co_lastName = co_lastName;
	}
	public String getCo_emailAddress() {
		return Co_emailAddress;
	}
	public void setCo_emailAddress(String co_emailAddress) {
		Co_emailAddress = co_emailAddress;
	}
	public Set<String> getCo_documents() {
		return Co_documents;
	}
	public void setCo_documents(Set<String> co_documents) {
		Co_documents = co_documents;
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
	
}
