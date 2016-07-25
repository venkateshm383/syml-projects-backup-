package controllers;

import java.util.Date;

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
@Table(name = "applicant_address")
public class Address {
	
	@Override
	public String toString() {
		return "Address [id=" + id + ", applicant_id=" + applicant_id + ", name=" + name + ", street=" + street
				+ ", months=" + months + ", city=" + city + ", provience=" + provience + ", postalCode=" + postalCode
				+ ", movedIn=" + movedIn + ", fullAddress=" + fullAddress + ", totalMonths=" + totalMonths
				+ ", applicant=" + applicant + "]";
	}
	@Id
	@GenericGenerator(name = "kaugen", strategy = "increment")
	@GeneratedValue(generator = "kaugen")
	@Column(name = "id",unique = true)
	private Integer id;
	@Column(name="applicant_id")
	private Integer applicant_id;
	@Column(name = "name")
	private String name;
	@Column(name = "street")
	private String street;
	@Transient
	private int months;
	@Column(name = "city")
	private String city;
	@Column(name = "province")
	private String provience;
	@Column(name = "postal_code")
	private String postalCode;
	@Column(name = "date")
	private Date movedIn;
	
	@Transient
	private String fullAddress;
	@Transient
	private int totalMonths;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "applicant_id", nullable = false,insertable = false, updatable = false)
	private Applicant applicant;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvience() {
		return provience;
	}
	public void setProvience(String provience) {
		this.provience = provience;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public Date getMovedIn() {
		return movedIn;
	}
	public void setMovedIn(Date movedIn) {
		this.movedIn = movedIn;
	}
	public int getMonths() {
		return months;
	}
	public void setMonths(int months) {
		this.months = months;
	}
	public Applicant getApplicant() {
		return applicant;
	}
	public void setApplicant(Applicant applicant) {
		this.applicant = applicant;
	}
	public Integer getApplicant_id() {
		return applicant_id;
	}
	public void setApplicant_id(Integer applicant_id) {
		this.applicant_id = applicant_id;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFullAddress() {
		return fullAddress;
	}
	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}
	public int getTotalMonths() {
		return totalMonths;
	}
	public void setTotalMonths(int totalMonths) {
		this.totalMonths = totalMonths;
	}	

}
