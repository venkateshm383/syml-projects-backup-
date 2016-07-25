package com.syml.generator.domain;

import java.util.Map;


import com.debortoliwines.openerp.api.SelectionOption;
import com.syml.constant.Constant;
import com.syml.domain.BaseBean;
import com.syml.generator.domain.annotation.OpenERPFieldInfo;
import com.syml.util.DataSynHelper;
import com.syml.util.OpenERPSessionUtil;

public class OpenERPAnalyser {
	
	private Map<String, String> classMap;
	public OpenERPAnalyser(){
		this.classMap = Constant.getOpenERPAndDomainMappings();
	}
		
	public void run() throws Exception{
		
		DataSynHelper<BaseBean> helper = OpenERPSessionUtil.getOpenERPHelper();
		String out = this.analyse(helper);
		System.out.println(out);
		
	}
	
	public String analyse(DataSynHelper<BaseBean> helper) throws Exception{
		String out = "";
		for (String tablename : classMap.keySet()) {
			
			Map<String, OpenERPFieldInfo> erpFields = helper.getFields(tablename.replace("_", "."));
			
			out+="table name:"+tablename.replace("_", ".")+"\n";
			
			for(String field : erpFields.keySet()){
				OpenERPFieldInfo info = erpFields.get(field);
				if("selection".equals(info.type)){
					boolean flag = false;
					
					if(isContainIllegalCharacter(info.name) || isKeyword(info.name))
						flag = true;
					
					if(info.typeValues!=null)
						for(int i = 0 ; i < info.typeValues.size(); i++){
							SelectionOption so = info.typeValues.get(i);
							if(so.code!=null && so.code.trim().length()>0){
								if(isContainIllegalCharacter(so.code) || isKeyword(so.code)){
									flag = true;
									break;
								}
							}
						}
					
					if(flag){
						
						String selectionOut = info.name+" (";
						if(info.typeValues!=null)
							for(int i = 0 ; i < info.typeValues.size(); i++){
								SelectionOption so = info.typeValues.get(i);
								if(so.code.trim().length()>0){
									selectionOut += so.code + ":"+ so.value;
									if(i<info.typeValues.size()-1)
										selectionOut += ", ";
								}
							}
						selectionOut += ")\n";
						
						out+=selectionOut;
					}
					
				}else{
					if(isContainIllegalCharacter(info.name) || isKeyword(info.name))
						out+=info.name+"\n";
				}
			}
			out+="\n\n";
		}
		return out;
	}
	
	private static enum IllegalCharacter{
		LeftBracket("("), RightBracket(")"), Slash("/"), Backslash("\\"), BlankSpace(" "), NumberCharacter("#"), PercentCharacter("%"),
		PlusSign("+"), AndCharacter("&"), StarCharacter("*"), ExclamationMark("!"), Line("-");
		private String value;
		private IllegalCharacter(String value){this.value = value;}
	}
	
	public static boolean isContainIllegalCharacter(String value){
		if(value!=null && value.trim().length()==0)
			return true;
		
		String startChar = value.substring(0,1);
		if(startChar.matches("\\d"))
			return true;
		
		String[] values = value.split("");
		for(int i=0; i<values.length; i++){
			if(values[i].equals(IllegalCharacter.LeftBracket.value))
				return true;
			else if(values[i].equals(IllegalCharacter.RightBracket.value))
				return true;
			else if(values[i].equals(IllegalCharacter.Slash.value))
				return true;
			else if(values[i].equals(IllegalCharacter.Backslash.value))
				return true;
			else if(values[i].equals(IllegalCharacter.BlankSpace.value))
				return true;
			else if(values[i].equals(IllegalCharacter.NumberCharacter.value))
				return true;
			else if(values[i].equals(IllegalCharacter.PercentCharacter.value))
				return true;
			else if(values[i].equals(IllegalCharacter.AndCharacter.value))
				return true;
			else if(values[i].equals(IllegalCharacter.StarCharacter.value))
				return true;
			else if(values[i].equals(IllegalCharacter.ExclamationMark.value))
				return true;
			else if(values[i].equals(IllegalCharacter.Line.value))
				return true;
		}
		return false;
	}
	
	private static boolean isKeyword(String fieldname){
		
		if("private".equalsIgnoreCase(fieldname))
			return true;
		else if("float".equalsIgnoreCase(fieldname))
			return true;
		else if("double".equalsIgnoreCase(fieldname))
			return true;
		else if("class".equalsIgnoreCase(fieldname))
			return true;
		else if("default".equalsIgnoreCase(fieldname))
			return true;
		else if("false".equalsIgnoreCase(fieldname))
			return true;
		else 
			return false;
		
	}
}
