package infrastracture;

import java.io.IOException;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.codehaus.jettison.json.JSONObject;

import play.Logger;

import com.couchbase.client.CouchbaseClient;
import com.couchbase.client.protocol.views.DesignDocument;
import com.couchbase.client.protocol.views.Query;
import com.couchbase.client.protocol.views.View;
import com.couchbase.client.protocol.views.ViewDesign;
import com.couchbase.client.protocol.views.ViewResponse;
import com.couchbase.client.protocol.views.ViewRow;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CouchBaseOperation {
	private static org.slf4j.Logger logger = play.Logger.underlying();
	CouchbaseClient client1 = null;
	//static Logger log = LoggerFactory.getLogger(CouchBaseOperation.class);
	ObjectMapper object = new ObjectMapper();

	public CouchbaseClient getConnectionToCouchBasePro() {
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
			logger.error("error in getting the property file" +e.getMessage());
		}

		try {
			logger.debug("inside getConnectionToCouchBase method of CouchBaseOperation class");
			// url = prop.getProperty("couchBaseUrl");

			url = prop.getProperty("couchBaseUrl");
			bucket = prop.getProperty("couchBaseBucketName");
			pass = prop.getProperty("couchBaseBucketPassword");
			//maximumRetry = new Integer(prop.getProperty("maximumRetry"));

			// 1. Add one or more nodes of your cluster (exchange the IP with
			// yours)
			nodes.add(URI.create(url));
			logger.debug("connecting .....");

			// =======

			client1 = new CouchbaseClient(nodes, bucket, pass);
		} catch (IOException e) {
			// TODO Please confirm with Shan the config of Production Couchbase
			// instance ... Should there be a failover address in catch block
			// and error in a final block?
			logger.error("error while connecting to couchbase" +e.getMessage());

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
			logger.error("error in getting the property file"+e.getMessage());
		}

		
		try {
			
			logger.debug("inside getConnectionToCouchBase method of CouchBaseOperation class");
		//	url = prop.getProperty("couchBaseUrl");
			 url= prop.getProperty("couchBaseUrl3");
			 bucket = prop.getProperty("couchBaseBucketName");
			 pass =prop.getProperty("couchBaseBucketPassword");
			 try{
			 maximumRetry=new Integer(prop.getProperty("maximumRetry"));
			 }catch(Exception e){
				 logger.error("error while login to couchbase"+e.getMessage());
			 }
			 // 1. Add one or more nodes of your cluster (exchange the IP with
			// yours)
			nodes.add(URI.create(url));
			logger.debug("connecting .....");
			
			client1 = getCouchbaseConnectionOne(nodes, bucket, pass, maximumRetry);
			if(client1==null){
				 url= prop.getProperty("couchBaseUrl1");
				 bucket = prop.getProperty("couchBaseBucketName2");
				 pass =prop.getProperty("couchBaseBucketPassword2");
				 ArrayList<URI> nodes1 = new ArrayList<URI>();
				nodes1.add(URI.create(url));
				 logger.debug(url  +" "+bucket+""+prop.getProperty("couchBaseBucketPassword2"));

				client1=getCouchbaseConnectionTwo(nodes1,bucket, pass, maximumRetry);
				 if(client1==null) {
					 
					 
					 url= prop.getProperty("couchBaseUrl2");
					 bucket = prop.getProperty("couchBaseBucketName2");
					 pass =prop.getProperty("couchBaseBucketPassword2");
					 nodes1 = new ArrayList<URI>();
					nodes1.add(URI.create(url));
					 logger.debug(url  +" "+bucket+""+prop.getProperty("couchBaseBucketPassword2"));

					client1=getCouchbaseConnectionThree(nodes1,bucket, pass, maximumRetry);
				
					if(client1==null){
					 //Send mail error in connecting couchbase
						logger.error("Error in Connecting Couhbase");
					}
					}
			}
		} catch (Exception e) {
			logger.error("error while connecting to couchbase " + e.getMessage());
			
			}
	
		
		return client1;
	}

	public CouchbaseClient getCouchbaseConnectionOne(ArrayList<URI> nodes,String bucketName,String password,int maximumRetry){
		
		
		logger.debug("inside GetcoonectionOne  method of couchbase");
		int retry=1;
		CouchbaseClient client=null;
		while(retry<=maximumRetry && client==null){
		try{
			client=new CouchbaseClient(nodes, bucketName, password);
		}catch(Exception e){
			client=null;
			logger.error(" getCouchbaseConnectionOne  Retry..    "+retry);
			retry+=1;
			logger.error("error in connecting Couchbase one"+e.getMessage());
		}
		
		}
		return client;
	}
	
public CouchbaseClient getCouchbaseConnectionTwo(ArrayList<URI> nodes,String bucketName,String password,int maximumRetry){
	logger.debug("inside GetCouchbaseConectionTwo  method of couchbase");
		int retry=1;
		CouchbaseClient client=null;
		while(retry<=maximumRetry && client==null){
		try{
			logger.debug(nodes +""+ bucketName +""+password );
			client=new CouchbaseClient(nodes, bucketName, password);
		}catch(Exception e){
			client=null;
			logger.error(" getCouchbaseConnectionTwo Retry..   "+retry);
			retry+=1;
			logger.error("error in connecting Couchbase two"+e.getMessage());
		}
		
		}
		return client;
	}


public CouchbaseClient getCouchbaseConnectionThree(ArrayList<URI> nodes,String bucketName,String password,int maximumRetry){
	logger.debug("inside GetCouchbaseConectionThree  method of couchbase");
		int retry=1;
		CouchbaseClient client=null;
		while(retry<=maximumRetry && client==null){
		try{
			logger.debug(nodes +""+ bucketName +""+password );
			client=new CouchbaseClient(nodes, bucketName, password);
			
		}catch(Exception e){
			client=null;
			logger.error(" getCouchbaseConnectionThree Retry..   "+retry);
			retry+=1;
			logger.error("error in connecting Couchbase three"+e.getMessage());
		}
		
		}
		return client;
	}

	public void storeDataInCouchBase(String key, JSONObject string) {
		logger.debug("inside storeDataInCouchBase method of CouchBaseOperation class");

		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");            //get current date time with Calendar()
        Calendar cal = Calendar.getInstance();
        String currentDateTime=(dateFormat.format(cal.getTime())); 
        try{
        string.put("Submission_Date_Time1b", currentDateTime);
        }catch(Exception e){
        	
        }
		try {
			client1 = getConnectionToCouchBase();
			client1.set(key, string.toString());
			closeCouchBaseConnection();
		} catch (Exception e) {
			logger.error("error while storing data into couchbase : "+e.getMessage());
		}

	}

public void  updateDataInCouchBase(String key, JSONObject editData){
		
		
		try {
			logger.debug("inside editDataInCouchBase method of CouchBaseOperation class");

			client1 = getConnectionToCouchBase();
			JSONObject json=new JSONObject(client1.get(key).toString());
			logger.debug("old data -------------------------"+json);
			Iterator<String> keysItr = editData.keys();
	
			while (keysItr.hasNext()) {
				String keyString = keysItr.next();
			Object value = editData.get(keyString);
						json.put(keyString, value);
			}
			logger.debug("editing data...");
			logger.debug("new data -------------------------"+json);
			try{
				json.remove("bytes");
			}catch(Exception e){
				logger.error("error while editing data inside couchbase : "+e.getMessage());
			}
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");            //get current date time with Calendar()
	        Calendar cal = Calendar.getInstance();
	        String currentDateTime=(dateFormat.format(cal.getTime())); 
	        try{
	        	json.put("Submission_Date_Time1b", currentDateTime);
	        }catch(Exception e){
	        	logger.error("error while submitting Current date & time : "+e.getMessage()); 	
	        }
			client1.replace(key, json.toString());

			closeCouchBaseConnection();

		} catch (Exception e) {		
			logger.error("error while  connection with couchbase : "+e.getMessage());
		}
	}

	public void closeCouchBaseConnection() {
		logger.debug("closing connection");
		client1.shutdown(9000l, TimeUnit.MILLISECONDS);
	}		
}
