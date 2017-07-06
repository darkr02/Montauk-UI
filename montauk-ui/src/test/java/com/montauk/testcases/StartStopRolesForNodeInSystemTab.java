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

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import com.montauk.generic.Generic;
import com.montauk.generic.Mytools;
import com.montauk.login.LoginTest;
import com.montauk.nodes.RolesForNodeUI;
import com.montauk.reports.Reports;

/**
 * This class holds the Role Cards Functionality in SystemTab.
 * @author Krishnendu.Daripa
 */

public class StartStopRolesForNodeInSystemTab 
{
	
	private List<RolesForNodeUI> Roles = new ArrayList<RolesForNodeUI>();
	Properties obj = new Properties();
	Properties linuxobj = new Properties();
	{
		System.setProperty("atu.reporter.config", System.getProperty("user.dir") + "\\src\\main\\java\\com\\montauk\\objectrepo\\atu.properties");
	}
	//--------------------------------------Verifying No. of Role present in System Tab---------------------------------------
	@Test(priority=1)
	public void NoOfRolesInSystemTab() 
	{
	  try
		{
			FileInputStream objfile = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\com\\montauk\\objectrepo\\objectuimap.properties");
			FileInputStream linuxfile = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\com\\montauk\\objectrepo\\linuxmap.properties");
			obj.load(objfile);
			linuxobj.load(linuxfile);
			Generic.setAuthorInfoForReports();
			Generic.clickElement(obj.getProperty("systemtablink"));
			Mytools.WaitFor(2L);
			Generic.CheckForElementVisibility(obj.getProperty("systemClusterid1"));
			Mytools.WaitFor(1L);
			Generic.clickElement(obj.getProperty("systemClusterid1"));
			Mytools.WaitFor(1L);
			Generic.clickElement(obj.getProperty("moreinfo"));
			Mytools.WaitFor(1L);
			int nCounter = 0;
			do
			{
				if (Generic.CheckForElementVisibility(obj.getProperty("systemtabnodeid2"))==true)
				{
					break; 
				}
				else
				{
					nCounter++;
					Mytools.WaitFor(1L);
				}
		    }while( nCounter < 20 );

			Generic.clickElement(obj.getProperty("systemtabnodeid2"));
			Mytools.WaitFor(1L);
			Generic.clickElement(obj.getProperty("moreinfo"));
			Mytools.WaitFor(1L);
			nCounter = 0;
			do
			{
				if (Generic.CheckForElementVisibility(obj.getProperty("rolesinsystemtab"))==true)
				{
					break; 
				}
				else
				{
					nCounter++;
					Mytools.WaitFor(1L);
				}
		    }while( nCounter < 20 );

			List<WebElement> allFormChildElements = LoginTest.oBrowser.findElements(By.xpath(obj.getProperty("rolesinsystemtab")));
	     	System.out.println("Number of Role is" + allFormChildElements.size() );
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
	//--------------------------------------Stop a role which is already in started status---------------------------------------
	@Test(priority=2)
	public void StopAStartedRoleOnaNode() 
	{
	 	 String errorMsg = "";
	 	 String sErrorString = "";
	         try
	         {	
	        	 
	        	 int noOfRoles;
	        	 //Boolean blnFail=false;
	        	 Generic.setAuthorInfoForReports();
	        	 List<WebElement> allFormChildElements = LoginTest.oBrowser.findElements(By.xpath(obj.getProperty("rolesinsystemtab")));
	        	 noOfRoles = allFormChildElements.size();
		    	 LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentrolepath")+ "div[1]")).click();
		
		    	 WebElement oResult=null;
		    	 try
		    	 {
				 	oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentrolepath") + "div[1]" + obj.getProperty("roleNamePath") ));
					 
					 Reports.LogResult("Role Name Exist Test Passed");
					 System.out.println("RoleName : " + oResult.getText());
		    		 
				 }
				 catch(Exception e)
				 {
					 System.out.println("Role Name does not exist.");
					 Reports.LogResult("Role Name does not exist.. Test Failed");
	        		 ATUReports.add("Fail Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName() ,LogAs.FAILED, new CaptureScreen(
		            		   ScreenshotOf.DESKTOP));
	        		 Assert.fail(sErrorString);
				 }
		    	 //***************************************************** Roles Start and Stop Button for Node***********************************************
		    	 try
		    	 {
					String imageStartUrl = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentrolepath") + "div[1]" + obj.getProperty("roleStartStopButtonPath") )).getAttribute("src");
					System.out.println("Role Start Stop Button Path : \n"+ imageStartUrl);  
		       		 
	        		String URL1 = imageStartUrl;
	        		
	        		 
	     		    
	     		    int startIndex1 = URL1.lastIndexOf( '-' );
	     		    
	     		    int endIndex1 =URL1.lastIndexOf( '.' );
		     		    
					Reports.LogResult("Role Action Supported Exist Test Passed");
					System.out.println("Role Action Supported : " + URL1.substring(startIndex1 + 1, endIndex1));
					String sStop = URL1.substring(startIndex1 + 1, endIndex1);
					//Verifying Stop functionality
					if (sStop.equalsIgnoreCase("stop"))
					{
						Reports.LogResult("Role Action Supported Stop Button Test Passed");
						LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentrolepath") + "div[1]" + obj.getProperty("roleStartStopButtonPath") )).click();
						
					        Alert alert = LoginTest.oBrowser.switchTo().alert();

					        // check if alert exists
					        // TODO find better way
					        System.out.println(alert.getText());

					        // alert handling
					        alert.accept();
						Mytools.WaitFor(5L);
						LoginTest.oBrowser.navigate().refresh();
						Generic.clickElement(obj.getProperty("systemtablink"));
						Mytools.WaitFor(2L);
						Generic.CheckForElementVisibility(obj.getProperty("systemClusterid1"));
						Mytools.WaitFor(1L);
						Generic.clickElement(obj.getProperty("systemClusterid1"));
						Mytools.WaitFor(1L);
						Generic.clickElement(obj.getProperty("moreinfo"));
						Mytools.WaitFor(1L);
						int nCounter = 0;
						do
						{
							if (Generic.CheckForElementVisibility(obj.getProperty("systemtabnodeid2"))==true)
							{
								Generic.clickElement(obj.getProperty("systemtabnodeid2"));
								Mytools.WaitFor(1L);
								Generic.clickElement(obj.getProperty("moreinfo"));
								break; 
							}
							else
							{
								nCounter++;
								Mytools.WaitFor(1L);
							}
					    }while( nCounter < 20 );
						
						nCounter = 0;
						do
						{
							if (Generic.CheckForElementVisibility(obj.getProperty("rolesinsystemtab"))==true)
							{
								break; 
							}
							else
							{
								nCounter++;
								Mytools.WaitFor(1L);
							}
					    }while( nCounter < 20 );
						String imageStartUrl1 = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentrolepath") + "div[1]" + obj.getProperty("roleStartStopButtonPath") )).getAttribute("src");
		        		String URL = imageStartUrl1;
		     		    int startIndex = URL.lastIndexOf( '-' );
		     		    
		     		    int endIndex =URL.lastIndexOf( '.' );
			     		    
						Reports.LogResult("Role Action Supported Exist Test Passed");
						System.out.println("Role Action Supported : " + URL.substring(startIndex + 1, endIndex));
						String sStart = URL.substring(startIndex + 1, endIndex);
						if (sStart.equalsIgnoreCase("start"))
						{
			                 ATUReports.add("Pass Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.PASSED, new CaptureScreen(
				            		   ScreenshotOf.DESKTOP));
							
						}
						else
						{
			        		 ATUReports.add("Fail Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName() ,LogAs.FAILED, new CaptureScreen(
				            		   ScreenshotOf.DESKTOP));
			        		 Assert.fail(sErrorString);
							
						}
					}
					 
		    		 
				 }
				 catch(Exception e)
				 {
					 System.out.println("Role Action Supported does not exist.");
					 Reports.LogResult("Role Action Supported does not exist.. Test Failed");
	        		 ATUReports.add("Fail Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName() ,LogAs.FAILED, new CaptureScreen(
		            		   ScreenshotOf.DESKTOP));
	        		 Assert.fail(sErrorString);
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
}
