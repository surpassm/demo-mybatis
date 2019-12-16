package com.liaoin.demo.config.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liaoin.demo.annotation.JwtConstants;
import com.liaoin.demo.common.Result;
import com.liaoin.demo.entity.common.OperationsLog;
import com.liaoin.demo.mapper.common.OperationsLogMapper;
import com.liaoin.demo.util.JwtUtils;
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
 * @author Administrator
 */
@Slf4j
@Aspect
@Component
public class LogAspect {
	@Resource
	private ObjectMapper objectMapper;
	@Resource
	private OperationsLogMapper operationsLogMapper;
	@Resource
	private  String[] noVerify;


	@Before("execution(* com.liaoin.*.controller..*.insert*(..)) || " +
			"execution(* com.liaoin.*.controller..*.update*(..)) || " +
			"execution(* com.liaoin.*.controller..*.delete*(..))")
	public void setLog(JoinPoint joinPoint) {
		// 日志
		OperationsLog operationsLog = new OperationsLog();
		// 模块
		Class clazz = joinPoint.getSignature().getDeclaringType();
		if (clazz.isAnnotationPresent(Api.class)) {
			Api api = (Api) clazz.getAnnotation(Api.class);
			operationsLog.setModule(api.tags()[0]);
		}
		// 功能
		for (Method method : clazz.getMethods()) {
			if (joinPoint.getSignature().getName().equals(method.getName())) {
				if (method.isAnnotationPresent(ApiOperation.class)) {
					ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
					operationsLog.setFunction(apiOperation.value());
				}
			}
		}
		// 接口地址、客户端IP
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (requestAttributes != null) {
			HttpServletRequest request = requestAttributes.getRequest();
			String url = request.getRequestURI();
			operationsLog.setUri(url);
			operationsLog.setClientIp(request.getRemoteHost());
			if (!checkAuthorization(url)) {
				String token = request.getHeader(JwtConstants.AUTHORIZATION_HEADER_KEY);
				long userId = Long.parseLong(JwtUtils.getSubFromToken(token));
				// 用户主键
				operationsLog.setUserId(userId);
			}
			// 操作开始时间
			operationsLog.setOperateStartTime(LocalDateTime.now());
			// 保存日志到本地线程
			LogHolder.set(operationsLog);
		}
	}

	/**
	 * 效验当前请求是否属于免验证接口
	 * @param url 接口
	 * @return boolean
	 */
	private boolean checkAuthorization(String url){
		boolean flag =false;
		url = url + "**";
		for (String s : noVerify) {
			 if (url.equals(s)){
				 flag = true;
				 break;
			 }
		}
		return flag;
	}

	@AfterReturning(pointcut = "execution(* com.liaoin.*.service..*.insert*(..)) || " +
			"execution(* com.liaoin.*.service..*.update*(..)) || " +
			"execution(* com.liaoin.*.service..*.deleteById*(..))", returning = "result")
	public void setLogData(Result result) throws JsonProcessingException {
		if (200 == result.getCode()) {
			// 获取日志
			OperationsLog operationsLog = LogHolder.get();
			// 数据
			if (result.getData() != null) {
				String data = objectMapper.writeValueAsString(result.getData());
				operationsLog.setData(data);
			}
			// 操作结束时间
			operationsLog.setOperateEndTime(LocalDateTime.now());
			// 新增日志
			operationsLogMapper.insert(operationsLog);
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