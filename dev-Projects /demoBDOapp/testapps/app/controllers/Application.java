package controllers;

import static play.data.Form.form;
import helper.Opprtunity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import org.codehaus.jettison.json.JSONObject;

import play.Logger;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.indexcopy;
import views.html.index;

import com.fasterxml.jackson.databind.JsonNode;
import com.sendwithus.SendWithUsExample;

import document.MortgageDocumentConverter;

public class Application extends Controller {
	
	private static org.slf4j.Logger logger = play.Logger.underlying();

	public static Result index() {
		String referralFirstName="";
        String referralLastName="";
        String referralSourceEmail="";
        String clientEmail="";
        String opportunityName="";

		ArrayList<OpportunityList> opporunityList = new ArrayList<OpportunityList>();

		try {
			opporunityList = new Odoo().getOpprunityDetials();
		} catch (Exception e) {
			 logger.error("Error occured in odoo connection "+e.getMessage());	
		}
		
		//String myvar = "{\"id\":4406,\"create_uid\":26,\"create_date\":\"2015-06-25 23:36:00.444185\",\"write_date\":\"2015-07-01 22:20:39.966939\",\"write_uid\":26,\"date_closed\":null,\"type_id\":null,\"color\":0,\"date_action_last\":null,\"day_close\":0,\"active\":true,\"street\":\"98 Strathcona Ave\",\"day_open\":0,\"contact_name\":null,\"partner_id\":4264,\"date_open\":null,\"user_id\":26,\"opt_out\":false,\"title\":null,\"partner_name\":null,\"planned_revenue\":null,\"country_id\":null,\"company_id\":1,\"priority\":\"3\",\"state\":\"all_product\",\"email_cc\":null,\"date_action_next\":null,\"type\":\"opportunity\",\"street2\":null,\"function\":null,\"fax\":null,\"description\":null,\"planned_cost\":null,\"ref2\":null,\"section_id\":null,\"title_action\":null,\"phone\":null,\"probability\":null,\"payment_mode\":null,\"date_action\":null,\"name\":\"veerendra lastname - 87 Strathcona Ave, Ottawa, First, 2015-06-2\",\"stage_id\":14,\"zip\":null,\"date_deadline\":null,\"mobile\":\"422-453-5321\",\"ref\":null,\"channel_id\":null,\"state_id\":null,\"email_from\":\"venkatesh.m@bizruntime.com\",\"referred\":null,\"marketing_auto\":null,\"opp_info_loan_amnt\":null,\"referral_source\":null,\"is_commercial\":false,\"underwriter\":null,\"considered_cites\":null,\"opp_info_type\":null,\"property_type\":\"2\",\"secondary_financing_amount\":null,\"is_modular_homes\":false,\"lifestyle_change\":\"5\",\"cash_back\":null,\"is_grow_ops\":false,\"propsal_date\":\"2015-07-04 22:21:19\",\"is_country_residential\":false,\"op_info_type\":null,\"lender\":null,\"Amortization\":null,\"monthly_rental_income\":0,\"internal_notes_final\":null,\"process_presntedutio_check\":false,\"monthly_payment\":null,\"total_comp_amount\":null,\"condo_fees\":null,\"open_closed\":null,\"current_renewal_date\":null,\"lender_ref\":null,\"marketing_comp_amount\":null,\"acres\":null,\"application_date\":\"2015-06-25 23:40:13\",\"is_leased_land\":false,\"term\":null,\"is_mobile_homes\":false,\"closing_date\":null,\"insurerfee\":null,\"looking_to\":null,\"property_less_then_5_years\":\"3\",\"current_mortgage_type\":null,\"lot_size\":null,\"is_agricultural\":false,\"postal_code\":\"K1S 1X5\",\"sale_of_existing_amount\":null,\"sewage\":\"1\",\"downpayment_amount\":null,\"qualified_check\":false,\"pending_application_check\":false,\"trainee\":null,\"renter_pay_heating\":null,\"gifted_amount\":null,\"rate\":null,\"is_construction_mortgage\":false,\"selected_product\":null,\"draws_required\":null,\"work_phone\":null,\"current_mortgage_amount\":42234,\"internal_note_property\":null,\"apartment_style\":null,\"is_uninsured_conv_2nd_home\":false,\"opp_info_renewal_date\":\"2015-06-25\",\"borrowed_amount\":null,\"purchase_price\":null,\"is_cottage_rec_property\":false,\"preferred_number\":\"cell\",\"future_family\":\"5\",\"referred_source\":392,\"water\":\"2\",\"existing_payout_penalty\":null,\"desired_mortgage_type\":\"1\",\"credit_story\":null,\"application_no\":\"4327\",\"address\":\"87 Strathcona Ave\",\"isUpdatedToUA\":false,\"is_a_small_centre\":false,\"is_rental_pools\":false,\"internal_note\":null,\"is_duplex\":false,\"future_mortgage\":null,\"property_style\":\"1\",\"is_four_plex\":false,\"estimated_valueof_home\":null,\"heating\":\"2\",\"garage_size\":\"2\",\"desired_amortization_moved0\":null,\"insurerref\":null,\"buy_new_vehicle\":\"3\",\"is_rental_property\":false,\"block\":null,\"desired_product_type\":null,\"mortgage_type\":null,\"lender_requires_insurance\":false,\"distance_to_major_center\":null,\"morweb_filogix\":null,\"training_associate_referral\":null,\"sweat_equity_amount\":null,\"is_co_operative_housing\":false,\"existing_lender\":null,\"garage_type\":\"2\",\"square_footage\":24234,\"date_created_co_applicant\":null,\"rrsp_amount\":null,\"current_mortgage_product\":null,\"trailer_comp_amount\":null,\"concerns_addressed_check\":false,\"city\":\"Ottawa\",\"desired_term\":\"4\",\"is_non_conv_construction\":false,\"is_high_ratio_2nd_home\":false,\"sales_associate\":null,\"trigger\":null,\"lot\":null,\"down_payment_coming_from\":null,\"opp_info_rate\":null,\"condition_of_financing_date\":null,\"internal_notes\":null,\"insurer\":null,\"opp_info_start_date\":\"2015-06-25\",\"email_work\":null,\"what_is_your_lending_goal\":\"3\",\"is_life_leased_property\":false,\"down_payment_amount\":null,\"ltv\":null,\"GDS\":null,\"new_home_warranty\":null,\"looking_fora\":null,\"broker_fee\":null,\"has_charges_behind\":false,\"approached_check\":false,\"other_amount\":null,\"total_one_time_fees\":null,\"mls\":null,\"is_military\":false,\"TDS\":null,\"is_floating_homes\":false,\"volume_bonus_amount\":null,\"is_agricultural_less_10_acres\":false,\"verify_product\":false,\"renovation_value\":null,\"source_of_borrowing\":null,\"living_in_property\":null,\"is_age_restricted\":false,\"lender_name\":null,\"desired_mortgage_amount\":null,\"property_taxes\":null,\"income_decreased_worried\":\"3\",\"expected_closing_date\":null,\"is_six_plex\":false,\"is_rooming_houses\":false,\"is_condo\":false,\"is_fractional_interests\":false,\"existing_mortgage\":null,\"is_boarding_houses\":false,\"current_interest_rate\":null,\"assistant\":null,\"province\":\"ON\",\"web_response\":null,\"term_rate\":null,\"total_mortgage_amount\":null,\"building_funds\":null,\"charge_on_title\":\"1\",\"plan\":null,\"property_value\":234,\"amortization\":null,\"min_heat_fee\":null,\"spouse\":null,\"realtor\":null,\"non_income_qualifer\":false,\"lender_response\":null,\"product_type\":null,\"is_eight_plex\":false,\"age\":null,\"financial_risk_taker\":\"5\",\"charges_behind_amount\":null,\"personal_cash_amount\":null,\"applicant_last_name\":null,\"outbuildings_value\":1,\"lead_source\":null,\"existing_equity_amount\":null,\"renewaldate\":null,\"final_lender\":null,\"is_raw_land\":false,\"job_5_years\":\"5\",\"base_comp_amount\":null,\"webform_uname\":null,\"webform_pwd\":null,\"from_web_form\":null,\"opportunity_id\":null,\"current_monthly_payment\":null,\"current_lender_moved0\":null,\"current_lender\":null,\"current_balance\":null,\"maximum_amount\":false,\"desired_amortization_moved1\":null,\"Township_PID\":null,\"mortgage_insured\":false,\"needs_power_attorney\":false,\"desired_amortization\":25,\"bank_account\":null,\"lawyer\":null,\"referral_fee\":null,\"hr_department_id\":1,\"all_email_ids\":null,\"deadline\":\"2015-06-28 23:36:11\",\"template_date\":\"2015-07-04 03:25:43\",\"prod_count\":836,\"dup_task_created\":false,\"frequency\":\"1\",\"delayed_app_task\":false,\"plus_minus_prime\":null,\"new_opp_users\":null,\"congrats_date\":null,\"greeting_send\":false,\"delayed_app_date\":null,\"lead_followed\":false,\"client_email_rem\":false,\"client_remd\":false,\"completed_ref\":false,\"client_survey\":null,\"lead_followup_date\":null,\"renewal_mail_date\":null,\"welcum_email_date\":null,\"date_renewed\":false,\"commitment_fee\":null,\"private_fee\":null,\"lender_fee\":null,\"base_commission\":null,\"volume_commission\":null,\"task_created\":null,\"post_selection\":null,\"all_product_clicked\":null,\"final_documents\":null,\"renewal_email_send\":false,\"renewal_reminder\":null,\"document_fields\":null,\"to_pages\":null,\"from_pages\":null,\"fax1\":null}";
		
	
			return ok(index.render("",opportunityName, clientEmail,referralFirstName,referralLastName,referralSourceEmail, opporunityList));
	}
	
	public static Result opportuntyData(){
		

		
		String referralFirstName="";
        String referralLastName="";
        String referralSourceEmail="";
        String clientEmail="";
        String opportunityName="";


referralFirstName= request().getQueryString("referralFirstName");
referralLastName= request().getQueryString("referralLastName");
referralSourceEmail= request().getQueryString("referralSourceEmail");

clientEmail= request().getQueryString("clientEmail");
opportunityName= request().getQueryString("opportunityName");
    logger.debug("referralName "+referralFirstName  +"referrLastName "+referralLastName +" referralOSursceEmial "+referralSourceEmail+" clientnameEmail  "+clientEmail  +" opportunity Name "+opportunityName);
		ArrayList<OpportunityList> opporunityList = new ArrayList<OpportunityList>();

		try {
			opporunityList = new Odoo().getOpprunityDetials();
		} catch (Exception e) {
			logger.error("Error occured in processing opportuntyData"+e.getMessage());
		}
		return ok(index.render("",opportunityName, clientEmail,referralFirstName,referralLastName,referralSourceEmail, opporunityList));

	}

	

	public static Result createReferralSoursce() {

		DynamicForm dynamicForm = form().bindFromRequest();

		String referralFirstName = dynamicForm.get("first");
		String referralLastName = dynamicForm.get("last");
		String referralEmail = dynamicForm.get("referralemail");
		String applicantnameEmail = dynamicForm.get("applicantEmail");
		String opporunityName = dynamicForm.get("opportunity");
		Odoo oddoObjectRef = new Odoo();

		ArrayList<OpportunityList> opporunityList = null;
		try {
			opporunityList = new Odoo().getOpprunityDetials();
		} catch (Exception e) {
			logger.error("Error occured while processing createReferralSoursce "+e.getMessage());
		}

		int contactId = 0;

		boolean applicantDataUpdated = false;

		try {
			contactId = oddoObjectRef.createContact(referralFirstName,
					referralLastName, referralEmail, "res.partner");
		} catch (Exception e) {
			logger.error("Error occured while processing createReferralSoursce "+e.getMessage());
		}
		logger.debug("contactid " + contactId);
		int referralId = 0;
		try {
			referralId = oddoObjectRef.createReferral(referralFirstName + "_"
					+ referralLastName, referralEmail, contactId,
					"hr.applicant");
		} catch (Exception e) {
			logger.error("Error occured while processing createReferralSoursce "+e.getMessage());
		}
		logger.debug("referral Id  " + referralId);

		try {

			applicantDataUpdated = oddoObjectRef.UpdateOpprtunityData(
					opporunityName, referralId, applicantnameEmail);
		} catch (Exception e) {
			logger.error("Error occured while processing createReferralSoursce of applicantDataUpdated "+e.getMessage());
		}
		logger.debug("applicantDataUpdated " + applicantDataUpdated);

		String result = "Referral Source has been created successfully and set into Opportunity";

		if (contactId == 0) {
			result = "Contact Creation failed ";
		} else if (referralId == 0) {
			result = "Referral Resource  Creation failed ";

		} else if (!applicantDataUpdated) {
			result = "Updating Opportunity with referral resource   failed ";

		}

		logger.debug("referral First Name " + referralFirstName);
		logger.debug("referral Last Name " + referralLastName);
		logger.debug("referralEmail" + referralEmail);

		logger.debug("applicantnameEmail" + applicantnameEmail);

		logger.debug("opporunityName" + opporunityName);

		return ok(index.render(result, opporunityName, applicantnameEmail,
				referralFirstName, referralLastName, referralEmail,
				opporunityList));
	}

	public static Result stageChange() {
		JsonNode jsonData = request().body().asJson();
		logger.debug("json data --------------------" + jsonData);
		JSONObject jsonObject = null;
		ArrayList<String> result = new ArrayList<String>();
		SendWithUsExample sendWithus = new SendWithUsExample();
		Opprtunity opportunityData = null;

		String applicantName = "";
		String coApplicantName = "";
		String applicantEmail = "";
		String coApplicnatEmail = "";
		String referralEmail = "";
		String referralName = "";
		try {

			result.add("Changing stage failed ");

			jsonObject = new JSONObject(jsonData.toString());
			referralName = jsonObject.get("referralname").toString();
			referralEmail = jsonObject.get("referralEmail").toString();
			JSONObject jsonOpporunitydata = null;
			switch (jsonObject.get("oppo").toString()) {
			case "Completed App":
				// 11
				logger.debug("inside completed stage");
				try {
					opportunityData = new Odoo()
							.getOpprtunityApplicantData(jsonObject.get(
									"opportunityName").toString());
					Set<Applicants> setApplicants = opportunityData
							.getApplicants();
					int i = 0;
					for (Iterator iterator = setApplicants.iterator(); iterator
							.hasNext();) {
						Applicants applicants = (Applicants) iterator.next();
						if (i == 0) {
							applicantName = applicants.getApplicantFirstName();
							applicantEmail = applicants.getApplicantEmail();
						} else if (i == 1) {
							coApplicantName = applicants
									.getApplicantFirstName();
							coApplicnatEmail = applicants.getApplicantEmail();
						}
						i++;
					}
					sendWithus.sendTOreferralCompletedApp(referralName,
							applicantName, referralEmail, coApplicantName);
					sendWithus.sendDisclosuresToclientCompletedApp(
							applicantName, applicantEmail, coApplicantName,
							coApplicnatEmail, "");
					result = new ArrayList<String>();
					result.add("Stage changed to Completed App sucessfully ");
					logger.debug("end completed stage");

				} catch (Exception e) {
					// TODO: handle exception

					result.add("Changing stage failed ");
					logger.error("Error occured while processing stageChange "+e.getMessage());
				}
				break;

			case "Awaiting Docs":

				logger.debug("insede Awaiting stage");

				// 20

				try {
					opportunityData = new Odoo()
							.getOpprtunityApplicantData(jsonObject.get(
									"opportunityName").toString());
					Set<Applicants> setApplicants = opportunityData
							.getApplicants();
					int i = 0;
					String applicantId = "0";
					for (Iterator iterator = setApplicants.iterator(); iterator
							.hasNext();) {
						Applicants applicants = (Applicants) iterator.next();
						if (i == 0) {
							applicantName = applicants.getApplicantFirstName();
							applicantEmail = applicants.getApplicantEmail();
							applicantId = applicants.getApplicantId();
						} else if (i == 1) {
							coApplicantName = applicants
									.getApplicantFirstName();
							coApplicnatEmail = applicants.getApplicantEmail();
						}
						i++;
					}
					MortgageDocumentConverter.convertToApplicantDocument(
							"Applicant_" + applicantId, applicantEmail);
					// sendWithus.sendTOApplicantDisclosures(applicantFirstName,
					// EmailId, coApplicantFirstName)
					result = new ArrayList<String>();

					result.add("Stage changed to Awaiting Docs sucessfully ");
					logger.debug("ende Awaiting stage");

				} catch (Exception e) {
					// TODO: handle exception

					result.add("Changing stage failed ");
				logger.error("Error occured while processing stageChange "+e.getMessage());
				}
				break;

			case "All Product":
				// 22
				break;
			case "Proposal":
				logger.debug("inside  Proposal stage");

				try {

					String id = Odoo.getOpprtunityID(jsonObject.get(
							"opportunityName").toString());
					logger.debug("opportunity data ----------------->"
							+ id);
					jsonOpporunitydata = Odoo.getOpprunityData(id);
					
					logger.debug("jsonOpporunitydata--------------------->"
									+ jsonOpporunitydata);
					if (jsonOpporunitydata != null) {
						jsonOpporunitydata.put("stage_id", 17);
						new RestCallClass(jsonOpporunitydata.toString())
								.start();

						result = new ArrayList<String>();

						result.add(" Stage changed to Proposal sucessfully");
						logger.debug("postelection chnaged sucessfully");

					}
				} catch (Exception e) {
					// TODO: handle exception

					result.add("Changing stage failed ");
					logger.error("error in Proposal stage"+e.getMessage());

				}
				logger.debug("postelection ended");
				break;

			case "Post Selection":
				try {

					logger.debug("inside Post Selection stage");

					String id = Odoo.getOpprtunityID(jsonObject.get(
							"opportunityName").toString());
					logger.debug("opportunity data ----------------->"
							+ id);
					jsonOpporunitydata = Odoo.getOpprunityData(id);
					jsonOpporunitydata.put("stage_id", 23);

					if (jsonOpporunitydata != null) {
						new RestCallClass(jsonOpporunitydata.toString())
								.start();

						result = new ArrayList<String>();

						result.add(" Stage changed to Post Selection sucessfully");

					}

					logger.debug("ende Post Selection stage");

				} catch (Exception e) {
					// TODO: handle exception

					result.add("Changing stage failed ");
					logger.error("error occured in jsondata"+e.getMessage());
				}
				break;

			case "Lender Submit":

				break;

			case "Commitment":
				try {
					String id = Odoo.getOpprtunityID(jsonObject.get(
							"opportunityName").toString());
					logger.debug("opportunity data ----------------->"
							+ id);
					jsonOpporunitydata = Odoo.getOpprunityData(id);

					if (jsonOpporunitydata != null) {
						jsonOpporunitydata.put("stage_id", 14);

						new RestCallClass(jsonOpporunitydata.toString())
								.start();

						result = new ArrayList<String>();

						result.add("Stage changed to Commitment  sucessfully");

					}
				} catch (Exception e) {
					// TODO: handle exception

					result.add("Changing stage failed ");
					logger.error("error occured in jsondata"+e.getMessage());
				}
				break;

			case "Compensation":
				try {

					logger.debug("inside Compensation stage");

					String id = Odoo.getOpprtunityID(jsonObject.get(
							"opportunityName").toString());
					logger.debug("opportunity data ----------------->"
							+ id);
					jsonOpporunitydata = Odoo.getOpprunityData(id);

					if (jsonOpporunitydata != null) {
						jsonOpporunitydata.put("stage_id", 16);

						new RestCallClass(jsonOpporunitydata.toString())
								.start();

						result = new ArrayList<String>();

						result.add(" Stage changed to Compensation sucessfully");

					}
					logger.debug("ende Compensation stage");

				} catch (Exception e) {
					result.add("Changing stage failed ");
					logger.error("error occured in jsondata"+e.getMessage());
					// TODO: handle exception
				}
				break;

			case "Paid":
				try {
					logger.debug("inside paied stage");

					String id = Odoo.getOpprtunityID(jsonObject.get(
							"opportunityName").toString());
					logger.debug("opportunity data ----------------->"
							+ id);
					jsonOpporunitydata = Odoo.getOpprunityData(id);
					if (jsonOpporunitydata != null) {
						jsonOpporunitydata.put("stage_id", 18);

						new RestCallClass(jsonOpporunitydata.toString())
								.start();
						result = new ArrayList<String>();

						result.add(" Stage changed to Paid sucessfully");

					}

					logger.debug("ende Compensation stage");

				} catch (Exception e) {

					result.add("Changing stage failed ");
					logger.error("error occured in jsondata"+e.getMessage());
					// TODO: handle exception
				}
				break;

			default:

				break;
			}
		} catch (Exception e) {
			logger.error("error occured in connection"+e.getMessage());
		}
		return ok(play.libs.Json.toJson(result));
	}
}
