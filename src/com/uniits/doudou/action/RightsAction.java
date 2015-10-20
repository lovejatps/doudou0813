package com.uniits.doudou.action;

import org.nutz.dao.QueryResult;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.uniits.doudou.model.Producecar;
import com.uniits.doudou.model.Sn;
import com.uniits.doudou.service.RightsService;
/**
 * 
 * @author hxn
 *
 */
@IocBean
@At("/rights")
public class RightsAction {
	@Inject
	private RightsService rigistService ; 
	
	@At("/listpage")
    @Ok("jsp:jsp/rights/list")
	public void listpage(){
		
	}
	
	@At("/showPage")
	@Ok("json")
	public QueryResult findPage(@Param("pageNo") int pageNo,
			@Param("pageSize") int pageSize, @Param("::queryMap.") NutMap map) {
		QueryResult queryResult = rigistService.findPage(pageNo, pageSize, map);
		return queryResult;
	}
	
	
	@At("/addpage")
    @Ok("jsp:jsp/rights/addproducecar")
	public void addpage(){
	}
	@At("/add")
    @Ok("jsp:jsp/rights/tip")
	public void addpage(@Param("::prod.") Producecar p){
		rigistService.addRightscar(p);
	}
	@At("/validatorUpdateprodName")
	@Ok("json")
	public int validatorUpdateprodName(@Param("prodName") String prodName){
		return rigistService.isProdName(prodName);
	}
	
	
	
	
 	@At("/deleteProduce")
	@Ok("json")
    public String deleteProduce(@Param("::prod.") Producecar p){
 		rigistService.deleteProduce(p.getId());
    	return "1";
    }
 	
 	@At("/selectProduce")
	@Ok("json")
    public int selectProduce(@Param("::prod.") Producecar p){
 		return  rigistService.isSelectRights(p.getId());
    	
    }
 	
}
