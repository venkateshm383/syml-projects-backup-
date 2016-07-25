package infrastracture;

import java.io.IOException;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.couchbase.client.CouchbaseClient;
import com.debortoliwines.openerp.api.FilterCollection;
import com.debortoliwines.openerp.api.ObjectAdapter;
import com.debortoliwines.openerp.api.Row;
import com.debortoliwines.openerp.api.RowCollection;
import com.debortoliwines.openerp.api.Session;
import com.fasterxml.jackson.databind.ObjectMapper;


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

			url =prop.getProperty("couchBaseUrl");
			bucket =prop.getProperty("couchBaseBucketName");
			pass =prop.getProperty("couchBaseBucketPassword");
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
			log.error("error while connecting to couchbase" + e);

		}
		return client1;
	}

	public CouchbaseClient getConnectionToCouchBaseForProduction() {
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
			System.out.println("prop.getProperty"
					+ prop.getProperty("couchBaseBucketName"));
		} catch (Exception e) {
			log.error("error in getting the property file" + e);
		}
		try {

			log.info("inside getConnectionToCouchBase method of CouchBaseOperation class");
			// url = prop.getProperty("couchBaseUrl");
			url = prop.getProperty("couchBaseUrl1");
			bucket = prop.getProperty("couchBaseBucketName");
			pass = prop.getProperty("couchBaseBucketPassword");
			maximumRetry = new Integer(prop.getProperty("maximumRetry"));

			// 1. Add one or more nodes of your cluster (exchange the IP with
			// yours)
			nodes.add(URI.create(url));
			log.debug("connecting .....");

			client1 = getCouchbaseConnectionOne(nodes, bucket, pass,
					maximumRetry);
			if (client1 == null) {
				url = prop.getProperty("couchBaseUrl2");
				nodes.add(URI.create(url));
				client1 = getCouchbaseConnectionTwo(nodes, bucket, pass,
						maximumRetry);
				if (client1 == null) {

					// Send mail error in connecting couchbase
					log.error("Error in Connecting Couhbase");
				}
			}
		} catch (Exception e) {
			log.error("error while connecting to couchbase " + e);

		}

		return client1;
	}

	public CouchbaseClient getCouchbaseConnectionOne(ArrayList<URI> nodes,
			String bucketName, String password, int maximumRetry) {

		log.debug("inside GetcoonectionOne  method of couchbase");
		int retry = 1;
		CouchbaseClient client = null;
		while (retry <= maximumRetry && client == null) {
			try {
				client = new CouchbaseClient(nodes, bucketName, password);
			} catch (Exception e) {
				client = null;
				log.error(" getCouchbaseConnectionOne  Retry..    " + retry);
				retry += 1;
				log.error("error in connecting Couchbase one" + e);
			}

		}
		return client;
	}

	public CouchbaseClient getCouchbaseConnectionTwo(ArrayList<URI> nodes,
			String bucketName, String password, int maximumRetry) {
		log.debug("inside GetCouchbaseConectionTwo  method of couchbase");
		int retry = 1;
		CouchbaseClient client = null;
		while (retry <= maximumRetry && client == null) {
			try {
				client = new CouchbaseClient(nodes, bucketName, password);
			} catch (Exception e) {
				client = null;
				log.error(" getCouchbaseConnectionTwo Retry..   " + retry);
				retry += 1;
				log.error("error in connecting Couchbase two" + e);
			}

		}
		return client;
	}

	public void storeDataInCouchBase(String key, JSONObject data) {
		log.info("inside storeDataInCouchBase method of CouchBaseOperation class");
System.out.println("key "+key);
DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");            //get current date time with Calendar()
Calendar cal = Calendar.getInstance();
String currentDateTime=(dateFormat.format(cal.getTime())); 
		try {
			data.put("Submission_Date_Time1b",currentDateTime);
			
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
	
	Session openERPSession=null;
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


	public String getReferralname(String contactId,String email){
		String referralName=null;
		
		try{
			Session openERPSession=	getOdooConnection();
			System.out.println("Seesion is"+openERPSession);
		ObjectAdapter opprtunity=openERPSession.getObjectAdapter("applicant.record");
		
		FilterCollection filter=new FilterCollection();
		if(contactId!=null){
		filter.add("id", "=", contactId);}else if(email!=null){
			filter.add("email_personal", "=", email);
		}
		RowCollection row=opprtunity.searchAndReadObject(filter,new String[]{"id","applicant_name","applicant_last_name","emial_personal"});
		for (Iterator iterator = row.iterator(); iterator.hasNext();) {
			Row row2 = (Row) iterator.next();
			referralName=row2.get("applicant_name").toString()+" "+row2.get("applicant_last_name").toString();

		}

		}catch(Exception e){
			
		}
		
		return referralName;
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
	
	
		
	
		

}
