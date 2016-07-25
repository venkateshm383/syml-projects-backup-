package infrastracture;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.xmlrpc.XmlRpcException;

import com.debortoliwines.openerp.api.Context;
import com.debortoliwines.openerp.api.ObjectAdapter;
import com.debortoliwines.openerp.api.OpenERPCommand;
import com.debortoliwines.openerp.api.OpenERPXmlRpcProxy;
import com.debortoliwines.openerp.api.OpenERPXmlRpcProxy.RPCProtocol;
import com.debortoliwines.openerp.api.OpenERPXmlRpcProxy.RPCServices;
import com.debortoliwines.openerp.api.OpeneERPApiException;
import com.debortoliwines.openerp.api.Version;

/***
 * Manages an OpenERP session by holding context and initiating all calls to the server.
 * @author Pieter van der Merwe
 *
 */
public class Session {
	private static org.slf4j.Logger logger = play.Logger.underlying();

	private String host;
	private int port;
	private String databaseName;
	private String userName;
	private String password;
	private int userID;
	private Context context = new Context();
	private static boolean connecting = false;
	private RPCProtocol protocol; 
	
	/***
   * Session constructor
   * @param protocol XML-RPC protocol to use.  ex http/https.
   * @param host Host name or IP address where the OpenERP server is hosted
   * @param port XML-RPC port number to connect to.  Typically 8069.
   * @param databaseName Database name to connect to
   * @param userName Username to log into the OpenERP server
   * @param password Password to log into the OpenERP server
   */
  public Session(RPCProtocol protocol, String host, int port, String databaseName, String userName, String password){
    this.protocol = protocol;
    this.host = host;
    this.port = port;
    this.databaseName = databaseName;
    this.userName = userName;
    this.password = password;
  }
	
	/***
	 * Session constructor.  Uses default http protocol to connect.
	 * @param host Host name or IP address where the OpenERP server is hosted
	 * @param port XML-RPC port number to connect to.  Typically 8069.
	 * @param databaseName Database name to connect to
	 * @param userName Username to log into the OpenERP server
	 * @param password Password to log into the OpenERP server
	 */
	public Session(String host, int port, String databaseName, String userName, String password){
		this(RPCProtocol.RPC_HTTP, host, port, databaseName, userName, password);
	}
	
	/**
	 * Returns an initialized OpenERPCommand object for ease of reference.
	 * A OpenERPCommand provides basic calls to the server
	 * @return
	 */
	/*public OpenERPCommand getOpenERPCommand(){
		return new OpenERPCommand(this);
	}*/
	
	/**
	 * Returns an initialized ObjectAdapter object for ease of reference.
	 * A ObjectAdapter object does type conversions and error checking before making a call to the server
	 * @return
	 */
/*	public ObjectAdapter getObjectAdapter(String objectName) throws XmlRpcException, OpeneERPApiException{
		return new ObjectAdapter(this, objectName);
	}*/

	/***
	 * Starts a session on the OpenERP server and saves the UserID for use in later calls
	 * @throws Exception
	 */
	public void startSession() throws Exception {

	  try{
	    // 21/07/2012 - Database listing may not be enabled (--no-database-list or list_db=false).
	    // Only provides additional information in any case.
  		ArrayList<String> dbList = OpenERPXmlRpcProxy.getDatabaseList(protocol,host,port);
  		if (dbList.indexOf(databaseName) < 0){
  			StringBuffer dbListBuff = new StringBuffer();
  			for (String dbName : dbList)
  				dbListBuff.append(dbName + System.getProperty("line.separator"));
  
  			throw new Exception("Error while connecting to OpenERP.  Database [" + databaseName + "] "
  					+ " was not found in the following list: " + System.getProperty("line.separator") 
  					+ System.getProperty("line.separator") + dbListBuff.toString());
  		}
	  }
	  catch(Exception e){
		  logger.error("Error while connecting to OpenERP Database "+e.getMessage());
	  }

		// Connect
		OpenERPXmlRpcProxy commonClient = new OpenERPXmlRpcProxy(protocol, host, port, RPCServices.RPC_COMMON);
		
		// Synchronize all threads to login.  If you login with the same user at the same time you get concurrency
		// errors in the OpenERP server (for example by running a multi threaded ETL process like Kettle).
		Session.startConnecting();
		
		Object id = null;
		try{
			id = commonClient.execute("login", new Object[] { databaseName, userName, password });
		}
		catch (ClassCastException c){
		  // General exception is only thrown if the database doesn't exist.
		  // Incorrect username and password will return an id of 0.  
		  // Incorrect server parameters (servername/port) will not be caught here  
			logger.error("Error while connecting to OpenERP Database "+c.getMessage());
		  throw new Exception("Database " + databaseName + " does not exist");
		  
		}
		finally{
			Session.connecting = false;
		}

		if (id instanceof Integer)
			userID = (Integer) id;
		else
			throw new Exception("Incorrect username and/or password.  Login Failed.");
		
    this.context.clear();
    @SuppressWarnings("unchecked")
    HashMap<String, Object> openerpContext = (HashMap<String, Object>) this.executeCommand("res.users","context_get", new Object[]{});
    this.context.putAll(openerpContext);
    
    // Standard behavior is web/gui clients.
    this.context.setActiveTest(true);

	}
	
	private synchronized static void startConnecting(){
		while (Session.connecting){
			try {
				Thread.sleep(100);
			}
			catch (Exception e){
				logger.error("Error while Thread sleeping "+e.getMessage());
			}
		}
		Session.connecting = true;
	}
	
	/***
	 * Get a list of databases available on a specific host and port
	 * @param host Host name or IP address where the OpenERP server is hosted
	 * @param port XML-RPC port number to connect to. Typically 8069.
	 * @return A list of databases available for the OpenERP instance
	 * @throws XmlRpcException
	 */
	public ArrayList<String> getDatabaseList (String host, int port) throws XmlRpcException
	{
		return OpenERPXmlRpcProxy.getDatabaseList(protocol, host, port);
	}
	
	/**
	 * Executes any command on the server linked to the /xmlrpc/object service.
	 * All parameters are prepended by: "databaseName,userID,password"
	 * @param objectName Object or model name to execute the command on
	 * @param commandName Command name to execute
	 * @param parameters List of parameters for the command.  For easy of use, consider the OpenERPCommand object or ObjectAdapter
	 * @return The result of the call
	 * @throws XmlRpcException
	 */
	public Object executeCommand(final String objectName, final String commandName, final Object[] parameters) throws XmlRpcException {
		Object[] connectionParams = new Object[] {databaseName,userID,password,objectName,commandName};
		
		// Combine the connection parameters and command parameters
		Object[] params = new Object[connectionParams.length + (parameters == null ? 0 : parameters.length)];
		System.arraycopy(connectionParams, 0, params, 0, connectionParams.length);
		
		if (parameters != null && parameters.length > 0)
			System.arraycopy(parameters, 0, params, connectionParams.length, parameters.length);
		   
		OpenERPXmlRpcProxy objectClient = new OpenERPXmlRpcProxy(protocol, host, port, RPCServices.RPC_OBJECT);
		return objectClient.execute("execute", params);		
	}
	
	/**
	 * Executes a workflow by sending a signal to the workflow engine for a specific object.
	 * This functions calls the 'exec_workflow' method on the object
	 * All parameters are prepended by: "databaseName,userID,password"
	 * @param objectName Object or model name to send the signal for
	 * @param signal Signal name to send, for example order_confirm
     * @param objectID Specific object ID to send the signal for
	 * @throws XmlRpcException
	 */
	public void executeWorkflow(final String objectName, final String signal, final int objectID) throws XmlRpcException {
    Object[] params = new Object[] {databaseName,userID,password,objectName,signal, objectID};
    
    OpenERPXmlRpcProxy objectClient = new OpenERPXmlRpcProxy(protocol, host, port, RPCServices.RPC_OBJECT);
    objectClient.execute("exec_workflow", params);   
  }
	
	/**
	 * Returns the OpenERP server version for this session
	 * @return
	 * @throws XmlRpcException
	 */
	public Version getServerVersion() throws XmlRpcException{
	  return OpenERPXmlRpcProxy.getServerVersion(protocol, host, port);
	}
	
	/**
	 * Retrieves the context object for the session to set properties on 
	 * @return
	 */
	public Context getContext(){
		return context;
	}
}
