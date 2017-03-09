package tmall.web.servlet;

import tmall.bean.User;
import tmall.util.Page;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 作者: wangxh
 * 创建日期: 17-3-9
 * 简介:
 */
@WebServlet(name = "userServlet", urlPatterns = "/userServlet")
public class UserServlet extends BaseBackServlet {

    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
        List<User> us = super.userService.list();
        int total = userService.getTotal();
        page.setTotal(total);
        request.setAttribute("page", page);
        request.setAttribute("us", us);
        return "/WEB-INF/jsp/admin/listUser.jsp";
    }
}
