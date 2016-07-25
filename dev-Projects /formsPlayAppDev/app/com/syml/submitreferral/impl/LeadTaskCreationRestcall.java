package com.syml.submitreferral.impl;


import org.codehaus.jettison.json.JSONObject;

import play.Logger;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import controllers.Lead;




public class LeadTaskCreationRestcall extends Thread {
	
	
	
	
	Lead lead;
	
	public LeadTaskCreationRestcall(Lead lead) {
		super();
		this.lead = lead;
	}
	
	
	@Override
	public void run() {
		restCallLeadTasks(pojoTojson(lead));
	}
		public  void restCallLeadTasks(String jsonString){


					try {

						Client client = Client.create();

						WebResource webResource = client
						   .resource("https://task.visdom.ca/leaddata");


						ClientResponse response = webResource.type("application/json")
						   .post(ClientResponse.class, jsonString);

						if (response.getStatus() != 200) {
							throw new RuntimeException("Failed : HTTP error code : "
							     + response.getStatus());
						}

					Logger.info("Output from Server .... \n");
						String output = response.getEntity(String.class);
						Logger.info(output);

					  } catch (Exception e) {

						e.printStackTrace();

					  }
					
				}

		    
	




public String pojoTojson(Lead lead){
	
	JSONObject jsonTableData = new JSONObject();
	try {
		jsonTableData.put("leadid", lead.getId());
		jsonTableData.put("leadName", lead.getName());
		jsonTableData.put("leadPhone", lead.getMobile());
		jsonTableData.put("leadEmail", lead.getEmail_from());

	
		jsonTableData.put("leadReferralName", lead.getReferral_Source_First_Name()
				+ " " + lead.getReferral_Source_Last_Name());
		jsonTableData.put("leadReferralPhone",lead.getReferral_Source_Phone()==null?"":lead.getReferral_Source_Phone() );
		jsonTableData.put("leadReferralEmail", lead.getReferral_Source_Email());

	} catch (org.codehaus.jettison.json.JSONException e1) {
	Logger.error("Error in parsing lead pojo to Json "+e1);
	}
	
	return jsonTableData.toString();
}



@SuppressWarnings("unused")
public static void main(String[] args) {
	
	JSONObject jsonTableData=new JSONObject();
	String name="devTest";
	String phoneNumber="988-566-655";
	try{	
	
	/*	jsonTableData.put("name", name);
		jsonTableData.put("phonenumber", phoneNumber);*/

		jsonTableData.put("leadid", "111");
		jsonTableData.put("leadName","DevTest");
		jsonTableData.put("leadPhone","555-651-7444");
		jsonTableData.put("leadReferralName","DevTestRefer");
		jsonTableData.put("leadReferralPhone","555-555-334");
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

}



}
