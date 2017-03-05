package tmall.dao.impl;

import org.junit.Test;
import tmall.bean.OrderItem;
import tmall.bean.Product;
import tmall.bean.User;

import static org.junit.Assert.*;

/**
 * 作者: wangxh
 * 创建日期: 17-3-3
 * 简介:
 */
public class OrderItemDAOTest {

    private OrderItemDAO orderItemDAO = new OrderItemDAO();
    private UserDAO userDAO = new UserDAO();
    private ProductDAO productDAO = new ProductDAO();

    @Test
    public void getTotal() throws Exception {
        System.out.println(orderItemDAO.getTotal());
    }

    @Test
    public void add() throws Exception {
        User user = userDAO.get("wangxh");
        Product product = productDAO.get(3);
        OrderItem orderItem = new OrderItem();
        orderItem.setId(1);
        orderItem.setNumber(2);
        orderItem.setProduct(product);
        orderItem.setUser(user);
        orderItemDAO.add(orderItem);
    }

    @Test
    public void update() throws Exception {

    }

    @Test
    public void delete() throws Exception {

    }

    @Test
    public void get() throws Exception {

    }

    @Test
    public void listByUser() throws Exception {
        User user = userDAO.get("wangxh");
        for (OrderItem orderItem : orderItemDAO.listByUser(user.getId())) System.out.println(orderItem);
    }

    @Test
    public void listByUser1() throws Exception {

    }

    @Test
    public void listByOrder() throws Exception {

    }

    @Test
    public void listByOrder1() throws Exception {

    }

    @Test
    public void fill() throws Exception {

    }

    @Test
    public void fill1() throws Exception {

    }

    @Test
    public void listByProduct() throws Exception {

    }

    @Test
    public void listByProduct1() throws Exception {

    }

    @Test
    public void getSaleCount() throws Exception {

    }

}