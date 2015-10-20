/**
 * 
 */
package com.uniits.doudou.vo;

/**
 * @author 成卫
 * 
 */
public class SystemPackageVo {

	private Long id;

	private String sysVersion;

	private Long type;

	private Long fileId;

	private String fileName;
	
	private String createDate;
	
	private String stringModle;
	
	private Long serialNumber;
	
	private String producename;
	
	
	

	public SystemPackageVo(Long id, String sysVersion, Long type, Long fileId,
			String fileName, String createDate, String stringModle,
			Long serialNumber, String producename) {
		super();
		this.id = id;
		this.sysVersion = sysVersion;
		this.type = type;
		this.fileId = fileId;
		this.fileName = fileName;
		this.createDate = createDate;
		this.stringModle = stringModle;
		this.serialNumber = serialNumber;
		this.producename = producename;
	}

	public String getProducename() {
		return producename;
	}

	public void setProducename(String producename) {
		this.producename = producename;
	}

	public SystemPackageVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SystemPackageVo(Long id, String sysVersion, Long type, Long fileId, String fileName,
			String createDate, String stringModle, Long serialNumber) {
		super();
		this.id = id;
		this.sysVersion = sysVersion;
		this.type = type;
		this.fileId = fileId;
		this.fileName = fileName;
		this.createDate = createDate;
		this.stringModle = stringModle;
		this.serialNumber = serialNumber;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSysVersion() {
		return sysVersion;
	}

	public void setSysVersion(String sysVersion) {
		this.sysVersion = sysVersion;
	}

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
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
