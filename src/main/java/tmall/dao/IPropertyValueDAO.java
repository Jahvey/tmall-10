package tmall.dao;

import tmall.bean.Product;
import tmall.bean.PropertyValue;

import java.util.List;

/**
 * 作者: wangxh
 * 创建日期: 17-3-4
 * 简介:
 */
public interface IPropertyValueDAO {

    int getTotal();

    void add(PropertyValue propertyValue);

    void update(PropertyValue propertyValue);

    void delete(int id);

    PropertyValue get(int id);

    PropertyValue get(int ptid, int pid);

    List<PropertyValue> list();

    /**
     * 查询某个产品下所有的属性值
     * @param pid 产品id
     * @return
     */
    List<PropertyValue> list(int pid);

    List<PropertyValue> list(int start, int count);

    /**
     * 将一件产品下的所有属性关系初始化
     * @param product 产品bean
     */
    void init(Product product);

}
