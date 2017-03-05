package tmall.dao.impl;

import org.junit.Test;
import tmall.bean.Category;
import tmall.bean.Product;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * 作者: wangxh
 * 创建日期: 17-3-3
 * 简介:
 */
public class ProductDAOTest {

    private ProductDAO productDAO = new ProductDAO();
    private CategoryDAO categoryDAO = new CategoryDAO();

    @Test
    public void getTotal() throws Exception {

    }

    @Test
    public void add() throws Exception {
        Product product = new Product();
        product.setName("Dell Inspiron14");
        product.setOrignalPrice(5000);
        product.setPromotePrice(4500);
        product.setReviewCount(10);
        product.setSubTitle("Dell Computer");
        product.setStock(10);
        product.setSaleCount(2);
        product.setCreateDate(new Date());
        product.setCategory(categoryDAO.get(1));
        productDAO.add(product);
    }

    @Test
    public void update() throws Exception {

    }

    @Test
    public void delete() throws Exception {

    }

    @Test
    public void get() throws Exception {

    }

    @Test
    public void list() throws Exception {

    }

    @Test
    public void list1() throws Exception {

    }

    @Test
    public void list2() throws Exception {

    }

    @Test
    public void list3() throws Exception {

    }

    @Test
    public void fill() throws Exception {

    }

    @Test
    public void fill1() throws Exception {

    }

    @Test
    public void fillByRow() throws Exception {

    }

    @Test
    public void setFirstProductImage() throws Exception {

    }

    @Test
    public void setSaleAndReviewNumber() throws Exception {

    }

    @Test
    public void setSaleAndReviewNumber1() throws Exception {

    }

    @Test
    public void search() throws Exception {

    }

}