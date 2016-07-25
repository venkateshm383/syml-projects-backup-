package infrastracture;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.couchbase.client.CouchbaseClient;
import com.debortoliwines.openerp.api.FilterCollection;
import com.debortoliwines.openerp.api.ObjectAdapter;
import com.debortoliwines.openerp.api.Row;
import com.debortoliwines.openerp.api.RowCollection;
import com.debortoliwines.openerp.api.Session;

import controllers.Application;
import controllers.Client;

public class ClientSurvey {
 	static Logger log = LoggerFactory.getLogger(ClientSurvey.class);

	CouchBaseOperation couchBaseOperation=new CouchBaseOperation();
	CouchbaseClient client1=null;
	
	
	//-----------------------------------client survey-----------------------------------

		public void checkClientSurvey(JSONObject jsonObject) {

			ArrayList<Client> list = null;
			client1 = couchBaseOperation.getConnectionToCouchBase();
ArrayList applicantList=null;
			JSONObject json = null;
			String applicantName = null;
			String applicantEmail = null;

			System.out
					.println("inside check client survey------------------------>");

			try {
				// getting list applicants already referrrad
				list = couchBaseOperation.getClientSurveyFromCouchbase(jsonObject.get("opprtunityId")
						.toString());

				// if list is empty once agian query because couchbase will take
				// time to query first time so giving second chance
				if (list.size() == 0) {
					try {
						Thread.sleep(6000);
					} catch (Exception e) {

					}
					list = couchBaseOperation.getClientSurveyFromCouchbase(jsonObject.get(
							"opprtunityId").toString());
					try {
						client1.deleteDesignDoc("dev_client"
								+ jsonObject.get("opprtunityId").toString());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				try {
					applicantList = (ArrayList) getApplicantnames(jsonObject.getString(
							"applicantId").toString());
					applicantName=applicantList.get(0).toString();
					applicantEmail=applicantList.get(1).toString();

				} catch (Exception e) {
					e.printStackTrace();
				}

				if (list.size() != 0) {
					// if list not equals to zero alreday payement done

					for (Iterator iterator = list.iterator(); iterator.hasNext();) {
						Client client = (Client) iterator.next();
						if (!client.getApplicantId().equalsIgnoreCase(
								jsonObject.getString("applicantId").toString())) {

							// store other applicant survey detials to cocuchbase
							jsonObject
									.put("alreadyPaid", client.getApplicantname());
							jsonObject.put("Type", "Client");

							jsonObject.put("ClientName", applicantName);
							jsonObject.put("ClientEmail", applicantEmail);
							couchBaseOperation.storeDataInCouchBase("survey_" + applicantName + "_"
									+ jsonObject.get("opprtunityId").toString(),
									jsonObject);
						}
					}
				} else {

					jsonObject.put("AlreadyPaid", applicantName);
					jsonObject.put("Type", "Client");

					jsonObject.put("ClientName", applicantName);
					jsonObject.put("ClientEmail", applicantEmail);
					couchBaseOperation.storeDataInCouchBase("survey_" + applicantName + "_"
							+ jsonObject.get("opprtunityId").toString(), jsonObject);
					// cal payemanet merhod ----------------------------------------
				}
				client1.shutdown();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		
		
		//----------------------Get applicant Name By Applicant id
		public List getApplicantnames(String contactId) {
			String applicantName = null;
			ArrayList list = new ArrayList();
			String applicantEmail=null;
			try {
				Session openERPSession =couchBaseOperation.getOdooConnection();
				log.debug("applicant id is" + contactId);
				ObjectAdapter opprtunity = openERPSession
						.getObjectAdapter("applicant.record");

				FilterCollection filter = new FilterCollection();
				if (contactId != null) {
					filter.add("id", "=", contactId);
				}
				RowCollection row = opprtunity.searchAndReadObject(filter,
						new String[] { "id", "applicant_name",
								"applicant_last_name", "email_personal" });
				for (Iterator iterator = row.iterator(); iterator.hasNext();) {
					Row row2 = (Row) iterator.next();
					log.debug("applicant size"+row2.get("applicant_name") +" "+row2.get("applicant_last_name").toString()+" "+row2.get("email_personal"));
					applicantName = row2.get("applicant_name") + "_"
							+ row2.get("applicant_last_name");
					applicantEmail=row2.get("email_personal").toString() ;
					list.add(applicantName);
					list.add(applicantEmail);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			return list;
		}

}
