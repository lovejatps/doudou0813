package com.uniits.doudou.utils;

import java.io.File;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CommonUtils {

	
	/**
	 * 判断对象是否为空对象或空字符串，如果是则返回 True.
	 * 
	 * @param msg
	 *            对象
	 * @return boolean 对象为空则为True.
	 */
	public static boolean isNull(Object msg) {
		return (msg != null && !msg.equals("")) ? false : true;
	}
	
	
	/**
	 * 将传入的数字保留两位小数, 四舍五入.
	 * @param objectValue
	 *            需要转换的数字 float或double类型
	 * @return Object 数字对象 float或double
	 */
	public static Float subNumThreeRounding(Object objectValue) {
		Object resultObject = null;
		if (!CommonUtils.isNull(objectValue)) {
			BigDecimal bd = null;
			if (objectValue instanceof Float) {
				float floatValue = (Float) objectValue;
				bd = new BigDecimal(String.valueOf(floatValue));
				bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
				resultObject = bd.floatValue();
			} else if (objectValue instanceof Double) {
				double doubleValue = (Double) objectValue;
				bd = new BigDecimal(String.valueOf(doubleValue));
				bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
				resultObject = bd.doubleValue();
			} else {
				resultObject = objectValue;
			}
		}
		return Float.parseFloat(String.valueOf(resultObject));
	}
	
	/**
	 * 格式大小转换
	 * @param file
	 * @return
	 */
	public static String getFileSize(File file){
		
		Long size = file.length();
		
		String fileSize = "";
		
		float KBsize = size / 1024;
		
		if(KBsize >= 1024){
			float Msize = KBsize / 1024;
			if(Msize >= 1024){
				float Gsize = Msize / 1024;
				fileSize = CommonUtils.subNumThreeRounding(Gsize)+"GB";
			}else{
				fileSize = CommonUtils.subNumThreeRounding(Msize)+"MB";
			}
		}else{
			fileSize = CommonUtils.subNumThreeRounding(KBsize)+"KB";
		}
		
		return fileSize;
		
	}
	
	/**
	 * 字符串是否存在汉字
	 * @param str
	 * @return
	 */
	public static boolean isContainChinese(String str) {

        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }
	
}
