package com.liaoin.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liaoin.demo.common.Result;
import com.liaoin.demo.common.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author mc
 * Create date 2019/3/1 9:12
 * Version 1.0
 * Description token拦截器
 */
@Slf4j
@Component
public class TokenInterceptor extends HandlerInterceptorAdapter {
	@Resource
	private ObjectMapper objectMapper;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String headerKey = "Authorization";
		String token = request.getHeader(headerKey);
		if (token != null) {
			request.setAttribute(headerKey, token);
			return true;
		}
		response(request,response);
		return false;
	}


	/**
	 * 返回错误信息
	 */
	private void response(HttpServletRequest request,HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Access-Control-Allow-Origin", "*");
		try (PrintWriter out = response.getWriter()) {
			log.error("请求地址:"+request.getRequestURI()+ ResultCode.PERMISSION_NO_ACCESS.getMsg()+",请携带token");
			out.write(objectMapper.writeValueAsString(new Result(ResultCode.PERMISSION_NO_ACCESS.getCode(), ResultCode.PERMISSION_NO_ACCESS.getMsg(), "")));
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
