package com.syml.mail;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.syml.jotform.ip.GetMyIP;
public class EmailUtil {
	
	 static	Logger log = LoggerFactory.getLogger(EmailUtil.class);

	
	
	/**
     * Utility method to send simple HTML email
     * @param session
     * @param toEmail
     * @param subject
     * @param body
     */
   /* public static void sendEmail(Session session,String fromEmail, String toEmail, String subject, String body){
        try
        {
        	
        	log.info("inside sendEmail of EmailUtil class");
        	
          MimeMessage msg = new MimeMessage(session);
          
          //set message headers
          msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
          msg.addHeader("format", "flowed");
          msg.addHeader("Content-Transfer-Encoding", "8bit");
 
          msg.setFrom(new InternetAddress(fromEmail));

 
          msg.setSubject(subject, "UTF-8");
 
          msg.setText(body, "UTF-8");
 
          msg.setSentDate(new Date());
          
          String[] address=new String[]{};
          //for single recipeint
          msg.setRecipient(Message.RecipientType.TO,new InternetAddress(toEmail));
         // msg.setRecipients(type, addresses)
          msg.addRecipients(Message.RecipientType.CC,"venkatesh.m@bizruntime.com,deepali.singh@bizruntime.com  ");
          log.debug("Message is ready");
          Transport.send(msg); 
 
          log.debug("EMail Sent Successfully!!");
        }
        catch (Exception e) {
          e.printStackTrace();
        }
    }
    */
    
    
 public static void sendMail(Session session,String fromEmail,String tomail,String subject,String body,HashSet<String> recipeints,String attachement){
	 
	 try{
	 log.info("inside sendEmail of EmailUtil class");
 	
     MimeMessage msg = new MimeMessage(session);
     
     //set message headers
     msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
     msg.addHeader("format", "flowed");
     msg.addHeader("Content-Transfer-Encoding", "8bit");

     msg.setFrom(new InternetAddress(fromEmail));


     msg.setSubject(subject, "UTF-8");

     msg.setText(body, "UTF-8");
   //  msg.setReplyTo(InternetAddress.parse(tomail));
     	
     msg.setSentDate(new Date());
   
     //for To Type
  
 MimeBodyPart mimeBodyPart=new MimeBodyPart();
    //	msg.setRecipient(Message.RecipientType.TO,InternetAddress.parse(tomail, true));   
    	msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(tomail));
    
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
   }
   catch (Exception e) {
     e.printStackTrace();
   }
}
 
 
}
