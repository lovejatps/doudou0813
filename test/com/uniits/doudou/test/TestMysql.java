/**
 * @description doudou com.uniits.doudou.test
 */
package com.uniits.doudou.test;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.nutz.dao.Dao;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.loader.json.JsonLoader;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * @author liuchengwei
 * @since 2015-3-16 下午9:14:47
 * @description
 */
public class TestMysql {

    Ioc ioc;
    Dao dao;

    @Test
    public void testaa() {
        System.out.println("2");
        DataSource ds = ioc.get(DataSource.class);
        System.out.println(ds.toString());
    }

    @Before
    public void before() {
        ioc = new NutIoc(new JsonLoader("ioc/dao.js"));
        dao = ioc.get(Dao.class);
    }

    @After
    public void after() {
        if (ioc != null)
            ioc.depose();
    }
}
