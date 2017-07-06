package com.montauk.nodes;

import java.util.List;

public class Nodes{
   	private String commissionstatus;
   	private Conf conf;
   	private String hostname;
   	private String ip;
   	private Number nodeid;
   	private Number numroles;
   	private Number numservices;
   	private String rackid;
   	private List roles;
   	private String status;
   	private String type;

 	public String getCommissionstatus(){
		return this.commissionstatus;
	}
	public void setCommissionstatus(String commissionstatus){
		this.commissionstatus = commissionstatus;
	}
 	/*public Conf getConf(){
		return this.conf;
	}
	public void setConf(Conf conf){
		this.conf = conf;
	}*/
 	public String getHostname(){
		return this.hostname;
	}
	public void setHostname(String hostname){
		this.hostname = hostname;
	}
 	public String getIp(){
		return this.ip;
	}
	public void setIp(String ip){
		this.ip = ip;
	}
 	public Number getNodeid(){
		return this.nodeid;
	}
	public void setNodeid(Number nodeid){
		this.nodeid = nodeid;
	}
 	public Number getNumroles(){
		return this.numroles;
	}
	public void setNumroles(Number numroles){
		this.numroles = numroles;
	}
 	public Number getNumservices(){
		return this.numservices;
	}
	public void setNumservices(Number numservices){
		this.numservices = numservices;
	}
 	public String getRackid(){
		return this.rackid;
	}
	public void setRackid(String rackid){
		this.rackid = rackid;
	}
 	public List getRoles(){
		return this.roles;
	}
	public void setRoles(List roles){
		this.roles = roles;
	}
 	public String getStatus(){
		return this.status;
	}
	public void setStatus(String status){
		this.status = status;
	}
 	public String getType(){
		return this.type;
	}
	public void setType(String type){
		this.type = type;
	}
}
