package tmall.dao;

import tmall.bean.Category;

import java.util.List;

/**
 * 作者: wangxh
 * 创建日期: 17-2-26
 * 简介:
 */
public interface ICategoryDAO {
    /**
     * 获取分类总数
     * @return
     */
    int getTotal();

    /**
     * 添加一条分类
     * @param category
     */
    void add(Category category);

    /**
     * 更新分类
     * @param category
     */
    void update(Category category);

    /**
     * 根据id删除一条分类
     * @param id
     */
    void deleteById(int id);

    /**
     * 根据id查找一条分类
     * @param id
     * @return
     */
    Category get(int id);

    /**
     * 返回所有条目信息
     * @return
     */
    List<Category> list();

    /**
     * 返回start - end之间的条目信息
     * @param start
     * @param count
     * @return
     */
    List<Category> list(int start, int count);
}
