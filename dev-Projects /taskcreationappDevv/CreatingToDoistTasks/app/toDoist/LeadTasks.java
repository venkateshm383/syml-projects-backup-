package toDoist;

import infrastracture.CouchBaseOperation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.UUID;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.couchbase.client.CouchbaseClient;

import dto.User;

public class LeadTasks {
 	static Logger log = LoggerFactory.getLogger(LeadTasks.class);

	// assistant
	public static void main(String[] args) throws JSONException {
		createLeadtasks("123455", "teste_test", "45356343", "test","test");
	}

	static String token = "19fb71b3edb93f05952f23e0b99120806ec7d224";
	// dale user
	static String todoistUser = null;
	static String daletoken = "181e3a07f6893de7946eaba76982bc0522791bf1";

	// darry1 user
	static String darry1token = "9eeb0908e4998327c65852bf31dfd16cf05c6b82";
	static	String  assignUser=null;

	public static String gettodoistUser() throws JSONException {
		CouchbaseClient cleClient = null;
		try {
			cleClient = new CouchBaseOperation().getConnectionToCouchBase();
			JSONObject jsonObject = new JSONObject(
				cleClient.get("TaskAssignusers1").toString());
			todoistUser = jsonObject.get("user").toString();
			log.debug("todoist user "+todoistUser);
			if (todoistUser.trim().equalsIgnoreCase("Audrey")) {
				todoistUser = "brenda@visdom.ca";
				assignUser="5528089";
				
				JSONObject json = new JSONObject();
				json.put("user", "brenda@visdom.ca");
				
				cleClient.replace("TaskAssignusers1", json.toString());
				log.debug("todoist user "+todoistUser);

			} else if (todoistUser.trim().equalsIgnoreCase("brenda@visdom.ca")) {
				
				todoistUser = "Audrey";
				assignUser="4287399";
				JSONObject json = new JSONObject();
				json.put("user", "Audrey");
				
				cleClient.replace("TaskAssignusers1", json.toString());
				log.debug("todoist user if  else "+todoistUser);

			}else{
				JSONObject json = new JSONObject();
				json.put("user", "Audrey");
				todoistUser = "Audrey";
				assignUser="4287399";

				cleClient.replace("TaskAssignusers1", json.toString());
				
				log.info("todoist user else "+todoistUser);

			}
		} catch (Exception e) {
			JSONObject json = new JSONObject();
			json.put("user", "Audrey");
			todoistUser = "Audrey";
			assignUser="4287399";

			cleClient.set("TaskAssignusers1", json.toString());
			e.printStackTrace();
		}
		try{
		cleClient.shutdown();	
		}catch(Exception e){
			
		}

		return assignUser;

	}

	public static String createProject(String projectName, String token)
			throws JSONException {

		UUID uid = UUID.randomUUID();
		UUID uid1 = UUID.randomUUID();
		log.info("project " + projectName);

		String projectId = null;
		log.info("uid " + uid);
		log.info("uid 2" + uid1);

		String myvar = "[{\"type\": \"project_add\", " + "\"temp_id\": \""
				+ uid + "\"," + " \"uuid\": \"" + uid1 + "\","
				+ " \"args\": {\"name\": \"" + projectName + "\"}}]";

		try {
			// log.info(URLEncoder.encode(myvar));

			URL url1 = new URL("https://todoist.com/API/v6/sync?token=" + token
					+ "&commands=" + URLEncoder.encode(myvar, "UTF-8"));
			HttpURLConnection conn1 = (HttpURLConnection) url1.openConnection();
			conn1.setDoOutput(true);
			conn1.setRequestMethod("POST");

			if (conn1.getResponseCode() != 200) {
				log.info("Failed : HTTP error code : "
						+ conn1.getResponseCode());
			} else {

				BufferedReader br1 = new BufferedReader(new InputStreamReader(
						(conn1.getInputStream())));

				log.info("Output from Server .... \n");
				String output1;
				while ((output1 = br1.readLine()) != null) {
					// log.info(output1);
					JSONObject jsondata = new JSONObject(output1);
					JSONObject insideJsondata = (JSONObject) jsondata
							.get("TempIdMapping");
					projectId = insideJsondata.get(uid.toString()).toString();
				}

			}
			// createLabels(projectName, token);
			conn1.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
		return projectId;
	}

	public static void createLeadtasks(String leadid, String leadName,
			String leadPhone, String leadReferralName, String leadReferralPhone)
			throws JSONException {
		String leadfullName = leadName;
		try {
			String[] str = leadName.split("_");
			leadName = str[0] + " " + str[1];
		} catch (Exception e) {

		}
		String leadTask1 = "Call "
				+ leadName
				+ " at "
				+ leadPhone
				+ " to ensure email has been received and it did not go into spam. Welcome to Visdom and assist with the application process if needed. Get an estimated time when they will have the mortgage application completed.";
		String leadtasktime = "30";
		String leadTask1note = "- Set reminder deadline for completed Mortgage Application as discussed with client call. \n- If no email was received confirming email address.  If this email address is correct have client verify the email is not in their spam folder (have them click Not Spam).   \n- If not in spam, get new email address, forward previous sent message with link. \n- Change email address in Business System in Contacts";
		String leadtask2 = "Follow up call with " + leadName + " at "
				+ leadPhone
				+ " to ensure the Mortgage Application has been received.";
		String leadtask2note = "- Use Call Script \n - Set reminder deadline for completed Mortgage Application as discussed with client call. \n- If no email was received confirming email address.  If this email address is correct have client verify the email is not in their spam folder (have them click Not Spam). \n  - If not in spam, get new email address, forward previous sent message with link. \n - Change email address in Business System in Contacts \n- Follow up Call to ensure Morgage Application is received (Reminder set deadline from phone call)";
		String leadtask3 = "Call "
				+ leadReferralName
				+ " at "
				+ leadReferralPhone
				+ " as we have not received an application and provide assistance in influencing client to complete application.";

		String leadtask3time = "1";
		String leadtask3note = "Step 1 - Follow up in 6 hours \nStep 2 - Follow up in 2 days \n Step 3 - Follow up in 7 days \nStep 4 - 10 day Call referral (if realtor) \nStep 5 - 14 days Move to lost";
		String projectId = "0";
		String taskId = "0";
		projectId = createProject(leadfullName + "_" + leadid, token);
		log.info("project Id-----------------------" + projectId);
		taskId = createTasks(leadTask1, projectId, token);
		SimpleDateFormat formatDate = new SimpleDateFormat("HH:mm a");

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, 30);
		String newTime = formatDate.format(cal.getTime());
		createLabel(leadid, token);

		taskUpdated(taskId, newTime);
		createNotes(taskId, leadTask1note, token);
		String taskId1 = "0";

		taskId1 = createTasks(leadtask2, projectId, token);
		createLabel(leadid + "_" + leadName, token);
		taskUpdated(taskId1, "+1");

		createNotes(taskId1, leadtask2note, token);
		String taskId2 = "0";

		taskId2 = createTasks(leadtask3, projectId, token);
		createLabel(leadid + "_" + leadReferralName, token);

		taskUpdated(taskId2, "+1");
		createNotes(taskId2, leadtask3note, token);

		// gettodoistUser();
		// User user=new CouchBaseOperation().getTodoistUserEmail(todoistUser);

		ArrayList<User> list = (ArrayList<User>) new CouchBaseOperation()
				.getUsers();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			User user2 = (User) iterator.next();
			if (!user2.getUserName().trim().equalsIgnoreCase("assistant")) {
				shareProject(projectId, user2.getEmail(), token);
			}

		}
		assignUser=gettodoistUser();
		assignUser(taskId, assignUser, "+1");
		assignUser(taskId1, assignUser, "+1");
		assignUser(taskId2, assignUser, "+1");


	}

	public static String createTasks(String taskName, String projectId,
			String token) throws JSONException {
		String taskId = "0";
		UUID uid = UUID.randomUUID();
		UUID uid1 = UUID.randomUUID();
		log.info("task name" + taskName);
		// log.info("uid " + uid);
		// log.info("uid 2" + uid1);
		String myvar = "[{\"type\": \"item_add\"," + " \"temp_id\": \"" + uid
				+ "\"," + " \"uuid\": \"" + uid1 + "\","
				+ " \"args\": {\"content\": \"" + taskName + "\","
				+ " \"project_id\": " + projectId + "}}]";

		try {
			// log.info(URLEncoder.encode(myvar));

			URL url1 = new URL("https://todoist.com/API/v6/sync?token=" + token
					+ "&commands=" + URLEncoder.encode(myvar, "UTF-8"));
			HttpURLConnection conn1 = (HttpURLConnection) url1.openConnection();
			conn1.setDoOutput(true);
			conn1.setRequestMethod("POST");

			if (conn1.getResponseCode() != 200) {
				log.info("Failed : HTTP error code : "
						+ conn1.getResponseCode());
			} else {

				BufferedReader br1 = new BufferedReader(new InputStreamReader(
						(conn1.getInputStream())));

				log.info("Output from Server .... \n");
				String output1;
				while ((output1 = br1.readLine()) != null) {
					// log.info(output1);

					JSONObject jsondata = new JSONObject(output1);
					JSONObject insideJsondata = (JSONObject) jsondata
							.get("TempIdMapping");
					taskId = insideJsondata.get(uid.toString()).toString();

				}

			}

			conn1.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
		return taskId;
	}

	public static void taskUpdated(String taskId, String date)
			throws JSONException {

		UUID uid = UUID.randomUUID();
		UUID uid1 = UUID.randomUUID();

		// log.info("uid " + uid);
		// log.info("uid 2" + uid1);

		String myvar = "[{\"type\": \"item_update\"," + " \"uuid\": \"" + uid
				+ "\", \"args\": {\"id\": " + taskId + ", \"date_string\": \""
				+ date + "\",\"priority\": 4}}]";

		JSONArray jsonArray = new JSONArray();
		JSONObject obj = new JSONObject();
		JSONObject obj2 = new JSONObject();
		obj2.put("id", taskId);
		obj2.put("date_string", date);
		obj.put("type", "item_update");
		obj.put("uuid", uid);
		obj.put("args", obj2);
		jsonArray.put(obj);

		try {
			// log.info(URLEncoder.encode(myvar));

			URL url1 = new URL("https://todoist.com/API/v6/sync?token=" + token
					+ "&commands=" + URLEncoder.encode(myvar, "UTF-8"));
		//	log.info(url1);
			HttpURLConnection conn1 = (HttpURLConnection) url1.openConnection();
			conn1.setDoOutput(true);
			conn1.setRequestMethod("POST");

			if (conn1.getResponseCode() != 200) {
				log.info("Failed : HTTP error code : "
						+ conn1.getResponseCode());
			} else {

				BufferedReader br1 = new BufferedReader(new InputStreamReader(
						(conn1.getInputStream())));

				log.info("Output from Server .... \n");
				String output1;
				while ((output1 = br1.readLine()) != null) {
					// log.info(output1);

				}

			}

			conn1.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}
	
	public static void assignUser(String taskId, String assignId,String date)
			throws JSONException {

		UUID uid = UUID.randomUUID();
		UUID uid1 = UUID.randomUUID();

		// log.info("uid " + uid);
		// log.info("uid 2" + uid1);

		String myvar = "[{\"type\": \"item_update\"," + " \"uuid\": \"" + uid
				+ "\", \"args\": {\"id\": " + taskId + ",\"responsible_uid\":"+assignId+",\"priority\": 4}}]";


		try {
			// log.info(URLEncoder.encode(myvar));

			URL url1 = new URL("https://todoist.com/API/v6/sync?token=" + token
					+ "&commands=" + URLEncoder.encode(myvar, "UTF-8"));
			//log.info(url1);
			HttpURLConnection conn1 = (HttpURLConnection) url1.openConnection();
			conn1.setDoOutput(true);
			conn1.setRequestMethod("POST");

			if (conn1.getResponseCode() != 200) {
				System.out.println("Failed : HTTP error code : "
						+ conn1.getResponseCode());
			} else {

				BufferedReader br1 = new BufferedReader(new InputStreamReader(
						(conn1.getInputStream())));

				System.out.println("Output from Server .... \n");
				String output1;
				while ((output1 = br1.readLine()) != null) {
					// System.out.println(output1);

				}

			}

			conn1.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	public static void createLabel(String label, String token)
			throws JSONException {

		UUID uid = UUID.randomUUID();
		UUID uid1 = UUID.randomUUID();

		String projectId = null;
		System.out.println("uid " + uid);
		System.out.println("uid 2" + uid1);

		String myvar = "[{\"type\": \"label_add\"," + " \"temp_id\": \"" + uid
				+ "\"," + " \"uuid\": \"" + uid1 + "\", "
				+ "\"args\": {\"name\": \"" + label + "\"}}]";

		try {
			// System.out.println(URLEncoder.encode(myvar));

			URL url1 = new URL("https://todoist.com/API/v6/sync?token=" + token
					+ "&commands=" + URLEncoder.encode(myvar, "UTF-8"));
			HttpURLConnection conn1 = (HttpURLConnection) url1.openConnection();
			conn1.setDoOutput(true);
			conn1.setRequestMethod("POST");

			if (conn1.getResponseCode() != 200) {
				System.out.println("Failed : HTTP error code : "
						+ conn1.getResponseCode());
			} else {

				BufferedReader br1 = new BufferedReader(new InputStreamReader(
						(conn1.getInputStream())));

				System.out.println("Output from Server .... \n");
				String output1;
				while ((output1 = br1.readLine()) != null) {
					// System.out.println(output1);
				}

			}
			// createLabels(projectName, token);
			conn1.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	public static void createNotes(String taskId, String note, String token) {
		System.out.println(taskId + "niote" + note);
		UUID uid = UUID.randomUUID();
		UUID uid1 = UUID.randomUUID();

		String myvar = "[{\"type\": \"note_add\", " + "\"temp_id\": \"" + uid
				+ "\"," + " \"uuid\": \"" + uid1 + "\", "
				+ "\"args\": {\"item_id\": " + taskId + ","
				+ " \"content\": \"" + note + "\"}}]";

		try {
			// System.out.println(URLEncoder.encode(myvar));

			// URL url1 = new URL(
			// "https://todoist.com/API/v6/sync?token=19fb71b3edb93f05952f23e0b99120806ec7d224&commands="
			// + URLEncoder.encode(myvar, "UTF-8"));
			URL url1 = new URL("https://todoist.com/API/v6/sync?token=" + token
					+ "&commands=" + URLEncoder.encode(myvar, "UTF-8"));
			HttpURLConnection conn1 = (HttpURLConnection) url1.openConnection();
			conn1.setDoOutput(true);
			conn1.setRequestMethod("POST");
			// conn1.setRequestProperty("Content-Type", "application/json");

			/*
			 * String
			 * input="{\"token\":\""+token+"\",\"commands\":\""+myvar+"\"}";
			 * System.out.println(input); OutputStream os =
			 * conn1.getOutputStream(); os.write(input.getBytes()); os.flush();
			 */
			if (conn1.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn1.getResponseCode());
			} else {

				BufferedReader br1 = new BufferedReader(new InputStreamReader(
						(conn1.getInputStream())));

				// System.out.println("Output from Server .... \n");
				String output1;
				while ((output1 = br1.readLine()) != null) {
					// System.out.println(output1);
				}

			}

			conn1.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	public static void shareProject(String projectId, String email, String token) {

		UUID uid = UUID.randomUUID();
		UUID uid1 = UUID.randomUUID();
		System.out.println("email " + email + "" + projectId);

		String myvar = "[{\"type\": \"share_project\"," + " \"temp_id\": \""
				+ uid + "\"," + " \"uuid\": \"" + uid1 + "\","
				+ " \"args\": {\"project_id\": \"" + projectId + "\","
				+ " \"message\": \"\", \"email\": \"" + email + "\"}}]";

		try {
			// System.out.println(URLEncoder.encode(myvar));

			URL url1 = new URL("https://todoist.com/API/v6/sync?token=" + token
					+ "&commands=" + URLEncoder.encode(myvar, "UTF-8"));
			HttpURLConnection conn1 = (HttpURLConnection) url1.openConnection();
			conn1.setDoOutput(true);
			conn1.setRequestMethod("POST");

			if (conn1.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn1.getResponseCode());
			} else {

				BufferedReader br1 = new BufferedReader(new InputStreamReader(
						(conn1.getInputStream())));

				// System.out.println("Output from Server .... \n");
				String output1;
				while ((output1 = br1.readLine()) != null) {
					System.out.println(output1);
				}

			}

			conn1.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
	}
}
