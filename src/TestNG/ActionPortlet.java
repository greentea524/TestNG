package TestNG;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

public class ActionPortlet {

	private WebDriver driver;
	private String
	discMethod = "//*[contains(@id,'DiscoveryMethod')]",
	tenantName = "//*[contains(@id,'TenantName')]",
	stacksBox = "//*[contains(@id,'Fld')]",
	vnfDescName = "//*[contains(@id,'VnfDescriptorName')]",
    vnfDescDescription = "//*[contains(@id,'VnfDescriptorDescription')]",
    vnfDescVersion = "//*[contains(@id,'VnfDescriptorVersion')]",
    vnfDescVendor = "//*[contains(@id,'VnfDescriptorVendor')]",
    vnfFlavor = "//*[contains(@id,'VnfFlavorName')]",
    groupVDU = "//*[contains(@id,'GroupVDUsBy')]",
    mgmtInterface = "//*[contains(@id,'ManagementInterfaceName')]",
    vnfExtConnPt = "//*[contains(@id,'VnfExternalConnectionPoint')]",
	vimfind = "//*[contains(@id,'fString')]", //search field for testVnfAction()
	row1 = "iceDatTblRow1", //first row item of a portlet or table
	ip1 = "//*[contains(@id,'IP1')]",
	searchbar = "//*[contains(@id,'pmeRCTaskDefSL')]", //action portlet search
	searchtext = "//*[contains(@id,'TaskDefsrchTxt')]";

	ActionItem action = new ActionItem();
	
	ActionPortlet(WebDriver driver, String url){
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		this.driver = driver;
	}
	//action with execute button 4th menu item
//	public void searchAction(String target){
//
//		System.out.println("search action: "+target);
//		action.clickElement(driver, searchbar);
//		action.sendKey(driver, searchtext, target);
//		action.execute(driver, row1);
//		
//	}
	//action with menuItem # passed into
	public void searchAction(String target, Integer menuItem){
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		System.out.println("search action: "+target);
		action.clickElement(driver, searchbar);
		action.sendKeyandEnter(driver, searchtext, target);
		action.execute(driver, row1, menuItem);
	}
	//test_ping ACLI
	public void testpingACLI(){
		System.out.println("test_ping execute");
		action.sendKeyandEnter(driver, ip1, "10.128.2.10");
		action.clickExecute(driver);

	}
	//test NFV-428 "Create Vnf Descriptor from Openstack Artifacts"
	//parameters: vim, type, name, tenant, vendor, flavor, mgt, ext cp, stack item#
	public void testVnfAction(String vim, String type, String name, String t, String v, String f, String mgt, String ext, Integer item){
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		System.out.println(vim + " " + name);
		//select and add vim
		if(action.checkElement(driver,vimfind)){
		action.sendKeyandEnter(driver, vimfind, vim);
		action.clickGo(driver);
		action.selectItemInWindow(driver, row1);
		action.clickAddSelection(driver);
		action.clickDone(driver);
		
		action.clickLinkText(driver,"Input");
		//fill out fields before executing the action
		if(type != null){
			
			if(type.equalsIgnoreCase("stacks")){
			//default
			}
			if(type.equalsIgnoreCase("servers")){
				action.clickElement(driver, discMethod);
				action.selectVisible(driver, discMethod, "Servers");
			}
		}
		//tenant
		if(t != null){
			action.clickElement(driver, tenantName);
			action.selectVisible(driver, tenantName, t);
		}
		//stack/server
		if(type != null) action.clickElement(driver, stacksBox);
		if(item != 0) action.selectItem(driver, stacksBox, item); //default item #1 = 0
		action.clickLinkText(driver,"Input");
		action.clickPlus(driver);
		//action.clickByClass(driver, "aui-icon-plus");

		//vnf fields
		action.sendKey(driver, vnfDescName, name);
		action.sendKey(driver, vnfDescDescription, "autotest");
		action.sendKey(driver, vnfDescVersion, "1.0");
		if(v != null) action.sendKey(driver, vnfDescVendor, v);
		if(f != null) action.sendKey(driver, vnfFlavor, f);
		action.selectItem(driver, groupVDU, 0); //0=default
		if(mgt != null) action.sendKey(driver, mgmtInterface, mgt);
		if(ext != null) action.sendKey(driver, vnfExtConnPt, ext);
		
		
		//execute
		action.clickExecute(driver);
		action.clickClose(driver);
		}
		
	}

	
}
