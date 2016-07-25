package controllers;

public class Refinance {
	private String addressOfProperty="";
	private String apxMarketValue="";
	private String howMuchAdditional="";
	private boolean bankAccount;
	private boolean rrsps;
	private boolean investments;
	private boolean borrowed;
	private boolean saleOfProperty;
	private boolean gift;
	private boolean personalCash;
	private boolean existingEquity;
	private boolean sweetEquity;
	private String whoWillLiving = "";
	private String rentalAmount = "";
	
	public Refinance() {
		// TODO Auto-generated constructor stub
	}

	public Refinance(String addressOfProperty, String apxMarketValue,
			String howMuchAdditional, boolean bankAccount, boolean rrsps,
			boolean investments, boolean borrowed, boolean saleOfProperty,
			boolean gift, boolean personalCash, boolean existingEquity,
			boolean sweetEquity, String whoWillLiving, String rentalAmount) {
		super();
		this.addressOfProperty = addressOfProperty;
		this.apxMarketValue = apxMarketValue;
		this.howMuchAdditional = howMuchAdditional;
		this.bankAccount = bankAccount;
		this.rrsps = rrsps;
		this.investments = investments;
		this.borrowed = borrowed;
		this.saleOfProperty = saleOfProperty;
		this.gift = gift;
		this.personalCash = personalCash;
		this.existingEquity = existingEquity;
		this.sweetEquity = sweetEquity;
		this.whoWillLiving = whoWillLiving;
		this.rentalAmount = rentalAmount;
	}

	public String getAddressOfProperty() {
		return addressOfProperty;
	}

	public void setAddressOfProperty(String addressOfProperty) {
		this.addressOfProperty = addressOfProperty;
	}

	public String getApxMarketValue() {
		return apxMarketValue;
	}

	public void setApxMarketValue(String apxMarketValue) {
		this.apxMarketValue = apxMarketValue;
	}

	public String getHowMuchAdditional() {
		return howMuchAdditional;
	}

	public void setHowMuchAdditional(String howMuchAdditional) {
		this.howMuchAdditional = howMuchAdditional;
	}

	public boolean isBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(boolean bankAccount) {
		this.bankAccount = bankAccount;
	}

	public boolean isRrsps() {
		return rrsps;
	}

	public void setRrsps(boolean rrsps) {
		this.rrsps = rrsps;
	}

	public boolean isInvestments() {
		return investments;
	}

	public void setInvestments(boolean investments) {
		this.investments = investments;
	}

	public boolean isBorrowed() {
		return borrowed;
	}

	public void setBorrowed(boolean borrowed) {
		this.borrowed = borrowed;
	}

	public boolean isSaleOfProperty() {
		return saleOfProperty;
	}

	public void setSaleOfProperty(boolean saleOfProperty) {
		this.saleOfProperty = saleOfProperty;
	}

	public boolean isGift() {
		return gift;
	}

	public void setGift(boolean gift) {
		this.gift = gift;
	}

	public boolean isPersonalCash() {
		return personalCash;
	}

	public void setPersonalCash(boolean personalCash) {
		this.personalCash = personalCash;
	}

	public boolean isExistingEquity() {
		return existingEquity;
	}

	public void setExistingEquity(boolean existingEquity) {
		this.existingEquity = existingEquity;
	}

	public boolean isSweetEquity() {
		return sweetEquity;
	}

	public void setSweetEquity(boolean sweetEquity) {
		this.sweetEquity = sweetEquity;
	}

	public String getWhoWillLiving() {
		return whoWillLiving;
	}

	public void setWhoWillLiving(String whoWillLiving) {
		this.whoWillLiving = whoWillLiving;
	}

	public String getRentalAmount() {
		return rentalAmount;
	}

	public void setRentalAmount(String rentalAmount) {
		this.rentalAmount = rentalAmount;
	}
	
	
}
