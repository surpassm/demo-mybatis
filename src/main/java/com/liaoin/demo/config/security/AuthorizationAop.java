package com.liaoin.demo.config.security;

import com.liaoin.demo.annotation.Login;
import com.liaoin.demo.common.ResultCode;
import com.liaoin.demo.exception.CustomException;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author mc
 * Create date 2019/12/16 15:05
 * Version 1.0
 * Description
 */

@Aspect
@Component
public class AuthorizationAop {

    @Resource
    private InterfaceAuth interfaceAuth;

    @Pointcut("@annotation(com.liaoin.demo.annotation.Login)")
    public void pointcut() {

    }

    @Around("pointcut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature ms = (MethodSignature) joinPoint.getSignature();
        Method method = ms.getMethod();
        Login annotation = method.getAnnotation(Login.class);
        if (annotation.required()){
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            if (!interfaceAuth.hasPermission(request)){
                throw new CustomException(ResultCode.PERMISSION_NO_ACCESS.getCode(), ResultCode.PERMISSION_NO_ACCESS.getMsg());
            }
        }
        return joinPoint.proceed();
    }
}
