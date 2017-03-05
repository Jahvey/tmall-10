package tmall.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tmall.bean.Order;
import tmall.bean.OrderItem;
import tmall.bean.Product;
import tmall.bean.User;
import tmall.dao.IOrderItemDAO;
import tmall.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者: wangxh
 * 创建日期: 17-3-2
 * 简介:
 */
public class OrderItemDAO implements IOrderItemDAO {

    private static final Logger logger = LoggerFactory.getLogger(OrderItemDAO.class);

    @Override
    public int getTotal() {
        int total = 0;
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
            String sql = "SELECT COUNT(*) FROM orderItem";
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                total = rs.getInt(1);
            }
            logger.info("查询订单物品总数成功");
        } catch (SQLException e) {
            logger.error("查询订单物品总数失败", e);
        }
        return total;
    }

    @Override
    public void add(OrderItem bean) {
        String sql = "INSERT INTO orderItem VALUES (null,?,?,?,?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, bean.getProduct().getId());
            // 订单项在创建的时候，是没有订单信息的
            ps.setInt(2, bean.getUser().getId());
            if(null == bean.getOrder())
                ps.setInt(3, -1);
            else
                ps.setInt(3, bean.getOrder().getId());
            ps.setInt(4, bean.getNumber());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                bean.setId(id);
            }
            logger.info("添加订单物品 {} 成功", bean.getNumber());
        } catch (SQLException e) {
            logger.error("添加订单物品 {} 错误", bean.getNumber(), e);
        }
    }

    @Override
    public void update(OrderItem bean) {
        String sql = "UPDATE orderItem SET pid= ?, oid=?, uid=?,number=?  WHERE id = ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, bean.getProduct().getId());
            if(null == bean.getOrder())
                ps.setInt(2, -1);
            else
                ps.setInt(2, bean.getOrder().getId());
            ps.setInt(3, bean.getUser().getId());
            ps.setInt(4, bean.getNumber());
            ps.setInt(5, bean.getId());
            ps.execute();
            logger.info("更新订单物品 {} 成功", bean.getNumber());
        } catch (SQLException e) {
            logger.error("更新订单物品 {} 错误", bean.getNumber());
        }
    }

    @Override
    public void delete(int id) {
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
            String sql = "DELETE FROM orderItem WHERE id = " + id;
            s.execute(sql);
            logger.info("删除订单物品成功, ID: ", id);
        } catch (SQLException e) {
            logger.error("删除订单物品失败, ID: ",  id);
        }
    }

    @Override
    public OrderItem get(int id) {
        OrderItem bean = new OrderItem();
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
            String sql = "SELECT * FROM orderItem WHERE id = " + id;
            ResultSet rs = s.executeQuery(sql);
            if (rs.next()) {
                int pid = rs.getInt("pid");
                int oid = rs.getInt("oid");
                int uid = rs.getInt("uid");
                int number = rs.getInt("number");
                Product product = new ProductDAO().get(pid);
                User user = new UserDAO().get(uid);
                bean.setProduct(product);
                bean.setUser(user);
                bean.setNumber(number);
                if(-1!=oid){
                    Order order= new OrderDAO().get(oid);
                    bean.setOrder(order);
                }
                bean.setId(id);
            }
            logger.info("获取订单物品 {} 成功", bean.getNumber());
        } catch (SQLException e) {
            logger.error("获取订单物品 {} 失败", bean.getNumber());
        }
        return bean;
    }

    @Override
    public List<OrderItem> listByUser(int uid) {
        return listByUser(uid, 0, Short.MAX_VALUE);
    }

    @Override
    public List<OrderItem> listByUser(int uid, int start, int count) {
        List<OrderItem> beans = new ArrayList<>();
        String sql = "SELECT * FROM orderItem WHERE uid = ? and oid=-1 ORDER BY id DESC LIMIT ?,? ";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, uid);
            ps.setInt(2, start);
            ps.setInt(3, count);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                OrderItem bean = new OrderItem();
                int id = rs.getInt(1);
                int pid = rs.getInt("pid");
                int oid = rs.getInt("oid");
                int number = rs.getInt("number");
                Product product = new ProductDAO().get(pid);
                if(-1!=oid){
                    Order order= new OrderDAO().get(oid);
                    bean.setOrder(order);
                }
                User user = new UserDAO().get(uid);
                bean.setProduct(product);
                bean.setUser(user);
                bean.setNumber(number);
                bean.setId(id);
                beans.add(bean);
            }
            logger.info("查询用户 {} 订单中的物品信息成功", uid);
        } catch (SQLException e) {
            logger.error("查询用户 {} 订单中的物品信息成功", uid, e);
        }
        return beans;
    }

    public List<OrderItem> listByOrder(int oid) {
        return listByOrder(oid, 0, Short.MAX_VALUE);
    }

    public List<OrderItem> listByOrder(int oid, int start, int count) {
        List<OrderItem> beans = new ArrayList<>();
        String sql = "SELECT * FROM orderItem WHERE oid = ? ORDER BY id DESC LIMIT ?,? ";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, oid);
            ps.setInt(2, start);
            ps.setInt(3, count);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                OrderItem bean = new OrderItem();
                int id = rs.getInt(1);
                int pid = rs.getInt("pid");
                int uid = rs.getInt("uid");
                int number = rs.getInt("number");
                Product product = new ProductDAO().get(pid);
                if(-1!=oid){
                    Order order= new OrderDAO().get(oid);
                    bean.setOrder(order);
                }
                User user = new UserDAO().get(uid);
                bean.setProduct(product);
                bean.setUser(user);
                bean.setNumber(number);
                bean.setId(id);
                beans.add(bean);
            }
            logger.info("根据订单查询订单物品信息失败");
        } catch (SQLException e) {
            logger.error("根据订单查询订单物品信息失败", e);
        }
        return beans;
    }

    public void fill(List<Order> os) {
        for (Order o : os) {
            List<OrderItem> ois = listByOrder(o.getId());
            float total = 0;
            int totalNumber = 0;
            for (OrderItem oi : ois) {
                // 计算订单的价格
                total += oi.getNumber() * oi.getProduct().getPromotePrice();
                totalNumber += oi.getNumber();
            }
            o.setTotal(total);
            o.setOrderItems(ois);
            o.setTotalNumber(totalNumber);
        }
    }

    public void fill(Order o) {
        List<OrderItem> ois = listByOrder(o.getId());
        float total = 0;
        for (OrderItem oi : ois) {
            total += oi.getNumber() * oi.getProduct().getPromotePrice();
        }
        o.setTotal(total);
        o.setOrderItems(ois);
    }

    public List<OrderItem> listByProduct(int pid) {
        return listByProduct(pid, 0, Short.MAX_VALUE);
    }

    public List<OrderItem> listByProduct(int pid, int start, int count) {
        List<OrderItem> beans = new ArrayList<>();
        String sql = "SELECT * FROM orderItem WHERE pid = ? ORDER BY id DESC LIMIT ?,? ";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, pid);
            ps.setInt(2, start);
            ps.setInt(3, count);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // 每次查询一个orderItem根据得到的oid查询Order
                // 并且设置关联关系
                OrderItem bean = new OrderItem();
                int id = rs.getInt(1);
                int uid = rs.getInt("uid");
                int oid = rs.getInt("oid");
                int number = rs.getInt("number");
                Product product = new ProductDAO().get(pid);
                if(-1 != oid){
                    Order order= new OrderDAO().get(oid);
                    bean.setOrder(order);
                }
                User user = new UserDAO().get(uid);
                bean.setProduct(product);
                bean.setUser(user);
                bean.setNumber(number);
                bean.setId(id);
                beans.add(bean);
            }
            logger.info("根据产品ID查找订单物品成功");
        } catch (SQLException e) {
            logger.error("根据产品ID查找订单物品失败", e);
        }
        return beans;
    }

    public int getSaleCount(int pid) {
        int total = 0;
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
            String sql = "SELECT SUM(number) FROM orderItem WHERE pid = " + pid;
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                total = rs.getInt(1);
            }
            logger.info("查询产品 {} 的总销量成功", pid);
        } catch (SQLException e) {
            logger.error("查询产品 {} 的总销量出错", pid);
        }
        return total;
    }
}
