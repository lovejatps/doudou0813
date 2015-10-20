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
@Table("t_animation")
public class Animation {

    @Id
    @Column("id")
    //@Prev({@SQL(db = DB.ORACLE,value="select seq_animation.nextval from dual")})
    private Long id;
    
    @Column("name")
    private String name;
    
    @Column("file_id")
    private Long fileId;
    
    @Column("file_name")
    private String fileName;
    
    @Column("describes")
    private String describes;
    
    @Column("create_date")
    private String createDate;
    
    @Column("deleted")
    private Long deleted;
    
    private Long sys_model_id ;
    

	public Long getSys_model_id() {
		return sys_model_id;
	}

	public void setSys_model_id(Long sys_model_id) {
		this.sys_model_id = sys_model_id;
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
