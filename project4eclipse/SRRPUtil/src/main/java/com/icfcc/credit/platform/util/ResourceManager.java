package com.icfcc.credit.platform.util;


import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 项目里用到的各配置参数manager
 * @version 1.0.0   2015-02-09
 * @author dingyahui
 *
 */
public class ResourceManager {
    
    private Log logger = LogFactory.getLog(ResourceManager.class);
    
    private static Properties prop = null;
    String log = "Not found resource file resource.properties in classpath!";
    // siglton的实例

    private static ResourceManager rm = null;

    // 通过当前线程获得resource实例
    private ResourceManager() {
        prop = new Properties();
        InputStream is = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream("config.properties");
        if (null == is) {
            logger.info(log);
        } else {
            try {
                prop.load(is);
                // LoggerAgent.info("Load resource.properties successfully!");
            } catch (IOException ex) {
                log = "IOException" + ex;
                logger.error( log);
            }
        }
        
    }
    // 控制resourcemanager只有一个实例对象
    public static ResourceManager getInstance() {
        if (null == rm)
            rm = new ResourceManager();
        return rm;
    }

    // 通过key获得数据，转码
    public String getValue(String key) {
        String message = prop.getProperty(key);
        if(message==null||message.length()<=0){
            return "";
        }
            
        message = message.trim();
        String transferMessage = null;
        try {
            transferMessage = new String(message.getBytes("ISO-8859-1"), "GBK");

        } catch (UnsupportedEncodingException e) {
            log = "not supported encoding!!!!!";
            logger.error(log);
        }
        return transferMessage;
    }

    // 通过key获得数据,是否转码
    public String getValue(String key, boolean isTrans) {
        if (isTrans) {
            return this.getValue(key);
        }
        
        String message = prop.getProperty(key);
        if(message==null||message.length()<=0){
           return "";
        }
        return message.trim();
    }
    public static void main(String[] args) {
    	 int  maximumPoolSize = ResourceManager.getInstance().getIntValue("maximumPoolSize");
    	 System.out.println("maximumPoolSize"+maximumPoolSize);
    }
    public  int getIntValue(String key) {
        String value = null;
        value = prop.getProperty(key);
        int retValue = -100000;

        if (null != value) {
            value = value.trim();
            if (value != null && value.length() > 0) {
                try {
                    retValue = Integer.parseInt(value);
                } catch (NumberFormatException ex) {
                    log = "NumberFormatException" + ex;
                    logger.error(log);
                }
            }
        }
        return retValue;
    }

    public long getLongValue(String key) {
        String value = null;
        value = prop.getProperty(key);
        long retValue = -10000;
        if (null != value) {
            value = value.trim();
            if (value != null && value.length() > 0) {
                try {
                    retValue = Long.parseLong(value);
                } catch (NumberFormatException ex) {
                    log = "NumberFormatException" + ex;
                    logger.error(log);
                }
            }
        }

        return retValue;
    }

}
