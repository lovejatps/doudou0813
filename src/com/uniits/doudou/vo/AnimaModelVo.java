/**
 * 
 */
package com.uniits.doudou.vo;

import org.nutz.dao.entity.annotation.Column;

/**
 * @author  wangguiyong
 * 接口
 * 
 */
public class AnimaModelVo {

	@Column("id")
	private Long id;
	
	@Column("name")
	private String name;

	@Column("file_id")
	private Long fileId;

	@Column("file_name")
	private String fileName;
	
	public AnimaModelVo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AnimaModelVo(Long id, String name, Long fileId, String fileName) {
		super();
		this.id = id;
		this.name = name;
		this.fileId = fileId;
		this.fileName = fileName;
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

}
