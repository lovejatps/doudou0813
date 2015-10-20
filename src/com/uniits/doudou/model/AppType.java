/**
 * @description doudou com.uniits.doudou.model
 */
package com.uniits.doudou.model;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * @author liuchengwei
 * @since 2015-3-14 下午3:30:10
 * @description
 */
@Table("t_app_type")
public class AppType {
    @Getter @Setter
    @Id
    @Column("id")
    private long id;
    
    @Getter @Setter
    @Column("name")
    private String name;
    
    @Getter @Setter
    @Column("description")
    private String description;
}
