package controllers;


import infrastracture.CouchBaseOperation;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Enumeration;
import java.util.GregorianCalendar;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class StorePlanerDataToCouhbase extends Thread {
	public static void main(String[] args) {
		//new StoredWistiaDataTocouchabse().storeRealtorData();
		//new StoredWistiaDataTocouchabse().getPublicIpAddress();
	
	}
	String contactId;
	String email;
	String ip;
	String leadId;

	public StorePlanerDataToCouhbase(String contactId, String email, String ip,String leadId) {
		
		this.contactId = contactId;
		this.email = email;
		this.ip = ip;
		this.leadId = leadId;


	}

	
	public void run() {
		
		
		

		try {
			Thread.sleep(240000);
		} catch (Exception e) {

		}

		JSONArray jsonArray = storeRealtorData(contactId, email);
		System.out.println(jsonArray);
		String applicantName = couchBaseOperation.getReferralname(contactId,
				email);

		if (jsonArray != null) {
			
			boolean test = true;
			try {
				while (test) {
					for (int i = 0; i < jsonArray.length(); i++) {
						
						JSONObject jsonObject=jsonArray.getJSONObject(i);
					
					try {
						jsonObject.put("ApplicantName", applicantName);
						jsonObject.put("applicantId", contactId);
						jsonObject.put("DateTime", cal.getTime());
						jsonObject.put("leadId",leadId);

					} catch (Exception e) {

					}
					if (ip.equalsIgnoreCase(jsonObject.get("ip").toString())) {
						JSONArray json = getVisitorCount();
						String key = "0";
						if (json != null) {
							for (int j = 0; j < json.length(); j++) {
								JSONObject vistorData=json.getJSONObject(j);
								System.out.println("json------"+jsonObject.get("visitor_key").toString()+"  ==   "+vistorData.get("visitor_key").toString());
								if(jsonObject.get("visitor_key").toString().equalsIgnoreCase(vistorData.get("visitor_key").toString())){
									key = vistorData.get("play_count").toString();
	
									break;
								}
								
							}
						}
						couchBaseOperation.storeDataInCouchBase(
								"Vedio_plannerpage_" + key, jsonObject);
						test = false;
					} else {

						try {
							Thread.sleep(200000);
						} catch (Exception e) {

						}
					}
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	GregorianCalendar cal = new GregorianCalendar();

	CouchBaseOperation couchBaseOperation = new CouchBaseOperation();
	SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");

	public JSONArray storeRealtorData(String contactId, String email) {

		JSONArray json = null;

		try {
			System.out.println();

			System.out.println(date.format(cal.getTime()));
			// String
			// ur="https://api.wistia.com/v1/stats/events.json?api_password=6bca8b64faaf09189e8266ed51b726292fd133063ffc1ca0d9fab9fc3661f3ab";
			String ur = "https://api.wistia.com/v1/stats/events.json?api_password=6bca8b64faaf09189e8266ed51b726292fd133063ffc1ca0d9fab9fc3661f3ab&start_date="
					+ date.format(cal.getTime())
					+ "&end_date="
					+ date.format(cal.getTime()) + "media_id=2kyvz697oy";

			URLEncoder.encode(ur, "UTF-8");
			URL url = new URL(ur);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			} else {

				BufferedReader br = new BufferedReader(new InputStreamReader(
						(conn.getInputStream())));

				System.out.println("Output from Server .... \n");
				String output;
				while ((output = br.readLine()) != null) {

					json = new JSONArray(output);

				}

			}

			conn.disconnect();

		} catch (Exception e) {

			e.printStackTrace();

		}
		return json;

	}

	public JSONArray getVisitorCount() {

		// String applicantName=couchBaseOperation.getReferralname(contactId,
		// email);
		JSONArray json = null;

		try {
			System.out.println();

			System.out.println(date.format(cal.getTime()));
			// String
			// ur="https://api.wistia.com/v1/stats/events.json?api_password=6bca8b64faaf09189e8266ed51b726292fd133063ffc1ca0d9fab9fc3661f3ab";
			String ur = "https://api.wistia.com/v1/stats/visitors.json?api_password=6bca8b64faaf09189e8266ed51b726292fd133063ffc1ca0d9fab9fc3661f3ab&media_id=2kyvz697oy";

			URLEncoder.encode(ur, "UTF-8");
			URL url = new URL(ur);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			} else {

				BufferedReader br = new BufferedReader(new InputStreamReader(
						(conn.getInputStream())));

				System.out.println("Output from Server .... \n");
				String output;
				while ((output = br.readLine()) != null) {

					json = new JSONArray(output);

				}

			}

			conn.disconnect();

		} catch (Exception e) {

			e.printStackTrace();

		}
		return json;

	}
}
