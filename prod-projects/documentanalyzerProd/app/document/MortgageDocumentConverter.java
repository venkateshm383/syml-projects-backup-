package document;

import helper.CouchBaseOperation;
import helper.RestCallClass;

import java.io.IOException;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import play.Logger;
import postgresdao.PostgresDAOI;
import postgresdao.impl.PostgresDAO;

import com.fasterxml.jackson.databind.ObjectMapper;

import dto.ApplicantDocument;
import dto.Opportunity;

public class MortgageDocumentConverter {

	private static org.slf4j.Logger logger = play.Logger.underlying();
	
	public static ApplicantDocument convertToApplicantDocument(
			String opportunityId) throws JSONException, IOException {
		PostgresDAOI psoDaoi = new PostgresDAO();
		Opportunity opportunity = psoDaoi.getOpportunityDetails(opportunityId);
		ApplicantDocument applicantDocumentList = new RequiredDocumentBuilder()
				.requiredDocumentBuilderMethod(opportunity);
	logger.info("applicantDocumentList :" + applicantDocumentList.toString());

		// storing documentlist data to couchbase
		ObjectMapper mapper = new ObjectMapper();
		

		logger.info("all documentList-----------"
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
				logger.error("error in listing documents list for the opporunity id ="+opportunityId ,e);
		}
		
		
try{
	new RestCallClass(opportunity.getId()+"").start();;
}catch(Exception e){
	logger.error("error in calling taskcreation app "+e.getMessage());
}
		return applicantDocumentList;
	}
	
	public static void main(String[] args) throws JSONException, IOException {
		convertToApplicantDocument("746");

	}

}
