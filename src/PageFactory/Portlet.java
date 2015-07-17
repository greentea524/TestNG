package PageFactory;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Portlet {
	WebDriver driver;
	Tab tab;

	@FindBy(xpath="//h1/span")
	List<WebElement> portlets;
	
	@FindBy(xpath="//*[@id]")
	List<WebElement> ids;
	
	String close = "//*[contains(@id,'boeditorClose')]";
	String save = "//*[contains(@id,'boeditorSave')]";
	
	public Portlet(WebDriver driver){
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	public void listPortlet(){
		//Get list of portlets
		for(WebElement webElement : portlets){
			System.out.println(webElement.getText());
		}
	}
	public void listIds(){
		for(WebElement webElement : ids){
			System.out.println(webElement.getAttribute("id"));
		}
	}
	public void createNSD(){
		String path = "//*[contains(@id,'NFVNetworkServiceDescriptorForm:ApmsttNFVNetworkServiceDescriptor')]";
		WebElement test = driver.findElement(By.xpath(path));
		testPortlet(test);
		tab = new Tab(driver);
		tab.listTab();
		//listIds();
		tab.clickField();
		clickButton(close);
	}
	public void createPNFD(){
		String path = "//*[contains(@id,'NFVPnfDescriptorForm:ApmsttNFVPnfDescriptor')]";
		WebElement test = driver.findElement(By.xpath(path));
		testPortlet(test);
		tab = new Tab(driver);
		tab.listTab();
		//listIds();
		tab.clickField();
		//tab.clickTab("Connection Point");

		clickButton(close);
		//clickButton(save);
		
	}
	public void clickButton(String str){

        driver.findElement(By.xpath(str)).click();  
	}
    public void testPortlet(WebElement e){
    	System.out.println("testPortlet() enter");

    	//move mouse to element e and right click. go to New on menu
        Actions builder = new Actions(driver);
        Action moveAction = builder
        		.moveToElement(e)
        		.contextClick()
        		.moveByOffset(10,10)
        		.clickAndHold()
        		.release()
        		.build();
        moveAction.perform();

       
        // Connection Point tab    
        //driver.findElement(By.linkText("Connection Point")).click(); 
        
        // Close
        //String close = "_106_WAR_netview_INSTANCE_IwmcbDG3XF64_:j_id7:boeditorClose";
        //driver.findElement(By.name(close)).click();    
    }
}
