package com.syml.util;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import play.Logger;

import com.debortoliwines.openerp.api.OpenERPXmlRpcProxy.RPCProtocol;


public class OpenERPSessionUtil {

	private static org.slf4j.Logger logger = play.Logger.underlying();

	
	public static void main(String[] args) {
		initSession();
	}
	
	public static class DefaultTrustManager implements X509TrustManager {

		@Override
		public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
		}

		@Override
		public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
		
    }

	public static void sslInit() {
		try {
			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(new KeyManager[0], new TrustManager[] {(TrustManager) new DefaultTrustManager()}, new SecureRandom());
			SSLContext.setDefault(sslContext);
			logger.debug(">>> SSL Initialization done.");
		} catch (NoSuchAlgorithmException | KeyManagementException e) {
			logger.error(">>> Error:{}"+e.getMessage());
			throw new RuntimeException(e);
		}
	}

	/**
	 * General contract of this method is that, if you call this instead of 
	 * using <code>new Session( .... )</code>, this method call 
	 * {@link Session#startSession()} automatically, and if failed, will try to 
	 * attempt to connect to second connection.
	 * @return Open ERP {@link Session} object.
	 */
	public static ExtendedSession initSession() {
		ExtendedSession session = null;
		boolean isDevelopmentMode =true;
		RPCProtocol protocol = isDevelopmentMode ? RPCProtocol.RPC_HTTPS : RPCProtocol.RPC_HTTP;

		logger.debug("Will connecting to OpenERP using: {} protocol.", protocol);
		logger.debug("connectin dev crm ---------------->");
		if (isDevelopmentMode) {
			sslInit();
			session = new ExtendedSession(protocol,
        			"dev-crm.visdom.ca", 
        			Integer.valueOf(8069), 
        			"symlsys", 
        			"guy@visdom.ca", 
        			"VisdomTesting");

			boolean connected = false;
			while (!connected) {
			try {
				try{
				session.startSession();
				
				connected = true;
				}catch(Exception e){
					logger.error(">>> Error while start session"+e.getMessage());
				}
          return session;
			} catch (Exception e) {
				logger.error(">>> Error while start session"+e.getMessage());
			}
			}
		} else {
			session = new ExtendedSession("dev-crm.visdom.ca", 
        			Integer.valueOf(8069), 
        			"symlsys", 
        			"guy@visdom.ca", 
        			"VisdomTesting");
			try {
				session.startSession();
			} catch (Exception e) {
				logger.warn(">>> Cannot connect to OpenERP server:{}. Attempt to connect to:{}");
				session = new ExtendedSession("dev-crm.visdom.ca", 
	        			Integer.valueOf(8069), 
	        			"symlsys", 
	        			"guy@visdom.ca", 
	        			"VisdomTesting");
				try {
					session.startSession();
				} catch (Exception ex) {
					logger.error(">>> Cannot connect to OpenERP server:{}. All Open ERP Server is down."+e.getMessage());
					throw new RuntimeException(ex);
				}
			}
		}

		logger.debug(">>> OpenERP Session established.");
		logger.debug("Session  is"+session);
		logger.debug("session returened "+session);
		return session;
	}
	
	
}
