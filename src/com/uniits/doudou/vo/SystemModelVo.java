/**
 * 
 */
package com.uniits.doudou.vo;

import org.nutz.dao.entity.annotation.Column;

/**
 * @author 
 * 
 */
public class SystemModelVo {

	@Column("id")
	private Long id;

	@Column("sys_version")
	private String sysVersion;

	@Column("type")
	private Long type;

	@Column("file_id")
	private Long fileId;

	@Column("file_name")
	private String fileName;
	
	@Column("describe")
	private String describe;

	public SystemModelVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SystemModelVo(Long id, String sysVersion, Long type, Long fileId, String fileName, String describe) {
		super();
		this.id = id;
		this.sysVersion = sysVersion;
		this.type = type;
		this.fileId = fileId;
		this.fileName = fileName;
		this.describe = describe;
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

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

}
