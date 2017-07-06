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
import com.montauk.login.LoginTest;
import com.montauk.reports.Reports;

/**
 * This class holds the Jobs Functionality in System Tab, Jobs panel.
 * @author Krishnendu.Daripa
 */

public class AllJobsInSystemTab 
{


	private List<JobClass> jobArray = new ArrayList<JobClass>();
	static Properties obj = new Properties();
	int noOfClusters;
	String[] t;
	int noOfJobsDefs;
	boolean bFlag;
	String allJobRefsString;
	static Properties cred = new Properties();
	static Properties linux = new Properties();
	static String errorMsg = "";
	static String sErrorString = "";
	private static List<JobClass> Job = new ArrayList<JobClass>();

	{
		System.setProperty("atu.reporter.config", System.getProperty("user.dir") + "\\src\\main\\java\\com\\montauk\\objectrepo\\atu.properties");
	}
	//------------------------------Navigating to System Tab, then Jobs Panel----------------------------------------
	@Test(priority=1)
	public void GoingToSystemTabJobs()
	{
		try
		{
			Generic.setAuthorInfoForReports();
			FileInputStream objfile = new FileInputStream(System.getProperty("user.dir")
					+ "\\src\\main\\java\\com\\montauk\\objectrepo\\objectuimap.properties");
			FileInputStream credfile = new FileInputStream(System.getProperty("user.dir")
					+ "\\src\\main\\java\\com\\montauk\\objectrepo\\credentials.properties");
			FileInputStream linuxfile = new FileInputStream(System.getProperty("user.dir")
					+ "\\src\\main\\java\\com\\montauk\\objectrepo\\linuxmap.properties");
			obj.load(objfile);
			cred.load(credfile);
			linux.load(linuxfile);
			//----------------------Clicking on the System TAB---------------------------
			LoginTest.oBrowser.navigate().refresh();
			Mytools.WaitForElementToBeLoaded(obj.getProperty("systemtablink"));
			Generic.clickElement(obj.getProperty("systemtablink"));
			if(Generic.CheckForElementVisibility(obj.getProperty("minimizesystemtray")))
				Generic.clickElement(obj.getProperty("minimizesystemtray"));
			Generic.clickElement(obj.getProperty("systemTabJobsButton"));
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
	//------------------------------Getting the no. of jobs from backend----------------------------------------
	@Test(priority=2)
	public void NoOfAllJobs()
	{
		try
		{
			String allJobRefs = linux.getProperty("getAllJobDefs");
			allJobRefs=Generic.CurlCommandReplacer(allJobRefs);
			allJobRefsString=ReadingDataFromLinux.ReadClusterData(allJobRefs);

			JSONObject json=new JSONObject(allJobRefsString);
			noOfJobsDefs=(json.getJSONArray("jobrefs").length());
			System.out.println("Number of Job definitions in the backend = " +noOfJobsDefs);
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
	//----------------------------------------Getting the list of Job names from the UI--------------------------------------
	@Test(priority=3)
	public void AllJobNamesInUI()
	{

		bFlag=false;
		try
		{	 
			Generic.setAuthorInfoForReports();
			Job=GetListOfJobsFromJobsPanelInUI();
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
		catch(Throwable e)
		{
			e.printStackTrace();
			Assert.fail(e.getMessage());
			ATUReports.add("Fail Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.FAILED, new CaptureScreen(
					ScreenshotOf.DESKTOP));
		}
	}
	//-------------------------------Verifying the list of jobs from UI and backend-------
	@Test(priority=6)
	public void VerifyAllJobsName()
	{
		try
		{
			int j=1;
			Generic.setAuthorInfoForReports();
			Generic.clickElement(obj.getProperty("jobsPanelFilterJobs"));
			Generic.clickElement(obj.getProperty("JobsPanelFilterJobsAllJobs"));
			//			Mytools.WaitFor(8L);
			System.out.println("********************All JOBS FILTER ***********************");
			//			inputString = ReadingDataFromLinux.ReadClusterData(linuxobj.getProperty(""));
			JSONObject json=new JSONObject(allJobRefsString);
			int noOfJobs=(json.getJSONArray("jobrefs").length());
			VerifyingJobNames(allJobRefsString,noOfJobs,j);
			System.out.println("********************All JOBS are present***********************");
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

	//-------------------------------Verifying the list of OOZIE jobs from UI and backend-------

	@Test(priority=4)
	public void VerifyOozieJobsName()
	{
		try
		{
			String inputString;
			int j=1;
			Generic.setAuthorInfoForReports();
			System.out.println("***********************All OOZIE JOBS verification*****************************");
			String getooziejobs = linux.getProperty("getooziejobs");
			getooziejobs = Generic.CurlCommandReplacer(getooziejobs);
			inputString = ReadingDataFromLinux.ReadClusterData(getooziejobs);
			JSONObject json = new JSONObject(inputString);
			int noOfJobs = (json.getJSONArray("jobrefs").length());
			System.out.println("No of OOZIE JOBS = "+ noOfJobs);
			Generic.clickElement(obj.getProperty("jobsPanelFilterJobs"));
			Mytools.WaitFor(2L);
			Mytools.WaitForElementToBeLoaded(obj.getProperty("JobsPanelFilterJobsOozieJobs"));
			Generic.clickElement(obj.getProperty("JobsPanelFilterJobsOozieJobs"));
			VerifyingJobNames(inputString,noOfJobs,j);
			System.out.println("***********************All OOZIE JOBS are present*****************************");

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

	//	-------------------------------Verifying the list of HADOOP jobs from UI and backend-------
	@Test(priority=5)
	public void VerifyHadoopJobsName()
	{
		try
		{
			String inputString;
			int j=1;
			Generic.setAuthorInfoForReports();
			System.out.println("***********************All HADOOP JOBS verification*****************************");
			String gethadoopjobs=linux.getProperty("gethadoopjobs");
			gethadoopjobs=Generic.CurlCommandReplacer(gethadoopjobs);
			inputString = ReadingDataFromLinux.ReadClusterData(gethadoopjobs);
			JSONObject json=new JSONObject(inputString);
			int noOfJobs=(json.getJSONArray("jobrefs").length());
			System.out.println("No of HADOOP JOBS = "+noOfJobs);
			Generic.clickElement(obj.getProperty("jobsPanelFilterJobs"));
			Mytools.WaitFor(2L);
			Mytools.WaitForElementToBeLoaded(obj.getProperty("JobsPanelFilterJobsHadoopJobs"));
			Generic.clickElement(obj.getProperty("JobsPanelFilterJobsHadoopJobs"));

			VerifyingJobNames(inputString,noOfJobs,j);
			System.out.println("***********************All HADOOP JOBS are present*****************************");

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

	public static List<JobClass> GetListOfJobsFromJobsPanelInUI() throws Exception
	{
		int noOfPagesinJobsList=Integer.parseInt(LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("noOfPagesInJobList"))).getText().substring(2).trim());
		System.out.println("Total no of pages in Jobs Panel : " + noOfPagesinJobsList);
		int currentPageNo=Integer.parseInt(LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("currentPageNumberinJobsPanel"))).getAttribute("value"));
		int currentJobNo=1;
		List<JobClass> Jobs = new ArrayList<JobClass>();
		while(currentPageNo<=noOfPagesinJobsList)
		{
			System.out.println("Current Page No : "+currentPageNo);
			
			Mytools.WaitFor(1L);
			List<WebElement> allFormChildElements = LoginTest.oBrowser.findElements(By.xpath(obj.getProperty("jobsParentPathInJobsPanel") + "table"));
			int noOfJobs=allFormChildElements.size();
			System.out.println("Number of Jobs on current page is : " + noOfJobs);
			JobClass job = new JobClass();

			for(int i=1; i<=noOfJobs; i++)
			{
				job =new JobClass();
				System.out.println("\nFrom Job list , details of job no. " + currentJobNo);
				WebElement oResult=null;
				String childpath = "table[" + i + "]/tbody/tr/td/div";

				try
				{
					LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("jobsParentPathInJobsPanel") + childpath )).click();
//					LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("jobsParentPathInJobsPanel") + childpath ));
					oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("jobsParentPathInJobsPanel") + childpath ));
					job.setJobName(oResult.getText());
					Reports.LogResult("Job "+ currentJobNo +" Job Name Exist     Test Passed");
					System.out.println("JobName : " + oResult.getText());
					currentJobNo++;
				}
				catch(Exception e)
				{
					System.out.println("Job Name does not exist.");
					Reports.LogResult("Job"+i+  " Job Name does not exist.. Test Failed");
					errorMsg = "Job Name does not exist.";
				}

				if (Generic.CheckForElementVisibility(obj.getProperty("jobhistorytimeline"))==true)
				{
					System.out.println("jobhistorytimeline exist");
				}
				else
				{	System.out.println("jobhistorytimeline doesnt exist");
					errorMsg = "jobhistorytimeline doesnt exist";
					sErrorString = errorMsg;
				}

				if (Generic.CheckForElementVisibility(obj.getProperty("jobshistoryPreviousPanel"))==true)
				{
					System.out.println("previous exist");
				}
				else
				{
					System.out.println("previous doesnt exist");
					errorMsg = "previous doesnt exist";
					sErrorString = errorMsg;
				}
				Jobs.add(job);
			}
			Generic.clickElement(obj.getProperty("jobsPanelNextPageButton"));
			currentPageNo++;
		}
		return Jobs;
	}

	private void VerifyingJobNames(String inputString,int num,int j) 
	{
		String sErrorString = null;
		String errorMsg = "";
		int noOfJobs;
		boolean bFlag = false;

		Mytools.WaitFor(3L);
		try
		{	
			JSONObject json=new JSONObject(inputString);
			if(num>50)
			{
				noOfJobs=50;
				num=num-50;
				bFlag=true;
			}

			else
			{
				noOfJobs=num;

			}
			t = new String[noOfJobs];
			int noOfPagesinJobsList=Integer.parseInt(LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("noOfPagesInJobList"))).getText().substring(2).trim());
			System.out.println("Total no of pages in Jobs Panel : " + noOfPagesinJobsList);
			int currentPageNo=Integer.parseInt(LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("currentPageNumberinJobsPanel"))).getAttribute("value"));
			System.out.println("Current Page No : "+currentPageNo);

			for (int i=1;i<=noOfJobs;i++)

			{
				String child = "table[" + i + "]/tbody/tr/td/div";
				WebElement oResult=null;
				oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("jobsParentPathInJobsPanel") + child ));
				t[i-1]=oResult.getText();
				System.out.println("UI     :"+t[i-1]);
				System.out.println("BACKEND:"+json.getJSONArray("jobrefs").getJSONObject(j-1).getString("jobname"));
				if(t[i-1].equals(json.getJSONArray("jobrefs").getJSONObject(j-1).getString("jobname")))
				{
					System.out.println("Backend Data Matches with UI data");
					j++;
				}

				else
				{
					System.out.println("Backend Data doesnt Matches with UI data");
					errorMsg = "Backend Data doesnt Matches with UI data";
					sErrorString = errorMsg;
					j++;
				}


			}
			if(bFlag)
			{
				Generic.clickElement(obj.getProperty("jobsPanelNextPageButton"));
				VerifyingJobNames(inputString,num,j++);

			}

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


		catch(Throwable e)
		{
			e.printStackTrace();
			Assert.fail(e.getMessage());
			ATUReports.add("Fail Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.FAILED, new CaptureScreen(
					ScreenshotOf.DESKTOP));
		}

	}
}
