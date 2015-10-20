package com.uniits.doudou.vo;

import java.util.ArrayList;
import java.util.List;

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

public class SystemPackageUpdate {

	/**
	 * 系统类型
	 */
	private List<SystemModel> sysMod = new ArrayList<SystemModel>();
	
	//---------------------------------------------------
	/**
	 * 系统包
	 */
	private SystemPackage sysPack = new SystemPackage();
	
	private List<SystemPackageModel> sysPackMod = new ArrayList<SystemPackageModel>();
	
	//---------------------------------------------------
	/**
	 * 动画包
	 */
	private Animation anima;
	
	private List<AnimationModel> animaMod = new ArrayList<AnimationModel>();
	
	//---------------------------------------------------
	
	/**
	 * 固件类型list
	 */
	private List<FirmModel> firmMod = new ArrayList<FirmModel>();
	
	/**
	 * 固件包
	 */
	private Firmware firmware;
	
	private List<FirmWareModel> firmwareMod = new ArrayList<FirmWareModel>();
	
	//---------------------------------------------------
	
	/**
	 * App包
	 */
	private App app;
	
	private List<AppSystem> appSys = new ArrayList<AppSystem>();
	
	private List<AppPicture> appPic = new ArrayList<AppPicture>();
	
	private String appSystemString;
	
	//---------------------------------------------------
	
	public List<SystemModel> getSysMod() {
		return sysMod;
	}

	public void setSysMod(List<SystemModel> sysMod) {
		this.sysMod = sysMod;
	}

	public SystemPackage getSysPack() {
		return sysPack;
	}

	public void setSysPack(SystemPackage sysPack) {
		this.sysPack = sysPack;
	}

	public List<SystemPackageModel> getSysPackMod() {
		return sysPackMod;
	}

	public String getAppSystemString() {
		return appSystemString;
	}

	public void setAppSystemString(String appSystemString) {
		this.appSystemString = appSystemString;
	}

	public void setSysPackMod(List<SystemPackageModel> sysPackMod) {
		this.sysPackMod = sysPackMod;
	}

	public Animation getAnima() {
		return anima;
	}

	public void setAnima(Animation anima) {
		this.anima = anima;
	}

	public List<AnimationModel> getAnimaMod() {
		return animaMod;
	}

	public void setAnimaMod(List<AnimationModel> animaMod) {
		this.animaMod = animaMod;
	}

	public List<FirmModel> getFirmMod() {
		return firmMod;
	}

	public void setFirmMod(List<FirmModel> firmMod) {
		this.firmMod = firmMod;
	}

	public Firmware getFirmware() {
		return firmware;
	}

	public void setFirmware(Firmware firmware) {
		this.firmware = firmware;
	}

	public List<FirmWareModel> getFirmwareMod() {
		return firmwareMod;
	}

	public void setFirmwareMod(List<FirmWareModel> firmwareMod) {
		this.firmwareMod = firmwareMod;
	}

	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}

	public List<AppSystem> getAppSys() {
		return appSys;
	}

	public void setAppSys(List<AppSystem> appSys) {
		this.appSys = appSys;
	}

	public List<AppPicture> getAppPic() {
		return appPic;
	}

	public void setAppPic(List<AppPicture> appPic) {
		this.appPic = appPic;
	}
	
}
