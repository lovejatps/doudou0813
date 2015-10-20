/**
 * 
 */
package com.uniits.doudou.vo;

/**
 * @author 成卫
 * 
 */
public class FirmwareVo {

	private Long id;

	private String version;

	private Long fileId;

	private String fileName;
	
	private String createDate;
	
	private String stringModle;
	
	private Long serialNumber;
	
	private String producename;


	public FirmwareVo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getProducename() {
		return producename;
	}

	public void setProducename(String producename) {
		this.producename = producename;
	}

	public FirmwareVo(Long id, String version, Long fileId, String fileName,
			String createDate, String stringModle, Long serialNumber,
			String producename) {
		super();
		this.id = id;
		this.version = version;
		this.fileId = fileId;
		this.fileName = fileName;
		this.createDate = createDate;
		this.stringModle = stringModle;
		this.serialNumber = serialNumber;
		this.producename = producename;
	}

	public FirmwareVo(Long id, String version, Long fileId, String fileName,
			String createDate, String stringModle, Long serialNumber) {
		super();
		this.id = id;
		this.version = version;
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

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
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
