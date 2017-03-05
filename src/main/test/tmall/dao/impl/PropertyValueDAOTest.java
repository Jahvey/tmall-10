package tmall.dao.impl;

import org.junit.Test;
import tmall.bean.PropertyValue;

import static org.junit.Assert.*;

/**
 * 作者: wangxh
 * 创建日期: 17-3-4
 * 简介:
 */
public class PropertyValueDAOTest {

    private PropertyValueDAO propertyValueDAO = new PropertyValueDAO();
    private PropertyDAO propertyDAO = new PropertyDAO();
    private ProductDAO productDAO = new ProductDAO();


    @Test
    public void getTotal() throws Exception {
        System.out.println(propertyValueDAO.getTotal());
    }

    @Test
    public void add() throws Exception {
        PropertyValue propertyValue = new PropertyValue();
        propertyValue.setValue("AMD R7 M265");
        propertyValue.setProduct(productDAO.get(3));
        propertyValue.setProperty(propertyDAO.get(2));
        propertyValueDAO.add(propertyValue);
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
    public void get1() throws Exception {

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
    public void init() throws Exception {

    }

}