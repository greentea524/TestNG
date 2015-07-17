package TestNG;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ManagedResource {
	private WebDriver driver;
	private WebElement element;
	private String 
	vendorSel = "//*[contains(@name,'vendorCatSel')]",
	typeSel = "//*[contains(@id,'commonTypeSel')]",
	modelSel = "//*[contains(@id,'modelSel')]",
	equipName = "//*[contains(@name,'RedCellEquipmentManagerName_')]",
	equipIP = "//*[contains(@name,'RedCellEquipmentManagerIPAddress_')]",
	resPortlet = "//*[contains(@id,'EquipmentManagerForm:ApmsttEquipmentManager')]",
	newMenu = "//*[contains(@id,'_newId:link')]";
	
	ActionItem action = new ActionItem();
	
	ManagedResource(WebDriver driver){
		this.driver = driver;
	}
	
	public void resourcePortlet(){

		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		action.clickLinkText(driver,"Resources");
		element = driver.findElement(By.xpath(resPortlet));
		System.out.println(driver.getTitle());
		
	}
	public void createNewDevice(){
    	System.out.println("createNewDevice() enter");
    	System.out.println(resPortlet);
        action.clickMenu(driver,resPortlet,newMenu);
        
        // create new device and input IP address
        //Select Vendor
        action.clickElement(driver,vendorSel);
        action.selectVisible(vendorSel, "Unknown");
        //Select Type
        action.selectVisible(typeSel, "Other");
        //Select Model
        action.selectVisible(modelSel, "Unknown");
        //Create
        action.clickAdd();
        
        action.clickLinkText("General");
        //Fill device name
        action.sendKey(equipName, "test"+Calendar.DATE);
        //Fill ip address
        action.sendKey(equipIP, "10.128.99."+Calendar.DAY_OF_WEEK);

        //Click Save or Close
        action.clickClose();   
        
        //validate save closed window successfully
        try{
        	 if(element.isDisplayed()){
        		 Assert.fail("failed to save");
             }
             
        }catch (Throwable e){
        	System.out.println("saved");     
        }
       
		
    }

}
