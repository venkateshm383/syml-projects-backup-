package com.syml.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.syml.domain.BaseBean;
import com.syml.generator.domain.annotation.ERP;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.persistence.TemporalType;

import com.syml.generator.domain.annotation.ERP.OpenERPType;

/**
* mapping open erp object : applicant.address
* @author gp
*
*/
@XmlTransient
@XmlRootElement
@Entity(name="applicant_address")
public class Address extends BaseBean implements Comparable<Address> {

	
	//id field in OpenERP Object as well.
	@ERP(name="id" )	
	public Integer id;
	
	//many to one : mapping table : applicant_record(com.syml.domain.Applicant)
	@ERP(name="applicant_id", type = OpenERPType.many2one )
	@JsonIgnore
	public Integer applicantId;
	
	//name This is street in OpenERP
	@ERP( name = "name" )
	public String name;
	
	//street This is often Blank in OpenERP, depends on form mapping.
	@ERP( name = "street" )
	public String street;

	//city
	@ERP( name = "city" )
	public String city;

	//province
	@ERP( name = "province" )
	public String province;

	//postalCode 
	@ERP( name = "postal_code" )
	public String postalCode;
	
	//date
	@Temporal(TemporalType.TIMESTAMP)
	public Date date;

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

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

    @Override
    public int compareTo(Address o) {
        // TODO Auto-generated method stub
        return this.date.compareTo(o.date);
    }
	
}
