package com.icfcc.credit.platform.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilterProperties {
	
	private static Logger logger = LoggerFactory.getLogger(FilterProperties.class);
	private static String projectName;

	public static  String  getProjectName(){  
        Properties p = new Properties();  
        try {  
            InputStream in = FilterProperties.class.getResourceAsStream("/configfilter.properties");
            p.load(in);  
            in.close();  
            if(p.containsKey("project.name")){  
            	FilterProperties.projectName = p.getProperty("project.name");  
            }  
             
        } catch (IOException ex) {  
        	logger.error("读取config.properties配置文件出错!");
        }  
        return projectName;  
    }  
}
