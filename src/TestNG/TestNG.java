package TestNG;

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
	NSDescriptor nsd;
	OpenStack os;
	DBValidation db;
	
	@BeforeTest
	public void beforeTest() throws SQLException {
		
		driver.get("");
		driver.manage().window().maximize();
		
		
    }
//	@Test
//	public void c_test() {
//		//Assert.fail();
//	}
//	
//	@Test
//	public void a_test() {
//		Assert.assertTrue(true);
//	}
//	
//	@Test
//	public void b_test() {
//		//throw new SkipException("skipping b_test...");	
//	}
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

	/****************** 2 **********/
	//VNF descriptor - create or edit vnfd
	//@Test(priority = 2)
	public void VNFD(){
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		vnfd = new VNFDescriptor(driver, baseUrl + "/group/root/descriptor");
		vnfd.descriptor();

		//edit test
		//vnfd.editAction("test-vm1");
		//editVNF parameters: name, description, enable, version, vendor, descriptor version

		//create test
		vnfd.createTestVNF("VNFtest");
		//delete test
		//deleteVNFD("VNFtest");
	}
	/***************** 3 ************/
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

    /***************** 4 *********************/
	@Test(priority = 4)
	//Action - execute an action
	public void actionVNFD(){
		//actionPortlet.searchAction("test_ping");
		//actionPortlet.testpingACLI();
		System.out.println("NFV-440");
		
		boolean on = true;
		if(on){
		//openstack1 10.101.160.2
		//parameters: vim, type, vnf test name, tenant, vendor, flavor, mgt, ext cp, stack item #
		testVnfAction("openstack1","stacks","VNFauto1","admin","vnf","simple","mgt1","pkt1",0);//vnf
		testVnfAction("openstack1","servers","VNFauto2","test2","testvm","m1.tiny","mgt2","pkt2",0);//test-vm1
		testVnfAction("openstack1","stacks","VNFauto3",null,null,"basic","port1","port2",0); //no required tenant
		//testVnfAction("openstack1",null,null,null,null,"platinum","port1","port2",0); //no vnfd name	
		
		//openstack5 10.101.50.2 
		//openstack6 10.101.170.3
		testVnfAction("openstack5","stacks","VNFauto4","admin","test-sonus","silver","mgt0","pk0",0); 
		testVnfAction("openstack5","stacks","VNFauto5","admin","teststack","gold","mgt0","pk0",1); 
		testVnfAction("openstack6","stacks","VNFauto6","QA","f5","bronze","mgt0","pkt0",0); 
		
		}
		
		
		//check vnfd exist in db
		db = new DBValidation();
		db.dbtest(url, "root", "dorado", "owbusdb", 3306, false);
		try {
			if(db.checkValueInTable("VNFauto1", "nfv_vnfdescriptor")) System.out.println("VNFauto1 ok");
			if(db.checkValueInTable("VNFauto2", "nfv_vnfdescriptor")) System.out.println("VNFauto2 ok");
			if(db.checkValueInTable("VNFauto3", "nfv_vnfdescriptor")) System.out.println("VNFauto3 ok");
			if(db.checkValueInTable("VNFauto4", "nfv_vnfdescriptor")) System.out.println("VNFauto4 ok");
			if(db.checkValueInTable("VNFauto5", "nfv_vnfdescriptor")) System.out.println("VNFauto5 ok");
			if(db.checkValueInTable("VNFauto6", "nfv_vnfdescriptor")) System.out.println("VNFauto6 ok");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.close();
		
		//delete test
		on = false;
		if(on){
		deleteVNFD("VNFauto1");
		deleteVNFD("VNFauto2");
		deleteVNFD("VNFauto3");
		}
		
	}
	/**************** 5 **************/
	//@Test(priority = 5) 
	//network service descriptor test - deploy
	public void NSD(){
		nsd = new NSDescriptor(driver, baseUrl + "/group/root/descriptor");
		nsd.descriptor();
		nsd.createNSD();
		//nsd.deployNSD("test");
	}
	
	/*************** functions ****************/
	
	//VNF descriptor - delete vnfd
	public void deleteVNFD(String toDelete){
		boolean ok = false;
		
		//check vnfd exist in db
		db = new DBValidation();
		db.dbtest(url, "root", "dorado", "owbusdb", 3306, false);
		try {
			if(db.checkValueInTable(toDelete, "nfv_vnfdescriptor")) {
				System.out.println(toDelete + " exist");
				ok = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.close();
		
		//delete if exist
		if(ok){
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			vnfd = new VNFDescriptor(driver, baseUrl + "/group/root/descriptor");
			vnfd.descriptor();
			vnfd.deleteAction(toDelete);
			System.out.println(toDelete + " deleted");
		}

	}


	//parameters: vim, type, name, tenant, vendor, flavor, mgt, ext cp, stack item #
	public void testVnfAction(String vim, String type, String name, String t, String v, String f, String mgt, String ext, Integer stackItem){
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		actionPortlet = new ActionPortlet(driver, baseUrl + "/group/root/cm/actions");
		//menu item execute is 2
		actionPortlet.searchAction("Create Vnf Descriptor from Openstack Artifacts",2);//2 - action execute menu
		actionPortlet.testVnfAction(vim,type,name,t,v,f,mgt,ext,stackItem);
	}

    
}
