package com.syml.bean.response;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.syml.bean.algo.AlgoNote;
import com.syml.domain.Lender;
import com.syml.domain.Product;

@XmlRootElement
public class FinalVerifyResponse {
	
	private List<AlgoNote> assistantTasks = new ArrayList<AlgoNote>();
	private List<AlgoNote> brokerTasks = new ArrayList<AlgoNote>();
	private List<AlgoNote> dealNotes = new ArrayList<AlgoNote>();
	//private List<AlgoNote> lenderNotes = new ArrayList<AlgoNote>();
	
//	//from lender
//	private String lenderName;
//    private Double purchasePrice;
//    private Double downPayment;
//    private Date closingDate;
//    private Date renewalDate;
//    private Double totalMortgageAmount;
//    
//    //from product
//    private Double rate;
//    private String term;
//    private String amortization;
//    private Double monthlyPayment;
//    private String mortageType;
//    private String productType;
//    private Double cashBack;
//    private String expectedCommission;
//    private String baseCommissions;
//    private String volumeCommissions;
	
	//from opportunity
	private Double amortization;
	private Integer cashBack;
	private Double downpayment;
	private Double insurerfee;
	private String lenderName;
	private Double monthlyPayment;
	
	//enum?
	private String mortgageType;
	private String openClosed;
	private String term;
	
	
	private Double rate;
	private Double totalMortgageAmount;
	private Double trailerCompAmount;
	private Double volumeBonusAmount;
	private Double baseCompAmount;
	private Double marketingCompAmount;
	private Double lenderFee;
	private Double brokerFee;
	private Double totalOneTimeFees;
	private Double totalCompAmount;
	
    public FinalVerifyResponse(){
    	
    }

    public void setTestData(){
    	
    	
    	AlgoNote note = new AlgoNote();
		note.description = "assistant";
		note.noteType = AlgoNote.TypeOfNote.AssistantAction;
		note.urgency = AlgoNote.Priority.High;
		AlgoNote note1 = new AlgoNote();
		note1.description = "assistant1";
		note1.noteType = AlgoNote.TypeOfNote.AssistantAction;
		note1.urgency = AlgoNote.Priority.Low;
		assistantTasks.add(note);
		assistantTasks.add(note1);
		
		AlgoNote note2 = new AlgoNote();
		note2.description = "brokerNotes";
		note2.noteType = AlgoNote.TypeOfNote.BrokerAction;
		note2.urgency = AlgoNote.Priority.Med;
		AlgoNote note3 = new AlgoNote();
		note3.description = "brokerNotes1";
		note3.noteType = AlgoNote.TypeOfNote.BrokerAction;
		note3.urgency = AlgoNote.Priority.High;
		brokerTasks.add(note2);
		brokerTasks.add(note3);
		
		AlgoNote note4 = new AlgoNote();
		note4.description = "marketingTemplateNotes";
		note4.noteType = AlgoNote.TypeOfNote.Change;
		note4.urgency = AlgoNote.Priority.Med;
		AlgoNote note5 = new AlgoNote();
		note5.description = "marketingTemplateNotes1";
		note5.noteType = AlgoNote.TypeOfNote.Change;
		note5.urgency = AlgoNote.Priority.High;
		dealNotes.add(note3);
		dealNotes.add(note5);
		
//		AlgoNote note6 = new AlgoNote();
//		note6.description = "dealNotes";
//		note6.noteType = AlgoNote.TypeOfNote.Info;
//		note6.urgency = AlgoNote.Priority.Low;
//		AlgoNote note7 = new AlgoNote();
//		note7.description = "dealNotes1";
//		note7.noteType = AlgoNote.TypeOfNote.Info;
//		note7.urgency = AlgoNote.Priority.Med;
//		lenderNotes.add(note6);
//		lenderNotes.add(note7);
    	
		lenderName = "xxx";
	    totalMortgageAmount = 0.00; 
	    rate = 0.00;
	    term = "xxx";
	    monthlyPayment = 0.00;
	    cashBack = 0;
		
	    amortization=0.00;
	    downpayment=0.00;
	    insurerfee=0.00;
	    monthlyPayment=0.00;
	    rate=0.00;
	    totalMortgageAmount=0.00;
	    trailerCompAmount=0.00;
	    volumeBonusAmount=0.00;
	    baseCompAmount=0.00;
	    marketingCompAmount=0.00;
	    lenderFee=0.00;
	    brokerFee=0.00;
	    totalOneTimeFees=0.00;
	    totalCompAmount=0.00;
	    
	    openClosed = "xxx";
	    mortgageType = "xxxs";
    }

	public List<AlgoNote> getAssistantTasks() {
		return assistantTasks;
	}

	public void setAssistantTasks(List<AlgoNote> assistantTasks) {
		this.assistantTasks = assistantTasks;
	}

	public List<AlgoNote> getBrokerTasks() {
		return brokerTasks;
	}

	public void setBrokerTasks(List<AlgoNote> brokerTasks) {
		this.brokerTasks = brokerTasks;
	}

	public List<AlgoNote> getDealNotes() {
		return dealNotes;
	}

	public void setDealNotes(List<AlgoNote> dealNotes) {
		this.dealNotes = dealNotes;
	}

//	public List<AlgoNote> getLenderNotes() {
//		return lenderNotes;
//	}
//
//	public void setLenderNotes(List<AlgoNote> lenderNotes) {
//		this.lenderNotes = lenderNotes;
//	}

	public String getLenderName() {
		return lenderName;
	}

	public void setLenderName(String lenderName) {
		this.lenderName = lenderName;
	}

	public Double getAmortization() {
		return amortization;
	}

	public void setAmortization(Double amortization) {
		this.amortization = amortization;
	}

	public Integer getCashBack() {
		return cashBack;
	}

	public void setCashBack(Integer cashBack) {
		this.cashBack = cashBack;
	}

	public Double getDownpayment() {
		return downpayment;
	}

	public void setDownpayment(Double downpayment) {
		this.downpayment = downpayment;
	}

	public Double getInsurerfee() {
		return insurerfee;
	}

	public void setInsurerfee(Double insurerfee) {
		this.insurerfee = insurerfee;
	}

	public Double getMonthlyPayment() {
		return monthlyPayment;
	}

	public void setMonthlyPayment(Double monthlyPayment) {
		this.monthlyPayment = monthlyPayment;
	}

	public String getMortgageType() {
		return mortgageType;
	}

	public void setMortgageType(String mortgageType) {
		this.mortgageType = mortgageType;
	}

	public String getOpenClosed() {
		return openClosed;
	}

	public void setOpenClosed(String openClosed) {
		this.openClosed = openClosed;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Double getTotalMortgageAmount() {
		return totalMortgageAmount;
	}

	public void setTotalMortgageAmount(Double totalMortgageAmount) {
		this.totalMortgageAmount = totalMortgageAmount;
	}

	public Double getTrailerCompAmount() {
		return trailerCompAmount;
	}

	public void setTrailerCompAmount(Double trailerCompAmount) {
		this.trailerCompAmount = trailerCompAmount;
	}

	public Double getVolumeBonusAmount() {
		return volumeBonusAmount;
	}

	public void setVolumeBonusAmount(Double volumeBonusAmount) {
		this.volumeBonusAmount = volumeBonusAmount;
	}

	public Double getBaseCompAmount() {
		return baseCompAmount;
	}

	public void setBaseCompAmount(Double baseCompAmount) {
		this.baseCompAmount = baseCompAmount;
	}

	public Double getMarketingCompAmount() {
		return marketingCompAmount;
	}

	public void setMarketingCompAmount(Double marketingCompAmount) {
		this.marketingCompAmount = marketingCompAmount;
	}

	public Double getLenderFee() {
		return lenderFee;
	}

	public void setLenderFee(Double lenderFee) {
		this.lenderFee = lenderFee;
	}

	public Double getBrokerFee() {
		return brokerFee;
	}

	public void setBrokerFee(Double brokerFee) {
		this.brokerFee = brokerFee;
	}

	public Double getTotalOneTimeFees() {
		return totalOneTimeFees;
	}

	public void setTotalOneTimeFees(Double totalOneTimeFees) {
		this.totalOneTimeFees = totalOneTimeFees;
	}

	public Double getTotalCompAmount() {
		return totalCompAmount;
	}

	public void setTotalCompAmount(Double totalCompAmount) {
		this.totalCompAmount = totalCompAmount;
	}

	@Override
	public String toString() {
		return "FinalVerifyResponse [assistantTasks=" + assistantTasks
				+ ", brokerTasks=" + brokerTasks + ", dealNotes=" + dealNotes
				+ ", amortization=" + amortization + ", cashBack=" + cashBack
				+ ", downpayment=" + downpayment + ", insurerfee=" + insurerfee
				+ ", lenderName=" + lenderName + ", monthlyPayment="
				+ monthlyPayment + ", mortgageType=" + mortgageType
				+ ", openClosed=" + openClosed + ", term=" + term + ", rate="
				+ rate + ", totalMortgageAmount=" + totalMortgageAmount
				+ ", trailerCompAmount=" + trailerCompAmount
				+ ", volumeBonusAmount=" + volumeBonusAmount
				+ ", baseCompAmount=" + baseCompAmount
				+ ", marketingCompAmount=" + marketingCompAmount
				+ ", lenderFee=" + lenderFee + ", brokerFee=" + brokerFee
				+ ", totalOneTimeFees=" + totalOneTimeFees
				+ ", totalCompAmount=" + totalCompAmount + "]";
	}

	
}