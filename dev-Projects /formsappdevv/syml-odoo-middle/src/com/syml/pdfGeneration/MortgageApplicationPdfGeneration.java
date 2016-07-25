package com.syml.pdfGeneration;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.UUID;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDPixelMap;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sendwithus.SendWithUsExample;
import com.syml.helper.GenericHelperClass;
import com.syml.mail.MortgageApplicationTemplate.ThankyouMailTemplateforApplicant;
import com.syml.openerp.ReferalToCreateReferralResoursce;

public class MortgageApplicationPdfGeneration {
	
	

		
		
		
		
	static Logger log = LoggerFactory.getLogger(MortgageApplicationPdfGeneration.class);

	public static void main(String[] args) throws JsonProcessingException {
		//MortgageApplicationPdfGenerationMethod("tset","No", "Yes", null, null, "ravi", "");
		new SendWithUsExample().sendDisclosuresToclientCompletedApp("tset", "venkatesh.m@bizruntime.com", "tset", "venkateshm383@gmail.com", null,		MortgageApplicationPdfGenerationMethod("tset","No", "Yes", null, null, "ravi", ""));
	}
			
			public static String MortgageApplicationPdfGenerationMethod(String applicantName,String areusingTouchScreenDevice,String isThereCoApplicant, BufferedImage image, BufferedImage image1,String myTypedName1,String myTypedName2){
			
log.debug("inside MortgageApplication Pdf generation method ....... MortgageApplicationPdfGeneration class");
				UUID uuid=UUID.randomUUID();
				 SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				 
				 
						  GregorianCalendar calendar=(GregorianCalendar) GregorianCalendar.getInstance();
			//	String file= "/home/venkateshm/Desktop/";
						//System.getProperty("user.dir");
				//String outputFileName=file+"Disclosures" +applicantName +"_"+format.format(calendar.getTime())+".pdf";
				GenericHelperClass genericHelperClass=new GenericHelperClass();
				 String outputFileName= genericHelperClass.readConfigfile().getProperty("pdfPath")+"Disclosure_" + applicantName +"_"+format.format(calendar.getTime())+".pdf";
			
				 Properties prop = new Properties();
			 		try {
			 			prop.load(MortgageApplicationPdfGeneration.class.getClassLoader().getResourceAsStream("applicant.properties"));
			 		

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
			 			cos.drawString("Applicant Agreement");
			 			cos.endText();
			 			int line2=0;
			 			
			 			cos.beginText();
			 			cos.setFont(fontplain, 10);
			 			cos.moveTextPositionByAmount(20, rect.getHeight() -100);
			 			cos.drawString(prop.getProperty("referral1"));
			 			 cos.endText();
			 				line2=line2+15;
			 			
			 			cos.beginText(); 
			 			cos.setFont(fontplain, 10);
			 			cos.moveTextPositionByAmount(25,rect.getHeight() -100-line2);
			 	        cos.drawString(prop.getProperty("referral2"));
			 	        cos.endText();
			 			line2=line2+10;
			 		
			 		
			 			cos.beginText(); 
			 			cos.setFont(fontplain, 10);
			 			cos.moveTextPositionByAmount(25,rect.getHeight() -100-line2);
			 		    cos.drawString(prop.getProperty("referral3"));
			 			cos.endText();
			 			line2=line2+10;
			 	        
			 			cos.beginText(); 
			 			cos.setFont(fontplain, 10);
			 			cos.moveTextPositionByAmount(25,rect.getHeight() -100-line2);
			 		    cos.drawString(prop.getProperty("referral4"));
			 			cos.endText();
			 			line2=line2+10;
			 		    
			 			cos.beginText(); 
			 			cos.setFont(fontplain, 10);
			 			cos.moveTextPositionByAmount(25,rect.getHeight() -100-line2);
			 			cos.drawString(prop.getProperty("referral5"));
			 			cos.endText();
			 			line2=line2+10;
			 	        
			 			cos.beginText(); 
			 			cos.setFont(fontplain, 10);
			 			cos.moveTextPositionByAmount(25,rect.getHeight() -100-line2);
			 			cos.drawString(prop.getProperty("referral6"));
			 			cos.endText();
			 			line2=line2+10;
			 			
			 			cos.beginText(); 
			 			cos.setFont(fontplain, 10);
			 			cos.moveTextPositionByAmount(40,rect.getHeight() -100-line2);
			 			cos.drawString(prop.getProperty("referral7"));
			 			cos.endText();
			 			line2=line2+10;
			 			
			 			cos.beginText(); 
			 			cos.setFont(fontplain, 10);
			 			cos.moveTextPositionByAmount(40,rect.getHeight() -100-line2);
			 			cos.drawString(prop.getProperty("referral8"));
			 			cos.endText();
			 			line2=line2+10;
			 			
			 			cos.beginText(); 
			 			cos.setFont(fontplain, 10);
			 			cos.moveTextPositionByAmount(40,rect.getHeight() -100-line2);
			 			cos.drawString(prop.getProperty("referral10"));
			 			cos.endText();
			 			line2=line2+10;
			 			
			 			
			 			cos.beginText(); 
			 			cos.setFont(fontplain, 10);
			 			cos.moveTextPositionByAmount(40,rect.getHeight() -100-line2);
			 			cos.drawString(prop.getProperty("referral11"));
			 			cos.endText();
			 			line2=line2+10;
			 			
			 			cos.beginText(); 
			 			cos.setFont(fontplain, 10);
			 			cos.moveTextPositionByAmount(40,rect.getHeight() -100-line2);
			 			cos.drawString(prop.getProperty("referral12"));
			 			cos.endText();
			 			line2=line2+10;
			 			
			 			cos.beginText(); 
			 			cos.setFont(fontplain, 10);
			 			cos.moveTextPositionByAmount(25,rect.getHeight() -100-line2);
			 			cos.drawString(prop.getProperty("referral13"));
			 			cos.endText();
			 			line2=line2+10;
			 			
			 			cos.beginText(); 
			 			cos.setFont(fontplain, 10);
			 			cos.moveTextPositionByAmount(25,rect.getHeight() -100-line2);
			 			cos.drawString(prop.getProperty("referral14"));
			 			cos.endText();
			 			line2=line2+10;
			 			cos.beginText(); 
			 			cos.setFont(fontplain, 10);
			 			cos.moveTextPositionByAmount(25,rect.getHeight() -100-line2);
			 			cos.drawString(prop.getProperty("referral15"));
			 			cos.endText();
			 			line2=line2+10;
			 			
			 			cos.beginText(); 
			 			cos.setFont(fontplain, 10);
			 			cos.moveTextPositionByAmount(25,rect.getHeight() -100-line2);
			 			cos.drawString(prop.getProperty("referral16"));
			 			cos.endText();
			 			line2=line2+10;
			 			
			 			cos.beginText(); 
			 			cos.setFont(fontplain, 10);
			 			cos.moveTextPositionByAmount(25,rect.getHeight() -100-line2);
			 			cos.drawString(prop.getProperty("referral17"));
			 			cos.endText();
			 			line2=line2+10;
			 			
			 			cos.beginText(); 
			 			cos.setFont(fontplain, 10);
			 			cos.moveTextPositionByAmount(25,rect.getHeight() -100-line2);
			 			cos.drawString(prop.getProperty("referral18"));
			 			cos.endText();
			 			line2=line2+10;
			 			
			 			cos.beginText(); 
			 			cos.setFont(fontplain, 10);
			 			cos.moveTextPositionByAmount(25,rect.getHeight() -100-line2);
			 			cos.drawString(prop.getProperty("referral19"));
			 			cos.endText();
			 			line2=line2+10;	
			 			
			 			cos.beginText(); 
			 			cos.setFont(fontplain, 10);
			 			cos.moveTextPositionByAmount(25,rect.getHeight() -100-line2);
			 			cos.drawString(prop.getProperty("referral191"));
			 			cos.endText();
			 			line2=line2+10;
			 			
			 			cos.beginText(); 
			 			cos.setFont(fontplain, 10);
			 			cos.moveTextPositionByAmount(25,rect.getHeight() -100-line2);
			 			cos.drawString(prop.getProperty("referral192"));
			 			cos.endText();
			 			line2=line2+10;
			 			
			 			cos.beginText(); 
			 			cos.setFont(fontplain, 10);
			 			cos.moveTextPositionByAmount(25,rect.getHeight() -100-line2);
			 			cos.drawString(prop.getProperty("referral193"));
			 			cos.endText();
			 			line2=line2+20;
			 			
			 			cos.beginText(); 
			 			cos.setFont(fontbold, 10);
			 			cos.moveTextPositionByAmount(20,rect.getHeight() -100-line2);
			 			cos.drawString(prop.getProperty("referral20"));
			 			cos.endText();
			 			line2=line2+10;
			 			
			 			
			 			cos.beginText(); 
			 			cos.setFont(fontplain, 10);
			 			cos.moveTextPositionByAmount(25,rect.getHeight() -100-line2);
			 			cos.drawString(prop.getProperty("referral21"));
			 			cos.endText();
			 			line2=line2+10;
			 			
			 			
			 			cos.beginText(); 
			 			cos.setFont(fontplain, 10);
			 			cos.moveTextPositionByAmount(25,rect.getHeight() -100-line2);
			 			cos.drawString(prop.getProperty("referral22"));
			 			cos.endText();
			 			line2=line2+20;
			 			
			 			
			 			cos.beginText(); 
			 			cos.setFont(fontplain, 10);
			 			cos.moveTextPositionByAmount(25,rect.getHeight() -100-line2);
			 			cos.drawString(prop.getProperty("referral24"));
			 			cos.endText();
			 			line2=line2+10;
			 			
			 			cos.beginText(); 
			 			cos.setFont(fontplain, 10);
			 			cos.moveTextPositionByAmount(25,rect.getHeight() -100-line2);
			 			cos.drawString(prop.getProperty("referral25"));
			 			cos.endText();
			 			line2=line2+10;
			 			
			 			
			 			cos.beginText(); 
			 			cos.setFont(fontplain, 10);
			 			cos.moveTextPositionByAmount(25,rect.getHeight() -100-line2);
			 			cos.drawString(prop.getProperty("referral26"));
			 			cos.endText();
			 			line2=line2+20;
			 			
			 			
			 			cos.beginText(); 
			 			cos.setFont(fontplain, 10);
			 			cos.moveTextPositionByAmount(25,rect.getHeight() -100-line2);
			 			cos.drawString(prop.getProperty("referral27"));
			 			cos.endText();
			 			line2=line2+10;
			 			
			 			cos.beginText(); 
			 			cos.setFont(fontplain, 10);
			 			cos.moveTextPositionByAmount(25,rect.getHeight() -100-line2);
			 			cos.drawString(prop.getProperty("referral28"));
			 			cos.endText();
			 			line2=line2+10;
			 			
			 			cos.beginText(); 
			 			cos.setFont(fontplain, 10);
			 			cos.moveTextPositionByAmount(25,rect.getHeight() -100-line2);
			 			cos.drawString(prop.getProperty("referral29"));
			 			cos.endText();
			 			line2=line2+10;
			 			
			 			cos.beginText(); 
			 			cos.setFont(fontplain, 10);
			 			cos.moveTextPositionByAmount(25,rect.getHeight() -100-line2);
			 			cos.drawString(prop.getProperty("referral30"));
			 			cos.endText();
			 			line2=line2+20;
			 			
			 			cos.beginText(); 
			 			cos.setFont(fontplain, 10);
			 			cos.moveTextPositionByAmount(25,rect.getHeight() -100-line2);
			 			cos.drawString(prop.getProperty("referral31"));
			 			cos.endText();
			 			line2=line2+10;
			 			
			 			
			 			cos.beginText(); 
			 			cos.setFont(fontplain, 10);
			 			cos.moveTextPositionByAmount(25,rect.getHeight() -100-line2);
			 			cos.drawString(prop.getProperty("referral32"));
			 			cos.endText();
			 			line2=line2+10;
			 			
			 			cos.beginText(); 
			 			cos.setFont(fontplain, 10);
			 			cos.moveTextPositionByAmount(25,rect.getHeight() -100-line2);
			 			cos.drawString(prop.getProperty("referral33"));
			 			cos.endText();
			 			line2=line2+20;
			 			
			 			
			 			cos.beginText(); 
			 			cos.setFont(fontplain, 10);
			 			cos.moveTextPositionByAmount(25,rect.getHeight() -100-line2);
			 			cos.drawString(prop.getProperty("referral34"));
			 			cos.endText();
			 			line2=line2+10;
			 			
			 			cos.beginText(); 
			 			cos.setFont(fontplain, 10);
			 			cos.moveTextPositionByAmount(25,rect.getHeight() -100-line2);
			 			cos.drawString(prop.getProperty("referral35"));
			 			cos.endText();
			 			line2=line2+10;
			 			
			 			
			 			cos.beginText(); 
			 			cos.setFont(fontplain, 10);
			 			cos.moveTextPositionByAmount(25,rect.getHeight() -100-line2);
			 			cos.drawString(prop.getProperty("referral36"));
			 			cos.endText();
			 			line2=line2+10;
			 			
			 			
			 			cos.beginText(); 
			 			cos.setFont(fontplain, 10);
			 			cos.moveTextPositionByAmount(25,rect.getHeight() -100-line2);
			 			cos.drawString(prop.getProperty("referral37"));
			 			cos.endText();
			 			line2=line2+10;
			 			
			 			cos.beginText(); 
			 			cos.setFont(fontplain, 10);
			 			cos.moveTextPositionByAmount(25,rect.getHeight() -100-line2);
			 			cos.drawString(prop.getProperty("referral38"));
			 			cos.endText();
			 			line2=line2+10;
			 			
			 			cos.beginText(); 
			 			cos.setFont(fontplain, 10);
			 			cos.moveTextPositionByAmount(25,rect.getHeight() -100-line2);
			 			cos.drawString(prop.getProperty("referral39"));
			 			cos.endText();
			 			line2=line2+10;
			 			
			 			cos.beginText(); 
			 			cos.setFont(fontplain, 10);
			 			cos.moveTextPositionByAmount(25,rect.getHeight() -100-line2);
			 			cos.drawString(prop.getProperty("referral40"));
			 			cos.endText();
			 			line2=line2+10;
			 			
			 			cos.beginText(); 
			 			cos.setFont(fontplain, 10);
			 			cos.moveTextPositionByAmount(25,rect.getHeight() -100-line2);
			 			cos.drawString(prop.getProperty("referral41"));
			 			cos.endText();
			 			line2=line2+20;
			 			
			 			
			 			cos.beginText(); 
			 			cos.setFont(fontplain, 10);
			 			cos.moveTextPositionByAmount(25,rect.getHeight() -100-line2);
			 			cos.drawString(prop.getProperty("referral42"));
			 			cos.endText();
			 			line2=line2+10;
			 			
			 			
			 			cos.beginText(); 
			 			cos.setFont(fontplain, 10);
			 			cos.moveTextPositionByAmount(25,rect.getHeight() -100-line2);
			 			cos.drawString(prop.getProperty("referral43"));
			 			cos.endText();
			 			line2=line2+10;
			 			
			 			
			 			cos.beginText(); 
			 			cos.setFont(fontplain, 10);
			 			cos.moveTextPositionByAmount(25,rect.getHeight() -100-line2);
			 			cos.drawString(prop.getProperty("referral44"));
			 			cos.endText();
			 			line2=line2+20;
			 			
			 			
			 			
			 			cos.beginText(); 
			 			cos.setFont(fontplain, 10);
			 			cos.moveTextPositionByAmount(25,rect.getHeight() -100-line2);
			 			cos.drawString(prop.getProperty("referral45"));
			 			cos.endText();
			 			line2=line2+10;
			 			
			 			
			 			cos.beginText(); 
			 			cos.setFont(fontplain, 10);
			 			cos.moveTextPositionByAmount(25,rect.getHeight() -100-line2);
			 			cos.drawString(prop.getProperty("referral46"));
			 			cos.endText();
			 			line2=line2+10;
			 			
			 			
			 			cos.beginText(); 
			 			cos.setFont(fontplain, 10);
			 			cos.moveTextPositionByAmount(25,rect.getHeight() -100-line2);
			 			cos.drawString(prop.getProperty("referral47"));
			 			cos.endText();
			 			line2=line2+20;
			 			

			 			cos.beginText(); 
			 			cos.setFont(fontplain, 10);
			 			cos.moveTextPositionByAmount(25,rect.getHeight() -100-line2);
			 			cos.drawString(prop.getProperty("referral48"));
			 			cos.endText();
			 			line2=line2+20;
			 			
			 			cos.beginText(); 
			 			cos.setFont(fontbold, 10);
			 			cos.moveTextPositionByAmount(50,rect.getHeight() -100-line2);
			 			cos.drawString("Applicant Signature :");
			 			cos.endText();
			 			//line2=line2+10;
			 			
			 			if(isThereCoApplicant.equals("Yes")){
			 			cos.beginText(); 
			 			cos.setFont(fontbold, 10);
			 			cos.moveTextPositionByAmount(350,rect.getHeight() -100-line2);
			 			cos.drawString("Co-Applicant Signature :");
			 			cos.endText();
			 			line2=line2+120;
			 			
			 			}else{
			 				line2=line2+120;
			 			}
			 			
			 			
			 			
			 			if(areusingTouchScreenDevice.equals("Yes")){

			 	        try {
			 	           // BufferedImage awtImage = ImageIO.read(new File("simple.jpg"));
			 	            PDXObjectImage ximage = new PDPixelMap(document, image);
			 	           float scale = 0.5f; // alter this value to set the image size
			 	            cos.drawXObject(ximage, 50,rect.getHeight() -100-line2 , ximage.getWidth()*scale, ximage.getHeight()*scale);
			 	        } catch (Exception fnfex) {
			 	            System.out.println("No image for you");
			 	        }
			 	        
			 	        if(isThereCoApplicant.equals("Yes")){
			 	        try {
			 	            // BufferedImage awtImage = ImageIO.read(new File("simple.jpg"));
			 	             PDXObjectImage ximage = new PDPixelMap(document, image1);
			 	            float scale = 0.5f; // alter this value to set the image size
			 	             cos.drawXObject(ximage, 400,rect.getHeight() -100-line2 , ximage.getWidth()*scale, ximage.getHeight()*scale);
			 	         } catch (Exception fnfex) {
			 	             System.out.println("No image for you");
			 	         }
			 	        }
			 	       cos.close();
			 			}else{
			 				cos.beginText(); 
				 			cos.setFont(fontbold, 10);
				 			cos.moveTextPositionByAmount(50,rect.getHeight() -100-line2+60);
				 			cos.drawString(myTypedName1);
				 			cos.endText();
				 		   
			 				
			 			     if(isThereCoApplicant.equals("Yes")){
			 			    	cos.beginText(); 
					 			cos.setFont(fontbold, 10);
					 			cos.moveTextPositionByAmount(400,rect.getHeight() -100-line2+60);
					 			cos.drawString(myTypedName2);
					 			cos.endText();
					 			 
				 				 
			 			     }
			 			     
			 				line2=line2+20;
				 			cos.beginText(); 
				 			cos.setFont(fontplain, 10);
				 			cos.moveTextPositionByAmount(60,rect.getHeight() -70-line2);
				 			cos.drawString("My typed name above serves as my electronic signature for the above agreement.");
				 			cos.endText();  
				 	        cos.close();
				 	       
			 				
			 			}
			 			
			 		

						document.save(outputFileName);
						document.close();
			 			
			 			
			 			
			 			
			 			
			 			
			 			
			 			
			 			
			 			
			 			
			 			
			 			
							return outputFileName;
					
			 			
			 			
			 		}catch(Exception e){
			 			
			 		}
			 		return outputFileName;
			}

		

	}


