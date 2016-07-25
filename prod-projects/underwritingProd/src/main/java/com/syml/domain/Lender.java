package com.syml.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.syml.domain.BaseBean;

import javax.persistence.Column;

import com.syml.generator.domain.annotation.ERP;

import java.util.Date;

import javax.persistence.Temporal;

import java.util.List;

import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlElement;
import javax.persistence.Transient;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import com.syml.generator.domain.annotation.ERP.OpenERPType;

import java.util.ArrayList;
/**
* mapping open erp object : res.partner
* @author .x.m.
*
*/
@XmlRootElement
@Entity(name="res_partner")
public class Lender extends BaseBean {
	//has_image
	@ERP( name = "has_image" )
	public Boolean hasImage;

	//street
	public String street;

	//customer
	public Boolean customer;

	//city
	public String city;

	//underwriting_office_street2
	@ERP( name = "underwriting_office_street2" )
	public String underwritingOfficeStreet2;

	//spouse
	public String spouse;

	//q3_volume
	@ERP( name = "q3_volume" )
	public Double q3Volume;

	//rolling_12Mo_volume
	@ERP( name = "rolling_12Mo_volume" )
	public Double rolling12MoVolume;

	//july_volume
	@ERP( name = "july_volume" )
	public Double julyVolume;

	//underwriting_office_website
	@ERP( name = "underwriting_office_website" )
	public String underwritingOfficeWebsite;

	//supplier
	public Boolean supplier;

	//middle_name
	@ERP( name = "middle_name" )
	public String middleName;

	//image
	@ERP( name = "image", type=OpenERPType.bytes )
	@XmlElement
	public String image;

	//aug_volume
	@ERP( name = "aug_volume" )
	public Double augVolume;

	//work_phone
	@ERP( name = "work_phone" )
	public String workPhone;

	//dec_volume
	@ERP( name = "dec_volume" )
	public Double decVolume;

	//last_name
	@ERP( name = "last_name" )
	public String lastName;

	//oct_volume
	@ERP( name = "oct_volume" )
	public Double octVolume;

	//comment
	@Column(length=9999)
	public String comment;

	//message_is_follower
	@ERP( name = "message_is_follower" )
	public Boolean messageIsFollower;

	//feb_volume
	@ERP( name = "feb_volume" )
	public Double febVolume;

	//underwriting_office_phone
	@ERP( name = "underwriting_office_phone" )
	public Integer underwritingOfficePhone;

	//underwriting_office_mobile
	@ERP( name = "underwriting_office_mobile" )
	public Integer underwritingOfficeMobile;

	//date
	@Temporal(TemporalType.TIMESTAMP)
	public Date date;

	//mar_volume
	@ERP( name = "mar_volume" )
	public Double marVolume;

	//newclientlead
	public Boolean newclientlead;

	//opt_out
	@ERP( name = "opt_out" )
	public Boolean optOut;

	//signup_token
	@ERP( name = "signup_token" )
	public String signupToken;

	//underwriting_office_street
	@ERP( name = "underwriting_office_street" )
	public String underwritingOfficeStreet;

	//signup_url
	@ERP( name = "signup_url" )
	public String signupUrl;

	//isupdatedtoua
	public Boolean isupdatedtoua;

	//opportunity_count
	@ERP( name = "opportunity_count" )
	public Integer opportunityCount;

	//ref
	public String ref;

	//purchase_order_count
	@ERP( name = "purchase_order_count" )
	public Integer purchaseOrderCount;

	//sin
	public String sin;

	//underwriting_office_city
	@ERP( name = "underwriting_office_city" )
	public String underwritingOfficeCity;

	//message_unread
	@ERP( name = "message_unread" )
	public Boolean messageUnread;

	//ean13
	@ERP( name = "ean13" )
	public String ean13;

	//sept_volume
	@ERP( name = "sept_volume" )
	public Double septVolume;

	//phone
	public String phone;

	//underwriting_office_work_phone
	@ERP( name = "underwriting_office_work_phone" )
	public Integer underwritingOfficeWorkPhone;

	//apr_volume
	@ERP( name = "apr_volume" )
	public Double aprVolume;

	//atmail_contact
	@ERP( name = "atmail_contact" )
	public Boolean atmailContact;

	//function
	public String function;

	//credit_limit
	@ERP( name = "credit_limit" )
	public Double creditLimit;

	//signup_valid
	@ERP( name = "signup_valid" )
	public Boolean signupValid;

	//underwriting_office_zip
	@ERP( name = "underwriting_office_zip" )
	public String underwritingOfficeZip;

	//jan_volume
	@ERP( name = "jan_volume" )
	public Double janVolume;

	//email_personal
	@ERP( name = "email_personal" )
	public String emailPersonal;

	//image_medium
	@ERP( name = "image_medium", type=OpenERPType.bytes )
	@XmlElement
	public String imageMedium;

	//fax
	public String fax;

	//signup_expiration
	@ERP( name = "signup_expiration" )
	public Date signupExpiration;

	//signup_type
	@ERP( name = "signup_type" )
	public String signupType;

	//june_volume
	@ERP( name = "june_volume" )
	public Double juneVolume;

	//q2_volume
	@ERP( name = "q2_volume" )
	public Double q2Volume;

	//email
	public String email;

	//q4_volume
	@ERP( name = "q4_volume" )
	public Double q4Volume;

	//may_volume
	@ERP( name = "may_volume" )
	public Double mayVolume;

	//underwriting_office_email
	@ERP( name = "underwriting_office_email" )
	public String underwritingOfficeEmail;

	//active
	public Boolean active;

	//newduallead
	public Boolean newduallead;

	//mobile
	public String mobile;

	//nov_volume
	@ERP( name = "nov_volume" )
	public Double novVolume;

	//debit_limit
	@ERP( name = "debit_limit" )
	public Double debitLimit;

	//last_reconciliation_date
	@ERP( name = "last_reconciliation_date" )
	public Date lastReconciliationDate;

	//vat
	public String vat;

	//ytd_volume
	@ERP( name = "ytd_volume" )
	public Double ytdVolume;

	//image_small
	@ERP( name = "image_small", type=OpenERPType.bytes )
	@XmlElement
	public String imageSmall;

	//vat_subjected
	@ERP( name = "vat_subjected" )
	public Boolean vatSubjected;

	//po_box
	@ERP( name = "po_box" )
	public String poBox;

	//q1_volume
	@ERP( name = "q1_volume" )
	public Double q1Volume;

	//meeting_count
	@ERP( name = "meeting_count" )
	public Integer meetingCount;

	//name
	public String name;

	//birthdate
	@Temporal(TemporalType.TIMESTAMP)
	public Date birthdate;

	//contact_address
	@ERP( name = "contact_address" )
	public String contactAddress;

	//employee
	public Boolean employee;

	//street2
	@ERP( name = "street2" )
	public String street2;

	//zip
	public String zip;

	//newhrlead
	public Boolean newhrlead;

	//tz_offset
	@ERP( name = "tz_offset" )
	public String tzOffset;

	//website
	public String website;

	//use_parent_address
	@ERP( name = "use_parent_address" )
	public Boolean useParentAddress;

	//underwriting_office_fax
	@ERP( name = "underwriting_office_fax" )
	public String underwritingOfficeFax;

	//color
	public Integer color;

	//is_company
	@ERP( name = "is_company" )
	public Boolean isCompany;

	//message_summary
	@Column(length=9999)
	@ERP( name = "message_summary" )
	public String messageSummary;

	/*********************************selection type******************************/
	//preferred_phone
	@ERP( type = OpenERPType.selection, name = "preferred_phone" )
	public String preferredPhone;

	//tz
	@ERP( type = OpenERPType.selection, name = "tz" )
	public String tz;

	//type
	@ERP( type = OpenERPType.selection, name = "type" )
	public String type;

	//province
	@ERP( type = OpenERPType.selection, name = "province" )
	public String province;

	//lang
	@ERP( type = OpenERPType.selection, name = "lang" )
	public String lang;

	//notification_email_send
	@ERP( type = OpenERPType.selection, name = "notification_email_send" )
	public String notificationEmailSend;

	//addr_type
	@ERP( type = OpenERPType.selection, name = "addr_type" )
	public String addrType;

	//bonus_comp_period
	@ERP( type = OpenERPType.selection, name = "bonus_comp_period" )
	public String bonusCompPeriod;

	/*********************************one2many type******************************/

	//many to one : mapping table : res_partner(com.syml.domain.Lender)-->parent_id
	@ERP(name="child_ids", type = OpenERPType.one2many )
	@JsonIgnore
	@Transient
	public List<Lender> childIds = new ArrayList<Lender>();

	//many to one : mapping table : crm_lead(com.syml.domain.Opportunity)-->partner_id
	@ERP(name="opportunity_ids", type = OpenERPType.one2many )
	@JsonIgnore
	@Transient
	public List<Opportunity> opportunityIds = new ArrayList<Opportunity>();

	/*********************************many2one type******************************/

	//many to one : mapping table : res_partner(com.syml.domain.Lender)
	@ERP(name="parent_id", type = OpenERPType.many2one )
	@JsonIgnore
	public Integer parentId;

	//many to one : mapping table : res_partner(com.syml.domain.Lender)
	@ERP(name="underwriter", type = OpenERPType.many2one )
	@JsonIgnore
	public Integer underwriter;

	//many to one : mapping table : product_product(com.syml.domain.Product)
	@ERP(name="lender_id", type = OpenERPType.many2one )
	@JsonIgnore
	public Integer lenderId;

	//many to one : mapping table : res_partner(com.syml.domain.Lender)
	@ERP(name="lender_business_development_manager", type = OpenERPType.many2one )
	@JsonIgnore
	public Integer lenderBusinessDevelopmentManager;

	//many to one : mapping table : res_partner(com.syml.domain.Lender)
	@ERP(name="lender_credit_admin", type = OpenERPType.many2one )
	@JsonIgnore
	public Integer lenderCreditAdmin;

	//many to one : mapping table : res_partner(com.syml.domain.Lender)
	@ERP(name="referredby", type = OpenERPType.many2one )
	@JsonIgnore
	public Integer referredby;

	public Lender(){}

	public Lender(Integer lender) {
		this.setId(lender);
	}

	public void setHasImage(Boolean hasImage){
		this.hasImage = hasImage;
	}

	public Boolean getHasImage(){
		return this.hasImage;
	}

	public void setStreet(String street){
		this.street = street;
	}

	public String getStreet(){
		return this.street;
	}

	public void setCustomer(Boolean customer){
		this.customer = customer;
	}

	public Boolean getCustomer(){
		return this.customer;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getCity(){
		return this.city;
	}

	public void setUnderwritingOfficeStreet2(String underwritingOfficeStreet2){
		this.underwritingOfficeStreet2 = underwritingOfficeStreet2;
	}

	public String getUnderwritingOfficeStreet2(){
		return this.underwritingOfficeStreet2;
	}

	public void setSpouse(String spouse){
		this.spouse = spouse;
	}

	public String getSpouse(){
		return this.spouse;
	}

	public void setQ3Volume(Double q3Volume){
		this.q3Volume = q3Volume;
	}

	public Double getQ3Volume(){
		return this.q3Volume;
	}

	public void setRolling12MoVolume(Double rolling12MoVolume){
		this.rolling12MoVolume = rolling12MoVolume;
	}

	public Double getRolling12MoVolume(){
		return this.rolling12MoVolume;
	}

	public void setJulyVolume(Double julyVolume){
		this.julyVolume = julyVolume;
	}

	public Double getJulyVolume(){
		return this.julyVolume;
	}

	public void setUnderwritingOfficeWebsite(String underwritingOfficeWebsite){
		this.underwritingOfficeWebsite = underwritingOfficeWebsite;
	}

	public String getUnderwritingOfficeWebsite(){
		return this.underwritingOfficeWebsite;
	}

	public void setSupplier(Boolean supplier){
		this.supplier = supplier;
	}

	public Boolean getSupplier(){
		return this.supplier;
	}

	public void setParentId(Integer parentId){
		this.parentId = parentId;
	}

	public Integer getParentId(){
		return this.parentId;
	}

	public void setMiddleName(String middleName){
		this.middleName = middleName;
	}

	public String getMiddleName(){
		return this.middleName;
	}

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return this.image;
	}

	public void setAugVolume(Double augVolume){
		this.augVolume = augVolume;
	}

	public Double getAugVolume(){
		return this.augVolume;
	}

	public void setWorkPhone(String workPhone){
		this.workPhone = workPhone;
	}

	public String getWorkPhone(){
		return this.workPhone;
	}

	public void setDecVolume(Double decVolume){
		this.decVolume = decVolume;
	}

	public Double getDecVolume(){
		return this.decVolume;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getLastName(){
		return this.lastName;
	}

	public void setOctVolume(Double octVolume){
		this.octVolume = octVolume;
	}

	public Double getOctVolume(){
		return this.octVolume;
	}

	public void setComment(String comment){
		this.comment = comment;
	}

	public String getComment(){
		return this.comment;
	}

	public void setMessageIsFollower(Boolean messageIsFollower){
		this.messageIsFollower = messageIsFollower;
	}

	public Boolean getMessageIsFollower(){
		return this.messageIsFollower;
	}

	public void setFebVolume(Double febVolume){
		this.febVolume = febVolume;
	}

	public Double getFebVolume(){
		return this.febVolume;
	}

	public void setUnderwritingOfficePhone(Integer underwritingOfficePhone){
		this.underwritingOfficePhone = underwritingOfficePhone;
	}

	public Integer getUnderwritingOfficePhone(){
		return this.underwritingOfficePhone;
	}

	public void setUnderwritingOfficeMobile(Integer underwritingOfficeMobile){
		this.underwritingOfficeMobile = underwritingOfficeMobile;
	}

	public Integer getUnderwritingOfficeMobile(){
		return this.underwritingOfficeMobile;
	}

	public void setDate(Date date){
		this.date = date;
	}

	public Date getDate(){
		return this.date;
	}

	public void setMarVolume(Double marVolume){
		this.marVolume = marVolume;
	}

	public Double getMarVolume(){
		return this.marVolume;
	}

	public void setNewclientlead(Boolean newclientlead){
		this.newclientlead = newclientlead;
	}

	public Boolean getNewclientlead(){
		return this.newclientlead;
	}

	public void setOptOut(Boolean optOut){
		this.optOut = optOut;
	}

	public Boolean getOptOut(){
		return this.optOut;
	}

	public void setSignupToken(String signupToken){
		this.signupToken = signupToken;
	}

	public String getSignupToken(){
		return this.signupToken;
	}

	public void setUnderwritingOfficeStreet(String underwritingOfficeStreet){
		this.underwritingOfficeStreet = underwritingOfficeStreet;
	}

	public String getUnderwritingOfficeStreet(){
		return this.underwritingOfficeStreet;
	}

	public void setPreferredPhone(String preferredPhone){
		this.preferredPhone = preferredPhone;
	}

	public String getPreferredPhone(){
		return this.preferredPhone;
	}

	public void setSignupUrl(String signupUrl){
		this.signupUrl = signupUrl;
	}

	public String getSignupUrl(){
		return this.signupUrl;
	}

	public void setIsupdatedtoua(Boolean isupdatedtoua){
		this.isupdatedtoua = isupdatedtoua;
	}

	public Boolean getIsupdatedtoua(){
		return this.isupdatedtoua;
	}

	public void setOpportunityCount(Integer opportunityCount){
		this.opportunityCount = opportunityCount;
	}

	public Integer getOpportunityCount(){
		return this.opportunityCount;
	}

	public void setRef(String ref){
		this.ref = ref;
	}

	public String getRef(){
		return this.ref;
	}

	public void setPurchaseOrderCount(Integer purchaseOrderCount){
		this.purchaseOrderCount = purchaseOrderCount;
	}

	public Integer getPurchaseOrderCount(){
		return this.purchaseOrderCount;
	}

	public void setTz(String tz){
		this.tz = tz;
	}

	public String getTz(){
		return this.tz;
	}

	public void setSin(String sin){
		this.sin = sin;
	}

	public String getSin(){
		return this.sin;
	}

	public void setUnderwritingOfficeCity(String underwritingOfficeCity){
		this.underwritingOfficeCity = underwritingOfficeCity;
	}

	public String getUnderwritingOfficeCity(){
		return this.underwritingOfficeCity;
	}

	public void setMessageUnread(Boolean messageUnread){
		this.messageUnread = messageUnread;
	}

	public Boolean getMessageUnread(){
		return this.messageUnread;
	}

	public void setEan13(String ean13){
		this.ean13 = ean13;
	}

	public String getEan13(){
		return this.ean13;
	}

	public void setChildIds(List<Lender> childIds){
		this.childIds = childIds;
	}

	public List<Lender> getChildIds(){
		return this.childIds;
	}

	public void setSeptVolume(Double septVolume){
		this.septVolume = septVolume;
	}

	public Double getSeptVolume(){
		return this.septVolume;
	}

	public void setPhone(String phone){
		this.phone = phone;
	}

	public String getPhone(){
		return this.phone;
	}

	public void setUnderwritingOfficeWorkPhone(Integer underwritingOfficeWorkPhone){
		this.underwritingOfficeWorkPhone = underwritingOfficeWorkPhone;
	}

	public Integer getUnderwritingOfficeWorkPhone(){
		return this.underwritingOfficeWorkPhone;
	}

	public void setAprVolume(Double aprVolume){
		this.aprVolume = aprVolume;
	}

	public Double getAprVolume(){
		return this.aprVolume;
	}

	public void setUnderwriter(Integer underwriter){
		this.underwriter = underwriter;
	}

	public Integer getUnderwriter(){
		return this.underwriter;
	}

	public void setAtmailContact(Boolean atmailContact){
		this.atmailContact = atmailContact;
	}

	public Boolean getAtmailContact(){
		return this.atmailContact;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return this.type;
	}

	public void setOpportunityIds(List<Opportunity> opportunityIds){
		this.opportunityIds = opportunityIds;
	}

	public List<Opportunity> getOpportunityIds(){
		return this.opportunityIds;
	}

	public void setFunction(String function){
		this.function = function;
	}

	public String getFunction(){
		return this.function;
	}

	public void setLenderId(Integer lenderId){
		this.lenderId = lenderId;
	}

	public Integer getLenderId(){
		return this.lenderId;
	}

	public void setCreditLimit(Double creditLimit){
		this.creditLimit = creditLimit;
	}

	public Double getCreditLimit(){
		return this.creditLimit;
	}

	public void setProvince(String province){
		this.province = province;
	}

	public String getProvince(){
		return this.province;
	}

	public void setSignupValid(Boolean signupValid){
		this.signupValid = signupValid;
	}

	public Boolean getSignupValid(){
		return this.signupValid;
	}

	public void setUnderwritingOfficeZip(String underwritingOfficeZip){
		this.underwritingOfficeZip = underwritingOfficeZip;
	}

	public String getUnderwritingOfficeZip(){
		return this.underwritingOfficeZip;
	}

	public void setLenderBusinessDevelopmentManager(Integer lenderBusinessDevelopmentManager){
		this.lenderBusinessDevelopmentManager = lenderBusinessDevelopmentManager;
	}

	public Integer getLenderBusinessDevelopmentManager(){
		return this.lenderBusinessDevelopmentManager;
	}

	public void setJanVolume(Double janVolume){
		this.janVolume = janVolume;
	}

	public Double getJanVolume(){
		return this.janVolume;
	}

	public void setEmailPersonal(String emailPersonal){
		this.emailPersonal = emailPersonal;
	}

	public String getEmailPersonal(){
		return this.emailPersonal;
	}

	public void setImageMedium(String imageMedium){
		this.imageMedium = imageMedium;
	}

	public String getImageMedium(){
		return this.imageMedium;
	}

	public void setFax(String fax){
		this.fax = fax;
	}

	public String getFax(){
		return this.fax;
	}

	public void setSignupExpiration(Date signupExpiration){
		this.signupExpiration = signupExpiration;
	}

	public Date getSignupExpiration(){
		return this.signupExpiration;
	}

	public void setSignupType(String signupType){
		this.signupType = signupType;
	}

	public String getSignupType(){
		return this.signupType;
	}

	public void setJuneVolume(Double juneVolume){
		this.juneVolume = juneVolume;
	}

	public Double getJuneVolume(){
		return this.juneVolume;
	}

	public void setQ2Volume(Double q2Volume){
		this.q2Volume = q2Volume;
	}

	public Double getQ2Volume(){
		return this.q2Volume;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return this.email;
	}

	public void setQ4Volume(Double q4Volume){
		this.q4Volume = q4Volume;
	}

	public Double getQ4Volume(){
		return this.q4Volume;
	}

	public void setMayVolume(Double mayVolume){
		this.mayVolume = mayVolume;
	}

	public Double getMayVolume(){
		return this.mayVolume;
	}

	public void setUnderwritingOfficeEmail(String underwritingOfficeEmail){
		this.underwritingOfficeEmail = underwritingOfficeEmail;
	}

	public String getUnderwritingOfficeEmail(){
		return this.underwritingOfficeEmail;
	}

	public void setActive(Boolean active){
		this.active = active;
	}

	public Boolean getActive(){
		return this.active;
	}

	public void setNewduallead(Boolean newduallead){
		this.newduallead = newduallead;
	}

	public Boolean getNewduallead(){
		return this.newduallead;
	}

	public void setMobile(String mobile){
		this.mobile = mobile;
	}

	public String getMobile(){
		return this.mobile;
	}

	public void setNovVolume(Double novVolume){
		this.novVolume = novVolume;
	}

	public Double getNovVolume(){
		return this.novVolume;
	}

	public void setDebitLimit(Double debitLimit){
		this.debitLimit = debitLimit;
	}

	public Double getDebitLimit(){
		return this.debitLimit;
	}

	public void setLastReconciliationDate(Date lastReconciliationDate){
		this.lastReconciliationDate = lastReconciliationDate;
	}

	public Date getLastReconciliationDate(){
		return this.lastReconciliationDate;
	}

	public void setVat(String vat){
		this.vat = vat;
	}

	public String getVat(){
		return this.vat;
	}

	public void setYtdVolume(Double ytdVolume){
		this.ytdVolume = ytdVolume;
	}

	public Double getYtdVolume(){
		return this.ytdVolume;
	}

	public void setImageSmall(String imageSmall){
		this.imageSmall = imageSmall;
	}

	public String getImageSmall(){
		return this.imageSmall;
	}

	public void setVatSubjected(Boolean vatSubjected){
		this.vatSubjected = vatSubjected;
	}

	public Boolean getVatSubjected(){
		return this.vatSubjected;
	}

	public void setLang(String lang){
		this.lang = lang;
	}

	public String getLang(){
		return this.lang;
	}

	public void setPoBox(String poBox){
		this.poBox = poBox;
	}

	public String getPoBox(){
		return this.poBox;
	}

	public void setQ1Volume(Double q1Volume){
		this.q1Volume = q1Volume;
	}

	public Double getQ1Volume(){
		return this.q1Volume;
	}

	public void setMeetingCount(Integer meetingCount){
		this.meetingCount = meetingCount;
	}

	public Integer getMeetingCount(){
		return this.meetingCount;
	}

	public void setNotificationEmailSend(String notificationEmailSend){
		this.notificationEmailSend = notificationEmailSend;
	}

	public String getNotificationEmailSend(){
		return this.notificationEmailSend;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setBirthdate(Date birthdate){
		this.birthdate = birthdate;
	}

	public Date getBirthdate(){
		return this.birthdate;
	}

	public void setContactAddress(String contactAddress){
		this.contactAddress = contactAddress;
	}

	public String getContactAddress(){
		return this.contactAddress;
	}

	public void setEmployee(Boolean employee){
		this.employee = employee;
	}

	public Boolean getEmployee(){
		return this.employee;
	}

	public void setLenderCreditAdmin(Integer lenderCreditAdmin){
		this.lenderCreditAdmin = lenderCreditAdmin;
	}

	public Integer getLenderCreditAdmin(){
		return this.lenderCreditAdmin;
	}

	public void setStreet2(String street2){
		this.street2 = street2;
	}

	public String getStreet2(){
		return this.street2;
	}

	public void setZip(String zip){
		this.zip = zip;
	}

	public String getZip(){
		return this.zip;
	}

	public void setNewhrlead(Boolean newhrlead){
		this.newhrlead = newhrlead;
	}

	public Boolean getNewhrlead(){
		return this.newhrlead;
	}

	public void setTzOffset(String tzOffset){
		this.tzOffset = tzOffset;
	}

	public String getTzOffset(){
		return this.tzOffset;
	}

	public void setWebsite(String website){
		this.website = website;
	}

	public String getWebsite(){
		return this.website;
	}

	public void setAddrType(String addrType){
		this.addrType = addrType;
	}

	public String getAddrType(){
		return this.addrType;
	}

	public void setUseParentAddress(Boolean useParentAddress){
		this.useParentAddress = useParentAddress;
	}

	public Boolean getUseParentAddress(){
		return this.useParentAddress;
	}

	public void setReferredby(Integer referredby){
		this.referredby = referredby;
	}

	public Integer getReferredby(){
		return this.referredby;
	}

	public void setUnderwritingOfficeFax(String underwritingOfficeFax){
		this.underwritingOfficeFax = underwritingOfficeFax;
	}

	public String getUnderwritingOfficeFax(){
		return this.underwritingOfficeFax;
	}

	public void setColor(Integer color){
		this.color = color;
	}

	public Integer getColor(){
		return this.color;
	}

	public void setIsCompany(Boolean isCompany){
		this.isCompany = isCompany;
	}

	public Boolean getIsCompany(){
		return this.isCompany;
	}

	public void setMessageSummary(String messageSummary){
		this.messageSummary = messageSummary;
	}

	public String getMessageSummary(){
		return this.messageSummary;
	}

	public void setBonusCompPeriod(String bonusCompPeriod){
		this.bonusCompPeriod = bonusCompPeriod;
	}

	public String getBonusCompPeriod(){
		return this.bonusCompPeriod;
	}

}