package com.montauk.nodes;

public class Roles{
   	private boolean actionsupported;
   	private String id;
   	private String name;
   	private String nodeid;
   	private String servicegroup;
   	private String servicegroupdisplayname;
   	private String state;
   	private String status;

   	
   	
   	public Roles() {

    }

    public Roles(boolean actionsupported, String id,String name, String nodeid,String servicegroupdisplayname,String servicegroup, String state, String status) 
    {
    	this.actionsupported = actionsupported;
    	this.id = id;
    	this.name = name;
    	this.nodeid = nodeid;
    	this.servicegroup = servicegroup;
    	this.servicegroupdisplayname = servicegroupdisplayname;
    	this.state = state;
    	this.status = status;
    }

 	public boolean getActionsupported(){
		return this.actionsupported;
	}
	public void setActionsupported(boolean actionsupported){
		this.actionsupported = actionsupported;
	}
 	public String getId(){
		return this.id;
	}
	public void setId(String id){
		this.id = id;
	}
 	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
 	public String getNodeid(){
		return this.nodeid;
	}
	public void setNodeid(String nodeid){
		this.nodeid = nodeid;
	}
 	public String getServicegroup(){
		return this.servicegroup;
	}
	public void setServicegroup(String servicegroup){
		this.servicegroup = servicegroup;
	}
 	public String getServicegroupdisplayname(){
		return this.servicegroupdisplayname;
	}
	public void setServicegroupdisplayname(String servicegroupdisplayname){
		this.servicegroupdisplayname = servicegroupdisplayname;
	}
 	public String getState(){
		return this.state;
	}
	public void setState(String state){
		this.state = state;
	}
 	public String getStatus(){
		return this.status;
	}
	public void setStatus(String status){
		this.status = status;
	}
}
