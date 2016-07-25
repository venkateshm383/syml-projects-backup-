package controllers;


public class LivingAllowIncomeTypeParam {
	
	//Fields
	private boolean livingAllow;
	private String business = "";
	private String startMonth = "";
	private String currentPosition = "";
	private String monthsWorked = "";
	
	// 0 Arg constructor
	public LivingAllowIncomeTypeParam() {
		
	}
	//Argumented Constructor
	public LivingAllowIncomeTypeParam(boolean livingAllow, String business,
			String startMonth, String currentPosition, String monthsWorked) {
		super();
		this.livingAllow=livingAllow;
		this.business = business;
		this.startMonth = startMonth;
		this.currentPosition = currentPosition;
		this.monthsWorked = monthsWorked;
	}
	//Getters and Setters
	public boolean isLivingAllow() {
		return livingAllow;
	}
	public void setLivingAllow(boolean livingAllow) {
		this.livingAllow = livingAllow;
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
	