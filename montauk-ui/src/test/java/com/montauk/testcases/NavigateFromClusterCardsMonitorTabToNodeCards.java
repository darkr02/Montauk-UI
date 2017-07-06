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
import com.montauk.clusters.Cluster;
import com.montauk.generic.Generic;
import com.montauk.generic.Mytools;
import com.montauk.login.LoginTest;

/**
 * This class holds the Clusters Details Functionality at Bottom in SystemTab.
 * @author Krishnendu.Daripa
 */

public class NavigateFromClusterCardsMonitorTabToNodeCards 
{
	
	private List<Cluster> Clusters = new ArrayList<Cluster>();
	String sClusterName;
	Properties obj = new Properties();
	Properties linuxobj = new Properties();
	{
		System.setProperty("atu.reporter.config", System.getProperty("user.dir") + "\\src\\main\\java\\com\\montauk\\objectrepo\\atu.properties");
	}
	//------------------------------Navigating to Monitor Tab----------------------------------------
	@Test(priority=1)
	public void GoingToMonitorTab()
	{
		try
		{
			Generic.setAuthorInfoForReports();
			//----------------------Clicking on the Monitor TAB---------------------------
			FileInputStream objfile = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\com\\montauk\\objectrepo\\objectuimap.properties");
			FileInputStream linuxfile = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\com\\montauk\\objectrepo\\linuxmap.properties");
			obj.load(objfile);
			linuxobj.load(linuxfile);
			LoginTest.oBrowser.navigate().refresh();
			Mytools.WaitFor(3L);
			if (Generic.CheckForElementVisibility(obj.getProperty("minimizesystemtray")))
			{
				Generic.clickElement(obj.getProperty("minimizesystemtray"));
			}
			Generic.clickElement(obj.getProperty("monitortablink"));
			Mytools.WaitFor(3L);
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
			ATUReports.add("Fail Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.FAILED, new CaptureScreen(
					ScreenshotOf.DESKTOP));
		}
	}
	//--------------------------------------Navigate from Cluster cards on Monitoring tab to Node cards on System Tab---------------------------------------
	@Test(priority=2)
	public void GoingFromMonitorTabClusterToNodeCardsInSystemTab() 
	{
		Generic.setAuthorInfoForReports();
		WebElement oResult=null;
		try
		{
			oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentdivpath") + "div[1]" + obj.getProperty("clusternamepath") ));
			System.out.println("ClusterName In Monitor tab cluster card Page: " + oResult.getText());
			sClusterName = oResult.getText();
			LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentdivpath") + "div[1]" + obj.getProperty("dropdown"))).click();
			LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("moreinfo"))).click();
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
	//--------------------------------------Navigate from Cluster cards on Monitoring tab to Node cards on System Tab---------------------------------------
	@Test(priority=3)
	public void VerifyNodeCardsInSystemTabForCluster() 
	{
		int noOfNodes;
         try
         {	 
        	Generic.setAuthorInfoForReports();
    		WebElement oResult=null;
			int nCounter = 0;
			do
			{
				if (Generic.CheckForElementVisibility(obj.getProperty("nodesinsystemtab"))==true)
				{
					//Generic.clickElement(obj.getProperty("systemtabnodeid2"));
					Mytools.WaitFor(1L);
					//Generic.clickElement(obj.getProperty("systemMoreInfo1"));
					break; 
				}
				else
				{
					nCounter++;
					Mytools.WaitFor(1L);
				}
		    }while( nCounter < 20 );
    		//Generic.CheckForElementVisibility(obj.getProperty("nodesinsystemtab"));
        	List<WebElement> allFormChildElements = LoginTest.oBrowser.findElements(By.xpath(obj.getProperty("nodesinsystemtab")));
        	noOfNodes = allFormChildElements.size();
			Mytools.WaitFor(1L);
			System.out.println("No Of Nodes in Node Cards Page: " + noOfNodes);
			oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentclusterdetailspathInNodeCardPage") + "div[1]" + obj.getProperty("SystemTabClusterName") ));
			System.out.println("ClusterName In Node Cards Page: " + oResult.getText());
        	if ((noOfNodes >= 1) && (sClusterName.equals(oResult.getText())))
        	{
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

}
