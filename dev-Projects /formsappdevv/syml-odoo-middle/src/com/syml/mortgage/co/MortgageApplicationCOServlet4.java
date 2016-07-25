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

public class MortgageApplicationCOServlet4 extends HttpServlet {
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		Logger log = LoggerFactory
				.getLogger(MortgageApplicationCOServlet4.class);
		SymlConstant sc=new SymlConstant();
		HashMap dataStoreValue= new HashMap();


		try{
			log.info("inside service method");

			String incomeType=req.getParameter("pleaseselect");  //incomeType
			
			String uniid=req.getParameter("uniid");

			String formType ="Mortgage Application";
			String subForm ="Mortgage Co-Application 4";

			log.debug("old unique id is  "+uniid);
			HttpSession ses=req.getSession(true);
			String form1UniqueId=(String)ses.getAttribute("form1uniqueId");
			String applicantId=(String) ses.getAttribute("applicantId2");
			if(uniid.equals(form1UniqueId)){


			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

			//get current date time with Calendar()
			Calendar cal = Calendar.getInstance();
			String currentDateTime=(dateFormat.format(cal.getTime()));

			//get ip of latest form sumitted
			GetMyIP myip=new GetMyIP();
			String ip=myip.myIp(sc.getMortgageForm4Id());

			CreateApplicant applicantEmployee=new CreateApplicant();


			Incomes income=null;
			ArrayList<Incomes> incomeListForOdoo = new ArrayList<Incomes>(); 

			log.debug("after incomeoddlist");
			String[] stringIncome=incomeType.split("\n");


			int strIncomeLen=stringIncome.length;

			List<String> selectedValues=new ArrayList<String>();
			for(int i=0; i<=strIncomeLen-1;i++ ){
				selectedValues.add(stringIncome[i].trim());
			}




			log.debug("before for");
			for(String selectedValue : selectedValues){
				log.debug("select value : "+selectedValue);
				if (selectedValue.equalsIgnoreCase("Pension")) {
					income = new Incomes();
					log.debug("inside pension");
					income.Type = "Pension"; 
					income.Months=null;
					income.Business = "Pension"; 
					income.JobTitle = "Pension";
					income.Supplementary =true;
					income.Months="0";
					
					int monthsInteger = Integer.parseInt("0");

					applicantEmployee.createIncomeApplicant(applicantId, 6, "Pension", "Pension", monthsInteger, true);

					log.debug("created Applicant Income for Pension");
					
					
					
					dataStoreValue.put("CoApplicant-Pensiontype","Pension");
					dataStoreValue.put("CoApplicant-Pensionbussiness","Pension");
					dataStoreValue.put("CoApplicant-PensionjobTitle","Pension");
					dataStoreValue.put("CoApplicant-Pensionsupplementry",true);
					dataStoreValue.put("CoApplicant-pensionmonths","0");

							Iterator iterate= incomeListForOdoo.iterator();

					// Remaining fields Stay Blank
					incomeListForOdoo.add(income);
				}if(selectedValue.equalsIgnoreCase("Vehicle Allowance")){
					log.debug("inside vehical allownace");
					income = new Incomes();
					income.Type = "Vehicle Allowance"; 
					income.Business = "Vehicle Allowance"; 
					income.JobTitle = "Vehicle Allowance";
					income.Supplementary =true;
					income.Months="0";
					
					// put log here

					log.debug("Going to create Applicantbased Incomes for Vehicle allowance");


					int monthsInteger = Integer.parseInt("0");
					//Bussiness created with MonthSelection

					applicantEmployee.createIncomeApplicant(applicantId, 12, "Vehicle Allowance","Vehicle Allowance", monthsInteger, true);
					log.debug("created with the VehicleAllowance");
					
				
					
					dataStoreValue.put("CoApplicant-VehicleAllowancetype2","Vehicle Allowance");
					dataStoreValue.put("CoApplicant-VehicleAllowancebussiness2","Vehicle Allowance");
					dataStoreValue.put("CoApplicant-VehicleAllowancejobTitle2","Vehicle Allowance");
					dataStoreValue.put("CoApplicant-VehicleAllowancesupplementry2",true);
					dataStoreValue.put("CoApplicant-VehicleAllowancemonths2","0");

							
					
					
					// Remaining fields Stay Blank 
					incomeListForOdoo.add(income);
					

				}if(selectedValue.equalsIgnoreCase("Interest")){
					log.debug("inside interst");
					income = new Incomes();
					income.Type = "Interest"; 
					income.Business = "Interest"; 
					income.JobTitle = "Interest";
					income.Supplementary = true;
					income.Months="0";
					
					log.debug("Going to create Applicantbased Incomes for Vehicle allowance");


					int monthsInteger = Integer.parseInt("0");
					//Bussiness created with MonthSelection

					applicantEmployee.createIncomeApplicant(applicantId, 5, "Interest", "Interest", monthsInteger,true);
					log.debug("created with the Interest");
					
					
					//CouchBase
					
				
					
					dataStoreValue.put("CoApplicant-Interesttype3","Interest");
					dataStoreValue.put("CoApplicant-Interestbussiness3","Interest");
					dataStoreValue.put("CoApplicant-InterestjobTitle3","Interest");
					dataStoreValue.put("CoApplicant-Interestsupplementry3",true);
					dataStoreValue.put("CoApplicant-Interestmonths3","0");
	
					
					// Remaining fields Stay Blank 
					incomeListForOdoo.add(income);



				}if(selectedValue.equalsIgnoreCase("Child Tax Credit")){
					log.debug("inside child tax crddit");
					income = new Incomes();
					income.Type = "Child Tax Credit"; 
					income.Business = "Child Tax Credit"; 
					income.JobTitle = "Child Tax Credit";
					income.Supplementary =  true;
					income.Months="0";

					// Remaining fields Stay Blank 
					incomeListForOdoo.add(income);
					// put log here

					log.debug("Going to create Applicantbased Incomes for Vehicle allowance");


					int monthsInteger = Integer.parseInt("0");
					//Bussiness created with MonthSelection

					applicantEmployee.createIncomeApplicant(applicantId, 10, "Child Tax Credit","Child Tax Credit", monthsInteger, true);
					
					
					
					log.debug("created with the Child Tax Credit");
//CochBase
					
				
					
					dataStoreValue.put("CoApplicant-Child Tax Credittype4","Child Tax Credit");
					dataStoreValue.put("CoApplicant-Child Tax Creditbussiness4","Child Tax Credit");
					dataStoreValue.put("CoApplicant-Child Tax CreditjobTitle4","Child Tax Credit");
					dataStoreValue.put("CoApplicant-Child Tax Creditsupplementry4",true);
					dataStoreValue.put("CoApplicant-Child Tax Creditmonths4","0");

				
					
					
					


				}if(selectedValue.equalsIgnoreCase("Child Support")){
					log.debug("inside child support");
					income = new Incomes();
					income.Type = "Child Support"; 
					income.Business = "Child Support"; 
					income.JobTitle = "Child Support";
					income.Supplementary = true;
					income.Months="0";
					
					
					log.debug("Going to create Applicantbased Incomes for Cild Support");


					int monthsInteger = Integer.parseInt("0");
					//Bussiness created with MonthSelection

					applicantEmployee.createIncomeApplicant(applicantId, 13, "Child Support","Child Support", monthsInteger,true);
					log.debug("created with the Child support");
					
					//cochBase
					
				
					
					dataStoreValue.put("CoApplicant-Child Supporttype5","Child Support");
					dataStoreValue.put("CoApplicant-Child Supportbussiness5","Child Support");
					dataStoreValue.put("CoApplicant-Child SupportjobTitle5","Child Support");
					dataStoreValue.put("CoApplicant-Child Supportsupplementry5",true);
					dataStoreValue.put("CoApplicant-Child Supportmonths5","0");

				
					// Remaining fields Stay Blank 
					incomeListForOdoo.add(income);
					
				}if(selectedValue.equalsIgnoreCase("Spouse Support")){
					log.debug("inside spouse support");
					income = new Incomes();
					income.Type = "Spouse Support"; 
					income.Business = "Spouse Support"; 
					income.JobTitle = "Spouse Support";
					income.Supplementary = true;
					income.Months="0";

					// Remaining fields Stay Blank 
					incomeListForOdoo.add(income);
					log.debug("Going to create Applicantbased Incomes for Spouse support allowance");


					int monthsInteger = Integer.parseInt("0");
					//Bussiness created with MonthSelection

					applicantEmployee.createIncomeApplicant(applicantId, 13, "Spouse Support", "Spouse Support", monthsInteger, true);
					log.debug("created with the Spouse Support");
					 //cochBase
					
					
					
					dataStoreValue.put("CoApplicant-Spouse Supporttype6","Spouse Support");
					dataStoreValue.put("CoApplicant-Spouse Supportbussiness6","Spouse Support");
					dataStoreValue.put("CoApplicant-Spouse SupportjobTitle6","Spouse Support");
					dataStoreValue.put("CoApplicant-Spouse Supportsupplementry6","Spouse Support");
					dataStoreValue.put("CoApplicant-Spouse Supportmonths6","0");

							
					
					
					



				}if(selectedValue.equalsIgnoreCase("Dividend")){
					log.debug("inside divident");
					income = new Incomes();
					income.Type = "Dividend"; 
					income.Business = "Dividend"; 
					income.JobTitle = "Dividend";
					income.Supplementary = true;
					income.Months="0";
					
					
					log.debug("Going to create Applicantbased Incomes for Divident");


					int monthsInteger = Integer.parseInt("0");
					//Bussiness created with MonthSelection

					applicantEmployee.createIncomeApplicant(applicantId, 13, "Dividend","Dividend", monthsInteger,true);
					log.debug("created with the Divident");
					//CouchBase
					
				
					
					dataStoreValue.put("CoApplicant-Dividendtype7","Dividend");
					dataStoreValue.put("CoApplicant-Dividendbussiness7","Dividend");
					dataStoreValue.put("CoApplicant-DividendjobTitle7","Dividend");
					dataStoreValue.put("CoApplicant-Dividendsupplementry7",true);
					dataStoreValue.put("CoApplicant-Dividendmonths7","0");

					
					// Remaining fields Stay Blank 
					incomeListForOdoo.add(income);

				}


			}

			for(String selectvalue : selectedValues){
				if(selectvalue.equalsIgnoreCase("Employed") && selectvalue!=null){
					log.debug("inside employed");
					income = new Incomes();
					String currentEmployee1=req.getParameter("employer"); // currentEmployee
					String currentPosition1=req.getParameter("position1"); //currentPosition
					String yearWorked1=req.getParameter("empoyedyears"); //yearWorked
					String monthsWorked1=req.getParameter("employedmonths1"); //monthsWorked
					String employeeTotalMonths1=req.getParameter("employedmonthstotal1"); //employeeTotalMonths

					

					dataStoreValue.put("CoApplicant-Employedtype8"," Employed");
					dataStoreValue.put("CoApplicant-Employedbussiness8",currentEmployee1);
					dataStoreValue.put("CoApplicant-EmployedjobTitle8",currentPosition1);
					dataStoreValue.put("CoApplicant-EmployedyearWorked8",yearWorked1);
					dataStoreValue.put("CoApplicant-EmployedmonthsWorked8",monthsWorked1);
					dataStoreValue.put("CoApplicant-EmployedemployeeTotalMonths8",employeeTotalMonths1);
					

					log.debug("currentEmployee1"+currentEmployee1);
					log.debug("currentPosition1"+currentPosition1);
					log.debug("yearWorked1"+yearWorked1);
					log.debug("monthsWorked1"+monthsWorked1);
					
					income.Type="Employed";
					income.Business=currentEmployee1;
					income.JobTitle=currentPosition1;
					income.Supplementary=true;
					income.Months=monthsWorked1;
					incomeListForOdoo.add(income);

					int monthsWorked=0;
					try{
					 monthsWorked = Integer.parseInt(monthsWorked1);
					}catch(Exception e){
						
					}




					int employeeTotal =0;
					try{
					employeeTotal = Integer.parseInt(employeeTotalMonths1);
					}catch(Exception e){
						
					}
					applicantEmployee.createIncomeApplicant(applicantId, 1, currentEmployee1, currentPosition1, employeeTotal, true);


					if(employeeTotal<36 && employeeTotalMonths1!=null){
						log.debug("inside employed leass then 36 ------------>1");

						income = new Incomes();

						String priorEmployee11=req.getParameter("employer138"); // priorEmployee1
						String position11=req.getParameter("position162");//position1


						String year11=req.getParameter("empoyedyears2");//year1
						String months11=req.getParameter("employedmonths2");//months1
						String monthsum11=req.getParameter("employedmonthssum2");//monthsum1
						String monthsTotal11=req.getParameter("employedmonthstotal2");//monthsTotal1


						log.debug("priorEmployee11"+priorEmployee11);
						log.debug("position11"+position11);
						log.debug("year11"+year11);
						log.debug("months11"+months11);
						log.debug("monthsum11"+monthsum11);
						log.debug("monthsTotal11"+monthsTotal11);
					
						dataStoreValue.put("CoApplicant-Employedtype9"," Employed");
						dataStoreValue.put("CoApplicant-Employedbussiness9",priorEmployee11);
						dataStoreValue.put("CoApplicant-EmployedjobTitle9",position11);
						dataStoreValue.put("CoApplicant-EmployedyearWorked9",year11);
						dataStoreValue.put("CoApplicant-EmployedmonthsWorked9",monthsWorked1);
						dataStoreValue.put("CoApplicant-EmployedemployeeTotalMonths9",monthsum11);

						income.Type="Employed";
						income.Business=priorEmployee11;
						income.JobTitle=position11;
						income.Supplementary=false;
						income.Historical=true;
						income.Months=monthsum11;
						incomeListForOdoo.add(income);
						//log.debug("list size : "+incomeListForOdoo.size());
						int monthsWorked11=0;
						try{
							monthsWorked11= Integer.parseInt(monthsum11);
						}catch(Exception e){
							
						}
						
						applicantEmployee.createIncomeApplicant(applicantId, 1, priorEmployee11, position11, monthsWorked11,false, true);

						int employeeTotal1=0;
						try{
							employeeTotal1= Integer.parseInt(monthsTotal11);
						}catch(Exception e){
							
						}

						if(employeeTotal1<36 && monthsTotal11!=null){
							log.debug("inside employed leass then 36 ------------>2");


							String priorEmployee21=req.getParameter("employer3");//priorEmployee2
							String position21=req.getParameter("position163"); // position2
							String year21=req.getParameter("empoyedyears3");//year2
							String months21=req.getParameter("employedmonths3");//months2
							String monthsum21=req.getParameter("employedmonthssum3");//monthsum2
							String monthsTotal21=req.getParameter("employedmonthstotal3");//monthsTotal2
							
							
							dataStoreValue.put("CoApplicant-Employedtype10"," Employed");
							dataStoreValue.put("CoApplicant-Employedbussiness10",priorEmployee21);
							dataStoreValue.put("CoApplicant-EmployedjobTitle10",position21);
							dataStoreValue.put("CoApplicant-EmployedyearWorked10",year21);
							dataStoreValue.put("CoApplicant-EmployedmonthsWorked10",months21);
							dataStoreValue.put("CoApplicant-EmployedemployeeTotalMonths10",monthsum21);
							income.Type="Employed";
							income.Business=priorEmployee21;
							income.JobTitle=position21;
							income.Supplementary=false;
							income.Historical=true;
							income.Months=months21;
							incomeListForOdoo.add(income);
							log.debug("priorEmployee21"+"\t"+priorEmployee21);
							log.debug("position21"+"\t"+position21);
							log.debug("months21"+"\t"+months21);
							log.debug("monthsTotal21"+"\t"+monthsTotal21);
							log.debug("monthsum21"+"\t"+monthsum21);


							
							
int monthsInteger=0;

							
							try{
						 monthsInteger = Integer.parseInt(monthsum21);
							}catch(Exception e){
								
							}
log.debug("afetr int");
							
							applicantEmployee.createIncomeApplicant(applicantId, 1, priorEmployee21, position21, monthsInteger, false,true);
							int employeeTotal2=0;
							try{
							 employeeTotal2=Integer.parseInt(monthsTotal21);
							}catch(Exception e){
								
							}

								log.debug("inside employed leass then 36 ------------>3");
								
								int employeeTotal3=0;
						        try{
						         employeeTotal3 = Integer.parseInt(monthsTotal21);
						        }catch(Exception e){
						         
						        }
						        if(employeeTotal3<36){

								String priorEmployee31=req.getParameter("employer4");//priorEmployee3
								String position31=req.getParameter("position164");//position3
								String year31=req.getParameter("employedyears4"); // year3
								String months31=req.getParameter("employedmonths4");//months3
								String monthsum31=req.getParameter("employedmonthssum4");//monthsum3
								String monthsTotal31=req.getParameter("employedmonthstotal158");//monthsTotal3

								log.debug("priorEmployee31"+priorEmployee31);
								log.debug("position31"+position31);
								log.debug("year31"+year31);
								log.debug("months31"+months31);
								log.debug("monthsum31"+monthsum31);
								log.debug("monthsTotal31"+monthsTotal31);
							
								
								dataStoreValue.put("CoApplicant-Employedtype11"," Employed");
								dataStoreValue.put("CoApplicant-Employedbussiness11",priorEmployee31);
								dataStoreValue.put("CoApplicant-EmployedjobTitle11",position31);
								dataStoreValue.put("CoApplicant-EmployedyearWorked11",year31);
								dataStoreValue.put("CoApplicant-EmployedmonthsWorked11",months31);
								dataStoreValue.put("CoApplicant-EmployedemployeeTotalMonths11",monthsTotal31);
								
								income.Type="Employed";
								income.Business=priorEmployee31;
								income.JobTitle=position31;
								income.Supplementary=false;
								income.Historical=true;
								income.Months=months31;
								incomeListForOdoo.add(income);
								int monthsInteger1 =0;
								try{
							 monthsInteger1 = Integer.parseInt(monthsum31);
								}catch(Exception e){
									
								}

								applicantEmployee.createIncomeApplicant(applicantId, 1, priorEmployee31, position31, monthsInteger1, false,true);

						        }

						}



					}
				}

				if(selectvalue.equalsIgnoreCase("Self-Employ")){
				     log.debug("inside Self Employed");

				     String priorEmployee1=req.getParameter("selfemployed1")+"(Current Year NOA)"; // priorEmployee1
				     String position1=req.getParameter("position165");//position1
				     String year1=req.getParameter("selfemployedyears1");//year1
				     String months1=req.getParameter("selfemployedmonths1");//months1
				     String monthsum1=req.getParameter("selfemployedmonthssum1");//monthsum1
				     String monthsTotal1=req.getParameter("selfemployedmonthstotal1");//monthsTotal1
				log.debug("priorEmployee1"+priorEmployee1);
				log.debug(position1);
				log.debug(year1);
				log.debug(months1);
				log.debug(monthsum1);
				log.debug(monthsTotal1);

				dataStoreValue.put("CoApplicant-Self-Employtype12"," Self-Employ");
				dataStoreValue.put("CoApplicant-Self-Employbussiness12",priorEmployee1);
				dataStoreValue.put("CoApplicant-Self-EmployjobTitle12",position1);
				dataStoreValue.put("CoApplicant-Self-EmployyearWorked12",year1);
				dataStoreValue.put("CoApplicant-Self-EmploymonthsWorked12",months1);
				dataStoreValue.put("CoApplicant-Self-EmployemployeeTotalMonths12",monthsum1);
				
				
				
				income = new Incomes();

				
				income.Type="Self-Employed";
				income.Business=priorEmployee1+"(Current Year NOA)";
				income.JobTitle=position1;
				income.Historical=false;
				income.Supplementary=false;
				income.Months=months1;

				boolean history=false;
				boolean supplementri=false;
				
log.debug("after Icnome Type");
int monthsInteger=0;
try{
monthsInteger = Integer.parseInt(monthsum1);
}catch(Exception e){
	
}
				     
				     log.debug("Inside Months");
				     log.debug("Created Incomes type Self employee below 3 years ");
				     log.debug("Going to CouchBAse");
				     
				 
				     int monthsum1Integer=0;
				     try{
						 monthsum1Integer = Integer.parseInt(monthsTotal1);
						
						
						log.debug("monthsum1Integer"+monthsum1Integer);

				     }catch (Exception e) {
e.printStackTrace();
				     }
				     applicantEmployee.createIncomeApplicant(applicantId, 2, priorEmployee1, position1, monthsum1Integer, history,supplementri);

				     if(monthsum1Integer<36 && monthsum1!=null){
				    	 log.debug("Privious Self employ Details");
				    	 
				    	 String selfemployed2=req.getParameter("selfemployed2")+"(Prior Year NOA)";
				    	 String positionself2=req.getParameter("positionself2");
				    	 String selfemployedyears2=req.getParameter("selfemployedyears2");
				    	 String selfemployedmonths170=req.getParameter("selfemployedmonths170");
				    	 String selfemployemonthssum2=req.getParameter("selfemployemonthssum2");
				    	 
				    	 
				    	 dataStoreValue.put("CoApplicant-Self-Employtype13"," Self-Employ");
							dataStoreValue.put("CoApplicant-Self-Employbussiness13",selfemployed2);
							dataStoreValue.put("CoApplicant-Self-EmployjobTitle13",positionself2);
							dataStoreValue.put("CoApplicant-Self-EmployyearWorked13",year1);
							dataStoreValue.put("CoApplicant-Self-EmploymonthsWorked13",selfemployedmonths170);
							dataStoreValue.put("CoApplicant-Self-EmployemployeeTotalMonths13",selfemployemonthssum2);
						
				    	 
				    	 log.debug("selfemployed2:\t"+selfemployed2);
				    	 log.debug("positionself2:\t"+positionself2);
				    	 log.debug("selfemployedyears2:\t"+selfemployedyears2);
				    	 log.debug("selfemployedmonths170:\t"+selfemployedmonths170);
				    	 log.debug("selfemployemonthssum2:\t"+selfemployemonthssum2);
				    					    	 
				    	 income=new Incomes();

				    	 income.Type="Self-Employed";
							income.Business=selfemployed2+"(Prior Year NOA)";
							income.JobTitle=positionself2;
							income.Historical=true;
							income.Supplementary=false;
							income.Months=selfemployemonthssum2;
							
						
							  int selfemployemonthssum2Integer=0;
								try{
						      selfemployemonthssum2Integer = Integer.parseInt(selfemployemonthssum2);
								}catch(Exception e){
									
								}

							//Logic to inser data in OpenERP
							log.debug("Going to OpenERP........");
						     applicantEmployee.createIncomeApplicant(applicantId, 2, selfemployed2, positionself2, selfemployemonthssum2Integer, false,true);

						//	logic for ClouchBASe
							dataStoreValue.put("CoApplicant-Self-Employselfemployed2", selfemployed2);
							dataStoreValue.put("CoApplicant-Self-Employpositionself2", positionself2);
							dataStoreValue.put("CoApplicant-Self-Employselfemployedyears2",selfemployedyears2);
							dataStoreValue.put("CoApplicant-Self-Employselfemployedmonths170", selfemployedmonths170);
							dataStoreValue.put("CoApplicant-Self-Employselfemployemonthssum2", selfemployemonthssum2);
				    	  }
				  	     

				    }
				if(selectvalue.equalsIgnoreCase("Commission") && selectvalue!=null){
					log.debug("inside Commission");
					income = new Incomes();
					String currentEmployee1=req.getParameter("employer"); // currentEmployee
					String currentPosition1=req.getParameter("position1"); //currentPosition
					String yearWorked1=req.getParameter("empoyedyears"); //yearWorked
					String monthsWorked1=req.getParameter("employedmonths1"); //monthsWorked
					String employeeTotalMonths1=req.getParameter("employedmonthstotal1"); //employeeTotalMonths


					log.debug("currentEmployee1"+currentEmployee1);
					log.debug("currentPosition1"+currentPosition1);
					log.debug("yearWorked1"+yearWorked1);
					log.debug("monthsWorked1"+monthsWorked1);

					income.Type="Commission";
					income.Business=currentEmployee1;
					income.JobTitle=currentPosition1;
					income.Supplementary=true;
					income.Months=monthsWorked1;
					incomeListForOdoo.add(income);

				//	int monthsWorked = Integer.parseInt(employeeTotalMonths1);
					int monthsInteger=0;
try{
					
					 monthsInteger = Integer.parseInt(monthsWorked1);
}catch(Exception e){
	e.printStackTrace();
	
}
					applicantEmployee.createIncomeApplicant(applicantId, 4, currentEmployee1, currentPosition1, monthsInteger, true);
					log.debug("OpenERP Going");

					log.debug("Going to CouchBAse");

					
					
					
					dataStoreValue.put("CoApplicant-Comissiontype14","Comission");
					dataStoreValue.put("CoApplicant-Comissionbussiness14",currentEmployee1);
					dataStoreValue.put("CoApplicant-ComissionjobTitle14",currentPosition1);
					dataStoreValue.put("CoApplicant-Comissionsupplementry14",true);
					dataStoreValue.put("CoApplicant-Comissionmonths14",monthsWorked1);
	
					int employeeTotal=0;

					try{

				employeeTotal = Integer.parseInt(employeeTotalMonths1);
					}catch (Exception e) {
e.printStackTrace();					}
					log.debug("employeeTotal"+employeeTotal);


					if(employeeTotal<36 && employeeTotalMonths1!=null){
						income = new Incomes();

						String priorEmployee11=req.getParameter("employer138"); // priorEmployee1
						String position11=req.getParameter("position162");//position1
						String year11=req.getParameter("empoyedyears2");//year1
						String months11=req.getParameter("employedmonths2");//months1
						String monthsum11=req.getParameter("employedmonthssum2");//monthsum1
						String monthsTotal11=req.getParameter("employedmonthstotal2");//monthsTotal1

						income.Type="Commission";
						income.Business=priorEmployee11;
						income.JobTitle=position11;
						income.Supplementary=false;
						income.Historical=true;
						income.Months=months11;
						incomeListForOdoo.add(income);
						log.debug("list size : "+incomeListForOdoo.size());
						int employeeTotal1 =0;
						try{
					 employeeTotal1 = Integer.parseInt(monthsTotal11);
						}catch(Exception e){
							
						}
						int monthsInteger1=0;
						try{
						 monthsInteger1 = Integer.parseInt(monthsum11);
						}catch(Exception e){
							
						}
						
						
						//Bussiness created with MonthSelection

						applicantEmployee.createIncomeApplicant(applicantId, 4, priorEmployee11, position11, monthsInteger1,false, true);
						log.debug("Created Incomes type Selected Employee below 3 years ");

						log.debug("Going to CouchBAse");
						
					
						
						dataStoreValue.put("CoApplicant-Comissiontype15","Commision");
						dataStoreValue.put("CoApplicant-Comissionbussiness15",priorEmployee11);
						dataStoreValue.put("CoApplicant-ComissionjobTitle15",position11);
						dataStoreValue.put("CoApplicant-Comissionsupplementry15",true);
						dataStoreValue.put("CoApplicant-Comissionmonths15",months11);

							

						
						if(employeeTotal1<36 && monthsTotal11!=null){

							String priorEmployee21=req.getParameter("employer3");//priorEmployee2
							String position21=req.getParameter("position163"); // position2
							String year21=req.getParameter("empoyedyears3");//year2
							String months21=req.getParameter("employedmonths3");//months2
							String monthsum21=req.getParameter("employedmonthssum3");//monthsum2
							String monthsTotal21=req.getParameter("employedmonthstotal3");//monthsTotal2
							
							income.Type="Commission";
							income.Business=priorEmployee21;
							income.JobTitle=position21;
							income.Supplementary=false;
							income.Historical=true;
							income.Months=months21;
							incomeListForOdoo.add(income);
							log.debug("Creating Incomes type Selected Employee below 3 years ");


							int monthsInteger11=0;
							try{
					monthsInteger11 = Integer.parseInt(monthsum21);
							}catch(Exception e){
								
							}
							//Bussiness created with MonthSelection

							applicantEmployee.createIncomeApplicant(applicantId, 4, priorEmployee21, position21, monthsInteger11,false, true);
							log.debug("Created Incomes type Selected Employee below 3 years ");

							log.debug("Going to CouchBAse");
							
							
							
							dataStoreValue.put("CoApplicant-Comissiontype16","Commision");
							dataStoreValue.put("CoApplicant-Comissionbussiness16",priorEmployee21);
							dataStoreValue.put("CoApplicant-ComissionjobTitle16",position21);
							dataStoreValue.put("CoApplicant-Comissionsupplementry16",true);
							dataStoreValue.put("CoApplicant-Comissionmonths16",months21);

								
							int employeeTotal2=0;
							try{
							 employeeTotal2=Integer.parseInt(monthsTotal21);
							}catch(Exception e){
								
							}

							if(employeeTotal2<36 && monthsTotal21!=null){
								String priorEmployee31=req.getParameter("employer4");//priorEmployee3
								String position31=req.getParameter("position164");//position3
								String year31=req.getParameter("employedyears4"); // year3
								String months31=req.getParameter("employedmonths4");//months3
								String monthsum31=req.getParameter("employedmonthssum4");//monthsum3
								String monthsTotal31=req.getParameter("employedmonthstotal158");//monthsTotal3
								income.Type="Commission";
								income.Business=priorEmployee31;
								income.JobTitle=position31;
								income.Supplementary=false;
								income.Historical=true;
								income.Months=months31;
								incomeListForOdoo.add(income);
								int monthsInteger3 =0;
								try{
								 monthsInteger3 = Integer.parseInt(months31);
								
								}catch(Exception e){
									
								}

								
								//Bussiness created with MonthSelection

								applicantEmployee.createIncomeApplicant(applicantId, 4, priorEmployee31, position31, monthsInteger3,false, true);
								log.debug("Created Incomes type Selected Employee below 3 years ");
								log.debug("Going to CouchBAse");
								
							
								
								dataStoreValue.put("CoApplicant-Comissiontype17","Commsion");
								dataStoreValue.put("CoApplicant-Comissionbussiness17",priorEmployee31);
								dataStoreValue.put("CoApplicant-ComissionjobTitle17",position31);
								dataStoreValue.put("CoApplicant-Comissionsupplementry17",true);
								dataStoreValue.put("CoApplicant-Comissionmonths1117",position31);
	
								
							}

						}



					}
				}
}
			
			dataStoreValue.put("CoApplicant-subForm4",subForm);
			String applicantIDone=(String) ses
					.getAttribute("applicantID");
			CouchBaseOperation	 storeData=new CouchBaseOperation();
		storeData.appendDataInCouchBase("Applicant_"+applicantIDone, dataStoreValue);
			log.debug("CouchBase data created"); 
	
			req.setAttribute("uniid",uniid);
	    		//req.setAttribute("applicantId",applicantId);
	     		req.getRequestDispatcher("MortgageApplicationCo5.jsp").include(req, res);
			
				// res.sendRedirect("http://form.jotform.us/form/50504873079156?unniid="+form1UniqueId+"&applicantId="+applicantId);

	     		}else{
					
	    			req.setAttribute("message", " We are sorry, but it seems the security and reliability of your internet connection may have been weakened.  To protect your identity and the security of your information, can you please submit this application again");
					req.getRequestDispatcher("MortgageApplicationSucess.jsp").forward(req, res);
					
				}

		} catch (Exception e) {
			log.error("error in service : " + e);
		}

	}

}