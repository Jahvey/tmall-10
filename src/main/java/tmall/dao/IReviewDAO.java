package tmall.dao;

import tmall.bean.Review;

import java.util.List;

/**
 * 作者: wangxh
 * 创建日期: 17-3-2
 * 简介:
 */
public interface IReviewDAO {

    int getTotal();

    /**
     * 获取产品评价
     * @param pid
     * @return
     */
    int getTotal(int pid);

    /**
     * 查询某建产品下的所有评论
     * @param pid 产品id
     * @return
     */
    int getCount(int pid);


    /**
     * 添加评论
     * @param review
     */
    void add(Review review);

    /**
     * 更新评论
     * @param review
     */
    void update(Review review);

    /**
     * 删除评论
     * @param id
     */
    void delete(int id);

    /**
     * 获取文章下的所有评论
     * @param pid 文章id
     * @return
     */
    List<Review> list(int pid);

    List<Review> list(int pid, int start, int count);

    /**
     * 判断一条评论是否存在
     * @param pid 评论id
     * @param content 评论内容
     * @return
     */
    boolean isExist(int pid, String content);
}
