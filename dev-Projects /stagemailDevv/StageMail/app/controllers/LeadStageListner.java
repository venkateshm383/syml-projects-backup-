package controllers;

import infrastracture.CouchBaseOperation;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import play.Logger;

import com.couchbase.client.CouchbaseClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sendwithus.SendWithUsExample;

public class LeadStageListner{

	private Connection conn;
	CouchBaseOperation couchbase = new CouchBaseOperation();
	CouchbaseClient client = null;
	
	String lead;
	
	
	public LeadStageListner(String lead) {
		super();
		this.lead = lead;
	}

	public static void main(String[] args) throws JsonProcessingException {
		mailToStageProposal(17, "reato", "4371", "test", "venkateshm383@gmail.com", "tests", "realtorName", "venkatesh.m@bizruntime.com");
	}

	public void run() {
		

		try {

			try {
				Class.forName("org.postgresql.Driver");

				Properties prop = new Properties();
				ArrayList<URI> nodes = new ArrayList<URI>();
				try {

					// getting connection parameter
					prop.load(Application.class.getClassLoader()
							.getResourceAsStream("config.properties"));

				} catch (Exception e) {

				}

				String url = prop.getProperty("postgresURL");
				String userName = prop.getProperty("postgresUserName");
				String userPassword = prop.getProperty("postgresPassword");
				// Create two distinct connections, one for the notifier
				// and another for the listener to show the communication
				// works across connections although this example would
				// work fine with just one connection.
				conn = DriverManager.getConnection(url, userName, userPassword);
			} catch (Exception e) {
				// TODO: handle exception
			}

			JSONObject json = null;
			try {
				Logger.info("Inside TRY ---------- ");

				json = new JSONObject(lead);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int stage_id = 0;
			int referralFee = 0;
			int realatorfee = 0;
			String lenderName = "";
			try {
				stage_id = (int) json.get("stage_id");
				Logger.info("StageID is" + stage_id);
				
			} catch (Exception e) {
				e.printStackTrace();

			}
			String Id = "0";
			Id = json.get("id").toString();

			JSONObject oldData = null;
			JSONObject jsonTableData = new JSONObject();
			JSONObject referralData = null;
			JSONObject realtorData = null;
			String referralId=null;
			String realtorId=null;
			String closingDate=null;
			try{
				referralId=json.get("referred_source").toString();
			}catch(Exception e){
				
			}
			try{
				closingDate=json.get("expected_closing_date").toString();
			}catch(Exception e){
				
			}
			try{
				realtorId=json.get("realtor").toString();
			}catch(Exception e){
				
			}
			Statement stmt11 = conn.createStatement();
			ResultSet rs11 = stmt11
					.executeQuery("select row_to_json(hr_applicant) as hr_applicant from hr_applicant where id ="
							+ json.get("referred_source"));
			// Logger.info("********hr_applicant_backup_tbl Data IS*****"+rs11.getString("hr_applicant_backup_tbl").toString());
			while (rs11.next()) {
				Logger.info("*******hr_applicant**************");
				Logger.info(rs11.getString("hr_applicant"));
				referralData = new JSONObject(rs11.getString("hr_applicant"));

			}
			rs11.close();

			Logger.info("************REALOTOR***********************");
			Statement stmtRealtor = conn.createStatement();

			ResultSet rsRealtor = stmtRealtor
					.executeQuery("select row_to_json(hr_applicant) as hr_applicant from hr_applicant where id ="
							+ json.get("realtor"));
			// Logger.info("********hr_applicant_backup_tbl Data IS*****"+rs11.getString("hr_applicant_backup_tbl").toString());
			while (rsRealtor.next()) {
				System.out
						.println("*******hr_applicant Realotor Data**************");
				Logger.info(rsRealtor.getString("hr_applicant"));
				realtorData = new JSONObject(
						rsRealtor.getString("hr_applicant"));

			}
			stmtRealtor.close();
			rsRealtor.close();

			ArrayList list = new ArrayList();
			Statement stmt12 = conn.createStatement();

			JSONObject recomendationJsonData = null;
			ResultSet rs12 = stmt12
					.executeQuery("select row_to_json(opp_recommendations) as opp_recommendations from opp_recommendations where opp_id ="
							+ Id);
			// Logger.info("*******opp_recommendations_backup_tbl Data IS*****"+rs12.getString("opp_recommendations_backup_tbl").toString());

			while (rs12.next()) {
				Logger.info("*******opp_recommendations**************");
				Logger.info(rs12.getString("opp_recommendations"));
				recomendationJsonData = new JSONObject(
						rs12.getString("opp_recommendations"));
				list.add(recomendationJsonData);

			}
			rs12.close();
			String referralName = "";
			String role = "";
			String referralEmail = "";
			if (referralData != null) {
				referralName = couchbase.getReferralname(referralData.get("partner_id").toString());
				try{
				role = referralData.get("role").toString();
				}catch(Exception e){
					Logger.info("role not specifed "+e);
				}
				Logger.info("Role Is " + role);
				referralEmail = referralData.get("email_from").toString();
				Logger.info("Email  Is " + referralEmail);

			}
			
			String realtorName = "";
			String rolerealotor = "";
			String realtorEmail = "";
			if (realtorData != null) {

				realtorName = couchbase.getReferralname(realtorData.get("partner_id").toString());
				try{
				rolerealotor = realtorData.get("role").toString();
				}catch(Exception e){
					Logger.info("role not specifed "+e);

				}
				
				Logger.info("Realtor Role Is " + rolerealotor);
				realtorEmail = realtorData.get("email_from").toString();
				Logger.info("Email  Is " + realtorEmail);

			}

			jsonTableData.put("id", Id + "");
			jsonTableData.put("stage_Id", stage_id);
// to get lender name ---------------------------------
			String productNameSeletected = "";
			String recomendedProduct = "";
			try {
				productNameSeletected = getProduct(Integer
						.parseInt(json.get("selected_product")
								.toString()));

			} catch (Exception e) {
				e.printStackTrace();
			}
			for (Iterator iterator = list.iterator(); iterator
					.hasNext();) {
				JSONObject object = (JSONObject) iterator
						.next();
				if (object.get("product") != null) {
					try{
					recomendedProduct = getProduct(Integer
							.parseInt(object.get("product")
									.toString()));
					
					if (recomendedProduct
							.equalsIgnoreCase(productNameSeletected)) {
						lenderName=getLender(Integer
								.parseInt(object.get("lender")
								.toString()));
								
					}}catch(Exception e){
						Logger.info("error "+e);
					}
				}

			}
			
			//---------------------------------------

			
			
			try {

			/*	client = couchbase.getConnectionToCouchBase();
				try {

					oldData = new JSONObject(client.get(
							"leadsstageTasksdone" + Id.trim()).toString());
					Logger.info("Old Data IS" + oldData);
					couchbase.closeCouchBaseConnection();

				} catch (Exception e) {
					e.printStackTrace();
				}*/
				Logger.info("inside lead------------------------->");

	/*			if (oldData != null && oldData.length() != 0) {*/
					/*String taskdone = null;
					try {
						taskdone = oldData.get("task" + stage_id).toString();
					} catch (Exception e) {
						e.printStackTrace();
					}
					Logger.info("inside lead------------------------->"
							+ taskdone);
			*/		String stageNAme = getstageId(stage_id);
				/*	if (taskdone != null) {
						if (stageNAme.equalsIgnoreCase("Proposal")) {

							System.out
									.println("**********Inside 2nd confirm Proposalconfirm satge stage********");
						 productNameSeletected = "";
							 recomendedProduct = "";
							try {
								productNameSeletected = getProduct(Integer
										.parseInt(json.get("selected_product")
												.toString()));

							} catch (Exception e) {
								e.printStackTrace();
							}
							Logger.info("product Size "+list.size());
							for (Iterator iterator = list.iterator(); iterator
									.hasNext();) {
								JSONObject object = (JSONObject) iterator
										.next();
								Logger.info("product Id "+object.get("product"));
								if (object.get("product") != null) {
									try{
									recomendedProduct = getProduct(Integer
											.parseInt(object.get("product")
													.toString()));
									Logger.info("product name exsist "+recomendedProduct  +"====="+productNameSeletected );
									
									if (recomendedProduct
											.equalsIgnoreCase(productNameSeletected)) {
										mailToProposalConfirm(Id, object,role,referralName,referralEmail,rolerealotor,realtorName,realtorEmail);
										lenderName=getLender(Integer
												.parseInt(object.get("lender")
												.toString()));
												
									}}catch(Exception e){
										Logger.info(e);
									}
								}

							}

						}
					}*/

		/*			if (taskdone == null) {*/
						/*jsonTableData.put("lender_name", lenderName);

						oldData.put("task" + stage_id, stage_id);
						couchbase.updateDataInCouchBase("leadsstageTasksdone"
								+ Id, oldData);
						RestCallClass.restCallStageChange(jsonTableData
								.toString());*/

						Logger.info("Stage Of Current" + stageNAme);

						if (stageNAme.equalsIgnoreCase("Proposal")) {
							System.out
									.println("**********Inside Proposal stage********");
							mailToStageProposal(stage_id, role, Id,
									referralName, referralEmail,rolerealotor,realtorName,realtorEmail);
							/*try {
								if (realtorData != null ) {
									if (realtorEmail != referralEmail) {
										if(!realtorEmail.equalsIgnoreCase("")){
										mailToStageProposal(stage_id,
												rolerealotor, Id, realtorName,
												realtorEmail);
										}

									}
								}
							} catch (Exception e) {
								e.printStackTrace();
							}*/

							

								/*System.out
										.println("**********Inside Proposalconfirm satge stage********");
								 productNameSeletected = "";
								 recomendedProduct = "";
								try {
									productNameSeletected = getProduct(Integer
											.parseInt(json.get(
													"selected_product")
													.toString()));

								} catch (Exception e) {
									e.printStackTrace();
								}
								for (Iterator iterator = list.iterator(); iterator
										.hasNext();) {
									JSONObject object = (JSONObject) iterator
											.next();
									if (object.get("product") != null) {
										
										try{
										recomendedProduct = getProduct(Integer
												.parseInt(object.get("product")
														.toString()));
										
										if (recomendedProduct
												.equalsIgnoreCase(productNameSeletected)) {
											mailToProposalConfirm(Id, object,role,referralName,referralEmail);

										}}catch(Exception e){
											Logger.info(e);
										}
										
									}

								

							}*/

						} else if (stageNAme.equalsIgnoreCase("Post Selection")) {
							System.out
									.println("***********Inside Stage All Product********");
							productNameSeletected = "";
							 recomendedProduct = "";
							 JSONObject object	=null;
							try {
								/*productNameSeletected = getProduct(Integer
										.parseInt(json.get("selected_product")*/
								
								
								
								
											JSONObject	 json1	=couchbase.getCouchBaseDataProposal("SelectRec_"+Id);

											JSONArray jsondata=
													jsondata=json1.getJSONArray("Recommendations");
											object=jsondata.getJSONObject(0);
							} catch (Exception e) {
								e.printStackTrace();
							}
							Logger.info("product Size "+list.size());
						/*	for (Iterator iterator = list.iterator(); iterator
									.hasNext();) {
								JSONObject object = (JSONObject) iterator
										.next();
								Logger.info("product Id "+object.get("product"));
								if (object.get("product") != null) {
									try{
									recomendedProduct = getProduct(Integer
											.parseInt(object.get("product")
													.toString()));
									Logger.info("product name exsist "+recomendedProduct  +"====="+productNameSeletected );
									
									if (recomendedProduct
											.equalsIgnoreCase(productNameSeletected)) {*/
							if(object!=null){
										mailToProposalConfirm(Id, object,role,referralName,referralEmail,rolerealotor,realtorName,realtorEmail);
										
							}
								/*				
									}}catch(Exception e){
										Logger.info(e);
									}
								}*/

							

						/*	mailToProduct(role, Id, referralName,
									referralEmail, recomendationJsonData,
									lenderName);

							try {
								if (realtorData != null) {
									if (realtorEmail != referralEmail) {

										mailToProduct(role, Id, realtorName,
												realtorEmail,
												recomendationJsonData,
												lenderName);

									}
								}
							} catch (Exception e) {
								e.printStackTrace();
							}*/

						} else if (stageNAme.equalsIgnoreCase("Commitment")) {
							System.out
									.println("***********Inside Stage Stage Commitment********");

							mailToStageCommitment(role, referralData, Id,
									referralName, referralEmail,rolerealotor,realtorName,realtorEmail);
							/*try {
								if (realtorData != null) {
									if (realtorEmail != referralEmail) {

										mailToStageCommitment(role,
												realtorData, Id, realtorName,
												realtorEmail);

									}
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
*/
						} else if (stageNAme.equalsIgnoreCase("Compensation")) {
							System.out
									.println("***********Inside Stage Compensation********");
							try {
								referralFee = Integer.parseInt(json.get(
										"referral_fee").toString());

							} catch (Exception e) {

							}
							mailToStgaeCompensation(Id, referralEmail,
									referralName, recomendationJsonData,
									referralFee,lenderName,closingDate);

						} else if (stageNAme.equalsIgnoreCase("Paid")) {
							System.out
									.println("*********** Inside Stage Paid********");
							try {
								referralFee = Integer.parseInt(json.get(
										"referral_fee").toString());

							} catch (Exception e) {

							}
							mailStageToPaid(Id, referralName, referralEmail,
									referralFee,referralId);
							
						}
				
					/*	}} else {
					oldData = new JSONObject();
					Logger.info("Else --------new Is" + oldData);
					oldData.put("task" + stage_id, stage_id);
					Logger.info("Else --------new data Is" + oldData);
					couchbase.storeDataInCouchBase(
							"leadsstageTasksdone" + Id.trim(), oldData);
				}*/

			} catch (Exception e) {
				e.printStackTrace();
			}

			// wait a while before checking again for old
			// notifications
			// Thread.sleep(500);
		} catch (Exception sqle) {

			try {
				conn.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
			sqle.printStackTrace();
		}

	}

	public static void mailToStageProposal(int stage_id, String role,
			String opprunity_id, String referralName, String referralEmail,String realtorRole,String realtorName,String realtorEmail)
			throws JsonProcessingException {
Logger.info("inside proposal mail method --------------------");
		//String stageNAme = getstageId(stage_id);
		CouchBaseOperation couchBaseOperation = new CouchBaseOperation();

		ArrayList<Applicants> applicantslist1 = couchBaseOperation
				.getApplicants(opprunity_id);

		Applicants app1 = null;
		Applicants app2 = null;
		Applicants app3 = null;
		int applicantsize = 0;

		
		for (Iterator iterator = applicantslist1.iterator(); iterator.hasNext();) {
			Logger.info("Iniside App For Data");
			Applicants applicants2 = (Applicants) iterator.next();
			Logger.info("Applicant data firstName is"
					+ applicants2.getApplicantFirstName());
			Logger.info("Applicant data LastName is"
					+ applicants2.getApplicantlastName());
			Logger.info("Applicant data Email is"
					+ applicants2.getApplicantEmail());

			++applicantsize;
			if (applicantsize == 1) {
				Logger.info("Inside If app1");
				app1 = applicants2;

			} else if (applicantsize == 2) {
				Logger.info("Inside If app2");

				app2 = applicants2;
			} else if (applicantsize == 3) {
				Logger.info("Inside If app3");

				app3 = applicants2;
			}

		}
    	String trackingUrl="https://dev-videos.visdom.ca/clientA?LeadID="+opprunity_id+"&ContactID="+app1.getApplicantId()+"";

		String applicantNames = "";
Logger.info("aplicant size "+ applicantsize);
SendWithUsExample sendEmail = new SendWithUsExample();

try{
		if (applicantsize == 1) {
			applicantNames = app1.getApplicantFirstName()  ;
			sendEmail.getsendWithUsApplicant_CoApplicant(opprunity_id,applicantNames,
					app1.getApplicantEmail(), null,trackingUrl);
			Logger.info("First Name   " + app1.getApplicantFirstName()
					+ "LastNAme   " + app1.getApplicantlastName());

		}
		
		if (applicantsize == 2) {
			applicantNames = app1.getApplicantFirstName()  ;
			
			applicantNames += " and " + app2.getApplicantFirstName() ;
		
			sendEmail.getsendWithUsApplicant_CoApplicant(opprunity_id,applicantNames,
					app1.getApplicantEmail(), app2.getApplicantEmail(),trackingUrl);
		} if (applicantsize==3) {
			applicantNames = app1.getApplicantFirstName() +" " ;
			applicantNames +=  app2.getApplicantFirstName()  ;
			applicantNames += " and " + app3.getApplicantFirstName()+"" ;
		
			sendEmail.getsendWithUsApplicant_CoApplicant(opprunity_id,applicantNames,
					app1.getApplicantEmail(), app2.getApplicantEmail(),trackingUrl);
		}
}catch(Exception e){
	e.printStackTrace();
}
		/*
		 * Applicants applicants =
		 * couchBaseOperation.getApplicants(opportunityId);
		 * .getApplicantDetials(opprunity_id);
		 */




		

		 //https:  webserv.visdom.ca/index.php?option=com_mortgageapplication&view=mortgageapplicationedit&crm_lead=220
Logger.info("---------------------after applicant Name split-----------------------------===========>");


Logger.info("inside commitement" + role + "referralEmail"
		+ referralEmail);
		try{
			if(realtorEmail==null){
				realtorEmail="";
			}

			if(referralEmail==null){
				referralEmail="";
			}
			if(referralEmail.trim().equalsIgnoreCase(realtorEmail.trim()))
			{
				sendEmail.getsendWithUsRefferal(applicantNames, referralEmail,
						referralName);

			}else{
				try{
				sendEmail.getsendWithUsRefferal(applicantNames, referralEmail,
						referralName);
				}catch(Exception e){
					e.printStackTrace();
				}
				sendEmail.getsendWithUsRefferal(applicantNames,realtorEmail, realtorName
						);
			}
			
			
			
			
			}catch(Exception e){
				e.printStackTrace();
			}
			
		
		

		
		Logger.info("Email Sended");

	}

	
	public static void mailToProposalConfirm(String opprunity_id,
			
	JSONObject recomendationJsonData, String role1, String referralName,
			String referralEmail, String realtorRole, String realtorName,
			String realtorEmail) throws NumberFormatException,
			JsonProcessingException, JSONException {
		Logger.info("----------------------inside mailToProposal-------------------");
		Logger.debug("Inside MailTOProduct");
		CouchBaseOperation couchBaseOperation = new CouchBaseOperation();
		String productType=null;
		
		
		ArrayList<Applicants> applicantslist1 = couchBaseOperation
				.getApplicants(opprunity_id);

		Applicants app1 = null;
		Applicants app2 = null;
		Applicants app3 = null;
		int applicantsize = 0;
		for (Iterator iterator = applicantslist1.iterator(); iterator.hasNext();) {
			Applicants applicants2 = (Applicants) iterator.next();
			++applicantsize;
			if (applicantsize == 1) {
				Logger.debug("Inside If Applicants 1");

				app1 = applicants2;

			} else if (applicantsize == 2) {
				Logger.debug("Inside If Applicant 2");
				app2 = applicants2;
			} else if (applicantsize == 3) {
				Logger.debug("Inside Applicant 3");
				app3 = applicants2;
			}

		}
		Logger.debug("aplicant size " + applicantsize);

		String applicantNames = "";

		SendWithUsExample sendEmail = new SendWithUsExample();

		try {
			if (applicantsize == 1) {
				applicantNames = app1.getApplicantFirstName();

			}

			if (applicantsize == 2) {
				applicantNames = app1.getApplicantFirstName();

				applicantNames += " and " + app2.getApplicantFirstName();
				Logger.debug("First Name" + app3.getApplicantFirstName()
						+ "LastNAme" + app3.getApplicantlastName());

			}
			if (applicantsize == 3) {
				applicantNames = app1.getApplicantFirstName() + " ";
				applicantNames += app2.getApplicantFirstName();
				applicantNames += " and " + app3.getApplicantFirstName() + "";
				Logger.debug("First Name" + app2.getApplicantFirstName()
						+ "LastNAme" + app2.getApplicantlastName());

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String lenderName = null;
		Logger.info("-------------------product tyepe---------------------------");
		try{
			productType=recomendationJsonData.get("productType").toString();
			Logger.info("-------------------product type of ---------------------------"+productType);
			lenderName=recomendationJsonData.get("SelectedProductLender").toString();
			Logger.info("-------------------lender name---------------------------"+lenderName);
			}catch(Exception e){
				
			}
		if(productType.equalsIgnoreCase("Combine")){
			
			Logger.info("--------------------inside combine -------------------------------------");
			mailtoProposalCombine(opprunity_id,recomendationJsonData,role1,referralName,referralEmail,realtorRole,realtorName,realtorEmail);
		}else{
		
			Logger.info("--------------------inside Single -------------------------------------");

		
		// Send Mail TO Refferal

		// send Mail TO Applicant, Product Selection

		SendWithUsExample sendEmail2ProductSelection = new SendWithUsExample();

		// Get Product Name
		String productName = null;
		String mortgage_Type = "";
		String mortgageType = null;
		String term = null;
		float amortization1 = 0;
		float interestRate1 = 0;
		float expectedPayment1 = 0;
	

		
		Recommendation rec=null;
		try {
			
			rec = new ObjectMapper().readValue(recomendationJsonData.toString(), Recommendation.class);
			Logger.info("---------------------------------rec -------------------------"+rec.toString());
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
		
	
		
		
		try {
			try {
				/*lenderName = getLender(Integer.parseInt(recomendationJsonData
						.get("lender").toString()));*/
				lenderName=
					recomendationJsonData
						.get("selectedProductLender").toString();
			} catch (Exception e) {

			}
		//	int id = (int) recomendationJsonData.get("product");

		//	productName = getProduct(id);
			
			try{
			productName=
					recomendationJsonData.get("selectedProductName").toString();
			mortgage_Type = (String) recomendationJsonData.get("selectedProductType");


			term = (String) recomendationJsonData.get("selectedProductTerm");
			}catch(Exception e){
				
			}
		
		
			try{
				amortization1 =Float.parseFloat( recomendationJsonData.get("maximumAmortization").toString());
			}catch(Exception e){
				e.printStackTrace();
			}
		
			try{
			interestRate1 = Float.parseFloat(recomendationJsonData.get("selectedProductRate").toString());
		
			}catch(Exception e){
				e.printStackTrace();
			}
			
			expectedPayment1 = Float.parseFloat(recomendationJsonData
					.get("selectedProductPayment").toString());
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (app2 == null) {

			sendEmail2ProductSelection.getsendWithUsProductSelection(
					applicantNames, lenderName, productName, mortgage_Type,
					term, amortization1 + "", interestRate1 + "",
					expectedPayment1 + "", app1.getApplicantEmail(), null);
		} else {
			sendEmail2ProductSelection.getsendWithUsProductSelection(
					applicantNames, lenderName, productName, mortgageType,
					term, amortization1 + "", interestRate1 + "",
					expectedPayment1 + "", app1.getApplicantEmail(),
					app2.getApplicantEmail());
		}
		
		
		
		}
		
		
		try {
			if (realtorEmail == null) {
				realtorEmail = "";
			}

			if (referralEmail == null) {
				referralEmail = "";
			}
			if (referralEmail.trim().equalsIgnoreCase(realtorEmail.trim())) {
				sendEmail.getsendWithUsReferalProposal(applicantNames,
						referralName, referralEmail, "has", lenderName);
			} else {
				try {
					sendEmail.getsendWithUsReferalProposal(applicantNames,
							referralName, referralEmail, "has", lenderName);
				} catch (Exception e) {

				}
				sendEmail.getsendWithUsReferalProposal(applicantNames,
						realtorName, realtorEmail, "has", lenderName);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		// Send Mail TO Refferal

		Logger.debug("Email Sended");

	
	
	}
	public static void mailtoProposalCombine(String opprunity_id,JSONObject recomendationJsonData, String role1, String referralName,

			
					String referralEmail, String realtorRole, String realtorName,
					String realtorEmail) throws JsonProcessingException, JSONException{
		
		Logger.info("--------------------------mailtoProposalCombine---------------------------------------------");
		
		Recommendation rec=null;
		try {
			
			rec = new ObjectMapper().readValue(recomendationJsonData.toString(), Recommendation.class);
			Logger.info("---------------------------------rec -------------------------"+rec.toString());
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		

		CouchBaseOperation couchBaseOperation = new CouchBaseOperation();
		

		ArrayList<Applicants> applicantslist1 = couchBaseOperation
				.getApplicants(opprunity_id);

		Applicants app1 = null;
		Applicants app2 = null;
		Applicants app3 = null;
		int applicantsize = 0;
		for (Iterator iterator = applicantslist1.iterator(); iterator.hasNext();) {
			Applicants applicants2 = (Applicants) iterator.next();
			++applicantsize;
			if (applicantsize == 1) {
				Logger.debug("Inside If Applicants 1");

				app1 = applicants2;

			} else if (applicantsize == 2) {
				Logger.debug("Inside If Applicant 2");
				app2 = applicants2;
			} else if (applicantsize == 3) {
				Logger.debug("Inside Applicant 3");
				app3 = applicants2;
			}

		}
		Logger.debug("aplicant size " + applicantsize);

		String applicantNames = "";

		SendWithUsExample sendEmail = new SendWithUsExample();

		try {
			if (applicantsize == 1) {
				applicantNames = app1.getApplicantFirstName();

			}

			if (applicantsize == 2) {
				applicantNames = app1.getApplicantFirstName();

				applicantNames += " and " + app2.getApplicantFirstName();
				Logger.debug("First Name" + app3.getApplicantFirstName()
						+ "LastNAme" + app3.getApplicantlastName());

			}
			if (applicantsize == 3) {
				applicantNames = app1.getApplicantFirstName() + " ";
				applicantNames += app2.getApplicantFirstName();
				applicantNames += " and " + app3.getApplicantFirstName() + "";
				Logger.debug("First Name" + app2.getApplicantFirstName()
						+ "LastNAme" + app2.getApplicantlastName());

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Send Mail TO Refferal

		// send Mail TO Applicant, Product Selection

		SendWithUsExample sendEmail2ProductSelection = new SendWithUsExample();

		// Get Product Name
		String productName = null;
		String mortgage_Type = "";
		String mortgageType = null;
		String term = null;
		float amortization1 = 0;
		float interestRate1 = 0;
		float expectedPayment1 = 0;
	

		
		
		
		
		
		String lenderName = null;
		
		/*
		try {
			try {
				lenderName = getLender(Integer.parseInt(recomendationJsonData
						.get("lender").toString()));
				lenderName=recomendationJsonData
						.get("SelectedProductLender").toString();
			} catch (Exception e) {

			}
		//	int id = (int) recomendationJsonData.get("product");

		//	productName = getProduct(id);
			
			try{
			productName=recomendationJsonData.get("SelectedProductName").toString();
			mortgage_Type = (String) recomendationJsonData.get("SelectedProductType");


			term = (String) recomendationJsonData.get("SelectedProductTerm");
			}catch(Exception e){
				
			}
		
		
			try{
				amortization1 =Float.parseFloat( recomendationJsonData.get("maximumAmortization").toString());
			}catch(Exception e){
				e.printStackTrace();
			}
		
			try{
			interestRate1 = Float.parseFloat(recomendationJsonData.get("SelectedProductRate").toString());
		
			}catch(Exception e){
				e.printStackTrace();
			}
			
			expectedPayment1 = Float.parseFloat(recomendationJsonData
					.get("SelectedProductPayment").toString());
		
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		if (app2 == null) {
			Logger.info("------------------------------getsendWithUsProductSelectionForCombine---for null---------------------------------------");
			sendEmail2ProductSelection.getsendWithUsProductSelectionForCombine(rec,applicantNames, app1.getApplicantEmail(), null);
			/*sendEmail2ProductSelection.getsendWithUsProductSelection(
					applicantNames, lenderName, productName, mortgage_Type,
					term, amortization1 + "", interestRate1 + "",
					expectedPayment1 + "", app1.getApplicantEmail(), null);*/
		} else {
			Logger.info("------------------------------getsendWithUsProductSelectionForCombine---for not null---------------------------------------");
			sendEmail2ProductSelection.getsendWithUsProductSelectionForCombine(rec,applicantNames, app1.getApplicantEmail(), app2.getApplicantEmail());
			/*sendEmail2ProductSelection.getsendWithUsProductSelection(
					applicantNames, lenderName, productName, mortgageType,
					term, amortization1 + "", interestRate1 + "",
					expectedPayment1 + "", app1.getApplicantEmail(),
					app2.getApplicantEmail());*/
		}
		
		
		
		
		
		
		
		// Send Mail TO Refferal

		Logger.debug("Email Sended");

	
	}

	public static void mailToStageCommitment(String role,
			JSONObject referralData, String opprunity_id,
			String referralSourceName, String referralEmail,String realtorRole,String realtorName,String realtorEmail) {

		CouchBaseOperation couchBaseOperation = new CouchBaseOperation();
		ArrayList<Applicants> applicantslist1 = couchBaseOperation
				.getApplicants(opprunity_id);

		Applicants app1 = null;
		Applicants app2 = null;
		Applicants app3 = null;
		int applicantsize = 0;
		for (Iterator iterator = applicantslist1.iterator(); iterator.hasNext();) {
			Applicants applicants2 = (Applicants) iterator.next();
			++applicantsize;
			if (applicantsize == 1) {

				app1 = applicants2;

			} else if (applicantsize == 2) {
				app2 = applicants2;
			} else if (applicantsize == 3) {
				app3 = applicants2;
			}

		}
		String applicantNames = "";
		Logger.info("aplicant size "+ applicantsize);
		try{
			if (applicantsize == 1) {
				applicantNames = app1.getApplicantFirstName() ;
				
				Logger.info("First Name   " + app1.getApplicantFirstName()
						+ "LastNAme   " + app1.getApplicantlastName());

			}
			
			if (applicantsize == 2) {
				applicantNames = app1.getApplicantFirstName()  ;

				applicantNames += " and " + app2.getApplicantFirstName() ;
		

			} if (applicantsize==3) {
				applicantNames = app1.getApplicantFirstName() +" " ;
				applicantNames +=  app2.getApplicantFirstName()  ;
				applicantNames += " and " + app3.getApplicantFirstName()+"" ;
				Logger.info("First Name" + app3.getApplicantFirstName()
						+ "LastNAme" + app2.getApplicantlastName());

			}
	}catch(Exception e){
		e.printStackTrace();
	}
	Logger.info("after applicant Name split");
		/*
		 * Applicants applicants = couchBaseOperation
		 * .getApplicantDetials(opprunity_id);
		 */
		Logger.info("inside commitement" + role + "referralEmail"
				+ referralEmail);
		
		
		SendWithUsExample sendTOReferral_Source = new SendWithUsExample();

		try{
			if(realtorEmail==null){
				realtorEmail="";
			}
			
			if(referralEmail==null){
				referralEmail="";
			}
			if(referralEmail.trim().equalsIgnoreCase(realtorEmail.trim()))
			{
				sendTOReferral_Source.getsendWithUsClient_RefferalSource(
						applicantNames, referralSourceName, referralEmail);
			}else{
				try{
				sendTOReferral_Source.getsendWithUsClient_RefferalSource(
						applicantNames, referralSourceName, referralEmail);
				}catch(Exception e){
					e.printStackTrace();
				}
				
				sendTOReferral_Source.getsendWithUsClient_RefferalSource(
						applicantNames, realtorName, realtorEmail);

			}
			
			
			
			
			}catch(Exception e){
				e.printStackTrace();
			}
			
		
		
		
		
		
		
		
		
		
		
	
			Logger.info("Email Sended");

		

	}

	public static void mailToStgaeCompensation(String opprunity_id,
			String referralemail, String referralName, JSONObject referralData,
			int referralFee,String lenderName,String date) throws JsonProcessingException {

		Logger.info("inside compnesationn-------------------"
				+ referralemail + "   ");
		CouchBaseOperation couchBaseOperation = new CouchBaseOperation();
		/*
		 * Applicants applicants = couchBaseOperation
		 * .getApplicantDetials(opprunity_id);
		 */
		ArrayList<Applicants> applicantslist1 = couchBaseOperation
				.getApplicants(opprunity_id);

		Applicants app1 = null;
		Applicants app2 = null;
		Applicants app3 = null;
		int applicantsize = 0;
		for (Iterator iterator = applicantslist1.iterator(); iterator.hasNext();) {
			Applicants applicants2 = (Applicants) iterator.next();
			++applicantsize;
			if (applicantsize == 1) {

				app1 = applicants2;

			} else if (applicantsize == 2) {
				app2 = applicants2;
			} else if (applicantsize == 3) {
				app3 = applicants2;
			}

		}
		String applicantNames = "";
		Logger.info("aplicant size "+ applicantsize);
		SendWithUsExample sendTOReferral_Source = new SendWithUsExample();

		try{
			if (applicantsize == 1) {
				String	surveyLink1="https://dev-surveys.visdom.ca/client?app="+app1.getApplicantId()+"&op="+opprunity_id;

				applicantNames = app1.getApplicantFirstName() ;
				sendTOReferral_Source.getSendWithCompenstionFileComplete(applicantNames, app1.getApplicantEmail(), null, lenderName,date,surveyLink1);

				Logger.info("First Name   " + app1.getApplicantFirstName()
						+ "LastNAme   " + app1.getApplicantlastName());

			}
			
			if (applicantsize == 2) {
				applicantNames = app1.getApplicantFirstName()  ;

				applicantNames += " and " + app2.getApplicantFirstName() ;
				String	surveyLink3="https://dev-surveys.visdom.ca/client?app="+app1.getApplicantId()+"&op="+opprunity_id;

				sendTOReferral_Source.getSendWithCompenstionFileComplete(app1.getApplicantFirstName(), app1.getApplicantEmail(), null, lenderName,date,surveyLink3);

				String	surveyLink1="https://dev-surveys.visdom.ca/client?app="+app2.getApplicantId()+"&op="+opprunity_id;
				sendTOReferral_Source.getSendWithCompenstionFileComplete(app2.getApplicantFirstName(), app2.getApplicantEmail(),null, lenderName,date,surveyLink1);


			} if (applicantsize==3) {
				applicantNames = app1.getApplicantFirstName() +" " ;
				applicantNames +=  app2.getApplicantFirstName()  ;
				applicantNames += " and " + app2.getApplicantFirstName()+"" ;
				
				String	surveyLink3="https://dev-surveys.visdom.ca/client?app="+app1.getApplicantId()+"&op="+opprunity_id;

				sendTOReferral_Source.getSendWithCompenstionFileComplete(app1.getApplicantFirstName(), app1.getApplicantEmail(), null, lenderName,date,surveyLink3);

				String	surveyLink1="https://dev-surveys.visdom.ca/client?app="+app2.getApplicantId()+"&op="+opprunity_id;
				sendTOReferral_Source.getSendWithCompenstionFileComplete(app2.getApplicantFirstName(), app2.getApplicantEmail(),null, lenderName,date,surveyLink1);

				
			}
	}catch(Exception e){
		e.printStackTrace();
	}
		// send TO Referral Source
		Logger.info("inside compnesationn-------------------"
				+ referralemail + "   ");

		// Name: Referral - Final Congratulations


		sendTOReferral_Source.getsendWithUsReferral_Final_Congratulations(
				applicantNames, referralName, referralemail, "");
		Logger.info("Email Sended");

	}

	public static void mailStageToPaid(String opprunity_id,
			String referralName, String referallMail, int referralFee,String referralId)
			throws JsonProcessingException {
		CouchBaseOperation couchBaseOperation = new CouchBaseOperation();
		String	surveyLink="https://dev-surveys.visdom.ca/referral?app="+referralId+"&op="+opprunity_id;

		Logger.info("inside paid-----------------------");
		/*
		 * Applicants applicants = couchBaseOperation
		 * .getApplicantDetials(opprunity_id);
		 */
		ArrayList<Applicants> applicantslist1 = couchBaseOperation
				.getApplicants(opprunity_id);

		Applicants app1 = null;
		Applicants app2 = null;
		Applicants app3 = null;
		int applicantsize = 0;
		for (Iterator iterator = applicantslist1.iterator(); iterator.hasNext();) {
			Applicants applicants2 = (Applicants) iterator.next();
			++applicantsize;
			if (applicantsize == 1) {

				app1 = applicants2;

			} else if (applicantsize == 2) {
				app2 = applicants2;
			} else if (applicantsize == 3) {
				app3 = applicants2;
			}

		}
		String applicantNames = "";
		SendWithUsExample sendTOReferral_Source = new SendWithUsExample();

		try{
			if (applicantsize == 1) {
			String	surveyLink1="https://dev-surveys.visdom.ca/client?app="+app1.getApplicantId()+"&op="+opprunity_id;
				applicantNames = app1.getApplicantFirstName()  ;
				sendTOReferral_Source.getsendWithUsApplicant_Client_FinalCongratulations(app1.getApplicantFirstName(), surveyLink1, app1.getApplicantEmail());

				Logger.info("First Name   " + app1.getApplicantFirstName()
						+ "LastNAme   " + app1.getApplicantlastName());

			}
			
			if (applicantsize == 2) {
				applicantNames = app1.getApplicantFirstName()  ;

				applicantNames += " and " + app2.getApplicantFirstName() ;
				
				String	surveyLink3="https://dev-surveys.visdom.ca/client?app="+app1.getApplicantId()+"&op="+opprunity_id;

				sendTOReferral_Source.getsendWithUsApplicant_Client_FinalCongratulations(app1.getApplicantFirstName(), surveyLink3, app1.getApplicantEmail());

				

				String	surveyLink1="https://dev-surveys.visdom.ca/client?app="+app2.getApplicantId()+"&op="+opprunity_id;

				sendTOReferral_Source.getsendWithUsApplicant_Client_FinalCongratulations(app2.getApplicantFirstName(), surveyLink1, app2.getApplicantEmail());



			} if (applicantsize==3) {
				applicantNames = app1.getApplicantFirstName() +" " ;
				applicantNames +=  app2.getApplicantFirstName()  ;
				applicantNames += " and " + app2.getApplicantFirstName()+"" ;
				
				
				String	surveyLink4="https://dev-surveys.visdom.ca/client?app="+app1.getApplicantId()+"&op="+opprunity_id;

				sendTOReferral_Source.getsendWithUsApplicant_Client_FinalCongratulations(app1.getApplicantFirstName(), surveyLink4, app1.getApplicantEmail());

				

				String	surveyLink1="https://dev-surveys.visdom.ca/client?app="+app2.getApplicantId()+"&op="+opprunity_id;

				sendTOReferral_Source.getsendWithUsApplicant_Client_FinalCongratulations(app2.getApplicantFirstName(), surveyLink1, app2.getApplicantEmail());


				String	surveyLink2="https://dev-surveys.visdom.ca/client?app="+app3.getApplicantId()+"&op="+opprunity_id;

				sendTOReferral_Source.getsendWithUsApplicant_Client_FinalCongratulations(app3.getApplicantFirstName(), surveyLink2, app3.getApplicantEmail());

				
			}
	}catch(Exception e){
		e.printStackTrace();
	}

		sendTOReferral_Source.getsendWithUsReferral_Referral_Paid(applicantNames,
				referralName, referallMail, "",surveyLink);
		Logger.info("Email Sended");

	}

	public static String getProduct(int id) {

		String product = null;

		Connection c = null;
		Statement stmt1 = null;

		try {
			Properties prop = new Properties();
			ArrayList<URI> nodes = new ArrayList<URI>();
			try {

				// getting connection parameter
				prop.load(Application.class.getClassLoader()
						.getResourceAsStream("config.properties"));

			} catch (Exception e) {

			}
			Class.forName("org.postgresql.Driver");

			String url = prop.getProperty("postgresURL");
			String userName = prop.getProperty("postgresUserName");
			String userPassword = prop.getProperty("postgresPassword");

			c = DriverManager.getConnection(url, userName, userPassword);
			c.setAutoCommit(false);
			Logger.info("Opened database successfully");

			stmt1 = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt1
					.executeQuery("select name_template as product from product_product where id="
							+ id + ";");

			int i = 1;

			while (rs.next()) {
				Logger.info("*******DEAL TABLE Data**************");
				Logger.info(rs.getString("product"));
				product = rs.getString("product");

			}
			c.close();
		} catch (Exception e) {

			e.printStackTrace();

		}
		return product;
	}

	public static String getLender(int id) {
		String lenderName = null;
		switch (id) {
		case 5:
			lenderName = "Lendwise";
			break;
		case 6:
			lenderName = "MCAP";
			break;
		case 7:
			lenderName = "Merix";
			break;
		case 3627:
			lenderName = "TD";
			break;
		case 3628:
			lenderName = "Home Trust";
			break;
		case 3629:
			lenderName = "Scotia";
			break;

		case 3687:
			lenderName = "First National";
			break;
		case 3688:
			lenderName = "Optimum";
			break;
		case 3689:
			lenderName = "RMG";
			break;
		default:
			lenderName = "Gathering Info";

			break;
		}
		return lenderName;

	}

	public static String getMortgage_Type(int id) {
		String mortgage_Type = null;
		switch (id) {
		case 1:
			mortgage_Type = "LOC";
			break;
		case 2:
			mortgage_Type = "Variable";
			break;
		case 3:
			mortgage_Type = "Fixed";
			break;
		case 4:
			mortgage_Type = "Cashback";
			break;
		case 5:
			mortgage_Type = "Combined";
			break;
		case 6:
			mortgage_Type = "Other";
			break;

		}

		return mortgage_Type;

	}

	public static String getTerm(int id) {
		String term = null;
		switch (id) {
		case 1:
			term = "6 Months";
			break;
		case 2:
			term = "1 Year";
			break;
		case 3:
			term = "2 Year";
			break;
		case 4:
			term = "3 Year";
			break;
		case 5:
			term = "4 Year";
			break;
		case 6:
			term = "5 Year";
			break;

		case 7:
			term = "6 Year";
			break;

		case 8:
			term = "7 Year";
			break;

		case 9:
			term = "8 Year";
			break;

		case 10:
			term = "9 Year";
			break;

		case 11:
			term = "10 Year";
			break;

		case 12:
			term = "Other";
			break;

		}

		return term;

	}

	public static String getlenderName(String id) {

		Connection c = null;
		Statement stmt1 = null;
		String lenderName = null;
		try {
			Properties prop = new Properties();
			ArrayList<URI> nodes = new ArrayList<URI>();
			try {

				// getting connection parameter
				prop.load(Application.class.getClassLoader()
						.getResourceAsStream("config.properties"));

			} catch (Exception e) {

			}
			Class.forName("org.postgresql.Driver");

			String url = prop.getProperty("postgresURL");
			String userName = prop.getProperty("postgresUserName");
			String userPassword = prop.getProperty("postgresPassword");

			c = DriverManager.getConnection(url, userName, userPassword);
			c.setAutoCommit(false);
			Logger.info("Opened database successfully");

			stmt1 = c.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt1
					.executeQuery("select name  from res.partner where id="
							+ id + ";");

			int i = 1;

			while (rs.next()) {
				System.out
						.println("*******Res.partner TABLE Data**************");

				lenderName = rs.getString("name");

			}
			c.close();
		} catch (Exception e) {

			e.printStackTrace();

		}

		return null;
	}

	public static String getstageId(int stageId) {
		String stageName = null;
		switch (stageId) {
		case 1:
			stageName = "Gathering Info";
			break;

		case 6:
			stageName = "Pending Application";

			break;
		case 7:
			stageName = "Won";

			break;
		case 8:
			stageName = "Expired";

			break;
		case 10:
			stageName = "Partial App ";

			break;

		case 11:
			stageName = "Completed App";

			break;

		case 12:
			stageName = "Credit";

			break;
		case 13:
			stageName = "Lender Submission";

			break;

		case 14:
			stageName = "Commitment";

			break;
		case 16:
			stageName = "Compensation";

			break;
		case 17:
			stageName = "Proposal";

			break;
		case 18:
			stageName = "Paid";

			break;
		case 19:
			stageName = "Lost";

			break;

		case 20:

			stageName = "Awaiting Docs";
			break;
		case 21:

			stageName = "Task";
			break;

		case 22:

			stageName = "All Product";
			break;
		case 23:

			stageName = "Post Selection";
			break;

		default:
			stageName = "Pending Application";

			break;
		}
		return stageName;

	}

}
