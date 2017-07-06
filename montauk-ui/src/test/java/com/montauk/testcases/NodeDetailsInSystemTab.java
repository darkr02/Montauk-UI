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
import com.montauk.nodes.NodeVerify;
import com.montauk.nodes.NodesFromUI;
import com.montauk.reports.Reports;

/**
 * This class holds the Nodes Functionality in SystemTab.
 * @author Krishnendu.Daripa
 */

public class NodeDetailsInSystemTab 
{
	
	private List<NodesFromUI> Nodes = new ArrayList<NodesFromUI>();
	private String sClusterID; 
	private String sNodeID;
	private String inputString;
	Properties obj = new Properties();
	Properties linuxobj = new Properties();
	{
		System.setProperty("atu.reporter.config", System.getProperty("user.dir") + "\\src\\main\\java\\com\\montauk\\objectrepo\\atu.properties");
	}
	//--------------------------------------Verifying No. of Nodes present in System Tab---------------------------------------
	@Test(priority=1)
	public void NoOfNodesInSystemTab() 
	{
	  try
		{
		  	String SystemNodeDetailsCurl;
			FileInputStream objfile = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\com\\montauk\\objectrepo\\objectuimap.properties");
			FileInputStream linuxfile = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\com\\montauk\\objectrepo\\linuxmap.properties");
			obj.load(objfile);
			linuxobj.load(linuxfile);
			WebElement oResult=null;
			Generic.setAuthorInfoForReports();
			LoginTest.oBrowser.navigate().refresh();
			Mytools.WaitFor(2L);
			if (Generic.CheckForElementVisibility(obj.getProperty("minimizesystemtray")))
			{
				Generic.clickElement(obj.getProperty("minimizesystemtray"));
			}
	     	System.out.println("Going to System Tab");
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
			Mytools.WaitFor(1L);
	     	System.out.println("Clicking on 1st cluster on System Tab");
			Generic.clickElement(obj.getProperty("systemClusterid1"));
			Mytools.WaitFor(1L);
			if (allClusterChildElements.size()>= 1)
			{
				oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("systemClusterparentpath") + "div[1]" + obj.getProperty("systemClusterid") ));
	   			System.out.println("Cluster ID: " + oResult.getAttribute("textContent"));
	   			sClusterID = oResult.getAttribute("textContent").trim();   			 
			}
	     	System.out.println("Going to Cluster details page using more info on System Tab");
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
	     	System.out.println("Clicking on 1st Node on System Tab");
			Generic.clickElement(obj.getProperty("systemtabnodeid1"));
			Mytools.WaitFor(1L);
			List<WebElement> allFormChildElements = LoginTest.oBrowser.findElements(By.xpath(obj.getProperty("nodesinsystemtab")));
	     	System.out.println("Number of Node is" + allFormChildElements.size() );

	     	
			if (allFormChildElements.size()>= 1)
			{
	     	oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentnodepath") + "div[1]" + obj.getProperty("systemtabnodeid") ));
   			System.out.println("Node ID: " + oResult.getAttribute("textContent"));
			sNodeID = oResult.getAttribute("textContent").replace("Node Id : ", "").trim();
			}
			System.out.println("Reading Nodes details present in System Tab From Backend .");
			SystemNodeDetailsCurl = Generic.CurlCommandReplacer(linuxobj.getProperty("NodeDetailsinSystemtab"));
			SystemNodeDetailsCurl = SystemNodeDetailsCurl.replace("clusterid", sClusterID).replace("nodeid", sNodeID);
			inputString = ReadingDataFromLinux.ReadClusterData(SystemNodeDetailsCurl);
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
	//--------------------------------------Creating Details of a Node for Verification from Linux Box---------------------------------------
	@Test(priority=2)
	public void CreatingNodeDetailsInfo() 
	{
	 	 String errorMsg = "";
	 	 String sErrorString = "";
	 	 Generic.setAuthorInfoForReports();
	         try
	         {	 
	        	 LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentnodepath")+ "div[1]/div")).click();
	        	 NodesFromUI node = new NodesFromUI();
	        	 // Node Name
	        	 WebElement oResult=null;
	        	 try
	        	 {
        		 	oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("nodename") ));
        			 
        			 Reports.LogResult("Node Name Exist Test Passed");
        			 System.out.println("NodeName : " + oResult.getText());
            		 
        			 node.setNodeName(oResult.getText());
        		 }
        		 catch(Exception e)
        		 {
        			 System.out.println("Node Name does not exist.");
        			 Reports.LogResult("Node Name does not exist.. Test Failed");
        			 errorMsg = "Node Name does not exist.";
        			 sErrorString = errorMsg; 
        		 }
        		 // Node IP Address
        		 try
        		 {
	     		 	oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("nodeipaddress") ));
	     		 	Reports.LogResult("Node IP Address Exist  Test Passed");
	     		 	System.out.println("Node IP Address : " + oResult.getText());
	         		 
	     			 node.setNodeIP(oResult.getText().replace("IP Address: ", ""));
		 		 }
		 		 catch(Exception e)
		 		 {
		 			 System.out.println("Node IP Address does not exist.");
		 			 Reports.LogResult("Node IP Address does not exist.. Test Failed");
		 			 errorMsg = "Node IP Address not exist.";
		 			 sErrorString = errorMsg; 
		 		 }
        		 // Node status
        		 try
        		 {
        			 oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("nodestatus") ));
        			 Reports.LogResult("Node status Exist Test Passed");
        			 System.out.println("Node status : " + oResult.getText());
            		 
        			 node.setNodeStatus(oResult.getText().replace("Status: ", ""));
        		 }
        		 catch(Exception e)
        		 {
        			 System.out.println("Node status does not exist.");
        			 Reports.LogResult("Node status does not exist..Test Failed");
        			 errorMsg = "Node status does not exist.";
        			 sErrorString = sErrorString + ";" + errorMsg;
        		 }
	        		         		
	        	
        		// Node type
        		 try
        		 {
        			 oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("nodetype") ));
        			 Reports.LogResult("Node Type Exist Test Passed");
        			 System.out.println("NodeType : " + oResult.getText());
        			 node.setNodeType(oResult.getText().replace("Node Type: ", ""));
            		 
        		 }
        		 catch(Exception e)
        		 {
        			 System.out.println("Node Type does not exist.");
        			 Reports.LogResult(" Node Type does not exist..Test Failed");
        			 errorMsg = "Node Type does not exist.";
        			 sErrorString = sErrorString + ";" + errorMsg;
        		 }
	        		 
        		// Node description
/*        		 try
        		 {
        			 oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentnodedetailspath") + "div[5]" + obj.getProperty("nodedescription") ));
        			 Reports.LogResult("Node description Exist Test Passed");
        			 System.out.println("Node Description : " + oResult.getText());
        			 node.setNodeDescription(oResult.getText().replace("Description: ", ""));
        		 }
        		 catch(Exception e)
        		 {
        			 System.out.println("Node description does not exist.");
        			 Reports.LogResult(" Node description does not exist..Test Failed");
        			 errorMsg = "Node description does not exist.";
        			 sErrorString = sErrorString + ";" + errorMsg;
        		 }
*/        		 Nodes.add(node);
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
	//--------------------------------------Verifying Nodes details with Backend present in System Tab---------------------------------------
	@Test(priority=3)
	public void VerifyUINodesWithBackend() 
	{
	  try
		{
		  	Generic.setAuthorInfoForReports();
			System.out.println("Verifying Nodes details with Backend present in System Tab.");
			NodeVerify.VerifyingNodeDetails(Nodes, inputString);
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
