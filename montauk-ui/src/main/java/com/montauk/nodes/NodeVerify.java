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

public class NodeVerify 
{
	//************************************
	public static void VerifyingNodeDetails(List	<NodesFromUI> nodeFromUI, String inputString)
	{
		try
		{
			
			String sErrorString = "";
			String newLine = System.getProperty("line.separator");
			//System.out.println(newLine +"String Displayed From Linuxbox for Nodes: " + newLine + newLine+ inputString);
		    String actualStr = inputString.replace("{\"node\":","").replace(",\"success\":true}", "");
		    //System.out.println(newLine + "Actual Data: " + actualStr);
		    Nodes nodes = null;
		    ObjectMapper mapper = new ObjectMapper();
		    //****************************It will ignore all the properties that are not declared in the JSON class
		    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		    nodes = mapper.readValue(actualStr, Nodes.class);
			//System.out.println("Array length is : " +nodes.length);
			
		    for(int i=0; i < nodeFromUI.size(); i++) 
		    {
		  		  
		  		  //-------------------------------------Node Name Verification-----------------------------------
		  		  if(nodeFromUI.get(i).getNodeName().equals(nodes.getHostname()))
		  		  {
		  			  	Reports.LogResult("UI Node Name Matched with Backend for Node " + (i+1));
		  		  }
		  		  else
		  		  {
				  		sErrorString = sErrorString + ";" + "UI Node Name did not Match with Backend for Node " + (i+1) + ". Expected Value: " + nodes.getHostname() + ". Actual UI Value: " + nodeFromUI.get(i).getNodeName() + "."; 
				  		Reports.LogResult("UI Node Name did not Match with Backend for Node " + (i+1));
		  		  }

		           //-------------------------------------Node IP Verification-----------------------------------
		  		  if(nodeFromUI.get(i).getNodeIP().equalsIgnoreCase(nodes.getIp()))
		  		  {
		  			  	Reports.LogResult("UI Node IP Matched with Backend for Node " + (i+1));
		  		  }
		  		  else
		  		  {
		  			  	sErrorString = sErrorString + ";" + "UI Node IP did not Match with Backend for Node " + (i+1) + ". Expected Value: " + nodes.getIp() + ". Actual UI Value: " + nodeFromUI.get(i).getNodeIP() + ".";
		  			  	Reports.LogResult("UI Node IP did not Match with Backend for Node " + (i+1));
		  		  }
		            //-------------------------------------Node Description Verification-----------------------------------
		  		  /*if(nodeFromUI.get(i).getNodeDescription().equals(nodes.getDescription()))
		  		  {
		  			  	Reports.LogResult("UI Node Description Matched with Backend for Node " + (i+1));
		  		  }
		  		  else
		  		  {
		  			  	sErrorString = sErrorString + ";" + "UI Node Description did not Match with Backend. Expected Value: " + nodes[i].getDescription() + ". Actual UI Value: " + nodeFromUI.get(i).getNodeDescription() + ".";
		  			  	Reports.LogResult("UI Node Description did not Match with Backend. Expected Value: " + nodes[i].getDescription() + ". Actual UI Value: " + nodeFromUI.get(i).getNodeDescription() + ".");
		  		  }*/
		            //-------------------------------------Node Status Verification-----------------------------------
		  		  if(nodeFromUI.get(i).getNodeStatus().equals(nodes.getStatus()))
		  		  {
		  			  	Reports.LogResult("UI Node Status Matched with Backend for Node " + (i+1));
		  		  }
		  		  else
		  		  {
						sErrorString = sErrorString + ";" + "UI Node Status did not Match with Backend for Node " + (i+1) + ". Expected Value: " + nodes.getStatus() + ". Actual UI Value: " + nodeFromUI.get(i).getNodeStatus() + "."; 
						Reports.LogResult("UI Node Status did not Match with Backend for Node " + (i+1));
		  		  }
		            //-------------------------------------Node Type Verification-----------------------------------
		  		  if(nodeFromUI.get(i).getNodeType().equals(nodes.getType()))
		  		  {
		  			  	Reports.LogResult("UI Node Type Matched with Backend for Node " + (i+1));
		  		  }
		  		  else
		  		  {
						sErrorString = sErrorString + ";" + "UI Node Type did not Match with Backend for Node " + (i+1) + ". Expected Value: " + nodes.getType() + ". Actual UI Value: " + nodeFromUI.get(i).getNodeType() + "."; 
						Reports.LogResult("UI Node Type did not Match with Backend for Node " + (i+1));
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
       		 	System.out.println(newLine + "Nodes details in System Tab are same in UI and Backend. Test Case passed.");
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
	//************************************
	public static void VerifyingNodeCardDetails(List	<NodesCardFromUI> nodeFromUI, String inputString, int nNoOfNode)
	{
		try
		{
			
			String sErrorString = "";
			String newLine = System.getProperty("line.separator");
			//System.out.println(newLine +"String Displayed From Linuxbox for Nodes: " + newLine + newLine+ inputString);
		    String actualStr = inputString.replace("{\"total\":" + nNoOfNode + ",\"nodes\":","").replace(",\"success\":true}", "");
		    //System.out.println(newLine + "Actual Data: " + actualStr);
		    //NodeCardDetails[] nodecards = null;
		    ObjectMapper mapper = new ObjectMapper();
		    //Alerts[] alerts = mapper.readValue(actualStr, TypeFactory.defaultInstance().constructArrayType(Alerts.class));
		    //****************************It will ignore all the properties that are not declared in the JSON class
		    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		    NodeCardDetails[] nodecards = mapper.readValue(actualStr, TypeFactory.defaultInstance().constructArrayType(NodeCardDetails.class));
		    //nodecards = mapper.readValue(actualStr, NodeCardDetails.class);
			//System.out.println("Array length is : " +nodecards.length);
			
		    for(int i=0; i < nodeFromUI.size(); i++) 
		    {
		  		  
		  		  //-------------------------------------Node Name Verification-----------------------------------
		  		  if(nodeFromUI.get(i).getNodeName().equals(nodecards[i].getHostname()))
		  		  {
		  			  	Reports.LogResult("UI Node Name Matched with Backend for Node " + (i+1));
		  		  }
		  		  else
		  		  {
				  		sErrorString = sErrorString + ";" + "UI Node Name did not Match with Backend for Node " + (i+1) + ". Expected Value: " + nodecards[i].getHostname() + ". Actual UI Value: " + nodeFromUI.get(i).getNodeName() + "."; 
				  		Reports.LogResult("UI Node Name did not Match with Backend for Node " + (i+1));
		  		  }

		           //-------------------------------------Node Card Alert Verification-----------------------------------
		  		  /*if(nodeFromUI.get(i).getNodeAlert().equals(nodecards[i].getStats().getAlerts().getTotal()))
		  		  {
		  			  	Reports.LogResult("UI Node Card Alert Matched with Backend for Node " + (i+1));
		  		  }
		  		  else
		  		  {
		  			  	sErrorString = sErrorString + ";" + "UI Node Card Alert did not Match with Backend for Node " + (i+1);
		  			  	Reports.LogResult("UI Node Card Alert did not Match with Backend for Node " + (i+1));
		  		  }*/
		           //-------------------------------------Node Card GB Used Verification-----------------------------------
		  		  if(nodeFromUI.get(i).getNodeGBUsed().equals(nodecards[i].getStats().getCapacity().getUsed()))
		  		  {
		  			  	Reports.LogResult("UI Node Card GB Used Matched with Backend for Node " + (i+1));
		  		  }
		  		  else
		  		  {
		  			  	sErrorString = sErrorString + ";" + "UI Node Card GB Used did not Match with Backend for Node " + (i+1) + ". Expected Value: " + nodecards[i].getStats().getCapacity().getUsed() + ". Actual UI Value: " + nodeFromUI.get(i).getNodeGBUsed() + ".";
		  			  	Reports.LogResult("UI Node Card GB Used did not Match with Backend for Node " + (i+1));
		  		  }
		            //-------------------------------------Node GB Available Verification-----------------------------------
		  		  if(nodeFromUI.get(i).getNodeGBAvailable().equals(nodecards[i].getStats().getCapacity().getFree()))
		  		  {
		  			  	Reports.LogResult("UI Node GB Available Matched with Backend for Node " + (i+1));
		  		  }
		  		  else
		  		  {
		  			  	sErrorString = sErrorString + ";" + "UI Node GB Available did not Match with Backend for Node " + (i+1) + ". Expected Value: " + nodecards[i].getStats().getCapacity().getFree() + ". Actual UI Value: " + nodeFromUI.get(i).getNodeGBAvailable() + ".";
		  			  	Reports.LogResult("UI Node GB Available did not Match with Backend. Expected Value: " + nodecards[i].getStats().getCapacity().getFree() + ". Actual UI Value: " + nodeFromUI.get(i).getNodeGBAvailable() + ".");
		  		  }
		            //-------------------------------------Node Card Status Verification-----------------------------------
		  		  if(nodeFromUI.get(i).getNodeStatus().equals(nodecards[i].getStatus()))
		  		  {
		  			  	Reports.LogResult("UI Node Status Matched with Backend for Node " + (i+1));
		  		  }
		  		  else
		  		  {
						sErrorString = sErrorString + ";" + "UI Node Status did not Match with Backend for Node " + (i+1) + ". Expected Value: " + nodecards[i].getStatus() + ". Actual UI Value: " + nodeFromUI.get(i).getNodeStatus() + "."; 
						Reports.LogResult("UI Node Status did not Match with Backend for Node " + (i+1));
		  		  }
		            //-------------------------------------Node GB Verification-----------------------------------
		  		  if(nodeFromUI.get(i).getNodeGB().equals(nodecards[i].getStats().getCapacity().getTotal()))
		  		  {
		  			  	Reports.LogResult("UI Node GB Matched with Backend for Node " + (i+1));
		  		  }
		  		  else
		  		  {
						sErrorString = sErrorString + ";" + "UI Node GB did not Match with Backend for Node " + (i+1) + ". Expected Value: " + nodecards[i].getStats().getCapacity().getTotal() + ". Actual UI Value: " + nodeFromUI.get(i).getNodeGB() + "."; 
						Reports.LogResult("UI Node GB did not Match with Backend for Node " + (i+1));
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
	                ATUReports.add("Pass Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.PASSED, new CaptureScreen(
		            		   ScreenshotOf.DESKTOP));
	    			System.out.println("Nodes details in System Tab are same in UI and Backend. Test Case passed.");
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
