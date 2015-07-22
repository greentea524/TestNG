package TestNG;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class NetworkDescriptor {

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
	cpType = "//*[contains(@id, 'BeanconnectionType')]"
	;
	
	ActionItem action = new ActionItem();
	
	NetworkDescriptor(WebDriver driver, String url){
		driver.get(url);
		this.driver = driver;
	}
	public void descriptor(){
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		element = driver.findElement(By.xpath(vnfdPortlet));
		element = driver.findElement(By.xpath(nsdPortlet));
		System.out.println(driver.getTitle());
	}
	public void createTestVNF(){
    	System.out.println("createVNF");
        action.clickMenu(driver,vnfdPortlet,newMenu);
        //general details
        fillVNF("automation","test","no","1.1","redcell","1.2");
        //vnf virtual link
        action.clickLinkText("VNF Virtual Link");
        fillVNFVL("virtual55", "automation");
        //vdu
        action.clickLinkText("VDU");
        fillVDU("vdu55","automation","1","image","2","2","20");
        //deployment flavor
        action.clickLinkText("Deployment Flavor");
        fillDeploymentFlavor("platinum", "test flavor", "platinum");
        //connection point
		action.clickLinkText("Connection Point");
		fillConnectionPoint("Ethernet", "test cp", "OTHER");
    }
	public void createNSD(){
		System.out.println("create network descriptor");
    	System.out.println(nsdPortlet);
        action.clickMenu(driver,nsdPortlet,newMenu);
	}
	public void fillVNF(){
		action.sendKey(name, "name" + Calendar.SECOND);
		action.sendKey(description, "automation");
		action.clickElement(driver,enable);
		action.sendKey(version, "1.0");
		action.sendKey(vendor, "dorado");
		action.sendKey(descVersion, "1.0");
	}
	//requirements if we want VNF enabled: The Virtual Network Function Descriptor entity is invalid due to the following errors:
    //This VNFD is enabled and requires VNF Virtual Links, Connection Points, VDUs, and VNF Deployment Flavors
	// parameters: name, description, enable, version, vendor, descriptor version
	public void fillVNF(String n, String d, String e, String v, String ven, String dVer){
		System.out.println(n+d);
		action.sendKey(name, n + Calendar.SECOND);
		action.sendKey(description, d);
		if(e.contains("yes"))
		action.clickElement(driver,enable);
		action.sendKey(version, v);
		action.sendKey(vendor, ven);
		action.sendKey(descVersion, dVer);
		action.clickApply(driver);
	}
	//vnf virtual link
	public void fillVNFVL(String n, String d){
		System.out.println(n+d);
		action.clickAdd(driver);
		action.sendKey(name, n);
		action.sendKey(description, d);
		action.clickApply(driver);
	}
	public void fillVDU(String n, String d, String o, String vm, String cpu, String mem, String disk){
		System.out.println(n+d);
		action.clickAdd(driver);
		action.sendKey(name, n);
		action.sendKey(description, d);
		action.sendKey(ordinal, o);
		action.sendKey(vmImage, vm);
		action.sendKey(cpuReq, cpu);
		action.sendKey(memReq, mem);
		action.sendKey(disReq, disk);
		action.clickApply(driver);
	}
	public void fillDeploymentFlavor(String n, String d, String f){
		action.clickAdd(driver);
		action.sendKey(name, n);
		action.sendKey(description, d);
		action.sendKey(flavor, f);
		action.clickApply(driver);
	}
	public void fillConnectionPoint(String n, String d, String t){
		action.clickAdd(driver);
		action.sendKey(name, n);
		action.sendKey(description, d);
		action.clickElement(driver, cpType);
		action.selectVisible(driver, cpType, t);
		action.clickApply(driver);
	}
}
