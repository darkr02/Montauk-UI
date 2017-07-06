package com.montauk.nodes;

//import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import com.montauk.reports.Reports;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

//import java.util.List;

public class RoleVerify 
{
	//************************************
	public static void VerifyingRoleCardDetails(List	<RolesForNodeUI> roleFromUI, String inputString)
	{
		try
		{
			
			String sErrorString = "";
			String newLine = System.getProperty("line.separator");
			//System.out.println(newLine +"String Displayed From Linuxbox for Roles: " + newLine + newLine+ inputString);
		    String actualStr = inputString.replace("{\"roles\":","").replace(",\"success\":true}", "");
		    //System.out.println(newLine + "Actual Data: " + actualStr);
		    ObjectMapper mapper = new ObjectMapper();
		    //****************************It will ignore all the properties that are not declared in the JSON class
		    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		    Roles[] rolecards = mapper.readValue(actualStr, TypeFactory.defaultInstance().constructArrayType(Roles.class));
		    for(int i=0; i < roleFromUI.size(); i++) 
		    {
		  		  
		  		  //-------------------------------------Role Name Verification-----------------------------------
		  		  if(roleFromUI.get(i).getName().equals(rolecards[i].getName()))
		  		  {
		  			  	Reports.LogResult("UI Role Name Matched with Backend for Role " + (i+1));
		  		  }
		  		  else
		  		  {
				  		sErrorString = sErrorString + ";" + "UI Role Name did not Match with Backend for Role " + (i+1) + ". Expected Value: " + rolecards[i].getName() + ". Actual UI Value: " + roleFromUI.get(i).getName() + "."; 
				  		Reports.LogResult("UI Role Name did not Match with Backend for Role " + (i+1));
		  		  }

		           //-------------------------------------Role State Verification-----------------------------------
		  		  if(roleFromUI.get(i).getState().equals(rolecards[i].getState()))
		  		  {
		  			  	Reports.LogResult("UI Role State Matched with Backend for Role " + (i+1));
		  		  }
		  		  else
		  		  {
		  			  	sErrorString = sErrorString + ";" + "UI Role State did not Match with Backend for Role " + (i+1) + ". Expected Value: " + rolecards[i].getState() + ". Actual UI Value: " + roleFromUI.get(i).getState() + ".";
		  			  	Reports.LogResult("UI Role State did not Match with Backend for Role " + (i+1));
		  		  }
		           //-------------------------------------Role Status Verification-----------------------------------
		  		  if(roleFromUI.get(i).getStatus().equals(rolecards[i].getStatus()))
		  		  {
		  			  	Reports.LogResult("UI Role Status Matched with Backend for Role " + (i+1));
		  		  }
		  		  else
		  		  {
		  			  	sErrorString = sErrorString + ";" + "UI Role Status did not Match with Backend for Role " + (i+1) + ". Expected Value: " + rolecards[i].getStatus() + ". Actual UI Value: " + roleFromUI.get(i).getStatus() + ".";
		  			  	Reports.LogResult("UI Role Status did not Match with Backend for Role " + (i+1));
		  		  }
		            //-------------------------------------Role ActionSupported Verification-----------------------------------
		  		  if(roleFromUI.get(i).getActionsupported()==rolecards[i].getActionsupported())
		  		  {
		  			  	Reports.LogResult("UI Role Action Supported Matched with Backend for Role " + (i+1));
		  		  }
		  		  else
		  		  {
		  			  	sErrorString = sErrorString + ";" + "UI Role Action Supported did not Match with Backend for Role " + (i+1) + ". Expected Value: " + rolecards[i].getActionsupported() + ". Actual UI Value: " + roleFromUI.get(i).getActionsupported() + ".";
		  			  	Reports.LogResult("UI Role Action Supported did not Match with Backend for Role " + (i+1));
		  		  }
		    }
		    if(!sErrorString.isEmpty())
	       	{
		    	
		    	sErrorString = sErrorString.substring(1); 
	       		ATUReports.add("Fail Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName() + "for " + sErrorString,LogAs.FAILED, new CaptureScreen(
		            		   ScreenshotOf.DESKTOP));
	       		Assert.fail(sErrorString);
	       	}
	       	 else
	       	 {
	       		 System.out.println(newLine + "Roles details in System Tab are same in UI and Backend. Test Case passed.");
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
