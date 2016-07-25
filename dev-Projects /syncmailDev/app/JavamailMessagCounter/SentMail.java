package JavamailMessagCounter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.FolderClosedException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.event.MessageCountAdapter;
import javax.mail.event.MessageCountEvent;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Infrastracture.CouchBaseOperation;
import JavaMail.EmailHistory;
import JavaMail.RecivedMailImap;

import com.couchbase.client.CouchbaseClient;
import com.sun.mail.imap.IMAPFolder;


public class SentMail extends Thread {
	static Logger log = LoggerFactory.getLogger(EmailHistory.class);

	  String emailId;
		String password;
	public SentMail(String emailId, String password) {
		
			this.emailId = emailId;
			this.password = password;
		}

	String filename = null;

	CouchbaseClient client = null;
	CouchBaseOperation cochbase = new CouchBaseOperation();
	
	SimpleDateFormat format = new SimpleDateFormat(
			"yyyy-MM-dd hh:mm:ss");
	Properties prop = new Properties();

  
  @Override
	public void run() {
		// TODO Auto-generated method stub
	  
	  int i=0;
	while(!currentThread().isInterrupted()){
	 Folder folder = null;
	    Store store = null;
	  
	    
	try {
			
			prop.load(RecivedMailImap.class.getClassLoader()
					.getResourceAsStream("config.properties"));

			log.debug(prop.getProperty("couchBaseUrl"));
			filename =  prop.getProperty("path");

		} catch (Exception e) {
			e.printStackTrace();
		}
	    try {
	      Properties props = System.getProperties();
	      props.setProperty("mail.store.protocol", "imaps");

	      Session session = Session.getDefaultInstance(props, null);
	      // session.setDebug(true);
	      store = session.getStore("imaps");
	      store.connect("imap.gmail.com",emailId, password);
	      folder = store.getFolder("[Gmail]/Sent Mail");
	      /* Others GMail folders :
	       * [Gmail]/All Mail   This folder contains all of your Gmail messages.
	       * [Gmail]/Drafts     Your drafts.
	       * [Gmail]/Sent Mail  Messages you sent to other people.
	       * [Gmail]/Spam       Messages marked as spam.
	       * [Gmail]/Starred    Starred messages.
	       * [Gmail]/Trash      Messages deleted from Gmail.
	       */
	      folder.open(Folder.READ_WRITE);
	      
	      
			
	      Message messages[] = folder.getMessages();
	     log.debug("No of sent Messages : " + folder.getMessageCount());
	     log.debug("No of Unread Messages : " + folder.getUnreadMessageCount());
	      
	      folder.addMessageCountListener(new MessageCountAdapter() {
	          public void messagesAdded(MessageCountEvent ev) {
	        	  
	              Message[] msgs = ev.getMessages();
	              log.debug("Got " + msgs.length + " new messages");

	              // Just dump out the new messages
	              for (int i = 0; i < msgs.length; i++) {
	              try {
	                  log.debug("-----sent maillllllllllll");
	                  
	              
	              
	              
						Message msg = msgs[i];

	              
	              
			String from = "unknown";
			try{
				
			
						if (msg.getReplyTo().length >= 1) {
							from = msg.getReplyTo()[0].toString();
						} else if (msg.getFrom().length >= 1) {
							from = msg.getFrom()[0].toString();
						}
			}catch(Exception e){
				e.printStackTrace();
			}
						String subject = msg.getSubject();
						String content = msg.getDescription();
						Address[] toEmail = msg.getAllRecipients();
						log.debug("Saving ... " + subject
								+ " " + from);
						
						String recipinets = "";
						for (int j = 0; j < toEmail.length; j++) {
							recipinets += toEmail[j];
						}

						
						
						
						Address [] fromEmail=msg.getFrom();
						String sender="";
try{
						for (int j = 0; j < fromEmail.length; j++) {
							sender += fromEmail[j];
						}
}catch(Exception e){
	
}
						// you may want to replace the spaces with
						// "_"
						// the TEMP directory is used to store the
						// files
						filename +=subject;
						Boolean attacheSucess = false;
						attacheSucess = savePartsForSentMail(
								msg.getContent(), filename, emailId,msg.getSentDate());
						  log.debug("filename "+filename); //
							try{
					File file=new File(filename);
					file.delete();
							}catch(Exception e){
								
							}
							String cnt ="";
							  try{
							  
								Object content1 = msg.getContent();
								MimeMultipart mimeMultipart = (MimeMultipart) content1;
								BodyPart part = null;
								log.debug("part size"+mimeMultipart.getCount());
							
								part = mimeMultipart.getBodyPart(0);
									
									String disposition = part.getDisposition();
									    String contentType = part.getContentType();
									    if (disposition == null) { // When just body
									        log.debug("Null: " + contentType);
									        // Check if plain
									        if ((contentType.length() >= 10)
									                && (contentType.toLowerCase().substring(
									                0, 10).equals("text/plain"))) {
	log.debug(""+part.getContent());
	}else if ((contentType.length() >= 9)
									                && (contentType.toLowerCase().substring(
									                        0, 9).equals("text/html"))) {
									                    part.writeTo(System.out);
									                } else if ((contentType.length() >= 9)
									                        && (contentType.toLowerCase().substring(
									                        0, 9).equals("text/html"))) {
									                    log.debug("Ook html gevonden");
									                    part.writeTo(System.out);
									                }  else{
									                    log.debug("Other body: " + part.toString());
									                    								                    
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
	log.debug("datat "+sb.toString());
	cnt=sb.toString();
									                }
									    }
									 
								
								
							  }catch(Exception e){
								e.printStackTrace();  
							  }
						// msg.setFlag(Flags.Flag.SEEN,true);
						// to delete the message
						// msg.setFlag(Flags.Flag.DELETED, true);
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("toEmail", recipinets);
						jsonObject.put("from", sender);
						jsonObject.put("subject", subject);
						jsonObject.put("body", cnt);

						jsonObject.put("SentDate",
								format.format(msg.getSentDate()));


						if (attacheSucess) {
							jsonObject.put("Attachmane_id",
									"doc_"
											+ emailId + "_"
											+ msg.getSentDate());
						}

						client = cochbase
								.getConnectionToCouchBase();
						cochbase.storeDataInCouchBase("SentMails_"+emailId+"_"+format.format(msg.getSentDate()), jsonObject);
				
	              
	              client.shutdown();
	              
	              
	              } catch (Exception ioex) { 
	                  ioex.printStackTrace(); 
	              } 
	              }
	          }
	          });
	      

	      int freq = Integer.parseInt("100");
	      boolean supportsIdle = false;
	      try {
	      if (folder instanceof IMAPFolder) {
	          IMAPFolder f = (IMAPFolder)folder;
	          f.idle();
	          supportsIdle = true;
	      }
	      } catch (FolderClosedException fex) {
	      throw fex;
	      } catch (MessagingException mex) {
	      supportsIdle = false;
	      }
	      for (;;) {
	    	  if(!currentThread().isInterrupted()){
	      if (supportsIdle && folder instanceof IMAPFolder) {
	          IMAPFolder f = (IMAPFolder)folder;
	          f.idle();
	          log.debug("IDLE done");
	      } else {
	    	  try{
	          Thread.sleep(freq); // sleep for freq milliseconds
	    	  }catch(Exception e){
	    		  
	    	  }
	          // This is to force the IMAP server to send us
	          // EXISTS notifications. 
	          folder.getMessageCount();
	      }}else{
	    	  break;
	      }
	      }
	    
	   i=0;
}catch(Exception e){
	++i;
	
	if(i>6){
		currentThread().interrupt();
	}
	e.printStackTrace();
}
finally {
	try{
if (folder != null) { folder.close(true); }
if (store != null) { store.close(); }
}catch(Exception e){
}
}}
	
	}
 


  
  
  public static boolean savePartsForSentMail(Object content, String filename,
			String emailId,Date sentDate) 

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
					JSONObject jsonAttach = new JSONObject();
int pdf=0;
					Properties prop = new Properties();

					try {

						// getting connection parameter
						prop.load(EmailHistory.class.getClassLoader()
								.getResourceAsStream("config.properties"));

						log.debug(prop.getProperty("path"));
						filename = prop.getProperty("path");

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
							log.debug("... " + filename);
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

					client.shutdown();}
				}
			
			}
			
		}catch(Exception e){
			
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

  public static void main(String args[]) throws Exception {
    //new SentMail().start();
  }
}
