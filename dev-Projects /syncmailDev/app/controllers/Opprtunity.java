package controllers;

import java.util.Set;

public class Opprtunity {
	
	private String opprtunityId;
	private String opprtunityName;
	private String opprtunityEmail;

private Set<Applicants> applicants;

public String getOpprtunityId() {
	return opprtunityId;
}

public void setOpprtunityId(String opprtunityId) {
	this.opprtunityId = opprtunityId;
}

public String getOpprtunityName() {
	return opprtunityName;
}

public void setOpprtunityName(String opprtunityName) {
	this.opprtunityName = opprtunityName;
}

public String getOpprtunityEmail() {
	return opprtunityEmail;
}

public void setOpprtunityEmail(String opprtunityEmail) {
	this.opprtunityEmail = opprtunityEmail;
}

public Set<Applicants> getApplicants() {
	return applicants;
}

public void setApplicants(Set<Applicants> applicants) {
	this.applicants = applicants;
}


}
