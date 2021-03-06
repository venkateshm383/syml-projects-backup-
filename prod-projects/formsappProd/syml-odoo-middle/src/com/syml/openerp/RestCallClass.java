package com.syml.openerp;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


import org.codehaus.jettison.json.JSONObject;




public class RestCallClass extends Thread {

String documentList;
	
	
	public RestCallClass(String documentList) {
	
	this.documentList = documentList;
}

	
	@Override
		public void run() {
			// TODO Auto-generated method stub
			restCallReferralTaskCreation(documentList);
		}


	public  void restCallReferralTaskCreation(String documentList){
		System.out.println("insdie rest call of the referral page");
		try{
			
			//	URL url = new URL("https://todoist.com/API/v6/login?email=assistant@visdom.ca&password=Visdom1234");
				//URL url = new URL("http:localhost:9000/doclist");
			//SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();

			 // configure the SSLContext with a TrustManager
	        SSLContext ctx;
			
				ctx = SSLContext.getInstance("TLS");
			
	        ctx.init(new KeyManager[0], new TrustManager[] {(TrustManager) new DefaultTrustManager()}, new SecureRandom());
	        SSLContext.setDefault(ctx);

	        URL url = new URL("https://task.visdom.ca/referaldata");
	        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
	       conn.setHostnameVerifier(new HostnameVerifier() {
	            @Override
	            public boolean verify(String arg0, SSLSession arg1) {
	                return true;
	            }

				
	        });
	        
	        conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			
			
		

			String input=documentList.toString();
		OutputStream os = conn.getOutputStream();
				os.write(input.getBytes());
				os.flush();
				if (conn.getResponseCode() != 200) {
					throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
				}else{
		 
				BufferedReader br = new BufferedReader(new InputStreamReader(
						(conn.getInputStream())));
		 
				
				System.out.println("Output from Server .... \n");
	                           String output;
				while ((output = br.readLine()) != null) {
					System.out.println(output);
			
					
					
					
				}
	                            
				}
	        System.out.println(conn.getResponseCode());
	        conn.disconnect();
		}catch(Exception e){
			e.printStackTrace();
			
		}
	    }
			

	    private static class DefaultTrustManager implements X509TrustManager {

	       

			@Override
			public void checkClientTrusted(X509Certificate[] chain, String authType)
					throws CertificateException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void checkServerTrusted(X509Certificate[] chain, String authType)
					throws CertificateException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				// TODO Auto-generated method stub
				return null;
			}
	    }
			
	
	
	public static void main(String[] args) {
		
		JSONObject jsonTableData=new JSONObject();
		String name="devTest";
		String phoneNumber="988-566-655";
		try{	
		
			jsonTableData.put("name", name);
			jsonTableData.put("phonenumber", phoneNumber);
new RestCallClass(jsonTableData.toString()).start();
			/*jsonTableData.put("leadid", "111");
			jsonTableData.put("leadName","DevTest");
			jsonTableData.put("leadPhone","555-651-7444");
			jsonTableData.put("leadReferralName","DevTestRefer");
			jsonTableData.put("leadReferralPhone","555-555-334");
			*/
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//restCallReferralTaskCreation(jsonTableData.toString());
		//restCallLeadTasks(jsonTableData.toString());
	}
	
	/*public static void restCallLeadTasks(String documentList){

		 System.out.println("inside the leadd taskcreation------------------------->");
	try{	  SSLContext ctx;
			
			ctx = SSLContext.getInstance("TLS");
		
       ctx.init(new KeyManager[0], new TrustManager[] {(TrustManager) new DefaultTrustManager()}, new SecureRandom());
       SSLContext.setDefault(ctx);

       URL url = new URL("https://dev-task.visdom.ca/leaddata");
       HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
       conn.setHostnameVerifier(new HostnameVerifier() {
           @Override
           public boolean verify(String arg0, SSLSession arg1) {
               return true;
           }

			
       });
       
       conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		
		
	

		String input=documentList.toString();
	OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
			}else{
	 
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
	 
			
			System.out.println("Output from Server .... \n");
                          String output;
			while ((output = br.readLine()) != null) {
				System.out.println(output);
		
				
				
				
			}
                           
			}
       System.out.println(conn.getResponseCode());
       conn.disconnect();
	}catch(Exception e){
		e.printStackTrace();
	}
   }

*/


/*public static void restCallDocumentListCreation(String documentList) {
	// TODO Auto-generated method stub
	
	
	try{
		
		//	URL url = new URL("https://todoist.com/API/v6/login?email=assistant@visdom.ca&password=Visdom1234");
			//URL url = new URL("http:localhost:9000/doclist");
		//SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();

		 // configure the SSLContext with a TrustManager
       SSLContext ctx;
		
			ctx = SSLContext.getInstance("TLS");
		
        ctx.init(new KeyManager[0], new TrustManager[] {(TrustManager) new DefaultTrustManager()}, new SecureRandom());
        SSLContext.setDefault(ctx);

        URL url = new URL("https//dev-documentanalyzer.visdom.ca/val");
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
      conn.setHostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String arg0, SSLSession arg1) {
                return true;
            }

			
        });
        
        conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		
		
	

		String input=documentList.toString();
	OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
			}else{
	 
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));
	 
			
			System.out.println("Output from Server .... \n");
                           String output;
			while ((output = br.readLine()) != null) {
				System.out.println(output);
		
				
				
				
			}
                            
			}
        System.out.println(conn.getResponseCode());
        conn.disconnect();
	}catch(Exception e){
		
	}*/


	
}
		
		
		






	
	
	
