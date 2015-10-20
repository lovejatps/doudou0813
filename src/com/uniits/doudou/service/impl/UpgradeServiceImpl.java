/**
 * @description doudou com.uniits.doudou.service.impl
 */
package com.uniits.doudou.service.impl;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.QueryResult;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.Entity;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.img.Images;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Files;
import org.nutz.lang.Lang;
import org.nutz.lang.Times;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.upload.TempFile;
import org.nutz.service.IdEntityService;

import com.uniits.doudou.constants.SystemConfig;
import com.uniits.doudou.model.Animation;
import com.uniits.doudou.model.AnimationModel;
import com.uniits.doudou.model.ApkInfo;
import com.uniits.doudou.model.App;
import com.uniits.doudou.model.AppPicture;
import com.uniits.doudou.model.AppSystem;
import com.uniits.doudou.model.FirmModel;
import com.uniits.doudou.model.FirmWareModel;
import com.uniits.doudou.model.Firmware;
import com.uniits.doudou.model.Producecar;
import com.uniits.doudou.model.SystemFile;
import com.uniits.doudou.model.SystemModel;
import com.uniits.doudou.model.SystemPackage;
import com.uniits.doudou.model.SystemPackageModel;
import com.uniits.doudou.service.RightsService;
import com.uniits.doudou.service.UpgradeService;
import com.uniits.doudou.utils.ApkIconTool;
import com.uniits.doudou.utils.ApkUtil;
import com.uniits.doudou.utils.CommonUtils;
import com.uniits.doudou.utils.Constants;
import com.uniits.doudou.vo.AnimationVo;
import com.uniits.doudou.vo.AppVo;
import com.uniits.doudou.vo.FirmwareVo;
import com.uniits.doudou.vo.ModelVO;
import com.uniits.doudou.vo.SystemPackageVo;

/**
 * @author wangguiyong
 * @since 2015-3-28 下午2:42:30
 * @description 
 */
@IocBean(name="upgradeService" ,args = {"refer:dao"})
public class UpgradeServiceImpl extends IdEntityService implements UpgradeService {
    
	@Inject
	private RightsService rigistService ; 
	
    private Map<String, String> getProduceName(){
    	Map<String, String> map = new HashMap<String, String>();
    	
    	List<Producecar> list = rigistService.findAll();
    	for(Producecar p :list){
    		map.put(""+p.getId(), p.getName());
    	}  	
    	return map ;
    	
    }
    
	
    public UpgradeServiceImpl() {
        super();
    }

    @SuppressWarnings("unchecked")
	public UpgradeServiceImpl(Dao dao, Class<Object> entityType) {
        super(dao, entityType);
    }

    public UpgradeServiceImpl(Dao dao) {
        super(dao);
    }

    @Override
    public void addSys(SystemModel sys) {
        dao().insert(sys);
    }
    
    public void addFirm(FirmModel firm){
    	dao().insert(firm);
    }
    
    public void updateApp(Long[] modelId, App app, Long[] pictureFileIds){
    	if(modelId != null && modelId.length > 0){
    		
    		if(app.getUpgrade().equals("0")){
    			app.setUpgrade("YES");
    		}else{
    			app.setUpgrade("NO");
    		}
    		
    		/*if(app.getRecommend().equals("0")){
    			app.setRecommend("YES");
    		}else{
    			app.setRecommend("NO");
    		}*/
    		
    		app.setFileName(app.getAppName());
    		app.setCreateDate(Times.sDT(new Date()));
    		dao().update(app);
    		
    		List<AppSystem> oldList = this.fetchAppSystem(app.getId());
    		for(AppSystem oldmod : oldList){
    			dao().delete(AppSystem.class, oldmod.getId());
    		}
    		
    		for(Long modId : modelId){
	    		AppSystem appSys = new AppSystem();
    			appSys.setState(Constants.NORMAL_STATE);
    			List<AppSystem> appList = dao().query(AppSystem.class, Cnd.where("state", "=", "1").and("sysModelId", "=", modId));
    			for(AppSystem appsystem : appList){
    				App capp = this.fetchApp(appsystem.getAppId());
    				if(capp.getPackName().endsWith(app.getPackName()) && capp.getAppVersion().compareTo(app.getAppVersion()) > 0){
    					
    					//上传的是低版本
    					appSys.setState(Constants.ABNORMAL_STATE);
    				}else if(capp.getPackName().endsWith(app.getPackName())){
    					//上传的是当前版本或新版本
    					appSys.setState(Constants.NORMAL_STATE);
    					appsystem.setState(Constants.ABNORMAL_STATE);
    					SystemFile newfile = dao().fetch(SystemFile.class, Cnd.where("id", "=", app.getAppFileId()));
    					SystemFile oldFile = dao().fetch(SystemFile.class, Cnd.where("id", "=", capp.getAppFileId()));
    					newfile.setDownCount(oldFile.getDownCount());
    					
    					List<AppSystem> oldsys = dao().query(AppSystem.class, Cnd.where("appId", "=", capp.getId()).and("state", "=", "1"));
    					if(oldsys !=null && oldsys.size()==1){
    						capp.setDeleted(1L);
    						dao().update(capp);
    					}
    					
    					dao().update(newfile);
    					dao().update(appsystem);
    				}
    			}
	    		
	    		appSys.setAppId(app.getId());
	    		appSys.setSysModelId(modId);
	    		dao().insert(appSys);
    		}
    		
    		List<AppPicture> picList = this.fetchAppPicture(app.getId());
    		for(AppPicture oldmod : picList){
    			dao().delete(AppPicture.class, oldmod.getId());
    		}
    		
    		for(Long picFileId : pictureFileIds){
    			AppPicture appPic = new AppPicture();
    			appPic.setAppId(app.getId());
    			appPic.setFileId(picFileId);
    			appPic.setState(Constants.NORMAL_STATE);
    			dao().insert(appPic);
    		}
    	}
    }
    
    //yyc
    public void addApp(Long[] modelId, App app, Long[] pictureFileIds){
    	if(modelId != null && modelId.length > 0){
    		
    		if(app.getUpgrade().equals("0")){
    			app.setUpgrade("YES");
    		}else{
    			app.setUpgrade("NO");
    		}
    		
    		/*if(app.getRecommend().equals("0")){
    			app.setRecommend("YES");
    		}else{
    			app.setRecommend("NO");
    		}*/
    		
    		
    		app.setCreateDate(Times.sDT(new Date()));
    		app.setFileName(app.getAppName());
    		app.setDeleted(0L);
    		dao().insert(app);
    		
    		for(Long modId : modelId){
    			AppSystem appSys = new AppSystem();
    			appSys.setState(Constants.NORMAL_STATE);
    			List<AppSystem> appList = dao().query(AppSystem.class, Cnd.where("state", "=", "1").and("sysModelId", "=", modId));
    			for(AppSystem appsystem : appList){
    				App capp = this.fetchApp(appsystem.getAppId());
    				if(capp.getPackName().endsWith(app.getPackName()) && capp.getAppVersion().compareTo(app.getAppVersion()) > 0){
    					//上传的是低版本
    					appSys.setState(Constants.ABNORMAL_STATE);
    				}else if(capp.getPackName().endsWith(app.getPackName())){
    					//上传的是当前版本或新版本
    					appSys.setState(Constants.NORMAL_STATE);
    					
    					SystemFile newfile = dao().fetch(SystemFile.class, Cnd.where("id", "=", app.getAppFileId()));
    					SystemFile oldFile = dao().fetch(SystemFile.class, Cnd.where("id", "=", capp.getAppFileId()));
    					newfile.setDownCount(oldFile.getDownCount());
    					dao().update(newfile);
    					
    					List<AppSystem> oldsys = dao().query(AppSystem.class, Cnd.where("appId", "=", capp.getId()).and("state", "=", "1"));
    					if(oldsys !=null && oldsys.size()==1){
    						capp.setDeleted(1L);
    						dao().update(capp);
    					}
    					
    					appsystem.setState(Constants.ABNORMAL_STATE);
    					dao().update(appsystem);
    				}
    			}
	    		
	    		appSys.setAppId(app.getId());
	    		appSys.setSysModelId(modId);
	    		dao().insert(appSys);
    		}
    		
    		for(Long picFileId : pictureFileIds){
    			AppPicture appPic = new AppPicture();
    			appPic.setAppId(app.getId());
    			appPic.setFileId(picFileId);
    			appPic.setState(Constants.NORMAL_STATE);
    			dao().insert(appPic);
    		}
    	}
    	
    }
    
    public void updateFirmware(Long[] modelId, Firmware firm, TempFile tmpFile){
    	if(modelId != null && modelId.length > 0){
	    	SystemFile systemFile = new SystemFile();
	    	if(tmpFile!=null){
		    	try {
			    	String oldFileName = tmpFile.getMeta().getFileLocalName();
					//附件原名
					systemFile.setName(oldFileName);
					firm.setFileName(oldFileName);
					String suffix = oldFileName.substring(oldFileName.lastIndexOf("."));
					String fileName = Times.format("yyyy-MM-dd hh-mm-ss", new Date()) + suffix;
					File file = new File(SystemConfig.uploadPath + "/" + fileName);
					
					systemFile.setUrl(file.getPath());
				
					Files.copyFile(tmpFile.getFile(), file);
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				//固件 附件
				
				//获取文件大小
				systemFile.setFileSize(CommonUtils.getFileSize(tmpFile.getFile()));
				
				systemFile.setDownCount(0L);
				systemFile.setCreateDate(Times.sDT(new Date()));
				systemFile.setDeleted(0L);
				dao().insert(systemFile);
				
				SystemFile sysFile1 = dao().fetch(SystemFile.class, Cnd.where("id","=",firm.getFileId()));
				File file1 = new File(sysFile1.getUrl());
				file1.delete();
				
				//固件
				firm.setFileId(systemFile.getId());
	    	}
			
	    	dao().update(firm);
	    	
	    	List<FirmWareModel> oldList = this.fetchFirmwareModList(firm.getId());
    		for(FirmWareModel oldfirmmod : oldList){
    			dao().delete(FirmWareModel.class, oldfirmmod.getId());
    		}

	    	for(Long modId : modelId){
    			Cnd c = Cnd.where("1","=","1");
    	        c.and("state", "=", 1);
    	        c.and("firmModelId", "=", modId);
                List<FirmWareModel> modList = dao().query(FirmWareModel.class, c);
                
                for(FirmWareModel firmMod : modList){
                	Firmware fir = dao().fetch(Firmware.class, Cnd.where("id","=",firmMod.getFirmwareId()));
    	        	if(fir != null && fir.getDeleted()!= 1L && fir.getFirmVersion().equals(firm.getFirmVersion())){
    	        		firmMod.setState(Constants.ABNORMAL_STATE);
    	        		dao().update(firmMod);
    	        		fir.setDeleted(1L);
    	        		dao().update(fir);
    	        	}
    	        }
    			
                FirmWareModel fm = new FirmWareModel();
                fm.setFirmModelId(modId);
                fm.setFirmwareId(firm.getId());
                fm.setState(Constants.NORMAL_STATE);
    			dao().insert(fm);
    		}
			
    	}
		
    }
    
    /**
     * yyc
     * 上传固件
     * */
    public String addFirmware(Long[] modelId, Firmware firm, TempFile tmpFile){
    
    	String md5_ = "";
		File tmpFile_ = null;
		String shortName = "";
		
		try{
			ZipFile zf = new ZipFile(tmpFile.getFile());
			Enumeration ee = zf.getEntries();
			if(null != ee){
				InputStream md5_txt = null;
				InputStream file_ = null;
				while(ee.hasMoreElements()){
					ZipEntry ze = (ZipEntry)ee.nextElement();
					if(!ze.isDirectory()){
						if(ze.getSize() > 0){
							if(ze.getName().equals("md5.txt")){
								md5_txt = zf.getInputStream(ze);
							} else {
								file_ = zf.getInputStream(ze);
								shortName = ze.getName();
								if(shortName != null && !"".equals(shortName)){
    								String regEx = "^[a-zA-Z0-9.\\-_]*$";
    								System.out.println(shortName.matches(regEx));
    								if(!shortName.matches(regEx)){
    									return "nerror";
    								}
								}
							}
		
						}
					}
				}
				if(md5_txt != null && file_ != null){
					String uuid = UUID.randomUUID().toString();
					File tf = new File(SystemConfig.uploadPathTemp + "/" + uuid);
					BufferedOutputStream os = new BufferedOutputStream(
							new FileOutputStream(tf));
					byte[] buffer = new byte[4096];
					int ln = 0;
					while((ln = file_.read(buffer, 0, 4096)) >= 0){
						os.write(buffer, 0, ln);
					}
					os.flush();
					os.close();
					tf = new File(SystemConfig.uploadPathTemp + "/" + uuid);
					//String md5 = getMd5ByFile(tf);
					String md5 = Lang.md5(tf);
					byte[] by = readInputStream(md5_txt);
					String md51 = new String(by);
					if(md5.equalsIgnoreCase(md51)){
						md5_ = md5;
						tmpFile_ = new File(SystemConfig.uploadPathTemp + "/" + uuid);
					}
				}
			}
		} catch(Exception e){
			System.out.println("上传出错,请稍后重试");
			e.printStackTrace();
		}
		// md5 end
		
		if(md5_ == null || md5_.length() == 0){
			//TODO 返回客户端md5校验失败
			return "error";
		}
		
		SystemFile systemFile = new SystemFile();
		
		String tmpFileName = tmpFile.getMeta().getFileLocalName();//确认下是否为短名称
		
		systemFile.setName(shortName);
		firm.setFileName(shortName);
		String suffix = shortName.substring(shortName.lastIndexOf("."));
				
		
				String fileName = Times.format("yyyy-MM-dd hh-mm-ss", new Date()) + suffix;
				File file = new File(SystemConfig.uploadPath + "/" + fileName);
				
				systemFile.setUrl(file.getPath());
			
				try {
					Files.copyFile(tmpFile_, file);
				} catch (IOException e) {
					System.out.println("上传出错,稍后重试");
					e.printStackTrace();
				}
				
			//固件 附件
			
			//获取文件大小
			systemFile.setFileSize(CommonUtils.getFileSize(tmpFile_));
			
			systemFile.setDownCount(0L);
			systemFile.setCreateDate(Times.sDT(new Date()));
			systemFile.setDeleted(0L);
			systemFile.setMd5(md5_);
			dao().insert(systemFile);
			
			//固件
			firm.setCreateDate(Times.sDT(new Date()));
			firm.setFileId(systemFile.getId());
			firm.setDeleted(0L);
			dao().insert(firm);
			
			for(Long modId : modelId){
    			Cnd c = Cnd.where("1","=","1");
    	        c.and("state", "=", 1);
    	        c.and("firmModelId", "=", modId);
                List<FirmWareModel> modList = dao().query(FirmWareModel.class, c);
                
                for(FirmWareModel firmMod : modList){
                	Firmware fir = dao().fetch(Firmware.class, Cnd.where("id","=",firmMod.getFirmwareId()));
    	        	if(fir != null && fir.getDeleted()!= 1L && fir.getFirmVersion().equals(firm.getFirmVersion())){
    	        		firmMod.setState(Constants.ABNORMAL_STATE);
    	        		dao().update(firmMod);
    	        		fir.setDeleted(1L);
    	        		dao().update(fir);
    	        	}
    	        }
    			
                FirmWareModel fm = new FirmWareModel();
                fm.setFirmModelId(modId);
                fm.setFirmwareId(firm.getId());
                fm.setState(Constants.NORMAL_STATE);
    			dao().insert(fm);
    		}
			return "add";
			
    	}
		
    
    
    public void updateAnimation(Long[] modelId, Animation ani, TempFile tmpFile){
    	if(modelId != null && modelId.length > 0){
    		SystemFile systemFile = new SystemFile();
    		if(tmpFile!=null){
	    		//附件原名
	    		systemFile.setName(tmpFile.getMeta().getFileLocalName());
	    		//动画添加附件名
	    		ani.setFileName(tmpFile.getMeta().getFileLocalName());
	    		String suffix = tmpFile.getMeta().getFileLocalName().substring(tmpFile.getMeta().getFileLocalName().lastIndexOf("."));
	    		String fileName = Times.format("yyyy-MM-dd hh-mm-ss", new Date()) + suffix;
	    		File file = new File(SystemConfig.uploadPath + "/" + fileName);
	    		
	    		systemFile.setUrl(file.getPath());
	    		try {
					Files.copyFile(tmpFile.getFile(), file);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				//获取文件大小
				systemFile.setFileSize(CommonUtils.getFileSize(tmpFile.getFile()));
				systemFile.setDownCount(0L);
				
				systemFile.setCreateDate(Times.sDT(new Date()));
				systemFile.setDeleted(0L);
				dao().insert(systemFile);
				
				SystemFile sysFile1 = dao().fetch(SystemFile.class, Cnd.where("id","=",ani.getFileId()));
				File file1 = new File(sysFile1.getUrl());
				file1.delete();
				
				ani.setFileId(systemFile.getId());
    		}
    			
    		dao().update(ani);
			
			List<AnimationModel> oldList = this.fetchAnimaModList(ani.getId());
    		for(AnimationModel oldanimod : oldList){
    			dao().delete(AnimationModel.class, oldanimod.getId());
    		}
			
			for(Long modId : modelId){
    			Cnd c = Cnd.where("1","=","1");
    	        c.and("state", "=", 1);
    	        c.and("sysModelId", "=", modId);
                List<AnimationModel> animodList = dao().query(AnimationModel.class, c);
                
                for(AnimationModel aniModel : animodList){
                	Animation animation = dao().fetch(Animation.class, Cnd.where("id","=",aniModel.getAnimaId()));
    	        	if(animation != null && animation.getDeleted()!= 1L){
    	        		aniModel.setState(Constants.ABNORMAL_STATE);
    	        		dao().update(aniModel);
    	        		animation.setDeleted(1L);
    	        		dao().update(animation);
    	        	}
    	        }
    			
                AnimationModel anm = new AnimationModel();
                anm.setSysModelId(modId);
                anm.setAnimaId(ani.getId());
                anm.setState(Constants.NORMAL_STATE);
    			dao().insert(anm);
    		}
    	}
    }
    
    //yyc
    public String addAnimation(Long[] modelId, Animation ani, TempFile tmpFile){
    	if(modelId != null && modelId.length > 0){
    		/*SystemFile systemFile = new SystemFile();
    		//附件原名
    		systemFile.setName(tmpFile.getMeta().getFileLocalName());
    		//动画添加附件名
    		ani.setFileName(tmpFile.getMeta().getFileLocalName());
    		String suffix = tmpFile.getMeta().getFileLocalName().substring(tmpFile.getMeta().getFileLocalName().lastIndexOf("."));*/
    		// md5 start
    		String md5_ = "";
    		File tmpFile_ = null;
    		String shortName = "";
    		
    		try{
    			ZipFile zf = new ZipFile(tmpFile.getFile());
    			Enumeration ee = zf.getEntries();
    			if(null != ee){
    				InputStream md5_txt = null;
    				InputStream file_ = null;
    				while(ee.hasMoreElements()){
    					ZipEntry ze = (ZipEntry)ee.nextElement();
    					if(!ze.isDirectory()){
    						if(ze.getSize() > 0){
    							if(ze.getName().equals("md5.txt")){
    								md5_txt = zf.getInputStream(ze);
    							} else {
    								file_ = zf.getInputStream(ze);
    								shortName = ze.getName();
    								if(shortName != null && !"".equals(shortName)){
	    								String regEx = "^[a-zA-Z0-9.\\-_]*$";
	    								System.out.println(shortName.matches(regEx));
	    								if(!shortName.matches(regEx)){
	    									return "nerror";
	    								}
    								}
    							}
    		
    						}
    					}
    				}
    				if(md5_txt != null && file_ != null){
						String uuid = UUID.randomUUID().toString();
						File tf = new File(SystemConfig.uploadPathTemp + "/" + uuid);
						BufferedOutputStream os = new BufferedOutputStream(
								new FileOutputStream(tf));
						byte[] buffer = new byte[4096];
						int ln = 0;
						while((ln = file_.read(buffer, 0, 4096)) >= 0){
							os.write(buffer, 0, ln);
						}
						os.flush();
						os.close();
						tf = new File(SystemConfig.uploadPathTemp + "/" + uuid);
						//String md5 = getMd5ByFile(tf);
						String md5 = Lang.md5(tf);
						byte[] by = readInputStream(md5_txt);
						String md51 = new String(by);
						if(md5.equalsIgnoreCase(md51)){
							md5_ = md5;
							tmpFile_ = new File(SystemConfig.uploadPathTemp + "/" + uuid);
						}
    				}
    			}
    		} catch(Exception e){
    			e.printStackTrace();
    		}
    		// md5 end
    		
    		if(md5_ == null || md5_.length() == 0){
    			
    			return "error";
    		} 
    		
    		SystemFile systemFile = new SystemFile();
    		
			String tmpFileName = tmpFile.getMeta().getFileLocalName();//确认下是否为短名称
			
			systemFile.setName(shortName);
			ani.setFileName(shortName);
			String suffix = shortName.substring(shortName.lastIndexOf("."));
    		String fileName = Times.format("yyyy-MM-dd hh-mm-ss", new Date()) + suffix;
    		File file = new File(SystemConfig.uploadPath + "/" + fileName);
    		
    		systemFile.setUrl(file.getPath());
    		try {
				Files.copyFile(tmpFile_, file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//获取文件大小
			systemFile.setFileSize(CommonUtils.getFileSize(tmpFile_));
			systemFile.setDownCount(0L);
			
			systemFile.setCreateDate(Times.sDT(new Date()));
			systemFile.setDeleted(0L);
			systemFile.setMd5(md5_);
			dao().insert(systemFile);
			
			ani.setCreateDate(Times.sDT(new Date()));
			ani.setFileId(systemFile.getId());
			ani.setDeleted(0L);
			dao().insert(ani);
			
			for(Long modId : modelId){
    			Cnd c = Cnd.where("1","=","1");
    	        c.and("state", "=", 1);
    	        c.and("sysModelId", "=", modId);
                List<AnimationModel> animodList = dao().query(AnimationModel.class, c);
                
                for(AnimationModel aniModel : animodList){
                	Animation animation = dao().fetch(Animation.class, Cnd.where("id","=",aniModel.getAnimaId()));
    	        	if(animation != null && animation.getDeleted()!= 1L){
    	        		aniModel.setState(Constants.ABNORMAL_STATE);
    	        		dao().update(aniModel);
    	        		animation.setDeleted(1L);
    	        		dao().update(animation);
    	        	}
    	        }
    			
                AnimationModel anm = new AnimationModel();
                anm.setSysModelId(modId);
                anm.setAnimaId(ani.getId());
                anm.setState(Constants.NORMAL_STATE);
    			dao().insert(anm);
    		}
    	}
    	return "add";
    }
    
    public Map<String, Object> doUploadAppPicture(TempFile tempFile){
    	
    	Map<String, Object> map = new HashMap<String, Object>();
    	
    	SystemFile systemFile = new SystemFile();
    	String name = tempFile.getMeta().getFileLocalName();
    	
		systemFile.setName(name);
		
		BufferedImage images = Images.read(tempFile.getFile());
		
		float fl = tempFile.getFile().length() / (1024*1024);
		
		System.out.println(images.getHeight()+"---------------"+images.getWidth());
		
		if(!((images.getHeight() >= 800 && images.getWidth() >= 480) || (images.getHeight() >= 480 && images.getWidth() >= 800))){
			map.put("error", "1");
			return map;
		}
		if(fl > 2){
			map.put("error", "2");
			return map;
		}
		
		BufferedImage image = Images.zoomScale(Images.read(tempFile.getFile()), 800, 480);
		
		
		String suffix = name.substring(name.lastIndexOf("."));
		
		String fileName = Times.format("yyyy-MM-dd hh-mm-ss", new Date()) + suffix;
		
		String newUrlandName = SystemConfig.uploadPath + "/" + Times.format("yyyy-MM-dd hh-mm-ss", new Date()) + " 800-480" + suffix;
		
		File file = new File(SystemConfig.uploadPath + "/" + fileName);
		
    	map.put("picUrl", file.getPath());
    	systemFile.setUrl(file.getPath());
    	try {
			Files.copyFile(tempFile.getFile(), file);
			
			ImageIO.write(image, name.substring(name.lastIndexOf(".")+1), new File(newUrlandName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//获取文件大小
		systemFile.setFileSize(CommonUtils.getFileSize(tempFile.getFile()));
		systemFile.setDownCount(0L);
		
		systemFile.setCreateDate(Times.sDT(new Date()));
		systemFile.setDeleted(0L);
		dao().insert(systemFile);
		
		map.put("picFileId", systemFile.getId());
		if(images.getWidth() > images.getHeight()){
			map.put("height", 100);
			map.put("width", images.getWidth()*100/images.getHeight());
		}else{
			map.put("width", 100);
			map.put("height", images.getHeight()*100/images.getWidth());
		}
		map.put("path", systemFile.getUrl());
    	return map;
    }
    
    public String checkAppModel(String modelIds, String version, String packname){
    	if(modelIds != null && modelIds != "" && version != null && packname != null){
			String[] str = modelIds.split(",");
			for(String modId : str){
				List<AppSystem> appList = dao().query(AppSystem.class, Cnd.where("state", "=", "1").and("sysModelId", "=", modId));
				for(AppSystem appsystem : appList){
					App capp = this.fetchApp(appsystem.getAppId());
					if(capp.getPackName().endsWith(packname) && capp.getAppVersion().compareTo(version) > 0){
						//上传的是低版本
						return "1";
					}else if(capp.getPackName().endsWith(packname)){
						//上传的是当前版本或新版本
						
					}
				}
	    	}
			
		}
    	
    	return "0";
    }
    
    //yyc
    public Map<String, Object> doUploadAppFile(TempFile tempFile, String modelIds, String version, String packname){
    	
    	
    	/*SystemFile systemFile = new SystemFile();
    	systemFile.setDownCount(0L);
		systemFile.setName(tempFile.getMeta().getFileLocalName());
		String suffix = tempFile.getMeta().getFileLocalName().substring(tempFile.getMeta().getFileLocalName().lastIndexOf("."));*/
    	
    	String md5_ = "";
		File tmpFile_ = null;
		String shortName = "";
		
		try{
			ZipFile zf = new ZipFile(tempFile.getFile());
			Enumeration ee = zf.getEntries();
			if(null != ee){
				InputStream md5_txt = null;
				InputStream file_ = null;
				while(ee.hasMoreElements()){
					ZipEntry ze = (ZipEntry)ee.nextElement();
					if(!ze.isDirectory()){
						if(ze.getSize() > 0){
							if(ze.getName().equals("md5.txt")){
								md5_txt = zf.getInputStream(ze);
							} else {
								file_ = zf.getInputStream(ze);
								shortName = ze.getName();
								/*if(shortName != null && !"".equals(shortName)){
    								String regEx = "^[a-zA-Z0-9.\\-_]*$";
    								Boolean b = shortName.matches(regEx);
    								if("false".equals(b.toString())){
    									Map<String, Object> nerror = new HashMap<String,Object>();
    									nerror.put("error", 4);
    									return nerror;
    								}
								}*/
							}
		
						}
					}
				}
				if(md5_txt != null && file_ != null){
					String uuid = UUID.randomUUID().toString();
					File tf = new File(SystemConfig.uploadPathTemp + "/" + uuid);
					BufferedOutputStream os = new BufferedOutputStream(
							new FileOutputStream(tf));
					byte[] buffer = new byte[4096];
					int ln = 0;
					while((ln = file_.read(buffer, 0, 4096)) >= 0){
						os.write(buffer, 0, ln);
					}
					os.flush();
					os.close();
					tf = new File(SystemConfig.uploadPathTemp + "/" + uuid);
					//String md5 = getMd5ByFile(tf);
					String md5 = Lang.md5(tf);
					byte[] by = readInputStream(md5_txt);
					String md51 = new String(by);
					if(md5.equalsIgnoreCase(md51)){
						md5_ = md5;
						tmpFile_ = new File(SystemConfig.uploadPathTemp + "/" + uuid);
					}
				}
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		// md5 end
		
		if(md5_ == null || md5_.length() == 0){
			//TODO 返回客户端md5校验失败
			Map<String, Object> md5Map = new HashMap<String,Object>();
			md5Map.put("error", 3);
			return md5Map;
		}
		
		SystemFile systemFile = new SystemFile();
		
		String tmpFileName = tempFile.getMeta().getFileLocalName();//确认下是否为短名称
		
		systemFile.setName(shortName);
		systemFile.setDownCount(0L);//Modify Zhouyangyang
		//pack.setFileName(tmpFileName);
		String suffix = shortName.substring(shortName.lastIndexOf("."));
    	
		String fileName = Times.format("yyyy-MM-dd hh-mm-ss", new Date()) + suffix;
		File file = new File(SystemConfig.uploadPath + "/" + fileName);
		Map<String, Object> map = new HashMap<String, Object>();
		
		systemFile.setUrl(file.getPath());
		try {
			Files.copyFile(tmpFile_, file);
			
			ApkUtil apkutil = new ApkUtil();
			ApkInfo apk = apkutil.getApkInfo(file.getPath());
		    
			if (apk != null) {
				map.put("packageName", apk.getPackageName());
				map.put("versionName", apk.getVersionName());
				map.put("versionCode", apk.getVersionCode());
				map.put("appName", apk.getApplicationLable());
			}
			if(modelIds != null && modelIds != ""){
				String[] str = modelIds.split(",");
				for(String modId : str){
					List<AppSystem> appList = dao().query(AppSystem.class, Cnd.where("state", "=", "1").and("sysModelId", "=", modId));
					for(AppSystem appsystem : appList){
						App capp = this.fetchApp(appsystem.getAppId());
						if(capp.getPackName().endsWith(apk.getPackageName()) && capp.getAppVersion().compareTo(apk.getVersionName()) > 0){
							//上传的是低版本
							map.put("error", "1");
							return map;
						}else if(capp.getPackName().endsWith(apk.getPackageName())){
							//上传的是当前版本或新版本
							SystemFile cappFile = dao().fetch(SystemFile.class, Cnd.where("id", "=", capp.getAppFileId()));
							systemFile.setDownCount(cappFile.getDownCount());
						}
					}
		    	}
			}
			if(version != null && version != "" && packname != null && packname != ""){
				if(!apk.getPackageName().equals(packname)){
					map.put("error", "2");
					return map;
				}
				if(apk.getVersionName().compareTo(version) < 0){
					map.put("error", "1");
					return map;
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//获取文件大小
		systemFile.setFileSize(CommonUtils.getFileSize(tmpFile_));
		
		systemFile.setCreateDate(Times.sDT(new Date()));
		systemFile.setDeleted(0L);
		systemFile.setMd5(md5_);
		
		//添加附件
		dao().insert(systemFile);
		
		//icon
		SystemFile iconFile = new SystemFile();
		iconFile.setName(systemFile.getName()+"_"+"icon");
		System.out.println(file.getPath());
		iconFile.setUrl(ApkIconTool.getApkIconPath(file.getPath()));
		iconFile.setDownCount(0L);
		iconFile.setCreateDate(Times.sDT(new Date()));
		iconFile.setDeleted(0L);
		dao().insert(iconFile);
		
		map.put("fileId", systemFile.getId());
		map.put("iconId", iconFile.getId());
		
		return map;
    }
    
    public void updateSysPackage(Long[] modelId, SystemPackage pack, TempFile tmpFile){
    	if(modelId != null && modelId.length > 0){
    		SystemFile systemFile = new SystemFile();
    		if(tmpFile!=null){
				systemFile.setName(tmpFile.getMeta().getFileLocalName());
				
				pack.setFileName(tmpFile.getMeta().getFileLocalName());
				String suffix = tmpFile.getMeta().getFileLocalName().substring(tmpFile.getMeta().getFileLocalName().lastIndexOf("."));
				String fileName = Times.format("yyyy-MM-dd hh-mm-ss", new Date()) + suffix;
				File file = new File(SystemConfig.uploadPath + "/" + fileName);
				
				systemFile.setUrl(file.getPath());
				try {
					Files.copyFile(tmpFile.getFile(), file);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				//获取文件大小
				systemFile.setFileSize(CommonUtils.getFileSize(tmpFile.getFile()));
				systemFile.setDownCount(0L);
				
				systemFile.setCreateDate(Times.sDT(new Date()));
				systemFile.setDeleted(0L);
				dao().insert(systemFile);
				
				SystemFile sysFile1 = dao().fetch(SystemFile.class, Cnd.where("id","=",pack.getFileId()));
				File file1 = new File(sysFile1.getUrl());
				file1.delete();
				
				pack.setFileId(systemFile.getId());
				dao().update(pack);
    		}else{
    			dao().update(pack);
    		}
    		
    		List<SystemPackageModel> oldList = this.fetchSysPackModList(pack.getId());
    		for(SystemPackageModel oldpackmod : oldList){
    			dao().delete(SystemPackageModel.class, oldpackmod.getId());
    		}
    		
    		for(Long modId : modelId){
    			//if(pack.getType()==Constants.COMPLATE_PACKAGE){
	    			Cnd c = Cnd.where("1","=","1");
	    	        c.and("state", "=", 1);
	    	        c.and("sysModelId", "=", modId);
	                List<SystemPackageModel> modelList = dao().query(SystemPackageModel.class, c);
	                
	                for(SystemPackageModel packModel : modelList){
	    	        	SystemPackage pac = dao().fetch(SystemPackage.class, Cnd.where("id","=",packModel.getSysPackId()));
	    	        	if(pac.getType()==pack.getType() && pac.getDeleted() != 1L &&
	    	        				pac.getSysVersion().equals(pack.getSysVersion())){
	    	        		packModel.setState(Constants.ABNORMAL_STATE);
	    	        		dao().update(packModel);
	    	        		pac.setDeleted(1L);
	    	        		dao().update(pac);
	    	        	}
	    	        }
    			//}
    			SystemPackageModel packModel = new SystemPackageModel();
    			packModel.setSysModelId(modId);
    			packModel.setSysPackId(pack.getId());
    			packModel.setState(Constants.NORMAL_STATE);
    			dao().insert(packModel);
    		}
    	}
    }
    
    
    // add md5
    
   /* public static String getMd5ByFile(File file) throws FileNotFoundException {  
        String value = null;  
        FileInputStream in = new FileInputStream(file);  
	    try {  
	        MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());  
	        MessageDigest md5 = MessageDigest.getInstance("MD5");  
	        md5.update(byteBuffer);  
	        BigInteger bi = new BigInteger(1, md5.digest());  
	        value = bi.toString(16);  
	    } catch (Exception e) {  
	        e.printStackTrace();  
	    } finally {  
	            if(null != in) {  
	                try {  
	                in.close();  
	            } catch (IOException e) {  
	                e.printStackTrace();  
	            }  
	        }  
	    }  
	    return value;  
    }  */
    
    public static byte[] readInputStream(InputStream input) throws IOException{
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte [] data = new byte [1024] ; 
		int len = 0;
		while((len = input.read(data)) != -1){
			output.write(data, 0, len);
		}
		input.close();
		return output.toByteArray() ;
    }
    
    //yyc
    public String addSysPackage(Long[] modelId, SystemPackage pack, TempFile tmpFile){
    	if(modelId != null && modelId.length > 0){
    		
    		// md5 start
    		String md5_ = "";
    		File tmpFile_ = null;
    		String shortName = "";
    		String shortNameGbk="";
    		
    		try{
    			ZipFile zf = new ZipFile(tmpFile.getFile());
    			Enumeration ee = zf.getEntries();
    			if(null != ee){
    				InputStream md5_txt = null;
    				InputStream file_ = null;
    				while(ee.hasMoreElements()){
    					ZipEntry ze = (ZipEntry)ee.nextElement();
    					if(!ze.isDirectory()){
    						if(ze.getSize() > 0){
    							if(ze.getName().equals("md5.txt")){
    								
    								md5_txt = zf.getInputStream(ze);
    							} else {
    								file_ = zf.getInputStream(ze);
    								shortName = ze.getName();
    								if(shortName != null && !"".equals(shortName)){
	    								String regEx = "^[a-zA-Z0-9.\\-_]*$";
	    								System.out.println(shortName.matches(regEx));
	    								if(!shortName.matches(regEx)){
	    									return "nerror";
	    								}
    								}
    							}
    						}
    					}
    				}
    				if(md5_txt != null && file_ != null){
						String uuid = UUID.randomUUID().toString();
						File tf = new File(SystemConfig.uploadPathTemp + "/" + uuid);
						BufferedOutputStream os = new BufferedOutputStream(
								new FileOutputStream(tf));
						byte[] buffer = new byte[4096];
						int ln = 0;
						while((ln = file_.read(buffer, 0, 4096)) >= 0){
							os.write(buffer, 0, ln);
						}
						os.flush();
						os.close();
						tf = new File(SystemConfig.uploadPathTemp + "/" + uuid);
						//String md5 = getMd5ByFile(tf);
						String md5 = Lang.md5(tf);
						byte[] by = readInputStream(md5_txt);
						String md51 = new String(by);
						if(md5.equalsIgnoreCase(md51)){
							md5_ = md5;
							tmpFile_ = new File(SystemConfig.uploadPathTemp + "/" + uuid);
						}
    				}
    			}
    		} catch(Exception e){
    			e.printStackTrace();
    		}
    		// md5 end
    		
    		if(md5_ == null || md5_.length() == 0){
    			//TODO 返回客户端md5校验失败
    			return "error";
    		}
    		
    		SystemFile systemFile = new SystemFile();
    		
			String tmpFileName = tmpFile.getMeta().getFileLocalName();//确认下是否为短名称
			
			systemFile.setName(shortName);
			pack.setFileName(shortName);
			String suffix = shortName.substring(shortName.lastIndexOf("."));
			String fileName = Times.format("yyyy-MM-dd hh-mm-ss", new Date()) + suffix;
			File file = new File(SystemConfig.uploadPath + "/" + fileName);
			
			systemFile.setUrl(file.getPath());
			try {
				Files.copyFile(tmpFile_, file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//获取文件大小
			systemFile.setFileSize(CommonUtils.getFileSize(tmpFile_));
			systemFile.setDownCount(0L);
			
			systemFile.setCreateDate(Times.sDT(new Date()));
			systemFile.setDeleted(0L);
			systemFile.setMd5(md5_);
			//systemFile.setProduceid(pack.getProduceid());
			dao().insert(systemFile);
			
			pack.setCreateDate(Times.sDT(new Date()));
			pack.setFileId(systemFile.getId());
			pack.setDeleted(0L);
			dao().insert(pack);
    		
    		for(Long modId : modelId){
    			//if(pack.getType()==Constants.COMPLATE_PACKAGE){
	    			Cnd c = Cnd.where("1","=","1");
	    	        c.and("state", "=", 1);
	    	        c.and("sysModelId", "=", modId);
	                List<SystemPackageModel> modelList = dao().query(SystemPackageModel.class, c);
	                
	                for(SystemPackageModel packModel : modelList){
	    	        	SystemPackage pac = dao().fetch(SystemPackage.class, Cnd.where("id","=",packModel.getSysPackId()));
	    	        	if(pac.getType()==pack.getType() && pac.getDeleted() != 1L &&
	    	        				pac.getSysVersion().equals(pack.getSysVersion())){
	    	        		packModel.setState(Constants.ABNORMAL_STATE);
	    	        		dao().update(packModel);
	    	        		pac.setDeleted(1L);
	    	        		dao().update(pac);
	    	        	}
	    	        }
    			//}
    			SystemPackageModel packModel = new SystemPackageModel();
    			packModel.setSysModelId(modId);
    			packModel.setSysPackId(pack.getId());
    			packModel.setState(Constants.NORMAL_STATE);
    			dao().insert(packModel);
    		}
    	}
    	return "add";
    }
    
    public List<SystemModel> findAllSystemModel(){
        Cnd cnd = Cnd.where("1", "=", "1");
        List<SystemModel> modelList = dao().query(SystemModel.class, cnd);
        
    	return modelList;
    }
    
    public ModelVO getModel(){
        Cnd cnd = Cnd.where("1", "=", "1");
        List<Producecar> list = rigistService.findAll();
        ModelVO vo = new ModelVO();
        List<SystemModel> modelList = dao().query(SystemModel.class, cnd);
        vo.setModelList(modelList);
        vo.setProds(list);        
    	return vo;
    }
    
    
    
    public List<SystemPackageModel> fetchSysPackModList(Long id){
    	Cnd cnd = Cnd.where("state", "=", "1");
    	cnd.and("sysPackId", "=", id);
        List<SystemPackageModel> modelList = dao().query(SystemPackageModel.class, cnd);
        return modelList;
    }
    
    public List<AnimationModel> fetchAnimaModList(Long id){
    	Cnd cnd = Cnd.where("state", "=", "1");
    	cnd.and("animaId", "=", id);
        List<AnimationModel> modelList = dao().query(AnimationModel.class, cnd);
        return modelList;
    }
    
    public List<FirmWareModel> fetchFirmwareModList(Long id){
    	Cnd cnd = Cnd.where("state", "=", "1");
    	cnd.and("firmwareId", "=", id);
        List<FirmWareModel> modelList = dao().query(FirmWareModel.class, cnd);
        return modelList;
    }
    
    public List<FirmModel> findAllFirmModel(){
    	Cnd cnd = Cnd.where("1", "=", "1");
        List<FirmModel> modelList = dao().query(FirmModel.class, cnd);
    	return modelList;
    }
    
    @Override
    public SystemModel fetchSys(String name) {
        return dao().fetch(SystemModel.class, Cnd.where("name","=",name));
    }
    
    public FirmModel fetchFirm(String name){
    	return dao().fetch(FirmModel.class, Cnd.where("name","=",name));
    }
    
    public SystemPackage fetchSysPackage(Long id){
    	return dao().fetch(SystemPackage.class, Cnd.where("id","=",id));
    }
    
    public Animation fetchAnimation(Long id){
    	return dao().fetch(Animation.class, Cnd.where("id","=",id));
    }
    
    public App fetchApp(Long id){
    	return dao().fetch(App.class, Cnd.where("id","=",id));
    }
    
    public Firmware fetchFirmware(Long id){
    	return dao().fetch(Firmware.class, Cnd.where("id","=",id));
    }
    
    @Override
    public String deleteSystemModel(long id) {
    	
        List<AnimationModel> aniList = dao().query(AnimationModel.class, Cnd.where("state", "=", "1").and("sysModelId", "=", id));
    	
        if(!Lang.isEmpty(aniList)) return "2";
        
        List<SystemPackageModel> packList = dao().query(SystemPackageModel.class, Cnd.where("state", "=", "1").and("sysModelId", "=", id));
        
        if(!Lang.isEmpty(packList)) return "2";
        
        List<AppSystem> appList = dao().query(AppSystem.class, Cnd.where("state", "=", "1").and("sysModelId", "=", id));
    	
        if(!Lang.isEmpty(appList)) return "2";
        
        dao().delete(SystemModel.class, id);
        
        return "1";
    }
    
    public String deleteFirmModel(long id){

        List<FirmWareModel> firmList = dao().query(FirmWareModel.class, Cnd.where("state", "=", "1").and("firmModelId", "=", id));
        
        if(!Lang.isEmpty(firmList)) return "2";
    	
    	dao().delete(FirmModel.class, id);
    	
    	return "1";
    }
    
    public void deleteAnimation(Long id){
    	Animation ani = dao().fetch(Animation.class, Cnd.where("id","=",id));
    	ani.setDeleted(1L);
    	dao().update(ani);
    	
    	SystemFile sysFile = dao().fetch(SystemFile.class, Cnd.where("id","=",ani.getFileId()));
    	File file = new File(sysFile.getUrl());
    	file.delete();
    	
    	List<AnimationModel> oldList = this.fetchAnimaModList(ani.getId());
		for(AnimationModel oldanimod : oldList){
			oldanimod.setState(Constants.ABNORMAL_STATE);
			dao().update(oldanimod);
		}
    	
    }
    
    public void deleteFirmware(Long id){
    	Firmware ani = dao().fetch(Firmware.class, Cnd.where("id","=",id));
    	ani.setDeleted(1L);
    	dao().update(ani);
    	
    	SystemFile sysFile = dao().fetch(SystemFile.class, Cnd.where("id","=",ani.getFileId()));
    	File file = new File(sysFile.getUrl());
    	file.delete();
    	
    	List<FirmWareModel> oldList = this.fetchFirmwareModList(ani.getId());
		for(FirmWareModel oldmod : oldList){
			oldmod.setState(Constants.ABNORMAL_STATE);
			dao().update(oldmod);
		}
    }
    
    public void deleteApp(Long id){
    	App app = dao().fetch(App.class, Cnd.where("id","=",id));
    	app.setDeleted(1L);
    	dao().update(app);
    	
    	SystemFile sysFile = dao().fetch(SystemFile.class, Cnd.where("id","=",app.getAppFileId()));
    	File file = new File(sysFile.getUrl());
    	file.delete();
    	
        List<AppPicture> modelList = dao().query(AppPicture.class, Cnd.where("state", "=", "1").and("appId", "=", app.getId()));
        for(AppPicture appPic : modelList){
        	SystemFile picFile = dao().fetch(SystemFile.class, Cnd.where("id","=",appPic.getFileId()));
        	File picfi = new File(picFile.getUrl());
        	picfi.delete();
        }
    	
    	List<AppSystem> oldList = this.fetchAppSystem(app.getId());
		for(AppSystem oldmod : oldList){
			oldmod.setState(Constants.ABNORMAL_STATE);
			dao().update(oldmod);
		}
    	
    }
    
    public List<AppSystem> fetchAppSystem(Long id){
    	Cnd cnd = Cnd.where("state", "=", "1");
    	cnd.and("appId", "=", id);
        List<AppSystem> modelList = dao().query(AppSystem.class, cnd);
        return modelList;
    }
    
    public List<AppPicture> fetchAppPicture(Long id){
    	Cnd cnd = Cnd.where("state", "=", "1");
    	cnd.and("appId", "=", id);
        List<AppPicture> modelList = dao().query(AppPicture.class, cnd);
        return modelList;
    }
    
    public void deleteSyspackage(Long id){
    	SystemPackage pack = dao().fetch(SystemPackage.class, Cnd.where("id","=",id));
    	pack.setDeleted(1L);
    	dao().update(pack);
    	
    	SystemFile sysFile = dao().fetch(SystemFile.class, Cnd.where("id","=",pack.getFileId()));
    	File file = new File(sysFile.getUrl());
    	file.delete();
    	
    	List<SystemPackageModel> oldList = this.fetchSysPackModList(pack.getId());
		for(SystemPackageModel oldmod : oldList){
			oldmod.setState(Constants.ABNORMAL_STATE);
			dao().update(oldmod);
		}
    }
    
    public void doDownSystemPackage(Long id, HttpServletResponse res, HttpServletRequest req){
    	SystemFile sysFile = dao().fetch(SystemFile.class, Cnd.where("id","=",id));
    	//File file = new File(sysFile.getUrl());
    	
    	BufferedInputStream bis = null;
	    try {
	        File file = new File(sysFile.getUrl());
	        if (file.exists()) {
	            long p = 0L;
	            long toLength = 0L;
	            long contentLength = 0L;
	            int rangeSwitch = 0; // 0,从头开始的全文下载；1,从某字节开始的下载（bytes=27000-）；2,从某字节开始到某字节结束的下载（bytes=27000-39000）
	            long fileLength;
	            String rangBytes = "";
	            fileLength = file.length();
	 
	            // get file content
	            InputStream ins = new FileInputStream(file);
	            bis = new BufferedInputStream(ins);
	 
	            // tell the client to allow accept-ranges
	            res.reset();
	            res.setHeader("Accept-Ranges", "bytes");
	 
	            // client requests a file block download start byte
	            String range = req.getHeader("Range");
	            if(range==null){
	                contentLength = fileLength;
	                sysFile.setDownCount(sysFile.getDownCount()+1);
	                dao().update(sysFile);
	            }
	            if (range != null && range.trim().length() > 0 && !"null".equals(range)) {
	                res.setStatus(javax.servlet.http.HttpServletResponse.SC_PARTIAL_CONTENT);
	                rangBytes = range.replaceAll("bytes=", "");
	                if (rangBytes.endsWith("-")) {  // bytes=270000-
	                    rangeSwitch = 1;
	                    p = Long.parseLong(rangBytes.substring(0, rangBytes.indexOf("-")));
	                    contentLength = fileLength - p;  // 客户端请求的是270000之后的字节（包括bytes下标索引为270000的字节）
	                } else { // bytes=270000-320000
	                    rangeSwitch = 2;
	                    String temp1 = rangBytes.substring(0, rangBytes.indexOf("-"));
	                    String temp2 = rangBytes.substring(rangBytes.indexOf("-") + 1, rangBytes.length());
	                    p = Long.parseLong(temp1);
	                    toLength = Long.parseLong(temp2);
	                    contentLength = toLength - p + 1; // 客户端请求的是 270000-320000 之间的字节
	                }
	            } 
	            
	            // 如果设设置了Content-Length，则客户端会自动进行多线程下载。如果不希望支持多线程，则不要设置这个参数。
	            // Content-Length: [文件的总大小] - [客户端请求的下载的文件块的开始字节]
	            res.setHeader("Content-Length", new Long(contentLength).toString());
	 
	            // 断点开始
	            // 响应的格式是:
	            // Content-Range: bytes [文件块的开始字节]-[文件的总大小 - 1]/[文件的总大小]
	            if (rangeSwitch == 1) {
	                String contentRange = new StringBuffer("bytes ").append(new Long(p).toString()).append("-")
	                        .append(new Long(fileLength - 1).toString()).append("/")
	                        .append(new Long(fileLength).toString()).toString();
	                res.setHeader("Content-Range", contentRange);
	                bis.skip(p);
	            } else if (rangeSwitch == 2) {
	                String contentRange = range.replace("=", " ") + "/" + new Long(fileLength).toString();
	                res.setHeader("Content-Range", contentRange);
	                bis.skip(p);
	            } else {
	                String contentRange = new StringBuffer("bytes ").append("0-")
	                        .append(fileLength - 1).append("/")
	                        .append(fileLength).toString();
	                res.setHeader("Content-Range", contentRange);
	            }
	 
	            String fileName = file.getName();
	            res.setContentType("multipart/form-data");
	            res.addHeader("Content-Disposition", "attachment;filename=" + fileName);
	 
	            OutputStream out = res.getOutputStream();
	            int n = 0;
	            long readLength = 0;
	            int bsize = 1024;
	            byte[] bytes = new byte[bsize];
	            if (rangeSwitch == 2) {
	                // 针对 bytes=27000-39000 的请求，从27000开始写数据                    
	                while (readLength <= contentLength - bsize) {
	                    n = bis.read(bytes);
	                    readLength += n;
	                    out.write(bytes, 0, n);
	                }
	                if (readLength <= contentLength) {
	                    n = bis.read(bytes, 0, (int) (contentLength - readLength));
	                    out.write(bytes, 0, n);
	                }                   
	            } else {
	                while ((n = bis.read(bytes)) != -1) {
	                    out.write(bytes,0,n);                                                      
	                }                   
	            }
	            out.flush();
	            out.close();
	            bis.close();
	        } else {
	        	
	        }
	    } catch (IOException ie) {
	        // 忽略 ClientAbortException 之类的异常
	    } catch (Exception e) {
	        
	    }
    }

	@Override
	public List<SystemModel> addSysBatch(List<SystemModel> list) {
		List<SystemModel> successList = new ArrayList<SystemModel>();
		for(SystemModel model : list){
			addSys(model);
			successList.add(model);
		}
		return successList;
	}
	
	public QueryResult findFirmware(int pageNo, int pageSize, NutMap map){
		Cnd cnd = Cnd.where("deleted","=","0");
		Map<String, String> prods =  getProduceName();
		if(!Lang.isEmpty(map)) {
			if(!Lang.isEmpty(map.getString("version"))) {
				cnd.and("firmVersion","LIKE","%" + map.getString("version") + "%");
			}
			
			if(!Lang.isEmpty(map.getString("startDate"))) {
				cnd.and("left(create_date,10)",">=", map.getString("startDate"));
			}
			
			if(!Lang.isEmpty(map.getString("endDate"))) {
				cnd.and("left(create_date,10)","<=", map.getString("endDate"));
			}
		}
		
		cnd.desc("createDate").desc("id");
        Pager pager = dao().createPager(pageNo, pageSize);
        List<Firmware> list = dao().query(Firmware.class, cnd, pager);
		
        List<FirmwareVo> voList = null;
        if(!Lang.isEmpty(list)){
        	voList = new ArrayList<FirmwareVo>();
        	int i=(pageNo-1)*pageSize;
        	for(Firmware firm : list){
        		Cnd c = Cnd.where("firmwareId", "=", firm.getId());
        		c.and("state", "=", Constants.NORMAL_STATE);
                List<FirmWareModel> firmodList = dao().query(FirmWareModel.class, c);
                String stringModle = "";
                String produceName ="";
                for(FirmWareModel firmod : firmodList){
                	FirmModel mod = dao().fetch(FirmModel.class, Cnd.where("id","=",firmod.getFirmModelId()));
                	stringModle += mod.getName() + ",";
                	produceName += null ==prods.get(mod.getProduceid()+"") ? "" :prods.get(mod.getProduceid()+"")+",";
                }
                if(stringModle=="")continue;
                
                stringModle = stringModle.substring(0, stringModle.length()-1);
                if(produceName.length()>1){
                	produceName = produceName.substring(0,produceName.length()-1);
                }
                i++;
        		voList.add(new FirmwareVo(firm.getId(), firm.getFirmVersion(), firm.getFileId(), 
        				firm.getFileName(), firm.getCreateDate(), stringModle, (long)i,produceName));
        	}
        }else{
        	pager.setPageNumber(0);
        }
        
        pager.setRecordCount(dao().count(Firmware.class,cnd));
        
        return new QueryResult(voList, pager);
	}
	
	public QueryResult findAnimation1(int pageNo, int pageSize, NutMap map ,Long roleId,Long produceid){
		Map<String, String> prods =  getProduceName();
		Cnd cnd = Cnd.where("deleted","=","0");
		
		if(!Lang.isEmpty(map)) {
			if(!Lang.isEmpty(map.getString("name"))) {
				cnd.and("name","LIKE","%" + map.getString("name") + "%");
			}
			
			if(!Lang.isEmpty(map.getString("startDate"))) {
				cnd.and("left(create_date,10)",">=", map.getString("startDate"));
			}
			
			if(!Lang.isEmpty(map.getString("endDate"))) {
				cnd.and("left(create_date,10)","<=", map.getString("endDate"));
			}
			
		}
		
		cnd.desc("createDate").desc("id");
        Pager pager = dao().createPager(pageNo, pageSize);
        List<Animation> list = dao().query(Animation.class, cnd, pager);
        
        Cnd cp = Cnd.where("produce_id","=","-1");
        List<SystemModel> listmodel = dao().query(SystemModel.class, cp);
        
        System.out.println(listmodel.size());
        
        List<AnimationVo> voList = null;
        if(!Lang.isEmpty(list)){
        	voList = new ArrayList<AnimationVo>();
        	int i=(pageNo-1)*pageSize;
        	for(Animation ani : list){
        		Cnd c = Cnd.where("animaId", "=", ani.getId());
        		c.and("state", "=", Constants.NORMAL_STATE);
                List<AnimationModel> animodList = dao().query(AnimationModel.class, c);
                String stringModle = "";
                String produceName ="";
                for(AnimationModel animod : animodList){
                	SystemModel mod = dao().fetch(SystemModel.class, Cnd.where("id","=",animod.getSysModelId()));
                	stringModle += mod.getName() + ",";
                	produceName += null ==prods.get(mod.getProduceid()+"") ? "" :prods.get(mod.getProduceid()+"")+",";
                }
                if(stringModle=="")continue;
                
                stringModle = stringModle.substring(0, stringModle.length()-1);
                if(produceName.length()>1){
                	produceName = produceName.substring(0,produceName.length()-1);
                }
                i++;
      
                voList.add(new AnimationVo(ani.getId(), ani.getName(), ani.getFileId(), 
        					ani.getFileName(), ani.getCreateDate(), stringModle, (long)i,produceName ));
        	}
        }else{
        	pager.setPageNumber(0);
        }
        
        pager.setRecordCount(dao().count(Animation.class,cnd));
        
        return new QueryResult(voList, pager);
	}
	
	public QueryResult findPagePack(int pageNo, int pageSize, NutMap map){
		Cnd cnd = Cnd.where("deleted","=","0");
		
		if(!Lang.isEmpty(map)) {
			if(!Lang.isEmpty(map.getString("version"))) {
				cnd.and("sys_version","LIKE","%" + map.getString("version") + "%");
			}
			
			if(!Lang.isEmpty(map.getString("startDate"))) {
				cnd.and("left(create_date,10)",">=", map.getString("startDate"));
			}
			
			if(!Lang.isEmpty(map.getString("endDate"))) {
				cnd.and("left(create_date,10)","<=", map.getString("endDate"));
			}
			
		}
		
		cnd.desc("createDate").desc("id");
        Pager pager = dao().createPager(pageNo, pageSize);
        List<SystemPackage> list = dao().query(SystemPackage.class, cnd, pager);
        
        List<SystemPackageVo> voList = null;
        if(!Lang.isEmpty(list)) {
        	Map<String, String> prods =  getProduceName();
        	voList = new ArrayList<SystemPackageVo>();
        	int i=(pageNo-1)*pageSize;
        	for(SystemPackage pack : list){
        		Cnd c = Cnd.where("sysPackId", "=", pack.getId());
        		c.and("state", "=", Constants.NORMAL_STATE);
                List<SystemPackageModel> modelList = dao().query(SystemPackageModel.class, c);
                String stringModle = "";
                String produceName ="";
                for(SystemPackageModel packModel : modelList){
                	SystemModel mod = dao().fetch(SystemModel.class, Cnd.where("id","=",packModel.getSysModelId()));
                	stringModle += mod.getName() + ",";
                	produceName += null ==prods.get(mod.getProduceid()+"") ? "" :prods.get(mod.getProduceid()+"")+",";
                }
                if(stringModle=="")continue;
                
                stringModle = stringModle.substring(0, stringModle.length()-1);
                if(produceName.length()>1){
                	produceName = produceName.substring(0,produceName.length()-1);
                }
                i++;
                
        		voList.add(new SystemPackageVo(pack.getId(), pack.getSysVersion(), pack.getType(), 
        				pack.getFileId(), pack.getFileName(), pack.getCreateDate(), stringModle, (long)i ,produceName ));
        	}
        }else{
        	pager.setPageNumber(0);
        }
        
        pager.setRecordCount(dao().count(SystemPackage.class,cnd));
        
        return new QueryResult(voList, pager);
        
	}
	
	public QueryResult showAppPack(int pageNo, int pageSize, NutMap map){
		Cnd cnd = Cnd.where("deleted","=","0");
		if(!Lang.isEmpty(map)) {
			if(!Lang.isEmpty(map.getString("appName"))) {
				cnd.and("appName","LIKE","%" + map.getString("appName") + "%");
			}
			
			if(!Lang.isEmpty(map.getString("appClassify"))) {
				cnd.and("appClassify","=", map.getLong("appClassify"));
			}
			
		}
		cnd.desc("createDate").desc("id");
        Pager pager = dao().createPager(pageNo, pageSize);
        List<App> list = dao().query(App.class, cnd, pager);
        
        List<AppVo> voList = null;
        
        if(!Lang.isEmpty(list)) {
        	voList = new ArrayList<AppVo>();
        	int i=(pageNo-1)*pageSize;
	        for(App app : list){
	        	Cnd c = Cnd.where("appId", "=", app.getId());
        		c.and("state", "=", Constants.NORMAL_STATE);
                List<AppSystem> appSysList = dao().query(AppSystem.class, c);
                String stringModle = "";
                for(AppSystem appsys : appSysList){
                	SystemModel mod = dao().fetch(SystemModel.class, Cnd.where("id","=",appsys.getSysModelId()));
                	stringModle += mod.getName() + ",";
                }
                if(stringModle=="")continue;
                
                stringModle = stringModle.substring(0, stringModle.length()-1);
                
                Cnd cn = Cnd.where("appId", "=", app.getId());
                cn.and("state", "=", Constants.NORMAL_STATE);
                List<AppPicture> appPicList = dao().query(AppPicture.class, c);
                
                Long picFileId = null;
                if(!Lang.isEmpty(appPicList)){
                	picFileId = appPicList.get(0).getFileId();
                }
                
                SystemFile sysfile = dao().fetch(SystemFile.class, Cnd.where("id","=", app.getAppFileId()));
                i++;
                voList.add(new AppVo(app.getId(), app.getAppName(), app.getAppVersion(), app.getAppFileId(), picFileId, app.getAppClassify(), app.getCreateDate(),
                		sysfile.getFileSize(), sysfile.getDownCount(), stringModle, (long)i ));
	        }
	        
        }else{
        	pager.setPageNumber(0);
        }
        pager.setRecordCount(dao().count(App.class,cnd));
		
        return new QueryResult(voList, pager);
	}
	
public QueryResult findFirm(int pageNo, int pageSize, NutMap map) {
	
		Map<String, String> prods =  getProduceName();
		
		Cnd cnd = Cnd.where("1","=","1");
		
		if(!Lang.isEmpty(map)) {
			if(!Lang.isEmpty(map.getString("sys"))) {
				cnd.and("name","LIKE","%" + map.getString("sys") + "%");
			}
			
			if(!Lang.isEmpty(map.getString("startDate"))) {
				cnd.and("left(create_date,10)",">=", map.getString("startDate"));
			}
			
			if(!Lang.isEmpty(map.getString("endDate"))) {
				cnd.and("left(create_date,10)","<=", map.getString("endDate"));
			}
			
		}
        cnd.desc("createDate").desc("id");
        Pager pager = dao().createPager(pageNo, pageSize);
        List<FirmModel> list = dao().query(FirmModel.class, cnd, pager);
        
        if(!Lang.isEmpty(list)){
        	int k=(pageNo-1)*pageSize;
        	for(int i=0; i<list.size(); i++){
        		k++;
        		list.get(i).setSerialNumber((long) (k));
        		list.get(i).setProducename(prods.get(""+list.get(i).getProduceid()));
        	}
        }else{
        	pager.setPageNumber(0);
        }
        
        pager.setRecordCount(dao().count(FirmModel.class,cnd));
        
        return new QueryResult(list, pager);
	}
	
	@Override
	public QueryResult findPage(int pageNo, int pageSize, NutMap map) {
		
		Cnd cnd = Cnd.where("1","=","1");
		
		 Map<String, String> prods =  getProduceName();
		
		if(!Lang.isEmpty(map)) {
			if(!Lang.isEmpty(map.getString("sys"))) {
				cnd.and("name","LIKE","%" + map.getString("sys") + "%");
			}
			
			if(!Lang.isEmpty(map.getString("startDate"))) {
				cnd.and("left(create_date,10)",">=", map.getString("startDate"));
			}
			
			if(!Lang.isEmpty(map.getString("endDate"))) {
				cnd.and("left(create_date,10)","<=", map.getString("endDate"));
			}
			
		}
        cnd.desc("createDate").desc("id");
        Pager pager = dao().createPager(pageNo, pageSize);
        List<SystemModel> list = dao().query(SystemModel.class, cnd, pager);
        if(!Lang.isEmpty(list)){
        	int k=(pageNo-1)*pageSize;
        	for(int i=0; i<list.size(); i++){
        		k++;   
        		list.get(i).setSerialNumber((long) (k));
        		list.get(i).setProdueName(prods.get(""+list.get(i).getProduceid()));
        	}
        }else{
        	pager.setPageNumber(0);
        }
        
        pager.setRecordCount(dao().count(SystemModel.class,cnd));
        
        return new QueryResult(list, pager);
	}
	
	
	public String checkUpdateFirmware(String modelIds, String version, Long firmId){
		modelIds = modelIds.substring(0, modelIds.length()-1);
		String[] str = modelIds.split(",");
		String massage = "";
		
		List<FirmWareModel> oldfirmmod = this.fetchFirmwareModList(firmId);
		Firmware firmware = this.fetchFirmware(firmId);
		for(String s : str){
			
			boolean flag = false;
			for(FirmWareModel oldmod : oldfirmmod){
				if(s.equals(oldmod.getFirmModelId().toString()) && version.equals(firmware.getFirmVersion())){
					flag = true;
				}
			}
			
			if(flag)continue;
			
			Cnd c = Cnd.where("1","=","1");
	        c.and("state", "=", 1);
	        c.and("firmModelId", "=", Long.parseLong(s));
	        
	        List<FirmWareModel> firmodList = dao().query(FirmWareModel.class, c);
	        for(FirmWareModel firModel : firmodList){
	        	Firmware fir = dao().fetch(Firmware.class, Cnd.where("id","=",firModel.getFirmwareId()));
	        	if(fir.getDeleted()==0 && fir.getFirmVersion().equals(version.trim())){
	        		FirmModel m = dao().fetch(FirmModel.class, Cnd.where("id", "=", firModel.getFirmModelId()));
	        		massage += "["+ m.getName() +"]";
	        	}
	        }
		}
		return massage;
	}
	
	public String findFirmware(String modelIds, String version){
		modelIds = modelIds.substring(0, modelIds.length()-1);
		String[] str = modelIds.split(",");
		String massage = "";
		for(String s : str){
			Cnd c = Cnd.where("1","=","1");
	        c.and("state", "=", 1);
	        c.and("firmModelId", "=", Long.parseLong(s));
	        
	        List<FirmWareModel> firmodList = dao().query(FirmWareModel.class, c);
	        for(FirmWareModel firModel : firmodList){
	        	Firmware fir = dao().fetch(Firmware.class, Cnd.where("id","=",firModel.getFirmwareId()));
	        	if(fir.getDeleted()==0 && fir.getFirmVersion().equals(version.trim())){
	        		FirmModel m = dao().fetch(FirmModel.class, Cnd.where("id", "=", firModel.getFirmModelId()));
	        		massage += "["+ m.getName() +"]";
	        	}
	        }
		}
		return massage;
	}
	
	public String checkUpdateAnimation(String modelIds, Long aniId){
		modelIds = modelIds.substring(0, modelIds.length()-1);
		String[] str = modelIds.split(",");
		String massage = "";
		List<AnimationModel> oldanimod =  this.fetchAnimaModList(aniId);
		for(String s : str){
	        boolean flag = false;
	        for(AnimationModel oldani : oldanimod){
	        	if(s.equals(oldani.getSysModelId().toString())){
	        		flag = true;
	        	}
	        }
	        
	        if(flag)continue;
	        
	        Cnd c = Cnd.where("1","=","1");
	        c.and("state", "=", 1);
	        c.and("sysModelId", "=", Long.parseLong(s));
	        
	        List<AnimationModel> animodList = dao().query(AnimationModel.class, c);
	        for(AnimationModel aniModel : animodList){
	        	Animation ani = dao().fetch(Animation.class, Cnd.where("id","=",aniModel.getAnimaId()));
	        	if(ani.getDeleted()==0){
	        		SystemModel m = dao().fetch(SystemModel.class, Cnd.where("id", "=", aniModel.getSysModelId()));
	        		massage += "["+ m.getName() +"]";
	        	}
	        }
		}
		return massage;
	}
	
	public String findAnimationModel(String modelIds){
		modelIds = modelIds.substring(0, modelIds.length()-1);
		String[] str = modelIds.split(",");
		String massage = "";
		for(String s : str){
			Cnd c = Cnd.where("1","=","1");
	        c.and("state", "=", 1);
	        c.and("sysModelId", "=", Long.parseLong(s));
	        
	        List<AnimationModel> animodList = dao().query(AnimationModel.class, c);
	        for(AnimationModel aniModel : animodList){
	        	Animation ani = dao().fetch(Animation.class, Cnd.where("id","=",aniModel.getAnimaId()));
	        	if(ani.getDeleted()==0){
	        		SystemModel m = dao().fetch(SystemModel.class, Cnd.where("id", "=", aniModel.getSysModelId()));
	        		massage += "["+ m.getName() +"]";
	        	}
	        }
		}
		return massage;
	}
	
	public String checkUpdateSyspackage(String modelIds, Long type, String version, Long packId){
		
		SystemPackage pack = this.fetchSysPackage(packId);
		List<SystemPackageModel> packmodLi = this.fetchSysPackModList(packId);
		modelIds = modelIds.substring(0, modelIds.length()-1);
		String[] str = modelIds.split(",");
		String massage = "";
		for(String s : str){
			boolean flag = false;
			for(SystemPackageModel oldpackMod : packmodLi){
        		if(s.equals(oldpackMod.getSysModelId().toString()) && pack.getType().toString().equals(type.toString()) && pack.getSysVersion().equals(version)){
        			flag = true;
        		}
        	}
			
			if(flag)continue;
			
			Cnd c = Cnd.where("1","=","1");
	        c.and("state", "=", 1);
	        c.and("sysModelId", "=", Long.parseLong(s));
	        
            List<SystemPackageModel> modelList = dao().query(SystemPackageModel.class, c);
	        
	        for(SystemPackageModel packModel : modelList){
	        	SystemPackage pac = dao().fetch(SystemPackage.class, Cnd.where("id","=",packModel.getSysPackId()));
	        	if(pac.getType()==type && pac.getSysVersion().equals(version.trim()) && pac.getDeleted()==0){
	        		SystemModel m = dao().fetch(SystemModel.class, Cnd.where("id", "=", packModel.getSysModelId()));
	        		massage += "["+ m.getName() +"]";
	        	}
	        }
		}
		return massage;
	}
	
	public String findSyspackageType(String modelIds, Long type, String version){
		modelIds = modelIds.substring(0, modelIds.length()-1);
		String[] str = modelIds.split(",");
		String massage = "";
		for(String s : str){
			Cnd c = Cnd.where("1","=","1");
	        c.and("state", "=", 1);
	        c.and("sysModelId", "=", Long.parseLong(s));
	        
            List<SystemPackageModel> modelList = dao().query(SystemPackageModel.class, c);
	        
	        for(SystemPackageModel packModel : modelList){
	        	SystemPackage pac = dao().fetch(SystemPackage.class, Cnd.where("id","=",packModel.getSysPackId()));
	        	if(pac.getType()==type && pac.getSysVersion().equals(version.trim()) && pac.getDeleted()==0){
	        		SystemModel m = dao().fetch(SystemModel.class, Cnd.where("id", "=", packModel.getSysModelId()));
	        		massage += "["+ m.getName() +"]";
	        	}
	        }
		}
		return massage;
	}
	
	@Test
    public void test1(){
		
		findAllSystemModel();
		
		//System.out.println(findSyspackageType("1,4,", 1L));
		
		
    	/*File file1 = new File("D:/test1/test.zip");
    	File file2 = new File("D:/uploadFile/doudou/normal/15-3-30 12-41-5.265.zip");
    	try {
			Files.copyFile(file1, file2);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
    }

	
	public QueryResult findFirmware(int pageNo, int pageSize, NutMap map ,Long roleId,Long produceid){
		Cnd cnd = Cnd.where("deleted","=","0");
		Map<String, String> prods =  getProduceName();
		Map<String, Movdel> firmmap =  getFirm_model();
		cnd.desc("createDate").desc("id");
        Pager pager = dao().createPager(pageNo, pageSize);
        String lens ="select * from t_firmware a where a.id in(select am.firmware_id "
        		+ "from t_firm_ware_model am where am.firm_model_id in(select ssm.id from t_firm_model ssm ";
        StringBuffer sqldata = new StringBuffer();
        sqldata.append(lens+ ") and am.state="+Constants.NORMAL_STATE+")");		
        
       
        
        if(!Constants.ROLEID.equals(""+roleId)){
        	sqldata.insert(lens.length(), "  where  ssm.produce_id=@produceId)");
			
		}		
		if(!Lang.isEmpty(map)) {
			if(!Lang.isEmpty(map.getString("version"))) {
				sqldata.append("  and a.firm_version LIKE @animaName ");
			}
		}		
		
		//sqldata = sqldata + " and am.state = " +Constants.NORMAL_STATE +" order by a.id desc"; 
		
		Sql sql = Sqls.create(sqldata.toString());
		if(!Constants.ROLEID.equals(""+roleId)){				
			sql.params().set("produceId", produceid);			 
		}
		if(!Lang.isEmpty(map)) {
			if(!Lang.isEmpty(map.getString("version"))) {
				sql.params().set("animaName", map.getString("version")+"%");
			}
		}
		
		sql.setCallback(Sqls.callback.entities());
		Entity<Firmware> enity = dao().getEntity(Firmware.class);
		sql.setEntity(enity);
		dao().execute(sql);
		pager.setRecordCount(sql.getList(Firmware.class).size());	
		sql.setPager(pager);
		dao().execute(sql);
		List<Firmware> ans = sql.getList(Firmware.class);
		if(Lang.isEmpty(ans)){
			pager.setPageNumber(0);
		}		
		List<FirmwareVo> voList = new ArrayList<FirmwareVo>();
		int i =((pageNo-1)*pageSize)+1 ;
		for(Firmware sysp : ans){
			voList.add(new FirmwareVo(sysp.getId(), sysp.getFirmVersion(), sysp.getFileId(), sysp.getFileName(), sysp.getCreateDate(), 
					getFirmwareName(sysp.getId()), (long)i,
					getStrName(firmmap, sysp.getId(), prods)));
			i += 1 ;
			
		}

        return new QueryResult(voList, pager);
	}	
	
	
	
public QueryResult findPagePack(int pageNo, int pageSize, NutMap map ,Long roleId,Long produceid){
		Cnd cnd = Cnd.where("deleted","=","0");
		Pager pager = dao().createPager(pageNo, pageSize);
		Map<String, Movdel> syspachmap =  getSystemPachageMap();
		Map<String, String> prods =  getProduceName();
		String lens = "select * from t_system_package a where a.id in  "
				+ "(select am.sys_pack_id from t_system_package_model am where am.sys_model_id in"
				+ " (select ssm.id from t_system_model ssm " ;
		
		StringBuffer sqldata =new StringBuffer();		
		sqldata.append(lens+ ") and am.state="+Constants.NORMAL_STATE+")");		
		if(!Constants.ROLEID.equals(""+roleId)){
			//sqldata = sqldata.substring(0, sqldata.length()-2) + "  where  ssm.produce_id=@produceId)";
			sqldata.insert(lens.length(), "where  ssm.produce_id=@produceId");
		}		
		if(!Lang.isEmpty(map)) {
			if(!Lang.isEmpty(map.getString("version"))) {
				//sqldata = sqldata +"  and a.sys_version LIKE @animaName " ;
				sqldata.append("  and a.sys_version LIKE @animaName ");
			}
		}	
		sqldata.append(" order by a.id desc");
		
		Sql sql = Sqls.create(sqldata.toString());
		if(!Constants.ROLEID.equals(""+roleId)){				
			sql.params().set("produceId", produceid);			
		}
		if(!Lang.isEmpty(map)) {
			if(!Lang.isEmpty(map.getString("version"))) {
				sql.params().set("animaName", map.getString("version")+"%");
			}
		}
		
		sql.setCallback(Sqls.callback.entities());
		Entity<SystemPackage> enity = dao().getEntity(SystemPackage.class);
		sql.setEntity(enity);		
		dao().execute(sql);
		pager.setRecordCount(sql.getList(SystemPackage.class).size());		
		sql.setPager(pager); 
		dao().execute(sql);
		List<SystemPackage> ans = sql.getList(SystemPackage.class);
		if(Lang.isEmpty(ans)){
			pager.setPageNumber(0);
		}
		List<SystemPackageVo> volist = new ArrayList<SystemPackageVo>();
		int i =((pageNo-1)*pageSize)+1 ;
		for(SystemPackage sysp : ans){
			System.out.println(sysp.getId());
			volist.add(new SystemPackageVo(sysp.getId(), sysp.getSysVersion(), sysp.getType(), sysp.getFileId(),
					sysp.getFileName(), sysp.getCreateDate(), getSystemModelName(sysp.getId()), (long)i,
					getStrName(syspachmap, sysp.getId(), prods)));
					i += 1 ;
			
		}        		

        return new QueryResult(volist, pager);
        
	}

public String getStrName(Map<String, Movdel> syspachmap , Long id,Map<String, String> prods){
	System.out.println(id);
	String name ="";
	Movdel mod = syspachmap.get(""+id);
	if(null != mod){
		Long p_id = mod.getProduce_id();
		name = prods.get(""+p_id);
	}
	return name;
}


public QueryResult showAppPack2(int pageNo, int pageSize, NutMap map ,Long roleId,Long produceid){
	
	Cnd cnd = Cnd.where("deleted","=","0");
	Pager pager = dao().createPager(pageNo, pageSize);
	Map<String, Movdel> mods =  getSystemAppMap();
	Map<String, String> prods =  getProduceName();
	String lens = "select * from t_app a where a.id in(select asy.app_id from t_app_system asy where asy.sys_model_id in(select am.id from t_system_model am ";
	
	StringBuffer sqldata =new StringBuffer();		
	sqldata.append(lens+ ") and asy.state="+Constants.NORMAL_STATE+")");		

	if(!Constants.ROLEID.equals(""+roleId)){
		sqldata.insert(lens.length(), "  where  am.produce_id=@produceId ");
	}		
	if(!Lang.isEmpty(map)) {
		if(!Lang.isEmpty(map.getString("appName"))) {
			sqldata.append("  and a.app_name LIKE @animaName ");
		}
		if(!Lang.isEmpty(map.getString("appClassify"))) {
			sqldata.append("  and a.app_classify = @classify ");
		}
	}			
	sqldata.append(" order by a.id desc");
	
	Sql sql = Sqls.create(sqldata.toString());
	if(!Constants.ROLEID.equals(""+roleId)){				
		sql.params().set("produceId", produceid);			
	}
	if(!Lang.isEmpty(map)) {
		if(!Lang.isEmpty(map.getString("appName"))) {
			sql.params().set("animaName", map.getString("appName")+"%");
		}
		if(!Lang.isEmpty(map.getString("appClassify"))) {
			sql.params().set("classify", map.getString("appClassify"));
		}
	}
	sql.setCallback(Sqls.callback.entities());
	Entity<App> enity = dao().getEntity(App.class);
	sql.setEntity(enity);
	//sql.setPager(pager); 
	dao().execute(sql);
	pager.setRecordCount(sql.getList(App.class).size());	
	sql.setPager(pager); 
	dao().execute(sql);
	List<App> ans = sql.getList(App.class);
	if(Lang.isEmpty(ans)){
		pager.setPageNumber(0);
	}
	List<AppVo> volist = new ArrayList<AppVo>();
	int i =((pageNo-1)*pageSize)+1 ;
	for(App an : ans){
		Cnd cn = Cnd.where("appId", "=", an.getId());
        cn.and("state", "=", Constants.NORMAL_STATE);
		List<AppPicture> appPicList = dao().query(AppPicture.class, cn);
        Long picFileId = null;
        if(!Lang.isEmpty(appPicList)){
        	picFileId = appPicList.get(0).getFileId();
        }
		SystemFile sysfile = dao().fetch(SystemFile.class, Cnd.where("id","=", an.getAppFileId()));	
		volist.add(new AppVo(an.getId(),an.getAppName(),an.getAppVersion(),an.getAppFileId(),picFileId,an.getAppClassify(),an.getCreateDate(),
				sysfile.getFileSize(),sysfile.getDownCount(),
				getAppName(an.getId()),(long)i,
				getStrName(mods, an.getId(), prods)
				));
		i += 1 ;
	}
	return new QueryResult(volist, pager);
}
	

	public int isSyspackager(String sysID){
		String sqldata = "select  *  from t_system_model  m where m.id in("+sysID+")";
		Sql sql = Sqls.create(sqldata);
		
		sql.setCallback(Sqls.callback.entities());
		Entity<SystemModel> enity = dao().getEntity(SystemModel.class);
		sql.setEntity(enity);
		dao().execute(sql);
		List<SystemModel> ans = sql.getList(SystemModel.class);
		if(!Lang.isEmpty(ans)){
			SystemModel md = ans.get(0);
			for(SystemModel sm : ans){
				if(md.getProduceid() != sm.getProduceid()){
					return 1 ;
				}
			}
		}
				
		
		return 0;
	}
	
public int isFirm_Model(String sysID){
		String sqldata = "select  *  from t_firm_model  m where m.id in("+sysID+")";
		Sql sql = Sqls.create(sqldata);
		
		sql.setCallback(Sqls.callback.entities());
		Entity<FirmModel> enity = dao().getEntity(FirmModel.class);
		sql.setEntity(enity);
		dao().execute(sql);
		List<FirmModel> ans = sql.getList(FirmModel.class);
		if(!Lang.isEmpty(ans)){
			FirmModel md = ans.get(0);
			for(FirmModel sm : ans){
				if(md.getProduceid() != sm.getProduceid()){
					return 1 ;
				}
			}
		}				
		
		return 0;
	}

	
	public QueryResult findAnimation(int pageNo, int pageSize, NutMap map ,Long roleId,Long produceid){
		Map<String, String> prods =  getProduceName();
		Map<String, Movdel> mods = getSystemModelMap();
		Cnd cnd = Cnd.where("deleted","=","0");
		Pager pager = dao().createPager(pageNo, pageSize);
		//c.and("state", "=", Constants.NORMAL_STATE);
		String lens = "select * from t_animation a where a.id in(select am.anima_id  "
				+ "from t_animation_model am where am.sys_model_id in(select ssm.id from t_system_model ssm ";
		
		StringBuffer sqldata = new StringBuffer();
		sqldata.append(lens+ ") and am.state="+Constants.NORMAL_STATE+")");		
		
		if(!Constants.ROLEID.equals(""+roleId)){
			//sqldata = sqldata.substring(0, sqldata.length()-1) + "  where  ssm.produce_id=@produceId)";
			sqldata.insert(lens.length(), "  where  ssm.produce_id=@produceId");
		}		
		if(!Lang.isEmpty(map)) {
			if(!Lang.isEmpty(map.getString("name"))) {
				sqldata.append("  and a.name LIKE @animaName ");
			}
		}		
		
		sqldata.append(" order by a.id desc");
		
		Sql sql = Sqls.create(sqldata.toString());
		if(!Constants.ROLEID.equals(""+roleId)){				
			sql.params().set("produceId", produceid);			
		}
		if(!Lang.isEmpty(map)) {
			if(!Lang.isEmpty(map.getString("name"))) {
				sql.params().set("animaName", map.getString("name")+"%");
			}
		}
		sql.setCallback(Sqls.callback.entities());
		Entity<Animation> enity = dao().getEntity(Animation.class);
		sql.setEntity(enity);
		//sql.setPager(pager); 
		dao().execute(sql);
		pager.setRecordCount(sql.getList(Animation.class).size());	
		sql.setPager(pager); 
		dao().execute(sql);
		List<Animation> ans = sql.getList(Animation.class);
		if(Lang.isEmpty(ans)){
			pager.setPageNumber(0);
		}
		List<AnimationVo> volist = new ArrayList<AnimationVo>();
		int i =((pageNo-1)*pageSize)+1 ;
		for(Animation an : ans){
			volist.add(new AnimationVo(an.getId(),an.getName(), an.getFileId(), an.getFileName(), an.getCreateDate(), 
					getAnimationName(an.getId()), (long)i,
					getStrName(mods,an.getId(),prods)));
			i += 1 ;
		}
		return new QueryResult(volist, pager);
	}

	private Map<String, Movdel> getFirm_model(){		
		Map<String, Movdel> sytmap = new HashMap<String, Movdel>();
		Cnd cnd = Cnd.where("1", "=", "1");
		List<FirmModel> modlist = dao().query(FirmModel.class, cnd);
		List<FirmWareModel> modelList = dao().query(FirmWareModel.class, cnd);
		for(FirmWareModel amod : modelList){
			for(FirmModel sysm : modlist){
				if(amod.getFirmModelId() == sysm.getId() || (""+amod.getFirmModelId()).equals(""+sysm.getId())){ 
					sytmap.put(""+amod.getFirmwareId(), new Movdel(sysm.getId(), sysm.getName(), sysm.getProduceid()));
				}
			}
		}
		return sytmap ;
	}
	
	
	
	
	private Map<String, Movdel> getSystemPachageMap(){		
		Map<String, Movdel> sytmap = new HashMap<String, Movdel>();
		Cnd cnd = Cnd.where("1", "=", "1");
		List<SystemModel> modlist = dao().query(SystemModel.class, cnd);
		List<SystemPackageModel> modelList = dao().query(SystemPackageModel.class, cnd);
		for(SystemPackageModel amod : modelList){
			for(SystemModel sysm : modlist){
				if(amod.getSysModelId() == sysm.getId() || (""+amod.getSysModelId()).equals(""+sysm.getId())){ 
					sytmap.put(amod.getSysPackId()+"", new Movdel(sysm.getId(), sysm.getName(), sysm.getProduceid()));
				}
			}
		}
		return sytmap ;
	}
	
	
	private Map<String, Movdel> getSystemAppMap(){		
		Map<String, Movdel> sytmap = new HashMap<String, Movdel>();
		Cnd cnd = Cnd.where("1", "=", "1");
		List<SystemModel> modlist = dao().query(SystemModel.class, cnd);
		List<AppSystem> modelList = dao().query(AppSystem.class, cnd);
		for(AppSystem amod : modelList){
			for(SystemModel sysm : modlist){
				if(amod.getSysModelId() == sysm.getId() || (""+amod.getSysModelId()).equals(""+sysm.getId())){ 
					sytmap.put(""+amod.getAppId(), new Movdel(sysm.getId(), sysm.getName(), sysm.getProduceid()));
				}
			}
		}
		return sytmap ;
	}
	
	
	private String getAnimationName(Long id){
		String sysName ="";
		Cnd cnd = Cnd.where("1", "=", "1");
		cnd.and("anima_id", "=", id);
		List<AnimationModel> models = dao().query(AnimationModel.class, cnd);
		List<SystemModel> syssmods = dao().query(SystemModel.class, null);
		for(AnimationModel sysp :models){
			for(SystemModel sysmod : syssmods){
				if(sysp.getSysModelId() == sysmod.getId() || (""+sysp.getSysModelId()).equals(""+sysmod.getId())){
					if("".equals(sysName)){
						sysName = sysmod.getName();
					}else{
						sysName = sysName +"," +sysmod.getName();
					}
					
				}
			}
		}
		
		return sysName ;
	}
	
	private String getAppName(Long id){
		String sysName ="";
		Cnd cnd = Cnd.where("1", "=", "1");
		cnd.and("app_id", "=", id);
		List<AppSystem> models = dao().query(AppSystem.class, cnd);
		List<SystemModel> syssmods = dao().query(SystemModel.class, null);
		for(AppSystem sysp :models){
			for(SystemModel sysmod : syssmods){
				if(sysp.getSysModelId() == sysmod.getId() || (""+sysp.getSysModelId()).equals(""+sysmod.getId())){
					if("".equals(sysName)){
						sysName = sysmod.getName();
					}else{
						sysName = sysName +"," +sysmod.getName();
					}
					
				}
			}
		}
		
		return sysName ;
	}
	
	
	
	
	private String getFirmwareName(Long id){
		String sysName ="";
		Cnd cnd = Cnd.where("1", "=", "1");
		cnd.and("firmware_id", "=", id);
		List<FirmWareModel> models = dao().query(FirmWareModel.class, cnd);
		List<FirmModel> syssmods = dao().query(FirmModel.class, null);
		for(FirmWareModel sysp :models){
			for(FirmModel sysmod : syssmods){
				if(sysp.getFirmModelId() == sysmod.getId() || (""+sysp.getFirmModelId()).equals(sysmod.getId())){
					if("".equals(sysName)){
						sysName = sysmod.getName();
					}else{
						sysName = sysName +"," +sysmod.getName();
					}
					
				}
			}
		}
		
		return sysName ;
	}
	
	
	
	private String getSystemModelName(Long id){
		String sysName ="";
		Cnd cnd = Cnd.where("1", "=", "1");
		cnd.and("sys_pack_id", "=", id);
		List<SystemPackageModel> models = dao().query(SystemPackageModel.class, cnd);
		List<SystemModel> syssmods = dao().query(SystemModel.class, null);
		for(SystemPackageModel sysp :models){	
			for(SystemModel sysmod : syssmods){
				if(sysp.getSysModelId() == sysmod.getId() || (""+sysp.getSysModelId()).equals(""+sysmod.getId())){
					if("".equals(sysName)){
						sysName = sysmod.getName();
					}else{
						sysName = sysName +"," +sysmod.getName();
					}
					
				}
			}
		}
		
		return sysName ;
	}
	
	
	
	private Map<String, Movdel> getSystemModelMap(){		
		Map<String, Movdel> sytmap = new HashMap<String, Movdel>();
		Cnd cnd = Cnd.where("1", "=", "1");
		List<SystemModel> modlist = dao().query(SystemModel.class, cnd);
		List<AnimationModel> animmodel = dao().query(AnimationModel.class, cnd);
		for(AnimationModel amod : animmodel){
			for(SystemModel sysm : modlist){
				if(amod.getSysModelId() == sysm.getId() || (""+amod.getSysModelId()).equals(""+sysm.getId())){ 
					sytmap.put(""+amod.getAnimaId(), new Movdel(sysm.getId(), sysm.getName(), sysm.getProduceid()));
					
				}
			}
		}
		return sytmap ;
	}
	
	class Movdel{
		private Long systemModel_id ;
		private String systemModel_name;
		private Long produce_id ;

		public Movdel(Long systemModel_id, String systemModel_name,
				Long produce_id) {
			super();
			this.systemModel_id = systemModel_id;
			this.systemModel_name = systemModel_name;
			this.produce_id = produce_id;
		}
		public Long getSystemModel_id() {
			return systemModel_id;
		}
		public void setSystemModel_id(Long systemModel_id) {
			this.systemModel_id = systemModel_id;
		}
		public String getSystemModel_name() {
			return systemModel_name;
		}
		public void setSystemModel_name(String systemModel_name) {
			this.systemModel_name = systemModel_name;
		}
		public Long getProduce_id() {
			return produce_id;
		}
		public void setProduce_id(Long produce_id) {
			this.produce_id = produce_id;
		}
	}
	
	
}
