/**
 * @Title: MyThreadPool.java 
 * @Package cn.pku.net.db.hive.ndvr.util 
 * @Description: 线程池
 * @author Jiawei Jiang    
 * @date 2014年10月13日 下午8:05:13 
 * School of EECS, Peking University
 * Copyright (c) All Rights Reserved.
 */
package cn.pku.net.db.hive.ndvr.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

/**
 * @ClassName: MyThreadPool
 * @Description: 线程池
 * @author Jiawei Jiang
 * @date 2014年10月13日 下午8:05:13
 */
public class MyThreadPool {

    private static Logger                logger   = Logger.getLogger(MyThreadPool.class);
    private static final MyThreadPool    INSTANCE = new MyThreadPool();
    private static final ExecutorService pool     = Executors.newFixedThreadPool(10);

    private MyThreadPool() {

    }

    public static MyThreadPool getInstance() {
        return INSTANCE;
    }

    public static ExecutorService getPool() {
        return pool;
    }
}
