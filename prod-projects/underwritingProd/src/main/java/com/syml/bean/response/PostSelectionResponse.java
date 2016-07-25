package com.syml.bean.response;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import sun.util.calendar.BaseCalendar.Date;

import com.syml.bean.algo.AlgoNote;
import com.syml.domain.Task;

@XmlRootElement
public class PostSelectionResponse {
	private List<AlgoNote> dealNotes = new ArrayList<AlgoNote>();
	private List<Task> assistantTasks = new ArrayList<Task>();
	private List<Task> brokerTasks = new ArrayList<Task>();
	public double ltv;
	public double GDS;
	public double TDS;
	public int lender;
	public String lender_name;
	public double purchase_price;
	public double downpayment_amount;
	public double total_mortgage_amount;
	public double commitment_fee;
	public double lender_fee;
	public double broker_fee;
	public double total_one_time_fees;
	public double rate;
	public double plusMinusPrime;
	public String term;
	public double amortization;
	public double insurer_fee;
	public String frequency;
	public double payment;
	public String mortgage_type;
	public String open_closed;
	public double cashback;
	public double base_comp_amount;
	public double volume_bonus_amount;
	public double trailer_comp_amount;
	public double marketing_comp_amount;
	public double total_comp_amount;
	public double referral_fee;
	public String closingDate;
	public String renewalDate;
	public double postedRate;
	public double percentVariable;
	
	
	
	public PostSelectionResponse(){}

	public PostSelectionResponse(List<Task> assistantTasks, List<Task> brokerTasks,
			List<AlgoNote> marketingTemplateNotes, List<AlgoNote> dealNotes) {
		super();
		this.assistantTasks = assistantTasks;
		this.brokerTasks = brokerTasks;
		this.dealNotes = dealNotes;
	}

	public void setTestData(){
		
//		AlgoNote note = new AlgoNote();
//		note.description = "assistant";
//		note.noteType = AlgoNote.TypeOfNote.AssistantAction;
//		note.urgency = AlgoNote.Priority.High;
//		AlgoNote note1 = new AlgoNote();
//		note1.description = "assistant1";
//		note1.noteType = AlgoNote.TypeOfNote.AssistantAction;
//		note1.urgency = AlgoNote.Priority.Low;
//		assistantNotes.add(note);
//		assistantNotes.add(note1);
//		
//		AlgoNote note2 = new AlgoNote();
//		note2.description = "brokerNotes";
//		note2.noteType = AlgoNote.TypeOfNote.BrokerAction;
//		note2.urgency = AlgoNote.Priority.Med;
//		AlgoNote note3 = new AlgoNote();
//		note3.description = "brokerNotes1";
//		note3.noteType = AlgoNote.TypeOfNote.BrokerAction;
//		note3.urgency = AlgoNote.Priority.High;
//		brokerNotes.add(note2);
//		brokerNotes.add(note3);
		
		AlgoNote note6 = new AlgoNote();
		note6.description = "dealNotes";
		note6.noteType = AlgoNote.TypeOfNote.Info;
		note6.urgency = AlgoNote.Priority.Low;
		AlgoNote note7 = new AlgoNote();
		note7.description = "dealNotes1";
		note7.noteType = AlgoNote.TypeOfNote.Info;
		note7.urgency = AlgoNote.Priority.Med;
		dealNotes.add(note6);
		dealNotes.add(note7);
		
	}
	

	public List<AlgoNote> getDealNotes() {
		return dealNotes;
	}

	public void setDealNotes(List<AlgoNote> dealNotes) {
		this.dealNotes = dealNotes;
	}

	public double getLtv() {
		return ltv;
	}

	public void setLtv(double ltv) {
		this.ltv = ltv;
	}

	public double getGDS() {
		return GDS;
	}

	public void setGDS(double gDS) {
		GDS = gDS;
	}

	public double getTDS() {
		return TDS;
	}

	public void setTDS(double tDS) {
		TDS = tDS;
	}

	public String getLender_name() {
		return lender_name;
	}

	public void setLender_name(String lender_name) {
		this.lender_name = lender_name;
	}

	public double getPurchase_price() {
		return purchase_price;
	}

	public void setPurchase_price(double purchase_price) {
		this.purchase_price = purchase_price;
	}

	public double getDownpayment_amount() {
		return downpayment_amount;
	}

	public void setDownpayment_amount(double downpayment_amount) {
		this.downpayment_amount = downpayment_amount;
	}

	public double getTotal_mortgage_amount() {
		return total_mortgage_amount;
	}

	public void setTotal_mortgage_amount(double total_mortgage_amount) {
		this.total_mortgage_amount = total_mortgage_amount;
	}

	public double getCommitment_fee() {
		return commitment_fee;
	}

	public void setCommitment_fee(double commitment_fee) {
		this.commitment_fee = commitment_fee;
	}

	public double getInsurer_fee() {
		return insurer_fee;
	}

	public void setInsurer_fee(double insurer_fee) {
		this.insurer_fee = insurer_fee;
	}

	public double getLender_fee() {
		return lender_fee;
	}

	public void setLender_fee(double lender_fee) {
		this.lender_fee = lender_fee;
	}

	public double getBroker_fee() {
		return broker_fee;
	}

	public void setBroker_fee(double broker_fee) {
		this.broker_fee = broker_fee;
	}

	public double getTotal_one_time_fees() {
		return total_one_time_fees;
	}

	public void setTotal_one_time_fees(double total_one_time_fees) {
		this.total_one_time_fees = total_one_time_fees;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public double getAmortization() {
		return amortization;
	}

	public void setAmortization(double amortization) {
		this.amortization = amortization;
	}

	public double getPayment() {
		return payment;
	}

	public void setPayment(double payment) {
		this.payment = payment;
	}

	public double getCashback() {
		return cashback;
	}

	public void setCashback(double cashback) {
		this.cashback = cashback;
	}

	public double getBase_comp_amount() {
		return base_comp_amount;
	}

	public void setBase_comp_amount(double base_comp_amount) {
		this.base_comp_amount = base_comp_amount;
	}

	public double getVolume_bonus_amount() {
		return volume_bonus_amount;
	}

	public void setVolume_bonus_amount(double volume_bonus_amount) {
		this.volume_bonus_amount = volume_bonus_amount;
	}

	public double getTrailer_comp_amount() {
		return trailer_comp_amount;
	}

	public void setTrailer_comp_amount(double trailer_comp_amount) {
		this.trailer_comp_amount = trailer_comp_amount;
	}

	public double getMarketing_comp_amount() {
		return marketing_comp_amount;
	}

	public void setMarketing_comp_amount(double marketing_comp_amount) {
		this.marketing_comp_amount = marketing_comp_amount;
	}

	public double getTotal_comp_amount() {
		return total_comp_amount;
	}

	public void setTotal_comp_amount(double total_comp_amount) {
		this.total_comp_amount = total_comp_amount;
	}

	public double getReferral_fee() {
		return referral_fee;
	}

	public void setReferral_fee(double referral_fee) {
		this.referral_fee = referral_fee;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getMortgage_type() {
		return mortgage_type;
	}

	public void setMortgage_type(String mortgage_type) {
		this.mortgage_type = mortgage_type;
	}

	public String getOpen_closed() {
		return open_closed;
	}

	public void setOpen_closed(String open_closed) {
		this.open_closed = open_closed;
	}

	public List<Task> getAssistantTasks() {
		return assistantTasks;
	}

	public void setAssistantTasks(List<Task> assistantTasks) {
		this.assistantTasks = assistantTasks;
	}

	public List<Task> getBrokerTasks() {
		return brokerTasks;
	}

	public void setBrokerTasks(List<Task> brokerTasks) {
		this.brokerTasks = brokerTasks;
	}

	public int getLender() {
		return lender;
	}

	public void setLender(int lender) {
		this.lender = lender;
	}

	@Override
	public String toString() {
		return "PostSelectionResponse [assistantTasks=" + assistantTasks
				+ ", brokerTasks=" + brokerTasks + ", dealNotes=" + dealNotes
				+ ", ltv=" + ltv + ", GDS=" + GDS + ", TDS=" + TDS
				+ ", lender_name=" + lender_name + ", purchase_price="
				+ purchase_price + ", downpayment_amount=" + downpayment_amount
				+ ", total_mortgage_amount=" + total_mortgage_amount
				+ ", commitment_fee=" + commitment_fee + ", lender_fee="
				+ lender_fee + ", broker_fee=" + broker_fee
				+ ", total_one_time_fees=" + total_one_time_fees + ", rate="
				+ rate + ", term=" + term + ", amortization=" + amortization
				+ ", frequency=" + frequency + ", payment=" + payment
				+ ", mortgage_type=" + mortgage_type + ", open_closed="
				+ open_closed + ", cashback=" + cashback
				+ ", base_comp_amount=" + base_comp_amount
				+ ", volume_bonus_amount=" + volume_bonus_amount
				+ ", trailer_comp_amount=" + trailer_comp_amount
				+ ", marketing_comp_amount=" + marketing_comp_amount
				+ ", total_comp_amount=" + total_comp_amount
				+ ", referral_fee=" + referral_fee + "]";
	}

	public String getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(String expectedClosingDate) {
		this.closingDate = expectedClosingDate;
	}

	public String getRenewalDate() {
		return renewalDate;
	}

	public void setRenewalDate(String closingDateAdd) {
		this.renewalDate = closingDateAdd;
	}

	public double getPlusMinusPrime() {
		return plusMinusPrime;
	}

	public void setPlusMinusPrime(double plusMinusPrime) {
		this.plusMinusPrime = plusMinusPrime;
	}

	public double getPostedRate() {
		return postedRate;
	}

	public void setPostedRate(double postedRate) {
		this.postedRate = postedRate;
	}

	public double getPercentVariable() {
		return percentVariable;
	}

	public void setPercentVariable(double percentVariable) {
		this.percentVariable = percentVariable;
	}

	
}
