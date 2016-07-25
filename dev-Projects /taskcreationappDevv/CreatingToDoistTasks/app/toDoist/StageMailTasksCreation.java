package toDoist;

import infrastracture.CouchBaseOperation;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.couchbase.client.CouchbaseClient;
import com.couchbase.client.protocol.views.DesignDocument;
import com.couchbase.client.protocol.views.Query;
import com.couchbase.client.protocol.views.View;
import com.couchbase.client.protocol.views.ViewDesign;
import com.couchbase.client.protocol.views.ViewResponse;
import com.couchbase.client.protocol.views.ViewRow;

import dto.ProjectDetails;
import dto.User;

public class StageMailTasksCreation {
 	static Logger log = LoggerFactory.getLogger(StageMailTasksCreation.class);

	//static String token = "19fb71b3edb93f05952f23e0b99120806ec7d224";
	static String todoistUser="Guy";
	static String token = "19fb71b3edb93f05952f23e0b99120806ec7d224";
	public static void main(String  args[]) throws JSONException {
	creatStageMailTasks("111", 24,"lender");
	//taskUpdated("4646896", "+28");
//	test();
	}

	

	public static String createTasks(String taskName, String projectId,
			String token) throws JSONException {
		String taskId = "0";
		UUID uid = UUID.randomUUID();
		UUID uid1 = UUID.randomUUID();
		log.debug("inside creating tasks"+projectId);

		// log.Debug("uid " + uid);
		// log.Debug("uid 2" + uid1);
		String myvar = "[" + "  {" + "    \"type\": \"item_add\","
				+ "    \"temp_id\": \"" + uid + "\"," + "    \"uuid\": \""
				+ uid1 + "\"," + "    \"args\": {" + "      \"content\": \""
				+ taskName + "\"," + "      \"project_id\": " + projectId + ""
				+ "    }" + "  }" + "]";

		try {
			// log.Debug(URLEncoder.encode(myvar));

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
					log.info(output1);

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

	public static void createNotes(String taskId, String note, String token) {
log.info(taskId+"niote"+note );
		UUID uid = UUID.randomUUID();
		UUID uid1 = UUID.randomUUID();

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
					+ "&commands=" + URLEncoder.encode(myvar,"UTF-8"));
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

	public static void taskUpdated(String taskId, String date,String assignId) throws JSONException {
		
		UUID uid = UUID.randomUUID();
		UUID uid1 = UUID.randomUUID();

		// log.info("uid " + uid);
		// log.info("uid 2" + uid1);

		String myvar = "[{\"type\": \"item_update\"," +
				" \"uuid\": \""+uid+"\", \"args\": {\"id\": "+taskId+", \"date_string\": \""+date+"\",\"responsible_uid\":"+assignId+",\"priority\": 4}}]";
		
		

		try {
			// log.info(URLEncoder.encode(myvar));

			URL url1 = new URL("https://todoist.com/API/v6/sync?token=" +token
					+"&commands=" +URLEncoder.encode(myvar,"UTF-8"));
		
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

	}public static void createLabel(String label, String token)
			throws JSONException {

		UUID uid = UUID.randomUUID();
		UUID uid1 = UUID.randomUUID();

		log.info("uid " + uid);
		log.info("uid 2" + uid1);

		String myvar = "[{\"type\": \"label_add\"," + " \"temp_id\": \"" + uid
				+ "\"," + " \"uuid\": \"" + uid1 + "\", "
				+ "\"args\": {\"name\": \"" + label + "\"}}]";

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
	public  static void creatStageMailTasks(String opprunityId, int stage,String lenderName)
			throws JSONException {
		String assignuser="";
		ArrayList<ProjectDetails> projectDetailsList=null;
		getProjectId(opprunityId);
		projectDetailsList=	view(opprunityId);
		try {
			Thread.sleep(6000);
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(projectDetailsList.size()==0){
		getProjectId(opprunityId);
		projectDetailsList=	view(opprunityId);
		}
		if(projectDetailsList.size()==0){
		 getProjectId(opprunityId);
		 projectDetailsList=view(opprunityId);
		 

			try {
				Thread.sleep(6000);
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(projectDetailsList.size()==0){
			 getProjectId(opprunityId);
			 projectDetailsList=view(opprunityId);
			}else{
				try{
					client1 = new CouchBaseOperation().getConnectionToCouchBase();
					client1.deleteDesignDoc("dev_projectid_"+opprunityId);
					client1.shutdown(9000l, TimeUnit.MILLISECONDS);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}else{
			try{
				client1 = new CouchBaseOperation().getConnectionToCouchBase();
				client1.deleteDesignDoc("dev_projectid_"+opprunityId);
				client1.shutdown(9000l, TimeUnit.MILLISECONDS);
			}catch(Exception e){
				e.printStackTrace();
			}	
		}
		
		int numberOfUsers=0;
		for (Iterator iterator = projectDetailsList.iterator(); iterator
				.hasNext();) {
			ProjectDetails projectDetails = (ProjectDetails) iterator.next();
		++numberOfUsers;
		
	
		if(numberOfUsers<2){
		String projectId =projectDetails.getProjectId();
		log.info("inside stage mail---------------project id"+projectId);
		//User user=new CouchBaseOperation().getTodoistUserEmail(todoistUser);
		//token=projectDetails.getToken();
		Properties prop =getNotesFromConfig();
		int time = 0;
		
		try{
			assignuser=gettodoistUser(projectDetails.getAssignedUser());
		}catch(Exception e){
			
		}

		SimpleDateFormat formatDate = new SimpleDateFormat("HH:mm a");

		Calendar cal = Calendar.getInstance();
		String stageName = null;
		String taskId = "0";
		String newTime ="";
		switch (stage) {
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

		case 16:
			stageName = "Credit";
		
			time = new Integer(prop.getProperty("creditsatgetime").trim());
			cal.add(Calendar.MINUTE, time);
			 newTime = formatDate.format(cal.getTime());

			taskId = createTasks(prop.getProperty("creditstagetask1"),
					projectId, token);
			log.info("Today " + newTime);
			taskUpdated(taskId, "Today " + newTime,assignuser);
			createNotes(taskId, prop.getProperty("creditstagenote"), token);
			taskId = createTasks(prop.getProperty("creditstagetask2"),
					projectId, token);
			taskUpdated(taskId, "Today " + newTime,assignuser);
			createNotes(taskId, prop.getProperty("creditstagenote2"), token);
			taskId = createTasks(prop.getProperty("creditstagetask3"),
					projectId, token);
			taskUpdated(taskId, "Today " + newTime,assignuser);

			break;
		case 22:
			stageName = "Lender Submission";
			
			String lenderTasksUpdate1="Update client on status of the deal using the Client Deal Status Email Template";
			String lenderTasksUpdate2="Update realtor on status of the deal using the Realtor Deal Status Email Template";
			taskId = createTasks(lenderTasksUpdate1,
					projectId, token);
			taskUpdated(taskId,  "+ "+24 +"Hours",assignuser);
			taskId = createTasks(lenderTasksUpdate2,
					projectId, token);
			taskUpdated(taskId,  "+ "+24 +"Hours",assignuser);
			taskId = createTasks(prop.getProperty("onlcicklendersubmissionstage"),
					projectId, token);
			taskUpdated(taskId,  "+ "+24 +"Hours",assignuser);
			taskId = createTasks(prop.getProperty("onlcicklendersubmissionstage1"),
					projectId, token);
			taskUpdated(taskId,  "+ "+24+"Hours",assignuser);
			taskId = createTasks(prop.getProperty("onlcicklendersubmissionstage2"),
					projectId, token);
			taskUpdated(taskId,  "+ "+24+"Hours",assignuser);

			String lenderTask="Broker to communicate with Realtor around status of the deal using the Realtor Deal Status Email Template. ";
				String lendingGoal="";
			//	lendingGoal=new OpenERPDAO().getLendingGoal(opprunityId);
				if(lendingGoal.equalsIgnoreCase("Purhase")||lendingGoal.equalsIgnoreCase("pre-approval") ){
					
					taskId = createTasks(lenderTask,
							projectId, token);
					taskUpdated(taskId,  "+ "+24+"Hours",assignuser);		
				}
			
			taskId = createTasks(prop.getProperty("onlcicklendersubmissionstage3"),
					projectId, token);
			taskUpdated(taskId,  "+ "+32+"Hours",assignuser);
			taskId = createTasks(prop.getProperty("onlcicklendersubmissionstage4"),
					projectId, token);
			taskUpdated(taskId,  "+ "+32+"Hours",assignuser);

			break;

		case 23:
			String commitmentsatgeupdate1="Gather all documents (including docs required to satisfy conditions) and upload them to google docs";
			String commitmentsatgeupdate2="Let Audrey know that all documents are uploaded to google docs";
			String commitmentsatgeupdate3="Notify Client by call and follow up email of any outstanding conditions";
			String commitmentsatgeupdate4="Notify Realtor by call and follow up email of any outstanding documents or conditions";
			String commitmentsatgeupdate5="Call Client to confirm Financing Letter was sent and received";
			String commitmentsatgeupdate6="Call Realtor to confirm Financing Letter was sent and received";
			String commitmentsatgeupdate7="Send File Complete email to Client using Client-file complete template";
			String commitmentsatgeupdate8="Send File Complete email to Realtor using Realtor-file complete template";
			String commitmentsatgeupdate9="Send File Complete email to Referral Source using Referral-file complete template";

			
			stageName = "Commitment";
			String onclickcommitmentStage2Note="- Change the Due date and Reminder date of this task to reflect the documentation commitment date provided to you by the client. \n- If documentation is not received by deadline, follow up with a text message or phone call. \n- For this second attempt, try a different method and time of day than the initial attempt. \n- If they have not met the documentation commitment date 2 times, involve the referral source with a text message. If there is still no response from the client after the referral source attempts to get the client to send documentation, try one last time yourself to get the documentation. \n- In the event it is not received by the 3rd documentation commitment date they provided, discuss with the referral source archiving the Opportunity until the client is ready. \n- If documentation received is partial, send the client the Client Partial Documentation email template. Also send the Referral Source the Referral - Partial Documentation Template)";	
			time = new Integer(prop.getProperty("onclickcommitmentStage12time").trim());
			cal.add(Calendar.MINUTE, time);
			 newTime = formatDate.format(cal.getTime());
			taskId = createTasks(prop.getProperty("onclickcommitmentStage1"),
					projectId, token);
			taskUpdated(taskId,  "Today " + newTime,assignuser);
			
			taskId = createTasks(commitmentsatgeupdate1,
					projectId, token);
			taskUpdated(taskId,  "Today " + newTime,assignuser);
			taskId = createTasks(commitmentsatgeupdate2,
					projectId, token);
			taskUpdated(taskId,  "Today " + newTime,assignuser);
			taskId = createTasks(commitmentsatgeupdate3,
					projectId, token);
			taskUpdated(taskId,  "Today " + newTime,assignuser);
			taskId = createTasks(commitmentsatgeupdate4,
					projectId, token);
			taskUpdated(taskId,  "Today " + newTime,assignuser);
			taskId = createTasks(commitmentsatgeupdate5,
					projectId, token);
			taskUpdated(taskId,  "Today " + newTime,assignuser);
			taskId = createTasks(commitmentsatgeupdate6,
					projectId, token);
			taskUpdated(taskId,  "Today " + newTime,assignuser);
			taskId = createTasks(commitmentsatgeupdate7,
					projectId, token);
			taskUpdated(taskId,  "Today " + newTime,assignuser);
			taskId = createTasks(commitmentsatgeupdate8,
					projectId, token);
			taskUpdated(taskId,  "Today " + newTime,assignuser);
			taskId = createTasks(commitmentsatgeupdate9,
					projectId, token);
			taskUpdated(taskId,  "Today " + newTime,assignuser);
			
			taskId = createTasks(prop.getProperty("onclickcommitmentStage2"),
					projectId, token);
			createNotes(taskId, onclickcommitmentStage2Note, token);

			taskUpdated(taskId,  "Today " + newTime,assignuser);
			//time = new Integer(prop.getProperty("onclickcommitmentStagetime"));

			taskId = createTasks(prop.getProperty("onclickcommitmentStage3"),
					projectId, token);
			taskUpdated(taskId,  "+24 Hours",assignuser);
			
			taskId = createTasks(prop.getProperty("onclickcommitmentStage4"),
					projectId, token);
			taskUpdated(taskId,  "+24 Hours",assignuser);
			createNotes(taskId, prop.getProperty("onclickcommitmentStage4Note"), token);
			
			taskId = createTasks(prop.getProperty("onclickcommitmentStage5"),
					projectId, token);
			taskUpdated(taskId,  "+24 Hours",assignuser);
			String onclickcommitmentStage5="Communicate to underwriter that this has been complete.";
			createNotes(taskId, onclickcommitmentStage5, token);

			
			taskId = createTasks(prop.getProperty("onclickcommitmentStage6"),
					projectId, token);
			taskUpdated(taskId,  "+24 Hours",assignuser);
		
			
			/*String onnclickcommitMentStage="Gather all documents (including docs required to satisfy conditions) and upload them to google docs. ";
			String onnclickcommitMentStageNote="Communicate to underwriter that this has been complete.";
			taskId = createTasks(onnclickcommitMentStage,
					projectId, token);
			taskUpdated(taskId, newTime);
			createNotes(taskId, onnclickcommitMentStageNote, token);*/

			
			taskId = createTasks(prop.getProperty("onclickcommitmentStage7"),
					projectId, token);
			taskUpdated(taskId,  "+48 Hours",assignuser);
			taskId = createTasks(prop.getProperty("onclickcommitmentStage8"),
					projectId, token);
			taskUpdated(taskId,  "+48 Hours",assignuser);
			taskId = createTasks(prop.getProperty("onclickcommitmentStage9"),
					projectId, token);
			
			createNotes(taskId, prop.getProperty("onclickcommitmentStage9Note"), token);

			taskUpdated(taskId,  "+48 Hours",assignuser);	taskId = createTasks(prop.getProperty("onclickcommitmentStage10"),
					projectId, token);
			taskUpdated(taskId,  "+48 Hours",assignuser);
			/*	taskId = createTasks(prop.getProperty("onclickcommitmentStage11"),
					projectId, token);
			taskUpdated(taskId,  "+48 Hours");
			taskId = createTasks(prop.getProperty("onclickcommitmentStage12"),
					projectId, token);
			taskUpdated(taskId,  "+48 Hours");
			taskId = createTasks(prop.getProperty("onclickcommitmentStage13"),
					projectId, token);
			taskUpdated(taskId,  "+48 Hours");
			taskId = createTasks(prop.getProperty("onclickcommitmentStage14"),
					projectId, token);
			taskUpdated(taskId,  "+48 Hours");
			taskId = createTasks(prop.getProperty("onclickcommitmentStage141"),
					projectId, token);
			taskUpdated(taskId,  "+48 Hours");
			taskId = createTasks(prop.getProperty("onclickcommitmentStage142"),
					projectId, token);
			taskUpdated(taskId,  "+48 Hours");
			taskId = createTasks(prop.getProperty("onclickcommitmentStage15"),
					projectId, token);
			taskUpdated(taskId,  "+48 Hours");
			
			taskId = createTasks(prop.getProperty("onclickcommitmentStage16"),
					projectId, token);
			taskUpdated(taskId,  "+48 Hours");
			
			taskId = createTasks(prop.getProperty("onclickcommitmentStage17"),
					projectId, token);
			taskUpdated(taskId,  "+48 Hours");*/
			taskId = createTasks(prop.getProperty("onclickcommitmentStage18"),
					projectId, token);
			taskUpdated(taskId,  "+48 Hours",assignuser);



			break;
		case 24:
			stageName = "Compensation";
			
			cal.add(Calendar.MINUTE, time);
			 newTime = formatDate.format(cal.getTime());
			taskId = createTasks(prop.getProperty("onclickcompensationstage1"),
					projectId, token);
			taskUpdated(taskId,  "Today " + newTime,assignuser);
			createNotes(taskId, prop.getProperty("onclickcompensationstage1Note"), token);
			taskId = createTasks(prop.getProperty("onclickcompensationstage2"),
					projectId, token);
			taskUpdated(taskId,  "Today " + newTime,assignuser);
			taskId = createTasks(prop.getProperty("onclickcompensationstage21"),
					projectId, token);
			taskUpdated(taskId,  "Today " + newTime,assignuser);


			
			taskId = createTasks(prop.getProperty("onclickcompensationstage3"),
					projectId, token);
			taskUpdated(taskId,  "Today " + newTime,assignuser);

			
			break;
		case 20:
			stageName = "Proposal";
			String onproposalstage3Note="Follow up Call to client till a product is selected";
			time = new Integer(prop.getProperty("onproposalstagetime").trim());
			cal.add(Calendar.MINUTE, time);
			 newTime = formatDate.format(cal.getTime());
			taskId = createTasks(prop.getProperty("onproposalstage1"),
					projectId, token);
			
			taskUpdated(taskId,  "Today " + newTime,assignuser);
			String onproposalstageNote1="If they are unable to review it right then, set up another time to review the proposal";
			createNotes(taskId, onproposalstageNote1, token);
			
			taskId = createTasks(prop.getProperty("onproposalstage2"),
					projectId, token);
			taskUpdated(taskId,  "Today " + newTime,assignuser);
			
			taskId = createTasks(prop.getProperty("onproposalstage3"),
					projectId, token);
			taskUpdated(taskId,  "Today " + newTime,assignuser);
			createNotes(taskId, onproposalstage3Note, token);
			
			taskId = createTasks(prop.getProperty("onproposalstage4"),
					projectId, token);
			taskUpdated(taskId,  "Today " + newTime,assignuser);

			break;
		case 25:
			stageName = "Paid";

			break;
		/*case 19:
			stageName = "Lost";

			break;*/

		case 18:

			stageName = "Awaiting Docs";
			String awaitingDoc2="Click the All Products Stage to Advance the Opportunity";
		
			time = new Integer(prop.getProperty("onawaitingstagetime").trim());
			

			taskId = createTasks(prop.getProperty("onawaitingocumentsatge"),
					projectId, token);
			taskUpdated(taskId,  "+"+time+"Hours",assignuser);
			taskId = createTasks(awaitingDoc2,
					projectId, token);
			taskUpdated(taskId,  "+"+time+"Hours",assignuser);
			
			
			break;
	/*	case 21:

			stageName = "Task";
			break;*/

		case 19:
String prodcutTask2="Review the Opportunity  which has returned 0 products";
String productTaskNote2="Check Tasks tab on the opportunity to double check all the proper fields have been completed. Then click all products. ";
String prodcutTask3="Once there are products, review the products to make sure they apply to the opportunity. ie. eliminate cashback options on refinances, and eliminate options from the lender they are currently using if refinancing. ";			
String productTask3Note="Eliminate cashback options on refinances, and eliminate options from the lender they are currently using if refinancing";
stageName = "All Product";
			time = new Integer(prop.getProperty("onproductstagetime"));
			cal.add(Calendar.MINUTE, time);
			 newTime = formatDate.format(cal.getTime());
			
			taskId = createTasks(prop.getProperty("onproductstage"),
					projectId, token);
			createNotes(taskId, prop.getProperty("onproductstagenote"), token);
			taskUpdated(taskId,  "Today " + newTime,assignuser);
			JSONObject jsonData=null;
			try{
				
				jsonData=(JSONObject) new CouchBaseOperation().getConnectionToCouchBase().get("OppertunityRecomendtion"+opprunityId);
			}catch(Exception e){
				
			}
			
			if(jsonData!=null&&jsonData.length()!=0){
				
			}else{

				taskId = createTasks(prodcutTask2,
						projectId, token);
				taskUpdated(taskId,  "Today " + newTime,assignuser);
				createNotes(taskId, productTaskNote2, token);

			}taskId = createTasks(prodcutTask3,
					projectId, token);
			taskUpdated(taskId,  "Today " + newTime,assignuser);
			taskId = createTasks("Click on Proposal Stage",
					projectId, token);
		String productTaskNote4="If they are unable to review it right then, set up another time to review the proposal";

			taskUpdated(taskId,  "Today " + newTime,assignuser);
			createNotes(taskId, productTaskNote4, token);

			

			break;
		case 21:
			//String postSelectStage="Notice to underwriter a product has been selected to be sent out to the lender.";
			//String postSelectStage1="Communicate with Underwriter if clients want to payoff any liabilities prior to closing or from proceeds";
			String psotSelectionSatge5="Send notice to Underwriter that a product has been selected and needs to be sent to the lender";
			String psotSelectionSatgeNote5="Communicate with Underwriter if clients want to payoff any liabilities from proceeds prior to closing.";
			stageName = "Post Selection";
			time = new Integer(prop.getProperty("onclickpostselectionstagetime").trim());
			
		
			cal.add(Calendar.MINUTE, time);
			 newTime = formatDate.format(cal.getTime());
			 
			 taskId = createTasks(psotSelectionSatge5,
						projectId, token);
				taskUpdated(taskId,  "Today " + newTime,assignuser);
				createNotes(taskId, psotSelectionSatgeNote5, token);
		/*	 taskId = createTasks(postSelectStage,
						projectId, token);
			 
			 taskId = createTasks(postSelectStage1,
						projectId, token);*/
			taskId = createTasks(prop.getProperty("onclickpostselectionstage"),
					projectId, token);
			taskUpdated(taskId,  "Today " + newTime,assignuser);
			taskId = createTasks(prop.getProperty("onclickpostselectionstage1"),
					projectId, token);
			taskUpdated(taskId,  "Today " + newTime,assignuser);
			taskId = createTasks(prop.getProperty("onclickpostselectionstage2"),
					projectId, token);
			taskUpdated(taskId,  "Today " + newTime,assignuser);
			taskId = createTasks(prop.getProperty("onclickpostselectionstage3"),
					projectId, token);
			taskUpdated(taskId,  "Today " + newTime,assignuser);
			
			taskId = createTasks(prop.getProperty("onclickpostselectionstage4"),
					projectId, token);
			taskUpdated(taskId,  "Today " + newTime,assignuser);
			break;

		default:
			stageName = "Pending Application";

			break;
		}
		
		}}
	}

	public static Properties getNotesFromConfig() {
		Properties prop = new Properties();

		try {

			// getting get notes content
			prop.load(StageMailTasksCreation.class.getClassLoader()
					.getResourceAsStream("stagemailtaskconfig.properties"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return prop;

	}
	static	CouchbaseClient client1=null;

	public static void  getProjectId(String opprunityID){
		String projectId="0";
		try	{	
			client1 = new CouchBaseOperation().getConnectionToCouchBase();
			DesignDocument designdoc=getDesignDocument("dev_projectid_"+opprunityID);
	        boolean found = false;
	        
	        //5. get the views and check the specified in code for existence
	        for(ViewDesign view:designdoc.getViews()){
	        	if(view.getMap()=="projectid_"+opprunityID){
	        	found=true;
	        	break;
	        	}
	        }
	        
	        //6. If not found then create view inside document
	        if(!found){
	        	ViewDesign view=new ViewDesign("projectid_"+opprunityID,"function (doc, meta) {\n" +
	        			"if(doc.crmId=="+opprunityID+" )\n" +
						"{emit(meta.id,null)}\n" +
				  
	                    "}");
	        	
	        	
	        	
	        	designdoc.getViews().add(view);
	        	client1.createDesignDoc(designdoc);
	        }
			
			
			//7. close the connection with couchbase
	      
			

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

			
		
		
	}
	
	
	
	public static ArrayList<ProjectDetails> view(String opprunityID){
		ArrayList<ProjectDetails> list1 = new ArrayList<ProjectDetails>();
String projectId="0";
		try {
			// get the view
			client1 = new CouchBaseOperation().getConnectionToCouchBase();

			View view = client1.getView("dev_projectid_"+opprunityID, "projectid_"+opprunityID);

			// create Query.
			Query query = new Query();
			query.setIncludeDocs(true).setLimit(1);

			// get ViewResponse
			ViewResponse viewRes = client1.query(view, query);

			// Iterate over the ViewResponse
			String assginUser="";
			for (ViewRow row : viewRes) {
				
				
			

				 JSONObject jsonData =new JSONObject(row.getDocument().toString());
				 
				 try{
					 assginUser=jsonData.getString("assigned_userName");
					}catch(Exception e){
						
					}
				 JSONObject josnInsidedata=(JSONObject) jsonData.get("event_data");
				 log.info("datat "+josnInsidedata);
				 projectId=josnInsidedata.get("id").toString();
				 try{
				 User user =new CouchBaseOperation().getTodoistUserEmail(jsonData.get("username").toString());
				 token=user.getToken();
				 }catch(Exception e){
					 
				 }
				 ProjectDetails project=new ProjectDetails();
				 project.setProjectId(projectId);
				 project.setAssignedUser(assginUser);
				 project.setToken(token);
				 list1.add(project);
				 log.info("datat "+projectId   +" token "+token);

			}
			try{
				client1.shutdown(9000l, TimeUnit.MILLISECONDS);
			}catch(Exception e){
				e.printStackTrace();
			}
	
			


		} catch (Exception e) {
			 e.printStackTrace();
		}
		return list1;
	}
	

	private static DesignDocument getDesignDocument(String name) {
		try {
			log.info("Design document with "+name+" exist ? "+client1.getDesignDoc(name));
	        return client1.getDesignDoc(name);
	    } catch (com.couchbase.client.protocol.views.InvalidViewException e) {
	        return new DesignDocument(name);
	    }
	}
	
	
	public static void main() throws IOException, JSONException {
		/*client1 = new CouchBaseOperation().getConnectionToCouchBase();
String content="";
		for(int i=254 ;i<=410;i++){
			String data=null;
			try{
				data=client1.get("Visdom_Referrel_"+i).toString();
			}catch(Exception e){
				
			}
			if(data!=null){
			content+="\nVisdom_Referrel_"+i;
			content+=data;
			}
		}
		

		File file = new File("/home/venkateshm/Desktop/referralData.txt");

		// if file doesnt exists, then create it
		if (!file.exists()) {
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(content);
		bw.close();

		log.info("Done");*/
		creatStageMailTasks("111", 18, "test");
		
	}
	
		

}
