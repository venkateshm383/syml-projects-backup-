package infrastracture;

import java.io.IOException;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.debortoliwines.openerp.api.FilterCollection;
import com.debortoliwines.openerp.api.ObjectAdapter;
import com.debortoliwines.openerp.api.OpenERPXmlRpcProxy.RPCProtocol;
import com.debortoliwines.openerp.api.Row;
import com.debortoliwines.openerp.api.RowCollection;
import com.debortoliwines.openerp.api.Session;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sendwithus.model.ExtendedSession;
import com.sendwithus.model.OpenERPSessionUtil;
import com.sendwithus.model.UnderwriteAppConfig;

import controllers.Applicants;





public class CouchBaseOperation {
	
	
	
	
	
	
	CouchbaseClient client1 = null;
	static Logger log = LoggerFactory.getLogger(CouchBaseOperation.class);
	ObjectMapper object = new ObjectMapper();
	
	static Session openERPSession = null;
	
	
	/*OpenERPSessionUtil util = new OpenERPSessionUtil();
	 ExtendedSession openERPSession =   util.initSession();

	*/
	
	
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

			///openERPSession = new Session(ip, port, db, user, pass);
			//openERPSession.startSession();
			log.info("connection successful");

		} catch (Exception e) {
			log.error("error in connectiong with odoo : " + e);
		}

		return openERPSession;
	}

	public Session getOdooConnection() {
		
		
		log.debug("Inside GetOddoConnection");
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
			log.debug("");

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


public CouchbaseClient getCouchbaseConnectionThree(ArrayList<URI> nodes,String bucketName,String password,int maximumRetry){
	log.debug("inside GetCouchbaseConectionThree  method of couchbase");
		int retry=1;
		CouchbaseClient client=null;
		while(retry<=maximumRetry && client==null){
		try{
			log.debug(nodes +""+ bucketName +""+password );
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

	
	public CouchbaseClient getConnectionToCouchBaseDev() {
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

			url = prop.getProperty("couchBaseUrl1");
			bucket = prop.getProperty("couchBaseBucketName");
			pass = prop.getProperty("couchBaseBucketPassword");
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

	public CouchbaseClient getConnectionToCouchBase(){
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
			log.debug("prop.getProperty"
					+ prop.getProperty("couchBaseBucketName"));
		} catch (Exception e) {
			log.error("error in getting the property file" + e);
		}
		try {

			log.info("inside getConnectionToCouchBase method of CouchBaseOperation class");
			// url = prop.getProperty("couchBaseUrl");
			url = prop.getProperty("couchBaseUrl2");
			bucket = prop.getProperty("couchBaseBucketName2");
			pass = prop.getProperty("couchBaseBucketPassword2");
			maximumRetry = new Integer(prop.getProperty("maximumRetry"));

			// 1. Add one or more nodes of your cluster (exchange the IP with
			// yours)
			nodes.add(URI.create(url));
			log.debug("connecting .....");

			client1 = getCouchbaseConnectionOne(nodes, bucket, pass,
					maximumRetry);
			if (client1 == null) {
				url = prop.getProperty("couchBaseUrl1");
				nodes.add(URI.create(url));
				client1 = getCouchbaseConnectionTwo(nodes, bucket, pass,
						maximumRetry);
				if (client1 == null) {

					url = prop.getProperty("couchBaseUrl2");
					nodes.add(URI.create(url));
					client1 = getCouchbaseConnectionThree(nodes, bucket, pass,
							maximumRetry);
					
					if(client1==null){
						
						// Send mail error in connecting couchbase
						log.error("Error in Connecting Couhbase");	
					}
					
				
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
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");            //get current date time with Calendar()
        Calendar cal = Calendar.getInstance();
        String currentDateTime=(dateFormat.format(cal.getTime())); 
		try {
			data.put("submissiondate", currentDateTime);
			data.put("Type", "StageChange");

			client1 = getConnectionToCouchBase();
			client1.set(key, data.toString());

		closeCouchBaseConnection();
		} catch (Exception e) {
			log.error("error while storing data into couchbase : " + e);
		}

	}

public void  updateDataInCouchBase(String key, JSONObject editData){
		
	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");            //get current date time with Calendar()
    Calendar cal = Calendar.getInstance();
    String currentDateTime=(dateFormat.format(cal.getTime())); 
	try {
		editData.put("submissiondate", currentDateTime);
			log.info("inside editDataInCouchBase method of CouchBaseOperation class");
			editData.put("Type", "StageChange");

			client1 = getConnectionToCouchBase();
			
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


	int dataExsist=0;
	
	
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
	
	public JSONObject getCouchBaseDataProposal(String key){
		log.debug("Inside Get couchbase data based on key");
		client1=getConnectionToCouchBase();
		String object=(String)client1.get(key);
		
		JSONObject jsonObject=null;
		try {
			jsonObject = new JSONObject(object);
			client1.shutdown();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("error in getting data from couchbase "+e);
		}
		
		return jsonObject;
	}
	
	public ArrayList<Applicants> getApplicants(String opportunityId){
		log.debug("**************Inside Applicant***********************");
		ArrayList<Applicants> list=new ArrayList<Applicants>();
			try {
				Session openERPSession=	getOdooConnection();
				log.debug("Seesion is"+openERPSession);
			ObjectAdapter opprtunity=openERPSession.getObjectAdapter("crm.lead");
			
			FilterCollection filter=new FilterCollection();
			filter.add("id", "=", opportunityId);
			RowCollection row=opprtunity.searchAndReadObject(filter,new String[]{"id","app_rec_ids"});
			Row row1=row.get(0);
			Object [] object=(Object[])row1.get("app_rec_ids");
			log.debug("Object is "+object);
		for (Object object2 : object) {
			log.debug("inside for Of Object");

			
ObjectAdapter applicants=openERPSession.getObjectAdapter("applicant.record");
			log.debug(object2.toString());
			FilterCollection filter1=new FilterCollection();
			filter1.add("id", "=", object2.toString());
			RowCollection row2=applicants.searchAndReadObject(filter1,new String[]{"applicant_name","applicant_last_name","email_personal"});
			
			
			for (Row row3 : row2){
				Applicants applicant=new Applicants();

			    log.debug("Row ID: " + row3.getID());
			    log.debug("Name:" + row3.get("applicant_name"));
			    log.debug("ApplicantLast_Name:" + row3.get("applicant_last_name"));
			    log.debug("Email:" + row3.get("email_personal"));
				applicant.setApplicantFirstName(row3.get("applicant_name").toString());
				applicant.setApplicantlastName(row3.get("applicant_last_name").toString());
				applicant.setApplicantId(object2.toString());
				applicant.setApplicantEmail(row3.get("email_personal").toString());

			    
				list.add(applicant);


			}
		
			
			log.debug("Applicant added");
			
		}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
		
	}
	
	public String getReferralname(String contactId){
		String referralName=null;
		
		try{
			Session openERPSession=	getOdooConnection();
			log.debug("Seesion is"+openERPSession);
		ObjectAdapter opprtunity=openERPSession.getObjectAdapter("res.partner");
		
		FilterCollection filter=new FilterCollection();
		filter.add("id", "=", contactId);
		RowCollection row=opprtunity.searchAndReadObject(filter,new String[]{"id","name"});
		Row row1=row.get(0);
		referralName=row1.get("name").toString();
log.debug("-----------------referral soursce Name--------------------- "+referralName);
		}catch(Exception e){
			
		}
		
		return referralName;
	}
	public Applicants getApplicantDetials(String id) {
		Applicants app=new Applicants();
		ArrayList<Applicants> list=new ArrayList<Applicants>();
		try	{	
			client1 = getConnectionToCouchBase();
			DesignDocument designdoc=getDesignDocument("dev_applicantView"+id);
	        boolean found = false;
	        
	        //5. get the views and check the specified in code for existence
	        for(ViewDesign view:designdoc.getViews()){
	        	if(view.getMap()=="applicant"+id){
	        	found=true;
	        	break;
	        	}
	        }
	        
	        //6. If not found then create view inside document
	        if(!found){
	        	ViewDesign view=new ViewDesign("applicant"+id,"function (doc, meta) {\n" +
	        			"if(doc.opporunity_id=="+id+")\n" +
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
				View view = client1.getView("dev_applicantView"+id, "applicant"+id);

				// create Query.
				Query query = new Query();
				query.setIncludeDocs(true);

				// get ViewResponse
				ViewResponse viewRes = client1.query(view, query);

				// Iterate over the ViewResponse
				for (ViewRow row : viewRes) {
					++dataExsist;
					JSONObject object=new JSONObject((String)row.getDocument());
					//	HashMap<String, String> parsedDoc = gson.fromJson((String)row.getDocument(), HashMap.class);

app.setApplicantFirstName(object.get("FirstName_of_applicant").toString());
app.setApplicantlastName(object.get("LastName_of_applicant").toString());
app.setApplicantEmail(object.get("Email_of_applicant").toString());
String co_Applicnt=null;
try{
	co_Applicnt=object.get("LastName_of_co_applicant").toString();
}catch (Exception e) {
	// TODO: handle exception
}
if(co_Applicnt!=null){
	app.setCoApplicantFirstName(object.get("LastName_of_co_applicant").toString());
	app.setCoApplicantLastName(object.get("FirstName_of_co_applicant").toString());
	app.setCoApplicantEmail(object.get("Email_of_co_applicant").toString());

}


				}
				client1.shutdown();

				
				if(app.getApplicantEmail()==null ){
					if(dataExsist<3){
						++dataExsist;
						getApplicantDetials( id);
					}
				}
			} catch (Exception e) {
				log.error("error is : " + e);
			}
			return app;
			
		}
		
		
		private  DesignDocument getDesignDocument(String name) {
			try {
				log.debug("Design document with "+name+" exist ? "+client1.getDesignDoc(name));
		        return client1.getDesignDoc(name);
		    } catch (com.couchbase.client.protocol.views.InvalidViewException e) {
		        return new DesignDocument(name);
		    }
		}
		
	
		

}
