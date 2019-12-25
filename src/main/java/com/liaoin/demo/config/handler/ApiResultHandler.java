package com.liaoin.demo.config.handler;

import com.liaoin.demo.common.Result;
import com.liaoin.demo.common.ResultCode;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import springfox.documentation.swagger.web.ApiResourceController;
import springfox.documentation.swagger2.web.Swagger2Controller;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mc
 * Create date 2019/12/25 14:55
 * Version 1.0
 * Description 统一返回封装数据
 */
@ControllerAdvice(annotations = RestController.class)
public class ApiResultHandler implements ResponseBodyAdvice<Object> {
    /**
     * 不需要拦截的类路径，这里写的是Class
     * 如果该类所在项目没有相关的依赖，可以换成String-类的全路径
     */
    private static final List<Class<?>> SKIP_CLASS_LIST = new ArrayList<>(2);
    static {
        //Swagger
        SKIP_CLASS_LIST.add(ApiResourceController.class);
        //Swagger
        SKIP_CLASS_LIST.add(Swagger2Controller.class);
    }

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        if (SKIP_CLASS_LIST.contains(returnType.getDeclaringClass())) {
            return false;
        }
//        Method returnTypeMethod = returnType.getMethod();
//        if (returnTypeMethod != null) {
//            return !returnTypeMethod.isAnnotationPresent(RestSkip.class);
//        }
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (MediaType.IMAGE_JPEG.getType().equalsIgnoreCase(selectedContentType.getType())) {
            return body;
        }
        if (body instanceof Result) {
            return body;
        }
        return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMsg(), body);
    }
}