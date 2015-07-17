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
	disReq = "//*[contains(@id, 'BeandiskRequired')]"
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
        
        fillVNF("automation","test","no","1.1","redcell","1.2");
        action.clickLinkText("VNF Virtual Link");
        fillVNFVL("virtual55", "automation");
        action.clickLinkText("VDU");
        fillVDU("vdu55","automation","1","image","2","2","20");

		
    }
	public void createNSD(){
		System.out.println("createNSD");
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
		action.clickApply();
	}
	//vnf virtual link
	public void fillVNFVL(String n, String d){
		System.out.println(n+d);
		action.clickAdd();
		action.sendKey(name, n);
		action.sendKey(description, d);
		action.clickApply();
	}
	public void fillVDU(String n, String d, String o, String vm, String cpu, String mem, String disk){
		System.out.println(n+d);
		action.clickAdd();
		action.sendKey(name, n);
		action.sendKey(description, d);
		action.sendKey(ordinal, o);
		action.sendKey(vmImage, vm);
		action.sendKey(cpuReq, cpu);
		action.sendKey(memReq, mem);
		action.sendKey(disReq, disk);
		action.clickApply();
	}
}