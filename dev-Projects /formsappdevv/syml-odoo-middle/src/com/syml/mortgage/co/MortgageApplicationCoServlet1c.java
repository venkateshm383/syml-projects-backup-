package com.syml.mortgage.co;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

public class MortgageApplicationCoServlet1c  extends HttpServlet{

 	Logger log = LoggerFactory.getLogger(MortgageApplicationCoServlet1c.class);
 	HashMap dataStoreValue= new HashMap();
	@Override
	
	protected void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		SymlConstant sc=new SymlConstant();
		String refareYouCanada = req.getParameter("isthe");
		String uniid=req.getParameter("uniid");
		String formType ="Mortgage Application";
		String subForm ="Mortgage Co-Application 1c";
		HttpSession ses=req.getSession(true);

		String form1UniqueId=(String)ses.getAttribute("form1uniqueId");
		if(uniid.equals(form1UniqueId)){
		String applicantID=(String)ses.getAttribute("applicantId2");
		//get ip of latest form sumitted
		String ip=req.getRemoteAddr();
		log.debug("Canadian status"+refareYouCanada);
		dataStoreValue.put("CoApplicant-non_Resident", refareYouCanada);
if(refareYouCanada.equalsIgnoreCase("yes")){
	
		String mobilePhone = req.getParameter("mobilenumber");
		String workPhone = req.getParameter("worknumber");
		String homePhone = req.getParameter("homenumber");
		String birthday=req.getParameter("whenis95");
		String insurance = req.getParameter("socialinsurancenumber");
		String non_Resident=req.getParameter("isthe79");
		
		
		
 boolean non_ResidentBool;
		 
		 
		if(non_Resident.equalsIgnoreCase("Yes")){
			
			String whendidCanda=req.getParameter("whendid100");
			 non_ResidentBool = Boolean.parseBoolean(non_Resident);
			 non_ResidentBool=true;

			log.debug("\t whendidCanda : " +whendidCanda);

			dataStoreValue.put("CoApplicant-whendidcametoCannada", whendidCanda);

		}else{
			non_ResidentBool = Boolean.parseBoolean(non_Resident);
			non_ResidentBool=false;
		}
		String startDateString = birthday;
	    DateFormat df = new SimpleDateFormat("MM/dd/yyyy"); 
	    Date startDate=null;
	    String newDateString=null;
	    try {
	        startDate = df.parse(startDateString);
	        newDateString = df.format(startDate);
	        System.out.println(newDateString);
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }	
	    
	    String relationshipStatus=req.getParameter("whatis101");
	    
	     if(relationshipStatus.equalsIgnoreCase("Common-Law") && relationshipStatus!=null){
	         
	         relationshipStatus="Common_Law";
	         
	        }
		log.debug("input from form2 referal resource : mobile "
				+ mobilePhone + "\t refareYouCanada : " + refareYouCanada
				+"\t workPhone : " + workPhone
				+"\t homePhone : " + homePhone
				+"\t insurance : " + insurance
				+"\t birthday : " + birthday
				+"\t relationshipStatus : " +relationshipStatus
				+"\t non_Resident : " +non_Resident
				+ "\t ip : " + ip);
		log.debug("store MobilePhone,HomePhone in OpenERP for Applicant");
		 CreateApplicant updateApplicant=new CreateApplicant();
			updateApplicant.updateApplicant(applicantID, mobilePhone, workPhone, homePhone, insurance, relationshipStatus,non_ResidentBool,startDate);
			//logic for store data in CouchBase
			dataStoreValue.put("CoApplicant-mobile",mobilePhone);
			dataStoreValue.put("CoApplicant-workPhone",workPhone);
			dataStoreValue.put("CoApplicant-homePhone",homePhone);
			dataStoreValue.put("CoApplicant-insurance",insurance);
			dataStoreValue.put("CoApplicant-relationshipStatus", relationshipStatus);
			dataStoreValue.put("CoApplicant-newtoCanada", non_Resident);
			dataStoreValue.put("CoApplicant-birthday", birthday);
	
			
}else{
	String mobilePhone = req.getParameter("mobilenumber");
	String relationshipStatus=req.getParameter("whatis101");
	
    if(relationshipStatus.equalsIgnoreCase("Common-Law") && relationshipStatus!=null){
        
        relationshipStatus="Common_Law";
        
       }
	String birthday=req.getParameter("whenis95");
	
	 DateFormat df = new SimpleDateFormat("MM/dd/yyyy"); 
	    Date startDate=null;
	    String newDateString=null;
	    try {
	        startDate = df.parse(birthday);
	        newDateString = df.format(startDate);
	        System.out.println(newDateString);
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }	
	log.debug("MobileNo is:"+mobilePhone+"relationshipIs:"+relationshipStatus);
	
	//Create Applicant Data in openerp
	CreateApplicant elseApplicant=new CreateApplicant();
	elseApplicant.updateApplicantIfNotCanad(applicantID, mobilePhone, relationshipStatus,startDate);
	
	//store data in CouchBase
	dataStoreValue.put("CoApplicant-mobile",mobilePhone);
	dataStoreValue.put("CoApplicant-relationshipStatus",relationshipStatus);
	dataStoreValue.put("CoApplicant-birthday", birthday);
	
	
	

}

		
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		
		//get current date time with Calendar()
		   Calendar cal = Calendar.getInstance();
		  String currentDateTime=(dateFormat.format(cal.getTime()));

		  //New Date Code
		  
		 
			log.debug("old unique id is  "+uniid);
	
		dataStoreValue.put("CoApplicant_SubForm1c",subForm);


		//cate Store data to couchbase
		String applicantIDone=(String) ses
				.getAttribute("applicantID");
	CouchBaseOperation storeData=new CouchBaseOperation();
	storeData.appendDataInCouchBase("Applicant_"+applicantIDone, dataStoreValue);

		//shows value from previos form

             log.info("input from form1 firstname: "
				+ "\t form1UniqueId : "
				+ form1UniqueId );
		

             req.setAttribute("uniid",uniid);
    		
     		req.getRequestDispatcher("MortgageApplicationCo2.jsp").include(req, res);
             //res.sendRedirect("http://form.jotformpro.com/form/50264983570965?uniid="+form1UniqueId+"&applicantid="+applicantID);

		}else{
			
			req.setAttribute("message", " We are sorry, but it seems the security and reliability of your internet connection may have been weakened.  To protect your identity and the security of your information, can you please submit this application again");
			req.getRequestDispatcher("MortgageApplicationSucess.jsp").forward(req, res);
			
	}
		
	}
}
