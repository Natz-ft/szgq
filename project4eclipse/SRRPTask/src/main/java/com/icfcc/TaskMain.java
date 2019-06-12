/**
 * 
 */
package com.icfcc;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author lijj
 *
 */
public class TaskMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext*.xml");//此文件放在SRC目录下
	}

}
