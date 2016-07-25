package com.syml.generator.domain;

import java.util.Map;

import com.syml.domain.BaseBean;
import com.syml.generator.domain.annotation.OpenERPFieldInfo;
import com.syml.util.DataSynHelper;
import com.syml.util.OpenERPSessionUtil;



public class Test {

	public static void main(String[] args) throws Exception {
		//new GeneratorBean().run();
		new GeneratorDB().run();
		System.out.println("Generate Database Complete");
		//new OpenERPAnalyser().run();
		
//		reference() ;
		
	} 	
	
	public static void reference() throws Exception{
		
		DataSynHelper<BaseBean> helper = OpenERPSessionUtil.getOpenERPHelper();
		Map<String, OpenERPFieldInfo> s = helper.getFields("applicant.record");
		for (String key : s.keySet()) {
			System.out.println(s.get(key).name);
		}
		
	}

}
