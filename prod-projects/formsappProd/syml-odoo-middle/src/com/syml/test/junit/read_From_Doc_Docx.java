package com.syml.test.junit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;


public class read_From_Doc_Docx {
    public static void main(String[] args) {
try{
    	
    		FileReader filereader=new FileReader("/home/venkateshm/Desktop/SampleClientDocs1.txt");
    		 StringBuilder sb=new StringBuilder();
    		  BufferedReader br=null;

    		  try{
       br=new BufferedReader(filereader);
    		   String line=null;
    		   while((line = br.readLine()) != null)
    		    {
    		      sb.append(line);
    		     }
    		   System.out.println(sb);
    		   }catch(Exception ex){
    		     //
    		   }finally{
    		    if(br!=null)
    		      br.close();
    		   }
    		  System.out.println(sb.toString());
    	  JSONObject jsonArray=new JSONObject(sb.toString());
    			//System.out.println( "hell"+br.readLine());
    			//helo+=br.readLine();
    		
    		
}catch(Exception e){    
	e.printStackTrace();
}
    }
}



















      
