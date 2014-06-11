package com.Ford;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

public class sample
{
	/*String browser;
sample(String typeBrowser)
{
	browser=typeBrowser;
}*/
	public static void main(String[] args) throws InterruptedException, MalformedURLException
	{
		/*Scanner sc=new Scanner(System.in);
		System.out.println("enter the type of browser: ");
		String type=sc.next();
		if(type.equals("iexplore"))
		{
			System.setProperty("webdriver.ie.driver", "C:\\Program Files\\Internet Explorer\\IEDriverServer.exe");
		}*/
		System.setProperty("webdriver.ie.driver", "C:\\Program Files\\Internet Explorer\\IEDriverServer.exe");
		 final DesiredCapabilities capability = DesiredCapabilities.firefox();
         capability.setBrowserName("iexplore");
         capability.setPlatform(Platform.WINDOWS);
         //capability.setVersion("10");
         WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capability);
         
		Thread.sleep(5000);
		driver.get("https://www.google.co.in");
		Thread.sleep(5000);
		driver.close();
	}

}
