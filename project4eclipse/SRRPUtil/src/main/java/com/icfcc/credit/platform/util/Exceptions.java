/**
 * Copyright (c) 2005-2014 leelong.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.icfcc.credit.platform.util;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 关于异常的工具类.
 * 
 */
public class Exceptions
{
    
    private Exceptions()
    {
        super();
    }
    
    /**
     * 将CheckedException转换为UncheckedException.
     */
    public static RuntimeException unchecked(Exception e)
    {
        if (e instanceof RuntimeException)
        {
            return (RuntimeException)e;
        }
        else
        {
            return new RuntimeException(e);
        }
    }
    
    /**
     * 将ErrorStack转化为String.
     */
    public static String getStackTraceAsString(Exception e)
    {
        StringWriter stringWriter = new StringWriter();
        return stringWriter.toString();
    }
    /**
     * Java如何将Exception.printStackTrace()转换为String输出
     * @param e
     * @return
     */
    public static String getErrorInfoFromException(Exception e) {  
        try {  
            StringWriter sw = new StringWriter();  
            PrintWriter pw = new PrintWriter(sw);  
            e.printStackTrace(pw);  
            return "\r\n" + sw.toString() + "\r\n";  
        } catch (Exception e2) {  
            return "bad getErrorInfoFromException";  
        }  
    }  

    /**
     * 
     * 判断异常是否由某些底层的异常引起.
     */
    @SafeVarargs
	public static boolean isCausedBy(Exception ex, Class<? extends Exception>... causeExceptionClasses)
    {
        Throwable cause = ex;
        while (cause != null)
        {
            for (Class<? extends Exception> causeClass : causeExceptionClasses)
            {
                if (causeClass.isInstance(cause))
                {
                    return true;
                }
            }
            cause = cause.getCause();
        }
        return false;
    }
}
