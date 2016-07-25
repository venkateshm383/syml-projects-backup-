package com.syml.helper;

import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.debortoliwines.openerp.api.FilterCollection;
import com.debortoliwines.openerp.api.ObjectAdapter;
import com.debortoliwines.openerp.api.OpenERPXmlRpcProxy.RPCProtocol;
import com.debortoliwines.openerp.api.Row;
import com.debortoliwines.openerp.api.RowCollection;
import com.debortoliwines.openerp.api.Session;
import com.syml.openerp.RestCallClass;

public class test {
	public static void main(String [] args) throws JSONException {
		new test().getGreeting();
	}
	
	
	public static void mains() {
		try{
			RowCollection referralList=null;
			
				
				
				
				 SSLContext ctx;
					
					ctx = SSLContext.getInstance("TLS");
				
		     ctx.init(new KeyManager[0], new TrustManager[] {(TrustManager) new DefaultTrustManager()}, new SecureRandom());
		     SSLContext.setDefault(ctx);

		     URL url = new URL("https://dev-crm.visdom.ca");
		     HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		     conn.setHostnameVerifier(new HostnameVerifier() {
		         @Override
		         public boolean verify(String arg0, SSLSession arg1) {
		             return true;
		         }

					
		     });
		     //"Kendall ", "Raessler ", "kENDALL@VISDOM.CA"

				String form1Email="kENDALL@VISDOM.CA";
				String form1Fname="Kendall";
				String form1Lname="Raessler";
			Session			openERPSession = new Session(RPCProtocol.RPC_HTTPS,"dev-crm.visdom.ca" ,443, "symlsys", "guy@visdom.ca", "VisdomTesting");
			openERPSession.startSession();
				System.out.println(openERPSession);
				ObjectAdapter partnerAd = openERPSession.getObjectAdapter("res.partner");
				ObjectAdapter referral=openERPSession.getObjectAdapter("hr.applicant");

				FilterCollection filters=new FilterCollection();
				filters.add("email","=",form1Email.toLowerCase().trim());
			    
			    RowCollection partners = partnerAd.searchAndReadObject(filters, new String[]{"email","name","last_name"});
			    
			    
			    for (Row row : partners){
			    		
			    		if(row.get("name").toString().trim().equalsIgnoreCase(form1Fname.trim()) && row.get("last_name").toString().trim().equalsIgnoreCase(form1Lname.trim())){
			    			FilterCollection filters1=new FilterCollection();
							filters1.add("partner_id","=",row.getID());
						    
						   referralList= referral.searchAndReadObject(filters1, new String[]{"id","name","partner_phone"});
						   	System.out.println(referralList.size()+"id");
			    		}
			    	
			    }
			    
			    
			    for (Row row : referralList){
					if(row
							.get("partner_phone")!=null){
						System.out.println(row
							.get("partner_phone").toString());
			    	//jsonTableData.put("leadReferralPhone", row
						//	.get("partner_phone").toString());
					}
				}

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
	
	public static void main1(String[] args) {
		
		String id="123";
		String refFirstName="referrFisrtname";
		String refPhone="676-565-5566";
		String form1Fname="promod";
		String refEmail="venkatesh.m@bizruntime.com";
		org.codehaus.jettison.json.JSONObject jsonTableData=new org.codehaus.jettison.json.JSONObject();
		try {
			jsonTableData.put("leadid", id);
			jsonTableData.put("leadName", refFirstName);
			jsonTableData.put("leadPhone", refPhone);
			jsonTableData.put("leadReferralName", form1Fname);
			jsonTableData.put("leadReferralPhone", "");
		
		} catch (org.codehaus.jettison.json.JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 RowCollection referralList=null;
		try{
			
		Session	openERPSession=new GenericHelperClass().getOdooConnection();
		ObjectAdapter referral=openERPSession.getObjectAdapter("hr.applicant");

		
	
	    		
	    			FilterCollection filters1=new FilterCollection();
					filters1.add("email_from","=",refEmail.toLowerCase().trim());
				    
				   referralList= referral.searchAndReadObject(filters1, new String[]{"email_from","name","partner_phone"});
				   	System.out.println(referralList.size()+"id");
	    	
	    
	    
	    
	    for (Row row : referralList){
			if(row
					.get("partner_phone")!=null){
	    	jsonTableData.put("leadReferralPhone", row
					.get("partner_phone").toString());
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	    

		System.out.println("data" +jsonTableData);
		
	//storeData.storeDataInCouchBase(key,formType,dataStoreValue);
	//	storeData.appendDataInCouchBase(key, dataStoreValue1);
		//pw.print("Thank you for submitting a referral to Visdom Mortgage Solutions.  Please check your inbox for for a confirmation email that we have received your referral and have contacted them by email.  In the event you do not see an email from Visdom, please check the spam folder to ensure your mail provider has not accidentally mislabelled it.");
		//send mail if everthing went smoothly
		//pw.println(message);
		//RestCallClass.restCallLeadTasks(jsonTableData.toString());
		
	}
	
	  public String getGreeting(){
	    	String greeting="";
	    	Calendar time = new GregorianCalendar();  
	    	TimeZone timeZone = TimeZone.getDefault();
String test="test test";
System.out.println(test.trim());
System.out.println(""+test.trim().equalsIgnoreCase("test test"));
	    	TimeZone timeZone1 = TimeZone.getTimeZone("Canada/Mountain");
	    					time.setTimeZone(TimeZone.getTimeZone("Canada/Mountain"));
	    	int hour = time.get(Calendar.HOUR_OF_DAY);  
	    	int min = time.get(Calendar.MINUTE);  
	    	int day = time.get(Calendar.DAY_OF_MONTH);  
	    	int month = time.get(Calendar.MONTH) + 1;  
	    	int year = time.get(Calendar.YEAR);  

	    	System.out.println("The current time is \t" + hour + ":" + min);  
	    	System.out.println("Today's date is \t" + month + "/" + day + "/"  
	    	  + year);  

	    	if (hour < 12){
	    		greeting="Good Morning ";
	    	 System.out.println("Good Morning!");  
	    	}else if (hour >12 && hour < 16 ){  
	    		greeting="Good Afternoon";
	    	 System.out.println("Good Afternoon");  
	    	}else if (hour == 12)  {
	    		greeting="Good Noon";

	    	 System.out.println("Good Noon");  
	    	 
	    }else if (hour == 16)  {
			greeting="Good Evening";

		 System.out.println("Good Evening");  
		 
	}
	    	
	    	
	    	else if (hour > 16 && hour<24)  {
	    		greeting="Good Evening";

	    	}else  
	    	 System.out.println("Good Evening"); 
			return greeting;
	    	
	    	
	    	
	    }

}
