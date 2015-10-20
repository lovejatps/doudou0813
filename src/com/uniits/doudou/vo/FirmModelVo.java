/**
 * 
 */
package com.uniits.doudou.vo;

import org.nutz.dao.entity.annotation.Column;

/**
 * @author 
 * 
 */
public class FirmModelVo {

	@Column("id")
	private Long id;

	@Column("firm_version")
	private String firmVersion;

	@Column("file_id")
	private Long fileId;

	@Column("file_name")
	private String fileName;

	public FirmModelVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FirmModelVo(Long id, String firmVersion, Long fileId, String fileName) {
		super();
		this.id = id;
		this.firmVersion = firmVersion;
		this.fileId = fileId;
		this.fileName = fileName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirmVersion() {
		return firmVersion;
	}

	public void setFirmVersion(String firmVersion) {
		this.firmVersion = firmVersion;
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

}
