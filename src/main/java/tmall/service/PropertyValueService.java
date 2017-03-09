package tmall.service;

import tmall.bean.PropertyValue;
import tmall.dao.impl.PropertyValueDAO;

import java.util.List;

/**
 * 作者: wangxh
 * 创建日期: 17-3-9
 * 简介:
 */
public class PropertyValueService {
    private final PropertyValueDAO propertyValueDAO = new PropertyValueDAO();

    public List<PropertyValue> list(int pid) {
        return propertyValueDAO.list(pid);
    }

    public void update(PropertyValue propertyValue) {
        propertyValueDAO.update(propertyValue);
    }

    public PropertyValue get(int id) {
        return propertyValueDAO.get(id);
    }
}
