package PageFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class Page {
	WebDriver driver;
	WebElement page;
	
	public Page(WebDriver driver){
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	public void clickPage(String strPage){
		page = driver.findElement(By.linkText(strPage));
		page.click();
	}
}
