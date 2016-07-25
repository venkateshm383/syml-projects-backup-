import controllers.TrigerListener;
import play.Application;
import play.GlobalSettings;
import play.Logger;


public class Global extends GlobalSettings {
	private static org.slf4j.Logger logger = play.Logger.underlying();
	
	@Override
	public void onStart(Application app) {
		// TODO Auto-generated method stub
	new	TrigerListener().callListner();
	logger.info("appp started----------------");
		
	}
	
	@Override
	public void onStop(Application app) {
		// TODO Auto-generated method stub
		
		logger.info("appp stopped----------------");

		new	TrigerListener().closeDbConnection();

	}

}
