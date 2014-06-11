package com.Ford;

import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DealeredOrder 
{

	public static void main(String[] args) throws InterruptedException 
	{
		WebDriver driver=new FirefoxDriver();
		driver.get("http://wwwqa.inventory.ford.com/dealer/Sarat-Ford-Lincoln-09137/dealerLot?zipcode=01001&segment=Car&model=Fiesta&year=2014;2013&inventoryType=extended&return=Complete#vin=vin3FADP4AJ6EM159188");
		
		Thread.sleep(5000);
		List<WebElement>lisParent=driver.findElements(By.xpath("//div[@id='ExactMatchContainer']/div[contains(@class,'inventory_vehicles_list')]/div[contains(@class,'inventory')]/div[contains(@class,'cell_cost')]/div[contains(@class,'basic')]"));
		Iterator<WebElement>iteParents=lisParent.iterator();
		List<WebElement>lisWantedCars = null;
		while(iteParents.hasNext())
		{
			WebElement parent=iteParents.next();
			List<WebElement>lisChildren=parent.findElements(By.xpath("/div"));
			if(lisChildren.size()>2)
			{
				break;
			}
			else
			{
				lisWantedCars.add(parent);
				System.out.println("parent doesnt contain dealered Orders...");
			}
		}

	}

}
