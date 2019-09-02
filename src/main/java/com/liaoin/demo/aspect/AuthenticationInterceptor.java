package com.liaoin.demo.aspect;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 认证拦截器
 *
 * @author zhangquanli
 */
@Slf4j
//@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // Get authorization from header
        String authorization = request.getHeader("JwtConstants.AUTHORIZATION_HEADER_KEY");
        // If the Http request is OPTIONS, then just return the status code 200
        if (request.getMethod().equals(HttpMethod.OPTIONS.name())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        // Check the authorization, check if the token is started by "Bearer "
        if (authorization == null || !authorization.startsWith("JwtConstants.AUTHORIZATION_HEADER_VALUE_PREFIX")) {
            throw new RuntimeException();
        }
        // Then get the JWT token from authorization
//        String jws = authorization.substring(JwtConstants.AUTHORIZATION_HEADER_VALUE_PREFIX.length());
        // Use JWT parser to check if the signature is valid with the Key
        Long userId = Long.parseLong("JwtUtil.getSubFromJws(jws)");
        // Save userId to UserIdHolder
        UserIdHolder.set(userId);
        log.info("认证成功>>>userId>>>{}", userId);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        UserIdHolder.remove();
    }
}
