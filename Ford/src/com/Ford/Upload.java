package com.Ford;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Upload {

public static void main(String[] args) throws IOException {
WebDriver driver = new FirefoxDriver();
driver.get("http://my.naukri.com/manager/createacc2.php?&nojs=1");
driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
driver.switchTo().frame("frmUpload");
driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
driver.findElement(By.id("browsecv")).click();
driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
Runtime.getRuntime().exec("H:Autoit1.exe");
driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
//driver.close();
}
}
