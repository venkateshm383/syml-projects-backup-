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
import com.syml.openerp.CheckReferalResource;
import com.syml.openerp.CreateApplicant;
import com.syml.openerp.CreateLead;
public class MortgageForm5Servlet extends HttpServlet{
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		Logger log = LoggerFactory.getLogger(MortgageForm5Servlet.class);
		SymlConstant sc=new SymlConstant();
		//String uniId=req.getParameter("uniid");
		String formType ="Mortgage Application";
		String subForm ="Mortgage Application 5";
		String uniid=req.getParameter("uniid");

		HttpSession ses=req.getSession(true);
		String form1UniqueId=(String)ses.getAttribute("form1uniqueId");
		String applicantId=(String) ses.getAttribute("applicantID");

		if(uniid.equals(form1UniqueId)){

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		HashMap dataStoreValue= new HashMap();

		//get current date time with Calendar()
		Calendar cal = Calendar.getInstance();
		String currentDateTime=(dateFormat.format(cal.getTime()));

		//get ip of latest form sumitted
		GetMyIP myip=new GetMyIP();
		//	String ip=myip.myIp(sc.getMortgageForm5Id());


		log.debug("old unique id is  "+uniid);
		try{
			log.debug("inside service method");

			String assetType=req.getParameter("clickto159");  //assetType
			
			if(assetType==null|| assetType.isEmpty()){
				assetType="";
		
				dataStoreValue.put("Applicant-SubForm-5",subForm);




				//CouchBase
			String otherApplicant=req.getParameter("arethere157");
				dataStoreValue.put("Applicant-otherApplicant",otherApplicant);
				
				CouchBaseOperation append=new CouchBaseOperation();
				append.appendDataInCouchBase("Applicant_"+applicantId, dataStoreValue);
				
				HttpSession sess=req.getSession(true);
				if(otherApplicant.equalsIgnoreCase("Yes") && otherApplicant!=null){

					req.setAttribute("uniid",uniid);

					sess.setAttribute("otherApplicant", otherApplicant);
					//req.setAttribute("applicantId",applicantId);otherApplicant
					req.getRequestDispatcher("MortgageApplicationCo1b.jsp").include(req, res);
					//res.sendRedirect("http://form.jotform.us/form/50504057882152");

				}else{
					//res.sendRedirect("http://form.jotformpro.com/form/50394286934969?unniid="+uniId+"&applicantId="+applicantId);

					   req.setAttribute("uniid",uniid);
					//req.setAttribute("applicantId",applicantId);
					req.getRequestDispatcher("MortgageApplication8.jsp").include(req, res);
				}

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
					dataStoreValue.put("Applicant-AssettypeBank Account",selectedValue);
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


								dataStoreValue.put("Applicant-discription_ Bank1",discription);
								dataStoreValue.put("Applicant-value_ Bank1",value);

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


								dataStoreValue.put("Applicant-discription_Bank",discription);
								dataStoreValue.put("Applicant-value_bank",value);
								dataStoreValue.put("Applicant-ownership_bank",ownership);


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



								dataStoreValue.put("Applicant-discription_Bank2",discription);
								dataStoreValue.put("Applicant-value_Bank2", value);
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



								dataStoreValue.put("Applicant-discription_bank description not filled",discription);
								dataStoreValue.put("Applicant-value_bank description not filled", value);

								dataStoreValue.put("Applicant-ownership_bank description not filled",ownership);

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



								dataStoreValue.put("Applicant-discription_Bank3",discription);
								dataStoreValue.put("Applicant-value_Bank3", value);
							}else{
								String discription=(String)job.get("Description")+","+"\t"+"Bank Acct";
								String value="0";
								String ownership=(String)job.get("Ownership");

								aseetsApplicant=new CreateApplicant();
								log.debug("Applicant Assets to be created .........");



								aseetsApplicant.createAssetApplicant(applicantId,"other", discription, value);

								//logic for couchBase



								dataStoreValue.put("Applicant-discription_Bank",discription);
								dataStoreValue.put("Applicant-value_Bank", value);

								dataStoreValue.put("Applicant-ownership_Bank",ownership);

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


								dataStoreValue= new HashMap();

								dataStoreValue.put("Applicant-discription_bank name not filled",discription);
								dataStoreValue.put("Applicant-value_bank name not filled", value);
								//dataStoreValue.put("ownership",ownership);
							}else{
								String discription="bank not filled";
								String value="0";
								String ownership=(String)job.get("Ownership");

								aseetsApplicant=new CreateApplicant();
								log.debug("Applicant Assets to be created .........");



								aseetsApplicant.createAssetApplicant(applicantId,"other", discription, value);

								//logic for couchBase


							

								dataStoreValue.put("Applicant-discription_bank4",discription);
								dataStoreValue.put("Applicant-value_bank4", value);

								dataStoreValue.put("Applicant-ownership_bank4",ownership);

							}

						}
}




				}
				
				if(selectedValue.trim().equalsIgnoreCase("RRSP / TSFA") && selectedValue!=null){
					log.debug("inside RRSP");
					arrayObj = new JSONArray(req.getParameter("rrspsand"));
					dataStoreValue.put("Applicant-AssetTypeRRSP / TSFA",selectedValue);
					log.debug("arrayobjjson : "+arrayObj.toString());
					log.debug("json array length : "+arrayObj.length());
 

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


							dataStoreValue.put("Applicant-discription_RRSP",discription);
							dataStoreValue.put("Applicant-value_RRSP",value);
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

								

								dataStoreValue.put("Applicant-discription_ownership",discription);
								dataStoreValue.put("Applicant-value_ownership",value);
								dataStoreValue.put("Applicant-ownership_ownership",ownership);


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



							dataStoreValue.put("Applicant-discription_RRSPs2_empty",discription);
							dataStoreValue.put("Applicant-value_RRSPs2_empty", value);
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


							

								dataStoreValue.put("Applicant-discription_RRSPs description not filled",discription);
								dataStoreValue.put("Applicant-value_RRSPs description not filled", value);

								dataStoreValue.put("Applicant-ownership_RRSPs description not filled",ownership);
								
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



								dataStoreValue.put("Applicant-discription_RRSPs3",discription);
								dataStoreValue.put("Applicant-value_RRSPs3", value);
								//dataStoreValue.put("ownership",ownership);
								}else{
									String discription=(String)job.get("Description");
									String value="0";
									String ownership=(String)job.get("Ownership");

									aseetsApplicant=new CreateApplicant();
									log.debug("Applicant Assets to be created .........");

									

									aseetsApplicant.createAssetApplicant(applicantId,"RRSPs", discription, value);

									//logic for couchBase



									dataStoreValue.put("Applicant-discription",discription);

									dataStoreValue.put("Applicant-ownership",ownership);
									
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


							

								dataStoreValue.put("Applicant-value", value);
								//dataStoreValue.put("ownership",ownership);
								}else{
									String discription="bank not filled";
									String value="0";
									String ownership=(String)job.get("Ownership");

									aseetsApplicant=new CreateApplicant();
									log.debug("Applicant Assets to be created .........");

									

									aseetsApplicant.createAssetApplicant(applicantId,"RRSPS", discription, value);

									//logic for couchBase


								

									dataStoreValue.put("Applicant-value_RRSPs4", value);

									dataStoreValue.put("Applicant-ownership_RRSPs4",ownership);
									
								}

						}




					}
						
						
						
					}//End Of RRSPS
						
					if(selectedValue.trim().equalsIgnoreCase("Vehicle") && assetType!=null){
					log.debug("inside Vehicle");
					dataStoreValue.put("Applicant-AssetType",selectedValue);
					//String vehicles=req.getParameter("vehicles"); //vehicles
					//log.debug(vehicles);
					arrayObj = new JSONArray(req.getParameter("vehicles"));

					log.debug("json array length : "+arrayObj.length());

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


							dataStoreValue.put("Applicant-discription_Vehicle",discription);
							dataStoreValue.put("Applicant-value_Vehicle",value);
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


								dataStoreValue.put("Applicant-discription_Vehicle1",discription);
								dataStoreValue.put("Applicant-value_Vehicle1",value);
								dataStoreValue.put("Applicant-ownership_Vehicle1",ownership);


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


			

							dataStoreValue.put("Applicant-value_Vehiclenotfilled", value);
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



								dataStoreValue.put("Applicant-value_VehiclenotFiiled ", value);

								dataStoreValue.put("Applicant-ownership_Vehiclenot filled",ownership);
								
							}

						}else if((job.get("Description")!=null && !job.get("Description").equals("")) &&  (job.get("Value")!=null && job.get("Value").equals(""))){
							log.debug("inside if Vehicle3");


							if(job.isNull("Ownership")){
								String discription=(String)job.get("Description");
								String value="0";
								//String ownership=(String)job.get("Ownership");

								aseetsApplicant=new CreateApplicant();
								log.debug("Applicant Assets to be created .........");

								
								aseetsApplicant.createAssetApplicant(applicantId,"Vehicle", discription, value);

								//logic for couchBase


					

								dataStoreValue.put("Applicant-discription_vehicles",discription);
								dataStoreValue.put("Applicant-value_vehicles", value);
								}else{
									String discription=(String)job.get("Description");
									String value="0";
									String ownership=(String)job.get("Ownership");

									aseetsApplicant=new CreateApplicant();
									log.debug("Applicant Assets to be created .........");

									

									aseetsApplicant.createAssetApplicant(applicantId,"Vehicle", discription, value);

									//logic for couchBase



									dataStoreValue.put("Applicant-discription",discription);
									dataStoreValue.put("Applicant-value", value);

									dataStoreValue.put("Applicant-ownership",ownership);
									
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


								

								}else{
									String discription="Vehicle not filled";
									String value="0";
									String ownership=(String)job.get("Ownership");

									aseetsApplicant=new CreateApplicant();
									log.debug("Applicant Assets to be created .........");

									

									aseetsApplicant.createAssetApplicant(applicantId,"Vehicle", discription, value);

									//logic for couchBase




									dataStoreValue.put("Applicant-ownership_vehicle_with",ownership);
									
								}

						}



					


					}//End of Vehicle Loop


				}if(selectedValue.trim().equalsIgnoreCase("Stocks / Bonds") || assetType!=null&&selectedValue.trim().equalsIgnoreCase("Mutual Funds") && assetType!=null){
					log.debug("inside Stocks");

					if(counter<1){
						counter=counter+1;
					
					//String investments=req.getParameter("investments"); //investments
					//log.debug(investments);
					arrayObj = new JSONArray(req.getParameter("investments"));

					log.debug("json array length : "+arrayObj.length());
					dataStoreValue.put("Applicant-AssetType",selectedValue);
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

						
							dataStoreValue.put("Applicant-discription_Stocks",discription);
							dataStoreValue.put("Applicant-value_Stocks",value);
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


								dataStoreValue.put("Applicant-discription_withoutOwner",discription);
								dataStoreValue.put("Applicant-value__withoutOwner",value);


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


						

							dataStoreValue.put("Applicant-value_withoutInevestment", value);
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



								dataStoreValue.put("Applicant-value_not filledInvestment", value);

								dataStoreValue.put("Applicant-ownership_not filledInvestment",ownership);
								
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


								

								dataStoreValue.put("Applicant-discription_Bonds3",discription);
								dataStoreValue.put("Applicant-value_Bonds3", value);
								//dataStoreValue.put("ownership",ownership);
								}else{
									String discription=(String)job.get("Description")+","+"\t"+"Stocks / Bonds";
									String value="0";
									String ownership=(String)job.get("Ownership");

									aseetsApplicant=new CreateApplicant();
									log.debug("Applicant Assets to be created .........");

									

									aseetsApplicant.createAssetApplicant(applicantId,"other", discription, value);

									//logic for couchBase


									

									dataStoreValue.put("Applicant-discription_withPutValue",discription);

									dataStoreValue.put("Applicant-ownership_withPutValue",ownership);
									
								}

						}else{
							log.debug("inside if Stocks / Bonds");


							if(job.isNull("Ownership")){
								String discription="Investments name not filled";
								String value="0";
								//String ownership=(String)job.get("Ownership");

								aseetsApplicant=new CreateApplicant();
								log.debug("Applicant Assets to be created .........");

								
								aseetsApplicant.createAssetApplicant(applicantId,"other", discription, value);

								//logic for couchBase



								dataStoreValue.put("Applicant-value_without_investment", value);
								//dataStoreValue.put("ownership",ownership);
								}else{
									String discription="Investment not filled";
									String value="0";
									String ownership=(String)job.get("Ownership");

									aseetsApplicant=new CreateApplicant();
									log.debug("Applicant Assets to be created .........");

									

									aseetsApplicant.createAssetApplicant(applicantId,"other", discription, value);

									//logic for couchBase


								

									dataStoreValue.put("Applicant-value_value_without_investmentElse", value);

									dataStoreValue.put("Applicant-ownership_value_without_investmentElse",ownership);
									
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

					log.debug("json array length : "+arrayObj.length());
					dataStoreValue.put("Applicant-AssetType",selectedValue);

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


							dataStoreValue.put("Applicant-discription_Property",discription);
							dataStoreValue.put("Applicant-value_Property",value);
							//dataStoreValue.put("ownership",ownership);

							}else{
								//ownership is comming as null
								log.debug("all values are not null and empty except ownership");
								String discription=(String)job.get("Description")+","+"\t"+"Property Deposit";
								String value=(String)job.get("Value");

								String ownership=(String)job.get("Ownership");
								aseetsApplicant=new CreateApplicant();
								log.debug("Applicant Assets to be created .........");

								aseetsApplicant.createAssetApplicant(applicantId,"other", discription, value);		
								log.debug("Applicant Assets created for Property Deposit withOut Value and OwnerShip");

								//Logic for CouchBase

							

								dataStoreValue.put("Applicant-discription_Property Deposit",discription);
								dataStoreValue.put("Applicant-value_Property Deposit",value);


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



							dataStoreValue.put("Applicant-value_Property DepositValue", value);
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



								dataStoreValue.put("Applicant-value_Property DepositNot", value);

								dataStoreValue.put("Applicant-ownership_Property Deposit_not",ownership);
								
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


								

								dataStoreValue.put("Applicant-discription_Property Deposit3",discription);
								dataStoreValue.put("Applicant-value_Property Deposit3", value);
								//dataStoreValue.put("ownership",ownership);
								}else{
									String discription=(String)job.get("Description");
									String value="0";
									String ownership=(String)job.get("Ownership");

									aseetsApplicant=new CreateApplicant();
									log.debug("Applicant Assets to be created .........");

									

									aseetsApplicant.createAssetApplicant(applicantId,"other", discription, value);

									//logic for couchBase



									dataStoreValue.put("Applicant-discription_Property Deposit3Not",discription);

									dataStoreValue.put("Applicant-ownership_Property Deposit3Not",ownership);
									
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


							

								dataStoreValue.put("Applicant-discription_Deposit4",discription);
								//dataStoreValue.put("ownership",ownership);
								}else{
									String discription="Property Deposit not filled";
									String value="0";
									String ownership=(String)job.get("Ownership");

									aseetsApplicant=new CreateApplicant();
									log.debug("Applicant Assets Property Deposit to be created .........");

									

									aseetsApplicant.createAssetApplicant(applicantId,"other", discription, value);

									//logic for couchBase



									dataStoreValue.put("Applicant-value_Deposit4Not", value);

									dataStoreValue.put("Applicant-ownership_Deposit4not",ownership);
									
								}

						}



					



					}//End of Loop



				}if(selectedValue.trim().equalsIgnoreCase("Household Goods") && assetType!=null){
					log.debug("Household Goods");
					dataStoreValue.put("Applicant-assetType",selectedValue);
					String householdGoodsPersonal=req.getParameter("howmuch"); //vehicles
					log.debug(householdGoodsPersonal);

					
					String discription="\t"+"Household Goods";
					String value=householdGoodsPersonal;
					
					aseetsApplicant=new CreateApplicant();
					log.debug("Applicant Assets to be created .........");

					aseetsApplicant.createAssetApplicant(applicantId,"other", discription, value);		
					
				
					dataStoreValue.put("Applicant-discription_Household Goods",discription);
					dataStoreValue.put("Applicant-value_Household Goods",value);
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

						

							dataStoreValue.put("Applicant-discription_Insurance",discription);
							dataStoreValue.put("Applicant-value_Insurance",value);
							//dataStoreValue.put("ownership",ownership);

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

						

								dataStoreValue.put("Applicant-discription_InsuranceOwner",discription);
								dataStoreValue.put("Applicant-value_InsuranceOwner",value);
								dataStoreValue.put("Applicant-ownership_InsuranceOwner",ownership);


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



							dataStoreValue.put("value_Description", value);
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



								dataStoreValue.put("Applicant-value_DescriptionNot", value);

								dataStoreValue.put("Applicant-ownership_DescriptionNot",ownership);
								
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



								dataStoreValue.put("discription_DescriptionNotFiled",discription);
								//dataStoreValue.put("ownership",ownership);
								}else{
									String discription=(String)job.get("Description")+","+"\t"+"Insurance";
									String value="0";
									String ownership=(String)job.get("Ownership");

									aseetsApplicant=new CreateApplicant();
									log.debug("Applicant Assets to be created .........");

									

									aseetsApplicant.createAssetApplicant(applicantId,"other", discription, value);

									//logic for couchBase


								

									dataStoreValue.put("Applicant-discription_valueNot",discription);

									dataStoreValue.put("Applicant-ownership_valueNot",ownership);
									
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



								dataStoreValue.put("Applicant-discription_Insurance4",discription);
								dataStoreValue.put("Applicant-value_Insurance4", value);
								//dataStoreValue.put("ownership",ownership);
								}else{
									String discription="Insurance not filled";
									String value="0";
									String ownership=(String)job.get("Ownership");

									aseetsApplicant=new CreateApplicant();
									log.debug("Applicant Assets to be created .........");

									

									aseetsApplicant.createAssetApplicant(applicantId,"other", discription, value);

									//logic for couchBase


								

									dataStoreValue.put("Applicant-value_Insurance4y", value);

									dataStoreValue.put("Applicant-ownership_Insurance4y",ownership);
									
								}

						}



						


					}//End Of Insurance


				}if(selectedValue.trim().equalsIgnoreCase("Other") && assetType!=null){
					log.debug("Other");

					String otherassets=req.getParameter("otherassets"); //otherassets
					String investments=req.getParameter("investments"); //investments


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


							dataStoreValue.put("Applicant-discription_Other",discription);
							dataStoreValue.put("Applicant-value_Other",value);
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


								dataStoreValue.put("Applicant-discription_OtherOwneship",discription);
								dataStoreValue.put("Applicant-value_OtherOwner",value);
								dataStoreValue.put("Applicant-ownership_OtherShip",ownership);


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


							dataStoreValue= new HashMap();

							dataStoreValue.put("value_notRSSP", value);
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



								dataStoreValue.put("Applicant-discription_notDeposit",discription);
								dataStoreValue.put("Applicant-value_notDepo", value);

								dataStoreValue.put("Applicant-ownership_notDepo",ownership);
								
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



								dataStoreValue.put("discription_otherNos",discription);
								//dataStoreValue.put("ownership",ownership);
								}else{
									String discription=(String)job.get("Description")+","+"\t"+"Other";
									String value="0";
									String ownership=(String)job.get("Ownership");

									aseetsApplicant=new CreateApplicant();
									log.debug("Applicant Assets to be created .........");

									

									aseetsApplicant.createAssetApplicant(applicantId,"other", discription, value);

									//logic for couchBase


								

									dataStoreValue.put("Applicant-discription",discription);

									dataStoreValue.put("Applicant-ownership",ownership);
									
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



								dataStoreValue.put("discription_Other4",discription);
								//dataStoreValue.put("ownership",ownership);
								}else{
									String discription="Other not filled";
									String value="0";
									String ownership=(String)job.get("Ownership");

									aseetsApplicant=new CreateApplicant();
									log.debug("Applicant Assets to be created .........");

									

									aseetsApplicant.createAssetApplicant(applicantId,"other", discription, value);

									//logic for couchBase


									dataStoreValue= new HashMap();

									dataStoreValue.put("discription_Other4Nio",discription);

									dataStoreValue.put("ownership_Other4Ni",ownership);
									
								}

						}







					}//End of LOOP
			}
			}


			dataStoreValue.put("Applicant-SubForm-5",subForm);



			String otherApplicant=req.getParameter("arethere157");

			//CouchBase
			dataStoreValue.put("Applicant-otherApplicant",otherApplicant);
			CouchBaseOperation append=new CouchBaseOperation();
			append.appendDataInCouchBase("Applicant_"+applicantId, dataStoreValue);

			HttpSession sess=req.getSession(true);
			if(otherApplicant.equalsIgnoreCase("Yes") && otherApplicant!=null){

				req.setAttribute("uniid",uniid);

				sess.setAttribute("otherApplicant", otherApplicant);
				//req.setAttribute("applicantId",applicantId);otherApplicant
				req.getRequestDispatcher("MortgageApplicationCo1b.jsp").include(req, res);
				//res.sendRedirect("http://form.jotform.us/form/50504057882152");

			}else{
				//res.sendRedirect("http://form.jotformpro.com/form/50394286934969?unniid="+uniId+"&applicantId="+applicantId);

				   req.setAttribute("uniid",uniid);
				//req.setAttribute("applicantId",applicantId);
				req.getRequestDispatcher("MortgageApplication8.jsp").include(req, res);
			}

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