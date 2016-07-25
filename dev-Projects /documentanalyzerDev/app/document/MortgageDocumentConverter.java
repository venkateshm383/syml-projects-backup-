package document;

import helper.CouchBaseOperation;
import helper.Odoo;
import helper.RestCallClass;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javassist.bytecode.stackmap.BasicBlock.Catch;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import postgresdao.PostgresDAOI;
import postgresdao.impl.PostgresDAO;

import com.couchbase.client.CouchbaseClient;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import dto.Applicant;
import dto.ApplicantDocument;
import dto.Income;
import dto.Opportunity;
import dto.Property;
import email.SendWithUsMail;

public class MortgageDocumentConverter {

	static Logger log = LoggerFactory
			.getLogger(MortgageDocumentConverter.class);

	public static ApplicantDocument convertToApplicantDocument(
			String opportunityId) throws JSONException, IOException {
		PostgresDAOI psoDaoi = new PostgresDAO();
		Opportunity opportunity = psoDaoi.getOpportunityDetails(opportunityId);
		ApplicantDocument applicantDocumentList = new RequiredDocumentBuilder()
				.RequiredDocumentBuilderMethod(opportunity);
		log.debug("applicantDocumentList :" + applicantDocumentList.toString());

		// storing documentlist data to couchbase
		ObjectMapper mapper = new ObjectMapper();
		

		log.info("all documentList-----------"
				+ mapper.writeValueAsString(applicantDocumentList));
		try {
			JSONObject json = new JSONObject(
					mapper.writeValueAsString(applicantDocumentList));
			json.put("Type", "DocMaster");
			json.put("Type_DocMaster", "DocMaster");
			json.put("CrmURL", opportunity.getId());

			json.put("DocMasterUrl", "");
			
			json.put("masterdoc_opporunityName", opportunity.getName());

			new CouchBaseOperation()
					.storeDataInCouchBase("DocumentListOfApplicationNo_"
							+ opportunity.getId(), json.toString());

		} catch (Exception e) {
				play.Logger.error("error in listing documents list for the opporunity id ="+opportunityId ,e);
		}
		
		/**
		 * TODO uncomment when using in production 
		 */

	/*	RestCallClass.restCallTaskCreater(mapper
				.writeValueAsString(applicantDocumentList));*/

		return applicantDocumentList;

	}

}
