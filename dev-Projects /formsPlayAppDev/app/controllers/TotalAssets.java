package controllers;

import java.util.ArrayList;
import java.util.List;

public class TotalAssets {
	private List<Assetes> vehicle=new ArrayList<Assetes>();
	private List<Assetes> bankAccount=new ArrayList<Assetes>();
	private List<Assetes> rrsp=new ArrayList<Assetes>();
	private List<Assetes> investments=new ArrayList<Assetes>();
	private List<Assetes> others=new ArrayList<Assetes>();
	
	//No Argument Constructor
	public TotalAssets() {
		// TODO Auto-generated constructor stub
	}

	//Argumented Constructor
	
	public TotalAssets(List<Assetes> vehicle,
			List<Assetes> bankAccount, List<Assetes> rrsp,
			List<Assetes> investments, List<Assetes> others) {
		super();
		this.vehicle = vehicle;
		this.bankAccount = bankAccount;
		this.rrsp = rrsp;
		this.investments = investments;
		this.others = others;
	}
	
	//Getters and Setters
	public List<Assetes> getVehicle() {
		return vehicle;
	}

	public void setVehicle(List<Assetes> vehicle) {
		this.vehicle = vehicle;
	}

	public List<Assetes> getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(List<Assetes> bankAccount) {
		this.bankAccount = bankAccount;
	}

	public List<Assetes> getRrsp() {
		return rrsp;
	}

	public void setRrsp(List<Assetes> rrsp) {
		this.rrsp = rrsp;
	}

	public List<Assetes> getInvestments() {
		return investments;
	}

	public void setInvestments(List<Assetes> investments) {
		this.investments = investments;
	}

	public List<Assetes> getOthers() {
		return others;
	}

	public void setOthers(List<Assetes> others) {
		this.others = others;
	}
	
}
