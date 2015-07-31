package TestNG;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class NSDescriptor {
	private WebDriver driver;
	WebElement element;
	private String 
	newMenu = "//*[contains(@id,'_newNfvId')]",
	nsdPortlet = "//*[contains(@id,'NFVNetworkServiceDescriptorForm:ApmsttNFVNetworkServiceDescriptor')]",
	name = "//*[contains(@id,'Beanname')]",
	description = "//*[contains(@id,'Beandescription')]",
	enable = "//*[contains(@id,'Beanenabled')]",
	version = "//*[contains(@id,'Beanversion')]",
	vendor = "//*[contains(@id,'Beanvendor')]",
	findVnfd = "//*[contains(@id,'fString')]", //search field for vnfd
	vnfdItem = "//*[contains(@id,'outCell')]",
	row1 = "iceDatTblRow1" //first row item of a portlet or table
	;
	ActionItem action = new ActionItem();
	
	NSDescriptor(WebDriver driver, String url){
		this.driver = driver;
		driver.get(url);
	}
	public void descriptor(){
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		element = driver.findElement(By.xpath(nsdPortlet));
	}
	// requirements: for enabled NSD - VNFD, VLD, connection point, service flavor
	public void createNSD(){
		System.out.println("create network descriptor");
        action.clickMenu(driver,nsdPortlet,newMenu);
        
        fillNSD("NSDtest","auto","1.0","internal",null);
        //vnfd
        action.clickLinkText(driver, "VNF Descriptor");
        addVNFD("VNFauto2");
	}
	// parameters: name, description, version, vendor, enable
	public void fillNSD(String n, String d, String v, String ven, String e){
		if(n != null) action.sendKey(driver,name, n);
		if(d != null) action.sendKey(driver,description, d);
		if((e != null)) action.clickElement(driver,enable);
		action.clickLinkText(driver, "Version Info");
		if(v != null) action.sendKey(driver,version, v);
		if(ven != null) action.sendKey(driver,vendor, ven);
		action.clickApply(driver);
	}
	public void addVNFD(String n){
		action.clickAdd(driver);
		action.sendKeyandEnter(driver, findVnfd, n);
		action.clickGo(driver);
		driver.switchTo();
		action.selectItemInWindow(driver, vnfdItem);


		
	}
}
