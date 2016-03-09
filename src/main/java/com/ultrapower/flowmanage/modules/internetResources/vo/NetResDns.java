/**
 * 
 */
package com.ultrapower.flowmanage.modules.internetResources.vo;

/**
 * @author acer
 *
 */
public class NetResDns {
	private String websiteName;
	private String domainName;
	private String ip;
	private String district;
	private String operator;
	private String belongAddress;
	private String bak;
	
	public String getWebsiteName() {
		return websiteName;
	}
	public void setWebsiteName(String websiteName) {
		this.websiteName = websiteName;
	}
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getBelongAddress() {
		return belongAddress;
	}
	public void setBelongAddress(String belongAddress) {
		this.belongAddress = belongAddress;
	}
	public String getBak() {
		return bak;
	}
	public void setBak(String bak) {
		this.bak = bak;
	}
	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bak == null) ? 0 : bak.hashCode());
		result = prime * result
				+ ((belongAddress == null) ? 0 : belongAddress.hashCode());
		result = prime * result
				+ ((district == null) ? 0 : district.hashCode());
		result = prime * result
				+ ((domainName == null) ? 0 : domainName.hashCode());
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result
				+ ((operator == null) ? 0 : operator.hashCode());
		result = prime * result
				+ ((websiteName == null) ? 0 : websiteName.hashCode());
		return result;
	}
	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NetResDns other = (NetResDns) obj;
		if (bak == null) {
			if (other.bak != null)
				return false;
		} else if (!bak.equals(other.bak))
			return false;
		if (belongAddress == null) {
			if (other.belongAddress != null)
				return false;
		} else if (!belongAddress.equals(other.belongAddress))
			return false;
		if (district == null) {
			if (other.district != null)
				return false;
		} else if (!district.equals(other.district))
			return false;
		if (domainName == null) {
			if (other.domainName != null)
				return false;
		} else if (!domainName.equals(other.domainName))
			return false;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		if (operator == null) {
			if (other.operator != null)
				return false;
		} else if (!operator.equals(other.operator))
			return false;
		if (websiteName == null) {
			if (other.websiteName != null)
				return false;
		} else if (!websiteName.equals(other.websiteName))
			return false;
		return true;
	}
	
	public String toString() {
		return "NetResDns [bak=" + bak + ", belongAddress=" + belongAddress
				+ ", district=" + district + ", domainName=" + domainName
				+ ", ip=" + ip + ", operator=" + operator + ", websiteName="
				+ websiteName + "]";
	}
}
