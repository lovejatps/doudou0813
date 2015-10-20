package com.uniits.doudou.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ApkIcon {

	private static String apkoldName = "";
	private static String apkPath = "F:\\QQ.apk";

	public static void getIconName() {
		try {
			Runtime rt = Runtime.getRuntime();
			String order = "F:\\QQ.apk";
			System.out.println(order);
			Process proc = rt.exec(order);
			InputStream stderr = proc.getInputStream();
			InputStreamReader isr = new InputStreamReader(stderr);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				if (line.contains("application:")) {
					apkoldName = line
							.substring(line.lastIndexOf("/") + 1,
									line.lastIndexOf("'")).trim().toLowerCase();
					break;
				}
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	public static void getIcon() {
		FileInputStream in = null;
		FileOutputStream out = null;
		ZipInputStream zipin = null;
		String apknewName = "icon.png";
		String iconpath = "F:\\" + apknewName;
		File apkFile = new File(apkPath);
		try {
			in = new FileInputStream(apkFile);
			zipin = new ZipInputStream(in);
			ZipEntry entry = null;
			while ((entry = zipin.getNextEntry()) != null) {
				String name = entry.getName().toLowerCase();
				if (name.endsWith("/" + apkoldName)
						&& name.contains("drawable") && name.contains("res")) {
					if (apkoldName.lastIndexOf(".") <= 0) {
						apknewName = "icon.jpg";
					} else {
						apknewName = "icon"
								+ apkoldName.substring(apkoldName
										.lastIndexOf("."));
					}
					iconpath = "F:\\" + apknewName;
					out = new FileOutputStream(new File(iconpath));
					byte[] buff = new byte[1024];
					int n = 0;
					while ((n = zipin.read(buff, 0, buff.length)) != -1) {
						out.write(buff, 0, n);
					}
				} else if (name.endsWith("/" + apkoldName)
						&& name.contains("raw") && name.contains("res")) {
					if (apkoldName.lastIndexOf(".") <= 0) {
						apknewName = "icon_.jpg";
					} else {
						apknewName = "icon_temp"
								+ apkoldName.substring(apkoldName
										.lastIndexOf("."));
					}
					iconpath = "F:\\" + apknewName;
					out = new FileOutputStream(new File(iconpath));
					byte[] buff = new byte[1024];
					int n = 0;
					while ((n = zipin.read(buff, 0, buff.length)) != -1) {
						out.write(buff, 0, n);
					}
				}
			}
			out = null;
			zipin.closeEntry();
			entry = null;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (zipin != null) {
					zipin.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String args[]) {
		getIconName();
		getIcon();
	}

}
