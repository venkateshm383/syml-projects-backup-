package com.syml.test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;
import org.hibernate.Transaction;
import org.junit.Ignore;
import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.syml.util.ExtendedSession;
import com.syml.util.HibernateUtil;
import com.syml.util.OpenERPSessionUtil;
import com.syml.util.UnderwriteAppConfig;

import couchbase.CouchBaseOperation;

public class UnderwriteAppConfigTest {
	
	
	private static final Logger logger = LoggerFactory.getLogger(UnderwriteAppConfigTest.class);
	@Ignore
	@Test
	public void testAllVariables() {
		UnderwriteAppConfig config = UnderwriteAppConfig.getInstance();
		assertThat(config, is(notNullValue()));
	}
	@Ignore
	@Test
	public void testOpenERPConnection() throws Exception {
		ExtendedSession session = OpenERPSessionUtil.initSession();
		assertThat(session, is(notNullValue()));
	}
	
	@Test
	public void testUsingApacheXMLRPC() throws NoSuchAlgorithmException, KeyManagementException, 
	MalformedURLException {
		TrustManager[] trustAllCerts = new TrustManager[] {
				new X509TrustManager() {
					public X509Certificate[] getAcceptedIssuers() { return null; }
					public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
					public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
				}
		};
		SSLContext sc = SSLContext.getInstance("SSL");
		HostnameVerifier hostnameVerifier = new HostnameVerifier() { 
			public boolean verify(String arg0, SSLSession arg1) { return true; } 
		};
		sc.init(null, trustAllCerts, new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);

		XmlRpcClient xmlrpcDb = new XmlRpcClient();
		XmlRpcClientConfigImpl xmlrpcConfigDb = new XmlRpcClientConfigImpl();
		xmlrpcConfigDb.setServerURL(new URL("https","ringcentral.visdom.ca", 443,"/xmlrpc/db"));

		xmlrpcDb.setConfig(xmlrpcConfigDb);

		try {
			//Retrieve databases
			List<Object> params = new ArrayList<Object>();
			Object result = xmlrpcDb.execute("list", params);
			Object[] a = (Object[]) result;
			List<String> res = new ArrayList<String>();
			for (int i = 0; i < a.length; i++) {
				if (a[i] instanceof String) {
					res.add( (String)a[i] );
				}
			}
			System.out.println(res.toString());
		} catch (XmlRpcException e) {
			System.err.println("XmlException Error while retrieving OpenERP Databases: " + e);
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("Error while retrieving OpenERP Databases: " + e);
			e.printStackTrace();
	  }
	}

	@Test
	@Ignore
	public void testDatabaseConnection() {
		
		
		//UnderwriteAppConfig config = UnderwriteAppConfig.getInstance();
	//	final String url = "jdbc:postgresql://" + config.getDbURL();
		//final String uname = config.getDbUsername();
		//final String pass = config.getDbPassword();
		try {
		//	logger.debug(" url : "+url+" uname : "+uname+ "pass : "+pass);
			logger.debug("Driver Class Name : "+org.postgresql.Driver.class.getName());
			Class.forName(org.postgresql.Driver.class.getName());
			//DriverManager.getConnection("jdbc:postgresql://postgres-01.cxyc85z2tb8q.us-west-2.rds.amazonaws.com:5432/underwriting", "postgres", "SyMl20160510");
			Connection con =DriverManager.getConnection("jdbc:postgresql://postgres-01.cxyc85z2tb8q.us-west-2.rds.amazonaws.com:5432/underwriting","postgres","SyMl20160510");
			logger.debug("get Schema : "+con.getCatalog());
		} catch (ClassNotFoundException e) {
			System.err.println("Cannot load postgre JDBC driver.");
			e.printStackTrace();
		} catch (SQLException e) {
			//System.err.println("Wrong URL/Username/Password: " + url + "/" + uname + "/" + pass);
			e.printStackTrace();
		}
	}
@Ignore
	@Test
	public void testHibernateConnection() {
		logger.debug("inside testHibernateConnection ");
		org.hibernate.Session session = HibernateUtil.getSession();
		Transaction transaction = session.beginTransaction();
		logger.debug("before "+session.isConnected());
		
		testDatabaseConnection();
		if (!session.isConnected()) {
			logger.debug("aftere "+session.isConnected());
			throw new IllegalStateException("HibernateUtil contains wrong configuration!");
		}
		transaction.commit();
	}
	@Ignore
	@Test
	public  void  testCouchBaseConnection(){
		CouchBaseOperation  opp=new  CouchBaseOperation();	
		opp.getConnectionToCouchBase();
		System.out.println("Connection  Success");

	}
}
