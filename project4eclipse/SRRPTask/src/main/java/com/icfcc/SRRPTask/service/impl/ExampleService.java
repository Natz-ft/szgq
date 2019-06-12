/**
 * 
 */
package com.icfcc.SRRPTask.service.impl;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.icfcc.SRRPTask.service.IExampleService;

/**
 * @author lijj
 *
 */
@Component
public class ExampleService implements IExampleService {
	//@Scheduled(cron="0/5 * *  * * ? ")   //每5秒执行一次  
    @Override  
    public void myTest(){  
          System.out.println("进入测试");  
    }  
}
