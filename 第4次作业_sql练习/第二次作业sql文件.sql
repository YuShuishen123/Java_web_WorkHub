-- 练习1 数据库操作
# 需求：结合 SQL 空文件的中的提示，完成数据库的创建、查看、使用、删除操作。
-- 示例1：创建一个名为 bigdata_db 的数据库
CREATE DATABASE bigdata_db;
-- 示例2：数据库不存在时，才创建数据库
CREATE DATABASE IF NOT EXISTS java_db;
-- 示例3：创建数据库并指定 utf8 编码字符集
CREATE DATABASE IF NOT EXISTS bigdata_db2 CHARACTER SET utf8;
-- 示例4：查看当前有哪些数据库
SHOW DATABASES;
-- 示例5：使用bigdata_db 数据库
USE bigdata_db;
-- 示例6：使用bigdata_db2 数据库
USE bigdata_db2;
-- 示例7：查看当前使用的是哪个数据库
SELECT DATABASE();
-- 示例8：删除 bigdata_db 数据库
DROP DATABASE bigdata_db;
-- 示例9：删除 bigdata_db2 数据库
DROP DATABASE IF EXISTS bigdata_db2;
-- ---------------------------------------------------------------------
-- 练习2 数据表操作
# 需求：结合 SQL 空文件的中的提示，完成数据表的创建、查看、重命名、删除操作。
-- 创建一个名为 bigdata_db 的数据库用来进行操作
CREATE DATABASE IF NOT EXISTS bigdata_db CHARACTER SET utf8;
USE bigdata_db;
-- 示例1：创建一个 users 数据表
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL
);
-- 示例2：查看当前数据库中有哪些表
SHOW TABLES;
-- 示例3：查看users 数据表的结构
DESC users;
-- 示例4：将 users 表重命名为categories
RENAME TABLE users TO categories;
-- ---------------------------------------------------------------------
-- 练习3 表字段操作
# 需求：结合SQL 空文件的中的提示，完成表字段的添加、修改、删除操作。
-- 示例1：给category 表添加一个 num 字段，整数类型并且不能为空
ALTER TABLE categories ADD num INT NOT NULL;
-- 示例2：给 category 表添加一个desc 字段，最大长度为100个字符
ALTER TABLE categories ADD `desc` VARCHAR(100);
-- 示例3：将 category 表的 desc 字段修改为 description 字段
ALTER TABLE categories CHANGE `desc` description VARCHAR(100);
-- 示例4：删除 category 表的 num 字段
ALTER TABLE categories DROP num;
-- ---------------------------------------------------------------------
-- 练习4 插入操作
# 需求：结合SQL空文件的中的提示，完成表数据的插入操作。
# 示例1：在category表中插入1条记录：不指定字段
INSERT INTO categories VALUES (1, '用户1', 10, '描述1');
# 示例2：在category 表中插入1条记录：指定字段
INSERT INTO categories (id, name, age, description) VALUES (2, '用户2', 20, '描述2');
# 示例3：在 category 中一次插入2行记录：不指定字段
INSERT INTO categories
VALUES (3, '用户3', 30, '描述3'),
       (4, '用户4', 40, '描述4');
# 示例4：在category 中一次插入3行记录：指定字段
INSERT INTO categories (id, name, age, description)
VALUES (5, '用户5', 50, '描述5'),
       (6, '用户6', 60, '描述6'),
       (7, '用户7', 70, '描述7');
-- ---------------------------------------------------------------------
-- 练习5 更新操作
# 需求：结合SQL 空文件的中的提示，完成表数据的更新操作。
# 删除age字段，将name改为cname，id改为cid
ALTER TABLE categories DROP age;
ALTER TABLE categories CHANGE name cname VARCHAR(100);
ALTER TABLE categories CHANGE id cid INT;
# 示例1：将 category 表中所有行的勺cname改为’家电
UPDATE categories SET cname = '家电';
# 示例2：将category 表中cid 为1 的记录的」cname改为’服装
UPDATE categories SET cname = '服装' WHERE cid = 1;
-- ---------------------------------------------------------------------
-- 练习6 删除操作
# 需求：结合 SQL空文件的中的提示，完成表数据的删除操作。
# 示例1：删除category 表中cid 为 5 的记录
DELETE FROM categories WHERE cid = 5;
# 示例2：删除余category 表中的所有记录
DELETE FROM categories;
-- ---------------------------------------------------------------------
-- 练习7 主键约束
# 需求：结合 SQL 空文件的中的提示，完成主键的相关操作。
# 示例1：创建表时添加主键约束
CREATE TABLE users (
    id INT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL
);
# 示例2：创建表后添加主键约束
ALTER TABLE users ADD PRIMARY KEY (id);
# 示例3:：删除主键约束
ALTER TABLE users DROP PRIMARY KEY;
# 示例4：主键约束必须是非空且唯一
CREATE TABLE users (
    id INT PRIMARY KEY NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL
);
-- ---------------------------------------------------------------------
-- 练习8 主键自增
# 需求：结合SQL 空文件的中的提示，完成主键自增的设置及使用，
# 示例5：主键自动增长设置
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL
);
-- ---------------------------------------------------------------------
-- 练习9 非空/唯一/默认值约束
# 需求：结合SQL 空文件的中的提示，完成非空/唯一/默认值约束的使用。
# 示例6：创建表时添加非空约束
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL
);
# 示例7：在创建表时添加唯一约束
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT UNIQUE,
    name VARCHAR(100) NOT NULL UNIQUE,
    age INT NOT NULL
);
# 示例8：在创建表时添加默认值
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL DEFAULT '匿名',
    age INT NOT NULL DEFAULT 0
);
-- ---------------------------------------------------------------------