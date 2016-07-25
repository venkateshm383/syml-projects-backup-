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
@Table(name = "crm_lead")
public class Lead {

	@Id
	@GenericGenerator(name = "kaugen", strategy = "increment")
	@GeneratedValue(generator = "kaugen")
	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "email_from")
	private String email_from;

	@Column(name = "stage_id")
	private int stage_id;

	@Column(name = "active")
	private boolean active=true;

	@Column(name = "partner_id")
	private int partner_id;

	@Column(name = "mobile")
	private String mobile;

	@Column(name = "work_phone")
	private String work_phone;

	@Column(name = "referred_source")
	private Integer referref_source;

	@Column(name = "street")
	private String street;

	@Column(name = "street2")
	private String street2;

	@Column(name = "city")
	private String city;

	@Column(name = "state_id")
	private Integer state_id;
	@Column(name = "postal_code")
	private String postal_code;
	
	@Column(name="country")
	private String country;
	
	
	@Transient
	private String form_Type;
	
	@Column(name="type")
	private String type="lead";
	
	@Transient
	private String firstName;
	
	@Transient
	private String lastName;
	
	@Column(name="user_id")
	private Integer user_id;
	
	@Column(name="create_date")
	private Date create_date=new Date();
	
	
	public Date getCreate_date() {
		return create_date;
	}

	public void setCreate_date(Date create_date) {
		this.create_date = create_date;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public Integer getCreate_uid() {
		return create_uid;
	}

	public void setCreate_uid(Integer create_uid) {
		this.create_uid = create_uid;
	}

	public Integer getHr_department_id() {
		return hr_department_id;
	}

	public void setHr_department_id(Integer hr_department_id) {
		this.hr_department_id = hr_department_id;
	}

	@Column(name="create_uid")
	private Integer create_uid;
	
	@Column(name="hr_department_id")
	private Integer	hr_department_id;
	
	

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

	public void setStage_id(int stage_id) {
		this.stage_id = stage_id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getForm_Type() {
		return form_Type;
	}

	public void setForm_Type(String form_Type) {
		this.form_Type = form_Type;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Transient
	private String Submission_Date_Time1b;
	
	@Transient
	private String referral_Source_First_Name;
	
	@Transient
	private String referral_Source_Last_Name;
	
	@Transient
	private String ip;
	
	@Transient
	private String referral_Source_Email; 
	
	@Transient
	private String referral_Source_Phone;
	
	public String getReferral_Source_Phone() {
		return referral_Source_Phone;
	}

	public void setReferral_Source_Phone(String referral_Source_Phone) {
		this.referral_Source_Phone = referral_Source_Phone;
	}

	public String getReferral_Source_First_Name() {
		return referral_Source_First_Name;
	}

	public void setReferral_Source_First_Name(String referral_Source_First_Name) {
		this.referral_Source_First_Name = referral_Source_First_Name;
	}

	public String getReferral_Source_Last_Name() {
		return referral_Source_Last_Name;
	}

	public void setReferral_Source_Last_Name(String referral_Source_Last_Name) {
		this.referral_Source_Last_Name = referral_Source_Last_Name;
	}


	
	

	public String getReferral_Source_Email() {
		return referral_Source_Email;
	}

	public void setReferral_Source_Email(String referral_Source_Email) {
		this.referral_Source_Email = referral_Source_Email;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPartner_id() {
		return partner_id;
	}

	public void setPartner_id(int partner_id) {
		this.partner_id = partner_id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getWork_phone() {
		return work_phone;
	}

	public void setWork_phone(String work_phone) {
		this.work_phone = work_phone;
	}

	public Integer getReferref_source() {
		return referref_source;
	}

	public void setReferref_source(Integer referref_source) {
		this.referref_source = referref_source;
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

	public Integer getState_id() {
		return state_id;
	}

	public void setState_id(Integer state_id) {
		this.state_id = state_id;
	}

	public String getPostal_code() {
		return postal_code;
	}

	public void setPostal_code(String postal_code) {
		this.postal_code = postal_code;
	}

	public String getSubmission_Date_Time1b() {
		return Submission_Date_Time1b;
	}

	public void setSubmission_Date_Time1b(String submission_Date_Time1b) {
		Submission_Date_Time1b = submission_Date_Time1b;
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

	public Integer getStage_id() {
		return stage_id;
	}

	public void setStage_id(Integer stage_id) {
		this.stage_id = stage_id;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "Lead [id=" + id + ", name=" + name + ", email_from="
				+ email_from + ", stage_id=" + stage_id + ", active=" + active
				+ ", partner_id=" + partner_id + ", mobile=" + mobile
				+ ", work_phone=" + work_phone + ", referref_source="
				+ referref_source + ", street=" + street + ", street2="
				+ street2 + ", city=" + city + ", state_id=" + state_id
				+ ", postal_code=" + postal_code + ", country=" + country
				+ ", form_Type=" + form_Type + ", type=" + type
				+ ", firstName=" + firstName + ", lastName=" + lastName
				+ ", user_id=" + user_id + ", create_date=" + create_date
				+ ", create_uid=" + create_uid + ", hr_department_id="
				+ hr_department_id + ", Submission_Date_Time1b="
				+ Submission_Date_Time1b + ", referral_Source_First_Name="
				+ referral_Source_First_Name + ", referral_Source_Last_Name="
				+ referral_Source_Last_Name + ", ip=" + ip
				+ ", referral_Source_Email=" + referral_Source_Email
				+ ", referral_Source_Phone=" + referral_Source_Phone + "]";
	}

	
	public Lead() {
		// TODO Auto-generated constructor stub
	}
}
