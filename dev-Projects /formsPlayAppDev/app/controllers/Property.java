package controllers;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "applicant_property")
public class Property {

	
	
	@Id
	@GenericGenerator(name = "kaugen", strategy = "increment")
	@GeneratedValue(generator = "kaugen")
	@Column(name = "id",unique = true)
	private Integer id;
	
	// name
	@Column(name="name")
	private String name;
	
	// value
	@Column(name="value")
	private Double value;
		
	// owed
	@Column(name="owed")
	private Integer owed;
	
	// annual_tax
	// @ERP( name = "annual_tax" )
	@Column(name="annual_tax")
	private Integer annualTax;
	
	// mo_condo_fees
	// @ERP( name = "mo_condo_fees" )
	@Column(name="mo_condo_fees")
	private Integer moCondoFees;
		
	// property_id
	// @ERP( name = "property_id" )
	@Column(name="property_id")
	private String propertyId;
	
	// selling
	@Column(name="selling")
	private Boolean selling;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "applicant_id", nullable = false,insertable = false, updatable = false)
	private Applicant applicant;
	
	@Transient
	private String mortgageYesNo;
	@Transient
	private String rentalProperty;
	@Transient
	private int mortgageValue;
	
	@Transient
	private int monthlyRent;
	
	@Transient
	private String ownwerShip;

	public int getMonthlyRent() {
		return monthlyRent;
	}

	public String getOwnwerShip() {
		return ownwerShip;
	}

	public void setOwnwerShip(String ownwerShip) {
		this.ownwerShip = ownwerShip;
	}

	public void setMonthlyRent(int monthlyRent) {
		this.monthlyRent = monthlyRent;
	}

	public int getMortgageValue() {
		return mortgageValue;
	}

	public void setMortgageValue(int mortgageValue) {
		this.mortgageValue = mortgageValue;
	}

	/********************************* many2one type *********************************/
	
	// many to one : mapping table : applicant_record(com.syml.domain.Applicant)
	// @ERP(name="applicant_id", type = OpenERPType.many2one )
	// @JsonIgnore
	@Column(name="applicant_id")
	private Integer applicantId;

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Applicant getApplicant() {
		return applicant;
	}

	public void setApplicant(Applicant applicant) {
		this.applicant = applicant;
	}
	
	public String getRentalProperty() {
		return rentalProperty;
	}

	public void setRentalProperty(String rentalProperty) {
		this.rentalProperty = rentalProperty;
	}
	
	public String getMortgageYesNo() {
		return mortgageYesNo;
	}

	public void setMortgageYesNo(String mortgageYesNo) {
		this.mortgageYesNo = mortgageYesNo;
	}


}
