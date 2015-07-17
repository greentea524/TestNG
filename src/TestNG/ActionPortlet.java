package TestNG;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

public class ActionPortlet {

	private WebDriver driver;
	private String
	row1 = "iceDatTblRow1",
	ip1 = "//*[contains(@id,'IP1')]",
	searchbar = "//*[contains(@id,'pmeRCTaskDefSL')]",
	searchtext = "//*[contains(@id,'TaskDefsrchTxt')]";

	ActionItem action = new ActionItem();
	
	ActionPortlet(WebDriver driver, String url){
		driver.get(url);
		this.driver = driver;
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
	}
	public void searchAction(String target){

		System.out.println("search action: "+target);
		action.clickElement(driver, searchbar);
		action.sendKey(driver, searchtext, target);
		action.execute(driver, row1);
		
	}
	//test_ping ACLI
	public void testpingACLI(){
		System.out.println("test_ping execute");
		action.sendKey(driver, ip1, "10.128.2.10");
		action.clickExecute(driver);

	}

	
}
