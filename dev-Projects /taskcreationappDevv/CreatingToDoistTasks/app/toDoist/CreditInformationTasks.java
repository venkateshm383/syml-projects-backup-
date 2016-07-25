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

public class CreditInformationTasks {

	ApplicantDocument doclist;
	static Logger log = LoggerFactory.getLogger(DocumentAnalyzerTasks.class);

	static CouchbaseClient client = null;
	// static String token = "19fb71b3edb93f05952f23e0b99120806ec7d224";
	static String token = "19fb71b3edb93f05952f23e0b99120806ec7d224";
	static String todoistUser = "Guy";

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
System.out.println("inside taskss .... \n"+taskName);
System.out.println("inside projectId .... \n"+projectId);
System.out.println("inside token .... \n"+token);

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
System.out.println("ouptut "+output1);
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
			 * log.info(input); OutputStream os = conn1.getOutputStream();
			 * os.write(input.getBytes()); os.flush();
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

	public static void taskUpdated1(String taskId, String date, String assignId)
			throws JSONException {

		UUID uid = UUID.randomUUID();
		UUID uid1 = UUID.randomUUID();

		// log.info("uid " + uid);
		// log.info("uid 2" + uid1);

		String myvar = "[{\"type\": \"item_update\"," + " \"uuid\": \"" + uid
				+ "\", \"args\": {\"id\": " + taskId + ", \"date_string\": \""
				+ date + "\",\"responsible_uid\":" + assignId
				+ ",\"priority\": 4}}]";

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

	public static void taskUpdated(String taskId, String date, String assignId)
			throws JSONException {
		System.out.println("inside tasks updated .... \n");

		UUID uid = UUID.randomUUID();
		UUID uid1 = UUID.randomUUID();

		// System.out.println("uid " + uid);
		// System.out.println("uid 2" + uid1);

		String myvar = "[{\"type\": \"item_update\"," + " \"uuid\": \"" + uid
				+ "\", \"args\": {\"id\": " + taskId + ",\"responsible_uid\":"
				+ assignId + ", \"date_string\": \"" + date
				+ "\",\"priority\": 4}}]";

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

	static String assignUser = null;

	public static String gettodoistUser(String assginUser) throws JSONException {
		CouchbaseClient cleClient = null;
		try {
			todoistUser = assginUser;
			if (todoistUser.trim().equalsIgnoreCase("Audrey")) {

				todoistUser = "Audrey";
				assignUser = "4287399";
			} else if (todoistUser.trim().equalsIgnoreCase("brenda@visdom.ca")) {

				todoistUser = "brenda@visdom.ca";
				assignUser = "5528089";

			} else {
				assignUser = "4287399";

			}
		} catch (Exception e) {

		}
		try {
		} catch (Exception e) {

		}

		return assignUser;

	}

	public static void createTasksCredit(JSONObject json) throws JSONException {
		Properties prop = getNotesFromConfig();
		String taskid = "0";

		String creditSucesTask = "";

		try {
			creditSucesTask = "Credit has been pulled for "
					+ json.getString("applicantName")
					+ ".  Please review their addresses list, "
					+ "incomes list and clean up any duplicated data sent by Equifax.";

		} catch (Exception e) {

		}
		String creditFailedtask = "";
		try {
			creditFailedtask = "We attempted to pull credit for "
					+ json.getString("applicantNamefailed")
					+ " however, the credit inquiry failed.  Please contact "
					+

					""
					+ json.getString("applicantNamefailed")
					+ " to verify the accuracy of their Birthhdate, Social Insurance Number and Address.";

		} catch (Exception e) {
			// User user=new
			// CouchBaseOperation().getTodoistUserEmail(todoistUser);
		}
		// token=user.getToken();
		String projectName = new Odoo().getReferralname(json.get("id")
				.toString());
		projectName += "_" + json.get("id").toString();
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
								+ json.get("id").toString());
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
							+ json.get("id").toString());
					client.shutdown(9000l, TimeUnit.MILLISECONDS);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		} else {

			try {
				client1 = new CouchBaseOperation().getConnectionToCouchBase();
				client1.deleteDesignDoc("dev_documenlistTask"
						+ json.get("id").toString());
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
		 */// System.out.println("size" + project.size());
		String assignUser = "";
		int numberOfUser = 0;
		for (Iterator iterator1 = project.iterator(); iterator1.hasNext();) {
			ProjectDetails projectDetails = (ProjectDetails) iterator1.next();

			++numberOfUser;
			String projectId = projectDetails.getProjectId();
			// token =projectDetails.getToken();
			System.out.println("projectid   -----  " + projectId);
			System.out.println("token   -----  " + token);
			System.out.println("assginuser   -----  "
					+ projectDetails.getAssignedUser());

			assignUser = gettodoistUser(projectDetails.getAssignedUser());
System.out.println("usser id "+assignUser);
			if (numberOfUser < 2) {

				if (json.get("sucess").toString().equalsIgnoreCase("true")) {

					taskid = createTasks(creditSucesTask, projectId.trim(), token.trim());

					  SimpleDateFormat formatDate = new
					  SimpleDateFormat("HH:mm a");
					  
					  Calendar cal = Calendar.getInstance();
					  cal.add(Calendar.MINUTE, 30); String newTime =
					  formatDate.format(cal.getTime()); taskUpdated(taskid,
					  "Today " + newTime,assignUser); /*createNotes(taskid,
					  mortgageTasknote, token);*/
					  
					/*  taskid = createTasks(mortgageTask1, projectId, token);
					  taskUpdated(taskid, "Today " + newTime,assignUser);
					  createNotes(taskid, mortgageTask1note, token);*/
					
				} else if(json.get("sucess").toString().equalsIgnoreCase("false")) {
					 SimpleDateFormat formatDate = new
							  SimpleDateFormat("HH:mm a");
							  
							  Calendar cal = Calendar.getInstance();
							  cal.add(Calendar.MINUTE, 30); String newTime =
							  formatDate.format(cal.getTime());
					taskid = createTasks(creditFailedtask, projectId, token);
					taskUpdated(taskid,
							  "Today " + newTime,assignUser);
				}

			}

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
			String assignUser = "";
			for (ViewRow row : viewRes) {
				++dataExsist;
				JSONObject jsonData = new JSONObject(row.getDocument()
						.toString());

				try {
					assignUser = jsonData.getString("assigned_userName");
				} catch (Exception e) {

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
JSONObject jsonSucessdata=new JSONObject();
jsonSucessdata.put("id", "439");
jsonSucessdata.put("sucess", "false");

jsonSucessdata.put("applicantNamefailed","test");		createTasksCredit(jsonSucessdata);
		
		// .createTasksForDocuments(docList);

	}

}
