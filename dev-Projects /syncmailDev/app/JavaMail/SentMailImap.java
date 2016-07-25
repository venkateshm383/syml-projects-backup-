package JavaMail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.mail.Address;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;

import javax.mail.internet.MimeBodyPart;

import org.codehaus.jettison.json.JSONObject;

import Infrastracture.CouchBaseOperation;

import com.couchbase.client.CouchbaseClient;


public class SentMailImap  {

	//
	// inspired by :
	// http://www.mikedesjardins.net/content/2008/03/using-javamail-to-read-and-extract/
	Folder folder = null;
	Store store = null;

	public void sentMails(String emailId,String password ) {

		CouchbaseClient client = null;
		CouchBaseOperation cochbase = new CouchBaseOperation();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
				
				Properties prop = new Properties();
				String filename = null;
				try {

					// getting connection parameter
					prop.load(EmailHistory.class.getClassLoader()
							.getResourceAsStream("config.properties"));

					System.out.println(prop.getProperty("path"));
					filename = "/home/venkateshm/Desktop/helloworld/";
					// prop.getProperty("path");

				} catch (Exception e) {
					e.printStackTrace();
				}

				
					Date lastAccessdate = null;
					Date reciveMaildate = null;
					GregorianCalendar calendar = (GregorianCalendar) GregorianCalendar
							.getInstance();
					GregorianCalendar calendar2 = (GregorianCalendar) GregorianCalendar
							.getInstance();

					try {

						Properties props = System.getProperties();
						props.setProperty("mail.store.protocol", "imaps");

						Session session = Session.getDefaultInstance(props,
								null);

						// session.setDebug(true);
						store = session.getStore("imaps");
						store.connect("imap.gmail.com", emailId, password);
						folder = store.getFolder("[Gmail]/Sent Mail");
						/*
						 * Others GMail folders : [Gmail]/All Mail This folder
						 * contains all of your Gmail messages. [Gmail]/Drafts
						 * Your drafts. [Gmail]/Sent Mail Messages you sent to
						 * other people. [Gmail]/Spam Messages marked as spam.
						 * [Gmail]/Starred Starred messages. [Gmail]/Trash
						 * Messages deleted from Gmail.
						 */
						folder.open(Folder.HOLDS_MESSAGES);

						// System.out.println( "new message  "+folder.);

						System.out.println("new message  "
								+ folder.hasNewMessages());

						Message messages[] = folder.getMessages();

						System.out.println("No of Messages : "
								+ folder.getMessageCount());
						System.out.println("No of Unread Messages : "
								+ folder.getUnreadMessageCount());
						JSONObject json = null;
						String lastAccessTimeStamp = null;
						try {
							client = cochbase.getConnectionToCouchBase();
							json = new JSONObject(client.get(
									"lastAccessTimeStamp").toString());
							lastAccessTimeStamp = json.getString("SentMail_"
									+ emailId);
							client.shutdown();
						} catch (Exception e) {

						}
						System.out.println(folder.getUnreadMessageCount());
						if (lastAccessTimeStamp != null) {
							lastAccessdate = format.parse(lastAccessTimeStamp);
							calendar.setTime(lastAccessdate);
							for (int i = 0; i <0; ++i) {
								System.out.println("MESSAGE #" + (i + 1) + ":");
								Message msg = messages[i];

								/*
								 * if we don''t want to fetch messages already
								 * processed if (!msg.isSet(Flags.Flag.SEEN)) {
								 * String from = "unknown"; ... }
								 */

								calendar2.setTime(msg.getSentDate());
								if (calendar2.getTime().after(
										calendar.getTime())) {

									String from = "unknown";
									if (msg.getReplyTo().length >= 1) {
										from = msg.getReplyTo()[0].toString();
									} else if (msg.getFrom().length >= 1) {
										from = msg.getFrom()[0].toString();
									}
									String subject = msg.getSubject();
									String content = msg.getDescription();
									Address[] toEmail = msg.getAllRecipients();
									System.out.println("Saving ... " + subject
											+ " " + from);
									// you may want to replace the spaces with
									// "_"
									// the TEMP directory is used to store the
									// files
									filename +=subject;
									Boolean attacheSucess = false;
									attacheSucess = savePartsForSentMail(
											msg.getContent(), filename, emailId);
									// msg.setFlag(Flags.Flag.SEEN,true);
									// to delete the message
									// msg.setFlag(Flags.Flag.DELETED, true);
									JSONObject jsonObject = new JSONObject();
									jsonObject.put("toEmail", toEmail);
									jsonObject.put("from", emailId);
									jsonObject.put("subject", subject);

									jsonObject.put("SentDate",
											format.format(msg.getSentDate()));

									jsonObject.put("Descripation", content);

									if (attacheSucess) {
										jsonObject.put("Attachmane_id",
												"SentMail_Attachment_"
														+ emailId + "_"
														+ new Date().getTime());
									}

									client = cochbase
											.getConnectionToCouchBase();
									cochbase.storeDataInCouchBase("SentMails_"+emailId+"_"+format.format(msg.getSentDate()), jsonObject);
									GregorianCalendar calendar12 = (GregorianCalendar) GregorianCalendar
											.getInstance();

									if (json != null) {
										jsonObject.put("SentMail_" + emailId,
												format.format(calendar12
														.getTime()));
										cochbase.updateDataInCouchBase(
												"lastAccessTimeStamp",
												jsonObject);

									}
									client.shutdown();

								}

							}
						}

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
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
		
		
	

	public static boolean savePartsForSentMail(Object content, String filename,
			String emailId) throws IOException, MessagingException

	{

		boolean attachment = false;
		CouchBaseOperation cochbase = new CouchBaseOperation();

		CouchbaseClient client = null;
		OutputStream out = null;
		InputStream in = null;
		JSONObject jsonAttach = new JSONObject();
		try {
			if (content instanceof Multipart) {
				Multipart multi = ((Multipart) content);
				int parts = multi.getCount();
				for (int j = 0; j < parts; ++j) {

					Properties prop = new Properties();

					try {

						// getting connection parameter
						prop.load(EmailHistory.class.getClassLoader()
								.getResourceAsStream("config.properties"));

						System.out.println(prop.getProperty("path"));
						filename = "/home/venkateshm/Desktop/helloworld/";
						// prop.getProperty("path");

					} catch (Exception e) {
						e.printStackTrace();
					}
					MimeBodyPart part = (MimeBodyPart) multi.getBodyPart(j);
					if (part.getContent() instanceof Multipart) {
						// part-within-a-part, do some recursion...
						savePartsForSentMail(part.getContent(), filename,
								emailId);
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
							}
							filename = filename + "." + extension;
							System.out.println("... " + filename);
							out = new FileOutputStream(new File(filename));
							in = part.getInputStream();
							int k;
							while ((k = in.read()) != -1) {
								out.write(k);
							}
						}
					}
					try {
						Path path1 = Paths.get(filename);
						byte[] data1 = Files.readAllBytes(path1);

						jsonAttach.put("attachement" + j, data1);

					} catch (Exception e) {
						e.printStackTrace();
					}
					attachment = true;

				}
				client = cochbase.getConnectionToCouchBase();

				cochbase.storeDataInCouchBase("SentMail_Attachment_" + emailId
						+ "_" + new Date().getTime(), jsonAttach);

				client.shutdown();
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

	public static void main(String args[]) throws Exception {
		SentMailImap sentMailImap = new SentMailImap();
		//sentMailImap.start();

	}

}
