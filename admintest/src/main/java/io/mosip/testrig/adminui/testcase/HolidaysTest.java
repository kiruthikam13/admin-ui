package io.mosip.testrig.adminui.testcase;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
// Generated by Selenium IDE
//import org.junit.Test;
//import org.junit.Before;
//import org.junit.After;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import io.mosip.testrig.adminui.kernel.util.ConfigManager;
import io.mosip.testrig.adminui.utility.BaseClass;
import io.mosip.testrig.adminui.utility.Commons;
import io.mosip.testrig.adminui.utility.JsonUtil;
import io.mosip.testrig.adminui.utility.PropertiesUtil;

public class HolidaysTest extends BaseClass{
 
  @Test(groups = "H")
  public void holidaysCRUD() throws Exception {
	  test=extent.createTest("HolidaysTest", "verify Login");
	String listofholidays="admin/masterdata/holiday/view";
	test=extent.createTest("HolidaysTest", "verify Login");
	String holidayDate=ConfigManager.getholidayDate();
    Commons.click(test,driver,By.xpath("//a[@href='#/admin/masterdata']"));
   
    Commons.click(test,driver,By.id(listofholidays));
    Commons.click(test,driver,By.id("Create"));
    test.log(Status.INFO, "Click on Create");
    
  
    Commons.enter(test,driver,By.id("holidayName"),data);
    Commons.enter(test,driver,By.id("holidayDesc"),data);
  //  Commons.enter(test,driver,By.id("holidayDate"),holidayDate);
   Commons.calendar(holidayDate);
    test.log(Status.INFO, "Click on Enters HolidayDate");
    Commons.dropdown(test,driver,By.id("locationCode"));
        
    
    Commons.create(test,driver);
	Commons.filter(test,driver, By.id("holidayName"), data);
	

	Commons.edit(test,driver,data+1,By.id("holidayName"));
	test.log(Status.INFO, "Click on edit");
	Commons.filter(test,driver, By.id("holidayName"), data+1);
	
	Commons.activate(test,driver);
	test.log(Status.INFO, "Click on Active");
	Commons.edit(test,driver,data+2,By.id("holidayName"));
	Commons.filter(test,driver, By.id("holidayName"), data+2);
	Commons.deactivate(test,driver);
	test.log(Status.INFO, "Click on Deactive");

    }
}
