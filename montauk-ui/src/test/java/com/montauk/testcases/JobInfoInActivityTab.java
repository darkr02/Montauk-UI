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

import org.json.JSONObject;
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
import com.montauk.jobs.JobClass;
import com.montauk.jobs.JobVerify;
import com.montauk.login.LoginTest;
import com.montauk.reports.Reports;

/**
 * This class holds the Alerts Functionality in MonitorTab.
 * @author Krishnendu.Daripa
 */

public class JobInfoInActivityTab 
{
	
	private List<JobClass> jobArray = new ArrayList<JobClass>();
	Properties obj = new Properties();
	Properties linuxobj = new Properties();
	int noOfClusters;
	String clusterID;
	 int noOfJobs;
	 WebElement oResult=null;
	 
	{
		System.setProperty("atu.reporter.config", System.getProperty("user.dir") + "\\src\\com\\montauk\\objectrepo\\atu.properties");
	}
	//------------------------------Navigating to System Tab----------------------------------------
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
			
			
			
			
			Generic.clickElement(obj.getProperty("systemtab1"));
			
			
			Generic.clickElement(obj.getProperty("minimizesystemtray"));
			
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
	
	
	
	//------------------------
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
	        	 noOfClusters=( allFormChildElements.size())/2;
	        	 
	        	 
	       
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
	  public void GetClusterID() 
	  {
	 	 String errorMsg = "";
	 	 String sErrorString = "";
	 	
	 	 int i;
	         try
	         {	 
	        	
	        	 
	        	 Generic.setAuthorInfoForReports();
	        	 
	        	 
	        	 
	        	WebElement oResult=null;
	        	
	        	for ( i =1;i<=noOfClusters;i++)
	        		 
	        	{ 	
	        		
	        		String child="div[" + i + "]";
	        		
	        		
	        		
	        		oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("sysparentpath") + child + obj.getProperty("sysnamepath") ));
	        		
					
	        		Reports.LogResult("Cluster"+i+"Cluster Name Exist     Test Passed");

						System.out.println("ClusterName : " + oResult.getText());

						if (oResult.getText().equalsIgnoreCase(obj.getProperty("ClusterNamePadma")))

						{
							
							Generic.clickElement(obj.getProperty("sysparentpath") + child + obj.getProperty("sysnamepath"));
							
							System.out.println(By.xpath(obj.getProperty("sysparentpath") + child + obj.getProperty("clusterid") ));
							
							 oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("sysparentpath") + child + obj.getProperty("clusterid") ));

	                         Reports.LogResult("Cluster "+i+". Cluster ID Exist Test Passed");
	                         
	                         System.out.println("Cluster ID: " + oResult.getAttribute("textContent"));
	                         
	                         clusterID = (oResult.getAttribute("textContent")).trim();


						}
	        	} 
        		
	        	Generic.clickElement(obj.getProperty("systemmoreinfopath"));
	        	
	        	Mytools.WaitFor(3L);
	        	
	        	 Generic.clickElement(obj.getProperty("systemActivitypath"));
	        	
	        	 Mytools.WaitFor(5L);
	        	
	        		 
	         }
	        		 
	         catch(Exception e)

             {

                             System.out.println("Cluster ID  does not exist.");

                             errorMsg = "Cluster ID  does not exist.";

                             sErrorString = sErrorString + ";" + errorMsg;

             }            


	  }
	         
	  @Test(priority=4)
	         
	      	 
	         public void GetNoOfJobs()
	        
	  			{
	        		
	        		
	        		 
	        		 // Get the no of jobs        		 
	        		 
	        		 try
	    	         {
	        			 
	        			 
	        			 String inputString;

                         String ClusterDetailsCurl;

                         Generic.setAuthorInfoForReports();

                        
                         ClusterDetailsCurl = linuxobj.getProperty("getjobsofacluster");

                         ClusterDetailsCurl = ClusterDetailsCurl.replace("clusteridfromui", clusterID);

                         inputString = ReadingDataFromLinux.ReadClusterData(ClusterDetailsCurl);
	        			 
	    	        	 JSONObject json=new JSONObject(inputString);
	    	 	    	 
	    	        	 noOfJobs=(json.getJSONArray("jobinsts").length());
	    	        	 
	    	        	 GetTheJobInfoFromUI(noOfJobs);
	    	        	
	    	       
	    	         }
	    	         catch(Throwable e)
	    	         {
	    	               e.printStackTrace();
	    	               Assert.fail(e.getMessage());
	    	               ATUReports.add("Fail Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.FAILED, new CaptureScreen(
	    	            		   ScreenshotOf.DESKTOP));
	    	         }
	        		 
	        	 }
	        		 
	        		
	//-----------------------------------Get the JOB INFO from UI------------------------------------------------------------------------------        		 
	      	 
		         public void GetTheJobInfoFromUI(int noOfJobs)
		         {
                     String errorMsg = "";
	        		 
	        		 String sErrorString = "";
	        		 
	        		 int noOfJobsUI;
	        		 
	        		 boolean bFlag = false;
	        	 
	        	 try
	        	 {
	        		
	        		 if(noOfJobs>20)
	 	 	    	{
	 	 	    		noOfJobsUI=20;
	 	 	    		noOfJobs=noOfJobs-20;
	 	 	    		bFlag=true;
	 	 	    	}
	 	 	    	
	 	 	    	else
	 	 	    		noOfJobsUI=noOfJobs;
	        		 
	        		 
	        		 for(int i=1; i <= noOfJobsUI; i++)
	        		 {
	        		 String child = "div[" + i + "]";
	        		 
	        		 JobClass job = new JobClass();
	        		 
	        		 try
	        		 {
	        			
	        			 //System.out.println((obj.getProperty("systemjobparentpath") + child + obj.getProperty("systemjobpath") ));
	        			 Generic.clickElement(obj.getProperty("systemjobparentpath") + child + obj.getProperty("systemjobpath") );
	        			 
	        			 oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("jobdefinitionname")));
	        			 Reports.LogResult("Cluster"+i+"Job Definition name is present     Test Passed");
	        			 System.out.println("ClusterName : " + oResult.getText());
	            		 
	            		 job.setJobName(oResult.getText());
	        		 }
	        		 catch(Exception e)
	        		 {
	        			 System.out.println("Job Definition Name does not exist.");
	        			System.out.println(e.getMessage());
	        			e.printStackTrace();
	        			 Reports.LogResult("Cluster"+i+  " Job Definition Name does not exist.. Test Failed");
	        			 //blnFail=true;
	        			 errorMsg = "Job Definition Name does not exist.";
	        			 sErrorString = errorMsg; 
	        		 }
	        		 // jobinstanceid
	        		 try
	        		 {
	        			 oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("jobinstanceid")));
	        			 Reports.LogResult("Cluster"+i+"jobinstanceid is present     Test Passed");
	        			 int lastindex=oResult.getText().lastIndexOf( ' ' );
	        			 String id = oResult.getText().substring(lastindex);
	        			 System.out.println("jobinstanceid : " + id);
	            		 
	            		 job.setJobRefId(id);
	        		 }
	        		 catch(Exception e)
	        		 {
	        			 System.out.println("jobinstanceid does not exist.");
	        			System.out.println(e.getMessage());
	        			 Reports.LogResult("Cluster"+i+  " jobinstanceid does not exist.. Test Failed");
	        			 //blnFail=true;
	        			 errorMsg = "jobinstanceid does not exist.";
	        			 sErrorString = errorMsg; 
	        		 }
	        		 
	        		 
	        		 //jobstatus
	        		 try
	        		 {
	        			// System.out.println((obj.getProperty("systemjobparentpath") + child + obj.getProperty("systemjobpath") ));
	        			// Generic.clickElement(obj.getProperty("systemjobparentpath") + child + obj.getProperty("systemjobpath") );
	        			 oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("jobstatus")));
	        			 Reports.LogResult("Cluster"+i+"jobstatus is present     Test Passed");
	        			 System.out.println("jobstatus : " + oResult.getText());
	            		 
	            		 job.setjobStatus(oResult.getText());
	        		 }
	        		 catch(Exception e)
	        		 {
	        			 System.out.println("jobstatus does not exist.");
	        			System.out.println(e.getMessage());
	        			 Reports.LogResult("Cluster"+i+  " jobstatus does not exist.. Test Failed");
	        			 //blnFail=true;
	        			 errorMsg = "jobstatus does not exist.";
	        			 sErrorString = errorMsg; 
	        		 }
	        		 
	        		 //jobdescription
	        		 try
	        		 {
	        			// System.out.println((obj.getProperty("systemjobparentpath") + child + obj.getProperty("systemjobpath") ));
	        			 //Generic.clickElement(obj.getProperty("systemjobparentpath") + child + obj.getProperty("systemjobpath") );
	        			 oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("jobdescription")));
	        			 Reports.LogResult("Cluster"+i+"jobdescription is present     Test Passed");
	        			 System.out.println("jobdescription : " + oResult.getText());
	            		 
	            		 job.setjobDescription(oResult.getText());
	        		 }
	        		 catch(Exception e)
	        		 {
	        			 System.out.println("jobdescription does not exist.");
	        			System.out.println(e.getMessage());
	        			 Reports.LogResult("Cluster"+i+  " jobdescription does not exist.. Test Failed");
	        			 //blnFail=true;
	        			 errorMsg = "jobdescription does not exist.";
	        			 sErrorString = errorMsg; 
	        		 }
	        		 
	        		 
	        		 //jobduration
	        		 try
	        		 {
	        			
	        			 oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("jobduration")));
	        			 Reports.LogResult("Cluster"+i+"jobduration is present     Test Passed");
	        			 System.out.println("jobduration : " + oResult.getText());
	            		 
	            		 job.setjobDuration(oResult.getText());
	        		 }
	        		 catch(Exception e)
	        		 {
	        			 System.out.println("jobduration does not exist.");
	        			System.out.println(e.getMessage());
	        			 Reports.LogResult("Cluster"+i+  " jobduration does not exist.. Test Failed");
	        			 //blnFail=true;
	        			 errorMsg = "jobduration does not exist.";
	        			 sErrorString = errorMsg; 
	        		 }
	        		 jobArray.add(job);

	        	 
	        	 if(!errorMsg.isEmpty())
	        	 {
	        		 
	        		 //Reports.CaptureScreenShot("AfterClick.png");
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
	        
	         
	        
	        	 if(bFlag)
        		 {
        			 Generic.clickElement(obj.getProperty("activityjobspagenext"));
        			 GetTheJobInfoFromUI(noOfJobs); 
        			 
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
	//-------------------------------Reading Job data from Linux and verify with UI-------
	 
		         
		      
	@Test(priority=5)
	public void ReadJobInfo()
	{
		for (int i=0;i<noOfJobs;i++)
		{
		try
		{
			String inputString;
			Generic.setAuthorInfoForReports();
			String jobRefId = jobArray.get(i).getJobRefId().trim();
			inputString = ReadingDataFromLinux.ReadClusterData(linuxobj.getProperty("getjobdetailsbyrefid")+jobRefId);
			JobVerify.VerifyingJobDetails(jobArray, inputString,i);
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
		}

	
}
