package com.syml.domain;

import com.syml.domain.BaseBean;
import com.syml.generator.domain.annotation.ERP;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;
import com.syml.generator.domain.annotation.ERP.OpenERPType;
/**
* mapping open erp object : deal
* @author .x.m.
*
*/
@XmlRootElement
@Entity(name="deal")
public class Note extends BaseBean {
	//name
	public String name;

	//marketing_field
	@ERP( name = "marketing_field" )
	public String marketingField;

	/*********************************selection type******************************/
	//note_type
	@ERP( type = OpenERPType.selection, name = "note_type" )
	public String noteType;

	//urgency
	@ERP( type = OpenERPType.selection, name = "urgency" )
	public String urgency;

	/*********************************many2one type******************************/

	//many to one : mapping table : crm_lead(com.syml.domain.Opportunity)
	@ERP(name="opportunity_id", type = OpenERPType.many2one )
	public Integer opportunityId;


	public void setOpportunityId(Integer opportunityId){
		this.opportunityId = opportunityId;
	}

	public Integer getOpportunityId(){
		return this.opportunityId;
	}

	public void setNoteType(String noteType){
		this.noteType = noteType;
	}

	public String getNoteType(){
		return this.noteType;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	public void setMarketingField(String marketingField){
		this.marketingField = marketingField;
	}

	public String getMarketingField(){
		return this.marketingField;
	}

	public void setUrgency(String urgency){
		this.urgency = urgency;
	}

	public String getUrgency(){
		return this.urgency;
	}

}