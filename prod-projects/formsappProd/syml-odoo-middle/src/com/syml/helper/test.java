package com.syml.helper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.codehaus.jettison.json.JSONException;

import com.debortoliwines.openerp.api.FilterCollection;
import com.debortoliwines.openerp.api.ObjectAdapter;
import com.debortoliwines.openerp.api.OpenERPXmlRpcProxy.RPCProtocol;
import com.debortoliwines.openerp.api.Row;
import com.debortoliwines.openerp.api.RowCollection;
import com.debortoliwines.openerp.api.Session;

public class test {
	// Logger logger = LoggerFactory.getLogger(test.class);
static int a=0;
	public static void main(String[] args) throws FileNotFoundException,
			IOException, JSONException {

		Session session = new GenericHelperClass().getOdooConnection();
		try {

			ObjectAdapter product = session.getObjectAdapter("crm.lead");

			FilterCollection productData = new FilterCollection();
			productData.add("id", "=", 791);
			RowCollection productDataRow = product.searchAndReadObject(
					productData, new String[] { "id", "name","hr_department_id",
							"stage_id" });
			Row rowproduct = productDataRow.get(0);
			rowproduct.put("hr_department_id", 1);


			product.writeObject(rowproduct,true);
			
			
			 productData = new FilterCollection();
			productData.add("id", "=", 791);
			 productDataRow = product.searchAndReadObject(
					productData, new String[] { "id", "name","hr_department_id",
							"stage_id" });
			 rowproduct = productDataRow.get(0);

			rowproduct.put("stage_id", 15);

			product.writeObject(rowproduct,true);
			System.out.println("stage changec suceffully ");
			// ObjectAdapter
			// product=session.getObjectAdapter("product.product");

			/*
			 * FilterCollection productData=new FilterCollection();
			 * productData.add("id","=",2479); RowCollection
			 * productDataRow=product.searchAndReadObject(productData, new
			 * String[]{"id","name","opportunity_id"}); Row
			 * rowproduct=productDataRow.get(0);
			 * //rowproduct.put("opportunity_id", 439);
			 * System.out.println(rowproduct.get("opportunity_id"));
			 * System.out.println("product Name"+rowproduct.get("name"));
			 * FieldCollection feildCollection=rowproduct.getFields(); for
			 * (Iterator iterator = feildCollection.iterator();
			 * iterator.hasNext();) { Field field = (Field) iterator.next();
			 * 
			 * System.out.println("filed Name "+field.getName());
			 * 
			 * } boolean sucess=product.writeObject(rowproduct, true);
			 * System.out.println("sucesss"+sucess);
			 */

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

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
			// logger.debug(">>> SSL Initialization done.");
		} catch (Exception e) {

		}
	}

	/**
	 * General contract of this method is that, if you call this instead of
	 * using <code>new Session( .... )</code>, this method call
	 * {@link Session#startSession()} automatically, and if failed, will try to
	 * attempt to connect to second connection.
	 * 
	 * @return Open ERP {@link Session} object.
	 */

	public static void main1(String[] args) {
		System.out.println("session -------------->" + initSession());
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
			session = new Session("http://52.21.109.221", 8069, "symlsys",
					"guy@visdom.ca", "VisdomTesting");
			try {
				session.startSession();
				return session;
			} catch (Exception ex) {
				// logger.error(">>> Cannot connect to OpenERP server:{}. All Open ERP Server is down.",
				// config.getOpenERPURL2());
				throw new RuntimeException(ex);
			}
		}

	}
}
