
package com.montauk.nodes;


public class NodeCardDetails{
   	private String clusterid;
   	private String clustername;
   	private String commissionstatus;
   	private String hostname;
   	private String ip;
   	private String nodeid;
   	private Number numroles;
   	private Number numservices;
   	private String owner;
   	private String rackid;
   	//private List roles;
   	private String state;
   	private Stats stats;
   	private String status;
   	private String tenant;
   	private String type;
   	
   	public NodeCardDetails() {

    }

    public NodeCardDetails(String clusterid, String clustername,String commissionstatus, String hostname,String ip,Number numroles, String nodeid, Number numservices, String owner, String rackid, String state, String tenant, String status, Stats stats, String type) 
    {
    	this.clusterid = clusterid;
    	this.clustername = clustername;
    	this.commissionstatus = commissionstatus;
    	this.hostname = hostname;
    	this.ip = ip;
    	this.numroles = numroles;
    	this.nodeid = nodeid;
    	this.numservices = numservices;
    	this.owner = owner;
    	this.rackid = rackid;
    	this.state = state;
    	this.tenant = tenant;
    	this.status = status;
    	this.stats = stats;
    	this.type = type;
    }

 	public String getClusterid(){
		return this.clusterid;
	}
	public void setClusterid(String clusterid){
		this.clusterid = clusterid;
	}
 	public String getClustername(){
		return this.clustername;
	}
	public void setClustername(String clustername){
		this.clustername = clustername;
	}
 	public String getCommissionstatus(){
		return this.commissionstatus;
	}
	public void setCommissionstatus(String commissionstatus){
		this.commissionstatus = commissionstatus;
	}
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
 	public String getNodeid(){
		return this.nodeid;
	}
	public void setNodeid(String nodeid){
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
 	public String getOwner(){
		return this.owner;
	}
	public void setOwner(String owner){
		this.owner = owner;
	}
 	public String getRackid(){
		return this.rackid;
	}
	public void setRackid(String rackid){
		this.rackid = rackid;
	}
 	/*public List getRoles(){
		return this.roles;
	}
	public void setRoles(List roles){
		this.roles = roles;
	}*/
 	public String getState(){
		return this.state;
	}
	public void setState(String state){
		this.state = state;
	}
 	public Stats getStats(){
		return this.stats;
	}
	public void setStats(Stats stats){
		this.stats = stats;
	}
 	public String getStatus(){
		return this.status;
	}
	public void setStatus(String status){
		this.status = status;
	}
 	public String getTenant(){
		return this.tenant;
	}
	public void setTenant(String tenant){
		this.tenant = tenant;
	}
 	public String getType(){
		return this.type;
	}
	public void setType(String type){
		this.type = type;
	}
}
