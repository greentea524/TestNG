package TestNG;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class VNFDescriptor {

	private WebDriver driver;
    WebElement element;
    private int 
    editItem = 2,
    deleteItem = 4;
    //web element xpath
	private String 
	newMenu = "//*[contains(@id,'_newNfvId')]",
	vnfdPortlet = "//*[contains(@id,'NFVVnfDescriptorForm:ApmsttNFVVnfDescriptor')]",
	name = "//*[contains(@id,'Bean_name')]",
	description = "//*[contains(@id,'Bean_description')]",
	enable = "//*[contains(@id,'Bean_enabled')]",
	version = "//*[contains(@id,'Bean_version')]",
	vendor = "//*[contains(@id,'Bean_vendor')]",
	descVersion = "//*[contains(@id,'Bean_descriptorVersion')]",
	ordinal = "//*[contains(@id, 'Bean_ordinal')]",
	vmImage = "//*[contains(@id, 'Bean_image')]",
	cpuReq = "//*[contains(@id, 'Bean_cpuRequired')]",
	memReq = "//*[contains(@id, 'Bean_memoryRequired')]",
	disReq = "//*[contains(@id, 'Bean_diskRequired')]",
	flavor = "//*[contains(@id, 'Bean_flavorKey')]",
	cpType = "//*[contains(@id, 'Bean_connectionType')]",
	bandwidthReq = "//*[contains(@id, 'Bean_bandwidthRequired')]",
	cpAddSource = "//*[contains(@id, 'Bean_vnfAddressSource')]",
	dataType = "//*[contains(@id, 'Bean_dataType')]",
	stringValue = "//*[contains(@id, 'Bean_stringValue')]",
	
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
	}
	public void editAction(String target){
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		System.out.println("search action: "+target);
		action.clickElement(driver, vnfsearchbar);
		action.sendKeyandEnter(driver, vnfsearchtext, target);
		action.execute(driver, row1, editItem);
	}
	public void deleteAction(String target){
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		System.out.println("search action: "+target);
		action.clickElement(driver, vnfsearchbar);
		action.sendKeyandEnter(driver, vnfsearchtext, target);
		action.execute(driver, row1, deleteItem);
		action.clickDelete(driver);
		
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
	//requirements if we want VNF enabled: The Virtual Network Function Descriptor entity is invalid due to the following errors:
    //This VNFD is enabled and requires VNF Virtual Links, Connection Points, VDUs, and VNF Deployment Flavors
	public void createTestVNF(String name){
    	System.out.println("createVNF");
        action.clickMenu(driver,vnfdPortlet,newMenu);
        //general details
        fillVNF(name,"test","1.1","redcell","1.2",null); 
        //vnf virtual link
        action.clickLinkText(driver,"VNF Virtual Link");
        fillVNFVL("virtual55", "automation");
        //vdu
        action.clickLinkText(driver,"VDU");
        fillVDU("vdu55","automation","1","image","2","2","20","1000");
        //deployment flavor
        action.clickLinkText(driver,"Deployment Flavor");
        fillDeploymentFlavor("platinum", "test flavor", "platinum");
        //connection point
		action.clickLinkText(driver,"Connection Point");
		fillConnectionPoint("Ethernet", "test cp", "OTHER","sure");
		action.clickLinkText(driver,"Connection Point");
		fillConnectionPoint("Ethernet2", "test cp", "OTHER","sure");
		//data values
		action.clickLinkText(driver, "Data Values");
		fillDataValue("data13513","test","string","test");
		//save
		action.clickSave(driver);
    }
	// parameters: name, description, version, vendor, descriptor version, enable
	public void fillVNF(String n, String d, String v, String ven, String dVer, String e){
		if(n != null) action.sendKey(driver,name, n);
		if(d != null) action.sendKey(driver,description, d);
		if((e != null)) action.clickElement(driver,enable);
		action.clickLinkText(driver, "Version Info");
		if(v != null) action.sendKey(driver,version, v);
		if(ven != null) action.sendKey(driver,vendor, ven);
		if(dVer != null) action.sendKey(driver,descVersion, dVer);
		action.clickApply(driver);
	}
	//vnf virtual link
	//parameters: name and description TODO add extended details
	public void fillVNFVL(String n, String d){
		action.clickAdd(driver);
		if(n != null) action.sendKey(driver, name, n);
		if(d != null) action.sendKey(driver, description, d);
		action.clickApply(driver);
	}
	//name, description, ordinal, vmImage name, cpu req, mem req, disk req, bandwidth req
	public void fillVDU(String n, String d, String o, String vm, String cpu, String mem, String disk, String bw){
		action.clickAdd(driver);
		if(n != null) action.sendKey(driver, name, n);
		if(d != null) action.sendKey(driver, description, d);
		if(o != null) action.sendKey(driver, ordinal, o);
		if(vm != null) action.sendKey(driver, vmImage, vm);
		if(cpu != null) action.sendKey(driver, cpuReq, cpu);
		if(mem != null) action.sendKey(driver, memReq, mem);
		if(disk != null) action.sendKey(driver, disReq, disk);
		if(bw != null) action.sendKey(driver, bandwidthReq, bw);
		
		action.clickApply(driver);
	}
	//name, description, flavor
	public void fillDeploymentFlavor(String n, String d, String f){
		action.clickAdd(driver);
		if(n != null) action.sendKey(driver, name, n);
		if(d != null) action.sendKey(driver, description, d);
		if(f != null) action.sendKey(driver, flavor, f);
		action.clickApply(driver);
	}
	//parameter: name, description, connection point type, add source checkbox
	public void fillConnectionPoint(String n, String d, String t, String as){
		action.clickAdd(driver);
		if(n != null) action.sendKey(driver, name, n);
		if(d != null) action.sendKey(driver, description, d);
		if(t != null) 
		{
			action.clickElement(driver, cpType);
			action.selectVisible(driver, cpType, t);
		}
		if((as != null)) action.clickElement(driver, cpAddSource);
		action.clickApply(driver);
	}
	//parameter: name, description, data type, value
	public void fillDataValue(String n, String d, String t, String v){
		action.clickAdd(driver);
		if(n != null) action.sendKey(driver, name, n);
		if(d != null) action.sendKey(driver, description, d);
		if(t != null) 
		{
			action.clickElement(driver, dataType);
			action.selectVisible(driver, dataType, t);
		}
		if(v != null) action.sendKey(driver, stringValue, v);
		action.clickApply(driver);
		
	}
}
