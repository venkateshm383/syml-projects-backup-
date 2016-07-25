package controllers;

public class LendingTerm {
	// PreApproval
	private String province = "";
	private String purchasePrice = "";

	// PreApproval & Purchase
	private String downpayment = "";

	// Purchase & Refinance
	private String address = "";
	// PreApproval & Purchase
	private boolean bankAccount;
	private boolean rrsps;
	private boolean investments;
	private boolean borrowed;
	private boolean saleOfProperty;
	private boolean gift;
	private boolean personalCash;
	private boolean existingEquity;
	private boolean sweetEquity;

	// PreApproval & Purchase & Refinance
	private String whoWillLiving = "";
	private String rentalAmount = "";

	// Refinance
	private boolean buyProperty;
	private boolean payOffDebt;
	private boolean buyInvestments;
	private boolean buyVehicle;
	private boolean renovate;
	private boolean refurnish;
	private boolean vacation;
	private boolean recVehicle;
	private boolean other;
	private String marketValue = "";
	private String additionalAmount = "";

	// Purchase
	private String mlsListed = "";

	public LendingTerm() {
		// TODO Auto-generated constructor stub
	}

	public LendingTerm(String province, String purchasePrice,
			String downpayment, String address, boolean bankAccount,
			boolean rrsps, boolean investments, boolean borrowed,
			boolean saleOfProperty, boolean gift, boolean personalCash,
			boolean existingEquity, boolean sweetEquity, String whoWillLiving,
			String rentalAmount, boolean buyProperty, boolean payOffDebt,
			boolean buyInvestments, boolean buyVehicle, boolean renovate,
			boolean refurnish, boolean vacation, boolean recVehicle,
			boolean other, String marketValue, String additionalAmount,
			String mlsListed) {
		super();
		this.province = province;
		this.purchasePrice = purchasePrice;
		this.downpayment = downpayment;
		this.address = address;
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
		this.buyProperty = buyProperty;
		this.payOffDebt = payOffDebt;
		this.buyInvestments = buyInvestments;
		this.buyVehicle = buyVehicle;
		this.renovate = renovate;
		this.refurnish = refurnish;
		this.vacation = vacation;
		this.recVehicle = recVehicle;
		this.other = other;
		this.marketValue = marketValue;
		this.additionalAmount = additionalAmount;
		this.mlsListed = mlsListed;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(String purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public String getDownpayment() {
		return downpayment;
	}

	public void setDownpayment(String downpayment) {
		this.downpayment = downpayment;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public boolean isBuyProperty() {
		return buyProperty;
	}

	public void setBuyProperty(boolean buyProperty) {
		this.buyProperty = buyProperty;
	}

	public boolean isPayOffDebt() {
		return payOffDebt;
	}

	public void setPayOffDebt(boolean payOffDebt) {
		this.payOffDebt = payOffDebt;
	}

	public boolean isBuyInvestments() {
		return buyInvestments;
	}

	public void setBuyInvestments(boolean buyInvestments) {
		this.buyInvestments = buyInvestments;
	}

	public boolean isBuyVehicle() {
		return buyVehicle;
	}

	public void setBuyVehicle(boolean buyVehicle) {
		this.buyVehicle = buyVehicle;
	}

	public boolean isRenovate() {
		return renovate;
	}

	public void setRenovate(boolean renovate) {
		this.renovate = renovate;
	}

	public boolean isRefurnish() {
		return refurnish;
	}

	public void setRefurnish(boolean refurnish) {
		this.refurnish = refurnish;
	}

	public boolean isVacation() {
		return vacation;
	}

	public void setVacation(boolean vacation) {
		this.vacation = vacation;
	}

	public boolean isRecVehicle() {
		return recVehicle;
	}

	public void setRecVehicle(boolean recVehicle) {
		this.recVehicle = recVehicle;
	}

	public boolean isOther() {
		return other;
	}

	public void setOther(boolean other) {
		this.other = other;
	}

	public String getMarketValue() {
		return marketValue;
	}

	public void setMarketValue(String marketValue) {
		this.marketValue = marketValue;
	}

	public String getAdditionalAmount() {
		return additionalAmount;
	}

	public void setAdditionalAmount(String additionalAmount) {
		this.additionalAmount = additionalAmount;
	}

	public String getMlsListed() {
		return mlsListed;
	}

	@Override
	public String toString() {
		return "LendingTerm [province=" + province + ", purchasePrice="
				+ purchasePrice + ", downpayment=" + downpayment + ", address="
				+ address + ", bankAccount=" + bankAccount + ", rrsps=" + rrsps
				+ ", investments=" + investments + ", borrowed=" + borrowed
				+ ", saleOfProperty=" + saleOfProperty + ", gift=" + gift
				+ ", personalCash=" + personalCash + ", existingEquity="
				+ existingEquity + ", sweetEquity=" + sweetEquity
				+ ", whoWillLiving=" + whoWillLiving + ", rentalAmount="
				+ rentalAmount + ", buyProperty=" + buyProperty
				+ ", payOffDebt=" + payOffDebt + ", buyInvestments="
				+ buyInvestments + ", buyVehicle=" + buyVehicle + ", renovate="
				+ renovate + ", refurnish=" + refurnish + ", vacation="
				+ vacation + ", recVehicle=" + recVehicle + ", other=" + other
				+ ", marketValue=" + marketValue + ", additionalAmount="
				+ additionalAmount + ", mlsListed=" + mlsListed + "]";
	}

	public void setMlsListed(String mlsListed) {
		this.mlsListed = mlsListed;
	}

}
