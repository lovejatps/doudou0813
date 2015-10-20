/**
 * @description doudou com.uniits.doudou.service
 */
package com.uniits.doudou.service;

import java.util.List;
import java.util.Map;

import com.uniits.doudou.vo.AppInfoVo;
import com.uniits.doudou.vo.AppInterfaceVo;

/**
 * @author wangguiyong
 * @since 2015-04-01 下午2:47:37
 * @description 
 */
public interface BuzupdateService {

	/**
	 * 系统升级包 接口
	 * @param id
	 * @param model
	 * @param sysVersion
	 * @param firmware
	 * @param firmVersion
	 * @param bootanimation
	 * @return
	 */
    public Map<String, String> findNewPage(String id, String model, String sysVersion, 
    				String firmware, String firmVersion, String bootanimation, String downUrl);
    
    /**
	 * app包 接口
	 * @param id
	 * @param model
	 * @param sysVersion
	 * @param firmware
	 * @param firmVersion
	 * @param bootanimation
	 * @return
	 */
    public Map<String, List<AppInterfaceVo>> findNewApp(String id, String sysTypeId, String downUrl);
    
    public Map<String, List<AppInfoVo>> appList(String id, String sysTypeId, String downUrl);
    
    public Map<String, List<AppInfoVo>> appSearchList(String id, String sysTypeId, String appName, String downUrl);
    
    public Map<String, Object> appDetail(Long appId, String downUrl);
	
}
