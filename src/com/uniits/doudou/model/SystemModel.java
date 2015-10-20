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
 * @author wangguiyong
 * @since 2015-3-28 下午1:29:23
 * @description
 */
@Table("t_system_model")
public class SystemModel {

    @Id
    @Column("id")
    //@Prev({@SQL(db = DB.ORACLE,value="select seq_system_model.nextval from dual")})
    private Long id;
    
    @Column("name")
    private String name;
    
    @Column("create_date")
    private String createDate;
    
    private Long serialNumber;
    
    @Column("produce_id")
    private long produceid ;
    
    private String produeName ;
    
    private List<Producecar> prods;
    



	public List<Producecar> getProds() {
		return prods;
	}

	public void setProds(List<Producecar> prods) {
		this.prods = prods;
	}

	public String getProdueName() {
		return produeName;
	}

	public void setProdueName(String produeName) {
		this.produeName = produeName;
	}

	public long getProduceid() {
		return produceid;
	}

	public void setProduceid(long produceid) {
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
