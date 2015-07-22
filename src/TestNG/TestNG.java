package TestNG;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestNG {
  
	public String
	baseUrl = "http://192.168.54.43:8080";
	ProfilesIni profile = new ProfilesIni();
	FirefoxProfile myprofile = profile.getProfile("default"); //to load a firefox profile user
	public WebDriver driver = new FirefoxDriver(myprofile); 
	
	ActionItem action = new ActionItem();
	ManagedResource test = new ManagedResource(driver);
	ActionPortlet actionPortlet;
	NetworkDescriptor nsd;
	OpenStack os;
	
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
	public void login(){
		loginUrl(baseUrl);
		// HD-3571 # created NFV-407
		//test.resourcePortlet();
		//test.createNewDevice();
	}
	public void loginUrl(String url){
		driver.get(url);
		
        //login username and password
        driver.findElement(By.name("_58_login")).sendKeys("admin");
        driver.findElement(By.name("_58_password")).sendKeys("admin");
        driver.findElement(By.className("aui-button-input-submit")).click();

	}
	//@Test
	//NFV-392 # created NFV-411
	public void checkAPIpage(){
		try{
			//System.out.println("NFV-392");
			driver.get(baseUrl + "/rest-api-doc");
			String swag = "http://swagger.io";
			driver.findElement(By.linkText(swag));
		}catch (Throwable e){
			//Assert.fail("Unable to locate http://swagger.io");
		}
		
	}
	//@Test(priority = 2)
	//NFV descriptor - create vnfd and nsd
	public void NSD(){
		nsd = new NetworkDescriptor(driver, baseUrl + "/group/root/descriptor");
		nsd.descriptor();
		nsd.createTestVNF();
		//nsd.createNSD();

	}
	@Test(priority = 3)
	//Action - execute an action
	public void Action(){
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//actionPortlet.searchAction("test_ping");
		//actionPortlet.testpingACLI();
		System.out.println("NFV-440");
		//openstack1 10.101.160.2
		//parameters: vim, type, name, tenant, vendor, flavor, mgt, ext cp, stack item #
		testVnfAction("openstack1","stacks","F5-BIGtest","admin","F5","simple","mgt0","pkt0",1);
		testVnfAction("openstack1","stacks","VNFtest","admin","dorado","simple","mgt1","pkt1",2);
		testVnfAction("openstack1","servers","test-vm1","test2","dorado","simple","mgt2","pkt2",0);
	}
	//parameters: vim, type, name, tenant, vendor, flavor, mgt, ext cp, stack item #
	public void testVnfAction(String vim, String type, String name, String t, String v, String f, String mgt, String ext, Integer stackItem){
		actionPortlet = new ActionPortlet(driver, baseUrl + "/group/root/cm/actions");
		//menu item execute is 2
		actionPortlet.searchAction("Create Vnf Descriptor from Openstack Artifacts",2);
		actionPortlet.testVnfAction(vim,type,name,t,v,f,mgt,ext,stackItem);
	}

	//not used
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


    //@Test(priority = 4)
    //Test openstack availability
    public void checkOpenstack(){
    	os = new OpenStack();
    	System.out.println("Checking openstack controller status");
    	os.checkAvailablity(driver, "10.101.160.2");
    	os.checkAvailablity(driver, "10.101.155.2");
    	os.checkAvailablity(driver, "10.101.150.2");
    	os.checkAvailablity(driver, "10.101.50.2");
    	
    }

    
}
