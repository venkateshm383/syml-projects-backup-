package infrastracture;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.codehaus.jettison.json.JSONArray;
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
import com.debortoliwines.openerp.api.FilterCollection;
import com.debortoliwines.openerp.api.ObjectAdapter;
import com.debortoliwines.openerp.api.Row;
import com.debortoliwines.openerp.api.RowCollection;
import com.debortoliwines.openerp.api.Session;
import com.fasterxml.jackson.databind.ObjectMapper;

import controllers.Client;

public class CouchBaseOperation {
	CouchbaseClient client1 = null;
	static Logger log = LoggerFactory.getLogger(CouchBaseOperation.class);
	ObjectMapper object = new ObjectMapper();

	//--------------------------------couchbase connection----------------------------------
	
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
		System.out.println("cooooonection");

		try {
			log.info("inside getConnectionToCouchBase method of CouchBaseOperation class");
			// url = prop.getProperty("couchBaseUrl");

			url = "http://107.23.89.76:8091/pools";
			// prop.getProperty("couchBaseUrl");
			bucket = "syml";
			// prop.getProperty("couchBaseBucketName");
			pass = "symL@0115";
			// prop.getProperty("couchBaseBucketPassword");
			// maximumRetry = new Integer(prop.getProperty("maximumRetry"));

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

	public CouchbaseClient getConnectionToCouchBaseProd() {
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
		System.out.println("key " + key);
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); // get
																				// current
																				// date
																				// time
																				// with
																				// Calendar()
		Calendar cal = Calendar.getInstance();
		String currentDateTime = (dateFormat.format(cal.getTime()));
		try {
			data.put("Submission_Date_Time1b", currentDateTime);

			client1 = getConnectionToCouchBase();
			client1.set(key, data.toString());
			closeCouchBaseConnection();
		} catch (Exception e) {
			log.error("error while storing data into couchbase : " + e);
		}

	}

	public void updatedDataInCouchBase(String key, JSONObject editData) {
		try {
			log.info("inside editDataInCouchBase method of CouchBaseOperation class");

			client1 = getConnectionToCouchBase();

			log.debug("editing data...");

			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); // get
																					// current
																					// date
																					// time
																					// with
																					// Calendar()
			Calendar cal = Calendar.getInstance();
			String currentDateTime = (dateFormat.format(cal.getTime()));
			editData.put("Submission_Date_Time1b", currentDateTime);
			client1.replace(key, editData.toString());

			closeCouchBaseConnection();

		} catch (Exception e) {
			log.error("error while editing data into couchbase : " + e);
		}
	}

	public void updateTaskInCouchBase(String key, JSONObject editData) {

		try {
			log.info("inside editDataInCouchBase method of CouchBaseOperation class");

			client1 = getConnectionToCouchBase();
			JSONObject json = null;

			try {
				json = new JSONObject(client1.get(key).toString());
				Iterator<String> keysItr = editData.keys();

				while (keysItr.hasNext()) {
					String keyString = keysItr.next();
					Object value = editData.get(keyString);
					json.put(keyString, value);
				}
			} catch (Exception e) {

			}
			log.debug("editing data...");
			if (json != null) {
				client1.replace(key, json.toString());

			} else {
				client1.set(key, editData.toString());

			}

			closeCouchBaseConnection();

		} catch (Exception e) {
			log.error("error while editing data into couchbase : " + e);
		}
	}

	public void closeCouchBaseConnection() {
		log.debug("closing connection");
		client1.shutdown(9000l, TimeUnit.MILLISECONDS);

	}

//-----------------------------------------openERp connection -------------------------	
	
	Session openERPSession = null;

	// connection useed for production -----------------
			public Session getOdooConnection() {
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
		System.out.println("stg---"+prop.getProperty("submitReferralstageId")+"---"+prop.getProperty("visdomreferralStageId")+"----"+prop.getProperty("opprtunitySatgeid"));
					// ip=prop.getProperty("odooIP");
					host ="crm1.visdom.ca";
							//prop.getProperty("odooHost1");
					try{
					port =8069;
							//new Integer(prop.getProperty("odooPort"));
					}catch(Exception e){
						
					}
					db ="symlsys"; 
							//prop.getProperty("odooDB");
					user ="admin";
							//prop.getProperty("odooUsername1");
					pass ="BusinessPlatform@Visdom1";
							//prop.getProperty("odoopassword1");
					maximumRetry =3; 
							//new Integer(prop.getProperty("maximumRetry"));
					System.out.println("");

					openERPSession = getCRMConnectionOne(host, port, db, user, pass,maximumRetry);
					if (openERPSession == null) {
						log.warn("error in getCRMConnectionOne");
						host ="crm2.visdom.ca";
								//prop.getProperty("odooHost2");
						//port = new Integer(prop.getProperty("odooPort2"));
						//db = prop.getProperty("odooDB2");
						//user = prop.getProperty("odooUsername2");
						//pass = prop.getProperty("odoopassword2");
						

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
	
	
	
	
	//---------------------------------------------referral survey-----------------------------------------
	
	

	
	public Properties readConfigfile() {

		Properties prop = new Properties();
		try {
			prop.load(CouchBaseOperation.class.getClassLoader()
					.getResourceAsStream("config.properties"));
		} catch (Exception e) {
			log.error("error in Reading config.properties file" + e);
		}
		return prop;
	}
	
	
	//-----------------query to get Already paied applicants------------

	public ArrayList<Client> getClientSurveyFromCouchbase(String opprtunityId) {
		boolean exsist = false;
		ArrayList<Client> list = new ArrayList<Client>();
		try {
			client1 = getConnectionToCouchBase();
			DesignDocument designdoc = getDesignDocument("dev_client_"
					+ opprtunityId);
			boolean found = false;

			// 5. get the views and check the specified in code for existence
			for (ViewDesign view : designdoc.getViews()) {
				if (view.getMap() == "client") {
					found = true;
					break;
				}
			}

			// 6. If not found then create view inside document
			if (!found) {
				ViewDesign view = new ViewDesign("client",
						"function (doc, meta) {\n" + "if(doc.opprtunityId==\""
								+ opprtunityId + "\"&& doc.Type==\"Client\")\n"
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
System.out.println("survey detaisl ");
		try {

			// get the view
			View view = client1.getView("dev_client_" + opprtunityId, "client");

			// create Query.
			Query query = new Query();
			query.setIncludeDocs(true);

			// get ViewResponse
			ViewResponse viewRes = client1.query(view, query);

			// Iterate over the ViewResponse
			for (ViewRow row : viewRes) {
System.out.println("survey details ");
				// Parse the Document to a bean User class
				JSONObject object = new JSONObject((String) row.getDocument());
				try {
					Client client = new Client();
					client.setApplicantId(object.get("applicantId").toString());
					client.setOpprtunityId(object.get("opprtunityId")
							.toString());
					try{
					client.setApplicantname(object.get("ClientName").toString());
					}catch(Exception e){
						
					}
					list.add(client);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			client1.shutdown();

		} catch (Exception e) {
			System.out.println("error is : " + e);
		}
		System.out.println("list"+list.size());
		return list;
	}

	public ArrayList<Client> getReferralSurveyFromCouchbase(String opprtunityId) {
		boolean exsist = false;
		ArrayList<Client> list = new ArrayList<Client>();
		try {
			client1 = getConnectionToCouchBase();
			DesignDocument designdoc = getDesignDocument("dev_referral_"
					+ opprtunityId);
			boolean found = false;

			// 5. get the views and check the specified in code for existence
			for (ViewDesign view : designdoc.getViews()) {
				if (view.getMap() == "referral") {
					found = true;
					break;
				}
			}

			// 6. If not found then create view inside document
			if (!found) {
				ViewDesign view = new ViewDesign("referral",
						"function (doc, meta) {\n" + "if(doc.opprtunityId==\""
								+ opprtunityId
								+ "\"&& doc.Type==\"Referral\")\n"
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
			View view = client1.getView("dev_referral_" + opprtunityId,
					"referral");

			// create Query.
			Query query = new Query();
			query.setIncludeDocs(true);

			// get ViewResponse
			ViewResponse viewRes = client1.query(view, query);

			// Iterate over the ViewResponse
			for (ViewRow row : viewRes) {

				// Parse the Document to a bean User class
				JSONObject object = new JSONObject((String) row.getDocument());
				try {
					Client client = new Client();
					client.setOpprtunityId(object.get("opprtunityId")
							.toString());
					client.setApplicantname(object.get("ClientName").toString());
					list.add(client);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			client1.shutdown();

		} catch (Exception e) {
			System.out.println("error is : " + e);
		}
		return list;
	}

	private DesignDocument getDesignDocument(String name) {
		try {
			System.out.println("Design document with " + name + " exist ? "
					+ client1.getDesignDoc(name));
			return client1.getDesignDoc(name);
		} catch (com.couchbase.client.protocol.views.InvalidViewException e) {
			return new DesignDocument(name);
		}
	}

	
	

	

	



}
