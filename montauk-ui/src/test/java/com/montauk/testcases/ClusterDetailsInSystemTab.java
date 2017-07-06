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
import com.montauk.reports.Reports;

/**
 * This class holds the Clusters Details Functionality at Bottom in SystemTab.
 * @author Krishnendu.Daripa
 */

public class ClusterDetailsInSystemTab 
{
	
	private List<Cluster> Clusters = new ArrayList<Cluster>();
	Properties obj = new Properties();
	Properties linuxobj = new Properties();
	private String inputString;
	private String sClusterID;
	{
		System.setProperty("atu.reporter.config", System.getProperty("user.dir") + "\\src\\main\\java\\com\\montauk\\objectrepo\\atu.properties");
	}
	//--------------------------------------Verifying No. of Cluster present in System Tab---------------------------------------
	@Test(priority=1)
	public void NoOfClustersInSystemTab() 
	{
	  try
		{
			FileInputStream objfile = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\com\\montauk\\objectrepo\\objectuimap.properties");
			FileInputStream linuxfile = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\com\\montauk\\objectrepo\\linuxmap.properties");
			obj.load(objfile);
			linuxobj.load(linuxfile);
			Generic.setAuthorInfoForReports();
			if (Generic.CheckForElementVisibility(obj.getProperty("minimizesystemtray")))
			{
				Generic.clickElement(obj.getProperty("minimizesystemtray"));
			}
			Mytools.WaitFor(1L);
	     	System.out.println("Going to System Tab");
			Generic.clickElement(obj.getProperty("systemtablink"));
			Mytools.WaitFor(2L);
			Mytools.WaitForElementToBeLoaded(obj.getProperty("systemClusterid1"));
			List<WebElement> allFormChildElements = LoginTest.oBrowser.findElements(By.xpath(obj.getProperty("ClustersinSystemTabpath")));
			Mytools.WaitFor(1L);
	     	System.out.println("Number of Cluster in System Tab is: " + allFormChildElements.size() );
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
	//--------------------------------------Creating Details of a Cluster on System Tab for Verification from Linux Box---------------------------------------
	@Test(priority=2)
	public void CreatingClusterDetailsInfo() 
	{
	 	 String errorMsg = "";
	 	 String sErrorString = "";
	 	 String SystemClusterDetailsCurl; 
	 	 Generic.setAuthorInfoForReports();

	         try
	         {	 
				int noOfClusters;
				Cluster ClusterDetails = new Cluster();
				WebElement oResult=null;
				WebElement oResult1=null;
				List<WebElement> allFormChildElements = LoginTest.oBrowser.findElements(By.xpath(obj.getProperty("ClustersinSystemTabpath")));
				noOfClusters = allFormChildElements.size();
				System.out.println("Creating Details of a Cluster on System Tab for Verification from Linux Box.");
	        	 
	        	 for(int i=1; i <= noOfClusters; i++) 
	        	 {
	        		 try
	        		 {
						ClusterDetails = new Cluster();
						String child = "div[" + i + "]";
						Mytools.WaitFor(1L);
						oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("systemClusterparentpath") + child + obj.getProperty("systemClustername") ));
						Generic.clickElement(obj.getProperty("systemClusterparentpath")+ child +"/div");
				        	 // Cluster ID from Cluster Card for specific cluster with clustername	        		 
			        	 try
			        	 {
							oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("systemClusterparentpath") + child + obj.getProperty("systemClusterid") ));
							sClusterID = oResult.getAttribute("textContent").trim();
							if (i==1)
							{
								System.out.println("Reading Cluster details present at the bottom in System Tab from Backend.");
								SystemClusterDetailsCurl = Generic.CurlCommandReplacer(linuxobj.getProperty("clusteridinSystab"));
								SystemClusterDetailsCurl = SystemClusterDetailsCurl.replace("clusterid", sClusterID);
								inputString = ReadingDataFromLinux.ReadClusterData(SystemClusterDetailsCurl);
							}
							System.out.println("Cluster ID : " + oResult.getAttribute("textContent"));
							ClusterDetails.setID(Integer.parseInt(sClusterID));
			        	 }
		        		 catch(Exception e)
		        		 {
		        			 System.out.println("Cluster ID does not exist.");
		        			 Reports.LogResult("Cluster ID does not exist.. Test Failed");
		        			 errorMsg = "Cluster ID does not exist.";
		        			 sErrorString = errorMsg; 
		        		 }
				        	 
				        	 // Cluster Name in details section at the buttom
				        	 try
				        	 {
			        		 	oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("SystemTabClusterName") ));
			        			 
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
			        		 // System Tab Cluster Status
			        		 try
			        		 {
				     		 	oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("SystemTabClusterStatus") ));
				     		 	Reports.LogResult("Cluster Status Exist  Test Passed");
				     		 	System.out.println("Cluster Status : " + oResult.getText());
				         		 
				     		 	ClusterDetails.setClusterStatus(oResult.getText().replace("Status:", "").trim());
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
			        			 oResult1 = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("SystemTabClusterDataOnDiskSpan")));
			        			 oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("SystemTabClusterDataOnDisk") ));
			        			 Reports.LogResult("Cluster Data On Disk Exist Test Passed");
			        			 System.out.println("Cluster Data On Disk : " + oResult.getText().replace(oResult1.getText(), ""));
			            		 
			        			 ClusterDetails.setGb(oResult.getText().replace("GB on disk", "").replace("B on disk", "").replace(oResult1.getText(), "").replace(" on Disk", "").trim());
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
			        			 oResult1 = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("SystemTabClusterNodesSpan")));
			        			 oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("SystemTabClusterNodes") ));
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
			        			 oResult1 = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("SystemTabClusterRunningJobsSpan")));
			        			 oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("SystemTabClusterRunningJobs") ));
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
			         		// System Tab Cluster Alert No
			        		 try
			        		 {
			        			 oResult1 = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("SystemTabClusterAlertSpan")));
			        			 oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("SystemTabClusterAlert") ));
			        			 Reports.LogResult("Cluster Alert No Exist Test Passed");
			        			 System.out.println("Cluster Alert No : " + oResult.getText().replace(oResult1.getText(), ""));
			        			 ClusterDetails.setAlerts(Integer.parseInt(oResult.getText().replace(oResult1.getText(), "").replace(" alerts", "").trim()));
			        		 }
			        		 catch(Exception e)
			        		 {
			        			 System.out.println("Cluster Alert No does not exist.");
			        			 Reports.LogResult(" Cluster Alert No does not exist..Test Failed");
			        			 errorMsg = "Cluster Alert No does not exist.";
			        			 sErrorString = sErrorString + ";" + errorMsg;
			        		 }
			         		// System Tab Cluster Description
			        		 try
			        		 {
			        			 oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("SystemTabClusterDesc") ));
			        			 Reports.LogResult("Cluster Description Exist Test Passed");
			        			 System.out.println("Cluster Description : " + oResult.getText());
			        			 ClusterDetails.setClusterDescripton(oResult.getText());
			        		 }
			        		 catch(Exception e)
			        		 {
			        			 System.out.println("Cluster Description does not exist.");
			        			 Reports.LogResult(" Cluster Description does not exist..Test Failed");
			        			 errorMsg = "Cluster Description does not exist.";
			        			 sErrorString = sErrorString + ";" + errorMsg;
			        		 }
			        		 Clusters.add(ClusterDetails);
		        		 
	        		 
	        		 
	        		 }
	        		 catch(Exception e)
	        		 {
	        			 System.out.println("Cluster Name does not exist.");
	        			 Reports.LogResult("Cluster"+i+  " Cluster Name does not exist.. Test Failed");
	        			 //blnFail=true;
	        			 errorMsg = "Cluster Name does not exist.";
	        			 sErrorString = errorMsg; 
	        		 }

	        		 
	        		 
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
	//--------------------------------------Verifying Cluster details present at the bottom in System Tab with Backend---------------------------------------
	@Test(priority=3)
	public void VerifyUIClusterDetailsWithBackend() 
	{
	  try
		{
			Generic.setAuthorInfoForReports();
			System.out.println("Verifying Cluster details present at the bottom in System Tab with Backend.");
			ClusterVerify.VerifyingClusterDetailsInSystemTab(Clusters, inputString);
			sClusterID = null;
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
	
	//--------------------------------------Verifying UI MoreInfo button And ChartHolder in System Tab---------------------------------------
		@Test(priority=4)
		public void VerifyUIMoreInfoAndChartHolder() 
		{
		  try
			{
			  //boolean bActions= Generic.CheckForElementVisibility(obj.getProperty("systemActions"));
			  Generic.setAuthorInfoForReports();
			  Mytools.WaitFor(1L);
			  boolean bMoreInfo = Generic.CheckForElementVisibility(obj.getProperty("moreinfo"));
			  boolean bChart = Generic.CheckForElementVisibility(obj.getProperty("SystemTabClusterChartsPlaceHolder"));
			  
			  if ((bMoreInfo == true) && (bChart == true))
			  {
				  ATUReports.add("Pass Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.PASSED, new CaptureScreen(
							ScreenshotOf.DESKTOP));
     			 System.out.println("MoreInfo button and ClusterChartsPlaceHolder exist in system tab page.");
			  }
			  else if (bMoreInfo == true) 
			  {
					ATUReports.add("Fail Test Case: " + Thread.currentThread().getStackTrace()[1].getMethodName() + " as SystemTabClusterChartsPlaceHolder is not available",LogAs.FAILED, new CaptureScreen(
							ScreenshotOf.DESKTOP));
	     			 System.out.println("MoreInfo button exist but ClusterChartsPlaceHolder doesn't in system tab page.");
				  
			  }
			  else if (bChart == true) 
			  {
					ATUReports.add("Fail Test Case: " + Thread.currentThread().getStackTrace()[1].getMethodName() + " as moreinfo is not available",LogAs.FAILED, new CaptureScreen(
							ScreenshotOf.DESKTOP));
	     			 System.out.println("ClusterChartsPlaceHolder exist but MoreInfo button doesn't in system tab page.");
				  
			  }
			  else
			  {
					ATUReports.add("Fail Test Case: " + Thread.currentThread().getStackTrace()[1].getMethodName() + " as moreinfo and SystemTabClusterChartsPlaceHolder both are not available",LogAs.FAILED, new CaptureScreen(
							ScreenshotOf.DESKTOP));
				  
			  }
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
