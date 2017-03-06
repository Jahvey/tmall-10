package tmall.dao.impl;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tmall.bean.Category;

import static org.junit.Assert.*;

/**
 * 作者: wangxh
 * 创建日期: 17-2-26
 * 简介:
 */
public class CategoryDAOTest {

    private static Logger logger = LoggerFactory.getLogger(CategoryDAO.class);
    private CategoryDAO categoryDAO = new CategoryDAO();

    @Test
    public void getTotal() throws Exception {
        int total = categoryDAO.getTotal();
        logger.info("category total: {}", total);
        Assert.assertNotNull(categoryDAO.getTotal());
    }

    @Test
    public void add() throws Exception {
        Category category = new Category("电脑");
        Category category1 = new Category("电视");
        Category category2 = new Category("马桶");
        categoryDAO.add(category);
        categoryDAO.add(category1);
        categoryDAO.add(category2);
    }

    @Test
    public void update() throws Exception {
        Category category = new Category(9, "毒奶粉2");
        categoryDAO.update(category);
    }

    @Test
    public void deleteById() throws Exception {
        categoryDAO.deleteById(3);
    }

    @Test
    public void getById() throws Exception {
        Category category = categoryDAO.get(2);
        logger.info(category.toString());
    }

    @Test
    public void list() throws Exception {
        logger.info(categoryDAO.list().toString());
    }

    @Test
    public void list1() throws Exception {

    }

}