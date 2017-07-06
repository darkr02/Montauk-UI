
package com.montauk.nodes;


public class Capacity{
   	private String free;
   	private String total;
   	private String unit;
   	private String used;

 	public String getFree(){
		return this.free;
	}
	public void setFree(String free){
		this.free = free;
	}
 	public String getTotal(){
		return this.total;
	}
	public void setTotal(String total){
		this.total = total;
	}
 	public String getUnit(){
		return this.unit;
	}
	public void setUnit(String unit){
		this.unit = unit;
	}
 	public String getUsed(){
		return this.used;
	}
	public void setUsed(String used){
		this.used = used;
	}
}
