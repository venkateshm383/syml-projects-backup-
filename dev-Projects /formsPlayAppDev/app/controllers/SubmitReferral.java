package controllers;

import static play.data.Form.form;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import play.Logger;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.error;
import views.html.leadsucess;
import views.html.submitreferral;
import views.html.submitreferralV;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.syml.Constants;
import com.syml.couchbase.dao.CouchbaseServiceException;
import com.syml.couchbase.dao.service.CouchBaseService;
import com.syml.hibernate.dao.IPostGresDaoService;
import com.syml.hibernate.dao.PostGressDaoServiceException;
import com.syml.hibernate.dao.impl.PostgresDAO;
import com.syml.submitreferral.impl.SubmitReferralConstants;
import com.syml.submitreferral.impl.SubmitReferralPageService;

public class SubmitReferral extends Controller {

	/**
	 * To Render Submitreferral Non Video Page and prepopulate with Referral
	 * Details in page Referral Id given in URl and data found in couchbase for
	 * given Referral ID
	 * 
	 * @return
	 */
	public static Result indexNotVedio() throws UnknownHostException {

		String referralId = request().getQueryString("referralId");
		String firstName = "";
		String lastName = "";
		String email = "";
		int couchbaseConnectionSucess = 0;

		ArrayList<String> list;
		try {
			list = (ArrayList<String>) new CouchBaseService()
					.getReferralTriggerDataFromCouhbase("ReferralTrigerData_"
							+ referralId);

			if (list.size() != 0) {
				firstName = list.get(2);
				lastName = list.get(3);
				email = list.get(1);
			}

		} catch (CouchbaseServiceException e) {
			couchbaseConnectionSucess = 1;
			Logger.error("error in getting referral data from couchbase  ", e);
		}
		if (couchbaseConnectionSucess == 1) {

			return ok(error.render(Constants.ERROR_MESSAGE
					,
					 SubmitReferralConstants.COUCHBASE_FETCH_DATA_ERROR_CODE
				));

		} else {
			return ok(submitreferral.render(referralId, "", "", "", "", "", "",
					"", firstName, lastName, email));
		}

	}

	/**
	 * To Render Submitreferral video Page and prepulate with Referral Details
	 * in page Referral Id given in URl and data found in couchbase for given
	 * Referral ID
	 * 
	 * @return
	 */
	public static Result indexV() {
		Logger.debug("inside indexV() in SubmitReferral controller .");
		String referralId = request().getQueryString("referralId");
		Logger.info("referel id" + referralId);
		String firstName = "";
		String lastName = "";
		String email = "";

		ArrayList<String> list;
		try {
			list = (ArrayList<String>) new CouchBaseService()
					.getReferralTriggerDataFromCouhbase("ReferralTrigerData_"
							+ referralId);
			if (list.size() != 0) {
				firstName = list.get(2);
				lastName = list.get(3);
				email = list.get(1);
			}

		} catch (CouchbaseServiceException e) {
			Logger.error("error in getting referral data from couchbase  ", e);
			return ok(error.render(Constants.ERROR_MESSAGE
					, SubmitReferralConstants.COUCHBASE_FETCH_DATA_ERROR_CODE
						));
		}
		
			

	
			return ok(submitreferralV.render(referralId, "", "", "", "", "",
					"", "", firstName, lastName, email));
		

	}

	/**
	 * It is SubmitForm Controller, process the submitreferral form data created
	 * lead , send mail of MortgageApplication Link to client(lead), and return
	 * sucess or Failed message on webpage Page to Referrer(broker)
	 * 
	 * @return
	 */
	public static Result submitReferral() {
		Logger.info("inside   submitReferral   controller method ----- ");
		IPostGresDaoService iPostGresDaoService = new PostgresDAO();
		SubmitReferralPageService submitReferralPageService = new SubmitReferralPageService();
		DynamicForm dynamicForm = form().bindFromRequest();
		Lead leadPojo = submitReferralPageService
				.getLeadDataFromForm(dynamicForm);
		leadPojo.setForm_Type(Constants.SUBMIT_REFERRAL_FORM);
		Contact contact = new Contact();
		contact.setName(dynamicForm.get("fname"));
		contact.setLast_name(dynamicForm.get("lname"));
		contact.setEmail(leadPojo.getEmail_from());
		boolean sucess = submitReferralPageService.validate(leadPojo);

		if (sucess) {
			try {
				/** To find matching referral Resource **/
				try {
					leadPojo = submitReferralPageService
							.toFindREferralSource(leadPojo);
				} catch (JsonProcessingException e1) {
					Logger.error("error getting referral source ", e1);
				}
				List<Contact> contactList;
				contactList = iPostGresDaoService.getContact(contact);

				if (contactList != null && contactList.size() > 0) {
					contact = contactList.get(0);
				} else {
					contact = iPostGresDaoService.insertContact(contact);
				}
				leadPojo.setPartner_id(contact.getId());
				// set stage to pending
				leadPojo.setStage_id(Constants.LEAD_PENDING_STAGE_ID);
				// leadPojo.setType(Constants.SUBMIT_REFERRAL_FORM);
				iPostGresDaoService.insertLead(leadPojo);

				CouchBaseService couchBaseService = new CouchBaseService();

				couchBaseService.storeDataToCouchbase(
						Constants.SUBMIT_REFERRAL_FORM_COUHBASE_KEY
								+ leadPojo.getId(), leadPojo);
				submitReferralPageService.sendMailForLeadAndReferral(leadPojo,
						contact);
				/**
				 * #TODO remove comment when deploying to production and add exception on Task
				 */
				// new LeadTaskCreationRestcall(leadPojo).start();

			} catch (CouchbaseServiceException e) {

				Logger.error("Error in storing data into couchbase :", e);
				return ok(error
						.render(Constants.ERROR_MESSAGE,SubmitReferralConstants.COUCHBASE_FETCH_DATA_ERROR_CODE
								));

			} catch (PostGressDaoServiceException e2) {
				Logger.error("Error in insertig Lead details into PostGressDb",
						e2);
				return ok(error.render(Constants.ERROR_MESSAGE
						
					,SubmitReferralConstants.POSTGRESS_ERROR
						));

			} catch (JsonProcessingException e) {
				Logger.error(
						"error in sending mail for leadCreated with lead deatials ="
								+ leadPojo, e);
				return ok(error.render(Constants.ERROR_MESSAGE
					,
					SubmitReferralConstants.MAIL_ERROR
						));

			}

			return ok(leadsucess
					.render(Constants.SUBMIT_REFERRAL_THANK_YOU_MESSAGE));

		} else {
			return ok(submitreferral.render("", "", "", "", "", "", "", "", "",
					"", ""));

		}

	}

}
