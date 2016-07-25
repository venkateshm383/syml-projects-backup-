package com.syml.mortgage.application;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.dgc.Lease;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.iharder.Base64;

import org.json.JSONObject;


import org.slf4j.LoggerFactory;

import com.debortoliwines.openerp.api.FilterCollection;
import com.debortoliwines.openerp.api.ObjectAdapter;
import com.debortoliwines.openerp.api.Row;
import com.debortoliwines.openerp.api.RowCollection;
import com.debortoliwines.openerp.api.Session;
import com.jotform.api.JotForm;
import com.sendwithus.SendWithUsExample;
import com.syml.constants.SymlConstant;
import com.syml.couchbase.CouchBaseOperation;
import com.syml.helper.GenericHelperClass;
import com.syml.mail.MortgageApplicationTemplate.CompletedMortgageAppMailTemplate;
import com.syml.mail.MortgageApplicationTemplate.SendinMailToReferrerTemplate;
import com.syml.mail.MortgageApplicationTemplate.ThankyouMailTemplateforApplicant;
import com.syml.openerp.CreateApplicant;
import com.syml.openerp.DocumentAnalyzerRestCall;
import com.syml.openerp.RestCallClass;
import com.syml.pdfGeneration.MortgageApplicationPdfGeneration;

public class MortgageForm8Servlet extends HttpServlet {
	
	private static org.slf4j.Logger log = LoggerFactory.getLogger(JotForm.class);
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		SymlConstant sc = new SymlConstant();
		HashMap dataStoreValue = new HashMap();
		String filepath = null;
		String applicantName = "";
		String applicantLastName = "";
		String applicantOneEmail = "";
		String applicantTwoEmail = "";
		String applicantThreeEmail = "";
		int applicantId = 0;
		String coApplicantName = "";
		String referrerEmail = "";
		String applicantTwoFirstName = "";
		String applicantTwoLastName = "";
		int applicantId2 = 0;
		String referralName = "";
		try {
			log.debug("inside service method");

			String uniid = req.getParameter("uniid");
			HttpSession ses = req.getSession(true);
			int leadId = 0;

			
			String coapplicant = req.getParameter("coapplicant");

			String co_applicantExsist = "";
			try {
				co_applicantExsist = (String) ses
						.getAttribute("otherApplicant");
				if (co_applicantExsist == null) {
					co_applicantExsist = "";
				}
			} catch (Exception e) {
				 log.error(" error during leading co-applicant information"+e.getMessage());
			}

			// session.setAttribute("crmLeadId", crmLeadId);
			try {
				referrerEmail = (String) ses.getAttribute("referralEmail");
				referrerEmail = referrerEmail.trim();
				referralName = (String) ses.getAttribute("referralName");

				applicantName = (String) ses.getAttribute("applicantFirstName");
				applicantLastName = (String) ses
						.getAttribute("applicantLasttName");
				applicantOneEmail = (String) ses.getAttribute("applicantEmail");
				applicantId = Integer.parseInt((String) ses
						.getAttribute("applicantID"));
			} catch (Exception e) {
				 log.error(" error during leading co-applicant information"+e.getMessage());
			}
			
			try {
				leadId = Integer.parseInt((String) ses
						.getAttribute("crmLeadId"));
			} catch (Exception e) {
				try{
				leadId = Integer.parseInt(req.getParameter("crmLeadId"));
				}catch(Exception e1){
		            log.error(" error in getting Lead ID on page 8"+e1.getMessage());

				}
            log.error(" error in getting Lead ID on page 8"+e.getMessage());
			}
			try{
				leadId = Integer.parseInt(req.getParameter("crmLeadId"));
				}catch(Exception e1){
		            log.error(" error in getting Lead ID on page 8"+e1.getMessage());

				}
			try {
				applicantTwoFirstName = (String) ses
						.getAttribute("co_applicantFirstName");
				if (applicantTwoFirstName == null) {
					applicantTwoFirstName = "";
				}

				applicantTwoLastName = (String) ses
						.getAttribute("co_applicantLastName");
				applicantTwoEmail = (String) ses
						.getAttribute("co_applicantEmail");

				if (applicantTwoEmail == null) {
					applicantTwoEmail = "";
				}
				applicantId2 = Integer.parseInt((String) ses
						.getAttribute("applicantId2"));
			} catch (Exception e) {
				 log.error(" error during leading co-applicant information"+e.getMessage());
			}
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			log.debug("old unique id is  " + uniid);
			// get current date time with Calendar()
			Calendar cal = Calendar.getInstance();
			String currentDateTime = (dateFormat.format(cal.getTime()));

			String form1UniqueId = (String) ses.getAttribute("form1uniqueId");
			// if(uniid.equals(form1UniqueId)){
			// get ip of latest form sumitted
			String ip = req.getRemoteAddr();
			// if(uniid.equals(form1UniqueId)){

			BufferedImage image = null;
			BufferedImage image1 = null;

			String areUsingTouchScreenDevice = req.getParameter("q35_areYou");
			if (areUsingTouchScreenDevice.equals("Yes")) {

				// BASE64Decoder decoder = new BASE64Decoder();
				String fileName = req.getParameter("sign1");
				String newString="";
				try{
				 newString = fileName.substring(22);
				}catch(Exception e){
				log.error("error while reading sign & image"+e.getMessage());	
				}

				if (co_applicantExsist.equalsIgnoreCase("Yes")) {
					String filename2 = req.getParameter("sign2");
					String newString1="";
					try{
					 newString1 = filename2.substring(22);
					}catch(Exception e){
						 log.error(" error during applicant detailes existance "+e.getMessage());		
					}
					byte[] decodedBytes1 = Base64.decode(newString1);
					image1 = ImageIO.read(new ByteArrayInputStream(
							decodedBytes1));

				}

				byte[] decodedBytes = Base64.decode(newString);

				if (decodedBytes == null) {
					log.debug("decodedBytes  is null");
				}

				image = ImageIO.read(new ByteArrayInputStream(decodedBytes));

				filepath = MortgageApplicationPdfGeneration
						.MortgageApplicationPdfGenerationMethod(applicantName,
								areUsingTouchScreenDevice, co_applicantExsist,
								image, image1, "", "");
				if (image == null) {
					log.debug("Buffered Image is null");
				}
			} else {
				String mytypedname2 = req.getParameter("secondname");
				// / System.out.println(mytypedname2);
				String mytypedname1 = req.getParameter("firstname");
				dataStoreValue.put("Signature_mytypedname1", mytypedname1);
				dataStoreValue.put("Signature_mytypedname2", mytypedname2);
				filepath = MortgageApplicationPdfGeneration
						.MortgageApplicationPdfGenerationMethod(applicantName,
								areUsingTouchScreenDevice, "Yes", null, null,
								mytypedname1, mytypedname2);

			}

			// Logic to store Signature in OpenERP
			log.debug("file path ----------" + filepath);
			CreateApplicant signatureAppliant = new CreateApplicant();

			signatureAppliant.updateApplicantSignatureAndIp(applicantId,
					cal.getTime(), ip);

			if (applicantId2 != 0) {
				signatureAppliant.updateApplicantSignatureAndIp(applicantId2,
						cal.getTime(), ip);
			}
			log.debug("going to update signature");
			try {
			
				SendWithUsExample sendWithUsExample = new SendWithUsExample();
				
				log.debug("referral name -------------------------"
						+ referralName);
				sendWithUsExample.sendTOreferralCompletedApp(
						referralName.trim(), applicantName, referrerEmail,
						applicantTwoFirstName);
				sendWithUsExample.sendDisclosuresToclientCompletedApp(
						applicantName, applicantOneEmail,
						applicantTwoFirstName, applicantTwoEmail,
						applicantThreeEmail, filepath);
			
			} catch (Exception e) {
          log.error(" error occurs while sending referral name"+e.getMessage());
			}
			try {
				GenericHelperClass genericHelperClass = new GenericHelperClass();
				log.debug("getting oppeunity name");
				Session openERPSession = genericHelperClass.getOdooConnection();
				ObjectAdapter applicantAd3 = openERPSession
						.getObjectAdapter("crm.lead");
				com.debortoliwines.openerp.api.FilterCollection filters11 = new com.debortoliwines.openerp.api.FilterCollection();
				filters11.add("id", "=", leadId);
				com.debortoliwines.openerp.api.RowCollection partners11 = applicantAd3
						.searchAndReadObject(filters11, new String[] { "name",
								"id","hr_department_id" });
				
				Row row = partners11.get(0);
				row.put("hr_department_id", 1);

				applicantAd3.writeObject(row, true);
				
				
				
		 filters11 = new com.debortoliwines.openerp.api.FilterCollection();
				filters11.add("id", "=", leadId);
				 partners11 = applicantAd3
						.searchAndReadObject(filters11, new String[] { "name",
								"id","stage_id" });
				
				 row = partners11.get(0);
				row.put("stage_id",15);
				
				applicantAd3.writeObject(row, true);
				log.debug("getting opprunity name:" + row.get("name"));

				String val = null;
				try {
					val = row.get("referred_source").toString();
				} catch (Exception e) {
					 log.error(" error occurs while sending referral name"+e.getMessage());
				}
				if (val == null) {
					new SendWithUsExample().sentToSupportReferralMissing(row
							.get("name").toString(), "support@visdom.ca");
				}// CompletedMortgageAppMailTemplate.CompletedMortgageAppMailTemplateMethod(leadId,
					// "crm.lead", row.get("name").toString(), applicantName,
					// applicantLastName);
			} catch (Exception e) {
				log.error("Error in converting stage partial to completedd app"+leadId);

				log.error("error in sending mail of completion Of mortgage" + e.getMessage());
			}
try{
			Path path = Paths.get(filepath);
			byte[] data1 = Files.readAllBytes(path);
			CouchBaseOperation storedata = new CouchBaseOperation();
			HashMap data11 = new HashMap();

			String encodeData = net.iharder.Base64.encodeBytes(data1);

			data11.put("attachement", encodeData);
			storedata.storeDataInCouchBase("doc_Disclosures_" + applicantId,
					"mortgageForm8", data11);
			dataStoreValue.put("Mortgage Brokerage Disclosures_id",
					"Disclosures_" + applicantId);
			String id = "Applicant_" + applicantId;
			dataStoreValue.put("Applicant-subForm8", "Mortgage Form 8");

			storedata.appendDataInCouchBase(id, dataStoreValue);
			JSONObject jsonObject = storedata.getData(id);
			if (jsonObject != null) {
				 GenericHelperClass.createNote2(new Integer(applicantId),
						jsonObject.toString(), "Applicant Json Data",
						"applicant.record");
			}
}catch(Exception e){
	log.error("error in get pdf data"+e.getMessage());
}
			try {
				File file2 = new File(filepath);
				file2.delete();
				log.debug("pdf file deleted   -----------");
			} catch (Exception e) {
				log.error("error at deleting file data"+e.getMessage());
			}
	
			req.setAttribute(
					"message",
					"Thank you for completing the Visdom Mortgage Application.  We will be in touch with you very soon.");
			req.getRequestDispatcher("MortgageApplicationSucess.jsp").forward(
					req, res);

			
		} catch (Exception e) {

			log.error("error at deleting file data"+e.getMessage());
		}
	}

	public String getReferralname(String referralEmail) {
		String referralName = null;
		String contactId = null;

		RowCollection referralList = null;
		try {

			Session openERPSession = new GenericHelperClass()
					.getOdooConnection();
			ObjectAdapter referral = openERPSession
					.getObjectAdapter("hr.applicant");

			FilterCollection filters1 = new FilterCollection();
			filters1.add("email_from", "=", referralEmail.toLowerCase().trim());

			referralList = referral.searchAndReadObject(filters1, new String[] {
					"email_from", "name", "partner_id" });
			log.debug(referralList.size() + "id");

			for (Row row : referralList) {
				if (row.get("partner_id") != null) {
					Object[] object = (Object[]) row.get("partner_id");
					contactId = object[0].toString();
				}
			}
		} catch (Exception e) {
			log.error("error at connection for reffering applicant data"+e.getMessage());
		}

		try {
			Session openERPSession = new GenericHelperClass()
					.getOdooConnection();
			log.debug("Seesion is" + openERPSession);
			ObjectAdapter opprtunity = openERPSession
					.getObjectAdapter("res.partner");

			FilterCollection filter = new FilterCollection();
			filter.add("id", "=", contactId);
			RowCollection row = opprtunity.searchAndReadObject(filter,
					new String[] { "id", "name" });
			Row row1 = row.get(0);
			referralName = row1.get("name").toString();
			log.debug("-----------------referral soursce Name--------------------- "
							+ referralName);
		} catch (Exception e) {
			log.error("error at connection for reffering applicant data"+e.getMessage());
		}

		return referralName;
	}

}
