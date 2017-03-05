package tmall.dao.impl;

import org.junit.Before;
import org.junit.Test;
import tmall.bean.Order;
import tmall.bean.OrderItem;
import tmall.bean.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * 作者: wangxh
 * 创建日期: 17-3-2
 * 简介:
 */
public class OrderDAOTest {

    private OrderDAO orderDAO = new OrderDAO();
    private Order order = new Order();

    @Before
    public void init() {
        User user = new UserDAO().get("wangxh");
        order.setId(1);
        order.setAddress("湖北武汉");
        order.setConfirmDate(new Date());
        order.setCreateDate(new Date());
        order.setDeliveryDate(new Date());
        order.setMobile("18657562524");
        order.setPost("stormaroon@gmail.com");
        order.setReceiver("wangxh");
        order.setUser(user);
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(new OrderItem());
        order.setOrderItems(orderItems);
    }

    @Test
    public void getTotal() throws Exception {
        int total = orderDAO.getTotal();
        System.out.println(total);
    }

    @Test
    public void add() throws Exception {
        orderDAO.add(order);
    }

    @Test
    public void update() throws Exception {
        order.setStatus(OrderDAO.WAIT_PAY);
        order.setUserMessage("尽快发货");
        orderDAO.update(order);
    }

    @Test
    public void delete() throws Exception {

    }

    @Test
    public void get() throws Exception {

    }

    @Test
    public void list() throws Exception {

    }

    @Test
    public void list1() throws Exception {
        for (Order order : orderDAO.list(0, 1)) System.out.println(order);

    }

    @Test
    public void list2() throws Exception {
        for (Order order : orderDAO.list(1, OrderDAO.WAIT_PAY));
    }

}