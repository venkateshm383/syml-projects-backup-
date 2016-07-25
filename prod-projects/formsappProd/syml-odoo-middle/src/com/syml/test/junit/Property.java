package com.syml.test.junit;

public class Property {
	
	
	//property_id
		//@ERP( name = "property_id" )
		public String propertyId;

		//selling
		public Boolean selling;

		//annual_tax
		//@ERP( name = "annual_tax" )
		public Integer annualTax;

		//mo_condo_fees
		//@ERP( name = "mo_condo_fees" )
		public Integer moCondoFees;

		//name
		public String name;

		//value
		public Double value;

		//owed
		public Integer owed;

		/*********************************many2one type******************************/

		//many to one : mapping table : applicant_record(com.syml.domain.Applicant)
		//@ERP(name="applicant_id", type = OpenERPType.many2one )
		//@JsonIgnore
		public Integer applicantId;

		public String getPropertyId() {
			return propertyId;
		}

		public void setPropertyId(String propertyId) {
			this.propertyId = propertyId;
		}

		public Boolean getSelling() {
			return selling;
		}

		public void setSelling(Boolean selling) {
			this.selling = selling;
		}

		public Integer getAnnualTax() {
			return annualTax;
		}

		public void setAnnualTax(Integer annualTax) {
			this.annualTax = annualTax;
		}

		public Integer getMoCondoFees() {
			return moCondoFees;
		}

		public void setMoCondoFees(Integer moCondoFees) {
			this.moCondoFees = moCondoFees;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Double getValue() {
			return value;
		}

		public void setValue(Double value) {
			this.value = value;
		}

		public Integer getOwed() {
			return owed;
		}

		public void setOwed(Integer owed) {
			this.owed = owed;
		}

		public Integer getApplicantId() {
			return applicantId;
		}

		public void setApplicantId(Integer applicantId) {
			this.applicantId = applicantId;
		}

}
