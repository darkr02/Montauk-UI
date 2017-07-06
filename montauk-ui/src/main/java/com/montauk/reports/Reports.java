package com.montauk.reports;

import java.io.File;
import java.io.FileOutputStream;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.montauk.login.LoginTest;

public class Reports 
{
	//------------------------------------------------
	public static void CaptureScreenShot(String strScreenshotFileName) throws Exception
	{
		File scrFile = ((TakesScreenshot)LoginTest.oBrowser).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir") + "\\src\\main\\resources\\Results\\"+strScreenshotFileName));
	}
	
	//------------------------------------------------
	public static void LogResult(String strData) throws Exception {
		FileOutputStream fos = new FileOutputStream(System.getProperty("user.dir")+ "\\src\\main\\resources\\Results\\Result.html",true);
		fos.write(strData.getBytes());
		fos.write("<br>".getBytes());
		fos.close();
	}

}
