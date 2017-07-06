package com.montauk.generic;

//import java.io.File;








//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.By;
//import org.openqa.selenium.Proxy;
//import org.openqa.selenium.firefox.FirefoxProfile;
//import org.openqa.selenium.ie.InternetExplorerDriver;
//import org.openqa.selenium.remote.CapabilityType;
//import org.openqa.selenium.remote.DesiredCapabilities;
//import org.testng.Assert;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
//import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;//import java.io.File;



















import atu.testng.reports.ATUReports;
import atu.testng.reports.utils.Utils;
import com.montauk.login.LoginTest;
import com.montauk.reports.Reports;

public class Generic 
{
	
	public static boolean Visibility = false;
	static WebElement oResult;
	static boolean bResult;
	static Properties obj = new Properties();
	static Properties cred = new Properties();

	//-----------------------------------------------Code for dynamic allocation of Array element----------------------------------------
	@SuppressWarnings({ "unused", "rawtypes" })
	private static Object resizeArray (Object oldArray, int newSize) 
	{
 	   int oldSize = java.lang.reflect.Array.getLength(oldArray);
 	   Class elementType = oldArray.getClass().getComponentType();
 	   Object newArray = java.lang.reflect.Array.newInstance(
 	         elementType, newSize);
 	   int preserveLength = Math.min(oldSize, newSize);
 	   if (preserveLength > 0)
 	      System.arraycopy(oldArray, 0, newArray, 0, preserveLength);
 	   return newArray; 
 	  }

	
	//------------------------------------------------Code for Logging into Montauk UI----------------------------------------
	public static Boolean loginToMontauk()
	{
		try
		{
			FileInputStream objfile = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\ca\\montauk\\objectrepo\\objectuimap.properties");
			FileInputStream credfile = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\ca\\montauk\\objectrepo\\credentials.properties");
			obj.load(objfile);
			cred.load(credfile);
			int nCounter = 0;
			do
			{
				if (Generic.CheckForElementVisibility(obj.getProperty("tenant")) == true && Generic.CheckForElementVisibility(obj.getProperty("login")) == true )
				{
					break; 
				}
				else
				{
					nCounter++;
					System.out.println("Page not displayed");
					LoginTest.oBrowser.navigate().refresh();
					Mytools.WaitFor(1L);
				}
		    }while( nCounter < 20 );
            //----------------------Enter Values for logging in to Montauk---------------------------
            //Generic.setValue(obj.getProperty("bdmserver"),cred.getProperty("serverid"));
            //Generic.setValue(obj.getProperty("port"),cred.getProperty("portno"));
            Generic.setValue(obj.getProperty("tenant"),cred.getProperty("tenant"));
			if (System.getProperty("UserName") == null || System.getProperty("UserName") == "" || System.getProperty("UserName") == "${UserName}")
			{
	            Generic.setValue(obj.getProperty("username"),cred.getProperty("username"));
			}
			else
			{
	            Generic.setValue(obj.getProperty("username"),System.getProperty("UserName"));
			}

			if (System.getProperty("Password") == null || System.getProperty("Password") == "" || System.getProperty("Password") == "${Password}")
			{
	            Generic.setValue(obj.getProperty("password"),cred.getProperty("password"));
			}
			else
			{
	            Generic.setValue(obj.getProperty("password"),System.getProperty("Password"));
			}
            //----------------------Clicking on the Submit Button---------------------------
            //----------------------Clicking on the OK Button---------------------------
            Mytools.WaitFor(2L);
            Generic.CheckForElementVisibility(obj.getProperty("login"));
            Generic.clickElement(obj.getProperty("login"));
            Generic.waitUntilElementVisible(obj.getProperty("monitortablink"));
            LoginTest.oBrowser.navigate().refresh();
			Mytools.WaitFor(3L);
            return true;
		}
		catch(Throwable e)
		{
			e.printStackTrace();
			Assert.fail(e.getMessage());
			return false;
		}
	}
	//------------------------------------------------Code for Setting Value to any object----------------------------------------
	public static void setValue(String sXpath, String sValue)
	{
		try
		{
			
			LoginTest.oBrowser.findElement(By.xpath(sXpath)).clear();
			LoginTest.oBrowser.findElement(By.xpath(sXpath)).sendKeys(sValue);
		}
		catch(Throwable e)
		{
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	  
	//------------------------------------------------Code for Setting Value to any object----------------------------------------
	public static void clickElement(String sXpath)
	{
		try
		{
			//WebElement oEdit;
			WebDriverWait oWait;
			By oBy;
			Mytools.WaitFor(2L);
			oWait = new WebDriverWait(LoginTest.oBrowser, 60);
			oBy = By.xpath(sXpath);
			oWait.until(ExpectedConditions.visibilityOfElementLocated(oBy));
			//LoginTest oDriver = new LoginTest();
			if(LoginTest.oBrowser.findElement(By.xpath(sXpath)).isEnabled())
			{
				LoginTest.oBrowser.findElement(By.xpath(sXpath)).click();
			}
		}
		catch(Throwable e)
		{
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	//------------------------------------------------
	public static void waitUntilElementVisible(String sXpath)
	{
		try
		{
			//WebElement oEdit;
			WebDriverWait oWait;
			By oBy;
			oWait = new WebDriverWait(LoginTest.oBrowser, 60);
			oBy = By.xpath(sXpath);
			oWait.until(ExpectedConditions.visibilityOfElementLocated(oBy));
		}
		catch(Throwable e)
		{
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	//------------------------------------------------Parsing Color for Data to colorcode------------------------------------------

	public static int ParseColorData(final String sColor) 
	{
		int nColorCode = 0;
          try
          {
        	  //--------------------SEVERITY COLOR CODE-------------------
        	  switch (sColor) {
        	  //--------------------CLEAR-------------------
              case "rgb(20, 170, 19)":  nColorCode = 1;
                   break;
             	  //--------------------Info-------------------
              case "rgb(0, 100, 175)":  nColorCode = 2;
                   break;
             	  //--------------------WARNING-------------------
              case "rgb(120, 29, 126)":  nColorCode = 3;
	              break;
	        	  //--------------------MINOR-------------------
              case "rgb(242, 182, 0)":  nColorCode = 4;
	              break;
	        	  //--------------------MAJOR-------------------
              case "rgb(233, 131, 0)":  nColorCode = 5;
	              break;
	        	  //--------------------CRITICAL-------------------
              case "rgb(205, 0, 60)":  nColorCode = 6;
	              break;
	        	  //--------------------UNKNOWN-------------------
              case "rgb(48, 57, 61)":  nColorCode = 7;
	              break;
          }
        	  return nColorCode;
                 
          }
          catch(Throwable e)
          {
        	  
        	  	e.printStackTrace();
  				Assert.fail(e.getMessage());
  				return nColorCode;
          }
       }
	
	//------------------------------------------------

	public static boolean CheckForElementVisibility(String xpath) {
	              // 
	              
	              try
	              {
	            	  
            		  Visibility = false;
	            	  if (LoginTest.oBrowser.findElement(By.xpath(xpath)).isDisplayed() && LoginTest.oBrowser.findElement(By.xpath(xpath)).isEnabled())
	            	  {
	            		  Visibility = true;
	            	  }
	            	  return Visibility;
	                     
	              }
	              catch(Throwable e)
	              {
	                     Visibility = false;
	                     return Visibility;
	              }
	       }

	//------------------------------------------------
	public static void setAuthorInfoForReports() 
	{
		ATUReports.setAuthorInfo("Krishnendu", Utils.getCurrentTime(), "1.0");
        //ATUReports.setAuthorInfo("Automation Tester", Utils.getCurrentTime(),"1.0");
	}
	//------------------------------------------------

	public static void setIndexPageDescription() 
	{
		ATUReports.indexPageDescription = "Big Data Management <br/> <b>Montauk UI Test</b>";
	}

	//------------------------------------------------
	public static void LaunchBrowser(String browser, String sUrl)
	{
		try
		{
	//Check if parameter passed from TestNG is 'firefox'
			if(browser.equalsIgnoreCase("firefox"))
			{
			//create firefox instance
				LoginTest.oBrowser = new FirefoxDriver(Mytools.getProxyCapability("ff"));
			}
			//Check if parameter passed as 'chrome'
			else if(browser.equalsIgnoreCase("chrome"))
			{
				//set path to chromedriver.exe
				System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + "\\src\\main\\resources\\lib\\chromedriver.exe");
				//create chrome instance
				LoginTest.oBrowser = new ChromeDriver(Mytools.getProxyCapability("chrome"));
			}
			//Check if parameter passed as 'IE'
			else if(browser.equalsIgnoreCase("ie"))
			{
				//set path to IE.exe
				System.setProperty("webdriver.ie.driver",System.getProperty("user.dir") + "\\src\\main\\resources\\lib\\IEDriverServer.exe");
				//create IE instance
				LoginTest.oBrowser = new InternetExplorerDriver(Mytools.getProxyCapability("ie"));
			}
			else
			{
				//If no browser passed throw exception
				throw new Exception("Browser is not correct");
			}
			LoginTest.oBrowser.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
			LoginTest.oBrowser.manage().window().maximize();
			LoginTest.oBrowser.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
			LoginTest.oBrowser.manage().deleteAllCookies();
			LoginTest.oBrowser.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
			LoginTest.oBrowser.get(sUrl);
			Mytools.WaitFor(3L);
			Reports.LogResult("-----------------------------------------------------------------------");
			Reports.LogResult("Started Execution with Browser: " + browser);
			Reports.LogResult("-----------------------------------------------------------------------");
			//Mytools.WaitFor(10L);
            
     }
		catch(Throwable e)
		{
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
}
	//------------------------------------------------
	//------------------------------------------------Parsing Color for Data to colorcode------------------------------------------

	public static int ParseStatus(final String sStatus) 
	{
		int nColorCode = 0;
          try
          {
        	  //--------------------SEVERITY COLOR CODE-------------------
        	  switch (sStatus) {
        	  //--------------------CLEAR-------------------
              case "CLEAR":  nColorCode = 1;
                   break;
             	  //--------------------Info-------------------
              case "INFO":  nColorCode = 2;
                   break;
             	  //--------------------WARNING-------------------
              case "WARNING":  nColorCode = 3;
	              break;
	        	  //--------------------MINOR-------------------
              case "MINOR":  nColorCode = 4;
	              break;
	        	  //--------------------MAJOR-------------------
              case "MAJOR":  nColorCode = 5;
	              break;
	        	  //--------------------CRITICAL-------------------
              case "CRITICAL":  nColorCode = 6;
	              break;
	        	  //--------------------UNKNOWN-------------------
              case "UNKNOWN":  nColorCode = 7;
	              break;
          }
        	  return nColorCode;
                 
          }
          catch(Throwable e)
          {
        	  
        	  	e.printStackTrace();
  				Assert.fail(e.getMessage());
  				return nColorCode;
          }
       }
	//------------------------------------------------
	//------------------------------------------------
	
//	REPLACES THE TEXT IN THE CURL COMMAND PASSED AS STRING WITH APPROPRIATE VARIABLE VALUES SET IN THE PROPERTIES FILE
	public static String CurlCommandReplacer(String curlCommand){
		
		Properties linux = new Properties();
		FileInputStream linuxfile;
		try {
			linuxfile = new FileInputStream(System.getProperty("user.dir")
							+ "\\src\\main\\java\\ca\\montauk\\objectrepo\\credentials.properties");
			linux.load(linuxfile);
			if (System.getProperty("Host") == null ||System.getProperty("Host") == "")
			{
				curlCommand = curlCommand.replace("host", linux.getProperty("serverid")).replace("port", linux.getProperty("portno"));
			}
			else
			{
				curlCommand = curlCommand.replace("host", System.getProperty("Host")).replace("port", System.getProperty("Port"));
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return curlCommand;
	}
	
}
