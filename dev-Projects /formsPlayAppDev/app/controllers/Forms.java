package controllers;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)

public class Forms {
	
	private String deviceType;
	private String ip;
	private Set<String> pages;
	private String startTime;
	private String geoCoordinates;
	
	private boolean isMobile;
	public boolean isMobile() {
		return isMobile;
	}
	public void setMobile(boolean isMobile) {
		this.isMobile = isMobile;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Set<String> getPages() {
		return pages;
	}
	public void setPages(Set<String> pages) {
		this.pages = pages;
	}
	public String getStartTime() {
		return startTime;
	}
	@Override
	public String toString() {
		return "Forms [deviceType=" + deviceType + ", ip=" + ip + ", pages="
				+ pages + ", startTime=" + startTime + ", geoCoordinates="
				+ geoCoordinates + "]";
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getGeoCoordinates() {
		return geoCoordinates;
	}
	public void setGeoCoordinates(String geoCoordinates) {
		this.geoCoordinates = geoCoordinates;
	}
	
	
	

}
