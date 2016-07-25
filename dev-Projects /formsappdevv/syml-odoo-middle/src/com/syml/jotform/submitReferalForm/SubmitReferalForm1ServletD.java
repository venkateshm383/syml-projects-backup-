package com.syml.jotform.submitReferalForm;
import java.io.IOException;
import java.io.PrintWriter;
//import java.nio.channels.SeekableByteChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import org.apache.catalina.connector.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.syml.couchbase.CouchBaseOperation;
import com.syml.jotform.createform.SubmitReferalForm2;
import com.syml.openerp.CheckReferalResource;
public class SubmitReferalForm1ServletD extends HttpServlet{

	
			@Override
			protected void service(HttpServletRequest req, HttpServletResponse res)
					throws ServletException, IOException {
			 	Logger log = LoggerFactory.getLogger(this.getClass());
			 	UUID uuid = UUID.randomUUID();

			 

			 	String uniqueId = uuid.toString();
			 			try{
				 	
				 	log.debug("inside service method of SubmitReferalForm1Servlet");
					
				String fname=req.getParameter("q4_firstname").trim();
					String lname=req.getParameter("q5_yourLast").trim();
							//req.getParameter("q5_lastname");
					String email=req.getParameter("q6_email6").toLowerCase().trim();
					
					String formType ="Submit Referral";
					
					/*DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					//get current date time with Calendar()
					   Calendar cal = Calendar.getInstance();
					  String currentDateTime=(dateFormat.format(cal.getTime()));
					*/
					  
					log.debug("firstname  is : " + fname + "\temail is : "
								+ email + "\tLastname is : " + lname + "\t unique id : "
								+ uniqueId);
					  
					
					//put variable into session
					
					HttpSession session=req.getSession(true);
					session.setAttribute("form1Fname", fname);
					session.setAttribute("form1Lname",lname);
					session.setAttribute("form1email",email);
					session.setAttribute("form1uniqueId",uniqueId);
					
					CheckReferalResource referalResource=new CheckReferalResource();
					ArrayList<String> sucessList=null;
					sucessList=referalResource.findReferralSourceCode(fname, lname, email);
							

					int code =0;
					int referralId=0;
					String phoneNumber="";
					try{try{
						code=Integer.parseInt( sucessList.get(1));
						
					}catch(Exception e){
						
					}
					
					try{
					referralId=Integer.parseInt(sucessList.get(2));
					}catch(Exception e){	
						
					}
					try{
					phoneNumber=sucessList.get(2);
					}catch(Exception e){
						
					}
					}catch(Exception e){
						
					}

					String message="";
					HashMap dataStoreValue=new HashMap();
					dataStoreValue.put("Referal_Source_FirstName",fname);
					dataStoreValue.put("Referal_Source_LastName",lname);
					dataStoreValue.put("Referal_Source_Email",email.toLowerCase());
					dataStoreValue.put("uniqueid",uniqueId);
					String key=formType+"_"+uniqueId;
					CouchBaseOperation client=new CouchBaseOperation();
					client.storeDataInCouchBase(key, formType, dataStoreValue);
					
					if(code==2){
						
						//PrintWriter pw=res.getWriter();
						//pw.print("We are have found your email address in our records.  However, it is associated with a different name.  Is the above first name and last name the same one you used when you became involved in Visdom's Referral Program?  If not, please send an email to referrals@visdom.ca with your current contact information so we can ensure the appropriate referral fee is paid to you and we adjust our records accordingly.");
						message="Thank you for verifying your involvement in Visdom's Referral Program. We have sent an email to you confirming the beginning of the mortgage application process for your referral.  In the event you did not receive it, please check the spam folder and mark all emails from Visdom as Not Spam.  Thank You.";	
					}else if(code==1){
						
						message="Thank you for verifying your involvement in Visdom's Referral Program. We have sent an email to you confirming the beginning of the mortgage application process for your referral.  In the event you did not receive it, please check the spam folder and mark all emails from Visdom as Not Spam.  Thank You.";
						log.debug("msg from open erp is "+message);
						//call jotform api to create a form 
						//SubmitReferalForm2 myform=new SubmitReferalForm2();
						
						
						//String url=myform.createMyForm(msg);
						//myform.sendMessage(message);
						
						//res.sendRedirect("http://form.jotformpro.com/form/50234255984963?uniid="+uniqueId);				
						
						//redirect to clone form for amazon usage
						//res.sendRedirect("http://form.jotform.me/form/50332184392451?uniid="+uniqueId);

						
					}else{
						
						//PrintWriter pw=res.getWriter();
						//pw.print("We are having difficulty finding your email address in our records.  Is the above email address the one your used when you became involved in Visdom's Referral Program?  If not, please change the email address above to the email address you used when you entered the Visdom Referral Program.  In the event your email address has changed, once you complete this form, please send an email to referrals@visdom.ca with your new email address and contact information so we can ensure the appropriate referral fee is paid to you and we adjust our records accordingly.");
						
						message="Your email address does not match our records.  Please confirm email address provided when you became a involved in Visdoms Referral Program.  If your email address has changed, please send an email to referrals@visdom.ca with all your correct contact information so we can adjust our records accordingly and pay appropriate referral fees.";
					}
				
					
					session.setAttribute("referralIdFound",referralId+"");
					session.setAttribute("phoneNumber",phoneNumber);

					session.setAttribute("submit_referral_message", message);
					RequestDispatcher rd=req.getRequestDispatcher("SubmitReferalForm2D.jsp");  
				     //res.sendRedirect("http://107.23.130.227/visdom/final/SubmitReferalFrom2D");
				   rd.forward(req,res);
					//res.sendRedirect("http://107.23.130.227/visdom/final/SubmitReferalFrom2D");				
					//res.sendRedirect(" Submit Referal Form2D.html");
					
				}catch(Exception e){
					log.error("error in service : "+e);
				}
				
			}
			
		
	
}