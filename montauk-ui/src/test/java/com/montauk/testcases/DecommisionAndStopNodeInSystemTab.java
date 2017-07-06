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
import com.montauk.generic.Generic;
import com.montauk.generic.Mytools;
import com.montauk.login.LoginTest;
import com.montauk.nodes.RolesForNodeUI;
import com.montauk.reports.Reports;

/**
 * This class holds the Role Cards Functionality in SystemTab.
 * @author Krishnendu.Daripa
 */

public class DecommisionAndStopNodeInSystemTab 
{
	
	private int nClusterId;
	private int nNodeId;
	private List<RolesForNodeUI> Roles = new ArrayList<RolesForNodeUI>();
	Properties obj = new Properties();
	Properties linuxobj = new Properties();
	{
		System.setProperty("atu.reporter.config", System.getProperty("user.dir") + "\\src\\main\\java\\com\\montauk\\objectrepo\\atu.properties");
	}
	//------------------------------Navigating to System Tab cluster details page loaded with nodes----------------------------------------
	@Test(priority=1)
	public void GoingToSystemTab()
	{
		
		String sErrorString;
		try
		{
			int noOfClusters;
			Generic.setAuthorInfoForReports();
			//----------------------Clicking on the System TAB---------------------------
			FileInputStream objfile = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\com\\montauk\\objectrepo\\objectuimap.properties");
			FileInputStream linuxfile = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\com\\montauk\\objectrepo\\linuxmap.properties");
			obj.load(objfile);
			linuxobj.load(linuxfile);
			Generic.clickElement(obj.getProperty("systemtablink"));
			Mytools.WaitFor(3L);
			List<WebElement> allFormChildElements = LoginTest.oBrowser.findElements(By.xpath(obj.getProperty("ClustersinSystemTabpath")));

			noOfClusters = allFormChildElements.size();
    	 
       	 
       	 
       	 for(int i=1; i <= noOfClusters; i++) 
       	 {
       		 
       		 String child = "div[" + i + "]";
       		 
       		// Generic.clickElement(obj.getProperty("parentdivpath")+ child +"/div");
       		// String Color = oBrowser.findElement(By.xpath("//div[5]/div[2]/div[2]/div/div[1]/div/div/div/div[2]/div/div/div/div[" + i +"]/div")).getCssValue("color");
       		 
       		 //System.out.println("Colors" + Color);
       		 LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("systemClusterparentpath")+ child +"/div")).click();
       		 
       		 // ClusterName
       		 WebElement oResult=null;
       		 
       		 try
       		 {
       			 oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("systemClusterparentpath") + child + obj.getProperty("systemClustername") ));
       			 
       			 Reports.LogResult("Cluster"+i+"Cluster Name Exist     Test Passed");
       			 System.out.println("ClusterName : " + oResult.getText());
       			 if (oResult.getText().equalsIgnoreCase(obj.getProperty("systemClusterNamePadma")))
       			 {
       				nClusterId = Integer.parseInt(LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("systemClusterparentpath") + child + obj.getProperty("systemClustername"))).getText().trim());
       				LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("systemClusterparentpath") + child + "/div")).click();
    				Generic.clickElement(obj.getProperty("moreinfo1"));
    				int nCounter = 0;
    				do
    				{
    					if (Generic.CheckForElementVisibility(obj.getProperty("nodesinsystemtab"))==true)
    					{
    						Generic.clickElement(obj.getProperty("systemtabnodeid2"));
    						nNodeId = Integer.parseInt(LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("systemtabnodeidNo2"))).getText().replace("Node Id : ", "").trim());
    						Mytools.WaitFor(1L);
    						break; 
    					}
    					else
    					{
    						nCounter++;
    						Mytools.WaitFor(1L);
    					}
    			    }while( nCounter < 30 );

       			 }
           		 
       		 }
       		 catch(Exception e)
       		 {
       			 System.out.println("Cluster Name does not exist.");
       			 Reports.LogResult("Cluster"+i+  " Cluster Name does not exist.. Test Failed");
       			 sErrorString = "Cluster Name does not exist."; 
       		 }

			
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

	//--------------------------------------Verifying Actions for Nodes are available in System Tab---------------------------------------
	@Test(priority=3)
	public void VerifyAllTabsForClusterDetailsPage() 
	{
         try
         {	 
        	Generic.setAuthorInfoForReports();
			if (Generic.CheckForElementVisibility(obj.getProperty("ActionDropdown"))==true)
			{
				Generic.clickElement(obj.getProperty("ActionDropdown"));
				ATUReports.add("Pass Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName() + " ALL Tabs Available",LogAs.PASSED, new CaptureScreen(
						ScreenshotOf.DESKTOP));
			}
			else
			{
	               ATUReports.add("Fail Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName()+ " ALL Tabs are not Available",LogAs.FAILED, new CaptureScreen(
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

}
