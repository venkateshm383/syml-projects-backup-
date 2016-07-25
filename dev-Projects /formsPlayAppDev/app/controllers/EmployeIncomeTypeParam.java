package controllers;


public class EmployeIncomeTypeParam {
	
	private boolean employed;
	private String business1 = "";
	private String startMonth1 = "";
	private String currentPosition1 = "";
	private String monthsWorked1 = "";
	
	private String business11 = "";
	private String startMonth11 = "";
	private String position11 = "";
	private String monthTotal11 = "";
	
	private String business21 = "";
	private String position21 = "";
	private String startMonth21 = "";
	private String monthsWorked21 = "";
	
	//0 Argumented Constructor
	public EmployeIncomeTypeParam() {
		// TODO Auto-generated constructor stub
	}
	//Argumented Constructor
	public EmployeIncomeTypeParam(boolean employed, String business1,
			String startMonth1, String currentPosition1, String monthsWorked1,
			String business11, String startMonth11, String position11,
			String monthTotal11, String business21, String position21,
			String startMonth21, String monthsWorked21) {
		super();
		this.employed = employed;
		this.business1 = business1;
		this.startMonth1 = startMonth1;
		this.currentPosition1 = currentPosition1;
		this.monthsWorked1 = monthsWorked1;
		this.business11 = business11;
		this.startMonth11 = startMonth11;
		this.position11 = position11;
		this.monthTotal11 = monthTotal11;
		this.business21 = business21;
		this.position21 = position21;
		this.startMonth21 = startMonth21;
		this.monthsWorked21 = monthsWorked21;
	}
	//Getters & Setters
	public boolean isEmployed() {
		return employed;
	}

	public void setEmployed(boolean employed) {
		this.employed = employed;
	}

	public String getBusiness1() {
		return business1;
	}

	public void setBusiness1(String business1) {
		this.business1 = business1;
	}

	public String getStartMonth1() {
		return startMonth1;
	}

	public void setStartMonth1(String startMonth1) {
		this.startMonth1 = startMonth1;
	}

	public String getCurrentPosition1() {
		return currentPosition1;
	}

	public void setCurrentPosition1(String currentPosition1) {
		this.currentPosition1 = currentPosition1;
	}

	public String getMonthsWorked1() {
		return monthsWorked1;
	}

	public void setMonthsWorked1(String monthsWorked1) {
		this.monthsWorked1 = monthsWorked1;
	}

	public String getBusiness11() {
		return business11;
	}

	public void setBusiness11(String business11) {
		this.business11 = business11;
	}

	public String getStartMonth11() {
		return startMonth11;
	}

	public void setStartMonth11(String startMonth11) {
		this.startMonth11 = startMonth11;
	}

	public String getPosition11() {
		return position11;
	}

	public void setPosition11(String position11) {
		this.position11 = position11;
	}

	public String getMonthTotal11() {
		return monthTotal11;
	}

	public void setMonthTotal11(String monthTotal11) {
		this.monthTotal11 = monthTotal11;
	}

	public String getBusiness21() {
		return business21;
	}

	public void setBusiness21(String business21) {
		this.business21 = business21;
	}

	public String getPosition21() {
		return position21;
	}

	public void setPosition21(String position21) {
		this.position21 = position21;
	}

	public String getStartMonth21() {
		return startMonth21;
	}

	public void setStartMonth21(String startMonth21) {
		this.startMonth21 = startMonth21;
	}

	public String getMonthsWorked21() {
		return monthsWorked21;
	}

	public void setMonthsWorked21(String monthsWorked21) {
		this.monthsWorked21 = monthsWorked21;
	}
	//Getters & Setters
}
	