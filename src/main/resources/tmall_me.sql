USE tmall_me;
-- 创建用户表
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS categroy;
DROP TABLE IF EXISTS property;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS propertyValue;
DROP TABLE IF EXISTS productImage;
DROP TABLE IF EXISTS review;
DROP TABLE IF EXISTS order_;
DROP TABLE IF EXISTS orderitem;
CREATE TABLE user (
    id INT NOT NULL AUTO_INCREMENT,
    name varchar(255) DEFAULT NULL,
    password varchar(255) DEFAULT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 创建分类表
CREATE TABLE categroy (
    id INT NOT NULL AUTO_INCREMENT,
    name varchar(255) DEFAULT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 创建属性表
CREATE TABLE property (
    id INT NOT NULL AUTO_INCREMENT,
    -- 对应 categroy 表的id
    -- 一个分类对应多个属性
    cid INT DEFAULT NULL,
    name varchar(255) DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_property_category FOREIGN KEY (cid) REFERENCES categroy (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 创建产品表
CREATE TABLE product (
    id INT NOT NULL AUTO_INCREMENT,
    name varchar(255) DEFAULT NULL,
    subTitle varchar(255) DEFAULT NULL,
    -- 原始价格
    orignalPrice FLOAT DEFAULT NULL,
    -- 优惠价格
    promotePrice FLOAT DEFAULT NULL,
    -- 库存
    stock INT DEFAULT NULL,
    cid INT DEFAULT NULL,
    createDate datetime DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_product_category FOREIGN KEY (cid) REFERENCES categroy (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 创建属性值表
CREATE TABLE propertyValue (
    id INT NOT NULL AUTO_INCREMENT,
    -- ptid 属性表外键
    pid INT DEFAULT NULL,
    -- pid 产品表外键
    ptid INT DEFAULT NULL,
    value varchar(255) DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_propertyvalue_property FOREIGN KEY (ptid) REFERENCES property(id) ON DELETE CASCADE,
    CONSTRAINT fk_propertvalue_product FOREIGN KEY (pid) REFERENCES product (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 创建产品图片表
CREATE TABLE productImage (
    id INT NOT NULL AUTO_INCREMENT,
    pid INT DEFAULT NULL,
    `type` varchar(255) DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_productimage_product FOREIGN KEY (pid) REFERENCES product (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 创建评价表
CREATE TABLE review (
    id INT NOT NULL AUTO_INCREMENT,
    content varchar(4000) DEFAULT NULL,
    -- 用户id
    uid INT DEFAULT NULL,
    -- pid 产品id
    pid INT DEFAULT NULL,
    createDate datetime DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_review_user FOREIGN KEY (uid) REFERENCES user (id),
    CONSTRAINT fk_review_product FOREIGN KEY (pid) REFERENCES product (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 创建订单表
-- 一个用户有多个订单
-- 一个订单有多个订单项
CREATE TABLE order_ (
    id INT NOT NULL AUTO_INCREMENT,
    orderCode varchar(255) DEFAULT NULL,
    address varchar(255) DEFAULT NULL,
    -- 邮箱
    post varchar(255) DEFAULT NULL,
    receiver varchar(255) DEFAULT NULL,
    mobile varchar(255) DEFAULT NULL,
    userMessage varchar(255) DEFAULT NULL,
    createDate datetime DEFAULT NULL,
    payDate datetime DEFAULT NULL,
    deliveryDate datetime DEFAULT NULL,
    confirmDate datetime DEFAULT NULL,
    uid INT DEFAULT NULL,
    status varchar(255) DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_order_user FOREIGN KEY (uid) REFERENCES user (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 创建订单项目表
-- 一笔订单有多个订单项
-- 一件产品可以有多个订单项
-- 一个用户可以有多个订单项
-- 一个订单项对应一笔订单, 一件产品，一个用户
CREATE TABLE orderitem (
    id INT NOT NULL AUTO_INCREMENT,
    -- 产品表外键
    pid INT DEFAULT NULL,
    -- 用户表外键
    uid INT DEFAULT NULL,
    -- 订单表外键
    oid INT DEFAULT NULL,
    number INT DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_orderitem_product FOREIGN KEY (pid) REFERENCES product (id),
    CONSTRAINT fk_orderitem_user FOREIGN KEY (uid) REFERENCES user (id),
    CONSTRAINT fk_orderitem_order FOREIGN KEY (oid) REFERENCES order_ (id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;







