package com.syml;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import addressgroup.pojo.AddressGroup;


public class SplitAddress {
	static Logger log = LoggerFactory.getLogger(SplitAddress.class);
	
	
	public Map<String,String> getProperAddress(String address) {
		String add[] = null;
		Map<String,String> map=new HashMap<String,String>();
		log.debug("insisde split address method with given address "+address);

		if(address==null){
			return map;
		}else{
			try{
			add=address.split(",");
			
		map.put("address1", add[0].trim());
		map.put("city", add[1].trim());
		String code[] = add[2].trim().split(" ");
		map.put("Province", code[0]);
		map.put("postalcode", code[1] + " " + code[2]);
		map.put("country", add[3].trim());
					
			}catch(Exception e){
				log.error("error in spliting address"+e);
			}
			
		// map.put("country", add[5].trim());
		}
		return map;
	}
	
	public Map<String,String> getProperAddressTwo(String address) {
		String add[] = null;
		Map<String,String> map=new HashMap<String,String>();
		log.debug("insisde split address method with given address "+address);
		if(address==null){
			return map;
		}else{
			try{
			add=address.split(",");
			
		map.put("address1", add[0].trim());
		map.put("city", add[1].trim());
		String code[] = add[2].trim().split(" ");
		map.put("Province", code[0]);
		map.put("postalcode", code[1] + " " + code[2]);
		map.put("country", add[3].trim());
					
			}catch(Exception e){
				log.error("error in spliting address"+e);
			}
			
		// map.put("country", add[5].trim());
		}
		return map;
	}


	public Map<String,String> getProperAddress(AddressGroup address) {
		
		String add[] = address.toString().split(",");
		Map<String,String> map = new HashMap<String,String>();
		try{
			
		
		map.put("address1", add[0].trim());
		map.put("city", add[1].trim());
		String code[] = add[2].trim().split(" ");
		map.put("Province", code[0]);
		map.put("postalcode", code[1] + " " + code[2]);
		map.put("country", add[3].trim());
		// map.put("country", add[5].trim());
		}catch(Exception e){
			
		}
		
		return map;
	}
	
	
}
