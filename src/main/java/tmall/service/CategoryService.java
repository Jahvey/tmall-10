package tmall.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tmall.bean.Category;
import tmall.dao.DAOFactory;
import tmall.dao.ICategoryDAO;
import tmall.util.ImageUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

    public Category get(int id) {
        return categoryDAO.get(id);
    }

    public void add(Category category) {
        this.categoryDAO.add(category);
    }

    public void update(Category category) {
        this.categoryDAO.update(category);
    }

    public void delete(int id) {
        this.categoryDAO.deleteById(id);
    }

    public List<Category> list(int start, int count) {
        return categoryDAO.list(start, count);
    }


}
