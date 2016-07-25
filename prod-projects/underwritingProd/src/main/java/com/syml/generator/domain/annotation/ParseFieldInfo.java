package com.syml.generator.domain.annotation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.syml.domain.BaseBean;
import com.syml.generator.domain.annotation.ERP.OpenERPType;
/**
 * get bean class field and getter method, setter method information.
 * @author .x.m.
 *
 * @param <T extends BaseBean>
 */
public class ParseFieldInfo<T extends BaseBean> {
	/**
	 * entity field Info
	 * @param clazz
	 * @return
	 */
	public List<FieldInfo> getFieldInfo(Class<? extends BaseBean> clazz){
		
		List<FieldInfo> fieldInfos = new ArrayList<FieldInfo>();
		
		//add id
		FieldInfo idFieldInfo = new FieldInfo();
		fieldInfos.add(idFieldInfo);
		
		idFieldInfo.fieldName="id";
		idFieldInfo.fieldTypeInERP="integer";
		idFieldInfo.setFieldMethod="setId";
		idFieldInfo.getFieldMethod="getId";
		idFieldInfo.classType=Integer.class;
		
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			
			FieldInfo fieldInfo = new FieldInfo();
			String fieldName = field.getName();
			String upperCasedFieldName = this.getMethodName(fieldName);
			fieldInfo.getFieldMethod="get"+upperCasedFieldName;
			fieldInfo.setFieldMethod="set"+upperCasedFieldName;
			
			ERP fieldAnno = field.getAnnotation(ERP.class);
			
			if(fieldAnno != null && fieldAnno.type().equals(OpenERPType.ignore))
				continue;
			
			if(fieldAnno==null){  //no annotation
				fieldInfo.fieldName=fieldName;
				fieldInfo.fieldTypeInERP=field.getType().getSimpleName();
				fieldInfo.classType=field.getType();
			}else{
				
				if(fieldAnno.type().equals(OpenERPType.Null)){  //only name @OpenERPType( name = "street2" )
					fieldInfo.fieldName=fieldAnno.name() == "" ? field.getName() : fieldAnno.name();
					fieldInfo.fieldTypeInERP=field.getType().getSimpleName();
					fieldInfo.classType=field.getType();
				}else if(fieldAnno.type().name().equals("one2many")){
					
					fieldInfo.fieldName = fieldAnno.name() == "" ? field.getName() : fieldAnno.name();
					fieldInfo.fieldTypeInERP =  fieldAnno.type().toString();
					fieldInfo.classType = field.getType();
							
				}else{ //@OpenERPType( name = "street2", type="selection" )
					fieldInfo.fieldName=fieldAnno.name();
					fieldInfo.fieldTypeInERP=fieldAnno.type().name();
					fieldInfo.classType=field.getType();
				}
			}
			fieldInfos.add(fieldInfo);
		}
		
		return fieldInfos;
	}
	
	private String getMethodName(String fildeName){
//		byte[] items = fildeName.getBytes();
//		items[0] = (byte) ((char) items[0] - 'a' + 'A');
//		return new String(items);
		
		String firt = fildeName.substring(0,1).toUpperCase();
		return firt+fildeName.substring(1);
		
	}
	
}
