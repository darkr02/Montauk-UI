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
import com.montauk.nodes.NodesCardFromUI;
import com.montauk.reports.Reports;

/**
 * This class holds the Node Cards Functionality in SystemTab.
 * @author Krishnendu.Daripa
 */

public class FilterNodesUsingSearchInSystemTab 
{
	
	private List<NodesCardFromUI> Nodes = new ArrayList<NodesCardFromUI>();
	private List<Cluster> Clusters = new ArrayList<Cluster>();
	Properties obj = new Properties();
	Properties linuxobj = new Properties();
	{
		System.setProperty("atu.reporter.config", System.getProperty("user.dir") + "\\src\\main\\java\\com\\montauk\\objectrepo\\atu.properties");
	}
	//--------------------------------------Verifying No. of Nodes present for a Cluster in System Tab---------------------------------------
	@Test(priority=1)
	public void NoOfNodesInSystemTab() 
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
			int nCounter = 0;
			do
			{
				if (Generic.CheckForElementVisibility(obj.getProperty("systemtabnodeid1"))==true)
				{
					break; 
				}
				else
				{
					nCounter++;
					Mytools.WaitFor(1L);
				}
		    }while( nCounter < 20 );
			Mytools.WaitFor(1L);
			List<WebElement> allFormChildElements = LoginTest.oBrowser.findElements(By.xpath(obj.getProperty("nodesinsystemtab")));
	     	System.out.println("Number of Node is" + allFormChildElements.size() );
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
	//--------------------------------------QAUINSRCH1_Search functionality for node catalog in card view---------------------------------------
	@Test(priority=2)
	public void VerifySearchFunctionalityForNodeCardView() 
	{
	 	 String sErrorString = "";
	         try
	         {	
	        	 
	        	 int noOfNodes;
	        	 //Boolean blnFail=false;
	        	 Generic.setAuthorInfoForReports();
	        	 String sSearch= "lodcbd09";
	        	 LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("nodecardpageSerachpath"))).sendKeys(sSearch);
	        	 Mytools.WaitFor(2L);
	        	 List<WebElement> allFormChildElements = LoginTest.oBrowser.findElements(By.xpath(obj.getProperty("nodesinsystemtab")));
	        	 noOfNodes = allFormChildElements.size();
	        	 
	        	 
	        	 for(int i=1; i <= noOfNodes; i++) 
	        	 {
	        		 
	        		 String child = "div[" + i + "]";
		        	 LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentnodepath")+ child)).click();
		        	 // Node Card Name
		        	 WebElement oResult=null;
	        		 	oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentnodepath") + child + obj.getProperty("nodecardnamepath") ));
	        			 
	        			 Reports.LogResult("Node Name Exist Test Passed");
	        			 System.out.println("NodeName : " + oResult.getText());
	            		 if (((sSearch==oResult.getText()) || (oResult.getText().contains(sSearch)))==false)
	            		 {
	            			 sErrorString = sErrorString + ";" + oResult.getText();
	            		 }
	        	 }
	        	 
	        	 if(!sErrorString.isEmpty())
	        	 {
	        		 ATUReports.add("Fail Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName() + "for following Nodes: " +sErrorString,LogAs.FAILED, new CaptureScreen(
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
	//--------------------------------------QAUINSRCH2_Search functionality for node catalog in List view---------------------------------------
	@Test(priority=3)
	public void VerifySearchFunctionalityForNodeListView() 
	{
	 	 String sErrorString = "";
	         try
	         {	
	        	 
	        	 int noOfNodes;
	        	 //Boolean blnFail=false;
	        	 Generic.setAuthorInfoForReports();
	        	 String sSearch= "lodcbd09";
	        	 LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("nodecardListViewButton"))).click();
	        	 Mytools.WaitFor(3L);
	        	 LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("nodecardpageSerachpath"))).clear();
	        	 LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("nodecardpageSerachpath"))).sendKeys(sSearch);
	        	 Mytools.WaitFor(3L);
	        	 List<WebElement> allFormChildElements = LoginTest.oBrowser.findElements(By.xpath(obj.getProperty("nodesinsystemtabListView")));
	        	 noOfNodes = allFormChildElements.size();
	        	 
	        	 
	        	 for(int i=1; i <= noOfNodes; i++) 
	        	 {
	        		 
	        		 String child = "div[" + i + "]";
		        	 LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentnodepathListView")+ child)).click();
		        	 // Node Card Name
		        	 WebElement oResult=null;
	        		 	oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentnodepathListView") + child + obj.getProperty("nodecardnamepathListView") ));
	        			 
	        			 Reports.LogResult("Node Name Exist Test Passed");
	        			 System.out.println("NodeName : " + oResult.getText());
	            		 if (((sSearch==oResult.getText()) || (oResult.getText().contains(sSearch)))==false)
	            		 {
	            			 sErrorString = sErrorString + ";" + oResult.getText();
	            		 }
	        	 }
	        	 
	        	 if(!sErrorString.isEmpty())
	        	 {
	        		 ATUReports.add("Fail Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName() + "for following Nodes: " +sErrorString,LogAs.FAILED, new CaptureScreen(
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
}
