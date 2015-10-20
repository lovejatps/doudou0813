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
 * @author liuchengwei
 * @since 2015-3-14 下午3:27:56
 * @description
 */
@Table("t_file")
public class SystemFile {

	@Id
    @Column("id")
    //@Prev({@SQL(db = DB.ORACLE,value="select seq_file.nextval from dual")})
	private long id;
	
	@Column("name")
	private String name;

	@Column("url")
	private String url;
	
	@Column("file_size")
	private String fileSize;
	
	@Column("down_count")
	private Long downCount;
	
	@Column("create_date")
	private String createDate;
	
	@Column("deleted")
	private Long deleted;
	
	@Column("md5")
	private String md5;
	
/*	@Column("produce_id")
	private Long produceid ;
	
	
	
	public Long getProduceid() {
		return produceid;
	}

	public void setProduceid(Long produceid) {
		this.produceid = produceid;
	}
*/
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getDeleted() {
		return deleted;
	}

	public void setDeleted(Long deleted) {
		this.deleted = deleted;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public Long getDownCount() {
		return downCount;
	}

	public void setDownCount(Long downCount) {
		this.downCount = downCount;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}
	
}
