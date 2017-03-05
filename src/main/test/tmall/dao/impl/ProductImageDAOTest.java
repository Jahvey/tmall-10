package tmall.dao.impl;

import org.junit.Test;
import tmall.bean.ProductImage;

import static org.junit.Assert.*;

/**
 * 作者: wangxh
 * 创建日期: 17-3-3
 * 简介:
 */
public class ProductImageDAOTest {

    private ProductDAO productDAO = new ProductDAO();
    private ProductImageDAO productImageDAO = new ProductImageDAO();

    @Test
    public void getTotal() throws Exception {

    }

    @Test
    public void add() throws Exception {
        ProductImage productImage = new ProductImage();
        productImage.setProduct(productDAO.get(3));
        productImage.setType(ProductImageDAO.TYPE_SINGLE);
        productImageDAO.add(productImage);
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

}