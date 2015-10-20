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
@Table("t_firm_model")
public class FirmModel {

    @Id
    @Column("id")
    //@Prev({@SQL(db = DB.ORACLE,value="select seq_firm_model.nextval from dual")})
    private Long id;
    
    @Column("name")
    private String name;
    
    @Column("create_date")
    private String createDate;
    
	@Column("produce_id")
    private Long produceid;
	
	private String producename ;
    
	
    public String getProducename() {
		return producename;
	}

	public void setProducename(String producename) {
		this.producename = producename;
	}

	private Long serialNumber;


    
    
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
