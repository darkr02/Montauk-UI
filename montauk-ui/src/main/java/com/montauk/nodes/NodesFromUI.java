package com.montauk.nodes;

public class NodesFromUI{
   	private String nodeIP;
   	private String nodeName;
   	private String description;
   	private String nodeStatus;
   	private String nodeType;
   	
 	public String getNodeName()
 	{
		return this.nodeName;
	}
	public void setNodeName(String nodeName)
	{
		this.nodeName = nodeName;
	}
 	public String getNodeIP()
 	{
		return this.nodeIP;
	}
	public void setNodeIP(String nodeIP)
	{
		this.nodeIP = nodeIP;
	}
 	public String getNodeDescription()
 	{
		return this.description;
	}
	public void setNodeDescription(String description)
	{
		this.description = description;
	}
 	public String getNodeStatus()
 	{
		return this.nodeStatus;
	}
	public void setNodeStatus(String nodeStatus)
	{
		this.nodeStatus = nodeStatus;
	}
 	public String getNodeType()
 	{
		return this.nodeType;
	}
	public void setNodeType(String nodeType)
	{
		this.nodeType = nodeType;
	}
}
