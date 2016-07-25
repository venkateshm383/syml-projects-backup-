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

public class ReferralSurvey {
	
 	static Logger log = LoggerFactory.getLogger(ReferralSurvey.class);

	CouchBaseOperation couchBaseOperation=new CouchBaseOperation();
	CouchbaseClient client1=null;
	
	public void checkReferralSurvey(JSONObject jsonObject) {

		ArrayList list = null;
		client1 = couchBaseOperation.getConnectionToCouchBase();
		System.out
				.println("inside check referrral survey------------------------>");

		String applicantName = null;
		String referralName = null;
		String refrralEmail = null;

		try {
			// getting list applicants already referrrad

			list = (ArrayList) getReferralnameByLeadID(jsonObject.get(
					"opprtunityId").toString());

			applicantName = getApplicantnamesByleadId(jsonObject.get(
					"opprtunityId").toString());

			try {
				referralName = list.get(1).toString();
				refrralEmail = list.get(0).toString();
			} catch (Exception e) {
				e.printStackTrace();
			}

			jsonObject.put("ClientName", applicantName);
			jsonObject.put("ReferralName", referralName);
			jsonObject.put("ReferralEmail", refrralEmail);

			couchBaseOperation.storeDataInCouchBase("survey_" + referralName + "_"
					+ jsonObject.get("opprtunityId").toString(), jsonObject);
			client1.shutdown();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	//Get applicantName by Lead id --------------------------
	
	public String getApplicantnamesByleadId(String opprunityID) {
		String applicantName = null;

		String contactId = null;

		try {
			Session openERPSession =couchBaseOperation.getOdooConnection();
			log.debug("Seesion is" + openERPSession);
			ObjectAdapter opprtunity = openERPSession
					.getObjectAdapter("crm.lead");

			FilterCollection filter = new FilterCollection();
			filter.add("id", "=", opprunityID);
			RowCollection row = opprtunity.searchAndReadObject(filter,
					new String[] { "id", "app_rec_ids" });
			Row row1 = row.get(0);
			Object[] object = (Object[]) row1.get("app_rec_ids");
			contactId = object[0].toString();
		} catch (Exception e) {

		}

		try {
			Session openERPSession =couchBaseOperation.getOdooConnection();
			log.debug("Seesion is" + openERPSession);
			ObjectAdapter opprtunity = openERPSession
					.getObjectAdapter("applicant.record");

			FilterCollection filter = new FilterCollection();
			if (contactId != null) {
				filter.add("id", "=", contactId);
			}
			RowCollection row = opprtunity.searchAndReadObject(filter,
					new String[] { "id", "applicant_name",
							"applicant_last_name", "emial_personal" });
			for (Iterator iterator = row.iterator(); iterator.hasNext();) {
				Row row2 = (Row) iterator.next();
				applicantName = row2.get("applicant_name").toString() + "_"
						+ row2.get("applicant_last_name").toString();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return applicantName;
	}
	
	
	
	//--------------getReferral name And email using leadId--------------from openerp--
	public List getReferralnameByLeadID(String leadId) {
		String referralName = null;
		String contactId = null;

		String referralID = null;
		ArrayList list = new ArrayList();

		RowCollection leadList = null;
		try {

			Session openERPSession =couchBaseOperation.getOdooConnection();
			ObjectAdapter lead = openERPSession.getObjectAdapter("crm.lead");

			FilterCollection filters1 = new FilterCollection();
			filters1.add("id", "=", leadId);

			leadList = lead.searchAndReadObject(filters1, new String[] { "id",
					"referred_source" });
			log.debug(leadList.size() + "id");

			for (Row row : leadList) {
				if (row.get("referred_source") != null) {
					Object[] object = (Object[]) row.get("referred_source");
					referralID = object[0].toString();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		RowCollection referralList = null;
		try {

			Session openERPSession = couchBaseOperation.getOdooConnection();
			ObjectAdapter referral = openERPSession
					.getObjectAdapter("hr.applicant");

			FilterCollection filters1 = new FilterCollection();
			filters1.add("id", "=", referralID);

			referralList = referral.searchAndReadObject(filters1, new String[] {
					"id", "name", "partner_id", "email_from" });
			log.debug(referralList.size() + "id");

			for (Row row : referralList) {
				if (row.get("partner_id") != null) {
					Object[] object = (Object[]) row.get("partner_id");
					contactId = object[0].toString();
				}
				list.add(row.get("email_from").toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			Session openERPSession =couchBaseOperation.getOdooConnection();
			log.debug("Seesion is" + openERPSession);
			ObjectAdapter opprtunity = openERPSession
					.getObjectAdapter("res.partner");

			FilterCollection filter = new FilterCollection();
			filter.add("id", "=", contactId);
			RowCollection row = opprtunity.searchAndReadObject(filter,
					new String[] { "id", "name" });
			Row row1 = row.get(0);
			referralName = row1.get("name").toString();
			list.add(referralName);
			System.out
					.println("-----------------referral soursce Name--------------------- "
							+ referralName);
		} catch (Exception e) {

		}

		return list;
	}

}
