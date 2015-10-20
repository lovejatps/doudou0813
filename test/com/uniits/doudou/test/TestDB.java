/**
 * @description doudou com.uniits.doudou.test
 */
package com.uniits.doudou.test;


import javax.sql.DataSource;

import org.nutz.lang.random.R;

import com.uniits.doudou.model.Register;
import com.uniits.doudou.service.RegistService;
import com.uniits.doudou.service.impl.RegistServiceImpl;

/**
 * @author liuchengwei
 * @since 2015-3-16 下午4:24:17
 * @description
 */
public class TestDB extends TestBase {
    
//    public void testjdbc() {
//        System.out.println(JdbcUtil.getInstance().testConnection(JdbcUtil.getDriver(), JdbcUtil.getUrl(), "root", "123456"));
//    }
    
//    public void testaaa() {
//        
//        DataSource dataSource = getIoc().get(DataSource.class,"dataSource");
//        assertNull(dataSource);
//        
//    }

    public void testregister() {
        
        RegistService registService = getIoc().get(RegistService.class);
        
        String username = R.UU16();
        String pwd = R.UU16();
        
        Register r = new Register();
        r.setVIN(username);
        r.setCarMachineId(pwd);
        r.setUsername(username);
        r.setPwd(pwd);
        r.setRoleId(-1);
        registService.addRegist(r);
        
    }
    
    
}
