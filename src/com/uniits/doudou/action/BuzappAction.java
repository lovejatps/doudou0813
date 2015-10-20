/**
 * @description doudou com.uniits.doudou.action
 */
package com.uniits.doudou.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.uniits.doudou.service.BuzupdateService;
import com.uniits.doudou.vo.AppInfoVo;
import com.uniits.doudou.vo.AppInterfaceVo;

/**
 * @author wangguiyong
 * @since 2015-04-01 上午10:39:53
 * @description
 */
@IocBean
@At("/buzapp")
public class BuzappAction {
	
	@Inject
	private BuzupdateService buzupdateService;

	@At("/appNew")
	@Ok("json")
	@Filters
	public Map<String, List<AppInterfaceVo>> appNew(@Param("id")String id, @Param("sysTypeId")String sysTypeId, HttpServletRequest res){
		
		String url = res.getRequestURL().toString();
		String downUrl = url.substring(0, url.substring(0,url.lastIndexOf("/")).lastIndexOf("/")+1);
		downUrl = downUrl + "upgrade/doDownLoad.htm?fileId=";
		//id = "1234567891234567891234567891234567";
		//model = "F3333"; sysVersion = "v1110.0"; firmware = "V1.1"; firmVersion = "V1.4.5.0303"; bootanimation = "121231432413414312";
		return buzupdateService.findNewApp(id, sysTypeId, downUrl);
	}
	
	@At("/appList")
	@Ok("json")
	@Filters
	public Map<String, List<AppInfoVo>> appList(@Param("id")String id, @Param("sysTypeId")String sysTypeId, HttpServletRequest res){
		
		String url = res.getRequestURL().toString();
		String downUrl = url.substring(0, url.substring(0,url.lastIndexOf("/")).lastIndexOf("/")+1);
		downUrl = downUrl + "upgrade/doDownLoad.htm?fileId=";
		//id = "1234567891234567891234567891234567";
		//model = "F3333"; sysVersion = "v1110.0"; firmware = "V1.1"; firmVersion = "V1.4.5.0303"; bootanimation = "121231432413414312";
		return buzupdateService.appList(id, sysTypeId, downUrl);
	}
	
	@At("/appSearchList")
	@Ok("json")
	@Filters
	public Map<String, List<AppInfoVo>> appSearchList(@Param("id")String id, @Param("sysTypeId")String sysTypeId, @Param("appName")String appName, HttpServletRequest res){
		
		String url = res.getRequestURL().toString();
		String downUrl = url.substring(0, url.substring(0,url.lastIndexOf("/")).lastIndexOf("/")+1);
		downUrl = downUrl + "upgrade/doDownLoad.htm?fileId=";
		//id = "1234567891234567891234567891234567";
		//model = "F3333"; sysVersion = "v1110.0"; firmware = "V1.1"; firmVersion = "V1.4.5.0303"; bootanimation = "121231432413414312";
		return buzupdateService.appSearchList(id, sysTypeId, appName, downUrl);
	}
	
	@At("/appDetail")
	@Ok("json")
	@Filters
	public Map<String, Object> appDetail(@Param("appId")Long appId, HttpServletRequest res){
		String url = res.getRequestURL().toString();
		String downUrl = url.substring(0, url.substring(0,url.lastIndexOf("/")).lastIndexOf("/")+1);
		downUrl = downUrl + "upgrade/doDownLoad.htm?fileId=";
		
		return buzupdateService.appDetail(appId, downUrl);
	}

}
