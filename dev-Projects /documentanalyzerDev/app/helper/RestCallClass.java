package helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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



public class RestCallClass {

	
	
	public static void restCallTaskCreater(String documentList){
		
		try {
			
			
			
			
			

			SSLContext ctx;
				
				ctx = SSLContext.getInstance("TLS");
			
	        ctx.init(new KeyManager[0], new TrustManager[] {(TrustManager) new DefaultTrustManager()}, new SecureRandom());
	        SSLContext.setDefault(ctx);
System.out.println("inside taskcreation apppppppp rest call ");
	        URL url = new URL("https://task.visdom.ca/doclist");
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
				

				String input=documentList;
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
						//System.out.println(output);
				
						
						
						
					}
	                                
					}
			 
					conn.disconnect();
			 
				  } catch (Exception e) {
			 
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
	
		
	}


