package TestNG;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestNG {
  
	public String baseUrl = "http://192.168.54.43:8080";
	ProfilesIni profile = new ProfilesIni();
	FirefoxProfile myprofile = profile.getProfile("default"); //to load a firefox profile user
	public WebDriver driver = new FirefoxDriver(myprofile); 
	
	ActionItem action = new ActionItem();
	ManagedResource test = new ManagedResource(driver);
	ActionPortlet actionPortlet;
	NetworkDescriptor nsd;
	
	@BeforeTest
	public void openUrl() {
		
		driver.get("");
		driver.manage().window().maximize();
		
    }
	@Test
	public void c_test() {
		//Assert.fail();
	}
	
	@Test
	public void a_test() {
		Assert.assertTrue(true);
	}
	
	@Test
	public void b_test() {
		//throw new SkipException("skipping b_test...");	
	}
	@Test
	// HD-3571 # created NFV-407
	public void createDevice(){
		loginUrl();
		//test.resourcePortlet();
		//test.createNewDevice();
	}
	@Test
	//NFV-392 # created NFV-411
	public void checkAPIpage(){
		try{
			//System.out.println("NFV-392");
			restAPIdoc(baseUrl + "/rest-api-doc");
		}catch (Throwable e){
			//Assert.fail("Unable to locate http://swagger.io");
		}
		
	}
	@Test(priority = 2)
	//NFV descriptor - create vnfd and nsd
	public void NSD(){
		nsd = new NetworkDescriptor(driver, baseUrl + "/group/root/descriptor");
		nsd.descriptor();
		nsd.createTestVNF();
		//nsd.createNSD();

	}
	//@Test(priority = 3)
	//Action - execute an action
	public void Action(){
		actionPortlet = new ActionPortlet(driver, baseUrl + "/group/root/cm/actions");
		actionPortlet.searchAction("test_ping");
		actionPortlet.testpingACLI();
	}
	public void loginUrl(){
		driver.get(baseUrl);
        //login username and password
        driver.findElement(By.name("_58_login")).sendKeys("admin");
        driver.findElement(By.name("_58_password")).sendKeys("admin");
        System.out.println(driver.getTitle());
        
        driver.findElement(By.className("aui-button-input-submit")).click();
        System.out.println(driver.getTitle());
	}
	public void clickPages(){
        //go to pages
		String[] pages = {"NFV", "Admin", "Sign Out"};
		int i = 0;

		do{
			driver.findElement(By.linkText(pages[i])).click();
			System.out.println(driver.getTitle());
			if(i < pages.length)i++;
			else i = -1;			
		}while (i < pages.length);
	}


	public void restAPIdoc(String url ){
		
		driver.get(url);
		String swag = "http://swagger.io";
		driver.findElement(By.linkText(swag));
		//Assert.fail();
		
	}

    

    
}
