package com.syml.referralsource.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;

import net.iharder.Base64;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDPixelMap;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;




import play.Logger;
import play.data.DynamicForm;

import com.couchbase.client.java.document.json.JsonArray;
import com.couchbase.client.java.document.json.JsonObject;
import com.sendwithus.SendWithUsExample;
import com.syml.Constants;
import com.syml.ReadConfigFile;
import com.syml.SplitAddress;
import com.syml.couchbase.dao.CouchbaseServiceException;
import com.syml.couchbase.dao.service.CouchBaseService;
import com.syml.hibernate.dao.IPostGresDaoService;
import com.syml.hibernate.dao.PostGressDaoServiceException;
import com.syml.hibernate.dao.impl.PostgresDAO;

import controllers.Contact;
import controllers.Referral_Source;
import controllers.VisdomReferral;

public class ReferralSourcePageService {

	/**
	 * To map second form feilds to Referral Source Pojo
	 * 
	 * @param dynamicForm
	 * @param referral_Source
	 * @return return referralSource
	 * @throws CouchbaseServiceException
	 */
	public Referral_Source loadSecondFormData(DynamicForm dynamicForm,
			Referral_Source referral_Source)
			throws CouchbaseServiceException {
		if (referral_Source == null)
			throw new CouchbaseServiceException(
					"Error Referral source data not exist in couhcbase ");

		referral_Source.setSignature(dynamicForm.get("typedName"));
		referral_Source.setElectronicSing(dynamicForm.get("sign"));
		referral_Source.setIsTouchScreeDevice(dynamicForm.get("touchScreen"));

		return referral_Source;
	}

	/**
	 * get onthraport ID for given Roles
	 * 
	 * @param referral_Source
	 * @return return onthrportid
	 * @throws CouchbaseServiceException
	 */
	public int getOntharaportId(Referral_Source referral_Source)
			throws CouchbaseServiceException {
		int ontraPortRoleId = 0;
		if (referral_Source == null)
			throw new CouchbaseServiceException(
					"Error Referral source data not exist in couhcbase ");

		if (referral_Source.getRoleName().equalsIgnoreCase("Realtor")) {
			ontraPortRoleId = 8;

		} else if (referral_Source.getRoleName().equalsIgnoreCase(
				"Financial Planner")) {

			ontraPortRoleId = 7;
		} else if (referral_Source.getRoleName().equalsIgnoreCase("Client")) {

			ontraPortRoleId = 6;
		}

		return ontraPortRoleId;

	}

	/**
	 * to map firstpage of referral source form values to Referral Source Pojo
	 * 
	 * @param dynamicForm
	 * @param arrayOfaddress
	 * @return referral source pojo
	 */
	public Referral_Source loadReferralSourceForm(DynamicForm dynamicForm,
			String[] arrayOfaddress) {
		Logger.debug("inside loadReferralSourceForm() in ReferralSourcePageService class");
		Referral_Source referral_Source = new Referral_Source();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"); // get

		Calendar cal = Calendar.getInstance();
		String currentDateTime = (dateFormat.format(cal.getTime()));
		referral_Source.setSubmission_Date_Time1b(currentDateTime);
		referral_Source.setRoleName(dynamicForm.get("role"));
		referral_Source.setName(dynamicForm.get("fname") + "_"
				+ dynamicForm.get("lname"));
		referral_Source.setPartner_mobile(dynamicForm.get("phone"));
		referral_Source.setEmail_from(dynamicForm.get("email"));
		referral_Source.setCompany(dynamicForm.get("company"));
		referral_Source.setReferralFullName(dynamicForm.get("fname") + "_"
				+ dynamicForm.get("lname"));
		referral_Source.setCompensation(dynamicForm.get("feeTo"));
		referral_Source.setFullAddress(dynamicForm.get("formatted_address"));
		referral_Source.setNameOfTheBuilder(dynamicForm.get("broker"));
		referral_Source.setPartner_phone(dynamicForm.get("brokerPhone"));
		String ip = dynamicForm.get("ip");
		referral_Source.setFirstName(dynamicForm.get("fname"));
		referral_Source.setLastName(dynamicForm.get("lname"));
		referral_Source.setReferrer(dynamicForm.get("refer"));
		Logger.debug("referral_Source --> "+referral_Source.toString());

		/**
		 * mapping ip details to Referral source taking ip details from given
		// input
		 * */
		 
		try {
			JsonObject jsonDataOfIp = JsonObject.fromJson(ip);
			
			
			/** to get ip */ 
			Logger.debug("++++++++++++++++++++++++++++++++  ");
Logger.debug("jsonDataOfIp ---> "+jsonDataOfIp);
Logger.debug("++++++++++++++++++++++++++++++++  ");
			referral_Source.setLongitude(jsonDataOfIp.get("lon").toString());
			referral_Source.setLatitude(jsonDataOfIp.get("lat").toString());
			referral_Source.setIp(jsonDataOfIp.get("query").toString());

		} catch (NullPointerException  e) {
			Logger.error("Error in parsing and seting referral pojo ", e);
		}catch (Exception e) {
			Logger.error("Error in parsing and seting referral pojo ", e);
		}

		String addressArray[] = arrayOfaddress;
		String addressFromArray = "";
		if (addressArray != null) {
			for (int i = 0; i < addressArray.length; i++) {
				addressFromArray = addressArray[i];
			}

		}
		referral_Source.setFullAddress(addressFromArray);
		SplitAddress ad = new SplitAddress();
		Map<String, String> addressMap = ad.getProperAddress(addressFromArray);
		if (!addressMap.isEmpty()) {
			referral_Source.setCity(addressMap.get("city"));
			referral_Source.setStreet2(addressMap.get("address1"));
			referral_Source.setPostal_code(addressMap.get("postalcode"));
			int statId = 0;
			try {
				statId = new PostgresDAO().getStateID(addressMap
						.get("Province"));
			} catch (PostGressDaoServiceException e) {
				Logger.error("Error in gettind state ID for given Province ="
						+ addressMap.get("Province"));
			}
			referral_Source.setProvince(statId);

			if (statId == 0)
				referral_Source.setProvince(null);
		}
		return referral_Source;
	}

	/**
	 * check Referral exist with contact id if exist update referral else create
	 * contact and Referral source ,if contact not Exist create contact and
	 * referral
	 * 
	 * @param contact
	 * @param referral_Source
	 * @return return referralsource pojo
	 * @throws PostGressDaoServiceException
	 */
	public Referral_Source createReferralSource(Contact contact,
			Referral_Source referral_Source)
			throws PostGressDaoServiceException {

		IPostGresDaoService iPostGresDaoService = new PostgresDAO();
		referral_Source.setActive(true);

		if (contact != null && contact.getId() == 0) {

			Logger.debug("if contact(res.partner) and referrel(hr.applicant) not exsist");
			iPostGresDaoService.insertContact(contact);
			referral_Source.setPartner_id(contact.getId());

			Logger.info("Contact is created with this Id " + contact.getId());
			// creating new referrer and returning the referrelId
			iPostGresDaoService.insertReferral(referral_Source);
			Logger.info("Referrer is created with this Id "
					+ referral_Source.getId());
		} else {

			Logger.debug("if referral Source  is Exsist");

			ArrayList<Referral_Source> referralList = iPostGresDaoService
					.getReferral_SourceByPartnerID(contact.getId());
			if (referralList != null && referralList.size() >= 1) {
				referral_Source.setReferralExist(1);
				referral_Source.setPartner_id(contact.getId());
				referral_Source.setId(referralList.get(0).getId());
				// Updating referral source
				referral_Source = iPostGresDaoService
						.updateReferral(referral_Source);
				Logger.info("updated referral Source for referral Id "
						+ referral_Source.getId());

			} else {
				iPostGresDaoService.insertContact(contact);
				referral_Source.setPartner_id(contact.getId());

				Logger.info("Contact is created with this Id "
						+ contact.getId());
				// creating new referrer and returning the referrelId
				referral_Source = iPostGresDaoService
						.insertReferral(referral_Source);
				Logger.info("referral source  is created with this Id "
						+ referral_Source.getId());

			}

		}

		return referral_Source;
	}

	/**
	 * mapping contacts based on Entered inputs from web page and check contact
	 * exist with enter details
	 * 
	 * @param dynamicForm
	 * @return conatct
	 * @throws PostGressDaoServiceException
	 */
	public Contact checkContactExist(DynamicForm dynamicForm)
			throws PostGressDaoServiceException {
		Contact contact = new Contact();
		contact.setName(dynamicForm.get("fname"));
		contact.setLast_name(dynamicForm.get("lname"));
		contact.setEmail(dynamicForm.get("email"));
		IPostGresDaoService postGresDaoService = new PostgresDAO();
		// to get contact by first name , lastname,and emailiD
		List<Contact> list = postGresDaoService.getContact(contact);
		if (list.isEmpty())
			return contact;

		return list.get(0);

	}

	/**
	 * to set Roles to pojo which are matching drop down list of OPenERP based
	 * on the given input (roles)
	 * 
	 * @param referral_Source
	 * @return referral source
	 */
	public Referral_Source getRoles(Referral_Source referral_Source) {
		Logger.debug("Inside (.) getRoles method  ");
		try {
			if (referral_Source.getRoleName().equalsIgnoreCase(
					ReferralConstants.REALTOR)) {
				referral_Source.setCompensation("Company");
				referral_Source.setRole("realtor");

			} else if (referral_Source.getRoleName().equalsIgnoreCase(
					ReferralConstants.PROFESSIONAL)) {
				referral_Source.setRole("other");

				referral_Source.setCompensation("Direct to Myself");
			} else if (referral_Source.getRoleName().equalsIgnoreCase(
					ReferralConstants.FINANCIAL_PLANNER)) {
				referral_Source.setRole("financail_planner");
			} else if (referral_Source.getRoleName().equalsIgnoreCase(
					ReferralConstants.CLIENT)) {
				referral_Source.setRole("client");

			} else {
				referral_Source.setRole("other");
			}
		} catch (NullPointerException e) {
			Logger.error("null pointer exception when getting role data " + e);
		}
		return referral_Source;
	}

	/**
	 * delete generated pdf files based given path
	 * 
	 * @param path
	 */
	public void deleteFile(String path) {
		Logger.debug("inside (.) deleteFile method ");

		try {
			File file = new File(path);

			if (file.exists()) {
				file.delete();
			}
		} catch (Exception e) {
			Logger.error("error in deleting file ");
		}
	}

	/**
	 * genertae Referral agreement pdf for wfg referrals
	 * 
	 * @param referralName
	 * @param areusingTouchScreenDevice
	 * @param myTypedName
	 * @param role
	 * @param image
	 * @return pdf file path
	 * @throws ReferralSourcePageServiceException
	 */

	public String VisdomReferralPdfGenerationMethodWfg(String referralName,
			String areusingTouchScreenDevice, String myTypedName, String role,
			BufferedImage image) throws ReferralSourcePageServiceException {

		Logger.debug("Inside (.)  VisdomReferralPdfGenerationMethodWfg method ");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		GregorianCalendar calendar = (GregorianCalendar) GregorianCalendar
				.getInstance();

		String outputFileName = ReadConfigFile.readConfigfile().getProperty(
				Constants.PDF_FILE_PATH)
				+ ReferralConstants.REFERRAL_AGREEMENT_NAME
				+ referralName
				+ "_"
				+ format.format(calendar.getTime())
				+ ReferralConstants.REFERRAL_AGREMENT_FILE_EXTENTION;

		Properties prop = new Properties();
		try {

			prop.load(VisdomReferral.class
					.getClassLoader()
					.getResourceAsStream(
							ReferralConstants.REFERRAL_PDF_DATA_CONFIG_PROPERTIES_FILE));
			Logger.debug("referralName " + referralName
					+ "areusingTouchScreenDevice " + areusingTouchScreenDevice
					+ " myTypedName" + myTypedName + "role " + role + " image"
					+ image);
			Logger.debug("prop " + prop.size());

			PDDocument document = new PDDocument();
			PDPage page1 = new PDPage(PDPage.PAGE_SIZE_A4);

			PDRectangle rect = page1.getMediaBox();
			document.addPage(page1);

			PDFont fontplain = PDType1Font.TIMES_ROMAN;
			PDFont fontbold = PDType1Font.HELVETICA_BOLD;
			// PDFont fontItalic = PDType1Font.HELVETICA_OBLIQUE;
			// PDFont fontMono = PDType1Font.COURIER;

			PDPageContentStream cos = new PDPageContentStream(document, page1);

			int line = 0;

			cos.beginText();
			cos.setFont(fontbold, 12);
			cos.moveTextPositionByAmount(240, rect.getHeight() - 50 * (++line));
			cos.drawString("Mortgage Referral Agreement");
			cos.endText();
			int line2 = 0;
			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100);
			cos.drawString(prop.getProperty("referral1"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral2"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral3"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral4"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral5"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral6"));
			cos.endText();
			line2 = line2 + 20;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(30, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral7"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral8"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral8"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral9"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral10"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral11"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral12"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral13"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral14"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral15"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral16"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral17"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral18"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral19"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral20"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral21"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral22"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral23"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral24"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral25"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral26"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral27"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral28"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral29"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral30"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral31"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral32"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral33"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral34"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral35"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral36"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral37"));
			cos.endText();
			line2 = line2 + 20;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(30, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral38"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral39"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral40"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral41"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral42"));
			cos.endText();
			line2 = line2 + 20;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(30, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral43"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral44"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral45"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral46"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral47"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral48"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral49"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral50"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral51"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral52"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral53"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral54"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral55"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral56"));
			cos.endText();
			line2 = line2 + 15;
			cos.close();

			PDPage page2 = new PDPage(PDPage.PAGE_SIZE_A4);
			document.addPage(page2);
			cos = new PDPageContentStream(document, page2);
			line2 = 0;
			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral57"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral58"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral59"));
			cos.endText();
			line2 = line2 + 10;

			/*
			 * cos.beginText(); cos.setFont(fontplain, 10);
			 * cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
			 * cos.drawString(prop.getProperty("referral60")); cos.endText();
			 * line2=line2+10;
			 */

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral61"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral62"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral63"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral64"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral65"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral66"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral67"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(30, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral68"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral69"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral70"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral71"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral72"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral73"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral74"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral75"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral76"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral77"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral78"));
			cos.endText();
			line2 = line2 + 20;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(30, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral79"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral80"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral81"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral82"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral83"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(30, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral84"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral85"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral86"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral87"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral88"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral89"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral90"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral91"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral92"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral93"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral94"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral95"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral96"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral97"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral98"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral99"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral70"));
			cos.endText();
			line2 = line2 + 20;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral101"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral102"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral103"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral104"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral105"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral106"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral107"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral108"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral109"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral110"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral111"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral112"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral113"));
			cos.endText();
			line2 = line2 + 20;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(30, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral114"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral115"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral116"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral117"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral118"));
			cos.endText();
			line2 = line2 + 15;

			cos.close();

			PDPage page3 = new PDPage(PDPage.PAGE_SIZE_A4);
			document.addPage(page3);
			cos = new PDPageContentStream(document, page3);

			line2 = 0;
			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral119"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral120"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral121"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral122"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral123"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral124"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral125"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral126"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral127"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral128"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral129"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral130"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral131"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral132"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral133"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral134"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral135"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral136"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral137"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral138"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral139"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral140"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral142"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral143"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral144"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral145"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral146"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral147"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral148"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral149"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral150"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral151"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral152"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral153"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral154"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral155"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral156"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral157"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral158"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral159"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral160"));
			cos.endText();
			line2 = line2 + 20;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral161"));
			cos.endText();
			line2 = line2 + 20;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral162"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(30, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral163"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral164"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral165"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral166"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(30, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral167"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral168"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral169"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(30, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral170"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral171"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral172"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral173"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral174"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral175"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral176"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral177"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral178"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral179"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral180"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral181"));
			cos.endText();
			line2 = line2 + 15;
			cos.close();

			PDPage page4 = new PDPage(PDPage.PAGE_SIZE_A4);
			document.addPage(page4);
			cos = new PDPageContentStream(document, page4);
			line2 = 0;
			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral182"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(30, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral183"));
			cos.endText();
			line2 = line2 + 20;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral184"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral185"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral186"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral187"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(30, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral188"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral189"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral190"));
			cos.endText();
			line2 = line2 + 25;
			line2 = line2 + 20;
			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString("Schedule B");
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(200, rect.getHeight() - 70 - line2);
			cos.drawString("REFERRAL FEE SCHEDULE");
			cos.endText();
			line2 = line2 + 20;

			String[][] content1 = {
					{ " Min Mortgage Value", " Max Mortgage Value",
							"    Referral Fee", "  Renewal Fee" },
					{ "$50,000.00", "$200,000.00", "$42.00", "$21.00" },
					{ "$200,001.00", "$350,000.00", "$105.00", "$42.00" },
					{ "$350,001.00", "$600,000.00", "$210.00", "$95.00" },
					{ "$600,001.00", "$900,000.00", "$315.00", "$147.00" },
					{ "$900,001.00", "$3,000,000.00", "$420.00", "$210.00" } };

			PdfTableCreationMethod(page4, cos, rect.getHeight() - 70 - line2,
					20, content1);

			line2 = line2 + 170;
			cos.beginText();
			cos.setFont(fontbold, 10);
			cos.moveTextPositionByAmount(60, rect.getHeight() - 70 - line2);
			cos.drawString("Referrer Signature :");
			cos.endText();
			line2 = line2 + 110;

			if (areusingTouchScreenDevice.equalsIgnoreCase("yes")) {
				try {

					PDXObjectImage ximage = new PDPixelMap(document, image);
					float scale = 0.5f; // alter this value to set the image
										// size
					cos.drawXObject(ximage, 50, rect.getHeight() - 70 - line2,
							ximage.getWidth() * scale, ximage.getHeight()
									* scale);
				} catch (Exception fnfex) {
					Logger.debug("No image for you");
				}
				cos.close();
			} else {

				cos.beginText();
				cos.setFont(fontbold, 10);
				cos.moveTextPositionByAmount(90, rect.getHeight() - 70 - line2
						+ 80);
				cos.drawString(myTypedName);
				cos.endText();
				line2 = line2 + 20;

				cos.beginText();
				cos.setFont(fontplain, 10);
				cos.moveTextPositionByAmount(60, rect.getHeight() - 70 - line2
						+ 70);
				cos.drawString("My typed name above serves as my electronic signature for the above agreement.");
				cos.endText();
				cos.close();
			}

			Logger.debug("Before saving pdf document file path is *"
					+ outputFileName);
			document.save(outputFileName);
			Logger.debug("After  saving pdf document file path is "
					+ outputFileName);

			document.close();

		} catch (Exception e) {
			throw new ReferralSourcePageServiceException(
					"Error in generatin referral agreement  pdf ", e);

		}

		return outputFileName;

	}

	public String sendMailofReferralAgrement(Referral_Source referral_Source)
			throws IOException, ReferralSourcePageServiceException {
		BufferedImage image = null;
		String pdffilepath = "";

		if (referral_Source.getIsTouchScreeDevice().equals("yes")) {
			String newString = referral_Source.getElectronicSing()
					.substring(22);
			byte[] decodedBytes = Base64.decode(newString);
			image = ImageIO.read(new ByteArrayInputStream(decodedBytes));
			pdffilepath = generateReferralAgreementPdf(
					referral_Source.getFirstName(),
					referral_Source.getIsTouchScreeDevice(), "",
					referral_Source.getRoleName(), image);
			new SendWithUsExample().sendTOReferralAttachment(
					referral_Source.getFirstName(),
					VisdomReferral.SUBMIT_REFERRAL_LINK
							+ referral_Source.getId(),
					referral_Source.getEmail_from(), pdffilepath);

		} else {

			pdffilepath = generateReferralAgreementPdf(
					referral_Source.getFirstName(),
					referral_Source.getIsTouchScreeDevice(),
					referral_Source.getSignature(),
					referral_Source.getRoleName(), image);
			new SendWithUsExample().sendTOReferralAttachment(
					referral_Source.getFirstName(),
					VisdomReferral.SUBMIT_REFERRAL_LINK
							+ referral_Source.getId(),
					referral_Source.getEmail_from(), pdffilepath);
		}

		return pdffilepath;
	}

	public String sendMailofReferralAgrementForWfg(
			Referral_Source referral_Source)
			throws ReferralSourcePageServiceException {
		BufferedImage image = null;
		String pdffilepath = "";
		try {
			if (referral_Source.getIsTouchScreeDevice().equals("yes")) {
				String newString = referral_Source.getElectronicSing()
						.substring(22);
				byte[] decodedBytes = Base64.decode(newString);
				image = ImageIO.read(new ByteArrayInputStream(decodedBytes));
				pdffilepath = generateReferralAgreementPdf(
						referral_Source.getFirstName(),
						referral_Source.getIsTouchScreeDevice(), "",
						referral_Source.getRoleName(), image);
				new SendWithUsExample().sendTOReferralAttachment(
						referral_Source.getFirstName(),
						VisdomReferral.SUBMIT_REFERRAL_LINK
								+ referral_Source.getId(),
						referral_Source.getEmail_from(), pdffilepath);

			} else {

				pdffilepath = generateReferralAgreementPdf(
						referral_Source.getName(),
						referral_Source.getIsTouchScreeDevice(),
						referral_Source.getSignature(),
						referral_Source.getRoleName(), image);
				new SendWithUsExample().sendTOReferralAttachment(
						referral_Source.getFirstName(),
						VisdomReferral.SUBMIT_REFERRAL_LINK
								+ referral_Source.getId(),
						referral_Source.getEmail_from(), pdffilepath);

			}
		} catch (Exception e) {
			throw new ReferralSourcePageServiceException(
					"error in generting pdf and sending mail to referral="
							+ referral_Source.getFirstName(), e);

		}
		return pdffilepath;
	}

	public Referral_Source storeReferralAgrementToCouchbase(String filePth,
			Referral_Source referral_Source)
			throws ReferralSourcePageServiceException {

		try {
			Path path = Paths.get(filePth);
			byte[] data1 = Files.readAllBytes(path);

			HashMap<String, String> hashdata = new HashMap<String, String>();
			String encodeData = net.iharder.Base64.encodeBytes(data1);
			hashdata.put("attachement", encodeData);
			JsonObject jsonObject = JsonObject.from(hashdata);
			jsonObject.put("FormType", ReferralConstants.REFERRAL_SOURCE_FORM);
			new CouchBaseService().storeDataToCouchbase(
					ReferralConstants.REFERRAL_AGREEMENT_COUCHBASE_KEY
							+ referral_Source.getId(), jsonObject);

			referral_Source
					.setReferralAgreemetfile_id(ReferralConstants.REFERRAL_AGREEMENT_COUCHBASE_KEY
							+ referral_Source.getId());
		} catch (Exception e) {

			throw new ReferralSourcePageServiceException(
					"error in storing referral agreement datat to couhbase", e);

		}
		return referral_Source;
	}

	/**
	 * to generate Referral Agreement PDf
	 * 
	 * @param referralName
	 * @param areusingTouchScreenDevice
	 * @param myTypedName
	 * @param role
	 * @param image
	 * @return Pdf file path
	 * @throws ReferralSourcePageServiceException
	 */

	public String generateReferralAgreementPdf(String referralName,
			String areusingTouchScreenDevice, String myTypedName, String role,
			BufferedImage image) throws ReferralSourcePageServiceException {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		GregorianCalendar calendar = (GregorianCalendar) GregorianCalendar
				.getInstance();

		String outputFileName = ReadConfigFile.readConfigfile().getProperty(
				Constants.PDF_FILE_PATH)
				+ ReferralConstants.REFERRAL_AGREEMENT_NAME
				+ referralName
				+ "_"
				+ format.format(calendar.getTime())
				+ ReferralConstants.REFERRAL_AGREMENT_FILE_EXTENTION;

		Properties prop = new Properties();
		try {

			prop.load(ReferralSourcePageService.class
					.getClassLoader()
					.getResourceAsStream(
							ReferralConstants.REFERRAL_PDF_DATA_CONFIG_PROPERTIES_FILE));
			Logger.debug("referralName " + referralName
					+ "areusingTouchScreenDevice " + areusingTouchScreenDevice
					+ " myTypedName" + myTypedName + "role " + role + " image"
					+ image);
			Logger.debug("prop " + prop.size());

			PDDocument document = new PDDocument();
			PDPage page1 = new PDPage(PDPage.PAGE_SIZE_A4);

			PDRectangle rect = page1.getMediaBox();
			document.addPage(page1);

			PDFont fontplain = PDType1Font.TIMES_ROMAN;
			PDFont fontbold = PDType1Font.HELVETICA_BOLD;
			// PDFont fontItalic = PDType1Font.HELVETICA_OBLIQUE;
			// PDFont fontMono = PDType1Font.COURIER;

			PDPageContentStream cos = new PDPageContentStream(document, page1);

			int line = 0;

			cos.beginText();
			cos.setFont(fontbold, 12);
			cos.moveTextPositionByAmount(240, rect.getHeight() - 50 * (++line));
			cos.drawString("Mortgage Referral Agreement");
			cos.endText();
			int line2 = 0;
			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100);
			cos.drawString(prop.getProperty("referral1"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral2"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral3"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral4"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral5"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral6"));
			cos.endText();
			line2 = line2 + 20;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(30, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral7"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral8"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral8"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral9"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral10"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral11"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral12"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral13"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral14"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral15"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral16"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral17"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral18"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral19"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral20"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral21"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral22"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral23"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral24"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral25"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral26"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral27"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral28"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral29"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral30"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral31"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral32"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral33"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral34"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral35"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral36"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral37"));
			cos.endText();
			line2 = line2 + 20;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(30, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral38"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral39"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral40"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral41"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral42"));
			cos.endText();
			line2 = line2 + 20;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(30, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral43"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral44"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral45"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral46"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral47"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral48"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral49"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral50"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral51"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral52"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral53"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral54"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral55"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral56"));
			cos.endText();
			line2 = line2 + 15;
			cos.close();

			PDPage page2 = new PDPage(PDPage.PAGE_SIZE_A4);
			document.addPage(page2);
			cos = new PDPageContentStream(document, page2);
			line2 = 0;
			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral57"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral58"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral59"));
			cos.endText();
			line2 = line2 + 10;

			/*
			 * cos.beginText(); cos.setFont(fontplain, 10);
			 * cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
			 * cos.drawString(prop.getProperty("referral60")); cos.endText();
			 * line2=line2+10;
			 */

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral61"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral62"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral63"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral64"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral65"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral66"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral67"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(30, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral68"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral69"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral70"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral71"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral72"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral73"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral74"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral75"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral76"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral77"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral78"));
			cos.endText();
			line2 = line2 + 20;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(30, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral79"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral80"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral81"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral82"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral83"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(30, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral84"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral85"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral86"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral87"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral88"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral89"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral90"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral91"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral92"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral93"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral94"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral95"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral96"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral97"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral98"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral99"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral70"));
			cos.endText();
			line2 = line2 + 20;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral101"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral102"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral103"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral104"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral105"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral106"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral107"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral108"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral109"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral110"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral111"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral112"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral113"));
			cos.endText();
			line2 = line2 + 20;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(30, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral114"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral115"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral116"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral117"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral118"));
			cos.endText();
			line2 = line2 + 15;

			cos.close();

			PDPage page3 = new PDPage(PDPage.PAGE_SIZE_A4);
			document.addPage(page3);
			cos = new PDPageContentStream(document, page3);

			line2 = 0;
			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral119"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral120"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral121"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral122"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral123"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral124"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral125"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral126"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral127"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral128"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral129"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral130"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral131"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral132"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral133"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral134"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral135"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral136"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral137"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral138"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral139"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral140"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral142"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral143"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral144"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral145"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral146"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral147"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral148"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral149"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral150"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral151"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral152"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral153"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral154"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral155"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral156"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral157"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral158"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral159"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral160"));
			cos.endText();
			line2 = line2 + 20;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral161"));
			cos.endText();
			line2 = line2 + 20;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral162"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(30, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral163"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral164"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral165"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral166"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(30, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral167"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral168"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral169"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(30, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral170"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral171"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral172"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral173"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral174"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral175"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral176"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral177"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral178"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral179"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral180"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral181"));
			cos.endText();
			line2 = line2 + 15;
			cos.close();

			PDPage page4 = new PDPage(PDPage.PAGE_SIZE_A4);
			document.addPage(page4);
			cos = new PDPageContentStream(document, page4);
			line2 = 0;
			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral182"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(30, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral183"));
			cos.endText();
			line2 = line2 + 20;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral184"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral185"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral186"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral187"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(30, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral188"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral189"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString(prop.getProperty("referral190"));
			cos.endText();
			line2 = line2 + 25;
			line2 = line2 + 20;
			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 70 - line2);
			cos.drawString("Schedule B");
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(200, rect.getHeight() - 70 - line2);
			cos.drawString("REFERRAL FEE SCHEDULE");
			cos.endText();
			line2 = line2 + 20;

			if (role != null && role.equalsIgnoreCase(ReferralConstants.CLIENT)) {

				String[][] content = {
						{ " Min Mortgage Value", " Max Mortgage Value",
								"    Referral Fee", "  Renewal Fee" },
						{ "$50,000.00", "$200,000.00", "$50.00", "$25.00" },
						{ "$200,001.00", "$350,000.00", "$125.00", "$50.00" },
						{ "$350,001.00", "$600,000.00", "$250.00", "$125.00" },
						{ "$600,001.00", "$900,000.00", "$350.00", "$175.00" },
						{ "$900,001.00", "$3,000,000.00", "$500.00", "$250.00" } };

				PdfTableCreationMethod(page4, cos, rect.getHeight() - 70
						- line2, 20, content);

			} else if (role != null
					&& role.equalsIgnoreCase(ReferralConstants.REALTOR)
					|| role.equals(ReferralConstants.FINANCIAL_PLANNER)
					|| role.equals(ReferralConstants.PROFESSIONAL)) {

				String[][] content1 = {
						{ " Min Mortgage Value", " Max Mortgage Value",
								"    Referral Fee", "  Renewal Fee" },
						{ "$50,000.00", "$200,000.00", "$100.00", "$50.00" },
						{ "$200,001.00", "$350,000.00", "$250.00", "$100.00" },
						{ "$350,001.00", "$600,000.00", "$500.00", "$225.00" },
						{ "$600,001.00", "$900,000.00", "$750.00", "$350.00" },
						{ "$900,001.00", "$3,000,000.00", "$1,000.00",
								"$500.00" } };

				PdfTableCreationMethod(page4, cos, rect.getHeight() - 70
						- line2, 20, content1);
			}

			line2 = line2 + 170;
			cos.beginText();
			cos.setFont(fontbold, 10);
			cos.moveTextPositionByAmount(60, rect.getHeight() - 70 - line2);
			cos.drawString("Referrer Signature :");
			cos.endText();
			line2 = line2 + 110;

			if (areusingTouchScreenDevice.equalsIgnoreCase("yes")) {
				try {
					// BufferedImage awtImage = ImageIO.read(new
					// File("simple.jpg"));
					PDXObjectImage ximage = new PDPixelMap(document, image);
					float scale = 0.5f; // alter this value to set the image
										// size
					cos.drawXObject(ximage, 50, rect.getHeight() - 70 - line2,
							ximage.getWidth() * scale, ximage.getHeight()
									* scale);
				} catch (Exception fnfex) {
					Logger.debug("No image for you");
				}
				cos.close();
			} else {

				cos.beginText();
				cos.setFont(fontbold, 10);
				cos.moveTextPositionByAmount(90, rect.getHeight() - 70 - line2
						+ 80);
				cos.drawString(myTypedName);
				cos.endText();
				line2 = line2 + 20;

				cos.beginText();
				cos.setFont(fontplain, 10);
				cos.moveTextPositionByAmount(60, rect.getHeight() - 70 - line2
						+ 70);
				cos.drawString("My typed name above serves as my electronic signature for the above agreement.");
				cos.endText();
				cos.close();
			}

			Logger.debug("Before saving to pdf document  Genereted Pdf FileName"
					+ outputFileName);
			document.save(outputFileName);
			Logger.debug("After saving to pdf document  Genereted Pdf FileName"
					+ outputFileName);

			document.close();

		} catch (Exception e) {
			throw new ReferralSourcePageServiceException(
					"Error in generating referral agreement  pdf ", e);

		}

		return outputFileName;

	}

	private void PdfTableCreationMethod(PDPage page,
			PDPageContentStream contentStream, float y, float margin,
			String[][] content) throws IOException {
		final int rows = content.length;
		final int cols = content[0].length;
		final float rowHeight = 20f;
		final float tableWidth = page.findMediaBox().getWidth() - (2 * margin);
		final float tableHeight = rowHeight * rows;
		final float colWidth = tableWidth / (float) cols;
		final float cellMargin = 5f;

		/***
		 * draw the rows
		 */
		float nexty = y;
		for (int i = 0; i <= rows; i++) {
			contentStream.drawLine(margin, nexty, margin + tableWidth, nexty);
			nexty -= rowHeight;
		}
/***
 * draw the rows
 */
		
		float nextx = margin;
		for (int i = 0; i <= cols; i++) {
			contentStream.drawLine(nextx, y, nextx, y - tableHeight);
			nextx += colWidth;
		}
		/***
		 * now add the text
		 */
		 
		contentStream.setFont(PDType1Font.TIMES_ROMAN, 10);

		float textx = margin + cellMargin;
		float texty = y - 15;
		for (int i = 0; i < content.length; i++) {
			for (int j = 0; j < content[i].length; j++) {
				String text = content[i][j];
				contentStream.beginText();
				contentStream.moveTextPositionByAmount(textx, texty);
				contentStream.drawString(text);
				contentStream.endText();
				textx += colWidth;
			}
			texty -= rowHeight;
			textx = margin + cellMargin;
		}
	}

	public void createContactInOntharaPort(
			Referral_Source refferReferral_Source, int role, String agreementURL) {
		JsonObject jsonOutside = JsonObject.create();
		JsonArray jsonArrayOutSide = JsonArray.create();
	
		try {
			jsonOutside.put("objectID", 0);

			jsonOutside.put("firstname", refferReferral_Source.getFirstName());

			jsonOutside.put("lastname", refferReferral_Source.getLastName());

			jsonOutside.put("email", refferReferral_Source.getEmail_from());
			jsonOutside.put("f1446", refferReferral_Source.getPartner_mobile());
			jsonOutside.put("sms_number",
					refferReferral_Source.getPartner_mobile());
			/** occupation fealid or role*/

			jsonOutside.put("f1419", role);
			jsonOutside.put("company", refferReferral_Source.getCompany());

			jsonOutside.put("f1422", true);
			/** referredby */

			jsonOutside.put("f1443", refferReferral_Source.getReferrer());
			/** refrrall */
			
			jsonOutside.put("f1444", refferReferral_Source.getId());
			jsonOutside.put("office_phone",
					refferReferral_Source.getPartner_phone());
			/** adding tag (agreement) */

			jsonOutside.put("contact_cat", 17);
			/** referral agremment */

			jsonOutside.put("f1445", agreementURL);

			jsonOutside.put("address", refferReferral_Source.getStreet());
			jsonOutside.put("city", refferReferral_Source.getCity());
			jsonOutside.put("zip", refferReferral_Source.getPostal_code());

			jsonArrayOutSide.add(jsonOutside);

			URL url = new URL(
					"https://api.ontraport.com/1/objects/saveorupdate");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Api-Appid", "2_80168_eQ7KwDUNN");
			conn.setRequestProperty("Api-Key", "GneeytyRKVlMBTN");

			String input = jsonOutside.toString();
			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			} else {

				

				Logger.info("Output from Server ....onthara port server  with response code  = "
						+ conn.getResponseCode());

			}
			conn.disconnect();
		} catch (Exception e) {
			Logger.error("error when sending request to onthraport ", e);
		}

	}

}
