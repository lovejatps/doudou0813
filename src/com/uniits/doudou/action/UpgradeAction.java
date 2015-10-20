/**
 * @description doudou com.uniits.doudou.action
 */
package com.uniits.doudou.action;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.nutz.dao.QueryResult;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Files;
import org.nutz.lang.Lang;
import org.nutz.lang.Times;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.upload.TempFile;
import org.nutz.mvc.upload.UploadAdaptor;

import com.uniits.doudou.model.Animation;
import com.uniits.doudou.model.App;
import com.uniits.doudou.model.FirmModel;
import com.uniits.doudou.model.Firmware;
import com.uniits.doudou.model.Producecar;
import com.uniits.doudou.model.SystemModel;
import com.uniits.doudou.model.SystemPackage;
import com.uniits.doudou.service.RegistService;
import com.uniits.doudou.service.RightsService;
import com.uniits.doudou.service.UpgradeService;
import com.uniits.doudou.utils.ApkIconTool;
import com.uniits.doudou.utils.CommonUtils;
import com.uniits.doudou.vo.MassageCount;
import com.uniits.doudou.vo.ModelVO;
import com.uniits.doudou.vo.SystemPackageUpdate;

/**
 * @author wangguiyong
 * @since 2015-3-19 上午10:39:53
 * @description
 */
@IocBean
@At("/upgrade")
public class UpgradeAction {
    
	@Inject
	private RightsService rigistService ; 
    
	@Inject
	private UpgradeService upgradeService;
	
	@At("/sys/list")
	@Ok("jsp:jsp/upgrade/sys/list")
	public void list() {
		
	}
	
	@At("/firm/listFirm")
	@Ok("jsp:jsp/upgrade/firm/list")
	public void listFirm() {
		
	}
	
	/**
	 * 跳转添加固件型号页面
	 */
	@At("/firm/addFirm")
	@Ok("jsp:jsp/upgrade/firm/add")
	public  List<Producecar>  addFirm() {
		return rigistService.findAll();
	}
	
	/**
	 * 添加固件型号
	 * @param 
	 * @return
	 */
	@At("/firm/doInputFirm")
	@Ok("jsp:jsp/upgrade/firm/tip")
	public String doInputFirm(@Param("::firm.") FirmModel firm) {
		firm.setCreateDate(Times.sD(new Date()));
		upgradeService.addFirm(firm);
		return "1";
	}
	
	/**
	 * 跳转添加系统型号页面
	 */
	@At("/sys/add")
	@Ok("jsp:jsp/upgrade/sys/add")
	public  List<Producecar>  add() {
		return rigistService.findAll();

	}
	
	@At("/appPack/addApp")
	@Ok("jsp:jsp/upgrade/appPack/add")
	public List<SystemModel> addApp(){
		return upgradeService.findAllSystemModel();
	}
	
	/**
	 * 跳转添加动画页面
	 * @return
	 */
	@At("/animation/addAnimation")
	@Ok("jsp:jsp/upgrade/animation/add")
	public ModelVO addAnimation() {
		return upgradeService.getModel();
		//return upgradeService.findAllSystemModel();
	}
	
	/**
	 * 跳转添加固件页面
	 * @return
	 */
	@At("/firmware/addFirmware")
	@Ok("jsp:jsp/upgrade/firmware/add")
	public List<FirmModel> addFirmware() {
		return upgradeService.findAllFirmModel();
	}
	
	/**
	 * 上传固件操作  上传文件
	 * @param modelId
	 * @param pack
	 * @param tmpFile
	 * @return
	 */
	@At("/firmware/doInputFirmware")
	@Ok("jsp:jsp/upgrade/firmware/tip")
	@AdaptBy(type = UploadAdaptor.class, args = { "${app.root}/WEB-INF/tmp" })
	public String doInputFirmware(@Param("modelId") Long[] modelId, @Param("::firm.") Firmware firm, @Param("firmFile") TempFile tmpFile) {

		String s = upgradeService.addFirmware(modelId, firm, tmpFile);
		return s;
	}
	
	/**
	 * 修改固件包
	 * @param modelId
	 * @param firm
	 * @param tmpFile
	 * @return
	 */
	@At("/firmware/doUpdateFirmware")
	@Ok("jsp:jsp/upgrade/firmware/tip")
	@AdaptBy(type = UploadAdaptor.class, args = { "${app.root}/WEB-INF/tmp" })
	public String doUpdateFirmware(@Param("modelId") Long[] modelId, @Param("::firm.") Firmware firm, @Param("firmFile") TempFile tmpFile) {

		upgradeService.updateFirmware(modelId, firm, tmpFile);
		return "update";
	}
	
	/**
	 * 跳转固件列表页面
	 */
	@At("/firmware/listFirmware")
	@Ok("jsp:jsp/upgrade/firmware/list")
	public void listFirmware() {
		
	}
	
	/**
	 * 固件分页列表   异步请求
	 * @param pageNo
	 * @param pageSize
	 * @param map
	 * @return
	 */
	@At("/firmware/showFirmware")
	@Ok("json")
	public QueryResult showFirmware(@Param("pageNo") int pageNo, @Param("pageSize") int pageSize, @Param("::queryMap.") NutMap map ,HttpSession session) {
		Long roleId = (Long)session.getAttribute("roleId");
		Long produceid =(Long) session.getAttribute("produceid");
		QueryResult queryResult = upgradeService.findFirmware(pageNo, pageSize, map,roleId,produceid);
		return queryResult;
	}
	
	/**
	 * 删除固件升级包操作
	 * @param syspackage
	 * @return
	 */
	@At("/firmware/deleteFirmware")
	@Ok("json")
	public String deleteFirmware(@Param("firmId") Long firmId){
		upgradeService.deleteFirmware(firmId);
		return "1";
	}
	
	/**
	 * 验证固件包     异步
	 * @param modelIds
	 * @param type
	 * @param version
	 * @return
	 */
	@At("/firmware/checkFirmware")
	@Ok("json")
	public String checkFirmware(@Param("modelIds") String modelIds, @Param("version") String version){
		
		return upgradeService.findFirmware(modelIds, version);
	}
	
	@At("/firmware/checkUpdateFirmware")
	@Ok("json")
	public String checkUpdateFirmware(@Param("modelIds") String modelIds, @Param("version") String version, @Param("firmId") Long firmId){
		
		return upgradeService.checkUpdateFirmware(modelIds, version, firmId);
	}
	
	
	/**
	 * 上传动画操作  上传文件
	 * @param modelId
	 * @param pack
	 * @param tmpFile
	 * @return
	 */
	@At("/animation/doInputAnimation")
	@Ok("jsp:jsp/upgrade/animation/tip")
	@AdaptBy(type = UploadAdaptor.class, args = { "${app.root}/WEB-INF/tmp" })
	public String doInputAnimation(@Param("modelId") Long[] modelId, @Param("::ani.") Animation ani, @Param("aniFile") TempFile tmpFile) {

		return upgradeService.addAnimation(modelId, ani, tmpFile);
	
	}
	
	/**
	 * 修改固件包
	 * @param modelId
	 * @param ani
	 * @param tmpFile
	 * @return
	 */
	@At("/animation/doUpdateAnimation")
	@Ok("jsp:jsp/upgrade/animation/tip")
	@AdaptBy(type = UploadAdaptor.class, args = { "${app.root}/WEB-INF/tmp" })
	public String doUpdateAnimation(@Param("modelId") Long[] modelId, @Param("::ani.") Animation ani, @Param("aniFile") TempFile tmpFile) {

		upgradeService.updateAnimation(modelId, ani, tmpFile);
		return "update";
	}
	
	/**
	 * 跳转动画列表页面
	 */
	@At("/animation/listAnimation")
	@Ok("jsp:jsp/upgrade/animation/list")
	public void listAnimation() {
		
	}
	
	/**
	 * 动画列表
	 * @param pageNo
	 * @param pageSize
	 * @param map
	 * @return
	 */
	@At("/animation/showAnimation")
	@Ok("json")
	public QueryResult showAnimation(@Param("pageNo") int pageNo,
			@Param("pageSize") int pageSize, @Param("::queryMap.") NutMap map ,HttpSession session) {
		Long roleId = (Long)session.getAttribute("roleId");
		Long produceid =(Long) session.getAttribute("produceid");
		QueryResult queryResult = upgradeService.findAnimation(pageNo, pageSize, map ,roleId,produceid);
		return queryResult;
	}
	
	/**
	 * 删除动画包操作
	 * @param syspackage
	 * @return
	 */
	@At("/animation/deleteAnimation")
	@Ok("json")
	public String deleteAnimation(@Param("aniId") Long aniId){
		upgradeService.deleteAnimation(aniId);
		return "1";
	}
	
	/**
	 * 验证动画包     异步
	 * @param modelIds
	 * @return
	 */
	@At("/animation/checkAnimation")
	@Ok("json")
	public String checkAnimation(@Param("modelIds") String modelIds){
		
		return upgradeService.findAnimationModel(modelIds);
	}
	
	/**
	 * 跳转系统包列表页面
	 */
	@At("/syspackage/listPackage")
	@Ok("jsp:jsp/upgrade/syspackage/list")
	public void listPackage() {
		
	}
	
	/**
	 * 跳转添加系统包页面
	 * @return
	 */
	@At("/syspackage/addPackage")
	@Ok("jsp:jsp/upgrade/syspackage/add")
	public ModelVO addPackage() {
		return upgradeService.getModel();
	}
	
	/**
	 * 系统包分页异步请求
	 * @param pageNo
	 * @param pageSize
	 * @param map
	 * @return
	 */
	@At("/syspackage/showPagePack")
	@Ok("json")
	public QueryResult showPagePack(@Param("pageNo") int pageNo,
			@Param("pageSize") int pageSize, @Param("::queryMap.") NutMap map ,HttpSession session) {
		Long roleId = (Long)session.getAttribute("roleId");
		Long produceid =(Long) session.getAttribute("produceid");
		QueryResult queryResult = upgradeService.findPagePack(pageNo, pageSize, map,roleId,produceid);
		return queryResult;
	}
	
	/**
	 * 添加系统包操作  上传文件
	 * @param modelId
	 * @param pack
	 * @param tmpFile
	 * @return
	 */
	@At("/syspackage/doInputSyspackage")
	@Ok("jsp:jsp/upgrade/syspackage/tip")
	@AdaptBy(type = UploadAdaptor.class, args = { "${app.root}/WEB-INF/tmp" })
	public String doInputPackage(@Param("modelId") Long[] modelId, @Param("::package.") SystemPackage pack, @Param("packFile") TempFile tmpFile) {
		pack.setSysVersion(pack.getSysVersion().toUpperCase());
		return upgradeService.addSysPackage(modelId, pack, tmpFile);
	}
	
	/**
	 * 跳转修改系统包
	 * @param id
	 * @return
	 */
	@At("/syspackage/updateSysPackage")
	@Ok("jsp:jsp/upgrade/syspackage/update")
	public SystemPackageUpdate updateSysPackage(@Param("id") Long id){
		SystemPackageUpdate packUpdate = new SystemPackageUpdate();
		packUpdate.setSysMod(upgradeService.findAllSystemModel());
		packUpdate.setSysPack(upgradeService.fetchSysPackage(id));
		packUpdate.setSysPackMod(upgradeService.fetchSysPackModList(id));
		return packUpdate;
	}
	
	/**
	 * 跳转修改动画包
	 * @param id
	 * @return
	 */
	@At("/animation/updateAnimation")
	@Ok("jsp:jsp/upgrade/animation/update")
	public SystemPackageUpdate updateAnimation(@Param("id") Long id){
		SystemPackageUpdate packUpdate = new SystemPackageUpdate();
		packUpdate.setSysMod(upgradeService.findAllSystemModel());
		packUpdate.setAnima(upgradeService.fetchAnimation(id));
		packUpdate.setAnimaMod(upgradeService.fetchAnimaModList(id));
		
		return packUpdate;
	}
	
	/**
	 * 跳转修改app包
	 * @param id
	 * @return
	 */
	@At("/appPack/updateApp")
	@Ok("jsp:jsp/upgrade/appPack/update")
	public SystemPackageUpdate updateApp(@Param("id") Long id){
		
		SystemPackageUpdate packUpdate = new SystemPackageUpdate();
		packUpdate.setSysMod(upgradeService.findAllSystemModel());
		packUpdate.setApp(upgradeService.fetchApp(id));
		packUpdate.setAppSys(upgradeService.fetchAppSystem(id));
		packUpdate.setAppPic(upgradeService.fetchAppPicture(id));
		
		return packUpdate;
	}
	
	/**
	 * 跳转修改固件包
	 * @param id
	 * @return
	 */
	@At("/firmware/updateFirmware")
	@Ok("jsp:jsp/upgrade/firmware/update")
	public SystemPackageUpdate updateFirmware(@Param("firmId") Long id){
		SystemPackageUpdate packUpdate = new SystemPackageUpdate();
		packUpdate.setFirmMod(upgradeService.findAllFirmModel());
		
		packUpdate.setFirmware(upgradeService.fetchFirmware(id));
		packUpdate.setFirmwareMod(upgradeService.fetchFirmwareModList(id));
		
		return packUpdate;
	}
	
	/**
	 * 修改固件包
	 * @param modelId
	 * @param pack
	 * @param tmpFile
	 * @return
	 */
	@At("/syspackage/doUpdateSyspackage")
	@Ok("jsp:jsp/upgrade/syspackage/tip")
	@AdaptBy(type = UploadAdaptor.class, args = { "${app.root}/WEB-INF/tmp" })
	public String doUpdatePackage(@Param("modelId") Long[] modelId, @Param("::package.") SystemPackage pack, @Param("packFile") TempFile tmpFile) {
		pack.setSysVersion(pack.getSysVersion().toUpperCase());
		upgradeService.updateSysPackage(modelId, pack, tmpFile);
		return "update";
	}
	
	
	/**
	 * 删除系统包操作
	 * @param syspackage
	 * @return
	 */
	@At("/syspackage/deleteSyspackage")
	@Ok("json")
	public String deleteSyspackage(@Param("::syspackage.") SystemPackage syspackage){
		upgradeService.deleteSyspackage(syspackage.getId());
		return "1";
	}
	
	/**
	 * 验证系统包     异步
	 * @param modelIds
	 * @param type
	 * @param version
	 * @return
	 */
	@At("/syspackage/checkSyspackage")
	@Ok("json")
	public String checkSyspackage(@Param("modelIds") String modelIds, @Param("type") Long type, @Param("version") String version){
		
		return upgradeService.findSyspackageType(modelIds, type, version);
	}
	
	/**
	 * 处理个系统升级包、APP、动画所选择的系统型号只能属于同一厂家
	 * @param modelIds
	 * @return
	 */
	@At("/syspackage/isSyspackager")
	@Ok("json")
	public int isSyspackager(@Param("modelIds") String modelIds){
		int d = upgradeService.isSyspackager(modelIds.substring(0, modelIds.length()-1));
		return d;
	}
	
	/**
	 * 处理个固件升级包选择的系统型号只能属于同一厂家
	 * @param modelIds
	 * @return
	 */
	@At("/syspackage/isfirmmodel")
	@Ok("json")
	public int isFirm_Model(@Param("modelIds") String modelIds){
		int d = upgradeService.isFirm_Model(modelIds.substring(0, modelIds.length()-1));
		return d;
	}
	
	
	@At("/syspackage/checkUpdateSyspackage")
	@Ok("json")
	public String checkUpdateSyspackage(@Param("modelIds") String modelIds, @Param("type") Long type, @Param("version") String version, @Param("packId") Long packId){
		
		return upgradeService.checkUpdateSyspackage(modelIds, type, version, packId);
	}
	
	/**
	 * 验证动画包
	 * @param modelIds
	 * @param aniId
	 * @return
	 */
	@At("/animation/checkUpdateAnimation")
	@Ok("json")
	public String checkUpdateAnimation(@Param("modelIds") String modelIds,  @Param("aniId") Long aniId){
		
		return upgradeService.checkUpdateAnimation(modelIds, aniId);
	}
	
	/**
	 * 下载文件
	 * @param fileId
	 * @param res
	 */
	@At("/doDownLoad")
	@Filters
	public void doDownLoad(@Param("fileId") Long fileId, HttpServletResponse res, HttpServletRequest req){
		upgradeService.doDownSystemPackage(fileId, res, req);
	}
	
	/**
	 * 异步上传app附件
	 * @param tmpFile
	 * @param modelIds
	 * @param version
	 * @param packname
	 * @return
	 */
	@At("/app/doUploadAppFile")
	@Ok("json")
	@AdaptBy(type = UploadAdaptor.class, args = { "${app.root}/WEB-INF/tmp" })
	public Map<String, Object> doUploadAppFile(@Param("appFile") TempFile tmpFile, @Param("modelIds") String modelIds, @Param("version") String version, @Param("packname") String packname){
		return upgradeService.doUploadAppFile(tmpFile, modelIds, version, packname);
	}
	
	/**
	 * 异步上传app图片附件
	 * @param tmpFile
	 * @return
	 */
	@At("/app/doUploadAppPicture")
	@Ok("json")
	@AdaptBy(type = UploadAdaptor.class, args = { "${app.root}/WEB-INF/tmp" })
	public Map<String, Object> doUploadAppPicture(@Param("appPicture") TempFile tmpFile){
		return upgradeService.doUploadAppPicture(tmpFile);
	}
	
	/**
	 * app包验证
	 * @param modelIds
	 * @param version
	 * @param packname
	 * @return
	 */
	@At("/app/checkAppModel")
	@Ok("json")
	public String checkAppModel(@Param("modelIds") String modelIds, @Param("version") String version, @Param("packName") String packname){
		return upgradeService.checkAppModel(modelIds, version, packname);
	}
	
	/**
	 * 添加系统型号
	 * @param sys
	 * @return
	 */
	@At("/sys/doInputSys")
	@Ok("jsp:jsp/upgrade/sys/tip")
	public String doInputSys(@Param("::sys.") SystemModel sys) {

		sys.setCreateDate(Times.sD(new Date()));
		upgradeService.addSys(sys);
		return "1";
	}
	
	/**
	 * 系统型号分页请求    异步
	 * @param pageNo
	 * @param pageSize
	 * @param map
	 * @return
	 */
	@At("/sys/showPage")
	@Ok("json")
	public QueryResult findPage(@Param("pageNo") int pageNo,
			@Param("pageSize") int pageSize, @Param("::queryMap.") NutMap map) {
		QueryResult queryResult = upgradeService.findPage(pageNo, pageSize, map);
		return queryResult;
	}
	
	@At("/firm/showFirm")
	@Ok("json")
	public QueryResult findFirm(@Param("pageNo") int pageNo,
			@Param("pageSize") int pageSize, @Param("::queryMap.") NutMap map) {
		QueryResult queryResult = upgradeService.findFirm(pageNo, pageSize, map);
		return queryResult;
	}
	
	/**
	 * 验证系统型号    异步
	 * @param name
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@At("/sys/validatorSys")
    @Ok("json")
	public int validatorSys(@Param("sys.name") String name) throws UnsupportedEncodingException {
		String sysname = URLDecoder.decode(name,"utf-8");
	    SystemModel systemModel = upgradeService.fetchSys(sysname);
	    if(systemModel != null) {
	        return 1;
	    }
        return 0;
    }
	
	/**
	 * 验证固件型号
	 * @param name
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@At("/firm/validatorFirm")
    @Ok("json")
	public int validatorFirm(@Param("firm.name") String name) throws UnsupportedEncodingException {
		String sysname = URLDecoder.decode(name,"utf-8");
	    FirmModel firmModel = upgradeService.fetchFirm(sysname);
	    if(firmModel != null) {
	        return 1;
	    }
        return 0;
    }
	
	/**
	 * 删除系统型号
	 * @param model
	 * @return
	 */
	@At("/sys/deleteSys")
	@Ok("json")
	public String deleteSys(@Param("::sys.") SystemModel model){
		
		return upgradeService.deleteSystemModel(model.getId());
	}
	
	/**
	 * 删除固件型号
	 * @param firmId
	 * @return
	 */
	@At("/firm/deleteFirm")
	@Ok("json")
	public String deleteFirm(@Param("firmId") Long firmId){
		
		return upgradeService.deleteFirmModel(firmId);
	}

	/**
	 * 上传系统型号 文件  txt 读取txt文件然后添加
	 * @param tmpFile
	 * @return
	 */
	@At("/sys/doInputSysFile")
	@Ok("jsp:jsp/upgrade/sys/tip")
	@AdaptBy(type = UploadAdaptor.class, args = { "${app.root}/WEB-INF/tmp" })
	public MassageCount doInputIdFile(@Param("sysFile") TempFile tmpFile) {
		MassageCount count = new MassageCount();
		
		String suffix = Files.getSuffixName(tmpFile.getFile()).toLowerCase();
		Long f = 0L;
		if ("txt".equalsIgnoreCase(suffix)) {
			
			List<String> snList = Files.readLines(tmpFile.getFile());
			
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
				List<SystemModel> list = new ArrayList<SystemModel>();
				for (String k : snList) {
					
					if (k == null || k == "") {
						continue;
					}
					if (CommonUtils.isContainChinese(k) || k.length() < 3
							|| k.length() > 20) {
						f++;
						continue;
					}
					
					SystemModel ss = upgradeService.fetchSys(k);
					if (Lang.isEmpty(ss)) {
						SystemModel sys = new SystemModel();
						sys.setName(k.trim());
						sys.setCreateDate(createDate);
						list.add(sys);
					}else{
						f++;
					}
				}
				if (!Lang.isEmpty(list) && list.size() > 0) {

					List<SystemModel> li = upgradeService.addSysBatch(list);
					count.setSuccessCount((long)li.size());
				}
			}
		}
		count.setErrorCount(f.longValue());
		return count;
	}
	
	/**
	 * 添加app包
	 * @param modelId
	 * @param app
	 * @param pictureFileId
	 * @return
	 */
	@At("/appPack/doInputApp")
	@Ok("jsp:jsp/upgrade/appPack/tip")
	@AdaptBy(type = UploadAdaptor.class, args = { "${app.root}/WEB-INF/tmp" })
	public String doInputApp(@Param("modelId") Long[] modelId, @Param("::app.") App app, @Param("pictureFileId") Long[] pictureFileId) {
		upgradeService.addApp(modelId, app, pictureFileId);
		return "add";
	}
	
	/**
	 * 修改app包
	 * @param modelId
	 * @param app
	 * @param pictureFileId
	 * @return
	 */
	@At("/appPack/doUpdateApp")
	@Ok("jsp:jsp/upgrade/appPack/tip")
	@AdaptBy(type = UploadAdaptor.class, args = { "${app.root}/WEB-INF/tmp" })
	public String doUpdateApp(@Param("modelId") Long[] modelId, @Param("::app.") App app, @Param("pictureFileId") Long[] pictureFileId) {
		upgradeService.updateApp(modelId, app, pictureFileId);
		return "update";
	}
	
	/**
	 * 
	 */
	@At("/appPack/listApp")
	@Ok("jsp:jsp/upgrade/appPack/list")
	public void listApp(){
		
	}
	
	/**
	 * app列表
	 * @param pageNo
	 * @param pageSize
	 * @param map
	 * @return
	 */
	@At("/appPack/showAppPack")
	@Ok("json")
	public QueryResult showAppPack(@Param("pageNo") int pageNo,
			@Param("pageSize") int pageSize, @Param("::queryMap.") NutMap map ,HttpSession session) {
		Long roleId = (Long)session.getAttribute("roleId");
		Long produceid =(Long) session.getAttribute("produceid");
		QueryResult queryResult = upgradeService.showAppPack2(pageNo, pageSize, map,roleId,produceid);
		return queryResult;
	}
	
	/**
	 * 删除app操作
	 * @param syspackage
	 * @return
	 */
	@At("/appPack/deleteApp")
	@Ok("json")
	public String deleteApp(@Param("appId") Long appId){
		upgradeService.deleteApp(appId);
		return "1";
	}
	
	/**
	 * app包详情
	 * @param id
	 * @return
	 */
	@At("/appPack/detailApp")
	@Ok("jsp:jsp/upgrade/appPack/detail")
	public SystemPackageUpdate detailApp(@Param("id") Long id){
		
		SystemPackageUpdate packUpdate = new SystemPackageUpdate();
		List<SystemModel>  modList = upgradeService.findAllSystemModel();
		String str = "";
		for(SystemModel mod : modList){
			str += mod.getName() + ",";
		}
		if(str!=""){
			str = str.substring(0, str.length()-1);
		}
		packUpdate.setAppSystemString(str);
		//packUpdate.setSysMod(upgradeService.findAllSystemModel());
		packUpdate.setApp(upgradeService.fetchApp(id));
		packUpdate.setAppSys(upgradeService.fetchAppSystem(id));
		packUpdate.setAppPic(upgradeService.fetchAppPicture(id));
		
		return packUpdate;
	}
	
	
	@At("/appPack/getApkIcon")
	@Ok("json")
	public String getApkIcon(){
		return ApkIconTool.getApkIconPath("F:\\QQ.apk");
	}
	
}
