package JavamailMessagCounter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.Random;
import java.util.UUID;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Infrastracture.CouchBaseOperation;

import akka.japi.Util;

import com.couchbase.client.CouchbaseClient;

import controllers.EmailList;

public class EmailHistory extends Thread {
	static Logger log = LoggerFactory.getLogger(EmailHistory.class);

	Folder folder = null;
	Store store = null;
	String emailId;
	String password;

	public EmailHistory(String emailId, String password) {
		this.emailId = emailId;
		this.password = password;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		

		log.debug("insede thread of EmailHistory  email:"+emailId);

		  boolean success=false; CouchbaseClient client=null;
		  CouchBaseOperation cochbase=new CouchBaseOperation();
		  SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		  
		  try{
		  client = cochbase.getConnectionToCouchBase();
			JSONObject jsonObject1 = null;
			ArrayList<EmailList> list = null;
			try {
				list = cochbase.getEmailList();
			} catch (Exception e) {

			}
			String encrptPassword = null;
			if (list.size() != 0) {
				int size = list.size();
				jsonObject1 = new JSONObject();
				encrptPassword = cochbase.encryptPassword(password);

				jsonObject1.put("visdomemailid", emailId);
				jsonObject1.put("visdompassword", encrptPassword);
				cochbase.storeDataInCouchBase("EmailList_" +UUID.randomUUID(),
						jsonObject1);
			} else {
				jsonObject1 = new JSONObject();
				encrptPassword = cochbase.encryptPassword(password);

				jsonObject1.put("visdomemailid", emailId);
				jsonObject1.put("visdompassword", encrptPassword);
				cochbase.storeDataInCouchBase("EmailList_" + UUID.randomUUID(),
						jsonObject1);
			}
		  }catch(Exception e){
			  e.printStackTrace();
		  }
		  try {
		  
		  Properties prop = new Properties();
		  
		  String path=""; try {
		  
		  // getting connection parameter
		  prop.load(EmailHistory.class.getClassLoader()
		  .getResourceAsStream("config.properties"));
		  
		  log.info(prop.getProperty("path"));
		  path=prop.getProperty("path");
		  
		  } catch (Exception e) { e.printStackTrace(); }
		  
		  Properties props = System.getProperties();
		  props.setProperty("mail.store.protocol", "imaps");
		  
		  Session session = Session.getDefaultInstance(props, null);
		  
		  
		   session.setDebug(true); store = session.getStore("imaps");
		  store.connect("imap.gmail.com",emailId, password); folder =
		  store.getFolder("Inbox"); 
		  folder.open(Folder.HOLDS_MESSAGES);
		  
		  // log.info( "new message  "+folder.);
		  
		  
		  
		  Message messages[] = folder.getMessages();
		  
		  log.info("No of Messages : " + folder.getMessageCount());
		  log.info("No of Unread Messages : " +
		  folder.getUnreadMessageCount());
		  
		  
		  client=cochbase.getConnectionToCouchBase();
		  
		  
		  
		  for (int i=0; i < folder.getMessageCount(); ++i) {
		  log.info("MESSAGE #" + (i + 1) + ":"); Message msg =
		  messages[i];
		  
		  
		  
		  
		  
		  
		 /* if we don''t want to fetch messages already processed if
		  (!msg.isSet(Flags.Flag.SEEN)) { String from = "unknown"; ... }
		  */
		  
		  
		  
		  
		  String from = "unknown"; if (msg.getReplyTo().length >= 1) { from =
		  msg.getReplyTo()[0].toString(); } else if (msg.getFrom().length >= 1)
		  { from = msg.getFrom()[0].toString(); } String subject =
		  msg.getSubject(); String content=msg.getDescription(); Address []
		  toEmail=msg.getAllRecipients(); log.info("Saving ... " +
		  subject +" " + from); // you may want to replace the spaces with "_"
		  // the TEMP directory is used to store the files
		  String filename =
		  path + subject+format.format(msg.getReceivedDate()); boolean
		  attachment=false; attachment= savePartsForInbox(msg.getContent(),
		  filename,emailId,msg.getReceivedDate()); // msg.setFlag(Flags.Flag.SEEN,true); // to delete
		  //the message // msg.setFlag(Flags.Flag.DELETED, true); 
		 
		  log.info("filename "+filename); //
			try{
	File file=new File(filename);
	file.delete();
			}catch(Exception e){
				
			}
		  String
		  recipinets=""; for (int j = 0; j < toEmail.length; j++) {
		  recipinets+=toEmail[j]; }
		  
		  
			Address [] fromEmail=msg.getFrom();
			String sender="";
try{
			for (int j = 0; j < fromEmail.length; j++) {
				sender += fromEmail[j];
			}
}catch(Exception e){
	e.printStackTrace();
}
String cnt ="";
try{

	Object content1 = msg.getContent();
	MimeMultipart mimeMultipart = (MimeMultipart) content1;
	BodyPart part = null;
	log.info("part size"+mimeMultipart.getCount());

	part = mimeMultipart.getBodyPart(0);
		
		String disposition = part.getDisposition();
		    String contentType = part.getContentType();
		    if (disposition == null) { // When just body
		        log.info("Null: " + contentType);
		        // Check if plain
		        if ((contentType.length() >= 10)
		                && (contentType.toLowerCase().substring(
		                0, 10).equals("text/plain"))) {
log.info(""+part.getContent());
}else if ((contentType.length() >= 9)
		                && (contentType.toLowerCase().substring(
		                        0, 9).equals("text/html"))) {
		                    part.writeTo(System.out);
		                } else if ((contentType.length() >= 9)
		                        && (contentType.toLowerCase().substring(
		                        0, 9).equals("text/html"))) {
		                    log.info("Ook html gevonden");
		                    part.writeTo(System.out);
		                }  else{
		                    log.info("Other body: " + part.toString());
		                    								                    
		      InputStream  inputStream=            part.getInputStream();
		                       System.out
									.println(inputStream);
		                       
		                       
		                       BufferedReader br = null;
		               		StringBuilder sb = new StringBuilder();

		               		String line;
		               		try {

		               			br = new BufferedReader(new InputStreamReader(inputStream));
		               			while ((line = br.readLine()) != null) {
		               				sb.append(line);
		               			}

		               		} catch (IOException e) {
		               			e.printStackTrace();
		               		} finally {
		               			if (br != null) {
		               				try {
		               					br.close();
		               				} catch (IOException e) {
		               					e.printStackTrace();
		               				}
		               			}
		               		}
log.info("datat "+sb.toString());
cnt=sb.toString();
		                }
		    }
		 
	
	
}catch(Exception e){
	e.printStackTrace();  
}
		  
		  JSONObject jsonObject=new JSONObject();
		  jsonObject.put("toEmail",recipinets ); jsonObject.put("from",emailId
		  ); jsonObject.put("subject",subject );
		  jsonObject.put("body",cnt);
jsonObject.put("fromEmail", sender);
		  
		  jsonObject.put("reciveDate",format.format(msg.getReceivedDate()) );
		  
		  
		 if(attachment){
		  jsonObject.put("Attachmane_id","doc_"+emailId+"_"+msg.getReceivedDate());
		  
		  } client=cochbase.getConnectionToCouchBase();
		  cochbase.storeDataInCouchBase
		  ("Inbox_"+emailId+"_"+format.format(msg.getReceivedDate()),
		  jsonObject);
		  
		  
		  client.shutdown();
		  
		  
		  } success=true;
		 

		storeSentMailHistory(emailId, password, path);
		
		  } catch (Exception e) { 
			  // TODO Auto-generated catch block
		  e.printStackTrace(); 
		  } finally { 
			  try{
				  if (folder != null) {
		  folder.close(true); 
		  } if (store != null) { store.close(); }
		  }catch(Exception e){ e.printStackTrace();
		  } 
			  }
	

	}

	public void storeSentMailHistory(String emailId, String password,
			String path) {

		CouchbaseClient client = null;
		CouchBaseOperation cochbase = new CouchBaseOperation();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

		
		  try {
		  
		  Properties props = System.getProperties();
		  props.setProperty("mail.store.protocol", "imaps");
		  
		  Session session = Session.getDefaultInstance(props, null);
		  
		  
		  // session.setDebug(true); store = session.getStore("imaps");
		  store.connect("imap.gmail.com",emailId, password); folder =
		  store.getFolder("[Gmail]/Sent Mail");/* Others GMail folders :
		  [Gmail]/All Mail This folder contains all of your Gmail messages.
		  [Gmail]/Drafts Your drafts. [Gmail]/Sent Mail Messages you sent to
		  other people. [Gmail]/Spam Messages marked as spam. [Gmail]/Starred
		  Starred messages. [Gmail]/Trash Messages deleted from Gmail.*/
		  
		  folder.open(Folder.READ_WRITE);
		  
		  // log.info( "new message  "+folder.);
		  
		  
		  log.info( "new message  "+folder.hasNewMessages());
		  
		  Message messages[] = folder.getMessages();
		  
		  log.info("No of Messages : " + folder.getMessageCount());
		  log.info("No of Unread Messages : " +
		  folder.getUnreadMessageCount());
		  
		  
		  client=cochbase.getConnectionToCouchBase();
		  
		  
		   for (int i=0; i <  folder.getMessageCount(); ++i) {
		  log.info("MESSAGE #" + (i + 1) + ":"); Message msg = messages[i];
		  
		  
		  
		  
		  
		  
		/*  if we don''t want to fetch messages already processed if
		  (!msg.isSet(Flags.Flag.SEEN)) { String from = "unknown"; ... }
		  */
		  
		  
		  
		  
		  String from = "unknown"; if (msg.getReplyTo().length >= 1) { from =
		  msg.getReplyTo()[0].toString(); } else if (msg.getFrom().length >= 1)
		  { from = msg.getFrom()[0].toString(); } String subject =
		  msg.getSubject(); String content=msg.getDescription(); Address []
		  toEmail=msg.getAllRecipients(); log.info("Saving ... " +
		  subject +" " + from); //you may want to replace the spaces with "_"
		  // the TEMP directory is used to store the files
		  String 
		  filename =
		  path + subject+""+format.format(msg.getReceivedDate()); boolean
		  attacheSucess=false; attacheSucess=
		  savePartsForSentMail(msg.getContent(), filename,emailId,msg.getSentDate());
		  log.info("filename "+filename); //
		try{
File file=new File(filename);
file.delete();
		}catch(Exception e){
			
		}
		  /*  msg.setFlag(Flags.Flag.SEEN,true); // to delete the message //
		  msg.setFlag(Flags.Flag.DELETED, true);*/
		  
		  String recipinets="";
			String cnt ="";
			  try{
			  
				Object content1 = msg.getContent();
				MimeMultipart mimeMultipart = (MimeMultipart) content1;
				BodyPart part = null;
				log.info("part size"+mimeMultipart.getCount());
			
				part = mimeMultipart.getBodyPart(0);
					
					String disposition = part.getDisposition();
					    String contentType = part.getContentType();
					    if (disposition == null) { // When just body
					        log.info("Null: " + contentType);
					        // Check if plain
					        if ((contentType.length() >= 10)
					                && (contentType.toLowerCase().substring(
					                0, 10).equals("text/plain"))) {
log.info(""+part.getContent());
}else if ((contentType.length() >= 9)
					                && (contentType.toLowerCase().substring(
					                        0, 9).equals("text/html"))) {
					                    part.writeTo(System.out);
					                } else if ((contentType.length() >= 9)
					                        && (contentType.toLowerCase().substring(
					                        0, 9).equals("text/html"))) {
					                    log.info("Ook html gevonden");
					                    part.writeTo(System.out);
					                }  else{
					                    log.info("Other body: " + part.toString());
					                    								                    
					      InputStream  inputStream=            part.getInputStream();
					                       System.out
												.println(inputStream);
					                       
					                       
					                       BufferedReader br = null;
					               		StringBuilder sb = new StringBuilder();

					               		String line;
					               		try {

					               			br = new BufferedReader(new InputStreamReader(inputStream));
					               			while ((line = br.readLine()) != null) {
					               				sb.append(line);
					               			}

					               		} catch (IOException e) {
					               			e.printStackTrace();
					               		} finally {
					               			if (br != null) {
					               				try {
					               					br.close();
					               				} catch (IOException e) {
					               					e.printStackTrace();
					               				}
					               			}
					               		}
log.info("datat "+sb.toString());
cnt=sb.toString();
					                }
					    }
					 
				
				
			  }catch(Exception e){
				e.printStackTrace();  
			  }
			
			
			
			Address [] fromEmail=msg.getFrom();
			String sender="";
try{
			for (int j = 0; j < fromEmail.length; j++) {
				sender += fromEmail[j];
			}
}catch(Exception e){
	e.printStackTrace();
}
			
			
		  for (int j = 0; j < toEmail.length; j++) { recipinets+=toEmail[j]; }
		  JSONObject jsonObject=new JSONObject();
		  jsonObject.put("toEmail",recipinets ); jsonObject.put("from",emailId
		  ); jsonObject.put("subject",subject );
		  
		  
		  jsonObject.put("reciveDate",format.format(msg.getSentDate()) );
		  jsonObject.put("Type", "Email");

		  jsonObject.put("fromEmail",sender);

		  jsonObject.put("body",cnt);
		  
		  if(attacheSucess){
		  jsonObject.put("Attachmane_id","doc_"+emailId+"_"+msg.getSentDate()); } client=cochbase.getConnectionToCouchBase();
		  cochbase
		  .storeDataInCouchBase("SentMails_"+emailId+"_"+format.format(msg
		  .getSentDate()), jsonObject);
		  
		  
		  client.shutdown();
		  
		  
		  }
		  
		  GregorianCalendar calendar=(GregorianCalendar)
		  GregorianCalendar.getInstance();
		  
		  
		  
		  
		  } catch (Exception e) { // TODO Auto-generated catch block
		  e.printStackTrace(); }
		 

		System.out
				.println("inside storing method.............................");
		try {
			

			ReceiveMailImap receiveMailImap = new ReceiveMailImap(emailId,
					password);
			receiveMailImap.start();
			receiveMailImap.setName("inbox" + emailId);

			SentMail sentMail = new SentMail(emailId, password);
			sentMail.start();
			sentMail.setName("sent" + emailId);
		} catch (Exception e) {

		} finally {
			try {
				if (folder != null) {
					folder.close(true);
				}
				if (store != null) {
					store.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean savePartsForInbox(Object content, String filename,
			String emailId,Date reciveDate) throws IOException, MessagingException

	{

		boolean attachment = false;
		CouchBaseOperation cochbase = new CouchBaseOperation();

		CouchbaseClient client = null;
		OutputStream out = null;
		InputStream in = null;
		try {
			if (content instanceof Multipart) {
				Multipart multi = ((Multipart) content);
				int parts = multi.getCount();
				for (int j = 0; j < parts; ++j) {
					int pdf=0;
					JSONObject jsonAttach = new JSONObject();

					Properties prop = new Properties();

					try {

						// getting connection parameter
						prop.load(EmailHistory.class.getClassLoader()
								.getResourceAsStream("config.properties"));

						log.info(prop.getProperty("path"));
						filename = prop.getProperty("path");

					} catch (Exception e) {
						e.printStackTrace();
					}
					MimeBodyPart part = (MimeBodyPart) multi.getBodyPart(j);
					if (part.getContent() instanceof Multipart) {
						// part-within-a-part, do some recursion...
						savePartsForInbox(part.getContent(), filename, emailId,reciveDate);
						log.info("fielname");
					} else {
						String extension = "";
						if (part.isMimeType("text/html")) {
							extension = "html";
						} else {
							if (part.isMimeType("text/plain")) {
								extension = "txt";
							} else {
								// Try to get the name of the attachment
								extension = part.getDataHandler().getName();
								pdf=1;

							}
							filename = filename + "." + extension;
							log.info("... " + filename);
							out = new FileOutputStream(new File(filename));
							in = part.getInputStream();
							int k;
							while ((k = in.read()) != -1) {
								out.write(k);
							}
						}
					}
					
					if(pdf==1){
					try {
						Path path1 = Paths.get(filename);
						byte[] data1 = Files.readAllBytes(path1);
										String encodeData=	  net.iharder.Base64.encodeBytes(data1);
				   
						jsonAttach.put("attachement", encodeData);

					} catch (Exception e) {
						e.printStackTrace();
					}
					attachment = true;
					client = cochbase.getConnectionToCouchBase();

					cochbase.storeDataInCouchBase("doc_" + emailId
							+ "_" +reciveDate, jsonAttach);

					client.shutdown();}
				}
				
			}
		} finally {
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.flush();
				out.close();
			}
		}
		return attachment;
	}

	public static boolean savePartsForSentMail(Object content, String filename,
			String emailId,Date sentDate) throws IOException, MessagingException

	{

		boolean attachment = false;
		CouchBaseOperation cochbase = new CouchBaseOperation();

		CouchbaseClient client = null;
		OutputStream out = null;
		InputStream in = null;
		
		try{
		try {
			if (content instanceof Multipart) {
				Multipart multi = ((Multipart) content);
				int parts = multi.getCount();
				for (int j = 0; j < parts; ++j) {
int pdf=0;
					Properties prop = new Properties();
					JSONObject jsonAttach = new JSONObject();

					try {

						// getting connection parameter
						prop.load(EmailHistory.class.getClassLoader()
								.getResourceAsStream("config.properties"));

						log.info(prop.getProperty("path"));
						filename =  prop.getProperty("path");

					} catch (Exception e) {
						e.printStackTrace();
					}
					MimeBodyPart part = (MimeBodyPart) multi.getBodyPart(j);
					if (part.getContent() instanceof Multipart) {
						// part-within-a-part, do some recursion...
						savePartsForSentMail(part.getContent(), filename,
								emailId,sentDate);
					} else {
						String extension = "";
						if (part.isMimeType("text/html")) {
							extension = "html";
						} else {
							if (part.isMimeType("text/plain")) {
								extension = "txt";
							} else {
								// Try to get the name of the attachment
								extension = part.getDataHandler().getName();
								pdf=1;

							}
							filename = filename + "." + extension;
							log.info("... " + filename);
							out = new FileOutputStream(new File(filename));
							in = part.getInputStream();
							int k;
							while ((k = in.read()) != -1) {
								out.write(k);
							}
						}
					}
					
					if(pdf==1){
					try {
						Path path1 = Paths.get(filename);
						byte[] data1 = Files.readAllBytes(path1);
					String encodeData=	  net.iharder.Base64.encodeBytes(data1);
				   
						jsonAttach.put("attachement", encodeData);

					} catch (Exception e) {
						e.printStackTrace();
					}
					attachment = true;
					client = cochbase.getConnectionToCouchBase();

					cochbase.storeDataInCouchBase("doc_" + emailId
							+ "_" + sentDate, jsonAttach);

					client.shutdown();
					}
				}
				
			}
		}catch(Exception e){
		e.printStackTrace();	
		}
		} finally {
			try{
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.flush();
				out.close();
			}
			}catch(Exception e){
				
			}
		}
		return attachment;
	}

}
