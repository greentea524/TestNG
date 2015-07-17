package PageFactory;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Login {

	WebDriver driver;
	@FindBy(name="_58_login")
	WebElement username;
	
	@FindBy(name="_58_password")
	WebElement password;
	
	@FindBy(className="aui-button-input-submit")
	WebElement login;
	
	public Login(WebDriver driver){
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	//click login button
	public void clickLogin(){
		login.click();
	}
	
	//send username and password keys
	public void loginWith(String strUsername, String strPassword){
		username.sendKeys(strUsername);
		password.sendKeys(strPassword);
		clickLogin();
		System.out.println("loginTo()");
	}
	
}
