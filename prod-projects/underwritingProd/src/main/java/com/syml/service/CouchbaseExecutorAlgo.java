package com.syml.service;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.codehaus.jettison.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.syml.bean.algo.UnderwriteAll;
import com.syml.bean.algo.UwAppAllAlgo;
import com.syml.bean.algo.UwAppAllProduct;




public class CouchbaseExecutorAlgo extends  Thread{
	CouchBaseOperation   operation=new  CouchBaseOperation();
	

	

public UwAppAllAlgo allProductAlgoJSON;

public CouchbaseExecutorAlgo(UwAppAllAlgo allProductAlgoJSON) {
	super();
	this.allProductAlgoJSON = allProductAlgoJSON;
}

@Override
	public void run() {
ObjectMapper  mapper=new  ObjectMapper();

		UUID  uuid=UUID.randomUUID();
			try{
			JSONObject  jsonobj=new  JSONObject(mapper.writeValueAsString(allProductAlgoJSON));
			operation.storeDataInCouchBase("uwapps2couchbase_allProductAlgoJSON_"+allProductAlgoJSON.getOpportunityID(), jsonobj);
			
			
			}catch (Exception e) {
			}
			
			
	
	
	
	
	super.run();
	}

}
