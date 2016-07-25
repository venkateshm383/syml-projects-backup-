package com.syml.service;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.codehaus.jettison.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.syml.bean.algo.ProposalSummary;
import com.syml.bean.algo.UnderwriteAll;
import com.syml.bean.algo.UwAppAllAlgo;
import com.syml.bean.algo.UwAppAllProduct;




public class CouchbaseExecutorProposal extends  Thread{
	CouchBaseOperation   operation=new  CouchBaseOperation();
	

	

public ProposalSummary proposalSummaryJSON;



public CouchbaseExecutorProposal(ProposalSummary proposalSummaryJSON) {
	super();
	this.proposalSummaryJSON = proposalSummaryJSON;
}



@Override
	public void run() {
ObjectMapper  mapper=new  ObjectMapper();

	
		UUID  uuid=UUID.randomUUID();
			try{
			JSONObject  jsonobj=new  JSONObject(mapper.writeValueAsString(proposalSummaryJSON));
		//operation.storeDataInCouchBase("uwapps2couchbase_proposalSummaryJSON"+uuid, jsonobj);
			
			
			}catch (Exception e) {
	}
			
			
	
	
	
	
	super.run();
	}

}
