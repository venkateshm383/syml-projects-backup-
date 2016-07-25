package controllers;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {

    public static Result index() {
    	
    	
    	  final Set<Map.Entry<String,String[]>> entries = request().queryString().entrySet();
          for (Map.Entry<String,String[]> entry : entries) {
              final String key = entry.getKey();
              final String value = Arrays.toString(entry.getValue());
              Logger.debug(key + " " + value);
          }
          Logger.debug(request().getQueryString("a"));
          Logger.debug(request().getQueryString("b"));
          Logger.debug(request().getQueryString("c"));
      
    	
        return ok(realtor.render("Your new application is ready."));
    }
    
    
    public static Result realtor() {

  	  final Set<Map.Entry<String,String[]>> entries = request().queryString().entrySet();
        for (Map.Entry<String,String[]> entry : entries) {
            final String key = entry.getKey();
            final String value = Arrays.toString(entry.getValue());
            Logger.debug(key + " " + value);
        }
    	String emailid=request().getQueryString("email");
    	String applicantId=request().getQueryString("ContactID");
    	String opprtunityId=request().getQueryString("LeadID");
    	request().getHeader("X-FORWARDED-FOR");
    	System.out.println(request().remoteAddress());
		new StoredRealtorDataTocouchabse(applicantId,emailid,request().remoteAddress(),opprtunityId).start();

    	
        return ok(realtor.render("Your new application is ready."));
    }
    
    public static Result planner() {
    	
  	  final Set<Map.Entry<String,String[]>> entries = request().queryString().entrySet();
  	String emailid=request().getQueryString("email");
	String applicantId=request().getQueryString("ContactID");
	String opprtunityId=request().getQueryString("LeadID");
	request().getHeader("X-FORWARDED-FOR");
	System.out.println(request().remoteAddress());
	new StorePlanerDataToCouhbase(applicantId,emailid,request().remoteAddress(),opprtunityId).start();

        return ok(planner.render("Your new application is ready."));
    }
    
    public static Result plannerC() {
    	
    	
    	  final Set<Map.Entry<String,String[]>> entries = request().queryString().entrySet();
    	String emailid=request().getQueryString("email");
  	String applicantId=request().getQueryString("ContactID");
  	String opprtunityId=request().getQueryString("LeadID");
  	request().getHeader("X-FORWARDED-FOR");
  	System.out.println(request().remoteAddress());
  	new StorePlanerCDataToCouhbase(applicantId,emailid,request().remoteAddress(),opprtunityId).start();


          return ok(plannerC.render("Your new application is ready."));
      }
    
    
    public static Result realtorC() {
    	
    	 final Set<Map.Entry<String,String[]>> entries = request().queryString().entrySet();
         for (Map.Entry<String,String[]> entry : entries) {
             final String key = entry.getKey();
             final String value = Arrays.toString(entry.getValue());
             Logger.debug(key + " " + value);
         }
     	String emailid=request().getQueryString("email");
     	String applicantId=request().getQueryString("ContactID");
     	String opprtunityId=request().getQueryString("LeadID");
     	request().getHeader("X-FORWARDED-FOR");
     	System.out.println(request().remoteAddress());
 		new StoredRealtorCDataTocouchabse(applicantId,emailid,request().remoteAddress(),opprtunityId).start();


        return ok(realtorC.render("Your new application is ready."));
    }
  
    
    public static Result clientABC() {
    	 final Set<Map.Entry<String,String[]>> entries = request().queryString().entrySet();
 	  	String emailid=request().getQueryString("email");
 		String applicantId=request().getQueryString("ContactID");
 		String opprtunityId=request().getQueryString("LeadID");
 		request().getHeader("X-FORWARDED-FOR");
 		System.out.println(request().remoteAddress());
 		new StoreClientABCDataToCouchbase(applicantId,emailid,request().remoteAddress(),opprtunityId).start();

          return ok(clientABC.render("Your new application is ready."));
      }
      
    
    public static Result clientA() {
  	  final Set<Map.Entry<String,String[]>> entries = request().queryString().entrySet();
  	String emailid=request().getQueryString("email");
	String applicantId=request().getQueryString("ContactID");
	String opprtunityId=request().getQueryString("LeadID");
	request().getHeader("X-FORWARDED-FOR");
	System.out.println(request().remoteAddress());
	new StoreClientADataToCouhcbase(applicantId,emailid,request().remoteAddress(),opprtunityId).start();

        return ok(clientA.render("Your new application is ready."));
    }
    
    public static Result clientB() {
    	 final Set<Map.Entry<String,String[]>> entries = request().queryString().entrySet();
    	  	String emailid=request().getQueryString("email");
    		String applicantId=request().getQueryString("ContactID");
    		String opprtunityId=request().getQueryString("LeadID");
    		request().getHeader("X-FORWARDED-FOR");
    		System.out.println(request().remoteAddress());
    		new StoreClintBDataToCouhbase(applicantId,emailid,request().remoteAddress(),opprtunityId).start();

        return ok(clientB.render("Your new application is ready."));
    }
    public static Result clientAB() {
    	 final Set<Map.Entry<String,String[]>> entries = request().queryString().entrySet();
 	  	String emailid=request().getQueryString("email");
 		String applicantId=request().getQueryString("ContactID");
 		String opprtunityId=request().getQueryString("LeadID");
 		request().getHeader("X-FORWARDED-FOR");
 		System.out.println(request().remoteAddress());
 		new StoreClientABDataToCouchbase(applicantId,emailid,request().remoteAddress(),opprtunityId).start();

        return ok(clientAB.render("Your new application is ready."));
    }
    

}
