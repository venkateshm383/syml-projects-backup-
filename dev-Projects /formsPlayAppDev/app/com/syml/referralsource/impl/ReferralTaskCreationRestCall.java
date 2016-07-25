package com.syml.referralsource.impl;

import org.codehaus.jettison.json.JSONObject;

import play.Logger;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import controllers.Referral_Source;

public class ReferralTaskCreationRestCall extends Thread {

	Referral_Source referral_Source;

	public ReferralTaskCreationRestCall(Referral_Source referral_Source) {

		this.referral_Source = referral_Source;
	}

	@Override
	public void run() {
		restCallReferralTaskCreation(referral_Source);
	}

	/**
	 * To make Rest call to task creation app to create referral tasks (about new referral Registred inivte 
	 * them )
	 * @param referral_Source
	 */
	public void restCallReferralTaskCreation(Referral_Source referral_Source) {
		Logger.info("insdie rest call of the referral page");
		
		JSONObject jsonTableData = new JSONObject();
		try {
			jsonTableData.put("name", referral_Source.getName());
			jsonTableData.put("phonenumber",
					referral_Source.getPartner_mobile());
			jsonTableData.put("email", referral_Source.getEmail_from());

			jsonTableData.put("referrdBy", referral_Source.getReferrer());

		} catch (Exception e) {
			Logger.error("error in parsing json " + e);
		}


		try {

			Client client = Client.create();

			WebResource webResource = client
					.resource("https://task.visdom.ca/referaldata");

			ClientResponse response = webResource.type("application/json")
					.post(ClientResponse.class, jsonTableData.toString());

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}

		 response.getEntity(String.class);
			Logger.info("Request of referral tasks send to  Taskcreation app  sucessfully  done.... \n");

		} catch (Exception e) {

			Logger.error("error in making rest call to Taskcreation app with given referral Details  "
					+ jsonTableData);

		}
	}

}
