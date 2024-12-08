-- 创建数据库
CREATE DATABASE IF NOT EXISTS online_shopping_mall DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE online_shopping_mall;

CREATE TABLE jwt_blacklist
(
    id         INT AUTO_INCREMENT
        PRIMARY KEY,
    token      VARCHAR(255)                        NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL COMMENT '加入黑名单时间',
    CONSTRAINT uniq_token
        UNIQUE (token)
)
    COMMENT 'JWT 黑名单表' CHARSET = utf8mb4;

CREATE TABLE orders
(
    order_id     INT AUTO_INCREMENT
        PRIMARY KEY,
    user_id      INT                                    NOT NULL,
    address_id   INT                                    NOT NULL,
    product_id   INT                                    NOT NULL,
    quantity     INT                                    NOT NULL,
    total_amount DECIMAL(10, 2)                         NOT NULL,
    order_status ENUM ('未支付', '已支付')              NOT NULL,
    order_note   VARCHAR(255) DEFAULT '未备注'          NULL,
    created_at   TIMESTAMP    DEFAULT CURRENT_TIMESTAMP NULL,
    updated_at   TIMESTAMP    DEFAULT CURRENT_TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    order_number VARCHAR(20)                            NOT NULL COMMENT '订单号'
);

CREATE TABLE product_categories
(
    id         BIGINT AUTO_INCREMENT COMMENT '分类主键'
        PRIMARY KEY,
    name       VARCHAR(100)                        NOT NULL COMMENT '分类名称',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    CONSTRAINT name
        UNIQUE (name)
)
    COMMENT '商品分类表' CHARSET = utf8mb4;

CREATE TABLE products
(
    id          BIGINT AUTO_INCREMENT COMMENT '商品主键'
        PRIMARY KEY,
    name        VARCHAR(100)                        NOT NULL COMMENT '商品名称',
    description TEXT                                NULL COMMENT '商品描述',
    price       DECIMAL(10, 2)                      NOT NULL COMMENT '商品价格',
    stock       INT                                 NOT NULL COMMENT '商品库存',
    category_id BIGINT                              NOT NULL COMMENT '商品分类 ID',
    image_url   VARCHAR(255)                        NULL COMMENT '商品图片路径',
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL COMMENT '创建时间',
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    CONSTRAINT name
        UNIQUE (name),
    CONSTRAINT fk_category
        FOREIGN KEY (category_id) REFERENCES product_categories (id)
            ON DELETE CASCADE
)
    COMMENT '商品信息表';

CREATE INDEX idx_category_id
    ON products (category_id);

CREATE FULLTEXT INDEX idx_name_description
    ON products (name, description);

CREATE INDEX idx_price
    ON products (price);

CREATE TABLE users
(
    id          BIGINT AUTO_INCREMENT COMMENT '用户主键'
        PRIMARY KEY,
    username    VARCHAR(50)                                                                                                               NOT NULL COMMENT '用户名',
    password    VARCHAR(255)                                                                                                              NOT NULL COMMENT '加密后的密码',
    email       VARCHAR(100)                                                                                                              NULL COMMENT '邮箱',
    phone       VARCHAR(20)                                                                                                               NULL COMMENT '手机号',
    nickname    VARCHAR(50)                                                                                                               NULL COMMENT '昵称',
    createdtime TIMESTAMP    DEFAULT CURRENT_TIMESTAMP                                                                                    NOT NULL COMMENT '创建时间',
    updatedtime TIMESTAMP    DEFAULT CURRENT_TIMESTAMP                                                                                    NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    avatar      VARCHAR(255) DEFAULT 'https://online-shopping-mall.oss-cn-chengdu.aliyuncs.com/a76a37e3-0b60-4632-9ba1-1e4849e2aa7d..jpg' NOT NULL,
    CONSTRAINT uniq_username
        UNIQUE (username)
)
    COMMENT '用户表' CHARSET = utf8mb4;

CREATE TABLE addresses
(
    id            BIGINT AUTO_INCREMENT COMMENT '地址主键'
        PRIMARY KEY,
    user_id       BIGINT                                 NOT NULL COMMENT '用户 ID',
    receiver_name VARCHAR(50)                            NOT NULL COMMENT '收货人姓名',
    phone         VARCHAR(20)                            NOT NULL COMMENT '收货人电话',
    created_at    TIMESTAMP    DEFAULT CURRENT_TIMESTAMP NULL COMMENT '创建时间',
    updated_at    TIMESTAMP    DEFAULT CURRENT_TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    province      VARCHAR(255) DEFAULT ''                NOT NULL,
    city          VARCHAR(255) DEFAULT ''                NOT NULL,
    district      VARCHAR(255) DEFAULT ''                NOT NULL,
    detail        VARCHAR(255) DEFAULT ''                NOT NULL,
    is_default    TINYINT(1)   DEFAULT 0                 NOT NULL,
    CONSTRAINT fk_addresses_user_id
        FOREIGN KEY (user_id) REFERENCES users (id)
            ON DELETE CASCADE
)
    COMMENT '收货地址表' CHARSET = utf8mb4;

CREATE INDEX idx_user_id
    ON addresses (user_id);

CREATE TABLE reviews
(
    id         BIGINT AUTO_INCREMENT
        PRIMARY KEY,
    product_id BIGINT                                 NOT NULL,
    user_id    BIGINT                                 NOT NULL,
    rating     TINYINT      DEFAULT 5                 NULL,
    comment    VARCHAR(255) DEFAULT '暂无评价'        NULL,
    created_at DATETIME     DEFAULT CURRENT_TIMESTAMP NULL,
    updated_at DATETIME     DEFAULT CURRENT_TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_review_product
        FOREIGN KEY (product_id) REFERENCES products (id),
    CONSTRAINT fk_review_user
        FOREIGN KEY (user_id) REFERENCES users (id)
            ON DELETE CASCADE
);


-- 插入初始数据
INSERT INTO product_categories (name) VALUES 
('电子产品'),
('服装'),
('食品'),
('图书');

-- 插入测试商品
INSERT INTO products (name, description, price, stock, category_id, image_url) VALUES
('iPhone 15', '最新款苹果手机', 6999.00, 100, 1, 'https://online-shopping-mall.oss-cn-chengdu.aliyuncs.com/product1.jpg'),
('MacBook Pro', '专业级笔记本电脑', 12999.00, 50, 1, 'https://online-shopping-mall.oss-cn-chengdu.aliyuncs.com/product2.jpg'),
('Nike运动鞋', '舒适透气运动鞋', 599.00, 200, 2, 'https://online-shopping-mall.oss-cn-chengdu.aliyuncs.com/product3.jpg');

-- 插入测试用户（密码：123456）
INSERT INTO users (username, password, email, phone, nickname) VALUES
('admin', '$2a$10$x/JfQHZhPQxLHRLmq.vN0.yp5YfKFE3tXWAqnXBwRhEqhYyXnK3rW', 'admin@example.com', '13800138000', '管理员');