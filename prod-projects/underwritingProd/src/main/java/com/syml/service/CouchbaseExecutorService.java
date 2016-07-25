package com.syml.service;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.couchbase.client.CouchbaseClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syml.bean.algo.UnderwriteAll;
import com.syml.bean.algo.UwAppAllAlgo;
import com.syml.bean.algo.UwAppAllProduct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CouchbaseExecutorService extends Thread {
	private static final Logger logger = LoggerFactory
			.getLogger(CouchbaseExecutorService.class);
	CouchBaseOperation operation = new CouchBaseOperation();
	
	CouchbaseClient client1 = null;

	public CouchbaseExecutorService(List<UwAppAllProduct> productsJsonList) {
		super();
		this.productsJsonList = productsJsonList;
	}

	public List<UwAppAllProduct> productsJsonList;

	JSONObject json = new JSONObject();
	JSONArray jsonarray = new JSONArray();
	JSONObject  jsonobj= null;
	String opp_id = "0";

	@Override
	public void run() {
		ObjectMapper mapper = new ObjectMapper();


		int siz = productsJsonList.size();
		
		UwAppAllProduct type =null;
		
		//logger.info("product json List size {}  ExecutorService class {} : "
		//		+ siz);
		
		client1 = operation.getConnectionToCouchBase();
		//logger.info("connection done : "+client1);
		
		for (Iterator iterator = productsJsonList.iterator(); iterator
				.hasNext();) {
			
			 type = (UwAppAllProduct) iterator.next();
			String productName1= type.getProductName();
			String productName = productName1.replaceAll("\\s","");
			opp_id = type.getOpportunityID();
		//	logger.info("productsJsonList ID  {}  :  " + type.getOpportunityID());

			
			try {
				
				 //logger.info("jsonObject of underwrting product ------------------------"+mapper.writeValueAsString(type));
				 UUID uuid = UUID.randomUUID();
				jsonobj = new JSONObject(mapper.writeValueAsString(type));
				if(type.getAlgo().equalsIgnoreCase("failedProduct")){
				///	logger.info("failed product  {} :"+productsJsonList.size());
					
					jsonobj.put("failedProduct", "failedProduct")	;
				}else{
				//	logger.info("passed product size {} : "+productsJsonList.size());
					jsonobj.put("passedProduct", "passedProduct")	;

				}
		
				//logger.info("----------------------------------------------------Before  store  in  CB--------------");

				operation.storeDataInCouchBaseforloop("AllProduct_"+opp_id+"_"+productName+"_"+uuid,jsonobj);
				//logger.info("store in couchbase  after {}  : "+"AllProduct_"+opp_id+"_"+productName+"_"+uuid,jsonobj);
				
				 
				
			
			} catch (JSONException | JsonProcessingException e) {
				logger.error("Exception while store in couchBasse {} "
						+ e.getMessage());

			}catch (Exception  e) {
				logger.error("Exception while store in couchBasse {} "
						+ e.getMessage());

			}

		}
		operation.closeCouchBaseConnection();
		
		

		super.run();

	}

}