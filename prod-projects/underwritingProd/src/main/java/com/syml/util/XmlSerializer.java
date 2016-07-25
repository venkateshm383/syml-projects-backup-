package com.syml.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class XmlSerializer {

	private XmlMapper xmlMapper;
	private static XmlSerializer instance = new XmlSerializer();

	// As singleton
    private XmlSerializer() {
    	this.xmlMapper = new XmlMapper();
    }

    public static final XmlSerializer getInstance() {
    	return instance;
    }

    public <T> T toObject(String xml, Class<T> pojoClass) {
    	try {
			return this.xmlMapper.readValue(xml, pojoClass);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
    }

    public String toXML(Object pojo, boolean prettyPrint) {
    	String result = "";
    	if (prettyPrint) 
    		this.xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
		try {
			result = this.xmlMapper.writeValueAsString(pojo);
			return result;
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
    }
}
