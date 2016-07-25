package infrastracture;

import java.io.IOException;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.codehaus.jettison.json.JSONObject;

import play.Logger;

import com.debortoliwines.openerp.api.FilterCollection;
import com.debortoliwines.openerp.api.ObjectAdapter;
import com.debortoliwines.openerp.api.OpeneERPApiException;
import com.debortoliwines.openerp.api.Row;
import com.debortoliwines.openerp.api.RowCollection;
import com.debortoliwines.openerp.api.Session;
import com.debortoliwines.openerp.api.OpenERPXmlRpcProxy.RPCProtocol;
import com.syml.util.OpenERPSessionUtil;

import controllers.GetPostgressConnection;


public class Odoo extends Thread {
	private static org.slf4j.Logger logger = play.Logger.underlying();
	
	static Session openERPSession = null;
public static void main(String[] args) {
openERPSession=	new Odoo().getOdooConnection();
getReferralname("3489");
}
	public Session getOdooConnectionProdyc() {
		String ip = null;
		int port = 0;
		String host = null;
		String db = null;
		String user = null;
		String pass = null;

		try {

			Properties prop = readConfigfile();

			ip = prop.getProperty("odooIP");
			try {
				port = new Integer(prop.getProperty("odooPort"));
			} catch (Exception e) {
				logger.error("error in getting the property file" +e.getMessage());
			}
			db = prop.getProperty("odooDB");
			user = prop.getProperty("odooUsername");
			pass = prop.getProperty("odoopassword");

			openERPSession = new Session(ip, port, db, user, pass);
			openERPSession.startSession();

		} catch (Exception e) {
			logger.error("OpenERP  Propoerty  Gettting  Error"+e.getMessage());
		}
		
    logger.error("session data "+openERPSession);
		return openERPSession;
	}

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

			logger.debug("inside getOdooConnection method of Generic Helper  class");
			Properties prop = readConfigfile();
			logger.debug("stg---"
					+ prop.getProperty("submitReferralstageId") + "---"
					+ prop.getProperty("visdomreferralStageId") + "----"
					+ prop.getProperty("opprtunitySatgeid"));
			// ip=prop.getProperty("odooIP");
			host = prop.getProperty("odooHost1");
			try {
				port = new Integer(prop.getProperty("odooPort"));
			} catch (Exception e) {
				logger.error("error while getting connection with Odoo" +e.getMessage());
			}
			db = prop.getProperty("odooDB");
			user = prop.getProperty("odooUsername1");
			pass = prop.getProperty("odoopassword1");
			maximumRetry = new Integer(prop.getProperty("maximumRetry"));
			

			openERPSession = getCRMConnectionOne(host, port, db, user, pass,
					maximumRetry);
			if (openERPSession == null) {
				logger.debug("error in getCRMConnectionOne");
				host = prop.getProperty("odooHost2");
				port = new Integer(prop.getProperty("odooPort2"));
				db = prop.getProperty("odooDB2");
				user = prop.getProperty("odooUsername2");
				pass = prop.getProperty("odoopassword2");

				openERPSession = getCRMConnectionTwo(host, port, db, user,
						pass, maximumRetry);

				if (openERPSession == null) {
					logger.debug("error in getCRMConnectionTwo");
				} else {
					logger.debug("connection successful");
				}

			}

		} catch (Exception e) {

			logger.error("error in connectiong with odoo : " +e.getMessage());
		}

		return openERPSession;
	}

	public Session getCRMConnectionOne(String host, int port, String db,
			String user, String pass, int maximumRetry) {

		logger.debug("inside GetCRMConectionOne  method of GenericHelperClass");
		int retry = 1;
		Session openERPSession = null;
		while (retry <= maximumRetry && openERPSession == null) {
			try {
				openERPSession = new Session(host, port, db, user, pass);
				openERPSession.startSession();
			} catch (Exception e) {
				openERPSession = null;
				logger.error(" GetCRMConectionOne  Retry..    " + retry);
				retry += 1;
				logger.error("error in connecting GetCRMConectionOne "
						+ e.getMessage());
			}
		}
		return openERPSession;
	}

	public Session getCRMConnectionTwo(String host, int port, String db,
			String user, String pass, int maximumRetry) {

		logger.debug("inside GetCRMConectionTwo method of GenericHelperClass");
		int retry = 1;
		Session openERPSession = null;
		while (retry <= maximumRetry && openERPSession == null) {
			try {
				openERPSession = new Session(host, port, db, user, pass);
				openERPSession.startSession();
			} catch (Exception e) {
				openERPSession = null;
				logger.debug(" GetCRMConectionTwo  Retry..    " + retry);
				retry += 1;
				logger.error("error in connecting GetCRMConectionTwo "
						+e.getMessage());
			}

		}
		return openERPSession;
	}

	public static ArrayList getReferralname(String opprtunityId) {

		RowCollection referralList = null;
		ArrayList list = new ArrayList();
		try {

			Session openERPSession = new Odoo().getOdooConnection();

			ObjectAdapter lead = openERPSession.getObjectAdapter("crm.lead");

			FilterCollection filters2 = new FilterCollection();
			filters2.add("id", "=", opprtunityId);

			RowCollection leadRow = lead.searchAndReadObject(filters2,
					new String[] { "id", "referred_source" });

			for (Iterator iterator = leadRow.iterator(); iterator.hasNext();) {
				Row row1 = (Row) iterator.next();

				ObjectAdapter referral = openERPSession
						.getObjectAdapter("hr.applicant");

				FilterCollection filters1 = new FilterCollection();
				filters1.add("id", "=", row1.get("referred_source"));

				referralList = referral.searchAndReadObject(filters1,
						new String[] { "email_from", "name", "role" });
				logger.debug(referralList.size() + "id");

				for (Row row : referralList) {
					list.add(row.get("name").toString());
					list.add(row.get("email_from").toString());
					list.add(row.get("role").toString());
				}
			}
		} catch (Exception e) {
			logger.error(">>> OpenERP  Object  Error: " + e.getMessage());

		}

		return list;
	}

	public Properties readConfigfile() {

		Properties prop = new Properties();
		
			try {
				prop.load(Odoo.class.getClassLoader().getResourceAsStream(
						"config.properties"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error(">>> config.properties  not  Found: "+e.getMessage());
			}
		
		return prop;
	}

	public void getOpprunityDetials() {
		try {

			Session openERPSession = new Odoo().getOdooConnection();

			ObjectAdapter lead = null;
			try {
				lead = openERPSession.getObjectAdapter("crm.lead");
			} catch (XmlRpcException e2) {
				// TODO Auto-generated catch block
				logger.error(">>> XMlRPC  error  while  Getting  Object  Adaptor: " + e2.getMessage());
			} catch (OpeneERPApiException e2) {
				// TODO Auto-generated catch block
				logger.error(">>> OpeneERPApiException  while  getting  ObjectAdapter: " + e2.getMessage());
			}

			FilterCollection filters2 = new FilterCollection();

			RowCollection leadRow = null;
			try {
				leadRow = lead.searchAndReadObject(filters2,
						new String[] { "id", "name", "create_date" });
			} catch (XmlRpcException e1) {
				// TODO Auto-generated catch block
				logger.error(">>> XMlRPC  Exception: " + e1.getMessage());
			} catch (OpeneERPApiException e1) {
				// TODO Auto-generated catch block
				logger.error(">>> OpeneERPApiException: " + e1.getMessage());
			}

			for (Iterator iterator = leadRow.iterator(); iterator.hasNext();) {
				Row row1 = (Row) iterator.next();

				int days = 0;
				Calendar calendar1 = Calendar.getInstance();
				Calendar calendar2 = Calendar.getInstance();
				Date dateString = (Date) row1.get("create_date");
				calendar1.setTime(dateString);
				SimpleDateFormat formatter = new SimpleDateFormat(
						"yy/MM/dd HH:mm:ss");
				String date2 = formatter.format(calendar2.getTime());
				String date1 = formatter.format(calendar1.getTime());
				// logger.debug("date 1"+ date2 +"dat3 "+ date1);
				try {
					days = (int) ((formatter.parse(date2).getTime() - formatter
							.parse(date1).getTime()) / (1000 * 60 * 60 * 24));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					logger.error(">>> Date  Parsing  Exception: " +e.getMessage());
				}

				if (days < 150) {

				}

			}
		} catch (NullPointerException e) {
			logger.error(">>> Error while retrieving OpenERP Databases: "+e.getMessage());
		}

	}

	CouchBaseOperation couchbase = new CouchBaseOperation();

	public String getOpprtunityName(String id) {

		String opporunityName = "";
		try {
			
			
			openERPSession=getOdooConnection();
			
			ObjectAdapter applicants=openERPSession.getObjectAdapter("applicant.record");
					FilterCollection filter1=new FilterCollection();
					filter1.add("id", "=",id);
					RowCollection row2=applicants.searchAndReadObject(filter1,new String[]{"id","applicant_name","applicant_last_name","email_personal","opp_rec_ids"});
					
					Object [] object=null;
					String opprunityId=null;
					for (Row row3 : row2){

						try{
					 object=(Object[])row3.get("opp_rec_ids");
						}catch(Exception e){
							logger.error("Error occured while proocessing objects updating in row "+e.getMessage());	
						}
						
					}
			
			Session openERPSession = new Odoo().getOdooConnection();
			logger.debug("Seesion is" + openERPSession);
			ObjectAdapter opprtunity = openERPSession
					.getObjectAdapter("crm.lead");

			FilterCollection filter = new FilterCollection();
			filter.add("id", "=", object[0]);
			RowCollection row = opprtunity.searchAndReadObject(filter,
					new String[] { "id", "name", "email_from" });
			Row row1 = row.get(0);
			opporunityName = row1.get("name").toString();

		} catch (Exception e) {
			logger.error("error while connecting to couchbase" +e.getMessage());
		}

		return opporunityName;
	}
	
	public String getApplicantID(String  id){
		String applicantid="0";
		try {
			Session openERPSession=	new Odoo().getOdooConnection();
			logger.debug("Seesion is"+openERPSession);
		ObjectAdapter opprtunity=openERPSession.getObjectAdapter("crm.lead");
		
		FilterCollection filter=new FilterCollection();
		
		logger.debug("opporunit id--------------------"+id);
		filter.add("id", "=", id.trim());
		RowCollection row=opprtunity.searchAndReadObject(filter,new String[]{"id","app_rec_ids"});
		Row row1=row.get(0);
		
	

		Object [] object=(Object[])row1.get("app_rec_ids");
		
		applicantid=object[0].toString();
		
		logger.debug("------------------------applicant id  to pass docuementanalyzer appp-------------------------- "+applicantid);
		}catch(Exception e){
			logger.error("error while connecting to Odoo" +e.getMessage());
		}
		return applicantid;
		}
	
	
	
	//get appplicabtData through database 
	/*public String getApplicantID(String  id){
		String applicantid="0";
		Connection connection=null;
		
		try{
			
			connection=GetPostgressConnection.getPostGressConnection();
		
				Statement stmt11 = connection.createStatement();
				ResultSet rs11 = stmt11
						.executeQuery("select row_to_json(crm_lead) as crm_record from crm_lead where id ="
								+id);
				// logger.debug("********hr_applicant_backup_tbl Data IS*****"+rs11.getString("hr_applicant_backup_tbl").toString());
				while (rs11.next()) {
					logger.debug("*******applicant**************");
					logger.debug(rs11.getString("applicant_record"));
					json = new JSONObject(
							rs11.getString("applicant_record"));

				}
				rs11.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
	try{
	
		connection=GetPostgressConnection.getPostGressConnection();
	
			Statement stmt11 = conn.createStatement();
			ResultSet rs11 = stmt11
					.executeQuery("select row_to_json(applicant_record) as applicant_record from applicant_record where id ="
							+applicantId);
			// logger.debug("********hr_applicant_backup_tbl Data IS*****"+rs11.getString("hr_applicant_backup_tbl").toString());
			while (rs11.next()) {
				logger.debug("*******applicant**************");
				logger.debug(rs11.getString("applicant_record"));
				json = new JSONObject(
						rs11.getString("applicant_record"));

			}
			rs11.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	String aplicant_name=(String)json.get("applicant_name");
	String aplicant_lastname=(String)json.get("applicant_last_name");
	}*/
	
	public static class DefaultTrustManager implements X509TrustManager {

		@Override
		public void checkClientTrusted(X509Certificate[] arg0, String arg1)
				throws CertificateException {
		}

		@Override
		public void checkServerTrusted(X509Certificate[] arg0, String arg1)
				throws CertificateException {
		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}

	}

	public static void sslInit() {
		try {
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext
					.init(new KeyManager[0],
							new TrustManager[] { (TrustManager) new DefaultTrustManager() },
							new SecureRandom());
			SSLContext.setDefault(sslContext);
			//logger.debug(">>> SSL Initialization done.");
		} catch (Exception e) {
			logger.error("SSLContext  Error  for  TLs"+e.getMessage());

		}
	}
	
	public static Session initSession() {
		Session session = null;
		boolean isDevelopmentMode = true;
		RPCProtocol protocol = isDevelopmentMode ? RPCProtocol.RPC_HTTPS
				: RPCProtocol.RPC_HTTP;

		// logger.debug("Will connecting to OpenERP using: {} protocol.",
		// protocol);

		
			sslInit();
			session = new Session(RPCProtocol.RPC_HTTPS, "dev-crm.visdom.ca", 443,
					"symlsys", "guy@visdom.ca", "VisdomTesting");
			try {
				session.startSession();
				return session;
			} catch (Exception e) {
				// logger.warn(">>> Cannot connect to OpenERP server:{}. Attempt to connect to:{}",
				// config.getOpenERPURL1(), config.getOpenERPURL2());
				sslInit();
				session = new Session("http://52.21.109.221", 8069,
						"symlsys", "guy@visdom.ca", "VisdomTesting");
				try {
					session.startSession();
					return session;
				} catch (Exception ex) {
					// logger.error(">>> Cannot connect to OpenERP server:{}. All Open ERP Server is down.",
					// config.getOpenERPURL2());
					logger.error("error while connecting to OpenERP" +e.getMessage());
					throw new RuntimeException(ex);
				}
			}
		
	}

	
	public static void  test(){
		try{
		TrustManager[] trustManagers = new TrustManager[] {
				new X509TrustManager() {
					public X509Certificate[] getAcceptedIssuers() { return null; }
					public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
					public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
				}
		};
		SSLContext sslContext = SSLContext.getInstance("SSL");
		sslContext.init(null, trustManagers, new java.security.SecureRandom());

		XmlRpcClient xmlrpcDb = new XmlRpcClient();
		XmlRpcClientConfigImpl xmlrpcConfigDb = new XmlRpcClientConfigImpl();
		xmlrpcConfigDb.setServerURL(new URL("https","dev-crm.visdom.ca", 443,"/xmlrpc/db"));

		xmlrpcDb.setConfig(xmlrpcConfigDb);

		try {
			//Retrieve databases only
			List<Object> params = new ArrayList<Object>();
			Object result = xmlrpcDb.execute("list", params);
			Object[] a = (Object[]) result;
			List<String> res = new ArrayList<String>();
			for (int i = 0; i < a.length; i++) {
				if (a[i] instanceof String) {
					res.add( (String)a[i] );
				}
			}
			logger.debug(">>> Databases : " + res.toString());
		} catch (XmlRpcException e) {
			logger.error(">>> XmlException Error while retrieving OpenERP Databases: "+e.getMessage());
	
		} 

		}catch(Exception e){
			logger.error(">>> Error while retrieving OpenERP Databases: " +e.getMessage());
		}
	}
}
