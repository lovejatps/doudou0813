/**
 * @description doudou com.uniits.doudou.service.impl
 */
package com.uniits.doudou.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.lang.Times;
import org.nutz.service.IdEntityService;

import com.uniits.doudou.model.App;
import com.uniits.doudou.model.AppPicture;
import com.uniits.doudou.model.AppSystem;
import com.uniits.doudou.model.CarMachine;
import com.uniits.doudou.model.SystemFile;
import com.uniits.doudou.model.SystemModel;
import com.uniits.doudou.service.BuzupdateService;
import com.uniits.doudou.utils.Constants;
import com.uniits.doudou.vo.AnimaModelVo;
import com.uniits.doudou.vo.AppInfoVo;
import com.uniits.doudou.vo.AppInterfaceVo;
import com.uniits.doudou.vo.FirmModelVo;
import com.uniits.doudou.vo.SystemModelVo;

/**
 * @author wangguiyong
 * @since 2015-3-17 下午3:42:30
 * @description 
 */
@IocBean(name="buzupdateService" ,args = {"refer:dao"})
public class BuzupdateServiceImpl extends IdEntityService implements BuzupdateService {
    
    public BuzupdateServiceImpl() {
        super();
    }

    @SuppressWarnings("unchecked")
    public BuzupdateServiceImpl(Dao dao, Class<Object> entityType) {
        super(dao, entityType);
    }

    public BuzupdateServiceImpl(Dao dao) {
        super(dao);
    }
    
    public Map<String, Object> appDetail(Long appId, String downUrl){
    	List<String> piclist = new ArrayList<String>();
    	
    	App app = dao().fetch(App.class, Cnd.where("id", "=", appId).and("deleted","=","0"));
    	
    	List<AppPicture> appPicList = dao().query(AppPicture.class, Cnd.where("appId", "=", appId).and("state", "=", "1"));
    	for(AppPicture appPic : appPicList){
    		piclist.add(downUrl + appPic.getFileId());
    	}
    	Map<String, Object> map  = new HashMap<String, Object>();
    	map.put("description", this.getDescribeAndDetail(app.getDescribes(), app.getCreateDate().substring(0, 10), app.getAppVersion(), app.getDetail()));
    	map.put("images", piclist);
    	return map;
    }
    
    public String getDescribeAndDetail(String describe, String createDate, String version, String detail){
    	String str = describe + "\n" + "【基本信息】\n" + "更新时间："+ createDate +"\n版本：" + version + "\n【更新内容】\n" + detail;
    	
    	return str;
    }
    
    public Map<String, List<AppInfoVo>> appSearchList(String id, String sysTypeId, String appName, String downUrl){
    	if(id.length()!=34){
    		return null;
    	}
    	Map<String, List<AppInfoVo>> map = new HashMap<String, List<AppInfoVo>>();
    	CarMachine carMach = dao().fetch(CarMachine.class, Cnd.where("value","=",id));
    	if(carMach == null){
    		CarMachine in = new CarMachine();
    		in.setValue(id);
    		in.setCreateDate(Times.sD(new Date()));
    		dao().insert(in);
    	}
    	if(sysTypeId==null || appName==null){
    		return null;
    	}
    	
//    	if(carMach==null || sysTypeId==null || appName==null){
//    		return null;
//    	}
    	
    	List<AppInfoVo> list = new ArrayList<AppInfoVo>();
    	
    	SystemModel mod = dao().fetch(SystemModel.class, Cnd.where("name", "=", sysTypeId));
    	
    	if(mod==null){
    		return null;
    	}
    	
    	
    	if(appName.contains("%") || appName.contains("_")){
    		appName = appName.replace("%", "\\%");
    		appName = appName.replace("_", "\\_");
    	}
    	
    	String sqlString = "select * from t_app a where a.id in (select sa.app_id from t_app_system sa where sa.sys_model_id in "
				+ " (select s.id from t_system_model s where s.name = '$name') and sa.state = 1) "
				+ " and a.deleted = 0 and a.app_name like '%$appName%' order by a.recommend_index desc, a.create_date desc";

		Sql sql = Sqls.create(sqlString);
		sql.vars().set("name", mod.getName());
		sql.vars().set("appName", appName);
		sql.setCallback(Sqls.callback.entities());
		sql.setEntity(dao().getEntity(App.class));
		dao().execute(sql);
		List<App> applist = sql.getList(App.class);
    	
    	//List<AppSystem>	appSystemList = dao().query(AppSystem.class, Cnd.where("sysModelId", "=", mod.getId()).and("state", "=", "1").desc("id"));
    	
    	//for(AppSystem appSystem : appSystemList){
    	//	Cnd c = Cnd.where("id", "=", appSystem.getAppId());
    	//	c.and("deleted","=","0");
    	//	c.and("appName","LIKE","%" + appName + "%");
    	//	App app = dao().fetch(App.class, c);
		for(App app : applist){
    		if(app!=null){
    			AppInfoVo vo = new AppInfoVo();
    			SystemFile systemFile = dao().fetch(SystemFile.class, Cnd.where("id","=",app.getAppFileId()));
    			vo.setApp_id(app.getId().toString());
    			vo.setApp_name(app.getAppName());
    			vo.setDownload_count(systemFile.getDownCount().toString());
    			vo.setFilesize(systemFile.getFileSize());
    			vo.setVersion(app.getAppVersion());
    			vo.setPackageName(app.getPackName());
    			vo.setIcon(downUrl + app.getIconId());
    			vo.setRecommend_index(app.getRecommendIndex()==null ? "1" : app.getRecommendIndex().toString());
    			vo.setUrl(downUrl + systemFile.getId());
    			list.add(vo);
    		}
    		
    	}
    	map.put("result", list);
    	return map;
    	
    }
    
    public Map<String, List<AppInfoVo>> appList(String id, String sysTypeId, String downUrl){
    	if(id.length()!=34){
    		return null;
    	}
    	Map<String, List<AppInfoVo>> map = new HashMap<String, List<AppInfoVo>>();
    	CarMachine carMach = dao().fetch(CarMachine.class, Cnd.where("value","=",id));
    	if(carMach == null){
    		CarMachine in = new CarMachine();
    		in.setValue(id);
    		in.setCreateDate(Times.sD(new Date()));
    		dao().insert(in);
    	}
    	
    	List<AppInfoVo> list = new ArrayList<AppInfoVo>();
    	//Map<String, String> map = new HashMap<String, String>();
    	
    	SystemModel mod = dao().fetch(SystemModel.class, Cnd.where("name", "=", sysTypeId));
    	
    	if(mod==null){
    		return null;
    	}
    	
    	String sqlString = "select * from t_app a where a.id in (select sa.app_id from t_app_system sa where sa.sys_model_id in "
    								+ " (select s.id from t_system_model s where s.name = '$name') and sa.state = 1) "
    								+ " and a.deleted = 0 order by a.recommend_index desc, a.create_date desc";
    	
    	Sql sql = Sqls.create(sqlString);
    	sql.vars().set("name", mod.getName());
    	sql.setCallback(Sqls.callback.entities());
    	sql.setEntity(dao().getEntity(App.class));
    	dao().execute(sql);
    	List<App> applist = sql.getList(App.class);
    	
    	//List<AppSystem>	appSystemList = dao().query(AppSystem.class, Cnd.where("sysModelId", "=", mod.getId()).and("state", "=", "1").desc("id"));
    	
    	//for(AppSystem appSystem : appSystemList){
    	//	Cnd c = Cnd.where("id", "=", appSystem.getAppId());
    	//	c.and("deleted","=","0");
    	//	c.desc("createDate");
    	//	App app = dao().fetch(App.class, c);
    	
    	for(App app : applist){
    		if(app!=null){
    			
    			AppInfoVo vo = new AppInfoVo();
    			
    			SystemFile systemFile = dao().fetch(SystemFile.class, Cnd.where("id","=",app.getAppFileId()));
    			
    			vo.setApp_id(app.getId().toString());
    			//map.put("app_id", app.getAppName());
    			
    			vo.setApp_name(app.getAppName());
    			//map.put("app_name", app.getAppName());
    			
    			vo.setDownload_count(systemFile.getDownCount().toString());
    			//map.put("download_count", systemFile.getDownCount().toString());
    			
    			vo.setFilesize(systemFile.getFileSize());
    			//map.put("filesize", systemFile.getFileSize());
    			
    			vo.setVersion(app.getAppVersion());
    			//map.put("version", app.getAppVersion());
    			
    			vo.setPackageName(app.getPackName());
    			//map.put("packageName", app.getPackName());
    			
    			vo.setIcon(downUrl + app.getIconId());
    			//map.put("icon", downUrl + app.getIconId());
    			
    			vo.setRecommend_index(app.getRecommendIndex()==null ? "1" : app.getRecommendIndex().toString());
    			//map.put("recommend_index", app.getRecommendIndex()==null ? "1" : app.getRecommendIndex().toString());
    			
    			vo.setUrl(downUrl + systemFile.getId());
    			//map.put("url", downUrl + systemFile.getId());
    			
    			list.add(vo);
    			
    			//List<AppPicture> appPicList = dao().query(AppPicture.class, Cnd.where("appId", "=", app.getId()).and("state", "=", "1"));
    		}
    		
    	}
    	map.put("result", list);
    	return map;
    }
    
    public Map<String, List<AppInterfaceVo>> findNewApp(String id, String sysTypeId, String downUrl){
    	if(id.length()!=34){
    		return null;
    	}
    	Map<String, List<AppInterfaceVo>> map = new HashMap<String, List<AppInterfaceVo>>();
    	CarMachine carMach = dao().fetch(CarMachine.class, Cnd.where("value","=",id));
    	if(carMach == null){
    		CarMachine in = new CarMachine();
    		in.setValue(id);
    		in.setCreateDate(Times.sD(new Date()));
    		dao().insert(in);
    	}
    	
    	List<AppInterfaceVo> list = new ArrayList<AppInterfaceVo>();
    	
    	SystemModel mod = dao().fetch(SystemModel.class, Cnd.where("name", "=", sysTypeId));
    	
    	List<AppSystem>	appSystemList = dao().query(AppSystem.class, Cnd.where("sysModelId", "=", mod.getId()).and("state", "=", "1"));
    	
    	for(AppSystem appSystem : appSystemList){
    		Cnd c = Cnd.where("id", "=", appSystem.getAppId());
    		c.and("deleted","=","0");
    		App app = dao().fetch(App.class, c);
    		if(app!=null){
    			AppInterfaceVo vo = new AppInterfaceVo();
    			
    			SystemFile systemFile = dao().fetch(SystemFile.class, Cnd.where("id","=",app.getAppFileId()));
    			
    			//map.put("appName", app.getAppName());
    			vo.setAppName(app.getAppName());
    			
    			//map.put("appDescribe", app.getDescribe());
    			vo.setAppDescribe(app.getDescribes());
    			
    			//map.put("appSize", systemFile.getFileSize());
    			vo.setAppSize(systemFile.getFileSize());
    			
    			//map.put("appVersion", app.getAppVersion());
    			vo.setAppVersion(app.getAppVersion());
    			
    			//map.put("packageName", app.getPackName());
    			vo.setPackageName(app.getPackName());
    			
    			if(app.getAppClassify()==Constants.APP_CLASSIFY_CHEZAI){
    				//map.put("appTypeId", "车载");
    				vo.setAppTypeId("车载");
    			}else if(app.getAppClassify()==Constants.APP_CLASSIFY_RUANJIAN){
    				//map.put("appTypeId", "软件");
    				vo.setAppTypeId("软件");
    			}else if(app.getAppClassify()==Constants.APP_CLASSIFY_YOUXI){
    				//map.put("appTypeId", "游戏");
    				vo.setAppTypeId("游戏");
    			}
    			
    			//map.put("appDetail", app.getDetail());
    			vo.setAppDetail(app.getDetail());
    			
    			//map.put("isRecommended", app.getRecommend());
    			vo.setIsRecommended(app.getRecommend());
    			
    			//map.put("isDefault", app.getUpgrade());
    			vo.setIsDefault(app.getUpgrade());
    			
    			List<AppPicture> appPicList = dao().query(AppPicture.class, Cnd.where("appId", "=", app.getId()).and("state", "=", "1"));
    			
    			if(appPicList!=null){
    				//map.put("appIcon", downUrl + appPicList.get(0).getFileId());
    				vo.setScreenShot(downUrl + appPicList.get(0).getFileId());
    			}
    			
    			//map.put("screenShot", null);
    			vo.setAppIcon(downUrl + app.getIconId());
    			
    			list.add(vo);
    		}
    		
    	}
    	map.put("result", list);
    	return map;
    }
    
    
    
    
    /**
	 * 系统升级 接口
	 * @param id  车机id
	 * @param model  系统型号
	 * @param sysVersion  系统版本(例:V100)
	 * @param firmware   固件型号(例:DD09-2014-PL3)
	 * @param firmVersion  固件版本(例:V1.4.2.0303)
	 * @param bootanimation   动画文件的md5值
	 * @return
	 */
    public Map<String, String> findNewPage(String id, String model, String sysVersion, String firmware, 
    					String firmVersion, String bootanimation, String downUrl){
    	if(id.length()!=34){
    		return null;
    	}
    	CarMachine carMach = dao().fetch(CarMachine.class, Cnd.where("value","=",id));
    	if(carMach == null){
    		CarMachine in = new CarMachine();
    		in.setValue(id);
    		in.setCreateDate(Times.sD(new Date()));
    		dao().insert(in);
    	}
    	
    	Map<String, String> map = new HashMap<String, String>();
    	//-----------------------------------------获取系统包------------------------------------
    	
    	String wheresql = "select p.id, p.sys_version, p.type, p.file_id, p.file_name, p.describes from t_system_package p, " +
			" t_system_model m, t_system_package_model pm where m.id = pm.sys_model_id and pm.sys_pack_id = p.id and pm.state = 1 " +
			" and p.deleted = 0 and m.name = '$name' and pm.state = 1 and p.type = $type and p.sys_version ";
    	
    	String issql = " > '$version' order by p.sys_version desc";
    	
    	Sql sql = Sqls.create(wheresql+issql);
    	
    	sql.vars().set("name", model);
    	sql.vars().set("version", sysVersion);
    	sql.vars().set("type", 1);
    	sql.setCallback(Sqls.callback.entities());
    	sql.setEntity(dao().getEntity(SystemModelVo.class));
    	dao().execute(sql);
    	
    	//除当前版本的系统包
    	List<SystemModelVo> list = sql.getList(SystemModelVo.class);
		if (list != null && list.size() > 0) {
			SystemFile systemFile = dao().fetch(SystemFile.class, list.get(0).getFileId());
			
			//最新全量包版本号
			//String sysNewVersion = list.get(0).getSysVersion();
    		File file = new File(systemFile.getUrl());
        	
        	String sysFullMD5 = Lang.md5(file);
        	map.put("sysFullMD5", sysFullMD5);//1111111111111
			
			map.put("sysVersion", list.get(0).getSysVersion());
			map.put("sysFullUrl", downUrl + list.get(0).getFileId());
			map.put("sysFullUrlSuffix", list.get(0).getFileName());
		}
		
		String packs = "like '%$version%' order by p.sys_version desc ";
		//String newpacks = " and p.sys_version like '%$nversion%'";
		Sql sql0 = Sqls.create(wheresql + packs);
		sql0.vars().set("name", model);
		sql0.vars().set("version", sysVersion);
		//sql0.vars().set("nversion", sysNewVersion);
		sql0.vars().set("type", 2);
		sql0.setCallback(Sqls.callback.entities());
		sql0.setEntity(dao().getEntity(SystemModelVo.class));
		dao().execute(sql0);
		// 最新系统包
		List<SystemModelVo> sysli = sql0.getList(SystemModelVo.class);
		if (sysli != null && sysli.size() > 0) {
			// 增量最新系统包
			SystemModelVo modVo = sysli.get(0);
			map.put("sysAddUrl", downUrl + modVo.getFileId());
			SystemFile sysFile = dao().fetch(SystemFile.class, modVo.getFileId());
	    		
	    	File addfile = new File(sysFile.getUrl());
	        	
	        String sysAddMD5 = Lang.md5(addfile);
	        map.put("sysAddMD5", sysAddMD5);//11111111111111
			map.put("sysAddUrlSuffix", modVo.getFileName());
			map.put("sysAddUrlVersion", modVo.getSysVersion());
			map.put("sysAddLog", modVo.getDescribe());
		}
		
    	
    	//-----------------------------------------获取固件------------------------------------
    	
    	String frimSql = "select f.id, f.firm_version, f.file_id, f.file_name from t_firmware f, t_firm_model m, t_firm_ware_model fm where f.id =" +
    			" fm.firmware_id and m.id = fm.firm_model_id and fm.state = 1 and f.deleted = 0 and m.name = '$firmware' and f.firm_version ";
    	
    	String isfrimSql = " > '$firmVersion' order by f.firm_version desc";
    	
    	Sql firm = Sqls.create(frimSql + isfrimSql);
    	
    	firm.vars().set("firmware", firmware);
    	firm.vars().set("firmVersion", firmVersion);
    	firm.setCallback(Sqls.callback.entities());
    	firm.setEntity(dao().getEntity(FirmModelVo.class));
    	dao().execute(firm);
    	
    	//除当前版本的固件包
    	List<FirmModelVo> firmList = firm.getList(FirmModelVo.class);
    	
    	if(firmList != null && firmList.size() > 0){
    		String firms = "= '$firmVersion'";
    		Sql sql1 = Sqls.create(frimSql + firms);
    		sql1.vars().set("firmware", firmware);
    		sql1.vars().set("firmVersion", firmList.get(0).getFirmVersion());
    		sql1.setCallback(Sqls.callback.entities());
    		sql1.setEntity(dao().getEntity(FirmModelVo.class));
    		dao().execute(sql1);
    		map.put("firmVersion", firmList.get(0).getFirmVersion());
    		
    		//最新系统包
    		List<FirmModelVo> li = sql1.getList(FirmModelVo.class);
    		for(FirmModelVo firmVo : li){
    			//最新固件包
    			SystemFile systemFile = dao().fetch(SystemFile.class, firmVo.getFileId());
	    		
	    		File file = new File(systemFile.getUrl());
	        	
	        	String firmMD5 = Lang.md5(file);
	        	map.put("firmMD5", firmMD5);
    			map.put("firmUrl", downUrl + firmVo.getFileId());
    			map.put("firmUrlSuffix", firmVo.getFileName());
    		}
    	}
    	
    	//-----------------------------------------获取动画------------------------------------
    	
    	String animaSql = "select a.id, a.name, a.file_id, a.file_name from t_animation a, t_animation_model am, " +
    				"t_system_model m where a.id = am.anima_id and am.sys_model_id = m.id and a.deleted = 0 and m.name = '$name'";
    	
    	Sql anima = Sqls.create(animaSql);
    	
    	anima.vars().set("name", model);
    	anima.setCallback(Sqls.callback.entities());
    	anima.setEntity(dao().getEntity(AnimaModelVo.class));
    	dao().execute(anima);
    	
    	//获取动画包
    	List<AnimaModelVo> animaList = anima.getList(AnimaModelVo.class);
    	
    	for(AnimaModelVo animaVo : animaList){
    		SystemFile systemFile = dao().fetch(SystemFile.class, animaVo.getFileId());
    		
    		File file = new File(systemFile.getUrl());
        	
        	String animaMd5 = Lang.md5(file);
        	//客户端md5值是否与服务端md5值相等   相等则没有最新动画包   不相等则存在最新动画包
        	if(!animaMd5.equals(bootanimation)){
        		map.put("bootanimation", animaMd5);
        		map.put("bootUrl", downUrl + animaVo.getFileId());
        		map.put("bootUrlSuffix", animaVo.getFileName());
        	}
    	}
    	
    	return map;
    }
    
//    public static void main(String[] args) {
//    	String describe = "【清理加速】一键加速解决卡慢 深度清理释放空间\n"+
//    								"【骚扰拦截】智能拦截垃圾短信 骚扰电话不再烦心\n"+
//    								"【流量监控】实时监控手机流量 自动校准省钱省心\n"+
//    								"【支付保镖】全面守护支付环境 手机先赔保障安全\n"+
//    								"【强力杀毒】国际顶级杀毒引擎 木马病毒无所遁形\n"+
//    								"【软件管理】轻松告别预装软件 手机应用安全纯净";
//    	String createDate = "2015-05-12 18:14:16";
//    	String version = "1.2.32";
//    	String detail = "1. 摇一摇全新改版，各类大奖、免费流量…想要就摇；\n "+
//    							"2. WiFi体检全面升级，体验更爽，蹭网更安全； \n"+
//    							"3. 接到骚扰电话？吐槽号码，让它无处可逃！\n "+
//    							"4. 上划工具箱大变身~你可以任意移除不想显示的功能；\n "+
//    							"5. 通知栏可以切换展示“已用”和“剩余”流量";
//    	
//		//System.out.println(getDescribeAndDetail(describe, createDate.substring(0, 10), version, detail));
//	}

}
