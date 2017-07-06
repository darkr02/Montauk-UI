package com.montauk.login;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.montauk.generic.Generic;
import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;


public class Logout {
	//--------------------------------------Verifying No. of Alerts present in Monitor Tab---------------------------------------
	@Test
	public void BrowserClose() 
	{
	  try
		{
		  	Generic.setAuthorInfoForReports();
			LoginTest.oBrowser.quit();
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
}
