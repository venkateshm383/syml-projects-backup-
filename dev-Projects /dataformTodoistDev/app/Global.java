import infrastracture.CouchBaseOperation;

import org.codehaus.jettison.json.JSONException;


import play.Application;
import play.GlobalSettings;


public class Global extends GlobalSettings {
	
	@Override
	public void onStart(Application app) {
		// TODO Auto-generated method stub
		CouchBaseOperation couhBaseOperation=new CouchBaseOperation();
		try {
			couhBaseOperation.storeAdminDetailsCouhbase("admin", "admin");
			
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
