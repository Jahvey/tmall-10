package tmall.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tmall.bean.Category;
import tmall.bean.Property;
import tmall.dao.IPropertyDAO;
import tmall.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者: wangxh
 * 创建日期: 17-3-1
 * 简介:
 */
public class PropertyDAO implements IPropertyDAO {

    private static Logger logger = LoggerFactory.getLogger(PropertyDAO.class);

    public int getTotal(int cid) {
        int total = 0;
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
            String sql = "SELECT COUNT(*) FROM property WHERE cid =" + cid;
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.error("查询总数出错", e);
        }
        return total;
    }

    public void add(Property bean) {
        String sql = "INSERT INTO property VALUES (null,?,?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, bean.getCategory().getId());
            ps.setString(2, bean.getName());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                bean.setId(id);
            }
            logger.info("插入属性 {} 成功", bean.getName());
        } catch (SQLException e) {
            logger.error("添加属性 {} 出错", bean.getName(), e);
        }
    }

    public void update(Property bean) {
        String sql = "UPDATE property SET cid= ?, name=? WHERE id = ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, bean.getCategory().getId());
            ps.setString(2, bean.getName());
            ps.setInt(3, bean.getId());
            ps.execute();
            logger.info("更新属性 {} 成功", bean.getName());
        } catch (SQLException e) {
            logger.error("更新属性成功, {}", bean.getName(), e);
        }
    }

    public void delete(int id) {
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
            String sql = "DELETE FROM property WHERE id = " + id;
            s.execute(sql);
            logger.info("删除属性成功, id: {}", id);
        } catch (SQLException e) {
            logger.error("删除属性失败, id: {}", id, e);
        }
    }

    public Property get(String name, int cid) {
        Property bean =null;
        String sql = "SELECT * FROM property WHERE name = ? AND cid = ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setInt(2, cid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                bean = new Property();
                bean.setName(name);
                Category category = new CategoryDAO().get(cid);
                bean.setCategory(category);
                bean.setId(id);
            }
            logger.info("查询分类 {} 下的 {} 属性成功", cid, name);
        } catch (SQLException e) {
            logger.info("查询分类 {} 下的 {} 属性失败", cid, name, e);
        }
        return bean;
    }

    public Property get(int id) {
        Property bean = new Property();
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
            String sql = "SELECT * FROM property WHERE id = " + id;
            ResultSet rs = s.executeQuery(sql);
            if (rs.next()) {
                String name = rs.getString("name");
                int cid = rs.getInt("cid");
                bean.setCid(cid);
                bean.setName(name);
                Category category = new CategoryDAO().get(cid);
                bean.setCategory(category);
                bean.setId(id);
                logger.info("查询id为 {} 的属性成功", id);
            } else logger.warn("没有查询到id为 {} 的属性", id);
        } catch (SQLException e) {
            logger.error("查询id为 {} 的属性失败", id, e);
        }
        return bean;
    }

    public List<Property> list(int cid) {
        return list(cid, 0, Short.MAX_VALUE);
    }

    public List<Property> list(int cid, int start, int count) {
        List<Property> beans = new ArrayList<>();
        String sql = "SELECT * FROM property WHERE cid = ? ORDER BY id DESC LIMIT ?,? ";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, cid);
            ps.setInt(2, start);
            ps.setInt(3, count);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Property bean = new Property();
                int id = rs.getInt(1);
                String name = rs.getString("name");
                bean.setName(name);
                Category category = new CategoryDAO().get(id);
                bean.setCategory(category);
                bean.setId(id);
                beans.add(bean);
            }
            logger.error("查询分类id为 {} 下的属性列表成功", cid);
        } catch (SQLException e) {
            logger.error("查询分类 {} 下的属性列表失败", cid, e);
        }
        return beans;
    }
}
