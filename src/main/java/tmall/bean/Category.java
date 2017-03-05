package tmall.bean;

import java.util.List;

/**
 * 作者: wangxh
 * 创建日期: 17-2-26
 * 简介: 产品分类bean
 */
public class Category {

    public Category() {}

    public Category(String name) {
        this.name = name;
    }

    public Category(int id, String name) {
        this.name = name;
        this.id = id;
    }

    private String name;
    private int id;
    private List<Product> products;

    /**
     * 为了配合首页按行显示产品的数据结构
     */
    private List<List<Product>> productsByRow;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<List<Product>> getProductsByRow() {
        return productsByRow;
    }

    public void setProductsByRow(List<List<Product>> productsByRow) {
        this.productsByRow = productsByRow;
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", products=" + products +
                ", productsByRow=" + productsByRow +
                '}';
    }
}
