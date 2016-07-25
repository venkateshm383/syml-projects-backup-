package com.syml.jotform.applyNowForm;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.couchbase.client.CouchbaseClient;
import com.jotform.api.JotForm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.syml.constants.SymlConstant;
import com.syml.couchbase.CouchBaseOperation;
import com.syml.openerp.CreateLead;

public class ApplyNowServlet extends HttpServlet {
    static CouchbaseClient client1 = null;
   SymlConstant symlUiid=new SymlConstant();
   
   
  
   UUID uuid = UUID.randomUUID();
   String unique = uuid.toString();
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		 	Logger log = LoggerFactory.getLogger(this.getClass());
	req.setAttribute("name", "test");	 	
		try{
			log.info("inside service method of ApplyNowServlet");
			
			SymlConstant sc=new SymlConstant();

			
			String fname=req.getParameter("firstname");
			String lname=req.getParameter("lastname");
			String email=req.getParameter("email");
			String phn=req.getParameter("mobilenumber");
			//String unique=req.getParameter("unique");
			String address=req.getParameter("address");
			String IP=req.getRemoteAddr();
			System.out.println("IP is :"+IP);
			
			log.debug("firstname  is : "+fname+"\temail is : "+email+"\tLastname is : "+lname+"\t"+"phn no. is :"+phn+"uniqueId"+unique);
			
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			//get current date time with Calendar()
			   Calendar cal = Calendar.getInstance();
			  String currentDateTime=(dateFormat.format(cal.getTime()));
			
			  
			//get ip of latest form sumitted
				String ip=req.getRemoteAddr();

			CreateLead createLead=new CreateLead();
			createLead.checkAndCreateLead(fname, lname, phn, email,address);
			
			
			HashMap map=new HashMap();
			map.put("FirstName",fname);
			map.put("LastName",lname);
			map.put("mobile",phn);
			map.put("email",email);
			map.put("address",address);
			
			String formType="apply_now";

			log.debug("input from form2 referal resource : firstname "
					+ fname + "\t lastname : " + lname + "\t phone : "
					+ phn + "\t email : " + email + "\t current time : "
					+ currentDateTime + "\t address : " + address + "\t ip : " + ip
					+ "\t uniId : " + unique);
			
			//create key to store data into couchbase
			String key=formType+"_"+unique;
			

			
			
			
			// enter data into couchdb
			CouchBaseOperation storeData = new CouchBaseOperation();
			storeData.storeDataInCouchBase(key,formType,map);
			
			
			
			
			
			
			PrintWriter pw=res.getWriter();
			pw.print("Thank you for you exploring your mortgage options with Visdom.  We have sent an email the the address with you provided which contains a link to your secure mortgage application.  Please check your email for this message from Visdom.  In the event it is not in your inbox, once again, perhaps check the spam folder to ensure your email provider did not mis-label it.  If you have any challenges, you can call our broker team at 1-855-699-2400.");
	
			
			
		}catch(Exception e){
			log.error("error in service : "+e);
		}
		
	}
	
	
	
	

}
