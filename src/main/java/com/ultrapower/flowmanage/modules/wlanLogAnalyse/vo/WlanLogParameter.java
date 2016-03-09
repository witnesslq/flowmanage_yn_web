package com.ultrapower.flowmanage.modules.wlanLogAnalyse.vo;

public class WlanLogParameter {
	private String userName;
	private String[] action;
	private String[] result;
	private String startTime;
	private String endTime;
	private int pageNo = 1;
	private int pageSize = 10;
	
	public WlanLogParameter(){}
	
	/*
	public WlanLogParameter(String userName, String[] action, String[] result,
			String startTime, String endTime, int pageNo, int pageSize) {
		super();
		this.userName = userName;
		this.action = action;
		this.result = result;
		this.startTime = startTime;
		this.endTime = endTime;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}
	*/
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String[] getAction() {
		return action;
	}
	public void setAction(String[] action) {
		this.action = action;
	}
	public String[] getResult() {
		return result;
	}
	public void setResult(String[] result) {
		this.result = result;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
}
