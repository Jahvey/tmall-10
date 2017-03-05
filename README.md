使用基础的JSP + Serlvet搭建的仿天猫样式的电商网站
==========================================

思路
----

### 后台

#### 基本流程

1. 访问后台URL **/tmall/admin**
2. 通过 **AdminServlet** 将链接指向 **admin_category_list**
3. 过滤器 **BackServletFilter** 中通过字符串截取的方式将 **admin_category_list**
    中的 **category** 和 **list** 关键字截取出来
4. 将category拼接成categoryServlet并通过 `setAttribute` 将 `method=list` 传入
5. 在 **categoryServlet** 中定义了若干个方法, 根据传入的 **method** 值进行调用,
    通用的service方法写在父类中
6. 调用完毕后返回字符串, `service` 方法根据返回的字符串结果进行处理

#### 图片显示

数据库中不保存图片链接, 图片链接默认以分类的id保存在 */imgs* 的各个对应路径下
需要在上传时就根据id在相应路径下进行保存


