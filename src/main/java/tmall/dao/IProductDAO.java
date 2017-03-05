package tmall.dao;

import tmall.bean.Category;
import tmall.bean.Product;

import java.util.List;

/**
 * 作者: wangxh
 * 创建日期: 17-3-2
 * 简介: 产品表DAO映射接口
 */
public interface IProductDAO {
    /**
     * 查询某个分类下所有产品总数
     * @param cid 分类ID
     * @return 产品总数
     */
    int getTotal(int cid);
    void add(Product product);
    void update(Product product);
    void delete(int id);
    Product get(int id);
    List<Product> list();

    /**
     * 查询某个分类下所有产品
     * @param cid 分类ID
     * @return 产品列表
     */
    List<Product> list(int cid);
    List<Product> list(int cid, int start, int count);
    List<Product> list(int start, int count);

    /**
     * 为某个分类填充产品信息, (建立反向对应关系)
     * @param c 分类
     */
    void fill(Category c);
    void fill(List<Category> cs);

    /**
     * 为多个分类设置productByROW属性
     * @param cs 分类集合
     */
    void fillByRow(List<Category> cs);

    /**
     * 设置主要图片
     * @param p 图片对象
     */
    void setFirstProductImage(Product p);

    /**
     * 设置销售和评价数量
     * @param p 产品对象
     */
    void setSaleAndReviewNumber(Product p);
    void setSaleAndReviewNumber(List<Product> products);

    /**
     * 根据关键字搜索产品
     * @param keyword 关键字
     * @param start 开始数量
     * @param count 搜索条数
     * @return List<Product> 产品集合
     */
    List<Product> search(String keyword, int start, int count);
}
