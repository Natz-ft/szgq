package com.icfcc.srrptask.util;

import java.io.File;
import java.net.URL;

import org.quartz.Scheduler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class ReadApplicationContext {
	public static ApplicationContext context = null;
	public static Scheduler sche;
	private static String basePath = "";
	public static ApplicationContext getContext(){
		 try {    
	            if(context != null){//懒汉式   
	                  
	            }else{  
	                //创建实例之前可能会有一些准备性的耗时工作   
	                Thread.sleep(300);  
	                synchronized (ReadApplicationContext.class) {  
	                    if(context == null){//二次检查  
	                    	URL path = ResourceLoader.getResource("");
	                	    System.out.println("path================================"+path.getPath());
	                	    File f = new File(path.getPath() );
//	                	    File f = new File(path.getPath() + "/conf");
	                	    if (f.exists())
	                	      basePath = path.getPath();
	                	    else {
	                	      basePath = path.getPath() + "..";
	                	    }
//	                	    String pathS = "/" + basePath + "/conf" + "/applicationContext*.xml";
	                	    String pathS = "/" + basePath + "/applicationContext*.xml";
//	                        System.out.println("pathS=================="+pathS);
	                    	context= new FileSystemXmlApplicationContext(pathS);
	                    }  
	                }  
	            }   
	        } catch (InterruptedException e) {   
	            e.printStackTrace();  
	        }  
		return context;
		
	}
	public static Scheduler getScheduler(){
		 try {    
	            if(sche != null){//懒汉式   
	                  
	            }else{  
	                //创建实例之前可能会有一些准备性的耗时工作   
	                Thread.sleep(300);  
	                synchronized (ReadApplicationContext.class) {  
	                    if(sche == null){//二次检查  
	                    	 sche = (Scheduler) getContext().getBean( "schedulerFactoryBean");
	                    }  
	                }  
	            }   
	        } catch (InterruptedException e) {   
	            e.printStackTrace();  
	        }  
		return sche;
		
	}
}
