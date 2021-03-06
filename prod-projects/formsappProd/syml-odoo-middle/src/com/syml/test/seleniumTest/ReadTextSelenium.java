package com.syml.test.seleniumTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class ReadTextSelenium {
	public static WebDriver driver;
	public static void main(String[] args) throws InterruptedException, IOException {
		 // Create a new instance of the Firefox driver
		
		 // System.setProperty("webdriver.chrome.driver", "/home/bizruntime/vikash/project/syml/selenium/chromedriver");

		 
   driver = new FirefoxDriver();
//driver=new ChromeDriver();

        // Put an Implicit wait, this means that any search for elements on the page could take the time the implicit wait is set for before throwing exception
 
     driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
 
        // Launch the ToolsQA Website
 
        driver.get("http://form.jotform.me/form/43496529286469");
        
                
 ReadTextFile rd=new ReadTextFile();
 ArrayList list=rd.readtext();
 

 
 Iterator itrate=list.iterator();
 while(itrate.hasNext()){
	 HashMap h=(HashMap)itrate.next();
	 Set<Map.Entry<String, String>> set = h.entrySet();

     for (Map.Entry<String,String> entry: set){
     	driver.findElement(By.id(entry.getKey())).sendKeys(entry.getValue());
     
     }

    

     // Click on Submit

     driver.findElement(By.id("input_19")).click();
   itrate.wait(5000);
     
 }
      	}

}
