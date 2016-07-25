package controllers;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

//import org.codehaus.jettison.json.JSONException;
//import org.codehaus.jettison.json.JSONObject;


public class RestCallClass  {

	public static void main(String[] args) {
		//JSONObject json=new JSONObject();

String myvar = "{\"api_password\":\"Syml@123\",\"start_date\":\"2015-07-13\",\"end_date\":\"2015-07-14\"}";		restcallTostagMail(myvar);
	restcallTostagMail(myvar);
	}
	

	public static void restcallTostagMail(String leadData){
		
		try {
			
			
			
			
			

			String ur="https://api.wistia.com/v1/stats/events.json?api_password=6bca8b64faaf09189e8266ed51b726292fd133063ffc1ca0d9fab9fc3661f3ab";
			URLEncoder.encode(ur, "UTF-8");
	        URL url = new URL(ur);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	      
			
			
				conn.setDoOutput(true);
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Content-Type", "application/json");
			//	conn.setRequestProperty("api_password", "Syml@123");
				//conn.setRequestProperty("start_date", "2015-07-11");

				//conn.setRequestProperty("end_date", "2015-07-13");

			

	/*			String input=leadData;
			OutputStream os = conn.getOutputStream();
					os.write(input.getBytes());
					os.flush();*/
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
			 
					conn.disconnect();
			 
				  } catch (Exception e) {
			 
					e.printStackTrace();
			 
				  } 
		
			
		}
	
	



	
	
	
}


