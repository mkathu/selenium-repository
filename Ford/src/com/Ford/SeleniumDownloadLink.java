package com.Ford;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;

public class SeleniumDownloadLink {
	
	public static WebDriver driver;
	
	static SeleniumDownloadLink seleniumDownloadLink = new SeleniumDownloadLink();
	
	public void setUp() {
		FirefoxProfile profile=new FirefoxProfile();
	    profile.setPreference("browser.download.folderList", "C:\\Images");
	    profile.setPreference("browser.download.folderList",2);
	    profile.setPreference("browser.download.useDownloadDir",true);
	    profile.setPreference("browser.download.dir","C:\\Images");
	    profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "image/jpeg");
	    profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "image/pjpeg");
	    profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "image/gif");
	    profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "image/png");
	    profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "image/png");
		driver = new FirefoxDriver(profile);
	}
	public List<WebElement> getImages() {

		List <WebElement> images = driver.findElements(By.xpath("//img"));
			return images;
		}
	
	public static void main(String [] args) throws Exception {
		seleniumDownloadLink.setUp();
		driver.get("http://www.google.com");
		Thread.sleep(6000);
		List<WebElement> ele = seleniumDownloadLink.getImages();
		Iterator<WebElement> its = ele.iterator();
		while (its.hasNext()) {
			WebElement element = its.next();
			//String eleme = element.getAttribute("src");
			//System.out.println("element is " + eleme);
			Actions act=new Actions(driver);
			act.moveToElement(element).contextClick().build().perform();
			Thread.sleep(3000);
			Robot robo=new Robot();
			robo.keyPress(KeyEvent.VK_DOWN);
			robo.keyRelease(KeyEvent.VK_DOWN);
			robo.keyPress(KeyEvent.VK_DOWN);
			robo.keyRelease(KeyEvent.VK_DOWN);
			robo.keyPress(KeyEvent.VK_DOWN);
			robo.keyRelease(KeyEvent.VK_DOWN);
			robo.keyPress(KeyEvent.VK_DOWN);
			robo.keyRelease(KeyEvent.VK_DOWN);
			robo.keyPress(KeyEvent.VK_ENTER);
			robo.keyRelease(KeyEvent.VK_ENTER);
		}
	}
	}
	


