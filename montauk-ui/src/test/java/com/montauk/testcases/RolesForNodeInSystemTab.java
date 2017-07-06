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
import com.montauk.backend.ReadingDataFromLinux;
import com.montauk.generic.Generic;
import com.montauk.generic.Mytools;
import com.montauk.login.LoginTest;
import com.montauk.nodes.RoleVerify;
import com.montauk.nodes.RolesForNodeUI;
import com.montauk.reports.Reports;

/**
 * This class holds the Role Cards Functionality in SystemTab.
 * @author Krishnendu.Daripa
 */

public class RolesForNodeInSystemTab 
{
	
	private List<RolesForNodeUI> Roles = new ArrayList<RolesForNodeUI>();
	Properties obj = new Properties();
	Properties linuxobj = new Properties();
	private String sClusterID; 
	private String inputString;
	private String sNodeID;
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
			String NodeDetailsCurl; 
			WebElement oResult=null;
			Generic.setAuthorInfoForReports();
			LoginTest.oBrowser.navigate().refresh();
			Mytools.WaitFor(2L);
			if (Generic.CheckForElementVisibility(obj.getProperty("minimizesystemtray")))
			{
				Generic.clickElement(obj.getProperty("minimizesystemtray"));
			}
			Mytools.WaitFor(2L);
			Generic.clickElement(obj.getProperty("systemtablink"));
			Mytools.WaitFor(2L);
 			int nCounter = 0;
			do
			{
				if (Generic.CheckForElementVisibility(obj.getProperty("systemClusterid1"))==true)
				{
					break; 
				}
				else
				{
					nCounter++;
					Mytools.WaitFor(1L);
				}
		    }while( nCounter < 20 );
			List<WebElement> allClusterChildElements = LoginTest.oBrowser.findElements(By.xpath(obj.getProperty("ClustersinSystemTabpath")));
			Generic.CheckForElementVisibility(obj.getProperty("systemClusterid1"));
			Mytools.WaitFor(1L);
			Generic.clickElement(obj.getProperty("systemClusterid1"));
			Mytools.WaitFor(1L);
			if (allClusterChildElements.size()>= 1)
			{
				oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("systemClusterparentpath") + "div[1]" + obj.getProperty("systemClusterid") ));
	   			System.out.println("Cluster ID: " + oResult.getAttribute("textContent"));
	   			sClusterID = oResult.getAttribute("textContent").trim();   			 
			}
			Generic.clickElement(obj.getProperty("moreinfo"));
			Mytools.WaitFor(1L);
			nCounter = 0;
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
			Generic.clickElement(obj.getProperty("systemtabnodeid1"));
			List<WebElement> allNodeChildElements = LoginTest.oBrowser.findElements(By.xpath(obj.getProperty("nodesinsystemtab")));
	     	System.out.println("Number of Node is: " + allNodeChildElements.size() );
			if (allNodeChildElements.size()>= 1)
			{
		     	oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentnodepath") + "div[1]" + obj.getProperty("systemtabnodeid") ));
				System.out.println("Node ID: " + oResult.getAttribute("textContent").replace("Node Id : ", "").trim());
				sNodeID = oResult.getAttribute("textContent").replace("Node Id : ", "").trim();
			}
			Mytools.WaitFor(1L);
			NodeDetailsCurl = Generic.CurlCommandReplacer(linuxobj.getProperty("RolesForNodeinSystemtab"));
			NodeDetailsCurl = NodeDetailsCurl.replace("clusterid", sClusterID).replace("nodeid", sNodeID);
			inputString = ReadingDataFromLinux.ReadClusterData(NodeDetailsCurl);
			Generic.clickElement(obj.getProperty("moreinfo"));
			Mytools.WaitFor(1L);
			List<WebElement> allFormChildElements = LoginTest.oBrowser.findElements(By.xpath(obj.getProperty("rolesinsystemtab")));
	     	System.out.println("Number of Role is: " + allFormChildElements.size() );
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
	//--------------------------------------Creating Details of a Role for Verification from Linux Box---------------------------------------
	@Test(priority=2)
	public void RoleCardsInfo() 
	{
	 	 String errorMsg = "";
	 	 String sErrorString = "";
	         try
	         {	
	        	 
	        	 int noOfRoles;
	        	 //Boolean blnFail=false;
	        	 Generic.setAuthorInfoForReports();
	        	 System.out.println("Creating Details of a Role for Verification from Linux Box.");
	        	 List<WebElement> allFormChildElements = LoginTest.oBrowser.findElements(By.xpath(obj.getProperty("rolesinsystemtab")));
	        	 noOfRoles = allFormChildElements.size();
	        	 
	        	 RolesForNodeUI role = new RolesForNodeUI();
	        	 
	        	 
	        	 for(int i=1; i <= noOfRoles; i++) 
	        	 {
	        		 
	        		 String child = "table[" + i + "]/tbody";
	        		 role = new RolesForNodeUI();
		        	 LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentrolepath")+ child)).click();

		        	 //***************************************************** Roles Name for Node***********************************************
		        	 WebElement oResult=null;
		        	 try
		        	 {
	        		 	oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentrolepath") + child + obj.getProperty("roleNamePath") ));
	        			 
	        			 Reports.LogResult("Role Name Exist Test Passed");
	        			 System.out.println("RoleName : " + oResult.getText());
	            		 
	        			 role.setName(oResult.getText());
	        		 }
	        		 catch(Exception e)
	        		 {
	        			 System.out.println("Role Name does not exist.");
	        			 Reports.LogResult("Role Name does not exist.. Test Failed");
	        			 errorMsg = "Role Name does not exist.";
	        			 sErrorString = errorMsg; 
	        		 }
		        	 //***************************************************** Roles Status for Node***********************************************
		        	 try
		        	 {
	        		 	oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentrolepath") + child + obj.getProperty("roleStatusPath") ));
	        			 
	        			 Reports.LogResult("Role Status Exist Test Passed");
	        			 System.out.println("Role Status : " + oResult.getText());
	            		 
	        			 role.setStatus(oResult.getText());
	        		 }
	        		 catch(Exception e)
	        		 {
	        			 System.out.println("Role Status does not exist.");
	        			 Reports.LogResult("Role Status does not exist.. Test Failed");
	        			 errorMsg = "Role Status does not exist.";
	        			 sErrorString = errorMsg; 
	        		 }
		        	if (!oResult.getText().toLowerCase().equals("na"))
		        	{
			        	 //***************************************************** Roles Action Supported for Start and Stop Button for Node***********************************************
		        		try
			        	 {
			        		 oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentrolepath") + child + obj.getProperty("roleStartStopButtonPath") ));
		        			 Reports.LogResult("Role Action Supported Exist Test Passed");
		        			 role.setActionsupported(true);
		        			 role.setButtonName(oResult.getText());
			        	 }
		        		 catch(Exception e)
		        		{
		        			 System.out.println("Role Action Supported does not exist.");
		        			 Reports.LogResult("Role Action Supported does not exist.. Test Failed");
		        			 errorMsg = "Role Action Supported does not exist.";
		        			 sErrorString = errorMsg; 
			        	 }
/*			        	 try
			        	 {
			        		 String imageStartUrl = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentrolepath") + child + obj.getProperty("roleStartStopButtonPath") )).getAttribute("src");
			        		 System.out.println("Role Start Stop Button Path : \n"+ imageStartUrl);  
				       		 
				        		String URL1 = imageStartUrl;
				        		
				        		 
				     		    
				     		    int startIndex1 = URL1.lastIndexOf( '-' );
				     		    
				     		    int endIndex1 =URL1.lastIndexOf( '.' );
				     		    
		        			 Reports.LogResult("Role Action Supported Exist Test Passed");
		        			 System.out.println("Role Action Supported : " + URL1.substring(startIndex1 + 1, endIndex1));
		            		 
		        			 role.setActionsupported(true);
		        			 role.setButtonName(URL1.substring(startIndex1 + 1, endIndex1));
		        		 }
		        		 catch(Exception e)
		        		 {
		        			 System.out.println("Role Action Supported does not exist.");
		        			 Reports.LogResult("Role Action Supported does not exist.. Test Failed");
		        			 errorMsg = "Role Action Supported does not exist.";
		        			 sErrorString = errorMsg; 
		        		 }
*/		        	}
			        else
			        {
	        			 Reports.LogResult("Role Action Supported Exist Test Passed");
	        			 System.out.println("Role Action Supported Flag: " + false);
	            		 
	        			 role.setActionsupported(false);
			        }

		        	 
		        	 //***************************************************** Roles State for Node***********************************************
		        	try
		        	 {
		        		 oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentrolepath") + child + obj.getProperty("roleStatePath") ));
	        			 Reports.LogResult("Role State Exist Test Passed");
	        			 System.out.println("Role State : " + oResult.getText());
	            		 
	        			 role.setState(oResult.getText());
		        	 }
	        		 catch(Exception e)
	        		 {
	        			 System.out.println("Role State does not exist.");
	        			 Reports.LogResult("Role State does not exist.. Test Failed");
	        			 errorMsg = "Role State does not exist.";
	        			 sErrorString = errorMsg; 
	        		 }
	        		 Roles.add(role);
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
	        catch(Throwable e)
	         {
	               e.printStackTrace();
	               Assert.fail(e.getMessage());
	               ATUReports.add("Fail Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.FAILED, new CaptureScreen(
	            		   ScreenshotOf.DESKTOP));
	         }
	  }
	//--------------------------------------Verifying UI node Roles with Backend for System Tab---------------------------------------
	@Test(priority=3)
	public void VerifyUIRolesWithBackend() 
	{
	  try
		{
		  System.out.println("Verifying UI node Roles with Backend for System Tab");
			RoleVerify.VerifyingRoleCardDetails(Roles, inputString);
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
