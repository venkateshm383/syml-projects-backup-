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
@Table(name = "applicant_mortgage")
public class Mortgages {
	@Id
	@GenericGenerator(name = "kaugen", strategy = "increment")
	@GeneratedValue(generator = "kaugen")
	@Column(name = "id",unique = true)
	private Integer id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "applicant_id", nullable = false,insertable = false, updatable = false)
	private Applicant applicant;
	@Column(name="monthly_rent")
	private Integer monthlyRent ;
	@Column(name="selling")
	private Boolean selling;
	@Column(name="property_id")
	private String propertyId;
	@Column(name="applicant_id")
	private Integer applicantId;
	
	public Mortgages() {
		// TODO Auto-generated constructor stub
	}

	public Mortgages(Integer id, Applicant applicant, Integer montylyRent,
			Boolean selling, String propertyId, Integer applicantId) {
		super();
		this.id = id;
		this.applicant = applicant;
		this.setMonthlyRent(montylyRent);
		this.selling = selling;
		this.propertyId = propertyId;
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

	public Boolean getSelling() {
		return selling;
	}

	public void setSelling(Boolean selling) {
		this.selling = selling;
	}

	public String getPropertyId() {
		return propertyId;
	}

	public void setPropertyId(String propertyId) {
		this.propertyId = propertyId;
	}

	public Integer getApplicantId() {
		return applicantId;
	}

	public void setApplicantId(Integer applicantId) {
		this.applicantId = applicantId;
	}

	@Override
	public String toString() {
		return "Mortgages [id=" + id + ", applicant=" + applicant
				+ ", montylyRent=" + getMonthlyRent() + ", selling=" + selling
				+ ", propertyId=" + propertyId + ", applicantId=" + applicantId
				+ "]";
	}

	public Integer getMonthlyRent() {
		return monthlyRent;
	}

	public void setMonthlyRent(Integer monthlyRent) {
		this.monthlyRent = monthlyRent;
	}
	
	
}
