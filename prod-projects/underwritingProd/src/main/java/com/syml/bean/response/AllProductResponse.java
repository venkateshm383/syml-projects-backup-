package com.syml.bean.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.syml.bean.algo.AlgoNote;
import com.syml.domain.Task;

@XmlRootElement
public class AllProductResponse implements Serializable {

	private static final long serialVersionUID = 7315182053608803302L;

	private List<Recommendation> recommendations = new ArrayList<Recommendation>();
	private List<Task> assistantTasks = new ArrayList<Task>();
	private List<Task> brokerTasks = new ArrayList<Task>();
	private List<AlgoNote> marketingTemplateNotes = new ArrayList<AlgoNote>();
	private List<AlgoNote> dealNotes = new ArrayList<AlgoNote>();
	
	public AllProductResponse(){}

	public AllProductResponse(List<Recommendation> productids,
			List<Task> assistantTasks, List<Task> brokerTasks,
			List<AlgoNote> marketingTemplateNotes, List<AlgoNote> dealNotes) {
		super();
		this.recommendations = productids;
		this.assistantTasks = assistantTasks;
		this.brokerTasks = brokerTasks;
		this.marketingTemplateNotes = marketingTemplateNotes;
		this.dealNotes = dealNotes;
	}

	public void setTestData(){
		
		Recommendation recommendation = new Recommendation();
		recommendation.setAmortization(66.7);
		recommendation.setCashback(22.5);
		recommendation.setLenderName("testLender");
		recommendation.setMortaggeAmout(54.4);
		recommendation.setMortgageType("1");
		recommendation.setPayment(666.5);
		recommendation.setPosition("center");
		recommendation.setProductName("testProduct");
		recommendation.setRate(44.5);
		recommendation.setTerm("3");
		this.recommendations.add(recommendation);
		
		
//		AlgoNote note = new AlgoNote();
//		note.description = "assistant";
//		note.noteType = AlgoNote.TypeOfNote.AssistantAction;
//		note.urgency = AlgoNote.Priority.High;
//		AlgoNote note1 = new AlgoNote();
//		note1.description = "assistant1";
//		note1.noteType = AlgoNote.TypeOfNote.AssistantAction;
//		note1.urgency = AlgoNote.Priority.Low;
//		assistantTasks.add(note);
//		assistantTasks.add(note1);
//		
//		AlgoNote note2 = new AlgoNote();
//		note2.description = "brokerNotes";
//		note2.noteType = AlgoNote.TypeOfNote.BrokerAction;
//		note2.urgency = AlgoNote.Priority.Med;
//		AlgoNote note3 = new AlgoNote();
//		note3.description = "brokerNotes1";
//		note3.noteType = AlgoNote.TypeOfNote.BrokerAction;
//		note3.urgency = AlgoNote.Priority.High;
//		brokerTasks.add(note2);
//		brokerTasks.add(note3);
//		
//		AlgoNote note4 = new AlgoNote();
//		note4.description = "marketingTemplateNotes";
//		note4.noteType = AlgoNote.TypeOfNote.Change;
//		note4.urgency = AlgoNote.Priority.Med;
//		AlgoNote note5 = new AlgoNote();
//		note5.description = "marketingTemplateNotes1";
//		note5.noteType = AlgoNote.TypeOfNote.Change;
//		note5.urgency = AlgoNote.Priority.High;
//		marketingTemplateNotes.add(note3);
//		marketingTemplateNotes.add(note5);
//		
//		AlgoNote note6 = new AlgoNote();
//		note6.description = "dealNotes";
//		note6.noteType = AlgoNote.TypeOfNote.Info;
//		note6.urgency = AlgoNote.Priority.Low;
//		AlgoNote note7 = new AlgoNote();
//		note7.description = "dealNotes1";
//		note7.noteType = AlgoNote.TypeOfNote.Info;
//		note7.urgency = AlgoNote.Priority.Med;
//		dealNotes.add(note6);
//		dealNotes.add(note7);
		
	}
	
	public List<Recommendation> getRecommendations() {
		return recommendations;
	}

	public void setRecommendations(List<Recommendation> recommendations) {
		this.recommendations = recommendations;
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

	public List<AlgoNote> getMarketingTemplateNotes() {
		return marketingTemplateNotes;
	}

	public void setMarketingTemplateNotes(List<AlgoNote> marketingTemplateNotes) {
		this.marketingTemplateNotes = marketingTemplateNotes;
	}

	public List<AlgoNote> getDealNotes() {
		return dealNotes;
	}

	public void setDealNotes(List<AlgoNote> dealNotes) {
		this.dealNotes = dealNotes;
	}
	
}
