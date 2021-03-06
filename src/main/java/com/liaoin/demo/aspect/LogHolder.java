package com.liaoin.demo.aspect;


import com.liaoin.demo.entity.common.Log;

/**
 * LogHolder
 *
 * @author zhangquanli
 */
public class LogHolder {

    private static final ThreadLocal<Log> LOG_THREAD_LOCAL = new ThreadLocal<>();

    public static void set(Log log) {
        LOG_THREAD_LOCAL.set(log);
    }

    public static Log get() {
        return LOG_THREAD_LOCAL.get();
    }

    public static void remove() {
        LOG_THREAD_LOCAL.remove();
    }
}
