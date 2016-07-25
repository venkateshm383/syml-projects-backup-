package com.syml.jotform.ip;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jotform.api.JotForm;
import com.syml.constants.SymlConstant;

public class GetMyIP {
 static	Logger log = LoggerFactory.getLogger(GetMyIP.class);

	
	public String myIp(long formId){
		
	log.info("inside myIp method of GetMyIp class");
		
	SymlConstant sc=new SymlConstant();
	String ip=null;
	try{
		
	JotForm client = new JotForm(sc.getJotformApiKey());
	
	
	JSONObject job=client.getFormSubmissions(formId, "", "1",null, "created_at");
    JSONArray submissions;
    submissions = job.getJSONArray("content");
    for (int i=0; i<submissions.length(); i++){
    	
        JSONObject submission = submissions.getJSONObject(i);
        
        ip=submission.get("ip").toString();
        
    log.debug("latest ip is : "+ip);
    log.debug("cretae date "+submission.get("created_at").toString());
    return ip;
    }
	}catch(Exception e){
		log.error("error in getting latest ip : "+e);
	}
		
		return ip;
	}
}
