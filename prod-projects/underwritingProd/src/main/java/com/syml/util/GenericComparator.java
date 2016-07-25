package com.syml.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Comparator;

public class GenericComparator implements Comparator{

	private String fieldname;
	private boolean isDescending;
	
	public GenericComparator(String fieldname, String orderby){
		this.fieldname = fieldname;
		
		if(orderby==null||orderby.trim().length()==0)
			isDescending = false;
		else if("desc".equalsIgnoreCase(orderby))
			isDescending = true;
		else 
			isDescending = false;
	}
	
	public int compare(Object o1, Object o2) {
		
		Object value1 = null;
		Object value2 = null;
		
		try {
			Field field = o1.getClass().getField(fieldname);
		
			String modifer = Modifier.toString(field.getModifiers());
			if(modifer.indexOf("public")!=-1){	//like: public String field;
				value1 = field.get(o1);  
				value2 = field.get(o2);
			}else{
				//Class o1 or o2 can not access a member of  with modifiers "private"
				//use field getter get value
				String getterStr = "get"+fieldname.substring(0,1).toUpperCase()+fieldname.substring(1);
				Method getter = o1.getClass().getDeclaredMethod(getterStr, null);
				value1 = getter.invoke(o1, null);
				value2 = getter.invoke(o2, null);
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		if(value1==null && value2 == null)
			return 0;
		if(value1==null)
			return isDescending ? 1 : -1 ;
		if(value2==null)
			return isDescending ? -1 : 1;
		
		int result = 0;
		if(value1 instanceof String){
			String value1Str = value1.toString();
			String value2Str = value2.toString();
			result = value1Str.compareTo(value2Str);
		}else if(value1 instanceof Double){
			Double value1d = (Double) value1 * 10000;
			Double value2d = (Double) value2 * 10000;
			result = (int) (value1d - value2d);
		}else if(value1 instanceof Float){
			Float value1f = (Float) value1;
			Float value2f = (Float) value2;
			result = (int) (value1f - value2f);
		}else if(value1 instanceof Integer){
			Integer value1f = (Integer) value1;
			Integer value2f = (Integer) value2;
			result = value1f - value2f;;
		}else{
			
			Class<?>[] itrfaces = value1.getClass().getInterfaces();
			boolean isExistImplComparable = false;
			for ( int i=0; i < itrfaces.length; i++ )
				if(itrfaces[i].equals(Comparable.class))
					isExistImplComparable = true;
			
			if(isExistImplComparable){
				try {
					Method method = value1.getClass().getMethod("compareTo", new Class[]{value1.getClass()});
					result =  (Integer) method.invoke(value1, new Object[]{value2});
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			
		}
		return isDescending ? -result : result;
	}
}

