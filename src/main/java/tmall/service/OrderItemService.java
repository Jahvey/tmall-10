package tmall.service;

import tmall.bean.Order;
import tmall.bean.OrderItem;
import tmall.dao.impl.OrderItemDAO;

import java.util.List;

/**
 * 作者: wangxh
 * 创建日期: 17-3-9
 * 简介:
 */
public class OrderItemService {
    private final OrderItemDAO orderItemDAO = new OrderItemDAO();

    public OrderItem get(int id) {
        return orderItemDAO.get(id);
    }

    public void fill(List<Order> orders) {
        orderItemDAO.fill(orders);
    }

    public List<OrderItem> listByOrder(int oid) {
        return orderItemDAO.listByOrder(oid);
    }

    public List<OrderItem> listByUser(int uid) {
        return orderItemDAO.listByUser(uid);
    }

    public void update(OrderItem orderItem) {
        orderItemDAO.update(orderItem);
    }
}
