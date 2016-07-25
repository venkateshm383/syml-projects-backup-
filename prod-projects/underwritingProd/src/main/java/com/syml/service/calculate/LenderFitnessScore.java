package com.syml.service.calculate;


import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;

@XmlRootElement
public class LenderFitnessScore implements Comparable<LenderFitnessScore>{
	private Double point;
	private Integer lenderId;
	private boolean done = false;
	
	@JsonCreator
	public LenderFitnessScore() {
	}
	
	public LenderFitnessScore(Double point, Integer lenderId) {
		super();
		this.point = point;
		this.lenderId = lenderId;
	}
	public Double getPoint() {
		return point;
	}
	public void setPoint(Double point) {
		this.point = point;
	}
	public Integer getLenderId() {
		return lenderId;
	}
	public void setLenderId(Integer lenderId) {
		this.lenderId = lenderId;
	}
	
	@JsonIgnore
	public boolean isDone() {
		return done;
	}

	@JsonIgnore
	public void setDone(boolean done) {
		this.done = done;
	}

	public int compareTo(LenderFitnessScore returnLender) {
		return returnLender.getPoint().compareTo(this.getPoint());
	}
	
//	public static void main(String[] args) {
//		
//		ReturnLender lender1 = new ReturnLender(12f,null);
//		ReturnLender lender2 = new ReturnLender(4f,null);
//		ReturnLender lender3 = new ReturnLender(9f,null);
//		
//		List<ReturnLender> rs = new ArrayList<ReturnLender>();
//		rs.add(lender1);
//		rs.add(lender2);
//		rs.add(lender3);
//		
//		Collections.sort(rs);
//		
//		for (ReturnLender returnLender : rs) {
//			System.out.println(returnLender.getPoint());
//		}
//		
//	}
}
