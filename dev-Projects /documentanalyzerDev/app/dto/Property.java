package dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Property {
	
	
	//property_id
		//@ERP( name = "property_id" )
		public String property_id;

		@Override
		public String toString() {
			return "Property [property_id=" + property_id + ", selling="
					+ selling + ", annual_tax=" + annual_tax
					+ ", mortgageYesNo=" + mortgageYesNo + ", rentalProperty="
					+ rentalProperty + ", mo_condo_fees=" + mo_condo_fees
					+ ", name=" + name + ", value=" + value + ", owed=" + owed
					+ ", applicantId=" + applicantId + "]";
		}

		//selling
		public Boolean selling;

		//annual_tax
		//@ERP( name = "annual_tax" )
		public Integer annual_tax;
		public String mortgageYesNo;

		public String getMortgageYesNo() {
			return mortgageYesNo;
		}

		public void setMortgageYesNo(String mortgageYesNo) {
			this.mortgageYesNo = mortgageYesNo;
		}

		public String rentalProperty;
		public String getRentalProperty() {
			return rentalProperty;
		}

		public void setRentalProperty(String rentalProperty) {
			this.rentalProperty = rentalProperty;
		}

		//mo_condo_fees
		//@ERP( name = "mo_condo_fees" )
		public Integer mo_condo_fees;

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

		

		public String getProperty_id() {
			return property_id;
		}

		public void setProperty_id(String property_id) {
			this.property_id = property_id;
		}

		public Integer getAnnual_tax() {
			return annual_tax;
		}

		public void setAnnual_tax(Integer annual_tax) {
			this.annual_tax = annual_tax;
		}

		public Integer getMo_condo_fees() {
			return mo_condo_fees;
		}

		public void setMo_condo_fees(Integer mo_condo_fees) {
			this.mo_condo_fees = mo_condo_fees;
		}

		public Boolean getSelling() {
			return selling;
		}

		public void setSelling(Boolean selling) {
			this.selling = selling;
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
