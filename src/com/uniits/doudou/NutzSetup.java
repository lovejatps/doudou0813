package com.uniits.doudou;

import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;

/**
 * 
 * Nutz启动模块
 * 
 * @author steven
 * 
 */
public class NutzSetup implements Setup {

    public void init(NutConfig config) {
        System.out.println(">>>>>> NutzSetup .... begin...");

    }

    public void destroy(NutConfig config) {
        System.out.println(">>>>>> NutzSetup .... end...");
    }

}
