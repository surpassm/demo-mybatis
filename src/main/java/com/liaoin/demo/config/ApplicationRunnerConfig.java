package com.liaoin.demo.config;

import com.github.surpassm.config.token.WebAppConfigurer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mc
 * Create date 2019/9/6 16:34
 * Version 1.0
 * Description
 */
@Slf4j
@Configuration
public class ApplicationRunnerConfig implements ApplicationRunner {
	@Resource
	private RequestMappingHandlerMapping mapping;
	@Override
	public void run(ApplicationArguments args) throws Exception {
		//新建一个数组
		List<String> exemptionUrl = new ArrayList<>();
		//新增默认排除项
		exemptionUrl.add("/swagger-");
		exemptionUrl.add("/images/");
		exemptionUrl.add("/webjars/");
		exemptionUrl.add("/v2/api-docs");
		exemptionUrl.add("/swagger-resources/configuration/**");
		exemptionUrl.add("/websocket/socketServer.ws");
		exemptionUrl.add("/sockjs/socketServer.ws");
		Map<RequestMappingInfo, HandlerMethod> map = mapping.getHandlerMethods();
		for (Map.Entry<RequestMappingInfo, HandlerMethod> m : map.entrySet()) {
			RequestMappingInfo info = m.getKey();
			HandlerMethod method = m.getValue();
			Method method1 = method.getMethod();
			Annotation[] annotations = method1.getAnnotations();
			boolean tag = false;
			for (Annotation annotation : annotations) {
				String typeName = annotation.annotationType().getTypeName();
				System.out.println(typeName);
			}
			if (tag) {
				PatternsRequestCondition p = info.getPatternsCondition();
				for (String url : p.getPatterns()) {
					exemptionUrl.add(url + "**");
				}
			}
		}
		Object[] objects = exemptionUrl.toArray();
	}
}
