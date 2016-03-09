/**
 * 
 */
package com.ultrapower.flowmanage.modules.internetResources.vo;

/**
 * @author acer
 *
 */
public class FmIpRange {
	private String startIp;
	private String endIp;
	private String operator;
	private String area;
	private String impDate;
	
	public String getStartIp() {
		return startIp;
	}
	public void setStartIp(String startIp) {
		this.startIp = startIp;
	}
	public String getEndIp() {
		return endIp;
	}
	public void setEndIp(String endIp) {
		this.endIp = endIp;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getImpDate() {
		return impDate;
	}
	public void setImpDate(String impDate) {
		this.impDate = impDate;
	}
	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((area == null) ? 0 : area.hashCode());
		result = prime * result + ((endIp == null) ? 0 : endIp.hashCode());
		result = prime * result + ((impDate == null) ? 0 : impDate.hashCode());
		result = prime * result
				+ ((operator == null) ? 0 : operator.hashCode());
		result = prime * result + ((startIp == null) ? 0 : startIp.hashCode());
		return result;
	}
	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FmIpRange other = (FmIpRange) obj;
		if (area == null) {
			if (other.area != null)
				return false;
		} else if (!area.equals(other.area))
			return false;
		if (endIp == null) {
			if (other.endIp != null)
				return false;
		} else if (!endIp.equals(other.endIp))
			return false;
		if (impDate == null) {
			if (other.impDate != null)
				return false;
		} else if (!impDate.equals(other.impDate))
			return false;
		if (operator == null) {
			if (other.operator != null)
				return false;
		} else if (!operator.equals(other.operator))
			return false;
		if (startIp == null) {
			if (other.startIp != null)
				return false;
		} else if (!startIp.equals(other.startIp))
			return false;
		return true;
	}
	
	public String toString() {
		return "FmIpRange [area=" + area + ", endIp=" + endIp + ", impDate="
				+ impDate + ", operator=" + operator + ", startIp=" + startIp
				+ "]";
	}
	

}
