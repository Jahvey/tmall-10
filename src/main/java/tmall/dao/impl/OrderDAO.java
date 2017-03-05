package tmall.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tmall.bean.Order;
import tmall.bean.User;
import tmall.dao.IOrderDAO;
import tmall.util.DBUtil;
import tmall.util.DateUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 作者: wangxh
 * 创建日期: 17-3-2
 * 简介:
 */
public class OrderDAO implements IOrderDAO {

    // 订单状态常量
    public static final String WAIT_PAY = "waitPay";
    public static final String WAIT_DELIVERY = "waitDelivery";
    public static final String WAIT_CONFIRM = "waitConfirm";
    public static final String WAIT_REVIEW = "waitReview";
    public static final String FINISH = "finish";
    public static final String DELETE = "delete";

    private static final Logger logger = LoggerFactory.getLogger(OrderDAO.class);

    @Override
    public int getTotal() {
        int total = 0;
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
            String sql = "SELECT COUNT(*) FROM order_";
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                total = rs.getInt(1);
            }
            logger.info("查询订单总数成功");
        } catch (SQLException e) {
            logger.error("查询订单总数失败", e);
        }
        return total;
    }

    @Override
    public void add(Order order) {
        String sql = "INSERT INTO order_ VALUES (null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, order.getOrderCode());
            ps.setString(2, order.getAddress());
            ps.setString(3, order.getPost());
            ps.setString(4, order.getReceiver());
            ps.setString(5, order.getMobile());
            ps.setString(6, order.getUserMessage());
            ps.setTimestamp(7,  DateUtil.dateToTimestamp(order.getCreateDate()));
            ps.setTimestamp(8,  DateUtil.dateToTimestamp(order.getPayDate()));
            ps.setTimestamp(9,  DateUtil.dateToTimestamp(order.getDeliveryDate()));
            ps.setTimestamp(10,  DateUtil.dateToTimestamp(order.getConfirmDate()));
            ps.setInt(11, order.getUser().getId());
            ps.setString(12, order.getStatus());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                order.setId(id);
                logger.error("插入订单 {} 成功", order.getId());
            }
            logger.error("插入订单 {} 失败", order.getId());
        } catch (SQLException e) {
            logger.error("插入订单 {} 失败", order.getId(), e);
        }

    }

    @Override
    public void update(Order order) {
        String sql = "UPDATE order_ SET address= ?, post=?, receiver=?, mobile=?, userMessage=?, createDate = ?," +
                "payDate =?, deliveryDate = ?, confirmDate = ? , orderCode =?, uid=?, status=? WHERE id = ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, order.getAddress());
            ps.setString(2, order.getPost());
            ps.setString(3, order.getReceiver());
            ps.setString(4, order.getMobile());
            ps.setString(5, order.getUserMessage());
            ps.setTimestamp(6, DateUtil.dateToTimestamp(order.getCreateDate()));
            ps.setTimestamp(7, DateUtil.dateToTimestamp(order.getPayDate()));
            ps.setTimestamp(8, DateUtil.dateToTimestamp(order.getDeliveryDate()));
            ps.setTimestamp(9, DateUtil.dateToTimestamp(order.getConfirmDate()));
            ps.setString(10, order.getOrderCode());
            ps.setInt(11, order.getUser().getId());
            ps.setString(12, order.getStatus());
            ps.setInt(13, order.getId());
            ps.execute();
            logger.info("更新订单 {} 成功", order.getId());
        } catch (SQLException e) {
            logger.error("更新订单 {} 失败", order.getId(), e);
        }
    }

    @Override
    public void delete(int id) {
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
            String sql = "DELETE FROM order_ WHERE id = " + id;
            s.execute(sql);
            logger.info("删除订单成功, ID: ", id);
        } catch (SQLException e) {
            logger.error("删除订单失败, ID: ", id);
        }
    }

    @Override
    public Order get(int id) {
        Order bean = new Order();
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
            String sql = "SELECT * FROM order_ WHERE id = " + id;
            ResultSet rs = s.executeQuery(sql);
            if (rs.next()) {
                String orderCode =rs.getString("orderCode");
                String address = rs.getString("address");
                String post = rs.getString("post");
                String receiver = rs.getString("receiver");
                String mobile = rs.getString("mobile");
                String userMessage = rs.getString("userMessage");
                String status = rs.getString("status");
                int uid =rs.getInt("uid");
                Date createDate = DateUtil.timeStampToDate( rs.getTimestamp("createDate"));
                Date payDate = DateUtil.timeStampToDate( rs.getTimestamp("payDate"));
                Date deliveryDate = DateUtil.timeStampToDate( rs.getTimestamp("deliveryDate"));
                Date confirmDate = DateUtil.timeStampToDate( rs.getTimestamp("confirmDate"));
                bean.setOrderCode(orderCode);
                bean.setAddress(address);
                bean.setPost(post);
                bean.setReceiver(receiver);
                bean.setMobile(mobile);
                bean.setUserMessage(userMessage);
                bean.setCreateDate(createDate);
                bean.setPayDate(payDate);
                bean.setDeliveryDate(deliveryDate);
                bean.setConfirmDate(confirmDate);
                User user = new UserDAO().get(uid);
                bean.setUser(user);
                bean.setStatus(status);
                bean.setId(id);
            }
            logger.info("查询订单 {} 成功", id);
        } catch (SQLException e) {
            logger.error("查询订单 {} 失败", id);
        }
        return bean;
    }

    @Override
    public List<Order> list(int start, int count) {
        List<Order> beans = new ArrayList<>();
        String sql = "SELECT * FROM order_ ORDER BY id DESC LIMIT ?,?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, start);
            ps.setInt(2, count);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order bean = new Order();
                String orderCode = rs.getString("orderCode");
                String address = rs.getString("address");
                String post = rs.getString("post");
                String receiver = rs.getString("receiver");
                String mobile = rs.getString("mobile");
                String userMessage = rs.getString("userMessage");
                String status = rs.getString("status");
                Date createDate = DateUtil.timeStampToDate( rs.getTimestamp("createDate"));
                Date payDate = DateUtil.timeStampToDate( rs.getTimestamp("payDate"));
                Date deliveryDate = DateUtil.timeStampToDate( rs.getTimestamp("deliveryDate"));
                Date confirmDate = DateUtil.timeStampToDate( rs.getTimestamp("confirmDate"));
                int uid =rs.getInt("uid");
                int id = rs.getInt("id");
                bean.setId(id);
                bean.setOrderCode(orderCode);
                bean.setAddress(address);
                bean.setPost(post);
                bean.setReceiver(receiver);
                bean.setMobile(mobile);
                bean.setUserMessage(userMessage);
                bean.setCreateDate(createDate);
                bean.setPayDate(payDate);
                bean.setDeliveryDate(deliveryDate);
                bean.setConfirmDate(confirmDate);
                // 查询根据uid查询出用户
                User user = new UserDAO().get(uid);
                bean.setUser(user);
                bean.setStatus(status);
                beans.add(bean);
            }
            logger.info("查询id为 {} - {} 的订单成功", start, count);
        } catch (SQLException e) {
            logger.error("查询id为 {} - {} 的订单失败", start, count, e);
        }
        return beans;
    }

    @Override
    public List<Order> list(int uid, String excludedStatus) {
        return list(uid,excludedStatus,0, Short.MAX_VALUE);
    }

    @Override
    public List<Order> list(int uid, String excludedStatus, int start, int count) {
        List<Order> beans = new ArrayList<>();
        String sql = "SELECT * FROM order_ WHERE uid = ? AND status != ? ORDER BY id DESC LIMIT ?,?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, uid);
            ps.setString(2, excludedStatus);
            ps.setInt(3, start);
            ps.setInt(4, count);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order bean = new Order();
                String orderCode =rs.getString("orderCode");
                String address = rs.getString("address");
                String post = rs.getString("post");
                String receiver = rs.getString("receiver");
                String mobile = rs.getString("mobile");
                String userMessage = rs.getString("userMessage");
                String status = rs.getString("status");
                Date createDate = DateUtil.timeStampToDate( rs.getTimestamp("createDate"));
                Date payDate = DateUtil.timeStampToDate( rs.getTimestamp("payDate"));
                Date deliveryDate = DateUtil.timeStampToDate( rs.getTimestamp("deliveryDate"));
                Date confirmDate = DateUtil.timeStampToDate( rs.getTimestamp("confirmDate"));
                int id = rs.getInt("id");
                bean.setId(id);
                bean.setOrderCode(orderCode);
                bean.setAddress(address);
                bean.setPost(post);
                bean.setReceiver(receiver);
                bean.setMobile(mobile);
                bean.setUserMessage(userMessage);
                bean.setCreateDate(createDate);
                bean.setPayDate(payDate);
                bean.setDeliveryDate(deliveryDate);
                bean.setConfirmDate(confirmDate);
                User user = new UserDAO().get(uid);
                bean.setStatus(status);
                bean.setUser(user);
                beans.add(bean);
            }
            logger.info("查询用户 {} 的订单成功, 排除 {} 状态的订单", uid, excludedStatus);
        } catch (SQLException e) {
            logger.error("查询用户 {} 的订单失败", uid, e);
        }
        return beans;
    }
}
