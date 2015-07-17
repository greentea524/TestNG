package PageFactory;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Tab {
	WebDriver driver;
	@FindBy(className="aui-tab-label")
	List<WebElement> tabs;
	WebElement tab;
	
	public Tab(WebDriver driver){
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	public void listTab(){
		System.out.println("listTab()");
		for(WebElement webElement : tabs){
			System.out.println(webElement.getText());
		}
		
	}
	public void clickTab(String strTab){
		tab = driver.findElement(By.linkText(strTab));
		tab.click();
	}
	public void clickField(){
		Field field = new Field(driver);
		field.clickField();
	}
}
