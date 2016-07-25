package com.syml.test.junit;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.syml.openerp.CreateLead;
public class OpenErpSplit {

	public static void main(String[] args) throws ParseException, JSONException {
		//String yearWorked1="347777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777777";
		/*
		if(yearWorked1.length() <36 && yearWorked1!=null){
			System.out.println("you can fill next form");
			
		}
		*/
		
		JSONArray array=new JSONArray();
		JSONObject jobj=new JSONObject();
		jobj.put("discripstion","somtinh");
		jobj.put("value", "dfds");
		jobj.put("value", "dfds");
		
		/*JSONObject jobj1=new JSONObject();
		jobj1.put("discripstion","somtinh");
		jobj1.put("value", "dfds");*/
		
		array.put(jobj);
		//System.out.println("jobj1 "+jobj);
		array.put(jobj);
		System.out.println("obj2 : "+jobj);
		
		System.out.println("array size : "+array.length());
		System.out.println("array value :  "+array.toString());
		
		for (int i=0;i<array.length();i++){
			JSONObject job=array.getJSONObject(i);
			String dis=(String)job.get("discripstion");
			System.out.println("dis : "+dis);
			
			//openerp logic to insert insert(dis,value,dcr)
		}
		
		
		
		
		
		
		

	}

}
