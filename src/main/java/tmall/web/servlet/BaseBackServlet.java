package tmall.web.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tmall.dao.impl.*;
import tmall.util.Page;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 作者: wangxh
 * 创建日期: 17-3-4
 * 简介:
 */
public abstract class BaseBackServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(BaseBackServlet.class);

    public abstract String add(HttpServletRequest request, HttpServletResponse response, Page page) ;
    public abstract String delete(HttpServletRequest request, HttpServletResponse response, Page page) ;
    public abstract String edit(HttpServletRequest request, HttpServletResponse response, Page page) ;
    public abstract String update(HttpServletRequest request, HttpServletResponse response, Page page) ;
    public abstract String list(HttpServletRequest request, HttpServletResponse response, Page page) ;

    protected CategoryDAO categoryDAO = new CategoryDAO();
    protected OrderDAO orderDAO = new OrderDAO();
    protected OrderItemDAO orderItemDAO = new OrderItemDAO();
    protected ProductDAO productDAO = new ProductDAO();
    protected ProductImageDAO productImageDAO = new ProductImageDAO();
    protected PropertyDAO propertyDAO = new PropertyDAO();
    protected PropertyValueDAO propertyValueDAO = new PropertyValueDAO();
    protected ReviewDAO reviewDAO = new ReviewDAO();
    protected UserDAO userDAO = new UserDAO();

    /**
     * 利用反射根据传过来的method参数判断需要执行哪个方法
     * 具体方法由子类实现
     * @param req request
     * @param resp response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String startStr = req.getParameter("page.start");
        String countStr = req.getParameter("page.count");
        int start = Integer.parseInt(
                startStr == null || "".equals(startStr) ? "0" : req.getParameter("page.start"));
        int count = Integer.parseInt(
                countStr == null || "".equals(countStr) ? "5" : req.getParameter("page.count"));
        Page page = new Page(start, count);
        String methodStr = (String) req.getAttribute("method");
        // 设置默认的方法
        if (methodStr == null) methodStr = "list";
        Method method;
        try {
            method = this.getClass().getMethod(methodStr, HttpServletRequest.class, HttpServletResponse.class,
                    Page.class);
            String redirect = (String) method.invoke(this, req, resp, page);
                /* @: 客户端跳转
                 * %: 页面输出
                 * others: 服务器跳转
                 */
            logger.info("重定向: " + redirect);
            if(redirect.startsWith("@"))
                resp.sendRedirect(redirect.substring(1));
            else if(redirect.startsWith("%"))
                resp.getWriter().print(redirect.substring(1));
            else
                req.getRequestDispatcher(redirect).forward(req, resp);
        } catch (NoSuchMethodException e) {
            logger.error("没有指定的方法{}", methodStr, e);
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error("方法 {} 反射出错", methodStr, e);
        }
    }

    public InputStream parseUpload(HttpServletRequest req, Map<String, String> params) {
        //TODO 待完成
        return null;
    }

}
