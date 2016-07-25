package JavamailMessagCounter;

public class UpdateForSentMailThreadName extends Thread {
	
	
	
	
	
	
	
	
	String email;
	String password;
	Thread thread;
	public UpdateForSentMailThreadName(String email, String password, Thread thread) {
		this.email = email;
		this.password = password;
		this.thread = thread;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		while (!thread.isAlive()) {
			
			 SentMail sentMail =new SentMail(email, password);
			 sentMail.start();
			 sentMail.setName("sent"+email);
			
		}
		
	}
	

}
