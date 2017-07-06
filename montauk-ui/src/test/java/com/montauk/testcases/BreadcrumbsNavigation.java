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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import com.montauk.backend.ReadingDataFromLinux;
import com.montauk.clusters.Cluster;
import com.montauk.clusters.ClusterVerify;
import com.montauk.generic.Generic;
import com.montauk.generic.Mytools;
import com.montauk.login.LoginTest;
import com.montauk.reports.Reports;

/**
 * This class holds the Alerts Functionality in MonitorTab.
 * @author Krishnendu.Daripa
 */

public class BreadcrumbsNavigation 
{
	
	private List<Cluster> Clusters = new ArrayList<Cluster>();
	int noOfClusters;
	Properties obj = new Properties();
	Properties linuxobj = new Properties();
	{
		System.setProperty("atu.reporter.config", System.getProperty("user.dir") + "\\src\\com\\montauk\\objectrepo\\atu.properties");
	}
	//------------------------------Navigating to Monitor Tab----------------------------------------
	@Test(priority=1)
	public void GoingToSystemTab()
	{
		try
		{
			Generic.setAuthorInfoForReports();
			//----------------------Clicking on the System TAB---------------------------
			FileInputStream objfile = new FileInputStream(System.getProperty("user.dir") + "\\src\\com\\montauk\\objectrepo\\objectuimap.properties");
			FileInputStream linuxfile = new FileInputStream(System.getProperty("user.dir") + "\\src\\com\\montauk\\objectrepo\\linuxmap.properties");
			obj.load(objfile);
			linuxobj.load(linuxfile);
			Generic.clickElement(obj.getProperty("systemtab"));
			Mytools.WaitFor(3L);
			Generic.clickElement(obj.getProperty("minimizesystemtray"));
			Mytools.WaitFor(4L);
			//String inputString;
			//setAuthorInfoForReports();
			//inputString = ReadingDataFromLinux.ReadClusterData(linuxobj.getProperty("jobsregcommand1"));
			//System.out.println(inputString);
			ATUReports.add("Pass Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.PASSED, new CaptureScreen(
					ScreenshotOf.DESKTOP));
		}
		catch(Throwable e)
		{
			e.printStackTrace();
			Assert.fail(e.getMessage());
			ATUReports.add("Fail Test Case  " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.FAILED, new CaptureScreen(
					ScreenshotOf.DESKTOP));
		}
	}
	//------------------------------Getting the no. of clusters from UI----------------------------------------
	 @Test(priority=2)
	  public void NoOfClusters()
	  {
	         try
	         {
	        	 Generic.setAuthorInfoForReports();
	        	 List<WebElement> allFormChildElements = LoginTest.oBrowser.findElements(By.xpath(obj.getProperty("ClustersinSystemTabpath")));
	        	 
	        	 System.out.println("Number of Clusters is" + (allFormChildElements.size())/2 );
	        	 ATUReports.add("Pass Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.PASSED, new CaptureScreen(
							ScreenshotOf.DESKTOP));
	        	 noOfClusters= (allFormChildElements.size()/2);
	       
	         }
	         catch(Throwable e)
	         {
	               e.printStackTrace();
	               Assert.fail(e.getMessage());
	               ATUReports.add("Fail Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.FAILED, new CaptureScreen(
	            		   ScreenshotOf.DESKTOP));
	         }
	  }
		//----------------------------------------Verifying the cluster information after clicking on the cluster--------------------------------------
	  @Test(priority=3)
	  public void VerifyNavigationForward()
	  {
	 	 String errorMsg = "";
	 	 String sErrorString = "";
	         try
	         {	 
	        	 
	        	 //Boolean blnFail=false;
	        	 Generic.setAuthorInfoForReports();
	        	 Generic.clickElement(obj.getProperty("systemClusterid3"));
	        	 Generic.clickElement(obj.getProperty("systemmoreinfopath"));
	        	
//---------------------------- Check for System Overview Breadcrumb---------------------------------------------------------------------------------------------	        	 
	        	 
	        	 try
	        	 {
	        	 WebElement oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("systemoverviewbreadcrumbs")));
	        	 System.out.println("First Breadcrumb is Present");
	        	 Reports.LogResult("First Breadcrumb is Present    Test Passed");
    			 	if(oResult.getText().equalsIgnoreCase("System Overview"))
    			 	{
        			 System.out.println( "First Breadcrumb text  matches "+oResult.getText()); 
    			 	}
    			 
    			 	else
    			 	{
    				 System.out.println( "First Breadcrumb text does not match"+oResult.getText()); 
    				 
    				 
    			 	}
    			 }
	        	 
	        	 
	        	 catch(Exception e)
        		 {
        			 System.out.println("First Breadcrumb doesnt exist");
        			System.out.println(e.getMessage());
        			e.printStackTrace();
        			 Reports.LogResult("First Breadcrumb doesnt exist");
        			 //blnFail=true;
        			 errorMsg = "First Breadcrumb doesnt exist";
        			 sErrorString = errorMsg; 
        		 }
//---------------------------- Check for Cluster Breadcrumb---------------------------------------------------------------------------------------------
	        	 try
	        	 {
	        	 WebElement oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("clusterbreadcrumbs")));
	        	 System.out.println("Cluster Breadcrumb is Present");
	        	 Reports.LogResult("Cluster Breadcrumb is Present    Test Passed");
    			 	if(oResult.getText().equalsIgnoreCase(obj.getProperty("breadcrumbtext2")))
    			 	{
        			 System.out.println( "Cluster Breadcrumb text  matches "+oResult.getText()); 
    			 	}
    			 
    			 	else
    			 	{
    				 System.out.println( "Cluster Breadcrumb text does not match"+oResult.getText()); 
    				 
    				 
    			 	}
    			 }
	        	 
	        	 
	        	 catch(Exception e)
        		 {
        			 System.out.println("Cluster Breadcrumb doesnt exist");
        			System.out.println(e.getMessage());
        			e.printStackTrace();
        			 Reports.LogResult("Cluster Breadcrumb doesnt exist");
        			 //blnFail=true;
        			 errorMsg = "Cluster Breadcrumb doesnt exist";
        			 sErrorString = errorMsg; 
        		 }
	        	 
	        	 
//---------------------------- Check for Node Breadcrumb---------------------------------------------------------------------------------------------	        	 
	        	 try
	        	 {
	        	 Mytools.WaitFor(20L);
	        	 Generic.clickElement(obj.getProperty("nodemoreinfo"));
	        	 Mytools.WaitFor(3L);
	        	 WebElement oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("nodebreadcrumbs")));
	        	 System.out.println("Node Breadcrumb is Present");
	        	 Reports.LogResult("Node Breadcrumb is Present    Test Passed");
    			 	if(oResult.getText().equalsIgnoreCase(obj.getProperty("breadcrumbtext3")))
    			 	{
        			 System.out.println( "Node Breadcrumb text  matches "+oResult.getText()); 
    			 	}
    			 
    			 	else
    			 	{
    				 System.out.println( "Node Breadcrumb text does not match"+oResult.getText()); 
    				 
    				 
    			 	}
    			 }
	        	 
	        	 
	        	 catch(Exception e)
        		 {
        			 System.out.println("Node Breadcrumb doesnt exist");
        			System.out.println(e.getMessage());
        			e.printStackTrace();
        			 Reports.LogResult("Node Breadcrumb doesnt exist");
        			 //blnFail=true;
        			 errorMsg = "Node Breadcrumb doesnt exist";
        			 sErrorString = errorMsg; 
        		 }
	        	 
//------------------------Check for element Visibility for ROLES and FILE SYSTEM--------------------------------------------------------------------------------	        	 
	        	 try
	        	 {
	        	 Mytools.WaitFor(5L);
	        	
    			 	Generic.CheckForElementVisibility(obj.getProperty("roles"));
    			 	if (Generic.Visibility)
    			 	{
        			 System.out.println( "Roles Panel Exist"); 
    			 	}
    			 
    			 	else
    			 	{
    				 System.out.println( "Roles doesnt Panel Exist"); 
    				 Reports.LogResult("Roles  doesnt exist");
        			 //blnFail=true;
        			 errorMsg = "Roles doesnt exist";
        			 sErrorString = errorMsg;
        			 
    				}
    			 	
    			 	Generic.CheckForElementVisibility(obj.getProperty("Filesystems"));
    			 	if (Generic.Visibility)
    			 	{
        			 System.out.println( "Filesystems Panel Exist"); 
        			 
    			 	}
    			 
    			 	else
    			 	{
    				 System.out.println( "Filesystems doesnt Panel Exist"); 
    				 Reports.LogResult("FileSystem Panels doesnt exist");
        			 //blnFail=true;
        			 errorMsg = "FileSystem Panels doesnt exist";
        			 sErrorString = errorMsg;
        			 
    				 
    			 	}
    			 }
	        	 
	        	 
	        	 catch(Exception e)
        		 {
        			 System.out.println("Roles and FileSystem Panels doesnt exist");
        			System.out.println(e.getMessage());
        			e.printStackTrace();
        			 Reports.LogResult("Roles and FileSystem Panels doesnt exist");
        			 //blnFail=true;
        			 errorMsg = "Roles and FileSystem Panels doesnt exist";
        			 sErrorString = errorMsg; 
        		 }
	        	 
	        	 
//------------------------Check for element Visibility for NAME NODE--------------------------------------------------------------------------------	        	 
	        	 
	        	 try
	        	 {
	        	 
	        		Generic.clickElement(obj.getProperty("clusterbreadcrumbs"));
	        		Mytools.WaitFor(5L);
    			 	Generic.CheckForElementVisibility(obj.getProperty("namenodepath"));
    			 	if (Generic.Visibility)
    			 	{
        			 System.out.println( "Name Node Exist"); 
    			 	}
    			 
    			 	else
    			 	{
    				 System.out.println( "Name Node doesnt Exist"); 
    				 Reports.LogResult("Name Node doesnt exist");
        			 //blnFail=true;
        			 errorMsg = "Name Node doesnt exist";
        			 sErrorString = errorMsg; 
        			 
    				}
    			 	
    			 	
    			 }
	        	 
	        	 
	        	 catch(Exception e)
        		 {
        			 System.out.println("Name Node doesnt exist");
        			System.out.println(e.getMessage());
        			e.printStackTrace();
        			 Reports.LogResult("Name Node doesnt exist");
        			 //blnFail=true;
        			 errorMsg = "Name Node doesnt exist";
        			 sErrorString = errorMsg; 
        		 }
	        	 
//------------------------Check for element Visibility for CLUSTER--------------------------------------------------------------------------------	        	 
	        	 
	        	 try
	        	 {
	        	 
	        		Generic.clickElement(obj.getProperty("systemoverviewbreadcrumbs"));
	        		Mytools.WaitFor(5L);
    			 	Generic.CheckForElementVisibility(obj.getProperty("systemClusterid3"));
    			 	if (Generic.Visibility)
    			 	{
        			 System.out.println( "Cluster Exist"); 
    			 	}
    			 
    			 	else
    			 	{
    				 System.out.println( "Cluster doesnt Exist"); 
    				 Reports.LogResult("Cluster doesnt exist");
         			 //blnFail=true;
         			 errorMsg = "Cluster doesnt exist";
         			 sErrorString = errorMsg; 
         			
    				}
    			 	
    			 	
    			 }
	        	 
	        	 
	        	 catch(Exception e)
        		 {
        			 System.out.println("Cluster doesnt exist");
        			System.out.println(e.getMessage());
        			e.printStackTrace();
        			 Reports.LogResult("Cluster doesnt exist");
        			 //blnFail=true;
        			 errorMsg = "Cluster doesnt exist";
        			 sErrorString = errorMsg; 
        		 }
	        	 
	        	 
	        	 
	        	 if(!errorMsg.isEmpty())
	        	 {
	        		 
	        		 //Reports.CaptureScreenShot("AfterClick.png");
	        		 ATUReports.add("Fail Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName() + "for " +sErrorString,LogAs.FAILED, new CaptureScreen(
		            		   ScreenshotOf.DESKTOP));
	        		 Assert.fail(sErrorString);
	        	 }
             ATUReports.add("Pass Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.PASSED, new CaptureScreen(
	            		   ScreenshotOf.DESKTOP));
	         }	 
	        catch(Throwable e)
	         {
	               e.printStackTrace();
	               Assert.fail(e.getMessage());
	               ATUReports.add("Fail Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.FAILED, new CaptureScreen(
	            		   ScreenshotOf.DESKTOP));
	         }
	  }
	//-------------------------------Reading Cluster data from Linux and verify with UI-------
	//@Test(priority=4)
	public void VerifyNavigationBackward()
	{
		try
		{
			String inputString;
			Generic.setAuthorInfoForReports();
			for ( int i=1;i<=2;i++)
			{
			inputString = ReadingDataFromLinux.ReadClusterData(linuxobj.getProperty("clusterid"+i));
			ClusterVerify.VerifyingCluster(Clusters, inputString);
			
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

	public List<Cluster> getClusters() 
	{
		return Clusters;
	}
}
