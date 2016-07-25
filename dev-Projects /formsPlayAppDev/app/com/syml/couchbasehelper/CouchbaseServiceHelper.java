package com.syml.couchbasehelper;

import play.Logger;

import com.couchbase.client.java.document.json.JsonObject;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syml.couchbase.dao.service.CouchBaseService;
import com.syml.mortgageapplication.MortgageApplicationConstants;
import com.syml.mortgageapplication.impl.MortgageApplicationPageServiceException;

import controllers.Opportunity;

public class CouchbaseServiceHelper {
	/**
	 * 
	 * @param id
	 * @return
	 * @throws MortgageApplicationPageServiceException
	 * By giving parameter id the Opportunity data will get from COUCHBASE ,If not then throws exception. 
	 */
	public Opportunity getCouhbaseDataByKey(int id) throws MortgageApplicationPageServiceException {
		Opportunity opportunity=null;
		try{
			ObjectMapper mapper = new ObjectMapper();
			mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		JsonObject opportunityInJsonOjbect = new CouchBaseService().getCouhbaseDataByKey(MortgageApplicationConstants.MORTGAGE_APPLICATION_COUCHBASE_KEY+id);
		Logger.debug("COuchbase Data Json Data for Opportunirty ID = "+id, opportunityInJsonOjbect.toString());
		opportunity=mapper.readValue(opportunityInJsonOjbect.toString(), Opportunity.class);
		}catch(Exception e){
			throw new MortgageApplicationPageServiceException("Error when reading Opportunity data from couchbase for OpporunityID="+id,e);
		}
		return opportunity;
	}
}
