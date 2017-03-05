package tmall.dao;

import tmall.bean.Product;
import tmall.bean.ProductImage;

import java.util.List;

/**
 * 作者: wangxh
 * 创建日期: 17-3-2
 * 简介:
 */
public interface IProductImageDAO {
    int getTotal();
    void add(ProductImage productImage);
    void update(ProductImage productImage);
    void delete(int id);
    ProductImage get(int id);

    /**
     * 查询某个产品对应的某个类型的所有图片
     * @param p 产品
     * @param type 类型
     * @return List<ProductImage>
     */
    List<ProductImage> list(Product p, String type);

    List<ProductImage> list(Product p, String type, int start, int count);
}
