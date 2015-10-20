/**
 * 
 */
package com.uniits.doudou.action;

import org.nutz.dao.QueryResult;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.uniits.doudou.model.Phone;
import com.uniits.doudou.model.Register;
import com.uniits.doudou.service.RegistService;

/**
 * @author 成卫
 * 
 */
@IocBean
@At("/buzregist")
public class RegistAction {
	@Inject
	private RegistService registService;

	@At("/list")
	@Ok("jsp:jsp/car/register/list")
	public void list() {

	}
	
	@At("/toOneCall")
	@Ok("jsp:jsp/car/register/oneCall")
	public Phone toOneCall(){
		//一键呼叫电话号码
		return registService.findOneCall();
	}
	
	@At("/findNewPhone")
	@Ok("json")
	@Filters
	public NutMap findNewPhone(){
		//一键呼叫电话号码
		NutMap map = new NutMap();
		map.put("errorMsg", "");
		map.put("phone", "");
		try {
			Phone p = registService.findOneCall();
			if(p != null && p.getCallPhone() != null && p.getCallPhone().length() > 0) {
				map.put("phone", p.getCallPhone());
			} else {
				map.put("errorMsg", "电话未设置");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			map.put("errorMsg", "服务异常");
			e.printStackTrace();
		}
		
		return map;
	}
	
	@At("/findCustPhone")
	@Ok("json")
	@Filters
	public NutMap findCustPhone(){
		//一键呼叫电话号码
		NutMap map = new NutMap();
		map.put("errorMsg", "");
		map.put("phone", "");
		try {
			Phone p = registService.findOneCall();
			if(p != null && p.getCustPhone() != null && p.getCustPhone().length() > 0) {
				map.put("phone", p.getCustPhone());
			} else {
				map.put("errorMsg", "电话未设置");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			map.put("errorMsg", "服务异常");
			e.printStackTrace();
		}
		
		return map;
	}
	
	@At("/doUpdateCallPhone")
    //@Ok("jsp:jsp/sbm/sn/list")
    @Ok("jsp:jsp/car/register/tip")
	public String doUpdateCallPhone(@Param("::phone.") Phone phone){
    	registService.updateOneCall(phone);
    	return "1";
    }
	
	@At("/doUpdateCustPhone")
    @Ok("jsp:jsp/car/register/tip")
	public String doUpdateCustPhone(@Param("::phone.") Phone phone){
    	registService.updateOneCall(phone);
    	return "1";
    }

	@At("/showPage")
	@Ok("json")
	public QueryResult findPage(@Param("pageNo") int pageNo,
			@Param("pageSize") int pageSize, @Param("::queryMap.") NutMap map) {
		QueryResult queryResult = registService.findPage(pageNo, pageSize, map);
		return queryResult;
	}
	
	@At("/deleteRegist")
	@Ok("json")
	public String deleteRegist(@Param("::reg.") Register reg){
		registService.deleteRegister(reg.getId());
		return "1";
	}

}
