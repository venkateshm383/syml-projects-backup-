package com.syml.test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.debortoliwines.openerp.api.FilterCollection;
import com.debortoliwines.openerp.api.ObjectAdapter;

import com.debortoliwines.openerp.api.Row;
import com.debortoliwines.openerp.api.RowCollection;
import com.debortoliwines.openerp.api.Session;

public class TestForStage {
 private static final Logger logger = LoggerFactory.getLogger(TestForStage.class);
 
 
  public static void main(String[] args) {
   
   
   
  // setthisvalue("606");
   
   Session session = new Session("crm1.visdom.ca", 8069, "symlsys", "admin", "BusinessPlatform@Visdom1");
  
  try {
	  session.startSession();
   ObjectAdapter partnerAd = session.getObjectAdapter("crm.lead");
   FilterCollection filters = new FilterCollection();
   filters.add("id","=",606);
   RowCollection partners = partnerAd.searchAndReadObject(filters, new String[]{"stage_id"});
  
   
   for (Row row : partners){
	  // row.put("stage_id", 18);
	   //partnerAd.writeObject(row, true);
	    System.out.println("Row ID: " + row.getID());
	    
	   // System.out.println("email_from:" + row.get("email_from"));
	    System.out.println("stage_id:" + row.get("stage_id").toString());
	  

	}
         
   
  } catch(Exception e){
   e.printStackTrace();   
  }
   
   
 
  }
  

     
 
 
}
