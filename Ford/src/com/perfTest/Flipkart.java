package com.perfTest;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Flipkart
{
	public static void main(String[] args) 
	{
		System.out.println("startted...");
      WebDriver driver=new FirefoxDriver();
      StopWatch watch=new StopWatch();
      
      watch.start();
      driver.get("http://www.flipkart.com/");
      //new WebDriverWait(driver, 10).until(ExpectedConditions.)
      driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
      watch.stop();
      
      System.out.println("time taken for page load: "+watch.getTime());
	}

}
