package com.liaoin.demo.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.surpassm.common.jackson.Result;
import com.liaoin.demo.entity.common.Log;
import com.liaoin.demo.mapper.common.LogMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * LogAspect
 *
 * @author zhangquanli
 */
//@Slf4j
//@Aspect
//@Component
public class LogAspect {
	private ObjectMapper objectMapper = new ObjectMapper();

	@Resource
	private LogMapper logMapper;

	@Before("execution(* com.liaoin.*.controller..*.insert*(..)) || " +
			"execution(* com.liaoin.*.controller..*.update*(..)) || " +
			"execution(* com.liaoin.*.controller..*.delete*(..))")
	public void setLog(JoinPoint joinPoint) {
		// 日志
		Log log = new Log();
		// 模块
		Class clazz = joinPoint.getSignature().getDeclaringType();
		if (clazz.isAnnotationPresent(Api.class)) {
			Api api = (Api) clazz.getAnnotation(Api.class);
			log.setModule(api.tags()[0]);
		}
		// 功能
		for (Method method : clazz.getMethods()) {
			if (joinPoint.getSignature().getName().equals(method.getName())) {
				if (method.isAnnotationPresent(ApiOperation.class)) {
					ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
					log.setFunction(apiOperation.value());
				}
			}
		}
		// 接口地址、客户端IP
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (requestAttributes != null) {
			HttpServletRequest request = requestAttributes.getRequest();
			log.setUri(request.getRequestURI());
			log.setClientIp(request.getRemoteHost());
			String header = request.getHeader("Authorization");
			if (header != null && header.startsWith("Bearer ")) {
				String token = header.substring(7);
				// 操作开始时间
				log.setOperateStartTime(LocalDateTime.now());
				// 用户主键
				log.setUserId(UserIdHolder.get());
				// 保存日志到本地线程
				LogHolder.set(log);
			}
		}

	}

	@AfterReturning(pointcut = "execution(* com.liaoin.*.service..*.insert(..)) || " +
			"execution(* com.liaoin.*.service..*.update(..)) || " +
			"execution(* com.liaoin.*.service..*.deleteById(..))", returning = "result")
	public void setLogData(Result result) throws JsonProcessingException {
		if (200 == result.getCode()) {
			// 获取日志
			Log log = LogHolder.get();
			// 数据
			if (result.getData() != null) {
				String data = objectMapper.writeValueAsString(result.getData());
				log.setData(data);
			}
			// 操作结束时间
			log.setOperateEndTime(LocalDateTime.now());
			// 新增日志
			logMapper.insert(log);
		}
	}

	@After("execution(* com.liaoin.*.controller..*.insert*(..)) || " +
			"execution(* com.liaoin.*.controller..*.update*(..)) || " +
			"execution(* com.liaoin.*.controller..*.delete*(..))")
	public void removeLog() {
		// 从本地线程中删除日志
		LogHolder.remove();
	}
}
