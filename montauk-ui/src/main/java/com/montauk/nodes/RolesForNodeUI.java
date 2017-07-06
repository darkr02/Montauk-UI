package com.montauk.nodes;

public class RolesForNodeUI{
   	private boolean actionsupported;
   	private String roleName;
   	private String roleButtonName;
   	private String roleState;
   	private String roleStatus;

 	public boolean getActionsupported(){
		return this.actionsupported;
	}
	public void setActionsupported(boolean actionsupported){
		this.actionsupported = actionsupported;
	}
 	public String getName(){
		return this.roleName;
	}
	public void setName(String name){
		this.roleName = name;
	}
 	public String getButtonName(){
		return this.roleButtonName;
	}
	public void setButtonName(String name){
		this.roleButtonName = name;
	}
 	public String getState(){
		return this.roleState;
	}
	public void setState(String state){
		this.roleState = state;
	}
 	public String getStatus(){
		return this.roleStatus;
	}
	public void setStatus(String status){
		this.roleStatus = status;
	}
}
