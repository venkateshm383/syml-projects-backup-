package controllers;

import helper.CouchBaseOperation;
import helper.Odoo;
import helper.RestCallClass;

import java.util.ArrayList;
import java.util.Iterator;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


import com.fasterxml.jackson.databind.JsonNode;

import play.Logger;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {
 static	JSONObject jsonData=null;
	public static Result index() {
		return ok(index.render("Your new application is ready."));
	}

	public static Result getCreditServerData() {
		
	 CouchBaseOperation couchBaseOperation=new CouchBaseOperation();

		JsonNode json = request().body().asJson();
			Logger.debug("jsondata =------------------------------------"
				+ json);
		
		String  oppId="";
		
		try {
			 jsonData = new JSONObject(json.toString());

			  oppId=jsonData.get("id").toString();
				Logger.debug("Opp_id  is"+oppId);
				Logger.debug("calling credit  stage  Methods");

				
				try{
			new Odoo().chnageToCreditStage(oppId);
			Logger.debug("stage  Chnages  To  Credit");

				}catch(Exception e){
					Logger.error("error in changing stage to Credit "+e);
				}
			
			Thread.sleep(60000);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			Logger.error("Data  Id  not  Found"+e1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			
			Logger.error("error inn  threed sleep"+e);
		}catch (Exception e) {
			// TODO: handle exception
			Logger.error("error inn  threed sleep"+e);
		}
		
		
		
		
		
		try {
			ArrayList<ApplicantPojo> beaconVales = new Odoo()
					.getCreditInformation(jsonData.get("id").toString());
			int i = 0;

			ApplicantPojo applicant = null;
			ApplicantPojo coApplicant = null;
			for (Iterator iterator = beaconVales.iterator(); iterator.hasNext();) {
				if (i == 0) {
					applicant = (ApplicantPojo) iterator.next();

				} else if (i == 1) {
					coApplicant = (ApplicantPojo) iterator.next();

				}
				i++;
			}
			if (beaconVales.size() == 1) {

				
			Logger.debug("single applicant exsist");
				if (applicant.isBeaconvalue()) {
				Logger.debug("single applicant exsist beacan value is greater than zero");

				Logger.debug("beacon data exisist");
					JSONObject jsonSucessdata = new JSONObject();
					jsonSucessdata.put("id", jsonData.get("id").toString());
					jsonSucessdata.put("sucess", "true");
					jsonSucessdata.put("applicants",1);
					try {
						jsonSucessdata.put("applicantName",
								applicant.getApplicantName());
					} catch (NullPointerException e) {
						Logger.error("if applicant NAme Not Exist faild get  "+e);

					}

					new RestCallClass(jsonSucessdata.toString()).start();

				Logger.debug("after  automated app calling taskcreation pp"
									+ json);

					// rest call to task taskcreation appp to create sucess
					// message
				} else {
				Logger.debug("single applicant exsist beacan value is less than zero");

					JSONObject jsonSucessdata = new JSONObject();
					jsonSucessdata.put("id", jsonData.get("id").toString());
					jsonSucessdata.put("sucess", "false");
					jsonSucessdata.put("applicants",1);

					try {
						jsonSucessdata.put("applicantNamefailed",
								applicant.getApplicantName());
					} catch (Exception e) {
						Logger.error("error in getting applicant data "+e);

					}
					i++;
					couchBaseOperation.deleteCouchbaseDocument(oppId);
					// rest call to taskcreation app to create unsucess message
					new RestCallClass(jsonSucessdata.toString()).start();
					Logger.info("after  automated app calling taskcreation pp"
									+ json);
					
					

				}
			}
			
			if(beaconVales.size()>1){
				
				if(applicant!=null&& coApplicant!=null){
					
					if(applicant.isBeaconvalue()&&coApplicant.isBeaconvalue()){
					
					Logger.debug("single applicant exsist beacan value is greater than zero");

					Logger.debug("beacon data exisist");
						JSONObject jsonSucessdata = new JSONObject();
						jsonSucessdata.put("id", jsonData.get("id").toString());
						jsonSucessdata.put("sucess", "true");
						jsonSucessdata.put("applicants",2);

						try {
							jsonSucessdata.put("applicantName",
									applicant.getApplicantName() +" and "+coApplicant.getApplicantName());
						} catch (Exception e) {
							Logger.error("error in getting applicant name "+e);

						}

						new RestCallClass(jsonSucessdata.toString()).start();

					Logger.debug("after  automated app calling taskcreation pp"+ json);
	
					}else{
					
					if (applicant.isBeaconvalue()) {
					Logger.debug("single applicant exsist beacan value is greater than zero");

					Logger.info("beacon data exisist");
						JSONObject jsonSucessdata = new JSONObject();
						jsonSucessdata.put("id", jsonData.get("id").toString());
						jsonSucessdata.put("sucess", "true");
						jsonSucessdata.put("applicants",2);

						try {
							jsonSucessdata.put("applicantName",
									applicant.getApplicantName());
						} catch (Exception e) {
Logger.error("error in getting applicant name "+e);
						}

						new RestCallClass(jsonSucessdata.toString()).start();

					Logger.debug("after  automated app calling taskcreation pp"
										+ json);

						// rest call to task taskcreation appp to create sucess
						// message
					} else {
					Logger.debug("single applicant exsist beacan value is less than zero");

						JSONObject jsonSucessdata = new JSONObject();
						jsonSucessdata.put("id", jsonData.get("id").toString());
						jsonSucessdata.put("sucess", "false");
						jsonSucessdata.put("applicants",2);

						try {
							jsonSucessdata.put("applicantNamefailed",
									applicant.getApplicantName());
						} catch (JSONException e) {
							
						Logger.error("Applicant  Name  Failed");

						}
						i++;
						

						// rest call to taskcreation app to create unsucess message
						new RestCallClass(jsonSucessdata.toString()).start();
					Logger.debug("after  automated app calling taskcreation pp"
										+ json);

					}
					
					if (coApplicant.isBeaconvalue()) {
					Logger.debug("single applicant exsist beacan value is greater than zero");

					Logger.debug("beacon data exisist");
						JSONObject jsonSucessdata = new JSONObject();
						jsonSucessdata.put("id", jsonData.get("id").toString());
						jsonSucessdata.put("sucess", "true");
						jsonSucessdata.put("applicants",2);

						try {
							jsonSucessdata.put("applicantName",
									coApplicant.getApplicantName());
						} catch (JSONException e) {
						Logger.error("Applicant  Name  Found");

						}

						new RestCallClass(jsonSucessdata.toString()).start();

					Logger.debug("after  automated app calling taskcreation pp"
										+ json);

						// rest call to task taskcreation appp to create sucess
						// message
					} else {
					Logger.debug("single applicant exsist beacan value is less than zero");

						JSONObject jsonSucessdata = new JSONObject();
						jsonSucessdata.put("id", jsonData.get("id").toString());
						jsonSucessdata.put("sucess", "false");
						jsonSucessdata.put("applicants",2);

						try {
							jsonSucessdata.put("applicantNamefailed",
									coApplicant.getApplicantName());
						} catch (Exception e) {
							Logger.error("Applicant  Name  Found");

						}
						i++;
						// rest call to taskcreation app to create unsucess message
						new RestCallClass(jsonSucessdata.toString()).start();
					Logger.debug("after  automated app calling taskcreation pp"
										+ json);

					}
					
					
					}
				}
				
				
			}

		Logger.debug("end of the automated appp" + json);

		} catch (Exception e) {
			Logger.error("error in  automtaed creadit app "+e);

		}

		return ok("");
	}
}
