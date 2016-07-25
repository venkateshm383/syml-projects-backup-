package com.syml.test.bk;

import javax.ws.rs.core.MediaType;

import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class TestProductWS {
	private String id = "1";
	
	@Test
	public void testGet() {
		Client client = Client.create();
		WebResource resource = client.resource("http://localhost:8080/UnderwrittingApp/services/integration/product");
		
		ClientResponse response = resource.path(id).accept(MediaType.TEXT_PLAIN).get(ClientResponse.class);
		System.out.println(response.getEntity(String.class));
	}
	
	@Test
	public void testUpdate() {
		Client client = new Client();
		WebResource resource = client.resource("http://localhost:8080/UnderwrittingApp/services/integration/product/update");
		
		ClientResponse response = resource.path(id).accept(MediaType.TEXT_PLAIN).get(ClientResponse.class);
		System.out.println(response.getEntity(String.class));
	}
	
	
}
