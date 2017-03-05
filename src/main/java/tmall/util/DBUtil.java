package tmall.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 作者: wangxh
 * 创建日期: 17-2-26
 * 简介: 数据库工具类
 */
public class DBUtil {

    private static Logger logger = LoggerFactory.getLogger(DBUtil.class);

    private static String ip = "127.0.0.1";
    private static int port = 3306;
    private static String database = "tmall_me";
    private static String encoding = "UTF-8";
    private static String loginName = "root";
    private static String password = "admin";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            logger.error("注册JDBC驱动出错", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        String url = String.format("jdbc:mysql://%s:%d/%s?characterEncoding=%s", ip, port, database, encoding);
        return DriverManager.getConnection(url, loginName, password);
    }
}
