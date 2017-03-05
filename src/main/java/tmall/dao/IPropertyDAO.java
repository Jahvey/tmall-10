package tmall.dao;

import tmall.bean.Property;

import java.util.List;

/**
 * 作者: wangxh
 * 创建日期: 17-3-1
 * 简介:
 */
public interface IPropertyDAO {

    /**
     * 查询某个分类下对应的属性总数
     * @param cid
     * @return
     */
    int getTotal(int cid);
    void add(Property property);
    void update(Property property);
    void delete(int id);
    Property get(int id);
    Property get(String name, int cid);

    /**
     * 查询某个分类下的所有属性
     * @param cid
     * @return
     */
    List<Property> list(int cid);

    /**
     * 查询某个分类下的属性
     * @param cid
     * @param start
     * @param count
     * @return
     */
    List<Property> list(int cid, int start, int count);
}
