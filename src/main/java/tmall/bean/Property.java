package tmall.bean;

/**
 * 作者: wangxh
 * 创建日期: 17-2-26
 * 简介: 属性表映射
 */
public class Property {
    private String name;
    private Category category;
    private int id;
    private int cid;

    public Property() {}

    public Property(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    public Property(String name, Category category, int id) {
        this.name = name;
        this.category = category;
        this.id = id;
    }

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
    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }
    public int getCid() {
        return cid;
    }
    public void setCid(int cid) {
        this.cid = cid;
    }

    @Override
    public String toString() {
        return "Property{" +
                "name='" + name + '\'' +
                ", category=" + category +
                ", id=" + id +
                ", cid=" + cid +
                '}';
    }
}
