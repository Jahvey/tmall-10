package tmall.util;

/**
 * 作者: wangxh
 * 创建日期: 17-3-4
 * 简介:
 */
public class Page {
    private int start;
    private int count;
    private int total;
    private String param;

    public Page(int start, int count) {
        super();
        this.start = start;
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isHasPreviouse(){
        if(start==0)
            return false;
        return true;

    }

    public boolean isHasNext(){
        if(start==getLast())
            return false;
        return true;
    }

    public int getTotalPage(){
        int totalPage;
        if (0 == total % count)
            totalPage = total /count;
        else
            totalPage = total / count + 1;
        if(0==totalPage)
            totalPage = 1;
        return totalPage;
    }

    /**
     * 获取最后一页的页码
     * @return
     */
    private int getLast(){
        int last;
        // 假设总数是50，是能够被5整除的，那么最后一页的开始就是45
        if (0 == total % count)
            last = total - count;
            // 假设总数是51，不能够被5整除的，那么最后一页的开始就是50
        else
            last = total - total % count;
        last = last < 0 ? 0 : last;
        return last;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

}
