package com.syml.jotform.submitReferalForm;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.debortoliwines.openerp.api.FilterCollection;
import com.debortoliwines.openerp.api.ObjectAdapter;
import com.debortoliwines.openerp.api.Row;
import com.debortoliwines.openerp.api.RowCollection;
import com.debortoliwines.openerp.api.Session;
import com.sendwithus.SendWithUsExample;
import com.syml.constants.SymlConstant;
import com.syml.couchbase.CouchBaseOperation;
import com.syml.helper.GenericHelperClass;
import com.syml.mail.leadMailTemplate.ConfirmEmailOfReferringLeadMessageTemplate;
import com.syml.mail.leadMailTemplate.ErrorInCreatingLeadMessageTemplate;
import com.syml.mail.leadMailTemplate.ErrorInFillingForm;
import com.syml.mail.leadMailTemplate.LeadAlreadyExsistMessageTemplate;
import com.syml.openerp.LeadTaskCreationRestcall;
import com.syml.openerp.ReferalCreateLead;
import com.syml.openerp.RestCallClass;

public class SubmitReferalForm2ServletD extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		SendWithUsExample sendWithus=	new SendWithUsExample();
		SymlConstant sc=new SymlConstant();
		
	 	Logger log = LoggerFactory.getLogger(this.getClass());
	 	
	 	log.debug("inside service method of SubmitReferalForm2Servlet");

		
		String refFirstName = req.getParameter("q5_firstname");
		String refLastName = req.getParameter("q6_lastname");
		String refPhone = req.getParameter("q7_referralsPhone");
		String refEmail = req.getParameter("q8_email").toLowerCase();
		String formType ="Submit Referral";
		String address=req.getParameter("q9_addressOf");
	//	String uniId=req.getParameter("uniid");
		HttpSession ses=req.getSession(true);
		String message=(String)ses.getAttribute("submit_referral_message");
		
	String	ReferralPhoneNumber=(String) ses.getAttribute("phoneNumber");
		String OldUniqueId=req.getParameter("uniqueid");
	String	referralIdFound =(String)ses.getAttribute("referralIdFound");
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		//get current date time with Calendar()
		   Calendar cal = Calendar.getInstance();
		  String currentDateTime=(dateFormat.format(cal.getTime()));
		  
		  //get ip of latest form sumitted
		 // GetMyIP myip=new GetMyIP();
		  String ip=null;
				  try{
					ip=req.getRemoteAddr();  
				  }catch(Exception e){
					 log.error("error in reading GetIpAddres"+e) ;
				  }
		  

		  String form1Fname="";
			String form1Lname="";
			String form1Email="";
			String form1UniqueId="";
		  try{
		   form1Fname=(String)ses.getAttribute("form1Fname");
			 form1Lname=(String)ses.getAttribute("form1Lname");
			 form1Email=(String)ses.getAttribute("form1email");
			 form1UniqueId=(String)ses.getAttribute("form1uniqueId");
		  }catch(Exception e){
			  log.error("error in reading data from session object"+e);
			  
		  }
		log.debug("input from form2 referal resource : firstname "
				+ refFirstName + "\t lastname : " + refLastName + "\t phone : "
				+ refPhone + "\t email : " + refEmail + "\t current time : "
				+ currentDateTime + "\t address : " + address + "\t ip : " + ip
				+ "\t uniId : " + form1UniqueId  +"oldUniqueid "+OldUniqueId);

		
		String key=formType+"_"+form1UniqueId;
		
		//cate Store data to couchbase
		CouchBaseOperation storeData=new CouchBaseOperation();
		/*JSONObject jsonObject=storeData.getCouchBaseData(key);
	
		String OldUniqueId=null;
		try {
			 OldUniqueId=jsonObject.getString("uniqueid");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			log.error("error in reading data from couchbase "+e1);
		}
		
		
	*/
		try{
			if(form1UniqueId==null){
				form1UniqueId="";
			}
		}catch(Exception e){
			
		}
	
		if(form1UniqueId.trim().equals(OldUniqueId.trim())){
			
			log.debug("both forms are filled by same person");
			
			//store value in hashmap to send data to storeSubmitReferalDataInCouchBase()
			HashMap dataStoreValue= new HashMap();
			dataStoreValue.put("referralIdFound", referralIdFound);

			dataStoreValue.put("FirstName_of_referal",refFirstName);
			dataStoreValue.put("LastName_of_referal",refLastName);
			dataStoreValue.put("phoneNumber_of_referal",refPhone);
			dataStoreValue.put("Email_of_referal",refEmail);
			dataStoreValue.put("Submission_Date_Time",currentDateTime);
			dataStoreValue.put("address_of_referal",address);
			dataStoreValue.put("ip", ip);
			
			//create a unique key for storing data into couchbase
			
			
			
	
		
			//shows value from previos form
			dataStoreValue.put("Referal_Source_FirstName",form1Fname);
			dataStoreValue.put("Referal_Source_LastName",form1Lname);
			dataStoreValue.put("Referal_Source_Email",form1Email);
			log.debug("input from form1 firstname: "
					+ form1Fname + "\t lastname : " + form1Lname + "\t form1UniqueId : "
					+ form1UniqueId + "\t email : " + form1Email);
			
			int code =0;
			//to create note taking id
			int id=0;
			//create Lead for referal source
			ReferalCreateLead referalCreateLead=new ReferalCreateLead();
			try {
			ArrayList list=referalCreateLead.checkAndCreateLead(dataStoreValue);
			code=(Integer) list.get(0);
			id=(Integer) list.get(1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				log.error("error in checkAndCreatelead "+e);
				code=3;

			}
			PrintWriter pw = res.getWriter();
			if(code==1){
			
				
				HashMap dataStoreValue1= new HashMap();
				dataStoreValue1.put("FirstName_of_referal",refFirstName);
				dataStoreValue1.put("LastName_of_referal",refLastName);
				dataStoreValue1.put("phoneNumber_of_referal",refPhone);
				dataStoreValue1.put("Email_of_referal",refEmail);
				dataStoreValue1.put("Submission_Date_Time",currentDateTime);
				dataStoreValue1.put("address_of_referal",address);
				dataStoreValue1.put("ip", ip);
				
				
				org.codehaus.jettison.json.JSONObject jsonTableData=new org.codehaus.jettison.json.JSONObject();
				try {
					jsonTableData.put("leadid", id);
					jsonTableData.put("leadName", refFirstName+"_"+refLastName);
					jsonTableData.put("leadPhone", refPhone);
					jsonTableData.put("leadReferralName", form1Fname+" "+form1Lname);
					jsonTableData.put("leadReferralPhone", "");
				
				} catch (org.codehaus.jettison.json.JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				

				
				
			//storeData.storeDataInCouchBase(key,formType,dataStoreValue);
				storeData.appendDataInCouchBase(key, dataStoreValue1);
				//pw.print("Thank you for submitting a referral to Visdom Mortgage Solutions.  Please check your inbox for for a confirmation email that we have received your referral and have contacted them by email.  In the event you do not see an email from Visdom, please check the spam folder to ensure your mail provider has not accidentally mislabelled it.");
				//send mail if everthing went smoothly
				//pw.println(message);
				log.debug("calling taskcretion appppppppppp------------->");
							//new 	LeadTaskCreationRestcall(jsonTableData.toString()).start();

sendWithus.sendTOreferalSubmittedReferral(refFirstName ,form1Fname, form1Email);
			//	ConfirmEmailOfReferringLeadMessageTemplate.ConfirmEmailOfReferringLeadMessageTemplateMethod(form1Fname,form1Email, refFirstName, refLastName, refEmail,message,id,"crm.lead");
				JSONObject jsonObject=	storeData.getData(key);
				if(jsonObject!=null){
					new GenericHelperClass(id, jsonObject).start();
				}
				req.setAttribute("message",message);
				res.sendRedirect("https://demobdo.visdom.ca/getdata?referralFirstName="+form1Fname+"&referralLastName="+form1Lname+" &referralSourceEmail="+form1Email+"&clientEmail="+refEmail+"&opportunityName="+refFirstName+"_"+refLastName);
			}else if(code==2){
				HashMap dataStoreValue1= new HashMap();
				dataStoreValue1.put("FirstName_of_referal",refFirstName);
				dataStoreValue1.put("LastName_of_referal",refLastName);
				dataStoreValue1.put("phoneNumber_of_referal",refPhone);
				dataStoreValue1.put("Email_of_referal",refEmail);
				dataStoreValue1.put("Submission_Date_Time",currentDateTime);
				dataStoreValue1.put("address_of_referal",address);
				dataStoreValue1.put("ip", ip);
				dataStoreValue1.put("duplicate", "The Lead is already exsist");
				
				//if alreday lead exsist deleting data from couhbase 
				storeData.appendDataInCouchBase(key, dataStoreValue1);
				//pw.print("The Lead is already exsist ");
				LeadAlreadyExsistMessageTemplate.LeadAlreadyExsistMessageTemplateMthod(form1Fname, form1Email, refFirstName, refLastName, refEmail,message,id,"crm.lead");
				req.setAttribute("message","The Lead already exsist with your enterd details");
				res.sendRedirect("https://demobdo.visdom.ca/getdata?referralFirstName="+form1Fname+"&referralLastName="+form1Lname+" &referralSourceEmail="+form1Email+"&clientEmail="+refEmail+"&opportunityName="+refFirstName+"_"+refLastName);
			}else if(code==3){
				
				HashMap dataStoreValue1= new HashMap();
				dataStoreValue1.put("FirstName_of_referal",refFirstName);
				dataStoreValue1.put("LastName_of_referal",refLastName);
				dataStoreValue1.put("phoneNumber_of_referal",refPhone);
				dataStoreValue1.put("Email_of_referal",refEmail);
				dataStoreValue1.put("Submission_Date_Time",currentDateTime);
				dataStoreValue1.put("address_of_referal",address);
				dataStoreValue1.put("ip", ip);
				dataStoreValue1.put("Error", "Error in Creating Lead");
				storeData.appendDataInCouchBase(key, dataStoreValue1);
				
				//pw.print("Error in Creating Lead ");
				ErrorInCreatingLeadMessageTemplate.ErrorInCreatingLeadMessageTemplateMethod(form1Fname, form1Lname, form1Email, refFirstName, refLastName, refEmail,id,"crm.lead");
				req.setAttribute("message","Error in creating Lead please fill the form once again");
				
				

				res.sendRedirect("https://demobdo.visdom.ca/getdata?referralFirstName="+form1Fname+"&referralLastName="+form1Lname+" &referralSourceEmail="+form1Email+"&clientEmail="+refEmail+"&opportunityName="+refFirstName+"_"+refLastName);

			}
			
			
		}else{
			
			log.debug("page filled by differnt person please refilled your form");
			PrintWriter pw = res.getWriter();
			//pw.print("We found problem while filling the form . It seems previous form is not filled by you. This may be because of session problem. Please abort this form and fill previous form Once again.");
			message ="We found problem while filling the form . It seems the previous form was not completed. This may be because of internet quality or session problems. "+
"Please open this link (https://dev-formsapp-v1.visdom.ca/formsapp/SubmitReferalform1D.html) in a new browser tab, close this tab and fill provide us with your information.";
		//We have to send mail to referre about error interrnally
			ErrorInFillingForm.ErrorInFillingFormMethod(form1Fname, form1Email, 0, "crm.lead");
			req.setAttribute("message",message);
			req.getRequestDispatcher("leadsucess.jsp").forward(req, res);
		
		}
		
		
		
	}
}
