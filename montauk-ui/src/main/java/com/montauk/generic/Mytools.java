package com.montauk.generic;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;






//import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
//import org.testng.Assert;


public class Mytools 
{
	public Mytools(){
		
	}
	
	public static void WaitFor(long seconds)
	{
		try
		{
			Thread.sleep(seconds *1000);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	 public static String DateFormatter(long strDate1) throws java.text.ParseException 
	  {
		  //long strDate1 = 1412716355033l;
		 
		  Date date=new Date(strDate1 * 1000L);
		  SimpleDateFormat df2 = new SimpleDateFormat("MM/dd/yy h:mm a");
		  String dateText = df2.format(date);
	      return dateText;
	  }
	//------------------------------------------------
	public static FirefoxProfile getFireFoxProxyProfile() 
	{	
		try
		{
			FirefoxProfile oProfile = new FirefoxProfile();
			oProfile.setPreference("network.proxy.autoconfig_url", "http://wpad.ca.com/wpad.dat");
			oProfile.setPreference("network.proxy.type", 2);
			return oProfile;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	//------------------------------------------------
	public static FirefoxProfile getDefaultFireFoxProfile() 
	{	
		try
		{
			String sPath;
			FirefoxProfile oProfile;
			sPath = "C:\\Users\\Darkr02\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles\\etwjluso.default";
			oProfile = new FirefoxProfile(new File(sPath));
			return oProfile;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}
	
	//------------------------------------------------
	public static DesiredCapabilities getProxyCapability(String sBrType)
	{
		try
		{
			Proxy oProxy;
			DesiredCapabilities oCapability;
			oProxy = new Proxy();
			//oProxy.setProxyType(ProxyType.AUTODETECT);
			//oProxy.setAutodetect(true);
			oProxy.setProxyAutoconfigUrl("http://wpad.ca.com/wpad.dat");
			oCapability = new DesiredCapabilities();
			oCapability.setJavascriptEnabled(true);
			oCapability.setCapability(CapabilityType.PROXY, oProxy);
			//oCapability.setCapability(Chrome, value);
		
			if(sBrType.equalsIgnoreCase("ie"))  
			{
				oCapability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
				oCapability.setCapability(InternetExplorerDriver.ENABLE_ELEMENT_CACHE_CLEANUP, true);
				//oCapability.setVersion("9");
				System.out.println(oCapability.getVersion());
				//oCapability.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
				//oCapability.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION,true);
				
				
				//oSession.utilReplaceInResponse("</title>", "</title><meta http-equiv=\"X-UA-Compatible\" content=\"IE=8\" >");
			}
			
				
			
			return oCapability;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

	//------------------------------------------------
	
	public static void WaitForElementToBeLoaded(String sElement)
	{
		int nCounter = 0;
		do
		{
			if (Generic.CheckForElementVisibility(sElement)==true)
			{
				break; 
			}
			else
			{
				nCounter++;
				Mytools.WaitFor(1L);
			}
	    }while( nCounter < 20 );
	}

	//------------------------------------------------
	//------------------------------------------------
	//------------------------------------------------
	//------------------------------------------------
	//------------------------------------------------
}
