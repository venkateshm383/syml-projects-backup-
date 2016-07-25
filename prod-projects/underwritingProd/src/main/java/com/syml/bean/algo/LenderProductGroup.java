package com.syml.bean.algo;

import java.util.ArrayList;

import com.syml.domain.Product;

public class LenderProductGroup {
	
	public String lenderName;
	public ArrayList<Product> locList ;
	public ArrayList<Product> variableList ;
	public ArrayList<Product> fixedList ;
	public int locCombineCounter;
	public int variableCombineCounter;
	public int fixedCombineCounter;
		
	public LenderProductGroup(String lenderName){
		this.lenderName = lenderName;
		locList = new ArrayList<>();
		variableList = new ArrayList<>();
		fixedList = new ArrayList<>();
	}
	
	public ArrayList<Product> getLocList() {
		return locList;
	}
	public void setLocList(ArrayList<Product> locList) {
		this.locList = locList;
	}
	public ArrayList<Product> getVariableList() {
		return variableList;
	}
	public void setVariableList(ArrayList<Product> variableList) {
		this.variableList = variableList;
	}
	public ArrayList<Product> getFixeList() {
		return fixedList;
	}
	public void setFixeList(ArrayList<Product> fixeList) {
		this.fixedList = fixeList;
	}
	public String getLenderName() {
		return lenderName;
	}
	public void setLenderName(String lenderName) {
		this.lenderName = lenderName;
	}
	public int getLocCombineCounter() {
		return locCombineCounter;
	}
	public void setLocCombineCounter(int locCombineCounter) {
		this.locCombineCounter = locCombineCounter;
	}
	public int getVariableCombineCounter() {
		return variableCombineCounter;
	}
	public void setVariableCombineCounter(int variableCombineCounter) {
		this.variableCombineCounter = variableCombineCounter;
	}
	public int getFixedCombineCounter() {
		return fixedCombineCounter;
	}
	public void setFixedCombineCounter(int fixedCombineCounter) {
		this.fixedCombineCounter = fixedCombineCounter;
	}
	
}
