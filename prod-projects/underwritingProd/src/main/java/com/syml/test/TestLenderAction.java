package com.syml.test;

import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;

import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.syml.domain.Lender;
public class TestLenderAction {

	@Test
	public void testGetLender() {
		Client client = Client.create();
		WebResource resource = client.resource("http://localhost:8080/UnderwrittingApp/services/lender");
		GenericType<JAXBElement<Lender>> generic = new GenericType<JAXBElement<Lender>>(){};
		JAXBElement<Lender> jaxb = resource.path("1").accept(MediaType.APPLICATION_JSON).get(generic);
		Lender lender = jaxb.getValue();
		System.out.println(lender); 
	}

	@Test
	public void testCreateLender() {
		Client client = Client.create();
		WebResource resource = client.resource("http://localhost:8080/UnderwrittingApp/services/lender");
		GenericType<JAXBElement<String>> generic = new GenericType<JAXBElement<String>>(){};
		JAXBElement<String> jaxb = resource.path("3").accept(MediaType.APPLICATION_JSON).post(generic);
		String result = jaxb.getValue();
		System.out.println(result); 
	}
	
	@Test
	public void testUpdateLender() {
		Client client = Client.create();
		WebResource resource = client.resource("http://localhost:8080/UnderwrittingApp/services/lender");
		GenericType<JAXBElement<String>> generic = new GenericType<JAXBElement<String>>(){};
		JAXBElement<String> jaxb = resource.path("1").accept(MediaType.APPLICATION_JSON).put(generic);
		String result = jaxb.getValue();
		System.out.println(result); 
	}
	
	@Test
	public void testDeleteLender() {
		Client client = Client.create();
		WebResource resource = client.resource("http://localhost:8080/UnderwrittingApp/services/lender");
		GenericType<JAXBElement<String>> generic = new GenericType<JAXBElement<String>>(){};
		JAXBElement<String> jaxb = resource.path("1").accept(MediaType.APPLICATION_JSON).delete(generic);
		String result = jaxb.getValue();
		System.out.println(result); 
	}
	
//	@Test 
//	public void testCreate() {
//		Client client = Client.create();
//		WebResource resource = client.resource("http://localhost:8080/UnderwrittingApp/services/lender");
//		ClientResponse response = resource.accept(MediaType.TEXT_PLAIN).type(MediaType.APPLICATION_JSON).post(ClientResponse.class,new Lender());
//		System.out.println(response.getEntity(String.class));
//	}
//
//	@Test
//	public void testUpdate() {
//		Client client = new Client();
//		WebResource resource = client.resource("http://localhost:8080/UnderwrittingApp/services/lender");
//		Lender lender = new Lender();
//		lender.setId(3);
//		ClientResponse response = resource.accept(MediaType.TEXT_PLAIN).type(MediaType.APPLICATION_JSON).put(ClientResponse.class,lender);
//		System.out.println(response.getEntity(String.class));
//	}
//
//	@Test
//	public void testDelete(){
//		Client client = new Client();
//		WebResource resource = client.resource("http://localhost:8080/UnderwrittingApp/services/lender");
//		ClientResponse response = resource.path("2").accept(MediaType.TEXT_PLAIN).delete(ClientResponse.class);
//		System.out.println(response);
//		System.out.println();
//	}
//	
}
