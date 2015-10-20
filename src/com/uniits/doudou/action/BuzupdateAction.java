/**
 * @description doudou com.uniits.doudou.action
 */
package com.uniits.doudou.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.uniits.doudou.service.BuzupdateService;

/**
 * @author wangguiyong
 * @since 2015-04-01 上午10:39:53
 * @description
 */
@IocBean
@At("/buzupdate")
public class BuzupdateAction {
	
	@Inject
	private BuzupdateService buzupdateService;

	@At("/findNew")
	@Ok("json")
	@Filters
	public Map<String, String> findNew(@Param("ID")String id, @Param("model")String model, @Param("sysVersion")String sysVersion, @Param("firmware")String firmware, 
			@Param("firmVersion")String firmVersion, @Param("bootanimation")String bootanimation, HttpServletRequest res){
		
		String url = res.getRequestURL().toString();
		String downUrl = url.substring(0, url.substring(0,url.lastIndexOf("/")).lastIndexOf("/")+1);
		downUrl = downUrl + "upgrade/doDownLoad.htm?fileId=";
		//id = "1234567891234567891234567891234567";
		//model = "F3333"; sysVersion = "v1110.0"; firmware = "V1.1"; firmVersion = "V1.4.5.0303"; bootanimation = "121231432413414312";
		return buzupdateService.findNewPage(id, model, sysVersion, firmware, firmVersion, bootanimation, downUrl);
	}

//	@At("/showPage")
//	@Ok("json")
//	public QueryResult findPage(@Param("pageNo") int pageNo,
//			@Param("pageSize") int pageSize, @Param("::queryMap.") NutMap map) {
//		QueryResult queryResult = vinService.findPage(pageNo, pageSize, map);
//		return queryResult;
//	}

}
