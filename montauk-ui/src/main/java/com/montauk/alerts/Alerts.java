package com.montauk.alerts;

public class Alerts{
   	private String alertid;
   	private String clusterid;
   	private String description;
   	private String name;
   	private String read;
   	private Number severity;
   	private String starttime;
   	private String type;
   	
   	public Alerts() {

    }

    public Alerts(String alertid, String clusterid,String description, String name,String read,Number severity, String starttime,String type) {
    	this.alertid = alertid;
    	this.clusterid = clusterid;
    	this.description = description;
    	this.name = name;
    	this.read = read;
    	this.severity = severity;
    	this.starttime = starttime;
    	this.type = type;
    }

 	public String getAlertid(){
		return this.alertid;
	}
	public void setAlertid(String alertid){
		this.alertid = alertid;
	}
 	public String getClusterid(){
		return this.clusterid;
	}
	public void setClusterid(String clusterid){
		this.clusterid = clusterid;
	}
 	public String getDescription(){
		return this.description;
	}
	public void setDescription(String description){
		this.description = description;
	}
 	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
 	public String getRead(){
		return this.read;
	}
	public void setRead(String read){
		this.read = read;
	}
 	public Number getSeverity(){
		return this.severity;
	}
	public void setSeverity(Number severity){
		this.severity = severity;
	}
 	public String getStarttime(){
		return this.starttime;
	}
	public void setStarttime(String starttime){
		this.starttime = starttime;
	}
 	public String getType(){
		return this.type;
	}
	public void setType(String type){
		this.type = type;
	}
}
