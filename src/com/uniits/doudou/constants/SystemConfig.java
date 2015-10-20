/**
 * 
 */
package com.uniits.doudou.constants;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.nutz.lang.Lang;
import org.nutz.log.Log;
import org.nutz.log.Logs;

/**
 * @author lcw
 * 
 */
public class SystemConfig {

    public final static String uploadPathTemp;

    public final static String uploadPath;

    // 配置文件
    private static Properties prop = new Properties();
    
    private static final Log log = Logs.get();

    static {

        try {
            prop.load(SystemConfig.class.getClassLoader().getResourceAsStream("system.properties"));
        }
        catch (IOException e) {
            log.error("系统配置文件读取错误！！！" + e.getMessage());
            e.printStackTrace();
        }
        if (Lang.isWin()) {
            uploadPathTemp = prop.getProperty("winFileUploadPathTemp");
            uploadPath = prop.getProperty("winFileUploadPath");
        } else {
            uploadPathTemp = prop.getProperty("linuxFileUploadPathTemp");
            uploadPath = prop.getProperty("linuxFileUploadPath");
        }
        
        File f = new File(uploadPath);
        if(Lang.isEmpty(uploadPath) && !f.exists()) {
            f.mkdirs();
        }
        
        f = new File(uploadPathTemp);
        if(Lang.isEmpty(uploadPathTemp) && !f.exists()) {
            f.mkdirs();
        }
        
    }

}
