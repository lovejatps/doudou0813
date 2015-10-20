/**
 * @description doudou com.uniits.doudou.action
 */
package com.uniits.doudou.action;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.upload.TempFile;
import org.nutz.mvc.upload.UploadAdaptor;

import com.uniits.doudou.model.Vin;
import com.uniits.doudou.service.VinService;
import com.uniits.doudou.utils.CommonUtils;
import com.uniits.doudou.utils.VinUtil;
import com.uniits.doudou.vo.MassageCount;

/**
 * @author liuchengwei
 * @since 2015-3-19 上午10:39:53
 * @description
 */
@IocBean
@At("/buzvin")
public class VinAction {
	
	@Inject
	private VinService vinService;

	@At("/add")
	@Ok("jsp:jsp/sbm/vin/add")
	public void add() {

	}

	@At("/doInputVin")
	// @Ok("jsp:jsp/sbm/sn/list")
	@Ok("jsp:jsp/sbm/vin/tip")
	public MassageCount doInputId(@Param("::vin.") Vin v) {

		v.setCreateDate(Times.sD(new Date()));
		vinService.addVin(v);
		
		MassageCount count = new MassageCount();
		count.setDoInputFile("doInput");
		count.setSuccessCount(1L);
		return count;
	}

	@At("/doInputVinFile")
	@Ok("jsp:jsp/sbm/vin/tip")
	@AdaptBy(type = UploadAdaptor.class, args = { "${app.root}/WEB-INF/tmp" })
	public MassageCount doInputIdFile(@Param("vinFile") TempFile tmpFile) {
		MassageCount count = new MassageCount();

		String suffix = Files.getSuffixName(tmpFile.getFile()).toLowerCase();
		Long f = 0L;
		if ("txt".equalsIgnoreCase(suffix)) {
			
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
            
			if (!Lang.isEmpty(snList)) {
				String createDate = Times.sD(new Date());
				List<Vin> list = new ArrayList<Vin>();
				//String[] sns = null;
				for (String k : snList) {
					if (k == null || k.trim().length() == 0) {
						continue;
					}
					if (CommonUtils.isContainChinese(k) || k.length() != 17 || !VinUtil.isLegal(k)) {
						f++;
						continue;
					}
					Vin vv = vinService.fetchVin(k);
					if (Lang.isEmpty(vv)) {
						Vin v = new Vin();
						v.setValue(k.trim());
						v.setCreateDate(createDate);
						list.add(v);
					} else {
						f++;
					}
				}
				if (!Lang.isEmpty(list) && list.size() > 0) {
					List<Vin> li = vinService.addVinBatch(list);
					count.setSuccessCount((long)li.size());
				}
			}
			
		}
		count.setErrorCount(f.longValue());
		return count;
	}

	@At("/list")
	@Ok("jsp:jsp/sbm/vin/list")
	public void list() {

	}

	@At("/showPage")
	@Ok("json")
	public QueryResult findPage(@Param("pageNo") int pageNo,
			@Param("pageSize") int pageSize, @Param("::queryMap.") NutMap map) {
		QueryResult queryResult = vinService.findPage(pageNo, pageSize, map);
		return queryResult;
	}
	
	@At("/deleteVin")
	@Ok("json")
	public String deleteVin(@Param("::vin.") Vin v){
		vinService.deleteVin(v.getId());
		return "1";
	}
	
	@At("/validatorVIN")
    @Ok("json")
	public int validatorVIN(@Param("vin.value") String v) {
	    Vin vin = vinService.fetchVin(v);
	    if(!Lang.isEmpty(vin)) {
	        return 1;
	    }
        return 0;
    }
	
	@At("/validatorVin_17")
    @Ok("json")
	public int validatorVin_17(@Param("vinName") String vinName) {
	    if(VinUtil.isLegal(vinName)) {
	        return 1;
	    }
        return 0;
    }

}
