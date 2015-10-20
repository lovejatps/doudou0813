/**
 * @description doudou com.uniits.doudou.model
 */
package com.uniits.doudou.model;

import java.util.List;

import org.nutz.dao.DB;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Prev;
import org.nutz.dao.entity.annotation.SQL;
import org.nutz.dao.entity.annotation.Table;

/**
 * @author liuchengwei
 * @since 2015-3-17 下午6:09:15
 * @description
 */
@Table("t_user")
public class User {
	
	@Column("id")
	@Id
    //@Prev({@SQL(db = DB.ORACLE,value="select seq_user.nextval from dual")})
	private long id;

	@Column("login_name")
	private String loginName;

	@Column("pwd")
	private String pwd;

	@Column("role_id")
	private long roleId;

	@Column("last_login_date")
	private String lastLoginDate;
	
	@Column("create_date")
	private String createDate;
	
	@Column("produce_id")
	private long produceid ;
	
	private String produceName ;

	private List<Producecar> prods ;


	public List<Producecar> getProds() {
		return prods;
	}

	public void setProds(List<Producecar> prods) {
		this.prods = prods;
	}

	public String getProduceName() {
		return produceName;
	}

	public void setProduceName(String produceName) {
		this.produceName = produceName;
	}

	public long getProduceid() {
		return produceid;
	}

	public void setProduceid(long produceid) {
		this.produceid = produceid;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	@Column("status")
	private long status;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public String getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public long getStatus() {
		return status;
	}

	public void setStatus(long status) {
		this.status = status;
	}

}
