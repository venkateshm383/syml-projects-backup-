package infrastracture;

import java.io.IOException;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.couchbase.client.CouchbaseClient;
import com.couchbase.client.protocol.views.DesignDocument;
import com.couchbase.client.protocol.views.Query;
import com.couchbase.client.protocol.views.View;
import com.couchbase.client.protocol.views.ViewDesign;
import com.couchbase.client.protocol.views.ViewResponse;
import com.couchbase.client.protocol.views.ViewRow;
import com.fasterxml.jackson.databind.ObjectMapper;

import dto.User;




public class CouchBaseOperation {
	CouchbaseClient client1 = null;
	static Logger log = LoggerFactory.getLogger(CouchBaseOperation.class);
	ObjectMapper object = new ObjectMapper();

	public CouchbaseClient getConnectionToCouchBaseprod() {
		String url = null;
		String bucket = null;
		String pass = null;
		int maximumRetry = 0;

		Properties prop = new Properties();
		ArrayList<URI> nodes = new ArrayList<URI>();
		try {

			// getting connection parameter
			prop.load(CouchBaseOperation.class.getClassLoader()
					.getResourceAsStream("config.properties"));

		} catch (Exception e) {
			log.error("error in getting the property file" + e);
		}

		try {
			log.info("inside getConnectionToCouchBase method of CouchBaseOperation class");
			// url = prop.getProperty("couchBaseUrl");

			url = prop.getProperty("couchBaseUrl");
			bucket = prop.getProperty("couchBaseBucketName");
			pass = prop.getProperty("couchBaseBucketPassword");
			maximumRetry = new Integer(prop.getProperty("maximumRetry"));

			// 1. Add one or more nodes of your cluster (exchange the IP with
			// yours)
			nodes.add(URI.create(url));
			log.debug("connecting .....");

			// =======

			client1 = new CouchbaseClient(nodes, bucket, pass);
		} catch (IOException e) {
			// TODO Please confirm with Shan the config of Production Couchbase
			// instance ... Should there be a failover address in catch block
			// and error in a final block?
			log.error("error while connecting to couchbase" + e);

		}
		return client1;
	}

	public CouchbaseClient getConnectionToCouchBase() {
		String url=null;
		String bucket=null;
		String pass=null;
		int maximumRetry=0;
		
		Properties prop = new Properties();
		ArrayList<URI> nodes = new ArrayList<URI>();
		try {

			// getting connection parameter
			prop.load(CouchBaseOperation.class.getClassLoader()
					.getResourceAsStream("config.properties"));

		} catch (Exception e) {
			log.error("error in getting the property file" + e);
		}
		
		
		try {
			
			log.info("inside getConnectionToCouchBase method of CouchBaseOperation class");
		//	url = prop.getProperty("couchBaseUrl");
			 url= prop.getProperty("couchBaseUrl1");
			 bucket = prop.getProperty("couchBaseBucketName");
			 pass =prop.getProperty("couchBaseBucketPassword");
			 try{
			 maximumRetry=new Integer(prop.getProperty("maximumRetry"));
			 }catch(Exception e){
				 
			 }
			 // 1. Add one or more nodes of your cluster (exchange the IP with
			// yours)
			nodes.add(URI.create(url));
			log.debug("connecting .....");
			
			client1 = getCouchbaseConnectionOne(nodes, bucket, pass, maximumRetry);
			if(client1==null){
				 url= prop.getProperty("couchBaseUrl2");
				 bucket = prop.getProperty("couchBaseBucketName2");
				 pass =prop.getProperty("couchBaseBucketPassword2");
				 ArrayList<URI> nodes1 = new ArrayList<URI>();
				nodes1.add(URI.create(url));
				 log.debug(url  +" "+bucket+""+prop.getProperty("couchBaseBucketPassword2"));

				client1=getCouchbaseConnectionTwo(nodes1,bucket, pass, maximumRetry);
				 if(client1==null) {
					 
					 //Send mail error in connecting couchbase
						log.error("Error in Connecting Couhbase");
					}
			}
		} catch (Exception e) {
			log.error("error while connecting to couchbase " + e);
			
			}
	
		
		return client1;
	}

	public CouchbaseClient getCouchbaseConnectionOne(ArrayList<URI> nodes,String bucketName,String password,int maximumRetry){
		
		
		log.debug("inside GetcoonectionOne  method of couchbase");
		int retry=1;
		CouchbaseClient client=null;
		while(retry<=maximumRetry && client==null){
		try{
			client=new CouchbaseClient(nodes, bucketName, password);
		}catch(Exception e){
			client=null;
			log.error(" getCouchbaseConnectionOne  Retry..    "+retry);
			retry+=1;
			log.error("error in connecting Couchbase one"+e);
		}
		
		}
		return client;
	}
	
public CouchbaseClient getCouchbaseConnectionTwo(ArrayList<URI> nodes,String bucketName,String password,int maximumRetry){
	log.debug("inside GetCouchbaseConectionTwo  method of couchbase");
		int retry=1;
		CouchbaseClient client=null;
		while(retry<=maximumRetry && client==null){
		try{
			log.debug(nodes +""+ bucketName +""+password );
			client=new CouchbaseClient(nodes, bucketName, password);
		}catch(Exception e){
			client=null;
			log.error(" getCouchbaseConnectionTwo Retry..   "+retry);
			retry+=1;
			log.error("error in connecting Couchbase two"+e);
		}
		
		}
		return client;
	}
	public void storeDataInCouchBase(String key, org.json.JSONObject data) {
		log.info("inside storeDataInCouchBase method of CouchBaseOperation class");
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");            //get current date time with Calendar()
        Calendar cal = Calendar.getInstance();
        String currentDateTime=(dateFormat.format(cal.getTime())); 
		try {
			data.put("Submission_Date_Time1b", currentDateTime);
			client1 = getConnectionToCouchBase();
			client1.set(key, data.toString());
		} catch (Exception e) {
			log.error("error while storing data into couchbase : " + e);
		}

	}

	public void updatedDataInCouchBase(String key, org.json.JSONObject editData) {
		try {
			log.info("inside editDataInCouchBase method of CouchBaseOperation class");

			client1 = getConnectionToCouchBase();
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");            //get current date time with Calendar()
	        Calendar cal = Calendar.getInstance();
	        String currentDateTime=(dateFormat.format(cal.getTime())); 
			try {
				editData.put("Submission_Date_Time1b", currentDateTime);
			}catch(Exception e){
				
			}
			log.debug("editing data...");
			client1.replace(key, editData.toString());

			closeCouchBaseConnection();

		} catch (Exception e) {
			log.error("error while editing data into couchbase : " + e);
		}
	}

	public void closeCouchBaseConnection() {
		log.debug("closing connection");
		client1.shutdown(9000l, TimeUnit.MILLISECONDS);

	}

	
	static int dataExsist=0;
	public User getTodoistUserEmail(String username){
		String email="";
		User user=null;
		try	{	
			client1 = getConnectionToCouchBase();
			DesignDocument designdoc=getDesignDocument("dev_TodoistUserView"+username);
	        boolean found = false;
	        
	        //5. get the views and check the specified in code for existence
	        for(ViewDesign view:designdoc.getViews()){
	        	if(view.getMap()=="user"){
	        	found=true;
	        	break;
	        	}
	        }
	        
	        //6. If not found then create view inside document
	        if(!found){
	        	ViewDesign view=new ViewDesign("user","function (doc, meta) {\n" +
	        			"if(doc.userName==\""+username.trim()+"\")\n" +
						"{emit(meta.id,null)}\n" +
				  
	                    "}");
	        	designdoc.getViews().add(view);
	        	client1.createDesignDoc(designdoc);
	        }
			
			
			
	      
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

			try {
				
				// get the view
				View view = client1.getView("dev_TodoistUserView"+username, "user");

				// create Query.
				Query query = new Query();
				query.setIncludeDocs(true);

				// get ViewResponse
				ViewResponse viewRes = client1.query(view, query);

				// Iterate over the ViewResponse
				for (ViewRow row : viewRes) {
						++dataExsist;
					// Parse the Document to a bean User class
					ObjectMapper object = new ObjectMapper();
					 user = object.readValue(row.getDocument().toString(),
							User.class);
					

				}
				
				try{
					client1.shutdown(9000l, TimeUnit.MILLISECONDS);

				}catch(Exception e){
					e.printStackTrace();
				}
				if(dataExsist==0){
					++dataExsist;

					getTodoistUserEmail(username);
				}
			} catch (Exception e) {
				log.debug("error is : " + e);
			}
			return user;
			
		}
		
		
		private  DesignDocument getDesignDocument(String name) {
			try {
				log.debug("Design document with "+name+" exist ? "+client1.getDesignDoc(name));
		        return client1.getDesignDoc(name);
		    } catch (com.couchbase.client.protocol.views.InvalidViewException e) {
		        return new DesignDocument(name);
		    }
		}
		
	
		public void  getApplicantDetails(String opprunityID){
			
			
			try	{	
				client1 = getConnectionToCouchBase();
				DesignDocument designdoc=getDesignDocument("dev_ApplicantView_"+opprunityID);
		        boolean found = false;
		        
		        //5. get the views and check the specified in code for existence
		        for(ViewDesign view:designdoc.getViews()){
		        	if(view.getMap()=="Applicant_"+opprunityID){
		        	found=true;
		        	break;
		        	}
		        }
		        
		        //6. If not found then create view inside document
		        if(!found){
		        	ViewDesign view=new ViewDesign("user","function (doc, meta) {\n" +
		        			"if(doc.opporunity_id=="+opprunityID+")\n" +
							"{emit(meta.id,null)}\n" +
					  
		                    "}");
		        	
		        	
		        	
		        	designdoc.getViews().add(view);
		        	client1.createDesignDoc(designdoc);
		        }
				
				
				//7. close the connection with couchbase
		      
				

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

				try {
					
					// get the view
					View view = client1.getView("dev_ApplicantView_"+opprunityID, "Applicant_"+opprunityID);

					// create Query.
					Query query = new Query();
					query.setIncludeDocs(true);

					// get ViewResponse
					ViewResponse viewRes = client1.query(view, query);

					// Iterate over the ViewResponse
					for (ViewRow row : viewRes) {

						// Parse the Document to a bean User class
						//ObjectMapper object = new ObjectMapper();
						//User user = object.readValue(row.getDocument().toString(),
							//	User.class);
						//list.add(user.getEmail());

					}
					closeCouchBaseConnection();

				} catch (Exception e) {
					log.debug("error is : " + e);
				}
			
		}
		
public String  getProjectId(String opprunityID){
			
			String projectId="0";
			try	{	
				client1 = getConnectionToCouchBase();
				DesignDocument designdoc=getDesignDocument("dev_projectid_"+opprunityID);
		        boolean found = false;
		        
		        //5. get the views and check the specified in code for existence
		        for(ViewDesign view:designdoc.getViews()){
		        	if(view.getMap()=="projectid_"+opprunityID){
		        	found=true;
		        	break;
		        	}
		        }
		        
		        //6. If not found then create view inside document
		        if(!found){
		        	ViewDesign view=new ViewDesign("projectid_"+opprunityID,"function (doc, meta) {\n" +
		        			"if(doc.crmId=="+opprunityID+" && doc.username!=\"assistant\")\n" +
							"{emit(meta.id,null)}\n" +
					  
		                    "}");
		        	
		        	
		        	
		        	designdoc.getViews().add(view);
		        	client1.createDesignDoc(designdoc);
		        }
				
				
				//7. close the connection with couchbase
		      
				

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

				try {
					
					// get the view
					View view = client1.getView("dev_projectid_"+opprunityID, "projectid_"+opprunityID);

					// create Query.
					Query query = new Query();
					query.setIncludeDocs(true);

					// get ViewResponse
					ViewResponse viewRes = client1.query(view, query);

					// Iterate over the ViewResponse
					for (ViewRow row : viewRes) {
						
						
						

						 JSONObject jsonData =new JSONObject(row.getDocument().toString());
						 JSONObject josnInsidedata=(JSONObject) jsonData.get("event_data");
						 
						 projectId=josnInsidedata.get("id").toString();

					}
					closeCouchBaseConnection();

				} catch (Exception e) {
					log.debug("error is : " + e);
				}
				return projectId;
			
		}

static ArrayList<User> list = new ArrayList<User>();


public List<User> getUsers() {

	try {
		client1 = getConnectionToCouchBase();
		DesignDocument designdoc = getDesignDocument("dev_TodoistUserGetView");
		boolean found = false;

		// 5. get the views and check the specified in code for existence
		for (ViewDesign view : designdoc.getViews()) {
			if (view.getMap() == "user") {
				found = true;
				break;
			}
		}

		// 6. If not found then create view inside document
		if (!found) {
			ViewDesign view = new ViewDesign("user",
					"function (doc, meta) {\n" + "if(doc.token)\n"
							+ "{emit(meta.id,null)}\n" +

							"}");
			designdoc.getViews().add(view);
			client1.createDesignDoc(designdoc);
		}

		// 7. close the connection with couchbase

	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

	try {

		// get the view
		View view = client1.getView("dev_TodoistUserGetView", "user");

		// create Query.
		Query query = new Query();
		query.setIncludeDocs(true);

		// get ViewResponse
		ViewResponse viewRes = client1.query(view, query);

		// Iterate over the ViewResponse
		for (ViewRow row : viewRes) {
			++dataExsist;
			// Parse the Document to a bean User class
			ObjectMapper object = new ObjectMapper();
			User user = object.readValue(row.getDocument().toString(),
					User.class);
			list.add(user);

		}
		if(list.isEmpty()){
		if (dataExsist < 3) {
			++dataExsist;

			getUsers();
			
		}
		}

		client1.shutdown();

	} catch (Exception e) {
		log.debug("error is : " + e);
	}
	return list;
}
		
		

}
