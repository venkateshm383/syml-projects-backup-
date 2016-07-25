package JavamailMessagCounter;

public class UpdateForInoboxThreadName extends Thread {
	
	
	
	String email;
	String password;
	Thread thread;
	public UpdateForInoboxThreadName(String email, String password, Thread thread) {
		this.email = email;
		this.password = password;
		this.thread = thread;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		while (!thread.isAlive()) {
			
			
			  ReceiveMailImap receiveMailImap= new	ReceiveMailImap(email,password);
 			 receiveMailImap.start();
 			 receiveMailImap.setName("inbox"+email);
 			 
			
		}
		
		
		
	}
	

}
