package controllers;


public class VehicleAllowIncomeTypeParam {
	
	//Fields
	private boolean vehicleAllow;
	private String business = "";
	private String startMonth = "";
	private String currentPosition = "";
	private String monthsWorked = "";
	
	// 0 Arg constructor
	public VehicleAllowIncomeTypeParam() {
		// TODO Auto-generated constructor stub
	}
	//Argumented Constructor
	public VehicleAllowIncomeTypeParam(boolean vehicleAllow, String business,
			String startMonth, String currentPosition, String monthsWorked) {
		super();
		this.vehicleAllow = vehicleAllow;
		this.business = business;
		this.startMonth = startMonth;
		this.currentPosition = currentPosition;
		this.monthsWorked = monthsWorked;
	}
	//Getters and Setters
	public boolean isVehicleAllow() {
		return vehicleAllow;
	}
	public void setVehicleAllow(boolean vehicleAllow) {
		this.vehicleAllow = vehicleAllow;
	}
	public String getBusiness() {
		return business;
	}
	public void setBusiness(String business) {
		this.business = business;
	}
	public String getStartMonth() {
		return startMonth;
	}
	public void setStartMonth(String startMonth) {
		this.startMonth = startMonth;
	}
	public String getCurrentPosition() {
		return currentPosition;
	}
	public void setCurrentPosition(String currentPosition) {
		this.currentPosition = currentPosition;
	}
	public String getMonthsWorked() {
		return monthsWorked;
	}
	public void setMonthsWorked(String monthsWorked) {
		this.monthsWorked = monthsWorked;
	}
	
	//End of Getters and Setters
}
	