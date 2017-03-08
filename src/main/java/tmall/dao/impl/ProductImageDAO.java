package tmall.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tmall.bean.Product;
import tmall.bean.ProductImage;
import tmall.dao.IProductImageDAO;
import tmall.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者: wangxh
 * 创建日期: 17-3-2
 * 简介:
 */
public class ProductImageDAO implements IProductImageDAO {
    public static final String TYPE_SINGLE = "type_single";
    public static final String TYPE_DETAIL = "type_detail";
    private static final Logger logger = LoggerFactory.getLogger(ProductImage.class);

    @Override
    public int getTotal() {
        int total = 0;
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
            String sql = "SELECT COUNT(*) FROM productImage";
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                total = rs.getInt(1);
            }
            logger.info("查询图片数量成功, {}", total);
        } catch (SQLException e) {
            logger.error("查询图片数量出错", e);
        }
        return total;
    }

    @Override
    public void add(ProductImage productImage) {
        String sql = "INSERT INTO productImage VALUES (null,?,?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, productImage.getProduct().getId());
            ps.setString(2, productImage.getType());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                productImage.setId(id);
                logger.info("添加图片成功, ID: {}", productImage.getId());
            } else logger.warn("添加图片失败");
        } catch (SQLException e) {
            logger.error("添加图片失败, ID: {}", productImage.getId(), e);
        }
    }

    @Override
    public void update(ProductImage productImage) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void delete(int id) {
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
            String sql = "DELETE FROM productImage WHERE id = " + id;
            s.execute(sql);
        } catch (SQLException e) {
            logger.error("删除图片失败, ID: ", id, e);
        }
    }

    @Override
    public ProductImage get(int id) {
        ProductImage bean = new ProductImage();
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
            String sql = "SELECT * FROM productImage WHERE id = " + id;
            ResultSet rs = s.executeQuery(sql);
            if (rs.next()) {
                int pid = rs.getInt("pid");
                String type = rs.getString("type");
                Product product = new ProductDAO().get(pid);
                bean.setProduct(product);
                bean.setType(type);
                bean.setId(id);
                logger.info("获取图片成功, ID: ", id);
            } else logger.error("获取图片失败, ID: ", id);
        } catch (SQLException e) {
            logger.error("获取图片出错, ID: ", id, e);
        }
        return bean;
    }

    @Override
    public List<ProductImage> list(Product p, String type) {
        return list(p, type,0, Short.MAX_VALUE);
    }

    @Override
    public List<ProductImage> list(Product p, String type, int start, int count) {
        List<ProductImage> beans = new ArrayList<>();
        String sql = "SELECT * FROM productImage WHERE pid =? AND type =? ORDER BY id DESC LIMIT ?,? ";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, p.getId());
            ps.setString(2, type);
            ps.setInt(3, start);
            ps.setInt(4, count);
            ResultSet rs = ps.executeQuery();
            int n = 0;
            while (rs.next()) {
                ProductImage bean = new ProductImage();
                int id = rs.getInt(1);
                bean.setProduct(p);
                bean.setType(type);
                bean.setId(id);
                beans.add(bean);
                logger.info("查询到产品 {} 的第 {} 张图片, 类型: {}", p.getName(), ++n, type);
            }
        } catch (SQLException e) {
            logger.error("查询产品 {} 的所有 {} 类型图片出错", p.getName(), type, e);
        }
        return beans;
    }
}
