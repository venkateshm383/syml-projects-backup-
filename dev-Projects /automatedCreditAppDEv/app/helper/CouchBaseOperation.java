package helper;



import java.io.IOException;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.codehaus.jettison.json.JSONException;
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



public class CouchBaseOperation  {
	CouchbaseClient client1 = null;
 	static Logger log = LoggerFactory.getLogger(CouchBaseOperation.class);

 	
 	public CouchbaseClient getConnectionToCouchBase() {
		String url=null;
		String bucket=null;
		String pass=null;
		int maximumRetry=0;
		
		Properties prop=new Properties();
		ArrayList<URI> nodes = new ArrayList<URI>();
		try{
		

			// getting connection parameter
			prop.load(CouchBaseOperation.class.getClassLoader().getResourceAsStream("config.properties"));	
			
		}catch(Exception e){
			log.error("error in getting the property file"+e);
		}
		
		try {
			log.info("inside getConnectionToCouchBase method of CouchBaseOperation class");
		//	url = prop.getProperty("couchBaseUrl");
			
			 url= prop.getProperty("couchBaseUrl");
			 bucket = prop.getProperty("couchBaseBucketName");
			 pass =prop.getProperty("couchBaseBucketPassword");
			 maximumRetry=new Integer(prop.getProperty("maximumRetry"));
		
			 // 1. Add one or more nodes of your cluster (exchange the IP with
			// yours)
			nodes.add(URI.create(url));
			log.debug("connecting .....");

		
//=======

			client1 = new CouchbaseClient(nodes, bucket, pass);
		} catch (IOException e) {
			// TODO Please confirm with Shan the config of Production Couchbase instance ... Should there be a failover address in catch block and error in a final block? 
			log.error("error while connecting to couchbase" + e);
			
		}
		return client1;
	}


 	public CouchbaseClient getConnectionToCouchBaseProduction() {
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
				 System.out.println(url  +" "+bucket+""+prop.getProperty("couchBaseBucketPassword2"));

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
			System.out.println(nodes +""+ bucketName +""+password );
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
public CouchbaseClient getCouchbaseConnectionThree(ArrayList<URI> nodes,String bucketName,String password,int maximumRetry){
	log.debug("inside GetCouchbaseConectionTwo  method of couchbase");
		int retry=1;
		CouchbaseClient client=null;
		while(retry<=maximumRetry && client==null){
		try{
			System.out.println(nodes +""+ bucketName +""+password );
			client=new CouchbaseClient(nodes, bucketName, password);
		}catch(Exception e){
			client=null;
			log.error(" getCouchbaseConnectionThree Retry..   "+retry);
			retry+=1;
			log.error("error in connecting Couchbase three"+e);
		}
		
		}
		return client;
	}

	public void storeDataInCouchBase(String key,String jsonData) {
		log.info("inside storeDataInCouchBase method of CouchBaseOperation class");

		try {
			client1 = getConnectionToCouchBase();
		
			// convert data into json
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");            //get current date time with Calendar()
            Calendar cal = Calendar.getInstance();
            String currentDateTime=(dateFormat.format(cal.getTime())); 
            
            JSONObject json=new JSONObject(jsonData);
			json.put("Submission_Date_Time1b",currentDateTime);

			
			client1.set(key,json.toString());
			//client1.set(key, );
			log.debug("sending data... done with id :"+key);
		closeCouchBaseConnection();

		} catch (Exception e) {
			log.error("error while storing data into couchbase : " + e);
		}

	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void appendDataInCouchBase(String key,HashMap appendData){
		try {
			log.info("inside appendDataInCouchBase method of CouchBaseOperation class");
			client1 = getConnectionToCouchBase();
		
			
			
			String object=(String)client1.get(key);
			// convert data into json
			log.debug("old json  data...  "+object);
			JSONObject jsonData =  new JSONObject(object);
			Set<Map.Entry<String, String>> set = appendData.entrySet();
			for (Map.Entry<String, String> entry : set) {
				jsonData.put(entry.getKey(), entry.getValue());
			}

			
		/*	String object=(String)client1.get(key);
		log.debug("couchbase data "+object);
				JSONObject jsonObject=null;
			
				JSONObject	jsonObject1 = new JSONObject(object);
					System.out.println(jsonObject);
	*/	/*	
			JSONParser p = new JSONParser();
		    net.minidev.json.JSONObject o1 = (net.minidev.json.JSONObject) p
		                        .parse(jsonObject1.toString());
		    net.minidev.json.JSONObject o2 = (net.minidev.json.JSONObject) p
		                        .parse(jsonData.toString());

		    o1.merge(o2);
		*/	
			

			log.debug("new json  data...  "+jsonData.toString());
			client1.replace(key, jsonData.toString());
		//	client1.replace(key, o1.toString());
			log.debug("replacing data... done");

		closeCouchBaseConnection();

		} catch (Exception e) {
			log.error("error while apeending data into couchbase : " + e);
		}

	}
	
	public void editDataInCouchBase(String key,JSONObject editData){
		try {
			log.info("inside editDataInCouchBase method of CouchBaseOperation class");

			client1 = getConnectionToCouchBase();
		
			// convert data into json
			

			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");            //get current date time with Calendar()
            Calendar cal = Calendar.getInstance();
            String currentDateTime=(dateFormat.format(cal.getTime())); 
            editData.put("Submission_Date_Time1b",currentDateTime);


			log.debug("editing data...");
			client1.replace(key, editData.toString());

		closeCouchBaseConnection();

		} catch (Exception e) {
			log.error("error while editing data into couchbase : " + e);
		}
	}

	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void storeMortgageFormData3InCouchBase(HashMap map) {
		try {

			client1 = getConnectionToCouchBase();
			String currentlyownanyrealestate = (String) map.get("doyoucurrentlyownanyrealestate");

			String mortgageform3UniqueId = (String) map.get("mortgageform3UniqueId");
			

			// convert data into json
			JSONObject jsonData = new JSONObject();

			Set<Map.Entry<String, String>> set = map.entrySet();
			for (Map.Entry<String, String> entry : set) {
				jsonData.put(entry.getKey(), entry.getValue());
			}

			// create a unqiue id
			String uid = mortgageform3UniqueId + "_" + currentlyownanyrealestate;

			log.info("sending data...");
			client1.set(uid, jsonData.toString());

			log.info("closing connection");
		closeCouchBaseConnection();

		} catch (Exception e) {
			log.error("error while storing data into couchbase : " + e);
		}

	}
	
	
	public void closeCouchBaseConnection() {
		log.debug("closing connection");
		client1.shutdown(900l, TimeUnit.MILLISECONDS);
	

	}
	
	public void deleteCouchBaseData(String key){
		log.debug("Deleting couchbase data based on key");
		client1=getConnectionToCouchBase();
		client1.delete(key);
	}
	
	/*public static void main(String[] args) {
		System.out.println(new CouchBaseOperation().getCouchBaseData("Applicant_606"));
		
	}*/
	
	public JSONObject getCouchBaseData(String key){
		log.debug("Inside Get couchbase data based on key");
		client1=getConnectionToCouchBase();
		String object=(String)client1.get(key);
		
		JSONObject jsonObject=null;
		try {
			jsonObject = new JSONObject(object);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			log.error("error in getting data from couchbase "+e);
		}
		return jsonObject;
	}
	
	
	public  void getDocSoloList(String opprunityId,String documnetId) {
		String projectId = "0";
		try {
			client1 = new CouchBaseOperation().getConnectionToCouchBase();
			DesignDocument designdoc = getDesignDocument("dev_docsolo"
					+ opprunityId);
			boolean found = false;

			// 5. get the views and check the specified in code for existence
			for (ViewDesign view : designdoc.getViews()) {
				if (view.getMap() == "docsolo" + opprunityId) {
					found = true;
					break;
				}
			}

			// 6. If not found then create view inside document
			if (!found) {
				ViewDesign view = new ViewDesign("docsolo"
						+ opprunityId, "function (doc, meta) {\n"
						+ "if(doc.OriginalDocument==\"" +documnetId
						+ "\")"

						+ "{emit(meta.id,null)}\n" +

						"}");

				designdoc.getViews().add(view);
				client1.createDesignDoc(designdoc);
			}
			client1.shutdown(9000l, TimeUnit.MILLISECONDS);

			// 7. close the connection with couchbase

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// list=view(projectName);

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static ArrayList view(String projectName) {

		@SuppressWarnings("unused")
		String projectId = "0";
		ArrayList list1 = new ArrayList();
		try {
			CouchbaseClient client2 = new CouchBaseOperation()
					.getConnectionToCouchBase();
			System.setProperty("viewmode", "development");
			// get the view
			View view = client2.getView("dev_docsolo" + projectName,
					"docsolo" + projectName);
			// create Query.
			System.out.println(view.getViewName());
			Query query = new Query();
			query.setIncludeDocs(true).setLimit(1);

			// get ViewResponse
			ViewResponse viewRes = client2.query(view, query);
			// Iterate over the ViewResponse
			@SuppressWarnings("unused")
			String assignUser="";
			for (ViewRow row : viewRes) {
				@SuppressWarnings("unused")
				JSONObject jsonData = new JSONObject(row.getDocument()
						.toString());
				
				
				list1.add("");
				System.out.println("lsit " + list1.size());
			}
			try {
				client2.shutdown(9000l, TimeUnit.MILLISECONDS);
			} catch (Exception e) {
				e.printStackTrace();
			}
			/*
			 * if(list1.isEmpty()){
			 * 
			 * view(projectName); //} }
			 */


		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("error is : " + e);
		}
		System.out.println("lsit --------------->" + list1.size());

		return list1;
	}

	private  DesignDocument getDesignDocument(String name) {
		try {

			System.out.println("Design document with " + name + " exist ? "
					+ client1.getDesignDoc(name));

			return client1.getDesignDoc(name);
		} catch (Exception e) {
			return new DesignDocument(name);
		}
	}
	
	
	public void deleteCouchbaseDocument(String opporunityId){
		client1=getConnectionToCouchBase();
		log.debug("insdie couchbase delete document method ");
		try{
			client1.delete("automatedData_"+opporunityId.trim());
			closeCouchBaseConnection();
		}catch(Exception e){
			log.error("error in deleting couhbase automated Document for opporunity Id "+ opporunityId +" : error  "  +e);
		}
		
	}

	

}
