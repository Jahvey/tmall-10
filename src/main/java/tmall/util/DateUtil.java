package tmall.util;

/**
 * 作者: wangxh
 * 创建日期: 17-2-26
 * 简介: 日期转换工具类
 */
public class DateUtil {
    public static java.sql.Timestamp dateToTimestamp(java.util.Date d) {
        if (null == d) return null;
        return new java.sql.Timestamp(d.getTime());
    }

    public static java.util.Date timeStampToDate(java.sql.Timestamp t) {
        if (null == t) return null;
        return new java.util.Date(t.getTime());
    }
}
