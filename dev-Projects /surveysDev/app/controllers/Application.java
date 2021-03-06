package controllers;

import static play.data.Form.form;
import infrastracture.ClientSurvey;
import infrastracture.CouchBaseOperation;
import infrastracture.ReferralSurvey;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.client;
import views.html.index;
import views.html.referral;

public class Application extends Controller {
 	static Logger log = LoggerFactory.getLogger(Application.class);

    public static Result index() throws FileNotFoundException {
        return ok(index.render("welcome"));
        		}
    
    static CouchBaseOperation couchbase=new CouchBaseOperation();
    

    public static Result getClientDetails(){
    	
    	
    	 final Set<Map.Entry<String,String[]>> entries = request().queryString().entrySet();
    		String applicantId=request().getQueryString("app");
    		String opprtunityId=request().getQueryString("op");
    		request().getHeader("X-FORWARDED-FOR");
    		log.debug(request().remoteAddress());
    		  ArrayList<Client> 		list=null;
    		  
    	    	int exist=0;
    	    	
    	    	
    	        return ok(client.render(opprtunityId,applicantId,exist));


    		/*try{
    	list = couchbase.getClientSurveyFromCouchbase(opprtunityId);
 log.debug("list---------"+list.size());
    		 	if(list.size()==0){
    		try{
    			Thread.sleep(6000);
    		}catch(Exception e){
    			log.error("Thread Exception time out error : "+e.getMessage());
    			
    		}
    		list = couchbase.getClientSurveyFromCouchbase(opprtunityId);
    		if(list.size()==0){
    			try{
        			Thread.sleep(6000);
        		}catch(Exception e){
        			
        		}
        		list = couchbase.getClientSurveyFromCouchbase(opprtunityId);
        		if(list.size()==0){
        			try{
            			Thread.sleep(6000);
            		}catch(Exception e){
            			log.error("Thread exception : "+e.getMessage());
            			
            		}
            		list = couchbase.getClientSurveyFromCouchbase(opprtunityId);

        		}
    		}
    	}
    		}catch(Exception e){
    			log.error("connection error : "+e.getMessage());
    		}
    		try{
    			if(list!=null){
    	        	if(list.size()!=0){
    	        		exist=1;
    	        	}
    	        	}
    	    	
    		}catch(Exception e){
    			log.error("null pointer exception :"+e.getMessage());
    		}
    	
    		 log.debug("list---------"+list.size());

    	if(exist==0){
        return ok(client.render(opprtunityId,applicantId,exist));
    	}else{
    		int dataExist=0;
    		try{
    		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Client client = (Client) iterator.next();
				
				if (client.getApplicantId().equalsIgnoreCase(applicantId)) {
					dataExist=1;
				}
    		}}catch(Exception e){
    			e.printStackTrace();
    		}
    		
    		 log.debug("list---------"+list.size());

    		if(dataExist==0){
    	        return ok(client.render(opprtunityId,applicantId,exist));

    		}else{
	        return ok(index.render("You have already completed a survey for this mortgage with Visdom.  Thank you for your feedback."));
    		}
    	}*/
    }
    
    public static Result getReferralDetails(){
    	
    	
   	 final Set<Map.Entry<String,String[]>> entries = request().queryString().entrySet();
   		String referralid=request().getQueryString("app");
   		String opprtunityId=request().getQueryString("op");
   		request().getHeader("X-FORWARDED-FOR");
   		log.debug(request().remoteAddress());
   	  	int exist=0;
        return ok(referral.render(opprtunityId,referralid,exist));

   /*	  ArrayList<Client>  list=null;
   	  try{
   	 list = couchbase.getReferralSurveyFromCouchbase(opprtunityId);
 	if(list.size()==0){
  		try{
  			Thread.sleep(6000);
  		}catch(Exception e){
  			
  		}
  		list = couchbase.getReferralSurveyFromCouchbase(opprtunityId);
  		if(list.size()==0){
  			try{
      			Thread.sleep(6000);
      		}catch(Exception e){
      			
      		}
      		list = couchbase.getReferralSurveyFromCouchbase(opprtunityId);
      		if(list.size()==0){
      			try{
          			Thread.sleep(6000);
          		}catch(Exception e){
          			
          		}
          		list = couchbase.getReferralSurveyFromCouchbase(opprtunityId);

      		}
  		}
  	}
  	
   	  }catch(Exception e){
   		  
   	  }
   	  
   	  try{
  	if(list!=null){
  	if(list.size()!=0){
  		exist=1;
  	}
  	}}catch(Exception e){
  		log.error("Null pointer Exception occur : "+e.getMessage());
  		
  	}
   	
  	if(exist==0){
       return ok(referral.render(opprtunityId,referralid,exist));
  	}else{
	        return ok(index.render("You have already completed a survey for this mortgage with Visdom.  Thank you for your feedback."));

  	}*/
   }

    
    public static Result getClientSurveydetails(){
        DynamicForm dynamicForm = form().bindFromRequest();
        log.debug(dynamicForm.get("options"));
        log.debug(dynamicForm.get("option"));
        log.debug(dynamicForm.get("sendMe"));
        log.debug(dynamicForm.get("text"));
        log.debug(dynamicForm.get("charity"));
String opprtunityId=dynamicForm.get("opprtunityId");
      
        log.debug(dynamicForm.get("opprtunityId"));
        log.debug(dynamicForm.get("applicantId"));
        if(dynamicForm.get("options")!=null&&dynamicForm.get("option")!=null){
        request().getHeader("X-FORWARDED-FOR");
   		log.debug(request().remoteAddress());
   		GregorianCalendar cal =new GregorianCalendar();
   		
   		try{
        		JSONObject jsonObject=new JSONObject();
        		jsonObject.put("Type", "Client");
        		jsonObject.put("opprtunityId",dynamicForm.get("opprtunityId"));
        		jsonObject.put("applicantId",dynamicForm.get("applicantId"));

        		jsonObject.put("DateTime", cal.getTime());
        		jsonObject.put("IPAddress", request().remoteAddress());
        		jsonObject.put("Question1", dynamicForm.get("options"));

        		jsonObject.put("Question2", dynamicForm.get("option"));

        		jsonObject.put("Feedback", dynamicForm.get("text"));
        		try{
        		jsonObject.put("FeePaidTo", dynamicForm.get("sendMe"));
        		if(dynamicForm.get("sendMe").equalsIgnoreCase("Charity")){
        			
        		
        		jsonObject.put("charityName", dynamicForm.get("charity"));
        		}
        		}catch(Exception e){
        			
        		}
        		new ClientSurvey().checkClientSurvey(jsonObject);
        		

        	}catch(Exception e){
        		
        	}
   		if(!dynamicForm.get("option").equalsIgnoreCase("yack")||!dynamicForm.get("option").equalsIgnoreCase("not so good")){
   		 return redirect("https://formsapp.visdom.ca/formsapp/VisdomReferralSource1.html");
   		}else{
   	        return ok(index.render("Thank you for your feedback ....."));

   		}
        }else{
        	
        	  ArrayList 		list = couchbase.getClientSurveyFromCouchbase(opprtunityId);
          	if(list.size()==0){
          		try{
          			Thread.sleep(6000);
          		}catch(Exception e){
          			
          		}
          		list = couchbase.getClientSurveyFromCouchbase(opprtunityId);
          		if(list.size()==0){
          			try{
              			Thread.sleep(6000);
              		}catch(Exception e){
              			
              		}
              		list = couchbase.getClientSurveyFromCouchbase(opprtunityId);
              		if(list.size()==0){
              			try{
                  			Thread.sleep(6000);
                  		}catch(Exception e){
                  			
                  		}
                  		list = couchbase.getClientSurveyFromCouchbase(opprtunityId);

              		}
          		}
          	}
          	
          	
          	
            	int exist=0;
            	if(list.size()!=0){
            		exist=1;
            	}
        	
            return ok(client.render(dynamicForm.get("opprtunityId"),dynamicForm.get("applicantId"),exist));
        }


    }
    
    
    
    public static Result getReferralSurveydetails(){
        DynamicForm dynamicForm = form().bindFromRequest();
        log.debug(dynamicForm.get("options"));
        log.debug(dynamicForm.get("option"));
       log.debug(dynamicForm.get("recomend"));
        log.debug(dynamicForm.get("text"));
        log.debug(dynamicForm.get("sendMe"));
        log.debug(dynamicForm.get("charity"));

   
        log.debug(dynamicForm.get("opprtunityId"));
        log.debug(dynamicForm.get("referralId"));
        
    
        request().getHeader("X-FORWARDED-FOR");
   		log.debug(request().remoteAddress());
   		GregorianCalendar cal =new GregorianCalendar();
	
   		try{
        		JSONObject jsonObject=new JSONObject();
        jsonObject.put("Type", "Referral");
		jsonObject.put("opprtunityId",dynamicForm.get("opprtunityId"));
		jsonObject.put("referralId",dynamicForm.get("referralId"));

		jsonObject.put("ClientName", "null");
		jsonObject.put("ReferralEmail", "null");
		jsonObject.put("ReferralName", "null");

		jsonObject.put("DateTime", cal.getTime());
		jsonObject.put("IPAddress", request().remoteAddress());
		jsonObject.put("Question1", dynamicForm.get("options"));

		jsonObject.put("Question2", dynamicForm.get("option"));
		jsonObject.put("Question3", dynamicForm.get("recomend"));

		jsonObject.put("Feedback", dynamicForm.get("text"));
		
		try{
    		jsonObject.put("FeePaidTo", dynamicForm.get("sendMe"));
    		if(dynamicForm.get("sendMe").equalsIgnoreCase("Charity")){
    			
    		
    		jsonObject.put("charityName", dynamicForm.get("charity"));
    		}
    		}catch(Exception e){
    			log.error("json Excepption occured :"+e.getMessage());
    			
    		}

	new ReferralSurvey().checkReferralSurvey(jsonObject);

   		}catch(Exception e){
   			log.error(""+e.getMessage());
   			//e.printStackTrace();
   		}
   	 return redirect("https://formsapp.visdom.ca/formsapp/SubmitReferalform1D.html");


       
    }
    

}



