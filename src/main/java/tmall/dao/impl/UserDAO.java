package tmall.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tmall.bean.User;
import tmall.dao.IUserDAO;
import tmall.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者: wangxh
 * 创建日期: 17-3-1
 * 简介:
 */
public class UserDAO implements IUserDAO {

    private static Logger logger = LoggerFactory.getLogger(UserDAO.class);

    @Override
    public int getTotal() {
        int total = 0;
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
            String sql = "SELECT COUNT(*) FROM user";
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                total = rs.getInt(1);
            }
            logger.info("获取用户总数成功: {}" + total);
        } catch (SQLException e) {
            logger.error("获取用户数失败", e);
        }
        return total;
    }

    @Override
    public void add(User bean) {
        String sql = "INSERT INTO user VALUES (null ,? ,?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, bean.getName());
            ps.setString(2, bean.getPassword());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                bean.setId(id);
                logger.info("添加用户 {}:{} 成功", bean.getId(), bean.getName());
            } else {
                logger.warn("添加用户 {} 失败", bean.getName());
            }
        } catch (SQLException e) {
            logger.error("添加用户 {} 失败", bean.getName() , e);
        }
    }

    @Override
    public void update(User bean) {
        String sql = "UPDATE user SET name= ? , password = ? WHERE id = ? ";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, bean.getName());
            ps.setString(2, bean.getPassword());
            ps.setInt(3, bean.getId());
            ps.execute();
            logger.info("更新用户 {} 成功", bean.getName());
        } catch (SQLException e) {
            logger.error("更新用户 {} 失败", bean.getName(), e);
        }

    }

    @Override
    public void delete(int id) {
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
            String sql = "DELETE FROM user WHERE id = " + id;
            s.execute(sql);
            logger.info("删除用户成功, ID: ", id);
        } catch (SQLException e) {
            logger.error("删除用户失败, ID: ", id, e);
        }
    }

    @Override
    public User get(int id) {
        User bean = null;
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
            String sql = "SELECT * FROM user WHERE id = " + id;
            ResultSet rs = s.executeQuery(sql);
            if (rs.next()) {
                bean = new User();
                String name = rs.getString("name");
                bean.setName(name);
                String password = rs.getString("password");
                bean.setPassword(password);
                bean.setId(id);
            }
            if (bean != null) {
                logger.info("获取用户 {}:{} 成功", id, bean.getName());
            } else logger.info("查询用户 {} 出错", id);
        } catch (SQLException e) {
            logger.error("获取用户 {} 失败", id, e);
        }
        return bean;
    }

    @Override
    public List<User> list() {
        return list(0, Short.MAX_VALUE);
    }

    @Override
    public List<User> list(int start, int count) {
        List<User> beans = new ArrayList<>();
        String sql = "SELECT * FROM user ORDER BY id DESC LIMIT ?,? ";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, start);
            ps.setInt(2, count);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User bean = new User();
                int id = rs.getInt(1);
                String name = rs.getString("name");
                bean.setName(name);
                String password = rs.getString("password");
                bean.setPassword(password);

                bean.setId(id);
                beans.add(bean);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return beans;
    }

    @Override
    public boolean isExist(String name) {
        User user = get(name);
        return user != null;
    }

    @Override
    public User get(String name) {
        User bean = null;
        String sql = "SELECT * FROM user WHERE name = ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, name);
            ResultSet rs =ps.executeQuery();
            if (rs.next()) {
                bean = new User();
                int id = rs.getInt("id");
                bean.setName(name);
                String password = rs.getString("password");
                bean.setPassword(password);
                bean.setId(id);
                logger.info("查询用户 {} 成功", name);
            } else {
                logger.warn("没有查询到 {} 用户", name);
            }
        } catch (SQLException e) {
            logger.error("查询用户 {} 出错", name, e);
        }
        return bean;
    }

    @Override
    public User get(String name, String password) {
        User bean = null;
        String sql = "SELECT * FROM user WHERE name = ? and password= ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, password);
            ResultSet rs =ps.executeQuery();
            if (rs.next()) {
                bean = new User();
                int id = rs.getInt("id");
                bean.setName(name);
                bean.setPassword(password);
                bean.setId(id);
            }
            logger.info("查询用户 {} 成功", name);
        } catch (SQLException e) {
            logger.error("查询用户 {} 出错", name, e);
        }
        return bean;
    }
}
