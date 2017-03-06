package tmall.web.filter;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 作者: wangxh
 * 创建日期: 17-3-4
 * 简介: 后台过滤器
 */
public class BackServletFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(BackServletFilter.class);

    public void destroy() {}

    public void doFilter(javax.servlet.ServletRequest req, javax.servlet.ServletResponse resp, javax.servlet.FilterChain chain) throws javax.servlet.ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        String contextPath = request.getServletContext().getContextPath();
        String filterPath = contextPath + "/admin_";
        String uri = request.getRequestURI();
        if (uri.startsWith(filterPath)) {
            /*
            例如uri为admin_category_list
            则servletPath为category
            method为list
             */
            String servletPath = StringUtils.substringBetween(uri, "_", "_") + "Servlet";
            String method = StringUtils.substringAfterLast(uri, "_");
            request.setAttribute("method", method);
            logger.info("拦截到后台请求 url: {} method: {}", uri, method);
            logger.info("重定向到: {}:{}", servletPath, method);
            req.getRequestDispatcher(servletPath).forward(req, resp);
            return;
        }
        chain.doFilter(req, resp);
    }

    public void init(javax.servlet.FilterConfig config) throws ServletException {

    }

}
