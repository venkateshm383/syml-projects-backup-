package controllers;


public class CommissionIncomeTypeParam {
	
	//Fields
	private boolean commission;
	private String business = "";
	private String startMonth = "";
	private String currentPosition = "";
	private String monthsWorked = "";
	
	// 0 Args constructor
	public CommissionIncomeTypeParam() {
		// TODO Auto-generated constructor stub
	}
	//Argumented Constructor

	public CommissionIncomeTypeParam(boolean commission, String business,
			String startMonth, String currentPosition, String monthsWorked) {
		super();
		this.commission = commission;
		this.business = business;
		this.startMonth = startMonth;
		this.currentPosition = currentPosition;
		this.monthsWorked = monthsWorked;
	}
	//Getters & Setters
	public boolean isCommission() {
		return commission;
	}

	public void setCommission(boolean commission) {
		this.commission = commission;
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
	