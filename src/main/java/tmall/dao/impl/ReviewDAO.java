package tmall.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tmall.bean.Product;
import tmall.bean.Review;
import tmall.bean.User;
import tmall.dao.IReviewDAO;
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
public class ReviewDAO implements IReviewDAO {

    private static final Logger logger = LoggerFactory.getLogger(ReviewDAO.class);

    @Override
    public int getTotal() {
        int total = 0;
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
            String sql = "SELECT COUNT(*) FROM review";
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                total = rs.getInt(1);
            }
            logger.info("查询评论总数成功");
        } catch (SQLException e) {
            logger.error("查询评论总数失败", e);
        }
        return total;
    }

    @Override
    public int getTotal(int pid) {
        int total = 0;
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
            String sql = " count(*) from Review where pid = " + pid;
            ResultSet rs = s.executeQuery(sql);
            if (rs.next()) {
                total = rs.getInt(1);
            }
            logger.info("获取产品 {} 下的所有文章成功", pid);
        } catch (SQLException e) {
            logger.error("获取产品 {} 下的所有文章失败", pid, e);
        }
        return total;
    }

    @Override
    public int getCount(int pid) {
        String sql = "SELECT COUNT(*) FROM review WHERE pid = ? ";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, pid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                logger.info("查询 {} 下的评论成功", pid);
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.error("查询 {} 下的评论失败", pid, e);
        }
        return 0;
    }

    @Override
    public void add(Review review) {
        String sql = "INSERT INTO review VALUES (null, ?, ?, ?, ?)";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql,
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, review.getContent());
            ps.setInt(2, review.getUser().getId());
            ps.setInt(3, review.getProduct().getId());
            ps.setTimestamp(4, DateUtil.dateToTimestamp(review.getCreateDate()));
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                review.setId(id);
            }
            logger.info("添加评论成功");
        } catch (SQLException e) {
            logger.error("添加评论失败", e);
        }

    }

    @Override
    public void update(Review review) {
        String sql = "UPDATE review SET content= ?, uid=?, pid=? , createDate = ? WHERE id = ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, review.getContent());
            ps.setInt(2, review.getUser().getId());
            ps.setInt(3, review.getProduct().getId());
            ps.setTimestamp(4, DateUtil.dateToTimestamp( review.getCreateDate()));
            ps.setInt(5, review.getId());
            ps.execute();
            logger.info("更新评论成功");
        } catch (SQLException e) {
            logger.error("更新删除评论失败", e);
        }
    }

    @Override
    public void delete(int id) {
        try (Connection c = DBUtil.getConnection(); Statement s = c.createStatement()) {
            String sql = "DELETE FROM review WHERE id = " + id;
            s.execute(sql);
            logger.info("删除文章成功, ID: {}", id);
        } catch (SQLException e) {
            logger.error("删除文章失败, ID: {}", id, e);
        }
    }

    @Override
    public List<Review> list(int pid) {
        return list(pid, 0, Short.MAX_VALUE);
    }

    @Override
    public List<Review> list(int pid, int start, int count) {
        List<Review> beans = new ArrayList<>();
        String sql = "SELECT * FROM review WHERE pid = ? ORDER BY id DESC LIMIT ?,? ";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, pid);
            ps.setInt(2, start);
            ps.setInt(3, count);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Review bean = new Review();
                int id = rs.getInt(1);
                int uid = rs.getInt("uid");
                Date createDate = DateUtil.timeStampToDate(rs.getTimestamp("createDate"));
                String content = rs.getString("content");
                Product product = new ProductDAO().get(pid);
                User user = new UserDAO().get(uid);
                bean.setContent(content);
                bean.setCreateDate(createDate);
                bean.setId(id);
                bean.setProduct(product);
                bean.setUser(user);
                beans.add(bean);
            }
            logger.info("查询产品 {} 下的所有评论成功", pid);
        } catch (SQLException e) {
            logger.error("查询产品 {} 下的所有评论失败", pid, e);
        }
        return beans;
    }

    @Override
    public boolean isExist(int pid, String content) {
        String sql = "SELECT * FROM review WHERE content = ? AND pid = ?";
        try (Connection c = DBUtil.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, content);
            ps.setInt(2, pid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            logger.error("查询 pid: {} 是否存在出错", e);
        }
        return false;
    }
}
