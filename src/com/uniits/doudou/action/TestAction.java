/**
 * @description doudou com.uniits.doudou.action.base
 */
package com.uniits.doudou.action;

import java.util.HashMap;
import java.util.Map;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

/**
 * @author liuchengwei
 * @since 2015-3-14 下午5:37:11
 * @description 
 */
@IocBean
@At("/test")
public class TestAction {

    @At("/test")
    @Ok("json")
    public Map<String,String> test() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("name", "steven");
        map.put("age", "27");
        map.put("gender", "男");
        return map;
    }
}
