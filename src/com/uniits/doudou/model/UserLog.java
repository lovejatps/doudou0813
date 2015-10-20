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
 * @since 2015-4-07 上午11:51:15
 * @description
 */
@Table("t_user_log")
public class UserLog {
	
	@Column("id")
	@Id
    //@Prev({@SQL(db = DB.ORACLE,value="select seq_user.nextval from dual")})
	private long id;
	
	@Column("user_id")
	private Long userId;

	@Column("user_name")
	private String userName;

	@Column("role_id")
	private long roleId;
	
	@Column("create_date")
	private String createDate;
	
	@Column("login_date")
	private String loginDate;
	


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public String getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(String loginDate) {
		this.loginDate = loginDate;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

}
