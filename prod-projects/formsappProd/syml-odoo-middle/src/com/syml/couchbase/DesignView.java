package com.syml.couchbase;

import java.io.File;
import java.net.URI;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import org.codehaus.jettison.json.JSONObject;

import com.couchbase.client.CouchbaseClient;
import com.couchbase.client.protocol.views.DesignDocument;
import com.couchbase.client.protocol.views.Query;
import com.couchbase.client.protocol.views.View;
import com.couchbase.client.protocol.views.ViewDesign;
import com.couchbase.client.protocol.views.ViewResponse;
import com.couchbase.client.protocol.views.ViewRow;


public class DesignView {
static	CouchbaseClient client=null;
	public static void main(String[] args) {
		String content="";

		try{
		ArrayList<URI> nodes=new ArrayList<URI>();
		
		//nodes.add(URI.create("http://107.23.89.76:8091"));
	//	nodes.add(URI.create("http://107.23.89.76:8091/pools"));
		
		//2. create a connection with couchbase server
		client=new CouchBaseOperation().getConnectionToCouchBase();
				//new CouchbaseClient(nodes, "syml","syml@123");
		System.out.println(""+client);
		
	// client=new CouchbaseClient(nodes,{"lastName":"Muchalla","firstname":"Tripta","companyname":"","brokerage_PhoneNumber":"","FormType":"Visdom_Referrel","ip":"127.0.0.1","Using_touch_Screen_Device":"No","phoneNumber":"587-775-0677","email":"tripta_much@hotmail.com","broker_Manages_urwork":"","role":"other","Submission_Date_Time":"2015\/06\/25 14:44:33","compansation":"Direct to Myself","referrer_Tovisdom":"Dale Woodhouse","signature":"Tripta Muchalla","ReferralAgreemetfile_id":"ReferralAgreemetfile_416","smart_Phone_Type":"iPhone"}"default","");
		
		DesignDocument designDocument=DesignView.getDesign("dev_devtest");
		
		
		
		
		boolean found=false;
		for(ViewDesign viewDesign: designDocument.getViews()){
			System.out.println("view"+viewDesign.getMap());
			if(viewDesign.getMap()=="test"){
			found=true;
			break;
		}}
		
		if(!found){
			/*ViewDesign viewDesign=new ViewDesign("info","function(doc,meta){\n " +
					"if(doc.name )\n" +
					"{emit(doc.name,[doc.last_name,doc.phone]);}\n" +
					"}");
		designDocument.getViews().add(viewDesign);*/
			
			String mapFunction="function(doc,meta){\n " +
					"if(doc.opporunity_id)\n" +
					"{emit(meta.id,null);}\n" +
					"}";
		
		ViewDesign viewDesign = new ViewDesign("test",mapFunction);
		designDocument.getViews().add(viewDesign);
	  //  client.createDesignDoc( designDocument );
		
		
		
		
		
		
		//client.createDesignDoc(designDocument);
		System.out.println("done documentview creating");
		
		View view=client.getView("dev_devtest", "test");
		
		Query query=new Query();
		query.setIncludeDocs(true);
		
		
		ViewResponse result=client.query(view, query);
		

System.out.println(result);
	
		for(ViewRow row:result){
			JSONObject object=new JSONObject((String)row.getDocument());
			System.out.println("objcet "+object);
		/*	try{
				String data= object.get("Mortgage Brokerage Disclosures_id").toString();

				System.out.println("referral Agreement"+data);
				JSONObject jsonObject=new JSONObject(client.get(data).toString());
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");            //get current date time with Calendar()
	            Calendar cal = Calendar.getInstance();
	            String currentDateTime=(dateFormat.format(cal.getTime())); 
				//jsonObject.put("Submission_Date_Time1b", currentDateTime);
				
				System.out.println("deleted data--- "+jsonObject);
				//client.replace(data, jsonObject.toString());
				
			}catch(Exception e){
			e.printStackTrace();	
			}*/
		//	HashMap<String, String> parsedDoc = gson.fromJson((String)row.getDocument(), HashMap.class);
			//System.out.println("row id "+row.getKey()+" name "+object.get("LastName_of_applicant")  );
			
			
		}
		}
		System.out.println("Done");

		ArrayList<HashMap<String, String>> list=new ArrayList<HashMap<String,String>>();
		
		System.out.println("content"+content);

		File file = new File("/home/venkateshm/Desktop/referralData.txt");

		// if file doesnt exists, then create it
	/*if (!file.exists()) {
			file.createNewFile();
		}*/

	/*	FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(content);
		bw.close();
		*/
		
		
	
		
		
		
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}		
	}
	static DesignDocument getDesign(String name){
		try {
			System.out.println("Design document with "+name+" exist ? "+client.getDesignDoc(name));
	        return client.getDesignDoc(name);
	    } catch (com.couchbase.client.protocol.views.InvalidViewException e) {
	        return new DesignDocument(name);
	    }
	}

}
