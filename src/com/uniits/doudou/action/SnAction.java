/**
 * @description doudou com.uniits.doudou.action
 */
package com.uniits.doudou.action;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.nutz.dao.QueryResult;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Files;
import org.nutz.lang.Lang;
import org.nutz.lang.Times;
import org.nutz.lang.util.NutMap;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.upload.TempFile;
import org.nutz.mvc.upload.UploadAdaptor;

import com.uniits.doudou.model.Sn;
import com.uniits.doudou.service.SnService;
import com.uniits.doudou.utils.CommonUtils;
import com.uniits.doudou.vo.MassageCount;

/**
 * @author liuchengwei
 * @since 2015-3-19 上午10:39:28
 * @description 
 */
@IocBean
@At("/buzsn")
public class SnAction {
    
    @Inject
    private SnService snService;
    
    private static final Log log = Logs.get();
    
    @At("/add")
    @Ok("jsp:jsp/sbm/sn/add")
    public void add() {
        
        
    }
    
    @At("/deleteSn")
	@Ok("json")
    public String deleteSn(@Param("::sn.") Sn sn){
    	snService.deleteSn(sn.getId());
    	return "1";
    }
    
    @At("/doInputSn")
    //@Ok("jsp:jsp/sbm/sn/list")
    @Ok("jsp:jsp/sbm/sn/tip")
    public MassageCount doInputId(@Param("::sn.") Sn sn) {
        
        sn.setCreateDate(Times.sD(new Date()));
        snService.addSn(sn);
        
        MassageCount count = new MassageCount();
		count.setDoInputFile("doInput");
		count.setSuccessCount(1L);
		return count;
    }
    
    @At("/doInputSnFile")
//    @Ok("json")
    @Ok("jsp:jsp/sbm/sn/tip")
    @AdaptBy(type = UploadAdaptor.class, args = { "${app.root}/WEB-INF/tmp" })
    public MassageCount doInputIdFile(@Param("snFile")TempFile tmpFile ){
    	MassageCount count = new MassageCount();
        
        String suffix = Files.getSuffixName(tmpFile.getFile()).toLowerCase();
        
        Long f = 0L;
        if("txt".equalsIgnoreCase(suffix)) {
        	
        	List<String> snList = new ArrayList<String>();
			
			try {
				InputStreamReader read = new InputStreamReader(new FileInputStream(tmpFile.getFile()),"gbk");
				BufferedReader reader=new BufferedReader(read);
				String line;
				while ((line = reader.readLine()) != null){
					snList.add(line);
				}
				read.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
        	
            if(!Lang.isEmpty(snList)) {
                String createDate = Times.sD(new Date());
                List<Sn> list = new ArrayList<Sn>();
                for (String k : snList) {
					if (k == null || k.trim().length() == 0) {
						continue;
					}
					if (CommonUtils.isContainChinese(k) || k.length() != 9) {
						f++;
						continue;
					}
					Sn ss = snService.fecthSn(k);
					if (Lang.isEmpty(ss)) {
						Sn sn = new Sn();
						sn.setValue(k.trim());
						sn.setCreateDate(createDate);
						list.add(sn);
					} else {
						f++;
					}
                }
                if(!Lang.isEmpty(list) && list.size() > 0){
                    List<Sn> li = snService.addSnBatch(list);
                    count.setSuccessCount((long)li.size());
                }
            }
        }
        count.setErrorCount(f.longValue());
        return count;
    }
    
    @At("/list")
    @Ok("jsp:jsp/sbm/sn/list")
    public void list() {
    	
    }
    
	@At("/showPage")
	@Ok("json")
	public QueryResult findPage(@Param("pageNo") int pageNo,
			@Param("pageSize") int pageSize, @Param("::queryMap.") NutMap map) {
		QueryResult queryResult = snService.findPage(pageNo, pageSize, map);
		return queryResult;
	}
	
	@At("/validatorSN")
    @Ok("json")
	public int validatorSN(@Param("sn.value") String sn) {
	    Sn snn = snService.fecthSn(sn);
	    if(!Lang.isEmpty(snn)) {
	        return 1;
	    }
	    return 0;
	}
}
