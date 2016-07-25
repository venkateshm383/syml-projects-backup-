package com.syml.couchbase;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

import com.couchbase.client.CouchbaseClient;
import com.couchbase.client.protocol.views.ComplexKey;
import com.couchbase.client.protocol.views.Query;
import com.couchbase.client.protocol.views.View;
import com.couchbase.client.protocol.views.ViewDesign;
import com.couchbase.client.protocol.views.ViewResponse;
import com.couchbase.client.protocol.views.ViewRow;
import com.google.gson.Gson;

public class QueryAndIndexingOperation {
	public static CouchbaseClient client=null;

	public static void main(String[] args) {
		try{
		//1. create the uri for couchbase 
		
		ArrayList<URI> node=new ArrayList<URI>();
		node.add(URI.create("http://198.72.106.5:8091/pools"));
		
		//2. create a connection with couchbase server
		client=new CouchbaseClient(node, "syml","symL@0115");
		
		//3. get the view
		View view=client.getView("applicant1","applicant");
		System.out.println(view.getViewName());
		
		
		//4. create Query.
		Query query=new Query();
		query.setIncludeDocs(true);
		
		//5. get ViewResponse
		ViewResponse viewRes=client.query(view, query);
		System.out.println("---"+viewRes.toString());
		System.out.println(viewRes.size());
		
		Gson gson=new Gson();
		 
		 ArrayList<HashMap<String, String>> infos = new ArrayList<HashMap<String, String>>();
		
		  //6. Iterate over the ViewResponse
		for(ViewRow row:viewRes){
			System.out.println("inside for loop");
			
			// Parse the Document to a HashMap
		    HashMap<String, String> parsedDoc = gson.fromJson((String)row.getDocument(), HashMap.class);

		    // Create a new info out of it
		      HashMap<String, String> info = new HashMap<String, String>();
		      info.put("id", row.getId());
		      info.put("name", parsedDoc.get("FirstName_of_applicant"));
		      infos.add(info);
		}
		
		System.out.println("complete data are : "+infos.toString());
		
		//7. close connection with couchbase
		client.shutdown();
		
		}catch(Exception e){
			System.out.println("error is : "+e);
		}
	}

}
