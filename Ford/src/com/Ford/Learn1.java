package com.Ford;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Learn1 
{
	public static void main(String[] args) 
	{
	  WebDriver driver= new FirefoxDriver();
	  driver.get("C:\\Users\\mkarthik\\Documents\\XpathGen\\Trial.html");
	  WebElement ele=driver.findElement(By.cssSelector("html > body > div"));
	 System.out.println(ele.getText());

	}

}
