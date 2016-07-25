package controllers;


public class PensionIncomeTypeParam {
	
	//Fields
	private boolean pension;
	private String business = "";
	private String startMonth = "";
	private String currentPosition = "";
	private String monthsWorked = "";
	
	// 0 Arg constructor
	public PensionIncomeTypeParam() {
		// TODO Auto-generated constructor stub
	}

	//Argumented Constructor
	public PensionIncomeTypeParam(boolean pension, String business,
			String startMonth, String currentPosition, String monthsWorked) {
		super();
		this.pension = pension;
		this.business = business;
		this.startMonth = startMonth;
		this.currentPosition = currentPosition;
		this.monthsWorked = monthsWorked;
	}
	
	//Getters & Setters
	public boolean isPension() {
		return pension;
	}

	public void setPension(boolean pension) {
		this.pension = pension;
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
	