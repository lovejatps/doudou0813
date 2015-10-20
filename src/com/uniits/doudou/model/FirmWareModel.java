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
@Table("t_firm_ware_model")
public class FirmWareModel {

    @Id
    @Column("id")
    //@Prev({@SQL(db = DB.ORACLE,value="select seq_firm_ware_model.nextval from dual")})
    private Long id;
    
    @Column("firm_model_id")
    private Long firmModelId;
    
    @Column("firmware_id")
    private Long firmwareId;

    @Column("state")
    private Long state;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFirmModelId() {
		return firmModelId;
	}

	public void setFirmModelId(Long firmModelId) {
		this.firmModelId = firmModelId;
	}

	public Long getFirmwareId() {
		return firmwareId;
	}

	public void setFirmwareId(Long firmwareId) {
		this.firmwareId = firmwareId;
	}

	public Long getState() {
		return state;
	}

	public void setState(Long state) {
		this.state = state;
	}
    
}
