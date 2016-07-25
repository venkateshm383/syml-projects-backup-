package toDoist;

import infrastracture.CouchBaseOperation;
import infrastracture.Odoo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import views.html.main;

import com.couchbase.client.CouchbaseClient;
import com.couchbase.client.protocol.views.DesignDocument;
import com.couchbase.client.protocol.views.Query;
import com.couchbase.client.protocol.views.View;
import com.couchbase.client.protocol.views.ViewDesign;
import com.couchbase.client.protocol.views.ViewResponse;
import com.couchbase.client.protocol.views.ViewRow;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import controllers.Application;

import dto.ApplicantDocument;
import dto.ProjectDetails;
import dto.User;

public class DocumentAnalyzerTasks extends Thread{
	
	ApplicantDocument doclist;
 	static Logger log = LoggerFactory.getLogger(DocumentAnalyzerTasks.class);

	public DocumentAnalyzerTasks(ApplicantDocument doclist) {
		this.doclist = doclist;
	}

	static CouchbaseClient client = null;
	// static String token = "19fb71b3edb93f05952f23e0b99120806ec7d224";
	static String token = "19fb71b3edb93f05952f23e0b99120806ec7d224";
	static String todoistUser = "Guy";

	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try{
		createTasksForDocuments(doclist);
		}catch(Exception e){
			
		}
	}
	/*
	 * public static void main(String[] args) throws JSONException {
	 * 
	 * // String project = createProject("sas",token);
	 * //createTasks("document list", project,token); // createLabels("nan"); //
	 * getLogin(); // shareProject(); }
	 */

	public static String getLogin(String token) throws JSONException {

		try {

			// URL url = new
			// URL("https://todoist.com/API/v6/login?email=assistant@visdom.ca&password=Visdom1234");
			URL url = new URL("https://todoist.com/API/v6/sync?token=" + token
					+ "&seq_no=0&seq_no_global=0&resource_types=[\"all\"]");

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			} else {

				BufferedReader br = new BufferedReader(new InputStreamReader(
						(conn.getInputStream())));

				log.info("Output from Server .... \n");
				String output;
				while ((output = br.readLine()) != null) {
					log.info(output);
					// JSONArray jsonArray=new JSONArray(output);

					JSONObject jsonObject = new JSONObject(output);
					token = jsonObject.getString("User");
					log.info("user details " + token);

				}

			}

			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
		return token;

	}

	public static void updateProject(String projectName, String token,
			String projectId) throws JSONException {

		UUID uid = UUID.randomUUID();
		UUID uid1 = UUID.randomUUID();
		log.info("inside update project .... \n");

		String myvar = "[{\"type\": \"project_update\"," + " \"uuid\": \""
				+ uid + "\"," + " \"args\": {\"id\": " + projectId
				+ ", \"name\": \"" + projectName + "\"}}]";

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
					/*
					 * JSONObject jsondata = new JSONObject(output1); JSONObject
					 * insideJsondata = (JSONObject) jsondata
					 * .get("TempIdMapping"); projectId =
					 * insideJsondata.get(uid.toString()).toString();
					 */
				}

			}
			createLabels(projectName, token);
			conn1.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}

	}

	public static String createTasks(String taskName, String projectId,
			String token) throws JSONException {
		String taskId = "0";
		UUID uid = UUID.randomUUID();
		UUID uid1 = UUID.randomUUID();
		log.info("inside taskss .... \n");

		// log.info("uid " + uid);
		// log.info("uid 2" + uid1);
		String myvar = "[" + "  {" + "    \"type\": \"item_add\","
				+ "    \"temp_id\": \"" + uid + "\"," + "    \"uuid\": \""
				+ uid1 + "\"," + "    \"args\": {" + "      \"content\": \""
				+ taskName + "\"," + "      \"project_id\": " + projectId + ""
				+ "    }" + "  }" + "]";

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

	public static void createLabels(String labelName, String token)
			throws JSONException {
		log.info("inside labless .... \n");

		UUID uid = UUID.randomUUID();
		UUID uid1 = UUID.randomUUID();

		String myvar = "[{\"type\": \"label_add\"," + " \"temp_id\": \"" + uid
				+ "\"," + " \"uuid\": \"" + uid1 + "\","
				+ " \"args\": {\"name\": \"" + labelName + "\"}}]";

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

				// log.info("Output from Server .... \n");
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

	public static void createNotes(String taskId, String note, String token) {

		UUID uid = UUID.randomUUID();
		UUID uid1 = UUID.randomUUID();
		log.info("inside notes .... \n");

		String myvar = "[{\"type\": \"note_add\", " + "\"temp_id\": \"" + uid
				+ "\"," + " \"uuid\": \"" + uid1 + "\", "
				+ "\"args\": {\"item_id\": " + taskId + ","
				+ " \"content\": \"" + note + "\"}}]";

		try {
			// log.info(URLEncoder.encode(myvar));

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
			 * log.info(input); OutputStream os =
			 * conn1.getOutputStream(); os.write(input.getBytes()); os.flush();
			 */
			if (conn1.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
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
	
public static void taskUpdated1(String taskId, String date,String assignId) throws JSONException {
		
		UUID uid = UUID.randomUUID();
		UUID uid1 = UUID.randomUUID();

		// log.info("uid " + uid);
		// log.info("uid 2" + uid1);

		String myvar = "[{\"type\": \"item_update\"," +
				" \"uuid\": \""+uid+"\", \"args\": {\"id\": "+taskId+", \"date_string\": \""+date+"\",\"responsible_uid\":"+assignId+",\"priority\": 4}}]";
		
		JSONArray jsonArray=new JSONArray();
		JSONObject obj=new JSONObject();
		JSONObject obj2=new JSONObject();
		obj2.put("id", taskId);
		obj2.put("date_string", date);
		obj.put("type", "item_update");
		obj.put("uuid", uid);
		obj.put("args", obj2);
		jsonArray.put(obj);

		try {
			// log.info(URLEncoder.encode(myvar));

			URL url1 = new URL("https://todoist.com/API/v6/sync?token=" +token
					+"&commands=" +URLEncoder.encode(myvar,"UTF-8"));
			log.info("");
			HttpURLConnection conn1 = (HttpURLConnection) url1.openConnection();
			conn1.setDoOutput(true);
			conn1.setRequestMethod("POST");

			if (conn1.getResponseCode() != 200) {
				log.info("Failed : HTTP error code : "
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

	
	

	public static void taskUpdated(String taskId, String date,String assignId)
			throws JSONException {
		System.out.println("inside tasks updated .... \n");

		UUID uid = UUID.randomUUID();
		UUID uid1 = UUID.randomUUID();

		// System.out.println("uid " + uid);
		// System.out.println("uid 2" + uid1);

		String myvar = "[{\"type\": \"item_update\"," + " \"uuid\": \"" + uid
				+ "\", \"args\": {\"id\": " + taskId + ",\"responsible_uid\":"+assignId+", \"date_string\": \""
				+ date + "\",\"priority\": 4}}]";

		try {
			System.out.println(date);

			URL url1 = new URL("https://todoist.com/API/v6/sync?token=" + token
					+ "&commands=" + URLEncoder.encode(myvar, "UTF-8"));
			System.out.println(url1);
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
					System.out.println("task updatedd");
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

	public static void createLabel(String label, String token)
			throws JSONException {

		UUID uid = UUID.randomUUID();
		UUID uid1 = UUID.randomUUID();

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
	
	static	String  assignUser=null;

	public static String gettodoistUser(String assginUser) throws JSONException {
		CouchbaseClient cleClient = null;
		try {
				todoistUser =assginUser; 
			if ( todoistUser.trim().equalsIgnoreCase("Audrey")) {
				
				todoistUser = "Audrey";
				assignUser="4287399";
		} else if (todoistUser.trim().equalsIgnoreCase("brenda@visdom.ca")) {

			todoistUser = "brenda@visdom.ca";
			assignUser="5528089";
		
			}else{
				assignUser="4287399";

			}
		} catch (Exception e) {
			
		}
		try{
		}catch(Exception e){
			
		}
		

		return assignUser;

	}


	public static void createTasksForDocuments(ApplicantDocument docList)
			throws JSONException {
		Properties prop = getNotesFromConfig();
		String mortgageTask1 = "Verify the spelling of the names, date of birth, SIN, and that the address is in the proper format. Make sure the data from the application has been retrieved in the proper areas.";
		String mortgageTask1note = " Review the file to make sure you are requesting all of the required documents (use the Documents Required Template). If there is a Bankruptcy, send them the email template Request for Bankruptcy Docs and call them to discuss the circumstances. If there is a collection, call them to discuss the Collection using the Collections Call script as a outline for the call. If their credit report has Slow Payment indications - R2 through R9, determine why that happened and send them the sample explanation email template for their completion. Send the correct template for the circumstances - e.g. Job Change, Moved, Divorced, etc.). Once the documents are received, they are entered into google docs.";
		String mortgageTask = "Follow up on email requesting supporting documents and get all documentation from client "
				+ docList.getFirstName() + " " + docList.lastName;
		String time = "30";
		String mortgageTasknote = "- Follow up call about the documentation email to confirm when we will receive supporting documents.\n - Set reminder date  to reflect clients commitment to getting the documentation sent to Visdom. \n- Follow up with a call & text  If documentation is not received by reminder due date.\n - If clients have not met commitment date after 2 attempts, try a different method and time of day.\n - Contact referral source with a text message if they have not met these deadlines.\n  - If there is still no response from the client after the referral source attempts to get the client to send documentation, try one last time to get the documentation. \n- In the event it is not received by the 3rd request or client commitment, call to advise referral source archiving this Opportunity is on hold until the client is ready.\n - If some documentation is received, send the client (Client Partial Documentation email template). \n- Send the Referral Source the Referral \n - Partial Documentation Template.";
		String taskid = "0";
		// User user=new CouchBaseOperation().getTodoistUserEmail(todoistUser);
		// token=user.getToken();
		String projectName = new Odoo().getReferralname(docList
				.getOpprtunityId());
		projectName += "_" + docList.getOpprtunityId();
		ArrayList<ProjectDetails> project = null;
		getProjectId(projectName);
		project = view(projectName);

		try {
			Thread.sleep(6000);

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getProjectId(projectName);
		project = view(projectName);
		if (project.size() == 0) {

			getProjectId(projectName);
			project = view(projectName);

			try {
				Thread.sleep(6000);

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (project.size() == 0) {
				getProjectId(projectName);
				project = view(projectName);
				try {
					Thread.sleep(6000);

				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (project.size() == 0) {
					getProjectId(projectName);
					project = view(projectName);
					try {
						Thread.sleep(6000);

					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {
					try {
						client1 = new CouchBaseOperation()
								.getConnectionToCouchBase();
						client1.deleteDesignDoc("dev_documenlistTask"
								+ docList.getOpprtunityId());
						client.shutdown(9000l, TimeUnit.MILLISECONDS);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}

			} else {
				try {
					client1 = new CouchBaseOperation()
							.getConnectionToCouchBase();
					client1.deleteDesignDoc("dev_documenlistTask"
							+ docList.getOpprtunityId());
					client.shutdown(9000l, TimeUnit.MILLISECONDS);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		} else {

			try {
				client1 = new CouchBaseOperation().getConnectionToCouchBase();
				client1.deleteDesignDoc("dev_documenlistTask"
						+ docList.getOpprtunityId());
				client.shutdown(9000l, TimeUnit.MILLISECONDS);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		/*
		 * ProjectDetails pro=new ProjectDetails();
		 * pro.setProjectId("145803486");
		 * pro.setToken("19fb71b3edb93f05952f23e0b99120806ec7d224");
		 * 
		 * project.add(pro); //=getProjectId(docList.getOpprtunityId());
		 *///System.out.println("size" + project.size());
String assignUser="";
		int numberOfUser = 0;
		for (Iterator iterator1 = project.iterator(); iterator1.hasNext();) {
			ProjectDetails projectDetails = (ProjectDetails) iterator1.next();

			++numberOfUser;
			String projectId =projectDetails.getProjectId();
			//token =projectDetails.getToken();
			System.out.println("projectid   -----  " + projectId);
			System.out.println("token   -----  " + token);
			System.out.println("assginuser   -----  " + projectDetails.getAssignedUser());

assignUser=gettodoistUser(projectDetails.getAssignedUser());
			/*
			 * updateProject( docList.getLendingGoal() + "_" +
			 * docList.getFirstName() + "_" + docList.getOpprtunityId(), token,
			 * projectId);
			 * 
			 * createLabel(docList.getLendingGoal() + "-" +
			 * docList.getFirstName() + "-" + docList.getOpprtunityId(), token);
			 */

			if (numberOfUser < 2) {

				taskid = createTasks(mortgageTask, projectId, token);

				SimpleDateFormat formatDate = new SimpleDateFormat("HH:mm a");

				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.MINUTE, 30);
				String newTime = formatDate.format(cal.getTime());
				taskUpdated(taskid, "Today " + newTime,assignUser);
				createNotes(taskid, mortgageTasknote, token);

				taskid = createTasks(mortgageTask1, projectId, token);
				taskUpdated(taskid, "Today " + newTime,assignUser);
				createNotes(taskid, mortgageTask1note, token);
				if (!docList.getDocuments().isEmpty()) {
					String taskId = "0";
					Set<String> data = docList.getDocuments();

					Iterator<String> iterator = data.iterator();
					while (iterator.hasNext()) {
						String docVal = iterator.next();

						taskId = createTasks(docList.getFirstName() + " "
								+ docList.getLastName() + "-" + docVal,
								projectId, token);
						taskUpdated1(taskId,  "+2",assignUser);

						addingNotesContent(taskId, docVal);

						try {
							String[] mortgageVal = docVal.split("\\(");
							switch (mortgageVal[0]) {
							case "Mortgage Statement":
								createNotes(taskId,
										prop.getProperty("mortgage"), token);
								break;

							case "Property Tax Assessment":
								createNotes(taskId,
										prop.getProperty("property"), token);
								break;

							default:
								break;
							}

						} catch (Exception e) {
							e.printStackTrace();

						}

					}
					taskId = createTasks(
							docList.getFirstName() + " "
									+ docList.getLastName()
									+ "-Confirmation of Taxes Paid", projectId,
							token);
					taskUpdated1(taskId,  "+2",assignUser);

					addingNotesContent(taskId, docList.getFirstName() + " "
							+ docList.getLastName()
							+ "-Confirmation of Taxes Paid");

				}
				if (!docList.getCo_documents().isEmpty()) {
					String taskId = "0";
					Set<String> data = docList.getCo_documents();

					Iterator<String> iterator = data.iterator();

					while (iterator.hasNext()) {
						String docVal = iterator.next();
						taskId = createTasks(docList.co_firstName + " "
								+ docList.co_lastName + "-" + docVal,
								projectId, token);
						taskUpdated1(taskId,  "+2",assignUser);

						addingNotesContent(taskId, docVal);

						try {
							String[] mortgageVal = docVal.split("\\(");
							switch (mortgageVal[0]) {
							case "Mortgage Statement":
								createNotes(taskId,
										prop.getProperty("mortgage"), token);
								break;

							case "Property Tax Assessment":
								createNotes(taskId,
										prop.getProperty("property"), token);
								break;

							default:
								break;
							}

						} catch (Exception e) {
							e.printStackTrace();

						}

					}
					taskId = createTasks(docList.co_firstName + " "
							+ docList.co_lastName
							+ "-Confirmation of Taxes Paid", projectId, token);
					taskUpdated1(taskId,  "+2",assignUser);

					addingNotesContent(taskId, docList.co_firstName + " "
							+ docList.co_lastName
							+ "-Confirmation of Taxes Paid");

				}
				if (docList.getDownPayments().size() != 0
						&& !docList.getDownPayments().isEmpty()) {
					Set<String> data = docList.getDownPayments();
					String taskId = "0";
					Iterator<String> iterator = data.iterator();
					System.out
							.println("------------doc list of downPayments------------------->"
									+ iterator.toString());

					while (iterator.hasNext()) {
						String docVal = iterator.next();

						taskId = createTasks(docVal, projectId, token);
						taskUpdated1(taskId,  "+2",assignUser);

						addingNotesContent(taskId, docVal);

					}
				}
				
				if (docList.getApplicantCreditDocuments().size() != 0
						&& !docList.getApplicantCreditDocuments().isEmpty()) {
					Set<String> data = docList.getApplicantCreditDocuments();
					String taskId = "0";
					Iterator<String> iterator = data.iterator();
					System.out
							.println("------------doc list of credit applicant docuemnts ------------------->"
									+ iterator.toString());

					while (iterator.hasNext()) {
						String docVal = iterator.next();

						taskId = createTasks(docList.firstName + " "
								+ docList.lastName+"_"+docVal, projectId, token);
						taskUpdated1(taskId,  "+2",assignUser);

						//addingNotesContent(taskId, docVal);

					}
				}
				
				if (docList.getCo_applicantCreditDocuments().size() != 0
						&& !docList.getCo_applicantCreditDocuments().isEmpty()) {
					Set<String> data = docList.getCo_applicantCreditDocuments();
					String taskId = "0";
					Iterator<String> iterator = data.iterator();
					System.out
							.println("------------doc list of credit co applicant docuemnts ------------------->"
									+ iterator.toString());

					while (iterator.hasNext()) {
						String docVal = iterator.next();

						taskId = createTasks(docList.co_firstName + " "
								+ docList.co_lastName+"_"+docVal, projectId, token);
						taskUpdated1(taskId,  "+2",assignUser);

						//addingNotesContent(taskId, docVal);

					}
				}


				if (docList.getPropertyDocments().size() != 0
						&& !docList.getPropertyDocments().isEmpty()) {
					Set<String> data = docList.getPropertyDocments();
					String taskId = "0";
					Iterator<String> iterator = data.iterator();

					while (iterator.hasNext()) {

						String docVal = iterator.next();

						taskId = createTasks(docVal, projectId, token);
						taskUpdated1(taskId,  "+2",assignUser);

						addingNotesContent(taskId, docVal);

					}
				}
			}

	}

	}

	public static void addingNotesContent(String taskId, String documentName) {
		System.out.println("note content");
		Properties prop = getNotesFromConfig();
		try {
			Calendar year1 = Calendar.getInstance();
			int currentMonth = year1.get(Calendar.MONTH) + 1;

			String currentYearNoa = "";
			String lastYearNoa = "";
			String lastSecondYesrNoa = "";
			if (currentMonth >= 3 && currentMonth < 5) {

				int startYear = year1.get(Calendar.YEAR) - 1;

				currentYearNoa = startYear + " T4";

				int priorYear = startYear - 1;
				lastYearNoa = priorYear + " Notice of Assessment";

				int priorYear2 = startYear - 2;
				lastSecondYesrNoa = priorYear2 + " Notice of Assessment";

			} else if (currentMonth >= 5) {
				int startYear = year1.get(Calendar.YEAR) - 1;

				lastYearNoa = startYear + " Notice of Assessment";
				int priorYear = startYear - 1;
				lastSecondYesrNoa = priorYear + " Notice of Assessment";

			}

			if (documentName.equalsIgnoreCase(currentYearNoa)) {
				createNotes(taskId, prop.getProperty("noa"), token);

			}
			if (documentName.equalsIgnoreCase(lastYearNoa)) {
				createNotes(taskId, prop.getProperty("noa"), token);
			}

			if (documentName.equalsIgnoreCase(lastSecondYesrNoa)) {
				createNotes(taskId, prop.getProperty("noa"), token);
			}

			switch (documentName) {
			case "December 31st Pay stub showing Year to date income":
				createNotes(taskId, prop.getProperty("noaDecember"), token);

				break;

			case "Letter of Employment":
				createNotes(taskId, prop.getProperty("letterofemployeement"),
						token);

				break;

			case "Applicant-Confirmation of Taxes Paid":
				createNotes(taskId, prop.getProperty("tax"), token);

				break;
			case "Co-Applicant-Confirmation of Taxes Paid":
				createNotes(taskId, prop.getProperty("tax"), token);

				break;

			case "Paystub":
				createNotes(taskId, prop.getProperty("paystub"), token);

				break;

			case "Divorce/Separation Agreement":
				createNotes(taskId, prop.getProperty("divorce"), token);

				break;
			case "Child Tax Credit":
				createNotes(taskId, prop.getProperty("childTax"), token);

				break;
			case "90 Days history bank statements":
				createNotes(taskId, prop.getProperty("bank"), token);

				break;
			case "90 Days history Investment":
				createNotes(taskId, prop.getProperty("rrsp"), token);

				break;

			case "Gift Letter":
				createNotes(taskId, prop.getProperty("giftletter"), token);

				break;

			case "Sale MLS Listing":
				createNotes(taskId, prop.getProperty("mls"), token);

				break;
			case "Offer to Sale of Existing":
				createNotes(taskId, prop.getProperty("purchase"), token);

				break;
			default:
				break;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static Properties getNotesFromConfig() {
		Properties prop = new Properties();

		try {

			// getting get notes content
			prop.load(CouchBaseOperation.class.getClassLoader()
					.getResourceAsStream("taskconfig.properties"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return prop;

	}

	static CouchbaseClient client1 = null;
	static int dataExsist = 0;
	static ArrayList<ProjectDetails> list = new ArrayList<ProjectDetails>();

	public static ArrayList<ProjectDetails> getProjectId(String projectName) {
		System.out.println("data exisis" + dataExsist);
		String projectId = "0";
		try {
			client1 = new CouchBaseOperation().getConnectionToCouchBase();
			DesignDocument designdoc = getDesignDocument("dev_documenlistTask"
					+ projectName);
			boolean found = false;

			// 5. get the views and check the specified in code for existence
			for (ViewDesign view : designdoc.getViews()) {
				if (view.getMap() == "documenlistTask" + projectName) {
					found = true;
					break;
				}
			}

			// 6. If not found then create view inside document
			if (!found) {
				ViewDesign view = new ViewDesign("documenlistTask"
						+ projectName, "function (doc, meta) {\n"
						+ "if(doc.ProjectName_==\"" + projectName.trim()
						+ "\")"

						+ "{emit(meta.id,null)}\n" +

						"}");

				designdoc.getViews().add(view);
				client1.createDesignDoc(designdoc);
			}
			client1.shutdown(9000l, TimeUnit.MILLISECONDS);

			// 7. close the connection with couchbase

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// list=view(projectName);
		return list;

	}

	public static ArrayList<ProjectDetails> view(String projectName) {

		String projectId = "0";
		ArrayList<ProjectDetails> list1 = new ArrayList<ProjectDetails>();
		try {
			CouchbaseClient client2 = new CouchBaseOperation()
					.getConnectionToCouchBase();
			System.setProperty("viewmode", "development");
			// get the view
			View view = client2.getView("dev_documenlistTask" + projectName,
					"documenlistTask" + projectName);
			// create Query.
			System.out.println(view.getViewName());
			Query query = new Query();
			query.setIncludeDocs(true).setLimit(1);

			// get ViewResponse
			ViewResponse viewRes = client2.query(view, query);
			// Iterate over the ViewResponse
			String assignUser="";
			for (ViewRow row : viewRes) {
				++dataExsist;
				JSONObject jsonData = new JSONObject(row.getDocument()
						.toString());
				
				try{
					assignUser=jsonData.getString("assigned_userName");
				}catch(Exception e){
					
				}
				JSONObject josnInsidedata = (JSONObject) jsonData
						.get("event_data");
				System.out.println("datat " + josnInsidedata);
				projectId = josnInsidedata.get("id").toString();
				try {
					User user = new CouchBaseOperation()
							.getTodoistUserEmail(jsonData.get("username")
									.toString());
					token = user.getToken();
				} catch (Exception e) {

				}
				System.out.println("user  " + projectId);
				ProjectDetails project = new ProjectDetails();
				project.setProjectId(projectId);
				
				project.setAssignedUser(assignUser);
				project.setToken(token);
				list1.add(project);
				System.out.println("lsit " + list1.size());
			}
			try {
				client2.shutdown(9000l, TimeUnit.MILLISECONDS);
			} catch (Exception e) {
				e.printStackTrace();
			}
			/*
			 * if(list1.isEmpty()){
			 * 
			 * view(projectName); //} }
			 */

			System.out.println("data exisst " + dataExsist);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("error is : " + e);
		}
		System.out.println("lsit --------------->" + list1.size());

		return list1;
	}

	private static DesignDocument getDesignDocument(String name) {
		try {

			System.out.println("Design document with " + name + " exist ? "
					+ client1.getDesignDoc(name));

			return client1.getDesignDoc(name);
		} catch (Exception e) {
			return new DesignDocument(name);
		}
	}

	public static void main(String[] args) throws JsonParseException,
			JsonMappingException, IOException, JSONException {

		// client1 = new CouchBaseOperation().getConnectionToCouchBase();
		// System.out.println("data"+ client1.get("Applicant_");
		/*
		 * getProjectId("4328"); ArrayList<ProjectDetails> list1=view("4328");
		 * System.out.println("api"+list1.size());
		 * 
		 * 
		 * System.out.println("hellloooooooooooooooooooo");
		 * 
		 * 
		 * try { Thread.sleep(6000);
		 * 
		 * } catch (InterruptedException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } getProjectId("4328");
		 * ArrayList<ProjectDetails> list=view("4328");
		 * 
		 * System.out.println("api"+list.size());
		 * 
		 * for (Iterator iterator1 = list.iterator(); iterator1.hasNext();) {
		 * ProjectDetails projectDetails = (ProjectDetails) iterator1.next();
		 * 
		 * 
		 * String projectId = projectDetails.getProjectId();
		 * System.out.println(projectId); }
		 */

		// main("4325");
		String myvar = "{\"firstName\":\"vikash\",\"lastName\":\"lastname\",\"emailAddress\":\"venkatesh.m@bizruntime.com\",\"documents\":[\"Copy of Photo ID (Driver's Licence or Passport)\",\"2013 Notice of Assessment\",\"Letter of Employment\",\"2014 Notice of Assessment\",\"Paystub\"],\"co_firstName\":null,\"co_lastName\":null,\"co_emailAddress\":null,\"co_documents\":[],\"propertyDocments\":[],\"downPayments\":[],\"lendingGoal\":\"Pre-Approval\",\"opprtunityId\":\"4328\"}";

		ObjectMapper mapper = new ObjectMapper();
		dto.ApplicantDocument docList = mapper.readValue(myvar,
				dto.ApplicantDocument.class);
		new DocumentAnalyzerTasks(docList).start();
		//.createTasksForDocuments(docList);

	}

}