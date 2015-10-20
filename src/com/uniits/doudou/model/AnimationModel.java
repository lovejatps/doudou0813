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
@Table("t_animation_model")
public class AnimationModel {

    @Id
    @Column("id")
    //@Prev({@SQL(db = DB.ORACLE,value="select seq_animation_model.nextval from dual")})
    private Long id;
    
    @Column("sys_model_id")
    private Long sysModelId;
    
    @Column("anima_id")
    private Long animaId;

    @Column("state")
    private Long state;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSysModelId() {
		return sysModelId;
	}

	public void setSysModelId(Long sysModelId) {
		this.sysModelId = sysModelId;
	}

	public Long getAnimaId() {
		return animaId;
	}

	public void setAnimaId(Long animaId) {
		this.animaId = animaId;
	}

	public Long getState() {
		return state;
	}

	public void setState(Long state) {
		this.state = state;
	}
    
}
