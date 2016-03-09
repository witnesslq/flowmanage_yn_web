package com.ultrapower.flowmanage.modules.flow.vo;
/**
 * 流量流向分析自定义vo
 * 
 * @author Administrator
 *
 */
public class CustomFlowDirection {
	//采集时间
	private String collectionTime;
	//流向Id
	private String flowDirectionId;
	//流向名称
	private String flowDirectionName;
	//业务
	private String business;
	//日均流量（TB）或者访问流量（TB）
	private String flow;
	//占比（%）
	private String proportion;
	//出口方向
	private String exportDirection;
	//地市
	private String city;
	//指标名称
	private String indexName;
	//指标值
	private String indexValue;
	//环比
	private String linkRelativeRatio;
	public String getCollectionTime() {
		return collectionTime;
	}
	public void setCollectionTime(String collectionTime) {
		this.collectionTime = collectionTime;
	}
	public String getBusiness() {
		return business;
	}
	public void setBusiness(String business) {
		this.business = business;
	}
	public String getFlow() {
		return flow;
	}
	public void setFlow(String flow) {
		this.flow = flow;
	}
	public String getProportion() {
		return proportion;
	}
	public void setProportion(String proportion) {
		this.proportion = proportion;
	}
	public String getExportDirection() {
		return exportDirection;
	}
	public void setExportDirection(String exportDirection) {
		this.exportDirection = exportDirection;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getIndexName() {
		return indexName;
	}
	public void setIndexName(String indexName) {
		this.indexName = indexName;
	}
	public String getIndexValue() {
		return indexValue;
	}
	public void setIndexValue(String indexValue) {
		this.indexValue = indexValue;
	}
	public String getLinkRelativeRatio() {
		return linkRelativeRatio;
	}
	public void setLinkRelativeRatio(String linkRelativeRatio) {
		this.linkRelativeRatio = linkRelativeRatio;
	}
	public String getFlowDirectionId() {
		return flowDirectionId;
	}
	public void setFlowDirectionId(String flowDirectionId) {
		this.flowDirectionId = flowDirectionId;
	}
	public String getFlowDirectionName() {
		return flowDirectionName;
	}
	public void setFlowDirectionName(String flowDirectionName) {
		this.flowDirectionName = flowDirectionName;
	}

}
