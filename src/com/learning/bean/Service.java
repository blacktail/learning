package com.learning.bean;

public class Service {
	private int id;
	private String name;
	private int globalSmAlertCond;
	private String individualAlertLevel;
	private String description;
	private String overallAlertLevel;
	private int pollingInterval;
	private int transition;
	private String url;
	private int parentId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGlobalSmAlertCond() {
		return globalSmAlertCond;
	}

	public void setGlobalSmAlertCond(int globalSmAlertCond) {
		this.globalSmAlertCond = globalSmAlertCond;
	}

	public String getIndividualAlertLevel() {
		return individualAlertLevel;
	}

	public void setIndividualAlertLevel(String individualAlertLevel) {
		this.individualAlertLevel = individualAlertLevel;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOverallAlertLevel() {
		return overallAlertLevel;
	}

	public void setOverallAlertLevel(String overallAlertLevel) {
		this.overallAlertLevel = overallAlertLevel;
	}

	public int getPollingInterval() {
		return pollingInterval;
	}

	public void setPollingInterval(int pollingInterval) {
		this.pollingInterval = pollingInterval;
	}

	public int getTransition() {
		return transition;
	}

	public void setTransition(int transition) {
		this.transition = transition;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getParentId() {
		if (parentId <= 0) {
			parentId = 1;
		}

		return parentId;
	}

	public void setParentId(int parentId) {
		if (parentId <= 0) {
			parentId = 1;
		}
		
		this.parentId= parentId;
	}
}
