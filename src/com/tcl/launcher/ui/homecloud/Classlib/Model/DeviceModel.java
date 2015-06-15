package com.tcl.launcher.ui.homecloud.Classlib.Model;

import com.tcl.launcher.ui.homecloud.Classlib.Const.Const.CapacityType;



public class DeviceModel {

	//[{\"ip\":\"3232235782\",\"port\":null,\"name\":null,\"type\":\"Normal\"}]

	private String deviceId = null;
	
	private CapacityType device_type = CapacityType.memory_card;
	
	private String name = null;
	
	private String devicePwd = null;
	
	private String ip = null;
	
	private int port = 0;
	
	private String version = null;
	
	private String mac_address = null;
	
	private String device_password = null;

	private String device_total_capacity = null;
	
	private String device_used_capacity = null;

	public String getMac_address() {
		return mac_address;
	}

	public void setMac_address(String mac_address) {
		this.mac_address = mac_address;
	}

	public String getDevice_password() {
		return device_password;
	}

	public void setDevice_password(String device_password) {
		this.device_password = device_password;
	}

	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * @return the deviceId
	 */
	public String getDeviceId() {
		return deviceId;
	}

	/**
	 * @param deviceId the deviceId to set
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	/**
	 * @return the devicePwd
	 */
	public String getDevicePwd() {
		return devicePwd;
	}

	/**
	 * @param devicePwd the devicePwd to set
	 */
	public void setDevicePwd(String devicePwd) {
		this.devicePwd = devicePwd;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public CapacityType getDevice_type() {
		return device_type;
	}

	public void setDevice_type(CapacityType device_type) {
		this.device_type = device_type;
	}

	public String getDevice_total_capacity() {
		return device_total_capacity;
	}

	public void setDevice_total_capacity(String device_total_capacity) {
		this.device_total_capacity = device_total_capacity;
	}

	public String getDevice_used_capacity() {
		return device_used_capacity;
	}

	public void setDevice_used_capacity(String device_used_capacity) {
		this.device_used_capacity = device_used_capacity;
	}
	
}

