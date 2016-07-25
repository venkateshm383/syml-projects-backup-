package com.syml.test.junit;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.HashedMap;
import org.junit.Test;

import com.syml.jotform.applyNowForm.ApplyNowServlet;
import com.syml.test.seleniumTest.ReadTextFile;

public class ApplyNowServletTest {
	HttpServletRequest req=null;
	HttpServletResponse res=null;

	@Test
	public boolean recieveHashMap() throws ServletException, IOException{

		HashedMap map=new HashedMap();
		map.put("fname", req.getParameter("firstname"));
		map.put("lname", req.getParameter("lastname"));
		map.put("phone", req.getParameter("mobilenumber"));
		map.put("email", req.getParameter("email"));
		map.put("address", req.getParameter("address"));


		assertEquals("recieveKey", map);
		return true;
	}
	@Test
	public boolean sendHashMap() throws IOException{
		ReadTextFile rd=new ReadTextFile();
		Object obj=rd.readtext();
		assertEquals("sendKey", obj);
		return true;


	}
	@Test
	public void testHashMap() throws ServletException, IOException{
		if(recieveHashMap() == sendHashMap()){
			assertEquals("valuTest", true);

			
		}else{
			assertEquals("valuTest", false);

		}



		
	}


public static void main(String[] args) {
	String hello="";
	
	
	if(hello.equals("real")){
		System.out.println("suc");
	}else{
		System.out.println("suc");
	}
	try{
	
	}catch(Exception e){
		
	}
	
}




}
