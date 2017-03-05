package tmall.bean;

/**
 * 作者: wangxh
 * 创建日期: 17-2-26
 * 简介: 订单项映射
 */
public class OrderItem {
    private int number;
    private Product product;
    private Order order;
    private User user;
    private int id;

    public OrderItem() {}

    public OrderItem(int number, Product product, Order order, User user) {
        this.number = number;
        this.product = product;
        this.order = order;
        this.user = user;
    }

    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public Order getOrder() {
        return order;
    }
    public void setOrder(Order order) {
        this.order = order;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "number=" + number +
                ", product=" + product +
                '}';
    }
}
