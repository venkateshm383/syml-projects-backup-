package com.syml.mortgage.application;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;

import com.jotform.api.JotForm;

public class TestMortgageServlet8 extends HttpServlet {
	private static org.slf4j.Logger log = LoggerFactory.getLogger(JotForm.class);
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String fileName = req.getParameter("sign1");

		//String newString = fileName.substring(22);
		log.debug("First Sign data "+fileName);

		
		
			String filename2 = req.getParameter("sign2");
			
			if(filename2!=null){
				log.debug("Second Sign data "+filename2);

			}
			

	}
	

}
