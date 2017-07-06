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
import com.montauk.clusters.Cluster;
import com.montauk.clusters.ClusterVerify;
import com.montauk.generic.Generic;
import com.montauk.generic.Mytools;
import com.montauk.login.LoginTest;
import com.montauk.nodes.NodeVerify;
import com.montauk.nodes.NodesCardFromUI;
import com.montauk.reports.Reports;

/**
 * This class holds the Node Cards Functionality in SystemTab.
 * @author Krishnendu.Daripa
 */

public class NodeCardsInSystemTab 
{
	
	private List<NodesCardFromUI> Nodes = new ArrayList<NodesCardFromUI>();
	private List<Cluster> Clusters = new ArrayList<Cluster>();
	private String sClusterID; 
	private String inputString;
	private String inputString1;
	private String sNodeID;
	private int nNoOfNode;
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
			Generic.setAuthorInfoForReports();
			FileInputStream objfile = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\com\\montauk\\objectrepo\\objectuimap.properties");
			FileInputStream linuxfile = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\com\\montauk\\objectrepo\\linuxmap.properties");
			obj.load(objfile);
			linuxobj.load(linuxfile);
			WebElement oResult=null;
			String NodeDetailsCurl; 
			LoginTest.oBrowser.navigate().refresh();
			Mytools.WaitFor(2L);
			if (Generic.CheckForElementVisibility(obj.getProperty("minimizesystemtray")))
			{
				Generic.clickElement(obj.getProperty("minimizesystemtray"));
			}
			Mytools.WaitFor(2L);
			System.out.println("Going to System Tab.");
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
			System.out.println("Clicking the 1st Cluster in the System Tab.");
			Generic.clickElement(obj.getProperty("systemClusterid1"));
			Mytools.WaitFor(1L);
			if (allClusterChildElements.size()>= 1)
			{
				oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("systemClusterparentpath") + "div[1]" + obj.getProperty("systemClusterid") ));
	   			System.out.println("Cluster ID: " + oResult.getAttribute("textContent"));
	   			sClusterID = oResult.getAttribute("textContent").trim();   			 
			}
			System.out.println("Going to the Cluster details page using More Info in the System Tab.");
			Generic.clickElement(obj.getProperty("moreinfo"));
			System.out.println("Reading Nodes data from Linux and verify with UI in System Tab.");
			NodeDetailsCurl = Generic.CurlCommandReplacer(linuxobj.getProperty("NodeCardsinSystemtab"));
			NodeDetailsCurl = NodeDetailsCurl.replace("clusterid", sClusterID);
			inputString = ReadingDataFromLinux.ReadClusterData(NodeDetailsCurl);
			String SystemClusterDetailsCurl = linuxobj.getProperty("clusteridinSystab");
			System.out.println("Reading Cluster details present at the left in Node Card Page in System Tab from Backend.");
			SystemClusterDetailsCurl = Generic.CurlCommandReplacer(linuxobj.getProperty("clusteridinSystab"));
			SystemClusterDetailsCurl = SystemClusterDetailsCurl.replace("clusterid", sClusterID);
			inputString1 = ReadingDataFromLinux.ReadClusterData(SystemClusterDetailsCurl);
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
			Mytools.WaitFor(2L);
			//List<WebElement> allFormChildElements = LoginTest.oBrowser.findElements(By.xpath(obj.getProperty("nodesinsystemtab")));
/*	     	System.out.println("Number of Node is: " + allFormChildElements.size() );
	     	nNoOfNode = allFormChildElements.size();
*/	     	ATUReports.add("Pass Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.PASSED, new CaptureScreen(
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
	public void NodeCardsInfo() 
	{
	 	 String errorMsg = "";
	 	 String sErrorString = "";
	         try
	         {	
	        	 
				//int noOfNodes;
				String sGB = "";
				//Boolean blnFail=false;
				Generic.setAuthorInfoForReports();
				System.out.println("Creating Details of a Node for Verification from Linux Box");
				Mytools.WaitFor(10L);
				List<WebElement> allFormChildElements = LoginTest.oBrowser.findElements(By.xpath(obj.getProperty("nodesinsystemtab")));
				nNoOfNode = allFormChildElements.size();
				System.out.println("Number of Node is: " + nNoOfNode);
	        	NodesCardFromUI node = new NodesCardFromUI();
	        	 
	        	 
	        	 for(int i=1; i <= nNoOfNode; i++) 
	        	 {
	        		 
	        		 String child = "div[" + i + "]";
	        		 node = new NodesCardFromUI();
		        	 LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentnodepath")+ child)).click();
		        	 // Node Card Name
		        	 WebElement oResult=null;
		        	 try
		        	 {
	        		 	oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentnodepath") + child + obj.getProperty("nodecardnamepath") ));
	        			 
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
		        	 // Node Card Name
		        	 try
		        	 {
	        		 	oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentnodepath") + child + obj.getProperty("systemtabnodeid") ));
	        			 
	        			 Reports.LogResult("Node ID Exist Test Passed");
	        			 sNodeID = oResult.getAttribute("textContent").replace("Node Id : ", "").trim();
	        			 System.out.println("Node ID: " + sNodeID);
	        		 }
	        		 catch(Exception e)
	        		 {
	        			 System.out.println("Node ID does not exist.");
	        			 Reports.LogResult("Node ID does not exist.. Test Failed");
	        			 errorMsg = "Node ID does not exist.";
	        			 sErrorString = errorMsg; 
	        		 }
	        		 // Node card GB 
	        		 try
	        		 {
		     		 	oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentnodepath") + child + obj.getProperty("nodecardgbpath") ));
		     		 	Reports.LogResult("Node Card GB Exist  Test Passed");
		     		 	System.out.println("Node Card GB : " + oResult.getText());
		     		 	sGB = oResult.getText();
		     			node.setNodeGB(oResult.getText());
			 		 }
			 		 catch(Exception e)
			 		 {
			 			 System.out.println("Node card GB does not exist.");
			 			 Reports.LogResult("Node card GB does not exist.. Test Failed");
			 			 errorMsg = "Node card GB not exist.";
			 			 sErrorString = errorMsg; 
			 		 }
		        		// Node card GB available 
	        		 try
	        		 {
	        			 if ((i== 1) || (sGB.equals("0")))
	        			 {
	        				 oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentnodepath") + child + obj.getProperty("nodecardgbavailablepath1") ));
	        			 }
	        			 else
	        			 {
	        				 oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentnodepath") + child + obj.getProperty("nodecardgbavailablepath") ));
	        			 }
	        			 Reports.LogResult("Node card GB available Exist Test Passed");
	        			 System.out.println("Node card GB available : " + oResult.getText());
	        			 
	        			 String sGBavailable = oResult.getText().replace(" GB Available", "").replace(" B Available", "");
	        			 sGBavailable = sGBavailable.indexOf(".") < 0 ? sGBavailable : sGBavailable.replaceAll("0*$", "").replaceAll("\\.$", "");
	        			 node.setNodeGBAvailable(sGBavailable);
	            		 
	        		 }
	        		 catch(Exception e)
	        		 {
	        			 System.out.println("Node card GB available does not exist.");
	        			 Reports.LogResult(" Node card GB available does not exist..Test Failed");
	        			 errorMsg = "Node card GB available does not exist.";
	        			 sErrorString = sErrorString + ";" + errorMsg;
	        		 }
	        		 // Node card status
	        		 try
	        		 {
	        			 oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentnodepath") + child + obj.getProperty("nodecardstatuspath") ));
	        			 Reports.LogResult("Node status Exist Test Passed");
	        			 System.out.println("Node status : " + oResult.getText());
	            		 
	        			 node.setNodeStatus(oResult.getText());
	        		 }
	        		 catch(Exception e)
	        		 {
	        			 System.out.println("Node status does not exist.");
	        			 Reports.LogResult("Node status does not exist..Test Failed");
	        			 errorMsg = "Node status does not exist.";
	        			 sErrorString = sErrorString + ";" + errorMsg;
	        		 }
		        		         		
		        	
		        		// Node card Used GB available 
	        		 try
	        		 {
	        			 if ((i== 1) || (sGB.equals("0")))
	        			 {
	        				 //oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentnodepath") + child + obj.getProperty("nodecardgbavailablepath1") ));
		        			 Reports.LogResult("Node card Used GB Exist. Test Passed");
		        			 System.out.println("Node card Used GB : 0");
		        			 node.setNodeGBUsed("0");
	        			 }
	        			 else
	        			 {
	        				 oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentnodepath") + child + obj.getProperty("nodecardgbusedpath") ));
		        			 Reports.LogResult("Node card Used GB Exist Test Passed");
		        			 System.out.println("Node card Used GB : " + oResult.getAttribute("title"));
		        			 node.setNodeGBUsed(oResult.getAttribute("title").replace(" GB used", ""));
	        			 }
	            		 
	        		 }
	        		 catch(Exception e)
	        		 {
	        			 System.out.println("Node card Used GB does not exist.");
	        			 Reports.LogResult(" Node card Used GB does not exist..Test Failed");
	        			 errorMsg = "Node card Used GB does not exist.";
	        			 sErrorString = sErrorString + ";" + errorMsg;
	        		 }
		        		 
		        		 
	        		// Node card Alert
	        		 try
	        		 {
	        				int nCounter = 0;
	        				do
	        				{
	        					if (Generic.CheckForElementVisibility(obj.getProperty("parentnodepath") + child + obj.getProperty("nodecardalertpath"))==true)
	        					{
	        						break; 
	        					}
	        					else
	        					{
	        						nCounter++;
	        						Mytools.WaitFor(1L);
	        					}
	        			    }while( nCounter < 20 );
	        			 
	        			oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentnodepath") + child + obj.getProperty("nodecardalertpath") ));
	        			Reports.LogResult("Node Alert Exist Test Passed");
	        			System.out.println("Node Alert : " + oResult.getText());
	        			node.setNodeAlert(Integer.parseInt(oResult.getText()));
	        		 }
	        		 catch(Exception e)
	        		 {
	        			 System.out.println("Node Alert does not exist.");
	        			 Reports.LogResult(" Node Alert does not exist..Test Failed");
	        			 errorMsg = "Node Alert does not exist.";
	        			 sErrorString = sErrorString + ";" + errorMsg;
	        		 }
	        		 Nodes.add(node);
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
	//--------------------------------------Verifying Node Card UI details in System Tab with Backend---------------------------------------
	@Test(priority=3)
	public void VerifyUINodesWithBackend() 
	{
	  try
		{
			Generic.setAuthorInfoForReports();
			System.out.println("Verifying Node Card UI details in System Tab with Backend.");
			NodeVerify.VerifyingNodeCardDetails(Nodes, inputString, nNoOfNode);
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

	//--------------------------------------Creating Details of a Cluster on left panel on System Tab Node Card page for Verification from Linux Box---------------------------------------
	@Test(priority=4)
	public void CreatingClusterDetailsInfoInNodeCardPage() 
	{
	 	 String errorMsg = "";
	 	 String sErrorString = "";
	 	 Generic.setAuthorInfoForReports();
	         try
	         {	 
	        	 //LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("systemClusterparentpath")+ "div[1]/div")).click();
	        	 Cluster ClusterDetails = new Cluster();
	        	 // Cluster Name in details section at the buttom
	        	 System.out.println("Creating Details of a Cluster on left panel on System Tab Node Card page for Verification from Linux Box");
	        	 WebElement oResult=null;
	        	 WebElement oResult1=null;
	        	 try
	        	 {
        		 	oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentclusterdetailspathInNCP") + "div[1]" + obj.getProperty("SystemTabClusterNameNCP") ));
        			 
        			 Reports.LogResult("Cluster Name Exist Test Passed");
        			 System.out.println("Cluster Name : " + oResult.getText());
            		 
        			 ClusterDetails.setClusterName(oResult.getText());
        		 }
        		 catch(Exception e)
        		 {
        			 System.out.println("Cluster Name does not exist.");
        			 Reports.LogResult("Cluster Name does not exist.. Test Failed");
        			 errorMsg = "Cluster Name does not exist.";
        			 sErrorString = errorMsg; 
        		 }
        		 // System Tab Cluster Alert no
        		 try
        		 {
	     		 	oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentclusterdetailspathInNCP") + "div[2]" + obj.getProperty("SystemTabClusterAlertNoNCP") ));
	     		 	Reports.LogResult("Cluster Alert no Exist  Test Passed");
	     		 	System.out.println("Cluster Alert no : " + oResult.getText());
	         		 
	     		 	ClusterDetails.setAlerts(Integer.parseInt(oResult.getText()));
		 		 }
		 		 catch(Exception e)
		 		 {
		 			 System.out.println("Cluster Alert no does not exist.");
		 			 Reports.LogResult("Cluster Alert no does not exist.. Test Failed");
		 			 errorMsg = "Cluster Status not exist.";
		 			 sErrorString = errorMsg; 
		 		 }
        		 // System Tab Cluster Description
        		 try
        		 {
	     		 	oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentclusterdetailspathInNCP") + "div[3]" + obj.getProperty("SystemTabClusterDescriptionNCP") ));
	     		 	Reports.LogResult("Cluster Description Exist  Test Passed");
	     		 	System.out.println("Cluster Description : " + oResult.getText());
	         		 
	     		 	ClusterDetails.setClusterDescripton(oResult.getText().replace("Description: ", ""));
		 		 }
		 		 catch(Exception e)
		 		 {
		 			 System.out.println("Cluster Description does not exist.");
		 			 Reports.LogResult("Cluster Description does not exist.. Test Failed");
		 			 errorMsg = "Cluster Status not exist.";
		 			 sErrorString = errorMsg; 
		 		 }
        		 // System Tab Cluster Status
        		 try
        		 {
	     		 	oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentclusterdetailspathInNCP") + "div[4]" + obj.getProperty("SystemTabClusterStatusNCP") ));
	     		 	Reports.LogResult("Cluster Status Exist  Test Passed");
	     		 	System.out.println("Cluster Status : " + oResult.getText());
	         		 
	     		 	ClusterDetails.setClusterStatus(oResult.getText().replace("Status: ", ""));
		 		 }
		 		 catch(Exception e)
		 		 {
		 			 System.out.println("Cluster Status does not exist.");
		 			 Reports.LogResult("Cluster Status does not exist.. Test Failed");
		 			 errorMsg = "Cluster Status not exist.";
		 			 sErrorString = errorMsg; 
		 		 }
        		 // System Tab Cluster Data On Disk
        		 try
        		 {
        			 oResult1 = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentclusterdetailspathInNCP") + "div[5]" + obj.getProperty("SystemTabClusterDODSpan")));
        			 oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentclusterdetailspathInNCP") + "div[5]" + obj.getProperty("SystemTabClusterDataOnDiskNCP") ));
        			 Reports.LogResult("Cluster Data On Disk Exist Test Passed");
        			 System.out.println("Cluster Data On Disk : " + oResult.getText().replace(oResult1.getText(), ""));
        			 //ClusterDetails.setGb(oResult.getText().replace("Data on Disk: ", "").replace(" GB on disk", ""));
        			 ClusterDetails.setGb(oResult.getText().replace(oResult1.getText(), "").replace("GB on disk", "").replace("B on disk", "").replace("Data on Disk: ", "").trim());
        		 }
        		 catch(Exception e)
        		 {
        			 System.out.println("Cluster Data On Disk does not exist.");
        			 Reports.LogResult("Cluster Data On Disk does not exist..Test Failed");
        			 errorMsg = "Cluster Data On Disk does not exist.";
        			 sErrorString = sErrorString + ";" + errorMsg;
        		 }
	        		         		
	        	
        		// System Tab Cluster No of Nodes
        		 try
        		 {
        			 oResult1 = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentclusterdetailspathInNCP") + "div[6]" + obj.getProperty("SystemTabClusterNodesNCPSpan") ));
        			 oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentclusterdetailspathInNCP") + "div[6]" + obj.getProperty("SystemTabClusterNodesNCP") ));
        			 Reports.LogResult("Cluster No of Nodes Exist Test Passed");
        			 System.out.println("Cluster No of Nodes : " + oResult.getText().replace(oResult1.getText(), ""));
        			 ClusterDetails.setNodes(Integer.parseInt(oResult.getText().replace(oResult1.getText(), "").replace(" nodes", "").trim()));

        		 }
        		 catch(Exception e)
        		 {
        			 System.out.println("Cluster No of Nodes does not exist.");
        			 Reports.LogResult(" Cluster No of Nodes does not exist..Test Failed");
        			 errorMsg = "Cluster No of Nodes does not exist.";
        			 sErrorString = sErrorString + ";" + errorMsg;
        		 }
	        		 
        		// System Tab Cluster Running Jobs
        		 try
        		 {
        			 oResult1 = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentclusterdetailspathInNCP") + "div[7]" + obj.getProperty("SystemTabClusterJobsSpan") ));
        			 oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentclusterdetailspathInNCP") + "div[7]" + obj.getProperty("SystemTabClusterJobsNCP") ));
        			 Reports.LogResult("Cluster Running Jobs Exist Test Passed");
        			 System.out.println("Cluster Running Jobs : " + oResult.getText().replace(oResult1.getText(), ""));
        			 ClusterDetails.setJobs(Integer.parseInt(oResult.getText().replace(oResult1.getText(), "").replace(" running jobs", "").trim()));
        		 }
        		 catch(Exception e)
        		 {
        			 System.out.println("Cluster Running Jobs does not exist.");
        			 Reports.LogResult(" Cluster Running Jobs does not exist..Test Failed");
        			 errorMsg = "Cluster Running Jobs does not exist.";
        			 sErrorString = sErrorString + ";" + errorMsg;
        		 }
        		 Clusters.add(ClusterDetails);
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
	//--------------------------------------Verifying Cluster details present at the left in Node Card Page in System Tab with Backend---------------------------------------
	@Test(priority=5)
	public void VerifySystemUIClusterDetailOnLeftPanelWithBackend() 
	{
	  try
		{
			Generic.setAuthorInfoForReports();
			System.out.println("Verifying Cluster details present at the left in Node Card Page in System Tab with Backend.");
			ClusterVerify.VerifyingLeftPanelClusterDetailsInSystemTab(Clusters, inputString1);
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
