/**
 *  Copyright (c)  2017-2027 ICFCC, Inc.
 *  All rights reserved.
 *
 *  This software is the confidential and proprietary information of ICFCC, 
 *  Inc. ("Confidential Information"). You shall not
 *  disclose such Confidential Information and shall use it only in
 *  accordance with the terms of the license agreement you entered into with ICFCC.
 */
package com.icfcc.credit.platform.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <线程池工具类>
 * @author Densen.Liu
 * @date 2017年4月18日
 */
public class ExecutorUtil {

    public static ExecutorService executor = Executors.newFixedThreadPool(16);
}
