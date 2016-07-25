package com.syml.domain;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class Task {
	
	// Fields
	public String name;
	public String description;
	public String assigned_to;
	public String related_to;
	public String team;
	public String deadline;
	public String tags;
	public double progress;
	public double planned_hours;
	public String date_start;
	public String date_end;
	public int sequence;
	
	public Task(){}
	
	public Task(String name, String description, String assigned_to,
			String related_to, String team, Calendar deadline, String tags,
			double progress, double planned_hours, Calendar date_start,
			Calendar date_end) {
		super();
		this.name = name;
		this.description = description;
		this.assigned_to = assigned_to;
		this.related_to = related_to;
		this.team = team;
		this.deadline = formatDateTime(deadline);
		this.tags = tags;
		this.progress = progress;
		this.planned_hours = planned_hours;
		this.date_start = formatDateTime(date_start);
		this.date_end = formatDateTime(date_end);
	}
	
	public Task(String name, String description, String assigned_to,
			String related_to, String team, Calendar deadline, String tags,
			double progress, double planned_hours, Calendar date_start,
			Calendar date_end, int sequence) {
		super();
		this.name = name;
		this.description = description;
		this.assigned_to = assigned_to;
		this.related_to = related_to;
		this.team = team;
		this.deadline = formatDateTime(deadline);
		this.tags = tags;
		this.progress = progress;
		this.planned_hours = planned_hours;
		this.date_start = formatDateTime(date_start);
		this.date_end = formatDateTime(date_end);
		this.sequence = sequence;
	}

	public String formatDateTime(Calendar cal){
    	SimpleDateFormat sdfDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    	String strDate = sdfDate.format(cal.getTime());
        return strDate;
    }
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAssigned_to() {
		return assigned_to;
	}

	public void setAssigned_to(String assigned_to) {
		this.assigned_to = assigned_to;
	}

	public String getRelated_to() {
		return related_to;
	}

	public void setRelated_to(String related_to) {
		this.related_to = related_to;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public double getProgress() {
		return progress;
	}

	public void setProgress(double progress) {
		this.progress = progress;
	}

	public double getPlanned_hours() {
		return planned_hours;
	}

	public void setPlanned_hours(double planned_hours) {
		this.planned_hours = planned_hours;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public String getDate_start() {
		return date_start;
	}

	public void setDate_start(String date_start) {
		this.date_start = date_start;
	}

	public String getDate_end() {
		return date_end;
	}

	public void setDate_end(String date_end) {
		this.date_end = date_end;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	
	
}
