package tmall.util;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.*;

/**
 * 作者: wangxh
 * 创建日期: 17-2-26
 * 简介:
 */
public class DBUtilTest {

    @Test
    public void getConnection() throws Exception {
        try (Connection connection = DBUtil.getConnection()) {
            Assert.assertNotNull(connection);
        }
    }

}