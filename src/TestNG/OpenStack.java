package TestNG;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class OpenStack {
	ActionItem action = new ActionItem();
	WebElement web;
	String
	osLogin = "Login - Mirantis OpenStack Dashboard",
	osTitle = "Usage Overview - Mirantis OpenStack Dashboard",
	submit = "//button[@type='submit']";
	
	
	public OpenStack() {
		// TODO Auto-generated constructor stub
	}
	public void checkAvailablity(WebDriver driver, String ip){
		//System.out.print("Checking " + ip + " controller");
		driver.get(ip);
		
		//Check if Login page is up
		if(driver.getTitle().equalsIgnoreCase(osLogin)){
		action.clickById(driver, "id_username", "admin");
		action.clickById(driver, "id_password", "admin");
		action.clickElement(driver, submit);
		//System.out.println(ip + " controller is UP");
		//System.out.println("logged");
		}else{
			//System.out.println("unable to log in -- ");
			//System.out.println(ip + " controller is DOWN");
		}
		
		//Check if Title page is showing after login
		if(driver.getTitle().equalsIgnoreCase(osTitle)){
			System.out.println(ip + " controller is UP");
			action.clickLinkText(driver, "Sign Out");
			
		}else{
			System.out.println(ip + " controller is DOWN");
		}
		
		
	}
}
