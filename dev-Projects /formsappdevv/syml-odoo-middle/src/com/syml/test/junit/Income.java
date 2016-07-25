package com.syml.test.junit;

public class Income {
	
	
	//position
		public String position;

		//month
		public Integer month;

		//annual_income
		//@ERP( name = "annual_income" )
		public String annualIncome;

		//property_id
		//@ERP( name = "property_id" )
		public String propertyID;
		
		//allow_duplex
		//@ERP( name = "historical" )
		public Boolean historical;
			
		//industry
		//@Column(length=9999)
		public String industry;

		//business
		public String business;

		/*********************************selection type******************************/
		//name
		//@ERP( type = OpenERPType.selection, name = "name" )
		public String name;

		//income_type
		//@ERP( type = OpenERPType.selection, name = "income_type" )
		public String typeOfIncome;
			
		/*********************************many2one type******************************/

		//many to one : mapping table : applicant_record(com.syml.domain.Applicant)
		//@ERP(name="applicant_id", type = OpenERPType.many2one )
		//@JsonIgnore
		public Integer applicantId;

		public String getPosition() {
			return position;
		}

		public void setPosition(String position) {
			this.position = position;
		}

		public Integer getMonth() {
			return month;
		}

		public void setMonth(Integer month) {
			this.month = month;
		}

		public String getAnnualIncome() {
			return annualIncome;
		}

		public void setAnnualIncome(String annualIncome) {
			this.annualIncome = annualIncome;
		}

		public String getPropertyID() {
			return propertyID;
		}

		public void setPropertyID(String propertyID) {
			this.propertyID = propertyID;
		}

		public Boolean getHistorical() {
			return historical;
		}

		public void setHistorical(Boolean historical) {
			this.historical = historical;
		}

		public String getIndustry() {
			return industry;
		}

		public void setIndustry(String industry) {
			this.industry = industry;
		}

		public String getBusiness() {
			return business;
		}

		public void setBusiness(String business) {
			this.business = business;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getTypeOfIncome() {
			return typeOfIncome;
		}

		public void setTypeOfIncome(String typeOfIncome) {
			this.typeOfIncome = typeOfIncome;
		}

		public Integer getApplicantId() {
			return applicantId;
		}

		public void setApplicantId(Integer applicantId) {
			this.applicantId = applicantId;
		}


}
