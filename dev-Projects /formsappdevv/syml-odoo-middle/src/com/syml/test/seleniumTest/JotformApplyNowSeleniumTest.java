package com.syml.test.seleniumTest;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class JotformApplyNowSeleniumTest {
	public static WebDriver driver;
	public static void main(String[] args) {
		 // Create a new instance of the Firefox driver
		 
        driver = new FirefoxDriver();
 
        // Put an Implicit wait, this means that any search for elements on the page could take the time the implicit wait is set for before throwing exception
 
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
 
        // Launch the ToolsQA Website
 
        driver.get("http://form.jotform.me/form/43496529286469");
 
        // Type Name      
        HashMap map= new HashMap();
        map.put("input_14", "vikash");
        map.put("input_15", "singh");
        map.put("input_20", "989-237-7867");
        map.put("input_16", "vik@yahoo.com");
        map.put("input_16_confirm", "vik@yahoo.com");
        Set<Map.Entry<String, String>> set = map.entrySet();

        for (Map.Entry<String,String> entry: set){
        	driver.findElement(By.id(entry.getKey())).sendKeys(entry.getValue());
        }
 
       
 
        // Click on Submit
 
        driver.findElement(By.id("input_19")).click();
	}

}
