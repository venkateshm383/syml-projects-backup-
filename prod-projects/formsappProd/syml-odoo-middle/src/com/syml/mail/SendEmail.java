package com.syml.mail;
import java.util.*;

import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import com.syml.constants.SymlConstant;
import com.syml.helper.GenericHelperClass;


public class SendEmail
{
	 static	Logger log = LoggerFactory.getLogger(SendEmail.class);
	
	public static void main(String[] args) {
		HashSet recipients=new HashSet();
		recipients.add("venkatesh.m@bizruntime.com");
		//sendEmail("test","<html><body><a><href=\"{{http://localhost:8080/syml-odoo-middle-0.0.1-SNAPSHOT/MortgageApplicationCo1b.jsp}}\">click</a><body></html>", "Venkateshm383@gmail.com", recipients);
		sendEmailWithAttachment("hello", "seted", "venktesh.m@bizruntime.com", recipients, "D:/Mavenprojects1/Test/src/SendEmail.java");   
		
	}
	
	 
	public void sendEmailMortgage(String subject,String body){
		
		log.info("inside sendEmailMortgage method  of SendEmai class");
		
		SymlConstant sc=new SymlConstant();
		 final String fromEmail = sc.getFromEmail(); //requires valid gmail id
	         final String password =sc.getPassword(); // correct password for gmail id
	        final String toEmail = sc.getToEmail(); // can be any email id
	        System.setProperty("javax.net.ssl.trustStore", "C:/.keystore");
	        System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
	        log.info("TLSEmail Start");
	        Properties props = new Properties();
	        props.put("mail.smtp.host", sc.getSmtpHost()); //SMTP Host
	        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");// ignore the certificate issue
	        props.put("mail.smtp.port",sc.getSmtpPort()); //TLS Port
	        props.put("mail.smtp.auth", sc.getSmtpAuth()); //enable authentication
	        props.put("mail.smtp.starttls.enable", sc.getSmtpTTLSEnabled()); //enable STARTTLS
	        props.put("mail.smtp.starttls.enable","true");

	        props.put("mail.smtp.EnableSSL.enable","true");
	                //create Authenticator object to pass in Session.getInstance argument
	        Authenticator auth = new Authenticator() {
	            //override the getPasswordAuthentication method
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(fromEmail, password);
	            }
	        };
	        javax.mail.Session session = javax.mail.Session.getInstance(props, auth);
	        HashSet<String> recipents=new HashSet<String>();
	        //recipents.add("venkatesh.m@bizruntime.com");
	         //recipents.add("deepali.singh@bizruntime.com");
	      //  EmailUtil.sendEmail(session,fromEmail, toEmail,subject,body);
	     
	         
	        //EmailUtil.sendMail(session, fromEmail,"venkatesh.m@bizruntime.com", subject,ReferralInstructionsMessageTemplate.ReferralInstructions("Venkatesh","iPhone"),recipents, "");
	         
	}
	
	
public static void sendEmail(String subject,String body,String toEmail,HashSet recipients){
		
		log.info("inside sendEmailMortgage method  of SendEmai class");
		
		SymlConstant sc=new SymlConstant();
		GenericHelperClass genericHelperClass=new GenericHelperClass();
		Properties prop=genericHelperClass.readEmailConfigfile();
		
		// Properties props = new Properties();
		//System.out.println( prop.getProperty("fromEmailid"));
		//System.out.println( prop.getProperty("password"));
		 final String fromEmail =  prop.getProperty("fromEmailid");//sc.getFromEmail(); //requires valid gmail id
	         final String password = prop.getProperty("password"); // correct password for gmail id
	       // final String toEmail = sc.getToEmail(); // can be any email id
	         
	        log.info("TLSEmail Start");
	        Properties props = new Properties();
	       
	        props.put("mail.smtp.host", prop.getProperty("Host"));	//sc.getSmtpHost()); //SMTP Host
	        props.put("mail.smtp.ssl.trust",prop.getProperty("certificate"));	// "smtp.gmail.com");// ignore the certificate issue
	        props.put("mail.smtp.port",prop.getProperty("smtpPort"));	                 //sc.getSmtpPort()); //TLS Port
	        props.put("mail.smtp.auth",prop.getProperty("smtpAuth"));	  //sc.getSmtpAuth()); //enable authentication
	        props.put("mail.smtp.starttls.enable", prop.getProperty("smtpTTLSEnabled"));					//sc.getSmtpTTLSEnabled()); //enable STARTTLS
	         
	                //create Authenticator object to pass in Session.getInstance argument
	        Authenticator auth = new Authenticator() {
	            //override the getPasswordAuthentication method
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(fromEmail, password);
	            }
	        };
	        Session session = Session.getInstance(props, auth);
	      //  HashSet<String> recipents=new HashSet<String>();
	        //recipents.add("venkatesh.m@bizruntime.com");
	         //recipents.add("deepali.singh@bizruntime.com");
	      //  EmailUtil.sendEmail(session,fromEmail, toEmail,subject,body);
	     
	      //   CreateNote.createNote(, body, )
	        EmailUtil.sendMail(session, fromEmail,toEmail, subject,body,recipients, "");
	         
	}
	
public static void sendEmailWithButton(String subject,String body,String toEmail,HashSet recipeints){
	
	
	
	
	GenericHelperClass genericHelperClass=new GenericHelperClass();
	Properties prop=genericHelperClass.readEmailConfigfile();
	
	// Properties props = new Properties();
	//System.out.println( prop.getProperty("fromEmailid"));
	//System.out.println( prop.getProperty("password"));
	 final String fromEmail =  prop.getProperty("fromEmailid");//sc.getFromEmail(); //requires valid gmail id
         final String password = prop.getProperty("password"); // correct password for gmail id
       // final String toEmail = sc.getToEmail(); // can be any email id
         
        log.info("TLSEmail Start");
        Properties props = new Properties();
       
        props.put("mail.smtp.host", prop.getProperty("Host"));	//sc.getSmtpHost()); //SMTP Host
        props.put("mail.smtp.ssl.trust",prop.getProperty("certificate"));	// "smtp.gmail.com");// ignore the certificate issue
        props.put("mail.smtp.port",prop.getProperty("smtpPort"));	                 //sc.getSmtpPort()); //TLS Port
        props.put("mail.smtp.auth",prop.getProperty("smtpAuth"));	  //sc.getSmtpAuth()); //enable authentication
        props.put("mail.smtp.starttls.enable", prop.getProperty("smtpTTLSEnabled"));					//sc.getSmtpTTLSEnabled()); //enable STARTTLS
        props.put("mail.debug", "true");
        //props.put("mail.smtp.starttls.enable","true");

        props.put("mail.smtp.EnableSSL.enable","true");
                //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        Session session = Session.getInstance(props, auth);
        MimeMessage msg = new MimeMessage(session);
        
        //set message headers
        try {
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
		
        msg.addHeader("format", "flowed");
        msg.addHeader("Content-Transfer-Encoding", "8bit");

        msg.setFrom(new InternetAddress(fromEmail));


        msg.setSubject(subject, "UTF-8");

     
	MimeBodyPart mbp = new MimeBodyPart(); 
    mbp.setContent(body, "text/html"); 
     MimeMultipart multipart = new MimeMultipart(); 
     multipart.addBodyPart(mbp); 
     mbp.setText(body, "UTF-8", "html");
     msg.setContent(multipart);
     
     msg.setSentDate(new Date());
     
     //for To Type
  
 MimeBodyPart mimeBodyPart=new MimeBodyPart();
    //	msg.setRecipient(Message.RecipientType.TO,InternetAddress.parse(tomail, true));   
	msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
    
     //for CC type mail
     Iterator iterator2=recipeints.iterator();
    if(recipeints.size()>=1){
    	 String recipients="";
    		int i=0;
    	while(iterator2.hasNext()){
    		
    		if(recipeints.size()==++i){
    	    	recipients+=iterator2.next();
    		}else{
    			recipients+=iterator2.next()+",";
    		}
    		
    	}
    
    	 msg.addRecipients(Message.RecipientType.CC,recipients);
     }else{
    	 log.debug(" Recipient EmailId is not enterd");
     }
    
   
    
    
    
     //for single recipeint
     
    // msg.setRecipients(type, addresses)
   
     log.debug("Message is ready");
   //  Transport.send(msg); 

     log.debug("EMail Sent Successfully!!");
   

        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

}




public static void sendEmailWithAttachment(String subject,String body,String toEmail,HashSet recipeints,String file){
	
	
	
	
	GenericHelperClass genericHelperClass=new GenericHelperClass();
	Properties prop=genericHelperClass.readEmailConfigfile();
	
	// Properties props = new Properties();
	//System.out.println( prop.getProperty("fromEmailid"));
	//System.out.println( prop.getProperty("password"));
	 final String fromEmail =  prop.getProperty("fromEmailid");//sc.getFromEmail(); //requires valid gmail id
         final String password = prop.getProperty("password"); // correct password for gmail id
       // final String toEmail = sc.getToEmail(); // can be any email id
         
        log.info("TLSEmail Start");
        Properties props = new Properties();
       
        props.put("mail.smtp.host", prop.getProperty("Host"));	//sc.getSmtpHost()); //SMTP Host
        props.put("mail.smtp.ssl.trust",prop.getProperty("certificate"));	// "smtp.gmail.com");// ignore the certificate issue
        props.put("mail.smtp.port",prop.getProperty("smtpPort"));	                 //sc.getSmtpPort()); //TLS Port
        props.put("mail.smtp.auth",prop.getProperty("smtpAuth"));	  //sc.getSmtpAuth()); //enable authentication
        props.put("mail.smtp.starttls.enable", prop.getProperty("smtpTTLSEnabled"));					//sc.getSmtpTTLSEnabled()); //enable STARTTLS
        props.put("mail.debug", "true");
        //props.put("mail.smtp.starttls.enable","true");

        props.put("mail.smtp.EnableSSL.enable","true");
                //create Authenticator object to pass in Session.getInstance argument
        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
        Session session = Session.getInstance(props, auth);
        MimeMessage msg = new MimeMessage(session);
        
        //set message headers
        try {
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
		
        msg.addHeader("format", "flowed");
        msg.addHeader("Content-Transfer-Encoding", "8bit");

        msg.setFrom(new InternetAddress(fromEmail));


        msg.setSubject(subject, "UTF-8");

     
	MimeBodyPart mbp = new MimeBodyPart(); 
	
     MimeMultipart multipart = new MimeMultipart(); 
     mbp.setText(body, "UTF-8", "html");
     multipart.addBodyPart(mbp); 
     msg.setContent(multipart);
    // String filename = "file.txt";
     
      mbp = new MimeBodyPart(); 
     DataSource source = new FileDataSource(file);
     mbp.setDataHandler(new DataHandler(source));
     mbp.setFileName(subject+".pdf");
    
     multipart.addBodyPart(mbp); 
     msg.setSentDate(new Date());
     
     //for To Type
  

    //	msg.setRecipient(Message.RecipientType.TO,InternetAddress.parse(tomail, true));   
	msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
    
     //for CC type mail
     Iterator iterator2=recipeints.iterator();
    if(recipeints.size()>=1){
    	 String recipients="";
    		int i=0;
    	while(iterator2.hasNext()){
    		
    		if(recipeints.size()==++i){
    	    	recipients+=iterator2.next();
    		}else{
    			recipients+=iterator2.next()+",";
    		}
    		
    	}
    
    	 msg.addRecipients(Message.RecipientType.CC,recipients);
     }else{
    	 log.debug(" Recipient EmailId is not enterd");
     }
    
   
    
    
    
     //for single recipeint
     
    // msg.setRecipients(type, addresses)
   
     log.debug("Message is ready");
    // Transport.send(msg); 

     log.debug("EMail Sent Successfully!!");
   

        } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

}
   
}
