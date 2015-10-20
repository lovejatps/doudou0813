/**
 * 
 */
package com.uniits.doudou.vo;

/**
 * @author wangguiyong
 * 
 */
public class AppVo {

	private Long id;

	private String appName;
	
	private String appVersion;
	
	private Long appFileId;
	
	private Long picFileId;
	
	private Long appClassify;
	
	private String createDate;
	
	private String appSize;
	
	private Long downCount;

	private String stringModle;
	
	private Long serialNumber;
	
	private String produceName;

	public AppVo() {
		super();
		// TODO Auto-generated constructor stub
	}


	public String getProduceName() {
		return produceName;
	}


	public void setProduceName(String produceName) {
		this.produceName = produceName;
	}

	
	
	
	

	public AppVo(Long id, String appName, String appVersion, Long appFileId,
			Long picFileId, Long appClassify, String createDate,
			String appSize, Long downCount, String stringModle,
			Long serialNumber) {
		super();
		this.id = id;
		this.appName = appName;
		this.appVersion = appVersion;
		this.appFileId = appFileId;
		this.picFileId = picFileId;
		this.appClassify = appClassify;
		this.createDate = createDate;
		this.appSize = appSize;
		this.downCount = downCount;
		this.stringModle = stringModle;
		this.serialNumber = serialNumber;
	}


	public AppVo(Long id, String appName, String appVersion, Long appFileId, Long picFileId, Long appClassify, String createDate, String appSize,
			Long downCount, String stringModle, Long serialNumber,String produceName) {
		super();
		this.id = id;
		this.appName = appName;
		this.appVersion = appVersion;
		this.appFileId = appFileId;
		this.picFileId = picFileId;
		this.appSize = appSize;
		this.appClassify = appClassify;
		this.createDate = createDate;
		this.downCount = downCount;
		this.stringModle = stringModle;
		this.serialNumber = serialNumber;
		this.produceName = produceName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public Long getAppFileId() {
		return appFileId;
	}

	public void setAppFileId(Long appFileId) {
		this.appFileId = appFileId;
	}

	public Long getPicFileId() {
		return picFileId;
	}

	public void setPicFileId(Long picFileId) {
		this.picFileId = picFileId;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getAppSize() {
		return appSize;
	}

	public void setAppSize(String appSize) {
		this.appSize = appSize;
	}

	public Long getAppClassify() {
		return appClassify;
	}

	public void setAppClassify(Long appClassify) {
		this.appClassify = appClassify;
	}

	public Long getDownCount() {
		return downCount;
	}

	public void setDownCount(Long downCount) {
		this.downCount = downCount;
	}

	public String getStringModle() {
		return stringModle;
	}

	public void setStringModle(String stringModle) {
		this.stringModle = stringModle;
	}

	public Long getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Long serialNumber) {
		this.serialNumber = serialNumber;
	}

}
