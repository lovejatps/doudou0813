/**
 * 
 */
package com.uniits.doudou.vo;

/**
 * @author wangguiyong
 * 
 */
public class AnimationVo {

	private Long id;

	private String name;

	private Long fileId;

	private String fileName;
	
	private String createDate;
	
	private String stringModle;
	
	private Long serialNumber;
	
	private String produceName;
	

	
	
	public AnimationVo(Long id, String name, Long fileId, String fileName,
			String createDate, String stringModle, Long serialNumber,
			String produceName) {
		super();
		this.id = id;
		this.name = name;
		this.fileId = fileId;
		this.fileName = fileName;
		this.createDate = createDate;
		this.stringModle = stringModle;
		this.serialNumber = serialNumber;
		this.produceName = produceName;
	}

	public String getProduceName() {
		return produceName;
	}

	public void setProduceName(String produceName) {
		this.produceName = produceName;
	}

	public AnimationVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AnimationVo(Long id, String name, Long fileId, String fileName,
			String createDate, String stringModle, Long serialNumber) {
		super();
		this.id = id;
		this.name = name;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
