package com.syml.jotform.visdomReferrelResoursce;



import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;
import net.iharder.Base64;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDPixelMap;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import com.couchbase.client.CouchbaseClient;
import com.sendwithus.SendWithUs;
import com.sendwithus.SendWithUsExample;
import com.sendwithus.exception.SendWithUsException;
import com.sendwithus.model.SendReceipt;
import com.syml.constants.SymlConstant;
import com.syml.couchbase.CouchBaseOperation;
import com.syml.helper.GenericHelperClass;
import com.syml.mail.vidomReferralResoursceMailTemplate.ReferralInstructionsMessageTemplate;
import com.syml.openerp.RestCallClass;
import com.syml.pdfGeneration.PdfTableCreation;
import com.syml.pdfGeneration.VisdomReferralPdfGeneration;



public class ThirdVisdomReferralFomServlet extends HttpServlet  {

	static Logger log = LoggerFactory
			.getLogger(ThirdVisdomReferralFomServlet.class);
	VisdomReferralPdfGeneration visdom=new VisdomReferralPdfGeneration();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HashMap referrelData = null;
		String uniqueId = req.getParameter("uniqueid");
		String referrelId = req.getParameter("referrelid");
		String message = req.getParameter("message");
		String smartPhoneType = req.getParameter("q35_whichType");
		HttpSession session = req.getSession(true);
		SendWithUsExample sendwithus=new SendWithUsExample();
	
		String oldUniqueid = (String) session.getAttribute("uniqueId");
		String pdffilepath="";
		BufferedImage image=null;
		String role="";
		String referralName="";

		try {
			referrelData = (HashMap) session
					.getAttribute("visdomreferreldata");
			referralName=referrelData.get("firstname").toString();
		} catch (Exception e) {
			log.error(e.toString());

		}
		role=(String)session.getAttribute("role");
		if(role==null){
			role="";
		}
		String formType = "Visdom_Referrel";
		/*if(referrelId==""||referrelId==null||smartPhoneType==""||smartPhoneType==null){
			resp.getWriter().print("Please fill the  Form Once  agian data was not filled Properly");
			resp.sendRedirect("http://form.jotformpro.com/form/50335714173955");
		}else{*/
			
			log.info("uniqueId " + uniqueId + "referrelId " + referrelId
					+ "  message " + message + "\noldUniqueid\n " + oldUniqueid
					+ "smartPhoneType " + smartPhoneType  +"role "+role);
		// checking the unique id and old uniqueid are equal
		if (uniqueId.equals(uniqueId)) {
			log.debug("both forms are filled by same person");
			log.info("uniqueId " + uniqueId + "referrelId " + referrelId
					+ "  message " + message + "\noldUniqueid\n " + oldUniqueid
					+ "smartPhoneType " + smartPhoneType);
			
			DateFormat dateFormat = new SimpleDateFormat(
					"yyyy/MM/dd HH:mm:ss");
			
			String name=(String)referrelData.get("firstname");
			String lastname=name+" "+ (String)referrelData.get("lastName");
			String email=(String)referrelData.get("email");
			String phoneNumber=(String)referrelData.get("phoneNumber");


			//String pdffilepath=(String)session.getAttribute("pdfpath");
			//log.debug("pdfpath "+ pdffilepath);
			SymlConstant sc = new SymlConstant();
			// get current date time with Calendar()
			Calendar cal = Calendar.getInstance();
			String currentDateTime = (dateFormat.format(cal.getTime()));
			String sign="";
			String  areUsingtouchScreenDevice=(String)session.getAttribute("areUsingtouchScreenDevice");
			// we pass the file to the output stream
			if (areUsingtouchScreenDevice.equals("Yes")) {
				sign = (String)session.getAttribute("sign");
				log.debug(" elecotronic signature  is "+sign );
				
				 String newString = sign.substring(22);
			    	// System.out.println(newString);
			    			
			     byte[] decodedBytes = Base64.decode(newString);
if (decodedBytes == null) {
		        	 log.debug("decodedBytes  is null");
		          }
		 
		 image = ImageIO.read(new ByteArrayInputStream(decodedBytes));
				
			pdffilepath=VisdomReferralPdfGenerationMethod(referralName,areUsingtouchScreenDevice, "", role, image);
			} else {
				sign =(String)session.getAttribute("sign");
				log.debug("typed signature  is "+sign );
				
				pdffilepath=VisdomReferralPdfGenerationMethod(referralName,areUsingtouchScreenDevice, sign, role, image);

			}

			File fl=new File(pdffilepath);
			System.out.println("file exissts------------------ "+fl.exists()+" file size----------------------->"+(fl.length()/1024));

			
			
			
			

			
			
			// get ip of latest form sumitted
			String ip="";
			ip=req.getRemoteAddr();
		
			  Path path = Paths.get(pdffilepath);
			  byte[] data1 = Files.readAllBytes(path);
			  
				File file = new File(pdffilepath ); //we create a file
			//    Base64Encoder encoder = new Base64Encoder();
			  
				CouchBaseOperation storeData = new CouchBaseOperation();

				OutputStream outputStream = new FileOutputStream( file ); 
				 HashMap hashdata=new HashMap(); 	
				 hashdata.put("ReferralAgreemetfile",data1);
				 storeData.storeDataInCouchBase("ReferralAgreemetfile_"+ referrelId, "visdomReferral", hashdata);
				 referrelData.put("ReferralAgreemetfile_id","ReferralAgreemetfile_"+ referrelId);
				 referrelData.put("Submission_Date_Time", currentDateTime);
				referrelData.put("smart_Phone_Type", smartPhoneType);
				referrelData.put("ip", ip);
			

				
			log.debug("Email of the Referral "+ email);
			// create a unique key for storing data into couchbase
			String key = formType+"_"+ referrelId;
			
			// cate Store data to couchbase
		
			
			System.out.println("file path ----------"+pdffilepath);

			//	RequestDispatcher rd=req.getRequestDispatcher("visdomReferralSucuss.jsp");
				
// checking referrer is exsist or not
			if (message != "") {
				log.debug("Referrel Is Already Exsist");
				
				String data=null;
				try{
					CouchbaseClient client=storeData.getConnectionToCouchBase();
						data=(String)client.get(key);
						client.shutdown();
				}catch(Exception e){
					
				}

				
				if(data==null){
					
			
			
					org.json.JSONObject jsonObject=	storeData.getData(key);
					if(jsonObject!=null){
						new GenericHelperClass().createNote2(new Integer(referrelId), jsonObject.toString(), "Referral Source Json Data", "hr.applicant");
					}
					
					storeData.storeDataInCouchBase(key, formType, referrelData);
					log.info("Referrer data is inserted into couchdatabase ");
					JSONObject jsonTableData=new JSONObject();
					try {
						jsonTableData.put("name", lastname);
						jsonTableData.put("phonenumber", phoneNumber);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    	
			//new	RestCallClass(jsonTableData.toString()).start();

			
					//ReferralInstructionsMessageTemplate.ReferralInstructions(name,email,smartPhoneType,new Integer(referrelId),"hr.applicant",pdffilepath);
					if (areUsingtouchScreenDevice.equals("Yes")) {
						sign = (String)session.getAttribute("sign");
						log.debug(" elecotronic signature  is "+sign );
						
						 String newString = sign.substring(22);
					    	// System.out.println(newString);
					    			
					  
					    byte[] decodedBytes = Base64.decode(newString);
				        
				     
				        if (decodedBytes == null) {
				        	 log.debug("decodedBytes  is null");
				          }
				 
				 image = ImageIO.read(new ByteArrayInputStream(decodedBytes));
						
					pdffilepath=VisdomReferralPdfGenerationMethod(referralName,areUsingtouchScreenDevice, "", role, image);
					} else {
						sign =(String)session.getAttribute("sign");
						log.debug("typed signature  is "+sign );
						
						pdffilepath=VisdomReferralPdfGenerationMethod(referralName,areUsingtouchScreenDevice, sign, role, image);

					}
					if(smartPhoneType.equalsIgnoreCase("iPhone")){
						
					sendwithus.sendTOReferralSourceIphone(name, "https://dev-formsapp-v1.visdom.ca/formsapp/SubmitReferalform1D.html", email,pdffilepath);
					}else if(smartPhoneType.equalsIgnoreCase("Android")){
						sendwithus.sendTOReferralSourceAndroid(name, "https://dev-formsapp-v1.visdom.ca/formsapp/SubmitReferalform1D.html", email,pdffilepath);
					
				}else{
					sendwithus.sendTOReferralSourceNiether(name, "https://dev-formsapp-v1.visdom.ca/formsapp/SubmitReferalform1D.html", email,pdffilepath);
					
			}
					req.setAttribute("message", " Thank you for becoming involved in the Visdom Referral Program. We have sent a copy of the referral agreement to the email provided. In the event you did not see it please check your spam folder in case your provider accidentally miscategorized it");
					req.getRequestDispatcher("visdomReferralSucuss.jsp").include(req, resp);
					
				}else{
					storeData.editDataInCouchBase(key,formType ,referrelData);
					//resp.getWriter().print("Data is  updated Successfully  ");
				//	ReferralInstructionsMessageTemplate.ReferralInstructions(name,email,smartPhoneType,new Integer(referrelId),"hr.applicant",pdffilepath);

					req.setAttribute("message", "Referral details is already exist so details  updated successfully" );
					//ReferralInstructionsMessageTemplate.ReferralInstructions(name,email,smartPhoneType,new Integer(referrelId),"hr.applicant",pdffilepath);
					
					if (areUsingtouchScreenDevice.equals("Yes")) {
						sign = (String)session.getAttribute("sign");
						log.debug(" elecotronic signature  is "+sign );
						
						 String newString = sign.substring(22);
					    	// System.out.println(newString);
					    			
					  
					    byte[] decodedBytes = Base64.decode(newString);
				     
				        if (decodedBytes == null) {
				        	 log.debug("decodedBytes  is null");
				          }
				 
				 image = ImageIO.read(new ByteArrayInputStream(decodedBytes));
						
					pdffilepath=VisdomReferralPdfGenerationMethod(referralName,areUsingtouchScreenDevice, "", role, image);
					} else {
						sign =(String)session.getAttribute("sign");
						log.debug("typed signature  is "+sign );
						
						pdffilepath=VisdomReferralPdfGenerationMethod(referralName,areUsingtouchScreenDevice, sign, role, image);

					}
						if(smartPhoneType.equalsIgnoreCase("iPhone")){
						sendwithus.sendTOReferralSourceIphone(name, "https://dev-formsapp-v1.visdom.ca/formsapp/SubmitReferalform1D.html", email,pdffilepath);
						}else if(smartPhoneType.equalsIgnoreCase("Android")){
							sendwithus.sendTOReferralSourceAndroid(name, "https://dev-formsapp-v1.visdom.ca/formsapp/SubmitReferalform1D.html", email,pdffilepath);
						
					}else{
						sendwithus.sendTOReferralSourceNiether(name, "https://dev-formsapp-v1.visdom.ca/formsapp/SubmitReferalform1D.html", email,pdffilepath);
						
					}
					req.getRequestDispatcher("visdomReferralSucuss.jsp").include(req, resp);
					///rd.forward(req, resp);
				}
				

			} else if (message == "") {
				
				
				storeData.editDataInCouchBase(key, formType, referrelData);
				log.info("Referrer data is inserted into couchdatabase ");
				//ReferralInstructionsMessageTemplate.ReferralInstructions(name,email,smartPhoneType,new Integer(referrelId),"hr.applicant",pdffilepath);

				JSONObject jsonTableData=new JSONObject();
				try {
					jsonTableData.put("name", lastname);
					jsonTableData.put("phonenumber", phoneNumber);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	
							//new	RestCallClass(jsonTableData.toString()).start();

		
				org.json.JSONObject jsonObject=	storeData.getData(key);
				if(jsonObject!=null){
					new GenericHelperClass().createNote2(new Integer(referrelId), jsonObject.toString(), "Referral Source Json Data", "hr.applicant");
				}
				//ReferralInstructionsMessageTemplate.ReferralInstructions(name,email,smartPhoneType,new Integer(referrelId),"hr.applicant");
				if (areUsingtouchScreenDevice.equals("Yes")) {
					sign = (String)session.getAttribute("sign");
					log.debug(" elecotronic signature  is "+sign );
					
					 String newString = sign.substring(22);
				    	// System.out.println(newString);
				    			
				  
				      byte[] decodedBytes = Base64.decode(newString);
			        
			        
			     
			        if (decodedBytes == null) {
			        	 log.debug("decodedBytes  is null");
			          }
			 
			 image = ImageIO.read(new ByteArrayInputStream(decodedBytes));
					
				pdffilepath=VisdomReferralPdfGenerationMethod(referralName,areUsingtouchScreenDevice, "", role, image);
				} else {
					sign =(String)session.getAttribute("sign");
					log.debug("typed signature  is "+sign );
					
					pdffilepath=VisdomReferralPdfGenerationMethod(referralName,areUsingtouchScreenDevice, sign, role, image);

				}
					if(smartPhoneType.equalsIgnoreCase("iPhone")){
					sendwithus.sendTOReferralSourceIphone(name, "https://dev-formsapp.visdom.ca/formsapp/SubmitReferalform1D.html", email,pdffilepath);
				
					
									      
					   
					
					}else if(smartPhoneType.equalsIgnoreCase("Android")){
						sendwithus.sendTOReferralSourceAndroid(name, "https://dev-formsapp.visdom.ca/formsapp/SubmitReferalform1D.html", email,pdffilepath);
					}else{
						sendwithus.sendTOReferralSourceNiether(name, "https://dev-formsapp.visdom.ca/formsapp/SubmitReferalform1D.html", email,pdffilepath);
						
					}
				
				
				try{
					File file2=new File(pdffilepath);
					file2.delete();
					log.debug("pdf file deleted   -----------");
					}catch(Exception e){
						
					}
				
				req.setAttribute("message", " Thank you for becoming involved in the Visdom Referral Program. We have sent a copy of the referral agreement to the email provided. In the event you did not see it please check your spam folder in case your provider accidentally miscategorized it");
				req.getRequestDispatcher("visdomReferralSucuss.jsp").include(req, resp);
					/*req.setAttribute("message1", " Close the tab you completed Visdom Referral Form");
					req.setAttribute("messag2", " Thank you for becoming involved in the Visdom Referral Program. We have sent a copy of the referral agreement to the email provided. In the event you did not see it please check your spam folder in case your provider accidentally miscategorized it");
					
					
					req.getRequestDispatcher("VisdomReferralSource3.jsp").include(req, resp);*/
			}
		
		

		} else {

			String  message1="We found problem while filling the form . It seems the previous form was not completed. This may be because of internet quality or session problems. "+
					"Please open this link (https://dev-formsapp.visdom.ca/formsapp/SubmitReferalform1D.html) in a new browser tab, close this tab and fill provide us with your information.";
			req.setAttribute("message", message1);
			req.getRequestDispatcher("visdomReferralSucuss.jsp").forward(req, resp);
			
			
		}
		}
/*	}*/
	
	public static void main(String[] args) throws IOException {
		String path=new VisdomReferralPdfGeneration().VisdomReferralPdfGenerationMethod("test", "no", "test", "other", null);
			System.out.println(path);
			new SendWithUsExample().sendTOReferralSourceAndroid("tset", "https://dev-formsapp.visdom.ca/formsapp/SubmitReferalform1D.html", "venkatesh.m@bizruntime.com",path);
	
		}
	
	
	
	public  String  VisdomReferralPdfGenerationMethod(String referralName,String areusingTouchScreenDevice,String myTypedName,String role,BufferedImage image ){
		
		
		 SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		 
		 
		  GregorianCalendar calendar=(GregorianCalendar) GregorianCalendar.getInstance();

		UUID uuid=UUID.randomUUID();
		log.debug("insdie Referreral  Agreement  Pdf generation");
		String file=  "/home/venkateshm/Desktop/";
/*	String outputFileName=file+"Referral_Agreement_" +referralName+"_"+format.format(calendar.getTime())+".pdf";
*/		GenericHelperClass genericHelperClass=new GenericHelperClass();
		String outputFileName = genericHelperClass.readConfigfile().getProperty("pdfPath")+"RerralAgreement_" + referralName +"_"+format.format(calendar.getTime())+".pdf";
	
		 Properties prop = new Properties();
	 		try {

	 		prop.load(VisdomReferralPdfGeneration.class.getClassLoader().getResourceAsStream("visdomReferral.properties"));
	 		System.out.println("referralName "+ referralName  +"areusingTouchScreenDevice "+ areusingTouchScreenDevice +" myTypedName"+ myTypedName +"role "+ role + " image"+image);
System.out.println("prop "+prop.size());
prop.load(ThirdVisdomReferralFomServlet.class.getClassLoader().getResourceAsStream("visdomReferral.properties"));
	

	PDDocument document =new PDDocument();
	PDPage page1 =new PDPage(PDPage.PAGE_SIZE_A4);
	
	PDRectangle rect=page1.getMediaBox();
	document.addPage(page1);
	
	PDFont fontplain = PDType1Font.TIMES_ROMAN;
	PDFont fontbold=PDType1Font.HELVETICA_BOLD;
	PDFont fontItalic =PDType1Font.HELVETICA_OBLIQUE;
	PDFont fontMono=PDType1Font.COURIER;
	
	
	
	PDPageContentStream cos=new PDPageContentStream(document, page1);
	
	
	int line=0;
	  
	cos.beginText();
	cos.setFont(fontbold, 12);
	cos.moveTextPositionByAmount(240, rect.getHeight() -50*(++line));
	cos.drawString("Mortgage Referral Agreement");
	cos.endText();
	int line2=0;
	cos.beginText();
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(25, rect.getHeight() -100);
	cos.drawString(prop.getProperty("referral1"));
	  cos.endText();
		line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(25,rect.getHeight() -100-line2);
cos.drawString(prop.getProperty("referral2"));
cos.endText();
	line2=line2+15;


	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
cos.drawString(prop.getProperty("referral3"));
	cos.endText();
	line2=line2+15;

	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
cos.drawString(prop.getProperty("referral4"));
	cos.endText();
	line2=line2+10;

	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral5"));
	cos.endText();
	line2=line2+10;

	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral6"));
cos.endText();
		line2=line2+20;
		
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(30,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral7"));
	cos.endText();
	line2=line2+15;
		
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral8"));
cos.endText();
	line2=line2+15;
		
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral8"));
	cos.endText();
	line2=line2+15;
		
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
cos.drawString(prop.getProperty("referral9"));
	cos.endText();
	line2=line2+10;
		
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral10"));
	cos.endText();
	line2=line2+15;
		
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral11"));
	cos.endText();
	line2=line2+15;
		
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral12"));
	cos.endText();
	line2=line2+10;
		
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral13"));
	cos.endText();
	line2=line2+10;
		
	
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	   cos.drawString(prop.getProperty("referral14"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	  cos.drawString(prop.getProperty("referral15"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	 cos.drawString(prop.getProperty("referral16"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	   cos.drawString(prop.getProperty("referral17"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	   cos.drawString(prop.getProperty("referral18"));
	cos.endText();
	line2=line2+10;
	
	
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	   cos.drawString(prop.getProperty("referral19"));
	cos.endText();
	line2=line2+15;

	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	   cos.drawString(prop.getProperty("referral20"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	   cos.drawString(prop.getProperty("referral21"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral22"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral23"));
	cos.endText();
	line2=line2+10;

	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral24"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral25"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral26"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral27"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral28"));
	cos.endText();
	line2=line2+15;

	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral29"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral30"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral31"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral32"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral33"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral34"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral35"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral36"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral37"));
	cos.endText();
	line2=line2+20;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(30,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral38"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral39"));
	cos.endText();
	line2=line2+10;
	
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral40"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral41"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral42"));
	cos.endText();
	line2=line2+20;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(30,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral43"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral44"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral45"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral46"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral47"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral48"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral49"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral50"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral51"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral52"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral53"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral54"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral55"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral56"));
	cos.endText();
	line2=line2+15;
cos.close();


PDPage page2=new PDPage(PDPage.PAGE_SIZE_A4);
document.addPage(page2);
cos =new PDPageContentStream(document, page2);
line2=0;
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral57"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral58"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral59"));
	cos.endText();
	line2=line2+10;
	
	/*cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
	cos.drawString(prop.getProperty("referral60"));
	cos.endText();
	line2=line2+10;*/
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral61"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral62"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral63"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral64"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral65"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral66"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral67"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(30,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral68"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral69"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral70"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral71"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral72"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral73"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral74"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral75"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral76"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral77"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral78"));
	cos.endText();
	line2=line2+20;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(30,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral79"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral80"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral81"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral82"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral83"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(30,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral84"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral85"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral86"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral87"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral88"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral89"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral90"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral91"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral92"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral93"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral94"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral95"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral96"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral97"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral98"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral99"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral70"));
	cos.endText();
	line2=line2+20;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral101"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral102"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral103"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral104"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral105"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral106"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral107"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral108"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral109"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral110"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral111"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral112"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral113"));
	cos.endText();
	line2=line2+20;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(30,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral114"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral115"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral116"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral117"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral118"));
	cos.endText();
	line2=line2+15;
	
	   cos.close();
    
    
    PDPage page3=new PDPage(PDPage.PAGE_SIZE_A4);
    document.addPage(page3);
    cos =new PDPageContentStream(document, page3);
    
	line2=0;
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral119"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral120"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral121"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral122"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral123"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral124"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral125"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral126"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral127"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral128"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral129"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral130"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral131"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral132"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral133"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral134"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral135"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral136"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral137"));
	cos.endText();
	line2=line2+15;
	
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral138"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral139"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral140"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral142"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral143"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral144"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral145"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral146"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral147"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral148"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral149"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral150"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral151"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral152"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral153"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral154"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral155"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral156"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral157"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral158"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral159"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral160"));
	cos.endText();
	line2=line2+20;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral161"));
	cos.endText();
	line2=line2+20;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral162"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(30,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral163"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral164"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral165"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral166"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(30,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral167"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral168"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral169"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(30,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral170"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral171"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral172"));
	cos.endText();
	line2=line2+10;
	
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral173"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral174"));
	cos.endText();
	line2=line2+10;
	
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral175"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral176"));
	cos.endText();
	line2=line2+10;
	
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral177"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral178"));
	cos.endText();
	line2=line2+10;
	
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral179"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral180"));
	cos.endText();
	line2=line2+10;
	
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral181"));
	cos.endText();
	line2=line2+15;
cos.close();


PDPage page4=new PDPage(PDPage.PAGE_SIZE_A4);
document.addPage(page4);
cos =new PDPageContentStream(document, page4);
line2=0;
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral182"));
	cos.endText();
	line2=line2+15;
	
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(30,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral183"));
	cos.endText();
	line2=line2+20;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral184"));
	cos.endText();
	line2=line2+10;
	
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral185"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral186"));
	cos.endText();
	line2=line2+10;
	
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral187"));
	cos.endText();
	line2=line2+15;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(30,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral188"));
	cos.endText();
	line2=line2+10;
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral189"));
	cos.endText();
	line2=line2+10;
	
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString(prop.getProperty("referral190"));
	cos.endText();
	line2=line2+25;
	line2=line2+20;
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(20,rect.getHeight() -70-line2);
	cos.drawString("Schedule B");
	cos.endText();
	line2=line2+15;
	
	
	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(200,rect.getHeight() -70-line2);
	cos.drawString("REFERRAL FEE SCHEDULE");
	cos.endText();
	line2=line2+20;
	
	if(role.equals("Business")|| role.equals("Customer")){
	
String[][] content = {{" Minimum Mortgage Value"," Maximum Mortgage Value", "    Referral Fee","  Renewal Fee"},
              {"$50,000.00","$200,000.00", "$50.00","$25.00"},       
              {"$200,001.00","$350,000.00","$125.00","$50.00"},
          	  {"$350,001.00","$600,000.00", "$250.00","$125.00"},
              {"$600,001.00","$900,000.00", "$350.00","$175.00"},
          	  {"$900,001.00","$3,000,000.00","$500.00","$250.00"} } ;
	
PdfTableCreation.PdfTableCreationMethod(page4, cos, rect.getHeight()-70-line2, 20, content);

}else if(role.equals("Realtor")||role.equals("Financial Planner")||role.equals("Builder")|| role.equals("Professional")){

String[][] content1 = {{" Minimum Mortgage Value"," Maximum Mortgage Value", "    Referral Fee","  Renewal Fee"},
{"$50,000.00","$200,000.00", "$100.00","$50.00"},       
{"$200,001.00","$350,000.00","$250.00","$100.00"},
	  {"$350,001.00","$600,000.00", "$500.00","$225.00"},
{"$600,001.00","$900,000.00", "$750.00","$350.00"},
	  {"$900,001.00","$3,000,000.00","$1,000.00","$500.00"} } ;

PdfTableCreation.PdfTableCreationMethod(page4, cos, rect.getHeight()-70-line2, 20, content1);
}

	

line2=line2+170;
	cos.beginText(); 
	cos.setFont(fontbold, 10);
	cos.moveTextPositionByAmount(60,rect.getHeight() -70-line2);
	cos.drawString("Referrer Signature :");
	cos.endText();
	line2=line2+110;
	       
if(areusingTouchScreenDevice.equals("Yes")){
	

try {
   // BufferedImage awtImage = ImageIO.read(new File("simple.jpg"));
   PDXObjectImage ximage = new PDPixelMap(document, image);
    float scale = 0.5f; // alter this value to set the image size
    cos.drawXObject(ximage, 50, rect.getHeight() -70-line2, ximage.getWidth()*scale, ximage.getHeight()*scale);
} catch (Exception fnfex) {
    System.out.println("No image for you");
}
cos.close();
}else{

	cos.beginText(); 
	cos.setFont(fontbold, 10);
	cos.moveTextPositionByAmount(90,rect.getHeight() -70-line2+80);
	cos.drawString(myTypedName);
	cos.endText();
	line2=line2+20;

	cos.beginText(); 
	cos.setFont(fontplain, 10);
	cos.moveTextPositionByAmount(60,rect.getHeight() -70-line2+70);
	cos.drawString("My typed name above serves as my electronic signature for the above agreement.");
	cos.endText();  
	cos.close();
}

	 			

/*	 			System.out.println("daoument "+document.toString()+"ddd"+document.getCurrentAccessPermission()+"do "+document.getDocumentInformation() +"doc ");
*/	 		
System.out.println("OutPut FileName********"+outputFileName);
document.save(outputFileName);
System.out.println("OutPut FileName&&&&&&&&&&&"+outputFileName);

	 			document.close();
	 			System.out.println("OutPut FileName##############"+outputFileName);

	 			try{
	 				File fl=new File(outputFileName);
	 				System.out.println("size "+fl.exists() +""+(file.length()/1024));
	 			}catch(Exception e){
	 				
	 			}
log.debug("The path pdf file created");
	 			//return outputFileName;
	 		}catch(Exception e){
	 			e.printStackTrace();
	 			
	 		}
		
		return outputFileName;

	}

}
