package controllers;






public class Recommendation {
	
	
	
	
	
	public String getSelectedProductID() {
		return selectedProductID;
	}
	public void setSelectedProductID(String selectedProductID) {
		this.selectedProductID = selectedProductID;
	}
	public String getSelectedAdditionalProductID() {
		return selectedAdditionalProductID;
	}
	public void setSelectedAdditionalProductID(String selectedAdditionalProductID) {
		this.selectedAdditionalProductID = selectedAdditionalProductID;
	}
	public String getSelectedBaseProductName() {
		return selectedBaseProductName;
	}
	public void setSelectedBaseProductName(String selectedBaseProductName) {
		this.selectedBaseProductName = selectedBaseProductName;
	}
	public String getSelectedAdditionalProductName() {
		return selectedAdditionalProductName;
	}
	public void setSelectedAdditionalProductName(
			String selectedAdditionalProductName) {
		this.selectedAdditionalProductName = selectedAdditionalProductName;
	}
	public String getSelectedBaseMortgageType() {
		return selectedBaseMortgageType;
	}
	public void setSelectedBaseMortgageType(String selectedBaseMortgageType) {
		this.selectedBaseMortgageType = selectedBaseMortgageType;
	}
	public String getSelectedAdditionalMortgageType() {
		return selectedAdditionalMortgageType;
	}
	public void setSelectedAdditionalMortgageType(
			String selectedAdditionalMortgageType) {
		this.selectedAdditionalMortgageType = selectedAdditionalMortgageType;
	}
	public String getSelectedBaseProductTerm() {
		return selectedBaseProductTerm;
	}
	public void setSelectedBaseProductTerm(String selectedBaseProductTerm) {
		this.selectedBaseProductTerm = selectedBaseProductTerm;
	}
	public String getSelectedAdditionalProductTerm() {
		return selectedAdditionalProductTerm;
	}
	public void setSelectedAdditionalProductTerm(
			String selectedAdditionalProductTerm) {
		this.selectedAdditionalProductTerm = selectedAdditionalProductTerm;
	}
	public String getSelectedBaseInterestRate() {
		return selectedBaseInterestRate;
	}
	public void setSelectedBaseInterestRate(String selectedBaseInterestRate) {
		this.selectedBaseInterestRate = selectedBaseInterestRate;
	}
	public String getSelectedAdditionalInterestRate() {
		return selectedAdditionalInterestRate;
	}
	public void setSelectedAdditionalInterestRate(
			String selectedAdditionalInterestRate) {
		this.selectedAdditionalInterestRate = selectedAdditionalInterestRate;
	}
	public String getSelectedBaseMortageAmount() {
		return selectedBaseMortageAmount;
	}
	public void setSelectedBaseMortageAmount(String selectedBaseMortageAmount) {
		this.selectedBaseMortageAmount = selectedBaseMortageAmount;
	}
	public String getSelectedAdditionalMortageAmount() {
		return selectedAdditionalMortageAmount;
	}
	public void setSelectedAdditionalMortageAmount(
			String selectedAdditionalMortageAmount) {
		this.selectedAdditionalMortageAmount = selectedAdditionalMortageAmount;
	}
	public String getTotalMortgageAmount() {
		return totalMortgageAmount;
	}
	public void setTotalMortgageAmount(String totalMortgageAmount) {
		this.totalMortgageAmount = totalMortgageAmount;
	}
	public String getAmortization() {
		return amortization;
	}
	public void setAmortization(String amortization) {
		this.amortization = amortization;
	}
	public String getTotalMonthlyPayment() {
		return totalMonthlyPayment;
	}
	public void setTotalMonthlyPayment(String totalMonthlyPayment) {
		this.totalMonthlyPayment = totalMonthlyPayment;
	}
	public String getSelectedProductLender() {
		return selectedProductLender;
	}
	public void setSelectedProductLender(String selectedProductLender) {
		this.selectedProductLender = selectedProductLender;
	}
	public String getProductType() {
		return ProductType;
	}
	@Override
	public String toString() {
		return "Recommendation [selectedProductID=" + selectedProductID
				+ ", selectedAdditionalProductID="
				+ selectedAdditionalProductID + ", selectedBaseProductName="
				+ selectedBaseProductName + ", selectedAdditionalProductName="
				+ selectedAdditionalProductName + ", selectedBaseMortgageType="
				+ selectedBaseMortgageType
				+ ", selectedAdditionalMortgageType="
				+ selectedAdditionalMortgageType + ", selectedBaseProductTerm="
				+ selectedBaseProductTerm + ", selectedAdditionalProductTerm="
				+ selectedAdditionalProductTerm + ", selectedBaseInterestRate="
				+ selectedBaseInterestRate
				+ ", selectedAdditionalInterestRate="
				+ selectedAdditionalInterestRate
				+ ", selectedBaseMortageAmount=" + selectedBaseMortageAmount
				+ ", selectedAdditionalMortageAmount="
				+ selectedAdditionalMortageAmount + ", totalMortgageAmount="
				+ totalMortgageAmount + ", amortization=" + amortization
				+ ", totalMonthlyPayment=" + totalMonthlyPayment
				+ ", selectedProductLender=" + selectedProductLender
				+ ", ProductType=" + ProductType + "]";
	}
	public void setProductType(String productType) {
		ProductType = productType;
	}
	private String  selectedProductID;
	private String  selectedAdditionalProductID;
	private String selectedBaseProductName;
	private String selectedAdditionalProductName;
	private String  selectedBaseMortgageType;
	private String selectedAdditionalMortgageType;
	private String selectedBaseProductTerm;
	private String selectedAdditionalProductTerm;
	private String selectedBaseInterestRate;
	private String selectedAdditionalInterestRate;
	private String selectedBaseMortageAmount;
	private String selectedAdditionalMortageAmount;
	private String totalMortgageAmount;
	private String amortization;
	private String  totalMonthlyPayment;
	private String selectedProductLender;
	private String ProductType;
   
    
   
   
    
    

}

