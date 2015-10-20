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
@Table("t_app_picture")
public class AppPicture {

    @Id
    @Column("id")
    //@Prev({@SQL(db = DB.ORACLE,value="select seq_app_picture.nextval from dual")})
    private Long id;
    
    @Column("file_id")
    private Long fileId;
    
    @Column("app_id")
    private Long appId;

    @Column("state")
    private Long state;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public Long getState() {
		return state;
	}

	public void setState(Long state) {
		this.state = state;
	}
    
}
