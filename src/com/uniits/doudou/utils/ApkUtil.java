/*
 * @(#)ApkUtil.java		       version: 0.2.1 
 * Date:2012-1-9
 *
 * Copyright (c) 2011 CFuture09, Institute of Software, 
 * Guangdong Ocean University, Zhanjiang, GuangDong, China.
 * All rights reserved.
 */
package com.uniits.doudou.utils;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.uniits.doudou.model.ApkInfo;

/**
 * apk工具类。封装了获取Apk信息的方法。
 * 
 * @author CFuture.Geek_Soledad(66704238@51uc.com)
 * 
 *         <p>
 *         <b>version description</b><br />
 *         V0.2.1 修改程序名字为从路径中获得。
 *         </p>
 */
public class ApkUtil {
	public static final String VERSION_CODE = "versionCode";
	public static final String VERSION_NAME = "versionName";
	public static final String SDK_VERSION = "sdkVersion";
	public static final String TARGET_SDK_VERSION = "targetSdkVersion";
	public static final String USES_PERMISSION = "uses-permission";
	public static final String APPLICATION_LABEL = "application-label";
	public static final String APPLICATIONLABEL = "application: label";
	public static final String APPLICATION_LABEL_M = "application-label:";
	
	public static final String APPLICATION_LABEL_ZH_CN = "application-label-zh_CN";
	
	public static final String APPLICATION_LABEL_ZH = "application-label-zh:";
	
	public static final String APPLICATION_ICON = "application-icon";
	public static final String USES_FEATURE = "uses-feature";
	public static final String USES_IMPLIED_FEATURE = "uses-implied-feature";
	public static final String SUPPORTS_SCREENS = "supports-screens";
	public static final String SUPPORTS_ANY_DENSITY = "supports-any-density";
	public static final String DENSITIES = "densities";
	public static final String PACKAGE = "package";
	public static final String APPLICATION = "application:";
	public static final String LAUNCHABLE_ACTIVITY = "launchable-activity";

	private ProcessBuilder mBuilder;
	private static final String SPLIT_REGEX = "(: )|(=')|(' )|'";
	
	static String[] shellCommand;
	static String softName = "";
	static String urlStr = "";
	static {
		shellCommand = new String[2];
		final String anOSName = System.getProperty("os.name");
		if (anOSName.toLowerCase().startsWith("windows")) {
			// Windows XP, Vista ...
			shellCommand[0] = "cmd";
			shellCommand[1] = "/C";
			softName = "aapt.exe";
			//urlStr = "F:\\linux_apktool\\";
			urlStr = "C:\\Users\\hxn\\Downloads\\";
		} else {
			// Unix, Linux ...
			shellCommand[0] = "/bin/sh";
			shellCommand[1] = "-c";
			softName = "aapt";
			urlStr = "/usr/bin/";
		}
	}
	
	
	/**
	 * aapt所在的目录。
	 */
	//private String mAaptPath = "/usr/bin/aapt";
	//private String mAaptPath = "F:\\linux_apktool\\aapt.exe";
	

	public ApkUtil() {
		mBuilder = new ProcessBuilder();
		mBuilder.redirectErrorStream(true);
	}

	/**
	 * 返回一个apk程序的信息。
	 * 
	 * @param apkPath
	 *            apk的路径。
	 * @return apkInfo 一个Apk的信息。
	 */
	public ApkInfo getApkInfo(String apkPath) throws Exception {
		//Process process = mBuilder.command(mAaptPath, "d", "badging", apkPath).start();
		
		String command = urlStr + softName
				+ " d badging \"" + apkPath + "\"";
		System.out.println(command);
		Process process = Runtime.getRuntime().exec(
				new String[] { shellCommand[0], shellCommand[1], command });
		InputStream is = null;
		is = process.getInputStream();
		BufferedReader br = new BufferedReader(
				new InputStreamReader(is, "UTF-8"));
		String tmp = br.readLine();
		try {
			if (tmp == null || !tmp.startsWith("package")) {
				throw new Exception("参数不正确，无法正常解析APK包。输出结果为:\n" + tmp + "...");
			}
			ApkInfo apkInfo = new ApkInfo();
			do {
				setApkInfoProperty(apkInfo, tmp);
			} while ((tmp = br.readLine()) != null);
			return apkInfo;
		} catch (Exception e) {
			throw e;
		} finally {
			process.destroy();
			closeIO(is);
			closeIO(br);
		}
	}

	/**
	 * 设置APK的属性信息。
	 * 
	 * @param apkInfo
	 * @param source
	 */
	public void setApkInfoProperty(ApkInfo apkInfo, String source) {
		System.out.println(source);
		if (source.startsWith(PACKAGE)) {
			splitPackageInfo(apkInfo, source);
		} else if(source.startsWith(LAUNCHABLE_ACTIVITY)){
			apkInfo.setLaunchableActivity(getPropertyInQuote(source));
		} else if (source.startsWith(SDK_VERSION)) {
			apkInfo.setSdkVersion(getPropertyInQuote(source));
		} else if (source.startsWith(TARGET_SDK_VERSION)) {
			apkInfo.setTargetSdkVersion(getPropertyInQuote(source));
		} else if (source.startsWith(USES_PERMISSION)) {
			apkInfo.addToUsesPermissions(getPropertyInQuote(source));
		} else if (source.startsWith(APPLICATION_LABEL)) {
			if(source.startsWith(APPLICATION_LABEL_ZH_CN)){
				apkInfo.setApplicationLable(getPropertyInQuote(source));
			}
			if(source.startsWith(APPLICATION_LABEL_ZH)){
				apkInfo.setApplicationLable(getPropertyInQuote(source));
			}
			if(apkInfo.getApplicationLable()==null || apkInfo.getApplicationLable()==""){
				if(source.startsWith(APPLICATION_LABEL_M)){
					apkInfo.setApplicationLable(getPropertyInQuote(source));
				}
			}
		} else if(source.startsWith(APPLICATIONLABEL)){
			apkInfo.setApplicationLable(getPropertyInQuote(source));		
		}else if (source.startsWith(APPLICATION_ICON)) {
			apkInfo.addToApplicationIcons(getKeyBeforeColon(source),
					getPropertyInQuote(source));
		} else if (source.startsWith(APPLICATION)) {
			String[] rs = source.split("( icon=')|'");
			apkInfo.setApplicationIcon(rs[rs.length - 1]);
		} else if (source.startsWith(USES_FEATURE)) {
			apkInfo.addToFeatures(getPropertyInQuote(source));
		} else {
//			System.out.println(source);
		}
	}



	/**
	 * 返回出格式为name: 'value'中的value内容。
	 * 
	 * @param source
	 * @return
	 */
	private String getPropertyInQuote(String source) {
		int index = source.indexOf("'") + 1;
		return source.substring(index, source.indexOf('\'', index));
	}

	/**
	 * 返回冒号前的属性名称
	 * 
	 * @param source
	 * @return
	 */
	private String getKeyBeforeColon(String source) {
		return source.substring(0, source.indexOf(':'));
	}

	/**
	 * 分离出包名、版本等信息。
	 * 
	 * @param apkInfo
	 * @param packageSource
	 */
	private void splitPackageInfo(ApkInfo apkInfo, String packageSource) {
		String[] packageInfo = packageSource.split(SPLIT_REGEX);
		apkInfo.setPackageName(packageInfo[2]);
		apkInfo.setVersionCode(packageInfo[4]);
		apkInfo.setVersionName(packageInfo[6]);
	}

	/**
	 * 释放资源。
	 * 
	 * @param c
	 *            将关闭的资源
	 */
	private final void closeIO(Closeable c) {
		if (c != null) {
			try {
				c.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

/*	public String getmAaptPath() {
		return mAaptPath;
	}

	public void setmAaptPath(String mAaptPath) {
		this.mAaptPath = mAaptPath;
	}*/
}
