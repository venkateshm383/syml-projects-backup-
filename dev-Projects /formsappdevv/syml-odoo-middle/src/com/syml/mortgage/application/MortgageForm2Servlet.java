package com.syml.mortgage.application;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.syml.address.splitAddress.Address;
import com.syml.addressgroup.pojo.AddressGroup;
import com.syml.constants.SymlConstant;
import com.syml.couchbase.CouchBaseOperation;
import com.syml.openerp.CreateApplicant;

public class MortgageForm2Servlet extends HttpServlet{
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		Logger log = LoggerFactory.getLogger(MortgageForm2Servlet.class);

	
		String uniid=req.getParameter("uniid");
		SymlConstant sc=new SymlConstant();
		HttpSession ses1=req.getSession(true);
		String form1UniqueId1=(String)ses1.getAttribute("form1uniqueId");
		String applicantID=(String)ses1.getAttribute("applicantID");

		AddressGroup currentaddressObj = null;
		AddressGroup prioraddress1Obj = null;
		AddressGroup prioraddress2Obj = null;
		AddressGroup prioraddress3Obj = null;
		Address addressSplit=new Address();
	

		String subForm ="Mortgage Application 2";
		
		HashMap dataStoreValue=new HashMap();
		uniid=form1UniqueId1;
		CreateApplicant cLead;
		if(uniid.equals(form1UniqueId1)){

try{
	
	log.debug("inside service method");


	ArrayList<AddressGroup> listOfAddresses = new ArrayList<AddressGroup>();
	String currentAddress=req.getParameter("whatis"); 
	String currentYear=req.getParameter("howlong83"); 
	String currentMonths=req.getParameter("input84"); 
	String currentSumMonth=req.getParameter("currentaddressmonthsum");   
	String totalcurrentMonths=req.getParameter("totalcurrentmonths");
	int totalcurrentMonthsInt=0;

	try{
totalcurrentMonthsInt = Integer.parseInt(totalcurrentMonths);
	}catch (Exception e) {
// TODO: handle exception
}
	log.debug("*********inside  currentAddress************ ");
	log.debug("currentAddress"+currentAddress);
	log.debug("currentYear"+currentYear);
	log.debug("currentMonths"+currentMonths);
	log.debug("currentSumMonth"+currentSumMonth);
	log.debug("totalcurrentMonths"+totalcurrentMonths);



	DateFormat odooDate = new SimpleDateFormat("MM/dd/yyyy");
	Calendar cal = Calendar.getInstance();
	int totalSumMonths=0;
	try{
		totalSumMonths=Integer.parseInt(totalcurrentMonths);
	}catch(Exception e){
		e.printStackTrace();
	}
	cal.add(Calendar.MONTH,-totalSumMonths );
	String currentDateTime=(odooDate.format(cal.getTime()));
	log.debug("currentDateTime Extra"+currentDateTime);

	DateFormat df = new SimpleDateFormat("MM/dd/yyyy"); 
	Date startDate=null;
	String newDateString=null;
	try {
		startDate = df.parse(currentDateTime);
		newDateString = df.format(startDate);
		log.debug(newDateString);
	} catch (ParseException e) {
		e.printStackTrace();
	}	
	log.debug("startDate"+startDate);
	if(currentAddress != null){
		currentaddressObj = new AddressGroup(currentAddress,currentYear,currentMonths,currentSumMonth,totalcurrentMonths);
		listOfAddresses.add(currentaddressObj);
		HashMap currentAddressSplit =addressSplit.getProperAddress(currentAddress);

		String name=null;
		String city=null;
		String province=null;
		String postalcode=null;
		if(currentAddressSplit != null){
			name=(String)currentAddressSplit.get("address1");
			city=(String)currentAddressSplit.get("city");
			province=(String)currentAddressSplit.get("Province");
			postalcode=(String)currentAddressSplit.get("postalcode");
			//creating Applicant
			cLead = new CreateApplicant();

			cLead.createApplicantAddress(applicantID, name, city, province,postalcode, startDate);
			log.debug("Applicant  created with currentAddressSplit ");
		}
dataStoreValue.put("Applicant-CurrentAddress",currentAddress);
dataStoreValue.put("Applicant-currentYear",currentYear);
dataStoreValue.put("Applicant-currentMonths",currentMonths);
dataStoreValue.put("Applicant-currentSumMonth",currentSumMonth);
dataStoreValue.put("Applicant-totalcurrentMonths",totalcurrentMonths);
		
	
	
	}
	
	if((totalcurrentMonths != null && totalcurrentMonths.length()!=0 && !totalcurrentMonths.equals(""))&& totalcurrentMonthsInt<=36){
		log.debug("**********inside 1st priorAddress1 *******************");

		String priorAddress1=req.getParameter("whatwas52"); 
		String priorYear1=req.getParameter("howlong"); 
		String priorMonths1=req.getParameter("input87"); 
		String priorSumMonth1=req.getParameter("priormonthsum1"); 
		String totalpriorcurrentmonths1=req.getParameter("totalpriormonths1"); 



		//	
		int totalpriorcurrentmonths1Int=0;

		try{
			totalpriorcurrentmonths1Int = Integer.parseInt(totalpriorcurrentmonths1);
		}catch (Exception e) {
			// TODO: handle exception
			log.error("exception in converting totalpriorcurrentmonths to int : "+e);
		}
		log.debug("priorAddress1"+priorAddress1);
		log.debug("priorYear1"+priorYear1);
		log.debug("priorMonths1"+priorMonths1);
		log.debug("priorSumMonth1"+priorSumMonth1);
		log.debug("totalpriorcurrentmonths1"+totalpriorcurrentmonths1);

		//New Logic For prior Date 1
		log.debug("totalMonths:"+totalpriorcurrentmonths1);

		log.debug("TotalDate is:"+totalpriorcurrentmonths1);

		Calendar prical1 = Calendar.getInstance();
		prical1.add(Calendar.MONTH,-totalpriorcurrentmonths1Int );
		String currentDateTimenew=(df.format(prical1.getTime()));

		log.debug("CurrentDate for Prior Address is:"+currentDateTimenew);

		Date startDatePrior1=null;
		String newDateString1=null;
		try {
			startDatePrior1 = df.parse(currentDateTimenew);
			newDateString1 = df.format(startDatePrior1);
			log.debug(newDateString1);
		} catch (ParseException e) {
			e.printStackTrace();
		}	
		log.debug("PriorAddress Date"+startDatePrior1);
		if(currentAddress != null && priorAddress1 != null){
			//currentaddressObj = new AddressGroup(currentAddress,currentYear,currentMonths,currentSumMonth,totalcurrentMonths);
			prioraddress1Obj = new AddressGroup(priorAddress1,priorYear1,priorMonths1,priorSumMonth1,totalpriorcurrentmonths1);
			//listOfAddresses.add(currentaddressObj);
			listOfAddresses.add(prioraddress1Obj);	
			HashMap currentAddressSplit =addressSplit.getProperAddress(priorAddress1);

			String name=null;
			String city=null;
			String province=null;
			String postalcode=null;
			if(currentAddressSplit != null){
				name=(String)currentAddressSplit.get("address1");
				city=(String)currentAddressSplit.get("city");
				province=(String)currentAddressSplit.get("Province");
				postalcode=(String)currentAddressSplit.get("postalcode");
									//Creating Applicant
				cLead = new  CreateApplicant();

				//logic for date
				cLead.createApplicantAddress(applicantID, name, city, province,postalcode, startDatePrior1);

				log.debug("Going to OpenERP Create Applicant address ");
			
				
				dataStoreValue.put("Applicant-priorAddress1",priorAddress1);
				dataStoreValue.put("Applicant-priorYear1",priorYear1);
				dataStoreValue.put("Applicant-priorMonths1",priorMonths1);
				dataStoreValue.put("Applicant-priorSumMonth1",priorSumMonth1);
				dataStoreValue.put("Applicant-totalpriorcurrentmonths1",totalpriorcurrentmonths1);
			

			}//spliting address



		}//checking prior address is not null
		
		log.debug("taking 2nd prior address");
		String priorAddress2=req.getParameter("whatwas"); 
		String priorYear2=req.getParameter("howlong89"); 
		String priorMonths2=req.getParameter("monthsprior2"); 
		String priorSumMonth2=req.getParameter("priormonthsum2"); 
		String totalpriorcurrentmonths2=req.getParameter("totalpriormonths2"); 
		int totalpriorcurrentmonths2Int=0;
		try{
			totalpriorcurrentmonths2Int = Integer.parseInt(totalpriorcurrentmonths2);
		}catch(Exception e){


		}
		
		if(totalpriorcurrentmonths2Int<=36 && (totalpriorcurrentmonths2!=null && !totalpriorcurrentmonths2.equals(""))){
			log.debug("**********inside 1st priorAddress2 *******************");


			log.debug("priorAddress2"+priorAddress2);
			log.debug("priorYear2"+priorYear2);
			log.debug("priorMonths2"+priorMonths2);
			log.debug("priorSumMonth2"+priorSumMonth2);
			log.debug("totalpriorcurrentmonths2"+totalpriorcurrentmonths2);
			//New Logic For prior Date 2
			log.debug("totalpriorcurrentmonths2:"+totalpriorcurrentmonths2);

			log.debug("TotalDate is:"+totalpriorcurrentmonths2Int);

			Calendar prical2 = Calendar.getInstance();
			prical2.add(Calendar.MONTH,-totalpriorcurrentmonths2Int );
			String currentDateTimenew2=(df.format(prical2.getTime()));

			log.debug("CurrentDate for Prior Address 2 is:"+currentDateTimenew2);

			Date startDatePrior2=null;
			String newDateString2=null;
			try {
				startDatePrior2 = df.parse(currentDateTimenew2);
				newDateString2 = df.format(startDatePrior2);
				log.debug(newDateString2);
			} catch (ParseException e) {
				e.printStackTrace();
			}	
			log.debug("PriorAddress Date"+startDatePrior2);
			if(priorAddress2 != null){

				//currentaddressObj = new AddressGroup(currentAddress,currentYear,currentMonths,currentSumMonth,totalcurrentMonths);
				//prioraddress1Obj = new AddressGroup(priorAddress1,priorYear1,priorMonths1,priorSumMonth1,totalpriorcurrentmonths1);
				prioraddress2Obj = new AddressGroup(priorAddress2,priorYear2,priorMonths2,priorSumMonth2,totalpriorcurrentmonths2);

				
				listOfAddresses.add(prioraddress2Obj);


				HashMap currentAddressSplit =addressSplit.getProperAddress(priorAddress2);

				String name=null;
				String city=null;
				String province=null;
				String postalcode=null;
				if(currentAddressSplit != null){
					name=(String)currentAddressSplit.get("address1");
					city=(String)currentAddressSplit.get("city");
					province=(String)currentAddressSplit.get("Province");
					postalcode=(String)currentAddressSplit.get("postalcode");
					cLead = new CreateApplicant();

					cLead.createApplicantAddress(applicantID, name, city, province,postalcode, startDatePrior2);
					log.debug("Applicant created with priorAddress2 ");


					log.debug("CouchBase data is appending..");

					
				
					dataStoreValue.put("Applicant-priorAddress2",priorAddress2);
					dataStoreValue.put("Applicant-priorYear2",priorYear2);
					dataStoreValue.put("Applicant-priorMonths2",priorMonths2);
					dataStoreValue.put("Applicant-priorSumMonth2",priorSumMonth2);
					dataStoreValue.put("Applicant-totalpriorcurrentmonths2",totalpriorcurrentmonths2);
				

										 
				}//spliting prior address 2

			}//checking prior address 2 is not null
			
			
			
			String priorAddress3=req.getParameter("whatwas56"); 
			String priorYear3=req.getParameter("howlong92"); 
			String priorMonths3=req.getParameter("input93");			   
			String priorSumMonth3=req.getParameter("priormonthsum3"); 
			String totalpriorcurrentmonths3=req.getParameter("totalpriormonths394"); 
			int totalpriorcurrentmonths3Int=0;
			try{
				totalpriorcurrentmonths3Int = Integer.parseInt(totalpriorcurrentmonths3);
			}catch(Exception e){

			}
			
			
			
			
			log.debug("inside PriorAddress3");
			
			log.debug("priorAddress3"+priorAddress3);
			log.debug("priorYear3"+priorYear3);
			log.debug("priorMonths3"+priorMonths3);
			log.debug("priorSumMonth3"+priorSumMonth3);
			log.debug("totalpriorcurrentmonths3"+totalpriorcurrentmonths3);
			//New Logic For prior Date 2
			log.debug("totalpriorcurrentmonths2:"+totalpriorcurrentmonths2);

			//New Logic For prior Date 3
			log.debug("totalpriorcurrentmonths3:"+totalpriorcurrentmonths3);



			Date startDatePrior3=null;
			String newDateString3=null;
			log.debug("TotalDate is:"+totalpriorcurrentmonths3Int);

			Calendar prical3 = Calendar.getInstance();
			prical3.add(Calendar.MONTH,-totalpriorcurrentmonths3Int );
			String currentDateTimenew3=(df.format(prical3.getTime()));

			log.debug("CurrentDate for Prior Address 3 is:"+currentDateTimenew3);


			try {
				startDatePrior3 = df.parse(currentDateTimenew3);
				newDateString3 = df.format(startDatePrior3);
				log.debug(newDateString3);
			} catch (ParseException e) {
				e.printStackTrace();
			}	
			log.debug("PriorAddress Date"+startDatePrior3);

			if(currentAddress != null && priorAddress1 != null && priorAddress2 != null && priorAddress3 != null){


				prioraddress3Obj = new AddressGroup(priorAddress3,priorYear3,priorMonths3,priorSumMonth3,totalpriorcurrentmonths3);

				
				listOfAddresses.add(prioraddress3Obj);




				HashMap currentAddressSplit =addressSplit.getProperAddress(priorAddress3);

				String name=null;
				String city=null;
				String province=null;
				String postalcode=null;
				if(currentAddressSplit != null){
					name=(String)currentAddressSplit.get("address1");
					city=(String)currentAddressSplit.get("city");
					province=(String)currentAddressSplit.get("Province");
					postalcode=(String)currentAddressSplit.get("postalcode");
					log.debug("going to openERP");
					cLead = new CreateApplicant();
					cLead.createApplicantAddress(applicantID, name, city, province,postalcode, startDatePrior3);
					log.debug("Applicant created with priorAddress3 ");


					
				
					dataStoreValue.put("Applicant-priorAddress3",priorAddress3);
					dataStoreValue.put("Applicant-priorYear3",priorYear3);
					dataStoreValue.put("Applicant-priorMonths3",priorMonths3);
					dataStoreValue.put("Applicant-priorSumMonth3",priorSumMonth3);
					dataStoreValue.put("Applicant-totalpriorcurrentmonths3",totalpriorcurrentmonths3);
				
					 					 
				}

			}//end of logic of prior address 3

		}// end of logic of prior address 2

		
	}// logic for prioraddress1
	
	dataStoreValue.put("Applicant-SubForm-2",subForm);

	
	CouchBaseOperation appendData=new CouchBaseOperation();
	
	appendData.appendDataInCouchBase("Applicant_"+applicantID, dataStoreValue);
	log.debug("Data created in coucbase for MortgageForm2");
	
	req.setAttribute("uniid",uniid);
	//req.setAttribute("applicantId",applicantId);
	req.getRequestDispatcher("MortgageApplication3.jsp").include(req, res);
		
}catch(Exception e){
	e.printStackTrace();
	
}
		}else{
			req.setAttribute("message", " We are sorry, but it seems the security and reliability of your internet connection may have been weakened.  To protect your identity and the security of your information, can you please submit this application again");
			req.getRequestDispatcher("MortgageApplicationSucess.jsp").forward(req, res);
			
		}
	}
	

}
