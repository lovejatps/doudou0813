/**
 * 
 */
package com.uniits.doudou.test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import org.apache.log4j.Logger;
/**
 * @author steven
 *
 */
public class JdbcUtil {
	 //连接数据库的参数1
    private static String url = null;
    private static String user = null;
    private static String driver = null;
    private static String password = null;
    
    private static Logger log = Logger.getLogger(JdbcUtil.class);
    private JdbcUtil () {
 
    }
 
    private static JdbcUtil instance = null;
 
    public static JdbcUtil getInstance() {
        if (instance == null) {
            synchronized (JdbcUtil.class) {
                if (instance == null) {
                    instance = new JdbcUtil();
                }
 
            }
        }
 
        return instance;
    }
     
    //配置文件
    private static Properties prop = new Properties();
     
    //注册驱动
    static {
        try {
            //利用类加载器读取配置文件
            InputStream is = JdbcUtil.class.getClassLoader().getResourceAsStream("jdbc.properties");
            prop.load(is);
            driver = prop.getProperty("jdbc.driver");
            url = prop.getProperty("jdbc.url");
            user = prop.getProperty("jdbc.username");
            password = prop.getProperty("jdbc.password");
            Class.forName(driver);
            log.info("JdbcUtil==>(static)-注册驱动开始：");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            log.error("JdbcUtil==>(static)-注册驱动出问题：", e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
     
    //该方法获得连接
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
    

    /** 
     * 连接数据 
     * 
     * @return conn 
     */  
    public boolean testConnection(String driver, String url, String username, String pwd) {  
        Connection conn = null;  
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, pwd);
            if (conn == null)
                return false;
        }
        catch (Exception e) {
            log.error("SqlUtil==>【testConnection()】-测试连接失败：", e);
            e.printStackTrace();
            return false;
        }
        finally {
            if(conn != null) free(conn);
        }
        return true;  
    } 
    
    /**
     * 释放资源
     * @param conn
     * @param st
     * @param rs
     */
    public void free(Connection conn, Statement st, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("JdbcUtil==>(free)-关闭ResultSet：", e);
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                	 log.error("JdbcUtil==>(free)-关闭Statement：", e);
                    e.printStackTrace();
                } finally {
                    free(conn);
                }
            }
        }
    }
    
    /**
     * 释放资源
     * @param conn
     */
    public void free(Connection conn){
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                log.error("JdbcUtil==>(free)-关闭Connection：", e);
            }
        }
    }
    
    /**
     * 释放资源
     * @param conn
     */
    public void free(Statement conn){
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                log.error("JdbcUtil==>(free)-关闭ResultSet：", e);
            }
        }
    }
    
    /**
     * 释放资源
     * @param conn
     */
    public void free(ResultSet conn){
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                log.error("JdbcUtil==>(free)-关闭ResultSet：", e);
            }
        }
    }



    public static String getUrl() {
        return url;
    }

    public static String getUser() {
        return user;
    }


    public static String getDriver() {
        return driver;
    }


    public static String getPassword() {
        return password;
    }

}
