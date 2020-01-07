package com.bupt.aiya.blockchain.Util;


import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by aiya on 2019/3/21 上午11:10
 */
@Component
@WebFilter(urlPatterns = "/*", filterName = "bcFilter")
public class PassHttpFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse=(HttpServletResponse) response;
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String requestUri = ((HttpServletRequest) request).getRequestURI();
        System.out.println("the request we get:"+requestUri);
        //allow other origin access
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Credentials","true");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "Content-Type,x-requested-with,X-Custom-Header, HaiYi-Access-Token");
        httpServletResponse.setHeader("Access-Control-Allow-Methods","PUT,POST,GET,DELETE,OPTIONS");
        chain.doFilter(request, httpServletResponse);
        System.out.println("i have been done filter");
    }

    @Override
    public void destroy() {

    }
}
