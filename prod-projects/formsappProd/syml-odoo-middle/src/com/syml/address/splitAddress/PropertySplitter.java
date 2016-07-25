package com.syml.address.splitAddress;

import java.util.HashMap;

public class PropertySplitter {
	String  income[] = null;
	public String bussiness;
	public String JobTitle;
	public String sumOfMonths;
	public String Type;
	
public HashMap properIncome(String properIncome){
	
 
	
	HashMap map=new HashMap<>();
	
	if(properIncome.equals("")||properIncome.length()==0){
		return map;
	}else if(properIncome.equals("income")||properIncome.length()==0){
		income=properIncome.split(".");
		
		map.put("income", income[0].trim());
	return map;
	}else if(properIncome.equals("bussiness")||properIncome.length()==0){
		income=properIncome.split(".");
		
		map.put("bussiness", income[0].trim());
	return map;
	}else if(properIncome.equals("JobTitle")||properIncome.length()==0){
		income=properIncome.split(".");
		
		map.put("JobTitle", income[0].trim());
	return map;
	}else if(properIncome.equals("sumOfMonths")||properIncome.length()==0){
		income=properIncome.split(".");
		
		map.put("sumOfMonths", income[0].trim());
	return map;
	}else if(properIncome.equals("Type")||properIncome.length()==0){
		income=properIncome.split(".");
		
		map.put("Type", income[0].trim());
	return map;
	}
	
	
	else{
		return map;
	}
}

}
