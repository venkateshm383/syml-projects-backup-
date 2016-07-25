package com.syml.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.syml.bean.algo.UnderwriteAll;
import com.syml.bean.algo.UnderwritingBase;
import com.syml.domain.BaseBean;

public class Util {
	public static String getJson(Object o)
	{
		ObjectMapper om = new ObjectMapper ();
		try {
			return om.writeValueAsString(o);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * algo a bean list some fields average
	 * @param listOfAcceptableProducts
	 * @param fieldName
	 * @return
	 * @throws NoSuchFieldException 
	 * @throws SecurityException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static Double listAverage(List<? extends BaseBean> listOfAcceptableProducts, String fieldName) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
		
		if(listOfAcceptableProducts==null || listOfAcceptableProducts.isEmpty())
			return new Double(0);
		
		List<Double> doubles = new ArrayList<Double>();
		
		for (BaseBean product : listOfAcceptableProducts) {
		
			if(product != null)
			{
				Field field = product.getClass().getField(fieldName);
				if(field != null)
				{
					
					Object o = field.get(product);
					if(o != null)
						doubles.add((Double)o);
				}
				//doubles.add(field.getDouble(product));
				
			}
				
			
		}
	
		double avg = 0.0;
		double count = 0.0;
		if(doubles.size() > 0)
		{
			for (Double double1 : doubles) {
				count += double1;
			}
			avg = count/doubles.size();
		}
		
		
		return avg;
	}
	
	public static Double listAverage(List<? extends Number> list){
		Double avg = 0.0;
		Double count = 0.0;
		for (Number number : list) {
			count += number.doubleValue();
		}
		avg = count.doubleValue()/list.size();
		return avg;
	}
	
	public static Double listAlgoAverage(List<UnderwriteAll> listOfAcceptableProducts, String fieldName){
		
		if(listOfAcceptableProducts==null || listOfAcceptableProducts.isEmpty())
			return new Double(0);
		
		List<Double> doubles = new ArrayList<Double>();
		
		for (UnderwritingBase product : listOfAcceptableProducts) {
			try {
				Field field = product.getClass().getField(fieldName);
				
				doubles.add(field.getDouble(product));
				
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		
		double avg = 0.0;
		double sum = 0.0; // TODO CHange count to Sum 
		for (Double double1 : doubles) {
			sum += double1;
		}
		avg = sum/doubles.size();
		
		return avg;
	}
	
	/**
	 * eg: 	GenericComparator comparator = new GenericComparator(fieldname, "asc");
	 *  	GenericComparator comparator = new GenericComparator(fieldname, "desc");
	 * 		Collection.sort(list, GenericComparator)
	 * @param fieldname fieldname type is : String, Integer, Double, Float. if not, The fieldname type must be implement Comparator<T>
	 * @param orderby: value : desc/asc
	 * @return
	 */
	public static GenericComparator newGenericComparator(String fieldname,String orderby){
		return new GenericComparator(fieldname, orderby);
	}
	
	/**
	 * eg: 	GenericComparator comparator = new GenericCOmparator(fieldname);
	 * 		{@link #newGenericComparator(String, String)}
	 * @param fieldname fieldname type is : String, Integer, Double, Float. if not, The fieldname type must be implement Comparator<T>
	 * @param orderby: value : desc/asc
	 * @return
	 */
	public static GenericComparator newGenericComparator(String fieldname){
		return new GenericComparator(fieldname, null);
	}
}
