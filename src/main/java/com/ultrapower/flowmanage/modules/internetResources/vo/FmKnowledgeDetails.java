/**
 * 
 */
package com.ultrapower.flowmanage.modules.internetResources.vo;

/**
 * @author acer
 *
 */
public class FmKnowledgeDetails {
	private String kid;
	private String port;
	private String host;
	private String effect;
	private String ip;
	private String belong;
	
	
	public String getKid() {
		return kid;
	}
	public void setKid(String kid) {
		this.kid = kid;
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
	public String getBelong() {
		return belong;
	}
	public void setBelong(String belong) {
		this.belong = belong;
	}
	
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((belong == null) ? 0 : belong.hashCode());
		result = prime * result + ((effect == null) ? 0 : effect.hashCode());
		result = prime * result + ((host == null) ? 0 : host.hashCode());
		result = prime * result + ((ip == null) ? 0 : ip.hashCode());
		result = prime * result + ((kid == null) ? 0 : kid.hashCode());
		result = prime * result + ((port == null) ? 0 : port.hashCode());
		return result;
	}
	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FmKnowledgeDetails other = (FmKnowledgeDetails) obj;
		if (belong == null) {
			if (other.belong != null)
				return false;
		} else if (!belong.equals(other.belong))
			return false;
		if (effect == null) {
			if (other.effect != null)
				return false;
		} else if (!effect.equals(other.effect))
			return false;
		if (host == null) {
			if (other.host != null)
				return false;
		} else if (!host.equals(other.host))
			return false;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		if (kid == null) {
			if (other.kid != null)
				return false;
		} else if (!kid.equals(other.kid))
			return false;
		if (port == null) {
			if (other.port != null)
				return false;
		} else if (!port.equals(other.port))
			return false;
		return true;
	}
	
	public String toString() {
		return "FmKnowledgeDetails [belong=" + belong + ", effect=" + effect
				+ ", host=" + host + ", ip=" + ip + ", kid=" + kid + ", port="
				+ port + "]";
	}
}
