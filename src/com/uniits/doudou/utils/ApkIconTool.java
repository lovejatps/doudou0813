package com.uniits.doudou.utils;

import java.awt.image.BufferedImage;  
import java.io.BufferedReader;  
import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileNotFoundException;  
import java.io.FileOutputStream;  
import java.io.FileReader;  
import java.io.IOException;  
import java.io.InputStream;  
import java.io.InputStreamReader;  
import java.io.OutputStream;
import java.text.SimpleDateFormat;  
import java.util.Date;  
import java.util.Enumeration;
import java.util.HashMap;  
import java.util.Map;  
import java.util.Map.Entry;  
import java.util.Properties;
import java.util.UUID;
import java.util.zip.ZipEntry;  
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;  

import javax.servlet.http.HttpServletRequest;

import org.nutz.lang.Times;

import com.uniits.doudou.constants.SystemConfig;
  
/** 
 * @author pengjin 2013-08-01 
 *  
 *  
 * apk icon全尺寸解析
 */  
public class ApkIconTool {  
	
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
			urlStr = "F:\\linux_apktool\\";
		} else {
			// Unix, Linux ...
			shellCommand[0] = "/bin/sh";
			shellCommand[1] = "-c";
			softName = "aapt";
			urlStr = "/usr/bin/";
		}
	}
  
	 /**
		 * 
		 * @author Morton
		 * apkUrl: apk路径
		 * request: request
		 *
		 */
	public static String getApkIconPath(String apkUrl){
	    	 byte b[] = new byte [1024];
	         int length; 
	         ZipFile zipFile;
	         String  iconName = Times.format("yyyy-MM-dd hh-mm-ss", new Date());//UUID构造文件名称，确保upload中文件名称无重复
	         String path = SystemConfig.uploadPath;
	         String logoUrl= path +"/"+iconName +".png";
	         
	         //apkUrl = "/usr/local/uploadFile/QQ.apk";
	         String iconname = getIconName(apkUrl);
	         
	         System.out.println(iconname);
	         try {
	             zipFile = new ZipFile(apkUrl);
	             Enumeration enumeration = zipFile.entries();
	             ZipEntry zipEntry = null ;           
	             while (enumeration.hasMoreElements()) {
	                zipEntry = (ZipEntry) enumeration.nextElement();           
	                if (zipEntry.isDirectory()) {
	                   
	                } else {
	                    if(iconname.equalsIgnoreCase(zipEntry.getName())){
	                        OutputStream outputStream = new FileOutputStream(logoUrl);
	                        InputStream inputStream = zipFile.getInputStream(zipEntry); 
	                        while ((length = inputStream.read(b)) > 0){
	                       	 outputStream.write(b, 0, length);
	                        }
	                        break;
	                    }
	                }
	             }
	             //new File(apkUrl).delete();
	             return logoUrl;
	         } catch (IOException e) {
	        	 e.printStackTrace();
	         }
	         return null;
	    }
	
	 /**
		 * 
		 * @author Morton
		 * apkPath : apk路径
		 *  aaptPath : aapt路径
		 *
		 */
		public static String getIconName(String apkPath) {
			String apkoldName = "";
			try {
				//Runtime rt = Runtime.getRuntime(); 
				//Properties props=System.getProperties();
				//String osname = props.getProperty("os.name");
				//LOG.info("osname:", osname);
				//String order = aaptPath+"aapt" + " d badging " + apkPath + " | grep -E \"application:\"";
				//if(osname.indexOf("Windows")>-1){
				//	order = aaptPath+"aapt.exe" + " d badging \"" + apkPath + "\"";
				//}
				//LOG.info("getIconName order:", order);
				//Process proc = rt.exec(order);   /usr/src/apktool/
				
				String command = urlStr + softName
						+ " d badging \"" + apkPath + "\"";
				Process proc = Runtime.getRuntime().exec(
						new String[] { shellCommand[0], shellCommand[1], command });
				InputStream stderr = proc.getInputStream();
				InputStreamReader isr = new InputStreamReader(stderr);
				BufferedReader br = new BufferedReader(isr);
				String line = null;
				while ((line = br.readLine()) != null) {
					if (line.contains("application:")) {
						apkoldName = line.substring(line.lastIndexOf("='") + 2,
								line.lastIndexOf("'")).trim().toLowerCase();
						break;
					}
				}
			} catch (Throwable t) {
				t.printStackTrace();
			}
			return apkoldName;
		}
		
		
		public static void main(String[] args) {
			//System.out.println(getIconName("F:\\QQ.apk"));
			//System.out.println(getApkIconPath("F:\\QQ.apk", request));
		}
}  

