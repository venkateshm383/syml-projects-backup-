import java.util.Iterator;
import java.util.List;

import org.codehaus.jettison.json.JSONException;

import controllers.EmailList;

import Infrastracture.CouchBaseOperation;
import JavaMail.RecivedMailImap;
import JavaMail.SentMailImap;
import JavamailMessagCounter.ReceiveMailImap;
import JavamailMessagCounter.SentMail;
import play.Application;
import play.GlobalSettings;


public class Global extends GlobalSettings {
	
	@Override
	public void onStart(Application app) {
		// TODO Auto-generated method stub
	 CouchBaseOperation couhBaseOperation=new CouchBaseOperation();
		try {
			couhBaseOperation.storeAdminDetailsCouhbase("admin", "admin");
			
			

	    	 
	    	List<EmailList> user=couhBaseOperation.getEmailList();
	    	if(user.isEmpty()){
	    		try{
	    			Thread.sleep(6000);
	    		}catch(Exception e){
	    			
	    		}
	    		user=couhBaseOperation.getEmailList();
	    	}
	    	
	    	for (Iterator iterator = user.iterator(); iterator.hasNext();) {
				EmailList emailList = (EmailList) iterator.next();
				ReceiveMailImap receiveMailImap = new ReceiveMailImap(emailList.getVisdomemailid(),
						emailList.getVisdompassword());
				receiveMailImap.start();
				receiveMailImap.setName("inbox" + emailList.getVisdomemailid());

				SentMail sentMail = new SentMail(emailList.getVisdomemailid(), emailList.getVisdompassword());
				sentMail.start();
				sentMail.setName("sent" + emailList.getVisdomemailid());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("appp started----------------");

	}
	
	@Override
	public void onStop(Application app) {
		// TODO Auto-generated method stub
		
		System.out.println("appp stopped----------------");

		

	}

}
