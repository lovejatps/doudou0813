package com.uniits.doudou.model;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * 
 * @author hxn
 *
 */
@Table("t_producecar")
public class Producecar {
	@Id
    @Column("id")
    //@Prev({@SQL(db = DB.ORACLE,value="select seq_file.nextval from dual")})
	private long id;
	
	@Column("name")
	private String name ;
	
	@Column("address")
	private String address ;
	
	private Long serialNumber;

	public Long getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(Long serialNumber) {
		this.serialNumber = serialNumber;
	}

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
