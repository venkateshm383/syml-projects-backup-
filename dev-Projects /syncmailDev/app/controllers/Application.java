package controllers;

import static play.data.Form.form;



import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.couchbase.client.CouchbaseClient;
import com.fasterxml.jackson.databind.JsonNode;




import Infrastracture.CouchBaseOperation;
import Infrastracture.EmailDataFromMailGun;

import JavaMail.Validate;
import JavamailMessagCounter.EmailHistory;
import JavamailMessagCounter.ReceiveMailImap;
import JavamailMessagCounter.SentMail;
import JavamailMessagCounter.UpdateForInoboxThreadName;
import JavamailMessagCounter.UpdateForSentMailThreadName;



import play.*;
import play.data.DynamicForm;
import play.data.Form;
import play.data.validation.Constraints.Required;

import play.mvc.*;

import views.html.*;

public class Application extends Controller {
 	static Logger log = LoggerFactory.getLogger(Application.class);

	public static class Login {
	
 public String email;
	
   public String password;

	}
     
    
    // -- Actions
  
    /**
     * Home page
     * 
     * 
     */
	public static Result index(){
		return ok(
           		login.render("")

           );	
	}
	
    public static Result backToForm() {
       if(session().get("test")!=null&&session().get("test").equalsIgnoreCase("test")&&checkSession()){

    	return ok(
           		index.render(form(Login.class))

           );
        }else{
        	return ok(
               		login.render("Session is Expired")

               );	
        }
       /* return ok(
            index.render(form(Login.class))
        );*/
    }
  
    /**
     * Handles the form submission.
     */
    
    
    public static Result loginSubmit(){
        DynamicForm dynamicForm = form().bindFromRequest();
        System.out.println(dynamicForm.get("userName")+" pasword "+dynamicForm.get("password"));
         String userName=null;
         String password=null;
         userName=dynamicForm.get("userName");
         password=dynamicForm.get("password");
         boolean tryedLogin=checkTryedForLogin();
         if(tryedLogin){
         if(userName==null||userName.isEmpty()||userName.equals("")||password==null||password.isEmpty()||password.equals("")){
    		 loginFailed();

        	 return ok(
             		login.render("Please Enter  Proper UserName And Password")

             );
         }else{
        	
        CouchBaseOperation couOperation=	 new CouchBaseOperation();
        	 boolean success=couOperation.getLogin(userName, password);
        	 if(success){
        	session().put("test", "test");
        	GregorianCalendar cal=new GregorianCalendar();
        	Date date=cal.getTime();
        session().put("userTime", date.getTime()+"");
        try{
    JSONObject    json=new JSONObject();
		json.put("try", 0);
		json.put("time", 0);
		couOperation.updateDataInCouchBase("tryLoginSyncMail", json);
        }catch(Exception e){
        	
        }
        		 return ok(
                   		index.render(form(Login.class))

                   );
        	 }else{
        		 loginFailed();
        		 return ok(
                  		login.render("Please Enter  Proper UserName And Password")

                  );
        	 }
        	
         }
        
         }else{
        	 return ok(
               		login.render("Your Account Locked for 15 minutes")

               ); 
         }
        
        
    }
    
    
    public static Result addemail(){
        
       
     if(session().get("test")!=null&&session().get("test").equalsIgnoreCase("test")&&checkSession()){
        	DynamicForm dynamicForm = form().bindFromRequest();
        	GregorianCalendar cal=new GregorianCalendar();
        	Date date=cal.getTime();
        session().put("userTime", date.getTime()+"");
        String email=dynamicForm.get("email");
        String password=dynamicForm.get("password");
   
     if(email==null||email.isEmpty()||email.equals("")||password==null||password.isEmpty()||password.equals("")){
        	
           
            return ok(
                    hello.render("Please Enter the Proper Email And Password")
                );
        }else{
        	 boolean sucess=true;
        	 try{
             sucess=new Validate().validate(email, password);
        	 }catch(Exception e){
        		 e.printStackTrace();
        	 }
             String result="";
             if(sucess){
            	new EmailHistory(email, password).start();
            	 result="User Email Detials Added Sucessfully";
            	 return ok(
                         hello.render(result)

                 );
             }else{
            	 result="Please Enter emailId  and password";
           
                return ok(
                        hello.render("Please Enter  Proper Email And Password")

                );
         }
        }
        }else{
        	 return ok(
               		login.render("Session is Expired")

               );
        }
    }
    
    
    public static Result editEmail(){
    	String key=request().getQueryString("key");
        EmailList email=new EmailList();

    	try{
       	 CouchBaseOperation cochbase=new CouchBaseOperation();
       	 
         JSONObject json=new JSONObject(cochbase.getDocument(key).toString());
         email.setVisdomemailid(json.getString(json.get("visdomemailid").toString().trim()));
         
       String  pass=cochbase.decryptPassword(json.get("visdompassword").toString().trim());
		 pass=pass.substring(6, pass.length());
         email.setVisdompassword(pass);
         email.setKey(key);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	
    	return ok(update.render(email));
    }
    
    public static Result updateEmail() {
	  if(session().get("test")!=null&&session().get("test").equalsIgnoreCase("test")&&checkSession()){
CouchBaseOperation couchbase=new CouchBaseOperation();
GregorianCalendar cal=new GregorianCalendar();
Date date=cal.getTime();
session().put("userTime", date.getTime()+"");
    	 DynamicForm dynamicForm = form().bindFromRequest();
    	 String email=dynamicForm.get("email");
         String password=dynamicForm.get("password");
         String key=dynamicForm.get("key");
         System.out.println("keyyyyyyyyyyy-------------------"+key);
         if(email==null||email.isEmpty()||email.equals("")||password==null||password.isEmpty()||password.equals("")){

             return ok(
                     hello.render("Please Enter the Proper Email And Password")
                 );
         }else{
        	 
        	 boolean sucess=true;
        	 try{
             sucess=new Validate().validate(email, password);
        	 }catch(Exception e){
        		 
        	 }
        	 if(sucess){
        	 String encrpptPassword=null;
        	 encrpptPassword=couchbase.encryptPassword(password);
        	 JSONObject jsonObject=new JSONObject();
        	 try{
        	 jsonObject.put("visdomemailid", email);
        	 jsonObject.put("visdompassword", encrpptPassword);
        	 }catch(Exception e){
        		 
        	 }
        	 couchbase.updateDataInCouchBase(key, jsonObject);
        	 Thread [] thredList=couchbase.getThreadGroup();
        	  if(thredList!=null){
        		  for (Thread thread : thredList) {
        			  
        			  if(thread.getName().trim().equalsIgnoreCase("inbox"+email.trim())){
        				  
        				  thread.interrupt();
        				  new UpdateForInoboxThreadName(email, password, thread).start();
           				  System.out.println("update inbox thread interupted-------------------------------------------->");

        			  }
        			  if(thread.getName().trim().equalsIgnoreCase("sent"+email.trim())){
        				  thread.interrupt();
        				  new UpdateForSentMailThreadName(email, password, thread).start();

           				  System.out.println("Update sent thread interupted-------------------------------------------->");

        			  }
				}
        	  }
        	 /* try{
        		  
        		  ReceiveMailImap receiveMailImap= new	ReceiveMailImap(email,password);
        			 receiveMailImap.start();
        			 receiveMailImap.setName("inbox"+email);
        			 
        			 SentMail sentMail =new SentMail(email, password);
        			 sentMail.start();
        			 sentMail.setName("sent"+email);
        	  }catch(Exception e){
        		  
        	  }
        	  */
        	  return ok(
               		 hello.render("Email Details Updated Sucessfully")
                );
        	 }else{
        		 return ok(
                   		 hello.render("Please Enter proper Email id and Password")
                    );
        	 }
         }

      
              
  	}else{
  		return ok(
  				login.render("Session is Expired"));
  	 	}
        
    }
    
    
    public static Result getEmailDetails() {
  	  if(session().get("test")!=null&&session().get("test").equalsIgnoreCase("test")&&checkSession()){
   		  GregorianCalendar cal=new GregorianCalendar();
          	Date date=cal.getTime();
          session().put("userTime", date.getTime()+"");
    	 CouchBaseOperation cochbase=new CouchBaseOperation();
    	List<EmailList> user=cochbase.getEmailList();
    	if(user.isEmpty()){
    		try{
    			Thread.sleep(6000);
    		}catch(Exception e){
    			
    		}
    		user=cochbase.getEmailList();
    	}
		return ok(list.render(user));
		

}else{
	return ok(
			login.render("Session is Expired"));
 	}
 
}
    
    
    public static Result deleteEmail(String key) {
    	String email=null;
 	  if(session().get("test")!=null&&session().get("test").equalsIgnoreCase("test")&&checkSession()){
		 CouchBaseOperation cochbase=new CouchBaseOperation();
  		GregorianCalendar cal=new GregorianCalendar();
    	Date date=cal.getTime();
    session().put("userTime", date.getTime()+"");
  		 	boolean sucess=false;
  		 	System.out.println("key to delte"+key);
        try{
   CouchbaseClient client=     	cochbase.getConnectionToCouchBase();
   JSONObject json=new JSONObject(client.get(key).toString());
   email=json.get("visdomemailid").toString();
   					client.delete((key.trim()).toString());
        	client.shutdown(9000l, TimeUnit.MILLISECONDS);
        	sucess=true;
        }catch(Exception e){
        	 e.printStackTrace();
        }
        if(sucess){
        	 Thread [] thredList=cochbase.getThreadGroup();
       	  if(thredList!=null){
       		  for (Thread thread : thredList) {
       			  
       			  if(thread.getName().trim().equalsIgnoreCase("inbox"+email.trim())){
       				  thread.interrupt();
       				  
       				  System.out.println("inbox thread interupted-------------------------------------------->");
       			  }
       			  if(thread.getName().trim().equalsIgnoreCase("sent"+email.trim())){
       				  thread.interrupt();
       				  System.out.println("sentbox thread interupted-------------------------------------------->");

       			  }
				}
       	  }
       	  
        	return ok(
                    hello.render("Email Details Deleted Sucessfully")
               );
        }else{
        	return ok(
                    hello.render("Error in Deleting Email Details")
               );
        }
       
        
  	}else{
  		return ok(
  				login.render("Session is Expired"));
  	 	}
  	 
 
}
public static Result getDocument() throws JSONException  {
	Map<String, String[]> body = request().body().asFormUrlEncoded();
	if (body == null) {
		return badRequest("Expecting Json data");
	} else {
		try{
		JSONObject jsonName=new JSONObject(play.libs.Json.toJson(body).toString());

	new EmailDataFromMailGun().storeMailGundata(jsonName);}catch(Exception e){
		
	}
			return ok("Hello ");
		
	}
}
    
    
public static Result logout(){
	try{
	session().clear();
	return ok(
            login.render("")
        );
	}catch(Exception e){
		return ok(
	            login.render("")
	        );
	}
}
  
public static Result GetUser(){
  if(session().get("test")!=null&&session().get("test").equalsIgnoreCase("test")&&checkSession()){
   	GregorianCalendar cal=new GregorianCalendar();
    	Date date=cal.getTime();
    session().put("userTime", date.getTime()+"");
    	JSONObject json=null;
    	String name="";
    	String password="";
    	CouchBaseOperation couhbase=  	new CouchBaseOperation();
    	try{
    		json=new JSONObject(couhbase.getDocument("syncMail").toString());
    	System.out.println("user data "+json);
    	}catch(Exception e){
    		
    	}
    	if(json!=null){
    		try{
    		name=json.get("username").toString();
    		password=couhbase.decryptPassword(json.get("password").toString());
    		   try{
    		       password=password.substring(6, password.length());
    		    		   }catch(Exception e){
    		    			   
    		    		   }
    		}catch(Exception e){
    			
    		}
    		return ok(
          			updateuser.render("",name,password)

               );
    	}else{
    		 return ok(
    	                hello.render("error in fetching user detials")

    	        );
    	}
    }else{
   	 return ok(
     		login.render("Session is Expired")

     );
}
	
}

public static Result updateUser(){
	
	
   if(session().get("test")!=null&&session().get("test").equalsIgnoreCase("test")&&checkSession()){
    	GregorianCalendar cal=new GregorianCalendar();
    	Date date=cal.getTime();
    session().put("userTime", date.getTime()+"");
	  DynamicForm dynamicForm = form().bindFromRequest();
      System.out.println("update user --------------------------"+dynamicForm.get("userName")+" pasword "+dynamicForm.get("password"));
       String userName=null;
       String password=null;
       userName=dynamicForm.get("userName");
       password=dynamicForm.get("password");
       if(userName==null||userName.isEmpty()||userName.equals("")||password==null||password.isEmpty()||password.equals("")){
      	 return ok(
      			updateuser.render("Please Enter  Proper Email And Password","","")

           );
       }else{
      	 
      	 new CouchBaseOperation().updateAdminDetailsCouhbase(userName, password);
      
   	 return ok(
                hello.render("User  Detials Udated Sucessfully")

        );
}
       
}else{
	 return ok(
        		login.render("Session is Expired")

        );
 }

}

public static boolean checkSession(){
	 String previousTick = session().get("userTime");
	 System.out.println("seseion cehcick"+previousTick);
	 boolean sessoinCheck=false;
	 try{
       if (previousTick != null && !previousTick.equals("")) {
           long previousT = Long.valueOf(previousTick);
           GregorianCalendar calendar=new GregorianCalendar();
           Date date=calendar.getTime();
           long currentT = date.getTime();
           long timeout = Long.valueOf(Play.application().configuration().getString("sessionTimeout")) * 1000 * 60;
          
           System.out.println("sesssion time given "+timeout);
           System.out.println("sesssion cuurenttime  "+currentT);
           System.out.println("deferrencein time"+(currentT - previousT));
           if ((currentT - previousT) > timeout) {
               // session expired
               session().clear();
               return sessoinCheck;
           } else{
           	return true;
           }
       }
	 }catch(Exception e){
		 
	 }
       return sessoinCheck;
}

public static boolean checkTryedForLogin(){
	CouchBaseOperation couchbase=new CouchBaseOperation();
	JSONObject json=null;
	boolean sucess=false;
	try{
		json=new JSONObject(couchbase.getDocument("tryLoginSyncMail").toString());
		System.out.println("checkTryorLogin json------------------>"+json);
	}catch(Exception e){
		e.printStackTrace();
	}
	try{
	if(json!=null){
		GregorianCalendar cal=new GregorianCalendar();
Date date=cal.getTime();
		if(date.getTime()>Long.parseLong(json.get("time").toString())){
			return true;
		}else{
			
			return sucess;
		}
	
	}else{
		json=new JSONObject();
		json.put("try", 0);
		json.put("time", 0);
		couchbase.storeDataInCouchBase("tryLoginSyncMail", json);
		return true;
	}
	
	}catch(Exception e){
		e.printStackTrace();
	}
	return sucess;
	
}

public static void  loginFailed(){

		CouchBaseOperation couchbase=new CouchBaseOperation();
		JSONObject json=null;
		boolean sucess=false;
		try{
			json=new JSONObject(couchbase.getDocument("tryLoginSyncMail").toString());
		}catch(Exception e){
			
		}
		try{
		if(json!=null){
			int i=0;
			i=Integer.parseInt(json.get("try").toString());
			if(i==10){
				GregorianCalendar cal=new GregorianCalendar();
				Date date=cal.getTime();
				json.put("time", date.getTime()+900000);
			}
				json.put("try", i+1);
				
				couchbase.updateDataInCouchBase("tryLoginSyncMail", json);
			}else{
				json=new JSONObject();
				json.put("try", 0);
				json.put("time", 0);
				couchbase.updateDataInCouchBase("tryLoginSyncMail", json);
			
				sucess=true;
				
			}
		
		}catch(Exception e){
			
		}


}
}
