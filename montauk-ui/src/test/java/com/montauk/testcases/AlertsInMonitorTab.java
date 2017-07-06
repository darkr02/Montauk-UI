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
package com.montauk.testcases;


import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import com.montauk.alerts.AlertFromUI;
import com.montauk.alerts.AlertVerify;
import com.montauk.backend.ReadingDataFromLinux;
import com.montauk.generic.Generic;
import com.montauk.generic.Mytools;
import com.montauk.login.LoginTest;
import com.montauk.reports.Reports;

/**
 * This class holds the Alerts Functionality in MonitorTab.
 * @author Krishnendu.Daripa
 */

public class AlertsInMonitorTab 
{
	
	private List<AlertFromUI> Alerts = new ArrayList<AlertFromUI>();
	Properties obj = new Properties();
	Properties linuxobj = new Properties();
	private String inputString;
	{
		System.setProperty("atu.reporter.config", System.getProperty("user.dir") + "\\src\\main\\java\\com\\montauk\\objectrepo\\atu.properties");
	}
	//--------------------------------------Verifying No. of Alerts present in Monitor Tab---------------------------------------
	@Test(priority=1)
	public void NoOfAlertsInMonitorTab() 
	{
	  try
		{
			FileInputStream objfile = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\com\\montauk\\objectrepo\\objectuimap.properties");
			FileInputStream linuxfile = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\com\\montauk\\objectrepo\\linuxmap.properties");
			obj.load(objfile);
			String SystemClusterDetailsCurl;
			linuxobj.load(linuxfile);
			LoginTest.oBrowser.navigate().refresh();
			Generic.setAuthorInfoForReports();
			Mytools.WaitForElementToBeLoaded(obj.getProperty("monitortablink"));
			Generic.clickElement(obj.getProperty("monitortablink"));
			Mytools.WaitForElementToBeLoaded(obj.getProperty("alertsinmonitortab"));
			SystemClusterDetailsCurl = Generic.CurlCommandReplacer(linuxobj.getProperty("alertsinMonitortab"));
//			System.out.println(SystemClusterDetailsCurl);
			inputString = ReadingDataFromLinux.ReadClusterData(SystemClusterDetailsCurl);
			List<WebElement> allFormChildElements = LoginTest.oBrowser.findElements(By.xpath(obj.getProperty("alertsinmonitortab")));
	     	System.out.println("Number of Aletrs is" + allFormChildElements.size() );
	     	ATUReports.add("Pass Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.PASSED, new CaptureScreen(
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
	//--------------------------------------Creating Alerts List with details for Verification from Linux Box---------------------------------------
	@Test(priority=2)
	public void CreatingAlertsInfo() 
	{
	 	 String errorMsg = "";
	 	 String sErrorString = "";
	         try
	         {	 
	        	 AlertFromUI alert = new AlertFromUI();
	        	 List<WebElement> allFormChildElements = LoginTest.oBrowser.findElements(By.xpath(obj.getProperty("alertsinmonitortab")));
	        	 int nAlerts = allFormChildElements.size();
	        	 if (nAlerts > 0)
	        	 {
		        	 for(int i=1; i <= 20; i++) 
		        	 {
		        		 alert = new AlertFromUI();
		        		 String child = "table[" + i + "]";
		        		 // Alert Name
		        		 WebElement oResult=null;
		        		 try
		        		 {
		        			 LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentalertpath")+ child +"/tbody")).click();
		        			 oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentalertpath") + child + obj.getProperty("alertsnamepath") ));
		        			 
		        			 //Reports.LogResult("Alert "+i+" Alert Name Exist     Test Passed");
		        			 System.out.println("AlertName : " + oResult.getText());
		            		 
		            		 alert.setAlertName(oResult.getText());
		        		 }
		        		 catch(Exception e)
		        		 {
		        			 System.out.println("Alert Name does not exist.");
		        			 //Reports.LogResult("Alert"+i+  " Alert Name does not exist.. Test Failed");
		        			 //blnFail=true;
		        			 errorMsg = "Alert Name does not exist.";
		        			 sErrorString = errorMsg; 
		        		 }
		        		 // AlertImage
		        		 //AlertType:Cloudera or Hortonworks
		        		 try
		        		 {
			        		String imageUrl=LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentalertpath") + child + obj.getProperty("alertsimagepath"))).getAttribute("src");  
//			        		System.out.println("Image source path : \n"+ imageUrl);  
			       		 
			        		String URL = imageUrl;
			        		
			        		 
			     		    
			     		    int startIndex = URL.lastIndexOf( '/' );
			     		    
			     		    int endIndex =URL.lastIndexOf( '-' );
			     		    
			     		    System.out.println("Alert Type: "+URL.substring(startIndex + 1, endIndex));
			     		    if (URL.substring(startIndex + 1, endIndex).equalsIgnoreCase("CA"))
			     		    {
				     		    alert.setAlertType("nimsoft");
			     		    }
			     		    else
			     		    {
				     		    alert.setAlertType(URL.substring(startIndex + 1, endIndex));
			     		    }
		        		 }
		        		 
		        		 catch(Exception e)
		        		 {
		        			 System.out.println("Alert Type does not exist.");
		        			 Reports.LogResult("Alert"+i+"Alert Type does not exist..Test Failed");
		        			 //blnFail=true;
		        			 errorMsg = "Alert Type does not exist.";
		        			 sErrorString = sErrorString + ";" + errorMsg;
		        		 }
		        		     
		     		 
		        		 
		        		 // Alert description
		        		 try
		        		 {
		        			 oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentalertpath") + child + obj.getProperty("alertsdescriptionpath") ));
		        			 //oResult.click();
		        			 Reports.LogResult("Alert"+i+"Alert Description Exist Test Passed");
		        			 System.out.println("AlertDescription : " + oResult.getText());
		            		 
		            		 alert.setDescription(oResult.getText().replace("Description:\n", ""));
		        		 }
		        		 catch(Exception e)
		        		 {
		        			 System.out.println("Alert Description does not exist.");
		        			 Reports.LogResult("Alert"+i+"Alert Description does not exist..Test Failed");
		        			 //blnFail=true;
		        			 errorMsg = "Alert Description does not exist.";
		        			 sErrorString = sErrorString + ";" + errorMsg;
		        		 }
		        		         		
		        	
		        		// Alert StartTime
		        		 try
		        		 {
		        			 oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentalertpath") + child + obj.getProperty("alertsdatepath") ));
		        			 Reports.LogResult("Alert"+i+"Alert StartTime Exist Test Passed");
		        			 System.out.println("AlertStartTime : " + oResult.getText());
		            		 
		            		 alert.setStarttime(oResult.getText().replace(",\n", " "));
		        		 }
		        		 catch(Exception e)
		        		 {
		        			 System.out.println("Alert StartTime does not exist.");
		        			 Reports.LogResult("Alert"+i+" Alert StartTime does not exist..Test Failed");
		        			 
		        			 //blnFail=true;
		        			 errorMsg = "Alert StartTime does not exist.";
		        			 sErrorString = sErrorString + ";" + errorMsg;
		        		 }
		        		// Alert Status
		        		 try
		        		 {
		        			 oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentalertpath") + child + obj.getProperty("alertStatuspath") ));
		        			 Reports.LogResult("Alert"+i+"Alert Status Exist Test Passed");
		        			 
		        			 System.out.println("AlertColor : " + oResult.getText());
		        			 Number nSeverity = Generic.ParseStatus(oResult.getText());
		        			 
		        			  //Split css value of rgb
		        			  //String[] numbers = text.replace("rgb(", "").replace(")", "").split(",");
		        			  /*int number1=Integer.parseInt(numbers[0]);
		        			  numbers[1] = numbers[1].trim();
		        			  int number2=Integer.parseInt(numbers[1]);
		        			  numbers[2] = numbers[2].trim();
		        			  int number3=Integer.parseInt(numbers[2]);
		        			  String hex = String.format("#%02x%02x%02x", number1,number2,number3);
		        			  System.out.println(hex);*/
		            		 
		            		 alert.setAlertSeverity(nSeverity);
		        		 }
		        		 catch(Exception e)
		        		 {
		        			 System.out.println("Alert Color does not exist.");
		        			 Reports.LogResult("Alert"+i+" Alert Color does not exist..Test Failed");
		        			
		        			// blnFail=true;
		        			 errorMsg = "Cluster Color does not exist.";
		        			 sErrorString = sErrorString + ";" + errorMsg;
		        		 }
	 
		        		 
		        		// Alert Color
	/*	        		 try
		        		 {
		        			 oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentalertpath") + child + obj.getProperty("alertscolorpath") ));
		        			 Reports.LogResult("Alert"+i+"Alert Color Exist Test Passed");
		        			 
		        			 System.out.println("AlertColor : " + oResult.getCssValue("background-Color"));
		        			 //System.out.println("AlertColor123 : " + oResult.getAttribute("css=element.style@background-color"));
		        			 Number nColor = Generic.ParseColorData(oResult.getCssValue("background-Color").toString());
		        			 
		        			  //Split css value of rgb
		        			  //String[] numbers = text.replace("rgb(", "").replace(")", "").split(",");
		        			  int number1=Integer.parseInt(numbers[0]);
		        			  numbers[1] = numbers[1].trim();
		        			  int number2=Integer.parseInt(numbers[1]);
		        			  numbers[2] = numbers[2].trim();
		        			  int number3=Integer.parseInt(numbers[2]);
		        			  String hex = String.format("#%02x%02x%02x", number1,number2,number3);
		        			  System.out.println(hex);
		            		 
		            		 alert.setAlertColor(nColor);
		        		 }
		        		 catch(Exception e)
		        		 {
		        			 System.out.println("Alert Color does not exist.");
		        			 Reports.LogResult("Alert"+i+" Alert Color does not exist..Test Failed");
		        			
		        			// blnFail=true;
		        			 errorMsg = "Cluster Color does not exist.";
		        			 sErrorString = sErrorString + ";" + errorMsg;
		        		 }
	*/	        		 Alerts.add(alert);
		        		 //Alerts.add(alert);
		        		 
		        	 	}
			        	 if(!errorMsg.isEmpty())
			        	 {
			        		 
			        		 ATUReports.add("Fail Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName() + "for " +sErrorString,LogAs.FAILED, new CaptureScreen(
				            		   ScreenshotOf.DESKTOP));
			        		 Assert.fail(sErrorString);
			        	 }
			        	 else
			        	 {
			                 ATUReports.add("Pass Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.PASSED, new CaptureScreen(
				            		   ScreenshotOf.DESKTOP));
			        	 }
	        	 }
	        	 else
	        	 {
	        		 System.out.println("Alerts are Not available in the Page");
	        		 ATUReports.add("Test Case Info. Unable to execute Method: " + Thread.currentThread().getStackTrace()[1].getMethodName() + " as Alerts are not available.",LogAs.INFO, new CaptureScreen(
	            		   ScreenshotOf.DESKTOP));
	        	 }
	         }
	         
	        catch(Throwable e)
	         {
	               e.printStackTrace();
	               Assert.fail(e.getMessage());
	               ATUReports.add("Fail Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.FAILED, new CaptureScreen(
	            		   ScreenshotOf.DESKTOP));
	         }
	  }
	//--------------------------------------Verifying Alerts present in Monitor Tab with backend---------------------------------------
	@Test(priority=3, dependsOnMethods = {"CreatingAlertsInfo"})
	public void VerifyUIAlertsWithBackend() 
	{
	  try
		{
		  System.out.println("Verifying Alerts present in Monitor Tab with backend");
		  AlertVerify.VerifyingAlerts(Alerts, inputString);
	     	ATUReports.add("Pass Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.PASSED, new CaptureScreen(
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


}
