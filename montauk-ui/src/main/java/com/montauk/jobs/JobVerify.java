package com.montauk.jobs;

import java.text.DecimalFormat;
import java.util.List;

import org.json.JSONObject;
import org.testng.Assert;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import com.montauk.clusters.Cluster;
import com.montauk.reports.Reports;

public class JobVerify {

	public static void VerifyingJobDetails(List	<JobClass> clusterFromUI, String inputString,int n)
	{
		String sErrorString = null;
		JobClass job = new JobClass();
	try
    {	
		String jobDescription;
    	JSONObject json=new JSONObject(inputString);
    	System.out.println(json.getJSONObject("jobinst").getString("jobname"));
    	
    	//job name
    	if(clusterFromUI.get(n).getJobName().equals(json.getJSONObject("jobinst").getString("jobname")))
       	{
       		System.out.println("JobName Passed !!");
       		Reports.LogResult("UI JobName Matched with Backend"+(n));
       	}
       	
       	else
       	{
       		System.out.println("JobName Failed !!");
       		sErrorString = sErrorString + ";" + "UI JobName did not Match with Backend " + (n); 
       		Assert.fail(sErrorString);
			Reports.LogResult("UI JobName did not Match with Backend"+(n));
       	}
    	
    	//job id
    	if((clusterFromUI.get(n).getJobRefId()).trim().equals(json.getJSONObject("jobinst").getString("id")))
       	{
       		System.out.println("JobID Passed !!");
       		Reports.LogResult("UI JobID Matched with Backend"+(n));
       	}
       	
       	else
       	{
       		System.out.println("JobID Failed !!");
       		sErrorString = sErrorString + ";" + "UI JobID did not Match with Backend " + (n); 
       		Assert.fail(sErrorString);
			Reports.LogResult("UI JobID did not Match with Backend"+(n));
       	}
    	
    	//job status
    	if(clusterFromUI.get(n).getjobStatus().equals(json.getJSONObject("jobinst").getString("status")))
       	{
       		System.out.println("Jobstatus Passed !!");
       		Reports.LogResult("UI Jobstatus Matched with Backend"+(n));
       	}
       	
       	else
       	{
       		System.out.println("Jobstatus Failed !!");
       		sErrorString = sErrorString + ";" + "UI Jobstatus did not Match with Backend " + (n); 
       		Assert.fail(sErrorString);
			Reports.LogResult("UI Jobstatus did not Match with Backend"+(n));
       	}
    	
    	//job description
    	
//    	if (clusterFromUI.get(n).getjobDescription().equalsIgnoreCase(""))
//    	{
//    	     jobDescription="null";	
//    	    
//    	}
//    	else
//    		
//    	{
//    		jobDescription=clusterFromUI.get(n).getjobDescription();
//    	    	
//    	}
    	if(clusterFromUI.get(n).getjobDescription().equals(json.getJSONObject("jobinst").getString("description")))
       	{
       		System.out.println("Jobdescription Passed !!");
       		Reports.LogResult("UI Jobdescription Matched with Backend"+(n));
       	}
       	
       	else
       	{
       		System.out.println("Jobdescription Failed !!");
       		sErrorString = sErrorString + ";" + "UI JobName did not Match with Backend " + (n); 
       		Assert.fail(sErrorString);
			Reports.LogResult("UI Jobdescription did not Match with Backend"+(n));
       	}
    	 String finalDuration;
    	 String  startTime = (json.getJSONObject("jobinst").getString("starttime"));
    	 String  endTime   = (json.getJSONObject("jobinst").getString("endtime"));
    	 double duration= (Double.parseDouble(endTime)-Double.parseDouble(startTime))/1000;
    	 if (duration>=60)
    	 {
    		 duration = duration/60;
    		 duration = (Math.round(duration * 100.0))/100.0;
    		 finalDuration= duration+" min";
    		 
    	 }
    	 
    	 else
    	 {
    		 duration = (Math.round(duration * 100.0))/100.0;
    		 finalDuration=duration+" s";
    	 
    	 }
    	
    	//job duration 
    	if(clusterFromUI.get(n).getjobDuration().equals(finalDuration))
       	{
       		System.out.println("JobDuration Passed !!");
       		Reports.LogResult("UI JobDuration Matched with Backend"+(n));
       	}
       	
       	else
       	{
       		System.out.println("JobDuration Failed !!");
       		sErrorString = sErrorString + ";" + "UI JobDuration did not Match with Backend " + (n); 
       		Assert.fail(sErrorString);
       		Reports.LogResult("UI JobDuration did not Match with Backend"+(n));
       	}

   	 
    	
    	
    }
	
	

catch(Throwable e)
{
	e.printStackTrace();
	ATUReports.add("Fail Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName() + "for " +sErrorString,LogAs.FAILED, new CaptureScreen(
 		   ScreenshotOf.DESKTOP));
Assert.fail(sErrorString);
}
	
	
	
    }
	}
	


