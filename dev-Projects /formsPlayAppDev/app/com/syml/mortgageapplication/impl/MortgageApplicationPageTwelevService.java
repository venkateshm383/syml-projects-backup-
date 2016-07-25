package com.syml.mortgageapplication.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
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

import com.couchbase.client.java.document.json.JsonObject;
import com.sendwithus.SendWithUsExample;
import com.syml.Constants;
import com.syml.ReadConfigFile;
import com.syml.couchbase.dao.CouchbaseServiceException;
import com.syml.couchbase.dao.service.CouchBaseService;
import com.syml.hibernate.dao.PostGressDaoServiceException;
import com.syml.hibernate.service.PostGresDaoService;
import com.syml.mortgageapplication.MortgageApplicationConstants;

import controllers.Opportunity;

public class MortgageApplicationPageTwelevService {

	PostGresDaoService posService = new PostGresDaoService();
	CouchBaseService couchBaseService = new CouchBaseService();

	/**
	 * To map 12th form values to Opportunity pojo
	 * 
	 * @param opportunity
	 * @param dynamicForm
	 * @return opportunity
	 */
	public Opportunity loadFormData(Opportunity opportunity,
			DynamicForm dynamicForm) {
		String isAdditionalApplicantExit = opportunity
				.getIsAdditionalApplicantExist();
		String areUsingTouchScreenDevice = dynamicForm.get("touchScreen");
		Logger.info("Devictype Id touchScreen =" + areUsingTouchScreenDevice);
		opportunity.setAreUsingTouchScreenDevice(areUsingTouchScreenDevice);
		if (areUsingTouchScreenDevice.equalsIgnoreCase("Yes")) {
			opportunity.getApplicants().get(0)
					.setElectronicSign(dynamicForm.get("sign1"));
			if (isAdditionalApplicantExit.equalsIgnoreCase("Yes")) {
				opportunity.getApplicants().get(1)
						.setElectronicSign(dynamicForm.get("sign2"));
			}
		} else {
			opportunity.getApplicants().get(0)
					.setNormalSign(dynamicForm.get("typedName1"));
			if (isAdditionalApplicantExit.equalsIgnoreCase("Yes")) {
				opportunity.getApplicants().get(1)
						.setNormalSign(dynamicForm.get("typedName2"));
			}
		}
		opportunity.setPogressStatus(99);
		opportunity
				.setStage_id(MortgageApplicationConstants.OPPORTUNITY_COMPLETED_STAGE_ID);
		opportunity.setApplication_date(new Date());
		opportunity.setApplication_no(opportunity.getId() + "");
		return opportunity;

	}

	/**
	 * To update opportunity in Db , update Opportunity details couchbase and
	 * send mail of discloserspdf to client
	 * 
	 * @param opportunity
	 * @throws MortgageApplicationPageServiceException
	 */
	public void updateOpportunity(Opportunity opportunity)
			throws MortgageApplicationPageServiceException {
		try {
			String pdfFilePath = sendMailOfDisclosers(opportunity);
			storeDiscloserPdfToCouchbase(pdfFilePath, opportunity.getId());
			opportunity
					.setMortgage_Brokerage_Disclosures_Id(MortgageApplicationConstants.MORTGAGE_APPLICATION_DISCLOSURE_KEY
							+ opportunity.getId());
			posService.updateOpportunityPage12(opportunity);
			couchBaseService
					.storeDataToCouchbase(
							MortgageApplicationConstants.MORTGAGE_APPLICATION_COUCHBASE_KEY
									+ opportunity.getId(), opportunity);

		} catch (PostGressDaoServiceException e) {
			throw new MortgageApplicationPageServiceException(
					"Error In updating opporunity Details Into Db ", e);
		} catch (CouchbaseServiceException e) {
			throw new MortgageApplicationPageServiceException(
					"Error In updating Opportunity Details Into Couchbase ", e);

		} catch (IOException e) {
			throw new MortgageApplicationPageServiceException(
					"Error in sending disclosers Mail  for Opporunity = "
							+ opportunity, e);
		}

	}

	/**
	 * to store pdf Disclosers to couchbase
	 * 
	 * @param filePath
	 * @param id
	 * @throws MortgageApplicationPageServiceException
	 */
	private void storeDiscloserPdfToCouchbase(String filePath, int id)
			throws MortgageApplicationPageServiceException {
		Logger.debug("Inside (.) storeDiscloserPdfToCouchbase  method  ");
		CouchBaseService storedata = new CouchBaseService();
		try {
			Path path = Paths.get(filePath);
			byte[] data1 = Files.readAllBytes(path);
			HashMap<String, Object> data11 = new HashMap<String, Object>();
			String encodeData = net.iharder.Base64.encodeBytes(data1);
			data11.put("attachement", encodeData);
			JsonObject mortDisclosureJson = JsonObject.from(data11);
			storedata
					.storeDataToCouchbase(
							MortgageApplicationConstants.MORTGAGE_APPLICATION_DISCLOSURE_KEY
									+ id, mortDisclosureJson);
			deleteFile(filePath);
		} catch (IOException e) {
			throw new MortgageApplicationPageServiceException(
					"Error in process given pdf file path =" + filePath
							+ " for OpporunityID=" + id, e);
		} catch (CouchbaseServiceException e) {
			throw new MortgageApplicationPageServiceException(
					"Error in Storing disclosersPdf to couchbase for given OpporunityID="
							+ id, e);
		}

	}

	/**
	 * To send mail of Disclosers to client and send mail to referral that
	 * client has completed mortgageApplication
	 * 
	 * @param opportunity
	 * @throws IOException
	 * @throws MortgageApplicationPageServiceException
	 */
	private String sendMailOfDisclosers(Opportunity opportunity)
			throws IOException, MortgageApplicationPageServiceException {
		SendWithUsExample sendWithUsExample = new SendWithUsExample();
		String coApplicantName = "";
		String coApplicantEmail = "";
		String pdfFilePath = getDisclosersPDF(opportunity);
		String applicantName = opportunity.getApplicants().get(0)
				.getApplicant_name();

		if (opportunity.getIsAdditionalApplicantExist().equalsIgnoreCase("Yes")) {
			coApplicantName = opportunity.getApplicants().get(1)
					.getApplicant_name();
			coApplicantEmail = opportunity.getApplicants().get(1)
					.getEmail_personal();
		}
		sendWithUsExample.sendTOreferralCompletedApp(
				opportunity.getReferralSourceName(), applicantName,
				opportunity.getReferralSourceEmail(), coApplicantName);
		sendWithUsExample.sendDisclosuresToclientCompletedApp(applicantName,
				opportunity.getApplicants().get(0).getEmail_personal(),
				coApplicantName, coApplicantEmail, null, pdfFilePath);
		return pdfFilePath;

	}

	/**
	 * To generate Disclosers PDF for client and return file path of pdf
	 * 
	 * @param opportunity
	 * @return string (pdf file path)
	 * @throws IOException
	 * @throws MortgageApplicationPageServiceException
	 */
	private String getDisclosersPDF(Opportunity opportunity)
			throws IOException, MortgageApplicationPageServiceException {
		BufferedImage imageApplicant = null;
		BufferedImage imageCo_applicant = null;
		String pdfGeneratedFilePath = null;
		String coapplicantSign = "";
		// client using Touch Screen Device
		if (opportunity.getAreUsingTouchScreenDevice().equalsIgnoreCase("Yes")) {
			String applicantElectronicSign = opportunity.getApplicants().get(0)
					.getElectronicSign();
			if (opportunity.getIsAdditionalApplicantExist().equalsIgnoreCase(
					"Yes")) {
				String co_applicantElectronicSign = opportunity.getApplicants()
						.get(1).getElectronicSign();
				byte[] decodedBytes = Base64.decode(co_applicantElectronicSign
						.substring(22));
				imageCo_applicant = ImageIO.read(new ByteArrayInputStream(
						decodedBytes));
			}
			byte[] decodedBytes = Base64.decode(applicantElectronicSign
					.substring(22));
			imageApplicant = ImageIO
					.read(new ByteArrayInputStream(decodedBytes));
			pdfGeneratedFilePath = generateDiscloserPdf(opportunity
					.getApplicants().get(0).getApplicant_name(), "Yes",
					opportunity.getIsAdditionalApplicantExist(),
					imageApplicant, imageCo_applicant, "", "");
		} else {
			// client not using Touch Screen Device
			if (opportunity.getIsAdditionalApplicantExist().equalsIgnoreCase(
					"Yes")) {
				coapplicantSign = opportunity.getApplicants().get(1)
						.getNormalSign();
			}
			pdfGeneratedFilePath = generateDiscloserPdf(opportunity
					.getApplicants().get(0).getApplicant_name(),
					opportunity.getAreUsingTouchScreenDevice(),
					opportunity.getIsAdditionalApplicantExist(),
					imageApplicant, imageCo_applicant, opportunity
							.getApplicants().get(0).getNormalSign(),
					coapplicantSign);
		}
		return pdfGeneratedFilePath;
	}

	/**
	 * To create pdf , write applicant agreement details to pdf along with
	 * normal sign(name) or elctronic sign
	 * 
	 * @param applicantName
	 * @param areusingTouchScreenDevice
	 * @param isThereCoApplicant
	 * @param image
	 * @param image1
	 * @param myTypedName1
	 * @param myTypedName2
	 * @return String pdf file path
	 * @throws MortgageApplicationPageServiceException
	 */
	@SuppressWarnings("resource")
	private String generateDiscloserPdf(String applicantName,
			String areusingTouchScreenDevice, String isThereCoApplicant,
			BufferedImage image, BufferedImage image1, String myTypedName1,
			String myTypedName2) throws MortgageApplicationPageServiceException {
		Logger.debug("inside (.) generateDiscloserPdf   method ....... "
				+ image);

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		GregorianCalendar calendar = (GregorianCalendar) GregorianCalendar
				.getInstance();
		String outputFileName = ReadConfigFile.readConfigfile().getProperty(
				Constants.PDF_FILE_PATH)
				+ "Disclosure_"
				+ applicantName
				+ "_"
				+ format.format(calendar.getTime()) + ".pdf";
		Properties prop = new Properties();
		try {

			prop.load(MortgageApplicationPageTwelevService.class
					.getClassLoader()
					.getResourceAsStream(
							MortgageApplicationConstants.DISCLOSER_CONFIGURATION_FILE_NAME));

			prop.load(MortgageApplicationPageTwelevService.class
					.getClassLoader().getResourceAsStream(
							"applicant.properties"));

			PDDocument document = new PDDocument();
			PDPage page1 = new PDPage(PDPage.PAGE_SIZE_A4);

			PDRectangle rect = page1.getMediaBox();
			document.addPage(page1);

			PDFont fontplain = PDType1Font.TIMES_ROMAN;
			PDFont fontbold = PDType1Font.HELVETICA_BOLD;

			PDPageContentStream cos = new PDPageContentStream(document, page1);

			int line = 0;

			cos.beginText();
			cos.setFont(fontbold, 12);
			cos.moveTextPositionByAmount(240, rect.getHeight() - 50 * (++line));
			cos.drawString("Applicant Agreement");
			cos.endText();
			int line2 = 0;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100);
			cos.drawString(prop.getProperty("referral1"));
			cos.endText();
			line2 = line2 + 15;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral2"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral3"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral4"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral5"));
			cos.endText();
			line2 = line2 + 20;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral6"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(40, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral7"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(40, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral8"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(40, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral10"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(40, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral11"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(40, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral12"));
			cos.endText();
			line2 = line2 + 20;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral13"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral14"));
			cos.endText();
			line2 = line2 + 20;
			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral15"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral16"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral17"));
			cos.endText();
			line2 = line2 + 20;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral18"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral19"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral201"));
			cos.endText();
			line2 = line2 + 20;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral191"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral192"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral193"));
			cos.endText();
			line2 = line2 + 20;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral202"));
			cos.endText();
			line2 = line2 + 10;
			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral203"));
			cos.endText();
			line2 = line2 + 10;
			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral204"));
			cos.endText();
			line2 = line2 + 40;

			String[][] content1 = {
					{ " Min Mortgage Value", " Max Mortgage Value",
							"    Referral Fee" },
					{ "$50,000.00", "$200,000.00", "$100.00" },
					{ "$200,001.00", "$350,000.00", "$250.00" },
					{ "$350,001.00", "$600,000.00", "$500.00" },
					{ "$600,001.00", "$900,000.00", "$750.00" },
					{ "$900,001.00", "$3,000,000.00", "$1,000.00" } };

			PdfTableCreationMethod(page1, cos, rect.getHeight() - 70 - line2,
					20, content1);

			line2 = line2 + 120;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral205"));
			cos.endText();
			line2 = line2 + 10;
			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral206"));
			cos.endText();
			line2 = line2 + 10;
			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral207"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral208"));
			cos.endText();
			line2 = line2 + 20;

			cos.beginText();
			cos.setFont(fontbold, 10);
			cos.moveTextPositionByAmount(20, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral20"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral21"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral22"));
			cos.endText();
			line2 = line2 + 20;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral24"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral25"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral26"));
			cos.endText();
			line2 = line2 + 20;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral27"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral28"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral29"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral30"));
			cos.endText();
			line2 = line2 + 20;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral31"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral32"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral33"));
			cos.endText();
			line2 = line2 + 20;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral34"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral35"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral36"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral37"));
			cos.endText();
			cos.close();
			PDPage page2 = new PDPage(PDPage.PAGE_SIZE_A4);
			document.addPage(page2);
			cos = new PDPageContentStream(document, page2);
			line2 = -80;
			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral38"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral39"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral40"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral41"));
			cos.endText();
			line2 = line2 + 20;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral42"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral43"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral44"));
			cos.endText();
			line2 = line2 + 20;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral45"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral46"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral47"));
			cos.endText();
			line2 = line2 + 10;
			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral48"));
			cos.endText();
			line2 = line2 + 20;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral49"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral50"));
			cos.endText();
			line2 = line2 + 10;

			cos.beginText();
			cos.setFont(fontplain, 10);
			cos.moveTextPositionByAmount(25, rect.getHeight() - 100 - line2);
			cos.drawString(prop.getProperty("referral51"));
			cos.endText();
			line2 = line2 + 20;

			cos.beginText();
			cos.setFont(fontbold, 10);
			cos.moveTextPositionByAmount(50, rect.getHeight() - 100 - line2);
			cos.drawString("Applicant Signature :");
			cos.endText();
			// line2=line2+10;

			if (isThereCoApplicant.equalsIgnoreCase("Yes")) {
				cos.beginText();
				cos.setFont(fontbold, 10);
				cos.moveTextPositionByAmount(350, rect.getHeight() - 100
						- line2);
				cos.drawString("Co-Applicant Signature :");
				cos.endText();
				line2 = line2 + 120;

			} else {
				line2 = line2 + 120;
			}

			if (areusingTouchScreenDevice.equalsIgnoreCase("Yes")) {

				try {
					// BufferedImage awtImage = ImageIO.read(new
					// File("simple.jpg"));
					PDXObjectImage ximage = new PDPixelMap(document, image);
					float scale = 0.5f; // alter this value to set the image
										// size
					cos.drawXObject(ximage, 50, rect.getHeight() - 100 - line2,
							ximage.getWidth() * scale, ximage.getHeight()
									* scale);
				} catch (Exception fnfex) {
					throw new MortgageApplicationPageServiceException(
							"Error Creating image In discloserPDf ", fnfex);
				}

				if (isThereCoApplicant.equalsIgnoreCase("Yes")) {
					try {
						// BufferedImage awtImage = ImageIO.read(new
						// File("simple.jpg"));
						PDXObjectImage ximage = new PDPixelMap(document, image1);
						float scale = 0.5f; // alter this value to set the image
											// size
						cos.drawXObject(ximage, 400, rect.getHeight() - 100
								- line2, ximage.getWidth() * scale,
								ximage.getHeight() * scale);
					} catch (Exception fnfex) {
						throw new MortgageApplicationPageServiceException(
								"Error Creating image In discloserPDf ", fnfex);
					}
				}
				cos.close();
			} else {
				cos.beginText();
				cos.setFont(fontbold, 10);
				cos.moveTextPositionByAmount(50, rect.getHeight() - 100 - line2
						+ 60);
				cos.drawString(myTypedName1);
				cos.endText();

				if (isThereCoApplicant.equalsIgnoreCase("Yes")) {
					cos.beginText();
					cos.setFont(fontbold, 10);
					cos.moveTextPositionByAmount(400, rect.getHeight() - 100
							- line2 + 60);
					cos.drawString(myTypedName2);
					cos.endText();

				}

				line2 = line2 + 20;
				cos.beginText();
				cos.setFont(fontplain, 10);
				cos.moveTextPositionByAmount(60, rect.getHeight() - 70 - line2);
				cos.drawString("My typed name above serves as my electronic signature for the above agreement.");
				cos.endText();
				cos.close();

			}

			document.save(outputFileName);
			document.close();

			return outputFileName;

		} catch (Exception e) {
			e.printStackTrace();

			throw new MortgageApplicationPageServiceException(
					"Error in generating Disclosers PDF for Applicant Name="
							+ applicantName, e);
		}

	}

	/**
	 *  To Create table on pdf file 
	 * @param page
	 * @param contentStream
	 * @param y
	 * @param margin
	 * @param content
	 * @throws IOException
	 */
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

		// draw the rows
		float nexty = y;
		for (int i = 0; i <= rows; i++) {
			contentStream.drawLine(margin, nexty, margin + tableWidth, nexty);
			nexty -= rowHeight;
		}

		// draw the columns
		float nextx = margin;
		for (int i = 0; i <= cols; i++) {
			contentStream.drawLine(nextx, y, nextx, y - tableHeight);
			nextx += colWidth;
		}

		// now add the text
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

}
