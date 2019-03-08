package com.suntion.core.log;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 操作日志多线程处理
 * @author suns suntion@yeah.net
 * @since 2018年4月10日上午11:22:22
 */
public class OperationLog {
    public static ThreadPoolExecutor threadPoolExecutor;
    public static int DEFAULT_OPERATION_LOG_THREAD_POOL_COUNT = 5;

    public static void pushLog(OperationLogDTO operationLogDTO) {
        if (operationLogDTO == null) {
            return;
        }

        int threadPoolCount = getOperationLogThreadCount(operationLogDTO);

        //初始化
        if (threadPoolExecutor == null || threadPoolExecutor.isShutdown()) {
            threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadPoolCount);
        }

        //线程池大小是否发生变化
        if (threadPoolExecutor.getCorePoolSize() != threadPoolCount) {
            threadPoolExecutor.shutdown();
            threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(threadPoolCount);
        }

        //向线程池添加线程
        OperationLogThread logThread = new OperationLogThread(operationLogDTO);
        threadPoolExecutor.execute(logThread);
    }


    public static int getOperationLogThreadCount(OperationLogDTO operationLogDTO) {
        if (0 != operationLogDTO.getThreadCount()) {
            return operationLogDTO.getThreadCount();
        } else {
            return DEFAULT_OPERATION_LOG_THREAD_POOL_COUNT;
        }
    }
}
