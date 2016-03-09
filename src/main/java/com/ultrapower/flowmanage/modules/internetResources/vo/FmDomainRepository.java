/**
 * 
 */
package com.ultrapower.flowmanage.modules.internetResources.vo;

import java.math.BigInteger;

/**
 * @author zouhaibo
 *
 */
public class FmDomainRepository {
//	private BigInteger serialNum;
	private String websiteName;
	private String domainName;
	private String ip;
	private String attribution;
	private String impDate;
	
//	public BigInteger getSerialNum() {
//		return serialNum;
//	}
//	public void setSerialNum(BigInteger serialNum) {
//		this.serialNum = serialNum;
//	}
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
	public String getAttribution() {
		return attribution;
	}
	public void setAttribution(String attribution) {
		this.attribution = attribution;
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
		result = prime * result
				+ ((attribution == null) ? 0 : attribution.hashCode());
		result = prime * result
				+ ((domainName == null) ? 0 : domainName.hashCode());
		result = prime * result + ((impDate == null) ? 0 : impDate.hashCode());
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
//		result = prime * result
//				+ ((serialNum == null) ? 0 : serialNum.hashCode());
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
		FmDomainRepository other = (FmDomainRepository) obj;
		if (attribution == null) {
			if (other.attribution != null)
				return false;
		} else if (!attribution.equals(other.attribution))
			return false;
		if (domainName == null) {
			if (other.domainName != null)
				return false;
		} else if (!domainName.equals(other.domainName))
			return false;
		if (impDate == null) {
			if (other.impDate != null)
				return false;
		} else if (!impDate.equals(other.impDate))
			return false;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
//		if (serialNum == null) {
//			if (other.serialNum != null)
//				return false;
//		} else if (!serialNum.equals(other.serialNum))
//			return false;
		if (websiteName == null) {
			if (other.websiteName != null)
				return false;
		} else if (!websiteName.equals(other.websiteName))
			return false;
		return true;
	}
	
	public String toString() {
		return "FmDomainRepository [attribution=" + attribution
				+ ", domainName=" + domainName + ", impDate=" + impDate
				+ ", ip=" + ip
//				+ ", serialNum=" + serialNum 
				+ ", websiteName="
				+ websiteName + "]";
	}
}
