package com.syml.mortgage.application;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


//import org.apache.catalina.connector.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.syml.addressgroup.pojo.Incomes;
import com.syml.addressgroup.pojo.PropertyGrouping;
import com.syml.constants.SymlConstant;
import com.syml.couchbase.CouchBaseOperation;
import com.syml.helper.GenericHelperClass;
import com.syml.openerp.CreateApplicant;
import com.syml.openerp.CreateLead;
public class MortgageForm3Servlet extends HttpServlet{
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		Logger log = LoggerFactory.getLogger(MortgageForm3Servlet.class);
		SymlConstant sc=new SymlConstant();
		HashMap dataStoreValue=new HashMap();
		CouchBaseOperation storeData=null;

		try{


			log.info("inside service method of mortgageFormServlet3");

			//String uniId=req.getParameter("unniid");
			String formType ="Mortgage Application";
			String subForm ="Mortgage Application 3";
			String uniid=req.getParameter("uniid");

			//get the session attribute
			HttpSession ses1=req.getSession(true);
			String form1UniqueId1=(String)ses1.getAttribute("form1uniqueId");
			String applicantID=(String)ses1.getAttribute("applicantID");
			String ip=req.getRemoteAddr();
			if(uniid.equals(form1UniqueId1)){

			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

			//get current date time with Calendar()
			Calendar cal = Calendar.getInstance();
			String currentDateTime=(dateFormat.format(cal.getTime()));

			String property1=req.getParameter("doyou");  //Property1

			log.debug(" property 1 : "+property1);

			CreateApplicant createLead = new CreateApplicant();

			//grouping properties
			ArrayList<PropertyGrouping> listOfProperties = new ArrayList<PropertyGrouping>();

			// max no. of property set (there total of 6)
			PropertyGrouping properti1 = null;

			PropertyGrouping properti2 = null;
			PropertyGrouping properti3 = null;

			PropertyGrouping properti4 = null;

			PropertyGrouping properti5 = null;
			PropertyGrouping properti6 = null;

			dataStoreValue.put("Applicant-doYouHaveProperty",property1);
			//if proprty1 i.e do you own any real state is true
			if(property1 != null && property1.equalsIgnoreCase("yes")){
				log.debug("1. do you own real estate? -> yes");
				String address1=req.getParameter("whatis135"); // address1
				String mortgageYesNo1=req.getParameter("doesthe"); //mortgageYesNo1
				String rentalYesNo1=req.getParameter("doyou160"); //rentalYesNo1
				String condoYesNo1=req.getParameter("doesthe161"); //condoYesNo1
				String condoFees1=req.getParameter("howmuch"); //condoFees1
				String sellingYesNo1=req.getParameter("areyou155"); //sellingYesNo1
				String additionalYesNo1=req.getParameter("doyou137"); // additionalYesNo1


				log.debug("old unique id is  "+uniid);
				//showing all valuses : 
				log.debug("address1 : "+address1+", mortgageYesNo1 : "+mortgageYesNo1+", rentalYesNo1 : "
						+rentalYesNo1+", condoYesNo1 : "+condoYesNo1+", condofee1 : "+condoFees1+", sellingYesNo1 : "
						+sellingYesNo1+", additionalYesNo1 : "+additionalYesNo1);
				dataStoreValue.put("Applicant-address1",address1);
				dataStoreValue.put("Applicant-mortgageYesNo1",mortgageYesNo1);
				dataStoreValue.put("Applicant-additionalYesNo1",additionalYesNo1);
				dataStoreValue.put("Applicant-sellingYesNo1",sellingYesNo1);
			
				dataStoreValue.put("Applicant-condoYesNo1",condoYesNo1);
				dataStoreValue.put("Applicant-condoFees1",condoFees1);
				dataStoreValue.put("Applicant-rentalYesNo1",rentalYesNo1);
		

				if(additionalYesNo1.equalsIgnoreCase("no")){

					properti1 = new PropertyGrouping(address1,mortgageYesNo1,rentalYesNo1,condoYesNo1,condoFees1,sellingYesNo1,additionalYesNo1);
					listOfProperties.add(properti1);

					if(mortgageYesNo1 != null && mortgageYesNo1.equalsIgnoreCase("yes") && sellingYesNo1 != null && sellingYesNo1.equalsIgnoreCase("yes")){

						log.debug("both mortgagae and selling set to yes");

						int propertyconter=GenericHelperClass.getPropertyCounter();
						createLead.createApplicantMortgage(applicantID, address1, propertyconter, sellingYesNo1);
						log.debug("Going to CouchBAse");

						

								if(condoYesNo1 != null && condoYesNo1.equalsIgnoreCase("yes")){
							log.debug("condo values set to yes");
							createLead.createApplicantProperties(applicantID, address1, condoFees1,propertyconter,sellingYesNo1);
							//logDebug
										
						}else{
							log.debug("condo values set to no");
							createLead.createApplicantProperties(applicantID, address1, "-1",propertyconter,sellingYesNo1);
							}



					}else if(mortgageYesNo1 != null && mortgageYesNo1.equalsIgnoreCase("yes") && sellingYesNo1 != null && sellingYesNo1.equalsIgnoreCase("no")){
						log.debug("when mortgage is true and selling is false");
						int propertyconter=GenericHelperClass.getPropertyCounter();

						if(rentalYesNo1 !=null && rentalYesNo1.equalsIgnoreCase("no")){
							log.debug("rental value is : "+rentalYesNo1);
							createLead.createApplicantMortgage(applicantID, address1,propertyconter, sellingYesNo1);
							log.debug("CouchBase");
									if(condoYesNo1 != null && condoYesNo1.equalsIgnoreCase("yes")){
								log.debug("condo values set to yes");
								createLead.createApplicantProperties(applicantID, address1, condoFees1,propertyconter,sellingYesNo1);
										}else{
								log.debug("condo values set to no");
								createLead.createApplicantProperties(applicantID, address1, "-1",propertyconter,sellingYesNo1);
								log.debug("CouchBase");
								}
						}else{
							log.debug("rental value is : "+rentalYesNo1);
							
							int propertyconter1=GenericHelperClass.getPropertyCounter();
							log.debug("propertycounet value if rental is yes nad selling is no : "+propertyconter1);

							createLead.createApplicantMortgageRental(applicantID, address1,propertyconter1, sellingYesNo1,"1");
							
							//

							createLead.createIncomeApplicantRental(applicantID,9,address1,"Landlord","12",false,true,propertyconter1);

									if(condoYesNo1 != null && condoYesNo1.equalsIgnoreCase("yes")){
								log.debug("condo values set to yes");
								createLead.createApplicantProperties(applicantID, address1, condoFees1,propertyconter1,sellingYesNo1);
										}else{
								log.debug("condo values set to no");
								createLead.createApplicantProperties(applicantID, address1, "-1",propertyconter1,sellingYesNo1);
								
							}
						}// rentalyesno1 if else end
					}else if(mortgageYesNo1 != null && mortgageYesNo1.equalsIgnoreCase("no") && sellingYesNo1 != null && sellingYesNo1.equalsIgnoreCase("yes")){
						log.debug("when mortgage is no and selling is true");
						int propertyconter=GenericHelperClass.getPropertyCounter();

						createLead.createApplicantMortgage(applicantID, address1,propertyconter, sellingYesNo1);
						if(condoYesNo1 != null && condoYesNo1.equalsIgnoreCase("yes")){
							log.debug("condo values set to yes");
							createLead.createApplicantProperties(applicantID, address1, condoFees1,propertyconter,sellingYesNo1);
							}else{
							log.debug("condo values set to no");
							createLead.createApplicantProperties(applicantID, address1, "-1",propertyconter,sellingYesNo1);
							
						}
					}else{
						int propertyconter=GenericHelperClass.getPropertyCounter();

						log.debug("when mortgage is no and selling is false");
						if(rentalYesNo1 != null && rentalYesNo1.equalsIgnoreCase("no")){
							createLead.createApplicantMortgage(applicantID, address1,propertyconter, sellingYesNo1);
										if(condoYesNo1 != null && condoYesNo1.equalsIgnoreCase("yes")){
								log.debug("condo values set to yes");
								createLead.createApplicantProperties(applicantID, address1, condoFees1,propertyconter,sellingYesNo1);
									}else{
								log.debug("condo values set to no");
								createLead.createApplicantProperties(applicantID, address1, "-1",propertyconter,sellingYesNo1);
									}
						}else{
							int propertyconter11=GenericHelperClass.getPropertyCounter();
							log.debug("propertycounet value if rental is yes nad selling is no : "+propertyconter11);
							createLead.createApplicantMortgageRental(applicantID, address1,propertyconter11, sellingYesNo1,"1");

							log.debug("calling income for rental");


							createLead.createIncomeApplicantRental(applicantID,9,address1,"Landlord","12",false,true ,propertyconter11);

									if(condoYesNo1 != null && condoYesNo1.equalsIgnoreCase("yes")){
								log.debug("condo values set to yes");
								createLead.createApplicantProperties(applicantID, address1, condoFees1,propertyconter11,sellingYesNo1);
									}else{
								log.debug("condo values set to no");
								createLead.createApplicantProperties(applicantID, address1, "-1",propertyconter11,sellingYesNo1);
										}
						}//end of if else rentalyesno1 
					}//end of else (mortgage and seeling logic
					//TODO couchbase operation
				}else {
					log.debug("2. do you own aditional property? -> yes");
					String address2=req.getParameter("whatis138");//address2
					String mortgageYesNo2=req.getParameter("doesthe139");//mortgageYesNo2
					String rentalYesNo2=req.getParameter("doyou163");//rentalYesNo2
					String condoYesNo2=req.getParameter("doesthe164");//condoYesNo2
					String condoFees2=req.getParameter("howmuch165");//condoFees2
					String sellingYesNo2=req.getParameter("areyou");//sellingYesNo2
					String additionalYesNo2=req.getParameter("doyou140"); // additionalYesNo2
					if(additionalYesNo2 !=null && additionalYesNo2.equalsIgnoreCase("no")){


						properti1 = new PropertyGrouping(address1,mortgageYesNo1,rentalYesNo1,condoYesNo1,condoFees1,sellingYesNo1,additionalYesNo1);
						properti2 = new PropertyGrouping(address2,mortgageYesNo2,rentalYesNo2,condoYesNo2,condoFees2,sellingYesNo2,additionalYesNo2);
						listOfProperties.add(properti1); 
						listOfProperties.add(properti2);
						Iterator iterate = listOfProperties.iterator();
						int i=1;
						while(iterate.hasNext()){
							
							
							
							log.debug("inside while loop with 2 values");
							PropertyGrouping property = (PropertyGrouping)iterate.next();

							// getting all properties stored in property Group
							String address = property.getAddress();
							String mortgageYesNo = property.getMortgageYesNo();
							String rentalYesNo = property.getRentalYesNo();
							String condoYesNo = property.getCondoYesNo();
							String condofee = property.getCondoFees();
							String sellingYesNo = property.getSellingYesNo();
							dataStoreValue.put("Applicant-address"+i,address);
							dataStoreValue.put("Applicant-mortgageYesNo"+i,mortgageYesNo);
						
							dataStoreValue.put("Applicant-sellingYesNo"+i,sellingYesNo);
						
							dataStoreValue.put("Applicant-condoYesNo"+i,condoYesNo);
							dataStoreValue.put("Applicant-condoFees"+i,condofee);
							dataStoreValue.put("Applicant-rentalYesNo"+i,rentalYesNo);
					
							//showing all valuses : 
							log.debug("address : "+address+", mortgageYesNo : "+mortgageYesNo+", rentalYesNo : "
									+rentalYesNo+", condoYesNo : "+condoYesNo+", condofee : "+condofee+", sellingYesNo : "+sellingYesNo);


							//now mortgage and selling logic
							if(mortgageYesNo != null && mortgageYesNo.equalsIgnoreCase("yes") && sellingYesNo != null && sellingYesNo.equalsIgnoreCase("yes")){

								log.debug("both mortgagae and selling set to yes");

								int propertyconter=GenericHelperClass.getPropertyCounter();
								createLead.createApplicantMortgage(applicantID, address, propertyconter, sellingYesNo);
											if(condoYesNo != null && condoYesNo.equalsIgnoreCase("yes")){
									log.debug("condo values set to yes");
									createLead.createApplicantProperties(applicantID, address, condofee,propertyconter,sellingYesNo);

										}else{
									log.debug("condo values set to no");
									createLead.createApplicantProperties(applicantID, address, "-1",propertyconter,sellingYesNo);
									
								}

							}else if(mortgageYesNo != null && mortgageYesNo.equalsIgnoreCase("yes") && sellingYesNo != null && sellingYesNo.equalsIgnoreCase("no")){
								log.debug("when mortgage is true and selling is false");
								if(rentalYesNo2 != null && rentalYesNo2.equalsIgnoreCase("no")){
									int propertyconter=GenericHelperClass.getPropertyCounter();

									log.debug("rentalyesno2 value : "+rentalYesNo2);
									createLead.createApplicantMortgage(applicantID, address,propertyconter, sellingYesNo);
									log.debug("CouchBase");
												if(condoYesNo != null && condoYesNo.equalsIgnoreCase("yes")){
										log.debug("condo values set to yes");
										createLead.createApplicantProperties(applicantID, address, condofee,propertyconter,sellingYesNo);
												}else{
										log.debug("condo values set to no");
										createLead.createApplicantProperties(applicantID, address, "-1",propertyconter,sellingYesNo);

									}
								}else{
									int propertyconter=GenericHelperClass.getPropertyCounter();
									log.debug("propertycounet value if rental is yes nad selling is no : "+propertyconter);
									log.debug("rentalyesno2 value : "+rentalYesNo2);
									createLead.createApplicantMortgageRental(applicantID, address,propertyconter, sellingYesNo,"1");

									log.debug("calling rental income");

									createLead.createIncomeApplicantRental(applicantID,9,address,"Landlord","12",false,true,propertyconter );


											if(condoYesNo != null && condoYesNo.equalsIgnoreCase("yes")){
										log.debug("condo values set to yes");
										createLead.createApplicantProperties(applicantID, address, condofee,propertyconter,sellingYesNo);
										log.debug("CouchBase");
											}else{
										log.debug("condo values set to no");
										createLead.createApplicantProperties(applicantID, address, "-1",propertyconter,sellingYesNo);

									}
								}//rentayesNo2 if else end
							}else if(mortgageYesNo != null && mortgageYesNo.equalsIgnoreCase("no") && sellingYesNo != null && sellingYesNo.equalsIgnoreCase("yes")){
								log.debug("when mortgage is no and selling is true");
								int propertyconter=GenericHelperClass.getPropertyCounter();

								createLead.createApplicantMortgage(applicantID, address,propertyconter, sellingYesNo);
								if(condoYesNo != null && condoYesNo.equalsIgnoreCase("yes")){
									log.debug("condo values set to yes");
									createLead.createApplicantProperties(applicantID, address, condofee,propertyconter,sellingYesNo);
											}else{
									log.debug("condo values set to no");
									createLead.createApplicantProperties(applicantID, address, "-1",propertyconter,sellingYesNo);
								
								}
							}else{
								log.debug("when mortgage is no and selling is false");
								int propertyconter=GenericHelperClass.getPropertyCounter();

								if(rentalYesNo2.equalsIgnoreCase("no") && rentalYesNo2 != null){
									createLead.createApplicantMortgage(applicantID, address,propertyconter, sellingYesNo1);
									log.debug("CouchBase");
										if(condoYesNo != null && condoYesNo.equalsIgnoreCase("yes")){
										log.debug("condo values set to yes");
										createLead.createApplicantProperties(applicantID, address, condoFees1,propertyconter,sellingYesNo);
											}else{
										log.debug("condo values set to no");
										createLead.createApplicantProperties(applicantID, address, "-1",propertyconter,sellingYesNo);
											}
								}else{
									int propertyconter2=GenericHelperClass.getPropertyCounter();
									log.debug("propertycounet value if rental is yes nad selling is no : "+propertyconter2);
									createLead.createApplicantMortgageRental(applicantID, address,propertyconter2, sellingYesNo1,"1");
									log.debug("CouchBase");

									log.debug("calling rental income");

									createLead.createIncomeApplicantRental(applicantID,9,address,"Landlord","12",false,true,propertyconter2);
											if(condoYesNo != null && condoYesNo.equalsIgnoreCase("yes")){
										log.debug("condo values set to yes");
										createLead.createApplicantProperties(applicantID, address, condoFees1,propertyconter2,sellingYesNo);
												}else{
										log.debug("condo values set to no");
										createLead.createApplicantProperties(applicantID, address, "-1",propertyconter2,sellingYesNo);
							
									}
								}//end of if else  rentalyesno2 
							}//end of else (mortgage and selling logic)
							//TODO couchbase operation

i=i+1;
						}// end of while



					}else{
						log.debug("3. do you own additional properties? --> yes");
						String address3=req.getParameter("whatis141");//address3
						String mortgageYesNo3=req.getParameter("doesthe142");//mortgageYesNo3
						String rentalYesNo3=req.getParameter("doyou166");//rentalYesNo3
						String condoYesNo3=req.getParameter("doesthe167");//condoYesNo3
						String condoFees3=req.getParameter("howmuch168");//condoFees3
						String sellingYesNo3=req.getParameter("areyou156");//sellingYesNo3



						String additionalYesNo3=req.getParameter("doyou143"); // additionalYesNo3

						if(additionalYesNo3 !=null && additionalYesNo3.equalsIgnoreCase("no")){


							properti1 = new PropertyGrouping(address1,mortgageYesNo1,rentalYesNo1,condoYesNo1,condoFees1,sellingYesNo1,additionalYesNo1);
							properti2 = new PropertyGrouping(address2,mortgageYesNo2,rentalYesNo2,condoYesNo2,condoFees2,sellingYesNo2,additionalYesNo2);
							properti3 = new PropertyGrouping(address3,mortgageYesNo3,rentalYesNo3,condoYesNo3,condoFees3,sellingYesNo3,additionalYesNo3);
							listOfProperties.add(properti1); 
							listOfProperties.add(properti2);
							listOfProperties.add(properti3);

							Iterator iterate = listOfProperties.iterator();
							int i=1;
							while(iterate.hasNext()){
								
								
							
								log.debug("inside while loop with 3 values");
								PropertyGrouping property = (PropertyGrouping)iterate.next();

								// getting all properties stored in property Group
								String address = property.getAddress();
								String mortgageYesNo = property.getMortgageYesNo();
								String rentalYesNo = property.getRentalYesNo();
								String condoYesNo = property.getCondoYesNo();
								String condofee = property.getCondoFees();
								String sellingYesNo = property.getSellingYesNo();
								
								dataStoreValue.put("Applicant-address"+i,address);
								dataStoreValue.put("Applicant-mortgageYesNo"+i,mortgageYesNo);
							
								dataStoreValue.put("Applicant-sellingYesNo"+i,sellingYesNo);
							
								dataStoreValue.put("Applicant-condoYesNo"+i,condoYesNo);
								dataStoreValue.put("Applicant-condoFees"+i,condofee);
								dataStoreValue.put("Applicant-rentalYesNo"+i,rentalYesNo);
						
								//showing all valuses : 
								log.debug("address : "+address+", mortgageYesNo : "+mortgageYesNo+", rentalYesNo : "
										+rentalYesNo+", condoYesNo : "+condoYesNo+", condofee : "+condofee+", sellingYesNo : "+sellingYesNo);


								//now mortgage and selling logic
								if(mortgageYesNo != null && mortgageYesNo.equalsIgnoreCase("yes") && sellingYesNo != null && sellingYesNo.equalsIgnoreCase("yes")){

									log.debug("both mortgagae and selling set to yes");

									int propertyconter=GenericHelperClass.getPropertyCounter();
									createLead.createApplicantMortgage(applicantID, address, propertyconter, sellingYesNo);
												if(condoYesNo != null && condoYesNo.equalsIgnoreCase("yes")){
										log.debug("condo values set to yes");
										createLead.createApplicantProperties(applicantID, address, condofee,propertyconter,sellingYesNo);
													}else{
										log.debug("condo values set to no");
										createLead.createApplicantProperties(applicantID, address, "-1",propertyconter,sellingYesNo);
												}

								}else if(mortgageYesNo != null && mortgageYesNo.equalsIgnoreCase("yes") && sellingYesNo != null && sellingYesNo.equalsIgnoreCase("no")){
									log.debug("when mortgage is true and selling is false");
									if(rentalYesNo3.equalsIgnoreCase("no") && rentalYesNo3 != null){
										int propertyconter=GenericHelperClass.getPropertyCounter();

										createLead.createApplicantMortgage(applicantID, address,propertyconter, sellingYesNo);
										log.debug("CouchBase");
										if(condoYesNo != null && condoYesNo1.equalsIgnoreCase("yes")){
											log.debug("condo values set to yes");
											createLead.createApplicantProperties(applicantID, address, condofee,propertyconter,sellingYesNo);
												}else{
											log.debug("condo values set to no");
											createLead.createApplicantProperties(applicantID, address, "-1",propertyconter,sellingYesNo);
											
										}
									}else{
										int propertyconter=GenericHelperClass.getPropertyCounter();
										log.debug("propertycounet value if rental is yes nad selling is no : "+propertyconter);
										createLead.createApplicantMortgageRental(applicantID, address,propertyconter, sellingYesNo,"1");


										log.debug("calling rental income");

										createLead.createIncomeApplicantRental(applicantID, 9, address3, "Landlord","12", false, true,propertyconter);

												if(condoYesNo != null && condoYesNo.equalsIgnoreCase("yes")){
											log.debug("condo values set to yes");
											createLead.createApplicantProperties(applicantID, address, condofee,propertyconter,sellingYesNo);
												}else{
											log.debug("condo values set to no");
											createLead.createApplicantProperties(applicantID, address, "-1",propertyconter,sellingYesNo);
													}
									}
								}else if(mortgageYesNo != null && mortgageYesNo.equalsIgnoreCase("no") && sellingYesNo != null && sellingYesNo.equalsIgnoreCase("yes")){
									log.debug("when mortgage is no and selling is true");
									int propertyconter=GenericHelperClass.getPropertyCounter();

									createLead.createApplicantMortgage(applicantID, address,propertyconter, sellingYesNo);
												if(condoYesNo != null && condoYesNo.equalsIgnoreCase("yes")){
										log.debug("condo values set to yes");
										createLead.createApplicantProperties(applicantID, address, condofee,propertyconter,sellingYesNo);
												}else{
										log.debug("condo values set to no");
										createLead.createApplicantProperties(applicantID, address, "-1",propertyconter,sellingYesNo);
												}
								}else{
									log.debug("when mortgage is no and selling is false");

									if(rentalYesNo3.equalsIgnoreCase("no") && rentalYesNo3 != null){
										int propertyconter=GenericHelperClass.getPropertyCounter();

										createLead.createApplicantMortgage(applicantID, address,propertyconter, sellingYesNo1);
														if(condoYesNo != null && condoYesNo.equalsIgnoreCase("yes")){
											log.debug("condo values set to yes");
											createLead.createApplicantProperties(applicantID, address, condoFees1,propertyconter,sellingYesNo);
													}else{
											log.debug("condo values set to no");
											createLead.createApplicantProperties(applicantID, address, "-1",propertyconter,sellingYesNo);
											
										}
									}else{
										int propertyconter=GenericHelperClass.getPropertyCounter();
										log.debug("propertycounet value if rental is yes nad selling is no : "+propertyconter);
										createLead.createApplicantMortgage(applicantID, address,propertyconter, sellingYesNo1);

										log.debug("calling rental income");

										createLead.createIncomeApplicantRental(applicantID, 9, address3, "Landlord","12",false,true,propertyconter);

											
										if(condoYesNo != null && condoYesNo.equalsIgnoreCase("yes")){
											log.debug("condo values set to yes");
											createLead.createApplicantProperties(applicantID, address, condoFees1,propertyconter,sellingYesNo);
																}else{
											log.debug("condo values set to no");
											createLead.createApplicantProperties(applicantID, address, "-1",propertyconter,sellingYesNo);
													
										}
									}
								}//end of else (mortgage and selling logic)
								//TODO couchbase operation
								i=i+1;

							}// end of while



						}else{
							log.debug("4. do you own additional properties? --> yes");
							String address4=req.getParameter("whatis144");//address4
							String mortgageYesNo4=req.getParameter("doesthe145");//mortgageYesNo4
							String rentalYesNo4=req.getParameter("doyou169");//rentalYesNo4
							String condoYesNo4=req.getParameter("doesthe170");//condoYesNo4
							String condoFees4=req.getParameter("howmuch171");//condoFees4
							String sellingYesNo4=req.getParameter("areyou157");//sellingYesNo4


							String additionalYesNo4=req.getParameter("doyou146"); // additionalYesNo4

							if(additionalYesNo4 !=null && additionalYesNo4.equalsIgnoreCase("no")){


								properti1 = new PropertyGrouping(address1,mortgageYesNo1,rentalYesNo1,condoYesNo1,condoFees1,sellingYesNo1,additionalYesNo1);
								properti2 = new PropertyGrouping(address2,mortgageYesNo2,rentalYesNo2,condoYesNo2,condoFees2,sellingYesNo2,additionalYesNo2);
								properti3 = new PropertyGrouping(address3,mortgageYesNo3,rentalYesNo3,condoYesNo3,condoFees3,sellingYesNo3,additionalYesNo3);
								properti4 = new PropertyGrouping(address4,mortgageYesNo4,rentalYesNo4,condoYesNo4,condoFees4,sellingYesNo4,additionalYesNo4);

								listOfProperties.add(properti1); 
								listOfProperties.add(properti2);
								listOfProperties.add(properti3);
								listOfProperties.add(properti4);
								Iterator iterate = listOfProperties.iterator();
								int i=1;
								while(iterate.hasNext()){
									log.debug("inside while loop with 4 values");
									PropertyGrouping property = (PropertyGrouping)iterate.next();

									// getting all properties stored in property Group
									String address = property.getAddress();
									String mortgageYesNo = property.getMortgageYesNo();
									String rentalYesNo = property.getRentalYesNo();
									String condoYesNo = property.getCondoYesNo();
									String condofee = property.getCondoFees();
									String sellingYesNo = property.getSellingYesNo();
									dataStoreValue.put("Applicant-address"+i,address);
									dataStoreValue.put("Applicant-mortgageYesNo"+i,mortgageYesNo);
								
									dataStoreValue.put("Applicant-sellingYesNo"+i,sellingYesNo);
								
									dataStoreValue.put("Applicant-condoYesNo"+i,condoYesNo);
									dataStoreValue.put("Applicant-condoFees"+i,condofee);
									dataStoreValue.put("Applicant-rentalYesNo"+i,rentalYesNo);
							
						
									//showing all valuses : 
									log.debug("address : "+address+", mortgageYesNo : "+mortgageYesNo+", rentalYesNo : "
											+rentalYesNo+", condoYesNo : "+condoYesNo+", condofee : "+condofee+", sellingYesNo : "+sellingYesNo);

									//now mortgage and selling logic
									if(mortgageYesNo != null && mortgageYesNo.equalsIgnoreCase("yes") && sellingYesNo != null && sellingYesNo.equalsIgnoreCase("yes")){

										log.debug("both mortgagae and selling set to yes");

										int propertyconter=GenericHelperClass.getPropertyCounter();
										createLead.createApplicantMortgage(applicantID, address, propertyconter, sellingYesNo);
												
										if(condoYesNo != null && condoYesNo.equalsIgnoreCase("yes")){
											log.debug("condo values set to yes");
											createLead.createApplicantProperties(applicantID, address, condofee,propertyconter,sellingYesNo);
																}else{
											log.debug("condo values set to no");
											createLead.createApplicantProperties(applicantID, address, "-1",propertyconter,sellingYesNo);

															}

									}else if(mortgageYesNo != null && mortgageYesNo.equalsIgnoreCase("yes") && sellingYesNo != null && sellingYesNo.equalsIgnoreCase("no")){
										log.debug("when mortgage is true and selling is false");

										//TODO add rental logic here, be careful

										if(rentalYesNo4.equalsIgnoreCase("no") && rentalYesNo4!=null){										
											int propertyconter=GenericHelperClass.getPropertyCounter();

											createLead.createApplicantMortgage(applicantID, address,propertyconter, sellingYesNo);
														if(condoYesNo != null && condoYesNo.equalsIgnoreCase("yes")){
												log.debug("condo values set to yes");
												createLead.createApplicantProperties(applicantID, address, condofee,propertyconter,sellingYesNo);
																}else{
												log.debug("condo values set to no");
												createLead.createApplicantProperties(applicantID, address, "-1",propertyconter,sellingYesNo);
												
											}

										}else{
											log.debug("inside Rental Logic 4");
											int propertyconter=GenericHelperClass.getPropertyCounter();
											log.debug("propertycounet value if rental is yes nad selling is no : "+propertyconter);

											createLead.createApplicantMortgageRental(applicantID,address,propertyconter, sellingYesNo, "1");
											log.debug("calling rental income");
											createLead.createIncomeApplicantRental(applicantID,9, address, "Landlord", "12", false,true,propertyconter);

															if(condoYesNo != null && condoYesNo.equalsIgnoreCase("yes")){
												log.debug("condo values set to yes");
												createLead.createApplicantProperties(applicantID, address, condofee,propertyconter,sellingYesNo);
																}else{
												log.debug("condo values set to no");
												createLead.createApplicantProperties(applicantID, address, "-1",propertyconter,sellingYesNo);
														
											}


										}

									}else if(mortgageYesNo != null && mortgageYesNo.equalsIgnoreCase("no") && sellingYesNo != null && sellingYesNo.equalsIgnoreCase("yes")){
										log.debug("when mortgage is no and selling is true");
										int propertyconter=GenericHelperClass.getPropertyCounter();

										createLead.createApplicantMortgage(applicantID, address,propertyconter, sellingYesNo);
													if(condoYesNo != null && condoYesNo.equalsIgnoreCase("yes")){
											log.debug("condo values set to yes");
											createLead.createApplicantProperties(applicantID, address, condofee,propertyconter,sellingYesNo);
															}else{
											log.debug("condo values set to no");
											createLead.createApplicantProperties(applicantID, address, "-1",propertyconter,sellingYesNo);
													}
									}else{
										log.debug("when mortgage is no and selling is false");
										//please add rental4 logic here
										if(rentalYesNo4.equalsIgnoreCase("no") && rentalYesNo4!=null){
											int propertyconter=GenericHelperClass.getPropertyCounter();

											createLead.createApplicantMortgage(applicantID, address,propertyconter, sellingYesNo1);
													if(condoYesNo != null && condoYesNo.equalsIgnoreCase("yes")){
												log.debug("condo values set to yes");
												createLead.createApplicantProperties(applicantID, address, condoFees1,propertyconter,sellingYesNo);
															}else{
												log.debug("condo values set to no");
												createLead.createApplicantProperties(applicantID, address, "-1",propertyconter,sellingYesNo);
															
											}
										}else{
											int propertyconter=GenericHelperClass.getPropertyCounter();
											log.debug("propertycounet value if rental is yes nad selling is no : "+propertyconter);

											createLead.createApplicantMortgageRental(applicantID, address,propertyconter, sellingYesNo, "1");
											log.debug("Logic for Rental Else block");
											log.debug("CouchBase");
											log.debug("calling rental income");

											createLead.createIncomeApplicantRental(applicantID,9,address,"Landlord","12",false,true,propertyconter );
										
														if(condoYesNo != null && condoYesNo.equalsIgnoreCase("yes")){
												log.debug("condo values set to yes");
												createLead.createApplicantProperties(applicantID, address, condoFees1,propertyconter,sellingYesNo);
													}else{
												log.debug("condo values set to no");
												createLead.createApplicantProperties(applicantID, address, "-1",propertyconter,sellingYesNo);
													
											}






										}
									}//end of else (mortgage and selling logic)
									//TODO couchbase operation


									i=i+1;


								}// end of while



							}else{
								log.debug("5. do you own additional properties? --> yes");
								String address5=req.getParameter("whatis147");//address5
								String mortgageYesNo5=req.getParameter("doesthe148");//mortgageYesNo5
								String rentalYesNo5=req.getParameter("doyou172");//rentalYesNo5
								String condoYesNo5=req.getParameter("doesthe173");//condoYesNo5
								String condoFees5=req.getParameter("howmuch174");//condoFees5
								String sellingYesNo5=req.getParameter("areyou158");//sellingYesNo5

								String additionalYesNo5=req.getParameter("doyou149"); // additionalYesNo5

								if(additionalYesNo5 !=null && additionalYesNo5.equalsIgnoreCase("no")){


									properti1 = new PropertyGrouping(address1,mortgageYesNo1,rentalYesNo1,condoYesNo1,condoFees1,sellingYesNo1,additionalYesNo1);
									properti2 = new PropertyGrouping(address2,mortgageYesNo2,rentalYesNo2,condoYesNo2,condoFees2,sellingYesNo2,additionalYesNo2);
									properti3 = new PropertyGrouping(address3,mortgageYesNo3,rentalYesNo3,condoYesNo3,condoFees3,sellingYesNo3,additionalYesNo3);
									properti4 = new PropertyGrouping(address4,mortgageYesNo4,rentalYesNo4,condoYesNo4,condoFees4,sellingYesNo4,additionalYesNo4);
									properti5 = new PropertyGrouping(address5,mortgageYesNo5,rentalYesNo5,condoYesNo5,condoFees5,sellingYesNo5,additionalYesNo5);

									listOfProperties.add(properti1); 
									listOfProperties.add(properti2);
									listOfProperties.add(properti3);
									listOfProperties.add(properti4);
									listOfProperties.add(properti5);

									Iterator iterate = listOfProperties.iterator();
									int i=1;
									while(iterate.hasNext()){
										log.debug("inside while loop with 5 values");
										PropertyGrouping property = (PropertyGrouping)iterate.next();

										// getting all properties stored in property Group
										String address = property.getAddress();
										String mortgageYesNo = property.getMortgageYesNo();
										String rentalYesNo = property.getRentalYesNo();
										String condoYesNo = property.getCondoYesNo();
										String condofee = property.getCondoFees();
										String sellingYesNo = property.getSellingYesNo();
										dataStoreValue.put("Applicant-address"+i,address);
										dataStoreValue.put("Applicant-mortgageYesNo"+i,mortgageYesNo);
									
										dataStoreValue.put("Applicant-sellingYesNo"+i,sellingYesNo);
									
										dataStoreValue.put("Applicant-condoYesNo"+i,condoYesNo);
										dataStoreValue.put("Applicant-condoFees"+i,condofee);
										dataStoreValue.put("Applicant-rentalYesNo"+i,rentalYesNo);
								
							
										//showing all valuses : 
										log.debug("address : "+address+", mortgageYesNo : "+mortgageYesNo+", rentalYesNo : "
												+rentalYesNo+", condoYesNo : "+condoYesNo+", condofee : "+condofee+", sellingYesNo : "+sellingYesNo);

										//now mortgage and selling logic
										if(mortgageYesNo != null && mortgageYesNo.equalsIgnoreCase("yes") && sellingYesNo != null && sellingYesNo.equalsIgnoreCase("yes")){

											log.debug("both mortgagae and selling set to yes");

											int propertyconter=GenericHelperClass.getPropertyCounter();
											createLead.createApplicantMortgage(applicantID, address, propertyconter, sellingYesNo);
														if(condoYesNo != null && condoYesNo.equalsIgnoreCase("yes")){
												log.debug("condo values set to yes");
												createLead.createApplicantProperties(applicantID, address, condofee,propertyconter,sellingYesNo);
											}else{
												log.debug("condo values set to no");
												createLead.createApplicantProperties(applicantID, address, "-1",propertyconter,sellingYesNo);

															}

										}else if(mortgageYesNo != null && mortgageYesNo.equalsIgnoreCase("yes") && sellingYesNo != null && sellingYesNo.equalsIgnoreCase("no")){
											log.debug("when mortgage is true and selling is false");
											//TODO rental 5 logic here



											if(rentalYesNo5.equalsIgnoreCase("no") && rentalYesNo5!=null){
												int propertyconter=GenericHelperClass.getPropertyCounter();

												createLead.createApplicantMortgage(applicantID, address,propertyconter, sellingYesNo);
																if(condoYesNo != null && condoYesNo.equalsIgnoreCase("yes")){
													log.debug("condo values set to yes");
													createLead.createApplicantProperties(applicantID, address, condofee,propertyconter,sellingYesNo);
														}else{
													log.debug("condo values set to no");
													createLead.createApplicantProperties(applicantID, address, "-1",propertyconter,sellingYesNo);

												}
											}else{
												int propertyconter=GenericHelperClass.getPropertyCounter();
												log.debug("propertycounet value if rental is yes nad selling is no : "+propertyconter);

												createLead.createApplicantMortgageRental(applicantID, address,propertyconter, sellingYesNo1,"1");



												log.debug("CouchBase");

												log.debug("calling rental income");

												createLead.createIncomeApplicantRental(applicantID,9,address,"Landlord","12",false,true,propertyconter);



														if(condoYesNo != null && condoYesNo.equalsIgnoreCase("yes")){
													log.debug("condo values set to yes");
													createLead.createApplicantProperties(applicantID, address, condofee,propertyconter,sellingYesNo);
														}else{
													log.debug("condo values set to no");
													createLead.createApplicantProperties(applicantID, address, "-1",propertyconter,sellingYesNo);

														}











											}



										}else if(mortgageYesNo != null && mortgageYesNo.equalsIgnoreCase("no") && sellingYesNo != null && sellingYesNo.equalsIgnoreCase("yes")){
											log.debug("when mortgage is no and selling is true");
											int propertyconter=GenericHelperClass.getPropertyCounter();

											createLead.createApplicantMortgage(applicantID, address,propertyconter, sellingYesNo);
												if(condoYesNo != null && condoYesNo.equalsIgnoreCase("yes")){
												log.debug("condo values set to yes");
												createLead.createApplicantProperties(applicantID, address, condofee,propertyconter,sellingYesNo);
											}else{
												log.debug("condo values set to no");
												createLead.createApplicantProperties(applicantID, address, "-1",propertyconter,sellingYesNo);
												}
										}else{
											log.debug("when mortgage is no and selling is false");
											//TODO renatal 5 logic here

											if(rentalYesNo5.equalsIgnoreCase("no") && rentalYesNo5!=null){
												int propertyconter=GenericHelperClass.getPropertyCounter();

												createLead.createApplicantMortgage(applicantID, address,propertyconter, sellingYesNo1);
													if(condoYesNo != null && condoYesNo.equalsIgnoreCase("yes")){
													log.debug("condo values set to yes");
													createLead.createApplicantProperties(applicantID, address, condoFees1,propertyconter,sellingYesNo);
														}else{
													log.debug("condo values set to no");
													createLead.createApplicantProperties(applicantID, address, "-1",propertyconter,sellingYesNo);
																									}

											}else{
												int propertyconter=GenericHelperClass.getPropertyCounter();
												log.debug("propertycounet value if rental is yes nad selling is no : "+propertyconter);
												createLead.createApplicantMortgageRental(applicantID, address,propertyconter, sellingYesNo1,"1");												


												log.debug("CouchBase");
												log.debug("calling rental income");

												createLead.createIncomeApplicantRental(applicantID,9,address,"Landlord","12",false,true ,propertyconter);
														if(condoYesNo != null && condoYesNo.equalsIgnoreCase("yes")){
													log.debug("condo values set to yes");
													createLead.createApplicantProperties(applicantID, address, condoFees1,propertyconter,sellingYesNo);
														}else{
													log.debug("condo values set to no");
													createLead.createApplicantProperties(applicantID, address, "-1",propertyconter,sellingYesNo);
													}








											}//end of loop 5 Rental




										}//end of else (mortgage and selling logic)
										//TODO couchbase operation


									}// end of while



								}else {

									log.debug("6. do you own additional properties? --> yes");
									String address6=req.getParameter("whatis150");//address6
									String mortgageYesNo6=req.getParameter("doesthe151");//mortgageYesNo6
									String rentalYesNo6=req.getParameter("doyou175");//rentalYesNo6
									String condoYesNo6=req.getParameter("doesthe176");//condoYesNo6
									String condoFees6=req.getParameter("howmuch177");//condoFees6
									String sellingYesNo6=req.getParameter("areyou159");//sellingYesNo6





									properti1 = new PropertyGrouping(address1,mortgageYesNo1,rentalYesNo1,condoYesNo1,condoFees1,sellingYesNo1,additionalYesNo1);
									properti2 = new PropertyGrouping(address2,mortgageYesNo2,rentalYesNo2,condoYesNo2,condoFees2,sellingYesNo2,additionalYesNo2);
									properti3 = new PropertyGrouping(address3,mortgageYesNo3,rentalYesNo3,condoYesNo3,condoFees3,sellingYesNo3,additionalYesNo3);
									properti4 = new PropertyGrouping(address4,mortgageYesNo4,rentalYesNo4,condoYesNo4,condoFees4,sellingYesNo4,additionalYesNo4);
									properti5 = new PropertyGrouping(address5,mortgageYesNo5,rentalYesNo5,condoYesNo5,condoFees5,sellingYesNo5,additionalYesNo5);
									properti6 = new PropertyGrouping(address6,mortgageYesNo6,rentalYesNo6,condoYesNo6,condoFees6,sellingYesNo6);
									listOfProperties.add(properti1); 
									listOfProperties.add(properti2);
									listOfProperties.add(properti3);
									listOfProperties.add(properti4);
									listOfProperties.add(properti5);
									listOfProperties.add(properti6);

									Iterator iterate = listOfProperties.iterator();
									int i=1;
									while(iterate.hasNext()){
										log.debug("inside while loop with 6 values");
										PropertyGrouping property = (PropertyGrouping)iterate.next();

										// getting all properties stored in property Group
										String address = property.getAddress();
										String mortgageYesNo = property.getMortgageYesNo();
										String rentalYesNo = property.getRentalYesNo();
										String condoYesNo = property.getCondoYesNo();
										String condofee = property.getCondoFees();
										String sellingYesNo = property.getSellingYesNo();
										dataStoreValue.put("Applicant-address"+i,address);
										dataStoreValue.put("Applicant-mortgageYesNo"+i,mortgageYesNo);
									
										dataStoreValue.put("Applicant-sellingYesNo"+i,sellingYesNo);
									
										dataStoreValue.put("Applicant-condoYesNo"+i,condoYesNo);
										dataStoreValue.put("Applicant-condoFees"+i,condofee);
										dataStoreValue.put("Applicant-rentalYesNo"+i,rentalYesNo);
								
							
										//showing all valuses : 
										log.debug("address : "+address+", mortgageYesNo : "+mortgageYesNo+", rentalYesNo : "
												+rentalYesNo+", condoYesNo : "+condoYesNo+", condofee : "+condofee+", sellingYesNo : "+sellingYesNo);

										//now mortgage and selling logic
										if(mortgageYesNo != null && mortgageYesNo.equalsIgnoreCase("yes") && sellingYesNo != null && sellingYesNo.equalsIgnoreCase("yes")){

											log.debug("both mortgagae and selling set to yes");

											int propertyconter=GenericHelperClass.getPropertyCounter();
											createLead.createApplicantMortgage(applicantID, address, propertyconter, sellingYesNo);
												if(condoYesNo != null && condoYesNo.equalsIgnoreCase("yes")){
												log.debug("condo values set to yes");
												createLead.createApplicantProperties(applicantID, address, condofee,propertyconter,sellingYesNo);
																							}else{
												log.debug("condo values set to no");
												createLead.createApplicantProperties(applicantID, address, "-1",propertyconter,sellingYesNo);

												}

										}else if(mortgageYesNo != null && mortgageYesNo.equalsIgnoreCase("yes") && sellingYesNo != null && sellingYesNo.equalsIgnoreCase("no")){
											log.debug("when mortgage is true and selling is false");
											//TODO rental 5 logic here



											if(rentalYesNo6.equalsIgnoreCase("no") && rentalYesNo6!=null){
												int propertyconter=GenericHelperClass.getPropertyCounter();

												createLead.createApplicantMortgage(applicantID, address,propertyconter, sellingYesNo);
												log.debug("CouchBase");
													if(condoYesNo != null && condoYesNo.equalsIgnoreCase("yes")){
													log.debug("condo values set to yes");
													createLead.createApplicantProperties(applicantID, address, condofee,propertyconter,sellingYesNo);
														}else{
													log.debug("condo values set to no");
													createLead.createApplicantProperties(applicantID, address, "-1",propertyconter,sellingYesNo);

														}
											}else{
												int propertyconter=GenericHelperClass.getPropertyCounter();
												log.debug("propertycounet value if rental is yes nad selling is no : "+propertyconter);

												createLead.createApplicantMortgageRental(applicantID, address,propertyconter, sellingYesNo1,"1");



												log.debug("CouchBase");

												log.debug("calling rental income");

												createLead.createIncomeApplicantRental(applicantID,9,address,"Landlord","12",false,true ,propertyconter);



													if(condoYesNo != null && condoYesNo.equalsIgnoreCase("yes")){
													log.debug("condo values set to yes");
													createLead.createApplicantProperties(applicantID, address, condofee,propertyconter,sellingYesNo);
													}else{
													log.debug("condo values set to no");
													createLead.createApplicantProperties(applicantID, address, "-1",propertyconter,sellingYesNo);

														}











											}



										}else if(mortgageYesNo != null && mortgageYesNo.equalsIgnoreCase("no") && sellingYesNo != null && sellingYesNo.equalsIgnoreCase("yes")){
											log.debug("when mortgage is no and selling is true");
											int propertyconter=GenericHelperClass.getPropertyCounter();

											createLead.createApplicantMortgage(applicantID, address,propertyconter, sellingYesNo);
											if(condoYesNo != null && condoYesNo.equalsIgnoreCase("yes")){
												log.debug("condo values set to yes");
												createLead.createApplicantProperties(applicantID, address, condofee,propertyconter,sellingYesNo);
											}else{
												log.debug("condo values set to no");
												createLead.createApplicantProperties(applicantID, address, "-1",propertyconter,sellingYesNo);
												}
										}else{
											log.debug("when mortgage is no and selling is false");
											//TODO renatal 5 logic here

											if(rentalYesNo6.equalsIgnoreCase("no") && rentalYesNo6!=null){
												int propertyconter=GenericHelperClass.getPropertyCounter();

												createLead.createApplicantMortgage(applicantID, address,propertyconter, sellingYesNo1);
													if(condoYesNo != null && condoYesNo.equalsIgnoreCase("yes")){
													log.debug("condo values set to yes");
													createLead.createApplicantProperties(applicantID, address, condoFees1,propertyconter,sellingYesNo);
															}else{
													log.debug("condo values set to no");
													createLead.createApplicantProperties(applicantID, address, "-1",propertyconter,sellingYesNo);
															}

											}else{
												int propertyconter=GenericHelperClass.getPropertyCounter();
												log.debug("propertycounet value if rental is yes nad selling is no : "+propertyconter);
												createLead.createApplicantMortgageRental(applicantID, address,propertyconter, sellingYesNo1,"1");												


												log.debug("CouchBase");
												log.debug("calling rental income");

												createLead.createIncomeApplicantRental(applicantID,9,address,"Landlord","12",false,true ,propertyconter);
												if(condoYesNo != null && condoYesNo.equalsIgnoreCase("yes")){
													log.debug("condo values set to yes");
													createLead.createApplicantProperties(applicantID, address, condoFees1,propertyconter,sellingYesNo);
													}else{
													log.debug("condo values set to no");
													createLead.createApplicantProperties(applicantID, address, "-1",propertyconter,sellingYesNo);
													}








											}//end of loop 6 Rental




										}//end of else (mortgage and selling logic)
										//TODO couchbase operation

i=i+1;
									}// end of while







								}//6. you own additional properties ? -->
							}//5. do you own additional properties? --> yes
						}//4. do you own additional properties? --> yes
					}//3. do you own additional properties? --> yes

				}//2. do you own additional properties? --> yes




			}//1. do you own additional properties?--> yes
CouchBaseOperation appendData=new CouchBaseOperation();
			
			dataStoreValue.put("Applicant-subForm-3", subForm);
			
			appendData.appendDataInCouchBase("Applicant_"+applicantID, dataStoreValue);
		
			   req.setAttribute("uniid",uniid);
			//req.setAttribute("applicantId",applicantId);
			req.getRequestDispatcher("MortgageApplication4.jsp").include(req, res);
			//res.sendRedirect("http://form.jotformpro.com/form/50265051738958?unniid="+form1UniqueId1+"&applicantId="+applicantID);

			}else{
				req.setAttribute("message", " We are sorry, but it seems the security and reliability of your internet connection may have been weakened.  To protect your identity and the security of your information, can you please submit this application again");
				req.getRequestDispatcher("MortgageApplicationSucess.jsp").forward(req, res);
				
			}

		}catch(Exception e){
			log.error("error in service : "+e);
		}

	}





}