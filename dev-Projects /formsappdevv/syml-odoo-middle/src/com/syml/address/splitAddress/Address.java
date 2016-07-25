package com.syml.address.splitAddress;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.syml.addressgroup.pojo.AddressGroup;
import com.syml.helper.GenericHelperClass;


public class Address {
	static Logger log = LoggerFactory.getLogger(Address.class);
	
	
	public HashMap getProperAddress(String address) {
		String add[] = null;
		HashMap map=new HashMap();
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
	
	public HashMap getProperAddressTwo(String address) {
		String add[] = null;
		HashMap map=new HashMap();
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


	public HashMap getProperAddress(AddressGroup address) {
		
		String add[] = address.toString().split(",");
		HashMap map = new HashMap();
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
