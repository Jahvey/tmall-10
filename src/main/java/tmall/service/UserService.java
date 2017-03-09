package tmall.service;

import tmall.bean.User;
import tmall.dao.impl.UserDAO;

import java.util.List;

/**
 * 作者: wangxh
 * 创建日期: 17-3-9
 * 简介:
 */
public class UserService {
    private final UserDAO userDAO = new UserDAO();

    public User get(int id) {
        return userDAO.get(id);
    }

    public int getTotal() {
        return userDAO.getTotal();
    }

    public List<User> list() {
        return userDAO.list();
    }

}
