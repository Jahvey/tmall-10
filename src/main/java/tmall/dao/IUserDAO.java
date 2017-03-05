package tmall.dao;

import tmall.bean.User;

import java.util.List;

/**
 * 作者: wangxh
 * 创建日期: 17-3-1
 * 简介:
 */
public interface IUserDAO {
    int getTotal();
    void add(User user);
    void update(User user);
    void delete(int id);
    User get(int id);
    User get(String name);
    User get(String name, String password);
    List<User> list();
    List<User> list(int start, int count);
    boolean isExist(String name);
}
