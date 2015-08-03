package TestNG;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

public class ActionItem {
	
	WebDriver driver;
	WebElement elementPath;
	
	//web element xpath
	String 
	plusIcon = "//button[contains(@name, 'plus')]",
	minusIcon = "//button[contains(@name, 'minus')]",
	doneButton = "//input[@value='Done']",
	selectButton = "//input[@value='Select']",
	addSelection = "//input[@value='Add Selection']",
	goButton = "//button[@value='Go']",
	executeButton = "//button[@value='Execute']",
	editButton = "//button[@value='Edit']",
	deleteButton = "//input[@value='Delete']",
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
	//execute menu item #4
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
	//execute menu item # item against target
	public void execute(WebDriver driver, String target, Integer item){
		String path = "//span[@title='"+ target +"']";
		elementPath = driver.findElement(By.xpath(path));
        Actions builder = new Actions(driver);
        Action moveAction = builder
        		.moveToElement(elementPath)
        		.click()
        		.moveToElement(driver.findElement(By.className("EmptyPadding")))
                .contextClick()
                .moveByOffset(10,menuItemUnit*item) //item unit * menu item #
                .clickAndHold()
        		.release()
        		.build();
        moveAction.perform();
	}
	public void executeAction(WebDriver driver, String target, Integer item){
		elementPath = driver.findElement(By.className(target));
        Actions builder = new Actions(driver);
        Action moveAction = builder
        		.moveToElement(elementPath)
        		.click()
        		.moveToElement(driver.findElement(By.className("EmptyPadding")))
                .contextClick()
                .moveByOffset(10,menuItemUnit*item) //item unit * menu item #
                .clickAndHold()
        		.release()
        		.build();
        moveAction.perform();
	}
	public void selectItemInWindow(WebDriver driver, String path){
		elementPath = driver.findElement(By.className(path));
        Actions builder = new Actions(driver);
        Action moveAction = builder
        		.moveToElement(elementPath)
        		.click()
        		.build();
        moveAction.perform();
	}
	//select item -- matching the name
	public void selectItem(WebDriver driver, String target){
		String path = "//span[@title='"+ target +"']";
		elementPath = driver.findElement(By.xpath(path));
		elementPath.click();
	}
	//select item from drop down list
	public void selectItem(WebDriver driver, String path, Integer item){
		elementPath = driver.findElement(By.xpath(path));
        if(item != 0){
        	do{
        		elementPath.sendKeys(Keys.DOWN);
        		item--;
        	}while(item != 0);
        }
        if(item == 0) elementPath.sendKeys(Keys.ENTER);
	}
	public void clickClose(WebDriver driver){
		driver.findElement(By.xpath(closeButton)).click(); 
	}
	public void clickSave(WebDriver driver){
		driver.findElement(By.xpath(saveButton)).click(); 
	}
	public void clickAdd(WebDriver driver){
		driver.findElement(By.xpath(addButton)).click(); 
	}
	public void clickApply(WebDriver driver){
		driver.findElement(By.xpath(applyButton)).click(); 
	}
	public void clickEdit(WebDriver driver){
		driver.findElement(By.xpath(editButton)).click(); 
	}
	public void clickDelete(WebDriver driver){
		driver.findElement(By.xpath(deleteButton)).click(); 
	}
	public void clickCancel(WebDriver driver){
		driver.findElement(By.xpath(cancelButton)).click(); 
	}
	public void clickPlus(WebDriver driver){
		driver.findElement(By.xpath(plusIcon)).click(); 
	}
	public void clickMinus(WebDriver driver){
		driver.findElement(By.xpath(minusIcon)).click(); 
	}
	public void clickAddSelection(WebDriver driver){
		driver.findElement(By.xpath(addSelection)).click(); 
	}
	public void clickDone(WebDriver driver){
		driver.findElement(By.xpath(doneButton)).click(); 
	}
	public void clickSelect(WebDriver driver){
		driver.findElement(By.xpath(selectButton)).click(); 
	}
	public void clickGo(WebDriver driver){
		driver.findElement(By.xpath(goButton)).click(); 
	}
	public void clickExecute(WebDriver driver){
		driver.findElement(By.xpath(executeButton)).click();
	}
	public void clickElement(WebDriver driver, String path){
		driver.findElement(By.xpath(path)).click();
	}
	public void doubleClick(WebDriver driver, String path){
		driver.findElement(By.xpath(path)).click();
		driver.findElement(By.xpath(path)).click();
	}
	public void clickLinkText(WebDriver driver, String text){
		driver.findElement(By.linkText(text)).click();
	}
	public void clickByClass(WebDriver driver, String name){
		driver.findElement(By.className(name)).click();
	}
	public void clickById(WebDriver driver, String id, String text){
		elementPath = driver.findElement(By.id(id));
		elementPath.click();
		elementPath.sendKeys(text);
	}
	public void clickByTitle(WebDriver driver, String text){
		String path = "//span[@title='"+ text +"']";
		elementPath = driver.findElement(By.xpath(path));
		elementPath.click();
	}
	//Given the xpath, send string text to the field
	public void sendKey(WebDriver driver, String path, String text){
		elementPath = driver.findElement(By.xpath(path));
		elementPath.click();
		elementPath.clear();
		elementPath.sendKeys(text);
	}
	public void sendKeyandEnter(WebDriver driver, String path, String text){
		elementPath = driver.findElement(By.xpath(path));
		elementPath.click();
		elementPath.sendKeys(text);
		elementPath.sendKeys(Keys.ENTER);
	}
	public void selectVisible(WebDriver driver, String path, String text){
		elementPath = driver.findElement(By.xpath(path));
        new Select(elementPath).selectByVisibleText(text);
        elementPath.sendKeys(Keys.ENTER);
	}

	public boolean checkElement(WebDriver driver, String path){
		if(driver.findElement(By.xpath(path)).isDisplayed()) return true;
		return false;
	}

	//click tree node example
//  String test = "//*[contains(@id,':0-0')]"; //tree node 1
//	driver.findElements(By.xpath("//span[text()='Child Item One']"))
//  action.clickElement(test);
//  action.clickLinkText("virtual55");
//  notes: //input[@name='action' and @value=' Search ']
}
