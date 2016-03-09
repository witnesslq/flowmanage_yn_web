package com.ultrapower.flowmanage.modules.health.vo;

public class CountryContrastVo {
	
	
	private int provinceId;
	
	private String province;
	
	private String score;
	
	private String collectTime;
	
	private int rownum;

	

	public int getRownum() {
		return rownum;
	}

	public void setRownum(int rownum) {
		this.rownum = rownum;
	}

	public int getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	
	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getCollectTime() {
		return collectTime;
	}

	public void setCollectTime(String collectTime) {
		this.collectTime = collectTime;
	}
	
	

}
