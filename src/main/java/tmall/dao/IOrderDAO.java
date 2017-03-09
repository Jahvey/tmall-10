package tmall.dao;

import tmall.bean.Order;

import java.util.List;

/**
 * 作者: wangxh
 * 创建日期: 17-3-2
 * 简介:
 */
public interface IOrderDAO {
    int getTotal();
    void add(Order order);
    void update(Order order);
    void delete(int id);
    Order get(int id);

    List<Order> list();

    List<Order> list(int start, int count);

    /**
     * 查询所有订单
     * @param uid 用户id
     * @param excludedStatus 排除状态
     * @return
     */
    List<Order> list(int uid, String excludedStatus);
    List<Order> list(int uid, String excludedStatus, int start, int count);
}
