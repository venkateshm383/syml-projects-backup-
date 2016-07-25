package controllers;


public class BonusIncomeTypeParam {
	
	//Fields
	private boolean bonus;
	private String business = "";
	private String startMonth = "";
	private String currentPosition = "";
	private String monthsWorked = "";
	
	// 0 Arg constructor
	public BonusIncomeTypeParam() {
		// TODO Auto-generated constructor stub
	}
	//Argumented Constructor
	public BonusIncomeTypeParam(boolean bonus, String business,
			String startMonth, String currentPosition, String monthsWorked) {
		super();
		this.bonus = bonus;
		this.business = business;
		this.startMonth = startMonth;
		this.currentPosition = currentPosition;
		this.monthsWorked = monthsWorked;
	}
	//Getters & Setters
	public boolean isBonus() {
		return bonus;
	}
	public void setBonus(boolean bonus) {
		this.bonus = bonus;
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
	