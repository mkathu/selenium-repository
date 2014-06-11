package com.Ford;

import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class CarPrice 
{

	public static void main(String[] args) throws InterruptedException 
	{
		WebDriver driver=new FirefoxDriver();
		driver.get("http://wwwqa.inventory.ford.com/");
		driver.manage().timeouts().pageLoadTimeout(5000,TimeUnit.MILLISECONDS);
		Thread.sleep(6000);
		driver.findElement(By.xpath("//div[@id='tdi_menuTopTable']/div[contains(@id,'tdi_globalNav')]/descendant::div[text()='TRUCKS']")).click();
		Thread.sleep(3000);
		String priceInTab=driver.findElement(By.xpath("//div[@id='tdi_menuTopTable']/following-sibling::div[@id='globalnav-content']/descendant::div[@class='topContain genVehic']/div[contains(@id,'tdi_trucks_plac')]/descendant::div[contains(@onmouseup,'transit')]/dl/descendant::dd[1]")).getText();
		System.out.println("priceInTab: "+priceInTab);
		int len=priceInTab.length();
		System.out.println("len: "+len);
		priceInTab=priceInTab.substring(0,len-1);
		System.out.println("new PriceInTab: "+priceInTab);
		driver.findElement(By.xpath("//div[@class='btnCloseTxt']")).click();
        String priceInView=driver.findElement(By.xpath("//div[@id='model-showroom-current-year-container']/div[3]/div[@id='models-all-container']/descendant::div[contains(@yearmodelsegmentname,'Transit')]/div[@class='model-price-container']/div[contains(@class,'displayPrice MSRPPrice')]/div[@class='model-price']")).getText();
        System.out.println("priceInView: "+priceInView);
        boolean res=priceInTab.equals(priceInView);
        System.out.println("res: "+res);
        Assert.assertEquals("does not matches",true,res);
	}

}
