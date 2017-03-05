package tmall.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tmall.bean.Product;
import tmall.bean.Property;
import tmall.bean.PropertyValue;
import tmall.dao.IPropertyValueDAO;
import tmall.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者: wangxh
 * 创建日期: 17-3-4
 * 简介:
 */
public class PropertyValueDAO implements IPropertyValueDAO {

    private static final Logger logger = LoggerFactory.getLogger(PropertyValueDAO.class);

    @Override
    public int getTotal() {
        int total = 0;
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
            String sql = "SELECT COUNT(*) FROM propertyValue";
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                total = rs.getInt(1);
            }
            logger.info("查询到propertyValue {} 条记录", total);
        } catch (SQLException e) {
            logger.error("查询属性值总数出错", e);
        }
        return total;
    }

    @Override
    public void add(PropertyValue propertyValue) {
        String sql = "INSERT INTO propertyValue VALUES (null,?,?,?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            int productId = propertyValue.getProduct().getId();
            int propertyId = propertyValue.getProperty().getId();
            ps.setInt(1, productId);
            ps.setInt(2, propertyId);
            ps.setString(3, propertyValue.getValue());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                propertyValue.setId(id);
                logger.info("为商品 {} 添加属性 {} 成功", productId, propertyValue);
            } else logger.warn("为商品 {} 添加属性 {} 失败", productId, propertyValue);
        } catch (SQLException e) {
            logger.error("添加属性 {} 出错", propertyValue, e);
        }
    }

    @Override
    public void update(PropertyValue propertyValue) {
        String sql = "UPDATE propertyValue SET pid= ?, ptid=?, value=?  WHERE id = ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, propertyValue.getProduct().getId());
            ps.setInt(2, propertyValue.getProperty().getId());
            ps.setString(3, propertyValue.getValue());
            ps.setInt(4, propertyValue.getId());
            ps.execute();
            logger.info("更新propertyValue成功");
        } catch (SQLException e) {
            logger.error("更新propertyValue失败", e);
        }
    }

    @Override
    public void delete(int id) {
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {
            String sql = "DELETE FROM propertyValue WHERE id = " + id;
            s.execute(sql);
            logger.info("删除属性值成功, ID", id);
        } catch (SQLException e) {
            logger.error("删除属性值失败, ID", id, e);
        }
    }

    @Override
    public PropertyValue get(int id) {
        PropertyValue bean = new PropertyValue();
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
            String sql = "SELECT * FROM propertyValue WHERE id = " + id;
            ResultSet rs = s.executeQuery(sql);
            if (rs.next()) {
                int pid = rs.getInt("pid");
                int ptid = rs.getInt("ptid");
                String value = rs.getString("value");
                Product product = new ProductDAO().get(pid);
                Property property = new PropertyDAO().get(ptid);
                bean.setProduct(product);
                bean.setProperty(property);
                bean.setValue(value);
                bean.setId(id);
                logger.info("查询id为 {} 的属性成功", id);
            } else logger.warn("查询id为 {} 的属性失败", id);
        } catch (SQLException e) {
            logger.error("查询id为 {} 的属性失败", id, e);
        }
        return bean;
    }

    @Override
    public PropertyValue get(int ptid, int pid) {
        PropertyValue bean = null;
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement();) {
            String sql = "select * from PropertyValue where ptid = " + ptid + " and pid = " + pid;
            ResultSet rs = s.executeQuery(sql);
            if (rs.next()) {
                bean= new PropertyValue();
                int id = rs.getInt("id");
                String value = rs.getString("value");
                Product product = new ProductDAO().get(pid);
                Property property = new PropertyDAO().get(ptid);
                bean.setProduct(product);
                bean.setProperty(property);
                bean.setValue(value);
                bean.setId(id);
                logger.info("查询属性为属性为 {}, 产品为 {} 的属性值成功", ptid, pid);
            } else {
                logger.warn("查询属性为属性为 {}, 产品为 {} 的属性值失败", ptid, pid);
            }
        } catch (SQLException e) {
            logger.error("查询属性为属性为 {}, 产品为 {} 的属性值错误", ptid, pid, e);
        }
        return bean;
    }

    @Override
    public List<PropertyValue> list() {
        return list(0, Short.MAX_VALUE);
    }

    @Override
    public List<PropertyValue> list(int pid) {
        List<PropertyValue> beans = new ArrayList<>();
        String sql = "SELECT * FROM propertyValue WHERE pid = ? ORDER BY ptid DESC";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, pid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PropertyValue bean = new PropertyValue();
                int id = rs.getInt(1);
                int ptid = rs.getInt("ptid");
                String value = rs.getString("value");
                Product product = new ProductDAO().get(pid);
                Property property = new PropertyDAO().get(ptid);
                bean.setProduct(product);
                bean.setProperty(property);
                bean.setValue(value);
                bean.setId(id);
                beans.add(bean);
            }
            logger.info("查询 {} 下的所有属性值成功", pid);
        } catch (SQLException e) {
            logger.error("查询 {} 下的所有属性值失败", pid, e);
        }
        return beans;
    }

    @Override
    public List<PropertyValue> list(int start, int count) {
        List<PropertyValue> beans = new ArrayList<>();
        String sql = "SELECT * FROM propertyValue ORDER BY id DESC LIMIT ?,?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, start);
            ps.setInt(2, count);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PropertyValue bean = new PropertyValue();
                int id = rs.getInt(1);
                int pid = rs.getInt("pid");
                int ptid = rs.getInt("ptid");
                String value = rs.getString("value");
                Product product = new ProductDAO().get(pid);
                Property property = new PropertyDAO().get(ptid);
                bean.setProduct(product);
                bean.setProperty(property);
                bean.setValue(value);
                bean.setId(id);
                beans.add(bean);
            }
            logger.info("查询id为 {} - {} 的属性成功");
        } catch (SQLException e) {
            logger.error("查询id为 {} - {} 的属性失败", e);
        }
        return beans;
    }

    @Override
    public void init(Product product) {
        // 列出分类 cid 下的所有属性
        List<Property> pts = new PropertyDAO().list(product.getCategory().getId());
        for (Property pt : pts) {
            // 根据属性id与产品id查到属性
            PropertyValue pv = get(pt.getId(), product.getId());
            if(null == pv) {
                pv = new PropertyValue();
                pv.setProduct(product);
                pv.setProperty(pt);
                this.add(pv);
            }
        }
    }
}
