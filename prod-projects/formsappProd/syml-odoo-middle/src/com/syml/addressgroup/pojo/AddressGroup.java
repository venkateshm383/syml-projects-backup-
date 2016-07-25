package com.syml.addressgroup.pojo;

public class AddressGroup {
	String address;
	String year;
	String months;
	String sumMonth;
	String totalMonths;
	public AddressGroup(String address, String year, String months,
			String sumMonth, String totalMonths) {
		super();
		this.address = address;
		this.year = year;
		this.months = months;
		this.sumMonth = sumMonth;
		this.totalMonths = totalMonths;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonths() {
		return months;
	}
	public void setMonths(String months) {
		this.months = months;
	}
	public String getSumMonth() {
		return sumMonth;
	}
	public void setSumMonth(String sumMonth) {
		this.sumMonth = sumMonth;
	}
	public String getTotalMonths() {
		return totalMonths;
	}
	public void setTotalMonths(String totalMonths) {
		this.totalMonths = totalMonths;
	}


	
	
	

}
