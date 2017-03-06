package tmall.service;

import tmall.bean.Property;
import tmall.dao.impl.PropertyDAO;

import java.util.List;

/**
 * 作者: wangxh
 * 创建日期: 17-3-6
 * 简介:
 */
public class PropertyService {
    private final PropertyDAO propertyDAO = new PropertyDAO();

    public List<Property> list(int cid, int start, int count) {
        return propertyDAO.list(cid, start, count);
    }

    public int getTotal(int cid) {
        return propertyDAO.getTotal(cid);
    }

    public Property get(int id) {
        return propertyDAO.get(id);
    }

    public void add(Property property) {
        propertyDAO.add(property);
    }

    public void update(Property property) {
        propertyDAO.update(property);
    }

    public void delete(int id) {
        propertyDAO.delete(id);
    }
}
