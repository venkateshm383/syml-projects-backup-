package controllers;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import play.Logger;


public class RestCallClass extends Thread {
	private static org.slf4j.Logger logger = play.Logger.underlying();
	
	String leaddata;
	
	
	public static void main(String[] args) throws JSONException {
		JSONObject json=new JSONObject();

String myvar = "{\"id\":4406,\"create_uid\":26,\"create_date\":\"2015-06-25 23:36:00.444185\",\"write_date\":\"2015-07-01 22:20:39.966939\",\"write_uid\":26,\"date_closed\":null,\"type_id\":null,\"color\":0,\"date_action_last\":null,\"day_close\":0,\"active\":true,\"street\":\"98 Strathcona Ave\",\"day_open\":0,\"contact_name\":null,\"partner_id\":4264,\"date_open\":null,\"user_id\":26,\"opt_out\":false,\"title\":null,\"partner_name\":null,\"planned_revenue\":null,\"country_id\":null,\"company_id\":1,\"priority\":\"3\",\"state\":\"all_product\",\"email_cc\":null,\"date_action_next\":null,\"type\":\"opportunity\",\"street2\":null,\"function\":null,\"fax\":null,\"description\":null,\"planned_cost\":null,\"ref2\":null,\"section_id\":null,\"title_action\":null,\"phone\":null,\"probability\":null,\"payment_mode\":null,\"date_action\":null,\"name\":\"veerendra lastname - 87 Strathcona Ave, Ottawa, First, 2015-06-2\",\"stage_id\":14,\"zip\":null,\"date_deadline\":null,\"mobile\":\"422-453-5321\",\"ref\":null,\"channel_id\":null,\"state_id\":null,\"email_from\":\"venkatesh.m@bizruntime.com\",\"referred\":null,\"marketing_auto\":null,\"opp_info_loan_amnt\":null,\"referral_source\":null,\"is_commercial\":false,\"underwriter\":null,\"considered_cites\":null,\"opp_info_type\":null,\"property_type\":\"2\",\"secondary_financing_amount\":null,\"is_modular_homes\":false,\"lifestyle_change\":\"5\",\"cash_back\":null,\"is_grow_ops\":false,\"propsal_date\":\"2015-07-04 22:21:19\",\"is_country_residential\":false,\"op_info_type\":null,\"lender\":null,\"Amortization\":null,\"monthly_rental_income\":0,\"internal_notes_final\":null,\"process_presntedutio_check\":false,\"monthly_payment\":null,\"total_comp_amount\":null,\"condo_fees\":null,\"open_closed\":null,\"current_renewal_date\":null,\"lender_ref\":null,\"marketing_comp_amount\":null,\"acres\":null,\"application_date\":\"2015-06-25 23:40:13\",\"is_leased_land\":false,\"term\":null,\"is_mobile_homes\":false,\"closing_date\":null,\"insurerfee\":null,\"looking_to\":null,\"property_less_then_5_years\":\"3\",\"current_mortgage_type\":null,\"lot_size\":null,\"is_agricultural\":false,\"postal_code\":\"K1S 1X5\",\"sale_of_existing_amount\":null,\"sewage\":\"1\",\"downpayment_amount\":null,\"qualified_check\":false,\"pending_application_check\":false,\"trainee\":null,\"renter_pay_heating\":null,\"gifted_amount\":null,\"rate\":null,\"is_construction_mortgage\":false,\"selected_product\":null,\"draws_required\":null,\"work_phone\":null,\"current_mortgage_amount\":42234,\"internal_note_property\":null,\"apartment_style\":null,\"is_uninsured_conv_2nd_home\":false,\"opp_info_renewal_date\":\"2015-06-25\",\"borrowed_amount\":null,\"purchase_price\":null,\"is_cottage_rec_property\":false,\"preferred_number\":\"cell\",\"future_family\":\"5\",\"referred_source\":392,\"water\":\"2\",\"existing_payout_penalty\":null,\"desired_mortgage_type\":\"1\",\"credit_story\":null,\"application_no\":\"4327\",\"address\":\"87 Strathcona Ave\",\"isUpdatedToUA\":false,\"is_a_small_centre\":false,\"is_rental_pools\":false,\"internal_note\":null,\"is_duplex\":false,\"future_mortgage\":null,\"property_style\":\"1\",\"is_four_plex\":false,\"estimated_valueof_home\":null,\"heating\":\"2\",\"garage_size\":\"2\",\"desired_amortization_moved0\":null,\"insurerref\":null,\"buy_new_vehicle\":\"3\",\"is_rental_property\":false,\"block\":null,\"desired_product_type\":null,\"mortgage_type\":null,\"lender_requires_insurance\":false,\"distance_to_major_center\":null,\"morweb_filogix\":null,\"training_associate_referral\":null,\"sweat_equity_amount\":null,\"is_co_operative_housing\":false,\"existing_lender\":null,\"garage_type\":\"2\",\"square_footage\":24234,\"date_created_co_applicant\":null,\"rrsp_amount\":null,\"current_mortgage_product\":null,\"trailer_comp_amount\":null,\"concerns_addressed_check\":false,\"city\":\"Ottawa\",\"desired_term\":\"4\",\"is_non_conv_construction\":false,\"is_high_ratio_2nd_home\":false,\"sales_associate\":null,\"trigger\":null,\"lot\":null,\"down_payment_coming_from\":null,\"opp_info_rate\":null,\"condition_of_financing_date\":null,\"internal_notes\":null,\"insurer\":null,\"opp_info_start_date\":\"2015-06-25\",\"email_work\":null,\"what_is_your_lending_goal\":\"3\",\"is_life_leased_property\":false,\"down_payment_amount\":null,\"ltv\":null,\"GDS\":null,\"new_home_warranty\":null,\"looking_fora\":null,\"broker_fee\":null,\"has_charges_behind\":false,\"approached_check\":false,\"other_amount\":null,\"total_one_time_fees\":null,\"mls\":null,\"is_military\":false,\"TDS\":null,\"is_floating_homes\":false,\"volume_bonus_amount\":null,\"is_agricultural_less_10_acres\":false,\"verify_product\":false,\"renovation_value\":null,\"source_of_borrowing\":null,\"living_in_property\":null,\"is_age_restricted\":false,\"lender_name\":null,\"desired_mortgage_amount\":null,\"property_taxes\":null,\"income_decreased_worried\":\"3\",\"expected_closing_date\":null,\"is_six_plex\":false,\"is_rooming_houses\":false,\"is_condo\":false,\"is_fractional_interests\":false,\"existing_mortgage\":null,\"is_boarding_houses\":false,\"current_interest_rate\":null,\"assistant\":null,\"province\":\"ON\",\"web_response\":null,\"term_rate\":null,\"total_mortgage_amount\":null,\"building_funds\":null,\"charge_on_title\":\"1\",\"plan\":null,\"property_value\":234,\"amortization\":null,\"min_heat_fee\":null,\"spouse\":null,\"realtor\":null,\"non_income_qualifer\":false,\"lender_response\":null,\"product_type\":null,\"is_eight_plex\":false,\"age\":null,\"financial_risk_taker\":\"5\",\"charges_behind_amount\":null,\"personal_cash_amount\":null,\"applicant_last_name\":null,\"outbuildings_value\":1,\"lead_source\":null,\"existing_equity_amount\":null,\"renewaldate\":null,\"final_lender\":null,\"is_raw_land\":false,\"job_5_years\":\"5\",\"base_comp_amount\":null,\"webform_uname\":null,\"webform_pwd\":null,\"from_web_form\":null,\"opportunity_id\":null,\"current_monthly_payment\":null,\"current_lender_moved0\":null,\"current_lender\":null,\"current_balance\":null,\"maximum_amount\":false,\"desired_amortization_moved1\":null,\"Township_PID\":null,\"mortgage_insured\":false,\"needs_power_attorney\":false,\"desired_amortization\":25,\"bank_account\":null,\"lawyer\":null,\"referral_fee\":null,\"hr_department_id\":1,\"all_email_ids\":null,\"deadline\":\"2015-06-28 23:36:11\",\"template_date\":\"2015-07-04 03:25:43\",\"prod_count\":836,\"dup_task_created\":false,\"frequency\":\"1\",\"delayed_app_task\":false,\"plus_minus_prime\":null,\"new_opp_users\":null,\"congrats_date\":null,\"greeting_send\":false,\"delayed_app_date\":null,\"lead_followed\":false,\"client_email_rem\":false,\"client_remd\":false,\"completed_ref\":false,\"client_survey\":null,\"lead_followup_date\":null,\"renewal_mail_date\":null,\"welcum_email_date\":null,\"date_renewed\":false,\"commitment_fee\":null,\"private_fee\":null,\"lender_fee\":null,\"base_commission\":null,\"volume_commission\":null,\"task_created\":null,\"post_selection\":null,\"all_product_clicked\":null,\"final_documents\":null,\"renewal_email_send\":false,\"renewal_reminder\":null,\"document_fields\":null,\"to_pages\":null,\"from_pages\":null,\"fax1\":null}";
	
 new RestCallClass().restcallTostagMail(myvar);
	}
	

public RestCallClass(String leaddata) {
	this.leaddata = leaddata;
}
public RestCallClass( ) {
}

@Override
public void run() {
	// TODO Auto-generated method stub
	
	restcallTostagMail(leaddata);
}

	public  void restcallTostagMail(String leadData){
		
		try {
			
			logger.info("eadData");
			logger.debug("leadData.... \n"+leadData);
             SSLContext ctx;
				
				ctx = SSLContext.getInstance("TLS");
			
	        ctx.init(new KeyManager[0], new TrustManager[] {(TrustManager) new DefaultTrustManager()}, new SecureRandom());
	        SSLContext.setDefault(ctx);

	        URL url = new URL("https://dev-stagedemo.visdom.ca/lead");
	        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
	      conn.setHostnameVerifier(new HostnameVerifier() {
	            @Override
	            public boolean verify(String arg0, SSLSession arg1) {
	                return true;
	            }	
	        });
			
			
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");
				
				logger.debug("leadData.... \n"+leadData);

				String input=leadData;
			OutputStream os = conn.getOutputStream();
					os.write(input.getBytes());
					os.flush();
					if (conn.getResponseCode() != 200) {
						throw new RuntimeException("Failed : HTTP error code : "
							+ conn.getResponseCode());
					}else{
			 
					BufferedReader br = new BufferedReader(new InputStreamReader(
							(conn.getInputStream())));
			 
					
					logger.debug("Output from Server .... \n");
	                               String output;
					while ((output = br.readLine()) != null) {
						logger.debug(output);	
					}
	              }
			 
					conn.disconnect();
				  } catch (Exception e) {
			     logger.error("Error occured in connection "+e.getMessage());	
				  } 		
		}
	
	private static class DefaultTrustManager implements X509TrustManager {
           @Override
			public void checkClientTrusted(X509Certificate[] chain, String authType)
					throws CertificateException {
				// TODO Auto-generated method stub	
			}

			@Override
			public void checkServerTrusted(X509Certificate[] chain, String authType)
					throws CertificateException {
				// TODO Auto-generated method stub
				
			}

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				// TODO Auto-generated method stub
				return null;
			}
	    }
		
}


