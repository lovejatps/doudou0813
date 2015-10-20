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
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.upload.TempFile;
import org.nutz.mvc.upload.UploadAdaptor;

import com.uniits.doudou.model.CarMachine;
import com.uniits.doudou.model.Vin;
import com.uniits.doudou.service.CarMachineService;
import com.uniits.doudou.utils.CommonUtils;
import com.uniits.doudou.vo.MassageCount;

/**
 * @author liuchengwei
 * @since 2015-3-19 上午10:40:16
 * @description 
 */
@IocBean
@At("/buzid")
public class IdAction {
	@Inject
	private CarMachineService carMachineService;

	@At("/add")
	@Ok("jsp:jsp/sbm/id/add")
	public void add() {

	}

	@At("/doInputId")
	// @Ok("jsp:jsp/sbm/sn/list")
	@Ok("jsp:jsp/sbm/id/tip")
	public MassageCount doInputId(@Param("::car.") CarMachine v) {

		v.setCreateDate(Times.sD(new Date()));
		carMachineService.addCarMachine(v);
		
		MassageCount count = new MassageCount();
		count.setDoInputFile("doInput");
		count.setSuccessCount(1L);
		return count;
	}
	
	@At("/deleteMachine")
    @Ok("json")
	public String deleteMachine(@Param("::car.") CarMachine v){
		carMachineService.deleteCarMachine(v.getId());
		return "1";
	}

	@At("/doInputIdFile")
	@Ok("jsp:jsp/sbm/id/tip")
	@AdaptBy(type = UploadAdaptor.class, args = { "${app.root}/WEB-INF/tmp" })
	public MassageCount doInputIdFile(@Param("idFile") TempFile tmpFile) {
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
				List<CarMachine> list = new ArrayList<CarMachine>();
				for (String k : snList) {
					if (k == null || k.trim().length() == 0){
						continue;
					}
					if (CommonUtils.isContainChinese(k) || k.length() != 34) {
						f++;
						continue;
					}
					CarMachine cc = carMachineService.fetchCarMachine(k);
					if (Lang.isEmpty(cc)) {
						CarMachine v = new CarMachine();
						v.setValue(k.trim());
						v.setCreateDate(createDate);
						list.add(v);
					} else {
						f++;
					}
				}
				if (!Lang.isEmpty(list) && list.size() > 0) {
					List<CarMachine> li = carMachineService.addCarMachineBatch(list);
					count.setSuccessCount((long)li.size());
				}
			}
		}
		count.setErrorCount(f);
		return count;
	}

	@At("/list")
	@Ok("jsp:jsp/sbm/id/list")
	public void list() {

	}

	@At("/showPage")
	@Ok("json")
	public QueryResult findPage(@Param("pageNo") int pageNo,
			@Param("pageSize") int pageSize, @Param("::queryMap.") NutMap map) {
		QueryResult queryResult = carMachineService.findPage(pageNo, pageSize, map);
		return queryResult;
	}
	
	@At("/validatorID")
    @Ok("json")
	public int validatorID(@Param("car.value") String carID){
	    CarMachine car = carMachineService.fetchCarMachine(carID);
	    if(!Lang.isEmpty(car)) {//已经存在
	        return 1;
	    }
	    return 0;
	}

}
