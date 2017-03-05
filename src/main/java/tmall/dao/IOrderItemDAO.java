package tmall.dao;

import tmall.bean.Order;
import tmall.bean.OrderItem;

import java.util.List;

/**
 * 作者: wangxh
 * 创建日期: 17-3-2
 * 简介:
 */
public interface IOrderItemDAO {
    int getTotal();
    void add(OrderItem orderItem);
    void update(OrderItem orderItem);
    void delete(int id);
    OrderItem get(int id);

    /**
     * 列出一个用户下的所有订单
     * @param uid 用户id
     * @return 该用户的OrderItem
     */
    List<OrderItem> listByUser(int uid);

    List<OrderItem> listByUser(int uid, int start, int count);

    /**
     * 列出一笔订单下的物品
     * @param oid 订单id
     * @return 订单物品集合
     */
    List<OrderItem> listByOrder(int oid);

    /**
     * 列出包含某件产品的订单
     * @param pid 产品id
     * @return 订单物品集合
     */
    List<OrderItem> listByProduct(int pid);

    /**
     * 建立反向关联关系
     * @param order 订单对象
     */
    void fill(Order order);
    void fill(List<Order> orders);

    /**
     * 获取某产品的销量
     * @param pid 产品id
     * @return int 销量
     */
    int getSaleCount(int pid);
}
