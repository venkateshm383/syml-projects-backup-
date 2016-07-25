package com.syml.openerp;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.codehaus.jettison.json.JSONObject;

import com.couchbase.client.CouchbaseClient;
import com.couchbase.client.protocol.views.DesignDocument;
import com.couchbase.client.protocol.views.Query;
import com.couchbase.client.protocol.views.View;
import com.couchbase.client.protocol.views.ViewDesign;
import com.couchbase.client.protocol.views.ViewResponse;
import com.couchbase.client.protocol.views.ViewRow;
import com.syml.couchbase.CouchBaseOperation;




public class LeadTaskCreationRestcall extends Thread {
	
	
	
	
	String documentlIst;
	
	public LeadTaskCreationRestcall(String documentlIst) {
		super();
		this.documentlIst = documentlIst;
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		restCallLeadTasks(documentlIst);
	}
public  void restCallLeadTasks(String documentList){
	try{
	JSONObject json=new JSONObject("documentList");
createView(json.get("leadid").toString());
	}catch(Exception e){
		e.printStackTrace();
	}
				 System.out.println("inside the leadd taskcreation------------------------->");
			try{	  SSLContext ctx;
					
					ctx = SSLContext.getInstance("TLS");
				
		        ctx.init(new KeyManager[0], new TrustManager[] {(TrustManager) new DefaultTrustManager()}, new SecureRandom());
		        SSLContext.setDefault(ctx);

		        URL url = new URL("https://task.visdom.ca/leaddata");
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
	
	/*	jsonTableData.put("name", name);
		jsonTableData.put("phonenumber", phoneNumber);*/

		jsonTableData.put("leadid", "111");
		jsonTableData.put("leadName","DevTest");
		jsonTableData.put("leadPhone","555-651-7444");
		jsonTableData.put("leadReferralName","DevTestRefer");
		jsonTableData.put("leadReferralPhone","555-555-334");
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	//restCallReferralTaskCreation(jsonTableData.toString());
//restCallLeadTasks(jsonTableData.toString());
}

static CouchbaseClient client1 = null;

public static void createView(String projectName) {
	
	
	

		String projectId = "0";
		try {
			client1 = new CouchBaseOperation().getConnectionToCouchBase();
			DesignDocument designdoc = getDesignDocument("dev_documenlistTask"
					+ projectName);
			boolean found = false;

			// 5. get the views and check the specified in code for existence
			for (ViewDesign view : designdoc.getViews()) {
				if (view.getMap() == "documenlistTask" + projectName) {
					found = true;
					break;
				}
			}

			// 6. If not found then create view inside document
			if (!found) {
				ViewDesign view = new ViewDesign("documenlistTask"
						+ projectName, "function (doc, meta) {\n"
						+ "if(doc.ProjectName_==\""+projectName.trim()+"\")"
						
						+ "{emit(meta.id,null)}\n" +

						"}");

				designdoc.getViews().add(view);
				client1.createDesignDoc(designdoc);
			}
			client1.shutdown(9000l, TimeUnit.MILLISECONDS);

			// 7. close the connection with couchbase
view(projectName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	//	list=view(projectName);
		

	}
	
	

	


public static ArrayList view (String projectName){
	
	String projectId = "0";
	ArrayList list1 = new ArrayList();
	try {
		CouchbaseClient client2 = new CouchBaseOperation().getConnectionToCouchBase();
		System.setProperty("viewmode", "development");
		// get the view
		View view = client2.getView("dev_documenlistTask" + projectName,
				"documenlistTask" + projectName);
		// create Query.
		System.out.println(view.getViewName());
		Query query = new Query();
		query.setIncludeDocs(true).setLimit(1);

		// get ViewResponse
		ViewResponse viewRes = client2.query(view, query);
		// Iterate over the ViewResponse
		for (ViewRow row : viewRes) {
			//++dataExsist;
			JSONObject jsonData = new JSONObject(row.getDocument()
					.toString());
		/*	JSONObject josnInsidedata = (JSONObject) jsonData
					.get("event_data");
			System.out.println("datat " + josnInsidedata);
			projectId = josnInsidedata.get("id").toString();
			User user = new CouchBaseOperation()
					.getTodoistUserEmail(jsonData.get("username")
							.toString());
			token = user.getToken();
			System.out.println("user  "+projectId);
			ProjectDetails project = new ProjectDetails();
			project.setProjectId(projectId);
			project.setToken(token);
			list1.add(project);*/
			System.out.println("lsit "+list1.size());
		}
		try{
			client2.shutdown(9000l, TimeUnit.MILLISECONDS);
		}catch(Exception e){
			e.printStackTrace();
		}
	/*	if(list1.isEmpty()){
			
			view(projectName);
			//}
		}*/
		
		
	} catch (Exception e) {
		e.printStackTrace();
		System.out.println("error is : " + e);
	}
	System.out.println("lsit --------------->"+list1.size());

	return list1;
}
private static DesignDocument getDesignDocument(String name) {
		try {

			System.out.println("Design document with " + name + " exist ? "
					+ client1.getDesignDoc(name));

			return client1.getDesignDoc(name);
		} catch (Exception e) {
			return new DesignDocument(name);
		}
}

}
