package com.syml.bean.algo;

import javax.xml.bind.annotation.XmlRootElement;
import com.syml.domain.Note;

@XmlRootElement(name="Note")
@com.fasterxml.jackson.annotation.JsonIgnoreProperties (value = { "note" ,  "TypeOfNote", "Priority" })  
public class AlgoNote
{
	public AlgoNote note;
    public enum TypeOfNote { Change, Info, AssistantAction, BrokerAction, ProposalInfo, LenderInfo }
    public TypeOfNote noteType;
    public enum Priority { High, Med, Low }
    public Priority urgency;
    public String description;
    public String marketingField;

    public AlgoNote(){}
    
    public AlgoNote(TypeOfNote noteType, Priority urgency, String content)
    {
    	this.noteType = noteType;		
    	this.urgency = urgency;		
    	this.description = content;
    	
    	Note note  = new Note();
    	note.noteType = noteType.name();
    	note.name = content; //?
    	note.urgency = urgency.name();
    }
    public AlgoNote(TypeOfNote noteType, Priority urgency, String content, String field)
    {
    	this.noteType = noteType;		
    	this.urgency = urgency;			
    	this.description = content;
    	this.marketingField = field;
    	
    	Note note  = new Note();
    	note.noteType = noteType.name();
    	note.urgency = urgency.name();
    	note.name = content; //?
//    	note.field = field;  //?
    }

	@Override
	public String toString() {
		return "AlgoNote [note=" + note + ", noteType=" + noteType
				+ ", urgency=" + urgency + ", content=" + description + ", field="
				+ marketingField + "]";
	} 
    
}
