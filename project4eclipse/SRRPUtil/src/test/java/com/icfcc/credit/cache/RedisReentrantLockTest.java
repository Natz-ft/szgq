/**
 *  Copyright (c)  2011-2020 ICFCC, Inc.
 *  All rights reserved.
 *
 *  This software is the confidential and proprietary information of ICFCC, 
 *  Inc. ("Confidential Information"). You shall not
 *  disclose such Confidential Information and shall use it only in
 *  accordance with the terms of the license agreement you entered into with ICFCC.
 */
package com.icfcc.credit.cache;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.redisson.api.RLock;

import com.icfcc.credit.platform.util.RedissonUtil;

/**
 * 分布式锁 的测试案例
 * @author JERRY.CHEN
 * @date 2017年7月4日
 */
public class RedisReentrantLockTest {
	public static void main(String[] args) {
		RedisReentrantLockTest t = new RedisReentrantLockTest();
		t.testReentrantLock();
//		t.testNoLock();
	}

	@Test
	public void testReentrantLock() {
		for (int i = 0; i < 10; i++) {
			Task t = new Task();
			t.start();
		}
	}
	
	@Test
	public void testNoLock() {
		for (int i = 0; i < 10; i++) {
			Task1 t = new Task1();
			t.start();
		}
	}

	class Task extends Thread {
		@Override
		public void run() {
			long threadId = Thread.currentThread().getId();
			RLock lock = null;
			try {
				lock = RedissonUtil.getLocalRedisson().getFairLock("anyLock");
				lock.tryLock(0, 1, TimeUnit.SECONDS);
				System.out.println("threadId："+threadId+"====尝试加锁成功========");
				System.out.println("threadId："+threadId+"======处理业务=========" + threadId + ":"
						+ System.currentTimeMillis());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				System.out.println("threadId："+threadId+"======解锁完成=========" + threadId + ":"
						+ System.currentTimeMillis());
				if(null != lock) {
					lock.unlock();
				}
				
			}

		}
	}
	class Task1 extends Thread {
		@Override
		public void run() {
			long threadId = Thread.currentThread().getId();
			try {
				System.out.println("threadId："+threadId+"====尝试加锁成功========");
				System.out.println("threadId："+threadId+"======处理业务=========" + threadId + ":"
						+ System.currentTimeMillis());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				System.out.println("threadId："+threadId+"======解锁完成=========" + threadId + ":"
						+ System.currentTimeMillis());
			}

		}
	}
}
