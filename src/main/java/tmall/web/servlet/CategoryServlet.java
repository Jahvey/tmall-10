package tmall.web.servlet;

import tmall.bean.Category;
import tmall.service.CategoryService;
import tmall.util.Page;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 作者: wangxh
 * 创建日期: 17-3-4
 * 简介:
 */
@WebServlet(name = "categoryServlet", urlPatterns = "/categoryServlet")
public class CategoryServlet extends BaseBackServlet {

    private CategoryService categoryService = new CategoryService();

    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    @Override
    public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
        return null;
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
        List<Category> categoryList = categoryService.list(page.getStart(), page.getCount());
        int total = categoryService.getTotal();
        page.setTotal(total);
        request.setAttribute("clist", categoryList);
        request.setAttribute("page", page);
        return "/WEB-INF/jsp/admin/listCategory.jsp";
    }

}
