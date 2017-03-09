package tmall.service;

import tmall.bean.Product;
import tmall.bean.ProductImage;
import tmall.dao.impl.ProductDAO;
import tmall.dao.impl.ProductImageDAO;

import java.util.List;

/**
 * 作者: wangxh
 * 创建日期: 17-3-8
 * 简介:
 */
public class ProductImageService {

    private final ProductImageDAO productImageDAO = new ProductImageDAO();
    private final ProductDAO productDAO = new ProductDAO();

    public List<ProductImage> list(Product product, String type) {
        return productImageDAO.list(product, type);
    }

    public ProductImage get(int id) {
        return productImageDAO.get(id);
    }

    public void add(ProductImage productImage) {
        productImageDAO.add(productImage);
    }

    public void delete(int id) { productImageDAO.delete(id); }

    public List<ProductImage> list(int pid, String type) {
        Product product = productDAO.get(pid);
        return this.list(product, type);
    }
}
