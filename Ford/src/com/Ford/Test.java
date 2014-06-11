package com.Ford;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.TextComponent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.plaf.basic.BasicOptionPaneUI.ButtonActionListener;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.events.MouseEvent;
import org.w3c.dom.views.AbstractView;

import com.gargoylesoftware.htmlunit.javascript.host.EventHandler;
import com.gargoylesoftware.htmlunit.javascript.host.Text;
import com.sun.java.accessibility.util.java.awt.TextComponentTranslator;

public class Test 
{

	public static void main(String[] args) throws AWTException
	{
		WebDriver driver=new FirefoxDriver();
		driver.get("file:///C:/Users/mkarthik/Downloads/test.html");
		driver.findElement(By.tagName("input")).click();
		Robot robo=new Robot();
		robo.keyPress(KeyEvent.VK_C);//C:\Users\mkarthik\Downloads
		robo.keyRelease(KeyEvent.VK_C);
		robo.keyPress(KeyEvent.VK_COLON);
		robo.keyRelease(KeyEvent.VK_COLON);
		robo.keyPress(KeyEvent.VK_BACK_SLASH);
		robo.keyRelease(KeyEvent.VK_BACK_SLASH);
	}

}
