package tmall.dao.impl;

import org.junit.Test;
import tmall.bean.Review;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * 作者: wangxh
 * 创建日期: 17-3-4
 * 简介:
 */
public class ReviewDAOTest {

    private ProductDAO productDAO = new ProductDAO();
    private ReviewDAO reviewDAO = new ReviewDAO();
    private UserDAO userDAO = new UserDAO();

    @Test
    public void getTotal() throws Exception {

    }

    @Test
    public void getTotal1() throws Exception {

    }

    @Test
    public void getCount() throws Exception {

    }

    @Test
    public void add() throws Exception {
        Review review = new Review();
        review.setProduct(productDAO.get(3));
        review.setUser(userDAO.get("wangxh"));
        review.setContent("nice computer");
        review.setCreateDate(new Date());
        reviewDAO.add(review);
    }

    @Test
    public void update() throws Exception {
    }

    @Test
    public void delete() throws Exception {

    }

    @Test
    public void list() throws Exception {

    }

    @Test
    public void list1() throws Exception {

    }

    @Test
    public void isExist() throws Exception {

    }

}