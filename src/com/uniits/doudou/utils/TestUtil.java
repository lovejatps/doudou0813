package com.uniits.doudou.utils;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.apkinfo.api.GetApkInfo;
import org.apkinfo.api.domain.ApkInfo;
import org.nutz.img.Images;

public class TestUtil {
	
	public static void main(String[] args) {
		
		//String url = "http://localhost:8080/doudou/buzupdate/findNew.htm";
		//System.out.println(url.substring(0, url.substring(0,url.lastIndexOf("/")).lastIndexOf("/")+1));
		
		File file = new File("D:/测试1.png");
		BufferedImage image = Images.zoomScale(Images.read(file), 120, 120);
		String name = file.getName();
		System.out.println(name.substring(name.lastIndexOf(".")+1));
		
		System.out.println(name.substring(0,name.lastIndexOf(".")));
		
		String newName = name.substring(0,name.lastIndexOf("."))+" 120-120"+"."+name.substring(name.lastIndexOf(".")+1);
		
		File newFile = new File("D:/"+newName);
		try {
			ApkInfo apk = GetApkInfo.getApkInfoByFilePath("D:/百度贴吧.apk");
			//File tempFile = new File("D:/图片2.png");
			//System.out.println(CommonUtils.getFileSize(tempFile));
			if (apk != null) {
				System.out.println(apk.getPackageName());
				System.out.println(apk.getVersionName());
				System.out.println(apk.getVersionCode());
			}
			
			ImageIO.write(image, name.substring(name.lastIndexOf(".")+1), newFile);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
