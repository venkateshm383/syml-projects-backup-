package com.syml.test.seleniumTest;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ReadTextFile {


//TODO create a method which do above and return arraylist/list
public ArrayList readtext() throws IOException{
	Scanner scanner;
	ArrayList<HashMap> lines = new ArrayList<HashMap>();

	try {
		
		scanner = new Scanner(new FileReader("/home/bizruntime/Downloads/symlText/ApplyNowTest2.txt"));
		//HashMap m=new HashMap();
		while (scanner.hasNextLine()) {
			//ignore first line
		    String columns = scanner.nextLine();
		    
		    System.out.println("coloums "+columns);
			HashMap m=new HashMap();
			
			//based on no. array size spacify the hashmap field
		   String col[]=  columns.split(",");
		   System.out.println("size of array"+col.length);
		  m.put("input_14",col[0].trim());
		   m.put("input_15",col[1].trim());
		   m.put("input_20",col[2].trim());
		   m.put("input_16",col[3].trim());
		   //m.put("input_16_confirm",col[4].trim());
		   m.put("input_21",col[4].trim());

		    lines.add(m);
		}
		System.out.println("no of lines : "+lines.size());
		System.out.println("values in lines "+lines.toString());
		Iterator i=lines.iterator();
		while(i.hasNext()){
			System.out.println(i.next());
		}
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return lines;
}
}
