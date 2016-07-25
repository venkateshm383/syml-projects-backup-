package controllers;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "income_employer")
public class Income {

	@Id
	@GenericGenerator(name = "kaugen", strategy = "increment")
	@GeneratedValue(generator = "kaugen")
	@Column(name = "id",unique = true)
	private Integer id;
	
	
	// position
	@Column(name = "position")
	private String position;

	// month
	@Column(name = "month")
	private Integer month;

	// annual_income
	// @ERP( name = "annual_income" )
	@Column(name = "annual_income")
	private String annualIncome;
	
	// property_id
	// @ERP( name = "property_id" )
	@Column(name = "property_id")
	private String propertyID;

	// allow_duplex
	// @ERP( name = "historical" )
	@Column(name = "historical")
	private Boolean historical;

	// industry
	// @Column(length=9999)
	@Column(name = "industry")
	private String industry;

	// business
	@Column(name = "business")
	private String business;

	/********************************* selection type ******************************/
	// name
	// @ERP( type = OpenERPType.selection, name = "name" )
	private String name;
	
	// income_type
	// @ERP( type = OpenERPType.selection, name = "income_type" )
	@Column(name = "income_type")
	private String typeOfIncome;

	/********************************* many2one type ******************************/

	// many to one : mapping table : applicant_record(com.syml.domain.Applicant)
	// @ERP(name="applicant_id", type = OpenERPType.many2one )
	// @JsonIgnore
	@Column(name="applicant_id")
	private Integer applicantId;
	
	@Column(name = "supplementary")
	private Boolean supplementary;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "applicant_id", nullable = false,insertable = false, updatable = false)
	private Applicant applicant;

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

	public Boolean getSupplementary() {
		return supplementary;
	}

	public void setSupplementary(Boolean supplementary) {
		this.supplementary = supplementary;
	}

	public Applicant getApplicant() {
		return applicant;
	}

	public void setApplicant(Applicant applicant) {
		this.applicant = applicant;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Income [id=" + id + ", position=" + position + ", month="
				+ month + ", annualIncome=" + annualIncome + ", propertyID="
				+ propertyID + ", historical=" + historical + ", industry="
				+ industry + ", business=" + business + ", name=" + name
				+ ", typeOfIncome=" + typeOfIncome + ", applicantId="
				+ applicantId + ", supplementary=" + supplementary
				+ ", applicant=" + applicant + "]";
	}
	

}
