package com.tjport.common.utils;

import com.tjport.common.utils.io.PropertiesLoader;

/**
 * 项目中用到的静态变量.
 * 
 * @author 
 * @date 
 */
public class SysConstants {
    /**
     * session 验证码key
     */
    public static final String SESSION_VALIDATE_CODE = "validateCode";
    
    private static PropertiesLoader appconfig = null;

    
    /**
     * 配置文件(appconfig.properties)
     */
    public static PropertiesLoader getAppConfig() {
    	if(appconfig == null){
    		appconfig = new PropertiesLoader("config.properties");
    	}
        return appconfig;
    }

    /**
     * 修改配置文件
     * @param key
     * @param value
     */
    public static void modifyAppConfig(String key,String value) {
        String filePath = "config.properties";
        if(appconfig == null){
            appconfig = new PropertiesLoader(filePath);
        }
        appconfig.modifyProperties(filePath,key,value);
    }
    

    /**
     * upload.basedir
     */
    public static String getUploadBaseDir(){
        return SysConstants.getAppConfig().getProperty("upload.basedir","");
    }

}
