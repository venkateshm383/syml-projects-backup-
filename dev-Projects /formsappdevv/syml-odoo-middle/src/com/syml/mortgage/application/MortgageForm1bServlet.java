package com.syml.mortgage.application;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.syml.constants.SymlConstant;
import com.syml.couchbase.CouchBaseOperation;
import com.syml.openerp.CreateApplicant;
import com.syml.openerp.CreateLead;

public class MortgageForm1bServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
	 	Logger log = LoggerFactory.getLogger(MortgageForm1bServlet.class);
		SymlConstant sc=new SymlConstant();
		String applicantFirstName = req.getParameter("firstname");
		String applicantLastName = req.getParameter("lastname");
		String applicantEmail = req.getParameter("email51");
		
		String uniid=req.getParameter("uniid");
		String formType ="Mortgage Application"; // Let's make the formType Mortgage Application which allows us to "Group" the applicaiton in Couchbase
		String subForm ="Mortgage Application 1b"; // Let's create a subform field which allows to identify this particular "subform" submission.
		
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		//get current date time with Calendar()
		   Calendar cal = Calendar.getInstance();
		  String currentDateTime=(dateFormat.format(cal.getTime()));
		  
		  //get ip of latest form sumitted
			String ip=req.getRemoteAddr();

		log.info("input from form2 referal resource : firstname "
						+ applicantFirstName + "\t lastname : " + applicantLastName
						+"\t email : " + applicantEmail
						+ "\t current time : " + currentDateTime
						+ "\t ip : " + ip);

		log.debug("old unique id is  "+uniid);
		HttpSession ses=req.getSession(true);
		
		String form1UniqueId=(String)ses.getAttribute("form1uniqueId"); 
	
		if(uniid.equals(form1UniqueId)){
		
		CreateApplicant applicantCreate=new CreateApplicant();
		
		applicantCreate.createApplicant(applicantFirstName, applicantLastName, applicantEmail);
		
		int appId=applicantCreate.getId();
		String applicantId = Integer.toString(appId);
		
		
		ses.setAttribute("applicantID", applicantId);

		JSONArray array=new JSONArray();
		
		
		
		
		
		HashMap dataStoreValue= new HashMap();
		// TODO Need to include the UID which was created on form 1a and is being passed from subform to subform.  (uniId) 
		dataStoreValue.put("FirstName_of_applicant",applicantFirstName); // This information is the applicant name, not referrla source.
		dataStoreValue.put("LastName_of_applicant",applicantLastName);
		dataStoreValue.put("Email_of_applicant",applicantEmail);
		dataStoreValue.put("FormType",formType);
		dataStoreValue.put("Applicant-SubForm-1b",subForm); // Added in subform to hashmap.
		dataStoreValue.put("Submission_Date_Time1b",currentDateTime); // Changed name of field in DB to allow for a different submisison time for each subform in the Record.
		dataStoreValue.put("ip", ip);
		dataStoreValue.put("ApplicantID1",applicantId);

		
		CouchBaseOperation storeData=new CouchBaseOperation();
storeData.storeDataInCouchBase("Applicant_"+applicantId, formType, dataStoreValue);
		
		log.debug("input from form1 firstname: "
				+ "\t form1UniqueId : "
				+ form1UniqueId );
		
		req.setAttribute("uniid",uniid);
		
		ses.setAttribute("applicantFirstName",applicantFirstName);
		ses.setAttribute("applicantLasttName",applicantLastName);
		ses.setAttribute("applicantEmail",applicantEmail);
		//req.setAttribute("applicantId",applicantId);
		req.getRequestDispatcher("MortgageApplication6.jsp").include(req, res);
		}else{
			req.setAttribute("message", " We are sorry, but it seems the security and reliability of your internet connection may have been weakened.  To protect your identity and the security of your information, can you please submit this application again");
			req.getRequestDispatcher("MortgageApplicationSucess.jsp").forward(req, res);
			
		}
		//res.sendRedirect("http://form.jotformpro.com/form/50264859730965?unniid="+form1UniqueId+"&applicantid="+applicantId);
		
	}
}
