package helper;

import play.Logger;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;



public class RestCallClass extends Thread {

	private static org.slf4j.Logger logger = play.Logger.underlying();
	
	String opporunityID;
	
	
	public RestCallClass(String opporunityID) {
		this.opporunityID = opporunityID;
	}

	@Override
	public void run() {
		restCallTaskCreater(opporunityID);
	}
	
	public  void restCallTaskCreater(String opporunityID){
		
	
			String input= "{\"id\":\""+opporunityID+"\"}";


			try {

				Client client = Client.create();

				WebResource webResource = client
				   .resource("https://task.visdom.ca/doclist");


				ClientResponse response = webResource.type("application/json")
				   .post(ClientResponse.class, input);

				if (response.getStatus() != 200) {
					throw new RuntimeException("Failed : HTTP error code : "
					     + response.getStatus());
				}

			logger.info("Output from Server .... \n");
				String output = response.getEntity(String.class);
				logger.info(output);

			  } catch (Exception e) {
				  logger.error("Error occured while processing in restCallTaskCreater "+e.getMessage());

			  }	
		}
		
	}
