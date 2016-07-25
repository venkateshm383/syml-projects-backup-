package controllers;


public class MaternityIncomeTypeParam {
	
	//Fields
	private boolean maternity;
	private String business = "";
	private String startMonth = "";
	private String currentPosition = "";
	private String monthsWorked = "";
	
	// 0 Arg constructor
	public MaternityIncomeTypeParam() {
		// TODO Auto-generated constructor stub
	}
	
	//Argumented Constructor
	public MaternityIncomeTypeParam(boolean maternity, String business,
			String startMonth, String currentPosition, String monthsWorked) {
		super();
		this.maternity = maternity;
		this.business = business;
		this.startMonth = startMonth;
		this.currentPosition = currentPosition;
		this.monthsWorked = monthsWorked;
	}

	public boolean isMaternity() {
		return maternity;
	}

	public void setMaternity(boolean maternity) {
		this.maternity = maternity;
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
	