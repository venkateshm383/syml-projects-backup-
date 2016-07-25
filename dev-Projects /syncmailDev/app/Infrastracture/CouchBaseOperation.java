package Infrastracture;


import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import net.iharder.Base64;

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
import com.debortoliwines.openerp.api.Session;
import com.fasterxml.jackson.databind.ObjectMapper;

import controllers.EmailList;






public class CouchBaseOperation {
	CouchbaseClient client1 = null;
	static Logger log = LoggerFactory.getLogger(CouchBaseOperation.class);
	ObjectMapper object = new ObjectMapper();
	
	

	public CouchbaseClient getConnectionToCouchBase() {
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

			url ="http://107.23.89.76:8091/pools";
					//prop.getProperty("couchBaseUrl");
			bucket ="syml";
					//prop.getProperty("couchBaseBucketName");
			pass ="symL@0115";
					//prop.getProperty("couchBaseBucketPassword");
			//maximumRetry = new Integer(prop.getProperty("maximumRetry"));

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
			e.printStackTrace();
			log.error("error while connecting to couchbase" + e);

		}
		return client1;
	}

	public CouchbaseClient getConnectionToCouchBasepro() {
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
			 url= "http://198.72.106.5:8091/pools";
					 //prop.getProperty("couchBaseUrl1");
			 bucket ="syml";
					 //prop.getProperty("couchBaseBucketName");
			 pass ="symL@0115";
			 //prop.getProperty("couchBaseBucketPassword");
			 try{
			 maximumRetry=3;
					 //new Integer(prop.getProperty("maximumRetry"));
			 }catch(Exception e){
				 
			 }
			 // 1. Add one or more nodes of your cluster (exchange the IP with
			// yours)
			nodes.add(URI.create(url));
			log.debug("connecting .....");
			
			client1 = getCouchbaseConnectionOne(nodes, bucket, pass, maximumRetry);
			if(client1==null){
				 url="http://198.72.106.10:8091/pools";
						 //prop.getProperty("couchBaseUrl2");
				// bucket = prop.getProperty("couchBaseBucketName2");
				 //pass =prop.getProperty("couchBaseBucketPassword2");
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

	public void storeDataInCouchBase(String key, JSONObject data) {
		log.info("inside storeDataInCouchBase method of CouchBaseOperation class");
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");            //get current date time with Calendar()
        Calendar cal = Calendar.getInstance();
        String currentDateTime=(dateFormat.format(cal.getTime())); 
		try {
			data.put("Submission_Date_Time1b", currentDateTime);
			System.out.println("tests1-----------------.------------");

			client1 = getConnectionToCouchBase();
			client1.set(key, data.toString());

		closeCouchBaseConnection();
		} catch (Exception e) {
			log.error("error while storing data into couchbase : " + e);
		}

	}
	
	public void storeBinaryDataInCouchBase(String key, JSONObject data) {
		log.info("inside storeDataInCouchBase method of CouchBaseOperation class");
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");            //get current date time with Calendar()
        Calendar cal = Calendar.getInstance();
        String currentDateTime=(dateFormat.format(cal.getTime())); 
		try {
			data.put("Submission_Date_Time1b", currentDateTime);
			System.out.println("tests1-----------------.------------");

			client1 = getConnectionToCouchBase();
			client1.set(key, data.toString());
Thread.sleep(60000);
		closeCouchBaseConnection();
		} catch (Exception e) {
			log.error("error while storing data into couchbase : " + e);
		}

	}

public void  updateDataInCouchBase(String key, JSONObject editData){
		
		
		try {
			log.info("inside editDataInCouchBase method of CouchBaseOperation class");
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");            //get current date time with Calendar()
	        Calendar cal = Calendar.getInstance();
	        String currentDateTime=(dateFormat.format(cal.getTime())); 

			client1 = getConnectionToCouchBase();
			editData.put("Submission_Date_Time1b", currentDateTime);

			client1.replace(key, editData.toString());

			closeCouchBaseConnection();

		} catch (Exception e) {
			e.printStackTrace();
			log.error("error while editing data into couchbase : " + e);
		}
	}

	public void closeCouchBaseConnection() {
		log.debug("closing connection");
		client1.shutdown(9000l, TimeUnit.MILLISECONDS);

	}

	
	public ArrayList<EmailList>  getEmailList(){
		ArrayList<EmailList> list=new ArrayList<EmailList>();
		String projectId="0";
		try	{	
			//System.setProperty("viewmode", "development");
			client1 = getConnectionToCouchBase();
			DesignDocument designdoc=getDesignDocument("dev_email");
	        boolean found = false;
	        
	        //5. get the views and check the specified in code for existence
	       for(ViewDesign view:designdoc.getViews()){
	        	if(view.getMap()=="email"){
	        	found=true;
	        	break;
	        	}
	        }
	        
	        //6. If not found then create view inside document
	        if(!found){
	        	ViewDesign view=new ViewDesign("email","function (doc, meta) {\n" +
	        			"if(doc.visdomemailid)\n" +
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
				View view = client1.getView("dev_email", "email");
System.out.println("view "+view.getViewName());
				// create Query.
				Query query = new Query();
				query.setIncludeDocs(true);

				// get ViewResponse
				ViewResponse viewRes = client1.query(view, query);
System.out.println("test "+viewRes.getTotalRows());
				// Iterate over the ViewResponse
				for (ViewRow row : viewRes) {
					
					
					
					
					 JSONObject jsonData =new JSONObject(row.getDocument().toString());
					 ObjectMapper mapper = new ObjectMapper();
			            EmailList emailList = mapper.readValue(jsonData.toString(), EmailList.class);
			            String passord=decryptPassword(emailList.getVisdompassword());
			            passord=passord.substring(6, passord.length());
			            emailList.setVisdompassword(passord);
System.out.println("email list "+jsonData +"key -----------"+row.getId());
emailList.setKey(row.getId());
					 list.add(emailList);
					 
				}
				System.out.println("email list "+list.size());

				closeCouchBaseConnection();

			} catch (Exception e) {
				System.out.println("error is : " + e);
			}
			return list;
		
	}

	
	private  DesignDocument getDesignDocument(String name) {
		try {
			System.out.println("Design document with "+name+" exist ? "+client1.getDesignDoc(name));
	        return client1.getDesignDoc(name);
	    } catch (com.couchbase.client.protocol.views.InvalidViewException e) {
	        return new DesignDocument(name);
	    }
	}
	

	public String encryptPassword(String password){
		StringBuffer secureString = new StringBuffer();
        try
        {
        	password="visdom"+password;
            char myKey = (char)0x15; // binary 10101
            char[] chPass = password.toCharArray();
            for (int i = 0; i < chPass.length; i++)
            {
                chPass[i] = (char)(chPass[i] ^ myKey); // XOR
                secureString.append(chPass[i]);
            }
        }catch(Exception e){
        	
        }
        
       
		
		return secureString.toString();
	}

	public String decryptPassword(String password){
		StringBuffer secureString = new StringBuffer();
        try
        {
 
            char myKey = (char)0x15; // binary 10101
            char[] chPass = password.toCharArray();
            for (int i = 0; i < chPass.length; i++)
            {
                chPass[i] = (char)(chPass[i] ^ myKey); // XOR
                secureString.append(chPass[i]);
            }
        }catch(Exception e){
        	
        }
        
       
		
		return secureString.toString();
	}

	

	


	public JSONObject getDocument(String key){
		JSONObject documentData=null;
		try{
			documentData=new JSONObject(new CouchBaseOperation().getConnectionToCouchBase().get(key).toString());
		}catch(Exception e){
			
		}
		
		return documentData;
		
	}
	
	public void storeAdminDetailsCouhbase(String userName,String password) throws JSONException{
		CouchbaseClient cleint=getConnectionToCouchBase();
		JSONObject json=null;
		
		try{
			json=new JSONObject(cleint.get("syncMail").toString());
			cleint.shutdown();

		}catch(Exception e){
			
		}
		if(json!=null){
			
		}else{
			json=new JSONObject();
			password=encryptPassword(password);
			json.put("username", userName);
			json.put("password", password);
			storeDataInCouchBase("syncMail",json);

		}
	}
	
	public void updateAdminDetailsCouhbase(String userName,String password) {
		CouchbaseClient cleint=getConnectionToCouchBase();
		JSONObject json=null;
		
		try{
			json=new JSONObject(cleint.get("syncMail").toString());
			cleint.shutdown(9000l, TimeUnit.MILLISECONDS);

		}catch(Exception e){
			
		}
		if(json!=null){
try{password=encryptPassword(password);
			json.put("username", userName);
			json.put("password", password);
			updateDataInCouchBase("syncMail",json);
}catch(Exception e){
	
}
}else{
			try{
			json=new JSONObject();
			password=encryptPassword(password);
			json.put("username", userName);
			json.put("password", password);
			updateDataInCouchBase("syncMail",json);
			}catch(Exception e){
				
			}
		}
	}
	
	public  boolean getLogin(String userName, String password) {
		CouchbaseClient cleint=getConnectionToCouchBase();

		JSONObject json=null;
		boolean sucess=false;

try{
		try{
			json=new JSONObject(cleint.get("syncMail").toString());
		
		}catch(Exception e){
			
		}
		if(json!=null){
			String pass=null;
			 pass=decryptPassword(json.get("password").toString().trim());
			 pass=pass.substring(6, pass.length());
			 System.out.println("passs"+pass);
			if(userName.trim().equalsIgnoreCase(json.get("username").toString().trim())&& password.trim().equalsIgnoreCase(pass)){
				sucess=true;
			}else{
				sucess=false;
			}
				
			
		}else{
			sucess=false;
		}
			
}catch(Exception e){
	
}
return sucess;
		
	}
		
	public Thread[] getThreadGroup(){
		Thread[] lstThreads=null;
		try{
		 ThreadGroup currentGroup = 
			      Thread.currentThread().getThreadGroup();
			      int noThreads = currentGroup.activeCount();
			       lstThreads = new Thread[noThreads];
			      currentGroup.enumerate(lstThreads);
			      
	}catch(Exception e){
		
	}
		return lstThreads;
	}
	
	
	static Session openERPSession = null;
	
	public Session getOdooConnectionForProduction() {
		String ip = null;
		int port = 0;
		String host = null;
		String db = null;
		String user = null;
		String pass = null;

		try {

			log.info("inside getOdooConnection method of Generic Helper  class");
			Properties prop = readConfigfile();

			ip = prop.getProperty("odooIP");

			port = new Integer(prop.getProperty("odooPort"));
			db = prop.getProperty("odooDB");
			user = prop.getProperty("odooUsername");
			pass = prop.getProperty("odoopassword");

			openERPSession = new Session(ip, port, db, user, pass);
			openERPSession.startSession();
			log.info("connection successful");

		} catch (Exception e) {
			log.error("error in connectiong with odoo : " + e);
		}

		return openERPSession;
	}

	public Session getOdooConnection() {
		System.out.println("Inside GetOddoConnection");
		String ip = null;
		int port = 0;
		String host = null;
		String db = null;
		String user = null;
		String pass = null;
		int maximumRetry = 0;
		try {

			log.info("inside getOdooConnection method of Generic Helper  class");
			Properties prop = readConfigfile();

			// ip=prop.getProperty("odooIP");
			host = prop.getProperty("odooHost1");
			try{
			port = new Integer(prop.getProperty("odooPort"));
			}catch(Exception e){
				
			}
			db = prop.getProperty("odooDB");
			user = prop.getProperty("odooUsername1");
			pass = prop.getProperty("odoopassword1");
			maximumRetry = new Integer(prop.getProperty("maximumRetry"));
			System.out.println("");

			openERPSession = getCRMConnectionOne(host, port, db, user, pass,maximumRetry);
			if (openERPSession == null) {
				log.warn("error in getCRMConnectionOne");
				host = prop.getProperty("odooHost2");
				port = new Integer(prop.getProperty("odooPort2"));
				db = prop.getProperty("odooDB2");
				user = prop.getProperty("odooUsername2");
				pass = prop.getProperty("odoopassword2");
				

				openERPSession = getCRMConnectionTwo(host, port, db, user,
						pass, maximumRetry);

				if (openERPSession == null) {
					log.warn("error in getCRMConnectionTwo");
				} else {
					log.debug("connection successful");
				}

			}

		} catch (Exception e) {

			log.error("error in connectiong with odoo : " + e);
		}

		return openERPSession;
	}

	public Session getCRMConnectionOne(String host, int port, String db,
			String user, String pass, int maximumRetry) {

		log.debug("inside GetCRMConectionOne  method of GenericHelperClass");
		int retry = 1;
		Session openERPSession = null;
		while (retry <= maximumRetry && openERPSession == null) {
			try {
				openERPSession = new Session(host, port, db, user, pass);
				openERPSession.startSession();
			} catch (Exception e) {
				openERPSession = null;
				log.error(" GetCRMConectionOne  Retry..    " + retry);
				retry += 1;
				log.error("error in connecting GetCRMConectionOne " + e);
			}
		}
		return openERPSession;
	}

	public Session getCRMConnectionTwo(String host, int port, String db,
			String user, String pass, int maximumRetry) {

		log.debug("inside GetCRMConectionTwo method of GenericHelperClass");
		int retry = 1;
		Session openERPSession = null;
		while (retry <= maximumRetry && openERPSession == null) {
			try {
				openERPSession = new Session(host, port, db, user, pass);
				openERPSession.startSession();
			} catch (Exception e) {
				openERPSession = null;
				log.error(" GetCRMConectionTwo  Retry..    " + retry);
				retry += 1;
				log.error("error in connecting GetCRMConectionTwo " + e);
			}

		}
		return openERPSession;
	}
	
	public Properties readConfigfile() {

		Properties prop = new Properties();
		try {
			prop.load(CouchBaseOperation.class.getClassLoader().getResourceAsStream(
					"config.properties"));
		} catch (Exception e) {
			log.error("error in Reading config.properties file" + e);
		}
		return prop;
	}
	
	public static void main(String[] args) throws JSONException {
		System.out.println(new CouchBaseOperation().getConnectionToCouchBase().get("ApplicantTrigerData_524"));
JSONObject		jsonObject1 = new JSONObject();
	String	encrptPassword =new CouchBaseOperation().encryptPassword("VMsolutions123");

		jsonObject1.put("visdomemailid","assistant@visdom.ca" );
		jsonObject1.put("visdompassword", encrptPassword);
		new CouchBaseOperation().storeDataInCouchBase("EmailList_" +UUID.randomUUID(),
				jsonObject1);
	
	}
	
}
