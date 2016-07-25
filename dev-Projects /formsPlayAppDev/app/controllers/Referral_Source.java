package controllers;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "hr_applicant")
public class Referral_Source {

	@Id
	@GenericGenerator(name = "kaugen", strategy = "increment")
	@GeneratedValue(generator = "kaugen")
	@Column(name = "id")
	private int id;
	
	@Column(name="active")
	private Boolean active;

	@Column(name="create_date")
	private Date create_date=new Date();
	

	@Column(name = "name")
	private String name;

	@Column(name = "email_from")
	private String email_from;
	
	@Column(name="partner_mobile")
	private String partner_mobile;
	@Column(name="partner_phone")
	private String partner_phone;
	@Column(name="company")
	private String company;
	
	@Column(name="agreement_date")
	private Date agreement_date;
	
	
	@Column(name = "condidate_street")
	private String street;

	@Column(name = "condidate_street2")
	private String street2;

	@Column(name = "condidate_city")
	private String city;

	@Column(name = "condidate_postal_code")
	private String postal_code;
	@Column(name = "condidate_state_id")
	private Integer province;
	
	@Transient
	private String referrer;
	
	@Transient
	private String firstName;
	
	@Transient
	private String lastName;
	
	@Transient
	private String isTouchScreeDevice;
	@Transient
	private int referralExist;
	
	@Transient
	private String electronicSing;
	public String getElectronicSing() {
		return electronicSing;
	}







	public void setElectronicSing(String electronicSing) {
		this.electronicSing = electronicSing;
	}







	public int getReferralExist() {
		return referralExist;
	}







	public void setReferralExist(int referralExist) {
		this.referralExist = referralExist;
	}







	public String getIsTouchScreeDevice() {
		return isTouchScreeDevice;
	}







	public void setIsTouchScreeDevice(String isTouchScreeDevice) {
		this.isTouchScreeDevice = isTouchScreeDevice;
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







	public Boolean getActive() {
		return active;
	}







	public String getReferrer() {
		return referrer;
	}







	public void setReferrer(String referrer) {
		this.referrer = referrer;
	}



	@Transient
	private String ReferralAgreemetfile_id;

	public String getReferralAgreemetfile_id() {
		return ReferralAgreemetfile_id;
	}







	public void setReferralAgreemetfile_id(String referralAgreemetfile_id) {
		ReferralAgreemetfile_id = referralAgreemetfile_id;
	}



	@Transient
	private String nameOfTheBuilder;
	


	@Column(name="role")
	private String role;
	
	@Transient
	private String roleName;
	
	@Transient
	private String fullAddress;

	@Transient
	private String compensation;


	@Transient
	private String referralFullName;

	
	
	@Transient
	private String type_Referral;
	
	
	

	@Transient
	private int referralUrl;
	
	@Transient
	private String Submission_Date_Time1b;
	
	@Column(name="stage_id")
	private Integer stage_id;
	
	@Column(name="partner_id")
	private Integer partner_id;
	
	@Transient
	private int referral_Progress;
	
	@Transient
	private String latitude;
	
	




	public String getLatitude() {
		return latitude;
	}







	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}







	public String getIp() {
		return ip;
	}




	


	public void setIp(String ip) {
		this.ip = ip;
	}



	@Transient
	private String longitude;
	
	public String getLongitude() {
		return longitude;
	}







	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}



	@Column(name="sign_ip")
	private String ip;
	
	@Column(name="signature")
	private String signature;
	

	public String getPartner_phone() {
		return partner_phone;
	}


	public Boolean isActive() {
		return active;
	}







	public void setActive(Boolean active) {
		this.active = active;
	}






	public void setPartner_phone(String partner_phone) {
		this.partner_phone = partner_phone;
	}







	public String getSignature() {
		return signature;
	}







	public void setSignature(String signature) {
		this.signature = signature;
	}







	public int getId() {
		return id;
	}




	public void setId(int id) {
		this.id = id;
	}




	public String getName() {
		return name;
	}




	public void setName(String name) {
		this.name = name;
	}




	public String getEmail_from() {
		return email_from;
	}




	public void setEmail_from(String email_from) {
		this.email_from = email_from;
	}




	public String getRole() {
		return role;
	}




	public void setRole(String role) {
		this.role = role;
	}




	public String getRoleName() {
		return roleName;
	}




	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}





	public String getReferralFullName() {
		return referralFullName;
	}




	public void setReferralFullName(String referralFullName) {
		this.referralFullName = referralFullName;
	}




	public String getType_Referral() {
		return type_Referral;
	}




	public void setType_Referral(String type_Referral) {
		this.type_Referral = type_Referral;
	}




	public int getReferralUrl() {
		return referralUrl;
	}




	public void setReferralUrl(int referralUrl) {
		this.referralUrl = referralUrl;
	}




	public String getSubmission_Date_Time1b() {
		return Submission_Date_Time1b;
	}




	public void setSubmission_Date_Time1b(String submission_Date_Time1b) {
		Submission_Date_Time1b = submission_Date_Time1b;
	}




	public Integer getStage_id() {
		return stage_id;
	}




	public void setStage_id(Integer stage_id) {
		this.stage_id = stage_id;
	}




	public Integer getPartner_id() {
		return partner_id;
	}




	public void setPartner_id(Integer partner_id) {
		this.partner_id = partner_id;
	}




	public int getReferral_Progress() {
		return referral_Progress;
	}




	public void setReferral_Progress(int referral_Progress) {
		this.referral_Progress = referral_Progress;
	}




	public String getPartner_mobile() {
		return partner_mobile;
	}




	public void setPartner_mobile(String partner_mobile) {
		this.partner_mobile = partner_mobile;
	}




	public String getCompany() {
		return company;
	}




	public void setCompany(String company) {
		this.company = company;
	}




	public Date getAgreement_date() {
		return agreement_date;
	}




	public void setAgreement_date(Date agreement_date) {
		this.agreement_date = agreement_date;
	}




	public String getStreet() {
		return street;
	}




	public void setStreet(String street) {
		this.street = street;
	}




	public String getStreet2() {
		return street2;
	}




	public void setStreet2(String street2) {
		this.street2 = street2;
	}




	public String getCity() {
		return city;
	}




	public void setCity(String city) {
		this.city = city;
	}




	




	public String getPostal_code() {
		return postal_code;
	}




	public void setPostal_code(String postal_code) {
		this.postal_code = postal_code;
	}


	public String getCompensation() {
		return compensation;
	}




	public void setCompensation(String compensation) {
		this.compensation = compensation;
	}


	



	public Integer getProvince() {
		return province;
	}







	public void setProvince(Integer province) {
		this.province = province;
	}







	public String getFullAddress() {
		return fullAddress;
	}




	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}


	public String getNameOfTheBuilder() {
		return nameOfTheBuilder;
	}




	public void setNameOfTheBuilder(String nameOfTheBuilder) {
		this.nameOfTheBuilder = nameOfTheBuilder;
	}



	@Override
	public String toString() {
		return "Referral_Source [id=" + id + ", name=" + name + ", email_from="
				+ email_from + ", partner_mobile=" + partner_mobile
				+ ", company=" + company + ", agreement_date=" + agreement_date
				+ ", street=" + street + ", street2=" + street2 + ", city="
				+ city + ", postal_code=" + postal_code + ", province="
				+ province 
				+ ", referralFullName=" + referralFullName + ", type_Referral="
				+ type_Referral + ", referralUrl=" + referralUrl
				+ ", Submission_Date_Time1b=" + Submission_Date_Time1b
				+ ", stage_id=" + stage_id + ", partner_id=" + partner_id
				+ ", referral_Progress=" + referral_Progress + "]";
	}





	
	
}
