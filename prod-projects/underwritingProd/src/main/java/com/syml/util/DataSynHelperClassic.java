package com.syml.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.persistence.Entity;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

import com.syml.domain.BaseBean;
import com.syml.generator.domain.annotation.FieldInfo;
import com.syml.generator.domain.annotation.ParseFieldInfo;

public class DataSynHelperClassic<T extends BaseBean>{

	private String protocol = "http";
	private int port = 8069;
	private String host = "107.23.27.61";
	private String username = "admin";
	private String password = "syml";
	private String dbname = "symlsys";
	
	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws Exception {
//		new DataSynHelperClassic<AccountFiscalPosition>().synchronizeModel(AccountFiscalPosition.class, 4);
//		new DataSynHelperClassic<Lender>().loginOpenERP();
		
	}
	
	public T synchronizeModel(Class<T> clazz, Integer id) throws Exception
	{	
		
		List<FieldInfo> fieldInfos = new ParseFieldInfo<T>().getFieldInfo(clazz);
		
		return this.getDataFromERP(clazz, fieldInfos, id);
	}
	
	@SuppressWarnings("unchecked")
	public T getDataFromERP(Class<T> clazz, List<FieldInfo> fieldInfos, Integer id ) throws Exception{
		
		List<String> fields = new ArrayList<String>();
		for (FieldInfo fieldInfo : fieldInfos) {
			fields.add(fieldInfo.fieldName);
		}
		
		//login
		int result = this.loginOpenERP();
		
		//search
		if(result>0){
			XmlRpcClient xmlrpc = new XmlRpcClient();
			
			XmlRpcClientConfigImpl xmlrpcConfig = new XmlRpcClientConfigImpl();
			xmlrpcConfig.setEnabledForExtensions(true);
			xmlrpcConfig.setServerURL(new URL(protocol, host, port,"/xmlrpc/object"));
			
			xmlrpc.setConfig(xmlrpcConfig);
			
			Entity an = clazz.getAnnotation(Entity.class);
			String name = an.name().replace("_", ".");
			
			Vector<Object> params = new Vector<Object>();
			params.add(dbname);             // DB name
			params.add(result);                    	// used_id
			params.add(password);         			 // DB password
			params.add(name);        	// table 
			params.add("read");
			params.add(id);                 		 // Table id 
			params.add(fields);                 		 // Table columns 
			
		    Object results = xmlrpc.execute("execute",params);
		    System.out.println("results:"+results);
//		    Object[] a = (Object[]) results;
		} 
	
		return null;
	}
	
	
	
	public int loginOpenERP() {
		try {
			XmlRpcClient client = new XmlRpcClient();
	        XmlRpcClientConfigImpl loginConfig = new XmlRpcClientConfigImpl();
	        loginConfig.setEnabledForExtensions(true);
       
			loginConfig.setServerURL(new URL(protocol, host, port, "/xmlrpc/common"));
		
	        client.setConfig(loginConfig);
	
	        Object [] loginParam = new Object []{dbname, username, password};
	        Object id = client.execute("login",loginParam);
	        if(id instanceof Integer)
	        	return (Integer) id;
		} catch (MalformedURLException e) {
			//"url can't connect to openERP!"
			e.printStackTrace();
			return -2;
		} catch (XmlRpcException e) {
			e.printStackTrace();
			return -3;
		}
		return -1;
	}
}
