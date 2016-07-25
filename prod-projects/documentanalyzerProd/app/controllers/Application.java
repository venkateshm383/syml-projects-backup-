package controllers;

import static play.data.Form.form;
import helper.Applicants;
import helper.CouchBaseOperation;
import helper.Odoo;
import helper.Opprtunity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import net.iharder.Base64;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import play.Logger;

import play.data.DynamicForm;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.doc;
import views.html.index;

import com.fasterxml.jackson.databind.JsonNode;

import document.ExtractPagesFromPdfAndSaveAsNewPDFPage;
import document.MortgageDocumentConverter;
import dto.ApplicantDocument;
import email.SendWithUsMail;

public class Application extends Controller {

	private static org.slf4j.Logger logger = play.Logger.underlying();

	public static Result index() throws JSONException, IOException {
		return ok("test");

	}

	public static Result getOriginalDocID() throws JSONException {
		String opprtunity = "";
		String id = "";
		id = request().getQueryString("id");
		ArrayList<String> applicants = new ArrayList<String>();

		ArrayList<String> docuemnts = new ArrayList<String>();
		CouchBaseOperation couchBaseOperation = new CouchBaseOperation();
		ArrayList<DocoSolo> splitedList = new ArrayList<DocoSolo>();

		ArrayList<String> applicantsDisplay = new ArrayList<String>();

		ArrayList<String> coapplicantsDisply = new ArrayList<String>();

		ArrayList<String> applicantsDocuments = new ArrayList<String>();
		ArrayList<String> coapplicantsDocuments = new ArrayList<String>();

		ArrayList<String> propertyDocuments = new ArrayList<String>();

		ArrayList<String> downpaymentDocuments = new ArrayList<String>();

		ArrayList<String> missleneusDocuments = new ArrayList<String>();

		String opportunityName = "";
		JSONObject jsonObject = null;

		ArrayList<OpportunityList> OpportunityList = new Odoo()
				.getOpprunityDetials();

		String typeProject = "";
		try {
			jsonObject = couchBaseOperation.getCouchBaseData(id);
			typeProject = jsonObject.get("Type").toString();
		} catch (Exception e) {
			logger.error("Error occured while getting OriginalDocID "+e.getMessage());

		}

		try{
		if (jsonObject != null) {
			opprtunity = jsonObject.get("opprtunityId1").toString();
			applicants.add(jsonObject.get("applicantName1").toString());
			applicantsDisplay.add(jsonObject.get("applicantName1").toString());
			opportunityName = jsonObject.get("opprtunity").toString();
			try {
				applicants.add(jsonObject.get("applicantName2").toString());
				coapplicantsDisply.add(jsonObject.get("applicantName2")
						.toString());
			} catch (Exception e) {
				logger.error("Error occured while getting OriginalDocID "+e.getMessage());
			}

			logger.debug("json object " + jsonObject);

		}
		
		}catch(Exception e){
			logger.error("Error occured while updating JSONobject"+e.getMessage());
		}
		applicants.add("Property Documents");
		applicants.add("DownPayment Documents");
		applicants.add("Miscellaneous Documents");

		JSONObject doclist = null;
		try {
			doclist = couchBaseOperation
					.getCouchBaseData("DocumentListOfApplicationNo_"
							+ opprtunity);
		} catch (Exception e) {
			logger.error("Error occured while getting DocumentList "+e.getMessage());
		}
		JSONObject jsonAttachmentKey = null;
		String attachment = null;

		try {
			jsonAttachmentKey = couchBaseOperation.getCouchBaseData(jsonObject
					.get("BinaryID").toString());
			attachment = jsonAttachmentKey.get("attachement").toString();

		} catch (Exception e) {
			logger.error("Error occured while getting CouchbaseData "+e.getMessage());
		}

		if (doclist != null) {
			try {
				JSONArray jsonArray = new JSONArray(doclist.get("documents")
						.toString());
				for (int i = 0; i < jsonArray.length(); i++) {
					docuemnts.add(jsonArray.get(i).toString());
					applicantsDocuments.add(jsonArray.get(i).toString());
				}
			} catch (Exception e) {
				logger.error("Error occured while getting CouchbaseData "+e.getMessage());
			}

			try {
				JSONArray jsonArray = new JSONArray(doclist.get("co_documents")
						.toString());
				for (int i = 0; i < jsonArray.length(); i++) {
					docuemnts.add(jsonArray.get(i).toString());
					coapplicantsDocuments.add(jsonArray.get(i).toString());
				}
			} catch (Exception e) {
				logger.error("Error occured while adding coapplicantsDocuments "+e.getMessage());
			}

			try {
				JSONArray jsonArray = new JSONArray(doclist.get(
						"propertyDocments").toString());
				for (int i = 0; i < jsonArray.length(); i++) {
					docuemnts.add(jsonArray.get(i).toString());
					propertyDocuments.add(jsonArray.get(i).toString());

				}
			} catch (Exception e) {
				logger.error("Error occured while adding propertyDocments "+e.getMessage());
			}

			try {
				JSONArray jsonArray = new JSONArray(doclist.get("downPayments")
						.toString());
				for (int i = 0; i < jsonArray.length(); i++) {
					docuemnts.add(jsonArray.get(i).toString());
					downpaymentDocuments.add(jsonArray.get(i).toString());
				}
			} catch (Exception e) {
				logger.error("Error occured while getting Documentlist "+e.getMessage());
			}

			try {
				JSONArray jsonArray = new JSONArray(doclist.get(
						"MiscellaneousDocuments").toString());
				for (int i = 0; i < jsonArray.length(); i++) {
					docuemnts.add(jsonArray.get(i).toString());
					missleneusDocuments.add(jsonArray.get(i).toString());
				}
			} catch (Exception e) {
				logger.error("Error occured while getting Documentlist "+e.getMessage());
			}

		}
		logger.debug("list of document needed " + docuemnts.toString());
		JSONObject oldSplited = null;
		try {
			oldSplited = couchBaseOperation
					.getCouchBaseData("DocumentSplitList" + opprtunity);
			JSONArray jsonArray = new JSONArray(oldSplited.get("splitedList")
					.toString());

			try {
				for (int i = 0; i < jsonArray.length(); i++) {

					DocoSolo docData = new DocoSolo();
					JSONObject jsonData = couchBaseOperation
							.getCouchBaseData(jsonArray.get(i).toString());
					docData.setDocId(jsonArray.get(i).toString());
					docData.setRelatedDoc(jsonData.get("DocumentName")
							.toString());
					docData.setDocumentType(jsonData.get("DocumentType")
							.toString());
					docData.setLink(jsonData.get("ViewerURL").toString());
					splitedList.add(docData);

				}

			} catch (Exception e) {
				logger.error("Error occured while adding Documentlist to couchbase"+e.getMessage());
			}
		} catch (Exception e) {
			logger.error("Error occured while adding Documentlist to couchbase"+e.getMessage());
		}

		// attachment
		return ok(index.render(attachment, OpportunityList, applicants,
				applicantsDisplay, coapplicantsDisply, applicantsDocuments,
				coapplicantsDocuments, propertyDocuments, downpaymentDocuments,
				missleneusDocuments, opportunityName, id, 1, splitedList, 1, 1));

	}

	public static Result deleteDocList() throws JSONException {
		String opprtunity = "";
		String id = "";
		String attachment = "";
		String opporunityName = "";
		String documenttype = "";
		DynamicForm dynamicForm = form().bindFromRequest();
		opporunityName = dynamicForm.get("opportunity");

		documenttype = dynamicForm.get("documenttype");
		logger.debug("opportunity name" + opporunityName);

		logger.debug("documenttype name" + documenttype);

		ArrayList<String> applicants = new ArrayList<String>();
		ArrayList<String> coapplicants = new ArrayList<String>();
		ArrayList<String> property = new ArrayList<String>();
		ArrayList<String> downpayment = new ArrayList<String>();
		ArrayList<String> misslenious = new ArrayList<String>();

		ArrayList<NotRecivedDocuments> applicantdocuemnts = new ArrayList<NotRecivedDocuments>();
		ArrayList<NotRecivedDocuments> coApplicantdocuemnts = new ArrayList<NotRecivedDocuments>();

		ArrayList<NotRecivedDocuments> propertyDocuemnts = new ArrayList<NotRecivedDocuments>();

		ArrayList<NotRecivedDocuments> downPaymentDocuemnts = new ArrayList<NotRecivedDocuments>();
		ArrayList<NotRecivedDocuments> missleniousDocuemnts = new ArrayList<NotRecivedDocuments>();

		ArrayList<ReceivedDocuments> applicantspliteddocuemnts = new ArrayList<ReceivedDocuments>();
		ArrayList<ReceivedDocuments> coApplicantspliteddocuemnts = new ArrayList<ReceivedDocuments>();

		ArrayList<ReceivedDocuments> propertysplitedDocuemnts = new ArrayList<ReceivedDocuments>();

		ArrayList<ReceivedDocuments> downPaymentsplitedDocuemnts = new ArrayList<ReceivedDocuments>();
		ArrayList<ReceivedDocuments> misslenioussplitedDocuemnts = new ArrayList<ReceivedDocuments>();
		
	
		Odoo odoo = new Odoo();

		ArrayList<String> docuemnts = new ArrayList<String>();
		CouchBaseOperation couchBaseOperation = new CouchBaseOperation();
		ArrayList<OpportunityList> OpportunityList = new Odoo()
				.getOpprunityDetials();

		String opportunityName = "";

		String applicantname = "";
		String coApplicantName = "";

		Opprtunity opporunitysearchedData = odoo
				.getOpprtunityData(opporunityName);

		if (opporunitysearchedData != null) {
			opprtunity = opporunitysearchedData.getOpprtunityId();
			opportunityName = opporunitysearchedData.getOpprtunityName();
			if (opporunitysearchedData.getApplicants() != null) {
				Set<Applicants> applicantss = opporunitysearchedData
						.getApplicants();

				Iterator<Applicants> appl = applicantss.iterator();
				int k = 0;
				while (appl.hasNext()) {
					Applicants applican = appl.next();

					if (k == 1) {
						coApplicantName = applican.getApplicantName();
						coapplicants.add(coApplicantName);
					} else if (k == 0) {
						applicantname = applican.getApplicantName();
						applicants.add(applican.getApplicantName());

					}
					k++;
				}

			}

		}

		JSONObject doclist = null;
		try {
			doclist = couchBaseOperation
					.getCouchBaseData("DocumentListOfApplicationNo_"
							+ opprtunity);
		} catch (Exception e) {
			logger.error("Error occured while deleteDocList"+e.getMessage());
		}

		if (doclist != null) {
			JSONArray jsonData = new JSONArray();
			try {
				JSONArray jsonArray = new JSONArray(doclist.get("documents")
						.toString());
				for (int i = 0; i < jsonArray.length(); i++) {

					if (!documenttype.equalsIgnoreCase(jsonArray.get(i)
							.toString())) {
						jsonData.put(jsonArray.get(i).toString());

					}

				}
				doclist.put("documents", jsonData);

				couchBaseOperation.editDataInCouchBase(
						"DocumentListOfApplicationNo_" + opprtunity, doclist);
			} catch (Exception e) {
				logger.error("Error occured while deleteDocList"+e.getMessage());
			}

			try {
				JSONArray jsonArray = new JSONArray(doclist.get("co_documents")
						.toString());
				JSONArray jsonData1 = new JSONArray();

				for (int i = 0; i < jsonArray.length(); i++) {

					if (!documenttype.equalsIgnoreCase(jsonArray.get(i)
							.toString())) {
						jsonData1.put(jsonArray.get(i).toString());

					}
				}

				doclist.put("co_documents", jsonData1);

				couchBaseOperation.editDataInCouchBase(
						"DocumentListOfApplicationNo_" + opprtunity, doclist);
			} catch (Exception e) {
				logger.error("Error occured while deleteDocList"+e.getMessage());
			}

			try {
				JSONArray jsonData1 = new JSONArray();

				JSONArray jsonArray = new JSONArray(doclist.get(
						"propertyDocments").toString());
				for (int i = 0; i < jsonArray.length(); i++) {

		 			if (!documenttype.equalsIgnoreCase(jsonArray.get(i)
							.toString())) {
						jsonData1.put(jsonArray.get(i).toString());

					}

				}

				doclist.put("propertyDocments", jsonData1);

				couchBaseOperation.editDataInCouchBase(
						"DocumentListOfApplicationNo_" + opprtunity, doclist);
			} catch (Exception e) {
				logger.error("Error occured while editing DocList"+e.getMessage());
			}

			try {
				JSONArray jsonArray = new JSONArray(doclist.get("downPayments")
						.toString());

				JSONArray jsonData1 = new JSONArray();

				for (int i = 0; i < jsonArray.length(); i++) {

					if (!documenttype.equalsIgnoreCase(jsonArray.get(i)
							.toString())) {
						jsonData1.put(jsonArray.get(i).toString());
					}
				}

				doclist.put("downPayments", jsonData1);

				couchBaseOperation.editDataInCouchBase(
						"DocumentListOfApplicationNo_" + opprtunity, doclist);

			} catch (Exception e) {
				logger.error("Error occured while getting downPayments of DocList"+e.getMessage());
			}

			try {
				JSONArray jsonData1 = new JSONArray();

				JSONArray jsonArray = new JSONArray(doclist.get(
						"MiscellaneousDocuments").toString());
				for (int i = 0; i < jsonArray.length(); i++) {

					if (!documenttype.equalsIgnoreCase(jsonArray.get(i)
							.toString())) {
						jsonData1.put(jsonArray.get(i).toString());
					}

				}

				doclist.put("MiscellaneousDocuments", jsonData1);
				couchBaseOperation.editDataInCouchBase(
						"DocumentListOfApplicationNo_" + opprtunity, doclist);

			} catch (Exception e) {
				logger.error("Error occured while inserting  DocList"+e.getMessage());
			}

			logger.debug("list of propertydocument needed "
					+ propertyDocuemnts.toString());
			logger.debug("list of downdocument needed "
					+ downPaymentDocuemnts.toString());

		}

		if (doclist != null) {
			try {
				JSONArray jsonArray = new JSONArray(doclist.get("documents")
						.toString());
				for (int i = 0; i < jsonArray.length(); i++) {
					NotRecivedDocuments notRecivedDocuments = new NotRecivedDocuments();

					notRecivedDocuments.setDocumentName(jsonArray.get(i)
							.toString());
					notRecivedDocuments.setRecived(0);

					docuemnts.add(jsonArray.get(i).toString());
					applicantdocuemnts.add(notRecivedDocuments);
				}
			} catch (Exception e) {
				logger.error("Error occured while not RecivedDocuments"+e.getMessage());
			}

			try {
				JSONArray jsonArray = new JSONArray(doclist.get("co_documents")
						.toString());
				for (int i = 0; i < jsonArray.length(); i++) {

					NotRecivedDocuments notRecivedDocuments = new NotRecivedDocuments();

					notRecivedDocuments.setDocumentName(jsonArray.get(i)
							.toString());
					notRecivedDocuments.setRecived(0);

					docuemnts.add(jsonArray.get(i).toString());
					coApplicantdocuemnts.add(notRecivedDocuments);
				}
			} catch (Exception e) {
				logger.error("Error occured while   notRecived any Documents"+e.getMessage());
			}

			try {
				JSONArray jsonArray = new JSONArray(doclist.get(
						"propertyDocments").toString());
				for (int i = 0; i < jsonArray.length(); i++) {

					NotRecivedDocuments notRecivedDocuments = new NotRecivedDocuments();

					notRecivedDocuments.setDocumentName(jsonArray.get(i)
							.toString());
					notRecivedDocuments.setRecived(0);

					docuemnts.add(jsonArray.get(i).toString());
					propertyDocuemnts.add(notRecivedDocuments);
					logger.debug("property Document--------------- -"
							+ jsonArray.get(i).toString());

				}
			} catch (Exception e) {
				logger.error("Error occured while   not Recived any Documents"+e.getMessage());
			}

			try {
				JSONArray jsonArray = new JSONArray(doclist.get("downPayments")
						.toString());
				for (int i = 0; i < jsonArray.length(); i++) {

					NotRecivedDocuments notRecivedDocuments = new NotRecivedDocuments();

					notRecivedDocuments.setDocumentName(jsonArray.get(i)
							.toString());
					notRecivedDocuments.setRecived(0);

					docuemnts.add(jsonArray.get(i).toString());
					downPaymentDocuemnts.add(notRecivedDocuments);

					logger.debug("downpayments--------------- -"
							+ jsonArray.get(i).toString());
				}
			} catch (Exception e) {
				logger.error("Error occured while not get any downpayment Documents"+e.getMessage());
			}

			try {
				JSONArray jsonArray = new JSONArray(doclist.get(
						"MiscellaneousDocuments").toString());
				for (int i = 0; i < jsonArray.length(); i++) {

					NotRecivedDocuments notRecivedDocuments = new NotRecivedDocuments();

					notRecivedDocuments.setDocumentName(jsonArray.get(i)
							.toString());
					notRecivedDocuments.setRecived(0);

					docuemnts.add(jsonArray.get(i).toString());
					missleniousDocuemnts.add(notRecivedDocuments);

				}
			} catch (Exception e) {
				logger.error("Error occured while  not get any Documents"+e.getMessage());
			}

			logger.debug("list of propertydocument needed "
					+ propertyDocuemnts.toString());
			logger.debug("list of downdocument needed "
					+ downPaymentDocuemnts.toString());

		}

		JSONObject oldSplited = null;
		try {
			oldSplited = couchBaseOperation
					.getCouchBaseData("DocumentSplitList" + opprtunity);
			JSONArray jsonArray = new JSONArray(oldSplited.get("splitedList")
					.toString());

			try {
				for (int i = 0; i < jsonArray.length(); i++) {

					JSONObject jsonData = couchBaseOperation
							.getCouchBaseData(jsonArray.get(i).toString());
					String document = jsonData.get("DocumentName").toString();

					if (document.equalsIgnoreCase(applicantname)) {
						ReceivedDocuments recived = new ReceivedDocuments();
						recived.setDocuementName(jsonData.get("DocumentType")
								.toString());

						recived.setSplitedDate(jsonData.get(
								"Submission_Date_Time1b").toString());
						recived.setDocumentTypeContent(jsonData.get(
								"documenTypeContent").toString());
						recived.setLink(jsonData.get(
								"OriginalDocumentAttachmentID").toString());
						recived.setSplitedLink(jsonData.get("ViewerURL")
								.toString());

						recived.setSplitedDocId(jsonData.get("URL").toString());
						applicantspliteddocuemnts.add(recived);
					} else if (document.equalsIgnoreCase(coApplicantName)) {

						ReceivedDocuments recived = new ReceivedDocuments();
						recived.setDocuementName(jsonData.get("DocumentType")
								.toString());
						recived.setDocumentTypeContent(jsonData.get(
								"documenTypeContent").toString());
						recived.setSplitedDate(jsonData.get(
								"Submission_Date_Time1b").toString());
						recived.setLink(jsonData.get(
								"OriginalDocumentAttachmentID").toString());
						recived.setSplitedLink(jsonData.get("ViewerURL")
								.toString());
						recived.setSplitedDocId(jsonData.get("URL").toString());
						coApplicantspliteddocuemnts.add(recived);
					} else if (document.equalsIgnoreCase("Property Documents")) {

						ReceivedDocuments recived = new ReceivedDocuments();
						recived.setDocuementName(jsonData.get("DocumentType")
								.toString());
						recived.setDocumentTypeContent(jsonData.get(
								"documenTypeContent").toString());
						recived.setSplitedDate(jsonData.get(
								"Submission_Date_Time1b").toString());
						recived.setLink(jsonData.get(
								"OriginalDocumentAttachmentID").toString());
						recived.setSplitedLink(jsonData.get("ViewerURL")
								.toString());
						recived.setSplitedDocId(jsonData.get("URL").toString());
						propertysplitedDocuemnts.add(recived);
					} else if (document
							.equalsIgnoreCase("DownPayment Documents")) {

						ReceivedDocuments recived = new ReceivedDocuments();
						recived.setDocuementName(jsonData.get("DocumentType")
								.toString());
						recived.setDocumentTypeContent(jsonData.get(
								"documenTypeContent").toString());
						recived.setSplitedDate(jsonData.get(
								"Submission_Date_Time1b").toString());
						recived.setLink(jsonData.get(
								"OriginalDocumentAttachmentID").toString());
						recived.setSplitedLink(jsonData.get("ViewerURL")
								.toString());
						recived.setSplitedDocId(jsonData.get("URL").toString());
						downPaymentsplitedDocuemnts.add(recived);
					}

					else if (document
							.equalsIgnoreCase("Miscellaneous Documents")) {

						ReceivedDocuments recived = new ReceivedDocuments();
						recived.setDocuementName(jsonData.get("DocumentType")
								.toString());
						recived.setDocumentTypeContent(jsonData.get(
								"documenTypeContent").toString());
						recived.setSplitedDate(jsonData.get(
								"Submission_Date_Time1b").toString());
						recived.setLink(jsonData.get(
								"OriginalDocumentAttachmentID").toString());
						recived.setSplitedLink(jsonData.get("ViewerURL")
								.toString());
						recived.setSplitedDocId(jsonData.get("URL").toString());
						misslenioussplitedDocuemnts.add(recived);
					}

				}

			} catch (Exception e) {
				logger.error("Error occured while equalsIgnoreCase of documents  "+e.getMessage());
			}
		} catch (Exception e) {
			logger.error("Error occured while equalsIgnoreCase of documents "+e.getMessage());
		}

		property.add("Property Documents");

		downpayment.add("DownPayment Documents");

		misslenious.add("Miscellaneous Documents");

		logger.debug("applcantdocsize :" + applicantdocuemnts.size()
				+ "applicantsplited docSize:"
				+ applicantspliteddocuemnts.size());

		if (applicantdocuemnts.size() != applicantspliteddocuemnts.size()) {
			for (int j = applicantspliteddocuemnts.size(); j < applicantdocuemnts
					.size(); j++) {
				ReceivedDocuments receivedDocuments = new ReceivedDocuments();
				receivedDocuments.setDocuementName("-");
				receivedDocuments.setSplitedDate("-");
				receivedDocuments.setLink("#");
				receivedDocuments.setRecived(0);

				applicantspliteddocuemnts.add(receivedDocuments);
			}

		}

		logger.debug("coApplicantdocuemnts :"
				+ coApplicantdocuemnts.size()
				+ "coApplicantspliteddocuemnts docSize:"
				+ coApplicantspliteddocuemnts.size());

		if (coApplicantdocuemnts.size() != coApplicantspliteddocuemnts.size()) {
			for (int j = coApplicantspliteddocuemnts.size(); j < coApplicantdocuemnts
					.size(); j++) {
				ReceivedDocuments receivedDocuments = new ReceivedDocuments();
				receivedDocuments.setDocuementName("-");
				receivedDocuments.setSplitedDate("-");
				receivedDocuments.setLink("#");
				receivedDocuments.setRecived(0);

				coApplicantspliteddocuemnts.add(receivedDocuments);
			}

		}

		logger.debug("coApplicantdocuemnts :"
				+ coApplicantdocuemnts.size()
				+ "coApplicantspliteddocuemnts docSize:"
				+ coApplicantspliteddocuemnts.size());

		logger.debug("propertyDocuemnts :" + propertyDocuemnts.size()
				+ "propertysplitedDocuemnts docSize:"
				+ propertysplitedDocuemnts.size());

		if (propertyDocuemnts.size() != propertysplitedDocuemnts.size()) {
			for (int j = propertysplitedDocuemnts.size(); j < propertyDocuemnts
					.size(); j++) {
				ReceivedDocuments receivedDocuments = new ReceivedDocuments();
				receivedDocuments.setDocuementName("-");
				receivedDocuments.setSplitedDate("-");
				receivedDocuments.setLink("#");
				receivedDocuments.setRecived(0);

				propertysplitedDocuemnts.add(receivedDocuments);
			}

		}
		logger.debug("propertyDocuemnts :" + propertyDocuemnts.size()
				+ "propertysplitedDocuemnts docSize:"
				+ propertysplitedDocuemnts.size());

		logger.debug("downPaymentDocuemnts :"
				+ downPaymentDocuemnts.size()
				+ "downPaymentsplitedDocuemnts docSize:"
				+ downPaymentsplitedDocuemnts.size());

		if (downPaymentDocuemnts.size() != downPaymentsplitedDocuemnts.size()) {
			for (int j = downPaymentsplitedDocuemnts.size(); j < downPaymentDocuemnts
					.size(); j++) {
				ReceivedDocuments receivedDocuments = new ReceivedDocuments();
				receivedDocuments.setDocuementName("-");
				receivedDocuments.setSplitedDate("-");
				receivedDocuments.setLink("#");
				receivedDocuments.setRecived(0);

				downPaymentsplitedDocuemnts.add(receivedDocuments);
			}

		}
		logger.debug("downPaymentDocuemnts :"
				+ downPaymentDocuemnts.size()
				+ "downPaymentsplitedDocuemnts docSize:"
				+ downPaymentsplitedDocuemnts.size());

		logger.debug("missleniousDocuemnts :"
				+ missleniousDocuemnts.size()
				+ "misslenioussplitedDocuemnts docSize:"
				+ misslenioussplitedDocuemnts.size());

		if (missleniousDocuemnts.size() != misslenioussplitedDocuemnts.size()) {
			for (int j = misslenioussplitedDocuemnts.size(); j < missleniousDocuemnts
					.size(); j++) {
				ReceivedDocuments receivedDocuments = new ReceivedDocuments();
				receivedDocuments.setDocuementName("-");
				receivedDocuments.setSplitedDate("-");
				receivedDocuments.setLink("#");
				receivedDocuments.setRecived(0);

				misslenioussplitedDocuemnts.add(receivedDocuments);
			}

		}

		// check for not recived applicantdocuemnts

		int size = applicantdocuemnts.size();
		int size2 = applicantspliteddocuemnts.size();
		int downlaod = 0;
		ArrayList<ReceivedDocuments> recivedApplicantDocarrayList = new ArrayList<ReceivedDocuments>();
		/*
		 * for(int y=0;y<size;y++){ ReceivedDocuments receivedDocuments = new
		 * ReceivedDocuments(); receivedDocuments.setDocuementName("-");
		 * receivedDocuments.setSplitedDate("-");
		 * receivedDocuments.setLink("#");
		 * recivedApplicantDocarrayList.add(receivedDocuments); }
		 */
		for (int k = 0; k < size; k++) {
			NotRecivedDocuments notrecived = applicantdocuemnts.get(k);
			logger.debug("applicant docuemnts " + k);

			ReceivedDocuments recivedDocuements = new ReceivedDocuments();
			recivedDocuements.setDocuementName(notrecived.getDocumentName());

			for (int z = 0; z < size2; z++) {
				ReceivedDocuments recieved = applicantspliteddocuemnts.get(z);

				logger.debug("not recived  " + z);
				recivedDocuements.setRecived(0);
				recivedDocuements.setRecivedDocuments(recieved
						.getDocuementName());
				recivedDocuements.setSplitedDate(recieved.getSplitedDate());
				recivedDocuements.setLink(recieved.getLink());
				recivedDocuements.setSplitedLink(recieved.getSplitedLink());
				recivedDocuements.setSplitedDocId(recieved.getSplitedDocId());
				if (notrecived.getDocumentName().equalsIgnoreCase(
						recieved.getDocumentTypeContent())) {
					recivedDocuements.setRecived(1);
					recivedDocuements.setRecivedDocuments(recieved
							.getDocuementName());
					if (downlaod == 0) {
						downlaod = 1;
					}
					recivedDocuements.setSplitedDate(recieved.getSplitedDate());
					recivedDocuements.setLink(recieved.getLink());
					recivedDocuements.setSplitedLink(recieved.getSplitedLink());
					recivedDocuements.setSplitedDocId(recieved
							.getSplitedDocId());
					// applicantdocuemnts.add(k, notrecived);
					break;
				}

			}

			recivedApplicantDocarrayList.add(recivedDocuements);

		}
		logger.debug("size of array list "
				+ recivedApplicantDocarrayList.size());

		size = coApplicantdocuemnts.size();
		size2 = coApplicantspliteddocuemnts.size();

		ArrayList<ReceivedDocuments> recivedCoApplicantDocarrayList = new ArrayList<ReceivedDocuments>();

		for (int k = 0; k < size; k++) {
			NotRecivedDocuments notrecived = coApplicantdocuemnts.get(k);
			logger.debug("applicant docuemnts " + k);

			ReceivedDocuments recivedDocuements = new ReceivedDocuments();
			recivedDocuements.setDocuementName(notrecived.getDocumentName());

			for (int z = 0; z < size2; z++) {
				ReceivedDocuments recieved = coApplicantspliteddocuemnts.get(z);

				logger.debug("not recived  " + z);
				recivedDocuements.setRecived(0);
				recivedDocuements.setRecivedDocuments(recieved
						.getDocuementName());
				recivedDocuements.setSplitedDate(recieved.getSplitedDate());
				recivedDocuements.setLink(recieved.getLink());
				recivedDocuements.setSplitedLink(recieved.getSplitedLink());
				recivedDocuements.setSplitedDocId(recieved.getSplitedDocId());
				if (notrecived.getDocumentName().equalsIgnoreCase(
						recieved.getDocumentTypeContent())) {
					recivedDocuements.setRecived(1);
					recivedDocuements.setRecivedDocuments(recieved
							.getDocuementName());
					if (downlaod == 0) {
						downlaod = 1;
					}
					recivedDocuements.setSplitedDate(recieved.getSplitedDate());
					recivedDocuements.setLink(recieved.getLink());
					recivedDocuements.setSplitedLink(recieved.getSplitedLink());
					recivedDocuements.setSplitedDocId(recieved
							.getSplitedDocId());
					// applicantdocuemnts.add(k, notrecived);
					break;
				}

			}

			recivedCoApplicantDocarrayList.add(recivedDocuements);

		}

		size = propertyDocuemnts.size();
		size2 = propertysplitedDocuemnts.size();
		ArrayList<ReceivedDocuments> recivedpropertyDocuemntsarrayList = new ArrayList<ReceivedDocuments>();

		for (int k = 0; k < size; k++) {
			NotRecivedDocuments notrecived = propertyDocuemnts.get(k);
			logger.debug("applicant docuemnts " + k);

			ReceivedDocuments recivedDocuements = new ReceivedDocuments();
			recivedDocuements.setDocuementName(notrecived.getDocumentName());

			for (int z = 0; z < size2; z++) {
				ReceivedDocuments recieved = propertysplitedDocuemnts.get(z);

				logger.debug("not recived  " + z);
				recivedDocuements.setRecived(0);
				recivedDocuements.setRecivedDocuments(recieved
						.getDocuementName());
				recivedDocuements.setSplitedDate(recieved.getSplitedDate());
				recivedDocuements.setLink(recieved.getLink());
				recivedDocuements.setSplitedLink(recieved.getSplitedLink());
				recivedDocuements.setSplitedDocId(recieved.getSplitedDocId());

				if (notrecived.getDocumentName().equalsIgnoreCase(
						recieved.getDocumentTypeContent())) {
					recivedDocuements.setRecived(1);
					recivedDocuements.setRecivedDocuments(recieved
							.getDocuementName());
					if (downlaod == 0) {
						downlaod = 1;
					}
					recivedDocuements.setSplitedDate(recieved.getSplitedDate());
					recivedDocuements.setLink(recieved.getLink());
					recivedDocuements.setSplitedLink(recieved.getSplitedLink());
					recivedDocuements.setSplitedDocId(recieved
							.getSplitedDocId());
					// applicantdocuemnts.add(k, notrecived);
					break;
				}

			}

			recivedpropertyDocuemntsarrayList.add(recivedDocuements);

		}

		size = downPaymentDocuemnts.size();
		size2 = downPaymentsplitedDocuemnts.size();
		ArrayList<ReceivedDocuments> reciveddownPaymentDocuemntsarrayList = new ArrayList<ReceivedDocuments>();

		for (int k = 0; k < size; k++) {
			NotRecivedDocuments notrecived = downPaymentDocuemnts.get(k);
			logger.debug("applicant docuemnts " + k);

			ReceivedDocuments recivedDocuements = new ReceivedDocuments();
			recivedDocuements.setDocuementName(notrecived.getDocumentName());

			for (int z = 0; z < size2; z++) {
				ReceivedDocuments recieved = downPaymentsplitedDocuemnts.get(z);

				logger.debug("not recived  " + z);
				recivedDocuements.setRecived(0);
				recivedDocuements.setRecivedDocuments(recieved
						.getDocuementName());
				recivedDocuements.setSplitedDate(recieved.getSplitedDate());
				recivedDocuements.setLink(recieved.getLink());
				recivedDocuements.setSplitedLink(recieved.getSplitedLink());

				recivedDocuements.setSplitedDocId(recieved.getSplitedDocId());
				if (notrecived.getDocumentName().equalsIgnoreCase(
						recieved.getDocumentTypeContent())) {
					recivedDocuements.setRecived(1);
					recivedDocuements.setRecivedDocuments(recieved
							.getDocuementName());
					if (downlaod == 0) {
						downlaod = 1;
					}
					recivedDocuements.setSplitedDate(recieved.getSplitedDate());
					recivedDocuements.setLink(recieved.getLink());
					recivedDocuements.setSplitedLink(recieved.getSplitedLink());
					recivedDocuements.setSplitedDocId(recieved
							.getSplitedDocId());
					// applicantdocuemnts.add(k, notrecived);
					break;
				}

			}

			reciveddownPaymentDocuemntsarrayList.add(recivedDocuements);

		}

		size = missleniousDocuemnts.size();
		size2 = misslenioussplitedDocuemnts.size();
		ArrayList<ReceivedDocuments> recivedmissleniousDocuemntsarrayList = new ArrayList<ReceivedDocuments>();

		for (int k = 0; k < size; k++) {
			NotRecivedDocuments notrecived = missleniousDocuemnts.get(k);
			logger.debug("applicant docuemnts " + k);

			ReceivedDocuments recivedDocuements = new ReceivedDocuments();
			recivedDocuements.setDocuementName(notrecived.getDocumentName());

			for (int z = 0; z < size2; z++) {
				ReceivedDocuments recieved = misslenioussplitedDocuemnts.get(z);

				logger.debug("not recived  " + z);
				recivedDocuements.setRecived(0);
				recivedDocuements.setRecivedDocuments(recieved
						.getDocuementName());
				recivedDocuements.setSplitedDate(recieved.getSplitedDate());
				recivedDocuements.setLink(recieved.getLink());
				recivedDocuements.setSplitedLink(recieved.getSplitedLink());
				recivedDocuements.setSplitedDocId(recieved.getSplitedDocId());

				if (notrecived.getDocumentName().equalsIgnoreCase(
						recieved.getDocumentTypeContent())) {
					recivedDocuements.setRecived(1);
					recivedDocuements.setRecivedDocuments(recieved
							.getDocuementName());
					if (downlaod == 0) {
						downlaod = 1;
					}
					recivedDocuements.setSplitedDate(recieved.getSplitedDate());
					recivedDocuements.setLink(recieved.getLink());
					recivedDocuements.setSplitedLink(recieved.getSplitedLink());
					recivedDocuements.setSplitedDocId(recieved
							.getSplitedDocId());
					// applicantdocuemnts.add(k, notrecived);
					break;
				}

			}

			recivedmissleniousDocuemntsarrayList.add(recivedDocuements);

		}
		logger.debug("missleniousDocuemnts :"
				+ missleniousDocuemnts.size()
				+ "misslenioussplitedDocuemnts docSize:"
				+ misslenioussplitedDocuemnts.size());

		logger.debug(" afterdocumentSize " + applicantdocuemnts.size()
				+ "splitedsize " + applicantspliteddocuemnts.size());

		return ok(doc.render(opportunityName, OpportunityList, applicants,
				coapplicants, property, downpayment, misslenious,
				recivedApplicantDocarrayList, recivedCoApplicantDocarrayList,
				recivedpropertyDocuemntsarrayList,
				reciveddownPaymentDocuemntsarrayList,
				recivedmissleniousDocuemntsarrayList, opprtunity, downlaod,
				docuemnts));

	}

	public static Result data() {
		JsonNode json = request().body().asJson();

		logger.debug("inde-------------------------------------->" + json);
		ArrayList<String> docuemnts = new ArrayList<String>();

		try {
			String applicantname = "";
			String coApplicantName = "";

			JSONObject jsonObject = new JSONObject(json.toString());
			String opporunityName = jsonObject.get("oppo").toString();
			String applicant = jsonObject.getString("data").toString();
			logger.debug("inde-------------------------------------->"
					+ opporunityName);

			logger.debug("inde-------------------------------------->"
					+ applicant);

			Opprtunity opporunitysearchedData = new Odoo()
					.getOpprtunityData(opporunityName);

			JSONObject doclist = null;
			try {
				doclist = new CouchBaseOperation()
						.getCouchBaseData("DocumentListOfApplicationNo_"
								+ opporunitysearchedData.getOpprtunityId());
				logger.debug("size of documents " + doclist.length());

			} catch (Exception e) {
	    logger.error("Error occured in data of document "+e.getMessage());
			}

			if (opporunitysearchedData.getApplicants() != null) {
				Set<Applicants> applicantss = opporunitysearchedData
						.getApplicants();

				int k = 0;
				Iterator<Applicants> appl = applicantss.iterator();
				while (appl.hasNext()) {
					Applicants applican = appl.next();

					if (k == 1) {
						coApplicantName = applican.getApplicantName();
					} else if (k == 0) {
						applicantname = applican.getApplicantName();

					}
					k++;
				}

			}

			switch (applicant) {
			case "Property Documents":
				try {
					JSONArray jsonArray = new JSONArray(doclist.get(
							"propertyDocments").toString());
					for (int i = 0; i < jsonArray.length(); i++) {
						docuemnts.add(jsonArray.get(i).toString());

					}
				} catch (Exception e) {
		logger.error("Error occured in data of propertyDocments "+e.getMessage());
				}
				break;

			case "DownPayment Documents":

				try {
					JSONArray jsonArray = new JSONArray(doclist.get(
							"downPayments").toString());
					for (int i = 0; i < jsonArray.length(); i++) {
						docuemnts.add(jsonArray.get(i).toString());

					}
				} catch (Exception e) {
					logger.error("Error occured in data of DownPayment Documents "+e.getMessage());
				}

				break;
			case "Miscellaneous Documents":

				try {
					JSONArray jsonArray = new JSONArray(doclist.get(
							"MiscellaneousDocuments").toString());
					for (int i = 0; i < jsonArray.length(); i++) {
						docuemnts.add(jsonArray.get(i).toString());

					}
				} catch (Exception e) {
					logger.error("Error occured in data of  MiscellaneousDocuments "+e.getMessage());
				}
				break;

			default:
				if (applicant.equalsIgnoreCase(applicantname)) {
					try {
						JSONArray jsonArray = new JSONArray(doclist.get(
								"documents").toString());
						for (int i = 0; i < jsonArray.length(); i++) {
							docuemnts.add(jsonArray.get(i).toString());
						}
					} catch (Exception e) {
						logger.error("Error occured in data adding to documents"+e.getMessage());
					}
				} else if (applicant.equalsIgnoreCase(coApplicantName)) {

					try {
						JSONArray jsonArray = new JSONArray(doclist.get(
								"co_documents").toString());
						for (int i = 0; i < jsonArray.length(); i++) {
							docuemnts.add(jsonArray.get(i).toString());
						}
					} catch (Exception e) {
						logger.error("Error occured in documents coapplicant name equals"+e.getMessage());
					}

				}

				break;
			}

		} catch (Exception e) {
			logger.error("Error occured in documents applicant name equals"+e.getMessage());
		}

		return ok(Json.toJson(docuemnts));
	}

	public static Result searchOpporunity() throws JSONException {
		String opprtunity = "";
		String id = "";
		String attachment = "";
		String opporunityName = "";
		// id = "Inbox_kumar.vikash005@gmail.com_2015-08-18 08:14:01";

		DynamicForm dynamicForm = form().bindFromRequest();
		opporunityName = dynamicForm.get("opportunity");
		id = dynamicForm.get("id");
		logger.debug("id---------------------------------->" + id);
		logger.debug("opportunity---------------------------------->"
				+ opporunityName);

		ArrayList<String> applicantsDisplay = new ArrayList<String>();

		ArrayList<String> coapplicantsDisply = new ArrayList<String>();

		ArrayList<String> applicantsDocuments = new ArrayList<String>();
		ArrayList<String> coapplicantsDocuments = new ArrayList<String>();

		ArrayList<String> propertyDocuments = new ArrayList<String>();

		ArrayList<String> downpaymentDocuments = new ArrayList<String>();

		ArrayList<String> missleneusDocuments = new ArrayList<String>();

		ArrayList<String> applicants = new ArrayList<String>();
		ArrayList<String> docuemnts = new ArrayList<String>();
		CouchBaseOperation couchBaseOperation = new CouchBaseOperation();
		ArrayList<DocoSolo> splitedList = new ArrayList<DocoSolo>();

		String opportunityName = "";
		JSONObject jsonObject = null;
		Odoo odoo = new Odoo();
		ArrayList<OpportunityList> OpportunityList = odoo.getOpprunityDetials();

		String typeProject = "";
		try {
			jsonObject = couchBaseOperation.getCouchBaseData(id);
			typeProject = jsonObject.get("Type").toString();
		} catch (Exception e) {
			logger.error("Error occured in searchOpporunity"+e.getMessage());

		}

		Opprtunity opporunitysearchedData = odoo
				.getOpprtunityData(opporunityName);

		if (opporunitysearchedData != null) {
			opprtunity = opporunitysearchedData.getOpprtunityId();
			opportunityName = opporunitysearchedData.getOpprtunityName();
			if (opporunitysearchedData.getApplicants() != null) {
				Set<Applicants> applicantss = opporunitysearchedData
						.getApplicants();

				Iterator<Applicants> appl = applicantss.iterator();
				int i = 0;
				while (appl.hasNext()) {
					Applicants applican = appl.next();
					applicants.add(applican.getApplicantName());
					++i;
					if (i == 1) {
						applicantsDisplay.add(applican.getApplicantName());
					} else if (i == 2) {
						coapplicantsDisply.add(applican.getApplicantName());

					}

				}

			}

			logger.debug("json object " + jsonObject);

		}

		applicants.add("Property Documents");
		applicants.add("DownPayment Documents");
		applicants.add("Miscellaneous Documents");

		JSONObject doclist = null;
		try {
			doclist = couchBaseOperation
					.getCouchBaseData("DocumentListOfApplicationNo_"
							+ opprtunity);
			logger.debug("size of documents " + doclist.length());

		} catch (Exception e) {
			logger.error("Error occured while searching documents"+e.getMessage());
		}

		JSONObject jsonAttachmentKey = null;

		try {
			jsonAttachmentKey = couchBaseOperation.getCouchBaseData(jsonObject
					.get("BinaryID").toString());
			attachment = jsonAttachmentKey.get("attachement").toString();

		} catch (Exception e) {
			logger.error("Error occured in searching documents using JSON"+e.getMessage());
		}

		if (doclist != null) {
			try {
				JSONArray jsonArray = new JSONArray(doclist.get("documents")
						.toString());
				for (int i = 0; i < jsonArray.length(); i++) {
					docuemnts.add(jsonArray.get(i).toString());
					applicantsDocuments.add(jsonArray.get(i).toString());

				}
			} catch (Exception e) {
				logger.error("Error occured in searching documents using JSON"+e.getMessage());
			}

			try {
				JSONArray jsonArray = new JSONArray(doclist.get("co_documents")
						.toString());
				for (int i = 0; i < jsonArray.length(); i++) {
					docuemnts.add(jsonArray.get(i).toString());
					coapplicantsDocuments.add(jsonArray.get(i).toString());
				}
			} catch (Exception e) {
				logger.error("Error occured in searching co_documents using JSON"+e.getMessage());
			}

			try {
				JSONArray jsonArray = new JSONArray(doclist.get(
						"propertyDocments").toString());
				for (int i = 0; i < jsonArray.length(); i++) {
					docuemnts.add(jsonArray.get(i).toString());
					propertyDocuments.add(jsonArray.get(i).toString());

				}
			} catch (Exception e) {
				logger.error("Error occured in searching propertyDocments using JSON"+e.getMessage());
			}

			try {
				JSONArray jsonArray = new JSONArray(doclist.get("downPayments")
						.toString());
				for (int i = 0; i < jsonArray.length(); i++) {
					docuemnts.add(jsonArray.get(i).toString());
					downpaymentDocuments.add(jsonArray.get(i).toString());

				}
			} catch (Exception e) {
				logger.error("Error occured in searching downPaymentsdocument using JSON"+e.getMessage());
			}

			try {
				JSONArray jsonArray = new JSONArray(doclist.get(
						"MiscellaneousDocuments").toString());
				for (int i = 0; i < jsonArray.length(); i++) {
					docuemnts.add(jsonArray.get(i).toString());
					missleneusDocuments.add(jsonArray.get(i).toString());

				}
			} catch (Exception e) {
				logger.error("Error occured in searching MiscellaneousDocuments using JSON"+e.getMessage());
			}

		}
		logger.debug("list of document needed " + docuemnts.toString());

		JSONObject oldSplited = null;
		try {
			oldSplited = couchBaseOperation
					.getCouchBaseData("DocumentSplitList" + opprtunity);
			JSONArray jsonArray = new JSONArray(oldSplited.get("splitedList")
					.toString());

			try {
				for (int i = 0; i < jsonArray.length(); i++) {

					DocoSolo docData = new DocoSolo();
					JSONObject jsonData = couchBaseOperation
							.getCouchBaseData(jsonArray.get(i).toString());
					docData.setDocId(jsonArray.get(i).toString());
					docData.setRelatedDoc(jsonData.get("DocumentName")
							.toString());
					docData.setDocumentType(jsonData.get("DocumentType")
							.toString());
					docData.setLink(jsonData.get("ViewerURL").toString());
					splitedList.add(docData);
				}

			} catch (Exception e) {
				logger.error("Error occured in searching Documents"+e.getMessage());
			}
		} catch (Exception e) {
			logger.error("Error occured in searching Documents"+e.getMessage());
		}
		logger.debug("id " + id);

		// attachment
		return ok(index.render(attachment, OpportunityList, applicants,
				applicantsDisplay, coapplicantsDisply, applicantsDocuments,
				coapplicantsDocuments, propertyDocuments, downpaymentDocuments,
				missleneusDocuments, opportunityName, id, 1, splitedList, 1, 1));

	}

	public static Result download() throws Exception {
		String id = request().getQueryString("Id");
		CompressZipFolderExample compres = new CompressZipFolderExample();
		ArrayList<String> file = compres.download(id);
		String fileName = "";
		String filePath = "";
		String folder = "";
		File filefolder = null;
		try {
			fileName = file.get(0);
			folder = file.get(1);
			filePath = file.get(2);
		} catch (Exception e) {

		}
		File file2 = null;
		FileInputStream file3 = null;
		try {

			file2 = new File(filePath);
			file3 = new FileInputStream(filePath);

			filefolder = new File(folder);
			compres.delete(file2);
			compres.delete(filefolder);

		} catch (Exception e) {
			logger.error("Error occured in Download"+e.getMessage());
		}
		logger.debug("fileNAme " + fileName);
		logger.debug("filepath " + filePath);
		response().setContentType("application/x-download");
		response().setHeader("Content-disposition",
				"attachment; filename=" + fileName);
		return ok(file3);
	}

	public static Result getDocument() throws JSONException, IOException {
		JsonNode json = request().body().asJson();
		logger.debug("inside getDocumentMethod reciving  json data");
		if (json == null) {
			logger.debug("inside getDocumentMethod not json data");

			return badRequest("Expecting Json data");
		} else {
			String documentId = json.findPath("id").textValue();
			if (documentId == null) {

				return badRequest("Missing parameter [id]");
			} else {

				ApplicantDocument applicantDocumentList = new MortgageDocumentConverter()
						.convertToApplicantDocument(documentId);
				return ok("Hello " + documentId);
			}
		}
	}

	public static Result deleteSplitDocument() throws JSONException {

		DynamicForm dynamicForm = form().bindFromRequest();

		String splitdocid = dynamicForm.get("spliteddocid");
		String id = dynamicForm.get("originalid");
		String opporunityName = dynamicForm.get("opportunity");

		logger.debug("document id " + splitdocid);
		CouchBaseOperation couchBaseOperation = new CouchBaseOperation();
		try {
			couchBaseOperation.deleteCouchBaseData(splitdocid);

			Thread.sleep(3000);

		} catch (Exception e) {
			logger.error("Error occured in deleteSplitDocument"+e.getMessage());
		}

		String opprtunity = "";

		/*
		 * String id = "Inbox_kumar.vikash005@gmail.com_2015-08-18 08:14:01";
		 */// request().getQueryString("id");
		ArrayList<String> applicants = new ArrayList<String>();
		ArrayList<String> docuemnts = new ArrayList<String>();
		ArrayList<DocoSolo> splitedList = new ArrayList<DocoSolo>();

		ArrayList<String> applicantsDisplay = new ArrayList<String>();

		ArrayList<String> coapplicantsDisply = new ArrayList<String>();

		ArrayList<String> applicantsDocuments = new ArrayList<String>();
		ArrayList<String> coapplicantsDocuments = new ArrayList<String>();

		ArrayList<String> propertyDocuments = new ArrayList<String>();

		ArrayList<String> downpaymentDocuments = new ArrayList<String>();

		ArrayList<String> missleneusDocuments = new ArrayList<String>();

		String opportunityName = "";
		JSONObject jsonObject = null;
		try {
			jsonObject = couchBaseOperation.getCouchBaseData(id);
		} catch (Exception e) {
			logger.error("Error occured in deleteSplitDocument"+e.getMessage());

		}
		Odoo odoo = new Odoo();
		ArrayList<OpportunityList> OpportunityList = odoo.getOpprunityDetials();

		Opprtunity opporunitysearchedData = odoo
				.getOpprtunityData(opporunityName);

		if (opporunitysearchedData != null) {
			opprtunity = opporunitysearchedData.getOpprtunityId();
			opportunityName = opporunitysearchedData.getOpprtunityName();
			if (opporunitysearchedData.getApplicants() != null) {
				Set<Applicants> applicantss = opporunitysearchedData
						.getApplicants();

				Iterator<Applicants> appl = applicantss.iterator();
				int i = 0;
				while (appl.hasNext()) {
					Applicants applican = appl.next();
					applicants.add(applican.getApplicantName());
					++i;
					if (i == 1) {
						applicantsDisplay.add(applican.getApplicantName());

					} else if (i == 2) {
						coapplicantsDisply.add(applican.getApplicantName());

					}
				}

			}
		}

		JSONObject oldSplited1 = null;
		try {
			oldSplited1 = couchBaseOperation
					.getCouchBaseData("DocumentSplitList" + opprtunity);
			JSONArray jsonArray = new JSONArray(oldSplited1.get("splitedList")
					.toString());
			logger.debug("-----------------------------------splted doc------------"
					+ jsonArray);

			try {

				JSONArray json = new JSONArray();
				for (int i = 0; i < jsonArray.length(); i++) {

					logger.debug("-----------------------------------splted doc------------"
							+ jsonArray.get(i).toString());
					if (!splitdocid.equalsIgnoreCase(jsonArray.get(i)
							.toString())) {
						json.put(jsonArray.get(i).toString());
					}

				}
				logger.info("-----------------------------------splted doc------------"
						+ json);

				oldSplited1.put("splitedList", json);
				couchBaseOperation.editDataInCouchBase("DocumentSplitList"
						+ opprtunity, oldSplited1);

			} catch (Exception e) {
				logger.error("Error occured in deleteSplitDocument"+e.getMessage());
			}
		} catch (Exception e) {
			logger.error("Error occured in deleteSplitDocument"+e.getMessage());
		}

		try {
			int i = 0;
			JSONObject originalJsonData = couchBaseOperation
					.getCouchBaseData(id);
			try {
				i = originalJsonData.getInt("TimesSplit");
			} catch (Exception e) {

			}
			if(i>0){
			i = i - 1;
			}
			originalJsonData.put("TimesSplit", i);
			couchBaseOperation.editDataInCouchBase(id, originalJsonData);
		} catch (Exception e) {
			logger.error("Error occured in time SplitDocument"+e.getMessage());
		}
		applicants.add("Property Documents");
		applicants.add("DownPayment Documents");
		applicants.add("Miscellaneous Documents");

		JSONObject doclist = null;
		try {
			doclist = couchBaseOperation
					.getCouchBaseData("DocumentListOfApplicationNo_"
							+ opprtunity);
		} catch (Exception e) {
			logger.error("Error occured in delete Documents by individually"+e.getMessage());
		}
		JSONObject jsonAttachmentKey = null;
		String attachment = null;

		try {
			jsonAttachmentKey = couchBaseOperation.getCouchBaseData(jsonObject
					.get("BinaryID").toString());
			attachment = jsonAttachmentKey.get("attachement").toString();

		} catch (Exception e) {
			logger.error("Error occured in delete Documents getting BinaryId"+e.getMessage());
		}

		if (doclist != null) {
			try {
				JSONArray jsonArray = new JSONArray(doclist.get("documents")
						.toString());
				for (int i = 0; i < jsonArray.length(); i++) {
					docuemnts.add(jsonArray.get(i).toString());
					applicantsDocuments.add(jsonArray.get(i).toString());
				}
			} catch (Exception e) {
				logger.error("Error occured in delete Documents"+e.getMessage());
			}

			try {
				JSONArray jsonArray = new JSONArray(doclist.get("co_documents")
						.toString());
				for (int i = 0; i < jsonArray.length(); i++) {
					docuemnts.add(jsonArray.get(i).toString());
					coapplicantsDocuments.add(jsonArray.get(i).toString());
				}
			} catch (Exception e) {
				logger.error("Error occured in delete Co-Documents"+e.getMessage());
			}

			try {
				JSONArray jsonArray = new JSONArray(doclist.get(
						"propertyDocments").toString());
				for (int i = 0; i < jsonArray.length(); i++) {
					docuemnts.add(jsonArray.get(i).toString());
					propertyDocuments.add(jsonArray.get(i).toString());

				}
			} catch (Exception e) {
				logger.error("Error occured in delete propertyDocuments"+e.getMessage());
			}

			try {
				JSONArray jsonArray = new JSONArray(doclist.get("downPayments")
						.toString());
				for (int i = 0; i < jsonArray.length(); i++) {
					docuemnts.add(jsonArray.get(i).toString());
					downpaymentDocuments.add(jsonArray.get(i).toString());

				}
			} catch (Exception e) {
				logger.error("Error occured to delete of downPayments"+e.getMessage());
			}

			try {
				JSONArray jsonArray = new JSONArray(doclist.get(
						"MiscellaneousDocuments").toString());
				for (int i = 0; i < jsonArray.length(); i++) {
					docuemnts.add(jsonArray.get(i).toString());
					missleneusDocuments.add(jsonArray.get(i).toString());

				}
			} catch (Exception e) {
				logger.error("Error occured to delete of MiscellaneousDocuments"+e.getMessage());
			}
		}

		JSONObject oldSplited = null;
		try {
			oldSplited = couchBaseOperation
					.getCouchBaseData("DocumentSplitList" + opprtunity);
			JSONArray jsonArray = new JSONArray(oldSplited.get("splitedList")
					.toString());

			try {
				for (int i = 0; i < jsonArray.length(); i++) {

					DocoSolo docData = new DocoSolo();
					JSONObject jsonData = couchBaseOperation
							.getCouchBaseData(jsonArray.get(i).toString());
					docData.setDocId(jsonArray.get(i).toString());
					docData.setRelatedDoc(jsonData.get("DocumentName")
							.toString());
					docData.setDocumentType(jsonData.get("DocumentType")
							.toString());
					docData.setLink(jsonData.get("ViewerURL").toString());
					splitedList.add(docData);
				}

			} catch (Exception e) {
				logger.error("Error occured to spilt Documents by type,Document Name"+e.getMessage());
			}
		} catch (Exception e) {
			logger.error("Error occured to spilt DocumentsList"+e.getMessage());
		}

		// attachment
		return ok(index.render(attachment, OpportunityList, applicants,
				applicantsDisplay, coapplicantsDisply, applicantsDocuments,
				coapplicantsDocuments, propertyDocuments, downpaymentDocuments,
				missleneusDocuments, opporunityName, id, 1, splitedList, 1, 1));

	}

	@SuppressWarnings("rawtypes")
	public static Result splitDocument() throws JSONException, IOException {

		CouchBaseOperation couchbase = new CouchBaseOperation();

		UUID id1 = UUID.randomUUID();
		String uniqueId = id1.toString();
		String opprtunity = "";

		ArrayList<DocoSolo> splitedList = new ArrayList<DocoSolo>();

		DynamicForm dynamicForm = form().bindFromRequest();

		String documentRelatedrecord = dynamicForm.get("pages");
		String relatedrecord = dynamicForm.get("relatedrecord");

		String documenTypeContent = dynamicForm.get("documenttype");

		String opportunityName = dynamicForm.get("opportunity");
		String recivedFromEmial = "";
		String id = dynamicForm.get("id");

		String originalCreationTime = "";
		String coApplicantName = "";
		String applicantname = "";
		logger.debug("id --------------------->" + id);
		logger.debug("id --------------------->" + documenTypeContent);

		ArrayList<String> applicants = new ArrayList<String>();
		ArrayList<String> docuemnts = new ArrayList<String>();
		CouchBaseOperation couchBaseOperation = new CouchBaseOperation();

		ArrayList<String> applicantsDisplay = new ArrayList<String>();

		ArrayList<String> coapplicantsDisply = new ArrayList<String>();

		ArrayList<String> applicantsDocuments = new ArrayList<String>();
		ArrayList<String> coapplicantsDocuments = new ArrayList<String>();

		ArrayList<String> propertyDocuments = new ArrayList<String>();

		ArrayList<String> downpaymentDocuments = new ArrayList<String>();

		ArrayList<String> missleneusDocuments = new ArrayList<String>();

		JSONObject jsonObject = null;
		try {
			jsonObject = couchBaseOperation.getCouchBaseData(id);
		} catch (Exception e) {
			logger.error("Error occured in connection"+e.getMessage());

		}
		Odoo odoo = new Odoo();
		ArrayList<OpportunityList> OpportunityList = odoo.getOpprunityDetials();

		Opprtunity opporunitysearchedData = odoo
				.getOpprtunityData(opportunityName);
String opportunityEmail="";
		if (opporunitysearchedData != null) {
			opprtunity = opporunitysearchedData.getOpprtunityId();
			opportunityName = opporunitysearchedData.getOpprtunityName();
			opportunityEmail=opporunitysearchedData.getOpprtunityEmail();
			if (opporunitysearchedData.getApplicants() != null) {
				Set<Applicants> applicantss = opporunitysearchedData
						.getApplicants();

				int k = 0;
				Iterator<Applicants> appl = applicantss.iterator();
				while (appl.hasNext()) {
					Applicants applican = appl.next();
					applicants.add(applican.getApplicantName());

					if (k == 1) {
						coApplicantName = applican.getApplicantName();
						coapplicantsDocuments.add(coApplicantName);
					} else if (k == 0) {
						applicantname = applican.getApplicantName();
						applicantsDisplay.add(applicantname);

					}
					k++;
				}

			}
		}

		applicants.add("Property Documents");
		applicants.add("DownPayment Documents");
		applicants.add("Miscellaneous Documents");
		JSONObject doclist = null;
		try {
			doclist = couchBaseOperation
					.getCouchBaseData("DocumentListOfApplicationNo_"
							+ opprtunity);

			originalCreationTime = doclist.get("Submission_Date_Time1b")
					.toString();

		} catch (Exception e) {
			logger.error("Error occured in while adding to Document couchbase"+e.getMessage());
		}
		JSONObject jsonAttachmentKey = null;
		String originalAttachment = null;
		String originalAttachmentId = null;
		try {

			originalAttachmentId = jsonObject.get("BinaryID").toString();
			jsonAttachmentKey = couchBaseOperation.getCouchBaseData(jsonObject
					.get("BinaryID").toString());
			originalAttachment = jsonAttachmentKey.get("attachement")
					.toString();

		} catch (Exception e) {
			logger.error("Error occured in search"+e.getMessage());
		}

		int properdocumentSelected = 0;

		if (doclist != null) {
			try {
				JSONArray jsonArray = new JSONArray(doclist.get("documents")
						.toString());
				for (int i = 0; i < jsonArray.length(); i++) {
					docuemnts.add(jsonArray.get(i).toString());

					applicantsDocuments.add(jsonArray.get(i).toString());
					if (relatedrecord.equalsIgnoreCase(applicantname)) {
						if (jsonArray.get(i).toString()
								.equalsIgnoreCase(documenTypeContent)) {
							properdocumentSelected = 1;
						}

					}
				}
			} catch (Exception e) {
				logger.error("Error occured in documentList null"+e.getMessage());
			}

			try {
				JSONArray jsonArray = new JSONArray(doclist.get("co_documents")
						.toString());
				for (int i = 0; i < jsonArray.length(); i++) {
					docuemnts.add(jsonArray.get(i).toString());
					coapplicantsDocuments.add(jsonArray.get(i).toString());

					if (relatedrecord.equalsIgnoreCase(coApplicantName)) {
						if (jsonArray.get(i).toString()
								.equalsIgnoreCase(documenTypeContent)) {
							properdocumentSelected = 1;
						}
					}
				}
			} catch (Exception e) {
				logger.error("Error occured in co_documentList null"+e.getMessage());
			}

			try {
				JSONArray jsonArray = new JSONArray(doclist.get(
						"propertyDocments").toString());
				for (int i = 0; i < jsonArray.length(); i++) {
					docuemnts.add(jsonArray.get(i).toString());

					propertyDocuments.add(jsonArray.get(i).toString());

					if (relatedrecord.equalsIgnoreCase("Property Documents")) {

						if (jsonArray.get(i).toString()
								.equalsIgnoreCase(documenTypeContent)) {
							properdocumentSelected = 1;
						}

					}
				}
			} catch (Exception e) {
				logger.error("Error occured while adding property documents"+e.getMessage());
			}

			try {
				JSONArray jsonArray = new JSONArray(doclist.get("downPayments")
						.toString());
				for (int i = 0; i < jsonArray.length(); i++) {
					docuemnts.add(jsonArray.get(i).toString());
					downpaymentDocuments.add(jsonArray.get(i).toString());

					if (relatedrecord.equalsIgnoreCase("DownPayment Documents")) {

						if (jsonArray.get(i).toString()
								.equalsIgnoreCase(documenTypeContent)) {
							properdocumentSelected = 1;
						}

					}

				}

			} catch (Exception e) {
				logger.error("Error occured while downpayment documents selection"+e.getMessage());
			}

			try {
				JSONArray jsonArray = new JSONArray(doclist.get(
						"MiscellaneousDocuments").toString());
				for (int i = 0; i < jsonArray.length(); i++) {
					docuemnts.add(jsonArray.get(i).toString());
					missleneusDocuments.add(jsonArray.get(i).toString());

					if (relatedrecord
							.equalsIgnoreCase("Miscellaneous Documents")) {

						if (jsonArray.get(i).toString()
								.equalsIgnoreCase(documenTypeContent)) {
							properdocumentSelected = 1;
						}

					}
				}
			} catch (Exception e) {
				logger.error("Error occured while Miscellaneous documents selection"+e.getMessage());
			}

		}
		logger.debug("proper document selected " + properdocumentSelected);
		int splitedSucessTrue = 1;
		JSONObject oldSplited = null;
		try {
			oldSplited = couchbase.getCouchBaseData("DocumentSplitList"
					+ opprtunity);
			JSONArray jsonArray = new JSONArray(oldSplited.get("splitedList")
					.toString());

			try {

				for (int i = 0; i < jsonArray.length(); i++) {

					DocoSolo docData = new DocoSolo();
					JSONObject jsonData = couchbase.getCouchBaseData(jsonArray
							.get(i).toString());
					docData.setDocId(jsonArray.get(i).toString());
					docData.setRelatedDoc(jsonData.get("DocumentName")
							.toString());
					docData.setDocumentType(jsonData.get("DocumentType")
							.toString());
					docData.setLink(jsonData.get("ViewerURL").toString());
					splitedList.add(docData);

					if (relatedrecord.equalsIgnoreCase(jsonData.get(
							"DocumentName").toString())) {

						if (documenTypeContent.equalsIgnoreCase(jsonData.get(
								"documenTypeContent").toString())) {
							splitedSucessTrue = 0;

						}
					}
				}

			} catch (Exception e) {
				logger.error("Error occured while not get DocumentSplitList"+e.getMessage());
			}
		} catch (Exception e) {
			logger.error("Error occured while not get DocumentName"+e.getMessage());
		}

		if (splitedSucessTrue == 1) {
			if (properdocumentSelected == 1) {
				int fromPage = 0;
				int toPage = 0;
				boolean givenproperpages = true;
				ArrayList<Pages> pagesList = new ArrayList<Pages>();
				try {
					logger.debug("document split " + documentRelatedrecord);
					String array[] = documentRelatedrecord.split(",");

					logger.info("document split size " + array.length);

					for (int i = 0; i < array.length; i++) {
						Pages page = new Pages();
						try {

							String array1[] = array[i].split("-");

							fromPage = Integer.parseInt(array1[0]);
							toPage = Integer.parseInt(array1[1]);
						} catch (Exception e) {
							logger.error("Error occured if Document not spilt"+e.getMessage());
						}
						logger.debug("from page " + fromPage + "to page "
								+ toPage);
						if (fromPage == 0) {
							givenproperpages = false;

							break;
						}
						page.setFromPage(fromPage);
						page.setToPage(toPage);
						fromPage = 0;
						toPage = 0;
						pagesList.add(page);

					}

				} catch (Exception e) {
					logger.error("Error occured if Document not spilt"+e.getMessage());
				}

				logger.info("doumen split size " + givenproperpages);

				byte[] decodeAttachementData = null;
				try {
					decodeAttachementData = Base64.decode(originalAttachment);
				} catch (Exception e) {
			logger.error("Error occured if Document not spilt"+e.getMessage());
				}
				String filePath = "";

				if (givenproperpages) {

					ArrayList listOfData = new ExtractPagesFromPdfAndSaveAsNewPDFPage()
							.pdfFilePath(pagesList, decodeAttachementData);
					try {
						filePath = (String) listOfData.get(0);
						givenproperpages = (boolean) listOfData.get(1);
					} catch (Exception e) {
				logger.error("Error occured if Document doesnot ExtractPagesFromPdfAndSaveAsNewPDFPage"+e.getMessage());
					}
				}

				logger.info("proper pages is " + givenproperpages);
				if (givenproperpages) {
					Path path = Paths.get(filePath);
					byte[] data1 = Files.readAllBytes(path);

					String encodeData = Base64.encodeBytes(data1);

					try {
						File file = new File(filePath);
						file.delete();
						logger.info("file deleted");
					} catch (Exception e) {
						logger.error("Error occured if Document files are not deleted"+e.getMessage());
					}

					JSONObject splitDocJsonData = new JSONObject();

					splitDocJsonData.put("attachement", encodeData);

					couchbase.storeDataInCouchBase("DocSoloAttachment"
							+ uniqueId, splitDocJsonData.toString());
					JSONObject jsonDocData = new JSONObject();

					if (relatedrecord.equalsIgnoreCase("Property Documents")
							|| relatedrecord
									.equalsIgnoreCase("DownPayment Documents")
							|| relatedrecord
									.equalsIgnoreCase("Miscellaneous Documents")) {

						jsonDocData.put("ApplicantName", applicantname);
						jsonDocData.put("DocumentType", applicantname + "_"
								+ documenTypeContent);

					} else {
						jsonDocData.put("ApplicantName", relatedrecord);
						jsonDocData.put("DocumentType", relatedrecord + "_"
								+ documenTypeContent);

					}

					jsonDocData.put("Type", "SplitDoc");
					jsonDocData.put("Type_SplitDoc", "SplitDoc");

					jsonDocData.put("documenTypeContent", documenTypeContent);
					jsonDocData.put("DocumentName", relatedrecord);
					jsonDocData.put("OpportunityName", opportunityName);
					jsonDocData.put("OpportunityId", opprtunity);
					jsonDocData.put("ReceivedEmail", recivedFromEmial);
					jsonDocData.put("OriginalDocumentAttachmentID",
							"https://doc.visdom.ca/getid?id="
									+ originalAttachmentId);
					jsonDocData.put("MasterDocURL", id);
					jsonDocData.put("SplitterURL", id);
					jsonDocData.put("splitedDataViewUrl", "DocSoloAttachment"
							+ uniqueId);

					jsonDocData.put("URL", "SplitDoc" + uniqueId);
					jsonDocData.put("ViewerURL",
							"https://doc.visdom.ca/getid?id="
									+ "DocSoloAttachment" + uniqueId);

					jsonDocData.put("DocSoloAttachment", "DocSoloAttachment"
							+ uniqueId);
					jsonDocData.put("OriginalDocURL",
							"https://doc.visdom.ca/getid?id="
									+ originalAttachmentId);
					jsonDocData.put("MasterDocumentID",
							"DocumentListOfApplicationNo_" + opprtunity);
					jsonDocData.put("OriginalCreationTime",
							originalCreationTime);

					couchbase.storeDataInCouchBase("SplitDoc" + uniqueId,
							jsonDocData.toString());
					JSONObject oldSplitedJSon = null;
					JSONArray jsonArray = null;
					try {
						oldSplitedJSon = couchbase
								.getCouchBaseData("DocumentSplitList"
										+ opprtunity);

					} catch (Exception e) {
						logger.error("Error occured if storeDataInCouchBase document is not spilt"+e.getMessage());
					}
					if (oldSplitedJSon != null) {
						jsonArray = new JSONArray(oldSplitedJSon.get(
								"splitedList").toString());
						logger.info("json  arrary datdata " + jsonArray);
						jsonArray.put("SplitDoc" + uniqueId);

						logger.info("json  arrary datdata " + jsonArray);

						oldSplitedJSon.put("splitedList", jsonArray);
						couchbase.editDataInCouchBase("DocumentSplitList"
								+ opprtunity, oldSplitedJSon);
					} else {
						jsonArray = new JSONArray();
						jsonArray.put("SplitDoc" + uniqueId);
						oldSplitedJSon = new JSONObject();
						oldSplitedJSon.put("splitedList", jsonArray);
						couchbase.storeDataInCouchBase("DocumentSplitList"
								+ opprtunity, oldSplitedJSon.toString());

					}

					try {
						int i = 0;
						JSONObject originalJsonData = couchbase
								.getCouchBaseData(id);
						try {
							i = originalJsonData.getInt("TimesSplit");
						} catch (Exception e) {
							logger.error("Error occured if document is not spilt acc to time"+e.getMessage());
						}
						i = i + 1;
						logger.debug("-----------------Timesplit  ------------------->"+i);
					
						originalJsonData.put("Type_DocOriginal",
								"DocOriginal");

						originalJsonData.put("opprtunityId1",
								opprtunity);
						originalJsonData.put("opprtunityEmail",
								opportunityEmail);
						originalJsonData.put("opprtunity",
								opportunityName);
						originalJsonData.put("applicantName1", applicantname);
						originalJsonData.put("applicantName2", coApplicantName);

						originalJsonData.put("TimesSplit", i);
						couchbase.editDataInCouchBase(id, originalJsonData);
					} catch (Exception e) {
						logger.error("Error occured while getCouchBaseData"+e.getMessage());
					}

					// updating documentMaster
					try {
						doclist.put("Type", "DocMaster");
						doclist.put("Type_DocMaster", "DocMaster");
					
						doclist.put("CrmURL",opprtunity );

						doclist.put("DocMasterUrl", id);
						doclist.put("masterdoc_opporunityName", opportunityName);
						DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");            //get current date time with Calendar()
			            Calendar cal = Calendar.getInstance();
			            String currentDateTime=(dateFormat.format(cal.getTime())); 
			            doclist.put("LastDocumentSplitedDate",currentDateTime);

						couchbase.editDataInCouchBase(
								"DocumentListOfApplicationNo_" + opprtunity,
								doclist);

					} catch (Exception e) {
				logger.error("Error occured if not updating document"+e.getMessage());
					}

					try {

						splitedList = new ArrayList<DocoSolo>();

						logger.info("json array size " + jsonArray.length());
						for (int i = 0; i < jsonArray.length(); i++) {

							DocoSolo docData = new DocoSolo();
							JSONObject jsonData = couchbase
									.getCouchBaseData(jsonArray.get(i)
											.toString());
							docData.setDocId(jsonArray.get(i).toString());
							docData.setRelatedDoc(jsonData.get("DocumentName")
									.toString());
							docData.setDocumentType(jsonData
									.get("DocumentType").toString());
							docData.setLink(jsonData.get("ViewerURL")
									.toString());
							splitedList.add(docData);
						}

						logger.info("json array size " + splitedList.size());

					} catch (Exception e) {
						logger.error("Error occured if not splitting document"+e.getMessage());
					}

					return ok(index.render(originalAttachment, OpportunityList,
							applicants, applicantsDisplay, coapplicantsDisply,
							applicantsDocuments, coapplicantsDocuments,
							propertyDocuments, downpaymentDocuments,
							missleneusDocuments, opportunityName, id, 1,
							splitedList, properdocumentSelected,
							splitedSucessTrue));

				} else {
					return ok(index.render(originalAttachment, OpportunityList,
							applicants, applicantsDisplay, coapplicantsDisply,
							applicantsDocuments, coapplicantsDocuments

							, propertyDocuments, downpaymentDocuments,
							missleneusDocuments, opportunityName, id, 0,
							splitedList, properdocumentSelected,
							splitedSucessTrue));

				}
			} else {
				return ok(index.render(originalAttachment, OpportunityList,
						applicants, applicantsDisplay, coapplicantsDisply,
						applicantsDocuments, coapplicantsDocuments,
						propertyDocuments, downpaymentDocuments,
						missleneusDocuments, opportunityName, id, 0,
						splitedList, properdocumentSelected, splitedSucessTrue));
			}

		} else {
			return ok(index.render(originalAttachment, OpportunityList,
					applicants, applicantsDisplay, coapplicantsDisply,
					applicantsDocuments, coapplicantsDocuments,
					propertyDocuments, downpaymentDocuments,
					missleneusDocuments, opportunityName, id, 0, splitedList,
					properdocumentSelected, splitedSucessTrue));
		}

		// attachment

	}

	public static Result masterDoc() throws JSONException, IOException {

		String opprtunity = "";
		String id = "";
		id = request().getQueryString("id");
		ArrayList<String> applicants = new ArrayList<String>();
		ArrayList<String> coapplicants = new ArrayList<String>();
		ArrayList<String> property = new ArrayList<String>();
		ArrayList<String> downpayment = new ArrayList<String>();
		ArrayList<String> misslenious = new ArrayList<String>();

		ArrayList<NotRecivedDocuments> applicantdocuemnts = new ArrayList<NotRecivedDocuments>();
		ArrayList<NotRecivedDocuments> coApplicantdocuemnts = new ArrayList<NotRecivedDocuments>();

		ArrayList<NotRecivedDocuments> propertyDocuemnts = new ArrayList<NotRecivedDocuments>();

		ArrayList<NotRecivedDocuments> downPaymentDocuemnts = new ArrayList<NotRecivedDocuments>();
		ArrayList<NotRecivedDocuments> missleniousDocuemnts = new ArrayList<NotRecivedDocuments>();

		ArrayList<ReceivedDocuments> applicantspliteddocuemnts = new ArrayList<ReceivedDocuments>();
		ArrayList<ReceivedDocuments> coApplicantspliteddocuemnts = new ArrayList<ReceivedDocuments>();

		ArrayList<ReceivedDocuments> propertysplitedDocuemnts = new ArrayList<ReceivedDocuments>();

		ArrayList<ReceivedDocuments> downPaymentsplitedDocuemnts = new ArrayList<ReceivedDocuments>();
		ArrayList<ReceivedDocuments> misslenioussplitedDocuemnts = new ArrayList<ReceivedDocuments>();

		ArrayList<String> docuemnts = new ArrayList<String>();

		CouchBaseOperation couchBaseOperation = new CouchBaseOperation();

		String opportunityName = "";
		JSONObject jsonObject = null;
		String applicantname = "";
		String coApplicantName = "";
		try {
			jsonObject = couchBaseOperation.getCouchBaseData(id);
		} catch (Exception e) {
			logger.error("Error occured in masterDoc"+e.getMessage());

		}

		ArrayList<OpportunityList> OpportunityList = new Odoo()
				.getOpprunityDetials();

		
		try{
		if (jsonObject != null) {
			opprtunity = jsonObject.get("opprtunityId1").toString();
			applicants.add(jsonObject.get("applicantName1").toString());
			applicantname = jsonObject.get("applicantName1").toString();
			opportunityName = jsonObject.get("opprtunity").toString();
			try {
				coapplicants.add(jsonObject.get("applicantName2").toString());
				coApplicantName = jsonObject.get("applicantName2").toString();
			} catch (Exception e) {
				logger.error("Error occured in not get applicants"+e.getMessage());
			}

			logger.info("json object " + jsonObject);

		}
		
		}catch(Exception e){
	logger.error("Error occured in not get applicants"+e.getMessage());		
		}

		JSONObject doclist = null;
		try {
			doclist = couchBaseOperation
					.getCouchBaseData("DocumentListOfApplicationNo_"
							+ opprtunity);
		} catch (Exception e) {
			logger.error("Error occured in not get DocumentListOfApplicationNo_"+e.getMessage());
		}

		if (doclist != null) {
			try {
				JSONArray jsonArray = new JSONArray(doclist.get("documents")
						.toString());
				for (int i = 0; i < jsonArray.length(); i++) {

					NotRecivedDocuments notRecivedDocuments = new NotRecivedDocuments();

					notRecivedDocuments.setDocumentName(jsonArray.get(i)
							.toString());

					docuemnts.add(jsonArray.get(i).toString());
					notRecivedDocuments.setRecived(0);
					applicantdocuemnts.add(notRecivedDocuments);
				}
			} catch (Exception e) {
				logger.error("Error occured in notRecivedDocuments"+e.getMessage());
			}

			try {
				JSONArray jsonArray = new JSONArray(doclist.get("co_documents")
						.toString());
				for (int i = 0; i < jsonArray.length(); i++) {

					NotRecivedDocuments notRecivedDocuments = new NotRecivedDocuments();

					notRecivedDocuments.setDocumentName(jsonArray.get(i)
							.toString());

					docuemnts.add(jsonArray.get(i).toString());
					notRecivedDocuments.setRecived(0);
					coApplicantdocuemnts.add(notRecivedDocuments);
				}
			} catch (Exception e) {
				logger.error("Error occured in notRecived co-Documents"+e.getMessage());
			}

			try {
				JSONArray jsonArray = new JSONArray(doclist.get(
						"propertyDocments").toString());
				for (int i = 0; i < jsonArray.length(); i++) {

					NotRecivedDocuments notRecivedDocuments = new NotRecivedDocuments();

					notRecivedDocuments.setDocumentName(jsonArray.get(i)
							.toString());
					notRecivedDocuments.setRecived(0);
					docuemnts.add(jsonArray.get(i).toString());
					propertyDocuemnts.add(notRecivedDocuments);
				}
			} catch (Exception e) {
				logger.error("Error occured in not Recived propertyDocments"+e.getMessage());
			}

			try {
				JSONArray jsonArray = new JSONArray(doclist.get("downPayments")
						.toString());
				for (int i = 0; i < jsonArray.length(); i++) {

					NotRecivedDocuments notRecivedDocuments = new NotRecivedDocuments();

					notRecivedDocuments.setDocumentName(jsonArray.get(i)
							.toString());
					notRecivedDocuments.setRecived(0);
					docuemnts.add(jsonArray.get(i).toString());
					downPaymentDocuemnts.add(notRecivedDocuments);
				}
			} catch (Exception e) {
				logger.error("Error occured in not Recived downPayments Docments"+e.getMessage());
			}

			try {
				JSONArray jsonArray = new JSONArray(doclist.get(
						"MiscellaneousDocuments").toString());
				for (int i = 0; i < jsonArray.length(); i++) {

					NotRecivedDocuments notRecivedDocuments = new NotRecivedDocuments();

					notRecivedDocuments.setDocumentName(jsonArray.get(i)
							.toString());
					notRecivedDocuments.setRecived(0);
					docuemnts.add(jsonArray.get(i).toString());
					missleniousDocuemnts.add(notRecivedDocuments);
				}
			} catch (Exception e) {
				logger.error("Error occured in not Recived MiscellaneousDocuments"+e.getMessage());
			}

		}

		JSONObject oldSplited = null;
		try {
			oldSplited = couchBaseOperation
					.getCouchBaseData("DocumentSplitList" + opprtunity);
			JSONArray jsonArray = new JSONArray(oldSplited.get("splitedList")
					.toString());

			try {
				for (int i = 0; i < jsonArray.length(); i++) {

					JSONObject jsonData = couchBaseOperation
							.getCouchBaseData(jsonArray.get(i).toString());
					String document = jsonData.get("DocumentName").toString();

					if (document.equalsIgnoreCase(applicantname)) {
						ReceivedDocuments recived = new ReceivedDocuments();
						recived.setDocuementName(jsonData.get("DocumentType")
								.toString());

						recived.setSplitedDate(jsonData.get(
								"Submission_Date_Time1b").toString());
						recived.setDocumentTypeContent(jsonData.get(
								"documenTypeContent").toString());
						recived.setLink(jsonData.get(
								"OriginalDocumentAttachmentID").toString());
						recived.setSplitedLink(jsonData.get("ViewerURL")
								.toString());
						recived.setSplitedDocId(jsonData.get("URL").toString());
						applicantspliteddocuemnts.add(recived);
					} else if (document.equalsIgnoreCase(coApplicantName)) {

						ReceivedDocuments recived = new ReceivedDocuments();
						recived.setDocuementName(jsonData.get("DocumentType")
								.toString());
						recived.setDocumentTypeContent(jsonData.get(
								"documenTypeContent").toString());
						recived.setSplitedDate(jsonData.get(
								"Submission_Date_Time1b").toString());
						recived.setLink(jsonData.get(
								"OriginalDocumentAttachmentID").toString());
						recived.setSplitedLink(jsonData.get("ViewerURL")
								.toString());
						recived.setSplitedDocId(jsonData.get("URL").toString());
						coApplicantspliteddocuemnts.add(recived);
					} else if (document.equalsIgnoreCase("Property Documents")) {

						ReceivedDocuments recived = new ReceivedDocuments();
						recived.setDocuementName(jsonData.get("DocumentType")
								.toString());
						recived.setDocumentTypeContent(jsonData.get(
								"documenTypeContent").toString());
						recived.setSplitedDate(jsonData.get(
								"Submission_Date_Time1b").toString());
						recived.setLink(jsonData.get(
								"OriginalDocumentAttachmentID").toString());
						recived.setSplitedLink(jsonData.get("ViewerURL")
								.toString());
						recived.setSplitedDocId(jsonData.get("URL").toString());

						propertysplitedDocuemnts.add(recived);
					} else if (document
							.equalsIgnoreCase("DownPayment Documents")) {

						ReceivedDocuments recived = new ReceivedDocuments();
						recived.setDocuementName(jsonData.get("DocumentType")
								.toString());
						recived.setDocumentTypeContent(jsonData.get(
								"documenTypeContent").toString());
						recived.setSplitedDate(jsonData.get(
								"Submission_Date_Time1b").toString());
						recived.setLink(jsonData.get(
								"OriginalDocumentAttachmentID").toString());

						recived.setSplitedLink(jsonData.get("ViewerURL")
								.toString());
						recived.setSplitedDocId(jsonData.get("URL").toString());
						downPaymentsplitedDocuemnts.add(recived);
					}

					else if (document
							.equalsIgnoreCase("Miscellaneous Documents")) {

						ReceivedDocuments recived = new ReceivedDocuments();
						recived.setDocuementName(jsonData.get("DocumentType")
								.toString());
						recived.setDocumentTypeContent(jsonData.get(
								"documenTypeContent").toString());
						recived.setSplitedDate(jsonData.get(
								"Submission_Date_Time1b").toString());
						recived.setLink(jsonData.get(
								"OriginalDocumentAttachmentID").toString());

						recived.setSplitedLink(jsonData.get("ViewerURL")
								.toString());

						recived.setSplitedDocId(jsonData.get("URL").toString());
						misslenioussplitedDocuemnts.add(recived);
					}

				}

			} catch (Exception e) {
				logger.error("Error occured in not get Documentsplit by name & type"+e.getMessage());
			}
		} catch (Exception e) {
			logger.error("Error occured in not get Documentsplit List"+e.getMessage());
		}

		property.add("Property Documents");

		downpayment.add("DownPayment Documents");

		misslenious.add("Miscellaneous Documents");

		logger.debug("applcantdocsize :" + applicantdocuemnts.size()
				+ "applicantsplited docSize:"
				+ applicantspliteddocuemnts.size());

		if (applicantdocuemnts.size() != applicantspliteddocuemnts.size()) {
			for (int j = applicantspliteddocuemnts.size(); j < applicantdocuemnts
					.size(); j++) {
				ReceivedDocuments receivedDocuments = new ReceivedDocuments();
				receivedDocuments.setDocuementName("-");
				receivedDocuments.setSplitedDate("-");
				receivedDocuments.setLink("#");
				receivedDocuments.setRecived(0);

				applicantspliteddocuemnts.add(receivedDocuments);
			}

		}

		logger.debug("coApplicantdocuemnts :"
				+ coApplicantdocuemnts.size()
				+ "coApplicantspliteddocuemnts docSize:"
				+ coApplicantspliteddocuemnts.size());

		if (coApplicantdocuemnts.size() != coApplicantspliteddocuemnts.size()) {
			for (int j = coApplicantspliteddocuemnts.size(); j < coApplicantdocuemnts
					.size(); j++) {
				ReceivedDocuments receivedDocuments = new ReceivedDocuments();
				receivedDocuments.setDocuementName("-");
				receivedDocuments.setSplitedDate("-");
				receivedDocuments.setLink("#");
				receivedDocuments.setRecived(0);

				coApplicantspliteddocuemnts.add(receivedDocuments);
			}

		}

		logger.debug("coApplicantdocuemnts :"
				+ coApplicantdocuemnts.size()
				+ "coApplicantspliteddocuemnts docSize:"
				+ coApplicantspliteddocuemnts.size());

		logger.debug("propertyDocuemnts :" + propertyDocuemnts.size()
				+ "propertysplitedDocuemnts docSize:"
				+ propertysplitedDocuemnts.size());

		if (propertyDocuemnts.size() != propertysplitedDocuemnts.size()) {
			for (int j = propertysplitedDocuemnts.size(); j < propertyDocuemnts
					.size(); j++) {
				ReceivedDocuments receivedDocuments = new ReceivedDocuments();
				receivedDocuments.setDocuementName("-");
				receivedDocuments.setSplitedDate("-");
				receivedDocuments.setLink("#");
				receivedDocuments.setRecived(0);

				propertysplitedDocuemnts.add(receivedDocuments);
			}

		}
		logger.debug("propertyDocuemnts :" + propertyDocuemnts.size()
				+ "propertysplitedDocuemnts docSize:"
				+ propertysplitedDocuemnts.size());

		logger.debug("downPaymentDocuemnts :"
				+ downPaymentDocuemnts.size()
				+ "downPaymentsplitedDocuemnts docSize:"
				+ downPaymentsplitedDocuemnts.size());

		if (downPaymentDocuemnts.size() != downPaymentsplitedDocuemnts.size()) {
			for (int j = downPaymentsplitedDocuemnts.size(); j < downPaymentDocuemnts
					.size(); j++) {
				ReceivedDocuments receivedDocuments = new ReceivedDocuments();
				receivedDocuments.setDocuementName("-");
				receivedDocuments.setSplitedDate("-");
				receivedDocuments.setLink("#");
				receivedDocuments.setRecived(0);

				downPaymentsplitedDocuemnts.add(receivedDocuments);
			}

		}
		logger.debug("downPaymentDocuemnts :"
				+ downPaymentDocuemnts.size()
				+ "downPaymentsplitedDocuemnts docSize:"
				+ downPaymentsplitedDocuemnts.size());

		logger.debug("missleniousDocuemnts :"
				+ missleniousDocuemnts.size()
				+ "misslenioussplitedDocuemnts docSize:"
				+ misslenioussplitedDocuemnts.size());

		if (missleniousDocuemnts.size() != misslenioussplitedDocuemnts.size()) {
			for (int j = misslenioussplitedDocuemnts.size(); j < missleniousDocuemnts
					.size(); j++) {
				ReceivedDocuments receivedDocuments = new ReceivedDocuments();
				receivedDocuments.setDocuementName("-");
				receivedDocuments.setSplitedDate("-");
				receivedDocuments.setLink("#");
				receivedDocuments.setRecived(0);

				misslenioussplitedDocuemnts.add(receivedDocuments);
			}

		}

		// check for not recived applicantdocuemnts

		int size = applicantdocuemnts.size();
		int size2 = applicantspliteddocuemnts.size();

		int downlaod = 0;

		ArrayList<ReceivedDocuments> recivedApplicantDocarrayList = new ArrayList<ReceivedDocuments>();
		/*
		 * for(int y=0;y<size;y++){ ReceivedDocuments receivedDocuments = new
		 * ReceivedDocuments(); receivedDocuments.setDocuementName("-");
		 * receivedDocuments.setSplitedDate("-");
		 * receivedDocuments.setLink("#");
		 * recivedApplicantDocarrayList.add(receivedDocuments); }
		 */
		for (int k = 0; k < size; k++) {
			NotRecivedDocuments notrecived = applicantdocuemnts.get(k);
			logger.debug("applicant docuemnts " + k);

			ReceivedDocuments recivedDocuements = new ReceivedDocuments();
			recivedDocuements.setDocuementName(notrecived.getDocumentName());

			for (int z = 0; z < size2; z++) {
				ReceivedDocuments recieved = applicantspliteddocuemnts.get(z);

				logger.debug("not recived  " + z);
				recivedDocuements.setRecived(0);
				recivedDocuements.setRecivedDocuments(recieved
						.getDocuementName());
				recivedDocuements.setSplitedDate(recieved.getSplitedDate());
				recivedDocuements.setLink(recieved.getLink());
				recivedDocuements.setSplitedLink(recieved.getSplitedLink());
				recivedDocuements.setSplitedDocId(recieved.getSplitedDocId());
				if (notrecived.getDocumentName().equalsIgnoreCase(
						recieved.getDocumentTypeContent())) {
					recivedDocuements.setRecived(1);
					recivedDocuements.setRecivedDocuments(recieved
							.getDocuementName());
					if (downlaod == 0) {
						downlaod = 1;
					}
					recivedDocuements.setSplitedDate(recieved.getSplitedDate());
					recivedDocuements.setLink(recieved.getLink());
					recivedDocuements.setSplitedLink(recieved.getSplitedLink());
					recivedDocuements.setSplitedDocId(recieved
							.getSplitedDocId());
					// applicantdocuemnts.add(k, notrecived);
					break;
				}

			}

			recivedApplicantDocarrayList.add(recivedDocuements);

		}
		logger.debug("size of array list "
				+ recivedApplicantDocarrayList.size());

		size = coApplicantdocuemnts.size();
		size2 = coApplicantspliteddocuemnts.size();

		ArrayList<ReceivedDocuments> recivedCoApplicantDocarrayList = new ArrayList<ReceivedDocuments>();

		for (int k = 0; k < size; k++) {
			NotRecivedDocuments notrecived = coApplicantdocuemnts.get(k);
			logger.debug("applicant docuemnts " + k);

			ReceivedDocuments recivedDocuements = new ReceivedDocuments();
			recivedDocuements.setDocuementName(notrecived.getDocumentName());

			for (int z = 0; z < size2; z++) {
				ReceivedDocuments recieved = coApplicantspliteddocuemnts.get(z);

				logger.debug("not recived  " + z);
				recivedDocuements.setRecived(0);
				recivedDocuements.setRecivedDocuments(recieved
						.getDocuementName());
				recivedDocuements.setSplitedDate(recieved.getSplitedDate());
				recivedDocuements.setLink(recieved.getLink());
				recivedDocuements.setSplitedLink(recieved.getSplitedLink());
				recivedDocuements.setSplitedDocId(recieved.getSplitedDocId());
				if (notrecived.getDocumentName().equalsIgnoreCase(
						recieved.getDocumentTypeContent())) {
					recivedDocuements.setRecived(1);
					recivedDocuements.setRecivedDocuments(recieved
							.getDocuementName());
					if (downlaod == 0) {
						downlaod = 1;
					}
					recivedDocuements.setSplitedDate(recieved.getSplitedDate());
					recivedDocuements.setLink(recieved.getLink());
					recivedDocuements.setSplitedLink(recieved.getSplitedLink());
					recivedDocuements.setSplitedDocId(recieved
							.getSplitedDocId());
					// applicantdocuemnts.add(k, notrecived);
					break;
				}

			}

			recivedCoApplicantDocarrayList.add(recivedDocuements);

		}

		size = propertyDocuemnts.size();
		size2 = propertysplitedDocuemnts.size();
		ArrayList<ReceivedDocuments> recivedpropertyDocuemntsarrayList = new ArrayList<ReceivedDocuments>();

		for (int k = 0; k < size; k++) {
			NotRecivedDocuments notrecived = propertyDocuemnts.get(k);
			logger.debug("applicant docuemnts " + k);

			ReceivedDocuments recivedDocuements = new ReceivedDocuments();
			recivedDocuements.setDocuementName(notrecived.getDocumentName());

			for (int z = 0; z < size2; z++) {
				ReceivedDocuments recieved = propertysplitedDocuemnts.get(z);

				logger.debug("not recived  " + z);
				recivedDocuements.setRecived(0);
				recivedDocuements.setRecivedDocuments(recieved
						.getDocuementName());
				recivedDocuements.setSplitedDate(recieved.getSplitedDate());
				recivedDocuements.setLink(recieved.getLink());
				recivedDocuements.setSplitedLink(recieved.getSplitedLink());
				recivedDocuements.setSplitedDocId(recieved.getSplitedDocId());
				if (notrecived.getDocumentName().equalsIgnoreCase(
						recieved.getDocumentTypeContent())) {
					recivedDocuements.setRecived(1);
					recivedDocuements.setRecivedDocuments(recieved
							.getDocuementName());
					if (downlaod == 0) {
						downlaod = 1;
					}
					recivedDocuements.setSplitedDate(recieved.getSplitedDate());
					recivedDocuements.setLink(recieved.getLink());
					recivedDocuements.setSplitedLink(recieved.getSplitedLink());
					recivedDocuements.setSplitedDocId(recieved
							.getSplitedDocId());
					// applicantdocuemnts.add(k, notrecived);
					break;
				}

			}

			recivedpropertyDocuemntsarrayList.add(recivedDocuements);

		}

		size = downPaymentDocuemnts.size();
		size2 = downPaymentsplitedDocuemnts.size();
		ArrayList<ReceivedDocuments> reciveddownPaymentDocuemntsarrayList = new ArrayList<ReceivedDocuments>();

		for (int k = 0; k < size; k++) {
			NotRecivedDocuments notrecived = downPaymentDocuemnts.get(k);
			logger.debug("applicant docuemnts " + k);

			ReceivedDocuments recivedDocuements = new ReceivedDocuments();
			recivedDocuements.setDocuementName(notrecived.getDocumentName());

			for (int z = 0; z < size2; z++) {
				ReceivedDocuments recieved = downPaymentsplitedDocuemnts.get(z);

				logger.debug("not recived  " + z);
				recivedDocuements.setRecived(0);
				recivedDocuements.setRecivedDocuments(recieved
						.getDocuementName());
				recivedDocuements.setSplitedDate(recieved.getSplitedDate());
				recivedDocuements.setLink(recieved.getLink());
				recivedDocuements.setSplitedLink(recieved.getSplitedLink());
				recivedDocuements.setSplitedDocId(recieved.getSplitedDocId());
				if (notrecived.getDocumentName().equalsIgnoreCase(
						recieved.getDocumentTypeContent())) {
					recivedDocuements.setRecived(1);
					recivedDocuements.setRecivedDocuments(recieved
							.getDocuementName());
					if (downlaod == 0) {
						downlaod = 1;
					}
					recivedDocuements.setSplitedDate(recieved.getSplitedDate());
					recivedDocuements.setLink(recieved.getLink());
					recivedDocuements.setSplitedLink(recieved.getSplitedLink());
					recivedDocuements.setSplitedDocId(recieved
							.getSplitedDocId());
					// applicantdocuemnts.add(k, notrecived);
					break;
				}

			}

			reciveddownPaymentDocuemntsarrayList.add(recivedDocuements);

		}

		size = missleniousDocuemnts.size();
		size2 = misslenioussplitedDocuemnts.size();
		ArrayList<ReceivedDocuments> recivedmissleniousDocuemntsarrayList = new ArrayList<ReceivedDocuments>();

		for (int k = 0; k < size; k++) {
			NotRecivedDocuments notrecived = missleniousDocuemnts.get(k);
			logger.debug("applicant docuemnts " + k);

			ReceivedDocuments recivedDocuements = new ReceivedDocuments();
			recivedDocuements.setDocuementName(notrecived.getDocumentName());

			for (int z = 0; z < size2; z++) {
				ReceivedDocuments recieved = misslenioussplitedDocuemnts.get(z);

				logger.debug("not recived  " + z);
				recivedDocuements.setRecived(0);
				recivedDocuements.setRecivedDocuments(recieved
						.getDocuementName());
				recivedDocuements.setSplitedDate(recieved.getSplitedDate());
				recivedDocuements.setLink(recieved.getLink());
				recivedDocuements.setSplitedLink(recieved.getSplitedLink());
				recivedDocuements.setSplitedDocId(recieved.getSplitedDocId());
				if (notrecived.getDocumentName().equalsIgnoreCase(
						recieved.getDocumentTypeContent())) {
					recivedDocuements.setRecived(1);
					recivedDocuements.setRecivedDocuments(recieved
							.getDocuementName());
					if (downlaod == 0) {
						downlaod = 1;
					}
					recivedDocuements.setSplitedDate(recieved.getSplitedDate());
					recivedDocuements.setLink(recieved.getLink());
					recivedDocuements.setSplitedLink(recieved.getSplitedLink());
					recivedDocuements.setSplitedDocId(recieved
							.getSplitedDocId());
					// applicantdocuemnts.add(k, notrecived);
					break;
				}

			}

			recivedmissleniousDocuemntsarrayList.add(recivedDocuements);

		}
		logger.debug("missleniousDocuemnts :"
				+ missleniousDocuemnts.size()
				+ "misslenioussplitedDocuemnts docSize:"
				+ misslenioussplitedDocuemnts.size());

		logger.debug(" afterdocumentSize " + applicantdocuemnts.size()
				+ "splitedsize " + applicantspliteddocuemnts.size());

		return ok(doc.render(opportunityName, OpportunityList, applicants,
				coapplicants, property, downpayment, misslenious,
				recivedApplicantDocarrayList, recivedCoApplicantDocarrayList,
				recivedpropertyDocuemntsarrayList,
				reciveddownPaymentDocuemntsarrayList,
				recivedmissleniousDocuemntsarrayList, opprtunity, downlaod,
				docuemnts));

	}

	public static Result deleteSplitdoc() throws JSONException, IOException {

		String splitdocid = request().getQueryString("docid");

		String opportunitySerachbleName = request().getQueryString(
				"opportunity");

		logger.debug("document id " + splitdocid);
		CouchBaseOperation couchBaseOperation = new CouchBaseOperation();
		try {
			couchBaseOperation.deleteCouchBaseData(splitdocid);
			Thread.sleep(3000);

		} catch (Exception e) {
			logger.error("Error occured in connection"+e.getMessage());
		}

		logger.debug("opporunity " + opportunitySerachbleName);

		String opprtunity = "";

		// request().getQueryString("id");
		ArrayList<String> applicants = new ArrayList<String>();
		ArrayList<String> coapplicants = new ArrayList<String>();
		ArrayList<String> property = new ArrayList<String>();
		ArrayList<String> downpayment = new ArrayList<String>();
		ArrayList<String> misslenious = new ArrayList<String>();

		ArrayList<NotRecivedDocuments> applicantdocuemnts = new ArrayList<NotRecivedDocuments>();
		ArrayList<NotRecivedDocuments> coApplicantdocuemnts = new ArrayList<NotRecivedDocuments>();

		ArrayList<NotRecivedDocuments> propertyDocuemnts = new ArrayList<NotRecivedDocuments>();

		ArrayList<NotRecivedDocuments> downPaymentDocuemnts = new ArrayList<NotRecivedDocuments>();
		ArrayList<NotRecivedDocuments> missleniousDocuemnts = new ArrayList<NotRecivedDocuments>();

		ArrayList<ReceivedDocuments> applicantspliteddocuemnts = new ArrayList<ReceivedDocuments>();
		ArrayList<ReceivedDocuments> coApplicantspliteddocuemnts = new ArrayList<ReceivedDocuments>();

		ArrayList<ReceivedDocuments> propertysplitedDocuemnts = new ArrayList<ReceivedDocuments>();

		ArrayList<ReceivedDocuments> downPaymentsplitedDocuemnts = new ArrayList<ReceivedDocuments>();
		ArrayList<ReceivedDocuments> misslenioussplitedDocuemnts = new ArrayList<ReceivedDocuments>();
		Odoo odoo = new Odoo();
		ArrayList<String> docuemnts = new ArrayList<String>();

		ArrayList<OpportunityList> OpportunityList = new Odoo()
				.getOpprunityDetials();

		String opportunityName = "";

		String applicantname = "";
		String coApplicantName = "";

		Opprtunity opporunitysearchedData = odoo
				.getOpprtunityData(opportunitySerachbleName);

		if (opporunitysearchedData != null) {
			opprtunity = opporunitysearchedData.getOpprtunityId();
			opportunityName = opporunitysearchedData.getOpprtunityName();
			if (opporunitysearchedData.getApplicants() != null) {
				Set<Applicants> applicantss = opporunitysearchedData
						.getApplicants();

				Iterator<Applicants> appl = applicantss.iterator();
				int k = 0;
				while (appl.hasNext()) {
					Applicants applican = appl.next();

					if (k == 1) {
						coApplicantName = applican.getApplicantName();
						coapplicants.add(coApplicantName);
					} else if (k == 0) {
						applicantname = applican.getApplicantName();
						applicants.add(applican.getApplicantName());

					}
					k++;

				}

			}

		}
		JSONObject oldSplited1 = null;
		try {
			oldSplited1 = couchBaseOperation
					.getCouchBaseData("DocumentSplitList" + opprtunity);
			JSONArray jsonArray = new JSONArray(oldSplited1.get("splitedList")
					.toString());
			logger.debug("-----------------------------------splted doc------------"
					+ jsonArray);

			try {

				JSONArray json = new JSONArray();
				for (int i = 0; i < jsonArray.length(); i++) {

					logger.debug("-----------------------------------splted doc------------"
							+ jsonArray.get(i).toString());
					if (!splitdocid.equalsIgnoreCase(jsonArray.get(i)
							.toString())) {
						json.put(jsonArray.get(i).toString());
					}

				}
				logger.info("-----------------------------------splted doc------------"
						+ json);

				oldSplited1.put("splitedList", json);
				couchBaseOperation.editDataInCouchBase("DocumentSplitList"
						+ opprtunity, oldSplited1);

			} catch (Exception e) {
				logger.error("Error occured while getting splitedList document"+e.getMessage());
			}
		} catch (Exception e) {
			logger.error("Error occured while getting splitedList document"+e.getMessage());
		}

		String originalDocumentId = "";

		try {
			JSONObject originalJsonData = couchBaseOperation
					.getCouchBaseData(splitdocid);
			originalDocumentId = originalJsonData.getString("SplitterURL");
		} catch (Exception e) {

		}

		try {
			int i = 0;
			JSONObject originalJsonData = couchBaseOperation
					.getCouchBaseData(originalDocumentId);
			try {
				i = originalJsonData.getInt("TimesSplit");
			} catch (Exception e) {
				logger.error("Error occured while getting TimesSplit of document"+e.getMessage());
			}
			if(i>0){
			i = i - 1;
			}
			originalJsonData.put("TimesSplit", i);
			couchBaseOperation.editDataInCouchBase(originalDocumentId,
					originalJsonData);
		} catch (Exception e) {
			logger.error("Error occured while TimesSplit of document"+e.getMessage());
		}

		JSONObject doclist = null;
		try {
			doclist = couchBaseOperation
					.getCouchBaseData("DocumentListOfApplicationNo_"
							+ opprtunity);
		} catch (Exception e) {
			logger.error("Error occured while doclist"+e.getMessage());
		}

		if (doclist != null) {
			try {
				JSONArray jsonArray = new JSONArray(doclist.get("documents")
						.toString());
				for (int i = 0; i < jsonArray.length(); i++) {
					NotRecivedDocuments notRecivedDocuments = new NotRecivedDocuments();

					notRecivedDocuments.setDocumentName(jsonArray.get(i)
							.toString());

					docuemnts.add(jsonArray.get(i).toString());
					notRecivedDocuments.setRecived(0);
					applicantdocuemnts.add(notRecivedDocuments);
				}
			} catch (Exception e) {
				logger.error("Error occured while notRecivedDocuments"+e.getMessage());
			}

			try {
				JSONArray jsonArray = new JSONArray(doclist.get("co_documents")
						.toString());
				for (int i = 0; i < jsonArray.length(); i++) {

					NotRecivedDocuments notRecivedDocuments = new NotRecivedDocuments();

					notRecivedDocuments.setDocumentName(jsonArray.get(i)
							.toString());

					docuemnts.add(jsonArray.get(i).toString());
					notRecivedDocuments.setRecived(0);
					coApplicantdocuemnts.add(notRecivedDocuments);
				}
			} catch (Exception e) {
				logger.error("Error occured while notRecived co_documents"+e.getMessage());
			}

			try {
				JSONArray jsonArray = new JSONArray(doclist.get(
						"propertyDocments").toString());
				for (int i = 0; i < jsonArray.length(); i++) {

					NotRecivedDocuments notRecivedDocuments = new NotRecivedDocuments();

					notRecivedDocuments.setDocumentName(jsonArray.get(i)
							.toString());

					docuemnts.add(jsonArray.get(i).toString());
					notRecivedDocuments.setRecived(0);
					propertyDocuemnts.add(notRecivedDocuments);
					logger.debug("property Document--------------- -"
							+ jsonArray.get(i).toString());

				}
			} catch (Exception e) {
				logger.error("Error occured while not Recived property Document"+e.getMessage());
			}

			try {
				JSONArray jsonArray = new JSONArray(doclist.get("downPayments")
						.toString());
				for (int i = 0; i < jsonArray.length(); i++) {

					NotRecivedDocuments notRecivedDocuments = new NotRecivedDocuments();

					notRecivedDocuments.setDocumentName(jsonArray.get(i)
							.toString());

					docuemnts.add(jsonArray.get(i).toString());
					notRecivedDocuments.setRecived(0);
					downPaymentDocuemnts.add(notRecivedDocuments);

					logger.debug("downpayments--------------- -"
							+ jsonArray.get(i).toString());
				}
			} catch (Exception e) {
				logger.error("Error occured while not Recived downpayments Document"+e.getMessage());
			}

			try {
				JSONArray jsonArray = new JSONArray(doclist.get(
						"MiscellaneousDocuments").toString());
				for (int i = 0; i < jsonArray.length(); i++) {

					NotRecivedDocuments notRecivedDocuments = new NotRecivedDocuments();

					notRecivedDocuments.setDocumentName(jsonArray.get(i)
							.toString());

					docuemnts.add(jsonArray.get(i).toString());
					notRecivedDocuments.setRecived(0);
					missleniousDocuemnts.add(notRecivedDocuments);

				}
			} catch (Exception e) {
				logger.error("Error occured while not Recived MiscellaneousDocuments"+e.getMessage());
			}

			logger.debug("list of propertydocument needed "
					+ propertyDocuemnts.toString());
			logger.debug("list of downdocument needed "
					+ downPaymentDocuemnts.toString());

		}

		JSONObject oldSplited = null;
		try {
			oldSplited = couchBaseOperation
					.getCouchBaseData("DocumentSplitList" + opprtunity);
			JSONArray jsonArray = new JSONArray(oldSplited.get("splitedList")
					.toString());

			try {
				for (int i = 0; i < jsonArray.length(); i++) {

					JSONObject jsonData = couchBaseOperation
							.getCouchBaseData(jsonArray.get(i).toString());
					String document = jsonData.get("DocumentName").toString();

					if (document.equalsIgnoreCase(applicantname)) {
						ReceivedDocuments recived = new ReceivedDocuments();
						recived.setDocuementName(jsonData.get("DocumentType")
								.toString());

						recived.setSplitedDate(jsonData.get(
								"Submission_Date_Time1b").toString());
						recived.setDocumentTypeContent(jsonData.get(
								"documenTypeContent").toString());
						recived.setLink(jsonData.get(
								"OriginalDocumentAttachmentID").toString());
						recived.setSplitedLink(jsonData.get("ViewerURL")
								.toString());

						recived.setSplitedDocId(jsonData.get("URL").toString());
						applicantspliteddocuemnts.add(recived);
					} else if (document.equalsIgnoreCase(coApplicantName)) {

						ReceivedDocuments recived = new ReceivedDocuments();
						recived.setDocuementName(jsonData.get("DocumentType")
								.toString());
						recived.setDocumentTypeContent(jsonData.get(
								"documenTypeContent").toString());
						recived.setSplitedDate(jsonData.get(
								"Submission_Date_Time1b").toString());
						recived.setLink(jsonData.get(
								"OriginalDocumentAttachmentID").toString());
						recived.setSplitedLink(jsonData.get("ViewerURL")
								.toString());
						recived.setSplitedDocId(jsonData.get("URL").toString());
						coApplicantspliteddocuemnts.add(recived);
					} else if (document.equalsIgnoreCase("Property Documents")) {

						ReceivedDocuments recived = new ReceivedDocuments();
						recived.setDocuementName(jsonData.get("DocumentType")
								.toString());
						recived.setDocumentTypeContent(jsonData.get(
								"documenTypeContent").toString());
						recived.setSplitedDate(jsonData.get(
								"Submission_Date_Time1b").toString());
						recived.setLink(jsonData.get(
								"OriginalDocumentAttachmentID").toString());
						recived.setSplitedLink(jsonData.get("ViewerURL")
								.toString());
						recived.setSplitedDocId(jsonData.get("URL").toString());
						propertysplitedDocuemnts.add(recived);
					} else if (document
							.equalsIgnoreCase("DownPayment Documents")) {

						ReceivedDocuments recived = new ReceivedDocuments();
						recived.setDocuementName(jsonData.get("DocumentType")
								.toString());
						recived.setDocumentTypeContent(jsonData.get(
								"documenTypeContent").toString());
						recived.setSplitedDate(jsonData.get(
								"Submission_Date_Time1b").toString());
						recived.setLink(jsonData.get(
								"OriginalDocumentAttachmentID").toString());
						recived.setSplitedLink(jsonData.get("ViewerURL")
								.toString());
						recived.setSplitedDocId(jsonData.get("URL").toString());
						downPaymentsplitedDocuemnts.add(recived);
					}

					else if (document
							.equalsIgnoreCase("Miscellaneous Documents")) {

						ReceivedDocuments recived = new ReceivedDocuments();
						recived.setDocuementName(jsonData.get("DocumentType")
								.toString());
						recived.setDocumentTypeContent(jsonData.get(
								"documenTypeContent").toString());
						recived.setSplitedDate(jsonData.get(
								"Submission_Date_Time1b").toString());
						recived.setLink(jsonData.get(
								"OriginalDocumentAttachmentID").toString());
						recived.setSplitedLink(jsonData.get("ViewerURL")
								.toString());
						recived.setSplitedDocId(jsonData.get("URL").toString());
						misslenioussplitedDocuemnts.add(recived);
					}

				}

			} catch (Exception e) {
				logger.error("Error occured while  Recived Documents"+e.getMessage());
			}
		} catch (Exception e) {
			logger.error("Error occured while  Recived Documents"+e.getMessage());
		}

		property.add("Property Documents");

		downpayment.add("DownPayment Documents");

		misslenious.add("Miscellaneous Documents");

		logger.debug("applcantdocsize :" + applicantdocuemnts.size()
				+ "applicantsplited docSize:"
				+ applicantspliteddocuemnts.size());

		if (applicantdocuemnts.size() != applicantspliteddocuemnts.size()) {
			for (int j = applicantspliteddocuemnts.size(); j < applicantdocuemnts
					.size(); j++) {
				ReceivedDocuments receivedDocuments = new ReceivedDocuments();
				receivedDocuments.setDocuementName("-");
				receivedDocuments.setSplitedDate("-");
				receivedDocuments.setLink("#");
				receivedDocuments.setRecived(0);

				applicantspliteddocuemnts.add(receivedDocuments);
			}

		}

		logger.debug("coApplicantdocuemnts :"
				+ coApplicantdocuemnts.size()
				+ "coApplicantspliteddocuemnts docSize:"
				+ coApplicantspliteddocuemnts.size());

		if (coApplicantdocuemnts.size() != coApplicantspliteddocuemnts.size()) {
			for (int j = coApplicantspliteddocuemnts.size(); j < coApplicantdocuemnts
					.size(); j++) {
				ReceivedDocuments receivedDocuments = new ReceivedDocuments();
				receivedDocuments.setDocuementName("-");
				receivedDocuments.setSplitedDate("-");
				receivedDocuments.setLink("#");
				receivedDocuments.setRecived(0);

				coApplicantspliteddocuemnts.add(receivedDocuments);
			}

		}

		logger.debug("coApplicantdocuemnts :"
				+ coApplicantdocuemnts.size()
				+ "coApplicantspliteddocuemnts docSize:"
				+ coApplicantspliteddocuemnts.size());

		logger.debug("propertyDocuemnts :" + propertyDocuemnts.size()
				+ "propertysplitedDocuemnts docSize:"
				+ propertysplitedDocuemnts.size());

		if (propertyDocuemnts.size() != propertysplitedDocuemnts.size()) {
			for (int j = propertysplitedDocuemnts.size(); j < propertyDocuemnts
					.size(); j++) {
				ReceivedDocuments receivedDocuments = new ReceivedDocuments();
				receivedDocuments.setDocuementName("-");
				receivedDocuments.setSplitedDate("-");
				receivedDocuments.setLink("#");
				receivedDocuments.setRecived(0);

				propertysplitedDocuemnts.add(receivedDocuments);
			}

		}
		logger.debug("propertyDocuemnts :" + propertyDocuemnts.size()
				+ "propertysplitedDocuemnts docSize:"
				+ propertysplitedDocuemnts.size());

		logger.debug("downPaymentDocuemnts :"
				+ downPaymentDocuemnts.size()
				+ "downPaymentsplitedDocuemnts docSize:"
				+ downPaymentsplitedDocuemnts.size());

		if (downPaymentDocuemnts.size() != downPaymentsplitedDocuemnts.size()) {
			for (int j = downPaymentsplitedDocuemnts.size(); j < downPaymentDocuemnts
					.size(); j++) {
				ReceivedDocuments receivedDocuments = new ReceivedDocuments();
				receivedDocuments.setDocuementName("-");
				receivedDocuments.setSplitedDate("-");
				receivedDocuments.setLink("#");
				receivedDocuments.setRecived(0);

				downPaymentsplitedDocuemnts.add(receivedDocuments);
			}

		}
		logger.debug("downPaymentDocuemnts :"
				+ downPaymentDocuemnts.size()
				+ "downPaymentsplitedDocuemnts docSize:"
				+ downPaymentsplitedDocuemnts.size());

		logger.debug("missleniousDocuemnts :"
				+ missleniousDocuemnts.size()
				+ "misslenioussplitedDocuemnts docSize:"
				+ misslenioussplitedDocuemnts.size());

		if (missleniousDocuemnts.size() != misslenioussplitedDocuemnts.size()) {
			for (int j = misslenioussplitedDocuemnts.size(); j < missleniousDocuemnts
					.size(); j++) {
				ReceivedDocuments receivedDocuments = new ReceivedDocuments();
				receivedDocuments.setDocuementName("-");
				receivedDocuments.setSplitedDate("-");
				receivedDocuments.setLink("#");
				receivedDocuments.setRecived(0);

				misslenioussplitedDocuemnts.add(receivedDocuments);
			}

		}

		// check for not recived applicantdocuemnts

		int size = applicantdocuemnts.size();
		int size2 = applicantspliteddocuemnts.size();

		int downlaod = 0;

		ArrayList<ReceivedDocuments> recivedApplicantDocarrayList = new ArrayList<ReceivedDocuments>();
		/*
		 * for(int y=0;y<size;y++){ ReceivedDocuments receivedDocuments = new
		 * ReceivedDocuments(); receivedDocuments.setDocuementName("-");
		 * receivedDocuments.setSplitedDate("-");
		 * receivedDocuments.setLink("#");
		 * recivedApplicantDocarrayList.add(receivedDocuments); }
		 */
		for (int k = 0; k < size; k++) {
			NotRecivedDocuments notrecived = applicantdocuemnts.get(k);
			logger.debug("applicant docuemnts " + k);

			ReceivedDocuments recivedDocuements = new ReceivedDocuments();
			recivedDocuements.setDocuementName(notrecived.getDocumentName());

			for (int z = 0; z < size2; z++) {
				ReceivedDocuments recieved = applicantspliteddocuemnts.get(z);

				logger.debug("not recived  " + z);
				recivedDocuements.setRecived(0);
				recivedDocuements.setRecivedDocuments(recieved
						.getDocuementName());
				recivedDocuements.setSplitedDate(recieved.getSplitedDate());
				recivedDocuements.setLink(recieved.getLink());
				recivedDocuements.setSplitedLink(recieved.getSplitedLink());
				recivedDocuements.setSplitedDocId(recieved.getSplitedDocId());
				if (notrecived.getDocumentName().equalsIgnoreCase(
						recieved.getDocumentTypeContent())) {
					recivedDocuements.setRecived(1);
					recivedDocuements.setRecivedDocuments(recieved
							.getDocuementName());
					if (downlaod == 0) {
						downlaod = 1;
					}
					recivedDocuements.setSplitedDate(recieved.getSplitedDate());
					recivedDocuements.setLink(recieved.getLink());
					recivedDocuements.setSplitedLink(recieved.getSplitedLink());
					recivedDocuements.setSplitedDocId(recieved
							.getSplitedDocId());
					// applicantdocuemnts.add(k, notrecived);
					break;
				}

			}

			recivedApplicantDocarrayList.add(recivedDocuements);

		}
		logger.debug("size of array list "
				+ recivedApplicantDocarrayList.size());

		size = coApplicantdocuemnts.size();
		size2 = coApplicantspliteddocuemnts.size();

		ArrayList<ReceivedDocuments> recivedCoApplicantDocarrayList = new ArrayList<ReceivedDocuments>();

		for (int k = 0; k < size; k++) {
			NotRecivedDocuments notrecived = coApplicantdocuemnts.get(k);
			logger.debug("applicant docuemnts " + k);

			ReceivedDocuments recivedDocuements = new ReceivedDocuments();
			recivedDocuements.setDocuementName(notrecived.getDocumentName());

			for (int z = 0; z < size2; z++) {
				ReceivedDocuments recieved = coApplicantspliteddocuemnts.get(z);

				logger.debug("not recived  " + z);
				recivedDocuements.setRecived(0);
				recivedDocuements.setRecivedDocuments(recieved
						.getDocuementName());
				recivedDocuements.setSplitedDate(recieved.getSplitedDate());
				recivedDocuements.setLink(recieved.getLink());
				recivedDocuements.setSplitedLink(recieved.getSplitedLink());
				recivedDocuements.setSplitedDocId(recieved.getSplitedDocId());
				if (notrecived.getDocumentName().equalsIgnoreCase(
						recieved.getDocumentTypeContent())) {
					recivedDocuements.setRecived(1);
					recivedDocuements.setRecivedDocuments(recieved
							.getDocuementName());
					if (downlaod == 0) {
						downlaod = 1;
					}
					recivedDocuements.setSplitedDate(recieved.getSplitedDate());
					recivedDocuements.setLink(recieved.getLink());
					recivedDocuements.setSplitedLink(recieved.getSplitedLink());
					recivedDocuements.setSplitedDocId(recieved
							.getSplitedDocId());
					// applicantdocuemnts.add(k, notrecived);
					break;
				}

			}

			recivedCoApplicantDocarrayList.add(recivedDocuements);

		}

		size = propertyDocuemnts.size();
		size2 = propertysplitedDocuemnts.size();
		ArrayList<ReceivedDocuments> recivedpropertyDocuemntsarrayList = new ArrayList<ReceivedDocuments>();

		for (int k = 0; k < size; k++) {
			NotRecivedDocuments notrecived = propertyDocuemnts.get(k);
			logger.debug("applicant docuemnts " + k);

			ReceivedDocuments recivedDocuements = new ReceivedDocuments();
			recivedDocuements.setDocuementName(notrecived.getDocumentName());

			for (int z = 0; z < size2; z++) {
				ReceivedDocuments recieved = propertysplitedDocuemnts.get(z);

				logger.debug("not recived  " + z);
				recivedDocuements.setRecived(0);
				recivedDocuements.setRecivedDocuments(recieved
						.getDocuementName());
				recivedDocuements.setSplitedDate(recieved.getSplitedDate());
				recivedDocuements.setLink(recieved.getLink());
				recivedDocuements.setSplitedLink(recieved.getSplitedLink());
				recivedDocuements.setSplitedDocId(recieved.getSplitedDocId());

				if (notrecived.getDocumentName().equalsIgnoreCase(
						recieved.getDocumentTypeContent())) {
					recivedDocuements.setRecived(1);
					recivedDocuements.setRecivedDocuments(recieved
							.getDocuementName());
					if (downlaod == 0) {
						downlaod = 1;
					}
					recivedDocuements.setSplitedDate(recieved.getSplitedDate());
					recivedDocuements.setLink(recieved.getLink());
					recivedDocuements.setSplitedLink(recieved.getSplitedLink());
					recivedDocuements.setSplitedDocId(recieved
							.getSplitedDocId());
					// applicantdocuemnts.add(k, notrecived);
					break;
				}

			}

			recivedpropertyDocuemntsarrayList.add(recivedDocuements);

		}

		size = downPaymentDocuemnts.size();
		size2 = downPaymentsplitedDocuemnts.size();
		ArrayList<ReceivedDocuments> reciveddownPaymentDocuemntsarrayList = new ArrayList<ReceivedDocuments>();

		for (int k = 0; k < size; k++) {
			NotRecivedDocuments notrecived = downPaymentDocuemnts.get(k);
			logger.debug("applicant docuemnts " + k);

			ReceivedDocuments recivedDocuements = new ReceivedDocuments();
			recivedDocuements.setDocuementName(notrecived.getDocumentName());

			for (int z = 0; z < size2; z++) {
				ReceivedDocuments recieved = downPaymentsplitedDocuemnts.get(z);

				logger.debug("not recived  " + z);
				recivedDocuements.setRecived(0);
				recivedDocuements.setRecivedDocuments(recieved
						.getDocuementName());
				recivedDocuements.setSplitedDate(recieved.getSplitedDate());
				recivedDocuements.setLink(recieved.getLink());
				recivedDocuements.setSplitedLink(recieved.getSplitedLink());

				recivedDocuements.setSplitedDocId(recieved.getSplitedDocId());
				if (notrecived.getDocumentName().equalsIgnoreCase(
						recieved.getDocumentTypeContent())) {
					recivedDocuements.setRecived(1);
					recivedDocuements.setRecivedDocuments(recieved
							.getDocuementName());
					if (downlaod == 0) {
						downlaod = 1;
					}
					recivedDocuements.setSplitedDate(recieved.getSplitedDate());
					recivedDocuements.setLink(recieved.getLink());
					recivedDocuements.setSplitedLink(recieved.getSplitedLink());
					recivedDocuements.setSplitedDocId(recieved
							.getSplitedDocId());
					// applicantdocuemnts.add(k, notrecived);
					break;
				}

			}

			reciveddownPaymentDocuemntsarrayList.add(recivedDocuements);

		}

		size = missleniousDocuemnts.size();
		size2 = misslenioussplitedDocuemnts.size();
		ArrayList<ReceivedDocuments> recivedmissleniousDocuemntsarrayList = new ArrayList<ReceivedDocuments>();

		for (int k = 0; k < size; k++) {
			NotRecivedDocuments notrecived = missleniousDocuemnts.get(k);
			logger.debug("applicant docuemnts " + k);

			ReceivedDocuments recivedDocuements = new ReceivedDocuments();
			recivedDocuements.setDocuementName(notrecived.getDocumentName());

			for (int z = 0; z < size2; z++) {
				ReceivedDocuments recieved = misslenioussplitedDocuemnts.get(z);

				logger.debug("not recived  " + z);
				recivedDocuements.setRecived(0);
				recivedDocuements.setRecivedDocuments(recieved
						.getDocuementName());
				recivedDocuements.setSplitedDate(recieved.getSplitedDate());
				recivedDocuements.setLink(recieved.getLink());
				recivedDocuements.setSplitedLink(recieved.getSplitedLink());
				recivedDocuements.setSplitedDocId(recieved.getSplitedDocId());

				if (notrecived.getDocumentName().equalsIgnoreCase(
						recieved.getDocumentTypeContent())) {
					recivedDocuements.setRecived(1);
					recivedDocuements.setRecivedDocuments(recieved
							.getDocuementName());
					if (downlaod == 0) {
						downlaod = 1;
					}
					recivedDocuements.setSplitedDate(recieved.getSplitedDate());
					recivedDocuements.setLink(recieved.getLink());
					recivedDocuements.setSplitedLink(recieved.getSplitedLink());
					recivedDocuements.setSplitedDocId(recieved
							.getSplitedDocId());
					// applicantdocuemnts.add(k, notrecived);
					break;
				}

			}

			recivedmissleniousDocuemntsarrayList.add(recivedDocuements);

		}
		logger.debug("missleniousDocuemnts :"
				+ missleniousDocuemnts.size()
				+ "misslenioussplitedDocuemnts docSize:"
				+ misslenioussplitedDocuemnts.size());

		logger.debug(" afterdocumentSize " + applicantdocuemnts.size()
				+ "splitedsize " + applicantspliteddocuemnts.size());

		return ok(doc.render(opportunityName, OpportunityList, applicants,
				coapplicants, property, downpayment, misslenious,
				recivedApplicantDocarrayList, recivedCoApplicantDocarrayList,
				recivedpropertyDocuemntsarrayList,
				reciveddownPaymentDocuemntsarrayList,
				recivedmissleniousDocuemntsarrayList, opprtunity, downlaod,
				docuemnts));

	}

	public static Result searchOpprunityMasterDoc() throws JSONException,
			IOException {

		DynamicForm dynamicForm = form().bindFromRequest();

		String opportunitySerachbleName = dynamicForm.get("opportunity");
		logger.debug("opporunity " + opportunitySerachbleName);

		String opprtunity = "";

		// request().getQueryString("id");
		ArrayList<String> applicants = new ArrayList<String>();
		ArrayList<String> coapplicants = new ArrayList<String>();
		ArrayList<String> property = new ArrayList<String>();
		ArrayList<String> downpayment = new ArrayList<String>();
		ArrayList<String> misslenious = new ArrayList<String>();

		ArrayList<NotRecivedDocuments> applicantdocuemnts = new ArrayList<NotRecivedDocuments>();
		ArrayList<NotRecivedDocuments> coApplicantdocuemnts = new ArrayList<NotRecivedDocuments>();

		ArrayList<NotRecivedDocuments> propertyDocuemnts = new ArrayList<NotRecivedDocuments>();

		ArrayList<NotRecivedDocuments> downPaymentDocuemnts = new ArrayList<NotRecivedDocuments>();
		ArrayList<NotRecivedDocuments> missleniousDocuemnts = new ArrayList<NotRecivedDocuments>();

		ArrayList<ReceivedDocuments> applicantspliteddocuemnts = new ArrayList<ReceivedDocuments>();
		ArrayList<ReceivedDocuments> coApplicantspliteddocuemnts = new ArrayList<ReceivedDocuments>();

		ArrayList<ReceivedDocuments> propertysplitedDocuemnts = new ArrayList<ReceivedDocuments>();

		ArrayList<ReceivedDocuments> downPaymentsplitedDocuemnts = new ArrayList<ReceivedDocuments>();
		ArrayList<ReceivedDocuments> misslenioussplitedDocuemnts = new ArrayList<ReceivedDocuments>();
		Odoo odoo = new Odoo();

		ArrayList<String> docuemnts = new ArrayList<String>();
		CouchBaseOperation couchBaseOperation = new CouchBaseOperation();
		ArrayList<OpportunityList> OpportunityList = new Odoo()
				.getOpprunityDetials();

		String opportunityName = "";

		String applicantname = "";
		String coApplicantName = "";

		Opprtunity opporunitysearchedData = odoo
				.getOpprtunityData(opportunitySerachbleName);
try{
		if (opporunitysearchedData != null) {
			opprtunity = opporunitysearchedData.getOpprtunityId();
			opportunityName = opporunitysearchedData.getOpprtunityName();
			if (opporunitysearchedData.getApplicants() != null) {
				Set<Applicants> applicantss = opporunitysearchedData
						.getApplicants();

				Iterator<Applicants> appl = applicantss.iterator();
				int k = 0;
				while (appl.hasNext()) {
					Applicants applican = appl.next();

					if (k == 1) {
						coApplicantName = applican.getApplicantName();
						coapplicants.add(coApplicantName);
					} else if (k == 0) {
						applicantname = applican.getApplicantName();
						applicants.add(applican.getApplicantName());

					}
					k++;
				}

			}

		} }catch(Exception e){
			logger.error("Error occured while  processing searchOpprunityMasterDoc"+e.getMessage());
		}

		JSONObject doclist = null;
		try {
			doclist = couchBaseOperation
					.getCouchBaseData("DocumentListOfApplicationNo_"
							+ opprtunity);
		} catch (Exception e) {
			logger.error("Error occured while  connection"+e.getMessage());
		}

		if (doclist != null) {
			try {
				JSONArray jsonArray = new JSONArray(doclist.get("documents")
						.toString());
				for (int i = 0; i < jsonArray.length(); i++) {
					NotRecivedDocuments notRecivedDocuments = new NotRecivedDocuments();

					notRecivedDocuments.setDocumentName(jsonArray.get(i)
							.toString());
					notRecivedDocuments.setRecived(0);

					docuemnts.add(jsonArray.get(i).toString());
					applicantdocuemnts.add(notRecivedDocuments);
				}
			} catch (Exception e) {
				logger.error("Error occured while  notRecivedDocuments"+e.getMessage());
			}

			try {
				JSONArray jsonArray = new JSONArray(doclist.get("co_documents")
						.toString());
				for (int i = 0; i < jsonArray.length(); i++) {

					NotRecivedDocuments notRecivedDocuments = new NotRecivedDocuments();

					notRecivedDocuments.setDocumentName(jsonArray.get(i)
							.toString());
					notRecivedDocuments.setRecived(0);

					docuemnts.add(jsonArray.get(i).toString());
					coApplicantdocuemnts.add(notRecivedDocuments);
				}
			} catch (Exception e) {
				logger.error("Error occured while  not Recived co-Documents"+e.getMessage());
			}

			try {
				JSONArray jsonArray = new JSONArray(doclist.get(
						"propertyDocments").toString());
				for (int i = 0; i < jsonArray.length(); i++) {

					NotRecivedDocuments notRecivedDocuments = new NotRecivedDocuments();

					notRecivedDocuments.setDocumentName(jsonArray.get(i)
							.toString());
					notRecivedDocuments.setRecived(0);

					docuemnts.add(jsonArray.get(i).toString());
					propertyDocuemnts.add(notRecivedDocuments);
					logger.debug("property Document--------------- -"
							+ jsonArray.get(i).toString());

				}
			} catch (Exception e) {
				logger.error("Error occured while  not get propertyDocments"+e.getMessage());
			}

			try {
				JSONArray jsonArray = new JSONArray(doclist.get("downPayments")
						.toString());
				for (int i = 0; i < jsonArray.length(); i++) {

					NotRecivedDocuments notRecivedDocuments = new NotRecivedDocuments();

					notRecivedDocuments.setDocumentName(jsonArray.get(i)
							.toString());
					notRecivedDocuments.setRecived(0);

					docuemnts.add(jsonArray.get(i).toString());
					downPaymentDocuemnts.add(notRecivedDocuments);

					logger.debug("downpayments--------------- -"
							+ jsonArray.get(i).toString());
				}
			} catch (Exception e) {
				logger.error("Error occured while  not get downpayments Docments"+e.getMessage());
			}

			try {
				JSONArray jsonArray = new JSONArray(doclist.get(
						"MiscellaneousDocuments").toString());
				for (int i = 0; i < jsonArray.length(); i++) {

					NotRecivedDocuments notRecivedDocuments = new NotRecivedDocuments();

					notRecivedDocuments.setDocumentName(jsonArray.get(i)
							.toString());
					notRecivedDocuments.setRecived(0);

					docuemnts.add(jsonArray.get(i).toString());
					missleniousDocuemnts.add(notRecivedDocuments);

				}
			} catch (Exception e) {
				logger.error("Error occured while  not get MiscellaneousDocuments Docments"+e.getMessage());
			}

			logger.debug("list of propertydocument needed "
					+ propertyDocuemnts.toString());
			logger.debug("list of downdocument needed "
					+ downPaymentDocuemnts.toString());

		}

		JSONObject oldSplited = null;
		try {
			oldSplited = couchBaseOperation
					.getCouchBaseData("DocumentSplitList" + opprtunity);
			JSONArray jsonArray = new JSONArray(oldSplited.get("splitedList")
					.toString());

			try {
				for (int i = 0; i < jsonArray.length(); i++) {

					JSONObject jsonData = couchBaseOperation
							.getCouchBaseData(jsonArray.get(i).toString());
					String document = jsonData.get("DocumentName").toString();

					if (document.equalsIgnoreCase(applicantname)) {
						ReceivedDocuments recived = new ReceivedDocuments();
						recived.setDocuementName(jsonData.get("DocumentType")
								.toString());

						recived.setSplitedDate(jsonData.get(
								"Submission_Date_Time1b").toString());
						recived.setDocumentTypeContent(jsonData.get(
								"documenTypeContent").toString());
						recived.setLink(jsonData.get(
								"OriginalDocumentAttachmentID").toString());
						recived.setSplitedLink(jsonData.get("ViewerURL")
								.toString());

						recived.setSplitedDocId(jsonData.get("URL").toString());
						applicantspliteddocuemnts.add(recived);
					} else if (document.equalsIgnoreCase(coApplicantName)) {

						ReceivedDocuments recived = new ReceivedDocuments();
						recived.setDocuementName(jsonData.get("DocumentType")
								.toString());
						recived.setDocumentTypeContent(jsonData.get(
								"documenTypeContent").toString());
						recived.setSplitedDate(jsonData.get(
								"Submission_Date_Time1b").toString());
						recived.setLink(jsonData.get(
								"OriginalDocumentAttachmentID").toString());
						recived.setSplitedLink(jsonData.get("ViewerURL")
								.toString());
						recived.setSplitedDocId(jsonData.get("URL").toString());
						coApplicantspliteddocuemnts.add(recived);
					} else if (document.equalsIgnoreCase("Property Documents")) {

						ReceivedDocuments recived = new ReceivedDocuments();
						recived.setDocuementName(jsonData.get("DocumentType")
								.toString());
						recived.setDocumentTypeContent(jsonData.get(
								"documenTypeContent").toString());
						recived.setSplitedDate(jsonData.get(
								"Submission_Date_Time1b").toString());
						recived.setLink(jsonData.get(
								"OriginalDocumentAttachmentID").toString());
						recived.setSplitedLink(jsonData.get("ViewerURL")
								.toString());
						recived.setSplitedDocId(jsonData.get("URL").toString());
						propertysplitedDocuemnts.add(recived);
					} else if (document
							.equalsIgnoreCase("DownPayment Documents")) {

						ReceivedDocuments recived = new ReceivedDocuments();
						recived.setDocuementName(jsonData.get("DocumentType")
								.toString());
						recived.setDocumentTypeContent(jsonData.get(
								"documenTypeContent").toString());
						recived.setSplitedDate(jsonData.get(
								"Submission_Date_Time1b").toString());
						recived.setLink(jsonData.get(
								"OriginalDocumentAttachmentID").toString());
						recived.setSplitedLink(jsonData.get("ViewerURL")
								.toString());
						recived.setSplitedDocId(jsonData.get("URL").toString());
						downPaymentsplitedDocuemnts.add(recived);
					}

					else if (document
							.equalsIgnoreCase("Miscellaneous Documents")) {

						ReceivedDocuments recived = new ReceivedDocuments();
						recived.setDocuementName(jsonData.get("DocumentType")
								.toString());
						recived.setDocumentTypeContent(jsonData.get(
								"documenTypeContent").toString());
						recived.setSplitedDate(jsonData.get(
								"Submission_Date_Time1b").toString());
						recived.setLink(jsonData.get(
								"OriginalDocumentAttachmentID").toString());
						recived.setSplitedLink(jsonData.get("ViewerURL")
								.toString());
						recived.setSplitedDocId(jsonData.get("URL").toString());
						misslenioussplitedDocuemnts.add(recived);
					}

				}

			} catch (Exception e) {
				logger.error("Error occured while  IOexception"+e.getMessage());
			}
		} catch (Exception e) {
			logger.error("Error occured while JsonException"+e.getMessage());
		}

		property.add("Property Documents");

		downpayment.add("DownPayment Documents");

		misslenious.add("Miscellaneous Documents");

		logger.debug("applcantdocsize :" + applicantdocuemnts.size()
				+ "applicantsplited docSize:"
				+ applicantspliteddocuemnts.size());

		if (applicantdocuemnts.size() != applicantspliteddocuemnts.size()) {
			for (int j = applicantspliteddocuemnts.size(); j < applicantdocuemnts
					.size(); j++) {
				ReceivedDocuments receivedDocuments = new ReceivedDocuments();
				receivedDocuments.setDocuementName("-");
				receivedDocuments.setSplitedDate("-");
				receivedDocuments.setLink("#");
				receivedDocuments.setRecived(0);

				applicantspliteddocuemnts.add(receivedDocuments);
			}

		}

		logger.debug("coApplicantdocuemnts :"
				+ coApplicantdocuemnts.size()
				+ "coApplicantspliteddocuemnts docSize:"
				+ coApplicantspliteddocuemnts.size());

		if (coApplicantdocuemnts.size() != coApplicantspliteddocuemnts.size()) {
			for (int j = coApplicantspliteddocuemnts.size(); j < coApplicantdocuemnts
					.size(); j++) {
				ReceivedDocuments receivedDocuments = new ReceivedDocuments();
				receivedDocuments.setDocuementName("-");
				receivedDocuments.setSplitedDate("-");
				receivedDocuments.setLink("#");
				receivedDocuments.setRecived(0);

				coApplicantspliteddocuemnts.add(receivedDocuments);
			}

		}

		logger.debug("coApplicantdocuemnts :"
				+ coApplicantdocuemnts.size()
				+ "coApplicantspliteddocuemnts docSize:"
				+ coApplicantspliteddocuemnts.size());

		logger.debug("propertyDocuemnts :" + propertyDocuemnts.size()
				+ "propertysplitedDocuemnts docSize:"
				+ propertysplitedDocuemnts.size());

		if (propertyDocuemnts.size() != propertysplitedDocuemnts.size()) {
			for (int j = propertysplitedDocuemnts.size(); j < propertyDocuemnts
					.size(); j++) {
				ReceivedDocuments receivedDocuments = new ReceivedDocuments();
				receivedDocuments.setDocuementName("-");
				receivedDocuments.setSplitedDate("-");
				receivedDocuments.setLink("#");
				receivedDocuments.setRecived(0);

				propertysplitedDocuemnts.add(receivedDocuments);
			}

		}
		logger.debug("propertyDocuemnts :" + propertyDocuemnts.size()
				+ "propertysplitedDocuemnts docSize:"
				+ propertysplitedDocuemnts.size());

		logger.debug("downPaymentDocuemnts :"
				+ downPaymentDocuemnts.size()
				+ "downPaymentsplitedDocuemnts docSize:"
				+ downPaymentsplitedDocuemnts.size());

		if (downPaymentDocuemnts.size() != downPaymentsplitedDocuemnts.size()) {
			for (int j = downPaymentsplitedDocuemnts.size(); j < downPaymentDocuemnts
					.size(); j++) {
				ReceivedDocuments receivedDocuments = new ReceivedDocuments();
				receivedDocuments.setDocuementName("-");
				receivedDocuments.setSplitedDate("-");
				receivedDocuments.setLink("#");
				receivedDocuments.setRecived(0);

				downPaymentsplitedDocuemnts.add(receivedDocuments);
			}

		}
		logger.debug("downPaymentDocuemnts :"
				+ downPaymentDocuemnts.size()
				+ "downPaymentsplitedDocuemnts docSize:"
				+ downPaymentsplitedDocuemnts.size());

		logger.debug("missleniousDocuemnts :"
				+ missleniousDocuemnts.size()
				+ "misslenioussplitedDocuemnts docSize:"
				+ misslenioussplitedDocuemnts.size());

		if (missleniousDocuemnts.size() != misslenioussplitedDocuemnts.size()) {
			for (int j = misslenioussplitedDocuemnts.size(); j < missleniousDocuemnts
					.size(); j++) {
				ReceivedDocuments receivedDocuments = new ReceivedDocuments();
				receivedDocuments.setDocuementName("-");
				receivedDocuments.setSplitedDate("-");
				receivedDocuments.setLink("#");
				receivedDocuments.setRecived(0);

				misslenioussplitedDocuemnts.add(receivedDocuments);
			}

		}

		// check for not recived applicantdocuemnts

		int size = applicantdocuemnts.size();
		int size2 = applicantspliteddocuemnts.size();
		int downlaod = 0;
		ArrayList<ReceivedDocuments> recivedApplicantDocarrayList = new ArrayList<ReceivedDocuments>();
		/*
		 * for(int y=0;y<size;y++){ ReceivedDocuments receivedDocuments = new
		 * ReceivedDocuments(); receivedDocuments.setDocuementName("-");
		 * receivedDocuments.setSplitedDate("-");
		 * receivedDocuments.setLink("#");
		 * recivedApplicantDocarrayList.add(receivedDocuments); }
		 */
		for (int k = 0; k < size; k++) {
			NotRecivedDocuments notrecived = applicantdocuemnts.get(k);
			logger.debug("applicant docuemnts " + k);

			ReceivedDocuments recivedDocuements = new ReceivedDocuments();
			recivedDocuements.setDocuementName(notrecived.getDocumentName());

			for (int z = 0; z < size2; z++) {
				ReceivedDocuments recieved = applicantspliteddocuemnts.get(z);

				logger.debug("not recived  " + z);
				recivedDocuements.setRecived(0);
				recivedDocuements.setRecivedDocuments(recieved
						.getDocuementName());
				recivedDocuements.setSplitedDate(recieved.getSplitedDate());
				recivedDocuements.setLink(recieved.getLink());
				recivedDocuements.setSplitedLink(recieved.getSplitedLink());
				recivedDocuements.setSplitedDocId(recieved.getSplitedDocId());
				if (notrecived.getDocumentName().equalsIgnoreCase(
						recieved.getDocumentTypeContent())) {
					recivedDocuements.setRecived(1);
					recivedDocuements.setRecivedDocuments(recieved
							.getDocuementName());
					if (downlaod == 0) {
						downlaod = 1;
					}
					recivedDocuements.setSplitedDate(recieved.getSplitedDate());
					recivedDocuements.setLink(recieved.getLink());
					recivedDocuements.setSplitedLink(recieved.getSplitedLink());
					recivedDocuements.setSplitedDocId(recieved
							.getSplitedDocId());
					// applicantdocuemnts.add(k, notrecived);
					break;
				}

			}

			recivedApplicantDocarrayList.add(recivedDocuements);

		}
		logger.debug("size of array list "
				+ recivedApplicantDocarrayList.size());

		size = coApplicantdocuemnts.size();
		size2 = coApplicantspliteddocuemnts.size();

		ArrayList<ReceivedDocuments> recivedCoApplicantDocarrayList = new ArrayList<ReceivedDocuments>();

		for (int k = 0; k < size; k++) {
			NotRecivedDocuments notrecived = coApplicantdocuemnts.get(k);
			logger.debug("applicant docuemnts " + k);

			ReceivedDocuments recivedDocuements = new ReceivedDocuments();
			recivedDocuements.setDocuementName(notrecived.getDocumentName());

			for (int z = 0; z < size2; z++) {
				ReceivedDocuments recieved = coApplicantspliteddocuemnts.get(z);

				logger.debug("not recived  " + z);
				recivedDocuements.setRecived(0);
				recivedDocuements.setRecivedDocuments(recieved
						.getDocuementName());
				recivedDocuements.setSplitedDate(recieved.getSplitedDate());
				recivedDocuements.setLink(recieved.getLink());
				recivedDocuements.setSplitedLink(recieved.getSplitedLink());
				recivedDocuements.setSplitedDocId(recieved.getSplitedDocId());
				if (notrecived.getDocumentName().equalsIgnoreCase(
						recieved.getDocumentTypeContent())) {
					recivedDocuements.setRecived(1);
					recivedDocuements.setRecivedDocuments(recieved
							.getDocuementName());
					if (downlaod == 0) {
						downlaod = 1;
					}
					recivedDocuements.setSplitedDate(recieved.getSplitedDate());
					recivedDocuements.setLink(recieved.getLink());
					recivedDocuements.setSplitedLink(recieved.getSplitedLink());
					recivedDocuements.setSplitedDocId(recieved
							.getSplitedDocId());
					// applicantdocuemnts.add(k, notrecived);
					break;
				}

			}

			recivedCoApplicantDocarrayList.add(recivedDocuements);

		}

		size = propertyDocuemnts.size();
		size2 = propertysplitedDocuemnts.size();
		ArrayList<ReceivedDocuments> recivedpropertyDocuemntsarrayList = new ArrayList<ReceivedDocuments>();

		for (int k = 0; k < size; k++) {
			NotRecivedDocuments notrecived = propertyDocuemnts.get(k);
			logger.debug("applicant docuemnts " + k);

			ReceivedDocuments recivedDocuements = new ReceivedDocuments();
			recivedDocuements.setDocuementName(notrecived.getDocumentName());

			for (int z = 0; z < size2; z++) {
				ReceivedDocuments recieved = propertysplitedDocuemnts.get(z);

				logger.debug("not recived  " + z);
				recivedDocuements.setRecived(0);
				recivedDocuements.setRecivedDocuments(recieved
						.getDocuementName());
				recivedDocuements.setSplitedDate(recieved.getSplitedDate());
				recivedDocuements.setLink(recieved.getLink());
				recivedDocuements.setSplitedLink(recieved.getSplitedLink());
				recivedDocuements.setSplitedDocId(recieved.getSplitedDocId());

				if (notrecived.getDocumentName().equalsIgnoreCase(
						recieved.getDocumentTypeContent())) {
					recivedDocuements.setRecived(1);
					recivedDocuements.setRecivedDocuments(recieved
							.getDocuementName());
					if (downlaod == 0) {
						downlaod = 1;
					}
					recivedDocuements.setSplitedDate(recieved.getSplitedDate());
					recivedDocuements.setLink(recieved.getLink());
					recivedDocuements.setSplitedLink(recieved.getSplitedLink());
					recivedDocuements.setSplitedDocId(recieved
							.getSplitedDocId());
					// applicantdocuemnts.add(k, notrecived);
					break;
				}

			}

			recivedpropertyDocuemntsarrayList.add(recivedDocuements);

		}

		size = downPaymentDocuemnts.size();
		size2 = downPaymentsplitedDocuemnts.size();
		ArrayList<ReceivedDocuments> reciveddownPaymentDocuemntsarrayList = new ArrayList<ReceivedDocuments>();

		for (int k = 0; k < size; k++) {
			NotRecivedDocuments notrecived = downPaymentDocuemnts.get(k);
			logger.debug("applicant docuemnts " + k);

			ReceivedDocuments recivedDocuements = new ReceivedDocuments();
			recivedDocuements.setDocuementName(notrecived.getDocumentName());

			for (int z = 0; z < size2; z++) {
				ReceivedDocuments recieved = downPaymentsplitedDocuemnts.get(z);

				logger.debug("not recived  " + z);
				recivedDocuements.setRecived(0);
				recivedDocuements.setRecivedDocuments(recieved
						.getDocuementName());
				recivedDocuements.setSplitedDate(recieved.getSplitedDate());
				recivedDocuements.setLink(recieved.getLink());
				recivedDocuements.setSplitedLink(recieved.getSplitedLink());

				recivedDocuements.setSplitedDocId(recieved.getSplitedDocId());
				if (notrecived.getDocumentName().equalsIgnoreCase(
						recieved.getDocumentTypeContent())) {
					recivedDocuements.setRecived(1);
					recivedDocuements.setRecivedDocuments(recieved
							.getDocuementName());
					if (downlaod == 0) {
						downlaod = 1;
					}
					recivedDocuements.setSplitedDate(recieved.getSplitedDate());
					recivedDocuements.setLink(recieved.getLink());
					recivedDocuements.setSplitedLink(recieved.getSplitedLink());
					recivedDocuements.setSplitedDocId(recieved
							.getSplitedDocId());
					// applicantdocuemnts.add(k, notrecived);
					break;
				}

			}

			reciveddownPaymentDocuemntsarrayList.add(recivedDocuements);

		}

		size = missleniousDocuemnts.size();
		size2 = misslenioussplitedDocuemnts.size();
		ArrayList<ReceivedDocuments> recivedmissleniousDocuemntsarrayList = new ArrayList<ReceivedDocuments>();

		for (int k = 0; k < size; k++) {
			NotRecivedDocuments notrecived = missleniousDocuemnts.get(k);
			logger.debug("applicant docuemnts " + k);

			ReceivedDocuments recivedDocuements = new ReceivedDocuments();
			recivedDocuements.setDocuementName(notrecived.getDocumentName());

			for (int z = 0; z < size2; z++) {
				ReceivedDocuments recieved = misslenioussplitedDocuemnts.get(z);

				logger.debug("not recived  " + z);
				recivedDocuements.setRecived(0);
				recivedDocuements.setRecivedDocuments(recieved
						.getDocuementName());
				recivedDocuements.setSplitedDate(recieved.getSplitedDate());
				recivedDocuements.setLink(recieved.getLink());
				recivedDocuements.setSplitedLink(recieved.getSplitedLink());
				recivedDocuements.setSplitedDocId(recieved.getSplitedDocId());

				if (notrecived.getDocumentName().equalsIgnoreCase(
						recieved.getDocumentTypeContent())) {
					recivedDocuements.setRecived(1);
					recivedDocuements.setRecivedDocuments(recieved
							.getDocuementName());
					if (downlaod == 0) {
						downlaod = 1;
					}
					recivedDocuements.setSplitedDate(recieved.getSplitedDate());
					recivedDocuements.setLink(recieved.getLink());
					recivedDocuements.setSplitedLink(recieved.getSplitedLink());
					recivedDocuements.setSplitedDocId(recieved
							.getSplitedDocId());
					// applicantdocuemnts.add(k, notrecived);
					break;
				}

			}

			recivedmissleniousDocuemntsarrayList.add(recivedDocuements);

		}
		logger.debug("missleniousDocuemnts :"
				+ missleniousDocuemnts.size()
				+ "misslenioussplitedDocuemnts docSize:"
				+ misslenioussplitedDocuemnts.size());

		logger.debug(" afterdocumentSize " + applicantdocuemnts.size()
				+ "splitedsize " + applicantspliteddocuemnts.size());

		return ok(doc.render(opportunityName, OpportunityList, applicants,
				coapplicants, property, downpayment, misslenious,
				recivedApplicantDocarrayList, recivedCoApplicantDocarrayList,
				recivedpropertyDocuemntsarrayList,
				reciveddownPaymentDocuemntsarrayList,
				recivedmissleniousDocuemntsarrayList, opprtunity, downlaod,
				docuemnts));

	}

	public static Result sendMail() {

		DynamicForm dynamicForm = form().bindFromRequest();
		String opportunitySerachbleName = request().getQueryString(
				"opportunity");
		// "Brad  Pitt - 246 Snowberry Dr. SW, Calgary, First, 2015-08-05 20";

		// dynamicForm.get("opportunity");
		logger.debug("opporunity " + opportunitySerachbleName);

		String opprtunity = "";

		// request().getQueryString("id");
		ArrayList<String> applicants = new ArrayList<String>();
		ArrayList<String> coapplicants = new ArrayList<String>();
		ArrayList<String> property = new ArrayList<String>();
		ArrayList<String> downpayment = new ArrayList<String>();
		ArrayList<String> misslenious = new ArrayList<String>();

		ArrayList<NotRecivedDocuments> applicantdocuemnts = new ArrayList<NotRecivedDocuments>();
		ArrayList<NotRecivedDocuments> coApplicantdocuemnts = new ArrayList<NotRecivedDocuments>();

		ArrayList<NotRecivedDocuments> propertyDocuemnts = new ArrayList<NotRecivedDocuments>();

		ArrayList<NotRecivedDocuments> downPaymentDocuemnts = new ArrayList<NotRecivedDocuments>();
		ArrayList<NotRecivedDocuments> missleniousDocuemnts = new ArrayList<NotRecivedDocuments>();

		ArrayList<ReceivedDocuments> applicantspliteddocuemnts = new ArrayList<ReceivedDocuments>();
		ArrayList<ReceivedDocuments> coApplicantspliteddocuemnts = new ArrayList<ReceivedDocuments>();

		ArrayList<ReceivedDocuments> propertysplitedDocuemnts = new ArrayList<ReceivedDocuments>();

		ArrayList<ReceivedDocuments> downPaymentsplitedDocuemnts = new ArrayList<ReceivedDocuments>();
		ArrayList<ReceivedDocuments> misslenioussplitedDocuemnts = new ArrayList<ReceivedDocuments>();
		Odoo odoo = new Odoo();

		ArrayList<String> docuemnts = new ArrayList<String>();
		CouchBaseOperation couchBaseOperation = new CouchBaseOperation();
		ArrayList<OpportunityList> OpportunityList = new Odoo()
				.getOpprunityDetials();

		String opportunityName = "";

		String applicantname = "";
		String coApplicantName = "";

		Opprtunity opporunitysearchedData = odoo
				.getOpprtunityData(opportunitySerachbleName);
		ArrayList<Applicants> applicantsDetailsListForEmail = new ArrayList<Applicants>();

		if (opporunitysearchedData != null) {
			opprtunity = opporunitysearchedData.getOpprtunityId();
			opportunityName = opporunitysearchedData.getOpprtunityName();
			if (opporunitysearchedData.getApplicants() != null) {
				Set<Applicants> applicantss = opporunitysearchedData
						.getApplicants();

				Iterator<Applicants> appl = applicantss.iterator();
				int k = 0;
				while (appl.hasNext()) {
					Applicants applican = appl.next();
					Applicants applicantsForEMail = new Applicants();
					String applicantNameSplited = "";
					try {
						applicantNameSplited = applican.getApplicantName();
						String a[] = applicantNameSplited.split("_");
						applicantNameSplited = a[0];
					} catch (Exception e) {
				 logger.error("Error occured while connection "+e.getMessage());	
					}
					applicantsForEMail.setApplicantName(applicantNameSplited);
					applicantsForEMail.setApplicantEmail(applican
							.getApplicantEmail());

					if (k == 1) {
						coApplicantName = applican.getApplicantName();
						coapplicants.add(coApplicantName);
					} else if (k == 0) {
						applicantname = applican.getApplicantName();
						applicants.add(applican.getApplicantName());

					}
					k++;
					applicantsDetailsListForEmail.add(applicantsForEMail);
				}

			}

		}

		JSONObject doclist = null;
		try {
			doclist = couchBaseOperation
					.getCouchBaseData("DocumentListOfApplicationNo_"
							+ opprtunity);
		} catch (Exception e) {
			logger.error("Error occured in JSON  "+e.getMessage());
		}

		if (doclist != null) {
			try {
				JSONArray jsonArray = new JSONArray(doclist.get("documents")
						.toString());
				for (int i = 0; i < jsonArray.length(); i++) {
					NotRecivedDocuments notRecivedDocuments = new NotRecivedDocuments();

					notRecivedDocuments.setDocumentName(jsonArray.get(i)
							.toString());
					notRecivedDocuments.setRecived(0);

					docuemnts.add(jsonArray.get(i).toString());
					applicantdocuemnts.add(notRecivedDocuments);
				}
			} catch (Exception e) {
				logger.error("Error occured in JSON while notRecivedDocuments "+e.getMessage());
			}

			try {
				JSONArray jsonArray = new JSONArray(doclist.get("co_documents")
						.toString());
				for (int i = 0; i < jsonArray.length(); i++) {

					NotRecivedDocuments notRecivedDocuments = new NotRecivedDocuments();

					notRecivedDocuments.setDocumentName(jsonArray.get(i)
							.toString());
					notRecivedDocuments.setRecived(0);

					docuemnts.add(jsonArray.get(i).toString());
					coApplicantdocuemnts.add(notRecivedDocuments);
				}
			} catch (Exception e) {
				logger.error("Error occured in JSON while notRecived co-Documents "+e.getMessage());
			}

			try {
				JSONArray jsonArray = new JSONArray(doclist.get(
						"propertyDocments").toString());
				for (int i = 0; i < jsonArray.length(); i++) {

					NotRecivedDocuments notRecivedDocuments = new NotRecivedDocuments();

					notRecivedDocuments.setDocumentName(jsonArray.get(i)
							.toString());
					notRecivedDocuments.setRecived(0);

					docuemnts.add(jsonArray.get(i).toString());
					propertyDocuemnts.add(notRecivedDocuments);
					logger.debug("property Document--------------- -"
							+ jsonArray.get(i).toString());

				}
			} catch (Exception e) {
				logger.error("Error occured in JSON while notRecived propertyDocments "+e.getMessage());
			}

			try {
				JSONArray jsonArray = new JSONArray(doclist.get("downPayments")
						.toString());
				for (int i = 0; i < jsonArray.length(); i++) {

					NotRecivedDocuments notRecivedDocuments = new NotRecivedDocuments();

					notRecivedDocuments.setDocumentName(jsonArray.get(i)
							.toString());
					notRecivedDocuments.setRecived(0);

					docuemnts.add(jsonArray.get(i).toString());
					downPaymentDocuemnts.add(notRecivedDocuments);

					logger.debug("downpayments--------------- -"
							+ jsonArray.get(i).toString());
				}
			} catch (Exception e) {
				logger.error("Error occured in JSON while notRecived downpayments Docments "+e.getMessage());
			}

			try {
				JSONArray jsonArray = new JSONArray(doclist.get(
						"MiscellaneousDocuments").toString());
				for (int i = 0; i < jsonArray.length(); i++) {

					NotRecivedDocuments notRecivedDocuments = new NotRecivedDocuments();

					notRecivedDocuments.setDocumentName(jsonArray.get(i)
							.toString());
					notRecivedDocuments.setRecived(0);

					docuemnts.add(jsonArray.get(i).toString());
					missleniousDocuemnts.add(notRecivedDocuments);

				}
			} catch (Exception e) {
				logger.error("Error occured in JSON while notRecived missleniousDocuemnts "+e.getMessage());
			}

			logger.debug("list of propertydocument needed "
					+ propertyDocuemnts.toString());
			logger.debug("list of downdocument needed "
					+ downPaymentDocuemnts.toString());

		}

		JSONObject oldSplited = null;
		try {
			oldSplited = couchBaseOperation
					.getCouchBaseData("DocumentSplitList" + opprtunity);
			JSONArray jsonArray = new JSONArray(oldSplited.get("splitedList")
					.toString());

			try {
				for (int i = 0; i < jsonArray.length(); i++) {

					JSONObject jsonData = couchBaseOperation
							.getCouchBaseData(jsonArray.get(i).toString());
					String document = jsonData.get("DocumentName").toString();

					if (document.equalsIgnoreCase(applicantname)) {
						ReceivedDocuments recived = new ReceivedDocuments();
						recived.setDocuementName(jsonData.get("DocumentType")
								.toString());

						recived.setSplitedDate(jsonData.get(
								"Submission_Date_Time1b").toString());
						recived.setDocumentTypeContent(jsonData.get(
								"documenTypeContent").toString());
						recived.setLink(jsonData.get(
								"OriginalDocumentAttachmentID").toString());
						recived.setSplitedLink(jsonData.get("ViewerURL")
								.toString());

						recived.setSplitedDocId(jsonData.get("URL").toString());
						applicantspliteddocuemnts.add(recived);
					} else if (document.equalsIgnoreCase(coApplicantName)) {

						ReceivedDocuments recived = new ReceivedDocuments();
						recived.setDocuementName(jsonData.get("DocumentType")
								.toString());
						recived.setDocumentTypeContent(jsonData.get(
								"documenTypeContent").toString());
						recived.setSplitedDate(jsonData.get(
								"Submission_Date_Time1b").toString());
						recived.setLink(jsonData.get(
								"OriginalDocumentAttachmentID").toString());
						recived.setSplitedLink(jsonData.get("ViewerURL")
								.toString());

						recived.setSplitedDocId(jsonData.get("URL").toString());
						coApplicantspliteddocuemnts.add(recived);
					} else if (document.equalsIgnoreCase("Property Documents")) {

						ReceivedDocuments recived = new ReceivedDocuments();
						recived.setDocuementName(jsonData.get("DocumentType")
								.toString());
						recived.setDocumentTypeContent(jsonData.get(
								"documenTypeContent").toString());
						recived.setSplitedDate(jsonData.get(
								"Submission_Date_Time1b").toString());
						recived.setLink(jsonData.get(
								"OriginalDocumentAttachmentID").toString());
						recived.setSplitedLink(jsonData.get("ViewerURL")
								.toString());

						recived.setSplitedDocId(jsonData.get("URL").toString());
						propertysplitedDocuemnts.add(recived);
					} else if (document
							.equalsIgnoreCase("DownPayment Documents")) {

						ReceivedDocuments recived = new ReceivedDocuments();
						recived.setDocuementName(jsonData.get("DocumentType")
								.toString());
						recived.setDocumentTypeContent(jsonData.get(
								"documenTypeContent").toString());
						recived.setSplitedDate(jsonData.get(
								"Submission_Date_Time1b").toString());
						recived.setLink(jsonData.get(
								"OriginalDocumentAttachmentID").toString());
						recived.setSplitedLink(jsonData.get("ViewerURL")
								.toString());

						recived.setSplitedDocId(jsonData.get("URL").toString());
						downPaymentsplitedDocuemnts.add(recived);
					}

					else if (document
							.equalsIgnoreCase("Miscellaneous Documents")) {

						ReceivedDocuments recived = new ReceivedDocuments();
						recived.setDocuementName(jsonData.get("DocumentType")
								.toString());
						recived.setDocumentTypeContent(jsonData.get(
								"documenTypeContent").toString());
						recived.setSplitedDate(jsonData.get(
								"Submission_Date_Time1b").toString());
						recived.setLink(jsonData.get(
								"OriginalDocumentAttachmentID").toString());
						recived.setSplitedLink(jsonData.get("ViewerURL")
								.toString());

						recived.setSplitedDocId(jsonData.get("URL").toString());
						misslenioussplitedDocuemnts.add(recived);
					}

				}

			} catch (Exception e) {
				logger.error("Error occured in JSON while  not split Docuemnts "+e.getMessage());
			}
		} catch (Exception e) {
			logger.error("Error occured while  not split Docuemnts "+e.getMessage());
		}

		property.add("Property Documents");

		downpayment.add("DownPayment Documents");

		misslenious.add("Miscellaneous Documents");

		logger.debug("applcantdocsize :" + applicantdocuemnts.size()
				+ "applicantsplited docSize:"
				+ applicantspliteddocuemnts.size());

		if (applicantdocuemnts.size() != applicantspliteddocuemnts.size()) {
			for (int j = applicantspliteddocuemnts.size(); j < applicantdocuemnts
					.size(); j++) {
				ReceivedDocuments receivedDocuments = new ReceivedDocuments();
				receivedDocuments.setDocuementName("-");
				receivedDocuments.setSplitedDate("-");
				receivedDocuments.setLink("#");
				receivedDocuments.setRecived(0);

				applicantspliteddocuemnts.add(receivedDocuments);
			}

		}

		logger.debug("coApplicantdocuemnts :"
				+ coApplicantdocuemnts.size()
				+ "coApplicantspliteddocuemnts docSize:"
				+ coApplicantspliteddocuemnts.size());

		if (coApplicantdocuemnts.size() != coApplicantspliteddocuemnts.size()) {
			for (int j = coApplicantspliteddocuemnts.size(); j < coApplicantdocuemnts
					.size(); j++) {
				ReceivedDocuments receivedDocuments = new ReceivedDocuments();
				receivedDocuments.setDocuementName("-");
				receivedDocuments.setSplitedDate("-");
				receivedDocuments.setLink("#");
				receivedDocuments.setRecived(0);

				coApplicantspliteddocuemnts.add(receivedDocuments);
			}

		}

		logger.debug("coApplicantdocuemnts :"
				+ coApplicantdocuemnts.size()
				+ "coApplicantspliteddocuemnts docSize:"
				+ coApplicantspliteddocuemnts.size());

		logger.debug("propertyDocuemnts :" + propertyDocuemnts.size()
				+ "propertysplitedDocuemnts docSize:"
				+ propertysplitedDocuemnts.size());

		if (propertyDocuemnts.size() != propertysplitedDocuemnts.size()) {
			for (int j = propertysplitedDocuemnts.size(); j < propertyDocuemnts
					.size(); j++) {
				ReceivedDocuments receivedDocuments = new ReceivedDocuments();
				receivedDocuments.setDocuementName("-");
				receivedDocuments.setSplitedDate("-");
				receivedDocuments.setLink("#");
				receivedDocuments.setRecived(0);

				propertysplitedDocuemnts.add(receivedDocuments);
			}

		}
		logger.debug("propertyDocuemnts :" + propertyDocuemnts.size()
				+ "propertysplitedDocuemnts docSize:"
				+ propertysplitedDocuemnts.size());

		logger.debug("downPaymentDocuemnts :"
				+ downPaymentDocuemnts.size()
				+ "downPaymentsplitedDocuemnts docSize:"
				+ downPaymentsplitedDocuemnts.size());

		if (downPaymentDocuemnts.size() != downPaymentsplitedDocuemnts.size()) {
			for (int j = downPaymentsplitedDocuemnts.size(); j < downPaymentDocuemnts
					.size(); j++) {
				ReceivedDocuments receivedDocuments = new ReceivedDocuments();
				receivedDocuments.setDocuementName("-");
				receivedDocuments.setSplitedDate("-");
				receivedDocuments.setLink("#");
				receivedDocuments.setRecived(0);

				downPaymentsplitedDocuemnts.add(receivedDocuments);
			}

		}
		logger.debug("downPaymentDocuemnts :"
				+ downPaymentDocuemnts.size()
				+ "downPaymentsplitedDocuemnts docSize:"
				+ downPaymentsplitedDocuemnts.size());

		logger.debug("missleniousDocuemnts :"
				+ missleniousDocuemnts.size()
				+ "misslenioussplitedDocuemnts docSize:"
				+ misslenioussplitedDocuemnts.size());

		if (missleniousDocuemnts.size() != misslenioussplitedDocuemnts.size()) {
			for (int j = misslenioussplitedDocuemnts.size(); j < missleniousDocuemnts
					.size(); j++) {
				ReceivedDocuments receivedDocuments = new ReceivedDocuments();
				receivedDocuments.setDocuementName("-");
				receivedDocuments.setSplitedDate("-");
				receivedDocuments.setLink("#");
				receivedDocuments.setRecived(0);

				misslenioussplitedDocuemnts.add(receivedDocuments);
			}

		}

		// check for not recived applicantdocuemnts

		int size = applicantdocuemnts.size();
		int size2 = applicantspliteddocuemnts.size();
		int downlaod = 0;

		ArrayList<ReceivedDocuments> recivedApplicantDocarrayList = new ArrayList<ReceivedDocuments>();
		/*
		 * for(int y=0;y<size;y++){ ReceivedDocuments receivedDocuments = new
		 * ReceivedDocuments(); receivedDocuments.setDocuementName("-");
		 * receivedDocuments.setSplitedDate("-");
		 * receivedDocuments.setLink("#");
		 * recivedApplicantDocarrayList.add(receivedDocuments); }
		 */
		for (int k = 0; k < size; k++) {
			NotRecivedDocuments notrecived = applicantdocuemnts.get(k);
			logger.debug("applicant docuemnts " + k);

			ReceivedDocuments recivedDocuements = new ReceivedDocuments();
			recivedDocuements.setDocuementName(notrecived.getDocumentName());

			for (int z = 0; z < size2; z++) {
				ReceivedDocuments recieved = applicantspliteddocuemnts.get(z);

				logger.debug("not recived  " + z);
				recivedDocuements.setRecived(0);
				recivedDocuements.setRecivedDocuments(recieved
						.getDocuementName());
				recivedDocuements.setSplitedDate(recieved.getSplitedDate());
				recivedDocuements.setLink(recieved.getLink());
				recivedDocuements.setSplitedLink(recieved.getSplitedLink());
				recivedDocuements.setSplitedDocId(recieved.getSplitedDocId());
				if (notrecived.getDocumentName().equalsIgnoreCase(
						recieved.getDocumentTypeContent())) {
					recivedDocuements.setRecived(1);
					recivedDocuements.setRecivedDocuments(recieved
							.getDocuementName());
					if (downlaod == 0) {
						downlaod = 1;
					}
					recivedDocuements.setSplitedDate(recieved.getSplitedDate());
					recivedDocuements.setLink(recieved.getLink());
					recivedDocuements.setSplitedLink(recieved.getSplitedLink());
					recivedDocuements.setSplitedDocId(recieved
							.getSplitedDocId());
					// applicantdocuemnts.add(k, notrecived);
					break;
				}

			}

			recivedApplicantDocarrayList.add(recivedDocuements);

		}
		logger.debug("size of array list "
				+ recivedApplicantDocarrayList.size());

		size = coApplicantdocuemnts.size();
		size2 = coApplicantspliteddocuemnts.size();

		ArrayList<ReceivedDocuments> recivedCoApplicantDocarrayList = new ArrayList<ReceivedDocuments>();

		for (int k = 0; k < size; k++) {
			NotRecivedDocuments notrecived = coApplicantdocuemnts.get(k);
			logger.debug("applicant docuemnts " + k);

			ReceivedDocuments recivedDocuements = new ReceivedDocuments();
			recivedDocuements.setDocuementName(notrecived.getDocumentName());

			for (int z = 0; z < size2; z++) {
				ReceivedDocuments recieved = coApplicantspliteddocuemnts.get(z);

				logger.debug("not recived  " + z);
				recivedDocuements.setRecived(0);
				recivedDocuements.setRecivedDocuments(recieved
						.getDocuementName());
				recivedDocuements.setSplitedDate(recieved.getSplitedDate());
				recivedDocuements.setLink(recieved.getLink());
				recivedDocuements.setSplitedLink(recieved.getSplitedLink());
				recivedDocuements.setSplitedDocId(recieved.getSplitedDocId());
				if (notrecived.getDocumentName().equalsIgnoreCase(
						recieved.getDocumentTypeContent())) {
					recivedDocuements.setRecived(1);
					recivedDocuements.setRecivedDocuments(recieved
							.getDocuementName());
					if (downlaod == 0) {
						downlaod = 1;
					}
					recivedDocuements.setSplitedDate(recieved.getSplitedDate());
					recivedDocuements.setLink(recieved.getLink());
					recivedDocuements.setSplitedLink(recieved.getSplitedLink());
					recivedDocuements.setSplitedDocId(recieved
							.getSplitedDocId());
					// applicantdocuemnts.add(k, notrecived);
					break;
				}

			}

			recivedCoApplicantDocarrayList.add(recivedDocuements);

		}

		size = propertyDocuemnts.size();
		size2 = propertysplitedDocuemnts.size();
		ArrayList<ReceivedDocuments> recivedpropertyDocuemntsarrayList = new ArrayList<ReceivedDocuments>();

		for (int k = 0; k < size; k++) {
			NotRecivedDocuments notrecived = propertyDocuemnts.get(k);
			logger.debug("applicant docuemnts " + k);

			ReceivedDocuments recivedDocuements = new ReceivedDocuments();
			recivedDocuements.setDocuementName(notrecived.getDocumentName());

			for (int z = 0; z < size2; z++) {
				ReceivedDocuments recieved = propertysplitedDocuemnts.get(z);

				logger.debug("not recived  " + z);
				recivedDocuements.setRecived(0);
				recivedDocuements.setRecivedDocuments(recieved
						.getDocuementName());
				recivedDocuements.setSplitedDate(recieved.getSplitedDate());
				recivedDocuements.setLink(recieved.getLink());
				recivedDocuements.setSplitedLink(recieved.getSplitedLink());

				recivedDocuements.setSplitedDocId(recieved.getSplitedDocId());
				if (notrecived.getDocumentName().equalsIgnoreCase(
						recieved.getDocumentTypeContent())) {
					recivedDocuements.setRecived(1);
					recivedDocuements.setRecivedDocuments(recieved
							.getDocuementName());
					if (downlaod == 0) {
						downlaod = 1;
					}
					recivedDocuements.setSplitedDate(recieved.getSplitedDate());
					recivedDocuements.setLink(recieved.getLink());
					recivedDocuements.setSplitedLink(recieved.getSplitedLink());
					recivedDocuements.setSplitedDocId(recieved
							.getSplitedDocId());
					// applicantdocuemnts.add(k, notrecived);
					break;
				}

			}

			recivedpropertyDocuemntsarrayList.add(recivedDocuements);

		}

		size = downPaymentDocuemnts.size();
		size2 = downPaymentsplitedDocuemnts.size();
		ArrayList<ReceivedDocuments> reciveddownPaymentDocuemntsarrayList = new ArrayList<ReceivedDocuments>();

		for (int k = 0; k < size; k++) {
			NotRecivedDocuments notrecived = downPaymentDocuemnts.get(k);
			logger.debug("applicant docuemnts " + k);

			ReceivedDocuments recivedDocuements = new ReceivedDocuments();
			recivedDocuements.setDocuementName(notrecived.getDocumentName());

			for (int z = 0; z < size2; z++) {
				ReceivedDocuments recieved = downPaymentsplitedDocuemnts.get(z);

				logger.debug("not recived  " + z);
				recivedDocuements.setRecived(0);
				recivedDocuements.setRecivedDocuments(recieved
						.getDocuementName());
				recivedDocuements.setSplitedDate(recieved.getSplitedDate());
				recivedDocuements.setLink(recieved.getLink());
				recivedDocuements.setSplitedLink(recieved.getSplitedLink());

				recivedDocuements.setSplitedDocId(recieved.getSplitedDocId());
				if (notrecived.getDocumentName().equalsIgnoreCase(
						recieved.getDocumentTypeContent())) {
					recivedDocuements.setRecived(1);
					recivedDocuements.setRecivedDocuments(recieved
							.getDocuementName());
					if (downlaod == 0) {
						downlaod = 1;
					}
					recivedDocuements.setSplitedDate(recieved.getSplitedDate());
					recivedDocuements.setLink(recieved.getLink());
					recivedDocuements.setSplitedLink(recieved.getSplitedLink());
					recivedDocuements.setSplitedDocId(recieved
							.getSplitedDocId());
					// applicantdocuemnts.add(k, notrecived);
					break;
				}

			}

			reciveddownPaymentDocuemntsarrayList.add(recivedDocuements);

		}

		size = missleniousDocuemnts.size();
		size2 = misslenioussplitedDocuemnts.size();
		ArrayList<ReceivedDocuments> recivedmissleniousDocuemntsarrayList = new ArrayList<ReceivedDocuments>();

		for (int k = 0; k < size; k++) {
			NotRecivedDocuments notrecived = missleniousDocuemnts.get(k);
			logger.debug("applicant docuemnts " + k);

			ReceivedDocuments recivedDocuements = new ReceivedDocuments();
			recivedDocuements.setDocuementName(notrecived.getDocumentName());

			for (int z = 0; z < size2; z++) {
				ReceivedDocuments recieved = misslenioussplitedDocuemnts.get(z);

				logger.debug("not recived  " + z);
				recivedDocuements.setRecived(0);
				recivedDocuements.setRecivedDocuments(recieved
						.getDocuementName());
				recivedDocuements.setSplitedDate(recieved.getSplitedDate());
				recivedDocuements.setLink(recieved.getLink());
				recivedDocuements.setSplitedLink(recieved.getSplitedLink());
				recivedDocuements.setSplitedDocId(recieved.getSplitedDocId());

				if (notrecived.getDocumentName().equalsIgnoreCase(
						recieved.getDocumentTypeContent())) {
					recivedDocuements.setRecived(1);
					recivedDocuements.setRecivedDocuments(recieved
							.getDocuementName());
					if (downlaod == 0) {
						downlaod = 1;
					}
					recivedDocuements.setSplitedDate(recieved.getSplitedDate());
					recivedDocuements.setLink(recieved.getLink());
					recivedDocuements.setSplitedLink(recieved.getSplitedLink());
					recivedDocuements.setSplitedDocId(recieved
							.getSplitedDocId());
					// applicantdocuemnts.add(k, notrecived);
					break;
				}

			}

			recivedmissleniousDocuemntsarrayList.add(recivedDocuements);

		}

		ArrayList<String> applicantMailDoclst = new ArrayList<String>();
		ArrayList<String> coapplicantMailDoclst = new ArrayList<String>();

		ArrayList<String> propertyMailDoclst = new ArrayList<String>();

		ArrayList<String> downpaymentMailDoclst = new ArrayList<String>();

		ArrayList<String> missleniosMailDoclst = new ArrayList<String>();

		for (int k = 0; k < recivedApplicantDocarrayList.size(); k++) {
			ReceivedDocuments recived = recivedApplicantDocarrayList.get(k);
			if (recived.getRecived() == 0) {
				applicantMailDoclst.add(recived.getDocuementName());
			}
		}

		logger.debug("recivedCoApplicantDocarrayList------------------------"
						+ recivedCoApplicantDocarrayList);
		for (int k = 0; k < recivedCoApplicantDocarrayList.size(); k++) {
			ReceivedDocuments recived = recivedCoApplicantDocarrayList.get(k);

			if (recived.getRecived() == 0) {
				coapplicantMailDoclst.add(recived.getDocuementName());
			}
		}
		for (int k = 0; k < recivedpropertyDocuemntsarrayList.size(); k++) {
			ReceivedDocuments recived = recivedpropertyDocuemntsarrayList
					.get(k);
			if (recived.getRecived() == 0) {
				propertyMailDoclst.add(recived.getDocuementName());
			}
		}

		for (int k = 0; k < reciveddownPaymentDocuemntsarrayList.size(); k++) {
			ReceivedDocuments recived = reciveddownPaymentDocuemntsarrayList
					.get(k);
			if (recived.getRecived() == 0) {
				downpaymentMailDoclst.add(recived.getDocuementName());
			}
		}
		for (int k = 0; k < recivedmissleniousDocuemntsarrayList.size(); k++) {
			ReceivedDocuments recived = recivedmissleniousDocuemntsarrayList
					.get(k);
			if (recived.getRecived() == 0) {
				missleniosMailDoclst.add(recived.getDocuementName());
			}
		}

		Set<String> aplicantMaildoclist = new LinkedHashSet<String>(
				applicantMailDoclst);
		Set<String> coaplicantMaildoclist = new LinkedHashSet<String>(
				coapplicantMailDoclst);
		Set<String> propertyMaildoclist = new LinkedHashSet<String>(
				propertyMailDoclst);
		Set<String> downpaymentMaildoclist = new LinkedHashSet<String>(
				downpaymentMailDoclst);
		Set<String> misselenuosMaildoclist = new LinkedHashSet<String>(
				missleniosMailDoclst);

		try {

			new SendWithUsMail().mailTemplateOfRemianderDocuments(
					applicantsDetailsListForEmail, aplicantMaildoclist,
					coaplicantMaildoclist, propertyMaildoclist,
					downpaymentMaildoclist, misselenuosMaildoclist);
			logger.debug("mail send sucessss-------------------"
					+ coaplicantMaildoclist);
		} catch (Exception e) {
			logger.error("Error occured while splitDocument "+e.getMessage());
		}
		logger.debug("missleniousDocuemnts :"
				+ missleniousDocuemnts.size()
				+ "misslenioussplitedDocuemnts docSize:"
				+ misslenioussplitedDocuemnts.size());

		logger.debug(" afterdocumentSize " + applicantdocuemnts.size()
				+ "splitedsize " + applicantspliteddocuemnts.size());

		return ok(doc.render(opportunityName, OpportunityList, applicants,
				coapplicants, property, downpayment, misslenious,
				recivedApplicantDocarrayList, recivedCoApplicantDocarrayList,
				recivedpropertyDocuemntsarrayList,
				reciveddownPaymentDocuemntsarrayList,
				recivedmissleniousDocuemntsarrayList, opprtunity, downlaod,
				docuemnts));

	}

	public static Result addExtraDocuements() throws JSONException, IOException {

		String applicantDoc[] = null;

		String coApplicantDoc[] = null;

		String propertyDoc[] = null;

		String downpaymentDoc[] = null;

		String mislinusDoc[] = null;
		try {
			applicantDoc = request().body().asFormUrlEncoded().get("applicant");
		} catch (Exception e) {
			logger.error("Error occured in addExtraDocuements"+e.getMessage());
		}
		try {
			coApplicantDoc = request().body().asFormUrlEncoded()
					.get("coapplicant");
		} catch (Exception e) {
			logger.error("Error occured in addExtra co-Docuements"+e.getMessage());
		}

		try {

			propertyDoc = request().body().asFormUrlEncoded().get("property");
		} catch (Exception e) {
			logger.error("Error occured in addExtra property-Docuements"+e.getMessage());
		}
		try {
			downpaymentDoc = request().body().asFormUrlEncoded()
					.get("downpayment");
		} catch (Exception e) {
			logger.error("Error occured in addExtra downpayment-Docuements"+e.getMessage());
		}

		try {
			mislinusDoc = request().body().asFormUrlEncoded().get("mislinuos");
		} catch (Exception e) {
			logger.error("Error occured in addExtra mislinuos-Docuements"+e.getMessage());
		}
		Map<String, String> dynamicForm = form().bindFromRequest().data();
		logger.debug("dynamicform",dynamicForm);
		logger.debug(dynamicForm.get("applicant"));

		String opportunitySerachbleName = dynamicForm.get("opportunity");
		logger.debug("opporunity " + opportunitySerachbleName);

		String opprtunity = "";

		// request().getQueryString("id");
		ArrayList<String> applicants = new ArrayList<String>();
		ArrayList<String> coapplicants = new ArrayList<String>();
		ArrayList<String> property = new ArrayList<String>();
		ArrayList<String> downpayment = new ArrayList<String>();
		ArrayList<String> misslenious = new ArrayList<String>();

		ArrayList<NotRecivedDocuments> applicantdocuemnts = new ArrayList<NotRecivedDocuments>();
		ArrayList<NotRecivedDocuments> coApplicantdocuemnts = new ArrayList<NotRecivedDocuments>();

		ArrayList<NotRecivedDocuments> propertyDocuemnts = new ArrayList<NotRecivedDocuments>();

		ArrayList<NotRecivedDocuments> downPaymentDocuemnts = new ArrayList<NotRecivedDocuments>();
		ArrayList<NotRecivedDocuments> missleniousDocuemnts = new ArrayList<NotRecivedDocuments>();

		ArrayList<ReceivedDocuments> applicantspliteddocuemnts = new ArrayList<ReceivedDocuments>();
		ArrayList<ReceivedDocuments> coApplicantspliteddocuemnts = new ArrayList<ReceivedDocuments>();

		ArrayList<ReceivedDocuments> propertysplitedDocuemnts = new ArrayList<ReceivedDocuments>();

		ArrayList<ReceivedDocuments> downPaymentsplitedDocuemnts = new ArrayList<ReceivedDocuments>();
		ArrayList<ReceivedDocuments> misslenioussplitedDocuemnts = new ArrayList<ReceivedDocuments>();
		Odoo odoo = new Odoo();
		ArrayList<String>

		docuemnts = new ArrayList<String>();
		CouchBaseOperation couchBaseOperation = new CouchBaseOperation();
		ArrayList<OpportunityList> OpportunityList = new Odoo()
				.getOpprunityDetials();

		String opportunityName = "";

		String applicantname = "";
		String coApplicantName = "";

		Opprtunity opporunitysearchedData = odoo
				.getOpprtunityData(opportunitySerachbleName);

		if (opporunitysearchedData != null) {
			opprtunity = opporunitysearchedData.getOpprtunityId();
			opportunityName = opporunitysearchedData.getOpprtunityName();
			if (opporunitysearchedData.getApplicants() != null) {
				Set<Applicants> applicantss = opporunitysearchedData
						.getApplicants();

				Iterator<Applicants> appl = applicantss.iterator();
				int k = 0;
				while (appl.hasNext()) {
					Applicants applican = appl.next();

					if (k == 1) {
						coApplicantName = applican.getApplicantName();
						coapplicants.add(coApplicantName);
					} else if (k == 0) {
						applicantname = applican.getApplicantName();
						applicants.add(applican.getApplicantName());

					}
					k++;
				}

			}

		}

		JSONObject doclist = null;
		try {
			doclist = couchBaseOperation
					.getCouchBaseData("DocumentListOfApplicationNo_"
							+ opprtunity);
		} catch (Exception e) {
			logger.error("Error occured in JSON"+e.getMessage());
		}

		if (doclist != null) {

			try {
				JSONArray jsonArray = new JSONArray(doclist.get("documents")
						.toString());
				try {
					if (applicantDoc != null) {
						for (int i = 0; i < applicantDoc.length; i++) {
							jsonArray.put(applicantDoc[i]);
						}
					}
				} catch (Exception e) {
					logger.error("Error occured in not get document"+e.getMessage());
				}
				doclist.put("documents", jsonArray);
				couchBaseOperation.editDataInCouchBase(
						"DocumentListOfApplicationNo_" + opprtunity, doclist);

			} catch (Exception e) {
				try {
					JSONArray jsonArray = new JSONArray();

					try {
						if (applicantDoc != null) {
							for (int i = 0; i < applicantDoc.length; i++) {
								jsonArray.put(applicantDoc[i]);
							}
						}
					} catch (Exception e2) {
						logger.error("Error occured in JSON"+e2.getMessage());
					}
					doclist.put("documents", jsonArray);
					couchBaseOperation.editDataInCouchBase(
							"DocumentListOfApplicationNo_" + opprtunity,
							doclist);
				} catch (Exception e1) {
					logger.error("Error occured in JSON"+e1.getMessage());
				}
				logger.error("Error occured in sendMail"+e.getMessage());
			}

			try {
				JSONArray jsonArray = new JSONArray(doclist.get("documents")
						.toString());
				for (int i = 0; i < jsonArray.length(); i++) {

					NotRecivedDocuments notRecivedDocuments = new NotRecivedDocuments();

					notRecivedDocuments.setDocumentName(jsonArray.get(i)
							.toString());

					docuemnts.add(jsonArray.get(i).toString());
					notRecivedDocuments.setRecived(0);
					applicantdocuemnts.add(notRecivedDocuments);

				}
			} catch (Exception e) {
				logger.error("Error occured in sendMail"+e.getMessage());
			}

			try {

				try {
					JSONArray jsonArray = new JSONArray(doclist.get(
							"co_documents").toString());
					try {
						if (coApplicantDoc != null) {
							for (int i = 0; i < coApplicantDoc.length; i++) {
								jsonArray.put(coApplicantDoc[i]);
							}
						}
					} catch (Exception e) {
						logger.error("Error occured in sendMail of co-document"+e.getMessage());
					}
					doclist.put("co_documents", jsonArray);

					couchBaseOperation.editDataInCouchBase(
							"DocumentListOfApplicationNo_" + opprtunity,
							doclist);

				} catch (Exception e) {
					try {

						JSONArray jsonArray = new JSONArray();
						try {
							if (coApplicantDoc != null) {
								for (int i = 0; i < coApplicantDoc.length; i++) {
									jsonArray.put(coApplicantDoc[i]);
								}
							}
						} catch (Exception e3) {
							logger.error("Error occured in sendMail of coApplicantDoc"+e3.getMessage());
						}
						doclist.put("co_documents", jsonArray);
						couchBaseOperation.editDataInCouchBase(
								"DocumentListOfApplicationNo_" + opprtunity,
								doclist);

					} catch (Exception e1) {
						logger.error("Error occured in sendMail of coApplicantDoc"+e1.getMessage());
					}
					logger.error("Error occured in sendMail"+e.getMessage());
				}
				JSONArray jsonArray = new JSONArray(doclist.get("co_documents")
						.toString());
				for (int i = 0; i < jsonArray.length(); i++) {

					NotRecivedDocuments notRecivedDocuments = new NotRecivedDocuments();

					notRecivedDocuments.setDocumentName(jsonArray.get(i)
							.toString());

					docuemnts.add(jsonArray.get(i).toString());
					notRecivedDocuments.setRecived(0);
					coApplicantdocuemnts.add(notRecivedDocuments);
				}
			} catch (Exception e) {
				logger.error("Error occured in sendMail of co-document"+e.getMessage());
			}

			try {

				try {
					JSONArray jsonArray = new JSONArray(doclist.get(
							"propertyDocments").toString());
					try {
						if (propertyDoc != null) {
							for (int i = 0; i < propertyDoc.length; i++) {
								jsonArray.put(propertyDoc[i]);
							}
						}
					} catch (Exception e) {
						logger.error("Error occured in sendMail of propertyDocments"+e.getMessage());
					}
					doclist.put("propertyDocments", jsonArray);
					couchBaseOperation.editDataInCouchBase(
							"DocumentListOfApplicationNo_" + opprtunity,
							doclist);

				} catch (Exception e) {
					try {
						JSONArray jsonArray = new JSONArray();
						try {
							if (propertyDoc != null) {
								for (int i = 0; i < propertyDoc.length; i++) {
									jsonArray.put(propertyDoc[i]);
								}
							}
						} catch (Exception e4) {
							logger.error("Error occured in sendMail of DocumentListOfApplicationNo_"+e4.getMessage());
						}
						doclist.put("propertyDocments", jsonArray);
						couchBaseOperation.editDataInCouchBase(
								"DocumentListOfApplicationNo_" + opprtunity,
								doclist);
					} catch (Exception e2) {
						logger.error("Error occured in sendMail of DocumentListOfApplicationNo_"+e2.getMessage());
					}
					logger.error("Error occured in sendMail of DocumentListOfApplicationNo_"+e.getMessage());
				}

				JSONArray jsonArray = new JSONArray(doclist.get(
						"propertyDocments").toString());
				for (int i = 0; i < jsonArray.length(); i++) {

					NotRecivedDocuments notRecivedDocuments = new NotRecivedDocuments();

					notRecivedDocuments.setDocumentName(jsonArray.get(i)
							.toString());

					docuemnts.add(jsonArray.get(i).toString());
					notRecivedDocuments.setRecived(0);
					propertyDocuemnts.add(notRecivedDocuments);

					logger.debug("property Document--------------- -"
							+ jsonArray.get(i).toString());

				}
			} catch (Exception e) {
				logger.error("Error occured in JSON"+e.getMessage());
			}

			try {

				try {
					JSONArray jsonArray = new JSONArray(doclist.get(
							"downPayments").toString());
					try {
						if (downpaymentDoc != null) {
							for (int i = 0; i < downpaymentDoc.length; i++) {
								jsonArray.put(downpaymentDoc[i]);
							}
						}
					} catch (Exception e2) {
						logger.error("Error occured in sendmail of downPayment doc"+e2.getMessage());
					}
					doclist.put("downPayments", jsonArray);
					couchBaseOperation.editDataInCouchBase(
							"DocumentListOfApplicationNo_" + opprtunity,
							doclist);

				} catch (Exception e) {

					try {

						JSONArray jsonArray = new JSONArray();
						try {
							if (downpaymentDoc != null) {
								for (int i = 0; i < downpaymentDoc.length; i++) {
									jsonArray.put(downpaymentDoc[i]);
								}
							}
						} catch (Exception ew) {
							logger.error("Error occured in sendmail of downPayment doc"+ew.getMessage());
						}
						doclist.put("downPayments", jsonArray);
						couchBaseOperation.editDataInCouchBase(
								"DocumentListOfApplicationNo_" + opprtunity,
								doclist);

					} catch (Exception e2) {
						logger.error("Error occured in sendmail of downPayment doc"+e2.getMessage());
					}
					logger.error("Error occured in sendmail"+e.getMessage());
				}

				JSONArray jsonArray = new JSONArray(doclist.get("downPayments")
						.toString());
				for (int i = 0; i < jsonArray.length(); i++) {

					NotRecivedDocuments notRecivedDocuments = new NotRecivedDocuments();

					notRecivedDocuments.setDocumentName(jsonArray.get(i)
							.toString());

					docuemnts.add(jsonArray.get(i).toString());
					notRecivedDocuments.setRecived(0);
					downPaymentDocuemnts.add(notRecivedDocuments);
					logger.debug("downpayments--------------- -"
							+ jsonArray.get(i).toString());
				}
			} catch (Exception e) {
				logger.error("Error occured in sendmail of downpayments"+e.getMessage());
			}

			try {

				try {
					JSONArray jsonArray = new JSONArray(doclist.get(
							"MiscellaneousDocuments").toString());
					try {
						if (mislinusDoc != null) {
							for (int i = 0; i < mislinusDoc.length; i++) {
								jsonArray.put(mislinusDoc[i]);
							}
						}
					} catch (Exception ew) {
						logger.error("Error occured in sendmail of MiscellaneousDocuments"+ew.getMessage());
					}
					doclist.put("MiscellaneousDocuments", jsonArray);
					couchBaseOperation.editDataInCouchBase(
							"DocumentListOfApplicationNo_" + opprtunity,
							doclist);

				} catch (Exception e) {
					try {
						JSONArray jsonArray = new JSONArray();
						try {
							if (mislinusDoc != null) {
								for (int i = 0; i < mislinusDoc.length; i++) {
									jsonArray.put(mislinusDoc[i]);
								}
							}
						} catch (Exception ee) {
							logger.error("Error occured in sendmail of MiscellaneousDocuments"+ee.getMessage());
						}
						doclist.put("MiscellaneousDocuments", jsonArray);
						couchBaseOperation.editDataInCouchBase(
								"DocumentListOfApplicationNo_" + opprtunity,
								doclist);
					} catch (Exception e2) {
						logger.error("Error occured in sendmail of MiscellaneousDocuments"+e2.getMessage());
					}
					logger.error("Error occured in sendmail "+e.getMessage());
				}

				JSONArray jsonArray = new JSONArray(doclist.get(
						"MiscellaneousDocuments").toString());
				for (int i = 0; i < jsonArray.length(); i++) {

					NotRecivedDocuments notRecivedDocuments = new NotRecivedDocuments();

					notRecivedDocuments.setDocumentName(jsonArray.get(i)
							.toString());

					docuemnts.add(jsonArray.get(i).toString());
					notRecivedDocuments.setRecived(0);
					missleniousDocuemnts.add(notRecivedDocuments);

				}
			} catch (Exception e) {
				logger.error("Error occured in sendmail "+e.getMessage());
			}

			logger.debug("list of propertydocument needed "
					+ propertyDocuemnts.toString());
			logger.debug("list of downdocument needed "
					+ downPaymentDocuemnts.toString());

		}

		JSONObject oldSplited = null;
		try {
			oldSplited = couchBaseOperation
					.getCouchBaseData("DocumentSplitList" + opprtunity);
			JSONArray jsonArray = new JSONArray(oldSplited.get("splitedList")
					.toString());

			try {
				for (int i = 0; i < jsonArray.length(); i++) {

					JSONObject jsonData = couchBaseOperation
							.getCouchBaseData(jsonArray.get(i).toString());
					String document = jsonData.get("DocumentName").toString();

					if (document.equalsIgnoreCase(applicantname)) {
						ReceivedDocuments recived = new ReceivedDocuments();
						recived.setDocuementName(jsonData.get("DocumentType")
								.toString());

						recived.setSplitedDate(jsonData.get(
								"Submission_Date_Time1b").toString());
						recived.setDocumentTypeContent(jsonData.get(
								"documenTypeContent").toString());
						recived.setLink(jsonData.get(
								"OriginalDocumentAttachmentID").toString());
						recived.setSplitedLink(jsonData.get("ViewerURL")
								.toString());

						recived.setSplitedDocId(jsonData.get("URL").toString());
						applicantspliteddocuemnts.add(recived);
					} else if (document.equalsIgnoreCase(coApplicantName)) {

						ReceivedDocuments recived = new ReceivedDocuments();
						recived.setDocuementName(jsonData.get("DocumentType")
								.toString());
						recived.setDocumentTypeContent(jsonData.get(
								"documenTypeContent").toString());
						recived.setSplitedDate(jsonData.get(
								"Submission_Date_Time1b").toString());
						recived.setLink(jsonData.get(
								"OriginalDocumentAttachmentID").toString());
						recived.setSplitedLink(jsonData.get("ViewerURL")
								.toString());

						recived.setSplitedDocId(jsonData.get("URL").toString());
						coApplicantspliteddocuemnts.add(recived);
					} else if (document.equalsIgnoreCase("Property Documents")) {

						ReceivedDocuments recived = new ReceivedDocuments();
						recived.setDocuementName(jsonData.get("DocumentType")
								.toString());
						recived.setDocumentTypeContent(jsonData.get(
								"documenTypeContent").toString());
						recived.setSplitedDate(jsonData.get(
								"Submission_Date_Time1b").toString());
						recived.setLink(jsonData.get(
								"OriginalDocumentAttachmentID").toString());
						recived.setSplitedLink(jsonData.get("ViewerURL")
								.toString());

						recived.setSplitedDocId(jsonData.get("URL").toString());
						propertysplitedDocuemnts.add(recived);
					} else if (document
							.equalsIgnoreCase("DownPayment Documents")) {

						ReceivedDocuments recived = new ReceivedDocuments();
						recived.setDocuementName(jsonData.get("DocumentType")
								.toString());
						recived.setDocumentTypeContent(jsonData.get(
								"documenTypeContent").toString());
						recived.setSplitedDate(jsonData.get(
								"Submission_Date_Time1b").toString());
						recived.setLink(jsonData.get(
								"OriginalDocumentAttachmentID").toString());
						recived.setSplitedLink(jsonData.get("ViewerURL")
								.toString());

						recived.setSplitedDocId(jsonData.get("URL").toString());
						downPaymentsplitedDocuemnts.add(recived);
					}

					else if (document
							.equalsIgnoreCase("Miscellaneous Documents")) {

						ReceivedDocuments recived = new ReceivedDocuments();
						recived.setDocuementName(jsonData.get("DocumentType")
								.toString());
						recived.setDocumentTypeContent(jsonData.get(
								"documenTypeContent").toString());
						recived.setSplitedDate(jsonData.get(
								"Submission_Date_Time1b").toString());
						recived.setLink(jsonData.get(
								"OriginalDocumentAttachmentID").toString());
						recived.setSplitedLink(jsonData.get("ViewerURL")
								.toString());

						recived.setSplitedDocId(jsonData.get("URL").toString());
						misslenioussplitedDocuemnts.add(recived);
					}

				}

			} catch (Exception e) {
				logger.error("Error occured in sendmail JSON "+e.getMessage());
			}
		} catch (Exception e) {
			logger.error("Error occured in sendmail JSON "+e.getMessage());
		}

		property.add("Property Documents");

		downpayment.add("DownPayment Documents");

		misslenious.add("Miscellaneous Documents");

		logger.debug("applcantdocsize :" + applicantdocuemnts.size()
				+ "applicantsplited docSize:"
				+ applicantspliteddocuemnts.size());

		if (applicantdocuemnts.size() != applicantspliteddocuemnts.size()) {
			for (int j = applicantspliteddocuemnts.size(); j < applicantdocuemnts
					.size(); j++) {
				ReceivedDocuments receivedDocuments = new ReceivedDocuments();
				receivedDocuments.setDocuementName("-");
				receivedDocuments.setSplitedDate("-");
				receivedDocuments.setLink("#");
				receivedDocuments.setRecived(0);

				applicantspliteddocuemnts.add(receivedDocuments);
			}

		}

		logger.debug("coApplicantdocuemnts :"
				+ coApplicantdocuemnts.size()
				+ "coApplicantspliteddocuemnts docSize:"
				+ coApplicantspliteddocuemnts.size());

		if (coApplicantdocuemnts.size() != coApplicantspliteddocuemnts.size()) {
			for (int j = coApplicantspliteddocuemnts.size(); j < coApplicantdocuemnts
					.size(); j++) {
				ReceivedDocuments receivedDocuments = new ReceivedDocuments();
				receivedDocuments.setDocuementName("-");
				receivedDocuments.setSplitedDate("-");
				receivedDocuments.setLink("#");
				receivedDocuments.setRecived(0);

				coApplicantspliteddocuemnts.add(receivedDocuments);
			}

		}

		logger.debug("coApplicantdocuemnts :"
				+ coApplicantdocuemnts.size()
				+ "coApplicantspliteddocuemnts docSize:"
				+ coApplicantspliteddocuemnts.size());

		logger.debug("propertyDocuemnts :" + propertyDocuemnts.size()
				+ "propertysplitedDocuemnts docSize:"
				+ propertysplitedDocuemnts.size());

		if (propertyDocuemnts.size() != propertysplitedDocuemnts.size()) {
			for (int j = propertysplitedDocuemnts.size(); j < propertyDocuemnts
					.size(); j++) {
				ReceivedDocuments receivedDocuments = new ReceivedDocuments();
				receivedDocuments.setDocuementName("-");
				receivedDocuments.setSplitedDate("-");
				receivedDocuments.setLink("#");
				receivedDocuments.setRecived(0);

				propertysplitedDocuemnts.add(receivedDocuments);
			}

		}
		logger.debug("propertyDocuemnts :" + propertyDocuemnts.size()
				+ "propertysplitedDocuemnts docSize:"
				+ propertysplitedDocuemnts.size());

		logger.debug("downPaymentDocuemnts :"
				+ downPaymentDocuemnts.size()
				+ "downPaymentsplitedDocuemnts docSize:"
				+ downPaymentsplitedDocuemnts.size());

		if (downPaymentDocuemnts.size() != downPaymentsplitedDocuemnts.size()) {
			for (int j = downPaymentsplitedDocuemnts.size(); j < downPaymentDocuemnts
					.size(); j++) {
				ReceivedDocuments receivedDocuments = new ReceivedDocuments();
				receivedDocuments.setDocuementName("-");
				receivedDocuments.setSplitedDate("-");
				receivedDocuments.setLink("#");
				receivedDocuments.setRecived(0);

				downPaymentsplitedDocuemnts.add(receivedDocuments);
			}

		}
		logger.debug("downPaymentDocuemnts :"
				+ downPaymentDocuemnts.size()
				+ "downPaymentsplitedDocuemnts docSize:"
				+ downPaymentsplitedDocuemnts.size());

		logger.debug("missleniousDocuemnts :"
				+ missleniousDocuemnts.size()
				+ "misslenioussplitedDocuemnts docSize:"
				+ misslenioussplitedDocuemnts.size());

		if (missleniousDocuemnts.size() != misslenioussplitedDocuemnts.size()) {
			for (int j = misslenioussplitedDocuemnts.size(); j < missleniousDocuemnts
					.size(); j++) {
				ReceivedDocuments receivedDocuments = new ReceivedDocuments();
				receivedDocuments.setDocuementName("-");
				receivedDocuments.setSplitedDate("-");
				receivedDocuments.setLink("#");
				receivedDocuments.setRecived(0);

				misslenioussplitedDocuemnts.add(receivedDocuments);
			}

		}

		// check for not recived applicantdocuemnts

		int size = applicantdocuemnts.size();
		int size2 = applicantspliteddocuemnts.size();
		int downlaod = 0;

		ArrayList<ReceivedDocuments> recivedApplicantDocarrayList = new ArrayList<ReceivedDocuments>();
		/*
		 * for(int y=0;y<size;y++){ ReceivedDocuments receivedDocuments = new
		 * ReceivedDocuments(); receivedDocuments.setDocuementName("-");
		 * receivedDocuments.setSplitedDate("-");
		 * receivedDocuments.setLink("#");
		 * recivedApplicantDocarrayList.add(receivedDocuments); }
		 */
		for (int k = 0; k < size; k++) {
			NotRecivedDocuments notrecived = applicantdocuemnts.get(k);
			logger.debug("applicant docuemnts " + k);

			ReceivedDocuments recivedDocuements = new ReceivedDocuments();
			recivedDocuements.setDocuementName(notrecived.getDocumentName());

			for (int z = 0; z < size2; z++) {
				ReceivedDocuments recieved = applicantspliteddocuemnts.get(z);

				logger.debug("not recived  " + z);
				recivedDocuements.setRecived(0);
				recivedDocuements.setRecivedDocuments(recieved
						.getDocuementName());
				recivedDocuements.setSplitedDate(recieved.getSplitedDate());
				recivedDocuements.setLink(recieved.getLink());
				recivedDocuements.setSplitedLink(recieved.getSplitedLink());
				recivedDocuements.setSplitedDocId(recieved.getSplitedDocId());
				if (notrecived.getDocumentName().equalsIgnoreCase(
						recieved.getDocumentTypeContent())) {
					downlaod = 1;
					recivedDocuements.setRecived(1);
					recivedDocuements.setRecivedDocuments(recieved
							.getDocuementName());
					recivedDocuements.setSplitedDate(recieved.getSplitedDate());
					recivedDocuements.setLink(recieved.getLink());
					recivedDocuements.setSplitedLink(recieved.getSplitedLink());
					recivedDocuements.setSplitedDocId(recieved
							.getSplitedDocId());

					// applicantdocuemnts.add(k, notrecived);
					break;
				}

			}

			recivedApplicantDocarrayList.add(recivedDocuements);

		}
		logger.debug("size of array list "
				+ recivedApplicantDocarrayList.size());

		size = coApplicantdocuemnts.size();
		size2 = coApplicantspliteddocuemnts.size();

		ArrayList<ReceivedDocuments> recivedCoApplicantDocarrayList = new ArrayList<ReceivedDocuments>();

		for (int k = 0; k < size; k++) {
			NotRecivedDocuments notrecived = coApplicantdocuemnts.get(k);
			logger.debug("applicant docuemnts " + k);

			ReceivedDocuments recivedDocuements = new ReceivedDocuments();
			recivedDocuements.setDocuementName(notrecived.getDocumentName());

			for (int z = 0; z < size2; z++) {
				ReceivedDocuments recieved = coApplicantspliteddocuemnts.get(z);

				logger.debug("not recived  " + z);
				recivedDocuements.setRecived(0);
				recivedDocuements.setRecivedDocuments(recieved
						.getDocuementName());
				recivedDocuements.setSplitedDate(recieved.getSplitedDate());
				recivedDocuements.setLink(recieved.getLink());
				recivedDocuements.setSplitedLink(recieved.getSplitedLink());
				recivedDocuements.setSplitedDocId(recieved.getSplitedDocId());

				if (notrecived.getDocumentName().equalsIgnoreCase(
						recieved.getDocumentTypeContent())) {
					if (downlaod == 0) {
						downlaod = 1;
					}
					recivedDocuements.setRecived(1);
					recivedDocuements.setRecivedDocuments(recieved
							.getDocuementName());
					recivedDocuements.setSplitedDate(recieved.getSplitedDate());
					recivedDocuements.setLink(recieved.getLink());
					recivedDocuements.setSplitedLink(recieved.getSplitedLink());
					recivedDocuements.setSplitedDocId(recieved
							.getSplitedDocId());

					// applicantdocuemnts.add(k, notrecived);
					break;
				}

			}

			recivedCoApplicantDocarrayList.add(recivedDocuements);

		}

		size = propertyDocuemnts.size();
		size2 = propertysplitedDocuemnts.size();
		ArrayList<ReceivedDocuments> recivedpropertyDocuemntsarrayList = new ArrayList<ReceivedDocuments>();

		for (int k = 0; k < size; k++) {
			NotRecivedDocuments notrecived = propertyDocuemnts.get(k);
			logger.debug("applicant docuemnts " + k);

			ReceivedDocuments recivedDocuements = new ReceivedDocuments();
			recivedDocuements.setDocuementName(notrecived.getDocumentName());

			for (int z = 0; z < size2; z++) {
				ReceivedDocuments recieved = propertysplitedDocuemnts.get(z);

				logger.debug("not recived  " + z);
				recivedDocuements.setRecived(0);
				recivedDocuements.setRecivedDocuments(recieved
						.getDocuementName());
				recivedDocuements.setSplitedDate(recieved.getSplitedDate());
				recivedDocuements.setLink(recieved.getLink());
				recivedDocuements.setSplitedLink(recieved.getSplitedLink());
				recivedDocuements.setSplitedDocId(recieved.getSplitedDocId());

				if (notrecived.getDocumentName().equalsIgnoreCase(
						recieved.getDocumentTypeContent())) {
					recivedDocuements.setRecived(1);
					if (downlaod == 0) {
						downlaod = 1;
					}
					recivedDocuements.setRecivedDocuments(recieved
							.getDocuementName());
					recivedDocuements.setSplitedDate(recieved.getSplitedDate());
					recivedDocuements.setLink(recieved.getLink());
					recivedDocuements.setSplitedLink(recieved.getSplitedLink());
					recivedDocuements.setSplitedDocId(recieved
							.getSplitedDocId());
					// applicantdocuemnts.add(k, notrecived);
					break;
				}

			}

			recivedpropertyDocuemntsarrayList.add(recivedDocuements);

		}

		size = downPaymentDocuemnts.size();
		size2 = downPaymentsplitedDocuemnts.size();
		ArrayList<ReceivedDocuments> reciveddownPaymentDocuemntsarrayList = new ArrayList<ReceivedDocuments>();

		for (int k = 0; k < size; k++) {
			NotRecivedDocuments notrecived = downPaymentDocuemnts.get(k);
			logger.debug("applicant docuemnts " + k);

			ReceivedDocuments recivedDocuements = new ReceivedDocuments();
			recivedDocuements.setDocuementName(notrecived.getDocumentName());

			for (int z = 0; z < size2; z++) {
				ReceivedDocuments recieved = downPaymentsplitedDocuemnts.get(z);

				logger.debug("not recived  " + z);
				recivedDocuements.setRecived(0);
				recivedDocuements.setRecivedDocuments(recieved
						.getDocuementName());
				recivedDocuements.setSplitedDate(recieved.getSplitedDate());
				recivedDocuements.setLink(recieved.getLink());
				recivedDocuements.setSplitedLink(recieved.getSplitedLink());
				recivedDocuements.setSplitedDocId(recieved.getSplitedDocId());
				if (notrecived.getDocumentName().equalsIgnoreCase(
						recieved.getDocumentTypeContent())) {
					recivedDocuements.setRecived(1);
					recivedDocuements.setRecivedDocuments(recieved
							.getDocuementName());
					if (downlaod == 0) {
						downlaod = 1;
					}
					recivedDocuements.setSplitedDate(recieved.getSplitedDate());
					recivedDocuements.setLink(recieved.getLink());
					recivedDocuements.setSplitedLink(recieved.getSplitedLink());
					recivedDocuements.setSplitedDocId(recieved
							.getSplitedDocId());
					// applicantdocuemnts.add(k, notrecived);
					break;
				}

			}

			reciveddownPaymentDocuemntsarrayList.add(recivedDocuements);

		}

		size = missleniousDocuemnts.size();
		size2 = misslenioussplitedDocuemnts.size();
		ArrayList<ReceivedDocuments> recivedmissleniousDocuemntsarrayList = new ArrayList<ReceivedDocuments>();

		for (int k = 0; k < size; k++) {
			NotRecivedDocuments notrecived = missleniousDocuemnts.get(k);
			logger.debug("applicant docuemnts " + k);

			ReceivedDocuments recivedDocuements = new ReceivedDocuments();
			recivedDocuements.setDocuementName(notrecived.getDocumentName());

			for (int z = 0; z < size2; z++) {
				ReceivedDocuments recieved = misslenioussplitedDocuemnts.get(z);

				logger.debug("not recived  " + z);
				recivedDocuements.setRecived(0);
				recivedDocuements.setRecivedDocuments(recieved
						.getDocuementName());
				recivedDocuements.setSplitedDate(recieved.getSplitedDate());
				recivedDocuements.setLink(recieved.getLink());
				recivedDocuements.setSplitedLink(recieved.getSplitedLink());
				recivedDocuements.setSplitedDocId(recieved.getSplitedDocId());
				if (notrecived.getDocumentName().equalsIgnoreCase(
						recieved.getDocumentTypeContent())) {
					recivedDocuements.setRecived(1);
					recivedDocuements.setRecivedDocuments(recieved
							.getDocuementName());
					if (downlaod == 0) {
						downlaod = 1;
					}
					recivedDocuements.setSplitedDate(recieved.getSplitedDate());
					recivedDocuements.setLink(recieved.getLink());
					recivedDocuements.setSplitedLink(recieved.getSplitedLink());
					recivedDocuements.setSplitedDocId(recieved
							.getSplitedDocId());
					// applicantdocuemnts.add(k, notrecived);
					break;
				}

			}

			recivedmissleniousDocuemntsarrayList.add(recivedDocuements);

		}

		logger.debug("missleniousDocuemnts :"
				+ missleniousDocuemnts.size()
				+ "misslenioussplitedDocuemnts docSize:"
				+ misslenioussplitedDocuemnts.size());

		logger.debug(" afterdocumentSize " + applicantdocuemnts.size()
				+ "splitedsize " + applicantspliteddocuemnts.size());

		return ok(doc.render(opportunityName, OpportunityList, applicants,
				coapplicants, property, downpayment, misslenious,
				recivedApplicantDocarrayList, recivedCoApplicantDocarrayList,
				recivedpropertyDocuemntsarrayList,
				reciveddownPaymentDocuemntsarrayList,
				recivedmissleniousDocuemntsarrayList, opprtunity, downlaod,
				docuemnts));
	}
}
