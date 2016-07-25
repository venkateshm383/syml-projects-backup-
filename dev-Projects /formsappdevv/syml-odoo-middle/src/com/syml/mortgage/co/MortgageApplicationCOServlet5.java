package com.syml.mortgage.co;
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
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
//import org.apache.catalina.connector.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.syml.address.splitAddress.PropertySplitter;
import com.syml.addressgroup.pojo.Incomes;
import com.syml.addressgroup.pojo.PropertyGrouping;
import com.syml.constants.SymlConstant;
import com.syml.couchbase.CouchBaseOperation;
import com.syml.jotform.createform.SubmitReferalForm2;
import com.syml.jotform.ip.GetMyIP;
import com.syml.mail.MortgageApplicationTemplate.MailTemplateForThirdCoApplicant;

import com.syml.openerp.CheckReferalResource;
import com.syml.openerp.CreateApplicant;
import com.syml.openerp.CreateLead;
public class MortgageApplicationCOServlet5 extends HttpServlet{
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		Logger log = LoggerFactory.getLogger(MortgageApplicationCOServlet5.class);
		SymlConstant sc=new SymlConstant();
		
		String formType ="Mortgage Application";
		String subForm ="Mortgage Co-Application 5";
		String uniid=req.getParameter("uniid");

		HttpSession ses=req.getSession(true);
		String form1UniqueId=(String)ses.getAttribute("form1uniqueId");
		
		String applicantId=(String) ses.getAttribute("applicantId2");


		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		HashMap dataStoreValue=new HashMap();
		log.debug("old unique id is  "+uniid);
		log.debug("sessoin unique id is  "+form1UniqueId);
		if(uniid.equals(form1UniqueId)){
		ses.setAttribute("form1uniqueId",uniid);
		//get current date time with Calendar()
		Calendar cal = Calendar.getInstance();
		String currentDateTime=(dateFormat.format(cal.getTime()));

		//get ip of latest form sumitted
		GetMyIP myip=new GetMyIP();
		//	String ip=myip.myIp(sc.getMortgageForm5Id());


	
		
		try{
			log.debug("inside service method");

			String assetType=req.getParameter("pleaseselect");  //assetType
			if(assetType==null || assetType.isEmpty()){
				
				
				
				String otherApplicant=req.getParameter("isthere");
				
				log.debug(" second  co_applicant exsist "+otherApplicant);
				
				
				String applicantName="";
				String applicantLastName="";
				String applicantOneEmail="";
				String applicantTwoEmail="";
				String applicantThreeEmail="";
				int applicantId1=0;
				String coApplicantName="";
				String referrerEmail="";
				String applicantTwoFirstName="";
				String applicantTwoLastName="";
				String coApplicantThreeEmail="";
				coApplicantThreeEmail=req.getParameter("pleaseprovide");
				
				try{
					// referrerEmail=(String)ses.getAttribute("referralEmail");
					 applicantName=(String)ses.getAttribute("applicantFirstName");
					applicantLastName=(String)ses.getAttribute("applicantLasttName");
					 applicantOneEmail=(String)ses.getAttribute("applicantEmail");
					 applicantId1=Integer.parseInt((String)ses.getAttribute("applicantID"));
					 }catch(Exception  e){
					 }
					 try{
					 applicantTwoFirstName=(String)ses.getAttribute("co_applicantFirstName");
					 applicantTwoLastName=(String)ses.getAttribute("co_applicantLastName");
					 applicantTwoEmail=(String)ses.getAttribute("co_applicantEmail");
					//applicantId2=Integer.parseInt((String)ses.getAttribute("applicantId2"));
					 }catch(Exception e){
					  
					 }
					 //to attcahed to lead 
					 String applicantID2=(String)ses.getAttribute("applicantId2");
						req.setAttribute("uniid",uniid);
						int leadId=0;
						
						try{
							leadId=Integer.parseInt((String)ses.getAttribute("crmLeadId"));
						}catch(Exception e){
							
						}
						
						int applicantIDInt=0;
					int	applicantID2Int=0;
						try{
					applicantIDInt = Integer.parseInt(applicantID2);
						
						}catch(Exception e){
							
						}
					 CreateApplicant createApplicant=new CreateApplicant();
					 createApplicant.assignOppertunity(applicantIDInt, 0,0,0,0,0,0,0, leadId);
					 dataStoreValue.put("CoApplicant-otherApplicant",otherApplicant);
						dataStoreValue.put("CoApplicant-subForm4",subForm);
						String applicantIDone=(String) ses
								.getAttribute("applicantID");
						CouchBaseOperation	 storeData=new CouchBaseOperation();
					storeData.appendDataInCouchBase("Applicant_"+applicantIDone, dataStoreValue);
						log.debug("CouchBase data created"); 
				
				
					 
		             			if(otherApplicant.equalsIgnoreCase("Yes") && otherApplicant!=null){
		             				
		             			MailTemplateForThirdCoApplicant.MailTemplateForThirdCoApplicantMethod(applicantOneEmail, applicantName, applicantTwoFirstName,applicantTwoEmail, coApplicantThreeEmail, applicantId1, "hr.applicant");
		             				
		             			}//}else{
		             			req.setAttribute("uniid",uniid);
		             				
		             				ses.setAttribute("form1uniqueId", uniid);
		             	     		//req.setAttribute("applicantId",applicantId);
		             	     		req.getRequestDispatcher("MortgageApplication8.jsp").include(req, res);
		             	     		
				
			}else{
				
			
			log.debug(assetType);
			String[] asstypeArray=assetType.split("\n");

			log.debug("asserttype array : "+asstypeArray.toString());

			int strasstypelen=asstypeArray.length;

			List<String> selectedValues=new ArrayList<String>();
			for(int i=0; i<=strasstypelen-1;i++ ){
				selectedValues.add(asstypeArray[i]);
			}
			log.debug("after incomeoddlist"+selectedValues.size());
			log.debug("select values : "+selectedValues.toString());

int counter=0;
			JSONArray arrayObj=null;
			CreateApplicant aseetsApplicant;
			for(String selectedValue : selectedValues){	
				log.debug("selected values inside loop : "+selectedValue);
				if(selectedValue.trim().equalsIgnoreCase("Bank Account") && selectedValue!=null){
					//String bankAccount=req.getParameter("clickto154");
					log.debug("inside Bank Account");
					dataStoreValue.put("CoApplicant-assetType",selectedValue);
					arrayObj = new JSONArray(req.getParameter("clickto154"));
					log.debug("arrayobjjson : "+arrayObj.toString());
					log.debug("json array length : "+arrayObj.length());

					for (int i=0;i<arrayObj.length();i++){
						JSONObject job=arrayObj.getJSONObject(i);
					

						if((job.get("Description")!=null && !job.get("Description").equals("")) &&  (job.get("Value")!=null && !job.get("Value").equals(""))){
							log.debug("inside if Bank1");
							if(job.isNull("Ownership")){

								log.debug("all values are not null and empty");

								String discription=(String)job.get("Description")+","+"\t"+"Bank Acct";
								String value=(String)job.get("Value");

								//String ownership=(String)job.get("Ownership");
								aseetsApplicant=new CreateApplicant();
								log.debug("Applicant Assets to be created .........");

								aseetsApplicant.createAssetApplicant(applicantId,"other", discription, value);		
								log.debug("Applicant Assets created for Bank withOut Value and OwnerShip");

								//Logic for CouchBase

								

								dataStoreValue.put("CoApplicant-Bank Accountdiscription",discription);
								dataStoreValue.put("CoApplicant-Bank Accountvalue",value);
								//dataStoreValue.put("ownership",ownership);

							}else{
								//ownership is comming as null
								log.debug("all values are not null and empty except ownership");
								String discription=(String)job.get("Description")+","+"\t"+"Bank Acct";
								String value=(String)job.get("Value");

								String ownership=(String)job.get("Ownership");
								aseetsApplicant=new CreateApplicant();
								log.debug("Applicant Assets to be created .........");

								aseetsApplicant.createAssetApplicant(applicantId,"other", discription, value);		
								log.debug("Applicant Assets created for Bank withOut Value and OwnerShip");

								//Logic for CouchBase


								dataStoreValue.put("CoApplicant-Bank Accountdiscription",discription);
								dataStoreValue.put("CoApplicant-Bank Accountvalue",value);
								dataStoreValue.put("CoApplicant-Bank Accountownership",ownership);


							}



						}else if((job.get("Description")!=null && job.get("Description").equals("")) &&  (job.get("Value")!=null && !job.get("Value").equals(""))){
							log.debug("inside if Bank2");
							if(job.isNull("Ownership")){
								String discription="bank description not filled";
								String value=(String)job.get("Value");
								//String ownership=(String)job.get("Ownership");

								aseetsApplicant=new CreateApplicant();
								log.debug("Applicant Assets to be created .........");

								int j=0;

								String str1 = Integer.toString(j);

								aseetsApplicant.createAssetApplicant(applicantId,"other", discription, str1);

								//logic for couchBase


							

								dataStoreValue.put("CoApplicant-Bank Accountdiscription1",discription);
								dataStoreValue.put("CoApplicant-Bank Accountvalue1", value);
								//dataStoreValue.put("ownership",ownership);
							}else{
								String discription="bank description not filled";
								String value=(String)job.get("Value");
								String ownership=(String)job.get("Ownership");

								aseetsApplicant=new CreateApplicant();
								log.debug("Applicant Assets to be created .........");

								int j=10;

								String str1 = Integer.toString(j);

								aseetsApplicant.createAssetApplicant(applicantId,"other", discription, str1);

								//logic for couchBase



								dataStoreValue.put("CoApplicant-Bank Accountdiscription1",discription);
								dataStoreValue.put("CoApplicant-Bank Accountvalue1", value);

								dataStoreValue.put("CoApplicant-Bank Accountownership1",ownership);

							}

						}else if((job.get("Description")!=null && !job.get("Description").equals("")) &&  (job.get("Value")!=null && job.get("Value").equals(""))){
							log.debug("inside if Bank3");


							if(job.isNull("Ownership")){
								String discription=(String)job.get("Description")+","+"\t"+"Bank Acct";
								String value="0";
								//String ownership=(String)job.get("Ownership");

								aseetsApplicant=new CreateApplicant();
								log.debug("Applicant Assets to be created .........");


								aseetsApplicant.createAssetApplicant(applicantId,"other", discription, value);

								//logic for couchBase


								

								dataStoreValue.put("CoApplicant-Bank Accountdiscription2",discription);
								dataStoreValue.put("CoApplicant-Bank Accountvalue2", value);
								//dataStoreValue.put("ownership",ownership);
							}else{
								String discription=(String)job.get("Description")+","+"\t"+"Bank Acct";
								String value="0";
								String ownership=(String)job.get("Ownership");

								aseetsApplicant=new CreateApplicant();
								log.debug("Applicant Assets to be created .........");



								aseetsApplicant.createAssetApplicant(applicantId,"other", discription, value);

								//logic for couchBase



								dataStoreValue.put("CoApplicant-Bank Accountdiscription2",discription);
								dataStoreValue.put("CoApplicant-Bank Accountvalue2", value);

								dataStoreValue.put("CoApplicant-Bank Accountownership2",ownership);

							}

						}else{
							log.debug("inside if Bank4");


							if(job.isNull("Ownership")){
								String discription="bank name not filled";
								String value="0";
								//String ownership=(String)job.get("Ownership");

								aseetsApplicant=new CreateApplicant();
								log.debug("Applicant Assets to be created .........");


								aseetsApplicant.createAssetApplicant(applicantId,"other", discription, value);

								//logic for couchBase


								dataStoreValue.put("CoApplicant-Bank Accountdiscription3",discription);
								dataStoreValue.put("CoApplicant-Bank Accountvalue3", value);
								//dataStoreValue.put("ownership",ownership);
							}else{
								String discription="bank not filled";
								String value="0";
								String ownership=(String)job.get("Ownership");

								aseetsApplicant=new CreateApplicant();
								log.debug("Applicant Assets to be created .........");



								aseetsApplicant.createAssetApplicant(applicantId,"other", discription, value);

								//logic for couchBase



								dataStoreValue.put("CoApplicant-Bank Accountdiscription3",discription);
								dataStoreValue.put("CoApplicant-Bank Accountvalue3", value);

								dataStoreValue.put("CoApplicant-Bank Accountownership3",ownership);

							}

						}



					}




				}
				
				if(selectedValue.trim().equalsIgnoreCase("RRSP / TSFA") && selectedValue!=null){
					log.debug("inside RRSP");
					arrayObj = new JSONArray(req.getParameter("rrspsand"));

					log.debug("arrayobjjson : "+arrayObj.toString());
					log.debug("json array length : "+arrayObj.length());
					dataStoreValue.put("AssetType2",selectedValue);

					for (int i=0;i<arrayObj.length();i++){
						JSONObject job=arrayObj.getJSONObject(i);
						
						if((job.get("Description")!=null && !job.get("Description").equals("")) &&  (job.get("Value")!=null && !job.get("Value").equals(""))){
							log.debug("inside if RRSPs1");
							if(job.isNull("Ownership")){
							
								log.debug("all values are not null and empty");
								
							String discription=(String)job.get("Description");
							String value=(String)job.get("Value");

							//String ownership=(String)job.get("Ownership");
							aseetsApplicant=new CreateApplicant();
							log.debug("Applicant Assets to be created .........");

							aseetsApplicant.createAssetApplicant(applicantId,"RRSPs", discription, value);		
							log.debug("Applicant Assets created for RRSPs withOut Value and OwnerShip");

							//Logic for CouchBase

							

							dataStoreValue.put("CoApplicant-discription RRS/TSFA",discription);
							dataStoreValue.put("CoApplicant-value RRSP/SFA",value);
							//dataStoreValue.put("ownership",ownership);

							}else{
								//ownership is comming as null
								log.debug("all values are not null and empty except ownership");
								String discription=(String)job.get("Description");
								String value=(String)job.get("Value");

								String ownership=(String)job.get("Ownership");
								aseetsApplicant=new CreateApplicant();
								log.debug("Applicant Assets to be created .........");

								aseetsApplicant.createAssetApplicant(applicantId,"RRSPs", discription, value);		
								log.debug("Applicant Assets created for RRSPs withOut Value and OwnerShip");

								//Logic for CouchBase

								

								dataStoreValue.put("CoApplicant-discription RRS/TSFA",discription);
								dataStoreValue.put("CoApplicant-value RRS/TSFA",value);
								dataStoreValue.put("CoApplicant-ownership RRS/TSFA",ownership);


							}



						}else if((job.get("Description")!=null && job.get("Description").equals("")) &&  (job.get("Value")!=null && !job.get("Value").equals(""))){
							log.debug("inside if RRSPs2");
							if(job.isNull("Ownership")){
							String discription="RRSPS description not filled";
							String value=(String)job.get("Value");
							//String ownership=(String)job.get("Ownership");

							aseetsApplicant=new CreateApplicant();
							log.debug("Applicant Assets to be created .........");

							int j=10;

							String str1 = Integer.toString(j);

							aseetsApplicant.createAssetApplicant(applicantId,"RRSPs", discription, value);

							//logic for couchBase



							dataStoreValue.put("CoApplicant-discription RRS/TSFA1",discription);
							dataStoreValue.put("CoApplicant-value RRS/TSFA1", value);
							//dataStoreValue.put("ownership",ownership);
							}else{
								String discription="RRSPs description not filled";
								String value=(String)job.get("Value");
								String ownership=(String)job.get("Ownership");

								aseetsApplicant=new CreateApplicant();
								log.debug("Applicant Assets to be created .........");

								int j=10;

								String str1 = Integer.toString(j);

								aseetsApplicant.createAssetApplicant(applicantId,"RRSPs", discription, value);

								//logic for couchBase


								dataStoreValue= new HashMap();

								dataStoreValue.put("CoApplicant-discription RRS/TSFA1",discription);
								dataStoreValue.put("CoApplicant-value RRS/TSFA1", value);

								dataStoreValue.put("CoApplicant-ownership RRS/TSFA1",ownership);
								
							}

						}else if((job.get("Description")!=null && !job.get("Description").equals("")) &&  (job.get("Value")!=null && job.get("Value").equals(""))){
							log.debug("inside if RRSPs3");


							if(job.isNull("Ownership")){
								String discription=(String)job.get("Description");
								String value="0";
								//String ownership=(String)job.get("Ownership");

								aseetsApplicant=new CreateApplicant();
								log.debug("Applicant Assets to be created .........");

								
								aseetsApplicant.createAssetApplicant(applicantId,"RRSPs", discription, value);

								//logic for couchBase


							

								dataStoreValue.put("CoApplicant-discription RRS/TSFA2",discription);
								dataStoreValue.put("CoApplicant-value RRS/TSFA2", value);
								//dataStoreValue.put("ownership",ownership);
								}else{
									String discription=(String)job.get("Description");
									String value="0";
									String ownership=(String)job.get("Ownership");

									aseetsApplicant=new CreateApplicant();
									log.debug("Applicant Assets to be created .........");

									

									aseetsApplicant.createAssetApplicant(applicantId,"RRSPs", discription, value);

									//logic for couchBase


									dataStoreValue= new HashMap();

									dataStoreValue.put("CoApplicant-discription RRS/TSFA2",discription);
									dataStoreValue.put("CoApplicant-value RRS/TSFA2", value);

									dataStoreValue.put("CoApplicant-ownership RRS/TSFA2",ownership);
									
								}

						}else{
							log.debug("inside if RRSPs4");


							if(job.isNull("Ownership")){
								String discription="RRSPs name not filled";
								String value="0";
								//String ownership=(String)job.get("Ownership");

								aseetsApplicant=new CreateApplicant();
								log.debug("Applicant Assets to be created .........");

								
								aseetsApplicant.createAssetApplicant(applicantId,"RRSPs", discription, value);

								//logic for couchBase



								dataStoreValue.put("CoApplicant-discription RRS/TSFA4",discription);
								dataStoreValue.put("CoApplicant-value RRS/TSFA4", value);
								//dataStoreValue.put("ownership",ownership);
								}else{
									String discription="bank not filled";
									String value="0";
									String ownership=(String)job.get("Ownership");

									aseetsApplicant=new CreateApplicant();
									log.debug("Applicant Assets to be created .........");

									

									aseetsApplicant.createAssetApplicant(applicantId,"RRSPS", discription, value);

									//logic for couchBase


									dataStoreValue= new HashMap();

									dataStoreValue.put("CoApplicant-discription RRS/TSFA4",discription);
									dataStoreValue.put("CoApplicant-tvalue RRS/TSFA4", value);

									dataStoreValue.put("CoApplicant-ownership RRS/TSFA4",ownership);
								
								}

						}




					}
						
						
						
					}//End Of RRSPS
						
					if(selectedValue.trim().equalsIgnoreCase("Vehicle") && assetType!=null){
					log.debug("inside Vehicle");

					//String vehicles=req.getParameter("vehicles"); //vehicles
					//log.debug(vehicles);
					arrayObj = new JSONArray(req.getParameter("vehicles"));

					log.debug("json array length : "+arrayObj.length());
					dataStoreValue.put("AssetType2",selectedValue);
					for (int i=0;i<arrayObj.length();i++){
						log.debug("inside Vehicle for loop");
						JSONObject job=arrayObj.getJSONObject(i);
						if((job.get("Description")!=null && !job.get("Description").equals("")) &&  (job.get("Value")!=null && !job.get("Value").equals(""))){
							log.debug("inside if Vehicle1");
							if(job.isNull("Ownership")){
							
								log.debug("all values are not null and empty");
								
							String discription=(String)job.get("Description");
							String value=(String)job.get("Value");

							//String ownership=(String)job.get("Ownership");
							aseetsApplicant=new CreateApplicant();
							log.debug("Applicant Assets to be created .........");

							aseetsApplicant.createAssetApplicant(applicantId,"Vehicle", discription, value);		
							log.debug("Applicant Assets created for Vehicle withOut Value and OwnerShip");

							//Logic for CouchBase

						

							dataStoreValue.put("CoApplicant-discription Vehicle",discription);
							dataStoreValue.put("CoApplicant-value Vehicle",value);
							//dataStoreValue.put("ownership",ownership);

							}else{
								//ownership is comming as null
								log.debug("all values are not null and empty except ownership");
								String discription=(String)job.get("Description");
								String value=(String)job.get("Value");

								String ownership=(String)job.get("Ownership");
								aseetsApplicant=new CreateApplicant();
								log.debug("Applicant Assets to be created .........");

								aseetsApplicant.createAssetApplicant(applicantId,"Vehicle", discription, value);		
								log.debug("Applicant Assets created for Vehicle withOut Value and OwnerShip");

								//Logic for CouchBase

							

								dataStoreValue.put("CoApplicant-discription Vehicle",discription);
								dataStoreValue.put("CoApplicant-value Vehicle",value);
								dataStoreValue.put("CoApplicant-ownership Vehicle",ownership);


							}



						}else if((job.get("Description")!=null && job.get("Description").equals("")) &&  (job.get("Value")!=null && !job.get("Value").equals(""))){
							log.debug("inside if Vehicle2");
							if(job.isNull("Ownership")){
							String discription="Vehicle description not filled";
							String value=(String)job.get("Value");
							//String ownership=(String)job.get("Ownership");

							aseetsApplicant=new CreateApplicant();
							log.debug("Applicant Assets to be created .........");

							int j=0;

							String str1 = Integer.toString(j);

							aseetsApplicant.createAssetApplicant(applicantId,"Vehicle", discription, value);

							//logic for couchBase


					

							dataStoreValue.put("CoApplicant-discription Vehicle1",discription);
							dataStoreValue.put("CoApplicant-value Vehicle1", value);
							//dataStoreValue.put("ownership",ownership);
							}else{
								String discription="Vehicle description not filled";
								String value=(String)job.get("Value");
								String ownership=(String)job.get("Ownership");

								aseetsApplicant=new CreateApplicant();
								log.debug("Applicant Assets to be created .........");

								int j=0;


								aseetsApplicant.createAssetApplicant(applicantId,"Vehicle", discription, value);

								//logic for couchBase


							

								dataStoreValue.put("CoApplicant-discription Vehicle1",discription);
								dataStoreValue.put("CoApplicant-value Vehicle2", value);

								dataStoreValue.put("CoApplicant-ownership Vehicle2",ownership);
								
							}

						}else if((job.get("Description")!=null && !job.get("Description").equals("")) &&  (job.get("Value")!=null && job.get("Value").equals(""))){
							log.debug("inside if RRSPs3");


							if(job.isNull("Ownership")){
								String discription=(String)job.get("Description");
								String value="0";
								//String ownership=(String)job.get("Ownership");

								aseetsApplicant=new CreateApplicant();
								log.debug("Applicant Assets to be created .........");

								
								aseetsApplicant.createAssetApplicant(applicantId,"Vehicle", discription, value);

								//logic for couchBase


							

								dataStoreValue.put("CoApplicant-discription Vehicle5",discription);
								dataStoreValue.put("CoApplicant-value Vehicle5", value);
								//dataStoreValue.put("ownership",ownership);
								}else{
									String discription=(String)job.get("Description");
									String value="0";
									String ownership=(String)job.get("Ownership");

									aseetsApplicant=new CreateApplicant();
									log.debug("Applicant Assets to be created .........");

									

									aseetsApplicant.createAssetApplicant(applicantId,"Vehicle", discription, value);

									//logic for couchBase


								

									dataStoreValue.put("CoApplicant-discription Vehicle6",discription);
									dataStoreValue.put("CoApplicant-value Vehicle6", value);

									dataStoreValue.put("CoApplicant-ownership Vehicle6",ownership);
									
								}

						}else{
							log.debug("inside if Vehicle4");


							if(job.isNull("Ownership")){
								String discription="Vehicle name not filled";
								String value="0";
								//String ownership=(String)job.get("Ownership");

								aseetsApplicant=new CreateApplicant();
								log.debug("Applicant Assets to be created .........");

								
								aseetsApplicant.createAssetApplicant(applicantId,"Vehicle", discription, value);

								//logic for couchBase



								dataStoreValue.put("CoApplicant-discription Vehicle7",discription);
								dataStoreValue.put("CoApplicant-value Vehicle7", value);
								//dataStoreValue.put("ownership",ownership);
								}else{
									String discription="Vehicle not filled";
									String value="0";
									String ownership=(String)job.get("Ownership");

									aseetsApplicant=new CreateApplicant();
									log.debug("Applicant Assets to be created .........");

									

									aseetsApplicant.createAssetApplicant(applicantId,"Vehicle", discription, value);

									//logic for couchBase


								

									dataStoreValue.put("CoApplicant-discription Vehicle7",discription);
									dataStoreValue.put("CoApplicant-value Vehicle7", value);

									dataStoreValue.put("CoApplicant-ownership Vehicle7",ownership);
									
								}

						}





					}//End of Vehicle Loop


				}if(selectedValue.trim().equalsIgnoreCase("Stocks / Bonds") || assetType!=null&&selectedValue.trim().equalsIgnoreCase("Mutual Funds") && assetType!=null){
					log.debug("inside Stocks");
					dataStoreValue.put("AssetType4",selectedValue);
					if(counter<1){
						counter=counter+1;
					
					//String investments=req.getParameter("investments"); //investments
					//log.debug(investments);
					arrayObj = new JSONArray(req.getParameter("investments"));

					log.debug("json array length : "+arrayObj.length());

					for (int i=0;i<arrayObj.length();i++){
						JSONObject job=arrayObj.getJSONObject(i);

						if((job.get("Description")!=null && !job.get("Description").equals("")) &&  (job.get("Value")!=null && !job.get("Value").equals(""))){
							log.debug("inside if Stocks / Bonds1");
							if(job.isNull("Ownership")){
							
								log.debug("all values are not null and empty");
								
							String discription=(String)job.get("Description")+","+"\t"+"Investments";
							String value=(String)job.get("Value");

							//String ownership=(String)job.get("Ownership");
							aseetsApplicant=new CreateApplicant();
							log.debug("Applicant Assets to be created .........");

							aseetsApplicant.createAssetApplicant(applicantId,"other", discription, value);		
							log.debug("Applicant Assets created for Stocks / Bonds withOut Value and OwnerShip");

							//Logic for CouchBase


							dataStoreValue.put("CoApplicant-discription Stocks / Bonds",discription);
							dataStoreValue.put("CoApplicant-value Stocks / Bonds",value);
							//dataStoreValue.put("ownership",ownership);

							}else{
								//ownership is comming as null
								log.debug("all values are not null and empty except ownership");
								String discription=(String)job.get("Description")+","+"\t"+"Investments";
								String value=(String)job.get("Value");

								String ownership=(String)job.get("Ownership");
								aseetsApplicant=new CreateApplicant();
								log.debug("Applicant Assets to be created .........");

								aseetsApplicant.createAssetApplicant(applicantId,"other", discription, value);		
								log.debug("Applicant Assets created for RRSPs withOut Value and OwnerShip");

								//Logic for CouchBase

							

								dataStoreValue.put("CoApplicant-discription Stocks / Bonds",discription);
								dataStoreValue.put("CoApplicant-value Stocks / Bonds",value);
								dataStoreValue.put("CoApplicant-ownership Stocks / Bonds",ownership);


							}



						}else if((job.get("Description")!=null && job.get("Description").equals("")) &&  (job.get("Value")!=null && !job.get("Value").equals(""))){
							log.debug("inside if Stocks / Bonds2");
							if(job.isNull("Ownership")){
							String discription="Investments description not filled";
							String value=(String)job.get("Value");
							//String ownership=(String)job.get("Ownership");

							aseetsApplicant=new CreateApplicant();
							log.debug("Applicant Assets to be created .........");

							int j=10;

							String str1 = Integer.toString(j);

							aseetsApplicant.createAssetApplicant(applicantId,"other", discription, value);

							//logic for couchBase


						

							dataStoreValue.put("CoApplicant-discription Stocks / Bonds",discription);
							dataStoreValue.put("CoApplicant-value Stocks / Bonds", value);
							//dataStoreValue.put("ownership",ownership);
							}else{
								String discription="Investmants description not filled";
								String value=(String)job.get("Value");
								String ownership=(String)job.get("Ownership");

								aseetsApplicant=new CreateApplicant();
								log.debug("Applicant Assets to be created .........");

								int j=10;

								String str1 = Integer.toString(j);

								aseetsApplicant.createAssetApplicant(applicantId,"other", discription, value);

								//logic for couchBase


							

								dataStoreValue.put("CoApplicant-discription Stocks / Bonds",discription);
								dataStoreValue.put("CoApplicant-value Stocks / Bonds", value);

								dataStoreValue.put("ownership",ownership);
								
							}

						}else if((job.get("Description")!=null && !job.get("Description").equals("")) &&  (job.get("Value")!=null && job.get("Value").equals(""))){
							log.debug("inside if Stocks / Bonds3");


							if(job.isNull("Ownership")){
								String discription=(String)job.get("Description")+","+"\t"+"InvestMents";
								String value="0";
								//String ownership=(String)job.get("Ownership");

								aseetsApplicant=new CreateApplicant();
								log.debug("Applicant Assets to be created .........");

								
								aseetsApplicant.createAssetApplicant(applicantId,"other", discription, value);

								//logic for couchBase


						

								dataStoreValue.put("CoApplicant-discription Stocks / Bonds1",discription);
								dataStoreValue.put("CoApplicant-value Stocks / Bonds1", value);
								//dataStoreValue.put("ownership",ownership);
								}else{
									String discription=(String)job.get("Description")+","+"\t"+"Stocks / Bonds";
									String value="0";
									String ownership=(String)job.get("Ownership");

									aseetsApplicant=new CreateApplicant();
									log.debug("Applicant Assets to be created .........");

									

									aseetsApplicant.createAssetApplicant(applicantId,"other", discription, value);

									//logic for couchBase


								

									dataStoreValue.put("CoApplicant-discription Stocks / Bonds1",discription);
									dataStoreValue.put("CoApplicant-value Stocks / Bonds1", value);

									dataStoreValue.put("CoApplicant-ownership Stocks / Bonds1",ownership);
									
								}

						}else{
							log.debug("inside if Stocks / Bonds BIZ4");


							if(job.isNull("Ownership")){
								String discription="Investments name not filled";
								String value="0";
								//String ownership=(String)job.get("Ownership");

								aseetsApplicant=new CreateApplicant();
								log.debug("Applicant Assets to be created .........");

								
								aseetsApplicant.createAssetApplicant(applicantId,"other", discription, value);

								//logic for couchBase


					

								dataStoreValue.put("CoApplicant-discription Stocks / Bonds2",discription);
								dataStoreValue.put("CoApplicant-value Stocks / Bonds2", value);
								//dataStoreValue.put("ownership",ownership);
								}else{
									String discription="Investment not filled";
									String value="0";
									String ownership=(String)job.get("Ownership");

									aseetsApplicant=new CreateApplicant();
									log.debug("Applicant Assets to be created .........");

									

									aseetsApplicant.createAssetApplicant(applicantId,"other", discription, value);

									//logic for couchBase


								
									dataStoreValue.put("CoApplicant-discription Stocks / Bonds2 ",discription);
									dataStoreValue.put("CoApplicant-value Stocks / Bonds2", value);

									dataStoreValue.put("CoApplicant-ownership Stocks / Bonds2",ownership);
									
								}

						}



					}//End of loop
					
					}
					
				}


				if(selectedValue.trim().equalsIgnoreCase("Property Deposit") && assetType!=null){
					log.debug("inside Property Deposit");

					String propertyDeposit=req.getParameter("investments166"); //vehicles
					String investments=req.getParameter("investments"); //investments
					log.debug(propertyDeposit);
					arrayObj = new JSONArray(req.getParameter("investments166"));
					dataStoreValue.put("AssetType5",selectedValue);
					log.debug("json array length : "+arrayObj.length());

					for (int i=0;i<arrayObj.length();i++){
						JSONObject job=arrayObj.getJSONObject(i);

						if((job.get("Description")!=null && !job.get("Description").equals("")) &&  (job.get("Value")!=null && !job.get("Value").equals(""))){
							log.debug("inside if Property Deposit1");
							if(job.isNull("Ownership")){
							
								log.debug("all values are not null and empty");
								
							String discription=(String)job.get("Description")+","+"\t"+"Property Deposit";
							String value=(String)job.get("Value");

							//String ownership=(String)job.get("Ownership");
							aseetsApplicant=new CreateApplicant();
							log.debug("Applicant Property Deposit to be created .........");

							aseetsApplicant.createAssetApplicant(applicantId,"other", discription, value);		
							log.debug("Applicant Assets created for Property Deposit withOut Value and OwnerShip");

							//Logic for CouchBase

						

							dataStoreValue.put("CoApplicant-discription Property Deposit",discription);
							dataStoreValue.put("CoApplicant-value Property Deposit",value);
							//dataStoreValue.put("ownership",ownership);

							}else{
								//ownership is comming as null
								log.debug("all values are not null and empty except ownership");
								String discription=(String)job.get("Description")+"Property Deposit";
								String value=(String)job.get("Value");

								String ownership=(String)job.get("Ownership");
								aseetsApplicant=new CreateApplicant();
								log.debug("Applicant Assets to be created .........");

								aseetsApplicant.createAssetApplicant(applicantId,"other", discription, value);		
								log.debug("Applicant Assets created for Property Deposit withOut Value and OwnerShip");

								//Logic for CouchBase

							

								dataStoreValue.put("CoApplicant-discription Property Deposit",discription);
								dataStoreValue.put("CoApplicant-value Property Deposit",value);
								dataStoreValue.put("CoApplicant-ownership Property Deposit",ownership);


							}



						}else if((job.get("Description")!=null && job.get("Description").equals("")) &&  (job.get("Value")!=null && !job.get("Value").equals(""))){
							log.debug("inside if Property Deposit2");
							if(job.isNull("Ownership")){
							String discription="Property Deposit description not filled";
							String value=(String)job.get("Value");
							//String ownership=(String)job.get("Ownership");

							aseetsApplicant=new CreateApplicant();
							log.debug("Applicant Assets to be created .........");

							int j=10;

							String str1 = Integer.toString(j);

							aseetsApplicant.createAssetApplicant(applicantId,"other", discription, value);

							//logic for couchBase


					

							dataStoreValue.put("CoApplicant-discription Property Deposit1",discription);
							dataStoreValue.put("CoApplicant-value Property Deposit1", value);
							//dataStoreValue.put("ownership",ownership);
							}else{
								String discription="Property Deposit description not filled";
								String value=(String)job.get("Value");
								String ownership=(String)job.get("Ownership");

								aseetsApplicant=new CreateApplicant();
								log.debug("Applicant Assets to be created .........");

								int j=10;

								String str1 = Integer.toString(j);

								aseetsApplicant.createAssetApplicant(applicantId,"other", discription, value);

								//logic for couchBase



								dataStoreValue.put("CoApplicant-discription Property Deposit1",discription);
								dataStoreValue.put("CoApplicant-value Property Deposit1", value);

								dataStoreValue.put("ownership Property Deposit1",ownership);
								
							}

						}else if((job.get("Description")!=null && !job.get("Description").equals("")) &&  (job.get("Value")!=null && job.get("Value").equals(""))){
							log.debug("inside if Property Deposit3");


							if(job.isNull("Ownership")){
								String discription=(String)job.get("Description")+","+"\t"+"Property Deposit";
								String value="0";
								//String ownership=(String)job.get("Ownership");

								aseetsApplicant=new CreateApplicant();
								log.debug("Applicant Assets Property Deposit to be created .........");

								
								aseetsApplicant.createAssetApplicant(applicantId,"other", discription, value);

								//logic for couchBase



								dataStoreValue.put("CoApplicant-discription Property Deposit2",discription);
								dataStoreValue.put("CoApplicant-value Property Deposit2", value);
								//dataStoreValue.put("ownership",ownership);
								}else{
									String discription=(String)job.get("Description");
									String value="0";
									String ownership=(String)job.get("Ownership");

									aseetsApplicant=new CreateApplicant();
									log.debug("Applicant Assets to be created .........");

									

									aseetsApplicant.createAssetApplicant(applicantId,"other", discription, value);

									//logic for couchBase


								

									dataStoreValue.put("CoApplicant-discription Property Deposit2",discription);
									dataStoreValue.put("CoApplicant-value Property Deposit2", value);

									dataStoreValue.put("CoApplicant-ownership Property Deposit2",ownership);
									
								}

						}else{
							log.debug("inside if Property Deposit4");


							if(job.isNull("Ownership")){
								String discription="Property Deposit name not filled";
								String value="0";
								//String ownership=(String)job.get("Ownership");

								aseetsApplicant=new CreateApplicant();
								log.debug("Applicant Assets Property Deposit to be created .........");

								
								aseetsApplicant.createAssetApplicant(applicantId,"other", discription, value);

								//logic for couchBase


							

								dataStoreValue.put("CoApplicant-discription Property Deposit4",discription);
								dataStoreValue.put("CoApplicant-value Property Deposit4", value);
								//dataStoreValue.put("ownership",ownership);
								}else{
									String discription="Property Deposit not filled";
									String value="0";
									String ownership=(String)job.get("Ownership");

									aseetsApplicant=new CreateApplicant();
									log.debug("Applicant Assets Property Deposit to be created .........");

									

									aseetsApplicant.createAssetApplicant(applicantId,"other", discription, value);

									//logic for couchBase


								

									dataStoreValue.put("CoApplicant-discription Property Deposit5",discription);
									dataStoreValue.put("CoApplicant-value Property Deposit5", value);

									dataStoreValue.put("CoApplicant-ownership Property Deposit5",ownership);
									
								}

						}




					}//End of Loop



				}if(selectedValue.trim().equalsIgnoreCase("Household Goods") && assetType!=null){
					log.debug("Household Goods");

					String householdGoodsPersonal=req.getParameter("howmuch"); //vehicles
					log.debug(householdGoodsPersonal);

					dataStoreValue.put("AssetType6",selectedValue);
					String discription="\t"+"Household Goods";
					String value=householdGoodsPersonal;
					
					aseetsApplicant=new CreateApplicant();
					log.debug("Applicant Assets to be created .........");

					aseetsApplicant.createAssetApplicant(applicantId,"other", discription, value);		
					
				

					dataStoreValue.put("CoApplicant-discriptionHousehold Goods",discription);
					dataStoreValue.put("CoApplicant-value Household Goods",value);
//End of HouseHold Things		
					



				}if(selectedValue.equalsIgnoreCase("Insurance") && assetType!=null){
					log.debug("Insurance");

					String insurance=req.getParameter("insurance"); //bankAccount
					String investments=req.getParameter("investments"); //investments
					log.debug("Applicant Assets to be created.........");


					log.debug(insurance);
					arrayObj = new JSONArray(req.getParameter("insurance"));

					log.debug("json array length : "+arrayObj.length());

					for (int i=0;i<arrayObj.length();i++){
						JSONObject job=arrayObj.getJSONObject(i);

						if((job.get("Description")!=null && !job.get("Description").equals("")) &&  (job.get("Value")!=null && !job.get("Value").equals(""))){
							log.debug("inside if Insurance1");
							if(job.isNull("Ownership")){
							
								log.debug("all values are not null and empty");
								
							String discription=(String)job.get("Description")+","+"\t"+"Insurance";
							String value=(String)job.get("Value");

							//String ownership=(String)job.get("Ownership");
							aseetsApplicant=new CreateApplicant();
							log.debug("Applicant Assets Insurance to be created .........");

							aseetsApplicant.createAssetApplicant(applicantId,"other", discription, value);		
							log.debug("Applicant Assets created for Insurance withOut Value and OwnerShip");

							//Logic for CouchBase

							
							}else{
								//ownership is comming as null
								log.debug("all values are not null and empty except ownership");
								String discription=(String)job.get("Description")+","+"\t"+"Insurance";
								String value=(String)job.get("Value");

								String ownership=(String)job.get("Ownership");
								aseetsApplicant=new CreateApplicant();
								log.debug("Applicant Assets Insurance to be created .........");

								aseetsApplicant.createAssetApplicant(applicantId,"other", discription, value);		
								log.debug("Applicant Assets created for Insurance withOut Value and OwnerShip");

								//Logic for CouchBase

							}



						}else if((job.get("Description")!=null && job.get("Description").equals("")) &&  (job.get("Value")!=null && !job.get("Value").equals(""))){
							log.debug("inside if Insurance2");
							if(job.isNull("Ownership")){
							String discription="Insurance description not filled";
							String value=(String)job.get("Value");
							//String ownership=(String)job.get("Ownership");

							aseetsApplicant=new CreateApplicant();
							log.debug("Applicant Assets Insurance to be created .........");

							int j=10;

							String str1 = Integer.toString(j);

							aseetsApplicant.createAssetApplicant(applicantId,"other", discription, value);

							//logic for couchBase

		//dataStoreValue.put("ownership",ownership);
							}else{
								String discription="Insurance description not filled";
								String value=(String)job.get("Value");
								String ownership=(String)job.get("Ownership");

								aseetsApplicant=new CreateApplicant();
								log.debug("Applicant Assets to be created .........");

								int j=10;

								String str1 = Integer.toString(j);

								aseetsApplicant.createAssetApplicant(applicantId,"other", discription, value);

								//logic for couchBase

		
							}

						}else if((job.get("Description")!=null && !job.get("Description").equals("")) &&  (job.get("Value")!=null && job.get("Value").equals(""))){
							log.debug("inside if RRSPs3");


							if(job.isNull("Ownership")){
								String discription=(String)job.get("Description")+","+"\t"+"Insurance";
								String value="0";
								//String ownership=(String)job.get("Ownership");

								aseetsApplicant=new CreateApplicant();
								log.debug("Applicant Assets to be created .........");

								
								aseetsApplicant.createAssetApplicant(applicantId,"other", discription, value);

								//logic for couchBase


									//dataStoreValue.put("ownership",ownership);
								}else{
									String discription=(String)job.get("Description")+","+"\t"+"Insurance";
									String value="0";
									String ownership=(String)job.get("Ownership");

									aseetsApplicant=new CreateApplicant();
									log.debug("Applicant Assets to be created .........");

									

									aseetsApplicant.createAssetApplicant(applicantId,"other", discription, value);

									//logic for couchBase


										
								}

						}else{
							log.debug("inside if Insurance4");


							if(job.isNull("Ownership")){
								String discription="Insurance name not filled";
								String value="0";
								//String ownership=(String)job.get("Ownership");

								aseetsApplicant=new CreateApplicant();
								log.debug("Applicant Assets to be created .........");

								
								aseetsApplicant.createAssetApplicant(applicantId,"other", discription, value);

								//logic for couchBase

		//dataStoreValue.put("ownership",ownership);
								}else{
									String discription="Insurance not filled";
									String value="0";
									String ownership=(String)job.get("Ownership");

									aseetsApplicant=new CreateApplicant();
									log.debug("Applicant Assets to be created .........");

									

									aseetsApplicant.createAssetApplicant(applicantId,"other", discription, value);

									//logic for couchBase


											
								}

						}





					}//End Of Insurance


				}if(selectedValue.trim().equalsIgnoreCase("Other") && assetType!=null){
					log.debug("Other");

					String otherassets=req.getParameter("otherassets"); //otherassets
					String investments=req.getParameter("investments"); //investments

					dataStoreValue.put("AssetType7",selectedValue);
					log.debug(otherassets);
					arrayObj = new JSONArray(req.getParameter("otherassets"));

					log.debug("json array length : "+arrayObj.length());

					for (int i=0;i<arrayObj.length();i++){
						JSONObject job=arrayObj.getJSONObject(i);

						if((job.get("Description")!=null && !job.get("Description").equals("")) &&  (job.get("Value")!=null && !job.get("Value").equals(""))){
							log.debug("inside if Other1");
							if(job.isNull("Ownership")){
							
								log.debug("all values are not null and empty");
								
							String discription=(String)job.get("Description")+","+"\t"+"Other";
							String value=(String)job.get("Value");

							//String ownership=(String)job.get("Ownership");
							aseetsApplicant=new CreateApplicant();
							log.debug("Applicant Assets to be created .........");

							aseetsApplicant.createAssetApplicant(applicantId,"other", discription, value);		
							log.debug("Applicant Assets created for Other withOut Value and OwnerShip");

							//Logic for CouchBase

					

							dataStoreValue.put("CoApplicant-discription Other",discription);
							dataStoreValue.put("CoApplicant- value Other",value);
							//dataStoreValue.put("ownership",ownership);

							}else{
								//ownership is comming as null
								log.debug("all values are not null and empty except ownership");
								String discription=(String)job.get("Description")+","+"\t"+"Other";
								String value=(String)job.get("Value");

								String ownership=(String)job.get("Ownership");
								aseetsApplicant=new CreateApplicant();
								log.debug("Applicant Assets to be created .........");

								aseetsApplicant.createAssetApplicant(applicantId,"other", discription, value);		
								log.debug("Applicant Assets created for Other withOut Value and OwnerShip");

								//Logic for CouchBase

							

							}



						}else if((job.get("Description")!=null && job.get("Description").equals("")) &&  (job.get("Value")!=null && !job.get("Value").equals(""))){
							log.debug("inside if Other2");
							if(job.isNull("Ownership")){
							String discription="RRSPS description not filled";
							String value=(String)job.get("Value");
							//String ownership=(String)job.get("Ownership");

							aseetsApplicant=new CreateApplicant();
							log.debug("Applicant Assets to be created .........");

							int j=10;

							String str1 = Integer.toString(j);

							aseetsApplicant.createAssetApplicant(applicantId,"other", discription, value);

							//logic for couchBase


									//dataStoreValue.put("ownership",ownership);
							}else{
								String discription="RRSPs description not filled";
								String value=(String)job.get("Value");
								String ownership=(String)job.get("Ownership");

								aseetsApplicant=new CreateApplicant();
								log.debug("Applicant Assets to be created .........");

								int j=10;

								String str1 = Integer.toString(j);

								aseetsApplicant.createAssetApplicant(applicantId,"other", discription, value);

								//logic for couchBase


										
							}

						}else if((job.get("Description")!=null && !job.get("Description").equals("")) &&  (job.get("Value")!=null && job.get("Value").equals(""))){
							log.debug("inside if Other3");


							if(job.isNull("Ownership")){
								String discription=(String)job.get("Description")+","+"\t"+"Other";
								String value="0";
								//String ownership=(String)job.get("Ownership");

								aseetsApplicant=new CreateApplicant();
								log.debug("Applicant Assets to be created .........");

								
								aseetsApplicant.createAssetApplicant(applicantId,"other", discription, value);

								//logic for couchBase

			}else{
									String discription=(String)job.get("Description")+","+"\t"+"Other";
									String value="0";
									String ownership=(String)job.get("Ownership");

									aseetsApplicant=new CreateApplicant();
									log.debug("Applicant Assets to be created .........");

									

									aseetsApplicant.createAssetApplicant(applicantId,"other", discription, value);

									//logic for couchBase
			
								}

						}else{
							log.debug("inside if Other4");


							if(job.isNull("Ownership")){
								String discription="Other name not filled";
								String value="0";
								//String ownership=(String)job.get("Ownership");

								aseetsApplicant=new CreateApplicant();
								log.debug("Applicant Assets to be created .........");

								
								aseetsApplicant.createAssetApplicant(applicantId,"other", discription, value);

								//logic for couchBase

		}else{
									String discription="Other not filled";
									String value="0";
									String ownership=(String)job.get("Ownership");

									aseetsApplicant=new CreateApplicant();
									log.debug("Applicant Assets to be created .........");

									

									aseetsApplicant.createAssetApplicant(applicantId,"other", discription, value);

									//logic for couchBase

		
								}

						}



						


					}//End of LOOP
			}
			}
			String otherApplicant=req.getParameter("isthere");
			
		log.debug(" second  co_applicant exsist "+otherApplicant);
		
		
		String applicantName="";
		String applicantLastName="";
		String applicantOneEmail="";
		String applicantTwoEmail="";
		String applicantThreeEmail="";
		int applicantId1=0;
		String coApplicantName="";
		String referrerEmail="";
		String applicantTwoFirstName="";
		String applicantTwoLastName="";
		String coApplicantThreeEmail="";
		coApplicantThreeEmail=req.getParameter("pleaseprovide");
		
		try{
			// referrerEmail=(String)ses.getAttribute("referralEmail");
			 applicantName=(String)ses.getAttribute("applicantFirstName");
			applicantLastName=(String)ses.getAttribute("applicantLasttName");
			 applicantOneEmail=(String)ses.getAttribute("applicantEmail");
			 applicantId1=Integer.parseInt((String)ses.getAttribute("applicantID"));
			 }catch(Exception  e){
			 }
			 try{
			 applicantTwoFirstName=(String)ses.getAttribute("co_applicantFirstName");
			 applicantTwoLastName=(String)ses.getAttribute("co_applicantLastName");
			 applicantTwoEmail=(String)ses.getAttribute("co_applicantEmail");
			//applicantId2=Integer.parseInt((String)ses.getAttribute("applicantId2"));
			 }catch(Exception e){
			  
			 }
			 //to attcahed to lead 
			 String applicantID2=(String)ses.getAttribute("applicantId2");
				req.setAttribute("uniid",uniid);
				int leadId=0;
				
				try{
					leadId=Integer.parseInt((String)ses.getAttribute("crmLeadId"));
				}catch(Exception e){
					
				}
				
				int applicantIDInt=0;
			int	applicantID2Int=0;
				try{
			applicantIDInt = Integer.parseInt(applicantID2);
				
				}catch(Exception e){
					
				}
			 CreateApplicant createApplicant=new CreateApplicant();
			 createApplicant.assignOppertunity(applicantIDInt, 0,0,0,0,0,0,0, leadId);
			 dataStoreValue.put("CoApplicant-otherApplicant",otherApplicant);
				dataStoreValue.put("CoApplicant-subForm4",subForm);
				String applicantIDone=(String) ses
						.getAttribute("applicantID");
				CouchBaseOperation	 storeData=new CouchBaseOperation();
			storeData.appendDataInCouchBase("Applicant_"+applicantIDone, dataStoreValue);
				log.debug("CouchBase data created"); 
		
		
			 
             			if(otherApplicant.equalsIgnoreCase("Yes") && otherApplicant!=null){
             				
             			MailTemplateForThirdCoApplicant.MailTemplateForThirdCoApplicantMethod(applicantOneEmail, applicantName, applicantTwoFirstName,applicantTwoEmail, coApplicantThreeEmail, applicantId1, "hr.applicant");
             				
             			}//}else{
             			req.setAttribute("uniid",uniid);
             				
             				ses.setAttribute("form1uniqueId", uniid);
             	     		//req.setAttribute("applicantId",applicantId);
             	     		req.getRequestDispatcher("MortgageApplication8.jsp").include(req, res);
             	     		
			}     		
		
		}catch(Exception e){
			log.error("error in service : "+e);
		}
}else{
	
	req.setAttribute("message", " We are sorry, but it seems the security and reliability of your internet connection may have been weakened.  To protect your identity and the security of your information, can you please submit this application again");
	req.getRequestDispatcher("MortgageApplicationSucess.jsp").forward(req, res);
	
			
		}
		
	}
	

}