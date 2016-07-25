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
import java.util.Date;
import java.util.HashMap;
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
public class MortgageForm6Servlet extends HttpServlet{
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		Logger log = LoggerFactory.getLogger(MortgageForm6Servlet.class);
		SymlConstant sc=new SymlConstant();
		HashMap dataStoreValue= new HashMap();

		try{
			log.debug("inside service method");

			String leadingGoal=req.getParameter("whatis44");  
			  dataStoreValue.put("Applicant-leadingGoal", leadingGoal);

log.debug("leadin goal "+leadingGoal);
String uniid=req.getParameter("uniid");
log.debug("old unique id is ----"+ uniid);
			String formType ="Mortgage Application";
			String subForm ="Mortgage Application 6";
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			CreateApplicant oppertunityUpdate=null;

			
			
			
			
			log.debug("old unique id is  "+uniid);
			//get current date time with Calendar()
			Calendar cal = Calendar.getInstance();
			String currentDateTime=(dateFormat.format(cal.getTime()));


			HttpSession ses=req.getSession(true);
			String form1UniqueId=(String)ses.getAttribute("form1uniqueId");
			log.debug("seesion unique id "+form1UniqueId);
			String applicantID=(String)ses.getAttribute("applicantID");
			
			//checking the both form filled by same person
			if(form1UniqueId.equals(form1UniqueId)){
			
			String applicantID2=(String)ses.getAttribute("applicantId2");
	
			int leadId=0;
			
			try{
				leadId=Integer.parseInt((String)ses.getAttribute("crmLeadId"));
			}catch(Exception e){
				
			}
			
			int applicantIDInt=0;
		int	applicantID2Int=0;
			try{
		applicantIDInt = Integer.parseInt(applicantID);
			
			}catch(Exception e){
				
			}
			
			try{
				applicantID2Int = Integer.parseInt(applicantID2);
					
					}catch(Exception e){
						
					}
			
			log.debug("applicantIDInt one"+applicantIDInt);
			log.debug("applicantIDInt two"+applicantID2Int);
			// get ip of latest form sumitted
			String ip=req.getRemoteAddr();
			
			
			String imaginesamejob1=req.getParameter("ieasily66");
			log.debug("imaginesamejob1"+imaginesamejob1);
	       
	        
	        //Getting ApplicantId
	        
	        
		 	
        	int jobForFiveYears=0;
        	int incomeDcreased=0;
        	int futurfamilly=0;
        	int buyNewVehical=0;
        	int lifeStyleChnage=0;
        	int financialRisktaker=0;
        	int proprtyLessThaniveyears=0;
        	String incomeraise1="";
        	 dataStoreValue.put("Applicant-imaginesamejob1", imaginesamejob1);
	        if(imaginesamejob1.equalsIgnoreCase("Yes") && imaginesamejob1!=null){
	        	log.debug("inside imaginesamejob1 YES");
	        	jobForFiveYears=5;
	
	        	 log.debug("updating Oppertunity");
	        	
	        	
	        	
	        	
	        }else if(imaginesamejob1.equalsIgnoreCase("No") && imaginesamejob1!=null){
	        	log.debug("inside imaginesamejob1 NO");
	        	jobForFiveYears=1;

	        	 log.debug("updating Oppertunity");
	        	
	        	
	        }else if(imaginesamejob1.equalsIgnoreCase("Maybe") && imaginesamejob1!=null){
	        	log.debug("inside imaginesamejob1 Maybe");
	        	jobForFiveYears=3;
	          	 log.debug("updating Oppertunity");
	        }
	        
	        String incomedown1=req.getParameter("ifmy");
			log.debug("incomedown1"+incomedown1);

			 dataStoreValue.put("Applicant-incomedown1", incomedown1);
	        if(incomedown1.equalsIgnoreCase("Yes") && incomedown1!=null){
	        	log.debug("inside imaginesamejob1 Yes");

	        	incomeDcreased=5;
	     
	        	 log.debug("updating Oppertunity");
	        	
	        	
	       
	        }else if(incomedown1.equalsIgnoreCase("No") && incomedown1!=null){
	        	log.debug("inside incomedown1 NO");
	        	incomeDcreased=1;

	        	
	        	
	        }else if(incomedown1.equalsIgnoreCase("Maybe") && incomedown1!=null){
	        	log.debug("inside incomedown1 Maybe");
	        	incomeDcreased=3;
	    
	        	 log.debug("updating Oppertunity");
	        }
	        
	        
	        
	        String largerfamily1 =req.getParameter("imight");
			log.debug("largerfamily1"+largerfamily1);

			 dataStoreValue.put("Applicant-largerfamily1", largerfamily1);
	        if(largerfamily1.equalsIgnoreCase("Yes") && largerfamily1!=null){
	        	log.debug("inside largerfamily1 Yes");

	        	futurfamilly=5;
	     
	        	 log.debug("updating Oppertunity");
	        	
	        	
	        	
	        	
	        }else if(largerfamily1.equalsIgnoreCase("No") && largerfamily1!=null){
	        	log.debug("inside largerfamily1 NO");
	        	
	           	futurfamilly=1;

	      
	        	 log.debug("updating Oppertunity");
	        	
	        	
	        }else if(largerfamily1.equalsIgnoreCase("Maybe") && largerfamily1!=null){
	        	log.debug("inside largerfamily1 Maybe");
	           	futurfamilly=3;
	        
	        	 log.debug("updating Oppertunity");
	        }
	        
	        
	        
	        
	        
	        
			String buyingnewvechile1=req.getParameter("iam");
			 dataStoreValue.put("Applicant-buyingnewvechile1", buyingnewvechile1);
			 if(buyingnewvechile1.equalsIgnoreCase("Yes") && buyingnewvechile1!=null){
		        	log.debug("inside buyingnewvechile1 Yes");

		        	buyNewVehical=5;
	        
	        	 log.debug("updating Oppertunity");
	        	
	        	
	        	
	        	
	        }else if(buyingnewvechile1.equalsIgnoreCase("No") && buyingnewvechile1!=null){
	        	log.debug("inside buyingnewvechile1 NO");
	        	buyNewVehical=1;
	        
	        	 log.debug("updating Oppertunity");
	        	
	        	
	        }else if(buyingnewvechile1.equalsIgnoreCase("Maybe") && buyingnewvechile1!=null){
	        	log.debug("inside buyingnewvechile1 Maybe");
	        	buyNewVehical=3;
	        	
	        	 log.debug("updating Oppertunity");
	        }
	        
			String Planninglifestyle1=req.getParameter("inthe63");
			 dataStoreValue.put("Applicant-Planninglifestyle1", Planninglifestyle1);
			 if(Planninglifestyle1.equalsIgnoreCase("Yes") && Planninglifestyle1!=null){
		        	log.debug("inside Planninglifestyle1 Yes");

		        	lifeStyleChnage=5;
	        	
	        	 log.debug("updating Oppertunity");
	        	
	        	
	        	
	        	
	        }else if(Planninglifestyle1.equalsIgnoreCase("No") && Planninglifestyle1!=null){
	        	log.debug("inside Planninglifestyle1 NO");

	        	lifeStyleChnage=1;
	        
	        	 log.debug("updating Oppertunity");
	        	
	        	
	        }else if(Planninglifestyle1.equalsIgnoreCase("Maybe") && Planninglifestyle1!=null){
	        	

	        	lifeStyleChnage=3;
	        
	        	 log.debug("updating Oppertunity");
	        }
			
			
			 dataStoreValue.put("opporunity_id", leadId);
			
			String financialrisk1=req.getParameter("iconsider");
			 dataStoreValue.put("Applicant-financialrisk1", financialrisk1);
			 if(financialrisk1.equalsIgnoreCase("Yes") && financialrisk1!=null){
		        	
				 financialRisktaker=5;
	        
	        	 log.debug("updating Oppertunity");
	        	       	
	        	
	        	
	        }else if(financialrisk1.equalsIgnoreCase("No") && Planninglifestyle1!=null){
	        	
	       	 financialRisktaker=1;
	        
	        	 log.debug("updating Oppertunity");
	        	
	        	
	        }else if(financialrisk1.equalsIgnoreCase("Maybe") && financialrisk1!=null){
	       	 financialRisktaker=3;
	        	
	        	 log.debug("updating Oppertunity");
	        }
			
			
			String thinkproperty1=req.getParameter("ithink65");
			 dataStoreValue.put("Applicant-thinkproperty1", thinkproperty1);
			
			if(thinkproperty1.equalsIgnoreCase("Yes") && thinkproperty1!=null){
	        	proprtyLessThaniveyears=5;
	        	
	        	 log.debug("updating Oppertunity");
	        	       	
	        	
	        	
	        }else if(thinkproperty1.equalsIgnoreCase("No") && thinkproperty1!=null){
	        	
	        	proprtyLessThaniveyears=1;
	        
	        	 log.debug("updating Oppertunity");
	        	
	        	
	        }else if(thinkproperty1.equalsIgnoreCase("Maybe") && thinkproperty1!=null){
	        	
	        	proprtyLessThaniveyears=3;
	        
	        	 log.debug("updating Oppertunity");
	        }
			
			
			 incomeraise1=req.getParameter("ieasily66");
		
			 log.debug("jobForFiveYears "+jobForFiveYears +" incomeDcreased "+incomeDcreased +" financialRisktaker "+ financialRisktaker +" buyNewVehical "+ buyNewVehical +"lifeStyleChnage "+financialRisktaker+" proprtyLessThaniveyears"+proprtyLessThaniveyears +"futurfamilly"+futurfamilly );
			 dataStoreValue.put("Applicant-incomeraise1", incomeraise1);
			 oppertunityUpdate=new CreateApplicant();
				String whoWillLive   =req.getParameter("whowill57");
				 dataStoreValue.put("Applicant-whoWillLive", whoWillLive);
				int liveDetails=0;
				float monthlyRentalIncome=0;
			 //own property checking 
			 
				String mortgageinmind1   =req.getParameter("doyou36");
				 dataStoreValue.put("Applicant-mortgageinmind1", mortgageinmind1);
			 int desiredType=0;
			 
			    if(mortgageinmind1.equalsIgnoreCase("Variable")){
			     desiredType=2;
			     
			     
			    }else if(mortgageinmind1.equalsIgnoreCase("Fixed")){
			     desiredType=1;

			     
			    }else if(mortgageinmind1.equalsIgnoreCase("Line of Credit")){
			     desiredType=0;

			     
			    }else if(mortgageinmind1.equalsIgnoreCase("Cashback")){
			     desiredType=3;

			    }else if(mortgageinmind1.equalsIgnoreCase("Combination")){
			     desiredType=5;

			    }else if(mortgageinmind1.equalsIgnoreCase("Best Option")){
			     desiredType=4;

			    }
			    
			    String currentMortgageTerm=req.getParameter("doyou37");
			    dataStoreValue.put("Applicant-currentMortgageTerm", currentMortgageTerm);
			    int  mortgagemind=0;
			    if(currentMortgageTerm.equalsIgnoreCase("6 Month")){
			        mortgagemind=2;
			        
			       }else if(currentMortgageTerm.equalsIgnoreCase("1 Year")){
			        mortgagemind=3;

			       }else if(currentMortgageTerm.equalsIgnoreCase("2 Year")){
			        mortgagemind=4;

			       }else if(currentMortgageTerm.equalsIgnoreCase("3 Year")){
			        mortgagemind=5;

			       }else if(currentMortgageTerm.equalsIgnoreCase("4 Year")){
			        mortgagemind=6;

			       }else if(currentMortgageTerm.equalsIgnoreCase("5 Year")){
			        mortgagemind=7;

			       }else if(currentMortgageTerm.equalsIgnoreCase("7 Year")){
			        mortgagemind=8;

			       }else if(currentMortgageTerm.equalsIgnoreCase("10 Year")){
			        mortgagemind=9;

			       }else if(currentMortgageTerm.equalsIgnoreCase("None")){
				        mortgagemind=1;

				       }
			 
				String lookingfor11  =req.getParameter("isthere");
				  dataStoreValue.put("Applicant-lookingfor11", lookingfor11);
				String desiredMortgitionValue="0";
				if(lookingfor11.equalsIgnoreCase("Suggest")){
					desiredMortgitionValue="25";
					
				}else{
					
					try{
						desiredMortgitionValue=lookingfor11.substring(0,2);
					}catch(Exception e){
						
					}
					
				}
			 

			
			if(leadingGoal.equalsIgnoreCase("Purchase") && leadingGoal!=null){
				log.debug("inside purchase");

				
				log.debug("Select Value was Property");
				String propertyaddress1=req.getParameter("pleaseconfirm");
				String purchaseprice11=req.getParameter("purchaseprice29"); 
				String downpayment1=req.getParameter("downpayment30"); 
				String paymentsource1=req.getParameter("whatare"); 
				
				String propertylisted1=req.getParameter("isthe");
				if(propertylisted1==null){
					propertylisted1="";
				}
				String possessiondate=req.getParameter("whatis");
				String financingdate1 =req.getParameter("whatis33");
				String refiAdditionalAmount =req.getParameter("refiadditionalamount47");
		
				String rentalAmount=null;
				if(whoWillLive.equalsIgnoreCase("Renter") && whoWillLive!=null){
                     log.debug("selected Renter");
     				 rentalAmount   =req.getParameter("rentalAmount");
     				dataStoreValue.put("Applicant-rentalAmount", rentalAmount);
 				
				}if(whoWillLive.equalsIgnoreCase("Owner&Renter") && whoWillLive!=null){
                    log.debug("selected Owner&Renter");
    				rentalAmount   =req.getParameter("rentalAmount");
     				dataStoreValue.put("Applicant-rentalAmount", rentalAmount);

				
				}
				
				
				
				
				
				  
				  if(whoWillLive.equalsIgnoreCase("Owner & Renter") && whoWillLive!=null){
					     liveDetails=3;
					     log.debug("Owner&Renter");
					     monthlyRentalIncome   =Float.parseFloat( req.getParameter("rentalAmount"));

					    }else if(whoWillLive.equalsIgnoreCase("Renter") && whoWillLive!=null){
					     liveDetails=2;
					     log.debug("Renter");

					     monthlyRentalIncome   =Float.parseFloat( req.getParameter("rentalAmount"));
					    }else if(whoWillLive.equalsIgnoreCase("Owner (Myself)") && whoWillLive!=null){
					     liveDetails=1;
					     log.debug("Owner");


					    }
				

			log.debug("propertyaddress1 : "+propertyaddress1+" purchaseprice11 : "+purchaseprice11+" downpayment1 : "+downpayment1+" paymentsource1 : "+paymentsource1
					
					+" propertylisted1 : "+propertylisted1+" propertypuchase1 : "+possessiondate+" financingdate1 : "+financingdate1+" refiAdditionalAmount : "+refiAdditionalAmount
					
					+" whoWillLive : "+whoWillLive+" rentalAmount : "+rentalAmount+formType+" subForm "+subForm+" form1UniqueId "+form1UniqueId);
				dataStoreValue.put("Applicant-propertyaddress1", propertyaddress1);
				dataStoreValue.put("Applicant-purchaseprice11", purchaseprice11);
				dataStoreValue.put("Applicant-downpayment1", downpayment1);
				dataStoreValue.put("Applicant-paymentsource1", paymentsource1);
				dataStoreValue.put("Applicant-propertylisted1", propertylisted1);
				dataStoreValue.put("Applicant-posessiondate", possessiondate);
				dataStoreValue.put("Applicant-financingdate1", financingdate1);
				dataStoreValue.put("Applicant-refiAdditionalAmount", refiAdditionalAmount);
			

				dataStoreValue.put("Applicant-subForm6", subForm);

		Date expected_closing_date=new Date(possessiondate);
		Date condition_of_financing_date=null;
		try{
		 condition_of_financing_date=new Date(financingdate1);
		}catch(Exception e){
			
		}
				
	        	 oppertunityUpdate.assignOppertunity(applicantIDInt,jobForFiveYears, incomeDcreased, futurfamilly, buyNewVehical, lifeStyleChnage, financialRisktaker, proprtyLessThaniveyears, leadId);

	        	 oppertunityUpdate.assignOppertunityPurchase(applicantID, leadId, "2", propertyaddress1, purchaseprice11, downpayment1+"", paymentsource1+"", expected_closing_date,condition_of_financing_date, liveDetails+"",monthlyRentalIncome+"", desiredType+"", mortgagemind+"",desiredMortgitionValue);
               //String in couchBase

				CouchBaseOperation storeData=new CouchBaseOperation();
				storeData.appendDataInCouchBase("Applicant_"+applicantID, dataStoreValue);

			
				if(propertylisted1.equals("MLS Listed")||propertylisted1.equals("New Build")){
					req.setAttribute("uniid",form1UniqueId);
					req.getRequestDispatcher("MortgageApplication1c.jsp").include(req, res);
					}else{
						req.setAttribute("uniid",form1UniqueId);
						req.getRequestDispatcher("MortgageApplication7.jsp").include(req, res);
					}
				
				
			}else if(leadingGoal.equalsIgnoreCase("Pre-Approval") && leadingGoal!=null){
				log.debug("inside Pre-Approval Selected");
				
				String purchaseprice11=req.getParameter("purchaseprice"); 
				String planning1=req.getParameter("downpayment"); 
				String propertybuying11=req.getParameter("whattype");
			
		
				String refiAdditionalAmount =req.getParameter("refiadditionalamount47");
				
				if(whoWillLive.equalsIgnoreCase("Renter") && whoWillLive!=null){
					String rentalAmount =req.getParameter("rentalAmount");
                 dataStoreValue.put("Applicant-rentalAmount", rentalAmount);
				}
				if(whoWillLive.equalsIgnoreCase("Owner&Renter") && whoWillLive!=null){
                    log.debug("selected Owner&Renter");
                    String rentalAmount  =req.getParameter("rentalAmount");
     				dataStoreValue.put("Applicant-rentalAmount", rentalAmount);

				
				}
				
				if(whoWillLive.equalsIgnoreCase("Owner & Renter") && whoWillLive!=null){
				     liveDetails=3;
				     log.debug("Owner&Renter");
				     monthlyRentalIncome   =Float.parseFloat( req.getParameter("rentalAmount"));

				    }else if(whoWillLive.equalsIgnoreCase("Renter") && whoWillLive!=null){
				     liveDetails=2;
				     log.debug("Renter");

				     monthlyRentalIncome   =Float.parseFloat( req.getParameter("rentalAmount"));
				    }else if(whoWillLive.equalsIgnoreCase("Owner (Myself)") && whoWillLive!=null){
				     liveDetails=1;
				     log.debug("Owner");


				    }

				log.debug("purchaseprice11"+purchaseprice11+"\nplanning1"+planning1
						
						
						+"\npropertybuying11  :"+propertybuying11+"\nwhoWillLive"+whoWillLive);
				
				dataStoreValue.put("Applicant-purchaseprice11", purchaseprice11);
				dataStoreValue.put("Applicant-planning1", planning1);
				dataStoreValue.put("Applicant-propertybuying11", propertybuying11);
			dataStoreValue.put("Applicant-refiAdditionalAmount", refiAdditionalAmount);
				
				
				int propertybuying11_Test=0;
			   
			    if(propertybuying11.equalsIgnoreCase("House")){
			     
			     propertybuying11_Test=2;
			     
			    }else if(propertybuying11.equalsIgnoreCase("Condo")){
			     propertybuying11_Test=1;

			     
			    }else if(propertybuying11.equalsIgnoreCase("Mobile Home")){
			     propertybuying11_Test=1;

			    }else if(propertybuying11.equalsIgnoreCase("Acreage")){
			     propertybuying11_Test=6;

			    }else if(propertybuying11.equalsIgnoreCase("Vacation Property")){
			     propertybuying11_Test=5;

			    }else if(propertybuying11.equalsIgnoreCase("2nd Home")){
			     propertybuying11_Test=7;

			    }else if(propertybuying11.equalsIgnoreCase("Duplex")){
			     propertybuying11_Test=6;

			    }
				//in OpenERP
			    int downpayment=0;
			    try{
			    	downpayment=Integer.parseInt(planning1);
			    }catch(Exception e){
			    	
			    }
				
				
	        	 oppertunityUpdate.assignOppertunity(applicantIDInt,jobForFiveYears, incomeDcreased, futurfamilly, buyNewVehical, lifeStyleChnage, financialRisktaker, proprtyLessThaniveyears, leadId);
	        	 oppertunityUpdate.assignOppertunityPreApproval(applicantIDInt, leadId, "1", downpayment+"", propertybuying11_Test+"",liveDetails+"", monthlyRentalIncome+"",desiredType+"", mortgagemind+"", refiAdditionalAmount+"",desiredMortgitionValue,purchaseprice11);

               //String in couchBase

				CouchBaseOperation storeData=new CouchBaseOperation();
				storeData.appendDataInCouchBase("Applicant_"+applicantID, dataStoreValue);
				
				
				req.setAttribute("uniid",form1UniqueId);

					req.getRequestDispatcher("MortgageApplication1c.jsp").include(req, res);
					
				
				
			}else if(leadingGoal.equalsIgnoreCase("Refinance") && leadingGoal!=null){
				log.debug(" inside Refinance Selected");
			
				

				String propertyaddress1=req.getParameter("pleaseconfirm");
				String refinancing1=req.getParameter("refivalue");
				String mortageamount1  =req.getParameter("refimortgageamount");
				String currentmortage1 =req.getParameter("doyou");
				
				
				log.debug("propertyaddress1");
				log.debug("refinancing1");
				log.debug("mortageamount1");
				log.debug("currentmortage1");


				String generaldescription1="";
		
				int refiAdditionalAmount= 0;
				if(currentmortage1.equalsIgnoreCase("Maximum Amount")){
					
					log.debug("inside Maximum amount");
					
					
					if(whoWillLive.equalsIgnoreCase("Owner & Renter") && whoWillLive!=null){
					     liveDetails=3;
					     log.debug("Owner&Renter");
					     monthlyRentalIncome   =Float.parseFloat( req.getParameter("rentalAmount"));

					    }else if(whoWillLive.equalsIgnoreCase("Renter") && whoWillLive!=null){
					     liveDetails=2;
					     log.debug("Renter");

					     monthlyRentalIncome   =Float.parseFloat( req.getParameter("rentalAmount"));
					    }else if(whoWillLive.equalsIgnoreCase("Owner (Myself)") && whoWillLive!=null){
					     liveDetails=1;
					     log.debug("Owner");


					    }
					

					 generaldescription1 =req.getParameter("lendersrequire48");
					 whoWillLive =req.getParameter("whowill57");
					 
					 dataStoreValue.put("generaldescription1", generaldescription1);
					


					if(whoWillLive.equalsIgnoreCase("Renter")){
						log.debug("Renter Methods calling");
						String monthlyRental =req.getParameter("rentalamount");
					 dataStoreValue.put("Applicant-rentalamount", monthlyRental);
					 
					 

					}
					if(whoWillLive.equalsIgnoreCase("Owner & Renter") && whoWillLive!=null){
	                    log.debug("selected Owner&Renter");
	                    String rentalAmount  =req.getParameter("rentalAmount");
	     				dataStoreValue.put("Applicant-rentalAmount", rentalAmount);

					
					}
				}if(currentmortage1.equalsIgnoreCase("Specific Amount")){
					log.debug("Specific Amount");
					try{
					refiAdditionalAmount =Integer.parseInt(req.getParameter("refiadditionalamount47"));
					}catch(Exception e){
						
					}
					 dataStoreValue.put("Applicant-refiAdditionalAmount", refiAdditionalAmount);

					 
					// dataStoreValue.put("generaldescription1", generaldescription1);
				

					if(whoWillLive.equalsIgnoreCase("Renter")){
						log.debug("Renter");
						String monthlyRental =req.getParameter("rentalamount");
					 dataStoreValue.put("Applicant-rentalamount", monthlyRental);
					 
					 

					}
					if(whoWillLive.equalsIgnoreCase("Owner & Renter") && whoWillLive!=null){
	                    log.debug("selected Owner&Renter");
	                    String rentalAmount  =req.getParameter("rentalAmount");
	     				dataStoreValue.put("Applicant-rentalAmount", rentalAmount);

					
					}

				}if(currentmortage1.equalsIgnoreCase("None")){
					
				
					 
			
						if(whoWillLive.equalsIgnoreCase("Owner & Renter") && whoWillLive!=null){
						     liveDetails=3;
						     log.debug("Owner&Renter");
						     monthlyRentalIncome   =Float.parseFloat( req.getParameter("rentalAmount"));

						    }else if(whoWillLive.equalsIgnoreCase("Renter") && whoWillLive!=null){
						     liveDetails=2;
						     log.debug("Renter");

						     monthlyRentalIncome   =Float.parseFloat( req.getParameter("rentalAmount"));
						    }else if(whoWillLive.equalsIgnoreCase("Owner (Myself)") && whoWillLive!=null){
						     liveDetails=1;
						     log.debug("Owner");


						    }
						

					if(whoWillLive.equalsIgnoreCase("Renter")){
						log.debug("Renter Methods calling");
						String monthlyRental =req.getParameter("Applicant-rentalamount");
		
					 
					 

					}
					if(whoWillLive.equalsIgnoreCase("Owner & Renter") && whoWillLive!=null){
	                    log.debug("selected Owner&Renter");
	                    String rentalAmount  =req.getParameter("Applicant-rentalAmount");
	     				

					
					}

				}
				
				
			
		
		        
				
				String rentalproperty1=req.getParameter("iwould68");
		


				

				log.debug("propertyaddress1"+propertyaddress1+"refinancing1"+refinancing1+"mortageamount1"+mortageamount1+"currentmortage1"+currentmortage1
						+"generaldescription1"+generaldescription1+"whoWillLive"+whoWillLive+"refiAdditionalAmount"+refiAdditionalAmount+"mortgageinmind1"+mortgageinmind1
						
						+"lookingfor11"+lookingfor11+"incomedown1"+incomedown1+"largerfamily1"+largerfamily1+"buyingnewvechile1"+buyingnewvechile1+"Planninglifestyle1"+Planninglifestyle1+"financialrisk1"+financialrisk1+"thinkproperty1"+thinkproperty1
						+"imaginesamejob1"+imaginesamejob1+"incomeraise1"+incomeraise1+"rentalproperty1"+rentalproperty1);
				
				
				
				dataStoreValue.put("Applicant-propertyaddress1", propertyaddress1);
				dataStoreValue.put("Applicant-refinancing1", refinancing1);
				dataStoreValue.put("Applicant-mortageamount1", mortageamount1);
				dataStoreValue.put("Applicant-currentmortage1", currentmortage1);
				
				//dataStoreValue.put("propertywillfinancing1", propertywillfinancing1);
	
				dataStoreValue.put("Applicant-rentalproperty1", rentalproperty1);
				//refiAdditionalAmount
	        	 oppertunityUpdate.assignOppertunity(applicantIDInt,jobForFiveYears, incomeDcreased, futurfamilly, buyNewVehical, lifeStyleChnage, financialRisktaker, proprtyLessThaniveyears, leadId);
	        	 oppertunityUpdate.assignOppertunityRefinance(applicantIDInt, leadId, "3", propertyaddress1, mortageamount1, liveDetails+"",monthlyRentalIncome+"" ,desiredType+"", mortgagemind+"", refiAdditionalAmount+"",desiredMortgitionValue,refinancing1);
               //String in couchBase

				CouchBaseOperation storeData=new CouchBaseOperation();
				storeData.appendDataInCouchBase("Applicant_"+applicantID, dataStoreValue);
				

				req.setAttribute("uniid",form1UniqueId);
						req.getRequestDispatcher("MortgageApplication7.jsp").include(req, res);
					



			}
		
	     		//req.setAttribute("applicantId",applicantId);
	     		//req.getRequestDispatcher("MortgageApplication7.jsp").include(req, res);
			//res.sendRedirect("http://form.jotformpro.com/form/50434652526958?uniqueId"+form1UniqueId);
			
			}else{
    			req.setAttribute("message", " We are sorry, but it seems the security and reliability of your internet connection may have been weakened.  To protect your identity and the security of your information, can you please submit this application again");
				req.getRequestDispatcher("MortgageApplicationSucess.jsp").forward(req, res);
				
			}
			
			
	}catch(Exception e){
			log.error("error in service : "+e);
		}

	}


}