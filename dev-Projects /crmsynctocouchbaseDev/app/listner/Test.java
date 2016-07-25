package listner;

import java.sql.Connection;

import play.Logger;

public class Test {
	private static org.slf4j.Logger logger = Logger.underlying();
	public static void main(String[] args) {
		
		
		Connection con=null;
		
		
		try{
			
		}catch(Exception e){
			logger.error("Error occured while connection "+e.getMessage());	
		}
		
		
	}
}
