package tmall.web.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tmall.bean.Category;
import tmall.bean.Property;
import tmall.dao.impl.CategoryDAO;
import tmall.service.PropertyService;
import tmall.util.Page;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 作者: wangxh
 * 创建日期: 17-3-6
 * 简介:
 */
@WebServlet(name = "propertyServlet", urlPatterns = "/propertyServlet")
public class PropertyServlet extends BaseBackServlet {

    private static final Logger logger = LoggerFactory.getLogger(PropertyServlet.class);
    private final PropertyService propertyService = new PropertyService();
    private final CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
        String name = request.getParameter("name");
        logger.info("cid: " + request.getParameter("cid"));
        int cid = Integer.parseInt(request.getParameter("cid"));
        Category category = categoryDAO.get(cid);
        Property property = new Property(name, category);
        propertyService.add(property);
        return "@admin_property_list?cid=" + cid;
    }

    @Override
    public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
        int id = Integer.parseInt(request.getParameter("id"));
        logger.info("pid: {}", id);
        Property property = propertyService.get(id);
        propertyService.delete(id);
        return "@admin_property_list?cid=" + property.getCategory().getId();
    }

    @Override
    public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
        int id = Integer.parseInt(request.getParameter("id"));
        Property property = propertyService.get(id);
        request.setAttribute("p", property);
        return "/WEB-INF/jsp/admin/editProperty.jsp";
    }

    @Override
    public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
        String name = request.getParameter("name");
        int id = Integer.parseInt(request.getParameter("id"));
        int cid = Integer.parseInt(request.getParameter("cid"));
        Property property = new Property();
        property.setId(id);
        property.setName(name);
        property.setCategory(categoryService.get(cid));
        propertyService.update(property);
        return "@admin_property_list?cid=" + cid;
    }

    @Override
    public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
        int cid = Integer.parseInt(request.getParameter("cid"));
        Category category = categoryDAO.get(cid);
        List<Property> properties = propertyService.list(cid, page.getStart(), page.getCount());
        int total = propertyService.getTotal(cid);
        page.setTotal(total);
        page.setParam("&cid=" + category.getId());
        request.setAttribute("c", category);
        request.setAttribute("page", page);
        request.setAttribute("ps", properties);
        return "/WEB-INF/jsp/admin/listProperty.jsp";
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }
}
