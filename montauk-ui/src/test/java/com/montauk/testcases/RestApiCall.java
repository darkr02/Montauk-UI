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


import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.yaml.snakeyaml.parser.ParserImpl;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;
import com.montauk.backend.ExcelUtils;
import com.montauk.generic.Mytools;

/**
 * This class holds the Alerts Functionality in MonitorTab.
 * @author Krishnendu.Daripa
 */

public class RestApiCall 
{
	//public static WebDriver oBrowser;
	{
		System.setProperty("atu.reporter.config", System.getProperty("user.dir") + "\\src\\main\\java\\ca\\montauk\\objectrepo\\atu.properties");
	}
//	@BeforeSuite
//	public void AUT_Suite_Init()
//	{
//		try
//		{
//			Generic.setIndexPageDescription();	
//			Generic.setAuthorInfoForReports();
//			System.out.println(" \n");
//			System.out.println("Execution started on the Browser: Chrome");
//			Generic.LaunchBrowser("chrome"); 
//		}
//		catch(Throwable e)
//		{
//			e.printStackTrace();
//			Assert.fail(e.getMessage());
//		}
//	}
	//------------------------------Navigating to Monitor Tab----------------------------------------
//	@Test(priority=1)
//	@DataProvider(name = "InputData")
	@Test(dataProvider = "InputData")
	public void GoingToRestURL(String suite, String tcNo, String testDesc, String restUrl, String ExpectedRow, String ExpectedColumn) throws Exception
	{
		
		try
		{
//			WebElement oResult=null;
//			String RestUrl="http://localhost:8080/hello-world";
//			String ExpectedOutPut = "{\"id\":6,\"content\":\"Hello, Stranger!\"}";
//			oBrowser.get(restUrl);
//			ATUReports.setWebDriver(oBrowser);
			Mytools.WaitFor(5L);
//			oResult = oBrowser.findElement(By.xpath("//pre" ));
//			Request request = new Request();
//			request.setur
//			  request.setUri("localhost:8080/db2t/api/v1/tables");
//			  request.addHeader("authorization", "Basic cWFvZmE6MTFxYW9mYQ==");
			//cWFvZmE6MTFxYW9mYQ==
//			  
			  String authStringEnc = "cWFvZmE6MTFxYW9mYQ==";
			  
			  URL url = new URL(restUrl);
			  URLConnection urlConnection = url.openConnection();
				urlConnection.setRequestProperty("Authorization", "Basic " + authStringEnc);
				InputStream is = urlConnection.getInputStream();
				InputStreamReader isr = new InputStreamReader(is);

				int numCharsRead;
				char[] charArray = new char[1024];
				StringBuffer sb = new StringBuffer();
				while ((numCharsRead = isr.read(charArray)) > 0) {
					sb.append(charArray, 0, numCharsRead);
				}
				String actualJson = sb.toString();
			System.out.println("JSon: " + actualJson);   
			JSONParser parser = new JSONParser();
			JSONArray array = (JSONArray)parser.parse(actualJson);
			int count = array.size();
			System.out.println("Json has: " + count + " objects..");
			boolean bFlag = true;
			for(int i=0;i<count;i++){
				org.json.simple.JSONObject objectI = (org.json.simple.JSONObject) array.get(i);
				//System.out.println("Object" + i +" has: " + objectI.size() + " columns..");
				
				if (objectI.size() != Integer.parseInt(ExpectedColumn))
				{
					bFlag = false;
				}
			}
			
			if ((Integer.parseInt(ExpectedRow) == count) && bFlag == true) 
			{
				System.out.println("Execution Passed for:" + tcNo + " And All objects have " + ExpectedColumn + " Columns");
				ATUReports.add("Pass Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.PASSED, new CaptureScreen(
						ScreenshotOf.DESKTOP));
			}
			else
			{
				System.out.println("Execution Failed for:" + tcNo );
				ATUReports.add("Fail Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.FAILED, new CaptureScreen(
						ScreenshotOf.DESKTOP));
			}
			
//			if (oResult.getText().equals(ExpectedOutPut)) 
//			{
//				System.out.println("Execution Passed for:" + tcNo );
//				ATUReports.add("Pass Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.PASSED, new CaptureScreen(
//						ScreenshotOf.DESKTOP));
//			}
//			else
//			{
//				System.out.println("Execution Failed for:" + tcNo );
//				ATUReports.add("Fail Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.FAILED, new CaptureScreen(
//						ScreenshotOf.DESKTOP));
//			}
			
			
		}
		catch(Throwable e)
		{
			e.printStackTrace();
			Assert.fail(e.getMessage());
			ATUReports.add("Fail Test Case " + Thread.currentThread().getStackTrace()[1].getMethodName(),LogAs.FAILED, new CaptureScreen(
					ScreenshotOf.DESKTOP));
		}
	}
//	@AfterSuite
//	public void AUT_Suite_TearDown()
//	{
//		try
//		{
//			oBrowser.close();
//		}
//		catch(Throwable e)
//		{
//			e.printStackTrace();
//			Assert.fail(e.getMessage());
//		}
//	}
	@DataProvider

    public Object[][] InputData() throws Exception{

//         Object[][] testObjArray = ExcelUtils.getTableArray("../../main/resources/InputExcel/InputSheet.xlsx","Sheet1");
		Object[][] testObjArray = ExcelUtils.getTableArray(System.getProperty("user.dir") + "\\src\\main\\resources\\InputExcel\\InputSheet.xlsx","Sheet1");
		
         return (testObjArray);

		}
}
