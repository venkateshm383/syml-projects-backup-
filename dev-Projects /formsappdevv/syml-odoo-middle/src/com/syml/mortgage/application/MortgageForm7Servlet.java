package com.syml.mortgage.application;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.URL;
//import java.nio.channels.SeekableByteChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
//import org.apache.catalina.connector.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.syml.address.splitAddress.PropertySplitter;
import com.syml.addressgroup.pojo.Incomes;
import com.syml.addressgroup.pojo.PropertyGrouping;
import com.syml.constants.SymlConstant;
import com.syml.couchbase.CouchBaseOperation;
import com.syml.helper.GenericHelperClass;
import com.syml.jotform.createform.SubmitReferalForm2;
import com.syml.jotform.ip.GetMyIP;
import com.syml.mail.MortgageApplicationTemplate.ThankyouMailTemplateforApplicant;
import com.syml.openerp.CheckReferalResource;
import com.syml.openerp.CreateApplicant;
import com.syml.openerp.CreateLead;
public class MortgageForm7Servlet extends HttpServlet{
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		Logger log = LoggerFactory.getLogger(MortgageForm7Servlet.class);
		SymlConstant sc=new SymlConstant();
		HashMap dataStoreValue= new HashMap();
		
		try{
			log.debug("inside service method");

			String uniid=req.getParameter("uniid");

		//	String uniqueid=req.getParameter("unique");
			String formType ="Mortgage Application";
			String subForm ="Mortgage Application 7";
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		//	log.debug("old unique id is  "+uniqueid);
			//get current date time with Calendar()
			Calendar cal = Calendar.getInstance();
			String currentDateTime=(dateFormat.format(cal.getTime()));


			HttpSession ses=req.getSession(true);
			
			String form1UniqueId=(String)ses.getAttribute("form1uniqueId");
			String applicantId=(String) ses.getAttribute("applicantID");
			// get ip of latest form sumitted
			String ip=req.getRemoteAddr();
			if(form1UniqueId.equals(form1UniqueId)){

			String propertystyle=req.getParameter("whatis44");
			String typeofbuilding=req.getParameter("whattype57");
			
			if(typeofbuilding==null){
				typeofbuilding="";
			}
			String propertyheated=req.getParameter("whatis58");
			String getwater=req.getParameter("wheredoes");
			String propertydispose=req.getParameter("howdoes");
			String garagetype=req.getParameter("whattype61");
			String additionalbuilding=req.getParameter("doesyour");
			String sqaurefootage=req.getParameter("squareFt");
			String garabageSize="";
			try{
			 garabageSize=req.getParameter("q64_whatSize");
			 if(garabageSize==null){
					garabageSize="";
				}
			}catch(Exception e){
				
			}
			
int leadId=0;
			
			try{
				leadId=Integer.parseInt((String)ses.getAttribute("crmLeadId"));
			}catch(Exception e){
				
			}
			
			CreateApplicant createApplicant=new CreateApplicant();
			
			createApplicant.updateOpportunity(leadId, propertystyle, typeofbuilding, propertyheated, getwater,propertydispose, garagetype, garabageSize,additionalbuilding, sqaurefootage);
			
			log.debug("Propertystyle:"+propertystyle);
			log.debug("Typeofbuilding:"+typeofbuilding);
			log.debug("Propertyheated:"+ propertyheated);
			log.debug("Getwater:"+getwater);
			log.debug("Propertydispose:"+propertydispose);
			log.debug("Garagetype:"+garagetype);
			log.debug("Additionalbuilding:"+additionalbuilding);
			log.debug("Squarefootage:" +sqaurefootage);
			log.debug(" garabage size "+ garabageSize);
			//Storing to couchBase

CouchBaseOperation appendData=new CouchBaseOperation();
log.debug("inside Couchbase operation");
dataStoreValue.put("Applicant-propertystyle",propertystyle);
dataStoreValue.put("Applicant-typeofbuilding", typeofbuilding);
dataStoreValue.put("Applicant-propertyheated", propertyheated);
dataStoreValue.put("Applicant-getwater",getwater);
dataStoreValue.put("Applicant-propertydispose", propertydispose);
dataStoreValue.put("Applicant-garagetype",garagetype);
dataStoreValue.put("Applicant-garageSize",garabageSize);
dataStoreValue.put("Applicant-additionalbuilding", additionalbuilding);
dataStoreValue.put("Applicant-sqaurefootage", sqaurefootage);

dataStoreValue.put("Applicant-subForm7",subForm);

appendData.appendDataInCouchBase("Applicant_"+applicantId, dataStoreValue);
log.debug(" Couchbase operation sucessfully");


req.setAttribute("uniid",uniid);
//todo adding applicant name  applicant email  module
//ThankyouMailTemplateforApplicant.ThankyouMailTemplateforApplicantMethod("applicantEmail", "applicantName", 0, "module");
req.setAttribute("message","Data is inserted sucessfully into Applicant Record");
	//req.setAttribute("applicantId",applicantId);
	req.getRequestDispatcher("MortgageApplication1c.jsp").include(req, res);
//res.sendRedirect("http://form.jotformpro.com/form/50496822883971?uniqueId"+form1UniqueId);

			
			}else{
    			req.setAttribute("message", " We are sorry, but it seems the security and reliability of your internet connection may have been weakened.  To protect your identity and the security of your information, can you please submit this application again");
				req.getRequestDispatcher("MortgageApplicationSucess.jsp").forward(req, res);
				
			}
			
	}catch(Exception e){
		e.printStackTrace();
			log.error("error in service : "+e);
		}

	}


}