package controllers;

import java.io.IOException;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.couchbase.client.CouchbaseClient;
import com.couchbase.client.protocol.views.Query;
import com.couchbase.client.protocol.views.View;
import com.couchbase.client.protocol.views.ViewResponse;
import com.couchbase.client.protocol.views.ViewRow;


public class CouchBaseOperation  {
	static CouchbaseClient client1 = null;
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


	public CouchbaseClient getConnectionToCouchBaseForProduction() {
		String url=null;
		String bucket=null;
		String pass=null;
		int maximumRetry=0;
		
		Properties prop=new Properties();
		ArrayList<URI> nodes = new ArrayList<URI>();
		
		try{
			

			// getting connection parameter
			prop.load(CouchBaseOperation.class.getClassLoader().getResourceAsStream("config.properties"));	
			System.out.println("prop.getProperty"+prop.getProperty("couchBaseBucketName"));
		}catch(Exception e){
			log.error("error in getting the property file"+e);
		}
		try {

	
			
			
			log.info("inside getConnectionToCouchBase method of CouchBaseOperation class");
		//	url = prop.getProperty("couchBaseUrl");
			 url= prop.getProperty("couchBaseUrl1");
			 bucket = prop.getProperty("couchBaseBucketName");
			 pass =prop.getProperty("couchBaseBucketPassword");
			 maximumRetry=new Integer(prop.getProperty("maximumRetry"));
		
			 // 1. Add one or more nodes of your cluster (exchange the IP with
			// yours)
			nodes.add(URI.create(url));
			log.debug("connecting .....");
			
			client1 = getCouchbaseConnectionOne(nodes, bucket, pass, maximumRetry);
			if(client1==null){
				 url= prop.getProperty("couchBaseUrl2");
				nodes.add(URI.create(url));
				client1=getCouchbaseConnectionTwo(nodes,bucket, pass, maximumRetry);
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
	

	public void storeDataInCouchBase(String key,String list) {
		log.info("inside storeDataInCouchBase method of CouchBaseOperation class");

		try {
			client1 = getConnectionToCouchBase();
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");            //get current date time with Calendar()
	        Calendar cal = Calendar.getInstance();
	        String currentDateTime=(dateFormat.format(cal.getTime())); 
	        System.out.println("Current Time"+currentDateTime);
			
			
			
JSONObject json=new JSONObject(list);
json.put("Submission_Date_Time1b", currentDateTime);

			
		
			// convert data into json
			
			
			client1.set(key,json.toString());
			//client1.set(key, );
			log.debug("sending data... done with id :"+key);
		closeCouchBaseConnection();

		} catch (Exception e) {
			log.error("error while storing data into couchbase : " + e);
		}

	}
	
	
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
	
	public void editDataInCouchBase(String key,String formType,HashMap editData){
		try {
			log.info("inside editDataInCouchBase method of CouchBaseOperation class");

			client1 = getConnectionToCouchBase();
		
			// convert data into json
			JSONObject jsonData = new JSONObject();
			jsonData.put("FormType",formType);
			Set<Map.Entry<String, String>> set = editData.entrySet();
			for (Map.Entry<String, String> entry : set) {
				jsonData.put(entry.getKey(), entry.getValue());
			}

			

			log.debug("editing data...");
			client1.replace(key, jsonData.toString());

		closeCouchBaseConnection();

		} catch (Exception e) {
			log.error("error while editing data into couchbase : " + e);
		}
	}

	
	
	public void storeMortgageFormData3InCouchBase(HashMap map) {
		try {

			client1 = getConnectionToCouchBase();
			String currentlyownanyrealestate = (String) map.get("doyoucurrentlyownanyrealestate");

			String mortgageform3UniqueId = (String) map.get("mortgageform3UniqueId");
			
			Calendar cal = new GregorianCalendar();  

			cal.setTimeZone(TimeZone.getTimeZone("Canada/Mountain"));
		String submitDate=(String)map.put("Submission_Date_Time1b",cal.getTime());
		System.out.println("******************************************************************");
		System.out.println("Current Mountain time"+submitDate);
		System.out.println("******************************************************************");

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
	
	public static void main(String[] args) throws JSONException {
		CouchBaseOperation opper=new CouchBaseOperation();
	JSONObject json=new JSONObject();
		json.put("name", "vikash");
		//opper.storeDataInCouchBase("CoSingh",json.toString());
		
		View view = client1.getView("DESIGN_VIEW NAME", "doc");
		Query query = new Query();
		ViewResponse viewResponse = client1.query(view, query);

		List<String> keys = new ArrayList<String>();
		for (ViewRow viewRow : viewResponse) {
		   keys.add(viewRow.getKey());
		}
		System.out.println("Keys is "+keys);
	}
	

}
