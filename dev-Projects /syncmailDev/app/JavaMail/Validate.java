package JavaMail;

import java.util.Properties;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;

public class Validate {

	Folder folder = null;
	  Store store = null;
	public boolean validate(String emailId,String password){
		boolean sucess=false;
		try{
		  Properties props = System.getProperties();
	      props.setProperty("mail.store.protocol", "imaps");
	    
	      Session session = Session.getDefaultInstance(props, null);
	 

	      // session.setDebug(true);
	      store = session.getStore("imaps");
	      store.connect("imap.gmail.com",emailId, password);
	      folder = store.getFolder("Inbox");
	      /* Others GMail folders :
	       * [Gmail]/All Mail   This folder contains all of your Gmail messages.
	       * [Gmail]/Drafts     Your drafts.
	       * [Gmail]/Sent Mail  Messages you sent to other people.
	       * [Gmail]/Spam       Messages marked as spam.
	       * [Gmail]/Starred    Starred messages.
	       * [Gmail]/Trash      Messages deleted from Gmail.
	       */
	      folder.open(Folder.HOLDS_MESSAGES);
	  
	     // System.out.println( "new message  "+folder.);
	    
	      

	      Message messages[] = folder.getMessages();
	      sucess=true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return sucess;
	}
}
