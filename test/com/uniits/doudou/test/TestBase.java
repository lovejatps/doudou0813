/**
 * @description doudou com.uniits.doudou.test
 */
package com.uniits.doudou.test;

import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.loader.combo.ComboIocLoader;

import junit.framework.TestCase;

/**
 * @author liuchengwei
 * @since 2015-3-16 下午3:59:24
 * @description
 */
public class TestBase extends TestCase {

    private static Ioc ioc;

    protected void setUp() throws Exception {
        super.setUp();
        ioc = new NutIoc(new ComboIocLoader(
                                                "*org.nutz.ioc.loader.json.JsonLoader", 
                                                "ioc/dao.js", 
                                                "*org.nutz.ioc.loader.annotation.AnnotationIocLoader", 
                                                "com.uniits.doudou"
                                        )); 
        System.out.println("-----------配置加载成功--------------");
    }

    /**
     * 根据bean类型得到bean
     * 
     * @param bean
     * @return
     */
    public Ioc getIoc() {
        return ioc;
    }
}
