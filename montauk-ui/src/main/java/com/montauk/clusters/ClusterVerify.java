package com.montauk.clusters;

import java.util.List;

import org.testng.Assert;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.montauk.clusters.Cluster;
import com.montauk.clusters.Clusters;
import com.montauk.clusters.Stats;
import com.montauk.reports.Reports;



public class ClusterVerify 
{
	
	
	//************************************
	public static void VerifyingCluster(List	<Cluster> clusterFromUI, String inputString)
	{
		try
		{
			String sErrorString = "";
			String newLine = System.getProperty("line.separator");
			//System.out.println(newLine +"String Displayed From Linuxbox for Clusters: " + newLine + newLine+ inputString);
		    inputString = inputString.substring(inputString.indexOf("cluster"));
		    String actualStr = inputString.replace("cluster\":","").replace(",\"success\":true}", "");
		    //System.out.println(newLine + "Actual Data: " + actualStr);
		    Clusters cluster = null;
		    ObjectMapper mapper = new ObjectMapper();
		    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		    cluster = mapper.readValue(actualStr, Clusters.class);
		    
		    Stats status = cluster.getStats();
			
		    for(int i=0; i < clusterFromUI.size(); i++) 
		    {
		  	  if (cluster.getClusterid().equals(clusterFromUI.get(i).getID().toString()))
		  	  {
		  		  Reports.LogResult("ClusterID Matched with Backend");
		  		  //-------------------------------------Cluster Name Verification-----------------------------------
		  		  if(clusterFromUI.get(i).getClusterName().equals(cluster.getClustername()))
		  		  {
		  			  Reports.LogResult("UI ClusterName Matched with Backend");
		  		  }
		  		  else
		  		  {
		  			  sErrorString = sErrorString + ";" + "UI Cluster Name did not Match with Backend. Expected Value: " + cluster.getClustername() + ". Actual UI Value: " + clusterFromUI.get(i).getClusterName() + ".";
		  			  Reports.LogResult("UI ClusterName did not Match with Backend");
		  		  }
		           //-------------------------------------Cluster Status Verification-----------------------------------
		  		  if(clusterFromUI.get(i).getClusterStatus().equalsIgnoreCase(cluster.getStatus()))
		  		  {
		  			  Reports.LogResult("UI ClusterStatus Matched with Backend");
		  		  }
		  		  else
		  		  {
		  			  sErrorString = sErrorString + ";" + "UI Cluster Status did not Match with Backend. Expected Value: " + cluster.getStatus() + ". Actual UI Value: " + clusterFromUI.get(i).getClusterStatus() + ".";
		  			  Reports.LogResult("UI ClusterStatus did not Match with Backend");
		  		  }
		            //-------------------------------------Cluster Jobs Verification-----------------------------------
		  		  if(clusterFromUI.get(i).getJobs().equals(status.getJobs().getRunning()))
		  		  {
		  			  Reports.LogResult("UI Jobs for Cluster Matched with Backend");
		  		  }
		  		  else
		  		  {
		  			  sErrorString = sErrorString + ";" + "UI Cluster Running Jobs did not Match with Backend. Expected Value: " + status.getJobs().getRunning() + ". Actual UI Value: " + clusterFromUI.get(i).getJobs() + ".";
		  			  Reports.LogResult("UI Jobs for Cluster did not Match with Backend. Expected Value: " + status.getJobs().getRunning() + ". Actual UI Value: " +clusterFromUI.get(i).getJobs() + ".");
		  		  }
		            //-------------------------------------Cluster Nodes Verification-----------------------------------
		  		  if(clusterFromUI.get(i).getNodes().equals(status.getNodes().getTotal()))
		  		  {
		  			  Reports.LogResult("UI Nodes for Cluster Matched with Backend");
		  		  }
		  		  else
		  		  {
		  			  sErrorString = sErrorString + ";" + "UI Cluster No of Nodes did not Match with Backend. Expected Value: " + status.getNodes().getTotal() + ". Actual UI Value: " + clusterFromUI.get(i).getNodes() + "."; 
		  			  Reports.LogResult("UI Nodes for Cluster did not Match with Backend");
		  		  }
		            //-------------------------------------Cluster GB Verification-----------------------------------
		  		  if(clusterFromUI.get(i).getGb().equals(status.getCapacity().getTotal()))
		  		  {
		  			  Reports.LogResult("UI Cluster Capacity in Total GB Matched with Backend");
		  		  }
		  		  else
		  		  {
		  			  sErrorString = sErrorString + ";" + "UI Cluster Data On Disk did not Match with Backend. Expected Value: " + status.getCapacity().getTotal() + ". Actual UI Value: " + clusterFromUI.get(i).getGb() + "."; 
		  			  Reports.LogResult("UI Cluster Capacity in Total GB  did not Match with Backend");
		  		  }
		      		  
		            //-------------------------------------Cluster Alerts Verification-----------------------------------
		  		  if(clusterFromUI.get(i).getAlerts().equals(status.getAlerts().getTotal()))
		  		  {
		  			  Reports.LogResult("UI Alerts for Cluster Matched with Backend");
		  		  }
		  		  else
		  		  {
		  			  sErrorString = sErrorString + ";" + "UI Cluster Alert Nos did not Match with Backend. Expected Value: " + status.getAlerts().getTotal() + ". Actual UI Value: " + clusterFromUI.get(i).getAlerts() + ".";
		  			  Reports.LogResult("UI Alerts for Cluster did not Match with Backend");
		  		  }
		            //-------------------------------------Cluster Disk Space Available Verification-----------------------------------
		  		  if(clusterFromUI.get(i).getDiskSpaceAvailable().contains(status.getCapacity().getFree()))
		  		  {
		  			  Reports.LogResult("UI Cluster Disk Space Available Matched with Backend");
		  		  }
		  		  else
		  		  {
		  			  sErrorString = sErrorString + ";" + "UI Cluster Data On Disk did not Match with Backend. Expected Value: " + status.getCapacity().getFree() + ". Actual UI Value: " + clusterFromUI.get(i).getDiskSpaceAvailable() + "."; 
		  			  Reports.LogResult("UI Cluster Disk Space Available did not Match with Backend");
		  		  }
		  			  
		  		  break;
		  	  }
		    }
		    if(!sErrorString.isEmpty())
	       	{
		    	
		    	sErrorString = sErrorString.substring(1); 
	       		ATUReports.add("Fail Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName() + "for " +sErrorString,LogAs.FAILED, new CaptureScreen(
		            		   ScreenshotOf.DESKTOP));
	       		Assert.fail(sErrorString);
	       	}
	       	 else
	       	 {
	                ATUReports.add("Pass Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.PASSED, new CaptureScreen(
		            		   ScreenshotOf.DESKTOP));
	    			System.out.println(newLine + "Cluster details in Monitor Tab are same in UI and Backend. Test Case passed.");
	       	 }
		}
		catch(Throwable e)
		{
			System.out.println("Cluster Verification failed due to mismatch");
			e.printStackTrace();
		}
		
	}
	//************************************
	public static void VerifyingClusterDetailsInSystemTab(List	<Cluster> clusterFromUI, String inputString)
	{
		try
		{
			
			String sErrorString = "";
			String newLine = System.getProperty("line.separator");
			//System.out.println(newLine +"String Displayed From Linuxbox for Clusters: " + newLine + newLine+ inputString);
		    String actualStr = inputString.replace("{\"cluster\":","").replace(",\"success\":true}", "");
		    //System.out.println(newLine + "Actual Data: " + actualStr);
		    Clusters cluster = null;
		    ObjectMapper mapper = new ObjectMapper();
		    //****************************It will ignore all the properties that are not declared in the JSON class
		    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		    cluster = mapper.readValue(actualStr, Clusters.class);
		    Stats status = cluster.getStats();

			//System.out.println("Array length is : " +clusters.length);
			
		    for(int i=0; i < clusterFromUI.size(); i++) 
		    {
		  		  
			  	  if (cluster.getClusterid().equals(clusterFromUI.get(i).getID().toString()))
			  	  {

			    	//-------------------------------------Cluster Name Verification-----------------------------------
			  		  if(clusterFromUI.get(i).getClusterName().equals(cluster.getClustername()))
			  		  {
			  			  	Reports.LogResult("UI Cluster Name Matched with Backend for Cluster " + clusterFromUI.get(i).getClusterName());
			  		  }
			  		  else
			  		  {
					  		sErrorString = sErrorString + ";" + "UI Cluster Name did not Match with Backend. Expected Value: " + cluster.getClustername() + ". Actual UI Value: " + clusterFromUI.get(i).getClusterName() + ".";
					  		Reports.LogResult("UI Cluster Name did not Match with Backend for Cluster " + clusterFromUI.get(i).getClusterName());
			  		  }
			            //-------------------------------------Cluster Alert Nos Verification-----------------------------------
			  		  if(clusterFromUI.get(i).getAlerts().equals(status.getAlerts().getTotal()))
			  		  {
			  			  	Reports.LogResult("UI Cluster Alert Nos Matched with Backend for Cluster " + clusterFromUI.get(i).getAlerts());
			  		  }
			  		  else
			  		  {
			  			  	sErrorString = sErrorString + ";" + "UI Cluster Alert Nos did not Match with Backend. Expected Value: " + status.getAlerts().getTotal() + ". Actual UI Value: " + clusterFromUI.get(i).getAlerts() + ".";
			  			  	Reports.LogResult("UI Cluster Alert Nos did not Match with Backend. Expected Value: " + status.getAlerts().getTotal() + ". Actual UI Value: " + clusterFromUI.get(i).getAlerts() + ".");
			  		  }
			            //-------------------------------------Cluster Description Verification-----------------------------------
			  		  if(clusterFromUI.get(i).getClusterDescripton().equals(cluster.getDescription()))
			  		  {
			  			  	Reports.LogResult("UI Cluster Description Matched with Backend for Cluster " + clusterFromUI.get(i).getClusterDescripton());
			  		  }
			  		  else
			  		  {
			  			  	sErrorString = sErrorString + ";" + "UI Cluster Description did not Match with Backend. Expected Value: " + cluster.getDescription() + ". Actual UI Value: " + clusterFromUI.get(i).getClusterDescripton() + ".";
			  			  	Reports.LogResult("UI Cluster Description did not Match with Backend. Expected Value: " + cluster.getDescription() + ". Actual UI Value: " + clusterFromUI.get(i).getClusterDescripton() + ".");
			  		  }
	
			           //-------------------------------------Cluster Status-----------------------------------
			  		  if(clusterFromUI.get(i).getClusterStatus().equalsIgnoreCase(cluster.getStatus()))
			  		  {
			  			  	Reports.LogResult("UI Cluster Status matched with Backend for Cluster " + clusterFromUI.get(i).getClusterStatus());
			  		  }
			  		  else
			  		  {
			  			  	sErrorString = sErrorString + ";" + "UI Cluster Status did not Match with Backend. Expected Value: " + cluster.getStatus() + ". Actual UI Value: " + clusterFromUI.get(i).getClusterStatus() + ".";
			  			  	Reports.LogResult("UI Cluster Status did not Match with Backend for Cluster " + clusterFromUI.get(i).getClusterStatus());
			  		  }
			            //-------------------------------------Cluster Running Jobs Verification-----------------------------------
			  		  if(clusterFromUI.get(i).getJobs().equals(status.getJobs().getRunning()))
			  		  {
			  			  	Reports.LogResult("UI Cluster Running Jobs Matched with Backend for Cluster " + clusterFromUI.get(i).getJobs());
			  		  }
			  		  else
			  		  {
			  			  	sErrorString = sErrorString + ";" + "UI Cluster Running Jobs did not Match with Backend. Expected Value: " + status.getJobs().getRunning() + ". Actual UI Value: " + clusterFromUI.get(i).getJobs() + ".";
			  			  	Reports.LogResult("UI Cluster Running Jobs did not Match with Backend. Expected Value: " + status.getJobs().getRunning() + ". Actual UI Value: " + clusterFromUI.get(i).getJobs() + ".");
			  		  }
			            //-------------------------------------Cluster Data On Disk Verification-----------------------------------
			  		  if(clusterFromUI.get(i).getGb().equals(status.getCapacity().getTotal()))
			  		  {
			  			  	Reports.LogResult("UI Cluster Data On Disk Matched with Backend for Cluster " + clusterFromUI.get(i).getGb());
			  		  }
			  		  else
			  		  {
							sErrorString = sErrorString + ";" + "UI Cluster Data On Disk did not Match with Backend. Expected Value: " + status.getCapacity().getTotal() + ". Actual UI Value: " + clusterFromUI.get(i).getGb() + "."; 
							Reports.LogResult("UI Cluster Data On Disk did not Match with Backend for Cluster " + clusterFromUI.get(i).getGb());
			  		  }
			            //-------------------------------------Cluster No of Nodes Verification-----------------------------------
			  		  if(clusterFromUI.get(i).getNodes().equals(status.getNodes().getTotal()))
			  		  {
			  			  	Reports.LogResult("UI Cluster No of Nodes Matched with Backend for Cluster " + clusterFromUI.get(i).getNodes());
			  		  }
			  		  else
			  		  {
							sErrorString = sErrorString + ";" + "UI Cluster No of Nodes did not Match with Backend. Expected Value: " + status.getNodes().getTotal() + ". Actual UI Value: " + clusterFromUI.get(i).getNodes() + "."; 
							Reports.LogResult("UI Cluster No of Nodes did not Match with Backend for Cluster " + clusterFromUI.get(i).getNodes());
			  		  }
			  	  }
		    }
		    if(!sErrorString.isEmpty())
	       	{
		    	
		    	sErrorString = sErrorString.substring(1); 
	       		ATUReports.add("Fail Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName() + "for " +sErrorString,LogAs.FAILED, new CaptureScreen(
		            		   ScreenshotOf.DESKTOP));
	       		Assert.fail(sErrorString);
	       	}
	       	 else
	       	 {
	                ATUReports.add("Pass Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.PASSED, new CaptureScreen(
		            		   ScreenshotOf.DESKTOP));
	    			System.out.println(newLine + "Cluster details in System Tab are same in UI and Backend. Test Case passed.");
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

	//************************************
	public static void VerifyingLeftPanelClusterDetailsInSystemTab(List	<Cluster> clusterFromUI, String inputString)
	{
		try
		{
			
			String sErrorString = "";
			String newLine = System.getProperty("line.separator");
			//System.out.println(newLine +"String Displayed From Linuxbox for Clusters: " + newLine + newLine+ inputString);
		    String actualStr = inputString.replace("{\"cluster\":","").replace(",\"success\":true}", "");
		    //System.out.println(newLine + "Actual Data: " + actualStr);
		    Clusters cluster = null;
		    ObjectMapper mapper = new ObjectMapper();
		    //****************************It will ignore all the properties that are not declared in the JSON class
		    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		    cluster = mapper.readValue(actualStr, Clusters.class);
		    Stats status = cluster.getStats();

			//System.out.println("Array length is : " +clusters.length);
			
		    for(int i=0; i < clusterFromUI.size(); i++) 
		    {
		  		  
		  		  //-------------------------------------Cluster Name Verification-----------------------------------
		  		  if(clusterFromUI.get(i).getClusterName().equals(cluster.getClustername()))
		  		  {
		  			  	Reports.LogResult("UI Cluster Name Matched with Backend for Cluster " + clusterFromUI.get(i).getClusterName());
		  		  }
		  		  else
		  		  {
				  		sErrorString = sErrorString + ";" + "UI Cluster Name did not Match with Backend. Expected Value: " + cluster.getClustername() + ". Actual UI Value: " + clusterFromUI.get(i).getClusterName() + ".";
				  		Reports.LogResult("UI Cluster Name did not Match with Backend for Cluster " + clusterFromUI.get(i).getClusterName());
		  		  }

		            //-------------------------------------Cluster Alert Nos Verification-----------------------------------
		  		  if(clusterFromUI.get(i).getAlerts().equals(status.getAlerts().getTotal()))
		  		  {
		  			  	Reports.LogResult("UI Cluster Alert Nos Matched with Backend for Cluster " + clusterFromUI.get(i).getClusterName());
		  		  }
		  		  else
		  		  {
		  			  	sErrorString = sErrorString + ";" + "UI Cluster Alert Nos did not Match with Backend. Expected Value: " + status.getAlerts().getTotal() + ". Actual UI Value: " + clusterFromUI.get(i).getAlerts() + ".";
		  			  	Reports.LogResult("UI Cluster Alert Nos did not Match with Backend. Expected Value: " + status.getAlerts().getTotal() + ". Actual UI Value: " + clusterFromUI.get(i).getAlerts() + ".");
		  		  }
		            //-------------------------------------Cluster Description Verification-----------------------------------
		  		  if(clusterFromUI.get(i).getClusterDescripton().equals(cluster.getDescription()))
		  		  {
		  			  	Reports.LogResult("UI Cluster Description Matched with Backend for Cluster " + clusterFromUI.get(i).getClusterName());
		  		  }
		  		  else
		  		  {
		  			  	sErrorString = sErrorString + ";" + "UI Cluster Description did not Match with Backend. Expected Value: " + cluster.getDescription() + ". Actual UI Value: " + clusterFromUI.get(i).getClusterDescripton() + ".";
		  			  	Reports.LogResult("UI Cluster Description did not Match with Backend. Expected Value: " + cluster.getDescription() + ". Actual UI Value: " + clusterFromUI.get(i).getClusterDescripton() + ".");
		  		  }
		           //-------------------------------------Cluster Status-----------------------------------
		  		  if(clusterFromUI.get(i).getClusterStatus().equalsIgnoreCase(cluster.getStatus()))
		  		  {
		  			  	Reports.LogResult("UI Cluster Status matched with Backend for Cluster " + clusterFromUI.get(i).getClusterName());
		  		  }
		  		  else
		  		  {
		  			  	sErrorString = sErrorString + ";" + "UI Cluster Status did not Match with Backend for Cluster " + clusterFromUI.get(i).getClusterName() + ". Expected Value: " + cluster.getStatus() + ". Actual UI Value: " + clusterFromUI.get(i).getClusterStatus() + ".";
		  			  	Reports.LogResult("UI Cluster Status did not Match with Backend for Cluster " + clusterFromUI.get(i).getClusterName());
		  		  }
		            //-------------------------------------Cluster Running Jobs Verification-----------------------------------
		  		  if(clusterFromUI.get(i).getJobs().equals(status.getJobs().getRunning()))
		  		  {
		  			  	Reports.LogResult("UI Cluster Running Jobs Matched with Backend for Cluster " + clusterFromUI.get(i).getClusterName());
		  		  }
		  		  else
		  		  {
		  			  	sErrorString = sErrorString + ";" + "UI Cluster Running Jobs did not Match with Backend. Expected Value: " + status.getJobs().getRunning() + ". Actual UI Value: " + clusterFromUI.get(i).getJobs() + ".";
		  			  	Reports.LogResult("UI Cluster Running Jobs did not Match with Backend. Expected Value: " + status.getJobs().getRunning() + ". Actual UI Value: " + clusterFromUI.get(i).getJobs() + ".");
		  		  }
		            //-------------------------------------Cluster Data On Disk Verification-----------------------------------
		  		  if(clusterFromUI.get(i).getGb().equals(status.getCapacity().getTotal()))
		  		  {
		  			  	Reports.LogResult("UI Cluster Data On Disk Matched with Backend for Cluster " + clusterFromUI.get(i).getClusterName());
		  		  }
		  		  else
		  		  {
						sErrorString = sErrorString + ";" + "UI Cluster Data On Disk did not Match with Backend for Cluster " + clusterFromUI.get(i).getClusterName() + ". Expected Value: " + status.getCapacity().getTotal() + ". Actual UI Value: " + clusterFromUI.get(i).getGb() + "."; 
						Reports.LogResult("UI Cluster Data On Disk did not Match with Backend for Cluster " + clusterFromUI.get(i).getClusterName());
		  		  }
		            //-------------------------------------Cluster No of Nodes Verification-----------------------------------
		  		  if(clusterFromUI.get(i).getNodes().equals(status.getNodes().getTotal()))
		  		  {
		  			  	Reports.LogResult("UI Cluster No of Nodes Matched with Backend for Cluster " + clusterFromUI.get(i).getClusterName());
		  		  }
		  		  else
		  		  {
						sErrorString = sErrorString + ";" + "UI Cluster No of Nodes did not Match with Backend for Cluster " + clusterFromUI.get(i).getClusterName() + ". Expected Value: " + status.getNodes().getTotal() + ". Actual UI Value: " + clusterFromUI.get(i).getNodes() + "."; 
						Reports.LogResult("UI Cluster No of Nodes did not Match with Backend for Cluster " + clusterFromUI.get(i).getClusterName());
		  		  }
		    }
		    if(!sErrorString.isEmpty())
	       	{
		    	
		    	sErrorString = sErrorString.substring(1); 
	       		ATUReports.add("Fail Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName() + "for " +sErrorString,LogAs.FAILED, new CaptureScreen(
		            		   ScreenshotOf.DESKTOP));
	       		Assert.fail(sErrorString);
	       	}
	       	 else
	       	 {
	                ATUReports.add("Pass Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.PASSED, new CaptureScreen(
		            		   ScreenshotOf.DESKTOP));
	    			System.out.println(newLine + "Cluster details in the left panel of the Node card page in System Tab are same in UI and Backend. Test Case passed.");
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
