package com.syml.service;

import java.util.UUID;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.couchbase.client.CouchbaseClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syml.bean.algo.UnderwritePostSel;



public class CouchbaseData {
	private static final Logger logger = LoggerFactory.getLogger(CouchbaseData.class);

	CouchbaseClient client1=null;
	CouchBaseOperation couchbas=null;
	public  JSONObject getDataFromCouchbase(Integer opp){
		
		JSONObject  jobj=null;
		couchbas= new CouchBaseOperation();
	 client1	=couchbas.getConnectionToCouchBase();

		ObjectMapper  mapper=new ObjectMapper();
		try{
			jobj =new JSONObject(client1.get("SelectRec_"+opp).toString());
			
		}catch(Exception e){
logger.error("Error  in  getting  SelectRec"+e.getMessage());

		}
		
		
		return jobj;
		
		
	}
	
	
	
	

	
	public static void main(String[] args) {
		CouchbaseData  data=new  CouchbaseData();
		
		//data.getDataFromCouchbase("4406");
	}
}
