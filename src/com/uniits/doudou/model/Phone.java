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
 * @since 2015-3-26 上午10:26:23
 * @description
 */
@Table("t_phone")
public class Phone {

	@Id
	@Column("id")
	//@Prev({@SQL(db = DB.ORACLE,value="select seq_phone.nextval from dual")})
	private long id;
	
	@Column("call_phone")
	private String callPhone;
	
	@Column("cust_phone")
	private String custPhone;

	@Column("create_date")
	private String createDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCallPhone() {
		return callPhone;
	}

	public void setCallPhone(String callPhone) {
		this.callPhone = callPhone;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getCustPhone() {
		return custPhone;
	}

	public void setCustPhone(String custPhone) {
		this.custPhone = custPhone;
	}

}
