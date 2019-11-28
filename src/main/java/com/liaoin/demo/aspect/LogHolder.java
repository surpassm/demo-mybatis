package com.liaoin.demo.aspect;


import com.liaoin.demo.entity.common.OperationsLog;

/**
 * @author Administrator
 */
public class LogHolder {

    private static final ThreadLocal<OperationsLog> LOG_THREAD_LOCAL = new ThreadLocal<>();

    public static void set(OperationsLog operationsLog) {
        LOG_THREAD_LOCAL.set(operationsLog);
    }

    public static OperationsLog get() {
        return LOG_THREAD_LOCAL.get();
    }

    public static void remove() {
        LOG_THREAD_LOCAL.remove();
    }
}
