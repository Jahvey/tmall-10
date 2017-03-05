package tmall.dao.impl;

import org.junit.Assert;
import org.junit.Test;
import tmall.bean.Category;
import tmall.bean.Property;

/**
 * 作者: wangxh
 * 创建日期: 17-3-1
 * 简介:
 */
public class PropertyDAOTest {

    private PropertyDAO propertyDAO = new PropertyDAO();

    @Test
    public void getTotal() throws Exception {
        Assert.assertEquals(propertyDAO.getTotal(1), 3);
    }

    @Test
    public void add() throws Exception {
        Category category = new Category(1, "电脑");
        Category category1 = new Category(2, "电视");
        Category category2 = new Category(3, "马桶");
        propertyDAO.add(new Property("CPU", category));
        propertyDAO.add(new Property("显卡", category));
        propertyDAO.add(new Property("硬盘", category));
        propertyDAO.add(new Property("屏幕尺寸", category1));
        propertyDAO.add(new Property("内置系统", category1));
        propertyDAO.add(new Property("冲水能力", category2));
        propertyDAO.add(new Property("舒适度", category2));
    }

    @Test
    public void update() throws Exception {
        Property property = new Property();
        property.setId(1);
        property.setCid(1);
        property.setName("CPU");
        property.setCategory(new Category(1, "电脑"));
        propertyDAO.update(property);
    }

    @Test
    public void delete() throws Exception {
    }

    @Test
    public void get() throws Exception {
        System.out.println(propertyDAO.get(2));
    }

    @Test
    public void list() throws Exception {
        System.out.println(propertyDAO.list(1));
    }

    @Test
    public void list1() throws Exception {
        System.out.println(propertyDAO.list(1, 0, 1));

    }

}