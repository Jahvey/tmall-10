package tmall.web.servlet;

import tmall.bean.Category;
import tmall.bean.Product;
import tmall.bean.PropertyValue;
import tmall.util.Page;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 作者: wangxh
 * 创建日期: 17-3-8
 * 简介:
 */
@WebServlet(name = "productServlet", urlPatterns = "/productServlet")
public class ProductServlet extends BaseBackServlet {

    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
        int cid = Integer.valueOf(request.getParameter("cid"));
        Product product = new Product();
        product.setName(request.getParameter("name"));
        product.setSubTitle(request.getParameter("subTitle"));
        product.setOrignalPrice(Float.parseFloat(request.getParameter("orignalPrice")));
        product.setPromotePrice(Float.parseFloat(request.getParameter("promotePrice")));
        product.setStock(Integer.parseInt(request.getParameter("stock")));
        product.setCategory(super.categoryService.get(cid));
        product.setCreateDate(new Date());
        super.productService.add(product);
        return "@admin_product_list?cid=" + cid;
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
        int id = Integer.parseInt(request.getParameter("id"));
        Product product = productService.get(id);
        super.productService.delete(id);
        return "@admin_product_list?cid=" + product.getCategory().getId();
    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
        int id = Integer.parseInt(request.getParameter("id"));
        Product product = super.productService.get(id);
        request.setAttribute("p", product);
        return "/WEB-INF/jsp/admin/editProduct.jsp";
    }

    @Override
    public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
        int cid = Integer.valueOf(request.getParameter("cid"));
        int id = Integer.parseInt(request.getParameter("id"));
        Product product = super.productService.get(id);
        product.setName(request.getParameter("name"));
        product.setSubTitle(request.getParameter("subTitle"));
        product.setOrignalPrice(Float.parseFloat(request.getParameter("orignalPrice")));
        product.setPromotePrice(Float.parseFloat(request.getParameter("promotePrice")));
        product.setStock(Integer.parseInt(request.getParameter("stock")));
        product.setCategory(super.categoryService.get(cid));
        super.productService.update(product);
        request.setAttribute("p", product);
        return "@admin_product_list?cid=" + cid;
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
        int cid = Integer.parseInt(request.getParameter("cid"));
        Category category = super.categoryService.get(cid);
        List<Product> ps = super.productService.list(cid);
        page.setTotal(super.productService.getTotal(cid));
        page.setParam("&cid=" + cid);
        request.setAttribute("c", category);
        request.setAttribute("ps", ps);
        request.setAttribute("page", page);
        return "/WEB-INF/jsp/admin/listProduct.jsp";
    }

    public String editPropertyValue(HttpServletRequest req, HttpServletResponse resp, Page page) {
        int pid = Integer.parseInt(req.getParameter("pid"));
        Product product = super.productService.get(pid);
        List<PropertyValue> propertyValues = super.propertyValueService.list(pid);
        req.setAttribute("p", product);
        req.setAttribute("pvs", propertyValues);
        return "/WEB-INF/jsp/admin/editPropertyValue.jsp";
    }

    public String updatePropertyValue(HttpServletRequest req, HttpServletResponse resp, Page page) {
        int id = Integer.parseInt(req.getParameter("pvid"));
        String value = req.getParameter("value");
        PropertyValue propertyValue = super.propertyValueService.get(id);
        propertyValue.setValue(value);
        super.propertyValueService.update(propertyValue);
        return "%success";
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }
}
