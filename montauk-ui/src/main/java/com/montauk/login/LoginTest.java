/***********************************************************************

Copyright (c) 2014 CA.  All rights reserved.

This software and all information contained therein is confidential and
proprietary and shall not be duplicated, used, disclosed or disseminated
in any way except as authorized by the applicable license agreement,
without the express written permission of CA ("CA"). All authorized
reproductions must be marked with this language.

EXCEPT AS SET FORTH IN THE APPLICABLE LICENSE AGREEMENT, TO THE EXTENT
PERMITTED BY APPLICABLE LAW, CA PROVIDES THIS SOFTWARE WITHOUT WARRANTY
OF ANY KIND, INCLUDING WITHOUT LIMITATION, ANY IMPLIED WARRANTIES OF
MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE.  IN NO EVENT WILL
CA BE LIABLE TO THE END USER OR ANY THIRD PARTY FOR ANY LOSS OR DAMAGE,
DIRECT OR INDIRECT, FROM THE USE OF THIS SOFTWARE, INCLUDING WITHOUT
LIMITATION, LOST PROFITS, BUSINESS INTERRUPTION, GOODWILL, OR LOST DATA,
EVEN IF CA IS EXPRESSLY ADVISED OF SUCH LOSS OR DAMAGE.

***********************************************************************/

package com.montauk.login;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import com.montauk.generic.Generic;
import com.montauk.generic.Mytools;

/**
 * This class holds the Login Functionality.
 * @author Krishnendu.Daripa
 */
//@Listeners({ ATUReportsListener.class, ConfigurationListener.class, MethodListener.class })
public class LoginTest 
{
	
	Properties obj = new Properties();
	Properties cred = new Properties();
	Properties linuxobj = new Properties();
	private String sUrl;
	
	//private String sAutoLoginTool = "C:\\Work\\AutoLogin.exe";
	//private Process sAutoLoginProcess;
	{
		System.setProperty("atu.reporter.config", System.getProperty("user.dir") + "\\src\\main\\java\\ca\\montauk\\objectrepo\\atu.properties");
		FileInputStream objfile;
		try 
		{
			objfile = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\ca\\montauk\\objectrepo\\objectuimap.properties");
			FileInputStream credfile = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\ca\\montauk\\objectrepo\\credentials.properties");
			FileInputStream linuxfile = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\ca\\montauk\\objectrepo\\linuxmap.properties");
			obj.load(objfile);
			cred.load(credfile);
			linuxobj.load(linuxfile);
			if (System.getProperty("Host") == null || System.getProperty("Host") == "" || System.getProperty("Host") == "${Host}")
			{
				
				sUrl = obj.getProperty("MontaukUIUrl").replace("host", cred.getProperty("serverid")).replace("port", cred.getProperty("portno"));
			}
			else
			{
				sUrl = obj.getProperty("MontaukUIUrl").replace("host", System.getProperty("Host")).replace("port", System.getProperty("Port"));
			}

		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	//private String sUrl ="http://kotpa04-i126579:8080/Montauk/index.html";
		public static WebDriver oBrowser;
	/**
	 * This function will execute before each Test tag in testing.xml
	 * @param browser
	 * @throws Exception
	 */
	@BeforeSuite
	public void AUT_Suite_Init()
	{
		try
		{
			Generic.setIndexPageDescription();		
		}
		catch(Throwable e)
		{
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	@BeforeTest
	@Parameters("browser")
	public void setup(String browser) throws Exception
	{
		try
		{
			System.out.println(" \n");
			System.out.println("Execution started on the Browser: " +browser);
			Generic.LaunchBrowser(browser, sUrl); 
			
			ATUReports.setWebDriver(oBrowser);
			//ATUReports.add("INfo Step: Browsed the URL: " + sUrl, LogAs.INFO,new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
		catch(Throwable e)
		{
			e.printStackTrace();
			Assert.fail(e.getMessage());
			//ATUReports.add("Fail Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.FAILED, new CaptureScreen(
			//		ScreenshotOf.DESKTOP));
		}
	}
	//@BeforeClass	
/*	public void AUT_Init()
		{
			try
			{
			}
			catch(Throwable e)
			{
				e.printStackTrace();
				Assert.fail(e.getMessage());
			}
		}
*/		//--------------------------------------
	/*	private void AutoLogin_Start()
		{
			try
			{
				String sCommand;
				sCommand = String.format("%s %s %s", sAutoLoginTool, "DARKR02", "Snoopy123");
				sAutoLoginProcess = Runtime.getRuntime().exec(sCommand);
			}
			catch(Throwable e)
			{
				e.printStackTrace();
				Assert.fail(e.getMessage());
			}
		}*/
	
	
	
		//--------------------------------------Logging the Page Information---------------------------------------
		@Test(priority=1)
		public void Log_PageInfo()
		{
			try
			{
				Generic.setAuthorInfoForReports();
				System.out.println("Title = " + oBrowser.getTitle());
				System.out.println("URL = " + oBrowser.getCurrentUrl());
				ATUReports.add("Title = " + oBrowser.getTitle() + "URL = " + oBrowser.getCurrentUrl(),LogAs.PASSED, new CaptureScreen(
						ScreenshotOf.DESKTOP));
				ATUReports.add("Pass Test Case: " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.PASSED, new CaptureScreen(
						ScreenshotOf.DESKTOP));
			}
			catch(Throwable e)
			{
				e.printStackTrace();
				Assert.fail(e.getMessage());
				ATUReports.add("Fail Test Case: " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.FAILED, new CaptureScreen(
						ScreenshotOf.DESKTOP));
			}
		}
		//------------------------------Login To the Montauk UI application----------------------------------------
		@Test(priority=2)
		public void LoginToUI()
		{
			try
			{
				Boolean bLogin;
				Generic.setAuthorInfoForReports();
				//Generic.setValue("//*[@name='bdmServer']","kotpa04-i26579");
				//Generic.setValue("//*[@name='bdmPort']","8080");
				//Generic.CheckForElementVisibility("//*[@name='bdmServer']");
				bLogin = Generic.loginToMontauk();
				Mytools.WaitFor(3L);
				if (bLogin == true)
				{
					ATUReports.add("Pass Test Case: " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));
				}
				else
				{
					ATUReports.add("Fail Test Case: " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
				}
			}
			catch(Throwable e)
			{
				e.printStackTrace();
				Assert.fail(e.getMessage());
				ATUReports.add("Fail Test Case: " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
			}
		}
		//--------------------------------Quitting the Browser object-----------------------------------------
		//@AfterTest
		public void AUT_End()
		{
			try
			{
				oBrowser.quit();
				//AutoLogin_End();
			}
			catch(Throwable e)
			{
				e.printStackTrace();
				Assert.fail(e.getMessage());
			}
		}

		//--------------------------------------
		/*private void AutoLogin_End()
		{
			try
			{
				sAutoLoginProcess.destroy();
				sAutoLoginProcess = null;
			}
			catch(Throwable e)
			{
				e.printStackTrace();
				Assert.fail(e.getMessage());
			}
		}*/
}
