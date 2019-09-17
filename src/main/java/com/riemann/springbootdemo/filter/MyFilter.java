package com.riemann.springbootdemo.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * filterName    过滤器名称
 * urlPatterns   拦截请求的url,进行正则匹配，/*是拦截所有的
 *
 * @author riemann
 * @date 2019/09/16 22:39
 */
@Order(1)
@WebFilter(filterName = "myFilter", urlPatterns = "/*")
public class MyFilter implements Filter {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 容器加载的时候调用
     *
     * @param filterConfig
     * @throws ServletException
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("filter init, filter name: " + filterConfig.getFilterName());
    }

    /**
     * 请求被拦截的时候进行调用
     *
     * @param servletRequest
     * @param servletResponse
     * @param filterChain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if ("1".equals(servletRequest.getParameter("id"))) {
            log.info("filter uri: " + ((HttpServletRequest) servletRequest).getServletPath());
            servletResponse.getWriter().write("filter the data!");
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    /**
     * 容器被销毁的时候被调用
     */
    @Override
    public void destroy() {
        log.info("filter destroy");
    }
}
