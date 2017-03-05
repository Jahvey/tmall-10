package tmall.dao;

import tmall.dao.impl.*;

/**
 * 作者: wangxh
 * 创建日期: 17-3-4
 * 简介:
 */
public class DAOFactory {
    public static  ICategoryDAO categoryDAO() {return new CategoryDAO();}
    public static IOrderDAO orderDAO() {return new OrderDAO();}
    public static IOrderItemDAO orderItemDAO() {return new OrderItemDAO();}
    public static IProductDAO productDAO() {return new ProductDAO();}
    public static IProductImageDAO productImageDAO() {return new ProductImageDAO();}
    public static IPropertyDAO propertyDAO() {return new PropertyDAO();}
    public static IPropertyValueDAO propertyValueDAO() {return new PropertyValueDAO();}
    public static IReviewDAO reviewDAO() {return new ReviewDAO();}
    public static IUserDAO userDAO() {return new UserDAO();}
}
