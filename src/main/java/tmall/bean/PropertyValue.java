package tmall.bean;

/**
 * 作者: wangxh
 * 创建日期: 17-2-26
 * 简介: 属性值表映射
 */
public class PropertyValue {
    private String value;
    private Product product;
    private Property property;
    private int id;

    public PropertyValue() {
    }

    public PropertyValue(int id, String value) {
        this.id = id;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public Property getProperty() {
        return property;
    }
    public void setProperty(Property property) {
        this.property = property;
    }
}
