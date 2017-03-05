package tmall.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tmall.bean.Category;
import tmall.dao.DAOFactory;
import tmall.dao.ICategoryDAO;

import java.util.List;

/**
 * 作者: wangxh
 * 创建日期: 17-3-5
 * 简介:
 */
public class CategoryService {
    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);
    private ICategoryDAO categoryDAO = DAOFactory.categoryDAO();

    public int getTotal() {
        return categoryDAO.getTotal();
    }

    public List<Category> list(int start, int count) {
        return categoryDAO.list(start, count);
    }
}
