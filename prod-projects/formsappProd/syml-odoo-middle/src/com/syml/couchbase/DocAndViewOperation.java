package com.syml.couchbase;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import com.couchbase.client.CouchbaseClient;
import com.couchbase.client.protocol.views.DesignDocument;
import com.couchbase.client.protocol.views.ViewDesign;

public class DocAndViewOperation {
		public static CouchbaseClient client=null;
		
	public static void main(String[] args) {
		try {
			//1. create the uri for couchbase 
			
			ArrayList<URI> node=new ArrayList<URI>();
			node.add(URI.create("http://198.72.106.5:8091/pools"));
			
			//2. create a connection with couchbase server
			client=new CouchbaseClient(node, "syml","symL@0115");
			
			System.out.println("try to verifying the view");
			
			//4.create Document design
			DesignDocument designdoc=DocAndViewOperation.getDesignDocument("applicant1");
	        boolean found = false;
	        
	        //5. get the views and check the specified in code for existence
	        for(ViewDesign view:designdoc.getViews()){
	        	if(view.getMap()=="applicant"){
	        	found=true;
	        	break;
	        	}
	        }
	        
	        //6. If not found then create view inside document
	        if(!found){
	        	ViewDesign view=new ViewDesign("applicant","function (doc, meta) {\n" +
	        			"if(doc.type&&doc.type==\"applicant1\")\n" +
						"{emit(doc.name,null);}\n" +
				  
	                    "}");
	        	designdoc.getViews().add(view);
	        	client.createDesignDoc(designdoc);
	        }
			
			
			//7. close the connection with couchbase
	        client.shutdown();
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}

	private static DesignDocument getDesignDocument(String name) {
		try {
			System.out.println("Design document with "+name+" exist ? "+client.getDesignDoc(name));
	        return client.getDesignDoc(name);
	    } catch (com.couchbase.client.protocol.views.InvalidViewException e) {
	        return new DesignDocument(name);
	    }
	}

}
