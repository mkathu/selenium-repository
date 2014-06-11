package com.Ford;

import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Frames
{

	public static void main(String[] args) throws InterruptedException 
	{
		System.setProperty("webdriver.chrome.driver", "C:\\AEM-CQ-Automation\\AEM-CQ-Automation\\Ford\\Driver\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("http://wwwqa.inventory.ford.com/dealer/Ourisman-Ford--Lincoln-00141/dealerLot/?zipcode=22222&model=Fiesta&year=2014&segment=Car");
		WebDriverWait wait=new WebDriverWait(driver, 10000);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@id='getLocalOfferLink']")));
		driver.findElement(By.xpath("//span[@id='getLocalOfferLink']")).click();
		Thread.sleep(5000);
		List <WebElement>li=driver.findElements(By.xpath("//iframe"));
		Iterator <WebElement>ite=li.iterator();
		while(ite.hasNext())
		{
			WebElement eleq=ite.next();
		   String s=eleq.getAttribute("id");
			if(s.equals("cwindow"))
			{
				System.out.println("frame address: "+eleq);
				List<WebElement>lis=driver.findElements(By.xpath("//input"));
				System.out.println(lis.size());
				System.out.println("-------------------------------------------------");
				driver.switchTo().frame(eleq);
				System.out.println("switched");
				lis=driver.findElements(By.xpath("//input"));
				System.out.println(lis.size());

			}
		}
		
	
	}

}
