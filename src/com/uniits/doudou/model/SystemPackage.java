/**
 * @description doudou com.uniits.doudou.model
 */
package com.uniits.doudou.model;


import org.nutz.dao.DB;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Prev;
import org.nutz.dao.entity.annotation.SQL;
import org.nutz.dao.entity.annotation.Table;

/**
 * @author wangguiyong
 * @since 2015-3-28 下午1:29:23
 * @description
 */
@Table("t_system_package")
public class SystemPackage {

    @Id
    @Column("id")
    //@Prev({@SQL(db = DB.ORACLE,value="select seq_system_package.nextval from dual")})
    private Long id;
    
    @Column("sys_version")
    private String sysVersion;
    
    @Column("type")
    private Long type;
    
    @Column("file_id")
    private Long fileId;
    
    @Column("file_name")
    private String fileName;
    
    @Column("create_date")
    private String createDate;
    
    @Column("describes")
    private String describes;
    
    @Column("deleted")
    private Long deleted;
    
  //  @Column("produce_id")
    private Long produceid ;
    

	public Long getProduceid() {
		return produceid;
	}

	public void setProduceid(Long produceid) {
		this.produceid = produceid;
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

	public String getDescribes() {
		return describes;
	}

	public void setDescribes(String describes) {
		this.describes = describes;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public Long getDeleted() {
		return deleted;
	}

	public void setDeleted(Long deleted) {
		this.deleted = deleted;
	}
    
}
