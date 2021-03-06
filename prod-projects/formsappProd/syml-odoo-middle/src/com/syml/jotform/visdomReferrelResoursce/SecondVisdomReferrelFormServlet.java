package com.syml.jotform.visdomReferrelResoursce;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.debortoliwines.openerp.api.FilterCollection;
import com.debortoliwines.openerp.api.ObjectAdapter;
import com.debortoliwines.openerp.api.Row;
import com.debortoliwines.openerp.api.RowCollection;
import com.debortoliwines.openerp.api.Session;
import com.syml.couchbase.CouchBaseOperation;
import com.syml.helper.GenericHelperClass;

@SuppressWarnings("serial")
public class SecondVisdomReferrelFormServlet extends HttpServlet {

	static Logger log = LoggerFactory
			.getLogger(SecondVisdomReferrelFormServlet.class);

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String uniqueId = req.getParameter("uniqueId");
		String referrelId = req.getParameter("referrelid");
		String role=req.getParameter("q43_TypeOfReferral");
		String message = req.getParameter("message");
		HttpSession session = req.getSession(true);
		String oldUniqueid = (String) session.getAttribute("uniqueId");
		String electronicSignature = req.getParameter("sign");
		String  signature= req.getParameter("q36_myTyped36");
		String areUsingtouchScreenDevice = req.getParameter("q35_areYou");
		String roleid=null;
		try{
		if(role.equalsIgnoreCase("Realtor")){
			roleid="realtor";
			
		}else if(role.equalsIgnoreCase("Financial Planner")){
			
			roleid="financial_planner";
		}else{
			roleid="other";
		}

		}catch(Exception e){
			log.debug("error in reading role "+ e);
		}
		HashMap referrelData = null;
		int partnerId=0;
	
		String sign = "";
		
	log.debug("uniqueId  "+ uniqueId  +"uniqueId  "+oldUniqueid);
	
		//checking unique id is equal to old uniqueid	
		if (uniqueId.equals(uniqueId)) {
			
			log.debug("both forms are filled by same person");
			log.info("uniqueId " + uniqueId + "referrelId " + referrelId
					+ "  message " + message + "\noUniqueid\n " + oldUniqueid
					+ "signature\n " + signature + "areUsingtouchScreenDevice "
					+ areUsingtouchScreenDevice + "electronicSignature "
					+ electronicSignature +"role"+role);
			//here we getting two signature one will be handwritten and electronic signature based condition 
			//but we need to take only one signature 
String typedSign="";
			if (areUsingtouchScreenDevice.equals("Yes")) {
				sign = electronicSignature;
				
			    	// System.out.println(newString);			
			  } else {
				  typedSign = signature;
				
				}
			
			session.setAttribute("areUsingtouchScreenDevice", areUsingtouchScreenDevice);
			session.setAttribute("sign", sign);
			try {
				
				referrelData = (HashMap) session
						.getAttribute("visdomreferreldata");
				partnerId=(Integer)session.getAttribute("contactId");
				log.debug(" contact id -----"+partnerId);
			} catch (Exception e) {
				log.error(e.toString()  );

			}
			log.info("Adding the signature to hashmap and sending to fourthform");
			referrelData.put("Using_touch_Screen_Device",
					areUsingtouchScreenDevice);
			referrelData.put("role", roleid);
			referrelData.put("signature", typedSign);
			referrelData.put("Type_referral", "Type_referral");
			referrelData.put("formprogress", "70%");

			
			session.setAttribute("visdomreferreldata", referrelData);
			
			
			@SuppressWarnings("unused")
			String name=(String)referrelData.get("firstname");
			String email=(String)referrelData.get("email");
			req.setAttribute("uniqueid", uniqueId);
			session.setAttribute("role", role);
			req.setAttribute("referrelid", referrelId);
			req.setAttribute("message", message);

			/*resp.sendRedirect("http://form.jotformpro.com/form/50346666758972?uniqueid="
					+ uniqueId
					+ "&referrelid="
					+ referrelId
					+ "&message="
					+ message);*/
			
			GenericHelperClass   glHelperClass=	new GenericHelperClass();
			Properties prop=null;
			try{
				 prop = glHelperClass.readConfigfile();
				System.out.println("stg---"+prop.getProperty("submitReferralstageId")+"---"+prop.getProperty("visdomreferralStageId")+"----"+prop.getProperty("opprtunitySatgeid"));
					
			}catch(Exception e){
				
			}
			try{
			Session opSession=glHelperClass.getOdooConnection();
			ObjectAdapter resPartner=opSession.getObjectAdapter("hr.applicant");
			FilterCollection filterCollection=new FilterCollection();
			filterCollection.add("email_from", "=", email);
			filterCollection.add("partner_id","=",partnerId);
			RowCollection row =resPartner.searchAndReadObject(filterCollection, new String[]{"email_from","name","partner_id","stage_id","sign_ip","agreement_date","role"});
			Row row1=row.get(0);
			log.debug("The referrer detalas for to change stage "+row1.get("name"));
			Date date= Calendar.getInstance().getTime();
			log.debug("date  "+date);
			row1.put("sign_ip",req.getRemoteAddr() );
			row1.put("agreement_date",date);
			row1.put("role", roleid);
			row1.put("stage_id", prop.getProperty("visdomreferralStageId"));
			//row1.put("stage_id", 37);
			resPartner.writeObject(row1, true);
			log.debug("Referrer  stage changed to involved");
			}catch(Exception e){
				e.printStackTrace();
				log.error("error in Referrer  stage changed to involved" + e);
			}
			String formType = "Visdom_Referrel";

			CouchBaseOperation storeData = new CouchBaseOperation();
			String key = formType+"_"+ referrelId;

			storeData.editDataInCouchBase(key, formType, referrelData);

			
			//session.setAttribute("pdfpath", pdffilepath);
			//ReferralAgreementMessageTemplate.ReferralAgreementMessage(name, email, signature,new Integer(referrelId), "hr.applicant",pdffilepath);
			
			
			req.getRequestDispatcher("VisdomReferralSource3.jsp").forward(req, resp);
		} else {
			
			String  message1="We found problem while filling the form . It seems the previous form was not completed. This may be because of internet quality or session problems. "+
					"Please open this link (https://formsapp.visdom.ca/formsapp/VisdomReferralSource1.html) in a new browser tab, close this tab and fill provide us with your information.";
			req.setAttribute("message", message1);
			req.getRequestDispatcher("visdomReferralSucuss.jsp").forward(req, resp);
			
		}
		}

	/*}*/

}
