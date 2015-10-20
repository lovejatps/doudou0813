/**
 * @description doudou com.uniits.doudou.model
 */
package com.uniits.doudou.model;

import org.nutz.dao.entity.annotation.Table;

/**
 * @author liuchengwei
 * @since 2015-3-14 下午3:28:24
 * @description
 */
@Table("t_role")
public class Role {
    
    private long id;
    
    private String name;
    
    private String description;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
