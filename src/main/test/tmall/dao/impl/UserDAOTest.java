package tmall.dao.impl;

import org.junit.Test;
import tmall.bean.User;

import static org.junit.Assert.*;

/**
 * 作者: wangxh
 * 创建日期: 17-3-1
 * 简介:
 */
public class UserDAOTest {
    private UserDAO userDAO = new UserDAO();

    @Test
    public void getTotal() throws Exception {

    }

    @Test
    public void add() throws Exception {
        User user = new User("wangxh", "19940413");
        userDAO.add(user);
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
    public void isExist() throws Exception {

    }

    @Test
    public void get1() throws Exception {

    }

    @Test
    public void get2() throws Exception {

    }

}