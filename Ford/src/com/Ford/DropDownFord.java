package com.Ford;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DropDownFord 
{

	public static void main(String[] args) 
	{
		WebDriver driver=new FirefoxDriver();
		driver.get("http://wwwqa.inventory.ford.com/dealer/Sarat-Ford-Lincoln-09137/dealerLot?zipcode=01001&segment=Car");
		WebDriverWait wait=new WebDriverWait(driver,4000);
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@class='selectedSegmentId']")));
		driver.findElement(By.xpath("//div[@class='selectedSegmentId']")).click();
		wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class='segmentIds']/ul/li/div")));
		List<WebElement>listSegment=driver.findElements(By.xpath("//div[@class='segmentIds']/ul/li/div"));
		System.out.println("no of elements: "+listSegment.size());
		
	}

}
