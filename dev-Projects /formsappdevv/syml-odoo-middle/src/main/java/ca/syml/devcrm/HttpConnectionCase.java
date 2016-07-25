package ca.syml.devcrm;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.xmlrpc.XmlRpcException;
import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

public class HttpConnectionCase {

	public static void main(String[] args) throws MalformedURLException {
		XmlRpcClient xmlrpcDb = new XmlRpcClient();
		XmlRpcClientConfigImpl xmlrpcConfigDb = new XmlRpcClientConfigImpl();
		xmlrpcConfigDb.setServerURL(new URL("http","crm1.visdom.ca", 8069,"/xmlrpc/db"));

		xmlrpcDb.setConfig(xmlrpcConfigDb);

		try {
			//Retrieve databases only
			List<Object> params = new ArrayList<Object>();
			Object result = xmlrpcDb.execute("list", params);
			Object[] a = (Object[]) result;
			List<String> res = new ArrayList<String>();
			for (int i = 0; i < a.length; i++) {
				if (a[i] instanceof String) {
					res.add( (String)a[i] );
				}
			}
			System.out.println(">>> Databases : " + res.toString());
		} catch (XmlRpcException e) {
			System.err.println(">>> XmlException Error while retrieving OpenERP Databases: " + e);
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println(">>> Error while retrieving OpenERP Databases: " + e);
			e.printStackTrace();
	  }
	}
}
