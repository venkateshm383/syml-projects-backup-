package controllers;

import static play.data.Form.form;
import infrastracture.CouchBaseOperation;

import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import net.spy.memcached.internal.OperationFuture;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import com.fasterxml.jackson.databind.JsonNode;

import play.*;
import play.data.DynamicForm;
import play.data.Form;
import play.data.validation.Constraints.Required;
import play.mvc.*;
import sun.security.util.PropertyExpander.ExpandException;

import views.html.*;

public class Application extends Controller {


 	static Logger log = LoggerFactory.getLogger(Application.class);

    // -- Actions
  
    /**
     * Home page
     */
    public static Result index() {
        return ok(
        		login.render("")
        );
    }
  
    
    public static Result backToForm() {
        if(session().get("test")!=null&&session().get("test").equalsIgnoreCase("test")&&checkSession()){

    	return ok(
           		index.render("")

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
         String userName=null;
         String password=null;
         userName=dynamicForm.get("userName");
         password=dynamicForm.get("password");
         boolean tryedLogin=checkTryedForLogin();
         
         log.debug("inside login method ");
         if(tryedLogin){
         if(userName==null||userName.isEmpty()||userName.equals("")||password==null||password.isEmpty()||password.equals("")){
    		 loginFailed();
             log.info("inside login failed ");

        	 return ok(
             		login.render("Please Enter  Proper UserName And Password")

             );
         }else{
             log.debug("login sucessfulll ------------- ");

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
		couOperation.updateDataInCouchBase("tryLogin", json);
        }catch(Exception e){
        	
        }
        		 return ok(
                   		index.render("")

                   );
        	 }else{
        		 loginFailed();
        		 
                 log.debug("login failed  ------------- ");

        		 return ok(
                  		login.render("Please Enter  Proper UserName And Password")

                  );
        	 }
        	
         }
        
         }else{
        	 
             log.debug("Your Account Locked for 15 minutes ");

        	 return ok(
               		login.render("Your Account Locked for 15 minutes")

               ); 
         }
    }
    
    
    
    public static Result adduser() {
    	
        log.debug("inside add user method  ");

        if(session().get("test")!=null&&session().get("test").equalsIgnoreCase("test")&&checkSession()){
        	GregorianCalendar cal=new GregorianCalendar();
        	Date date=cal.getTime();
        session().put("userTime", date.getTime()+"");
        DynamicForm form = form().bindFromRequest();
   String token=form.get("token");
   
   if(token!=null&&!token.isEmpty()){
            boolean sucess=false;
         sucess=   new CouchBaseOperation().addingUser(token);
         String result="";
         if(sucess){
        	 
             log.debug("User Data Added Sucessfully");

        	 result="User Data Added Sucessfully";
         }else{
             log.debug("Please Enter Proper Token");

        	 result="Please Enter Proper Token";
         }
            return ok(
                hello.render(result)
            );
   }else{
	   return ok(
               hello.render("Please Enter Proper Token")
           );
   }
        } else{
        	return ok(
               		login.render("Session is Expired")

               );	
        }
    }
    
    
    public static Result updateUser() {
        log.debug("inside update user method ");

        if(session().get("test")!=null&&session().get("test").equalsIgnoreCase("test")&&checkSession()){
        	GregorianCalendar cal=new GregorianCalendar();
        	Date date=cal.getTime();
        session().put("userTime", date.getTime()+"");
        DynamicForm form = form().bindFromRequest();
        
           String token=form.get("token");
           if(token!=null&&!token.isEmpty()){
               log.debug("User Data Updated Sucessfully ");

            new CouchBaseOperation().updateUser( token);
            return ok(
                hello.render("User Data Updated Sucessfully ")
            );
           }else{
        	   return ok(
                       hello.render("Please Enter Proper token ")
                   );
           }
        
        } else{
        	return ok(
               		login.render("Session is Expired")

               );	
        }
    }

	public static Result getTasks() throws JSONException, IOException,
			org.json.JSONException {
		
		
        log.debug("inside getTasks method ");

		JsonNode json = request().body().asJson();
		if (json == null) {
			return badRequest("Expecting Json data");
		} else {

			System.out.println("Json Data" + json);
			try {

	/*			for (int i = 0; i < jsonArray.length(); i++) {*/

					JSONObject jsonData = new JSONObject(json.toString()) ;
System.out.println("json data----------------"+jsonData);
					String eventName = (String) jsonData.get("event_name");
					JSONObject jsonEventdata = (JSONObject) jsonData
							.get("event_data");
					String taskId = new Integer((int) jsonEventdata.get("id"))
							.toString();
					int opprtunityId=0;
					
					try{
						String val[]=null;
						String projectName=null;
						switch (eventName) {
						case "project:added":
							 projectName=jsonEventdata.get("name")
							.toString();
				 val=projectName.split("_");
					System.out.println("vals "+val[2]);
					opprtunityId=new Integer(val[2]);
					jsonData.put("crmId", opprtunityId);
							break;

						case "project:updated":
							 projectName=jsonEventdata.get("name")
							.toString();
					 val=projectName.split("_");
					System.out.println("vals "+val[2]);
					opprtunityId=new Integer(val[2]);
					jsonData.put("crmId", opprtunityId);				break;

						case "project:deleted":
							 projectName=jsonEventdata.get("name")
							.toString();
					 val=projectName.split("_");
					opprtunityId=new Integer(val[2]);
					jsonData.put("crmId", opprtunityId);
							break;

						case "project:archived":
							 projectName=jsonEventdata.get("name")
							.toString();
				 val=projectName.split("-");
					opprtunityId=new Integer(val[2]);
					jsonData.put("crmId", opprtunityId);
					break;
					
					default:
						break;
						}
						
					}catch(Exception e){
						
					}
					String userId=null;
				try{
					userId=new Integer((int) jsonData.get("user_id")).toString();
				}catch(Exception e){
					
				}
				new CouchBaseOperation().splitEvents(eventName, taskId,
							jsonData,userId);
				
			} catch (Exception e) {
				e.printStackTrace();

			}

			return ok("Hello ");

		}
	}
	
	public static Result getUserDetails(){
		
        log.debug("inside getUserDetails method ");

        if(session().get("test")!=null&&session().get("test").equalsIgnoreCase("test")&&checkSession()){
        	GregorianCalendar cal=new GregorianCalendar();
        	Date date=cal.getTime();
        session().put("userTime", date.getTime()+"");
		List<controllers.User> user=new CouchBaseOperation().getUsers();
		if(user.isEmpty()){
		try{
			Thread.sleep(6000);
		}catch(Exception e){
			
		}
		user=new CouchBaseOperation().getUsers();
		
		if(user.isEmpty()){
			try{
				Thread.sleep(6000);
			}catch(Exception e){
				
			}
			user=new CouchBaseOperation().getUsers();
		}
		}
		return ok(list.render(user));
        } else{
        	return ok(
               		login.render("Session is Expired")

               );	
        }
	}
	
	public static Result editUser(String userID){
        if(session().get("test")!=null&&session().get("test").equalsIgnoreCase("test")&&checkSession()){
        	GregorianCalendar cal=new GregorianCalendar();
        	Date date=cal.getTime();
        session().put("userTime", date.getTime()+"");
User user=new CouchBaseOperation().getPerticulerUser(userID);
		return ok(edit.render(user));
		
        } else{
        	return ok(
               		login.render("Session is Expired")

               );	
        }
	}

	public static Result deleteUser(String userID){
        if(session().get("test")!=null&&session().get("test").equalsIgnoreCase("test")&&checkSession()){
        	GregorianCalendar cal=new GregorianCalendar();
        	Date date=cal.getTime();
        session().put("userTime", date.getTime()+"");
boolean sucess;
sucess=new CouchBaseOperation().deleteUser(userID);
if(sucess){
	
    log.debug("Todoist user Detials Deleted sucessfully ");

	return ok(
       		hello.render("Todoist user Detials Deleted sucessfully")

       );	
}else{
    log.debug("error in deleting Todoist user detials");

	return ok(
       		hello.render("error in deleting Todoist user detials")

       );	
}
		
        } else{
        	return ok(
               		login.render("Session is Expired")

               );	
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
	    		json=new JSONObject(couhbase.getDocument("DataFromTodoist").toString());
	    	
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

	public static Result updateLoginUser(){
		
		
	    if(session().get("test")!=null&&session().get("test").equalsIgnoreCase("test")&&checkSession()){
	    	GregorianCalendar cal=new GregorianCalendar();
        	Date date=cal.getTime();
        session().put("userTime", date.getTime()+"");
		  DynamicForm dynamicForm = form().bindFromRequest();
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
	}}else{
		 return ok(
	        		login.render("Session is Expired")

	        );
	 }}
	
	public static boolean checkSession(){
		 String previousTick = session().get("userTime");
		 System.out.println("seseion cehcick"+previousTick);
		 boolean sessoinCheck=false;
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
	        return sessoinCheck;
	}
	
	public static boolean checkTryedForLogin(){
		CouchBaseOperation couchbase=new CouchBaseOperation();
		JSONObject json=null;
		boolean sucess=false;
		try{
			json=new JSONObject(couchbase.getDocument("tryLogin").toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
		if(json!=null){
			GregorianCalendar cal=new GregorianCalendar();
Date date=cal.getTime();
System.out.println("date.getTime() "+date.getTime() +" "+json.get("time").toString() +" greter "+(date.getTime()>Long.parseLong(json.get("time").toString())));
			if(date.getTime()>Long.parseLong(json.get("time").toString())){
				return true;
			}else{
				
				return sucess;
			}
		
		}else{
			json=new JSONObject();
			json.put("try", 0);
			json.put("time", 0);
			couchbase.storeDataInCouchBase("tryLogin", json);
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
				json=new JSONObject(couchbase.getDocument("tryLogin").toString());
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
					
					couchbase.updateDataInCouchBase("tryLogin", json);
				}else{
					json=new JSONObject();
					json.put("try", 0);
					json.put("time", 0);
					couchbase.updateDataInCouchBase("tryLogin", json);
				
					sucess=true;
					
				}
			
			}catch(Exception e){
				
			}
	

	}
	
public static void main(String[] args) {
	System.out.println(checkTryedForLogin());
}
}