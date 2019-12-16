package com.liaoin.demo.config.security;

import com.liaoin.demo.annotation.JwtConstants;
import com.liaoin.demo.annotation.Login;
import com.liaoin.demo.util.JwtUtils;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

/**
 * @author mc
 * Create date 2019/3/4 15:53
 * Version 1.0
 * Description
 */
@Component
public class TokenMethodArgumentResolver implements HandlerMethodArgumentResolver {


    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterType().isAssignableFrom(Long.class) && methodParameter.hasParameterAnnotation(Login.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        String token = (String) nativeWebRequest.getAttribute(JwtConstants.AUTHORIZATION_HEADER_KEY, RequestAttributes.SCOPE_REQUEST);
        if (token != null) {
            return Long.parseLong(JwtUtils.getSubFromToken(token));
        }
        throw new MissingServletRequestPartException("请携带header:Authorization");

    }
}