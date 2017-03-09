package tmall.web.servlet;

import tmall.bean.Order;
import tmall.dao.impl.OrderDAO;
import tmall.util.Page;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * 作者: wangxh
 * 创建日期: 17-3-9
 * 简介:
 */
@WebServlet(name = "orderServlet", urlPatterns = "/orderServlet")
public class OrderServlet extends BaseBackServlet {

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
        List<Order> os = super.orderService.list(page.getStart(), page.getCount());
        // fill 计算订单的数量以及总资金
        super.orderItemService.fill(os);
        int total = super.orderService.getTotal();
        page.setTotal(total);
        request.setAttribute("page", page);
        request.setAttribute("os", os);
        return "/WEB-INF/jsp/admin/listOrder.jsp";
    }

    public String delivery(HttpServletRequest req, HttpServletResponse resp, Page page) {
        int id = Integer.parseInt(req.getParameter("id"));
        Order order = super.orderService.get(id);
        order.setStatus(OrderDAO.WAIT_CONFIRM);
        order.setDeliveryDate(new Date());
        super.orderService.update(order);
        return "@admin_order_list";
    }
}
