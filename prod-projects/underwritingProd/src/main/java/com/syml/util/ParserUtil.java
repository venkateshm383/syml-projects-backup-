package com.syml.util;

public class ParserUtil {
	
	public static double parserDouble(String s)
	{
		if(s == null || s.trim().length() == 0)
			return 0;
		else
		{
			try
			{
				return Double.parseDouble(s);				
			}
			catch(NumberFormatException nfe)
			{
				return 0;
			}
		}
	}

}
