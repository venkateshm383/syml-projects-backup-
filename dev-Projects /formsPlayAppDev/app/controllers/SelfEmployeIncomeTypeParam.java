package controllers;


public class SelfEmployeIncomeTypeParam {
	
	//Fields
	private boolean selfEmployed;
	private String business21 = "";
	private String startMonth21 = "";
	private String currentPosition21 = "";
	private String monthsWorked21 = "";
	
	private String business22 = "";
	private String startMonth22 = "";
	private String position22 = "";
	private String monthTotal22 = "";
	
	private String business23 = "";
	private String position23 = "";
	private String startMonth23 = "";
	private String monthsWorked23 = "";
	
	// 0 Arg constructor
	public SelfEmployeIncomeTypeParam() {
		// TODO Auto-generated constructor stub
	}
	
	//Argumented Constructor
	public SelfEmployeIncomeTypeParam(boolean selfEmployed, String business21,
			String startMonth21, String currentPosition21,
			String monthsWorked21, String business22, String startMonth22,
			String position22, String monthTotal22, String business23,
			String position23, String startMonth23, String monthsWorked23) {
		super();
		this.selfEmployed = selfEmployed;
		this.business21 = business21;
		this.startMonth21 = startMonth21;
		this.currentPosition21 = currentPosition21;
		this.monthsWorked21 = monthsWorked21;
		this.business22 = business22;
		this.startMonth22 = startMonth22;
		this.position22 = position22;
		this.monthTotal22 = monthTotal22;
		this.business23 = business23;
		this.position23 = position23;
		this.startMonth23 = startMonth23;
		this.monthsWorked23 = monthsWorked23;
	}

	//Getters & Setters
	public boolean isSelfEmployed() {
		return selfEmployed;
	}

	public void setSelfEmployed(boolean selfEmployed) {
		this.selfEmployed = selfEmployed;
	}

	public String getBusiness21() {
		return business21;
	}

	public void setBusiness21(String business21) {
		this.business21 = business21;
	}

	public String getStartMonth21() {
		return startMonth21;
	}

	public void setStartMonth21(String startMonth21) {
		this.startMonth21 = startMonth21;
	}

	public String getCurrentPosition21() {
		return currentPosition21;
	}

	public void setCurrentPosition21(String currentPosition21) {
		this.currentPosition21 = currentPosition21;
	}

	public String getMonthsWorked21() {
		return monthsWorked21;
	}

	public void setMonthsWorked21(String monthsWorked21) {
		this.monthsWorked21 = monthsWorked21;
	}

	public String getBusiness22() {
		return business22;
	}

	public void setBusiness22(String business22) {
		this.business22 = business22;
	}

	public String getStartMonth22() {
		return startMonth22;
	}

	public void setStartMonth22(String startMonth22) {
		this.startMonth22 = startMonth22;
	}

	public String getPosition22() {
		return position22;
	}

	public void setPosition22(String position22) {
		this.position22 = position22;
	}

	public String getMonthTotal22() {
		return monthTotal22;
	}

	public void setMonthTotal22(String monthTotal22) {
		this.monthTotal22 = monthTotal22;
	}

	public String getBusiness23() {
		return business23;
	}

	public void setBusiness23(String business23) {
		this.business23 = business23;
	}

	public String getPosition23() {
		return position23;
	}

	public void setPosition23(String position23) {
		this.position23 = position23;
	}

	public String getStartMonth23() {
		return startMonth23;
	}

	public void setStartMonth23(String startMonth23) {
		this.startMonth23 = startMonth23;
	}

	public String getMonthsWorked23() {
		return monthsWorked23;
	}

	public void setMonthsWorked23(String monthsWorked23) {
		this.monthsWorked23 = monthsWorked23;
	}
	
		//End of Getters and Setters
}
	