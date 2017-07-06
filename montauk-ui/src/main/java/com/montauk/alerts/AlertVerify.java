package com.montauk.alerts;

//import java.util.ArrayList;
import java.util.List;









import org.testng.Assert;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import com.montauk.generic.Mytools;
import com.montauk.reports.Reports;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

//import java.util.List;

public class AlertVerify 
{
	//************************************
	public static void VerifyingAlerts(List	<AlertFromUI> alertFromUI, String inputString)
	{
		try
		{
			
			String sErrorString = "";
			String newLine = System.getProperty("line.separator");
			//System.out.println(newLine +"String Displayed From Linuxbox for Alerts: " + newLine + newLine+ inputString);
			int startIndex = inputString.indexOf(",\"alerts\":") + 10;
			int endIndex = inputString.lastIndexOf("],\"success") + 1;
		    String actualStr = inputString.substring(startIndex, endIndex);
		    //System.out.println(newLine + "Actual Data: " + actualStr);
		    //Clusters cluster = null;
		    ObjectMapper mapper = new ObjectMapper();
		    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			Alerts[] alerts = mapper.readValue(actualStr, TypeFactory.defaultInstance().constructArrayType(Alerts.class));
			//System.out.println("Array length is : " +alerts.length);
				   /*System.out.print(alerts[i].getAlertid() + ", ");
				   System.out.print(alerts[i].getClusterid() + ", ");
				   System.out.print(alerts[i].getDescription() + ", ");
				   System.out.print(alerts[i].getName() + ", ");
				   System.out.print(alerts[i].getRead() + ", ");
				   System.out.print(alerts[i].getStarttime() + ", ");
				   System.out.print(alerts[i].getType() + ", ");
				   System.out.print(alerts[i].getSeverity() + ", ");
				   System.out.println("");			   //System.out.print(alerts[i].getType() + ", ");
				}*/

			
		    for(int i=0; i < alertFromUI.size(); i++) 
		    {
		  		  
		  		  //-------------------------------------Alert Name Verification-----------------------------------
		  		  if(alertFromUI.get(i).getAlertName().equals(alerts[i].getName()))
		  		  {
		  			  	Reports.LogResult("UI AlertName Matched with Backend for Alert " + (i+1));
		  		  }
		  		  else
		  		  {
				  		sErrorString = sErrorString + ";" + "UI AlertName did not Match with Backend for Alert " + (i+1) + ". Expected Value: " + alerts[i].getName() + ". Actual UI Value: " + alertFromUI.get(i).getAlertName() + ".";
				  		Reports.LogResult("UI AlertName did not Match with Backend for Alert " + (i+1));
		  		  }

		           //-------------------------------------Alert Type Verification-----------------------------------
		  		  if(alertFromUI.get(i).getAlertType().equalsIgnoreCase(alerts[i].getType()))
		  		  {
		  			  	Reports.LogResult("UI Alert Type Matched with Backend for Alert " + (i+1));
		  		  }
		  		  else
		  		  {
		  			  	sErrorString = sErrorString + ";" + "UI Alert Type did not Match with Backend for Alert " + (i+1) + ". Expected Value: " + alerts[i].getType() + ". Actual UI Value: " + alertFromUI.get(i).getAlertType() + ".";
		  			  	Reports.LogResult("UI Alert Type did not Match with Backend for Alert " + (i+1));
		  		  }
		            //-------------------------------------Alert Description Verification-----------------------------------
		  		  if(alertFromUI.get(i).getDescription().equals(alerts[i].getDescription()))
		  		  {
		  			  	Reports.LogResult("UI Alert Description Matched with Backend for Alert " + (i+1));
		  		  }
		  		  else
		  		  {
		  			  	sErrorString = sErrorString + ";" + "UI Alert Description did not Match with Backend " + (i+1) + ". Expected Value: " + alerts[i].getDescription() + ". Actual UI Value: " + alertFromUI.get(i).getDescription() + ".";
		  			  	Reports.LogResult("UI Alert Description did not Match with Backend " + (i+1) + ". Expected Value: " + alerts[i].getDescription() + ". Actual UI Value: " + alertFromUI.get(i).getDescription() + ".");
		  		  }
		            //-------------------------------------Alert Start Time Verification-----------------------------------
		  		  if(alertFromUI.get(i).getStarttime().equals(Mytools.DateFormatter(Long.parseLong(alerts[i].getStarttime()))))
		  		  {
		  			  	Reports.LogResult("UI Alert Start Time Matched with Backend for Alert " + (i+1));
		  		  }
		  		  else
		  		  {
						sErrorString = sErrorString + ";" + "UI Alert Start Time did not Match with Backend for Alert " + (i+1) + ". Expected Value: " + Mytools.DateFormatter(Long.parseLong(alerts[i].getStarttime())) + ". Actual UI Value: " + alertFromUI.get(i).getStarttime() + "."; 
						Reports.LogResult("UI Alert Start Time did not Match with Backend for Alert " + (i+1));
		  		  }
		            //-------------------------------------Alert Severity Verification-----------------------------------
		  		  if(alertFromUI.get(i).getAlertSeverity().equals(alerts[i].getSeverity()))
		  		  {
		  			  	Reports.LogResult("UI Alert Status Matched with Backend for Alert " + (i+1));
		  		  }
		  		  else
		  		  {
			  			sErrorString = sErrorString + ";" + "UI Alert Status did not Match with Backend for Alert " + (i+1) + ". Expected Value: " + alerts[i].getSeverity() + ". Actual UI Value: " + alertFromUI.get(i).getAlertSeverity() + "."; 
			  			Reports.LogResult("UI Alert Status did not Match with Backend for Alert " + (i+1));
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
