package controllers;


public class OtherIncomeTypeParam {
	
	//Fields
	private boolean other;
	private String business = "";
	private String startMonth = "";
	private String currentPosition = "";
	private String monthsWorked = "";
	
	// 0 Arg constructor
	public OtherIncomeTypeParam() {
		// TODO Auto-generated constructor stub
	}
	//Argumented Constructor
	public OtherIncomeTypeParam(boolean other, String business,
			String startMonth, String currentPosition, String monthsWorked) {
		super();
		this.other = other;
		this.business = business;
		this.startMonth = startMonth;
		this.currentPosition = currentPosition;
		this.monthsWorked = monthsWorked;
	}
	//Getters & Setters
	public boolean isOther() {
		return other;
	}
	public void setOther(boolean other) {
		this.other = other;
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
	