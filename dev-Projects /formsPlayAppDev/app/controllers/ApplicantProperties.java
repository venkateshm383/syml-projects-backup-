package controllers;

public class ApplicantProperties {
	
	private String address="";
	private String appxValue="";
	private String mortgage="";
	private int rentMo=0;
	private String condoFees="";
	private String ownership="";
	private boolean selling=false;
	
	//No arg Constructor
	public ApplicantProperties() {
		// TODO Auto-generated constructor stub
	}
	//Argumented Constructor
	public ApplicantProperties(String address, String appxValue, String mortgage,
			int rentMo, String condoFees, String ownership, boolean selling) {
		super();
		this.address = address;
		this.appxValue = appxValue;
		this.mortgage = mortgage;
		this.rentMo = rentMo;
		this.condoFees = condoFees;
		this.ownership = ownership;
		this.selling = selling;
	}
	//Getters and Setters
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAppxValue() {
		return appxValue;
	}
	public void setAppxValue(String appxValue) {
		this.appxValue = appxValue;
	}
	public String getMortgage() {
		return mortgage;
	}
	public void setMortgage(String mortgage) {
		this.mortgage = mortgage;
	}
	public int getRentMo() {
		return rentMo;
	}
	public void setRentMo(int rentMo) {
		this.rentMo = rentMo;
	}
	public String getCondoFees() {
		return condoFees;
	}
	public void setCondoFees(String condoFees) {
		this.condoFees = condoFees;
	}
	public String getOwnership() {
		return ownership;
	}
	public void setOwnership(String ownership) {
		this.ownership = ownership;
	}
	public boolean isSelling() {
		return selling;
	}
	public void setSelling(boolean selling) {
		this.selling = selling;
	}
	
}
