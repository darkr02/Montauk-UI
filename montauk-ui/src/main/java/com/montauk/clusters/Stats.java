
package com.montauk.clusters;

public class Stats{
   	private Alerts alerts;
   	private Capacity capacity;
   	private Jobs jobs;
   	private Nodes nodes;

 	public Alerts getAlerts(){
		return this.alerts;
	}
	public void setAlerts(Alerts alerts){
		this.alerts = alerts;
	}
 	public Capacity getCapacity(){
		return this.capacity;
	}
	public void setCapacity(Capacity capacity){
		this.capacity = capacity;
	}
 	public Jobs getJobs(){
		return this.jobs;
	}
	public void setJobs(Jobs jobs){
		this.jobs = jobs;
	}
 	public Nodes getNodes(){
		return this.nodes;
	}
	public void setNodes(Nodes nodes){
		this.nodes = nodes;
	}
}
