package com.syml.mortgage.application;

import java.io.IOException;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.syml.constants.SymlConstant;
import com.syml.couchbase.CouchBaseOperation;

import com.syml.openerp.CheckReferalResource;
public class MortgageForm1aServlet extends HttpServlet{
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
	 	Logger log = LoggerFactory.getLogger(MortgageForm1aServlet.class);
		SymlConstant sc=new SymlConstant();
/*req.setAttribute("name", "test");
*/	try{
/*   req.getRequestDispatcher("hello.jsp").forward(req, res);
*/			
		System.out.println("inside service method");
			String formType ="Mortgage Application"; // Let's make the formType Mortgage Application which allows us to "Group" the applicaiton in Couchbase
			String subForm ="Mortgage Application 1a";
			
			UUID uuid = UUID.randomUUID();
			
			String uniqueId=uuid.toString();;
			log.debug("unique id  "+ uniqueId);
				
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

			//get current date time with Calendar()
			Calendar cal = Calendar.getInstance();
			String currentDateTime=(dateFormat.format(cal.getTime()));

			//get ip of latest form sumitted
			String ip=req.getRemoteAddr();
			//put variable into session
			
			String crmLeadId=req.getParameter("crmLeadId");
			String referralEmail=req.getParameter("referralEmail");
			String referralName=req.getParameter("referralName");

			
			

			log.debug("crmLead id is  ------ "+crmLeadId);
			log.debug("Referral Email id is  ------ "+referralEmail);
			log.debug("Referral Name is  ------ "+referralName);

			HttpSession session=req.getSession(true);
			
			
			session.setAttribute("crmLeadId", crmLeadId);
			session.setAttribute("referralEmail", referralEmail);
			session.setAttribute("referralName", referralName);

		
		
			session.setAttribute("form1uniqueId",uniqueId);
			HashMap dataStoreValue= new HashMap();
			dataStoreValue.put("FormType",formType);
			dataStoreValue.put("SubForm",subForm); // Added in subform to hashmap.
			dataStoreValue.put("mortgageformUniqueId",uniqueId); // TODO Is this a new UID?  If so, what purpose is it serving? 
			dataStoreValue.put("Submission_Date_Time1a",currentDateTime); // Changed name of field in DB to allow for a different submisison time for each subform in the Record.
			dataStoreValue.put("ip", ip);

			CouchBaseOperation storeData=new CouchBaseOperation();
			//storeData.storeDataInCouchBase(uniqueId, formType, dataStoreValue);
			//res.sendRedirect("http://form.jotformpro.com/form/50264938264965?unnid="+uniqueId);

			
			
			req.setAttribute("uniid",uniqueId);
			//req.setAttribute("applicantId",applicantId);
			req.getRequestDispatcher("MortgageApplication1b.jsp").include(req, res);
		}catch(Exception e){
			log.info("error in service : "+e);
		}

	}





}
