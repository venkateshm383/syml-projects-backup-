package com.syml.test.bk;
//package com.syml.test;
//
//import javax.ws.rs.core.MediaType;
//import javax.xml.bind.JAXBElement;
//
//import org.junit.Test;
//
//import com.sun.jersey.api.client.Client;
//import com.sun.jersey.api.client.ClientResponse;
//import com.sun.jersey.api.client.GenericType;
//import com.sun.jersey.api.client.WebResource;
//import com.syml.bean.guideline.GuideLine;
//import com.syml.bean.guideline.GuideLineGenerator;
//import com.syml.bean.lender.Lender;
//import com.syml.bean.lender.LenderDocumentationCommunication;
//import com.syml.bean.lender.LenderGenerator;
//import com.syml.bean.product.Product;
//import com.syml.bean.product.ProductGenerator;
//
//@Deprecated
//public class TestGuideLineAction {
//
//	@Test
//	public void testGet() {
//		Client client = Client.create();
//		WebResource resource = client.resource("http://localhost:8080/UnderwrittingApp/services/guide");
//		GenericType<JAXBElement<GuideLine>> generic = new GenericType<JAXBElement<GuideLine>>(){};
//		JAXBElement<GuideLine> jaxb = resource.path("1").accept(MediaType.APPLICATION_JSON).get(generic);
//		GuideLine gl = jaxb.getValue();
//		System.out.println(gl);
//	}
//
//	@Test
//	public void testCreate() {
//		Client client = Client.create();
//		WebResource resource = client.resource("http://localhost:8080/UnderwrittingApp/services/guide");
//		ClientResponse response = resource.path("1").accept(MediaType.TEXT_PLAIN).type(MediaType.APPLICATION_JSON).post(ClientResponse.class,GuideLineGenerator.gen());
//		System.out.println(response.getEntity(String.class));
//		
//	}
//
//	@Test
//	public void testUpdate() {
//		Client client = new Client();
//		WebResource resource = client.resource("http://localhost:8080/UnderwrittingApp/services/guide");
//		GuideLine gl = GuideLineGenerator.gen();
//		gl.setId(1);
//		gl.setAgeRestrictedProperties("oooo");
//		ClientResponse response = resource.accept(MediaType.TEXT_PLAIN).type(MediaType.APPLICATION_JSON).put(ClientResponse.class,gl);
//		System.out.println(response.getEntity(String.class));
//	}
//
//	@Test
//	public void testDelete(){
//		Client client = new Client();
//		WebResource resource = client.resource("http://localhost:8080/UnderwrittingApp/services/guide");
//		ClientResponse response = resource.path("1").accept(MediaType.TEXT_PLAIN).delete(ClientResponse.class);
//		System.out.println(response);
//	}
//	
//}
