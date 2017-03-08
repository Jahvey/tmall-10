package tmall.service;

import tmall.bean.Product;
import tmall.dao.impl.ProductDAO;

import java.util.List;

/**
 * 作者: wangxh
 * 创建日期: 17-3-8
 * 简介:
 */
public class ProductService {
    private final ProductDAO productDAO = new ProductDAO();

    public int getTotal(int cid) {
        return productDAO.getTotal(cid);
    }

    public Product get(int id) {
        return productDAO.get(id);
    }

    public void update(Product product) {
        productDAO.update(product);
    }

    public void delete(int id) {
        productDAO.delete(id);
    }

    public List<Product> list(int cid) {
        return productDAO.list(cid);
    }

    public void add(Product product) {
        productDAO.add(product);
    }
}
