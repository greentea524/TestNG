package TestNG;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class ActionItem {
	
	private WebDriver driver;
	private WebElement elementPath;
	private String 
	executeButton = "//button[@value='Execute']",
	cancelButton = "//button[@value='Cancel']",//"//*[contains(@id, 'cncBtn')]",
	applyButton = "//button[@value='Apply']",//"//*[contains(@id, 'applyBtn')]",
	addButton = "//button[@value='Add']",//"//*[contains(@id, 'addBtn')]",
	saveButton = "//button[@value='Save']",//"//*[contains(@id,'Save')]",
	closeButton = "//button[@value='Close']";//"//*[contains(@id,'Close')]";
	
	private int 
	menuItemUnit = 20,
	executeItem = 4; //4th menu item
	
	public ActionItem() {
		// TODO Auto-generated constructor stub
	}
	public void clickMenu(WebDriver driver, String path, String item){
		this.driver = driver;
		elementPath = driver.findElement(By.xpath(path));
    	//assignItem(item);
    	//move mouse to element e and right click. go to a menu item
        Actions builder = new Actions(driver);
        Action moveAction = builder
        		.moveToElement(elementPath)
        		.contextClick()
        		//.moveToElement(menuItem)
        		.moveByOffset(10, menuItemUnit) //first menu item - new
        		.clickAndHold()
        		.release()
        		.build();
        moveAction.perform();
	}
	public void execute(WebDriver driver, String path){
		elementPath = driver.findElement(By.className(path));
        Actions builder = new Actions(driver);
        Action moveAction = builder
        		.moveToElement(elementPath)
        		.click()
        		.moveToElement(driver.findElement(By.className("EmptyPadding")))
                .contextClick()
                .moveByOffset(10,menuItemUnit*executeItem) //item unit * menu item #
                .clickAndHold()
        		.release()
        		.build();
        moveAction.perform();
	}
//	private void assignItem(String item){
//		newMenu = item;
//		if(item.contains("new")){ 
//		menuItem = driver.findElement(By.xpath(newMenu));
//		}else{ System.out.println("352035235235"); }
//	}
	public void clickClose(){
		driver.findElement(By.xpath(closeButton)).click(); 
	}
	public void clickSave(){
		driver.findElement(By.xpath(saveButton)).click(); 
	}
	public void clickAdd(){
		driver.findElement(By.xpath(addButton)).click(); 
	}
	public void clickApply(){
		driver.findElement(By.xpath(applyButton)).click(); 
	}
	public void clickCancel(){
		driver.findElement(By.xpath(cancelButton)).click(); 
	}
	public void clickExecute(WebDriver driver){
		driver.findElement(By.xpath(executeButton)).click();
	}
	public void clickElement(WebDriver driver, String path){
		driver.findElement(By.xpath(path)).click();
	}
	public void clickLinkText(String text){
		System.out.println(text);
		driver.findElement(By.linkText(text)).click();
	}
	public void clickLinkText(WebDriver driver, String text){
		System.out.println(text);
		driver.findElement(By.linkText(text)).click();
	}
	public void clickByClass(WebDriver driver, String name){
		driver.findElement(By.className(name)).click();
	}
	//Given the xpath, send string text to the field
	public void sendKey(String path, String text){
		elementPath = driver.findElement(By.xpath(path));
		elementPath.click();
		elementPath.sendKeys(text);
	}
	public void sendKey(WebDriver driver, String path, String text){
		elementPath = driver.findElement(By.xpath(path));
		elementPath.click();
		elementPath.sendKeys(text);
		elementPath.sendKeys(Keys.ENTER);
	}
	public void selectVisible(String path, String text){
		elementPath = driver.findElement(By.xpath(path));
        new Select(elementPath).selectByVisibleText(text);
	}
	//click tree node example
//  String test = "//*[contains(@id,':0-0')]"; //tree node 1
//  action.clickElement(test);
//  action.clickLinkText("virtual55");
//  notes: //input[@name='action' and @value=' Search ']
}
