package tmall.service;

import tmall.bean.Order;
import tmall.dao.impl.OrderDAO;

import java.util.List;

/**
 * 作者: wangxh
 * 创建日期: 17-3-9
 * 简介:
 */
public class OrderService {
    private final OrderDAO orderDAO = new OrderDAO();

    public Order get(int id) {
        return orderDAO.get(id);
    }

    public int getTotal() {
        return orderDAO.getTotal();
    }

    public void update(Order order) {
        orderDAO.update(order);
    }

    public List<Order> list() {
        return orderDAO.list();
    }

    public List<Order> list(int uid, String excludedStatus) {
        return orderDAO.list(uid, excludedStatus);
    }

    public List<Order> list(int start, int count) {
        return orderDAO.list(start, count);
    }
}
