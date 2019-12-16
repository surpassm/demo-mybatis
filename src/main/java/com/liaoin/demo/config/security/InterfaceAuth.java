package com.liaoin.demo.config.security;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * @author mc
 * Create date 2019/12/16 13:07
 * Version 1.0
 * Description
 */
public interface InterfaceAuth {
    /**
     * 当前请求是否具备权限
     * @param request request
     * @return boolean
     */
    boolean hasPermission(HttpServletRequest request);
}
