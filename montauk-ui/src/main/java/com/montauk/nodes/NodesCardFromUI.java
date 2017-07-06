package com.montauk.nodes;

public class NodesCardFromUI{
   	private String nodeGB;
   	private String nodeName;
   	private Number nodeAlert;
   	private String nodeStatus;
   	private String nodeGBAvailable;
   	private String nodeGBUsed;
   	
   	
 	public String getNodeName()
 	{
		return this.nodeName;
	}
	public void setNodeName(String nodeName)
	{
		this.nodeName = nodeName;
	}
 	public String getNodeGB()
 	{
		return this.nodeGB;
	}
	public void setNodeGB(String nodeGB)
	{
		this.nodeGB = nodeGB;
	}
 	public Number getNodeAlert()
 	{
		return this.nodeAlert;
	}
	public void setNodeAlert(Number nodeAlert)
	{
		this.nodeAlert = nodeAlert;
	}
 	public String getNodeStatus()
 	{
		return this.nodeStatus;
	}
	public void setNodeStatus(String nodeStatus)
	{
		this.nodeStatus = nodeStatus;
	}
 	public String getNodeGBAvailable()
 	{
		return this.nodeGBAvailable;
	}
	public void setNodeGBAvailable(String nodeGBAvailable)
	{
		this.nodeGBAvailable = nodeGBAvailable;
	}
 	public String getNodeGBUsed()
 	{
		return this.nodeGBUsed;
	}
	public void setNodeGBUsed(String nodeGBUsed)
	{
		this.nodeGBUsed = nodeGBUsed;
	}

}
