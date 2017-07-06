package com.montauk.clusters;

public class Cluster {

	private String clusterName;

	private String clusterDescription;

	private String clusterType;

	private String clusterStatus;

	private Number jobs;

	private Number nodes;

	private Number id;

	private String gb;

	private Number alerts;

	private String diskSpaceUsed;

	private String diskSpaceAvailable;

	public String getClusterName() {
		return clusterName;
	}

	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}
	public String getClusterDescripton() {
		return clusterDescription;
	}

	public void setClusterDescripton(String clusterDescription) {
		this.clusterDescription = clusterDescription;
	}

	public String getClusterType() {
		return clusterType;
	}

	public void setClusterType(String clusterType) {
		this.clusterType = clusterType;
	}

	public String getClusterStatus() {
		return clusterStatus;
	}

	public void setClusterStatus(String clusterStatus) {
		this.clusterStatus = clusterStatus;
	}

	public Number getJobs() {
		return jobs;
	}

	public void setJobs(Number jobs) {
		this.jobs = jobs;
	}

	public Number getNodes() {
		return nodes;
	}

	public void setNodes(Number nodes) {
		this.nodes = nodes;
	}

	public String getGb() {
		return gb;
	}

	public void setGb(String gb) {
		this.gb = gb;
	}

	public Number getID() {
		return id;
	}

	public void setID(Number id) {
		this.id = id;
	}

	public Number getAlerts() {
		return alerts;
	}

	public void setAlerts(Number alerts) {
		this.alerts = alerts;
	}
	public String getDiskSpaceUsed() {
		return diskSpaceUsed;
	}

	public void setDiskSpaceUsed(String diskSpaceUsed) {
		this.diskSpaceUsed = diskSpaceUsed;
	}

	public String getDiskSpaceAvailable() {
		return diskSpaceAvailable;
	}

	public void setDiskSpaceAvailable(String diskSpaceAvailable) {
		this.diskSpaceAvailable = diskSpaceAvailable;
	}

}
