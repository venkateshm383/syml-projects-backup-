package com.syml.bean.response;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.syml.bean.algo.AlgoNote;
import com.syml.domain.Task;

@XmlRootElement()
public class DesiredProductResponse {
	
	private Integer opportunityId;
	private Integer productId;
	private List<AlgoNote> dealNotes = new ArrayList<AlgoNote>();
	private List<Task> assistantTasks = new ArrayList<Task>();
	private List<Task> brokerTasks = new ArrayList<Task>();
	
	public DesiredProductResponse(){}

	public DesiredProductResponse(Integer opportunityId, Integer productId, List<AlgoNote> dealNotes,
			List<Task> assistantNotes, List<Task> brokerNotes) {
		super();
		this.opportunityId = opportunityId;
		this.productId = productId;
		this.dealNotes = dealNotes;
		this.assistantTasks = assistantNotes;
		this.brokerTasks = brokerNotes;
	}
	
	public DesiredProductResponse(List<AlgoNote> dealNotes, List<Task> assistantNotes, List<Task> brokerNotes) {
		super();
		this.dealNotes = dealNotes;
		this.assistantTasks = assistantNotes;
		this.brokerTasks = brokerNotes;
	}
	
	public void nullOpportunity(){
		AlgoNote note = new AlgoNote();
		note.description = "The Opportunity cannot be found in OpenERP - Null Opportunity";
		note.noteType = AlgoNote.TypeOfNote.AssistantAction;
		note.urgency = AlgoNote.Priority.High;
		dealNotes.add(note);
	}

	public void setTestData(){
		
//		this.opportunityId=1;
//		this.productId=1;
//		
//		AlgoNote note = new AlgoNote();
//		note.description = "xxxx";
//		note.noteType = AlgoNote.TypeOfNote.AssistantAction;
//		note.urgency = AlgoNote.Priority.High;
//		
//		AlgoNote note1 = new AlgoNote();
//		note1.description = "xxxxxxx";
//		note1.noteType = AlgoNote.TypeOfNote.AssistantAction;
//		note1.urgency = AlgoNote.Priority.Low;
//		
//		AlgoNote note2 = new AlgoNote();
//		note2.description = "xxxx";
//		note2.noteType = AlgoNote.TypeOfNote.BrokerAction;
//		note2.urgency = AlgoNote.Priority.Med;
//		
//		AlgoNote note3 = new AlgoNote();
//		note3.description = "xxxxxxx";
//		note3.noteType = AlgoNote.TypeOfNote.BrokerAction;
//		note3.urgency = AlgoNote.Priority.High;
//		
//		assistantNotes.add(note);
//		assistantNotes.add(note1);
//		brokerNotes.add(note2);
//		brokerNotes.add(note3);
	}
	
	public Integer getOpportunityId() {
		return opportunityId;
	}

	public void setOpportunityId(Integer opportunityId) {
		this.opportunityId = opportunityId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public List<Task> getAssistantNotes() {
		return assistantTasks;
	}

	public void setAssistantNotes(List<Task> assistantNotes) {
		this.assistantTasks = assistantNotes;
	}

	public List<Task> getBrokerNotes() {
		return brokerTasks;
	}

	public void setBrokerNotes(List<Task> brokerNotes) {
		this.brokerTasks = brokerNotes;
	}

	public List<AlgoNote> getDealNotes() {
		return dealNotes;
	}

	public void setDealNotes(List<AlgoNote> dealNotes) {
		this.dealNotes = dealNotes;
	}

	@Override
	public String toString() {
		return "DesiredProductResponse [opportunityId=" + opportunityId
				+ ", productId=" + productId + ", assistantNotes="
				+ assistantTasks + ", brokerNotes=" + brokerTasks + "]";
	}
	
}
