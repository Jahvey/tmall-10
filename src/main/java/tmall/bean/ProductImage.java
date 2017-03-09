package tmall.bean;

/**
 * 作者: wangxh
 * 创建日期: 17-2-26
 * 简介:
 */
public class ProductImage {
    private String type;
    private Product product;
    private int id;

    public ProductImage() {
    }

    public ProductImage(Product product, String type) {
        this.product = product;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
}
