package document;

import helper.CouchBaseOperation;
import helper.Odoo;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javassist.bytecode.stackmap.BasicBlock.Catch;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import play.Logger;


import com.couchbase.client.CouchbaseClient;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import dto.Applicant;
import dto.ApplicantDocument;
import dto.Income;
import dto.Opportunity;
import dto.Property;

import email.SendWithUsMail;

public class MortgageDocumentConverter {
	
	private static org.slf4j.Logger logger = play.Logger.underlying();
	
	public static ApplicantDocument convertToApplicantDocument(
			String documentId,String applcantEmail) throws JSONException, IOException {
		//documentId="";
		// to get couchbase connection
		CouchBaseOperation cbo = new CouchBaseOperation();
		CouchbaseClient client1 = cbo.getConnectionToCouchBase();

		logger.debug("Inside MortgageDocumentConverterMethod "+documentId);
		ArrayList<Income> incomeList = new ArrayList<Income>();
		ArrayList<Income> co_incomeList = new ArrayList<Income>();
		Property property1 = new Property();
		Property property2 = new Property();
		Property property3 = new Property();
		Property property4 = new Property();
		Property property5 = new Property();
		Property property6 = new Property();

		Property co_property1 = new Property();
		Property co_property2 = new Property();
		Property co_property3 = new Property();
		Property co_property4 = new Property();
		Property co_property5 = new Property();
		Property co_property6 = new Property();

		ArrayList<Property> propertyList = new ArrayList<Property>();
		ArrayList<Property> co_propertyList = new ArrayList<Property>();

		Applicant aplApplicant = new Applicant();
		Applicant co_applApplicant = new Applicant();

		String stringJsonData = (String) client1.get(documentId);
	//	logger.debug("json "+stringJsonData);
		client1.shutdown(4000l, TimeUnit.MILLISECONDS);
		//client1.shutdown(9000l, TimeUnit.MILLISECONDS);

		JSONObject jsonData = new JSONObject(stringJsonData);

		Iterator<String> keysItr = jsonData.keys();
		Opportunity opportunity = new Opportunity();
		while (keysItr.hasNext()) {
			String key = keysItr.next();
			Object value = jsonData.get(key);

			switch (key) {

			case "FirstName_of_applicant":
				aplApplicant.setApplicantName(value.toString());

				break;
			case "LastName_of_applicant":
				aplApplicant.setApplicantLastName(value.toString());

				break;
			case "Email_of_applicant":
				aplApplicant.setEmailPersonal(applcantEmail);

				break;
			case "ApplicantID1":
				aplApplicant.setApplicantId(value.toString());

				break;

			case "FirstName_of_co_applicant":
				co_applApplicant.setApplicantName(value.toString());

				break;
			case "LastName_of_co_applicant":
				co_applApplicant.setApplicantLastName(value.toString());

				break;
			case "Email_of_co_applicant":
				co_applApplicant.setEmailPersonal(value.toString());

				break;
			case "Applicant-leadingGoal":
				opportunity.setWhatIsYourLendingGoal(value.toString());

				break;
			case "opporunity_id":

				int id = 0;
				try {
					id = Integer.parseInt(value.toString());
				} catch (Exception e) {
					// TODO: handle exception
					logger.error("error occurs storing JSONdata "+e.getMessage());
				}
				opportunity.setPartnerId(id);

				break;
			case "Applicant-propertylisted1":
				try{
				opportunity.setMls(value.toString());
				}catch (Exception e) {
					logger.error("error occurs storing JSONdata "+e.getMessage());
				}
			case "Applicant-propertyaddress1":
				opportunity.setAddress(value.toString());

				break;
			case "Applicant-imaginesamejob1":
				opportunity.setJob5Years(value.toString());
				break;

			case "Applicant-paymentsource1":
				opportunity.setDownPaymentComingFrom(value.toString());

				String[] downSourcePayment = value.toString().split("\n");

				int downSourcePaymentLen = downSourcePayment.length;

				List<String> selectedValues = new ArrayList<String>();
				for (int i = 0; i <= downSourcePaymentLen - 1; i++) {
					selectedValues.add(downSourcePayment[i].trim());
				}
				for (String selectedValue : selectedValues) {
					if (selectedValue.equalsIgnoreCase("Bank Account")) {

						opportunity.setBankAccount(1d);

					} else if (selectedValue.equalsIgnoreCase("RRSPS")) {
						opportunity.setRrspAmount(1d);

					} else if (selectedValue.equalsIgnoreCase("Investments")) {
						opportunity.setOtherAmount(1d);
					} else if (selectedValue.equalsIgnoreCase("Borrowed")) {
						opportunity.setBorrowedAmount(1d);

					} else if (selectedValue
							.equalsIgnoreCase("Sale of Property")) {

						opportunity.setSaleOfExistingAmount(1d);

					} else if (selectedValue.equalsIgnoreCase("Gift")) {

						opportunity.setGiftedAmount(1d);

					} else if (selectedValue.equalsIgnoreCase("Personal Cash")) {

						opportunity.setPersonalCashAmount(1d);

					} else if (selectedValue
							.equalsIgnoreCase("Existing Equity")) {

						opportunity.setExistingEquityAmount(1d);

					} else if (selectedValue.equalsIgnoreCase("Sweat Equity")) {

						opportunity.setSweatEquityAmount(1d);

					} else if (selectedValue.equalsIgnoreCase("2nd Mortgage")) {

						opportunity.setSecondaryFinancingAmount(1d);

					} else if (selectedValue.equalsIgnoreCase("Other")) {

						opportunity.setOtherAmount(1d);

					}
				}

				break;

			case "Applicant-downpayment1":

				double downpayment = 0;
				try {
					downpayment = new Double(value.toString());
				} catch (Exception e) {
					logger.error("error occurs conversion of JSONdata of Applicant-downpayment1 "+e.getMessage());
				}
				opportunity.setDownpaymentAmount(downpayment);
				break;

			case "Applicant-whoWillLive":
				if (value.toString().equalsIgnoreCase("Renter")) {
					opportunity.setIsRentalProperty(true);
				}
				break;
			case "Applicant-rentalAmount":
				double rental = 0;
				try {
					rental = new Double(value.toString());
				} catch (Exception e) {
					logger.error("error occurs conversion of JSONdata of Applicant-rentalAmount "+e.getMessage());
				}
				opportunity.setMonthlyRentalIncome(rental);
				break;

			case "Applicant-lookingfor11":
				int desiredMortgitionValue = 0;
				if (value.toString().equalsIgnoreCase("Suggest")) {
					desiredMortgitionValue = 25;

				} else {

					try {
						desiredMortgitionValue = new Integer(value.toString()
								.substring(0, 2));
					} catch (Exception e) {
						logger.error("error occurs conversion of JSONdata of Applicant-lookingfor11 "+e.getMessage());
					}

				}
				opportunity.setDesiredAmortization(desiredMortgitionValue);
				break;

			case "Applicant-currentMortgageTerm":
				opportunity.setDesiredTerm(value.toString());
				break;

			case "Applicant-mortgageinmind1":
				opportunity.setDesiredMortgageType(value.toString());
				break;

			// incomes details added from here------------------------------

			case "Applicant-type_Pension":
				Income income1 = new Income();
				income1.setTypeOfIncome(value.toString());
				incomeList.add(income1);

				break;

			case "Applicant-type_Vehicle":
				Income income2 = new Income();
				income2.setTypeOfIncome(value.toString());
				incomeList.add(income2);

				break;

			case "Applicant-type_Interest":
				Income income3 = new Income();
				income3.setTypeOfIncome(value.toString());
				incomeList.add(income3);

				break;

			case "Applicant-type_Child":
				Income income4 = new Income();
				income4.setTypeOfIncome(value.toString());
				incomeList.add(income4);

				break;

			case "Applicant-type_Child Support":
				Income income5 = new Income();
				income5.setTypeOfIncome(value.toString());
				incomeList.add(income5);

				break;
			case "Applicant-type_Spouse":
				Income income6 = new Income();
				income6.setTypeOfIncome(value.toString());
				incomeList.add(income6);

				break;

			case "Applicant-type_Dividend":
				Income income7 = new Income();
				income7.setTypeOfIncome(value.toString());
				incomeList.add(income7);

				break;

			case "Applicant-currentEmployee1":
				Income income8 = new Income();
				income8.setTypeOfIncome("Employeed");
				incomeList.add(income8);

				break;
			case "Applicant-priorEmployee1_Self-Employ":
				Income income9 = new Income();
				income9.setTypeOfIncome("Self-Employeed");
				incomeList.add(income9);

				break;
			case "Applicant-type_Comission":
				Income income10 = new Income();
				income10.setTypeOfIncome("Commision");
				incomeList.add(income10);

				break;

			case "CoApplicant-Pensiontype":
				Income income11 = new Income();
				income11.setTypeOfIncome(value.toString());
				co_incomeList.add(income11);

				break;

			case "CoApplicant-VehicleAllowancetype2":
				Income income22 = new Income();
				income22.setTypeOfIncome(value.toString());
				co_incomeList.add(income22);

				break;

			case "CoApplicant-Interesttype3":
				Income income33 = new Income();
				income33.setTypeOfIncome(value.toString());
				co_incomeList.add(income33);

				break;

			case "CoApplicant-Child Tax Credittype4":
				Income income44 = new Income();
				income44.setTypeOfIncome(value.toString());
				co_incomeList.add(income44);

				break;

			case "CoApplicant-Child Supporttype5":
				Income income55 = new Income();
				income55.setTypeOfIncome(value.toString());
				co_incomeList.add(income55);

				break;
			case "CoApplicant-Spouse Supporttype6":
				Income income66 = new Income();
				income66.setTypeOfIncome(value.toString());
				co_incomeList.add(income66);

				break;

			case "CoApplicant-Dividendtype7":
				Income income77 = new Income();
				income77.setTypeOfIncome(value.toString());
				co_incomeList.add(income77);

				break;

			case "CoApplicant-Employedtype8":
				logger.debug("CoApplicant-Employedtype8"+ value.toString());
				Income income88 = new Income();
				income88.setTypeOfIncome("Employeed");
				co_incomeList.add(income88);

				break;
			case "CoApplicant-Self-Employtype12":
				Income income99 = new Income();
				income99.setTypeOfIncome("Self-Employeed");
				co_incomeList.add(income99);

				break;
			case "CoApplicant-Comissiontype14":
				Income income101 = new Income();
				income101.setTypeOfIncome("Commision");
				co_incomeList.add(income101);

				break;

			/*
			 * // assets data inserting
			 * here----------------------------------------------
			 * 
			 * 
			 * case "Applicant-AssettypeBank Account": Asset asset1=new Asset();
			 * asset1.setAssetType("Bank Account"); assetList.add(asset1);
			 * 
			 * break;
			 */
			// imigration date inserting.....................................

			case "Applicant-whendidCanda":
				try {
					aplApplicant.setImmigrationDate(new Date(value.toString()));
				} catch (Exception e) {
					logger.error("error occurs conversion of Date "+e.getMessage());
				}

				break;

			case "Applicant-non_Resident":
				boolean val = true;
				if (value.toString().equalsIgnoreCase("yes")) {
					val = false;
				}

				aplApplicant.setNonResident(val);

				break;

			case "CoApplicant-whendidcametoCannada":
				try {
					co_applApplicant.setImmigrationDate(new Date(value
							.toString()));
				} catch (Exception e) {
					logger.error("error occurs while verifying coapplicant related data"+e.getMessage());
				}

				break;

			case "CoApplicant-non_Resident":
				boolean val1 = true;
				if (value.toString().equalsIgnoreCase("yes")) {
					val1 = false;
				}

				co_applApplicant.setNonResident(val1);

				break;

			// /------inserting applicant proprtys[---------------
			case "Applicant-address1":
				property1.setName(value.toString());

				break;
			case "Applicant-condoFees1":
				int moCondofees = 0;
				try {
					moCondofees = new Integer(value.toString());
				} catch (Exception e) {
					logger.error("error occurs while verifying Applicant-condoFees1"+e.getMessage());
				}
				property1.setMoCondoFees(moCondofees);

				break;
			case "Applicant-rentalYesNo1":
				if(value.toString().toString().equalsIgnoreCase("yes")){
					property1.setRentalProperty("yes");

				}else{
					property1.setRentalProperty("no");

				}
				
				break;
				
			case "Applicant-mortgageYesNo1":
			if(value.toString().toString().equalsIgnoreCase("yes")){
				property1.setMortgageYesNo("yes");

			}else{
				property1.setMortgageYesNo("no");

			}
			
			break;

			case "Applicant-address2":
				property2.setName(value.toString());

				break;
			case "Applicant-condoFees2":
				int moCondofees1 = 0;
				try {
					moCondofees1 = new Integer(value.toString());
				} catch (Exception e) {
					logger.error("error occurs while verifying Applicant-condoFees2"+e.getMessage());
				}
				property2.setMoCondoFees(moCondofees1);

				break;
			case "Applicant-rentalYesNo2":
				if(value.toString().toString().equalsIgnoreCase("yes")){
					property2.setRentalProperty("yes");

				}else{
					property2.setRentalProperty("no");

				}
				
				break;
				
			case "Applicant-mortgageYesNo2":
				if(value.toString().toString().equalsIgnoreCase("yes")){
					property2.setMortgageYesNo("yes");

				}else{
					property2.setMortgageYesNo("no");

				}
				
				break;

			case "Applicant-address3":
				property3.setName(value.toString());

				break;
			case "Applicant-condoFees3":
				int moCondofees2 = 0;
				try {
					moCondofees2 = new Integer(value.toString());
				} catch (Exception e) {
					logger.error("error occurs while verifying Applicant-address"+e.getMessage());
				}
				property3.setMoCondoFees(moCondofees2);

				break;
			case "Applicant-rentalYesNo3":
				if(value.toString().toString().equalsIgnoreCase("yes")){
					property3.setRentalProperty("yes");

				}else{
					property3.setRentalProperty("no");

				}
				
				break;
				
			case "Applicant-mortgageYesNo3":
				if(value.toString().toString().equalsIgnoreCase("yes")){
					property3.setMortgageYesNo("yes");

				}else{
					property3.setMortgageYesNo("no");

				}
				
				break;

			case "Applicant-address4":
				property4.setName(value.toString());

				break;
			case "Applicant-condoFees4":
				int moCondofees3 = 0;
				try {
					moCondofees3 = new Integer(value.toString());
				} catch (Exception e) {
					logger.error("error occurs while verifying Applicant-address"+e.getMessage());
				}
				property4.setMoCondoFees(moCondofees3);
				break;
				
			case "Applicant-rentalYesNo4":
				if(value.toString().toString().equalsIgnoreCase("yes")){
					property4.setRentalProperty("yes");

				}else{
					property4.setRentalProperty("no");

				}
				
				break;
				
			case "Applicant-mortgageYesNo4":
				if(value.toString().toString().equalsIgnoreCase("yes")){
					property4.setMortgageYesNo("yes");

				}else{
					property4.setMortgageYesNo("no");

				}
				
				break;

			case "Applicant-address5":
				property5.setName(value.toString());

				break;
			case "Applicant-condoFees5":
				int moCondofees4 = 0;
				try {
					moCondofees4 = new Integer(value.toString());
				} catch (Exception e) {
					logger.error("error occurs while verifying Applicant-address"+e.getMessage());
				}
				property5.setMoCondoFees(moCondofees4);
				break;
			case "Applicant-rentalYesNo5":
				if(value.toString().toString().equalsIgnoreCase("yes")){
					property5.setRentalProperty("yes");

				}else{
					property5.setRentalProperty("no");

				}
				
				break;
			case "Applicant-mortgageYesNo5":
				if(value.toString().toString().equalsIgnoreCase("yes")){
					property5.setMortgageYesNo("yes");

				}else{
					property5.setMortgageYesNo("no");

				}
				
				break;

			case "Applicant-address6":
				property6.setName(value.toString());

				break;
			case "Applicant-condoFees6":
				int moCondofees5 = 0;
				try {
					moCondofees5 = new Integer(value.toString());
				} catch (Exception e) {
					logger.error("error occurs while verifying Applicant-address"+e.getMessage());
				}
				property6.setMoCondoFees(moCondofees5);
				break;
				
			case "Applicant-rentalYesNo6":
				if(value.toString().toString().equalsIgnoreCase("yes")){
					property6.setRentalProperty("yes");

				}else{
					property6.setRentalProperty("no");

				}
				
				break;
				
			case "Applicant-mortgageYesNo6":
				if(value.toString().toString().equalsIgnoreCase("yes")){
					property6.setMortgageYesNo("yes");

				}else{
					property6.setMortgageYesNo("no");

				}
				
				break;

			case "CoApplicant-address1":
				co_property1.setName(value.toString());

				break;
			case "CoApplicant-condoFees1":
				int moCondofees11 = 0;
				try {
					moCondofees11 = new Integer(value.toString());
				} catch (Exception e) {
					logger.error("error occurs while verifying Applicant-address"+e.getMessage());
				}
				co_property1.setMoCondoFees(moCondofees11);

				break;
				
			case "CoApplicant-rentalYesNo1":
				if(value.toString().toString().equalsIgnoreCase("yes")){
					co_property1.setRentalProperty("yes");

				}else{
					co_property1.setRentalProperty("no");

				}
				
				break;

				
				
			case "CoApplicant-mortgageYesNo1":
				if(value.toString().toString().equalsIgnoreCase("yes")){
					co_property1.setMortgageYesNo("yes");

				}else{
					co_property1.setMortgageYesNo("no");

				}
				
				break;

			case "CoApplicant-address2":
				co_property2.setName(value.toString());

				break;
			case "CoApplicant-condoFees2":
				int moCondofees12 = 0;
				try {
					moCondofees12 = new Integer(value.toString());
				} catch (Exception e) {
					logger.error("error occurs while verifying Applicant-address"+e.getMessage());
				}
				co_property2.setMoCondoFees(moCondofees12);

				break;
				
			case "CoApplicant-rentalYesNo2":
				if(value.toString().toString().equalsIgnoreCase("yes")){
					co_property2.setRentalProperty("yes");

				}else{
					co_property2.setRentalProperty("no");

				}
				
				break;
				
			case "CoApplicant-mortgageYesNo2":
				if(value.toString().toString().equalsIgnoreCase("yes")){
					co_property2.setMortgageYesNo("yes");

				}else{
					co_property2.setMortgageYesNo("no");

				}
				
				break;

			case "CoApplicant-address3":
				co_property3.setName(value.toString());

				break;
			case "CoApplicant-condoFees3":
				int moCondofees22 = 0;
				try {
					moCondofees22 = new Integer(value.toString());
				} catch (Exception e) {
					logger.error("error occurs while verifying CoApplicant-address"+e.getMessage());
				}
				co_property3.setMoCondoFees(moCondofees22);

				break;
			case "CoApplicant-rentalYesNo3":
				if(value.toString().toString().equalsIgnoreCase("yes")){
					co_property3.setRentalProperty("yes");

				}else{
					co_property3.setRentalProperty("no");

				}
				
				break;
				
			case "CoApplicant-mortgageYesNo3":
				if(value.toString().toString().equalsIgnoreCase("yes")){
					co_property3.setMortgageYesNo("yes");

				}else{
					co_property3.setMortgageYesNo("no");

				}
				
				break;


			case "CoApplicant-address4":
				co_property4.setName(value.toString());

				break;
			case "CoApplicant-condoFees4":
				int moCondofees33 = 0;
				try {
					moCondofees33 = new Integer(value.toString());
				} catch (Exception e) {
					logger.error("error occurs while verifying CoApplicant-address"+e.getMessage());
				}
				co_property4.setMoCondoFees(moCondofees33);

				break;
				
			case "CoApplicant-mortgageYesNo4":
				if(value.toString().toString().equalsIgnoreCase("yes")){
					co_property4.setMortgageYesNo("yes");

				}else{
					co_property4.setMortgageYesNo("no");

				}
				
				break;
				
			case "CoApplicant-rentalYesNo4":
				if(value.toString().toString().equalsIgnoreCase("yes")){
					co_property4.setRentalProperty("yes");

				}else{
					co_property4.setRentalProperty("no");

				}
				
				break;


			case "CoApplicant-address5":
				co_property5.setName(value.toString());

				break;
			case "CoApplicant-condoFees5":
				int moCondofees44 = 0;
				try {
					moCondofees44 = new Integer(value.toString());
				} catch (Exception e) {
					logger.error("error occurs while verifying CoApplicant-address"+e.getMessage());
				}
				co_property5.setMoCondoFees(moCondofees44);

				break;
			case "CoApplicant-rentalYesNo5":
				if(value.toString().toString().equalsIgnoreCase("yes")){
					co_property5.setRentalProperty("yes");

				}else{
					co_property5.setRentalProperty("no");

				}
				
				break;
				
			case "CoApplicant-mortgageYesNo5":
				if(value.toString().toString().equalsIgnoreCase("yes")){
					co_property5.setMortgageYesNo("yes");

				}else{
					co_property5.setMortgageYesNo("no");

				}
				
				break;


			case "CoApplicant-address6":
				co_property6.setName(value.toString());

				break;
			case "CoApplicant-condoFees6":
				int moCondofees55 = 0;
				try {
					moCondofees55 = new Integer(value.toString());
				} catch (Exception e) {
					logger.error("error occurs while verifying CoApplicant-address"+e.getMessage());
				}
				co_property6.setMoCondoFees(moCondofees55);

				break;
			case "CoApplicant-rentalYesNo6":
				if(value.toString().toString().equalsIgnoreCase("yes")){
					co_property6.setRentalProperty("yes");

				}else{
					co_property6.setRentalProperty("no");

				}
				
				break;
				
			case "CoApplicant-mortgageYesNo6":
				if(value.toString().toString().equalsIgnoreCase("yes")){
					co_property6.setMortgageYesNo("yes");

				}else{
					co_property6.setMortgageYesNo("no");

				}
				
				break;


			default:
				break;
			}

		}
		// adding Applicantpropertys objects to arraylist of Applicantpropertys
		propertyList.add(property1);
		propertyList.add(property2);
		propertyList.add(property3);
		propertyList.add(property4);
		propertyList.add(property5);
		propertyList.add(property6);
		// adding Co_Applicantpropertys objects to arraylist of
		// Co_Applicantpropertys

		co_propertyList.add(co_property1);
		co_propertyList.add(co_property2);
		co_propertyList.add(co_property3);
		co_propertyList.add(co_property4);
		co_propertyList.add(co_property5);
		co_propertyList.add(co_property6);

		// adding the ArrayListofAppliacant propertys to Applicant
		aplApplicant.setProperties(propertyList);

		// adding the ArrayListofCo_appliacnt propertys to Co_appliacnt

		co_applApplicant.setProperties(co_propertyList);

		// adding Aplicant income list to Applicant
		aplApplicant.setIncomes(incomeList);

		// adding Co_applicantincome list to Co_applicant

		co_applApplicant.setIncomes(co_incomeList);

		ArrayList<Applicant> applicantList = new ArrayList<Applicant>();
		applicantList.add(aplApplicant);
		applicantList.add(co_applApplicant);
		// adding Applicant and co_Applicant to opprtunity
		opportunity.setApplicants(applicantList);
		// calling RequiredDocumentBuilder method To get applicantDocumentList
		ApplicantDocument applicantDocumentList = new RequiredDocumentBuilder()
				.RequiredDocumentBuilderMethod(opportunity);
		logger.debug("applicantDocumentList :" + applicantDocumentList.toString());
		
		//storing documentlist data to  couchbase
		ObjectMapper mapper=new ObjectMapper();
		//JsonGenerator jsonData1=new JsonGenerator();
		//mapper.writeValue(jsonData1, applicantDocumentList);
		
		logger.info("all documentList-----------"+mapper.writeValueAsString(applicantDocumentList));

		try{
			JSONObject json=new JSONObject(mapper.writeValueAsString(applicantDocumentList));
			json.put("Type","DocMaster");
			json.put("Type_DocMaster","DocMaster");

			json.put("DocMasterUrl","");
			String opporunityName=new Odoo().getOpprtunityName(json.get("opprtunityId").toString());
			json.put("masterdoc_opporunityName",opporunityName);

		//	new	CouchBaseOperation().storeDataInCouchBase("DocumentListOfApplicationNo_"+opportunity.getPartnerId(), json.toString());

		}catch(Exception e){
			logger.error("error occurs while processing properties of Applicants"+e.getMessage());
		}
		
/*try{
		SendWithUsMail sendWithUsMail=new SendWithUsMail();
		if(applicantDocumentList.getLendingGoal().equalsIgnoreCase("Purchase")){
			SendWithUsMail.mailTemplateOfPurchaseDocuments(applicantDocumentList);
			ArrayList list=new Odoo().getReferralname(applicantDocumentList.getOpprtunityId());
			try{
				if(list.get(2).toString().equalsIgnoreCase("Realtor")){
			sendWithUsMail.mailTemplateOfRealtor(applicantDocumentList, list.get(0).toString(), list.get(1).toString());
				}}catch(Exception e){
				
			}
			}else if(applicantDocumentList.getLendingGoal().equalsIgnoreCase("Refinance")){
			SendWithUsMail.mailTemplateOfRefinanceDocuments(applicantDocumentList);

		}else if(applicantDocumentList.getLendingGoal().equalsIgnoreCase("Pre-Approval")){
			

			sendWithUsMail.mailTemplateOfPreeApprovalDocuments(applicantDocumentList);
			ArrayList list=new Odoo().getReferralname(applicantDocumentList.getOpprtunityId());
			try{
				if(list.get(2).toString().equalsIgnoreCase("Realtor")){
			sendWithUsMail.mailTemplateOfRealtor(applicantDocumentList, list.get(0).toString(), list.get(1).toString());
				}}catch(Exception e){
				
			}
		}
}catch(Exception e){
	e.printStackTrace();
}*/

		
		//RestCallClass.restCallTaskCreater(mapper.writeValueAsString(applicantDocumentList));
		
	//new	CouchBaseOperation().storeDataInCouchBase("DocumentListOfApplicationNo_"+opportunity.getPartnerId(), applicantDocumentList);
	//new	EmailTemplateForDocumentList().emailTemplateForDocumentList(applicantDocumentList);
		
		
		
		
		

		return applicantDocumentList;

	}
	public static void main(String[] args) throws JSONException, IOException {
		//new MortgageDocumentConverter().convertToApplicantDocument("Applicant_2534");
	}
}
