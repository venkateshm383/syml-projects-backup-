package controllers;

import com.couchbase.client.CouchbaseClient;
import com.fasterxml.jackson.databind.JsonNode;
import com.sendwithus.SendWithUsExample;

import play.*;
import play.libs.Json;
import play.mvc.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;







import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import views.html.*;

public class Application extends Controller {
static	CouchbaseClient client=null;
    public static Result index() {
    //	new TrigerListnerClass().callListner();
    	
    	
        return ok(index.render("Your new application is ready."));
    }
    public static Result getLeads()  {
		JsonNode json = request().body().asJson();
		if (json == null) {
			return badRequest("Expecting Json data");
		} else {
			
		JSONObject jsonObj=null;
		try {
			jsonObj = new JSONObject(json.toString());
			new LeadStageListner(jsonObj.toString()).run();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
			
			
			
				return ok("Leads Notified ");
			}
		
	}
    	
}
