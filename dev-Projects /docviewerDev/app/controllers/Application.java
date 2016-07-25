package controllers;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import net.iharder.Base64;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.couchbase.client.CouchbaseClient;

import play.*;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {
	static CouchbaseClient client1 = null;
	static JSONObject json=null;
	static Logger log = LoggerFactory.getLogger(Application.class);

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }
public static Result getPdfData() throws IOException, JSONException{
	
	request().getQueryString("id");
	log.debug("Data From coucbase");
	log.debug("data"+request().getQueryString("id"));
	
	CouchBaseOperation operaition=new CouchBaseOperation();
	client1=operaition.getConnectionToCouchBase();
	log.debug("Connection Success");
	try{
 json=new JSONObject(client1.get(request().getQueryString("id")).toString());
	//	json=new JSONObject(client1.get("test").toString());
	}catch (Exception e) {
e.printStackTrace();	}
if(json!=null){
	
	String pdfdata=null;
	
	try{
		pdfdata=json.get("attachement").toString();
	}catch(Exception e){
		
	}
	if(pdfdata==null){
		return ok(index.render("Data is Not in Pdf Format"));

	}else{
		return ok(index.render(pdfdata));

	}
     

}else{
	return ok(index.render("Data is Not Pdf Format"));
}
   
	
}	
/*public static Result createPDF() throws IOException {
	  byte[] pdfAsBinary = Base64.decode(json.toString());
	  response().setContentType("application/pdf");
	  return ok(pdfAsBinary);
	   //return ok(new FileInputStream("pdfs/doc.pdf")).as("application/pdf");
	}
	*/
}
