package com.syml.jotform.createform;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jotform.api.JotForm;
import com.syml.constants.SymlConstant;

public class SubmitReferalForm2 {
 	Logger log = LoggerFactory.getLogger(SubmitReferalForm2.class);

	
	
	public void sendMessage(String msg) throws JSONException{
		
		log.info("inside sendMessage method of SubmitReferalForm2 class");
	
		SymlConstant symlconstants = new SymlConstant();
	
		
		JotForm jform=new JotForm(symlconstants.getJotformApiKey());
				
			

		HashMap editquest=new HashMap();
		editquest.put("text",msg);



		jform.editFormQuestion(50234255984963l, 1, editquest);

			}
			

}
