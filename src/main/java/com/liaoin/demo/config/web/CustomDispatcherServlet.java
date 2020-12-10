package com.liaoin.demo.config.web;

import com.liaoin.demo.config.security.XssHttpServletRequestWrapper;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mc
 * Create date 2020/12/10 11:15
 * Version 1.0
 * Description
 */
public class CustomDispatcherServlet extends DispatcherServlet {

    @Override
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        super.doDispatch(new XssHttpServletRequestWrapper(request), response);

    }
}
