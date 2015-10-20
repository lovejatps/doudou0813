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
 * @since 2015-3-14 下午3:28:12
 * @description 
 */
@Table("t_car_vin")
public class Vin {
    @Column("id")
    @Id
    //@Prev({@SQL(db = DB.ORACLE,value="select seq_car_vin.nextval from dual")})
    private long id;
    
    @Column("vin")
    private String value;
    
	@Column("create_date")
    private String createDate;
	
	private Long serialNumber;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public Long getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Long serialNumber) {
		this.serialNumber = serialNumber;
	}
    
}
