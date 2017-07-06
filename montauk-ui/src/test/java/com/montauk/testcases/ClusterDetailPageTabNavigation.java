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

/**
 * This class holds the Navigation of Clusters Detail page in SystemTab.
 * @author Krishnendu.Daripa
 */

public class ClusterDetailPageTabNavigation 
{
	
	String sClusterName;
	Properties obj = new Properties();
	Properties linuxobj = new Properties();
	{
		System.setProperty("atu.reporter.config", System.getProperty("user.dir") + "\\src\\main\\java\\com\\montauk\\objectrepo\\atu.properties");
	}
	//------------------------------Navigating to System Tab----------------------------------------
	@Test(priority=1)
	public void GoingToSystemTab()
	{
		try
		{
			Generic.setAuthorInfoForReports();
			//----------------------Clicking on the Monitor TAB---------------------------
			FileInputStream objfile = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\com\\montauk\\objectrepo\\objectuimap.properties");
			FileInputStream linuxfile = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\com\\montauk\\objectrepo\\linuxmap.properties");
			obj.load(objfile);
			linuxobj.load(linuxfile);
			Generic.clickElement(obj.getProperty("systemtablink"));
			Mytools.WaitFor(3L);
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
	//--------------------------------------Navigate from Cluster cards on System tab to Node cards on System Tab---------------------------------------
	@Test(priority=2)
	public void GoingFromSystemTabClusterToNodeCardsInSystemTab() 
	{
		Generic.setAuthorInfoForReports();
		try
		{
			List<WebElement> allFormChildElements = LoginTest.oBrowser.findElements(By.xpath(obj.getProperty("ClustersinSystemTabpath")));
			if (allFormChildElements.size() == 1)
			{
				Generic.clickElement(obj.getProperty("ClustersinSystemTabpath"));
				Mytools.WaitFor(2L);
				Generic.clickElement(obj.getProperty("moreinfo"));
				int nCounter = 0;
				do
				{
					if (Generic.CheckForElementVisibility(obj.getProperty("nodesinsystemtab"))==true)
					{
						break; 
					}
					else
					{
						nCounter++;
						Mytools.WaitFor(1L);
					}
			    }while( nCounter < 30 );
				ATUReports.add("Pass Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.PASSED, new CaptureScreen(
						ScreenshotOf.DESKTOP));
	        	 
			}
			else if(allFormChildElements.size() > 1)
			{
				Generic.clickElement(obj.getProperty("ClustersinSystemTabpath") + "[1]");
				Mytools.WaitFor(2L);
				Generic.clickElement(obj.getProperty("moreinfo"));
				int nCounter = 0;
				do
				{
					if (Generic.CheckForElementVisibility(obj.getProperty("nodesinsystemtab"))==true)
					{
						break; 
					}
					else
					{
						nCounter++;
						Mytools.WaitFor(1L);
					}
			    }while( nCounter < 30 );
				ATUReports.add("Pass Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.PASSED, new CaptureScreen(
						ScreenshotOf.DESKTOP));
	        	 
			}
			else
			{
	           ATUReports.add("Fail Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.FAILED, new CaptureScreen(
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
	//--------------------------------------Verifying all tabs(Nodes, Activity and Stats) are available in System Tab---------------------------------------
	@Test(priority=3)
	public void VerifyAllTabsForClusterDetailsPage() 
	{
         try
         {	 
        	Generic.setAuthorInfoForReports();
			if (Generic.CheckForElementVisibility(obj.getProperty("NodeTab"))==true && Generic.CheckForElementVisibility(obj.getProperty("ActivityTab"))==true && Generic.CheckForElementVisibility(obj.getProperty("StatsTab"))==true )
			{
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
	//--------------------------------------Navigating to all tabs(Nodes, Activity and Stats) in System Tab---------------------------------------
	@Test(priority=4)
	public void VerifyNavigationForTabs() 
	{
         try
         {	 
        	Generic.setAuthorInfoForReports();
    		Generic.clickElement(obj.getProperty("NodeTab"));
    		if (Generic.CheckForElementVisibility(obj.getProperty("nodesinsystemtab"))==true)
    		{
				ATUReports.add("Pass Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName() + " Nodes Tab is Displayed",LogAs.PASSED, new CaptureScreen(
						ScreenshotOf.DESKTOP));
    		}
    		else
    		{
	               ATUReports.add("Fail Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName()+ " Nodes Tab is not Displayed",LogAs.FAILED, new CaptureScreen(
	            		   ScreenshotOf.DESKTOP));
    			
    		}
    		Generic.clickElement(obj.getProperty("ActivityTab"));
    		if (Generic.CheckForElementVisibility(obj.getProperty("StartAJobLink"))==true)
    		{
				ATUReports.add("Pass Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName() + " Activity Tab is Displayed",LogAs.PASSED, new CaptureScreen(
						ScreenshotOf.DESKTOP));
    		}
    		else
    		{
	               ATUReports.add("Fail Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName()+ " Activity Tab is not Displayed",LogAs.FAILED, new CaptureScreen(
	            		   ScreenshotOf.DESKTOP));
    			
    		}
    		Generic.clickElement(obj.getProperty("NodeTab"));
    		Mytools.WaitFor(1L);
    		Generic.clickElement(obj.getProperty("StatsTab"));
    		if (Generic.CheckForElementVisibility(obj.getProperty("ClusterStats"))==true)
    		{
				ATUReports.add("Pass Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName() + " Stats Tab is Displayed",LogAs.PASSED, new CaptureScreen(
						ScreenshotOf.DESKTOP));
    		}
    		else
    		{
	               ATUReports.add("Fail Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName()+ " Stats Tab is not Displayed",LogAs.FAILED, new CaptureScreen(
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
