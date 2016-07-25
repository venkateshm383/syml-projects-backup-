package com.syml.mortgage.application;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.LoggerFactory;

import com.jotform.api.JotForm;

public class MortgageApplicationServlet extends HttpServlet {
	
	private static org.slf4j.Logger log = LoggerFactory.getLogger(JotForm.class);
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
			
		String crmLeadId=req.getParameter("crmLeadId");
		String referralEmail=req.getParameter("referrerEmail");
		String referralName=req.getParameter("referralName");
		

		log.debug("crmLead id is  ------ "+crmLeadId);
		log.debug("Referral Email id is  ------ "+referralEmail);
		HttpSession session=req.getSession(true);
		session.setAttribute("crmLeadId", crmLeadId);
		session.setAttribute("referralEmail", referralEmail);
		req.getRequestDispatcher("MortgageApplication1a.jsp?crmLeadId="+crmLeadId+"&referralEmail="+referralEmail+"&referralName="+referralName).include(req, resp);
	}

}
