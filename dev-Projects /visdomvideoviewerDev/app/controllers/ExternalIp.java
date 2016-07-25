package controllers;

import java.net.InetAddress;

public class ExternalIp {
	private static String URL = "http://automation.whatismyip.com";
	
	public static void main(String[] args) {
		ExternalIp ipGetter = new ExternalIp();
		System.out.println(ipGetter.getIpAddress());
	}
	
	 private InetAddress thisIp;

	  private String thisIpAddress;

	  private void setIpAdd()
	  {
	    try
	    {
	       InetAddress thisIp = InetAddress.getLocalHost();
	       thisIpAddress = thisIp.getHostAddress().toString();
	    }
	    catch(Exception e){}
	  }

	  protected String getIpAddress()
	  {
	     setIpAdd();
	     return thisIpAddress;
	  }
}
