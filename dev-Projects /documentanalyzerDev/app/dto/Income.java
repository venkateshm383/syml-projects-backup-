package dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Income {
	
	
	@Override
	public String toString() {
		return "Income [position=" + position + ", month=" + month
				+ ", annual_income=" + annual_income + ", property_id="
				+ property_id + ", historical=" + historical + ", industry="
				+ industry + ", business=" + business + ", name=" + name
				+ ", income_type=" + income_type + ", applicantId="
				+ applicantId + "]";
	}

		//position
		public String position;

		//month
		public Integer month;

		//annual_income
		//@ERP( name = "annual_income" )
		public String annual_income;

		//property_id
		//@ERP( name = "property_id" )
		public String property_id;
		
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
		public String income_type;
			
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

		
		public String getAnnual_income() {
			return annual_income;
		}

		public void setAnnual_income(String annual_income) {
			this.annual_income = annual_income;
		}

		public String getProperty_id() {
			return property_id;
		}

		public void setProperty_id(String property_id) {
			this.property_id = property_id;
		}

		public String getIncome_type() {
			return income_type;
		}

		public void setIncome_type(String income_type) {
			this.income_type = income_type;
		}

		public Integer getApplicantId() {
			return applicantId;
		}

		public void setApplicantId(Integer applicantId) {
			this.applicantId = applicantId;
		}


}
