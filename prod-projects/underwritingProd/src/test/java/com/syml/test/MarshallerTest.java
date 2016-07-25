package com.syml.test;

import java.io.IOException;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import org.junit.Test;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import com.sun.xml.bind.marshaller.NamespacePrefixMapper;
import com.syml.morweb.BDIRequest;

public class MarshallerTest {

	public class MyNsPrefixMapper extends NamespacePrefixMapper {
		public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
			System.out.println("namespaceUri = " + namespaceUri);
			System.out.println("suggestion = " + suggestion);
			System.out.println("requiredPrefix = " + requirePrefix);
			if (namespaceUri.equals("http://MSC.IntegrationService.Schemas.MorWEB.BDI.Request.1") && requirePrefix) {
				return "";
			}
            return "";
		}

		public String[] getPreDeclaredNamespaceUris() {
			return new String[0];
		}
	}

	@Test
	public void testMorwebRequest() throws JAXBException, DatatypeConfigurationException {
		final com.syml.morweb.ObjectFactory morwebObjectFactory = new com.syml.morweb.ObjectFactory();

		final XMLGregorianCalendar calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar());
		final BDIRequest bdiRequest = morwebObjectFactory.createBDIRequest();
		bdiRequest.setCreatedDateTime(calendar);

		JAXBContext context = JAXBContext.newInstance(BDIRequest.class);
		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
		marshaller.setProperty("com.sun.xml.bind.namespacePrefixMapper", new MyNsPrefixMapper());

		QName bdiRequestQName = new QName("BDIRequest");
		final JAXBElement<BDIRequest> bdiRequestElement = new JAXBElement<BDIRequest>(bdiRequestQName, BDIRequest.class, null, bdiRequest);

		marshaller.marshal(bdiRequestElement, System.out);
	}

	@Test
	public void testUsingJackson() throws DatatypeConfigurationException, IOException {
		final com.syml.morweb.ObjectFactory morwebObjectFactory = new com.syml.morweb.ObjectFactory();
		final XMLGregorianCalendar calendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar());
		final BDIRequest bdiRequest = morwebObjectFactory.createBDIRequest();
		bdiRequest.setCreatedDateTime(calendar);

		final XmlMapper mapper = new XmlMapper();
		AnnotationIntrospector introspector = new JaxbAnnotationIntrospector(TypeFactory.defaultInstance());
		mapper.setAnnotationIntrospectors(introspector, introspector);
		mapper.writeValue(System.out, bdiRequest);
	}
}
