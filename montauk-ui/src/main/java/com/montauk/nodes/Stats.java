
package com.montauk.nodes;

import java.util.List;

public class Stats{
   	private Alerts alerts;
   	private Capacity capacity;

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
}
