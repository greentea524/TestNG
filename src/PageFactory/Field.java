package PageFactory;

import java.util.Calendar;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class Field {
	WebDriver driver;
	WebElement field;

	public Field(WebDriver driver){
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	public void clickField(){
		System.out.println("clickField() and fillField()");
		String name = "//*[contains(@id,'Beanname')]";
		field = driver.findElement(By.xpath(name));
		fillField("name");
		
		String description = "//*[contains(@id,'Beandescription')]";
		field = driver.findElement(By.xpath(description));
		fillField("description");

	}
	public void fillField(String text){
	    field.sendKeys(text+Calendar.SECOND);
	
	}
	public void fillField(String text, WebElement field){
		field.sendKeys(text);
	}
	
}
