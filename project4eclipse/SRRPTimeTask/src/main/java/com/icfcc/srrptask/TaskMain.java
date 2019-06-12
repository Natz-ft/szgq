/**
 * 
 */
package com.icfcc.srrptask;

import com.icfcc.srrptask.util.ReadApplicationContext;

/**
 * @author lijj
 *
 */

public  class TaskMain {
	
	
	
	/**
	 * @param args
	 */
	
	public static void main(String[] args) {
		System.out.println("开始执行调度");
		//启动调度器  开始定时内务
		ReadApplicationContext.getContext();
//		 SpringContext.me();
		
        
	}

}
