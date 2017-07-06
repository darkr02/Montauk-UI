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
 * This class holds the Alerts Functionality in MonitorTab.
 * @author Krishnendu.Daripa
 */

public class ClustersInMonitorTab 
{
	
	private List<Cluster> Clusters = new ArrayList<Cluster>();
	Properties obj = new Properties();
	Properties linuxobj = new Properties();
	private String inputString;
	private int nClusterID = 0; 
	private String sClusterID; 
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
			if (Generic.CheckForElementVisibility(obj.getProperty("minimizesystemtray")))
			{
				Generic.clickElement(obj.getProperty("minimizesystemtray"));
			}
			Mytools.WaitFor(1L);
			System.out.println("Going to Monitor Tab.");
			Generic.clickElement(obj.getProperty("monitortablink"));
			Mytools.WaitFor(1L);
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
	//------------------------------Getting the no. of clusters from UI----------------------------------------
	  @Test(priority=2)
	  public void NoOfClusters()
	  {
	         try
	         {
	        	 Generic.setAuthorInfoForReports();
	        	 Mytools.WaitForElementToBeLoaded(obj.getProperty("monitortabclusterid1"));
	        	 List<WebElement> allFormChildElements = LoginTest.oBrowser.findElements(By.xpath(obj.getProperty("clustersinmonitortab")));
	        	 System.out.println("\n Number of Clusters in Monitor Tab is: " + allFormChildElements.size() );
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
		//----------------------------------------Verifying the cluster information after clicking on the cluster on Monitor Tab UI--------------------------------------
	  @Test(priority=3)
	  public void VerifyClusterInfo()
	  {
	 	 String errorMsg = "";
	 	 String sErrorString = "";
	         try
	         {	 
	        	 int noOfClusters;
	        	 //Boolean blnFail=false;
	        	 Generic.setAuthorInfoForReports();
	        	 List<WebElement> allFormChildElements = LoginTest.oBrowser.findElements(By.xpath(obj.getProperty("clustersinmonitortab")));
	        	 noOfClusters = allFormChildElements.size();
	        	 System.out.println("Getting the cluster information after clicking on the cluster on Monitor Tab UI.");
	        	 
	        	 Cluster cluster = new Cluster();
	        	 
	        	 
	        	 for(int i=1; i <= noOfClusters; i++) 
	        	 {
	        		 cluster = new Cluster();
	        		 String child = "div[" + i + "]";
	        		 String ClusterDetailsCurl; 
	        		 String sGB = "";
	        		 Generic.clickElement(obj.getProperty("parentdivpath")+ child +"/div");
	        		 // ClusterName
	        		 WebElement oResult=null;
	        		 try
	        		 {
    	        		// Cluster ID
    	        		 try
    	        		 {
    	        			 oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentdivpath") + child + obj.getProperty("clusterid") ));
    	        			 Reports.LogResult("Cluster "+i+". Cluster ID Exist Test Passed");
    	        			 
    	        			 sClusterID = oResult.getAttribute("textContent").trim();
    	        			 nClusterID = Integer.parseInt(sClusterID);
    	        			 if (i == 1)
    	        			 {
	        					System.out.println("Reading Cluster data from Linux.");
	        					ClusterDetailsCurl = Generic.CurlCommandReplacer(linuxobj.getProperty("clusterid"));
        						ClusterDetailsCurl = ClusterDetailsCurl.replace("clusterid", sClusterID);
	        					inputString = ReadingDataFromLinux.ReadClusterData(ClusterDetailsCurl);
	        					System.out.println("Cluster data read is done successfully.");
    	        			 }
    	        			 System.out.println("Cluster ID: " + oResult.getAttribute("textContent"));
    	            		 cluster.setID(nClusterID);
    	        		 }
    	        		 catch(Exception e)
    	        		 {
    	        			 System.out.println("Cluster ID  does not exist.");
    	        			 Reports.LogResult("Cluster "+i+" Cluster ID does not exist.. Test Failed");
    	        			 errorMsg = "Cluster ID  does not exist.";
    	        			 sErrorString = sErrorString + ";" + errorMsg;
    	        		 }
    	        		 try
    	        		 {
    	        			 oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentdivpath") + child + obj.getProperty("clusternamepath") ));
    	        			 
    	        			 Reports.LogResult("Cluster"+i+"Cluster Name Exist     Test Passed");
    	        			 System.out.println(" \n");
    	        			 System.out.println("ClusterName : " + oResult.getText());
    	        			 cluster.setClusterName(oResult.getText());
    	        			 
    	        		 }
    	        		 catch(Exception e)
    	        		 {
    	        			 System.out.println("Cluster Name  does not exist.");
    	        			 Reports.LogResult("Cluster "+i+" Cluster Name does not exist.. Test Failed");
    	        			 errorMsg = "Cluster Name  does not exist.";
    	        			 sErrorString = sErrorString + ";" + errorMsg;
    	        		 }

	    	        		 // ClusterImage
	    	        		 try
	    	        		 {
	    		        		String imageUrl=LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentdivpath") + child + obj.getProperty("imagepath"))).getAttribute("src");  
	    		        		//System.out.println("Image source path : \n"+ imageUrl);  
	    		        		System.out.println("Details of the cluster: "+ oResult.getText());
	    		        		String URL = imageUrl;
	    		        		
	    		        		 
	    		     		    
	    		     		    int startIndex = URL.lastIndexOf( '/' );
	    		     		    
	    		     		    int endIndex =URL.lastIndexOf( '-' );
	    		     		    
	    		     		    System.out.println("Cluster Type: "+URL.substring(startIndex + 1, endIndex));
	    		     		    
	    		     		    cluster.setClusterType(URL.substring(startIndex + 1, endIndex));
	    	        		 }
	    	        		 
	    	        		 catch(Exception e)
	    	        		 {
	    	        			 System.out.println("Cluster Type does not exist.");
	    	        			 Reports.LogResult("Cluster"+i+"Cluster Type does not exist..Test Failed");
	    	        			 //blnFail=true;
	    	        			 errorMsg = "Cluster Type does not exist.";
	    	        			 sErrorString = sErrorString + ";" + errorMsg;
	    	        		 }
	    	        		     
	    	     		 
	    	        		 
	    	        		 // Cluster Status
	    	        		 try
	    	        		 {
	    	        			 oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentdivpath") + child + obj.getProperty("statuspath") ));
	    	        			 Reports.LogResult("Cluster"+i+"Cluster Status Exist Test Passed");
	    	        			 System.out.println("ClusterStatus : " + oResult.getText());
	    	            		 
	    	            		 cluster.setClusterStatus(oResult.getText());
	    	        		 }
	    	        		 catch(Exception e)
	    	        		 {
	    	        			 System.out.println("Cluster Status does not exist.");
	    	        			 Reports.LogResult("Cluster"+i+"Cluster Status does not exist..Test Failed");
	    	        			 //blnFail=true;
	    	        			 errorMsg = "Cluster Status does not exist.";
	    	        			 sErrorString = sErrorString + ";" + errorMsg;
	    	        		 }
	    	        		         		
	    	        	
	    	        		// Cluster Job
	    	        		 try
	    	        		 {
	    	        			 oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentdivpath") + child + obj.getProperty("jobpath") ));
	    	        			 Reports.LogResult("Cluster"+i+"Cluster Job Exist Test Passed");
	    	        			 System.out.println("ClusterJob : " + oResult.getText());
	    	            		 
	    	            		 cluster.setJobs(Integer.parseInt(oResult.getText()));
	    	        		 }
	    	        		 catch(Exception e)
	    	        		 {
	    	        			 System.out.println("Cluster Job does not exist.");
	    	        			 Reports.LogResult("Cluster"+i+" Cluster Job does not exist..Test Failed");
	    	        			 
	    	        			 //blnFail=true;
	    	        			 errorMsg = "Cluster Job does not exist.";
	    	        			 sErrorString = sErrorString + ";" + errorMsg;
	    	        		 }
	    	        		 
	    	        		// Cluster Nodes
	    	        		 try
	    	        		 {
	    	        			 oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentdivpath") + child + obj.getProperty("nodespath") ));
	    	        			 Reports.LogResult("Cluster"+i+"Cluster Nodes Exist Test Passed");
	    	        			 
	    	        			 System.out.println("ClusterNodes : " + oResult.getText());
	    	            		 
	    	            		 cluster.setNodes(Integer.parseInt(oResult.getText()));
	    	        		 }
	    	        		 catch(Exception e)
	    	        		 {
	    	        			 System.out.println("Cluster Nodes does not exist.");
	    	        			 Reports.LogResult("Cluster"+i+" Cluster Nodes does not exist..Test Failed");
	    	        			
	    	        			// blnFail=true;
	    	        			 errorMsg = "Cluster Nodes does not exist.";
	    	        			 sErrorString = sErrorString + ";" + errorMsg;
	    	        		 }
	    	        		 
	    	        		// Cluster gb
	    	        		 try
	    	        		 {
	    	        			 oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentdivpath") + child + obj.getProperty("gbpath") ));
	    	        			 Reports.LogResult("Cluster"+i+"Cluster GB Exist Test Passed");
	    	        			 
	    	        			 System.out.println("ClusterGB : " + oResult.getText());
	    	            		 sGB = oResult.getText();
	    	            		 cluster.setGb(oResult.getText());
	    	        		 }
	    	        		 catch(Exception e)
	    	        		 {
	    	        			 System.out.println("Cluster GB does not exist.");
	    	        			 Reports.LogResult("Cluster"+i+" Cluster GB does not exist..Test Failed");
	    	        			 
	    	        			// blnFail=true;
	    	        			 errorMsg = "Cluster GB does not exist.";
	    	        			 sErrorString = sErrorString + ";" + errorMsg;
	    	        		 }
	    	        		// Cluster Alerts
	    	        		 
	    	        		 try
	    	        		 {
	    	        			 oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentdivpath") + child + obj.getProperty("alertspath") ));
	    	        			 Reports.LogResult("Cluster"+i+"Cluster Alerts Exist Test Passed");
	    	        			
	    	        			 System.out.println("ClusterAlerts : " + oResult.getText());
	    	            		 
	    	            		 cluster.setAlerts(Integer.parseInt(oResult.getText()));
	    	        		 }
	    	        		 catch(Exception e)
	    	        		 {
	    	        			 System.out.println("Cluster Alerts does not exist.");
	    	        			 Reports.LogResult("Cluster"+i+" Cluster Alerts does not exist..Test Failed");
	    	        			 
	    	        			// blnFail=true;
	    	        			 errorMsg = "Cluster Alerts does not exist.";
	    	        			 sErrorString = sErrorString + ";" + errorMsg;
	    	        		 }
		    	        		// Cluster Disk Available
	    	        		 try
	    	        		 {
	    	        			 if (sGB.equals("0"))
	    	        			 {
		    	        			 oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentdivpath") + child + obj.getProperty("diskspaceavailablepath1") ));
	    	        			 }
	    	        			 else
	    	        			 {
		    	        			 oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentdivpath") + child + obj.getProperty("diskspaceavailablepath") ));
	    	        			 }
	    	        			 Reports.LogResult("Cluster"+i+"Cluster Available Exist Test Passed");
	    	        			 
	    	        			 System.out.println("Cluster Disk Available : " + oResult.getText());
	    	            		 
	    	            		 cluster.setDiskSpaceAvailable(oResult.getText());
	    	        		 }
	    	        		 catch(Exception e)
	    	        		 {
	    	        			 System.out.println("Cluster Disk Available does not exist.");
	    	        			 Reports.LogResult("Cluster"+i+" Cluster Disk Available does not exist.. Test Failed");
	    	        			 
	    	        			// blnFail=true;
	    	        			 errorMsg = "Cluster Disk Available does not exist.";
	    	        			 sErrorString = sErrorString + ";" + errorMsg;
	    	        		 }	 

	    	        		 
	    	        		// Cluster DiskSpaceUsed
	    	        		 try
	    	        		 {
	    	        			 oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentdivpath") + child + obj.getProperty("diskspaceusedpath") ));
	    	        			 Reports.LogResult("Cluster"+i+"Cluster DiskSpaceUsed Exist Test Passed");
	    	        			 
	    	        			 System.out.println("ClusterDiskSpaceUsed : " + oResult.getText());
	    	            		 
	    	            		 cluster.setDiskSpaceUsed(oResult.getText());
	    	        		 }
	    	        		 catch(Exception e)
	    	        		 {
	    	        			 System.out.println("Cluster DiskSpaceUsed does not exist.");
	    	        			 Reports.LogResult("Cluster"+i+" Cluster DiskSpaceUsed does not exist.. Test Failed");
	    	        			
	    	        			 //blnFail=true;
	    	        			 errorMsg = "Cluster DiskSpaceUsed does not exist.";
	    	        			 sErrorString = sErrorString + ";" + errorMsg;
	    	        		 }
	        		 }	 
	        		 catch(Exception e)
	        		 {
	        			 System.out.println("Cluster Name does not exist.");
	        			 Reports.LogResult("Cluster"+i+  " Cluster Name does not exist.. Test Failed");
	        			 //blnFail=true;
	        			 errorMsg = "Cluster Name does not exist.";
	        			 sErrorString = errorMsg; 
	        		 }
	        		 Clusters.add(cluster);
	        		 
	        	 }
	        	 
	        	 //System.out.println("Cluster Array List :" + Clusters.size());
	        	 

	        	 
	        	 if(!errorMsg.isEmpty())
	        	 {
	        		 
	        		 //Reports.CaptureScreenShot("AfterClick.png");
	        		 ATUReports.add("Fail Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName() + " for " +sErrorString,LogAs.FAILED, new CaptureScreen(
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
	//-------------------------------Reading Cluster data from Linux and verify with UI-------------------------------------------
	@Test(priority=4)
	public void ReadClusterData()
	{
		try
		{
			Generic.setAuthorInfoForReports();
			System.out.println("verify UI Cluster data with Linux.");
			ClusterVerify.VerifyingCluster(Clusters, inputString);
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

	public List<Cluster> getClusters() 
	{
		return Clusters;
	}
}
