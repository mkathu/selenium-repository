package com.Ford;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

public class Samples
{

	public static void main(String[] args) throws InterruptedException, MalformedURLException
	{
		 final DesiredCapabilities capability = DesiredCapabilities.firefox();
         capability.setBrowserName("firefox");
         capability.setPlatform(Platform.WINDOWS);
         //capability.setVersion("10");
         WebDriver driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capability);
         
		Thread.sleep(5000);
		driver.get("https://www.google.co.in");
		Thread.sleep(5000);
		driver.close();

	}

}
