package com.syml.mortgage.co;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.syml.constants.SymlConstant;
import com.syml.couchbase.CouchBaseOperation;
import com.syml.jotform.ip.GetMyIP;
import com.syml.openerp.CreateApplicant;
import com.syml.openerp.CreateLead;

public class MortgageApplicationCoServlet1b extends HttpServlet {
	
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse res)
		throws ServletException, IOException {
	// TODO Auto-generated method stub
	
 	Logger log = LoggerFactory.getLogger(MortgageApplicationCoServlet1b.class);
		SymlConstant sc=new SymlConstant();
		String applicantFirstName = req.getParameter("firstname");
		String applicantLastName = req.getParameter("lastname");
		String applicantEmail = req.getParameter("email51");
		String formType ="Mortgage Application"; // Let's make the formType Mortgage Application which allows us to "Group" the applicaiton in Couchbase
		String subForm ="Mortgage Co-Application 1b"; // Let's create a subform field which allows to identify this particular "subform" submission.
		String uniid=req.getParameter("uniid");

	
DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		//get current date time with Calendar()
		   Calendar cal = Calendar.getInstance();
		  String currentDateTime=(dateFormat.format(cal.getTime()));
		  
		  //get ip of latest form sumitted
			String ip=req.getRemoteAddr();

			log.debug("old unique id is  "+uniid);
		log.info("input from form2 referal resource : firstname "
						+ applicantFirstName + "\t lastname : " + applicantLastName
						+"\t email : " + applicantEmail
						+ "\t current time : " + currentDateTime
						+ "\t ip : " + ip);

		HttpSession ses=req.getSession(true);
		
		String form1UniqueId=(String)ses.getAttribute("form1uniqueId"); 
		if(uniid.equals(form1UniqueId)){
				//(String)ses.getAttribute("form1uniqueId"); // TODO What is the purpose for this UID? 
		
		
		CreateApplicant applicantCreate=new CreateApplicant();
		
		applicantCreate.createApplicant(applicantFirstName, applicantLastName, applicantEmail);
		
		int appId=applicantCreate.getId();
		String applicantId = Integer.toString(appId);
		
		
	//	ses.setAttribute("applicantID", applicantId);

		
		
		
		HashMap dataStoreValue= new HashMap();
		// TODO Need to include the UID which was created on form 1a and is being passed from subform to subform.  (uniId) 
		dataStoreValue.put("FirstName_of_co_applicant",applicantFirstName); // This information is the applicant name, not referrla source.
		dataStoreValue.put("LastName_of_co_applicant",applicantLastName);
		dataStoreValue.put("Email_of_co_applicant",applicantEmail);
		
		dataStoreValue.put("CoApplicant_SubForm1b",subForm); // Added in subform to hashmap.
			dataStoreValue.put("ApplicantID_2",applicantId);
		// TODO Add the ApplicantID into Couchbase Document 

		// TODO this Data needs to be appended to the Mortgage Application created in Subform 1a Please ensure you are calling the Append method and that it works. 
		//cate Store data to couchbase
			
			String applicantIDone=(String) ses
					.getAttribute("applicantID");
		CouchBaseOperation storeData=new CouchBaseOperation();
		storeData.appendDataInCouchBase("Applicant_"+applicantIDone, dataStoreValue);
//storeData.appendDataInCouchBase(applicantId, dataStoreValue);
		
		log.debug("input from form1 firstname: "
				+ "\t form1UniqueId : "
				+ form1UniqueId );
		
		ses.setAttribute("co_applicantFirstName", applicantFirstName);
		ses.setAttribute("co_applicantLastName", applicantLastName);
			ses.setAttribute("co_applicantEmail", applicantEmail);
			req.setAttribute("uniid",uniid);
		ses.setAttribute("applicantId2",applicantId);
		req.setAttribute("applicantId2",applicantId);
		req.getRequestDispatcher("MortgageApplicationCo1c.jsp").include(req, res);
		//res.sendRedirect("http://form.jotformpro.com/form/50264859730965?unniid="+form1UniqueId+"&applicantid="+applicantId);
		}else{
			req.setAttribute("message", " We are sorry, but it seems the security and reliability of your internet connection may have been weakened.  To protect your identity and the security of your information, can you please submit this application again");
			req.getRequestDispatcher("MortgageApplicationSucess.jsp").forward(req, res);
		}
}

}
