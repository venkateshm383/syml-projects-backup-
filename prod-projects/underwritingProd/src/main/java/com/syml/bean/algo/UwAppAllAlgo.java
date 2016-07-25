package com.syml.bean.algo;

import java.util.Set;

public class UwAppAllAlgo {
	private String 	algo;

	private String opportunityName;

	private int opportunityID;
	private String address;
	
	private  String city;
	private String province;
	private  String combinedTable;
	
	
	public String getCombinedTable() {
		return combinedTable;
	}
	public void setCombinedTable(String combinedTable) {
		this.combinedTable = combinedTable;
	}
	private  Set<ApplicantsID> applicantsID;
	private  Set<ApplicantsNames> applicantsNames;
	private  Set<MarketingNotes> marketingNotes;
	private  Set<Recommendation> recommendations;
	private  Set<DesiredProduct> desiredProduct;
	private  Set<RecommendedProduct> recommendedProduct;
	private Set<CombinedRecommendation> combinedRecommendation;
	
	public Set<CombinedRecommendation> getCombinedRecommendation() {
		return combinedRecommendation;
	}
	public void setCombinedRecommendation(
			Set<CombinedRecommendation> combinedRecommendation) {
		this.combinedRecommendation = combinedRecommendation;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getAlgo() {
		return algo;
	}
	public void setAlgo(String algo) {
		this.algo = algo;
	}
	public String getOpportunityName() {
		return opportunityName;
	}
	public void setOpportunityName(String opportunityName) {
		this.opportunityName = opportunityName;
	}
/*	public String getOpportunityID() {
		return opportunityID;
	}
	public void setOpportunityID(String opportunityID) {
		this.opportunityID = opportunityID;
	}*/
	public Set<ApplicantsID> getApplicantsID() {
		return applicantsID;
	}
	public int getOpportunityID() {
		return opportunityID;
	}
	public void setOpportunityID(int opportunityID) {
		this.opportunityID = opportunityID;
	}
	public void setApplicantsID(Set<ApplicantsID> applicantsID) {
		this.applicantsID = applicantsID;
	}
	public Set<ApplicantsNames> getApplicantsNames() {
		return applicantsNames;
	}
	public void setApplicantsNames(Set<ApplicantsNames> applicantsNames) {
		this.applicantsNames = applicantsNames;
	}
	public Set<MarketingNotes> getMarketingNotes() {
		return marketingNotes;
	}
	public void setMarketingNotes(Set<MarketingNotes> marketingNotes) {
		this.marketingNotes = marketingNotes;
	}
	public Set<Recommendation> getRecommendations() {
		return recommendations;
	}
	public void setRecommendations(Set<Recommendation> recommendations) {
		this.recommendations = recommendations;
	}
	public Set<DesiredProduct> getDesiredProduct() {
		return desiredProduct;
	}
	public void setDesiredProduct(Set<DesiredProduct> desiredProduct) {
		this.desiredProduct = desiredProduct;
	}
	public Set<RecommendedProduct> getRecommendedProduct() {
		return recommendedProduct;
	}
	public void setRecommendedProduct(Set<RecommendedProduct> recommendedProduct) {
		this.recommendedProduct = recommendedProduct;
	}
	
	
}
