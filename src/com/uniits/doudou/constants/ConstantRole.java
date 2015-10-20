/**
 * @description doudou com.uniits.doudou.constants
 */
package com.uniits.doudou.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liuchengwei
 * @since 2015-3-17 下午1:52:21
 * @description 
 */
public class ConstantRole {
    public static long ADMIN = -1L;
    
    public static long NORMAL = -2L;
    
    public static long CAR_USER = -3L;
    
    public static Map<Long,String> roleMap; 
    
    static {
    	roleMap = new HashMap<Long, String>();
    	roleMap.put(ADMIN, "管理员");
    	roleMap.put(NORMAL, "厂家");
    	//roleMap.put(CAR_USER, "普通用户");
    }
}
