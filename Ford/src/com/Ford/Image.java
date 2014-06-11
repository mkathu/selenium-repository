package com.Ford;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Image
{
	public static void main(String[] args) throws InterruptedException
	{
      WebDriver driver = new FirefoxDriver();
      driver.get("http://wwwqa.inventory.ford.com/");   
      Thread.sleep(10000);
      List<WebElement>li=driver.findElements(By.xpath("//div[3]/div[2]/div[4]/div[3]/div[1]/div[1]/div[2]/div/descendant::div[@class='model-show-container']/div[2]/img[1]"));
      int size=li.size();
      System.out.println(li.size());
      /*Iterator<WebElement>ite=li.iterator();*/
      int i=1;
      String car,sub,src;
     // while(ite.hasNext())
      for(int j=1;j<=6;j++)
      {
    	  WebElement elee=driver.findElement(By.xpath("//div[3]/div[2]/div[4]/div[3]/div[1]/div[1]/div[2]/div["+j+"]/descendant::div[@class='model-show-container']/div[2]/img[1]"));
    	  System.out.println("1: elee: "+elee);
    	  car=driver.findElement(By.xpath("//div[3]/div[2]/div[4]/div[3]/div[1]/div[1]/div[2]/div["+i+"]/div/div[1]/div[1]/div[2]")).getText();
    	  System.out.println("car: "+car);
    	  if(car.equals("C-MAX"))
    	  {
    		  src=elee.getAttribute("src");
    	  }
    	  else
    	  {
    	   sub=car.substring(1);
    	   boolean res=sub.contains("-");
    	   if(res==true)
    	   {
    		  sub=sub.replace("-","");
    	   }
    	    sub=sub.toLowerCase();
    	    car=Character.toString(car.charAt(0));
    	    car=car+sub;
    	    System.out.println("after conversion..."+car);
    	    src=elee.getAttribute("src");
    	  }
    	 
    	  System.out.println("2: src: "+src); 
    	  try{
    	  driver.get(src+"1");
    	  Thread.sleep(10000);  
    	  WebElement we=driver.findElement(By.xpath("//.[contains(@src,'"+car+"')]"));
    	  System.out.println("3: "+we);
    	  Assert.assertNotNull("element not found..",we);//if we is null then this means that link is broken....
    	  }
    	  catch(Exception e)
    	  {
    		  System.out.println("element not found....");
    	  }
    	  driver.navigate().back();
    	  driver.manage().timeouts().pageLoadTimeout(5000, TimeUnit.MILLISECONDS);
    	  WebDriverWait wait=new WebDriverWait(driver,5000);
    	  wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[3]/div[2]/div[4]/div[3]/div[1]/div[1]/div[2]/div/descendant::div[@class='model-show-container']/div[2]/img[1]")));
    	  i=i+1;
    	  if(i==size)
    	  {
    		  break;
    	  }
    	  
      }
	}

}
