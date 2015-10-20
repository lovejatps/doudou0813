/**
 * @description doudou com.uniits.doudou.service
 */
package com.uniits.doudou.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nutz.dao.QueryResult;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.upload.TempFile;

import com.uniits.doudou.model.Animation;
import com.uniits.doudou.model.AnimationModel;
import com.uniits.doudou.model.App;
import com.uniits.doudou.model.AppPicture;
import com.uniits.doudou.model.AppSystem;
import com.uniits.doudou.model.FirmModel;
import com.uniits.doudou.model.FirmWareModel;
import com.uniits.doudou.model.Firmware;
import com.uniits.doudou.model.SystemModel;
import com.uniits.doudou.model.SystemPackage;
import com.uniits.doudou.model.SystemPackageModel;
import com.uniits.doudou.vo.ModelVO;

/**
 * @author liuchengwei
 * @since 2015-3-17 下午2:47:37
 * @description 
 */
public interface UpgradeService {

    public void addSys(SystemModel sys);
    
    public void addFirm(FirmModel firm);
    
    public SystemModel fetchSys(String name);
    
    public FirmModel fetchFirm(String name);
    
    public SystemPackage fetchSysPackage(Long id);
    
    public Animation fetchAnimation(Long id);
    
    public App fetchApp(Long id);
    
    public Firmware fetchFirmware(Long id);
    
    public String addSysPackage(Long[] modelId, SystemPackage pack, TempFile tmpFile);
    
    public void updateSysPackage(Long[] modelId, SystemPackage pack, TempFile tmpFile);
    
    public Map<String, Object> doUploadAppFile(TempFile tempFile, String modelIds, String version, String packname);
    
    public Map<String, Object> doUploadAppPicture(TempFile tempFile);
    
    public String checkAppModel(String modelIds, String version, String packname);
    
    public String addAnimation(Long[] modelId, Animation ani, TempFile tmpFile);
    
    public void updateAnimation(Long[] modelId, Animation ani, TempFile tmpFile);
    
    public String addFirmware(Long[] modelId, Firmware firm, TempFile tmpFile);
    
    public void updateFirmware(Long[] modelId, Firmware firm, TempFile tmpFile);
    
    public void addApp(Long[] modelId, App app, Long[] pictureFileId);
    
    public void updateApp(Long[] modelId, App app, Long[] pictureFileId);
    
    public String deleteSystemModel(long id);
    
    public List<AppSystem> fetchAppSystem(Long id);
    
    public List<AppPicture> fetchAppPicture(Long id);
    
    public String deleteFirmModel(long id);
    
    public void deleteSyspackage(Long id);
    
    public void deleteAnimation(Long id);
    
    public void deleteFirmware(Long id);
    
    public void deleteApp(Long id);
    
    public String findSyspackageType(String modelIds, Long type, String version);
    
    public String checkUpdateSyspackage(String modelIds, Long type, String version, Long packId);
    
    public String checkUpdateAnimation(String modelIds, Long aniId);
    
    public String findFirmware(String modelIds, String version);
    
    public String checkUpdateFirmware(String modelIds, String version, Long firmId);
    
    public String findAnimationModel(String modelIds);
    
    public void doDownSystemPackage(Long id, HttpServletResponse res, HttpServletRequest req);
    
    public List<SystemModel> findAllSystemModel();
    
    public ModelVO getModel();
    
    public List<SystemPackageModel> fetchSysPackModList(Long id);
    
    public List<AnimationModel> fetchAnimaModList(Long id);
    
    public List<FirmWareModel> fetchFirmwareModList(Long id);
    
    public List<FirmModel> findAllFirmModel();

	public List<SystemModel> addSysBatch(List<SystemModel> list);

	public QueryResult findPage(int pageNo, int pageSize, NutMap map);
	
	public QueryResult findFirm(int pageNo, int pageSize, NutMap map);
	
	public QueryResult findPagePack(int pageNo, int pageSize, NutMap map ,Long roleId,Long produceid);
	
	public QueryResult findAnimation(int pageNo, int pageSize, NutMap map ,Long roleId,Long produceid);
	
	public QueryResult findFirmware(int pageNo, int pageSize, NutMap map  ,Long roleId,Long produceid);
	
	public QueryResult showAppPack(int pageNo, int pageSize, NutMap map);
	public QueryResult showAppPack2(int pageNo, int pageSize, NutMap map  ,Long roleId,Long produceid);
	
	public int isSyspackager(String sysID);
	public int isFirm_Model(String sysID);
    
}
