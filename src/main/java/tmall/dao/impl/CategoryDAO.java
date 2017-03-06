package tmall.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tmall.bean.Category;
import tmall.dao.ICategoryDAO;
import tmall.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者: wangxh
 * 创建日期: 17-2-26
 * 简介:
 */
public class CategoryDAO implements ICategoryDAO {

    private static Logger logger = LoggerFactory.getLogger(CategoryDAO.class);

    @Override
    public int getTotal() {
        int total = 0;
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT COUNT(*) FROM tmall_me.category");
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) total = rs.getInt(1);
            logger.info("查询到条目总数 {} 条", total);
        } catch (SQLException e) {
            logger.error("获取条目总数失败", e);
        }
        return total;
    }

    @Override
    public void add(Category category) {
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "INSERT INTO tmall_me.category(id, name) VALUES (null, ?)",
                     PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            logger.info("插入条目表: {}", category.getName());
            ps.setString(1, category.getName());
            if (ps.executeUpdate() != 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {category.setId(rs.getInt(1));}
                }
                logger.info("{} 条目添加成功", category.getName());
            } else {logger.error("{} 条目添加失败", category.getName());}
        } catch (SQLException e) {
            logger.error("插入条目数据 {}:{} 出错", category.getId(), category.getName(), e);
        }
    }

    @Override
    public void update(Category category) {
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement("UPDATE tmall_me.category SET name = ? WHERE id = ?")) {
            ps.setString(1, category.getName());
            ps.setInt(2, category.getId());
            if (ps.executeUpdate() == 0) logger.error("{} 条目更新出错", category.getName());
            else logger.info("更新条目 {} 成功", category.getName());
        } catch (SQLException e) {
            logger.error("更新条目数据 {}:{} 出错", category.getId(), category.getName());
        }

    }

    @Override
    public void deleteById(int id) {
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement("DELETE FROM tmall_me.category WHERE id = ?")) {
            ps.setInt(1, id);
            if (ps.executeUpdate() != 0) logger.info("删除id为 {} 的条目成功", id);
            else logger.error("删除条目失败, id: {}", id);
        } catch (SQLException e) {
            logger.error("删除条目出错，id: {}", id);
        }
    }

    @Override
    public Category get(int id) {
        Category category = new Category();
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "SELECT category.name FROM category WHERE id = ?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString(1);
                    logger.info("查询到id为 {} 的条目: {}", id, name);
                    category.setId(id);
                    category.setName(name);
                } else logger.error("没有查询到id为 {} 的条目", id);
            }
        } catch (SQLException e) {
            logger.error("查询id为 {} 的条目出错", e);
        }
        return category;
    }

    @Override
    public List<Category> list() {
        return this.list(0, Short.MAX_VALUE);
    }

    @Override
    public List<Category> list(int start, int count) {
        List<Category> categories = new ArrayList<>();
        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareCall(
                     "SELECT category.id, category.name FROM category ORDER BY category.id DESC LIMIT ?, ?")) {
            ps.setInt(1, start);
            ps.setInt(2, count);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Category category = new Category(rs.getInt(1), rs.getString(2));
                    categories.add(category);
                }
            }
            logger.info("查询 {} - {} 的条目信息成功", start, count);
        } catch (SQLException e) {
            logger.error("查询条目列表 {} - {} 出错", start, count);
        }
        return categories;
    }
}
