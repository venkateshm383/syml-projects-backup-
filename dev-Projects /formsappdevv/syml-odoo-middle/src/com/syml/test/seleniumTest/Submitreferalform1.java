package com.syml.test.seleniumTest;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Submitreferalform1 {

private WebDriver driver;
private String baseurl;

	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
      driver.manage().timeouts().implicitlyWait(120,TimeUnit.SECONDS);
      System.out.println(" Firefox driver loaded");
      baseurl = "http://107.23.130.227/visdom/submitreferral/";
	}

	@Test
	public void test() throws IOException {
		driver.get(baseurl);
		driver.findElement(By.partialLinkText("Submit Referal form1D")).click();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		String str;    
		 try
		 {
		String Inputpath ;
		Inputpath = "N:\\Testdata\\Input.txt";
	     FileInputStream fr = new FileInputStream(Inputpath);
		 BufferedReader br=null;
		 br = new BufferedReader (new InputStreamReader(fr));
		 while((str=br.readLine())!=null)
			 {
				System.out.println(str);
			     String[] ar=str.split(",");
			     String input1= ar[0];
			     String input2= ar[1];
			     String input3=ar[2];
			     System.out.println("The first input is :" + input1);
			     System.out.println("The Second input is :" + input2);
			     System.out.println("The Third input is :" + input3);
			    //From here on selenium would be sending data to form1
			     driver.findElement(By.xpath("/html/body/form/div/ul/li[3]/div/input")).sendKeys("test");
			     System.out.println("Keys sent");
			     driver.findElement(By.xpath("/html/body/form/div/ul/li[4]/div/input")).sendKeys(input2);
			     driver.findElement(By.xpath("/html/body/form/div/ul/li[5]/div/input")).sendKeys(input3);
			     driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
			     driver.findElement(By.id("input_2")).click();
			     driver.manage().deleteAllCookies();
			     String currenturl = driver.getCurrentUrl();
			     System.out.println(currenturl);
			     driver.get(baseurl);
			     driver.manage().window().maximize();
				 driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
			  }
		 br.close();
		 }catch (IOException ex) {
			 System.out.println(" The exception is : " + ex);
		 }
	    
	}


	@After
	public void tearDown() throws Exception {
		System.out.println("browser ended");
		driver.quit();
	}

}
