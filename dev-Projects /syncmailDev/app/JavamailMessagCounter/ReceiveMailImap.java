package JavamailMessagCounter;

import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.imageio.ImageIO;
import javax.mail.*;
import javax.mail.event.FolderEvent;
import javax.mail.event.FolderListener;
import javax.mail.event.MessageCountAdapter;
import javax.mail.event.MessageCountEvent;
import javax.mail.internet.*;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDPixelMap;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;
import net.iharder.Base64;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import Infrastracture.CouchBaseOperation;
import Infrastracture.GetOpprtunityData;
import JavaMail.EmailHistory;
import JavaMail.RecivedMailImap;

import com.couchbase.client.CouchbaseClient;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.sun.mail.imap.IMAPFolder;

import controllers.Applicants;
import controllers.Opprtunity;

public class ReceiveMailImap extends Thread {
	static Logger log = LoggerFactory.getLogger(EmailHistory.class);

	String emailId;

	public ReceiveMailImap(String emailId, String password) {
		super();
		this.emailId = emailId;
		this.password = password;
	}

	String password;

	CouchbaseClient client = null;
	CouchBaseOperation cochbase = new CouchBaseOperation();

	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	Properties prop = new Properties();

	String filename = null;

	//
	// inspired by :
	// http://www.mikedesjardins.net/content/2008/03/using-javamail-to-read-and-extract/
	//
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		int i=0;
		while (!currentThread().isInterrupted()) {
			Folder folder = null;
			Store store = null;

			try {

				prop.load(RecivedMailImap.class.getClassLoader()
						.getResourceAsStream("config.properties"));

				System.out.println(prop.getProperty("couchBaseUrl"));
				filename = prop.getProperty("path");

			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				Properties props = System.getProperties();
				props.setProperty("mail.store.protocol", "imaps");

				Session session = Session.getDefaultInstance(props, null);
				// session.setDebug(true);
				store = session.getStore("imaps");
				store.connect("imap.gmail.com", emailId, password);
				folder = store.getFolder("Inbox");
				/*
				 * Others GMail folders : [Gmail]/All Mail This folder contains
				 * all of your Gmail messages. [Gmail]/Drafts Your drafts.
				 * [Gmail]/Sent Mail Messages you sent to other people.
				 * [Gmail]/Spam Messages marked as spam. [Gmail]/Starred Starred
				 * messages. [Gmail]/Trash Messages deleted from Gmail.
				 */
				folder.open(Folder.READ_WRITE);

				System.out.println("inbox messages");

				Message messages[] = folder.getMessages();
				/*
				 * System.out.println("No of recive Messages : " +
				 * folder.getMess projectName=jsonEventdata.get("name")
				 * ageCount());
				 */
				System.out.println("No of Unread Messages : "
						+ folder.getUnreadMessageCount());

				folder.addMessageCountListener(new MessageCountAdapter() {
					public void messagesAdded(MessageCountEvent ev) {
						Message[] msgs = ev.getMessages();
						System.out.println("Got " + msgs.length
								+ " new messages");

						// Just dump out the new messages
						for (int i = 0; i < msgs.length; i++) {
							try {
								System.out
										.println("-----inbox mailllllllllllll");

								Message msg = msgs[i];

								String from = "unknown";
								try {
									if (msg.getReplyTo().length >= 1) {
										from = msg.getReplyTo()[0].toString();
									} else if (msg.getFrom().length >= 1) {
										from = msg.getFrom()[0].toString();
									}
									from = ((InternetAddress) ((Address) (msg
											.getFrom()[0]))).getAddress();
								} catch (Exception e) {
									e.printStackTrace();
								}

								Opprtunity opprtunity = null;
								JSONObject jsonObject = new JSONObject();
								String opprunityName = "";
								String opportunityId = "";
								String opportunityEmail = "";
								JSONObject doclist = null;

								UUID uuid = UUID.randomUUID();

								String uniqueId = uuid.toString();
								String applicantName = "";
								try {
									try {
										opprtunity = new GetOpprtunityData()
												.getApplicantDetials(from);
										System.out.println("opprtunity"
												+ opprtunity);
									} catch (Exception e) {

									}

									if (opprtunity != null) {

										if (opprtunity.getOpprtunityId() != null) {
											System.out.println("opporunity id "
													+ opprtunity
															.getOpprtunityId());
											opportunityId = opprtunity
													.getOpprtunityId();
											jsonObject.put("opprtunityId1",
													opprtunity
															.getOpprtunityId());

										}

										if (opprtunity.getOpprtunityEmail() != null) {
											opportunityEmail = opprtunity
													.getOpprtunityEmail();
											jsonObject
													.put("opprtunityEmail",
															opprtunity
																	.getOpprtunityEmail());
										}

										if (opprtunity.getOpprtunityName() != null) {
											opprunityName = opprtunity
													.getOpprtunityName();
											jsonObject
													.put("opprtunity",
															opprtunity
																	.getOpprtunityName());
										}
										Set<Applicants> applicantsSet = opprtunity
												.getApplicants();
										int k = 0;
										for (Iterator iterator = applicantsSet
												.iterator(); iterator.hasNext();) {
											Applicants applicants = (Applicants) iterator
													.next();
											++k;
											if (from.equalsIgnoreCase(applicants
													.getApplicantEmail())) {
												applicantName = applicants
														.getApplicantName();
											}
											jsonObject
													.put("applicantName" + k,
															applicants
																	.getApplicantName());
											jsonObject
													.put("applicantEmail" + k,
															applicants
																	.getApplicantEmail());
											jsonObject
													.put("applicantId" + k,
															applicants
																	.getApplicantId());

										}
										jsonObject.put("recivedFromEmailId",
												from);
										jsonObject.put("Type", "DocOriginal");
										jsonObject.put("Type_DocOriginal",
												"DocOriginal");
										try {
											doclist = cochbase
													.getDocument("DocumentListOfApplicationNo_"
															+ opportunityId);

										} catch (Exception e) {

										}

									} else {
										jsonObject.put("Type", "DocOriginal");
										jsonObject.put("recivedFromEmailId",
												from);
										jsonObject.put("Type_DocOriginal",
												"DocOriginal");

										jsonObject.put("opprtunityId1",
												opportunityId);
										jsonObject.put("opprtunityEmail",
												opportunityEmail);
										jsonObject.put("opprtunity",
												opprunityName);
										jsonObject.put("applicantName1", "");

									}

								} catch (Exception e) {
									e.printStackTrace();
								}

								System.out.println("received from "
										+ ((InternetAddress) ((Address) (msg
												.getFrom()[0]))).getAddress());
								String subject = msg.getSubject();
								String cnt = "";
								try {

									Object content1 = msg.getContent();
									MimeMultipart mimeMultipart = (MimeMultipart) content1;
									BodyPart part = null;
									System.out.println("part size"
											+ mimeMultipart.getCount());

									part = mimeMultipart.getBodyPart(0);

									String disposition = part.getDisposition();
									String contentType = part.getContentType();
									if (disposition == null) { // When just body
										System.out.println("Null: "
												+ contentType);
										// Check if plain
										if ((contentType.length() >= 10)
												&& (contentType.toLowerCase()
														.substring(0, 10)
														.equals("text/plain"))) {
											System.out.println(""
													+ part.getContent());
										} else if ((contentType.length() >= 9)
												&& (contentType.toLowerCase()
														.substring(0, 9)
														.equals("text/html"))) {
											part.writeTo(System.out);
										} else if ((contentType.length() >= 9)
												&& (contentType.toLowerCase()
														.substring(0, 9)
														.equals("text/html"))) {
											System.out
													.println("Ook html gevonden");
											part.writeTo(System.out);
										} else {
											System.out.println("Other body: "
													+ part.toString());

											InputStream inputStream = part
													.getInputStream();
											System.out.println(inputStream);

											BufferedReader br = null;
											StringBuilder sb = new StringBuilder();

											String line;
											try {

												br = new BufferedReader(
														new InputStreamReader(
																inputStream));
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
											System.out.println("datat "
													+ sb.toString());
											cnt = sb.toString();
										}
									}

								} catch (Exception e) {
									e.printStackTrace();
								}

								Address[] toEmail = msg.getAllRecipients();

								String recipinets = "";
								for (int j = 0; j < toEmail.length; j++) {
									recipinets += toEmail[j];
								}

								Address[] fromEmail = msg.getFrom();
								String sender = "";
								try {
									for (int j = 0; j < fromEmail.length; j++) {
										sender += fromEmail[j];
									}

								} catch (Exception e) {
									e.printStackTrace();
								}

								filename = filename + subject
										+ format.format(msg.getReceivedDate());
								ArrayList<JSONArray> attachment = savePartsForInbox(
										msg.getContent(), filename, emailId,
										msg.getReceivedDate(), jsonObject);

								JSONArray attachmentsArray = null;
								JSONArray originalDocArray = null;
								try {
									attachmentsArray = attachment.get(0);
									originalDocArray = attachment.get(1);
								} catch (Exception e) {

								}
								System.out.println("filename " + filename); //
								
								// msg.setFlag(Flags.Flag.SEEN,true);
								// to delete the message
								// msg.setFlag(Flags.Flag.DELETED, true);

								System.out.println("descripation -------------"
										+ msg.getContent().toString());
								jsonObject = new JSONObject();
								jsonObject.put("Type", "Email");
								jsonObject.put("Direction", "Inbox");
								jsonObject.put("Type_Email", "Email");

								jsonObject.put("toEmail", recipinets);
								jsonObject.put("from", from);
								jsonObject.put("subject", subject);
								jsonObject.put("body", cnt);
								jsonObject.put("EmailSource", "gmail");
								jsonObject.put("Opportunity", opprunityName);
								jsonObject.put("applicantName1", applicantName);

								if (opprtunity != null) {

									JSONArray json = new JSONArray();
									JSONObject jsn = new JSONObject();
									jsn.put("applicant", true);
									jsn.put("opportunity", true);
									json.put(jsn);

									jsonObject.put("Matched", json);
								} else {
									JSONArray json = new JSONArray();
									JSONObject jsn = new JSONObject();
									jsn.put("applicant", false);
									jsn.put("opportunity", false);
									json.put(jsn);

									jsonObject.put("Matched", json);
								}
								jsonObject
										.put("OriginalDocs", originalDocArray);

								jsonObject.put("reciveDate",
										format.format(msg.getReceivedDate()));

								jsonObject.put("Attachments", attachmentsArray);

								client = cochbase.getConnectionToCouchBase();
								cochbase.storeDataInCouchBase(
										"Inbox_"
												+ from
												+ "_"
												+ format.format(msg
														.getReceivedDate()),
										jsonObject);

								try {
									doclist.put("Type", "DocMaster");
									doclist.put("Type_DocMaster", "DocMaster");
									doclist.put("CrmURL", opportunityId);
									try {
										doclist.put("DocMasterUrl",
												originalDocArray.get(0));
									} catch (Exception e) {
										e.printStackTrace();
									}
									doclist.put("masterdoc_opporunityName",
											opprtunity);
									
									cochbase.updateDataInCouchBase(
											"DocumentListOfApplicationNo_"
													+ opprtunity, doclist);
								} catch (Exception e) {

								}
								System.out.println("doc key " + "Inbox_" + from
										+ "_"
										+ format.format(msg.getReceivedDate()));
								client.shutdown();
								// msgs[i].writeTo(System.out);
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
						IMAPFolder f = (IMAPFolder) folder;
						f.idle();
						supportsIdle = true;
					}
				} catch (FolderClosedException fex) {
					throw fex;
				} catch (MessagingException mex) {
					supportsIdle = false;
				}
				for (;;) {

					if (!currentThread().isInterrupted()) {
						System.out
								.println("thred running---------------------");

						if (supportsIdle && folder instanceof IMAPFolder) {
							IMAPFolder f = (IMAPFolder) folder;
							f.idle();
							System.out.println("IDLE done");
						} else {
							try {
								Thread.sleep(freq); // sleep for freq
													// milliseconds
							} catch (Exception e) {

							}
							// This is to force the IMAP server to send us
							// EXISTS notifications.
							folder.getMessageCount();
						}
					} else {
						System.out.println("thred stoped---------------------");
						break;
					}
				}

				i=0;
			} catch (Exception e) {
				
				++i;
				
				if(i>6){
					currentThread().interrupt();
				}
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
				}
			}
		}

	}

	public static ArrayList<JSONArray> savePartsForInbox(Object content,
			String filename, String emailId, Date reciveDate,
			JSONObject jsonObject)

	{
		
		ArrayList<String> list=new ArrayList<String>();

		ArrayList<JSONArray> attachmentList = new ArrayList<JSONArray>();
		JSONArray attachmentArray = new JSONArray();
		JSONArray orginalDocArray = new JSONArray();
		boolean attachment = false;
		CouchBaseOperation cochbase = new CouchBaseOperation();

		CouchbaseClient client = null;
		OutputStream out = null;
		InputStream in = null;
		try {

			try {
				if (content instanceof Multipart) {
					Multipart multi = ((Multipart) content);
					int parts = multi.getCount();
					for (int j = 1; j < parts; ++j) {
						UUID uid = UUID.randomUUID();
						String attachmentName="";
						System.out
								.println("number of attachments------------------> "
										+ parts);
						int pdf = 0;
						JSONObject jsonAttach = new JSONObject();

						Properties prop = new Properties();

						try {

							// getting connection parameter
							prop.load(EmailHistory.class.getClassLoader()
									.getResourceAsStream("config.properties"));

							System.out.println(prop.getProperty("path"));
							filename = prop.getProperty("path");

						} catch (Exception e) {
							e.printStackTrace();
						}
						MimeBodyPart part = (MimeBodyPart) multi.getBodyPart(j);
						if (part.getContent() instanceof Multipart) {
							// part-within-a-part, do some recursion...
							savePartsForInbox(part.getContent(), filename,
									emailId, reciveDate, jsonObject);
							System.out.println("fielname");
							pdf = 1;
						} else {
							String extension = "";
							System.out.println("file type -----------------");
							if (part.isMimeType("text/html")) {
								extension = "html";
							} else {
								
								
								if (part.isMimeType("text/plain")) {
									extension = "txt";
								} else if(part.isMimeType("image/png")||part.isMimeType("image/jpeg")) {
									
									extension= part.getDataHandler().getName();
									filename=filename + ""+ extension;
									list.add(filename);
									
									
									System.out.println("images file  file   ----------"+filename);


																		
								}else{
									// Try to get the name of the attachment
									extension = part.getDataHandler().getName();
									pdf = 1;
									System.out.println("pdf file   ----------"+filename);

								}
								try {
									filename = filename + ""+ extension;
									System.out.println("file name  ----------"+filename);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							attachmentName=extension;
						}
						client = cochbase.getConnectionToCouchBase();
						if (pdf == 1) {
							try {

								Path path1 = Paths.get(filename);
								byte[] data1 = Files.readAllBytes(path1);
								String encodeData = net.iharder.Base64
										.encodeBytes(data1);
								System.out.println(encodeData.length());
								jsonAttach.put("attachement", encodeData);
								cochbase.storeBinaryDataInCouchBase("doc_"
										+ emailId + "_" + uid.toString(),
										jsonAttach);
								Thread.sleep(2000);
								attachmentArray.put("doc_" + emailId + "_"
										+ uid.toString());
								try {
									File file = new File(filename);
									file.delete();
								} catch (Exception e) {

								}

							} catch (Exception e) {
								e.printStackTrace();
							}
							attachment = true;

							jsonObject.put("DocMasterUrl", "OriginalDocs_"
									+ uid.toString());
							jsonObject.put("OriginalDocumentName", attachmentName);

							jsonObject.put("TimesSplit", 0);
							jsonObject.put("OriginalURL", "doc_" + emailId
									+ "_" + uid.toString());
							jsonObject.put("BinaryID", "doc_" + emailId + "_"
									+ uid.toString());
							jsonObject.put("SplitterURL",
									"OriginalDocs_" + uid.toString());
							cochbase.storeDataInCouchBase(
									"OriginalDocs_" + uid.toString(),
									jsonObject);
							orginalDocArray.put("OriginalDocs_"
									+ uid.toString());
							client.shutdown();
						}else{
							
						}
					}
					String filePath="";
					try {
Properties prop=new Properties();
						prop.load(RecivedMailImap.class.getClassLoader()
								.getResourceAsStream("config.properties"));

						System.out.println(prop.getProperty("couchBaseUrl"));
						filePath = prop.getProperty("path");

					} catch (Exception e) {
						e.printStackTrace();
					}
					
					UUID uid = UUID.randomUUID();
try{
	filePath=filePath+uid+".pdf";
						PDDocument document =new PDDocument();
System.out.println("number of image attachments size  "+list.size());
						BufferedImage image=null;
						for(int i=0;i<list.size();i++){
							PDPage page1 =new PDPage(PDPage.PAGE_SIZE_A4);
							File filedata=new File(list.get(i));
							String pdfFile=Base64.encodeFromFile(list.get(i));
							PDRectangle rect=page1.getMediaBox();
							document.addPage(page1);
							byte[] decodedBytes = Base64.decode(pdfFile);
							PDPageContentStream cos=new PDPageContentStream(document, page1);

							if (decodedBytes == null) {
								System.out.println("decodedBytes  is null");
							}

							image = ImageIO.read(new ByteArrayInputStream(decodedBytes));
							PDFont fontplain = PDType1Font.TIMES_ROMAN;
							PDFont fontbold=PDType1Font.HELVETICA_BOLD;
							PDFont fontItalic =PDType1Font.HELVETICA_OBLIQUE;
							PDFont fontMono=PDType1Font.COURIER;
							
							
							
							
							int line=0;
							  
						
							int line2=500;

						  try {
					           // BufferedImage awtImage = ImageIO.read(new File("simple.jpg"));
					            PDXObjectImage ximage = new PDPixelMap(document, image);
					           float scale = 0.35f; // alter this value to set the image size
					        //  cos.drawImage(ximage, 180, 700);
					          cos.drawXObject(ximage,70,rect.getHeight() -100-line2 , ximage.getWidth()*scale, ximage.getHeight()*scale);
						  } catch (Exception fnfex) {
					            System.out.println("No image for you");
					        }
					        cos.close();
					        System.out.println("image is craeted");
						}
						

						document.save(filePath);
						document.close();
}catch(Exception e){
	e.printStackTrace();
}
	
						try {
JSONObject jsonAttach=new JSONObject();
							Path path1 = Paths.get(filePath);
							byte[] data1 = Files.readAllBytes(path1);
							String encodeData = net.iharder.Base64
									.encodeBytes(data1);
							System.out.println(encodeData.length());
							jsonAttach.put("attachement", encodeData);
							cochbase.storeBinaryDataInCouchBase("doc_"
									+ emailId + "_" + uid.toString(),
									jsonAttach);
							Thread.sleep(2000);
							attachmentArray.put("doc_" + emailId + "_"
									+ uid.toString());
							try {
								File file = new File(filePath);
								file.delete();
							} catch (Exception e) {

							}

						} catch (Exception e) {
							e.printStackTrace();
						}
						attachment = true;

						jsonObject.put("DocMasterUrl", "OriginalDocs_"
								+ uid.toString());
						jsonObject.put("OriginalDocumentName", "ImagePdf");

						jsonObject.put("TimesSplit", 0);
						jsonObject.put("OriginalURL", "doc_" + emailId
								+ "_" + uid.toString());
						jsonObject.put("BinaryID", "doc_" + emailId + "_"
								+ uid.toString());
						jsonObject.put("SplitterURL",
								"OriginalDocs_" + uid.toString());
						cochbase.storeDataInCouchBase(
								"OriginalDocs_" + uid.toString(),
								jsonObject);
						orginalDocArray.put("OriginalDocs_"
								+ uid.toString());
						client.shutdown();	
							
					
					
						
						
					

				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.flush();
					out.close();
				}
			} catch (Exception e) {

			}
		}
		attachmentList.add(attachmentArray);
		attachmentList.add(orginalDocArray);
		return attachmentList;
	}

	public static void main(String args[]) throws Exception {
		new ReceiveMailImap("assistant@visdom.ca", "Visdom@123").start();
	}
}