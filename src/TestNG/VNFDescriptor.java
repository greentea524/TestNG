package TestNG;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class VNFDescriptor {

	private WebDriver driver;
    WebElement element;
	private String 
	newMenu = "//*[contains(@id,'_newNfvId')]",
	nsdPortlet = "//*[contains(@id,'NFVNetworkServiceDescriptorForm:ApmsttNFVNetworkServiceDescriptor')]",
	vnfdPortlet = "//*[contains(@id,'NFVVnfDescriptorForm:ApmsttNFVVnfDescriptor')]",
	name = "//*[contains(@id,'Beanname')]",
	description = "//*[contains(@id,'Beandescription')]",
	enable = "//*[contains(@id,'Beanenabled')]",
	version = "//*[contains(@id,'Beanversion')]",
	vendor = "//*[contains(@id,'Beanvendor')]",
	descVersion = "//*[contains(@id,'BeandescriptorVersion')]",
	ordinal = "//*[contains(@id, 'Beanordinal')]",
	vmImage = "//*[contains(@id, 'Beanimage')]",
	cpuReq = "//*[contains(@id, 'BeancpuRequired')]",
	memReq = "//*[contains(@id, 'BeanmemoryRequired')]",
	disReq = "//*[contains(@id, 'BeandiskRequired')]",
	flavor = "//*[contains(@id, 'BeanflavorKey')]",
	cpType = "//*[contains(@id, 'BeanconnectionType')]",
	row1 = "iceDatTblRow1", //first row item of a portlet or table
	vnfsearchtext = "//*[contains(@id,'pmeNFVVnfDescriptorsrchTxt')]",
	vnfsearchbar = "//*[contains(@id,'pmeNFVVnfDescriptorSL')]" //vnf portlet search
	;
	
	ActionItem action = new ActionItem();
	
	VNFDescriptor(WebDriver driver, String url){
		this.driver = driver;
		driver.get(url);
	}
	public void descriptor(){
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		element = driver.findElement(By.xpath(vnfdPortlet));
		element = driver.findElement(By.xpath(nsdPortlet));
		System.out.println(driver.getTitle());
	}
	public void searchAction(String target, Integer menuItem){
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		System.out.println("search action: "+target);
		action.clickElement(driver, vnfsearchbar);
		action.sendKeyandEnter(driver, vnfsearchtext, target);
		action.execute(driver, row1, menuItem);
	}
	//editVNF parameters: name, description, enable, version, vendor, descriptor version
	public void editVNF(String n, String d, String e, String v, String ven, String dVer){
		System.out.println("editing "+n);
		action.clickEdit(driver);
		//null = don't edit
		//fillVNF parameters: name, description, enable, version, vendor, descriptor version
		fillVNF(null,"edit test",null,"1.1",null,"1.0");
		
		action.clickApply(driver);
	}
	public void createTestVNF(){
    	System.out.println("createVNF");
        action.clickMenu(driver,vnfdPortlet,newMenu);
        //general details
        fillVNF("automation","test","no","1.1","redcell","1.2");
        //vnf virtual link
        action.clickLinkText(driver,"VNF Virtual Link");
        fillVNFVL("virtual55", "automation");
        //vdu
        action.clickLinkText(driver,"VDU");
        fillVDU("vdu55","automation","1","image","2","2","20");
        //deployment flavor
        action.clickLinkText(driver,"Deployment Flavor");
        fillDeploymentFlavor("platinum", "test flavor", "platinum");
        //connection point
		action.clickLinkText(driver,"Connection Point");
		fillConnectionPoint("Ethernet", "test cp", "OTHER");
    }
	public void createNSD(){
		System.out.println("create network descriptor");
    	System.out.println(nsdPortlet);
        action.clickMenu(driver,nsdPortlet,newMenu);
	}
	public void fillVNF(){
		action.sendKey(driver,name, "name" + Calendar.SECOND);
		action.sendKey(driver,description, "automation");
		action.clickElement(driver,enable);
		action.sendKey(driver,version, "1.0");
		action.sendKey(driver,vendor, "dorado");
		action.sendKey(driver,descVersion, "1.0");
	}
	//requirements if we want VNF enabled: The Virtual Network Function Descriptor entity is invalid due to the following errors:
    //This VNFD is enabled and requires VNF Virtual Links, Connection Points, VDUs, and VNF Deployment Flavors
	// parameters: name, description, enable, version, vendor, descriptor version
	public void fillVNF(String n, String d, String e, String v, String ven, String dVer){
		if(n != null) action.sendKey(driver,name, n + Calendar.SECOND);
		if(d != null) action.sendKey(driver,description, d);
		if((e != null)) action.doubleClick(driver,enable);
		action.clickLinkText(driver, "Version Info");
		if(v != null) action.sendKey(driver,version, v);
		if(ven != null) action.sendKey(driver,vendor, ven);
		if(dVer != null) action.sendKey(driver,descVersion, dVer);
		action.clickApply(driver);
	}
	//vnf virtual link
	public void fillVNFVL(String n, String d){
		action.clickAdd(driver);
		if(n != null) action.sendKey(driver, name, n);
		if(d != null) action.sendKey(driver, description, d);
		action.clickApply(driver);
	}
	public void fillVDU(String n, String d, String o, String vm, String cpu, String mem, String disk){
		action.clickAdd(driver);
		if(n != null) action.sendKey(driver, name, n);
		if(d != null) action.sendKey(driver, description, d);
		if(o != null) action.sendKey(driver, ordinal, o);
		if(vm != null) action.sendKey(driver, vmImage, vm);
		if(cpu != null) action.sendKey(driver, cpuReq, cpu);
		if(mem != null) action.sendKey(driver, memReq, mem);
		if(disk != null) action.sendKey(driver, disReq, disk);
		action.clickApply(driver);
	}
	public void fillDeploymentFlavor(String n, String d, String f){
		action.clickAdd(driver);
		if(n != null) action.sendKey(driver, name, n);
		if(d != null) action.sendKey(driver, description, d);
		if(f != null) action.sendKey(driver, flavor, f);
		action.clickApply(driver);
	}
	public void fillConnectionPoint(String n, String d, String t){
		action.clickAdd(driver);
		if(n != null) action.sendKey(driver, name, n);
		if(d != null) action.sendKey(driver, description, d);
		if(t != null) 
		{
			action.clickElement(driver, cpType);
			action.selectVisible(driver, cpType, t);
		}
		action.clickApply(driver);
	}
}