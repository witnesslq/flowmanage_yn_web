/**
 * 
 */
package com.ultrapower.flowmanage.modules.internetResources.vo;

/**
 * @author acer
 *
 */
public class FmKnowledge {
	private int kid;
	private String kname;
	private String ktype;
	private String versions;
	private String platforms;
	private String corporation;
	private String function;
	private String feature;
	private String usergroup;
	private String protocols;
	private String ports;
	private String port;
	private String host;
	private String effect;
	private String ip;
	private String belong;
	private String impDate;
	
	public String getImpDate() {
		return impDate;
	}
	public void setImpDate(String impDate) {
		this.impDate = impDate;
	}
	public String getBelong() {
		return belong;
	}
	public void setBelong(String belong) {
		this.belong = belong;
	}
	public int getKid() {
		return kid;
	}
	public void setKid(int kid) {
		this.kid = kid;
	}
	public String getKname() {
		return kname;
	}
	public void setKname(String kname) {
		this.kname = kname;
	}
	public String getKtype() {
		return ktype;
	}
	public void setKtype(String ktype) {
		this.ktype = ktype;
	}
	public String getVersions() {
		return versions;
	}
	public void setVersions(String versions) {
		this.versions = versions;
	}
	public String getPlatforms() {
		return platforms;
	}
	public void setPlatforms(String platforms) {
		this.platforms = platforms;
	}
	public String getCorporation() {
		return corporation;
	}
	public void setCorporation(String corporation) {
		this.corporation = corporation;
	}
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}
	public String getFeature() {
		return feature;
	}
	public void setFeature(String feature) {
		this.feature = feature;
	}
	public String getUsergroup() {
		return usergroup;
	}
	public void setUsergroup(String usergroup) {
		this.usergroup = usergroup;
	}
	public String getProtocols() {
		return protocols;
	}
	public void setProtocols(String protocols) {
		this.protocols = protocols;
	}
	public String getPorts() {
		return ports;
	}
	public void setPorts(String ports) {
		this.ports = ports;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getEffect() {
		return effect;
	}
	public void setEffect(String effect) {
		this.effect = effect;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((belong == null) ? 0 : belong.hashCode());
		result = prime * result
				+ ((corporation == null) ? 0 : corporation.hashCode());
		result = prime * result + ((effect == null) ? 0 : effect.hashCode());
		result = prime * result + ((feature == null) ? 0 : feature.hashCode());
		result = prime * result
				+ ((function == null) ? 0 : function.hashCode());
		result = prime * result + ((host == null) ? 0 : host.hashCode());
		result = prime * result + ((impDate == null) ? 0 : impDate.hashCode());
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result + kid;
		result = prime * result + ((kname == null) ? 0 : kname.hashCode());
		result = prime * result + ((ktype == null) ? 0 : ktype.hashCode());
		result = prime * result
				+ ((platforms == null) ? 0 : platforms.hashCode());
		result = prime * result + ((port == null) ? 0 : port.hashCode());
		result = prime * result + ((ports == null) ? 0 : ports.hashCode());
		result = prime * result
				+ ((protocols == null) ? 0 : protocols.hashCode());
		result = prime * result
				+ ((usergroup == null) ? 0 : usergroup.hashCode());
		result = prime * result
				+ ((versions == null) ? 0 : versions.hashCode());
		return result;
	}
	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FmKnowledge other = (FmKnowledge) obj;
		if (belong == null) {
			if (other.belong != null)
				return false;
		} else if (!belong.equals(other.belong))
			return false;
		if (corporation == null) {
			if (other.corporation != null)
				return false;
		} else if (!corporation.equals(other.corporation))
			return false;
		if (effect == null) {
			if (other.effect != null)
				return false;
		} else if (!effect.equals(other.effect))
			return false;
		if (feature == null) {
			if (other.feature != null)
				return false;
		} else if (!feature.equals(other.feature))
			return false;
		if (function == null) {
			if (other.function != null)
				return false;
		} else if (!function.equals(other.function))
			return false;
		if (host == null) {
			if (other.host != null)
				return false;
		} else if (!host.equals(other.host))
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
		if (kid != other.kid)
			return false;
		if (kname == null) {
			if (other.kname != null)
				return false;
		} else if (!kname.equals(other.kname))
			return false;
		if (ktype == null) {
			if (other.ktype != null)
				return false;
		} else if (!ktype.equals(other.ktype))
			return false;
		if (platforms == null) {
			if (other.platforms != null)
				return false;
		} else if (!platforms.equals(other.platforms))
			return false;
		if (port == null) {
			if (other.port != null)
				return false;
		} else if (!port.equals(other.port))
			return false;
		if (ports == null) {
			if (other.ports != null)
				return false;
		} else if (!ports.equals(other.ports))
			return false;
		if (protocols == null) {
			if (other.protocols != null)
				return false;
		} else if (!protocols.equals(other.protocols))
			return false;
		if (usergroup == null) {
			if (other.usergroup != null)
				return false;
		} else if (!usergroup.equals(other.usergroup))
			return false;
		if (versions == null) {
			if (other.versions != null)
				return false;
		} else if (!versions.equals(other.versions))
			return false;
		return true;
	}
	
	public String toString() {
		return "FmKnowledge [kid=" + kid + ", kname=" + kname + ", host="
				+ host + ", belong=" + belong + ", ip=" + ip + ", corporation="
				+ corporation + ", effect=" + effect + ", feature=" + feature
				+ ", function=" + function + ", ktype=" + ktype
				+ ", platforms=" + platforms + ", port=" + port + ", ports="
				+ ports + ", protocols=" + protocols + ", usergroup="
				+ usergroup + ", versions=" + versions + ", impDate=" + impDate
				+ "]";
	}
}
