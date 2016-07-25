package controllers;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@SuppressWarnings("serial")
@Entity
@Table(name="opportunity_applicant_rel")
public class ApplicantOpportunityRelationShip implements Serializable {
	
	@Id
	@Column(name="opp_id")
	private int opp_id;
	
	@Id
	@Column(name="app_id")
	private int app_id;

	public int getOpp_id() {
		return opp_id;
	}

	public void setOpp_id(int opp_id) {
		this.opp_id = opp_id;
	}

	public int getApp_id() {
		return app_id;
	}

	public void setApp_id(int app_id) {
		this.app_id = app_id;
	}

	@Override
	public String toString() {
		return "ApplicantOpportunityRelationShip [opp_id=" + opp_id
				+ ", app_id=" + app_id + "]";
	}

}
