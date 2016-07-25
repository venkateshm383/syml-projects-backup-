package com.syml.addressgroup.pojo;

public class PropertyGrouping {
	String  address;
	String	mortgageYesNo;
	String	rentalYesNo;
	String	 condoYesNo;
	String	 condoFees;
	String	 sellingYesNo;
	String	 additionalYesNo;
	
	
	public PropertyGrouping() {
		super();
	}
	public PropertyGrouping(String address, String mortgageYesNo,
			String rentalYesNo, String condoYesNo, String condoFees,
			String sellingYesNo) {
		super();
		this.address = address;
		this.mortgageYesNo = mortgageYesNo;
		this.rentalYesNo = rentalYesNo;
		this.condoYesNo = condoYesNo;
		this.condoFees = condoFees;
		this.sellingYesNo = sellingYesNo;
	}
	public PropertyGrouping(String address, String mortgageYesNo,
			String rentalYesNo, String condoYesNo, String condoFees,
			String sellingYesNo, String additionalYesNo) {
		super();
		this.address = address;
		this.mortgageYesNo = mortgageYesNo;
		this.rentalYesNo = rentalYesNo;
		this.condoYesNo = condoYesNo;
		this.condoFees = condoFees;
		this.sellingYesNo = sellingYesNo;
		this.additionalYesNo = additionalYesNo;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getMortgageYesNo() {
		return mortgageYesNo;
	}
	public void setMortgageYesNo(String mortgageYesNo) {
		this.mortgageYesNo = mortgageYesNo;
	}
	public String getRentalYesNo() {
		return rentalYesNo;
	}
	public void setRentalYesNo(String rentalYesNo) {
		this.rentalYesNo = rentalYesNo;
	}
	public String getCondoYesNo() {
		return condoYesNo;
	}
	public void setCondoYesNo(String condoYesNo) {
		this.condoYesNo = condoYesNo;
	}
	public String getCondoFees() {
		return condoFees;
	}
	public void setCondoFees(String condoFees) {
		this.condoFees = condoFees;
	}
	public String getSellingYesNo() {
		return sellingYesNo;
	}
	public void setSellingYesNo(String sellingYesNo) {
		this.sellingYesNo = sellingYesNo;
	}
	public String getAdditionalYesNo() {
		return additionalYesNo;
	}
	public void setAdditionalYesNo(String additionalYesNo) {
		this.additionalYesNo = additionalYesNo;
	}


}
