package com.montauk.testcases;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
//import com.thoughtworks.selenium.webdriven.commands.SetTimeout;
import com.montauk.backend.ReadingDataFromLinux;
import com.montauk.generic.Generic;
import com.montauk.generic.Mytools;
import com.montauk.jobs.JobClass;
import com.montauk.login.LoginTest;
import com.montauk.reports.Reports;

public class RunningJobsinMonitorTab {

	WebElement oResult = null;
	static Properties obj = new Properties();
	static Properties cred = new Properties();
	static Properties linux = new Properties();
	String output;
	String jobRefId;
	String jobInfoOutput;
	String[] consoleOutput = new String[4];
	String[] consoleJobRefId = new String[]{"","","",""};
	String[] consolejobInfoOutput = new String[4];
	private static List<JobClass> Job = new ArrayList<JobClass>();
	private String clusterID;

	@BeforeTest
	public void AUT_Init() {
		try {
			//LoginTest.setup("chrome");
			FileInputStream objfile = new FileInputStream(
					System.getProperty("user.dir")
					+ "\\src\\main\\java\\com\\montauk\\objectrepo\\objectuimap.properties");
			FileInputStream credfile = new FileInputStream(
					System.getProperty("user.dir")
					+ "\\src\\main\\java\\com\\montauk\\objectrepo\\credentials.properties");
			FileInputStream linuxfile = new FileInputStream(
					System.getProperty("user.dir")
					+ "\\src\\main\\java\\com\\montauk\\objectrepo\\linuxmap.properties");
			obj.load(objfile);
			cred.load(credfile);
			linux.load(linuxfile);
			/*	LoginTest.Log_PageInfo();
			LoginTest.LoginToUI();*/
		}

		catch (Throwable e) {
			e.printStackTrace();
			Assert.fail("Login Failed");
		}

	}

	// ********************************************************************************************************************************************************//
	
	// UI-Navigate to monitor tab
	@Test(priority=1)
	public void GoingToMonitorTab()
	{
		try
		{
			//----------------------Clicking on the Monitor Tab---------------------------
			LoginTest.oBrowser.navigate().refresh();
			Generic.setAuthorInfoForReports();
			Mytools.WaitForElementToBeLoaded(obj.getProperty("monitortablink"));
			Generic.clickElement(obj.getProperty("monitortablink"));
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

	// ********************************************************************************************************************************************************//

	// When no Jobs are running on the UI
	@Test(priority=2 )
	public void JobsActivity() {

		try {

			Generic.setAuthorInfoForReports();
			String backendOutput = "{\"total\":0,\"jobinsts\":[],\"success\":true}";
			oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("nojobspath")));
			System.out.println("Message on UI : " + oResult.getText());
			if (oResult.getText().equals("No Data")) 
			{
				String getJobsDefList=linux.getProperty("listofrunningjobs");
				getJobsDefList=Generic.CurlCommandReplacer(getJobsDefList);
				output = ReadingDataFromLinux.ReadClusterData(getJobsDefList);
				if (output.equals(backendOutput))
					System.out.println("No jobs in RUNNING state");
				ATUReports.add("Pass Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.PASSED, new CaptureScreen(
						ScreenshotOf.DESKTOP));
			}
		}

		catch (Throwable e) {
			e.printStackTrace();
			Assert.fail("Jobs Activity Error Message not displayed !!");
			ATUReports.add("Fail Test Case: " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.FAILED, new CaptureScreen(
					ScreenshotOf.DESKTOP));
		}
	}

	// ********************************************************************************************************************************************************//
	// To register and start a JOB

		@Test(priority=3 )

	public void RegisterAndStartJob()
	{
		Generic.setAuthorInfoForReports();
		System.out.println("Register and start Job initialising\n");
		try
		{	 
			//			Mytools.WaitFor(10L);

			String errorMsg = "";

			String sErrorString = "";

			int noOfClusters;
			Generic.setAuthorInfoForReports();

			//Boolean blnFail=false;
			Generic.clickElement(obj.getProperty("monitortablink"));
			//Mytools.WaitFor(3L);
			if(Generic.CheckForElementVisibility(obj.getProperty("minimizesystemtray")))
				Generic.clickElement(obj.getProperty("minimizesystemtray"));

			List<WebElement> allFormChildElements = LoginTest.oBrowser.findElements(By.xpath(obj.getProperty("clustersinmonitortab")));

			noOfClusters = allFormChildElements.size();
			System.out.println("No of clusters in the Monitor Tab : "+noOfClusters);
			int i=1;

			for(i=1; i <= noOfClusters; i++)
			{
				String child = "div[" + i + "]";
//				Generic.clickElement(obj.getProperty("parentdivpath")+ child +"/div");
				WebElement oResult=null;
				try
				{
					oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentdivpath") + child + obj.getProperty("clusternamepath") ));
					Reports.LogResult("Cluster"+i+"Cluster Name Exist     Test Passed");
					System.out.println("ClusterName : " + oResult.getText());
					/*if (oResult.getText().equalsIgnoreCase(obj.getProperty("ClusterNamePadma")))
					{
						oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("parentdivpath") + child + obj.getProperty("clusterid") ));
						Reports.LogResult("Cluster "+i+". Cluster ID Exist Test Passed");
						System.out.println("Cluster ID: " + oResult.getAttribute("textContent"));
						clusterID = oResult.getAttribute("textContent");
					}*/
				}
				catch(Exception e)
				{
					System.out.println("Cluster ID does not exist.");
					Reports.LogResult("Cluster"+i+"Cluster ID does not exist..Test Failed");
					//blnFail=true;
					errorMsg = "Cluster ID does not exist.";
					sErrorString = sErrorString + ";" + errorMsg;
				}
			}
			//			String child = "div[" + i + "]";
			//Click element to unselect the last clicked cluster
			//Generic.clickElement(obj.getProperty("parentdivpath")+ "div[" + (i-1) + "]" +"/div");

			String registerjob=linux.getProperty("registerjob1");
			registerjob=Generic.CurlCommandReplacer(registerjob);
			output=ReadingDataFromLinux.ReadClusterData(registerjob);
			System.out.println("Details of the Registered Job: "+output);
			jobRefId=GetJobInstanceID(output);
			System.out.println(jobRefId);
			ATUReports.add("Pass Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.PASSED, new CaptureScreen(
					ScreenshotOf.DESKTOP));
		}

		catch(Throwable e)
		{
			e.printStackTrace();
			Assert.fail("Job is not registered !!");
			ATUReports.add("Fail Test Case: " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.FAILED, new CaptureScreen(
					ScreenshotOf.DESKTOP));
		}
	}

	// ********************************************************************************************************************************************************//
	// Check the Status of the JOB register

		@Test(priority = 4)
	public void CheckForRunningStatus() {

		try {
			System.out.println("\n Checking for Running status\nJob ID checked : " + jobRefId);
			String getjobdetailsbyRefid=linux.getProperty("getjobdetailsbyRefid");
			getjobdetailsbyRefid=Generic.CurlCommandReplacer(getjobdetailsbyRefid);
			getjobdetailsbyRefid=getjobdetailsbyRefid+"/"+jobRefId;
			output = ReadingDataFromLinux.ReadClusterData(getjobdetailsbyRefid);
			System.out.println(output);
			JSONObject tempJsonObj = new JSONObject(output);

			// tempJsonObj=(JSONObject) tempJsonObj.get("jobinst");
			// System.out.println(tempJsonObj.get("status"));
			System.out.println(tempJsonObj.getJSONObject("jobinst").getString("status"));

			long timeoutExpiredMs = System.currentTimeMillis() + 120000;

			while (!(tempJsonObj.getJSONObject("jobinst").getString("status")
					.equals("RUNNING"))) {

				output = ReadingDataFromLinux.GetJobDetails(getjobdetailsbyRefid, jobRefId);
				tempJsonObj = new JSONObject(output);

				long timeleft = timeoutExpiredMs - System.currentTimeMillis();
				if ((System.currentTimeMillis() >= timeleft)) {
					System.out.println(tempJsonObj.getJSONObject("jobinst").getString(
							"status"));
					break;
				} else {
					System.out.println("test"
							+ tempJsonObj.getJSONObject("jobinst").getString("status"));
					continue;
				}
			}
			ATUReports.add("Pass Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.PASSED, new CaptureScreen(
					ScreenshotOf.DESKTOP));
		}

		catch (Throwable e) {
			e.printStackTrace();
			Assert.fail("Job not captured during the RUNNING state");
			ATUReports.add("Fail Test Case: " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.FAILED, new CaptureScreen(
					ScreenshotOf.DESKTOP));
		}
	}

	// ********************************************************************************************************************************************************//
	// Get the Information of the registered JOB

		@Test(priority = 5)
	public void GetJobInfo() {

		System.out.println("\nGet Job Info started");

		try {
			System.out.println("Getting info of job started by using refID");
			//			Mytools.WaitFor(40L);
			String infoOfRegisteredJob=linux.getProperty("getjobdetailsbyRefid");
			infoOfRegisteredJob=Generic.CurlCommandReplacer(infoOfRegisteredJob);
			infoOfRegisteredJob=infoOfRegisteredJob+"/"+jobRefId;
			jobInfoOutput = ReadingDataFromLinux.ReadClusterData(infoOfRegisteredJob);
			System.out.println("Details of the Job Started" + jobInfoOutput);
			ATUReports.add("Pass Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.PASSED, new CaptureScreen(
					ScreenshotOf.DESKTOP));

		}

		catch (Throwable e) {
			e.printStackTrace();
			Assert.fail("JOB Reference ID is not obtained !!");
			ATUReports.add("Fail Test Case: " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.FAILED, new CaptureScreen(
					ScreenshotOf.DESKTOP));
		}
	}

	// ********************************************************************************************************************************************************//
	// Function to get the JOBREF ID of the registered job

	private String GetJobInstanceID(String curlOutput)

	{
		String jobId;
		int startIndex = curlOutput.lastIndexOf('[');
		int endIndex = curlOutput.lastIndexOf(']');
		jobId = curlOutput.substring(startIndex + 2, endIndex - 1);
		return jobId;
	}

	// ********************************************************************************************************************************************************//
	// Get the INFO of the RUNNING jobs in the UI

		@Test(priority = 6)
	public void VerifyJobActivityFromUI()
	{
		//		String errorMsg="";
		System.out.println("\nVerify JobActivity From UI Started");
		try {
			Generic.clickElement(obj.getProperty("monitortablink"));
			Job= GetListOfJobsFromActivityPanelInUI();
			ATUReports.add("Pass Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.PASSED, new CaptureScreen(
					ScreenshotOf.DESKTOP));

		}
		catch (Throwable e) {
			e.printStackTrace();
			Assert.fail("Job Details are not displayed");
			ATUReports.add("Fail Test Case: " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.FAILED, new CaptureScreen(
					ScreenshotOf.DESKTOP));
		}	

	}

	//--------------------------------------------------
	public static List<JobClass> GetListOfJobsFromActivityPanelInUI() throws Exception
	{
		System.out.println("Getting INFO of RUNNING jobs from UI");
		Mytools.WaitFor(1L);
		Mytools.WaitForElementToBeLoaded(obj.getProperty("runningjobsPanelRefreshbutton"));
		Generic.clickElement(obj.getProperty("runningjobsPanelRefreshbutton"));
		Mytools.WaitFor(1L);
		int noOfPagesinActivityPanel=Integer.parseInt(LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("noOfPagesinActivityPanel"))).getText().substring(2).trim());
		System.out.println("Total no of pages in Activity Panel : " + noOfPagesinActivityPanel);
		int currentPageNo=1;
		int i;
		String errorMsg="";

		List<JobClass> Jobs = new ArrayList<JobClass>();
		while(currentPageNo<=noOfPagesinActivityPanel)
		{
			System.out.println("Current Page No : "+currentPageNo);
			int nCounter = 0;
			do
			{
				if (Generic.CheckForElementVisibility(obj.getProperty("jobsinActivitypanel"))==true)
				{
					//					System.out.println("Able to view jobsinActivityPanel");
					break; 
				}
				else
				{
					nCounter++;
					Mytools.WaitFor(1L);
				}
			}while( nCounter < 20 );

			Mytools.WaitFor(1L);
			List<WebElement> allFormChildElements = LoginTest.oBrowser.findElements(By.xpath(obj.getProperty("jobsinActivitypanel")));
			int noOfJobs=allFormChildElements.size();
			System.out.println("Number of Jobs on current page is : " + noOfJobs);
			JobClass job = new JobClass();

			for(i=1; i<=noOfJobs; i++)
			{
				job =new JobClass();
				System.out.println("\nFrom Job card " + i);
				WebElement oResult=null;
				String childpath = "table[" + i + "]/tbody";
				LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("jobsparentpath") + childpath +obj.getProperty("jobcardinActivitypanel"))).click();

				try
				{
					oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("jobsparentpath") + childpath + obj.getProperty("jobcardNamepath") ));
					job.setJobName(oResult.getText());
					Reports.LogResult("Job "+i+" Job Name Exist     Test Passed");
					System.out.println("JobName : " + oResult.getText());

				}
				catch(Exception e)
				{
					System.out.println("Job Name does not exist.");
					Reports.LogResult("Job "+i+  " Job Name does not exist.. Test Failed");
					//					blnFail=true;
					errorMsg = "Job Name does not exist.";
					//					sErrorString = errorMsg; 
				}

				try
				{
					oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("jobsparentpath") + childpath + obj.getProperty("jobcardCompletionPercentage") ));
					job.setCompletionPercent(oResult.getText());
					Reports.LogResult("Job "+i+" Job Completion Percentage Exist     Test Passed");
					System.out.println("JobCompletion Percent : " + oResult.getText());

				}
				catch(Exception e)
				{
					System.out.println("Job Completion Percentage does not exist.");
					Reports.LogResult("Job"+i+  " Job Completion Percentage does not exist.. Test Failed");
					//					blnFail=true;
					errorMsg = "Job Completion Percentage does not exist.";
					//					sErrorString = errorMsg; 
				}


				try
				{
					oResult = LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("jobsparentpath") + childpath + obj.getProperty("jobcardJobInstanceID")));
					job.setJobRefId(oResult.getText());
					Reports.LogResult("Job "+i+" Job instance ID Exist     Test Passed");
					System.out.println("Job instance ID : " + oResult.getText());

				}
				catch(Exception e)
				{
					System.out.println("Job instance ID does not exist.");
					Reports.LogResult("Job"+i+  " Job instance ID does not exist.. Test Failed");
					//					blnFail=true;
					errorMsg = "Job instance ID does not exist.";
					//					sErrorString = errorMsg; 
				}

				Jobs.add(job);

			}
			Generic.clickElement(obj.getProperty("activityPanelNextPageButton"));
			currentPageNo++;
		}
		return Jobs;

	}



	// //--------------------------------Quitting the Browser
	// object-----------------------------------------
	//@AfterTest
	public void AUT_End() {
		try {
			LoginTest.oBrowser.quit();
			// AutoLogin_End();
		} catch (Throwable e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}

	// ******************************Store the output from UI in Arraylist
	/*	public static void readUIString(String jobUI) {
		try {

			String delim = "\n";
			String[] tokens = jobUI.split(delim);
			JobClass job = new JobClass();
			String type = tokens[0].substring(tokens[0].lastIndexOf(":") + 1);
			job.setjobType(type);
			String status = tokens[1].substring(tokens[1].lastIndexOf(" ") + 1);
			job.setjobStatus(status);
			job.setJobName(tokens[2]);
			job.setJobRefId(tokens[3]);
			job.setCompletionPercent(tokens[5]);

			Job.add(job);

		} catch (Throwable e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	}
	 */
	// ********************************************************************************************************************************************************//
	
	
	// Verify the Backend and UI Data
		@Test(priority = 7)
	public void JobsVerifyUIandBackend() {

		System.out.println("\nVerification of job from UI and backend intialising");
		try {
			JSONObject tempJsonObj = new JSONObject(output);
			System.out.println("Output" + output);
			int i=0, compareCountFlag=0;
			while(i<Job.size()){

				if (Job.get(i).getJobName()
						.equals(tempJsonObj.getJSONObject("jobinst").getString("jobname")) ) {
					compareCountFlag+=1;
					System.out.println("JobName Passed !!");
				}

				if (Job.get(i).getJobRefId()
						.equals(tempJsonObj.getJSONObject("jobinst").getString("id")) ) {
					compareCountFlag+=1;
					System.out.println("JobRefID Passed !!");
				}
				i++;
				//			NOT COMPARING COMPLETION PERCENTAGE AS IT VARIES OVER JOB RUN TIME
			}
			if (compareCountFlag!=2)
			{	
				System.out.println("Job match Failed !!\nRegistered job not found in UI");
			}
			ATUReports.add("Pass Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.PASSED, new CaptureScreen(
					ScreenshotOf.DESKTOP));
		} 
		catch (Throwable e) {
			e.printStackTrace();
			Assert.fail("UI data didnt match with the backend Data !!");
			ATUReports.add("Fail Test Case: " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.FAILED, new CaptureScreen(
					ScreenshotOf.DESKTOP));
		}

	}

	// ********************************************************************************************************************************************************//
	// Check whether the job is displayed in the job activity panel when the
	// status has reached from RUNNING to SUCCEEDED

		@Test(priority = 8)
	public void VerifyJobSumbitted() {

		System.out.println("\nVerification whether job goes to a final state initialising");

		try {
			System.out.println(jobRefId);
			String getjobstatus=Generic.CurlCommandReplacer(linux.getProperty("getjobdetailsbyRefid"));
			getjobstatus=getjobstatus+"/"+jobRefId;
			output = ReadingDataFromLinux.ReadClusterData(getjobstatus);
			System.out.println(output);
			JSONObject tempJsonObj = new JSONObject(output);

			// tempJsonObj=(JSONObject) tempJsonObj.get("jobinst");
			// System.out.println(tempJsonObj.get("status"));
			System.out.println(tempJsonObj.getJSONObject("jobinst").getString("status"));

			if ((tempJsonObj.getJSONObject("jobinst").getString("status")
					.equals("SUCCEEDED"))
					|| (tempJsonObj.getJSONObject("jobinst").getString("status")
							.equals("KILLED "))
							|| (tempJsonObj.getJSONObject("jobinst").getString("status")
									.equals("FAILED"))) {
				System.out.println(tempJsonObj.getJSONObject("jobinst").getString(
						"status"));
				Generic.clickElement(obj.getProperty("monitortablink"));
				Mytools.WaitForElementToBeLoaded(obj.getProperty("runningjobsPanelRefreshbutton"));
				Generic.clickElement(obj.getProperty("runningjobsPanelRefreshbutton"));
				oResult = LoginTest.oBrowser.findElement(By.xpath(obj
						.getProperty("nojobsinActivitypanelpath")));
				System.out.println("Message on UI " + " \" " + oResult.getText() + " \" ");

				/*	if (oResult.getText().equals(
						"There are currently no jobs for this system.")) {
					System.out
							.println("No Jobs are displayed in the UI when thr are no jobs running");
				}*/

				if (oResult.getText().equals("No Data"))
					System.out.println("No job displayed in the Running Jobs panel when there is no job running");

				// CHECK IF NEEDED	
				/*	else
					System.out
					.println("Job is displayed in UI even when there is no job running in the cluster");*/
			}

			else {
				System.out.println(tempJsonObj.getJSONObject("jobinst").getString("status"));

			}

			while ((tempJsonObj.getJSONObject("jobinst").getString("status")
					.equals("RUNNING"))) {
				output = ReadingDataFromLinux.ReadClusterData(getjobstatus);
				tempJsonObj = new JSONObject(output);

				if ((tempJsonObj.getJSONObject("jobinst").getString("status")
						.equals("SUCCEDED"))
						|| (tempJsonObj.getJSONObject("jobinst").getString("status")
								.equals("KILLED "))
								|| (tempJsonObj.getJSONObject("jobinst").getString("status")
										.equals("FAILED"))) {
					System.out.println(tempJsonObj.getJSONObject("jobinst").getString(
							"status"));
					//					LoginTest.oBrowser.navigate().refresh();
					Generic.clickElement(obj.getProperty("monitortab"));
					Mytools.WaitForElementToBeLoaded(obj.getProperty("runningjobsPanelRefreshbutton"));
					Generic.clickElement(obj.getProperty("runningjobsPanelRefreshbutton"));
					oResult = LoginTest.oBrowser.findElement(By.xpath(obj
							.getProperty("nojobsinActivitypanelpath")));
					System.out.println("Message on UI "+ oResult.getText());

					if (oResult.getText().equals("No Data"))
						System.out.println("No job displayed in the Running Jobs panel when there is no job running");

					//CHECK IF NEEDED
					/*else
						System.out
						.println("Job is displayed in UI even when there is no job running in the cluster");*/
				}

				else {
					System.out.println(tempJsonObj.getJSONObject("jobinst").getString("status"));
					continue;
				}
			}
			ATUReports.add("Pass Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.PASSED, new CaptureScreen(
					ScreenshotOf.DESKTOP));
		}

		catch (Throwable e) {
			e.printStackTrace();
			Assert.fail("RUNNING job did not attend the state SUCCEEDDED OR KILLED OR FAILED !!");
			ATUReports.add("Fail Test Case: " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.FAILED, new CaptureScreen(
					ScreenshotOf.DESKTOP));
		}

	}

	// ********************************************************************************************************************************************************//
	// Register multiple jobs on multiple clusters
	@Test(priority = 9)
	public void RegisterAndStartMultipleJobs() {

		try {
			System.out.println("\nRegister and Run multiple jobs initialising");
			String[] consoleOutput = new String[4];
			for (int i = 0; i < 2; i++) 
			{

				String temp = "registerjob" + (i + 1);
				temp=Generic.CurlCommandReplacer(linux.getProperty(temp));
				consoleOutput[i] = ReadingDataFromLinux.ReadClusterData(temp);
				System.out.println("\n" + temp);

				System.out.println("Details of the Registered Job" + (i + 1) + " : " + consoleOutput[i]);
				consoleJobRefId[i] = GetJobInstanceID(consoleOutput[i]);
				System.out.println(consoleJobRefId[i]);
			}
		
			ATUReports.add("Pass Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.PASSED, new CaptureScreen(
					ScreenshotOf.DESKTOP));
		}

		catch (Throwable e) {
			e.printStackTrace();
			Assert.fail("Multiple Jobs failed to register and run !!");
			ATUReports.add("Fail Test Case: " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.FAILED, new CaptureScreen(
					ScreenshotOf.DESKTOP));
		}
	}

	// ********************************************************************************************************************************************************//
	// Check For Running Status for Multiple Jobs

	@Test(priority = 10)
	public void CheckForRunningStatusMulipleJobs() {

		System.out.println("\nCheck RUNNING status for Multiple Jobs initialising");
		try {
			for (int i = 0; i < 2; i++) 
			{
				System.out.println("Checking for registered job no. : " + (i+1));
				System.out.println(consoleJobRefId[i]);
				String getjobdetailsbyRefid=linux.getProperty("getjobdetailsbyRefid");
				getjobdetailsbyRefid=Generic.CurlCommandReplacer(getjobdetailsbyRefid);
				getjobdetailsbyRefid=getjobdetailsbyRefid+"/"+consoleJobRefId[i];
				output = ReadingDataFromLinux.ReadClusterData(getjobdetailsbyRefid);
				System.out.println(output);
				JSONObject tempJsonObj = new JSONObject(output);

				// tempJsonObj=(JSONObject) tempJsonObj.get("jobinst");
				// System.out.println(tempJsonObj.get("status"));
				// System.out.println(tempJsonObj.getJSONObject("jobinst").getString("status"));

				long timeoutExpiredMs = System.currentTimeMillis() + 120000;

				while (!(tempJsonObj.getJSONObject("jobinst").getString("status")
						.equals("RUNNING"))) 
				{
					output = ReadingDataFromLinux.GetJobDetails(getjobdetailsbyRefid,consoleJobRefId[i]);
					tempJsonObj = new JSONObject(output);

					long timeleft = timeoutExpiredMs - System.currentTimeMillis();
					if ((System.currentTimeMillis() >= timeleft)) 
					{
						//System.out.println(tempJsonObj.getJSONObject("jobinst").getString("status"));
						break;
					} else {
						//System.out.println("test"+tempJsonObj.getJSONObject("jobinst").getString("status"));
						continue;
					}
				}
			}
			ATUReports.add("Pass Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.PASSED, new CaptureScreen(
					ScreenshotOf.DESKTOP));
		}

		catch (Throwable e) {
			e.printStackTrace();
			Assert.fail("JOB Status RUNNING is not obtained !!");
			ATUReports.add("Fail Test Case: " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.FAILED, new CaptureScreen(
					ScreenshotOf.DESKTOP));
		}
	}

	// ********************************************************************************************************************************************************//
	// Get the INFO of the registered jobs
	@Test(priority = 11)
	public void GetJobInfoForMultipleJobsFromBackendOnly() {

		try {
			for (int i = 0; i < 2; i++) {
				//				Mytools.WaitFor(40L);
				String getjobdetailsbyRefid=linux.getProperty("getjobdetailsbyRefid");
				getjobdetailsbyRefid=Generic.CurlCommandReplacer(getjobdetailsbyRefid);
				getjobdetailsbyRefid=getjobdetailsbyRefid+"/"+consoleJobRefId[i];
				consolejobInfoOutput[i] = ReadingDataFromLinux.ReadClusterData(getjobdetailsbyRefid);
				System.out.println("Details of the Job Started " + (i + 1)+ consolejobInfoOutput[i]);
				ATUReports.add("Pass Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.PASSED, new CaptureScreen(
						ScreenshotOf.DESKTOP));
			}
		}

		catch (Throwable e) {
			e.printStackTrace();
			Assert.fail("JOBs Details is not obtained !!");
			ATUReports.add("Fail Test Case: " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.FAILED, new CaptureScreen(
					ScreenshotOf.DESKTOP));
		}
	}

	// ********************************************************************************************************************************************************//
	// Get the INFO of the jobs on a different clusters-UI

	@Test(priority = 12)
	public void VerifyJobActivityFromUIOnlyForMultipleJobs()

	{
		System.out.println("\nGet info of multiple jobs from the UI Starting");
		try {
			Generic.clickElement(obj.getProperty("monitortablink"));
			Mytools.WaitForElementToBeLoaded(obj.getProperty("runningjobsPanelRefreshbutton"));
			Generic.clickElement(obj.getProperty("runningjobsPanelRefreshbutton"));
			Job=GetListOfJobsFromActivityPanelInUI();
			ATUReports.add("Pass Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.PASSED, new CaptureScreen(
					ScreenshotOf.DESKTOP));
		}

		catch (Throwable e) {
			e.printStackTrace();
			Assert.fail("Jobs Details are not displayed for multiple clusters");
			ATUReports.add("Fail Test Case: " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.FAILED, new CaptureScreen(
					ScreenshotOf.DESKTOP));
		}

	}

	// ********************************************************************************************************************************************************//
	// Verify the BACKEND data with the UI
	@Test(priority = 13)
	public void JobsVerifyUIWithBackendForMultipleJobs() {

		try {
			System.out.println("\nVerification of job from UI and backend for Multiple jobs intialising");
			int jobFlagCount=0;
			for (int j = 0; j < 2; j++) {
				JSONObject tempJsonObj = new JSONObject(consolejobInfoOutput[Job.size()-j-1]);
				System.out.println("Output " + consolejobInfoOutput[Job.size()-j-1]);
				int compareCountFlag=0;

					if (Job.get(j).getJobName()
							.equals(tempJsonObj.getJSONObject("jobinst").getString("jobname")) ) 
					{
						compareCountFlag++;
						System.out.println("JobName match Passed !!");
					}
					else
					{
						System.out.println();
					}

					if (Job.get(j).getJobRefId()
							.equals(tempJsonObj.getJSONObject("jobinst").getString("id")) ) 
					{
						compareCountFlag++;
						System.out.println("JobRefID match Passed !!");
					}
					//			NOT COMPARING COMPLETION PERCENTAGE AS IT VARIES OVER JOB RUN TIME
					if (compareCountFlag==2)
					{
						jobFlagCount++;
						System.out.println("Job match Passed !!\nRegistered job no " + (Job.size()-j) +" was found in UI");
					}
					else
					{	
						System.out.println("Job match Failed !!\nRegistered job no " + (Job.size()-j) +" NOT found in UI");
					}
				}
			if(jobFlagCount==2)
			{
				System.out.println("Job match PASSED !!\nRegistered number of jobs same as number of jobs found in UI");
			}
			else if(jobFlagCount!=2)
			{
				System.out.println("Job match FAILED !!\nRegistered number of jobs not same as number of jobs found in UI");
			}
			
			ATUReports.add("Pass Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.PASSED, new CaptureScreen(
					ScreenshotOf.DESKTOP));
		}
		catch (Throwable e) {
			e.printStackTrace();
			Assert.fail("UI data didnt match with the backend Data !!");
			ATUReports.add("Fail Test Case: " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.FAILED, new CaptureScreen(
					ScreenshotOf.DESKTOP));
		}

	}

	// ********************************************************************************************************************************************************//
	// Get the job details of a particular registered cluster
	@Test(priority=14)

	public void GetJobsFromUIForParticularCluster()

	{
		try {
			System.out.println("\nGet Jobs from UI for a particular cluster starting");
			Mytools.WaitForElementToBeLoaded(obj.getProperty("monitortabclusterid1"));
			Generic.clickElement(obj.getProperty("monitortabclusterid1"));
			Mytools.WaitForElementToBeLoaded(obj.getProperty("runningjobsPanelRefreshbutton"));
			
			Generic.clickElement(obj.getProperty("runningjobsPanelRefreshbutton"));

			List<WebElement> allFormChildElements = LoginTest.oBrowser.findElements(By.xpath(obj.getProperty("clustersinmonitortab")));
			int noOfClusters= allFormChildElements.size();
			System.out.println("\n Number of Clusters in Monitor Tab is: " + noOfClusters);
			
			// FOLLOWING IS THE CODE TO RANDOMIZE THE CLUSTER SELECTION
			// COMMENT AND UN COMMENT THE APPROPRIATE LINES FOR ENABLE IT
			
			//			int selectRandomCluster = (int)Math.ceil(Math.random()* noOfClusters);
			//			String child = "[" + selectRandomCluster + "]";
			//			WebElement randomCluster =LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("clustersinmonitortab") + child));
			WebElement randomCluster =LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("clustersinmonitortab") + "[1]"));
			randomCluster.click();
			//			Generic.clickElement(obj.getProperty("monitortabclusterid1"));
			//			String nameOfSelectedCluster=LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("clustersinmonitortab") + child + obj.getProperty("clusternamepath"))	).getText();
			String nameOfSelectedCluster=LoginTest.oBrowser.findElement(By.xpath(obj.getProperty("clustersinmonitortab") + "[1]" + obj.getProperty("clusternamepath"))).getText();
			System.out.println("Cluster selected : "+ nameOfSelectedCluster);

			Job= GetListOfJobsFromActivityPanelInUI();
			ATUReports.add("Pass Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.PASSED, new CaptureScreen(
					ScreenshotOf.DESKTOP));

		}

		catch (Throwable e) {
			e.printStackTrace();
			Assert.fail("Job Details are not displayed");
			ATUReports.add("Fail Test Case: " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.FAILED, new CaptureScreen(
					ScreenshotOf.DESKTOP));
		}

	}

	// ********************************************************************************************************************************************************//
	
//---------------------------------------------------------------------------------------//	
//-----------------CHECK IF BELOW CODE IS NEEDED OR NOT------------------------------------------------//
//---------------------------------------------------------------------------------------//
	
	// Verify BACKEND and UI data For Multiple Jobs For Particular Cluster
	// @Test(priority=15)

	public void JobsVerifyForMultipleJobsForParticularCluster() {

		try {
			for (int i = 2; i < 4; i++) {
				JSONObject tempJsonObj = new JSONObject(consolejobInfoOutput[i]);
				if (Job.get(i)
						.getjobType()
						.equals(tempJsonObj.getJSONArray("jobinsts").getJSONObject(i)
								.getString("jobtype"))) {
					System.out.println("JobType Passed !!");
				}

				else
					System.out.println("JobType Failed !!");

				if (Job.get(i)
						.getjobStatus()
						.equals(tempJsonObj.getJSONArray("jobinsts").getJSONObject(i)
								.getString("status"))) {
					System.out.println("JobStatus Passed !!");
				}

				else
					System.out.println("JobStatus Failed !!");

				if (Job.get(i)
						.getJobName()
						.equals(tempJsonObj.getJSONArray("jobinsts").getJSONObject(i)
								.getString("jobname"))) {
					System.out.println("JobName Passed !!");
				}

				else
					System.out.println("Jobname Failed !!");

				if (Job.get(i)
						.getJobRefId()
						.equals(tempJsonObj.getJSONArray("jobinsts").getJSONObject(i)
								.getString("id"))) {
					System.out.println("JobRefID Passed !!");
				}

				else
					System.out.println("JobRefID Failed !!");

				if (Job.get(i)
						.getCompletionPercent()
						.equals(tempJsonObj.getJSONArray("jobinsts").getJSONObject(i)
								.getString("progress"))) {
					System.out.println("JobCompletionPercent Passed !!");
				}

				else
					System.out.println("JobCompletionPercent Failed !!");

			}

		} catch (Throwable e) {
			e.printStackTrace();
			Assert.fail("UI data didnt match with the backend Data for the selected cluster!!");
		}

	}

}
