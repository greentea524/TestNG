package test;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import PageFactory.Field;
import PageFactory.Page;
import PageFactory.Portlet;
import PageFactory.Login;

public class Start {
	WebDriver driver;
	Login objLogin;
	Page objPage;
	Portlet objPortlet;
	Field objField;

	@BeforeTest
	public void setup() throws IOException{
		String baseUrl = "http://192.168.54.43:8080";
		//String baseUrl = "chrome://restclient/content/restclient.html";
		//String baseUrl = "chrome://resteasy/content/resteasy.html";
		
		//Firefox Profile to loads extensions
		ProfilesIni allProfiles = new ProfilesIni();
		FirefoxProfile profile = allProfiles.getProfile("test");
		
		//Initialize and open browser URL
		driver = new FirefoxDriver(profile);
		driver.get(baseUrl);
		System.out.println(driver.getTitle());
	}
	//Login Redcell and click in NFV page and create NSD
	//@Test(priority=0)
	public void test1(){

		objLogin = new Login(driver);
		objPage = new Page(driver);
		objPortlet = new Portlet(driver);
	
		objLogin.loginWith("admin","admin");
		objPage.clickPage("NFV");
		objPortlet.listPortlet();
		
		objPortlet.createNSD();
		
	}
	//Login Redcell, go to NFV page and create PNFD
	@Test(priority=0)
	public void test2(){

		objLogin = new Login(driver);
		objPage = new Page(driver);
		objPortlet = new Portlet(driver);
		
		objLogin.loginWith("admin","admin");
		objPage.clickPage("NFV");
		objPortlet.listPortlet();
		
		objPortlet.createNSD();
		objPortlet.createPNFD();
		
	}
	//Use RESTClient in firefox
	//@Test(priority=1)
	public void test3() throws AWTException{
		
		//Select method to POST
		String method = "//*[contains(@id,'request-method')]";
		WebElement action = driver.findElement(By.xpath(method));
		System.out.println(retryingFindClick(By.xpath(method)));
		action.clear();
		action.sendKeys("POST");
		
		//Put appserver link
		String requestUrl = "//*[contains(@id,'request-url')]";
		action = driver.findElement(By.xpath(requestUrl));
		System.out.println(retryingFindClick(By.xpath(requestUrl)));
		action.clear();
		action.sendKeys("http://192.168.54.43:8089/rest/auth/");
	    
		//Put body
		String body = "{\"username\" : \"admin\",\"password\" : \"admin\"}";
		String text = "//*[contains(@id,'request-body')]";
		action = driver.findElement(By.xpath(text));
		System.out.println(retryingFindClick(By.xpath(text)));
		action.sendKeys(body);
		
		//Click and add Content-Type header
		objPage = new Page(driver);
		objPage.clickPage("Headers");
		objPage.clickPage("Content-Type: applicat...");
		
		//Press hotkey send
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_S);
		robot.keyPress(KeyEvent.VK_2);
	}
	//Premade Get Token query
	//@Test(priority=1)
	public void test4() throws AWTException{
		//to avoid StaleElementException
		String text = "//*[contains(@id,'request-body')]";
		System.out.println(retryingFindClick(By.xpath(text)));
		//Click saved query to Get Token
		objPage = new Page(driver);
		objPage.clickPage("Favorite Requests");
		objPage.clickPage("Get Token");
		
		//Press hotkey send
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_S);
		robot.keyPress(KeyEvent.VK_2);
	}
	public boolean retryingFindClick(By by) {
        boolean result = false;
        int attempts = 0;
        while(attempts < 2) {
            try {
                driver.findElement(by).click();
                result = true;
                break;
            } catch(StaleElementReferenceException e) {
            }
            attempts++;
        }
        return result;
    }

}
