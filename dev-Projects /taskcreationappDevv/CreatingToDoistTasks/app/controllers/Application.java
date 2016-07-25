package controllers;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


import play.mvc.*;

import toDoist.DocumentAnalyzerTasks;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import toDoist.CreditInformationTasks;
import toDoist.LeadTasks;
import toDoist.ReferralTasks;
import toDoist.StageMailTasksCreation;

import views.html.*;

public class Application extends Controller {
 	static Logger log = LoggerFactory.getLogger(Application.class);

    
    public static Result index() {
        return ok(index.render("Hi test")
        );
    }
    
    
    public static Result creatingTasks() {
    	
    	JsonNode json = request().body().asJson();
		if (json == null) {
			log.debug("Expecting Json data");
			return badRequest("Expecting Json data");
		} else {

			log.debug("properjson data sent");

			ObjectMapper mapper=new  ObjectMapper();
			try {
				dto.ApplicantDocument docList=mapper.readValue(json.toString(),dto.ApplicantDocument.class);
				new DocumentAnalyzerTasks(docList).start();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			return ok(index.render("sucess")
			        );
		}
    	
        
    }
    
    
    public static Result createTasksForCreaditInfromation(){
    	JsonNode json = request().body().asJson();

    	try{
    		JSONObject jsonData=new JSONObject(json.toString());
			new CreditInformationTasks().createTasksCredit(jsonData);

    	}catch(Exception e){
    		
    	}
    	return ok("sucesss");
    }
    
    
public static Result creatingStageMailTasks() {
    	
    	JsonNode jsona = request().body().asJson();
		if (jsona == null) {
			return badRequest("Expecting Json data");
		} else {
			System.out.println("json " +jsona);
			ObjectMapper mapper=new  ObjectMapper();
			try {
				JSONObject json = new JSONObject(jsona.toString());
				String opprunityId=json.get("id").toString();
				int stage=new Integer(json.get("stage_Id").toString());
				String lenderName=null;
				
				try{
					lenderName=json.getString("lender_name");
				}catch(Exception e){
					
				}
				StageMailTasksCreation.creatStageMailTasks(opprunityId, stage,lenderName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			return ok(index.render("sucess")
			        );
		}
    	
        
    }

    
public static Result creatingReferralTasks() {
	
	JsonNode jsona = request().body().asJson();
	if (jsona == null) {
		return badRequest("Expecting Json data");
	} else {
		System.out.println("json " +jsona);
		ObjectMapper mapper=new  ObjectMapper();
		try {
			JSONObject json = new JSONObject(jsona.toString());
			String referralName=json.get("name").toString();
			String phoneNumber=json.get("phonenumber").toString();
			ReferralTasks.createReferralproject(referralName, phoneNumber);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return ok(index.render("sucess")
		        );
	}
	
    
} 




public static Result creatingLeadTasks() {
	
	JsonNode jsona = request().body().asJson();
	if (jsona == null) {
		return badRequest("Expecting Json data");
	} else {
		System.out.println("json " +jsona);
		ObjectMapper mapper=new  ObjectMapper();
		try {
			JSONObject json = new JSONObject(jsona.toString());
			String leadid=json.get("leadid").toString();
			String leadName=json.get("leadName").toString();
			String leadPhone=json.get("leadPhone").toString();
			String leadReferralName=json.get("leadReferralName").toString();
			String leadReferralPhone=json.get("leadReferralPhone").toString();
			LeadTasks.createLeadtasks(leadid, leadName, leadPhone, leadReferralName, leadReferralPhone);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return ok(index.render("sucess")
		        );
	}
	
    
} 
  
}
