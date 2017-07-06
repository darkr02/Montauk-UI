package com.montauk.alerts;

public class AlertFromUI{
   	private String description;
   	private String alertName;
   	private Number alertSeverity;
   	private String starttime;
   	private String alertType;
   	
 	public String getDescription()
 	{
		return this.description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
 	public String getAlertName()
 	{
		return this.alertName;
	}
	public void setAlertName(String alertName)
	{
		this.alertName = alertName;
	}
 	public Number getAlertSeverity()
 	{
		return this.alertSeverity;
	}
	public void setAlertSeverity(Number nSeverity)
	{
		this.alertSeverity = nSeverity;
	}
 	public String getStarttime()
 	{
		return this.starttime;
	}
	public void setStarttime(String starttime)
	{
		this.starttime = starttime;
	}
 	public String getAlertType()
 	{
		return this.alertType;
	}
	public void setAlertType(String alertType)
	{
		this.alertType = alertType;
	}
}
