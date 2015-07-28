package TestNG;

import java.sql.ResultSet;
import java.sql.SQLException;
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
	url = "192.168.54.43",
	baseUrl = "http://"+url+":8080";
	ProfilesIni profile = new ProfilesIni();
	FirefoxProfile myprofile = profile.getProfile("default"); //to load a firefox profile user
	public WebDriver driver = new FirefoxDriver(myprofile); 
	
	ActionItem action = new ActionItem();
	ManagedResource test = new ManagedResource(driver);
	ActionPortlet actionPortlet;
	VNFDescriptor vnfd;
	OpenStack os;
	DBValidation db;
	
	@BeforeTest
	public void beforeTest() throws SQLException {
		
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
	//login p1
	@Test(priority = 1)
	public void login(){
		loginUrl(baseUrl);
		// HD-3571 # created NFV-407
		//test.resourcePortlet();
		//test.createNewDevice();
	}
	public void loginUrl(String url){
		driver.get(url);
        //login username and password
		action.clickById(driver, "_58_login", "admin");
		action.clickById(driver, "_58_password", "admin");
        //driver.findElement(By.name("_58_login")).sendKeys("admin");
        //driver.findElement(By.name("_58_password")).sendKeys("admin");
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
	//VNF descriptor - create vnfd
	//vnfd.searchAction - find VNF and edit
	//@Test(priority = 2)
	public void VNFD(){
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		vnfd = new VNFDescriptor(driver, baseUrl + "/group/root/descriptor");
		vnfd.descriptor();

		//edit test
		vnfd.searchAction("test-vm1", 2);//2 - vnf edit menuitem
		//editVNF parameters: name, description, enable, version, vendor, descriptor version

		//create test
		//nsd.createTestVNF();
		//nsd.createNSD();
	}
	//@Test(priority = 3)
    //@Test(priority = 3)
    //Test openstack availability
    public void checkOpenstack(){
    	os = new OpenStack();
    	System.out.println("Checking openstack controller status");
    	os.checkAvailablity(driver, "10.101.160.2");
    	os.checkAvailablity(driver, "10.101.155.2");
    	os.checkAvailablity(driver, "10.101.150.2");
    	os.checkAvailablity(driver, "10.101.50.2");
    	
    }

	@Test(priority = 4)
	//Action - execute an action
	public void action(){
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//actionPortlet.searchAction("test_ping");
		//actionPortlet.testpingACLI();
		System.out.println("NFV-440");
		//openstack1 10.101.160.2
		//parameters: vim, type, vnf test name, tenant, vendor, flavor, mgt, ext cp, stack item #
		//testVnfAction("openstack1","stacks","VNFtest","admin","dorado","simple","mgt1","pkt1",0);
		//testVnfAction("openstack1","servers","test-vm1","test2","dorado","simple","mgt2","pkt2",0);
		
		// TODO negative test cases
		testVnfAction("openstack1","stacks","VNF25232",null,null,"platinum","port1","port2",0);
		
		
		//check vnfd exist in db
		db = new DBValidation();
		db.dbtest(url, "root", "dorado", "owbusdb", 3306, false);
		try {
			if(db.checkValueInTable("test-vm1", "nfv_vnfdescriptor")) System.out.println("test-vm1 ok");
			if(db.checkValueInTable("F5-BIGtest", "nfv_vnfdescriptor")) System.out.println("F5-BIGtest ok");
			if(db.checkValueInTable("VNFtest", "nfv_vnfdescriptor")) System.out.println("VNFtest-vm1 ok");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.close();
		
	}
	//parameters: vim, type, name, tenant, vendor, flavor, mgt, ext cp, stack item #
	public void testVnfAction(String vim, String type, String name, String t, String v, String f, String mgt, String ext, Integer stackItem){
		actionPortlet = new ActionPortlet(driver, baseUrl + "/group/root/cm/actions");
		//menu item execute is 2
		actionPortlet.searchAction("Create Vnf Descriptor from Openstack Artifacts",2);//2 - action execute menu
		actionPortlet.testVnfAction(vim,type,name,t,v,f,mgt,ext,stackItem);
	}

    
}
