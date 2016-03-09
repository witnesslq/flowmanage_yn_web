package com.ultrapower.flowmanage.modules.userPermissions.vo;

public class UserInfo {
	private String username;
	private boolean urlExitFlag = false;
	private String erre;
	private String url;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public boolean isUrlExitFlag() {
		return urlExitFlag;
	}
	public void setUrlExitFlag(boolean urlExitFlag) {
		this.urlExitFlag = urlExitFlag;
	}
	public String getErre() {
		return erre;
	}
	public void setErre(String erre) {
		this.erre = erre;
	}
	
}
