
package com.montauk.clusters;

//import java.util.List;

public class Clusters{
   	private String clustername;
   	private String clusterid;
   	private String description;
   	private String hadoopversion;
   	private String namenodeipaddr;
   	private String state;
   	private Stats stats;
   	private String status;
   	private String type;

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
 	public String getDescription(){
		return this.description;
	}
	public void setDescription(String description){
		this.description = description;
	}
 	public String getHadoopversion(){
		return this.hadoopversion;
	}
	public void setHadoopversion(String hadoopversion){
		this.hadoopversion = hadoopversion;
	}
 	public String getNamenodeipaddr(){
		return this.namenodeipaddr;
	}
	public void setNamenodeipaddr(String namenodeipaddr){
		this.namenodeipaddr = namenodeipaddr;
	}
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
 	public String getType(){
		return this.type;
	}
	public void setType(String type){
		this.type = type;
	}
	
}
